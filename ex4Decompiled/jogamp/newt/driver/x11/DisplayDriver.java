// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.x11;

import com.jogamp.nativewindow.NativeWindowException;
import jogamp.newt.NEWTJNILibLoader;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.x11.X11GraphicsDevice;
import jogamp.nativewindow.x11.X11Util;
import jogamp.newt.DisplayImpl;

public class DisplayDriver extends DisplayImpl
{
    private long windowDeleteAtom;
    private long javaObjectAtom;
    private int randr_event_base;
    private int randr_error_base;
    private RandR rAndR;
    
    static void initSingleton() {
    }
    
    @Override
    public String validateDisplayName(final String s, final long n) {
        return X11Util.validateDisplayName(s, n);
    }
    
    @Override
    protected void createNativeImpl() {
        X11Util.setX11ErrorHandler(true, !DisplayDriver.DEBUG);
        final long openDisplay = X11Util.openDisplay(this.name);
        if (0L == openDisplay) {
            throw new RuntimeException("Error creating display(Win): " + this.name);
        }
        this.aDevice = new X11GraphicsDevice(openDisplay, 0, true);
        try {
            this.CompleteDisplay0(this.aDevice.getHandle());
        }
        catch (RuntimeException ex) {
            this.closeNativeImpl(this.aDevice);
            throw ex;
        }
    }
    
    @Override
    protected void closeNativeImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        this.DisplayRelease0(abstractGraphicsDevice.getHandle(), this.javaObjectAtom, this.windowDeleteAtom);
        this.javaObjectAtom = 0L;
        this.windowDeleteAtom = 0L;
        abstractGraphicsDevice.close();
    }
    
    @Override
    protected void dispatchMessagesNative() {
        final AbstractGraphicsDevice aDevice = this.aDevice;
        aDevice.lock();
        try {
            final long handle = aDevice.getHandle();
            if (0L != handle) {
                this.DispatchMessages0(handle, this.javaObjectAtom, this.windowDeleteAtom, this.randr_event_base, this.randr_error_base);
            }
        }
        finally {
            aDevice.unlock();
        }
    }
    
    protected long getJavaObjectAtom() {
        return this.javaObjectAtom;
    }
    
    protected long getWindowDeleteAtom() {
        return this.windowDeleteAtom;
    }
    
    protected int getRandREventBase() {
        return this.randr_event_base;
    }
    
    protected int getRandRErrorBase() {
        return this.randr_error_base;
    }
    
    protected Boolean isXineramaEnabled() {
        return this.isNativeValid() ? ((X11GraphicsDevice)this.aDevice).isXineramaEnabled() : null;
    }
    
    @Override
    protected final long createPointerIconImpl(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        return createPointerIcon(this.getHandle(), byteBuffer, n, n2, n3, n4);
    }
    
    @Override
    protected final void destroyPointerIconImpl(final long n, final long n2) {
        destroyPointerIcon0(n, n2);
    }
    
    private static native boolean initIDs0(final boolean p0);
    
    private native void CompleteDisplay0(final long p0);
    
    private void displayCompleted(final long javaObjectAtom, final long windowDeleteAtom, final int randr_event_base, final int randr_error_base) {
        this.javaObjectAtom = javaObjectAtom;
        this.windowDeleteAtom = windowDeleteAtom;
        this.randr_event_base = randr_event_base;
        this.randr_error_base = randr_error_base;
    }
    
    private void sendRRScreenChangeNotify(final long n) {
        if (null != this.rAndR) {
            this.rAndR.sendRRScreenChangeNotify(this.getHandle(), n);
        }
    }
    
    void registerRandR(final RandR rAndR) {
        this.rAndR = rAndR;
    }
    
    private native void DisplayRelease0(final long p0, final long p1, final long p2);
    
    private native void DispatchMessages0(final long p0, final long p1, final long p2, final int p3, final int p4);
    
    private static long createPointerIcon(final long n, final Buffer buffer, final int n2, final int n3, final int n4, final int n5) {
        final boolean direct = Buffers.isDirect(buffer);
        return createPointerIcon0(n, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n2, n3, n4, n5);
    }
    
    private static native long createPointerIcon0(final long p0, final Object p1, final int p2, final boolean p3, final int p4, final int p5, final int p6, final int p7);
    
    private static native void destroyPointerIcon0(final long p0, final long p1);
    
    static {
        NEWTJNILibLoader.loadNEWT();
        if (!initIDs0(X11Util.XERROR_STACKDUMP)) {
            throw new NativeWindowException("Failed to initialize X11Display jmethodIDs");
        }
        if (!WindowDriver.initIDs0()) {
            throw new NativeWindowException("Failed to initialize X11Window jmethodIDs");
        }
    }
}
