// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.newt.event.NEWTEventConsumer;
import com.jogamp.newt.event.NEWTEvent;
import java.lang.ref.WeakReference;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.newt.NewtFactory;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.util.PixelFormatUtil;
import java.net.URLConnection;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.util.PNGPixelRect;
import java.io.IOException;
import com.jogamp.common.util.IOUtil;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.newt.util.EDTUtil;
import jogamp.newt.event.NEWTEventTask;
import java.util.ArrayList;
import com.jogamp.newt.Display;

public abstract class DisplayImpl extends Display
{
    private static int serialno;
    private static final boolean pngUtilAvail;
    final ArrayList<PointerIconImpl> pointerIconList;
    public static final String nilString = "nil";
    private final Object eventsLock;
    private ArrayList<NEWTEventTask> events;
    private volatile boolean haveEvents;
    protected final Runnable dispatchMessagesRunnable;
    protected volatile EDTUtil edtUtil;
    protected int id;
    protected String name;
    protected String type;
    protected String fqname;
    protected int hashCode;
    protected int refCount;
    protected boolean exclusive;
    protected AbstractGraphicsDevice aDevice;
    
    public DisplayImpl() {
        this.pointerIconList = new ArrayList<PointerIconImpl>();
        this.eventsLock = new Object();
        this.events = new ArrayList<NEWTEventTask>();
        this.haveEvents = false;
        this.dispatchMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                DisplayImpl.this.dispatchMessages();
            }
        };
        this.edtUtil = null;
    }
    
    public static final boolean isPNGUtilAvailable() {
        return DisplayImpl.pngUtilAvail;
    }
    
    private void destroyAllPointerIconFromList(final long n) {
        synchronized (this.pointerIconList) {
            for (int size = this.pointerIconList.size(), i = 0; i < size; ++i) {
                final PointerIconImpl pointerIconImpl = this.pointerIconList.get(i);
                if (DisplayImpl.DEBUG) {
                    System.err.println("destroyAllPointerIconFromList: dpy " + Display.toHexString(n) + ", # " + i + "/" + size + ": " + pointerIconImpl + " @ " + Display.getThreadName());
                }
                if (null != pointerIconImpl && pointerIconImpl.isValid()) {
                    pointerIconImpl.destroyOnEDT(n);
                }
            }
            this.pointerIconList.clear();
        }
    }
    
    @Override
    public PixelFormat getNativePointerIconPixelFormat() {
        return PixelFormat.BGRA8888;
    }
    
    @Override
    public boolean getNativePointerIconForceDirectNIO() {
        return false;
    }
    
    @Override
    public final PointerIcon createPointerIcon(final IOUtil.ClassResources classResources, final int n, final int n2) throws IllegalArgumentException, IllegalStateException, IOException {
        if (null == classResources || 0 >= classResources.resourceCount()) {
            throw new IllegalArgumentException("Null or invalid pngResource " + classResources);
        }
        if (!DisplayImpl.pngUtilAvail) {
            return null;
        }
        final PointerIconImpl[] array = { null };
        final Exception[] array2 = { null };
        final String string = "Could not resolve " + classResources.resourcePaths[0];
        this.runOnEDTIfAvail(true, new Runnable() {
            @Override
            public void run() {
                try {
                    if (!DisplayImpl.this.isNativeValidAsync()) {
                        throw new IllegalStateException("Display.createPointerIcon: Display invalid " + DisplayImpl.this);
                    }
                    final URLConnection resolve = classResources.resolve(0);
                    if (null == resolve) {
                        throw new IOException(string);
                    }
                    final PNGPixelRect read = PNGPixelRect.read(resolve.getInputStream(), DisplayImpl.this.getNativePointerIconPixelFormat(), DisplayImpl.this.getNativePointerIconForceDirectNIO(), 0, false);
                    final long pointerIconImplChecked = DisplayImpl.this.createPointerIconImplChecked(read.getPixelformat(), read.getSize().getWidth(), read.getSize().getHeight(), read.getPixels(), n, n2);
                    final Point point = new Point(n, n2);
                    if (DisplayImpl.DEBUG_POINTER_ICON) {
                        System.err.println("createPointerIconPNG.0: " + read + ", handle: " + Display.toHexString(pointerIconImplChecked) + ", hot " + point);
                    }
                    if (0L == pointerIconImplChecked) {
                        throw new IOException(string);
                    }
                    array[0] = new PointerIconImpl(DisplayImpl.this, read, point, pointerIconImplChecked);
                    if (DisplayImpl.DEBUG_POINTER_ICON) {
                        System.err.println("createPointerIconPNG.0: " + array[0]);
                    }
                }
                catch (Exception ex) {
                    array2[0] = ex;
                }
            }
        });
        if (null != array2[0]) {
            final Exception ex = array2[0];
            if (ex instanceof IllegalArgumentException) {
                throw new IllegalArgumentException(ex);
            }
            if (ex instanceof IllegalStateException) {
                throw new IllegalStateException(ex);
            }
            throw new IOException(ex);
        }
        else {
            if (null == array[0]) {
                throw new IOException(string);
            }
            synchronized (this.pointerIconList) {
                this.pointerIconList.add(array[0]);
            }
            return array[0];
        }
    }
    
    @Override
    public final PointerIcon createPointerIcon(final PixelRectangle pixelRectangle, final int n, final int n2) throws IllegalArgumentException, IllegalStateException {
        if (null == pixelRectangle) {
            throw new IllegalArgumentException("Null or pixelrect");
        }
        PixelRectangle convert;
        if (this.getNativePointerIconPixelFormat() != pixelRectangle.getPixelformat() || pixelRectangle.isGLOriented()) {
            convert = PixelFormatUtil.convert(pixelRectangle, this.getNativePointerIconPixelFormat(), 0, false, this.getNativePointerIconForceDirectNIO());
            if (DisplayImpl.DEBUG_POINTER_ICON) {
                System.err.println("createPointerIconRES.0: Conversion-FMT " + pixelRectangle + " -> " + convert);
            }
        }
        else if (this.getNativePointerIconForceDirectNIO() && !Buffers.isDirect(pixelRectangle.getPixels())) {
            final ByteBuffer pixels = pixelRectangle.getPixels();
            convert = new PixelRectangle.GenericPixelRect(pixelRectangle.getPixelformat(), pixelRectangle.getSize(), pixelRectangle.getStride(), pixelRectangle.isGLOriented(), Buffers.newDirectByteBuffer(pixels.array(), pixels.arrayOffset()));
            if (DisplayImpl.DEBUG_POINTER_ICON) {
                System.err.println("createPointerIconRES.0: Conversion-NIO " + pixelRectangle + " -> " + convert);
            }
        }
        else {
            convert = pixelRectangle;
            if (DisplayImpl.DEBUG_POINTER_ICON) {
                System.err.println("createPointerIconRES.0: No conversion " + convert);
            }
        }
        final PointerIconImpl[] array = { null };
        this.runOnEDTIfAvail(true, new Runnable() {
            @Override
            public void run() {
                try {
                    if (!DisplayImpl.this.isNativeValidAsync()) {
                        throw new IllegalStateException("Display.createPointerIcon: Display invalid " + DisplayImpl.this);
                    }
                    if (null != convert) {
                        final long pointerIconImplChecked = DisplayImpl.this.createPointerIconImplChecked(convert.getPixelformat(), convert.getSize().getWidth(), convert.getSize().getHeight(), convert.getPixels(), n, n2);
                        if (0L != pointerIconImplChecked) {
                            array[0] = new PointerIconImpl(DisplayImpl.this, convert, new Point(n, n2), pointerIconImplChecked);
                        }
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        if (null != array[0]) {
            synchronized (this.pointerIconList) {
                this.pointerIconList.add(array[0]);
            }
        }
        return array[0];
    }
    
    protected final long createPointerIconImplChecked(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        if (this.getNativePointerIconPixelFormat() != pixelFormat) {
            throw new IllegalArgumentException("Pixelformat no " + this.getNativePointerIconPixelFormat() + ", but " + pixelFormat);
        }
        if (this.getNativePointerIconForceDirectNIO() && !Buffers.isDirect(byteBuffer)) {
            throw new IllegalArgumentException("pixel buffer is not direct " + byteBuffer);
        }
        return this.createPointerIconImpl(pixelFormat, n, n2, byteBuffer, n3, n4);
    }
    
    protected long createPointerIconImpl(final PixelFormat pixelFormat, final int n, final int n2, final ByteBuffer byteBuffer, final int n3, final int n4) {
        return 0L;
    }
    
    protected void destroyPointerIconImpl(final long n, final long n2) {
    }
    
    static void initSingleton() {
    }
    
    private static Class<?> getDisplayClass(final String s) throws ClassNotFoundException {
        final Class<?> customClass = NewtFactory.getCustomClass(s, "DisplayDriver");
        if (null == customClass) {
            throw new ClassNotFoundException("Failed to find NEWT Display Class <" + s + ".DisplayDriver>");
        }
        return customClass;
    }
    
    public static Display create(final String type, String validateDisplayName, final long n, final boolean b) {
        try {
            final DisplayImpl displayImpl = (DisplayImpl)getDisplayClass(type).newInstance();
            validateDisplayName = displayImpl.validateDisplayName(validateDisplayName, n);
            synchronized (DisplayImpl.displayList) {
                if (b) {
                    final Display lastDisplay = Display.getLastDisplayOf(type, validateDisplayName, -1, true);
                    if (null != lastDisplay) {
                        if (DisplayImpl.DEBUG) {
                            System.err.println("Display.create() REUSE: " + lastDisplay + " " + Display.getThreadName());
                        }
                        return lastDisplay;
                    }
                }
                displayImpl.exclusive = !b;
                displayImpl.name = validateDisplayName;
                displayImpl.type = type;
                displayImpl.refCount = 0;
                displayImpl.id = DisplayImpl.serialno++;
                displayImpl.fqname = getFQName(displayImpl.type, displayImpl.name, displayImpl.id);
                displayImpl.hashCode = displayImpl.fqname.hashCode();
                displayImpl.setEDTUtil(displayImpl.edtUtil);
                Display.addDisplay2List(displayImpl);
            }
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.create() NEW: " + displayImpl + " " + Display.getThreadName());
            }
            return displayImpl;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final DisplayImpl displayImpl = (DisplayImpl)o;
        if (this.id != displayImpl.id) {
            return false;
        }
        Label_0070: {
            if (this.name == null) {
                if (displayImpl.name == null) {
                    break Label_0070;
                }
            }
            else if (this.name.equals(displayImpl.name)) {
                break Label_0070;
            }
            return false;
        }
        if (this.type == null) {
            if (displayImpl.type == null) {
                return true;
            }
        }
        else if (this.type.equals(displayImpl.type)) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public final synchronized void createNative() throws NativeWindowException {
        if (null == this.aDevice) {
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.createNative() START (" + Display.getThreadName() + ", " + this + ")");
            }
            try {
                this.runOnEDTIfAvail(true, new Runnable() {
                    @Override
                    public void run() {
                        DisplayImpl.this.createNativeImpl();
                    }
                });
            }
            catch (Throwable t) {
                throw new NativeWindowException(t);
            }
            if (null == this.aDevice) {
                throw new NativeWindowException("Display.createNative() failed to instanciate an AbstractGraphicsDevice");
            }
            synchronized (DisplayImpl.displayList) {
                ++DisplayImpl.displaysActive;
                if (DisplayImpl.DEBUG) {
                    System.err.println("Display.createNative() END (" + Display.getThreadName() + ", " + this + ", active " + DisplayImpl.displaysActive + ")");
                }
            }
        }
    }
    
    protected EDTUtil createEDTUtil() {
        DefaultEDTUtil defaultEDTUtil;
        if (NewtFactory.useEDT()) {
            defaultEDTUtil = new DefaultEDTUtil(Thread.currentThread().getThreadGroup(), "Display-" + this.getFQName(), this.dispatchMessagesRunnable);
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.createEDTUtil(" + this.getFQName() + "): " + defaultEDTUtil.getClass().getName());
            }
        }
        else {
            defaultEDTUtil = null;
        }
        return defaultEDTUtil;
    }
    
    @Override
    public synchronized EDTUtil setEDTUtil(final EDTUtil edtUtil) {
        final EDTUtil edtUtil2 = this.edtUtil;
        if (null != edtUtil && edtUtil == edtUtil2) {
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.setEDTUtil: " + edtUtil + " - keep!");
            }
            return edtUtil2;
        }
        if (DisplayImpl.DEBUG) {
            System.err.println("Display.setEDTUtil(" + ((null == edtUtil) ? "default" : "custom") + "): " + edtUtil2 + " -> " + edtUtil);
        }
        stopEDT(edtUtil2, null);
        this.edtUtil = ((null == edtUtil) ? this.createEDTUtil() : edtUtil);
        return edtUtil2;
    }
    
    @Override
    public final EDTUtil getEDTUtil() {
        return this.edtUtil;
    }
    
    private static void stopEDT(final EDTUtil edtUtil, final Runnable runnable) {
        if (null != edtUtil) {
            if (edtUtil.isRunning()) {
                final boolean invokeStop = edtUtil.invokeStop(true, runnable);
                if (DisplayImpl.DEBUG && !invokeStop) {
                    System.err.println("Warning: invokeStop() failed");
                    ExceptionUtils.dumpStack(System.err);
                }
            }
            edtUtil.waitUntilStopped();
        }
        else if (null != runnable) {
            runnable.run();
        }
    }
    
    public void runOnEDTIfAvail(final boolean b, final Runnable runnable) {
        final EDTUtil edtUtil = this.edtUtil;
        if (!edtUtil.isRunning()) {
            synchronized (this) {
                if (!edtUtil.isRunning()) {
                    if (DisplayImpl.DEBUG) {
                        System.err.println("Info: EDT start " + Thread.currentThread().getName() + ", " + this);
                        ExceptionUtils.dumpStack(System.err);
                    }
                    edtUtil.start();
                }
            }
        }
        if (!edtUtil.isCurrentThreadEDT()) {
            if (edtUtil.invoke(b, runnable)) {
                return;
            }
            if (DisplayImpl.DEBUG) {
                System.err.println("Warning: invoke(wait " + b + ", ..) on EDT failed .. invoke on current thread " + Thread.currentThread().getName());
                ExceptionUtils.dumpStack(System.err);
            }
        }
        runnable.run();
    }
    
    @Override
    public boolean validateEDTStopped() {
        if (0 == this.refCount && null == this.aDevice) {
            final EDTUtil edtUtil = this.edtUtil;
            if (null != edtUtil && edtUtil.isRunning()) {
                synchronized (this) {
                    if (null != this.edtUtil && this.edtUtil.isRunning()) {
                        stopEDT(this.edtUtil, null);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public final synchronized void destroy() {
        if (DisplayImpl.DEBUG) {
            Display.dumpDisplayList("Display.destroy(" + this.getFQName() + ") BEGIN");
        }
        synchronized (DisplayImpl.displayList) {
            if (0 < DisplayImpl.displaysActive) {
                --DisplayImpl.displaysActive;
            }
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.destroy(): " + this + ", active " + DisplayImpl.displaysActive + " " + Display.getThreadName());
            }
        }
        final AbstractGraphicsDevice aDevice = this.aDevice;
        this.aDevice = null;
        this.refCount = 0;
        stopEDT(this.edtUtil, new Runnable() {
            @Override
            public void run() {
                if (null != aDevice) {
                    DisplayImpl.this.destroyAllPointerIconFromList(aDevice.getHandle());
                    DisplayImpl.this.closeNativeImpl(aDevice);
                }
            }
        });
        if (DisplayImpl.DEBUG) {
            Display.dumpDisplayList("Display.destroy(" + this.getFQName() + ") END");
        }
    }
    
    static final void shutdownAll() {
        final int size = DisplayImpl.displayList.size();
        if (DisplayImpl.DEBUG) {
            Display.dumpDisplayList("Display.shutdownAll " + size + " instances, on thread " + Display.getThreadName());
        }
        for (int n = 0; n < size && DisplayImpl.displayList.size() > 0; ++n) {
            final DisplayImpl displayImpl = DisplayImpl.displayList.remove(0).get();
            if (DisplayImpl.DEBUG) {
                System.err.println("Display.shutdownAll[" + (n + 1) + "/" + size + "]: " + displayImpl + ", GCed " + (null == displayImpl));
            }
            if (null != displayImpl) {
                if (0 < DisplayImpl.displaysActive) {
                    --DisplayImpl.displaysActive;
                }
                final EDTUtil edtUtil = displayImpl.getEDTUtil();
                final AbstractGraphicsDevice aDevice = displayImpl.aDevice;
                displayImpl.aDevice = null;
                displayImpl.refCount = 0;
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (null != displayImpl.getGraphicsDevice()) {
                            displayImpl.destroyAllPointerIconFromList(aDevice.getHandle());
                            displayImpl.closeNativeImpl(aDevice);
                        }
                    }
                };
                if (null != edtUtil) {
                    final long n2 = edtUtil.getPollPeriod() * 2L;
                    if (edtUtil.isRunning()) {
                        edtUtil.invokeStop(false, runnable);
                    }
                    try {
                        Thread.sleep((n2 < 50L) ? n2 : 50L);
                    }
                    catch (InterruptedException ex) {}
                }
                else {
                    runnable.run();
                }
            }
        }
    }
    
    @Override
    public final synchronized int addReference() {
        if (DisplayImpl.DEBUG) {
            System.err.println("Display.addReference() (" + Display.getThreadName() + "): " + this.refCount + " -> " + (this.refCount + 1));
        }
        if (0 == this.refCount) {
            this.createNative();
        }
        if (null == this.aDevice) {
            throw new NativeWindowException("Display.addReference() (refCount " + this.refCount + ") null AbstractGraphicsDevice");
        }
        return this.refCount++;
    }
    
    @Override
    public final synchronized int removeReference() {
        if (DisplayImpl.DEBUG) {
            System.err.println("Display.removeReference() (" + Display.getThreadName() + "): " + this.refCount + " -> " + (this.refCount - 1));
        }
        --this.refCount;
        if (0 >= this.refCount) {
            this.destroy();
            this.refCount = 0;
        }
        return this.refCount;
    }
    
    @Override
    public final synchronized int getReferenceCount() {
        return this.refCount;
    }
    
    protected abstract void createNativeImpl();
    
    protected abstract void closeNativeImpl(final AbstractGraphicsDevice p0);
    
    @Override
    public final int getId() {
        return this.id;
    }
    
    @Override
    public final String getType() {
        return this.type;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    @Override
    public final String getFQName() {
        return this.fqname;
    }
    
    @Override
    public final boolean isExclusive() {
        return this.exclusive;
    }
    
    public String validateDisplayName(String string, final long n) {
        if (null == string && 0L != n) {
            string = "wrapping-" + Display.toHexString(n);
        }
        return (null == string) ? "nil" : string;
    }
    
    private static String getFQName(String s, String s2, final int n) {
        if (null == s) {
            s = "nil";
        }
        if (null == s2) {
            s2 = "nil";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append("_");
        sb.append(s2);
        sb.append("-");
        sb.append(n);
        return sb.toString();
    }
    
    @Override
    public final long getHandle() {
        if (null != this.aDevice) {
            return this.aDevice.getHandle();
        }
        return 0L;
    }
    
    @Override
    public final AbstractGraphicsDevice getGraphicsDevice() {
        return this.aDevice;
    }
    
    @Override
    public final synchronized boolean isNativeValid() {
        return null != this.aDevice;
    }
    
    protected final boolean isNativeValidAsync() {
        return null != this.aDevice;
    }
    
    @Override
    public boolean isEDTRunning() {
        final EDTUtil edtUtil = this.edtUtil;
        return null != edtUtil && edtUtil.isRunning();
    }
    
    @Override
    public String toString() {
        final EDTUtil edtUtil = this.edtUtil;
        return "NEWT-Display[" + this.getFQName() + ", excl " + this.exclusive + ", refCount " + this.refCount + ", hasEDT " + (null != edtUtil) + ", edtRunning " + (null != edtUtil && edtUtil.isRunning()) + ", " + this.aDevice + "]";
    }
    
    protected abstract void dispatchMessagesNative();
    
    final void dispatchMessage(final NEWTEvent newtEvent) {
        try {
            final Object source = newtEvent.getSource();
            if (!(source instanceof NEWTEventConsumer)) {
                throw new RuntimeException("Event source not NEWT: " + ((NEWTEventConsumer)source).getClass().getName() + ", " + source);
            }
            if (!((NEWTEventConsumer)source).consumeEvent(newtEvent)) {
                this.enqueueEvent(false, newtEvent);
            }
        }
        catch (Throwable t) {
            RuntimeException ex;
            if (t instanceof RuntimeException) {
                ex = (RuntimeException)t;
            }
            else {
                ex = new RuntimeException(t);
            }
            throw ex;
        }
    }
    
    final void dispatchMessage(final NEWTEventTask newtEventTask) {
        final NEWTEvent value = newtEventTask.get();
        try {
            if (null == value) {
                System.err.println("Warning: event of eventTask is NULL");
                ExceptionUtils.dumpStack(System.err);
                return;
            }
            this.dispatchMessage(value);
        }
        catch (RuntimeException exception) {
            if (!newtEventTask.isCallerWaiting()) {
                throw exception;
            }
            newtEventTask.setException(exception);
        }
        finally {
            newtEventTask.notifyCaller();
        }
    }
    
    @Override
    public void dispatchMessages() {
        if (0 == this.refCount || null == this.getGraphicsDevice()) {
            return;
        }
        ArrayList<NEWTEventTask> events = null;
        if (this.haveEvents) {
            synchronized (this.eventsLock) {
                if (this.haveEvents) {
                    events = this.events;
                    this.events = new ArrayList<NEWTEventTask>();
                    this.haveEvents = false;
                }
                this.eventsLock.notifyAll();
            }
            if (null != events) {
                for (int i = 0; i < events.size(); ++i) {
                    final NEWTEventTask newtEventTask = events.get(i);
                    if (!newtEventTask.isDispatched()) {
                        this.dispatchMessage(newtEventTask);
                    }
                }
            }
        }
        this.dispatchMessagesNative();
    }
    
    public void enqueueEvent(final boolean b, final NEWTEvent newtEvent) {
        final EDTUtil edtUtil = this.edtUtil;
        if (!edtUtil.isRunning()) {
            if (DisplayImpl.DEBUG) {
                System.err.println("Warning: EDT already stopped: wait:=" + b + ", " + newtEvent);
                ExceptionUtils.dumpStack(System.err);
            }
            return;
        }
        if (b && edtUtil.isCurrentThreadEDTorNEDT()) {
            this.dispatchMessage(newtEvent);
            return;
        }
        final Object o = new Object();
        final NEWTEventTask newtEventTask = new NEWTEventTask(newtEvent, b ? o : null);
        synchronized (o) {
            synchronized (this.eventsLock) {
                this.events.add(newtEventTask);
                this.haveEvents = true;
                this.eventsLock.notifyAll();
            }
            while (b && !newtEventTask.isDispatched()) {
                try {
                    o.wait();
                }
                catch (InterruptedException ex) {
                    newtEventTask.setDispatched();
                    throw new InterruptedRuntimeException(ex);
                }
                if (null != newtEventTask.getException()) {
                    throw newtEventTask.getException();
                }
            }
        }
    }
    
    public static final <T> T runWithLockedDevice(final AbstractGraphicsDevice abstractGraphicsDevice, final DisplayRunnable<T> displayRunnable) {
        abstractGraphicsDevice.lock();
        T run;
        try {
            run = displayRunnable.run(abstractGraphicsDevice.getHandle());
        }
        finally {
            abstractGraphicsDevice.unlock();
        }
        return run;
    }
    
    public final <T> T runWithLockedDisplayDevice(final DisplayRunnable<T> displayRunnable) {
        final AbstractGraphicsDevice graphicsDevice = this.getGraphicsDevice();
        if (null == graphicsDevice) {
            throw new RuntimeException("null device - not initialized: " + this);
        }
        return runWithLockedDevice(graphicsDevice, displayRunnable);
    }
    
    static {
        DisplayImpl.serialno = 1;
        NativeWindowFactory.addCustomShutdownHook(true, new Runnable() {
            @Override
            public void run() {
                WindowImpl.shutdownAll();
                ScreenImpl.shutdownAll();
                DisplayImpl.shutdownAll();
            }
        });
        pngUtilAvail = ReflectionUtil.isClassAvailable("com.jogamp.opengl.util.PNGPixelRect", DisplayImpl.class.getClassLoader());
    }
    
    public interface DisplayRunnable<T>
    {
        T run(final long p0);
    }
}
