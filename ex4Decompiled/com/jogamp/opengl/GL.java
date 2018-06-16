// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;

public interface GL extends GLBase
{
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_ONE_MINUS_DST_COLOR = 775;
    public static final int GL_RENDERBUFFER_SAMPLES = 36011;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_INVALID_OPERATION = 1282;
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 33778;
    public static final int GL_RGB32F = 34837;
    public static final int GL_DYNAMIC_DRAW = 35048;
    public static final int GL_DEPTH_BITS = 3414;
    public static final int GL_UNKNOWN_CONTEXT_RESET = 33365;
    public static final int GL_STENCIL_BUFFER_BIT = 1024;
    public static final int GL_MAX_SAMPLES = 36183;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    public static final int GL_FRONT_FACE = 2886;
    public static final int GL_TEXTURE0 = 33984;
    public static final int GL_TEXTURE4 = 33988;
    public static final int GL_TEXTURE3 = 33987;
    public static final int GL_TEXTURE2 = 33986;
    public static final int GL_TEXTURE1 = 33985;
    public static final int GL_TEXTURE8 = 33992;
    public static final int GL_TEXTURE7 = 33991;
    public static final int GL_TEXTURE6 = 33990;
    public static final int GL_TEXTURE5 = 33989;
    public static final int GL_TEXTURE9 = 33993;
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 33779;
    public static final int GL_RGB5_A1 = 32855;
    public static final int GL_INCR = 7682;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_CULL_FACE_MODE = 2885;
    public static final int GL_FASTEST = 4353;
    public static final int GL_R16F = 33325;
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 33901;
    public static final int GL_SHORT = 5122;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_DST_COLOR = 774;
    public static final int GL_LESS = 513;
    public static final int GL_ONE = 1;
    public static final int GL_SRGB = 35904;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_FIXED = 5132;
    public static final int GL_RGBA = 6408;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static final int GL_EXTENSIONS = 7939;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_BYTE = 5120;
    public static final int GL_GENERATE_MIPMAP_HINT = 33170;
    public static final int GL_EQUAL = 514;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_POLYGON_OFFSET_UNITS = 10752;
    public static final int GL_RGBA4 = 32854;
    public static final int GL_BLEND_EQUATION_RGB = 32777;
    public static final int GL_RGB16F = 34843;
    public static final int GL_SAMPLE_COVERAGE_INVERT = 32939;
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT = 34047;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    public static final int GL_VERSION = 7938;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV_EXT = 33638;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_DITHER = 3024;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 36057;
    public static final int GL_INVALID_ENUM = 1280;
    public static final int GL_TEXTURE_CUBE_MAP = 34067;
    public static final int GL_ALWAYS = 519;
    public static final int GL_INVERT = 5386;
    public static final int GL_READ_FRAMEBUFFER_BINDING = 36010;
    public static final int GL_LUMINANCE = 6409;
    public static final int GL_BLEND_EQUATION_ALPHA = 34877;
    public static final int GL_DEPTH24_STENCIL8 = 35056;
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    public static final int GL_RG8 = 33323;
    public static final int GL_LINEAR_MIPMAP_NEAREST = 9985;
    public static final int GL_REPLACE = 7681;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_SRGB8_ALPHA8 = 35907;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_RENDERBUFFER_WIDTH = 36162;
    public static final int GL_NEVER = 512;
    public static final int GL_FALSE = 0;
    public static final int GL_DEPTH_COMPONENT16 = 33189;
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    public static final int GL_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_PACK_ALIGNMENT = 3333;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    public static final int GL_STENCIL_INDEX1 = 36166;
    public static final int GL_BUFFER_MAPPED = 35004;
    public static final int GL_COLOR_CLEAR_VALUE = 3106;
    public static final int GL_RGBA16F = 34842;
    public static final int GL_RGBA8 = 32856;
    public static final int GL_BLEND_EQUATION = 32777;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    public static final int GL_STENCIL_WRITEMASK = 2968;
    public static final int GL_LUMINANCE32F = 34840;
    public static final int GL_UNSIGNED_INT = 5125;
    public static final int GL_DEPTH_ATTACHMENT = 36096;
    public static final int GL_RGB10_A2 = 32857;
    public static final int GL_SAMPLE_COVERAGE_VALUE = 32938;
    public static final int GL_DRAW_FRAMEBUFFER = 36009;
    public static final int GL_TRUE = 1;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_BLEND_SRC_RGB = 32969;
    public static final int GL_DEPTH_FUNC = 2932;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static final int GL_MAX_RENDERBUFFER_SIZE = 34024;
    public static final int GL_LINEAR = 9729;
    public static final int GL_SAMPLE_COVERAGE = 32928;
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 33635;
    public static final int GL_BACK = 1029;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP = 34068;
    public static final int GL_READ_FRAMEBUFFER = 36008;
    public static final int GL_SAMPLE_BUFFERS = 32936;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_EXT = 33637;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_COLOR_ATTACHMENT0 = 36064;
    public static final int GL_LUMINANCE_ALPHA16F = 34847;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 34070;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 34074;
    public static final int GL_INCR_WRAP = 34055;
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 34072;
    public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
    public static final int GL_BLUE_BITS = 3412;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_RGB = 6407;
    public static final int GL_RG16F = 33327;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 34076;
    public static final int GL_RENDERBUFFER_BINDING = 36007;
    public static final int GL_RESET_NOTIFICATION_STRATEGY = 33366;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_ALPHA32F = 34838;
    public static final int GL_POLYGON_OFFSET_FACTOR = 32824;
    public static final int GL_BGR = 32992;
    public static final int GL_KEEP = 7680;
    public static final int GL_BGRA = 32993;
    public static final int GL_COLOR_WRITEMASK = 3107;
    public static final int GL_MIRRORED_REPEAT = 33648;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
    public static final int GL_SCISSOR_BOX = 3088;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 34069;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 34071;
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 34073;
    public static final int GL_DECR = 7683;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_LINES = 1;
    public static final int GL_ALPHA_BITS = 3413;
    public static final int GL_BLEND_SRC_ALPHA = 32971;
    public static final int GL_SRGB_ALPHA = 35906;
    public static final int GL_FRAMEBUFFER_SRGB = 36281;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
    public static final int GL_RENDERBUFFER_HEIGHT = 36163;
    public static final int GL_POLYGON_OFFSET_FILL = 32823;
    public static final int GL_LUMINANCE8_ALPHA8 = 32837;
    public static final int GL_RGB565 = 36194;
    public static final int GL_LINE_WIDTH = 2849;
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
    public static final int GL_ALPHA8 = 32828;
    public static final int GL_CW = 2304;
    public static final int GL_LEQUAL = 515;
    public static final int GL_LUMINANCE8 = 32832;
    public static final int GL_GREEN_BITS = 3411;
    public static final int GL_RED_BITS = 3410;
    public static final int GL_RGB8 = 32849;
    public static final int GL_NO_ERROR = 0;
    public static final int GL_STENCIL_VALUE_MASK = 2963;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_DEPTH_CLEAR_VALUE = 2931;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_ACTIVE_TEXTURE = 34016;
    public static final int GL_INNOCENT_CONTEXT_RESET = 33364;
    public static final int GL_BUFFER_USAGE = 34661;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 34466;
    public static final int GL_LINE_LOOP = 2;
    public static final int GL_BUFFER_SIZE = 34660;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    public static final int GL_TEXTURE20 = 34004;
    public static final int GL_MAX_VIEWPORT_DIMS = 3386;
    public static final int GL_DECR_WRAP = 34056;
    public static final int GL_TEXTURE19 = 34003;
    public static final int GL_TEXTURE18 = 34002;
    public static final int GL_TEXTURE17 = 34001;
    public static final int GL_TEXTURE16 = 34000;
    public static final int GL_TEXTURE15 = 33999;
    public static final int GL_TEXTURE14 = 33998;
    public static final int GL_TEXTURE13 = 33997;
    public static final int GL_TEXTURE12 = 33996;
    public static final int GL_TEXTURE11 = 33995;
    public static final int GL_TEXTURE10 = 33994;
    public static final int GL_TEXTURE31 = 34015;
    public static final int GL_TEXTURE30 = 34014;
    public static final int GL_R8 = 33321;
    public static final int GL_ALPHA16F = 34844;
    public static final int GL_TEXTURE29 = 34013;
    public static final int GL_TEXTURE28 = 34012;
    public static final int GL_TEXTURE27 = 34011;
    public static final int GL_TEXTURE26 = 34010;
    public static final int GL_TEXTURE25 = 34009;
    public static final int GL_TEXTURE24 = 34008;
    public static final int GL_TEXTURE23 = 34007;
    public static final int GL_TEXTURE22 = 34006;
    public static final int GL_TEXTURE21 = 34005;
    public static final int GL_STENCIL_FAIL = 2964;
    public static final int GL_FRONT = 1028;
    public static final int GL_SUBPIXEL_BITS = 3408;
    public static final int GL_ARRAY_BUFFER_BINDING = 34964;
    public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static final int GL_VENDOR = 7936;
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
    public static final int GL_CCW = 2305;
    public static final int GL_STENCIL_REF = 2967;
    public static final int GL_TEXTURE = 5890;
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 2966;
    public static final int GL_FRONT_AND_BACK = 1032;
    public static final int GL_LUMINANCE_ALPHA32F = 34841;
    public static final int GL_LUMINANCE_ALPHA = 6410;
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_FRAMEBUFFER = 36160;
    public static final int GL_ARRAY_BUFFER = 34962;
    public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 37167;
    public static final int GL_OUT_OF_MEMORY = 1285;
    public static final int GL_NONE = 0;
    public static final int GL_ALPHA = 6406;
    public static final int GL_DONT_CARE = 4352;
    public static final int GL_COMPRESSED_RGB_S3TC_DXT1_EXT = 33776;
    public static final int GL_LUMINANCE16F = 34846;
    public static final int GL_DEPTH_WRITEMASK = 2930;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static final int GL_REPEAT = 10497;
    public static final int GL_RENDERER = 7937;
    public static final int GL_STENCIL_INDEX8 = 36168;
    public static final int GL_STENCIL_TEST = 2960;
    public static final int GL_WRITE_ONLY = 35001;
    public static final int GL_STATIC_DRAW = 35044;
    public static final int GL_POINTS = 0;
    public static final int GL_DEPTH_COMPONENT32 = 33191;
    public static final int GL_DEPTH_RANGE = 2928;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static final int GL_SAMPLES = 32937;
    public static final int GL_TEXTURE_BINDING_2D = 32873;
    public static final int GL_DEPTH_COMPONENT24 = 33190;
    public static final int GL_STENCIL_BITS = 3415;
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    public static final int GL_BLEND_DST_RGB = 32968;
    public static final int GL_BLEND = 3042;
    public static final int GL_NO_RESET_NOTIFICATION = 33377;
    public static final int GL_DEPTH_STENCIL = 34041;
    public static final int GL_BUFFER_ACCESS = 35003;
    public static final int GL_NEAREST = 9728;
    public static final int GL_RENDERBUFFER = 36161;
    public static final int GL_CULL_FACE = 2884;
    public static final int GL_GUILTY_CONTEXT_RESET = 33363;
    public static final int GL_VIEWPORT = 2978;
    public static final int GL_LOSE_CONTEXT_ON_RESET = 33362;
    public static final int GL_GREATER = 516;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    public static final int GL_STENCIL_INDEX4 = 36167;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_RENDERBUFFER_RED_SIZE = 36176;
    public static final int GL_SRC_ALPHA_SATURATE = 776;
    public static final int GL_UNPACK_ALIGNMENT = 3317;
    public static final int GL_LUMINANCE4_ALPHA4 = 32835;
    public static final int GL_RG32F = 33328;
    public static final int GL_R32F = 33326;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 32926;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_UNSIGNED_INT_24_8 = 34042;
    public static final int GL_TEXTURE_MAX_ANISOTROPY_EXT = 34046;
    public static final int GL_STENCIL_CLEAR_VALUE = 2961;
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 33777;
    public static final int GL_ZERO = 0;
    public static final int GL_CLAMP_TO_EDGE = 33071;
    public static final int GL_BLEND_DST_ALPHA = 32970;
    public static final int GL_RGB10 = 32850;
    public static final int GL_BGRA8 = 37793;
    public static final int GL_FLOAT = 5126;
    public static final int GL_RGBA32F = 34836;
    public static final int GL_STENCIL_ATTACHMENT = 36128;
    public static final int GL_INVALID_VALUE = 1281;
    public static final int GL_BUFFER_MAP_POINTER = 35005;
    public static final int GL_GEQUAL = 518;
    public static final int GL_NICEST = 4354;
    public static final int GL_STENCIL_FUNC = 2962;
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 34467;
    public static final int GL_AND = 5377;
    public static final int GL_AND_INVERTED = 5380;
    public static final int GL_AND_REVERSE = 5378;
    public static final int GL_BLEND_DST = 3040;
    public static final int GL_BLEND_SRC = 3041;
    public static final int GL_CLEAR = 5376;
    public static final int GL_COLOR_LOGIC_OP = 3058;
    public static final int GL_COPY = 5379;
    public static final int GL_COPY_INVERTED = 5388;
    public static final int GL_EQUIV = 5385;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_LINE_SMOOTH_HINT = 3154;
    public static final int GL_LOGIC_OP_MODE = 3056;
    public static final int GL_MULTISAMPLE = 32925;
    public static final int GL_NAND = 5390;
    public static final int GL_NOOP = 5381;
    public static final int GL_NOR = 5384;
    public static final int GL_OR = 5383;
    public static final int GL_OR_INVERTED = 5389;
    public static final int GL_OR_REVERSE = 5387;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
    public static final int GL_POINT_SIZE = 2833;
    public static final int GL_SAMPLE_ALPHA_TO_ONE = 32927;
    public static final int GL_SET = 5391;
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
    public static final int GL_XOR = 5382;
    public static final int GL_HALF_FLOAT = 5131;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS = 36058;
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
    
    void glActiveTexture(final int p0);
    
    void glBindBuffer(final int p0, final int p1);
    
    void glBindFramebuffer(final int p0, final int p1);
    
    void glBindRenderbuffer(final int p0, final int p1);
    
    void glBindTexture(final int p0, final int p1);
    
    void glBlendEquation(final int p0);
    
    void glBlendEquationSeparate(final int p0, final int p1);
    
    void glBlendFunc(final int p0, final int p1);
    
    void glBlendFuncSeparate(final int p0, final int p1, final int p2, final int p3);
    
    void glBufferData(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glBufferSubData(final int p0, final long p1, final long p2, final Buffer p3);
    
    int glCheckFramebufferStatus(final int p0);
    
    void glClear(final int p0);
    
    void glClearColor(final float p0, final float p1, final float p2, final float p3);
    
    void glClearDepthf(final float p0);
    
    void glClearStencil(final int p0);
    
    void glColorMask(final boolean p0, final boolean p1, final boolean p2, final boolean p3);
    
    void glCompressedTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glCompressedTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    void glCompressedTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glCompressedTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glCopyTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glCopyTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glCullFace(final int p0);
    
    void glDeleteBuffers(final int p0, final IntBuffer p1);
    
    void glDeleteBuffers(final int p0, final int[] p1, final int p2);
    
    void glDeleteFramebuffers(final int p0, final IntBuffer p1);
    
    void glDeleteFramebuffers(final int p0, final int[] p1, final int p2);
    
    void glDeleteRenderbuffers(final int p0, final IntBuffer p1);
    
    void glDeleteRenderbuffers(final int p0, final int[] p1, final int p2);
    
    void glDeleteTextures(final int p0, final IntBuffer p1);
    
    void glDeleteTextures(final int p0, final int[] p1, final int p2);
    
    void glDepthFunc(final int p0);
    
    void glDepthMask(final boolean p0);
    
    void glDepthRangef(final float p0, final float p1);
    
    void glDisable(final int p0);
    
    void glDrawArrays(final int p0, final int p1, final int p2);
    
    void glDrawElements(final int p0, final int p1, final int p2, final long p3);
    
    void glEnable(final int p0);
    
    void glFinish();
    
    void glFlush();
    
    void glFramebufferRenderbuffer(final int p0, final int p1, final int p2, final int p3);
    
    void glFramebufferTexture2D(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFrontFace(final int p0);
    
    void glGenBuffers(final int p0, final IntBuffer p1);
    
    void glGenBuffers(final int p0, final int[] p1, final int p2);
    
    void glGenerateMipmap(final int p0);
    
    void glGenFramebuffers(final int p0, final IntBuffer p1);
    
    void glGenFramebuffers(final int p0, final int[] p1, final int p2);
    
    void glGenRenderbuffers(final int p0, final IntBuffer p1);
    
    void glGenRenderbuffers(final int p0, final int[] p1, final int p2);
    
    void glGenTextures(final int p0, final IntBuffer p1);
    
    void glGenTextures(final int p0, final int[] p1, final int p2);
    
    void glGetBooleanv(final int p0, final ByteBuffer p1);
    
    void glGetBooleanv(final int p0, final byte[] p1, final int p2);
    
    void glGetBufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetBufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    int glGetError();
    
    void glGetFloatv(final int p0, final FloatBuffer p1);
    
    void glGetFloatv(final int p0, final float[] p1, final int p2);
    
    void glGetFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetIntegerv(final int p0, final IntBuffer p1);
    
    void glGetIntegerv(final int p0, final int[] p1, final int p2);
    
    void glGetRenderbufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetRenderbufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    String glGetString(final int p0);
    
    void glGetTexParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetTexParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetTexParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glHint(final int p0, final int p1);
    
    boolean glIsBuffer(final int p0);
    
    boolean glIsEnabled(final int p0);
    
    boolean glIsFramebuffer(final int p0);
    
    boolean glIsRenderbuffer(final int p0);
    
    boolean glIsTexture(final int p0);
    
    void glLineWidth(final float p0);
    
    void glPixelStorei(final int p0, final int p1);
    
    void glPolygonOffset(final float p0, final float p1);
    
    void glReadPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glReadPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glRenderbufferStorage(final int p0, final int p1, final int p2, final int p3);
    
    void glSampleCoverage(final float p0, final boolean p1);
    
    void glScissor(final int p0, final int p1, final int p2, final int p3);
    
    void glStencilFunc(final int p0, final int p1, final int p2);
    
    void glStencilMask(final int p0);
    
    void glStencilOp(final int p0, final int p1, final int p2);
    
    void glTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glTexImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glTexParameterf(final int p0, final int p1, final float p2);
    
    void glTexParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glTexParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glTexParameteri(final int p0, final int p1, final int p2);
    
    void glTexParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glTexSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glViewport(final int p0, final int p1, final int p2, final int p3);
    
    void glTexStorage1D(final int p0, final int p1, final int p2, final int p3);
    
    void glTexStorage2D(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glTexStorage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glTextureStorage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glTextureStorage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glTextureStorage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    ByteBuffer glMapBuffer(final int p0, final int p1);
    
    boolean glUnmapBuffer(final int p0);
    
    void glRenderbufferStorageMultisample(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    ByteBuffer glMapBufferRange(final int p0, final long p1, final long p2, final int p3);
    
    void glFlushMappedBufferRange(final int p0, final long p1, final long p2);
    
    int glGetGraphicsResetStatus();
    
    void glReadnPixels(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glGetnUniformfv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetnUniformfv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetnUniformiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetnUniformiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
}
