// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import java.nio.Buffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLArrayData;

public interface GLArrayDataEditable extends GLArrayData
{
    boolean sealed();
    
    boolean enabled();
    
    boolean isVBOWritten();
    
    void setVBOWritten(final boolean p0);
    
    void destroy(final GL p0);
    
    void reset(final GL p0);
    
    void seal(final GL p0, final boolean p1);
    
    void enableBuffer(final GL p0, final boolean p1);
    
    boolean bindBuffer(final GL p0, final boolean p1);
    
    void setEnableAlways(final boolean p0);
    
    void reset();
    
    void seal(final boolean p0);
    
    void rewind();
    
    void padding(final int p0);
    
    void put(final Buffer p0);
    
    void putb(final byte p0);
    
    void puts(final short p0);
    
    void puti(final int p0);
    
    void putx(final int p0);
    
    void putf(final float p0);
}
