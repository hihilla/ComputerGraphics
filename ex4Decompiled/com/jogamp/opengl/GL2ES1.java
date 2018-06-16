// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

public interface GL2ES1 extends GL, GLMatrixFunc, GLPointerFunc, GLLightingFunc
{
    public static final int GL_POINT_SIZE_MAX = 33063;
    public static final int GL_SRC2_ALPHA = 34186;
    public static final int GL_MODELVIEW_STACK_DEPTH = 2979;
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 34967;
    public static final int GL_COLOR_ARRAY_POINTER = 32912;
    public static final int GL_PREVIOUS = 34168;
    public static final int GL_OPERAND0_RGB = 34192;
    public static final int GL_VERTEX_ARRAY_SIZE = 32890;
    public static final int GL_FOG_HINT = 3156;
    public static final int GL_CURRENT_TEXTURE_COORDS = 2819;
    public static final int GL_CURRENT_COLOR = 2816;
    public static final int GL_VERTEX_ARRAY_TYPE = 32891;
    public static final int GL_MAX_TEXTURE_UNITS = 34018;
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 34966;
    public static final int GL_RESCALE_NORMAL = 32826;
    public static final int GL_VERTEX_ARRAY_STRIDE = 32892;
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 34968;
    public static final int GL_CLIP_PLANE0 = 12288;
    public static final int GL_CLIP_PLANE1 = 12289;
    public static final int GL_CLIP_PLANE2 = 12290;
    public static final int GL_CLIP_PLANE3 = 12291;
    public static final int GL_RGB_SCALE = 34163;
    public static final int GL_CLIP_PLANE4 = 12292;
    public static final int GL_CLIP_PLANE5 = 12293;
    public static final int GL_ALPHA_TEST = 3008;
    public static final int GL_COLOR_ARRAY_STRIDE = 32899;
    public static final int GL_MAX_LIGHTS = 3377;
    public static final int GL_COMBINE_RGB = 34161;
    public static final int GL_PROJECTION_STACK_DEPTH = 2980;
    public static final int GL_OPERAND1_ALPHA = 34201;
    public static final int GL_NORMAL_ARRAY_POINTER = 32911;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static final int GL_CLIENT_ACTIVE_TEXTURE = 34017;
    public static final int GL_TEXTURE_STACK_DEPTH = 2981;
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 2898;
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 32905;
    public static final int GL_SRC0_RGB = 34176;
    public static final int GL_FOG_MODE = 2917;
    public static final int GL_ALPHA_TEST_FUNC = 3009;
    public static final int GL_MAX_CLIP_PLANES = 3378;
    public static final int GL_VERTEX_ARRAY_POINTER = 32910;
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 32904;
    public static final int GL_MODULATE = 8448;
    public static final int GL_OPERAND2_ALPHA = 34202;
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    public static final int GL_TEXTURE_ENV = 8960;
    public static final int GL_NORMAL_ARRAY_STRIDE = 32895;
    public static final int GL_SRC1_RGB = 34177;
    public static final int GL_FOG_COLOR = 2918;
    public static final int GL_POINT_SIZE_MIN = 33062;
    public static final int GL_OPERAND0_ALPHA = 34200;
    public static final int GL_POINT_SMOOTH = 2832;
    public static final int GL_EXP2 = 2049;
    public static final int GL_EXP = 2048;
    public static final int GL_COLOR_ARRAY_SIZE = 32897;
    public static final int GL_LIGHT_MODEL_AMBIENT = 2899;
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 32914;
    public static final int GL_CURRENT_NORMAL = 2818;
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    public static final int GL_PRIMARY_COLOR = 34167;
    public static final int GL_TEXTURE_ENV_COLOR = 8705;
    public static final int GL_SRC2_RGB = 34178;
    public static final int GL_INTERPOLATE = 34165;
    public static final int GL_DECAL = 8449;
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_TEXTURE_ENV_MODE = 8704;
    public static final int GL_ALPHA_TEST_REF = 3010;
    public static final int GL_CONTEXT_ROBUST_ACCESS = 37107;
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 34970;
    public static final int GL_POINT_DISTANCE_ATTENUATION = 33065;
    public static final int GL_ALPHA_SCALE = 3356;
    public static final int GL_CONSTANT = 34166;
    public static final int GL_COLOR_ARRAY_TYPE = 32898;
    public static final int GL_DOT3_RGB = 34478;
    public static final int GL_POINT_SMOOTH_HINT = 3153;
    public static final int GL_SHADE_MODEL = 2900;
    public static final int GL_OPERAND1_RGB = 34193;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_DOT3_RGBA = 34479;
    public static final int GL_FOG_END = 2916;
    public static final int GL_COORD_REPLACE = 34914;
    public static final int GL_SUBTRACT = 34023;
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 32906;
    public static final int GL_NORMAL_ARRAY_TYPE = 32894;
    public static final int GL_SRC0_ALPHA = 34184;
    public static final int GL_COMBINE = 34160;
    public static final int GL_POINT_SPRITE = 34913;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_ADD = 260;
    public static final int GL_FOG_DENSITY = 2914;
    public static final int GL_OPERAND2_RGB = 34194;
    public static final int GL_GENERATE_MIPMAP = 33169;
    public static final int GL_FOG = 2912;
    public static final int GL_FOG_START = 2915;
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
    public static final int GL_COMBINE_ALPHA = 34162;
    public static final int GL_ADD_SIGNED = 34164;
    
    void glAlphaFunc(final int p0, final float p1);
    
    void glFogf(final int p0, final float p1);
    
    void glFogfv(final int p0, final FloatBuffer p1);
    
    void glFogfv(final int p0, final float[] p1, final int p2);
    
    void glGetLightfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetLightfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetMaterialfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMaterialfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetTexEnvfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetTexEnvfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glLightModelf(final int p0, final float p1);
    
    void glLightModelfv(final int p0, final FloatBuffer p1);
    
    void glLightModelfv(final int p0, final float[] p1, final int p2);
    
    void glLightf(final int p0, final int p1, final float p2);
    
    void glMultiTexCoord4f(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glNormal3f(final float p0, final float p1, final float p2);
    
    void glPointParameterf(final int p0, final float p1);
    
    void glPointParameterfv(final int p0, final FloatBuffer p1);
    
    void glPointParameterfv(final int p0, final float[] p1, final int p2);
    
    void glPointSize(final float p0);
    
    void glTexEnvf(final int p0, final int p1, final float p2);
    
    void glTexEnvfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glTexEnvfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glClientActiveTexture(final int p0);
    
    void glColor4ub(final byte p0, final byte p1, final byte p2, final byte p3);
    
    void glGetTexEnviv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexEnviv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glLogicOp(final int p0);
    
    void glTexEnvi(final int p0, final int p1, final int p2);
    
    void glTexEnviv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexEnviv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glOrtho(final double p0, final double p1, final double p2, final double p3, final double p4, final double p5);
    
    void glFrustum(final double p0, final double p1, final double p2, final double p3, final double p4, final double p5);
    
    void glDrawElements(final int p0, final int p1, final int p2, final Buffer p3);
}
