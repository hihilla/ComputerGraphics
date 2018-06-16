// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.macosx.cgl;

import java.nio.FloatBuffer;
import com.jogamp.opengl.GLException;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;

public class CGL
{
    public static final int kCGLPFAAllRenderers = 1;
    public static final int kCGLPFADoubleBuffer = 5;
    public static final int kCGLPFAStereo = 6;
    public static final int kCGLPFAAuxBuffers = 7;
    public static final int kCGLPFAColorSize = 8;
    public static final int kCGLPFAAlphaSize = 11;
    public static final int kCGLPFADepthSize = 12;
    public static final int kCGLPFAStencilSize = 13;
    public static final int kCGLPFAAccumSize = 14;
    public static final int kCGLPFAMinimumPolicy = 51;
    public static final int kCGLPFAMaximumPolicy = 52;
    public static final int kCGLPFAOffScreen = 53;
    public static final int kCGLPFAFullScreen = 54;
    public static final int kCGLPFASampleBuffers = 55;
    public static final int kCGLPFASamples = 56;
    public static final int kCGLPFAAuxDepthStencil = 57;
    public static final int kCGLPFAColorFloat = 58;
    public static final int kCGLPFAMultisample = 59;
    public static final int kCGLPFASupersample = 60;
    public static final int kCGLPFASampleAlpha = 61;
    public static final int kCGLPFARendererID = 70;
    public static final int kCGLPFASingleRenderer = 71;
    public static final int kCGLPFANoRecovery = 72;
    public static final int kCGLPFAAccelerated = 73;
    public static final int kCGLPFAClosestPolicy = 74;
    public static final int kCGLPFARobust = 75;
    public static final int kCGLPFABackingStore = 76;
    public static final int kCGLPFAMPSafe = 78;
    public static final int kCGLPFAWindow = 80;
    public static final int kCGLPFAMultiScreen = 81;
    public static final int kCGLPFACompliant = 83;
    public static final int kCGLPFADisplayMask = 84;
    public static final int kCGLPFAPBuffer = 90;
    public static final int kCGLPFARemotePBuffer = 91;
    public static final int kCGLPFAAcceleratedCompute = 97;
    public static final int kCGLPFAOpenGLProfile = 99;
    public static final int kCGLPFAVirtualScreenCount = 128;
    public static final int kCGLOGLPVersion_Legacy = 4096;
    public static final int kCGLOGLPVersion_GL3_Core = 12800;
    public static final int kCGLOGLPVersion_GL4_Core = 16640;
    public static final int NSOpenGLPFAAllRenderers = 1;
    public static final int NSOpenGLPFADoubleBuffer = 5;
    public static final int NSOpenGLPFAStereo = 6;
    public static final int NSOpenGLPFAAuxBuffers = 7;
    public static final int NSOpenGLPFAColorSize = 8;
    public static final int NSOpenGLPFAAlphaSize = 11;
    public static final int NSOpenGLPFADepthSize = 12;
    public static final int NSOpenGLPFAStencilSize = 13;
    public static final int NSOpenGLPFAAccumSize = 14;
    public static final int NSOpenGLPFAMinimumPolicy = 51;
    public static final int NSOpenGLPFAMaximumPolicy = 52;
    public static final int NSOpenGLPFAOffScreen = 53;
    public static final int NSOpenGLPFAFullScreen = 54;
    public static final int NSOpenGLPFASampleBuffers = 55;
    public static final int NSOpenGLPFASamples = 56;
    public static final int NSOpenGLPFAAuxDepthStencil = 57;
    public static final int NSOpenGLPFAColorFloat = 58;
    public static final int NSOpenGLPFAMultisample = 59;
    public static final int NSOpenGLPFASupersample = 60;
    public static final int NSOpenGLPFASampleAlpha = 61;
    public static final int NSOpenGLPFARendererID = 70;
    public static final int NSOpenGLPFASingleRenderer = 71;
    public static final int NSOpenGLPFANoRecovery = 72;
    public static final int NSOpenGLPFAAccelerated = 73;
    public static final int NSOpenGLPFAClosestPolicy = 74;
    public static final int NSOpenGLPFARobust = 75;
    public static final int NSOpenGLPFABackingStore = 76;
    public static final int NSOpenGLPFAMPSafe = 78;
    public static final int NSOpenGLPFAWindow = 80;
    public static final int NSOpenGLPFAMultiScreen = 81;
    public static final int NSOpenGLPFACompliant = 83;
    public static final int NSOpenGLPFAScreenMask = 84;
    public static final int NSOpenGLPFAPixelBuffer = 90;
    public static final int NSOpenGLPFAVirtualScreenCount = 128;
    public static final int kCGLNoError = 0;
    public static final int kCGLBadAttribute = 10000;
    public static final int kCGLBadProperty = 10001;
    public static final int kCGLBadPixelFormat = 10002;
    public static final int kCGLBadRendererInfo = 10003;
    public static final int kCGLBadContext = 10004;
    public static final int kCGLBadDrawable = 10005;
    public static final int kCGLBadDisplay = 10006;
    public static final int kCGLBadState = 10007;
    public static final int kCGLBadValue = 10008;
    public static final int kCGLBadMatch = 10009;
    public static final int kCGLBadEnumeration = 10010;
    public static final int kCGLBadOffScreen = 10011;
    public static final int kCGLBadFullScreen = 10012;
    public static final int kCGLBadWindow = 10013;
    public static final int kCGLBadAddress = 10014;
    public static final int kCGLBadCodeModule = 10015;
    public static final int kCGLBadAlloc = 10016;
    public static final int kCGLBadConnection = 10017;
    public static final int kCGLCPSwapRectangle = 200;
    public static final int kCGLCPSwapInterval = 222;
    public static final int kCGLCPDispatchTableSize = 224;
    public static final int kCGLCPClientStorage = 226;
    public static final int kCGLCPSurfaceTexture = 228;
    public static final int kCGLCPSurfaceOrder = 235;
    public static final int kCGLCPSurfaceOpacity = 236;
    public static final int kCGLCPSurfaceBackingSize = 304;
    public static final int kCGLCPSurfaceSurfaceVolatile = 306;
    public static final int kCGLCPReclaimResources = 308;
    public static final int kCGLCPCurrentRendererID = 309;
    public static final int kCGLCPGPUVertexProcessing = 310;
    public static final int kCGLCPGPUFragmentProcessing = 311;
    public static final int kCGLCPHasDrawable = 314;
    public static final int kCGLCPMPSwapsInFlight = 315;
    
    public static int CGLChoosePixelFormat(final IntBuffer intBuffer, final PointerBuffer pointerBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"pix\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"npix\" is not a direct buffer");
        }
        return CGLChoosePixelFormat0(intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2));
    }
    
    private static native int CGLChoosePixelFormat0(final Object p0, final int p1, final Object p2, final int p3, final Object p4, final int p5);
    
    public static native int CGLDestroyPixelFormat(final long p0);
    
    public static native long CGLGetPixelFormat(final long p0);
    
    public static int CGLCreateContext(final long n, final long n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"ctx\" is not a direct buffer");
        }
        return CGLCreateContext0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer));
    }
    
    private static native int CGLCreateContext0(final long p0, final long p1, final Object p2, final int p3);
    
    public static native void CGLReleaseContext(final long p0);
    
    public static native int CGLDestroyContext(final long p0);
    
    public static native int CGLLockContext(final long p0);
    
    public static native int CGLUnlockContext(final long p0);
    
    public static native int CGLSetCurrentContext(final long p0);
    
    public static native long CGLGetCurrentContext();
    
    public static native int CGLFlushDrawable(final long p0);
    
    public static int CGLSetParameter(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"params\" is not a direct buffer");
        }
        return CGLSetParameter0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer));
    }
    
    private static native int CGLSetParameter0(final long p0, final int p1, final Object p2, final int p3);
    
    public static native int CGLCopyContext(final long p0, final long p1, final int p2);
    
    public static native long CGLGetShareGroup(final long p0);
    
    public static int CGLCreatePBuffer(final int n, final int n2, final int n3, final int n4, final int n5, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"pbuffer\" is not a direct buffer");
        }
        return CGLCreatePBuffer0(n, n2, n3, n4, n5, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer));
    }
    
    private static native int CGLCreatePBuffer0(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6);
    
    public static native int CGLDestroyPBuffer(final long p0);
    
    public static native int CGLSetPBuffer(final long p0, final long p1, final int p2, final int p3, final int p4);
    
    public static void CGLQueryPixelFormat(final long n, final IntBuffer intBuffer, final int n2, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"iattrs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"ivalues\" is not a direct buffer");
        }
        CGLQueryPixelFormat0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2));
    }
    
    private static native void CGLQueryPixelFormat0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5);
    
    public static long createPixelFormat(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"iattrs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"ivalues\" is not a direct buffer");
        }
        return createPixelFormat0(intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2));
    }
    
    private static native long createPixelFormat0(final Object p0, final int p1, final int p2, final Object p3, final int p4);
    
    public static void queryPixelFormat(final long n, final IntBuffer intBuffer, final int n2, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"iattrs\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"ivalues\" is not a direct buffer");
        }
        queryPixelFormat0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2));
    }
    
    private static native void queryPixelFormat0(final long p0, final Object p1, final int p2, final int p3, final Object p4, final int p5);
    
    public static native void deletePixelFormat(final long p0);
    
    public static native long getCurrentContext();
    
    public static native long getCGLContext(final long p0);
    
    public static native long getNSView(final long p0);
    
    public static long createContext(final long n, final long n2, final boolean b, final long n3, final boolean b2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"viewNotReady\" is not a direct buffer");
        }
        return createContext0(n, n2, b, n3, b2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer));
    }
    
    private static native long createContext0(final long p0, final long p1, final boolean p2, final long p3, final boolean p4, final Object p5, final int p6);
    
    public static native void setContextView(final long p0, final long p1);
    
    public static native void clearDrawable(final long p0);
    
    public static native boolean makeCurrentContext(final long p0);
    
    public static native boolean clearCurrentContext(final long p0);
    
    public static native boolean deleteContext(final long p0, final boolean p1);
    
    public static native boolean flushBuffer(final long p0);
    
    public static native void setContextOpacity(final long p0, final int p1);
    
    public static native void updateContext(final long p0);
    
    public static native void copyContext(final long p0, final long p1, final int p2);
    
    public static native long updateContextRegister(final long p0, final long p1);
    
    public static native boolean updateContextNeedsUpdate(final long p0);
    
    public static native void updateContextUnregister(final long p0);
    
    public static native long createPBuffer(final int p0, final int p1, final int p2, final int p3);
    
    public static native boolean destroyPBuffer(final long p0);
    
    public static native void setContextPBuffer(final long p0, final long p1);
    
    public static native void setContextTextureImageToPBuffer(final long p0, final long p1, final int p2);
    
    public static native boolean isNSOpenGLPixelBuffer(final long p0);
    
    private static native long createNSOpenGLLayerImpl(final long p0, final int p1, final long p2, final long p3, final int p4, final boolean p5, final int p6, final int p7, final int p8, final int p9);
    
    private static native void setNSOpenGLLayerEnabledImpl(final long p0, final boolean p1);
    
    public static native void setNSOpenGLLayerSwapInterval(final long p0, final int p1);
    
    public static native void waitUntilNSOpenGLLayerIsReady(final long p0, final long p1);
    
    public static native void setNSOpenGLLayerNeedsDisplayFBO(final long p0, final int p1);
    
    public static native void setNSOpenGLLayerNeedsDisplayPBuffer(final long p0, final long p1);
    
    private static native void releaseNSOpenGLLayerImpl(final long p0);
    
    public static native long getProcAddress(final String p0);
    
    public static native void setSwapInterval(final long p0, final int p1);
    
    public static boolean setGammaRamp(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final FloatBuffer floatBuffer3) {
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"redRamp\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer2)) {
            throw new GLException("Argument \"greenRamp\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer3)) {
            throw new GLException("Argument \"blueRamp\" is not a direct buffer");
        }
        return setGammaRamp0(n, floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), floatBuffer2, Buffers.getDirectBufferByteOffset(floatBuffer2), floatBuffer3, Buffers.getDirectBufferByteOffset(floatBuffer3));
    }
    
    private static native boolean setGammaRamp0(final int p0, final Object p1, final int p2, final Object p3, final int p4, final Object p5, final int p6);
    
    public static native void resetGammaRamp();
    
    public static long createNSOpenGLLayer(final long n, final int n2, final long n3, final long n4, final int n5, final boolean b, final int n6, final int n7, final int n8, final int n9) {
        return createNSOpenGLLayerImpl(n, n2, n3, n4, n5, b, n6, n7, n8, n9);
    }
    
    public static void setNSOpenGLLayerEnabled(final long n, final boolean b) {
        setNSOpenGLLayerEnabledImpl(n, b);
    }
    
    public static void releaseNSOpenGLLayer(final long n) {
        releaseNSOpenGLLayerImpl(n);
    }
}
