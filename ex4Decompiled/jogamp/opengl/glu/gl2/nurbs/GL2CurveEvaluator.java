// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.gl2.nurbs;

import jogamp.opengl.glu.nurbs.CArrayOfFloats;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.GL2;
import jogamp.opengl.glu.nurbs.CurveEvaluator;

class GL2CurveEvaluator implements CurveEvaluator
{
    private boolean output_triangles;
    private final GL2 gl;
    private int vertex_flag;
    private int normal_flag;
    private int color_flag;
    private int texcoord_flag;
    private int poradi;
    
    public GL2CurveEvaluator() {
        this.gl = GLUgl2.getCurrentGL2();
    }
    
    @Override
    public void bgnmap1f() {
        if (this.output_triangles) {
            this.vertex_flag = 0;
            this.normal_flag = 0;
            this.color_flag = 0;
            this.texcoord_flag = 0;
        }
        else {
            this.gl.glPushAttrib(65536);
        }
    }
    
    @Override
    public void endmap1f() {
        if (!this.output_triangles) {
            this.gl.glPopAttrib();
        }
    }
    
    @Override
    public void map1f(final int n, final float n2, final float n3, final int n4, final int n5, final CArrayOfFloats cArrayOfFloats) {
        if (!this.output_triangles) {
            this.gl.glMap1f(n, n2, n3, n4, n5, cArrayOfFloats.getArray(), cArrayOfFloats.getPointer());
        }
    }
    
    @Override
    public void enable(final int n) {
        this.gl.glEnable(n);
    }
    
    @Override
    public void mapgrid1f(final int n, final float n2, final float n3) {
        if (!this.output_triangles) {
            this.gl.glMapGrid1f(n, n2, n3);
        }
    }
    
    @Override
    public void mapmesh1f(final int n, final int n2, final int n3) {
        if (!this.output_triangles) {
            switch (n) {
                case 0:
                case 1: {
                    this.gl.glEvalMesh1(6913, n2, n3);
                    break;
                }
                case 2: {
                    this.gl.glEvalMesh1(6912, n2, n3);
                    break;
                }
            }
        }
    }
}
