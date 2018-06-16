// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl.fixedfunc;

import jogamp.opengl.Debug;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.GLArrayData;
import java.nio.Buffer;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLES3;
import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.GLES1;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GL4ES3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GL3bc;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLBufferStorage;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.GL;

public class FixedFuncImpl implements GL, GLMatrixFunc, GLPointerFunc, GLLightingFunc, GL2ES1
{
    public static final boolean DEBUG;
    private FixedFuncHook prologFixedFuncHook;
    private GL2ES2 downstreamGL2ES2;
    
    public FixedFuncImpl(final GL2ES2 downstreamGL2ES2, final FixedFuncHook prologFixedFuncHook) {
        if (downstreamGL2ES2 == null) {
            throw new IllegalArgumentException("null downstreamGL2ES2");
        }
        this.downstreamGL2ES2 = downstreamGL2ES2;
        this.prologFixedFuncHook = prologFixedFuncHook;
    }
    
    @Override
    public final GL getDownstreamGL() throws GLException {
        return this.downstreamGL2ES2;
    }
    
    @Override
    public int getBoundBuffer(final int n) {
        return this.downstreamGL2ES2.getBoundBuffer(n);
    }
    
    @Override
    public int getBoundFramebuffer(final int n) {
        return this.downstreamGL2ES2.getBoundFramebuffer(n);
    }
    
    @Override
    public GLBufferStorage getBufferStorage(final int n) {
        return this.downstreamGL2ES2.getBufferStorage(n);
    }
    
    @Override
    public GLContext getContext() {
        return this.downstreamGL2ES2.getContext();
    }
    
    @Override
    public int getDefaultDrawFramebuffer() {
        return this.downstreamGL2ES2.getDefaultDrawFramebuffer();
    }
    
    @Override
    public int getDefaultReadBuffer() {
        return this.downstreamGL2ES2.getDefaultReadBuffer();
    }
    
    @Override
    public int getDefaultReadFramebuffer() {
        return this.downstreamGL2ES2.getDefaultReadFramebuffer();
    }
    
    @Override
    public Object getExtension(final String s) {
        return this.downstreamGL2ES2.getExtension(s);
    }
    
    @Override
    public GL getGL() {
        return this;
    }
    
    @Override
    public GL2 getGL2() {
        throw new GLException("Not a GL2 implementation");
    }
    
    @Override
    public GL2ES1 getGL2ES1() {
        if (this.isGL2ES1()) {
            return this;
        }
        throw new GLException("Not a GL2ES1 implementation");
    }
    
    @Override
    public GL2ES2 getGL2ES2() {
        throw new GLException("Not a GL2ES2 implementation");
    }
    
    @Override
    public GL2ES3 getGL2ES3() {
        throw new GLException("Not a GL2ES3 implementation");
    }
    
    @Override
    public GL2GL3 getGL2GL3() {
        throw new GLException("Not a GL2GL3 implementation");
    }
    
    @Override
    public GL3 getGL3() {
        throw new GLException("Not a GL3 implementation");
    }
    
    @Override
    public GL3ES3 getGL3ES3() {
        throw new GLException("Not a GL3ES3 implementation");
    }
    
    @Override
    public GL3bc getGL3bc() {
        throw new GLException("Not a GL3bc implementation");
    }
    
    @Override
    public GL4 getGL4() {
        throw new GLException("Not a GL4 implementation");
    }
    
    @Override
    public GL4ES3 getGL4ES3() {
        throw new GLException("Not a GL4ES3 implementation");
    }
    
    @Override
    public GL4bc getGL4bc() {
        throw new GLException("Not a GL4bc implementation");
    }
    
    @Override
    public GLES1 getGLES1() {
        throw new GLException("Not a GLES1 implementation");
    }
    
    @Override
    public GLES2 getGLES2() {
        throw new GLException("Not a GLES2 implementation");
    }
    
    @Override
    public GLES3 getGLES3() {
        throw new GLException("Not a GLES3 implementation");
    }
    
    @Override
    public GLProfile getGLProfile() {
        return this.prologFixedFuncHook.getGLProfile();
    }
    
    @Override
    public int getMaxRenderbufferSamples() {
        return this.downstreamGL2ES2.getMaxRenderbufferSamples();
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.downstreamGL2ES2.getPlatformGLExtensions();
    }
    
    @Override
    public GL getRootGL() {
        return this.downstreamGL2ES2.getRootGL();
    }
    
    @Override
    public int getSwapInterval() {
        return this.downstreamGL2ES2.getSwapInterval();
    }
    
    @Override
    public void glActiveTexture(final int n) {
        this.prologFixedFuncHook.glActiveTexture(n);
    }
    
    @Override
    public void glAlphaFunc(final int n, final float n2) {
        this.prologFixedFuncHook.glAlphaFunc(n, n2);
    }
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        this.downstreamGL2ES2.glBindBuffer(n, n2);
    }
    
    @Override
    public void glBindFramebuffer(final int n, final int n2) {
        this.downstreamGL2ES2.glBindFramebuffer(n, n2);
    }
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        this.downstreamGL2ES2.glBindRenderbuffer(n, n2);
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.prologFixedFuncHook.glBindTexture(n, n2);
    }
    
    @Override
    public void glBlendEquation(final int n) {
        this.downstreamGL2ES2.glBlendEquation(n);
    }
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        this.downstreamGL2ES2.glBlendEquationSeparate(n, n2);
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.downstreamGL2ES2.glBlendFunc(n, n2);
    }
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glBlendFuncSeparate(n, n2, n3, n4);
    }
    
    @Override
    public void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.downstreamGL2ES2.glBufferData(n, n2, buffer, n3);
    }
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        this.downstreamGL2ES2.glBufferSubData(n, n2, n3, buffer);
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        return this.downstreamGL2ES2.glCheckFramebufferStatus(n);
    }
    
    @Override
    public void glClear(final int n) {
        this.downstreamGL2ES2.glClear(n);
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        this.downstreamGL2ES2.glClearColor(n, n2, n3, n4);
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.downstreamGL2ES2.glClearDepth(n);
    }
    
    @Override
    public void glClearDepthf(final float n) {
        this.downstreamGL2ES2.glClearDepthf(n);
    }
    
    @Override
    public void glClearStencil(final int n) {
        this.downstreamGL2ES2.glClearStencil(n);
    }
    
    @Override
    public void glClientActiveTexture(final int n) {
        this.prologFixedFuncHook.glClientActiveTexture(n);
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, final float n4) {
        this.prologFixedFuncHook.glColor4f(n, n2, n3, n4);
    }
    
    @Override
    public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        this.prologFixedFuncHook.glColor4ub(b, b2, b3, b4);
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.downstreamGL2ES2.glColorMask(b, b2, b3, b4);
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        this.prologFixedFuncHook.glColorPointer(n, n2, n3, n4);
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.prologFixedFuncHook.glColorPointer(n, n2, n3, buffer);
    }
    
    @Override
    public void glColorPointer(final GLArrayData glArrayData) {
        this.prologFixedFuncHook.glColorPointer(glArrayData);
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.downstreamGL2ES2.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.downstreamGL2ES2.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.downstreamGL2ES2.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.downstreamGL2ES2.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.downstreamGL2ES2.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.downstreamGL2ES2.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    @Override
    public void glCullFace(final int n) {
        this.downstreamGL2ES2.glCullFace(n);
    }
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glDeleteBuffers(n, intBuffer);
    }
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glDeleteBuffers(n, array, n2);
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glDeleteFramebuffers(n, intBuffer);
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glDeleteFramebuffers(n, array, n2);
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glDeleteRenderbuffers(n, array, n2);
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glDeleteRenderbuffers(n, intBuffer);
    }
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glDeleteTextures(n, array, n2);
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glDeleteTextures(n, intBuffer);
    }
    
    @Override
    public void glDepthFunc(final int n) {
        this.downstreamGL2ES2.glDepthFunc(n);
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        this.downstreamGL2ES2.glDepthMask(b);
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.downstreamGL2ES2.glDepthRange(n, n2);
    }
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        this.downstreamGL2ES2.glDepthRangef(n, n2);
    }
    
    @Override
    public void glDisable(final int n) {
        this.prologFixedFuncHook.glDisable(n);
    }
    
    @Override
    public void glDisableClientState(final int n) {
        this.prologFixedFuncHook.glDisableClientState(n);
    }
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.prologFixedFuncHook.glDrawArrays(n, n2, n3);
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.prologFixedFuncHook.glDrawElements(n, n2, n3, n4);
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.prologFixedFuncHook.glDrawElements(n, n2, n3, buffer);
    }
    
    @Override
    public void glEnable(final int n) {
        this.prologFixedFuncHook.glEnable(n);
    }
    
    @Override
    public void glEnableClientState(final int n) {
        this.prologFixedFuncHook.glEnableClientState(n);
    }
    
    @Override
    public void glFinish() {
        this.downstreamGL2ES2.glFinish();
    }
    
    @Override
    public void glFlush() {
        this.downstreamGL2ES2.glFlush();
    }
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        this.downstreamGL2ES2.glFlushMappedBufferRange(n, n2, n3);
    }
    
    @Override
    public void glFogf(final int n, final float n2) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glFogf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        }
    }
    
    @Override
    public void glFogfv(final int n, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glFogfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glFogfv(final int n, final float[] array, final int n2) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glFogfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glFramebufferRenderbuffer(n, n2, n3, n4);
    }
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.downstreamGL2ES2.glFramebufferTexture2D(n, n2, n3, n4, n5);
    }
    
    @Override
    public void glFrontFace(final int n) {
        this.downstreamGL2ES2.glFrontFace(n);
    }
    
    @Override
    public void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.prologFixedFuncHook.glFrustum(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.prologFixedFuncHook.glFrustumf(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGenBuffers(n, intBuffer);
    }
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glGenBuffers(n, array, n2);
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGenFramebuffers(n, intBuffer);
    }
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glGenFramebuffers(n, array, n2);
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGenRenderbuffers(n, intBuffer);
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glGenRenderbuffers(n, array, n2);
    }
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        this.downstreamGL2ES2.glGenTextures(n, array, n2);
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGenTextures(n, intBuffer);
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        this.downstreamGL2ES2.glGenerateMipmap(n);
    }
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        this.downstreamGL2ES2.glGetBooleanv(n, array, n2);
    }
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        this.downstreamGL2ES2.glGetBooleanv(n, byteBuffer);
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGetBufferParameteriv(n, n2, intBuffer);
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.downstreamGL2ES2.glGetBufferParameteriv(n, n2, array, n3);
    }
    
    @Override
    public int glGetError() {
        return this.downstreamGL2ES2.glGetError();
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glGetFloatv(n, floatBuffer);
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        this.prologFixedFuncHook.glGetFloatv(n, array, n2);
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.downstreamGL2ES2.glGetFramebufferAttachmentParameteriv(n, n2, n3, array, n4);
    }
    
    @Override
    public int glGetGraphicsResetStatus() {
        return this.downstreamGL2ES2.glGetGraphicsResetStatus();
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        this.prologFixedFuncHook.glGetIntegerv(n, intBuffer);
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        this.prologFixedFuncHook.glGetIntegerv(n, array, n2);
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final float[] array, final int n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGetRenderbufferParameteriv(n, n2, intBuffer);
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.downstreamGL2ES2.glGetRenderbufferParameteriv(n, n2, array, n3);
    }
    
    @Override
    public String glGetString(final int n) {
        return this.downstreamGL2ES2.glGetString(n);
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glGetTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.prologFixedFuncHook.glGetTexEnviv(n, n2, array, n3);
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.prologFixedFuncHook.glGetTexEnviv(n, n2, intBuffer);
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.downstreamGL2ES2.glGetTexParameterfv(n, n2, array, n3);
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.downstreamGL2ES2.glGetTexParameterfv(n, n2, floatBuffer);
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.downstreamGL2ES2.glGetTexParameteriv(n, n2, array, n3);
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGetTexParameteriv(n, n2, intBuffer);
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.downstreamGL2ES2.glGetnUniformfv(n, n2, n3, array, n4);
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.downstreamGL2ES2.glGetnUniformfv(n, n2, n3, floatBuffer);
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.downstreamGL2ES2.glGetnUniformiv(n, n2, n3, array, n4);
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glGetnUniformiv(n, n2, n3, intBuffer);
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        this.downstreamGL2ES2.glHint(n, n2);
    }
    
    @Override
    public boolean glIsBuffer(final int n) {
        return this.downstreamGL2ES2.glIsBuffer(n);
    }
    
    @Override
    public boolean glIsEnabled(final int n) {
        return this.downstreamGL2ES2.glIsEnabled(n);
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        return this.downstreamGL2ES2.glIsFramebuffer(n);
    }
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        return this.downstreamGL2ES2.glIsRenderbuffer(n);
    }
    
    @Override
    public boolean glIsTexture(final int n) {
        return this.downstreamGL2ES2.glIsTexture(n);
    }
    
    @Override
    public void glLightModelf(final int n, final float n2) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glLightModelf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        }
    }
    
    @Override
    public void glLightModelfv(final int n, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glLightModelfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glLightModelfv(final int n, final float[] array, final int n2) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glLightModelfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glLightf(final int n, final int n2, final float n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glLightf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        }
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glLightfv(n, n2, floatBuffer);
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.prologFixedFuncHook.glLightfv(n, n2, array, n3);
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.downstreamGL2ES2.glLineWidth(n);
    }
    
    @Override
    public void glLoadIdentity() {
        this.prologFixedFuncHook.glLoadIdentity();
    }
    
    @Override
    public void glLoadMatrixf(final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glLoadMatrixf(floatBuffer);
    }
    
    @Override
    public void glLoadMatrixf(final float[] array, final int n) {
        this.prologFixedFuncHook.glLoadMatrixf(array, n);
    }
    
    @Override
    public void glLogicOp(final int n) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glLogicOp(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        }
    }
    
    @Override
    public ByteBuffer glMapBuffer(final int n, final int n2) {
        return this.downstreamGL2ES2.glMapBuffer(n, n2);
    }
    
    @Override
    public ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.downstreamGL2ES2.glMapBufferRange(n, n2, n3, n4);
    }
    
    @Override
    public void glMaterialf(final int n, final int n2, final float n3) {
        this.prologFixedFuncHook.glMaterialf(n, n2, n3);
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.prologFixedFuncHook.glMaterialfv(n, n2, array, n3);
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glMaterialfv(n, n2, floatBuffer);
    }
    
    @Override
    public void glMatrixMode(final int n) {
        this.prologFixedFuncHook.glMatrixMode(n);
    }
    
    @Override
    public void glMultMatrixf(final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glMultMatrixf(floatBuffer);
    }
    
    @Override
    public void glMultMatrixf(final float[] array, final int n) {
        this.prologFixedFuncHook.glMultMatrixf(array, n);
    }
    
    @Override
    public void glMultiTexCoord4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glMultiTexCoord4f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        }
    }
    
    @Override
    public void glNormal3f(final float n, final float n2, final float n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glNormal3f(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        }
    }
    
    @Override
    public void glNormalPointer(final GLArrayData glArrayData) {
        this.prologFixedFuncHook.glNormalPointer(glArrayData);
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final Buffer buffer) {
        this.prologFixedFuncHook.glNormalPointer(n, n2, buffer);
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final long n3) {
        this.prologFixedFuncHook.glNormalPointer(n, n2, n3);
    }
    
    @Override
    public void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.prologFixedFuncHook.glOrtho(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.prologFixedFuncHook.glOrthof(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.downstreamGL2ES2.glPixelStorei(n, n2);
    }
    
    @Override
    public void glPointParameterf(final int n, final float n2) {
        this.prologFixedFuncHook.glPointParameterf(n, n2);
    }
    
    @Override
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        this.prologFixedFuncHook.glPointParameterfv(n, array, n2);
    }
    
    @Override
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        this.prologFixedFuncHook.glPointParameterfv(n, floatBuffer);
    }
    
    @Override
    public void glPointSize(final float n) {
        this.prologFixedFuncHook.glPointSize(n);
    }
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        this.downstreamGL2ES2.glPolygonOffset(n, n2);
    }
    
    @Override
    public void glPopMatrix() {
        this.prologFixedFuncHook.glPopMatrix();
    }
    
    @Override
    public void glPushMatrix() {
        this.prologFixedFuncHook.glPushMatrix();
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.downstreamGL2ES2.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.downstreamGL2ES2.glReadPixels(n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.downstreamGL2ES2.glReadnPixels(n, n2, n3, n4, n5, n6, n7, buffer);
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glRenderbufferStorage(n, n2, n3, n4);
    }
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.downstreamGL2ES2.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.prologFixedFuncHook.glRotatef(n, n2, n3, n4);
    }
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        this.downstreamGL2ES2.glSampleCoverage(n, b);
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        this.prologFixedFuncHook.glScalef(n, n2, n3);
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glScissor(n, n2, n3, n4);
    }
    
    @Override
    public void glShadeModel(final int n) {
        this.prologFixedFuncHook.glShadeModel(n);
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        this.downstreamGL2ES2.glStencilFunc(n, n2, n3);
    }
    
    @Override
    public void glStencilMask(final int n) {
        this.downstreamGL2ES2.glStencilMask(n);
    }
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        this.downstreamGL2ES2.glStencilOp(n, n2, n3);
    }
    
    @Override
    public void glTexCoordPointer(final GLArrayData glArrayData) {
        this.prologFixedFuncHook.glTexCoordPointer(glArrayData);
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        this.prologFixedFuncHook.glTexCoordPointer(n, n2, n3, n4);
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.prologFixedFuncHook.glTexCoordPointer(n, n2, n3, buffer);
    }
    
    @Override
    public void glTexEnvf(final int n, final int n2, final float n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glTexEnvf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        }
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        }
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        this.prologFixedFuncHook.glTexEnvi(n, n2, n3);
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        }
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        if (FixedFuncImpl.DEBUG) {
            System.out.println("WARNING: No prolog, no downstream, empty: glTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        }
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.prologFixedFuncHook.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.prologFixedFuncHook.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        this.downstreamGL2ES2.glTexParameterf(n, n2, n3);
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.downstreamGL2ES2.glTexParameterfv(n, n2, array, n3);
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.downstreamGL2ES2.glTexParameterfv(n, n2, floatBuffer);
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        this.downstreamGL2ES2.glTexParameteri(n, n2, n3);
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.downstreamGL2ES2.glTexParameteriv(n, n2, intBuffer);
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.downstreamGL2ES2.glTexParameteriv(n, n2, array, n3);
    }
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glTexStorage1D(n, n2, n3, n4);
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.downstreamGL2ES2.glTexStorage2D(n, n2, n3, n4, n5);
    }
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.downstreamGL2ES2.glTexStorage3D(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.downstreamGL2ES2.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.downstreamGL2ES2.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
    }
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.downstreamGL2ES2.glTextureStorage1DEXT(n, n2, n3, n4, n5);
    }
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.downstreamGL2ES2.glTextureStorage2DEXT(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.downstreamGL2ES2.glTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        this.prologFixedFuncHook.glTranslatef(n, n2, n3);
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        return this.downstreamGL2ES2.glUnmapBuffer(n);
    }
    
    @Override
    public void glVertexPointer(final GLArrayData glArrayData) {
        this.prologFixedFuncHook.glVertexPointer(glArrayData);
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        this.prologFixedFuncHook.glVertexPointer(n, n2, n3, n4);
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.prologFixedFuncHook.glVertexPointer(n, n2, n3, buffer);
    }
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        this.downstreamGL2ES2.glViewport(n, n2, n3, n4);
    }
    
    @Override
    public boolean hasBasicFBOSupport() {
        return this.downstreamGL2ES2.hasBasicFBOSupport();
    }
    
    @Override
    public boolean hasFullFBOSupport() {
        return this.downstreamGL2ES2.hasFullFBOSupport();
    }
    
    @Override
    public boolean hasGLSL() {
        return this.downstreamGL2ES2.hasGLSL();
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this.downstreamGL2ES2.isExtensionAvailable(s);
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this.downstreamGL2ES2.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isGL() {
        return true;
    }
    
    @Override
    public boolean isGL2() {
        return false;
    }
    
    @Override
    public boolean isGL2ES1() {
        return true;
    }
    
    @Override
    public boolean isGL2ES2() {
        return false;
    }
    
    @Override
    public boolean isGL2ES3() {
        return false;
    }
    
    @Override
    public boolean isGL2GL3() {
        return false;
    }
    
    @Override
    public boolean isGL3() {
        return false;
    }
    
    @Override
    public boolean isGL3ES3() {
        return false;
    }
    
    @Override
    public boolean isGL3bc() {
        return false;
    }
    
    @Override
    public boolean isGL3core() {
        return this.prologFixedFuncHook.isGL3core();
    }
    
    @Override
    public boolean isGL4() {
        return false;
    }
    
    @Override
    public boolean isGL4ES3() {
        return false;
    }
    
    @Override
    public boolean isGL4bc() {
        return false;
    }
    
    @Override
    public boolean isGL4core() {
        return this.prologFixedFuncHook.isGL4core();
    }
    
    @Override
    public boolean isGLES() {
        return this.downstreamGL2ES2.isGLES();
    }
    
    @Override
    public boolean isGLES1() {
        return false;
    }
    
    @Override
    public boolean isGLES2() {
        return false;
    }
    
    @Override
    public boolean isGLES2Compatible() {
        return this.prologFixedFuncHook.isGLES2Compatible();
    }
    
    @Override
    public boolean isGLES3() {
        return false;
    }
    
    @Override
    public boolean isGLES31Compatible() {
        return this.downstreamGL2ES2.isGLES31Compatible();
    }
    
    @Override
    public boolean isGLES32Compatible() {
        return this.downstreamGL2ES2.isGLES32Compatible();
    }
    
    @Override
    public boolean isGLES3Compatible() {
        return this.prologFixedFuncHook.isGLES3Compatible();
    }
    
    @Override
    public boolean isGLcore() {
        return this.prologFixedFuncHook.isGLcore();
    }
    
    @Override
    public boolean isNPOTTextureAvailable() {
        return this.downstreamGL2ES2.isNPOTTextureAvailable();
    }
    
    @Override
    public boolean isTextureFormatBGRA8888Available() {
        return this.downstreamGL2ES2.isTextureFormatBGRA8888Available();
    }
    
    @Override
    public boolean isVBOArrayBound() {
        return this.downstreamGL2ES2.isVBOArrayBound();
    }
    
    @Override
    public boolean isVBOElementArrayBound() {
        return this.downstreamGL2ES2.isVBOElementArrayBound();
    }
    
    @Override
    public GLBufferStorage mapBuffer(final int n, final int n2) {
        return this.downstreamGL2ES2.mapBuffer(n, n2);
    }
    
    @Override
    public GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        return this.downstreamGL2ES2.mapBufferRange(n, n2, n3, n4);
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        this.downstreamGL2ES2.setSwapInterval(swapInterval);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FixedFuncImpl [this 0x" + Integer.toHexString(this.hashCode()) + " implementing com.jogamp.opengl.GL2ES1,\n\t");
        sb.append(" prolog: " + this.prologFixedFuncHook.toString() + ",\n\t");
        sb.append(" downstream: " + this.downstreamGL2ES2.toString() + "\n\t]");
        return sb.toString();
    }
    
    static {
        DEBUG = Debug.debug("FixedFuncImpl");
    }
}
