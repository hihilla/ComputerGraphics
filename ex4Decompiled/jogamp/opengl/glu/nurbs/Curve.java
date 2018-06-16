// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Curve
{
    private static final int MAXCOORDS = 5;
    private static final int MAXORDER = 24;
    public Curve next;
    private final Mapdesc mapdesc;
    private final boolean needsSampling;
    private final int cullval;
    private final int stride;
    private final int order;
    private final float[] range;
    public float stepsize;
    private float minstepsize;
    float[] spts;
    
    public Curve(final Quilt quilt, final float[] array, final float[] array2, final Curve next) {
        this.spts = new float[120];
        this.mapdesc = quilt.mapdesc;
        this.next = next;
        this.needsSampling = this.mapdesc.isRangeSampling();
        this.cullval = (this.mapdesc.isCulling() ? 1 : 0);
        this.order = quilt.qspec.get(0).order;
        this.stride = 5;
        final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(quilt.cpts.getArray(), 0);
        final CArrayOfQuiltspecs qspec = quilt.qspec;
        cArrayOfFloats.raisePointerBy(qspec.get().offset);
        cArrayOfFloats.raisePointerBy(qspec.get().index * qspec.get().order * qspec.get().stride);
        if (this.needsSampling) {
            this.mapdesc.xformSampling(cArrayOfFloats, qspec.get().order, qspec.get().stride, this.spts, this.stride);
        }
        if (this.cullval == 1) {}
        (this.range = new float[3])[0] = qspec.get().breakpoints[qspec.get().index];
        this.range[1] = qspec.get().breakpoints[qspec.get().index + 1];
        this.range[2] = this.range[1] - this.range[0];
        if (this.range[0] != array[0]) {}
        if (this.range[1] != array2[0]) {}
    }
    
    public int cullCheck() {
        if (this.cullval == 1) {}
        return 1;
    }
    
    public void getStepSize() {
        this.minstepsize = 0.0f;
        if (this.mapdesc.isConstantSampling()) {
            this.setstepsize(this.mapdesc.maxrate);
        }
        else if (this.mapdesc.isDomainSampling()) {
            this.setstepsize(this.mapdesc.maxrate * this.range[2]);
        }
        else {
            assert this.order <= 24;
            this.setstepsize(this.mapdesc.maxrate);
        }
    }
    
    private void setstepsize(final float n) {
        this.stepsize = ((n >= 1.0f) ? (this.range[2] / n) : this.range[2]);
        this.minstepsize = this.stepsize;
    }
    
    public void clamp() {
        if (this.stepsize < this.minstepsize) {
            this.stepsize = this.mapdesc.clampfactor * this.minstepsize;
        }
    }
    
    public boolean needsSamplingSubdivision() {
        return this.stepsize < this.minstepsize;
    }
}
