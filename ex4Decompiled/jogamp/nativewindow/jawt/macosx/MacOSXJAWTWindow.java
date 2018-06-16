// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt.macosx;

import com.jogamp.common.util.PropertyAccess;
import jogamp.nativewindow.Debug;
import com.jogamp.nativewindow.util.Rectangle;
import java.nio.Buffer;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.Capabilities;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.awt.GraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowException;
import jogamp.nativewindow.jawt.JAWT;
import java.awt.Insets;
import java.awt.Component;
import jogamp.nativewindow.jawt.JAWTUtil;
import jogamp.nativewindow.awt.AWTMisc;
import com.jogamp.nativewindow.util.Point;
import jogamp.nativewindow.macosx.OSXUtil;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import jogamp.nativewindow.jawt.JAWT_DrawingSurfaceInfo;
import jogamp.nativewindow.jawt.JAWT_DrawingSurface;
import com.jogamp.nativewindow.MutableSurface;
import com.jogamp.nativewindow.awt.JAWTWindow;

public class MacOSXJAWTWindow extends JAWTWindow implements MutableSurface
{
    private static final boolean DEBUG_CALAYER_POS_CRITICAL;
    private JAWT_DrawingSurface ds;
    private boolean dsLocked;
    private JAWT_DrawingSurfaceInfo dsi;
    private long jawtSurfaceLayersHandle;
    private JAWT_MacOSXDrawingSurfaceInfo macosxdsi;
    private volatile long rootSurfaceLayer;
    private long windowHandle;
    private long offscreenSurfaceDrawable;
    private boolean offscreenSurfaceDrawableSet;
    private boolean firstLock;
    
    public MacOSXJAWTWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        super(o, abstractGraphicsConfiguration);
        this.rootSurfaceLayer = 0L;
        this.windowHandle = 0L;
        this.offscreenSurfaceDrawable = 0L;
        this.offscreenSurfaceDrawableSet = false;
        this.firstLock = true;
        if (MacOSXJAWTWindow.DEBUG) {
            this.dumpInfo();
        }
    }
    
    @Override
    protected void invalidateNative() {
        if (MacOSXJAWTWindow.DEBUG) {
            System.err.println("MacOSXJAWTWindow.invalidateNative(): osh-enabled " + this.isOffscreenLayerSurfaceEnabled() + ", osd-set " + this.offscreenSurfaceDrawableSet + ", osd " + JAWTWindow.toHexString(this.offscreenSurfaceDrawable) + ", osl " + JAWTWindow.toHexString(this.getAttachedSurfaceLayer()) + ", rsl " + JAWTWindow.toHexString(this.rootSurfaceLayer) + ", wh " + JAWTWindow.toHexString(this.windowHandle) + " - " + Thread.currentThread().getName());
        }
        this.offscreenSurfaceDrawable = 0L;
        this.offscreenSurfaceDrawableSet = false;
        if (this.isOffscreenLayerSurfaceEnabled()) {
            if (0L != this.windowHandle) {
                OSXUtil.DestroyNSWindow(this.windowHandle);
            }
            OSXUtil.RunOnMainThread(false, true, new Runnable() {
                @Override
                public void run() {
                    if (0L != MacOSXJAWTWindow.this.jawtSurfaceLayersHandle) {
                        UnsetJAWTRootSurfaceLayer0(MacOSXJAWTWindow.this.jawtSurfaceLayersHandle, MacOSXJAWTWindow.this.rootSurfaceLayer);
                    }
                    MacOSXJAWTWindow.this.jawtSurfaceLayersHandle = 0L;
                    if (0L != MacOSXJAWTWindow.this.rootSurfaceLayer) {
                        OSXUtil.DestroyCALayer(MacOSXJAWTWindow.this.rootSurfaceLayer);
                        MacOSXJAWTWindow.this.rootSurfaceLayer = 0L;
                    }
                }
            });
        }
        this.windowHandle = 0L;
    }
    
    @Override
    public boolean setSurfaceScale(final float[] surfaceScale) {
        super.setSurfaceScale(surfaceScale);
        if (0L != this.getWindowHandle() && this.setReqPixelScale()) {
            if (0L != this.getAttachedSurfaceLayer()) {
                OSXUtil.RunOnMainThread(false, false, new Runnable() {
                    @Override
                    public void run() {
                        final long attachedSurfaceLayer = MacOSXJAWTWindow.this.getAttachedSurfaceLayer();
                        if (0L != attachedSurfaceLayer) {
                            OSXUtil.SetCALayerPixelScale(MacOSXJAWTWindow.this.rootSurfaceLayer, attachedSurfaceLayer, MacOSXJAWTWindow.this.getPixelScaleX());
                        }
                    }
                });
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void attachSurfaceLayerImpl(final long n) {
        OSXUtil.RunOnMainThread(false, false, new Runnable() {
            @Override
            public void run() {
                final Point point = new Point();
                final Component locationOnScreenNonBlocking = AWTMisc.getLocationOnScreenNonBlocking(point, MacOSXJAWTWindow.this.component, MacOSXJAWTWindow.DEBUG);
                final Insets insets = AWTMisc.getInsets(locationOnScreenNonBlocking, true);
                final Point point2 = (Point)point.cloneMutable();
                point2.translate(-locationOnScreenNonBlocking.getX(), -locationOnScreenNonBlocking.getY());
                if (null != insets) {
                    point2.translate(-insets.left, -insets.top);
                }
                if (MacOSXJAWTWindow.DEBUG_CALAYER_POS_CRITICAL) {
                    final java.awt.Point locationOnScreen = MacOSXJAWTWindow.this.component.getLocationOnScreen();
                    final Point point3 = new Point(locationOnScreen.x, locationOnScreen.y);
                    point3.translate(-locationOnScreenNonBlocking.getX(), -locationOnScreenNonBlocking.getY());
                    if (null != insets) {
                        point3.translate(-insets.left, -insets.top);
                    }
                    System.err.println("JAWTWindow.attachSurfaceLayerImpl: " + JAWTWindow.toHexString(n) + ", [ins " + insets + "], pA " + locationOnScreen + " -> " + point3 + ", p0 " + point + " -> " + point2 + ", bounds " + MacOSXJAWTWindow.this.bounds);
                }
                else if (MacOSXJAWTWindow.DEBUG) {
                    System.err.println("JAWTWindow.attachSurfaceLayerImpl: " + JAWTWindow.toHexString(n) + ", [ins " + insets + "], p0 " + point + " -> " + point2 + ", bounds " + MacOSXJAWTWindow.this.bounds);
                }
                OSXUtil.AddCASublayer(MacOSXJAWTWindow.this.rootSurfaceLayer, n, point2.getX(), point2.getY(), MacOSXJAWTWindow.this.getWidth(), MacOSXJAWTWindow.this.getHeight(), MacOSXJAWTWindow.this.getPixelScaleX(), JAWTUtil.getOSXCALayerQuirks());
            }
        });
    }
    
    @Override
    protected void layoutSurfaceLayerImpl(final long n, final boolean b) {
        final int osxcaLayerQuirks = JAWTUtil.getOSXCALayerQuirks();
        final Point point = new Point();
        final Component locationOnScreenNonBlocking = AWTMisc.getLocationOnScreenNonBlocking(point, this.component, MacOSXJAWTWindow.DEBUG);
        final Insets insets = AWTMisc.getInsets(locationOnScreenNonBlocking, true);
        final Point point2 = (Point)point.cloneMutable();
        point2.translate(-locationOnScreenNonBlocking.getX(), -locationOnScreenNonBlocking.getY());
        if (null != insets) {
            point2.translate(-insets.left, -insets.top);
        }
        if (MacOSXJAWTWindow.DEBUG_CALAYER_POS_CRITICAL) {
            final java.awt.Point locationOnScreen = this.component.getLocationOnScreen();
            final Point point3 = new Point(locationOnScreen.x, locationOnScreen.y);
            point3.translate(-locationOnScreenNonBlocking.getX(), -locationOnScreenNonBlocking.getY());
            if (null != insets) {
                point3.translate(-insets.left, -insets.top);
            }
            System.err.println("JAWTWindow.layoutSurfaceLayerImpl: " + JAWTWindow.toHexString(n) + ", quirks " + osxcaLayerQuirks + ", visible " + b + ", [ins " + insets + "], pA " + locationOnScreen + " -> " + point3 + ", p0 " + point + " -> " + point2 + ", bounds " + this.bounds);
        }
        else if (MacOSXJAWTWindow.DEBUG) {
            System.err.println("JAWTWindow.layoutSurfaceLayerImpl: " + JAWTWindow.toHexString(n) + ", quirks " + osxcaLayerQuirks + ", visible " + b + ", [ins " + insets + "], p0 " + point + " -> " + point2 + ", bounds " + this.bounds);
        }
        OSXUtil.FixCALayerLayout(this.rootSurfaceLayer, n, b, point2.getX(), point2.getY(), this.getWidth(), this.getHeight(), osxcaLayerQuirks);
    }
    
    @Override
    protected void detachSurfaceLayerImpl(final long n, final Runnable runnable) {
        OSXUtil.RunOnMainThread(false, true, new Runnable() {
            @Override
            public void run() {
                runnable.run();
                OSXUtil.RemoveCASublayer(MacOSXJAWTWindow.this.rootSurfaceLayer, n);
            }
        });
    }
    
    @Override
    public final long getWindowHandle() {
        return this.windowHandle;
    }
    
    @Override
    public final long getSurfaceHandle() {
        return this.offscreenSurfaceDrawableSet ? this.offscreenSurfaceDrawable : this.drawable;
    }
    
    @Override
    public void setSurfaceHandle(final long offscreenSurfaceDrawable) {
        if (!this.isOffscreenLayerSurfaceEnabled()) {
            throw new UnsupportedOperationException("Not using CALAYER");
        }
        if (MacOSXJAWTWindow.DEBUG) {
            System.err.println("MacOSXJAWTWindow.setSurfaceHandle(): " + JAWTWindow.toHexString(offscreenSurfaceDrawable));
        }
        this.offscreenSurfaceDrawable = offscreenSurfaceDrawable;
        this.offscreenSurfaceDrawableSet = true;
    }
    
    @Override
    protected JAWT fetchJAWTImpl() throws NativeWindowException {
        return JAWTUtil.getJAWT(this.getShallUseOffscreenLayer() || this.isApplet());
    }
    
    @Override
    protected int lockSurfaceImpl(final GraphicsConfiguration graphicsConfiguration) throws NativeWindowException {
        this.ds = this.getJAWT().GetDrawingSurface(this.component);
        if (this.ds == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        final int lock = this.ds.Lock();
        if (!(this.dsLocked = (0x0 == (lock & 0x1)))) {
            this.unlockSurfaceImpl();
            throw new NativeWindowException("Unable to lock surface");
        }
        if ((lock & 0x8) != 0x0) {}
        if (this.firstLock) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    MacOSXJAWTWindow.this.dsi = MacOSXJAWTWindow.this.ds.GetDrawingSurfaceInfo();
                    return null;
                }
            });
        }
        else {
            this.dsi = this.ds.GetDrawingSurfaceInfo();
        }
        if (this.dsi == null) {
            this.unlockSurfaceImpl();
            return 1;
        }
        this.updateLockedData(this.dsi.getBounds(), graphicsConfiguration);
        if (MacOSXJAWTWindow.DEBUG && this.firstLock) {
            this.dumpInfo();
        }
        this.firstLock = false;
        int n;
        if (!this.isOffscreenLayerSurfaceEnabled()) {
            this.macosxdsi = (JAWT_MacOSXDrawingSurfaceInfo)this.dsi.platformInfo(this.getJAWT());
            if (this.macosxdsi == null) {
                this.unlockSurfaceImpl();
                return 1;
            }
            this.drawable = this.macosxdsi.getCocoaViewRef();
            if (this.drawable == 0L) {
                this.unlockSurfaceImpl();
                return 1;
            }
            this.windowHandle = OSXUtil.GetNSWindow(this.drawable);
            n = 3;
        }
        else {
            String string = null;
            if (0L == this.drawable) {
                this.windowHandle = OSXUtil.CreateNSWindow(0, 0, 64, 64);
                if (0L == this.windowHandle) {
                    string = "Unable to create dummy NSWindow (layered case)";
                }
                else {
                    this.drawable = OSXUtil.GetNSView(this.windowHandle);
                    if (0L == this.drawable) {
                        string = "Null NSView of NSWindow " + JAWTWindow.toHexString(this.windowHandle);
                    }
                }
                if (null == string) {
                    final Capabilities chosenCapabilities = (Capabilities)this.getGraphicsConfiguration().getChosenCapabilities().cloneMutable();
                    chosenCapabilities.setOnscreen(false);
                    this.setChosenCapabilities(chosenCapabilities);
                }
            }
            if (null == string) {
                this.jawtSurfaceLayersHandle = GetJAWTSurfaceLayersHandle0(this.dsi.getBuffer());
                OSXUtil.RunOnMainThread(false, false, new Runnable() {
                    @Override
                    public void run() {
                        String string = null;
                        if (0L == MacOSXJAWTWindow.this.rootSurfaceLayer && 0L != MacOSXJAWTWindow.this.jawtSurfaceLayersHandle) {
                            MacOSXJAWTWindow.this.rootSurfaceLayer = OSXUtil.CreateCALayer(MacOSXJAWTWindow.this.bounds.getWidth(), MacOSXJAWTWindow.this.bounds.getHeight(), MacOSXJAWTWindow.this.getPixelScaleX());
                            if (0L == MacOSXJAWTWindow.this.rootSurfaceLayer) {
                                string = "Could not create root CALayer";
                            }
                            else {
                                try {
                                    SetJAWTRootSurfaceLayer0(MacOSXJAWTWindow.this.jawtSurfaceLayersHandle, MacOSXJAWTWindow.this.rootSurfaceLayer);
                                }
                                catch (Exception ex) {
                                    string = "Could not set JAWT rootSurfaceLayerHandle " + JAWTWindow.toHexString(MacOSXJAWTWindow.this.rootSurfaceLayer) + ", cause: " + ex.getMessage();
                                }
                            }
                            if (null != string) {
                                if (0L != MacOSXJAWTWindow.this.rootSurfaceLayer) {
                                    OSXUtil.DestroyCALayer(MacOSXJAWTWindow.this.rootSurfaceLayer);
                                    MacOSXJAWTWindow.this.rootSurfaceLayer = 0L;
                                }
                                throw new NativeWindowException(string + ": " + MacOSXJAWTWindow.this);
                            }
                        }
                    }
                });
            }
            if (null != string) {
                if (0L != this.windowHandle) {
                    OSXUtil.DestroyNSWindow(this.windowHandle);
                    this.windowHandle = 0L;
                }
                this.drawable = 0L;
                this.unlockSurfaceImpl();
                throw new NativeWindowException(string + ": " + this);
            }
            n = 3;
        }
        return n;
    }
    
    @Override
    protected void unlockSurfaceImpl() throws NativeWindowException {
        if (null != this.ds) {
            if (null != this.dsi) {
                this.ds.FreeDrawingSurfaceInfo(this.dsi);
            }
            if (this.dsLocked) {
                this.ds.Unlock();
            }
            this.getJAWT().FreeDrawingSurface(this.ds);
        }
        this.ds = null;
        this.dsi = null;
    }
    
    private void dumpInfo() {
        System.err.println("MaxOSXJAWTWindow: 0x" + Integer.toHexString(this.hashCode()) + " - thread: " + Thread.currentThread().getName());
        this.dumpJAWTInfo();
    }
    
    @Override
    public Point getLocationOnScreen(Point point) {
        if (null == point) {
            point = new Point();
        }
        AWTMisc.getLocationOnScreenNonBlocking(point, this.component, MacOSXJAWTWindow.DEBUG);
        return point;
    }
    
    @Override
    protected Point getLocationOnScreenNativeImpl(final int n, final int n2) {
        return null;
    }
    
    private static native long GetJAWTSurfaceLayersHandle0(final Buffer p0);
    
    private static native void SetJAWTRootSurfaceLayer0(final long p0, final long p1);
    
    private static native void UnsetJAWTRootSurfaceLayer0(final long p0, final long p1);
    
    static {
        Debug.initSingleton();
        DEBUG_CALAYER_POS_CRITICAL = PropertyAccess.isPropertyDefined("nativewindow.debug.JAWT.OSXCALayerPos", true);
    }
}
