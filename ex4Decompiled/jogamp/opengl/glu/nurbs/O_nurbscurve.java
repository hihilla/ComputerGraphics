// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class O_nurbscurve
{
    public Quilt bezier_curves;
    public int type;
    public boolean used;
    public O_curve owner;
    public O_nurbscurve next;
    
    public O_nurbscurve(final int type) {
        this.type = type;
        this.owner = null;
        this.next = null;
        this.used = false;
    }
}
