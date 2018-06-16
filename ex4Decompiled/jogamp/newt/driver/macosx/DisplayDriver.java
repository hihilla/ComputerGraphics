// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.macosx;

import java.net.URLConnection;
import com.jogamp.common.util.IOUtil;
import com.jogamp.newt.NewtFactory;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.newt.NEWTJNILibLoader;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.macosx.MacOSXGraphicsDevice;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.util.PNGPixelRect;
import jogamp.newt.DisplayImpl;

public class DisplayDriver extends DisplayImpl
{
    private static final PNGPixelRect defaultIconData;
    
    public static void initSingleton() {
    }
    
    @Override
    public PixelFormat getNativePointerIconPixelFormat() {
        return PixelFormat.RGBA8888;
    }
    
    @Override
    protected void dispatchMessagesNative() {
    }
    
    @Override
    protected void createNativeImpl() {
        this.aDevice = new MacOSXGraphicsDevice(0);
    }
    
    @Override
    protected void closeNativeImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        abstractGraphicsDevice.close();
    }
    
    @Override
    public final boolean getNativePointerIconForceDirectNIO() {
        return true;
    }
    
    @Override
    protected final long createPointerIconImpl(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        return createPointerIcon0(byteBuffer, Buffers.getDirectBufferByteOffset(byteBuffer), true, n, n2, n3, n4);
    }
    
    @Override
    protected final void destroyPointerIconImpl(final long n, final long n2) {
        destroyPointerIcon0(n2);
    }
    
    public static void runNSApplication() {
        runNSApplication0();
    }
    
    public static void stopNSApplication() {
        stopNSApplication0();
    }
    
    private static native boolean initNSApplication0();
    
    private static native void runNSApplication0();
    
    private static native void stopNSApplication0();
    
    static native void setAppIcon0(final Object p0, final int p1, final boolean p2, final int p3, final int p4);
    
    private static native long createPointerIcon0(final Object p0, final int p1, final boolean p2, final int p3, final int p4, final int p5, final int p6);
    
    private static native long destroyPointerIcon0(final long p0);
    
    static {
        NEWTJNILibLoader.loadNEWT();
        if (!initNSApplication0()) {
            throw new NativeWindowException("Failed to initialize native Application hook");
        }
        if (!WindowDriver.initIDs0()) {
            throw new NativeWindowException("Failed to initialize jmethodIDs");
        }
        PNGPixelRect read = null;
        if (DisplayImpl.isPNGUtilAvailable()) {
            try {
                final IOUtil.ClassResources windowIcons = NewtFactory.getWindowIcons();
                final URLConnection resolve = windowIcons.resolve(windowIcons.resourceCount() - 1);
                if (null != resolve) {
                    read = PNGPixelRect.read(resolve.getInputStream(), PixelFormat.RGBA8888, true, 0, false);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        defaultIconData = read;
        if (null != DisplayDriver.defaultIconData) {
            final ByteBuffer pixels = DisplayDriver.defaultIconData.getPixels();
            setAppIcon0(pixels, Buffers.getDirectBufferByteOffset(pixels), true, DisplayDriver.defaultIconData.getSize().getWidth(), DisplayDriver.defaultIconData.getSize().getHeight());
        }
        if (DisplayDriver.DEBUG) {
            System.err.println("MacDisplay.init App and IDs OK " + Thread.currentThread().getName());
        }
    }
}
