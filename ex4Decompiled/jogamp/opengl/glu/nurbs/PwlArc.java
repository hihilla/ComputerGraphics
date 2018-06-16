// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class PwlArc
{
    private final int npts;
    public TrimVertex[] pts;
    private final int type;
    
    public PwlArc(final int npts, final TrimVertex[] pts) {
        this.npts = npts;
        this.pts = pts;
        this.type = 8;
    }
}
