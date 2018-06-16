// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class GLUhalfEdge
{
    public GLUhalfEdge next;
    public GLUhalfEdge Sym;
    public GLUhalfEdge Onext;
    public GLUhalfEdge Lnext;
    public GLUvertex Org;
    public GLUface Lface;
    public ActiveRegion activeRegion;
    public int winding;
    public boolean first;
    
    public GLUhalfEdge(final boolean first) {
        this.first = first;
    }
}
