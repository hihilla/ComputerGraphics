// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import com.jogamp.nativewindow.util.PointImmutable;
import jogamp.newt.Debug;
import java.util.Collection;
import java.util.Iterator;
import com.jogamp.newt.util.EDTUtil;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.nativewindow.util.PixelRectangle;
import java.io.IOException;
import com.jogamp.common.util.IOUtil;
import com.jogamp.nativewindow.util.PixelFormat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class Display
{
    public static final boolean DEBUG;
    protected static final boolean DEBUG_POINTER_ICON;
    protected static final ArrayList<WeakReference<Display>> displayList;
    protected static int displaysActive;
    
    @Override
    public abstract int hashCode();
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof Display && ((Display)o).getFQName().equals(this.getFQName()));
    }
    
    public abstract PixelFormat getNativePointerIconPixelFormat();
    
    public abstract boolean getNativePointerIconForceDirectNIO();
    
    public abstract PointerIcon createPointerIcon(final IOUtil.ClassResources p0, final int p1, final int p2) throws IllegalArgumentException, IllegalStateException, IOException;
    
    public abstract PointerIcon createPointerIcon(final PixelRectangle p0, final int p1, final int p2) throws IllegalArgumentException, IllegalStateException;
    
    public abstract void createNative() throws NativeWindowException;
    
    public abstract void destroy();
    
    public abstract boolean validateEDTStopped();
    
    public abstract boolean isNativeValid();
    
    public abstract int getReferenceCount();
    
    public abstract int addReference() throws NativeWindowException;
    
    public abstract int removeReference();
    
    public abstract AbstractGraphicsDevice getGraphicsDevice();
    
    public abstract long getHandle();
    
    public abstract String getFQName();
    
    public abstract int getId();
    
    public abstract String getName();
    
    public abstract String getType();
    
    public abstract boolean isExclusive();
    
    public abstract EDTUtil setEDTUtil(final EDTUtil p0);
    
    public abstract EDTUtil getEDTUtil();
    
    public abstract boolean isEDTRunning();
    
    public abstract void dispatchMessages();
    
    public static void dumpDisplayList(final String s) {
        synchronized (Display.displayList) {
            System.err.println(s + " DisplayList[] entries: " + Display.displayList.size() + " - " + getThreadName());
            final Iterator<WeakReference<Display>> iterator = Display.displayList.iterator();
            int n = 0;
            while (iterator.hasNext()) {
                final Display display = iterator.next().get();
                System.err.println("  [" + n + "] : " + display + ", GC'ed " + (null == display));
                ++n;
            }
        }
    }
    
    public static Display getFirstDisplayOf(final String s, final String s2, final int n, final boolean b) {
        return getDisplayOfImpl(s, s2, n, 1, b);
    }
    
    public static Display getLastDisplayOf(final String s, final String s2, final int n, final boolean b) {
        return getDisplayOfImpl(s, s2, n, -1, b);
    }
    
    private static Display getDisplayOfImpl(final String s, final String s2, final int n, final int n2, final boolean b) {
        synchronized (Display.displayList) {
            int n3 = (n >= 0) ? n : (Display.displayList.size() - 1);
            while (true) {
                if (n2 > 0) {
                    if (n3 >= Display.displayList.size()) {
                        break;
                    }
                }
                else if (n3 < 0) {
                    break;
                }
                final Display display = Display.displayList.get(n3).get();
                if (null == display) {
                    Display.displayList.remove(n3);
                    if (n2 >= 0) {
                        continue;
                    }
                    n3 += n2;
                }
                else {
                    if (display.getType().equals(s) && display.getName().equals(s2) && (!b || (b && !display.isExclusive()))) {
                        return display;
                    }
                    n3 += n2;
                }
            }
        }
        return null;
    }
    
    protected static void addDisplay2List(final Display display) {
        synchronized (Display.displayList) {
            int i = 0;
            while (i < Display.displayList.size()) {
                if (null == Display.displayList.get(i).get()) {
                    Display.displayList.remove(i);
                }
                else {
                    ++i;
                }
            }
            Display.displayList.add(new WeakReference<Display>(display));
        }
    }
    
    public static Collection<Display> getAllDisplays() {
        final ArrayList<Object> list;
        synchronized (Display.displayList) {
            list = (ArrayList<Object>)new ArrayList<Display>();
            int i = 0;
            while (i < Display.displayList.size()) {
                if (null == Display.displayList.get(i).get()) {
                    Display.displayList.remove(i);
                }
                else {
                    list.add(Display.displayList.get(i).get());
                    ++i;
                }
            }
        }
        return (Collection<Display>)list;
    }
    
    public static int getActiveDisplayNumber() {
        synchronized (Display.displayList) {
            return Display.displaysActive;
        }
    }
    
    public static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    public static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    public static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    public static int hashCodeNullSafe(final Object o) {
        return (null != o) ? o.hashCode() : 0;
    }
    
    static {
        DEBUG = Debug.debug("Display");
        DEBUG_POINTER_ICON = Debug.debug("Display.PointerIcon");
        displayList = new ArrayList<WeakReference<Display>>();
        Display.displaysActive = 0;
    }
    
    public interface PointerIcon extends PixelRectangle
    {
        int getStride();
        
        boolean isGLOriented();
        
        int hashCode();
        
        Display getDisplay();
        
        PointImmutable getHotspot();
        
        boolean isValid();
        
        boolean validate();
        
        void destroy();
    }
}
