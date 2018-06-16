// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.common.util.PropertyAccess;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.NEWTEvent;
import com.jogamp.newt.event.MonitorEvent;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.nativewindow.OffscreenLayerSurface;
import com.jogamp.newt.MonitorMode;
import com.jogamp.nativewindow.util.InsetsImmutable;
import jogamp.nativewindow.SurfaceScaleUtils;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.SurfaceUpdatedListener;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.newt.Screen;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Display;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.newt.event.MonitorModeListener;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.GestureHandler;
import com.jogamp.newt.event.DoubleTapScrollGesture;
import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.newt.event.MouseListener;
import jogamp.nativewindow.SurfaceUpdatedHelper;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.common.util.Bitfield;
import com.jogamp.newt.MonitorDevice;
import java.util.List;
import com.jogamp.nativewindow.CapabilitiesChooser;
import com.jogamp.nativewindow.CapabilitiesImmutable;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.NativeWindow;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.util.Insets;
import com.jogamp.newt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import com.jogamp.newt.event.NEWTEventConsumer;
import com.jogamp.newt.Window;

public abstract class WindowImpl implements Window, NEWTEventConsumer
{
    public static final boolean DEBUG_TEST_REPARENT_INCOMPATIBLE;
    private static final boolean DEBUG_FREEZE_AT_VISIBILITY_FAILURE;
    protected static final ArrayList<WeakReference<WindowImpl>> windowList;
    static final long QUEUED_EVENT_TO = 1200L;
    private static final MouseEvent.PointerType[] constMousePointerTypes;
    private volatile long windowHandle;
    private volatile int pixWidth;
    private volatile int pixHeight;
    private volatile int winWidth;
    private volatile int winHeight;
    protected final float[] minPixelScale;
    protected final float[] maxPixelScale;
    protected final float[] hasPixelScale;
    protected final float[] reqPixelScale;
    private volatile int x;
    private volatile int y;
    private volatile Insets insets;
    private boolean blockInsetsChange;
    private final RecursiveLock windowLock;
    private int surfaceLockCount;
    private ScreenImpl screen;
    private boolean screenReferenceAdded;
    private NativeWindow parentWindow;
    private long parentWindowHandle;
    private AbstractGraphicsConfiguration config;
    protected CapabilitiesImmutable capsRequested;
    protected CapabilitiesChooser capabilitiesChooser;
    private List<MonitorDevice> fullscreenMonitors;
    private int nfs_width;
    private int nfs_height;
    private int nfs_x;
    private int nfs_y;
    private NativeWindow nfs_parent;
    private String title;
    private PointerIconImpl pointerIcon;
    private LifecycleHook lifecycleHook;
    protected static final int QUIRK_BIT_VISIBILITY = 0;
    protected static final Bitfield quirks;
    protected static final int STATE_BIT_COUNT_ALL_PUBLIC = 14;
    protected static final int STATE_MASK_ALL_PUBLIC = 16383;
    protected static final int STATE_BIT_FULLSCREEN_SPAN = 14;
    protected static final int STATE_BIT_COUNT_ALL_RECONFIG = 15;
    protected static final int STATE_MASK_ALL_RECONFIG = 32767;
    protected static final int STATE_MASK_ALL_PUBLIC_SUPPORTED = 16381;
    static final int PSTATE_BIT_FOCUS_CHANGE_BROKEN = 30;
    static final int PSTATE_BIT_FULLSCREEN_MAINMONITOR = 31;
    static final int STATE_MASK_FULLSCREEN_SPAN = 16384;
    static final int PSTATE_MASK_FOCUS_CHANGE_BROKEN = 1073741824;
    static final int PSTATE_MASK_FULLSCREEN_MAINMONITOR = Integer.MIN_VALUE;
    private static final int STATE_MASK_FULLSCREEN_NFS = 1824;
    protected static final int STATE_MASK_CREATENATIVE = 2047;
    protected static final int CHANGE_MASK_VISIBILITY = Integer.MIN_VALUE;
    protected static final int CHANGE_MASK_VISIBILITY_FAST = 1073741824;
    protected static final int CHANGE_MASK_PARENTING = 536870912;
    protected static final int CHANGE_MASK_DECORATION = 268435456;
    protected static final int CHANGE_MASK_ALWAYSONTOP = 134217728;
    protected static final int CHANGE_MASK_ALWAYSONBOTTOM = 67108864;
    protected static final int CHANGE_MASK_STICKY = 33554432;
    protected static final int CHANGE_MASK_RESIZABLE = 16777216;
    protected static final int CHANGE_MASK_MAXIMIZED_VERT = 8388608;
    protected static final int CHANGE_MASK_MAXIMIZED_HORZ = 4194304;
    protected static final int CHANGE_MASK_FULLSCREEN = 2097152;
    final Bitfield stateMask;
    private final Bitfield stateMaskNFS;
    protected int supportedReconfigStateMask;
    protected static final int minimumReconfigStateMask = 2057;
    private Runnable windowDestroyNotifyAction;
    private FocusRunnable focusAction;
    private KeyListener keyboardFocusHandler;
    private final SurfaceUpdatedHelper surfaceUpdatedHelper;
    private final Object childWindowsLock;
    private final ArrayList<NativeWindow> childWindows;
    private ArrayList<MouseListener> mouseListeners;
    private final PointerState0 pState0;
    private final PointerState1 pState1;
    private final ArrayHashSet<Integer> pName2pID;
    private boolean defaultGestureHandlerEnabled;
    private DoubleTapScrollGesture gesture2PtrTouchScroll;
    private ArrayList<GestureHandler> pointerGestureHandler;
    private ArrayList<GestureHandler.GestureListener> gestureListeners;
    private ArrayList<KeyListener> keyListeners;
    private ArrayList<WindowListener> windowListeners;
    private boolean repaintQueued;
    private final Object closingListenerLock;
    private WindowClosingProtocol.WindowClosingMode defaultCloseOperation;
    private final Runnable destroyAction;
    private final Runnable reparentActionRecreate;
    private final int[] normPosSize;
    private final boolean[] normPosSizeStored;
    private final Runnable requestFocusAction;
    private final Runnable requestFocusActionForced;
    private final FullScreenAction fullScreenAction;
    private final MonitorModeListenerImpl monitorModeListenerImpl;
    private static final int keyTrackingRange = 255;
    private final Bitfield keyPressedState;
    protected boolean keyboardVisible;
    
    public WindowImpl() {
        this.windowHandle = 0L;
        this.pixWidth = 128;
        this.pixHeight = 128;
        this.winWidth = 128;
        this.winHeight = 128;
        this.minPixelScale = new float[] { 1.0f, 1.0f };
        this.maxPixelScale = new float[] { 1.0f, 1.0f };
        this.hasPixelScale = new float[] { 1.0f, 1.0f };
        this.reqPixelScale = new float[] { 0.0f, 0.0f };
        this.x = 64;
        this.y = 64;
        this.insets = new Insets();
        this.blockInsetsChange = false;
        this.windowLock = LockFactory.createRecursiveLock();
        this.surfaceLockCount = 0;
        this.screenReferenceAdded = false;
        this.parentWindow = null;
        this.parentWindowHandle = 0L;
        this.config = null;
        this.capsRequested = null;
        this.capabilitiesChooser = null;
        this.fullscreenMonitors = null;
        this.nfs_parent = null;
        this.title = "Newt Window";
        this.pointerIcon = null;
        this.lifecycleHook = null;
        this.stateMask = Bitfield.Factory.synchronize(Bitfield.Factory.create(32));
        this.stateMaskNFS = Bitfield.Factory.synchronize(Bitfield.Factory.create(32));
        this.supportedReconfigStateMask = 0;
        this.windowDestroyNotifyAction = null;
        this.focusAction = null;
        this.keyboardFocusHandler = null;
        this.surfaceUpdatedHelper = new SurfaceUpdatedHelper();
        this.childWindowsLock = new Object();
        this.childWindows = new ArrayList<NativeWindow>();
        this.mouseListeners = new ArrayList<MouseListener>();
        this.pState0 = new PointerState0();
        this.pState1 = new PointerState1();
        this.pName2pID = new ArrayHashSet<Integer>(false, 16, 0.75f);
        this.defaultGestureHandlerEnabled = true;
        this.gesture2PtrTouchScroll = null;
        this.pointerGestureHandler = new ArrayList<GestureHandler>();
        this.gestureListeners = new ArrayList<GestureHandler.GestureListener>();
        this.keyListeners = new ArrayList<KeyListener>();
        this.windowListeners = new ArrayList<WindowListener>();
        this.repaintQueued = false;
        this.closingListenerLock = new Object();
        this.defaultCloseOperation = WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE;
        this.destroyAction = new Runnable() {
            @Override
            public final void run() {
                boolean pauseRenderingAction = false;
                if (null != WindowImpl.this.lifecycleHook) {
                    pauseRenderingAction = WindowImpl.this.lifecycleHook.pauseRenderingAction();
                }
                if (null != WindowImpl.this.lifecycleHook) {
                    WindowImpl.this.lifecycleHook.destroyActionPreLock();
                }
                Throwable t = null;
                final RecursiveLock access$400 = WindowImpl.this.windowLock;
                access$400.lock();
                try {
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window DestroyAction() hasScreen " + (null != WindowImpl.this.screen) + ", isNativeValid " + WindowImpl.this.isNativeValid() + " - " + WindowImpl.getThreadName());
                    }
                    WindowImpl.this.sendWindowEvent(102);
                    synchronized (WindowImpl.this.childWindowsLock) {
                        if (WindowImpl.this.childWindows.size() > 0) {
                            final ArrayList list = (ArrayList)WindowImpl.this.childWindows.clone();
                            while (list.size() > 0) {
                                final NativeWindow nativeWindow = list.remove(0);
                                if (nativeWindow instanceof WindowImpl) {
                                    ((WindowImpl)nativeWindow).windowDestroyNotify(true);
                                }
                                else {
                                    nativeWindow.destroy();
                                }
                            }
                        }
                    }
                    if (null != WindowImpl.this.lifecycleHook) {
                        try {
                            WindowImpl.this.lifecycleHook.destroyActionInLock();
                        }
                        catch (RuntimeException ex) {
                            t = ex;
                        }
                    }
                    if (WindowImpl.this.isNativeValid()) {
                        WindowImpl.this.screen.removeMonitorModeListener(WindowImpl.this.monitorModeListenerImpl);
                        WindowImpl.this.closeNativeImpl();
                        final AbstractGraphicsDevice device = WindowImpl.this.config.getScreen().getDevice();
                        if (device != WindowImpl.this.screen.getDisplay().getGraphicsDevice()) {
                            device.close();
                        }
                        WindowImpl.this.setGraphicsConfiguration(null);
                    }
                    WindowImpl.this.removeScreenReference();
                    final Display display = WindowImpl.this.screen.getDisplay();
                    if (null != display) {
                        display.validateEDTStopped();
                    }
                    WindowImpl.this.sendWindowEvent(106);
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.destroy() END " + WindowImpl.getThreadName());
                        if (null != t) {
                            System.err.println("Window.destroy() caught: " + t.getMessage());
                            t.printStackTrace();
                        }
                    }
                    if (null != t) {
                        throw t;
                    }
                }
                finally {
                    WindowImpl.this.setWindowHandle(0L);
                    WindowImpl.this.resetStateMask();
                    WindowImpl.this.fullscreenMonitors = null;
                    WindowImpl.this.parentWindowHandle = 0L;
                    WindowImpl.this.hasPixelScale[0] = 1.0f;
                    WindowImpl.this.hasPixelScale[1] = 1.0f;
                    WindowImpl.this.minPixelScale[0] = 1.0f;
                    WindowImpl.this.minPixelScale[1] = 1.0f;
                    WindowImpl.this.maxPixelScale[0] = 1.0f;
                    WindowImpl.this.maxPixelScale[1] = 1.0f;
                    access$400.unlock();
                }
                if (pauseRenderingAction) {
                    WindowImpl.this.lifecycleHook.resumeRenderingAction();
                }
            }
        };
        this.reparentActionRecreate = new Runnable() {
            @Override
            public final void run() {
                final RecursiveLock access$400 = WindowImpl.this.windowLock;
                access$400.lock();
                try {
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.reparent: ReparentActionRecreate (" + WindowImpl.getThreadName() + ") state " + WindowImpl.this.getStateMaskString() + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + ", parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + ", parentWindow " + Display.hashCodeNullSafe(WindowImpl.this.parentWindow));
                    }
                    WindowImpl.this.setVisibleActionImpl(true);
                    WindowImpl.this.requestFocusInt(0L == WindowImpl.this.parentWindowHandle);
                }
                finally {
                    access$400.unlock();
                }
            }
        };
        this.normPosSize = new int[] { 0, 0, 0, 0 };
        this.normPosSizeStored = new boolean[] { false, false };
        this.requestFocusAction = new Runnable() {
            @Override
            public final void run() {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.RequestFocusAction: force 0 - (" + WindowImpl.getThreadName() + "): state " + WindowImpl.this.getStateMaskString() + " -> focus true - windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle));
                }
                WindowImpl.this.requestFocusImpl(false);
            }
        };
        this.requestFocusActionForced = new Runnable() {
            @Override
            public final void run() {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.RequestFocusAction: force 1 - (" + WindowImpl.getThreadName() + "): state " + WindowImpl.this.getStateMaskString() + " -> focus true - windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle));
                }
                WindowImpl.this.requestFocusImpl(true);
            }
        };
        this.fullScreenAction = new FullScreenAction();
        this.monitorModeListenerImpl = new MonitorModeListenerImpl();
        this.keyPressedState = Bitfield.Factory.create(256);
        this.keyboardVisible = false;
    }
    
    public static final void shutdownAll() {
        final int size = WindowImpl.windowList.size();
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.shutdownAll " + size + " instances, on thread " + getThreadName());
        }
        for (int n = 0; n < size && WindowImpl.windowList.size() > 0; ++n) {
            final WindowImpl windowImpl = WindowImpl.windowList.remove(0).get();
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.shutdownAll[" + (n + 1) + "/" + size + "]: " + toHexString((null != windowImpl) ? windowImpl.getWindowHandle() : 0L) + ", GCed " + (null == windowImpl));
            }
            if (null != windowImpl) {
                windowImpl.shutdown();
            }
        }
    }
    
    private static void addWindow2List(final WindowImpl windowImpl) {
        synchronized (WindowImpl.windowList) {
            int i = 0;
            int n = 0;
            while (i < WindowImpl.windowList.size()) {
                if (null == WindowImpl.windowList.get(i).get()) {
                    ++n;
                    WindowImpl.windowList.remove(i);
                }
                else {
                    ++i;
                }
            }
            WindowImpl.windowList.add(new WeakReference<WindowImpl>(windowImpl));
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.addWindow2List: GCed " + n + ", size " + WindowImpl.windowList.size());
            }
        }
    }
    
    final void resetStateMask() {
        this.stateMask.clearField(false);
        this.stateMask.put32(0, 32, 0x2 | ((null != this.parentWindow) ? 4 : 0) | 0x100 | 0x1000 | Integer.MIN_VALUE);
        this.stateMaskNFS.clearField(false);
        this.normPosSizeStored[0] = false;
        this.normPosSizeStored[1] = false;
        this.supportedReconfigStateMask = 32767;
    }
    
    @Override
    public final int getStatePublicBitCount() {
        return 14;
    }
    
    @Override
    public final int getStatePublicBitmask() {
        return 16383;
    }
    
    @Override
    public final int getStateMask() {
        return this.stateMask.get32(0, 14);
    }
    
    @Override
    public final String getStateMaskString() {
        return appendStateBits(new StringBuilder(), this.stateMask.get32(0, 14), false).toString();
    }
    
    @Override
    public final int getSupportedStateMask() {
        return this.supportedReconfigStateMask & 0x3FFD;
    }
    
    @Override
    public final String getSupportedStateMaskString() {
        return appendStateBits(new StringBuilder(), this.getSupportedStateMask(), true).toString();
    }
    
    protected static StringBuilder appendStateBits(final StringBuilder sb, final int n, final boolean b) {
        sb.append("[");
        if (b) {
            if (0x0 != (Integer.MIN_VALUE & n)) {
                sb.append("*");
            }
            if (0x0 != (0x40000000 & n)) {
                sb.append("*");
            }
        }
        sb.append((0x0 != (0x1 & n)) ? "visible" : "invisible");
        sb.append(", ");
        sb.append((0x0 != (0x2 & n)) ? "autopos, " : "");
        if (b) {
            if (0x0 != (0x20000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x4 & n)) ? "child" : "toplevel");
            sb.append(", ");
        }
        else if (0x0 != (0x4 & n)) {
            sb.append("child");
            sb.append(", ");
        }
        sb.append((0x0 != (0x8 & n)) ? "focused, " : "");
        if (b) {
            if (0x0 != (0x10000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x10 & n)) ? "undecor" : "decor");
            sb.append(", ");
        }
        else if (0x0 != (0x10 & n)) {
            sb.append("undecor");
            sb.append(", ");
        }
        if (b) {
            if (0x0 != (0x8000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x20 & n)) ? "aontop" : "!aontop");
            sb.append(", ");
        }
        else if (0x0 != (0x20 & n)) {
            sb.append("aontop");
            sb.append(", ");
        }
        if (b) {
            if (0x0 != (0x4000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x40 & n)) ? "aonbottom" : "!aonbottom");
            sb.append(", ");
        }
        else if (0x0 != (0x40 & n)) {
            sb.append("aonbottom");
            sb.append(", ");
        }
        if (b) {
            if (0x0 != (0x2000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x80 & n)) ? "sticky" : "unsticky");
            sb.append(", ");
        }
        else if (0x0 != (0x80 & n)) {
            sb.append("sticky");
            sb.append(", ");
        }
        if (b) {
            if (0x0 != (0x1000000 & n)) {
                sb.append("*");
            }
            sb.append((0x0 != (0x100 & n)) ? "resizable" : "unresizable");
            sb.append(", ");
        }
        else if (0x0 == (0x100 & n)) {
            sb.append("unresizable");
            sb.append(", ");
        }
        if (b) {
            sb.append("max[");
            if (0x0 != (0x400000 & n)) {
                sb.append("*");
            }
            if (0x0 == (0x400 & n)) {
                sb.append("!");
            }
            sb.append("h");
            sb.append(", ");
            if (0x0 != (0x800000 & n)) {
                sb.append("*");
            }
            if (0x0 == (0x200 & n)) {
                sb.append("!");
            }
            sb.append("v");
            sb.append("], ");
        }
        else if (0x0 != (0x600 & n)) {
            sb.append("max[");
            if (0x0 != (0x400 & n)) {
                sb.append("h");
            }
            if (0x0 != (0x200 & n)) {
                sb.append("v");
            }
            sb.append("], ");
        }
        if (b) {
            if (0x0 != (0x200000 & n)) {
                sb.append("*");
            }
            sb.append("fullscreen[");
            sb.append(0x0 != (0x800 & n));
            sb.append((0x0 != (0x4000 & n)) ? ", span" : "");
            sb.append("], ");
        }
        else if (0x0 != (0x800 & n)) {
            sb.append("fullscreen");
            sb.append(", ");
        }
        if (b) {
            sb.append("pointer[");
            if (0x0 == (0x1000 & n)) {
                sb.append("invisible");
            }
            else {
                sb.append("visible");
            }
            sb.append(", ");
            if (0x0 != (0x2000 & n)) {
                sb.append("confined");
            }
            else {
                sb.append("free");
            }
            sb.append("]");
        }
        else if (0x0 == (0x1000 & n) || 0x0 != (0x2000 & n)) {
            sb.append("pointer[");
            if (0x0 == (0x1000 & n)) {
                sb.append("invisible");
                sb.append(", ");
            }
            if (0x0 != (0x2000 & n)) {
                sb.append("confined");
            }
            sb.append("]");
        }
        sb.append("]");
        return sb;
    }
    
    private static Class<?> getWindowClass(final String s) throws ClassNotFoundException {
        final Class<?> customClass = NewtFactory.getCustomClass(s, "WindowDriver");
        if (null == customClass) {
            throw new ClassNotFoundException("Failed to find NEWT Window Class <" + s + ".WindowDriver>");
        }
        return customClass;
    }
    
    public static WindowImpl create(final NativeWindow parentWindow, final long parentWindowHandle, final Screen screen, final CapabilitiesImmutable capabilitiesImmutable) {
        try {
            Class<?> windowClass;
            if (capabilitiesImmutable.isOnscreen()) {
                windowClass = getWindowClass(screen.getDisplay().getType());
            }
            else {
                windowClass = OffscreenWindow.class;
            }
            final WindowImpl windowImpl = (WindowImpl)windowClass.newInstance();
            windowImpl.parentWindow = parentWindow;
            windowImpl.parentWindowHandle = parentWindowHandle;
            windowImpl.screen = (ScreenImpl)screen;
            windowImpl.capsRequested = (CapabilitiesImmutable)capabilitiesImmutable.cloneMutable();
            windowImpl.instantiationFinished();
            addWindow2List(windowImpl);
            return windowImpl;
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new NativeWindowException(t);
        }
    }
    
    public static WindowImpl create(final Object[] array, final Screen screen, final CapabilitiesImmutable capabilitiesImmutable) {
        try {
            final Class<?> windowClass = getWindowClass(screen.getDisplay().getType());
            final Class<?>[] customConstructorArgumentTypes = getCustomConstructorArgumentTypes(windowClass);
            if (null == customConstructorArgumentTypes) {
                throw new NativeWindowException("WindowClass " + windowClass + " doesn't support custom arguments in constructor");
            }
            final int verifyConstructorArgumentTypes = verifyConstructorArgumentTypes(customConstructorArgumentTypes, array);
            if (verifyConstructorArgumentTypes < array.length) {
                throw new NativeWindowException("WindowClass " + windowClass + " constructor mismatch at argument #" + verifyConstructorArgumentTypes + "; Constructor: " + getTypeStrList(customConstructorArgumentTypes) + ", arguments: " + getArgsStrList(array));
            }
            final WindowImpl windowImpl = (WindowImpl)ReflectionUtil.createInstance(windowClass, customConstructorArgumentTypes, array);
            windowImpl.screen = (ScreenImpl)screen;
            windowImpl.capsRequested = (CapabilitiesImmutable)capabilitiesImmutable.cloneMutable();
            windowImpl.instantiationFinished();
            addWindow2List(windowImpl);
            return windowImpl;
        }
        catch (Throwable t) {
            throw new NativeWindowException(t);
        }
    }
    
    private final void shutdown() {
        if (null != this.lifecycleHook) {
            this.lifecycleHook.shutdownRenderingAction();
        }
        this.setWindowHandle(0L);
        this.resetStateMask();
        this.fullscreenMonitors = null;
        this.parentWindowHandle = 0L;
    }
    
    protected final void setGraphicsConfiguration(final AbstractGraphicsConfiguration config) {
        this.config = config;
    }
    
    private boolean createNative() {
        long nanoTime;
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            nanoTime = System.nanoTime();
            System.err.println("Window.createNative() START (" + getThreadName() + ", " + this + ")");
        }
        else {
            nanoTime = 0L;
        }
        if (null != this.parentWindow && 1 >= this.parentWindow.lockSurface()) {
            throw new NativeWindowException("Parent surface lock: not ready: " + this.parentWindow);
        }
        final boolean b = null != this.parentWindow || 0L != this.parentWindowHandle;
        if (b && (this.stateMask.get(1) || 0 > this.getX() || 0 > this.getY())) {
            this.definePosition(0, 0);
        }
        boolean b2 = false;
        try {
            if (this.validateParentWindowHandle()) {
                if (!this.screenReferenceAdded) {
                    this.screen.addReference();
                    this.screenReferenceAdded = true;
                }
                if (this.canCreateNativeImpl()) {
                    int x;
                    int y;
                    boolean b3;
                    if (this.stateMask.get(1)) {
                        x = 0;
                        y = 0;
                        b3 = false;
                    }
                    else {
                        x = this.getX();
                        y = this.getY();
                        b3 = true;
                    }
                    final long currentTimeMillis = System.currentTimeMillis();
                    this.createNativeImpl();
                    this.supportedReconfigStateMask = (this.getSupportedReconfigMaskImpl() & 0x7FFF);
                    if (WindowImpl.DEBUG_IMPLEMENTATION) {
                        System.err.println("Supported Reconfig (minimum-ok " + (0x809 == (0x809 & this.supportedReconfigStateMask)) + "): " + appendStateBits(new StringBuilder(), this.supportedReconfigStateMask, true).toString());
                    }
                    this.screen.addMonitorModeListener(this.monitorModeListenerImpl);
                    this.setTitleImpl(this.title);
                    this.setPointerIconIntern(this.pointerIcon);
                    if (!this.stateMask.get(12)) {
                        if (this.isReconfigureMaskSupported(4096)) {
                            this.setPointerVisibleIntern(this.stateMask.get(12));
                        }
                        else {
                            this.stateMask.set(12);
                        }
                    }
                    if (this.stateMask.get(13)) {
                        if (this.isReconfigureMaskSupported(8192)) {
                            this.confinePointerImpl(true);
                        }
                        else {
                            this.stateMask.clear(13);
                        }
                    }
                    this.setKeyboardVisible(this.keyboardVisible);
                    if (0L <= this.waitForVisible(true, false)) {
                        if (this.stateMask.get(11) && !this.isReconfigureMaskSupported(2048)) {
                            this.stateMask.clear(11);
                        }
                        if (this.stateMask.get(11)) {
                            synchronized (this.fullScreenAction) {
                                this.stateMask.clear(11);
                                this.fullScreenAction.init(true);
                                this.fullScreenAction.run();
                            }
                        }
                        else if (!b) {
                            this.waitForPosition(b3, x, y, 1000L);
                        }
                        if (WindowImpl.DEBUG_IMPLEMENTATION) {
                            System.err.println("Window.createNative(): elapsed " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
                        }
                        b2 = true;
                    }
                }
            }
        }
        finally {
            if (null != this.parentWindow) {
                this.parentWindow.unlockSurface();
            }
        }
        if (b2) {
            this.requestFocusInt(this.isFullscreen());
            ((DisplayImpl)this.screen.getDisplay()).dispatchMessagesNative();
        }
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.createNative() END (" + getThreadName() + ", " + this + ") total " + (System.nanoTime() - nanoTime) / 1000000.0 + "ms");
        }
        return this.isNativeValid();
    }
    
    private void removeScreenReference() {
        if (this.screenReferenceAdded) {
            this.screenReferenceAdded = false;
            this.screen.removeReference();
        }
    }
    
    private boolean validateParentWindowHandle() {
        if (null != this.parentWindow) {
            this.parentWindowHandle = getNativeWindowHandle(this.parentWindow);
            return 0L != this.parentWindowHandle;
        }
        return true;
    }
    
    private static long getNativeWindowHandle(final NativeWindow nativeWindow) {
        long windowHandle = 0L;
        if (null != nativeWindow) {
            boolean b = false;
            if (1 < nativeWindow.lockSurface()) {
                b = true;
                try {
                    windowHandle = nativeWindow.getWindowHandle();
                    if (0L == windowHandle) {
                        throw new NativeWindowException("Parent native window handle is NULL, after succesful locking: " + nativeWindow);
                    }
                }
                catch (NativeWindowException ex) {
                    if (WindowImpl.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.getNativeWindowHandle: not successful yet: " + ex);
                    }
                }
                finally {
                    nativeWindow.unlockSurface();
                }
            }
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.getNativeWindowHandle: locked " + b + ", " + nativeWindow);
            }
        }
        return windowHandle;
    }
    
    protected int lockSurfaceImpl() {
        return 3;
    }
    
    protected void unlockSurfaceImpl() {
    }
    
    @Override
    public final WindowClosingProtocol.WindowClosingMode getDefaultCloseOperation() {
        synchronized (this.closingListenerLock) {
            return this.defaultCloseOperation;
        }
    }
    
    @Override
    public final WindowClosingProtocol.WindowClosingMode setDefaultCloseOperation(final WindowClosingProtocol.WindowClosingMode defaultCloseOperation) {
        synchronized (this.closingListenerLock) {
            final WindowClosingProtocol.WindowClosingMode defaultCloseOperation2 = this.defaultCloseOperation;
            this.defaultCloseOperation = defaultCloseOperation;
            return defaultCloseOperation2;
        }
    }
    
    private final void instantiationFinished() {
        this.resetStateMask();
        this.instantiationFinishedImpl();
    }
    
    protected void instantiationFinishedImpl() {
    }
    
    protected boolean canCreateNativeImpl() {
        return true;
    }
    
    protected abstract void createNativeImpl();
    
    protected abstract void closeNativeImpl();
    
    protected abstract void requestFocusImpl(final boolean p0);
    
    protected abstract int getSupportedReconfigMaskImpl();
    
    protected abstract boolean reconfigureWindowImpl(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    protected final boolean isReconfigureMaskSupported(final int n) {
        return n == (n & this.supportedReconfigStateMask);
    }
    
    protected int getReconfigureMask(final int n, final boolean b) {
        final int get32 = this.stateMask.get32(0, 15);
        return n | (get32 & 0xFFFFFFEA) | (b ? 1 : 0) | (isUndecorated(get32) ? 16 : 0) | ((0L != this.getParentWindowHandle()) ? 4 : 0);
    }
    
    protected static String getReconfigStateMaskString(final int n) {
        return appendStateBits(new StringBuilder(), n, true).toString();
    }
    
    protected void setTitleImpl(final String s) {
    }
    
    protected abstract Point getLocationOnScreenImpl(final int p0, final int p1);
    
    protected boolean setPointerVisibleImpl(final boolean b) {
        return false;
    }
    
    protected boolean confinePointerImpl(final boolean b) {
        return false;
    }
    
    protected void warpPointerImpl(final int n, final int n2) {
    }
    
    protected void setPointerIconImpl(final PointerIconImpl pointerIconImpl) {
    }
    
    @Override
    public final int lockSurface() throws NativeWindowException, RuntimeException {
        final RecursiveLock windowLock = this.windowLock;
        windowLock.lock();
        ++this.surfaceLockCount;
        int lockSurfaceImpl = (1 == this.surfaceLockCount) ? 1 : 3;
        if (lockSurfaceImpl != 0) {
            try {
                if (this.isNativeValid()) {
                    final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
                    device.lock();
                    try {
                        lockSurfaceImpl = this.lockSurfaceImpl();
                    }
                    finally {
                        if (1 >= lockSurfaceImpl) {
                            device.unlock();
                        }
                    }
                }
            }
            finally {
                if (1 >= lockSurfaceImpl) {
                    --this.surfaceLockCount;
                    windowLock.unlock();
                }
            }
        }
        return lockSurfaceImpl;
    }
    
    @Override
    public final void unlockSurface() {
        final RecursiveLock windowLock = this.windowLock;
        windowLock.validateLocked();
        if (1 == this.surfaceLockCount) {
            final AbstractGraphicsDevice device = this.getGraphicsConfiguration().getScreen().getDevice();
            try {
                this.unlockSurfaceImpl();
            }
            finally {
                device.unlock();
            }
        }
        --this.surfaceLockCount;
        windowLock.unlock();
    }
    
    @Override
    public final boolean isSurfaceLockedByOtherThread() {
        return this.windowLock.isLockedByOtherThread();
    }
    
    @Override
    public final Thread getSurfaceLockOwner() {
        return this.windowLock.getOwner();
    }
    
    public final RecursiveLock getLock() {
        return this.windowLock;
    }
    
    @Override
    public long getSurfaceHandle() {
        return this.windowHandle;
    }
    
    @Override
    public boolean surfaceSwap() {
        return false;
    }
    
    @Override
    public final void addSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public final void addSurfaceUpdatedListener(final int n, final SurfaceUpdatedListener surfaceUpdatedListener) throws IndexOutOfBoundsException {
        this.surfaceUpdatedHelper.addSurfaceUpdatedListener(n, surfaceUpdatedListener);
    }
    
    @Override
    public final void removeSurfaceUpdatedListener(final SurfaceUpdatedListener surfaceUpdatedListener) {
        this.surfaceUpdatedHelper.removeSurfaceUpdatedListener(surfaceUpdatedListener);
    }
    
    @Override
    public final void surfaceUpdated(final Object o, final NativeSurface nativeSurface, final long n) {
        this.surfaceUpdatedHelper.surfaceUpdated(o, nativeSurface, n);
    }
    
    @Override
    public final AbstractGraphicsConfiguration getGraphicsConfiguration() {
        return this.config.getNativeGraphicsConfiguration();
    }
    
    @Override
    public final long getDisplayHandle() {
        return this.config.getNativeGraphicsConfiguration().getScreen().getDevice().getHandle();
    }
    
    @Override
    public final int getScreenIndex() {
        return this.screen.getIndex();
    }
    
    @Override
    public final NativeSurface getNativeSurface() {
        return this;
    }
    
    @Override
    public final NativeWindow getParent() {
        return this.parentWindow;
    }
    
    @Override
    public final long getWindowHandle() {
        return this.windowHandle;
    }
    
    @Override
    public Point getLocationOnScreen(Point point) {
        if (this.isNativeValid()) {
            final RecursiveLock windowLock = this.windowLock;
            windowLock.lock();
            Point locationOnScreenImpl;
            try {
                locationOnScreenImpl = this.getLocationOnScreenImpl(0, 0);
            }
            finally {
                windowLock.unlock();
            }
            if (null != locationOnScreenImpl) {
                if (null != point) {
                    point.translate(locationOnScreenImpl.getX(), locationOnScreenImpl.getY());
                    return point;
                }
                return locationOnScreenImpl;
            }
        }
        if (null != point) {
            point.translate(this.getX(), this.getY());
        }
        else {
            point = new Point(this.getX(), this.getY());
        }
        if (null != this.parentWindow) {
            this.parentWindow.getLocationOnScreen(point);
        }
        return point;
    }
    
    @Override
    public final boolean isNativeValid() {
        return 0L != this.windowHandle;
    }
    
    @Override
    public final Screen getScreen() {
        return this.screen;
    }
    
    protected void setScreen(final ScreenImpl screen) {
        this.removeScreenReference();
        this.screen = screen;
    }
    
    @Override
    public final MonitorDevice getMainMonitor() {
        return this.screen.getMainMonitor(this.getBounds());
    }
    
    protected final void setVisibleImpl(final boolean b, final boolean b2, final int n, final int n2, final int n3, final int n4) {
        int n5;
        if (b2) {
            n5 = this.getReconfigureMask(-1073741824, b);
        }
        else {
            n5 = this.getReconfigureMask(Integer.MIN_VALUE, b);
        }
        this.reconfigureWindowImpl(n, n2, n3, n4, n5);
    }
    
    final void setVisibleActionImpl(final boolean b) {
        boolean native1 = false;
        int n = -1;
        final RecursiveLock windowLock = this.windowLock;
        windowLock.lock();
        try {
            if (!b && null != this.childWindows && this.childWindows.size() > 0) {
                synchronized (this.childWindowsLock) {
                    for (int i = 0; i < this.childWindows.size(); ++i) {
                        final NativeWindow nativeWindow = this.childWindows.get(i);
                        if (nativeWindow instanceof WindowImpl) {
                            ((WindowImpl)nativeWindow).setVisible(false);
                        }
                    }
                }
            }
            if (!this.isNativeValid() && b) {
                if (0 < this.getWidth() * this.getHeight()) {
                    native1 = this.createNative();
                    n = (native1 ? 1 : -1);
                }
                this.stateMask.set(0);
            }
            else if (this.stateMask.get(0) != b) {
                if (this.isNativeValid()) {
                    final boolean value = WindowImpl.quirks.get(0);
                    this.setVisibleImpl(b, value || this.isChildWindow(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
                    if (0L > this.waitForVisible(b, false)) {
                        if (!value) {
                            WindowImpl.quirks.set(0);
                            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                                System.err.println("Setting VISIBILITY QUIRK, due to setVisible(" + b + ") failure");
                            }
                            this.setVisibleImpl(b, true, this.getX(), this.getY(), this.getWidth(), this.getHeight());
                            if (0L <= this.waitForVisible(b, false)) {
                                n = (b ? 1 : 0);
                            }
                        }
                    }
                    else {
                        n = (b ? 1 : 0);
                    }
                }
                else {
                    this.stateMask.set(0);
                }
            }
            if (null != this.lifecycleHook) {
                this.lifecycleHook.setVisibleActionPost(b, native1);
            }
            if (this.isNativeValid() && b && null != this.childWindows && this.childWindows.size() > 0) {
                synchronized (this.childWindowsLock) {
                    for (int j = 0; j < this.childWindows.size(); ++j) {
                        final NativeWindow nativeWindow2 = this.childWindows.get(j);
                        if (nativeWindow2 instanceof WindowImpl) {
                            ((WindowImpl)nativeWindow2).setVisible(true);
                        }
                    }
                }
            }
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window setVisible: END (" + getThreadName() + ") state " + this.getStateMaskString() + ", nativeWindowCreated: " + native1 + ", madeVisible: " + n + ", geom " + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + ", windowHandle " + toHexString(this.windowHandle));
            }
        }
        finally {
            if (null != this.lifecycleHook) {
                this.lifecycleHook.resetCounter();
            }
            windowLock.unlock();
        }
        if (native1 || n) {
            this.sendWindowEvent(100);
        }
    }
    
    @Override
    public final void setVisible(final boolean b, final boolean b2) {
        if (!this.isReconfigureMaskSupported(1) && this.isNativeValid()) {
            return;
        }
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window setVisible: START (" + getThreadName() + ") " + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + ", windowHandle " + toHexString(this.windowHandle) + ", state " + this.getStateMaskString() + " -> visible " + b2 + ", parentWindowHandle " + toHexString(this.parentWindowHandle) + ", parentWindow " + (null != this.parentWindow));
        }
        this.runOnEDTIfAvail(b, new VisibleAction(b2));
    }
    
    @Override
    public final void setVisible(final boolean b) {
        this.setVisible(true, b);
    }
    
    private void setSize(final int n, final int n2, final boolean b) {
        this.runOnEDTIfAvail(true, new SetSizeAction(n, n2, b));
    }
    
    @Override
    public final void setSize(final int n, final int n2) {
        this.runOnEDTIfAvail(true, new SetSizeAction(n, n2, false));
    }
    
    @Override
    public final void setSurfaceSize(final int n, final int n2) {
        this.setSize(SurfaceScaleUtils.scaleInv(n, this.getPixelScaleX()), SurfaceScaleUtils.scaleInv(n2, this.getPixelScaleY()));
    }
    
    @Override
    public final void setTopLevelSize(final int n, final int n2) {
        final InsetsImmutable insets = this.getInsets();
        this.setSize(n - insets.getTotalWidth(), n2 - insets.getTotalHeight());
    }
    
    @Override
    public void destroy() {
        this.stateMask.clear(0);
        this.runOnEDTIfAvail(true, this.destroyAction);
    }
    
    protected void destroy(final boolean b) {
        if (null != this.lifecycleHook) {
            this.lifecycleHook.preserveGLStateAtDestroy(b);
        }
        this.destroy();
    }
    
    protected static boolean isOffscreenInstance(final NativeWindow nativeWindow, final NativeWindow nativeWindow2) {
        boolean b = false;
        final AbstractGraphicsConfiguration graphicsConfiguration = nativeWindow.getGraphicsConfiguration();
        if (null != graphicsConfiguration) {
            b = !graphicsConfiguration.getChosenCapabilities().isOnscreen();
        }
        if (!b && null != nativeWindow2) {
            final AbstractGraphicsConfiguration graphicsConfiguration2 = nativeWindow2.getGraphicsConfiguration();
            if (null != graphicsConfiguration2) {
                b = !graphicsConfiguration2.getChosenCapabilities().isOnscreen();
            }
        }
        return b;
    }
    
    @Override
    public final ReparentOperation reparentWindow(final NativeWindow nativeWindow, final int n, final int n2, final int n3) {
        if (!this.isReconfigureMaskSupported(4) && this.isNativeValid()) {
            return ReparentOperation.ACTION_INVALID;
        }
        final ReparentAction reparentAction = new ReparentAction(nativeWindow, n, n2, n3);
        this.runOnEDTIfAvail(true, reparentAction);
        return reparentAction.getOp();
    }
    
    @Override
    public final boolean isChildWindow() {
        return this.stateMask.get(2);
    }
    
    @Override
    public final CapabilitiesChooser setCapabilitiesChooser(final CapabilitiesChooser capabilitiesChooser) {
        final CapabilitiesChooser capabilitiesChooser2 = this.capabilitiesChooser;
        this.capabilitiesChooser = capabilitiesChooser;
        return capabilitiesChooser2;
    }
    
    @Override
    public final CapabilitiesImmutable getChosenCapabilities() {
        return this.getGraphicsConfiguration().getChosenCapabilities();
    }
    
    @Override
    public final CapabilitiesImmutable getRequestedCapabilities() {
        return this.capsRequested;
    }
    
    @Override
    public final void setUndecorated(final boolean b) {
        if (this.isNativeValid()) {
            if (!this.isReconfigureMaskSupported(16)) {
                return;
            }
            if (this.isFullscreen()) {
                this.stateMaskNFS.put(16, b);
                return;
            }
        }
        this.runOnEDTIfAvail(true, new DecorationAction(b));
    }
    
    @Override
    public final boolean isUndecorated() {
        return isUndecorated(this.getStateMask());
    }
    
    private static final boolean isUndecorated(final int n) {
        return 0x0 != (n & 0x814);
    }
    
    @Override
    public final void setAlwaysOnTop(final boolean b) {
        if (this.isChildWindow()) {
            return;
        }
        if (this.isNativeValid()) {
            if (!this.isReconfigureMaskSupported(32)) {
                return;
            }
            if (this.isFullscreen()) {
                if (b && this.isAlwaysOnBottom()) {
                    this.setAlwaysOnBottom(false);
                }
                this.stateMaskNFS.put(5, b);
                return;
            }
        }
        if (b && this.isAlwaysOnBottom()) {
            this.setAlwaysOnBottom(false);
        }
        this.runOnEDTIfAvail(true, new AlwaysOnTopAction(b));
    }
    
    @Override
    public final boolean isAlwaysOnTop() {
        return this.stateMask.get(5);
    }
    
    @Override
    public final void setAlwaysOnBottom(final boolean b) {
        if (this.isChildWindow()) {
            return;
        }
        if (!this.isReconfigureMaskSupported(64) && this.isNativeValid()) {
            return;
        }
        if (b && this.isAlwaysOnTop()) {
            this.setAlwaysOnTop(false);
        }
        this.runOnEDTIfAvail(true, new AlwaysOnBottomAction(b));
    }
    
    @Override
    public final boolean isAlwaysOnBottom() {
        return this.stateMask.get(6);
    }
    
    @Override
    public final void setResizable(final boolean b) {
        if (this.isChildWindow()) {
            return;
        }
        if (this.isNativeValid()) {
            if (!this.isReconfigureMaskSupported(256)) {
                return;
            }
            if (this.isFullscreen()) {
                this.stateMaskNFS.put(8, b);
                return;
            }
        }
        this.runOnEDTIfAvail(true, new ResizableAction(b));
    }
    
    @Override
    public final boolean isResizable() {
        return this.stateMask.get(8);
    }
    
    @Override
    public final void setSticky(final boolean b) {
        if (this.isChildWindow()) {
            return;
        }
        if (!this.isReconfigureMaskSupported(128) && this.isNativeValid()) {
            return;
        }
        this.runOnEDTIfAvail(true, new StickyAction(b));
    }
    
    @Override
    public final boolean isSticky() {
        return this.stateMask.get(7);
    }
    
    @Override
    public final void setMaximized(boolean b, boolean b2) {
        if (this.isNativeValid()) {
            if (b && !this.isReconfigureMaskSupported(1024)) {
                b = false;
            }
            if (b2 && !this.isReconfigureMaskSupported(512)) {
                b2 = false;
            }
        }
        if (this.isChildWindow()) {
            return;
        }
        if (this.isFullscreen()) {
            this.stateMaskNFS.put(10, b);
            this.stateMaskNFS.put(9, b2);
        }
        else {
            this.runOnEDTIfAvail(true, new MaximizeAction(b, b2));
        }
    }
    
    @Override
    public final boolean isMaximizedVert() {
        return this.stateMask.get(9);
    }
    
    @Override
    public final boolean isMaximizedHorz() {
        return this.stateMask.get(10);
    }
    
    protected final void maximizedChanged(final boolean b, final boolean b2) {
        if (!this.isFullscreen()) {
            final String s = WindowImpl.DEBUG_IMPLEMENTATION ? this.getStateMaskString() : null;
            final boolean b3 = this.stateMask.put(10, b) != b;
            final boolean b4 = this.stateMask.put(9, b2) != b2;
            if (WindowImpl.DEBUG_IMPLEMENTATION && (b3 || b4)) {
                System.err.println("Window.maximizedChanged.accepted: " + s + " -> " + this.getStateMaskString());
            }
        }
        else if (WindowImpl.DEBUG_IMPLEMENTATION) {
            final String s2 = WindowImpl.DEBUG_IMPLEMENTATION ? this.getStateMaskString() : null;
            final boolean b5 = this.stateMask.get(10) != b;
            final boolean b6 = this.stateMask.get(9) != b2;
            if (b5 || b6) {
                System.err.println("Window.maximizedChanged.ignored: " + s2 + " -> max[" + (b ? "" : "!") + "h, " + (b2 ? "" : "!") + "v]");
            }
        }
    }
    
    protected void reconfigMaximizedManual(final int n, final int[] array, final InsetsImmutable insetsImmutable) {
        final MonitorMode currentMode = this.getMainMonitor().getCurrentMode();
        final int scaleInv = SurfaceScaleUtils.scaleInv(currentMode.getRotatedWidth(), this.getPixelScaleX());
        final int scaleInv2 = SurfaceScaleUtils.scaleInv(currentMode.getRotatedHeight(), this.getPixelScaleY());
        if (0x0 != (0x400000 & n)) {
            if (0x0 != (0x400 & n)) {
                this.normPosSizeStored[0] = true;
                this.normPosSize[0] = array[0];
                this.normPosSize[2] = array[2];
                array[0] = insetsImmutable.getLeftWidth();
                array[2] = scaleInv - insetsImmutable.getTotalWidth();
            }
            else {
                this.normPosSizeStored[0] = false;
                array[0] = this.normPosSize[0];
                array[2] = this.normPosSize[2];
            }
        }
        if (0x0 != (0x800000 & n)) {
            if (0x0 != (0x200 & n)) {
                this.normPosSizeStored[1] = true;
                this.normPosSize[1] = array[1];
                this.normPosSize[3] = array[3];
                array[1] = insetsImmutable.getTopHeight();
                array[3] = scaleInv2 - insetsImmutable.getTotalHeight();
            }
            else {
                this.normPosSizeStored[1] = false;
                array[1] = this.normPosSize[1];
                array[3] = this.normPosSize[3];
            }
        }
    }
    
    protected void resetMaximizedManual(final int[] array) {
        if (this.normPosSizeStored[0]) {
            this.normPosSizeStored[0] = false;
            array[0] = this.normPosSize[0];
            array[2] = this.normPosSize[2];
        }
        if (this.normPosSizeStored[1]) {
            this.normPosSizeStored[1] = false;
            array[1] = this.normPosSize[1];
            array[3] = this.normPosSize[3];
        }
    }
    
    @Override
    public final String getTitle() {
        return this.title;
    }
    
    @Override
    public final void setTitle(String s) {
        if (s == null) {
            s = "";
        }
        this.title = s;
        if (0L != this.getWindowHandle()) {
            this.setTitleImpl(s);
        }
    }
    
    @Override
    public final boolean isPointerVisible() {
        return this.stateMask.get(12);
    }
    
    @Override
    public final void setPointerVisible(final boolean pointerVisibleIntern) {
        if (!this.isReconfigureMaskSupported(4096) && this.isNativeValid()) {
            return;
        }
        if (this.stateMask.get(12) != pointerVisibleIntern) {
            boolean setPointerVisibleIntern = 0L == this.getWindowHandle();
            if (!setPointerVisibleIntern) {
                setPointerVisibleIntern = this.setPointerVisibleIntern(pointerVisibleIntern);
            }
            if (setPointerVisibleIntern) {
                this.stateMask.put(12, pointerVisibleIntern);
            }
        }
    }
    
    private boolean setPointerVisibleIntern(final boolean pointerVisibleImpl) {
        final boolean setOffscreenPointerVisible = this.setOffscreenPointerVisible(pointerVisibleImpl, this.pointerIcon);
        return this.setPointerVisibleImpl(pointerVisibleImpl) || setOffscreenPointerVisible;
    }
    
    private boolean setOffscreenPointerVisible(final boolean b, final PointerIconImpl offscreenPointerIcon) {
        if (b) {
            return this.setOffscreenPointerIcon(offscreenPointerIcon);
        }
        final NativeWindow parent = this.getParent();
        if (parent instanceof OffscreenLayerSurface) {
            final OffscreenLayerSurface offscreenLayerSurface = (OffscreenLayerSurface)parent;
            try {
                return offscreenLayerSurface.hideCursor();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    
    @Override
    public final Display.PointerIcon getPointerIcon() {
        return this.pointerIcon;
    }
    
    @Override
    public final void setPointerIcon(final Display.PointerIcon pointerIcon) {
        final PointerIconImpl pointerIcon2 = (PointerIconImpl)pointerIcon;
        if (this.pointerIcon != pointerIcon2) {
            if (this.isNativeValid()) {
                this.runOnEDTIfAvail(true, new Runnable() {
                    @Override
                    public void run() {
                        WindowImpl.this.setPointerIconIntern(pointerIcon2);
                    }
                });
            }
            this.pointerIcon = pointerIcon2;
        }
    }
    
    private void setPointerIconIntern(final PointerIconImpl pointerIconImpl) {
        this.setOffscreenPointerIcon(pointerIconImpl);
        this.setPointerIconImpl(pointerIconImpl);
    }
    
    private boolean setOffscreenPointerIcon(final PointerIconImpl pointerIconImpl) {
        final NativeWindow parent = this.getParent();
        if (parent instanceof OffscreenLayerSurface) {
            final OffscreenLayerSurface offscreenLayerSurface = (OffscreenLayerSurface)parent;
            try {
                if (null != pointerIconImpl) {
                    return offscreenLayerSurface.setCursor(pointerIconImpl, pointerIconImpl.getHotspot());
                }
                return offscreenLayerSurface.setCursor(null, null);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    
    @Override
    public final boolean isPointerConfined() {
        return this.stateMask.get(13);
    }
    
    @Override
    public final void confinePointer(final boolean b) {
        if (!this.isReconfigureMaskSupported(8192) && this.isNativeValid()) {
            return;
        }
        if (this.stateMask.get(13) != b) {
            boolean confinePointerImpl = 0L == this.getWindowHandle();
            if (!confinePointerImpl) {
                if (b) {
                    this.requestFocus();
                    this.warpPointer(this.getSurfaceWidth() / 2, this.getSurfaceHeight() / 2);
                }
                confinePointerImpl = this.confinePointerImpl(b);
                if (b) {
                    try {
                        Thread.sleep(3L * this.screen.getDisplay().getEDTUtil().getPollPeriod());
                    }
                    catch (InterruptedException ex) {}
                }
            }
            if (confinePointerImpl) {
                this.stateMask.put(13, b);
            }
        }
    }
    
    @Override
    public final void warpPointer(final int n, final int n2) {
        if (0L != this.getWindowHandle()) {
            this.warpPointerImpl(n, n2);
        }
    }
    
    @Override
    public final InsetsImmutable getInsets() {
        if (this.isUndecorated()) {
            return Insets.getZero();
        }
        return this.insets;
    }
    
    @Override
    public final int getX() {
        return this.x;
    }
    
    @Override
    public final int getY() {
        return this.y;
    }
    
    @Override
    public final int getWidth() {
        return this.winWidth;
    }
    
    @Override
    public final int getHeight() {
        return this.winHeight;
    }
    
    @Override
    public final Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.winWidth, this.winHeight);
    }
    
    @Override
    public final int getSurfaceWidth() {
        return this.pixWidth;
    }
    
    @Override
    public final int getSurfaceHeight() {
        return this.pixHeight;
    }
    
    @Override
    public final int[] convertToWindowUnits(final int[] array) {
        return SurfaceScaleUtils.scaleInv(array, array, this.hasPixelScale);
    }
    
    @Override
    public final int[] convertToPixelUnits(final int[] array) {
        return SurfaceScaleUtils.scale(array, array, this.hasPixelScale);
    }
    
    protected final Point convertToWindowUnits(final Point point) {
        return point.scaleInv(this.getPixelScaleX(), this.getPixelScaleY());
    }
    
    protected final Point convertToPixelUnits(final Point point) {
        return point.scale(this.getPixelScaleX(), this.getPixelScaleY());
    }
    
    protected final float getPixelScaleX() {
        return this.hasPixelScale[0];
    }
    
    protected final float getPixelScaleY() {
        return this.hasPixelScale[1];
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
    public final float[] getMinimumSurfaceScale(final float[] array) {
        System.arraycopy(this.minPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getMaximumSurfaceScale(final float[] array) {
        System.arraycopy(this.maxPixelScale, 0, array, 0, 2);
        return array;
    }
    
    @Override
    public final float[] getPixelsPerMM(final float[] array) {
        this.getMainMonitor().getPixelsPerMM(array);
        final int n = 0;
        array[n] *= this.hasPixelScale[0] / this.maxPixelScale[0];
        final int n2 = 1;
        array[n2] *= this.hasPixelScale[1] / this.maxPixelScale[1];
        return array;
    }
    
    protected final boolean autoPosition() {
        return this.stateMask.get(1);
    }
    
    protected final void definePosition(final int x, final int y) {
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("definePosition: " + this.x + "/" + this.y + " -> " + x + "/" + y);
        }
        this.stateMask.clear(1);
        this.x = x;
        this.y = y;
    }
    
    protected final void defineSize(final int winWidth, final int winHeight) {
        final int scale = SurfaceScaleUtils.scale(winWidth, this.getPixelScaleX());
        final int scale2 = SurfaceScaleUtils.scale(winHeight, this.getPixelScaleY());
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("defineSize: win[" + this.winWidth + "x" + this.winHeight + " -> " + winWidth + "x" + winHeight + "], pixel[" + this.pixWidth + "x" + this.pixHeight + " -> " + scale + "x" + scale2 + "]");
        }
        this.winWidth = winWidth;
        this.winHeight = winHeight;
        this.pixWidth = scale;
        this.pixHeight = scale2;
    }
    
    @Override
    public final boolean isVisible() {
        return this.stateMask.get(0);
    }
    
    @Override
    public final boolean isFullscreen() {
        return this.stateMask.get(11);
    }
    
    @Override
    public final Window getDelegatedWindow() {
        return this;
    }
    
    public boolean hasDeviceChanged() {
        return false;
    }
    
    public final LifecycleHook getLifecycleHook() {
        return this.lifecycleHook;
    }
    
    public final LifecycleHook setLifecycleHook(final LifecycleHook lifecycleHook) {
        final LifecycleHook lifecycleHook2 = this.lifecycleHook;
        this.lifecycleHook = lifecycleHook;
        return lifecycleHook2;
    }
    
    public NativeSurface getWrappedSurface() {
        return null;
    }
    
    @Override
    public final void setWindowDestroyNotifyAction(final Runnable windowDestroyNotifyAction) {
        this.windowDestroyNotifyAction = windowDestroyNotifyAction;
    }
    
    protected final long getParentWindowHandle() {
        return this.isFullscreen() ? 0L : this.parentWindowHandle;
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName() + "[State " + this.getStateMaskString() + ",\n " + this.screen + ",\n window[" + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + " wu, " + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + " pixel]" + ",\n Config " + this.config + ",\n ParentWindow " + this.parentWindow + ",\n ParentWindowHandle " + toHexString(this.parentWindowHandle) + " (" + (0L != this.getParentWindowHandle()) + ")" + ",\n WindowHandle " + toHexString(this.getWindowHandle()) + ",\n SurfaceHandle " + toHexString(this.getSurfaceHandle()) + " (lockedExt window " + this.windowLock.isLockedByOtherThread() + ", surface " + this.isSurfaceLockedByOtherThread() + ")" + ",\n WrappedSurface " + this.getWrappedSurface() + ",\n ChildWindows " + this.childWindows.size());
        sb.append(", SurfaceUpdatedListeners num " + this.surfaceUpdatedHelper.size() + " [");
        for (int i = 0; i < this.surfaceUpdatedHelper.size(); ++i) {
            sb.append(this.surfaceUpdatedHelper.get(i) + ", ");
        }
        sb.append("], WindowListeners num " + this.windowListeners.size() + " [");
        for (int j = 0; j < this.windowListeners.size(); ++j) {
            sb.append(this.windowListeners.get(j) + ", ");
        }
        sb.append("], MouseListeners num " + this.mouseListeners.size() + " [");
        for (int k = 0; k < this.mouseListeners.size(); ++k) {
            sb.append(this.mouseListeners.get(k) + ", ");
        }
        sb.append("], PointerGestures default " + this.defaultGestureHandlerEnabled + ", custom " + this.pointerGestureHandler.size() + " [");
        for (int l = 0; l < this.pointerGestureHandler.size(); ++l) {
            sb.append(this.pointerGestureHandler.get(l) + ", ");
        }
        sb.append("], KeyListeners num " + this.keyListeners.size() + " [");
        for (int n = 0; n < this.keyListeners.size(); ++n) {
            sb.append(this.keyListeners.get(n) + ", ");
        }
        sb.append("], windowLock " + this.windowLock + ", surfaceLockCount " + this.surfaceLockCount + "]");
        return sb.toString();
    }
    
    protected final void setWindowHandle(final long windowHandle) {
        this.windowHandle = windowHandle;
    }
    
    @Override
    public final void runOnEDTIfAvail(final boolean b, final Runnable runnable) {
        if (this.windowLock.isOwner(Thread.currentThread())) {
            runnable.run();
        }
        else {
            ((DisplayImpl)this.screen.getDisplay()).runOnEDTIfAvail(b, runnable);
        }
    }
    
    @Override
    public final boolean hasFocus() {
        return this.stateMask.get(3);
    }
    
    @Override
    public final void requestFocus() {
        this.requestFocus(true);
    }
    
    @Override
    public final void requestFocus(final boolean b) {
        this.requestFocus(b, false, this.stateMask.get(30));
    }
    
    private void requestFocus(final boolean b, final boolean b2, final boolean b3) {
        if (this.isNativeValid() && (b3 || !this.hasFocus()) && (b2 || !this.focusAction())) {
            this.runOnEDTIfAvail(b, b3 ? this.requestFocusActionForced : this.requestFocusAction);
        }
    }
    
    private void requestFocusInt(final boolean b) {
        if (b || !this.focusAction()) {
            if (!this.isReconfigureMaskSupported(8)) {
                return;
            }
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.RequestFocusInt: forcing - (" + getThreadName() + "): skipFocusAction " + b + ", state " + this.getStateMaskString() + " -> focus true - windowHandle " + toHexString(this.windowHandle) + " parentWindowHandle " + toHexString(this.parentWindowHandle));
            }
            this.requestFocusImpl(true);
        }
    }
    
    @Override
    public final void setFocusAction(final FocusRunnable focusAction) {
        this.focusAction = focusAction;
    }
    
    private boolean focusAction() {
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.focusAction() START - " + getThreadName() + ", focusAction: " + this.focusAction + " - windowHandle " + toHexString(this.getWindowHandle()));
        }
        final boolean b = null != this.focusAction && this.focusAction.run();
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.focusAction() END - " + getThreadName() + ", focusAction: " + this.focusAction + " - windowHandle " + toHexString(this.getWindowHandle()) + ", res: " + b);
        }
        return b;
    }
    
    protected final void setBrokenFocusChange(final boolean b) {
        this.stateMask.put(30, b);
    }
    
    @Override
    public final void setKeyboardFocusHandler(final KeyListener keyboardFocusHandler) {
        this.keyboardFocusHandler = keyboardFocusHandler;
    }
    
    @Override
    public void setPosition(final int n, final int n2) {
        this.stateMask.clear(1);
        this.runOnEDTIfAvail(true, new SetPositionAction(n, n2));
    }
    
    @Override
    public final void setTopLevelPosition(final int n, final int n2) {
        final InsetsImmutable insets = this.getInsets();
        this.setPosition(n + insets.getLeftWidth(), n2 + insets.getTopHeight());
    }
    
    @Override
    public boolean setFullscreen(final boolean b) {
        return this.setFullscreenImpl(b, true, null);
    }
    
    @Override
    public boolean setFullscreen(final List<MonitorDevice> list) {
        return this.setFullscreenImpl(true, false, list);
    }
    
    private boolean setFullscreenImpl(final boolean b, final boolean b2, final List<MonitorDevice> fullscreenMonitors) {
        synchronized (this.fullScreenAction) {
            this.fullscreenMonitors = fullscreenMonitors;
            this.stateMask.put(31, b2);
            if (this.fullScreenAction.init(b)) {
                if (this.fullScreenAction.fsOn() && isOffscreenInstance(this, this.parentWindow)) {
                    if (null == this.parentWindow) {
                        throw new InternalError("Offscreen instance w/o parent unhandled");
                    }
                    this.nfs_parent = this.parentWindow;
                    this.reparentWindow(null, -1, -1, 3);
                }
                this.runOnEDTIfAvail(true, this.fullScreenAction);
                if (!this.fullScreenAction.fsOn() && null != this.nfs_parent) {
                    this.reparentWindow(this.nfs_parent, -1, -1, 3);
                    this.nfs_parent = null;
                }
            }
            return this.stateMask.get(11);
        }
    }
    
    protected void monitorModeChanged(final MonitorEvent monitorEvent, final boolean b) {
    }
    
    @Override
    public final boolean removeChild(final NativeWindow nativeWindow) {
        synchronized (this.childWindowsLock) {
            return this.childWindows.remove(nativeWindow);
        }
    }
    
    @Override
    public final boolean addChild(final NativeWindow nativeWindow) {
        if (nativeWindow == null) {
            return false;
        }
        synchronized (this.childWindowsLock) {
            return this.childWindows.add(nativeWindow);
        }
    }
    
    private void doEvent(final boolean b, boolean consumeEvent, final NEWTEvent newtEvent) {
        int n = 0;
        if (!b) {
            n = ((consumeEvent = this.consumeEvent(newtEvent)) ? 1 : 0);
        }
        if (n == 0) {
            this.enqueueEvent(consumeEvent, newtEvent);
        }
    }
    
    @Override
    public final void enqueueEvent(final boolean b, final NEWTEvent newtEvent) {
        if (this.isNativeValid()) {
            ((DisplayImpl)this.screen.getDisplay()).enqueueEvent(b, newtEvent);
        }
    }
    
    @Override
    public final boolean consumeEvent(final NEWTEvent newtEvent) {
        switch (newtEvent.getEventType()) {
            case 105: {
                if (!this.windowLock.isLockedByOtherThread()) {
                    this.repaintQueued = false;
                    break;
                }
                if (!this.repaintQueued) {
                    this.repaintQueued = true;
                    final boolean b = 1200L <= System.currentTimeMillis() - newtEvent.getWhen();
                    if (WindowImpl.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.consumeEvent: REPAINT [me " + Thread.currentThread().getName() + ", owner " + this.windowLock.getOwner() + "] - queued " + newtEvent + ", discard-to " + b);
                    }
                    return b;
                }
                return true;
            }
            case 100: {
                if (this.windowLock.isLockedByOtherThread()) {
                    final boolean b2 = 1200L <= System.currentTimeMillis() - newtEvent.getWhen();
                    if (WindowImpl.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.consumeEvent: RESIZED [me " + Thread.currentThread().getName() + ", owner " + this.windowLock.getOwner() + "] - queued " + newtEvent + ", discard-to " + b2);
                    }
                    return b2;
                }
                break;
            }
        }
        if (newtEvent instanceof WindowEvent) {
            this.consumeWindowEvent((WindowEvent)newtEvent);
        }
        else if (newtEvent instanceof KeyEvent) {
            this.consumeKeyEvent((KeyEvent)newtEvent);
        }
        else {
            if (!(newtEvent instanceof MouseEvent)) {
                throw new NativeWindowException("Unexpected NEWTEvent type " + newtEvent);
            }
            this.consumePointerEvent((MouseEvent)newtEvent);
        }
        return true;
    }
    
    public final void sendMouseEvent(final short n, final int n2, final int n3, final int n4, final short n5, final float n6) {
        this.doMouseEvent(false, false, n, n2, n3, n4, n5, MouseEvent.getRotationXYZ(n6, n2), 1.0f);
    }
    
    public final void enqueueMouseEvent(final boolean b, final short n, final int n2, final int n3, final int n4, final short n5, final float n6) {
        this.doMouseEvent(true, b, n, n2, n3, n4, n5, MouseEvent.getRotationXYZ(n6, n2), 1.0f);
    }
    
    protected final void doMouseEvent(final boolean b, final boolean b2, final short n, final int n2, final int n3, final int n4, final short n5, final float n6) {
        this.doMouseEvent(b, b2, n, n2, n3, n4, n5, MouseEvent.getRotationXYZ(n6, n2), 1.0f);
    }
    
    protected void doMouseEvent(final boolean b, final boolean b2, final short n, final int n2, final int n3, final int n4, final short n5, final float[] array, final float n6) {
        if (0 > n5 || n5 > 16) {
            throw new NativeWindowException("Invalid mouse button number" + n5);
        }
        this.doPointerEvent(b, b2, WindowImpl.constMousePointerTypes, n, n2, 0, new short[] { 0 }, n5, new int[] { n3 }, new int[] { n4 }, new float[] { 0.0f }, 1.0f, array, n6);
    }
    
    public final void doPointerEvent(final boolean b, final boolean b2, final MouseEvent.PointerType[] array, final short n, final int n2, final int n3, final boolean b3, final int[] array2, final int[] array3, final int[] array4, final float[] array5, final float n4, final float[] array6, final float n5) {
        final int length = array2.length;
        final short[] array7 = new short[length];
        for (int i = 0; i < length; ++i) {
            if (!b3) {
                final int size = this.pName2pID.size();
                final Integer n6 = this.pName2pID.getOrAdd(array2[i]);
                final short n7 = (short)this.pName2pID.indexOf(n6);
                array7[i] = n7;
                if (WindowImpl.DEBUG_MOUSE_EVENT) {
                    final int size2 = this.pName2pID.size();
                    if (size != size2) {
                        System.err.println("PointerName2ID[sz " + size2 + "]: Map " + n6 + " == " + n7);
                    }
                }
                if (204 == n) {
                    this.pName2pID.remove(n6);
                    if (WindowImpl.DEBUG_MOUSE_EVENT) {
                        System.err.println("PointerName2ID[sz " + this.pName2pID.size() + "]: Unmap " + n6 + " == " + n7);
                    }
                }
            }
            else {
                array7[i] = (short)array2[i];
            }
        }
        this.doPointerEvent(b, b2, array, n, n2, n3, array7, (short)((0 < length) ? ((short)(array7[0] + 1)) : 0), array3, array4, array5, n4, array6, n5);
    }
    
    public final void doPointerEvent(final boolean b, final boolean b2, final MouseEvent.PointerType[] array, final short n, int n2, final int n3, final short[] array2, final short n4, final int[] array3, final int[] array4, final float[] array5, final float n5, final float[] array6, final float n6) {
        final long currentTimeMillis = System.currentTimeMillis();
        final int length = array.length;
        if (0 > n3 || n3 >= length) {
            throw new IllegalArgumentException("actionIdx out of bounds [0.." + (length - 1) + "]");
        }
        if (0 < n3) {
            final MouseEvent.PointerType pointerType = array[n3];
            array[n3] = array[0];
            array[0] = pointerType;
            final short n7 = array2[n3];
            array2[n3] = array2[0];
            array2[0] = n7;
            final int n8 = array3[n3];
            array3[n3] = array3[0];
            array3[0] = n8;
            final int n9 = array4[n3];
            array4[n3] = array4[0];
            array4[0] = n9;
            final float n10 = array5[n3];
            array5[n3] = array5[0];
            array5[0] = n10;
        }
        short buttonPressed;
        if (0 <= n4 && n4 <= 16) {
            buttonPressed = n4;
        }
        else {
            buttonPressed = 1;
        }
        int n11 = array3[0];
        int n12 = array4[0];
        final boolean insideSurface = n11 >= 0 && n12 >= 0 && n11 < this.getSurfaceWidth() && n12 < this.getSurfaceHeight();
        final Point movePosition = this.pState1.getMovePosition(array2[0]);
        Label_0685: {
            switch (n) {
                case 202: {
                    if (this.pState1.dragging) {
                        if (WindowImpl.DEBUG_MOUSE_EVENT) {
                            System.err.println("doPointerEvent: drop " + MouseEvent.getEventTypeString(n) + " due to dragging: " + this.pState1);
                        }
                        return;
                    }
                    if (null != movePosition) {
                        if (n11 == -1 && n12 == -1) {
                            n11 = movePosition.getX();
                            n12 = movePosition.getY();
                        }
                        movePosition.set(0, 0);
                    }
                }
                case 201: {
                    if (n == 201) {
                        this.pState1.insideSurface = true;
                        this.pState1.exitSent = false;
                    }
                    else {
                        this.pState1.insideSurface = false;
                        this.pState1.exitSent = true;
                    }
                    this.pState1.clearButton();
                    if (array[0] != MouseEvent.PointerType.Mouse) {
                        if (WindowImpl.DEBUG_MOUSE_EVENT) {
                            System.err.println("doPointerEvent: drop " + MouseEvent.getEventTypeString(n) + " due to !Mouse but " + array[0] + ": " + this.pState1);
                        }
                        return;
                    }
                    n11 = Math.min(Math.max(n11, 0), this.getSurfaceWidth() - 1);
                    n12 = Math.min(Math.max(n12, 0), this.getSurfaceHeight() - 1);
                    break Label_0685;
                }
                case 205:
                case 206: {
                    if (null == movePosition) {
                        break;
                    }
                    if (movePosition.getX() == n11 && movePosition.getY() == n12) {
                        if (WindowImpl.DEBUG_MOUSE_EVENT) {
                            System.err.println("doPointerEvent: drop " + MouseEvent.getEventTypeString(n) + " w/ same position: " + movePosition + ", " + this.pState1);
                        }
                        return;
                    }
                    movePosition.set(n11, n12);
                    break;
                }
            }
            if (this.pState1.insideSurface != insideSurface) {
                this.pState1.insideSurface = insideSurface;
                if (insideSurface) {
                    this.pState1.exitSent = false;
                }
                this.pState1.clearButton();
            }
        }
        if (!this.pState1.dragging && !insideSurface && 202 != n) {
            if (WindowImpl.DEBUG_MOUSE_EVENT) {
                System.err.println("doPointerEvent: drop: " + MouseEvent.getEventTypeString(n) + ", mod " + n2 + ", pos " + n11 + "/" + n12 + ", button " + buttonPressed + ", lastMousePosition: " + movePosition + ", insideWindow " + insideSurface + ", " + this.pState1);
            }
            return;
        }
        if (WindowImpl.DEBUG_MOUSE_EVENT) {
            System.err.println("doPointerEvent: enqueue " + b + ", wait " + b2 + ", " + MouseEvent.getEventTypeString(n) + ", mod " + n2 + ", pos " + n11 + "/" + n12 + ", button " + buttonPressed + ", lastMousePosition: " + movePosition + ", " + this.pState1);
        }
        final int buttonMask = InputEvent.getButtonMask(buttonPressed);
        n2 |= buttonMask;
        n2 |= this.pState1.buttonPressedMask;
        if (this.isPointerConfined()) {
            n2 |= 0x40000000;
        }
        if (!this.isPointerVisible()) {
            n2 |= Integer.MIN_VALUE;
        }
        array3[0] = n11;
        array4[0] = n12;
        MouseEvent mouseEvent = null;
        Label_1568: {
            switch (n) {
                case 200: {
                    return;
                }
                case 203: {
                    if (0.0f >= array5[0]) {
                        array5[0] = n5;
                    }
                    final PointerState1 pState1 = this.pState1;
                    pState1.buttonPressedMask |= buttonMask;
                    if (length != 0) {
                        if (currentTimeMillis - this.pState1.lastButtonPressTime < MouseEvent.getClickTimeout()) {
                            final PointerState1 pState2 = this.pState1;
                            ++pState2.lastButtonClickCount;
                        }
                        else {
                            this.pState1.lastButtonClickCount = 1;
                        }
                        this.pState1.lastButtonPressTime = currentTimeMillis;
                        this.pState1.buttonPressed = buttonPressed;
                        mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, this.pState1.lastButtonClickCount, array6, n6);
                        break Label_1568;
                    }
                    mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, (short)1, array6, n6);
                    break Label_1568;
                }
                case 204: {
                    final PointerState1 pState3 = this.pState1;
                    pState3.buttonPressedMask &= ~buttonMask;
                    if (length != 0) {
                        mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, this.pState1.lastButtonClickCount, array6, n6);
                        if (currentTimeMillis - this.pState1.lastButtonPressTime >= MouseEvent.getClickTimeout()) {
                            this.pState1.lastButtonClickCount = 0;
                            this.pState1.lastButtonPressTime = 0L;
                        }
                        this.pState1.buttonPressed = 0;
                        this.pState1.dragging = false;
                    }
                    else {
                        mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, (short)1, array6, n6);
                        if (0 == this.pState1.buttonPressedMask) {
                            this.pState1.clearButton();
                        }
                    }
                    if (null != movePosition) {
                        movePosition.set(0, 0);
                    }
                    break Label_1568;
                }
                case 205: {
                    if (0 != this.pState1.buttonPressedMask) {
                        mouseEvent = new MouseEvent((short)206, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, this.pState1.buttonPressed, (short)1, array6, n6);
                        this.pState1.dragging = true;
                        break Label_1568;
                    }
                    mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, (short)0, array6, n6);
                    break Label_1568;
                }
                case 206: {
                    if (0.0f >= array5[0]) {
                        array5[0] = n5;
                    }
                    this.pState1.dragging = true;
                    break;
                }
            }
            mouseEvent = new MouseEvent(n, this, currentTimeMillis, n2, array, array2, array3, array4, array5, n5, buttonPressed, (short)0, array6, n6);
        }
        this.doEvent(b, b2, mouseEvent);
    }
    
    private static int step(final int n, final int n2, final int n3) {
        return (n3 < n2) ? n : n3;
    }
    
    protected void consumePointerEvent(MouseEvent mouseEvent) {
        if (WindowImpl.DEBUG_MOUSE_EVENT) {
            System.err.println("consumePointerEvent.in: " + mouseEvent + ", " + this.pState0 + ", pos " + mouseEvent.getX() + "/" + mouseEvent.getY() + ", win[" + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + "], pixel[" + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + "]");
        }
        final long when = mouseEvent.getWhen();
        final short eventType = mouseEvent.getEventType();
        boolean b = false;
        MouseEvent mouseEvent2 = null;
        MouseEvent mouseEvent3 = null;
        boolean insideSurface = false;
        Label_0671: {
            switch (eventType) {
                case 202: {
                    if (this.pState0.exitSent || this.pState0.dragging) {
                        if (WindowImpl.DEBUG_MOUSE_EVENT) {
                            System.err.println("consumePointerEvent: drop " + (this.pState0.exitSent ? "already sent" : "due to dragging") + ": " + mouseEvent + ", " + this.pState0);
                        }
                        return;
                    }
                }
                case 201: {
                    this.pState0.clearButton();
                    if (eventType == 201) {
                        insideSurface = true;
                        this.pState0.insideSurface = true;
                        this.pState0.exitSent = false;
                        this.pState0.dragging = false;
                        break Label_0671;
                    }
                    insideSurface = false;
                    this.pState0.insideSurface = false;
                    this.pState0.exitSent = true;
                    break Label_0671;
                }
                case 204:
                case 205: {
                    if (1 >= mouseEvent.getButtonDownCount()) {
                        b = !this.pState0.exitSent;
                        this.pState0.dragging = false;
                        break;
                    }
                    break;
                }
            }
            final int x = mouseEvent.getX();
            final int y = mouseEvent.getY();
            insideSurface = (x >= 0 && y >= 0 && x < this.getSurfaceWidth() && y < this.getSurfaceHeight());
            if (mouseEvent.getPointerType(0) == MouseEvent.PointerType.Mouse) {
                if (!this.pState0.insideSurface && insideSurface) {
                    mouseEvent2 = new MouseEvent((short)201, mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), Math.min(Math.max(x, 0), this.getSurfaceWidth() - 1), Math.min(Math.max(y, 0), this.getSurfaceHeight() - 1), (short)0, (short)0, mouseEvent.getRotation(), mouseEvent.getRotationScale());
                    this.pState0.exitSent = false;
                }
                else if (!insideSurface && b) {
                    mouseEvent3 = new MouseEvent((short)202, mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), Math.min(Math.max(x, 0), this.getSurfaceWidth() - 1), Math.min(Math.max(y, 0), this.getSurfaceHeight() - 1), (short)0, (short)0, mouseEvent.getRotation(), mouseEvent.getRotationScale());
                    this.pState0.exitSent = true;
                }
            }
            if (this.pState0.insideSurface != insideSurface || null != mouseEvent2 || null != mouseEvent3) {
                this.pState0.clearButton();
            }
            this.pState0.insideSurface = insideSurface;
        }
        if (null != mouseEvent2) {
            if (WindowImpl.DEBUG_MOUSE_EVENT) {
                System.err.println("consumePointerEvent.send.0: " + mouseEvent2 + ", " + this.pState0);
            }
            this.dispatchMouseEvent(mouseEvent2);
        }
        else if (WindowImpl.DEBUG_MOUSE_EVENT && !insideSurface) {
            System.err.println("INFO consumePointerEvent.exterior: " + this.pState0 + ", " + mouseEvent);
        }
        if (this.defaultGestureHandlerEnabled && mouseEvent.getPointerType(0).getPointerClass() == MouseEvent.PointerClass.Onscreen) {
            if (null == this.gesture2PtrTouchScroll) {
                final MonitorDevice mainMonitor = this.getMainMonitor();
                int n;
                int n2;
                if (null != mainMonitor) {
                    final DimensionImmutable sizeMM = mainMonitor.getSizeMM();
                    final float min = Math.min(mainMonitor.getCurrentMode().getRotatedHeight() / sizeMM.getHeight(), mainMonitor.getCurrentMode().getRotatedWidth() / sizeMM.getWidth());
                    n = Math.round(DoubleTapScrollGesture.SCROLL_SLOP_MM * min);
                    n2 = Math.round(DoubleTapScrollGesture.DOUBLE_TAP_SLOP_MM * min);
                    if (WindowImpl.DEBUG_MOUSE_EVENT) {
                        System.err.println("consumePointerEvent.gscroll: scrollSlop " + n + ", doubleTapSlop " + n2 + ", pixPerMM " + min + ", " + mainMonitor + ", " + this.pState0);
                    }
                }
                else {
                    n = DoubleTapScrollGesture.SCROLL_SLOP_PIXEL;
                    n2 = DoubleTapScrollGesture.DOUBLE_TAP_SLOP_PIXEL;
                }
                this.gesture2PtrTouchScroll = new DoubleTapScrollGesture(step(DoubleTapScrollGesture.SCROLL_SLOP_PIXEL, DoubleTapScrollGesture.SCROLL_SLOP_PIXEL / 2, n), step(DoubleTapScrollGesture.DOUBLE_TAP_SLOP_PIXEL, DoubleTapScrollGesture.DOUBLE_TAP_SLOP_PIXEL / 2, n2));
            }
            if (this.gesture2PtrTouchScroll.process(mouseEvent)) {
                mouseEvent = (MouseEvent)this.gesture2PtrTouchScroll.getGestureEvent();
                this.gesture2PtrTouchScroll.clear(false);
                if (WindowImpl.DEBUG_MOUSE_EVENT) {
                    System.err.println("consumePointerEvent.gscroll: " + mouseEvent + ", " + this.pState0);
                }
                this.dispatchMouseEvent(mouseEvent);
                return;
            }
            if (this.gesture2PtrTouchScroll.isWithinGesture()) {
                return;
            }
        }
        final int size = this.pointerGestureHandler.size();
        if (size > 0) {
            boolean b2 = false;
            for (int n3 = 0; !mouseEvent.isConsumed() && n3 < size; ++n3) {
                final GestureHandler gestureHandler = this.pointerGestureHandler.get(n3);
                if (gestureHandler.process(mouseEvent)) {
                    final InputEvent gestureEvent = gestureHandler.getGestureEvent();
                    gestureHandler.clear(false);
                    if (gestureEvent instanceof MouseEvent) {
                        this.dispatchMouseEvent((MouseEvent)gestureEvent);
                    }
                    else if (gestureEvent instanceof GestureHandler.GestureEvent) {
                        final GestureHandler.GestureEvent gestureEvent2 = (GestureHandler.GestureEvent)gestureEvent;
                        for (int n4 = 0; !gestureEvent2.isConsumed() && n4 < this.gestureListeners.size(); ++n4) {
                            this.gestureListeners.get(n4).gestureDetected(gestureEvent2);
                        }
                    }
                    return;
                }
                b2 |= gestureHandler.isWithinGesture();
            }
            if (b2) {
                return;
            }
        }
        MouseEvent variant = null;
        switch (eventType) {
            case 203: {
                if (1 == mouseEvent.getPointerCount()) {
                    this.pState0.lastButtonPressTime = when;
                    break;
                }
                break;
            }
            case 204: {
                if (1 == mouseEvent.getPointerCount() && when - this.pState0.lastButtonPressTime < MouseEvent.getClickTimeout()) {
                    variant = mouseEvent.createVariant((short)200);
                    break;
                }
                this.pState0.lastButtonPressTime = 0L;
                break;
            }
            case 200: {
                if (WindowImpl.DEBUG_MOUSE_EVENT) {
                    System.err.println("consumePointerEvent: drop recv'ed (synth here) " + mouseEvent + ", " + this.pState0);
                }
                mouseEvent = null;
                break;
            }
            case 206: {
                this.pState0.dragging = true;
                break;
            }
        }
        if (null != mouseEvent) {
            if (WindowImpl.DEBUG_MOUSE_EVENT) {
                System.err.println("consumePointerEvent.send.1: " + mouseEvent + ", " + this.pState0);
            }
            this.dispatchMouseEvent(mouseEvent);
        }
        if (null != variant) {
            if (WindowImpl.DEBUG_MOUSE_EVENT) {
                System.err.println("consumePointerEvent.send.2: " + variant + ", " + this.pState0);
            }
            this.dispatchMouseEvent(variant);
        }
        if (null != mouseEvent3) {
            if (WindowImpl.DEBUG_MOUSE_EVENT) {
                System.err.println("consumePointerEvent.send.3: " + mouseEvent3 + ", " + this.pState0);
            }
            this.dispatchMouseEvent(mouseEvent3);
        }
    }
    
    @Override
    public final void addMouseListener(final MouseListener mouseListener) {
        this.addMouseListener(-1, mouseListener);
    }
    
    @Override
    public final void addMouseListener(int size, final MouseListener mouseListener) {
        if (mouseListener == null) {
            return;
        }
        final ArrayList mouseListeners = (ArrayList)this.mouseListeners.clone();
        if (0 > size) {
            size = mouseListeners.size();
        }
        mouseListeners.add(size, mouseListener);
        this.mouseListeners = (ArrayList<MouseListener>)mouseListeners;
    }
    
    @Override
    public final void removeMouseListener(final MouseListener mouseListener) {
        if (mouseListener == null) {
            return;
        }
        final ArrayList mouseListeners = (ArrayList)this.mouseListeners.clone();
        mouseListeners.remove(mouseListener);
        this.mouseListeners = (ArrayList<MouseListener>)mouseListeners;
    }
    
    @Override
    public final MouseListener getMouseListener(int n) {
        final ArrayList list = (ArrayList)this.mouseListeners.clone();
        if (0 > n) {
            n = list.size() - 1;
        }
        return list.get(n);
    }
    
    @Override
    public final MouseListener[] getMouseListeners() {
        return this.mouseListeners.toArray(new MouseListener[this.mouseListeners.size()]);
    }
    
    @Override
    public final void setDefaultGesturesEnabled(final boolean defaultGestureHandlerEnabled) {
        this.defaultGestureHandlerEnabled = defaultGestureHandlerEnabled;
    }
    
    @Override
    public final boolean areDefaultGesturesEnabled() {
        return this.defaultGestureHandlerEnabled;
    }
    
    @Override
    public final void addGestureHandler(final GestureHandler gestureHandler) {
        this.addGestureHandler(-1, gestureHandler);
    }
    
    @Override
    public final void addGestureHandler(int size, final GestureHandler gestureHandler) {
        if (gestureHandler == null) {
            return;
        }
        final ArrayList pointerGestureHandler = (ArrayList)this.pointerGestureHandler.clone();
        if (0 > size) {
            size = pointerGestureHandler.size();
        }
        pointerGestureHandler.add(size, gestureHandler);
        this.pointerGestureHandler = (ArrayList<GestureHandler>)pointerGestureHandler;
    }
    
    @Override
    public final void removeGestureHandler(final GestureHandler gestureHandler) {
        if (gestureHandler == null) {
            return;
        }
        final ArrayList pointerGestureHandler = (ArrayList)this.pointerGestureHandler.clone();
        pointerGestureHandler.remove(gestureHandler);
        this.pointerGestureHandler = (ArrayList<GestureHandler>)pointerGestureHandler;
    }
    
    @Override
    public final void addGestureListener(final GestureHandler.GestureListener gestureListener) {
        this.addGestureListener(-1, gestureListener);
    }
    
    @Override
    public final void addGestureListener(int size, final GestureHandler.GestureListener gestureListener) {
        if (gestureListener == null) {
            return;
        }
        final ArrayList gestureListeners = (ArrayList)this.gestureListeners.clone();
        if (0 > size) {
            size = gestureListeners.size();
        }
        gestureListeners.add(size, gestureListener);
        this.gestureListeners = (ArrayList<GestureHandler.GestureListener>)gestureListeners;
    }
    
    @Override
    public final void removeGestureListener(final GestureHandler.GestureListener gestureListener) {
        if (gestureListener == null) {
            return;
        }
        final ArrayList gestureListeners = (ArrayList)this.gestureListeners.clone();
        gestureListeners.remove(gestureListener);
        this.gestureListeners = (ArrayList<GestureHandler.GestureListener>)gestureListeners;
    }
    
    private final void dispatchMouseEvent(final MouseEvent mouseEvent) {
        for (int n = 0; !mouseEvent.isConsumed() && n < this.mouseListeners.size(); ++n) {
            final MouseListener mouseListener = this.mouseListeners.get(n);
            switch (mouseEvent.getEventType()) {
                case 200: {
                    mouseListener.mouseClicked(mouseEvent);
                    break;
                }
                case 201: {
                    mouseListener.mouseEntered(mouseEvent);
                    break;
                }
                case 202: {
                    mouseListener.mouseExited(mouseEvent);
                    break;
                }
                case 203: {
                    mouseListener.mousePressed(mouseEvent);
                    break;
                }
                case 204: {
                    mouseListener.mouseReleased(mouseEvent);
                    break;
                }
                case 205: {
                    mouseListener.mouseMoved(mouseEvent);
                    break;
                }
                case 206: {
                    mouseListener.mouseDragged(mouseEvent);
                    break;
                }
                case 207: {
                    mouseListener.mouseWheelMoved(mouseEvent);
                    break;
                }
                default: {
                    throw new NativeWindowException("Unexpected mouse event type " + mouseEvent.getEventType());
                }
            }
        }
    }
    
    protected final boolean isKeyCodeTracked(final short n) {
        return (0xFFFF & n) <= 255;
    }
    
    protected final boolean setKeyPressed(final short n, final boolean b) {
        final int n2 = 0xFFFF & n;
        return n2 <= 255 && this.keyPressedState.put(n2, b);
    }
    
    protected final boolean isKeyPressed(final short n) {
        final int n2 = 0xFFFF & n;
        return n2 <= 255 && this.keyPressedState.get(n2);
    }
    
    public void sendKeyEvent(final short n, final int n2, final short n3, final short n4, final char c) {
        this.consumeKeyEvent(KeyEvent.create(n, this, System.currentTimeMillis(), n2 | this.pState1.buttonPressedMask, n3, n4, c));
    }
    
    public void enqueueKeyEvent(final boolean b, final short n, final int n2, final short n3, final short n4, final char c) {
        this.enqueueEvent(b, KeyEvent.create(n, this, System.currentTimeMillis(), n2 | this.pState1.buttonPressedMask, n3, n4, c));
    }
    
    @Override
    public final void setKeyboardVisible(final boolean keyboardVisibleImpl) {
        if (this.isNativeValid()) {
            final boolean setKeyboardVisibleImpl = this.setKeyboardVisibleImpl(keyboardVisibleImpl);
            if (WindowImpl.DEBUG_IMPLEMENTATION || WindowImpl.DEBUG_KEY_EVENT) {
                System.err.println("setKeyboardVisible(native): visible " + this.keyboardVisible + " -- op[visible:" + keyboardVisibleImpl + ", ok " + setKeyboardVisibleImpl + "] -> " + (keyboardVisibleImpl && setKeyboardVisibleImpl));
            }
            this.keyboardVisibilityChanged(keyboardVisibleImpl && setKeyboardVisibleImpl);
        }
        else {
            this.keyboardVisibilityChanged(keyboardVisibleImpl);
        }
    }
    
    @Override
    public final boolean isKeyboardVisible() {
        return this.keyboardVisible;
    }
    
    protected boolean setKeyboardVisibleImpl(final boolean b) {
        return false;
    }
    
    protected void keyboardVisibilityChanged(final boolean keyboardVisible) {
        if (this.keyboardVisible != keyboardVisible) {
            if (WindowImpl.DEBUG_IMPLEMENTATION || WindowImpl.DEBUG_KEY_EVENT) {
                System.err.println("keyboardVisibilityChanged: " + this.keyboardVisible + " -> " + keyboardVisible);
            }
            this.keyboardVisible = keyboardVisible;
        }
    }
    
    @Override
    public final void addKeyListener(final KeyListener keyListener) {
        this.addKeyListener(-1, keyListener);
    }
    
    @Override
    public final void addKeyListener(int size, final KeyListener keyListener) {
        if (keyListener == null) {
            return;
        }
        final ArrayList keyListeners = (ArrayList)this.keyListeners.clone();
        if (0 > size) {
            size = keyListeners.size();
        }
        keyListeners.add(size, keyListener);
        this.keyListeners = (ArrayList<KeyListener>)keyListeners;
    }
    
    @Override
    public final void removeKeyListener(final KeyListener keyListener) {
        if (keyListener == null) {
            return;
        }
        final ArrayList keyListeners = (ArrayList)this.keyListeners.clone();
        keyListeners.remove(keyListener);
        this.keyListeners = (ArrayList<KeyListener>)keyListeners;
    }
    
    @Override
    public final KeyListener getKeyListener(int n) {
        final ArrayList list = (ArrayList)this.keyListeners.clone();
        if (0 > n) {
            n = list.size() - 1;
        }
        return list.get(n);
    }
    
    @Override
    public final KeyListener[] getKeyListeners() {
        return this.keyListeners.toArray(new KeyListener[this.keyListeners.size()]);
    }
    
    private final boolean propagateKeyEvent(final KeyEvent keyEvent, final KeyListener keyListener) {
        switch (keyEvent.getEventType()) {
            case 300: {
                keyListener.keyPressed(keyEvent);
                break;
            }
            case 301: {
                keyListener.keyReleased(keyEvent);
                break;
            }
            default: {
                throw new NativeWindowException("Unexpected key event type " + keyEvent.getEventType());
            }
        }
        return keyEvent.isConsumed();
    }
    
    protected void consumeKeyEvent(final KeyEvent keyEvent) {
        boolean b = false;
        if (null != this.keyboardFocusHandler && !keyEvent.isAutoRepeat()) {
            b = this.propagateKeyEvent(keyEvent, this.keyboardFocusHandler);
            if (WindowImpl.DEBUG_KEY_EVENT && b) {
                System.err.println("consumeKeyEvent(kfh): " + keyEvent + ", consumed: " + b);
            }
        }
        if (!b) {
            for (int n = 0; !b && n < this.keyListeners.size(); b = this.propagateKeyEvent(keyEvent, this.keyListeners.get(n)), ++n) {}
            if (WindowImpl.DEBUG_KEY_EVENT) {
                System.err.println("consumeKeyEvent(usr): " + keyEvent + ", consumed: " + b);
            }
        }
    }
    
    @Override
    public final void sendWindowEvent(final int n) {
        this.consumeWindowEvent(new WindowEvent((short)n, this, System.currentTimeMillis()));
    }
    
    public final void enqueueWindowEvent(final boolean b, final int n) {
        this.enqueueEvent(b, new WindowEvent((short)n, this, System.currentTimeMillis()));
    }
    
    @Override
    public final void addWindowListener(final WindowListener windowListener) {
        this.addWindowListener(-1, windowListener);
    }
    
    @Override
    public final void addWindowListener(int size, final WindowListener windowListener) throws IndexOutOfBoundsException {
        if (windowListener == null) {
            return;
        }
        final ArrayList windowListeners = (ArrayList)this.windowListeners.clone();
        if (0 > size) {
            size = windowListeners.size();
        }
        windowListeners.add(size, windowListener);
        this.windowListeners = (ArrayList<WindowListener>)windowListeners;
    }
    
    @Override
    public final void removeWindowListener(final WindowListener windowListener) {
        if (windowListener == null) {
            return;
        }
        final ArrayList windowListeners = (ArrayList)this.windowListeners.clone();
        windowListeners.remove(windowListener);
        this.windowListeners = (ArrayList<WindowListener>)windowListeners;
    }
    
    @Override
    public final WindowListener getWindowListener(int n) {
        final ArrayList list = (ArrayList)this.windowListeners.clone();
        if (0 > n) {
            n = list.size() - 1;
        }
        return list.get(n);
    }
    
    @Override
    public final WindowListener[] getWindowListeners() {
        return this.windowListeners.toArray(new WindowListener[this.windowListeners.size()]);
    }
    
    protected void consumeWindowEvent(final WindowEvent windowEvent) {
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("consumeWindowEvent: " + windowEvent + ", visible " + this.isVisible() + " " + this.getX() + "/" + this.getY() + ", win[" + this.getX() + "/" + this.getY() + " " + this.getWidth() + "x" + this.getHeight() + "], pixel[" + this.getSurfaceWidth() + "x" + this.getSurfaceHeight() + "]");
        }
        for (int n = 0; !windowEvent.isConsumed() && n < this.windowListeners.size(); ++n) {
            final WindowListener windowListener = this.windowListeners.get(n);
            switch (windowEvent.getEventType()) {
                case 100: {
                    windowListener.windowResized(windowEvent);
                    break;
                }
                case 101: {
                    windowListener.windowMoved(windowEvent);
                    break;
                }
                case 102: {
                    windowListener.windowDestroyNotify(windowEvent);
                    break;
                }
                case 106: {
                    windowListener.windowDestroyed(windowEvent);
                    break;
                }
                case 103: {
                    windowListener.windowGainedFocus(windowEvent);
                    break;
                }
                case 104: {
                    windowListener.windowLostFocus(windowEvent);
                    break;
                }
                case 105: {
                    windowListener.windowRepaint((WindowUpdateEvent)windowEvent);
                    break;
                }
                default: {
                    throw new NativeWindowException("Unexpected window event type " + windowEvent.getEventType());
                }
            }
        }
    }
    
    protected void focusChanged(final boolean b, final boolean b2) {
        if (this.stateMask.get(30) || this.stateMask.get(3) != b2) {
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.focusChanged: (" + getThreadName() + "): (defer: " + b + ") state " + this.getStateMaskString() + " -> focus " + b2 + " - windowHandle " + toHexString(this.windowHandle) + " parentWindowHandle " + toHexString(this.parentWindowHandle));
            }
            this.stateMask.put(3, b2);
            final int n = b2 ? 103 : 104;
            if (!b) {
                this.sendWindowEvent(n);
            }
            else {
                this.enqueueWindowEvent(false, n);
            }
        }
    }
    
    protected final void visibleChanged(final boolean b, final boolean b2) {
        if (this.stateMask.put(0, b2) != b2 && WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.visibleChanged (" + getThreadName() + "): (defer: " + b + ") visible " + !b2 + " -> state " + this.getStateMaskString() + " - windowHandle " + toHexString(this.windowHandle) + " parentWindowHandle " + toHexString(this.parentWindowHandle));
        }
    }
    
    private long waitForVisible(final boolean b, final boolean b2) {
        return this.waitForVisible(b, b2, 1000L);
    }
    
    private long waitForVisible(final boolean b, final boolean b2, final long n) {
        final DisplayImpl displayImpl = (DisplayImpl)this.screen.getDisplay();
        displayImpl.dispatchMessagesNative();
        boolean b3;
        long n2;
        for (b3 = this.stateMask.get(0), n2 = n; 0L < n2 && b3 != b; b3 = this.stateMask.get(0), n2 -= 10L) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException ex2) {}
            displayImpl.dispatchMessagesNative();
        }
        if (b != b3) {
            final String string = "Visibility not reached as requested within " + n + "ms : requested " + b + ", is " + b3;
            if (WindowImpl.DEBUG_FREEZE_AT_VISIBILITY_FAILURE) {
                System.err.println("XXXX: " + string);
                System.err.println("XXXX: FREEZE");
                try {
                    while (true) {
                        Thread.sleep(100L);
                        displayImpl.dispatchMessagesNative();
                    }
                }
                catch (InterruptedException ex) {
                    ExceptionUtils.dumpThrowable("", ex);
                    Thread.currentThread().interrupt();
                    throw new NativeWindowException(string);
                }
            }
            if (b2) {
                throw new NativeWindowException(string);
            }
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println(string);
                ExceptionUtils.dumpStack(System.err);
            }
            return -1L;
        }
        else {
            if (0L < n2) {
                return n2;
            }
            return 0L;
        }
    }
    
    public final void pixelScaleChangeNotify(final float[] array, final float[] array2, final boolean b) {
        System.arraycopy(array, 0, this.minPixelScale, 0, 2);
        System.arraycopy(array2, 0, this.maxPixelScale, 0, 2);
        if (b) {
            this.setSurfaceScale(this.reqPixelScale);
        }
    }
    
    protected void sizeChanged(final boolean b, final int n, final int n2, final boolean b2) {
        if (b2 || this.getWidth() != n || this.getHeight() != n2) {
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.sizeChanged: (" + getThreadName() + "): (defer: " + b + ") force " + b2 + ", " + this.getWidth() + "x" + this.getHeight() + " -> " + n + "x" + n2 + ", state " + this.getStateMaskString() + " - windowHandle " + toHexString(this.windowHandle) + " parentWindowHandle " + toHexString(this.parentWindowHandle));
            }
            if (0 > n || 0 > n2) {
                throw new NativeWindowException("Illegal width or height " + n + "x" + n2 + " (must be >= 0)");
            }
            this.defineSize(n, n2);
            if (this.isNativeValid()) {
                if (!b) {
                    this.sendWindowEvent(100);
                }
                else {
                    this.enqueueWindowEvent(false, 100);
                }
            }
        }
    }
    
    private boolean waitForSize(final int n, final int n2, final boolean b, final long n3) {
        final DisplayImpl displayImpl = (DisplayImpl)this.screen.getDisplay();
        displayImpl.dispatchMessagesNative();
        long n4;
        for (n4 = n3; 0L < n4 && n != this.getWidth() && n2 != this.getHeight(); n4 -= 10L) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException ex) {}
            displayImpl.dispatchMessagesNative();
        }
        if (0L < n4) {
            return true;
        }
        final String string = "Size/Pos not reached as requested within " + n3 + "ms : requested " + n + "x" + n2 + ", is " + this.getWidth() + "x" + this.getHeight();
        if (b) {
            throw new NativeWindowException(string);
        }
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println(string);
            ExceptionUtils.dumpStack(System.err);
        }
        return false;
    }
    
    protected final void positionChanged(final boolean b, final int n, final int n2) {
        if (this.getX() != n || this.getY() != n2) {
            if (WindowImpl.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.positionChanged: (" + getThreadName() + "): (defer: " + b + ") " + this.getX() + "/" + this.getY() + " -> " + n + "/" + n2 + " - windowHandle " + toHexString(this.windowHandle) + " parentWindowHandle " + toHexString(this.parentWindowHandle));
            }
            this.definePosition(n, n2);
            if (!b) {
                this.sendWindowEvent(101);
            }
            else {
                this.enqueueWindowEvent(false, 101);
            }
        }
        else {
            this.stateMask.clear(1);
        }
    }
    
    private boolean waitForPosition(final boolean b, final int n, final int n2, final long n3) {
        final DisplayImpl displayImpl = (DisplayImpl)this.screen.getDisplay();
        final InsetsImmutable insets = this.getInsets();
        final int max = Math.max(64, insets.getLeftWidth() * 2);
        final int max2 = Math.max(64, insets.getTopHeight() * 2);
        long n4 = n3;
        boolean value = false;
        boolean b2;
        do {
            if (b) {
                b2 = (Math.abs(n - this.getX()) <= max && Math.abs(n2 - this.getY()) <= max2);
            }
            else {
                value = this.stateMask.get(1);
                b2 = !value;
            }
            if (!b2) {
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex) {}
                displayImpl.dispatchMessagesNative();
                n4 -= 10L;
            }
        } while (0L < n4 && !b2);
        if (WindowImpl.DEBUG_IMPLEMENTATION && !b2) {
            if (b) {
                System.err.println("Custom position " + n + "/" + n2 + " not reached within timeout, has " + this.getX() + "/" + this.getY() + ", remaining " + n4);
            }
            else {
                System.err.println("Auto position not reached within timeout, has " + this.getX() + "/" + this.getY() + ", autoPosition " + value + ", remaining " + n4);
            }
            ExceptionUtils.dumpStack(System.err);
        }
        return b2;
    }
    
    protected void insetsChanged(final boolean b, final int n, final int n2, final int n3, final int n4) {
        if (n >= 0 && n2 >= 0 && n3 >= 0 && n4 >= 0) {
            final boolean b2 = n != this.insets.getLeftWidth() || n2 != this.insets.getRightWidth() || n3 != this.insets.getTopHeight() || n4 != this.insets.getBottomHeight();
            if (this.blockInsetsChange || this.isUndecorated()) {
                if (WindowImpl.DEBUG_IMPLEMENTATION && b2) {
                    System.err.println("Window.insetsChanged (defer: " + b + "): Skip insets change " + this.insets + " -> " + new Insets(n, n2, n3, n4) + " (blocked " + this.blockInsetsChange + ", undecoration " + this.isUndecorated() + ")");
                }
            }
            else if (b2) {
                if (WindowImpl.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.insetsChanged (defer: " + b + "): Changed " + this.insets + " -> " + new Insets(n, n2, n3, n4));
                }
                this.insets.set(n, n2, n3, n4);
            }
        }
    }
    
    public final boolean windowDestroyNotify(final boolean b) {
        final WindowClosingProtocol.WindowClosingMode defaultCloseOperation = this.getDefaultCloseOperation();
        final WindowClosingProtocol.WindowClosingMode defaultCloseOperation2 = b ? WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE : defaultCloseOperation;
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.windowDestroyNotify(isNativeValid: " + this.isNativeValid() + ", force: " + b + ", mode " + defaultCloseOperation + " -> " + defaultCloseOperation2 + ") " + getThreadName() + ": " + this);
        }
        boolean b2;
        if (this.isNativeValid()) {
            if (WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE == defaultCloseOperation2) {
                if (b) {
                    this.setDefaultCloseOperation(defaultCloseOperation2);
                }
                try {
                    if (null == this.windowDestroyNotifyAction) {
                        this.destroy();
                    }
                    else {
                        this.windowDestroyNotifyAction.run();
                    }
                }
                finally {
                    if (b) {
                        this.setDefaultCloseOperation(defaultCloseOperation);
                    }
                }
            }
            else {
                this.sendWindowEvent(102);
            }
            b2 = !this.isNativeValid();
        }
        else {
            b2 = true;
        }
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.windowDestroyNotify(isNativeValid: " + this.isNativeValid() + ", force: " + b + ", mode " + defaultCloseOperation2 + ") END " + getThreadName() + ": destroyed " + b2 + ", " + this);
        }
        return b2;
    }
    
    @Override
    public final void windowRepaint(final int n, final int n2, final int n3, final int n4) {
        this.windowRepaint(false, n, n2, n3, n4);
    }
    
    protected final void windowRepaint(final boolean b, final int n, final int n2, int n3, int n4) {
        n3 = ((0 >= n3) ? this.getSurfaceWidth() : n3);
        n4 = ((0 >= n4) ? this.getSurfaceHeight() : n4);
        if (WindowImpl.DEBUG_IMPLEMENTATION) {
            System.err.println("Window.windowRepaint " + getThreadName() + " (defer: " + b + ") " + n + "/" + n2 + " " + n3 + "x" + n4);
        }
        if (this.isNativeValid()) {
            this.doEvent(b, false, new WindowUpdateEvent((short)105, this, System.currentTimeMillis(), new Rectangle(n, n2, n3, n4)));
        }
    }
    
    protected final void sendMouseEventRequestFocus(final short n, final int n2, final int n3, final int n4, final short n5, final float n6) {
        this.sendMouseEvent(n, n2, n3, n4, n5, n6);
        this.requestFocus(false);
    }
    
    protected final void visibleChangedSendMouseEvent(final boolean b, final int n, final short n2, final int n3, final int n4, final int n5, final short n6, final float n7) {
        if (0 <= n) {
            this.visibleChanged(b, 0 < n);
        }
        if (0 < n2) {
            if (b) {
                this.enqueueMouseEvent(false, n2, n3, n4, n5, n6, n7);
            }
            else {
                this.sendMouseEvent(n2, n3, n4, n5, n6, n7);
            }
        }
    }
    
    protected final void visibleChangedWindowRepaint(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (0 <= n) {
            this.visibleChanged(b, 0 < n);
        }
        this.windowRepaint(b, n2, n3, n4, n5);
    }
    
    protected final void focusVisibleChanged(final boolean b, final int n, final int n2) {
        if (0 <= n) {
            this.focusChanged(b, 0 < n);
        }
        if (0 <= n2) {
            this.visibleChanged(b, 0 < n2);
        }
    }
    
    protected final void insetsVisibleChanged(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5) {
        this.insetsChanged(b, n, n2, n3, n4);
        if (0 <= n5) {
            this.visibleChanged(b, 0 < n5);
        }
    }
    
    protected final void sizePosInsetsFocusVisibleChanged(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final boolean b2) {
        this.sizeChanged(b, n3, n4, b2);
        this.positionChanged(b, n, n2);
        this.insetsChanged(b, n5, n6, n7, n8);
        if (0 <= n9) {
            this.focusChanged(b, 0 < n9);
        }
        if (0 <= n10) {
            this.visibleChanged(b, 0 < n10);
        }
    }
    
    protected final void sizePosMaxInsetsVisibleChanged(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final boolean b2) {
        this.sizeChanged(b, n3, n4, b2);
        this.positionChanged(b, n, n2);
        if (0 <= n5 && 0 <= n6) {
            this.maximizedChanged(0 < n5, 0 < n6);
        }
        this.insetsChanged(b, n7, n8, n9, n10);
        if (0 <= n11) {
            this.visibleChanged(b, 0 < n11);
        }
    }
    
    private static Class<?>[] getCustomConstructorArgumentTypes(final Class<?> clazz) {
        Class<?>[] array = null;
        try {
            array = (Class<?>[])clazz.getDeclaredMethod("getCustomConstructorArgumentTypes", (Class[])new Class[0]).invoke(null, (Object[])null);
        }
        catch (Throwable t) {}
        return array;
    }
    
    private static int verifyConstructorArgumentTypes(final Class<?>[] array, final Object[] array2) {
        if (array.length != array2.length) {
            return -1;
        }
        for (int i = 0; i < array2.length; ++i) {
            if (!array[i].isInstance(array2[i])) {
                return i;
            }
        }
        return array2.length;
    }
    
    private static String getArgsStrList(final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i].getClass());
            if (i < array.length) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    private static String getTypeStrList(final Class<?>[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i < array.length) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    public static String getThreadName() {
        return Display.getThreadName();
    }
    
    public static String toHexString(final int n) {
        return Display.toHexString(n);
    }
    
    public static String toHexString(final long n) {
        return Display.toHexString(n);
    }
    
    static {
        Debug.initSingleton();
        DEBUG_TEST_REPARENT_INCOMPATIBLE = PropertyAccess.isPropertyDefined("newt.test.Window.reparent.incompatible", true);
        DEBUG_FREEZE_AT_VISIBILITY_FAILURE = PropertyAccess.isPropertyDefined("newt.debug.Window.visibility.failure.freeze", true);
        ScreenImpl.initSingleton();
        windowList = new ArrayList<WeakReference<WindowImpl>>();
        constMousePointerTypes = new MouseEvent.PointerType[] { MouseEvent.PointerType.Mouse };
        quirks = Bitfield.Factory.synchronize(Bitfield.Factory.create(32));
    }
    
    private static class PointerState0
    {
        boolean insideSurface;
        boolean exitSent;
        long lastButtonPressTime;
        boolean dragging;
        
        private PointerState0() {
            this.insideSurface = false;
            this.exitSent = false;
            this.lastButtonPressTime = 0L;
            this.dragging = false;
        }
        
        void clearButton() {
            this.lastButtonPressTime = 0L;
        }
        
        @Override
        public String toString() {
            return "PState0[inside " + this.insideSurface + ", exitSent " + this.exitSent + ", lastPress " + this.lastButtonPressTime + ", dragging " + this.dragging + "]";
        }
    }
    
    private static class PointerState1 extends PointerState0
    {
        short buttonPressed;
        int buttonPressedMask;
        short lastButtonClickCount;
        final Point[] movePositions;
        
        private PointerState1() {
            this.buttonPressed = 0;
            this.buttonPressedMask = 0;
            this.lastButtonClickCount = 0;
            this.movePositions = new Point[] { new Point(), new Point(), new Point(), new Point(), new Point(), new Point(), new Point(), new Point() };
        }
        
        @Override
        final void clearButton() {
            super.clearButton();
            this.lastButtonClickCount = 0;
            if (!this.dragging || 0 == this.buttonPressedMask) {
                this.buttonPressed = 0;
                this.buttonPressedMask = 0;
                this.dragging = false;
            }
        }
        
        final Point getMovePosition(final int n) {
            if (0 <= n && n < this.movePositions.length) {
                return this.movePositions[n];
            }
            return null;
        }
        
        @Override
        public final String toString() {
            return "PState1[inside " + this.insideSurface + ", exitSent " + this.exitSent + ", lastPress " + this.lastButtonPressTime + ", pressed [button " + this.buttonPressed + ", mask " + this.buttonPressedMask + ", dragging " + this.dragging + ", clickCount " + this.lastButtonClickCount + "]";
        }
    }
    
    private class VisibleAction implements Runnable
    {
        boolean visible;
        
        private VisibleAction(final boolean visible) {
            this.visible = visible;
        }
        
        @Override
        public final void run() {
            WindowImpl.this.setVisibleActionImpl(this.visible);
        }
    }
    
    private class SetSizeAction implements Runnable
    {
        int width;
        int height;
        boolean force;
        
        private SetSizeAction(final int width, final int height, final boolean force) {
            this.width = width;
            this.height = height;
            this.force = force;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (this.force || (!WindowImpl.this.isFullscreen() && (WindowImpl.this.getWidth() != this.width || WindowImpl.this.getHeight() != this.height))) {
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window setSize: START force " + this.force + ", " + WindowImpl.this.getWidth() + "x" + WindowImpl.this.getHeight() + " -> " + this.width + "x" + this.height + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + ", state " + WindowImpl.this.getStateMaskString());
                    }
                    final boolean value = WindowImpl.this.stateMask.get(0);
                    int n;
                    if (value && WindowImpl.this.isNativeValid() && (0 >= this.width || 0 >= this.height)) {
                        n = 1;
                        WindowImpl.this.defineSize(0, 0);
                    }
                    else if (value && !WindowImpl.this.isNativeValid() && 0 < this.width && 0 < this.height) {
                        n = 2;
                        WindowImpl.this.defineSize(this.width, this.height);
                    }
                    else if (value && WindowImpl.this.isNativeValid()) {
                        n = 0;
                        WindowImpl.this.reconfigureWindowImpl(WindowImpl.this.getX(), WindowImpl.this.getY(), this.width, this.height, WindowImpl.this.getReconfigureMask(0, WindowImpl.this.isVisible()));
                        WindowImpl.this.waitForSize(this.width, this.height, false, 1000L);
                    }
                    else {
                        n = 0;
                        WindowImpl.this.defineSize(this.width, this.height);
                    }
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window setSize: END " + WindowImpl.this.getWidth() + "x" + WindowImpl.this.getHeight() + ", visibleAction " + n);
                    }
                    switch (n) {
                        case 1: {
                            WindowImpl.this.setVisibleActionImpl(false);
                            break;
                        }
                        case 2: {
                            WindowImpl.this.setVisibleActionImpl(true);
                            break;
                        }
                    }
                }
            }
            finally {
                access$400.unlock();
            }
        }
    }
    
    private class ReparentAction implements Runnable
    {
        final NativeWindow newParentWindow;
        final int topLevelX;
        final int topLevelY;
        final int hints;
        ReparentOperation operation;
        
        private ReparentAction(final NativeWindow newParentWindow, final int topLevelX, final int topLevelY, int hints) {
            this.newParentWindow = newParentWindow;
            this.topLevelX = topLevelX;
            this.topLevelY = topLevelY;
            if (WindowImpl.DEBUG_TEST_REPARENT_INCOMPATIBLE) {
                hints |= 0x1;
            }
            this.hints = hints;
            this.operation = ReparentOperation.ACTION_INVALID;
        }
        
        private ReparentOperation getOp() {
            return this.operation;
        }
        
        @Override
        public final void run() {
            if (WindowImpl.this.isFullscreen()) {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.reparent: NOP (in fullscreen, " + WindowImpl.getThreadName() + ") valid " + WindowImpl.this.isNativeValid() + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + ", state " + WindowImpl.this.getStateMaskString());
                }
                return;
            }
            boolean pauseRenderingAction = false;
            if (null != WindowImpl.this.lifecycleHook) {
                pauseRenderingAction = WindowImpl.this.lifecycleHook.pauseRenderingAction();
            }
            this.reparent();
            if (pauseRenderingAction) {
                WindowImpl.this.lifecycleHook.resumeRenderingAction();
            }
        }
        
        private void reparent() {
            final int x = WindowImpl.this.getX();
            final int y = WindowImpl.this.getY();
            final int width = WindowImpl.this.getWidth();
            final int height = WindowImpl.this.getHeight();
            int width2 = width;
            int height2 = height;
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            boolean visible;
            try {
                boolean b = 0x0 != (0x1 & this.hints);
                if (WindowImpl.this.isNativeValid()) {
                    b |= WindowImpl.isOffscreenInstance(WindowImpl.this, this.newParentWindow);
                }
                final boolean b2 = b;
                visible = WindowImpl.this.isVisible();
                final boolean b3 = visible || 0x0 != (0x2 & this.hints);
                Window window = null;
                if (this.newParentWindow instanceof Window) {
                    window = (Window)this.newParentWindow;
                }
                long access$401 = 0L;
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.reparent: START (" + WindowImpl.getThreadName() + ") valid " + WindowImpl.this.isNativeValid() + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + ", state " + WindowImpl.this.getStateMaskString() + " -> visible " + b3 + ", forceDestroyCreate " + b2 + ", DEBUG_TEST_REPARENT_INCOMPATIBLE " + WindowImpl.DEBUG_TEST_REPARENT_INCOMPATIBLE + ", HINT_FORCE_RECREATION " + (0x0 != (0x1 & this.hints)) + ", HINT_BECOMES_VISIBLE " + (0x0 != (0x2 & this.hints)) + ", old parentWindow: " + Display.hashCodeNullSafe(WindowImpl.this.parentWindow) + ", new parentWindow: " + Display.hashCodeNullSafe(this.newParentWindow));
                }
                int n;
                int n2;
                if (null != this.newParentWindow) {
                    n = 0;
                    n2 = 0;
                    if (width2 > this.newParentWindow.getWidth()) {
                        width2 = this.newParentWindow.getWidth();
                    }
                    if (height2 > this.newParentWindow.getHeight()) {
                        height2 = this.newParentWindow.getHeight();
                    }
                    access$401 = getNativeWindowHandle(this.newParentWindow);
                    if (0L == access$401) {
                        if (null == window) {
                            throw new NativeWindowException("Reparenting with non NEWT Window type only available after it's realized: " + this.newParentWindow);
                        }
                        WindowImpl.this.destroy(b3);
                        WindowImpl.this.setScreen((ScreenImpl)window.getScreen());
                        this.operation = ReparentOperation.ACTION_NATIVE_CREATION_PENDING;
                    }
                    else if (this.newParentWindow != WindowImpl.this.getParent()) {
                        if (!WindowImpl.this.isNativeValid()) {
                            if (null != window) {
                                WindowImpl.this.setScreen((ScreenImpl)window.getScreen());
                            }
                            else {
                                final Screen compatibleScreen = NewtFactory.createCompatibleScreen(this.newParentWindow, WindowImpl.this.screen);
                                if (WindowImpl.this.screen != compatibleScreen) {
                                    WindowImpl.this.setScreen((ScreenImpl)compatibleScreen);
                                }
                            }
                            if (0 < width2 && 0 < height2) {
                                this.operation = ReparentOperation.ACTION_NATIVE_CREATION;
                            }
                            else {
                                this.operation = ReparentOperation.ACTION_NATIVE_CREATION_PENDING;
                            }
                        }
                        else if (b2 || !NewtFactory.isScreenCompatible(this.newParentWindow, WindowImpl.this.screen)) {
                            WindowImpl.this.destroy(b3);
                            if (null != window) {
                                WindowImpl.this.setScreen((ScreenImpl)window.getScreen());
                            }
                            else {
                                WindowImpl.this.setScreen((ScreenImpl)NewtFactory.createCompatibleScreen(this.newParentWindow, WindowImpl.this.screen));
                            }
                            this.operation = ReparentOperation.ACTION_NATIVE_CREATION;
                        }
                        else {
                            this.operation = ReparentOperation.ACTION_NATIVE_REPARENTING;
                        }
                    }
                    else {
                        this.operation = ReparentOperation.ACTION_NOP;
                    }
                }
                else {
                    if (0 <= this.topLevelX && 0 <= this.topLevelY) {
                        n = this.topLevelX;
                        n2 = this.topLevelY;
                    }
                    else if (null != WindowImpl.this.parentWindow) {
                        final Point locationOnScreen = WindowImpl.this.getLocationOnScreen(null);
                        n = locationOnScreen.getX();
                        n2 = locationOnScreen.getY();
                    }
                    else {
                        n = x;
                        n2 = y;
                    }
                    if (0L == WindowImpl.this.parentWindowHandle) {
                        this.operation = ReparentOperation.ACTION_NOP;
                    }
                    else if (!WindowImpl.this.isNativeValid() || b2) {
                        WindowImpl.this.destroy(b3);
                        if (0 < width2 && 0 < height2) {
                            this.operation = ReparentOperation.ACTION_NATIVE_CREATION;
                        }
                        else {
                            this.operation = ReparentOperation.ACTION_NATIVE_CREATION_PENDING;
                        }
                    }
                    else {
                        this.operation = ReparentOperation.ACTION_NATIVE_REPARENTING;
                    }
                }
                WindowImpl.this.parentWindowHandle = access$401;
                if (ReparentOperation.ACTION_INVALID == this.operation) {
                    throw new NativeWindowException("Internal Error: reparentAction not set");
                }
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.reparent: ACTION (" + WindowImpl.getThreadName() + ") windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " new parentWindowHandle " + WindowImpl.toHexString(access$401) + ", reparentAction " + this.operation + ", pos/size " + n + "/" + n2 + " " + width2 + "x" + height2 + ", visible " + visible);
                }
                if (ReparentOperation.ACTION_NOP == this.operation) {
                    return;
                }
                if (null == this.newParentWindow) {
                    WindowImpl.this.setOffscreenPointerIcon(null);
                    WindowImpl.this.setOffscreenPointerVisible(true, null);
                }
                if (null != WindowImpl.this.parentWindow && WindowImpl.this.parentWindow instanceof Window) {
                    ((Window)WindowImpl.this.parentWindow).removeChild(WindowImpl.this);
                }
                WindowImpl.this.parentWindow = this.newParentWindow;
                WindowImpl.this.stateMask.put(2, null != WindowImpl.this.parentWindow);
                if (WindowImpl.this.parentWindow instanceof Window) {
                    ((Window)WindowImpl.this.parentWindow).addChild(WindowImpl.this);
                }
                if (ReparentOperation.ACTION_NATIVE_REPARENTING == this.operation) {
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    if (null != WindowImpl.this.parentWindow && visible && NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
                        WindowImpl.this.setVisibleImpl(false, true, x, y, width, height);
                        WindowImpl.this.waitForVisible(false, false);
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException ex) {}
                        displayImpl.dispatchMessagesNative();
                    }
                    NativeWindow access$402;
                    if (null != WindowImpl.this.parentWindow) {
                        access$402 = WindowImpl.this.parentWindow;
                        if (1 >= access$402.lockSurface()) {
                            throw new NativeWindowException("Parent surface lock: not ready: " + access$402);
                        }
                        WindowImpl.this.parentWindowHandle = access$402.getWindowHandle();
                    }
                    else {
                        access$402 = null;
                    }
                    int n3 = 0;
                    try {
                        n3 = (WindowImpl.this.reconfigureWindowImpl(n, n2, width2, height2, WindowImpl.this.getReconfigureMask(805306368, WindowImpl.this.isVisible())) ? 1 : 0);
                    }
                    finally {
                        if (null != access$402) {
                            access$402.unlockSurface();
                        }
                    }
                    WindowImpl.this.definePosition(n, n2);
                    if (n3 != 0) {
                        displayImpl.dispatchMessagesNative();
                        if (visible) {
                            WindowImpl.this.setVisibleImpl(true, true, n, n2, width2, height2);
                            n3 = ((0L <= WindowImpl.this.waitForVisible(true, false)) ? 1 : 0);
                            if (n3 != 0) {
                                if (WindowImpl.this.isAlwaysOnTop() && 0L == WindowImpl.this.parentWindowHandle && NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
                                    WindowImpl.this.reconfigureWindowImpl(n, n2, width2, height2, WindowImpl.this.getReconfigureMask(134217728, WindowImpl.this.isVisible()));
                                }
                                n3 = (WindowImpl.this.waitForSize(width2, height2, false, 1000L) ? 1 : 0);
                            }
                            if (n3 != 0) {
                                if (0L == WindowImpl.this.parentWindowHandle) {
                                    WindowImpl.this.waitForPosition(true, n, n2, 1000L);
                                }
                                WindowImpl.this.requestFocusInt(0L == WindowImpl.this.parentWindowHandle);
                                displayImpl.dispatchMessagesNative();
                            }
                        }
                    }
                    if (n3 == 0 || !visible) {
                        WindowImpl.this.definePosition(n, n2);
                        WindowImpl.this.defineSize(width2, height2);
                    }
                    if (n3 == 0) {
                        if (Window.DEBUG_IMPLEMENTATION) {
                            System.err.println("Window.reparent: native reparenting failed (" + WindowImpl.getThreadName() + ") windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + " parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + " -> " + WindowImpl.toHexString(access$401) + " - Trying recreation");
                        }
                        WindowImpl.this.destroy(b3);
                        this.operation = ReparentOperation.ACTION_NATIVE_CREATION;
                    }
                    else if (null != WindowImpl.this.parentWindow) {
                        WindowImpl.this.setOffscreenPointerIcon(WindowImpl.this.pointerIcon);
                        WindowImpl.this.setOffscreenPointerVisible(WindowImpl.this.stateMask.get(12), WindowImpl.this.pointerIcon);
                    }
                }
                else {
                    WindowImpl.this.definePosition(n, n2);
                    WindowImpl.this.defineSize(width2, height2);
                }
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.reparent: END-1 (" + WindowImpl.getThreadName() + ") state " + WindowImpl.this.getStateMaskString() + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + ", parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + ", parentWindow " + Display.hashCodeNullSafe(WindowImpl.this.parentWindow) + " " + WindowImpl.this.getX() + "/" + WindowImpl.this.getY() + " " + WindowImpl.this.getWidth() + "x" + WindowImpl.this.getHeight());
                }
            }
            finally {
                if (null != WindowImpl.this.lifecycleHook) {
                    WindowImpl.this.lifecycleHook.resetCounter();
                }
                access$400.unlock();
            }
            if (visible) {
                switch (this.operation) {
                    case ACTION_NATIVE_REPARENTING: {
                        WindowImpl.this.sendWindowEvent(100);
                        break;
                    }
                    case ACTION_NATIVE_CREATION: {
                        WindowImpl.this.runOnEDTIfAvail(true, WindowImpl.this.reparentActionRecreate);
                        break;
                    }
                }
            }
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.reparent: END-X (" + WindowImpl.getThreadName() + ") state " + WindowImpl.this.getStateMaskString() + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle) + ", parentWindowHandle " + WindowImpl.toHexString(WindowImpl.this.parentWindowHandle) + ", parentWindow " + Display.hashCodeNullSafe(WindowImpl.this.parentWindow) + " " + WindowImpl.this.getX() + "/" + WindowImpl.this.getY() + " " + WindowImpl.this.getWidth() + "x" + WindowImpl.this.getHeight());
            }
        }
    }
    
    private class DecorationAction implements Runnable
    {
        boolean undecorated;
        
        private DecorationAction(final boolean undecorated) {
            this.undecorated = undecorated;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (WindowImpl.this.stateMask.put(4, this.undecorated) != this.undecorated && WindowImpl.this.isNativeValid() && !WindowImpl.this.isFullscreen()) {
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(268435456, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class AlwaysOnTopAction implements Runnable
    {
        boolean alwaysOnTop;
        
        private AlwaysOnTopAction(final boolean alwaysOnTop) {
            this.alwaysOnTop = alwaysOnTop;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (WindowImpl.this.stateMask.put(5, this.alwaysOnTop) != this.alwaysOnTop && WindowImpl.this.isNativeValid() && !WindowImpl.this.isFullscreen()) {
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(134217728, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class AlwaysOnBottomAction implements Runnable
    {
        boolean alwaysOnBottom;
        
        private AlwaysOnBottomAction(final boolean alwaysOnBottom) {
            this.alwaysOnBottom = alwaysOnBottom;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (WindowImpl.this.stateMask.put(6, this.alwaysOnBottom) != this.alwaysOnBottom && WindowImpl.this.isNativeValid()) {
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(67108864, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class ResizableAction implements Runnable
    {
        boolean resizable;
        
        private ResizableAction(final boolean resizable) {
            this.resizable = resizable;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (WindowImpl.this.stateMask.put(8, this.resizable) != this.resizable && WindowImpl.this.isNativeValid()) {
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(16777216, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class StickyAction implements Runnable
    {
        boolean sticky;
        
        private StickyAction(final boolean sticky) {
            this.sticky = sticky;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (WindowImpl.this.stateMask.put(7, this.sticky) != this.sticky && WindowImpl.this.isNativeValid()) {
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(33554432, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class MaximizeAction implements Runnable
    {
        boolean horz;
        boolean vert;
        
        private MaximizeAction(final boolean horz, final boolean vert) {
            this.horz = horz;
            this.vert = vert;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                int n = 0;
                if (WindowImpl.this.stateMask.put(9, this.vert) != this.vert) {
                    n |= 0x800000;
                }
                if (WindowImpl.this.stateMask.put(10, this.horz) != this.horz) {
                    n |= 0x400000;
                }
                if (n && WindowImpl.this.isNativeValid()) {
                    final boolean hasFocus = WindowImpl.this.hasFocus();
                    final int x = WindowImpl.this.getX();
                    final int y = WindowImpl.this.getY();
                    final int width = WindowImpl.this.getWidth();
                    final int height = WindowImpl.this.getHeight();
                    final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                    displayImpl.dispatchMessagesNative();
                    WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(n, WindowImpl.this.isVisible()));
                    displayImpl.dispatchMessagesNative();
                    if (hasFocus) {
                        WindowImpl.this.requestFocusInt(0L == WindowImpl.this.parentWindowHandle);
                    }
                }
            }
            finally {
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class SetPositionAction implements Runnable
    {
        int x;
        int y;
        
        private SetPositionAction(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            try {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window setPosition: " + WindowImpl.this.getX() + "/" + WindowImpl.this.getY() + " -> " + this.x + "/" + this.y + ", fs " + WindowImpl.this.stateMask.get(11) + ", windowHandle " + WindowImpl.toHexString(WindowImpl.this.windowHandle));
                }
                if (!WindowImpl.this.isFullscreen() && (WindowImpl.this.getX() != this.x || WindowImpl.this.getY() != this.y || null != WindowImpl.this.getParent())) {
                    if (WindowImpl.this.isNativeValid()) {
                        WindowImpl.this.reconfigureWindowImpl(this.x, this.y, WindowImpl.this.getWidth(), WindowImpl.this.getHeight(), WindowImpl.this.getReconfigureMask(0, WindowImpl.this.isVisible()));
                        if (null == WindowImpl.this.parentWindow) {
                            WindowImpl.this.waitForPosition(true, this.x, this.y, 1000L);
                        }
                    }
                    else {
                        WindowImpl.this.definePosition(this.x, this.y);
                    }
                }
            }
            finally {
                access$400.unlock();
            }
        }
    }
    
    private class FullScreenAction implements Runnable
    {
        boolean _fullscreen;
        
        private boolean init(final boolean fullscreen) {
            if (!WindowImpl.this.isNativeValid()) {
                WindowImpl.this.stateMask.put(11, fullscreen);
                return false;
            }
            if (!WindowImpl.this.isReconfigureMaskSupported(2048)) {
                return false;
            }
            this._fullscreen = fullscreen;
            return WindowImpl.this.isFullscreen() != fullscreen;
        }
        
        public boolean fsOn() {
            return this._fullscreen;
        }
        
        @Override
        public final void run() {
            final RecursiveLock access$400 = WindowImpl.this.windowLock;
            access$400.lock();
            WindowImpl.this.blockInsetsChange = true;
            try {
                final int x = WindowImpl.this.getX();
                final int y = WindowImpl.this.getY();
                final int width = WindowImpl.this.getWidth();
                final int height = WindowImpl.this.getHeight();
                final RectangleImmutable viewportInWindowUnits = WindowImpl.this.screen.getViewportInWindowUnits();
                Rectangle rectangle2;
                int x2;
                int y2;
                int n;
                int n2;
                boolean value;
                boolean b;
                if (this._fullscreen) {
                    if (null == WindowImpl.this.fullscreenMonitors) {
                        if (WindowImpl.this.stateMask.get(31)) {
                            WindowImpl.this.fullscreenMonitors = (List<MonitorDevice>)new ArrayList();
                            WindowImpl.this.fullscreenMonitors.add(WindowImpl.this.getMainMonitor());
                        }
                        else {
                            WindowImpl.this.fullscreenMonitors = WindowImpl.this.getScreen().getMonitorDevices();
                        }
                    }
                    final Rectangle rectangle = new Rectangle();
                    MonitorDevice.unionOfViewports(null, rectangle, WindowImpl.this.fullscreenMonitors);
                    rectangle2 = rectangle;
                    if (WindowImpl.this.isReconfigureMaskSupported(16384) && (WindowImpl.this.fullscreenMonitors.size() > 1 || viewportInWindowUnits.compareTo((RectangleImmutable)rectangle2) > 0)) {
                        WindowImpl.this.stateMask.set(14);
                    }
                    else {
                        WindowImpl.this.stateMask.clear(14);
                    }
                    WindowImpl.this.nfs_x = x;
                    WindowImpl.this.nfs_y = y;
                    WindowImpl.this.nfs_width = width;
                    WindowImpl.this.nfs_height = height;
                    WindowImpl.this.stateMaskNFS.put32(0, 32, WindowImpl.this.stateMask.get32(0, 32) & 0x720);
                    x2 = rectangle2.getX();
                    y2 = rectangle2.getY();
                    n = rectangle2.getWidth();
                    n2 = rectangle2.getHeight();
                    WindowImpl.this.stateMask.clear(5);
                    WindowImpl.this.stateMask.set(8);
                    value = WindowImpl.this.stateMaskNFS.get(5);
                    b = !WindowImpl.this.stateMaskNFS.get(8);
                }
                else {
                    WindowImpl.this.stateMask.set(31);
                    WindowImpl.this.fullscreenMonitors = null;
                    WindowImpl.this.stateMask.clear(14);
                    rectangle2 = null;
                    final int access$401 = WindowImpl.this.nfs_x;
                    final int access$402 = WindowImpl.this.nfs_y;
                    final int access$403 = WindowImpl.this.nfs_width;
                    final int access$404 = WindowImpl.this.nfs_height;
                    value = (WindowImpl.this.stateMaskNFS.get(5) != WindowImpl.this.stateMask.get(5));
                    b = (WindowImpl.this.stateMaskNFS.get(8) != WindowImpl.this.stateMask.get(8));
                    WindowImpl.this.stateMask.put32(0, 32, WindowImpl.this.stateMaskNFS.get32(0, 32) | (WindowImpl.this.stateMask.get32(0, 32) & 0xFFFFF8DF));
                    if (null != WindowImpl.this.parentWindow) {
                        x2 = 0;
                        y2 = 0;
                        if (access$403 > WindowImpl.this.parentWindow.getWidth()) {
                            n = WindowImpl.this.parentWindow.getWidth();
                        }
                        else {
                            n = access$403;
                        }
                        if (access$404 > WindowImpl.this.parentWindow.getHeight()) {
                            n2 = WindowImpl.this.parentWindow.getHeight();
                        }
                        else {
                            n2 = access$404;
                        }
                    }
                    else {
                        x2 = access$401;
                        y2 = access$402;
                        n = access$403;
                        n2 = access$404;
                    }
                }
                final DisplayImpl displayImpl = (DisplayImpl)WindowImpl.this.screen.getDisplay();
                displayImpl.dispatchMessagesNative();
                final boolean visible = WindowImpl.this.isVisible();
                final boolean b2 = !this._fullscreen && visible && NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true);
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window " + x2 + "/" + y2 + " " + n + "x" + n2 + ", virtl-screenSize: " + viewportInWindowUnits + " [wu], monitorsViewport " + rectangle2 + " [wu]" + ", wasVisible " + visible + ", tempInvisible " + b2 + ", hasParent " + (null != WindowImpl.this.parentWindow) + ", state " + WindowImpl.this.getStateMaskString() + " @ " + Thread.currentThread().getName());
                }
                if (b2) {
                    WindowImpl.this.setVisibleImpl(false, true, x, y, width, height);
                    WindowImpl.this.waitForVisible(false, false);
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException ex) {}
                    displayImpl.dispatchMessagesNative();
                }
                NativeWindow access$405;
                if (null != WindowImpl.this.parentWindow) {
                    access$405 = WindowImpl.this.parentWindow;
                    if (1 >= access$405.lockSurface()) {
                        throw new NativeWindowException("Parent surface lock: not ready: " + WindowImpl.this.parentWindow);
                    }
                }
                else {
                    access$405 = null;
                }
                int n4;
                try {
                    int n3 = 0;
                    if (value) {
                        n3 = 134217728;
                    }
                    if (b) {
                        n3 |= 0x1000000;
                    }
                    n4 = n3;
                    if (this._fullscreen && n4) {
                        WindowImpl.this.reconfigureWindowImpl(x, y, width, height, WindowImpl.this.getReconfigureMask(n4, WindowImpl.this.isVisible()));
                    }
                    WindowImpl.this.stateMask.put(11, this._fullscreen);
                    WindowImpl.this.reconfigureWindowImpl(x2, y2, n, n2, WindowImpl.this.getReconfigureMask(((null != access$405) ? 536870912 : 0) | 0x200000 | 0x10000000, WindowImpl.this.isVisible()));
                }
                finally {
                    if (null != access$405) {
                        access$405.unlockSurface();
                    }
                }
                displayImpl.dispatchMessagesNative();
                if (visible) {
                    if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException ex2) {}
                        displayImpl.dispatchMessagesNative();
                    }
                    WindowImpl.this.setVisibleImpl(true, true, x2, y2, n, n2);
                    boolean access$406 = 0L <= WindowImpl.this.waitForVisible(true, false);
                    if (access$406) {
                        access$406 = WindowImpl.this.waitForSize(n, n2, false, 1000L);
                    }
                    if (access$406 && !this._fullscreen && null == WindowImpl.this.parentWindow) {
                        WindowImpl.this.waitForPosition(true, x2, y2, 1000L);
                    }
                    if (access$406) {
                        if (!this._fullscreen && n4) {
                            WindowImpl.this.reconfigureWindowImpl(x2, y2, n, n2, WindowImpl.this.getReconfigureMask(n4, WindowImpl.this.isVisible()));
                        }
                        if (WindowImpl.this.isAlwaysOnBottom()) {
                            WindowImpl.this.reconfigureWindowImpl(x2, y2, n, n2, WindowImpl.this.getReconfigureMask(67108864, WindowImpl.this.isVisible()));
                        }
                        if (WindowImpl.this.isSticky()) {
                            WindowImpl.this.reconfigureWindowImpl(x2, y2, n, n2, WindowImpl.this.getReconfigureMask(33554432, WindowImpl.this.isVisible()));
                        }
                    }
                    if (access$406) {
                        WindowImpl.this.requestFocusInt(this._fullscreen);
                        displayImpl.dispatchMessagesNative();
                    }
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window fs done: ok " + access$406 + ", " + WindowImpl.this);
                    }
                }
            }
            finally {
                WindowImpl.this.blockInsetsChange = false;
                access$400.unlock();
            }
            WindowImpl.this.sendWindowEvent(100);
        }
    }
    
    private class MonitorModeListenerImpl implements MonitorModeListener
    {
        boolean animatorPaused;
        boolean hidden;
        boolean hadFocus;
        boolean fullscreenPaused;
        List<MonitorDevice> _fullscreenMonitors;
        boolean _fullscreenUseMainMonitor;
        
        private MonitorModeListenerImpl() {
            this.animatorPaused = false;
            this.hidden = false;
            this.hadFocus = false;
            this.fullscreenPaused = false;
            this._fullscreenMonitors = null;
            this._fullscreenUseMainMonitor = true;
        }
        
        @Override
        public void monitorModeChangeNotify(final MonitorEvent monitorEvent) {
            this.hadFocus = WindowImpl.this.hasFocus();
            final boolean value = WindowImpl.this.stateMask.get(11);
            final boolean b = NativeWindowFactory.TYPE_MACOSX == NativeWindowFactory.getNativeWindowType(true);
            final boolean b2 = value && WindowImpl.this.isReconfigureMaskSupported(16384);
            final boolean b3 = !b2 && !value && WindowImpl.this.isVisible() && b;
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.monitorModeChangeNotify: hadFocus " + this.hadFocus + ", qFSPause " + b2 + ", qHide " + b3 + ", " + monitorEvent + " @ " + Thread.currentThread().getName());
            }
            if (null != WindowImpl.this.lifecycleHook) {
                this.animatorPaused = WindowImpl.this.lifecycleHook.pauseRenderingAction();
            }
            if (b2) {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.monitorModeChangeNotify: FS Pause");
                }
                this.fullscreenPaused = true;
                this._fullscreenMonitors = WindowImpl.this.fullscreenMonitors;
                this._fullscreenUseMainMonitor = WindowImpl.this.stateMask.get(31);
                WindowImpl.this.setFullscreenImpl(false, true, null);
            }
            if (b3) {
                this.hidden = true;
                WindowImpl.this.setVisible(false);
            }
        }
        
        @Override
        public void monitorModeChanged(final MonitorEvent monitorEvent, final boolean b) {
            if (!this.animatorPaused && b && null != WindowImpl.this.lifecycleHook) {
                this.animatorPaused = WindowImpl.this.lifecycleHook.pauseRenderingAction();
            }
            final boolean value = WindowImpl.this.stateMask.get(11);
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.monitorModeChanged.0: success: " + b + ", hadFocus " + this.hadFocus + ", animPaused " + this.animatorPaused + ", hidden " + this.hidden + ", FS " + value + ", FS-paused " + this.fullscreenPaused + " @ " + Thread.currentThread().getName());
                System.err.println("Window.monitorModeChanged.0: " + WindowImpl.this.getScreen());
                System.err.println("Window.monitorModeChanged.0: " + monitorEvent);
            }
            WindowImpl.this.monitorModeChanged(monitorEvent, b);
            if (b && !value && !this.fullscreenPaused) {
                final RectangleImmutable viewportInWindowUnits = WindowImpl.this.screen.getViewportInWindowUnits();
                if (viewportInWindowUnits.getWidth() > 0 && viewportInWindowUnits.getHeight() > 0) {
                    final Rectangle rectangle = new Rectangle(WindowImpl.this.getX(), WindowImpl.this.getY(), WindowImpl.this.getWidth(), WindowImpl.this.getHeight());
                    final RectangleImmutable intersection = viewportInWindowUnits.intersection(rectangle);
                    if (WindowImpl.this.getHeight() > intersection.getHeight() || WindowImpl.this.getWidth() > intersection.getWidth()) {
                        if (Window.DEBUG_IMPLEMENTATION) {
                            System.err.println("Window.monitorModeChanged.1: Non-FS - Fit window " + rectangle + " into screen viewport " + viewportInWindowUnits + ", due to minimal intersection " + intersection);
                        }
                        WindowImpl.this.definePosition(viewportInWindowUnits.getX(), viewportInWindowUnits.getY());
                        WindowImpl.this.setSize(viewportInWindowUnits.getWidth(), viewportInWindowUnits.getHeight(), true);
                    }
                }
            }
            else if (this.fullscreenPaused) {
                if (Window.DEBUG_IMPLEMENTATION) {
                    System.err.println("Window.monitorModeChanged.2: FS Restore");
                }
                WindowImpl.this.setFullscreenImpl(true, this._fullscreenUseMainMonitor, this._fullscreenMonitors);
                this.fullscreenPaused = false;
                this._fullscreenMonitors = null;
                this._fullscreenUseMainMonitor = true;
            }
            else if (b && value && null != WindowImpl.this.fullscreenMonitors) {
                final MonitorDevice monitor = monitorEvent.getMonitor();
                if (WindowImpl.this.fullscreenMonitors.contains(monitor)) {
                    final Rectangle rectangle2 = new Rectangle();
                    MonitorDevice.unionOfViewports(null, rectangle2, WindowImpl.this.fullscreenMonitors);
                    if (Window.DEBUG_IMPLEMENTATION) {
                        System.err.println("Window.monitorModeChanged.3: FS Monitor Match: Fit window " + WindowImpl.this.getBounds() + " into new viewport union " + rectangle2 + " [window], provoked by " + monitor);
                    }
                    WindowImpl.this.definePosition(rectangle2.getX(), rectangle2.getY());
                    WindowImpl.this.setSize(rectangle2.getWidth(), rectangle2.getHeight(), true);
                }
            }
            if (this.hidden) {
                WindowImpl.this.setVisible(true);
                this.hidden = false;
            }
            WindowImpl.this.sendWindowEvent(100);
            if (this.animatorPaused) {
                WindowImpl.this.lifecycleHook.resumeRenderingAction();
            }
            if (this.hadFocus) {
                WindowImpl.this.requestFocus(true);
            }
            if (Window.DEBUG_IMPLEMENTATION) {
                System.err.println("Window.monitorModeChanged.X: @ " + Thread.currentThread().getName() + ", this: " + WindowImpl.this);
            }
        }
    }
    
    public interface LifecycleHook
    {
        void resetCounter();
        
        void setVisibleActionPost(final boolean p0, final boolean p1);
        
        void preserveGLStateAtDestroy(final boolean p0);
        
        void destroyActionPreLock();
        
        void destroyActionInLock();
        
        boolean pauseRenderingAction();
        
        void resumeRenderingAction();
        
        void shutdownRenderingAction();
    }
}
