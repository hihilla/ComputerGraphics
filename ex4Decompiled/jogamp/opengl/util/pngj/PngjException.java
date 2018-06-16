// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class PngjException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public PngjException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public PngjException(final String s) {
        super(s);
    }
    
    public PngjException(final Throwable t) {
        super(t);
    }
}
