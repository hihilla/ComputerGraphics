// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Quiltspec
{
    public int stride;
    public int width;
    public int order;
    public int offset;
    public int index;
    public int[] bdry;
    public float[] breakpoints;
    
    public Quiltspec() {
        this.bdry = new int[2];
    }
}
