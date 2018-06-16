// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.jogamp.common.util.InterruptSource;
import jogamp.newt.Debug;
import com.jogamp.common.util.PropertyAccess;
import jogamp.newt.NEWTJNILibLoader;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.util.ArrayList;
import java.util.List;
import com.jogamp.common.os.Platform;

public class MainThread
{
    private static final String MACOSXDisplayClassName = "jogamp.newt.driver.macosx.DisplayDriver";
    private static final Platform.OSType osType;
    private static final boolean isMacOSX;
    private static final ThreadGroup rootThreadGroup;
    public static final boolean HINT_USE_MAIN_THREAD;
    public static boolean useMainThread;
    protected static final boolean DEBUG;
    private static final MainThread singletonMainThread;
    private static UserApp mainAction;
    
    private static final ThreadGroup getRootThreadGroup() {
        ThreadGroup threadGroup;
        ThreadGroup parent;
        for (threadGroup = Thread.currentThread().getThreadGroup(); (parent = threadGroup.getParent()) != null; threadGroup = parent) {}
        return threadGroup;
    }
    
    private static final Thread[] getAllThreads(final int[] array) {
        Thread[] array2;
        int enumerate;
        for (array2 = new Thread[MainThread.rootThreadGroup.activeCount()]; (enumerate = MainThread.rootThreadGroup.enumerate(array2, true)) == array2.length; array2 = new Thread[array2.length * 2]) {}
        array[0] = enumerate;
        return array2;
    }
    
    private static final List<Thread> getNonDaemonThreads() {
        final ArrayList<Thread> list = new ArrayList<Thread>();
        final int[] array = { 0 };
        final Thread[] allThreads = getAllThreads(array);
        for (int i = array[0] - 1; i >= 0; --i) {
            final Thread thread = allThreads[i];
            try {
                if (thread.isAlive() && !thread.isDaemon()) {
                    list.add(thread);
                    if (MainThread.DEBUG) {
                        System.err.println("XXX0: " + thread.getName() + ", " + thread);
                    }
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return list;
    }
    
    private static final int getNonDaemonThreadCount(final List<Thread> list) {
        int n = 0;
        final int[] array = { 0 };
        final Thread[] allThreads = getAllThreads(array);
        for (int i = array[0] - 1; i >= 0; --i) {
            final Thread thread = allThreads[i];
            try {
                if (thread.isAlive() && !thread.isDaemon() && !list.contains(thread)) {
                    ++n;
                    if (MainThread.DEBUG) {
                        System.err.println("MainAction.run(): non daemon thread: " + thread);
                    }
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return n;
    }
    
    public static void main(final String[] array) throws SecurityException, NoSuchMethodException, ClassNotFoundException {
        final Thread currentThread = Thread.currentThread();
        MainThread.useMainThread = MainThread.HINT_USE_MAIN_THREAD;
        if (MainThread.DEBUG) {
            System.err.println("MainThread.main(): " + currentThread.getName() + ", useMainThread " + MainThread.useMainThread + ", HINT_USE_MAIN_THREAD " + MainThread.HINT_USE_MAIN_THREAD + ", isAWTAvailable " + NativeWindowFactory.isAWTAvailable() + ", ostype " + MainThread.osType + ", isMacOSX " + MainThread.isMacOSX);
        }
        if (!MainThread.useMainThread && !NativeWindowFactory.isAWTAvailable()) {
            throw new RuntimeException("!USE_MAIN_THREAD and no AWT available");
        }
        if (array.length == 0) {
            return;
        }
        final String s = array[0];
        final String[] array2 = new String[array.length - 1];
        if (array.length > 1) {
            System.arraycopy(array, 1, array2, 0, array.length - 1);
        }
        MainThread.mainAction = new UserApp(s, array2);
        if (MainThread.isMacOSX) {
            ReflectionUtil.callStaticMethod("jogamp.newt.driver.macosx.DisplayDriver", "initSingleton", null, null, MainThread.class.getClassLoader());
        }
        if (MainThread.useMainThread) {
            try {
                currentThread.setName(currentThread.getName() + "-MainThread");
            }
            catch (Exception ex2) {}
            MainThread.mainAction.start();
            if (MainThread.isMacOSX) {
                try {
                    if (MainThread.DEBUG) {
                        System.err.println("MainThread.main(): " + currentThread.getName() + "- runNSApp");
                    }
                    ReflectionUtil.callStaticMethod("jogamp.newt.driver.macosx.DisplayDriver", "runNSApplication", null, null, MainThread.class.getClassLoader());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (MainThread.DEBUG) {
                System.err.println("MainThread - wait until last non daemon thread ends ...");
            }
        }
        else {
            MainThread.mainAction.run();
        }
    }
    
    public static MainThread getSingleton() {
        return MainThread.singletonMainThread;
    }
    
    static {
        NativeWindowFactory.initSingleton();
        NEWTJNILibLoader.loadNEWT();
        HINT_USE_MAIN_THREAD = (!NativeWindowFactory.isAWTAvailable() || PropertyAccess.getBooleanProperty("newt.MainThread.force", true));
        osType = Platform.getOSType();
        isMacOSX = (MainThread.osType == Platform.OSType.MACOS);
        rootThreadGroup = getRootThreadGroup();
        MainThread.useMainThread = false;
        DEBUG = Debug.debug("MainThread");
        singletonMainThread = new MainThread();
    }
    
    static class UserApp extends InterruptSource.Thread
    {
        private final String mainClassNameShort;
        private final String mainClassName;
        private final String[] mainClassArgs;
        private final Method mainClassMain;
        private List<java.lang.Thread> nonDaemonThreadsAtStart;
        
        public UserApp(final String mainClassName, final String[] mainClassArgs) throws SecurityException, NoSuchMethodException, ClassNotFoundException {
            this.mainClassName = mainClassName;
            this.mainClassArgs = mainClassArgs;
            final Class<?> class1 = ReflectionUtil.getClass(mainClassName, true, this.getClass().getClassLoader());
            if (null == class1) {
                throw new ClassNotFoundException("MainAction couldn't find main class " + mainClassName);
            }
            this.mainClassNameShort = class1.getSimpleName();
            (this.mainClassMain = class1.getDeclaredMethod("main", String[].class)).setAccessible(true);
            this.setName(this.getName() + "-UserApp-" + this.mainClassNameShort);
            this.setDaemon(false);
            if (MainThread.DEBUG) {
                System.err.println("MainAction(): instantiated: " + this.getName() + ", is daemon " + this.isDaemon() + ", main-class: " + class1.getName());
            }
        }
        
        @Override
        public void run() {
            this.nonDaemonThreadsAtStart = getNonDaemonThreads();
            if (MainThread.DEBUG) {
                System.err.println("MainAction.run(): " + java.lang.Thread.currentThread().getName() + " start, nonDaemonThreadsAtStart " + this.nonDaemonThreadsAtStart);
            }
            try {
                if (MainThread.DEBUG) {
                    System.err.println("MainAction.run(): " + java.lang.Thread.currentThread().getName() + " invoke " + this.mainClassName);
                }
                this.mainClassMain.invoke(null, this.mainClassArgs);
            }
            catch (InvocationTargetException ex) {
                ex.getTargetException().printStackTrace();
                return;
            }
            catch (Throwable t) {
                t.printStackTrace();
                return;
            }
            int access$100;
            while (0 < (access$100 = getNonDaemonThreadCount(this.nonDaemonThreadsAtStart))) {
                if (MainThread.DEBUG) {
                    System.err.println("MainAction.run(): post user app, non daemon threads alive: " + access$100);
                }
                try {
                    java.lang.Thread.sleep(1000L);
                }
                catch (InterruptedException ex2) {
                    ex2.printStackTrace();
                }
            }
            if (MainThread.DEBUG) {
                System.err.println("MainAction.run(): " + java.lang.Thread.currentThread().getName() + " user app fin: " + access$100);
            }
            if (MainThread.useMainThread) {
                if (MainThread.isMacOSX) {
                    try {
                        if (MainThread.DEBUG) {
                            System.err.println("MainAction.main(): " + java.lang.Thread.currentThread() + " MainAction fin - stopNSApp.0");
                        }
                        ReflectionUtil.callStaticMethod("jogamp.newt.driver.macosx.DisplayDriver", "stopNSApplication", null, null, MainThread.class.getClassLoader());
                        if (MainThread.DEBUG) {
                            System.err.println("MainAction.main(): " + java.lang.Thread.currentThread() + " MainAction fin - stopNSApp.X");
                        }
                    }
                    catch (Exception ex3) {
                        ex3.printStackTrace();
                    }
                }
                else {
                    if (MainThread.DEBUG) {
                        System.err.println("MainAction.run(): " + java.lang.Thread.currentThread().getName() + " MainAction fin - System.exit(0)");
                    }
                    System.exit(0);
                }
            }
        }
    }
}
