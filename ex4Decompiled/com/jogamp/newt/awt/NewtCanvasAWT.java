// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.awt;

import java.awt.Toolkit;
import jogamp.newt.driver.DriverClearFocus;
import java.beans.PropertyChangeEvent;
import java.util.Set;
import java.awt.AWTKeyStroke;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.Display;
import jogamp.newt.Debug;
import java.security.AccessController;
import java.io.Serializable;
import java.security.PrivilegedAction;
import jogamp.nativewindow.jawt.JAWTUtil;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.newt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.Graphics2D;
import com.jogamp.opengl.util.TileRenderer;
import java.awt.Graphics;
import jogamp.newt.WindowImpl;
import jogamp.newt.awt.NewtFactoryAWT;
import com.jogamp.common.ExceptionUtils;
import java.awt.EventQueue;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import java.beans.Beans;
import java.applet.Applet;
import com.jogamp.nativewindow.NativeWindow;
import java.awt.Dimension;
import java.awt.Container;
import jogamp.nativewindow.awt.AWTMisc;
import java.awt.GraphicsConfiguration;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.util.GLDrawableUtil;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.common.util.awt.AWTEDTExecutor;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowAdapter;
import javax.swing.MenuSelectionManager;
import java.awt.Component;
import java.lang.reflect.Method;
import jogamp.opengl.awt.AWTTilePainter;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLAnimatorControl;
import java.awt.KeyboardFocusManager;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.nativewindow.awt.AWTWindowClosingProtocol;
import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.newt.event.awt.AWTAdapter;
import jogamp.newt.awt.event.AWTParentWindowAdapter;
import com.jogamp.newt.Window;
import com.jogamp.nativewindow.awt.JAWTWindow;
import com.jogamp.nativewindow.awt.AWTPrintLifecycle;
import com.jogamp.nativewindow.OffscreenLayerOption;
import com.jogamp.nativewindow.WindowClosingProtocol;
import java.awt.Canvas;

public class NewtCanvasAWT extends Canvas implements WindowClosingProtocol, OffscreenLayerOption, AWTPrintLifecycle
{
    public static final boolean DEBUG;
    private final Object sync;
    private volatile JAWTWindow jawtWindow;
    private boolean isApplet;
    private boolean shallUseOffscreenLayer;
    private Window newtChild;
    private boolean newtChildAttached;
    private boolean isOnscreen;
    private WindowClosingMode newtChildCloseOp;
    private final AWTParentWindowAdapter awtWinAdapter;
    private final AWTAdapter awtMouseAdapter;
    private final AWTAdapter awtKeyAdapter;
    private volatile AWTGraphicsConfiguration awtConfig;
    private boolean destroyJAWTPending;
    private boolean skipJAWTDestroy;
    private volatile boolean componentAdded;
    private final AWTWindowClosingProtocol awtWindowClosingProtocol;
    private final FocusAction focusAction;
    private static final Runnable awtClearGlobalFocusOwner;
    private final Runnable awtClearSelectedMenuPath;
    private final WindowListener clearAWTMenusOnNewtFocus;
    private final FocusTraversalKeyListener newtFocusTraversalKeyListener;
    private final FocusPropertyChangeListener focusPropertyChangeListener;
    private volatile KeyboardFocusManager keyboardFocusManager;
    private volatile boolean printActive;
    private GLAnimatorControl printAnimator;
    private GLAutoDrawable printGLAD;
    private AWTTilePainter printAWTTiles;
    private final Runnable setupPrintOnEDT;
    private final Runnable releasePrintOnEDT;
    private final Runnable forceRelayout;
    private static boolean disableBackgroundEraseInitialized;
    private static Method disableBackgroundEraseMethod;
    
    public NewtCanvasAWT() {
        this.sync = new Object();
        this.jawtWindow = null;
        this.isApplet = false;
        this.shallUseOffscreenLayer = false;
        this.newtChild = null;
        this.newtChildAttached = false;
        this.isOnscreen = true;
        this.destroyJAWTPending = false;
        this.skipJAWTDestroy = false;
        this.componentAdded = false;
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded) {
                    NewtCanvasAWT.this.destroyImpl(false, true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded && NewtCanvasAWT.this.newtChild != null) {
                    NewtCanvasAWT.this.newtChild.sendWindowEvent(102);
                }
            }
        });
        this.focusAction = new FocusAction();
        this.awtClearSelectedMenuPath = new Runnable() {
            @Override
            public void run() {
                MenuSelectionManager.defaultManager().clearSelectedPath();
            }
        };
        this.clearAWTMenusOnNewtFocus = new WindowAdapter() {
            @Override
            public void windowResized(final WindowEvent windowEvent) {
                NewtCanvasAWT.this.updateLayoutSize();
            }
            
            @Override
            public void windowGainedFocus(final WindowEvent windowEvent) {
                if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                    AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.this.awtClearSelectedMenuPath);
                }
            }
        };
        this.newtFocusTraversalKeyListener = new FocusTraversalKeyListener();
        this.focusPropertyChangeListener = new FocusPropertyChangeListener();
        this.keyboardFocusManager = null;
        this.printActive = false;
        this.printAnimator = null;
        this.printGLAD = null;
        this.printAWTTiles = null;
        this.setupPrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (!NewtCanvasAWT.this.validateComponent(true)) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, drawable not valid yet");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    if (!NewtCanvasAWT.this.isVisible()) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, canvas not visible");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    if (null == access$1600) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println("AWT print.setup exit, newtChild not a GLAutoDrawable: " + NewtCanvasAWT.this.newtChild);
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    NewtCanvasAWT.this.printAnimator = access$1600.getAnimator();
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.remove(access$1600);
                    }
                    NewtCanvasAWT.this.printGLAD = access$1600;
                    final GLCapabilitiesImmutable chosenGLCapabilities = access$1600.getChosenGLCapabilities();
                    final int numSamples = NewtCanvasAWT.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1 && NewtCanvasAWT.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1 && NewtCanvasAWT.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final boolean onscreen = chosenGLCapabilities.isOnscreen();
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(access$1600.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (onscreen || b || b2) && swapGLContextSafe;
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ onscreen " + onscreen + ", samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + NewtCanvasAWT.this.printAWTTiles.customTileWidth + "x" + NewtCanvasAWT.this.printAWTTiles.customTileHeight + ", scaleMat " + NewtCanvasAWT.this.printAWTTiles.scaleMatX + " x " + NewtCanvasAWT.this.printAWTTiles.scaleMatY + ", numSamples " + NewtCanvasAWT.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + NewtCanvasAWT.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileWidth : 1024, (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (NewtCanvasAWT.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            NewtCanvasAWT.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(access$1600, NewtCanvasAWT.this.printGLAD);
                            glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    NewtCanvasAWT.this.printAWTTiles.setGLOrientation(NewtCanvasAWT.this.printGLAD.isGLOriented(), NewtCanvasAWT.this.printGLAD.isGLOriented());
                    NewtCanvasAWT.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    NewtCanvasAWT.this.printAWTTiles.renderer.attachAutoDrawable(NewtCanvasAWT.this.printGLAD);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup " + NewtCanvasAWT.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + NewtCanvasAWT.this.printGLAD.getSurfaceWidth() + "x" + NewtCanvasAWT.this.printGLAD.getSurfaceHeight() + ", " + NewtCanvasAWT.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.release " + NewtCanvasAWT.this.printAWTTiles);
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    NewtCanvasAWT.this.printAWTTiles.dispose();
                    NewtCanvasAWT.this.printAWTTiles = null;
                    if (NewtCanvasAWT.this.printGLAD != access$1600) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(NewtCanvasAWT.this.printGLAD, access$1600);
                        NewtCanvasAWT.this.printGLAD.destroy();
                    }
                    NewtCanvasAWT.this.printGLAD = null;
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.add(access$1600);
                        NewtCanvasAWT.this.printAnimator = null;
                    }
                    NewtCanvasAWT.this.printActive = false;
                }
            }
        };
        this.forceRelayout = new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.0");
                }
                final NewtCanvasAWT this$0 = NewtCanvasAWT.this;
                final int width = this$0.getWidth();
                final int height = this$0.getHeight();
                this$0.setSize(width + 1, height + 1);
                this$0.setSize(width, height);
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.X");
                }
            }
        };
        this.awtMouseAdapter = new AWTMouseAdapter().addTo(this);
        this.awtKeyAdapter = new AWTKeyAdapter().addTo(this);
        (this.awtWinAdapter = (AWTParentWindowAdapter)new AWTParentWindowAdapter().addTo(this)).removeWindowClosingFrom(this);
    }
    
    public NewtCanvasAWT(final GraphicsConfiguration graphicsConfiguration) {
        super(graphicsConfiguration);
        this.sync = new Object();
        this.jawtWindow = null;
        this.isApplet = false;
        this.shallUseOffscreenLayer = false;
        this.newtChild = null;
        this.newtChildAttached = false;
        this.isOnscreen = true;
        this.destroyJAWTPending = false;
        this.skipJAWTDestroy = false;
        this.componentAdded = false;
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded) {
                    NewtCanvasAWT.this.destroyImpl(false, true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded && NewtCanvasAWT.this.newtChild != null) {
                    NewtCanvasAWT.this.newtChild.sendWindowEvent(102);
                }
            }
        });
        this.focusAction = new FocusAction();
        this.awtClearSelectedMenuPath = new Runnable() {
            @Override
            public void run() {
                MenuSelectionManager.defaultManager().clearSelectedPath();
            }
        };
        this.clearAWTMenusOnNewtFocus = new WindowAdapter() {
            @Override
            public void windowResized(final WindowEvent windowEvent) {
                NewtCanvasAWT.this.updateLayoutSize();
            }
            
            @Override
            public void windowGainedFocus(final WindowEvent windowEvent) {
                if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                    AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.this.awtClearSelectedMenuPath);
                }
            }
        };
        this.newtFocusTraversalKeyListener = new FocusTraversalKeyListener();
        this.focusPropertyChangeListener = new FocusPropertyChangeListener();
        this.keyboardFocusManager = null;
        this.printActive = false;
        this.printAnimator = null;
        this.printGLAD = null;
        this.printAWTTiles = null;
        this.setupPrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (!NewtCanvasAWT.this.validateComponent(true)) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, drawable not valid yet");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    if (!NewtCanvasAWT.this.isVisible()) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, canvas not visible");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    if (null == access$1600) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println("AWT print.setup exit, newtChild not a GLAutoDrawable: " + NewtCanvasAWT.this.newtChild);
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    NewtCanvasAWT.this.printAnimator = access$1600.getAnimator();
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.remove(access$1600);
                    }
                    NewtCanvasAWT.this.printGLAD = access$1600;
                    final GLCapabilitiesImmutable chosenGLCapabilities = access$1600.getChosenGLCapabilities();
                    final int numSamples = NewtCanvasAWT.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1 && NewtCanvasAWT.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1 && NewtCanvasAWT.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final boolean onscreen = chosenGLCapabilities.isOnscreen();
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(access$1600.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (onscreen || b || b2) && swapGLContextSafe;
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ onscreen " + onscreen + ", samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + NewtCanvasAWT.this.printAWTTiles.customTileWidth + "x" + NewtCanvasAWT.this.printAWTTiles.customTileHeight + ", scaleMat " + NewtCanvasAWT.this.printAWTTiles.scaleMatX + " x " + NewtCanvasAWT.this.printAWTTiles.scaleMatY + ", numSamples " + NewtCanvasAWT.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + NewtCanvasAWT.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileWidth : 1024, (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (NewtCanvasAWT.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            NewtCanvasAWT.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(access$1600, NewtCanvasAWT.this.printGLAD);
                            glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    NewtCanvasAWT.this.printAWTTiles.setGLOrientation(NewtCanvasAWT.this.printGLAD.isGLOriented(), NewtCanvasAWT.this.printGLAD.isGLOriented());
                    NewtCanvasAWT.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    NewtCanvasAWT.this.printAWTTiles.renderer.attachAutoDrawable(NewtCanvasAWT.this.printGLAD);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup " + NewtCanvasAWT.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + NewtCanvasAWT.this.printGLAD.getSurfaceWidth() + "x" + NewtCanvasAWT.this.printGLAD.getSurfaceHeight() + ", " + NewtCanvasAWT.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.release " + NewtCanvasAWT.this.printAWTTiles);
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    NewtCanvasAWT.this.printAWTTiles.dispose();
                    NewtCanvasAWT.this.printAWTTiles = null;
                    if (NewtCanvasAWT.this.printGLAD != access$1600) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(NewtCanvasAWT.this.printGLAD, access$1600);
                        NewtCanvasAWT.this.printGLAD.destroy();
                    }
                    NewtCanvasAWT.this.printGLAD = null;
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.add(access$1600);
                        NewtCanvasAWT.this.printAnimator = null;
                    }
                    NewtCanvasAWT.this.printActive = false;
                }
            }
        };
        this.forceRelayout = new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.0");
                }
                final NewtCanvasAWT this$0 = NewtCanvasAWT.this;
                final int width = this$0.getWidth();
                final int height = this$0.getHeight();
                this$0.setSize(width + 1, height + 1);
                this$0.setSize(width, height);
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.X");
                }
            }
        };
        this.awtMouseAdapter = new AWTMouseAdapter().addTo(this);
        this.awtKeyAdapter = new AWTKeyAdapter().addTo(this);
        (this.awtWinAdapter = (AWTParentWindowAdapter)new AWTParentWindowAdapter().addTo(this)).removeWindowClosingFrom(this);
    }
    
    public NewtCanvasAWT(final Window newtChild) {
        this.sync = new Object();
        this.jawtWindow = null;
        this.isApplet = false;
        this.shallUseOffscreenLayer = false;
        this.newtChild = null;
        this.newtChildAttached = false;
        this.isOnscreen = true;
        this.destroyJAWTPending = false;
        this.skipJAWTDestroy = false;
        this.componentAdded = false;
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded) {
                    NewtCanvasAWT.this.destroyImpl(false, true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded && NewtCanvasAWT.this.newtChild != null) {
                    NewtCanvasAWT.this.newtChild.sendWindowEvent(102);
                }
            }
        });
        this.focusAction = new FocusAction();
        this.awtClearSelectedMenuPath = new Runnable() {
            @Override
            public void run() {
                MenuSelectionManager.defaultManager().clearSelectedPath();
            }
        };
        this.clearAWTMenusOnNewtFocus = new WindowAdapter() {
            @Override
            public void windowResized(final WindowEvent windowEvent) {
                NewtCanvasAWT.this.updateLayoutSize();
            }
            
            @Override
            public void windowGainedFocus(final WindowEvent windowEvent) {
                if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                    AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.this.awtClearSelectedMenuPath);
                }
            }
        };
        this.newtFocusTraversalKeyListener = new FocusTraversalKeyListener();
        this.focusPropertyChangeListener = new FocusPropertyChangeListener();
        this.keyboardFocusManager = null;
        this.printActive = false;
        this.printAnimator = null;
        this.printGLAD = null;
        this.printAWTTiles = null;
        this.setupPrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (!NewtCanvasAWT.this.validateComponent(true)) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, drawable not valid yet");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    if (!NewtCanvasAWT.this.isVisible()) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, canvas not visible");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    if (null == access$1600) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println("AWT print.setup exit, newtChild not a GLAutoDrawable: " + NewtCanvasAWT.this.newtChild);
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    NewtCanvasAWT.this.printAnimator = access$1600.getAnimator();
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.remove(access$1600);
                    }
                    NewtCanvasAWT.this.printGLAD = access$1600;
                    final GLCapabilitiesImmutable chosenGLCapabilities = access$1600.getChosenGLCapabilities();
                    final int numSamples = NewtCanvasAWT.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1 && NewtCanvasAWT.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1 && NewtCanvasAWT.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final boolean onscreen = chosenGLCapabilities.isOnscreen();
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(access$1600.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (onscreen || b || b2) && swapGLContextSafe;
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ onscreen " + onscreen + ", samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + NewtCanvasAWT.this.printAWTTiles.customTileWidth + "x" + NewtCanvasAWT.this.printAWTTiles.customTileHeight + ", scaleMat " + NewtCanvasAWT.this.printAWTTiles.scaleMatX + " x " + NewtCanvasAWT.this.printAWTTiles.scaleMatY + ", numSamples " + NewtCanvasAWT.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + NewtCanvasAWT.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileWidth : 1024, (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (NewtCanvasAWT.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            NewtCanvasAWT.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(access$1600, NewtCanvasAWT.this.printGLAD);
                            glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    NewtCanvasAWT.this.printAWTTiles.setGLOrientation(NewtCanvasAWT.this.printGLAD.isGLOriented(), NewtCanvasAWT.this.printGLAD.isGLOriented());
                    NewtCanvasAWT.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    NewtCanvasAWT.this.printAWTTiles.renderer.attachAutoDrawable(NewtCanvasAWT.this.printGLAD);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup " + NewtCanvasAWT.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + NewtCanvasAWT.this.printGLAD.getSurfaceWidth() + "x" + NewtCanvasAWT.this.printGLAD.getSurfaceHeight() + ", " + NewtCanvasAWT.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.release " + NewtCanvasAWT.this.printAWTTiles);
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    NewtCanvasAWT.this.printAWTTiles.dispose();
                    NewtCanvasAWT.this.printAWTTiles = null;
                    if (NewtCanvasAWT.this.printGLAD != access$1600) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(NewtCanvasAWT.this.printGLAD, access$1600);
                        NewtCanvasAWT.this.printGLAD.destroy();
                    }
                    NewtCanvasAWT.this.printGLAD = null;
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.add(access$1600);
                        NewtCanvasAWT.this.printAnimator = null;
                    }
                    NewtCanvasAWT.this.printActive = false;
                }
            }
        };
        this.forceRelayout = new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.0");
                }
                final NewtCanvasAWT this$0 = NewtCanvasAWT.this;
                final int width = this$0.getWidth();
                final int height = this$0.getHeight();
                this$0.setSize(width + 1, height + 1);
                this$0.setSize(width, height);
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.X");
                }
            }
        };
        this.awtMouseAdapter = new AWTMouseAdapter().addTo(this);
        this.awtKeyAdapter = new AWTKeyAdapter().addTo(this);
        (this.awtWinAdapter = (AWTParentWindowAdapter)new AWTParentWindowAdapter().addTo(this)).removeWindowClosingFrom(this);
        this.setNEWTChild(newtChild);
    }
    
    public NewtCanvasAWT(final GraphicsConfiguration graphicsConfiguration, final Window newtChild) {
        super(graphicsConfiguration);
        this.sync = new Object();
        this.jawtWindow = null;
        this.isApplet = false;
        this.shallUseOffscreenLayer = false;
        this.newtChild = null;
        this.newtChildAttached = false;
        this.isOnscreen = true;
        this.destroyJAWTPending = false;
        this.skipJAWTDestroy = false;
        this.componentAdded = false;
        this.awtWindowClosingProtocol = new AWTWindowClosingProtocol(this, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded) {
                    NewtCanvasAWT.this.destroyImpl(false, true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.this.componentAdded && NewtCanvasAWT.this.newtChild != null) {
                    NewtCanvasAWT.this.newtChild.sendWindowEvent(102);
                }
            }
        });
        this.focusAction = new FocusAction();
        this.awtClearSelectedMenuPath = new Runnable() {
            @Override
            public void run() {
                MenuSelectionManager.defaultManager().clearSelectedPath();
            }
        };
        this.clearAWTMenusOnNewtFocus = new WindowAdapter() {
            @Override
            public void windowResized(final WindowEvent windowEvent) {
                NewtCanvasAWT.this.updateLayoutSize();
            }
            
            @Override
            public void windowGainedFocus(final WindowEvent windowEvent) {
                if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                    AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.this.awtClearSelectedMenuPath);
                }
            }
        };
        this.newtFocusTraversalKeyListener = new FocusTraversalKeyListener();
        this.focusPropertyChangeListener = new FocusPropertyChangeListener();
        this.keyboardFocusManager = null;
        this.printActive = false;
        this.printAnimator = null;
        this.printGLAD = null;
        this.printAWTTiles = null;
        this.setupPrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (!NewtCanvasAWT.this.validateComponent(true)) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, drawable not valid yet");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    if (!NewtCanvasAWT.this.isVisible()) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println(NewtCanvasAWT.currentThreadName() + ": Info: NewtCanvasAWT setupPrint - skipped GL render, canvas not visible");
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    if (null == access$1600) {
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println("AWT print.setup exit, newtChild not a GLAutoDrawable: " + NewtCanvasAWT.this.newtChild);
                        }
                        NewtCanvasAWT.this.printActive = false;
                        return;
                    }
                    NewtCanvasAWT.this.printAnimator = access$1600.getAnimator();
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.remove(access$1600);
                    }
                    NewtCanvasAWT.this.printGLAD = access$1600;
                    final GLCapabilitiesImmutable chosenGLCapabilities = access$1600.getChosenGLCapabilities();
                    final int numSamples = NewtCanvasAWT.this.printAWTTiles.getNumSamples(chosenGLCapabilities);
                    GLDrawable glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                    final boolean b = numSamples != chosenGLCapabilities.getNumSamples();
                    final boolean b2 = (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1 && NewtCanvasAWT.this.printAWTTiles.customTileWidth != glDrawable.getSurfaceWidth()) || (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1 && NewtCanvasAWT.this.printAWTTiles.customTileHeight != glDrawable.getSurfaceHeight());
                    final boolean onscreen = chosenGLCapabilities.isOnscreen();
                    final GLCapabilities glCapabilities = (GLCapabilities)chosenGLCapabilities.cloneMutable();
                    glCapabilities.setDoubleBuffered(false);
                    glCapabilities.setOnscreen(false);
                    if (numSamples != glCapabilities.getNumSamples()) {
                        glCapabilities.setSampleBuffers(0 < numSamples);
                        glCapabilities.setNumSamples(numSamples);
                    }
                    final boolean swapGLContextSafe = GLDrawableUtil.isSwapGLContextSafe(access$1600.getRequestedGLCapabilities(), chosenGLCapabilities, glCapabilities);
                    final boolean b3 = (onscreen || b || b2) && swapGLContextSafe;
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup: reqNewGLAD " + b3 + "[ onscreen " + onscreen + ", samples " + b + ", size " + b2 + ", safe " + swapGLContextSafe + "], " + ", drawableSize " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", customTileSize " + NewtCanvasAWT.this.printAWTTiles.customTileWidth + "x" + NewtCanvasAWT.this.printAWTTiles.customTileHeight + ", scaleMat " + NewtCanvasAWT.this.printAWTTiles.scaleMatX + " x " + NewtCanvasAWT.this.printAWTTiles.scaleMatY + ", numSamples " + NewtCanvasAWT.this.printAWTTiles.customNumSamples + " -> " + numSamples + ", printAnimator " + NewtCanvasAWT.this.printAnimator);
                    }
                    if (b3) {
                        final GLDrawableFactory factory = GLDrawableFactory.getFactory(glCapabilities.getGLProfile());
                        GLAutoDrawable offscreenAutoDrawable = null;
                        try {
                            offscreenAutoDrawable = factory.createOffscreenAutoDrawable(null, glCapabilities, null, (NewtCanvasAWT.this.printAWTTiles.customTileWidth != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileWidth : 1024, (NewtCanvasAWT.this.printAWTTiles.customTileHeight != -1) ? NewtCanvasAWT.this.printAWTTiles.customTileHeight : 1024);
                        }
                        catch (GLException ex) {
                            if (NewtCanvasAWT.DEBUG) {
                                System.err.println("Caught: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                        if (null != offscreenAutoDrawable) {
                            NewtCanvasAWT.this.printGLAD = offscreenAutoDrawable;
                            GLDrawableUtil.swapGLContextAndAllGLEventListener(access$1600, NewtCanvasAWT.this.printGLAD);
                            glDrawable = NewtCanvasAWT.this.printGLAD.getDelegatedDrawable();
                        }
                    }
                    NewtCanvasAWT.this.printAWTTiles.setGLOrientation(NewtCanvasAWT.this.printGLAD.isGLOriented(), NewtCanvasAWT.this.printGLAD.isGLOriented());
                    NewtCanvasAWT.this.printAWTTiles.renderer.setTileSize(glDrawable.getSurfaceWidth(), glDrawable.getSurfaceHeight(), 0);
                    NewtCanvasAWT.this.printAWTTiles.renderer.attachAutoDrawable(NewtCanvasAWT.this.printGLAD);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.setup " + NewtCanvasAWT.this.printAWTTiles);
                        System.err.println("AWT print.setup AA " + numSamples + ", " + glCapabilities);
                        System.err.println("AWT print.setup printGLAD: " + NewtCanvasAWT.this.printGLAD.getSurfaceWidth() + "x" + NewtCanvasAWT.this.printGLAD.getSurfaceHeight() + ", " + NewtCanvasAWT.this.printGLAD);
                        System.err.println("AWT print.setup printDraw: " + glDrawable.getSurfaceWidth() + "x" + glDrawable.getSurfaceHeight() + ", " + glDrawable);
                    }
                }
            }
        };
        this.releasePrintOnEDT = new Runnable() {
            @Override
            public void run() {
                synchronized (NewtCanvasAWT.this.sync) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("AWT print.release " + NewtCanvasAWT.this.printAWTTiles);
                    }
                    final GLAutoDrawable access$1600 = NewtCanvasAWT.this.getGLAD();
                    NewtCanvasAWT.this.printAWTTiles.dispose();
                    NewtCanvasAWT.this.printAWTTiles = null;
                    if (NewtCanvasAWT.this.printGLAD != access$1600) {
                        GLDrawableUtil.swapGLContextAndAllGLEventListener(NewtCanvasAWT.this.printGLAD, access$1600);
                        NewtCanvasAWT.this.printGLAD.destroy();
                    }
                    NewtCanvasAWT.this.printGLAD = null;
                    if (null != NewtCanvasAWT.this.printAnimator) {
                        NewtCanvasAWT.this.printAnimator.add(access$1600);
                        NewtCanvasAWT.this.printAnimator = null;
                    }
                    NewtCanvasAWT.this.printActive = false;
                }
            }
        };
        this.forceRelayout = new Runnable() {
            @Override
            public void run() {
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.0");
                }
                final NewtCanvasAWT this$0 = NewtCanvasAWT.this;
                final int width = this$0.getWidth();
                final int height = this$0.getHeight();
                this$0.setSize(width + 1, height + 1);
                this$0.setSize(width, height);
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.forceRelayout.X");
                }
            }
        };
        this.awtMouseAdapter = new AWTMouseAdapter().addTo(this);
        this.awtKeyAdapter = new AWTKeyAdapter().addTo(this);
        (this.awtWinAdapter = (AWTParentWindowAdapter)new AWTParentWindowAdapter().addTo(this)).removeWindowClosingFrom(this);
        this.setNEWTChild(newtChild);
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
    
    public final boolean isApplet() {
        return this.isApplet;
    }
    
    private final boolean isParent() {
        final Window newtChild = this.newtChild;
        return null != newtChild && this.jawtWindow == newtChild.getParent();
    }
    
    private final boolean isFullscreen() {
        final Window newtChild = this.newtChild;
        return null != newtChild && newtChild.isFullscreen();
    }
    
    private final void requestFocusNEWTChild() {
        if (null != this.newtChild) {
            this.newtChild.setFocusAction(null);
            if (this.isOnscreen) {
                AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.awtClearGlobalFocusOwner);
            }
            this.newtChild.requestFocus();
            this.newtChild.setFocusAction(this.focusAction);
        }
    }
    
    public Window setNEWTChild(final Window newtChild) {
        synchronized (this.sync) {
            final Window newtChild2 = this.newtChild;
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.setNEWTChild.0: win " + newtWinHandleToHexString(newtChild2) + " -> " + newtWinHandleToHexString(newtChild));
            }
            final Container container = AWTMisc.getContainer(this);
            if (null != this.newtChild) {
                this.detachNewtChild(container);
                this.newtChild = null;
            }
            this.newtChild = newtChild;
            this.updateLayoutSize();
            return newtChild2;
        }
    }
    
    private final void updateLayoutSize() {
        final Window newtChild = this.newtChild;
        if (null != newtChild) {
            final Dimension dimension = new Dimension(newtChild.getWidth(), newtChild.getHeight());
            this.setMinimumSize(dimension);
            this.setPreferredSize(dimension);
        }
    }
    
    public Window getNEWTChild() {
        return this.newtChild;
    }
    
    public NativeWindow getNativeWindow() {
        return this.jawtWindow;
    }
    
    @Override
    public WindowClosingMode getDefaultCloseOperation() {
        return this.awtWindowClosingProtocol.getDefaultCloseOperation();
    }
    
    @Override
    public WindowClosingMode setDefaultCloseOperation(final WindowClosingMode defaultCloseOperation) {
        return this.awtWindowClosingProtocol.setDefaultCloseOperation(defaultCloseOperation);
    }
    
    public final void setSkipJAWTDestroy(final boolean skipJAWTDestroy) {
        this.skipJAWTDestroy = skipJAWTDestroy;
    }
    
    public final boolean getSkipJAWTDestroy() {
        return this.skipJAWTDestroy;
    }
    
    private final void determineIfApplet() {
        this.isApplet = false;
        for (Component parent = this; !this.isApplet && null != parent; parent = parent.getParent()) {
            this.isApplet = (parent instanceof Applet);
        }
    }
    
    private void setAWTGraphicsConfiguration(final AWTGraphicsConfiguration awtGraphicsConfiguration) {
        this.awtConfig = awtGraphicsConfiguration;
        if (null != this.jawtWindow) {
            this.jawtWindow.setAWTGraphicsConfiguration(awtGraphicsConfiguration);
        }
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
                final AWTGraphicsConfiguration create = AWTGraphicsConfiguration.create(graphicsConfiguration, this.awtConfig.getChosenCapabilities(), this.awtConfig.getRequestedCapabilities());
                final GraphicsConfiguration awtGraphicsConfiguration = create.getAWTGraphicsConfiguration();
                final boolean equals = create.getChosenCapabilities().equals(this.awtConfig.getChosenCapabilities());
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println(getThreadName() + ": getGraphicsConfiguration() Info: Changed GC and GD");
                    System.err.println("Created Config (n): Old     GC " + graphicsConfiguration2);
                    System.err.println("Created Config (n): Old     GD " + graphicsConfiguration2.getDevice().getIDstring());
                    System.err.println("Created Config (n): Parent  GC " + graphicsConfiguration);
                    System.err.println("Created Config (n): Parent  GD " + graphicsConfiguration.getDevice().getIDstring());
                    System.err.println("Created Config (n): New     GC " + awtGraphicsConfiguration);
                    System.err.println("Created Config (n): Old     CF " + this.awtConfig);
                    System.err.println("Created Config (n): New     CF " + create);
                    System.err.println("Created Config (n): EQUALS CAPS " + equals);
                }
                if (null != awtGraphicsConfiguration) {
                    this.setAWTGraphicsConfiguration(create);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println(getThreadName() + ": Info: getGraphicsConfiguration - end.01: newGC " + awtGraphicsConfiguration);
                    }
                    return awtGraphicsConfiguration;
                }
                if (NewtCanvasAWT.DEBUG) {
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
    
    private static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    @Override
    public void addNotify() {
        if (Beans.isDesignTime()) {
            super.addNotify();
        }
        else {
            this.disableBackgroundErase();
            final GraphicsConfiguration graphicsConfiguration = super.getGraphicsConfiguration();
            if (null == graphicsConfiguration) {
                throw new GLException("Error: NULL AWT GraphicsConfiguration");
            }
            final AWTGraphicsConfiguration create = AWTGraphicsConfiguration.create(graphicsConfiguration, null, (null != this.newtChild) ? this.newtChild.getRequestedCapabilities() : null);
            if (null == create) {
                throw new GLException("Error: NULL AWTGraphicsConfiguration");
            }
            this.setAWTGraphicsConfiguration(create);
            super.addNotify();
            this.disableBackgroundErase();
            synchronized (this.sync) {
                this.determineIfApplet();
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.addNotify.0 - isApplet " + this.isApplet + ", addedOnAWTEDT " + EventQueue.isDispatchThread() + " @ " + currentThreadName());
                    ExceptionUtils.dumpStack(System.err);
                }
                (this.jawtWindow = NewtFactoryAWT.getNativeWindow(this, create)).setShallUseOffscreenLayer(this.shallUseOffscreenLayer);
                this.jawtWindow.lockSurface();
                this.jawtWindow.unlockSurface();
                this.awtWindowClosingProtocol.addClosingListener();
                this.componentAdded = true;
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.addNotify.X: twin " + newtWinHandleToHexString(this.newtChild) + ", comp " + this + ", visible " + this.isVisible() + ", showing " + this.isShowing() + ", displayable " + this.isDisplayable() + ", cont " + AWTMisc.getContainer(this));
                }
            }
        }
    }
    
    private final boolean updatePixelScale(final GraphicsConfiguration graphicsConfiguration) {
        if (this.jawtWindow.updatePixelScale(graphicsConfiguration, true)) {
            final Window delegatedWindow = this.newtChild.getDelegatedWindow();
            if (delegatedWindow instanceof WindowImpl) {
                ((WindowImpl)delegatedWindow).pixelScaleChangeNotify(this.jawtWindow.getMinimumSurfaceScale(new float[2]), this.jawtWindow.getMaximumSurfaceScale(new float[2]), true);
            }
            else if (this.jawtWindow.setSurfaceScale(this.jawtWindow.getRequestedSurfaceScale(new float[2]))) {}
            return true;
        }
        return false;
    }
    
    @Override
    public void removeNotify() {
        if (Beans.isDesignTime()) {
            super.removeNotify();
        }
        else {
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.removeNotify.0 - isApplet " + this.isApplet + " @ " + currentThreadName());
                ExceptionUtils.dumpStack(System.err);
            }
            this.componentAdded = false;
            this.awtWindowClosingProtocol.removeClosingListener();
            this.destroyImpl(true, false);
            super.removeNotify();
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.removeNotify.X @ " + currentThreadName());
            }
        }
    }
    
    public final void destroy() {
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.destroy() @ " + currentThreadName());
            ExceptionUtils.dumpStack(System.err);
        }
        AWTEDTExecutor.singleton.invoke(true, new Runnable() {
            @Override
            public void run() {
                NewtCanvasAWT.this.destroyImpl(false, false);
            }
        });
    }
    
    private final void destroyImpl(final boolean b, final boolean b2) {
        synchronized (this.sync) {
            final Container container = AWTMisc.getContainer(this);
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.destroyImpl @ " + currentThreadName());
                System.err.println("NewtCanvasAWT.destroyImpl.0 - isApplet " + this.isApplet + ", isOnAWTEDT " + EventQueue.isDispatchThread() + ", skipJAWTDestroy " + this.skipJAWTDestroy + "; removeNotify " + b + ", windowClosing " + b2 + ", destroyJAWTPending " + this.destroyJAWTPending + ", hasJAWT " + (null != this.jawtWindow) + ", hasNEWT " + (null != this.newtChild) + "): nw " + newtWinHandleToHexString(this.newtChild) + ", from " + container);
            }
            if (null != this.newtChild) {
                this.detachNewtChild(container);
                if (!b) {
                    final Window newtChild = this.newtChild;
                    final Window delegatedWindow = newtChild.getDelegatedWindow();
                    this.newtChild = null;
                    if (b2 && delegatedWindow instanceof WindowImpl) {
                        ((WindowImpl)delegatedWindow).windowDestroyNotify(true);
                    }
                    else {
                        newtChild.destroy();
                    }
                }
            }
            if ((this.destroyJAWTPending || b || b2) && null != this.jawtWindow) {
                if (this.skipJAWTDestroy) {
                    this.destroyJAWTPending = true;
                }
                else {
                    NewtFactoryAWT.destroyNativeWindow(this.jawtWindow);
                    this.jawtWindow = null;
                    this.awtConfig = null;
                    this.destroyJAWTPending = false;
                }
            }
        }
    }
    
    @Override
    public void paint(final Graphics graphics) {
        synchronized (this.sync) {
            if (this.validateComponent(true) && !this.printActive) {
                this.newtChild.windowRepaint(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
    
    @Override
    public void update(final Graphics graphics) {
        this.paint(graphics);
    }
    
    @Override
    public void reshape(final int n, final int n2, final int n3, final int n4) {
        synchronized (this.getTreeLock()) {
            synchronized (this.sync) {
                super.reshape(n, n2, n3, n4);
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("NewtCanvasAWT.reshape: " + n + "/" + n2 + " " + n3 + "x" + n4);
                }
                if (!this.validateComponent(true) || this.printActive || this.updatePixelScale(this.getGraphicsConfiguration())) {}
            }
        }
    }
    
    private final GLAutoDrawable getGLAD() {
        if (null != this.newtChild && this.newtChild instanceof GLAutoDrawable) {
            return (GLAutoDrawable)this.newtChild;
        }
        return null;
    }
    
    @Override
    public void setupPrint(final double n, final double n2, final int n3, final int n4, final int n5) {
        this.printActive = true;
        this.printAWTTiles = new AWTTilePainter(new TileRenderer(), this.isOpaque() ? 3 : 4, n, n2, n3, n4, n5, NewtCanvasAWT.DEBUG);
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.setupPrintOnEDT);
    }
    
    @Override
    public void releasePrint() {
        if (!this.printActive || null == this.printGLAD) {
            throw new IllegalStateException("setupPrint() not called");
        }
        AWTEDTExecutor.singleton.invoke(this.getTreeLock(), true, true, this.releasePrintOnEDT);
        this.newtChild.sendWindowEvent(100);
    }
    
    @Override
    public void print(final Graphics graphics) {
        synchronized (this.sync) {
            if (!this.printActive || null == this.printGLAD) {
                throw new IllegalStateException("setupPrint() not called");
            }
            if (NewtCanvasAWT.DEBUG && !EventQueue.isDispatchThread()) {
                System.err.println(currentThreadName() + ": Warning: GLCanvas print - not called from AWT-EDT");
            }
            final Graphics2D graphics2D = (Graphics2D)graphics;
            try {
                this.printAWTTiles.setupGraphics2DAndClipBounds(graphics2D, this.getWidth(), this.getHeight());
                final TileRenderer renderer = this.printAWTTiles.renderer;
                if (NewtCanvasAWT.DEBUG) {
                    System.err.println("AWT print.0: " + renderer);
                }
                if (!renderer.eot()) {
                    try {
                        do {
                            renderer.display();
                        } while (!renderer.eot());
                        if (NewtCanvasAWT.DEBUG) {
                            System.err.println("AWT print.1: " + this.printAWTTiles);
                        }
                        renderer.reset();
                    }
                    finally {
                        this.printAWTTiles.resetGraphics2D();
                    }
                }
            }
            catch (NoninvertibleTransformException ex) {
                System.err.println("Caught: Inversion failed of: " + graphics2D.getTransform());
                ex.printStackTrace();
            }
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("AWT print.X: " + this.printAWTTiles);
            }
        }
    }
    
    private final boolean validateComponent(final boolean b) {
        if (Beans.isDesignTime() || !this.isDisplayable()) {
            return false;
        }
        if (null == this.newtChild || null == this.jawtWindow) {
            return false;
        }
        if (0 >= this.getWidth() || 0 >= this.getHeight()) {
            return false;
        }
        if (b && !this.newtChildAttached && null != this.newtChild) {
            this.attachNewtChild();
        }
        return true;
    }
    
    private final void configureNewtChild(final boolean b) {
        this.awtWinAdapter.clear();
        this.awtKeyAdapter.clear();
        this.awtMouseAdapter.clear();
        if (null != this.keyboardFocusManager) {
            this.keyboardFocusManager.removePropertyChangeListener("focusOwner", this.focusPropertyChangeListener);
            this.keyboardFocusManager = null;
        }
        if (null != this.newtChild) {
            this.newtChild.setKeyboardFocusHandler(null);
            if (b) {
                if (null == this.jawtWindow.getGraphicsConfiguration()) {
                    throw new InternalError("XXX");
                }
                this.isOnscreen = this.jawtWindow.getGraphicsConfiguration().getChosenCapabilities().isOnscreen();
                this.awtWinAdapter.setDownstream(this.jawtWindow, this.newtChild);
                this.newtChild.addWindowListener(this.clearAWTMenusOnNewtFocus);
                this.newtChild.setFocusAction(this.focusAction);
                this.newtChildCloseOp = this.newtChild.setDefaultCloseOperation(WindowClosingMode.DO_NOTHING_ON_CLOSE);
                (this.keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager()).addPropertyChangeListener("focusOwner", this.focusPropertyChangeListener);
                this.setFocusable(true);
                if (this.isOnscreen) {
                    this.newtChild.setKeyboardFocusHandler(this.newtFocusTraversalKeyListener);
                }
                else {
                    this.awtMouseAdapter.setDownstream(this.newtChild);
                    this.awtKeyAdapter.setDownstream(this.newtChild);
                    this.awtKeyAdapter.setConsumeAWTEvent(true);
                }
            }
            else {
                this.newtChild.removeWindowListener(this.clearAWTMenusOnNewtFocus);
                this.newtChild.setFocusAction(null);
                this.newtChild.setDefaultCloseOperation(this.newtChildCloseOp);
                this.setFocusable(false);
            }
        }
    }
    
    public final boolean isAWTEventPassThrough() {
        return !this.isOnscreen;
    }
    
    private final void attachNewtChild() {
        if (null == this.newtChild || null == this.jawtWindow || this.newtChildAttached) {
            return;
        }
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.attachNewtChild.0 @ " + currentThreadName());
            System.err.println("\twin " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil() + ", comp " + this + ", visible " + this.isVisible() + ", showing " + this.isShowing() + ", displayable " + this.isDisplayable() + ", cont " + AWTMisc.getContainer(this));
        }
        this.newtChildAttached = true;
        this.newtChild.setFocusAction(null);
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.attachNewtChild.1: newtChild: " + this.newtChild);
        }
        final int width = this.getWidth();
        final int height = this.getHeight();
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.attachNewtChild.2: size " + width + "x" + height + ", isNValid " + this.newtChild.isNativeValid());
        }
        this.newtChild.setVisible(false);
        this.newtChild.setSize(width, height);
        this.jawtWindow.setSurfaceScale(this.newtChild.getRequestedSurfaceScale(new float[2]));
        this.newtChild.reparentWindow(this.jawtWindow, -1, -1, 2);
        this.newtChild.addSurfaceUpdatedListener(this.jawtWindow);
        if (this.jawtWindow.isOffscreenLayerSurfaceEnabled() && 0x0 != (0x2 & JAWTUtil.getOSXCALayerQuirks())) {
            AWTEDTExecutor.singleton.invoke(false, this.forceRelayout);
        }
        this.newtChild.setVisible(true);
        this.configureNewtChild(true);
        this.newtChild.sendWindowEvent(100);
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.attachNewtChild.X: win " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil() + ", comp " + this);
        }
    }
    
    private final void detachNewtChild(final Container container) {
        if (null == this.newtChild || null == this.jawtWindow || !this.newtChildAttached) {
            return;
        }
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.detachNewtChild.0: win " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil() + ", comp " + this + ", visible " + this.isVisible() + ", showing " + this.isShowing() + ", displayable " + this.isDisplayable() + ", cont " + container);
        }
        this.newtChild.removeSurfaceUpdatedListener(this.jawtWindow);
        this.newtChildAttached = false;
        this.newtChild.setFocusAction(null);
        this.configureNewtChild(false);
        this.newtChild.setVisible(false);
        this.newtChild.reparentWindow(null, -1, -1, 0);
        if (NewtCanvasAWT.DEBUG) {
            System.err.println("NewtCanvasAWT.detachNewtChild.X: win " + newtWinHandleToHexString(this.newtChild) + ", EDTUtil: cur " + this.newtChild.getScreen().getDisplay().getEDTUtil() + ", comp " + this);
        }
    }
    
    private void disableBackgroundErase() {
        if (!NewtCanvasAWT.disableBackgroundEraseInitialized) {
            try {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        try {
                            Serializable s = NewtCanvasAWT.this.getToolkit().getClass();
                            while (s != null && NewtCanvasAWT.disableBackgroundEraseMethod == null) {
                                try {
                                    NewtCanvasAWT.disableBackgroundEraseMethod = ((Class)s).getDeclaredMethod("disableBackgroundErase", Canvas.class);
                                    NewtCanvasAWT.disableBackgroundEraseMethod.setAccessible(true);
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
            NewtCanvasAWT.disableBackgroundEraseInitialized = true;
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT: TK disableBackgroundErase method found: " + (null != NewtCanvasAWT.disableBackgroundEraseMethod));
            }
        }
        if (NewtCanvasAWT.disableBackgroundEraseMethod != null) {
            Object o = null;
            try {
                NewtCanvasAWT.disableBackgroundEraseMethod.invoke(this.getToolkit(), this);
            }
            catch (Exception ex) {
                o = ex;
            }
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT: TK disableBackgroundErase error: " + o);
            }
        }
    }
    
    protected static String currentThreadName() {
        return "[" + Thread.currentThread().getName() + ", isAWT-EDT " + EventQueue.isDispatchThread() + "]";
    }
    
    static String newtWinHandleToHexString(final Window window) {
        return (null != window) ? toHexString(window.getWindowHandle()) : "nil";
    }
    
    static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    static {
        DEBUG = Debug.debug("Window");
        awtClearGlobalFocusOwner = new ClearFocusOwner();
    }
    
    class FocusAction implements Window.FocusRunnable
    {
        @Override
        public boolean run() {
            final boolean access$300 = NewtCanvasAWT.this.isParent();
            final boolean access$301 = NewtCanvasAWT.this.isFullscreen();
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.FocusAction: " + Display.getThreadName() + ", isOnscreen " + NewtCanvasAWT.this.isOnscreen + ", hasFocus " + NewtCanvasAWT.this.hasFocus() + ", isParent " + access$300 + ", isFS " + access$301);
            }
            if (access$300 && !access$301) {
                if (NewtCanvasAWT.this.isOnscreen) {
                    AWTEDTExecutor.singleton.invoke(false, NewtCanvasAWT.awtClearGlobalFocusOwner);
                }
                else if (!NewtCanvasAWT.this.hasFocus()) {
                    NewtCanvasAWT.this.requestFocus();
                }
            }
            return false;
        }
    }
    
    private static class ClearFocusOwner implements Runnable
    {
        @Override
        public void run() {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
        }
    }
    
    class FocusTraversalKeyListener implements KeyListener
    {
        @Override
        public void keyPressed(final KeyEvent keyEvent) {
            if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                this.handleKey(keyEvent, false);
            }
        }
        
        @Override
        public void keyReleased(final KeyEvent keyEvent) {
            if (NewtCanvasAWT.this.isParent() && !NewtCanvasAWT.this.isFullscreen()) {
                this.handleKey(keyEvent, true);
            }
        }
        
        void handleKey(final KeyEvent keyEvent, final boolean b) {
            if (null == NewtCanvasAWT.this.keyboardFocusManager) {
                throw new InternalError("XXX");
            }
            final AWTKeyStroke awtKeyStroke = AWTKeyStroke.getAWTKeyStroke(keyEvent.getKeyCode(), keyEvent.getModifiers(), b);
            boolean b2 = false;
            if (null != awtKeyStroke) {
                final Set<AWTKeyStroke> defaultFocusTraversalKeys = NewtCanvasAWT.this.keyboardFocusManager.getDefaultFocusTraversalKeys(0);
                final Set<AWTKeyStroke> defaultFocusTraversalKeys2 = NewtCanvasAWT.this.keyboardFocusManager.getDefaultFocusTraversalKeys(1);
                if (defaultFocusTraversalKeys.contains(awtKeyStroke)) {
                    final Component nextFocus = AWTMisc.getNextFocus(NewtCanvasAWT.this, true);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("NewtCanvasAWT.focusKey (fwd): " + awtKeyStroke + ", current focusOwner " + NewtCanvasAWT.this.keyboardFocusManager.getFocusOwner() + ", hasFocus: " + NewtCanvasAWT.this.hasFocus() + ", nextFocus " + nextFocus);
                    }
                    nextFocus.requestFocus();
                    b2 = true;
                }
                else if (defaultFocusTraversalKeys2.contains(awtKeyStroke)) {
                    final Component nextFocus2 = AWTMisc.getNextFocus(NewtCanvasAWT.this, false);
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("NewtCanvasAWT.focusKey (bwd): " + awtKeyStroke + ", current focusOwner " + NewtCanvasAWT.this.keyboardFocusManager.getFocusOwner() + ", hasFocus: " + NewtCanvasAWT.this.hasFocus() + ", prevFocus " + nextFocus2);
                    }
                    nextFocus2.requestFocus();
                    b2 = true;
                }
            }
            if (b2) {
                keyEvent.setConsumed(true);
            }
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.focusKey: XXX: " + awtKeyStroke);
            }
        }
    }
    
    class FocusPropertyChangeListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
            final Object oldValue = propertyChangeEvent.getOldValue();
            final Object newValue = propertyChangeEvent.getNewValue();
            final boolean access$300 = NewtCanvasAWT.this.isParent();
            final boolean access$301 = NewtCanvasAWT.this.isFullscreen();
            if (NewtCanvasAWT.DEBUG) {
                System.err.println("NewtCanvasAWT.FocusProperty: " + propertyChangeEvent.getPropertyName() + ", src " + propertyChangeEvent.getSource() + ", " + oldValue + " -> " + newValue + ", isParent " + access$300 + ", isFS " + access$301);
            }
            if (access$300 && !access$301) {
                if (newValue == NewtCanvasAWT.this) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("NewtCanvasAWT.FocusProperty: AWT focus -> NEWT focus traversal");
                    }
                    NewtCanvasAWT.this.requestFocusNEWTChild();
                }
                else if (oldValue == NewtCanvasAWT.this && newValue == null) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("NewtCanvasAWT.FocusProperty: NEWT focus");
                    }
                }
                else if (null != newValue && newValue != NewtCanvasAWT.this) {
                    if (NewtCanvasAWT.DEBUG) {
                        System.err.println("NewtCanvasAWT.FocusProperty: lost focus - clear focus");
                    }
                    if (NewtCanvasAWT.this.newtChild.getDelegatedWindow() instanceof DriverClearFocus) {
                        ((DriverClearFocus)NewtCanvasAWT.this.newtChild.getDelegatedWindow()).clearFocus();
                    }
                }
            }
        }
    }
}
