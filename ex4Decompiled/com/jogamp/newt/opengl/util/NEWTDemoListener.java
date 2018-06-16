// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.opengl.util;

import java.net.URLConnection;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.util.PNGPixelRect;
import jogamp.newt.driver.PNGIcon;
import com.jogamp.common.util.IOUtil;
import java.util.ArrayList;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.FPSCounter;
import com.jogamp.opengl.GLRunnable;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.Gamma;
import com.jogamp.newt.MonitorDevice;
import java.util.List;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.Display;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;

public class NEWTDemoListener extends WindowAdapter implements KeyListener, MouseListener
{
    protected final GLWindow glWindow;
    final Display.PointerIcon[] pointerIcons;
    int pointerIconIdx;
    float gamma;
    float brightness;
    float contrast;
    boolean confinedFixedCenter;
    private boolean quitAdapterShouldQuit;
    private boolean quitAdapterEnabled;
    private boolean quitAdapterEnabled2;
    
    public NEWTDemoListener(final GLWindow glWindow, final Display.PointerIcon[] pointerIcons) {
        this.pointerIconIdx = 0;
        this.gamma = 1.0f;
        this.brightness = 0.0f;
        this.contrast = 1.0f;
        this.confinedFixedCenter = false;
        this.quitAdapterShouldQuit = false;
        this.quitAdapterEnabled = false;
        this.quitAdapterEnabled2 = true;
        this.glWindow = glWindow;
        if (null != pointerIcons) {
            this.pointerIcons = pointerIcons;
        }
        else {
            this.pointerIcons = createPointerIcons(this.glWindow.getScreen().getDisplay());
        }
    }
    
    public NEWTDemoListener(final GLWindow glWindow) {
        this(glWindow, null);
    }
    
    protected void printlnState(final String s) {
        System.err.println(s + ": " + this.glWindow.getX() + "/" + this.glWindow.getY() + " " + this.glWindow.getSurfaceWidth() + "x" + this.glWindow.getSurfaceHeight() + ", f " + this.glWindow.isFullscreen() + ", a " + this.glWindow.isAlwaysOnTop() + ", " + this.glWindow.getInsets() + ", state " + this.glWindow.getStateMaskString());
    }
    
    protected void printlnState(final String s, final String s2) {
        System.err.println(s + ": " + this.glWindow.getX() + "/" + this.glWindow.getY() + " " + this.glWindow.getSurfaceWidth() + "x" + this.glWindow.getSurfaceHeight() + ", f " + this.glWindow.isFullscreen() + ", a " + this.glWindow.isAlwaysOnTop() + ", " + this.glWindow.getInsets() + ", state " + this.glWindow.getStateMaskString() + ", " + s2);
    }
    
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        if (keyEvent.isAutoRepeat() || keyEvent.isConsumed()) {
            return;
        }
        switch (keyEvent.getKeySymbol()) {
            case 32: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnCurrentThread(new Runnable() {
                    @Override
                    public void run() {
                        if (NEWTDemoListener.this.glWindow.getAnimator().isPaused()) {
                            NEWTDemoListener.this.glWindow.getAnimator().resume();
                        }
                        else {
                            NEWTDemoListener.this.glWindow.getAnimator().pause();
                        }
                    }
                });
                break;
            }
            case 65: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set alwaysontop pre]");
                        NEWTDemoListener.this.glWindow.setAlwaysOnTop(!NEWTDemoListener.this.glWindow.isAlwaysOnTop());
                        NEWTDemoListener.this.printlnState("[set alwaysontop post]");
                    }
                });
                break;
            }
            case 66: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set alwaysonbottom pre]");
                        NEWTDemoListener.this.glWindow.setAlwaysOnBottom(!NEWTDemoListener.this.glWindow.isAlwaysOnBottom());
                        NEWTDemoListener.this.printlnState("[set alwaysonbottom post]");
                    }
                });
                break;
            }
            case 67: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        if (null != NEWTDemoListener.this.pointerIcons) {
                            NEWTDemoListener.this.printlnState("[set pointer-icon pre]");
                            final Display.PointerIcon pointerIcon = NEWTDemoListener.this.glWindow.getPointerIcon();
                            Display.PointerIcon pointerIcon2;
                            if (NEWTDemoListener.this.pointerIconIdx >= NEWTDemoListener.this.pointerIcons.length) {
                                pointerIcon2 = null;
                                NEWTDemoListener.this.pointerIconIdx = 0;
                            }
                            else {
                                pointerIcon2 = NEWTDemoListener.this.pointerIcons[NEWTDemoListener.this.pointerIconIdx++];
                            }
                            NEWTDemoListener.this.glWindow.setPointerIcon(pointerIcon2);
                            NEWTDemoListener.this.printlnState("[set pointer-icon post]", pointerIcon + " -> " + NEWTDemoListener.this.glWindow.getPointerIcon());
                        }
                    }
                });
                break;
            }
            case 68: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set undecorated pre]");
                        NEWTDemoListener.this.glWindow.setUndecorated(!NEWTDemoListener.this.glWindow.isUndecorated());
                        NEWTDemoListener.this.printlnState("[set undecorated post]");
                    }
                });
                break;
            }
            case 70: {
                keyEvent.setConsumed(true);
                this.quitAdapterOff();
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set fullscreen pre]");
                        if (NEWTDemoListener.this.glWindow.isFullscreen()) {
                            NEWTDemoListener.this.glWindow.setFullscreen(false);
                        }
                        else if (keyEvent.isAltDown()) {
                            NEWTDemoListener.this.glWindow.setFullscreen(null);
                        }
                        else {
                            NEWTDemoListener.this.glWindow.setFullscreen(true);
                        }
                        NEWTDemoListener.this.printlnState("[set fullscreen post]");
                        NEWTDemoListener.this.quitAdapterOn();
                    }
                });
                break;
            }
            case 71: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        final float gamma = NEWTDemoListener.this.gamma + (keyEvent.isShiftDown() ? -0.1f : 0.1f);
                        System.err.println("[set gamma]: " + NEWTDemoListener.this.gamma + " -> " + gamma);
                        if (Gamma.setDisplayGamma(NEWTDemoListener.this.glWindow, gamma, NEWTDemoListener.this.brightness, NEWTDemoListener.this.contrast)) {
                            NEWTDemoListener.this.gamma = gamma;
                        }
                    }
                });
                break;
            }
            case 73: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set pointer-visible pre]");
                        NEWTDemoListener.this.glWindow.setPointerVisible(!NEWTDemoListener.this.glWindow.isPointerVisible());
                        NEWTDemoListener.this.printlnState("[set pointer-visible post]");
                    }
                });
                break;
            }
            case 74: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set pointer-confined pre]", "warp-center: " + keyEvent.isShiftDown());
                        final boolean confinedFixedCenter = !NEWTDemoListener.this.glWindow.isPointerConfined();
                        NEWTDemoListener.this.glWindow.confinePointer(confinedFixedCenter);
                        NEWTDemoListener.this.printlnState("[set pointer-confined post]", "warp-center: " + keyEvent.isShiftDown());
                        if (keyEvent.isShiftDown()) {
                            NEWTDemoListener.this.setConfinedFixedCenter(confinedFixedCenter);
                        }
                        else if (!confinedFixedCenter) {
                            NEWTDemoListener.this.setConfinedFixedCenter(false);
                        }
                    }
                });
                break;
            }
            case 77: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        boolean b;
                        boolean b2;
                        if (keyEvent.isControlDown()) {
                            b = false;
                            b2 = false;
                        }
                        else if (keyEvent.isShiftDown()) {
                            final boolean b3 = NEWTDemoListener.this.glWindow.isMaximizedHorz() && NEWTDemoListener.this.glWindow.isMaximizedVert();
                            b = !b3;
                            b2 = !b3;
                        }
                        else if (!keyEvent.isAltDown()) {
                            b = NEWTDemoListener.this.glWindow.isMaximizedHorz();
                            b2 = !NEWTDemoListener.this.glWindow.isMaximizedVert();
                        }
                        else if (keyEvent.isAltDown()) {
                            b = !NEWTDemoListener.this.glWindow.isMaximizedHorz();
                            b2 = NEWTDemoListener.this.glWindow.isMaximizedVert();
                        }
                        else {
                            b2 = NEWTDemoListener.this.glWindow.isMaximizedVert();
                            b = NEWTDemoListener.this.glWindow.isMaximizedHorz();
                        }
                        NEWTDemoListener.this.printlnState("[set maximize pre]", "max[vert " + b2 + ", horz " + b + "]");
                        NEWTDemoListener.this.glWindow.setMaximized(b, b2);
                        NEWTDemoListener.this.printlnState("[set maximize post]", "max[vert " + b2 + ", horz " + b + "]");
                    }
                });
                break;
            }
            case 81: {
                if (this.quitAdapterEnabled && 0 == keyEvent.getModifiers()) {
                    System.err.println("QUIT Key " + Thread.currentThread());
                    this.quitAdapterShouldQuit = true;
                    break;
                }
                break;
            }
            case 80: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set position pre]");
                        NEWTDemoListener.this.glWindow.setPosition(100, 100);
                        NEWTDemoListener.this.printlnState("[set position post]");
                    }
                });
                break;
            }
            case 82: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set resizable pre]");
                        NEWTDemoListener.this.glWindow.setResizable(!NEWTDemoListener.this.glWindow.isResizable());
                        NEWTDemoListener.this.printlnState("[set resizable post]");
                    }
                });
                break;
            }
            case 83: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set sticky pre]");
                        NEWTDemoListener.this.glWindow.setSticky(!NEWTDemoListener.this.glWindow.isSticky());
                        NEWTDemoListener.this.printlnState("[set sticky post]");
                    }
                });
                break;
            }
            case 86: {
                keyEvent.setConsumed(true);
                if (keyEvent.isControlDown()) {
                    this.glWindow.invoke(false, new GLRunnable() {
                        @Override
                        public boolean run(final GLAutoDrawable glAutoDrawable) {
                            final GL gl = glAutoDrawable.getGL();
                            final int swapInterval = gl.getSwapInterval();
                            int swapInterval2 = 0;
                            switch (swapInterval) {
                                case 0: {
                                    swapInterval2 = -1;
                                    break;
                                }
                                case -1: {
                                    swapInterval2 = 1;
                                    break;
                                }
                                case 1: {
                                    swapInterval2 = 0;
                                    break;
                                }
                                default: {
                                    swapInterval2 = 1;
                                    break;
                                }
                            }
                            gl.setSwapInterval(swapInterval2);
                            final GLAnimatorControl animator = glAutoDrawable.getAnimator();
                            if (null != animator) {
                                animator.resetFPSCounter();
                            }
                            if (glAutoDrawable instanceof FPSCounter) {
                                ((FPSCounter)glAutoDrawable).resetFPSCounter();
                            }
                            System.err.println("Swap Interval: " + swapInterval + " -> " + swapInterval2 + " -> " + gl.getSwapInterval());
                            return true;
                        }
                    });
                    break;
                }
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        final boolean visible = NEWTDemoListener.this.glWindow.isVisible();
                        NEWTDemoListener.this.printlnState("[set visible pre]");
                        NEWTDemoListener.this.glWindow.setVisible(!visible);
                        NEWTDemoListener.this.printlnState("[set visible post]");
                        if (visible && !keyEvent.isShiftDown()) {
                            try {
                                Thread.sleep(5000L);
                            }
                            catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            NEWTDemoListener.this.printlnState("[reset visible pre]");
                            NEWTDemoListener.this.glWindow.setVisible(true);
                            NEWTDemoListener.this.printlnState("[reset visible post]");
                        }
                    }
                });
                break;
            }
            case 87: {
                keyEvent.setConsumed(true);
                this.glWindow.invokeOnNewThread(null, false, new Runnable() {
                    @Override
                    public void run() {
                        NEWTDemoListener.this.printlnState("[set pointer-pos pre]");
                        NEWTDemoListener.this.glWindow.warpPointer(NEWTDemoListener.this.glWindow.getSurfaceWidth() / 2, NEWTDemoListener.this.glWindow.getSurfaceHeight() / 2);
                        NEWTDemoListener.this.printlnState("[set pointer-pos post]");
                    }
                });
                break;
            }
            case 88: {
                keyEvent.setConsumed(true);
                final float[] currentSurfaceScale = this.glWindow.getCurrentSurfaceScale(new float[2]);
                float[] surfaceScale;
                if (currentSurfaceScale[0] == 1.0f) {
                    surfaceScale = new float[] { 0.0f, 0.0f };
                }
                else {
                    surfaceScale = new float[] { 1.0f, 1.0f };
                }
                System.err.println("[set PixelScale pre]: had " + currentSurfaceScale[0] + "x" + currentSurfaceScale[1] + " -> req " + surfaceScale[0] + "x" + surfaceScale[1]);
                this.glWindow.setSurfaceScale(surfaceScale);
                final float[] requestedSurfaceScale = this.glWindow.getRequestedSurfaceScale(new float[2]);
                final float[] currentSurfaceScale2 = this.glWindow.getCurrentSurfaceScale(new float[2]);
                System.err.println("[set PixelScale post]: " + currentSurfaceScale[0] + "x" + currentSurfaceScale[1] + " (had) -> " + surfaceScale[0] + "x" + surfaceScale[1] + " (req) -> " + requestedSurfaceScale[0] + "x" + requestedSurfaceScale[1] + " (val) -> " + currentSurfaceScale2[0] + "x" + currentSurfaceScale2[1] + " (has)");
                this.setTitle();
                break;
            }
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent keyEvent) {
    }
    
    public void setConfinedFixedCenter(final boolean confinedFixedCenter) {
        this.confinedFixedCenter = confinedFixedCenter;
    }
    
    @Override
    public void mouseMoved(final MouseEvent mouseEvent) {
        if (mouseEvent.isConfined()) {
            this.mouseCenterWarp(mouseEvent);
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent mouseEvent) {
        if (mouseEvent.isConfined()) {
            this.mouseCenterWarp(mouseEvent);
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2 && mouseEvent.getPointerCount() == 3) {
            this.glWindow.setFullscreen(!this.glWindow.isFullscreen());
            System.err.println("setFullscreen: " + this.glWindow.isFullscreen());
        }
    }
    
    private void mouseCenterWarp(final MouseEvent mouseEvent) {
        if (mouseEvent.isConfined() && this.confinedFixedCenter) {
            this.glWindow.warpPointer(this.glWindow.getSurfaceWidth() / 2, this.glWindow.getSurfaceHeight() / 2);
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mouseExited(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mousePressed(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) {
    }
    
    @Override
    public void mouseWheelMoved(final MouseEvent mouseEvent) {
    }
    
    protected void quitAdapterOff() {
        this.quitAdapterEnabled2 = false;
    }
    
    protected void quitAdapterOn() {
        this.clearQuitAdapter();
        this.quitAdapterEnabled2 = true;
    }
    
    public void quitAdapterEnable(final boolean quitAdapterEnabled) {
        this.quitAdapterEnabled = quitAdapterEnabled;
    }
    
    public void clearQuitAdapter() {
        this.quitAdapterShouldQuit = false;
    }
    
    public boolean shouldQuit() {
        return this.quitAdapterShouldQuit;
    }
    
    public void doQuit() {
        this.quitAdapterShouldQuit = true;
    }
    
    @Override
    public void windowDestroyNotify(final WindowEvent windowEvent) {
        if (this.quitAdapterEnabled && this.quitAdapterEnabled2) {
            System.err.println("QUIT Window " + Thread.currentThread());
            this.quitAdapterShouldQuit = true;
        }
    }
    
    public void setTitle() {
        setTitle(this.glWindow);
    }
    
    public static void setTitle(final GLWindow glWindow) {
        final CapabilitiesImmutable chosenCapabilities = glWindow.getChosenCapabilities();
        final CapabilitiesImmutable requestedCapabilities = glWindow.getRequestedCapabilities();
        final String s = ((null != chosenCapabilities) ? chosenCapabilities : requestedCapabilities).isBackgroundOpaque() ? "opaque" : "transl";
        final float[] pixelsPerMM;
        final float[] array = pixelsPerMM = glWindow.getPixelsPerMM(new float[2]);
        final int n = 0;
        pixelsPerMM[n] *= 25.4f;
        final float[] array2 = array;
        final int n2 = 1;
        array2[n2] *= 25.4f;
        final float[] minimumSurfaceScale = glWindow.getMinimumSurfaceScale(new float[2]);
        final float[] maximumSurfaceScale = glWindow.getMaximumSurfaceScale(new float[2]);
        final float[] requestedSurfaceScale = glWindow.getRequestedSurfaceScale(new float[2]);
        final float[] currentSurfaceScale = glWindow.getCurrentSurfaceScale(new float[2]);
        glWindow.setTitle("GLWindow[" + s + "], win: " + glWindow.getBounds() + ", pix: " + glWindow.getSurfaceWidth() + "x" + glWindow.getSurfaceHeight() + ", sDPI " + array[0] + " x " + array[1] + ", scale[min " + minimumSurfaceScale[0] + "x" + minimumSurfaceScale[1] + ", max " + maximumSurfaceScale[0] + "x" + maximumSurfaceScale[1] + ", req " + requestedSurfaceScale[0] + "x" + requestedSurfaceScale[1] + " -> has " + currentSurfaceScale[0] + "x" + currentSurfaceScale[1] + "]");
    }
    
    public static Display.PointerIcon[] createPointerIcons(final Display display) {
        final ArrayList<Display.PointerIcon> list = new ArrayList<Display.PointerIcon>();
        display.createNative();
        final IOUtil.ClassResources classResources = new IOUtil.ClassResources(new String[] { "newt/data/cross-grey-alpha-16x16.png" }, display.getClass().getClassLoader(), null);
        try {
            final Display.PointerIcon pointerIcon = display.createPointerIcon(classResources, 8, 8);
            list.add(pointerIcon);
            System.err.printf("Create PointerIcon #%02d: %s%n", list.size(), pointerIcon.toString());
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        final IOUtil.ClassResources classResources2 = new IOUtil.ClassResources(new String[] { "newt/data/pointer-grey-alpha-16x24.png" }, display.getClass().getClassLoader(), null);
        try {
            final Display.PointerIcon pointerIcon2 = display.createPointerIcon(classResources2, 0, 0);
            list.add(pointerIcon2);
            System.err.printf("Create PointerIcon #%02d: %s%n", list.size(), pointerIcon2.toString());
        }
        catch (Exception ex2) {
            System.err.println(ex2.getMessage());
        }
        final IOUtil.ClassResources classResources3 = new IOUtil.ClassResources(new String[] { "arrow-red-alpha-64x64.png" }, display.getClass().getClassLoader(), null);
        try {
            final Display.PointerIcon pointerIcon3 = display.createPointerIcon(classResources3, 0, 0);
            list.add(pointerIcon3);
            System.err.printf("Create PointerIcon #%02d: %s%n", list.size(), pointerIcon3.toString());
        }
        catch (Exception ex3) {
            System.err.println(ex3.getMessage());
        }
        final IOUtil.ClassResources classResources4 = new IOUtil.ClassResources(new String[] { "arrow-blue-alpha-64x64.png" }, display.getClass().getClassLoader(), null);
        try {
            final Display.PointerIcon pointerIcon4 = display.createPointerIcon(classResources4, 0, 0);
            list.add(pointerIcon4);
            System.err.printf("Create PointerIcon #%02d: %s%n", list.size(), pointerIcon4.toString());
        }
        catch (Exception ex4) {
            System.err.println(ex4.getMessage());
        }
        if (PNGIcon.isAvailable()) {
            final IOUtil.ClassResources classResources5 = new IOUtil.ClassResources(new String[] { "jogamp-pointer-64x64.png" }, display.getClass().getClassLoader(), null);
            try {
                final URLConnection resolve = classResources5.resolve(0);
                if (null != resolve) {
                    final PNGPixelRect read = PNGPixelRect.read(resolve.getInputStream(), null, false, 0, false);
                    System.err.printf("Create PointerIcon #%02d: %s%n", list.size() + 1, read.toString());
                    final Display.PointerIcon pointerIcon5 = display.createPointerIcon(read, 32, 0);
                    list.add(pointerIcon5);
                    System.err.printf("Create PointerIcon #%02d: %s%n", list.size(), pointerIcon5.toString());
                }
            }
            catch (Exception ex5) {
                System.err.println(ex5.getMessage());
            }
        }
        return list.toArray(new Display.PointerIcon[list.size()]);
    }
}
