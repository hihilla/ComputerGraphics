// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Quilt
{
    private static final int MAXDIM = 2;
    Mapdesc mapdesc;
    public CArrayOfQuiltspecs qspec;
    public CArrayOfQuiltspecs eqspec;
    public CArrayOfFloats cpts;
    public Quilt next;
    
    public Quilt(final Mapdesc mapdesc) {
        this.mapdesc = mapdesc;
        final Quiltspec[] array = new Quiltspec[2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new Quiltspec();
        }
        this.qspec = new CArrayOfQuiltspecs(array);
    }
    
    public void toBezier(final Knotvector knotvector, final Knotvector knotvector2, final CArrayOfFloats cArrayOfFloats, final int n) {
        final Splinespec splinespec = new Splinespec(2);
        splinespec.kspecinit(knotvector, knotvector2);
        splinespec.select();
        splinespec.layout(n);
        splinespec.setupquilt(this);
        splinespec.copy(cArrayOfFloats);
        splinespec.transform();
    }
    
    public void toBezier(final Knotvector knotvector, final CArrayOfFloats cArrayOfFloats, final int n) {
        final Splinespec splinespec = new Splinespec(1);
        splinespec.kspecinit(knotvector);
        splinespec.select();
        splinespec.layout(n);
        splinespec.setupquilt(this);
        splinespec.copy(cArrayOfFloats);
        splinespec.transform();
    }
    
    public void downloadAll(final float[] array, final float[] array2, final Backend backend) {
        for (Quilt next = this; next != null; next = next.next) {
            next.select(array, array2);
            next.download(backend);
        }
    }
    
    private void download(final Backend backend) {
        if (this.getDimension() == 2) {
            final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(this.cpts);
            cArrayOfFloats.raisePointerBy(this.qspec.get(0).offset);
            cArrayOfFloats.raisePointerBy(this.qspec.get(1).offset);
            cArrayOfFloats.raisePointerBy(this.qspec.get(0).index * this.qspec.get(0).order * this.qspec.get(0).stride);
            cArrayOfFloats.raisePointerBy(this.qspec.get(1).index * this.qspec.get(1).order * this.qspec.get(1).stride);
            backend.surfpts(this.mapdesc.getType(), cArrayOfFloats, this.qspec.get(0).stride, this.qspec.get(1).stride, this.qspec.get(0).order, this.qspec.get(1).order, this.qspec.get(0).breakpoints[this.qspec.get(0).index], this.qspec.get(0).breakpoints[this.qspec.get(0).index + 1], this.qspec.get(1).breakpoints[this.qspec.get(1).index], this.qspec.get(1).breakpoints[this.qspec.get(1).index + 1]);
        }
        else {
            final CArrayOfFloats cArrayOfFloats2 = new CArrayOfFloats(this.cpts.getArray(), 0);
            cArrayOfFloats2.raisePointerBy(this.qspec.get(0).offset);
            cArrayOfFloats2.raisePointerBy(this.qspec.get(0).index * this.qspec.get(0).order * this.qspec.get(0).stride);
            backend.curvpts(this.mapdesc.getType(), cArrayOfFloats2, this.qspec.get(0).stride, this.qspec.get(0).order, this.qspec.get(0).breakpoints[this.qspec.get(0).index], this.qspec.get(0).breakpoints[this.qspec.get(0).index + 1]);
        }
    }
    
    private int getDimension() {
        return this.eqspec.getPointer() - this.qspec.getPointer();
    }
    
    private void select(final float[] array, final float[] array2) {
        for (int n = this.eqspec.getPointer() - this.qspec.getPointer(), i = 0; i < n; ++i) {
            int index;
            for (index = this.qspec.get(i).width - 1; index >= 0 && (this.qspec.get(i).breakpoints[index] > array[i] || array2[i] > this.qspec.get(i).breakpoints[index + 1]); --index) {}
            assert index != -1;
            this.qspec.get(i).index = index;
        }
    }
    
    public void getRange(final float[] array, final float[] array2, final Flist flist) {
        this.getRange(array, array2, 0, flist);
    }
    
    private void getRange(final float[] array, final float[] array2, final int n, final Flist flist) {
        array[n] = this.qspec.get(n).breakpoints[0];
        array2[n] = this.qspec.get(n).breakpoints[this.qspec.get(n).width];
        int n2 = 0;
        for (Quilt next = this; next != null; next = next.next) {
            if (next.qspec.get(n).breakpoints[0] > array[n]) {
                array[n] = next.qspec.get(n).breakpoints[0];
            }
            if (next.qspec.get(n).breakpoints[next.qspec.get(n).width] < array2[n]) {
                array2[n] = next.qspec.get(n).breakpoints[next.qspec.get(n).width];
            }
            n2 += next.qspec.get(n).width + 1;
        }
        flist.grow(n2);
        for (Quilt next2 = this; next2 != null; next2 = next2.next) {
            for (int i = 0; i <= next2.qspec.get(n).width; ++i) {
                flist.add(next2.qspec.get(n).breakpoints[i]);
            }
        }
        flist.filter();
        flist.taper(array[n], array2[n]);
    }
    
    public int isCulled() {
        if (this.mapdesc.isCulling()) {
            return 0;
        }
        return 1;
    }
    
    public void getRange(final float[] array, final float[] array2, final Flist flist, final Flist flist2) {
        this.getRange(array, array2, 0, flist);
        this.getRange(array, array2, 1, flist2);
    }
    
    public void findRates(final Flist flist, final Flist flist2, final float[] array) {
    }
}
