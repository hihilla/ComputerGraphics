// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

public class PngjExceptionInternal extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public PngjExceptionInternal(final String s, final Throwable t) {
        super(s, t);
    }
    
    public PngjExceptionInternal(final String s) {
        super(s);
    }
    
    public PngjExceptionInternal(final Throwable t) {
        super(t);
    }
}
