// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

class StrokeFontRec
{
    public String name;
    public int num_chars;
    public StrokeCharRec[] ch;
    public float top;
    public float bottom;
    
    public StrokeFontRec(final String name, final int num_chars, final StrokeCharRec[] ch, final float top, final float bottom) {
        this.name = name;
        this.num_chars = num_chars;
        this.ch = ch;
        this.top = top;
        this.bottom = bottom;
    }
}
