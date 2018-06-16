// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.awt;

import java.awt.Rectangle;
import com.jogamp.opengl.GL2;
import java.awt.GraphicsConfiguration;
import jogamp.opengl.GLDrawableImpl;
import java.awt.image.ImageObserver;
import java.awt.Image;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.nativewindow.util.PixelFormat;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;
import com.jogamp.opengl.util.texture.TextureState;
import com.jogamp.opengl.GLFBODrawable;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.opengl.util.GLPixelStorageModes;
import jogamp.opengl.GLContextImpl;
import jogamp.opengl.util.glsl.GLSLTextureRaster;
import com.jogamp.opengl.FBObject;
import java.nio.IntBuffer;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import jogamp.opengl.awt.Java2D;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.opengl.GL;
import java.util.List;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.GLEventListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Graphics2D;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import com.jogamp.opengl.util.TileRenderer;
import jogamp.nativewindow.jawt.JAWTUtil;
import com.jogamp.nativewindow.NativeSurface;
import jogamp.nativewindow.WrappedSurface;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.Color;
import java.beans.Beans;
import java.awt.Graphics;
import com.jogamp.opengl.Threading;
import java.awt.EventQueue;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLContext;
import jogamp.nativewindow.SurfaceScaleUtils;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.util.GLDrawableUtil;
import com.jogamp.opengl.GLCapabilities;
import java.awt.Component;
import java.awt.event.HierarchyEvent;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.opengl.GLException;
import jogamp.opengl.awt.AWTTilePainter;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.nativewindow.awt.AWTWindowClosingProtocol;
import java.awt.event.HierarchyListener;
import com.jogamp.opengl.GLCapabilitiesChooser;
import jogamp.opengl.GLDrawableFactoryImpl;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import jogamp.opengl.GLDrawableHelper;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.opengl.util.awt.AWTGLPixelBuffer;
import com.jogamp.nativewindow.ScalableSurface;
import com.jogamp.opengl.GLSharedContextSetter;
import com.jogamp.nativewindow.awt.AWTPrintLifecycle;
import com.jogamp.nativewindow.WindowClosingProtocol;
import javax.swing.JPanel;

public class GLJPanel extends JPanel implements AWTGLAutoDrawable, WindowClosingProtocol, AWTPrintLifecycle, GLSharedContextSetter, ScalableSurface
{
    private static final boolean DEBUG;
    private static final boolean DEBUG_FRAMES;
    private static final boolean DEBUG_VIEWPORT;
    private static final boolean USE_GLSL_TEXTURE_RASTERIZER;
    private static final boolean SKIP_VERTICAL_FLIP_DEFAULT;
    private static final boolean java2dOGLEnabledByProp;
    private static final boolean useJava2DGLPipeline;
    private static boolean java2DGLPipelineOK;
    private static AWTGLPixelBuffer.SingleAWTGLPixelBufferProvider singleAWTGLPixelBufferProvider;
    private final RecursiveLock lock;
    private final GLDrawableHelper helper;
    private boolean autoSwapBufferMode;
    private volatile boolean isInitialized;
    private AWTGLPixelBuffer.AWTGLPixelBufferProvider customPixelBufferProvider;
    private volatile GLCapabilitiesImmutable reqOffscreenCaps;
    private volatile GLDrawableFactoryImpl factory;
    private final GLCapabilitiesChooser chooser;
    private int additionalCtxCreationFlags;
    private boolean handleReshape;
    private boolean sendReshape;
    private final float[] minPixelScale;
    private final float[] maxPixelScale;
    private final float[] hasPixelScale;
    private final float[] reqPixelScale;
    private int reshapeWidth;
    private int reshapeHeight;
    private int panelWidth;
    private int panelHeight;
    private int viewportX;
    private int viewportY;
    private int requestedTextureUnit;
    private volatile Backend backend;
    private boolean skipGLOrientationVerticalFlip;
    private final Updater updater;
    private volatile boolean isShowing;
    private final HierarchyListener hierarchyListener;
    private final AWTWindowClosingProtocol awtWindowClosingProtocol;
    private final Runnable setSurfaceScaleAction;
    private volatile boolean printActive;
    private GLAnimatorControl printAnimator;
    private GLAutoDrawable printGLAD;
    private AWTTilePainter printAWTTiles;
    private final Runnable setupPrintOnEDT;
    private final Runnable releasePrintOnEDT;
    private final Object initSync;
    private final Runnable disposeAction;
    private final Runnable updaterInitAction;
    private final Runnable updaterDisplayAction;
    private final Runnable updaterPlainDisplayAction;
    private final Runnable paintImmediatelyAction;
    
    private static synchronized AWTGLPixelBuffer.SingleAWTGLPixelBufferProvider getSingleAWTGLPixelBufferProvider() {
        if (null == GLJPanel.singleAWTGLPixelBufferProvider) {
            GLJPanel.singleAWTGLPixelBufferProvider = new AWTGLPixelBuffer.SingleAWTGLPixelBufferProvider(true);
        }
        return GLJPanel.singleAWTGLPixelBufferProvider;
    }
    
    private boolean oglPipelineUsable() {
        return null == this.customPixelBufferProvider && GLJPanel.useJava2DGLPipeline && GLJPanel.java2DGLPipelineOK;
    }
    
    public GLJPanel() throws GLException {
        this((GLCapabilitiesImmutable)null);
    }
    
    public GLJPanel(final GLCapabilitiesImmutable glCapabilitiesImmutable) throws GLException {
        this(glCapabilitiesImmutable, null);
    }
    
    public GLJPanel(final GLCapabilitiesImmutable glCapabilitiesImmutable, final GLCapabilitiesChooser chooser) throws GLException {
        this.lock = LockFactory.createRecursiveLock();
        this.customPixelBufferProvider = null;
        this.additionalCtxCreationFlags = 0;
        this.handleReshape = false;
        this.sendReshape = true;
        this.minPixelScale = new float[] { 1.0f, 1.0f };
        this.maxPixelScale = new float[] { 1.0f, 1.0f };
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.reqPixelScale = new float[] { 0.0f, 0.0f };
        this.panelWidth = 0;
        this.panelHeight = 0;
        this.requestedTextureUnit = 0;
        this.skipGLOrientationVerticalFlip = GLJPanel.SKIP_VERTICAL_FLIP_DEFAULT;
        this.updater = new Updater();
        this.hierarchyListener = new HierarchyListener() {
            @Override
            public void hierarchyChanged(final HierarchyEvent hierarchyEvent) {
                GLJPanel.this.isShowing = GLJPanel.this.isShowing();
            }
        };
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.destroy();
            }
        }, null);
        this.setSurfaceScaleAction = new Runnable() {
            @Override
            public void run() {
                final Backend access$300 = GLJPanel.this.backend;
                if (null != access$300 && GLJPanel.this.setSurfaceScaleImpl(access$300) && !GLJPanel.this.helper.isAnimatorAnimatingOnOtherThread()) {
                    GLJPanel.this.paintImmediatelyAction.run();
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
                final RecursiveLock access$700 = GLJPanel.this.lock;
                access$700.lock();
                try {
                    if (!GLJPanel.this.isInitialized) {
                        GLJPanel.this.initializeBackendImpl();
                    }
                    if (!GLJPanel.this.isInitialized) {
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": Info: GLJPanel setupPrint - skipped GL render, drawable not valid yet");
                        }
                        GLJPanel.this.printActive = false;
                        return;
                    }
                    if (!GLJPanel.this.isVisible()) {
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": Info: GLJPanel setupPrint - skipped GL render, panel not visible");
                        }
                        GLJPanel.this.printActive = false;
                        return;
                    }
                    GLJPanel.this.sendReshape = false;
                    GLJPanel.this.handleReshape = false;
                    GLJPanel.this.printAnimator = GLJPanel.this.helper.getAnimator();
                    if (null != GLJPanel.this.printAnimator) {
                        GLJPanel.this.printAnimator.remove(GLJPanel.this);
                    }
                    GLJPanel.this.printGLAD = GLJPanel.this;
                    final GLCapabilitiesImmutable chosenGLCapabilities = GLJPanel.this.getChosenGLCapabilities();
                    final int numSamples = GLJPanel.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = GLJPanel.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (GLJPanel.this.printAWTTiles.customTileWidth != -1 && GLJPanel.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (GLJPanel.this.printAWTTiles.customTileHeight != -1 && GLJPanel.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(GLJPanel.this.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (b || b2) && swapGLContextSafe;
                    if (GLJPanel.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + GLJPanel.this.printAWTTiles.customTileWidth + "x" + GLJPanel.this.printAWTTiles.customTileHeight + ", scaleMat " + GLJPanel.this.printAWTTiles.scaleMatX + " x " + GLJPanel.this.printAWTTiles.scaleMatY + ", numSamples " + GLJPanel.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + GLJPanel.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (GLJPanel.this.printAWTTiles.customTileWidth != -1) ? GLJPanel.this.printAWTTiles.customTileWidth : 1024, (GLJPanel.this.printAWTTiles.customTileHeight != -1) ? GLJPanel.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (GLJPanel.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            GLJPanel.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(GLJPanel.this, GLJPanel.this.printGLAD);
                            glDrawable = GLJPanel.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    GLJPanel.this.printAWTTiles.setGLOrientation(!GLJPanel.this.skipGLOrientationVerticalFlip && GLJPanel.this.printGLAD.isGLOriented(), GLJPanel.this.printGLAD.isGLOriented());
                    GLJPanel.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    GLJPanel.this.printAWTTiles.renderer.attachAutoDrawable(GLJPanel.this.printGLAD);
                    if (GLJPanel.DEBUG) {
                        System.err.println("AWT print.setup " + GLJPanel.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + GLJPanel.this.printGLAD.getSurfaceWidth() + "x" + GLJPanel.this.printGLAD.getSurfaceHeight() + ", " + GLJPanel.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
                finally {
                    access$700.unlock();
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$700 = GLJPanel.this.lock;
                access$700.lock();
                try {
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.releasePrintOnEDT.0 " + GLJPanel.this.printAWTTiles);
                    }
                    GLJPanel.this.printAWTTiles.dispose();
                    GLJPanel.this.printAWTTiles = null;
                    if (GLJPanel.this.printGLAD != GLJPanel.this) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(GLJPanel.this.printGLAD, GLJPanel.this);
                        GLJPanel.this.printGLAD.destroy();
                    }
                    GLJPanel.this.printGLAD = null;
                    if (null != GLJPanel.this.printAnimator) {
                        GLJPanel.this.printAnimator.add(GLJPanel.this);
                        GLJPanel.this.printAnimator = null;
                    }
                    final int width = GLJPanel.this.getWidth();
                    final int height = GLJPanel.this.getHeight();
                    final int scale = SurfaceScaleUtils.scale(width, GLJPanel.this.hasPixelScale[0]);
                    final int scale2 = SurfaceScaleUtils.scale(height, GLJPanel.this.hasPixelScale[1]);
                    final GLDrawable delegatedDrawable = GLJPanel.this.getDelegatedDrawable();
                    if (scale != GLJPanel.this.panelWidth || scale2 != GLJPanel.this.panelHeight || delegatedDrawable.getSurfaceWidth() != GLJPanel.this.panelWidth || delegatedDrawable.getSurfaceHeight() != GLJPanel.this.panelHeight) {
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": GLJPanel.releasePrintOnEDT.0: resize [printing] panel " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight + " @ scale " + GLJPanel.this.getPixelScaleStr() + ", draw " + delegatedDrawable.getSurfaceWidth() + "x" + delegatedDrawable.getSurfaceHeight() + " -> " + width + "x" + height + " * " + GLJPanel.this.getPixelScaleStr() + " -> " + scale + "x" + scale2);
                        }
                        GLJPanel.this.reshapeWidth = scale;
                        GLJPanel.this.reshapeHeight = scale2;
                        GLJPanel.this.sendReshape = GLJPanel.this.handleReshape();
                    }
                    else {
                        GLJPanel.this.sendReshape = true;
                    }
                    GLJPanel.this.printActive = false;
                    GLJPanel.this.display();
                }
                finally {
                    access$700.unlock();
                }
            }
        };
        this.initSync = new Object();
        this.disposeAction = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$700 = GLJPanel.this.lock;
                access$700.lock();
                try {
                    if (null != GLJPanel.this.backend) {
                        final GLContext context = GLJPanel.this.backend.getContext();
                        final boolean b = !GLJPanel.this.backend.isUsingOwnLifecycle();
                        GLException ex = null;
                        if (null != context && context.isCreated()) {
                            try {
                                GLJPanel.this.helper.disposeGL(GLJPanel.this, context, !b);
                            }
                            catch (GLException ex2) {
                                ex = ex2;
                            }
                        }
                        Throwable t = null;
                        if (b) {
                            try {
                                GLJPanel.this.backend.destroy();
                            }
                            catch (Throwable t2) {
                                t = t2;
                            }
                            GLJPanel.this.backend = null;
                            GLJPanel.this.isInitialized = false;
                        }
                        if (null != ex) {
                            throw ex;
                        }
                        if (null != t) {
                            throw GLException.newGLException(t);
                        }
                    }
                }
                finally {
                    access$700.unlock();
                }
            }
        };
        this.updaterInitAction = new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.updater.init(GLJPanel.this);
            }
        };
        this.updaterDisplayAction = new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.updater.display(GLJPanel.this);
            }
        };
        this.updaterPlainDisplayAction = new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.updater.plainPaint(GLJPanel.this);
            }
        };
        this.paintImmediatelyAction = new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.paintImmediately(0, 0, GLJPanel.this.getWidth(), GLJPanel.this.getHeight());
            }
        };
        GLCapabilities reqOffscreenCaps;
        if (glCapabilitiesImmutable != null) {
            reqOffscreenCaps = (GLCapabilities)glCapabilitiesImmutable.cloneMutable();
        }
        else {
            reqOffscreenCaps = new GLCapabilities(GLProfile.getDefault(GLProfile.getDefaultDevice()));
        }
        reqOffscreenCaps.setDoubleBuffered(false);
        this.reqOffscreenCaps = reqOffscreenCaps;
        this.factory = GLDrawableFactoryImpl.getFactoryImpl(this.reqOffscreenCaps.getGLProfile());
        this.chooser = chooser;
        this.helper = new GLDrawableHelper();
        this.autoSwapBufferMode = this.helper.getAutoSwapBufferMode();
        this.setFocusable(true);
        this.addHierarchyListener(this.hierarchyListener);
        this.isShowing = this.isShowing();
    }
    
    public final boolean initializeBackend(final boolean b) {
        if (b) {
            new InterruptSource.Thread(null, null, getThreadName() + "-GLJPanel_Init") {
                @Override
                public void run() {
                    if (!GLJPanel.this.isInitialized) {
                        GLJPanel.this.initializeBackendImpl();
                    }
                }
            }.start();
            return true;
        }
        return this.isInitialized || this.initializeBackendImpl();
    }
    
    @Override
    public final void setSharedContext(final GLContext glContext) throws IllegalStateException {
        this.helper.setSharedContext(this.getContext(), glContext);
    }
    
    @Override
    public final void setSharedAutoDrawable(final GLAutoDrawable glAutoDrawable) throws IllegalStateException {
        this.helper.setSharedAutoDrawable(this, glAutoDrawable);
    }
    
    public AWTGLPixelBuffer.AWTGLPixelBufferProvider getCustomPixelBufferProvider() {
        return this.customPixelBufferProvider;
    }
    
    public void setPixelBufferProvider(final AWTGLPixelBuffer.AWTGLPixelBufferProvider customPixelBufferProvider) throws IllegalArgumentException, IllegalStateException {
        if (null == customPixelBufferProvider) {
            throw new IllegalArgumentException("Null PixelBufferProvider");
        }
        if (null != this.backend) {
            throw new IllegalStateException("Backend already realized.");
        }
        this.customPixelBufferProvider = customPixelBufferProvider;
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
        return EventQueue.isDispatchThread();
    }
    
    @Override
    public void display() {
        if (this.isShowing || (this.printActive && this.isVisible())) {
            if (EventQueue.isDispatchThread()) {
                this.paintImmediatelyAction.run();
            }
            else {
                try {
                    EventQueue.invokeAndWait(this.paintImmediatelyAction);
                }
                catch (Exception ex) {
                    throw new GLException(ex);
                }
            }
        }
    }
    
    protected void dispose(final Runnable runnable) {
        if (GLJPanel.DEBUG) {
            System.err.println(getThreadName() + ": GLJPanel.dispose() - start");
        }
        if (this.backend != null && this.backend.getContext() != null) {
            final GLAnimatorControl animator = this.getAnimator();
            final boolean b = null != animator && animator.pause();
            if (this.backend.getContext().isCreated()) {
                Threading.invoke(true, this.disposeAction, this.getTreeLock());
            }
            if (null != this.backend) {
                this.backend.destroy();
                this.isInitialized = false;
            }
            if (null != runnable) {
                runnable.run();
            }
            if (b) {
                animator.resume();
            }
        }
        if (GLJPanel.DEBUG) {
            System.err.println(getThreadName() + ": GLJPanel.dispose() - stop");
        }
    }
    
    @Override
    public void destroy() {
        this.removeNotify();
    }
    
    @Override
    protected void paintComponent(final Graphics graphics) {
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
            return;
        }
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            if (!this.isInitialized) {
                this.initializeBackendImpl();
            }
            if (!this.isInitialized || this.printActive) {
                return;
            }
            if (!this.printActive) {
                this.updatePixelScale(this.backend);
                if (this.handleReshape) {
                    this.handleReshape = false;
                    this.sendReshape = this.handleReshape();
                }
                if (this.isShowing) {
                    this.updater.setGraphics(graphics);
                    this.backend.doPaintComponent(graphics);
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private final void updateWrappedSurfaceScale(final GLDrawable glDrawable) {
        final NativeSurface nativeSurface = glDrawable.getNativeSurface();
        if (nativeSurface instanceof WrappedSurface) {
            ((WrappedSurface)nativeSurface).setSurfaceScale(this.hasPixelScale);
        }
    }
    
    @Override
    public final boolean setSurfaceScale(final float[] array) {
        System.arraycopy(array, 0, this.reqPixelScale, 0, 2);
        final Backend backend = this.backend;
        if (this.isInitialized && null != backend && this.isShowing) {
            if (this.isShowing || (this.printActive && this.isVisible())) {
                if (EventQueue.isDispatchThread()) {
                    this.setSurfaceScaleAction.run();
                }
                else {
                    try {
                        EventQueue.invokeAndWait(this.setSurfaceScaleAction);
                    }
                    catch (Exception ex) {
                        throw new GLException(ex);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private final boolean setSurfaceScaleImpl(final Backend backend) {
        if (SurfaceScaleUtils.setNewPixelScale(this.hasPixelScale, this.hasPixelScale, this.reqPixelScale, this.minPixelScale, this.maxPixelScale, GLJPanel.DEBUG ? this.getClass().getSimpleName() : null)) {
            this.reshapeImpl(this.getWidth(), this.getHeight());
            this.updateWrappedSurfaceScale(backend.getDrawable());
            return true;
        }
        return false;
    }
    
    private final boolean updatePixelScale(final Backend surfaceScaleImpl) {
        return JAWTUtil.getPixelScale(this.getGraphicsConfiguration(), this.minPixelScale, this.maxPixelScale) && this.setSurfaceScaleImpl(surfaceScaleImpl);
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
    
    @Override
    public void addNotify() {
        super.addNotify();
        this.awtWindowClosingProtocol.addClosingListener();
        JAWTUtil.getPixelScale(this.getGraphicsConfiguration(), this.minPixelScale, this.maxPixelScale);
        SurfaceScaleUtils.setNewPixelScale(this.hasPixelScale, this.hasPixelScale, this.reqPixelScale, this.minPixelScale, this.maxPixelScale, GLJPanel.DEBUG ? this.getClass().getSimpleName() : null);
        if (GLJPanel.DEBUG) {
            System.err.println(getThreadName() + ": GLJPanel.addNotify()");
        }
    }
    
    @Override
    public void removeNotify() {
        this.awtWindowClosingProtocol.removeClosingListener();
        this.dispose(null);
        this.hasPixelScale[0] = 1.0f;
        this.hasPixelScale[1] = 1.0f;
        this.minPixelScale[0] = 1.0f;
        this.minPixelScale[1] = 1.0f;
        this.maxPixelScale[0] = 1.0f;
        this.maxPixelScale[1] = 1.0f;
        super.removeNotify();
    }
    
    @Override
    public void reshape(final int n, final int n2, final int n3, final int n4) {
        super.reshape(n, n2, n3, n4);
        this.reshapeImpl(n3, n4);
    }
    
    private void reshapeImpl(final int n, final int n2) {
        final int scale = SurfaceScaleUtils.scale(n, this.hasPixelScale[0]);
        final int scale2 = SurfaceScaleUtils.scale(n2, this.hasPixelScale[1]);
        if (!this.printActive && (this.handleReshape || scale != this.panelWidth || scale2 != this.panelHeight)) {
            this.reshapeWidth = scale;
            this.reshapeHeight = scale2;
            this.handleReshape = true;
        }
        if (GLJPanel.DEBUG) {
            System.err.println(getThreadName() + ": GLJPanel.reshape.0 " + this.getName() + " resize [" + (this.printActive ? "printing" : "paint") + "] [ this " + this.getWidth() + "x" + this.getHeight() + ", pixelScale " + this.getPixelScaleStr() + ", panel " + this.panelWidth + "x" + this.panelHeight + "] -> " + (this.handleReshape ? "" : "[skipped] ") + n + "x" + n2 + " * " + this.getPixelScaleStr() + " -> " + scale + "x" + scale2 + ", reshapeSize " + this.reshapeWidth + "x" + this.reshapeHeight);
        }
    }
    
    @Override
    public void setupPrint(final double n, final double n2, final int n3, final int n4, final int n5) {
        this.printActive = true;
        if (GLJPanel.DEBUG) {
            System.err.printf(getThreadName() + ": GLJPanel.setupPrint: scale %f / %f, samples %d, tileSz %d x %d%n", n, n2, n3, n4, n5);
        }
        this.printAWTTiles = new AWTTilePainter(new TileRenderer(), this.isOpaque() ? 3 : 4, n, n2, n3, n4, n5, GLJPanel.DEBUG);
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.setupPrintOnEDT);
    }
    
    @Override
    public void releasePrint() {
        if (!this.printActive) {
            throw new IllegalStateException("setupPrint() not called");
        }
        this.sendReshape = false;
        this.handleReshape = false;
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.releasePrintOnEDT);
    }
    
    @Override
    public void print(final Graphics graphics) {
        if (!this.printActive) {
            throw new IllegalStateException("setupPrint() not called");
        }
        if (GLJPanel.DEBUG && !EventQueue.isDispatchThread()) {
            System.err.println(getThreadName() + ": Warning: GLCanvas print - not called from AWT-EDT");
        }
        this.sendReshape = false;
        this.handleReshape = false;
        final Graphics2D graphics2D = (Graphics2D)graphics;
        try {
            this.printAWTTiles.setupGraphics2DAndClipBounds(graphics2D, this.getWidth(), this.getHeight());
            final TileRenderer renderer = this.printAWTTiles.renderer;
            if (GLJPanel.DEBUG) {
                System.err.println("AWT print.0: " + renderer);
            }
            if (!renderer.eot()) {
                try {
                    do {
                        if (this.printGLAD != this) {
                            renderer.display();
                        }
                        else {
                            this.backend.doPlainPaint();
                        }
                    } while (!renderer.eot());
                    if (GLJPanel.DEBUG) {
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
        if (GLJPanel.DEBUG) {
            System.err.println("AWT print.X: " + this.printAWTTiles);
        }
    }
    
    @Override
    protected void printComponent(final Graphics graphics) {
        if (GLJPanel.DEBUG) {
            System.err.println("AWT printComponent.X: " + this.printAWTTiles);
        }
        this.print(graphics);
    }
    
    @Override
    public void setOpaque(final boolean b) {
        if (this.backend != null) {
            this.backend.setOpaque(b);
        }
        super.setOpaque(b);
    }
    
    @Override
    public void addGLEventListener(final GLEventListener glEventListener) {
        this.helper.addGLEventListener(glEventListener);
    }
    
    @Override
    public void addGLEventListener(final int n, final GLEventListener glEventListener) {
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
        if (EventQueue.isDispatchThread()) {
            disposeGLEventListenerAction.run();
        }
        else {
            try {
                EventQueue.invokeAndWait(disposeGLEventListenerAction);
            }
            catch (Exception ex) {
                throw new GLException(ex);
            }
        }
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
        return this.helper.setExclusiveContextThread(thread, this.getContext());
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
    public GLContext createContext(final GLContext glContext) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final Backend backend = this.backend;
            if (null == backend) {
                return null;
            }
            return backend.createContext(glContext);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void setRealized(final boolean b) {
    }
    
    @Override
    public boolean isRealized() {
        return this.isInitialized;
    }
    
    @Override
    public GLContext setContext(final GLContext context, final boolean b) {
        final RecursiveLock lock = this.lock;
        lock.lock();
        try {
            final Backend backend = this.backend;
            if (null == backend) {
                return null;
            }
            final GLContext context2 = backend.getContext();
            GLDrawableHelper.switchContext(backend.getDrawable(), context2, b, context, this.additionalCtxCreationFlags);
            backend.setContext(context);
            return context2;
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public final GLDrawable getDelegatedDrawable() {
        final Backend backend = this.backend;
        if (null == backend) {
            return null;
        }
        return backend.getDrawable();
    }
    
    @Override
    public GLContext getContext() {
        final Backend backend = this.backend;
        if (null == backend) {
            return null;
        }
        return backend.getContext();
    }
    
    @Override
    public GL getGL() {
        if (Beans.isDesignTime()) {
            return null;
        }
        final GLContext context = this.getContext();
        return (context == null) ? null : context.getGL();
    }
    
    @Override
    public GL setGL(final GL gl) {
        final GLContext context = this.getContext();
        if (context != null) {
            context.setGL(gl);
            return gl;
        }
        return null;
    }
    
    @Override
    public void setAutoSwapBufferMode(final boolean b) {
        this.autoSwapBufferMode = b;
        boolean handlesSwapBuffer = false;
        if (this.isInitialized) {
            final Backend backend = this.backend;
            if (null != backend) {
                handlesSwapBuffer = backend.handlesSwapBuffer();
            }
        }
        if (!handlesSwapBuffer) {
            this.helper.setAutoSwapBufferMode(b);
        }
    }
    
    @Override
    public boolean getAutoSwapBufferMode() {
        return this.autoSwapBufferMode;
    }
    
    @Override
    public void swapBuffers() {
        if (this.isInitialized) {
            final Backend backend = this.backend;
            if (null != backend) {
                backend.swapBuffers();
            }
        }
    }
    
    @Override
    public void setContextCreationFlags(final int additionalCtxCreationFlags) {
        this.additionalCtxCreationFlags = additionalCtxCreationFlags;
    }
    
    @Override
    public int getContextCreationFlags() {
        return this.additionalCtxCreationFlags;
    }
    
    public boolean shouldPreserveColorBufferIfTranslucent() {
        return this.oglPipelineUsable();
    }
    
    @Override
    public int getSurfaceWidth() {
        return this.panelWidth;
    }
    
    @Override
    public int getSurfaceHeight() {
        return this.panelHeight;
    }
    
    @Override
    public boolean isGLOriented() {
        final Backend backend = this.backend;
        return null == backend || backend.getDrawable().isGLOriented();
    }
    
    public final void setSkipGLOrientationVerticalFlip(final boolean skipGLOrientationVerticalFlip) {
        this.skipGLOrientationVerticalFlip = skipGLOrientationVerticalFlip;
    }
    
    public final boolean getSkipGLOrientationVerticalFlip() {
        return this.skipGLOrientationVerticalFlip;
    }
    
    @Override
    public GLCapabilitiesImmutable getChosenGLCapabilities() {
        final Backend backend = this.backend;
        if (null == backend) {
            return null;
        }
        return backend.getChosenGLCapabilities();
    }
    
    @Override
    public final GLCapabilitiesImmutable getRequestedGLCapabilities() {
        return this.reqOffscreenCaps;
    }
    
    public final void setRequestedGLCapabilities(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        if (null == glCapabilitiesImmutable) {
            throw new IllegalArgumentException("null caps");
        }
        Threading.invoke(true, new Runnable() {
            @Override
            public void run() {
                GLJPanel.this.dispose(new Runnable() {
                    @Override
                    public void run() {
                        GLJPanel.this.reqOffscreenCaps = glCapabilitiesImmutable;
                        GLJPanel.this.initializeBackendImpl();
                    }
                });
            }
        }, this.getTreeLock());
    }
    
    @Override
    public final GLProfile getGLProfile() {
        return this.reqOffscreenCaps.getGLProfile();
    }
    
    @Override
    public NativeSurface getNativeSurface() {
        final Backend backend = this.backend;
        if (null == backend) {
            return null;
        }
        return backend.getDrawable().getNativeSurface();
    }
    
    @Override
    public long getHandle() {
        final Backend backend = this.backend;
        if (null == backend) {
            return 0L;
        }
        return backend.getDrawable().getNativeSurface().getSurfaceHandle();
    }
    
    @Override
    public final GLDrawableFactory getFactory() {
        return this.factory;
    }
    
    public final int getTextureUnit() {
        final Backend backend = this.backend;
        if (null == backend) {
            return -1;
        }
        return backend.getTextureUnit();
    }
    
    public final void setTextureUnit(final int requestedTextureUnit) {
        this.requestedTextureUnit = requestedTextureUnit;
    }
    
    private boolean initializeBackendImpl() {
        synchronized (this.initSync) {
            if (this.isInitialized) {
                return true;
            }
            if (this.handleReshape) {
                if (GLJPanel.DEBUG) {
                    System.err.println(getThreadName() + ": GLJPanel.createAndInitializeBackend.1: [" + (this.printActive ? "printing" : "paint") + "] " + this.panelWidth + "x" + this.panelHeight + " @ scale " + this.getPixelScaleStr() + " -> " + this.reshapeWidth + "x" + this.reshapeHeight + " @ scale " + this.getPixelScaleStr());
                }
                this.panelWidth = this.reshapeWidth;
                this.panelHeight = this.reshapeHeight;
                this.handleReshape = false;
            }
            else if (GLJPanel.DEBUG) {
                System.err.println(getThreadName() + ": GLJPanel.createAndInitializeBackend.0: [" + (this.printActive ? "printing" : "paint") + "] " + this.panelWidth + "x" + this.panelHeight + " @ scale " + this.getPixelScaleStr());
            }
            if (0 >= this.panelWidth || 0 >= this.panelHeight) {
                return false;
            }
            if (null == this.backend) {
                if (this.oglPipelineUsable()) {
                    this.backend = new J2DOGLBackend();
                }
                else {
                    this.backend = new OffscreenBackend(this.customPixelBufferProvider);
                }
                this.isInitialized = false;
            }
            if (!this.isInitialized) {
                this.factory = GLDrawableFactoryImpl.getFactoryImpl(this.reqOffscreenCaps.getGLProfile());
                this.backend.initialize();
            }
            return this.isInitialized;
        }
    }
    
    private final String getPixelScaleStr() {
        return "[" + this.hasPixelScale[0] + ", " + this.hasPixelScale[1] + "]";
    }
    
    @Override
    public WindowClosingMode getDefaultCloseOperation() {
        return this.awtWindowClosingProtocol.getDefaultCloseOperation();
    }
    
    @Override
    public WindowClosingMode setDefaultCloseOperation(final WindowClosingMode defaultCloseOperation) {
        return this.awtWindowClosingProtocol.setDefaultCloseOperation(defaultCloseOperation);
    }
    
    private boolean handleReshape() {
        if (GLJPanel.DEBUG) {
            System.err.println(getThreadName() + ": GLJPanel.handleReshape: " + this.panelWidth + "x" + this.panelHeight + " @ scale " + this.getPixelScaleStr() + " -> " + this.reshapeWidth + "x" + this.reshapeHeight + " @ scale " + this.getPixelScaleStr());
        }
        this.panelWidth = this.reshapeWidth;
        this.panelHeight = this.reshapeHeight;
        return this.backend.handleReshape();
    }
    
    @Override
    public String toString() {
        final GLDrawable glDrawable = (null != this.backend) ? this.backend.getDrawable() : null;
        return "AWT-GLJPanel[ drawableType " + ((null != glDrawable) ? glDrawable.getClass().getName() : "null") + ", chosenCaps " + this.getChosenGLCapabilities() + "]";
    }
    
    private int getGLInteger(final GL gl, final int n) {
        final int[] array = { 0 };
        gl.glGetIntegerv(n, array, 0);
        return array[0];
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("GLJPanel");
        DEBUG_FRAMES = PropertyAccess.isPropertyDefined("jogl.debug.GLJPanel.Frames", true);
        DEBUG_VIEWPORT = PropertyAccess.isPropertyDefined("jogl.debug.GLJPanel.Viewport", true);
        USE_GLSL_TEXTURE_RASTERIZER = !PropertyAccess.isPropertyDefined("jogl.gljpanel.noglsl", true);
        SKIP_VERTICAL_FLIP_DEFAULT = PropertyAccess.isPropertyDefined("jogl.gljpanel.noverticalflip", true);
        java2dOGLEnabledByProp = (PropertyAccess.getBooleanProperty("sun.java2d.opengl", false) && !PropertyAccess.isPropertyDefined("jogl.gljpanel.noogl", true));
        boolean b = false;
        if (GLJPanel.java2dOGLEnabledByProp && Java2D.isOGLPipelineResourceCompatible() && Java2D.isFBOEnabled() && null != Java2D.getShareContext(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice())) {
            b = true;
        }
        useJava2DGLPipeline = b;
        GLJPanel.java2DGLPipelineOK = b;
        if (GLJPanel.DEBUG) {
            System.err.println("GLJPanel: DEBUG_VIEWPORT " + GLJPanel.DEBUG_VIEWPORT);
            System.err.println("GLJPanel: USE_GLSL_TEXTURE_RASTERIZER " + GLJPanel.USE_GLSL_TEXTURE_RASTERIZER);
            System.err.println("GLJPanel: SKIP_VERTICAL_FLIP_DEFAULT " + GLJPanel.SKIP_VERTICAL_FLIP_DEFAULT);
            System.err.println("GLJPanel: java2dOGLEnabledByProp " + GLJPanel.java2dOGLEnabledByProp);
            System.err.println("GLJPanel: useJava2DGLPipeline " + GLJPanel.useJava2DGLPipeline);
            System.err.println("GLJPanel: java2DGLPipelineOK " + GLJPanel.java2DGLPipelineOK);
        }
        GLJPanel.singleAWTGLPixelBufferProvider = null;
    }
    
    class Updater implements GLEventListener
    {
        private Graphics g;
        
        public void setGraphics(final Graphics g) {
            this.g = g;
        }
        
        @Override
        public void init(final GLAutoDrawable glAutoDrawable) {
            if (!GLJPanel.this.backend.preGL(this.g)) {
                return;
            }
            GLJPanel.this.helper.init(GLJPanel.this, !GLJPanel.this.sendReshape);
            GLJPanel.this.backend.postGL(this.g, false);
        }
        
        @Override
        public void dispose(final GLAutoDrawable glAutoDrawable) {
            GLJPanel.this.helper.disposeAllGLEventListener(GLJPanel.this, false);
        }
        
        @Override
        public void display(final GLAutoDrawable glAutoDrawable) {
            if (!GLJPanel.this.backend.preGL(this.g)) {
                return;
            }
            if (GLJPanel.this.sendReshape) {
                if (GLJPanel.DEBUG) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel.display: reshape(" + GLJPanel.this.viewportX + "," + GLJPanel.this.viewportY + " " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight + " @ scale " + GLJPanel.this.getPixelScaleStr() + ")");
                }
                GLJPanel.this.helper.reshape(GLJPanel.this, GLJPanel.this.viewportX, GLJPanel.this.viewportY, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                GLJPanel.this.sendReshape = false;
            }
            GLJPanel.this.helper.display(GLJPanel.this);
            GLJPanel.this.backend.postGL(this.g, true);
        }
        
        public void plainPaint(final GLAutoDrawable glAutoDrawable) {
            GLJPanel.this.helper.display(GLJPanel.this);
        }
        
        @Override
        public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        }
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
            final Backend access$300 = GLJPanel.this.backend;
            if (null != access$300) {
                this.listener = GLJPanel.this.helper.disposeGLEventListener(GLJPanel.this, access$300.getDrawable(), access$300.getContext(), this.listener, this.remove);
            }
        }
    }
    
    class OffscreenBackend implements Backend
    {
        private final AWTGLPixelBuffer.AWTGLPixelBufferProvider pixelBufferProvider;
        private final boolean useSingletonBuffer;
        private AWTGLPixelBuffer pixelBuffer;
        private BufferedImage alignedImage;
        protected IntBuffer readBackIntsForCPUVFlip;
        private volatile GLDrawable offscreenDrawable;
        private boolean offscreenIsFBO;
        private FBObject fboFlipped;
        private GLSLTextureRaster glslTextureRaster;
        private volatile GLContextImpl offscreenContext;
        private boolean flipVertical;
        private int frameCount;
        private final GLPixelStorageModes psm;
        
        OffscreenBackend(final AWTGLPixelBuffer.AWTGLPixelBufferProvider pixelBufferProvider) {
            this.frameCount = 0;
            this.psm = new GLPixelStorageModes();
            if (null == pixelBufferProvider) {
                this.pixelBufferProvider = getSingleAWTGLPixelBufferProvider();
            }
            else {
                this.pixelBufferProvider = pixelBufferProvider;
            }
            if (this.pixelBufferProvider instanceof GLPixelBuffer.SingletonGLPixelBufferProvider) {
                this.useSingletonBuffer = true;
            }
            else {
                this.useSingletonBuffer = false;
            }
        }
        
        @Override
        public final boolean isUsingOwnLifecycle() {
            return false;
        }
        
        @Override
        public final void initialize() {
            if (GLJPanel.DEBUG) {
                System.err.println(GLJPanel.getThreadName() + ": OffscreenBackend: initialize() - frameCount " + this.frameCount);
            }
            Throwable t = null;
            try {
                final GLContext[] array = { null };
                if (GLJPanel.this.helper.isSharedGLContextPending(array)) {
                    return;
                }
                this.offscreenDrawable = GLJPanel.this.factory.createOffscreenDrawable(null, GLJPanel.this.reqOffscreenCaps, GLJPanel.this.chooser, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                GLJPanel.this.updateWrappedSurfaceScale(this.offscreenDrawable);
                this.offscreenDrawable.setRealized(true);
                if (GLJPanel.DEBUG_FRAMES) {
                    this.offscreenDrawable.getNativeSurface().addSurfaceUpdatedListener(new SurfaceUpdatedListener() {
                        @Override
                        public final void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
                            System.err.println(GLJPanel.getThreadName() + ": OffscreenBackend.swapBuffers - frameCount " + OffscreenBackend.this.frameCount);
                        }
                    });
                }
                this.flipVertical = (!GLJPanel.this.skipGLOrientationVerticalFlip && this.offscreenDrawable.isGLOriented());
                this.offscreenIsFBO = this.offscreenDrawable.getRequestedGLCapabilities().isFBO();
                final boolean b = this.flipVertical && this.offscreenIsFBO && GLJPanel.this.reqOffscreenCaps.getGLProfile().isGL2ES2() && GLJPanel.USE_GLSL_TEXTURE_RASTERIZER;
                if (this.offscreenIsFBO && !b) {
                    ((GLFBODrawable)this.offscreenDrawable).setFBOMode(0);
                }
                (this.offscreenContext = (GLContextImpl)this.offscreenDrawable.createContext(array[0])).setContextCreationFlags(GLJPanel.this.additionalCtxCreationFlags);
                if (0 < this.offscreenContext.makeCurrent()) {
                    GLJPanel.this.isInitialized = true;
                    GLJPanel.this.helper.setAutoSwapBufferMode(false);
                    final GL gl = this.offscreenContext.getGL();
                    final GLCapabilitiesImmutable chosenGLCapabilities = this.offscreenDrawable.getChosenGLCapabilities();
                    final boolean b2 = !this.offscreenContext.hasRendererQuirk(12);
                    final boolean b3 = b && gl.isGL2ES2() && b2;
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": OffscreenBackend.initialize: useGLSLFlip " + b3 + " [flip " + this.flipVertical + ", isFBO " + this.offscreenIsFBO + ", isGL2ES2 " + gl.isGL2ES2() + ", noglsl " + !GLJPanel.USE_GLSL_TEXTURE_RASTERIZER + ", glslNonCompliant " + !b2 + ", isGL2ES2 " + gl.isGL2ES2() + "\n " + this.offscreenDrawable + "]");
                    }
                    if (b3) {
                        final GLFBODrawable glfboDrawable = (GLFBODrawable)this.offscreenDrawable;
                        glfboDrawable.setTextureUnit(GLJPanel.this.requestedTextureUnit);
                        try {
                            (this.fboFlipped = new FBObject()).init(gl, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, 0);
                            this.fboFlipped.attachColorbuffer(gl, 0, chosenGLCapabilities.getAlphaBits() > 0);
                            gl.glClear(16384);
                            (this.glslTextureRaster = new GLSLTextureRaster(glfboDrawable.getTextureUnit(), true)).init(gl.getGL2ES2());
                            this.glslTextureRaster.reshape(gl.getGL2ES2(), 0, 0, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            if (null != this.glslTextureRaster) {
                                this.glslTextureRaster.dispose(gl.getGL2ES2());
                                this.glslTextureRaster = null;
                            }
                            if (null != this.fboFlipped) {
                                this.fboFlipped.destroy(gl);
                                this.fboFlipped = null;
                            }
                        }
                    }
                    else {
                        this.fboFlipped = null;
                        this.glslTextureRaster = null;
                    }
                    this.offscreenContext.release();
                }
                else {
                    GLJPanel.this.isInitialized = false;
                }
            }
            catch (GLException ex2) {
                t = ex2;
            }
            finally {
                if (!GLJPanel.this.isInitialized) {
                    if (null != this.offscreenContext) {
                        this.offscreenContext.destroy();
                        this.offscreenContext = null;
                    }
                    if (null != this.offscreenDrawable) {
                        this.offscreenDrawable.setRealized(false);
                        this.offscreenDrawable = null;
                    }
                }
                if (null != t) {
                    throw new GLException("Caught GLException: " + t.getMessage(), t);
                }
            }
        }
        
        @Override
        public final void destroy() {
            if (GLJPanel.DEBUG) {
                System.err.println(GLJPanel.getThreadName() + ": OffscreenBackend: destroy() - offscreenContext: " + (null != this.offscreenContext) + " - offscreenDrawable: " + (null != this.offscreenDrawable) + " - frameCount " + this.frameCount);
            }
            if (null != this.offscreenContext && this.offscreenContext.isCreated() && 0 < this.offscreenContext.makeCurrent()) {
                try {
                    final GL gl = this.offscreenContext.getGL();
                    if (null != this.glslTextureRaster) {
                        this.glslTextureRaster.dispose(gl.getGL2ES2());
                    }
                    if (null != this.fboFlipped) {
                        this.fboFlipped.destroy(gl);
                    }
                }
                finally {
                    this.offscreenContext.destroy();
                }
            }
            this.offscreenContext = null;
            this.glslTextureRaster = null;
            this.fboFlipped = null;
            this.offscreenContext = null;
            if (this.offscreenDrawable != null) {
                final AbstractGraphicsDevice device = this.offscreenDrawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
                this.offscreenDrawable.setRealized(false);
                this.offscreenDrawable = null;
                if (null != device) {
                    device.close();
                }
            }
            this.offscreenIsFBO = false;
            if (null != this.readBackIntsForCPUVFlip) {
                this.readBackIntsForCPUVFlip.clear();
                this.readBackIntsForCPUVFlip = null;
            }
            if (null != this.pixelBuffer) {
                if (!this.useSingletonBuffer) {
                    this.pixelBuffer.dispose();
                }
                this.pixelBuffer = null;
            }
            this.alignedImage = null;
        }
        
        @Override
        public final void setOpaque(final boolean b) {
            if (b != GLJPanel.this.isOpaque() && !this.useSingletonBuffer) {
                this.pixelBuffer.dispose();
                this.pixelBuffer = null;
                this.alignedImage = null;
            }
        }
        
        @Override
        public final boolean preGL(final Graphics graphics) {
            return true;
        }
        
        @Override
        public final boolean handlesSwapBuffer() {
            return true;
        }
        
        @Override
        public final void swapBuffers() {
            final GLDrawable offscreenDrawable = this.offscreenDrawable;
            if (null != offscreenDrawable) {
                offscreenDrawable.swapBuffers();
            }
        }
        
        @Override
        public final void postGL(final Graphics graphics, final boolean b) {
            if (b) {
                if (GLJPanel.DEBUG_FRAMES) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: - frameCount " + this.frameCount);
                }
                final GL gl = this.offscreenContext.getGL();
                int n;
                TextureState textureState;
                TextureState textureState2;
                if (this.offscreenIsFBO) {
                    n = 33984 + ((GLFBODrawable)this.offscreenDrawable).getTextureUnit();
                    textureState = new TextureState(gl, 3553);
                    if (n != textureState.getUnit()) {
                        gl.glActiveTexture(n);
                        textureState2 = new TextureState(gl, n, 3553);
                    }
                    else {
                        textureState2 = textureState;
                    }
                }
                else {
                    n = 0;
                    textureState = null;
                    textureState2 = null;
                }
                if (GLJPanel.this.autoSwapBufferMode) {
                    this.offscreenDrawable.swapBuffers();
                }
                int n2;
                int n3;
                if (GLJPanel.this.isOpaque()) {
                    n2 = 3;
                    n3 = 1;
                }
                else {
                    n2 = 4;
                    n3 = 4;
                }
                final PixelFormat awtPixelFormat = this.pixelBufferProvider.getAWTPixelFormat(gl.getGLProfile(), n2);
                final GLPixelBuffer.GLPixelAttributes attributes = this.pixelBufferProvider.getAttributes(gl, n2, true);
                if (this.useSingletonBuffer) {
                    this.pixelBuffer = (AWTGLPixelBuffer)((GLPixelBuffer.SingletonGLPixelBufferProvider)this.pixelBufferProvider).getSingleBuffer(awtPixelFormat.comp, attributes, true);
                }
                if (null != this.pixelBuffer && this.pixelBuffer.requiresNewBuffer(gl, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, 0)) {
                    this.pixelBuffer.dispose();
                    this.pixelBuffer = null;
                    this.alignedImage = null;
                }
                boolean b2;
                if (null == this.pixelBuffer) {
                    if (0 >= GLJPanel.this.panelWidth || 0 >= GLJPanel.this.panelHeight) {
                        return;
                    }
                    this.pixelBuffer = this.pixelBufferProvider.allocate(gl, awtPixelFormat.comp, attributes, true, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, 1, 0);
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " pixelBufferProvider isSingletonBufferProvider " + this.useSingletonBuffer + ", 0x" + Integer.toHexString(this.pixelBufferProvider.hashCode()) + ", " + this.pixelBufferProvider.getClass().getSimpleName());
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " pixelBuffer 0x" + Integer.toHexString(this.pixelBuffer.hashCode()) + ", " + this.pixelBuffer + ", alignment " + n3);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " flippedVertical " + this.flipVertical + ", glslTextureRaster " + (null != this.glslTextureRaster) + ", isGL2ES3 " + gl.isGL2ES3());
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " panelSize " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight + " @ scale " + GLJPanel.this.getPixelScaleStr());
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " pixelAttribs " + attributes);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " awtPixelFormat " + awtPixelFormat);
                        b2 = true;
                    }
                    else {
                        b2 = false;
                    }
                }
                else {
                    b2 = false;
                }
                if (this.offscreenDrawable.getSurfaceWidth() != GLJPanel.this.panelWidth || this.offscreenDrawable.getSurfaceHeight() != GLJPanel.this.panelHeight) {
                    throw new InternalError("OffscreenDrawable panelSize mismatch (reshape missed): panelSize " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight + " != drawable " + this.offscreenDrawable.getSurfaceWidth() + "x" + this.offscreenDrawable.getSurfaceHeight() + ", on thread " + GLJPanel.getThreadName());
                }
                if (null == this.alignedImage || GLJPanel.this.panelWidth != this.alignedImage.getWidth() || GLJPanel.this.panelHeight != this.alignedImage.getHeight() || !this.pixelBuffer.isDataBufferSource(this.alignedImage)) {
                    this.alignedImage = this.pixelBuffer.getAlignedImage(GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0: " + GLJPanel.this.getName() + " new alignedImage " + this.alignedImage.getWidth() + "x" + this.alignedImage.getHeight() + " @ scale " + GLJPanel.this.getPixelScaleStr() + ", " + this.alignedImage + ", pixelBuffer " + this.pixelBuffer.width + "x" + this.pixelBuffer.height + ", " + this.pixelBuffer);
                    }
                }
                IntBuffer readBackIntsForCPUVFlip;
                if (!this.flipVertical || null != this.glslTextureRaster) {
                    readBackIntsForCPUVFlip = (IntBuffer)this.pixelBuffer.buffer;
                }
                else {
                    if (null == this.readBackIntsForCPUVFlip || this.pixelBuffer.width * this.pixelBuffer.height > this.readBackIntsForCPUVFlip.remaining()) {
                        this.readBackIntsForCPUVFlip = IntBuffer.allocate(this.pixelBuffer.width * this.pixelBuffer.height);
                    }
                    readBackIntsForCPUVFlip = this.readBackIntsForCPUVFlip;
                }
                if (GLJPanel.DEBUG_FRAMES) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.readPixels: - frameCount " + this.frameCount);
                }
                this.psm.setPackAlignment(gl, n3);
                if (gl.isGL2ES3()) {
                    final GL2ES3 gl2ES3 = gl.getGL2ES3();
                    this.psm.setPackRowLength(gl2ES3, GLJPanel.this.panelWidth);
                    gl2ES3.glReadBuffer(gl2ES3.getDefaultReadBuffer());
                    if (b2) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.0: fboDrawable " + this.offscreenDrawable);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.0: isGL2ES3, readBuffer 0x" + Integer.toHexString(gl2ES3.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.0: def-readBuffer 0x" + Integer.toHexString(gl2ES3.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.0: def-readFBO    0x" + Integer.toHexString(gl2ES3.getDefaultReadFramebuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.0: bound-readFBO  0x" + Integer.toHexString(gl2ES3.getBoundFramebuffer(36008)));
                    }
                }
                if (null != this.glslTextureRaster) {
                    final int[] array = { 0, 0, 0, 0 };
                    gl.glGetIntegerv(2978, array, 0);
                    final boolean b3 = 0 != array[0] || 0 != array[1] || GLJPanel.this.panelWidth != array[2] || GLJPanel.this.panelHeight != array[3];
                    if (GLJPanel.DEBUG_VIEWPORT) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL: " + GLJPanel.this.getName() + " Viewport: change " + b3 + ", " + array[0] + "/" + array[1] + " " + array[2] + "x" + array[3] + " -> 0/0 " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight);
                    }
                    if (b3) {
                        gl.glViewport(0, 0, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                    }
                    final GLFBODrawable glfboDrawable = (GLFBODrawable)this.offscreenDrawable;
                    final FBObject.TextureAttachment textureAttachment = glfboDrawable.getColorbuffer(1028).getTextureAttachment();
                    this.fboFlipped.bind(gl);
                    gl.glBindTexture(3553, textureAttachment.getName());
                    this.glslTextureRaster.display(gl.getGL2ES2());
                    if (b2) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: fboDrawable " + glfboDrawable);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: read from fbo-rb " + this.fboFlipped.getReadFramebuffer() + ", fbo " + this.fboFlipped);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: isGL2ES3, readBuffer 0x" + Integer.toHexString(gl.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: def-readBuffer 0x" + Integer.toHexString(gl.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: def-readFBO    0x" + Integer.toHexString(gl.getDefaultReadFramebuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: bound-readFBO  0x" + Integer.toHexString(gl.getBoundFramebuffer(36008)));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.1: " + GLJPanel.this.getName() + " pixelAttribs " + attributes);
                    }
                    gl.glReadPixels(0, 0, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, attributes.format, attributes.type, readBackIntsForCPUVFlip);
                    this.fboFlipped.unbind(gl);
                    if (b2) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: fboDrawable " + glfboDrawable);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: read from fbo-rb " + this.fboFlipped.getReadFramebuffer() + ", fbo " + this.fboFlipped);
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: isGL2ES3, readBuffer 0x" + Integer.toHexString(gl.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: def-readBuffer 0x" + Integer.toHexString(gl.getDefaultReadBuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: def-readFBO    0x" + Integer.toHexString(gl.getDefaultReadFramebuffer()));
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.postGL.0.2: bound-readFBO  0x" + Integer.toHexString(gl.getBoundFramebuffer(36008)));
                    }
                    if (b3) {
                        gl.glViewport(array[0], array[1], array[2], array[3]);
                    }
                }
                else {
                    gl.glReadPixels(0, 0, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, attributes.format, attributes.type, readBackIntsForCPUVFlip);
                    if (this.flipVertical) {
                        final BufferedImage alignedImage = this.alignedImage;
                        final int[] array2 = readBackIntsForCPUVFlip.array();
                        final int[] data = ((DataBufferInt)alignedImage.getRaster().getDataBuffer()).getData();
                        final int access$1700 = GLJPanel.this.panelWidth;
                        int n4 = 0;
                        for (int i = (GLJPanel.this.panelHeight - 1) * GLJPanel.this.panelWidth; i >= 0; i -= access$1700) {
                            System.arraycopy(array2, n4, data, i, access$1700);
                            n4 += access$1700;
                        }
                    }
                }
                if (n != 0) {
                    textureState2.restore(gl);
                    if (n != textureState.getUnit()) {
                        textureState.restore(gl);
                    }
                }
                this.psm.restore(gl);
            }
        }
        
        @Override
        public final int getTextureUnit() {
            if (null != this.glslTextureRaster && null != this.offscreenDrawable) {
                return ((GLFBODrawable)this.offscreenDrawable).getTextureUnit();
            }
            return -1;
        }
        
        @Override
        public final void doPaintComponent(final Graphics graphics) {
            GLJPanel.this.helper.invokeGL(this.offscreenDrawable, this.offscreenContext, GLJPanel.this.updaterDisplayAction, GLJPanel.this.updaterInitAction);
            if (null != this.alignedImage) {
                if (GLJPanel.DEBUG_FRAMES) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.doPaintComponent.drawImage: - frameCount " + this.frameCount);
                }
                graphics.drawImage(this.alignedImage, 0, 0, SurfaceScaleUtils.scaleInv(this.alignedImage.getWidth(), GLJPanel.this.hasPixelScale[0]), SurfaceScaleUtils.scaleInv(this.alignedImage.getHeight(), GLJPanel.this.hasPixelScale[1]), null);
            }
            ++this.frameCount;
        }
        
        @Override
        public final void doPlainPaint() {
            GLJPanel.this.helper.invokeGL(this.offscreenDrawable, this.offscreenContext, GLJPanel.this.updaterPlainDisplayAction, GLJPanel.this.updaterInitAction);
        }
        
        @Override
        public final boolean handleReshape() {
            GLDrawableImpl glDrawableImpl = (GLDrawableImpl)this.offscreenDrawable;
            final GLDrawableImpl resizeOffscreenDrawable = GLDrawableHelper.resizeOffscreenDrawable(glDrawableImpl, this.offscreenContext, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
            if (glDrawableImpl != resizeOffscreenDrawable) {
                glDrawableImpl = resizeOffscreenDrawable;
                this.offscreenDrawable = resizeOffscreenDrawable;
                GLJPanel.this.updateWrappedSurfaceScale(this.offscreenDrawable);
            }
            if (GLJPanel.DEBUG) {
                System.err.println(GLJPanel.getThreadName() + ": GLJPanel.OffscreenBackend.handleReshape: " + GLJPanel.this.panelWidth + "x" + GLJPanel.this.panelHeight + " @ scale " + GLJPanel.this.getPixelScaleStr() + " -> " + glDrawableImpl.getSurfaceWidth() + "x" + glDrawableImpl.getSurfaceHeight());
            }
            GLJPanel.this.panelWidth = glDrawableImpl.getSurfaceWidth();
            GLJPanel.this.panelHeight = glDrawableImpl.getSurfaceHeight();
            if (null != this.glslTextureRaster && 0 < this.offscreenContext.makeCurrent()) {
                try {
                    final GL gl = this.offscreenContext.getGL();
                    this.fboFlipped.reset(gl, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight, 0);
                    this.glslTextureRaster.reshape(gl.getGL2ES2(), 0, 0, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
                }
                finally {
                    this.offscreenContext.release();
                }
            }
            return glDrawableImpl.isRealized();
        }
        
        @Override
        public final GLContext createContext(final GLContext glContext) {
            return (null != this.offscreenDrawable) ? this.offscreenDrawable.createContext(glContext) : null;
        }
        
        @Override
        public final void setContext(final GLContext glContext) {
            this.offscreenContext = (GLContextImpl)glContext;
        }
        
        @Override
        public final GLContext getContext() {
            return this.offscreenContext;
        }
        
        @Override
        public final GLDrawable getDrawable() {
            return this.offscreenDrawable;
        }
        
        @Override
        public final GLCapabilitiesImmutable getChosenGLCapabilities() {
            if (this.offscreenDrawable == null) {
                return null;
            }
            return this.offscreenDrawable.getChosenGLCapabilities();
        }
        
        @Override
        public final GLProfile getGLProfile() {
            if (this.offscreenDrawable == null) {
                return null;
            }
            return this.offscreenDrawable.getGLProfile();
        }
    }
    
    class J2DOGLBackend implements Backend
    {
        private Object j2dSurface;
        private GLContext j2dContext;
        private GLDrawable joglDrawable;
        private GLContext joglContext;
        private final int[] drawBuffer;
        private final int[] readBuffer;
        private final int[] frameBuffer;
        private boolean checkedForFBObjectWorkarounds;
        private boolean fbObjectWorkarounds;
        private int[] frameBufferDepthBuffer;
        private int[] frameBufferTexture;
        private boolean createNewDepthBuffer;
        private boolean checkedGLVendor;
        private boolean vendorIsATI;
        private GraphicsConfiguration workaroundConfig;
        
        J2DOGLBackend() {
            this.drawBuffer = new int[1];
            this.readBuffer = new int[1];
            this.frameBuffer = new int[1];
        }
        
        @Override
        public final boolean isUsingOwnLifecycle() {
            return true;
        }
        
        @Override
        public final void initialize() {
            if (GLJPanel.DEBUG) {
                System.err.println(GLJPanel.getThreadName() + ": J2DOGL: initialize()");
            }
            GLJPanel.this.isInitialized = true;
        }
        
        @Override
        public final void destroy() {
            Java2D.invokeWithOGLContextCurrent(null, new Runnable() {
                @Override
                public void run() {
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": J2DOGL: destroy() - joglContext: " + (null != J2DOGLBackend.this.joglContext) + " - joglDrawable: " + (null != J2DOGLBackend.this.joglDrawable));
                    }
                    if (J2DOGLBackend.this.joglContext != null) {
                        J2DOGLBackend.this.joglContext.destroy();
                        J2DOGLBackend.this.joglContext = null;
                    }
                    J2DOGLBackend.this.joglDrawable = null;
                    if (J2DOGLBackend.this.j2dContext != null) {
                        J2DOGLBackend.this.j2dContext.destroy();
                        J2DOGLBackend.this.j2dContext = null;
                    }
                }
            });
        }
        
        @Override
        public final void setOpaque(final boolean b) {
        }
        
        @Override
        public final GLContext createContext(final GLContext glContext) {
            if (null != glContext) {
                throw new GLException("J2DOGLBackend cannot create context w/ additional shared context, since it already needs to share the context w/ J2D.");
            }
            return (null != this.joglDrawable && null != this.j2dContext) ? this.joglDrawable.createContext(this.j2dContext) : null;
        }
        
        @Override
        public final void setContext(final GLContext joglContext) {
            this.joglContext = joglContext;
        }
        
        @Override
        public final GLContext getContext() {
            return this.joglContext;
        }
        
        @Override
        public final GLDrawable getDrawable() {
            return this.joglDrawable;
        }
        
        @Override
        public final int getTextureUnit() {
            return -1;
        }
        
        @Override
        public final GLCapabilitiesImmutable getChosenGLCapabilities() {
            return new GLCapabilities(null);
        }
        
        @Override
        public final GLProfile getGLProfile() {
            return GLProfile.getDefault(GLProfile.getDefaultDevice());
        }
        
        @Override
        public final boolean handleReshape() {
            return true;
        }
        
        @Override
        public final boolean preGL(final Graphics graphics) {
            final GL2 gl2 = this.joglContext.getGL().getGL2();
            gl2.glEnable(3089);
            final Rectangle oglScissorBox = Java2D.getOGLScissorBox(graphics);
            if (oglScissorBox == null) {
                if (GLJPanel.DEBUG) {
                    System.err.println(GLJPanel.getThreadName() + ": Java2D.getOGLScissorBox() returned null");
                }
                return false;
            }
            if (GLJPanel.DEBUG) {
                System.err.println(GLJPanel.getThreadName() + ": GLJPanel: gl.glScissor(" + oglScissorBox.x + ", " + oglScissorBox.y + ", " + oglScissorBox.width + ", " + oglScissorBox.height + ")");
            }
            gl2.glScissor(oglScissorBox.x, oglScissorBox.y, oglScissorBox.width, oglScissorBox.height);
            final Rectangle oglViewport = Java2D.getOGLViewport(graphics, GLJPanel.this.panelWidth, GLJPanel.this.panelHeight);
            if (GLJPanel.this.viewportX != oglViewport.x || GLJPanel.this.viewportY != oglViewport.y) {
                GLJPanel.this.sendReshape = true;
                if (GLJPanel.DEBUG) {
                    System.err.println(GLJPanel.getThreadName() + ": Sending reshape because viewport changed");
                    System.err.println("  viewportX (" + GLJPanel.this.viewportX + ") ?= oglViewport.x (" + oglViewport.x + ")");
                    System.err.println("  viewportY (" + GLJPanel.this.viewportY + ") ?= oglViewport.y (" + oglViewport.y + ")");
                }
            }
            GLJPanel.this.viewportX = oglViewport.x;
            GLJPanel.this.viewportY = oglViewport.y;
            if (Java2D.isFBOEnabled() && Java2D.getOGLSurfaceType(graphics) == Java2D.FBOBJECT) {
                final int oglTextureType = Java2D.getOGLTextureType(graphics);
                if (!this.checkedForFBObjectWorkarounds) {
                    this.checkedForFBObjectWorkarounds = true;
                    gl2.glBindTexture(oglTextureType, 0);
                    gl2.glBindFramebuffer(36160, this.frameBuffer[0]);
                    final int glCheckFramebufferStatus = gl2.glCheckFramebufferStatus(36160);
                    if (glCheckFramebufferStatus != 36053) {
                        this.fbObjectWorkarounds = true;
                        this.createNewDepthBuffer = true;
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": GLJPanel: ERR GL_FRAMEBUFFER_BINDING: Discovered Invalid J2D FBO(" + this.frameBuffer[0] + "): " + FBObject.getStatusString(glCheckFramebufferStatus) + ", frame_buffer_object workarounds to be necessary");
                        }
                    }
                    else {
                        this.frameBufferTexture = null;
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": GLJPanel: OK GL_FRAMEBUFFER_BINDING: " + this.frameBuffer[0]);
                        }
                    }
                }
                if (this.fbObjectWorkarounds && this.createNewDepthBuffer) {
                    if (this.frameBufferDepthBuffer == null) {
                        this.frameBufferDepthBuffer = new int[1];
                    }
                    if (this.frameBufferDepthBuffer[0] != 0) {
                        gl2.glDeleteRenderbuffers(1, this.frameBufferDepthBuffer, 0);
                        this.frameBufferDepthBuffer[0] = 0;
                    }
                    gl2.glBindTexture(oglTextureType, this.frameBufferTexture[0]);
                    final int[] array = { 0 };
                    final int[] array2 = { 0 };
                    gl2.glGetTexLevelParameteriv(oglTextureType, 0, 4096, array, 0);
                    gl2.glGetTexLevelParameteriv(oglTextureType, 0, 4097, array2, 0);
                    gl2.glGenRenderbuffers(1, this.frameBufferDepthBuffer, 0);
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel: Generated frameBufferDepthBuffer " + this.frameBufferDepthBuffer[0] + " with width " + array[0] + ", height " + array2[0]);
                    }
                    gl2.glBindRenderbuffer(36161, this.frameBufferDepthBuffer[0]);
                    gl2.glRenderbufferStorage(36161, 33190, array[0], array2[0]);
                    gl2.glBindRenderbuffer(36161, 0);
                    this.createNewDepthBuffer = false;
                }
                gl2.glBindTexture(oglTextureType, 0);
                gl2.glBindFramebuffer(36160, this.frameBuffer[0]);
                if (this.fbObjectWorkarounds) {
                    gl2.glFramebufferTexture2D(36160, 36064, oglTextureType, this.frameBufferTexture[0], 0);
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel: frameBufferDepthBuffer: " + this.frameBufferDepthBuffer[0]);
                    }
                    gl2.glFramebufferRenderbuffer(36160, 36096, 36161, this.frameBufferDepthBuffer[0]);
                }
                if (GLJPanel.DEBUG) {
                    final int glCheckFramebufferStatus2 = gl2.glCheckFramebufferStatus(36160);
                    if (glCheckFramebufferStatus2 != 36053) {
                        throw new GLException("Error: framebuffer was incomplete: status = 0x" + Integer.toHexString(glCheckFramebufferStatus2));
                    }
                }
            }
            else {
                if (GLJPanel.DEBUG) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel: Setting up drawBuffer " + this.drawBuffer[0] + " and readBuffer " + this.readBuffer[0]);
                }
                gl2.glDrawBuffer(this.drawBuffer[0]);
                gl2.glReadBuffer(this.readBuffer[0]);
            }
            return true;
        }
        
        @Override
        public final boolean handlesSwapBuffer() {
            return false;
        }
        
        @Override
        public final void swapBuffers() {
            final GLDrawable joglDrawable = this.joglDrawable;
            if (null != joglDrawable) {
                joglDrawable.swapBuffers();
            }
        }
        
        @Override
        public final void postGL(final Graphics graphics, final boolean b) {
            final GL gl = this.joglContext.getGL();
            gl.glFinish();
            if (Java2D.isFBOEnabled() && Java2D.getOGLSurfaceType(graphics) == Java2D.FBOBJECT) {
                gl.glBindFramebuffer(36160, 0);
            }
        }
        
        @Override
        public final void doPaintComponent(final Graphics graphics) {
            if (Java2D.isFBOEnabled()) {
                if (this.workaroundConfig == null) {
                    this.workaroundConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                }
                Java2D.invokeWithOGLSharedContextCurrent(this.workaroundConfig, new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
            Java2D.invokeWithOGLContextCurrent(graphics, new Runnable() {
                @Override
                public void run() {
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel.invokeWithOGLContextCurrent");
                    }
                    if (J2DOGLBackend.this.j2dContext == null) {
                        J2DOGLBackend.this.j2dContext = GLJPanel.this.factory.createExternalGLContext();
                        if (GLJPanel.DEBUG) {
                            System.err.println(GLJPanel.getThreadName() + ": GLJPanel.Created External Context: " + J2DOGLBackend.this.j2dContext);
                        }
                        if (GLJPanel.DEBUG) {}
                        J2DOGLBackend.this.j2dContext.makeCurrent();
                        final GL gl = J2DOGLBackend.this.j2dContext.getGL();
                        if (GLJPanel.this.getGLInteger(gl, 3410) < GLJPanel.this.reqOffscreenCaps.getRedBits() || GLJPanel.this.getGLInteger(gl, 3411) < GLJPanel.this.reqOffscreenCaps.getGreenBits() || GLJPanel.this.getGLInteger(gl, 3412) < GLJPanel.this.reqOffscreenCaps.getBlueBits() || GLJPanel.this.getGLInteger(gl, 3416) < GLJPanel.this.reqOffscreenCaps.getAccumRedBits() || GLJPanel.this.getGLInteger(gl, 3417) < GLJPanel.this.reqOffscreenCaps.getAccumGreenBits() || GLJPanel.this.getGLInteger(gl, 3418) < GLJPanel.this.reqOffscreenCaps.getAccumBlueBits() || GLJPanel.this.getGLInteger(gl, 3419) < GLJPanel.this.reqOffscreenCaps.getAccumAlphaBits() || GLJPanel.this.getGLInteger(gl, 3415) < GLJPanel.this.reqOffscreenCaps.getStencilBits()) {
                            if (GLJPanel.DEBUG) {
                                System.err.println(GLJPanel.getThreadName() + ": GLJPanel: Falling back to pbuffer-based support because Java2D context insufficient");
                                System.err.println("                    Available              Required");
                                System.err.println("GL_RED_BITS         " + GLJPanel.this.getGLInteger(gl, 3410) + "              " + GLJPanel.this.reqOffscreenCaps.getRedBits());
                                System.err.println("GL_GREEN_BITS       " + GLJPanel.this.getGLInteger(gl, 3411) + "              " + GLJPanel.this.reqOffscreenCaps.getGreenBits());
                                System.err.println("GL_BLUE_BITS        " + GLJPanel.this.getGLInteger(gl, 3412) + "              " + GLJPanel.this.reqOffscreenCaps.getBlueBits());
                                System.err.println("GL_ALPHA_BITS       " + GLJPanel.this.getGLInteger(gl, 3413) + "              " + GLJPanel.this.reqOffscreenCaps.getAlphaBits());
                                System.err.println("GL_ACCUM_RED_BITS   " + GLJPanel.this.getGLInteger(gl, 3416) + "              " + GLJPanel.this.reqOffscreenCaps.getAccumRedBits());
                                System.err.println("GL_ACCUM_GREEN_BITS " + GLJPanel.this.getGLInteger(gl, 3417) + "              " + GLJPanel.this.reqOffscreenCaps.getAccumGreenBits());
                                System.err.println("GL_ACCUM_BLUE_BITS  " + GLJPanel.this.getGLInteger(gl, 3418) + "              " + GLJPanel.this.reqOffscreenCaps.getAccumBlueBits());
                                System.err.println("GL_ACCUM_ALPHA_BITS " + GLJPanel.this.getGLInteger(gl, 3419) + "              " + GLJPanel.this.reqOffscreenCaps.getAccumAlphaBits());
                                System.err.println("GL_DEPTH_BITS       " + GLJPanel.this.getGLInteger(gl, 3414) + "              " + GLJPanel.this.reqOffscreenCaps.getDepthBits());
                                System.err.println("GL_STENCIL_BITS     " + GLJPanel.this.getGLInteger(gl, 3415) + "              " + GLJPanel.this.reqOffscreenCaps.getStencilBits());
                            }
                            GLJPanel.this.isInitialized = false;
                            GLJPanel.this.backend = null;
                            GLJPanel.java2DGLPipelineOK = false;
                            GLJPanel.this.handleReshape = true;
                            J2DOGLBackend.this.j2dContext.destroy();
                            J2DOGLBackend.this.j2dContext = null;
                            return;
                        }
                    }
                    else {
                        J2DOGLBackend.this.j2dContext.makeCurrent();
                    }
                    try {
                        J2DOGLBackend.this.captureJ2DState(J2DOGLBackend.this.j2dContext.getGL(), graphics);
                        final Object oglSurfaceIdentifier = Java2D.getOGLSurfaceIdentifier(graphics);
                        if (oglSurfaceIdentifier != null) {
                            if (J2DOGLBackend.this.j2dSurface != oglSurfaceIdentifier) {
                                if (J2DOGLBackend.this.joglContext != null) {
                                    J2DOGLBackend.this.joglContext.destroy();
                                    J2DOGLBackend.this.joglContext = null;
                                    J2DOGLBackend.this.joglDrawable = null;
                                    GLJPanel.this.sendReshape = true;
                                    if (GLJPanel.DEBUG) {
                                        System.err.println(GLJPanel.getThreadName() + ": Sending reshape because surface changed");
                                        System.err.println("New surface = " + oglSurfaceIdentifier);
                                    }
                                }
                                J2DOGLBackend.this.j2dSurface = oglSurfaceIdentifier;
                                if (GLJPanel.DEBUG) {
                                    System.err.print(GLJPanel.getThreadName() + ": Surface type: ");
                                    final int oglSurfaceType = Java2D.getOGLSurfaceType(graphics);
                                    if (oglSurfaceType == Java2D.UNDEFINED) {
                                        System.err.println("UNDEFINED");
                                    }
                                    else if (oglSurfaceType == Java2D.WINDOW) {
                                        System.err.println("WINDOW");
                                    }
                                    else if (oglSurfaceType == Java2D.PBUFFER) {
                                        System.err.println("PBUFFER");
                                    }
                                    else if (oglSurfaceType == Java2D.TEXTURE) {
                                        System.err.println("TEXTURE");
                                    }
                                    else if (oglSurfaceType == Java2D.FLIP_BACKBUFFER) {
                                        System.err.println("FLIP_BACKBUFFER");
                                    }
                                    else if (oglSurfaceType == Java2D.FBOBJECT) {
                                        System.err.println("FBOBJECT");
                                    }
                                    else {
                                        System.err.println("(Unknown surface type " + oglSurfaceType + ")");
                                    }
                                }
                            }
                            if (J2DOGLBackend.this.joglContext == null) {
                                if (GLJPanel.this.factory.canCreateExternalGLDrawable(J2DOGLBackend.this.j2dContext.getGLDrawable().getNativeSurface().getGraphicsConfiguration().getScreen().getDevice())) {
                                    J2DOGLBackend.this.joglDrawable = GLJPanel.this.factory.createExternalGLDrawable();
                                    J2DOGLBackend.this.joglContext = J2DOGLBackend.this.joglDrawable.createContext(J2DOGLBackend.this.j2dContext);
                                    if (GLJPanel.DEBUG) {
                                        System.err.println("-- Created External Drawable: " + J2DOGLBackend.this.joglDrawable);
                                        System.err.println("-- Created Context: " + J2DOGLBackend.this.joglContext);
                                    }
                                }
                                if (Java2D.isFBOEnabled() && Java2D.getOGLSurfaceType(graphics) == Java2D.FBOBJECT && J2DOGLBackend.this.fbObjectWorkarounds) {
                                    J2DOGLBackend.this.createNewDepthBuffer = true;
                                }
                            }
                            GLJPanel.this.helper.invokeGL(J2DOGLBackend.this.joglDrawable, J2DOGLBackend.this.joglContext, GLJPanel.this.updaterDisplayAction, GLJPanel.this.updaterInitAction);
                        }
                    }
                    finally {
                        J2DOGLBackend.this.j2dContext.release();
                    }
                }
            });
        }
        
        @Override
        public final void doPlainPaint() {
            GLJPanel.this.helper.invokeGL(this.joglDrawable, this.joglContext, GLJPanel.this.updaterPlainDisplayAction, GLJPanel.this.updaterInitAction);
        }
        
        private final void captureJ2DState(final GL gl, final Graphics graphics) {
            gl.glGetIntegerv(3073, this.drawBuffer, 0);
            gl.glGetIntegerv(3074, this.readBuffer, 0);
            if (Java2D.isFBOEnabled() && Java2D.getOGLSurfaceType(graphics) == Java2D.FBOBJECT) {
                gl.glGetIntegerv(36006, this.frameBuffer, 0);
                if (!gl.glIsFramebuffer(this.frameBuffer[0])) {
                    this.checkedForFBObjectWorkarounds = true;
                    this.fbObjectWorkarounds = true;
                    this.createNewDepthBuffer = true;
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel: Fetched ERR GL_FRAMEBUFFER_BINDING: " + this.frameBuffer[0] + " - NOT A FBO" + ", frame_buffer_object workarounds to be necessary");
                    }
                }
                else if (GLJPanel.DEBUG) {
                    System.err.println(GLJPanel.getThreadName() + ": GLJPanel: Fetched OK GL_FRAMEBUFFER_BINDING: " + this.frameBuffer[0]);
                }
                if (this.fbObjectWorkarounds || !this.checkedForFBObjectWorkarounds) {
                    if (this.frameBufferTexture == null) {
                        this.frameBufferTexture = new int[1];
                    }
                    gl.glGetFramebufferAttachmentParameteriv(36160, 36064, 36049, this.frameBufferTexture, 0);
                    if (GLJPanel.DEBUG) {
                        System.err.println(GLJPanel.getThreadName() + ": GLJPanel: FBO COLOR_ATTACHMENT0: " + this.frameBufferTexture[0]);
                    }
                }
                if (!this.checkedGLVendor) {
                    this.checkedGLVendor = true;
                    final String glGetString = gl.glGetString(7936);
                    if (glGetString != null && glGetString.startsWith("ATI")) {
                        this.vendorIsATI = true;
                    }
                }
                if (this.vendorIsATI) {
                    gl.glBindFramebuffer(36160, 0);
                }
            }
        }
    }
    
    interface Backend
    {
        boolean isUsingOwnLifecycle();
        
        void initialize();
        
        void destroy();
        
        void setOpaque(final boolean p0);
        
        GLContext createContext(final GLContext p0);
        
        void setContext(final GLContext p0);
        
        GLContext getContext();
        
        GLDrawable getDrawable();
        
        int getTextureUnit();
        
        GLCapabilitiesImmutable getChosenGLCapabilities();
        
        GLProfile getGLProfile();
        
        boolean handleReshape();
        
        boolean preGL(final Graphics p0);
        
        boolean handlesSwapBuffer();
        
        void swapBuffers();
        
        void postGL(final Graphics p0, final boolean p1);
        
        void doPaintComponent(final Graphics p0);
        
        void doPlainPaint();
    }
}
