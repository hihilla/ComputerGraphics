// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class TessMono
{
    private static final int MARKED_FOR_DELETION = Integer.MAX_VALUE;
    
    static boolean __gl_meshTessellateMonoRegion(final GLUface glUface, final boolean b) {
        GLUhalfEdge glUhalfEdge = glUface.anEdge;
        assert glUhalfEdge.Lnext != glUhalfEdge && glUhalfEdge.Lnext.Lnext != glUhalfEdge;
        while (Geom.VertLeq(glUhalfEdge.Sym.Org, glUhalfEdge.Org)) {
            glUhalfEdge = glUhalfEdge.Onext.Sym;
        }
        while (Geom.VertLeq(glUhalfEdge.Org, glUhalfEdge.Sym.Org)) {
            glUhalfEdge = glUhalfEdge.Lnext;
        }
        GLUhalfEdge glUhalfEdge2 = glUhalfEdge.Onext.Sym;
        int n = 0;
        while (glUhalfEdge.Lnext != glUhalfEdge2) {
            if (b && n == 0) {
                if (Geom.EdgeCos(glUhalfEdge2.Lnext.Org, glUhalfEdge2.Org, glUhalfEdge2.Lnext.Lnext.Org) <= -0.99999) {
                    do {
                        glUhalfEdge2 = glUhalfEdge2.Onext.Sym;
                        n = 1;
                        if (glUhalfEdge.Lnext != glUhalfEdge2) {
                            continue;
                        }
                        break;
                    } while (Geom.EdgeCos(glUhalfEdge2.Lnext.Org, glUhalfEdge2.Org, glUhalfEdge2.Lnext.Lnext.Org) <= -0.99999);
                }
                else if (Geom.EdgeCos(glUhalfEdge.Onext.Sym.Org, glUhalfEdge.Org, glUhalfEdge.Onext.Sym.Onext.Sym.Org) <= -0.99999) {
                    do {
                        glUhalfEdge = glUhalfEdge.Lnext;
                        n = 1;
                    } while (glUhalfEdge.Lnext != glUhalfEdge2 && Geom.EdgeCos(glUhalfEdge.Onext.Sym.Org, glUhalfEdge.Org, glUhalfEdge.Onext.Sym.Onext.Sym.Org) <= -0.99999);
                }
                if (glUhalfEdge.Lnext == glUhalfEdge2) {
                    break;
                }
            }
            if (Geom.VertLeq(glUhalfEdge.Sym.Org, glUhalfEdge2.Org)) {
                while (glUhalfEdge2.Lnext != glUhalfEdge && (Geom.EdgeGoesLeft(glUhalfEdge2.Lnext) || Geom.EdgeSign(glUhalfEdge2.Org, glUhalfEdge2.Sym.Org, glUhalfEdge2.Lnext.Sym.Org) <= 0.0)) {
                    final GLUhalfEdge _gl_meshConnect = Mesh.__gl_meshConnect(glUhalfEdge2.Lnext, glUhalfEdge2);
                    n = 0;
                    if (_gl_meshConnect == null) {
                        return false;
                    }
                    glUhalfEdge2 = _gl_meshConnect.Sym;
                }
                glUhalfEdge2 = glUhalfEdge2.Onext.Sym;
            }
            else {
                while (glUhalfEdge2.Lnext != glUhalfEdge && (Geom.EdgeGoesRight(glUhalfEdge.Onext.Sym) || Geom.EdgeSign(glUhalfEdge.Sym.Org, glUhalfEdge.Org, glUhalfEdge.Onext.Sym.Org) >= 0.0)) {
                    final GLUhalfEdge _gl_meshConnect2 = Mesh.__gl_meshConnect(glUhalfEdge, glUhalfEdge.Onext.Sym);
                    n = 0;
                    if (_gl_meshConnect2 == null) {
                        return false;
                    }
                    glUhalfEdge = _gl_meshConnect2.Sym;
                }
                glUhalfEdge = glUhalfEdge.Lnext;
            }
        }
        assert glUhalfEdge2.Lnext != glUhalfEdge;
        while (glUhalfEdge2.Lnext.Lnext != glUhalfEdge) {
            final GLUhalfEdge _gl_meshConnect3 = Mesh.__gl_meshConnect(glUhalfEdge2.Lnext, glUhalfEdge2);
            if (_gl_meshConnect3 == null) {
                return false;
            }
            glUhalfEdge2 = _gl_meshConnect3.Sym;
        }
        return true;
    }
    
    public static boolean __gl_meshTessellateInterior(final GLUmesh glUmesh, final boolean b) {
        GLUface next2;
        for (GLUface next = glUmesh.fHead.next; next != glUmesh.fHead; next = next2) {
            next2 = next.next;
            if (next.inside && !__gl_meshTessellateMonoRegion(next, b)) {
                return false;
            }
        }
        return true;
    }
    
    public static void __gl_meshDiscardExterior(final GLUmesh glUmesh) {
        GLUface next2;
        for (GLUface next = glUmesh.fHead.next; next != glUmesh.fHead; next = next2) {
            next2 = next.next;
            if (!next.inside) {
                Mesh.__gl_meshZapFace(next);
            }
        }
    }
    
    public static boolean __gl_meshSetWindingNumber(final GLUmesh glUmesh, final int n, final boolean b) {
        GLUhalfEdge next2;
        for (GLUhalfEdge next = glUmesh.eHead.next; next != glUmesh.eHead; next = next2) {
            next2 = next.next;
            if (next.Sym.Lface.inside != next.Lface.inside) {
                next.winding = (next.Lface.inside ? n : (-n));
            }
            else if (!b) {
                next.winding = 0;
            }
            else if (!Mesh.__gl_meshDelete(next)) {
                return false;
            }
        }
        return true;
    }
}
