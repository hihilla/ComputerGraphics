// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.Buffer;
import java.nio.IntBuffer;

public interface GLES2 extends GL2ES2
{
    public static final int GL_PROGRAM_BINARY_ANGLE = 37798;
    public static final int GL_RASTER_MULTISAMPLE_EXT = 37671;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS_EXT = 37156;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5_KHR = 37842;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_EXT = 13;
    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS_EXT = 37274;
    public static final int GL_READ_FRAMEBUFFER_NV = 36008;
    public static final int GL_LINE_STRIP_ADJACENCY_EXT = 11;
    public static final int GL_FIRST_VERTEX_CONVENTION_OES = 36429;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS_OES = 37582;
    public static final int GL_QUERY_WAIT = 36371;
    public static final int GL_FILL_RECTANGLE_NV = 37692;
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS_EXT = 37080;
    public static final int GL_COVERAGE_AUTOMATIC_NV = 36567;
    public static final int GL_TEXTURE_EXTERNAL_OES = 36197;
    public static final int GL_GEOMETRY_LINKED_VERTICES_OUT_EXT = 35094;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_EXT = 36319;
    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS_OES = 36481;
    public static final int GL_SRC_ALPHA_SATURATE_EXT = 776;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5x4_OES = 37861;
    public static final int GL_MAX_PATCH_VERTICES_EXT = 36477;
    public static final int GL_STENCIL_INDEX8_OES = 36168;
    public static final int GL_STENCIL_BUFFER_BIT2_QCOM = 262144;
    public static final int GL_VIRTUAL_PAGE_SIZE_Y_EXT = 37270;
    public static final long GL_MULTISAMPLE_BUFFER_BIT7_QCOM = 2147483648L;
    public static final int GL_Z400_BINARY_AMD = 34624;
    public static final int GL_COLOR_BUFFER_BIT7_QCOM = 128;
    public static final int GL_SRC1_COLOR_EXT = 35065;
    public static final int GL_ISOLINES_OES = 36474;
    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY_OES = 36959;
    public static final int GL_SHARED_EDGE_NV = 192;
    public static final int GL_RENDERBUFFER_SAMPLES_NV = 36011;
    public static final int GL_SLUMINANCE8_NV = 35911;
    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS_OES = 36483;
    public static final int GL_COLOR_BUFFER_BIT0_QCOM = 1;
    public static final int GL_HSL_SATURATION = 37550;
    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS_OES = 36383;
    public static final int GL_SAMPLER_EXTERNAL_2D_Y2Y_EXT = 35815;
    public static final int GL_LINES_ADJACENCY_EXT = 10;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT_OES = 37279;
    public static final int GL_SHADER_BINARY_VIV = 36804;
    public static final int GL_MAX_TESS_PATCH_COMPONENTS_OES = 36484;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_OES = 36263;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS_OES = 37589;
    public static final int GL_TEXTURE_BUFFER_SIZE_EXT = 37278;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4x4_OES = 37859;
    public static final int GL_IMAGE_CUBE_MAP_ARRAY_OES = 36948;
    public static final int GL_COVERAGE_MODULATION_TABLE_NV = 37681;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER_OES = 37641;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_OES = 36876;
    public static final int GL_CONSERVATIVE_RASTERIZATION_NV = 37702;
    public static final int GL_TEXTURE_MAX_LEVEL_APPLE = 33085;
    public static final int GL_FILL_NV = 6914;
    public static final int GL_DRAW_FRAMEBUFFER_NV = 36009;
    public static final int GL_INT_SAMPLER_BUFFER_OES = 36304;
    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS_EXT = 35378;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x5_KHR = 37813;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x4x4_OES = 37827;
    public static final int GL_FLOAT_MAT2x4_NV = 35686;
    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static final int GL_ETC1_SRGB8_NV = 35054;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS_OES = 36479;
    public static final int GL_COLORBURN = 37530;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x4_KHR = 37808;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10_KHR = 37851;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x3x3_OES = 37857;
    public static final int GL_MAX_TESS_GEN_LEVEL_OES = 36478;
    public static final int GL_REQUIRED_TEXTURE_IMAGE_UNITS_OES = 36200;
    public static final int GL_FLOAT_MAT3x2_NV = 35687;
    public static final int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY_OES = 36970;
    public static final int GL_MAX_SUBPIXEL_PRECISION_BIAS_BITS_NV = 37705;
    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV1_EXT = 35414;
    public static final int GL_SR8_EXT = 36797;
    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES_EXT = 36469;
    public static final int GL_MAX_TESS_PATCH_COMPONENTS_EXT = 36484;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_EXT = 36263;
    public static final int GL_DEPTH_BUFFER_BIT0_QCOM = 256;
    public static final int GL_STENCIL_EXT = 6146;
    public static final int GL_DEPTH_BUFFER_BIT4_QCOM = 4096;
    public static final int GL_COMPRESSED_RGB_PVRTC_4BPPV1_IMG = 35840;
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_ANGLE = 33778;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER_EXT = 37641;
    public static final int GL_COMPRESSED_SRGB_S3TC_DXT1_NV = 35916;
    public static final int GL_TESS_CONTROL_SHADER_BIT_EXT = 8;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6_KHR = 37844;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS_EXT = 37587;
    public static final int GL_STENCIL_BUFFER_BIT4_QCOM = 1048576;
    public static final int GL_TEXTURE_BINDING_BUFFER_EXT = 35884;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX_NV = 33375;
    public static final int GL_MAX_TESS_GEN_LEVEL_EXT = 36478;
    public static final int GL_3DC_XY_AMD = 34810;
    public static final int GL_SOFTLIGHT = 37532;
    public static final int GL_UNDEFINED_VERTEX_OES = 33376;
    public static final int GL_TRANSLATED_SHADER_SOURCE_LENGTH_ANGLE = 37792;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_NUM_VIEWS_OVR = 38448;
    public static final int GL_VERTEX_ARRAY = 32884;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS_EXT = 37582;
    public static final int GL_LAYER_PROVOKING_VERTEX_OES = 33374;
    public static final int GL_IS_PER_PATCH_OES = 37607;
    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS_OES = 36486;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x10_KHR = 37820;
    public static final int GL_DECODE_EXT = 35401;
    public static final int GL_MIXED_DEPTH_SAMPLES_SUPPORTED_NV = 37679;
    public static final int GL_TESS_GEN_MODE_OES = 36470;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5_KHR = 37845;
    public static final int GL_MULTISAMPLE_BUFFER_BIT0_QCOM = 16777216;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET = 36052;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS_OES = 37156;
    public static final int GL_GPU_OPTIMIZED_QCOM = 36786;
    public static final int GL_READ_FRAMEBUFFER_ANGLE = 36008;
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY_EXT = 36878;
    public static final int GL_POLYGON_MODE_NV = 2880;
    public static final int GL_FRAGMENT_COVERAGE_TO_COLOR_NV = 37597;
    public static final int GL_RASTER_FIXED_SAMPLE_LOCATIONS_EXT = 37674;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6x6_OES = 37865;
    public static final int GL_RASTER_SAMPLES_EXT = 37672;
    public static final int GL_DEPTH_BUFFER_BIT7_QCOM = 32768;
    public static final int GL_MAP_PERSISTENT_BIT_EXT = 64;
    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV2_IMG = 37873;
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_ANGLE = 33779;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_OES = 36319;
    public static final int GL_NUM_SPARSE_LEVELS_EXT = 37290;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4_KHR = 37840;
    public static final int GL_READ_FRAMEBUFFER_BINDING_ANGLE = 36010;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5x5_OES = 37863;
    public static final int GL_POLYGON_OFFSET_LINE_NV = 10754;
    public static final int GL_HARDLIGHT = 37531;
    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static final int GL_GEOMETRY_LINKED_OUTPUT_TYPE_OES = 35096;
    public static final int GL_MAX_SHADER_PIXEL_LOCAL_STORAGE_FAST_SIZE_EXT = 36707;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x3x3_OES = 37825;
    public static final int GL_SRG8_EXT = 36798;
    public static final int GL_POINT_NV = 6912;
    public static final int GL_TESS_GEN_VERTEX_ORDER_OES = 36472;
    public static final int GL_TEXTURE_FORMAT_QCOM = 35798;
    public static final int GL_MIN_SAMPLE_SHADING_VALUE_OES = 35895;
    public static final int GL_DYNAMIC_STORAGE_BIT_EXT = 256;
    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS_OES = 36382;
    public static final int GL_MULTISAMPLE_BUFFER_BIT3_QCOM = 134217728;
    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV1_EXT = 35415;
    public static final int GL_SRGB8_NV = 35905;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6_KHR = 37849;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x4x4_OES = 37828;
    public static final int GL_MALI_PROGRAM_BINARY_ARM = 36705;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x6_KHR = 37817;
    public static final int GL_MAX_MULTIVIEW_BUFFERS_EXT = 37106;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    public static final int GL_GEOMETRY_SHADER_BIT_EXT = 4;
    public static final int GL_TEXTURE_TYPE_QCOM = 35799;
    public static final int GL_DEPTH_BUFFER_BIT5_QCOM = 8192;
    public static final int GL_TRIANGLES_ADJACENCY_EXT = 12;
    public static final int GL_COMPRESSED_RGBA_ASTC_12x12_KHR = 37821;
    public static final int GL_IS_PER_PATCH_EXT = 37607;
    public static final int GL_LINE_STRIP_ADJACENCY_OES = 11;
    public static final int GL_SAMPLE_SHADING_OES = 35894;
    public static final int GL_NUM_VIRTUAL_PAGE_SIZES_EXT = 37288;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS_EXT = 37655;
    public static final int GL_SGX_BINARY_IMG = 35850;
    public static final int GL_SAMPLER_BUFFER_OES = 36290;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x6_KHR = 37812;
    public static final int GL_TESS_EVALUATION_SHADER_EXT = 36487;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x5x4_OES = 37829;
    public static final int GL_COLOR_BUFFER_BIT2_QCOM = 4;
    public static final int GL_LIGHTEN = 37528;
    public static final int GL_FRACTIONAL_EVEN_EXT = 36476;
    public static final int GL_MULTISAMPLE_BUFFER_BIT4_QCOM = 268435456;
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS_OES = 37080;
    public static final int GL_ATC_RGBA_INTERPOLATED_ALPHA_AMD = 34798;
    public static final int GL_FLOAT_MAT4x2_NV = 35689;
    public static final int GL_UNDEFINED_VERTEX_EXT = 33376;
    public static final int GL_SPARSE_TEXTURE_FULL_ARRAY_CUBE_MIPMAPS_EXT = 37289;
    public static final int GL_DEPTH_EXT = 6145;
    public static final int GL_TEXTURE_BUFFER_OFFSET_EXT = 37277;
    public static final int GL_TESS_CONTROL_SHADER_EXT = 36488;
    public static final int GL_PRIMITIVES_GENERATED_EXT = 35975;
    public static final int GL_TEXTURE_HEIGHT_QCOM = 35795;
    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_NV = 37693;
    public static final int GL_VIEWPORT_BOUNDS_RANGE_NV = 33373;
    public static final int GL_PRIMITIVE_BOUNDING_BOX_OES = 37566;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_OES = 36320;
    public static final int GL_FLOAT_MAT2x3_NV = 35685;
    public static final int GL_FIRST_VERTEX_CONVENTION_EXT = 36429;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8_KHR = 37850;
    public static final int GL_STENCIL_SAMPLES_NV = 37678;
    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS_EXT = 36483;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS_OES = 36489;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT_EXT = 37279;
    public static final int GL_COMPRESSED_RGBA_ASTC_4x4x3_OES = 37826;
    public static final int GL_ALPHA_TEST_QCOM = 3008;
    public static final int GL_ETC1_RGB8_OES = 36196;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_IMG = 33637;
    public static final int GL_PATCH_VERTICES_OES = 36466;
    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS_OES = 37068;
    public static final int GL_MULTIPLY = 37524;
    public static final int GL_STENCIL_BUFFER_BIT0_QCOM = 65536;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x4_KHR = 37809;
    public static final int GL_MULTISAMPLE_RASTERIZATION_ALLOWED_EXT = 37675;
    public static final int GL_TEXTURE_SRGB_DECODE_EXT = 35400;
    public static final int GL_PERFMON_GLOBAL_MODE_QCOM = 36768;
    public static final int GL_TEXTURE_INTERNAL_FORMAT_QCOM = 35797;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_NV = 36182;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER_EXT = 37640;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_OES = 36312;
    public static final int GL_R11F_G11F_B10F_APPLE = 35898;
    public static final int GL_SLUMINANCE_NV = 35910;
    public static final int GL_RGBA16_SNORM_EXT = 36763;
    public static final int GL_READ_BUFFER_NV = 3074;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_OES = 36264;
    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static final int GL_COLOR_BUFFER_BIT5_QCOM = 32;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_EXT = 36876;
    public static final int GL_TEXTURE_BUFFER_OFFSET_OES = 37277;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_SAMPLES_EXT = 36204;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT3_NV = 35918;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x8_KHR = 37815;
    public static final int GL_RGB9_E5_APPLE = 35901;
    public static final int GL_MIXED_STENCIL_SAMPLES_SUPPORTED_NV = 37680;
    public static final int GL_SGX_PROGRAM_BINARY_IMG = 37168;
    public static final int GL_COMPRESSED_RGBA_ASTC_3x3x3_OES = 37824;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_EXT = 35881;
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV_APPLE = 35899;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_NV = 37696;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4x4_OES = 37860;
    public static final int GL_MULTISAMPLE_BUFFER_BIT1_QCOM = 33554432;
    public static final int GL_COVERAGE_ALL_FRAGMENTS_NV = 36565;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_Y_BITS_NV = 37704;
    public static final int GL_SAMPLER_CUBE_SHADOW_NV = 36293;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS_EXT = 36489;
    public static final int GL_LAST_VERTEX_CONVENTION_EXT = 36430;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH = 33532;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ANGLE = 37795;
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY_OES = 36873;
    public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT_KHR = 8;
    public static final int GL_IMAGE_BUFFER_EXT = 36945;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_NV = 35070;
    public static final int GL_GEOMETRY_LINKED_OUTPUT_TYPE_EXT = 35096;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x6x6_OES = 37833;
    public static final int GL_CLIP_DISTANCE6_APPLE = 12294;
    public static final int GL_COMPRESSED_RGBA_PVRTC_2BPPV2_IMG = 37175;
    public static final int GL_TESS_CONTROL_SHADER_BIT_OES = 8;
    public static final int GL_DIFFERENCE = 37534;
    public static final int GL_QUERY_NO_WAIT = 36372;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT_EXT = 16384;
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY_OES = 37122;
    public static final int GL_CLIP_DISTANCE0_APPLE = 12288;
    public static final int GL_CLIP_DISTANCE7_APPLE = 12295;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS_OES = 36480;
    public static final int GL_CLIP_DISTANCE1_APPLE = 12289;
    public static final int GL_DRAW_BUFFER_EXT = 3073;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS_OES = 37155;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_NV = 37697;
    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER_EXT = 36967;
    public static final int GL_CLIP_DISTANCE5_APPLE = 12293;
    public static final int GL_CONTEXT_LOST = 1287;
    public static final int GL_CLIP_DISTANCE2_APPLE = 12290;
    public static final int GL_R16_EXT = 33322;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS_EXT = 35068;
    public static final int GL_TESS_EVALUATION_SHADER_BIT_EXT = 16;
    public static final int GL_PACK_REVERSE_ROW_ORDER_ANGLE = 37796;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ANGLE = 35070;
    public static final int GL_CLIP_DISTANCE3_APPLE = 12291;
    public static final int GL_STENCIL_BUFFER_BIT6_QCOM = 4194304;
    public static final int GL_OVERLAY = 37526;
    public static final int GL_TEXTURE_OBJECT_VALID_QCOM = 35803;
    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS_EXT = 36486;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_NV = 37695;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_OES = 36321;
    public static final int GL_CLIP_DISTANCE4_APPLE = 12292;
    public static final int GL_MAX_SHADER_PIXEL_LOCAL_STORAGE_SIZE_EXT = 36711;
    public static final int GL_DEPTH_BUFFER_BIT2_QCOM = 1024;
    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS_EXT = 34924;
    public static final int GL_COVERAGE_BUFFER_BIT_NV = 32768;
    public static final int GL_DARKEN = 37527;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_EXT = 36320;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS_EXT = 36480;
    public static final int GL_COPY_WRITE_BUFFER_NV = 36663;
    public static final int GL_VIRTUAL_PAGE_SIZE_X_EXT = 37269;
    public static final int GL_COMPRESSED_RGBA_ASTC_8x6_KHR = 37814;
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY_EXT = 36873;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW_OES = 36877;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING_NV = 36006;
    public static final int GL_COMPRESSED_RGB_PVRTC_2BPPV1_IMG = 35841;
    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS_EXT = 37068;
    public static final int GL_STENCIL_BUFFER_BIT3_QCOM = 524288;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS_EXT = 37588;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12_KHR = 37853;
    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS_OES = 36485;
    public static final int GL_GEOMETRY_SHADER_INVOCATIONS_EXT = 34943;
    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_EXT = 37273;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_NV = 37694;
    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER_OES = 36967;
    public static final int GL_COLOR_BUFFER_BIT6_QCOM = 64;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS_EXT = 36490;
    public static final int GL_STATE_RESTORE = 35804;
    public static final int GL_QUERY_BY_REGION_WAIT = 36373;
    public static final int GL_VERTEX_ARRAY_BINDING_OES = 34229;
    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS_EXT = 37069;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY_OES = 36874;
    public static final int GL_TESS_GEN_VERTEX_ORDER_EXT = 36472;
    public static final int GL_FRACTIONAL_EVEN_OES = 36476;
    public static final int GL_SAMPLE_ALPHA_TO_ONE_EXT = 32927;
    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_NV = 37699;
    public static final int GL_PATCH_VERTICES_EXT = 36466;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4x3_OES = 37858;
    public static final int GL_DEPTH_BUFFER_BIT3_QCOM = 2048;
    public static final int GL_HSL_LUMINOSITY = 37552;
    public static final int GL_FETCH_PER_SAMPLE_ARM = 36709;
    public static final int GL_HALF_FLOAT_OES = 36193;
    public static final int GL_DEPTH_BUFFER_BIT1_QCOM = 512;
    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS_EXT = 36382;
    public static final int GL_ATC_RGBA_EXPLICIT_ALPHA_AMD = 35987;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING_ANGLE = 36006;
    public static final int GL_MULTISAMPLE_BUFFER_BIT6_QCOM = 1073741824;
    public static final int GL_SKIP_DECODE_EXT = 35402;
    public static final int GL_SAMPLER_EXTERNAL_OES = 36198;
    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE_EXT = 33311;
    public static final int GL_RGBA8_SNORM = 36759;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER_OES = 37640;
    public static final int GL_INT_IMAGE_BUFFER_EXT = 36956;
    public static final int GL_FRAGMENT_SHADER_FRAMEBUFFER_FETCH_MRT_ARM = 36710;
    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS_EXT = 35372;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS_EXT = 37650;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_IMG = 37172;
    public static final int GL_GEOMETRY_SHADER_BIT_OES = 4;
    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV2_IMG = 37872;
    public static final int GL_TESS_CONTROL_SHADER_OES = 36488;
    public static final int GL_TRIANGLES_ADJACENCY_OES = 12;
    public static final int GL_PRIMITIVE_BOUNDING_BOX_EXT = 37566;
    public static final int GL_TEXTURE_BUFFER_BINDING_EXT = 35882;
    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS_OES = 37067;
    public static final int GL_COLOR_ATTACHMENT_EXT = 37104;
    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED_OES = 33313;
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS_EXT = 37081;
    public static final int GL_SAMPLER_BUFFER_EXT = 36290;
    public static final int GL_FLOAT_MAT4x3_NV = 35690;
    public static final int GL_TESS_GEN_POINT_MODE_OES = 36473;
    public static final int GL_TEXTURE_2D_ARRAY = 35866;
    public static final int GL_TESS_EVALUATION_SHADER_OES = 36487;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY_OES = 37125;
    public static final int GL_3DC_X_AMD = 34809;
    public static final int GL_GEOMETRY_SHADER_EXT = 36313;
    public static final int GL_MULTISAMPLE_EXT = 32925;
    public static final int GL_BGRA_IMG = 32993;
    public static final int GL_INT_IMAGE_BUFFER_OES = 36956;
    public static final int GL_LINE_NV = 6913;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_EXT = 36321;
    public static final int GL_COVERAGE_MODULATION_NV = 37682;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY_OES = 36879;
    public static final int GL_TEXTURE_DEPTH_QCOM = 35796;
    public static final int GL_PRIMITIVES_GENERATED_OES = 35975;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_3x3x3_OES = 37856;
    public static final int GL_LAST_VERTEX_CONVENTION_OES = 36430;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS_EXT = 37155;
    public static final int GL_SMAPHS_PROGRAM_BINARY_DMP = 37458;
    public static final int GL_STENCIL_BUFFER_BIT1_QCOM = 131072;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS_OES = 37655;
    public static final int GL_IMAGE_BUFFER_OES = 36945;
    public static final int GL_ATC_RGB_AMD = 35986;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER_OES = 37639;
    public static final int GL_COLOR_SAMPLES_NV = 36384;
    public static final int GL_COVERAGE_SAMPLES_NV = 36564;
    public static final int GL_FRACTIONAL_ODD_OES = 36475;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8_KHR = 37847;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_EXT = 36182;
    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS_OES = 35372;
    public static final int GL_TEXTURE_NUM_LEVELS_QCOM = 35801;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x5x5_OES = 37830;
    public static final int GL_RG8_SNORM = 36757;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_EXT = 36312;
    public static final int GL_TESS_EVALUATION_SHADER_BIT_OES = 16;
    public static final int GL_SAMPLE_LOCATION_NV = 36432;
    public static final int GL_COMPRESSED_SRGB_PVRTC_2BPPV1_EXT = 35412;
    public static final int GL_FRAMEBUFFER_UNDEFINED_OES = 33305;
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS_EXT = 37079;
    public static final int GL_SHADER_PIXEL_LOCAL_STORAGE_EXT = 36708;
    public static final int GL_MAX_VIEWPORTS_NV = 33371;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW_EXT = 36877;
    public static final int GL_HSL_COLOR = 37551;
    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS_OES = 36442;
    public static final int GL_READ_FRAMEBUFFER_BINDING_NV = 36010;
    public static final int GL_TEXTURE_SPARSE_EXT = 37286;
    public static final int GL_TEXTURE_BUFFER_OES = 35882;
    public static final int GL_MAX = 32776;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_EXT = 36264;
    public static final int GL_READ_BUFFER_EXT = 3074;
    public static final int GL_COMPRESSED_RGBA_PVRTC_4BPPV1_IMG = 35842;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_OES = 35881;
    public static final int GL_ONE_MINUS_SRC1_ALPHA_EXT = 35067;
    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS_OES = 37069;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY_EXT = 36874;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_X_BITS_NV = 37703;
    public static final int GL_GEOMETRY_LINKED_INPUT_TYPE_EXT = 35095;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6_KHR = 37846;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4_KHR = 37841;
    public static final int GL_LOCATION_INDEX_EXT = 37647;
    public static final int GL_FLOAT_MAT3x4_NV = 35688;
    public static final int GL_RGB16_SNORM_EXT = 36762;
    public static final int GL_RENDERBUFFER_SAMPLES_EXT = 36011;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6x5_OES = 37864;
    public static final int GL_GEOMETRY_SHADER_INVOCATIONS_OES = 34943;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5_KHR = 37843;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS_OES = 37583;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x8_KHR = 37818;
    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS_EXT = 36482;
    public static final int GL_COLOR_BUFFER_BIT3_QCOM = 8;
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS_OES = 37081;
    public static final int GL_COVERAGE_BUFFERS_NV = 36563;
    public static final int GL_ISOLINES_EXT = 36474;
    public static final int GL_MAX_SAMPLES_EXT = 36183;
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV_APPLE = 35902;
    public static final int GL_TESS_GEN_POINT_MODE_EXT = 36473;
    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS_EXT = 36442;
    public static final int GL_RGB16_EXT = 32852;
    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS_OES = 36445;
    public static final int GL_FRAGMENT_SHADER_DISCARDS_SAMPLES_EXT = 35410;
    public static final int GL_VIRTUAL_PAGE_SIZE_INDEX_EXT = 37287;
    public static final int GL_SLUMINANCE_ALPHA_NV = 35908;
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS_OES = 37079;
    public static final int GL_QUADS_EXT = 7;
    public static final int GL_TEXTURE_BUFFER_SIZE_OES = 37278;
    public static final int GL_MAX_PATCH_VERTICES_OES = 36477;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5_KHR = 37848;
    public static final int GL_MAX_VIEWS_OVR = 38449;
    public static final int GL_RENDERBUFFER_SAMPLES_IMG = 37171;
    public static final int GL_COMPRESSED_RGBA_PVRTC_2BPPV1_IMG = 35843;
    public static final int GL_COVERAGE_ATTACHMENT_NV = 36562;
    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_EXT = 37272;
    public static final int GL_R8_SNORM = 36756;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS_EXT = 36479;
    public static final int GL_DEPTH_BUFFER_BIT6_QCOM = 16384;
    public static final int GL_GEOMETRY_LINKED_VERTICES_OUT_OES = 35094;
    public static final int GL_COVERAGE_EDGE_FRAGMENTS_NV = 36566;
    public static final int GL_TESS_GEN_SPACING_EXT = 36471;
    public static final int GL_DRAW_FRAMEBUFFER_ANGLE = 36009;
    public static final int GL_ONE_MINUS_SRC1_COLOR_EXT = 35066;
    public static final int GL_TEXTURE_IMAGE_VALID_QCOM = 35800;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS_EXT = 37589;
    public static final int GL_ALPHA_TEST_FUNC_QCOM = 3009;
    public static final int GL_COLORDODGE = 37529;
    public static final int GL_COMPRESSED_RGBA_PVRTC_4BPPV2_IMG = 37176;
    public static final int GL_TEXTURE_BUFFER_BINDING_OES = 35882;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_OES = 13;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW_NV = 36292;
    public static final int GL_CLAMP_TO_BORDER_NV = 33069;
    public static final int GL_INT_SAMPLER_BUFFER_EXT = 36304;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x5_KHR = 37816;
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_OES = 35883;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS_NV = 33372;
    public static final int GL_DEPTH_SAMPLES_NV = 37677;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS_OES = 36490;
    public static final int GL_RGBA16_EXT = 32859;
    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS_OES = 34925;
    public static final int GL_COMPRESSED_RGBA_ASTC_10x10_KHR = 37819;
    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 33313;
    public static final int GL_MULTISAMPLE_BUFFER_BIT2_QCOM = 67108864;
    public static final int GL_TEXTURE_WIDTH_QCOM = 35794;
    public static final int GL_COLOR_BUFFER_BIT4_QCOM = 16;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS_OES = 37581;
    public static final int GL_EXCLUSION = 37536;
    public static final int GL_COMPRESSED_RGBA_ASTC_5x5_KHR = 37810;
    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES_OES = 36469;
    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS_OES = 34924;
    public static final int GL_IMAGE_CUBE_MAP_ARRAY_EXT = 36948;
    public static final int GL_STENCIL_BUFFER_BIT7_QCOM = 8388608;
    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS_EXT = 37067;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY_EXT = 36879;
    public static final int GL_COVERAGE_COMPONENT4_NV = 36561;
    public static final int GL_MULTIVIEW_EXT = 37105;
    public static final int GL_SLUMINANCE8_ALPHA8_NV = 35909;
    public static final int GL_COLOR_EXT = 6144;
    public static final int GL_POLYGON_OFFSET_POINT_NV = 10753;
    public static final int GL_CLIENT_STORAGE_BIT_EXT = 512;
    public static final int GL_LINES_ADJACENCY_OES = 10;
    public static final int GL_COMPRESSED_SRGB_PVRTC_4BPPV1_EXT = 35413;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x5_KHR = 37811;
    public static final int GL_STENCIL_BUFFER_BIT5_QCOM = 2097152;
    public static final int GL_HSL_HUE = 37549;
    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_OES = 35885;
    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS_EXT = 36481;
    public static final int GL_TEXTURE_TARGET_QCOM = 35802;
    public static final int GL_DMP_PROGRAM_BINARY_DMP = 37459;
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 36970;
    public static final int GL_MAX_RASTER_SAMPLES_EXT = 37673;
    public static final int GL_COVERAGE_COMPONENT_NV = 36560;
    public static final int GL_PATCHES_OES = 14;
    public static final int GL_COPY_READ_BUFFER_NV = 36662;
    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET_OES = 36444;
    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS_OES = 35378;
    public static final int GL_TEXTURE_BORDER_COLOR_NV = 4100;
    public static final int GL_STENCIL_INDEX_OES = 6401;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS_OES = 37650;
    public static final int GL_RG16_EXT = 33324;
    public static final int GL_DEPTH_COMPONENT16_NONLINEAR_NV = 36396;
    public static final int GL_COVERAGE_MODULATION_TABLE_SIZE_NV = 37683;
    public static final int GL_FRACTIONAL_ODD_EXT = 36475;
    public static final int GL_LAYER_PROVOKING_VERTEX_EXT = 33374;
    public static final int GL_MAP_COHERENT_BIT_EXT = 128;
    public static final int GL_SRC1_ALPHA_EXT = 34185;
    public static final int GL_COLOR_BUFFER_BIT1_QCOM = 2;
    public static final int GL_TESS_GEN_MODE_EXT = 36470;
    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS_EXT = 34925;
    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS_EXT = 36383;
    public static final int GL_TEXTURE_BINDING_BUFFER_OES = 35884;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5_NV = 35919;
    public static final int GL_TEXTURE_SAMPLES_IMG = 37174;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS_EXT = 37581;
    public static final int GL_TEXTURE_USAGE_ANGLE = 37794;
    public static final int GL_GCCSO_SHADER_BINARY_FJ = 37472;
    public static final int GL_R16_SNORM_EXT = 36760;
    public static final int GL_MAX_SAMPLES_IMG = 37173;
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 37131;
    public static final int GL_PATCHES_EXT = 14;
    public static final int GL_BUFFER_STORAGE_FLAGS_EXT = 33312;
    public static final int GL_MAX_CLIP_DISTANCES_APPLE = 3378;
    public static final int GL_GEOMETRY_SHADER_OES = 36313;
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_EXT = 35883;
    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET_OES = 36443;
    public static final int GL_FRAGMENT_COVERAGE_COLOR_NV = 37598;
    public static final int GL_RG16_SNORM_EXT = 36761;
    public static final int GL_VIRTUAL_PAGE_SIZE_Z_EXT = 37271;
    public static final int GL_WRITEONLY_RENDERING_QCOM = 34851;
    public static final int GL_ALPHA_TEST_REF_QCOM = 3010;
    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_EXT = 35885;
    public static final int GL_TEXTURE_BINDING_EXTERNAL_OES = 36199;
    public static final int GL_MAX_SAMPLES_NV = 36183;
    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5x5_OES = 37862;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x5x5_OES = 37831;
    public static final int GL_CPU_OPTIMIZED_QCOM = 36785;
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS_EXT = 37583;
    public static final int GL_SMAPHS30_PROGRAM_BINARY_DMP = 37457;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 37132;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER_EXT = 37639;
    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS_OES = 36482;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR = 33531;
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS_OES = 37587;
    public static final int GL_EFFECTIVE_RASTER_SAMPLES_EXT = 37676;
    public static final int GL_GEOMETRY_LINKED_INPUT_TYPE_OES = 35095;
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS_OES = 37588;
    public static final int GL_QUADS_OES = 7;
    public static final int GL_RENDER_DIRECT_TO_FRAMEBUFFER_QCOM = 36787;
    public static final int GL_TESS_GEN_SPACING_OES = 36471;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10_KHR = 37852;
    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 36959;
    public static final int GL_TEXTURE_BUFFER_EXT = 35882;
    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS_EXT = 36485;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_BASE_VIEW_INDEX_OVR = 38450;
    public static final int GL_COMPRESSED_RGBA_ASTC_6x6x5_OES = 37832;
    public static final int GL_BINNING_CONTROL_HINT_QCOM = 36784;
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY_OES = 36878;
    public static final int GL_SCREEN = 37525;
    public static final int GL_MULTISAMPLE_BUFFER_BIT5_QCOM = 536870912;
    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_NV = 37698;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 37133;
    public static final int GL_MALI_SHADER_BINARY_ARM = 36704;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT1_NV = 35917;
    public static final int GL_NVIDIA_PLATFORM_BINARY_NV = 35083;
    
    void glBlendBarrier();
    
    void glGetnUniformuiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetnUniformuiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glEGLImageTargetTexture2DOES(final int p0, final long p1);
    
    void glEGLImageTargetRenderbufferStorageOES(final int p0, final long p1);
    
    void glEnableiOES(final int p0, final int p1);
    
    void glDisableiOES(final int p0, final int p1);
    
    void glBlendEquationiOES(final int p0, final int p1);
    
    void glBlendEquationSeparateiOES(final int p0, final int p1, final int p2);
    
    void glBlendFunciOES(final int p0, final int p1, final int p2);
    
    void glBlendFuncSeparateiOES(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glColorMaskiOES(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4);
    
    boolean glIsEnablediOES(final int p0, final int p1);
    
    void glDrawElementsBaseVertexOES(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawRangeElementsBaseVertexOES(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5, final int p6);
    
    void glDrawElementsInstancedBaseVertexOES(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5);
    
    void glMultiDrawElementsBaseVertexOES(final int p0, final IntBuffer p1, final int p2, final PointerBuffer p3, final int p4, final IntBuffer p5);
    
    void glMultiDrawElementsBaseVertexOES(final int p0, final int[] p1, final int p2, final int p3, final PointerBuffer p4, final int p5, final int[] p6, final int p7);
    
    void glFramebufferTextureOES(final int p0, final int p1, final int p2, final int p3);
    
    void glPrimitiveBoundingBoxOES(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    void glMinSampleShadingOES(final float p0);
    
    void glPatchParameteriOES(final int p0, final int p1);
    
    void glTexBufferOES(final int p0, final int p1, final int p2);
    
    void glTexBufferRangeOES(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    void glTexStorage3DMultisampleOES(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glTextureView(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glBindVertexArrayOES(final int p0);
    
    void glDeleteVertexArraysOES(final int p0, final IntBuffer p1);
    
    void glDeleteVertexArraysOES(final int p0, final int[] p1, final int p2);
    
    void glGenVertexArraysOES(final int p0, final IntBuffer p1);
    
    void glGenVertexArraysOES(final int p0, final int[] p1, final int p2);
    
    boolean glIsVertexArrayOES(final int p0);
    
    void glBlitFramebufferANGLE(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9);
    
    void glDrawArraysInstancedANGLE(final int p0, final int p1, final int p2, final int p3);
    
    void glDrawElementsInstancedANGLE(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glVertexAttribDivisorANGLE(final int p0, final int p1);
    
    void glGetTranslatedShaderSourceANGLE(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetTranslatedShaderSourceANGLE(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glCopyTextureLevelsAPPLE(final int p0, final int p1, final int p2, final int p3);
    
    void glResolveMultisampleFramebuffer();
    
    void glBindFragDataLocationIndexedEXT(final int p0, final int p1, final int p2, final ByteBuffer p3);
    
    void glBindFragDataLocationIndexedEXT(final int p0, final int p1, final int p2, final byte[] p3, final int p4);
    
    void glBindFragDataLocationEXT(final int p0, final int p1, final ByteBuffer p2);
    
    void glBindFragDataLocationEXT(final int p0, final int p1, final byte[] p2, final int p3);
    
    int glGetProgramResourceLocationIndexEXT(final int p0, final int p1, final ByteBuffer p2);
    
    int glGetProgramResourceLocationIndexEXT(final int p0, final int p1, final byte[] p2, final int p3);
    
    int glGetFragDataIndexEXT(final int p0, final ByteBuffer p1);
    
    int glGetFragDataIndexEXT(final int p0, final byte[] p1, final int p2);
    
    void glBufferStorageEXT(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glDiscardFramebufferEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glDiscardFramebufferEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glEnableiEXT(final int p0, final int p1);
    
    void glDisableiEXT(final int p0, final int p1);
    
    void glBlendEquationiEXT(final int p0, final int p1);
    
    void glBlendEquationSeparateiEXT(final int p0, final int p1, final int p2);
    
    void glBlendFunciEXT(final int p0, final int p1, final int p2);
    
    void glBlendFuncSeparateiEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glColorMaskiEXT(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4);
    
    boolean glIsEnablediEXT(final int p0, final int p1);
    
    void glDrawElementsBaseVertexEXT(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawRangeElementsBaseVertexEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5, final int p6);
    
    void glDrawElementsInstancedBaseVertexEXT(final int p0, final int p1, final int p2, final Buffer p3, final int p4, final int p5);
    
    void glMultiDrawElementsBaseVertexEXT(final int p0, final IntBuffer p1, final int p2, final PointerBuffer p3, final int p4, final IntBuffer p5);
    
    void glMultiDrawElementsBaseVertexEXT(final int p0, final int[] p1, final int p2, final int p3, final PointerBuffer p4, final int p5, final int[] p6, final int p7);
    
    void glFramebufferTextureEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribDivisor(final int p0, final int p1);
    
    void glMultiDrawArraysIndirectEXT(final int p0, final Buffer p1, final int p2, final int p3);
    
    void glMultiDrawElementsIndirectEXT(final int p0, final int p1, final Buffer p2, final int p3, final int p4);
    
    void glRenderbufferStorageMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFramebufferTexture2DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glReadBufferIndexedEXT(final int p0, final int p1);
    
    void glDrawBuffersIndexedEXT(final int p0, final IntBuffer p1, final IntBuffer p2);
    
    void glDrawBuffersIndexedEXT(final int p0, final int[] p1, final int p2, final int[] p3, final int p4);
    
    void glGetIntegeri_vEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetIntegeri_vEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glPrimitiveBoundingBoxEXT(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    void glRasterSamplesEXT(final int p0, final boolean p1);
    
    void glTexPageCommitmentEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8);
    
    void glPatchParameteriEXT(final int p0, final int p1);
    
    void glTexBufferEXT(final int p0, final int p1, final int p2);
    
    void glTexBufferRangeEXT(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    void glRenderbufferStorageMultisampleIMG(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFramebufferTexture2DMultisampleIMG(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glBeginConditionalRender(final int p0, final int p1);
    
    void glEndConditionalRender();
    
    void glSubpixelPrecisionBiasNV(final int p0, final int p1);
    
    void glCopyBufferSubDataNV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glCoverageMaskNV(final boolean p0);
    
    void glCoverageOperationNV(final int p0);
    
    void glDrawArraysInstancedNV(final int p0, final int p1, final int p2, final int p3);
    
    void glDrawElementsInstancedNV(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glFragmentCoverageColorNV(final int p0);
    
    void glBlitFramebufferNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9);
    
    void glCoverageModulationTableNV(final int p0, final FloatBuffer p1);
    
    void glCoverageModulationTableNV(final int p0, final float[] p1, final int p2);
    
    void glGetCoverageModulationTableNV(final int p0, final FloatBuffer p1);
    
    void glGetCoverageModulationTableNV(final int p0, final float[] p1, final int p2);
    
    void glCoverageModulationNV(final int p0);
    
    void glRenderbufferStorageMultisampleNV(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexAttribDivisorNV(final int p0, final int p1);
    
    void glUniformMatrix2x3fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2x3fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3x2fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3x2fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix2x4fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2x4fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4x2fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4x2fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3x4fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3x4fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4x3fvNV(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4x3fvNV(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glPolygonModeNV(final int p0, final int p1);
    
    void glReadBufferNV(final int p0);
    
    void glFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glNamedFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glNamedFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glResolveDepthValuesNV();
    
    void glViewportArrayvNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glViewportArrayvNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glViewportIndexedfNV(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glViewportIndexedfvNV(final int p0, final FloatBuffer p1);
    
    void glViewportIndexedfvNV(final int p0, final float[] p1, final int p2);
    
    void glScissorArrayvNV(final int p0, final int p1, final IntBuffer p2);
    
    void glScissorArrayvNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glScissorIndexedNV(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glScissorIndexedvNV(final int p0, final IntBuffer p1);
    
    void glScissorIndexedvNV(final int p0, final int[] p1, final int p2);
    
    void glDepthRangeArrayfvNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glDepthRangeArrayfvNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glDepthRangeIndexedfNV(final int p0, final float p1, final float p2);
    
    void glGetFloati_vNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetFloati_vNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glEnableiNV(final int p0, final int p1);
    
    void glDisableiNV(final int p0, final int p1);
    
    boolean glIsEnablediNV(final int p0, final int p1);
    
    void glFramebufferTextureMultiviewOVR(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glAlphaFuncQCOM(final int p0, final float p1);
    
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
    
    void glDrawElements(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glVertexAttribPointer(final int p0, final int p1, final int p2, final boolean p3, final int p4, final Buffer p5);
}
