// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public interface CurveEvaluator
{
    void bgnmap1f();
    
    void endmap1f();
    
    void map1f(final int p0, final float p1, final float p2, final int p3, final int p4, final CArrayOfFloats p5);
    
    void enable(final int p0);
    
    void mapgrid1f(final int p0, final float p1, final float p2);
    
    void mapmesh1f(final int p0, final int p1, final int p2);
}
