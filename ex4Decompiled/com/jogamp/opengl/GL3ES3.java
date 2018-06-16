// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public interface GL3ES3 extends GL2ES3
{
    public static final int GL_GEOMETRY_INPUT_TYPE = 35095;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    public static final int GL_OVERLAY = 37526;
    public static final int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    public static final int GL_SHADER_STORAGE_BUFFER_BINDING = 37075;
    public static final int GL_LINES_ADJACENCY = 10;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x4 = 37808;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
    public static final int GL_FRACTIONAL_ODD = 36475;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6 = 37849;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5 = 37848;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8 = 37850;
    public static final int GL_HSL_HUE = 37549;
    public static final int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 37309;
    public static final int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static final int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 36665;
    public static final int GL_ACTIVE_RESOURCES = 37621;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x8 = 37815;
    public static final int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 33379;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x6 = 37814;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x5 = 37813;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    public static final int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    public static final int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 37078;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5 = 37845;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6 = 37846;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8 = 37847;
    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
    public static final int GL_FRACTIONAL_EVEN = 36476;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 37311;
    public static final int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    public static final int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    public static final int GL_HARDLIGHT = 37531;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET = 36446;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4 = 37840;
    public static final int GL_MAX_UNIFORM_LOCATIONS = 33390;
    public static final int GL_UNIFORM_BLOCK = 37602;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_OFFSET = 37628;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    public static final int GL_MAX_PATCH_VERTICES = 36477;
    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
    public static final int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 37086;
    public static final int GL_PROGRAM_OUTPUT = 37604;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 37082;
    public static final int GL_SHADER_STORAGE_BUFFER = 37074;
    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    public static final int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 37083;
    public static final int GL_TESS_GEN_VERTEX_ORDER = 36472;
    public static final int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 37087;
    public static final int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 37085;
    public static final int GL_LINE_STRIP_ADJACENCY = 11;
    public static final int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 33382;
    public static final int GL_MATRIX_STRIDE = 37631;
    public static final int GL_ACTIVE_VARIABLES = 37637;
    public static final int GL_SCREEN = 37525;
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 37080;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 36443;
    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 36444;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 37310;
    public static final int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    public static final int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 37308;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x6 = 37817;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x8 = 37818;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x5 = 37816;
    public static final int GL_PROGRAM_INPUT = 37603;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10 = 37851;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x10 = 37819;
    public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static final int GL_HSL_SATURATION = 37550;
    public static final int GL_MAX_TESS_GEN_LEVEL = 36478;
    public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 33381;
    public static final int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 37084;
    public static final int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 37307;
    public static final int GL_ARRAY_STRIDE = 37630;
    public static final int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 33378;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
    public static final int GL_TRIANGLES_ADJACENCY = 12;
    public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
    public static final int GL_IS_ROW_MAJOR = 37632;
    public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 37079;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET = 36447;
    public static final int GL_HSL_COLOR = 37551;
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 37081;
    public static final int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 37103;
    public static final int GL_TESS_CONTROL_SHADER = 36488;
    public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    public static final int GL_VERTEX_BINDING_STRIDE = 33496;
    public static final int GL_COMPUTE_SHADER = 37305;
    public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = 33509;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    public static final int GL_COLORBURN = 37530;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x6 = 37812;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x5 = 37811;
    public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5 = 37843;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6 = 37844;
    public static final int GL_GEOMETRY_SHADER_INVOCATIONS = 34943;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_TESS_GEN_SPACING = 36471;
    public static final int GL_BUFFER_VARIABLE = 37605;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    public static final int GL_SHADER_STORAGE_BUFFER_SIZE = 37077;
    public static final int GL_VERTEX_BINDING_BUFFER = 36687;
    public static final int GL_IS_PER_PATCH = 37607;
    public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_MAX_NAME_LENGTH = 37622;
    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
    public static final int GL_UNIFORM = 37601;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 36445;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 33380;
    public static final int GL_BUFFER_BINDING = 37634;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
    public static final int GL_GEOMETRY_SHADER = 36313;
    public static final int GL_HSL_LUMINOSITY = 37552;
    public static final int GL_GEOMETRY_OUTPUT_TYPE = 35096;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_ARRAY_SIZE = 37627;
    public static final int GL_MULTIPLY = 37524;
    public static final int GL_UNDEFINED_VERTEX = 33376;
    public static final int GL_COLORDODGE = 37529;
    public static final int GL_ISOLINES = 36474;
    public static final int GL_DARKEN = 37527;
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
    public static final int GL_BUFFER_DATA_SIZE = 37635;
    public static final int GL_NUM_ACTIVE_VARIABLES = 37636;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    public static final int GL_TESS_GEN_MODE = 36470;
    public static final int GL_SOFTLIGHT = 37532;
    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
    public static final int GL_TYPE = 37626;
    public static final int GL_COMPUTE_SHADER_BIT = 32;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_NAME_LENGTH = 37625;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x5 = 37810;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x4 = 37809;
    public static final int GL_LIGHTEN = 37528;
    public static final int GL_TESS_GEN_POINT_MODE = 36473;
    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 33313;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final int GL_WAIT_FAILED = 37149;
    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 36442;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_GEOMETRY_VERTICES_OUT = 35094;
    public static final int GL_PATCHES = 14;
    public static final int GL_VERTEX_BINDING_OFFSET = 33495;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_INT_2_10_10_10_REV = 36255;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12 = 37853;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10 = 37852;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x12 = 37821;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 37099;
    public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static final int GL_CONTEXT_LOST = 1287;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x10 = 37820;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
    public static final int GL_SHADER_STORAGE_BLOCK = 37606;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5 = 37842;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4 = 37841;
    public static final int GL_DISPATCH_INDIRECT_BUFFER = 37102;
    public static final int GL_SHADER_STORAGE_BUFFER_START = 37076;
    public static final int GL_EXCLUSION = 37536;
    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
    public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
    public static final int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    public static final int GL_TEXTURE_BUFFER_BINDING = 35882;
    public static final int GL_COMPUTE_WORK_GROUP_SIZE = 33383;
    public static final int GL_PATCH_VERTICES = 36466;
    public static final int GL_DIFFERENCE = 37534;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_LOCATION = 37646;
    public static final int GL_SAMPLER_BINDING = 35097;
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_TESS_EVALUATION_SHADER = 36487;
    public static final int GL_SHADER_STORAGE_BARRIER_BIT = 8192;
    public static final int GL_BLOCK_INDEX = 37629;
    
    long glFenceSync(final int p0, final int p1);
    
    boolean glIsSync(final long p0);
    
    void glDeleteSync(final long p0);
    
    int glClientWaitSync(final long p0, final int p1, final long p2);
    
    void glWaitSync(final long p0, final int p1, final long p2);
    
    void glGetInteger64v(final int p0, final LongBuffer p1);
    
    void glGetInteger64v(final int p0, final long[] p1, final int p2);
    
    void glGetSynciv(final long p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4);
    
    void glGetSynciv(final long p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6);
    
    void glGetInteger64i_v(final int p0, final int p1, final LongBuffer p2);
    
    void glGetInteger64i_v(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetBufferParameteri64v(final int p0, final int p1, final LongBuffer p2);
    
    void glGetBufferParameteri64v(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGenSamplers(final int p0, final IntBuffer p1);
    
    void glGenSamplers(final int p0, final int[] p1, final int p2);
    
    void glDeleteSamplers(final int p0, final IntBuffer p1);
    
    void glDeleteSamplers(final int p0, final int[] p1, final int p2);
    
    boolean glIsSampler(final int p0);
    
    void glBindSampler(final int p0, final int p1);
    
    void glSamplerParameteri(final int p0, final int p1, final int p2);
    
    void glSamplerParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glSamplerParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glSamplerParameterf(final int p0, final int p1, final float p2);
    
    void glSamplerParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glSamplerParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetSamplerParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetSamplerParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetSamplerParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetSamplerParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glDispatchCompute(final int p0, final int p1, final int p2);
    
    void glDispatchComputeIndirect(final long p0);
    
    void glDrawArraysIndirect(final int p0, final Buffer p1);
    
    void glDrawArraysIndirect(final int p0, final long p1);
    
    void glDrawElementsIndirect(final int p0, final int p1, final Buffer p2);
    
    void glDrawElementsIndirect(final int p0, final int p1, final long p2);
    
    void glGetProgramInterfaceiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetProgramInterfaceiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    int glGetProgramResourceIndex(final int p0, final int p1, final ByteBuffer p2);
    
    int glGetProgramResourceIndex(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glGetProgramResourceName(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final ByteBuffer p5);
    
    void glGetProgramResourceName(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final byte[] p6, final int p7);
    
    void glGetProgramResourceiv(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final int p5, final IntBuffer p6, final IntBuffer p7);
    
    void glGetProgramResourceiv(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final int p6, final int[] p7, final int p8, final int[] p9, final int p10);
    
    int glGetProgramResourceLocation(final int p0, final int p1, final ByteBuffer p2);
    
    int glGetProgramResourceLocation(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glMemoryBarrierByRegion(final int p0);
    
    void glBindVertexBuffer(final int p0, final int p1, final long p2, final int p3);
    
    void glVertexAttribFormat(final int p0, final int p1, final int p2, final boolean p3, final int p4);
    
    void glVertexAttribIFormat(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribBinding(final int p0, final int p1);
    
    void glVertexBindingDivisor(final int p0, final int p1);
    
    void glBlendBarrier();
    
    void glDrawElementsBaseVertex(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawElementsBaseVertex(final int p0, final int p1, final int p2, final long p3, final int p4);
    
    void glDrawRangeElementsBaseVertex(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5, final int p6);
    
    void glDrawRangeElementsBaseVertex(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final int p6);
    
    void glDrawElementsInstancedBaseVertex(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5);
    
    void glDrawElementsInstancedBaseVertex(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5);
    
    void glFramebufferTexture(final int p0, final int p1, final int p2, final int p3);
    
    void glPatchParameteri(final int p0, final int p1);
    
    void glTexBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4);
}
