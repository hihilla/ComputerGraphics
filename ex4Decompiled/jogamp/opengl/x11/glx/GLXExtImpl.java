// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.x11.glx;

import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ByteBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLException;

public class GLXExtImpl implements GLXExt
{
    private X11GLXContext _context;
    
    public long glXGetProcAddress(final String s) {
        final long addressof_glXGetProcAddress = this._context.getGLXExtProcAddressTable()._addressof_glXGetProcAddress;
        if (addressof_glXGetProcAddress == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetProcAddress"));
        }
        return this.dispatch_glXGetProcAddress0(s, addressof_glXGetProcAddress);
    }
    
    private native long dispatch_glXGetProcAddress0(final String p0, final long p1);
    
    public long glXGetProcAddressARB(final String s) {
        final long addressof_glXGetProcAddressARB = this._context.getGLXExtProcAddressTable()._addressof_glXGetProcAddressARB;
        if (addressof_glXGetProcAddressARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetProcAddressARB"));
        }
        return this.dispatch_glXGetProcAddressARB0(s, addressof_glXGetProcAddressARB);
    }
    
    private native long dispatch_glXGetProcAddressARB0(final String p0, final long p1);
    
    @Override
    public long glXCreateContextAttribsARB(final long n, final long n2, final long n3, final boolean b, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXCreateContextAttribsARB = this._context.getGLXExtProcAddressTable()._addressof_glXCreateContextAttribsARB;
        if (addressof_glXCreateContextAttribsARB == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateContextAttribsARB"));
        }
        return this.dispatch_glXCreateContextAttribsARB0(n, n2, n3, b, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXCreateContextAttribsARB);
    }
    
    private native long dispatch_glXCreateContextAttribsARB0(final long p0, final long p1, final long p2, final boolean p3, final Object p4, final int p5, final long p6);
    
    @Override
    public int glXGetGPUIDsAMD(final int n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"ids\" is not a direct buffer");
        }
        final long addressof_glXGetGPUIDsAMD = this._context.getGLXExtProcAddressTable()._addressof_glXGetGPUIDsAMD;
        if (addressof_glXGetGPUIDsAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetGPUIDsAMD"));
        }
        return this.dispatch_glXGetGPUIDsAMD0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetGPUIDsAMD);
    }
    
    private native int dispatch_glXGetGPUIDsAMD0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public int glXGetGPUInfoAMD(final int n, final int n2, final int n3, final int n4, final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"data\" is not a direct buffer");
        }
        final long addressof_glXGetGPUInfoAMD = this._context.getGLXExtProcAddressTable()._addressof_glXGetGPUInfoAMD;
        if (addressof_glXGetGPUInfoAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetGPUInfoAMD"));
        }
        return this.dispatch_glXGetGPUInfoAMD0(n, n2, n3, n4, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glXGetGPUInfoAMD);
    }
    
    private native int dispatch_glXGetGPUInfoAMD0(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public int glXGetContextGPUIDAMD(final long n) {
        final long addressof_glXGetContextGPUIDAMD = this._context.getGLXExtProcAddressTable()._addressof_glXGetContextGPUIDAMD;
        if (addressof_glXGetContextGPUIDAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetContextGPUIDAMD"));
        }
        return this.dispatch_glXGetContextGPUIDAMD0(n, addressof_glXGetContextGPUIDAMD);
    }
    
    private native int dispatch_glXGetContextGPUIDAMD0(final long p0, final long p1);
    
    @Override
    public long glXCreateAssociatedContextAMD(final int n, final long n2) {
        final long addressof_glXCreateAssociatedContextAMD = this._context.getGLXExtProcAddressTable()._addressof_glXCreateAssociatedContextAMD;
        if (addressof_glXCreateAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateAssociatedContextAMD"));
        }
        return this.dispatch_glXCreateAssociatedContextAMD0(n, n2, addressof_glXCreateAssociatedContextAMD);
    }
    
    private native long dispatch_glXCreateAssociatedContextAMD0(final int p0, final long p1, final long p2);
    
    @Override
    public long glXCreateAssociatedContextAttribsAMD(final int n, final long n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attribList\" is not a direct buffer");
        }
        final long addressof_glXCreateAssociatedContextAttribsAMD = this._context.getGLXExtProcAddressTable()._addressof_glXCreateAssociatedContextAttribsAMD;
        if (addressof_glXCreateAssociatedContextAttribsAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCreateAssociatedContextAttribsAMD"));
        }
        return this.dispatch_glXCreateAssociatedContextAttribsAMD0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXCreateAssociatedContextAttribsAMD);
    }
    
    private native long dispatch_glXCreateAssociatedContextAttribsAMD0(final int p0, final long p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean glXDeleteAssociatedContextAMD(final long n) {
        final long addressof_glXDeleteAssociatedContextAMD = this._context.getGLXExtProcAddressTable()._addressof_glXDeleteAssociatedContextAMD;
        if (addressof_glXDeleteAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDeleteAssociatedContextAMD"));
        }
        return this.dispatch_glXDeleteAssociatedContextAMD0(n, addressof_glXDeleteAssociatedContextAMD);
    }
    
    private native boolean dispatch_glXDeleteAssociatedContextAMD0(final long p0, final long p1);
    
    @Override
    public boolean glXMakeAssociatedContextCurrentAMD(final long n) {
        final long addressof_glXMakeAssociatedContextCurrentAMD = this._context.getGLXExtProcAddressTable()._addressof_glXMakeAssociatedContextCurrentAMD;
        if (addressof_glXMakeAssociatedContextCurrentAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXMakeAssociatedContextCurrentAMD"));
        }
        return this.dispatch_glXMakeAssociatedContextCurrentAMD0(n, addressof_glXMakeAssociatedContextCurrentAMD);
    }
    
    private native boolean dispatch_glXMakeAssociatedContextCurrentAMD0(final long p0, final long p1);
    
    @Override
    public long glXGetCurrentAssociatedContextAMD() {
        final long addressof_glXGetCurrentAssociatedContextAMD = this._context.getGLXExtProcAddressTable()._addressof_glXGetCurrentAssociatedContextAMD;
        if (addressof_glXGetCurrentAssociatedContextAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentAssociatedContextAMD"));
        }
        return this.dispatch_glXGetCurrentAssociatedContextAMD0(addressof_glXGetCurrentAssociatedContextAMD);
    }
    
    private native long dispatch_glXGetCurrentAssociatedContextAMD0(final long p0);
    
    @Override
    public void glXBlitContextFramebufferAMD(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11) {
        final long addressof_glXBlitContextFramebufferAMD = this._context.getGLXExtProcAddressTable()._addressof_glXBlitContextFramebufferAMD;
        if (addressof_glXBlitContextFramebufferAMD == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBlitContextFramebufferAMD"));
        }
        this.dispatch_glXBlitContextFramebufferAMD0(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, addressof_glXBlitContextFramebufferAMD);
    }
    
    private native void dispatch_glXBlitContextFramebufferAMD0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final long p11);
    
    @Override
    public long glXGetCurrentDisplayEXT() {
        final long addressof_glXGetCurrentDisplayEXT = this._context.getGLXExtProcAddressTable()._addressof_glXGetCurrentDisplayEXT;
        if (addressof_glXGetCurrentDisplayEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentDisplayEXT"));
        }
        return this.dispatch_glXGetCurrentDisplayEXT0(addressof_glXGetCurrentDisplayEXT);
    }
    
    private native long dispatch_glXGetCurrentDisplayEXT0(final long p0);
    
    @Override
    public int glXQueryContextInfoEXT(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryContextInfoEXT = this._context.getGLXExtProcAddressTable()._addressof_glXQueryContextInfoEXT;
        if (addressof_glXQueryContextInfoEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryContextInfoEXT"));
        }
        return this.dispatch_glXQueryContextInfoEXT0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryContextInfoEXT);
    }
    
    private native int dispatch_glXQueryContextInfoEXT0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public long glXGetContextIDEXT(final long n) {
        final long addressof_glXGetContextIDEXT = this._context.getGLXExtProcAddressTable()._addressof_glXGetContextIDEXT;
        if (addressof_glXGetContextIDEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetContextIDEXT"));
        }
        return this.dispatch_glXGetContextIDEXT0(n, addressof_glXGetContextIDEXT);
    }
    
    private native long dispatch_glXGetContextIDEXT0(final long p0, final long p1);
    
    @Override
    public long glXImportContextEXT(final long n, final long n2) {
        final long addressof_glXImportContextEXT = this._context.getGLXExtProcAddressTable()._addressof_glXImportContextEXT;
        if (addressof_glXImportContextEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXImportContextEXT"));
        }
        return this.dispatch_glXImportContextEXT0(n, n2, addressof_glXImportContextEXT);
    }
    
    private native long dispatch_glXImportContextEXT0(final long p0, final long p1, final long p2);
    
    @Override
    public void glXFreeContextEXT(final long n, final long n2) {
        final long addressof_glXFreeContextEXT = this._context.getGLXExtProcAddressTable()._addressof_glXFreeContextEXT;
        if (addressof_glXFreeContextEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXFreeContextEXT"));
        }
        this.dispatch_glXFreeContextEXT0(n, n2, addressof_glXFreeContextEXT);
    }
    
    private native void dispatch_glXFreeContextEXT0(final long p0, final long p1, final long p2);
    
    public void glXSwapIntervalEXT(final long n, final long n2, final int n3) {
        final long addressof_glXSwapIntervalEXT = this._context.getGLXExtProcAddressTable()._addressof_glXSwapIntervalEXT;
        if (addressof_glXSwapIntervalEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSwapIntervalEXT"));
        }
        this.dispatch_glXSwapIntervalEXT0(n, n2, n3, addressof_glXSwapIntervalEXT);
    }
    
    private native void dispatch_glXSwapIntervalEXT0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public void glXBindTexImageEXT(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXBindTexImageEXT = this._context.getGLXExtProcAddressTable()._addressof_glXBindTexImageEXT;
        if (addressof_glXBindTexImageEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindTexImageEXT"));
        }
        this.dispatch_glXBindTexImageEXT0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXBindTexImageEXT);
    }
    
    private native void dispatch_glXBindTexImageEXT0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public void glXReleaseTexImageEXT(final long n, final long n2, final int n3) {
        final long addressof_glXReleaseTexImageEXT = this._context.getGLXExtProcAddressTable()._addressof_glXReleaseTexImageEXT;
        if (addressof_glXReleaseTexImageEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXReleaseTexImageEXT"));
        }
        this.dispatch_glXReleaseTexImageEXT0(n, n2, n3, addressof_glXReleaseTexImageEXT);
    }
    
    private native void dispatch_glXReleaseTexImageEXT0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public int glXGetAGPOffsetMESA(final Buffer buffer) {
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glXGetAGPOffsetMESA = this._context.getGLXExtProcAddressTable()._addressof_glXGetAGPOffsetMESA;
        if (addressof_glXGetAGPOffsetMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetAGPOffsetMESA"));
        }
        return this.dispatch_glXGetAGPOffsetMESA0(buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glXGetAGPOffsetMESA);
    }
    
    private native int dispatch_glXGetAGPOffsetMESA0(final Object p0, final int p1, final long p2);
    
    @Override
    public void glXCopySubBufferMESA(final long n, final long n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glXCopySubBufferMESA = this._context.getGLXExtProcAddressTable()._addressof_glXCopySubBufferMESA;
        if (addressof_glXCopySubBufferMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCopySubBufferMESA"));
        }
        this.dispatch_glXCopySubBufferMESA0(n, n2, n3, n4, n5, n6, addressof_glXCopySubBufferMESA);
    }
    
    private native void dispatch_glXCopySubBufferMESA0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public boolean glXQueryCurrentRendererIntegerMESA(final int n, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryCurrentRendererIntegerMESA = this._context.getGLXExtProcAddressTable()._addressof_glXQueryCurrentRendererIntegerMESA;
        if (addressof_glXQueryCurrentRendererIntegerMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryCurrentRendererIntegerMESA"));
        }
        return this.dispatch_glXQueryCurrentRendererIntegerMESA0(n, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryCurrentRendererIntegerMESA);
    }
    
    private native boolean dispatch_glXQueryCurrentRendererIntegerMESA0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public ByteBuffer glXQueryCurrentRendererStringMESA(final int n) {
        final long addressof_glXQueryCurrentRendererStringMESA = this._context.getGLXExtProcAddressTable()._addressof_glXQueryCurrentRendererStringMESA;
        if (addressof_glXQueryCurrentRendererStringMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryCurrentRendererStringMESA"));
        }
        final ByteBuffer dispatch_glXQueryCurrentRendererStringMESA0 = this.dispatch_glXQueryCurrentRendererStringMESA0(n, addressof_glXQueryCurrentRendererStringMESA);
        if (dispatch_glXQueryCurrentRendererStringMESA0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_glXQueryCurrentRendererStringMESA0);
        return dispatch_glXQueryCurrentRendererStringMESA0;
    }
    
    private native ByteBuffer dispatch_glXQueryCurrentRendererStringMESA0(final int p0, final long p1);
    
    @Override
    public boolean glXQueryRendererIntegerMESA(final long n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryRendererIntegerMESA = this._context.getGLXExtProcAddressTable()._addressof_glXQueryRendererIntegerMESA;
        if (addressof_glXQueryRendererIntegerMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryRendererIntegerMESA"));
        }
        return this.dispatch_glXQueryRendererIntegerMESA0(n, n2, n3, n4, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryRendererIntegerMESA);
    }
    
    private native boolean dispatch_glXQueryRendererIntegerMESA0(final long p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public ByteBuffer glXQueryRendererStringMESA(final long n, final int n2, final int n3, final int n4) {
        final long addressof_glXQueryRendererStringMESA = this._context.getGLXExtProcAddressTable()._addressof_glXQueryRendererStringMESA;
        if (addressof_glXQueryRendererStringMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryRendererStringMESA"));
        }
        final ByteBuffer dispatch_glXQueryRendererStringMESA0 = this.dispatch_glXQueryRendererStringMESA0(n, n2, n3, n4, addressof_glXQueryRendererStringMESA);
        if (dispatch_glXQueryRendererStringMESA0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_glXQueryRendererStringMESA0);
        return dispatch_glXQueryRendererStringMESA0;
    }
    
    private native ByteBuffer dispatch_glXQueryRendererStringMESA0(final long p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public boolean glXReleaseBuffersMESA(final long n, final long n2) {
        final long addressof_glXReleaseBuffersMESA = this._context.getGLXExtProcAddressTable()._addressof_glXReleaseBuffersMESA;
        if (addressof_glXReleaseBuffersMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXReleaseBuffersMESA"));
        }
        return this.dispatch_glXReleaseBuffersMESA0(n, n2, addressof_glXReleaseBuffersMESA);
    }
    
    private native boolean dispatch_glXReleaseBuffersMESA0(final long p0, final long p1, final long p2);
    
    @Override
    public boolean glXSet3DfxModeMESA(final int n) {
        final long addressof_glXSet3DfxModeMESA = this._context.getGLXExtProcAddressTable()._addressof_glXSet3DfxModeMESA;
        if (addressof_glXSet3DfxModeMESA == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSet3DfxModeMESA"));
        }
        return this.dispatch_glXSet3DfxModeMESA0(n, addressof_glXSet3DfxModeMESA);
    }
    
    private native boolean dispatch_glXSet3DfxModeMESA0(final int p0, final long p1);
    
    @Override
    public void glXCopyBufferSubDataNV(final long n, final long n2, final long n3, final int n4, final int n5, final long n6, final long n7, final long n8) {
        final long addressof_glXCopyBufferSubDataNV = this._context.getGLXExtProcAddressTable()._addressof_glXCopyBufferSubDataNV;
        if (addressof_glXCopyBufferSubDataNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCopyBufferSubDataNV"));
        }
        this.dispatch_glXCopyBufferSubDataNV0(n, n2, n3, n4, n5, n6, n7, n8, addressof_glXCopyBufferSubDataNV);
    }
    
    private native void dispatch_glXCopyBufferSubDataNV0(final long p0, final long p1, final long p2, final int p3, final int p4, final long p5, final long p6, final long p7, final long p8);
    
    @Override
    public void glXNamedCopyBufferSubDataNV(final long n, final long n2, final long n3, final int n4, final int n5, final long n6, final long n7, final long n8) {
        final long addressof_glXNamedCopyBufferSubDataNV = this._context.getGLXExtProcAddressTable()._addressof_glXNamedCopyBufferSubDataNV;
        if (addressof_glXNamedCopyBufferSubDataNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXNamedCopyBufferSubDataNV"));
        }
        this.dispatch_glXNamedCopyBufferSubDataNV0(n, n2, n3, n4, n5, n6, n7, n8, addressof_glXNamedCopyBufferSubDataNV);
    }
    
    private native void dispatch_glXNamedCopyBufferSubDataNV0(final long p0, final long p1, final long p2, final int p3, final int p4, final long p5, final long p6, final long p7, final long p8);
    
    @Override
    public void glXCopyImageSubDataNV(final long n, final long n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15, final int n16, final int n17, final int n18) {
        final long addressof_glXCopyImageSubDataNV = this._context.getGLXExtProcAddressTable()._addressof_glXCopyImageSubDataNV;
        if (addressof_glXCopyImageSubDataNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCopyImageSubDataNV"));
        }
        this.dispatch_glXCopyImageSubDataNV0(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, addressof_glXCopyImageSubDataNV);
    }
    
    private native void dispatch_glXCopyImageSubDataNV0(final long p0, final long p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final int p15, final int p16, final int p17, final long p18);
    
    @Override
    public boolean glXDelayBeforeSwapNV(final long n, final long n2, final float n3) {
        final long addressof_glXDelayBeforeSwapNV = this._context.getGLXExtProcAddressTable()._addressof_glXDelayBeforeSwapNV;
        if (addressof_glXDelayBeforeSwapNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXDelayBeforeSwapNV"));
        }
        return this.dispatch_glXDelayBeforeSwapNV0(n, n2, n3, addressof_glXDelayBeforeSwapNV);
    }
    
    private native boolean dispatch_glXDelayBeforeSwapNV0(final long p0, final long p1, final float p2, final long p3);
    
    @Override
    public IntBuffer glXEnumerateVideoDevicesNV(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"nelements\" is not a direct buffer");
        }
        final long addressof_glXEnumerateVideoDevicesNV = this._context.getGLXExtProcAddressTable()._addressof_glXEnumerateVideoDevicesNV;
        if (addressof_glXEnumerateVideoDevicesNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXEnumerateVideoDevicesNV"));
        }
        final ByteBuffer dispatch_glXEnumerateVideoDevicesNV0 = this.dispatch_glXEnumerateVideoDevicesNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXEnumerateVideoDevicesNV);
        if (dispatch_glXEnumerateVideoDevicesNV0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_glXEnumerateVideoDevicesNV0);
        return dispatch_glXEnumerateVideoDevicesNV0.asIntBuffer();
    }
    
    private native ByteBuffer dispatch_glXEnumerateVideoDevicesNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public int glXBindVideoDeviceNV(final long n, final int n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"attrib_list\" is not a direct buffer");
        }
        final long addressof_glXBindVideoDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXBindVideoDeviceNV;
        if (addressof_glXBindVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindVideoDeviceNV"));
        }
        return this.dispatch_glXBindVideoDeviceNV0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXBindVideoDeviceNV);
    }
    
    private native int dispatch_glXBindVideoDeviceNV0(final long p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public boolean glXJoinSwapGroupNV(final long n, final long n2, final int n3) {
        final long addressof_glXJoinSwapGroupNV = this._context.getGLXExtProcAddressTable()._addressof_glXJoinSwapGroupNV;
        if (addressof_glXJoinSwapGroupNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXJoinSwapGroupNV"));
        }
        return this.dispatch_glXJoinSwapGroupNV0(n, n2, n3, addressof_glXJoinSwapGroupNV);
    }
    
    private native boolean dispatch_glXJoinSwapGroupNV0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public boolean glXBindSwapBarrierNV(final long n, final int n2, final int n3) {
        final long addressof_glXBindSwapBarrierNV = this._context.getGLXExtProcAddressTable()._addressof_glXBindSwapBarrierNV;
        if (addressof_glXBindSwapBarrierNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindSwapBarrierNV"));
        }
        return this.dispatch_glXBindSwapBarrierNV0(n, n2, n3, addressof_glXBindSwapBarrierNV);
    }
    
    private native boolean dispatch_glXBindSwapBarrierNV0(final long p0, final int p1, final int p2, final long p3);
    
    @Override
    public boolean glXQuerySwapGroupNV(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"group\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"barrier\" is not a direct buffer");
        }
        final long addressof_glXQuerySwapGroupNV = this._context.getGLXExtProcAddressTable()._addressof_glXQuerySwapGroupNV;
        if (addressof_glXQuerySwapGroupNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQuerySwapGroupNV"));
        }
        return this.dispatch_glXQuerySwapGroupNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXQuerySwapGroupNV);
    }
    
    private native boolean dispatch_glXQuerySwapGroupNV0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean glXQueryMaxSwapGroupsNV(final long n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"maxGroups\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"maxBarriers\" is not a direct buffer");
        }
        final long addressof_glXQueryMaxSwapGroupsNV = this._context.getGLXExtProcAddressTable()._addressof_glXQueryMaxSwapGroupsNV;
        if (addressof_glXQueryMaxSwapGroupsNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryMaxSwapGroupsNV"));
        }
        return this.dispatch_glXQueryMaxSwapGroupsNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXQueryMaxSwapGroupsNV);
    }
    
    private native boolean dispatch_glXQueryMaxSwapGroupsNV0(final long p0, final int p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public boolean glXQueryFrameCountNV(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"count\" is not a direct buffer");
        }
        final long addressof_glXQueryFrameCountNV = this._context.getGLXExtProcAddressTable()._addressof_glXQueryFrameCountNV;
        if (addressof_glXQueryFrameCountNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryFrameCountNV"));
        }
        return this.dispatch_glXQueryFrameCountNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryFrameCountNV);
    }
    
    private native boolean dispatch_glXQueryFrameCountNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public boolean glXResetFrameCountNV(final long n, final int n2) {
        final long addressof_glXResetFrameCountNV = this._context.getGLXExtProcAddressTable()._addressof_glXResetFrameCountNV;
        if (addressof_glXResetFrameCountNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXResetFrameCountNV"));
        }
        return this.dispatch_glXResetFrameCountNV0(n, n2, addressof_glXResetFrameCountNV);
    }
    
    private native boolean dispatch_glXResetFrameCountNV0(final long p0, final int p1, final long p2);
    
    @Override
    public int glXBindVideoCaptureDeviceNV(final long n, final int n2, final long n3) {
        final long addressof_glXBindVideoCaptureDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXBindVideoCaptureDeviceNV;
        if (addressof_glXBindVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindVideoCaptureDeviceNV"));
        }
        return this.dispatch_glXBindVideoCaptureDeviceNV0(n, n2, n3, addressof_glXBindVideoCaptureDeviceNV);
    }
    
    private native int dispatch_glXBindVideoCaptureDeviceNV0(final long p0, final int p1, final long p2, final long p3);
    
    @Override
    public PointerBuffer glXEnumerateVideoCaptureDevicesNV(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"nelements\" is not a direct buffer");
        }
        final long addressof_glXEnumerateVideoCaptureDevicesNV = this._context.getGLXExtProcAddressTable()._addressof_glXEnumerateVideoCaptureDevicesNV;
        if (addressof_glXEnumerateVideoCaptureDevicesNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXEnumerateVideoCaptureDevicesNV"));
        }
        final ByteBuffer dispatch_glXEnumerateVideoCaptureDevicesNV0 = this.dispatch_glXEnumerateVideoCaptureDevicesNV0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXEnumerateVideoCaptureDevicesNV);
        if (dispatch_glXEnumerateVideoCaptureDevicesNV0 == null) {
            return null;
        }
        Buffers.nativeOrder(dispatch_glXEnumerateVideoCaptureDevicesNV0);
        return PointerBuffer.wrap(dispatch_glXEnumerateVideoCaptureDevicesNV0);
    }
    
    private native ByteBuffer dispatch_glXEnumerateVideoCaptureDevicesNV0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public void glXLockVideoCaptureDeviceNV(final long n, final long n2) {
        final long addressof_glXLockVideoCaptureDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXLockVideoCaptureDeviceNV;
        if (addressof_glXLockVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXLockVideoCaptureDeviceNV"));
        }
        this.dispatch_glXLockVideoCaptureDeviceNV0(n, n2, addressof_glXLockVideoCaptureDeviceNV);
    }
    
    private native void dispatch_glXLockVideoCaptureDeviceNV0(final long p0, final long p1, final long p2);
    
    @Override
    public int glXQueryVideoCaptureDeviceNV(final long n, final long n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"value\" is not a direct buffer");
        }
        final long addressof_glXQueryVideoCaptureDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXQueryVideoCaptureDeviceNV;
        if (addressof_glXQueryVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryVideoCaptureDeviceNV"));
        }
        return this.dispatch_glXQueryVideoCaptureDeviceNV0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryVideoCaptureDeviceNV);
    }
    
    private native int dispatch_glXQueryVideoCaptureDeviceNV0(final long p0, final long p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public void glXReleaseVideoCaptureDeviceNV(final long n, final long n2) {
        final long addressof_glXReleaseVideoCaptureDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXReleaseVideoCaptureDeviceNV;
        if (addressof_glXReleaseVideoCaptureDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXReleaseVideoCaptureDeviceNV"));
        }
        this.dispatch_glXReleaseVideoCaptureDeviceNV0(n, n2, addressof_glXReleaseVideoCaptureDeviceNV);
    }
    
    private native void dispatch_glXReleaseVideoCaptureDeviceNV0(final long p0, final long p1, final long p2);
    
    @Override
    public int glXGetVideoDeviceNV(final long n, final int n2, final int n3, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"pVideoDevice\" is not a direct buffer");
        }
        final long addressof_glXGetVideoDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXGetVideoDeviceNV;
        if (addressof_glXGetVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetVideoDeviceNV"));
        }
        return this.dispatch_glXGetVideoDeviceNV0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetVideoDeviceNV);
    }
    
    private native int dispatch_glXGetVideoDeviceNV0(final long p0, final int p1, final int p2, final Object p3, final int p4, final long p5);
    
    @Override
    public int glXReleaseVideoDeviceNV(final long n, final int n2, final int n3) {
        final long addressof_glXReleaseVideoDeviceNV = this._context.getGLXExtProcAddressTable()._addressof_glXReleaseVideoDeviceNV;
        if (addressof_glXReleaseVideoDeviceNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXReleaseVideoDeviceNV"));
        }
        return this.dispatch_glXReleaseVideoDeviceNV0(n, n2, n3, addressof_glXReleaseVideoDeviceNV);
    }
    
    private native int dispatch_glXReleaseVideoDeviceNV0(final long p0, final int p1, final int p2, final long p3);
    
    @Override
    public int glXBindVideoImageNV(final long n, final int n2, final long n3, final int n4) {
        final long addressof_glXBindVideoImageNV = this._context.getGLXExtProcAddressTable()._addressof_glXBindVideoImageNV;
        if (addressof_glXBindVideoImageNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindVideoImageNV"));
        }
        return this.dispatch_glXBindVideoImageNV0(n, n2, n3, n4, addressof_glXBindVideoImageNV);
    }
    
    private native int dispatch_glXBindVideoImageNV0(final long p0, final int p1, final long p2, final int p3, final long p4);
    
    @Override
    public int glXReleaseVideoImageNV(final long n, final long n2) {
        final long addressof_glXReleaseVideoImageNV = this._context.getGLXExtProcAddressTable()._addressof_glXReleaseVideoImageNV;
        if (addressof_glXReleaseVideoImageNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXReleaseVideoImageNV"));
        }
        return this.dispatch_glXReleaseVideoImageNV0(n, n2, addressof_glXReleaseVideoImageNV);
    }
    
    private native int dispatch_glXReleaseVideoImageNV0(final long p0, final long p1, final long p2);
    
    @Override
    public int glXSendPbufferToVideoNV(final long n, final long n2, final int n3, final LongBuffer longBuffer, final boolean b) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"pulCounterPbuffer\" is not a direct buffer");
        }
        final long addressof_glXSendPbufferToVideoNV = this._context.getGLXExtProcAddressTable()._addressof_glXSendPbufferToVideoNV;
        if (addressof_glXSendPbufferToVideoNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSendPbufferToVideoNV"));
        }
        return this.dispatch_glXSendPbufferToVideoNV0(n, n2, n3, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), b, addressof_glXSendPbufferToVideoNV);
    }
    
    private native int dispatch_glXSendPbufferToVideoNV0(final long p0, final long p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public int glXGetVideoInfoNV(final long n, final int n2, final int n3, final LongBuffer longBuffer, final LongBuffer longBuffer2) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"pulCounterOutputPbuffer\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer2)) {
            throw new GLException("Argument \"pulCounterOutputVideo\" is not a direct buffer");
        }
        final long addressof_glXGetVideoInfoNV = this._context.getGLXExtProcAddressTable()._addressof_glXGetVideoInfoNV;
        if (addressof_glXGetVideoInfoNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetVideoInfoNV"));
        }
        return this.dispatch_glXGetVideoInfoNV0(n, n2, n3, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), longBuffer2, Buffers.getDirectBufferByteOffset(longBuffer2), addressof_glXGetVideoInfoNV);
    }
    
    private native int dispatch_glXGetVideoInfoNV0(final long p0, final int p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final long p7);
    
    @Override
    public boolean glXGetSyncValuesOML(final long n, final long n2, final LongBuffer longBuffer, final LongBuffer longBuffer2, final LongBuffer longBuffer3) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"ust\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer2)) {
            throw new GLException("Argument \"msc\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer3)) {
            throw new GLException("Argument \"sbc\" is not a direct buffer");
        }
        final long addressof_glXGetSyncValuesOML = this._context.getGLXExtProcAddressTable()._addressof_glXGetSyncValuesOML;
        if (addressof_glXGetSyncValuesOML == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetSyncValuesOML"));
        }
        return this.dispatch_glXGetSyncValuesOML0(n, n2, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), longBuffer2, Buffers.getDirectBufferByteOffset(longBuffer2), longBuffer3, Buffers.getDirectBufferByteOffset(longBuffer3), addressof_glXGetSyncValuesOML);
    }
    
    private native boolean dispatch_glXGetSyncValuesOML0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final Object p6, final int p7, final long p8);
    
    @Override
    public boolean glXGetMscRateOML(final long n, final long n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"numerator\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"denominator\" is not a direct buffer");
        }
        final long addressof_glXGetMscRateOML = this._context.getGLXExtProcAddressTable()._addressof_glXGetMscRateOML;
        if (addressof_glXGetMscRateOML == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetMscRateOML"));
        }
        return this.dispatch_glXGetMscRateOML0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), addressof_glXGetMscRateOML);
    }
    
    private native boolean dispatch_glXGetMscRateOML0(final long p0, final long p1, final Object p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public long glXSwapBuffersMscOML(final long n, final long n2, final long n3, final long n4, final long n5) {
        final long addressof_glXSwapBuffersMscOML = this._context.getGLXExtProcAddressTable()._addressof_glXSwapBuffersMscOML;
        if (addressof_glXSwapBuffersMscOML == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSwapBuffersMscOML"));
        }
        return this.dispatch_glXSwapBuffersMscOML0(n, n2, n3, n4, n5, addressof_glXSwapBuffersMscOML);
    }
    
    private native long dispatch_glXSwapBuffersMscOML0(final long p0, final long p1, final long p2, final long p3, final long p4, final long p5);
    
    @Override
    public boolean glXWaitForMscOML(final long n, final long n2, final long n3, final long n4, final long n5, final LongBuffer longBuffer, final LongBuffer longBuffer2, final LongBuffer longBuffer3) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"ust\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer2)) {
            throw new GLException("Argument \"msc\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer3)) {
            throw new GLException("Argument \"sbc\" is not a direct buffer");
        }
        final long addressof_glXWaitForMscOML = this._context.getGLXExtProcAddressTable()._addressof_glXWaitForMscOML;
        if (addressof_glXWaitForMscOML == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXWaitForMscOML"));
        }
        return this.dispatch_glXWaitForMscOML0(n, n2, n3, n4, n5, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), longBuffer2, Buffers.getDirectBufferByteOffset(longBuffer2), longBuffer3, Buffers.getDirectBufferByteOffset(longBuffer3), addressof_glXWaitForMscOML);
    }
    
    private native boolean dispatch_glXWaitForMscOML0(final long p0, final long p1, final long p2, final long p3, final long p4, final Object p5, final int p6, final Object p7, final int p8, final Object p9, final int p10, final long p11);
    
    @Override
    public boolean glXWaitForSbcOML(final long n, final long n2, final long n3, final LongBuffer longBuffer, final LongBuffer longBuffer2, final LongBuffer longBuffer3) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"ust\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer2)) {
            throw new GLException("Argument \"msc\" is not a direct buffer");
        }
        if (!Buffers.isDirect(longBuffer3)) {
            throw new GLException("Argument \"sbc\" is not a direct buffer");
        }
        final long addressof_glXWaitForSbcOML = this._context.getGLXExtProcAddressTable()._addressof_glXWaitForSbcOML;
        if (addressof_glXWaitForSbcOML == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXWaitForSbcOML"));
        }
        return this.dispatch_glXWaitForSbcOML0(n, n2, n3, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), longBuffer2, Buffers.getDirectBufferByteOffset(longBuffer2), longBuffer3, Buffers.getDirectBufferByteOffset(longBuffer3), addressof_glXWaitForSbcOML);
    }
    
    private native boolean dispatch_glXWaitForSbcOML0(final long p0, final long p1, final long p2, final Object p3, final int p4, final Object p5, final int p6, final Object p7, final int p8, final long p9);
    
    @Override
    public void glXBindSwapBarrierSGIX(final long n, final long n2, final int n3) {
        final long addressof_glXBindSwapBarrierSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXBindSwapBarrierSGIX;
        if (addressof_glXBindSwapBarrierSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindSwapBarrierSGIX"));
        }
        this.dispatch_glXBindSwapBarrierSGIX0(n, n2, n3, addressof_glXBindSwapBarrierSGIX);
    }
    
    private native void dispatch_glXBindSwapBarrierSGIX0(final long p0, final long p1, final int p2, final long p3);
    
    @Override
    public boolean glXQueryMaxSwapBarriersSGIX(final long n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"max\" is not a direct buffer");
        }
        final long addressof_glXQueryMaxSwapBarriersSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXQueryMaxSwapBarriersSGIX;
        if (addressof_glXQueryMaxSwapBarriersSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryMaxSwapBarriersSGIX"));
        }
        return this.dispatch_glXQueryMaxSwapBarriersSGIX0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXQueryMaxSwapBarriersSGIX);
    }
    
    private native boolean dispatch_glXQueryMaxSwapBarriersSGIX0(final long p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public void glXJoinSwapGroupSGIX(final long n, final long n2, final long n3) {
        final long addressof_glXJoinSwapGroupSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXJoinSwapGroupSGIX;
        if (addressof_glXJoinSwapGroupSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXJoinSwapGroupSGIX"));
        }
        this.dispatch_glXJoinSwapGroupSGIX0(n, n2, n3, addressof_glXJoinSwapGroupSGIX);
    }
    
    private native void dispatch_glXJoinSwapGroupSGIX0(final long p0, final long p1, final long p2, final long p3);
    
    @Override
    public int glXBindChannelToWindowSGIX(final long n, final int n2, final int n3, final long n4) {
        final long addressof_glXBindChannelToWindowSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXBindChannelToWindowSGIX;
        if (addressof_glXBindChannelToWindowSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXBindChannelToWindowSGIX"));
        }
        return this.dispatch_glXBindChannelToWindowSGIX0(n, n2, n3, n4, addressof_glXBindChannelToWindowSGIX);
    }
    
    private native int dispatch_glXBindChannelToWindowSGIX0(final long p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public int glXChannelRectSGIX(final long n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long addressof_glXChannelRectSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXChannelRectSGIX;
        if (addressof_glXChannelRectSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXChannelRectSGIX"));
        }
        return this.dispatch_glXChannelRectSGIX0(n, n2, n3, n4, n5, n6, n7, addressof_glXChannelRectSGIX);
    }
    
    private native int dispatch_glXChannelRectSGIX0(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public int glXQueryChannelRectSGIX(final long n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"dx\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"dy\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new GLException("Argument \"dw\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer4)) {
            throw new GLException("Argument \"dh\" is not a direct buffer");
        }
        final long addressof_glXQueryChannelRectSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXQueryChannelRectSGIX;
        if (addressof_glXQueryChannelRectSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryChannelRectSGIX"));
        }
        return this.dispatch_glXQueryChannelRectSGIX0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), intBuffer4, Buffers.getDirectBufferByteOffset(intBuffer4), addressof_glXQueryChannelRectSGIX);
    }
    
    private native int dispatch_glXQueryChannelRectSGIX0(final long p0, final int p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final Object p7, final int p8, final Object p9, final int p10, final long p11);
    
    @Override
    public int glXQueryChannelDeltasSGIX(final long n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"x\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer2)) {
            throw new GLException("Argument \"y\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer3)) {
            throw new GLException("Argument \"w\" is not a direct buffer");
        }
        if (!Buffers.isDirect(intBuffer4)) {
            throw new GLException("Argument \"h\" is not a direct buffer");
        }
        final long addressof_glXQueryChannelDeltasSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXQueryChannelDeltasSGIX;
        if (addressof_glXQueryChannelDeltasSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXQueryChannelDeltasSGIX"));
        }
        return this.dispatch_glXQueryChannelDeltasSGIX0(n, n2, n3, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), intBuffer2, Buffers.getDirectBufferByteOffset(intBuffer2), intBuffer3, Buffers.getDirectBufferByteOffset(intBuffer3), intBuffer4, Buffers.getDirectBufferByteOffset(intBuffer4), addressof_glXQueryChannelDeltasSGIX);
    }
    
    private native int dispatch_glXQueryChannelDeltasSGIX0(final long p0, final int p1, final int p2, final Object p3, final int p4, final Object p5, final int p6, final Object p7, final int p8, final Object p9, final int p10, final long p11);
    
    @Override
    public int glXChannelRectSyncSGIX(final long n, final int n2, final int n3, final int n4) {
        final long addressof_glXChannelRectSyncSGIX = this._context.getGLXExtProcAddressTable()._addressof_glXChannelRectSyncSGIX;
        if (addressof_glXChannelRectSyncSGIX == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXChannelRectSyncSGIX"));
        }
        return this.dispatch_glXChannelRectSyncSGIX0(n, n2, n3, n4, addressof_glXChannelRectSyncSGIX);
    }
    
    private native int dispatch_glXChannelRectSyncSGIX0(final long p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glXCushionSGI(final long n, final long n2, final float n3) {
        final long addressof_glXCushionSGI = this._context.getGLXExtProcAddressTable()._addressof_glXCushionSGI;
        if (addressof_glXCushionSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXCushionSGI"));
        }
        this.dispatch_glXCushionSGI0(n, n2, n3, addressof_glXCushionSGI);
    }
    
    private native void dispatch_glXCushionSGI0(final long p0, final long p1, final float p2, final long p3);
    
    @Override
    public boolean glXMakeCurrentReadSGI(final long n, final long n2, final long n3, final long n4) {
        final long addressof_glXMakeCurrentReadSGI = this._context.getGLXExtProcAddressTable()._addressof_glXMakeCurrentReadSGI;
        if (addressof_glXMakeCurrentReadSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXMakeCurrentReadSGI"));
        }
        return this.dispatch_glXMakeCurrentReadSGI0(n, n2, n3, n4, addressof_glXMakeCurrentReadSGI);
    }
    
    private native boolean dispatch_glXMakeCurrentReadSGI0(final long p0, final long p1, final long p2, final long p3, final long p4);
    
    @Override
    public long glXGetCurrentReadDrawableSGI() {
        final long addressof_glXGetCurrentReadDrawableSGI = this._context.getGLXExtProcAddressTable()._addressof_glXGetCurrentReadDrawableSGI;
        if (addressof_glXGetCurrentReadDrawableSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetCurrentReadDrawableSGI"));
        }
        return this.dispatch_glXGetCurrentReadDrawableSGI0(addressof_glXGetCurrentReadDrawableSGI);
    }
    
    private native long dispatch_glXGetCurrentReadDrawableSGI0(final long p0);
    
    @Override
    public int glXSwapIntervalSGI(final int n) {
        final long addressof_glXSwapIntervalSGI = this._context.getGLXExtProcAddressTable()._addressof_glXSwapIntervalSGI;
        if (addressof_glXSwapIntervalSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXSwapIntervalSGI"));
        }
        return this.dispatch_glXSwapIntervalSGI0(n, addressof_glXSwapIntervalSGI);
    }
    
    private native int dispatch_glXSwapIntervalSGI0(final int p0, final long p1);
    
    @Override
    public int glXGetVideoSyncSGI(final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"count\" is not a direct buffer");
        }
        final long addressof_glXGetVideoSyncSGI = this._context.getGLXExtProcAddressTable()._addressof_glXGetVideoSyncSGI;
        if (addressof_glXGetVideoSyncSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetVideoSyncSGI"));
        }
        return this.dispatch_glXGetVideoSyncSGI0(intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXGetVideoSyncSGI);
    }
    
    private native int dispatch_glXGetVideoSyncSGI0(final Object p0, final int p1, final long p2);
    
    @Override
    public int glXWaitVideoSyncSGI(final int n, final int n2, final IntBuffer intBuffer) {
        if (!Buffers.isDirect(intBuffer)) {
            throw new GLException("Argument \"count\" is not a direct buffer");
        }
        final long addressof_glXWaitVideoSyncSGI = this._context.getGLXExtProcAddressTable()._addressof_glXWaitVideoSyncSGI;
        if (addressof_glXWaitVideoSyncSGI == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXWaitVideoSyncSGI"));
        }
        return this.dispatch_glXWaitVideoSyncSGI0(n, n2, intBuffer, Buffers.getDirectBufferByteOffset(intBuffer), addressof_glXWaitVideoSyncSGI);
    }
    
    private native int dispatch_glXWaitVideoSyncSGI0(final int p0, final int p1, final Object p2, final int p3, final long p4);
    
    @Override
    public int glXGetTransparentIndexSUN(final long n, final long n2, final long n3, final LongBuffer longBuffer) {
        if (!Buffers.isDirect(longBuffer)) {
            throw new GLException("Argument \"pTransparentIndex\" is not a direct buffer");
        }
        final long addressof_glXGetTransparentIndexSUN = this._context.getGLXExtProcAddressTable()._addressof_glXGetTransparentIndexSUN;
        if (addressof_glXGetTransparentIndexSUN == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glXGetTransparentIndexSUN"));
        }
        return this.dispatch_glXGetTransparentIndexSUN0(n, n2, n3, longBuffer, Buffers.getDirectBufferByteOffset(longBuffer), addressof_glXGetTransparentIndexSUN);
    }
    
    private native int dispatch_glXGetTransparentIndexSUN0(final long p0, final long p1, final long p2, final Object p3, final int p4, final long p5);
    
    public GLXExtImpl(final X11GLXContext context) {
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
