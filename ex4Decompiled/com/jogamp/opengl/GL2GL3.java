// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.LongBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.FloatBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;
import java.nio.DoubleBuffer;

public interface GL2GL3 extends GL2ES3
{
    public static final int GL_EDGE_FLAG_ARRAY_ADDRESS_NV = 36646;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT = 36309;
    public static final int GL_FOG_COORD_ARRAY_ADDRESS_NV = 36648;
    public static final int GL_VIRTUAL_PAGE_SIZE_Y_ARB = 37270;
    public static final int GL_PACK_IMAGE_HEIGHT = 32876;
    public static final int GL_GREEN_INTEGER = 36245;
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 36875;
    public static final int GL_COMPRESSED_SRGB_ALPHA = 35913;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGB = 33484;
    public static final int GL_TEXTURE_GATHER = 33442;
    public static final int GL_IMAGE_CLASS_10_10_10_2 = 33475;
    public static final int GL_TESS_EVALUATION_TEXTURE = 33437;
    public static final int GL_BACK_LEFT = 1026;
    public static final int GL_POLYGON_OFFSET_POINT = 10753;
    public static final int GL_GPU_ADDRESS_NV = 36660;
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
    public static final int GL_TEXTURE_COMPRESSION_HINT = 34031;
    public static final int GL_VIEW_CLASS_RGTC1_RED = 33488;
    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS_ARB = 37274;
    public static final int GL_TEXTURE_VIEW = 33461;
    public static final int GL_STENCIL_INDEX16 = 36169;
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE = 36960;
    public static final int GL_DEPTH_COMPONENTS = 33412;
    public static final int GL_RGB5 = 32848;
    public static final int GL_RGB4 = 32847;
    public static final int GL_FRAMEBUFFER_RENDERABLE = 33417;
    public static final int GL_VERTEX_ATTRIB_ARRAY_LENGTH_NV = 36650;
    public static final int GL_TEXTURE_RECTANGLE = 34037;
    public static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 37193;
    public static final int GL_DEBUG_SEVERITY_LOW_AMD = 37192;
    public static final int GL_UNSIGNED_INT_IMAGE_1D = 36962;
    public static final int GL_TESS_CONTROL_TEXTURE = 33436;
    public static final int GL_READ_PIXELS = 33420;
    public static final int GL_POLYGON_SMOOTH_HINT = 3155;
    public static final int GL_INT_IMAGE_1D = 36951;
    public static final int GL_POINT = 6912;
    public static final int GL_VERTEX_SHADER_INVOCATIONS_ARB = 33520;
    public static final int GL_NORMAL_ARRAY_LENGTH_NV = 36652;
    public static final int GL_IMAGE_1D_ARRAY = 36946;
    public static final int GL_COMPUTE_SHADER_INVOCATIONS_ARB = 33525;
    public static final int GL_POLYGON_OFFSET_LINE = 10754;
    public static final int GL_INTERNALFORMAT_STENCIL_TYPE = 33405;
    public static final int GL_MAX_WIDTH = 33406;
    public static final int GL_TESS_CONTROL_SHADER_PATCHES_ARB = 33521;
    public static final int GL_PRIMITIVES_SUBMITTED_ARB = 33519;
    public static final int GL_VIEW_CLASS_24_BITS = 33481;
    public static final int GL_TEXTURE_LOD_BIAS = 34049;
    public static final int GL_CLIPPING_OUTPUT_PRIMITIVES_ARB = 33527;
    public static final int GL_INTERNALFORMAT_GREEN_SIZE = 33394;
    public static final int GL_SET_AMD = 34634;
    public static final int GL_SUBSAMPLE_DISTANCE_AMD = 34879;
    public static final int GL_SAMPLER_1D = 35677;
    public static final int GL_SAMPLES_PASSED = 35092;
    public static final int GL_CLIP_DISTANCE1 = 12289;
    public static final int GL_CLIP_DISTANCE0 = 12288;
    public static final int GL_CLIP_DISTANCE3 = 12291;
    public static final int GL_CLIP_DISTANCE2 = 12290;
    public static final int GL_CLIP_DISTANCE5 = 12293;
    public static final int GL_CLIP_DISTANCE4 = 12292;
    public static final int GL_CLIP_DISTANCE7 = 12295;
    public static final int GL_CLIP_DISTANCE6 = 12294;
    public static final int GL_MAX_HEIGHT = 33407;
    public static final int GL_TEXTURE_SWIZZLE_RGBA = 36422;
    public static final int GL_FIXED_ONLY = 35101;
    public static final int GL_TEXTURE_1D_ARRAY = 35864;
    public static final int GL_TEXTURE_SHADOW = 33441;
    public static final int GL_SPARSE_TEXTURE_FULL_ARRAY_CUBE_MIPMAPS_ARB = 37289;
    public static final int GL_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 33458;
    public static final int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = 37572;
    public static final int GL_VERTICES_SUBMITTED_ARB = 33518;
    public static final int GL_MIPMAP = 33427;
    public static final int GL_INT_SAMPLER_1D = 36297;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_DEPTH = 37161;
    public static final int GL_TEXTURE_SRGB_DECODE_EXT = 35400;
    public static final int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 33486;
    public static final int GL_UNSIGNED_INT_IMAGE_1D_ARRAY = 36968;
    public static final int GL_R3_G3_B2 = 10768;
    public static final int GL_UPPER_LEFT = 36002;
    public static final int GL_DEPTH_CLAMP_FAR_AMD = 36895;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
    public static final int GL_BGRA_INTEGER = 36251;
    public static final int GL_SPARSE_STORAGE_BIT_ARB = 1024;
    public static final int GL_PACK_SKIP_IMAGES = 32875;
    public static final int GL_DRAW_BUFFER = 3073;
    public static final int GL_COMPRESSED_RED_RGTC1 = 36283;
    public static final int GL_BLUE_INTEGER = 36246;
    public static final int GL_INTERNALFORMAT_SHARED_SIZE = 33399;
    public static final int GL_GEOMETRY_TEXTURE = 33438;
    public static final int GL_IMAGE_CLASS_1_X_8 = 33473;
    public static final int GL_IMAGE_1D = 36940;
    public static final int GL_DOUBLE = 5130;
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = 37573;
    public static final int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
    public static final int GL_TESS_EVALUATION_SHADER_INVOCATIONS_ARB = 33522;
    public static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 37194;
    public static final int GL_DRAW_INDIRECT_ADDRESS_NV = 36673;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_AMD = 37188;
    public static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 37191;
    public static final int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS = 36665;
    public static final int GL_MAX_VARYING_FLOATS = 35659;
    public static final int GL_DEBUG_LOGGED_MESSAGES_AMD = 37189;
    public static final int GL_INTERNALFORMAT_RED_SIZE = 33393;
    public static final int GL_PACK_COMPRESSED_BLOCK_HEIGHT = 37164;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = 37579;
    public static final int GL_INT_SAMPLER_2D_RECT = 36301;
    public static final int GL_SKIP_DECODE_EXT = 35402;
    public static final int GL_SAMPLER_BUFFER_AMD = 36865;
    public static final int GL_LOWER_LEFT = 36001;
    public static final int GL_IMAGE_PIXEL_TYPE = 33450;
    public static final int GL_DEPTH_RENDERABLE = 33415;
    public static final int GL_STENCIL_BACK_OP_VALUE_AMD = 34637;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_WIDTH = 37159;
    public static final int GL_INTERNALFORMAT_BLUE_TYPE = 33402;
    public static final int GL_SRGB_WRITE = 33432;
    public static final int GL_VIRTUAL_PAGE_SIZE_X_ARB = 37269;
    public static final int GL_DEPTH_CLAMP_NEAR_AMD = 36894;
    public static final int GL_VIEW_CLASS_BPTC_UNORM = 33490;
    public static final int GL_CLIPPING_INPUT_PRIMITIVES_ARB = 33526;
    public static final int GL_DOUBLEBUFFER = 3122;
    public static final int GL_R16 = 33322;
    public static final int GL_SHADER_IMAGE_STORE = 33445;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_AMD = 36867;
    public static final int GL_SAMPLER_1D_ARRAY = 36288;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 33452;
    public static final int GL_COMPRESSED_RG = 33318;
    public static final int GL_COLOR_ENCODING = 33430;
    public static final int GL_CLEAR_BUFFER = 33460;
    public static final int GL_TEXTURE_1D = 3552;
    public static final int GL_DRAW_INDIRECT_LENGTH_NV = 36674;
    public static final int GL_FILTER = 33434;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 35397;
    public static final int GL_TEXTURE_COORD_ARRAY_ADDRESS_NV = 36645;
    public static final int GL_VIEW_COMPATIBILITY_CLASS = 33462;
    public static final int GL_IMAGE_CLASS_2_X_16 = 33469;
    public static final int GL_INT_SAMPLER_1D_ARRAY = 36302;
    public static final int GL_TEXTURE_SPARSE_ARB = 37286;
    public static final int GL_IMAGE_CLASS_2_X_32 = 33466;
    public static final int GL_GET_TEXTURE_IMAGE_TYPE = 33426;
    public static final int GL_SAMPLER_2D_RECT_SHADOW = 35684;
    public static final int GL_UNSIGNED_BYTE_3_3_2 = 32818;
    public static final int GL_IMAGE_CLASS_11_11_10 = 33474;
    public static final int GL_INTERNALFORMAT_DEPTH_TYPE = 33404;
    public static final int GL_TESSELLATION_MODE_AMD = 36868;
    public static final int GL_FRAGMENT_SHADER_INVOCATIONS_ARB = 33524;
    public static final int GL_INT_IMAGE_1D_ARRAY = 36957;
    public static final int GL_MAX_IMAGE_SAMPLES = 36973;
    public static final int GL_VIEW_CLASS_32_BITS = 33480;
    public static final int GL_CLAMP_READ_COLOR = 35100;
    public static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 37199;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = 37577;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 33485;
    public static final int GL_UNSIGNED_INT_8_8_8_8 = 32821;
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 33639;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 33459;
    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE = 34040;
    public static final int GL_COMPRESSED_RG_RGTC2 = 36285;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    public static final int GL_STEREO = 3123;
    public static final int GL_UNPACK_SWAP_BYTES = 3312;
    public static final int GL_INT_SAMPLER_BUFFER_AMD = 36866;
    public static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 37195;
    public static final int GL_INDEX_ARRAY_LENGTH_NV = 36654;
    public static final int GL_SCALED_RESOLVE_FASTEST_EXT = 37050;
    public static final int GL_VIEW_CLASS_RGTC2_RG = 33489;
    public static final int GL_LINE = 6913;
    public static final int GL_IMAGE_COMPATIBILITY_CLASS = 33448;
    public static final int GL_MANUAL_GENERATE_MIPMAP = 33428;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 33637;
    public static final int GL_BUFFER_GPU_ADDRESS_NV = 36637;
    public static final int GL_PROXY_TEXTURE_RECTANGLE = 34039;
    public static final int GL_MAX_DEPTH = 33408;
    public static final int GL_FACTOR_MAX_AMD = 36893;
    public static final int GL_TEXTURE_IMAGE_TYPE = 33424;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 33454;
    public static final int GL_DRAW_INDIRECT_UNIFIED_NV = 36672;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
    public static final int GL_INTERNALFORMAT_ALPHA_SIZE = 33396;
    public static final int GL_FOG_COORD_ARRAY_LENGTH_NV = 36658;
    public static final int GL_IMAGE_CLASS_2_X_8 = 33472;
    public static final int GL_IMAGE_TEXEL_SIZE = 33447;
    public static final int GL_STENCIL_RENDERABLE = 33416;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE = 36971;
    public static final int GL_GET_TEXTURE_IMAGE_FORMAT = 33425;
    public static final int GL_COLOR_COMPONENTS = 33411;
    public static final int GL_BGR_INTEGER = 36250;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36972;
    public static final int GL_MAX_LAYERS = 33409;
    public static final int GL_MAX_VERTEX_STREAMS = 36465;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static final int GL_SRGB_READ = 33431;
    public static final int GL_IMAGE_2D_MULTISAMPLE_ARRAY = 36950;
    public static final int GL_MAX_COMBINED_DIMENSIONS = 33410;
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 33636;
    public static final int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 33418;
    public static final int GL_MAX_SHADER_BUFFER_ADDRESS_NV = 36661;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED_ARB = 33523;
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 33638;
    public static final int GL_STENCIL_OP_VALUE_AMD = 34636;
    public static final int GL_FRONT_RIGHT = 1025;
    public static final int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
    public static final int GL_TEXTURE_GATHER_SHADOW = 33443;
    public static final int GL_SRGB_DECODE = 33433;
    public static final int GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW_ARB = 33517;
    public static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 37198;
    public static final int GL_STENCIL_COMPONENTS = 33413;
    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 36000;
    public static final int GL_COLOR_RENDERABLE = 33414;
    public static final int GL_NORMAL_ARRAY_ADDRESS_NV = 36642;
    public static final int GL_RGBA16_SNORM = 36763;
    public static final int GL_POLYGON_MODE = 2880;
    public static final int GL_SAMPLER_1D_SHADOW = 35681;
    public static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 37197;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_SIZE = 37162;
    public static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 37196;
    public static final int GL_FULL_SUPPORT = 33463;
    public static final int GL_TEXTURE_BINDING_RECTANGLE = 34038;
    public static final int GL_VIEW_CLASS_128_BITS = 33476;
    public static final int GL_DECODE_EXT = 35401;
    public static final int GL_RGBA2 = 32853;
    public static final int GL_UNPACK_COMPRESSED_BLOCK_HEIGHT = 37160;
    public static final int GL_TEXTURE_IMAGE_FORMAT = 33423;
    public static final int GL_COMPUTE_TEXTURE = 33440;
    public static final int GL_ELEMENT_ARRAY_ADDRESS_NV = 36649;
    public static final int GL_MAX_CLIP_DISTANCES = 3378;
    public static final int GL_ELEMENT_ARRAY_LENGTH_NV = 36659;
    public static final int GL_IMAGE_2D_MULTISAMPLE = 36949;
    public static final int GL_SYNC_X11_FENCE_EXT = 37089;
    public static final int GL_NUM_SPARSE_LEVELS_ARB = 37290;
    public static final int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 33487;
    public static final int GL_NUM_VIRTUAL_PAGE_SIZES_ARB = 37288;
    public static final int GL_VIEW_CLASS_8_BITS = 33483;
    public static final int GL_PACK_LSB_FIRST = 3329;
    public static final int GL_INTERNALFORMAT_STENCIL_SIZE = 33398;
    public static final int GL_INT_IMAGE_2D_RECT = 36954;
    public static final int GL_TESSELLATION_FACTOR_AMD = 36869;
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 36961;
    public static final int GL_POLYGON_SMOOTH = 2881;
    public static final int GL_DISCRETE_AMD = 36870;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ADDRESS_NV = 36640;
    public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
    public static final int GL_TEXTURE_COORD_ARRAY_LENGTH_NV = 36655;
    public static final int GL_INTERNALFORMAT_GREEN_TYPE = 33401;
    public static final int GL_CAVEAT_SUPPORT = 33464;
    public static final int GL_VIEW_CLASS_64_BITS = 33478;
    public static final int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
    public static final int GL_MIN_MAP_BUFFER_ALIGNMENT = 37052;
    public static final int GL_COMPRESSED_RGBA = 34030;
    public static final int GL_FILL = 6914;
    public static final int GL_IMAGE_CLASS_1_X_32 = 33467;
    public static final int GL_IMAGE_CLASS_1_X_16 = 33470;
    public static final int GL_SECONDARY_COLOR_ARRAY_ADDRESS_NV = 36647;
    public static final int GL_PROXY_TEXTURE_2D = 32868;
    public static final int GL_FACTOR_MIN_AMD = 36892;
    public static final int GL_IMAGE_CLASS_4_X_16 = 33468;
    public static final int GL_COMPRESSED_RGB = 34029;
    public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT_KHR = 8;
    public static final int GL_PROXY_TEXTURE_3D = 32880;
    public static final int GL_SPARSE_BUFFER_PAGE_SIZE_ARB = 33528;
    public static final int GL_INTERNALFORMAT_SUPPORTED = 33391;
    public static final int GL_SECONDARY_COLOR_ARRAY_LENGTH_NV = 36657;
    public static final int GL_PROXY_TEXTURE_1D = 32867;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 34464;
    public static final int GL_COMPRESSED_SRGB = 35912;
    public static final int GL_COMPRESSED_RED = 33317;
    public static final int GL_IMAGE_CLASS_4_X_32 = 33465;
    public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
    public static final int GL_SAMPLER_2D_RECT = 35683;
    public static final int GL_INTERNALFORMAT_BLUE_SIZE = 33395;
    public static final int GL_VERTEX_ATTRIB_ARRAY_UNIFIED_NV = 36638;
    public static final int GL_POINT_SIZE_RANGE = 2834;
    public static final int GL_ELEMENT_ARRAY_UNIFIED_NV = 36639;
    public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    public static final int GL_PACK_COMPRESSED_BLOCK_SIZE = 37166;
    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_ARB = 37273;
    public static final int GL_DEBUG_CATEGORY_OTHER_AMD = 37200;
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = 37574;
    public static final int GL_VERTEX_ARRAY_LENGTH_NV = 36651;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = 37576;
    public static final int GL_AUTO_GENERATE_MIPMAP = 33429;
    public static final int GL_IMAGE_2D_RECT = 36943;
    public static final int GL_PRIMITIVE_RESTART_INDEX = 36766;
    public static final int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = 37594;
    public static final int GL_VIRTUAL_PAGE_SIZE_Z_ARB = 37271;
    public static final int GL_PACK_COMPRESSED_BLOCK_DEPTH = 37165;
    public static final int GL_VIEW_CLASS_96_BITS = 33477;
    public static final int GL_INDEX_ARRAY_ADDRESS_NV = 36644;
    public static final int GL_IMAGE_PIXEL_FORMAT = 33449;
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 33634;
    public static final int GL_DEBUG_SEVERITY_HIGH_AMD = 37190;
    public static final int GL_COLOR_ARRAY_LENGTH_NV = 36653;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = 37578;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 33455;
    public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_BACK_RIGHT = 1027;
    public static final int GL_INTERNALFORMAT_DEPTH_SIZE = 33397;
    public static final int GL_INTERNALFORMAT_PREFERRED = 33392;
    public static final int GL_READ_PIXELS_TYPE = 33422;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static final int GL_VERTEX_TEXTURE = 33435;
    public static final int GL_VERTEX_ARRAY_ADDRESS_NV = 36641;
    public static final int GL_PROXY_TEXTURE_CUBE_MAP = 34075;
    public static final int GL_R16_SNORM = 36760;
    public static final int GL_PRIMITIVE_RESTART = 36765;
    public static final int GL_PACK_SWAP_BYTES = 3328;
    public static final int GL_TEXTURE_COLOR_SAMPLES_NV = 36934;
    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_ARB = 37272;
    public static final int GL_RGB16_SNORM = 36762;
    public static final int GL_VIRTUAL_PAGE_SIZE_INDEX_ARB = 37287;
    public static final int GL_VIEW_CLASS_BPTC_FLOAT = 33491;
    public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_EDGE_FLAG_ARRAY_LENGTH_NV = 36656;
    public static final int GL_RIGHT = 1031;
    public static final int GL_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
    public static final int GL_RG16 = 33324;
    public static final int GL_VIEW_CLASS_16_BITS = 33482;
    public static final int GL_TRANSFORM_FEEDBACK_OVERFLOW_ARB = 33516;
    public static final int GL_COLOR_ARRAY_ADDRESS_NV = 36643;
    public static final int GL_UNSIGNED_INT_IMAGE_2D_RECT = 36965;
    public static final int GL_PACK_COMPRESSED_BLOCK_WIDTH = 37163;
    public static final int GL_RGB16 = 32852;
    public static final int GL_SHADER_IMAGE_LOAD = 33444;
    public static final int GL_RGB12 = 32851;
    public static final int GL_FRAMEBUFFER_BLEND = 33419;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = 37575;
    public static final int GL_EXTERNAL_VIRTUAL_MEMORY_BUFFER_AMD = 37216;
    public static final int GL_TEXTURE_BINDING_1D = 32872;
    public static final int GL_UNPACK_LSB_FIRST = 3313;
    public static final int GL_LEFT = 1030;
    public static final int GL_RGBA16 = 32859;
    public static final int GL_TEXTURE_COVERAGE_SAMPLES_NV = 36933;
    public static final int GL_SCALED_RESOLVE_NICEST_EXT = 37051;
    public static final int GL_VIEW_CLASS_48_BITS = 33479;
    public static final int GL_RGBA12 = 32858;
    public static final int GL_FRAGMENT_TEXTURE = 33439;
    public static final int GL_SHADER_IMAGE_ATOMIC = 33446;
    public static final int GL_LINE_WIDTH_RANGE = 2850;
    public static final int GL_IMAGE_CLASS_4_X_8 = 33471;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 33457;
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_CONTINUOUS_AMD = 36871;
    public static final int GL_REPLACE_VALUE_AMD = 34635;
    public static final int GL_RG16_SNORM = 36761;
    public static final int GL_FRONT_LEFT = 1024;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 33453;
    public static final int GL_PROVOKING_VERTEX = 36431;
    public static final int GL_INTERNALFORMAT_RED_TYPE = 33400;
    public static final int GL_INTERNALFORMAT_ALPHA_TYPE = 33403;
    public static final int GL_READ_PIXELS_FORMAT = 33421;
    
    void glLogicOp(final int p0);
    
    void glPointSize(final float p0);
    
    void glPolygonMode(final int p0, final int p1);
    
    void glDrawBuffer(final int p0);
    
    void glGetDoublev(final int p0, final DoubleBuffer p1);
    
    void glGetDoublev(final int p0, final double[] p1, final int p2);
    
    void glPixelStoref(final int p0, final float p1);
    
    void glTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Buffer p7);
    
    void glTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    void glGetTexImage(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glGetTexImage(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glEnableClientState(final int p0);
    
    void glDisableClientState(final int p0);
    
    void glTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glCopyTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    void glCopyTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glCompressedTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glCompressedTexImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glCompressedTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glCompressedTexSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glGetCompressedTexImage(final int p0, final int p1, final Buffer p2);
    
    void glGetCompressedTexImage(final int p0, final int p1, final long p2);
    
    void glMultiDrawArrays(final int p0, final IntBuffer p1, final IntBuffer p2, final int p3);
    
    void glMultiDrawArrays(final int p0, final int[] p1, final int p2, final int[] p3, final int p4, final int p5);
    
    void glMultiDrawElements(final int p0, final IntBuffer p1, final int p2, final PointerBuffer p3, final int p4);
    
    void glPointParameterf(final int p0, final float p1);
    
    void glPointParameterfv(final int p0, final FloatBuffer p1);
    
    void glPointParameterfv(final int p0, final float[] p1, final int p2);
    
    void glPointParameteri(final int p0, final int p1);
    
    void glPointParameteriv(final int p0, final IntBuffer p1);
    
    void glPointParameteriv(final int p0, final int[] p1, final int p2);
    
    void glGetBufferSubData(final int p0, final long p1, final long p2, final Buffer p3);
    
    void glGetVertexAttribdv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetVertexAttribdv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glVertexAttrib1d(final int p0, final double p1);
    
    void glVertexAttrib1dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib1dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib1s(final int p0, final short p1);
    
    void glVertexAttrib1sv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib1sv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib2d(final int p0, final double p1, final double p2);
    
    void glVertexAttrib2dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib2dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib2s(final int p0, final short p1, final short p2);
    
    void glVertexAttrib2sv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib2sv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib3d(final int p0, final double p1, final double p2, final double p3);
    
    void glVertexAttrib3dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib3dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib3s(final int p0, final short p1, final short p2, final short p3);
    
    void glVertexAttrib3sv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib3sv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4Nbv(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4Nbv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4Niv(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4Niv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4Nsv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4Nsv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4Nub(final int p0, final byte p1, final byte p2, final byte p3, final byte p4);
    
    void glVertexAttrib4Nubv(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4Nubv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4Nuiv(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4Nuiv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4Nusv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4Nusv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4bv(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4bv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4d(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glVertexAttrib4dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttrib4dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttrib4iv(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4iv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4s(final int p0, final short p1, final short p2, final short p3, final short p4);
    
    void glVertexAttrib4sv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4sv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttrib4ubv(final int p0, final ByteBuffer p1);
    
    void glVertexAttrib4ubv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttrib4uiv(final int p0, final IntBuffer p1);
    
    void glVertexAttrib4uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttrib4usv(final int p0, final ShortBuffer p1);
    
    void glVertexAttrib4usv(final int p0, final short[] p1, final int p2);
    
    void glClampColor(final int p0, final int p1);
    
    void glVertexAttribI1i(final int p0, final int p1);
    
    void glVertexAttribI2i(final int p0, final int p1, final int p2);
    
    void glVertexAttribI3i(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribI1ui(final int p0, final int p1);
    
    void glVertexAttribI2ui(final int p0, final int p1, final int p2);
    
    void glVertexAttribI3ui(final int p0, final int p1, final int p2, final int p3);
    
    void glVertexAttribI1iv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI1iv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI2iv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI2iv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI3iv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI3iv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI1uiv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI1uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI2uiv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI2uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI3uiv(final int p0, final IntBuffer p1);
    
    void glVertexAttribI3uiv(final int p0, final int[] p1, final int p2);
    
    void glVertexAttribI4bv(final int p0, final ByteBuffer p1);
    
    void glVertexAttribI4bv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttribI4sv(final int p0, final ShortBuffer p1);
    
    void glVertexAttribI4sv(final int p0, final short[] p1, final int p2);
    
    void glVertexAttribI4ubv(final int p0, final ByteBuffer p1);
    
    void glVertexAttribI4ubv(final int p0, final byte[] p1, final int p2);
    
    void glVertexAttribI4usv(final int p0, final ShortBuffer p1);
    
    void glVertexAttribI4usv(final int p0, final short[] p1, final int p2);
    
    void glBindFragDataLocation(final int p0, final int p1, final String p2);
    
    void glFramebufferTexture1D(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glPrimitiveRestartIndex(final int p0);
    
    void glGetActiveUniformName(final int p0, final int p1, final int p2, final IntBuffer p3, final ByteBuffer p4);
    
    void glGetActiveUniformName(final int p0, final int p1, final int p2, final int[] p3, final int p4, final byte[] p5, final int p6);
    
    void glProvokingVertex(final int p0);
    
    void glDrawTransformFeedback(final int p0, final int p1);
    
    void glDrawTransformFeedbackStream(final int p0, final int p1, final int p2);
    
    void glBeginQueryIndexed(final int p0, final int p1, final int p2);
    
    void glEndQueryIndexed(final int p0, final int p1);
    
    void glGetQueryIndexediv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetQueryIndexediv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform1d(final int p0, final int p1, final double p2);
    
    void glProgramUniform1dv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform1dv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform2d(final int p0, final int p1, final double p2, final double p3);
    
    void glProgramUniform2dv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform2dv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform3d(final int p0, final int p1, final double p2, final double p3, final double p4);
    
    void glProgramUniform3dv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform3dv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniform4d(final int p0, final int p1, final double p2, final double p3, final double p4, final double p5);
    
    void glProgramUniform4dv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glProgramUniform4dv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glProgramUniformMatrix2dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix2x3dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2x3dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3x2dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3x2dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix2x4dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix2x4dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4x2dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4x2dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix3x4dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix3x4dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glProgramUniformMatrix4x3dv(final int p0, final int p1, final int p2, final boolean p3, final DoubleBuffer p4);
    
    void glProgramUniformMatrix4x3dv(final int p0, final int p1, final int p2, final boolean p3, final double[] p4, final int p5);
    
    void glVertexAttribL1d(final int p0, final double p1);
    
    void glVertexAttribL2d(final int p0, final double p1, final double p2);
    
    void glVertexAttribL3d(final int p0, final double p1, final double p2, final double p3);
    
    void glVertexAttribL4d(final int p0, final double p1, final double p2, final double p3, final double p4);
    
    void glVertexAttribL1dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttribL1dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttribL2dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttribL2dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttribL3dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttribL3dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttribL4dv(final int p0, final DoubleBuffer p1);
    
    void glVertexAttribL4dv(final int p0, final double[] p1, final int p2);
    
    void glVertexAttribLPointer(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    void glGetVertexAttribLdv(final int p0, final int p1, final DoubleBuffer p2);
    
    void glGetVertexAttribLdv(final int p0, final int p1, final double[] p2, final int p3);
    
    void glGetActiveAtomicCounterBufferiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetActiveAtomicCounterBufferiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glClearBufferData(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glClearBufferSubData(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final Buffer p6);
    
    void glGetInternalformati64v(final int p0, final int p1, final int p2, final int p3, final LongBuffer p4);
    
    void glGetInternalformati64v(final int p0, final int p1, final int p2, final int p3, final long[] p4, final int p5);
    
    void glInvalidateTexSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glInvalidateTexImage(final int p0, final int p1);
    
    void glInvalidateBufferSubData(final int p0, final long p1, final long p2);
    
    void glInvalidateBufferData(final int p0);
    
    void glGetnCompressedTexImage(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glGetnTexImage(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glGetnUniformdv(final int p0, final int p1, final int p2, final DoubleBuffer p3);
    
    void glGetnUniformdv(final int p0, final int p1, final int p2, final double[] p3, final int p4);
    
    void glBufferPageCommitmentARB(final int p0, final long p1, final long p2, final boolean p3);
    
    void glNamedBufferPageCommitmentEXT(final int p0, final long p1, final long p2, final boolean p3);
    
    void glNamedBufferPageCommitmentARB(final int p0, final long p1, final long p2, final boolean p3);
    
    void glTexPageCommitmentARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8);
    
    void glDebugMessageEnableAMD(final int p0, final int p1, final int p2, final IntBuffer p3, final boolean p4);
    
    void glDebugMessageEnableAMD(final int p0, final int p1, final int p2, final int[] p3, final int p4, final boolean p5);
    
    void glDebugMessageInsertAMD(final int p0, final int p1, final int p2, final int p3, final String p4);
    
    int glGetDebugMessageLogAMD(final int p0, final int p1, final IntBuffer p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final ByteBuffer p6);
    
    int glGetDebugMessageLogAMD(final int p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5, final int[] p6, final int p7, final int[] p8, final int p9, final byte[] p10, final int p11);
    
    void glGetUniformui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetUniformui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glMultiDrawArraysIndirectAMD(final int p0, final Buffer p1, final int p2, final int p3);
    
    void glMultiDrawElementsIndirectAMD(final int p0, final int p1, final Buffer p2, final int p3, final int p4);
    
    void glSetMultisamplefvAMD(final int p0, final int p1, final FloatBuffer p2);
    
    void glSetMultisamplefvAMD(final int p0, final int p1, final float[] p2, final int p3);
    
    void glStencilOpValueAMD(final int p0, final int p1);
    
    void glTessellationFactorAMD(final float p0);
    
    void glTessellationModeAMD(final int p0);
    
    long glImportSyncEXT(final int p0, final long p1, final int p2);
    
    void glMakeBufferResidentNV(final int p0, final int p1);
    
    void glMakeBufferNonResidentNV(final int p0);
    
    boolean glIsBufferResidentNV(final int p0);
    
    void glMakeNamedBufferResidentNV(final int p0, final int p1);
    
    void glMakeNamedBufferNonResidentNV(final int p0);
    
    boolean glIsNamedBufferResidentNV(final int p0);
    
    void glGetBufferParameterui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetBufferParameterui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetNamedBufferParameterui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetNamedBufferParameterui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetIntegerui64vNV(final int p0, final LongBuffer p1);
    
    void glGetIntegerui64vNV(final int p0, final long[] p1, final int p2);
    
    void glUniformui64NV(final int p0, final long p1);
    
    void glUniformui64vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glUniformui64vNV(final int p0, final int p1, final long[] p2, final int p3);
    
    void glProgramUniformui64NV(final int p0, final int p1, final long p2);
    
    void glProgramUniformui64vNV(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniformui64vNV(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glTexImage2DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glTexImage3DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
    
    void glTextureImage2DMultisampleNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glTextureImage3DMultisampleNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
    
    void glTextureImage2DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
    
    void glTextureImage3DMultisampleCoverageNV(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8);
    
    void glBufferAddressRangeNV(final int p0, final int p1, final long p2, final long p3);
    
    void glVertexFormatNV(final int p0, final int p1, final int p2);
    
    void glNormalFormatNV(final int p0, final int p1);
    
    void glColorFormatNV(final int p0, final int p1, final int p2);
    
    void glIndexFormatNV(final int p0, final int p1);
    
    void glTexCoordFormatNV(final int p0, final int p1, final int p2);
    
    void glEdgeFlagFormatNV(final int p0);
    
    void glSecondaryColorFormatNV(final int p0, final int p1, final int p2);
    
    void glFogCoordFormatNV(final int p0, final int p1);
    
    void glVertexAttribFormatNV(final int p0, final int p1, final int p2, final boolean p3, final int p4);
    
    void glVertexAttribIFormatNV(final int p0, final int p1, final int p2, final int p3);
    
    void glGetIntegerui64i_vNV(final int p0, final int p1, final LongBuffer p2);
    
    void glGetIntegerui64i_vNV(final int p0, final int p1, final long[] p2, final int p3);
}
