// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.util;

import com.jogamp.common.util.Bitfield;

public class SyncedBitfield implements Bitfield
{
    private final Bitfield impl;
    
    public SyncedBitfield(final Bitfield impl) {
        this.impl = impl;
    }
    
    @Override
    public final synchronized int size() {
        return this.impl.size();
    }
    
    @Override
    public final synchronized void clearField(final boolean b) {
        this.impl.clearField(b);
    }
    
    @Override
    public final synchronized int get32(final int n, final int n2) throws IndexOutOfBoundsException {
        return this.impl.get32(n, n2);
    }
    
    @Override
    public final synchronized void put32(final int n, final int n2, final int n3) throws IndexOutOfBoundsException {
        this.impl.put32(n, n2, n3);
    }
    
    @Override
    public final synchronized int copy32(final int n, final int n2, final int n3) throws IndexOutOfBoundsException {
        return this.impl.copy32(n, n2, n3);
    }
    
    @Override
    public final synchronized boolean get(final int n) throws IndexOutOfBoundsException {
        return this.impl.get(n);
    }
    
    @Override
    public final synchronized boolean put(final int n, final boolean b) throws IndexOutOfBoundsException {
        return this.impl.put(n, b);
    }
    
    @Override
    public final synchronized void set(final int n) throws IndexOutOfBoundsException {
        this.impl.set(n);
    }
    
    @Override
    public final synchronized void clear(final int n) throws IndexOutOfBoundsException {
        this.impl.clear(n);
    }
    
    @Override
    public final synchronized boolean copy(final int n, final int n2) throws IndexOutOfBoundsException {
        return this.impl.copy(n, n2);
    }
    
    @Override
    public final synchronized int bitCount() {
        return this.impl.bitCount();
    }
}
