// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.es3;

import com.jogamp.opengl.GLUniformData;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLES1;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3bc;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLBufferStorage;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.LongBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.Buffer;
import com.jogamp.opengl.GLException;
import jogamp.opengl.GLBufferStateTracker;
import jogamp.opengl.GLBufferObjectTracker;
import jogamp.opengl.GLStateTracker;
import jogamp.opengl.GLContextImpl;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLES3;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLBase;

public class GLES3Impl implements GLBase, GL, GL2ES2, GLES2, GL2ES3, GL3ES3, GL4ES3, GLES3
{
    private static final int params_offset = 0;
    private static final int data_offset = 0;
    private final GLProfile glProfile;
    private final GLContextImpl _context;
    private final GLStateTracker glStateTracker;
    private final GLBufferObjectTracker bufferObjectTracker;
    private final GLBufferStateTracker bufferStateTracker;
    private final GLBufferObjectTracker.CreateStorageDispatch glBufferDataDispatch;
    private final GLBufferObjectTracker.UnmapBufferDispatch glUnmapBufferDispatch;
    private final GLBufferObjectTracker.MapBufferAllDispatch glMapBufferDispatch;
    private final GLBufferObjectTracker.MapBufferRangeDispatch glMapBufferRangeDispatch;
    private final GLES3ProcAddressTable _pat;
    private int[] imageSizeTemp;
    private final boolean _isES3;
    
    @Override
    public void glActiveTexture(final int n) {
        final long addressof_glActiveTexture = this._pat._addressof_glActiveTexture;
        if (addressof_glActiveTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glActiveTexture"));
        }
        this.dispatch_glActiveTexture1(n, addressof_glActiveTexture);
    }
    
    private native void dispatch_glActiveTexture1(final int p0, final long p1);
    
    @Override
    public void glAttachShader(final int n, final int n2) {
        final long addressof_glAttachShader = this._pat._addressof_glAttachShader;
        if (addressof_glAttachShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glAttachShader"));
        }
        this.dispatch_glAttachShader1(n, n2, addressof_glAttachShader);
    }
    
    private native void dispatch_glAttachShader1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindAttribLocation(final int n, final int n2, final String s) {
        final long addressof_glBindAttribLocation = this._pat._addressof_glBindAttribLocation;
        if (addressof_glBindAttribLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindAttribLocation"));
        }
        this.dispatch_glBindAttribLocation1(n, n2, s, addressof_glBindAttribLocation);
    }
    
    private native void dispatch_glBindAttribLocation1(final int p0, final int p1, final String p2, final long p3);
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        final long addressof_glBindBuffer = this._pat._addressof_glBindBuffer;
        if (addressof_glBindBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindBuffer"));
        }
        this.dispatch_glBindBuffer1(n, n2, addressof_glBindBuffer);
        this.bufferStateTracker.setBoundBufferObject(n, n2);
    }
    
    private native void dispatch_glBindBuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindFramebuffer(final int n, int n2) {
        if (0 == n2) {
            if (36160 == n || 36009 == n) {
                n2 = this._context.getDefaultDrawFramebuffer();
            }
            else if (36008 == n) {
                n2 = this._context.getDefaultReadFramebuffer();
            }
        }
        final long addressof_glBindFramebuffer = this._pat._addressof_glBindFramebuffer;
        if (addressof_glBindFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFramebuffer"));
        }
        this.dispatch_glBindFramebuffer1(n, n2, addressof_glBindFramebuffer);
        this._context.setBoundFramebuffer(n, n2);
    }
    
    private native void dispatch_glBindFramebuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        final long addressof_glBindRenderbuffer = this._pat._addressof_glBindRenderbuffer;
        if (addressof_glBindRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindRenderbuffer"));
        }
        this.dispatch_glBindRenderbuffer1(n, n2, addressof_glBindRenderbuffer);
    }
    
    private native void dispatch_glBindRenderbuffer1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        final long addressof_glBindTexture = this._pat._addressof_glBindTexture;
        if (addressof_glBindTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindTexture"));
        }
        this.dispatch_glBindTexture1(n, n2, addressof_glBindTexture);
    }
    
    private native void dispatch_glBindTexture1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendColor(final float n, final float n2, final float n3, final float n4) {
        final long addressof_glBlendColor = this._pat._addressof_glBlendColor;
        if (addressof_glBlendColor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendColor"));
        }
        this.dispatch_glBlendColor1(n, n2, n3, n4, addressof_glBlendColor);
    }
    
    private native void dispatch_glBlendColor1(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glBlendEquation(final int n) {
        final long addressof_glBlendEquation = this._pat._addressof_glBlendEquation;
        if (addressof_glBlendEquation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquation"));
        }
        this.dispatch_glBlendEquation1(n, addressof_glBlendEquation);
    }
    
    private native void dispatch_glBlendEquation1(final int p0, final long p1);
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        final long addressof_glBlendEquationSeparate = this._pat._addressof_glBlendEquationSeparate;
        if (addressof_glBlendEquationSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationSeparate"));
        }
        this.dispatch_glBlendEquationSeparate1(n, n2, addressof_glBlendEquationSeparate);
    }
    
    private native void dispatch_glBlendEquationSeparate1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        final long addressof_glBlendFunc = this._pat._addressof_glBlendFunc;
        if (addressof_glBlendFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFunc"));
        }
        this.dispatch_glBlendFunc1(n, n2, addressof_glBlendFunc);
    }
    
    private native void dispatch_glBlendFunc1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glBlendFuncSeparate = this._pat._addressof_glBlendFuncSeparate;
        if (addressof_glBlendFuncSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFuncSeparate"));
        }
        this.dispatch_glBlendFuncSeparate1(n, n2, n3, n4, addressof_glBlendFuncSeparate);
    }
    
    private native void dispatch_glBlendFuncSeparate1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    private void glBufferDataDelegate(final int n, final long n2, final Buffer buffer, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glBufferData = this._pat._addressof_glBufferData;
        if (addressof_glBufferData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBufferData"));
        }
        this.dispatch_glBufferDataDelegate1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n3, addressof_glBufferData);
    }
    
    private native void dispatch_glBufferDataDelegate1(final int p0, final long p1, final Object p2, final int p3, final boolean p4, final int p5, final long p6);
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glBufferSubData = this._pat._addressof_glBufferSubData;
        if (addressof_glBufferSubData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBufferSubData"));
        }
        this.dispatch_glBufferSubData1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glBufferSubData);
    }
    
    private native void dispatch_glBufferSubData1(final int p0, final long p1, final long p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        final long addressof_glCheckFramebufferStatus = this._pat._addressof_glCheckFramebufferStatus;
        if (addressof_glCheckFramebufferStatus == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCheckFramebufferStatus"));
        }
        return this.dispatch_glCheckFramebufferStatus1(n, addressof_glCheckFramebufferStatus);
    }
    
    private native int dispatch_glCheckFramebufferStatus1(final int p0, final long p1);
    
    @Override
    public void glClear(final int n) {
        final long addressof_glClear = this._pat._addressof_glClear;
        if (addressof_glClear == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClear"));
        }
        this.dispatch_glClear1(n, addressof_glClear);
    }
    
    private native void dispatch_glClear1(final int p0, final long p1);
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        final long addressof_glClearColor = this._pat._addressof_glClearColor;
        if (addressof_glClearColor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearColor"));
        }
        this.dispatch_glClearColor1(n, n2, n3, n4, addressof_glClearColor);
    }
    
    private native void dispatch_glClearColor1(final float p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glClearDepthf(final float n) {
        final long addressof_glClearDepthf = this._pat._addressof_glClearDepthf;
        if (addressof_glClearDepthf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearDepthf"));
        }
        this.dispatch_glClearDepthf1(n, addressof_glClearDepthf);
    }
    
    private native void dispatch_glClearDepthf1(final float p0, final long p1);
    
    @Override
    public void glClearStencil(final int n) {
        final long addressof_glClearStencil = this._pat._addressof_glClearStencil;
        if (addressof_glClearStencil == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearStencil"));
        }
        this.dispatch_glClearStencil1(n, addressof_glClearStencil);
    }
    
    private native void dispatch_glClearStencil1(final int p0, final long p1);
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long addressof_glColorMask = this._pat._addressof_glColorMask;
        if (addressof_glColorMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorMask"));
        }
        this.dispatch_glColorMask1(b, b2, b3, b4, addressof_glColorMask);
    }
    
    private native void dispatch_glColorMask1(final boolean p0, final boolean p1, final boolean p2, final boolean p3, final long p4);
    
    @Override
    public void glCompileShader(final int n) {
        final long addressof_glCompileShader = this._pat._addressof_glCompileShader;
        if (addressof_glCompileShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompileShader"));
        }
        this.dispatch_glCompileShader1(n, addressof_glCompileShader);
    }
    
    private native void dispatch_glCompileShader1(final int p0, final long p1);
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexImage2D = this._pat._addressof_glCompressedTexImage2D;
        if (addressof_glCompressedTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage2D"));
        }
        this.dispatch_glCompressedTexImage2D1(n, n2, n3, n4, n5, n6, n7, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexImage2D);
    }
    
    private native void dispatch_glCompressedTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexImage2D = this._pat._addressof_glCompressedTexImage2D;
        if (addressof_glCompressedTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage2D"));
        }
        this.dispatch_glCompressedTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCompressedTexImage2D);
    }
    
    private native void dispatch_glCompressedTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7, final long p8);
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexSubImage2D = this._pat._addressof_glCompressedTexSubImage2D;
        if (addressof_glCompressedTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage2D"));
        }
        this.dispatch_glCompressedTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexSubImage2D);
    }
    
    private native void dispatch_glCompressedTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexSubImage2D = this._pat._addressof_glCompressedTexSubImage2D;
        if (addressof_glCompressedTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage2D"));
        }
        this.dispatch_glCompressedTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glCompressedTexSubImage2D);
    }
    
    private native void dispatch_glCompressedTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long addressof_glCopyTexImage2D = this._pat._addressof_glCopyTexImage2D;
        if (addressof_glCopyTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTexImage2D"));
        }
        this.dispatch_glCopyTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCopyTexImage2D);
    }
    
    private native void dispatch_glCopyTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long addressof_glCopyTexSubImage2D = this._pat._addressof_glCopyTexSubImage2D;
        if (addressof_glCopyTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTexSubImage2D"));
        }
        this.dispatch_glCopyTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glCopyTexSubImage2D);
    }
    
    private native void dispatch_glCopyTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    @Override
    public int glCreateProgram() {
        final long addressof_glCreateProgram = this._pat._addressof_glCreateProgram;
        if (addressof_glCreateProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCreateProgram"));
        }
        return this.dispatch_glCreateProgram1(addressof_glCreateProgram);
    }
    
    private native int dispatch_glCreateProgram1(final long p0);
    
    @Override
    public int glCreateShader(final int n) {
        final long addressof_glCreateShader = this._pat._addressof_glCreateShader;
        if (addressof_glCreateShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCreateShader"));
        }
        return this.dispatch_glCreateShader1(n, addressof_glCreateShader);
    }
    
    private native int dispatch_glCreateShader1(final int p0, final long p1);
    
    @Override
    public void glCullFace(final int n) {
        final long addressof_glCullFace = this._pat._addressof_glCullFace;
        if (addressof_glCullFace == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCullFace"));
        }
        this.dispatch_glCullFace1(n, addressof_glCullFace);
    }
    
    private native void dispatch_glCullFace1(final int p0, final long p1);
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.bufferObjectTracker.notifyBuffersDeleted(n, intBuffer);
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteBuffers = this._pat._addressof_glDeleteBuffers;
        if (addressof_glDeleteBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteBuffers"));
        }
        this.dispatch_glDeleteBuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteBuffers);
    }
    
    private native void dispatch_glDeleteBuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.bufferObjectTracker.notifyBuffersDeleted(n, array, n2);
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteBuffers = this._pat._addressof_glDeleteBuffers;
        if (addressof_glDeleteBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteBuffers"));
        }
        this.dispatch_glDeleteBuffers1(n, array, 4 * n2, false, addressof_glDeleteBuffers);
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteFramebuffers = this._pat._addressof_glDeleteFramebuffers;
        if (addressof_glDeleteFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteFramebuffers"));
        }
        this.dispatch_glDeleteFramebuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteFramebuffers);
    }
    
    private native void dispatch_glDeleteFramebuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteFramebuffers = this._pat._addressof_glDeleteFramebuffers;
        if (addressof_glDeleteFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteFramebuffers"));
        }
        this.dispatch_glDeleteFramebuffers1(n, array, 4 * n2, false, addressof_glDeleteFramebuffers);
    }
    
    @Override
    public void glDeleteProgram(final int n) {
        final long addressof_glDeleteProgram = this._pat._addressof_glDeleteProgram;
        if (addressof_glDeleteProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteProgram"));
        }
        this.dispatch_glDeleteProgram1(n, addressof_glDeleteProgram);
    }
    
    private native void dispatch_glDeleteProgram1(final int p0, final long p1);
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteRenderbuffers = this._pat._addressof_glDeleteRenderbuffers;
        if (addressof_glDeleteRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteRenderbuffers"));
        }
        this.dispatch_glDeleteRenderbuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteRenderbuffers);
    }
    
    private native void dispatch_glDeleteRenderbuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteRenderbuffers = this._pat._addressof_glDeleteRenderbuffers;
        if (addressof_glDeleteRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteRenderbuffers"));
        }
        this.dispatch_glDeleteRenderbuffers1(n, array, 4 * n2, false, addressof_glDeleteRenderbuffers);
    }
    
    @Override
    public void glDeleteShader(final int n) {
        final long addressof_glDeleteShader = this._pat._addressof_glDeleteShader;
        if (addressof_glDeleteShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteShader"));
        }
        this.dispatch_glDeleteShader1(n, addressof_glDeleteShader);
    }
    
    private native void dispatch_glDeleteShader1(final int p0, final long p1);
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteTextures = this._pat._addressof_glDeleteTextures;
        if (addressof_glDeleteTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTextures"));
        }
        this.dispatch_glDeleteTextures1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteTextures);
    }
    
    private native void dispatch_glDeleteTextures1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"textures_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteTextures = this._pat._addressof_glDeleteTextures;
        if (addressof_glDeleteTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTextures"));
        }
        this.dispatch_glDeleteTextures1(n, array, 4 * n2, false, addressof_glDeleteTextures);
    }
    
    @Override
    public void glDepthFunc(final int n) {
        final long addressof_glDepthFunc = this._pat._addressof_glDepthFunc;
        if (addressof_glDepthFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthFunc"));
        }
        this.dispatch_glDepthFunc1(n, addressof_glDepthFunc);
    }
    
    private native void dispatch_glDepthFunc1(final int p0, final long p1);
    
    @Override
    public void glDepthMask(final boolean b) {
        final long addressof_glDepthMask = this._pat._addressof_glDepthMask;
        if (addressof_glDepthMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthMask"));
        }
        this.dispatch_glDepthMask1(b, addressof_glDepthMask);
    }
    
    private native void dispatch_glDepthMask1(final boolean p0, final long p1);
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        final long addressof_glDepthRangef = this._pat._addressof_glDepthRangef;
        if (addressof_glDepthRangef == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangef"));
        }
        this.dispatch_glDepthRangef1(n, n2, addressof_glDepthRangef);
    }
    
    private native void dispatch_glDepthRangef1(final float p0, final float p1, final long p2);
    
    @Override
    public void glDetachShader(final int n, final int n2) {
        final long addressof_glDetachShader = this._pat._addressof_glDetachShader;
        if (addressof_glDetachShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDetachShader"));
        }
        this.dispatch_glDetachShader1(n, n2, addressof_glDetachShader);
    }
    
    private native void dispatch_glDetachShader1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisable(final int n) {
        final long addressof_glDisable = this._pat._addressof_glDisable;
        if (addressof_glDisable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisable"));
        }
        this.dispatch_glDisable1(n, addressof_glDisable);
    }
    
    private native void dispatch_glDisable1(final int p0, final long p1);
    
    @Override
    public void glDisableVertexAttribArray(final int n) {
        final long addressof_glDisableVertexAttribArray = this._pat._addressof_glDisableVertexAttribArray;
        if (addressof_glDisableVertexAttribArray == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableVertexAttribArray"));
        }
        this.dispatch_glDisableVertexAttribArray1(n, addressof_glDisableVertexAttribArray);
    }
    
    private native void dispatch_glDisableVertexAttribArray1(final int p0, final long p1);
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        final long addressof_glDrawArrays = this._pat._addressof_glDrawArrays;
        if (addressof_glDrawArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArrays"));
        }
        this.dispatch_glDrawArrays1(n, n2, n3, addressof_glDrawArrays);
    }
    
    private native void dispatch_glDrawArrays1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElements = this._pat._addressof_glDrawElements;
        if (addressof_glDrawElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElements"));
        }
        this.dispatch_glDrawElements1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glDrawElements);
    }
    
    private native void dispatch_glDrawElements1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElements = this._pat._addressof_glDrawElements;
        if (addressof_glDrawElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElements"));
        }
        this.dispatch_glDrawElements1(n, n2, n3, n4, addressof_glDrawElements);
    }
    
    private native void dispatch_glDrawElements1(final int p0, final int p1, final int p2, final long p3, final long p4);
    
    @Override
    public void glEnable(final int n) {
        final long addressof_glEnable = this._pat._addressof_glEnable;
        if (addressof_glEnable == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnable"));
        }
        this.dispatch_glEnable1(n, addressof_glEnable);
    }
    
    private native void dispatch_glEnable1(final int p0, final long p1);
    
    @Override
    public void glEnableVertexAttribArray(final int n) {
        final long addressof_glEnableVertexAttribArray = this._pat._addressof_glEnableVertexAttribArray;
        if (addressof_glEnableVertexAttribArray == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableVertexAttribArray"));
        }
        this.dispatch_glEnableVertexAttribArray1(n, addressof_glEnableVertexAttribArray);
    }
    
    private native void dispatch_glEnableVertexAttribArray1(final int p0, final long p1);
    
    @Override
    public void glFinish() {
        final long addressof_glFinish = this._pat._addressof_glFinish;
        if (addressof_glFinish == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFinish"));
        }
        this.dispatch_glFinish1(addressof_glFinish);
    }
    
    private native void dispatch_glFinish1(final long p0);
    
    @Override
    public void glFlush() {
        final long addressof_glFlush = this._pat._addressof_glFlush;
        if (addressof_glFlush == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFlush"));
        }
        this.dispatch_glFlush1(addressof_glFlush);
    }
    
    private native void dispatch_glFlush1(final long p0);
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glFramebufferRenderbuffer = this._pat._addressof_glFramebufferRenderbuffer;
        if (addressof_glFramebufferRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferRenderbuffer"));
        }
        this.dispatch_glFramebufferRenderbuffer1(n, n2, n3, n4, addressof_glFramebufferRenderbuffer);
    }
    
    private native void dispatch_glFramebufferRenderbuffer1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glFramebufferTexture2D = this._pat._addressof_glFramebufferTexture2D;
        if (addressof_glFramebufferTexture2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2D"));
        }
        this.dispatch_glFramebufferTexture2D1(n, n2, n3, n4, n5, addressof_glFramebufferTexture2D);
    }
    
    private native void dispatch_glFramebufferTexture2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFrontFace(final int n) {
        final long addressof_glFrontFace = this._pat._addressof_glFrontFace;
        if (addressof_glFrontFace == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFrontFace"));
        }
        this.dispatch_glFrontFace1(n, addressof_glFrontFace);
    }
    
    private native void dispatch_glFrontFace1(final int p0, final long p1);
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenBuffers = this._pat._addressof_glGenBuffers;
        if (addressof_glGenBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenBuffers"));
        }
        this.dispatch_glGenBuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenBuffers);
    }
    
    private native void dispatch_glGenBuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenBuffers = this._pat._addressof_glGenBuffers;
        if (addressof_glGenBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenBuffers"));
        }
        this.dispatch_glGenBuffers1(n, array, 4 * n2, false, addressof_glGenBuffers);
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        final long addressof_glGenerateMipmap = this._pat._addressof_glGenerateMipmap;
        if (addressof_glGenerateMipmap == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenerateMipmap"));
        }
        this.dispatch_glGenerateMipmap1(n, addressof_glGenerateMipmap);
    }
    
    private native void dispatch_glGenerateMipmap1(final int p0, final long p1);
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenFramebuffers = this._pat._addressof_glGenFramebuffers;
        if (addressof_glGenFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenFramebuffers"));
        }
        this.dispatch_glGenFramebuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenFramebuffers);
    }
    
    private native void dispatch_glGenFramebuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenFramebuffers = this._pat._addressof_glGenFramebuffers;
        if (addressof_glGenFramebuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenFramebuffers"));
        }
        this.dispatch_glGenFramebuffers1(n, array, 4 * n2, false, addressof_glGenFramebuffers);
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenRenderbuffers = this._pat._addressof_glGenRenderbuffers;
        if (addressof_glGenRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenRenderbuffers"));
        }
        this.dispatch_glGenRenderbuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenRenderbuffers);
    }
    
    private native void dispatch_glGenRenderbuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenRenderbuffers = this._pat._addressof_glGenRenderbuffers;
        if (addressof_glGenRenderbuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenRenderbuffers"));
        }
        this.dispatch_glGenRenderbuffers1(n, array, 4 * n2, false, addressof_glGenRenderbuffers);
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenTextures = this._pat._addressof_glGenTextures;
        if (addressof_glGenTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTextures"));
        }
        this.dispatch_glGenTextures1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenTextures);
    }
    
    private native void dispatch_glGenTextures1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"textures_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenTextures = this._pat._addressof_glGenTextures;
        if (addressof_glGenTextures == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTextures"));
        }
        this.dispatch_glGenTextures1(n, array, 4 * n2, false, addressof_glGenTextures);
    }
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(intBuffer3);
        final boolean direct4 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetActiveAttrib = this._pat._addressof_glGetActiveAttrib;
        if (addressof_glGetActiveAttrib == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveAttrib"));
        }
        this.dispatch_glGetActiveAttrib1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? intBuffer3 : Buffers.getArray(intBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer3) : Buffers.getIndirectBufferByteOffset(intBuffer3), direct3, direct4 ? byteBuffer : Buffers.getArray(byteBuffer), direct4 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct4, addressof_glGetActiveAttrib);
    }
    
    private native void dispatch_glGetActiveAttrib1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final Object p9, final int p10, final boolean p11, final Object p12, final int p13, final boolean p14, final long p15);
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"size_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n6) {
            throw new GLException("array offset argument \"type_offset\" (" + n6 + ") equals or exceeds array length (" + array3.length + ")");
        }
        if (array4 != null && array4.length <= n7) {
            throw new GLException("array offset argument \"name_offset\" (" + n7 + ") equals or exceeds array length (" + array4.length + ")");
        }
        final long addressof_glGetActiveAttrib = this._pat._addressof_glGetActiveAttrib;
        if (addressof_glGetActiveAttrib == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveAttrib"));
        }
        this.dispatch_glGetActiveAttrib1(n, n2, n3, array, 4 * n4, false, array2, 4 * n5, false, array3, 4 * n6, false, array4, n7, false, addressof_glGetActiveAttrib);
    }
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(intBuffer3);
        final boolean direct4 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetActiveUniform = this._pat._addressof_glGetActiveUniform;
        if (addressof_glGetActiveUniform == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniform"));
        }
        this.dispatch_glGetActiveUniform1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? intBuffer3 : Buffers.getArray(intBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer3) : Buffers.getIndirectBufferByteOffset(intBuffer3), direct3, direct4 ? byteBuffer : Buffers.getArray(byteBuffer), direct4 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct4, addressof_glGetActiveUniform);
    }
    
    private native void dispatch_glGetActiveUniform1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final Object p9, final int p10, final boolean p11, final Object p12, final int p13, final boolean p14, final long p15);
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"size_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n6) {
            throw new GLException("array offset argument \"type_offset\" (" + n6 + ") equals or exceeds array length (" + array3.length + ")");
        }
        if (array4 != null && array4.length <= n7) {
            throw new GLException("array offset argument \"name_offset\" (" + n7 + ") equals or exceeds array length (" + array4.length + ")");
        }
        final long addressof_glGetActiveUniform = this._pat._addressof_glGetActiveUniform;
        if (addressof_glGetActiveUniform == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniform"));
        }
        this.dispatch_glGetActiveUniform1(n, n2, n3, array, 4 * n4, false, array2, 4 * n5, false, array3, 4 * n6, false, array4, n7, false, addressof_glGetActiveUniform);
    }
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetAttachedShaders = this._pat._addressof_glGetAttachedShaders;
        if (addressof_glGetAttachedShaders == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetAttachedShaders"));
        }
        this.dispatch_glGetAttachedShaders1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetAttachedShaders);
    }
    
    private native void dispatch_glGetAttachedShaders1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"count_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"shaders_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetAttachedShaders = this._pat._addressof_glGetAttachedShaders;
        if (addressof_glGetAttachedShaders == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetAttachedShaders"));
        }
        this.dispatch_glGetAttachedShaders1(n, n2, array, 4 * n3, false, array2, 4 * n4, false, addressof_glGetAttachedShaders);
    }
    
    @Override
    public int glGetAttribLocation(final int n, final String s) {
        final long addressof_glGetAttribLocation = this._pat._addressof_glGetAttribLocation;
        if (addressof_glGetAttribLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetAttribLocation"));
        }
        return this.dispatch_glGetAttribLocation1(n, s, addressof_glGetAttribLocation);
    }
    
    private native int dispatch_glGetAttribLocation1(final int p0, final String p1, final long p2);
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetBooleanv = this._pat._addressof_glGetBooleanv;
        if (addressof_glGetBooleanv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleanv"));
        }
        this.dispatch_glGetBooleanv1(n, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetBooleanv);
    }
    
    private native void dispatch_glGetBooleanv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"data_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBooleanv = this._pat._addressof_glGetBooleanv;
        if (addressof_glGetBooleanv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleanv"));
        }
        this.dispatch_glGetBooleanv1(n, array, n2, false, addressof_glGetBooleanv);
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetBufferParameteriv = this._pat._addressof_glGetBufferParameteriv;
        if (addressof_glGetBufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteriv"));
        }
        this.dispatch_glGetBufferParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetBufferParameteriv);
    }
    
    private native void dispatch_glGetBufferParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBufferParameteriv = this._pat._addressof_glGetBufferParameteriv;
        if (addressof_glGetBufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteriv"));
        }
        this.dispatch_glGetBufferParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetBufferParameteriv);
    }
    
    @Override
    public int glGetError() {
        final long addressof_glGetError = this._pat._addressof_glGetError;
        if (addressof_glGetError == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetError"));
        }
        return this.dispatch_glGetError1(addressof_glGetError);
    }
    
    private native int dispatch_glGetError1(final long p0);
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetFloatv = this._pat._addressof_glGetFloatv;
        if (addressof_glGetFloatv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloatv"));
        }
        this.dispatch_glGetFloatv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetFloatv);
    }
    
    private native void dispatch_glGetFloatv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"data_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFloatv = this._pat._addressof_glGetFloatv;
        if (addressof_glGetFloatv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloatv"));
        }
        this.dispatch_glGetFloatv1(n, array, 4 * n2, false, addressof_glGetFloatv);
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetFramebufferAttachmentParameteriv = this._pat._addressof_glGetFramebufferAttachmentParameteriv;
        if (addressof_glGetFramebufferAttachmentParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferAttachmentParameteriv"));
        }
        this.dispatch_glGetFramebufferAttachmentParameteriv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetFramebufferAttachmentParameteriv);
    }
    
    private native void dispatch_glGetFramebufferAttachmentParameteriv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFramebufferAttachmentParameteriv = this._pat._addressof_glGetFramebufferAttachmentParameteriv;
        if (addressof_glGetFramebufferAttachmentParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferAttachmentParameteriv"));
        }
        this.dispatch_glGetFramebufferAttachmentParameteriv1(n, n2, n3, array, 4 * n4, false, addressof_glGetFramebufferAttachmentParameteriv);
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        if (this.glStateTracker.getInt(n, intBuffer, 0)) {
            return;
        }
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetIntegerv = this._pat._addressof_glGetIntegerv;
        if (addressof_glGetIntegerv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegerv"));
        }
        this.dispatch_glGetIntegerv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetIntegerv);
    }
    
    private native void dispatch_glGetIntegerv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        if (this.glStateTracker.getInt(n, array, n2)) {
            return;
        }
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"data_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetIntegerv = this._pat._addressof_glGetIntegerv;
        if (addressof_glGetIntegerv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegerv"));
        }
        this.dispatch_glGetIntegerv1(n, array, 4 * n2, false, addressof_glGetIntegerv);
    }
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetProgramiv = this._pat._addressof_glGetProgramiv;
        if (addressof_glGetProgramiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramiv"));
        }
        this.dispatch_glGetProgramiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetProgramiv);
    }
    
    private native void dispatch_glGetProgramiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramiv = this._pat._addressof_glGetProgramiv;
        if (addressof_glGetProgramiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramiv"));
        }
        this.dispatch_glGetProgramiv1(n, n2, array, 4 * n3, false, addressof_glGetProgramiv);
    }
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramInfoLog = this._pat._addressof_glGetProgramInfoLog;
        if (addressof_glGetProgramInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramInfoLog"));
        }
        this.dispatch_glGetProgramInfoLog1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetProgramInfoLog);
    }
    
    private native void dispatch_glGetProgramInfoLog1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"infoLog_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetProgramInfoLog = this._pat._addressof_glGetProgramInfoLog;
        if (addressof_glGetProgramInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramInfoLog"));
        }
        this.dispatch_glGetProgramInfoLog1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetProgramInfoLog);
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetRenderbufferParameteriv = this._pat._addressof_glGetRenderbufferParameteriv;
        if (addressof_glGetRenderbufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetRenderbufferParameteriv"));
        }
        this.dispatch_glGetRenderbufferParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetRenderbufferParameteriv);
    }
    
    private native void dispatch_glGetRenderbufferParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetRenderbufferParameteriv = this._pat._addressof_glGetRenderbufferParameteriv;
        if (addressof_glGetRenderbufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetRenderbufferParameteriv"));
        }
        this.dispatch_glGetRenderbufferParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetRenderbufferParameteriv);
    }
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetShaderiv = this._pat._addressof_glGetShaderiv;
        if (addressof_glGetShaderiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderiv"));
        }
        this.dispatch_glGetShaderiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetShaderiv);
    }
    
    private native void dispatch_glGetShaderiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetShaderiv = this._pat._addressof_glGetShaderiv;
        if (addressof_glGetShaderiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderiv"));
        }
        this.dispatch_glGetShaderiv1(n, n2, array, 4 * n3, false, addressof_glGetShaderiv);
    }
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetShaderInfoLog = this._pat._addressof_glGetShaderInfoLog;
        if (addressof_glGetShaderInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderInfoLog"));
        }
        this.dispatch_glGetShaderInfoLog1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetShaderInfoLog);
    }
    
    private native void dispatch_glGetShaderInfoLog1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"infoLog_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetShaderInfoLog = this._pat._addressof_glGetShaderInfoLog;
        if (addressof_glGetShaderInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderInfoLog"));
        }
        this.dispatch_glGetShaderInfoLog1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetShaderInfoLog);
    }
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        if (!this._context.isGLES2Compatible()) {
            throw new GLException("Method \"glGetShaderPrecisionFormat\" not available");
        }
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetShaderPrecisionFormat = this._pat._addressof_glGetShaderPrecisionFormat;
        if (addressof_glGetShaderPrecisionFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderPrecisionFormat"));
        }
        this.dispatch_glGetShaderPrecisionFormat1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetShaderPrecisionFormat);
    }
    
    private native void dispatch_glGetShaderPrecisionFormat1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        if (!this._context.isGLES2Compatible()) {
            throw new GLException("Method \"glGetShaderPrecisionFormat\" not available");
        }
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"range_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"precision_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetShaderPrecisionFormat = this._pat._addressof_glGetShaderPrecisionFormat;
        if (addressof_glGetShaderPrecisionFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderPrecisionFormat"));
        }
        this.dispatch_glGetShaderPrecisionFormat1(n, n2, array, 4 * n3, false, array2, 4 * n4, false, addressof_glGetShaderPrecisionFormat);
    }
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetShaderSource = this._pat._addressof_glGetShaderSource;
        if (addressof_glGetShaderSource == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderSource"));
        }
        this.dispatch_glGetShaderSource1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetShaderSource);
    }
    
    private native void dispatch_glGetShaderSource1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"source_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetShaderSource = this._pat._addressof_glGetShaderSource;
        if (addressof_glGetShaderSource == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetShaderSource"));
        }
        this.dispatch_glGetShaderSource1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetShaderSource);
    }
    
    @Override
    public String glGetString(final int n) {
        if (this._context.isExtensionCacheInitialized() && 7939 == n) {
            return this._context.getGLExtensionsString();
        }
        final long addressof_glGetString = this._pat._addressof_glGetString;
        if (addressof_glGetString == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetString"));
        }
        return this.dispatch_glGetString1(n, addressof_glGetString);
    }
    
    private native String dispatch_glGetString1(final int p0, final long p1);
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetTexParameterfv = this._pat._addressof_glGetTexParameterfv;
        if (addressof_glGetTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterfv"));
        }
        this.dispatch_glGetTexParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetTexParameterfv);
    }
    
    private native void dispatch_glGetTexParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameterfv = this._pat._addressof_glGetTexParameterfv;
        if (addressof_glGetTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterfv"));
        }
        this.dispatch_glGetTexParameterfv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameterfv);
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexParameteriv = this._pat._addressof_glGetTexParameteriv;
        if (addressof_glGetTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameteriv"));
        }
        this.dispatch_glGetTexParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexParameteriv);
    }
    
    private native void dispatch_glGetTexParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameteriv = this._pat._addressof_glGetTexParameteriv;
        if (addressof_glGetTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameteriv"));
        }
        this.dispatch_glGetTexParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameteriv);
    }
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetUniformfv = this._pat._addressof_glGetUniformfv;
        if (addressof_glGetUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformfv"));
        }
        this.dispatch_glGetUniformfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetUniformfv);
    }
    
    private native void dispatch_glGetUniformfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetUniformfv = this._pat._addressof_glGetUniformfv;
        if (addressof_glGetUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformfv"));
        }
        this.dispatch_glGetUniformfv1(n, n2, array, 4 * n3, false, addressof_glGetUniformfv);
    }
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetUniformiv = this._pat._addressof_glGetUniformiv;
        if (addressof_glGetUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformiv"));
        }
        this.dispatch_glGetUniformiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetUniformiv);
    }
    
    private native void dispatch_glGetUniformiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetUniformiv = this._pat._addressof_glGetUniformiv;
        if (addressof_glGetUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformiv"));
        }
        this.dispatch_glGetUniformiv1(n, n2, array, 4 * n3, false, addressof_glGetUniformiv);
    }
    
    @Override
    public int glGetUniformLocation(final int n, final String s) {
        final long addressof_glGetUniformLocation = this._pat._addressof_glGetUniformLocation;
        if (addressof_glGetUniformLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformLocation"));
        }
        return this.dispatch_glGetUniformLocation1(n, s, addressof_glGetUniformLocation);
    }
    
    private native int dispatch_glGetUniformLocation1(final int p0, final String p1, final long p2);
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetVertexAttribfv = this._pat._addressof_glGetVertexAttribfv;
        if (addressof_glGetVertexAttribfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribfv"));
        }
        this.dispatch_glGetVertexAttribfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetVertexAttribfv);
    }
    
    private native void dispatch_glGetVertexAttribfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetVertexAttribfv = this._pat._addressof_glGetVertexAttribfv;
        if (addressof_glGetVertexAttribfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribfv"));
        }
        this.dispatch_glGetVertexAttribfv1(n, n2, array, 4 * n3, false, addressof_glGetVertexAttribfv);
    }
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetVertexAttribiv = this._pat._addressof_glGetVertexAttribiv;
        if (addressof_glGetVertexAttribiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribiv"));
        }
        this.dispatch_glGetVertexAttribiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetVertexAttribiv);
    }
    
    private native void dispatch_glGetVertexAttribiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetVertexAttribiv = this._pat._addressof_glGetVertexAttribiv;
        if (addressof_glGetVertexAttribiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribiv"));
        }
        this.dispatch_glGetVertexAttribiv1(n, n2, array, 4 * n3, false, addressof_glGetVertexAttribiv);
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        final long addressof_glHint = this._pat._addressof_glHint;
        if (addressof_glHint == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glHint"));
        }
        this.dispatch_glHint1(n, n2, addressof_glHint);
    }
    
    private native void dispatch_glHint1(final int p0, final int p1, final long p2);
    
    @Override
    public boolean glIsBuffer(final int n) {
        final long addressof_glIsBuffer = this._pat._addressof_glIsBuffer;
        if (addressof_glIsBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsBuffer"));
        }
        return this.dispatch_glIsBuffer1(n, addressof_glIsBuffer);
    }
    
    private native boolean dispatch_glIsBuffer1(final int p0, final long p1);
    
    @Override
    public boolean glIsEnabled(final int n) {
        final long addressof_glIsEnabled = this._pat._addressof_glIsEnabled;
        if (addressof_glIsEnabled == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnabled"));
        }
        return this.dispatch_glIsEnabled1(n, addressof_glIsEnabled);
    }
    
    private native boolean dispatch_glIsEnabled1(final int p0, final long p1);
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        final long addressof_glIsFramebuffer = this._pat._addressof_glIsFramebuffer;
        if (addressof_glIsFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsFramebuffer"));
        }
        return this.dispatch_glIsFramebuffer1(n, addressof_glIsFramebuffer);
    }
    
    private native boolean dispatch_glIsFramebuffer1(final int p0, final long p1);
    
    @Override
    public boolean glIsProgram(final int n) {
        final long addressof_glIsProgram = this._pat._addressof_glIsProgram;
        if (addressof_glIsProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsProgram"));
        }
        return this.dispatch_glIsProgram1(n, addressof_glIsProgram);
    }
    
    private native boolean dispatch_glIsProgram1(final int p0, final long p1);
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        final long addressof_glIsRenderbuffer = this._pat._addressof_glIsRenderbuffer;
        if (addressof_glIsRenderbuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsRenderbuffer"));
        }
        return this.dispatch_glIsRenderbuffer1(n, addressof_glIsRenderbuffer);
    }
    
    private native boolean dispatch_glIsRenderbuffer1(final int p0, final long p1);
    
    @Override
    public boolean glIsShader(final int n) {
        final long addressof_glIsShader = this._pat._addressof_glIsShader;
        if (addressof_glIsShader == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsShader"));
        }
        return this.dispatch_glIsShader1(n, addressof_glIsShader);
    }
    
    private native boolean dispatch_glIsShader1(final int p0, final long p1);
    
    @Override
    public boolean glIsTexture(final int n) {
        final long addressof_glIsTexture = this._pat._addressof_glIsTexture;
        if (addressof_glIsTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsTexture"));
        }
        return this.dispatch_glIsTexture1(n, addressof_glIsTexture);
    }
    
    private native boolean dispatch_glIsTexture1(final int p0, final long p1);
    
    @Override
    public void glLineWidth(final float n) {
        final long addressof_glLineWidth = this._pat._addressof_glLineWidth;
        if (addressof_glLineWidth == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLineWidth"));
        }
        this.dispatch_glLineWidth1(n, addressof_glLineWidth);
    }
    
    private native void dispatch_glLineWidth1(final float p0, final long p1);
    
    @Override
    public void glLinkProgram(final int n) {
        final long addressof_glLinkProgram = this._pat._addressof_glLinkProgram;
        if (addressof_glLinkProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glLinkProgram"));
        }
        this.dispatch_glLinkProgram1(n, addressof_glLinkProgram);
    }
    
    private native void dispatch_glLinkProgram1(final int p0, final long p1);
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.glStateTracker.setInt(n, n2);
        final long addressof_glPixelStorei = this._pat._addressof_glPixelStorei;
        if (addressof_glPixelStorei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPixelStorei"));
        }
        this.dispatch_glPixelStorei1(n, n2, addressof_glPixelStorei);
    }
    
    private native void dispatch_glPixelStorei1(final int p0, final int p1, final long p2);
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        final long addressof_glPolygonOffset = this._pat._addressof_glPolygonOffset;
        if (addressof_glPolygonOffset == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPolygonOffset"));
        }
        this.dispatch_glPolygonOffset1(n, n2, addressof_glPolygonOffset);
    }
    
    private native void dispatch_glPolygonOffset1(final float p0, final float p1, final long p2);
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.checkPackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n5, n6, n3, n4, 1, true));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glReadPixels = this._pat._addressof_glReadPixels;
        if (addressof_glReadPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadPixels"));
        }
        this.dispatch_glReadPixels1(n, n2, n3, n4, n5, n6, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glReadPixels);
    }
    
    private native void dispatch_glReadPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.checkPackPBOBound(true);
        final long addressof_glReadPixels = this._pat._addressof_glReadPixels;
        if (addressof_glReadPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadPixels"));
        }
        this.dispatch_glReadPixels1(n, n2, n3, n4, n5, n6, n7, addressof_glReadPixels);
    }
    
    private native void dispatch_glReadPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6, final long p7);
    
    @Override
    public void glReleaseShaderCompiler() {
        if (!this._context.isGLES2Compatible()) {
            return;
        }
        final long addressof_glReleaseShaderCompiler = this._pat._addressof_glReleaseShaderCompiler;
        if (addressof_glReleaseShaderCompiler == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReleaseShaderCompiler"));
        }
        this.dispatch_glReleaseShaderCompiler1(addressof_glReleaseShaderCompiler);
    }
    
    private native void dispatch_glReleaseShaderCompiler1(final long p0);
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glRenderbufferStorage = this._pat._addressof_glRenderbufferStorage;
        if (addressof_glRenderbufferStorage == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorage"));
        }
        this.dispatch_glRenderbufferStorage1(n, n2, n3, n4, addressof_glRenderbufferStorage);
    }
    
    private native void dispatch_glRenderbufferStorage1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        final long addressof_glSampleCoverage = this._pat._addressof_glSampleCoverage;
        if (addressof_glSampleCoverage == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSampleCoverage"));
        }
        this.dispatch_glSampleCoverage1(n, b, addressof_glSampleCoverage);
    }
    
    private native void dispatch_glSampleCoverage1(final float p0, final boolean p1, final long p2);
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glScissor = this._pat._addressof_glScissor;
        if (addressof_glScissor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissor"));
        }
        this.dispatch_glScissor1(n, n2, n3, n4, addressof_glScissor);
    }
    
    private native void dispatch_glScissor1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glShaderBinary(final int n, final IntBuffer intBuffer, final int n2, final Buffer buffer, final int n3) {
        if (!this._context.isGLES2Compatible()) {
            throw new GLException("Method \"glShaderBinary\" not available");
        }
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(buffer);
        final long addressof_glShaderBinary = this._pat._addressof_glShaderBinary;
        if (addressof_glShaderBinary == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glShaderBinary"));
        }
        this.dispatch_glShaderBinary1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n2, direct2 ? buffer : Buffers.getArray(buffer), direct2 ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct2, n3, addressof_glShaderBinary);
    }
    
    private native void dispatch_glShaderBinary1(final int p0, final Object p1, final int p2, final boolean p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final long p9);
    
    @Override
    public void glShaderBinary(final int n, final int[] array, final int n2, final int n3, final Buffer buffer, final int n4) {
        if (!this._context.isGLES2Compatible()) {
            throw new GLException("Method \"glShaderBinary\" not available");
        }
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"shaders_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glShaderBinary = this._pat._addressof_glShaderBinary;
        if (addressof_glShaderBinary == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glShaderBinary"));
        }
        this.dispatch_glShaderBinary1(n, array, 4 * n2, false, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glShaderBinary);
    }
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glShaderSource = this._pat._addressof_glShaderSource;
        if (addressof_glShaderSource == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glShaderSource"));
        }
        this.dispatch_glShaderSource1(n, n2, array, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glShaderSource);
    }
    
    private native void dispatch_glShaderSource1(final int p0, final int p1, final String[] p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glShaderSource = this._pat._addressof_glShaderSource;
        if (addressof_glShaderSource == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glShaderSource"));
        }
        this.dispatch_glShaderSource1(n, n2, array, array2, 4 * n3, false, addressof_glShaderSource);
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        final long addressof_glStencilFunc = this._pat._addressof_glStencilFunc;
        if (addressof_glStencilFunc == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilFunc"));
        }
        this.dispatch_glStencilFunc1(n, n2, n3, addressof_glStencilFunc);
    }
    
    private native void dispatch_glStencilFunc1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glStencilFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glStencilFuncSeparate = this._pat._addressof_glStencilFuncSeparate;
        if (addressof_glStencilFuncSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilFuncSeparate"));
        }
        this.dispatch_glStencilFuncSeparate1(n, n2, n3, n4, addressof_glStencilFuncSeparate);
    }
    
    private native void dispatch_glStencilFuncSeparate1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glStencilMask(final int n) {
        final long addressof_glStencilMask = this._pat._addressof_glStencilMask;
        if (addressof_glStencilMask == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilMask"));
        }
        this.dispatch_glStencilMask1(n, addressof_glStencilMask);
    }
    
    private native void dispatch_glStencilMask1(final int p0, final long p1);
    
    @Override
    public void glStencilMaskSeparate(final int n, final int n2) {
        final long addressof_glStencilMaskSeparate = this._pat._addressof_glStencilMaskSeparate;
        if (addressof_glStencilMaskSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilMaskSeparate"));
        }
        this.dispatch_glStencilMaskSeparate1(n, n2, addressof_glStencilMaskSeparate);
    }
    
    private native void dispatch_glStencilMaskSeparate1(final int p0, final int p1, final long p2);
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        final long addressof_glStencilOp = this._pat._addressof_glStencilOp;
        if (addressof_glStencilOp == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilOp"));
        }
        this.dispatch_glStencilOp1(n, n2, n3, addressof_glStencilOp);
    }
    
    private native void dispatch_glStencilOp1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glStencilOpSeparate(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glStencilOpSeparate = this._pat._addressof_glStencilOpSeparate;
        if (addressof_glStencilOpSeparate == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStencilOpSeparate"));
        }
        this.dispatch_glStencilOpSeparate1(n, n2, n3, n4, addressof_glStencilOpSeparate);
    }
    
    private native void dispatch_glStencilOpSeparate1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n7, n8, n4, n5, 1, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexImage2D = this._pat._addressof_glTexImage2D;
        if (addressof_glTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage2D"));
        }
        this.dispatch_glTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexImage2D);
    }
    
    private native void dispatch_glTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexImage2D = this._pat._addressof_glTexImage2D;
        if (addressof_glTexImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage2D"));
        }
        this.dispatch_glTexImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glTexImage2D);
    }
    
    private native void dispatch_glTexImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        final long addressof_glTexParameterf = this._pat._addressof_glTexParameterf;
        if (addressof_glTexParameterf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterf"));
        }
        this.dispatch_glTexParameterf1(n, n2, n3, addressof_glTexParameterf);
    }
    
    private native void dispatch_glTexParameterf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glTexParameterfv = this._pat._addressof_glTexParameterfv;
        if (addressof_glTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterfv"));
        }
        this.dispatch_glTexParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glTexParameterfv);
    }
    
    private native void dispatch_glTexParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameterfv = this._pat._addressof_glTexParameterfv;
        if (addressof_glTexParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterfv"));
        }
        this.dispatch_glTexParameterfv1(n, n2, array, 4 * n3, false, addressof_glTexParameterfv);
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        final long addressof_glTexParameteri = this._pat._addressof_glTexParameteri;
        if (addressof_glTexParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteri"));
        }
        this.dispatch_glTexParameteri1(n, n2, n3, addressof_glTexParameteri);
    }
    
    private native void dispatch_glTexParameteri1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexParameteriv = this._pat._addressof_glTexParameteriv;
        if (addressof_glTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteriv"));
        }
        this.dispatch_glTexParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexParameteriv);
    }
    
    private native void dispatch_glTexParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameteriv = this._pat._addressof_glTexParameteriv;
        if (addressof_glTexParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameteriv"));
        }
        this.dispatch_glTexParameteriv1(n, n2, array, 4 * n3, false, addressof_glTexParameteriv);
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n7, n8, n5, n6, 1, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexSubImage2D = this._pat._addressof_glTexSubImage2D;
        if (addressof_glTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage2D"));
        }
        this.dispatch_glTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexSubImage2D);
    }
    
    private native void dispatch_glTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexSubImage2D = this._pat._addressof_glTexSubImage2D;
        if (addressof_glTexSubImage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage2D"));
        }
        this.dispatch_glTexSubImage2D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glTexSubImage2D);
    }
    
    private native void dispatch_glTexSubImage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glUniform1f(final int n, final float n2) {
        final long addressof_glUniform1f = this._pat._addressof_glUniform1f;
        if (addressof_glUniform1f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1f"));
        }
        this.dispatch_glUniform1f1(n, n2, addressof_glUniform1f);
    }
    
    private native void dispatch_glUniform1f1(final int p0, final float p1, final long p2);
    
    @Override
    public void glUniform1fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniform1fv = this._pat._addressof_glUniform1fv;
        if (addressof_glUniform1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1fv"));
        }
        this.dispatch_glUniform1fv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniform1fv);
    }
    
    private native void dispatch_glUniform1fv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform1fv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform1fv = this._pat._addressof_glUniform1fv;
        if (addressof_glUniform1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1fv"));
        }
        this.dispatch_glUniform1fv1(n, n2, array, 4 * n3, false, addressof_glUniform1fv);
    }
    
    @Override
    public void glUniform1i(final int n, final int n2) {
        final long addressof_glUniform1i = this._pat._addressof_glUniform1i;
        if (addressof_glUniform1i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1i"));
        }
        this.dispatch_glUniform1i1(n, n2, addressof_glUniform1i);
    }
    
    private native void dispatch_glUniform1i1(final int p0, final int p1, final long p2);
    
    @Override
    public void glUniform1iv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform1iv = this._pat._addressof_glUniform1iv;
        if (addressof_glUniform1iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1iv"));
        }
        this.dispatch_glUniform1iv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform1iv);
    }
    
    private native void dispatch_glUniform1iv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform1iv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform1iv = this._pat._addressof_glUniform1iv;
        if (addressof_glUniform1iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1iv"));
        }
        this.dispatch_glUniform1iv1(n, n2, array, 4 * n3, false, addressof_glUniform1iv);
    }
    
    @Override
    public void glUniform2f(final int n, final float n2, final float n3) {
        final long addressof_glUniform2f = this._pat._addressof_glUniform2f;
        if (addressof_glUniform2f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2f"));
        }
        this.dispatch_glUniform2f1(n, n2, n3, addressof_glUniform2f);
    }
    
    private native void dispatch_glUniform2f1(final int p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glUniform2fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniform2fv = this._pat._addressof_glUniform2fv;
        if (addressof_glUniform2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2fv"));
        }
        this.dispatch_glUniform2fv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniform2fv);
    }
    
    private native void dispatch_glUniform2fv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform2fv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform2fv = this._pat._addressof_glUniform2fv;
        if (addressof_glUniform2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2fv"));
        }
        this.dispatch_glUniform2fv1(n, n2, array, 4 * n3, false, addressof_glUniform2fv);
    }
    
    @Override
    public void glUniform2i(final int n, final int n2, final int n3) {
        final long addressof_glUniform2i = this._pat._addressof_glUniform2i;
        if (addressof_glUniform2i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2i"));
        }
        this.dispatch_glUniform2i1(n, n2, n3, addressof_glUniform2i);
    }
    
    private native void dispatch_glUniform2i1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glUniform2iv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform2iv = this._pat._addressof_glUniform2iv;
        if (addressof_glUniform2iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2iv"));
        }
        this.dispatch_glUniform2iv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform2iv);
    }
    
    private native void dispatch_glUniform2iv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform2iv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform2iv = this._pat._addressof_glUniform2iv;
        if (addressof_glUniform2iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2iv"));
        }
        this.dispatch_glUniform2iv1(n, n2, array, 4 * n3, false, addressof_glUniform2iv);
    }
    
    @Override
    public void glUniform3f(final int n, final float n2, final float n3, final float n4) {
        final long addressof_glUniform3f = this._pat._addressof_glUniform3f;
        if (addressof_glUniform3f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3f"));
        }
        this.dispatch_glUniform3f1(n, n2, n3, n4, addressof_glUniform3f);
    }
    
    private native void dispatch_glUniform3f1(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glUniform3fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniform3fv = this._pat._addressof_glUniform3fv;
        if (addressof_glUniform3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3fv"));
        }
        this.dispatch_glUniform3fv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniform3fv);
    }
    
    private native void dispatch_glUniform3fv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform3fv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform3fv = this._pat._addressof_glUniform3fv;
        if (addressof_glUniform3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3fv"));
        }
        this.dispatch_glUniform3fv1(n, n2, array, 4 * n3, false, addressof_glUniform3fv);
    }
    
    @Override
    public void glUniform3i(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glUniform3i = this._pat._addressof_glUniform3i;
        if (addressof_glUniform3i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3i"));
        }
        this.dispatch_glUniform3i1(n, n2, n3, n4, addressof_glUniform3i);
    }
    
    private native void dispatch_glUniform3i1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glUniform3iv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform3iv = this._pat._addressof_glUniform3iv;
        if (addressof_glUniform3iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3iv"));
        }
        this.dispatch_glUniform3iv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform3iv);
    }
    
    private native void dispatch_glUniform3iv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform3iv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform3iv = this._pat._addressof_glUniform3iv;
        if (addressof_glUniform3iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3iv"));
        }
        this.dispatch_glUniform3iv1(n, n2, array, 4 * n3, false, addressof_glUniform3iv);
    }
    
    @Override
    public void glUniform4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long addressof_glUniform4f = this._pat._addressof_glUniform4f;
        if (addressof_glUniform4f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4f"));
        }
        this.dispatch_glUniform4f1(n, n2, n3, n4, n5, addressof_glUniform4f);
    }
    
    private native void dispatch_glUniform4f1(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glUniform4fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniform4fv = this._pat._addressof_glUniform4fv;
        if (addressof_glUniform4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4fv"));
        }
        this.dispatch_glUniform4fv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniform4fv);
    }
    
    private native void dispatch_glUniform4fv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform4fv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform4fv = this._pat._addressof_glUniform4fv;
        if (addressof_glUniform4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4fv"));
        }
        this.dispatch_glUniform4fv1(n, n2, array, 4 * n3, false, addressof_glUniform4fv);
    }
    
    @Override
    public void glUniform4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glUniform4i = this._pat._addressof_glUniform4i;
        if (addressof_glUniform4i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4i"));
        }
        this.dispatch_glUniform4i1(n, n2, n3, n4, n5, addressof_glUniform4i);
    }
    
    private native void dispatch_glUniform4i1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glUniform4iv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform4iv = this._pat._addressof_glUniform4iv;
        if (addressof_glUniform4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4iv"));
        }
        this.dispatch_glUniform4iv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform4iv);
    }
    
    private native void dispatch_glUniform4iv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform4iv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform4iv = this._pat._addressof_glUniform4iv;
        if (addressof_glUniform4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4iv"));
        }
        this.dispatch_glUniform4iv1(n, n2, array, 4 * n3, false, addressof_glUniform4iv);
    }
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix2fv = this._pat._addressof_glUniformMatrix2fv;
        if (addressof_glUniformMatrix2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2fv"));
        }
        this.dispatch_glUniformMatrix2fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix2fv);
    }
    
    private native void dispatch_glUniformMatrix2fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix2fv = this._pat._addressof_glUniformMatrix2fv;
        if (addressof_glUniformMatrix2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2fv"));
        }
        this.dispatch_glUniformMatrix2fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix2fv);
    }
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix3fv = this._pat._addressof_glUniformMatrix3fv;
        if (addressof_glUniformMatrix3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3fv"));
        }
        this.dispatch_glUniformMatrix3fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix3fv);
    }
    
    private native void dispatch_glUniformMatrix3fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix3fv = this._pat._addressof_glUniformMatrix3fv;
        if (addressof_glUniformMatrix3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3fv"));
        }
        this.dispatch_glUniformMatrix3fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix3fv);
    }
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix4fv = this._pat._addressof_glUniformMatrix4fv;
        if (addressof_glUniformMatrix4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4fv"));
        }
        this.dispatch_glUniformMatrix4fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix4fv);
    }
    
    private native void dispatch_glUniformMatrix4fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix4fv = this._pat._addressof_glUniformMatrix4fv;
        if (addressof_glUniformMatrix4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4fv"));
        }
        this.dispatch_glUniformMatrix4fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix4fv);
    }
    
    @Override
    public void glUseProgram(final int n) {
        final long addressof_glUseProgram = this._pat._addressof_glUseProgram;
        if (addressof_glUseProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUseProgram"));
        }
        this.dispatch_glUseProgram1(n, addressof_glUseProgram);
    }
    
    private native void dispatch_glUseProgram1(final int p0, final long p1);
    
    @Override
    public void glValidateProgram(final int n) {
        final long addressof_glValidateProgram = this._pat._addressof_glValidateProgram;
        if (addressof_glValidateProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glValidateProgram"));
        }
        this.dispatch_glValidateProgram1(n, addressof_glValidateProgram);
    }
    
    private native void dispatch_glValidateProgram1(final int p0, final long p1);
    
    @Override
    public void glVertexAttrib1f(final int n, final float n2) {
        final long addressof_glVertexAttrib1f = this._pat._addressof_glVertexAttrib1f;
        if (addressof_glVertexAttrib1f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib1f"));
        }
        this.dispatch_glVertexAttrib1f1(n, n2, addressof_glVertexAttrib1f);
    }
    
    private native void dispatch_glVertexAttrib1f1(final int p0, final float p1, final long p2);
    
    @Override
    public void glVertexAttrib1fv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glVertexAttrib1fv = this._pat._addressof_glVertexAttrib1fv;
        if (addressof_glVertexAttrib1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib1fv"));
        }
        this.dispatch_glVertexAttrib1fv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glVertexAttrib1fv);
    }
    
    private native void dispatch_glVertexAttrib1fv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttrib1fv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttrib1fv = this._pat._addressof_glVertexAttrib1fv;
        if (addressof_glVertexAttrib1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib1fv"));
        }
        this.dispatch_glVertexAttrib1fv1(n, array, 4 * n2, false, addressof_glVertexAttrib1fv);
    }
    
    @Override
    public void glVertexAttrib2f(final int n, final float n2, final float n3) {
        final long addressof_glVertexAttrib2f = this._pat._addressof_glVertexAttrib2f;
        if (addressof_glVertexAttrib2f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib2f"));
        }
        this.dispatch_glVertexAttrib2f1(n, n2, n3, addressof_glVertexAttrib2f);
    }
    
    private native void dispatch_glVertexAttrib2f1(final int p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glVertexAttrib2fv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glVertexAttrib2fv = this._pat._addressof_glVertexAttrib2fv;
        if (addressof_glVertexAttrib2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib2fv"));
        }
        this.dispatch_glVertexAttrib2fv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glVertexAttrib2fv);
    }
    
    private native void dispatch_glVertexAttrib2fv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttrib2fv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttrib2fv = this._pat._addressof_glVertexAttrib2fv;
        if (addressof_glVertexAttrib2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib2fv"));
        }
        this.dispatch_glVertexAttrib2fv1(n, array, 4 * n2, false, addressof_glVertexAttrib2fv);
    }
    
    @Override
    public void glVertexAttrib3f(final int n, final float n2, final float n3, final float n4) {
        final long addressof_glVertexAttrib3f = this._pat._addressof_glVertexAttrib3f;
        if (addressof_glVertexAttrib3f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib3f"));
        }
        this.dispatch_glVertexAttrib3f1(n, n2, n3, n4, addressof_glVertexAttrib3f);
    }
    
    private native void dispatch_glVertexAttrib3f1(final int p0, final float p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glVertexAttrib3fv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glVertexAttrib3fv = this._pat._addressof_glVertexAttrib3fv;
        if (addressof_glVertexAttrib3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib3fv"));
        }
        this.dispatch_glVertexAttrib3fv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glVertexAttrib3fv);
    }
    
    private native void dispatch_glVertexAttrib3fv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttrib3fv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttrib3fv = this._pat._addressof_glVertexAttrib3fv;
        if (addressof_glVertexAttrib3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib3fv"));
        }
        this.dispatch_glVertexAttrib3fv1(n, array, 4 * n2, false, addressof_glVertexAttrib3fv);
    }
    
    @Override
    public void glVertexAttrib4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long addressof_glVertexAttrib4f = this._pat._addressof_glVertexAttrib4f;
        if (addressof_glVertexAttrib4f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib4f"));
        }
        this.dispatch_glVertexAttrib4f1(n, n2, n3, n4, n5, addressof_glVertexAttrib4f);
    }
    
    private native void dispatch_glVertexAttrib4f1(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glVertexAttrib4fv(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glVertexAttrib4fv = this._pat._addressof_glVertexAttrib4fv;
        if (addressof_glVertexAttrib4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib4fv"));
        }
        this.dispatch_glVertexAttrib4fv1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glVertexAttrib4fv);
    }
    
    private native void dispatch_glVertexAttrib4fv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttrib4fv(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttrib4fv = this._pat._addressof_glVertexAttrib4fv;
        if (addressof_glVertexAttrib4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttrib4fv"));
        }
        this.dispatch_glVertexAttrib4fv1(n, array, 4 * n2, false, addressof_glVertexAttrib4fv);
    }
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glVertexAttribPointer = this._pat._addressof_glVertexAttribPointer;
        if (addressof_glVertexAttribPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribPointer"));
        }
        this.dispatch_glVertexAttribPointer0(n, n2, n3, b, n4, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glVertexAttribPointer);
    }
    
    private native void dispatch_glVertexAttribPointer0(final int p0, final int p1, final int p2, final boolean p3, final int p4, final Object p5, final int p6, final long p7);
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        this.checkArrayVBOBound(true);
        final long addressof_glVertexAttribPointer = this._pat._addressof_glVertexAttribPointer;
        if (addressof_glVertexAttribPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribPointer"));
        }
        this.dispatch_glVertexAttribPointer0(n, n2, n3, b, n4, n5, addressof_glVertexAttribPointer);
    }
    
    private native void dispatch_glVertexAttribPointer0(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5, final long p6);
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glViewport = this._pat._addressof_glViewport;
        if (addressof_glViewport == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewport"));
        }
        this.dispatch_glViewport1(n, n2, n3, n4, addressof_glViewport);
    }
    
    private native void dispatch_glViewport1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glReadBuffer(final int n) {
        final long addressof_glReadBuffer = this._pat._addressof_glReadBuffer;
        if (addressof_glReadBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadBuffer"));
        }
        this.dispatch_glReadBuffer1(n, addressof_glReadBuffer);
    }
    
    private native void dispatch_glReadBuffer1(final int p0, final long p1);
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n4);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawRangeElements = this._pat._addressof_glDrawRangeElements;
        if (addressof_glDrawRangeElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElements"));
        }
        this.dispatch_glDrawRangeElements1(n, n2, n3, n4, n5, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glDrawRangeElements);
    }
    
    private native void dispatch_glDrawRangeElements1(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawRangeElements = this._pat._addressof_glDrawRangeElements;
        if (addressof_glDrawRangeElements == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElements"));
        }
        this.dispatch_glDrawRangeElements1(n, n2, n3, n4, n5, n6, addressof_glDrawRangeElements);
    }
    
    private native void dispatch_glDrawRangeElements1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final long p6);
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n8, n9, n4, n5, n6, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexImage3D = this._pat._addressof_glTexImage3D;
        if (addressof_glTexImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage3D"));
        }
        this.dispatch_glTexImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexImage3D);
    }
    
    private native void dispatch_glTexImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final Object p9, final int p10, final boolean p11, final long p12);
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexImage3D = this._pat._addressof_glTexImage3D;
        if (addressof_glTexImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage3D"));
        }
        this.dispatch_glTexImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, addressof_glTexImage3D);
    }
    
    private native void dispatch_glTexImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9, final long p10);
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        Buffers.rangeCheckBytes(buffer, this.imageSizeInBytes(n9, n10, n6, n7, n8, false));
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glTexSubImage3D = this._pat._addressof_glTexSubImage3D;
        if (addressof_glTexSubImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage3D"));
        }
        this.dispatch_glTexSubImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glTexSubImage3D);
    }
    
    private native void dispatch_glTexSubImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.checkUnpackPBOBound(true);
        final long addressof_glTexSubImage3D = this._pat._addressof_glTexSubImage3D;
        if (addressof_glTexSubImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexSubImage3D"));
        }
        this.dispatch_glTexSubImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, addressof_glTexSubImage3D);
    }
    
    private native void dispatch_glTexSubImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    @Override
    public void glCopyTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        final long addressof_glCopyTexSubImage3D = this._pat._addressof_glCopyTexSubImage3D;
        if (addressof_glCopyTexSubImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTexSubImage3D"));
        }
        this.dispatch_glCopyTexSubImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glCopyTexSubImage3D);
    }
    
    private native void dispatch_glCopyTexSubImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexImage3D = this._pat._addressof_glCompressedTexImage3D;
        if (addressof_glCompressedTexImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage3D"));
        }
        this.dispatch_glCompressedTexImage3D1(n, n2, n3, n4, n5, n6, n7, n8, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexImage3D);
    }
    
    private native void dispatch_glCompressedTexImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexImage3D = this._pat._addressof_glCompressedTexImage3D;
        if (addressof_glCompressedTexImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexImage3D"));
        }
        this.dispatch_glCompressedTexImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, addressof_glCompressedTexImage3D);
    }
    
    private native void dispatch_glCompressedTexImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8, final long p9);
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkUnpackPBOUnbound(true);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glCompressedTexSubImage3D = this._pat._addressof_glCompressedTexSubImage3D;
        if (addressof_glCompressedTexSubImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage3D"));
        }
        this.dispatch_glCompressedTexSubImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glCompressedTexSubImage3D);
    }
    
    private native void dispatch_glCompressedTexSubImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.checkUnpackPBOBound(true);
        final long addressof_glCompressedTexSubImage3D = this._pat._addressof_glCompressedTexSubImage3D;
        if (addressof_glCompressedTexSubImage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCompressedTexSubImage3D"));
        }
        this.dispatch_glCompressedTexSubImage3D1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, addressof_glCompressedTexSubImage3D);
    }
    
    private native void dispatch_glCompressedTexSubImage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10, final long p11);
    
    @Override
    public void glGenQueries(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenQueries = this._pat._addressof_glGenQueries;
        if (addressof_glGenQueries == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenQueries"));
        }
        this.dispatch_glGenQueries1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenQueries);
    }
    
    private native void dispatch_glGenQueries1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenQueries(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"ids_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenQueries = this._pat._addressof_glGenQueries;
        if (addressof_glGenQueries == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenQueries"));
        }
        this.dispatch_glGenQueries1(n, array, 4 * n2, false, addressof_glGenQueries);
    }
    
    @Override
    public void glDeleteQueries(final int n, final IntBuffer intBuffer) {
        Buffers.rangeCheck(intBuffer, n);
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteQueries = this._pat._addressof_glDeleteQueries;
        if (addressof_glDeleteQueries == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteQueries"));
        }
        this.dispatch_glDeleteQueries1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteQueries);
    }
    
    private native void dispatch_glDeleteQueries1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteQueries(final int n, final int[] array, final int n2) {
        Buffers.rangeCheck(array, n2, n);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"ids_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteQueries = this._pat._addressof_glDeleteQueries;
        if (addressof_glDeleteQueries == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteQueries"));
        }
        this.dispatch_glDeleteQueries1(n, array, 4 * n2, false, addressof_glDeleteQueries);
    }
    
    @Override
    public boolean glIsQuery(final int n) {
        final long addressof_glIsQuery = this._pat._addressof_glIsQuery;
        if (addressof_glIsQuery == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsQuery"));
        }
        return this.dispatch_glIsQuery1(n, addressof_glIsQuery);
    }
    
    private native boolean dispatch_glIsQuery1(final int p0, final long p1);
    
    @Override
    public void glBeginQuery(final int n, final int n2) {
        final long addressof_glBeginQuery = this._pat._addressof_glBeginQuery;
        if (addressof_glBeginQuery == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBeginQuery"));
        }
        this.dispatch_glBeginQuery1(n, n2, addressof_glBeginQuery);
    }
    
    private native void dispatch_glBeginQuery1(final int p0, final int p1, final long p2);
    
    @Override
    public void glEndQuery(final int n) {
        final long addressof_glEndQuery = this._pat._addressof_glEndQuery;
        if (addressof_glEndQuery == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEndQuery"));
        }
        this.dispatch_glEndQuery1(n, addressof_glEndQuery);
    }
    
    private native void dispatch_glEndQuery1(final int p0, final long p1);
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetQueryiv = this._pat._addressof_glGetQueryiv;
        if (addressof_glGetQueryiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryiv"));
        }
        this.dispatch_glGetQueryiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetQueryiv);
    }
    
    private native void dispatch_glGetQueryiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetQueryiv = this._pat._addressof_glGetQueryiv;
        if (addressof_glGetQueryiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryiv"));
        }
        this.dispatch_glGetQueryiv1(n, n2, array, 4 * n3, false, addressof_glGetQueryiv);
    }
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetQueryObjectuiv = this._pat._addressof_glGetQueryObjectuiv;
        if (addressof_glGetQueryObjectuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectuiv"));
        }
        this.dispatch_glGetQueryObjectuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetQueryObjectuiv);
    }
    
    private native void dispatch_glGetQueryObjectuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetQueryObjectuiv = this._pat._addressof_glGetQueryObjectuiv;
        if (addressof_glGetQueryObjectuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectuiv"));
        }
        this.dispatch_glGetQueryObjectuiv1(n, n2, array, 4 * n3, false, addressof_glGetQueryObjectuiv);
    }
    
    private boolean glUnmapBufferDelegate(final int n) {
        final long addressof_glUnmapBuffer = this._pat._addressof_glUnmapBuffer;
        if (addressof_glUnmapBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUnmapBuffer"));
        }
        return this.dispatch_glUnmapBufferDelegate1(n, addressof_glUnmapBuffer);
    }
    
    private native boolean dispatch_glUnmapBufferDelegate1(final int p0, final long p1);
    
    @Override
    public void glDrawBuffers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDrawBuffers = this._pat._addressof_glDrawBuffers;
        if (addressof_glDrawBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawBuffers"));
        }
        this.dispatch_glDrawBuffers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDrawBuffers);
    }
    
    private native void dispatch_glDrawBuffers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDrawBuffers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"bufs_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDrawBuffers = this._pat._addressof_glDrawBuffers;
        if (addressof_glDrawBuffers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawBuffers"));
        }
        this.dispatch_glDrawBuffers1(n, array, 4 * n2, false, addressof_glDrawBuffers);
    }
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix2x3fv = this._pat._addressof_glUniformMatrix2x3fv;
        if (addressof_glUniformMatrix2x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x3fv"));
        }
        this.dispatch_glUniformMatrix2x3fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix2x3fv);
    }
    
    private native void dispatch_glUniformMatrix2x3fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix2x3fv = this._pat._addressof_glUniformMatrix2x3fv;
        if (addressof_glUniformMatrix2x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x3fv"));
        }
        this.dispatch_glUniformMatrix2x3fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix2x3fv);
    }
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix3x2fv = this._pat._addressof_glUniformMatrix3x2fv;
        if (addressof_glUniformMatrix3x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x2fv"));
        }
        this.dispatch_glUniformMatrix3x2fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix3x2fv);
    }
    
    private native void dispatch_glUniformMatrix3x2fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix3x2fv = this._pat._addressof_glUniformMatrix3x2fv;
        if (addressof_glUniformMatrix3x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x2fv"));
        }
        this.dispatch_glUniformMatrix3x2fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix3x2fv);
    }
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix2x4fv = this._pat._addressof_glUniformMatrix2x4fv;
        if (addressof_glUniformMatrix2x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x4fv"));
        }
        this.dispatch_glUniformMatrix2x4fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix2x4fv);
    }
    
    private native void dispatch_glUniformMatrix2x4fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix2x4fv = this._pat._addressof_glUniformMatrix2x4fv;
        if (addressof_glUniformMatrix2x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x4fv"));
        }
        this.dispatch_glUniformMatrix2x4fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix2x4fv);
    }
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix4x2fv = this._pat._addressof_glUniformMatrix4x2fv;
        if (addressof_glUniformMatrix4x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x2fv"));
        }
        this.dispatch_glUniformMatrix4x2fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix4x2fv);
    }
    
    private native void dispatch_glUniformMatrix4x2fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix4x2fv = this._pat._addressof_glUniformMatrix4x2fv;
        if (addressof_glUniformMatrix4x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x2fv"));
        }
        this.dispatch_glUniformMatrix4x2fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix4x2fv);
    }
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix3x4fv = this._pat._addressof_glUniformMatrix3x4fv;
        if (addressof_glUniformMatrix3x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x4fv"));
        }
        this.dispatch_glUniformMatrix3x4fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix3x4fv);
    }
    
    private native void dispatch_glUniformMatrix3x4fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix3x4fv = this._pat._addressof_glUniformMatrix3x4fv;
        if (addressof_glUniformMatrix3x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x4fv"));
        }
        this.dispatch_glUniformMatrix3x4fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix3x4fv);
    }
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix4x3fv = this._pat._addressof_glUniformMatrix4x3fv;
        if (addressof_glUniformMatrix4x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x3fv"));
        }
        this.dispatch_glUniformMatrix4x3fv1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix4x3fv);
    }
    
    private native void dispatch_glUniformMatrix4x3fv1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix4x3fv = this._pat._addressof_glUniformMatrix4x3fv;
        if (addressof_glUniformMatrix4x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x3fv"));
        }
        this.dispatch_glUniformMatrix4x3fv1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix4x3fv);
    }
    
    @Override
    public void glBlitFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long addressof_glBlitFramebuffer = this._pat._addressof_glBlitFramebuffer;
        if (addressof_glBlitFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlitFramebuffer"));
        }
        this.dispatch_glBlitFramebuffer1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, addressof_glBlitFramebuffer);
    }
    
    private native void dispatch_glBlitFramebuffer1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisample = this._pat._addressof_glRenderbufferStorageMultisample;
        if (addressof_glRenderbufferStorageMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisample"));
        }
        this.dispatch_glRenderbufferStorageMultisample1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisample);
    }
    
    private native void dispatch_glRenderbufferStorageMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glFramebufferTextureLayer = this._pat._addressof_glFramebufferTextureLayer;
        if (addressof_glFramebufferTextureLayer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTextureLayer"));
        }
        this.dispatch_glFramebufferTextureLayer1(n, n2, n3, n4, n5, addressof_glFramebufferTextureLayer);
    }
    
    private native void dispatch_glFramebufferTextureLayer1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    private long glMapBufferRangeDelegate(final int n, final long n2, final long n3, final int n4) {
        final long addressof_glMapBufferRange = this._pat._addressof_glMapBufferRange;
        if (addressof_glMapBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMapBufferRange"));
        }
        return this.dispatch_glMapBufferRangeDelegate1(n, n2, n3, n4, addressof_glMapBufferRange);
    }
    
    private native long dispatch_glMapBufferRangeDelegate1(final int p0, final long p1, final long p2, final int p3, final long p4);
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        final long addressof_glFlushMappedBufferRange = this._pat._addressof_glFlushMappedBufferRange;
        if (addressof_glFlushMappedBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFlushMappedBufferRange"));
        }
        this.dispatch_glFlushMappedBufferRange1(n, n2, n3, addressof_glFlushMappedBufferRange);
    }
    
    private native void dispatch_glFlushMappedBufferRange1(final int p0, final long p1, final long p2, final long p3);
    
    @Override
    public void glBindVertexArray(final int n) {
        final long addressof_glBindVertexArray = this._pat._addressof_glBindVertexArray;
        if (addressof_glBindVertexArray == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindVertexArray"));
        }
        this.dispatch_glBindVertexArray1(n, addressof_glBindVertexArray);
        this.bufferStateTracker.setBoundBufferObject(34229, n);
    }
    
    private native void dispatch_glBindVertexArray1(final int p0, final long p1);
    
    @Override
    public void glDeleteVertexArrays(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteVertexArrays = this._pat._addressof_glDeleteVertexArrays;
        if (addressof_glDeleteVertexArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArrays"));
        }
        this.dispatch_glDeleteVertexArrays1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteVertexArrays);
    }
    
    private native void dispatch_glDeleteVertexArrays1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteVertexArrays(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteVertexArrays = this._pat._addressof_glDeleteVertexArrays;
        if (addressof_glDeleteVertexArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArrays"));
        }
        this.dispatch_glDeleteVertexArrays1(n, array, 4 * n2, false, addressof_glDeleteVertexArrays);
    }
    
    @Override
    public void glGenVertexArrays(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenVertexArrays = this._pat._addressof_glGenVertexArrays;
        if (addressof_glGenVertexArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArrays"));
        }
        this.dispatch_glGenVertexArrays1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenVertexArrays);
    }
    
    private native void dispatch_glGenVertexArrays1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenVertexArrays(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenVertexArrays = this._pat._addressof_glGenVertexArrays;
        if (addressof_glGenVertexArrays == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArrays"));
        }
        this.dispatch_glGenVertexArrays1(n, array, 4 * n2, false, addressof_glGenVertexArrays);
    }
    
    @Override
    public boolean glIsVertexArray(final int n) {
        final long addressof_glIsVertexArray = this._pat._addressof_glIsVertexArray;
        if (addressof_glIsVertexArray == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsVertexArray"));
        }
        return this.dispatch_glIsVertexArray1(n, addressof_glIsVertexArray);
    }
    
    private native boolean dispatch_glIsVertexArray1(final int p0, final long p1);
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetIntegeri_v = this._pat._addressof_glGetIntegeri_v;
        if (addressof_glGetIntegeri_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegeri_v"));
        }
        this.dispatch_glGetIntegeri_v1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetIntegeri_v);
    }
    
    private native void dispatch_glGetIntegeri_v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"data_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetIntegeri_v = this._pat._addressof_glGetIntegeri_v;
        if (addressof_glGetIntegeri_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegeri_v"));
        }
        this.dispatch_glGetIntegeri_v1(n, n2, array, 4 * n3, false, addressof_glGetIntegeri_v);
    }
    
    @Override
    public void glBeginTransformFeedback(final int n) {
        final long addressof_glBeginTransformFeedback = this._pat._addressof_glBeginTransformFeedback;
        if (addressof_glBeginTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBeginTransformFeedback"));
        }
        this.dispatch_glBeginTransformFeedback1(n, addressof_glBeginTransformFeedback);
    }
    
    private native void dispatch_glBeginTransformFeedback1(final int p0, final long p1);
    
    @Override
    public void glEndTransformFeedback() {
        final long addressof_glEndTransformFeedback = this._pat._addressof_glEndTransformFeedback;
        if (addressof_glEndTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEndTransformFeedback"));
        }
        this.dispatch_glEndTransformFeedback1(addressof_glEndTransformFeedback);
    }
    
    private native void dispatch_glEndTransformFeedback1(final long p0);
    
    @Override
    public void glBindBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long addressof_glBindBufferRange = this._pat._addressof_glBindBufferRange;
        if (addressof_glBindBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindBufferRange"));
        }
        this.dispatch_glBindBufferRange1(n, n2, n3, n4, n5, addressof_glBindBufferRange);
        this.bufferStateTracker.setBoundBufferObject(n, n3);
    }
    
    private native void dispatch_glBindBufferRange1(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glBindBufferBase(final int n, final int n2, final int n3) {
        final long addressof_glBindBufferBase = this._pat._addressof_glBindBufferBase;
        if (addressof_glBindBufferBase == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindBufferBase"));
        }
        this.dispatch_glBindBufferBase1(n, n2, n3, addressof_glBindBufferBase);
        this.bufferStateTracker.setBoundBufferObject(n, n3);
    }
    
    private native void dispatch_glBindBufferBase1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTransformFeedbackVaryings(final int n, final int n2, final String[] array, final int n3) {
        final long addressof_glTransformFeedbackVaryings = this._pat._addressof_glTransformFeedbackVaryings;
        if (addressof_glTransformFeedbackVaryings == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTransformFeedbackVaryings"));
        }
        this.dispatch_glTransformFeedbackVaryings1(n, n2, array, n3, addressof_glTransformFeedbackVaryings);
    }
    
    private native void dispatch_glTransformFeedbackVaryings1(final int p0, final int p1, final String[] p2, final int p3, final long p4);
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(intBuffer3);
        final boolean direct4 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetTransformFeedbackVarying = this._pat._addressof_glGetTransformFeedbackVarying;
        if (addressof_glGetTransformFeedbackVarying == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTransformFeedbackVarying"));
        }
        this.dispatch_glGetTransformFeedbackVarying1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? intBuffer3 : Buffers.getArray(intBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer3) : Buffers.getIndirectBufferByteOffset(intBuffer3), direct3, direct4 ? byteBuffer : Buffers.getArray(byteBuffer), direct4 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct4, addressof_glGetTransformFeedbackVarying);
    }
    
    private native void dispatch_glGetTransformFeedbackVarying1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final Object p9, final int p10, final boolean p11, final Object p12, final int p13, final boolean p14, final long p15);
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"size_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n6) {
            throw new GLException("array offset argument \"type_offset\" (" + n6 + ") equals or exceeds array length (" + array3.length + ")");
        }
        if (array4 != null && array4.length <= n7) {
            throw new GLException("array offset argument \"name_offset\" (" + n7 + ") equals or exceeds array length (" + array4.length + ")");
        }
        final long addressof_glGetTransformFeedbackVarying = this._pat._addressof_glGetTransformFeedbackVarying;
        if (addressof_glGetTransformFeedbackVarying == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTransformFeedbackVarying"));
        }
        this.dispatch_glGetTransformFeedbackVarying1(n, n2, n3, array, 4 * n4, false, array2, 4 * n5, false, array3, 4 * n6, false, array4, n7, false, addressof_glGetTransformFeedbackVarying);
    }
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final Buffer buffer) {
        this.checkArrayVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        if (!Buffers.isDirect(buffer)) {
            throw new GLException("Argument \"pointer\" is not a direct buffer");
        }
        final long addressof_glVertexAttribIPointer = this._pat._addressof_glVertexAttribIPointer;
        if (addressof_glVertexAttribIPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribIPointer"));
        }
        this.dispatch_glVertexAttribIPointer0(n, n2, n3, n4, buffer, Buffers.getDirectBufferByteOffset(buffer), addressof_glVertexAttribIPointer);
    }
    
    private native void dispatch_glVertexAttribIPointer0(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final long p6);
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final long n5) {
        this.checkArrayVBOBound(true);
        final long addressof_glVertexAttribIPointer = this._pat._addressof_glVertexAttribIPointer;
        if (addressof_glVertexAttribIPointer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribIPointer"));
        }
        this.dispatch_glVertexAttribIPointer0(n, n2, n3, n4, n5, addressof_glVertexAttribIPointer);
    }
    
    private native void dispatch_glVertexAttribIPointer0(final int p0, final int p1, final int p2, final int p3, final long p4, final long p5);
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetVertexAttribIiv = this._pat._addressof_glGetVertexAttribIiv;
        if (addressof_glGetVertexAttribIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribIiv"));
        }
        this.dispatch_glGetVertexAttribIiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetVertexAttribIiv);
    }
    
    private native void dispatch_glGetVertexAttribIiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetVertexAttribIiv = this._pat._addressof_glGetVertexAttribIiv;
        if (addressof_glGetVertexAttribIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribIiv"));
        }
        this.dispatch_glGetVertexAttribIiv1(n, n2, array, 4 * n3, false, addressof_glGetVertexAttribIiv);
    }
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetVertexAttribIuiv = this._pat._addressof_glGetVertexAttribIuiv;
        if (addressof_glGetVertexAttribIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribIuiv"));
        }
        this.dispatch_glGetVertexAttribIuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetVertexAttribIuiv);
    }
    
    private native void dispatch_glGetVertexAttribIuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetVertexAttribIuiv = this._pat._addressof_glGetVertexAttribIuiv;
        if (addressof_glGetVertexAttribIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetVertexAttribIuiv"));
        }
        this.dispatch_glGetVertexAttribIuiv1(n, n2, array, 4 * n3, false, addressof_glGetVertexAttribIuiv);
    }
    
    @Override
    public void glVertexAttribI4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glVertexAttribI4i = this._pat._addressof_glVertexAttribI4i;
        if (addressof_glVertexAttribI4i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4i"));
        }
        this.dispatch_glVertexAttribI4i1(n, n2, n3, n4, n5, addressof_glVertexAttribI4i);
    }
    
    private native void dispatch_glVertexAttribI4i1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glVertexAttribI4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glVertexAttribI4ui = this._pat._addressof_glVertexAttribI4ui;
        if (addressof_glVertexAttribI4ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4ui"));
        }
        this.dispatch_glVertexAttribI4ui1(n, n2, n3, n4, n5, addressof_glVertexAttribI4ui);
    }
    
    private native void dispatch_glVertexAttribI4ui1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glVertexAttribI4iv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glVertexAttribI4iv = this._pat._addressof_glVertexAttribI4iv;
        if (addressof_glVertexAttribI4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4iv"));
        }
        this.dispatch_glVertexAttribI4iv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glVertexAttribI4iv);
    }
    
    private native void dispatch_glVertexAttribI4iv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttribI4iv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttribI4iv = this._pat._addressof_glVertexAttribI4iv;
        if (addressof_glVertexAttribI4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4iv"));
        }
        this.dispatch_glVertexAttribI4iv1(n, array, 4 * n2, false, addressof_glVertexAttribI4iv);
    }
    
    @Override
    public void glVertexAttribI4uiv(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glVertexAttribI4uiv = this._pat._addressof_glVertexAttribI4uiv;
        if (addressof_glVertexAttribI4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4uiv"));
        }
        this.dispatch_glVertexAttribI4uiv1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glVertexAttribI4uiv);
    }
    
    private native void dispatch_glVertexAttribI4uiv1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glVertexAttribI4uiv(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glVertexAttribI4uiv = this._pat._addressof_glVertexAttribI4uiv;
        if (addressof_glVertexAttribI4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribI4uiv"));
        }
        this.dispatch_glVertexAttribI4uiv1(n, array, 4 * n2, false, addressof_glVertexAttribI4uiv);
    }
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetUniformuiv = this._pat._addressof_glGetUniformuiv;
        if (addressof_glGetUniformuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformuiv"));
        }
        this.dispatch_glGetUniformuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetUniformuiv);
    }
    
    private native void dispatch_glGetUniformuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetUniformuiv = this._pat._addressof_glGetUniformuiv;
        if (addressof_glGetUniformuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformuiv"));
        }
        this.dispatch_glGetUniformuiv1(n, n2, array, 4 * n3, false, addressof_glGetUniformuiv);
    }
    
    @Override
    public int glGetFragDataLocation(final int n, final String s) {
        final long addressof_glGetFragDataLocation = this._pat._addressof_glGetFragDataLocation;
        if (addressof_glGetFragDataLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFragDataLocation"));
        }
        return this.dispatch_glGetFragDataLocation1(n, s, addressof_glGetFragDataLocation);
    }
    
    private native int dispatch_glGetFragDataLocation1(final int p0, final String p1, final long p2);
    
    @Override
    public void glUniform1ui(final int n, final int n2) {
        final long addressof_glUniform1ui = this._pat._addressof_glUniform1ui;
        if (addressof_glUniform1ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1ui"));
        }
        this.dispatch_glUniform1ui1(n, n2, addressof_glUniform1ui);
    }
    
    private native void dispatch_glUniform1ui1(final int p0, final int p1, final long p2);
    
    @Override
    public void glUniform2ui(final int n, final int n2, final int n3) {
        final long addressof_glUniform2ui = this._pat._addressof_glUniform2ui;
        if (addressof_glUniform2ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2ui"));
        }
        this.dispatch_glUniform2ui1(n, n2, n3, addressof_glUniform2ui);
    }
    
    private native void dispatch_glUniform2ui1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glUniform3ui(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glUniform3ui = this._pat._addressof_glUniform3ui;
        if (addressof_glUniform3ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3ui"));
        }
        this.dispatch_glUniform3ui1(n, n2, n3, n4, addressof_glUniform3ui);
    }
    
    private native void dispatch_glUniform3ui1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glUniform4ui = this._pat._addressof_glUniform4ui;
        if (addressof_glUniform4ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4ui"));
        }
        this.dispatch_glUniform4ui1(n, n2, n3, n4, n5, addressof_glUniform4ui);
    }
    
    private native void dispatch_glUniform4ui1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform1uiv = this._pat._addressof_glUniform1uiv;
        if (addressof_glUniform1uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1uiv"));
        }
        this.dispatch_glUniform1uiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform1uiv);
    }
    
    private native void dispatch_glUniform1uiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform1uiv = this._pat._addressof_glUniform1uiv;
        if (addressof_glUniform1uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform1uiv"));
        }
        this.dispatch_glUniform1uiv1(n, n2, array, 4 * n3, false, addressof_glUniform1uiv);
    }
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform2uiv = this._pat._addressof_glUniform2uiv;
        if (addressof_glUniform2uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2uiv"));
        }
        this.dispatch_glUniform2uiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform2uiv);
    }
    
    private native void dispatch_glUniform2uiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform2uiv = this._pat._addressof_glUniform2uiv;
        if (addressof_glUniform2uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform2uiv"));
        }
        this.dispatch_glUniform2uiv1(n, n2, array, 4 * n3, false, addressof_glUniform2uiv);
    }
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform3uiv = this._pat._addressof_glUniform3uiv;
        if (addressof_glUniform3uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3uiv"));
        }
        this.dispatch_glUniform3uiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform3uiv);
    }
    
    private native void dispatch_glUniform3uiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform3uiv = this._pat._addressof_glUniform3uiv;
        if (addressof_glUniform3uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform3uiv"));
        }
        this.dispatch_glUniform3uiv1(n, n2, array, 4 * n3, false, addressof_glUniform3uiv);
    }
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glUniform4uiv = this._pat._addressof_glUniform4uiv;
        if (addressof_glUniform4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4uiv"));
        }
        this.dispatch_glUniform4uiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glUniform4uiv);
    }
    
    private native void dispatch_glUniform4uiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniform4uiv = this._pat._addressof_glUniform4uiv;
        if (addressof_glUniform4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniform4uiv"));
        }
        this.dispatch_glUniform4uiv1(n, n2, array, 4 * n3, false, addressof_glUniform4uiv);
    }
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glClearBufferiv = this._pat._addressof_glClearBufferiv;
        if (addressof_glClearBufferiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferiv"));
        }
        this.dispatch_glClearBufferiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glClearBufferiv);
    }
    
    private native void dispatch_glClearBufferiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClearBufferiv = this._pat._addressof_glClearBufferiv;
        if (addressof_glClearBufferiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferiv"));
        }
        this.dispatch_glClearBufferiv1(n, n2, array, 4 * n3, false, addressof_glClearBufferiv);
    }
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glClearBufferuiv = this._pat._addressof_glClearBufferuiv;
        if (addressof_glClearBufferuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferuiv"));
        }
        this.dispatch_glClearBufferuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glClearBufferuiv);
    }
    
    private native void dispatch_glClearBufferuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClearBufferuiv = this._pat._addressof_glClearBufferuiv;
        if (addressof_glClearBufferuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferuiv"));
        }
        this.dispatch_glClearBufferuiv1(n, n2, array, 4 * n3, false, addressof_glClearBufferuiv);
    }
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glClearBufferfv = this._pat._addressof_glClearBufferfv;
        if (addressof_glClearBufferfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferfv"));
        }
        this.dispatch_glClearBufferfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glClearBufferfv);
    }
    
    private native void dispatch_glClearBufferfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glClearBufferfv = this._pat._addressof_glClearBufferfv;
        if (addressof_glClearBufferfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferfv"));
        }
        this.dispatch_glClearBufferfv1(n, n2, array, 4 * n3, false, addressof_glClearBufferfv);
    }
    
    @Override
    public void glClearBufferfi(final int n, final int n2, final float n3, final int n4) {
        final long addressof_glClearBufferfi = this._pat._addressof_glClearBufferfi;
        if (addressof_glClearBufferfi == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClearBufferfi"));
        }
        this.dispatch_glClearBufferfi1(n, n2, n3, n4, addressof_glClearBufferfi);
    }
    
    private native void dispatch_glClearBufferfi1(final int p0, final int p1, final float p2, final int p3, final long p4);
    
    @Override
    public String glGetStringi(final int n, final int n2) {
        final long addressof_glGetStringi = this._pat._addressof_glGetStringi;
        if (addressof_glGetStringi == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetStringi"));
        }
        return this.dispatch_glGetStringi1(n, n2, addressof_glGetStringi);
    }
    
    private native String dispatch_glGetStringi1(final int p0, final int p1, final long p2);
    
    @Override
    public void glCopyBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long addressof_glCopyBufferSubData = this._pat._addressof_glCopyBufferSubData;
        if (addressof_glCopyBufferSubData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyBufferSubData"));
        }
        this.dispatch_glCopyBufferSubData1(n, n2, n3, n4, n5, addressof_glCopyBufferSubData);
    }
    
    private native void dispatch_glCopyBufferSubData1(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetUniformIndices = this._pat._addressof_glGetUniformIndices;
        if (addressof_glGetUniformIndices == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformIndices"));
        }
        this.dispatch_glGetUniformIndices1(n, n2, array, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetUniformIndices);
    }
    
    private native void dispatch_glGetUniformIndices1(final int p0, final int p1, final String[] p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"uniformIndices_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetUniformIndices = this._pat._addressof_glGetUniformIndices;
        if (addressof_glGetUniformIndices == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformIndices"));
        }
        this.dispatch_glGetUniformIndices1(n, n2, array, array2, 4 * n3, false, addressof_glGetUniformIndices);
    }
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final IntBuffer intBuffer, final int n3, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetActiveUniformsiv = this._pat._addressof_glGetActiveUniformsiv;
        if (addressof_glGetActiveUniformsiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformsiv"));
        }
        this.dispatch_glGetActiveUniformsiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n3, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetActiveUniformsiv);
    }
    
    private native void dispatch_glGetActiveUniformsiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final int p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final int[] array, final int n3, final int n4, final int[] array2, final int n5) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"uniformIndices_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"params_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetActiveUniformsiv = this._pat._addressof_glGetActiveUniformsiv;
        if (addressof_glGetActiveUniformsiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformsiv"));
        }
        this.dispatch_glGetActiveUniformsiv1(n, n2, array, 4 * n3, false, n4, array2, 4 * n5, false, addressof_glGetActiveUniformsiv);
    }
    
    @Override
    public int glGetUniformBlockIndex(final int n, final String s) {
        final long addressof_glGetUniformBlockIndex = this._pat._addressof_glGetUniformBlockIndex;
        if (addressof_glGetUniformBlockIndex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetUniformBlockIndex"));
        }
        return this.dispatch_glGetUniformBlockIndex1(n, s, addressof_glGetUniformBlockIndex);
    }
    
    private native int dispatch_glGetUniformBlockIndex1(final int p0, final String p1, final long p2);
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetActiveUniformBlockiv = this._pat._addressof_glGetActiveUniformBlockiv;
        if (addressof_glGetActiveUniformBlockiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformBlockiv"));
        }
        this.dispatch_glGetActiveUniformBlockiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetActiveUniformBlockiv);
    }
    
    private native void dispatch_glGetActiveUniformBlockiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetActiveUniformBlockiv = this._pat._addressof_glGetActiveUniformBlockiv;
        if (addressof_glGetActiveUniformBlockiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformBlockiv"));
        }
        this.dispatch_glGetActiveUniformBlockiv1(n, n2, n3, array, 4 * n4, false, addressof_glGetActiveUniformBlockiv);
    }
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetActiveUniformBlockName = this._pat._addressof_glGetActiveUniformBlockName;
        if (addressof_glGetActiveUniformBlockName == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformBlockName"));
        }
        this.dispatch_glGetActiveUniformBlockName1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetActiveUniformBlockName);
    }
    
    private native void dispatch_glGetActiveUniformBlockName1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"uniformBlockName_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetActiveUniformBlockName = this._pat._addressof_glGetActiveUniformBlockName;
        if (addressof_glGetActiveUniformBlockName == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetActiveUniformBlockName"));
        }
        this.dispatch_glGetActiveUniformBlockName1(n, n2, n3, array, 4 * n4, false, array2, n5, false, addressof_glGetActiveUniformBlockName);
    }
    
    @Override
    public void glUniformBlockBinding(final int n, final int n2, final int n3) {
        final long addressof_glUniformBlockBinding = this._pat._addressof_glUniformBlockBinding;
        if (addressof_glUniformBlockBinding == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformBlockBinding"));
        }
        this.dispatch_glUniformBlockBinding1(n, n2, n3, addressof_glUniformBlockBinding);
    }
    
    private native void dispatch_glUniformBlockBinding1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glDrawArraysInstanced(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glDrawArraysInstanced = this._pat._addressof_glDrawArraysInstanced;
        if (addressof_glDrawArraysInstanced == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysInstanced"));
        }
        this.dispatch_glDrawArraysInstanced1(n, n2, n3, n4, addressof_glDrawArraysInstanced);
    }
    
    private native void dispatch_glDrawArraysInstanced1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstanced = this._pat._addressof_glDrawElementsInstanced;
        if (addressof_glDrawElementsInstanced == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstanced"));
        }
        this.dispatch_glDrawElementsInstanced1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsInstanced);
    }
    
    private native void dispatch_glDrawElementsInstanced1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElementsInstanced = this._pat._addressof_glDrawElementsInstanced;
        if (addressof_glDrawElementsInstanced == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstanced"));
        }
        this.dispatch_glDrawElementsInstanced1(n, n2, n3, n4, n5, addressof_glDrawElementsInstanced);
    }
    
    private native void dispatch_glDrawElementsInstanced1(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    @Override
    public long glFenceSync(final int n, final int n2) {
        final long addressof_glFenceSync = this._pat._addressof_glFenceSync;
        if (addressof_glFenceSync == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFenceSync"));
        }
        return this.dispatch_glFenceSync1(n, n2, addressof_glFenceSync);
    }
    
    private native long dispatch_glFenceSync1(final int p0, final int p1, final long p2);
    
    @Override
    public boolean glIsSync(final long n) {
        final long addressof_glIsSync = this._pat._addressof_glIsSync;
        if (addressof_glIsSync == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsSync"));
        }
        return this.dispatch_glIsSync1(n, addressof_glIsSync);
    }
    
    private native boolean dispatch_glIsSync1(final long p0, final long p1);
    
    @Override
    public void glDeleteSync(final long n) {
        final long addressof_glDeleteSync = this._pat._addressof_glDeleteSync;
        if (addressof_glDeleteSync == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteSync"));
        }
        this.dispatch_glDeleteSync1(n, addressof_glDeleteSync);
    }
    
    private native void dispatch_glDeleteSync1(final long p0, final long p1);
    
    @Override
    public int glClientWaitSync(final long n, final int n2, final long n3) {
        final long addressof_glClientWaitSync = this._pat._addressof_glClientWaitSync;
        if (addressof_glClientWaitSync == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glClientWaitSync"));
        }
        return this.dispatch_glClientWaitSync1(n, n2, n3, addressof_glClientWaitSync);
    }
    
    private native int dispatch_glClientWaitSync1(final long p0, final int p1, final long p2, final long p3);
    
    @Override
    public void glWaitSync(final long n, final int n2, final long n3) {
        final long addressof_glWaitSync = this._pat._addressof_glWaitSync;
        if (addressof_glWaitSync == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glWaitSync"));
        }
        this.dispatch_glWaitSync1(n, n2, n3, addressof_glWaitSync);
    }
    
    private native void dispatch_glWaitSync1(final long p0, final int p1, final long p2, final long p3);
    
    @Override
    public void glGetInteger64v(final int n, final LongBuffer longBuffer) {
        final boolean direct = Buffers.isDirect(longBuffer);
        final long addressof_glGetInteger64v = this._pat._addressof_glGetInteger64v;
        if (addressof_glGetInteger64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInteger64v"));
        }
        this.dispatch_glGetInteger64v1(n, direct ? longBuffer : Buffers.getArray(longBuffer), direct ? Buffers.getDirectBufferByteOffset(longBuffer) : Buffers.getIndirectBufferByteOffset(longBuffer), direct, addressof_glGetInteger64v);
    }
    
    private native void dispatch_glGetInteger64v1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetInteger64v(final int n, final long[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"data_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetInteger64v = this._pat._addressof_glGetInteger64v;
        if (addressof_glGetInteger64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInteger64v"));
        }
        this.dispatch_glGetInteger64v1(n, array, 8 * n2, false, addressof_glGetInteger64v);
    }
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetSynciv = this._pat._addressof_glGetSynciv;
        if (addressof_glGetSynciv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSynciv"));
        }
        this.dispatch_glGetSynciv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetSynciv);
    }
    
    private native void dispatch_glGetSynciv1(final long p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"values_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetSynciv = this._pat._addressof_glGetSynciv;
        if (addressof_glGetSynciv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSynciv"));
        }
        this.dispatch_glGetSynciv1(n, n2, n3, array, 4 * n4, false, array2, 4 * n5, false, addressof_glGetSynciv);
    }
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final LongBuffer longBuffer) {
        final boolean direct = Buffers.isDirect(longBuffer);
        final long addressof_glGetInteger64i_v = this._pat._addressof_glGetInteger64i_v;
        if (addressof_glGetInteger64i_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInteger64i_v"));
        }
        this.dispatch_glGetInteger64i_v1(n, n2, direct ? longBuffer : Buffers.getArray(longBuffer), direct ? Buffers.getDirectBufferByteOffset(longBuffer) : Buffers.getIndirectBufferByteOffset(longBuffer), direct, addressof_glGetInteger64i_v);
    }
    
    private native void dispatch_glGetInteger64i_v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final long[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"data_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetInteger64i_v = this._pat._addressof_glGetInteger64i_v;
        if (addressof_glGetInteger64i_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInteger64i_v"));
        }
        this.dispatch_glGetInteger64i_v1(n, n2, array, 8 * n3, false, addressof_glGetInteger64i_v);
    }
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final LongBuffer longBuffer) {
        final boolean direct = Buffers.isDirect(longBuffer);
        final long addressof_glGetBufferParameteri64v = this._pat._addressof_glGetBufferParameteri64v;
        if (addressof_glGetBufferParameteri64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteri64v"));
        }
        this.dispatch_glGetBufferParameteri64v1(n, n2, direct ? longBuffer : Buffers.getArray(longBuffer), direct ? Buffers.getDirectBufferByteOffset(longBuffer) : Buffers.getIndirectBufferByteOffset(longBuffer), direct, addressof_glGetBufferParameteri64v);
    }
    
    private native void dispatch_glGetBufferParameteri64v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final long[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBufferParameteri64v = this._pat._addressof_glGetBufferParameteri64v;
        if (addressof_glGetBufferParameteri64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBufferParameteri64v"));
        }
        this.dispatch_glGetBufferParameteri64v1(n, n2, array, 8 * n3, false, addressof_glGetBufferParameteri64v);
    }
    
    @Override
    public void glGenSamplers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenSamplers = this._pat._addressof_glGenSamplers;
        if (addressof_glGenSamplers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenSamplers"));
        }
        this.dispatch_glGenSamplers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenSamplers);
    }
    
    private native void dispatch_glGenSamplers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenSamplers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"samplers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenSamplers = this._pat._addressof_glGenSamplers;
        if (addressof_glGenSamplers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenSamplers"));
        }
        this.dispatch_glGenSamplers1(n, array, 4 * n2, false, addressof_glGenSamplers);
    }
    
    @Override
    public void glDeleteSamplers(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteSamplers = this._pat._addressof_glDeleteSamplers;
        if (addressof_glDeleteSamplers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteSamplers"));
        }
        this.dispatch_glDeleteSamplers1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteSamplers);
    }
    
    private native void dispatch_glDeleteSamplers1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteSamplers(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"samplers_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteSamplers = this._pat._addressof_glDeleteSamplers;
        if (addressof_glDeleteSamplers == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteSamplers"));
        }
        this.dispatch_glDeleteSamplers1(n, array, 4 * n2, false, addressof_glDeleteSamplers);
    }
    
    @Override
    public boolean glIsSampler(final int n) {
        final long addressof_glIsSampler = this._pat._addressof_glIsSampler;
        if (addressof_glIsSampler == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsSampler"));
        }
        return this.dispatch_glIsSampler1(n, addressof_glIsSampler);
    }
    
    private native boolean dispatch_glIsSampler1(final int p0, final long p1);
    
    @Override
    public void glBindSampler(final int n, final int n2) {
        final long addressof_glBindSampler = this._pat._addressof_glBindSampler;
        if (addressof_glBindSampler == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindSampler"));
        }
        this.dispatch_glBindSampler1(n, n2, addressof_glBindSampler);
    }
    
    private native void dispatch_glBindSampler1(final int p0, final int p1, final long p2);
    
    @Override
    public void glSamplerParameteri(final int n, final int n2, final int n3) {
        final long addressof_glSamplerParameteri = this._pat._addressof_glSamplerParameteri;
        if (addressof_glSamplerParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameteri"));
        }
        this.dispatch_glSamplerParameteri1(n, n2, n3, addressof_glSamplerParameteri);
    }
    
    private native void dispatch_glSamplerParameteri1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glSamplerParameteriv = this._pat._addressof_glSamplerParameteriv;
        if (addressof_glSamplerParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameteriv"));
        }
        this.dispatch_glSamplerParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glSamplerParameteriv);
    }
    
    private native void dispatch_glSamplerParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"param_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glSamplerParameteriv = this._pat._addressof_glSamplerParameteriv;
        if (addressof_glSamplerParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameteriv"));
        }
        this.dispatch_glSamplerParameteriv1(n, n2, array, 4 * n3, false, addressof_glSamplerParameteriv);
    }
    
    @Override
    public void glSamplerParameterf(final int n, final int n2, final float n3) {
        final long addressof_glSamplerParameterf = this._pat._addressof_glSamplerParameterf;
        if (addressof_glSamplerParameterf == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterf"));
        }
        this.dispatch_glSamplerParameterf1(n, n2, n3, addressof_glSamplerParameterf);
    }
    
    private native void dispatch_glSamplerParameterf1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glSamplerParameterfv = this._pat._addressof_glSamplerParameterfv;
        if (addressof_glSamplerParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterfv"));
        }
        this.dispatch_glSamplerParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glSamplerParameterfv);
    }
    
    private native void dispatch_glSamplerParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"param_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glSamplerParameterfv = this._pat._addressof_glSamplerParameterfv;
        if (addressof_glSamplerParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterfv"));
        }
        this.dispatch_glSamplerParameterfv1(n, n2, array, 4 * n3, false, addressof_glSamplerParameterfv);
    }
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetSamplerParameteriv = this._pat._addressof_glGetSamplerParameteriv;
        if (addressof_glGetSamplerParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameteriv"));
        }
        this.dispatch_glGetSamplerParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetSamplerParameteriv);
    }
    
    private native void dispatch_glGetSamplerParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetSamplerParameteriv = this._pat._addressof_glGetSamplerParameteriv;
        if (addressof_glGetSamplerParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameteriv"));
        }
        this.dispatch_glGetSamplerParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetSamplerParameteriv);
    }
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetSamplerParameterfv = this._pat._addressof_glGetSamplerParameterfv;
        if (addressof_glGetSamplerParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterfv"));
        }
        this.dispatch_glGetSamplerParameterfv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetSamplerParameterfv);
    }
    
    private native void dispatch_glGetSamplerParameterfv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetSamplerParameterfv = this._pat._addressof_glGetSamplerParameterfv;
        if (addressof_glGetSamplerParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterfv"));
        }
        this.dispatch_glGetSamplerParameterfv1(n, n2, array, 4 * n3, false, addressof_glGetSamplerParameterfv);
    }
    
    @Override
    public void glVertexAttribDivisor(final int n, final int n2) {
        final long addressof_glVertexAttribDivisor = this._pat._addressof_glVertexAttribDivisor;
        if (addressof_glVertexAttribDivisor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribDivisor"));
        }
        this.dispatch_glVertexAttribDivisor1(n, n2, addressof_glVertexAttribDivisor);
    }
    
    private native void dispatch_glVertexAttribDivisor1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBindTransformFeedback(final int n, final int n2) {
        final long addressof_glBindTransformFeedback = this._pat._addressof_glBindTransformFeedback;
        if (addressof_glBindTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindTransformFeedback"));
        }
        this.dispatch_glBindTransformFeedback1(n, n2, addressof_glBindTransformFeedback);
    }
    
    private native void dispatch_glBindTransformFeedback1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteTransformFeedbacks = this._pat._addressof_glDeleteTransformFeedbacks;
        if (addressof_glDeleteTransformFeedbacks == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTransformFeedbacks"));
        }
        this.dispatch_glDeleteTransformFeedbacks1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteTransformFeedbacks);
    }
    
    private native void dispatch_glDeleteTransformFeedbacks1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"ids_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteTransformFeedbacks = this._pat._addressof_glDeleteTransformFeedbacks;
        if (addressof_glDeleteTransformFeedbacks == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteTransformFeedbacks"));
        }
        this.dispatch_glDeleteTransformFeedbacks1(n, array, 4 * n2, false, addressof_glDeleteTransformFeedbacks);
    }
    
    @Override
    public void glGenTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenTransformFeedbacks = this._pat._addressof_glGenTransformFeedbacks;
        if (addressof_glGenTransformFeedbacks == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTransformFeedbacks"));
        }
        this.dispatch_glGenTransformFeedbacks1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenTransformFeedbacks);
    }
    
    private native void dispatch_glGenTransformFeedbacks1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenTransformFeedbacks(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"ids_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenTransformFeedbacks = this._pat._addressof_glGenTransformFeedbacks;
        if (addressof_glGenTransformFeedbacks == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenTransformFeedbacks"));
        }
        this.dispatch_glGenTransformFeedbacks1(n, array, 4 * n2, false, addressof_glGenTransformFeedbacks);
    }
    
    @Override
    public boolean glIsTransformFeedback(final int n) {
        final long addressof_glIsTransformFeedback = this._pat._addressof_glIsTransformFeedback;
        if (addressof_glIsTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsTransformFeedback"));
        }
        return this.dispatch_glIsTransformFeedback1(n, addressof_glIsTransformFeedback);
    }
    
    private native boolean dispatch_glIsTransformFeedback1(final int p0, final long p1);
    
    @Override
    public void glPauseTransformFeedback() {
        final long addressof_glPauseTransformFeedback = this._pat._addressof_glPauseTransformFeedback;
        if (addressof_glPauseTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPauseTransformFeedback"));
        }
        this.dispatch_glPauseTransformFeedback1(addressof_glPauseTransformFeedback);
    }
    
    private native void dispatch_glPauseTransformFeedback1(final long p0);
    
    @Override
    public void glResumeTransformFeedback() {
        final long addressof_glResumeTransformFeedback = this._pat._addressof_glResumeTransformFeedback;
        if (addressof_glResumeTransformFeedback == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glResumeTransformFeedback"));
        }
        this.dispatch_glResumeTransformFeedback1(addressof_glResumeTransformFeedback);
    }
    
    private native void dispatch_glResumeTransformFeedback1(final long p0);
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(buffer);
        final long addressof_glGetProgramBinary = this._pat._addressof_glGetProgramBinary;
        if (addressof_glGetProgramBinary == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramBinary"));
        }
        this.dispatch_glGetProgramBinary1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? buffer : Buffers.getArray(buffer), direct3 ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct3, addressof_glGetProgramBinary);
    }
    
    private native void dispatch_glGetProgramBinary1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final Object p8, final int p9, final boolean p10, final long p11);
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final Buffer buffer) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"binaryFormat_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glGetProgramBinary = this._pat._addressof_glGetProgramBinary;
        if (addressof_glGetProgramBinary == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramBinary"));
        }
        this.dispatch_glGetProgramBinary1(n, n2, array, 4 * n3, false, array2, 4 * n4, false, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glGetProgramBinary);
    }
    
    @Override
    public void glProgramBinary(final int n, final int n2, final Buffer buffer, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glProgramBinary = this._pat._addressof_glProgramBinary;
        if (addressof_glProgramBinary == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramBinary"));
        }
        this.dispatch_glProgramBinary1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n3, addressof_glProgramBinary);
    }
    
    private native void dispatch_glProgramBinary1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final int p5, final long p6);
    
    @Override
    public void glProgramParameteri(final int n, final int n2, final int n3) {
        final long addressof_glProgramParameteri = this._pat._addressof_glProgramParameteri;
        if (addressof_glProgramParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramParameteri"));
        }
        this.dispatch_glProgramParameteri1(n, n2, n3, addressof_glProgramParameteri);
    }
    
    private native void dispatch_glProgramParameteri1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glInvalidateFramebuffer = this._pat._addressof_glInvalidateFramebuffer;
        if (addressof_glInvalidateFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glInvalidateFramebuffer"));
        }
        this.dispatch_glInvalidateFramebuffer1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glInvalidateFramebuffer);
    }
    
    private native void dispatch_glInvalidateFramebuffer1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"attachments_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glInvalidateFramebuffer = this._pat._addressof_glInvalidateFramebuffer;
        if (addressof_glInvalidateFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glInvalidateFramebuffer"));
        }
        this.dispatch_glInvalidateFramebuffer1(n, n2, array, 4 * n3, false, addressof_glInvalidateFramebuffer);
    }
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4, final int n5, final int n6) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glInvalidateSubFramebuffer = this._pat._addressof_glInvalidateSubFramebuffer;
        if (addressof_glInvalidateSubFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glInvalidateSubFramebuffer"));
        }
        this.dispatch_glInvalidateSubFramebuffer1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n3, n4, n5, n6, addressof_glInvalidateSubFramebuffer);
    }
    
    private native void dispatch_glInvalidateSubFramebuffer1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final int p5, final int p6, final int p7, final int p8, final long p9);
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final int[] array, final int n3, final int n4, final int n5, final int n6, final int n7) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"attachments_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glInvalidateSubFramebuffer = this._pat._addressof_glInvalidateSubFramebuffer;
        if (addressof_glInvalidateSubFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glInvalidateSubFramebuffer"));
        }
        this.dispatch_glInvalidateSubFramebuffer1(n, n2, array, 4 * n3, false, n4, n5, n6, n7, addressof_glInvalidateSubFramebuffer);
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glTexStorage2D = this._pat._addressof_glTexStorage2D;
        if (addressof_glTexStorage2D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage2D"));
        }
        this.dispatch_glTexStorage2D1(n, n2, n3, n4, n5, addressof_glTexStorage2D);
    }
    
    private native void dispatch_glTexStorage2D1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glTexStorage3D = this._pat._addressof_glTexStorage3D;
        if (addressof_glTexStorage3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage3D"));
        }
        this.dispatch_glTexStorage3D1(n, n2, n3, n4, n5, n6, addressof_glTexStorage3D);
    }
    
    private native void dispatch_glTexStorage3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetInternalformativ = this._pat._addressof_glGetInternalformativ;
        if (addressof_glGetInternalformativ == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInternalformativ"));
        }
        this.dispatch_glGetInternalformativ1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetInternalformativ);
    }
    
    private native void dispatch_glGetInternalformativ1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"params_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetInternalformativ = this._pat._addressof_glGetInternalformativ;
        if (addressof_glGetInternalformativ == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetInternalformativ"));
        }
        this.dispatch_glGetInternalformativ1(n, n2, n3, n4, array, 4 * n5, false, addressof_glGetInternalformativ);
    }
    
    @Override
    public void glDispatchCompute(final int n, final int n2, final int n3) {
        final long addressof_glDispatchCompute = this._pat._addressof_glDispatchCompute;
        if (addressof_glDispatchCompute == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDispatchCompute"));
        }
        this.dispatch_glDispatchCompute1(n, n2, n3, addressof_glDispatchCompute);
    }
    
    private native void dispatch_glDispatchCompute1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glDispatchComputeIndirect(final long n) {
        final long addressof_glDispatchComputeIndirect = this._pat._addressof_glDispatchComputeIndirect;
        if (addressof_glDispatchComputeIndirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDispatchComputeIndirect"));
        }
        this.dispatch_glDispatchComputeIndirect1(n, addressof_glDispatchComputeIndirect);
    }
    
    private native void dispatch_glDispatchComputeIndirect1(final long p0, final long p1);
    
    @Override
    public void glDrawArraysIndirect(final int n, final Buffer buffer) {
        this.checkIndirectVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawArraysIndirect = this._pat._addressof_glDrawArraysIndirect;
        if (addressof_glDrawArraysIndirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysIndirect"));
        }
        this.dispatch_glDrawArraysIndirect1(n, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glDrawArraysIndirect);
    }
    
    private native void dispatch_glDrawArraysIndirect1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDrawArraysIndirect(final int n, final long n2) {
        this.checkIndirectVBOBound(true);
        final long addressof_glDrawArraysIndirect = this._pat._addressof_glDrawArraysIndirect;
        if (addressof_glDrawArraysIndirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysIndirect"));
        }
        this.dispatch_glDrawArraysIndirect1(n, n2, addressof_glDrawArraysIndirect);
    }
    
    private native void dispatch_glDrawArraysIndirect1(final int p0, final long p1, final long p2);
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final Buffer buffer) {
        this.checkIndirectVBOUnbound(true);
        Buffers.rangeCheck(buffer, 1);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsIndirect = this._pat._addressof_glDrawElementsIndirect;
        if (addressof_glDrawElementsIndirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsIndirect"));
        }
        this.dispatch_glDrawElementsIndirect1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glDrawElementsIndirect);
    }
    
    private native void dispatch_glDrawElementsIndirect1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final long n3) {
        this.checkIndirectVBOBound(true);
        final long addressof_glDrawElementsIndirect = this._pat._addressof_glDrawElementsIndirect;
        if (addressof_glDrawElementsIndirect == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsIndirect"));
        }
        this.dispatch_glDrawElementsIndirect1(n, n2, n3, addressof_glDrawElementsIndirect);
    }
    
    private native void dispatch_glDrawElementsIndirect1(final int p0, final int p1, final long p2, final long p3);
    
    @Override
    public void glFramebufferParameteri(final int n, final int n2, final int n3) {
        final long addressof_glFramebufferParameteri = this._pat._addressof_glFramebufferParameteri;
        if (addressof_glFramebufferParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferParameteri"));
        }
        this.dispatch_glFramebufferParameteri1(n, n2, n3, addressof_glFramebufferParameteri);
    }
    
    private native void dispatch_glFramebufferParameteri1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetFramebufferParameteriv = this._pat._addressof_glGetFramebufferParameteriv;
        if (addressof_glGetFramebufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferParameteriv"));
        }
        this.dispatch_glGetFramebufferParameteriv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetFramebufferParameteriv);
    }
    
    private native void dispatch_glGetFramebufferParameteriv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFramebufferParameteriv = this._pat._addressof_glGetFramebufferParameteriv;
        if (addressof_glGetFramebufferParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFramebufferParameteriv"));
        }
        this.dispatch_glGetFramebufferParameteriv1(n, n2, array, 4 * n3, false, addressof_glGetFramebufferParameteriv);
    }
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetProgramInterfaceiv = this._pat._addressof_glGetProgramInterfaceiv;
        if (addressof_glGetProgramInterfaceiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramInterfaceiv"));
        }
        this.dispatch_glGetProgramInterfaceiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetProgramInterfaceiv);
    }
    
    private native void dispatch_glGetProgramInterfaceiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramInterfaceiv = this._pat._addressof_glGetProgramInterfaceiv;
        if (addressof_glGetProgramInterfaceiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramInterfaceiv"));
        }
        this.dispatch_glGetProgramInterfaceiv1(n, n2, n3, array, 4 * n4, false, addressof_glGetProgramInterfaceiv);
    }
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramResourceIndex = this._pat._addressof_glGetProgramResourceIndex;
        if (addressof_glGetProgramResourceIndex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceIndex"));
        }
        return this.dispatch_glGetProgramResourceIndex1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetProgramResourceIndex);
    }
    
    private native int dispatch_glGetProgramResourceIndex1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final byte[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"name_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramResourceIndex = this._pat._addressof_glGetProgramResourceIndex;
        if (addressof_glGetProgramResourceIndex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceIndex"));
        }
        return this.dispatch_glGetProgramResourceIndex1(n, n2, array, n3, false, addressof_glGetProgramResourceIndex);
    }
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramResourceName = this._pat._addressof_glGetProgramResourceName;
        if (addressof_glGetProgramResourceName == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceName"));
        }
        this.dispatch_glGetProgramResourceName1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetProgramResourceName);
    }
    
    private native void dispatch_glGetProgramResourceName1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final byte[] array2, final int n6) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"length_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n6) {
            throw new GLException("array offset argument \"name_offset\" (" + n6 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetProgramResourceName = this._pat._addressof_glGetProgramResourceName;
        if (addressof_glGetProgramResourceName == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceName"));
        }
        this.dispatch_glGetProgramResourceName1(n, n2, n3, n4, array, 4 * n5, false, array2, n6, false, addressof_glGetProgramResourceName);
    }
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final int n5, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(intBuffer3);
        final long addressof_glGetProgramResourceiv = this._pat._addressof_glGetProgramResourceiv;
        if (addressof_glGetProgramResourceiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceiv"));
        }
        this.dispatch_glGetProgramResourceiv1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n5, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? intBuffer3 : Buffers.getArray(intBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer3) : Buffers.getIndirectBufferByteOffset(intBuffer3), direct3, addressof_glGetProgramResourceiv);
    }
    
    private native void dispatch_glGetProgramResourceiv1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final int p7, final Object p8, final int p9, final boolean p10, final Object p11, final int p12, final boolean p13, final long p14);
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final int n6, final int[] array2, final int n7, final int[] array3, final int n8) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"props_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n7) {
            throw new GLException("array offset argument \"length_offset\" (" + n7 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n8) {
            throw new GLException("array offset argument \"params_offset\" (" + n8 + ") equals or exceeds array length (" + array3.length + ")");
        }
        final long addressof_glGetProgramResourceiv = this._pat._addressof_glGetProgramResourceiv;
        if (addressof_glGetProgramResourceiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceiv"));
        }
        this.dispatch_glGetProgramResourceiv1(n, n2, n3, n4, array, 4 * n5, false, n6, array2, 4 * n7, false, array3, 4 * n8, false, addressof_glGetProgramResourceiv);
    }
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramResourceLocation = this._pat._addressof_glGetProgramResourceLocation;
        if (addressof_glGetProgramResourceLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceLocation"));
        }
        return this.dispatch_glGetProgramResourceLocation1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetProgramResourceLocation);
    }
    
    private native int dispatch_glGetProgramResourceLocation1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final byte[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"name_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramResourceLocation = this._pat._addressof_glGetProgramResourceLocation;
        if (addressof_glGetProgramResourceLocation == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceLocation"));
        }
        return this.dispatch_glGetProgramResourceLocation1(n, n2, array, n3, false, addressof_glGetProgramResourceLocation);
    }
    
    @Override
    public void glUseProgramStages(final int n, final int n2, final int n3) {
        final long addressof_glUseProgramStages = this._pat._addressof_glUseProgramStages;
        if (addressof_glUseProgramStages == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUseProgramStages"));
        }
        this.dispatch_glUseProgramStages1(n, n2, n3, addressof_glUseProgramStages);
    }
    
    private native void dispatch_glUseProgramStages1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glActiveShaderProgram(final int n, final int n2) {
        final long addressof_glActiveShaderProgram = this._pat._addressof_glActiveShaderProgram;
        if (addressof_glActiveShaderProgram == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glActiveShaderProgram"));
        }
        this.dispatch_glActiveShaderProgram1(n, n2, addressof_glActiveShaderProgram);
    }
    
    private native void dispatch_glActiveShaderProgram1(final int p0, final int p1, final long p2);
    
    @Override
    public int glCreateShaderProgramv(final int n, final int n2, final String[] array) {
        final long addressof_glCreateShaderProgramv = this._pat._addressof_glCreateShaderProgramv;
        if (addressof_glCreateShaderProgramv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCreateShaderProgramv"));
        }
        return this.dispatch_glCreateShaderProgramv1(n, n2, array, addressof_glCreateShaderProgramv);
    }
    
    private native int dispatch_glCreateShaderProgramv1(final int p0, final int p1, final String[] p2, final long p3);
    
    @Override
    public void glBindProgramPipeline(final int n) {
        final long addressof_glBindProgramPipeline = this._pat._addressof_glBindProgramPipeline;
        if (addressof_glBindProgramPipeline == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindProgramPipeline"));
        }
        this.dispatch_glBindProgramPipeline1(n, addressof_glBindProgramPipeline);
    }
    
    private native void dispatch_glBindProgramPipeline1(final int p0, final long p1);
    
    @Override
    public void glDeleteProgramPipelines(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteProgramPipelines = this._pat._addressof_glDeleteProgramPipelines;
        if (addressof_glDeleteProgramPipelines == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteProgramPipelines"));
        }
        this.dispatch_glDeleteProgramPipelines1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteProgramPipelines);
    }
    
    private native void dispatch_glDeleteProgramPipelines1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteProgramPipelines(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"pipelines_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteProgramPipelines = this._pat._addressof_glDeleteProgramPipelines;
        if (addressof_glDeleteProgramPipelines == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteProgramPipelines"));
        }
        this.dispatch_glDeleteProgramPipelines1(n, array, 4 * n2, false, addressof_glDeleteProgramPipelines);
    }
    
    @Override
    public void glGenProgramPipelines(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenProgramPipelines = this._pat._addressof_glGenProgramPipelines;
        if (addressof_glGenProgramPipelines == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenProgramPipelines"));
        }
        this.dispatch_glGenProgramPipelines1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenProgramPipelines);
    }
    
    private native void dispatch_glGenProgramPipelines1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenProgramPipelines(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"pipelines_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenProgramPipelines = this._pat._addressof_glGenProgramPipelines;
        if (addressof_glGenProgramPipelines == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenProgramPipelines"));
        }
        this.dispatch_glGenProgramPipelines1(n, array, 4 * n2, false, addressof_glGenProgramPipelines);
    }
    
    @Override
    public boolean glIsProgramPipeline(final int n) {
        final long addressof_glIsProgramPipeline = this._pat._addressof_glIsProgramPipeline;
        if (addressof_glIsProgramPipeline == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsProgramPipeline"));
        }
        return this.dispatch_glIsProgramPipeline1(n, addressof_glIsProgramPipeline);
    }
    
    private native boolean dispatch_glIsProgramPipeline1(final int p0, final long p1);
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetProgramPipelineiv = this._pat._addressof_glGetProgramPipelineiv;
        if (addressof_glGetProgramPipelineiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramPipelineiv"));
        }
        this.dispatch_glGetProgramPipelineiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetProgramPipelineiv);
    }
    
    private native void dispatch_glGetProgramPipelineiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramPipelineiv = this._pat._addressof_glGetProgramPipelineiv;
        if (addressof_glGetProgramPipelineiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramPipelineiv"));
        }
        this.dispatch_glGetProgramPipelineiv1(n, n2, array, 4 * n3, false, addressof_glGetProgramPipelineiv);
    }
    
    @Override
    public void glProgramUniform1i(final int n, final int n2, final int n3) {
        final long addressof_glProgramUniform1i = this._pat._addressof_glProgramUniform1i;
        if (addressof_glProgramUniform1i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1i"));
        }
        this.dispatch_glProgramUniform1i1(n, n2, n3, addressof_glProgramUniform1i);
    }
    
    private native void dispatch_glProgramUniform1i1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glProgramUniform2i(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glProgramUniform2i = this._pat._addressof_glProgramUniform2i;
        if (addressof_glProgramUniform2i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2i"));
        }
        this.dispatch_glProgramUniform2i1(n, n2, n3, n4, addressof_glProgramUniform2i);
    }
    
    private native void dispatch_glProgramUniform2i1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glProgramUniform3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glProgramUniform3i = this._pat._addressof_glProgramUniform3i;
        if (addressof_glProgramUniform3i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3i"));
        }
        this.dispatch_glProgramUniform3i1(n, n2, n3, n4, n5, addressof_glProgramUniform3i);
    }
    
    private native void dispatch_glProgramUniform3i1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glProgramUniform4i(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glProgramUniform4i = this._pat._addressof_glProgramUniform4i;
        if (addressof_glProgramUniform4i == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4i"));
        }
        this.dispatch_glProgramUniform4i1(n, n2, n3, n4, n5, n6, addressof_glProgramUniform4i);
    }
    
    private native void dispatch_glProgramUniform4i1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glProgramUniform1ui(final int n, final int n2, final int n3) {
        final long addressof_glProgramUniform1ui = this._pat._addressof_glProgramUniform1ui;
        if (addressof_glProgramUniform1ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1ui"));
        }
        this.dispatch_glProgramUniform1ui1(n, n2, n3, addressof_glProgramUniform1ui);
    }
    
    private native void dispatch_glProgramUniform1ui1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glProgramUniform2ui(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glProgramUniform2ui = this._pat._addressof_glProgramUniform2ui;
        if (addressof_glProgramUniform2ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2ui"));
        }
        this.dispatch_glProgramUniform2ui1(n, n2, n3, n4, addressof_glProgramUniform2ui);
    }
    
    private native void dispatch_glProgramUniform2ui1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glProgramUniform3ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glProgramUniform3ui = this._pat._addressof_glProgramUniform3ui;
        if (addressof_glProgramUniform3ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3ui"));
        }
        this.dispatch_glProgramUniform3ui1(n, n2, n3, n4, n5, addressof_glProgramUniform3ui);
    }
    
    private native void dispatch_glProgramUniform3ui1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glProgramUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glProgramUniform4ui = this._pat._addressof_glProgramUniform4ui;
        if (addressof_glProgramUniform4ui == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4ui"));
        }
        this.dispatch_glProgramUniform4ui1(n, n2, n3, n4, n5, n6, addressof_glProgramUniform4ui);
    }
    
    private native void dispatch_glProgramUniform4ui1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glProgramUniform1f(final int n, final int n2, final float n3) {
        final long addressof_glProgramUniform1f = this._pat._addressof_glProgramUniform1f;
        if (addressof_glProgramUniform1f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1f"));
        }
        this.dispatch_glProgramUniform1f1(n, n2, n3, addressof_glProgramUniform1f);
    }
    
    private native void dispatch_glProgramUniform1f1(final int p0, final int p1, final float p2, final long p3);
    
    @Override
    public void glProgramUniform2f(final int n, final int n2, final float n3, final float n4) {
        final long addressof_glProgramUniform2f = this._pat._addressof_glProgramUniform2f;
        if (addressof_glProgramUniform2f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2f"));
        }
        this.dispatch_glProgramUniform2f1(n, n2, n3, n4, addressof_glProgramUniform2f);
    }
    
    private native void dispatch_glProgramUniform2f1(final int p0, final int p1, final float p2, final float p3, final long p4);
    
    @Override
    public void glProgramUniform3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        final long addressof_glProgramUniform3f = this._pat._addressof_glProgramUniform3f;
        if (addressof_glProgramUniform3f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3f"));
        }
        this.dispatch_glProgramUniform3f1(n, n2, n3, n4, n5, addressof_glProgramUniform3f);
    }
    
    private native void dispatch_glProgramUniform3f1(final int p0, final int p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glProgramUniform4f(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        final long addressof_glProgramUniform4f = this._pat._addressof_glProgramUniform4f;
        if (addressof_glProgramUniform4f == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4f"));
        }
        this.dispatch_glProgramUniform4f1(n, n2, n3, n4, n5, n6, addressof_glProgramUniform4f);
    }
    
    private native void dispatch_glProgramUniform4f1(final int p0, final int p1, final float p2, final float p3, final float p4, final float p5, final long p6);
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform1iv = this._pat._addressof_glProgramUniform1iv;
        if (addressof_glProgramUniform1iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1iv"));
        }
        this.dispatch_glProgramUniform1iv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform1iv);
    }
    
    private native void dispatch_glProgramUniform1iv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform1iv = this._pat._addressof_glProgramUniform1iv;
        if (addressof_glProgramUniform1iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1iv"));
        }
        this.dispatch_glProgramUniform1iv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform1iv);
    }
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform2iv = this._pat._addressof_glProgramUniform2iv;
        if (addressof_glProgramUniform2iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2iv"));
        }
        this.dispatch_glProgramUniform2iv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform2iv);
    }
    
    private native void dispatch_glProgramUniform2iv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform2iv = this._pat._addressof_glProgramUniform2iv;
        if (addressof_glProgramUniform2iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2iv"));
        }
        this.dispatch_glProgramUniform2iv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform2iv);
    }
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform3iv = this._pat._addressof_glProgramUniform3iv;
        if (addressof_glProgramUniform3iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3iv"));
        }
        this.dispatch_glProgramUniform3iv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform3iv);
    }
    
    private native void dispatch_glProgramUniform3iv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform3iv = this._pat._addressof_glProgramUniform3iv;
        if (addressof_glProgramUniform3iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3iv"));
        }
        this.dispatch_glProgramUniform3iv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform3iv);
    }
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform4iv = this._pat._addressof_glProgramUniform4iv;
        if (addressof_glProgramUniform4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4iv"));
        }
        this.dispatch_glProgramUniform4iv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform4iv);
    }
    
    private native void dispatch_glProgramUniform4iv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform4iv = this._pat._addressof_glProgramUniform4iv;
        if (addressof_glProgramUniform4iv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4iv"));
        }
        this.dispatch_glProgramUniform4iv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform4iv);
    }
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform1uiv = this._pat._addressof_glProgramUniform1uiv;
        if (addressof_glProgramUniform1uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1uiv"));
        }
        this.dispatch_glProgramUniform1uiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform1uiv);
    }
    
    private native void dispatch_glProgramUniform1uiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform1uiv = this._pat._addressof_glProgramUniform1uiv;
        if (addressof_glProgramUniform1uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1uiv"));
        }
        this.dispatch_glProgramUniform1uiv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform1uiv);
    }
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform2uiv = this._pat._addressof_glProgramUniform2uiv;
        if (addressof_glProgramUniform2uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2uiv"));
        }
        this.dispatch_glProgramUniform2uiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform2uiv);
    }
    
    private native void dispatch_glProgramUniform2uiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform2uiv = this._pat._addressof_glProgramUniform2uiv;
        if (addressof_glProgramUniform2uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2uiv"));
        }
        this.dispatch_glProgramUniform2uiv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform2uiv);
    }
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform3uiv = this._pat._addressof_glProgramUniform3uiv;
        if (addressof_glProgramUniform3uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3uiv"));
        }
        this.dispatch_glProgramUniform3uiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform3uiv);
    }
    
    private native void dispatch_glProgramUniform3uiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform3uiv = this._pat._addressof_glProgramUniform3uiv;
        if (addressof_glProgramUniform3uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3uiv"));
        }
        this.dispatch_glProgramUniform3uiv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform3uiv);
    }
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glProgramUniform4uiv = this._pat._addressof_glProgramUniform4uiv;
        if (addressof_glProgramUniform4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4uiv"));
        }
        this.dispatch_glProgramUniform4uiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glProgramUniform4uiv);
    }
    
    private native void dispatch_glProgramUniform4uiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform4uiv = this._pat._addressof_glProgramUniform4uiv;
        if (addressof_glProgramUniform4uiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4uiv"));
        }
        this.dispatch_glProgramUniform4uiv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform4uiv);
    }
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniform1fv = this._pat._addressof_glProgramUniform1fv;
        if (addressof_glProgramUniform1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1fv"));
        }
        this.dispatch_glProgramUniform1fv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniform1fv);
    }
    
    private native void dispatch_glProgramUniform1fv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform1fv = this._pat._addressof_glProgramUniform1fv;
        if (addressof_glProgramUniform1fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform1fv"));
        }
        this.dispatch_glProgramUniform1fv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform1fv);
    }
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniform2fv = this._pat._addressof_glProgramUniform2fv;
        if (addressof_glProgramUniform2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2fv"));
        }
        this.dispatch_glProgramUniform2fv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniform2fv);
    }
    
    private native void dispatch_glProgramUniform2fv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform2fv = this._pat._addressof_glProgramUniform2fv;
        if (addressof_glProgramUniform2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform2fv"));
        }
        this.dispatch_glProgramUniform2fv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform2fv);
    }
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniform3fv = this._pat._addressof_glProgramUniform3fv;
        if (addressof_glProgramUniform3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3fv"));
        }
        this.dispatch_glProgramUniform3fv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniform3fv);
    }
    
    private native void dispatch_glProgramUniform3fv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform3fv = this._pat._addressof_glProgramUniform3fv;
        if (addressof_glProgramUniform3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform3fv"));
        }
        this.dispatch_glProgramUniform3fv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform3fv);
    }
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniform4fv = this._pat._addressof_glProgramUniform4fv;
        if (addressof_glProgramUniform4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4fv"));
        }
        this.dispatch_glProgramUniform4fv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniform4fv);
    }
    
    private native void dispatch_glProgramUniform4fv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniform4fv = this._pat._addressof_glProgramUniform4fv;
        if (addressof_glProgramUniform4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniform4fv"));
        }
        this.dispatch_glProgramUniform4fv1(n, n2, n3, array, 4 * n4, false, addressof_glProgramUniform4fv);
    }
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix2fv = this._pat._addressof_glProgramUniformMatrix2fv;
        if (addressof_glProgramUniformMatrix2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2fv"));
        }
        this.dispatch_glProgramUniformMatrix2fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix2fv);
    }
    
    private native void dispatch_glProgramUniformMatrix2fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix2fv = this._pat._addressof_glProgramUniformMatrix2fv;
        if (addressof_glProgramUniformMatrix2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2fv"));
        }
        this.dispatch_glProgramUniformMatrix2fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix2fv);
    }
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix3fv = this._pat._addressof_glProgramUniformMatrix3fv;
        if (addressof_glProgramUniformMatrix3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3fv"));
        }
        this.dispatch_glProgramUniformMatrix3fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix3fv);
    }
    
    private native void dispatch_glProgramUniformMatrix3fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix3fv = this._pat._addressof_glProgramUniformMatrix3fv;
        if (addressof_glProgramUniformMatrix3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3fv"));
        }
        this.dispatch_glProgramUniformMatrix3fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix3fv);
    }
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix4fv = this._pat._addressof_glProgramUniformMatrix4fv;
        if (addressof_glProgramUniformMatrix4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4fv"));
        }
        this.dispatch_glProgramUniformMatrix4fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix4fv);
    }
    
    private native void dispatch_glProgramUniformMatrix4fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix4fv = this._pat._addressof_glProgramUniformMatrix4fv;
        if (addressof_glProgramUniformMatrix4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4fv"));
        }
        this.dispatch_glProgramUniformMatrix4fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix4fv);
    }
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix2x3fv = this._pat._addressof_glProgramUniformMatrix2x3fv;
        if (addressof_glProgramUniformMatrix2x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2x3fv"));
        }
        this.dispatch_glProgramUniformMatrix2x3fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix2x3fv);
    }
    
    private native void dispatch_glProgramUniformMatrix2x3fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix2x3fv = this._pat._addressof_glProgramUniformMatrix2x3fv;
        if (addressof_glProgramUniformMatrix2x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2x3fv"));
        }
        this.dispatch_glProgramUniformMatrix2x3fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix2x3fv);
    }
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix3x2fv = this._pat._addressof_glProgramUniformMatrix3x2fv;
        if (addressof_glProgramUniformMatrix3x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3x2fv"));
        }
        this.dispatch_glProgramUniformMatrix3x2fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix3x2fv);
    }
    
    private native void dispatch_glProgramUniformMatrix3x2fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix3x2fv = this._pat._addressof_glProgramUniformMatrix3x2fv;
        if (addressof_glProgramUniformMatrix3x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3x2fv"));
        }
        this.dispatch_glProgramUniformMatrix3x2fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix3x2fv);
    }
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix2x4fv = this._pat._addressof_glProgramUniformMatrix2x4fv;
        if (addressof_glProgramUniformMatrix2x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2x4fv"));
        }
        this.dispatch_glProgramUniformMatrix2x4fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix2x4fv);
    }
    
    private native void dispatch_glProgramUniformMatrix2x4fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix2x4fv = this._pat._addressof_glProgramUniformMatrix2x4fv;
        if (addressof_glProgramUniformMatrix2x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix2x4fv"));
        }
        this.dispatch_glProgramUniformMatrix2x4fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix2x4fv);
    }
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix4x2fv = this._pat._addressof_glProgramUniformMatrix4x2fv;
        if (addressof_glProgramUniformMatrix4x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4x2fv"));
        }
        this.dispatch_glProgramUniformMatrix4x2fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix4x2fv);
    }
    
    private native void dispatch_glProgramUniformMatrix4x2fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix4x2fv = this._pat._addressof_glProgramUniformMatrix4x2fv;
        if (addressof_glProgramUniformMatrix4x2fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4x2fv"));
        }
        this.dispatch_glProgramUniformMatrix4x2fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix4x2fv);
    }
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix3x4fv = this._pat._addressof_glProgramUniformMatrix3x4fv;
        if (addressof_glProgramUniformMatrix3x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3x4fv"));
        }
        this.dispatch_glProgramUniformMatrix3x4fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix3x4fv);
    }
    
    private native void dispatch_glProgramUniformMatrix3x4fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix3x4fv = this._pat._addressof_glProgramUniformMatrix3x4fv;
        if (addressof_glProgramUniformMatrix3x4fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix3x4fv"));
        }
        this.dispatch_glProgramUniformMatrix3x4fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix3x4fv);
    }
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glProgramUniformMatrix4x3fv = this._pat._addressof_glProgramUniformMatrix4x3fv;
        if (addressof_glProgramUniformMatrix4x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4x3fv"));
        }
        this.dispatch_glProgramUniformMatrix4x3fv1(n, n2, n3, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glProgramUniformMatrix4x3fv);
    }
    
    private native void dispatch_glProgramUniformMatrix4x3fv1(final int p0, final int p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"value_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glProgramUniformMatrix4x3fv = this._pat._addressof_glProgramUniformMatrix4x3fv;
        if (addressof_glProgramUniformMatrix4x3fv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glProgramUniformMatrix4x3fv"));
        }
        this.dispatch_glProgramUniformMatrix4x3fv1(n, n2, n3, b, array, 4 * n4, false, addressof_glProgramUniformMatrix4x3fv);
    }
    
    @Override
    public void glValidateProgramPipeline(final int n) {
        final long addressof_glValidateProgramPipeline = this._pat._addressof_glValidateProgramPipeline;
        if (addressof_glValidateProgramPipeline == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glValidateProgramPipeline"));
        }
        this.dispatch_glValidateProgramPipeline1(n, addressof_glValidateProgramPipeline);
    }
    
    private native void dispatch_glValidateProgramPipeline1(final int p0, final long p1);
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramPipelineInfoLog = this._pat._addressof_glGetProgramPipelineInfoLog;
        if (addressof_glGetProgramPipelineInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramPipelineInfoLog"));
        }
        this.dispatch_glGetProgramPipelineInfoLog1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetProgramPipelineInfoLog);
    }
    
    private native void dispatch_glGetProgramPipelineInfoLog1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"infoLog_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetProgramPipelineInfoLog = this._pat._addressof_glGetProgramPipelineInfoLog;
        if (addressof_glGetProgramPipelineInfoLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramPipelineInfoLog"));
        }
        this.dispatch_glGetProgramPipelineInfoLog1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetProgramPipelineInfoLog);
    }
    
    @Override
    public void glBindImageTexture(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6) {
        final long addressof_glBindImageTexture = this._pat._addressof_glBindImageTexture;
        if (addressof_glBindImageTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindImageTexture"));
        }
        this.dispatch_glBindImageTexture1(n, n2, n3, b, n4, n5, n6, addressof_glBindImageTexture);
    }
    
    private native void dispatch_glBindImageTexture1(final int p0, final int p1, final int p2, final boolean p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetBooleani_v = this._pat._addressof_glGetBooleani_v;
        if (addressof_glGetBooleani_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleani_v"));
        }
        this.dispatch_glGetBooleani_v1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetBooleani_v);
    }
    
    private native void dispatch_glGetBooleani_v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final byte[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"data_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetBooleani_v = this._pat._addressof_glGetBooleani_v;
        if (addressof_glGetBooleani_v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetBooleani_v"));
        }
        this.dispatch_glGetBooleani_v1(n, n2, array, n3, false, addressof_glGetBooleani_v);
    }
    
    @Override
    public void glMemoryBarrier(final int n) {
        final long addressof_glMemoryBarrier = this._pat._addressof_glMemoryBarrier;
        if (addressof_glMemoryBarrier == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMemoryBarrier"));
        }
        this.dispatch_glMemoryBarrier1(n, addressof_glMemoryBarrier);
    }
    
    private native void dispatch_glMemoryBarrier1(final int p0, final long p1);
    
    @Override
    public void glMemoryBarrierByRegion(final int n) {
        final long addressof_glMemoryBarrierByRegion = this._pat._addressof_glMemoryBarrierByRegion;
        if (addressof_glMemoryBarrierByRegion == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMemoryBarrierByRegion"));
        }
        this.dispatch_glMemoryBarrierByRegion1(n, addressof_glMemoryBarrierByRegion);
    }
    
    private native void dispatch_glMemoryBarrierByRegion1(final int p0, final long p1);
    
    @Override
    public void glTexStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final long addressof_glTexStorage2DMultisample = this._pat._addressof_glTexStorage2DMultisample;
        if (addressof_glTexStorage2DMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage2DMultisample"));
        }
        this.dispatch_glTexStorage2DMultisample1(n, n2, n3, n4, n5, b, addressof_glTexStorage2DMultisample);
    }
    
    private native void dispatch_glTexStorage2DMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetMultisamplefv = this._pat._addressof_glGetMultisamplefv;
        if (addressof_glGetMultisamplefv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMultisamplefv"));
        }
        this.dispatch_glGetMultisamplefv1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetMultisamplefv);
    }
    
    private native void dispatch_glGetMultisamplefv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"val_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetMultisamplefv = this._pat._addressof_glGetMultisamplefv;
        if (addressof_glGetMultisamplefv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetMultisamplefv"));
        }
        this.dispatch_glGetMultisamplefv1(n, n2, array, 4 * n3, false, addressof_glGetMultisamplefv);
    }
    
    @Override
    public void glSampleMaski(final int n, final int n2) {
        final long addressof_glSampleMaski = this._pat._addressof_glSampleMaski;
        if (addressof_glSampleMaski == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSampleMaski"));
        }
        this.dispatch_glSampleMaski1(n, n2, addressof_glSampleMaski);
    }
    
    private native void dispatch_glSampleMaski1(final int p0, final int p1, final long p2);
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexLevelParameteriv = this._pat._addressof_glGetTexLevelParameteriv;
        if (addressof_glGetTexLevelParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexLevelParameteriv"));
        }
        this.dispatch_glGetTexLevelParameteriv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexLevelParameteriv);
    }
    
    private native void dispatch_glGetTexLevelParameteriv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexLevelParameteriv = this._pat._addressof_glGetTexLevelParameteriv;
        if (addressof_glGetTexLevelParameteriv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexLevelParameteriv"));
        }
        this.dispatch_glGetTexLevelParameteriv1(n, n2, n3, array, 4 * n4, false, addressof_glGetTexLevelParameteriv);
    }
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetTexLevelParameterfv = this._pat._addressof_glGetTexLevelParameterfv;
        if (addressof_glGetTexLevelParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexLevelParameterfv"));
        }
        this.dispatch_glGetTexLevelParameterfv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetTexLevelParameterfv);
    }
    
    private native void dispatch_glGetTexLevelParameterfv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexLevelParameterfv = this._pat._addressof_glGetTexLevelParameterfv;
        if (addressof_glGetTexLevelParameterfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexLevelParameterfv"));
        }
        this.dispatch_glGetTexLevelParameterfv1(n, n2, n3, array, 4 * n4, false, addressof_glGetTexLevelParameterfv);
    }
    
    @Override
    public void glBindVertexBuffer(final int n, final int n2, final long n3, final int n4) {
        final long addressof_glBindVertexBuffer = this._pat._addressof_glBindVertexBuffer;
        if (addressof_glBindVertexBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindVertexBuffer"));
        }
        this.dispatch_glBindVertexBuffer1(n, n2, n3, n4, addressof_glBindVertexBuffer);
    }
    
    private native void dispatch_glBindVertexBuffer1(final int p0, final int p1, final long p2, final int p3, final long p4);
    
    @Override
    public void glVertexAttribFormat(final int n, final int n2, final int n3, final boolean b, final int n4) {
        final long addressof_glVertexAttribFormat = this._pat._addressof_glVertexAttribFormat;
        if (addressof_glVertexAttribFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribFormat"));
        }
        this.dispatch_glVertexAttribFormat1(n, n2, n3, b, n4, addressof_glVertexAttribFormat);
    }
    
    private native void dispatch_glVertexAttribFormat1(final int p0, final int p1, final int p2, final boolean p3, final int p4, final long p5);
    
    @Override
    public void glVertexAttribIFormat(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glVertexAttribIFormat = this._pat._addressof_glVertexAttribIFormat;
        if (addressof_glVertexAttribIFormat == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribIFormat"));
        }
        this.dispatch_glVertexAttribIFormat1(n, n2, n3, n4, addressof_glVertexAttribIFormat);
    }
    
    private native void dispatch_glVertexAttribIFormat1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glVertexAttribBinding(final int n, final int n2) {
        final long addressof_glVertexAttribBinding = this._pat._addressof_glVertexAttribBinding;
        if (addressof_glVertexAttribBinding == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribBinding"));
        }
        this.dispatch_glVertexAttribBinding1(n, n2, addressof_glVertexAttribBinding);
    }
    
    private native void dispatch_glVertexAttribBinding1(final int p0, final int p1, final long p2);
    
    @Override
    public void glVertexBindingDivisor(final int n, final int n2) {
        final long addressof_glVertexBindingDivisor = this._pat._addressof_glVertexBindingDivisor;
        if (addressof_glVertexBindingDivisor == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexBindingDivisor"));
        }
        this.dispatch_glVertexBindingDivisor1(n, n2, addressof_glVertexBindingDivisor);
    }
    
    private native void dispatch_glVertexBindingDivisor1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendBarrier() {
        final long addressof_glBlendBarrier = this._pat._addressof_glBlendBarrier;
        if (addressof_glBlendBarrier == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendBarrier"));
        }
        this.dispatch_glBlendBarrier1(addressof_glBlendBarrier);
    }
    
    private native void dispatch_glBlendBarrier1(final long p0);
    
    @Override
    public void glCopyImageSubData(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15) {
        final long addressof_glCopyImageSubData = this._pat._addressof_glCopyImageSubData;
        if (addressof_glCopyImageSubData == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyImageSubData"));
        }
        this.dispatch_glCopyImageSubData1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, addressof_glCopyImageSubData);
    }
    
    private native void dispatch_glCopyImageSubData1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10, final int p11, final int p12, final int p13, final int p14, final long p15);
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final boolean b) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDebugMessageControl = this._pat._addressof_glDebugMessageControl;
        if (addressof_glDebugMessageControl == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDebugMessageControl"));
        }
        this.dispatch_glDebugMessageControl1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, b, addressof_glDebugMessageControl);
    }
    
    private native void dispatch_glDebugMessageControl1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final boolean p7, final long p8);
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final boolean b) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"ids_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDebugMessageControl = this._pat._addressof_glDebugMessageControl;
        if (addressof_glDebugMessageControl == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDebugMessageControl"));
        }
        this.dispatch_glDebugMessageControl1(n, n2, n3, n4, array, 4 * n5, false, b, addressof_glDebugMessageControl);
    }
    
    @Override
    public void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        final long addressof_glDebugMessageInsert = this._pat._addressof_glDebugMessageInsert;
        if (addressof_glDebugMessageInsert == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDebugMessageInsert"));
        }
        this.dispatch_glDebugMessageInsert1(n, n2, n3, n4, n5, s, addressof_glDebugMessageInsert);
    }
    
    private native void dispatch_glDebugMessageInsert1(final int p0, final int p1, final int p2, final int p3, final int p4, final String p5, final long p6);
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final boolean direct3 = Buffers.isDirect(intBuffer3);
        final boolean direct4 = Buffers.isDirect(intBuffer4);
        final boolean direct5 = Buffers.isDirect(intBuffer5);
        final boolean direct6 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetDebugMessageLog = this._pat._addressof_glGetDebugMessageLog;
        if (addressof_glGetDebugMessageLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDebugMessageLog"));
        }
        return this.dispatch_glGetDebugMessageLog1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, direct3 ? intBuffer3 : Buffers.getArray(intBuffer3), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer3) : Buffers.getIndirectBufferByteOffset(intBuffer3), direct3, direct4 ? intBuffer4 : Buffers.getArray(intBuffer4), direct4 ? Buffers.getDirectBufferByteOffset(intBuffer4) : Buffers.getIndirectBufferByteOffset(intBuffer4), direct4, direct5 ? intBuffer5 : Buffers.getArray(intBuffer5), direct5 ? Buffers.getDirectBufferByteOffset(intBuffer5) : Buffers.getIndirectBufferByteOffset(intBuffer5), direct5, direct6 ? byteBuffer : Buffers.getArray(byteBuffer), direct6 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct6, addressof_glGetDebugMessageLog);
    }
    
    private native int dispatch_glGetDebugMessageLog1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final Object p8, final int p9, final boolean p10, final Object p11, final int p12, final boolean p13, final Object p14, final int p15, final boolean p16, final Object p17, final int p18, final boolean p19, final long p20);
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final int[] array3, final int n5, final int[] array4, final int n6, final int[] array5, final int n7, final byte[] array6, final int n8) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"sources_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"types_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        if (array3 != null && array3.length <= n5) {
            throw new GLException("array offset argument \"ids_offset\" (" + n5 + ") equals or exceeds array length (" + array3.length + ")");
        }
        if (array4 != null && array4.length <= n6) {
            throw new GLException("array offset argument \"severities_offset\" (" + n6 + ") equals or exceeds array length (" + array4.length + ")");
        }
        if (array5 != null && array5.length <= n7) {
            throw new GLException("array offset argument \"lengths_offset\" (" + n7 + ") equals or exceeds array length (" + array5.length + ")");
        }
        if (array6 != null && array6.length <= n8) {
            throw new GLException("array offset argument \"messageLog_offset\" (" + n8 + ") equals or exceeds array length (" + array6.length + ")");
        }
        final long addressof_glGetDebugMessageLog = this._pat._addressof_glGetDebugMessageLog;
        if (addressof_glGetDebugMessageLog == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDebugMessageLog"));
        }
        return this.dispatch_glGetDebugMessageLog1(n, n2, array, 4 * n3, false, array2, 4 * n4, false, array3, 4 * n5, false, array4, 4 * n6, false, array5, 4 * n7, false, array6, n8, false, addressof_glGetDebugMessageLog);
    }
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glPushDebugGroup = this._pat._addressof_glPushDebugGroup;
        if (addressof_glPushDebugGroup == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPushDebugGroup"));
        }
        this.dispatch_glPushDebugGroup1(n, n2, n3, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glPushDebugGroup);
    }
    
    private native void dispatch_glPushDebugGroup1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"message_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glPushDebugGroup = this._pat._addressof_glPushDebugGroup;
        if (addressof_glPushDebugGroup == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPushDebugGroup"));
        }
        this.dispatch_glPushDebugGroup1(n, n2, n3, array, n4, false, addressof_glPushDebugGroup);
    }
    
    @Override
    public void glPopDebugGroup() {
        final long addressof_glPopDebugGroup = this._pat._addressof_glPopDebugGroup;
        if (addressof_glPopDebugGroup == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPopDebugGroup"));
        }
        this.dispatch_glPopDebugGroup1(addressof_glPopDebugGroup);
    }
    
    private native void dispatch_glPopDebugGroup1(final long p0);
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glObjectLabel = this._pat._addressof_glObjectLabel;
        if (addressof_glObjectLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glObjectLabel"));
        }
        this.dispatch_glObjectLabel1(n, n2, n3, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glObjectLabel);
    }
    
    private native void dispatch_glObjectLabel1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"label_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glObjectLabel = this._pat._addressof_glObjectLabel;
        if (addressof_glObjectLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glObjectLabel"));
        }
        this.dispatch_glObjectLabel1(n, n2, n3, array, n4, false, addressof_glObjectLabel);
    }
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetObjectLabel = this._pat._addressof_glGetObjectLabel;
        if (addressof_glGetObjectLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetObjectLabel"));
        }
        this.dispatch_glGetObjectLabel1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetObjectLabel);
    }
    
    private native void dispatch_glGetObjectLabel1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final Object p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"label_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetObjectLabel = this._pat._addressof_glGetObjectLabel;
        if (addressof_glGetObjectLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetObjectLabel"));
        }
        this.dispatch_glGetObjectLabel1(n, n2, n3, array, 4 * n4, false, array2, n5, false, addressof_glGetObjectLabel);
    }
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glObjectPtrLabel = this._pat._addressof_glObjectPtrLabel;
        if (addressof_glObjectPtrLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glObjectPtrLabel"));
        }
        this.dispatch_glObjectPtrLabel1(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glObjectPtrLabel);
    }
    
    private native void dispatch_glObjectPtrLabel1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final byte[] array, final int n2) {
        final boolean direct = Buffers.isDirect(buffer);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"label_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glObjectPtrLabel = this._pat._addressof_glObjectPtrLabel;
        if (addressof_glObjectPtrLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glObjectPtrLabel"));
        }
        this.dispatch_glObjectPtrLabel1(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n, array, n2, false, addressof_glObjectPtrLabel);
    }
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final boolean direct2 = Buffers.isDirect(intBuffer);
        final boolean direct3 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetObjectPtrLabel = this._pat._addressof_glGetObjectPtrLabel;
        if (addressof_glGetObjectPtrLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetObjectPtrLabel"));
        }
        this.dispatch_glGetObjectPtrLabel1(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n, direct2 ? intBuffer : Buffers.getArray(intBuffer), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct2, direct3 ? byteBuffer : Buffers.getArray(byteBuffer), direct3 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct3, addressof_glGetObjectPtrLabel);
    }
    
    private native void dispatch_glGetObjectPtrLabel1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final int[] array, final int n2, final byte[] array2, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"length_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"label_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetObjectPtrLabel = this._pat._addressof_glGetObjectPtrLabel;
        if (addressof_glGetObjectPtrLabel == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetObjectPtrLabel"));
        }
        this.dispatch_glGetObjectPtrLabel1(direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n, array, 4 * n2, false, array2, n3, false, addressof_glGetObjectPtrLabel);
    }
    
    @Override
    public void glEnablei(final int n, final int n2) {
        final long addressof_glEnablei = this._pat._addressof_glEnablei;
        if (addressof_glEnablei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnablei"));
        }
        this.dispatch_glEnablei1(n, n2, addressof_glEnablei);
    }
    
    private native void dispatch_glEnablei1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisablei(final int n, final int n2) {
        final long addressof_glDisablei = this._pat._addressof_glDisablei;
        if (addressof_glDisablei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisablei"));
        }
        this.dispatch_glDisablei1(n, n2, addressof_glDisablei);
    }
    
    private native void dispatch_glDisablei1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationi(final int n, final int n2) {
        final long addressof_glBlendEquationi = this._pat._addressof_glBlendEquationi;
        if (addressof_glBlendEquationi == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationi"));
        }
        this.dispatch_glBlendEquationi1(n, n2, addressof_glBlendEquationi);
    }
    
    private native void dispatch_glBlendEquationi1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationSeparatei(final int n, final int n2, final int n3) {
        final long addressof_glBlendEquationSeparatei = this._pat._addressof_glBlendEquationSeparatei;
        if (addressof_glBlendEquationSeparatei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationSeparatei"));
        }
        this.dispatch_glBlendEquationSeparatei1(n, n2, n3, addressof_glBlendEquationSeparatei);
    }
    
    private native void dispatch_glBlendEquationSeparatei1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFunci(final int n, final int n2, final int n3) {
        final long addressof_glBlendFunci = this._pat._addressof_glBlendFunci;
        if (addressof_glBlendFunci == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFunci"));
        }
        this.dispatch_glBlendFunci1(n, n2, n3, addressof_glBlendFunci);
    }
    
    private native void dispatch_glBlendFunci1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFuncSeparatei(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glBlendFuncSeparatei = this._pat._addressof_glBlendFuncSeparatei;
        if (addressof_glBlendFuncSeparatei == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFuncSeparatei"));
        }
        this.dispatch_glBlendFuncSeparatei1(n, n2, n3, n4, n5, addressof_glBlendFuncSeparatei);
    }
    
    private native void dispatch_glBlendFuncSeparatei1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glColorMaski(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long addressof_glColorMaski = this._pat._addressof_glColorMaski;
        if (addressof_glColorMaski == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorMaski"));
        }
        this.dispatch_glColorMaski1(n, b, b2, b3, b4, addressof_glColorMaski);
    }
    
    private native void dispatch_glColorMaski1(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4, final long p5);
    
    @Override
    public boolean glIsEnabledi(final int n, final int n2) {
        final long addressof_glIsEnabledi = this._pat._addressof_glIsEnabledi;
        if (addressof_glIsEnabledi == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnabledi"));
        }
        return this.dispatch_glIsEnabledi1(n, n2, addressof_glIsEnabledi);
    }
    
    private native boolean dispatch_glIsEnabledi1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsBaseVertex = this._pat._addressof_glDrawElementsBaseVertex;
        if (addressof_glDrawElementsBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsBaseVertex"));
        }
        this.dispatch_glDrawElementsBaseVertex1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsBaseVertex);
    }
    
    private native void dispatch_glDrawElementsBaseVertex1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElementsBaseVertex = this._pat._addressof_glDrawElementsBaseVertex;
        if (addressof_glDrawElementsBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsBaseVertex"));
        }
        this.dispatch_glDrawElementsBaseVertex1(n, n2, n3, n4, n5, addressof_glDrawElementsBaseVertex);
    }
    
    private native void dispatch_glDrawElementsBaseVertex1(final int p0, final int p1, final int p2, final long p3, final int p4, final long p5);
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n4);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawRangeElementsBaseVertex = this._pat._addressof_glDrawRangeElementsBaseVertex;
        if (addressof_glDrawRangeElementsBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElementsBaseVertex"));
        }
        this.dispatch_glDrawRangeElementsBaseVertex1(n, n2, n3, n4, n5, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n6, addressof_glDrawRangeElementsBaseVertex);
    }
    
    private native void dispatch_glDrawRangeElementsBaseVertex1(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final long p9);
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final int n7) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawRangeElementsBaseVertex = this._pat._addressof_glDrawRangeElementsBaseVertex;
        if (addressof_glDrawRangeElementsBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElementsBaseVertex"));
        }
        this.dispatch_glDrawRangeElementsBaseVertex1(n, n2, n3, n4, n5, n6, n7, addressof_glDrawRangeElementsBaseVertex);
    }
    
    private native void dispatch_glDrawRangeElementsBaseVertex1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5, final int p6, final long p7);
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedBaseVertex = this._pat._addressof_glDrawElementsInstancedBaseVertex;
        if (addressof_glDrawElementsInstancedBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertex"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertex1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, n5, addressof_glDrawElementsInstancedBaseVertex);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertex1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElementsInstancedBaseVertex = this._pat._addressof_glDrawElementsInstancedBaseVertex;
        if (addressof_glDrawElementsInstancedBaseVertex == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertex"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertex1(n, n2, n3, n4, n5, n6, addressof_glDrawElementsInstancedBaseVertex);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertex1(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glFramebufferTexture = this._pat._addressof_glFramebufferTexture;
        if (addressof_glFramebufferTexture == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture"));
        }
        this.dispatch_glFramebufferTexture1(n, n2, n3, n4, addressof_glFramebufferTexture);
    }
    
    private native void dispatch_glFramebufferTexture1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glPrimitiveBoundingBox(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final long addressof_glPrimitiveBoundingBox = this._pat._addressof_glPrimitiveBoundingBox;
        if (addressof_glPrimitiveBoundingBox == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPrimitiveBoundingBox"));
        }
        this.dispatch_glPrimitiveBoundingBox1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glPrimitiveBoundingBox);
    }
    
    private native void dispatch_glPrimitiveBoundingBox1(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final long p8);
    
    @Override
    public int glGetGraphicsResetStatus() {
        final long addressof_glGetGraphicsResetStatus = this._pat._addressof_glGetGraphicsResetStatus;
        if (addressof_glGetGraphicsResetStatus == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetGraphicsResetStatus"));
        }
        return this.dispatch_glGetGraphicsResetStatus1(addressof_glGetGraphicsResetStatus);
    }
    
    private native int dispatch_glGetGraphicsResetStatus1(final long p0);
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glReadnPixels = this._pat._addressof_glReadnPixels;
        if (addressof_glReadnPixels == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadnPixels"));
        }
        this.dispatch_glReadnPixels1(n, n2, n3, n4, n5, n6, n7, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glReadnPixels);
    }
    
    private native void dispatch_glReadnPixels1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final Object p7, final int p8, final boolean p9, final long p10);
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetnUniformfv = this._pat._addressof_glGetnUniformfv;
        if (addressof_glGetnUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformfv"));
        }
        this.dispatch_glGetnUniformfv1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetnUniformfv);
    }
    
    private native void dispatch_glGetnUniformfv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetnUniformfv = this._pat._addressof_glGetnUniformfv;
        if (addressof_glGetnUniformfv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformfv"));
        }
        this.dispatch_glGetnUniformfv1(n, n2, n3, array, 4 * n4, false, addressof_glGetnUniformfv);
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetnUniformiv = this._pat._addressof_glGetnUniformiv;
        if (addressof_glGetnUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformiv"));
        }
        this.dispatch_glGetnUniformiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetnUniformiv);
    }
    
    private native void dispatch_glGetnUniformiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetnUniformiv = this._pat._addressof_glGetnUniformiv;
        if (addressof_glGetnUniformiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformiv"));
        }
        this.dispatch_glGetnUniformiv1(n, n2, n3, array, 4 * n4, false, addressof_glGetnUniformiv);
    }
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetnUniformuiv = this._pat._addressof_glGetnUniformuiv;
        if (addressof_glGetnUniformuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformuiv"));
        }
        this.dispatch_glGetnUniformuiv1(n, n2, n3, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetnUniformuiv);
    }
    
    private native void dispatch_glGetnUniformuiv1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"params_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetnUniformuiv = this._pat._addressof_glGetnUniformuiv;
        if (addressof_glGetnUniformuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetnUniformuiv"));
        }
        this.dispatch_glGetnUniformuiv1(n, n2, n3, array, 4 * n4, false, addressof_glGetnUniformuiv);
    }
    
    @Override
    public void glMinSampleShading(final float n) {
        final long addressof_glMinSampleShading = this._pat._addressof_glMinSampleShading;
        if (addressof_glMinSampleShading == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMinSampleShading"));
        }
        this.dispatch_glMinSampleShading1(n, addressof_glMinSampleShading);
    }
    
    private native void dispatch_glMinSampleShading1(final float p0, final long p1);
    
    @Override
    public void glPatchParameteri(final int n, final int n2) {
        final long addressof_glPatchParameteri = this._pat._addressof_glPatchParameteri;
        if (addressof_glPatchParameteri == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPatchParameteri"));
        }
        this.dispatch_glPatchParameteri1(n, n2, addressof_glPatchParameteri);
    }
    
    private native void dispatch_glPatchParameteri1(final int p0, final int p1, final long p2);
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexParameterIiv = this._pat._addressof_glTexParameterIiv;
        if (addressof_glTexParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterIiv"));
        }
        this.dispatch_glTexParameterIiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexParameterIiv);
    }
    
    private native void dispatch_glTexParameterIiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameterIiv = this._pat._addressof_glTexParameterIiv;
        if (addressof_glTexParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterIiv"));
        }
        this.dispatch_glTexParameterIiv1(n, n2, array, 4 * n3, false, addressof_glTexParameterIiv);
    }
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glTexParameterIuiv = this._pat._addressof_glTexParameterIuiv;
        if (addressof_glTexParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterIuiv"));
        }
        this.dispatch_glTexParameterIuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glTexParameterIuiv);
    }
    
    private native void dispatch_glTexParameterIuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glTexParameterIuiv = this._pat._addressof_glTexParameterIuiv;
        if (addressof_glTexParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexParameterIuiv"));
        }
        this.dispatch_glTexParameterIuiv1(n, n2, array, 4 * n3, false, addressof_glTexParameterIuiv);
    }
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexParameterIiv = this._pat._addressof_glGetTexParameterIiv;
        if (addressof_glGetTexParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterIiv"));
        }
        this.dispatch_glGetTexParameterIiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexParameterIiv);
    }
    
    private native void dispatch_glGetTexParameterIiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameterIiv = this._pat._addressof_glGetTexParameterIiv;
        if (addressof_glGetTexParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterIiv"));
        }
        this.dispatch_glGetTexParameterIiv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameterIiv);
    }
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetTexParameterIuiv = this._pat._addressof_glGetTexParameterIuiv;
        if (addressof_glGetTexParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterIuiv"));
        }
        this.dispatch_glGetTexParameterIuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetTexParameterIuiv);
    }
    
    private native void dispatch_glGetTexParameterIuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetTexParameterIuiv = this._pat._addressof_glGetTexParameterIuiv;
        if (addressof_glGetTexParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTexParameterIuiv"));
        }
        this.dispatch_glGetTexParameterIuiv1(n, n2, array, 4 * n3, false, addressof_glGetTexParameterIuiv);
    }
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glSamplerParameterIiv = this._pat._addressof_glSamplerParameterIiv;
        if (addressof_glSamplerParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterIiv"));
        }
        this.dispatch_glSamplerParameterIiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glSamplerParameterIiv);
    }
    
    private native void dispatch_glSamplerParameterIiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"param_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glSamplerParameterIiv = this._pat._addressof_glSamplerParameterIiv;
        if (addressof_glSamplerParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterIiv"));
        }
        this.dispatch_glSamplerParameterIiv1(n, n2, array, 4 * n3, false, addressof_glSamplerParameterIiv);
    }
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glSamplerParameterIuiv = this._pat._addressof_glSamplerParameterIuiv;
        if (addressof_glSamplerParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterIuiv"));
        }
        this.dispatch_glSamplerParameterIuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glSamplerParameterIuiv);
    }
    
    private native void dispatch_glSamplerParameterIuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"param_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glSamplerParameterIuiv = this._pat._addressof_glSamplerParameterIuiv;
        if (addressof_glSamplerParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSamplerParameterIuiv"));
        }
        this.dispatch_glSamplerParameterIuiv1(n, n2, array, 4 * n3, false, addressof_glSamplerParameterIuiv);
    }
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetSamplerParameterIiv = this._pat._addressof_glGetSamplerParameterIiv;
        if (addressof_glGetSamplerParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterIiv"));
        }
        this.dispatch_glGetSamplerParameterIiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetSamplerParameterIiv);
    }
    
    private native void dispatch_glGetSamplerParameterIiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetSamplerParameterIiv = this._pat._addressof_glGetSamplerParameterIiv;
        if (addressof_glGetSamplerParameterIiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterIiv"));
        }
        this.dispatch_glGetSamplerParameterIiv1(n, n2, array, 4 * n3, false, addressof_glGetSamplerParameterIiv);
    }
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetSamplerParameterIuiv = this._pat._addressof_glGetSamplerParameterIuiv;
        if (addressof_glGetSamplerParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterIuiv"));
        }
        this.dispatch_glGetSamplerParameterIuiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetSamplerParameterIuiv);
    }
    
    private native void dispatch_glGetSamplerParameterIuiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetSamplerParameterIuiv = this._pat._addressof_glGetSamplerParameterIuiv;
        if (addressof_glGetSamplerParameterIuiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetSamplerParameterIuiv"));
        }
        this.dispatch_glGetSamplerParameterIuiv1(n, n2, array, 4 * n3, false, addressof_glGetSamplerParameterIuiv);
    }
    
    @Override
    public void glTexBuffer(final int n, final int n2, final int n3) {
        final long addressof_glTexBuffer = this._pat._addressof_glTexBuffer;
        if (addressof_glTexBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBuffer"));
        }
        this.dispatch_glTexBuffer1(n, n2, n3, addressof_glTexBuffer);
    }
    
    private native void dispatch_glTexBuffer1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long addressof_glTexBufferRange = this._pat._addressof_glTexBufferRange;
        if (addressof_glTexBufferRange == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBufferRange"));
        }
        this.dispatch_glTexBufferRange1(n, n2, n3, n4, n5, addressof_glTexBufferRange);
    }
    
    private native void dispatch_glTexBufferRange1(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glTexStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long addressof_glTexStorage3DMultisample = this._pat._addressof_glTexStorage3DMultisample;
        if (addressof_glTexStorage3DMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage3DMultisample"));
        }
        this.dispatch_glTexStorage3DMultisample1(n, n2, n3, n4, n5, n6, b, addressof_glTexStorage3DMultisample);
    }
    
    private native void dispatch_glTexStorage3DMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glTexStorage1D = this._pat._addressof_glTexStorage1D;
        if (addressof_glTexStorage1D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage1D"));
        }
        this.dispatch_glTexStorage1D1(n, n2, n3, n4, addressof_glTexStorage1D);
    }
    
    private native void dispatch_glTexStorage1D1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glTextureStorage1DEXT = this._pat._addressof_glTextureStorage1DEXT;
        if (addressof_glTextureStorage1DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage1DEXT"));
        }
        this.dispatch_glTextureStorage1DEXT1(n, n2, n3, n4, n5, addressof_glTextureStorage1DEXT);
    }
    
    private native void dispatch_glTextureStorage1DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glTextureStorage2DEXT = this._pat._addressof_glTextureStorage2DEXT;
        if (addressof_glTextureStorage2DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage2DEXT"));
        }
        this.dispatch_glTextureStorage2DEXT1(n, n2, n3, n4, n5, n6, addressof_glTextureStorage2DEXT);
    }
    
    private native void dispatch_glTextureStorage2DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final long addressof_glTextureStorage3DEXT = this._pat._addressof_glTextureStorage3DEXT;
        if (addressof_glTextureStorage3DEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureStorage3DEXT"));
        }
        this.dispatch_glTextureStorage3DEXT1(n, n2, n3, n4, n5, n6, n7, addressof_glTextureStorage3DEXT);
    }
    
    private native void dispatch_glTextureStorage3DEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public void glTexImage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        final long addressof_glTexImage2DMultisample = this._pat._addressof_glTexImage2DMultisample;
        if (addressof_glTexImage2DMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage2DMultisample"));
        }
        this.dispatch_glTexImage2DMultisample1(n, n2, n3, n4, n5, b, addressof_glTexImage2DMultisample);
    }
    
    private native void dispatch_glTexImage2DMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glTexImage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long addressof_glTexImage3DMultisample = this._pat._addressof_glTexImage3DMultisample;
        if (addressof_glTexImage3DMultisample == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexImage3DMultisample"));
        }
        this.dispatch_glTexImage3DMultisample1(n, n2, n3, n4, n5, n6, b, addressof_glTexImage3DMultisample);
    }
    
    private native void dispatch_glTexImage3DMultisample1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        final long addressof_glEGLImageTargetTexture2DOES = this._pat._addressof_glEGLImageTargetTexture2DOES;
        if (addressof_glEGLImageTargetTexture2DOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEGLImageTargetTexture2DOES"));
        }
        this.dispatch_glEGLImageTargetTexture2DOES1(n, n2, addressof_glEGLImageTargetTexture2DOES);
    }
    
    private native void dispatch_glEGLImageTargetTexture2DOES1(final int p0, final long p1, final long p2);
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        final long addressof_glEGLImageTargetRenderbufferStorageOES = this._pat._addressof_glEGLImageTargetRenderbufferStorageOES;
        if (addressof_glEGLImageTargetRenderbufferStorageOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEGLImageTargetRenderbufferStorageOES"));
        }
        this.dispatch_glEGLImageTargetRenderbufferStorageOES1(n, n2, addressof_glEGLImageTargetRenderbufferStorageOES);
    }
    
    private native void dispatch_glEGLImageTargetRenderbufferStorageOES1(final int p0, final long p1, final long p2);
    
    @Override
    public void glEnableiOES(final int n, final int n2) {
        final long addressof_glEnableiOES = this._pat._addressof_glEnableiOES;
        if (addressof_glEnableiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableiOES"));
        }
        this.dispatch_glEnableiOES1(n, n2, addressof_glEnableiOES);
    }
    
    private native void dispatch_glEnableiOES1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisableiOES(final int n, final int n2) {
        final long addressof_glDisableiOES = this._pat._addressof_glDisableiOES;
        if (addressof_glDisableiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableiOES"));
        }
        this.dispatch_glDisableiOES1(n, n2, addressof_glDisableiOES);
    }
    
    private native void dispatch_glDisableiOES1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationiOES(final int n, final int n2) {
        final long addressof_glBlendEquationiOES = this._pat._addressof_glBlendEquationiOES;
        if (addressof_glBlendEquationiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationiOES"));
        }
        this.dispatch_glBlendEquationiOES1(n, n2, addressof_glBlendEquationiOES);
    }
    
    private native void dispatch_glBlendEquationiOES1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationSeparateiOES(final int n, final int n2, final int n3) {
        final long addressof_glBlendEquationSeparateiOES = this._pat._addressof_glBlendEquationSeparateiOES;
        if (addressof_glBlendEquationSeparateiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationSeparateiOES"));
        }
        this.dispatch_glBlendEquationSeparateiOES1(n, n2, n3, addressof_glBlendEquationSeparateiOES);
    }
    
    private native void dispatch_glBlendEquationSeparateiOES1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFunciOES(final int n, final int n2, final int n3) {
        final long addressof_glBlendFunciOES = this._pat._addressof_glBlendFunciOES;
        if (addressof_glBlendFunciOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFunciOES"));
        }
        this.dispatch_glBlendFunciOES1(n, n2, n3, addressof_glBlendFunciOES);
    }
    
    private native void dispatch_glBlendFunciOES1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFuncSeparateiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glBlendFuncSeparateiOES = this._pat._addressof_glBlendFuncSeparateiOES;
        if (addressof_glBlendFuncSeparateiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFuncSeparateiOES"));
        }
        this.dispatch_glBlendFuncSeparateiOES1(n, n2, n3, n4, n5, addressof_glBlendFuncSeparateiOES);
    }
    
    private native void dispatch_glBlendFuncSeparateiOES1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glColorMaskiOES(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long addressof_glColorMaskiOES = this._pat._addressof_glColorMaskiOES;
        if (addressof_glColorMaskiOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorMaskiOES"));
        }
        this.dispatch_glColorMaskiOES1(n, b, b2, b3, b4, addressof_glColorMaskiOES);
    }
    
    private native void dispatch_glColorMaskiOES1(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4, final long p5);
    
    @Override
    public boolean glIsEnablediOES(final int n, final int n2) {
        final long addressof_glIsEnablediOES = this._pat._addressof_glIsEnablediOES;
        if (addressof_glIsEnablediOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnablediOES"));
        }
        return this.dispatch_glIsEnablediOES1(n, n2, addressof_glIsEnablediOES);
    }
    
    private native boolean dispatch_glIsEnablediOES1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDrawElementsBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsBaseVertexOES = this._pat._addressof_glDrawElementsBaseVertexOES;
        if (addressof_glDrawElementsBaseVertexOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsBaseVertexOES"));
        }
        this.dispatch_glDrawElementsBaseVertexOES1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsBaseVertexOES);
    }
    
    private native void dispatch_glDrawElementsBaseVertexOES1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glDrawRangeElementsBaseVertexOES(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawRangeElementsBaseVertexOES = this._pat._addressof_glDrawRangeElementsBaseVertexOES;
        if (addressof_glDrawRangeElementsBaseVertexOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElementsBaseVertexOES"));
        }
        this.dispatch_glDrawRangeElementsBaseVertexOES1(n, n2, n3, n4, n5, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n6, addressof_glDrawRangeElementsBaseVertexOES);
    }
    
    private native void dispatch_glDrawRangeElementsBaseVertexOES1(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final long p9);
    
    @Override
    public void glDrawElementsInstancedBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedBaseVertexOES = this._pat._addressof_glDrawElementsInstancedBaseVertexOES;
        if (addressof_glDrawElementsInstancedBaseVertexOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertexOES"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertexOES1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, n5, addressof_glDrawElementsInstancedBaseVertexOES);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertexOES1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(pointerBuffer);
        final boolean direct3 = Buffers.isDirect(intBuffer2);
        final long addressof_glMultiDrawElementsBaseVertexOES = this._pat._addressof_glMultiDrawElementsBaseVertexOES;
        if (addressof_glMultiDrawElementsBaseVertexOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawElementsBaseVertexOES"));
        }
        this.dispatch_glMultiDrawElementsBaseVertexOES1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n2, direct2 ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct2 ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct2, n3, direct3 ? intBuffer2 : Buffers.getArray(intBuffer2), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct3, addressof_glMultiDrawElementsBaseVertexOES);
    }
    
    private native void dispatch_glMultiDrawElementsBaseVertexOES1(final int p0, final Object p1, final int p2, final boolean p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final Object p9, final int p10, final boolean p11, final long p12);
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"count_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final boolean direct = Buffers.isDirect(pointerBuffer);
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"basevertex_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glMultiDrawElementsBaseVertexOES = this._pat._addressof_glMultiDrawElementsBaseVertexOES;
        if (addressof_glMultiDrawElementsBaseVertexOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawElementsBaseVertexOES"));
        }
        this.dispatch_glMultiDrawElementsBaseVertexOES1(n, array, 4 * n2, false, n3, direct ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct, n4, array2, 4 * n5, false, addressof_glMultiDrawElementsBaseVertexOES);
    }
    
    @Override
    public void glFramebufferTextureOES(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glFramebufferTextureOES = this._pat._addressof_glFramebufferTextureOES;
        if (addressof_glFramebufferTextureOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTextureOES"));
        }
        this.dispatch_glFramebufferTextureOES1(n, n2, n3, n4, addressof_glFramebufferTextureOES);
    }
    
    private native void dispatch_glFramebufferTextureOES1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    private long glMapBufferDelegate(final int n, final int n2) {
        final long addressof_glMapBuffer = this._pat._addressof_glMapBuffer;
        if (addressof_glMapBuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMapBuffer"));
        }
        return this.dispatch_glMapBufferDelegate1(n, n2, addressof_glMapBuffer);
    }
    
    private native long dispatch_glMapBufferDelegate1(final int p0, final int p1, final long p2);
    
    @Override
    public void glPrimitiveBoundingBoxOES(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final long addressof_glPrimitiveBoundingBoxOES = this._pat._addressof_glPrimitiveBoundingBoxOES;
        if (addressof_glPrimitiveBoundingBoxOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPrimitiveBoundingBoxOES"));
        }
        this.dispatch_glPrimitiveBoundingBoxOES1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glPrimitiveBoundingBoxOES);
    }
    
    private native void dispatch_glPrimitiveBoundingBoxOES1(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final long p8);
    
    @Override
    public void glMinSampleShadingOES(final float n) {
        final long addressof_glMinSampleShadingOES = this._pat._addressof_glMinSampleShadingOES;
        if (addressof_glMinSampleShadingOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMinSampleShadingOES"));
        }
        this.dispatch_glMinSampleShadingOES1(n, addressof_glMinSampleShadingOES);
    }
    
    private native void dispatch_glMinSampleShadingOES1(final float p0, final long p1);
    
    @Override
    public void glPatchParameteriOES(final int n, final int n2) {
        final long addressof_glPatchParameteriOES = this._pat._addressof_glPatchParameteriOES;
        if (addressof_glPatchParameteriOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPatchParameteriOES"));
        }
        this.dispatch_glPatchParameteriOES1(n, n2, addressof_glPatchParameteriOES);
    }
    
    private native void dispatch_glPatchParameteriOES1(final int p0, final int p1, final long p2);
    
    @Override
    public void glFramebufferTexture3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTexture3D = this._pat._addressof_glFramebufferTexture3D;
        if (addressof_glFramebufferTexture3D == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture3D"));
        }
        this.dispatch_glFramebufferTexture3D1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTexture3D);
    }
    
    private native void dispatch_glFramebufferTexture3D1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glTexBufferOES(final int n, final int n2, final int n3) {
        final long addressof_glTexBufferOES = this._pat._addressof_glTexBufferOES;
        if (addressof_glTexBufferOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBufferOES"));
        }
        this.dispatch_glTexBufferOES1(n, n2, n3, addressof_glTexBufferOES);
    }
    
    private native void dispatch_glTexBufferOES1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexBufferRangeOES(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long addressof_glTexBufferRangeOES = this._pat._addressof_glTexBufferRangeOES;
        if (addressof_glTexBufferRangeOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBufferRangeOES"));
        }
        this.dispatch_glTexBufferRangeOES1(n, n2, n3, n4, n5, addressof_glTexBufferRangeOES);
    }
    
    private native void dispatch_glTexBufferRangeOES1(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glTexStorage3DMultisampleOES(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        final long addressof_glTexStorage3DMultisampleOES = this._pat._addressof_glTexStorage3DMultisampleOES;
        if (addressof_glTexStorage3DMultisampleOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexStorage3DMultisampleOES"));
        }
        this.dispatch_glTexStorage3DMultisampleOES1(n, n2, n3, n4, n5, n6, b, addressof_glTexStorage3DMultisampleOES);
    }
    
    private native void dispatch_glTexStorage3DMultisampleOES1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glTextureView(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        final long addressof_glTextureView = this._pat._addressof_glTextureView;
        if (addressof_glTextureView == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTextureView"));
        }
        this.dispatch_glTextureView1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glTextureView);
    }
    
    private native void dispatch_glTextureView1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        final long addressof_glBindVertexArrayOES = this._pat._addressof_glBindVertexArrayOES;
        if (addressof_glBindVertexArrayOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindVertexArrayOES"));
        }
        this.dispatch_glBindVertexArrayOES1(n, addressof_glBindVertexArrayOES);
    }
    
    private native void dispatch_glBindVertexArrayOES1(final int p0, final long p1);
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDeleteVertexArraysOES = this._pat._addressof_glDeleteVertexArraysOES;
        if (addressof_glDeleteVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArraysOES"));
        }
        this.dispatch_glDeleteVertexArraysOES1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDeleteVertexArraysOES);
    }
    
    private native void dispatch_glDeleteVertexArraysOES1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDeleteVertexArraysOES = this._pat._addressof_glDeleteVertexArraysOES;
        if (addressof_glDeleteVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDeleteVertexArraysOES"));
        }
        this.dispatch_glDeleteVertexArraysOES1(n, array, 4 * n2, false, addressof_glDeleteVertexArraysOES);
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGenVertexArraysOES = this._pat._addressof_glGenVertexArraysOES;
        if (addressof_glGenVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArraysOES"));
        }
        this.dispatch_glGenVertexArraysOES1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGenVertexArraysOES);
    }
    
    private native void dispatch_glGenVertexArraysOES1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"arrays_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGenVertexArraysOES = this._pat._addressof_glGenVertexArraysOES;
        if (addressof_glGenVertexArraysOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGenVertexArraysOES"));
        }
        this.dispatch_glGenVertexArraysOES1(n, array, 4 * n2, false, addressof_glGenVertexArraysOES);
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        final long addressof_glIsVertexArrayOES = this._pat._addressof_glIsVertexArrayOES;
        if (addressof_glIsVertexArrayOES == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsVertexArrayOES"));
        }
        return this.dispatch_glIsVertexArrayOES1(n, addressof_glIsVertexArrayOES);
    }
    
    private native boolean dispatch_glIsVertexArrayOES1(final int p0, final long p1);
    
    @Override
    public void glBlitFramebufferANGLE(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long addressof_glBlitFramebufferANGLE = this._pat._addressof_glBlitFramebufferANGLE;
        if (addressof_glBlitFramebufferANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlitFramebufferANGLE"));
        }
        this.dispatch_glBlitFramebufferANGLE1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, addressof_glBlitFramebufferANGLE);
    }
    
    private native void dispatch_glBlitFramebufferANGLE1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    @Override
    public void glDrawArraysInstancedANGLE(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glDrawArraysInstancedANGLE = this._pat._addressof_glDrawArraysInstancedANGLE;
        if (addressof_glDrawArraysInstancedANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysInstancedANGLE"));
        }
        this.dispatch_glDrawArraysInstancedANGLE1(n, n2, n3, n4, addressof_glDrawArraysInstancedANGLE);
    }
    
    private native void dispatch_glDrawArraysInstancedANGLE1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glDrawElementsInstancedANGLE(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedANGLE = this._pat._addressof_glDrawElementsInstancedANGLE;
        if (addressof_glDrawElementsInstancedANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedANGLE"));
        }
        this.dispatch_glDrawElementsInstancedANGLE1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsInstancedANGLE);
    }
    
    private native void dispatch_glDrawElementsInstancedANGLE1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glVertexAttribDivisorANGLE(final int n, final int n2) {
        final long addressof_glVertexAttribDivisorANGLE = this._pat._addressof_glVertexAttribDivisorANGLE;
        if (addressof_glVertexAttribDivisorANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribDivisorANGLE"));
        }
        this.dispatch_glVertexAttribDivisorANGLE1(n, n2, addressof_glVertexAttribDivisorANGLE);
    }
    
    private native void dispatch_glVertexAttribDivisorANGLE1(final int p0, final int p1, final long p2);
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetTranslatedShaderSourceANGLE = this._pat._addressof_glGetTranslatedShaderSourceANGLE;
        if (addressof_glGetTranslatedShaderSourceANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTranslatedShaderSourceANGLE"));
        }
        this.dispatch_glGetTranslatedShaderSourceANGLE1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetTranslatedShaderSourceANGLE);
    }
    
    private native void dispatch_glGetTranslatedShaderSourceANGLE1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"source_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetTranslatedShaderSourceANGLE = this._pat._addressof_glGetTranslatedShaderSourceANGLE;
        if (addressof_glGetTranslatedShaderSourceANGLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetTranslatedShaderSourceANGLE"));
        }
        this.dispatch_glGetTranslatedShaderSourceANGLE1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetTranslatedShaderSourceANGLE);
    }
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glCopyTextureLevelsAPPLE = this._pat._addressof_glCopyTextureLevelsAPPLE;
        if (addressof_glCopyTextureLevelsAPPLE == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyTextureLevelsAPPLE"));
        }
        this.dispatch_glCopyTextureLevelsAPPLE1(n, n2, n3, n4, addressof_glCopyTextureLevelsAPPLE);
    }
    
    private native void dispatch_glCopyTextureLevelsAPPLE1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        final long addressof_glResolveMultisampleFramebuffer = this._pat._addressof_glResolveMultisampleFramebuffer;
        if (addressof_glResolveMultisampleFramebuffer == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glResolveMultisampleFramebuffer"));
        }
        this.dispatch_glResolveMultisampleFramebuffer1(addressof_glResolveMultisampleFramebuffer);
    }
    
    private native void dispatch_glResolveMultisampleFramebuffer1(final long p0);
    
    @Override
    public void glDrawArraysInstancedBaseInstance(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glDrawArraysInstancedBaseInstance = this._pat._addressof_glDrawArraysInstancedBaseInstance;
        if (addressof_glDrawArraysInstancedBaseInstance == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysInstancedBaseInstance"));
        }
        this.dispatch_glDrawArraysInstancedBaseInstance1(n, n2, n3, n4, n5, addressof_glDrawArraysInstancedBaseInstance);
    }
    
    private native void dispatch_glDrawArraysInstancedBaseInstance1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    public void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedBaseInstance = this._pat._addressof_glDrawElementsInstancedBaseInstance;
        if (addressof_glDrawElementsInstancedBaseInstance == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseInstance"));
        }
        this.dispatch_glDrawElementsInstancedBaseInstance1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, n5, addressof_glDrawElementsInstancedBaseInstance);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseInstance1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElementsInstancedBaseInstance = this._pat._addressof_glDrawElementsInstancedBaseInstance;
        if (addressof_glDrawElementsInstancedBaseInstance == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseInstance"));
        }
        this.dispatch_glDrawElementsInstancedBaseInstance1(n, n2, n3, n4, n5, n6, addressof_glDrawElementsInstancedBaseInstance);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseInstance1(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final long p6);
    
    public void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5, final int n6) {
        this.checkElementVBOUnbound(true);
        Buffers.rangeCheck(buffer, n2);
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedBaseVertexBaseInstance = this._pat._addressof_glDrawElementsInstancedBaseVertexBaseInstance;
        if (addressof_glDrawElementsInstancedBaseVertexBaseInstance == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertexBaseInstance"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertexBaseInstance1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, n5, n6, addressof_glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertexBaseInstance1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final int p7, final int p8, final long p9);
    
    @Override
    public void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6, final int n7) {
        this.checkElementVBOBound(true);
        final long addressof_glDrawElementsInstancedBaseVertexBaseInstance = this._pat._addressof_glDrawElementsInstancedBaseVertexBaseInstance;
        if (addressof_glDrawElementsInstancedBaseVertexBaseInstance == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertexBaseInstance"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertexBaseInstance1(n, n2, n3, n4, n5, n6, n7, addressof_glDrawElementsInstancedBaseVertexBaseInstance);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertexBaseInstance1(final int p0, final int p1, final int p2, final long p3, final int p4, final int p5, final int p6, final long p7);
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glBindFragDataLocationIndexedEXT = this._pat._addressof_glBindFragDataLocationIndexedEXT;
        if (addressof_glBindFragDataLocationIndexedEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFragDataLocationIndexedEXT"));
        }
        this.dispatch_glBindFragDataLocationIndexedEXT1(n, n2, n3, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glBindFragDataLocationIndexedEXT);
    }
    
    private native void dispatch_glBindFragDataLocationIndexedEXT1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"name_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glBindFragDataLocationIndexedEXT = this._pat._addressof_glBindFragDataLocationIndexedEXT;
        if (addressof_glBindFragDataLocationIndexedEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFragDataLocationIndexedEXT"));
        }
        this.dispatch_glBindFragDataLocationIndexedEXT1(n, n2, n3, array, n4, false, addressof_glBindFragDataLocationIndexedEXT);
    }
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glBindFragDataLocationEXT = this._pat._addressof_glBindFragDataLocationEXT;
        if (addressof_glBindFragDataLocationEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFragDataLocationEXT"));
        }
        this.dispatch_glBindFragDataLocationEXT1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glBindFragDataLocationEXT);
    }
    
    private native void dispatch_glBindFragDataLocationEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final byte[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"name_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glBindFragDataLocationEXT = this._pat._addressof_glBindFragDataLocationEXT;
        if (addressof_glBindFragDataLocationEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBindFragDataLocationEXT"));
        }
        this.dispatch_glBindFragDataLocationEXT1(n, n2, array, n3, false, addressof_glBindFragDataLocationEXT);
    }
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetProgramResourceLocationIndexEXT = this._pat._addressof_glGetProgramResourceLocationIndexEXT;
        if (addressof_glGetProgramResourceLocationIndexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceLocationIndexEXT"));
        }
        return this.dispatch_glGetProgramResourceLocationIndexEXT1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetProgramResourceLocationIndexEXT);
    }
    
    private native int dispatch_glGetProgramResourceLocationIndexEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final byte[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"name_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetProgramResourceLocationIndexEXT = this._pat._addressof_glGetProgramResourceLocationIndexEXT;
        if (addressof_glGetProgramResourceLocationIndexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetProgramResourceLocationIndexEXT"));
        }
        return this.dispatch_glGetProgramResourceLocationIndexEXT1(n, n2, array, n3, false, addressof_glGetProgramResourceLocationIndexEXT);
    }
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final long addressof_glGetFragDataIndexEXT = this._pat._addressof_glGetFragDataIndexEXT;
        if (addressof_glGetFragDataIndexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFragDataIndexEXT"));
        }
        return this.dispatch_glGetFragDataIndexEXT1(n, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, addressof_glGetFragDataIndexEXT);
    }
    
    private native int dispatch_glGetFragDataIndexEXT1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final byte[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"name_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFragDataIndexEXT = this._pat._addressof_glGetFragDataIndexEXT;
        if (addressof_glGetFragDataIndexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFragDataIndexEXT"));
        }
        return this.dispatch_glGetFragDataIndexEXT1(n, array, n2, false, addressof_glGetFragDataIndexEXT);
    }
    
    @Override
    public void glBufferStorageEXT(final int n, final long n2, final Buffer buffer, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glBufferStorageEXT = this._pat._addressof_glBufferStorageEXT;
        if (addressof_glBufferStorageEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBufferStorageEXT"));
        }
        this.dispatch_glBufferStorageEXT1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n3, addressof_glBufferStorageEXT);
    }
    
    private native void dispatch_glBufferStorageEXT1(final int p0, final long p1, final Object p2, final int p3, final boolean p4, final int p5, final long p6);
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glDiscardFramebufferEXT = this._pat._addressof_glDiscardFramebufferEXT;
        if (addressof_glDiscardFramebufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDiscardFramebufferEXT"));
        }
        this.dispatch_glDiscardFramebufferEXT1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glDiscardFramebufferEXT);
    }
    
    private native void dispatch_glDiscardFramebufferEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"attachments_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDiscardFramebufferEXT = this._pat._addressof_glDiscardFramebufferEXT;
        if (addressof_glDiscardFramebufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDiscardFramebufferEXT"));
        }
        this.dispatch_glDiscardFramebufferEXT1(n, n2, array, 4 * n3, false, addressof_glDiscardFramebufferEXT);
    }
    
    @Override
    public void glQueryCounter(final int n, final int n2) {
        final long addressof_glQueryCounter = this._pat._addressof_glQueryCounter;
        if (addressof_glQueryCounter == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glQueryCounter"));
        }
        this.dispatch_glQueryCounter1(n, n2, addressof_glQueryCounter);
    }
    
    private native void dispatch_glQueryCounter1(final int p0, final int p1, final long p2);
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetQueryObjectiv = this._pat._addressof_glGetQueryObjectiv;
        if (addressof_glGetQueryObjectiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectiv"));
        }
        this.dispatch_glGetQueryObjectiv1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetQueryObjectiv);
    }
    
    private native void dispatch_glGetQueryObjectiv1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetQueryObjectiv = this._pat._addressof_glGetQueryObjectiv;
        if (addressof_glGetQueryObjectiv == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectiv"));
        }
        this.dispatch_glGetQueryObjectiv1(n, n2, array, 4 * n3, false, addressof_glGetQueryObjectiv);
    }
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final LongBuffer longBuffer) {
        final boolean direct = Buffers.isDirect(longBuffer);
        final long addressof_glGetQueryObjecti64v = this._pat._addressof_glGetQueryObjecti64v;
        if (addressof_glGetQueryObjecti64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjecti64v"));
        }
        this.dispatch_glGetQueryObjecti64v1(n, n2, direct ? longBuffer : Buffers.getArray(longBuffer), direct ? Buffers.getDirectBufferByteOffset(longBuffer) : Buffers.getIndirectBufferByteOffset(longBuffer), direct, addressof_glGetQueryObjecti64v);
    }
    
    private native void dispatch_glGetQueryObjecti64v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final long[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetQueryObjecti64v = this._pat._addressof_glGetQueryObjecti64v;
        if (addressof_glGetQueryObjecti64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjecti64v"));
        }
        this.dispatch_glGetQueryObjecti64v1(n, n2, array, 8 * n3, false, addressof_glGetQueryObjecti64v);
    }
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final LongBuffer longBuffer) {
        final boolean direct = Buffers.isDirect(longBuffer);
        final long addressof_glGetQueryObjectui64v = this._pat._addressof_glGetQueryObjectui64v;
        if (addressof_glGetQueryObjectui64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectui64v"));
        }
        this.dispatch_glGetQueryObjectui64v1(n, n2, direct ? longBuffer : Buffers.getArray(longBuffer), direct ? Buffers.getDirectBufferByteOffset(longBuffer) : Buffers.getIndirectBufferByteOffset(longBuffer), direct, addressof_glGetQueryObjectui64v);
    }
    
    private native void dispatch_glGetQueryObjectui64v1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final long[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"params_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetQueryObjectui64v = this._pat._addressof_glGetQueryObjectui64v;
        if (addressof_glGetQueryObjectui64v == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetQueryObjectui64v"));
        }
        this.dispatch_glGetQueryObjectui64v1(n, n2, array, 8 * n3, false, addressof_glGetQueryObjectui64v);
    }
    
    @Override
    public void glEnableiEXT(final int n, final int n2) {
        final long addressof_glEnableiEXT = this._pat._addressof_glEnableiEXT;
        if (addressof_glEnableiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableiEXT"));
        }
        this.dispatch_glEnableiEXT1(n, n2, addressof_glEnableiEXT);
    }
    
    private native void dispatch_glEnableiEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisableiEXT(final int n, final int n2) {
        final long addressof_glDisableiEXT = this._pat._addressof_glDisableiEXT;
        if (addressof_glDisableiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableiEXT"));
        }
        this.dispatch_glDisableiEXT1(n, n2, addressof_glDisableiEXT);
    }
    
    private native void dispatch_glDisableiEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationiEXT(final int n, final int n2) {
        final long addressof_glBlendEquationiEXT = this._pat._addressof_glBlendEquationiEXT;
        if (addressof_glBlendEquationiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationiEXT"));
        }
        this.dispatch_glBlendEquationiEXT1(n, n2, addressof_glBlendEquationiEXT);
    }
    
    private native void dispatch_glBlendEquationiEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glBlendEquationSeparateiEXT(final int n, final int n2, final int n3) {
        final long addressof_glBlendEquationSeparateiEXT = this._pat._addressof_glBlendEquationSeparateiEXT;
        if (addressof_glBlendEquationSeparateiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendEquationSeparateiEXT"));
        }
        this.dispatch_glBlendEquationSeparateiEXT1(n, n2, n3, addressof_glBlendEquationSeparateiEXT);
    }
    
    private native void dispatch_glBlendEquationSeparateiEXT1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFunciEXT(final int n, final int n2, final int n3) {
        final long addressof_glBlendFunciEXT = this._pat._addressof_glBlendFunciEXT;
        if (addressof_glBlendFunciEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFunciEXT"));
        }
        this.dispatch_glBlendFunciEXT1(n, n2, n3, addressof_glBlendFunciEXT);
    }
    
    private native void dispatch_glBlendFunciEXT1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glBlendFuncSeparateiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glBlendFuncSeparateiEXT = this._pat._addressof_glBlendFuncSeparateiEXT;
        if (addressof_glBlendFuncSeparateiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlendFuncSeparateiEXT"));
        }
        this.dispatch_glBlendFuncSeparateiEXT1(n, n2, n3, n4, n5, addressof_glBlendFuncSeparateiEXT);
    }
    
    private native void dispatch_glBlendFuncSeparateiEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glColorMaskiEXT(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        final long addressof_glColorMaskiEXT = this._pat._addressof_glColorMaskiEXT;
        if (addressof_glColorMaskiEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glColorMaskiEXT"));
        }
        this.dispatch_glColorMaskiEXT1(n, b, b2, b3, b4, addressof_glColorMaskiEXT);
    }
    
    private native void dispatch_glColorMaskiEXT1(final int p0, final boolean p1, final boolean p2, final boolean p3, final boolean p4, final long p5);
    
    @Override
    public boolean glIsEnablediEXT(final int n, final int n2) {
        final long addressof_glIsEnablediEXT = this._pat._addressof_glIsEnablediEXT;
        if (addressof_glIsEnablediEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnablediEXT"));
        }
        return this.dispatch_glIsEnablediEXT1(n, n2, addressof_glIsEnablediEXT);
    }
    
    private native boolean dispatch_glIsEnablediEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDrawElementsBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsBaseVertexEXT = this._pat._addressof_glDrawElementsBaseVertexEXT;
        if (addressof_glDrawElementsBaseVertexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsBaseVertexEXT"));
        }
        this.dispatch_glDrawElementsBaseVertexEXT1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsBaseVertexEXT);
    }
    
    private native void dispatch_glDrawElementsBaseVertexEXT1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glDrawRangeElementsBaseVertexEXT(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawRangeElementsBaseVertexEXT = this._pat._addressof_glDrawRangeElementsBaseVertexEXT;
        if (addressof_glDrawRangeElementsBaseVertexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawRangeElementsBaseVertexEXT"));
        }
        this.dispatch_glDrawRangeElementsBaseVertexEXT1(n, n2, n3, n4, n5, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n6, addressof_glDrawRangeElementsBaseVertexEXT);
    }
    
    private native void dispatch_glDrawRangeElementsBaseVertexEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final long p9);
    
    @Override
    public void glDrawElementsInstancedBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedBaseVertexEXT = this._pat._addressof_glDrawElementsInstancedBaseVertexEXT;
        if (addressof_glDrawElementsInstancedBaseVertexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedBaseVertexEXT"));
        }
        this.dispatch_glDrawElementsInstancedBaseVertexEXT1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, n5, addressof_glDrawElementsInstancedBaseVertexEXT);
    }
    
    private native void dispatch_glDrawElementsInstancedBaseVertexEXT1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final int p7, final long p8);
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(pointerBuffer);
        final boolean direct3 = Buffers.isDirect(intBuffer2);
        final long addressof_glMultiDrawElementsBaseVertexEXT = this._pat._addressof_glMultiDrawElementsBaseVertexEXT;
        if (addressof_glMultiDrawElementsBaseVertexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawElementsBaseVertexEXT"));
        }
        this.dispatch_glMultiDrawElementsBaseVertexEXT1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n2, direct2 ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct2 ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct2, n3, direct3 ? intBuffer2 : Buffers.getArray(intBuffer2), direct3 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct3, addressof_glMultiDrawElementsBaseVertexEXT);
    }
    
    private native void dispatch_glMultiDrawElementsBaseVertexEXT1(final int p0, final Object p1, final int p2, final boolean p3, final int p4, final Object p5, final int p6, final boolean p7, final int p8, final Object p9, final int p10, final boolean p11, final long p12);
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"count_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final boolean direct = Buffers.isDirect(pointerBuffer);
        if (array2 != null && array2.length <= n5) {
            throw new GLException("array offset argument \"basevertex_offset\" (" + n5 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glMultiDrawElementsBaseVertexEXT = this._pat._addressof_glMultiDrawElementsBaseVertexEXT;
        if (addressof_glMultiDrawElementsBaseVertexEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawElementsBaseVertexEXT"));
        }
        this.dispatch_glMultiDrawElementsBaseVertexEXT1(n, array, 4 * n2, false, n3, direct ? ((pointerBuffer != null) ? pointerBuffer.getBuffer() : null) : Buffers.getArray(pointerBuffer), direct ? Buffers.getDirectBufferByteOffset(pointerBuffer) : Buffers.getIndirectBufferByteOffset(pointerBuffer), direct, n4, array2, 4 * n5, false, addressof_glMultiDrawElementsBaseVertexEXT);
    }
    
    @Override
    public void glFramebufferTextureEXT(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glFramebufferTextureEXT = this._pat._addressof_glFramebufferTextureEXT;
        if (addressof_glFramebufferTextureEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTextureEXT"));
        }
        this.dispatch_glFramebufferTextureEXT1(n, n2, n3, n4, addressof_glFramebufferTextureEXT);
    }
    
    private native void dispatch_glFramebufferTextureEXT1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glMultiDrawArraysIndirectEXT(final int n, final Buffer buffer, final int n2, final int n3) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glMultiDrawArraysIndirectEXT = this._pat._addressof_glMultiDrawArraysIndirectEXT;
        if (addressof_glMultiDrawArraysIndirectEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawArraysIndirectEXT"));
        }
        this.dispatch_glMultiDrawArraysIndirectEXT1(n, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n2, n3, addressof_glMultiDrawArraysIndirectEXT);
    }
    
    private native void dispatch_glMultiDrawArraysIndirectEXT1(final int p0, final Object p1, final int p2, final boolean p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glMultiDrawElementsIndirectEXT(final int n, final int n2, final Buffer buffer, final int n3, final int n4) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glMultiDrawElementsIndirectEXT = this._pat._addressof_glMultiDrawElementsIndirectEXT;
        if (addressof_glMultiDrawElementsIndirectEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glMultiDrawElementsIndirectEXT"));
        }
        this.dispatch_glMultiDrawElementsIndirectEXT1(n, n2, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n3, n4, addressof_glMultiDrawElementsIndirectEXT);
    }
    
    private native void dispatch_glMultiDrawElementsIndirectEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final int p5, final int p6, final long p7);
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisampleEXT = this._pat._addressof_glRenderbufferStorageMultisampleEXT;
        if (addressof_glRenderbufferStorageMultisampleEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisampleEXT"));
        }
        this.dispatch_glRenderbufferStorageMultisampleEXT1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisampleEXT);
    }
    
    private native void dispatch_glRenderbufferStorageMultisampleEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTexture2DMultisampleEXT = this._pat._addressof_glFramebufferTexture2DMultisampleEXT;
        if (addressof_glFramebufferTexture2DMultisampleEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2DMultisampleEXT"));
        }
        this.dispatch_glFramebufferTexture2DMultisampleEXT1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTexture2DMultisampleEXT);
    }
    
    private native void dispatch_glFramebufferTexture2DMultisampleEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glReadBufferIndexedEXT(final int n, final int n2) {
        final long addressof_glReadBufferIndexedEXT = this._pat._addressof_glReadBufferIndexedEXT;
        if (addressof_glReadBufferIndexedEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadBufferIndexedEXT"));
        }
        this.dispatch_glReadBufferIndexedEXT1(n, n2, addressof_glReadBufferIndexedEXT);
    }
    
    private native void dispatch_glReadBufferIndexedEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glDrawBuffersIndexedEXT = this._pat._addressof_glDrawBuffersIndexedEXT;
        if (addressof_glDrawBuffersIndexedEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawBuffersIndexedEXT"));
        }
        this.dispatch_glDrawBuffersIndexedEXT1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glDrawBuffersIndexedEXT);
    }
    
    private native void dispatch_glDrawBuffersIndexedEXT1(final int p0, final Object p1, final int p2, final boolean p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final int[] array, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"location_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"indices_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glDrawBuffersIndexedEXT = this._pat._addressof_glDrawBuffersIndexedEXT;
        if (addressof_glDrawBuffersIndexedEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawBuffersIndexedEXT"));
        }
        this.dispatch_glDrawBuffersIndexedEXT1(n, array, 4 * n2, false, array2, 4 * n3, false, addressof_glDrawBuffersIndexedEXT);
    }
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glGetIntegeri_vEXT = this._pat._addressof_glGetIntegeri_vEXT;
        if (addressof_glGetIntegeri_vEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegeri_vEXT"));
        }
        this.dispatch_glGetIntegeri_vEXT1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glGetIntegeri_vEXT);
    }
    
    private native void dispatch_glGetIntegeri_vEXT1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"data_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetIntegeri_vEXT = this._pat._addressof_glGetIntegeri_vEXT;
        if (addressof_glGetIntegeri_vEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetIntegeri_vEXT"));
        }
        this.dispatch_glGetIntegeri_vEXT1(n, n2, array, 4 * n3, false, addressof_glGetIntegeri_vEXT);
    }
    
    @Override
    public void glPrimitiveBoundingBoxEXT(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final long addressof_glPrimitiveBoundingBoxEXT = this._pat._addressof_glPrimitiveBoundingBoxEXT;
        if (addressof_glPrimitiveBoundingBoxEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPrimitiveBoundingBoxEXT"));
        }
        this.dispatch_glPrimitiveBoundingBoxEXT1(n, n2, n3, n4, n5, n6, n7, n8, addressof_glPrimitiveBoundingBoxEXT);
    }
    
    private native void dispatch_glPrimitiveBoundingBoxEXT1(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final long p8);
    
    @Override
    public void glRasterSamplesEXT(final int n, final boolean b) {
        final long addressof_glRasterSamplesEXT = this._pat._addressof_glRasterSamplesEXT;
        if (addressof_glRasterSamplesEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRasterSamplesEXT"));
        }
        this.dispatch_glRasterSamplesEXT1(n, b, addressof_glRasterSamplesEXT);
    }
    
    private native void dispatch_glRasterSamplesEXT1(final int p0, final boolean p1, final long p2);
    
    @Override
    public void glTexPageCommitmentEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        final long addressof_glTexPageCommitmentEXT = this._pat._addressof_glTexPageCommitmentEXT;
        if (addressof_glTexPageCommitmentEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexPageCommitmentEXT"));
        }
        this.dispatch_glTexPageCommitmentEXT1(n, n2, n3, n4, n5, n6, n7, n8, b, addressof_glTexPageCommitmentEXT);
    }
    
    private native void dispatch_glTexPageCommitmentEXT1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final boolean p8, final long p9);
    
    @Override
    public void glPatchParameteriEXT(final int n, final int n2) {
        final long addressof_glPatchParameteriEXT = this._pat._addressof_glPatchParameteriEXT;
        if (addressof_glPatchParameteriEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPatchParameteriEXT"));
        }
        this.dispatch_glPatchParameteriEXT1(n, n2, addressof_glPatchParameteriEXT);
    }
    
    private native void dispatch_glPatchParameteriEXT1(final int p0, final int p1, final long p2);
    
    @Override
    public void glTexBufferEXT(final int n, final int n2, final int n3) {
        final long addressof_glTexBufferEXT = this._pat._addressof_glTexBufferEXT;
        if (addressof_glTexBufferEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBufferEXT"));
        }
        this.dispatch_glTexBufferEXT1(n, n2, n3, addressof_glTexBufferEXT);
    }
    
    private native void dispatch_glTexBufferEXT1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glTexBufferRangeEXT(final int n, final int n2, final int n3, final long n4, final long n5) {
        final long addressof_glTexBufferRangeEXT = this._pat._addressof_glTexBufferRangeEXT;
        if (addressof_glTexBufferRangeEXT == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glTexBufferRangeEXT"));
        }
        this.dispatch_glTexBufferRangeEXT1(n, n2, n3, n4, n5, addressof_glTexBufferRangeEXT);
    }
    
    private native void dispatch_glTexBufferRangeEXT1(final int p0, final int p1, final int p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisampleIMG = this._pat._addressof_glRenderbufferStorageMultisampleIMG;
        if (addressof_glRenderbufferStorageMultisampleIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisampleIMG"));
        }
        this.dispatch_glRenderbufferStorageMultisampleIMG1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisampleIMG);
    }
    
    private native void dispatch_glRenderbufferStorageMultisampleIMG1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTexture2DMultisampleIMG = this._pat._addressof_glFramebufferTexture2DMultisampleIMG;
        if (addressof_glFramebufferTexture2DMultisampleIMG == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTexture2DMultisampleIMG"));
        }
        this.dispatch_glFramebufferTexture2DMultisampleIMG1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTexture2DMultisampleIMG);
    }
    
    private native void dispatch_glFramebufferTexture2DMultisampleIMG1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glApplyFramebufferAttachmentCMAAINTEL() {
        final long addressof_glApplyFramebufferAttachmentCMAAINTEL = this._pat._addressof_glApplyFramebufferAttachmentCMAAINTEL;
        if (addressof_glApplyFramebufferAttachmentCMAAINTEL == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glApplyFramebufferAttachmentCMAAINTEL"));
        }
        this.dispatch_glApplyFramebufferAttachmentCMAAINTEL1(addressof_glApplyFramebufferAttachmentCMAAINTEL);
    }
    
    private native void dispatch_glApplyFramebufferAttachmentCMAAINTEL1(final long p0);
    
    @Override
    public void glBeginConditionalRender(final int n, final int n2) {
        final long addressof_glBeginConditionalRender = this._pat._addressof_glBeginConditionalRender;
        if (addressof_glBeginConditionalRender == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBeginConditionalRender"));
        }
        this.dispatch_glBeginConditionalRender1(n, n2, addressof_glBeginConditionalRender);
    }
    
    private native void dispatch_glBeginConditionalRender1(final int p0, final int p1, final long p2);
    
    @Override
    public void glEndConditionalRender() {
        final long addressof_glEndConditionalRender = this._pat._addressof_glEndConditionalRender;
        if (addressof_glEndConditionalRender == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEndConditionalRender"));
        }
        this.dispatch_glEndConditionalRender1(addressof_glEndConditionalRender);
    }
    
    private native void dispatch_glEndConditionalRender1(final long p0);
    
    @Override
    public void glSubpixelPrecisionBiasNV(final int n, final int n2) {
        final long addressof_glSubpixelPrecisionBiasNV = this._pat._addressof_glSubpixelPrecisionBiasNV;
        if (addressof_glSubpixelPrecisionBiasNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glSubpixelPrecisionBiasNV"));
        }
        this.dispatch_glSubpixelPrecisionBiasNV1(n, n2, addressof_glSubpixelPrecisionBiasNV);
    }
    
    private native void dispatch_glSubpixelPrecisionBiasNV1(final int p0, final int p1, final long p2);
    
    @Override
    public void glCopyBufferSubDataNV(final int n, final int n2, final long n3, final long n4, final long n5) {
        final long addressof_glCopyBufferSubDataNV = this._pat._addressof_glCopyBufferSubDataNV;
        if (addressof_glCopyBufferSubDataNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCopyBufferSubDataNV"));
        }
        this.dispatch_glCopyBufferSubDataNV1(n, n2, n3, n4, n5, addressof_glCopyBufferSubDataNV);
    }
    
    private native void dispatch_glCopyBufferSubDataNV1(final int p0, final int p1, final long p2, final long p3, final long p4, final long p5);
    
    @Override
    public void glCoverageMaskNV(final boolean b) {
        final long addressof_glCoverageMaskNV = this._pat._addressof_glCoverageMaskNV;
        if (addressof_glCoverageMaskNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCoverageMaskNV"));
        }
        this.dispatch_glCoverageMaskNV1(b, addressof_glCoverageMaskNV);
    }
    
    private native void dispatch_glCoverageMaskNV1(final boolean p0, final long p1);
    
    @Override
    public void glCoverageOperationNV(final int n) {
        final long addressof_glCoverageOperationNV = this._pat._addressof_glCoverageOperationNV;
        if (addressof_glCoverageOperationNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCoverageOperationNV"));
        }
        this.dispatch_glCoverageOperationNV1(n, addressof_glCoverageOperationNV);
    }
    
    private native void dispatch_glCoverageOperationNV1(final int p0, final long p1);
    
    @Override
    public void glDrawArraysInstancedNV(final int n, final int n2, final int n3, final int n4) {
        final long addressof_glDrawArraysInstancedNV = this._pat._addressof_glDrawArraysInstancedNV;
        if (addressof_glDrawArraysInstancedNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawArraysInstancedNV"));
        }
        this.dispatch_glDrawArraysInstancedNV1(n, n2, n3, n4, addressof_glDrawArraysInstancedNV);
    }
    
    private native void dispatch_glDrawArraysInstancedNV1(final int p0, final int p1, final int p2, final int p3, final long p4);
    
    @Override
    public void glDrawElementsInstancedNV(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glDrawElementsInstancedNV = this._pat._addressof_glDrawElementsInstancedNV;
        if (addressof_glDrawElementsInstancedNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDrawElementsInstancedNV"));
        }
        this.dispatch_glDrawElementsInstancedNV1(n, n2, n3, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, n4, addressof_glDrawElementsInstancedNV);
    }
    
    private native void dispatch_glDrawElementsInstancedNV1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final int p6, final long p7);
    
    @Override
    public void glFragmentCoverageColorNV(final int n) {
        final long addressof_glFragmentCoverageColorNV = this._pat._addressof_glFragmentCoverageColorNV;
        if (addressof_glFragmentCoverageColorNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFragmentCoverageColorNV"));
        }
        this.dispatch_glFragmentCoverageColorNV1(n, addressof_glFragmentCoverageColorNV);
    }
    
    private native void dispatch_glFragmentCoverageColorNV1(final int p0, final long p1);
    
    @Override
    public void glBlitFramebufferNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final long addressof_glBlitFramebufferNV = this._pat._addressof_glBlitFramebufferNV;
        if (addressof_glBlitFramebufferNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glBlitFramebufferNV"));
        }
        this.dispatch_glBlitFramebufferNV1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, addressof_glBlitFramebufferNV);
    }
    
    private native void dispatch_glBlitFramebufferNV1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final long p10);
    
    @Override
    public void glCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glCoverageModulationTableNV = this._pat._addressof_glCoverageModulationTableNV;
        if (addressof_glCoverageModulationTableNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCoverageModulationTableNV"));
        }
        this.dispatch_glCoverageModulationTableNV1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glCoverageModulationTableNV);
    }
    
    private native void dispatch_glCoverageModulationTableNV1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glCoverageModulationTableNV = this._pat._addressof_glCoverageModulationTableNV;
        if (addressof_glCoverageModulationTableNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCoverageModulationTableNV"));
        }
        this.dispatch_glCoverageModulationTableNV1(n, array, 4 * n2, false, addressof_glCoverageModulationTableNV);
    }
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetCoverageModulationTableNV = this._pat._addressof_glGetCoverageModulationTableNV;
        if (addressof_glGetCoverageModulationTableNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetCoverageModulationTableNV"));
        }
        this.dispatch_glGetCoverageModulationTableNV1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetCoverageModulationTableNV);
    }
    
    private native void dispatch_glGetCoverageModulationTableNV1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetCoverageModulationTableNV = this._pat._addressof_glGetCoverageModulationTableNV;
        if (addressof_glGetCoverageModulationTableNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetCoverageModulationTableNV"));
        }
        this.dispatch_glGetCoverageModulationTableNV1(n, array, 4 * n2, false, addressof_glGetCoverageModulationTableNV);
    }
    
    @Override
    public void glCoverageModulationNV(final int n) {
        final long addressof_glCoverageModulationNV = this._pat._addressof_glCoverageModulationNV;
        if (addressof_glCoverageModulationNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glCoverageModulationNV"));
        }
        this.dispatch_glCoverageModulationNV1(n, addressof_glCoverageModulationNV);
    }
    
    private native void dispatch_glCoverageModulationNV1(final int p0, final long p1);
    
    @Override
    public void glRenderbufferStorageMultisampleNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glRenderbufferStorageMultisampleNV = this._pat._addressof_glRenderbufferStorageMultisampleNV;
        if (addressof_glRenderbufferStorageMultisampleNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glRenderbufferStorageMultisampleNV"));
        }
        this.dispatch_glRenderbufferStorageMultisampleNV1(n, n2, n3, n4, n5, addressof_glRenderbufferStorageMultisampleNV);
    }
    
    private native void dispatch_glRenderbufferStorageMultisampleNV1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glVertexAttribDivisorNV(final int n, final int n2) {
        final long addressof_glVertexAttribDivisorNV = this._pat._addressof_glVertexAttribDivisorNV;
        if (addressof_glVertexAttribDivisorNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glVertexAttribDivisorNV"));
        }
        this.dispatch_glVertexAttribDivisorNV1(n, n2, addressof_glVertexAttribDivisorNV);
    }
    
    private native void dispatch_glVertexAttribDivisorNV1(final int p0, final int p1, final long p2);
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix2x3fvNV = this._pat._addressof_glUniformMatrix2x3fvNV;
        if (addressof_glUniformMatrix2x3fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x3fvNV"));
        }
        this.dispatch_glUniformMatrix2x3fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix2x3fvNV);
    }
    
    private native void dispatch_glUniformMatrix2x3fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix2x3fvNV = this._pat._addressof_glUniformMatrix2x3fvNV;
        if (addressof_glUniformMatrix2x3fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x3fvNV"));
        }
        this.dispatch_glUniformMatrix2x3fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix2x3fvNV);
    }
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix3x2fvNV = this._pat._addressof_glUniformMatrix3x2fvNV;
        if (addressof_glUniformMatrix3x2fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x2fvNV"));
        }
        this.dispatch_glUniformMatrix3x2fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix3x2fvNV);
    }
    
    private native void dispatch_glUniformMatrix3x2fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix3x2fvNV = this._pat._addressof_glUniformMatrix3x2fvNV;
        if (addressof_glUniformMatrix3x2fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x2fvNV"));
        }
        this.dispatch_glUniformMatrix3x2fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix3x2fvNV);
    }
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix2x4fvNV = this._pat._addressof_glUniformMatrix2x4fvNV;
        if (addressof_glUniformMatrix2x4fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x4fvNV"));
        }
        this.dispatch_glUniformMatrix2x4fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix2x4fvNV);
    }
    
    private native void dispatch_glUniformMatrix2x4fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix2x4fvNV = this._pat._addressof_glUniformMatrix2x4fvNV;
        if (addressof_glUniformMatrix2x4fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix2x4fvNV"));
        }
        this.dispatch_glUniformMatrix2x4fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix2x4fvNV);
    }
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix4x2fvNV = this._pat._addressof_glUniformMatrix4x2fvNV;
        if (addressof_glUniformMatrix4x2fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x2fvNV"));
        }
        this.dispatch_glUniformMatrix4x2fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix4x2fvNV);
    }
    
    private native void dispatch_glUniformMatrix4x2fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix4x2fvNV = this._pat._addressof_glUniformMatrix4x2fvNV;
        if (addressof_glUniformMatrix4x2fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x2fvNV"));
        }
        this.dispatch_glUniformMatrix4x2fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix4x2fvNV);
    }
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix3x4fvNV = this._pat._addressof_glUniformMatrix3x4fvNV;
        if (addressof_glUniformMatrix3x4fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x4fvNV"));
        }
        this.dispatch_glUniformMatrix3x4fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix3x4fvNV);
    }
    
    private native void dispatch_glUniformMatrix3x4fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix3x4fvNV = this._pat._addressof_glUniformMatrix3x4fvNV;
        if (addressof_glUniformMatrix3x4fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix3x4fvNV"));
        }
        this.dispatch_glUniformMatrix3x4fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix3x4fvNV);
    }
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glUniformMatrix4x3fvNV = this._pat._addressof_glUniformMatrix4x3fvNV;
        if (addressof_glUniformMatrix4x3fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x3fvNV"));
        }
        this.dispatch_glUniformMatrix4x3fvNV1(n, n2, b, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glUniformMatrix4x3fvNV);
    }
    
    private native void dispatch_glUniformMatrix4x3fvNV1(final int p0, final int p1, final boolean p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"value_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glUniformMatrix4x3fvNV = this._pat._addressof_glUniformMatrix4x3fvNV;
        if (addressof_glUniformMatrix4x3fvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glUniformMatrix4x3fvNV"));
        }
        this.dispatch_glUniformMatrix4x3fvNV1(n, n2, b, array, 4 * n3, false, addressof_glUniformMatrix4x3fvNV);
    }
    
    @Override
    public void glPolygonModeNV(final int n, final int n2) {
        final long addressof_glPolygonModeNV = this._pat._addressof_glPolygonModeNV;
        if (addressof_glPolygonModeNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glPolygonModeNV"));
        }
        this.dispatch_glPolygonModeNV1(n, n2, addressof_glPolygonModeNV);
    }
    
    private native void dispatch_glPolygonModeNV1(final int p0, final int p1, final long p2);
    
    @Override
    public void glReadBufferNV(final int n) {
        final long addressof_glReadBufferNV = this._pat._addressof_glReadBufferNV;
        if (addressof_glReadBufferNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glReadBufferNV"));
        }
        this.dispatch_glReadBufferNV1(n, addressof_glReadBufferNV);
    }
    
    private native void dispatch_glReadBufferNV1(final int p0, final long p1);
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glFramebufferSampleLocationsfvNV = this._pat._addressof_glFramebufferSampleLocationsfvNV;
        if (addressof_glFramebufferSampleLocationsfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferSampleLocationsfvNV"));
        }
        this.dispatch_glFramebufferSampleLocationsfvNV1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glFramebufferSampleLocationsfvNV);
    }
    
    private native void dispatch_glFramebufferSampleLocationsfvNV1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"v_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glFramebufferSampleLocationsfvNV = this._pat._addressof_glFramebufferSampleLocationsfvNV;
        if (addressof_glFramebufferSampleLocationsfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferSampleLocationsfvNV"));
        }
        this.dispatch_glFramebufferSampleLocationsfvNV1(n, n2, n3, array, 4 * n4, false, addressof_glFramebufferSampleLocationsfvNV);
    }
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glNamedFramebufferSampleLocationsfvNV = this._pat._addressof_glNamedFramebufferSampleLocationsfvNV;
        if (addressof_glNamedFramebufferSampleLocationsfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNamedFramebufferSampleLocationsfvNV"));
        }
        this.dispatch_glNamedFramebufferSampleLocationsfvNV1(n, n2, n3, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glNamedFramebufferSampleLocationsfvNV);
    }
    
    private native void dispatch_glNamedFramebufferSampleLocationsfvNV1(final int p0, final int p1, final int p2, final Object p3, final int p4, final boolean p5, final long p6);
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        if (array != null && array.length <= n4) {
            throw new GLException("array offset argument \"v_offset\" (" + n4 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glNamedFramebufferSampleLocationsfvNV = this._pat._addressof_glNamedFramebufferSampleLocationsfvNV;
        if (addressof_glNamedFramebufferSampleLocationsfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glNamedFramebufferSampleLocationsfvNV"));
        }
        this.dispatch_glNamedFramebufferSampleLocationsfvNV1(n, n2, n3, array, 4 * n4, false, addressof_glNamedFramebufferSampleLocationsfvNV);
    }
    
    @Override
    public void glResolveDepthValuesNV() {
        final long addressof_glResolveDepthValuesNV = this._pat._addressof_glResolveDepthValuesNV;
        if (addressof_glResolveDepthValuesNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glResolveDepthValuesNV"));
        }
        this.dispatch_glResolveDepthValuesNV1(addressof_glResolveDepthValuesNV);
    }
    
    private native void dispatch_glResolveDepthValuesNV1(final long p0);
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glViewportArrayvNV = this._pat._addressof_glViewportArrayvNV;
        if (addressof_glViewportArrayvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewportArrayvNV"));
        }
        this.dispatch_glViewportArrayvNV1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glViewportArrayvNV);
    }
    
    private native void dispatch_glViewportArrayvNV1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"v_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glViewportArrayvNV = this._pat._addressof_glViewportArrayvNV;
        if (addressof_glViewportArrayvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewportArrayvNV"));
        }
        this.dispatch_glViewportArrayvNV1(n, n2, array, 4 * n3, false, addressof_glViewportArrayvNV);
    }
    
    @Override
    public void glViewportIndexedfNV(final int n, final float n2, final float n3, final float n4, final float n5) {
        final long addressof_glViewportIndexedfNV = this._pat._addressof_glViewportIndexedfNV;
        if (addressof_glViewportIndexedfNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewportIndexedfNV"));
        }
        this.dispatch_glViewportIndexedfNV1(n, n2, n3, n4, n5, addressof_glViewportIndexedfNV);
    }
    
    private native void dispatch_glViewportIndexedfNV1(final int p0, final float p1, final float p2, final float p3, final float p4, final long p5);
    
    @Override
    public void glViewportIndexedfvNV(final int n, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glViewportIndexedfvNV = this._pat._addressof_glViewportIndexedfvNV;
        if (addressof_glViewportIndexedfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewportIndexedfvNV"));
        }
        this.dispatch_glViewportIndexedfvNV1(n, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glViewportIndexedfvNV);
    }
    
    private native void dispatch_glViewportIndexedfvNV1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glViewportIndexedfvNV(final int n, final float[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glViewportIndexedfvNV = this._pat._addressof_glViewportIndexedfvNV;
        if (addressof_glViewportIndexedfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glViewportIndexedfvNV"));
        }
        this.dispatch_glViewportIndexedfvNV1(n, array, 4 * n2, false, addressof_glViewportIndexedfvNV);
    }
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glScissorArrayvNV = this._pat._addressof_glScissorArrayvNV;
        if (addressof_glScissorArrayvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissorArrayvNV"));
        }
        this.dispatch_glScissorArrayvNV1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glScissorArrayvNV);
    }
    
    private native void dispatch_glScissorArrayvNV1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final int[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"v_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glScissorArrayvNV = this._pat._addressof_glScissorArrayvNV;
        if (addressof_glScissorArrayvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissorArrayvNV"));
        }
        this.dispatch_glScissorArrayvNV1(n, n2, array, 4 * n3, false, addressof_glScissorArrayvNV);
    }
    
    @Override
    public void glScissorIndexedNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glScissorIndexedNV = this._pat._addressof_glScissorIndexedNV;
        if (addressof_glScissorIndexedNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissorIndexedNV"));
        }
        this.dispatch_glScissorIndexedNV1(n, n2, n3, n4, n5, addressof_glScissorIndexedNV);
    }
    
    private native void dispatch_glScissorIndexedNV1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glScissorIndexedvNV(final int n, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glScissorIndexedvNV = this._pat._addressof_glScissorIndexedvNV;
        if (addressof_glScissorIndexedvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissorIndexedvNV"));
        }
        this.dispatch_glScissorIndexedvNV1(n, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glScissorIndexedvNV);
    }
    
    private native void dispatch_glScissorIndexedvNV1(final int p0, final Object p1, final int p2, final boolean p3, final long p4);
    
    @Override
    public void glScissorIndexedvNV(final int n, final int[] array, final int n2) {
        if (array != null && array.length <= n2) {
            throw new GLException("array offset argument \"v_offset\" (" + n2 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glScissorIndexedvNV = this._pat._addressof_glScissorIndexedvNV;
        if (addressof_glScissorIndexedvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glScissorIndexedvNV"));
        }
        this.dispatch_glScissorIndexedvNV1(n, array, 4 * n2, false, addressof_glScissorIndexedvNV);
    }
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glDepthRangeArrayfvNV = this._pat._addressof_glDepthRangeArrayfvNV;
        if (addressof_glDepthRangeArrayfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangeArrayfvNV"));
        }
        this.dispatch_glDepthRangeArrayfvNV1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glDepthRangeArrayfvNV);
    }
    
    private native void dispatch_glDepthRangeArrayfvNV1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"v_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glDepthRangeArrayfvNV = this._pat._addressof_glDepthRangeArrayfvNV;
        if (addressof_glDepthRangeArrayfvNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangeArrayfvNV"));
        }
        this.dispatch_glDepthRangeArrayfvNV1(n, n2, array, 4 * n3, false, addressof_glDepthRangeArrayfvNV);
    }
    
    @Override
    public void glDepthRangeIndexedfNV(final int n, final float n2, final float n3) {
        final long addressof_glDepthRangeIndexedfNV = this._pat._addressof_glDepthRangeIndexedfNV;
        if (addressof_glDepthRangeIndexedfNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDepthRangeIndexedfNV"));
        }
        this.dispatch_glDepthRangeIndexedfNV1(n, n2, n3, addressof_glDepthRangeIndexedfNV);
    }
    
    private native void dispatch_glDepthRangeIndexedfNV1(final int p0, final float p1, final float p2, final long p3);
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        final boolean direct = Buffers.isDirect(floatBuffer);
        final long addressof_glGetFloati_vNV = this._pat._addressof_glGetFloati_vNV;
        if (addressof_glGetFloati_vNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloati_vNV"));
        }
        this.dispatch_glGetFloati_vNV1(n, n2, direct ? floatBuffer : Buffers.getArray(floatBuffer), direct ? Buffers.getDirectBufferByteOffset(floatBuffer) : Buffers.getIndirectBufferByteOffset(floatBuffer), direct, addressof_glGetFloati_vNV);
    }
    
    private native void dispatch_glGetFloati_vNV1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final long p5);
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final float[] array, final int n3) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"data_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glGetFloati_vNV = this._pat._addressof_glGetFloati_vNV;
        if (addressof_glGetFloati_vNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetFloati_vNV"));
        }
        this.dispatch_glGetFloati_vNV1(n, n2, array, 4 * n3, false, addressof_glGetFloati_vNV);
    }
    
    @Override
    public void glEnableiNV(final int n, final int n2) {
        final long addressof_glEnableiNV = this._pat._addressof_glEnableiNV;
        if (addressof_glEnableiNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableiNV"));
        }
        this.dispatch_glEnableiNV1(n, n2, addressof_glEnableiNV);
    }
    
    private native void dispatch_glEnableiNV1(final int p0, final int p1, final long p2);
    
    @Override
    public void glDisableiNV(final int n, final int n2) {
        final long addressof_glDisableiNV = this._pat._addressof_glDisableiNV;
        if (addressof_glDisableiNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableiNV"));
        }
        this.dispatch_glDisableiNV1(n, n2, addressof_glDisableiNV);
    }
    
    private native void dispatch_glDisableiNV1(final int p0, final int p1, final long p2);
    
    @Override
    public boolean glIsEnablediNV(final int n, final int n2) {
        final long addressof_glIsEnablediNV = this._pat._addressof_glIsEnablediNV;
        if (addressof_glIsEnablediNV == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glIsEnablediNV"));
        }
        return this.dispatch_glIsEnablediNV1(n, n2, addressof_glIsEnablediNV);
    }
    
    private native boolean dispatch_glIsEnablediNV1(final int p0, final int p1, final long p2);
    
    @Override
    public void glFramebufferTextureMultiviewOVR(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final long addressof_glFramebufferTextureMultiviewOVR = this._pat._addressof_glFramebufferTextureMultiviewOVR;
        if (addressof_glFramebufferTextureMultiviewOVR == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glFramebufferTextureMultiviewOVR"));
        }
        this.dispatch_glFramebufferTextureMultiviewOVR1(n, n2, n3, n4, n5, n6, addressof_glFramebufferTextureMultiviewOVR);
    }
    
    private native void dispatch_glFramebufferTextureMultiviewOVR1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    @Override
    public void glAlphaFuncQCOM(final int n, final float n2) {
        final long addressof_glAlphaFuncQCOM = this._pat._addressof_glAlphaFuncQCOM;
        if (addressof_glAlphaFuncQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glAlphaFuncQCOM"));
        }
        this.dispatch_glAlphaFuncQCOM1(n, n2, addressof_glAlphaFuncQCOM);
    }
    
    private native void dispatch_glAlphaFuncQCOM1(final int p0, final float p1, final long p2);
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glGetDriverControlsQCOM = this._pat._addressof_glGetDriverControlsQCOM;
        if (addressof_glGetDriverControlsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlsQCOM"));
        }
        this.dispatch_glGetDriverControlsQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glGetDriverControlsQCOM);
    }
    
    private native void dispatch_glGetDriverControlsQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"num_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"driverControls_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetDriverControlsQCOM = this._pat._addressof_glGetDriverControlsQCOM;
        if (addressof_glGetDriverControlsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlsQCOM"));
        }
        this.dispatch_glGetDriverControlsQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glGetDriverControlsQCOM);
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(byteBuffer);
        final long addressof_glGetDriverControlStringQCOM = this._pat._addressof_glGetDriverControlStringQCOM;
        if (addressof_glGetDriverControlStringQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlStringQCOM"));
        }
        this.dispatch_glGetDriverControlStringQCOM1(n, n2, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, direct2 ? byteBuffer : Buffers.getArray(byteBuffer), direct2 ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct2, addressof_glGetDriverControlStringQCOM);
    }
    
    private native void dispatch_glGetDriverControlStringQCOM1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"length_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"driverControlString_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glGetDriverControlStringQCOM = this._pat._addressof_glGetDriverControlStringQCOM;
        if (addressof_glGetDriverControlStringQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glGetDriverControlStringQCOM"));
        }
        this.dispatch_glGetDriverControlStringQCOM1(n, n2, array, 4 * n3, false, array2, n4, false, addressof_glGetDriverControlStringQCOM);
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        final long addressof_glEnableDriverControlQCOM = this._pat._addressof_glEnableDriverControlQCOM;
        if (addressof_glEnableDriverControlQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEnableDriverControlQCOM"));
        }
        this.dispatch_glEnableDriverControlQCOM1(n, addressof_glEnableDriverControlQCOM);
    }
    
    private native void dispatch_glEnableDriverControlQCOM1(final int p0, final long p1);
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        final long addressof_glDisableDriverControlQCOM = this._pat._addressof_glDisableDriverControlQCOM;
        if (addressof_glDisableDriverControlQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glDisableDriverControlQCOM"));
        }
        this.dispatch_glDisableDriverControlQCOM1(n, addressof_glDisableDriverControlQCOM);
    }
    
    private native void dispatch_glDisableDriverControlQCOM1(final int p0, final long p1);
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetTexturesQCOM = this._pat._addressof_glExtGetTexturesQCOM;
        if (addressof_glExtGetTexturesQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexturesQCOM"));
        }
        this.dispatch_glExtGetTexturesQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetTexturesQCOM);
    }
    
    private native void dispatch_glExtGetTexturesQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"textures_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numTextures_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetTexturesQCOM = this._pat._addressof_glExtGetTexturesQCOM;
        if (addressof_glExtGetTexturesQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexturesQCOM"));
        }
        this.dispatch_glExtGetTexturesQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetTexturesQCOM);
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetBuffersQCOM = this._pat._addressof_glExtGetBuffersQCOM;
        if (addressof_glExtGetBuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBuffersQCOM"));
        }
        this.dispatch_glExtGetBuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetBuffersQCOM);
    }
    
    private native void dispatch_glExtGetBuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"buffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numBuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetBuffersQCOM = this._pat._addressof_glExtGetBuffersQCOM;
        if (addressof_glExtGetBuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBuffersQCOM"));
        }
        this.dispatch_glExtGetBuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetBuffersQCOM);
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetRenderbuffersQCOM = this._pat._addressof_glExtGetRenderbuffersQCOM;
        if (addressof_glExtGetRenderbuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetRenderbuffersQCOM"));
        }
        this.dispatch_glExtGetRenderbuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetRenderbuffersQCOM);
    }
    
    private native void dispatch_glExtGetRenderbuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"renderbuffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numRenderbuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetRenderbuffersQCOM = this._pat._addressof_glExtGetRenderbuffersQCOM;
        if (addressof_glExtGetRenderbuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetRenderbuffersQCOM"));
        }
        this.dispatch_glExtGetRenderbuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetRenderbuffersQCOM);
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetFramebuffersQCOM = this._pat._addressof_glExtGetFramebuffersQCOM;
        if (addressof_glExtGetFramebuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetFramebuffersQCOM"));
        }
        this.dispatch_glExtGetFramebuffersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetFramebuffersQCOM);
    }
    
    private native void dispatch_glExtGetFramebuffersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"framebuffers_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numFramebuffers_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetFramebuffersQCOM = this._pat._addressof_glExtGetFramebuffersQCOM;
        if (addressof_glExtGetFramebuffersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetFramebuffersQCOM"));
        }
        this.dispatch_glExtGetFramebuffersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetFramebuffersQCOM);
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final long addressof_glExtGetTexLevelParameterivQCOM = this._pat._addressof_glExtGetTexLevelParameterivQCOM;
        if (addressof_glExtGetTexLevelParameterivQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexLevelParameterivQCOM"));
        }
        this.dispatch_glExtGetTexLevelParameterivQCOM1(n, n2, n3, n4, direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, addressof_glExtGetTexLevelParameterivQCOM);
    }
    
    private native void dispatch_glExtGetTexLevelParameterivQCOM1(final int p0, final int p1, final int p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        if (array != null && array.length <= n5) {
            throw new GLException("array offset argument \"params_offset\" (" + n5 + ") equals or exceeds array length (" + array.length + ")");
        }
        final long addressof_glExtGetTexLevelParameterivQCOM = this._pat._addressof_glExtGetTexLevelParameterivQCOM;
        if (addressof_glExtGetTexLevelParameterivQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexLevelParameterivQCOM"));
        }
        this.dispatch_glExtGetTexLevelParameterivQCOM1(n, n2, n3, n4, array, 4 * n5, false, addressof_glExtGetTexLevelParameterivQCOM);
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        final long addressof_glExtTexObjectStateOverrideiQCOM = this._pat._addressof_glExtTexObjectStateOverrideiQCOM;
        if (addressof_glExtTexObjectStateOverrideiQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtTexObjectStateOverrideiQCOM"));
        }
        this.dispatch_glExtTexObjectStateOverrideiQCOM1(n, n2, n3, addressof_glExtTexObjectStateOverrideiQCOM);
    }
    
    private native void dispatch_glExtTexObjectStateOverrideiQCOM1(final int p0, final int p1, final int p2, final long p3);
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        final boolean direct = Buffers.isDirect(buffer);
        final long addressof_glExtGetTexSubImageQCOM = this._pat._addressof_glExtGetTexSubImageQCOM;
        if (addressof_glExtGetTexSubImageQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetTexSubImageQCOM"));
        }
        this.dispatch_glExtGetTexSubImageQCOM1(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, direct ? buffer : Buffers.getArray(buffer), direct ? Buffers.getDirectBufferByteOffset(buffer) : Buffers.getIndirectBufferByteOffset(buffer), direct, addressof_glExtGetTexSubImageQCOM);
    }
    
    private native void dispatch_glExtGetTexSubImageQCOM1(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final Object p10, final int p11, final boolean p12, final long p13);
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        if (!Buffers.isDirect(pointerBuffer)) {
            throw new GLException("Argument \"params\" is not a direct buffer");
        }
        final long addressof_glExtGetBufferPointervQCOM = this._pat._addressof_glExtGetBufferPointervQCOM;
        if (addressof_glExtGetBufferPointervQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetBufferPointervQCOM"));
        }
        this.dispatch_glExtGetBufferPointervQCOM0(n, (pointerBuffer != null) ? pointerBuffer.getBuffer() : null, Buffers.getDirectBufferByteOffset(pointerBuffer), addressof_glExtGetBufferPointervQCOM);
    }
    
    private native void dispatch_glExtGetBufferPointervQCOM0(final int p0, final Object p1, final int p2, final long p3);
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetShadersQCOM = this._pat._addressof_glExtGetShadersQCOM;
        if (addressof_glExtGetShadersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetShadersQCOM"));
        }
        this.dispatch_glExtGetShadersQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetShadersQCOM);
    }
    
    private native void dispatch_glExtGetShadersQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"shaders_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numShaders_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetShadersQCOM = this._pat._addressof_glExtGetShadersQCOM;
        if (addressof_glExtGetShadersQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetShadersQCOM"));
        }
        this.dispatch_glExtGetShadersQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetShadersQCOM);
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        final boolean direct = Buffers.isDirect(intBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer2);
        final long addressof_glExtGetProgramsQCOM = this._pat._addressof_glExtGetProgramsQCOM;
        if (addressof_glExtGetProgramsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramsQCOM"));
        }
        this.dispatch_glExtGetProgramsQCOM1(direct ? intBuffer : Buffers.getArray(intBuffer), direct ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct, n, direct2 ? intBuffer2 : Buffers.getArray(intBuffer2), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer2) : Buffers.getIndirectBufferByteOffset(intBuffer2), direct2, addressof_glExtGetProgramsQCOM);
    }
    
    private native void dispatch_glExtGetProgramsQCOM1(final Object p0, final int p1, final boolean p2, final int p3, final Object p4, final int p5, final boolean p6, final long p7);
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        if (array != null && array.length <= n) {
            throw new GLException("array offset argument \"programs_offset\" (" + n + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n3) {
            throw new GLException("array offset argument \"numPrograms_offset\" (" + n3 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetProgramsQCOM = this._pat._addressof_glExtGetProgramsQCOM;
        if (addressof_glExtGetProgramsQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramsQCOM"));
        }
        this.dispatch_glExtGetProgramsQCOM1(array, 4 * n, false, n2, array2, 4 * n3, false, addressof_glExtGetProgramsQCOM);
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        final long addressof_glExtIsProgramBinaryQCOM = this._pat._addressof_glExtIsProgramBinaryQCOM;
        if (addressof_glExtIsProgramBinaryQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtIsProgramBinaryQCOM"));
        }
        return this.dispatch_glExtIsProgramBinaryQCOM1(n, addressof_glExtIsProgramBinaryQCOM);
    }
    
    private native boolean dispatch_glExtIsProgramBinaryQCOM1(final int p0, final long p1);
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        final boolean direct = Buffers.isDirect(byteBuffer);
        final boolean direct2 = Buffers.isDirect(intBuffer);
        final long addressof_glExtGetProgramBinarySourceQCOM = this._pat._addressof_glExtGetProgramBinarySourceQCOM;
        if (addressof_glExtGetProgramBinarySourceQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramBinarySourceQCOM"));
        }
        this.dispatch_glExtGetProgramBinarySourceQCOM1(n, n2, direct ? byteBuffer : Buffers.getArray(byteBuffer), direct ? Buffers.getDirectBufferByteOffset(byteBuffer) : Buffers.getIndirectBufferByteOffset(byteBuffer), direct, direct2 ? intBuffer : Buffers.getArray(intBuffer), direct2 ? Buffers.getDirectBufferByteOffset(intBuffer) : Buffers.getIndirectBufferByteOffset(intBuffer), direct2, addressof_glExtGetProgramBinarySourceQCOM);
    }
    
    private native void dispatch_glExtGetProgramBinarySourceQCOM1(final int p0, final int p1, final Object p2, final int p3, final boolean p4, final Object p5, final int p6, final boolean p7, final long p8);
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        if (array != null && array.length <= n3) {
            throw new GLException("array offset argument \"source_offset\" (" + n3 + ") equals or exceeds array length (" + array.length + ")");
        }
        if (array2 != null && array2.length <= n4) {
            throw new GLException("array offset argument \"length_offset\" (" + n4 + ") equals or exceeds array length (" + array2.length + ")");
        }
        final long addressof_glExtGetProgramBinarySourceQCOM = this._pat._addressof_glExtGetProgramBinarySourceQCOM;
        if (addressof_glExtGetProgramBinarySourceQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glExtGetProgramBinarySourceQCOM"));
        }
        this.dispatch_glExtGetProgramBinarySourceQCOM1(n, n2, array, n3, false, array2, 4 * n4, false, addressof_glExtGetProgramBinarySourceQCOM);
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        final long addressof_glStartTilingQCOM = this._pat._addressof_glStartTilingQCOM;
        if (addressof_glStartTilingQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glStartTilingQCOM"));
        }
        this.dispatch_glStartTilingQCOM1(n, n2, n3, n4, n5, addressof_glStartTilingQCOM);
    }
    
    private native void dispatch_glStartTilingQCOM1(final int p0, final int p1, final int p2, final int p3, final int p4, final long p5);
    
    @Override
    public void glEndTilingQCOM(final int n) {
        final long addressof_glEndTilingQCOM = this._pat._addressof_glEndTilingQCOM;
        if (addressof_glEndTilingQCOM == 0L) {
            throw new GLException(String.format("Method \"%s\" not available", "glEndTilingQCOM"));
        }
        this.dispatch_glEndTilingQCOM1(n, addressof_glEndTilingQCOM);
    }
    
    private native void dispatch_glEndTilingQCOM1(final int p0, final long p1);
    
    @Override
    public GLProfile getGLProfile() {
        return this.glProfile;
    }
    
    @Override
    public final int getBoundBuffer(final int n) {
        return this.bufferStateTracker.getBoundBufferObject(n, this);
    }
    
    @Override
    public final GLBufferStorage getBufferStorage(final int n) {
        return this.bufferObjectTracker.getBufferStorage(n);
    }
    
    @Override
    public final boolean isVBOArrayBound() {
        return this.checkArrayVBOBound(false);
    }
    
    @Override
    public final boolean isVBOElementArrayBound() {
        return this.checkElementVBOBound(false);
    }
    
    @Override
    public final GL getDownstreamGL() throws GLException {
        return null;
    }
    
    @Override
    public final GL getRootGL() throws GLException {
        return this;
    }
    
    @Override
    public final boolean isGL() {
        return true;
    }
    
    @Override
    public final GL getGL() throws GLException {
        return this;
    }
    
    @Override
    public final boolean isFunctionAvailable(final String s) {
        return this._context.isFunctionAvailable(s);
    }
    
    @Override
    public final boolean isExtensionAvailable(final String s) {
        return this._context.isExtensionAvailable(s);
    }
    
    @Override
    public final Object getExtension(final String s) {
        return null;
    }
    
    @Override
    public final boolean hasBasicFBOSupport() {
        return this._context.hasBasicFBOSupport();
    }
    
    @Override
    public final boolean hasFullFBOSupport() {
        return this._context.hasFullFBOSupport();
    }
    
    @Override
    public final int getMaxRenderbufferSamples() {
        return this._context.getMaxRenderbufferSamples();
    }
    
    @Override
    public final boolean isTextureFormatBGRA8888Available() {
        return this._context.isTextureFormatBGRA8888Available();
    }
    
    @Override
    public final GLContext getContext() {
        return this._context;
    }
    
    @Override
    public final void setSwapInterval(final int swapInterval) {
        this._context.setSwapInterval(swapInterval);
    }
    
    @Override
    public final int getSwapInterval() {
        return this._context.getSwapInterval();
    }
    
    @Override
    public final Object getPlatformGLExtensions() {
        return this._context.getPlatformGLExtensions();
    }
    
    @Override
    public final int getBoundFramebuffer(final int n) {
        return this._context.getBoundFramebuffer(n);
    }
    
    @Override
    public final int getDefaultDrawFramebuffer() {
        return this._context.getDefaultDrawFramebuffer();
    }
    
    @Override
    public final int getDefaultReadFramebuffer() {
        return this._context.getDefaultReadFramebuffer();
    }
    
    @Override
    public final int getDefaultReadBuffer() {
        return this._context.getDefaultReadBuffer();
    }
    
    @Override
    public final void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.bufferObjectTracker.createBufferStorage(this.bufferStateTracker, this, n, n2, buffer, n3, 0, this.glBufferDataDispatch);
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        return this.bufferObjectTracker.unmapBuffer(this.bufferStateTracker, this, n, this.glUnmapBufferDispatch);
    }
    
    @Override
    public final ByteBuffer glMapBuffer(final int n, final int n2) {
        return this.mapBuffer(n, n2).getMappedBuffer();
    }
    
    @Override
    public final GLBufferStorage mapBuffer(final int n, final int n2) {
        return this.bufferObjectTracker.mapBuffer(this.bufferStateTracker, this, n, n2, this.glMapBufferDispatch);
    }
    
    @Override
    public final ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.mapBufferRange(n, n2, n3, n4).getMappedBuffer();
    }
    
    @Override
    public final GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.bufferObjectTracker.mapBuffer(this.bufferStateTracker, this, n, n2, n3, n4, this.glMapBufferRangeDispatch);
    }
    
    private native ByteBuffer newDirectByteBuffer(final long p0, final long p1);
    
    public GLES3Impl(final GLProfile glProfile, final GLContextImpl context) {
        this.glBufferDataDispatch = new GLBufferObjectTracker.CreateStorageDispatch() {
            @Override
            public final void create(final int n, final long n2, final Buffer buffer, final int n3) {
                GLES3Impl.this.glBufferDataDelegate(n, n2, buffer, n3);
            }
        };
        this.glUnmapBufferDispatch = new GLBufferObjectTracker.UnmapBufferDispatch() {
            @Override
            public final boolean unmap(final int n) {
                return GLES3Impl.this.glUnmapBufferDelegate(n);
            }
        };
        this.glMapBufferDispatch = new GLBufferObjectTracker.MapBufferAllDispatch() {
            @Override
            public final ByteBuffer allocNioByteBuffer(final long n, final long n2) {
                return GLES3Impl.this.newDirectByteBuffer(n, n2);
            }
            
            @Override
            public final long mapBuffer(final int n, final int n2) {
                return GLES3Impl.this.glMapBufferDelegate(n, n2);
            }
        };
        this.glMapBufferRangeDispatch = new GLBufferObjectTracker.MapBufferRangeDispatch() {
            @Override
            public final ByteBuffer allocNioByteBuffer(final long n, final long n2) {
                return GLES3Impl.this.newDirectByteBuffer(n, n2);
            }
            
            @Override
            public final long mapBuffer(final int n, final long n2, final long n3, final int n4) {
                return GLES3Impl.this.glMapBufferRangeDelegate(n, n2, n3, n4);
            }
        };
        this.imageSizeTemp = new int[1];
        this._context = context;
        this._pat = (GLES3ProcAddressTable)this._context.getGLProcAddressTable();
        this.bufferObjectTracker = context.getBufferObjectTracker();
        this.bufferStateTracker = context.getBufferStateTracker();
        this.glStateTracker = context.getGLStateTracker();
        this.glProfile = glProfile;
        this._isES3 = (glProfile.getImplName() == "GLES3");
    }
    
    public final void finalizeInit() {
    }
    
    private final int imageSizeInBytes(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        return GLBuffers.sizeof(this, this.imageSizeTemp, n, n2, n3, n4, n5, b);
    }
    
    @Override
    public final boolean isGL4bc() {
        return false;
    }
    
    @Override
    public final boolean isGL4() {
        return false;
    }
    
    @Override
    public final boolean isGL3bc() {
        return false;
    }
    
    @Override
    public final boolean isGL3() {
        return false;
    }
    
    @Override
    public final boolean isGL2() {
        return false;
    }
    
    @Override
    public final boolean isGLES1() {
        return false;
    }
    
    @Override
    public final boolean isGLES2() {
        return true;
    }
    
    @Override
    public final boolean isGLES3() {
        return this._isES3;
    }
    
    @Override
    public final boolean isGLES() {
        return true;
    }
    
    @Override
    public final boolean isGL2ES1() {
        return false;
    }
    
    @Override
    public final boolean isGL2ES2() {
        return true;
    }
    
    @Override
    public final boolean isGL2ES3() {
        return this._isES3;
    }
    
    @Override
    public final boolean isGL3ES3() {
        return this._isES3;
    }
    
    @Override
    public final boolean isGL4ES3() {
        return this._isES3;
    }
    
    @Override
    public final boolean isGL4core() {
        return false;
    }
    
    @Override
    public final boolean isGL3core() {
        return false;
    }
    
    @Override
    public final boolean isGLcore() {
        return true;
    }
    
    @Override
    public final boolean isGLES2Compatible() {
        return true;
    }
    
    @Override
    public final boolean isGLES3Compatible() {
        return this._isES3;
    }
    
    @Override
    public final boolean isGLES31Compatible() {
        return this._context.isGLES31Compatible();
    }
    
    @Override
    public final boolean isGLES32Compatible() {
        return this._context.isGLES32Compatible();
    }
    
    @Override
    public final boolean isGL2GL3() {
        return false;
    }
    
    @Override
    public final boolean hasGLSL() {
        return true;
    }
    
    @Override
    public boolean isNPOTTextureAvailable() {
        return true;
    }
    
    @Override
    public final GL4bc getGL4bc() throws GLException {
        throw new GLException("Not a GL4bc implementation");
    }
    
    @Override
    public final GL4 getGL4() throws GLException {
        throw new GLException("Not a GL4 implementation");
    }
    
    @Override
    public final GL3bc getGL3bc() throws GLException {
        throw new GLException("Not a GL3bc implementation");
    }
    
    @Override
    public final GL3 getGL3() throws GLException {
        throw new GLException("Not a GL3 implementation");
    }
    
    @Override
    public final GL2 getGL2() throws GLException {
        throw new GLException("Not a GL2 implementation");
    }
    
    @Override
    public final GLES1 getGLES1() throws GLException {
        throw new GLException("Not a GLES1 implementation");
    }
    
    @Override
    public final GLES2 getGLES2() throws GLException {
        return this;
    }
    
    @Override
    public final GLES3 getGLES3() throws GLException {
        if (!this._isES3) {
            throw new GLException("Not a GLES3 implementation");
        }
        return this;
    }
    
    @Override
    public final GL2ES1 getGL2ES1() throws GLException {
        throw new GLException("Not a GL2ES1 implementation");
    }
    
    @Override
    public final GL2ES2 getGL2ES2() throws GLException {
        return this;
    }
    
    @Override
    public final GL2ES3 getGL2ES3() throws GLException {
        if (!this._isES3) {
            throw new GLException("Not a GL2ES3 implementation");
        }
        return this;
    }
    
    @Override
    public final GL3ES3 getGL3ES3() throws GLException {
        if (!this._isES3) {
            throw new GLException("Not a GL3ES3 implementation");
        }
        return this;
    }
    
    @Override
    public final GL4ES3 getGL4ES3() throws GLException {
        if (!this._isES3) {
            throw new GLException("Not a GL4ES3 implementation");
        }
        return this;
    }
    
    @Override
    public final GL2GL3 getGL2GL3() throws GLException {
        throw new GLException("Not a GL2GL3 implementation");
    }
    
    private final boolean checkBufferObject(final boolean b, final boolean b2, final boolean b3, final int n, final String s, final boolean b4) {
        if (!b) {
            if (!b3) {
                return true;
            }
            if (b4) {
                throw new GLException("Required extensions not available to call this function");
            }
            return false;
        }
        else {
            final int boundBufferObject = this.bufferStateTracker.getBoundBufferObject(n, this);
            if (b3) {
                if (boundBufferObject != 0) {
                    return true;
                }
                if (b2) {
                    final int boundBufferObject2 = this.bufferStateTracker.getBoundBufferObject(34229, this);
                    if (boundBufferObject2 && this._context.getDefaultVAO() != boundBufferObject2) {
                        return true;
                    }
                }
                if (b4) {
                    throw new GLException(s + " must be bound to call this method");
                }
                return false;
            }
            else {
                if (boundBufferObject == 0) {
                    return true;
                }
                if (b4) {
                    throw new GLException(s + " must be unbound to call this method");
                }
                return false;
            }
        }
    }
    
    private final void validateCPUSourcedAvail() {
        if (!this._context.isCPUDataSourcingAvail()) {
            throw new GLException("CPU data sourcing n/a w/ " + this._context);
        }
    }
    
    private final boolean checkArrayVBOUnbound(final boolean b) {
        if (b) {
            this.validateCPUSourcedAvail();
        }
        return this.checkBufferObject(true, this._isES3, false, 34962, "array vertex_buffer_object", b);
    }
    
    private final boolean checkArrayVBOBound(final boolean b) {
        return this.checkBufferObject(true, this._isES3, true, 34962, "array vertex_buffer_object", b);
    }
    
    private final boolean checkElementVBOUnbound(final boolean b) {
        if (b) {
            this.validateCPUSourcedAvail();
        }
        return this.checkBufferObject(true, this._isES3, false, 34963, "element vertex_buffer_object", b);
    }
    
    private final boolean checkElementVBOBound(final boolean b) {
        return this.checkBufferObject(true, this._isES3, true, 34963, "element vertex_buffer_object", b);
    }
    
    private final boolean checkIndirectVBOUnbound(final boolean b) {
        if (b) {
            this.validateCPUSourcedAvail();
        }
        return this.checkBufferObject(true, this._isES3, false, 36671, "indirect vertex_buffer_object", b);
    }
    
    private final boolean checkIndirectVBOBound(final boolean b) {
        return this.checkBufferObject(true, this._isES3, true, 36671, "indirect vertex_buffer_object", b);
    }
    
    private final boolean checkUnpackPBOUnbound(final boolean b) {
        return this.checkBufferObject(this._isES3, false, false, 35052, "unpack pixel_buffer_object", b);
    }
    
    private final boolean checkUnpackPBOBound(final boolean b) {
        return this.checkBufferObject(this._isES3, false, true, 35052, "unpack pixel_buffer_object", b);
    }
    
    private final boolean checkPackPBOUnbound(final boolean b) {
        return this.checkBufferObject(this._isES3, false, false, 35051, "pack pixel_buffer_object", b);
    }
    
    private final boolean checkPackPBOBound(final boolean b) {
        return this.checkBufferObject(this._isES3, false, true, 35051, "pack pixel_buffer_object", b);
    }
    
    @Override
    public final boolean isPBOPackBound() {
        return this.checkPackPBOBound(false);
    }
    
    @Override
    public final boolean isPBOUnpackBound() {
        return this.checkUnpackPBOBound(false);
    }
    
    @Override
    public final void glClearDepth(final double n) {
        this.glClearDepthf((float)n);
    }
    
    @Override
    public final void glDepthRange(final double n, final double n2) {
        this.glDepthRangef((float)n, (float)n2);
    }
    
    @Override
    public final void glVertexAttribPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() == 0) {
            return;
        }
        if (glArrayData.isVBO()) {
            this.glVertexAttribPointer(glArrayData.getLocation(), glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getNormalized(), glArrayData.getStride(), glArrayData.getVBOOffset());
        }
        else {
            this.glVertexAttribPointer(glArrayData.getLocation(), glArrayData.getComponentCount(), glArrayData.getComponentType(), glArrayData.getNormalized(), glArrayData.getStride(), glArrayData.getBuffer());
        }
    }
    
    @Override
    public final void glUniform(final GLUniformData glUniformData) {
        int n = 0;
        if (glUniformData.isBuffer()) {
            final Buffer buffer = glUniformData.getBuffer();
            if (glUniformData.isMatrix()) {
                if (buffer instanceof FloatBuffer) {
                    switch (glUniformData.columns()) {
                        case 2: {
                            this.glUniformMatrix2fv(glUniformData.getLocation(), glUniformData.count(), false, (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 3: {
                            this.glUniformMatrix3fv(glUniformData.getLocation(), glUniformData.count(), false, (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 4: {
                            this.glUniformMatrix4fv(glUniformData.getLocation(), glUniformData.count(), false, (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                    }
                }
                if (n == 0) {
                    throw new GLException("glUniformMatrix only available for 2fv, 3fv and 4fv");
                }
            }
            else {
                if (buffer instanceof IntBuffer) {
                    switch (glUniformData.components()) {
                        case 1: {
                            this.glUniform1iv(glUniformData.getLocation(), glUniformData.count(), (IntBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 2: {
                            this.glUniform2iv(glUniformData.getLocation(), glUniformData.count(), (IntBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 3: {
                            this.glUniform3iv(glUniformData.getLocation(), glUniformData.count(), (IntBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 4: {
                            this.glUniform4iv(glUniformData.getLocation(), glUniformData.count(), (IntBuffer)buffer);
                            n = 1;
                            break;
                        }
                    }
                }
                else if (buffer instanceof FloatBuffer) {
                    switch (glUniformData.components()) {
                        case 1: {
                            this.glUniform1fv(glUniformData.getLocation(), glUniformData.count(), (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 2: {
                            this.glUniform2fv(glUniformData.getLocation(), glUniformData.count(), (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 3: {
                            this.glUniform3fv(glUniformData.getLocation(), glUniformData.count(), (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                        case 4: {
                            this.glUniform4fv(glUniformData.getLocation(), glUniformData.count(), (FloatBuffer)buffer);
                            n = 1;
                            break;
                        }
                    }
                }
                if (n == 0) {
                    throw new GLException("glUniform vector only available for 1[if]v 2[if]v, 3[if]v and 4[if]v");
                }
            }
        }
        else {
            final Object object = glUniformData.getObject();
            if (object instanceof Integer) {
                this.glUniform1i(glUniformData.getLocation(), (int)object);
                n = 1;
            }
            else if (object instanceof Float) {
                this.glUniform1f(glUniformData.getLocation(), (float)object);
                n = 1;
            }
            if (n == 0) {
                throw new GLException("glUniform atom only available for 1i and 1f");
            }
        }
    }
}
