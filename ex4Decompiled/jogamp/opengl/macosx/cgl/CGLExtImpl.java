// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

public class CGLExtImpl implements CGLExt
{
    private MacOSXCGLContext _context;
    
    public CGLExtImpl(final MacOSXCGLContext context) {
        this._context = context;
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this._context.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this._context.isExtensionAvailable(s);
    }
}
