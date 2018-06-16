// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

class StrokeCharRec
{
    public int num_strokes;
    public StrokeRec[] stroke;
    public float center;
    public float right;
    
    public StrokeCharRec(final int num_strokes, final StrokeRec[] stroke, final float center, final float right) {
        this.num_strokes = num_strokes;
        this.stroke = stroke;
        this.center = center;
        this.right = right;
    }
}
