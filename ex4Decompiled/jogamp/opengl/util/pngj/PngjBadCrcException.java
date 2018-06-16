// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class PngjBadCrcException extends PngjInputException
{
    private static final long serialVersionUID = 1L;
    
    public PngjBadCrcException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public PngjBadCrcException(final String s) {
        super(s);
    }
    
    public PngjBadCrcException(final Throwable t) {
        super(t);
    }
}
