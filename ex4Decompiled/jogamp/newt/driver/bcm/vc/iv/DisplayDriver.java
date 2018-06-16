// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.bcm.vc.iv;

import java.net.URLConnection;
import com.jogamp.common.util.IOUtil;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.opengl.GLProfile;
import jogamp.newt.NEWTJNILibLoader;
import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.Display;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.newt.driver.linux.LinuxMouseTracker;
import com.jogamp.nativewindow.util.PixelRectangle;
import jogamp.opengl.egl.EGLDisplayUtil;
import jogamp.newt.PointerIconImpl;
import com.jogamp.opengl.util.PNGPixelRect;
import jogamp.newt.DisplayImpl;

public class DisplayDriver extends DisplayImpl
{
    static final PNGPixelRect defaultPointerIconImage;
    private PointerIconImpl defaultPointerIcon;
    private long bcmHandle;
    private long activePointerIcon;
    private boolean activePointerIconVisible;
    private final Object pointerIconSync;
    
    public static void initSingleton() {
    }
    
    public DisplayDriver() {
        this.defaultPointerIcon = null;
        this.pointerIconSync = new Object();
        this.bcmHandle = 0L;
        this.activePointerIcon = 0L;
        this.activePointerIconVisible = false;
    }
    
    @Override
    protected void createNativeImpl() {
        this.bcmHandle = OpenBCMDisplay0();
        (this.aDevice = EGLDisplayUtil.eglCreateEGLGraphicsDevice(0L, "decon", 0)).open();
        if (null != DisplayDriver.defaultPointerIconImage) {
            this.defaultPointerIcon = (PointerIconImpl)this.createPointerIcon(DisplayDriver.defaultPointerIconImage, 0, 0);
        }
        else {
            this.defaultPointerIcon = null;
        }
        if (DisplayDriver.DEBUG_POINTER_ICON) {
            System.err.println("Display.PointerIcon.createDefault: " + this.defaultPointerIcon);
        }
        if (null != this.defaultPointerIcon) {
            final LinuxMouseTracker singleton = LinuxMouseTracker.getSingleton();
            this.setPointerIconActive(this.defaultPointerIcon.getHandle(), singleton.getLastX(), singleton.getLastY());
        }
    }
    
    @Override
    protected void closeNativeImpl(final AbstractGraphicsDevice abstractGraphicsDevice) {
        abstractGraphicsDevice.close();
        CloseBCMDisplay0(this.bcmHandle);
        this.bcmHandle = 0L;
    }
    
    final long getBCMHandle() {
        return this.bcmHandle;
    }
    
    @Override
    protected void dispatchMessagesNative() {
        DispatchMessages0();
    }
    
    @Override
    protected final long createPointerIconImpl(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        return CreatePointerIcon(this.bcmHandle, byteBuffer, n, n2, n3, n4);
    }
    
    @Override
    protected final void destroyPointerIconImpl(final long n, final long n2) {
        DestroyPointerIcon0(n2);
    }
    
    void setPointerIconActive(long handle, final int n, final int n2) {
        synchronized (this.pointerIconSync) {
            if (DisplayDriver.DEBUG_POINTER_ICON) {
                System.err.println("Display.PointerIcon.set.0: active [" + Display.toHexString(this.activePointerIcon) + ", visible " + this.activePointerIconVisible + "] -> " + Display.toHexString(handle));
            }
            if (0L != this.activePointerIcon && this.activePointerIconVisible) {
                SetPointerIcon0(this.bcmHandle, this.activePointerIcon, false, n, n2);
            }
            if (0L == handle && null != this.defaultPointerIcon) {
                handle = this.defaultPointerIcon.getHandle();
            }
            if (0L != handle) {
                SetPointerIcon0(this.bcmHandle, handle, true, n, n2);
                this.activePointerIconVisible = true;
            }
            else {
                this.activePointerIconVisible = false;
            }
            this.activePointerIcon = handle;
            if (DisplayDriver.DEBUG_POINTER_ICON) {
                System.err.println("Display.PointerIcon.set.X: active [" + Display.toHexString(this.activePointerIcon) + ", visible " + this.activePointerIconVisible + "]");
            }
        }
    }
    
    void setActivePointerIconVisible(final boolean activePointerIconVisible, final int n, final int n2) {
        synchronized (this.pointerIconSync) {
            if (DisplayDriver.DEBUG_POINTER_ICON) {
                System.err.println("Display.PointerIcon.visible: active [" + Display.toHexString(this.activePointerIcon) + ", visible " + this.activePointerIconVisible + "] -> visible " + activePointerIconVisible);
            }
            if (this.activePointerIconVisible != activePointerIconVisible) {
                if (0L != this.activePointerIcon) {
                    SetPointerIcon0(this.bcmHandle, this.activePointerIcon, activePointerIconVisible, n, n2);
                }
                this.activePointerIconVisible = activePointerIconVisible;
            }
        }
    }
    
    void moveActivePointerIcon(final int n, final int n2) {
        synchronized (this.pointerIconSync) {
            if (DisplayDriver.DEBUG_POINTER_ICON) {
                System.err.println("Display.PointerIcon.move: active [" + Display.toHexString(this.activePointerIcon) + ", visible " + this.activePointerIconVisible + "], " + n + "/" + n2);
            }
            if (0L != this.activePointerIcon && this.activePointerIconVisible) {
                MovePointerIcon0(this.activePointerIcon, n, n2);
            }
        }
    }
    
    protected static native boolean initIDs();
    
    private static native long OpenBCMDisplay0();
    
    private static native void CloseBCMDisplay0(final long p0);
    
    private static long CreatePointerIcon(final long n, final Buffer buffer, final int n2, final int n3, final int n4, final int n5) {
        final boolean direct = Buffers.isDirect(buffer);
        return CreatePointerIcon0(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n2, n3, n4, n5);
    }
    
    private static native long CreatePointerIcon0(final Object p0, final int p1, final boolean p2, final int p3, final int p4, final int p5, final int p6);
    
    private static native void DestroyPointerIcon0(final long p0);
    
    private static native void SetPointerIcon0(final long p0, final long p1, final boolean p2, final int p3, final int p4);
    
    private static native void MovePointerIcon0(final long p0, final int p1, final int p2);
    
    private static native void DispatchMessages0();
    
    static {
        NEWTJNILibLoader.loadNEWT();
        GLProfile.initSingleton();
        if (!initIDs()) {
            throw new NativeWindowException("Failed to initialize bcm.vc.iv Display jmethodIDs");
        }
        if (!ScreenDriver.initIDs()) {
            throw new NativeWindowException("Failed to initialize bcm.vc.iv Screen jmethodIDs");
        }
        if (!WindowDriver.initIDs()) {
            throw new NativeWindowException("Failed to initialize bcm.vc.iv Window jmethodIDs");
        }
        PNGPixelRect read = null;
        if (DisplayImpl.isPNGUtilAvailable()) {
            final IOUtil.ClassResources classResources = new IOUtil.ClassResources(new String[] { "newt/data/pointer-grey-alpha-16x24.png" }, DisplayDriver.class.getClassLoader(), null);
            try {
                final URLConnection resolve = classResources.resolve(0);
                if (null != resolve) {
                    read = PNGPixelRect.read(resolve.getInputStream(), PixelFormat.BGRA8888, false, 0, false);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        defaultPointerIconImage = read;
    }
}
