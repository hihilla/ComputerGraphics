// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import java.nio.IntBuffer;

public interface GLXExt
{
    public static final int GLX_BIND_TO_TEXTURE_TARGETS_EXT = 8403;
    public static final int GLX_SYNC_FRAME_SGIX = 0;
    public static final int GLX_BACK_LEFT_EXT = 8416;
    public static final int GLX_GPU_CLOCK_AMD = 8612;
    public static final int GLX_RGBA_TYPE_SGIX = 32788;
    public static final int GLX_NUM_VIDEO_SLOTS_NV = 8432;
    public static final int GLX_COLOR_SAMPLES_NV = 8371;
    public static final int GLX_GPU_NUM_SPI_AMD = 8616;
    public static final int GLX_VIDEO_OUT_FIELD_2_NV = 8394;
    public static final int GLX_VIDEO_OUT_STACKED_FIELDS_1_2_NV = 8395;
    public static final int GLX_COVERAGE_SAMPLES_NV = 100001;
    public static final int GLX_MIPMAP_TEXTURE_EXT = 8407;
    public static final int GLX_BLENDED_RGBA_SGIS = 32805;
    public static final int GLX_WIDTH_SGIX = 32797;
    public static final int GLX_SAMPLES_SGIS = 100001;
    public static final int GLX_LARGEST_PBUFFER_SGIX = 32796;
    public static final int GLX_AUX_BUFFERS_BIT_SGIX = 16;
    public static final int GLX_AUX7_EXT = 8425;
    public static final int GLX_VIDEO_OUT_ALPHA_NV = 8388;
    public static final int GLX_TEXTURE_2D_EXT = 8412;
    public static final int GLX_HEIGHT_SGIX = 32798;
    public static final int GLX_DEVICE_ID_NV = 8397;
    public static final int GLX_RENDERER_VIDEO_MEMORY_MESA = 33159;
    public static final int GLX_VIDEO_OUT_COLOR_AND_DEPTH_NV = 8391;
    public static final int GLX_FBCONFIG_ID_SGIX = 32787;
    public static final int GLX_VIDEO_OUT_FRAME_NV = 8392;
    public static final int GLX_FRONT_LEFT_BUFFER_BIT_SGIX = 1;
    public static final int GLX_BAD_HYPERPIPE_SGIX = 92;
    public static final int GLX_TEXTURE_FORMAT_RGBA_EXT = 8410;
    public static final int GLX_VIDEO_OUT_COLOR_NV = 8387;
    public static final int GLX_3DFX_WINDOW_MODE_MESA = 1;
    public static final int GLX_Y_INVERTED_EXT = 8404;
    public static final int GLX_VIDEO_OUT_FIELD_1_NV = 8393;
    public static final int GLX_RENDERER_OPENGL_COMPATIBILITY_PROFILE_VERSION_MESA = 33163;
    public static final int GLX_EVENT_MASK_SGIX = 32799;
    public static final int GLX_BACK_LEFT_BUFFER_BIT_SGIX = 4;
    public static final int GLX_OPTIMAL_PBUFFER_WIDTH_SGIX = 32793;
    public static final int GLX_TEXTURE_2D_BIT_EXT = 2;
    public static final int GLX_AUX2_EXT = 8420;
    public static final int GLX_MAX_PBUFFER_PIXELS_SGIX = 32792;
    public static final int GLX_WINDOW_BIT_SGIX = 1;
    public static final int GLX_PBUFFER_SGIX = 32803;
    public static final int GLX_DEPTH_BUFFER_BIT_SGIX = 32;
    public static final int GLX_ACCUM_BUFFER_BIT_SGIX = 128;
    public static final int GLX_RENDERER_ACCELERATED_MESA = 33158;
    public static final int GLX_GPU_RAM_AMD = 8611;
    public static final int GLX_SAVED_SGIX = 32801;
    public static final int GLX_WINDOW_SGIX = 32802;
    public static final int GLX_BACK_EXT = 8416;
    public static final int GLX_TEXTURE_RECTANGLE_BIT_EXT = 4;
    public static final int GLX_MULTISAMPLE_SUB_RECT_HEIGHT_SGIS = 32807;
    public static final int GLX_X_RENDERABLE_SGIX = 32786;
    public static final int GLX_HYPERPIPE_PIPE_NAME_LENGTH_SGIX = 80;
    public static final int GLX_RENDER_TYPE_SGIX = 32785;
    public static final int GLX_VIDEO_OUT_DEPTH_NV = 8389;
    public static final int GLX_MAX_PBUFFER_HEIGHT_SGIX = 32791;
    public static final int GLX_GPU_OPENGL_VERSION_STRING_AMD = 7938;
    public static final int GLX_BUFFER_CLOBBER_MASK_SGIX = 134217728;
    public static final int GLX_TEXTURE_FORMAT_NONE_EXT = 8408;
    public static final int GLX_AUX8_EXT = 8426;
    public static final int GLX_3DFX_FULLSCREEN_MODE_MESA = 2;
    public static final int GLX_HYPERPIPE_DISPLAY_PIPE_SGIX = 1;
    public static final int GLX_VISUAL_SELECT_GROUP_SGIX = 32808;
    public static final int GLX_RGBA_BIT_SGIX = 1;
    public static final int GLX_COLOR_INDEX_TYPE_SGIX = 32789;
    public static final int GLX_FRONT_LEFT_EXT = 8414;
    public static final int GLX_VIDEO_OUT_COLOR_AND_ALPHA_NV = 8390;
    public static final int GLX_FLOAT_COMPONENTS_NV = 8368;
    public static final int GLX_AUX3_EXT = 8421;
    public static final int GLX_SWAP_EXCHANGE_OML = 32865;
    public static final int GLX_SWAP_METHOD_OML = 32864;
    public static final int GLX_RENDERER_UNIFIED_MEMORY_ARCHITECTURE_MESA = 33160;
    public static final int GLX_TEXTURE_RECTANGLE_EXT = 8413;
    public static final int GLX_MAX_PBUFFER_WIDTH_SGIX = 32790;
    public static final int GLX_HYPERPIPE_ID_SGIX = 32816;
    public static final int GLX_AUX1_EXT = 8419;
    public static final int GLX_HYPERPIPE_PIXEL_AVERAGE_SGIX = 4;
    public static final int GLX_TEXTURE_1D_BIT_EXT = 1;
    public static final int GLX_MULTISAMPLE_SUB_RECT_WIDTH_SGIS = 32806;
    public static final int GLX_DRAWABLE_TYPE_SGIX = 32784;
    public static final int GLX_FRONT_EXT = 8414;
    public static final int GLX_OPTIMAL_PBUFFER_HEIGHT_SGIX = 32794;
    public static final int GLX_RENDERER_PREFERRED_PROFILE_MESA = 33161;
    public static final int GLX_PIXMAP_BIT_SGIX = 2;
    public static final int GLX_RENDERER_VERSION_MESA = 33157;
    public static final int GLX_FRONT_RIGHT_BUFFER_BIT_SGIX = 2;
    public static final int GLX_GPU_VENDOR_AMD = 7936;
    public static final int GLX_TEXTURE_TARGET_EXT = 8406;
    public static final int GLX_PIPE_RECT_SGIX = 1;
    public static final int GLX_BIND_TO_MIPMAP_TEXTURE_EXT = 8402;
    public static final int GLX_RENDERER_VENDOR_ID_MESA = 33155;
    public static final int GLX_DAMAGED_SGIX = 32800;
    public static final int GLX_AUX4_EXT = 8422;
    public static final int GLX_AUX9_EXT = 8427;
    public static final int GLX_COLOR_INDEX_BIT_SGIX = 2;
    public static final int GLX_RENDERER_OPENGL_ES2_PROFILE_VERSION_MESA = 33165;
    public static final int GLX_GPU_RENDERER_STRING_AMD = 7937;
    public static final int GLX_TEXTURE_FORMAT_RGB_EXT = 8409;
    public static final int GLX_SAMPLE_BUFFERS_BIT_SGIX = 256;
    public static final int GLX_SYNC_SWAP_SGIX = 1;
    public static final int GLX_GPU_NUM_RB_AMD = 8615;
    public static final int GLX_PRESERVED_CONTENTS_SGIX = 32795;
    public static final int GLX_GPU_NUM_PIPES_AMD = 8613;
    public static final int GLX_BIND_TO_TEXTURE_RGBA_EXT = 8401;
    public static final int GLX_NUM_VIDEO_CAPTURE_SLOTS_NV = 8399;
    public static final int GLX_GPU_NUM_SIMD_AMD = 8614;
    public static final int GLX_BACK_RIGHT_BUFFER_BIT_SGIX = 8;
    public static final int GLX_AUX0_EXT = 8418;
    public static final int GLX_SWAP_COPY_OML = 32866;
    public static final int GLX_HYPERPIPE_RENDER_PIPE_SGIX = 2;
    public static final int GLX_VIDEO_OUT_STACKED_FIELDS_2_1_NV = 8396;
    public static final int GLX_SWAP_UNDEFINED_OML = 32867;
    public static final int GLX_PIPE_RECT_LIMITS_SGIX = 2;
    public static final int GLX_AUX6_EXT = 8424;
    public static final int GLX_FRONT_RIGHT_EXT = 8415;
    public static final int GLX_RENDERER_DEVICE_ID_MESA = 33156;
    public static final int GLX_PBUFFER_BIT_SGIX = 4;
    public static final int GLX_AUX5_EXT = 8423;
    public static final int GLX_BACK_RIGHT_EXT = 8417;
    public static final int GLX_RENDERER_OPENGL_ES_PROFILE_VERSION_MESA = 33164;
    public static final int GLX_UNIQUE_ID_NV = 8398;
    public static final int GLX_SAMPLE_BUFFERS_SGIS = 100000;
    public static final int GLX_STENCIL_BUFFER_BIT_SGIX = 64;
    public static final int GLX_BIND_TO_TEXTURE_RGB_EXT = 8400;
    public static final int GLX_BAD_HYPERPIPE_CONFIG_SGIX = 91;
    public static final int GLX_TEXTURE_FORMAT_EXT = 8405;
    public static final int GLX_RENDERER_ID_MESA = 33166;
    public static final int GLX_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
    public static final int GLX_HYPERPIPE_STEREO_SGIX = 3;
    public static final int GLX_TEXTURE_1D_EXT = 8411;
    public static final int GLX_RENDERER_OPENGL_CORE_PROFILE_VERSION_MESA = 33162;
    
    long glXCreateContextAttribsARB(final long p0, final long p1, final long p2, final boolean p3, final IntBuffer p4);
    
    int glXGetGPUIDsAMD(final int p0, final IntBuffer p1);
    
    int glXGetGPUInfoAMD(final int p0, final int p1, final int p2, final int p3, final Buffer p4);
    
    int glXGetContextGPUIDAMD(final long p0);
    
    long glXCreateAssociatedContextAMD(final int p0, final long p1);
    
    long glXCreateAssociatedContextAttribsAMD(final int p0, final long p1, final IntBuffer p2);
    
    boolean glXDeleteAssociatedContextAMD(final long p0);
    
    boolean glXMakeAssociatedContextCurrentAMD(final long p0);
    
    long glXGetCurrentAssociatedContextAMD();
    
    void glXBlitContextFramebufferAMD(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    long glXGetCurrentDisplayEXT();
    
    int glXQueryContextInfoEXT(final long p0, final long p1, final int p2, final IntBuffer p3);
    
    long glXGetContextIDEXT(final long p0);
    
    long glXImportContextEXT(final long p0, final long p1);
    
    void glXFreeContextEXT(final long p0, final long p1);
    
    void glXBindTexImageEXT(final long p0, final long p1, final int p2, final IntBuffer p3);
    
    void glXReleaseTexImageEXT(final long p0, final long p1, final int p2);
    
    int glXGetAGPOffsetMESA(final Buffer p0);
    
    void glXCopySubBufferMESA(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5);
    
    boolean glXQueryCurrentRendererIntegerMESA(final int p0, final IntBuffer p1);
    
    ByteBuffer glXQueryCurrentRendererStringMESA(final int p0);
    
    boolean glXQueryRendererIntegerMESA(final long p0, final int p1, final int p2, final int p3, final IntBuffer p4);
    
    ByteBuffer glXQueryRendererStringMESA(final long p0, final int p1, final int p2, final int p3);
    
    boolean glXReleaseBuffersMESA(final long p0, final long p1);
    
    boolean glXSet3DfxModeMESA(final int p0);
    
    void glXCopyBufferSubDataNV(final long p0, final long p1, final long p2, final int p3, final int p4, final long p5, final long p6, final long p7);
    
    void glXNamedCopyBufferSubDataNV(final long p0, final long p1, final long p2, final int p3, final int p4, final long p5, final long p6, final long p7);
    
    void glXCopyImageSubDataNV(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final int p15, final int p16, final int p17);
    
    boolean glXDelayBeforeSwapNV(final long p0, final long p1, final float p2);
    
    IntBuffer glXEnumerateVideoDevicesNV(final long p0, final int p1, final IntBuffer p2);
    
    int glXBindVideoDeviceNV(final long p0, final int p1, final int p2, final IntBuffer p3);
    
    boolean glXJoinSwapGroupNV(final long p0, final long p1, final int p2);
    
    boolean glXBindSwapBarrierNV(final long p0, final int p1, final int p2);
    
    boolean glXQuerySwapGroupNV(final long p0, final long p1, final IntBuffer p2, final IntBuffer p3);
    
    boolean glXQueryMaxSwapGroupsNV(final long p0, final int p1, final IntBuffer p2, final IntBuffer p3);
    
    boolean glXQueryFrameCountNV(final long p0, final int p1, final IntBuffer p2);
    
    boolean glXResetFrameCountNV(final long p0, final int p1);
    
    int glXBindVideoCaptureDeviceNV(final long p0, final int p1, final long p2);
    
    PointerBuffer glXEnumerateVideoCaptureDevicesNV(final long p0, final int p1, final IntBuffer p2);
    
    void glXLockVideoCaptureDeviceNV(final long p0, final long p1);
    
    int glXQueryVideoCaptureDeviceNV(final long p0, final long p1, final int p2, final IntBuffer p3);
    
    void glXReleaseVideoCaptureDeviceNV(final long p0, final long p1);
    
    int glXGetVideoDeviceNV(final long p0, final int p1, final int p2, final IntBuffer p3);
    
    int glXReleaseVideoDeviceNV(final long p0, final int p1, final int p2);
    
    int glXBindVideoImageNV(final long p0, final int p1, final long p2, final int p3);
    
    int glXReleaseVideoImageNV(final long p0, final long p1);
    
    int glXSendPbufferToVideoNV(final long p0, final long p1, final int p2, final LongBuffer p3, final boolean p4);
    
    int glXGetVideoInfoNV(final long p0, final int p1, final int p2, final LongBuffer p3, final LongBuffer p4);
    
    boolean glXGetSyncValuesOML(final long p0, final long p1, final LongBuffer p2, final LongBuffer p3, final LongBuffer p4);
    
    boolean glXGetMscRateOML(final long p0, final long p1, final IntBuffer p2, final IntBuffer p3);
    
    long glXSwapBuffersMscOML(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    boolean glXWaitForMscOML(final long p0, final long p1, final long p2, final long p3, final long p4, final LongBuffer p5, final LongBuffer p6, final LongBuffer p7);
    
    boolean glXWaitForSbcOML(final long p0, final long p1, final long p2, final LongBuffer p3, final LongBuffer p4, final LongBuffer p5);
    
    void glXBindSwapBarrierSGIX(final long p0, final long p1, final int p2);
    
    boolean glXQueryMaxSwapBarriersSGIX(final long p0, final int p1, final IntBuffer p2);
    
    void glXJoinSwapGroupSGIX(final long p0, final long p1, final long p2);
    
    int glXBindChannelToWindowSGIX(final long p0, final int p1, final int p2, final long p3);
    
    int glXChannelRectSGIX(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    int glXQueryChannelRectSGIX(final long p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final IntBuffer p6);
    
    int glXQueryChannelDeltasSGIX(final long p0, final int p1, final int p2, final IntBuffer p3, final IntBuffer p4, final IntBuffer p5, final IntBuffer p6);
    
    int glXChannelRectSyncSGIX(final long p0, final int p1, final int p2, final int p3);
    
    void glXCushionSGI(final long p0, final long p1, final float p2);
    
    boolean glXMakeCurrentReadSGI(final long p0, final long p1, final long p2, final long p3);
    
    long glXGetCurrentReadDrawableSGI();
    
    int glXSwapIntervalSGI(final int p0);
    
    int glXGetVideoSyncSGI(final IntBuffer p0);
    
    int glXWaitVideoSyncSGI(final int p0, final int p1, final IntBuffer p2);
    
    int glXGetTransparentIndexSUN(final long p0, final long p1, final long p2, final LongBuffer p3);
    
    boolean isFunctionAvailable(final String p0);
    
    boolean isExtensionAvailable(final String p0);
}
