// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public interface GLES1 extends GL2ES1
{
    public static final int GL_MAX_SAMPLES_EXT = 36183;
    public static final int GL_TEXTURE_GEN_MODE = 9472;
    public static final int GL_TEXTURE_FORMAT_QCOM = 35798;
    public static final int GL_WEIGHT_ARRAY_OES = 34477;
    public static final int GL_NORMAL_MAP = 34065;
    public static final int GL_POINT_SIZE_ARRAY_POINTER_OES = 35212;
    public static final int GL_POINT_SIZE_ARRAY_BUFFER_BINDING_OES = 35743;
    public static final int GL_TEXTURE_EXTERNAL_OES = 36197;
    public static final int GL_MAX_VERTEX_UNITS_OES = 34468;
    public static final int GL_RENDERBUFFER_SAMPLES_IMG = 37171;
    public static final int GL_MULTISAMPLE_BUFFER_BIT3_QCOM = 134217728;
    public static final int GL_COMPRESSED_RGB_PVRTC_2BPPV1_IMG = 35841;
    public static final int GL_STENCIL_BUFFER_BIT3_QCOM = 524288;
    public static final int GL_COMPRESSED_RGBA_PVRTC_2BPPV1_IMG = 35843;
    public static final int GL_CURRENT_PALETTE_MATRIX_OES = 34883;
    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_OES = 34889;
    public static final int GL_CLIP_PLANE5_IMG = 12293;
    public static final int GL_STENCIL_BUFFER_BIT2_QCOM = 262144;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_OES = 34974;
    public static final long GL_MULTISAMPLE_BUFFER_BIT7_QCOM = 2147483648L;
    public static final int GL_DEPTH_BUFFER_BIT6_QCOM = 16384;
    public static final int GL_COLOR_BUFFER_BIT7_QCOM = 128;
    public static final int GL_TEXTURE_TYPE_QCOM = 35799;
    public static final int GL_TEXTURE_IMAGE_VALID_QCOM = 35800;
    public static final int GL_COLOR_BUFFER_BIT6_QCOM = 64;
    public static final int GL_DEPTH_BUFFER_BIT5_QCOM = 8192;
    public static final int GL_STATE_RESTORE = 35804;
    public static final int GL_REFLECTION_MAP = 34066;
    public static final int GL_VERTEX_ARRAY_BINDING_OES = 34229;
    public static final int GL_COLOR_BUFFER_BIT0_QCOM = 1;
    public static final int GL_DEPTH_BUFFER_BIT3_QCOM = 2048;
    public static final int GL_MAX_PALETTE_MATRICES_OES = 34882;
    public static final int GL_TEXTURE_GEN_STR = 36192;
    public static final int GL_COLOR_BUFFER_BIT2_QCOM = 4;
    public static final int GL_DEPTH_BUFFER_BIT1_QCOM = 512;
    public static final int GL_MULTISAMPLE_BUFFER_BIT2_QCOM = 67108864;
    public static final int GL_MULTISAMPLE_BUFFER_BIT4_QCOM = 268435456;
    public static final int GL_TEXTURE_WIDTH_QCOM = 35794;
    public static final int GL_ATC_RGBA_INTERPOLATED_ALPHA_AMD = 34798;
    public static final int GL_COLOR_BUFFER_BIT4_QCOM = 16;
    public static final int GL_ATC_RGBA_EXPLICIT_ALPHA_AMD = 35987;
    public static final int GL_CLIP_PLANE4_IMG = 12292;
    public static final int GL_DEPTH_EXT = 6145;
    public static final int GL_DOT3_RGBA_IMG = 34479;
    public static final int GL_MULTISAMPLE_BUFFER_BIT6_QCOM = 1073741824;
    public static final int GL_FACTOR_ALPHA_MODULATE_IMG = 35847;
    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_OES = 34888;
    public static final int GL_TEXTURE_HEIGHT_QCOM = 35795;
    public static final int GL_STENCIL_BUFFER_BIT7_QCOM = 8388608;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_IMG = 37172;
    public static final int GL_MAX_CLIP_PLANES_IMG = 3378;
    public static final int GL_COLOR_EXT = 6144;
    public static final int GL_CLIP_PLANE3_IMG = 12291;
    public static final int GL_ADD_BLEND_IMG = 35849;
    public static final int GL_POINT_SIZE_ARRAY_OES = 35740;
    public static final int GL_STENCIL_BUFFER_BIT5_QCOM = 2097152;
    public static final int GL_ETC1_RGB8_OES = 36196;
    public static final int GL_TEXTURE_MAX_LEVEL_APPLE = 33085;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_IMG = 33637;
    public static final int GL_TEXTURE_TARGET_QCOM = 35802;
    public static final int GL_TEXTURE_ALPHA_MODULATE_IMG = 35846;
    public static final int GL_STENCIL_BUFFER_BIT0_QCOM = 65536;
    public static final int GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES = 35215;
    public static final int GL_3DC_X_AMD = 34809;
    public static final int GL_REQUIRED_TEXTURE_IMAGE_UNITS_OES = 36200;
    public static final int GL_PERFMON_GLOBAL_MODE_QCOM = 36768;
    public static final int GL_BGRA_IMG = 32993;
    public static final int GL_CLIP_PLANE2_IMG = 12290;
    public static final int GL_TEXTURE_INTERNAL_FORMAT_QCOM = 35797;
    public static final int GL_TEXTURE_DEPTH_QCOM = 35796;
    public static final int GL_COLOR_BUFFER_BIT1_QCOM = 2;
    public static final int GL_DEPTH_BUFFER_BIT0_QCOM = 256;
    public static final int GL_STENCIL_BUFFER_BIT1_QCOM = 131072;
    public static final int GL_STENCIL_EXT = 6146;
    public static final int GL_COLOR_BUFFER_BIT5_QCOM = 32;
    public static final int GL_DEPTH_BUFFER_BIT4_QCOM = 4096;
    public static final int GL_COMPRESSED_RGB_PVRTC_4BPPV1_IMG = 35840;
    public static final int GL_TEXTURE_SAMPLES_IMG = 37174;
    public static final int GL_ATC_RGB_AMD = 35986;
    public static final int GL_MODULATE_COLOR_IMG = 35844;
    public static final int GL_MATRIX_PALETTE_OES = 34880;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_SAMPLES_EXT = 36204;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_EXT = 36182;
    public static final int GL_POINT_SIZE_ARRAY_TYPE_OES = 35210;
    public static final int GL_STENCIL_BUFFER_BIT4_QCOM = 1048576;
    public static final int GL_MAX_SAMPLES_IMG = 37173;
    public static final int GL_3DC_XY_AMD = 34810;
    public static final int GL_TEXTURE_NUM_LEVELS_QCOM = 35801;
    public static final int GL_MULTISAMPLE_BUFFER_BIT1_QCOM = 33554432;
    public static final int GL_WRITEONLY_RENDERING_QCOM = 34851;
    public static final int GL_CLIP_PLANE1_IMG = 12289;
    public static final int GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES = 35214;
    public static final int GL_TEXTURE_BINDING_EXTERNAL_OES = 36199;
    public static final int GL_MATRIX_INDEX_ARRAY_OES = 34884;
    public static final int GL_TEXTURE_CROP_RECT_OES = 35741;
    public static final int GL_POINT_SIZE_ARRAY_STRIDE_OES = 35211;
    public static final int GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES = 35213;
    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_OES = 34887;
    public static final int GL_WEIGHT_ARRAY_POINTER_OES = 34476;
    public static final int GL_MULTISAMPLE_BUFFER_BIT0_QCOM = 16777216;
    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_OES = 34886;
    public static final int GL_CLIP_PLANE0_IMG = 12288;
    public static final int GL_MATRIX_INDEX_ARRAY_BUFFER_BINDING_OES = 35742;
    public static final int GL_COMPRESSED_RGBA_PVRTC_4BPPV1_IMG = 35842;
    public static final int GL_FRAGMENT_ALPHA_MODULATE_IMG = 35848;
    public static final int GL_WEIGHT_ARRAY_STRIDE_OES = 34474;
    public static final int GL_DEPTH_BUFFER_BIT7_QCOM = 32768;
    public static final int GL_STENCIL_BUFFER_BIT6_QCOM = 4194304;
    public static final int GL_MULTISAMPLE_BUFFER_BIT5_QCOM = 536870912;
    public static final int GL_WEIGHT_ARRAY_TYPE_OES = 34473;
    public static final int GL_RENDERBUFFER_SAMPLES_EXT = 36011;
    public static final int GL_RECIP_ADD_SIGNED_ALPHA_IMG = 35845;
    public static final int GL_TEXTURE_OBJECT_VALID_QCOM = 35803;
    public static final int GL_WEIGHT_ARRAY_SIZE_OES = 34475;
    public static final int GL_COLOR_BUFFER_BIT3_QCOM = 8;
    public static final int GL_DEPTH_BUFFER_BIT2_QCOM = 1024;
    
    void glClipPlanef(final int p0, final FloatBuffer p1);
    
    void glClipPlanef(final int p0, final float[] p1, final int p2);
    
    void glGetClipPlanef(final int p0, final FloatBuffer p1);
    
    void glGetClipPlanef(final int p0, final float[] p1, final int p2);
    
    void glAlphaFuncx(final int p0, final int p1);
    
    void glClearColorx(final int p0, final int p1, final int p2, final int p3);
    
    void glClearDepthx(final int p0);
    
    void glClipPlanex(final int p0, final IntBuffer p1);
    
    void glClipPlanex(final int p0, final int[] p1, final int p2);
    
    void glColor4x(final int p0, final int p1, final int p2, final int p3);
    
    void glDepthRangex(final int p0, final int p1);
    
    void glFogx(final int p0, final int p1);
    
    void glFogxv(final int p0, final IntBuffer p1);
    
    void glFogxv(final int p0, final int[] p1, final int p2);
    
    void glFrustumx(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glGetClipPlanex(final int p0, final IntBuffer p1);
    
    void glGetClipPlanex(final int p0, final int[] p1, final int p2);
    
    void glGetFixedv(final int p0, final IntBuffer p1);
    
    void glGetFixedv(final int p0, final int[] p1, final int p2);
    
    void glGetLightxv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetLightxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetMaterialxv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetMaterialxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexEnvxv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexEnvxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexParameterxv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexParameterxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glLightModelx(final int p0, final int p1);
    
    void glLightModelxv(final int p0, final IntBuffer p1);
    
    void glLightModelxv(final int p0, final int[] p1, final int p2);
    
    void glLightx(final int p0, final int p1, final int p2);
    
    void glLightxv(final int p0, final int p1, final IntBuffer p2);
    
    void glLightxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glLineWidthx(final int p0);
    
    void glLoadMatrixx(final IntBuffer p0);
    
    void glLoadMatrixx(final int[] p0, final int p1);
    
    void glMaterialx(final int p0, final int p1, final int p2);
    
    void glMaterialxv(final int p0, final int p1, final IntBuffer p2);
    
    void glMaterialxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMultMatrixx(final IntBuffer p0);
    
    void glMultMatrixx(final int[] p0, final int p1);
    
    void glMultiTexCoord4x(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNormal3x(final int p0, final int p1, final int p2);
    
    void glOrthox(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glPointParameterx(final int p0, final int p1);
    
    void glPointParameterxv(final int p0, final IntBuffer p1);
    
    void glPointParameterxv(final int p0, final int[] p1, final int p2);
    
    void glPointSizex(final int p0);
    
    void glPolygonOffsetx(final int p0, final int p1);
    
    void glRotatex(final int p0, final int p1, final int p2, final int p3);
    
    void glSampleCoveragex(final int p0, final boolean p1);
    
    void glScalex(final int p0, final int p1, final int p2);
    
    void glTexEnvx(final int p0, final int p1, final int p2);
    
    void glTexEnvxv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexEnvxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTexParameterx(final int p0, final int p1, final int p2);
    
    void glTexParameterxv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexParameterxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTranslatex(final int p0, final int p1, final int p2);
    
    void glPointSizePointerOES(final int p0, final int p1, final Buffer p2);
    
    void glDrawTexsOES(final short p0, final short p1, final short p2, final short p3, final short p4);
    
    void glDrawTexiOES(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glDrawTexxOES(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glDrawTexsvOES(final ShortBuffer p0);
    
    void glDrawTexsvOES(final short[] p0, final int p1);
    
    void glDrawTexivOES(final IntBuffer p0);
    
    void glDrawTexivOES(final int[] p0, final int p1);
    
    void glDrawTexxvOES(final IntBuffer p0);
    
    void glDrawTexxvOES(final int[] p0, final int p1);
    
    void glDrawTexfOES(final float p0, final float p1, final float p2, final float p3, final float p4);
    
    void glDrawTexfvOES(final FloatBuffer p0);
    
    void glDrawTexfvOES(final float[] p0, final int p1);
    
    void glEGLImageTargetTexture2DOES(final int p0, final long p1);
    
    void glEGLImageTargetRenderbufferStorageOES(final int p0, final long p1);
    
    void glCurrentPaletteMatrixOES(final int p0);
    
    void glLoadPaletteFromModelViewMatrixOES();
    
    void glMatrixIndexPointerOES(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glWeightPointerOES(final int p0, final int p1, final int p2, final Buffer p3);
    
    int glQueryMatrixxOES(final IntBuffer p0, final IntBuffer p1);
    
    int glQueryMatrixxOES(final int[] p0, final int p1, final int[] p2, final int p3);
    
    void glTexGenf(final int p0, final int p1, final float p2);
    
    void glTexGenfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glTexGenfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glTexGeni(final int p0, final int p1, final int p2);
    
    void glTexGeniv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexGeniv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTexGenx(final int p0, final int p1, final int p2);
    
    void glTexGenxv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexGenxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexGenfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetTexGenfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetTexGeniv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexGeniv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexGenxv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexGenxv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBindVertexArrayOES(final int p0);
    
    void glDeleteVertexArraysOES(final int p0, final IntBuffer p1);
    
    void glDeleteVertexArraysOES(final int p0, final int[] p1, final int p2);
    
    void glGenVertexArraysOES(final int p0, final IntBuffer p1);
    
    void glGenVertexArraysOES(final int p0, final int[] p1, final int p2);
    
    boolean glIsVertexArrayOES(final int p0);
    
    void glCopyTextureLevelsAPPLE(final int p0, final int p1, final int p2, final int p3);
    
    void glResolveMultisampleFramebuffer();
    
    void glDiscardFramebufferEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glDiscardFramebufferEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glRenderbufferStorageMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFramebufferTexture2DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glClipPlanefIMG(final int p0, final FloatBuffer p1);
    
    void glClipPlanefIMG(final int p0, final float[] p1, final int p2);
    
    void glClipPlanexIMG(final int p0, final IntBuffer p1);
    
    void glClipPlanexIMG(final int p0, final int[] p1, final int p2);
    
    void glRenderbufferStorageMultisampleIMG(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFramebufferTexture2DMultisampleIMG(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glGetDriverControlsQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glGetDriverControlsQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetDriverControlStringQCOM(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetDriverControlStringQCOM(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glEnableDriverControlQCOM(final int p0);
    
    void glDisableDriverControlQCOM(final int p0);
    
    void glExtGetTexturesQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetTexturesQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glExtGetBuffersQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetBuffersQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glExtGetRenderbuffersQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetRenderbuffersQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glExtGetFramebuffersQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetFramebuffersQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glExtGetTexLevelParameterivQCOM(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glExtGetTexLevelParameterivQCOM(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glExtTexObjectStateOverrideiQCOM(final int p0, final int p1, final int p2);
    
    void glExtGetTexSubImageQCOM(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glExtGetBufferPointervQCOM(final int p0, final PointerBuffer p1);
    
    void glExtGetShadersQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetShadersQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glExtGetProgramsQCOM(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glExtGetProgramsQCOM(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    boolean glExtIsProgramBinaryQCOM(final int p0);
    
    void glExtGetProgramBinarySourceQCOM(final int p0, final int p1, final ByteBuffer p2, final IntBuffer p3);
    
    void glExtGetProgramBinarySourceQCOM(final int p0, final int p1, final byte[] p2, final int p3, final int[] p4, final int p5);
    
    void glStartTilingQCOM(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glEndTilingQCOM(final int p0);
}
