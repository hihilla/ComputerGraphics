// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

import java.util.Arrays;

public class Flist
{
    public int end;
    public int start;
    public float[] pts;
    private int npts;
    
    public void grow(final int npts) {
        if (this.npts < npts) {
            this.npts = npts;
            this.pts = new float[this.npts];
        }
        this.start = 0;
        this.end = 0;
    }
    
    public void filter() {
        Arrays.sort(this.pts);
        this.start = 0;
        int n = 0;
        for (int i = 1; i < this.end; ++i) {
            if (this.pts[i] == this.pts[i - n - 1]) {
                ++n;
            }
            this.pts[i - n] = this.pts[i];
        }
        this.end -= n;
    }
    
    public void taper(final float n, final float n2) {
        while (this.pts[this.start] != n) {
            ++this.start;
        }
        while (this.pts[this.end - 1] != n2) {
            --this.end;
        }
    }
    
    public void add(final float n) {
        this.pts[this.end++] = n;
    }
}
