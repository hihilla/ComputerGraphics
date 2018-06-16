// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class GLUface
{
    public GLUface next;
    public GLUface prev;
    public GLUhalfEdge anEdge;
    public Object data;
    public GLUface trail;
    public boolean marked;
    public boolean inside;
}
