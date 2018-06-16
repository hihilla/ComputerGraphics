// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.event.HierarchyEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.ComponentListener;
import com.jogamp.common.os.Platform;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.awt.EventQueue;
import com.jogamp.common.ExceptionUtils;
import java.applet.Applet;
import jogamp.nativewindow.awt.AWTMisc;
import java.awt.Point;
import java.awt.Cursor;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.util.InsetsImmutable;
import com.jogamp.nativewindow.util.RectangleImmutable;
import jogamp.nativewindow.SurfaceScaleUtils;
import jogamp.nativewindow.jawt.JAWTUtil;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import jogamp.nativewindow.jawt.JAWT_Rectangle;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.util.Insets;
import com.jogamp.nativewindow.util.Rectangle;
import jogamp.nativewindow.jawt.JAWT;
import com.jogamp.common.util.locks.RecursiveLock;
import jogamp.nativewindow.SurfaceUpdatedHelper;
import java.awt.Component;
import com.jogamp.nativewindow.ScalableSurface;
import com.jogamp.nativewindow.OffscreenLayerOption;
import com.jogamp.nativewindow.OffscreenLayerSurface;
import com.jogamp.nativewindow.NativeWindow;

public abstract class JAWTWindow implements NativeWindow, OffscreenLayerSurface, OffscreenLayerOption, ScalableSurface
{
    protected static final boolean DEBUG;
    protected boolean shallUseOffscreenLayer;
    protected final Component component;
    private final AppContextInfo appContextInfo;
    private final SurfaceUpdatedHelper surfaceUpdatedHelper;
    private final RecursiveLock surfaceLock;
    private final JAWTComponentListener jawtComponentListener;
    private volatile AWTGraphicsConfiguration awtConfig;
    private boolean isApplet;
    private JAWT jawt;
    private boolean isOffscreenLayerSurface;
    protected long drawable;
    protected Rectangle bounds;
    protected Insets insets;
    private volatile long offscreenSurfaceLayer;
    private final float[] minPixelScale;
    private final float[] maxPixelScale;
    private final float[] hasPixelScale;
    private final float[] reqPixelScale;
    private volatile boolean hasPixelScaleChanged;
    private long drawable_old;
    private final Runnable repaintTask;
    private final Runnable detachSurfaceLayerNotify;
    
    protected JAWTWindow(final Object o, final AbstractGraphicsConfiguration abstractGraphicsConfiguration) {
        this.shallUseOffscreenLayer = false;
        this.surfaceUpdatedHelper = new SurfaceUpdatedHelper();
        this.surfaceLock = LockFactory.createRecursiveLock();
        this.minPixelScale = new float[] { 1.0f, 1.0f };
        this.maxPixelScale = new float[] { 1.0f, 1.0f };
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.reqPixelScale = new float[] { 0.0f, 0.0f };
        this.hasPixelScaleChanged = false;
        this.repaintTask = new Runnable() {
            @Override
            public void run() {
                final Component component = JAWTWindow.this.component;
                if (JAWTWindow.DEBUG) {
                    System.err.println("Bug 1004: RepaintTask on " + Thread.currentThread() + ": Has Comp " + (null != component));
                }
                if (null != component) {
                    component.repaint();
                }
            }
        };
        this.detachSurfaceLayerNotify = new Runnable() {
            @Override
            public void run() {
                JAWTWindow.this.offscreenSurfaceLayer = 0L;
            }
        };
        if (abstractGraphicsConfiguration == null) {
            throw new IllegalArgumentException("Error: AbstractGraphicsConfiguration is null");
        }
        if (!(abstractGraphicsConfiguration instanceof AWTGraphicsConfiguration)) {
            throw new NativeWindowException("Error: AbstractGraphicsConfiguration is not an AWTGraphicsConfiguration: " + abstractGraphicsConfiguration);
        }
        this.appContextInfo = new AppContextInfo("<init>");
        this.component = (Component)o;
        this.jawtComponentListener = new JAWTComponentListener();
        this.invalidate();
        this.awtConfig = (AWTGraphicsConfiguration)abstractGraphicsConfiguration;
        this.isApplet = false;
        this.offscreenSurfaceLayer = 0L;
        if (JAWTWindow.DEBUG) {
            System.err.println(this.jawtStr2("ctor"));
        }
    }
    
    private static String id(final Object o) {
        return (null != o) ? toHexString(o.hashCode()) : "nil";
    }
    
    private String jawtStr1() {
        return "JAWTWindow[" + id(this) + "]";
    }
    
    private String jawtStr2(final String s) {
        return this.jawtStr1() + "." + s + " @ Thread " + getThreadName();
    }
    
    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    protected synchronized void invalidate() {
        if (JAWTWindow.DEBUG) {
            System.err.println(this.jawtStr2("invalidate") + " - " + this.jawtComponentListener.toString());
            if (this.isSurfaceLayerAttached()) {
                System.err.println("OffscreenSurfaceLayer still attached: 0x" + Long.toHexString(this.offscreenSurfaceLayer));
            }
        }
        this.invalidateNative();
        this.jawt = null;
        this.awtConfig = null;
        this.isOffscreenLayerSurface = false;
        this.drawable = 0L;
        this.drawable_old = 0L;
        this.bounds = new Rectangle();
        this.insets = new Insets();
        this.hasPixelScale[0] = 1.0f;
        this.hasPixelScale[1] = 1.0f;
        this.minPixelScale[0] = 1.0f;
        this.minPixelScale[1] = 1.0f;
        this.maxPixelScale[0] = 1.0f;
        this.maxPixelScale[1] = 1.0f;
        this.hasPixelScaleChanged = false;
    }
    
    protected abstract void invalidateNative();
    
    public final void setAWTGraphicsConfiguration(final AWTGraphicsConfiguration awtConfig) {
        if (JAWTWindow.DEBUG) {
            System.err.println(this.jawtStr2("setAWTGraphicsConfiguration") + ": " + this.awtConfig + " -> " + awtConfig);
        }
        if (null == this.awtConfig) {
            throw new IllegalArgumentException(this.jawtStr2("") + ": null config");
        }
        this.awtConfig = awtConfig;
    }
    
    public final AWTGraphicsConfiguration getAWTGraphicsConfiguration() {
        return this.awtConfig;
    }
    
    @Override
    public boolean setSurfaceScale(final float[] array) {
        System.arraycopy(array, 0, this.reqPixelScale, 0, 2);
        return false;
    }
    
    @Override
    public final float[] getRequestedSurfaceScale(final float[] array) {
        System.arraycopy(this.reqPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getCurrentSurfaceScale(final float[] array) {
        System.arraycopy(this.hasPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public float[] getMinimumSurfaceScale(final float[] array) {
        System.arraycopy(this.minPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getMaximumSurfaceScale(final float[] array) {
        System.arraycopy(this.maxPixelScale, 0, array, 0, 2);
        return array;
    }
    
    protected final boolean updateLockedData(final JAWT_Rectangle jawt_Rectangle, final GraphicsConfiguration graphicsConfiguration) {
        final Rectangle rectangle = new Rectangle(jawt_Rectangle.getX(), jawt_Rectangle.getY(), jawt_Rectangle.getWidth(), jawt_Rectangle.getHeight());
        final boolean b = !this.bounds.equals(rectangle);
        if (b) {
            if (JAWTWindow.DEBUG) {
                System.err.println("JAWTWindow.updateBounds: " + this.bounds + " -> " + rectangle);
            }
            this.bounds.set(jawt_Rectangle.getX(), jawt_Rectangle.getY(), jawt_Rectangle.getWidth(), jawt_Rectangle.getHeight());
            if (this.component instanceof Container) {
                final java.awt.Insets insets = ((Container)this.component).getInsets();
                this.insets.set(insets.left, insets.right, insets.top, insets.bottom);
            }
        }
        this.updatePixelScale(graphicsConfiguration, false);
        return this.hasPixelScaleChanged || b;
    }
    
    public final boolean updatePixelScale(final GraphicsConfiguration graphicsConfiguration, final boolean b) {
        if (JAWTUtil.getPixelScale(graphicsConfiguration, this.minPixelScale, this.maxPixelScale)) {
            this.hasPixelScaleChanged = true;
            if (JAWTWindow.DEBUG) {
                System.err.println("JAWTWindow.updatePixelScale: updated req[" + this.reqPixelScale[0] + ", " + this.reqPixelScale[1] + "], min[" + this.minPixelScale[0] + ", " + this.minPixelScale[1] + "], max[" + this.maxPixelScale[0] + ", " + this.maxPixelScale[1] + "], has[" + this.hasPixelScale[0] + ", " + this.hasPixelScale[1] + "]");
            }
        }
        if (b) {
            final boolean hasPixelScaleChanged = this.hasPixelScaleChanged;
            this.hasPixelScaleChanged = false;
            return hasPixelScaleChanged;
        }
        return this.hasPixelScaleChanged;
    }
    
    public final boolean updatePixelScale(final boolean b) {
        return this.updatePixelScale(this.awtConfig.getAWTGraphicsConfiguration(), b);
    }
    
    protected final boolean updateLockedData(final JAWT_Rectangle jawt_Rectangle) {
        throw new RuntimeException("Invalid API entry");
    }
    
    protected int lockSurfaceImpl() throws NativeWindowException {
        throw new RuntimeException("Invalid API entry");
    }
    
    public final boolean hasPixelScaleChanged() {
        final boolean hasPixelScaleChanged = this.hasPixelScaleChanged;
        this.hasPixelScaleChanged = false;
        return hasPixelScaleChanged;
    }
    
    protected final boolean setReqPixelScale() {
        this.updatePixelScale(this.awtConfig.getAWTGraphicsConfiguration(), true);
        return SurfaceScaleUtils.setNewPixelScale(this.hasPixelScale, this.hasPixelScale, this.reqPixelScale, this.minPixelScale, this.maxPixelScale, JAWTWindow.DEBUG ? this.getClass().getSimpleName() : null);
    }
    
    public final RectangleImmutable getBounds() {
        return this.bounds;
    }
    
    protected final float getPixelScaleX() {
        return this.hasPixelScale[0];
    }
    
    protected final float getPixelScaleY() {
        return this.hasPixelScale[1];
    }
    
    @Override
    public final InsetsImmutable getInsets() {
        return this.insets;
    }
    
    public final Component getAWTComponent() {
        return this.component;
    }
    
    public final boolean isApplet() {
        return this.isApplet;
    }
    
    public final JAWT getJAWT() {
        return this.jawt;
    }
    
    @Override
    public void setShallUseOffscreenLayer(final boolean shallUseOffscreenLayer) {
        this.shallUseOffscreenLayer = shallUseOffscreenLayer;
    }
    
    @Override
    public final boolean getShallUseOffscreenLayer() {
        return this.shallUseOffscreenLayer;
    }
    
    @Override
    public final boolean isOffscreenLayerSurfaceEnabled() {
        return this.isOffscreenLayerSurface;
    }
    
    @Override
    public final void attachSurfaceLayer(final long offscreenSurfaceLayer) throws NativeWindowException {
        if (!this.isOffscreenLayerSurfaceEnabled()) {
            throw new NativeWindowException("Not an offscreen layer surface");
        }
        this.attachSurfaceLayerImpl(offscreenSurfaceLayer);
        this.offscreenSurfaceLayer = offscreenSurfaceLayer;
        this.appContextInfo.invokeOnAppContextThread(false, this.repaintTask, "Repaint");
    }
    
    protected void attachSurfaceLayerImpl(final long n) {
        throw new UnsupportedOperationException("offscreen layer not supported");
    }
    
    protected void layoutSurfaceLayerImpl(final long n, final boolean b) {
    }
    
    private final void layoutSurfaceLayerIfEnabled(final boolean b) throws NativeWindowException {
        if (this.isOffscreenLayerSurfaceEnabled() && 0L != this.offscreenSurfaceLayer) {
            this.layoutSurfaceLayerImpl(this.offscreenSurfaceLayer, b);
        }
    }
    
    @Override
    public final void detachSurfaceLayer() throws NativeWindowException {
        if (0L == this.offscreenSurfaceLayer) {
            throw new NativeWindowException("No offscreen layer attached: " + this);
        }
        if (JAWTWindow.DEBUG) {
            System.err.println("JAWTWindow.detachSurfaceHandle(): osh " + toHexString(this.offscreenSurfaceLayer));
        }
        this.detachSurfaceLayerImpl(this.offscreenSurfaceLayer, this.detachSurfaceLayerNotify);
    }
    
    protected void detachSurfaceLayerImpl(final long n, final Runnable runnable) {
        throw new UnsupportedOperationException("offscreen layer not supported");
    }
    
    @Override
    public final long getAttachedSurfaceLayer() {
        return this.offscreenSurfaceLayer;
    }
    
    @Override
    public final boolean isSurfaceLayerAttached() {
        return 0L != this.offscreenSurfaceLayer;
    }
    
    @Override
    public final void setChosenCapabilities(final CapabilitiesImmutable capabilitiesImmutable) {
        ((MutableGraphicsConfiguration)this.getGraphicsConfiguration()).setChosenCapabilities(capabilitiesImmutable);
        this.awtConfig.setChosenCapabilities(capabilitiesImmutable);
    }
    
    @Override
    public final RecursiveLock getLock() {
        return this.surfaceLock;
    }
    
    @Override
    public final boolean setCursor(final PixelRectangle pixelRectangle, final PointImmutable pointImmutable) {
        AWTEDTExecutor.singleton.invoke(false, new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                if (null == pixelRectangle || null == pointImmutable) {
                    cursor = Cursor.getDefaultCursor();
                }
                else {
                    final Point point = new Point(pointImmutable.getX(), pointImmutable.getY());
                    try {
                        cursor = AWTMisc.getCursor(pixelRectangle, point);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (null != cursor) {
                    JAWTWindow.this.component.setCursor(cursor);
                }
            }
        });
        return true;
    }
    
    @Override
    public final boolean hideCursor() {
        AWTEDTExecutor.singleton.invoke(false, new Runnable() {
            @Override
            public void run() {
                final Cursor nullCursor = AWTMisc.getNullCursor();
                if (null != nullCursor) {
                    JAWTWindow.this.component.setCursor(nullCursor);
                }
            }
        });
        return true;
    }
    
    private void determineIfApplet() {
        for (Component component = this.component; !this.isApplet && null != component; component = component.getParent()) {
            this.isApplet = (component instanceof Applet);
        }
    }
    
    protected abstract JAWT fetchJAWTImpl() throws NativeWindowException;
    
    protected abstract int lockSurfaceImpl(final GraphicsConfiguration p0) throws NativeWindowException;
    
    protected void dumpJAWTInfo() {
        System.err.println(this.jawt2String(null).toString());
    }
    
    @Override
    public final int lockSurface() throws NativeWindowException, RuntimeException {
        this.surfaceLock.lock();
        int lockSurfaceImpl = (this.surfaceLock.getHoldCount() == 1) ? 1 : 3;
        if (lockSurfaceImpl != 0) {
            if (!this.component.isDisplayable()) {
                this.surfaceLock.unlock();
                if (JAWTWindow.DEBUG) {
                    System.err.println("JAWTWindow: Can't lock surface, component peer n/a. Component displayable " + this.component.isDisplayable() + ", " + this.component);
                    ExceptionUtils.dumpStack(System.err);
                }
            }
            else {
                GraphicsConfiguration graphicsConfiguration;
                if (EventQueue.isDispatchThread() || Thread.holdsLock(this.component.getTreeLock())) {
                    graphicsConfiguration = this.component.getGraphicsConfiguration();
                }
                else {
                    graphicsConfiguration = this.awtConfig.getAWTGraphicsConfiguration();
                }
                this.determineIfApplet();
                try {
                    final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
                    device.lock();
                    try {
                        if (null == this.jawt) {
                            this.jawt = this.fetchJAWTImpl();
                            this.isOffscreenLayerSurface = JAWTUtil.isJAWTUsingOffscreenLayer(this.jawt);
                        }
                        lockSurfaceImpl = this.lockSurfaceImpl(graphicsConfiguration);
                        if (3 == lockSurfaceImpl && this.drawable_old != this.drawable) {
                            lockSurfaceImpl = 2;
                            if (JAWTWindow.DEBUG) {
                                System.err.println("JAWTWindow: surface change " + toHexString(this.drawable_old) + " -> " + toHexString(this.drawable));
                            }
                        }
                    }
                    finally {
                        if (1 >= lockSurfaceImpl) {
                            device.unlock();
                        }
                    }
                }
                finally {
                    if (1 >= lockSurfaceImpl) {
                        this.surfaceLock.unlock();
                    }
                }
            }
        }
        return lockSurfaceImpl;
    }
    
    protected abstract void unlockSurfaceImpl() throws NativeWindowException;
    
    @Override
    public final void unlockSurface() {
        this.surfaceLock.validateLocked();
        this.drawable_old = this.drawable;
        if (this.surfaceLock.getHoldCount() == 1) {
            final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
            try {
                if (null != this.jawt) {
                    this.unlockSurfaceImpl();
                }
            }
            finally {
                device.unlock();
            }
        }
        this.surfaceLock.unlock();
    }
    
    @Override
    public final boolean isSurfaceLockedByOtherThread() {
        return this.surfaceLock.isLockedByOtherThread();
    }
    
    @Override
    public final Thread getSurfaceLockOwner() {
        return this.surfaceLock.getOwner();
    }
    
    @Override
    public boolean surfaceSwap() {
        return false;
    }
    
    @Override
    public void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public void addSurfaceUpdatedListener(final int n, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(n, surfaceUpdatedListener);
    }
    
    @Override
    public void removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.removeSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        this.surfaceUpdatedHelper.surfaceUpdated(o, nativeSurface, n);
    }
    
    @Override
    public long getSurfaceHandle() {
        return this.drawable;
    }
    
    @Override
    public final AbstractGraphicsConfiguration getGraphicsConfiguration() {
        if (null == this.awtConfig) {
            throw new NativeWindowException(this.jawtStr2("") + ": null awtConfig, invalidated");
        }
        return this.awtConfig.getNativeGraphicsConfiguration();
    }
    
    @Override
    public final long getDisplayHandle() {
        return this.getGraphicsConfiguration().getScreen().getDevice().getHandle();
    }
    
    @Override
    public final int getScreenIndex() {
        return this.getGraphicsConfiguration().getScreen().getIndex();
    }
    
    @Override
    public final int getSurfaceWidth() {
        return SurfaceScaleUtils.scale(this.getWidth(), this.getPixelScaleX());
    }
    
    @Override
    public final int getSurfaceHeight() {
        return SurfaceScaleUtils.scale(this.getHeight(), this.getPixelScaleY());
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
    public final NativeSurface getNativeSurface() {
        return this;
    }
    
    @Override
    public final int getWidth() {
        return this.component.getWidth();
    }
    
    @Override
    public final int getHeight() {
        return this.component.getHeight();
    }
    
    @Override
    public void destroy() {
        this.surfaceLock.lock();
        try {
            if (JAWTWindow.DEBUG) {
                System.err.println(this.jawtStr2("destroy"));
            }
            this.jawtComponentListener.detach();
            this.invalidate();
        }
        finally {
            this.surfaceLock.unlock();
        }
    }
    
    @Override
    public final NativeWindow getParent() {
        return null;
    }
    
    @Override
    public long getWindowHandle() {
        return this.drawable;
    }
    
    @Override
    public final int getX() {
        return this.component.getX();
    }
    
    @Override
    public final int getY() {
        return this.component.getY();
    }
    
    @Override
    public com.jogamp.nativewindow.util.Point getLocationOnScreen(final com.jogamp.nativewindow.util.Point point) {
        com.jogamp.nativewindow.util.Point point2 = this.getLocationOnScreenNative(point);
        if (null == point2) {
            point2 = AWTMisc.getLocationOnScreenSafe(point, this.component, JAWTWindow.DEBUG);
        }
        return point2;
    }
    
    protected com.jogamp.nativewindow.util.Point getLocationOnScreenNative(final com.jogamp.nativewindow.util.Point point) {
        if (1 == this.lockSurface()) {
            if (JAWTWindow.DEBUG) {
                System.err.println("Warning: JAWT Lock couldn't be acquired: " + this);
                ExceptionUtils.dumpStack(System.err);
            }
            return null;
        }
        try {
            final com.jogamp.nativewindow.util.Point locationOnScreenNativeImpl = this.getLocationOnScreenNativeImpl(0, 0);
            if (null != locationOnScreenNativeImpl && null != point) {
                point.translate(locationOnScreenNativeImpl.getX(), locationOnScreenNativeImpl.getY());
                return point;
            }
            return locationOnScreenNativeImpl;
        }
        finally {
            this.unlockSurface();
        }
    }
    
    protected abstract com.jogamp.nativewindow.util.Point getLocationOnScreenNativeImpl(final int p0, final int p1);
    
    @Override
    public boolean hasFocus() {
        return this.component.hasFocus();
    }
    
    protected StringBuilder jawt2String(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("JVM version: ").append(PlatformPropsImpl.JAVA_VERSION).append(" (").append(PlatformPropsImpl.JAVA_VERSION_NUMBER).append(" update ").append(PlatformPropsImpl.JAVA_VERSION_UPDATE).append(")").append(Platform.getNewline());
        if (null != this.jawt) {
            sb.append("JAWT version: ").append(toHexString(this.jawt.getCachedVersion())).append(", CA_LAYER: ").append(JAWTUtil.isJAWTUsingOffscreenLayer(this.jawt)).append(", isLayeredSurface ").append(this.isOffscreenLayerSurfaceEnabled()).append(", bounds ").append(this.bounds).append(", insets ").append(this.insets).append(", pixelScale ").append(this.getPixelScaleX()).append("x").append(this.getPixelScaleY());
        }
        else {
            sb.append("JAWT n/a, bounds ").append(this.bounds).append(", insets ").append(this.insets);
        }
        return sb;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.jawtStr1() + "[");
        this.jawt2String(sb);
        sb.append(", shallUseOffscreenLayer " + this.shallUseOffscreenLayer + ", isOffscreenLayerSurface " + this.isOffscreenLayerSurface + ", attachedSurfaceLayer " + toHexString(this.getAttachedSurfaceLayer()) + ", windowHandle " + toHexString(this.getWindowHandle()) + ", surfaceHandle " + toHexString(this.getSurfaceHandle()) + ", bounds " + this.bounds + ", insets " + this.insets);
        sb.append(", window [" + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + "], pixels[scale " + this.getPixelScaleX() + ", " + this.getPixelScaleY() + " -> " + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + "]" + ", visible " + this.component.isVisible());
        sb.append(", lockedExt " + this.isSurfaceLockedByOtherThread() + ",\n\tconfig " + this.awtConfig + ",\n\tawtComponent " + this.getAWTComponent() + ",\n\tsurfaceLock " + this.surfaceLock + "]");
        return sb.toString();
    }
    
    protected static final String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    protected static final String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    static {
        DEBUG = JAWTUtil.DEBUG;
    }
    
    private class JAWTComponentListener implements ComponentListener, HierarchyListener
    {
        private volatile boolean isShowing;
        
        private String str(final Object o) {
            if (null == o) {
                return "0xnil: null";
            }
            if (o instanceof Component) {
                final Component component = (Component)o;
                return id(o) + ": " + component.getClass().getSimpleName() + "[visible " + component.isVisible() + ", showing " + component.isShowing() + ", valid " + component.isValid() + ", displayable " + component.isDisplayable() + ", " + component.getX() + "/" + component.getY() + " " + component.getWidth() + "x" + component.getHeight() + "]";
            }
            return id(o) + ": " + o.getClass().getSimpleName() + "[..]";
        }
        
        private String s(final ComponentEvent componentEvent) {
            return "visible[isShowing " + this.isShowing + "]," + Platform.getNewline() + "    ** COMP " + this.str(componentEvent.getComponent()) + Platform.getNewline() + "    ** SOURCE " + this.str(componentEvent.getSource()) + Platform.getNewline() + "    ** THIS " + this.str(JAWTWindow.this.component) + Platform.getNewline() + "    ** THREAD " + getThreadName();
        }
        
        private String s(final HierarchyEvent hierarchyEvent) {
            return "visible[isShowing " + this.isShowing + "], changeBits 0x" + Long.toHexString(hierarchyEvent.getChangeFlags()) + Platform.getNewline() + "    ** COMP " + this.str(hierarchyEvent.getComponent()) + Platform.getNewline() + "    ** SOURCE " + this.str(hierarchyEvent.getSource()) + Platform.getNewline() + "    ** CHANGED " + this.str(hierarchyEvent.getChanged()) + Platform.getNewline() + "    ** CHANGEDPARENT " + this.str(hierarchyEvent.getChangedParent()) + Platform.getNewline() + "    ** THIS " + this.str(JAWTWindow.this.component) + Platform.getNewline() + "    ** THREAD " + getThreadName();
        }
        
        @Override
        public final String toString() {
            return "visible[isShowing " + this.isShowing + "]," + Platform.getNewline() + "    ** THIS " + this.str(JAWTWindow.this.component) + Platform.getNewline() + "    ** THREAD " + getThreadName();
        }
        
        private JAWTComponentListener() {
            this.isShowing = JAWTWindow.this.component.isShowing();
            AWTEDTExecutor.singleton.invoke(false, new Runnable() {
                @Override
                public void run() {
                    JAWTComponentListener.this.isShowing = JAWTWindow.this.component.isShowing();
                    if (JAWTWindow.DEBUG) {
                        System.err.println(JAWTWindow.this.jawtStr2("attach") + ": " + JAWTComponentListener.this.toString());
                    }
                    JAWTWindow.this.component.addComponentListener(JAWTComponentListener.this);
                    JAWTWindow.this.component.addHierarchyListener(JAWTComponentListener.this);
                }
            });
        }
        
        private final void detach() {
            AWTEDTExecutor.singleton.invoke(false, new Runnable() {
                @Override
                public void run() {
                    if (JAWTWindow.DEBUG) {
                        System.err.println(JAWTWindow.this.jawtStr2("detach") + ": " + JAWTComponentListener.this.toString());
                    }
                    JAWTWindow.this.component.removeComponentListener(JAWTComponentListener.this);
                    JAWTWindow.this.component.removeHierarchyListener(JAWTComponentListener.this);
                }
            });
        }
        
        @Override
        public final void componentResized(final ComponentEvent componentEvent) {
            if (JAWTWindow.DEBUG) {
                System.err.println(JAWTWindow.this.jawtStr2("componentResized") + ": " + this.s(componentEvent));
            }
            JAWTWindow.this.layoutSurfaceLayerIfEnabled(this.isShowing);
        }
        
        @Override
        public final void componentMoved(final ComponentEvent componentEvent) {
            if (JAWTWindow.DEBUG) {
                System.err.println(JAWTWindow.this.jawtStr2("componentMoved") + ": " + this.s(componentEvent));
            }
            JAWTWindow.this.layoutSurfaceLayerIfEnabled(this.isShowing);
        }
        
        @Override
        public final void componentShown(final ComponentEvent componentEvent) {
            if (JAWTWindow.DEBUG) {
                System.err.println(JAWTWindow.this.jawtStr2("componentShown") + ": " + this.s(componentEvent));
            }
            JAWTWindow.this.layoutSurfaceLayerIfEnabled(this.isShowing);
        }
        
        @Override
        public final void componentHidden(final ComponentEvent componentEvent) {
            if (JAWTWindow.DEBUG) {
                System.err.println(JAWTWindow.this.jawtStr2("componentHidden") + ": " + this.s(componentEvent));
            }
            JAWTWindow.this.layoutSurfaceLayerIfEnabled(this.isShowing);
        }
        
        @Override
        public final void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
            final boolean isShowing = this.isShowing;
            this.isShowing = JAWTWindow.this.component.isShowing();
            int n = 0;
            if (0x0L != (0x4L & hierarchyEvent.getChangeFlags()) && hierarchyEvent.getChanged() != JAWTWindow.this.component && isShowing != this.isShowing) {
                JAWTWindow.this.layoutSurfaceLayerIfEnabled(this.isShowing);
                n = 1;
            }
            if (JAWTWindow.DEBUG) {
                final Component changed = hierarchyEvent.getChanged();
                System.err.println(JAWTWindow.this.jawtStr2("hierarchyChanged") + ": action " + n + ", displayable " + changed.isDisplayable() + ", showing [changed " + changed.isShowing() + ", comp " + isShowing + " -> " + this.isShowing + "], " + this.s(hierarchyEvent));
            }
        }
    }
}
