// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLException;

public class WGLExtImpl implements WGLExt
{
    private WindowsWGLContext _context;
    
    @Override
    public long wglCreateBufferRegionARB(final long n, final int n2, final int n3) {
        final long addressof_wglCreateBufferRegionARB = this._context.getWGLExtProcAddressTable()._addressof_wglCreateBufferRegionARB;
        if (addressof_wglCreateBufferRegionARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateBufferRegionARB"));
        }
        return this.dispatch_wglCreateBufferRegionARB0(n, n2, n3, addressof_wglCreateBufferRegionARB);
    }
    
    private native long dispatch_wglCreateBufferRegionARB0(final long p0, final int p1, final int p2, final long p3);
    
    @Override
    public void wglDeleteBufferRegionARB(final long n) {
        final long addressof_wglDeleteBufferRegionARB = this._context.getWGLExtProcAddressTable()._addressof_wglDeleteBufferRegionARB;
        if (addressof_wglDeleteBufferRegionARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDeleteBufferRegionARB"));
        }
        this.dispatch_wglDeleteBufferRegionARB0(n, addressof_wglDeleteBufferRegionARB);
    }
    
    private native void dispatch_wglDeleteBufferRegionARB0(final long p0, final long p1);
    
    @Override
    public boolean wglSaveBufferRegionARB(final long n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_wglSaveBufferRegionARB = this._context.getWGLExtProcAddressTable()._addressof_wglSaveBufferRegionARB;
        if (addressof_wglSaveBufferRegionARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSaveBufferRegionARB"));
        }
        return this.dispatch_wglSaveBufferRegionARB0(n, n2, n3, n4, n5, addressof_wglSaveBufferRegionARB);
    }
    
    private native boolean dispatch_wglSaveBufferRegionARB0(final long p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public boolean wglRestoreBufferRegionARB(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long addressof_wglRestoreBufferRegionARB = this._context.getWGLExtProcAddressTable()._addressof_wglRestoreBufferRegionARB;
        if (addressof_wglRestoreBufferRegionARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglRestoreBufferRegionARB"));
        }
        return this.dispatch_wglRestoreBufferRegionARB0(n, n2, n3, n4, n5, n6, n7, addressof_wglRestoreBufferRegionARB);
    }
    
    private native boolean dispatch_wglRestoreBufferRegionARB0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public long wglCreateContextAttribsARB(final long n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribList\" is not a direct buffer");
        }
        final long addressof_wglCreateContextAttribsARB = this._context.getWGLExtProcAddressTable()._addressof_wglCreateContextAttribsARB;
        if (addressof_wglCreateContextAttribsARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateContextAttribsARB"));
        }
        return this.dispatch_wglCreateContextAttribsARB0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglCreateContextAttribsARB);
    }
    
    private native long dispatch_wglCreateContextAttribsARB0(final long p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public String wglGetExtensionsStringARB(final long n) {
        final long addressof_wglGetExtensionsStringARB = this._context.getWGLExtProcAddressTable()._addressof_wglGetExtensionsStringARB;
        if (addressof_wglGetExtensionsStringARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetExtensionsStringARB"));
        }
        return this.dispatch_wglGetExtensionsStringARB0(n, addressof_wglGetExtensionsStringARB);
    }
    
    private native String dispatch_wglGetExtensionsStringARB0(final long p0, final long p1);
    
    @Override
    public boolean wglMakeContextCurrent(final long n, final long n2, final long n3) {
        final long addressof_wglMakeContextCurrent = this._context.getWGLExtProcAddressTable()._addressof_wglMakeContextCurrent;
        if (addressof_wglMakeContextCurrent == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglMakeContextCurrent"));
        }
        return this.dispatch_wglMakeContextCurrent0(n, n2, n3, addressof_wglMakeContextCurrent);
    }
    
    private native boolean dispatch_wglMakeContextCurrent0(final long p0, final long p1, final long p2, final long p3);
    
    @Override
    public long wglGetCurrentReadDC() {
        final long addressof_wglGetCurrentReadDC = this._context.getWGLExtProcAddressTable()._addressof_wglGetCurrentReadDC;
        if (addressof_wglGetCurrentReadDC == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetCurrentReadDC"));
        }
        return this.dispatch_wglGetCurrentReadDC0(addressof_wglGetCurrentReadDC);
    }
    
    private native long dispatch_wglGetCurrentReadDC0(final long p0);
    
    @Override
    public long wglCreatePbufferARB(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribList\" is not a direct buffer");
        }
        final long addressof_wglCreatePbufferARB = this._context.getWGLExtProcAddressTable()._addressof_wglCreatePbufferARB;
        if (addressof_wglCreatePbufferARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreatePbufferARB"));
        }
        return this.dispatch_wglCreatePbufferARB0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglCreatePbufferARB);
    }
    
    private native long dispatch_wglCreatePbufferARB0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long wglGetPbufferDCARB(final long n) {
        final long addressof_wglGetPbufferDCARB = this._context.getWGLExtProcAddressTable()._addressof_wglGetPbufferDCARB;
        if (addressof_wglGetPbufferDCARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPbufferDCARB"));
        }
        return this.dispatch_wglGetPbufferDCARB0(n, addressof_wglGetPbufferDCARB);
    }
    
    private native long dispatch_wglGetPbufferDCARB0(final long p0, final long p1);
    
    @Override
    public int wglReleasePbufferDCARB(final long n, final long n2) {
        final long addressof_wglReleasePbufferDCARB = this._context.getWGLExtProcAddressTable()._addressof_wglReleasePbufferDCARB;
        if (addressof_wglReleasePbufferDCARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleasePbufferDCARB"));
        }
        return this.dispatch_wglReleasePbufferDCARB0(n, n2, addressof_wglReleasePbufferDCARB);
    }
    
    private native int dispatch_wglReleasePbufferDCARB0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean wglDestroyPbufferARB(final long n) {
        final long addressof_wglDestroyPbufferARB = this._context.getWGLExtProcAddressTable()._addressof_wglDestroyPbufferARB;
        if (addressof_wglDestroyPbufferARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDestroyPbufferARB"));
        }
        return this.dispatch_wglDestroyPbufferARB0(n, addressof_wglDestroyPbufferARB);
    }
    
    private native boolean dispatch_wglDestroyPbufferARB0(final long p0, final long p1);
    
    @Override
    public boolean wglQueryPbufferARB(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piValue\" is not a direct buffer");
        }
        final long addressof_wglQueryPbufferARB = this._context.getWGLExtProcAddressTable()._addressof_wglQueryPbufferARB;
        if (addressof_wglQueryPbufferARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryPbufferARB"));
        }
        return this.dispatch_wglQueryPbufferARB0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglQueryPbufferARB);
    }
    
    private native boolean dispatch_wglQueryPbufferARB0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglGetPixelFormatAttribivARB(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttributes\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"piValues\" is not a direct buffer");
        }
        final long addressof_wglGetPixelFormatAttribivARB = this._context.getWGLExtProcAddressTable()._addressof_wglGetPixelFormatAttribivARB;
        if (addressof_wglGetPixelFormatAttribivARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPixelFormatAttribivARB"));
        }
        return this.dispatch_wglGetPixelFormatAttribivARB0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_wglGetPixelFormatAttribivARB);
    }
    
    private native boolean dispatch_wglGetPixelFormatAttribivARB0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean wglGetPixelFormatAttribfvARB(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttributes\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pfValues\" is not a direct buffer");
        }
        final long addressof_wglGetPixelFormatAttribfvARB = this._context.getWGLExtProcAddressTable()._addressof_wglGetPixelFormatAttribfvARB;
        if (addressof_wglGetPixelFormatAttribfvARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPixelFormatAttribfvARB"));
        }
        return this.dispatch_wglGetPixelFormatAttribfvARB0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), addressof_wglGetPixelFormatAttribfvARB);
    }
    
    private native boolean dispatch_wglGetPixelFormatAttribfvARB0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean wglChoosePixelFormatARB(final long n, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final int n2, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribIList\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pfAttribFList\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"piFormats\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new GLException("Argument \"nNumFormats\" is not a direct buffer");
        }
        final long addressof_wglChoosePixelFormatARB = this._context.getWGLExtProcAddressTable()._addressof_wglChoosePixelFormatARB;
        if (addressof_wglChoosePixelFormatARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglChoosePixelFormatARB"));
        }
        return this.dispatch_wglChoosePixelFormatARB0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), addressof_wglChoosePixelFormatARB);
    }
    
    private native boolean dispatch_wglChoosePixelFormatARB0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final Object p8, final int p9, final long p10);
    
    @Override
    public boolean wglBindTexImageARB(final long n, final int n2) {
        final long addressof_wglBindTexImageARB = this._context.getWGLExtProcAddressTable()._addressof_wglBindTexImageARB;
        if (addressof_wglBindTexImageARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindTexImageARB"));
        }
        return this.dispatch_wglBindTexImageARB0(n, n2, addressof_wglBindTexImageARB);
    }
    
    private native boolean dispatch_wglBindTexImageARB0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean wglReleaseTexImageARB(final long n, final int n2) {
        final long addressof_wglReleaseTexImageARB = this._context.getWGLExtProcAddressTable()._addressof_wglReleaseTexImageARB;
        if (addressof_wglReleaseTexImageARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleaseTexImageARB"));
        }
        return this.dispatch_wglReleaseTexImageARB0(n, n2, addressof_wglReleaseTexImageARB);
    }
    
    private native boolean dispatch_wglReleaseTexImageARB0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean wglSetPbufferAttribARB(final long n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribList\" is not a direct buffer");
        }
        final long addressof_wglSetPbufferAttribARB = this._context.getWGLExtProcAddressTable()._addressof_wglSetPbufferAttribARB;
        if (addressof_wglSetPbufferAttribARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSetPbufferAttribARB"));
        }
        return this.dispatch_wglSetPbufferAttribARB0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglSetPbufferAttribARB);
    }
    
    private native boolean dispatch_wglSetPbufferAttribARB0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean wglSetStereoEmitterState3DL(final long n, final int n2) {
        final long addressof_wglSetStereoEmitterState3DL = this._context.getWGLExtProcAddressTable()._addressof_wglSetStereoEmitterState3DL;
        if (addressof_wglSetStereoEmitterState3DL == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSetStereoEmitterState3DL"));
        }
        return this.dispatch_wglSetStereoEmitterState3DL0(n, n2, addressof_wglSetStereoEmitterState3DL);
    }
    
    private native boolean dispatch_wglSetStereoEmitterState3DL0(final long p0, final int p1, final long p2);
    
    @Override
    public int wglGetGPUIDsAMD(final int n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"ids\" is not a direct buffer");
        }
        final long addressof_wglGetGPUIDsAMD = this._context.getWGLExtProcAddressTable()._addressof_wglGetGPUIDsAMD;
        if (addressof_wglGetGPUIDsAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetGPUIDsAMD"));
        }
        return this.dispatch_wglGetGPUIDsAMD0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglGetGPUIDsAMD);
    }
    
    private native int dispatch_wglGetGPUIDsAMD0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public int wglGetGPUInfoAMD(final int n, final int n2, final int n3, final int n4, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"data\" is not a direct buffer");
        }
        final long addressof_wglGetGPUInfoAMD = this._context.getWGLExtProcAddressTable()._addressof_wglGetGPUInfoAMD;
        if (addressof_wglGetGPUInfoAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetGPUInfoAMD"));
        }
        return this.dispatch_wglGetGPUInfoAMD0(n, n2, n3, n4, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_wglGetGPUInfoAMD);
    }
    
    private native int dispatch_wglGetGPUInfoAMD0(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public int wglGetContextGPUIDAMD(final long n) {
        final long addressof_wglGetContextGPUIDAMD = this._context.getWGLExtProcAddressTable()._addressof_wglGetContextGPUIDAMD;
        if (addressof_wglGetContextGPUIDAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetContextGPUIDAMD"));
        }
        return this.dispatch_wglGetContextGPUIDAMD0(n, addressof_wglGetContextGPUIDAMD);
    }
    
    private native int dispatch_wglGetContextGPUIDAMD0(final long p0, final long p1);
    
    @Override
    public long wglCreateAssociatedContextAMD(final int n) {
        final long addressof_wglCreateAssociatedContextAMD = this._context.getWGLExtProcAddressTable()._addressof_wglCreateAssociatedContextAMD;
        if (addressof_wglCreateAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateAssociatedContextAMD"));
        }
        return this.dispatch_wglCreateAssociatedContextAMD0(n, addressof_wglCreateAssociatedContextAMD);
    }
    
    private native long dispatch_wglCreateAssociatedContextAMD0(final int p0, final long p1);
    
    @Override
    public long wglCreateAssociatedContextAttribsAMD(final int n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribList\" is not a direct buffer");
        }
        final long addressof_wglCreateAssociatedContextAttribsAMD = this._context.getWGLExtProcAddressTable()._addressof_wglCreateAssociatedContextAttribsAMD;
        if (addressof_wglCreateAssociatedContextAttribsAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateAssociatedContextAttribsAMD"));
        }
        return this.dispatch_wglCreateAssociatedContextAttribsAMD0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglCreateAssociatedContextAttribsAMD);
    }
    
    private native long dispatch_wglCreateAssociatedContextAttribsAMD0(final int p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglDeleteAssociatedContextAMD(final long n) {
        final long addressof_wglDeleteAssociatedContextAMD = this._context.getWGLExtProcAddressTable()._addressof_wglDeleteAssociatedContextAMD;
        if (addressof_wglDeleteAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDeleteAssociatedContextAMD"));
        }
        return this.dispatch_wglDeleteAssociatedContextAMD0(n, addressof_wglDeleteAssociatedContextAMD);
    }
    
    private native boolean dispatch_wglDeleteAssociatedContextAMD0(final long p0, final long p1);
    
    @Override
    public boolean wglMakeAssociatedContextCurrentAMD(final long n) {
        final long addressof_wglMakeAssociatedContextCurrentAMD = this._context.getWGLExtProcAddressTable()._addressof_wglMakeAssociatedContextCurrentAMD;
        if (addressof_wglMakeAssociatedContextCurrentAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglMakeAssociatedContextCurrentAMD"));
        }
        return this.dispatch_wglMakeAssociatedContextCurrentAMD0(n, addressof_wglMakeAssociatedContextCurrentAMD);
    }
    
    private native boolean dispatch_wglMakeAssociatedContextCurrentAMD0(final long p0, final long p1);
    
    @Override
    public long wglGetCurrentAssociatedContextAMD() {
        final long addressof_wglGetCurrentAssociatedContextAMD = this._context.getWGLExtProcAddressTable()._addressof_wglGetCurrentAssociatedContextAMD;
        if (addressof_wglGetCurrentAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetCurrentAssociatedContextAMD"));
        }
        return this.dispatch_wglGetCurrentAssociatedContextAMD0(addressof_wglGetCurrentAssociatedContextAMD);
    }
    
    private native long dispatch_wglGetCurrentAssociatedContextAMD0(final long p0);
    
    @Override
    public void wglBlitContextFramebufferAMD(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11) {
        final long addressof_wglBlitContextFramebufferAMD = this._context.getWGLExtProcAddressTable()._addressof_wglBlitContextFramebufferAMD;
        if (addressof_wglBlitContextFramebufferAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBlitContextFramebufferAMD"));
        }
        this.dispatch_wglBlitContextFramebufferAMD0(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, addressof_wglBlitContextFramebufferAMD);
    }
    
    private native void dispatch_wglBlitContextFramebufferAMD0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11);
    
    @Override
    public boolean wglCreateDisplayColorTableEXT(final short n) {
        final long addressof_wglCreateDisplayColorTableEXT = this._context.getWGLExtProcAddressTable()._addressof_wglCreateDisplayColorTableEXT;
        if (addressof_wglCreateDisplayColorTableEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreateDisplayColorTableEXT"));
        }
        return this.dispatch_wglCreateDisplayColorTableEXT0(n, addressof_wglCreateDisplayColorTableEXT);
    }
    
    private native boolean dispatch_wglCreateDisplayColorTableEXT0(final short p0, final long p1);
    
    @Override
    public boolean wglLoadDisplayColorTableEXT(final ShortBuffer shortBuffer, final int n) {
        if (!Buffers.isDirect(shortBuffer)) {
            throw new GLException("Argument \"table\" is not a direct buffer");
        }
        final long addressof_wglLoadDisplayColorTableEXT = this._context.getWGLExtProcAddressTable()._addressof_wglLoadDisplayColorTableEXT;
        if (addressof_wglLoadDisplayColorTableEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglLoadDisplayColorTableEXT"));
        }
        return this.dispatch_wglLoadDisplayColorTableEXT0(shortBuffer, Buffers.getDirectBufferByteOffset(shortBuffer), n, addressof_wglLoadDisplayColorTableEXT);
    }
    
    private native boolean dispatch_wglLoadDisplayColorTableEXT0(final Object p0, final int p1, final int p2, final long p3);
    
    @Override
    public boolean wglBindDisplayColorTableEXT(final short n) {
        final long addressof_wglBindDisplayColorTableEXT = this._context.getWGLExtProcAddressTable()._addressof_wglBindDisplayColorTableEXT;
        if (addressof_wglBindDisplayColorTableEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindDisplayColorTableEXT"));
        }
        return this.dispatch_wglBindDisplayColorTableEXT0(n, addressof_wglBindDisplayColorTableEXT);
    }
    
    private native boolean dispatch_wglBindDisplayColorTableEXT0(final short p0, final long p1);
    
    @Override
    public void wglDestroyDisplayColorTableEXT(final short n) {
        final long addressof_wglDestroyDisplayColorTableEXT = this._context.getWGLExtProcAddressTable()._addressof_wglDestroyDisplayColorTableEXT;
        if (addressof_wglDestroyDisplayColorTableEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDestroyDisplayColorTableEXT"));
        }
        this.dispatch_wglDestroyDisplayColorTableEXT0(n, addressof_wglDestroyDisplayColorTableEXT);
    }
    
    private native void dispatch_wglDestroyDisplayColorTableEXT0(final short p0, final long p1);
    
    @Override
    public String wglGetExtensionsStringEXT() {
        final long addressof_wglGetExtensionsStringEXT = this._context.getWGLExtProcAddressTable()._addressof_wglGetExtensionsStringEXT;
        if (addressof_wglGetExtensionsStringEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetExtensionsStringEXT"));
        }
        return this.dispatch_wglGetExtensionsStringEXT0(addressof_wglGetExtensionsStringEXT);
    }
    
    private native String dispatch_wglGetExtensionsStringEXT0(final long p0);
    
    @Override
    public long wglCreatePbufferEXT(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribList\" is not a direct buffer");
        }
        final long addressof_wglCreatePbufferEXT = this._context.getWGLExtProcAddressTable()._addressof_wglCreatePbufferEXT;
        if (addressof_wglCreatePbufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCreatePbufferEXT"));
        }
        return this.dispatch_wglCreatePbufferEXT0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglCreatePbufferEXT);
    }
    
    private native long dispatch_wglCreatePbufferEXT0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long wglGetPbufferDCEXT(final long n) {
        final long addressof_wglGetPbufferDCEXT = this._context.getWGLExtProcAddressTable()._addressof_wglGetPbufferDCEXT;
        if (addressof_wglGetPbufferDCEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPbufferDCEXT"));
        }
        return this.dispatch_wglGetPbufferDCEXT0(n, addressof_wglGetPbufferDCEXT);
    }
    
    private native long dispatch_wglGetPbufferDCEXT0(final long p0, final long p1);
    
    @Override
    public int wglReleasePbufferDCEXT(final long n, final long n2) {
        final long addressof_wglReleasePbufferDCEXT = this._context.getWGLExtProcAddressTable()._addressof_wglReleasePbufferDCEXT;
        if (addressof_wglReleasePbufferDCEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleasePbufferDCEXT"));
        }
        return this.dispatch_wglReleasePbufferDCEXT0(n, n2, addressof_wglReleasePbufferDCEXT);
    }
    
    private native int dispatch_wglReleasePbufferDCEXT0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean wglDestroyPbufferEXT(final long n) {
        final long addressof_wglDestroyPbufferEXT = this._context.getWGLExtProcAddressTable()._addressof_wglDestroyPbufferEXT;
        if (addressof_wglDestroyPbufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDestroyPbufferEXT"));
        }
        return this.dispatch_wglDestroyPbufferEXT0(n, addressof_wglDestroyPbufferEXT);
    }
    
    private native boolean dispatch_wglDestroyPbufferEXT0(final long p0, final long p1);
    
    @Override
    public boolean wglQueryPbufferEXT(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piValue\" is not a direct buffer");
        }
        final long addressof_wglQueryPbufferEXT = this._context.getWGLExtProcAddressTable()._addressof_wglQueryPbufferEXT;
        if (addressof_wglQueryPbufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryPbufferEXT"));
        }
        return this.dispatch_wglQueryPbufferEXT0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglQueryPbufferEXT);
    }
    
    private native boolean dispatch_wglQueryPbufferEXT0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglGetPixelFormatAttribivEXT(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttributes\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"piValues\" is not a direct buffer");
        }
        final long addressof_wglGetPixelFormatAttribivEXT = this._context.getWGLExtProcAddressTable()._addressof_wglGetPixelFormatAttribivEXT;
        if (addressof_wglGetPixelFormatAttribivEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPixelFormatAttribivEXT"));
        }
        return this.dispatch_wglGetPixelFormatAttribivEXT0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_wglGetPixelFormatAttribivEXT);
    }
    
    private native boolean dispatch_wglGetPixelFormatAttribivEXT0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean wglGetPixelFormatAttribfvEXT(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final FloatBuffer floatBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttributes\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pfValues\" is not a direct buffer");
        }
        final long addressof_wglGetPixelFormatAttribfvEXT = this._context.getWGLExtProcAddressTable()._addressof_wglGetPixelFormatAttribfvEXT;
        if (addressof_wglGetPixelFormatAttribfvEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetPixelFormatAttribfvEXT"));
        }
        return this.dispatch_wglGetPixelFormatAttribfvEXT0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), addressof_wglGetPixelFormatAttribfvEXT);
    }
    
    private native boolean dispatch_wglGetPixelFormatAttribfvEXT0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean wglChoosePixelFormatEXT(final long n, final IntBuffer intBuffer, final FloatBuffer floatBuffer, final int n2, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribIList\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pfAttribFList\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"piFormats\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new GLException("Argument \"nNumFormats\" is not a direct buffer");
        }
        final long addressof_wglChoosePixelFormatEXT = this._context.getWGLExtProcAddressTable()._addressof_wglChoosePixelFormatEXT;
        if (addressof_wglChoosePixelFormatEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglChoosePixelFormatEXT"));
        }
        return this.dispatch_wglChoosePixelFormatEXT0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), n2, intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), addressof_wglChoosePixelFormatEXT);
    }
    
    private native boolean dispatch_wglChoosePixelFormatEXT0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final int p5, final Object p6, final int p7, final Object p8, final int p9, final long p10);
    
    @Override
    public boolean wglSwapIntervalEXT(final int n) {
        final long addressof_wglSwapIntervalEXT = this._context.getWGLExtProcAddressTable()._addressof_wglSwapIntervalEXT;
        if (addressof_wglSwapIntervalEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSwapIntervalEXT"));
        }
        return this.dispatch_wglSwapIntervalEXT0(n, addressof_wglSwapIntervalEXT);
    }
    
    private native boolean dispatch_wglSwapIntervalEXT0(final int p0, final long p1);
    
    @Override
    public int wglGetSwapIntervalEXT() {
        final long addressof_wglGetSwapIntervalEXT = this._context.getWGLExtProcAddressTable()._addressof_wglGetSwapIntervalEXT;
        if (addressof_wglGetSwapIntervalEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetSwapIntervalEXT"));
        }
        return this.dispatch_wglGetSwapIntervalEXT0(addressof_wglGetSwapIntervalEXT);
    }
    
    private native int dispatch_wglGetSwapIntervalEXT0(final long p0);
    
    @Override
    public boolean wglEnableFrameLockI3D() {
        final long addressof_wglEnableFrameLockI3D = this._context.getWGLExtProcAddressTable()._addressof_wglEnableFrameLockI3D;
        if (addressof_wglEnableFrameLockI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglEnableFrameLockI3D"));
        }
        return this.dispatch_wglEnableFrameLockI3D0(addressof_wglEnableFrameLockI3D);
    }
    
    private native boolean dispatch_wglEnableFrameLockI3D0(final long p0);
    
    @Override
    public boolean wglDisableFrameLockI3D() {
        final long addressof_wglDisableFrameLockI3D = this._context.getWGLExtProcAddressTable()._addressof_wglDisableFrameLockI3D;
        if (addressof_wglDisableFrameLockI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDisableFrameLockI3D"));
        }
        return this.dispatch_wglDisableFrameLockI3D0(addressof_wglDisableFrameLockI3D);
    }
    
    private native boolean dispatch_wglDisableFrameLockI3D0(final long p0);
    
    @Override
    public boolean wglIsEnabledFrameLockI3D(final ByteBuffer byteBuffer) {
        if (!Buffers.isDirect(byteBuffer)) {
            throw new GLException("Argument \"pFlag\" is not a direct buffer");
        }
        final long addressof_wglIsEnabledFrameLockI3D = this._context.getWGLExtProcAddressTable()._addressof_wglIsEnabledFrameLockI3D;
        if (addressof_wglIsEnabledFrameLockI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglIsEnabledFrameLockI3D"));
        }
        return this.dispatch_wglIsEnabledFrameLockI3D0(byteBuffer, Buffers.getDirectBufferByteOffset(byteBuffer), addressof_wglIsEnabledFrameLockI3D);
    }
    
    private native boolean dispatch_wglIsEnabledFrameLockI3D0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean wglQueryFrameLockMasterI3D(final ByteBuffer byteBuffer) {
        if (!Buffers.isDirect(byteBuffer)) {
            throw new GLException("Argument \"pFlag\" is not a direct buffer");
        }
        final long addressof_wglQueryFrameLockMasterI3D = this._context.getWGLExtProcAddressTable()._addressof_wglQueryFrameLockMasterI3D;
        if (addressof_wglQueryFrameLockMasterI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryFrameLockMasterI3D"));
        }
        return this.dispatch_wglQueryFrameLockMasterI3D0(byteBuffer, Buffers.getDirectBufferByteOffset(byteBuffer), addressof_wglQueryFrameLockMasterI3D);
    }
    
    private native boolean dispatch_wglQueryFrameLockMasterI3D0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean wglGetFrameUsageI3D(final FloatBuffer floatBuffer) {
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pUsage\" is not a direct buffer");
        }
        final long addressof_wglGetFrameUsageI3D = this._context.getWGLExtProcAddressTable()._addressof_wglGetFrameUsageI3D;
        if (addressof_wglGetFrameUsageI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetFrameUsageI3D"));
        }
        return this.dispatch_wglGetFrameUsageI3D0(floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), addressof_wglGetFrameUsageI3D);
    }
    
    private native boolean dispatch_wglGetFrameUsageI3D0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean wglBeginFrameTrackingI3D() {
        final long addressof_wglBeginFrameTrackingI3D = this._context.getWGLExtProcAddressTable()._addressof_wglBeginFrameTrackingI3D;
        if (addressof_wglBeginFrameTrackingI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBeginFrameTrackingI3D"));
        }
        return this.dispatch_wglBeginFrameTrackingI3D0(addressof_wglBeginFrameTrackingI3D);
    }
    
    private native boolean dispatch_wglBeginFrameTrackingI3D0(final long p0);
    
    @Override
    public boolean wglEndFrameTrackingI3D() {
        final long addressof_wglEndFrameTrackingI3D = this._context.getWGLExtProcAddressTable()._addressof_wglEndFrameTrackingI3D;
        if (addressof_wglEndFrameTrackingI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglEndFrameTrackingI3D"));
        }
        return this.dispatch_wglEndFrameTrackingI3D0(addressof_wglEndFrameTrackingI3D);
    }
    
    private native boolean dispatch_wglEndFrameTrackingI3D0(final long p0);
    
    @Override
    public boolean wglQueryFrameTrackingI3D(final IntBuffer intBuffer, final IntBuffer intBuffer2, final FloatBuffer floatBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"pFrameCount\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"pMissedFrames\" is not a direct buffer");
        }
        if (!Buffers.isDirect(floatBuffer)) {
            throw new GLException("Argument \"pLastMissedUsage\" is not a direct buffer");
        }
        final long addressof_wglQueryFrameTrackingI3D = this._context.getWGLExtProcAddressTable()._addressof_wglQueryFrameTrackingI3D;
        if (addressof_wglQueryFrameTrackingI3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryFrameTrackingI3D"));
        }
        return this.dispatch_wglQueryFrameTrackingI3D0(intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), floatBuffer, Buffers.getDirectBufferByteOffset(floatBuffer), addressof_wglQueryFrameTrackingI3D);
    }
    
    private native boolean dispatch_wglQueryFrameTrackingI3D0(final Object p0, final int p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean wglDXSetResourceShareHandleNV(final Buffer buffer, final long n) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"dxObject\" is not a direct buffer");
        }
        final long addressof_wglDXSetResourceShareHandleNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXSetResourceShareHandleNV;
        if (addressof_wglDXSetResourceShareHandleNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXSetResourceShareHandleNV"));
        }
        return this.dispatch_wglDXSetResourceShareHandleNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), n, addressof_wglDXSetResourceShareHandleNV);
    }
    
    private native boolean dispatch_wglDXSetResourceShareHandleNV0(final Object p0, final int p1, final long p2, final long p3);
    
    @Override
    public long wglDXOpenDeviceNV(final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"dxDevice\" is not a direct buffer");
        }
        final long addressof_wglDXOpenDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXOpenDeviceNV;
        if (addressof_wglDXOpenDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXOpenDeviceNV"));
        }
        return this.dispatch_wglDXOpenDeviceNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_wglDXOpenDeviceNV);
    }
    
    private native long dispatch_wglDXOpenDeviceNV0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean wglDXCloseDeviceNV(final long n) {
        final long addressof_wglDXCloseDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXCloseDeviceNV;
        if (addressof_wglDXCloseDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXCloseDeviceNV"));
        }
        return this.dispatch_wglDXCloseDeviceNV0(n, addressof_wglDXCloseDeviceNV);
    }
    
    private native boolean dispatch_wglDXCloseDeviceNV0(final long p0, final long p1);
    
    @Override
    public long wglDXRegisterObjectNV(final long n, final Buffer buffer, final int n2, final int n3, final int n4) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"dxObject\" is not a direct buffer");
        }
        final long addressof_wglDXRegisterObjectNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXRegisterObjectNV;
        if (addressof_wglDXRegisterObjectNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXRegisterObjectNV"));
        }
        return this.dispatch_wglDXRegisterObjectNV0(n, buffer, Buffers.getDirectBufferByteOffset(buffer), n2, n3, n4, addressof_wglDXRegisterObjectNV);
    }
    
    private native long dispatch_wglDXRegisterObjectNV0(final long p0, final Object p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public boolean wglDXUnregisterObjectNV(final long n, final long n2) {
        final long addressof_wglDXUnregisterObjectNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXUnregisterObjectNV;
        if (addressof_wglDXUnregisterObjectNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXUnregisterObjectNV"));
        }
        return this.dispatch_wglDXUnregisterObjectNV0(n, n2, addressof_wglDXUnregisterObjectNV);
    }
    
    private native boolean dispatch_wglDXUnregisterObjectNV0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean wglDXObjectAccessNV(final long n, final int n2) {
        final long addressof_wglDXObjectAccessNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXObjectAccessNV;
        if (addressof_wglDXObjectAccessNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXObjectAccessNV"));
        }
        return this.dispatch_wglDXObjectAccessNV0(n, n2, addressof_wglDXObjectAccessNV);
    }
    
    private native boolean dispatch_wglDXObjectAccessNV0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean wglDXLockObjectsNV(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"hObjects\" is not a direct buffer");
        }
        final long addressof_wglDXLockObjectsNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXLockObjectsNV;
        if (addressof_wglDXLockObjectsNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXLockObjectsNV"));
        }
        return this.dispatch_wglDXLockObjectsNV0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_wglDXLockObjectsNV);
    }
    
    private native boolean dispatch_wglDXLockObjectsNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglDXUnlockObjectsNV(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"hObjects\" is not a direct buffer");
        }
        final long addressof_wglDXUnlockObjectsNV = this._context.getWGLExtProcAddressTable()._addressof_wglDXUnlockObjectsNV;
        if (addressof_wglDXUnlockObjectsNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDXUnlockObjectsNV"));
        }
        return this.dispatch_wglDXUnlockObjectsNV0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_wglDXUnlockObjectsNV);
    }
    
    private native boolean dispatch_wglDXUnlockObjectsNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglCopyImageSubDataNV(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15, final int n16, final int n17) {
        final long addressof_wglCopyImageSubDataNV = this._context.getWGLExtProcAddressTable()._addressof_wglCopyImageSubDataNV;
        if (addressof_wglCopyImageSubDataNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglCopyImageSubDataNV"));
        }
        return this.dispatch_wglCopyImageSubDataNV0(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, addressof_wglCopyImageSubDataNV);
    }
    
    private native boolean dispatch_wglCopyImageSubDataNV0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final int p15, final int p16, final long p17);
    
    @Override
    public boolean wglDelayBeforeSwapNV(final long n, final float n2) {
        final long addressof_wglDelayBeforeSwapNV = this._context.getWGLExtProcAddressTable()._addressof_wglDelayBeforeSwapNV;
        if (addressof_wglDelayBeforeSwapNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglDelayBeforeSwapNV"));
        }
        return this.dispatch_wglDelayBeforeSwapNV0(n, n2, addressof_wglDelayBeforeSwapNV);
    }
    
    private native boolean dispatch_wglDelayBeforeSwapNV0(final long p0, final float p1, final long p2);
    
    @Override
    public int wglEnumerateVideoDevicesNV(final long n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"phDeviceList\" is not a direct buffer");
        }
        final long addressof_wglEnumerateVideoDevicesNV = this._context.getWGLExtProcAddressTable()._addressof_wglEnumerateVideoDevicesNV;
        if (addressof_wglEnumerateVideoDevicesNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglEnumerateVideoDevicesNV"));
        }
        return this.dispatch_wglEnumerateVideoDevicesNV0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_wglEnumerateVideoDevicesNV);
    }
    
    private native int dispatch_wglEnumerateVideoDevicesNV0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean wglBindVideoDeviceNV(final long n, final int n2, final long n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piAttribList\" is not a direct buffer");
        }
        final long addressof_wglBindVideoDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglBindVideoDeviceNV;
        if (addressof_wglBindVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindVideoDeviceNV"));
        }
        return this.dispatch_wglBindVideoDeviceNV0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglBindVideoDeviceNV);
    }
    
    private native boolean dispatch_wglBindVideoDeviceNV0(final long p0, final int p1, final long p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean wglQueryCurrentContextNV(final int n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piValue\" is not a direct buffer");
        }
        final long addressof_wglQueryCurrentContextNV = this._context.getWGLExtProcAddressTable()._addressof_wglQueryCurrentContextNV;
        if (addressof_wglQueryCurrentContextNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryCurrentContextNV"));
        }
        return this.dispatch_wglQueryCurrentContextNV0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglQueryCurrentContextNV);
    }
    
    private native boolean dispatch_wglQueryCurrentContextNV0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean wglJoinSwapGroupNV(final long n, final int n2) {
        final long addressof_wglJoinSwapGroupNV = this._context.getWGLExtProcAddressTable()._addressof_wglJoinSwapGroupNV;
        if (addressof_wglJoinSwapGroupNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglJoinSwapGroupNV"));
        }
        return this.dispatch_wglJoinSwapGroupNV0(n, n2, addressof_wglJoinSwapGroupNV);
    }
    
    private native boolean dispatch_wglJoinSwapGroupNV0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean wglBindSwapBarrierNV(final int n, final int n2) {
        final long addressof_wglBindSwapBarrierNV = this._context.getWGLExtProcAddressTable()._addressof_wglBindSwapBarrierNV;
        if (addressof_wglBindSwapBarrierNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindSwapBarrierNV"));
        }
        return this.dispatch_wglBindSwapBarrierNV0(n, n2, addressof_wglBindSwapBarrierNV);
    }
    
    private native boolean dispatch_wglBindSwapBarrierNV0(final int p0, final int p1, final long p2);
    
    @Override
    public boolean wglQuerySwapGroupNV(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"group\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"barrier\" is not a direct buffer");
        }
        final long addressof_wglQuerySwapGroupNV = this._context.getWGLExtProcAddressTable()._addressof_wglQuerySwapGroupNV;
        if (addressof_wglQuerySwapGroupNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQuerySwapGroupNV"));
        }
        return this.dispatch_wglQuerySwapGroupNV0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_wglQuerySwapGroupNV);
    }
    
    private native boolean dispatch_wglQuerySwapGroupNV0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean wglQueryMaxSwapGroupsNV(final long n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"maxGroups\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"maxBarriers\" is not a direct buffer");
        }
        final long addressof_wglQueryMaxSwapGroupsNV = this._context.getWGLExtProcAddressTable()._addressof_wglQueryMaxSwapGroupsNV;
        if (addressof_wglQueryMaxSwapGroupsNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryMaxSwapGroupsNV"));
        }
        return this.dispatch_wglQueryMaxSwapGroupsNV0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_wglQueryMaxSwapGroupsNV);
    }
    
    private native boolean dispatch_wglQueryMaxSwapGroupsNV0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean wglQueryFrameCountNV(final long n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"count\" is not a direct buffer");
        }
        final long addressof_wglQueryFrameCountNV = this._context.getWGLExtProcAddressTable()._addressof_wglQueryFrameCountNV;
        if (addressof_wglQueryFrameCountNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryFrameCountNV"));
        }
        return this.dispatch_wglQueryFrameCountNV0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglQueryFrameCountNV);
    }
    
    private native boolean dispatch_wglQueryFrameCountNV0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean wglResetFrameCountNV(final long n) {
        final long addressof_wglResetFrameCountNV = this._context.getWGLExtProcAddressTable()._addressof_wglResetFrameCountNV;
        if (addressof_wglResetFrameCountNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglResetFrameCountNV"));
        }
        return this.dispatch_wglResetFrameCountNV0(n, addressof_wglResetFrameCountNV);
    }
    
    private native boolean dispatch_wglResetFrameCountNV0(final long p0, final long p1);
    
    @Override
    public ByteBuffer wglAllocateMemoryNV(final int n, final float n2, final float n3, final float n4) {
        final long addressof_wglAllocateMemoryNV = this._context.getWGLExtProcAddressTable()._addressof_wglAllocateMemoryNV;
        if (addressof_wglAllocateMemoryNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglAllocateMemoryNV"));
        }
        final ByteBuffer dispatch_wglAllocateMemoryNV0 = this.dispatch_wglAllocateMemoryNV0(n, n2, n3, n4, addressof_wglAllocateMemoryNV);
        if (dispatch_wglAllocateMemoryNV0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_wglAllocateMemoryNV0);
        return dispatch_wglAllocateMemoryNV0;
    }
    
    private native ByteBuffer dispatch_wglAllocateMemoryNV0(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void wglFreeMemoryNV(final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_wglFreeMemoryNV = this._context.getWGLExtProcAddressTable()._addressof_wglFreeMemoryNV;
        if (addressof_wglFreeMemoryNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglFreeMemoryNV"));
        }
        this.dispatch_wglFreeMemoryNV0(buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_wglFreeMemoryNV);
    }
    
    private native void dispatch_wglFreeMemoryNV0(final Object p0, final int p1, final long p2);
    
    @Override
    public boolean wglBindVideoCaptureDeviceNV(final int n, final long n2) {
        final long addressof_wglBindVideoCaptureDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglBindVideoCaptureDeviceNV;
        if (addressof_wglBindVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindVideoCaptureDeviceNV"));
        }
        return this.dispatch_wglBindVideoCaptureDeviceNV0(n, n2, addressof_wglBindVideoCaptureDeviceNV);
    }
    
    private native boolean dispatch_wglBindVideoCaptureDeviceNV0(final int p0, final long p1, final long p2);
    
    @Override
    public int wglEnumerateVideoCaptureDevicesNV(final long n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"phDeviceList\" is not a direct buffer");
        }
        final long addressof_wglEnumerateVideoCaptureDevicesNV = this._context.getWGLExtProcAddressTable()._addressof_wglEnumerateVideoCaptureDevicesNV;
        if (addressof_wglEnumerateVideoCaptureDevicesNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglEnumerateVideoCaptureDevicesNV"));
        }
        return this.dispatch_wglEnumerateVideoCaptureDevicesNV0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_wglEnumerateVideoCaptureDevicesNV);
    }
    
    private native int dispatch_wglEnumerateVideoCaptureDevicesNV0(final long p0, final Object p1, final int p2, final long p3);
    
    @Override
    public boolean wglLockVideoCaptureDeviceNV(final long n, final long n2) {
        final long addressof_wglLockVideoCaptureDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglLockVideoCaptureDeviceNV;
        if (addressof_wglLockVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglLockVideoCaptureDeviceNV"));
        }
        return this.dispatch_wglLockVideoCaptureDeviceNV0(n, n2, addressof_wglLockVideoCaptureDeviceNV);
    }
    
    private native boolean dispatch_wglLockVideoCaptureDeviceNV0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean wglQueryVideoCaptureDeviceNV(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"piValue\" is not a direct buffer");
        }
        final long addressof_wglQueryVideoCaptureDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglQueryVideoCaptureDeviceNV;
        if (addressof_wglQueryVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglQueryVideoCaptureDeviceNV"));
        }
        return this.dispatch_wglQueryVideoCaptureDeviceNV0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_wglQueryVideoCaptureDeviceNV);
    }
    
    private native boolean dispatch_wglQueryVideoCaptureDeviceNV0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean wglReleaseVideoCaptureDeviceNV(final long n, final long n2) {
        final long addressof_wglReleaseVideoCaptureDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglReleaseVideoCaptureDeviceNV;
        if (addressof_wglReleaseVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleaseVideoCaptureDeviceNV"));
        }
        return this.dispatch_wglReleaseVideoCaptureDeviceNV0(n, n2, addressof_wglReleaseVideoCaptureDeviceNV);
    }
    
    private native boolean dispatch_wglReleaseVideoCaptureDeviceNV0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean wglGetVideoDeviceNV(final long n, final int n2, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"hVideoDevice\" is not a direct buffer");
        }
        final long addressof_wglGetVideoDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglGetVideoDeviceNV;
        if (addressof_wglGetVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetVideoDeviceNV"));
        }
        return this.dispatch_wglGetVideoDeviceNV0(n, n2, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_wglGetVideoDeviceNV);
    }
    
    private native boolean dispatch_wglGetVideoDeviceNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean wglReleaseVideoDeviceNV(final long n) {
        final long addressof_wglReleaseVideoDeviceNV = this._context.getWGLExtProcAddressTable()._addressof_wglReleaseVideoDeviceNV;
        if (addressof_wglReleaseVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleaseVideoDeviceNV"));
        }
        return this.dispatch_wglReleaseVideoDeviceNV0(n, addressof_wglReleaseVideoDeviceNV);
    }
    
    private native boolean dispatch_wglReleaseVideoDeviceNV0(final long p0, final long p1);
    
    @Override
    public boolean wglBindVideoImageNV(final long n, final long n2, final int n3) {
        final long addressof_wglBindVideoImageNV = this._context.getWGLExtProcAddressTable()._addressof_wglBindVideoImageNV;
        if (addressof_wglBindVideoImageNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglBindVideoImageNV"));
        }
        return this.dispatch_wglBindVideoImageNV0(n, n2, n3, addressof_wglBindVideoImageNV);
    }
    
    private native boolean dispatch_wglBindVideoImageNV0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public boolean wglReleaseVideoImageNV(final long n, final int n2) {
        final long addressof_wglReleaseVideoImageNV = this._context.getWGLExtProcAddressTable()._addressof_wglReleaseVideoImageNV;
        if (addressof_wglReleaseVideoImageNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglReleaseVideoImageNV"));
        }
        return this.dispatch_wglReleaseVideoImageNV0(n, n2, addressof_wglReleaseVideoImageNV);
    }
    
    private native boolean dispatch_wglReleaseVideoImageNV0(final long p0, final int p1, final long p2);
    
    @Override
    public boolean wglSendPbufferToVideoNV(final long n, final int n2, final LongBuffer longBuffer, final boolean b) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"pulCounterPbuffer\" is not a direct buffer");
        }
        final long addressof_wglSendPbufferToVideoNV = this._context.getWGLExtProcAddressTable()._addressof_wglSendPbufferToVideoNV;
        if (addressof_wglSendPbufferToVideoNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglSendPbufferToVideoNV"));
        }
        return this.dispatch_wglSendPbufferToVideoNV0(n, n2, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), b, addressof_wglSendPbufferToVideoNV);
    }
    
    private native boolean dispatch_wglSendPbufferToVideoNV0(final long p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public boolean wglGetVideoInfoNV(final long n, final LongBuffer longBuffer, final LongBuffer longBuffer2) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"pulCounterOutputPbuffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer2)) {
            throw new GLException("Argument \"pulCounterOutputVideo\" is not a direct buffer");
        }
        final long addressof_wglGetVideoInfoNV = this._context.getWGLExtProcAddressTable()._addressof_wglGetVideoInfoNV;
        if (addressof_wglGetVideoInfoNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "wglGetVideoInfoNV"));
        }
        return this.dispatch_wglGetVideoInfoNV0(n, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), longBuffer2, Buffers.getDirectBufferByteOffset(longBuffer2), addressof_wglGetVideoInfoNV);
    }
    
    private native boolean dispatch_wglGetVideoInfoNV0(final long p0, final Object p1, final int p2, final Object p3, final int p4, final long p5);
    
    public WGLExtImpl(final WindowsWGLContext context) {
        this._context = context;
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this._context.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this._context.isExtensionAvailable(s);
    }
}
