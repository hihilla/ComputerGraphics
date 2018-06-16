// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Knotvector
{
    public static final float TOLERANCE = 1.0E-5f;
    private static final int MAXORDER = 24;
    int knotcount;
    int stride;
    int order;
    float[] knotlist;
    
    public Knotvector(final int n, final int n2, final int n3, final float[] array) {
        this.init(n, n2, n3, array);
    }
    
    public void init(final int knotcount, final int stride, final int order, final float[] array) {
        this.knotcount = knotcount;
        this.stride = stride;
        this.order = order;
        this.knotlist = new float[knotcount];
        for (int i = 0; i < knotcount; ++i) {
            this.knotlist[i] = array[i];
        }
    }
    
    public int validate() {
        int i = this.knotcount - 1;
        if (this.order < 1 || this.order > 24) {
            return 1;
        }
        if (this.knotcount < 2 * this.order) {
            return 2;
        }
        if (identical(this.knotlist[i - (this.order - 1)], this.knotlist[this.order - 1])) {
            return 3;
        }
        for (int j = 0; j < i; ++j) {
            if (this.knotlist[j] > this.knotlist[j + 1]) {
                return 4;
            }
        }
        int n = 1;
        while (i >= 1) {
            if (this.knotlist[i] - this.knotlist[i - 1] < 1.0E-5f) {
                ++n;
            }
            else {
                if (n > this.order) {
                    return 5;
                }
                n = 1;
            }
            --i;
        }
        if (n > this.order) {
            return 5;
        }
        return 0;
    }
    
    public void show(final String s) {
    }
    
    public static boolean identical(final float n, final float n2) {
        return n - n2 < 1.0E-5f;
    }
}
