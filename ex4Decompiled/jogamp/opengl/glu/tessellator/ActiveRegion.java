// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class ActiveRegion
{
    GLUhalfEdge eUp;
    DictNode nodeUp;
    int windingNumber;
    boolean inside;
    boolean sentinel;
    boolean dirty;
    boolean fixUpperEdge;
}
