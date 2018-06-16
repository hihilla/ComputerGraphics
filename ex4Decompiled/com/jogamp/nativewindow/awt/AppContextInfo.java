// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.security.AccessController;
import java.security.PrivilegedAction;
import jogamp.nativewindow.jawt.JAWTUtil;
import com.jogamp.common.util.RunnableTask;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class AppContextInfo
{
    private static final boolean DEBUG;
    private static final Method getAppContextMethod;
    private static final Object mainThreadAppContextLock;
    private volatile WeakReference<Object> mainThreadAppContextWR;
    private volatile WeakReference<ThreadGroup> mainThreadGroupWR;
    
    public AppContextInfo(final String s) {
        this.mainThreadAppContextWR = null;
        this.mainThreadGroupWR = null;
        this.update(s);
    }
    
    public final boolean isValid() {
        return null != this.getCachedThreadGroup();
    }
    
    public final ThreadGroup getCachedThreadGroup() {
        final WeakReference<ThreadGroup> mainThreadGroupWR = this.mainThreadGroupWR;
        return (null != mainThreadGroupWR) ? ((ThreadGroup)mainThreadGroupWR.get()) : null;
    }
    
    public Thread invokeOnAppContextThread(final boolean b, final Runnable runnable, final String s) {
        Thread thread;
        if (this.update("invoke")) {
            thread = Thread.currentThread();
            if (AppContextInfo.DEBUG) {
                System.err.println("Bug 1004: Invoke.0 on current AppContext thread: " + thread + " " + toHexString(thread.hashCode()));
            }
            runnable.run();
        }
        else {
            final ThreadGroup cachedThreadGroup = this.getCachedThreadGroup();
            thread = RunnableTask.invokeOnNewThread(cachedThreadGroup, b, runnable, s + ((null != cachedThreadGroup) ? "-OnAppContextTG" : "-OnSystemTG"));
            if (AppContextInfo.DEBUG) {
                System.err.println("Bug 1004: Invoke.1 on new AppContext thread: " + thread + " " + toHexString(thread.hashCode()) + ", tg " + cachedThreadGroup + " " + toHexString((null != cachedThreadGroup) ? cachedThreadGroup.hashCode() : 0));
            }
        }
        return thread;
    }
    
    public final boolean update(final String s) {
        if (null != AppContextInfo.getAppContextMethod) {
            final Object fetchAppContext = fetchAppContext();
            final boolean b = null != fetchAppContext;
            final Thread currentThread = Thread.currentThread();
            final ThreadGroup threadGroup = currentThread.getThreadGroup();
            final WeakReference<Object> mainThreadAppContextWR = this.mainThreadAppContextWR;
            final Object o = (null != mainThreadAppContextWR) ? mainThreadAppContextWR.get() : null;
            if (b) {
                if (null == o || o != fetchAppContext) {
                    final int n = (null != o) ? o.hashCode() : 0;
                    final int hashCode;
                    synchronized (AppContextInfo.mainThreadAppContextLock) {
                        this.mainThreadGroupWR = new WeakReference<ThreadGroup>(threadGroup);
                        this.mainThreadAppContextWR = new WeakReference<Object>(fetchAppContext);
                        hashCode = fetchAppContext.hashCode();
                    }
                    if (AppContextInfo.DEBUG) {
                        System.err.println("Bug 1004[TGMapped " + b + "]: Init AppContext @ " + s + " on thread " + currentThread.getName() + " " + toHexString(currentThread.hashCode()) + ": tg " + threadGroup.getName() + " " + toHexString(threadGroup.hashCode()) + " -> appCtx [ main " + o + " " + toHexString(n) + " -> this " + fetchAppContext + " " + toHexString(hashCode) + " ] ");
                    }
                }
                else if (AppContextInfo.DEBUG) {
                    System.err.println("Bug 1004[TGMapped " + b + "]: OK AppContext @ " + s + " on thread " + currentThread.getName() + " " + toHexString(currentThread.hashCode()) + ": tg " + threadGroup.getName() + " " + toHexString(threadGroup.hashCode()) + "  : appCtx [ this " + fetchAppContext + " " + toHexString(fetchAppContext.hashCode()) + "  , main " + o + " " + toHexString(o.hashCode()) + " ] ");
                }
                return true;
            }
            if (AppContextInfo.DEBUG) {
                System.err.println("Bug 1004[TGMapped " + b + "]: No AppContext @ " + s + " on thread " + currentThread.getName() + " " + toHexString(currentThread.hashCode()) + ": tg " + threadGroup.getName() + " " + toHexString(threadGroup.hashCode()) + " -> appCtx [ this " + fetchAppContext + " " + toHexString((null != fetchAppContext) ? fetchAppContext.hashCode() : 0) + " -> main " + o + " " + toHexString((null != o) ? o.hashCode() : 0) + " ] ");
            }
        }
        return false;
    }
    
    private static Object fetchAppContext() {
        try {
            return AppContextInfo.getAppContextMethod.invoke(null, new Object[0]);
        }
        catch (Exception ex) {
            System.err.println("Bug 1004: Caught: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    static {
        mainThreadAppContextLock = new Object();
        DEBUG = JAWTUtil.DEBUG;
        final Method[] array = { null };
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    array[0] = Class.forName("sun.awt.AppContext").getMethod("getAppContext", (Class<?>[])new Class[0]);
                }
                catch (Throwable t) {
                    System.err.println("Bug 1004: Caught @ static: " + t.getMessage());
                    t.printStackTrace();
                }
                return null;
            }
        });
        getAppContextMethod = array[0];
    }
}
