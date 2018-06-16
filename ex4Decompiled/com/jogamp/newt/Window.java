// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import jogamp.newt.Debug;
import com.jogamp.newt.event.GestureHandler;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.NEWTEvent;
import com.jogamp.newt.event.KeyListener;
import java.util.List;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.ScalableSurface;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.nativewindow.NativeWindow;

public interface Window extends NativeWindow, WindowClosingProtocol, ScalableSurface
{
    public static final boolean DEBUG_MOUSE_EVENT = Debug.debug("Window.MouseEvent");
    public static final boolean DEBUG_KEY_EVENT = Debug.debug("Window.KeyEvent");
    public static final boolean DEBUG_IMPLEMENTATION = Debug.debug("Window");
    public static final long TIMEOUT_NATIVEWINDOW = 1000L;
    public static final int STATE_BIT_VISIBLE = 0;
    public static final int STATE_BIT_AUTOPOSITION = 1;
    public static final int STATE_BIT_CHILDWIN = 2;
    public static final int STATE_BIT_FOCUSED = 3;
    public static final int STATE_BIT_UNDECORATED = 4;
    public static final int STATE_BIT_ALWAYSONTOP = 5;
    public static final int STATE_BIT_ALWAYSONBOTTOM = 6;
    public static final int STATE_BIT_STICKY = 7;
    public static final int STATE_BIT_RESIZABLE = 8;
    public static final int STATE_BIT_MAXIMIZED_VERT = 9;
    public static final int STATE_BIT_MAXIMIZED_HORZ = 10;
    public static final int STATE_BIT_FULLSCREEN = 11;
    public static final int STATE_BIT_POINTERVISIBLE = 12;
    public static final int STATE_BIT_POINTERCONFINED = 13;
    public static final int STATE_MASK_VISIBLE = 1;
    public static final int STATE_MASK_AUTOPOSITION = 2;
    public static final int STATE_MASK_CHILDWIN = 4;
    public static final int STATE_MASK_FOCUSED = 8;
    public static final int STATE_MASK_UNDECORATED = 16;
    public static final int STATE_MASK_ALWAYSONTOP = 32;
    public static final int STATE_MASK_ALWAYSONBOTTOM = 64;
    public static final int STATE_MASK_STICKY = 128;
    public static final int STATE_MASK_RESIZABLE = 256;
    public static final int STATE_MASK_MAXIMIZED_VERT = 512;
    public static final int STATE_MASK_MAXIMIZED_HORZ = 1024;
    public static final int STATE_MASK_FULLSCREEN = 2048;
    public static final int STATE_MASK_POINTERVISIBLE = 4096;
    public static final int STATE_MASK_POINTERCONFINED = 8192;
    public static final int REPARENT_HINT_FORCE_RECREATION = 1;
    public static final int REPARENT_HINT_BECOMES_VISIBLE = 2;
    
    int getStatePublicBitCount();
    
    int getStatePublicBitmask();
    
    int getStateMask();
    
    String getStateMaskString();
    
    int getSupportedStateMask();
    
    String getSupportedStateMaskString();
    
    boolean isNativeValid();
    
    Screen getScreen();
    
    MonitorDevice getMainMonitor();
    
    CapabilitiesChooser setCapabilitiesChooser(final CapabilitiesChooser p0);
    
    CapabilitiesImmutable getRequestedCapabilities();
    
    CapabilitiesImmutable getChosenCapabilities();
    
    void destroy();
    
    void setWindowDestroyNotifyAction(final Runnable p0);
    
    void setVisible(final boolean p0);
    
    void setVisible(final boolean p0, final boolean p1);
    
    boolean isVisible();
    
    Window getDelegatedWindow();
    
    boolean addChild(final NativeWindow p0);
    
    boolean removeChild(final NativeWindow p0);
    
    Rectangle getBounds();
    
    float[] getPixelsPerMM(final float[] p0);
    
    void setSize(final int p0, final int p1);
    
    void setSurfaceSize(final int p0, final int p1);
    
    void setTopLevelSize(final int p0, final int p1);
    
    void setPosition(final int p0, final int p1);
    
    void setTopLevelPosition(final int p0, final int p1);
    
    void setUndecorated(final boolean p0);
    
    boolean isUndecorated();
    
    void setAlwaysOnTop(final boolean p0);
    
    boolean isAlwaysOnTop();
    
    void setAlwaysOnBottom(final boolean p0);
    
    boolean isAlwaysOnBottom();
    
    void setResizable(final boolean p0);
    
    boolean isResizable();
    
    void setSticky(final boolean p0);
    
    boolean isSticky();
    
    void setMaximized(final boolean p0, final boolean p1);
    
    boolean isMaximizedVert();
    
    boolean isMaximizedHorz();
    
    void setTitle(final String p0);
    
    String getTitle();
    
    boolean isPointerVisible();
    
    void setPointerVisible(final boolean p0);
    
    Display.PointerIcon getPointerIcon();
    
    void setPointerIcon(final Display.PointerIcon p0);
    
    boolean isPointerConfined();
    
    void confinePointer(final boolean p0);
    
    void warpPointer(final int p0, final int p1);
    
    ReparentOperation reparentWindow(final NativeWindow p0, final int p1, final int p2, final int p3);
    
    boolean isChildWindow();
    
    boolean setFullscreen(final boolean p0);
    
    boolean setFullscreen(final List<MonitorDevice> p0);
    
    boolean isFullscreen();
    
    void setFocusAction(final FocusRunnable p0);
    
    void setKeyboardFocusHandler(final KeyListener p0);
    
    void requestFocus();
    
    void requestFocus(final boolean p0);
    
    void windowRepaint(final int p0, final int p1, final int p2, final int p3);
    
    void enqueueEvent(final boolean p0, final NEWTEvent p1);
    
    void runOnEDTIfAvail(final boolean p0, final Runnable p1);
    
    void sendWindowEvent(final int p0);
    
    void addWindowListener(final WindowListener p0);
    
    void addWindowListener(final int p0, final WindowListener p1) throws IndexOutOfBoundsException;
    
    void removeWindowListener(final WindowListener p0);
    
    WindowListener getWindowListener(final int p0);
    
    WindowListener[] getWindowListeners();
    
    void setKeyboardVisible(final boolean p0);
    
    boolean isKeyboardVisible();
    
    void addKeyListener(final KeyListener p0);
    
    void addKeyListener(final int p0, final KeyListener p1);
    
    void removeKeyListener(final KeyListener p0);
    
    KeyListener getKeyListener(final int p0);
    
    KeyListener[] getKeyListeners();
    
    void addMouseListener(final MouseListener p0);
    
    void addMouseListener(final int p0, final MouseListener p1);
    
    void removeMouseListener(final MouseListener p0);
    
    MouseListener getMouseListener(final int p0);
    
    MouseListener[] getMouseListeners();
    
    void setDefaultGesturesEnabled(final boolean p0);
    
    boolean areDefaultGesturesEnabled();
    
    void addGestureHandler(final GestureHandler p0);
    
    void addGestureHandler(final int p0, final GestureHandler p1);
    
    void removeGestureHandler(final GestureHandler p0);
    
    void addGestureListener(final GestureHandler.GestureListener p0);
    
    void addGestureListener(final int p0, final GestureHandler.GestureListener p1);
    
    void removeGestureListener(final GestureHandler.GestureListener p0);
    
    public enum ReparentOperation
    {
        ACTION_INVALID, 
        ACTION_NOP, 
        ACTION_NATIVE_REPARENTING, 
        ACTION_NATIVE_CREATION, 
        ACTION_NATIVE_CREATION_PENDING;
    }
    
    public interface FocusRunnable
    {
        boolean run();
    }
}
