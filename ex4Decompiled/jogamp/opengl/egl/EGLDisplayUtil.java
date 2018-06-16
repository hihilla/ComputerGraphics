// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.common.nio.Buffers;
import jogamp.opengl.Debug;
import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.egl.EGL;
import java.util.Iterator;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.egl.EGLGraphicsDevice;
import java.nio.IntBuffer;
import com.jogamp.common.util.LongObjectHashMap;

public class EGLDisplayUtil
{
    private static final boolean DEBUG;
    private static boolean useSingletonEGLDisplay;
    private static EGLDisplayRef singletonEGLDisplay;
    private static final LongObjectHashMap openEGLDisplays;
    private static final IntBuffer _eglMajorVersion;
    private static final IntBuffer _eglMinorVersion;
    private static final EGLGraphicsDevice.EGLDisplayLifecycleCallback eglLifecycleCallback;
    
    public static int shutdown(final boolean b) {
        if (EGLDisplayUtil.DEBUG || b || EGLDisplayUtil.openEGLDisplays.size() > 0) {
            System.err.println("EGLDisplayUtil.EGLDisplays: Shutdown (open: " + EGLDisplayUtil.openEGLDisplays.size() + ")");
            if (EGLDisplayUtil.DEBUG) {
                ExceptionUtils.dumpStack(System.err);
            }
            if (EGLDisplayUtil.openEGLDisplays.size() > 0) {
                dumpOpenDisplayConnections();
            }
        }
        return EGLDisplayUtil.openEGLDisplays.size();
    }
    
    public static void dumpOpenDisplayConnections() {
        System.err.println("EGLDisplayUtil: Open EGL Display Connections: " + EGLDisplayUtil.openEGLDisplays.size());
        int n = 0;
        for (final LongObjectHashMap.Entry entry : EGLDisplayUtil.openEGLDisplays) {
            final EGLDisplayRef eglDisplayRef = (EGLDisplayRef)entry.value;
            System.err.println("EGLDisplayUtil: Open[" + n + "]: 0x" + Long.toHexString(entry.key) + ": " + eglDisplayRef);
            if (null != eglDisplayRef.createdStack) {
                eglDisplayRef.createdStack.printStackTrace();
            }
            ++n;
        }
    }
    
    static synchronized void setSingletonEGLDisplayOnly(final boolean useSingletonEGLDisplay) {
        EGLDisplayUtil.useSingletonEGLDisplay = useSingletonEGLDisplay;
    }
    
    private static synchronized long eglGetDisplay(final long n) {
        if (EGLDisplayUtil.useSingletonEGLDisplay && null != EGLDisplayUtil.singletonEGLDisplay) {
            if (EGLDisplayUtil.DEBUG) {
                System.err.println("EGLDisplayUtil.eglGetDisplay.s: eglDisplay(" + EGLContext.toHexString(n) + "): " + EGLContext.toHexString(EGLDisplayUtil.singletonEGLDisplay.eglDisplay) + ", " + ((0L != EGLDisplayUtil.singletonEGLDisplay.eglDisplay) ? "OK" : "Failed") + ", singletonEGLDisplay " + EGLDisplayUtil.singletonEGLDisplay + " (use " + EGLDisplayUtil.useSingletonEGLDisplay + ")");
            }
            return EGLDisplayUtil.singletonEGLDisplay.eglDisplay;
        }
        final long eglGetDisplay = EGL.eglGetDisplay(n);
        if (EGLDisplayUtil.DEBUG) {
            System.err.println("EGLDisplayUtil.eglGetDisplay.X: eglDisplay(" + EGLContext.toHexString(n) + "): " + EGLContext.toHexString(eglGetDisplay) + ", " + ((0L != eglGetDisplay) ? "OK" : "Failed") + ", singletonEGLDisplay " + EGLDisplayUtil.singletonEGLDisplay + " (use " + EGLDisplayUtil.useSingletonEGLDisplay + ")");
        }
        return eglGetDisplay;
    }
    
    private static synchronized boolean eglInitialize(final long n, final int[] array, final int[] array2) {
        if (0L == n) {
            return false;
        }
        final EGLDisplayRef orCreateOpened = EGLDisplayRef.getOrCreateOpened(n, EGLDisplayUtil._eglMajorVersion, EGLDisplayUtil._eglMinorVersion);
        final int value = EGLDisplayUtil._eglMajorVersion.get(0);
        final int value2 = EGLDisplayUtil._eglMinorVersion.get(0);
        if (null != array && null != array2) {
            if (null != orCreateOpened) {
                array[0] = value;
                array2[0] = value2;
            }
            else {
                array2[array[0] = 0] = 0;
            }
        }
        if (EGLDisplayUtil.DEBUG) {
            System.err.println("EGLDisplayUtil.eglInitialize(" + EGLContext.toHexString(n) + " ...): " + orCreateOpened + " = " + (null != orCreateOpened) + ", eglVersion " + value + "." + value2 + ", singletonEGLDisplay " + EGLDisplayUtil.singletonEGLDisplay + " (use " + EGLDisplayUtil.useSingletonEGLDisplay + ")");
        }
        return null != orCreateOpened;
    }
    
    private static synchronized int eglGetDisplayAndInitialize(final long n, final long[] array, final int[] array2, final int[] array3, final int[] array4) {
        array[0] = 0L;
        final long eglGetDisplay = eglGetDisplay(n);
        if (0L == eglGetDisplay) {
            array2[0] = EGL.eglGetError();
            return 12296;
        }
        if (!eglInitialize(eglGetDisplay, array3, array4)) {
            array2[0] = EGL.eglGetError();
            return 12289;
        }
        array[0] = eglGetDisplay;
        return 12288;
    }
    
    private static synchronized long eglGetDisplayAndInitialize(final long[] array, final int[] array2, final int[] array3) {
        final long[] array4 = { 0L };
        final int[] array5 = { 0 };
        int n = eglGetDisplayAndInitialize(array[0], array4, array5, array2, array3);
        if (12288 == n) {
            return array4[0];
        }
        if (0L != array[0]) {
            if (EGLDisplayUtil.DEBUG) {
                System.err.println("EGLDisplayUtil.eglGetAndInitDisplay failed with native " + EGLContext.toHexString(array[0]) + ", error " + EGLContext.toHexString(n) + "/" + EGLContext.toHexString(array5[0]) + " - fallback!");
            }
            n = eglGetDisplayAndInitialize(0L, array4, array5, array2, array3);
            if (12288 == n) {
                array[0] = 0L;
                return array4[0];
            }
        }
        throw new GLException("Failed to created/initialize EGL display incl. fallback default: native " + EGLContext.toHexString(array[0]) + ", error " + EGLContext.toHexString(n) + "/" + EGLContext.toHexString(array5[0]));
    }
    
    private static synchronized boolean eglTerminate(final long n) {
        if (0L == n) {
            return false;
        }
        final boolean[] array = { false };
        final EGLDisplayRef closeOpened = EGLDisplayRef.closeOpened(n, array);
        if (EGLDisplayUtil.DEBUG) {
            System.err.println("EGLDisplayUtil.eglTerminate.X(" + EGLContext.toHexString(n) + " ...): " + closeOpened + " = " + array[0] + ", singletonEGLDisplay " + EGLDisplayUtil.singletonEGLDisplay + " (use " + EGLDisplayUtil.useSingletonEGLDisplay + ")");
        }
        return array[0];
    }
    
    public static EGLGraphicsDevice eglCreateEGLGraphicsDevice(final long n, final String s, final int n2) {
        return new EGLGraphicsDevice(n, 0L, s, n2, EGLDisplayUtil.eglLifecycleCallback);
    }
    
    public static EGLGraphicsDevice eglCreateEGLGraphicsDevice(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return new EGLGraphicsDevice(abstractGraphicsDevice, 0L, EGLDisplayUtil.eglLifecycleCallback);
    }
    
    public static EGLGraphicsDevice eglCreateEGLGraphicsDevice(final NativeSurface nativeSurface) {
        long n;
        if (NativeWindowFactory.TYPE_WINDOWS == NativeWindowFactory.getNativeWindowType(false)) {
            n = nativeSurface.getSurfaceHandle();
        }
        else {
            n = nativeSurface.getDisplayHandle();
        }
        final AbstractGraphicsDevice device = nativeSurface.getGraphicsConfiguration().getScreen().getDevice();
        return new EGLGraphicsDevice(n, 0L, device.getConnection(), device.getUnitID(), EGLDisplayUtil.eglLifecycleCallback);
    }
    
    static {
        DEBUG = Debug.debug("EGLDisplayUtil");
        EGLDisplayUtil.useSingletonEGLDisplay = false;
        EGLDisplayUtil.singletonEGLDisplay = null;
        (openEGLDisplays = new LongObjectHashMap()).setKeyNotFoundValue(null);
        _eglMajorVersion = Buffers.newDirectIntBuffer(1);
        _eglMinorVersion = Buffers.newDirectIntBuffer(1);
        eglLifecycleCallback = new EGLGraphicsDevice.EGLDisplayLifecycleCallback() {
            @Override
            public long eglGetAndInitDisplay(final long[] array, final int[] array2, final int[] array3) {
                return eglGetDisplayAndInitialize(array, array2, array3);
            }
            
            @Override
            public void eglTerminate(final long n) {
                eglTerminate(n);
            }
        };
    }
    
    private static class EGLDisplayRef
    {
        final long eglDisplay;
        final Throwable createdStack;
        int initRefCount;
        
        static EGLDisplayRef getOrCreateOpened(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
            final EGLDisplayRef eglDisplayRef = (EGLDisplayRef)EGLDisplayUtil.openEGLDisplays.get(n);
            if (null != eglDisplayRef) {
                final EGLDisplayRef eglDisplayRef2 = eglDisplayRef;
                ++eglDisplayRef2.initRefCount;
                return eglDisplayRef;
            }
            final boolean eglInitialize = EGL.eglInitialize(n, intBuffer, intBuffer2);
            if (EGLDisplayUtil.DEBUG) {
                System.err.println("EGLDisplayUtil.EGL.eglInitialize 0x" + Long.toHexString(n) + " -> " + eglInitialize);
            }
            if (eglInitialize) {
                final EGLDisplayRef eglDisplayRef3 = new EGLDisplayRef(n);
                EGLDisplayUtil.openEGLDisplays.put(n, eglDisplayRef3);
                final EGLDisplayRef eglDisplayRef4 = eglDisplayRef3;
                ++eglDisplayRef4.initRefCount;
                if (EGLDisplayUtil.DEBUG) {
                    System.err.println("EGLDisplayUtil.EGL.eglInitialize " + eglDisplayRef3);
                }
                if (null == EGLDisplayUtil.singletonEGLDisplay) {
                    EGLDisplayUtil.singletonEGLDisplay = eglDisplayRef3;
                }
                return eglDisplayRef3;
            }
            return null;
        }
        
        static EGLDisplayRef closeOpened(final long n, final boolean[] array) {
            final EGLDisplayRef eglDisplayRef = (EGLDisplayRef)EGLDisplayUtil.openEGLDisplays.get(n);
            array[0] = true;
            if (null != eglDisplayRef) {
                if (0 < eglDisplayRef.initRefCount) {
                    final EGLDisplayRef eglDisplayRef2 = eglDisplayRef;
                    --eglDisplayRef2.initRefCount;
                    if (0 == eglDisplayRef.initRefCount) {
                        final boolean eglTerminate = EGL.eglTerminate(n);
                        if (EGLDisplayUtil.DEBUG) {
                            System.err.println("EGLDisplayUtil.EGL.eglTerminate 0x" + Long.toHexString(n) + " -> " + eglTerminate);
                            System.err.println("EGLDisplayUtil.EGL.eglTerminate " + eglDisplayRef);
                        }
                        array[0] = eglTerminate;
                        if (eglDisplayRef == EGLDisplayUtil.singletonEGLDisplay) {
                            EGLDisplayUtil.singletonEGLDisplay = null;
                        }
                    }
                }
                if (0 >= eglDisplayRef.initRefCount) {
                    EGLDisplayUtil.openEGLDisplays.remove(n);
                }
            }
            return eglDisplayRef;
        }
        
        private EGLDisplayRef(final long eglDisplay) {
            this.eglDisplay = eglDisplay;
            this.initRefCount = 0;
            this.createdStack = (EGLDisplayUtil.DEBUG ? new Throwable() : null);
        }
        
        @Override
        public String toString() {
            return "EGLDisplayRef[0x" + Long.toHexString(this.eglDisplay) + ": refCnt " + this.initRefCount + "]";
        }
    }
}
