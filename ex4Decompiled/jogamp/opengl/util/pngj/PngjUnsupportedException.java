// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class PngjUnsupportedException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public PngjUnsupportedException() {
    }
    
    public PngjUnsupportedException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public PngjUnsupportedException(final String s) {
        super(s);
    }
    
    public PngjUnsupportedException(final Throwable t) {
        super(t);
    }
}
