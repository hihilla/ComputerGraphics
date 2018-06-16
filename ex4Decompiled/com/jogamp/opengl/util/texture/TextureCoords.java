// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

public class TextureCoords
{
    private final float left;
    private final float bottom;
    private final float right;
    private final float top;
    
    public TextureCoords(final float left, final float bottom, final float right, final float top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }
    
    public float[] getST_LB_RB_LT_RT(final float[] array, final int n, final float n2, final float n3) {
        array[0 + n] = this.left * n2;
        array[1 + n] = this.bottom * n3;
        array[2 + n] = this.right * n2;
        array[3 + n] = this.bottom * n3;
        array[4 + n] = this.left * n2;
        array[5 + n] = this.top * n3;
        array[6 + n] = this.right * n2;
        array[7 + n] = this.top * n3;
        return array;
    }
    
    public float left() {
        return this.left;
    }
    
    public float right() {
        return this.right;
    }
    
    public float bottom() {
        return this.bottom;
    }
    
    public float top() {
        return this.top;
    }
    
    @Override
    public String toString() {
        return "TexCoord[h: " + this.left + " - " + this.right + ", v: " + this.bottom + " - " + this.top + "]";
    }
}
