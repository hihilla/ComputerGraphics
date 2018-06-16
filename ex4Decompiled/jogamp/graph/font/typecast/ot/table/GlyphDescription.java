// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

public interface GlyphDescription
{
    int getGlyphIndex();
    
    int getEndPtOfContours(final int p0);
    
    byte getFlags(final int p0);
    
    short getXCoordinate(final int p0);
    
    short getYCoordinate(final int p0);
    
    short getXMaximum();
    
    short getXMinimum();
    
    short getYMaximum();
    
    short getYMinimum();
    
    boolean isComposite();
    
    int getPointCount();
    
    int getContourCount();
}
