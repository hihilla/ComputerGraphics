// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.gl2;

class StrokeRec
{
    public int num_coords;
    public CoordRec[] coord;
    
    public StrokeRec(final int num_coords, final CoordRec[] coord) {
        this.num_coords = num_coords;
        this.coord = coord;
    }
}
