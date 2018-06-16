// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.LongBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GLException;
import jogamp.nativewindow.x11.XVisualInfo;

public class GLX
{
    public static final int GLX_TRANSPARENT_BLUE_VALUE = 39;
    public static final int GLX_ACCUM_ALPHA_SIZE = 17;
    public static final int GLX_TRUE_COLOR_EXT = 32770;
    public static final int GLX_ACCUM_RED_SIZE = 14;
    public static final int GLX_X_VISUAL_TYPE = 34;
    public static final int GLX_SHARE_CONTEXT_EXT = 32778;
    public static final int GLX_SCREEN_EXT = 32780;
    public static final int GLX_SAMPLE_BUFFERS_ARB = 100000;
    public static final int GLX_TRANSPARENT_RED_VALUE_EXT = 37;
    public static final int GLX_CONTEXT_DEBUG_BIT_ARB = 1;
    public static final int GLX_STATIC_GRAY_EXT = 32775;
    public static final int GLX_X_VISUAL_TYPE_EXT = 34;
    public static final int GLX_STEREO = 6;
    public static final int GLX_CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;
    public static final int GLX_CONTEXT_ALLOW_BUFFER_BYTE_ORDER_MISMATCH_ARB = 8341;
    public static final int GLX_GREEN_SIZE = 9;
    public static final int GLX_TRANSPARENT_INDEX = 32777;
    public static final int GLX_TRANSPARENT_INDEX_VALUE_EXT = 36;
    public static final int GLX_PSEUDO_COLOR = 32772;
    public static final int GLX_STATIC_COLOR_EXT = 32773;
    public static final int GLX_DOUBLEBUFFER = 5;
    public static final int GLX_TRANSPARENT_TYPE_EXT = 35;
    public static final int GLX_CONTEXT_RELEASE_BEHAVIOR_ARB = 8343;
    public static final int GLX_DEPTH_BUFFER_BIT = 32;
    public static final int GLX_CONTEXT_COMPATIBILITY_PROFILE_BIT_ARB = 2;
    public static final int GLX_WIDTH = 32797;
    public static final int GLX_STEREO_NOTIFY_MASK_EXT = 1;
    public static final int GLX_LARGEST_PBUFFER = 32796;
    public static final int GLX_NON_CONFORMANT_VISUAL_EXT = 32781;
    public static final int GLX_HEIGHT = 32798;
    public static final int GLX_DIRECT_COLOR_EXT = 32771;
    public static final int GLX_FRAMEBUFFER_SRGB_CAPABLE_ARB = 8370;
    public static final int GLX_VISUAL_CAVEAT_EXT = 32;
    public static final int GLX_BACK_LEFT_BUFFER_BIT = 4;
    public static final int GLX_BAD_CONTEXT = 5;
    public static final int GLX_PBUFFER_HEIGHT = 32832;
    public static final int GLX_BAD_ATTRIBUTE = 2;
    public static final int GLX_SAMPLES_ARB = 100001;
    public static final int GLX_SAMPLE_BUFFERS = 100000;
    public static final int GLX_WINDOW_BIT = 1;
    public static final int GLX_TRANSPARENT_ALPHA_VALUE = 40;
    public static final int GLX_FBCONFIG_ID = 32787;
    public static final int GLX_TRANSPARENT_INDEX_VALUE = 36;
    public static final int GLX_PSEUDO_COLOR_EXT = 32772;
    public static final int GLX_BLUE_SIZE = 10;
    public static final int GLX_BUFFER_SWAP_COMPLETE_INTEL_MASK = 67108864;
    public static final int GLX_PBUFFER_CLOBBER_MASK = 134217728;
    public static final int GLX_CONTEXT_ROBUST_ACCESS_BIT_ARB = 4;
    public static final int GLX_CONTEXT_RESET_ISOLATION_BIT_ARB = 8;
    public static final int GLX_MAX_PBUFFER_WIDTH = 32790;
    public static final int GLX_RENDER_TYPE = 32785;
    public static final int GLX_NONE_EXT = 32768;
    public static final int GLX_CONTEXT_CORE_PROFILE_BIT_ARB = 1;
    public static final int GLX_NONE = 32768;
    public static final int GLX_CONTEXT_MAJOR_VERSION_ARB = 8337;
    public static final int GLX_RGBA = 4;
    public static final int GLX_DEPTH_SIZE = 12;
    public static final int GLX_CONTEXT_MINOR_VERSION_ARB = 8338;
    public static final int GLX_TRANSPARENT_GREEN_VALUE = 38;
    public static final int GLX_VENDOR = 1;
    public static final int GLX_COPY_COMPLETE_INTEL = 33153;
    public static final int GLX_NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int GLX_BUFFER_SIZE = 2;
    public static final int GLX_PBUFFER = 32803;
    public static final int GLX_FRONT_LEFT_BUFFER_BIT = 1;
    public static final int GLX_SCREEN = 32780;
    public static final int GLX_TRANSPARENT_TYPE = 35;
    public static final int GLX_VISUAL_ID_EXT = 32779;
    public static final int GLX_SLOW_VISUAL_EXT = 32769;
    public static final int GLX_ACCUM_BUFFER_BIT = 128;
    public static final int GLX_SAMPLES = 100001;
    public static final int GLX_SAVED = 32801;
    public static final int GLX_TRANSPARENT_ALPHA_VALUE_EXT = 40;
    public static final int GLX_FRONT_RIGHT_BUFFER_BIT = 2;
    public static final int GLX_CONTEXT_RELEASE_BEHAVIOR_NONE_ARB = 0;
    public static final int GLX_COLOR_INDEX_TYPE = 32789;
    public static final int GLX_MAX_SWAP_INTERVAL_EXT = 8434;
    public static final int GLX_MAX_PBUFFER_PIXELS = 32792;
    public static final int GLX_LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int GLX_STENCIL_SIZE = 13;
    public static final int GLX_PBUFFER_WIDTH = 32833;
    public static final int GLX_RGBA_BIT = 1;
    public static final int GLX_DIRECT_COLOR = 32771;
    public static final int GLX_USE_GL = 1;
    public static final int GLX_TRANSPARENT_RED_VALUE = 37;
    public static final int GLX_PBUFFER_BIT = 4;
    public static final int GLX_CONTEXT_ES_PROFILE_BIT_EXT = 4;
    public static final int GLX_RGBA_UNSIGNED_FLOAT_BIT_EXT = 8;
    public static final int GLX_PRESERVED_CONTENTS = 32795;
    public static final int GLX_SAMPLES_3DFX = 32849;
    public static final int GLX_NO_EXTENSION = 3;
    public static final int GLX_DAMAGED = 32800;
    public static final int GLX_RGBA_FLOAT_TYPE_ARB = 8377;
    public static final int GLX_GRAY_SCALE_EXT = 32774;
    public static final int GLX_PIXMAP_BIT = 2;
    public static final int GLX_STATIC_COLOR = 32773;
    public static final int GLX_TRANSPARENT_BLUE_VALUE_EXT = 39;
    public static final int GLX_FLIP_COMPLETE_INTEL = 33154;
    public static final int GLX_BAD_VISUAL = 4;
    public static final int GLX_STEREO_TREE_EXT = 8437;
    public static final int GLX_TRANSPARENT_GREEN_VALUE_EXT = 38;
    public static final int GLX_LEVEL = 3;
    public static final int GLX_GRAY_SCALE = 32774;
    public static final int GLX_STEREO_NOTIFY_EXT = 0;
    public static final int GLX_X_RENDERABLE = 32786;
    public static final int GLX_BACK_RIGHT_BUFFER_BIT = 8;
    public static final int GLX_BAD_ENUM = 7;
    public static final int GLX_FRAMEBUFFER_SRGB_CAPABLE_EXT = 8370;
    public static final int GLX_SAMPLE_BUFFERS_3DFX = 32848;
    public static final int GLX_LATE_SWAPS_TEAR_EXT = 8435;
    public static final int GLX_CONTEXT_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int GLX_RGBA_UNSIGNED_FLOAT_TYPE_EXT = 8369;
    public static final int GLX_CONFIG_CAVEAT = 32;
    public static final int GLX_TRANSPARENT_INDEX_EXT = 32777;
    public static final int GLX_COLOR_INDEX_BIT = 2;
    public static final int GLX_EVENT_MASK = 32799;
    public static final int GLX_SWAP_INTERVAL_EXT = 8433;
    public static final int GLX_SLOW_CONFIG = 32769;
    public static final int GLX_BACK_BUFFER_AGE_EXT = 8436;
    public static final int GLX_VERSION = 2;
    public static final int GLX_TRANSPARENT_RGB_EXT = 32776;
    public static final long GLX_DONT_CARE = 4294967295L;
    public static final int GLX_CONTEXT_FLAGS_ARB = 8340;
    public static final int GLX_EXTENSIONS = 3;
    public static final int GLX_BAD_VALUE = 6;
    public static final int GLX_ACCUM_BLUE_SIZE = 16;
    public static final int GLX_STENCIL_BUFFER_BIT = 64;
    public static final int GLX_ACCUM_GREEN_SIZE = 15;
    public static final int GLX_ALPHA_SIZE = 11;
    public static final int GLX_WINDOW = 32802;
    public static final int GLX_AUX_BUFFERS_BIT = 16;
    public static final int GLX_VISUAL_ID = 32779;
    public static final int GLX_RGBA_TYPE = 32788;
    public static final int GLX_DRAWABLE_TYPE = 32784;
    public static final int GLX_MAX_PBUFFER_HEIGHT = 32791;
    public static final int GLX_CONTEXT_PROFILE_MASK_ARB = 37158;
    public static final int GLX_EXCHANGE_COMPLETE_INTEL = 33152;
    public static final int GLX_AUX_BUFFERS = 7;
    public static final int GLX_RED_SIZE = 8;
    public static final int GLX_CONTEXT_RELEASE_BEHAVIOR_FLUSH_ARB = 8344;
    public static final int GLX_TRUE_COLOR = 32770;
    public static final int GLX_RGBA_FLOAT_BIT_ARB = 4;
    public static final int GLX_TRANSPARENT_RGB = 32776;
    public static final int GLX_STATIC_GRAY = 32775;
    public static final int GLX_BAD_SCREEN = 1;
    public static final int GLX_CONTEXT_ES2_PROFILE_BIT_EXT = 4;
    public static final int GLX_NON_CONFORMANT_CONFIG = 32781;
    private static GLXProcAddressTable glxProcAddressTable;
    
    public static long glXCreateContext(final long n, final XVisualInfo xVisualInfo, final long n2, final boolean b) {
        final long addressof_glXCreateContext = GLX.glxProcAddressTable._addressof_glXCreateContext;
        if (addressof_glXCreateContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateContext"));
        }
        return dispatch_glXCreateContext0(n, (xVisualInfo == null) ? null : xVisualInfo.getBuffer(), n2, b, addressof_glXCreateContext);
    }
    
    private static native long dispatch_glXCreateContext0(final long p0, final ByteBuffer p1, final long p2, final boolean p3, final long p4);
    
    public static void glXDestroyContext(final long n, final long n2) {
        final long addressof_glXDestroyContext = GLX.glxProcAddressTable._addressof_glXDestroyContext;
        if (addressof_glXDestroyContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDestroyContext"));
        }
        dispatch_glXDestroyContext0(n, n2, addressof_glXDestroyContext);
    }
    
    private static native void dispatch_glXDestroyContext0(final long p0, final long p1, final long p2);
    
    public static boolean glXMakeCurrent(final long n, final long n2, final long n3) {
        final long addressof_glXMakeCurrent = GLX.glxProcAddressTable._addressof_glXMakeCurrent;
        if (addressof_glXMakeCurrent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXMakeCurrent"));
        }
        return dispatch_glXMakeCurrent0(n, n2, n3, addressof_glXMakeCurrent);
    }
    
    private static native boolean dispatch_glXMakeCurrent0(final long p0, final long p1, final long p2, final long p3);
    
    public static void glXCopyContext(final long n, final long n2, final long n3, final long n4) {
        final long addressof_glXCopyContext = GLX.glxProcAddressTable._addressof_glXCopyContext;
        if (addressof_glXCopyContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCopyContext"));
        }
        dispatch_glXCopyContext0(n, n2, n3, n4, addressof_glXCopyContext);
    }
    
    private static native void dispatch_glXCopyContext0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    public static void glXSwapBuffers(final long n, final long n2) {
        final long addressof_glXSwapBuffers = GLX.glxProcAddressTable._addressof_glXSwapBuffers;
        if (addressof_glXSwapBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSwapBuffers"));
        }
        dispatch_glXSwapBuffers0(n, n2, addressof_glXSwapBuffers);
    }
    
    private static native void dispatch_glXSwapBuffers0(final long p0, final long p1, final long p2);
    
    public static long glXCreateGLXPixmap(final long n, final XVisualInfo xVisualInfo, final long n2) {
        final long addressof_glXCreateGLXPixmap = GLX.glxProcAddressTable._addressof_glXCreateGLXPixmap;
        if (addressof_glXCreateGLXPixmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateGLXPixmap"));
        }
        return dispatch_glXCreateGLXPixmap0(n, (xVisualInfo == null) ? null : xVisualInfo.getBuffer(), n2, addressof_glXCreateGLXPixmap);
    }
    
    private static native long dispatch_glXCreateGLXPixmap0(final long p0, final ByteBuffer p1, final long p2, final long p3);
    
    public static void glXDestroyGLXPixmap(final long n, final long n2) {
        final long addressof_glXDestroyGLXPixmap = GLX.glxProcAddressTable._addressof_glXDestroyGLXPixmap;
        if (addressof_glXDestroyGLXPixmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDestroyGLXPixmap"));
        }
        dispatch_glXDestroyGLXPixmap0(n, n2, addressof_glXDestroyGLXPixmap);
    }
    
    private static native void dispatch_glXDestroyGLXPixmap0(final long p0, final long p1, final long p2);
    
    public static boolean glXQueryExtension(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"errorb\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"event\" is not a direct buffer");
        }
        final long addressof_glXQueryExtension = GLX.glxProcAddressTable._addressof_glXQueryExtension;
        if (addressof_glXQueryExtension == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryExtension"));
        }
        return dispatch_glXQueryExtension0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXQueryExtension);
    }
    
    private static native boolean dispatch_glXQueryExtension0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static boolean glXQueryVersion(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"maj\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"min\" is not a direct buffer");
        }
        final long addressof_glXQueryVersion = GLX.glxProcAddressTable._addressof_glXQueryVersion;
        if (addressof_glXQueryVersion == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryVersion"));
        }
        return dispatch_glXQueryVersion0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXQueryVersion);
    }
    
    private static native boolean dispatch_glXQueryVersion0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static boolean glXIsDirect(final long n, final long n2) {
        final long addressof_glXIsDirect = GLX.glxProcAddressTable._addressof_glXIsDirect;
        if (addressof_glXIsDirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXIsDirect"));
        }
        return dispatch_glXIsDirect0(n, n2, addressof_glXIsDirect);
    }
    
    private static native boolean dispatch_glXIsDirect0(final long p0, final long p1, final long p2);
    
    public static int glXGetConfig(final long n, final XVisualInfo xVisualInfo, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXGetConfig = GLX.glxProcAddressTable._addressof_glXGetConfig;
        if (addressof_glXGetConfig == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetConfig"));
        }
        return dispatch_glXGetConfig0(n, (xVisualInfo == null) ? null : xVisualInfo.getBuffer(), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetConfig);
    }
    
    private static native int dispatch_glXGetConfig0(final long p0, final ByteBuffer p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static long glXGetCurrentContext() {
        final long addressof_glXGetCurrentContext = GLX.glxProcAddressTable._addressof_glXGetCurrentContext;
        if (addressof_glXGetCurrentContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentContext"));
        }
        return dispatch_glXGetCurrentContext0(addressof_glXGetCurrentContext);
    }
    
    private static native long dispatch_glXGetCurrentContext0(final long p0);
    
    public static long glXGetCurrentDrawable() {
        final long addressof_glXGetCurrentDrawable = GLX.glxProcAddressTable._addressof_glXGetCurrentDrawable;
        if (addressof_glXGetCurrentDrawable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentDrawable"));
        }
        return dispatch_glXGetCurrentDrawable0(addressof_glXGetCurrentDrawable);
    }
    
    private static native long dispatch_glXGetCurrentDrawable0(final long p0);
    
    public static void glXWaitGL() {
        final long addressof_glXWaitGL = GLX.glxProcAddressTable._addressof_glXWaitGL;
        if (addressof_glXWaitGL == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXWaitGL"));
        }
        dispatch_glXWaitGL0(addressof_glXWaitGL);
    }
    
    private static native void dispatch_glXWaitGL0(final long p0);
    
    public static void glXWaitX() {
        final long addressof_glXWaitX = GLX.glxProcAddressTable._addressof_glXWaitX;
        if (addressof_glXWaitX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXWaitX"));
        }
        dispatch_glXWaitX0(addressof_glXWaitX);
    }
    
    private static native void dispatch_glXWaitX0(final long p0);
    
    public static void glXUseXFont(final long n, final int n2, final int n3, final int n4) {
        final long addressof_glXUseXFont = GLX.glxProcAddressTable._addressof_glXUseXFont;
        if (addressof_glXUseXFont == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXUseXFont"));
        }
        dispatch_glXUseXFont0(n, n2, n3, n4, addressof_glXUseXFont);
    }
    
    private static native void dispatch_glXUseXFont0(final long p0, final int p1, final int p2, final int p3, final long p4);
    
    public static String glXQueryExtensionsString(final long n, final int n2) {
        final long addressof_glXQueryExtensionsString = GLX.glxProcAddressTable._addressof_glXQueryExtensionsString;
        if (addressof_glXQueryExtensionsString == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryExtensionsString"));
        }
        return dispatch_glXQueryExtensionsString0(n, n2, addressof_glXQueryExtensionsString);
    }
    
    private static native String dispatch_glXQueryExtensionsString0(final long p0, final int p1, final long p2);
    
    public static String glXQueryServerString(final long n, final int n2, final int n3) {
        final long addressof_glXQueryServerString = GLX.glxProcAddressTable._addressof_glXQueryServerString;
        if (addressof_glXQueryServerString == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryServerString"));
        }
        return dispatch_glXQueryServerString0(n, n2, n3, addressof_glXQueryServerString);
    }
    
    private static native String dispatch_glXQueryServerString0(final long p0, final int p1, final int p2, final long p3);
    
    public static String glXGetClientString(final long n, final int n2) {
        final long addressof_glXGetClientString = GLX.glxProcAddressTable._addressof_glXGetClientString;
        if (addressof_glXGetClientString == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetClientString"));
        }
        return dispatch_glXGetClientString0(n, n2, addressof_glXGetClientString);
    }
    
    private static native String dispatch_glXGetClientString0(final long p0, final int p1, final long p2);
    
    public static long glXGetCurrentDisplay() {
        final long addressof_glXGetCurrentDisplay = GLX.glxProcAddressTable._addressof_glXGetCurrentDisplay;
        if (addressof_glXGetCurrentDisplay == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentDisplay"));
        }
        return dispatch_glXGetCurrentDisplay0(addressof_glXGetCurrentDisplay);
    }
    
    private static native long dispatch_glXGetCurrentDisplay0(final long p0);
    
    public static long glXGetProcAddress(final String s) {
        final long addressof_glXGetProcAddress = GLX.glxProcAddressTable._addressof_glXGetProcAddress;
        if (addressof_glXGetProcAddress == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetProcAddress"));
        }
        return dispatch_glXGetProcAddress0(s, addressof_glXGetProcAddress);
    }
    
    private static native long dispatch_glXGetProcAddress0(final String p0, final long p1);
    
    public static long glXGetProcAddressARB(final String s) {
        final long addressof_glXGetProcAddressARB = GLX.glxProcAddressTable._addressof_glXGetProcAddressARB;
        if (addressof_glXGetProcAddressARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetProcAddressARB"));
        }
        return dispatch_glXGetProcAddressARB0(s, addressof_glXGetProcAddressARB);
    }
    
    private static native long dispatch_glXGetProcAddressARB0(final String p0, final long p1);
    
    public static int glXGetFBConfigAttrib(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXGetFBConfigAttrib = GLX.glxProcAddressTable._addressof_glXGetFBConfigAttrib;
        if (addressof_glXGetFBConfigAttrib == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetFBConfigAttrib"));
        }
        return dispatch_glXGetFBConfigAttrib0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetFBConfigAttrib);
    }
    
    private static native int dispatch_glXGetFBConfigAttrib0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static long glXCreateWindow(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXCreateWindow = GLX.glxProcAddressTable._addressof_glXCreateWindow;
        if (addressof_glXCreateWindow == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateWindow"));
        }
        return dispatch_glXCreateWindow0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXCreateWindow);
    }
    
    private static native long dispatch_glXCreateWindow0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public static void glXDestroyWindow(final long n, final long n2) {
        final long addressof_glXDestroyWindow = GLX.glxProcAddressTable._addressof_glXDestroyWindow;
        if (addressof_glXDestroyWindow == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDestroyWindow"));
        }
        dispatch_glXDestroyWindow0(n, n2, addressof_glXDestroyWindow);
    }
    
    private static native void dispatch_glXDestroyWindow0(final long p0, final long p1, final long p2);
    
    public static long glXCreatePixmap(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXCreatePixmap = GLX.glxProcAddressTable._addressof_glXCreatePixmap;
        if (addressof_glXCreatePixmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreatePixmap"));
        }
        return dispatch_glXCreatePixmap0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXCreatePixmap);
    }
    
    private static native long dispatch_glXCreatePixmap0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public static void glXDestroyPixmap(final long n, final long n2) {
        final long addressof_glXDestroyPixmap = GLX.glxProcAddressTable._addressof_glXDestroyPixmap;
        if (addressof_glXDestroyPixmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDestroyPixmap"));
        }
        dispatch_glXDestroyPixmap0(n, n2, addressof_glXDestroyPixmap);
    }
    
    private static native void dispatch_glXDestroyPixmap0(final long p0, final long p1, final long p2);
    
    public static long glXCreatePbuffer(final long n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXCreatePbuffer = GLX.glxProcAddressTable._addressof_glXCreatePbuffer;
        if (addressof_glXCreatePbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreatePbuffer"));
        }
        return dispatch_glXCreatePbuffer0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXCreatePbuffer);
    }
    
    private static native long dispatch_glXCreatePbuffer0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    public static void glXDestroyPbuffer(final long n, final long n2) {
        final long addressof_glXDestroyPbuffer = GLX.glxProcAddressTable._addressof_glXDestroyPbuffer;
        if (addressof_glXDestroyPbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDestroyPbuffer"));
        }
        dispatch_glXDestroyPbuffer0(n, n2, addressof_glXDestroyPbuffer);
    }
    
    private static native void dispatch_glXDestroyPbuffer0(final long p0, final long p1, final long p2);
    
    public static void glXQueryDrawable(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryDrawable = GLX.glxProcAddressTable._addressof_glXQueryDrawable;
        if (addressof_glXQueryDrawable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryDrawable"));
        }
        dispatch_glXQueryDrawable0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryDrawable);
    }
    
    private static native void dispatch_glXQueryDrawable0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static long glXCreateNewContext(final long n, final long n2, final int n3, final long n4, final boolean b) {
        final long addressof_glXCreateNewContext = GLX.glxProcAddressTable._addressof_glXCreateNewContext;
        if (addressof_glXCreateNewContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateNewContext"));
        }
        return dispatch_glXCreateNewContext0(n, n2, n3, n4, b, addressof_glXCreateNewContext);
    }
    
    private static native long dispatch_glXCreateNewContext0(final long p0, final long p1, final int p2, final long p3, final boolean p4, final long p5);
    
    public static boolean glXMakeContextCurrent(final long n, final long n2, final long n3, final long n4) {
        final long addressof_glXMakeContextCurrent = GLX.glxProcAddressTable._addressof_glXMakeContextCurrent;
        if (addressof_glXMakeContextCurrent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXMakeContextCurrent"));
        }
        return dispatch_glXMakeContextCurrent0(n, n2, n3, n4, addressof_glXMakeContextCurrent);
    }
    
    private static native boolean dispatch_glXMakeContextCurrent0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    public static long glXGetCurrentReadDrawable() {
        final long addressof_glXGetCurrentReadDrawable = GLX.glxProcAddressTable._addressof_glXGetCurrentReadDrawable;
        if (addressof_glXGetCurrentReadDrawable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentReadDrawable"));
        }
        return dispatch_glXGetCurrentReadDrawable0(addressof_glXGetCurrentReadDrawable);
    }
    
    private static native long dispatch_glXGetCurrentReadDrawable0(final long p0);
    
    public static int glXQueryContext(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryContext = GLX.glxProcAddressTable._addressof_glXQueryContext;
        if (addressof_glXQueryContext == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryContext"));
        }
        return dispatch_glXQueryContext0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryContext);
    }
    
    private static native int dispatch_glXQueryContext0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static void glXSelectEvent(final long n, final long n2, final long n3) {
        final long addressof_glXSelectEvent = GLX.glxProcAddressTable._addressof_glXSelectEvent;
        if (addressof_glXSelectEvent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSelectEvent"));
        }
        dispatch_glXSelectEvent0(n, n2, n3, addressof_glXSelectEvent);
    }
    
    private static native void dispatch_glXSelectEvent0(final long p0, final long p1, final long p2, final long p3);
    
    public static void glXGetSelectedEvent(final long n, final long n2, final LongBuffer longBuffer) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"event_mask\" is not a direct buffer");
        }
        final long addressof_glXGetSelectedEvent = GLX.glxProcAddressTable._addressof_glXGetSelectedEvent;
        if (addressof_glXGetSelectedEvent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetSelectedEvent"));
        }
        dispatch_glXGetSelectedEvent0(n, n2, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), addressof_glXGetSelectedEvent);
    }
    
    private static native void dispatch_glXGetSelectedEvent0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    public static void glXSwapIntervalEXT(final long n, final long n2, final int n3) {
        final long addressof_glXSwapIntervalEXT = GLX.glxProcAddressTable._addressof_glXSwapIntervalEXT;
        if (addressof_glXSwapIntervalEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSwapIntervalEXT"));
        }
        dispatch_glXSwapIntervalEXT0(n, n2, n3, addressof_glXSwapIntervalEXT);
    }
    
    private static native void dispatch_glXSwapIntervalEXT0(final long p0, final long p1, final int p2, final long p3);
    
    public static GLXProcAddressTable getGLXProcAddressTable() {
        return GLX.glxProcAddressTable;
    }
    
    public static int glXGetFBConfigAttributes(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (intBuffer == null || intBuffer2 == null) {
            throw new RuntimeException("arrays buffers are null");
        }
        if (!Buffers.isDirect(intBuffer) || !Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("arrays buffers are not direct");
        }
        if (intBuffer.remaining() > intBuffer2.remaining()) {
            throw new RuntimeException("not enough values " + intBuffer2 + " for attributes " + intBuffer);
        }
        return dispatch_glXGetFBConfigAttributes(n, n2, intBuffer.remaining(), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), GLX.glxProcAddressTable._addressof_glXGetFBConfigAttrib);
    }
    
    private static native int dispatch_glXGetFBConfigAttributes(final long p0, final long p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    public static XVisualInfo glXGetVisualFromFBConfig(final long n, final long n2) {
        final long addressof_glXGetVisualFromFBConfig = GLX.glxProcAddressTable._addressof_glXGetVisualFromFBConfig;
        if (addressof_glXGetVisualFromFBConfig == 0L) {
            throw new GLException("Method \"glXGetVisualFromFBConfig\" not available");
        }
        final ByteBuffer dispatch_glXGetVisualFromFBConfig = dispatch_glXGetVisualFromFBConfig(n, n2, addressof_glXGetVisualFromFBConfig);
        if (dispatch_glXGetVisualFromFBConfig == null) {
            return null;
        }
        return XVisualInfo.create(Buffers.nativeOrder(dispatch_glXGetVisualFromFBConfig));
    }
    
    private static native ByteBuffer dispatch_glXGetVisualFromFBConfig(final long p0, final long p1, final long p2);
    
    public static PointerBuffer glXChooseFBConfig(final long n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribList\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"nitems\" is not a direct buffer");
        }
        final long addressof_glXChooseFBConfig = GLX.glxProcAddressTable._addressof_glXChooseFBConfig;
        if (addressof_glXChooseFBConfig == 0L) {
            throw new GLException("Method \"glXChooseFBConfig\" not available");
        }
        final ByteBuffer dispatch_glXChooseFBConfig = dispatch_glXChooseFBConfig(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXChooseFBConfig);
        if (dispatch_glXChooseFBConfig == null) {
            return null;
        }
        return PointerBuffer.wrap(Buffers.nativeOrder(dispatch_glXChooseFBConfig));
    }
    
    private static native ByteBuffer dispatch_glXChooseFBConfig(final long p0, final int p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    public static PointerBuffer glXGetFBConfigs(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"nelements\" is not a direct buffer");
        }
        final long addressof_glXGetFBConfigs = GLX.glxProcAddressTable._addressof_glXGetFBConfigs;
        if (addressof_glXGetFBConfigs == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetFBConfigs"));
        }
        final ByteBuffer dispatch_glXGetFBConfigs = dispatch_glXGetFBConfigs(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetFBConfigs);
        if (dispatch_glXGetFBConfigs == null) {
            return null;
        }
        return PointerBuffer.wrap(Buffers.nativeOrder(dispatch_glXGetFBConfigs));
    }
    
    private static native ByteBuffer dispatch_glXGetFBConfigs(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    public static XVisualInfo glXChooseVisual(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribList\" is not a direct buffer");
        }
        final long addressof_glXChooseVisual = GLX.glxProcAddressTable._addressof_glXChooseVisual;
        if (addressof_glXChooseVisual == 0L) {
            throw new GLException("Method \"glXChooseVisual\" not available");
        }
        final ByteBuffer dispatch_glXChooseVisual = dispatch_glXChooseVisual(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXChooseVisual);
        if (dispatch_glXChooseVisual == null) {
            return null;
        }
        return XVisualInfo.create(Buffers.nativeOrder(dispatch_glXChooseVisual));
    }
    
    private static native ByteBuffer dispatch_glXChooseVisual(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    static long glXGetProcAddress(final long n, final String s) {
        if (n == 0L) {
            throw new GLException("Passed null pointer for method \"glXGetProcAddress\"");
        }
        return dispatch_glXGetProcAddress0(s, n);
    }
    
    private static native boolean initializeImpl();
    
    private static native int getCStringLengthImpl(final long p0);
    
    static {
        GLX.glxProcAddressTable = new GLXProcAddressTable(new GLProcAddressResolver());
        if (!initializeImpl()) {
            throw new RuntimeException("Initialization failure");
        }
    }
}
