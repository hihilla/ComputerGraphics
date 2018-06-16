// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Curvelist
{
    private Curve curve;
    float[] range;
    public float stepsize;
    private boolean needsSubdivision;
    
    public Curvelist(final Quilt quilt, final float[] array, final float[] array2) {
        this.curve = null;
        this.range = new float[3];
        for (Quilt next = quilt; next != null; next = next.next) {
            this.curve = new Curve(next, array, array2, this.curve);
        }
        this.range[0] = array[0];
        this.range[1] = array2[0];
        this.range[2] = this.range[1] - this.range[0];
    }
    
    public void getstepsize() {
        this.stepsize = this.range[2];
        Curve curve;
        for (curve = this.curve; curve != null; curve = curve.next) {
            curve.getStepSize();
            curve.clamp();
            this.stepsize = ((curve.stepsize < this.stepsize) ? curve.stepsize : this.stepsize);
            if (curve.needsSamplingSubdivision()) {
                break;
            }
        }
        this.needsSubdivision = (curve != null);
    }
    
    public boolean needsSamplingSubdivision() {
        return this.needsSubdivision;
    }
    
    public int cullCheck() {
        for (Curve curve = this.curve; curve != null; curve = curve.next) {
            if (curve.cullCheck() == 0) {
                return 0;
            }
        }
        return 1;
    }
}
