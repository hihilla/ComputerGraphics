// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.packrect;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class RectanglePacker
{
    private final BackingStoreManager manager;
    private Object backingStore;
    private LevelSet levels;
    private static final float EXPANSION_FACTOR = 0.5f;
    private static final float SHRINK_FACTOR = 0.3f;
    private final int initialWidth;
    private final int initialHeight;
    private int maxWidth;
    private int maxHeight;
    private static final Comparator<Rect> rectHComparator;
    
    public RectanglePacker(final BackingStoreManager manager, final int initialWidth, final int initialHeight) {
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.manager = manager;
        this.levels = new LevelSet(initialWidth, initialHeight);
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
    }
    
    public Object getBackingStore() {
        if (this.backingStore == null) {
            this.backingStore = this.manager.allocateBackingStore(this.levels.w(), this.levels.h());
        }
        return this.backingStore;
    }
    
    public void setMaxSize(final int maxWidth, final int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    
    public void add(final Rect rect) throws RuntimeException {
        if (this.backingStore == null) {
            this.backingStore = this.manager.allocateBackingStore(this.levels.w(), this.levels.h());
        }
        int n = 0;
        while (!this.levels.add(rect)) {
            boolean b;
            if (this.manager.canCompact()) {
                if (this.levels.compactAndAdd(rect, this.backingStore, this.manager)) {
                    return;
                }
                b = this.manager.preExpand(rect, n++);
            }
            else {
                b = this.manager.additionFailed(rect, n++);
            }
            if (!b) {
                if (!this.manager.canCompact()) {
                    throw new RuntimeException("BackingStoreManager does not support compaction or expansion, and didn't clear space for new rectangle");
                }
                this.compactImpl(rect);
                this.add(rect);
            }
        }
    }
    
    public void remove(final Rect rect) {
        this.levels.remove(rect);
    }
    
    public void visit(final RectVisitor rectVisitor) {
        this.levels.visit(rectVisitor);
    }
    
    public float verticalFragmentationRatio() {
        return this.levels.verticalFragmentationRatio();
    }
    
    public void compact() {
        this.compactImpl(null);
    }
    
    private void compactImpl(final Rect rect) {
        int i = 0;
        int n = this.levels.w();
        int n2 = this.levels.h();
        LevelSet levels = null;
        int n3 = 0;
        while (i == 0) {
            if (rect != null) {
                if (rect.w() > n) {
                    n = rect.w();
                }
                else {
                    n2 *= (int)1.5f;
                }
            }
            boolean b = false;
            if (this.maxWidth > 0 && n > this.maxWidth) {
                n = this.maxWidth;
                b = true;
            }
            if (this.maxHeight > 0 && n2 > this.maxHeight) {
                n2 = this.maxHeight;
                b = true;
            }
            levels = new LevelSet(n, n2);
            final ArrayList<Object> list = new ArrayList<Object>();
            final Iterator<Level> iterator = this.levels.iterator();
            while (iterator.hasNext()) {
                for (final Rect nextLocation : iterator.next()) {
                    final Rect nextLocation2 = new Rect(0, 0, nextLocation.w(), nextLocation.h(), null);
                    nextLocation.setNextLocation(nextLocation2);
                    nextLocation2.setNextLocation(nextLocation);
                    list.add(nextLocation2);
                }
            }
            Collections.sort(list, (Comparator<? super Object>)RectanglePacker.rectHComparator);
            i = 1;
            final Iterator<Rect> iterator3 = list.iterator();
            while (iterator3.hasNext()) {
                if (!levels.add(iterator3.next())) {
                    i = 0;
                    break;
                }
            }
            if (i != 0 && rect != null) {
                if (!levels.add(rect)) {
                    i = 0;
                }
            }
            if (i == 0 && b && rect != null) {
                this.manager.additionFailed(rect, n3);
            }
            ++n3;
        }
        if (levels.getUsedHeight() > 0 && levels.getUsedHeight() < levels.h() * 0.3f) {
            int height = Math.max(this.initialHeight, (int)(levels.getUsedHeight() * 1.5f));
            if (this.maxHeight > 0 && height > this.maxHeight) {
                height = this.maxHeight;
            }
            levels.setHeight(height);
        }
        if (rect != null) {
            levels.remove(rect);
        }
        final Object allocateBackingStore = this.manager.allocateBackingStore(levels.w(), levels.h());
        this.manager.beginMovement(this.backingStore, allocateBackingStore);
        final Iterator<Level> iterator4 = this.levels.iterator();
        while (iterator4.hasNext()) {
            for (final Rect rect2 : iterator4.next()) {
                this.manager.move(this.backingStore, rect2, allocateBackingStore, rect2.getNextLocation());
            }
        }
        levels.updateRectangleReferences();
        this.manager.endMovement(this.backingStore, allocateBackingStore);
        this.manager.deleteBackingStore(this.backingStore);
        this.backingStore = allocateBackingStore;
        this.levels = levels;
    }
    
    public void clear() {
        this.levels.clear();
    }
    
    public void dispose() {
        if (this.backingStore != null) {
            this.manager.deleteBackingStore(this.backingStore);
        }
        this.backingStore = null;
        this.levels = null;
    }
    
    static {
        rectHComparator = new RectHComparator();
    }
    
    static class RectHComparator implements Comparator<Rect>
    {
        @Override
        public int compare(final Rect rect, final Rect rect2) {
            return rect2.h() - rect.h();
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o;
        }
    }
}
