// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.nio.PointerBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.ByteBuffer;

public interface GL2 extends GL2ES1, GL2GL3
{
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_SAMPLE_LOCATION_ARB = 36432;
    public static final int GL_INTENSITY8UI = 36223;
    public static final int GL_WEIGHT_ARRAY_POINTER_ARB = 34476;
    public static final int GL_TEXTURE_GEN_MODE = 9472;
    public static final int GL_MAGNITUDE_BIAS_NV = 34584;
    public static final int GL_VERTEX_SHADER_INSTRUCTIONS_EXT = 34767;
    public static final int GL_GPU_MEMORY_INFO_CURRENT_AVAILABLE_VIDMEM_NVX = 36937;
    public static final int GL_TESS_CONTROL_PROGRAM_NV = 35102;
    public static final int GL_ACCUM_CLEAR_VALUE = 2944;
    public static final int GL_VERTEX_ATTRIB_MAP2_SIZE_APPLE = 35334;
    public static final int GL_MULTISAMPLE_FILTER_HINT_NV = 34100;
    public static final int GL_TESS_EVALUATION_PROGRAM_NV = 35103;
    public static final int GL_RGBA_DXT5_S3TC = 33700;
    public static final int GL_MODELVIEW8_ARB = 34600;
    public static final int GL_SOURCE0_RGB = 34176;
    public static final int GL_LUMINANCE_ALPHA8I = 36243;
    public static final int GL_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 34804;
    public static final int GL_BLUE_MAX_CLAMP_INGR = 34150;
    public static final int GL_POST_COLOR_MATRIX_RED_SCALE = 32948;
    public static final int GL_FILL_RECTANGLE_NV = 37692;
    public static final int GL_PROGRAM_ERROR_STRING_ARB = 34932;
    public static final int GL_INT64_VEC2_NV = 36841;
    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_ARB = 34888;
    public static final int GL_ALPHA8UI = 36222;
    public static final int GL_MIRROR_CLAMP_EXT = 34626;
    public static final int GL_MAX_PROGRAM_LOCAL_PARAMETERS_ARB = 34996;
    public static final int GL_RGBA_FLOAT32_APPLE = 34836;
    public static final int GL_TESS_CONTROL_PROGRAM_PARAMETER_BUFFER_NV = 35956;
    public static final int GL_CUBIC_EXT = 33588;
    public static final int GL_FOG_COORD_ARRAY_TYPE = 33876;
    public static final int GL_PERFQUERY_COUNTER_DURATION_RAW_INTEL = 38130;
    public static final int GL_DSDT_NV = 34549;
    public static final int GL_MATRIX22_ARB = 35030;
    public static final int GL_DEPENDENT_GB_TEXTURE_2D_NV = 34538;
    public static final int GL_SLUMINANCE8 = 35911;
    public static final int GL_WARP_SIZE_NV = 37689;
    public static final int GL_PERFQUERY_SINGLE_CONTEXT_INTEL = 0;
    public static final int GL_MAX_GEOMETRY_PROGRAM_INVOCATIONS_NV = 36442;
    public static final int GL_INTENSITY_SNORM = 36883;
    public static final int GL_EVAL_VERTEX_ATTRIB2_NV = 34504;
    public static final int GL_PIXEL_MAP_A_TO_A = 3193;
    public static final int GL_BLEND_COLOR_COMMAND_NV = 11;
    public static final int GL_SHARED_EDGE_NV = 192;
    public static final int GL_BUFFER_SERIALIZED_MODIFY = 35346;
    public static final int GL_RGB_FLOAT16_APPLE = 34843;
    public static final int GL_VIDEO_CAPTURE_SURFACE_ORIGIN_NV = 36924;
    public static final int GL_FLOAT_MAT4_ARB = 35676;
    public static final int GL_FLOAT_RGBA16_NV = 34954;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET_NV = 35077;
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 32908;
    public static final int GL_OP_MOV_EXT = 34713;
    public static final int GL_DEPENDENT_HILO_TEXTURE_2D_NV = 34904;
    public static final int GL_COLOR_TABLE_ALPHA_SIZE = 32989;
    public static final int GL_OUTPUT_TEXTURE_COORD5_EXT = 34722;
    public static final int GL_OP_MUL_EXT = 34694;
    public static final int GL_NORMALIZED_RANGE_EXT = 34784;
    public static final int GL_MAX_PROGRAM_SUBROUTINE_NUM_NV = 36677;
    public static final int GL_ALPHA32UI = 36210;
    public static final int GL_OUTPUT_TEXTURE_COORD14_EXT = 34731;
    public static final int GL_COLOR_MATERIAL_FACE = 2901;
    public static final int GL_EVAL_2D_NV = 34496;
    public static final int GL_FLOAT_VEC4_ARB = 35666;
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 34971;
    public static final int GL_OP_RECIP_SQRT_EXT = 34709;
    public static final int GL_DOT_PRODUCT_CONST_EYE_REFLECT_CUBE_MAP_NV = 34547;
    public static final int GL_TERMINATE_SEQUENCE_COMMAND_NV = 0;
    public static final int GL_DOT_PRODUCT_DEPTH_REPLACE_NV = 34541;
    public static final int GL_MATRIX_PALETTE_ARB = 34880;
    public static final int GL_TEXTURE_BINDING_RENDERBUFFER_NV = 36435;
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 33882;
    public static final int GL_SIGNED_RGBA8_NV = 34556;
    public static final int GL_FOG_COORDINATE = 33873;
    public static final int GL_LIST_MODE = 2864;
    public static final int GL_RGB_S3TC = 33696;
    public static final int GL_MATRIX3_ARB = 35011;
    public static final int GL_OFFSET_TEXTURE_2D_MATRIX_NV = 34529;
    public static final int GL_SAMPLER_3D_ARB = 35679;
    public static final int GL_WIDE_LINE_HINT_PGI = 107042;
    public static final int GL_MAX_PROGRAM_NATIVE_ATTRIBS_ARB = 34991;
    public static final int GL_UNSIGNED_INT8_VEC4_NV = 36847;
    public static final int GL_CONSERVATIVE_RASTERIZATION_NV = 37702;
    public static final int GL_RGB4_S3TC = 33697;
    public static final int GL_AUX_DEPTH_STENCIL_APPLE = 35348;
    public static final int GL_SHADING_LANGUAGE_VERSION_ARB = 35724;
    public static final int GL_VERTEX_PROGRAM_PARAMETER_BUFFER_NV = 36258;
    public static final int GL_PROGRAM_NATIVE_ALU_INSTRUCTIONS_ARB = 34824;
    public static final int GL_LUMINANCE_SNORM = 36881;
    public static final int GL_TEXTURE_STORAGE_HINT_APPLE = 34236;
    public static final int GL_OP_INDEX_EXT = 34690;
    public static final int GL_RESAMPLE_ZERO_FILL_OML = 35207;
    public static final int GL_SAMPLE_POSITION_NV = 36432;
    public static final int GL_OFFSET_HILO_TEXTURE_2D_NV = 34900;
    public static final int GL_COLOR_SUM = 33880;
    public static final int GL_Z4Y12Z4CB12Z4Y12Z4CR12_422_NV = 36917;
    public static final int GL_MATRIX31_ARB = 35039;
    public static final int GL_RENDERBUFFER_COVERAGE_SAMPLES_NV = 36011;
    public static final int GL_DRAW_BUFFER8_ATI = 34861;
    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 33877;
    public static final int GL_DOT_PRODUCT_NV = 34540;
    public static final int GL_TEXTURE_INTENSITY_SIZE = 32865;
    public static final int GL_EVAL_VERTEX_ATTRIB13_NV = 34515;
    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_RECTANGLE_SCALE_NV = 34899;
    public static final int GL_LUMINANCE_ALPHA32I = 36231;
    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_ARB = 34887;
    public static final int GL_EVAL_VERTEX_ATTRIB5_NV = 34507;
    public static final int GL_ALPHA32I = 36228;
    public static final int GL_DSDT_MAG_VIB_NV = 34551;
    public static final int GL_SIGNED_INTENSITY_NV = 34567;
    public static final int GL_TEXTURE_SWIZZLE_A_EXT = 36421;
    public static final int GL_OP_DOT4_EXT = 34693;
    public static final int GL_MAX_PROGRAM_MATRIX_STACK_DEPTH_ARB = 34350;
    public static final int GL_Z4Y12Z4CB12Z4CR12_444_NV = 36919;
    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE_ARB = 34040;
    public static final int GL_INT8_VEC4_NV = 36835;
    public static final int GL_MIN_SPARSE_LEVEL_AMD = 37275;
    public static final int GL_FLOAT_RGBA32_NV = 34955;
    public static final int GL_MAX_PROGRAM_OUTPUT_VERTICES_NV = 35879;
    public static final int GL_OFFSET_TEXTURE_RECTANGLE_SCALE_NV = 34381;
    public static final int GL_MATRIX26_ARB = 35034;
    public static final int GL_FORCE_BLUE_TO_ONE_NV = 34912;
    public static final int GL_CONSERVE_MEMORY_HINT_PGI = 107005;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
    public static final int GL_STREAM_RASTERIZATION_AMD = 37280;
    public static final int GL_OP_CLAMP_EXT = 34702;
    public static final int GL_TRANSPOSE_CURRENT_MATRIX_ARB = 34999;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED_NV = 36387;
    public static final int GL_MATRIX17_ARB = 35025;
    public static final int GL_UNPACK_ROW_BYTES_APPLE = 35350;
    public static final int GL_PERFMON_RESULT_AMD = 35782;
    public static final int GL_LUMINANCE8UI = 36224;
    public static final int GL_OUTPUT_TEXTURE_COORD1_EXT = 34718;
    public static final int GL_ZOOM_X = 3350;
    public static final int GL_ZOOM_Y = 3351;
    public static final int GL_UNPACK_RESAMPLE_OML = 35205;
    public static final int GL_SOFTLIGHT = 37532;
    public static final int GL_VIDEO_COLOR_CONVERSION_MATRIX_NV = 36905;
    public static final int GL_CONVOLUTION_FORMAT = 32791;
    public static final int GL_LINE_STIPPLE_REPEAT = 2854;
    public static final int GL_OP_EXP_BASE_2_EXT = 34705;
    public static final int GL_TRANSPOSE_PROGRAM_MATRIX_EXT = 36398;
    public static final int GL_OUTPUT_FOG_EXT = 34749;
    public static final int GL_LO_BIAS_NV = 34581;
    public static final int GL_OBJECT_INFO_LOG_LENGTH_ARB = 35716;
    public static final int GL_TEXTURE_MATERIAL_PARAMETER_EXT = 33618;
    public static final int GL_SPHERE_MAP = 9218;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_NUM_VIEWS_OVR = 38448;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT3 = 35918;
    public static final int GL_MODELVIEW17_ARB = 34609;
    public static final int GL_COLOR_TABLE = 32976;
    public static final int GL_UNSIGNED_INT64_AMD = 35778;
    public static final int GL_CURRENT_FOG_COORD = 33875;
    public static final int GL_LUMINANCE16_ALPHA16_SNORM = 36890;
    public static final int GL_EMBOSS_MAP_NV = 34143;
    public static final int GL_BOOL_VEC3_ARB = 35672;
    public static final int GL_DATA_BUFFER_AMD = 37201;
    public static final int GL_ARRAY_ELEMENT_LOCK_FIRST_EXT = 33192;
    public static final int GL_OBJECT_COMPILE_STATUS_ARB = 35713;
    public static final int GL_SLUMINANCE8_ALPHA8 = 35909;
    public static final int GL_CULL_VERTEX_EYE_POSITION_EXT = 33195;
    public static final int GL_C4F_N3F_V3F = 10790;
    public static final int GL_SAMPLER_2D_RECT_SHADOW_ARB = 35684;
    public static final int GL_OUTPUT_TEXTURE_COORD18_EXT = 34735;
    public static final int GL_MATRIX13_ARB = 35021;
    public static final int GL_T2F_N3F_V3F = 10795;
    public static final int GL_PROGRAM_ADDRESS_REGISTERS_ARB = 34992;
    public static final int GL_MAX_TEXTURE_COORDS = 34929;
    public static final int GL_MULTISAMPLE_COVERAGE_MODES_NV = 36370;
    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_ARB = 37693;
    public static final int GL_CURRENT_OCCLUSION_QUERY_ID_NV = 34917;
    public static final int GL_CULL_FRAGMENT_NV = 34535;
    public static final int GL_INDEX_WRITEMASK = 3105;
    public static final int GL_OBJECT_VALIDATE_STATUS_ARB = 35715;
    public static final int GL_RASTER_FIXED_SAMPLE_LOCATIONS_EXT = 37674;
    public static final int GL_PIXEL_MODE_BIT = 32;
    public static final int GL_RASTER_SAMPLES_EXT = 37672;
    public static final int GL_TEXTURE_BINDING_RECTANGLE_ARB = 34038;
    public static final int GL_RGBA_UNSIGNED_DOT_PRODUCT_MAPPING_NV = 34521;
    public static final int GL_PROGRAM_NATIVE_TEX_INSTRUCTIONS_ARB = 34825;
    public static final int GL_PURGEABLE_APPLE = 35357;
    public static final int GL_DRAW_ELEMENTS_COMMAND_NV = 2;
    public static final int GL_STRICT_LIGHTING_HINT_PGI = 107031;
    public static final int GL_SIGNED_RGB8_UNSIGNED_ALPHA8_NV = 34573;
    public static final int GL_MAP1_VERTEX_3 = 3479;
    public static final int GL_MAP1_VERTEX_4 = 3480;
    public static final int GL_NATIVE_GRAPHICS_HANDLE_PGI = 107010;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 34938;
    public static final int GL_MAP_ATTRIB_U_ORDER_NV = 34499;
    public static final int GL_FLOAT16_VEC3_NV = 36858;
    public static final int GL_PROGRAM_LENGTH_ARB = 34343;
    public static final int GL_GEOMETRY_PROGRAM_PARAMETER_BUFFER_NV = 36259;
    public static final int GL_PROGRAM_MATRIX_EXT = 36397;
    public static final int GL_SURFACE_STATE_NV = 34539;
    public static final int GL_PERFQUERY_COUNTER_DATA_BOOL32_INTEL = 38140;
    public static final int GL_MAX_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 34801;
    public static final int GL_QUERY_BUFFER_BINDING_AMD = 37267;
    public static final int GL_DEPENDENT_AR_TEXTURE_2D_NV = 34537;
    public static final int GL_TEXTURE_COMPARE_FAIL_VALUE_ARB = 32959;
    public static final int GL_NEGATIVE_W_EXT = 34780;
    public static final int GL_COLOR_SUM_ARB = 33880;
    public static final int GL_VIDEO_COLOR_CONVERSION_MAX_NV = 36906;
    public static final int GL_OP_SET_LT_EXT = 34701;
    public static final int GL_ACCUM_BLUE_BITS = 3418;
    public static final int GL_LIST_INDEX = 2867;
    public static final int GL_INDEX_ARRAY_STRIDE = 32902;
    public static final int GL_MAP1_GRID_DOMAIN = 3536;
    public static final int GL_TEXTURE_RANGE_LENGTH_APPLE = 34231;
    public static final int GL_VARIANT_ARRAY_STRIDE_EXT = 34790;
    public static final int GL_INVARIANT_VALUE_EXT = 34794;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_COUNT_EXT = 36265;
    public static final int GL_MATRIX_EXT = 34752;
    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET_NV = 36443;
    public static final int GL_INDEX_BIT_PGI = 524288;
    public static final int GL_SLUMINANCE_ALPHA = 35908;
    public static final long GL_TEXCOORD4_BIT_PGI = 2147483648L;
    public static final int GL_RGBA_INTEGER_MODE = 36254;
    public static final int GL_MODELVIEW0_ARB = 5888;
    public static final int GL_OFFSET_TEXTURE_MATRIX_NV = 34529;
    public static final int GL_COMPRESSED_INTENSITY = 34028;
    public static final int GL_COMPLETION_STATUS_ARB = 37297;
    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_RECTANGLE_NV = 34898;
    public static final int GL_EVAL_VERTEX_ATTRIB8_NV = 34510;
    public static final int GL_INTENSITY16 = 32845;
    public static final int GL_INTENSITY12 = 32844;
    public static final int GL_PROGRAM_ATTRIBS_ARB = 34988;
    public static final int GL_REDUCE = 32790;
    public static final int GL_MODELVIEW13_ARB = 34605;
    public static final int GL_FRAGMENT_DEPTH = 33874;
    public static final int GL_VERTEX_ATTRIB_MAP2_DOMAIN_APPLE = 35337;
    public static final int GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX = 36936;
    public static final int GL_TEXTURE_HI_SIZE_NV = 34587;
    public static final int GL_TIME_ELAPSED_EXT = 35007;
    public static final int GL_PROGRAM_OBJECT_ARB = 35648;
    public static final int GL_STORAGE_CLIENT_APPLE = 34228;
    public static final int GL_422_REV_EXT = 32973;
    public static final int GL_POLYGON_STIPPLE = 2882;
    public static final int GL_COMPRESSED_SLUMINANCE = 35914;
    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = 35915;
    public static final int GL_HISTOGRAM_ALPHA_SIZE = 32811;
    public static final int GL_MODELVIEW22_ARB = 34614;
    public static final int GL_LUMINANCE16_SNORM = 36889;
    public static final int GL_TEXTURE_MAG_SIZE_NV = 34591;
    public static final int GL_DRAW_BUFFER12_ATI = 34865;
    public static final int GL_QUERY_DEPTH_PASS_EVENT_BIT_AMD = 1;
    public static final int GL_PROGRAM_ATTRIB_COMPONENTS_NV = 35078;
    public static final int GL_POST_COLOR_MATRIX_RED_BIAS = 32952;
    public static final int GL_COMPILE_AND_EXECUTE = 4865;
    public static final int GL_MAX_RATIONAL_EVAL_ORDER_NV = 34519;
    public static final int GL_LUMINANCE6_ALPHA2 = 32836;
    public static final int GL_OP_FLOOR_EXT = 34703;
    public static final int GL_MAX_SHADER_COMPILER_THREADS_ARB = 37296;
    public static final int GL_DEPTH_BOUNDS_TEST_EXT = 34960;
    public static final int GL_MATRIX10_ARB = 35018;
    public static final int GL_MAX_PROGRAM_ATTRIBS_ARB = 34989;
    public static final int GL_SAMPLER_1D_SHADOW_ARB = 35681;
    public static final int GL_MULTIPLY = 37524;
    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX = 34019;
    public static final int GL_DRAW_BUFFER5_ATI = 34858;
    public static final int GL_BLUE_BIAS = 3355;
    public static final int GL_LUMINANCE32UI = 36212;
    public static final int GL_AUTO_NORMAL = 3456;
    public static final int GL_INT16_VEC2_NV = 36837;
    public static final int GL_MAX_PROGRAM_TOTAL_OUTPUT_COMPONENTS_NV = 35880;
    public static final int GL_SINGLE_COLOR = 33273;
    public static final int GL_CLAMP_FRAGMENT_COLOR = 35099;
    public static final int GL_MULTISAMPLE_RASTERIZATION_ALLOWED_EXT = 37675;
    public static final int GL_ALPHA8_SNORM = 36884;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_BIAS = 32955;
    public static final int GL_OUTPUT_TEXTURE_COORD20_EXT = 34737;
    public static final int GL_DRAW_BUFFER1_ATI = 34854;
    public static final int GL_EVAL_TRIANGULAR_2D_NV = 34497;
    public static final int GL_SURFACE_REGISTERED_NV = 34557;
    public static final int GL_INT64_VEC3_ARB = 36842;
    public static final int GL_MAX_VERTEX_HINT_PGI = 107053;
    public static final int GL_PIXEL_MAP_R_TO_R = 3190;
    public static final int GL_OFFSET_TEXTURE_2D_NV = 34536;
    public static final int GL_VERTEX_ATTRIB_MAP1_APPLE = 35328;
    public static final int GL_ALPHA_INTEGER = 36247;
    public static final int GL_PROGRAM_FORMAT_ARB = 34934;
    public static final int GL_SIGNED_ALPHA_NV = 34565;
    public static final int GL_PROGRAM_NATIVE_TEMPORARIES_ARB = 34982;
    public static final int GL_OBJECT_SHADER_SOURCE_LENGTH_ARB = 35720;
    public static final int GL_RGBA_FLOAT16_ATI = 34842;
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 32864;
    public static final int GL_STENCIL_REF_COMMAND_NV = 12;
    public static final int GL_MATRIX6_ARB = 35014;
    public static final int GL_LIST_BIT = 131072;
    public static final int GL_CURRENT_WEIGHT_ARB = 34472;
    public static final int GL_ALPHA_MAX_CLAMP_INGR = 34151;
    public static final int GL_MODELVIEW4_ARB = 34596;
    public static final int GL_FIELD_LOWER_NV = 36899;
    public static final int GL_OUTPUT_TEXTURE_COORD11_EXT = 34728;
    public static final int GL_INDEX_CLEAR_VALUE = 3104;
    public static final int GL_WEIGHTED_AVERAGE_ARB = 37735;
    public static final int GL_WEIGHT_ARRAY_TYPE_ARB = 34473;
    public static final int GL_REPLICATE_BORDER = 33107;
    public static final int GL_ALPHA_FLOAT16_APPLE = 34844;
    public static final int GL_OUTPUT_TEXTURE_COORD26_EXT = 34743;
    public static final int GL_MAX_PROGRAM_MATRICES_ARB = 34351;
    public static final int GL_INTENSITY32I = 36229;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_NV = 37696;
    public static final int GL_TEXTURE_LUMINANCE_TYPE = 35860;
    public static final int GL_VIDEO_BUFFER_NV = 36896;
    public static final int GL_GEOMETRY_PROGRAM_NV = 35878;
    public static final int GL_MAX_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34995;
    public static final int GL_WEIGHT_ARRAY_SIZE_ARB = 34475;
    public static final int GL_LOAD = 257;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_Y_BITS_NV = 37704;
    public static final int GL_T2F_V3F = 10791;
    public static final int GL_CURRENT_VERTEX_WEIGHT_EXT = 34059;
    public static final int GL_VIRTUAL_PAGE_SIZE_Y_AMD = 37270;
    public static final int GL_VARIANT_ARRAY_POINTER_EXT = 34793;
    public static final int GL_TEXTURE_BORDER = 4101;
    public static final int GL_COMPRESSED_SRGB_S3TC_DXT1 = 35916;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_VARIANTS_EXT = 34763;
    public static final int GL_SIGNED_HILO16_NV = 34554;
    public static final int GL_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34978;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCALS_EXT = 34766;
    public static final int GL_ALLOW_DRAW_MEM_HINT_PGI = 107025;
    public static final int GL_DIFFERENCE = 37534;
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 3252;
    public static final int GL_DISPLAY_LIST = 33511;
    public static final int GL_MAX_DEEP_3D_TEXTURE_WIDTH_HEIGHT_NV = 37072;
    public static final int GL_UNSIGNED_INT8_NV = 36844;
    public static final int GL_ALPHA16 = 32830;
    public static final int GL_GEOMETRY_OUTPUT_TYPE_EXT = 36316;
    public static final int GL_OP_MIN_EXT = 34699;
    public static final int GL_ALPHA12 = 32829;
    public static final int GL_SIGNED_RGBA_NV = 34555;
    public static final int GL_OUTPUT_TEXTURE_COORD9_EXT = 34726;
    public static final int GL_LUMINANCE_FLOAT16_ATI = 34846;
    public static final int GL_LUMINANCE16_ALPHA16 = 32840;
    public static final int GL_MODELVIEW26_ARB = 34618;
    public static final int GL_CONVOLUTION_WIDTH = 32792;
    public static final int GL_ELEMENT_ADDRESS_COMMAND_NV = 8;
    public static final int GL_UNSIGNED_SHORT_8_8_REV_APPLE = 34235;
    public static final int GL_GEOMETRY_VERTICES_OUT_EXT = 36314;
    public static final int GL_VIEWPORT_COMMAND_NV = 16;
    public static final int GL_OBJECT_ACTIVE_UNIFORM_MAX_LENGTH_ARB = 35719;
    public static final int GL_PIXEL_MAG_FILTER_EXT = 33585;
    public static final int GL_Z_EXT = 34775;
    public static final int GL_INT16_NV = 36836;
    public static final int GL_INDEX_TEST_FUNC_EXT = 33206;
    public static final int GL_MAT_EMISSION_BIT_PGI = 8388608;
    public static final int GL_UNSIGNED_INT64_VEC2_ARB = 36853;
    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_ARB = 37699;
    public static final int GL_LUMINANCE_ALPHA_FLOAT16_ATI = 34847;
    public static final int GL_VERTEX_ATTRIB_MAP1_SIZE_APPLE = 35330;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_NV = 37695;
    public static final int GL_COLOR_CLEAR_UNCLAMPED_VALUE_ATI = 34869;
    public static final int GL_POST_CONVOLUTION_BLUE_SCALE = 32798;
    public static final int GL_EVAL_VERTEX_ATTRIB0_NV = 34502;
    public static final int GL_DARKEN = 37527;
    public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = 33887;
    public static final int GL_T4F_C4F_N3F_V4F = 10797;
    public static final int GL_CURRENT_MATRIX_ARB = 34369;
    public static final int GL_LUMINANCE_INTEGER = 36252;
    public static final int GL_LUMINANCE_ALPHA_FLOAT16_APPLE = 34847;
    public static final int GL_NEXT_VIDEO_CAPTURE_BUFFER_STATUS_NV = 36901;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING_EXT = 35055;
    public static final int GL_UNSIGNED_INT64_VEC3_ARB = 36854;
    public static final int GL_MAX_PROGRAM_ATTRIB_COMPONENTS_NV = 35080;
    public static final int GL_OUTPUT_TEXTURE_COORD24_EXT = 34741;
    public static final int GL_SIGNED_ALPHA8_NV = 34566;
    public static final int GL_CONVOLUTION_FILTER_SCALE = 32788;
    public static final int GL_UNSIGNED_INT16_VEC3_NV = 36850;
    public static final int GL_DOT_PRODUCT_PASS_THROUGH_NV = 34907;
    public static final int GL_VIBRANCE_SCALE_NV = 34579;
    public static final int GL_UNIFORM_BUFFER_BINDING_EXT = 36335;
    public static final int GL_TEXTURE_MEMORY_LAYOUT_INTEL = 33791;
    public static final int GL_UNIFORM_BUFFER_ADDRESS_NV = 37743;
    public static final int GL_VIRTUAL_PAGE_SIZE_X_AMD = 37269;
    public static final int GL_QUERY_RESULT_NO_WAIT_AMD = 37268;
    public static final int GL_INT64_VEC4_NV = 36843;
    public static final int GL_VERTEX_ID_SWIZZLE_AMD = 37285;
    public static final int GL_INTERLACE_OML = 35200;
    public static final int GL_MAGNITUDE_SCALE_NV = 34578;
    public static final int GL_OUTPUT_TEXTURE_COORD31_EXT = 34748;
    public static final int GL_LUMINANCE_ALPHA_FLOAT32_APPLE = 34841;
    public static final int GL_TRANSFORM_HINT_APPLE = 34225;
    public static final int GL_IUI_N3F_V2F_EXT = 33199;
    public static final int GL_CONSERVATIVE_RASTER_DILATE_GRANULARITY_NV = 37755;
    public static final int GL_MATRIX16_ARB = 35024;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 34940;
    public static final int GL_HINT_BIT = 32768;
    public static final int GL_SOURCE1_RGB = 34177;
    public static final int GL_OUTPUT_TEXTURE_COORD15_EXT = 34732;
    public static final int GL_T2F_C4F_N3F_V3F = 10796;
    public static final int GL_PROGRAM_NATIVE_PARAMETERS_ARB = 34986;
    public static final int GL_INT64_NV = 5134;
    public static final int GL_INTENSITY16F = 34845;
    public static final int GL_RENDER = 7168;
    public static final int GL_OP_CROSS_PRODUCT_EXT = 34711;
    public static final int GL_MATRIX4_ARB = 35012;
    public static final int GL_FLOAT_RGB32_NV = 34953;
    public static final int GL_VIDEO_BUFFER_BINDING_NV = 36897;
    public static final int GL_STORAGE_SHARED_APPLE = 34239;
    public static final int GL_VERTEX23_BIT_PGI = 4;
    public static final int GL_INTENSITY_FLOAT16_ATI = 34845;
    public static final int GL_YCBCR_422_APPLE = 34233;
    public static final int GL_FLOAT_RG32_NV = 34951;
    public static final int GL_OBJECT_SUBTYPE_ARB = 35663;
    public static final int GL_GEOMETRY_INPUT_TYPE_EXT = 36315;
    public static final int GL_SELECT = 7170;
    public static final int GL_COMPRESSED_ALPHA = 34025;
    public static final int GL_RGB_FLOAT32_ATI = 34837;
    public static final int GL_MODELVIEW29_ARB = 34621;
    public static final int GL_UNSIGNED_INT64_VEC4_NV = 36855;
    public static final int GL_TEXTURE_APPLICATION_MODE_EXT = 33615;
    public static final int GL_INVARIANT_EXT = 34754;
    public static final int GL_CULL_VERTEX_EXT = 33194;
    public static final int GL_OUTPUT_TEXTURE_COORD2_EXT = 34719;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_ARB = 37695;
    public static final int GL_FLOAT_R16_NV = 34948;
    public static final int GL_T2F_IUI_N3F_V3F_EXT = 33204;
    public static final int GL_DSDT8_MAG8_INTENSITY8_NV = 34571;
    public static final int GL_FORMAT_SUBSAMPLE_24_24_OML = 35202;
    public static final int GL_FOG_DISTANCE_MODE_NV = 34138;
    public static final int GL_FOG_COORDINATE_ARRAY = 33879;
    public static final int GL_BOOL_ARB = 35670;
    public static final int GL_LOCAL_CONSTANT_DATATYPE_EXT = 34797;
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 3254;
    public static final int GL_DSDT_MAG_NV = 34550;
    public static final int GL_MAX_PROGRAM_SUBROUTINE_PARAMETERS_NV = 36676;
    public static final int GL_RESAMPLE_AVERAGE_OML = 35208;
    public static final int GL_T2F_C4UB_V3F = 10793;
    public static final int GL_INT16_VEC3_NV = 36838;
    public static final int GL_LIGHT_MODEL_SPECULAR_VECTOR_APPLE = 34224;
    public static final int GL_VERTEX_ELEMENT_SWIZZLE_AMD = 37284;
    public static final int GL_TEXTURE_RECTANGLE_ARB = 34037;
    public static final int GL_MATRIX25_ARB = 35033;
    public static final int GL_MINMAX_FORMAT = 32815;
    public static final int GL_BACK_NORMALS_HINT_PGI = 107043;
    public static final int GL_ABGR_EXT = 32768;
    public static final int GL_MAP1_NORMAL = 3474;
    public static final long GL_ALL_CLIENT_ATTRIB_BITS = 4294967295L;
    public static final int GL_FRAGMENT_COLOR_EXT = 33612;
    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_SIZE_NV = 36257;
    public static final int GL_PROGRAM_NATIVE_ATTRIBS_ARB = 34990;
    public static final int GL_FRAGMENT_NORMAL_EXT = 33610;
    public static final int GL_EVAL_VERTEX_ATTRIB15_NV = 34517;
    public static final int GL_YCBYCR8_422_NV = 36913;
    public static final int GL_RED_MIN_CLAMP_INGR = 34144;
    public static final int GL_FRAGMENT_PROGRAM_INTERPOLATION_OFFSET_BITS_NV = 36445;
    public static final int GL_EVAL_BIT = 65536;
    public static final int GL_INT_VEC4_ARB = 35669;
    public static final int GL_OUTPUT_TEXTURE_COORD19_EXT = 34736;
    public static final int GL_COVERAGE_MODULATION_NV = 37682;
    public static final int GL_PROGRAM_PARAMETERS_ARB = 34984;
    public static final int GL_OFFSET_TEXTURE_RECTANGLE_NV = 34380;
    public static final int GL_DEPTH_STENCIL_TO_BGRA_NV = 34927;
    public static final int GL_QUERY_DEPTH_FAIL_EVENT_BIT_AMD = 2;
    public static final int GL_SIGNED_RGB_UNSIGNED_ALPHA_NV = 34572;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 34930;
    public static final int GL_CONVOLUTION_1D = 32784;
    public static final int GL_OUTPUT_COLOR0_EXT = 34715;
    public static final int GL_VARIANT_ARRAY_TYPE_EXT = 34791;
    public static final int GL_MAX_DEEP_3D_TEXTURE_DEPTH_NV = 37073;
    public static final int GL_VERTEX_WEIGHT_ARRAY_SIZE_EXT = 34061;
    public static final int GL_SELECTION_BUFFER_SIZE = 3572;
    public static final int GL_MAX_NAME_STACK_DEPTH = 3383;
    public static final int GL_VIDEO_BUFFER_PITCH_NV = 36904;
    public static final int GL_MAX_PROGRAM_NATIVE_PARAMETERS_ARB = 34987;
    public static final int GL_MIRROR_CLAMP_TO_BORDER_EXT = 35090;
    public static final int GL_COLOR_SAMPLES_NV = 36384;
    public static final int GL_ACTIVE_VERTEX_UNITS_ARB = 34469;
    public static final int GL_MATRIX12_ARB = 35020;
    public static final int GL_COLOR_TABLE_RED_SIZE = 32986;
    public static final int GL_INT8_NV = 36832;
    public static final int GL_PERFQUERY_COUNTER_NAME_LENGTH_MAX_INTEL = 38142;
    public static final int GL_PERFQUERY_COUNTER_THROUGHPUT_INTEL = 38131;
    public static final int GL_COLOR_MATRIX = 32945;
    public static final int GL_VIDEO_CAPTURE_FRAME_HEIGHT_NV = 36921;
    public static final int GL_OBJECT_PLANE = 9473;
    public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 33878;
    public static final int GL_TRANSFORM_BIT = 4096;
    public static final int GL_SCISSOR_BIT = 524288;
    public static final int GL_MAP1_TEXTURE_COORD_3 = 3477;
    public static final int GL_MAP1_TEXTURE_COORD_2 = 3476;
    public static final int GL_MAP1_TEXTURE_COORD_4 = 3478;
    public static final int GL_LUMINANCE_ALPHA_INTEGER = 36253;
    public static final int GL_MAP1_TEXTURE_COORD_1 = 3475;
    public static final int GL_MATRIX0_ARB = 35008;
    public static final int GL_TEXTURE_DT_SIZE_NV = 34590;
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 3250;
    public static final int GL_LUMINANCE_ALPHA32UI = 36213;
    public static final int GL_SAMPLE_LOCATION_NV = 36432;
    public static final int GL_ALPHA_BIAS = 3357;
    public static final int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 34941;
    public static final int GL_NATIVE_GRAPHICS_BEGIN_HINT_PGI = 107011;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INSTRUCTIONS_EXT = 34762;
    public static final int GL_SHADER_OBJECT_ARB = 35656;
    public static final int GL_PACK_CMYK_HINT_EXT = 32782;
    public static final int GL_PROGRAM_BINDING_ARB = 34423;
    public static final int GL_HSL_COLOR = 37551;
    public static final int GL_NEGATIVE_X_EXT = 34777;
    public static final int GL_TRANSFORM_FEEDBACK_NV = 36386;
    public static final int GL_MAX_PROGRAM_PARAMETERS_ARB = 34985;
    public static final int GL_Z6Y10Z6CB10Z6A10Z6Y10Z6CR10Z6A10_4224_NV = 36916;
    public static final int GL_SIGNED_HILO8_NV = 34911;
    public static final int GL_OBJECT_LINEAR = 9217;
    public static final int GL_POLYGON_OFFSET_CLAMP_EXT = 36379;
    public static final int GL_GPU_MEMORY_INFO_EVICTION_COUNT_NVX = 36938;
    public static final int GL_EVAL_VERTEX_ATTRIB3_NV = 34505;
    public static final int GL_RECLAIM_MEMORY_HINT_PGI = 107006;
    public static final int GL_CMYKA_EXT = 32781;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
    public static final int GL_MATRIX29_ARB = 35037;
    public static final int GL_DS_SCALE_NV = 34576;
    public static final int GL_SAMPLE_MASK_NV = 36433;
    public static final int GL_SURFACE_MAPPED_NV = 34560;
    public static final int GL_DRAW_BUFFER9_ATI = 34862;
    public static final int GL_CURRENT_INDEX = 2817;
    public static final int GL_VIDEO_COLOR_CONVERSION_OFFSET_NV = 36908;
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 3249;
    public static final int GL_OFFSET_HILO_TEXTURE_RECTANGLE_NV = 34901;
    public static final int GL_BOOL_VEC2_ARB = 35671;
    public static final int GL_CONVOLUTION_2D = 32785;
    public static final int GL_MAP_COLOR = 3344;
    public static final int GL_LUMINANCE_ALPHA16UI = 36219;
    public static final int GL_MAP2_GRID_DOMAIN = 3538;
    public static final int GL_LOCAL_EXT = 34756;
    public static final int GL_INTENSITY_FLOAT32_APPLE = 34839;
    public static final int GL_MATRIX_INDEX_ARRAY_ARB = 34884;
    public static final int GL_FLOAT_RGB16_NV = 34952;
    public static final int GL_PROXY_POST_CONVOLUTION_COLOR_TABLE = 32980;
    public static final int GL_C4UB_V2F = 10786;
    public static final int GL_OBJECT_ATTACHED_OBJECTS_ARB = 35717;
    public static final int GL_INDEX_MATERIAL_PARAMETER_EXT = 33209;
    public static final int GL_MAP_ATTRIB_V_ORDER_NV = 34500;
    public static final int GL_MODELVIEW31_ARB = 34623;
    public static final int GL_T2F_IUI_V2F_EXT = 33201;
    public static final int GL_MODELVIEW1_EXT = 34058;
    public static final int GL_LUMINANCE32I = 36230;
    public static final int GL_HISTOGRAM_FORMAT = 32807;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
    public static final int GL_FEEDBACK = 7169;
    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_ARB = 34886;
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 3253;
    public static final int GL_FLOAT_RG16_NV = 34950;
    public static final int GL_LUMINANCE4 = 32831;
    public static final int GL_AUX_BUFFERS = 3072;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_ARB = 37694;
    public static final int GL_CLIP_VOLUME_CLIPPING_HINT_EXT = 33008;
    public static final int GL_C4UB_V3F = 10787;
    public static final int GL_RGBA_S3TC = 33698;
    public static final int GL_DRAW_ELEMENTS_STRIP_COMMAND_NV = 4;
    public static final int GL_VERTEX_BLEND_ARB = 34471;
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 34972;
    public static final int GL_MAX_VIEWS_OVR = 38449;
    public static final int GL_PASS_THROUGH_TOKEN = 1792;
    public static final int GL_MODELVIEW16_ARB = 34608;
    public static final int GL_OP_POWER_EXT = 34707;
    public static final int GL_RGBA_SNORM = 36755;
    public static final int GL_TEXTURE_UNSIGNED_REMAP_MODE_NV = 34959;
    public static final int GL_LINE_STIPPLE = 2852;
    public static final int GL_CONSTANT_BORDER = 33105;
    public static final int GL_UNSIGNED_INT16_NV = 36848;
    public static final int GL_MINMAX = 32814;
    public static final int GL_MAP2_VERTEX_4 = 3512;
    public static final int GL_MAP2_VERTEX_3 = 3511;
    public static final int GL_COLOR4_BIT_PGI = 131072;
    public static final int GL_INTENSITY32UI = 36211;
    public static final int GL_PROXY_POST_COLOR_MATRIX_COLOR_TABLE = 32981;
    public static final int GL_COLORDODGE = 37529;
    public static final int GL_W_EXT = 34776;
    public static final int GL_COMPRESSED_LUMINANCE_LATC1_EXT = 35952;
    public static final int GL_POINT_TOKEN = 1793;
    public static final int GL_OFFSET_TEXTURE_BIAS_NV = 34531;
    public static final int GL_EYE_RADIAL_NV = 34139;
    public static final int GL_POST_CONVOLUTION_GREEN_BIAS = 32801;
    public static final int GL_OUTPUT_TEXTURE_COORD29_EXT = 34746;
    public static final int GL_DEPENDENT_RGB_TEXTURE_3D_NV = 34905;
    public static final int GL_PERFORMANCE_MONITOR_AMD = 37202;
    public static final int GL_TEXTURE_DS_SIZE_NV = 34589;
    public static final int GL_MODELVIEW3_ARB = 34595;
    public static final int GL_DS_BIAS_NV = 34582;
    public static final int GL_ALWAYS_SOFT_HINT_PGI = 107021;
    public static final int GL_TEXTURE_RANGE_POINTER_APPLE = 34232;
    public static final int GL_MAX_PIXEL_MAP_TABLE = 3380;
    public static final int GL_RGB_SNORM = 36754;
    public static final int GL_STORAGE_CACHED_APPLE = 34238;
    public static final int GL_AVERAGE_EXT = 33589;
    public static final int GL_UNIFORM_BUFFER_UNIFIED_NV = 37742;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE = 34371;
    public static final int GL_DRAW_BUFFER6_ATI = 34859;
    public static final int GL_PERFQUERY_QUERY_NAME_LENGTH_MAX_INTEL = 38141;
    public static final int GL_SLUMINANCE = 35910;
    public static final int GL_MAX_CONVOLUTION_HEIGHT = 32795;
    public static final int GL_MAX_BINDABLE_UNIFORM_SIZE_EXT = 36333;
    public static final int GL_HISTOGRAM_WIDTH = 32806;
    public static final int GL_ACCUM_ALPHA_BITS = 3419;
    public static final int GL_PERFQUERY_COUNTER_RAW_INTEL = 38132;
    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS = 37274;
    public static final int GL_LAYOUT_LINEAR_CPU_CACHED_INTEL = 2;
    public static final int GL_MODELVIEW1_STACK_DEPTH_EXT = 34050;
    public static final int GL_CURRENT_RASTER_POSITION = 2823;
    public static final int GL_INT8_VEC2_NV = 36833;
    public static final int GL_LINE_TOKEN = 1794;
    public static final int GL_CURRENT_PALETTE_MATRIX_ARB = 34883;
    public static final int GL_HSL_HUE = 37549;
    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_ARB = 34889;
    public static final int GL_FLOAT_RG_NV = 34945;
    public static final int GL_T2F_C3F_V3F = 10794;
    public static final int GL_TEXTURE_FILTER_CONTROL = 34048;
    public static final int GL_VERTEX_ARRAY_OBJECT_AMD = 37204;
    public static final int GL_ALPHA4 = 32827;
    public static final int GL_FRAGMENT_PROGRAM_ARB = 34820;
    public static final int GL_EDGE_FLAG_ARRAY_POINTER = 32915;
    public static final int GL_DT_BIAS_NV = 34583;
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 3256;
    public static final int GL_MODELVIEW21_ARB = 34613;
    public static final int GL_PIXEL_CUBIC_WEIGHT_EXT = 33587;
    public static final int GL_DRAW_BUFFER13_ATI = 34866;
    public static final int GL_TEXTURE_BUFFER_FORMAT = 35886;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER_EXT = 36052;
    public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 35059;
    public static final int GL_PIXEL_TRANSFORM_2D_STACK_DEPTH_EXT = 33590;
    public static final int GL_MAP1_INDEX = 3473;
    public static final int GL_SUCCESS_NV = 36911;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34764;
    public static final int GL_COPY_PIXEL_TOKEN = 1798;
    public static final int GL_OUTPUT_TEXTURE_COORD12_EXT = 34729;
    public static final int GL_VERTEX_WEIGHT_ARRAY_EXT = 34060;
    public static final int GL_COMPRESSED_SIGNED_LUMINANCE_ALPHA_LATC2_EXT = 35955;
    public static final int GL_FRONT_FACE_COMMAND_NV = 18;
    public static final int GL_ALPHA_FLOAT32_ATI = 34838;
    public static final int GL_RGBA4_DXT5_S3TC = 33701;
    public static final int GL_PROGRAM_STRING_ARB = 34344;
    public static final int GL_CURRENT_BIT = 1;
    public static final int GL_COEFF = 2560;
    public static final int GL_422_AVERAGE_EXT = 32974;
    public static final int GL_NUM_VIDEO_CAPTURE_STREAMS_NV = 36900;
    public static final int GL_EVAL_VERTEX_ATTRIB6_NV = 34508;
    public static final int GL_VIDEO_BUFFER_INTERNAL_FORMAT_NV = 36909;
    public static final int GL_SOURCE0_ALPHA = 34184;
    public static final int GL_ALLOW_DRAW_WIN_HINT_PGI = 107023;
    public static final int GL_OP_SET_GE_EXT = 34700;
    public static final int GL_OP_MAX_EXT = 34698;
    public static final int GL_VIEWPORT_BIT = 2048;
    public static final int GL_SOURCE2_ALPHA = 34186;
    public static final int GL_DOMAIN = 2562;
    public static final int GL_SCREEN_COORDINATES_REND = 33936;
    public static final int GL_EYE_PLANE_ABSOLUTE_NV = 34140;
    public static final int GL_OUTPUT_TEXTURE_COORD6_EXT = 34723;
    public static final int GL_FOG_COORD_ARRAY_POINTER = 33878;
    public static final int GL_PIXEL_COUNT_AVAILABLE_NV = 34919;
    public static final int GL_DRAW_BUFFER2_ATI = 34855;
    public static final int GL_UNPACK_CMYK_HINT_EXT = 32783;
    public static final int GL_C3F_V3F = 10788;
    public static final int GL_HILO_NV = 34548;
    public static final int GL_STENCIL_TEST_TWO_SIDE_EXT = 35088;
    public static final int GL_FEEDBACK_BUFFER_TYPE = 3570;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_ARB = 37697;
    public static final int GL_COLOR_TABLE_BIAS = 32983;
    public static final int GL_VERTEX_CONSISTENT_HINT_PGI = 107051;
    public static final int GL_MODELVIEW12_ARB = 34604;
    public static final int GL_OUTPUT_TEXTURE_COORD21_EXT = 34738;
    public static final int GL_RETURN = 258;
    public static final int GL_MAX_SPOT_EXPONENT_NV = 34053;
    public static final int GL_TRANSPOSE_COLOR_MATRIX = 34022;
    public static final int GL_SCALAR_EXT = 34750;
    public static final int GL_VERTEX_ATTRIB_MAP1_COEFF_APPLE = 35331;
    public static final int GL_MODELVIEW25_ARB = 34617;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION_EXT = 36428;
    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET_NV = 36444;
    public static final int GL_422_EXT = 32972;
    public static final int GL_COMPRESSED_LUMINANCE = 34026;
    public static final int GL_CURRENT_FOG_COORDINATE = 33875;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_NV = 34936;
    public static final int GL_OUTPUT_TEXTURE_COORD25_EXT = 34742;
    public static final int GL_LOCAL_CONSTANT_VALUE_EXT = 34796;
    public static final int GL_MAP1_GRID_SEGMENTS = 3537;
    public static final int GL_PROGRAM_MATRIX_STACK_DEPTH_EXT = 36399;
    public static final int GL_Q = 8195;
    public static final int GL_S = 8192;
    public static final int GL_R = 8194;
    public static final int GL_T = 8193;
    public static final int GL_PERCENTAGE_AMD = 35779;
    public static final int GL_EDGE_FLAG_ARRAY = 32889;
    public static final int GL_FOG_BIT = 128;
    public static final int GL_NOP_COMMAND_NV = 1;
    public static final int GL_IUI_N3F_V3F_EXT = 33200;
    public static final int GL_DOT_PRODUCT_TEXTURE_3D_NV = 34543;
    public static final int GL_INTERLACE_READ_INGR = 34152;
    public static final int GL_NATIVE_GRAPHICS_END_HINT_PGI = 107012;
    public static final int GL_INDEX_MODE = 3120;
    public static final int GL_ALLOW_DRAW_FRG_HINT_PGI = 107024;
    public static final int GL_MAT_AMBIENT_BIT_PGI = 1048576;
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 3257;
    public static final int GL_BUFFER_FLUSHING_UNMAP = 35347;
    public static final int GL_STRICT_DEPTHFUNC_HINT_PGI = 107030;
    public static final int GL_GREEN_MIN_CLAMP_INGR = 34145;
    public static final int GL_RENDERBUFFER_COLOR_SAMPLES_NV = 36368;
    public static final int GL_LUMINANCE_FLOAT32_APPLE = 34840;
    public static final int GL_TEXTURE_STORAGE_SPARSE_BIT_AMD = 1;
    public static final int GL_INT64_VEC2_ARB = 36841;
    public static final int GL_SHADOW_ATTENUATION_EXT = 33614;
    public static final int GL_PROGRAM_UNDER_NATIVE_LIMITS_ARB = 34998;
    public static final int GL_EVAL_VERTEX_ATTRIB10_NV = 34512;
    public static final int GL_MODELVIEW7_ARB = 34599;
    public static final long GL_QUERY_ALL_EVENT_BITS_AMD = 4294967295L;
    public static final int GL_INDEX_ARRAY_TYPE = 32901;
    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_NV = 37698;
    public static final int GL_SIGNED_RGB8_NV = 34559;
    public static final int GL_MATRIX7_ARB = 35015;
    public static final int GL_TEXTURE_LO_SIZE_NV = 34588;
    public static final int GL_SIGNED_LUMINANCE8_ALPHA8_NV = 34564;
    public static final int GL_RED_SCALE = 3348;
    public static final int GL_VIBRANCE_BIAS_NV = 34585;
    public static final int GL_POST_COLOR_MATRIX_GREEN_SCALE = 32949;
    public static final int GL_RENDERBUFFER_FREE_MEMORY_ATI = 34813;
    public static final int GL_OUTPUT_TEXTURE_COORD3_EXT = 34720;
    public static final int GL_RASTER_MULTISAMPLE_EXT = 37671;
    public static final int GL_TEXTURE_CONSTANT_DATA_SUNX = 33238;
    public static final int GL_UNPACK_CLIENT_STORAGE_APPLE = 34226;
    public static final int GL_COLOR_FLOAT_APPLE = 35343;
    public static final int GL_COMPARE_R_TO_TEXTURE = 34894;
    public static final int GL_VERTEX_SHADER_LOCALS_EXT = 34771;
    public static final int GL_UNDEFINED_APPLE = 35356;
    public static final int GL_PIXEL_COUNTER_BITS_NV = 34916;
    public static final int GL_HISTOGRAM = 32804;
    public static final int GL_MAX_VERTEX_SHADER_LOCALS_EXT = 34761;
    public static final int GL_ACCUM_BUFFER_BIT = 512;
    public static final int GL_RGBA_FLOAT16_APPLE = 34842;
    public static final int GL_OUTPUT_TEXTURE_COORD16_EXT = 34733;
    public static final int GL_MATRIX15_ARB = 35023;
    public static final int GL_SIGNED_LUMINANCE8_NV = 34562;
    public static final int GL_HI_SCALE_NV = 34574;
    public static final int GL_SAMPLE_MASK_VALUE_NV = 36434;
    public static final int GL_INTENSITY = 32841;
    public static final int GL_INDEX_TEST_EXT = 33205;
    public static final int GL_WRITE_DISCARD_NV = 35006;
    public static final int GL_FLOAT_CLEAR_COLOR_VALUE_NV = 34957;
    public static final int GL_MATRIX1_ARB = 35009;
    public static final int GL_POST_COLOR_MATRIX_GREEN_BIAS = 32953;
    public static final int GL_TEXTURE_SWIZZLE_R_EXT = 36418;
    public static final int GL_SAMPLER_1D_ARB = 35677;
    public static final int GL_TRANSPOSE_TEXTURE_MATRIX = 34021;
    public static final int GL_OFFSET_HILO_PROJECTIVE_TEXTURE_RECTANGLE_NV = 34903;
    public static final int GL_INDEX_ARRAY = 32887;
    public static final int GL_POST_CONVOLUTION_ALPHA_SCALE = 32799;
    public static final int GL_PROGRAM_RESULT_COMPONENTS_NV = 35079;
    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_NV = 36446;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5 = 35919;
    public static final int GL_NORMAL_BIT_PGI = 134217728;
    public static final int GL_UNSIGNED_INT64_VEC4_ARB = 36855;
    public static final int GL_Z6Y10Z6CB10Z6Y10Z6CR10_422_NV = 36915;
    public static final int GL_GPU_MEMORY_INFO_DEDICATED_VIDMEM_NVX = 36935;
    public static final int GL_VERTEX_ARRAY_RANGE_APPLE = 34077;
    public static final int GL_HSL_SATURATION = 37550;
    public static final int GL_FLOAT_RGB_NV = 34946;
    public static final int GL_LUMINANCE_ALPHA16I = 36237;
    public static final int GL_QUERY_STENCIL_FAIL_EVENT_BIT_AMD = 4;
    public static final int GL_UNSIGNED_INT64_NV = 5135;
    public static final int GL_ORDER = 2561;
    public static final int GL_SAMPLER_2D_RECT_ARB = 35683;
    public static final int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 34939;
    public static final int GL_MATRIX24_ARB = 35032;
    public static final int GL_CONST_EYE_NV = 34533;
    public static final int GL_INTENSITY8 = 32843;
    public static final int GL_MAX_TEXTURE_COORDS_ARB = 34929;
    public static final int GL_INTENSITY4 = 32842;
    public static final int GL_SHARED_TEXTURE_PALETTE_EXT = 33275;
    public static final int GL_LINE_STIPPLE_PATTERN = 2853;
    public static final int GL_FULL_STIPPLE_HINT_PGI = 107033;
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 33883;
    public static final int GL_COLOR3_BIT_PGI = 65536;
    public static final int GL_CMYK_EXT = 32780;
    public static final int GL_COVERAGE_MODULATION_TABLE_NV = 37681;
    public static final int GL_PROXY_COLOR_TABLE = 32979;
    public static final int GL_PROGRAM_ALU_INSTRUCTIONS_ARB = 34821;
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING = 34969;
    public static final int GL_EVAL_VERTEX_ATTRIB9_NV = 34511;
    public static final int GL_BITMAP = 6656;
    public static final int GL_ALWAYS_FAST_HINT_PGI = 107020;
    public static final int GL_EMBOSS_LIGHT_NV = 34141;
    public static final int GL_UNSIGNED_SHORT_8_8_APPLE = 34234;
    public static final int GL_4D_COLOR_TEXTURE = 1540;
    public static final int GL_COLORBURN = 37530;
    public static final int GL_MATRIX11_ARB = 35019;
    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_BINDINGS_NV = 36256;
    public static final int GL_TEXTURE_FLOAT_COMPONENTS_NV = 34956;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 33885;
    public static final int GL_LUMINANCE12_ALPHA12 = 32839;
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER_NV = 35069;
    public static final int GL_MAX_SUBPIXEL_PRECISION_BIAS_BITS_NV = 37705;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE_NV = 36388;
    public static final int GL_QUERY_OBJECT_AMD = 37203;
    public static final int GL_ALPHA_FLOAT16_ATI = 34844;
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 3251;
    public static final int GL_EYE_LINEAR = 9216;
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT1 = 35917;
    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_ARB = 34368;
    public static final int GL_SIGNED_RGB_NV = 34558;
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 2897;
    public static final int GL_UNSIGNED_INT_8_8_S8_S8_REV_NV = 34523;
    public static final int GL_FLOAT_VEC2_ARB = 35664;
    public static final int GL_HALF_APPLE = 5131;
    public static final int GL_DSDT8_MAG8_NV = 34570;
    public static final int GL_VERTEX_ATTRIB_MAP2_ORDER_APPLE = 35336;
    public static final int GL_TEXTURE_SWIZZLE_RGBA_EXT = 36422;
    public static final int GL_RESAMPLE_REPLICATE_OML = 35206;
    public static final int GL_EYE_PLANE = 9474;
    public static final int GL_PRIMITIVE_RESTART_NV = 34136;
    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_APPLE = 34078;
    public static final int GL_FRAGMENT_MATERIAL_EXT = 33609;
    public static final int GL_PIXEL_MIN_FILTER_EXT = 33586;
    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INVARIANTS_EXT = 34765;
    public static final int GL_ARRAY_ELEMENT_LOCK_COUNT_EXT = 33193;
    public static final int GL_VERTEX_SHADER_OPTIMIZED_EXT = 34772;
    public static final int GL_MODELVIEW1_MATRIX_EXT = 34054;
    public static final int GL_PIXEL_COUNT_NV = 34918;
    public static final int GL_INT_VEC3_ARB = 35668;
    public static final int GL_T2F_IUI_N3F_V2F_EXT = 33203;
    public static final int GL_CURRENT_VERTEX_EXT = 34786;
    public static final int GL_FOG_COORD_SRC = 33872;
    public static final int GL_MULT = 259;
    public static final int GL_VARIANT_DATATYPE_EXT = 34789;
    public static final int GL_PARTIAL_SUCCESS_NV = 36910;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_SCALE = 32951;
    public static final int GL_TRANSPOSE_PROJECTION_MATRIX = 34020;
    public static final int GL_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34770;
    public static final int GL_ALPHA_REF_COMMAND_NV = 15;
    public static final int GL_MAX_PROGRAM_TEMPORARIES_ARB = 34981;
    public static final int GL_ALPHA16UI = 36216;
    public static final int GL_FLOAT_RGBA_MODE_NV = 34958;
    public static final int GL_COLOR_MATERIAL_PARAMETER = 2902;
    public static final int GL_VERTEX_WEIGHT_ARRAY_POINTER_EXT = 34064;
    public static final int GL_3_BYTES = 5128;
    public static final int GL_INDEX_OFFSET = 3347;
    public static final int GL_DSDT8_NV = 34569;
    public static final int GL_POST_CONVOLUTION_GREEN_SCALE = 32797;
    public static final int GL_SAMPLER_OBJECT_AMD = 37205;
    public static final int GL_MATRIX28_ARB = 35036;
    public static final int GL_OFFSET_HILO_PROJECTIVE_TEXTURE_2D_NV = 34902;
    public static final int GL_LUMINANCE_ALPHA_SNORM = 36882;
    public static final int GL_OUTPUT_TEXTURE_COORD30_EXT = 34747;
    public static final int GL_ACCUM_GREEN_BITS = 3417;
    public static final int GL_INT_SAMPLER_RENDERBUFFER_NV = 36439;
    public static final int GL_TEXTURE_LIGHT_EXT = 33616;
    public static final int GL_MIXED_DEPTH_SAMPLES_SUPPORTED_NV = 37679;
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE = 32991;
    public static final int GL_ALPHA16I = 36234;
    public static final int GL_RELEASED_APPLE = 35353;
    public static final int GL_ATTRIBUTE_ADDRESS_COMMAND_NV = 9;
    public static final int GL_FOG_COORD_ARRAY_STRIDE = 33877;
    public static final int GL_OFFSET_TEXTURE_2D_BIAS_NV = 34531;
    public static final int GL_UNSIGNED_INT_S8_S8_8_8_NV = 34522;
    public static final int GL_TEXTURE_INTENSITY_TYPE = 35861;
    public static final int GL_INTENSITY16_SNORM = 36891;
    public static final int GL_SM_COUNT_NV = 37691;
    public static final int GL_EDGE_FLAG = 2883;
    public static final int GL_FRAGMENT_COVERAGE_TO_COLOR_NV = 37597;
    public static final int GL_MAX_PROGRAM_NATIVE_TEMPORARIES_ARB = 34983;
    public static final int GL_VERTEX4_BIT_PGI = 8;
    public static final int GL_VERTEX_SHADER_VARIANTS_EXT = 34768;
    public static final int GL_MAX_ATTRIB_STACK_DEPTH = 3381;
    public static final int GL_RGB_FLOAT32_APPLE = 34837;
    public static final int GL_NEGATIVE_Y_EXT = 34778;
    public static final int GL_COMPARE_REF_DEPTH_TO_TEXTURE = 34894;
    public static final int GL_MAT_COLOR_INDEXES_BIT_PGI = 16777216;
    public static final int GL_OUTPUT_COLOR1_EXT = 34716;
    public static final int GL_LAYOUT_LINEAR_INTEL = 1;
    public static final int GL_MAX_PROGRAM_TEX_INSTRUCTIONS_ARB = 34828;
    public static final int GL_HARDLIGHT = 37531;
    public static final int GL_COLOR_MATRIX_STACK_DEPTH = 32946;
    public static final int GL_CLAMP_VERTEX_COLOR = 35098;
    public static final int GL_MAX_MULTISAMPLE_COVERAGE_MODES_NV = 36369;
    public static final int GL_DEPTH_CLAMP_NV = 34383;
    public static final int GL_DOT_PRODUCT_AFFINE_DEPTH_REPLACE_NV = 34909;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
    public static final int GL_MAX_PROGRAM_ADDRESS_REGISTERS_ARB = 34993;
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 3255;
    public static final int GL_MODELVIEW19_ARB = 34611;
    public static final int GL_CURRENT_SECONDARY_COLOR = 33881;
    public static final int GL_COMPILE = 4864;
    public static final int GL_LINE_BIT = 4;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = 34974;
    public static final int GL_LO_SCALE_NV = 34575;
    public static final int GL_PERFQUERY_DONOT_FLUSH_INTEL = 33785;
    public static final int GL_DRAW_BUFFER14_ATI = 34867;
    public static final int GL_TEXTURE_RENDERBUFFER_NV = 36437;
    public static final int GL_MODELVIEW15_ARB = 34607;
    public static final int GL_GREEN_BIAS = 3353;
    public static final int GL_PASS_THROUGH_NV = 34534;
    public static final int GL_VIDEO_COLOR_CONVERSION_MIN_NV = 36907;
    public static final int GL_OP_NEGATE_EXT = 34691;
    public static final int GL_ZERO_EXT = 34781;
    public static final int GL_VERTEX_SHADER_EXT = 34688;
    public static final int GL_PACK_ROW_BYTES_APPLE = 35349;
    public static final int GL_POST_CONVOLUTION_RED_SCALE = 32796;
    public static final int GL_MAP2_GRID_SEGMENTS = 3539;
    public static final int GL_MAX_PROGRAM_RESULT_COMPONENTS_NV = 35081;
    public static final int GL_YCBAYCR8A_4224_NV = 36914;
    public static final int GL_RGBA_SIGNED_COMPONENTS = 35900;
    public static final int GL_PERFQUERY_COUNTER_TIMESTAMP_INTEL = 38133;
    public static final int GL_PN_TRIANGLES_POINT_MODE_LINEAR_ATI = 34805;
    public static final int GL_MODELVIEW24_ARB = 34616;
    public static final int GL_INT64_ARB = 5134;
    public static final int GL_QUERY_DEPTH_BOUNDS_FAIL_EVENT_BIT_AMD = 8;
    public static final int GL_MODELVIEW30_ARB = 34622;
    public static final int GL_EVAL_VERTEX_ATTRIB1_NV = 34503;
    public static final int GL_MAX_VERTEX_SHADER_INVARIANTS_EXT = 34759;
    public static final int GL_PROGRAM_FORMAT_ASCII_ARB = 34933;
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 2;
    public static final int GL_2_BYTES = 5127;
    public static final int GL_MODELVIEW0_EXT = 5888;
    public static final int GL_COMPUTE_PROGRAM_NV = 37115;
    public static final int GL_MAT_AMBIENT_AND_DIFFUSE_BIT_PGI = 2097152;
    public static final int GL_PREVIOUS_TEXTURE_INPUT_NV = 34532;
    public static final int GL_UNSIGNED_INT16_VEC2_NV = 36849;
    public static final int GL_OBJECT_DELETE_STATUS_ARB = 35712;
    public static final int GL_STRICT_SCISSOR_HINT_PGI = 107032;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_CONVOLUTION_HEIGHT = 32793;
    public static final int GL_FULL_RANGE_EXT = 34785;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING_NV = 36389;
    public static final int GL_DRAW_ELEMENTS_INSTANCED_COMMAND_NV = 6;
    public static final int GL_CLIENT_PIXEL_STORE_BIT = 1;
    public static final int GL_MATERIAL_SIDE_HINT_PGI = 107052;
    public static final int GL_LUMINANCE16I = 36236;
    public static final int GL_MINMAX_SINK = 32816;
    public static final int GL_LIGHTEN = 37528;
    public static final int GL_VBO_FREE_MEMORY_ATI = 34811;
    public static final int GL_FEEDBACK_BUFFER_POINTER = 3568;
    public static final int GL_COLOR_TABLE_BLUE_SIZE = 32988;
    public static final int GL_DRAW_BUFFER3_ATI = 34856;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_LINEAR_ATI = 34807;
    public static final int GL_RENDER_MODE = 3136;
    public static final int GL_CLIP_FAR_HINT_PGI = 107041;
    public static final int GL_SAMPLER_CUBE_ARB = 35680;
    public static final int GL_PERTURB_EXT = 34222;
    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_NV = 37693;
    public static final int GL_CURRENT_RASTER_DISTANCE = 2825;
    public static final int GL_DRAW_PIXEL_TOKEN = 1797;
    public static final int GL_DT_SCALE_NV = 34577;
    public static final int GL_DOT_PRODUCT_DIFFUSE_CUBE_MAP_NV = 34545;
    public static final int GL_CLAMP = 10496;
    public static final int GL_MODELVIEW2_ARB = 34594;
    public static final int GL_STENCIL_SAMPLES_NV = 37678;
    public static final int GL_PERFMON_RESULT_AVAILABLE_AMD = 35780;
    public static final int GL_TEXTURE_FREE_MEMORY_ATI = 34812;
    public static final int GL_OUTPUT_TEXTURE_COORD28_EXT = 34745;
    public static final int GL_GREEN_MAX_CLAMP_INGR = 34149;
    public static final int GL_MATRIX19_ARB = 35027;
    public static final int GL_VARIANT_VALUE_EXT = 34788;
    public static final int GL_FOG_COORD_ARRAY = 33879;
    public static final int GL_FIELD_UPPER_NV = 36898;
    public static final int GL_COUNTER_RANGE_AMD = 35777;
    public static final int GL_INTENSITY16UI = 36217;
    public static final int GL_MAX_CONVOLUTION_WIDTH = 32794;
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_PERFQUERY_COUNTER_DATA_UINT32_INTEL = 38136;
    public static final int GL_INDEX_ARRAY_POINTER = 32913;
    public static final int GL_ENABLE_BIT = 8192;
    public static final int GL_UNSIGNED_INT64_VEC2_NV = 36853;
    public static final int GL_DEPTH_TEXTURE_MODE = 34891;
    public static final int GL_STORAGE_PRIVATE_APPLE = 34237;
    public static final int GL_MAX_VERTEX_BINDABLE_UNIFORMS_EXT = 36322;
    public static final int GL_TEXTURE_RESIDENT = 32871;
    public static final int GL_PERFQUERY_COUNTER_DATA_FLOAT_INTEL = 38138;
    public static final int GL_MAX_COLOR_MATRIX_STACK_DEPTH = 32947;
    public static final int GL_MAX_PROGRAM_NATIVE_TEX_INDIRECTIONS_ARB = 34832;
    public static final int GL_MODELVIEW11_ARB = 34603;
    public static final int GL_FOG_INDEX = 2913;
    public static final int GL_HISTOGRAM_LUMINANCE_SIZE = 32812;
    public static final int GL_DRAW_BUFFER10_ATI = 34863;
    public static final int GL_MAX_PROGRAM_TEX_INDIRECTIONS_ARB = 34829;
    public static final int GL_FORMAT_SUBSAMPLE_244_244_OML = 35203;
    public static final int GL_DEPTH_BIAS = 3359;
    public static final int GL_VIDEO_CAPTURE_FRAME_WIDTH_NV = 36920;
    public static final int GL_UNSIGNED_INT8_VEC2_NV = 36845;
    public static final int GL_SIGNED_INTENSITY8_NV = 34568;
    public static final int GL_OUTPUT_TEXTURE_COORD7_EXT = 34724;
    public static final int GL_HISTOGRAM_SINK = 32813;
    public static final int GL_DRAW_ARRAYS_STRIP_COMMAND_NV = 5;
    public static final int GL_STENCIL_TAG_BITS_EXT = 35058;
    public static final int GL_POST_COLOR_MATRIX_BLUE_BIAS = 32954;
    public static final int GL_EVAL_VERTEX_ATTRIB12_NV = 34514;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_NV = 36447;
    public static final int GL_OP_ADD_EXT = 34695;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET_NV = 35076;
    public static final int GL_FLOAT_R_NV = 34944;
    public static final int GL_POST_COLOR_MATRIX_BLUE_SCALE = 32950;
    public static final int GL_LUMINANCE8I = 36242;
    public static final int GL_BUFFER_OBJECT_APPLE = 34227;
    public static final int GL_MIXED_STENCIL_SAMPLES_SUPPORTED_NV = 37680;
    public static final int GL_MODELVIEW28_ARB = 34620;
    public static final int GL_DOT_PRODUCT_TEXTURE_CUBE_MAP_NV = 34544;
    public static final int GL_LIST_BASE = 2866;
    public static final int GL_NAME_STACK_DEPTH = 3440;
    public static final int GL_OFFSET_TEXTURE_SCALE_NV = 34530;
    public static final int GL_PIXEL_MAP_G_TO_G = 3191;
    public static final int GL_INDEX = 33314;
    public static final int GL_LUMINANCE_FLOAT16_APPLE = 34846;
    public static final int GL_WEIGHT_SUM_UNITY_ARB = 34470;
    public static final int GL_RED_MAX_CLAMP_INGR = 34148;
    public static final int GL_T2F_IUI_V3F_EXT = 33202;
    public static final int GL_FAILURE_NV = 36912;
    public static final int GL_VERTEX_ARRAY_STORAGE_HINT_APPLE = 34079;
    public static final long GL_CLIENT_ALL_ATTRIB_BITS = 4294967295L;
    public static final int GL_COLOR_TABLE_FORMAT = 32984;
    public static final int GL_MAP_TESSELLATION_NV = 34498;
    public static final int GL_OP_LOG_BASE_2_EXT = 34706;
    public static final int GL_SIGNED_LUMINANCE_ALPHA_NV = 34563;
    public static final int GL_TEXTURE_RENDERBUFFER_DATA_STORE_BINDING_NV = 36436;
    public static final int GL_TEXTURE_BORDER_VALUES_NV = 34586;
    public static final int GL_MATRIX21_ARB = 35029;
    public static final int GL_X_EXT = 34773;
    public static final int GL_CURRENT_RASTER_COLOR = 2820;
    public static final int GL_Z4Y12Z4CB12Z4A12Z4Y12Z4CR12Z4A12_4224_NV = 36918;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_AMD = 37187;
    public static final int GL_MODELVIEW0_MATRIX_EXT = 2982;
    public static final int GL_ACCUM = 256;
    public static final int GL_MATRIX8_ARB = 35016;
    public static final int GL_LOCAL_CONSTANT_EXT = 34755;
    public static final int GL_TEXTURE_GEN_Q = 3171;
    public static final int GL_TEXTURE_GEN_R = 3170;
    public static final int GL_LUMINANCE12_ALPHA4 = 32838;
    public static final int GL_TEXTURE_GEN_S = 3168;
    public static final int GL_TEXTURE_GEN_T = 3169;
    public static final int GL_MAX_MAP_TESSELLATION_NV = 34518;
    public static final int GL_OUTPUT_TEXTURE_COORD22_EXT = 34739;
    public static final int GL_PERFQUERY_COUNTER_DATA_UINT64_INTEL = 38137;
    public static final int GL_COMPUTE_PROGRAM_PARAMETER_BUFFER_NV = 37116;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_NV = 37697;
    public static final int GL_DOT_PRODUCT_TEXTURE_2D_NV = 34542;
    public static final int GL_FLOAT_MAT2_ARB = 35674;
    public static final int GL_EVAL_VERTEX_ATTRIB4_NV = 34506;
    public static final int GL_PROGRAM_TEX_INSTRUCTIONS_ARB = 34822;
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE = 32990;
    public static final int GL_TEXTURE_SWIZZLE_G_EXT = 36419;
    public static final int GL_MODELVIEW6_ARB = 34598;
    public static final int GL_MAX_SAMPLE_MASK_WORDS_NV = 36441;
    public static final int GL_FLOAT16_VEC4_NV = 36859;
    public static final int GL_INDEX_MATERIAL_FACE_EXT = 33210;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_ATI = 34803;
    public static final int GL_OCCLUSION_QUERY_EVENT_MASK_AMD = 34639;
    public static final int GL_TEXTURE_REDUCTION_MODE_ARB = 37734;
    public static final int GL_ALPHA_SNORM = 36880;
    public static final int GL_SHADER_OPERATION_NV = 34527;
    public static final int GL_OVERLAY = 37526;
    public static final int GL_RED_BIAS = 3349;
    public static final int GL_FLOAT_R32_NV = 34949;
    public static final int GL_COLOR_INDEX = 6400;
    public static final int GL_OUTPUT_VERTEX_EXT = 34714;
    public static final int GL_3D = 1537;
    public static final int GL_OBJECT_ACTIVE_UNIFORMS_ARB = 35718;
    public static final int GL_MAX_PROGRAM_NATIVE_TEX_INSTRUCTIONS_ARB = 34831;
    public static final int GL_MAX_VERTEX_SHADER_VARIANTS_EXT = 34758;
    public static final int GL_GREEN_SCALE = 3352;
    public static final int GL_INTENSITY32F = 34839;
    public static final int GL_MATRIX23_ARB = 35031;
    public static final int GL_LUMINANCE8_SNORM = 36885;
    public static final int GL_MATRIX2_ARB = 35010;
    public static final int GL_POST_CONVOLUTION_COLOR_TABLE = 32977;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING_EXT = 35053;
    public static final int GL_HILO8_NV = 34910;
    public static final int GL_VERTEX_SHADER_INVARIANTS_EXT = 34769;
    public static final int GL_MAX_PROGRAM_PATCH_ATTRIBS_NV = 34520;
    public static final int GL_PIXEL_PACK_BUFFER_EXT = 35051;
    public static final int GL_MATRIX30_ARB = 35038;
    public static final int GL_SAMPLER_2D_ARB = 35678;
    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_ARB = 37698;
    public static final int GL_UNIFORM_BUFFER_EXT = 36334;
    public static final int GL_COUNTER_TYPE_AMD = 35776;
    public static final int GL_PIXEL_TRANSFORM_2D_MATRIX_EXT = 33592;
    public static final int GL_MAX_LIST_NESTING = 2865;
    public static final int GL_BLUE_MIN_CLAMP_INGR = 34146;
    public static final int GL_SIGNED_LUMINANCE_NV = 34561;
    public static final int GL_RGBA_MODE = 3121;
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 33884;
    public static final int GL_SCISSOR_COMMAND_NV = 17;
    public static final int GL_POST_CONVOLUTION_ALPHA_BIAS = 32803;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_NV = 37694;
    public static final int GL_RGBA4_S3TC = 33699;
    public static final int GL_DEPTH_STENCIL_TO_RGBA_NV = 34926;
    public static final int GL_VERTEX_PROGRAM_ARB = 34336;
    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_AMD = 37273;
    public static final int GL_LIGHTING_BIT = 64;
    public static final int GL_MODELVIEW9_ARB = 34601;
    public static final int GL_SAMPLER_RENDERBUFFER_NV = 36438;
    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_NV = 37699;
    public static final int GL_EDGEFLAG_BIT_PGI = 262144;
    public static final int GL_MAX_FRAGMENT_BINDABLE_UNIFORMS_EXT = 36323;
    public static final int GL_INDEX_MATERIAL_EXT = 33208;
    public static final int GL_HSL_LUMINOSITY = 37552;
    public static final int GL_VIDEO_CAPTURE_FIELD_UPPER_HEIGHT_NV = 36922;
    public static final int GL_FOG_COORDINATE_SOURCE = 33872;
    public static final int GL_PIXEL_TRANSFORM_2D_EXT = 33584;
    public static final int GL_OP_FRAC_EXT = 34697;
    public static final int GL_DEPENDENT_RGB_TEXTURE_CUBE_MAP_NV = 34906;
    public static final int GL_MVP_MATRIX_EXT = 34787;
    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_2D_SCALE_NV = 34897;
    public static final int GL_BLUE_SCALE = 3354;
    public static final int GL_MAX_VERTEX_UNITS_ARB = 34468;
    public static final int GL_MAP1_COLOR_4 = 3472;
    public static final int GL_TEXTURE_SHADER_NV = 34526;
    public static final int GL_TEXTURE_COMPONENTS = 4099;
    public static final int GL_RED_SNORM = 36752;
    public static final int GL_FOG_COORD = 33873;
    public static final int GL_LAST_VIDEO_CAPTURE_STATUS_NV = 36903;
    public static final int GL_PROGRAM_TEX_INDIRECTIONS_ARB = 34823;
    public static final int GL_VARIANT_EXT = 34753;
    public static final int GL_3D_COLOR = 1538;
    public static final int GL_PROGRAM_NATIVE_TEX_INDIRECTIONS_ARB = 34826;
    public static final int GL_PROGRAM_POINT_SIZE_EXT = 34370;
    public static final int GL_PROGRAM_TEMPORARIES_ARB = 34980;
    public static final int GL_ALPHA16_SNORM = 36888;
    public static final int GL_OUTPUT_TEXTURE_COORD13_EXT = 34730;
    public static final int GL_OUTPUT_TEXTURE_COORD4_EXT = 34721;
    public static final int GL_INT8_VEC3_NV = 36834;
    public static final int GL_CONSERVATIVE_RASTER_DILATE_NV = 37753;
    public static final int GL_OP_MULTIPLY_MATRIX_EXT = 34712;
    public static final int GL_EVAL_FRACTIONAL_TESSELLATION_NV = 34501;
    public static final int GL_RESAMPLE_DECIMATE_OML = 35209;
    public static final int GL_VERTEX_ATTRIB_MAP2_APPLE = 35329;
    public static final int GL_TEXTURE_SWIZZLE_B_EXT = 36420;
    public static final int GL_TEXTURE_PRIORITY = 32870;
    public static final int GL_PROXY_HISTOGRAM = 32805;
    public static final int GL_MAX_DRAW_BUFFERS_ATI = 34852;
    public static final int GL_422_REV_AVERAGE_EXT = 32975;
    public static final int GL_POLYGON_TOKEN = 1795;
    public static final int GL_COLOR_TABLE_SCALE = 32982;
    public static final int GL_PN_TRIANGLES_ATI = 34800;
    public static final int GL_MAX_SHININESS_NV = 34052;
    public static final int GL_NEGATIVE_Z_EXT = 34779;
    public static final int GL_MATRIX18_ARB = 35026;
    public static final int GL_COMPRESSED_SIGNED_LUMINANCE_LATC1_EXT = 35953;
    public static final int GL_MAX_GEOMETRY_BINDABLE_UNIFORMS_EXT = 36324;
    public static final int GL_MAX_MATRIX_PALETTE_STACK_DEPTH_ARB = 34881;
    public static final int GL_MODELVIEW20_ARB = 34612;
    public static final int GL_HILO16_NV = 34552;
    public static final int GL_ATTENUATION_EXT = 33613;
    public static final int GL_VERTEX_ATTRIB_MAP1_ORDER_APPLE = 35332;
    public static final int GL_MODELVIEW18_ARB = 34610;
    public static final int GL_ONE_EXT = 34782;
    public static final int GL_OUTPUT_TEXTURE_COORD0_EXT = 34717;
    public static final int GL_PERFQUERY_FLUSH_INTEL = 33786;
    public static final int GL_GPU_MEMORY_INFO_EVICTED_MEMORY_NVX = 36939;
    public static final int GL_TABLE_TOO_LARGE = 32817;
    public static final int GL_LINE_WIDTH_COMMAND_NV = 13;
    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
    public static final int GL_ALL_ATTRIB_BITS = 1048575;
    public static final int GL_FLOAT_RGBA_NV = 34947;
    public static final int GL_OP_SUB_EXT = 34710;
    public static final int GL_VECTOR_EXT = 34751;
    public static final int GL_CULL_MODES_NV = 34528;
    public static final int GL_FLOAT_VEC3_ARB = 35665;
    public static final int GL_UNPACK_CONSTANT_DATA_SUNX = 33237;
    public static final int GL_SAMPLER_2D_SHADOW_ARB = 35682;
    public static final int GL_PERFQUERY_COUNTER_DURATION_NORM_INTEL = 38129;
    public static final int GL_MULTISAMPLE_BIT = 536870912;
    public static final int GL_SEPARABLE_2D = 32786;
    public static final int GL_VERTEX_SHADER_BINDING_EXT = 34689;
    public static final int GL_PACK_RESAMPLE_OML = 35204;
    public static final int GL_4_BYTES = 5129;
    public static final int GL_RGBA_FLOAT32_ATI = 34836;
    public static final int GL_ACTIVE_STENCIL_FACE_EXT = 35089;
    public static final int GL_POST_CONVOLUTION_RED_BIAS = 32800;
    public static final int GL_BOOL_VEC4_ARB = 35673;
    public static final int GL_TESS_EVALUATION_PROGRAM_PARAMETER_BUFFER_NV = 35957;
    public static final int GL_POLYGON_BIT = 8;
    public static final int GL_OP_DOT3_EXT = 34692;
    public static final int GL_BITMAP_TOKEN = 1796;
    public static final int GL_MATRIX27_ARB = 35035;
    public static final int GL_INTENSITY_FLOAT16_APPLE = 34845;
    public static final int GL_CLIP_NEAR_HINT_PGI = 107040;
    public static final int GL_PERFQUERY_COUNTER_DESC_LENGTH_MAX_INTEL = 38143;
    public static final int GL_POLYGON_OFFSET_COMMAND_NV = 14;
    public static final int GL_EVAL_VERTEX_ATTRIB7_NV = 34509;
    public static final int GL_DRAW_BUFFER7_ATI = 34860;
    public static final int GL_MAX_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 34760;
    public static final int GL_VERTEX_ATTRIB_MAP2_COEFF_APPLE = 35335;
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL = 33272;
    public static final int GL_POST_COLOR_MATRIX_COLOR_TABLE = 32978;
    public static final int GL_INDEX_BITS = 3409;
    public static final int GL_INT_VEC2_ARB = 35667;
    public static final int GL_SIGNED_HILO_NV = 34553;
    public static final int GL_HI_BIAS_NV = 34580;
    public static final int GL_LUMINANCE16 = 32834;
    public static final int GL_V2F = 10784;
    public static final int GL_OP_ROUND_EXT = 34704;
    public static final int GL_POST_CONVOLUTION_BLUE_BIAS = 32802;
    public static final int GL_PIXEL_MAP_B_TO_B = 3192;
    public static final int GL_EVAL_VERTEX_ATTRIB11_NV = 34513;
    public static final int GL_NORMAL_MAP = 34065;
    public static final int GL_V3F = 10785;
    public static final int GL_MAP2_TEXTURE_COORD_4 = 3510;
    public static final int GL_MAP2_TEXTURE_COORD_3 = 3509;
    public static final int GL_MAP2_TEXTURE_COORD_2 = 3508;
    public static final int GL_MAP2_TEXTURE_COORD_1 = 3507;
    public static final int GL_LUMINANCE12 = 32833;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
    public static final int GL_VOLATILE_APPLE = 35354;
    public static final int GL_TEXCOORD3_BIT_PGI = 1073741824;
    public static final int GL_INDEX_SHIFT = 3346;
    public static final int GL_PIXEL_MAP_I_TO_B = 3188;
    public static final int GL_QUAD_STRIP = 8;
    public static final int GL_PIXEL_MAP_I_TO_A = 3189;
    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_2D_NV = 34896;
    public static final int GL_PIXEL_MAP_I_TO_G = 3187;
    public static final int GL_PIXEL_MAP_I_TO_I = 3184;
    public static final int GL_PIXEL_MAP_I_TO_R = 3186;
    public static final int GL_INDEX_LOGIC_OP = 3057;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_X_BITS_NV = 37703;
    public static final int GL_VERTEX_WEIGHTING_EXT = 34057;
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
    public static final int GL_T4F_V4F = 10792;
    public static final int GL_ALLOW_DRAW_OBJ_HINT_PGI = 107022;
    public static final int GL_ALPHA_MIN_CLAMP_INGR = 34147;
    public static final int GL_DRAW_ARRAYS_INSTANCED_COMMAND_NV = 7;
    public static final int GL_PROGRAM_INSTRUCTIONS_ARB = 34976;
    public static final int GL_RGB_FLOAT16_ATI = 34843;
    public static final int GL_MATRIX14_ARB = 35022;
    public static final int GL_DRAW_ARRAYS_COMMAND_NV = 3;
    public static final int GL_MAX_EVAL_ORDER = 3376;
    public static final int GL_VARIANT_ARRAY_EXT = 34792;
    public static final int GL_OUTPUT_TEXTURE_COORD17_EXT = 34734;
    public static final int GL_PROXY_TEXTURE_RECTANGLE_ARB = 34039;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_ARB = 37696;
    public static final int GL_MAX_PALETTE_MATRICES_ARB = 34882;
    public static final int GL_UNSIGNED_INT16_VEC4_NV = 36851;
    public static final int GL_TEXCOORD1_BIT_PGI = 268435456;
    public static final int GL_DOT_PRODUCT_TEXTURE_1D_NV = 34908;
    public static final int GL_UNSIGNED_INT_SAMPLER_RENDERBUFFER_NV = 36440;
    public static final int GL_INT64_VEC3_NV = 36842;
    public static final int GL_RETAINED_APPLE = 35355;
    public static final int GL_DRAW_BUFFER4_ATI = 34857;
    public static final int GL_MAP2_INDEX = 3505;
    public static final int GL_VERTEX_WEIGHT_ARRAY_TYPE_EXT = 34062;
    public static final int GL_SHADER_CONSISTENT_NV = 34525;
    public static final int GL_SHADER_GLOBAL_ACCESS_BARRIER_BIT_NV = 16;
    public static final int GL_MAX_PROGRAM_NATIVE_ALU_INSTRUCTIONS_ARB = 34830;
    public static final int GL_CONSERVATIVE_RASTER_DILATE_RANGE_NV = 37754;
    public static final int GL_LUMINANCE_ALPHA8UI = 36225;
    public static final int GL_IUI_V3F_EXT = 33198;
    public static final int GL_SOURCE1_ALPHA = 34185;
    public static final int GL_MODELVIEW1_ARB = 34058;
    public static final int GL_DRAW_BUFFER15_ATI = 34868;
    public static final int GL_MAP_STENCIL = 3345;
    public static final int GL_CONVOLUTION_BORDER_COLOR = 33108;
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_QUADRATIC_ATI = 34808;
    public static final int GL_MAT_SPECULAR_BIT_PGI = 67108864;
    public static final int GL_VERTEX_WEIGHT_ARRAY_STRIDE_EXT = 34063;
    public static final int GL_EMBOSS_CONSTANT_NV = 34142;
    public static final int GL_PIXEL_MAP_S_TO_S = 3185;
    public static final int GL_LUMINANCE16UI = 36218;
    public static final int GL_LUMINANCE_FLOAT32_ATI = 34840;
    public static final int GL_SECONDARY_COLOR_ARRAY = 33886;
    public static final int GL_MATRIX9_ARB = 35017;
    public static final int GL_NEGATIVE_ONE_EXT = 34783;
    public static final int GL_FRAMEBUFFER_SRGB_CAPABLE = 36282;
    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_AMD = 37272;
    public static final int GL_PROGRAM_ERROR_POSITION_ARB = 34379;
    public static final int GL_MODELVIEW23_ARB = 34615;
    public static final int GL_TEXTURE_NORMAL_EXT = 34223;
    public static final int GL_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34994;
    public static final int GL_DEPTH_SAMPLES_NV = 37677;
    public static final int GL_HISTOGRAM_RED_SIZE = 32808;
    public static final int GL_CONVOLUTION_BORDER_MODE = 32787;
    public static final int GL_MAX_PROGRAM_GENERIC_ATTRIBS_NV = 36261;
    public static final int GL_UNIFORM_ADDRESS_COMMAND_NV = 10;
    public static final int GL_VIDEO_CAPTURE_TO_422_SUPPORTED_NV = 36902;
    public static final int GL_EXCLUSION = 37536;
    public static final int GL_LOGIC_OP = 3057;
    public static final int GL_CULL_VERTEX_OBJECT_POSITION_EXT = 33196;
    public static final int GL_3D_COLOR_TEXTURE = 1539;
    public static final int GL_FRAGMENT_PROGRAM_PARAMETER_BUFFER_NV = 36260;
    public static final int GL_RG_SNORM = 36753;
    public static final int GL_PROVOKING_VERTEX_EXT = 36431;
    public static final int GL_FEEDBACK_BUFFER_SIZE = 3569;
    public static final int GL_MIN_LOD_WARNING_AMD = 37276;
    public static final int GL_DEPTH_SCALE = 3358;
    public static final int GL_CURRENT_MATRIX_INDEX_ARB = 34885;
    public static final int GL_ACCUM_RED_BITS = 3416;
    public static final int GL_MAP2_COLOR_4 = 3504;
    public static final int GL_TEXTURE_BIT = 262144;
    public static final int GL_MODELVIEW14_ARB = 34606;
    public static final int GL_SELECTION_BUFFER_POINTER = 3571;
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 2824;
    public static final int GL_DOT_PRODUCT_TEXTURE_RECTANGLE_NV = 34382;
    public static final int GL_LINE_RESET_TOKEN = 1799;
    public static final int GL_QUERY_BUFFER_AMD = 37266;
    public static final int GL_COMPRESSED_LUMINANCE_ALPHA_LATC2_EXT = 35954;
    public static final int GL_IUI_V2F_EXT = 33197;
    public static final int GL_PRIMITIVE_RESTART_INDEX_NV = 34137;
    public static final int GL_PN_TRIANGLES_POINT_MODE_ATI = 34802;
    public static final int GL_COMPRESSED_LUMINANCE_ALPHA = 34027;
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 2822;
    public static final int GL_UNSIGNED_INT8_VEC3_NV = 36846;
    public static final int GL_MAX_RASTER_SAMPLES_EXT = 37673;
    public static final int GL_READ_PIXEL_DATA_RANGE_NV = 34937;
    public static final int GL_PREFER_DOUBLEBUFFER_HINT_PGI = 107000;
    public static final int GL_PERFQUERY_COUNTER_EVENT_INTEL = 38128;
    public static final int GL_ALPHA_FLOAT32_APPLE = 34838;
    public static final int GL_UNSIGNED_INT64_VEC3_NV = 36854;
    public static final int GL_REFLECTION_MAP = 34066;
    public static final int GL_PERFQUERY_GPA_EXTENDED_COUNTERS_INTEL = 38144;
    public static final int GL_MAX_PROGRAM_ALU_INSTRUCTIONS_ARB = 34827;
    public static final int GL_DOT_PRODUCT_REFLECT_CUBE_MAP_NV = 34546;
    public static final int GL_SEPARATE_SPECULAR_COLOR = 33274;
    public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 33876;
    public static final int GL_CONVOLUTION_FILTER_BIAS = 32789;
    public static final int GL_POINT_BIT = 2;
    public static final int GL_INVERTED_SCREEN_W_REND = 33937;
    public static final int GL_INT16_VEC4_NV = 36839;
    public static final int GL_MODELVIEW27_ARB = 34619;
    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
    public static final int GL_INTENSITY8_SNORM = 36887;
    public static final int GL_OUTPUT_TEXTURE_COORD27_EXT = 34744;
    public static final int GL_MAX_PROGRAM_ENV_PARAMETERS_ARB = 34997;
    public static final int GL_COLOR_TABLE_WIDTH = 32985;
    public static final int GL_PERFQUERY_GLOBAL_CONTEXT_INTEL = 1;
    public static final int GL_MODELVIEW5_ARB = 34597;
    public static final int GL_MAT_SHININESS_BIT_PGI = 33554432;
    public static final int GL_ATTRIB_STACK_DEPTH = 2992;
    public static final int GL_COVERAGE_MODULATION_TABLE_SIZE_NV = 37683;
    public static final int GL_EVAL_VERTEX_ATTRIB14_NV = 34516;
    public static final int GL_VIDEO_CAPTURE_FIELD_LOWER_HEIGHT_NV = 36923;
    public static final int GL_MAX_PIXEL_TRANSFORM_2D_STACK_DEPTH_EXT = 33591;
    public static final int GL_DSDT_MAG_INTENSITY_NV = 34524;
    public static final int GL_HISTOGRAM_BLUE_SIZE = 32810;
    public static final int GL_MAP2_NORMAL = 3506;
    public static final int GL_MAX_PROGRAM_INSTRUCTIONS_ARB = 34977;
    public static final int GL_PERFQUERY_COUNTER_DATA_DOUBLE_INTEL = 38139;
    public static final int GL_FLOAT16_NV = 36856;
    public static final int GL_OP_RECIP_EXT = 34708;
    public static final int GL_VIRTUAL_PAGE_SIZE_Z_AMD = 37271;
    public static final int GL_LUMINANCE_ALPHA_FLOAT32_ATI = 34841;
    public static final int GL_CURRENT_RASTER_INDEX = 2821;
    public static final int GL_HISTOGRAM_GREEN_SIZE = 32809;
    public static final int GL_INT64_VEC4_ARB = 36843;
    public static final int GL_MIRROR_CLAMP_TO_EDGE_EXT = 34627;
    public static final int GL_WEIGHT_ARRAY_STRIDE_ARB = 34474;
    public static final int GL_UNIFORM_BUFFER_LENGTH_NV = 37744;
    public static final int GL_DRAW_BUFFER11_ATI = 34864;
    public static final int GL_PERFQUERY_WAIT_INTEL = 33787;
    public static final int GL_MAX_VERTEX_SHADER_INSTRUCTIONS_EXT = 34757;
    public static final int GL_FRAGMENT_COVERAGE_COLOR_NV = 37598;
    public static final int GL_POLYGON_STIPPLE_BIT = 16;
    public static final int GL_MATRIX5_ARB = 35013;
    public static final int GL_OP_MADD_EXT = 34696;
    public static final int GL_RGBA_FLOAT_MODE_ATI = 34848;
    public static final int GL_OUTPUT_TEXTURE_COORD10_EXT = 34727;
    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_APPLE = 34081;
    public static final int GL_ALPHA8I = 36240;
    public static final int GL_INTENSITY_FLOAT32_ATI = 34839;
    public static final int GL_MODELVIEW0_STACK_DEPTH_EXT = 2979;
    public static final int GL_INTERLACE_READ_OML = 35201;
    public static final int GL_VERTEX_ATTRIB_MAP1_DOMAIN_APPLE = 35333;
    public static final int GL_OBJECT_TYPE_ARB = 35662;
    public static final int GL_TEXCOORD2_BIT_PGI = 536870912;
    public static final int GL_PERFMON_RESULT_SIZE_AMD = 35781;
    public static final int GL_PN_TRIANGLES_POINT_MODE_CUBIC_ATI = 34806;
    public static final int GL_WARPS_PER_SM_NV = 37690;
    public static final int GL_WEIGHT_ARRAY_ARB = 34477;
    public static final int GL_SOURCE2_RGB = 34178;
    public static final int GL_EFFECTIVE_RASTER_SAMPLES_EXT = 37676;
    public static final int GL_FLOAT_MAT3_ARB = 35675;
    public static final int GL_FLOAT16_VEC2_NV = 36857;
    public static final int GL_LAYOUT_DEFAULT_INTEL = 0;
    public static final int GL_N3F_V3F = 10789;
    public static final int GL_DRAW_BUFFER0_ATI = 34853;
    public static final int GL_MAT_DIFFUSE_BIT_PGI = 4194304;
    public static final int GL_POLYGON = 9;
    public static final int GL_MATRIX20_ARB = 35028;
    public static final int GL_INDEX_TEST_REF_EXT = 33207;
    public static final int GL_COLOR_INDEXES = 5635;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_BASE_VIEW_INDEX_OVR = 38450;
    public static final int GL_TEXTURE_MATERIAL_FACE_EXT = 33617;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET = 36052;
    public static final int GL_Y_EXT = 34774;
    public static final int GL_AUX0 = 1033;
    public static final int GL_AUX1 = 1034;
    public static final int GL_RGBA_FLOAT_MODE = 34848;
    public static final int GL_INVARIANT_DATATYPE_EXT = 34795;
    public static final int GL_VERTEX_DATA_HINT_PGI = 107050;
    public static final int GL_AUX2 = 1035;
    public static final int GL_AUX3 = 1036;
    public static final int GL_INTENSITY16I = 36235;
    public static final int GL_SCREEN = 37525;
    public static final int GL_OUTPUT_TEXTURE_COORD23_EXT = 34740;
    public static final int GL_OBJECT_LINK_STATUS_ARB = 35714;
    public static final int GL_LUMINANCE8_ALPHA8_SNORM = 36886;
    public static final int GL_2D = 1536;
    public static final int GL_COLOR_TABLE_GREEN_SIZE = 32987;
    public static final int GL_OUTPUT_TEXTURE_COORD8_EXT = 34725;
    public static final int GL_MAX_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34979;
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 3248;
    public static final int GL_MAX_PROGRAM_GENERIC_RESULTS_NV = 36262;
    public static final int GL_DEPTH_BOUNDS_EXT = 34961;
    public static final int GL_MODELVIEW10_ARB = 34602;
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 2993;
    public static final int GL_PIXEL_UNPACK_BUFFER_EXT = 35052;
    public static final int GL_INTENSITY8I = 36241;
    public static final int GL_OFFSET_TEXTURE_2D_SCALE_NV = 34530;
    
    void glClearIndex(final float p0);
    
    void glIndexMask(final int p0);
    
    void glLineStipple(final int p0, final short p1);
    
    void glPolygonStipple(final ByteBuffer p0);
    
    void glPolygonStipple(final byte[] p0, final int p1);
    
    void glPolygonStipple(final long p0);
    
    void glGetPolygonStipple(final ByteBuffer p0);
    
    void glGetPolygonStipple(final byte[] p0, final int p1);
    
    void glGetPolygonStipple(final long p0);
    
    void glEdgeFlag(final boolean p0);
    
    void glEdgeFlagv(final ByteBuffer p0);
    
    void glEdgeFlagv(final byte[] p0, final int p1);
    
    void glClipPlane(final int p0, final DoubleBuffer p1);
    
    void glClipPlane(final int p0, final double[] p1, final int p2);
    
    void glGetClipPlane(final int p0, final DoubleBuffer p1);
    
    void glGetClipPlane(final int p0, final double[] p1, final int p2);
    
    void glPushAttrib(final int p0);
    
    void glPopAttrib();
    
    int glRenderMode(final int p0);
    
    void glClearAccum(final float p0, final float p1, final float p2, final float p3);
    
    void glAccum(final int p0, final float p1);
    
    void glLoadMatrixd(final DoubleBuffer p0);
    
    void glLoadMatrixd(final double[] p0, final int p1);
    
    void glMultMatrixd(final DoubleBuffer p0);
    
    void glMultMatrixd(final double[] p0, final int p1);
    
    void glRotated(final double p0, final double p1, final double p2, final double p3);
    
    void glScaled(final double p0, final double p1, final double p2);
    
    void glTranslated(final double p0, final double p1, final double p2);
    
    boolean glIsList(final int p0);
    
    void glDeleteLists(final int p0, final int p1);
    
    int glGenLists(final int p0);
    
    void glNewList(final int p0, final int p1);
    
    void glEndList();
    
    void glCallList(final int p0);
    
    void glCallLists(final int p0, final int p1, final Buffer p2);
    
    void glListBase(final int p0);
    
    void glBegin(final int p0);
    
    void glEnd();
    
    void glVertex2d(final double p0, final double p1);
    
    void glVertex2f(final float p0, final float p1);
    
    void glVertex2i(final int p0, final int p1);
    
    void glVertex2s(final short p0, final short p1);
    
    void glVertex3d(final double p0, final double p1, final double p2);
    
    void glVertex3f(final float p0, final float p1, final float p2);
    
    void glVertex3i(final int p0, final int p1, final int p2);
    
    void glVertex3s(final short p0, final short p1, final short p2);
    
    void glVertex4d(final double p0, final double p1, final double p2, final double p3);
    
    void glVertex4f(final float p0, final float p1, final float p2, final float p3);
    
    void glVertex4i(final int p0, final int p1, final int p2, final int p3);
    
    void glVertex4s(final short p0, final short p1, final short p2, final short p3);
    
    void glVertex2dv(final DoubleBuffer p0);
    
    void glVertex2dv(final double[] p0, final int p1);
    
    void glVertex2fv(final FloatBuffer p0);
    
    void glVertex2fv(final float[] p0, final int p1);
    
    void glVertex2iv(final IntBuffer p0);
    
    void glVertex2iv(final int[] p0, final int p1);
    
    void glVertex2sv(final ShortBuffer p0);
    
    void glVertex2sv(final short[] p0, final int p1);
    
    void glVertex3dv(final DoubleBuffer p0);
    
    void glVertex3dv(final double[] p0, final int p1);
    
    void glVertex3fv(final FloatBuffer p0);
    
    void glVertex3fv(final float[] p0, final int p1);
    
    void glVertex3iv(final IntBuffer p0);
    
    void glVertex3iv(final int[] p0, final int p1);
    
    void glVertex3sv(final ShortBuffer p0);
    
    void glVertex3sv(final short[] p0, final int p1);
    
    void glVertex4dv(final DoubleBuffer p0);
    
    void glVertex4dv(final double[] p0, final int p1);
    
    void glVertex4fv(final FloatBuffer p0);
    
    void glVertex4fv(final float[] p0, final int p1);
    
    void glVertex4iv(final IntBuffer p0);
    
    void glVertex4iv(final int[] p0, final int p1);
    
    void glVertex4sv(final ShortBuffer p0);
    
    void glVertex4sv(final short[] p0, final int p1);
    
    void glNormal3b(final byte p0, final byte p1, final byte p2);
    
    void glNormal3d(final double p0, final double p1, final double p2);
    
    void glNormal3i(final int p0, final int p1, final int p2);
    
    void glNormal3s(final short p0, final short p1, final short p2);
    
    void glNormal3bv(final ByteBuffer p0);
    
    void glNormal3bv(final byte[] p0, final int p1);
    
    void glNormal3dv(final DoubleBuffer p0);
    
    void glNormal3dv(final double[] p0, final int p1);
    
    void glNormal3fv(final FloatBuffer p0);
    
    void glNormal3fv(final float[] p0, final int p1);
    
    void glNormal3iv(final IntBuffer p0);
    
    void glNormal3iv(final int[] p0, final int p1);
    
    void glNormal3sv(final ShortBuffer p0);
    
    void glNormal3sv(final short[] p0, final int p1);
    
    void glIndexd(final double p0);
    
    void glIndexf(final float p0);
    
    void glIndexi(final int p0);
    
    void glIndexs(final short p0);
    
    void glIndexdv(final DoubleBuffer p0);
    
    void glIndexdv(final double[] p0, final int p1);
    
    void glIndexfv(final FloatBuffer p0);
    
    void glIndexfv(final float[] p0, final int p1);
    
    void glIndexiv(final IntBuffer p0);
    
    void glIndexiv(final int[] p0, final int p1);
    
    void glIndexsv(final ShortBuffer p0);
    
    void glIndexsv(final short[] p0, final int p1);
    
    void glColor3b(final byte p0, final byte p1, final byte p2);
    
    void glColor3d(final double p0, final double p1, final double p2);
    
    void glColor3f(final float p0, final float p1, final float p2);
    
    void glColor3i(final int p0, final int p1, final int p2);
    
    void glColor3s(final short p0, final short p1, final short p2);
    
    void glColor3ub(final byte p0, final byte p1, final byte p2);
    
    void glColor3ui(final int p0, final int p1, final int p2);
    
    void glColor3us(final short p0, final short p1, final short p2);
    
    void glColor4b(final byte p0, final byte p1, final byte p2, final byte p3);
    
    void glColor4d(final double p0, final double p1, final double p2, final double p3);
    
    void glColor4i(final int p0, final int p1, final int p2, final int p3);
    
    void glColor4s(final short p0, final short p1, final short p2, final short p3);
    
    void glColor4ui(final int p0, final int p1, final int p2, final int p3);
    
    void glColor4us(final short p0, final short p1, final short p2, final short p3);
    
    void glColor3bv(final ByteBuffer p0);
    
    void glColor3bv(final byte[] p0, final int p1);
    
    void glColor3dv(final DoubleBuffer p0);
    
    void glColor3dv(final double[] p0, final int p1);
    
    void glColor3fv(final FloatBuffer p0);
    
    void glColor3fv(final float[] p0, final int p1);
    
    void glColor3iv(final IntBuffer p0);
    
    void glColor3iv(final int[] p0, final int p1);
    
    void glColor3sv(final ShortBuffer p0);
    
    void glColor3sv(final short[] p0, final int p1);
    
    void glColor3ubv(final ByteBuffer p0);
    
    void glColor3ubv(final byte[] p0, final int p1);
    
    void glColor3uiv(final IntBuffer p0);
    
    void glColor3uiv(final int[] p0, final int p1);
    
    void glColor3usv(final ShortBuffer p0);
    
    void glColor3usv(final short[] p0, final int p1);
    
    void glColor4bv(final ByteBuffer p0);
    
    void glColor4bv(final byte[] p0, final int p1);
    
    void glColor4dv(final DoubleBuffer p0);
    
    void glColor4dv(final double[] p0, final int p1);
    
    void glColor4fv(final FloatBuffer p0);
    
    void glColor4fv(final float[] p0, final int p1);
    
    void glColor4iv(final IntBuffer p0);
    
    void glColor4iv(final int[] p0, final int p1);
    
    void glColor4sv(final ShortBuffer p0);
    
    void glColor4sv(final short[] p0, final int p1);
    
    void glColor4ubv(final ByteBuffer p0);
    
    void glColor4ubv(final byte[] p0, final int p1);
    
    void glColor4uiv(final IntBuffer p0);
    
    void glColor4uiv(final int[] p0, final int p1);
    
    void glColor4usv(final ShortBuffer p0);
    
    void glColor4usv(final short[] p0, final int p1);
    
    void glTexCoord1d(final double p0);
    
    void glTexCoord1f(final float p0);
    
    void glTexCoord1i(final int p0);
    
    void glTexCoord1s(final short p0);
    
    void glTexCoord2d(final double p0, final double p1);
    
    void glTexCoord2f(final float p0, final float p1);
    
    void glTexCoord2i(final int p0, final int p1);
    
    void glTexCoord2s(final short p0, final short p1);
    
    void glTexCoord3d(final double p0, final double p1, final double p2);
    
    void glTexCoord3f(final float p0, final float p1, final float p2);
    
    void glTexCoord3i(final int p0, final int p1, final int p2);
    
    void glTexCoord3s(final short p0, final short p1, final short p2);
    
    void glTexCoord4d(final double p0, final double p1, final double p2, final double p3);
    
    void glTexCoord4f(final float p0, final float p1, final float p2, final float p3);
    
    void glTexCoord4i(final int p0, final int p1, final int p2, final int p3);
    
    void glTexCoord4s(final short p0, final short p1, final short p2, final short p3);
    
    void glTexCoord1dv(final DoubleBuffer p0);
    
    void glTexCoord1dv(final double[] p0, final int p1);
    
    void glTexCoord1fv(final FloatBuffer p0);
    
    void glTexCoord1fv(final float[] p0, final int p1);
    
    void glTexCoord1iv(final IntBuffer p0);
    
    void glTexCoord1iv(final int[] p0, final int p1);
    
    void glTexCoord1sv(final ShortBuffer p0);
    
    void glTexCoord1sv(final short[] p0, final int p1);
    
    void glTexCoord2dv(final DoubleBuffer p0);
    
    void glTexCoord2dv(final double[] p0, final int p1);
    
    void glTexCoord2fv(final FloatBuffer p0);
    
    void glTexCoord2fv(final float[] p0, final int p1);
    
    void glTexCoord2iv(final IntBuffer p0);
    
    void glTexCoord2iv(final int[] p0, final int p1);
    
    void glTexCoord2sv(final ShortBuffer p0);
    
    void glTexCoord2sv(final short[] p0, final int p1);
    
    void glTexCoord3dv(final DoubleBuffer p0);
    
    void glTexCoord3dv(final double[] p0, final int p1);
    
    void glTexCoord3fv(final FloatBuffer p0);
    
    void glTexCoord3fv(final float[] p0, final int p1);
    
    void glTexCoord3iv(final IntBuffer p0);
    
    void glTexCoord3iv(final int[] p0, final int p1);
    
    void glTexCoord3sv(final ShortBuffer p0);
    
    void glTexCoord3sv(final short[] p0, final int p1);
    
    void glTexCoord4dv(final DoubleBuffer p0);
    
    void glTexCoord4dv(final double[] p0, final int p1);
    
    void glTexCoord4fv(final FloatBuffer p0);
    
    void glTexCoord4fv(final float[] p0, final int p1);
    
    void glTexCoord4iv(final IntBuffer p0);
    
    void glTexCoord4iv(final int[] p0, final int p1);
    
    void glTexCoord4sv(final ShortBuffer p0);
    
    void glTexCoord4sv(final short[] p0, final int p1);
    
    void glRasterPos2d(final double p0, final double p1);
    
    void glRasterPos2f(final float p0, final float p1);
    
    void glRasterPos2i(final int p0, final int p1);
    
    void glRasterPos2s(final short p0, final short p1);
    
    void glRasterPos3d(final double p0, final double p1, final double p2);
    
    void glRasterPos3f(final float p0, final float p1, final float p2);
    
    void glRasterPos3i(final int p0, final int p1, final int p2);
    
    void glRasterPos3s(final short p0, final short p1, final short p2);
    
    void glRasterPos4d(final double p0, final double p1, final double p2, final double p3);
    
    void glRasterPos4f(final float p0, final float p1, final float p2, final float p3);
    
    void glRasterPos4i(final int p0, final int p1, final int p2, final int p3);
    
    void glRasterPos4s(final short p0, final short p1, final short p2, final short p3);
    
    void glRasterPos2dv(final DoubleBuffer p0);
    
    void glRasterPos2dv(final double[] p0, final int p1);
    
    void glRasterPos2fv(final FloatBuffer p0);
    
    void glRasterPos2fv(final float[] p0, final int p1);
    
    void glRasterPos2iv(final IntBuffer p0);
    
    void glRasterPos2iv(final int[] p0, final int p1);
    
    void glRasterPos2sv(final ShortBuffer p0);
    
    void glRasterPos2sv(final short[] p0, final int p1);
    
    void glRasterPos3dv(final DoubleBuffer p0);
    
    void glRasterPos3dv(final double[] p0, final int p1);
    
    void glRasterPos3fv(final FloatBuffer p0);
    
    void glRasterPos3fv(final float[] p0, final int p1);
    
    void glRasterPos3iv(final IntBuffer p0);
    
    void glRasterPos3iv(final int[] p0, final int p1);
    
    void glRasterPos3sv(final ShortBuffer p0);
    
    void glRasterPos3sv(final short[] p0, final int p1);
    
    void glRasterPos4dv(final DoubleBuffer p0);
    
    void glRasterPos4dv(final double[] p0, final int p1);
    
    void glRasterPos4fv(final FloatBuffer p0);
    
    void glRasterPos4fv(final float[] p0, final int p1);
    
    void glRasterPos4iv(final IntBuffer p0);
    
    void glRasterPos4iv(final int[] p0, final int p1);
    
    void glRasterPos4sv(final ShortBuffer p0);
    
    void glRasterPos4sv(final short[] p0, final int p1);
    
    void glRectd(final double p0, final double p1, final double p2, final double p3);
    
    void glRectf(final float p0, final float p1, final float p2, final float p3);
    
    void glRecti(final int p0, final int p1, final int p2, final int p3);
    
    void glRects(final short p0, final short p1, final short p2, final short p3);
    
    void glRectdv(final DoubleBuffer p0, final DoubleBuffer p1);
    
    void glRectdv(final double[] p0, final int p1, final double[] p2, final int p3);
    
    void glRectfv(final FloatBuffer p0, final FloatBuffer p1);
    
    void glRectfv(final float[] p0, final int p1, final float[] p2, final int p3);
    
    void glRectiv(final IntBuffer p0, final IntBuffer p1);
    
    void glRectiv(final int[] p0, final int p1, final int[] p2, final int p3);
    
    void glRectsv(final ShortBuffer p0, final ShortBuffer p1);
    
    void glRectsv(final short[] p0, final int p1, final short[] p2, final int p3);
    
    void glLighti(final int p0, final int p1, final int p2);
    
    void glLightiv(final int p0, final int p1, final IntBuffer p2);
    
    void glLightiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetLightiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetLightiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glLightModeli(final int p0, final int p1);
    
    void glLightModeliv(final int p0, final IntBuffer p1);
    
    void glLightModeliv(final int p0, final int[] p1, final int p2);
    
    void glMateriali(final int p0, final int p1, final int p2);
    
    void glMaterialiv(final int p0, final int p1, final IntBuffer p2);
    
    void glMaterialiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetMaterialiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetMaterialiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glColorMaterial(final int p0, final int p1);
    
    void glPixelZoom(final float p0, final float p1);
    
    void glPixelTransferf(final int p0, final float p1);
    
    void glPixelTransferi(final int p0, final int p1);
    
    void glPixelMapfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glPixelMapfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glPixelMapfv(final int p0, final int p1, final long p2);
    
    void glPixelMapuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glPixelMapuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glPixelMapuiv(final int p0, final int p1, final long p2);
    
    void glPixelMapusv(final int p0, final int p1, final ShortBuffer p2);
    
    void glPixelMapusv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glPixelMapusv(final int p0, final int p1, final long p2);
    
    void glGetPixelMapfv(final int p0, final FloatBuffer p1);
    
    void glGetPixelMapfv(final int p0, final float[] p1, final int p2);
    
    void glGetPixelMapfv(final int p0, final long p1);
    
    void glGetPixelMapuiv(final int p0, final IntBuffer p1);
    
    void glGetPixelMapuiv(final int p0, final int[] p1, final int p2);
    
    void glGetPixelMapuiv(final int p0, final long p1);
    
    void glGetPixelMapusv(final int p0, final ShortBuffer p1);
    
    void glGetPixelMapusv(final int p0, final short[] p1, final int p2);
    
    void glGetPixelMapusv(final int p0, final long p1);
    
    void glBitmap(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final ByteBuffer p6);
    
    void glBitmap(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final byte[] p6, final int p7);
    
    void glBitmap(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    void glDrawPixels(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glDrawPixels(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glCopyPixels(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glTexGend(final int p0, final int p1, final double p2);
    
    void glTexGenf(final int p0, final int p1, final float p2);
    
    void glTexGeni(final int p0, final int p1, final int p2);
    
    void glTexGendv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glTexGendv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glTexGenfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glTexGenfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glTexGeniv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexGeniv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexGendv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetTexGendv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetTexGenfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetTexGenfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetTexGeniv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexGeniv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMap1d(final int p0, final double p1, final double p2, final int p3, final int p4, final DoubleBuffer p5);
    
    void glMap1d(final int p0, final double p1, final double p2, final int p3, final int p4, final double[] p5, final int p6);
    
    void glMap1f(final int p0, final float p1, final float p2, final int p3, final int p4, final FloatBuffer p5);
    
    void glMap1f(final int p0, final float p1, final float p2, final int p3, final int p4, final float[] p5, final int p6);
    
    void glMap2d(final int p0, final double p1, final double p2, final int p3, final int p4, final double p5, final double p6, final int p7, final int p8, final DoubleBuffer p9);
    
    void glMap2d(final int p0, final double p1, final double p2, final int p3, final int p4, final double p5, final double p6, final int p7, final int p8, final double[] p9, final int p10);
    
    void glMap2f(final int p0, final float p1, final float p2, final int p3, final int p4, final float p5, final float p6, final int p7, final int p8, final FloatBuffer p9);
    
    void glMap2f(final int p0, final float p1, final float p2, final int p3, final int p4, final float p5, final float p6, final int p7, final int p8, final float[] p9, final int p10);
    
    void glGetMapdv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetMapdv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetMapfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMapfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetMapiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetMapiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glEvalCoord1d(final double p0);
    
    void glEvalCoord1f(final float p0);
    
    void glEvalCoord1dv(final DoubleBuffer p0);
    
    void glEvalCoord1dv(final double[] p0, final int p1);
    
    void glEvalCoord1fv(final FloatBuffer p0);
    
    void glEvalCoord1fv(final float[] p0, final int p1);
    
    void glEvalCoord2d(final double p0, final double p1);
    
    void glEvalCoord2f(final float p0, final float p1);
    
    void glEvalCoord2dv(final DoubleBuffer p0);
    
    void glEvalCoord2dv(final double[] p0, final int p1);
    
    void glEvalCoord2fv(final FloatBuffer p0);
    
    void glEvalCoord2fv(final float[] p0, final int p1);
    
    void glMapGrid1d(final int p0, final double p1, final double p2);
    
    void glMapGrid1f(final int p0, final float p1, final float p2);
    
    void glMapGrid2d(final int p0, final double p1, final double p2, final int p3, final double p4, final double p5);
    
    void glMapGrid2f(final int p0, final float p1, final float p2, final int p3, final float p4, final float p5);
    
    void glEvalPoint1(final int p0);
    
    void glEvalPoint2(final int p0, final int p1);
    
    void glEvalMesh1(final int p0, final int p1, final int p2);
    
    void glEvalMesh2(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glFogi(final int p0, final int p1);
    
    void glFogiv(final int p0, final IntBuffer p1);
    
    void glFogiv(final int p0, final int[] p1, final int p2);
    
    void glFeedbackBuffer(final int p0, final int p1, final FloatBuffer p2);
    
    void glPassThrough(final float p0);
    
    void glSelectBuffer(final int p0, final IntBuffer p1);
    
    void glInitNames();
    
    void glLoadName(final int p0);
    
    void glPushName(final int p0);
    
    void glPopName();
    
    void glIndexub(final byte p0);
    
    void glIndexubv(final ByteBuffer p0);
    
    void glIndexubv(final byte[] p0, final int p1);
    
    void glPushClientAttrib(final int p0);
    
    void glPopClientAttrib();
    
    void glIndexPointer(final int p0, final int p1, final Buffer p2);
    
    void glEdgeFlagPointer(final int p0, final Buffer p1);
    
    void glEdgeFlagPointer(final int p0, final long p1);
    
    void glArrayElement(final int p0);
    
    void glInterleavedArrays(final int p0, final int p1, final Buffer p2);
    
    void glInterleavedArrays(final int p0, final int p1, final long p2);
    
    void glPrioritizeTextures(final int p0, final IntBuffer p1, final FloatBuffer p2);
    
    void glPrioritizeTextures(final int p0, final int[] p1, final int p2, final float[] p3, final int p4);
    
    boolean glAreTexturesResident(final int p0, final IntBuffer p1, final ByteBuffer p2);
    
    boolean glAreTexturesResident(final int p0, final int[] p1, final int p2, final byte[] p3, final int p4);
    
    void glMultiTexCoord1d(final int p0, final double p1);
    
    void glMultiTexCoord1dv(final int p0, final DoubleBuffer p1);
    
    void glMultiTexCoord1dv(final int p0, final double[] p1, final int p2);
    
    void glMultiTexCoord1f(final int p0, final float p1);
    
    void glMultiTexCoord1fv(final int p0, final FloatBuffer p1);
    
    void glMultiTexCoord1fv(final int p0, final float[] p1, final int p2);
    
    void glMultiTexCoord1i(final int p0, final int p1);
    
    void glMultiTexCoord1iv(final int p0, final IntBuffer p1);
    
    void glMultiTexCoord1iv(final int p0, final int[] p1, final int p2);
    
    void glMultiTexCoord1s(final int p0, final short p1);
    
    void glMultiTexCoord1sv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord1sv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord2d(final int p0, final double p1, final double p2);
    
    void glMultiTexCoord2dv(final int p0, final DoubleBuffer p1);
    
    void glMultiTexCoord2dv(final int p0, final double[] p1, final int p2);
    
    void glMultiTexCoord2f(final int p0, final float p1, final float p2);
    
    void glMultiTexCoord2fv(final int p0, final FloatBuffer p1);
    
    void glMultiTexCoord2fv(final int p0, final float[] p1, final int p2);
    
    void glMultiTexCoord2i(final int p0, final int p1, final int p2);
    
    void glMultiTexCoord2iv(final int p0, final IntBuffer p1);
    
    void glMultiTexCoord2iv(final int p0, final int[] p1, final int p2);
    
    void glMultiTexCoord2s(final int p0, final short p1, final short p2);
    
    void glMultiTexCoord2sv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord2sv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord3d(final int p0, final double p1, final double p2, final double p3);
    
    void glMultiTexCoord3dv(final int p0, final DoubleBuffer p1);
    
    void glMultiTexCoord3dv(final int p0, final double[] p1, final int p2);
    
    void glMultiTexCoord3f(final int p0, final float p1, final float p2, final float p3);
    
    void glMultiTexCoord3fv(final int p0, final FloatBuffer p1);
    
    void glMultiTexCoord3fv(final int p0, final float[] p1, final int p2);
    
    void glMultiTexCoord3i(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiTexCoord3iv(final int p0, final IntBuffer p1);
    
    void glMultiTexCoord3iv(final int p0, final int[] p1, final int p2);
    
    void glMultiTexCoord3s(final int p0, final short p1, final short p2, final short p3);
    
    void glMultiTexCoord3sv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord3sv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord4d(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glMultiTexCoord4dv(final int p0, final DoubleBuffer p1);
    
    void glMultiTexCoord4dv(final int p0, final double[] p1, final int p2);
    
    void glMultiTexCoord4fv(final int p0, final FloatBuffer p1);
    
    void glMultiTexCoord4fv(final int p0, final float[] p1, final int p2);
    
    void glMultiTexCoord4i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glMultiTexCoord4iv(final int p0, final IntBuffer p1);
    
    void glMultiTexCoord4iv(final int p0, final int[] p1, final int p2);
    
    void glMultiTexCoord4s(final int p0, final short p1, final short p2, final short p3, final short p4);
    
    void glMultiTexCoord4sv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord4sv(final int p0, final short[] p1, final int p2);
    
    void glLoadTransposeMatrixf(final FloatBuffer p0);
    
    void glLoadTransposeMatrixf(final float[] p0, final int p1);
    
    void glLoadTransposeMatrixd(final DoubleBuffer p0);
    
    void glLoadTransposeMatrixd(final double[] p0, final int p1);
    
    void glMultTransposeMatrixf(final FloatBuffer p0);
    
    void glMultTransposeMatrixf(final float[] p0, final int p1);
    
    void glMultTransposeMatrixd(final DoubleBuffer p0);
    
    void glMultTransposeMatrixd(final double[] p0, final int p1);
    
    void glFogCoordf(final float p0);
    
    void glFogCoordfv(final FloatBuffer p0);
    
    void glFogCoordfv(final float[] p0, final int p1);
    
    void glFogCoordd(final double p0);
    
    void glFogCoorddv(final DoubleBuffer p0);
    
    void glFogCoorddv(final double[] p0, final int p1);
    
    void glFogCoordPointer(final int p0, final int p1, final Buffer p2);
    
    void glFogCoordPointer(final int p0, final int p1, final long p2);
    
    void glSecondaryColor3b(final byte p0, final byte p1, final byte p2);
    
    void glSecondaryColor3bv(final ByteBuffer p0);
    
    void glSecondaryColor3bv(final byte[] p0, final int p1);
    
    void glSecondaryColor3d(final double p0, final double p1, final double p2);
    
    void glSecondaryColor3dv(final DoubleBuffer p0);
    
    void glSecondaryColor3dv(final double[] p0, final int p1);
    
    void glSecondaryColor3f(final float p0, final float p1, final float p2);
    
    void glSecondaryColor3fv(final FloatBuffer p0);
    
    void glSecondaryColor3fv(final float[] p0, final int p1);
    
    void glSecondaryColor3i(final int p0, final int p1, final int p2);
    
    void glSecondaryColor3iv(final IntBuffer p0);
    
    void glSecondaryColor3iv(final int[] p0, final int p1);
    
    void glSecondaryColor3s(final short p0, final short p1, final short p2);
    
    void glSecondaryColor3sv(final ShortBuffer p0);
    
    void glSecondaryColor3sv(final short[] p0, final int p1);
    
    void glSecondaryColor3ub(final byte p0, final byte p1, final byte p2);
    
    void glSecondaryColor3ubv(final ByteBuffer p0);
    
    void glSecondaryColor3ubv(final byte[] p0, final int p1);
    
    void glSecondaryColor3ui(final int p0, final int p1, final int p2);
    
    void glSecondaryColor3uiv(final IntBuffer p0);
    
    void glSecondaryColor3uiv(final int[] p0, final int p1);
    
    void glSecondaryColor3us(final short p0, final short p1, final short p2);
    
    void glSecondaryColor3usv(final ShortBuffer p0);
    
    void glSecondaryColor3usv(final short[] p0, final int p1);
    
    void glSecondaryColorPointer(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glSecondaryColorPointer(final int p0, final int p1, final int p2, final long p3);
    
    void glWindowPos2d(final double p0, final double p1);
    
    void glWindowPos2dv(final DoubleBuffer p0);
    
    void glWindowPos2dv(final double[] p0, final int p1);
    
    void glWindowPos2f(final float p0, final float p1);
    
    void glWindowPos2fv(final FloatBuffer p0);
    
    void glWindowPos2fv(final float[] p0, final int p1);
    
    void glWindowPos2i(final int p0, final int p1);
    
    void glWindowPos2iv(final IntBuffer p0);
    
    void glWindowPos2iv(final int[] p0, final int p1);
    
    void glWindowPos2s(final short p0, final short p1);
    
    void glWindowPos2sv(final ShortBuffer p0);
    
    void glWindowPos2sv(final short[] p0, final int p1);
    
    void glWindowPos3d(final double p0, final double p1, final double p2);
    
    void glWindowPos3dv(final DoubleBuffer p0);
    
    void glWindowPos3dv(final double[] p0, final int p1);
    
    void glWindowPos3f(final float p0, final float p1, final float p2);
    
    void glWindowPos3fv(final FloatBuffer p0);
    
    void glWindowPos3fv(final float[] p0, final int p1);
    
    void glWindowPos3i(final int p0, final int p1, final int p2);
    
    void glWindowPos3iv(final IntBuffer p0);
    
    void glWindowPos3iv(final int[] p0, final int p1);
    
    void glWindowPos3s(final short p0, final short p1, final short p2);
    
    void glWindowPos3sv(final ShortBuffer p0);
    
    void glWindowPos3sv(final short[] p0, final int p1);
    
    void glClearNamedBufferData(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glClearNamedBufferSubData(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final Buffer p6);
    
    void glNamedFramebufferParameteri(final int p0, final int p1, final int p2);
    
    void glGetNamedFramebufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedFramebufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetnMapdv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glGetnMapdv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glGetnMapfv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetnMapfv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetnMapiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetnMapiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetnPixelMapfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetnPixelMapfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetnPixelMapuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetnPixelMapuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetnPixelMapusv(final int p0, final int p1, final ShortBuffer p2);
    
    void glGetnPixelMapusv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glGetnPolygonStipple(final int p0, final ByteBuffer p1);
    
    void glGetnPolygonStipple(final int p0, final byte[] p1, final int p2);
    
    void glGetnColorTable(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glGetnConvolutionFilter(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glGetnSeparableFilter(final int p0, final int p1, final int p2, final int p3, final Buffer p4, final int p5, final Buffer p6, final Buffer p7);
    
    void glGetnHistogram(final int p0, final boolean p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glGetnMinmax(final int p0, final boolean p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glProgramStringARB(final int p0, final int p1, final int p2, final String p3);
    
    void glBindProgramARB(final int p0, final int p1);
    
    void glDeleteProgramsARB(final int p0, final IntBuffer p1);
    
    void glDeleteProgramsARB(final int p0, final int[] p1, final int p2);
    
    void glGenProgramsARB(final int p0, final IntBuffer p1);
    
    void glGenProgramsARB(final int p0, final int[] p1, final int p2);
    
    void glProgramEnvParameter4dARB(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5);
    
    void glProgramEnvParameter4dvARB(final int p0, final int p1, final DoubleBuffer p2);
    
    void glProgramEnvParameter4dvARB(final int p0, final int p1, final double[] p2, final int p3);
    
    void glProgramEnvParameter4fARB(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5);
    
    void glProgramEnvParameter4fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glProgramEnvParameter4fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glProgramLocalParameter4dARB(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5);
    
    void glProgramLocalParameter4dvARB(final int p0, final int p1, final DoubleBuffer p2);
    
    void glProgramLocalParameter4dvARB(final int p0, final int p1, final double[] p2, final int p3);
    
    void glProgramLocalParameter4fARB(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5);
    
    void glProgramLocalParameter4fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glProgramLocalParameter4fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetProgramEnvParameterdvARB(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetProgramEnvParameterdvARB(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetProgramEnvParameterfvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetProgramEnvParameterfvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetProgramLocalParameterdvARB(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetProgramLocalParameterdvARB(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetProgramLocalParameterfvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetProgramLocalParameterfvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetProgramivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramStringARB(final int p0, final int p1, final Buffer p2);
    
    boolean glIsProgramARB(final int p0);
    
    void glUniform1i64ARB(final int p0, final long p1);
    
    void glUniform2i64ARB(final int p0, final long p1, final long p2);
    
    void glUniform3i64ARB(final int p0, final long p1, final long p2, final long p3);
    
    void glUniform4i64ARB(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glUniform1i64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform1i64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform2i64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform2i64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform3i64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform3i64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform4i64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform4i64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform1ui64ARB(final int p0, final long p1);
    
    void glUniform2ui64ARB(final int p0, final long p1, final long p2);
    
    void glUniform3ui64ARB(final int p0, final long p1, final long p2, final long p3);
    
    void glUniform4ui64ARB(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glUniform1ui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform1ui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform2ui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform2ui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform3ui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform3ui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform4ui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform4ui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetUniformi64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glGetUniformi64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetUniformui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glGetUniformui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetnUniformi64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glGetnUniformi64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glGetnUniformui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glGetnUniformui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform1i64ARB(final int p0, final int p1, final long p2);
    
    void glProgramUniform2i64ARB(final int p0, final int p1, final long p2, final long p3);
    
    void glProgramUniform3i64ARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glProgramUniform4i64ARB(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    void glProgramUniform1i64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform1i64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform2i64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform2i64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform3i64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform3i64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform4i64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform4i64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform1ui64ARB(final int p0, final int p1, final long p2);
    
    void glProgramUniform2ui64ARB(final int p0, final int p1, final long p2, final long p3);
    
    void glProgramUniform3ui64ARB(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glProgramUniform4ui64ARB(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    void glProgramUniform1ui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform1ui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform2ui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform2ui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform3ui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform3ui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform4ui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform4ui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glColorTable(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glColorTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glColorTableParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glColorTableParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glColorTableParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glColorTableParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glCopyColorTable(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glGetColorTable(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glGetColorTable(final int p0, final int p1, final int p2, final long p3);
    
    void glGetColorTableParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetColorTableParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetColorTableParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetColorTableParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glColorSubTable(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glColorSubTable(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glCopyColorSubTable(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glConvolutionFilter1D(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glConvolutionFilter1D(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glConvolutionFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glConvolutionFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glConvolutionParameterf(final int p0, final int p1, final float p2);
    
    void glConvolutionParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glConvolutionParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glConvolutionParameteri(final int p0, final int p1, final int p2);
    
    void glConvolutionParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glConvolutionParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glCopyConvolutionFilter1D(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glCopyConvolutionFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glGetConvolutionFilter(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glGetConvolutionFilter(final int p0, final int p1, final int p2, final long p3);
    
    void glGetConvolutionParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetConvolutionParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetConvolutionParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetConvolutionParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetSeparableFilter(final int p0, final int p1, final int p2, final Buffer p3, final Buffer p4, final Buffer p5);
    
    void glGetSeparableFilter(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    void glSeparableFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6, final Buffer p7);
    
    void glSeparableFilter2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    void glGetHistogram(final int p0, final boolean p1, final int p2, final int p3, final Buffer p4);
    
    void glGetHistogram(final int p0, final boolean p1, final int p2, final int p3, final long p4);
    
    void glGetHistogramParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetHistogramParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetHistogramParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetHistogramParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetMinmax(final int p0, final boolean p1, final int p2, final int p3, final Buffer p4);
    
    void glGetMinmax(final int p0, final boolean p1, final int p2, final int p3, final long p4);
    
    void glGetMinmaxParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMinmaxParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetMinmaxParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetMinmaxParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glHistogram(final int p0, final int p1, final int p2, final boolean p3);
    
    void glMinmax(final int p0, final int p1, final boolean p2);
    
    void glResetHistogram(final int p0);
    
    void glResetMinmax(final int p0);
    
    void glCurrentPaletteMatrixARB(final int p0);
    
    void glMatrixIndexubvARB(final int p0, final ByteBuffer p1);
    
    void glMatrixIndexubvARB(final int p0, final byte[] p1, final int p2);
    
    void glMatrixIndexusvARB(final int p0, final ShortBuffer p1);
    
    void glMatrixIndexusvARB(final int p0, final short[] p1, final int p2);
    
    void glMatrixIndexuivARB(final int p0, final IntBuffer p1);
    
    void glMatrixIndexuivARB(final int p0, final int[] p1, final int p2);
    
    void glMatrixIndexPointerARB(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glMatrixIndexPointerARB(final int p0, final int p1, final int p2, final long p3);
    
    void glMaxShaderCompilerThreadsARB(final int p0);
    
    void glFramebufferSampleLocationsfvARB(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glFramebufferSampleLocationsfvARB(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glNamedFramebufferSampleLocationsfvARB(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glNamedFramebufferSampleLocationsfvARB(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glEvaluateDepthValuesARB();
    
    void glDeleteObjectARB(final long p0);
    
    long glGetHandleARB(final int p0);
    
    void glDetachObjectARB(final long p0, final long p1);
    
    long glCreateShaderObjectARB(final int p0);
    
    void glShaderSourceARB(final long p0, final int p1, final String[] p2, final IntBuffer p3);
    
    void glShaderSourceARB(final long p0, final int p1, final String[] p2, final int[] p3, final int p4);
    
    void glCompileShaderARB(final long p0);
    
    long glCreateProgramObjectARB();
    
    void glAttachObjectARB(final long p0, final long p1);
    
    void glLinkProgramARB(final long p0);
    
    void glUseProgramObjectARB(final long p0);
    
    void glValidateProgramARB(final long p0);
    
    void glUniform1fARB(final int p0, final float p1);
    
    void glUniform2fARB(final int p0, final float p1, final float p2);
    
    void glUniform3fARB(final int p0, final float p1, final float p2, final float p3);
    
    void glUniform4fARB(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glUniform1iARB(final int p0, final int p1);
    
    void glUniform2iARB(final int p0, final int p1, final int p2);
    
    void glUniform3iARB(final int p0, final int p1, final int p2, final int p3);
    
    void glUniform4iARB(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glUniform1fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform1fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform2fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform2fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform3fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform3fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform4fvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform4fvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform1ivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform1ivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform2ivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform2ivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform3ivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform3ivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform4ivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform4ivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniformMatrix2fvARB(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2fvARB(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3fvARB(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3fvARB(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4fvARB(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4fvARB(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glGetObjectParameterfvARB(final long p0, final int p1, final FloatBuffer p2);
    
    void glGetObjectParameterfvARB(final long p0, final int p1, final float[] p2, final int p3);
    
    void glGetObjectParameterivARB(final long p0, final int p1, final IntBuffer p2);
    
    void glGetObjectParameterivARB(final long p0, final int p1, final int[] p2, final int p3);
    
    void glGetInfoLogARB(final long p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetInfoLogARB(final long p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetAttachedObjectsARB(final long p0, final int p1, final IntBuffer p2, final LongBuffer p3);
    
    void glGetAttachedObjectsARB(final long p0, final int p1, final int[] p2, final int p3, final long[] p4, final int p5);
    
    int glGetUniformLocationARB(final long p0, final String p1);
    
    void glGetActiveUniformARB(final long p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final ByteBuffer p6);
    
    void glGetActiveUniformARB(final long p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6, final int[] p7, final int p8, final byte[] p9, final int p10);
    
    void glGetUniformfvARB(final long p0, final int p1, final FloatBuffer p2);
    
    void glGetUniformfvARB(final long p0, final int p1, final float[] p2, final int p3);
    
    void glGetUniformivARB(final long p0, final int p1, final IntBuffer p2);
    
    void glGetUniformivARB(final long p0, final int p1, final int[] p2, final int p3);
    
    void glGetShaderSourceARB(final long p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetShaderSourceARB(final long p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glWeightbvARB(final int p0, final ByteBuffer p1);
    
    void glWeightbvARB(final int p0, final byte[] p1, final int p2);
    
    void glWeightsvARB(final int p0, final ShortBuffer p1);
    
    void glWeightsvARB(final int p0, final short[] p1, final int p2);
    
    void glWeightivARB(final int p0, final IntBuffer p1);
    
    void glWeightivARB(final int p0, final int[] p1, final int p2);
    
    void glWeightfvARB(final int p0, final FloatBuffer p1);
    
    void glWeightfvARB(final int p0, final float[] p1, final int p2);
    
    void glWeightdvARB(final int p0, final DoubleBuffer p1);
    
    void glWeightdvARB(final int p0, final double[] p1, final int p2);
    
    void glWeightubvARB(final int p0, final ByteBuffer p1);
    
    void glWeightubvARB(final int p0, final byte[] p1, final int p2);
    
    void glWeightusvARB(final int p0, final ShortBuffer p1);
    
    void glWeightusvARB(final int p0, final short[] p1, final int p2);
    
    void glWeightuivARB(final int p0, final IntBuffer p1);
    
    void glWeightuivARB(final int p0, final int[] p1, final int p2);
    
    void glWeightPointerARB(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glWeightPointerARB(final int p0, final int p1, final int p2, final long p3);
    
    void glVertexBlendARB(final int p0);
    
    void glVertexAttrib1dARB(final int p0, final double p1);
    
    void glVertexAttrib1dvARB(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib1dvARB(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib1fARB(final int p0, final float p1);
    
    void glVertexAttrib1fvARB(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib1fvARB(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib1sARB(final int p0, final short p1);
    
    void glVertexAttrib1svARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib1svARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib2dARB(final int p0, final double p1, final double p2);
    
    void glVertexAttrib2dvARB(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib2dvARB(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib2fARB(final int p0, final float p1, final float p2);
    
    void glVertexAttrib2fvARB(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib2fvARB(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib2sARB(final int p0, final short p1, final short p2);
    
    void glVertexAttrib2svARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib2svARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib3dARB(final int p0, final double p1, final double p2, final double p3);
    
    void glVertexAttrib3dvARB(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib3dvARB(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib3fARB(final int p0, final float p1, final float p2, final float p3);
    
    void glVertexAttrib3fvARB(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib3fvARB(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib3sARB(final int p0, final short p1, final short p2, final short p3);
    
    void glVertexAttrib3svARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib3svARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4NbvARB(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4NbvARB(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4NivARB(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4NivARB(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4NsvARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4NsvARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4NubARB(final int p0, final byte p1, final byte p2, final byte p3, final byte p4);
    
    void glVertexAttrib4NubvARB(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4NubvARB(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4NuivARB(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4NuivARB(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4NusvARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4NusvARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4bvARB(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4bvARB(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4dARB(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glVertexAttrib4dvARB(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib4dvARB(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib4fARB(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glVertexAttrib4fvARB(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib4fvARB(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib4ivARB(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4ivARB(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4sARB(final int p0, final short p1, final short p2, final short p3, final short p4);
    
    void glVertexAttrib4svARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4svARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4ubvARB(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4ubvARB(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4uivARB(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4uivARB(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4usvARB(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4usvARB(final int p0, final short[] p1, final int p2);
    
    void glVertexAttribPointerARB(final int p0, final int p1, final int p2, final boolean p3, final int p4, final Buffer p5);
    
    void glVertexAttribPointerARB(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5);
    
    void glEnableVertexAttribArrayARB(final int p0);
    
    void glDisableVertexAttribArrayARB(final int p0);
    
    void glGetVertexAttribdvARB(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetVertexAttribdvARB(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetVertexAttribfvARB(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetVertexAttribfvARB(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetVertexAttribivARB(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribivARB(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBlendBarrier();
    
    void glMultiTexCoord1bOES(final int p0, final byte p1);
    
    void glMultiTexCoord1bvOES(final int p0, final ByteBuffer p1);
    
    void glMultiTexCoord1bvOES(final int p0, final byte[] p1, final int p2);
    
    void glMultiTexCoord2bOES(final int p0, final byte p1, final byte p2);
    
    void glMultiTexCoord2bvOES(final int p0, final ByteBuffer p1);
    
    void glMultiTexCoord2bvOES(final int p0, final byte[] p1, final int p2);
    
    void glMultiTexCoord3bOES(final int p0, final byte p1, final byte p2, final byte p3);
    
    void glMultiTexCoord3bvOES(final int p0, final ByteBuffer p1);
    
    void glMultiTexCoord3bvOES(final int p0, final byte[] p1, final int p2);
    
    void glMultiTexCoord4bOES(final int p0, final byte p1, final byte p2, final byte p3, final byte p4);
    
    void glMultiTexCoord4bvOES(final int p0, final ByteBuffer p1);
    
    void glMultiTexCoord4bvOES(final int p0, final byte[] p1, final int p2);
    
    void glTexCoord1bOES(final byte p0);
    
    void glTexCoord1bvOES(final ByteBuffer p0);
    
    void glTexCoord1bvOES(final byte[] p0, final int p1);
    
    void glTexCoord2bOES(final byte p0, final byte p1);
    
    void glTexCoord2bvOES(final ByteBuffer p0);
    
    void glTexCoord2bvOES(final byte[] p0, final int p1);
    
    void glTexCoord3bOES(final byte p0, final byte p1, final byte p2);
    
    void glTexCoord3bvOES(final ByteBuffer p0);
    
    void glTexCoord3bvOES(final byte[] p0, final int p1);
    
    void glTexCoord4bOES(final byte p0, final byte p1, final byte p2, final byte p3);
    
    void glTexCoord4bvOES(final ByteBuffer p0);
    
    void glTexCoord4bvOES(final byte[] p0, final int p1);
    
    void glVertex2bOES(final byte p0, final byte p1);
    
    void glVertex2bvOES(final ByteBuffer p0);
    
    void glVertex2bvOES(final byte[] p0, final int p1);
    
    void glVertex3bOES(final byte p0, final byte p1, final byte p2);
    
    void glVertex3bvOES(final ByteBuffer p0);
    
    void glVertex3bvOES(final byte[] p0, final int p1);
    
    void glVertex4bOES(final byte p0, final byte p1, final byte p2, final byte p3);
    
    void glVertex4bvOES(final ByteBuffer p0);
    
    void glVertex4bvOES(final byte[] p0, final int p1);
    
    int glQueryMatrixxOES(final IntBuffer p0, final IntBuffer p1);
    
    int glQueryMatrixxOES(final int[] p0, final int p1, final int[] p2, final int p3);
    
    void glClipPlanef(final int p0, final FloatBuffer p1);
    
    void glClipPlanef(final int p0, final float[] p1, final int p2);
    
    void glGetClipPlanef(final int p0, final FloatBuffer p1);
    
    void glGetClipPlanef(final int p0, final float[] p1, final int p2);
    
    void glBlendFuncIndexedAMD(final int p0, final int p1, final int p2);
    
    void glBlendFuncSeparateIndexedAMD(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glBlendEquationIndexedAMD(final int p0, final int p1);
    
    void glBlendEquationSeparateIndexedAMD(final int p0, final int p1, final int p2);
    
    void glUniform1i64NV(final int p0, final long p1);
    
    void glUniform2i64NV(final int p0, final long p1, final long p2);
    
    void glUniform3i64NV(final int p0, final long p1, final long p2, final long p3);
    
    void glUniform4i64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glUniform1i64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform1i64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform2i64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform2i64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform3i64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform3i64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform4i64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform4i64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform1ui64NV(final int p0, final long p1);
    
    void glUniform2ui64NV(final int p0, final long p1, final long p2);
    
    void glUniform3ui64NV(final int p0, final long p1, final long p2, final long p3);
    
    void glUniform4ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glUniform1ui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform1ui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform2ui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform2ui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform3ui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform3ui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glUniform4ui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniform4ui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetUniformi64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetUniformi64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glProgramUniform1i64NV(final int p0, final int p1, final long p2);
    
    void glProgramUniform2i64NV(final int p0, final int p1, final long p2, final long p3);
    
    void glProgramUniform3i64NV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glProgramUniform4i64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    void glProgramUniform1i64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform1i64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform2i64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform2i64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform3i64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform3i64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform4i64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform4i64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform1ui64NV(final int p0, final int p1, final long p2);
    
    void glProgramUniform2ui64NV(final int p0, final int p1, final long p2, final long p3);
    
    void glProgramUniform3ui64NV(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glProgramUniform4ui64NV(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    void glProgramUniform1ui64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform1ui64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform2ui64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform2ui64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform3ui64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform3ui64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glProgramUniform4ui64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniform4ui64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glVertexAttribParameteriAMD(final int p0, final int p1, final int p2);
    
    void glGenNamesAMD(final int p0, final int p1, final IntBuffer p2);
    
    void glGenNamesAMD(final int p0, final int p1, final int[] p2, final int p3);
    
    void glDeleteNamesAMD(final int p0, final int p1, final IntBuffer p2);
    
    void glDeleteNamesAMD(final int p0, final int p1, final int[] p2, final int p3);
    
    boolean glIsNameAMD(final int p0, final int p1);
    
    void glQueryObjectParameteruiAMD(final int p0, final int p1, final int p2, final int p3);
    
    void glGetPerfMonitorGroupsAMD(final IntBuffer p0, final int p1, final IntBuffer p2);
    
    void glGetPerfMonitorGroupsAMD(final int[] p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetPerfMonitorCountersAMD(final int p0, final IntBuffer p1, final IntBuffer p2, final int p3, final IntBuffer p4);
    
    void glGetPerfMonitorCountersAMD(final int p0, final int[] p1, final int p2, final int[] p3, final int p4, final int p5, final int[] p6, final int p7);
    
    void glGetPerfMonitorGroupStringAMD(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetPerfMonitorGroupStringAMD(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetPerfMonitorCounterStringAMD(final int p0, final int p1, final int p2, final IntBuffer p3, final ByteBuffer p4);
    
    void glGetPerfMonitorCounterStringAMD(final int p0, final int p1, final int p2, final int[] p3, final int p4, final byte[] p5, final int p6);
    
    void glGetPerfMonitorCounterInfoAMD(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glGenPerfMonitorsAMD(final int p0, final IntBuffer p1);
    
    void glGenPerfMonitorsAMD(final int p0, final int[] p1, final int p2);
    
    void glDeletePerfMonitorsAMD(final int p0, final IntBuffer p1);
    
    void glDeletePerfMonitorsAMD(final int p0, final int[] p1, final int p2);
    
    void glSelectPerfMonitorCountersAMD(final int p0, final boolean p1, final int p2, final int p3, final IntBuffer p4);
    
    void glSelectPerfMonitorCountersAMD(final int p0, final boolean p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glBeginPerfMonitorAMD(final int p0);
    
    void glEndPerfMonitorAMD(final int p0);
    
    void glGetPerfMonitorCounterDataAMD(final int p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4);
    
    void glGetPerfMonitorCounterDataAMD(final int p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6);
    
    void glTexStorageSparseAMD(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glTextureStorageSparseAMD(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glBufferParameteri(final int p0, final int p1, final int p2);
    
    int glObjectPurgeableAPPLE(final int p0, final int p1, final int p2);
    
    int glObjectUnpurgeableAPPLE(final int p0, final int p1, final int p2);
    
    void glGetObjectParameterivAPPLE(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetObjectParameterivAPPLE(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glTextureRangeAPPLE(final int p0, final int p1, final Buffer p2);
    
    void glVertexArrayRangeAPPLE(final int p0, final Buffer p1);
    
    void glFlushVertexArrayRangeAPPLE(final int p0, final Buffer p1);
    
    void glVertexArrayParameteriAPPLE(final int p0, final int p1);
    
    void glEnableVertexAttribAPPLE(final int p0, final int p1);
    
    void glDisableVertexAttribAPPLE(final int p0, final int p1);
    
    boolean glIsVertexAttribEnabledAPPLE(final int p0, final int p1);
    
    void glMapVertexAttrib1dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final DoubleBuffer p6);
    
    void glMapVertexAttrib1dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final double[] p6, final int p7);
    
    void glMapVertexAttrib1fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final FloatBuffer p6);
    
    void glMapVertexAttrib1fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final float[] p6, final int p7);
    
    void glMapVertexAttrib2dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final double p6, final double p7, final int p8, final int p9, final DoubleBuffer p10);
    
    void glMapVertexAttrib2dAPPLE(final int p0, final int p1, final double p2, final double p3, final int p4, final int p5, final double p6, final double p7, final int p8, final int p9, final double[] p10, final int p11);
    
    void glMapVertexAttrib2fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final float p6, final float p7, final int p8, final int p9, final FloatBuffer p10);
    
    void glMapVertexAttrib2fAPPLE(final int p0, final int p1, final float p2, final float p3, final int p4, final int p5, final float p6, final float p7, final int p8, final int p9, final float[] p10, final int p11);
    
    void glDrawBuffersATI(final int p0, final IntBuffer p1);
    
    void glDrawBuffersATI(final int p0, final int[] p1, final int p2);
    
    void glPNTrianglesiATI(final int p0, final int p1);
    
    void glPNTrianglesfATI(final int p0, final float p1);
    
    void glUniformBufferEXT(final int p0, final int p1, final int p2);
    
    int glGetUniformBufferSizeEXT(final int p0, final int p1);
    
    long glGetUniformOffsetEXT(final int p0, final int p1);
    
    void glLockArraysEXT(final int p0, final int p1);
    
    void glUnlockArraysEXT();
    
    void glCullParameterdvEXT(final int p0, final DoubleBuffer p1);
    
    void glCullParameterdvEXT(final int p0, final double[] p1, final int p2);
    
    void glCullParameterfvEXT(final int p0, final FloatBuffer p1);
    
    void glCullParameterfvEXT(final int p0, final float[] p1, final int p2);
    
    void glDepthBoundsEXT(final double p0, final double p1);
    
    void glMatrixLoadfEXT(final int p0, final FloatBuffer p1);
    
    void glMatrixLoadfEXT(final int p0, final float[] p1, final int p2);
    
    void glMatrixLoaddEXT(final int p0, final DoubleBuffer p1);
    
    void glMatrixLoaddEXT(final int p0, final double[] p1, final int p2);
    
    void glMatrixMultfEXT(final int p0, final FloatBuffer p1);
    
    void glMatrixMultfEXT(final int p0, final float[] p1, final int p2);
    
    void glMatrixMultdEXT(final int p0, final DoubleBuffer p1);
    
    void glMatrixMultdEXT(final int p0, final double[] p1, final int p2);
    
    void glMatrixLoadIdentityEXT(final int p0);
    
    void glMatrixRotatefEXT(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glMatrixRotatedEXT(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glMatrixScalefEXT(final int p0, final float p1, final float p2, final float p3);
    
    void glMatrixScaledEXT(final int p0, final double p1, final double p2, final double p3);
    
    void glMatrixTranslatefEXT(final int p0, final float p1, final float p2, final float p3);
    
    void glMatrixTranslatedEXT(final int p0, final double p1, final double p2, final double p3);
    
    void glMatrixFrustumEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6);
    
    void glMatrixOrthoEXT(final int p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6);
    
    void glMatrixPopEXT(final int p0);
    
    void glMatrixPushEXT(final int p0);
    
    void glClientAttribDefaultEXT(final int p0);
    
    void glPushClientAttribDefaultEXT(final int p0);
    
    void glTextureParameterfEXT(final int p0, final int p1, final int p2, final float p3);
    
    void glTextureParameterfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glTextureParameterfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glTextureParameteriEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glTextureParameterivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glTextureParameterivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    void glTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    void glTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    void glCopyTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glCopyTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glCopyTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glCopyTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glGetTextureImageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glGetTextureParameterfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetTextureParameterfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetTextureParameterivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTextureParameterivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetTextureLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final FloatBuffer p4);
    
    void glGetTextureLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final float[] p4, final int p5);
    
    void glGetTextureLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glGetTextureLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glTextureImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glTextureImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    void glTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final Buffer p11);
    
    void glTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11);
    
    void glCopyTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9);
    
    void glBindMultiTextureEXT(final int p0, final int p1, final int p2);
    
    void glMultiTexCoordPointerEXT(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glMultiTexEnvfEXT(final int p0, final int p1, final int p2, final float p3);
    
    void glMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glMultiTexEnviEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiTexEnvivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glMultiTexEnvivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glMultiTexGendEXT(final int p0, final int p1, final int p2, final double p3);
    
    void glMultiTexGendvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glMultiTexGendvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glMultiTexGenfEXT(final int p0, final int p1, final int p2, final float p3);
    
    void glMultiTexGenfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glMultiTexGenfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glMultiTexGeniEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiTexGenivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glMultiTexGenivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetMultiTexEnvfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetMultiTexEnvivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMultiTexEnvivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMultiTexGendvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glGetMultiTexGendvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glGetMultiTexGenfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetMultiTexGenfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetMultiTexGenivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMultiTexGenivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glMultiTexParameteriEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiTexParameterivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glMultiTexParameterivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glMultiTexParameterfEXT(final int p0, final int p1, final int p2, final float p3);
    
    void glMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glCopyMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glCopyMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glCopyMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glCopyMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glGetMultiTexImageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glGetMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetMultiTexParameterfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetMultiTexParameterivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMultiTexParameterivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMultiTexLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final FloatBuffer p4);
    
    void glGetMultiTexLevelParameterfvEXT(final int p0, final int p1, final int p2, final int p3, final float[] p4, final int p5);
    
    void glGetMultiTexLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glGetMultiTexLevelParameterivEXT(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glMultiTexImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final Buffer p11);
    
    void glCopyMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9);
    
    void glEnableClientStateIndexedEXT(final int p0, final int p1);
    
    void glDisableClientStateIndexedEXT(final int p0, final int p1);
    
    void glGetFloatIndexedvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetFloatIndexedvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetDoubleIndexedvEXT(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetDoubleIndexedvEXT(final int p0, final int p1, final double[] p2, final int p3);
    
    void glEnableIndexed(final int p0, final int p1);
    
    void glDisableIndexed(final int p0, final int p1);
    
    boolean glIsEnabledIndexed(final int p0, final int p1);
    
    void glGetIntegerIndexedv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetIntegerIndexedv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetBooleanIndexedv(final int p0, final int p1, final ByteBuffer p2);
    
    void glGetBooleanIndexedv(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glCompressedTextureImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glCompressedTextureImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glCompressedTextureImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glCompressedTextureSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final Buffer p11);
    
    void glCompressedTextureSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glCompressedTextureSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glGetCompressedTextureImageEXT(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glCompressedMultiTexImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glCompressedMultiTexImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glCompressedMultiTexImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glCompressedMultiTexSubImage3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final Buffer p11);
    
    void glCompressedMultiTexSubImage2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glCompressedMultiTexSubImage1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glGetCompressedMultiTexImageEXT(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glMatrixLoadTransposefEXT(final int p0, final FloatBuffer p1);
    
    void glMatrixLoadTransposefEXT(final int p0, final float[] p1, final int p2);
    
    void glMatrixLoadTransposedEXT(final int p0, final DoubleBuffer p1);
    
    void glMatrixLoadTransposedEXT(final int p0, final double[] p1, final int p2);
    
    void glMatrixMultTransposefEXT(final int p0, final FloatBuffer p1);
    
    void glMatrixMultTransposefEXT(final int p0, final float[] p1, final int p2);
    
    void glMatrixMultTransposedEXT(final int p0, final DoubleBuffer p1);
    
    void glMatrixMultTransposedEXT(final int p0, final double[] p1, final int p2);
    
    void glNamedBufferDataEXT(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glNamedBufferSubDataEXT(final int p0, final long p1, final long p2, final Buffer p3);
    
    ByteBuffer glMapNamedBufferEXT(final int p0, final int p1);
    
    boolean glUnmapNamedBufferEXT(final int p0);
    
    void glGetNamedBufferParameterivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedBufferParameterivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetNamedBufferSubDataEXT(final int p0, final long p1, final long p2, final Buffer p3);
    
    void glTextureBufferEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glMultiTexBufferEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glTextureParameterIivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glTextureParameterIivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glTextureParameterIuivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glTextureParameterIuivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetTextureParameterIivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTextureParameterIivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetTextureParameterIuivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTextureParameterIuivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMultiTexParameterIivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMultiTexParameterIuivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glNamedProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final int p3, final FloatBuffer p4);
    
    void glNamedProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final int p3, final float[] p4, final int p5);
    
    void glNamedProgramLocalParameterI4iEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glNamedProgramLocalParameterI4ivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glNamedProgramLocalParameterI4ivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glNamedProgramLocalParametersI4ivEXT(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glNamedProgramLocalParametersI4ivEXT(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glNamedProgramLocalParameterI4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glNamedProgramLocalParameterI4uivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glNamedProgramLocalParameterI4uivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glNamedProgramLocalParametersI4uivEXT(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glNamedProgramLocalParametersI4uivEXT(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glGetNamedProgramLocalParameterIivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetNamedProgramLocalParameterIivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetNamedProgramLocalParameterIuivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetNamedProgramLocalParameterIuivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glEnableClientStateiEXT(final int p0, final int p1);
    
    void glDisableClientStateiEXT(final int p0, final int p1);
    
    void glGetFloati_vEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetFloati_vEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetDoublei_vEXT(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetDoublei_vEXT(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetPointeri_vEXT(final int p0, final int p1, final PointerBuffer p2);
    
    void glNamedProgramStringEXT(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glNamedProgramLocalParameter4dEXT(final int p0, final int p1, final int p2, final double p3, final double p4, final double p5, final double p6);
    
    void glNamedProgramLocalParameter4dvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glNamedProgramLocalParameter4dvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glNamedProgramLocalParameter4fEXT(final int p0, final int p1, final int p2, final float p3, final float p4, final float p5, final float p6);
    
    void glNamedProgramLocalParameter4fvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glNamedProgramLocalParameter4fvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetNamedProgramLocalParameterdvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glGetNamedProgramLocalParameterdvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glGetNamedProgramLocalParameterfvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetNamedProgramLocalParameterfvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetNamedProgramivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetNamedProgramivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetNamedProgramStringEXT(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glNamedRenderbufferStorageEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glGetNamedRenderbufferParameterivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedRenderbufferParameterivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glNamedRenderbufferStorageMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNamedRenderbufferStorageMultisampleCoverageEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    int glCheckNamedFramebufferStatusEXT(final int p0, final int p1);
    
    void glNamedFramebufferTexture1DEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNamedFramebufferTexture2DEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNamedFramebufferTexture3DEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glNamedFramebufferRenderbufferEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glGetNamedFramebufferAttachmentParameterivEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetNamedFramebufferAttachmentParameterivEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGenerateTextureMipmapEXT(final int p0, final int p1);
    
    void glGenerateMultiTexMipmapEXT(final int p0, final int p1);
    
    void glFramebufferDrawBufferEXT(final int p0, final int p1);
    
    void glFramebufferDrawBuffersEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glFramebufferDrawBuffersEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glFramebufferReadBufferEXT(final int p0, final int p1);
    
    void glGetFramebufferParameterivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetFramebufferParameterivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glNamedCopyBufferSubDataEXT(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glNamedFramebufferTextureEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glNamedFramebufferTextureLayerEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNamedFramebufferTextureFaceEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glTextureRenderbufferEXT(final int p0, final int p1, final int p2);
    
    void glMultiTexRenderbufferEXT(final int p0, final int p1, final int p2);
    
    void glVertexArrayVertexOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glVertexArrayColorOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glVertexArrayEdgeFlagOffsetEXT(final int p0, final int p1, final int p2, final long p3);
    
    void glVertexArrayIndexOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glVertexArrayNormalOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glVertexArrayTexCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glVertexArrayMultiTexCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glVertexArrayFogCoordOffsetEXT(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glVertexArraySecondaryColorOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    void glVertexArrayVertexAttribOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final int p6, final long p7);
    
    void glVertexArrayVertexAttribIOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glEnableVertexArrayEXT(final int p0, final int p1);
    
    void glDisableVertexArrayEXT(final int p0, final int p1);
    
    void glEnableVertexArrayAttribEXT(final int p0, final int p1);
    
    void glDisableVertexArrayAttribEXT(final int p0, final int p1);
    
    void glGetVertexArrayIntegervEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexArrayIntegervEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVertexArrayPointervEXT(final int p0, final int p1, final PointerBuffer p2);
    
    void glGetVertexArrayIntegeri_vEXT(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetVertexArrayIntegeri_vEXT(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetVertexArrayPointeri_vEXT(final int p0, final int p1, final int p2, final PointerBuffer p3);
    
    ByteBuffer glMapNamedBufferRangeEXT(final int p0, final long p1, final long p2, final int p3);
    
    void glFlushMappedNamedBufferRangeEXT(final int p0, final long p1, final long p2);
    
    void glNamedBufferStorageEXT(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glProgramUniform1dEXT(final int p0, final int p1, final double p2);
    
    void glProgramUniform2dEXT(final int p0, final int p1, final double p2, final double p3);
    
    void glProgramUniform3dEXT(final int p0, final int p1, final double p2, final double p3, final double p4);
    
    void glProgramUniform4dEXT(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5);
    
    void glProgramUniform1dvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform1dvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform2dvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform2dvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform3dvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform3dvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform4dvEXT(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform4dvEXT(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniformMatrix2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix2x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix2x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3x4dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4x2dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4x3dvEXT(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glTextureBufferRangeEXT(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    void glTextureStorage2DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glTextureStorage3DMultisampleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
    
    void glVertexArrayBindVertexBufferEXT(final int p0, final int p1, final int p2, final long p3, final int p4);
    
    void glVertexArrayVertexAttribFormatEXT(final int p0, final int p1, final int p2, final int p3, final boolean p4, final int p5);
    
    void glVertexArrayVertexAttribIFormatEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexArrayVertexAttribLFormatEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexArrayVertexAttribBindingEXT(final int p0, final int p1, final int p2);
    
    void glVertexArrayVertexBindingDivisorEXT(final int p0, final int p1, final int p2);
    
    void glVertexArrayVertexAttribLOffsetEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glTexturePageCommitmentEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8);
    
    void glVertexArrayVertexAttribDivisorEXT(final int p0, final int p1, final int p2);
    
    void glColorMaskIndexed(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4);
    
    void glProgramEnvParameters4fvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramEnvParameters4fvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramLocalParameters4fvEXT(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glIndexFuncEXT(final int p0, final float p1);
    
    void glIndexMaterialEXT(final int p0, final int p1);
    
    void glApplyTextureEXT(final int p0);
    
    void glTextureLightEXT(final int p0);
    
    void glTextureMaterialEXT(final int p0, final int p1);
    
    void glPixelTransformParameteriEXT(final int p0, final int p1, final int p2);
    
    void glPixelTransformParameterfEXT(final int p0, final int p1, final float p2);
    
    void glPixelTransformParameterivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glPixelTransformParameterivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glPixelTransformParameterfvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glPixelTransformParameterfvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetPixelTransformParameterivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetPixelTransformParameterivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetPixelTransformParameterfvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetPixelTransformParameterfvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glPolygonOffsetClampEXT(final float p0, final float p1, final float p2);
    
    void glProvokingVertexEXT(final int p0);
    
    void glRasterSamplesEXT(final int p0, final boolean p1);
    
    void glStencilClearTagEXT(final int p0, final int p1);
    
    void glActiveStencilFaceEXT(final int p0);
    
    void glClearColorIi(final int p0, final int p1, final int p2, final int p3);
    
    void glClearColorIui(final int p0, final int p1, final int p2, final int p3);
    
    void glTextureNormalEXT(final int p0);
    
    void glGetQueryObjecti64vEXT(final int p0, final int p1, final LongBuffer p2);
    
    void glGetQueryObjecti64vEXT(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetQueryObjectui64vEXT(final int p0, final int p1, final LongBuffer p2);
    
    void glGetQueryObjectui64vEXT(final int p0, final int p1, final long[] p2, final int p3);
    
    void glBeginVertexShaderEXT();
    
    void glEndVertexShaderEXT();
    
    void glBindVertexShaderEXT(final int p0);
    
    int glGenVertexShadersEXT(final int p0);
    
    void glDeleteVertexShaderEXT(final int p0);
    
    void glShaderOp1EXT(final int p0, final int p1, final int p2);
    
    void glShaderOp2EXT(final int p0, final int p1, final int p2, final int p3);
    
    void glShaderOp3EXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glSwizzleEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glWriteMaskEXT(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glInsertComponentEXT(final int p0, final int p1, final int p2);
    
    void glExtractComponentEXT(final int p0, final int p1, final int p2);
    
    int glGenSymbolsEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glSetInvariantEXT(final int p0, final int p1, final Buffer p2);
    
    void glSetLocalConstantEXT(final int p0, final int p1, final Buffer p2);
    
    void glVariantbvEXT(final int p0, final ByteBuffer p1);
    
    void glVariantbvEXT(final int p0, final byte[] p1, final int p2);
    
    void glVariantsvEXT(final int p0, final ShortBuffer p1);
    
    void glVariantsvEXT(final int p0, final short[] p1, final int p2);
    
    void glVariantivEXT(final int p0, final IntBuffer p1);
    
    void glVariantivEXT(final int p0, final int[] p1, final int p2);
    
    void glVariantfvEXT(final int p0, final FloatBuffer p1);
    
    void glVariantfvEXT(final int p0, final float[] p1, final int p2);
    
    void glVariantdvEXT(final int p0, final DoubleBuffer p1);
    
    void glVariantdvEXT(final int p0, final double[] p1, final int p2);
    
    void glVariantubvEXT(final int p0, final ByteBuffer p1);
    
    void glVariantubvEXT(final int p0, final byte[] p1, final int p2);
    
    void glVariantusvEXT(final int p0, final ShortBuffer p1);
    
    void glVariantusvEXT(final int p0, final short[] p1, final int p2);
    
    void glVariantuivEXT(final int p0, final IntBuffer p1);
    
    void glVariantuivEXT(final int p0, final int[] p1, final int p2);
    
    void glVariantPointerEXT(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glVariantPointerEXT(final int p0, final int p1, final int p2, final long p3);
    
    void glEnableVariantClientStateEXT(final int p0);
    
    void glDisableVariantClientStateEXT(final int p0);
    
    int glBindLightParameterEXT(final int p0, final int p1);
    
    int glBindMaterialParameterEXT(final int p0, final int p1);
    
    int glBindTexGenParameterEXT(final int p0, final int p1, final int p2);
    
    int glBindTextureUnitParameterEXT(final int p0, final int p1);
    
    int glBindParameterEXT(final int p0);
    
    boolean glIsVariantEnabledEXT(final int p0, final int p1);
    
    void glGetVariantBooleanvEXT(final int p0, final int p1, final ByteBuffer p2);
    
    void glGetVariantBooleanvEXT(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glGetVariantIntegervEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVariantIntegervEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVariantFloatvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetVariantFloatvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetInvariantBooleanvEXT(final int p0, final int p1, final ByteBuffer p2);
    
    void glGetInvariantBooleanvEXT(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glGetInvariantIntegervEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetInvariantIntegervEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetInvariantFloatvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetInvariantFloatvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetLocalConstantBooleanvEXT(final int p0, final int p1, final ByteBuffer p2);
    
    void glGetLocalConstantBooleanvEXT(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glGetLocalConstantIntegervEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetLocalConstantIntegervEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetLocalConstantFloatvEXT(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetLocalConstantFloatvEXT(final int p0, final int p1, final float[] p2, final int p3);
    
    void glVertexWeightfEXT(final float p0);
    
    void glVertexWeightfvEXT(final FloatBuffer p0);
    
    void glVertexWeightfvEXT(final float[] p0, final int p1);
    
    void glVertexWeightPointerEXT(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glVertexWeightPointerEXT(final int p0, final int p1, final int p2, final long p3);
    
    void glFrameTerminatorGREMEDY();
    
    void glStringMarkerGREMEDY(final int p0, final Buffer p1);
    
    void glBlendFuncSeparateINGR(final int p0, final int p1, final int p2, final int p3);
    
    void glSyncTextureINTEL(final int p0);
    
    void glUnmapTexture2DINTEL(final int p0, final int p1);
    
    ByteBuffer glMapTexture2DINTEL(final int p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4);
    
    ByteBuffer glMapTexture2DINTEL(final int p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6);
    
    void glBeginPerfQueryINTEL(final int p0);
    
    void glCreatePerfQueryINTEL(final int p0, final IntBuffer p1);
    
    void glCreatePerfQueryINTEL(final int p0, final int[] p1, final int p2);
    
    void glDeletePerfQueryINTEL(final int p0);
    
    void glEndPerfQueryINTEL(final int p0);
    
    void glGetFirstPerfQueryIdINTEL(final IntBuffer p0);
    
    void glGetFirstPerfQueryIdINTEL(final int[] p0, final int p1);
    
    void glGetNextPerfQueryIdINTEL(final int p0, final IntBuffer p1);
    
    void glGetNextPerfQueryIdINTEL(final int p0, final int[] p1, final int p2);
    
    void glGetPerfCounterInfoINTEL(final int p0, final int p1, final int p2, final ByteBuffer p3, final int p4, final ByteBuffer p5, final IntBuffer p6, final IntBuffer p7, final IntBuffer p8, final IntBuffer p9, final LongBuffer p10);
    
    void glGetPerfCounterInfoINTEL(final int p0, final int p1, final int p2, final byte[] p3, final int p4, final int p5, final byte[] p6, final int p7, final int[] p8, final int p9, final int[] p10, final int p11, final int[] p12, final int p13, final int[] p14, final int p15, final long[] p16, final int p17);
    
    void glGetPerfQueryDataINTEL(final int p0, final int p1, final int p2, final Buffer p3, final IntBuffer p4);
    
    void glGetPerfQueryDataINTEL(final int p0, final int p1, final int p2, final Buffer p3, final int[] p4, final int p5);
    
    void glGetPerfQueryIdByNameINTEL(final ByteBuffer p0, final IntBuffer p1);
    
    void glGetPerfQueryIdByNameINTEL(final byte[] p0, final int p1, final int[] p2, final int p3);
    
    void glGetPerfQueryInfoINTEL(final int p0, final int p1, final ByteBuffer p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final IntBuffer p6);
    
    void glGetPerfQueryInfoINTEL(final int p0, final int p1, final byte[] p2, final int p3, final int[] p4, final int p5, final int[] p6, final int p7, final int[] p8, final int p9, final int[] p10, final int p11);
    
    void glBeginConditionalRenderNVX(final int p0);
    
    void glEndConditionalRenderNVX();
    
    void glMultiDrawArraysIndirectBindlessNV(final int p0, final Buffer p1, final int p2, final int p3, final int p4);
    
    void glMultiDrawElementsIndirectBindlessNV(final int p0, final int p1, final Buffer p2, final int p3, final int p4, final int p5);
    
    void glMultiDrawArraysIndirectBindlessCountNV(final int p0, final Buffer p1, final int p2, final int p3, final int p4, final int p5);
    
    void glMultiDrawElementsIndirectBindlessCountNV(final int p0, final int p1, final Buffer p2, final int p3, final int p4, final int p5, final int p6);
    
    void glCreateStatesNV(final int p0, final IntBuffer p1);
    
    void glCreateStatesNV(final int p0, final int[] p1, final int p2);
    
    void glDeleteStatesNV(final int p0, final IntBuffer p1);
    
    void glDeleteStatesNV(final int p0, final int[] p1, final int p2);
    
    boolean glIsStateNV(final int p0);
    
    void glStateCaptureNV(final int p0, final int p1);
    
    int glGetCommandHeaderNV(final int p0, final int p1);
    
    short glGetStageIndexNV(final int p0);
    
    void glDrawCommandsNV(final int p0, final int p1, final PointerBuffer p2, final IntBuffer p3, final int p4);
    
    void glDrawCommandsNV(final int p0, final int p1, final PointerBuffer p2, final int[] p3, final int p4, final int p5);
    
    void glDrawCommandsAddressNV(final int p0, final LongBuffer p1, final IntBuffer p2, final int p3);
    
    void glDrawCommandsAddressNV(final int p0, final long[] p1, final int p2, final int[] p3, final int p4, final int p5);
    
    void glDrawCommandsStatesNV(final int p0, final PointerBuffer p1, final IntBuffer p2, final IntBuffer p3, final IntBuffer p4, final int p5);
    
    void glDrawCommandsStatesNV(final int p0, final PointerBuffer p1, final int[] p2, final int p3, final int[] p4, final int p5, final int[] p6, final int p7, final int p8);
    
    void glDrawCommandsStatesAddressNV(final LongBuffer p0, final IntBuffer p1, final IntBuffer p2, final IntBuffer p3, final int p4);
    
    void glDrawCommandsStatesAddressNV(final long[] p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5, final int[] p6, final int p7, final int p8);
    
    void glCreateCommandListsNV(final int p0, final IntBuffer p1);
    
    void glCreateCommandListsNV(final int p0, final int[] p1, final int p2);
    
    void glDeleteCommandListsNV(final int p0, final IntBuffer p1);
    
    void glDeleteCommandListsNV(final int p0, final int[] p1, final int p2);
    
    boolean glIsCommandListNV(final int p0);
    
    void glListDrawCommandsStatesClientNV(final int p0, final int p1, final PointerBuffer p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final int p6);
    
    void glListDrawCommandsStatesClientNV(final int p0, final int p1, final PointerBuffer p2, final int[] p3, final int p4, final int[] p5, final int p6, final int[] p7, final int p8, final int p9);
    
    void glCommandListSegmentsNV(final int p0, final int p1);
    
    void glCompileCommandListNV(final int p0);
    
    void glCallCommandListNV(final int p0);
    
    void glSubpixelPrecisionBiasNV(final int p0, final int p1);
    
    void glConservativeRasterParameterfNV(final int p0, final float p1);
    
    void glCopyImageSubDataNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14);
    
    void glDrawTextureNV(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10);
    
    void glMapControlPointsNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7, final Buffer p8);
    
    void glMapParameterivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glMapParameterivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glMapParameterfvNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glMapParameterfvNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetMapControlPointsNV(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final Buffer p6);
    
    void glGetMapParameterivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetMapParameterivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetMapParameterfvNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMapParameterfvNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetMapAttribParameterivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetMapAttribParameterivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetMapAttribParameterfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetMapAttribParameterfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glEvalMapsNV(final int p0, final int p1);
    
    void glGetMultisamplefvNV(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMultisamplefvNV(final int p0, final int p1, final float[] p2, final int p3);
    
    void glSampleMaskIndexedNV(final int p0, final int p1);
    
    void glTexRenderbufferNV(final int p0, final int p1);
    
    void glFragmentCoverageColorNV(final int p0);
    
    void glCoverageModulationTableNV(final int p0, final FloatBuffer p1);
    
    void glCoverageModulationTableNV(final int p0, final float[] p1, final int p2);
    
    void glGetCoverageModulationTableNV(final int p0, final FloatBuffer p1);
    
    void glGetCoverageModulationTableNV(final int p0, final float[] p1, final int p2);
    
    void glCoverageModulationNV(final int p0);
    
    void glRenderbufferStorageMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramVertexLimitNV(final int p0, final int p1);
    
    void glFramebufferTextureFaceEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glProgramLocalParameterI4iNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramLocalParameterI4ivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glProgramLocalParameterI4ivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramLocalParametersI4ivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramLocalParametersI4ivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramLocalParameterI4uiNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramLocalParameterI4uivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glProgramLocalParameterI4uivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramLocalParametersI4uivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramLocalParametersI4uivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramEnvParameterI4iNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramEnvParameterI4ivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glProgramEnvParameterI4ivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramEnvParametersI4ivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramEnvParametersI4ivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramEnvParameterI4uiNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramEnvParameterI4uivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glProgramEnvParameterI4uivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramEnvParametersI4uivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramEnvParametersI4uivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetProgramLocalParameterIivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramLocalParameterIivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramLocalParameterIuivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramLocalParameterIuivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramEnvParameterIivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramEnvParameterIivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramEnvParameterIuivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramEnvParameterIuivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramSubroutineParametersuivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glProgramSubroutineParametersuivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramSubroutineParameteruivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramSubroutineParameteruivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glVertex2h(final short p0, final short p1);
    
    void glVertex2hv(final ShortBuffer p0);
    
    void glVertex2hv(final short[] p0, final int p1);
    
    void glVertex3h(final short p0, final short p1, final short p2);
    
    void glVertex3hv(final ShortBuffer p0);
    
    void glVertex3hv(final short[] p0, final int p1);
    
    void glVertex4h(final short p0, final short p1, final short p2, final short p3);
    
    void glVertex4hv(final ShortBuffer p0);
    
    void glVertex4hv(final short[] p0, final int p1);
    
    void glNormal3h(final short p0, final short p1, final short p2);
    
    void glNormal3hv(final ShortBuffer p0);
    
    void glNormal3hv(final short[] p0, final int p1);
    
    void glColor3h(final short p0, final short p1, final short p2);
    
    void glColor3hv(final ShortBuffer p0);
    
    void glColor3hv(final short[] p0, final int p1);
    
    void glColor4h(final short p0, final short p1, final short p2, final short p3);
    
    void glColor4hv(final ShortBuffer p0);
    
    void glColor4hv(final short[] p0, final int p1);
    
    void glTexCoord1h(final short p0);
    
    void glTexCoord1hv(final ShortBuffer p0);
    
    void glTexCoord1hv(final short[] p0, final int p1);
    
    void glTexCoord2h(final short p0, final short p1);
    
    void glTexCoord2hv(final ShortBuffer p0);
    
    void glTexCoord2hv(final short[] p0, final int p1);
    
    void glTexCoord3h(final short p0, final short p1, final short p2);
    
    void glTexCoord3hv(final ShortBuffer p0);
    
    void glTexCoord3hv(final short[] p0, final int p1);
    
    void glTexCoord4h(final short p0, final short p1, final short p2, final short p3);
    
    void glTexCoord4hv(final ShortBuffer p0);
    
    void glTexCoord4hv(final short[] p0, final int p1);
    
    void glMultiTexCoord1h(final int p0, final short p1);
    
    void glMultiTexCoord1hv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord1hv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord2h(final int p0, final short p1, final short p2);
    
    void glMultiTexCoord2hv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord2hv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord3h(final int p0, final short p1, final short p2, final short p3);
    
    void glMultiTexCoord3hv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord3hv(final int p0, final short[] p1, final int p2);
    
    void glMultiTexCoord4h(final int p0, final short p1, final short p2, final short p3, final short p4);
    
    void glMultiTexCoord4hv(final int p0, final ShortBuffer p1);
    
    void glMultiTexCoord4hv(final int p0, final short[] p1, final int p2);
    
    void glFogCoordh(final short p0);
    
    void glFogCoordhv(final ShortBuffer p0);
    
    void glFogCoordhv(final short[] p0, final int p1);
    
    void glSecondaryColor3h(final short p0, final short p1, final short p2);
    
    void glSecondaryColor3hv(final ShortBuffer p0);
    
    void glSecondaryColor3hv(final short[] p0, final int p1);
    
    void glVertexWeighth(final short p0);
    
    void glVertexWeighthv(final ShortBuffer p0);
    
    void glVertexWeighthv(final short[] p0, final int p1);
    
    void glVertexAttrib1h(final int p0, final short p1);
    
    void glVertexAttrib1hv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib1hv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib2h(final int p0, final short p1, final short p2);
    
    void glVertexAttrib2hv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib2hv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib3h(final int p0, final short p1, final short p2, final short p3);
    
    void glVertexAttrib3hv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib3hv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4h(final int p0, final short p1, final short p2, final short p3, final short p4);
    
    void glVertexAttrib4hv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4hv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttribs1hv(final int p0, final int p1, final ShortBuffer p2);
    
    void glVertexAttribs1hv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glVertexAttribs2hv(final int p0, final int p1, final ShortBuffer p2);
    
    void glVertexAttribs2hv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glVertexAttribs3hv(final int p0, final int p1, final ShortBuffer p2);
    
    void glVertexAttribs3hv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glVertexAttribs4hv(final int p0, final int p1, final ShortBuffer p2);
    
    void glVertexAttribs4hv(final int p0, final int p1, final short[] p2, final int p3);
    
    void glGenOcclusionQueriesNV(final int p0, final IntBuffer p1);
    
    void glGenOcclusionQueriesNV(final int p0, final int[] p1, final int p2);
    
    void glDeleteOcclusionQueriesNV(final int p0, final IntBuffer p1);
    
    void glDeleteOcclusionQueriesNV(final int p0, final int[] p1, final int p2);
    
    boolean glIsOcclusionQueryNV(final int p0);
    
    void glBeginOcclusionQueryNV(final int p0);
    
    void glEndOcclusionQueryNV();
    
    void glGetOcclusionQueryivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetOcclusionQueryivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetOcclusionQueryuivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetOcclusionQueryuivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glProgramBufferParametersfvNV(final int p0, final int p1, final int p2, final int p3, final FloatBuffer p4);
    
    void glProgramBufferParametersfvNV(final int p0, final int p1, final int p2, final int p3, final float[] p4, final int p5);
    
    void glProgramBufferParametersIivNV(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glProgramBufferParametersIivNV(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glProgramBufferParametersIuivNV(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    void glProgramBufferParametersIuivNV(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5);
    
    void glPixelDataRangeNV(final int p0, final int p1, final Buffer p2);
    
    void glFlushPixelDataRangeNV(final int p0);
    
    void glPrimitiveRestartNV();
    
    void glPrimitiveRestartIndexNV(final int p0);
    
    void glFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glNamedFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glNamedFramebufferSampleLocationsfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glResolveDepthValuesNV();
    
    void glTextureBarrierNV();
    
    void glBindTransformFeedbackNV(final int p0, final int p1);
    
    void glDeleteTransformFeedbacksNV(final int p0, final IntBuffer p1);
    
    void glDeleteTransformFeedbacksNV(final int p0, final int[] p1, final int p2);
    
    void glGenTransformFeedbacksNV(final int p0, final IntBuffer p1);
    
    void glGenTransformFeedbacksNV(final int p0, final int[] p1, final int p2);
    
    boolean glIsTransformFeedbackNV(final int p0);
    
    void glPauseTransformFeedbackNV();
    
    void glResumeTransformFeedbackNV();
    
    void glDrawTransformFeedbackNV(final int p0, final int p1);
    
    void glVDPAUInitNV(final Buffer p0, final Buffer p1);
    
    void glVDPAUFiniNV();
    
    long glVDPAURegisterVideoSurfaceNV(final Buffer p0, final int p1, final int p2, final IntBuffer p3);
    
    long glVDPAURegisterVideoSurfaceNV(final Buffer p0, final int p1, final int p2, final int[] p3, final int p4);
    
    long glVDPAURegisterOutputSurfaceNV(final Buffer p0, final int p1, final int p2, final IntBuffer p3);
    
    long glVDPAURegisterOutputSurfaceNV(final Buffer p0, final int p1, final int p2, final int[] p3, final int p4);
    
    boolean glVDPAUIsSurfaceNV(final long p0);
    
    void glVDPAUUnregisterSurfaceNV(final long p0);
    
    void glVDPAUGetSurfaceivNV(final long p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4);
    
    void glVDPAUGetSurfaceivNV(final long p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6);
    
    void glVDPAUSurfaceAccessNV(final long p0, final int p1);
    
    void glVDPAUMapSurfacesNV(final int p0, final PointerBuffer p1);
    
    void glVDPAUUnmapSurfacesNV(final int p0, final PointerBuffer p1);
    
    void glVertexAttribL1i64NV(final int p0, final long p1);
    
    void glVertexAttribL2i64NV(final int p0, final long p1, final long p2);
    
    void glVertexAttribL3i64NV(final int p0, final long p1, final long p2, final long p3);
    
    void glVertexAttribL4i64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glVertexAttribL1i64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL1i64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL2i64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL2i64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL3i64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL3i64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL4i64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL4i64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL1ui64NV(final int p0, final long p1);
    
    void glVertexAttribL2ui64NV(final int p0, final long p1, final long p2);
    
    void glVertexAttribL3ui64NV(final int p0, final long p1, final long p2, final long p3);
    
    void glVertexAttribL4ui64NV(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    void glVertexAttribL1ui64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL1ui64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL2ui64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL2ui64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL3ui64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL3ui64vNV(final int p0, final long[] p1, final int p2);
    
    void glVertexAttribL4ui64vNV(final int p0, final LongBuffer p1);
    
    void glVertexAttribL4ui64vNV(final int p0, final long[] p1, final int p2);
    
    void glGetVertexAttribLi64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetVertexAttribLi64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetVertexAttribLui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetVertexAttribLui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glVertexAttribLFormatNV(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribI1iEXT(final int p0, final int p1);
    
    void glVertexAttribI2iEXT(final int p0, final int p1, final int p2);
    
    void glVertexAttribI3iEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribI4iEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexAttribI1uiEXT(final int p0, final int p1);
    
    void glVertexAttribI2uiEXT(final int p0, final int p1, final int p2);
    
    void glVertexAttribI3uiEXT(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribI4uiEXT(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexAttribI1ivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI1ivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI2ivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI2ivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI3ivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI3ivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI4ivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI4ivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI1uivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI1uivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI2uivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI2uivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI3uivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI3uivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI4uivEXT(final int p0, final IntBuffer p1);
    
    void glVertexAttribI4uivEXT(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI4bvEXT(final int p0, final ByteBuffer p1);
    
    void glVertexAttribI4bvEXT(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttribI4svEXT(final int p0, final ShortBuffer p1);
    
    void glVertexAttribI4svEXT(final int p0, final short[] p1, final int p2);
    
    void glVertexAttribI4ubvEXT(final int p0, final ByteBuffer p1);
    
    void glVertexAttribI4ubvEXT(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttribI4usvEXT(final int p0, final ShortBuffer p1);
    
    void glVertexAttribI4usvEXT(final int p0, final short[] p1, final int p2);
    
    void glVertexAttribIPointerEXT(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glGetVertexAttribIivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribIivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVertexAttribIuivEXT(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribIuivEXT(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBeginVideoCaptureNV(final int p0);
    
    void glBindVideoCaptureStreamBufferNV(final int p0, final int p1, final int p2, final long p3);
    
    void glBindVideoCaptureStreamTextureNV(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glEndVideoCaptureNV(final int p0);
    
    void glGetVideoCaptureivNV(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVideoCaptureivNV(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVideoCaptureStreamivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetVideoCaptureStreamivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetVideoCaptureStreamfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetVideoCaptureStreamfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetVideoCaptureStreamdvNV(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glGetVideoCaptureStreamdvNV(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    int glVideoCaptureNV(final int p0, final IntBuffer p1, final LongBuffer p2);
    
    int glVideoCaptureNV(final int p0, final int[] p1, final int p2, final long[] p3, final int p4);
    
    void glVideoCaptureStreamParameterivNV(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glVideoCaptureStreamParameterivNV(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glVideoCaptureStreamParameterfvNV(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glVideoCaptureStreamParameterfvNV(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glVideoCaptureStreamParameterdvNV(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glVideoCaptureStreamParameterdvNV(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glFramebufferTextureMultiviewOVR(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glHintPGI(final int p0, final int p1);
    
    void glFinishTextureSUNX();
    
    GLBufferStorage mapNamedBufferEXT(final int p0, final int p1) throws GLException;
    
    GLBufferStorage mapNamedBufferRangeEXT(final int p0, final long p1, final long p2, final int p3) throws GLException;
    
    void glVertexAttribPointer(final int p0, final int p1, final int p2, final boolean p3, final int p4, final Buffer p5);
    
    void glDrawElementsInstanced(final int p0, final int p1, final int p2, final Buffer p3, final int p4);
    
    void glDrawRangeElements(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glVertexAttribIPointer(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
}
