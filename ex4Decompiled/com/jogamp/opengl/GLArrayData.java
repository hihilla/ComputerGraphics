// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;

public interface GLArrayData
{
    void associate(final Object p0, final boolean p1);
    
    boolean isVertexAttribute();
    
    int getIndex();
    
    String getName();
    
    void setName(final String p0);
    
    int getLocation();
    
    int setLocation(final int p0);
    
    int setLocation(final GL2ES2 p0, final int p1);
    
    int setLocation(final GL2ES2 p0, final int p1, final int p2);
    
    boolean isVBO();
    
    long getVBOOffset();
    
    int getVBOName();
    
    int getVBOUsage();
    
    int getVBOTarget();
    
    Buffer getBuffer();
    
    int getComponentCount();
    
    int getComponentType();
    
    int getComponentSizeInBytes();
    
    int getElementCount();
    
    int getSizeInBytes();
    
    boolean getNormalized();
    
    int getStride();
    
    String toString();
    
    void destroy(final GL p0);
}
