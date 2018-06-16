// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class GLUmesh
{
    GLUvertex vHead;
    GLUface fHead;
    GLUhalfEdge eHead;
    GLUhalfEdge eHeadSym;
    
    GLUmesh() {
        this.vHead = new GLUvertex();
        this.fHead = new GLUface();
        this.eHead = new GLUhalfEdge(true);
        this.eHeadSym = new GLUhalfEdge(false);
    }
}
