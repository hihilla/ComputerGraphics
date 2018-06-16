// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.ByteBuffer;

public abstract class GLBufferStorage
{
    private final int name;
    private long size;
    private int mutableUsage;
    private int immutableFlags;
    private ByteBuffer mappedBuffer;
    private static final String msgClazzName = "GLBufferStorage";
    
    protected GLBufferStorage(final int name, final long size, final int mutableUsage, final int immutableFlags) {
        this.name = name;
        this.size = size;
        this.mutableUsage = mutableUsage;
        this.immutableFlags = immutableFlags;
        this.mappedBuffer = null;
    }
    
    protected void reset(final long size, final int mutableUsage, final int immutableFlags) {
        this.size = size;
        this.mutableUsage = mutableUsage;
        this.immutableFlags = immutableFlags;
        this.mappedBuffer = null;
    }
    
    protected void setMappedBuffer(final ByteBuffer mappedBuffer) {
        this.mappedBuffer = mappedBuffer;
    }
    
    public final int getName() {
        return this.name;
    }
    
    public final long getSize() {
        return this.size;
    }
    
    public final boolean isMutableStorage() {
        return 0 != this.mutableUsage;
    }
    
    public final int getMutableUsage() {
        return this.mutableUsage;
    }
    
    public final int getImmutableFlags() {
        return this.immutableFlags;
    }
    
    public final ByteBuffer getMappedBuffer() {
        return this.mappedBuffer;
    }
    
    @Override
    public final String toString() {
        return this.toString(false);
    }
    
    public final String toString(final boolean b) {
        String s;
        if (this.isMutableStorage()) {
            s = String.format("%s[name %s, size %d, mutable usage 0x%X", "GLBufferStorage", this.name, this.size, this.mutableUsage);
        }
        else {
            s = String.format("%s[name %s, size %d, immutable flags 0x%X", "GLBufferStorage", this.name, this.size, this.immutableFlags);
        }
        if (b) {
            return s + "]";
        }
        return s + ", mapped " + this.mappedBuffer + "]";
    }
}
