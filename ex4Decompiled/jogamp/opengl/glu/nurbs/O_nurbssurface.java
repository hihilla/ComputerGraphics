// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class O_nurbssurface
{
    public Quilt bezier_patches;
    public boolean used;
    public O_surface owner;
    public O_nurbssurface next;
    private final int type;
    
    public O_nurbssurface(final int type) {
        this.type = type;
        this.owner = null;
        this.next = null;
        this.used = false;
    }
}
