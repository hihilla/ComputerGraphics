// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import java.util.ArrayList;

public class CyclicList<T> extends ArrayList<T>
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public T get(final int i) {
        final int size = this.size();
        return super.get((i % size + size) % size);
    }
}
