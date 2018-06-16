// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

public class Mat3x3
{
    private final Vec col1;
    private final Vec col2;
    private final Vec col3;
    
    public Mat3x3(final Vec col1, final Vec col2, final Vec col3) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
    }
    
    public Vec col1() {
        return new Vec(this.col1);
    }
    
    public Vec col2() {
        return new Vec(this.col2);
    }
    
    public Vec col3() {
        return new Vec(this.col3);
    }
    
    public Vec row1() {
        return new Vec(this.col1.x, this.col2.x, this.col3.x);
    }
    
    public Vec row2() {
        return new Vec(this.col1.y, this.col2.y, this.col3.y);
    }
    
    public Vec row3() {
        return new Vec(this.col1.z, this.col2.z, this.col3.z);
    }
    
    public Mat3x3 transpose() {
        return new Mat3x3(this.row1(), this.row2(), this.row3());
    }
    
    public Vec mult(final Vec v) {
        final float x = this.row1().dot(v);
        final float y = this.row2().dot(v);
        final float z = this.row3().dot(v);
        return new Vec(x, y, z);
    }
}
