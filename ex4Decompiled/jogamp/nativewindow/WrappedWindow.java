// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.util.Insets;
import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.UpstreamWindowHookMutableSizePos;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.util.InsetsImmutable;
import com.jogamp.nativewindow.NativeWindow;

public class WrappedWindow extends WrappedSurface implements NativeWindow
{
    private final InsetsImmutable insets;
    private long windowHandle;
    
    public WrappedWindow(final AbstractGraphicsConfiguration abstractGraphicsConfiguration, final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b, final long n8) {
        this(abstractGraphicsConfiguration, n, new UpstreamWindowHookMutableSizePos(n2, n3, n4, n5, n6, n7), b, n8);
    }
    
    public WrappedWindow(final AbstractGraphicsConfiguration abstractGraphicsConfiguration, final long n, final UpstreamWindowHookMutableSizePos upstreamWindowHookMutableSizePos, final boolean b, final long windowHandle) {
        super(abstractGraphicsConfiguration, n, upstreamWindowHookMutableSizePos, b);
        this.insets = new Insets(0, 0, 0, 0);
        this.windowHandle = windowHandle;
    }
    
    @Override
    protected void invalidateImpl() {
        super.invalidateImpl();
        this.windowHandle = 0L;
    }
    
    @Override
    public void destroy() {
        this.destroyNotify();
    }
    
    @Override
    public final NativeSurface getNativeSurface() {
        return this;
    }
    
    @Override
    public NativeWindow getParent() {
        return null;
    }
    
    @Override
    public long getWindowHandle() {
        return this.windowHandle;
    }
    
    @Override
    public InsetsImmutable getInsets() {
        return this.insets;
    }
    
    @Override
    public int getX() {
        return ((UpstreamWindowHookMutableSizePos)this.getUpstreamSurfaceHook()).getX();
    }
    
    @Override
    public int getY() {
        return ((UpstreamWindowHookMutableSizePos)this.getUpstreamSurfaceHook()).getY();
    }
    
    @Override
    public int getWidth() {
        return ((UpstreamWindowHookMutableSizePos)this.getUpstreamSurfaceHook()).getWidth();
    }
    
    @Override
    public int getHeight() {
        return ((UpstreamWindowHookMutableSizePos)this.getUpstreamSurfaceHook()).getHeight();
    }
    
    @Override
    public Point getLocationOnScreen(final Point point) {
        if (null != point) {
            return point;
        }
        return new Point(0, 0);
    }
    
    @Override
    public boolean hasFocus() {
        return false;
    }
}
