// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Splinespec
{
    private final int dim;
    private Knotspec kspec;
    private CArrayOfFloats outcpts;
    
    public Splinespec(final int dim) {
        this.dim = dim;
    }
    
    public void kspecinit(final Knotvector knotvector) {
        this.kspec = new Knotspec();
        this.kspec.inkbegin = new CArrayOfFloats(knotvector.knotlist, 0);
        this.kspec.inkend = new CArrayOfFloats(knotvector.knotlist, knotvector.knotcount);
        this.kspec.prestride = knotvector.stride;
        this.kspec.order = knotvector.order;
        this.kspec.next = null;
    }
    
    public void kspecinit(final Knotvector knotvector, final Knotvector knotvector2) {
        this.kspec = new Knotspec();
        final Knotspec next = new Knotspec();
        this.kspec.inkbegin = new CArrayOfFloats(knotvector.knotlist, 0);
        this.kspec.inkend = new CArrayOfFloats(knotvector.knotlist, knotvector.knotcount);
        this.kspec.prestride = knotvector.stride;
        this.kspec.order = knotvector.order;
        this.kspec.next = next;
        next.inkbegin = new CArrayOfFloats(knotvector2.knotlist, 0);
        next.inkend = new CArrayOfFloats(knotvector2.knotlist, knotvector2.knotcount);
        next.prestride = knotvector2.stride;
        next.order = knotvector2.order;
        next.next = null;
    }
    
    public void select() {
        for (Knotspec knotspec = this.kspec; knotspec != null; knotspec = knotspec.next) {
            knotspec.preselect();
            knotspec.select();
        }
    }
    
    public void layout(final int ncoords) {
        int poststride = ncoords;
        for (Knotspec knotspec = this.kspec; knotspec != null; knotspec = knotspec.next) {
            knotspec.poststride = poststride;
            poststride *= (knotspec.bend.getPointer() - knotspec.bbegin.getPointer()) * knotspec.order + knotspec.postoffset;
            final Knotspec knotspec2 = knotspec;
            knotspec2.preoffset *= knotspec.prestride;
            final Knotspec knotspec3 = knotspec;
            knotspec3.prewidth *= knotspec.poststride;
            final Knotspec knotspec4 = knotspec;
            knotspec4.postwidth *= knotspec.poststride;
            final Knotspec knotspec5 = knotspec;
            knotspec5.postoffset *= knotspec.poststride;
            knotspec.ncoords = ncoords;
        }
        this.outcpts = new CArrayOfFloats(new float[poststride]);
    }
    
    public void setupquilt(final Quilt quilt) {
        final CArrayOfQuiltspecs cArrayOfQuiltspecs = new CArrayOfQuiltspecs(quilt.qspec);
        quilt.eqspec = new CArrayOfQuiltspecs(cArrayOfQuiltspecs.getArray(), this.dim);
        Knotspec knotspec = this.kspec;
        while (knotspec != null) {
            cArrayOfQuiltspecs.get().stride = knotspec.poststride;
            cArrayOfQuiltspecs.get().width = knotspec.bend.getPointer() - knotspec.bbegin.getPointer();
            cArrayOfQuiltspecs.get().order = knotspec.order;
            cArrayOfQuiltspecs.get().offset = knotspec.postoffset;
            cArrayOfQuiltspecs.get().index = 0;
            cArrayOfQuiltspecs.get().bdry[0] = ((knotspec.kleft.getPointer() == knotspec.kfirst.getPointer()) ? 1 : 0);
            cArrayOfQuiltspecs.get().bdry[1] = ((knotspec.kright.getPointer() == knotspec.klast.getPointer()) ? 1 : 0);
            cArrayOfQuiltspecs.get().breakpoints = new float[cArrayOfQuiltspecs.get().width + 1];
            final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(cArrayOfQuiltspecs.get().breakpoints, 0);
            final CArrayOfBreakpts cArrayOfBreakpts = new CArrayOfBreakpts(knotspec.bbegin);
            while (cArrayOfBreakpts.getPointer() <= knotspec.bend.getPointer()) {
                cArrayOfFloats.set(cArrayOfBreakpts.get().value);
                cArrayOfFloats.pp();
                cArrayOfBreakpts.pp();
            }
            knotspec = knotspec.next;
            if (knotspec != null) {
                cArrayOfQuiltspecs.pp();
            }
        }
        quilt.cpts = new CArrayOfFloats(this.outcpts);
        quilt.next = null;
    }
    
    public void copy(final CArrayOfFloats cArrayOfFloats) {
        this.kspec.copy(cArrayOfFloats, this.outcpts);
    }
    
    public void transform() {
        this.outcpts.setPointer(0);
        for (Knotspec knotspec = this.kspec; knotspec != null; knotspec = knotspec.next) {
            knotspec.istransformed = false;
        }
        for (Knotspec kspectotrans = this.kspec; kspectotrans != null; kspectotrans = kspectotrans.next) {
            for (Knotspec knotspec2 = this.kspec; knotspec2 != null; knotspec2 = knotspec2.next) {
                knotspec2.kspectotrans = kspectotrans;
            }
            this.kspec.transform(this.outcpts);
            kspectotrans.istransformed = true;
        }
    }
}
