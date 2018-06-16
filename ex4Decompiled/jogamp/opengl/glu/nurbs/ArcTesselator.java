// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class ArcTesselator
{
    public void bezier(final Arc arc, final float n, final float n2, final float n3, final float n4) {
        final TrimVertex[] array = { new TrimVertex(), new TrimVertex() };
        arc.pwlArc = new PwlArc(2, array);
        array[0].param[0] = n;
        array[0].param[1] = n2;
        array[1].param[0] = n3;
        array[1].param[1] = n4;
        arc.setbezier();
    }
    
    public void pwl_right(final Arc arc, final float n, final float n2, final float n3, final float n4) {
    }
    
    public void pwl_left(final Arc arc, final float n, final float n2, final float n3, final float n4) {
    }
}
