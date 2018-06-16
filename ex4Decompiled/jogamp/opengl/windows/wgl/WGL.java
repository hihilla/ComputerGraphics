// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import java.nio.ByteBuffer;
import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;
import com.jogamp.opengl.GLException;

public class WGL
{
    public static final int WGL_SWAP_MAIN_PLANE = 1;
    public static final int WGL_SWAP_UNDERLAY15 = 1073741824;
    public static final int WGL_SWAP_UNDERLAY14 = 536870912;
    public static final int WGL_SWAP_UNDERLAY13 = 268435456;
    public static final int WGL_SWAP_UNDERLAY12 = 134217728;
    public static final int WGL_SWAP_UNDERLAY11 = 67108864;
    public static final int WGL_SWAP_UNDERLAY10 = 33554432;
    public static final int WGL_SWAP_OVERLAY14 = 16384;
    public static final int WGL_SWAP_OVERLAY15 = 32768;
    public static final int WGL_SWAP_OVERLAY12 = 4096;
    public static final int WGL_SWAP_OVERLAY13 = 8192;
    public static final int WGL_SWAP_OVERLAY10 = 1024;
    public static final int WGL_SWAP_OVERLAY11 = 2048;
    public static final int WGL_SWAP_OVERLAY9 = 512;
    public static final int WGL_SWAP_OVERLAY7 = 128;
    public static final int WGL_SWAP_OVERLAY8 = 256;
    public static final int WGL_SWAP_OVERLAY5 = 32;
    public static final int WGL_SWAP_OVERLAY6 = 64;
    public static final int WGL_SWAP_OVERLAY3 = 8;
    public static final int WGL_SWAP_OVERLAY4 = 16;
    public static final int WGL_SWAP_OVERLAY1 = 2;
    public static final int WGL_SWAP_OVERLAY2 = 4;
    public static final int WGL_SWAP_UNDERLAY4 = 524288;
    public static final int WGL_SWAP_UNDERLAY3 = 262144;
    public static final int WGL_SWAP_UNDERLAY2 = 131072;
    public static final int WGL_SWAP_UNDERLAY1 = 65536;
    public static final int WGL_SWAP_UNDERLAY9 = 16777216;
    public static final int WGL_SWAP_UNDERLAY8 = 8388608;
    public static final int WGL_SWAP_UNDERLAY7 = 4194304;
    public static final int WGL_SWAP_UNDERLAY6 = 2097152;
    public static final int WGL_SWAP_UNDERLAY5 = 1048576;
    private static WGLProcAddressTable wglProcAddressTable;
    
    public static boolean wglCopyContext(final long n, final long n2, final int n3) {
        final long addressof_wglCopyContext = WGL.wglProcAddressTable._addressof_wglCopyContext;
        if (addressof_wglCopyContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCopyContext"));
        }
        return dispatch_wglCopyContext0(n, n2, n3, addressof_wglCopyContext);
    }
    
    private static native boolean dispatch_wglCopyContext0(final long p0, final long p1, final int p2, final long p3);
    
    public static long wglCreateContext(final long n) {
        final long addressof_wglCreateContext = WGL.wglProcAddressTable._addressof_wglCreateContext;
        if (addressof_wglCreateContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateContext"));
        }
        return dispatch_wglCreateContext0(n, addressof_wglCreateContext);
    }
    
    private static native long dispatch_wglCreateContext0(final long p0, final long p1);
    
    public static boolean wglDeleteContext(final long n) {
        final long addressof_wglDeleteContext = WGL.wglProcAddressTable._addressof_wglDeleteContext;
        if (addressof_wglDeleteContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDeleteContext"));
        }
        return dispatch_wglDeleteContext0(n, addressof_wglDeleteContext);
    }
    
    private static native boolean dispatch_wglDeleteContext0(final long p0, final long p1);
    
    public static long wglGetCurrentContext() {
        final long addressof_wglGetCurrentContext = WGL.wglProcAddressTable._addressof_wglGetCurrentContext;
        if (addressof_wglGetCurrentContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetCurrentContext"));
        }
        return dispatch_wglGetCurrentContext0(addressof_wglGetCurrentContext);
    }
    
    private static native long dispatch_wglGetCurrentContext0(final long p0);
    
    public static long wglGetCurrentDC() {
        final long addressof_wglGetCurrentDC = WGL.wglProcAddressTable._addressof_wglGetCurrentDC;
        if (addressof_wglGetCurrentDC == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetCurrentDC"));
        }
        return dispatch_wglGetCurrentDC0(addressof_wglGetCurrentDC);
    }
    
    private static native long dispatch_wglGetCurrentDC0(final long p0);
    
    public static boolean wglMakeCurrent(final long n, final long n2) {
        final long addressof_wglMakeCurrent = WGL.wglProcAddressTable._addressof_wglMakeCurrent;
        if (addressof_wglMakeCurrent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglMakeCurrent"));
        }
        return dispatch_wglMakeCurrent0(n, n2, addressof_wglMakeCurrent);
    }
    
    private static native boolean dispatch_wglMakeCurrent0(final long p0, final long p1, final long p2);
    
    public static boolean wglShareLists(final long n, final long n2) {
        final long addressof_wglShareLists = WGL.wglProcAddressTable._addressof_wglShareLists;
        if (addressof_wglShareLists == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglShareLists"));
        }
        return dispatch_wglShareLists0(n, n2, addressof_wglShareLists);
    }
    
    private static native boolean dispatch_wglShareLists0(final long p0, final long p1, final long p2);
    
    public static long wglGetProcAddress(final String s) {
        final long addressof_wglGetProcAddress = WGL.wglProcAddressTable._addressof_wglGetProcAddress;
        if (addressof_wglGetProcAddress == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetProcAddress"));
        }
        return dispatch_wglGetProcAddress0(s, addressof_wglGetProcAddress);
    }
    
    private static native long dispatch_wglGetProcAddress0(final String p0, final long p1);
    
    public static boolean wglSwapLayerBuffers(final long n, final int n2) {
        final long addressof_wglSwapLayerBuffers = WGL.wglProcAddressTable._addressof_wglSwapLayerBuffers;
        if (addressof_wglSwapLayerBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSwapLayerBuffers"));
        }
        return dispatch_wglSwapLayerBuffers0(n, n2, addressof_wglSwapLayerBuffers);
    }
    
    private static native boolean dispatch_wglSwapLayerBuffers0(final long p0, final int p1, final long p2);
    
    public static int wglChoosePixelFormat(final long n, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        final long addressof_wglChoosePixelFormat = WGL.wglProcAddressTable._addressof_wglChoosePixelFormat;
        if (addressof_wglChoosePixelFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglChoosePixelFormat"));
        }
        return dispatch_wglChoosePixelFormat0(n, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer(), addressof_wglChoosePixelFormat);
    }
    
    private static native int dispatch_wglChoosePixelFormat0(final long p0, final ByteBuffer p1, final long p2);
    
    public static int wglDescribePixelFormat(final long n, final int n2, final int n3, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        final long addressof_wglDescribePixelFormat = WGL.wglProcAddressTable._addressof_wglDescribePixelFormat;
        if (addressof_wglDescribePixelFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDescribePixelFormat"));
        }
        return dispatch_wglDescribePixelFormat0(n, n2, n3, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer(), addressof_wglDescribePixelFormat);
    }
    
    private static native int dispatch_wglDescribePixelFormat0(final long p0, final int p1, final int p2, final ByteBuffer p3, final long p4);
    
    public static int wglGetPixelFormat(final long n) {
        final long addressof_wglGetPixelFormat = WGL.wglProcAddressTable._addressof_wglGetPixelFormat;
        if (addressof_wglGetPixelFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPixelFormat"));
        }
        return dispatch_wglGetPixelFormat0(n, addressof_wglGetPixelFormat);
    }
    
    private static native int dispatch_wglGetPixelFormat0(final long p0, final long p1);
    
    public static boolean wglSetPixelFormat(final long n, final int n2, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        final long addressof_wglSetPixelFormat = WGL.wglProcAddressTable._addressof_wglSetPixelFormat;
        if (addressof_wglSetPixelFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSetPixelFormat"));
        }
        return dispatch_wglSetPixelFormat0(n, n2, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer(), addressof_wglSetPixelFormat);
    }
    
    private static native boolean dispatch_wglSetPixelFormat0(final long p0, final int p1, final ByteBuffer p2, final long p3);
    
    public static boolean wglSwapBuffers(final long n) {
        final long addressof_wglSwapBuffers = WGL.wglProcAddressTable._addressof_wglSwapBuffers;
        if (addressof_wglSwapBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSwapBuffers"));
        }
        return dispatch_wglSwapBuffers0(n, addressof_wglSwapBuffers);
    }
    
    private static native boolean dispatch_wglSwapBuffers0(final long p0, final long p1);
    
    public static WGLProcAddressTable getWGLProcAddressTable() {
        return WGL.wglProcAddressTable;
    }
    
    static long wglGetProcAddress(final long n, final String s) {
        if (n == 0L) {
            throw new GLException("Passed null pointer for method \"wglGetProcAddress\"");
        }
        return dispatch_wglGetProcAddress0(s, n);
    }
    
    static {
        WGL.wglProcAddressTable = new WGLProcAddressTable(new GLProcAddressResolver());
    }
}
