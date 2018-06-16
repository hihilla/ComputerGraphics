// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;

public class GDI
{
    public static final int DWMWA_NCRENDERING_ENABLED = 1;
    public static final int DWMWA_NCRENDERING_POLICY = 2;
    public static final int DWMWA_TRANSITIONS_FORCEDISABLED = 3;
    public static final int DWMWA_ALLOW_NCPAINT = 4;
    public static final int DWMWA_CAPTION_BUTTON_BOUNDS = 5;
    public static final int DWMWA_NONCLIENT_RTL_LAYOUT = 6;
    public static final int DWMWA_FORCE_ICONIC_REPRESENTATION = 7;
    public static final int DWMWA_FLIP3D_POLICY = 8;
    public static final int DWMWA_EXTENDED_FRAME_BOUNDS = 9;
    public static final int DWMWA_HAS_ICONIC_BITMAP = 10;
    public static final int DWMWA_DISALLOW_PEEK = 11;
    public static final int DWMWA_EXCLUDED_FROM_PEEK = 12;
    public static final int DWMWA_CLOAK = 13;
    public static final int DWMWA_CLOAKED = 14;
    public static final int DWMWA_FREEZE_REPRESENTATION = 15;
    public static final int DWMWA_LAST = 16;
    public static final int ACCENT_DISABLED = 0;
    public static final int ACCENT_ENABLE_GRADIENT = 1;
    public static final int ACCENT_ENABLE_TRANSPARENTGRADIENT = 2;
    public static final int ACCENT_ENABLE_BLURBEHIND = 3;
    public static final int ACCENT_INVALID_STATE = 4;
    public static final int DWMNCRP_USEWINDOWSTYLE = 0;
    public static final int DWMNCRP_DISABLED = 1;
    public static final int DWMNCRP_ENABLED = 2;
    public static final int DWMNCRP_LAST = 3;
    public static final int OBJ_METADC = 4;
    public static final int SW_SHOW = 5;
    public static final int SW_SHOWDEFAULT = 10;
    public static final int PFD_STEREO = 2;
    public static final int PFD_SUPPORT_DIRECTDRAW = 8192;
    public static final int DIB_PAL_COLORS = 1;
    public static final int BI_RLE8 = 1;
    public static final int PFD_GENERIC_ACCELERATED = 4096;
    public static final int BI_RLE4 = 2;
    public static final int OBJ_ENHMETAFILE = 13;
    public static final int SW_FORCEMINIMIZE = 11;
    public static final int ERROR_SUCCESS = 0;
    public static final int ERROR_INVALID_DATA = 13;
    public static final int PFD_UNDERLAY_PLANE = -1;
    public static final int DWM_EC_ENABLECOMPOSITION = 1;
    public static final int DWM_BB_BLURREGION = 2;
    public static final int SW_NORMAL = 1;
    public static final int ERROR_INVALID_WINDOW_HANDLE = 1400;
    public static final int DIB_RGB_COLORS = 0;
    public static final int ERROR_NO_SYSTEM_RESOURCES = 1450;
    public static final int BI_BITFIELDS = 3;
    public static final int PFD_MAIN_PLANE = 0;
    public static final int DWM_EC_DISABLECOMPOSITION = 0;
    public static final int OBJ_BRUSH = 2;
    public static final int PFD_DEPTH_DONTCARE = 536870912;
    public static final int PFD_SUPPORT_GDI = 16;
    public static final long PFD_STEREO_DONTCARE = 2147483648L;
    public static final int OBJ_BITMAP = 7;
    public static final int SW_SHOWMINNOACTIVE = 7;
    public static final int OBJ_COLORSPACE = 14;
    public static final int SW_MINIMIZE = 6;
    public static final int OBJ_MEMDC = 10;
    public static final int PFD_SWAP_COPY = 1024;
    public static final int PFD_TYPE_COLORINDEX = 1;
    public static final int SW_SHOWNORMAL = 1;
    public static final int PFD_NEED_SYSTEM_PALETTE = 256;
    public static final int SW_MAXIMIZE = 3;
    public static final int SW_RESTORE = 9;
    public static final int PFD_SUPPORT_OPENGL = 32;
    public static final int SW_HIDE = 0;
    public static final int OBJ_FONT = 6;
    public static final int PFD_SWAP_EXCHANGE = 512;
    public static final int SW_SHOWNA = 8;
    public static final int PFD_DRAW_TO_BITMAP = 8;
    public static final int OBJ_EXTPEN = 11;
    public static final int PFD_NEED_PALETTE = 128;
    public static final int ERROR_PROC_NOT_FOUND = 127;
    public static final int OBJ_METAFILE = 9;
    public static final int PFD_SWAP_LAYER_BUFFERS = 2048;
    public static final int PFD_DOUBLEBUFFER = 1;
    public static final int PFD_DRAW_TO_WINDOW = 4;
    public static final int SW_SHOWNOACTIVATE = 4;
    public static final int OBJ_ENHMETADC = 12;
    public static final int BI_RGB = 0;
    public static final int DWM_BB_TRANSITIONONMAXIMIZED = 4;
    public static final int SW_SHOWMINIMIZED = 2;
    public static final int PFD_DOUBLEBUFFER_DONTCARE = 1073741824;
    public static final int OBJ_REGION = 8;
    public static final int SW_MAX = 11;
    public static final int OBJ_DC = 3;
    public static final int PFD_OVERLAY_PLANE = 1;
    public static final int PFD_GENERIC_FORMAT = 64;
    public static final int OBJ_PAL = 5;
    public static final int ERROR_INVALID_PIXEL_FORMAT = 2000;
    public static final int OBJ_PEN = 1;
    public static final int SW_SHOWMAXIMIZED = 3;
    public static final int PFD_TYPE_RGBA = 0;
    public static final int DWM_BB_ENABLE = 1;
    
    public static native int GetLastError();
    
    public static int ChoosePixelFormat(final long n, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        return ChoosePixelFormat1(n, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer());
    }
    
    private static native int ChoosePixelFormat1(final long p0, final ByteBuffer p1);
    
    public static int DescribePixelFormat(final long n, final int n2, final int n3, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        return DescribePixelFormat1(n, n2, n3, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer());
    }
    
    private static native int DescribePixelFormat1(final long p0, final int p1, final int p2, final ByteBuffer p3);
    
    public static native int GetPixelFormat(final long p0);
    
    public static boolean SetPixelFormat(final long n, final int n2, final PIXELFORMATDESCRIPTOR pixelformatdescriptor) {
        return SetPixelFormat1(n, n2, (pixelformatdescriptor == null) ? null : pixelformatdescriptor.getBuffer());
    }
    
    private static native boolean SetPixelFormat1(final long p0, final int p1, final ByteBuffer p2);
    
    public static native boolean SwapBuffers(final long p0);
    
    public static native long CreateCompatibleDC(final long p0);
    
    public static long CreateDIBSection(final long n, final BITMAPINFO bitmapinfo, final int n2, final PointerBuffer pointerBuffer, final long n3, final int n4) {
        final boolean direct = Buffers.isDirect(pointerBuffer);
        return CreateDIBSection1(n, (bitmapinfo == null) ? null : bitmapinfo.getBuffer(), n2, direct ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct, n3, n4);
    }
    
    private static native long CreateDIBSection1(final long p0, final ByteBuffer p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6, final int p7);
    
    public static native boolean DeleteDC(final long p0);
    
    public static native boolean DeleteObject(final long p0);
    
    public static native long SelectObject(final long p0, final long p1);
    
    public static native long GetApplicationHandle();
    
    public static native boolean ShowWindow(final long p0, final int p1);
    
    public static native long GetDC(final long p0);
    
    public static native int ReleaseDC(final long p0, final long p1);
    
    public static native long WindowFromDC(final long p0);
    
    public static boolean GetClientRect(final long n, final RECT rect) {
        return GetClientRect1(n, (rect == null) ? null : rect.getBuffer());
    }
    
    private static native boolean GetClientRect1(final long p0, final ByteBuffer p1);
    
    public static native boolean DestroyWindow(final long p0);
    
    public static native int GetObjectType(final long p0);
    
    public static native boolean IsWindowVisible(final long p0);
    
    public static native boolean IsWindow(final long p0);
    
    public static native long GetParent(final long p0);
    
    public static native long SetParent(final long p0, final long p1);
    
    public static native long GetCurrentProcess();
    
    public static native long GetCurrentThread();
    
    public static boolean GetProcessAffinityMask(final long n, final PointerBuffer pointerBuffer, final PointerBuffer pointerBuffer2) {
        final boolean direct = Buffers.isDirect(pointerBuffer);
        final boolean direct2 = Buffers.isDirect(pointerBuffer2);
        return GetProcessAffinityMask1(n, direct ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct, direct2 ? ((pointerBuffer2 != null) ? pointerBuffer2.getBuffer() : null) : Buffers.getArray(pointerBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(pointerBuffer2) : Buffers.getIndirectBufferByteOffset(pointerBuffer2), direct2);
    }
    
    private static native boolean GetProcessAffinityMask1(final long p0, final Object p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6);
    
    public static native boolean SetProcessAffinityMask(final long p0, final long p1);
    
    public static native long SetThreadAffinityMask(final long p0, final long p1);
    
    public static boolean GetDeviceGammaRamp(final long n, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        return GetDeviceGammaRamp1(n, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct);
    }
    
    private static native boolean GetDeviceGammaRamp1(final long p0, final Object p1, final int p2, final boolean p3);
    
    public static boolean SetDeviceGammaRamp(final long n, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        return SetDeviceGammaRamp1(n, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct);
    }
    
    private static native boolean SetDeviceGammaRamp1(final long p0, final Object p1, final int p2, final boolean p3);
    
    public static native boolean DwmIsExtensionAvailable();
    
    public static native boolean DwmIsCompositionEnabled();
    
    public static native boolean DwmEnableComposition(final int p0);
    
    public static boolean DwmEnableBlurBehindWindow(final long n, final DWM_BLURBEHIND dwm_BLURBEHIND) {
        return DwmEnableBlurBehindWindow0(n, (dwm_BLURBEHIND == null) ? null : dwm_BLURBEHIND.getBuffer());
    }
    
    private static native boolean DwmEnableBlurBehindWindow0(final long p0, final ByteBuffer p1);
    
    public static boolean DwmExtendFrameIntoClientArea(final long n, final MARGINS margins) {
        return DwmExtendFrameIntoClientArea0(n, (margins == null) ? null : margins.getBuffer());
    }
    
    private static native boolean DwmExtendFrameIntoClientArea0(final long p0, final ByteBuffer p1);
    
    public static int DwmGetWindowAttribute(final long n, final int n2, final Buffer buffer, final int n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"pvAttribute\" is not a direct buffer");
        }
        return DwmGetWindowAttribute0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), n3);
    }
    
    private static native int DwmGetWindowAttribute0(final long p0, final int p1, final Object p2, final int p3, final int p4);
    
    public static int DwmSetWindowAttribute(final long n, final int n2, final Buffer buffer, final int n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"pvAttribute\" is not a direct buffer");
        }
        return DwmSetWindowAttribute0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), n3);
    }
    
    private static native int DwmSetWindowAttribute0(final long p0, final int p1, final Object p2, final int p3, final int p4);
    
    public static native boolean IsWindowCompositionExtensionAvailable();
    
    public static boolean GetWindowCompositionAccentPolicy(final long n, final AccentPolicy accentPolicy) {
        return GetWindowCompositionAccentPolicy0(n, (accentPolicy == null) ? null : accentPolicy.getBuffer());
    }
    
    private static native boolean GetWindowCompositionAccentPolicy0(final long p0, final ByteBuffer p1);
    
    public static boolean SetWindowCompositionAccentPolicy(final long n, final AccentPolicy accentPolicy) {
        return SetWindowCompositionAccentPolicy0(n, (accentPolicy == null) ? null : accentPolicy.getBuffer());
    }
    
    private static native boolean SetWindowCompositionAccentPolicy0(final long p0, final ByteBuffer p1);
}
