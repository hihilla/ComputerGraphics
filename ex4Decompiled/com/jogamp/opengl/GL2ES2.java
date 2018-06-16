// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.LongBuffer;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public interface GL2ES2 extends GL
{
    public static final int GL_SHADER = 33505;
    public static final int GL_CURRENT_VERTEX_ATTRIB = 34342;
    public static final int GL_VERTEX_SHADER = 35633;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static final int GL_DRAW_BUFFER1 = 34854;
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static final int GL_QUERY = 33507;
    public static final int GL_VERTEX_SHADER_BIT = 1;
    public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 34819;
    public static final int GL_ACTIVE_PROGRAM = 33369;
    public static final int GL_COLOR_ATTACHMENT5 = 36069;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static final int GL_STENCIL_BACK_FUNC = 34816;
    public static final int GL_COMPILE_STATUS = 35713;
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static final int GL_FLOAT_VEC2 = 35664;
    public static final int GL_FLOAT_VEC4 = 35666;
    public static final int GL_FLOAT_VEC3 = 35665;
    public static final int GL_TIMESTAMP = 36392;
    public static final int GL_MAX_LABEL_LENGTH = 33512;
    public static final int GL_DEBUG_SOURCE_OTHER = 33355;
    public static final int GL_CURRENT_PROGRAM = 35725;
    public static final int GL_DRAW_BUFFER10 = 34863;
    public static final int GL_SHADER_SOURCE_LENGTH = 35720;
    public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    public static final int GL_COMPARE_REF_TO_TEXTURE = 34894;
    public static final int GL_COLOR_ATTACHMENT12 = 36076;
    public static final int GL_DRAW_BUFFER14 = 34867;
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static final int GL_SHADER_BINARY_FORMATS = 36344;
    public static final int GL_TEXTURE_BINDING_3D = 32874;
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static final int GL_STENCIL_BACK_VALUE_MASK = 36004;
    public static final int GL_DRAW_BUFFER8 = 34861;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 34373;
    public static final int GL_TEXTURE_BORDER_COLOR = 4100;
    public static final int GL_GPU_DISJOINT = 36795;
    public static final int GL_MAX_VARYING_VECTORS = 36348;
    public static final int GL_COLOR_ATTACHMENT9 = 36073;
    public static final int GL_DRAW_BUFFER4 = 34857;
    public static final int GL_INT_10_10_10_2 = 36343;
    public static final int GL_STREAM_DRAW = 35040;
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    public static final int GL_ATTACHED_SHADERS = 35717;
    public static final int GL_LOW_INT = 36339;
    public static final int GL_BLEND_ADVANCED_COHERENT_KHR = 37509;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 34922;
    public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 35722;
    public static final int GL_STENCIL_BACK_WRITEMASK = 36005;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    public static final int GL_STENCIL_BACK_FAIL = 34817;
    public static final int GL_MEDIUM_FLOAT = 36337;
    public static final int GL_SHADER_BINARY_DMP = 37456;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static final int GL_DEBUG_SOURCE_API = 33350;
    public static final int GL_NUM_PROGRAM_BINARY_FORMATS = 34814;
    public static final int GL_SAMPLER_2D = 35678;
    public static final int GL_STENCIL_BACK_REF = 36003;
    public static final int GL_INT = 5124;
    public static final int GL_MEDIUM_INT = 36340;
    public static final int GL_TEXTURE_COMPARE_MODE = 34892;
    public static final int GL_VALIDATE_STATUS = 35715;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static final int GL_COLOR_ATTACHMENT4 = 36068;
    public static final int GL_TIME_ELAPSED = 35007;
    public static final int GL_COLOR_ATTACHMENT10 = 36074;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static final int GL_SAMPLE_MASK_VALUE = 36434;
    public static final int GL_DRAW_BUFFER0 = 34853;
    public static final int GL_SHADING_LANGUAGE_VERSION = 35724;
    public static final int GL_CURRENT_QUERY = 34917;
    public static final int GL_UNSIGNED_NORMALIZED = 35863;
    public static final int GL_TEXTURE_SAMPLES = 37126;
    public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static final int GL_SAMPLER_CUBE = 35680;
    public static final int GL_LOW_FLOAT = 36336;
    public static final int GL_COLOR_ATTACHMENT1 = 36065;
    public static final int GL_DELETE_STATUS = 35712;
    public static final int GL_QUERY_RESULT = 34918;
    public static final int GL_DEBUG_TYPE_OTHER = 33361;
    public static final int GL_COLOR_ATTACHMENT13 = 36077;
    public static final int GL_BLEND_COLOR = 32773;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 35660;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 35719;
    public static final int GL_DRAW_BUFFER13 = 34866;
    public static final int GL_ANY_SAMPLES_PASSED = 35887;
    public static final int GL_DRAW_BUFFER7 = 34860;
    public static final int GL_DRAW_BUFFER3 = 34856;
    public static final int GL_DEBUG_OUTPUT = 37600;
    public static final int GL_COLOR_ATTACHMENT8 = 36072;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    public static final int GL_UNPACK_SKIP_PIXELS = 3316;
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    public static final int GL_PROGRAM_BINARY_LENGTH = 34625;
    public static final int GL_SHADER_COMPILER = 36346;
    public static final int GL_DEBUG_SEVERITY_LOW = 37192;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    public static final int GL_PROGRAM_SEPARABLE = 33368;
    public static final int GL_BOOL = 35670;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 34341;
    public static final int GL_DEBUG_TYPE_ERROR = 33356;
    public static final int GL_UNPACK_ROW_LENGTH = 3314;
    public static final int GL_HIGH_INT = 36341;
    public static final int GL_COLOR_ATTACHMENT3 = 36067;
    public static final long GL_ALL_SHADER_BITS = 4294967295L;
    public static final int GL_MAX_VERTEX_ATTRIBS = 34921;
    public static final int GL_CONSTANT_ALPHA = 32771;
    public static final int GL_SAMPLE_MASK = 36433;
    public static final int GL_MAX_3D_TEXTURE_SIZE = 32883;
    public static final int GL_LINK_STATUS = 35714;
    public static final int GL_SAMPLER = 33510;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 34339;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 35661;
    public static final int GL_UNPACK_SKIP_ROWS = 3315;
    public static final int GL_SAMPLER_2D_SHADOW = 35682;
    public static final int GL_ACTIVE_UNIFORMS = 35718;
    public static final int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    public static final int GL_INFO_LOG_LENGTH = 35716;
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static final int GL_DRAW_BUFFER6 = 34859;
    public static final int GL_DEPTH_COMPONENT = 6402;
    public static final int GL_CONTEXT_ROBUST_ACCESS = 37107;
    public static final int GL_TEXTURE_COMPARE_FUNC = 34893;
    public static final int GL_DRAW_BUFFER12 = 34865;
    public static final int GL_RG = 33319;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static final int GL_TEXTURE_3D = 32879;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    public static final int GL_QUERY_COUNTER_BITS = 34916;
    public static final int GL_PROGRAM = 33506;
    public static final int GL_UNSIGNED_INT_10_10_10_2 = 36342;
    public static final int GL_BOOL_VEC4 = 35673;
    public static final int GL_BOOL_VEC3 = 35672;
    public static final int GL_BOOL_VEC2 = 35671;
    public static final int GL_BUFFER = 33504;
    public static final int GL_SHADER_TYPE = 35663;
    public static final int GL_FRAGMENT_SHADER_BIT = 2;
    public static final int GL_ACTIVE_ATTRIBUTES = 35721;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 34930;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    public static final int GL_INT_VEC4 = 35669;
    public static final int GL_INT_VEC2 = 35667;
    public static final int GL_INT_VEC3 = 35668;
    public static final int GL_DEBUG_TYPE_MARKER = 33384;
    public static final int GL_CONSTANT_COLOR = 32769;
    public static final int GL_QUERY_RESULT_AVAILABLE = 34919;
    public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static final int GL_COLOR_ATTACHMENT14 = 36078;
    public static final int GL_COLOR_ATTACHMENT7 = 36071;
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static final int GL_CLAMP_TO_BORDER = 33069;
    public static final int GL_TEXTURE_WRAP_R = 32882;
    public static final int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    public static final int GL_COLOR_ATTACHMENT6 = 36070;
    public static final int GL_SAMPLE_POSITION = 36432;
    public static final int GL_DRAW_BUFFER2 = 34855;
    public static final int GL_MAX_COLOR_ATTACHMENTS = 36063;
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static final int GL_DRAW_BUFFER9 = 34862;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 36202;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 34340;
    public static final int GL_DRAW_BUFFER11 = 34864;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 34818;
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 35723;
    public static final int GL_COLOR_ATTACHMENT2 = 36066;
    public static final int GL_PROGRAM_PIPELINE_BINDING = 33370;
    public static final int GL_MAX_DRAW_BUFFERS = 34852;
    public static final int GL_FRAGMENT_SHADER = 35632;
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static final int GL_PROGRAM_PIPELINE = 33508;
    public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static final int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static final int GL_HIGH_FLOAT = 36338;
    public static final int GL_COLOR_ATTACHMENT11 = 36075;
    public static final int GL_SAMPLER_3D = 35679;
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    public static final int GL_COLOR_ATTACHMENT15 = 36079;
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static final int GL_PROGRAM_BINARY_FORMATS = 34815;
    public static final int GL_FLOAT_MAT4 = 35676;
    public static final int GL_FLOAT_MAT3 = 35675;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
    public static final int GL_FLOAT_MAT2 = 35674;
    public static final int GL_MAX_INTEGER_SAMPLES = 37136;
    public static final int GL_RED = 6403;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 34975;
    public static final int GL_DRAW_BUFFER5 = 34858;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 34338;
    public static final int GL_DRAW_BUFFER15 = 34868;
    public static final int GL_STENCIL_INDEX = 6401;
    
    void glAttachShader(final int p0, final int p1);
    
    void glBindAttribLocation(final int p0, final int p1, final String p2);
    
    void glBlendColor(final float p0, final float p1, final float p2, final float p3);
    
    void glCompileShader(final int p0);
    
    int glCreateProgram();
    
    int glCreateShader(final int p0);
    
    void glDeleteProgram(final int p0);
    
    void glDeleteShader(final int p0);
    
    void glDetachShader(final int p0, final int p1);
    
    void glDisableVertexAttribArray(final int p0);
    
    void glEnableVertexAttribArray(final int p0);
    
    void glGetActiveAttrib(final int p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final ByteBuffer p6);
    
    void glGetActiveAttrib(final int p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6, final int[] p7, final int p8, final byte[] p9, final int p10);
    
    void glGetActiveUniform(final int p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final ByteBuffer p6);
    
    void glGetActiveUniform(final int p0, final int p1, final int p2, final int[] p3, final int p4, final int[] p5, final int p6, final int[] p7, final int p8, final byte[] p9, final int p10);
    
    void glGetAttachedShaders(final int p0, final int p1, final IntBuffer p2, final IntBuffer p3);
    
    void glGetAttachedShaders(final int p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5);
    
    int glGetAttribLocation(final int p0, final String p1);
    
    void glGetProgramiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetProgramInfoLog(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetProgramInfoLog(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetShaderiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetShaderiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetShaderInfoLog(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetShaderInfoLog(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetShaderSource(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetShaderSource(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetUniformfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetUniformfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetUniformiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetUniformiv(final int p0, final int p1, final int[] p2, final int p3);
    
    int glGetUniformLocation(final int p0, final String p1);
    
    void glGetVertexAttribfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetVertexAttribfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetVertexAttribiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexAttribiv(final int p0, final int p1, final int[] p2, final int p3);
    
    boolean glIsProgram(final int p0);
    
    boolean glIsShader(final int p0);
    
    void glLinkProgram(final int p0);
    
    void glShaderSource(final int p0, final int p1, final String[] p2, final IntBuffer p3);
    
    void glShaderSource(final int p0, final int p1, final String[] p2, final int[] p3, final int p4);
    
    void glStencilFuncSeparate(final int p0, final int p1, final int p2, final int p3);
    
    void glStencilMaskSeparate(final int p0, final int p1);
    
    void glStencilOpSeparate(final int p0, final int p1, final int p2, final int p3);
    
    void glUniform1f(final int p0, final float p1);
    
    void glUniform1fv(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform1fv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform1i(final int p0, final int p1);
    
    void glUniform1iv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform1iv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform2f(final int p0, final float p1, final float p2);
    
    void glUniform2fv(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform2fv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform2i(final int p0, final int p1, final int p2);
    
    void glUniform2iv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform2iv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform3f(final int p0, final float p1, final float p2, final float p3);
    
    void glUniform3fv(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform3fv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform3i(final int p0, final int p1, final int p2, final int p3);
    
    void glUniform3iv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform3iv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniform4f(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glUniform4fv(final int p0, final int p1, final FloatBuffer p2);
    
    void glUniform4fv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glUniform4i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glUniform4iv(final int p0, final int p1, final IntBuffer p2);
    
    void glUniform4iv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glUniformMatrix2fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix2fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix3fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix3fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUniformMatrix4fv(final int p0, final int p1, final boolean p2, final FloatBuffer p3);
    
    void glUniformMatrix4fv(final int p0, final int p1, final boolean p2, final float[] p3, final int p4);
    
    void glUseProgram(final int p0);
    
    void glValidateProgram(final int p0);
    
    void glVertexAttrib1f(final int p0, final float p1);
    
    void glVertexAttrib1fv(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib1fv(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib2f(final int p0, final float p1, final float p2);
    
    void glVertexAttrib2fv(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib2fv(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib3f(final int p0, final float p1, final float p2, final float p3);
    
    void glVertexAttrib3fv(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib3fv(final int p0, final float[] p1, final int p2);
    
    void glVertexAttrib4f(final int p0, final float p1, final float p2, final float p3, final float p4);
    
    void glVertexAttrib4fv(final int p0, final FloatBuffer p1);
    
    void glVertexAttrib4fv(final int p0, final float[] p1, final int p2);
    
    void glVertexAttribPointer(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5);
    
    void glTexImage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void glTexImage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glGetMultisamplefv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetMultisamplefv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glSampleMaski(final int p0, final int p1);
    
    void glDebugMessageControl(final int p0, final int p1, final int p2, final int p3, final IntBuffer p4, final boolean p5);
    
    void glDebugMessageControl(final int p0, final int p1, final int p2, final int p3, final int[] p4, final int p5, final boolean p6);
    
    void glDebugMessageInsert(final int p0, final int p1, final int p2, final int p3, final int p4, final String p5);
    
    int glGetDebugMessageLog(final int p0, final int p1, final IntBuffer p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final IntBuffer p6, final ByteBuffer p7);
    
    int glGetDebugMessageLog(final int p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5, final int[] p6, final int p7, final int[] p8, final int p9, final int[] p10, final int p11, final byte[] p12, final int p13);
    
    void glPushDebugGroup(final int p0, final int p1, final int p2, final ByteBuffer p3);
    
    void glPushDebugGroup(final int p0, final int p1, final int p2, final byte[] p3, final int p4);
    
    void glPopDebugGroup();
    
    void glObjectLabel(final int p0, final int p1, final int p2, final ByteBuffer p3);
    
    void glObjectLabel(final int p0, final int p1, final int p2, final byte[] p3, final int p4);
    
    void glGetObjectLabel(final int p0, final int p1, final int p2, final IntBuffer p3, final ByteBuffer p4);
    
    void glGetObjectLabel(final int p0, final int p1, final int p2, final int[] p3, final int p4, final byte[] p5, final int p6);
    
    void glObjectPtrLabel(final Buffer p0, final int p1, final ByteBuffer p2);
    
    void glObjectPtrLabel(final Buffer p0, final int p1, final byte[] p2, final int p3);
    
    void glGetObjectPtrLabel(final Buffer p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetObjectPtrLabel(final Buffer p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glCopyImageSubData(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14);
    
    void glGetProgramBinary(final int p0, final int p1, final IntBuffer p2, final IntBuffer p3, final Buffer p4);
    
    void glGetProgramBinary(final int p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5, final Buffer p6);
    
    void glProgramBinary(final int p0, final int p1, final Buffer p2, final int p3);
    
    void glTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    void glTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    void glCopyTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glCompressedTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glCompressedTexImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glCompressedTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glCompressedTexSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    void glFramebufferTexture3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glTexParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTexParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glTexParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTexParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTexParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glSamplerParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glSamplerParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glSamplerParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glSamplerParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetSamplerParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetSamplerParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetSamplerParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetSamplerParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glDrawArraysInstancedBaseInstance(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glDrawElementsInstancedBaseInstance(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5);
    
    void glDrawElementsInstancedBaseVertexBaseInstance(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final int p6);
    
    void glGenQueries(final int p0, final IntBuffer p1);
    
    void glGenQueries(final int p0, final int[] p1, final int p2);
    
    void glDeleteQueries(final int p0, final IntBuffer p1);
    
    void glDeleteQueries(final int p0, final int[] p1, final int p2);
    
    boolean glIsQuery(final int p0);
    
    void glBeginQuery(final int p0, final int p1);
    
    void glEndQuery(final int p0);
    
    void glQueryCounter(final int p0, final int p1);
    
    void glGetQueryiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetQueryiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetQueryObjectiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetQueryObjectiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetQueryObjectuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetQueryObjectuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetQueryObjecti64v(final int p0, final int p1, final LongBuffer p2);
    
    void glGetQueryObjecti64v(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetQueryObjectui64v(final int p0, final int p1, final LongBuffer p2);
    
    void glGetQueryObjectui64v(final int p0, final int p1, final long[] p2, final int p3);
    
    void glActiveShaderProgram(final int p0, final int p1);
    
    void glBindProgramPipeline(final int p0);
    
    int glCreateShaderProgramv(final int p0, final int p1, final String[] p2);
    
    void glDeleteProgramPipelines(final int p0, final IntBuffer p1);
    
    void glDeleteProgramPipelines(final int p0, final int[] p1, final int p2);
    
    void glGenProgramPipelines(final int p0, final IntBuffer p1);
    
    void glGenProgramPipelines(final int p0, final int[] p1, final int p2);
    
    void glGetProgramPipelineInfoLog(final int p0, final int p1, final IntBuffer p2, final ByteBuffer p3);
    
    void glGetProgramPipelineInfoLog(final int p0, final int p1, final int[] p2, final int p3, final byte[] p4, final int p5);
    
    void glGetProgramPipelineiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetProgramPipelineiv(final int p0, final int p1, final int[] p2, final int p3);
    
    boolean glIsProgramPipeline(final int p0);
    
    void glProgramParameteri(final int p0, final int p1, final int p2);
    
    void glProgramUniform1f(final int p0, final int p1, final float p2);
    
    void glProgramUniform1fv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramUniform1fv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glProgramUniform1i(final int p0, final int p1, final int p2);
    
    void glProgramUniform1iv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform1iv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform2f(final int p0, final int p1, final float p2, final float p3);
    
    void glProgramUniform2fv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramUniform2fv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glProgramUniform2i(final int p0, final int p1, final int p2, final int p3);
    
    void glProgramUniform2iv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform2iv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform3f(final int p0, final int p1, final float p2, final float p3, final float p4);
    
    void glProgramUniform3fv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramUniform3fv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glProgramUniform3i(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glProgramUniform3iv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform3iv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform4f(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5);
    
    void glProgramUniform4fv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glProgramUniform4fv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glProgramUniform4i(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramUniform4iv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform4iv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniformMatrix2fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix2fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix3fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix3fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix4fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix4fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glUseProgramStages(final int p0, final int p1, final int p2);
    
    void glValidateProgramPipeline(final int p0);
    
    void glProgramUniform1ui(final int p0, final int p1, final int p2);
    
    void glProgramUniform2ui(final int p0, final int p1, final int p2, final int p3);
    
    void glProgramUniform3ui(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glProgramUniform4ui(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glProgramUniform1uiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform1uiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform2uiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform2uiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform3uiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform3uiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniform4uiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glProgramUniform4uiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glProgramUniformMatrix2x3fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix2x3fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix3x2fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix3x2fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix2x4fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix2x4fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix4x2fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix4x2fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix3x4fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix3x4fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glProgramUniformMatrix4x3fv(final int p0, final int p1, final int p2, final boolean p3, final FloatBuffer p4);
    
    void glProgramUniformMatrix4x3fv(final int p0, final int p1, final int p2, final boolean p3, final float[] p4, final int p5);
    
    void glApplyFramebufferAttachmentCMAAINTEL();
    
    void glDrawBuffers(final int p0, final IntBuffer p1);
    
    void glDrawBuffers(final int p0, final int[] p1, final int p2);
    
    void glReleaseShaderCompiler();
    
    void glShaderBinary(final int p0, final IntBuffer p1, final int p2, final Buffer p3, final int p4);
    
    void glShaderBinary(final int p0, final int[] p1, final int p2, final int p3, final Buffer p4, final int p5);
    
    void glGetShaderPrecisionFormat(final int p0, final int p1, final IntBuffer p2, final IntBuffer p3);
    
    void glGetShaderPrecisionFormat(final int p0, final int p1, final int[] p2, final int p3, final int[] p4, final int p5);
    
    void glDepthRangef(final float p0, final float p1);
    
    void glDepthRange(final double p0, final double p1);
    
    void glClearDepthf(final float p0);
    
    void glClearDepth(final double p0);
    
    void glVertexAttribPointer(final GLArrayData p0);
    
    void glUniform(final GLUniformData p0);
}
