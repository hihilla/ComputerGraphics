// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.awt;

import java.awt.Toolkit;
import jogamp.opengl.Debug;
import javax.swing.SwingUtilities;
import java.awt.Frame;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import java.lang.reflect.InvocationTargetException;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import java.util.ArrayList;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.GraphicsConfigurationFactory;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.nativewindow.awt.AWTGraphicsScreen;
import java.security.AccessController;
import java.io.Serializable;
import java.security.PrivilegedAction;
import com.jogamp.opengl.GL;
import java.util.List;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.GLEventListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Graphics2D;
import java.awt.EventQueue;
import com.jogamp.opengl.util.TileRenderer;
import jogamp.nativewindow.SurfaceScaleUtils;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import java.beans.Beans;
import java.awt.GraphicsConfiguration;
import com.jogamp.opengl.Threading;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.util.GLDrawableUtil;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLDrawable;
import java.awt.Component;
import java.awt.event.HierarchyEvent;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.opengl.GLException;
import java.lang.reflect.Method;
import jogamp.opengl.awt.AWTTilePainter;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.nativewindow.awt.AWTWindowClosingProtocol;
import java.awt.event.HierarchyListener;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import java.awt.GraphicsDevice;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import jogamp.opengl.GLContextImpl;
import com.jogamp.nativewindow.awt.JAWTWindow;
import jogamp.opengl.GLDrawableImpl;
import jogamp.opengl.GLDrawableHelper;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.ScalableSurface;
import com.jogamp.opengl.GLSharedContextSetter;
import com.jogamp.nativewindow.awt.AWTPrintLifecycle;
import com.jogamp.nativewindow.OffscreenLayerOption;
import com.jogamp.nativewindow.WindowClosingProtocol;
import java.awt.Canvas;

public class GLCanvas extends Canvas implements AWTGLAutoDrawable, WindowClosingProtocol, OffscreenLayerOption, AWTPrintLifecycle, GLSharedContextSetter, ScalableSurface
{
    private static final boolean DEBUG;
    private final RecursiveLock lock;
    private final GLDrawableHelper helper;
    private volatile GLDrawableImpl drawable;
    private volatile JAWTWindow jawtWindow;
    private volatile GLContextImpl context;
    private volatile boolean sendReshape;
    private final float[] minPixelScale;
    private final float[] maxPixelScale;
    private final float[] hasPixelScale;
    final float[] reqPixelScale;
    private final GLCapabilitiesImmutable capsReqUser;
    private final GLCapabilitiesChooser chooser;
    private int additionalCtxCreationFlags;
    private boolean shallUseOffscreenLayer;
    private volatile GraphicsDevice awtDeviceReq;
    private volatile AWTGraphicsConfiguration awtConfig;
    private volatile boolean isShowing;
    private final HierarchyListener hierarchyListener;
    private final AWTWindowClosingProtocol awtWindowClosingProtocol;
    private final Runnable realizeOnEDTAction;
    private final Runnable unrealizeOnEDTAction;
    private final Runnable setSurfaceScaleOnEDTAction;
    private volatile boolean printActive;
    private GLAnimatorControl printAnimator;
    private GLAutoDrawable printGLAD;
    private AWTTilePainter printAWTTiles;
    private final Runnable setupPrintOnEDT;
    private final Runnable releasePrintOnEDT;
    private final Runnable destroyOnEDTAction;
    private final Runnable disposeJAWTWindowAndAWTDeviceOnEDT;
    private final Runnable initAction;
    private final Runnable displayAction;
    private final Runnable displayOnEDTAction;
    private final Runnable swapBuffersOnEDTAction;
    private static boolean disableBackgroundEraseInitialized;
    private static Method disableBackgroundEraseMethod;
    
    public GLCanvas() throws GLException {
        this((GLCapabilitiesImmutable)null);
    }
    
    public GLCanvas(final GLCapabilitiesImmutable glCapabilitiesImmutable) throws GLException {
        this(glCapabilitiesImmutable, null, null);
    }
    
    public GLCanvas(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser chooser, final GraphicsDevice awtDeviceReq) throws GLException {
        this.lock = LockFactory.createRecursiveLock();
        this.helper = new GLDrawableHelper();
        this.sendReshape = false;
        this.minPixelScale = new float[] { 1.0f, 1.0f };
        this.maxPixelScale = new float[] { 1.0f, 1.0f };
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.reqPixelScale = new float[] { 0.0f, 0.0f };
        this.additionalCtxCreationFlags = 0;
        this.shallUseOffscreenLayer = false;
        this.hierarchyListener = new HierarchyListener() {
            @Override
            public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
                GLCanvas.this.isShowing = GLCanvas.this.isShowing();
            }
        };
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.destroyImpl(true);
            }
        }, null);
        this.realizeOnEDTAction = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.setRealizedImpl(true);
            }
        };
        this.unrealizeOnEDTAction = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.setRealizedImpl(false);
            }
        };
        this.setSurfaceScaleOnEDTAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    if (null != GLCanvas.this.drawable && GLCanvas.this.drawable.isRealized() && GLCanvas.this.setSurfaceScaleImpl(GLCanvas.this.jawtWindow)) {
                        GLCanvas.this.reshapeImpl(GLCanvas.this.getWidth(), GLCanvas.this.getHeight());
                        if (!GLCanvas.this.helper.isAnimatorAnimatingOnOtherThread()) {
                            GLCanvas.this.helper.invokeGL(GLCanvas.this.drawable, GLCanvas.this.context, GLCanvas.this.displayAction, GLCanvas.this.initAction);
                        }
                    }
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        this.printActive = false;
        this.printAnimator = null;
        this.printGLAD = null;
        this.printAWTTiles = null;
        this.setupPrintOnEDT = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    if (!GLCanvas.this.validateGLDrawable()) {
                        if (GLCanvas.DEBUG) {
                            System.err.println(GLCanvas.getThreadName() + ": Info: GLCanvas setupPrint - skipped GL render, drawable not valid yet");
                        }
                        GLCanvas.this.printActive = false;
                        return;
                    }
                    if (!GLCanvas.this.isVisible()) {
                        if (GLCanvas.DEBUG) {
                            System.err.println(GLCanvas.getThreadName() + ": Info: GLCanvas setupPrint - skipped GL render, canvas not visible");
                        }
                        GLCanvas.this.printActive = false;
                        return;
                    }
                    GLCanvas.this.sendReshape = false;
                    GLCanvas.this.printAnimator = GLCanvas.this.helper.getAnimator();
                    if (null != GLCanvas.this.printAnimator) {
                        GLCanvas.this.printAnimator.remove(GLCanvas.this);
                    }
                    GLCanvas.this.printGLAD = GLCanvas.this;
                    final GLCapabilitiesImmutable chosenGLCapabilities = GLCanvas.this.getChosenGLCapabilities();
                    final int numSamples = GLCanvas.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = GLCanvas.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (GLCanvas.this.printAWTTiles.customTileWidth != -1 && GLCanvas.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (GLCanvas.this.printAWTTiles.customTileHeight != -1 && GLCanvas.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final boolean onscreen = chosenGLCapabilities.isOnscreen();
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(GLCanvas.this.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (onscreen || b || b2) && swapGLContextSafe;
                    if (GLCanvas.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ onscreen " + onscreen + ", samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + GLCanvas.this.printAWTTiles.customTileWidth + "x" + GLCanvas.this.printAWTTiles.customTileHeight + ", scaleMat " + GLCanvas.this.printAWTTiles.scaleMatX + " x " + GLCanvas.this.printAWTTiles.scaleMatY + ", numSamples " + GLCanvas.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + GLCanvas.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (GLCanvas.this.printAWTTiles.customTileWidth != -1) ? GLCanvas.this.printAWTTiles.customTileWidth : 1024, (GLCanvas.this.printAWTTiles.customTileHeight != -1) ? GLCanvas.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (GLCanvas.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            GLCanvas.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(GLCanvas.this, GLCanvas.this.printGLAD);
                            glDrawable = GLCanvas.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    GLCanvas.this.printAWTTiles.setGLOrientation(GLCanvas.this.printGLAD.isGLOriented(), GLCanvas.this.printGLAD.isGLOriented());
                    GLCanvas.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    GLCanvas.this.printAWTTiles.renderer.attachAutoDrawable(GLCanvas.this.printGLAD);
                    if (GLCanvas.DEBUG) {
                        System.err.println("AWT print.setup " + GLCanvas.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + GLCanvas.this.printGLAD.getSurfaceWidth() + "x" + GLCanvas.this.printGLAD.getSurfaceHeight() + ", " + GLCanvas.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    if (GLCanvas.DEBUG) {
                        System.err.println("AWT print.release " + GLCanvas.this.printAWTTiles);
                    }
                    GLCanvas.this.printAWTTiles.dispose();
                    GLCanvas.this.printAWTTiles = null;
                    if (GLCanvas.this.printGLAD != GLCanvas.this) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(GLCanvas.this.printGLAD, GLCanvas.this);
                        GLCanvas.this.printGLAD.destroy();
                    }
                    GLCanvas.this.printGLAD = null;
                    if (null != GLCanvas.this.printAnimator) {
                        GLCanvas.this.printAnimator.add(GLCanvas.this);
                        GLCanvas.this.printAnimator = null;
                    }
                    GLCanvas.this.sendReshape = true;
                    GLCanvas.this.printActive = false;
                    GLCanvas.this.display();
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        this.destroyOnEDTAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    final GLAnimatorControl animator = GLCanvas.this.getAnimator();
                    if (GLCanvas.DEBUG) {
                        System.err.println(GLCanvas.getThreadName() + ": Info: destroyOnEDTAction() - START, hasContext " + (null != GLCanvas.this.context) + ", hasDrawable " + (null != GLCanvas.this.drawable) + ", " + animator);
                    }
                    final boolean b = null != animator && animator.pause();
                    GLException ex = null;
                    if (null != GLCanvas.this.context) {
                        if (GLCanvas.this.context.isCreated()) {
                            try {
                                GLCanvas.this.helper.disposeGL(GLCanvas.this, GLCanvas.this.context, true);
                                if (GLCanvas.DEBUG) {
                                    System.err.println(GLCanvas.getThreadName() + ": destroyOnEDTAction() - post ctx: " + GLCanvas.this.context);
                                }
                            }
                            catch (GLException ex2) {
                                ex = ex2;
                            }
                        }
                        GLCanvas.this.context = null;
                    }
                    Throwable t = null;
                    if (null != GLCanvas.this.drawable) {
                        try {
                            GLCanvas.this.drawable.setRealized(false);
                            if (GLCanvas.DEBUG) {
                                System.err.println(GLCanvas.getThreadName() + ": destroyOnEDTAction() - post drawable: " + GLCanvas.this.drawable);
                            }
                        }
                        catch (Throwable t2) {
                            t = t2;
                        }
                        GLCanvas.this.drawable = null;
                    }
                    if (b) {
                        animator.resume();
                    }
                    if (null != ex) {
                        throw ex;
                    }
                    if (null != t) {
                        throw GLException.newGLException(t);
                    }
                    if (GLCanvas.DEBUG) {
                        System.err.println(GLCanvas.getThreadName() + ": dispose() - END, animator " + animator);
                    }
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        this.disposeJAWTWindowAndAWTDeviceOnEDT = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.context = null;
                GLCanvas.this.drawable = null;
                if (null != GLCanvas.this.jawtWindow) {
                    GLCanvas.this.jawtWindow.destroy();
                    if (GLCanvas.DEBUG) {
                        System.err.println(GLCanvas.getThreadName() + ": GLCanvas.disposeJAWTWindowAndAWTDeviceOnEDT(): post JAWTWindow: " + GLCanvas.this.jawtWindow);
                    }
                    GLCanvas.this.jawtWindow = null;
                }
                GLCanvas.this.hasPixelScale[0] = 1.0f;
                GLCanvas.this.hasPixelScale[1] = 1.0f;
                GLCanvas.this.minPixelScale[0] = 1.0f;
                GLCanvas.this.minPixelScale[1] = 1.0f;
                GLCanvas.this.maxPixelScale[0] = 1.0f;
                GLCanvas.this.maxPixelScale[1] = 1.0f;
                if (null != GLCanvas.this.awtConfig) {
                    final AbstractGraphicsDevice device = GLCanvas.this.awtConfig.getNativeGraphicsConfiguration().getScreen().getDevice();
                    String string;
                    if (GLCanvas.DEBUG) {
                        string = device.toString();
                    }
                    else {
                        string = null;
                    }
                    final boolean close = device.close();
                    if (GLCanvas.DEBUG) {
                        System.err.println(GLCanvas.getThreadName() + ": GLCanvas.disposeJAWTWindowAndAWTDeviceOnEDT(): post GraphicsDevice: " + string + ", result: " + close);
                    }
                }
                GLCanvas.this.awtConfig = null;
            }
        };
        this.initAction = new Runnable() {
            @Override
            public void run() {
                GLCanvas.this.helper.init(GLCanvas.this, !GLCanvas.this.sendReshape);
            }
        };
        this.displayAction = new Runnable() {
            @Override
            public void run() {
                if (GLCanvas.this.sendReshape) {
                    if (GLCanvas.DEBUG) {
                        System.err.println(GLCanvas.getThreadName() + ": Reshape: " + GLCanvas.this.getSurfaceWidth() + "x" + GLCanvas.this.getSurfaceHeight());
                    }
                    GLCanvas.this.helper.reshape(GLCanvas.this, 0, 0, GLCanvas.this.getSurfaceWidth(), GLCanvas.this.getSurfaceHeight());
                    GLCanvas.this.sendReshape = false;
                }
                GLCanvas.this.helper.display(GLCanvas.this);
            }
        };
        this.displayOnEDTAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    if (null != GLCanvas.this.drawable && GLCanvas.this.drawable.isRealized()) {
                        if (GLCanvas.this.updatePixelScale()) {
                            GLCanvas.this.reshapeImpl(GLCanvas.this.getWidth(), GLCanvas.this.getHeight());
                        }
                        GLCanvas.this.helper.invokeGL(GLCanvas.this.drawable, GLCanvas.this.context, GLCanvas.this.displayAction, GLCanvas.this.initAction);
                    }
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        this.swapBuffersOnEDTAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$200 = GLCanvas.this.lock;
                access$200.lock();
                try {
                    if (null != GLCanvas.this.drawable && GLCanvas.this.drawable.isRealized()) {
                        GLCanvas.this.drawable.swapBuffers();
                    }
                }
                finally {
                    access$200.unlock();
                }
            }
        };
        if (null == glCapabilitiesImmutable) {
            this.capsReqUser = new GLCapabilities(GLProfile.getDefault(GLProfile.getDefaultDevice()));
        }
        else {
            this.capsReqUser = (GLCapabilitiesImmutable)glCapabilitiesImmutable.cloneMutable();
        }
        if (!this.capsReqUser.isOnscreen()) {
            this.setShallUseOffscreenLayer(true);
        }
        this.awtDeviceReq = awtDeviceReq;
        this.chooser = chooser;
        this.addHierarchyListener(this.hierarchyListener);
        this.isShowing = this.isShowing();
    }
    
    @Override
    public final void setSharedContext(final GLContext glContext) throws IllegalStateException {
        this.helper.setSharedContext(this.context, glContext);
    }
    
    @Override
    public final void setSharedAutoDrawable(final GLAutoDrawable glAutoDrawable) throws IllegalStateException {
        this.helper.setSharedAutoDrawable(this, glAutoDrawable);
    }
    
    @Override
    public final Object getUpstreamWidget() {
        return this;
    }
    
    @Override
    public final RecursiveLock getUpstreamLock() {
        return this.lock;
    }
    
    @Override
    public final boolean isThreadGLCapable() {
        return Threading.isOpenGLThread();
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
        final JAWTWindow jawtWindow = this.jawtWindow;
        return null != jawtWindow && jawtWindow.isOffscreenLayerSurfaceEnabled();
    }
    
    @Override
    public GraphicsConfiguration getGraphicsConfiguration() {
        final GraphicsConfiguration graphicsConfiguration = super.getGraphicsConfiguration();
        if (Beans.isDesignTime()) {
            return graphicsConfiguration;
        }
        final GraphicsConfiguration graphicsConfiguration2 = (null != this.awtConfig) ? this.awtConfig.getAWTGraphicsConfiguration() : null;
        if (null != graphicsConfiguration && null != graphicsConfiguration2 && !graphicsConfiguration2.equals(graphicsConfiguration)) {
            if (!graphicsConfiguration2.getDevice().getIDstring().equals(graphicsConfiguration.getDevice().getIDstring())) {
                final AWTGraphicsConfiguration chooseGraphicsConfiguration = this.chooseGraphicsConfiguration((GLCapabilitiesImmutable)this.awtConfig.getChosenCapabilities(), (GLCapabilitiesImmutable)this.awtConfig.getRequestedCapabilities(), this.chooser, graphicsConfiguration.getDevice());
                final GraphicsConfiguration awtGraphicsConfiguration = chooseGraphicsConfiguration.getAWTGraphicsConfiguration();
                final boolean equals = chooseGraphicsConfiguration.getChosenCapabilities().equals(this.awtConfig.getChosenCapabilities());
                if (GLCanvas.DEBUG) {
                    System.err.println(getThreadName() + ": getGraphicsConfiguration() Info: Changed GC and GD");
                    System.err.println("Created Config (n): Old     GC " + graphicsConfiguration2);
                    System.err.println("Created Config (n): Old     GD " + graphicsConfiguration2.getDevice().getIDstring());
                    System.err.println("Created Config (n): Parent  GC " + graphicsConfiguration);
                    System.err.println("Created Config (n): Parent  GD " + graphicsConfiguration.getDevice().getIDstring());
                    System.err.println("Created Config (n): New     GC " + awtGraphicsConfiguration);
                    System.err.println("Created Config (n): New     GD " + awtGraphicsConfiguration.getDevice().getIDstring());
                    System.err.println("Created Config (n): Old     CF " + this.awtConfig);
                    System.err.println("Created Config (n): New     CF " + chooseGraphicsConfiguration);
                    System.err.println("Created Config (n): EQUALS CAPS " + equals);
                }
                if (null != awtGraphicsConfiguration) {
                    if (!equals && GLAutoDrawable.SCREEN_CHANGE_ACTION_ENABLED) {
                        this.destroyImpl(true);
                        this.setAWTGraphicsConfiguration(chooseGraphicsConfiguration);
                        this.createJAWTDrawableAndContext();
                        this.validateGLDrawable();
                    }
                    else {
                        this.setAWTGraphicsConfiguration(chooseGraphicsConfiguration);
                    }
                    if (GLCanvas.DEBUG) {
                        System.err.println(getThreadName() + ": Info: getGraphicsConfiguration - end.01: newGC " + awtGraphicsConfiguration);
                    }
                    return awtGraphicsConfiguration;
                }
                if (GLCanvas.DEBUG) {
                    System.err.println(getThreadName() + ": Info: getGraphicsConfiguration - end.00: oldGC " + graphicsConfiguration2);
                }
            }
            return graphicsConfiguration2;
        }
        if (null == graphicsConfiguration) {
            return graphicsConfiguration2;
        }
        return graphicsConfiguration;
    }
    
    @Override
    public GLContext createContext(final GLContext glContext) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            if (this.drawable != null) {
                final GLContext context = this.drawable.createContext(glContext);
                context.setContextCreationFlags(this.additionalCtxCreationFlags);
                return context;
            }
            return null;
        }
        finally {
            lock.unlock();
        }
    }
    
    private final void setRealizedImpl(final boolean realized) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final GLDrawableImpl drawable = this.drawable;
            if (null == drawable || realized == drawable.isRealized() || (realized && (0 >= drawable.getSurfaceWidth() || 0 >= drawable.getSurfaceHeight()))) {
                return;
            }
            drawable.setRealized(realized);
            if (realized && drawable.isRealized()) {
                this.sendReshape = true;
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public final void setRealized(final boolean b) {
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), false, true, b ? this.realizeOnEDTAction : this.unrealizeOnEDTAction);
    }
    
    @Override
    public boolean isRealized() {
        final GLDrawableImpl drawable = this.drawable;
        return null != drawable && drawable.isRealized();
    }
    
    @Override
    public WindowClosingMode getDefaultCloseOperation() {
        return this.awtWindowClosingProtocol.getDefaultCloseOperation();
    }
    
    @Override
    public WindowClosingMode setDefaultCloseOperation(final WindowClosingMode defaultCloseOperation) {
        return this.awtWindowClosingProtocol.setDefaultCloseOperation(defaultCloseOperation);
    }
    
    @Override
    public void display() {
        if (!this.validateGLDrawable()) {
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": Info: GLCanvas display - skipped GL render, drawable not valid yet");
            }
            return;
        }
        if (this.isShowing && !this.printActive) {
            Threading.invoke(true, this.displayOnEDTAction, this.getTreeLock());
        }
    }
    
    @Override
    public void destroy() {
        this.destroyImpl(false);
    }
    
    protected void destroyImpl(final boolean b) {
        Threading.invoke(true, this.destroyOnEDTAction, this.getTreeLock());
        if (b) {
            AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.disposeJAWTWindowAndAWTDeviceOnEDT);
        }
    }
    
    @Override
    public void paint(final Graphics graphics) {
        if (Beans.isDesignTime()) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            final FontMetrics fontMetrics = graphics.getFontMetrics();
            String s = this.getName();
            if (s == null) {
                s = this.getClass().getName();
                final int lastIndex = s.lastIndexOf(46);
                if (lastIndex >= 0) {
                    s = s.substring(lastIndex + 1);
                }
            }
            final Rectangle2D stringBounds = fontMetrics.getStringBounds(s, graphics);
            graphics.setColor(Color.WHITE);
            graphics.drawString(s, (int)((this.getWidth() - stringBounds.getWidth()) / 2.0), (int)((this.getHeight() + stringBounds.getHeight()) / 2.0));
        }
        else if (!this.helper.isAnimatorAnimatingOnOtherThread()) {
            this.display();
        }
    }
    
    @Override
    public void addNotify() {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final boolean designTime = Beans.isDesignTime();
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": Info: addNotify - start, bounds: " + this.getBounds() + ", isBeansDesignTime " + designTime);
            }
            if (designTime) {
                super.addNotify();
            }
            else {
                this.disableBackgroundErase();
                GraphicsDevice graphicsDevice;
                if (null == this.awtDeviceReq) {
                    final GraphicsConfiguration graphicsConfiguration = super.getGraphicsConfiguration();
                    if (null == graphicsConfiguration) {
                        throw new GLException("Error: NULL AWT GraphicsConfiguration");
                    }
                    graphicsDevice = graphicsConfiguration.getDevice();
                }
                else {
                    graphicsDevice = this.awtDeviceReq;
                    this.awtDeviceReq = null;
                }
                final AWTGraphicsConfiguration chooseGraphicsConfiguration = this.chooseGraphicsConfiguration(this.capsReqUser, this.capsReqUser, this.chooser, graphicsDevice);
                if (null == chooseGraphicsConfiguration) {
                    throw new GLException("Error: NULL AWTGraphicsConfiguration");
                }
                this.setAWTGraphicsConfiguration(chooseGraphicsConfiguration);
                super.addNotify();
                this.disableBackgroundErase();
                this.createJAWTDrawableAndContext();
            }
            this.awtWindowClosingProtocol.addClosingListener();
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": Info: addNotify - end: peer: " + this.getPeer());
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public final boolean setSurfaceScale(final float[] array) {
        System.arraycopy(array, 0, this.reqPixelScale, 0, 2);
        if (this.isRealized() && this.isShowing) {
            Threading.invoke(true, this.setSurfaceScaleOnEDTAction, this.getTreeLock());
            return true;
        }
        return false;
    }
    
    private final boolean setSurfaceScaleImpl(final ScalableSurface scalableSurface) {
        if (scalableSurface.setSurfaceScale(this.reqPixelScale)) {
            scalableSurface.getCurrentSurfaceScale(this.hasPixelScale);
            return true;
        }
        return false;
    }
    
    private final boolean updatePixelScale() {
        if (this.jawtWindow.hasPixelScaleChanged()) {
            this.jawtWindow.getMaximumSurfaceScale(this.maxPixelScale);
            this.jawtWindow.getMinimumSurfaceScale(this.minPixelScale);
            return this.setSurfaceScaleImpl(this.jawtWindow);
        }
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
    public float[] getMaximumSurfaceScale(final float[] array) {
        System.arraycopy(this.maxPixelScale, 0, array, 0, 2);
        return array;
    }
    
    private void createJAWTDrawableAndContext() {
        if (!Beans.isDesignTime()) {
            (this.jawtWindow = (JAWTWindow)NativeWindowFactory.getNativeWindow(this, this.awtConfig)).setShallUseOffscreenLayer(this.shallUseOffscreenLayer);
            this.jawtWindow.lockSurface();
            try {
                this.jawtWindow.setSurfaceScale(this.reqPixelScale);
                this.createContextImpl(this.drawable = (GLDrawableImpl)GLDrawableFactory.getFactory(this.capsReqUser.getGLProfile()).createGLDrawable(this.jawtWindow));
                this.jawtWindow.getCurrentSurfaceScale(this.hasPixelScale);
                this.jawtWindow.getMinimumSurfaceScale(this.minPixelScale);
                this.jawtWindow.getMaximumSurfaceScale(this.maxPixelScale);
            }
            finally {
                this.jawtWindow.unlockSurface();
            }
        }
    }
    
    private boolean createContextImpl(final GLDrawable glDrawable) {
        final GLContext[] array = { null };
        if (!this.helper.isSharedGLContextPending(array)) {
            (this.context = (GLContextImpl)glDrawable.createContext(array[0])).setContextCreationFlags(this.additionalCtxCreationFlags);
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": Context created: has shared " + (null != array[0]));
            }
            return true;
        }
        if (GLCanvas.DEBUG) {
            System.err.println(getThreadName() + ": Context !created: pending share");
        }
        return false;
    }
    
    private boolean validateGLDrawable() {
        if (Beans.isDesignTime() || !this.isDisplayable()) {
            return false;
        }
        final GLDrawableImpl drawable = this.drawable;
        if (null != drawable) {
            boolean b = drawable.isRealized();
            if (!b) {
                if (0 >= drawable.getSurfaceWidth() || 0 >= drawable.getSurfaceHeight()) {
                    return false;
                }
                this.setRealized(true);
                b = drawable.isRealized();
                if (GLCanvas.DEBUG) {
                    System.err.println(getThreadName() + ": Realized Drawable: isRealized " + b + ", " + drawable.toString());
                }
            }
            if (b && null == this.context) {
                b = this.createContextImpl(drawable);
            }
            return b;
        }
        return false;
    }
    
    private void setAWTGraphicsConfiguration(final AWTGraphicsConfiguration awtGraphicsConfiguration) {
        this.awtConfig = awtGraphicsConfiguration;
        if (null != this.jawtWindow) {
            this.jawtWindow.setAWTGraphicsConfiguration(awtGraphicsConfiguration);
        }
    }
    
    @Override
    public void removeNotify() {
        if (GLCanvas.DEBUG) {
            System.err.println(getThreadName() + ": Info: removeNotify - start");
        }
        this.awtWindowClosingProtocol.removeClosingListener();
        if (Beans.isDesignTime()) {
            super.removeNotify();
        }
        else {
            try {
                this.destroyImpl(true);
            }
            finally {
                super.removeNotify();
            }
        }
        if (GLCanvas.DEBUG) {
            System.err.println(getThreadName() + ": Info: removeNotify - end, peer: " + this.getPeer());
        }
    }
    
    @Override
    public void reshape(final int n, final int n2, final int n3, final int n4) {
        synchronized (this.getTreeLock()) {
            super.reshape(n, n2, n3, n4);
            this.reshapeImpl(n3, n4);
        }
    }
    
    private void reshapeImpl(final int n, final int n2) {
        final int scale = SurfaceScaleUtils.scale(n, this.hasPixelScale[0]);
        final int scale2 = SurfaceScaleUtils.scale(n2, this.hasPixelScale[1]);
        if (GLCanvas.DEBUG) {
            final NativeSurface nativeSurface = this.getNativeSurface();
            System.err.println(getThreadName() + ": GLCanvas.reshape.0 " + this.getName() + " resize" + (this.printActive ? "WithinPrint" : "") + " [ this " + this.getWidth() + "x" + this.getHeight() + ", pixelScale " + this.getPixelScaleStr() + "] -> " + (this.printActive ? "[skipped] " : "") + n + "x" + n2 + " * " + this.getPixelScaleStr() + " -> " + scale + "x" + scale2 + " - surfaceHandle 0x" + Long.toHexString((null != nativeSurface) ? nativeSurface.getSurfaceHandle() : 0L));
        }
        if (this.validateGLDrawable() && !this.printActive) {
            final GLDrawableImpl drawable = this.drawable;
            if (!drawable.getChosenGLCapabilities().isOnscreen()) {
                final RecursiveLock lock = this.lock;
                lock.lock();
                try {
                    final GLDrawableImpl resizeOffscreenDrawable = GLDrawableHelper.resizeOffscreenDrawable(drawable, this.context, scale, scale2);
                    if (drawable != resizeOffscreenDrawable) {
                        this.drawable = resizeOffscreenDrawable;
                    }
                }
                finally {
                    lock.unlock();
                }
            }
            this.sendReshape = true;
        }
    }
    
    @Override
    public void update(final Graphics graphics) {
        this.paint(graphics);
    }
    
    @Override
    public void setupPrint(final double n, final double n2, final int n3, final int n4, final int n5) {
        this.printActive = true;
        this.printAWTTiles = new AWTTilePainter(new TileRenderer(), this.isOpaque() ? 3 : 4, n, n2, n3, n4, n5, GLCanvas.DEBUG);
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.setupPrintOnEDT);
    }
    
    @Override
    public void releasePrint() {
        if (!this.printActive || null == this.printGLAD) {
            throw new IllegalStateException("setupPrint() not called");
        }
        this.sendReshape = false;
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.releasePrintOnEDT);
    }
    
    @Override
    public void print(final Graphics graphics) {
        if (!this.printActive || null == this.printGLAD) {
            throw new IllegalStateException("setupPrint() not called");
        }
        if (GLCanvas.DEBUG && !EventQueue.isDispatchThread()) {
            System.err.println(getThreadName() + ": Warning: GLCanvas print - not called from AWT-EDT");
        }
        this.sendReshape = false;
        final Graphics2D graphics2D = (Graphics2D)graphics;
        try {
            this.printAWTTiles.setupGraphics2DAndClipBounds(graphics2D, this.getWidth(), this.getHeight());
            final TileRenderer renderer = this.printAWTTiles.renderer;
            if (GLCanvas.DEBUG) {
                System.err.println("AWT print.0: " + renderer);
            }
            if (!renderer.eot()) {
                try {
                    do {
                        if (this.printGLAD != this) {
                            renderer.display();
                        }
                        else {
                            Threading.invoke(true, this.displayOnEDTAction, this.getTreeLock());
                        }
                    } while (!renderer.eot());
                    if (GLCanvas.DEBUG) {
                        System.err.println("AWT print.1: " + this.printAWTTiles);
                    }
                }
                finally {
                    renderer.reset();
                    this.printAWTTiles.resetGraphics2D();
                }
            }
        }
        catch (NoninvertibleTransformException ex) {
            System.err.println("Caught: Inversion failed of: " + graphics2D.getTransform());
            ex.printStackTrace();
        }
        if (GLCanvas.DEBUG) {
            System.err.println("AWT print.X: " + this.printAWTTiles);
        }
    }
    
    @Override
    public void addGLEventListener(final GLEventListener glEventListener) {
        this.helper.addGLEventListener(glEventListener);
    }
    
    @Override
    public void addGLEventListener(final int n, final GLEventListener glEventListener) throws IndexOutOfBoundsException {
        this.helper.addGLEventListener(n, glEventListener);
    }
    
    @Override
    public int getGLEventListenerCount() {
        return this.helper.getGLEventListenerCount();
    }
    
    @Override
    public GLEventListener getGLEventListener(final int n) throws IndexOutOfBoundsException {
        return this.helper.getGLEventListener(n);
    }
    
    @Override
    public boolean areAllGLEventListenerInitialized() {
        return this.helper.areAllGLEventListenerInitialized();
    }
    
    @Override
    public boolean getGLEventListenerInitState(final GLEventListener glEventListener) {
        return this.helper.getGLEventListenerInitState(glEventListener);
    }
    
    @Override
    public void setGLEventListenerInitState(final GLEventListener glEventListener, final boolean b) {
        this.helper.setGLEventListenerInitState(glEventListener, b);
    }
    
    @Override
    public GLEventListener disposeGLEventListener(final GLEventListener glEventListener, final boolean b) {
        final DisposeGLEventListenerAction disposeGLEventListenerAction = new DisposeGLEventListenerAction(glEventListener, b);
        Threading.invoke(true, disposeGLEventListenerAction, this.getTreeLock());
        return disposeGLEventListenerAction.listener;
    }
    
    @Override
    public GLEventListener removeGLEventListener(final GLEventListener glEventListener) {
        return this.helper.removeGLEventListener(glEventListener);
    }
    
    @Override
    public void setAnimator(final GLAnimatorControl animator) {
        this.helper.setAnimator(animator);
    }
    
    @Override
    public GLAnimatorControl getAnimator() {
        return this.helper.getAnimator();
    }
    
    @Override
    public final Thread setExclusiveContextThread(final Thread thread) throws GLException {
        return this.helper.setExclusiveContextThread(thread, this.context);
    }
    
    @Override
    public final Thread getExclusiveContextThread() {
        return this.helper.getExclusiveContextThread();
    }
    
    @Override
    public boolean invoke(final boolean b, final GLRunnable glRunnable) throws IllegalStateException {
        return this.helper.invoke(this, b, glRunnable);
    }
    
    @Override
    public boolean invoke(final boolean b, final List<GLRunnable> list) throws IllegalStateException {
        return this.helper.invoke(this, b, list);
    }
    
    @Override
    public void flushGLRunnables() {
        this.helper.flushGLRunnables();
    }
    
    @Override
    public GLContext setContext(final GLContext glContext, final boolean b) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final GLContextImpl context = this.context;
            GLDrawableHelper.switchContext(this.drawable, context, b, glContext, this.additionalCtxCreationFlags);
            this.context = (GLContextImpl)glContext;
            return context;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public final GLDrawable getDelegatedDrawable() {
        return this.drawable;
    }
    
    @Override
    public GLContext getContext() {
        return this.context;
    }
    
    @Override
    public GL getGL() {
        if (Beans.isDesignTime()) {
            return null;
        }
        final GLContextImpl context = this.context;
        return (context == null) ? null : context.getGL();
    }
    
    @Override
    public GL setGL(final GL gl) {
        final GLContextImpl context = this.context;
        if (context != null) {
            context.setGL(gl);
            return gl;
        }
        return null;
    }
    
    @Override
    public void setAutoSwapBufferMode(final boolean autoSwapBufferMode) {
        this.helper.setAutoSwapBufferMode(autoSwapBufferMode);
    }
    
    @Override
    public boolean getAutoSwapBufferMode() {
        return this.helper.getAutoSwapBufferMode();
    }
    
    @Override
    public void swapBuffers() {
        Threading.invoke(true, this.swapBuffersOnEDTAction, this.getTreeLock());
    }
    
    @Override
    public void setContextCreationFlags(final int additionalCtxCreationFlags) {
        this.additionalCtxCreationFlags = additionalCtxCreationFlags;
        final GLContextImpl context = this.context;
        if (null != context) {
            context.setContextCreationFlags(this.additionalCtxCreationFlags);
        }
    }
    
    @Override
    public int getContextCreationFlags() {
        return this.additionalCtxCreationFlags;
    }
    
    @Override
    public GLProfile getGLProfile() {
        return this.capsReqUser.getGLProfile();
    }
    
    @Override
    public GLCapabilitiesImmutable getChosenGLCapabilities() {
        if (Beans.isDesignTime()) {
            return this.capsReqUser;
        }
        if (null == this.awtConfig) {
            throw new GLException("No AWTGraphicsConfiguration: " + this);
        }
        return (GLCapabilitiesImmutable)this.awtConfig.getChosenCapabilities();
    }
    
    @Override
    public GLCapabilitiesImmutable getRequestedGLCapabilities() {
        if (null == this.awtConfig) {
            return this.capsReqUser;
        }
        return (GLCapabilitiesImmutable)this.awtConfig.getRequestedCapabilities();
    }
    
    @Override
    public int getSurfaceWidth() {
        return SurfaceScaleUtils.scale(this.getWidth(), this.hasPixelScale[0]);
    }
    
    @Override
    public int getSurfaceHeight() {
        return SurfaceScaleUtils.scale(this.getHeight(), this.hasPixelScale[1]);
    }
    
    @Override
    public boolean isGLOriented() {
        final GLDrawableImpl drawable = this.drawable;
        return null == drawable || drawable.isGLOriented();
    }
    
    @Override
    public NativeSurface getNativeSurface() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getNativeSurface() : null;
    }
    
    @Override
    public long getHandle() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getHandle() : 0L;
    }
    
    @Override
    public GLDrawableFactory getFactory() {
        final GLDrawableImpl drawable = this.drawable;
        return (null != drawable) ? drawable.getFactory() : null;
    }
    
    @Override
    public String toString() {
        final GLDrawableImpl drawable = this.drawable;
        return "AWT-GLCanvas[Realized " + this.isRealized() + ",\n\t" + ((null != drawable) ? drawable.getClass().getName() : "null-drawable") + ",\n\tFactory   " + this.getFactory() + ",\n\thandle    0x" + Long.toHexString(this.getHandle()) + ",\n\tDrawable size " + ((null != drawable) ? drawable.getSurfaceWidth() : -1) + "x" + ((null != drawable) ? drawable.getSurfaceHeight() : -1) + " surface[" + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + "]" + ",\n\tAWT[pos " + this.getX() + "/" + this.getY() + ", size " + this.getWidth() + "x" + this.getHeight() + ",\n\tvisible " + this.isVisible() + ", displayable " + this.isDisplayable() + ", showing " + this.isShowing + ",\n\t" + this.awtConfig + "]]";
    }
    
    private final String getPixelScaleStr() {
        return "[" + this.hasPixelScale[0] + ", " + this.hasPixelScale[1] + "]";
    }
    
    private void disableBackgroundErase() {
        if (!GLCanvas.disableBackgroundEraseInitialized) {
            try {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        try {
                            Serializable s = GLCanvas.this.getToolkit().getClass();
                            while (s != null && GLCanvas.disableBackgroundEraseMethod == null) {
                                try {
                                    GLCanvas.disableBackgroundEraseMethod = ((Class)s).getDeclaredMethod("disableBackgroundErase", Canvas.class);
                                    GLCanvas.disableBackgroundEraseMethod.setAccessible(true);
                                }
                                catch (Exception ex) {
                                    s = ((Class<? extends Toolkit>)s).getSuperclass();
                                }
                            }
                        }
                        catch (Exception ex2) {}
                        return null;
                    }
                });
            }
            catch (Exception ex2) {}
            GLCanvas.disableBackgroundEraseInitialized = true;
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": GLCanvas: TK disableBackgroundErase method found: " + (null != GLCanvas.disableBackgroundEraseMethod));
            }
        }
        if (GLCanvas.disableBackgroundEraseMethod != null) {
            Object o = null;
            try {
                GLCanvas.disableBackgroundEraseMethod.invoke(this.getToolkit(), this);
            }
            catch (Exception ex) {
                o = ex;
            }
            if (GLCanvas.DEBUG) {
                System.err.println(getThreadName() + ": GLCanvas: TK disableBackgroundErase error: " + o);
            }
        }
    }
    
    private AWTGraphicsConfiguration chooseGraphicsConfiguration(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesImmutable glCapabilitiesImmutable2, final GLCapabilitiesChooser glCapabilitiesChooser, final GraphicsDevice graphicsDevice) {
        if (Beans.isDesignTime()) {
            return null;
        }
        if (null == graphicsDevice) {
            throw new GLException("Error: NULL AWT GraphicsDevice");
        }
        final AbstractGraphicsScreen screenDevice = AWTGraphicsScreen.createScreenDevice(graphicsDevice, 0);
        AWTGraphicsConfiguration awtGraphicsConfiguration;
        if (EventQueue.isDispatchThread() || Thread.holdsLock(this.getTreeLock())) {
            awtGraphicsConfiguration = (AWTGraphicsConfiguration)GraphicsConfigurationFactory.getFactory(AWTGraphicsDevice.class, GLCapabilitiesImmutable.class).chooseGraphicsConfiguration(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, screenDevice, 0);
        }
        else {
            try {
                final ArrayList<AWTGraphicsConfiguration> list = new ArrayList<AWTGraphicsConfiguration>(1);
                EventQueue.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        list.add(GraphicsConfigurationFactory.getFactory(AWTGraphicsDevice.class, GLCapabilitiesImmutable.class).chooseGraphicsConfiguration(glCapabilitiesImmutable, glCapabilitiesImmutable2, glCapabilitiesChooser, screenDevice, 0));
                    }
                });
                awtGraphicsConfiguration = ((list.size() > 0) ? list.get(0) : null);
            }
            catch (InvocationTargetException ex) {
                throw new GLException(ex.getTargetException());
            }
            catch (InterruptedException ex2) {
                throw new GLException(ex2);
            }
        }
        if (null == awtGraphicsConfiguration) {
            throw new GLException("Error: Couldn't fetch AWTGraphicsConfiguration");
        }
        return awtGraphicsConfiguration;
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(JoglVersion.getInstance());
        System.err.println(JoglVersion.getDefaultOpenGLInfo(null, null, true).toString());
        final GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault(GLProfile.getDefaultDevice()));
        final Frame frame = new Frame("JOGL AWT Test");
        final GLCanvas glCanvas = new GLCanvas(glCapabilities);
        frame.add(glCanvas);
        frame.setSize(128, 128);
        glCanvas.addGLEventListener(new GLEventListener() {
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
                System.err.println(JoglVersion.getGLInfo(glAutoDrawable.getGL(), null));
            }
            
            @Override
            public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
            }
            
            @Override
            public void display(final GLAutoDrawable glAutoDrawable) {
            }
            
            @Override
            public void dispose(final GLAutoDrawable glAutoDrawable) {
            }
        });
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame.setVisible(true);
                }
            });
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        glCanvas.display();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame.dispose();
                }
            });
        }
        catch (Throwable t2) {
            t2.printStackTrace();
        }
    }
    
    static {
        DEBUG = Debug.debug("GLCanvas");
    }
    
    private class DisposeGLEventListenerAction implements Runnable
    {
        GLEventListener listener;
        private final boolean remove;
        
        private DisposeGLEventListenerAction(final GLEventListener listener, final boolean remove) {
            this.listener = listener;
            this.remove = remove;
        }
        
        @Override
        public void run() {
            final RecursiveLock access$200 = GLCanvas.this.lock;
            access$200.lock();
            try {
                this.listener = GLCanvas.this.helper.disposeGLEventListener(GLCanvas.this, GLCanvas.this.drawable, GLCanvas.this.context, this.listener, this.remove);
            }
            finally {
                access$200.unlock();
            }
        }
    }
}
