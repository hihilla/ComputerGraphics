// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.windows;

import java.net.URLConnection;
import com.jogamp.common.util.IOUtil;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.opengl.util.PNGPixelRect;
import com.jogamp.newt.NewtFactory;
import jogamp.newt.NEWTJNILibLoader;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.windows.WindowsGraphicsDevice;
import jogamp.nativewindow.windows.RegisteredClass;
import jogamp.nativewindow.windows.RegisteredClassFactory;
import jogamp.newt.DisplayImpl;

public class DisplayDriver extends DisplayImpl
{
    private static final String newtClassBaseName = "_newt_clazz";
    private static final long[] defaultIconHandles;
    private static RegisteredClassFactory sharedClassFactory;
    private RegisteredClass sharedClass;
    
    public static void initSingleton() {
    }
    
    protected static long getHInstance() {
        return RegisteredClassFactory.getHInstance();
    }
    
    @Override
    protected void createNativeImpl() {
        this.sharedClass = DisplayDriver.sharedClassFactory.getSharedClass();
        this.aDevice = new WindowsGraphicsDevice(0);
    }
    
    @Override
    protected void closeNativeImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        DisplayDriver.sharedClassFactory.releaseSharedClass();
        abstractGraphicsDevice.close();
    }
    
    @Override
    protected void dispatchMessagesNative() {
        DispatchMessages0();
    }
    
    protected String getWindowClassName() {
        return this.sharedClass.getName();
    }
    
    @Override
    protected final long createPointerIconImpl(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        return createBGRA8888Icon0(byteBuffer, n, n2, true, n3, n4);
    }
    
    @Override
    protected final void destroyPointerIconImpl(final long n, final long n2) {
        destroyIcon0(n2);
    }
    
    private static native void DispatchMessages0();
    
    static long createBGRA8888Icon0(final Buffer buffer, final int n, final int n2, final boolean b, final int n3, final int n4) {
        if (null == buffer) {
            throw new IllegalArgumentException("data buffer/size");
        }
        final boolean direct = Buffers.isDirect(buffer);
        return createBGRA8888Icon0(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n, n2, b, n3, n4);
    }
    
    private static native long createBGRA8888Icon0(final Object p0, final int p1, final boolean p2, final int p3, final int p4, final boolean p5, final int p6, final int p7);
    
    private static native void destroyIcon0(final long p0);
    
    static {
        NEWTJNILibLoader.loadNEWT();
        final long[] defaultIconHandles2 = { 0L, 0L };
        if (DisplayImpl.isPNGUtilAvailable()) {
            try {
                final IOUtil.ClassResources windowIcons = NewtFactory.getWindowIcons();
                final URLConnection resolve = windowIcons.resolve(0);
                if (null != resolve) {
                    final PNGPixelRect read = PNGPixelRect.read(resolve.getInputStream(), PixelFormat.BGRA8888, false, 0, false);
                    defaultIconHandles2[0] = createBGRA8888Icon0(read.getPixels(), read.getSize().getWidth(), read.getSize().getHeight(), false, 0, 0);
                }
                final URLConnection resolve2 = windowIcons.resolve(windowIcons.resourceCount() - 1);
                if (null != resolve2) {
                    final PNGPixelRect read2 = PNGPixelRect.read(resolve2.getInputStream(), PixelFormat.BGRA8888, false, 0, false);
                    defaultIconHandles2[1] = createBGRA8888Icon0(read2.getPixels(), read2.getSize().getWidth(), read2.getSize().getHeight(), false, 0, 0);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        defaultIconHandles = defaultIconHandles2;
        DisplayDriver.sharedClassFactory = new RegisteredClassFactory("_newt_clazz", WindowDriver.getNewtWndProc0(), false, DisplayDriver.defaultIconHandles[0], DisplayDriver.defaultIconHandles[1]);
        if (!WindowDriver.initIDs0(RegisteredClassFactory.getHInstance())) {
            throw new NativeWindowException("Failed to initialize WindowsWindow jmethodIDs");
        }
    }
}
