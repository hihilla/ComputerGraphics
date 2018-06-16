// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.packrect;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Level
{
    private final int width;
    private int height;
    private final int yPos;
    private final LevelSet holder;
    private final List<Rect> rects;
    private List<Rect> freeList;
    private int nextAddX;
    private static final Comparator<Rect> rectXComparator;
    
    public Level(final int width, final int height, final int yPos, final LevelSet holder) {
        this.rects = new ArrayList<Rect>();
        this.width = width;
        this.height = height;
        this.yPos = yPos;
        this.holder = holder;
    }
    
    public int w() {
        return this.width;
    }
    
    public int h() {
        return this.height;
    }
    
    public int yPos() {
        return this.yPos;
    }
    
    public boolean add(final Rect rect) {
        if (rect.h() > this.height) {
            if (this.nextAddX + rect.w() > this.width) {
                return false;
            }
            if (!this.holder.canExpand(this, rect.h())) {
                return false;
            }
            this.holder.expand(this, this.height, rect.h());
            this.height = rect.h();
        }
        if (this.nextAddX + rect.w() <= this.width) {
            rect.setPosition(this.nextAddX, this.yPos);
            this.rects.add(rect);
            this.nextAddX += rect.w();
            return true;
        }
        if (this.freeList != null) {
            Rect rect2 = null;
            for (final Rect rect3 : this.freeList) {
                if (rect3.canContain(rect)) {
                    rect2 = rect3;
                    break;
                }
            }
            if (rect2 != null) {
                this.freeList.remove(rect2);
                rect.setPosition(rect2.x(), rect2.y());
                this.rects.add(rect);
                if (rect2.w() > rect.w()) {
                    rect2.setPosition(rect2.x() + rect.w(), rect2.y());
                    rect2.setSize(rect2.w() - rect.w(), this.height);
                    this.freeList.add(rect2);
                }
                this.coalesceFreeList();
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(final Rect rect) {
        if (!this.rects.remove(rect)) {
            return false;
        }
        if (rect.maxX() + 1 == this.nextAddX) {
            this.nextAddX -= rect.w();
        }
        else {
            if (this.freeList == null) {
                this.freeList = new ArrayList<Rect>();
            }
            this.freeList.add(new Rect(rect.x(), rect.y(), rect.w(), this.height, null));
            this.coalesceFreeList();
        }
        return true;
    }
    
    public boolean isEmpty() {
        return this.rects.isEmpty();
    }
    
    public boolean couldAllocateIfCompacted(final Rect rect) {
        if (rect.h() > this.height) {
            return false;
        }
        if (this.freeList == null) {
            return false;
        }
        int n = 0;
        final Iterator<Rect> iterator = this.freeList.iterator();
        while (iterator.hasNext()) {
            n += iterator.next().w();
        }
        return n + (this.width - this.nextAddX) >= rect.w();
    }
    
    public void compact(final Object o, final BackingStoreManager backingStoreManager) {
        Collections.sort(this.rects, Level.rectXComparator);
        int nextAddX = 0;
        backingStoreManager.beginMovement(o, o);
        for (final Rect rect : this.rects) {
            if (rect.x() != nextAddX) {
                backingStoreManager.move(o, rect, o, new Rect(nextAddX, rect.y(), rect.w(), rect.h(), null));
                rect.setPosition(nextAddX, rect.y());
            }
            nextAddX += rect.w();
        }
        this.nextAddX = nextAddX;
        this.freeList.clear();
        backingStoreManager.endMovement(o, o);
    }
    
    public Iterator<Rect> iterator() {
        return this.rects.iterator();
    }
    
    public void visit(final RectVisitor rectVisitor) {
        final Iterator<Rect> iterator = this.rects.iterator();
        while (iterator.hasNext()) {
            rectVisitor.visit(iterator.next());
        }
    }
    
    public void updateRectangleReferences() {
        for (int i = 0; i < this.rects.size(); ++i) {
            final Rect rect = this.rects.get(i);
            final Rect nextLocation = rect.getNextLocation();
            nextLocation.setPosition(rect.x(), rect.y());
            if (rect.w() != nextLocation.w() || rect.h() != nextLocation.h()) {
                throw new RuntimeException("Unexpected disparity in rectangle sizes during updateRectangleReferences");
            }
            this.rects.set(i, nextLocation);
        }
    }
    
    private void coalesceFreeList() {
        if (this.freeList == null) {
            return;
        }
        if (this.freeList.isEmpty()) {
            return;
        }
        Collections.sort(this.freeList, Level.rectXComparator);
        int i = 0;
        while (i < this.freeList.size() - 1) {
            final Rect rect = this.freeList.get(i);
            final Rect rect2 = this.freeList.get(i + 1);
            if (rect.maxX() + 1 == rect2.x()) {
                this.freeList.remove(i + 1);
                rect.setSize(rect.w() + rect2.w(), rect.h());
            }
            else {
                ++i;
            }
        }
        final Rect rect3 = this.freeList.get(this.freeList.size() - 1);
        if (rect3.maxX() + 1 == this.nextAddX) {
            this.nextAddX -= rect3.w();
            this.freeList.remove(this.freeList.size() - 1);
        }
        if (this.freeList.isEmpty()) {
            this.freeList = null;
        }
    }
    
    public void dumpFreeSpace() {
        int n = 0;
        for (final Rect rect : this.freeList) {
            System.err.println(" Free rectangle at " + rect);
            n += rect.w();
        }
        System.err.println(" Remaining free space " + (this.width - this.nextAddX));
        System.err.println(" Total free space " + (n + (this.width - this.nextAddX)));
    }
    
    static {
        rectXComparator = new RectXComparator();
    }
    
    static class RectXComparator implements Comparator<Rect>
    {
        @Override
        public int compare(final Rect rect, final Rect rect2) {
            return rect.x() - rect2.x();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o;
        }
    }
}
