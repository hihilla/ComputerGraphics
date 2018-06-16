// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.x11;

import com.jogamp.nativewindow.util.Point;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;

public class X11Lib
{
    public static final int VisualNoMask = 0;
    public static final int PictFormatBlueMask = 256;
    public static final int VisualScreenMask = 2;
    public static final int PictFormatRed = 8;
    public static final int PictFormatType = 2;
    public static final int VisualGreenMaskMask = 32;
    public static final int PictFormatRedMask = 16;
    public static final int VisualBlueMaskMask = 64;
    public static final int PictFormatGreen = 32;
    public static final int VisualAllMask = 511;
    public static final int PictFormatAlpha = 512;
    public static final int VisualIDMask = 1;
    public static final int VisualDepthMask = 4;
    public static final int PictFormatColormap = 2048;
    public static final int PictFormatDepth = 4;
    public static final int VisualColormapSizeMask = 128;
    public static final int PictFormatID = 1;
    public static final int VisualBitsPerRGBMask = 256;
    public static final int PictFormatBlue = 128;
    public static final int VisualRedMaskMask = 16;
    public static final int PictFormatAlphaMask = 1024;
    public static final int PictFormatGreenMask = 64;
    public static final int VisualClassMask = 8;
    
    public static native long XineramaGetLibHandle();
    
    public static native boolean XineramaReleaseLibHandle(final long p0);
    
    public static native long XineramaGetQueryFunc(final long p0);
    
    public static native boolean XineramaIsEnabled(final long p0, final long p1);
    
    public static native long XSynchronize(final long p0, final boolean p1);
    
    public static native int XFlush(final long p0);
    
    public static native int XSync(final long p0, final boolean p1);
    
    public static native String XDisplayString(final long p0);
    
    public static native long XOpenDisplay(final String p0);
    
    public static native int DefaultScreen(final long p0);
    
    public static native int ScreenCount(final long p0);
    
    public static native long RootWindow(final long p0, final int p1);
    
    public static native long XCreatePixmap(final long p0, final long p1, final int p2, final int p3, final int p4);
    
    public static native int XFreePixmap(final long p0, final long p1);
    
    public static native int XFree(final long p0);
    
    public static boolean XF86VidModeGetGammaRampSize(final long n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        return XF86VidModeGetGammaRampSize1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct);
    }
    
    private static native boolean XF86VidModeGetGammaRampSize1(final long p0, final int p1, final Object p2, final int p3, final boolean p4);
    
    public static boolean XF86VidModeGetGammaRampSize(final long n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new RuntimeException("array offset argument \"size_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        return XF86VidModeGetGammaRampSize1(n, n2, array, 4 * n3, false);
    }
    
    public static boolean XF86VidModeGetGammaRamp(final long n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ShortBuffer shortBuffer3) {
        final boolean direct = Buffers.isDirect(shortBuffer);
        final boolean direct2 = Buffers.isDirect(shortBuffer2);
        final boolean direct3 = Buffers.isDirect(shortBuffer3);
        return XF86VidModeGetGammaRamp1(n, n2, n3, direct ? shortBuffer : Buffers.getArray(shortBuffer), direct ? Buffers.getDirectBufferByteOffset(shortBuffer) : Buffers.getIndirectBufferByteOffset(shortBuffer), direct, direct2 ? shortBuffer2 : Buffers.getArray(shortBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(shortBuffer2) : Buffers.getIndirectBufferByteOffset(shortBuffer2), direct2, direct3 ? shortBuffer3 : Buffers.getArray(shortBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(shortBuffer3) : Buffers.getIndirectBufferByteOffset(shortBuffer3), direct3);
    }
    
    private static native boolean XF86VidModeGetGammaRamp1(final long p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final Object p9, final int p10, final boolean p11);
    
    public static boolean XF86VidModeGetGammaRamp(final long n, final int n2, final int n3, final short[] array, final int n4, final short[] array2, final int n5, final short[] array3, final int n6) {
        if (array != null && array.length <= n4) {
            throw new RuntimeException("array offset argument \"red_array_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new RuntimeException("array offset argument \"green_array_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n6) {
            throw new RuntimeException("array offset argument \"blue_array_offset\" (" + n6 + ") equals or exceeds array length (" + array3.length + ")");
        }
        return XF86VidModeGetGammaRamp1(n, n2, n3, array, 2 * n4, false, array2, 2 * n5, false, array3, 2 * n6, false);
    }
    
    public static boolean XF86VidModeSetGammaRamp(final long n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2, final ShortBuffer shortBuffer3) {
        final boolean direct = Buffers.isDirect(shortBuffer);
        final boolean direct2 = Buffers.isDirect(shortBuffer2);
        final boolean direct3 = Buffers.isDirect(shortBuffer3);
        return XF86VidModeSetGammaRamp1(n, n2, n3, direct ? shortBuffer : Buffers.getArray(shortBuffer), direct ? Buffers.getDirectBufferByteOffset(shortBuffer) : Buffers.getIndirectBufferByteOffset(shortBuffer), direct, direct2 ? shortBuffer2 : Buffers.getArray(shortBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(shortBuffer2) : Buffers.getIndirectBufferByteOffset(shortBuffer2), direct2, direct3 ? shortBuffer3 : Buffers.getArray(shortBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(shortBuffer3) : Buffers.getIndirectBufferByteOffset(shortBuffer3), direct3);
    }
    
    private static native boolean XF86VidModeSetGammaRamp1(final long p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final Object p9, final int p10, final boolean p11);
    
    public static boolean XF86VidModeSetGammaRamp(final long n, final int n2, final int n3, final short[] array, final int n4, final short[] array2, final int n5, final short[] array3, final int n6) {
        if (array != null && array.length <= n4) {
            throw new RuntimeException("array offset argument \"red_array_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new RuntimeException("array offset argument \"green_array_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n6) {
            throw new RuntimeException("array offset argument \"blue_array_offset\" (" + n6 + ") equals or exceeds array length (" + array3.length + ")");
        }
        return XF86VidModeSetGammaRamp1(n, n2, n3, array, 2 * n4, false, array2, 2 * n5, false, array3, 2 * n6, false);
    }
    
    public static boolean XRenderFindVisualFormat(final long n, final long n2, final XRenderPictFormat xRenderPictFormat) {
        if (xRenderPictFormat == null) {
            throw new RuntimeException("dest is null");
        }
        final ByteBuffer buffer = xRenderPictFormat.getBuffer();
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("dest buffer is not direct");
        }
        return XRenderFindVisualFormat1(n, n2, buffer);
    }
    
    private static native boolean XRenderFindVisualFormat1(final long p0, final long p1, final ByteBuffer p2);
    
    public static XVisualInfo[] XGetVisualInfo(final long n, final long n2, final XVisualInfo xVisualInfo, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new RuntimeException("array offset argument \"arg3_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final ByteBuffer xGetVisualInfo1 = XGetVisualInfo1(n, n2, (xVisualInfo == null) ? null : xVisualInfo.getBuffer(), array, 4 * n3);
        if (xGetVisualInfo1 == null) {
            return null;
        }
        Buffers.nativeOrder(xGetVisualInfo1);
        final int firstElement = getFirstElement(array, n3);
        if (firstElement <= 0) {
            return null;
        }
        final int n4 = xGetVisualInfo1.capacity() / firstElement;
        if (n4 < XVisualInfo.size()) {
            throw new RuntimeException("element-size " + xGetVisualInfo1.capacity() + "/" + firstElement + "=" + n4 + " < " + XVisualInfo.size());
        }
        final XVisualInfo[] array2 = new XVisualInfo[firstElement];
        for (int i = 0; i < firstElement; ++i) {
            xGetVisualInfo1.position(i * n4);
            xGetVisualInfo1.limit((1 + i) * n4);
            final ByteBuffer slice = xGetVisualInfo1.slice();
            xGetVisualInfo1.position(0);
            xGetVisualInfo1.limit(xGetVisualInfo1.capacity());
            array2[i] = XVisualInfo.create(slice);
        }
        return array2;
    }
    
    private static native ByteBuffer XGetVisualInfo1(final long p0, final long p1, final ByteBuffer p2, final Object p3, final int p4);
    
    public static native int GetVisualIDFromWindow(final long p0, final long p1);
    
    public static native int DefaultVisualID(final long p0, final int p1);
    
    public static native long CreateWindow(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final boolean p7);
    
    public static native void DestroyWindow(final long p0, final long p1);
    
    public static native void SetWindowPosSize(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5);
    
    public static Point GetRelativeLocation(final long n, final int n2, final long n3, final long n4, final int n5, final int n6) {
        return (Point)GetRelativeLocation0(n, n2, n3, n4, n5, n6);
    }
    
    private static native Object GetRelativeLocation0(final long p0, final int p1, final long p2, final long p3, final int p4, final int p5);
    
    public static boolean QueryExtension(final long n, final String s) {
        return QueryExtension0(n, s);
    }
    
    private static native boolean QueryExtension0(final long p0, final String p1);
    
    public static native int XCloseDisplay(final long p0);
    
    public static native void XUnlockDisplay(final long p0);
    
    public static native void XLockDisplay(final long p0);
    
    private static int getFirstElement(final IntBuffer intBuffer) {
        return intBuffer.get(intBuffer.position());
    }
    
    private static int getFirstElement(final int[] array, final int n) {
        return array[n];
    }
}
