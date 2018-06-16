// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.egl;

import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import com.jogamp.gluegen.runtime.opengl.GLProcAddressResolver;
import com.jogamp.common.os.DynamicLookupHelper;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;

public class EGL
{
    public static final int KHRONOS_FALSE = 0;
    public static final int KHRONOS_TRUE = 1;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 12470;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 12468;
    public static final int EGL_MULTISAMPLE_RESOLVE_DEFAULT = 12442;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 12472;
    public static final int EGL_GL_COLORSPACE = 12445;
    public static final int EGL_BAD_CURRENT_SURFACE = 12295;
    public static final int EGL_BIND_TO_TEXTURE_RGB = 12345;
    public static final int EGL_TEXTURE_FORMAT = 12416;
    public static final int EGL_COLOR_BUFFER_TYPE = 12351;
    public static final int EGL_ALPHA_FORMAT_NONPRE = 12427;
    public static final int EGL_MULTISAMPLE_RESOLVE_BOX = 12443;
    public static final int EGL_SURFACE_TYPE = 12339;
    public static final int EGL_CONTEXT_OPENGL_DEBUG = 12720;
    public static final int EGL_TRANSPARENT_TYPE = 12340;
    public static final int EGL_BACK_BUFFER = 12420;
    public static final int EGL_LUMINANCE_SIZE = 12349;
    public static final int EGL_MIN_SWAP_INTERVAL = 12347;
    public static final int EGL_CONTEXT_LOST = 12302;
    public static final int EGL_VG_ALPHA_FORMAT = 12424;
    public static final int EGL_SYNC_CL_EVENT_COMPLETE = 12543;
    public static final int EGL_VG_ALPHA_FORMAT_NONPRE = 12427;
    public static final int EGL_SAMPLE_BUFFERS = 12338;
    public static final int EGL_TRANSPARENT_BLUE_VALUE = 12341;
    public static final int EGL_ALPHA_MASK_SIZE = 12350;
    public static final int EGL_NO_RESET_NOTIFICATION = 12734;
    public static final int EGL_GL_RENDERBUFFER = 12473;
    public static final int EGL_SYNC_CL_EVENT = 12542;
    public static final int EGL_TRANSPARENT_RGB = 12370;
    public static final int EGL_WINDOW_BIT = 4;
    public static final int EGL_ALPHA_FORMAT = 12424;
    public static final int EGL_OPENGL_ES2_BIT = 4;
    public static final int EGL_BAD_DISPLAY = 12296;
    public static final int EGL_BAD_PARAMETER = 12300;
    public static final int EGL_BAD_CONTEXT = 12294;
    public static final int EGL_CONTEXT_OPENGL_FORWARD_COMPATIBLE = 12721;
    public static final int EGL_MULTISAMPLE_RESOLVE = 12441;
    public static final int EGL_SLOW_CONFIG = 12368;
    public static final int EGL_FALSE = 0;
    public static final int EGL_SINGLE_BUFFER = 12421;
    public static final int EGL_MULTISAMPLE_RESOLVE_BOX_BIT = 512;
    public static final int EGL_COLORSPACE_LINEAR = 12426;
    public static final int EGL_EXTENSIONS = 12373;
    public static final int EGL_ALPHA_FORMAT_PRE = 12428;
    public static final int EGL_CONFORMANT = 12354;
    public static final int EGL_SIGNALED = 12530;
    public static final int EGL_SAMPLES = 12337;
    public static final int EGL_LUMINANCE_BUFFER = 12431;
    public static final int EGL_LOSE_CONTEXT_ON_RESET = 12735;
    public static final int EGL_VG_COLORSPACE_LINEAR_BIT = 32;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_X = 12467;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 12469;
    public static final int EGL_DEPTH_SIZE = 12325;
    public static final int EGL_MAX_PBUFFER_HEIGHT = 12330;
    public static final int EGL_BAD_CONFIG = 12293;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 12471;
    public static final int EGL_GL_TEXTURE_ZOFFSET = 12477;
    public static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    public static final int EGL_SWAP_BEHAVIOR_PRESERVED_BIT = 1024;
    public static final int EGL_SWAP_BEHAVIOR = 12435;
    public static final int EGL_CONDITION_SATISFIED = 12534;
    public static final int EGL_MIPMAP_TEXTURE = 12418;
    public static final int EGL_WIDTH = 12375;
    public static final int EGL_CL_EVENT_HANDLE = 12444;
    public static final int EGL_READ = 12378;
    public static final int EGL_SYNC_TYPE = 12535;
    public static final int EGL_RENDER_BUFFER = 12422;
    public static final int EGL_TRUE = 1;
    public static final int EGL_BUFFER_PRESERVED = 12436;
    public static final int EGL_OPENGL_ES3_BIT = 64;
    public static final int EGL_MATCH_NATIVE_PIXMAP = 12353;
    public static final int EGL_BUFFER_DESTROYED = 12437;
    public static final int EGL_GL_COLORSPACE_LINEAR = 12426;
    public static final int EGL_COLORSPACE = 12423;
    public static final int EGL_PBUFFER_BIT = 1;
    public static final int EGL_LEVEL = 12329;
    public static final int EGL_UNSIGNALED = 12531;
    public static final int EGL_CONTEXT_OPENGL_PROFILE_MASK = 12541;
    public static final int EGL_STENCIL_SIZE = 12326;
    public static final int EGL_CONTEXT_OPENGL_RESET_NOTIFICATION_STRATEGY = 12733;
    public static final int EGL_GREEN_SIZE = 12323;
    public static final int EGL_TEXTURE_2D = 12383;
    public static final int EGL_BAD_ACCESS = 12290;
    public static final int EGL_DISPLAY_SCALING = 10000;
    public static final int EGL_BAD_SURFACE = 12301;
    public static final int EGL_MIPMAP_LEVEL = 12419;
    public static final int EGL_BLUE_SIZE = 12322;
    public static final int EGL_BAD_ATTRIBUTE = 12292;
    public static final int EGL_CONTEXT_MAJOR_VERSION = 12440;
    public static final int EGL_SYNC_STATUS = 12529;
    public static final int EGL_OPENGL_ES_API = 12448;
    public static final int EGL_CONTEXT_CLIENT_TYPE = 12439;
    public static final int EGL_BAD_NATIVE_PIXMAP = 12298;
    public static final int EGL_TIMEOUT_EXPIRED = 12533;
    public static final int EGL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final int EGL_GL_COLORSPACE_SRGB = 12425;
    public static final int EGL_VERTICAL_RESOLUTION = 12433;
    public static final int EGL_VG_COLORSPACE = 12423;
    public static final int EGL_VG_COLORSPACE_LINEAR = 12426;
    public static final int EGL_CONFIG_CAVEAT = 12327;
    public static final int EGL_PIXEL_ASPECT_RATIO = 12434;
    public static final int EGL_CONTEXT_OPENGL_CORE_PROFILE_BIT = 1;
    public static final int EGL_TEXTURE_TARGET = 12417;
    public static final int EGL_CLIENT_APIS = 12429;
    public static final int EGL_VG_ALPHA_FORMAT_PRE = 12428;
    public static final int EGL_BIND_TO_TEXTURE_RGBA = 12346;
    public static final int EGL_NATIVE_VISUAL_TYPE = 12335;
    public static final int EGL_BAD_MATCH = 12297;
    public static final int EGL_NON_CONFORMANT_CONFIG = 12369;
    public static final int EGL_TEXTURE_RGB = 12381;
    public static final int EGL_RGB_BUFFER = 12430;
    public static final int EGL_CONTEXT_MINOR_VERSION = 12539;
    public static final int EGL_HORIZONTAL_RESOLUTION = 12432;
    public static final int EGL_SYNC_PRIOR_COMMANDS_COMPLETE = 12528;
    public static final int EGL_SYNC_FENCE = 12537;
    public static final int EGL_BAD_ALLOC = 12291;
    public static final int EGL_OPENGL_ES_BIT = 1;
    public static final int EGL_NATIVE_VISUAL_ID = 12334;
    public static final int EGL_MAX_PBUFFER_PIXELS = 12331;
    public static final int EGL_MAX_SWAP_INTERVAL = 12348;
    public static final int EGL_BUFFER_SIZE = 12320;
    public static final int EGL_NATIVE_RENDERABLE = 12333;
    public static final int EGL_VERSION = 12372;
    public static final int EGL_CONFIG_ID = 12328;
    public static final int EGL_LARGEST_PBUFFER = 12376;
    public static final int EGL_TRANSPARENT_GREEN_VALUE = 12342;
    public static final int EGL_MAX_PBUFFER_WIDTH = 12332;
    public static final int EGL_HEIGHT = 12374;
    public static final int EGL_TRANSPARENT_RED_VALUE = 12343;
    public static final int EGL_TEXTURE_RGBA = 12382;
    public static final int EGL_ALPHA_SIZE = 12321;
    public static final int EGL_SYNC_CONDITION = 12536;
    public static final int EGL_IMAGE_PRESERVED = 12498;
    public static final int EGL_GL_TEXTURE_2D = 12465;
    public static final int EGL_BAD_NATIVE_WINDOW = 12299;
    public static final int EGL_PIXMAP_BIT = 2;
    public static final int EGL_COLORSPACE_sRGB = 12425;
    public static final int EGL_VG_ALPHA_FORMAT_PRE_BIT = 64;
    public static final int EGL_OPENVG_API = 12449;
    public static final int EGL_NOT_INITIALIZED = 12289;
    public static final int EGL_VG_COLORSPACE_sRGB = 12425;
    public static final int EGL_RED_SIZE = 12324;
    public static final int EGL_NO_TEXTURE = 12380;
    public static final int EGL_CONTEXT_OPENGL_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int EGL_OPENVG_BIT = 2;
    public static final int EGL_SUCCESS = 12288;
    public static final int EGL_CONTEXT_OPENGL_ROBUST_ACCESS = 12722;
    public static final int EGL_DRAW = 12377;
    public static final int EGL_CORE_NATIVE_ENGINE = 12379;
    public static final int EGL_OPENGL_BIT = 8;
    public static final int EGL_GL_TEXTURE_3D = 12466;
    public static final int EGL_NONE = 12344;
    public static final int EGL_OPENVG_IMAGE = 12438;
    public static final int EGL_RENDERABLE_TYPE = 12352;
    public static final int EGL_VENDOR = 12371;
    public static final int EGL_GL_TEXTURE_LEVEL = 12476;
    public static final int EGL_OPENGL_API = 12450;
    public static final long KHRONOS_BOOLEAN_ENUM_FORCE_SIZE = 2147483647L;
    private static EGLProcAddressTable _table;
    public static final long EGL_DEFAULT_DISPLAY = 0L;
    public static final long EGL_NO_CONTEXT = 0L;
    public static final long EGL_NO_DISPLAY = 0L;
    public static final long EGL_NO_SURFACE = 0L;
    public static final int EGL_DONT_CARE = -1;
    public static final int EGL_UNKNOWN = -1;
    
    public static boolean eglChooseConfig(final long n, final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final int n2, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"configs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"num_config\" is not a direct buffer");
        }
        final long addressof_eglChooseConfig = EGL._table._addressof_eglChooseConfig;
        if (addressof_eglChooseConfig == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglChooseConfig"));
        }
        return dispatch_eglChooseConfig0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_eglChooseConfig);
    }
    
    private static native boolean dispatch_eglChooseConfig0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final long p8);
    
    public static boolean eglCopyBuffers(final long n, final long n2, final long n3) {
        final long addressof_eglCopyBuffers = EGL._table._addressof_eglCopyBuffers;
        if (addressof_eglCopyBuffers == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCopyBuffers"));
        }
        return dispatch_eglCopyBuffers0(n, n2, n3, addressof_eglCopyBuffers);
    }
    
    private static native boolean dispatch_eglCopyBuffers0(final long p0, final long p1, final long p2, final long p3);
    
    public static long eglCreateContext(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateContext = EGL._table._addressof_eglCreateContext;
        if (addressof_eglCreateContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateContext"));
        }
        return dispatch_eglCreateContext0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateContext);
    }
    
    private static native long dispatch_eglCreateContext0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public static long eglCreatePbufferSurface(final long n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePbufferSurface = EGL._table._addressof_eglCreatePbufferSurface;
        if (addressof_eglCreatePbufferSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePbufferSurface"));
        }
        return dispatch_eglCreatePbufferSurface0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePbufferSurface);
    }
    
    private static native long dispatch_eglCreatePbufferSurface0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    public static long eglCreatePixmapSurface(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePixmapSurface = EGL._table._addressof_eglCreatePixmapSurface;
        if (addressof_eglCreatePixmapSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePixmapSurface"));
        }
        return dispatch_eglCreatePixmapSurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePixmapSurface);
    }
    
    private static native long dispatch_eglCreatePixmapSurface0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public static long eglCreateWindowSurface(final long n, final long n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateWindowSurface = EGL._table._addressof_eglCreateWindowSurface;
        if (addressof_eglCreateWindowSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateWindowSurface"));
        }
        return dispatch_eglCreateWindowSurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreateWindowSurface);
    }
    
    private static native long dispatch_eglCreateWindowSurface0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public static boolean eglDestroyContext(final long n, final long n2) {
        final long addressof_eglDestroyContext = EGL._table._addressof_eglDestroyContext;
        if (addressof_eglDestroyContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyContext"));
        }
        return dispatch_eglDestroyContext0(n, n2, addressof_eglDestroyContext);
    }
    
    private static native boolean dispatch_eglDestroyContext0(final long p0, final long p1, final long p2);
    
    public static boolean eglDestroySurface(final long n, final long n2) {
        final long addressof_eglDestroySurface = EGL._table._addressof_eglDestroySurface;
        if (addressof_eglDestroySurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySurface"));
        }
        return dispatch_eglDestroySurface0(n, n2, addressof_eglDestroySurface);
    }
    
    private static native boolean dispatch_eglDestroySurface0(final long p0, final long p1, final long p2);
    
    public static boolean eglGetConfigAttrib(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetConfigAttrib = EGL._table._addressof_eglGetConfigAttrib;
        if (addressof_eglGetConfigAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetConfigAttrib"));
        }
        return dispatch_eglGetConfigAttrib0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetConfigAttrib);
    }
    
    private static native boolean dispatch_eglGetConfigAttrib0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static boolean eglGetConfigs(final long n, final PointerBuffer pointerBuffer, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"configs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"num_config\" is not a direct buffer");
        }
        final long addressof_eglGetConfigs = EGL._table._addressof_eglGetConfigs;
        if (addressof_eglGetConfigs == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetConfigs"));
        }
        return dispatch_eglGetConfigs0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglGetConfigs);
    }
    
    private static native boolean dispatch_eglGetConfigs0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    public static long eglGetCurrentDisplay() {
        final long addressof_eglGetCurrentDisplay = EGL._table._addressof_eglGetCurrentDisplay;
        if (addressof_eglGetCurrentDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentDisplay"));
        }
        return dispatch_eglGetCurrentDisplay0(addressof_eglGetCurrentDisplay);
    }
    
    private static native long dispatch_eglGetCurrentDisplay0(final long p0);
    
    public static long eglGetCurrentSurface(final int n) {
        final long addressof_eglGetCurrentSurface = EGL._table._addressof_eglGetCurrentSurface;
        if (addressof_eglGetCurrentSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentSurface"));
        }
        return dispatch_eglGetCurrentSurface0(n, addressof_eglGetCurrentSurface);
    }
    
    private static native long dispatch_eglGetCurrentSurface0(final int p0, final long p1);
    
    public static long eglGetDisplay(final long n) {
        final long addressof_eglGetDisplay = EGL._table._addressof_eglGetDisplay;
        if (addressof_eglGetDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetDisplay"));
        }
        return dispatch_eglGetDisplay0(n, addressof_eglGetDisplay);
    }
    
    private static native long dispatch_eglGetDisplay0(final long p0, final long p1);
    
    public static int eglGetError() {
        final long addressof_eglGetError = EGL._table._addressof_eglGetError;
        if (addressof_eglGetError == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetError"));
        }
        return dispatch_eglGetError0(addressof_eglGetError);
    }
    
    private static native int dispatch_eglGetError0(final long p0);
    
    public static boolean eglInitialize(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"major\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("Argument \"minor\" is not a direct buffer");
        }
        final long addressof_eglInitialize = EGL._table._addressof_eglInitialize;
        if (addressof_eglInitialize == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglInitialize"));
        }
        return dispatch_eglInitialize0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_eglInitialize);
    }
    
    private static native boolean dispatch_eglInitialize0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static boolean eglMakeCurrent(final long n, final long n2, final long n3, final long n4) {
        final long addressof_eglMakeCurrent = EGL._table._addressof_eglMakeCurrent;
        if (addressof_eglMakeCurrent == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglMakeCurrent"));
        }
        return dispatch_eglMakeCurrent0(n, n2, n3, n4, addressof_eglMakeCurrent);
    }
    
    private static native boolean dispatch_eglMakeCurrent0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    public static boolean eglQueryContext(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQueryContext = EGL._table._addressof_eglQueryContext;
        if (addressof_eglQueryContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryContext"));
        }
        return dispatch_eglQueryContext0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQueryContext);
    }
    
    private static native boolean dispatch_eglQueryContext0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static String eglQueryString(final long n, final int n2) {
        final long addressof_eglQueryString = EGL._table._addressof_eglQueryString;
        if (addressof_eglQueryString == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryString"));
        }
        return dispatch_eglQueryString0(n, n2, addressof_eglQueryString);
    }
    
    private static native String dispatch_eglQueryString0(final long p0, final int p1, final long p2);
    
    public static boolean eglQuerySurface(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglQuerySurface = EGL._table._addressof_eglQuerySurface;
        if (addressof_eglQuerySurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQuerySurface"));
        }
        return dispatch_eglQuerySurface0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglQuerySurface);
    }
    
    private static native boolean dispatch_eglQuerySurface0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static boolean eglSwapBuffers(final long n, final long n2) {
        final long addressof_eglSwapBuffers = EGL._table._addressof_eglSwapBuffers;
        if (addressof_eglSwapBuffers == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapBuffers"));
        }
        return dispatch_eglSwapBuffers0(n, n2, addressof_eglSwapBuffers);
    }
    
    private static native boolean dispatch_eglSwapBuffers0(final long p0, final long p1, final long p2);
    
    public static boolean eglTerminate(final long n) {
        final long addressof_eglTerminate = EGL._table._addressof_eglTerminate;
        if (addressof_eglTerminate == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglTerminate"));
        }
        return dispatch_eglTerminate0(n, addressof_eglTerminate);
    }
    
    private static native boolean dispatch_eglTerminate0(final long p0, final long p1);
    
    public static boolean eglWaitGL() {
        final long addressof_eglWaitGL = EGL._table._addressof_eglWaitGL;
        if (addressof_eglWaitGL == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitGL"));
        }
        return dispatch_eglWaitGL0(addressof_eglWaitGL);
    }
    
    private static native boolean dispatch_eglWaitGL0(final long p0);
    
    public static boolean eglWaitNative(final int n) {
        final long addressof_eglWaitNative = EGL._table._addressof_eglWaitNative;
        if (addressof_eglWaitNative == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitNative"));
        }
        return dispatch_eglWaitNative0(n, addressof_eglWaitNative);
    }
    
    private static native boolean dispatch_eglWaitNative0(final int p0, final long p1);
    
    public static boolean eglBindTexImage(final long n, final long n2, final int n3) {
        final long addressof_eglBindTexImage = EGL._table._addressof_eglBindTexImage;
        if (addressof_eglBindTexImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglBindTexImage"));
        }
        return dispatch_eglBindTexImage0(n, n2, n3, addressof_eglBindTexImage);
    }
    
    private static native boolean dispatch_eglBindTexImage0(final long p0, final long p1, final int p2, final long p3);
    
    public static boolean eglReleaseTexImage(final long n, final long n2, final int n3) {
        final long addressof_eglReleaseTexImage = EGL._table._addressof_eglReleaseTexImage;
        if (addressof_eglReleaseTexImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglReleaseTexImage"));
        }
        return dispatch_eglReleaseTexImage0(n, n2, n3, addressof_eglReleaseTexImage);
    }
    
    private static native boolean dispatch_eglReleaseTexImage0(final long p0, final long p1, final int p2, final long p3);
    
    public static boolean eglSurfaceAttrib(final long n, final long n2, final int n3, final int n4) {
        final long addressof_eglSurfaceAttrib = EGL._table._addressof_eglSurfaceAttrib;
        if (addressof_eglSurfaceAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSurfaceAttrib"));
        }
        return dispatch_eglSurfaceAttrib0(n, n2, n3, n4, addressof_eglSurfaceAttrib);
    }
    
    private static native boolean dispatch_eglSurfaceAttrib0(final long p0, final long p1, final int p2, final int p3, final long p4);
    
    public static boolean eglSwapInterval(final long n, final int n2) {
        final long addressof_eglSwapInterval = EGL._table._addressof_eglSwapInterval;
        if (addressof_eglSwapInterval == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglSwapInterval"));
        }
        return dispatch_eglSwapInterval0(n, n2, addressof_eglSwapInterval);
    }
    
    private static native boolean dispatch_eglSwapInterval0(final long p0, final int p1, final long p2);
    
    public static boolean eglBindAPI(final int n) {
        final long addressof_eglBindAPI = EGL._table._addressof_eglBindAPI;
        if (addressof_eglBindAPI == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglBindAPI"));
        }
        return dispatch_eglBindAPI0(n, addressof_eglBindAPI);
    }
    
    private static native boolean dispatch_eglBindAPI0(final int p0, final long p1);
    
    public static int eglQueryAPI() {
        final long addressof_eglQueryAPI = EGL._table._addressof_eglQueryAPI;
        if (addressof_eglQueryAPI == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglQueryAPI"));
        }
        return dispatch_eglQueryAPI0(addressof_eglQueryAPI);
    }
    
    private static native int dispatch_eglQueryAPI0(final long p0);
    
    public static long eglCreatePbufferFromClientBuffer(final long n, final int n2, final Buffer buffer, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"buffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePbufferFromClientBuffer = EGL._table._addressof_eglCreatePbufferFromClientBuffer;
        if (addressof_eglCreatePbufferFromClientBuffer == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePbufferFromClientBuffer"));
        }
        return dispatch_eglCreatePbufferFromClientBuffer0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_eglCreatePbufferFromClientBuffer);
    }
    
    private static native long dispatch_eglCreatePbufferFromClientBuffer0(final long p0, final int p1, final Object p2, final int p3, final long p4, final Object p5, final int p6, final long p7);
    
    public static boolean eglReleaseThread() {
        final long addressof_eglReleaseThread = EGL._table._addressof_eglReleaseThread;
        if (addressof_eglReleaseThread == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglReleaseThread"));
        }
        return dispatch_eglReleaseThread0(addressof_eglReleaseThread);
    }
    
    private static native boolean dispatch_eglReleaseThread0(final long p0);
    
    public static boolean eglWaitClient() {
        final long addressof_eglWaitClient = EGL._table._addressof_eglWaitClient;
        if (addressof_eglWaitClient == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitClient"));
        }
        return dispatch_eglWaitClient0(addressof_eglWaitClient);
    }
    
    private static native boolean dispatch_eglWaitClient0(final long p0);
    
    public static long eglGetCurrentContext() {
        final long addressof_eglGetCurrentContext = EGL._table._addressof_eglGetCurrentContext;
        if (addressof_eglGetCurrentContext == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetCurrentContext"));
        }
        return dispatch_eglGetCurrentContext0(addressof_eglGetCurrentContext);
    }
    
    private static native long dispatch_eglGetCurrentContext0(final long p0);
    
    public static ByteBuffer eglCreateSync(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateSync = EGL._table._addressof_eglCreateSync;
        if (addressof_eglCreateSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateSync"));
        }
        final ByteBuffer dispatch_eglCreateSync0 = dispatch_eglCreateSync0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreateSync);
        if (dispatch_eglCreateSync0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateSync0);
        return dispatch_eglCreateSync0;
    }
    
    private static native ByteBuffer dispatch_eglCreateSync0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    public static boolean eglDestroySync(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglDestroySync = EGL._table._addressof_eglDestroySync;
        if (addressof_eglDestroySync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroySync"));
        }
        return dispatch_eglDestroySync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroySync);
    }
    
    private static native boolean dispatch_eglDestroySync0(final long p0, final Object p1, final int p2, final long p3);
    
    public static int eglClientWaitSync(final long n, final Buffer buffer, final int n2, final long n3) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglClientWaitSync = EGL._table._addressof_eglClientWaitSync;
        if (addressof_eglClientWaitSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglClientWaitSync"));
        }
        return dispatch_eglClientWaitSync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, addressof_eglClientWaitSync);
    }
    
    private static native int dispatch_eglClientWaitSync0(final long p0, final Object p1, final int p2, final int p3, final long p4, final long p5);
    
    public static boolean eglGetSyncAttrib(final long n, final Buffer buffer, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_eglGetSyncAttrib = EGL._table._addressof_eglGetSyncAttrib;
        if (addressof_eglGetSyncAttrib == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetSyncAttrib"));
        }
        return dispatch_eglGetSyncAttrib0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglGetSyncAttrib);
    }
    
    private static native boolean dispatch_eglGetSyncAttrib0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    public static ByteBuffer eglCreateImage(final long n, final long n2, final int n3, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"buffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreateImage = EGL._table._addressof_eglCreateImage;
        if (addressof_eglCreateImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreateImage"));
        }
        final ByteBuffer dispatch_eglCreateImage0 = dispatch_eglCreateImage0(n, n2, n3, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreateImage);
        if (dispatch_eglCreateImage0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_eglCreateImage0);
        return dispatch_eglCreateImage0;
    }
    
    private static native ByteBuffer dispatch_eglCreateImage0(final long p0, final long p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    public static boolean eglDestroyImage(final long n, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"image\" is not a direct buffer");
        }
        final long addressof_eglDestroyImage = EGL._table._addressof_eglDestroyImage;
        if (addressof_eglDestroyImage == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglDestroyImage"));
        }
        return dispatch_eglDestroyImage0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_eglDestroyImage);
    }
    
    private static native boolean dispatch_eglDestroyImage0(final long p0, final Object p1, final int p2, final long p3);
    
    public static long eglGetPlatformDisplay(final int n, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_display\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglGetPlatformDisplay = EGL._table._addressof_eglGetPlatformDisplay;
        if (addressof_eglGetPlatformDisplay == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglGetPlatformDisplay"));
        }
        return dispatch_eglGetPlatformDisplay0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglGetPlatformDisplay);
    }
    
    private static native long dispatch_eglGetPlatformDisplay0(final int p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public static long eglCreatePlatformWindowSurface(final long n, final long n2, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_window\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformWindowSurface = EGL._table._addressof_eglCreatePlatformWindowSurface;
        if (addressof_eglCreatePlatformWindowSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformWindowSurface"));
        }
        return dispatch_eglCreatePlatformWindowSurface0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreatePlatformWindowSurface);
    }
    
    private static native long dispatch_eglCreatePlatformWindowSurface0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    public static long eglCreatePlatformPixmapSurface(final long n, final long n2, final Buffer buffer, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"native_pixmap\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new RuntimeException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_eglCreatePlatformPixmapSurface = EGL._table._addressof_eglCreatePlatformPixmapSurface;
        if (addressof_eglCreatePlatformPixmapSurface == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglCreatePlatformPixmapSurface"));
        }
        return dispatch_eglCreatePlatformPixmapSurface0(n, n2, buffer, Buffers.getDirectBufferByteOffset(buffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_eglCreatePlatformPixmapSurface);
    }
    
    private static native long dispatch_eglCreatePlatformPixmapSurface0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    public static boolean eglWaitSync(final long n, final Buffer buffer, final int n2) {
        if (!Buffers.isDirect(buffer)) {
            throw new RuntimeException("Argument \"sync\" is not a direct buffer");
        }
        final long addressof_eglWaitSync = EGL._table._addressof_eglWaitSync;
        if (addressof_eglWaitSync == 0L) {
            throw new UnsupportedOperationException(String.format("Method \"%s\" not available", "eglWaitSync"));
        }
        return dispatch_eglWaitSync0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, addressof_eglWaitSync);
    }
    
    private static native boolean dispatch_eglWaitSync0(final long p0, final Object p1, final int p2, final int p3, final long p4);
    
    protected static boolean resetProcAddressTable(final DynamicLookupHelper dynamicLookupHelper) {
        EGL._table.reset(dynamicLookupHelper);
        return 0L != EGL._table._addressof_eglGetDisplay && 0L != EGL._table._addressof_eglInitialize;
    }
    
    public static void eglGetConfigAttributes(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (intBuffer == null || intBuffer2 == null) {
            throw new RuntimeException("arrays buffers are null");
        }
        if (!Buffers.isDirect(intBuffer) || !Buffers.isDirect(intBuffer2)) {
            throw new RuntimeException("arrays buffers are not direct");
        }
        if (intBuffer.remaining() > intBuffer2.remaining()) {
            throw new RuntimeException("not enough values " + intBuffer2 + " for attributes " + intBuffer);
        }
        dispatch_eglGetConfigAttributes(n, n2, intBuffer.remaining(), intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), EGL._table._addressof_eglGetConfigAttrib);
    }
    
    private static native void dispatch_eglGetConfigAttributes(final long p0, final long p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    static {
        EGL._table = new EGLProcAddressTable(new GLProcAddressResolver());
    }
}
