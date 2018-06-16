// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.packrect;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class LevelSet
{
    private final List<Level> levels;
    private int nextAddY;
    private final int w;
    private int h;
    
    public LevelSet(final int w, final int h) {
        this.levels = new ArrayList<Level>();
        this.w = w;
        this.h = h;
    }
    
    public int w() {
        return this.w;
    }
    
    public int h() {
        return this.h;
    }
    
    public boolean add(final Rect rect) {
        if (rect.w() > this.w) {
            return false;
        }
        for (int i = this.levels.size() - 1; i >= 0; --i) {
            if (this.levels.get(i).add(rect)) {
                return true;
            }
        }
        for (int j = this.levels.size() - 1; j >= 0; --j) {
            if (this.levels.get(j).couldAllocateIfCompacted(rect)) {
                return false;
            }
        }
        if (this.nextAddY + rect.h() > this.h) {
            return false;
        }
        final Level level = new Level(this.w, rect.h(), this.nextAddY, this);
        this.levels.add(level);
        this.nextAddY += rect.h();
        if (!level.add(rect)) {
            throw new RuntimeException("Unexpected failure in addition to new Level");
        }
        return true;
    }
    
    public boolean remove(final Rect rect) {
        for (int i = this.levels.size() - 1; i >= 0; --i) {
            if (this.levels.get(i).remove(rect)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean compactAndAdd(final Rect rect, final Object o, final BackingStoreManager backingStoreManager) {
        int i = this.levels.size() - 1;
        while (i >= 0) {
            final Level level = this.levels.get(i);
            if (level.couldAllocateIfCompacted(rect)) {
                level.compact(o, backingStoreManager);
                if (!level.add(rect)) {
                    throw new RuntimeException("Unexpected failure to add after compaction");
                }
                return true;
            }
            else {
                --i;
            }
        }
        return false;
    }
    
    public boolean canExpand(final Level level, final int n) {
        return !this.levels.isEmpty() && (this.levels.get(this.levels.size() - 1) == level && this.h - this.nextAddY >= n - level.h());
    }
    
    public void expand(final Level level, final int n, final int n2) {
        this.nextAddY += n2 - n;
    }
    
    public int getUsedHeight() {
        return this.nextAddY;
    }
    
    public void setHeight(final int h) throws IllegalArgumentException {
        if (h < this.getUsedHeight()) {
            throw new IllegalArgumentException("May not reduce height below currently used height");
        }
        this.h = h;
    }
    
    public float verticalFragmentationRatio() {
        int n = 0;
        final int usedHeight = this.getUsedHeight();
        if (usedHeight == 0) {
            return 0.0f;
        }
        for (final Level level : this) {
            if (level.isEmpty()) {
                n += level.h();
            }
        }
        return n / usedHeight;
    }
    
    public Iterator<Level> iterator() {
        return this.levels.iterator();
    }
    
    public void visit(final RectVisitor rectVisitor) {
        final Iterator<Level> iterator = this.levels.iterator();
        while (iterator.hasNext()) {
            iterator.next().visit(rectVisitor);
        }
    }
    
    public void updateRectangleReferences() {
        final Iterator<Level> iterator = this.levels.iterator();
        while (iterator.hasNext()) {
            iterator.next().updateRectangleReferences();
        }
    }
    
    public void clear() {
        this.levels.clear();
        this.nextAddY = 0;
    }
}
