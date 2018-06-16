// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Patchlist
{
    public Pspec[] pspec;
    private Patch patch;
    
    public Patchlist(final Quilt quilt, final float[] array, final float[] array2) {
        this.patch = null;
        for (Quilt next = quilt; next != null; next = next.next) {
            this.patch = new Patch(next, array, array2, this.patch);
        }
        (this.pspec = new Pspec[2])[0] = new Pspec();
        this.pspec[0].range[0] = array[0];
        this.pspec[0].range[1] = array2[0];
        this.pspec[0].range[2] = array2[0] - array[0];
        this.pspec[1] = new Pspec();
        this.pspec[1].range[0] = array[1];
        this.pspec[1].range[1] = array2[1];
        this.pspec[1].range[2] = array2[1] - array[1];
    }
    
    public Patchlist(final Patchlist patchlist, final int n, final float n2) {
    }
    
    public int cullCheck() {
        return 0;
    }
    
    public void getstepsize() {
    }
    
    public boolean needsSamplingSubdivision() {
        return false;
    }
    
    public boolean needsSubdivision(final int n) {
        return false;
    }
    
    public boolean needsNonSamplingSubdivision() {
        return false;
    }
    
    public void bbox() {
    }
}
