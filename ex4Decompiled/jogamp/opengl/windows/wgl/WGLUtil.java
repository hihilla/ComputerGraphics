// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import jogamp.nativewindow.windows.GDI;
import jogamp.nativewindow.windows.PIXELFORMATDESCRIPTOR;

public class WGLUtil
{
    public static final boolean USE_WGLVersion_Of_5WGLGDIFuncSet;
    
    public static int ChoosePixelFormat(final long n, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglChoosePixelFormat(n, pixelformatdescriptor);
        }
        return GDI.ChoosePixelFormat(n, pixelformatdescriptor);
    }
    
    public static int DescribePixelFormat(final long n, final int n2, final int n3, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglDescribePixelFormat(n, n2, n3, pixelformatdescriptor);
        }
        return GDI.DescribePixelFormat(n, n2, n3, pixelformatdescriptor);
    }
    
    public static int GetPixelFormat(final long n) {
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglGetPixelFormat(n);
        }
        return GDI.GetPixelFormat(n);
    }
    
    public static boolean SetPixelFormat(final long n, final int n2, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglSetPixelFormat(n, n2, pixelformatdescriptor);
        }
        return GDI.SetPixelFormat(n, n2, pixelformatdescriptor);
    }
    
    public static boolean SwapBuffers(final long n) {
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            return WGL.wglSwapBuffers(n);
        }
        return GDI.SwapBuffers(n);
    }
    
    static {
        Debug.initSingleton();
        USE_WGLVersion_Of_5WGLGDIFuncSet = PropertyAccess.isPropertyDefined("jogl.windows.useWGLVersionOf5WGLGDIFuncSet", true);
        if (WGLUtil.USE_WGLVersion_Of_5WGLGDIFuncSet) {
            System.err.println("Use WGL version of 5 WGL/GDI functions.");
        }
    }
}
