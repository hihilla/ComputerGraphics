// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public interface SurfaceEvaluator
{
    void bgnmap2f();
    
    void polymode(final int p0);
    
    void endmap2f();
    
    void domain2f(final float p0, final float p1, final float p2, final float p3);
    
    void mapgrid2f(final int p0, final float p1, final float p2, final int p3, final float p4, final float p5);
    
    void mapmesh2f(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void map2f(final int p0, final float p1, final float p2, final int p3, final int p4, final float p5, final float p6, final int p7, final int p8, final CArrayOfFloats p9);
    
    void enable(final int p0);
}
