// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;

public interface GL3 extends GL3ES3, GL2GL3
{
    public static final int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS = 36767;
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static final int GL_DOUBLE_MAT2x4 = 36682;
    public static final int GL_DOUBLE_MAT2x3 = 36681;
    public static final int GL_SRC1_COLOR = 35065;
    public static final int GL_LINES_ADJACENCY_ARB = 10;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_ARB = 36319;
    public static final int GL_SHADER_INCLUDE_ARB = 36270;
    public static final int GL_DOUBLE_VEC4 = 36862;
    public static final int GL_DOUBLE_VEC3 = 36861;
    public static final int GL_DOUBLE_VEC2 = 36860;
    public static final int GL_LINE_STRIP_ADJACENCY_ARB = 11;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_ARB = 36263;
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT_ARB = 36494;
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static final int GL_COMPATIBLE_SUBROUTINES = 36427;
    public static final int GL_DEPTH_CLAMP = 34383;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_ARB = 13;
    public static final int GL_SYNC_CL_EVENT_ARB = 33344;
    public static final int GL_TRIANGLES_ADJACENCY_ARB = 12;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_COUNT_ARB = 36265;
    public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
    public static final int GL_DOUBLE_MAT4 = 36680;
    public static final int GL_DOUBLE_MAT3 = 36679;
    public static final int GL_DOUBLE_MAT2 = 36678;
    public static final int GL_MAX_GEOMETRY_VARYING_COMPONENTS_ARB = 36317;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM_ARB = 36492;
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM_ARB = 36493;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_ARB = 35881;
    public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int GL_GEOMETRY_OUTPUT_TYPE_ARB = 36316;
    public static final int GL_GEOMETRY_VERTICES_OUT_ARB = 36314;
    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_ARB = 36320;
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static final int GL_NAMED_STRING_LENGTH_ARB = 36329;
    public static final int GL_DOUBLE_MAT4x2 = 36685;
    public static final int GL_DOUBLE_MAT4x3 = 36686;
    public static final int GL_GEOMETRY_INPUT_TYPE_ARB = 36315;
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
    public static final int GL_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_GEOMETRY_SHADER_ARB = 36313;
    public static final int GL_ACTIVE_SUBROUTINES = 36325;
    public static final int GL_MAX_VERTEX_VARYING_COMPONENTS_ARB = 36318;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_ARB = 36321;
    public static final int GL_SYNC_CL_EVENT_COMPLETE_ARB = 33345;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_ARB = 36264;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    public static final int GL_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT_ARB = 36495;
    public static final int GL_NAMED_STRING_TYPE_ARB = 36330;
    public static final int GL_DOUBLE_MAT3x4 = 36684;
    public static final int GL_DOUBLE_MAT3x2 = 36683;
    public static final int GL_CONTEXT_PROFILE_MASK = 37158;
    public static final int GL_MAX_SUBROUTINES = 36327;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
    public static final int GL_MAX_VIEWPORTS = 33371;
    
    void glMultiDrawElementsBaseVertex(final int p0, final IntBuffer p1, final int p2, final PointerBuffer p3, final int p4, final IntBuffer p5);
    
    void glBindFragDataLocationIndexed(final int p0, final int p1, final int p2, final String p3);
    
    int glGetFragDataIndex(final int p0, final String p1);
    
    void glVertexAttribP1ui(final int p0, final int p1, final boolean p2, final int p3);
    
    void glVertexAttribP1uiv(final int p0, final int p1, final boolean p2, final IntBuffer p3);
    
    void glVertexAttribP1uiv(final int p0, final int p1, final boolean p2, final int[] p3, final int p4);
    
    void glVertexAttribP2ui(final int p0, final int p1, final boolean p2, final int p3);
    
    void glVertexAttribP2uiv(final int p0, final int p1, final boolean p2, final IntBuffer p3);
    
    void glVertexAttribP2uiv(final int p0, final int p1, final boolean p2, final int[] p3, final int p4);
    
    void glVertexAttribP3ui(final int p0, final int p1, final boolean p2, final int p3);
    
    void glVertexAttribP3uiv(final int p0, final int p1, final boolean p2, final IntBuffer p3);
    
    void glVertexAttribP3uiv(final int p0, final int p1, final boolean p2, final int[] p3, final int p4);
    
    void glVertexAttribP4ui(final int p0, final int p1, final boolean p2, final int p3);
    
    void glVertexAttribP4uiv(final int p0, final int p1, final boolean p2, final IntBuffer p3);
    
    void glVertexAttribP4uiv(final int p0, final int p1, final boolean p2, final int[] p3, final int p4);
    
    void glUniform1d(final int p0, final double p1);
    
    void glUniform2d(final int p0, final double p1, final double p2);
    
    void glUniform3d(final int p0, final double p1, final double p2, final double p3);
    
    void glUniform4d(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glUniform1dv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glUniform1dv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glUniform2dv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glUniform2dv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glUniform3dv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glUniform3dv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glUniform4dv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glUniform4dv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glUniformMatrix2dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix2dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix3dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix3dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix4dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix4dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix2x3dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix2x3dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix2x4dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix2x4dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix3x2dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix3x2dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix3x4dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix3x4dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix4x2dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix4x2dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glUniformMatrix4x3dv(final int p0, final int p1, final boolean p2, final DoubleBuffer p3);
    
    void glUniformMatrix4x3dv(final int p0, final int p1, final boolean p2, final double[] p3, final int p4);
    
    void glGetUniformdv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetUniformdv(final int p0, final int p1, final double[] p2, final int p3);
    
    int glGetSubroutineUniformLocation(final int p0, final int p1, final String p2);
    
    int glGetSubroutineIndex(final int p0, final int p1, final String p2);
    
    void glGetActiveSubroutineUniformiv(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glGetActiveSubroutineUniformiv(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glGetActiveSubroutineUniformName(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final ByteBuffer p5);
    
    void glGetActiveSubroutineUniformName(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final byte[] p6, final int p7);
    
    void glGetActiveSubroutineName(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final ByteBuffer p5);
    
    void glGetActiveSubroutineName(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final byte[] p6, final int p7);
    
    void glUniformSubroutinesuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniformSubroutinesuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetUniformSubroutineuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetUniformSubroutineuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramStageiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetProgramStageiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glPatchParameterfv(final int p0, final FloatBuffer p1);
    
    void glPatchParameterfv(final int p0, final float[] p1, final int p2);
    
    void glViewportArrayv(final int p0, final int p1, final FloatBuffer p2);
    
    void glViewportArrayv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glViewportIndexedf(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glViewportIndexedfv(final int p0, final FloatBuffer p1);
    
    void glViewportIndexedfv(final int p0, final float[] p1, final int p2);
    
    void glScissorArrayv(final int p0, final int p1, final IntBuffer p2);
    
    void glScissorArrayv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glScissorIndexed(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glScissorIndexedv(final int p0, final IntBuffer p1);
    
    void glScissorIndexedv(final int p0, final int[] p1, final int p2);
    
    void glDepthRangeArrayv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glDepthRangeArrayv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glDepthRangeIndexed(final int p0, final double p1, final double p2);
    
    void glGetFloati_v(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetFloati_v(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetDoublei_v(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetDoublei_v(final int p0, final int p1, final double[] p2, final int p3);
    
    void glDrawTransformFeedbackInstanced(final int p0, final int p1, final int p2);
    
    void glDrawTransformFeedbackStreamInstanced(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiDrawArraysIndirect(final int p0, final long p1, final int p2, final int p3);
    
    void glMultiDrawElementsIndirect(final int p0, final int p1, final Buffer p2, final int p3, final int p4);
    
    long glCreateSyncFromCLeventARB(final long p0, final long p1, final int p2);
    
    void glNamedStringARB(final int p0, final int p1, final String p2, final int p3, final String p4);
    
    void glDeleteNamedStringARB(final int p0, final String p1);
    
    void glCompileShaderIncludeARB(final int p0, final int p1, final String[] p2, final IntBuffer p3);
    
    void glCompileShaderIncludeARB(final int p0, final int p1, final String[] p2, final int[] p3, final int p4);
    
    boolean glIsNamedStringARB(final int p0, final String p1);
    
    void glGetNamedStringARB(final int p0, final String p1, final int p2, final IntBuffer p3, final ByteBuffer p4);
    
    void glGetNamedStringARB(final int p0, final String p1, final int p2, final int[] p3, final int p4, final byte[] p5, final int p6);
    
    void glGetNamedStringivARB(final int p0, final String p1, final int p2, final IntBuffer p3);
    
    void glGetNamedStringivARB(final int p0, final String p1, final int p2, final int[] p3, final int p4);
    
    void glProgramParameteriARB(final int p0, final int p1, final int p2);
    
    void glFramebufferTextureARB(final int p0, final int p1, final int p2, final int p3);
    
    void glFramebufferTextureLayerARB(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFramebufferTextureFaceARB(final int p0, final int p1, final int p2, final int p3, final int p4);
}
