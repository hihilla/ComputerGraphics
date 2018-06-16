// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.gl2.nurbs;

import jogamp.opengl.glu.nurbs.CArrayOfFloats;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.GL2;
import jogamp.opengl.glu.nurbs.SurfaceEvaluator;

class GL2SurfaceEvaluator implements SurfaceEvaluator
{
    private final GL2 gl;
    private boolean output_triangles;
    private int poradi;
    
    public GL2SurfaceEvaluator() {
        this.gl = GLUgl2.getCurrentGL2();
    }
    
    @Override
    public void bgnmap2f() {
        if (!this.output_triangles) {
            this.gl.glPushAttrib(65536);
        }
    }
    
    @Override
    public void polymode(final int n) {
        if (!this.output_triangles) {
            switch (n) {
                default: {
                    this.gl.glPolygonMode(1032, 6914);
                    break;
                }
                case 1: {
                    this.gl.glPolygonMode(1032, 6913);
                    break;
                }
                case 2: {
                    this.gl.glPolygonMode(1032, 6912);
                    break;
                }
            }
        }
    }
    
    @Override
    public void endmap2f() {
        if (!this.output_triangles) {
            this.gl.glPopAttrib();
        }
    }
    
    @Override
    public void domain2f(final float n, final float n2, final float n3, final float n4) {
    }
    
    @Override
    public void mapgrid2f(final int n, final float n2, final float n3, final int n4, final float n5, final float n6) {
        if (!this.output_triangles) {
            this.gl.glMapGrid2d(n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void mapmesh2f(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (!this.output_triangles) {
            switch (n) {
                case 0: {
                    this.gl.glEvalMesh2(6914, n2, n3, n4, n5);
                    break;
                }
                case 1: {
                    this.gl.glEvalMesh2(6913, n2, n3, n4, n5);
                    break;
                }
                case 2: {
                    this.gl.glEvalMesh2(6912, n2, n3, n4, n5);
                    break;
                }
            }
        }
    }
    
    @Override
    public void map2f(final int n, final float n2, final float n3, final int n4, final int n5, final float n6, final float n7, final int n8, final int n9, final CArrayOfFloats cArrayOfFloats) {
        if (!this.output_triangles) {
            this.gl.glMap2f(n, n2, n3, n4, n5, n6, n7, n8, n9, cArrayOfFloats.getArray(), cArrayOfFloats.getPointer());
        }
    }
    
    @Override
    public void enable(final int n) {
        this.gl.glEnable(n);
    }
}
