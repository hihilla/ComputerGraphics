// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Mapdesc
{
    private static final int MAXCOORDS = 5;
    public Mapdesc next;
    public int isrational;
    public int ncoords;
    private final int type;
    private final int hcoords;
    private final int inhcoords;
    private final int mask;
    private float pixel_tolerance;
    private float error_tolerance;
    private float bbox_subdividing;
    private float culling_method;
    private float sampling_method;
    float clampfactor;
    private float minsavings;
    private float s_steps;
    private float t_steps;
    float maxrate;
    private float maxsrate;
    private float maxtrate;
    private final float[][] bmat;
    private final float[][] smat;
    private final float[][] cmat;
    private final float[] bboxsize;
    
    public Mapdesc(final int type, final int isrational, final int ncoords) {
        this.type = type;
        this.isrational = isrational;
        this.ncoords = ncoords;
        this.hcoords = ncoords + ((this.isrational <= 0) ? 1 : 0);
        this.inhcoords = ncoords - ((this.isrational > 0) ? 1 : 0);
        this.mask = (1 << this.inhcoords * 2) - 1;
        this.next = null;
        assert this.hcoords <= 5;
        assert this.inhcoords >= 1;
        this.pixel_tolerance = 1.0f;
        this.error_tolerance = 1.0f;
        this.bbox_subdividing = 0.0f;
        this.culling_method = 0.0f;
        this.sampling_method = 0.0f;
        this.clampfactor = 0.0f;
        this.minsavings = 0.0f;
        this.s_steps = 0.0f;
        this.t_steps = 0.0f;
        this.maxrate = ((this.s_steps < 0.0f) ? 0.0f : this.s_steps);
        this.maxsrate = ((this.s_steps < 0.0f) ? 0.0f : this.s_steps);
        this.maxtrate = ((this.t_steps < 0.0f) ? 0.0f : this.t_steps);
        this.bmat = new float[5][5];
        this.cmat = new float[5][5];
        this.smat = new float[5][5];
        this.identify(this.bmat);
        this.identify(this.cmat);
        this.identify(this.smat);
        this.bboxsize = new float[5];
        for (int i = 0; i < this.inhcoords; ++i) {
            this.bboxsize[i] = 1.0f;
        }
    }
    
    private void identify(final float[][] array) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                array[i][j] = 0.0f;
            }
        }
        for (int k = 0; k < 5; ++k) {
            array[k][k] = 1.0f;
        }
    }
    
    public boolean isProperty(final int n) {
        boolean b = false;
        switch (n) {
            case 1:
            case 2:
            case 6:
            case 7:
            case 10:
            case 13:
            case 14:
            case 17:
            case 20: {
                b = true;
                break;
            }
            default: {
                b = false;
                break;
            }
        }
        return b;
    }
    
    public int getNCoords() {
        return this.ncoords;
    }
    
    public int getType() {
        return this.type;
    }
    
    public boolean isRangeSampling() {
        return this.isParametricDistanceSampling() || this.isPathLengthSampling() || this.isSurfaceAreaSampling() || this.isObjectSpaceParaSampling() || this.isObjectSpacePathSampling();
    }
    
    private boolean isObjectSpacePathSampling() {
        return this.sampling_method == 9.0f;
    }
    
    private boolean isObjectSpaceParaSampling() {
        return this.sampling_method == 8.0f;
    }
    
    private boolean isSurfaceAreaSampling() {
        return this.sampling_method == 7.0f;
    }
    
    boolean isPathLengthSampling() {
        return this.sampling_method == 6.0f;
    }
    
    boolean isParametricDistanceSampling() {
        return this.sampling_method == 5.0f;
    }
    
    public boolean isCulling() {
        return this.culling_method != 0.0f;
    }
    
    public boolean isConstantSampling() {
        return this.sampling_method == 3.0f;
    }
    
    public boolean isDomainSampling() {
        return this.sampling_method == 2.0f;
    }
    
    public float getProperty(final int n) {
        return 0.0f;
    }
    
    public void setProperty(final int n, float n2) {
        switch (n) {
            case 1: {
                this.pixel_tolerance = n2;
                break;
            }
            case 20: {
                this.error_tolerance = n2;
                break;
            }
            case 2: {
                this.culling_method = n2;
                break;
            }
            case 17: {
                if (n2 <= 0.0f) {
                    n2 = 0.0f;
                }
                this.bbox_subdividing = n2;
                break;
            }
            case 6: {
                if (n2 < 0.0f) {
                    n2 = 0.0f;
                }
                this.s_steps = n2;
                this.maxrate = n2;
                this.maxsrate = n2;
                break;
            }
            case 7: {
                if (n2 < 0.0f) {
                    n2 = 0.0f;
                }
                this.t_steps = n2;
                this.maxtrate = n2;
                break;
            }
            case 10: {
                this.sampling_method = n2;
                break;
            }
            case 13: {
                if (n2 < 0.0f) {
                    n2 = 0.0f;
                }
                this.clampfactor = n2;
                break;
            }
            case 14: {
                if (n2 <= 0.0f) {
                    n2 = 0.0f;
                }
                this.minsavings = n2;
                break;
            }
        }
    }
    
    public void xformSampling(final CArrayOfFloats cArrayOfFloats, final int n, final int n2, final float[] array, final int n3) {
        this.xFormMat(this.smat, cArrayOfFloats, n, n2, array, n3);
    }
    
    private void xFormMat(final float[][] array, final CArrayOfFloats cArrayOfFloats, final int n, final int n2, final float[] array2, final int n3) {
        if (this.isrational > 0) {}
    }
}
