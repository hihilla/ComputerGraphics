// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

class BitmapFontRec
{
    public String name;
    public int num_chars;
    public int first;
    public BitmapCharRec[] ch;
    
    public BitmapFontRec(final String name, final int num_chars, final int first, final BitmapCharRec[] ch) {
        this.name = name;
        this.num_chars = num_chars;
        this.first = first;
        this.ch = ch;
    }
}
