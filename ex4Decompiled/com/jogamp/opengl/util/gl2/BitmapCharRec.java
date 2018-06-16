// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

class BitmapCharRec
{
    public int width;
    public int height;
    public float xorig;
    public float yorig;
    public float advance;
    public byte[] bitmap;
    
    public BitmapCharRec(final int width, final int height, final float xorig, final float yorig, final float advance, final byte[] bitmap) {
        this.width = width;
        this.height = height;
        this.xorig = xorig;
        this.yorig = yorig;
        this.advance = advance;
        this.bitmap = bitmap;
    }
}
