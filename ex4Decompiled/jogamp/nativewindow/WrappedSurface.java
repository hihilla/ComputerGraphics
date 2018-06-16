// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.UpstreamSurfaceHook;
import com.jogamp.nativewindow.UpstreamSurfaceHookMutableSize;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.ScalableSurface;

public class WrappedSurface extends ProxySurfaceImpl implements ScalableSurface
{
    private final float[] hasPixelScale;
    private long surfaceHandle;
    
    public WrappedSurface(final AbstractGraphicsConfiguration abstractGraphicsConfiguration, final long surfaceHandle, final int n, final int n2, final boolean b) {
        super(abstractGraphicsConfiguration, new UpstreamSurfaceHookMutableSize(n, n2), b);
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.surfaceHandle = surfaceHandle;
    }
    
    public WrappedSurface(final AbstractGraphicsConfiguration abstractGraphicsConfiguration, final long surfaceHandle, final UpstreamSurfaceHook upstreamSurfaceHook, final boolean b) {
        super(abstractGraphicsConfiguration, upstreamSurfaceHook, b);
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.surfaceHandle = surfaceHandle;
    }
    
    @Override
    protected void invalidateImpl() {
        this.surfaceHandle = 0L;
        this.hasPixelScale[0] = 1.0f;
        this.hasPixelScale[1] = 1.0f;
    }
    
    @Override
    public final long getSurfaceHandle() {
        return this.surfaceHandle;
    }
    
    @Override
    public final void setSurfaceHandle(final long surfaceHandle) {
        this.surfaceHandle = surfaceHandle;
    }
    
    @Override
    protected final int lockSurfaceImpl() {
        return 3;
    }
    
    @Override
    protected final void unlockSurfaceImpl() {
    }
    
    @Override
    public final int[] convertToWindowUnits(final int[] array) {
        return SurfaceScaleUtils.scaleInv(array, array, this.hasPixelScale);
    }
    
    @Override
    public final int[] convertToPixelUnits(final int[] array) {
        return SurfaceScaleUtils.scale(array, array, this.hasPixelScale);
    }
    
    @Override
    public final boolean setSurfaceScale(final float[] array) {
        final boolean b = this.hasPixelScale[0] != array[0] || this.hasPixelScale[1] != array[1];
        System.arraycopy(array, 0, this.hasPixelScale, 0, 2);
        return b;
    }
    
    @Override
    public final float[] getRequestedSurfaceScale(final float[] array) {
        System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getCurrentSurfaceScale(final float[] array) {
        System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public float[] getMinimumSurfaceScale(final float[] array) {
        System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getMaximumSurfaceScale(final float[] array) {
        System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
        return array;
    }
}
