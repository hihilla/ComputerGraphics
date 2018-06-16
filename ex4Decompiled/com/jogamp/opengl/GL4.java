// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public interface GL4 extends GL4ES3, GL3
{
    public static final int GL_MAP_PERSISTENT_BIT = 64;
    public static final int GL_UNSIGNED_INT64_ARB = 5135;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 37101;
    public static final int GL_QUERY_TARGET = 33514;
    public static final int GL_PARAMETER_BUFFER_ARB = 33006;
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM = 36492;
    public static final int GL_QUERY_BUFFER = 37266;
    public static final int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    public static final int GL_PARAMETER_BUFFER_BINDING_ARB = 33007;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR = 33531;
    public static final int GL_VERTEX_ATTRIB_ARRAY_LONG = 34638;
    public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH = 33532;
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT = 36494;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 37100;
    public static final int GL_DYNAMIC_STORAGE_BIT = 256;
    public static final int GL_CLIP_ORIGIN = 37724;
    public static final int GL_CLIP_DEPTH_MODE = 37725;
    public static final int GL_MIRROR_CLAMP_TO_EDGE = 34627;
    public static final int GL_CLEAR_TEXTURE = 37733;
    public static final int GL_QUERY_BUFFER_BARRIER_BIT = 32768;
    public static final int GL_MAX_CULL_DISTANCES = 33529;
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_SIZE_ARB = 37311;
    public static final int GL_NUM_SHADING_LANGUAGE_VERSIONS = 33513;
    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_INVOCATIONS_ARB = 37700;
    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
    public static final int GL_NEGATIVE_ONE_TO_ONE = 37726;
    public static final int GL_FRAGMENT_SUBROUTINE = 37612;
    public static final int GL_QUERY_RESULT_NO_WAIT = 37268;
    public static final int GL_COMPUTE_SUBROUTINE = 37613;
    public static final int GL_VERTEX_SUBROUTINE = 37608;
    public static final int GL_ZERO_TO_ONE = 37727;
    public static final int GL_QUERY_WAIT_INVERTED = 36375;
    public static final int GL_QUERY_BY_REGION_WAIT_INVERTED = 36377;
    public static final int GL_MAP_COHERENT_BIT = 128;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 37707;
    public static final int GL_TESS_CONTROL_SUBROUTINE = 37609;
    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_SIZE_ARB = 37701;
    public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = 36495;
    public static final int GL_QUERY_BUFFER_BINDING = 37267;
    public static final int GL_GEOMETRY_SUBROUTINE = 37611;
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_INVOCATIONS_ARB = 37099;
    public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static final int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    public static final int GL_TEXTURE_TARGET = 4102;
    public static final int GL_CLIENT_STORAGE_BIT = 512;
    public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    public static final int GL_LOCATION_INDEX = 37647;
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM = 36493;
    public static final int GL_QUERY_NO_WAIT_INVERTED = 36376;
    public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    public static final int GL_QUERY_BY_REGION_NO_WAIT_INVERTED = 36378;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 37708;
    public static final int GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES = 33530;
    public static final int GL_LOCATION_COMPONENT = 37706;
    public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    
    int glGetProgramResourceLocationIndex(final int p0, final int p1, final ByteBuffer p2);
    
    int glGetProgramResourceLocationIndex(final int p0, final int p1, final byte[] p2, final int p3);
    
    void glShaderStorageBlockBinding(final int p0, final int p1, final int p2);
    
    void glTextureView(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glVertexAttribLFormat(final int p0, final int p1, final int p2, final int p3);
    
    void glBufferStorage(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glClearTexImage(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glClearTexSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glBindBuffersBase(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glBindBuffersBase(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glBindBuffersRange(final int p0, final int p1, final int p2, final IntBuffer p3, final PointerBuffer p4, final PointerBuffer p5);
    
    void glBindBuffersRange(final int p0, final int p1, final int p2, final int[] p3, final int p4, final PointerBuffer p5, final PointerBuffer p6);
    
    void glBindTextures(final int p0, final int p1, final IntBuffer p2);
    
    void glBindTextures(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBindSamplers(final int p0, final int p1, final IntBuffer p2);
    
    void glBindSamplers(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBindImageTextures(final int p0, final int p1, final IntBuffer p2);
    
    void glBindImageTextures(final int p0, final int p1, final int[] p2, final int p3);
    
    void glBindVertexBuffers(final int p0, final int p1, final IntBuffer p2, final PointerBuffer p3, final IntBuffer p4);
    
    void glBindVertexBuffers(final int p0, final int p1, final int[] p2, final int p3, final PointerBuffer p4, final int[] p5, final int p6);
    
    void glClipControl(final int p0, final int p1);
    
    void glCreateTransformFeedbacks(final int p0, final IntBuffer p1);
    
    void glCreateTransformFeedbacks(final int p0, final int[] p1, final int p2);
    
    void glTransformFeedbackBufferBase(final int p0, final int p1, final int p2);
    
    void glTransformFeedbackBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    void glGetTransformFeedbackiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTransformFeedbackiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTransformFeedbacki_v(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTransformFeedbacki_v(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetTransformFeedbacki64_v(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glGetTransformFeedbacki64_v(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glCreateBuffers(final int p0, final IntBuffer p1);
    
    void glCreateBuffers(final int p0, final int[] p1, final int p2);
    
    void glNamedBufferStorage(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glNamedBufferData(final int p0, final long p1, final Buffer p2, final int p3);
    
    void glNamedBufferSubData(final int p0, final long p1, final long p2, final Buffer p3);
    
    void glCopyNamedBufferSubData(final int p0, final int p1, final long p2, final long p3, final long p4);
    
    void glClearNamedBufferData(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    void glClearNamedBufferSubData(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5, final Buffer p6);
    
    ByteBuffer glMapNamedBuffer(final int p0, final int p1);
    
    ByteBuffer glMapNamedBufferRange(final int p0, final long p1, final long p2, final int p3);
    
    boolean glUnmapNamedBuffer(final int p0);
    
    void glFlushMappedNamedBufferRange(final int p0, final long p1, final long p2);
    
    void glGetNamedBufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedBufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetNamedBufferParameteri64v(final int p0, final int p1, final LongBuffer p2);
    
    void glGetNamedBufferParameteri64v(final int p0, final int p1, final long[] p2, final int p3);
    
    void glGetNamedBufferPointerv(final int p0, final int p1, final PointerBuffer p2);
    
    void glGetNamedBufferSubData(final int p0, final long p1, final long p2, final Buffer p3);
    
    void glCreateFramebuffers(final int p0, final IntBuffer p1);
    
    void glCreateFramebuffers(final int p0, final int[] p1, final int p2);
    
    void glNamedFramebufferRenderbuffer(final int p0, final int p1, final int p2, final int p3);
    
    void glNamedFramebufferParameteri(final int p0, final int p1, final int p2);
    
    void glNamedFramebufferTexture(final int p0, final int p1, final int p2, final int p3);
    
    void glNamedFramebufferTextureLayer(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glNamedFramebufferDrawBuffer(final int p0, final int p1);
    
    void glNamedFramebufferDrawBuffers(final int p0, final int p1, final IntBuffer p2);
    
    void glNamedFramebufferDrawBuffers(final int p0, final int p1, final int[] p2, final int p3);
    
    void glNamedFramebufferReadBuffer(final int p0, final int p1);
    
    void glInvalidateNamedFramebufferData(final int p0, final int p1, final IntBuffer p2);
    
    void glInvalidateNamedFramebufferData(final int p0, final int p1, final int[] p2, final int p3);
    
    void glInvalidateNamedFramebufferSubData(final int p0, final int p1, final IntBuffer p2, final int p3, final int p4, final int p5, final int p6);
    
    void glInvalidateNamedFramebufferSubData(final int p0, final int p1, final int[] p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glClearNamedFramebufferiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glClearNamedFramebufferiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glClearNamedFramebufferuiv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glClearNamedFramebufferuiv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glClearNamedFramebufferfv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glClearNamedFramebufferfv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glClearNamedFramebufferfi(final int p0, final int p1, final float p2, final int p3);
    
    void glBlitNamedFramebuffer(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11);
    
    int glCheckNamedFramebufferStatus(final int p0, final int p1);
    
    void glGetNamedFramebufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedFramebufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetNamedFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetNamedFramebufferAttachmentParameteriv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glCreateRenderbuffers(final int p0, final IntBuffer p1);
    
    void glCreateRenderbuffers(final int p0, final int[] p1, final int p2);
    
    void glNamedRenderbufferStorage(final int p0, final int p1, final int p2, final int p3);
    
    void glNamedRenderbufferStorageMultisample(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glGetNamedRenderbufferParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetNamedRenderbufferParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glCreateTextures(final int p0, final int p1, final IntBuffer p2);
    
    void glCreateTextures(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTextureBuffer(final int p0, final int p1, final int p2);
    
    void glTextureBufferRange(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    void glTextureStorage1D(final int p0, final int p1, final int p2, final int p3);
    
    void glTextureStorage2D(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glTextureStorage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glTextureStorage2DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void glTextureStorage3DMultisample(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6);
    
    void glTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    void glTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    void glTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    void glCompressedTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Buffer p6);
    
    void glCompressedTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Buffer p8);
    
    void glCompressedTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Buffer p10);
    
    void glCopyTextureSubImage1D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glCopyTextureSubImage2D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    void glCopyTextureSubImage3D(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8);
    
    void glTextureParameterf(final int p0, final int p1, final float p2);
    
    void glTextureParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glTextureParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glTextureParameteri(final int p0, final int p1, final int p2);
    
    void glTextureParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glTextureParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTextureParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glTextureParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glTextureParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glTextureParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGenerateTextureMipmap(final int p0);
    
    void glBindTextureUnit(final int p0, final int p1);
    
    void glGetTextureImage(final int p0, final int p1, final int p2, final int p3, final int p4, final Buffer p5);
    
    void glGetCompressedTextureImage(final int p0, final int p1, final int p2, final Buffer p3);
    
    void glGetTextureLevelParameterfv(final int p0, final int p1, final int p2, final FloatBuffer p3);
    
    void glGetTextureLevelParameterfv(final int p0, final int p1, final int p2, final float[] p3, final int p4);
    
    void glGetTextureLevelParameteriv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetTextureLevelParameteriv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetTextureParameterfv(final int p0, final int p1, final FloatBuffer p2);
    
    void glGetTextureParameterfv(final int p0, final int p1, final float[] p2, final int p3);
    
    void glGetTextureParameterIiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTextureParameterIiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTextureParameterIuiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTextureParameterIuiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetTextureParameteriv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetTextureParameteriv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glCreateVertexArrays(final int p0, final IntBuffer p1);
    
    void glCreateVertexArrays(final int p0, final int[] p1, final int p2);
    
    void glDisableVertexArrayAttrib(final int p0, final int p1);
    
    void glEnableVertexArrayAttrib(final int p0, final int p1);
    
    void glVertexArrayElementBuffer(final int p0, final int p1);
    
    void glVertexArrayVertexBuffer(final int p0, final int p1, final int p2, final long p3, final int p4);
    
    void glVertexArrayVertexBuffers(final int p0, final int p1, final int p2, final IntBuffer p3, final PointerBuffer p4, final IntBuffer p5);
    
    void glVertexArrayVertexBuffers(final int p0, final int p1, final int p2, final int[] p3, final int p4, final PointerBuffer p5, final int[] p6, final int p7);
    
    void glVertexArrayAttribBinding(final int p0, final int p1, final int p2);
    
    void glVertexArrayAttribFormat(final int p0, final int p1, final int p2, final int p3, final boolean p4, final int p5);
    
    void glVertexArrayAttribIFormat(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexArrayAttribLFormat(final int p0, final int p1, final int p2, final int p3, final int p4);
    
    void glVertexArrayBindingDivisor(final int p0, final int p1, final int p2);
    
    void glGetVertexArrayiv(final int p0, final int p1, final IntBuffer p2);
    
    void glGetVertexArrayiv(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetVertexArrayIndexediv(final int p0, final int p1, final int p2, final IntBuffer p3);
    
    void glGetVertexArrayIndexediv(final int p0, final int p1, final int p2, final int[] p3, final int p4);
    
    void glGetVertexArrayIndexed64iv(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glGetVertexArrayIndexed64iv(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    void glCreateSamplers(final int p0, final IntBuffer p1);
    
    void glCreateSamplers(final int p0, final int[] p1, final int p2);
    
    void glCreateProgramPipelines(final int p0, final IntBuffer p1);
    
    void glCreateProgramPipelines(final int p0, final int[] p1, final int p2);
    
    void glCreateQueries(final int p0, final int p1, final IntBuffer p2);
    
    void glCreateQueries(final int p0, final int p1, final int[] p2, final int p3);
    
    void glGetQueryBufferObjecti64v(final int p0, final int p1, final int p2, final long p3);
    
    void glGetQueryBufferObjectiv(final int p0, final int p1, final int p2, final long p3);
    
    void glGetQueryBufferObjectui64v(final int p0, final int p1, final int p2, final long p3);
    
    void glGetQueryBufferObjectuiv(final int p0, final int p1, final int p2, final long p3);
    
    void glGetTextureSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final Buffer p11);
    
    void glGetCompressedTextureSubImage(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Buffer p9);
    
    void glTextureBarrier();
    
    long glGetTextureHandleARB(final int p0);
    
    long glGetTextureSamplerHandleARB(final int p0, final int p1);
    
    void glMakeTextureHandleResidentARB(final long p0);
    
    void glMakeTextureHandleNonResidentARB(final long p0);
    
    long glGetImageHandleARB(final int p0, final int p1, final boolean p2, final int p3, final int p4);
    
    void glMakeImageHandleResidentARB(final long p0, final int p1);
    
    void glMakeImageHandleNonResidentARB(final long p0);
    
    void glUniformHandleui64ARB(final int p0, final long p1);
    
    void glUniformHandleui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glUniformHandleui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glProgramUniformHandleui64ARB(final int p0, final int p1, final long p2);
    
    void glProgramUniformHandleui64vARB(final int p0, final int p1, final int p2, final LongBuffer p3);
    
    void glProgramUniformHandleui64vARB(final int p0, final int p1, final int p2, final long[] p3, final int p4);
    
    boolean glIsTextureHandleResidentARB(final long p0);
    
    boolean glIsImageHandleResidentARB(final long p0);
    
    void glVertexAttribL1ui64ARB(final int p0, final long p1);
    
    void glVertexAttribL1ui64vARB(final int p0, final LongBuffer p1);
    
    void glVertexAttribL1ui64vARB(final int p0, final long[] p1, final int p2);
    
    void glGetVertexAttribLui64vARB(final int p0, final int p1, final LongBuffer p2);
    
    void glGetVertexAttribLui64vARB(final int p0, final int p1, final long[] p2, final int p3);
    
    void glDispatchComputeGroupSizeARB(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    void glMultiDrawArraysIndirectCountARB(final int p0, final long p1, final long p2, final int p3, final int p4);
    
    void glMultiDrawElementsIndirectCountARB(final int p0, final int p1, final long p2, final long p3, final int p4, final int p5);
    
    GLBufferStorage mapNamedBuffer(final int p0, final int p1) throws GLException;
    
    GLBufferStorage mapNamedBufferRange(final int p0, final long p1, final long p2, final int p3) throws GLException;
}
