// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public abstract class Backend
{
    public static final int N_MESHFILL = 0;
    public static final int N_MESHLINE = 1;
    public static final int N_MESHPOINT = 2;
    protected CurveEvaluator curveEvaluator;
    protected SurfaceEvaluator surfaceEvaluator;
    
    public void bgncurv() {
        this.curveEvaluator.bgnmap1f();
    }
    
    public void endcurv() {
        this.curveEvaluator.endmap1f();
    }
    
    public void curvpts(final int n, final CArrayOfFloats cArrayOfFloats, final int n2, final int n3, final float n4, final float n5) {
        this.curveEvaluator.map1f(n, n4, n5, n2, n3, cArrayOfFloats);
        this.curveEvaluator.enable(n);
    }
    
    public void curvgrid(final float n, final float n2, final int n3) {
        this.curveEvaluator.mapgrid1f(n3, n, n2);
    }
    
    public void curvmesh(final int n, final int n2) {
        this.curveEvaluator.mapmesh1f(0, n, n + n2);
    }
    
    public void bgnsurf(final int n, final int n2) {
        this.surfaceEvaluator.bgnmap2f();
        if (n > 0) {
            this.surfaceEvaluator.polymode(1);
        }
        else {
            this.surfaceEvaluator.polymode(0);
        }
    }
    
    public void endsurf() {
        this.surfaceEvaluator.endmap2f();
    }
    
    public void patch(final float n, final float n2, final float n3, final float n4) {
        this.surfaceEvaluator.domain2f(n, n2, n3, n4);
    }
    
    public void surfgrid(final float n, final float n2, final int n3, final float n4, final float n5, final int n6) {
        this.surfaceEvaluator.mapgrid2f(n3, n, n2, n6, n4, n5);
    }
    
    public void surfmesh(final int n, final int n2, final int n3, final int n4) {
        this.surfaceEvaluator.mapmesh2f(0, n, n + n3, n2, n2 + n4);
    }
    
    public void surfpts(final int n, final CArrayOfFloats cArrayOfFloats, final int n2, final int n3, final int n4, final int n5, final float n6, final float n7, final float n8, final float n9) {
        this.surfaceEvaluator.map2f(n, n6, n7, n2, n4, n8, n9, n3, n5, cArrayOfFloats);
        this.surfaceEvaluator.enable(n);
    }
}
