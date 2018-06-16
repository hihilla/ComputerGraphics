// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.gl2.nurbs;

import jogamp.opengl.glu.nurbs.Backend;

public class GL2Backend extends Backend
{
    public GL2Backend() {
        this.curveEvaluator = new GL2CurveEvaluator();
        this.surfaceEvaluator = new GL2SurfaceEvaluator();
    }
}
