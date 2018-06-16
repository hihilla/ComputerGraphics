// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class DebugGLES3 implements GLES2, GL4ES3, GLES3
{
    public static final boolean DEBUG;
    private GLContext _context;
    private GLES3 downstreamGLES3;
    
    public DebugGLES3(final GLES3 downstreamGLES3) {
        if (downstreamGLES3 == null) {
            throw new IllegalArgumentException("null downstreamGLES3");
        }
        this.downstreamGLES3 = downstreamGLES3;
        this._context = downstreamGLES3.getContext();
    }
    
    @Override
    public final GL getDownstreamGL() throws GLException {
        return this.downstreamGLES3;
    }
    
    @Override
    public int getBoundBuffer(final int n) {
        return this.downstreamGLES3.getBoundBuffer(n);
    }
    
    @Override
    public int getBoundFramebuffer(final int n) {
        return this.downstreamGLES3.getBoundFramebuffer(n);
    }
    
    @Override
    public GLBufferStorage getBufferStorage(final int n) {
        return this.downstreamGLES3.getBufferStorage(n);
    }
    
    @Override
    public GLContext getContext() {
        return this.downstreamGLES3.getContext();
    }
    
    @Override
    public int getDefaultDrawFramebuffer() {
        return this.downstreamGLES3.getDefaultDrawFramebuffer();
    }
    
    @Override
    public int getDefaultReadBuffer() {
        return this.downstreamGLES3.getDefaultReadBuffer();
    }
    
    @Override
    public int getDefaultReadFramebuffer() {
        return this.downstreamGLES3.getDefaultReadFramebuffer();
    }
    
    @Override
    public Object getExtension(final String s) {
        return this.downstreamGLES3.getExtension(s);
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
        throw new GLException("Not a GL2ES1 implementation");
    }
    
    @Override
    public GL2ES2 getGL2ES2() {
        if (this.isGL2ES2()) {
            return this;
        }
        throw new GLException("Not a GL2ES2 implementation");
    }
    
    @Override
    public GL2ES3 getGL2ES3() {
        if (this.isGL2ES3()) {
            return this;
        }
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
        if (this.isGL3ES3()) {
            return this;
        }
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
        if (this.isGL4ES3()) {
            return this;
        }
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
        if (this.isGLES2()) {
            return this;
        }
        throw new GLException("Not a GLES2 implementation");
    }
    
    @Override
    public GLES3 getGLES3() {
        if (this.isGLES3()) {
            return this;
        }
        throw new GLException("Not a GLES3 implementation");
    }
    
    @Override
    public GLProfile getGLProfile() {
        return this.downstreamGLES3.getGLProfile();
    }
    
    @Override
    public int getMaxRenderbufferSamples() {
        return this.downstreamGLES3.getMaxRenderbufferSamples();
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.downstreamGLES3.getPlatformGLExtensions();
    }
    
    @Override
    public GL getRootGL() {
        return this.downstreamGLES3.getRootGL();
    }
    
    @Override
    public int getSwapInterval() {
        return this.downstreamGLES3.getSwapInterval();
    }
    
    @Override
    public void glActiveShaderProgram(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glActiveShaderProgram(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glActiveShaderProgram", n, n2);
        }
    }
    
    @Override
    public void glActiveTexture(final int n) {
        this.checkContext();
        this.downstreamGLES3.glActiveTexture(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glActiveTexture", n);
        }
    }
    
    @Override
    public void glAlphaFuncQCOM(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES3.glAlphaFuncQCOM(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glAlphaFuncQCOM", n, n2);
        }
    }
    
    @Override
    public void glApplyFramebufferAttachmentCMAAINTEL() {
        this.checkContext();
        this.downstreamGLES3.glApplyFramebufferAttachmentCMAAINTEL();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glApplyFramebufferAttachmentCMAAINTEL");
        }
    }
    
    @Override
    public void glAttachShader(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glAttachShader(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glAttachShader", n, n2);
        }
    }
    
    @Override
    public void glBeginConditionalRender(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBeginConditionalRender(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBeginConditionalRender", n, n2);
        }
    }
    
    @Override
    public void glBeginQuery(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBeginQuery(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBeginQuery", n, n2);
        }
    }
    
    @Override
    public void glBeginTransformFeedback(final int n) {
        this.checkContext();
        this.downstreamGLES3.glBeginTransformFeedback(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBeginTransformFeedback", n);
        }
    }
    
    @Override
    public void glBindAttribLocation(final int n, final int n2, final String s) {
        this.checkContext();
        this.downstreamGLES3.glBindAttribLocation(n, n2, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.lang.String> %s)", "glBindAttribLocation", n, n2, s);
        }
    }
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindBuffer", n, n2);
        }
    }
    
    @Override
    public void glBindBufferBase(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBindBufferBase(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBindBufferBase", n, n2, n3);
        }
    }
    
    @Override
    public void glBindBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glBindBufferRange(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <long> %s)", "glBindBufferRange", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final byte[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBindFragDataLocationEXT(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glBindFragDataLocationEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glBindFragDataLocationEXT(n, n2, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glBindFragDataLocationEXT", n, n2, byteBuffer);
        }
    }
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glBindFragDataLocationIndexedEXT(n, n2, n3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glBindFragDataLocationIndexedEXT", n, n2, n3, byteBuffer);
        }
    }
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glBindFragDataLocationIndexedEXT(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glBindFragDataLocationIndexedEXT", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glBindFramebuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindFramebuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindFramebuffer", n, n2);
        }
    }
    
    @Override
    public void glBindImageTexture(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glBindImageTexture(n, n2, n3, b, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBindImageTexture", n, n2, n3, b, n4, n5, n6);
        }
    }
    
    @Override
    public void glBindProgramPipeline(final int n) {
        this.checkContext();
        this.downstreamGLES3.glBindProgramPipeline(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBindProgramPipeline", n);
        }
    }
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindRenderbuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindRenderbuffer", n, n2);
        }
    }
    
    @Override
    public void glBindSampler(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindSampler(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindSampler", n, n2);
        }
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindTexture(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindTexture", n, n2);
        }
    }
    
    @Override
    public void glBindTransformFeedback(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBindTransformFeedback(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindTransformFeedback", n, n2);
        }
    }
    
    @Override
    public void glBindVertexArray(final int n) {
        this.checkContext();
        this.downstreamGLES3.glBindVertexArray(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBindVertexArray", n);
        }
    }
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        this.checkContext();
        this.downstreamGLES3.glBindVertexArrayOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBindVertexArrayOES", n);
        }
    }
    
    @Override
    public void glBindVertexBuffer(final int n, final int n2, final long n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glBindVertexBuffer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X)", "glBindVertexBuffer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glBlendBarrier() {
        this.checkContext();
        this.downstreamGLES3.glBlendBarrier();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glBlendBarrier");
        }
    }
    
    @Override
    public void glBlendColor(final float n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES3.glBlendColor(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s)", "glBlendColor", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glBlendEquation(final int n) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquation(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBlendEquation", n);
        }
    }
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationSeparate(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendEquationSeparate", n, n2);
        }
    }
    
    @Override
    public void glBlendEquationSeparatei(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationSeparatei(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendEquationSeparatei", n, n2, n3);
        }
    }
    
    @Override
    public void glBlendEquationSeparateiEXT(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationSeparateiEXT(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendEquationSeparateiEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glBlendEquationSeparateiOES(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationSeparateiOES(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendEquationSeparateiOES", n, n2, n3);
        }
    }
    
    @Override
    public void glBlendEquationi(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationi(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendEquationi", n, n2);
        }
    }
    
    @Override
    public void glBlendEquationiEXT(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationiEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendEquationiEXT", n, n2);
        }
    }
    
    @Override
    public void glBlendEquationiOES(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBlendEquationiOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendEquationiOES", n, n2);
        }
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glBlendFunc(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendFunc", n, n2);
        }
    }
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glBlendFuncSeparate(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFuncSeparate", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glBlendFuncSeparatei(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glBlendFuncSeparatei(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFuncSeparatei", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glBlendFuncSeparateiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glBlendFuncSeparateiEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFuncSeparateiEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glBlendFuncSeparateiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glBlendFuncSeparateiOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFuncSeparateiOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glBlendFunci(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendFunci(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFunci", n, n2, n3);
        }
    }
    
    @Override
    public void glBlendFunciEXT(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendFunciEXT(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFunciEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glBlendFunciOES(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBlendFunciOES(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFunciOES", n, n2, n3);
        }
    }
    
    @Override
    public void glBlitFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.checkContext();
        this.downstreamGLES3.glBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlitFramebuffer", n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        }
    }
    
    @Override
    public void glBlitFramebufferANGLE(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.checkContext();
        this.downstreamGLES3.glBlitFramebufferANGLE(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlitFramebufferANGLE", n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        }
    }
    
    @Override
    public void glBlitFramebufferNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.checkContext();
        this.downstreamGLES3.glBlitFramebufferNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlitFramebufferNV", n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        }
    }
    
    @Override
    public void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBufferData(n, n2, buffer, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <java.nio.Buffer> %s, <int> 0x%X)", "glBufferData", n, n2, buffer, n3);
        }
    }
    
    @Override
    public void glBufferStorageEXT(final int n, final long n2, final Buffer buffer, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glBufferStorageEXT(n, n2, buffer, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <java.nio.Buffer> %s, <int> 0x%X)", "glBufferStorageEXT", n, n2, buffer, n3);
        }
    }
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glBufferSubData(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <java.nio.Buffer> %s)", "glBufferSubData", n, n2, n3, buffer);
        }
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        this.checkContext();
        final int glCheckFramebufferStatus = this.downstreamGLES3.glCheckFramebufferStatus(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCheckFramebufferStatus", n);
        }
        return glCheckFramebufferStatus;
    }
    
    @Override
    public void glClear(final int n) {
        this.checkContext();
        this.downstreamGLES3.glClear(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClear", n);
        }
    }
    
    @Override
    public void glClearBufferfi(final int n, final int n2, final float n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferfi(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s, <int> 0x%X)", "glClearBufferfi", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glClearBufferfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glClearBufferfv", n, n2, n3);
        }
    }
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glClearBufferiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glClearBufferiv", n, n2, n3);
        }
    }
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glClearBufferuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glClearBufferuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glClearBufferuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES3.glClearColor(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s)", "glClearColor", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.checkContext();
        this.downstreamGLES3.glClearDepth(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s)", "glClearDepth", n);
        }
    }
    
    @Override
    public void glClearDepthf(final float n) {
        this.checkContext();
        this.downstreamGLES3.glClearDepthf(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glClearDepthf", n);
        }
    }
    
    @Override
    public void glClearStencil(final int n) {
        this.checkContext();
        this.downstreamGLES3.glClearStencil(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClearStencil", n);
        }
    }
    
    @Override
    public int glClientWaitSync(final long n, final int n2, final long n3) {
        this.checkContext();
        final int glClientWaitSync = this.downstreamGLES3.glClientWaitSync(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s, <int> 0x%X, <long> %s)", "glClientWaitSync", n, n2, n3);
        }
        return glClientWaitSync;
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.checkContext();
        this.downstreamGLES3.glColorMask(b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<boolean> %s, <boolean> %s, <boolean> %s, <boolean> %s)", "glColorMask", b, b2, b3, b4);
        }
    }
    
    @Override
    public void glColorMaski(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.checkContext();
        this.downstreamGLES3.glColorMaski(n, b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <boolean> %s, <boolean> %s, <boolean> %s, <boolean> %s)", "glColorMaski", n, b, b2, b3, b4);
        }
    }
    
    @Override
    public void glColorMaskiEXT(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.checkContext();
        this.downstreamGLES3.glColorMaskiEXT(n, b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <boolean> %s, <boolean> %s, <boolean> %s, <boolean> %s)", "glColorMaskiEXT", n, b, b2, b3, b4);
        }
    }
    
    @Override
    public void glColorMaskiOES(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.checkContext();
        this.downstreamGLES3.glColorMaskiOES(n, b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <boolean> %s, <boolean> %s, <boolean> %s, <boolean> %s)", "glColorMaskiOES", n, b, b2, b3, b4);
        }
    }
    
    @Override
    public void glCompileShader(final int n) {
        this.checkContext();
        this.downstreamGLES3.glCompileShader(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCompileShader", n);
        }
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexImage2D", n, n2, n3, n4, n5, n6, n7, buffer);
        }
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexImage3D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexSubImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        }
    }
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.checkContext();
        this.downstreamGLES3.glCompressedTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexSubImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        }
    }
    
    @Override
    public void glCopyBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glCopyBufferSubData(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <long> %s, <long> %s, <long> %s)", "glCopyBufferSubData", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glCopyBufferSubDataNV(final int n, final int n2, final long n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glCopyBufferSubDataNV(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <long> %s, <long> %s, <long> %s)", "glCopyBufferSubDataNV", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glCopyImageSubData(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15) {
        this.checkContext();
        this.downstreamGLES3.glCopyImageSubData(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyImageSubData", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15);
        }
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.checkContext();
        this.downstreamGLES3.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTexImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.checkContext();
        this.downstreamGLES3.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCopyTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        this.checkContext();
        this.downstreamGLES3.glCopyTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTexSubImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glCopyTextureLevelsAPPLE(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTextureLevelsAPPLE", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glCoverageMaskNV(final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glCoverageMaskNV(b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<boolean> %s)", "glCoverageMaskNV", b);
        }
    }
    
    @Override
    public void glCoverageModulationNV(final int n) {
        this.checkContext();
        this.downstreamGLES3.glCoverageModulationNV(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCoverageModulationNV", n);
        }
    }
    
    @Override
    public void glCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glCoverageModulationTableNV(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glCoverageModulationTableNV", n, floatBuffer);
        }
    }
    
    @Override
    public void glCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glCoverageModulationTableNV(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glCoverageModulationTableNV", n, n2);
        }
    }
    
    @Override
    public void glCoverageOperationNV(final int n) {
        this.checkContext();
        this.downstreamGLES3.glCoverageOperationNV(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCoverageOperationNV", n);
        }
    }
    
    @Override
    public int glCreateProgram() {
        this.checkContext();
        final int glCreateProgram = this.downstreamGLES3.glCreateProgram();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glCreateProgram");
        }
        return glCreateProgram;
    }
    
    @Override
    public int glCreateShader(final int n) {
        this.checkContext();
        final int glCreateShader = this.downstreamGLES3.glCreateShader(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCreateShader", n);
        }
        return glCreateShader;
    }
    
    @Override
    public int glCreateShaderProgramv(final int n, final int n2, final String[] array) {
        this.checkContext();
        final int glCreateShaderProgramv = this.downstreamGLES3.glCreateShaderProgramv(n, n2, array);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>)", "glCreateShaderProgramv", n, n2);
        }
        return glCreateShaderProgramv;
    }
    
    @Override
    public void glCullFace(final int n) {
        this.checkContext();
        this.downstreamGLES3.glCullFace(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCullFace", n);
        }
    }
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glDebugMessageControl(n, n2, n3, n4, array, n5, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <boolean> %s)", "glDebugMessageControl", n, n2, n3, n4, n5, b);
        }
    }
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glDebugMessageControl(n, n2, n3, n4, intBuffer, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <boolean> %s)", "glDebugMessageControl", n, n2, n3, n4, intBuffer, b);
        }
    }
    
    @Override
    public void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        this.checkContext();
        this.downstreamGLES3.glDebugMessageInsert(n, n2, n3, n4, n5, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.lang.String> %s)", "glDebugMessageInsert", n, n2, n3, n4, n5, s);
        }
    }
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteBuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteBuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteBuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteBuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteFramebuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteFramebuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteFramebuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteFramebuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteProgram(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDeleteProgram(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDeleteProgram", n);
        }
    }
    
    @Override
    public void glDeleteProgramPipelines(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteProgramPipelines(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteProgramPipelines", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteProgramPipelines(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteProgramPipelines(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteProgramPipelines", n, n2);
        }
    }
    
    @Override
    public void glDeleteQueries(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteQueries(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteQueries", n, n2);
        }
    }
    
    @Override
    public void glDeleteQueries(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteQueries(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteQueries", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteRenderbuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteRenderbuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteRenderbuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteRenderbuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteSamplers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteSamplers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteSamplers", n, n2);
        }
    }
    
    @Override
    public void glDeleteSamplers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteSamplers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteSamplers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteShader(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDeleteShader(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDeleteShader", n);
        }
    }
    
    @Override
    public void glDeleteSync(final long n) {
        this.checkContext();
        this.downstreamGLES3.glDeleteSync(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s)", "glDeleteSync", n);
        }
    }
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteTextures(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteTextures", n, n2);
        }
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteTextures(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteTextures", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteTransformFeedbacks(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteTransformFeedbacks", n, n2);
        }
    }
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteTransformFeedbacks(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteTransformFeedbacks", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteVertexArrays(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteVertexArrays(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteVertexArrays", n, n2);
        }
    }
    
    @Override
    public void glDeleteVertexArrays(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteVertexArrays(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteVertexArrays", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDeleteVertexArraysOES(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteVertexArraysOES", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDeleteVertexArraysOES(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteVertexArraysOES", n, n2);
        }
    }
    
    @Override
    public void glDepthFunc(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDepthFunc(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDepthFunc", n);
        }
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glDepthMask(b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<boolean> %s)", "glDepthMask", b);
        }
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.checkContext();
        this.downstreamGLES3.glDepthRange(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s, <double> %s)", "glDepthRange", n, n2);
        }
    }
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDepthRangeArrayfvNV(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glDepthRangeArrayfvNV", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glDepthRangeArrayfvNV(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glDepthRangeArrayfvNV", n, n2, n3);
        }
    }
    
    @Override
    public void glDepthRangeIndexedfNV(final int n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glDepthRangeIndexedfNV(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s)", "glDepthRangeIndexedfNV", n, n2, n3);
        }
    }
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        this.checkContext();
        this.downstreamGLES3.glDepthRangef(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s)", "glDepthRangef", n, n2);
        }
    }
    
    @Override
    public void glDetachShader(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDetachShader(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDetachShader", n, n2);
        }
    }
    
    @Override
    public void glDisable(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDisable(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisable", n);
        }
    }
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDisableDriverControlQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisableDriverControlQCOM", n);
        }
    }
    
    @Override
    public void glDisableVertexAttribArray(final int n) {
        this.checkContext();
        this.downstreamGLES3.glDisableVertexAttribArray(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisableVertexAttribArray", n);
        }
    }
    
    @Override
    public void glDisablei(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDisablei(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDisablei", n, n2);
        }
    }
    
    @Override
    public void glDisableiEXT(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDisableiEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDisableiEXT", n, n2);
        }
    }
    
    @Override
    public void glDisableiNV(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDisableiNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDisableiNV", n, n2);
        }
    }
    
    @Override
    public void glDisableiOES(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDisableiOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDisableiOES", n, n2);
        }
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDiscardFramebufferEXT(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glDiscardFramebufferEXT", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glDiscardFramebufferEXT(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glDiscardFramebufferEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glDispatchCompute(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glDispatchCompute(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDispatchCompute", n, n2, n3);
        }
    }
    
    @Override
    public void glDispatchComputeIndirect(final long n) {
        this.checkContext();
        this.downstreamGLES3.glDispatchComputeIndirect(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s)", "glDispatchComputeIndirect", n);
        }
    }
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glDrawArrays(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArrays", n, n2, n3);
        }
    }
    
    @Override
    public void glDrawArraysIndirect(final int n, final long n2) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysIndirect(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s)", "glDrawArraysIndirect", n, n2);
        }
    }
    
    @Override
    public void glDrawArraysIndirect(final int n, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysIndirect(n, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.Buffer> %s)", "glDrawArraysIndirect", n, buffer);
        }
    }
    
    @Override
    public void glDrawArraysInstanced(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysInstanced(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArraysInstanced", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glDrawArraysInstancedANGLE(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysInstancedANGLE(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArraysInstancedANGLE", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glDrawArraysInstancedBaseInstance(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysInstancedBaseInstance(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArraysInstancedBaseInstance", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawArraysInstancedNV(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawArraysInstancedNV(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArraysInstancedNV", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glDrawBuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glDrawBuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDrawBuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDrawBuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glDrawBuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDrawBuffers", n, n2);
        }
    }
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final int[] array, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glDrawBuffersIndexedEXT(n, array, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glDrawBuffersIndexedEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glDrawBuffersIndexedEXT(n, intBuffer, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glDrawBuffersIndexedEXT", n, intBuffer, intBuffer2);
        }
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElements(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glDrawElements", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glDrawElements(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glDrawElements", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsBaseVertex(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsBaseVertex", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsBaseVertex(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X)", "glDrawElementsBaseVertex", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawElementsBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsBaseVertexEXT(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsBaseVertexEXT", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawElementsBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsBaseVertexOES(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsBaseVertexOES", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsIndirect(n, n2, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glDrawElementsIndirect", n, n2, buffer);
        }
    }
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final long n3) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsIndirect(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <long> %s)", "glDrawElementsIndirect", n, n2, n3);
        }
    }
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstanced(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsInstanced", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstanced(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X)", "glDrawElementsInstanced", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawElementsInstancedANGLE(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedANGLE(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsInstancedANGLE", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseInstance(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseInstance", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseVertex(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseVertex", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseVertex(n, n2, n3, buffer, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseVertex", n, n2, n3, buffer, n4, n5);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexBaseInstance(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseVertexBaseInstance", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexEXT(n, n2, n3, buffer, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseVertexEXT", n, n2, n3, buffer, n4, n5);
        }
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexOES(n, n2, n3, buffer, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X, <int> 0x%X)", "glDrawElementsInstancedBaseVertexOES", n, n2, n3, buffer, n4, n5);
        }
    }
    
    @Override
    public void glDrawElementsInstancedNV(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glDrawElementsInstancedNV(n, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawElementsInstancedNV", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElements(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glDrawRangeElements", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElements(n, n2, n3, n4, n5, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glDrawRangeElements", n, n2, n3, n4, n5, buffer);
        }
    }
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <int> 0x%X)", "glDrawRangeElementsBaseVertex", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, buffer, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawRangeElementsBaseVertex", n, n2, n3, n4, n5, buffer, n6);
        }
    }
    
    @Override
    public void glDrawRangeElementsBaseVertexEXT(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElementsBaseVertexEXT(n, n2, n3, n4, n5, buffer, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawRangeElementsBaseVertexEXT", n, n2, n3, n4, n5, buffer, n6);
        }
    }
    
    @Override
    public void glDrawRangeElementsBaseVertexOES(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glDrawRangeElementsBaseVertexOES(n, n2, n3, n4, n5, buffer, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glDrawRangeElementsBaseVertexOES", n, n2, n3, n4, n5, buffer, n6);
        }
    }
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        this.checkContext();
        this.downstreamGLES3.glEGLImageTargetRenderbufferStorageOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s)", "glEGLImageTargetRenderbufferStorageOES", n, n2);
        }
    }
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        this.checkContext();
        this.downstreamGLES3.glEGLImageTargetTexture2DOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s)", "glEGLImageTargetTexture2DOES", n, n2);
        }
    }
    
    @Override
    public void glEnable(final int n) {
        this.checkContext();
        this.downstreamGLES3.glEnable(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnable", n);
        }
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES3.glEnableDriverControlQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnableDriverControlQCOM", n);
        }
    }
    
    @Override
    public void glEnableVertexAttribArray(final int n) {
        this.checkContext();
        this.downstreamGLES3.glEnableVertexAttribArray(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnableVertexAttribArray", n);
        }
    }
    
    @Override
    public void glEnablei(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glEnablei(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glEnablei", n, n2);
        }
    }
    
    @Override
    public void glEnableiEXT(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glEnableiEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glEnableiEXT", n, n2);
        }
    }
    
    @Override
    public void glEnableiNV(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glEnableiNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glEnableiNV", n, n2);
        }
    }
    
    @Override
    public void glEnableiOES(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glEnableiOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glEnableiOES", n, n2);
        }
    }
    
    @Override
    public void glEndConditionalRender() {
        this.checkContext();
        this.downstreamGLES3.glEndConditionalRender();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glEndConditionalRender");
        }
    }
    
    @Override
    public void glEndQuery(final int n) {
        this.checkContext();
        this.downstreamGLES3.glEndQuery(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEndQuery", n);
        }
    }
    
    @Override
    public void glEndTilingQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES3.glEndTilingQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEndTilingQCOM", n);
        }
    }
    
    @Override
    public void glEndTransformFeedback() {
        this.checkContext();
        this.downstreamGLES3.glEndTransformFeedback();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glEndTransformFeedback");
        }
    }
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        this.checkContext();
        this.downstreamGLES3.glExtGetBufferPointervQCOM(n, pointerBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s)", "glExtGetBufferPointervQCOM", n, pointerBuffer);
        }
    }
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetBuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetBuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetBuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetBuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetFramebuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetFramebuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetFramebuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetFramebuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glExtGetProgramBinarySourceQCOM(n, n2, byteBuffer, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s, <java.nio.IntBuffer> %s)", "glExtGetProgramBinarySourceQCOM", n, n2, byteBuffer, intBuffer);
        }
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glExtGetProgramBinarySourceQCOM(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetProgramBinarySourceQCOM", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetProgramsQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetProgramsQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetProgramsQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetProgramsQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetRenderbuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetRenderbuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetRenderbuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetRenderbuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetShadersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetShadersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetShadersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetShadersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetTexLevelParameterivQCOM", n, n2, n3, n4, intBuffer);
        }
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, array, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetTexLevelParameterivQCOM", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glExtGetTexSubImageQCOM(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glExtGetTexSubImageQCOM", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        }
    }
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtGetTexturesQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetTexturesQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glExtGetTexturesQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetTexturesQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        this.checkContext();
        final boolean glExtIsProgramBinaryQCOM = this.downstreamGLES3.glExtIsProgramBinaryQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glExtIsProgramBinaryQCOM", n);
        }
        return glExtIsProgramBinaryQCOM;
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glExtTexObjectStateOverrideiQCOM(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glExtTexObjectStateOverrideiQCOM", n, n2, n3);
        }
    }
    
    @Override
    public long glFenceSync(final int n, final int n2) {
        this.checkContext();
        final long glFenceSync = this.downstreamGLES3.glFenceSync(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glFenceSync", n, n2);
        }
        return glFenceSync;
    }
    
    @Override
    public void glFinish() {
        this.checkContext();
        this.downstreamGLES3.glFinish();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glFinish");
        }
    }
    
    @Override
    public void glFlush() {
        this.checkContext();
        this.downstreamGLES3.glFlush();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glFlush");
        }
    }
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        this.checkContext();
        this.downstreamGLES3.glFlushMappedBufferRange(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s)", "glFlushMappedBufferRange", n, n2, n3);
        }
    }
    
    @Override
    public void glFragmentCoverageColorNV(final int n) {
        this.checkContext();
        this.downstreamGLES3.glFragmentCoverageColorNV(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glFragmentCoverageColorNV", n);
        }
    }
    
    @Override
    public void glFramebufferParameteri(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferParameteri(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferParameteri", n, n2, n3);
        }
    }
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferRenderbuffer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferRenderbuffer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferSampleLocationsfvNV(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glFramebufferSampleLocationsfvNV", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferSampleLocationsfvNV(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glFramebufferSampleLocationsfvNV", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTexture(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTexture2D(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2D", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTexture2DMultisampleEXT(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2DMultisampleEXT", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTexture2DMultisampleIMG(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2DMultisampleIMG", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFramebufferTexture3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTexture3D(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture3D", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFramebufferTextureEXT(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTextureEXT(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTextureEXT", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTextureLayer(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTextureLayer", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glFramebufferTextureMultiviewOVR(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTextureMultiviewOVR(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTextureMultiviewOVR", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFramebufferTextureOES(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glFramebufferTextureOES(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTextureOES", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFrontFace(final int n) {
        this.checkContext();
        this.downstreamGLES3.glFrontFace(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glFrontFace", n);
        }
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenBuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenBuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenBuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenBuffers", n, n2);
        }
    }
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenFramebuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenFramebuffers", n, n2);
        }
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenFramebuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenFramebuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenProgramPipelines(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenProgramPipelines(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenProgramPipelines", n, intBuffer);
        }
    }
    
    @Override
    public void glGenProgramPipelines(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenProgramPipelines(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenProgramPipelines", n, n2);
        }
    }
    
    @Override
    public void glGenQueries(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenQueries(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenQueries", n, n2);
        }
    }
    
    @Override
    public void glGenQueries(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenQueries(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenQueries", n, intBuffer);
        }
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenRenderbuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenRenderbuffers", n, n2);
        }
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenRenderbuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenRenderbuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenSamplers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenSamplers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenSamplers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenSamplers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenSamplers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenSamplers", n, n2);
        }
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenTextures(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenTextures", n, intBuffer);
        }
    }
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenTextures(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenTextures", n, n2);
        }
    }
    
    @Override
    public void glGenTransformFeedbacks(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenTransformFeedbacks(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenTransformFeedbacks", n, n2);
        }
    }
    
    @Override
    public void glGenTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenTransformFeedbacks(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenTransformFeedbacks", n, intBuffer);
        }
    }
    
    @Override
    public void glGenVertexArrays(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenVertexArrays(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenVertexArrays", n, n2);
        }
    }
    
    @Override
    public void glGenVertexArrays(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenVertexArrays(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenVertexArrays", n, intBuffer);
        }
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGenVertexArraysOES(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenVertexArraysOES", n, n2);
        }
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGenVertexArraysOES(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenVertexArraysOES", n, intBuffer);
        }
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        this.checkContext();
        this.downstreamGLES3.glGenerateMipmap(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glGenerateMipmap", n);
        }
    }
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveAttrib(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetActiveAttrib", n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        }
    }
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveAttrib(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetActiveAttrib", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniform(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetActiveUniform", n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        }
    }
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniform(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetActiveUniform", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformBlockName(n, n2, n3, array, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetActiveUniformBlockName", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformBlockName(n, n2, n3, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetActiveUniformBlockName", n, n2, n3, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformBlockiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetActiveUniformBlockiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformBlockiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetActiveUniformBlockiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final int[] array, final int n3, final int n4, final int[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformsiv(n, n2, array, n3, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetActiveUniformsiv", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final IntBuffer intBuffer, final int n3, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glGetActiveUniformsiv(n, n2, intBuffer, n3, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetActiveUniformsiv", n, n2, intBuffer, n3, intBuffer2);
        }
    }
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetAttachedShaders(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glGetAttachedShaders", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glGetAttachedShaders(n, n2, intBuffer, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glGetAttachedShaders", n, n2, intBuffer, intBuffer2);
        }
    }
    
    @Override
    public int glGetAttribLocation(final int n, final String s) {
        this.checkContext();
        final int glGetAttribLocation = this.downstreamGLES3.glGetAttribLocation(n, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.lang.String> %s)", "glGetAttribLocation", n, s);
        }
        return glGetAttribLocation;
    }
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final byte[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetBooleani_v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glGetBooleani_v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetBooleani_v(n, n2, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetBooleani_v", n, n2, byteBuffer);
        }
    }
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetBooleanv(n, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetBooleanv", n, byteBuffer);
        }
    }
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGetBooleanv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[B>, <int> 0x%X)", "glGetBooleanv", n, n2);
        }
    }
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetBufferParameteri64v(n, n2, longBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.LongBuffer> %s)", "glGetBufferParameteri64v", n, n2, longBuffer);
        }
    }
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final long[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetBufferParameteri64v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[J>, <int> 0x%X)", "glGetBufferParameteri64v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetBufferParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetBufferParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetBufferParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetBufferParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGetCoverageModulationTableNV(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glGetCoverageModulationTableNV", n, n2);
        }
    }
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetCoverageModulationTableNV(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetCoverageModulationTableNV", n, floatBuffer);
        }
    }
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final int[] array3, final int n5, final int[] array4, final int n6, final int[] array5, final int n7, final byte[] array6, final int n8) {
        this.checkContext();
        final int glGetDebugMessageLog = this.downstreamGLES3.glGetDebugMessageLog(n, n2, array, n3, array2, n4, array3, n5, array4, n6, array5, n7, array6, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetDebugMessageLog", n, n2, n3, n4, n5, n6, n7, n8);
        }
        return glGetDebugMessageLog;
    }
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        this.checkContext();
        final int glGetDebugMessageLog = this.downstreamGLES3.glGetDebugMessageLog(n, n2, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetDebugMessageLog", n, n2, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
        }
        return glGetDebugMessageLog;
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetDriverControlStringQCOM(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetDriverControlStringQCOM", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetDriverControlStringQCOM(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetDriverControlStringQCOM", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetDriverControlsQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetDriverControlsQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glGetDriverControlsQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetDriverControlsQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public int glGetError() {
        this.checkContext();
        final int glGetError = this.downstreamGLES3.glGetError();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glGetError");
        }
        return glGetError;
    }
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetFloati_vNV(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetFloati_vNV", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetFloati_vNV(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetFloati_vNV", n, n2, n3);
        }
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetFloatv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetFloatv", n, floatBuffer);
        }
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGetFloatv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glGetFloatv", n, n2);
        }
    }
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final ByteBuffer byteBuffer) {
        this.checkContext();
        final int glGetFragDataIndexEXT = this.downstreamGLES3.glGetFragDataIndexEXT(n, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetFragDataIndexEXT", n, byteBuffer);
        }
        return glGetFragDataIndexEXT;
    }
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final byte[] array, final int n2) {
        this.checkContext();
        final int glGetFragDataIndexEXT = this.downstreamGLES3.glGetFragDataIndexEXT(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[B>, <int> 0x%X)", "glGetFragDataIndexEXT", n, n2);
        }
        return glGetFragDataIndexEXT;
    }
    
    @Override
    public int glGetFragDataLocation(final int n, final String s) {
        this.checkContext();
        final int glGetFragDataLocation = this.downstreamGLES3.glGetFragDataLocation(n, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.lang.String> %s)", "glGetFragDataLocation", n, s);
        }
        return glGetFragDataLocation;
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetFramebufferAttachmentParameteriv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetFramebufferAttachmentParameteriv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetFramebufferAttachmentParameteriv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetFramebufferParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetFramebufferParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetFramebufferParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetFramebufferParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public int glGetGraphicsResetStatus() {
        this.checkContext();
        final int glGetGraphicsResetStatus = this.downstreamGLES3.glGetGraphicsResetStatus();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glGetGraphicsResetStatus");
        }
        return glGetGraphicsResetStatus;
    }
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final long[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetInteger64i_v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[J>, <int> 0x%X)", "glGetInteger64i_v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final LongBuffer longBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetInteger64i_v(n, n2, longBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.LongBuffer> %s)", "glGetInteger64i_v", n, n2, longBuffer);
        }
    }
    
    @Override
    public void glGetInteger64v(final int n, final LongBuffer longBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetInteger64v(n, longBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.LongBuffer> %s)", "glGetInteger64v", n, longBuffer);
        }
    }
    
    @Override
    public void glGetInteger64v(final int n, final long[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGetInteger64v(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[J>, <int> 0x%X)", "glGetInteger64v", n, n2);
        }
    }
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegeri_v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetIntegeri_v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegeri_v(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetIntegeri_v", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegeri_vEXT(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetIntegeri_vEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegeri_vEXT(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetIntegeri_vEXT", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegerv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGetIntegerv", n, n2);
        }
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetIntegerv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGetIntegerv", n, intBuffer);
        }
    }
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetInternalformativ(n, n2, n3, n4, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetInternalformativ", n, n2, n3, n4, intBuffer);
        }
    }
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glGetInternalformativ(n, n2, n3, n4, array, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetInternalformativ", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetMultisamplefv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetMultisamplefv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetMultisamplefv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetMultisamplefv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetObjectLabel(n, n2, n3, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetObjectLabel", n, n2, n3, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glGetObjectLabel(n, n2, n3, array, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetObjectLabel", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final int[] array, final int n2, final byte[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetObjectPtrLabel(buffer, n, array, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.Buffer> %s, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetObjectPtrLabel", buffer, n, n2, n3);
        }
    }
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetObjectPtrLabel(buffer, n, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.Buffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetObjectPtrLabel", buffer, n, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramBinary(n, n2, intBuffer, intBuffer2, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.Buffer> %s)", "glGetProgramBinary", n, n2, intBuffer, intBuffer2, buffer);
        }
    }
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramBinary(n, n2, array, n3, array2, n4, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <java.nio.Buffer> %s)", "glGetProgramBinary", n, n2, n3, n4, buffer);
        }
    }
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramInfoLog(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetProgramInfoLog", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramInfoLog(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramInfoLog", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramInterfaceiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetProgramInterfaceiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramInterfaceiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetProgramInterfaceiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramPipelineInfoLog(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetProgramPipelineInfoLog", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramPipelineInfoLog(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramPipelineInfoLog", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramPipelineiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetProgramPipelineiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramPipelineiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetProgramPipelineiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final byte[] array, final int n3) {
        this.checkContext();
        final int glGetProgramResourceIndex = this.downstreamGLES3.glGetProgramResourceIndex(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramResourceIndex", n, n2, n3);
        }
        return glGetProgramResourceIndex;
    }
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.checkContext();
        final int glGetProgramResourceIndex = this.downstreamGLES3.glGetProgramResourceIndex(n, n2, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetProgramResourceIndex", n, n2, byteBuffer);
        }
        return glGetProgramResourceIndex;
    }
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.checkContext();
        final int glGetProgramResourceLocation = this.downstreamGLES3.glGetProgramResourceLocation(n, n2, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetProgramResourceLocation", n, n2, byteBuffer);
        }
        return glGetProgramResourceLocation;
    }
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final byte[] array, final int n3) {
        this.checkContext();
        final int glGetProgramResourceLocation = this.downstreamGLES3.glGetProgramResourceLocation(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramResourceLocation", n, n2, n3);
        }
        return glGetProgramResourceLocation;
    }
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.checkContext();
        final int glGetProgramResourceLocationIndexEXT = this.downstreamGLES3.glGetProgramResourceLocationIndexEXT(n, n2, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetProgramResourceLocationIndexEXT", n, n2, byteBuffer);
        }
        return glGetProgramResourceLocationIndexEXT;
    }
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final byte[] array, final int n3) {
        this.checkContext();
        final int glGetProgramResourceLocationIndexEXT = this.downstreamGLES3.glGetProgramResourceLocationIndexEXT(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramResourceLocationIndexEXT", n, n2, n3);
        }
        return glGetProgramResourceLocationIndexEXT;
    }
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final byte[] array2, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramResourceName(n, n2, n3, n4, array, n5, array2, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetProgramResourceName", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramResourceName(n, n2, n3, n4, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetProgramResourceName", n, n2, n3, n4, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final int n6, final int[] array2, final int n7, final int[] array3, final int n8) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramResourceiv(n, n2, n3, n4, array, n5, n6, array2, n7, array3, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glGetProgramResourceiv", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final int n5, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramResourceiv(n, n2, n3, n4, intBuffer, n5, intBuffer2, intBuffer3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glGetProgramResourceiv", n, n2, n3, n4, intBuffer, n5, intBuffer2, intBuffer3);
        }
    }
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetProgramiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetProgramiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetProgramiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final long[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjecti64v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[J>, <int> 0x%X)", "glGetQueryObjecti64v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjecti64v(n, n2, longBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.LongBuffer> %s)", "glGetQueryObjecti64v", n, n2, longBuffer);
        }
    }
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetQueryObjectiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetQueryObjectiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectui64v(n, n2, longBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.LongBuffer> %s)", "glGetQueryObjectui64v", n, n2, longBuffer);
        }
    }
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final long[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectui64v(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[J>, <int> 0x%X)", "glGetQueryObjectui64v", n, n2, n3);
        }
    }
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetQueryObjectuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryObjectuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetQueryObjectuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetQueryiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetQueryiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetQueryiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetRenderbufferParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetRenderbufferParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetRenderbufferParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetRenderbufferParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterIiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetSamplerParameterIiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterIiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetSamplerParameterIiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterIuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetSamplerParameterIuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterIuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetSamplerParameterIuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetSamplerParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetSamplerParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetSamplerParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetSamplerParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetSamplerParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderInfoLog(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetShaderInfoLog", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderInfoLog(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetShaderInfoLog", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderPrecisionFormat(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glGetShaderPrecisionFormat", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderPrecisionFormat(n, n2, intBuffer, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glGetShaderPrecisionFormat", n, n2, intBuffer, intBuffer2);
        }
    }
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderSource(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetShaderSource", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderSource(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetShaderSource", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetShaderiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetShaderiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetShaderiv", n, n2, n3);
        }
    }
    
    @Override
    public String glGetString(final int n) {
        this.checkContext();
        final String glGetString = this.downstreamGLES3.glGetString(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glGetString", n);
        }
        return glGetString;
    }
    
    @Override
    public String glGetStringi(final int n, final int n2) {
        this.checkContext();
        final String glGetStringi = this.downstreamGLES3.glGetStringi(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glGetStringi", n, n2);
        }
        return glGetStringi;
    }
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glGetSynciv(n, n2, n3, intBuffer, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glGetSynciv", n, n2, n3, intBuffer, intBuffer2);
        }
    }
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glGetSynciv(n, n2, n3, array, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glGetSynciv", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexLevelParameterfv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetTexLevelParameterfv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetTexLevelParameterfv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetTexLevelParameterfv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetTexLevelParameteriv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexLevelParameteriv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexLevelParameteriv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexLevelParameteriv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterIiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexParameterIiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterIiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexParameterIiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterIuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexParameterIuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterIuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexParameterIuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetTexParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetTexParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetTexParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glGetTransformFeedbackVarying(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetTransformFeedbackVarying", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTransformFeedbackVarying(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetTransformFeedbackVarying", n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        }
    }
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetTranslatedShaderSourceANGLE(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetTranslatedShaderSourceANGLE", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetTranslatedShaderSourceANGLE(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetTranslatedShaderSourceANGLE", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public int glGetUniformBlockIndex(final int n, final String s) {
        this.checkContext();
        final int glGetUniformBlockIndex = this.downstreamGLES3.glGetUniformBlockIndex(n, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.lang.String> %s)", "glGetUniformBlockIndex", n, s);
        }
        return glGetUniformBlockIndex;
    }
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformIndices(n, n2, array, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>, <[I>, <int> 0x%X)", "glGetUniformIndices", n, n2, n3);
        }
    }
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformIndices(n, n2, array, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>, <java.nio.IntBuffer> %s)", "glGetUniformIndices", n, n2, intBuffer);
        }
    }
    
    @Override
    public int glGetUniformLocation(final int n, final String s) {
        this.checkContext();
        final int glGetUniformLocation = this.downstreamGLES3.glGetUniformLocation(n, s);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.lang.String> %s)", "glGetUniformLocation", n, s);
        }
        return glGetUniformLocation;
    }
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetUniformfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetUniformfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetUniformiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetUniformiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetUniformuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetUniformuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetUniformuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribIiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetVertexAttribIiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribIiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetVertexAttribIiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribIuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetVertexAttribIuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribIuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetVertexAttribIuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetVertexAttribfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetVertexAttribfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetVertexAttribiv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetVertexAttribiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetVertexAttribiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformfv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetnUniformfv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformfv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetnUniformfv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetnUniformiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetnUniformiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformuiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetnUniformuiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glGetnUniformuiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetnUniformuiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glHint(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glHint", n, n2);
        }
    }
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glInvalidateFramebuffer(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glInvalidateFramebuffer", n, n2, n3);
        }
    }
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glInvalidateFramebuffer(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glInvalidateFramebuffer", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glInvalidateSubFramebuffer(n, n2, intBuffer, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glInvalidateSubFramebuffer", n, n2, intBuffer, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final int[] array, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glInvalidateSubFramebuffer(n, n2, array, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glInvalidateSubFramebuffer", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean glIsBuffer(final int n) {
        this.checkContext();
        final boolean glIsBuffer = this.downstreamGLES3.glIsBuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsBuffer", n);
        }
        return glIsBuffer;
    }
    
    @Override
    public boolean glIsEnabled(final int n) {
        this.checkContext();
        final boolean glIsEnabled = this.downstreamGLES3.glIsEnabled(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsEnabled", n);
        }
        return glIsEnabled;
    }
    
    @Override
    public boolean glIsEnabledi(final int n, final int n2) {
        this.checkContext();
        final boolean glIsEnabledi = this.downstreamGLES3.glIsEnabledi(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glIsEnabledi", n, n2);
        }
        return glIsEnabledi;
    }
    
    @Override
    public boolean glIsEnablediEXT(final int n, final int n2) {
        this.checkContext();
        final boolean glIsEnablediEXT = this.downstreamGLES3.glIsEnablediEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glIsEnablediEXT", n, n2);
        }
        return glIsEnablediEXT;
    }
    
    @Override
    public boolean glIsEnablediNV(final int n, final int n2) {
        this.checkContext();
        final boolean glIsEnablediNV = this.downstreamGLES3.glIsEnablediNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glIsEnablediNV", n, n2);
        }
        return glIsEnablediNV;
    }
    
    @Override
    public boolean glIsEnablediOES(final int n, final int n2) {
        this.checkContext();
        final boolean glIsEnablediOES = this.downstreamGLES3.glIsEnablediOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glIsEnablediOES", n, n2);
        }
        return glIsEnablediOES;
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        this.checkContext();
        final boolean glIsFramebuffer = this.downstreamGLES3.glIsFramebuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsFramebuffer", n);
        }
        return glIsFramebuffer;
    }
    
    @Override
    public boolean glIsProgram(final int n) {
        this.checkContext();
        final boolean glIsProgram = this.downstreamGLES3.glIsProgram(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsProgram", n);
        }
        return glIsProgram;
    }
    
    @Override
    public boolean glIsProgramPipeline(final int n) {
        this.checkContext();
        final boolean glIsProgramPipeline = this.downstreamGLES3.glIsProgramPipeline(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsProgramPipeline", n);
        }
        return glIsProgramPipeline;
    }
    
    @Override
    public boolean glIsQuery(final int n) {
        this.checkContext();
        final boolean glIsQuery = this.downstreamGLES3.glIsQuery(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsQuery", n);
        }
        return glIsQuery;
    }
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        this.checkContext();
        final boolean glIsRenderbuffer = this.downstreamGLES3.glIsRenderbuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsRenderbuffer", n);
        }
        return glIsRenderbuffer;
    }
    
    @Override
    public boolean glIsSampler(final int n) {
        this.checkContext();
        final boolean glIsSampler = this.downstreamGLES3.glIsSampler(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsSampler", n);
        }
        return glIsSampler;
    }
    
    @Override
    public boolean glIsShader(final int n) {
        this.checkContext();
        final boolean glIsShader = this.downstreamGLES3.glIsShader(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsShader", n);
        }
        return glIsShader;
    }
    
    @Override
    public boolean glIsSync(final long n) {
        this.checkContext();
        final boolean glIsSync = this.downstreamGLES3.glIsSync(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s)", "glIsSync", n);
        }
        return glIsSync;
    }
    
    @Override
    public boolean glIsTexture(final int n) {
        this.checkContext();
        final boolean glIsTexture = this.downstreamGLES3.glIsTexture(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsTexture", n);
        }
        return glIsTexture;
    }
    
    @Override
    public boolean glIsTransformFeedback(final int n) {
        this.checkContext();
        final boolean glIsTransformFeedback = this.downstreamGLES3.glIsTransformFeedback(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsTransformFeedback", n);
        }
        return glIsTransformFeedback;
    }
    
    @Override
    public boolean glIsVertexArray(final int n) {
        this.checkContext();
        final boolean glIsVertexArray = this.downstreamGLES3.glIsVertexArray(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsVertexArray", n);
        }
        return glIsVertexArray;
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        this.checkContext();
        final boolean glIsVertexArrayOES = this.downstreamGLES3.glIsVertexArrayOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsVertexArrayOES", n);
        }
        return glIsVertexArrayOES;
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.checkContext();
        this.downstreamGLES3.glLineWidth(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glLineWidth", n);
        }
    }
    
    @Override
    public void glLinkProgram(final int n) {
        this.checkContext();
        this.downstreamGLES3.glLinkProgram(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glLinkProgram", n);
        }
    }
    
    @Override
    public ByteBuffer glMapBuffer(final int n, final int n2) {
        this.checkContext();
        final ByteBuffer glMapBuffer = this.downstreamGLES3.glMapBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glMapBuffer", n, n2);
        }
        return glMapBuffer;
    }
    
    @Override
    public ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.checkContext();
        final ByteBuffer glMapBufferRange = this.downstreamGLES3.glMapBufferRange(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <int> 0x%X)", "glMapBufferRange", n, n2, n3, n4);
        }
        return glMapBufferRange;
    }
    
    @Override
    public void glMemoryBarrier(final int n) {
        this.checkContext();
        this.downstreamGLES3.glMemoryBarrier(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glMemoryBarrier", n);
        }
    }
    
    @Override
    public void glMemoryBarrierByRegion(final int n) {
        this.checkContext();
        this.downstreamGLES3.glMemoryBarrierByRegion(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glMemoryBarrierByRegion", n);
        }
    }
    
    @Override
    public void glMinSampleShading(final float n) {
        this.checkContext();
        this.downstreamGLES3.glMinSampleShading(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glMinSampleShading", n);
        }
    }
    
    @Override
    public void glMinSampleShadingOES(final float n) {
        this.checkContext();
        this.downstreamGLES3.glMinSampleShadingOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glMinSampleShadingOES", n);
        }
    }
    
    @Override
    public void glMultiDrawArraysIndirectEXT(final int n, final Buffer buffer, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawArraysIndirectEXT(n, buffer, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X, <int> 0x%X)", "glMultiDrawArraysIndirectEXT", n, buffer, n2, n3);
        }
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawElementsBaseVertexEXT(n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glMultiDrawElementsBaseVertexEXT", n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        }
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawElementsBaseVertexEXT(n, array, n2, n3, pointerBuffer, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s, <int> 0x%X, <[I>, <int> 0x%X)", "glMultiDrawElementsBaseVertexEXT", n, n2, n3, pointerBuffer, n4, n5);
        }
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawElementsBaseVertexOES(n, array, n2, n3, pointerBuffer, n4, array2, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s, <int> 0x%X, <[I>, <int> 0x%X)", "glMultiDrawElementsBaseVertexOES", n, n2, n3, pointerBuffer, n4, n5);
        }
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawElementsBaseVertexOES(n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glMultiDrawElementsBaseVertexOES", n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        }
    }
    
    @Override
    public void glMultiDrawElementsIndirectEXT(final int n, final int n2, final Buffer buffer, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glMultiDrawElementsIndirectEXT(n, n2, buffer, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X, <int> 0x%X)", "glMultiDrawElementsIndirectEXT", n, n2, buffer, n3, n4);
        }
    }
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glNamedFramebufferSampleLocationsfvNV(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glNamedFramebufferSampleLocationsfvNV", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glNamedFramebufferSampleLocationsfvNV(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glNamedFramebufferSampleLocationsfvNV", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glObjectLabel(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glObjectLabel", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glObjectLabel(n, n2, n3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glObjectLabel", n, n2, n3, byteBuffer);
        }
    }
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glObjectPtrLabel(buffer, n, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.Buffer> %s, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glObjectPtrLabel", buffer, n, byteBuffer);
        }
    }
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final byte[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glObjectPtrLabel(buffer, n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.Buffer> %s, <int> 0x%X, <[B>, <int> 0x%X)", "glObjectPtrLabel", buffer, n, n2);
        }
    }
    
    @Override
    public void glPatchParameteri(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glPatchParameteri(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPatchParameteri", n, n2);
        }
    }
    
    @Override
    public void glPatchParameteriEXT(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glPatchParameteriEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPatchParameteriEXT", n, n2);
        }
    }
    
    @Override
    public void glPatchParameteriOES(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glPatchParameteriOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPatchParameteriOES", n, n2);
        }
    }
    
    @Override
    public void glPauseTransformFeedback() {
        this.checkContext();
        this.downstreamGLES3.glPauseTransformFeedback();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glPauseTransformFeedback");
        }
    }
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glPixelStorei(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPixelStorei", n, n2);
        }
    }
    
    @Override
    public void glPolygonModeNV(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glPolygonModeNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPolygonModeNV", n, n2);
        }
    }
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        this.checkContext();
        this.downstreamGLES3.glPolygonOffset(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s)", "glPolygonOffset", n, n2);
        }
    }
    
    @Override
    public void glPopDebugGroup() {
        this.checkContext();
        this.downstreamGLES3.glPopDebugGroup();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glPopDebugGroup");
        }
    }
    
    @Override
    public void glPrimitiveBoundingBox(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.checkContext();
        this.downstreamGLES3.glPrimitiveBoundingBox(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glPrimitiveBoundingBox", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glPrimitiveBoundingBoxEXT(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.checkContext();
        this.downstreamGLES3.glPrimitiveBoundingBoxEXT(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glPrimitiveBoundingBoxEXT", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glPrimitiveBoundingBoxOES(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.checkContext();
        this.downstreamGLES3.glPrimitiveBoundingBoxOES(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glPrimitiveBoundingBoxOES", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glProgramBinary(final int n, final int n2, final Buffer buffer, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glProgramBinary(n, n2, buffer, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glProgramBinary", n, n2, buffer, n3);
        }
    }
    
    @Override
    public void glProgramParameteri(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glProgramParameteri(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramParameteri", n, n2, n3);
        }
    }
    
    @Override
    public void glProgramUniform1f(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1f(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glProgramUniform1f", n, n2, n3);
        }
    }
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1fv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glProgramUniform1fv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1fv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glProgramUniform1fv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniform1i(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1i(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform1i", n, n2, n3);
        }
    }
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1iv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform1iv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1iv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform1iv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform1ui(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1ui(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform1ui", n, n2, n3);
        }
    }
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1uiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform1uiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform1uiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform1uiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform2f(final int n, final int n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2f(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s, <float> %s)", "glProgramUniform2f", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2fv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glProgramUniform2fv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2fv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glProgramUniform2fv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2i(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2i(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform2i", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2iv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform2iv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2iv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform2iv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2ui(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2ui(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform2ui", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2uiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform2uiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform2uiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform2uiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3f(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s, <float> %s, <float> %s)", "glProgramUniform3f", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3fv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glProgramUniform3fv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3fv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glProgramUniform3fv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniform3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3i(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform3i", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3iv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform3iv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3iv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform3iv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform3ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3ui(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform3ui", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3uiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform3uiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform3uiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform3uiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform4f(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4f(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s, <float> %s, <float> %s, <float> %s)", "glProgramUniform4f", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4fv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glProgramUniform4fv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4fv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glProgramUniform4fv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform4i(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4i(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform4i", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4iv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform4iv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4iv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform4iv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4ui(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glProgramUniform4ui", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4uiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glProgramUniform4uiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniform4uiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glProgramUniform4uiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix2fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix2fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2x3fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix2x3fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2x3fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix2x3fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2x4fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix2x4fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix2x4fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix2x4fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix3fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix3fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3x2fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix3x2fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3x2fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix3x2fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3x4fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix3x4fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix3x4fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix3x4fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix4fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix4fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4x2fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix4x2fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4x2fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix4x2fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4x3fv(n, n2, n3, b, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glProgramUniformMatrix4x3fv", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glProgramUniformMatrix4x3fv(n, n2, n3, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glProgramUniformMatrix4x3fv", n, n2, n3, b, floatBuffer);
        }
    }
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES3.glPushDebugGroup(n, n2, n3, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s)", "glPushDebugGroup", n, n2, n3, byteBuffer);
        }
    }
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glPushDebugGroup(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X)", "glPushDebugGroup", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glQueryCounter(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glQueryCounter(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glQueryCounter", n, n2);
        }
    }
    
    @Override
    public void glRasterSamplesEXT(final int n, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glRasterSamplesEXT(n, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <boolean> %s)", "glRasterSamplesEXT", n, b);
        }
    }
    
    @Override
    public void glReadBuffer(final int n) {
        this.checkContext();
        this.downstreamGLES3.glReadBuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glReadBuffer", n);
        }
    }
    
    @Override
    public void glReadBufferIndexedEXT(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glReadBufferIndexedEXT(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glReadBufferIndexedEXT", n, n2);
        }
    }
    
    @Override
    public void glReadBufferNV(final int n) {
        this.checkContext();
        this.downstreamGLES3.glReadBufferNV(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glReadBufferNV", n);
        }
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.checkContext();
        this.downstreamGLES3.glReadPixels(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glReadPixels", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glReadPixels", n, n2, n3, n4, n5, n6, buffer);
        }
    }
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glReadnPixels(n, n2, n3, n4, n5, n6, n7, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glReadnPixels", n, n2, n3, n4, n5, n6, n7, buffer);
        }
    }
    
    @Override
    public void glReleaseShaderCompiler() {
        this.checkContext();
        this.downstreamGLES3.glReleaseShaderCompiler();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glReleaseShaderCompiler");
        }
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glRenderbufferStorage(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorage", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisample", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisampleEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glRenderbufferStorageMultisampleIMG(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisampleIMG", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisampleNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glRenderbufferStorageMultisampleNV(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisampleNV", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glResolveDepthValuesNV() {
        this.checkContext();
        this.downstreamGLES3.glResolveDepthValuesNV();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glResolveDepthValuesNV");
        }
    }
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        this.checkContext();
        this.downstreamGLES3.glResolveMultisampleFramebuffer();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glResolveMultisampleFramebuffer");
        }
    }
    
    @Override
    public void glResumeTransformFeedback() {
        this.checkContext();
        this.downstreamGLES3.glResumeTransformFeedback();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glResumeTransformFeedback");
        }
    }
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glSampleCoverage(n, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <boolean> %s)", "glSampleCoverage", n, b);
        }
    }
    
    @Override
    public void glSampleMaski(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glSampleMaski(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glSampleMaski", n, n2);
        }
    }
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterIiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glSamplerParameterIiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterIiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glSamplerParameterIiv", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterIuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glSamplerParameterIuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterIuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glSamplerParameterIuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameterf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glSamplerParameterf", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glSamplerParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glSamplerParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameteri(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameteri(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glSamplerParameteri", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glSamplerParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glSamplerParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glSamplerParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glScissor(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glScissor", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glScissorArrayvNV(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glScissorArrayvNV", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glScissorArrayvNV(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glScissorArrayvNV", n, n2, n3);
        }
    }
    
    @Override
    public void glScissorIndexedNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glScissorIndexedNV(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glScissorIndexedNV", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glScissorIndexedvNV(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glScissorIndexedvNV(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glScissorIndexedvNV", n, n2);
        }
    }
    
    @Override
    public void glScissorIndexedvNV(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glScissorIndexedvNV(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glScissorIndexedvNV", n, intBuffer);
        }
    }
    
    @Override
    public void glShaderBinary(final int n, final int[] array, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glShaderBinary(n, array, n2, n3, buffer, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glShaderBinary", n, n2, n3, buffer, n4);
        }
    }
    
    @Override
    public void glShaderBinary(final int n, final IntBuffer intBuffer, final int n2, final Buffer buffer, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glShaderBinary(n, intBuffer, n2, buffer, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.Buffer> %s, <int> 0x%X)", "glShaderBinary", n, intBuffer, n2, buffer, n3);
        }
    }
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glShaderSource(n, n2, array, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>, <java.nio.IntBuffer> %s)", "glShaderSource", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glShaderSource(n, n2, array, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>, <[I>, <int> 0x%X)", "glShaderSource", n, n2, n3);
        }
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glStartTilingQCOM(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStartTilingQCOM", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glStencilFunc(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilFunc", n, n2, n3);
        }
    }
    
    @Override
    public void glStencilFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glStencilFuncSeparate(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilFuncSeparate", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glStencilMask(final int n) {
        this.checkContext();
        this.downstreamGLES3.glStencilMask(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glStencilMask", n);
        }
    }
    
    @Override
    public void glStencilMaskSeparate(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glStencilMaskSeparate(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glStencilMaskSeparate", n, n2);
        }
    }
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glStencilOp(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilOp", n, n2, n3);
        }
    }
    
    @Override
    public void glStencilOpSeparate(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glStencilOpSeparate(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilOpSeparate", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glSubpixelPrecisionBiasNV(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glSubpixelPrecisionBiasNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glSubpixelPrecisionBiasNV", n, n2);
        }
    }
    
    @Override
    public void glTexBuffer(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexBuffer(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexBuffer", n, n2, n3);
        }
    }
    
    @Override
    public void glTexBufferEXT(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexBufferEXT(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexBufferEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glTexBufferOES(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexBufferOES(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexBufferOES", n, n2, n3);
        }
    }
    
    @Override
    public void glTexBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glTexBufferRange(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <long> %s)", "glTexBufferRange", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTexBufferRangeEXT(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glTexBufferRangeEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <long> %s)", "glTexBufferRangeEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTexBufferRangeOES(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glTexBufferRangeOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s, <long> %s)", "glTexBufferRangeOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES3.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glTexImage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexImage2DMultisample(n, n2, n3, n4, n5, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexImage2DMultisample", n, n2, n3, n4, n5, b);
        }
    }
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, buffer);
        }
    }
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        this.checkContext();
        this.downstreamGLES3.glTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        }
    }
    
    @Override
    public void glTexImage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexImage3DMultisample(n, n2, n3, n4, n5, n6, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexImage3DMultisample", n, n2, n3, n4, n5, n6, b);
        }
    }
    
    @Override
    public void glTexPageCommitmentEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexPageCommitmentEXT(n, n2, n3, n4, n5, n6, n7, n8, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexPageCommitmentEXT", n, n2, n3, n4, n5, n6, n7, n8, b);
        }
    }
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterIiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexParameterIiv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterIiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexParameterIiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterIuiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexParameterIuiv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterIuiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexParameterIuiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glTexParameterf", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glTexParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glTexParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glTexParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameteri(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexParameteri", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTexParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glTexParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage1D(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage1D", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage2D(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage2D", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTexStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage2DMultisample(n, n2, n3, n4, n5, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexStorage2DMultisample", n, n2, n3, n4, n5, b);
        }
    }
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage3D(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage3D", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glTexStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage3DMultisample(n, n2, n3, n4, n5, n6, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexStorage3DMultisample", n, n2, n3, n4, n5, n6, b);
        }
    }
    
    @Override
    public void glTexStorage3DMultisampleOES(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.checkContext();
        this.downstreamGLES3.glTexStorage3DMultisampleOES(n, n2, n3, n4, n5, n6, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s)", "glTexStorage3DMultisampleOES", n, n2, n3, n4, n5, n6, b);
        }
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES3.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexSubImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        }
    }
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.checkContext();
        this.downstreamGLES3.glTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexSubImage3D", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        }
    }
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glTextureStorage1DEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage1DEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES3.glTextureStorage2DEXT(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage2DEXT", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.checkContext();
        this.downstreamGLES3.glTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage3DEXT", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glTextureView(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.checkContext();
        this.downstreamGLES3.glTextureView(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureView", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glTransformFeedbackVaryings(final int n, final int n2, final String[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glTransformFeedbackVaryings(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[Ljava.lang.String;>, <int> 0x%X)", "glTransformFeedbackVaryings", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform(final GLUniformData glUniformData) {
        this.checkContext();
        this.downstreamGLES3.glUniform(glUniformData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLUniformData> %s)", "glUniform", glUniformData);
        }
    }
    
    @Override
    public void glUniform1f(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES3.glUniform1f(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glUniform1f", n, n2);
        }
    }
    
    @Override
    public void glUniform1fv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform1fv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glUniform1fv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform1fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform1fv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glUniform1fv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glUniform1i(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glUniform1i(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glUniform1i", n, n2);
        }
    }
    
    @Override
    public void glUniform1iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform1iv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform1iv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform1iv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform1iv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform1iv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform1ui(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glUniform1ui(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glUniform1ui", n, n2);
        }
    }
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform1uiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform1uiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform1uiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform1uiv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2f(final int n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2f(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s)", "glUniform2f", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform2fv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glUniform2fv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glUniform2fv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2fv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glUniform2fv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2i(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2i(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform2i", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform2iv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform2iv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform2iv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2iv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform2iv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2ui(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2ui(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform2ui", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform2uiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform2uiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform2uiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform2uiv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform3f(final int n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES3.glUniform3f(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s)", "glUniform3f", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glUniform3fv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform3fv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glUniform3fv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform3fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform3fv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glUniform3fv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glUniform3i(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glUniform3i(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform3i", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glUniform3iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform3iv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform3iv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform3iv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform3iv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform3iv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform3ui(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glUniform3ui(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform3ui", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform3uiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform3uiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform3uiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform3uiv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES3.glUniform4f(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s, <float> %s)", "glUniform4f", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glUniform4fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform4fv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glUniform4fv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glUniform4fv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform4fv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glUniform4fv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glUniform4i(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform4i", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glUniform4iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform4iv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform4iv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform4iv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform4iv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform4iv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glUniform4ui(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniform4ui", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniform4uiv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glUniform4uiv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniform4uiv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glUniform4uiv", n, n2, n3);
        }
    }
    
    @Override
    public void glUniformBlockBinding(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformBlockBinding(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUniformBlockBinding", n, n2, n3);
        }
    }
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix2fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix2fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x3fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix2x3fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x3fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix2x3fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x3fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix2x3fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x3fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix2x3fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x4fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix2x4fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x4fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix2x4fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x4fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix2x4fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix2x4fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix2x4fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix3fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix3fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x2fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix3x2fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x2fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix3x2fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x2fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix3x2fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x2fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix3x2fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x4fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix3x4fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x4fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix3x4fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x4fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix3x4fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix3x4fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix3x4fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix4fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix4fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x2fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix4x2fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x2fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix4x2fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x2fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix4x2fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x2fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix4x2fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x3fv(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix4x3fv", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x3fv(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix4x3fv", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x3fvNV(n, n2, b, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <[F>, <int> 0x%X)", "glUniformMatrix4x3fvNV", n, n2, b, n3);
        }
    }
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glUniformMatrix4x3fvNV(n, n2, b, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <boolean> %s, <java.nio.FloatBuffer> %s)", "glUniformMatrix4x3fvNV", n, n2, b, floatBuffer);
        }
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        this.checkContext();
        final boolean glUnmapBuffer = this.downstreamGLES3.glUnmapBuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glUnmapBuffer", n);
        }
        return glUnmapBuffer;
    }
    
    @Override
    public void glUseProgram(final int n) {
        this.checkContext();
        this.downstreamGLES3.glUseProgram(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glUseProgram", n);
        }
    }
    
    @Override
    public void glUseProgramStages(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glUseProgramStages(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glUseProgramStages", n, n2, n3);
        }
    }
    
    @Override
    public void glValidateProgram(final int n) {
        this.checkContext();
        this.downstreamGLES3.glValidateProgram(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glValidateProgram", n);
        }
    }
    
    @Override
    public void glValidateProgramPipeline(final int n) {
        this.checkContext();
        this.downstreamGLES3.glValidateProgramPipeline(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glValidateProgramPipeline", n);
        }
    }
    
    @Override
    public void glVertexAttrib1f(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib1f(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glVertexAttrib1f", n, n2);
        }
    }
    
    @Override
    public void glVertexAttrib1fv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib1fv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glVertexAttrib1fv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttrib1fv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib1fv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glVertexAttrib1fv", n, floatBuffer);
        }
    }
    
    @Override
    public void glVertexAttrib2f(final int n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib2f(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s)", "glVertexAttrib2f", n, n2, n3);
        }
    }
    
    @Override
    public void glVertexAttrib2fv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib2fv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glVertexAttrib2fv", n, floatBuffer);
        }
    }
    
    @Override
    public void glVertexAttrib2fv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib2fv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glVertexAttrib2fv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttrib3f(final int n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib3f(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s)", "glVertexAttrib3f", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glVertexAttrib3fv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib3fv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glVertexAttrib3fv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttrib3fv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib3fv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glVertexAttrib3fv", n, floatBuffer);
        }
    }
    
    @Override
    public void glVertexAttrib4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib4f(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s, <float> %s)", "glVertexAttrib4f", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glVertexAttrib4fv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib4fv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glVertexAttrib4fv", n, floatBuffer);
        }
    }
    
    @Override
    public void glVertexAttrib4fv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttrib4fv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glVertexAttrib4fv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribBinding(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribBinding(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glVertexAttribBinding", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribDivisor(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribDivisor(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glVertexAttribDivisor", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribDivisorANGLE(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribDivisorANGLE(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glVertexAttribDivisorANGLE", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribDivisorNV(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribDivisorNV(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glVertexAttribDivisorNV", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribFormat(final int n, final int n2, final int n3, final boolean b, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribFormat(n, n2, n3, b, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <int> 0x%X)", "glVertexAttribFormat", n, n2, n3, b, n4);
        }
    }
    
    @Override
    public void glVertexAttribI4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4i(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glVertexAttribI4i", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glVertexAttribI4iv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4iv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glVertexAttribI4iv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribI4iv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4iv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glVertexAttribI4iv", n, intBuffer);
        }
    }
    
    @Override
    public void glVertexAttribI4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4ui(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glVertexAttribI4ui", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glVertexAttribI4uiv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4uiv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glVertexAttribI4uiv", n, n2);
        }
    }
    
    @Override
    public void glVertexAttribI4uiv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribI4uiv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glVertexAttribI4uiv", n, intBuffer);
        }
    }
    
    @Override
    public void glVertexAttribIFormat(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribIFormat(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glVertexAttribIFormat", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribIPointer(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glVertexAttribIPointer", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribIPointer(n, n2, n3, n4, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glVertexAttribIPointer", n, n2, n3, n4, buffer);
        }
    }
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribPointer(n, n2, n3, b, n4, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <int> 0x%X, <java.nio.Buffer> %s)", "glVertexAttribPointer", n, n2, n3, b, n4, buffer);
        }
    }
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribPointer(n, n2, n3, b, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <boolean> %s, <int> 0x%X, <long> %s)", "glVertexAttribPointer", n, n2, n3, b, n4, n5);
        }
    }
    
    @Override
    public void glVertexAttribPointer(final GLArrayData glArrayData) {
        this.checkContext();
        this.downstreamGLES3.glVertexAttribPointer(glArrayData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLArrayData> %s)", "glVertexAttribPointer", glArrayData);
        }
    }
    
    @Override
    public void glVertexBindingDivisor(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glVertexBindingDivisor(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glVertexBindingDivisor", n, n2);
        }
    }
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES3.glViewport(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glViewport", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES3.glViewportArrayvNV(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glViewportArrayvNV", n, n2, n3);
        }
    }
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glViewportArrayvNV(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glViewportArrayvNV", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glViewportIndexedfNV(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES3.glViewportIndexedfNV(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s, <float> %s)", "glViewportIndexedfNV", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glViewportIndexedfvNV(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES3.glViewportIndexedfvNV(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glViewportIndexedfvNV", n, floatBuffer);
        }
    }
    
    @Override
    public void glViewportIndexedfvNV(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES3.glViewportIndexedfvNV(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glViewportIndexedfvNV", n, n2);
        }
    }
    
    @Override
    public void glWaitSync(final long n, final int n2, final long n3) {
        this.checkContext();
        this.downstreamGLES3.glWaitSync(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<long> %s, <int> 0x%X, <long> %s)", "glWaitSync", n, n2, n3);
        }
    }
    
    @Override
    public boolean hasBasicFBOSupport() {
        return this.downstreamGLES3.hasBasicFBOSupport();
    }
    
    @Override
    public boolean hasFullFBOSupport() {
        return this.downstreamGLES3.hasFullFBOSupport();
    }
    
    @Override
    public boolean hasGLSL() {
        return this.downstreamGLES3.hasGLSL();
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this.downstreamGLES3.isExtensionAvailable(s);
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this.downstreamGLES3.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isGL() {
        return true;
    }
    
    @Override
    public boolean isGL2() {
        return this.downstreamGLES3.isGL2();
    }
    
    @Override
    public boolean isGL2ES1() {
        return this.downstreamGLES3.isGL2ES1();
    }
    
    @Override
    public boolean isGL2ES2() {
        return this.downstreamGLES3.isGL2ES2();
    }
    
    @Override
    public boolean isGL2ES3() {
        return this.downstreamGLES3.isGL2ES3();
    }
    
    @Override
    public boolean isGL2GL3() {
        return this.downstreamGLES3.isGL2GL3();
    }
    
    @Override
    public boolean isGL3() {
        return this.downstreamGLES3.isGL3();
    }
    
    @Override
    public boolean isGL3ES3() {
        return this.downstreamGLES3.isGL3ES3();
    }
    
    @Override
    public boolean isGL3bc() {
        return this.downstreamGLES3.isGL3bc();
    }
    
    @Override
    public boolean isGL3core() {
        return this.downstreamGLES3.isGL3core();
    }
    
    @Override
    public boolean isGL4() {
        return this.downstreamGLES3.isGL4();
    }
    
    @Override
    public boolean isGL4ES3() {
        return this.downstreamGLES3.isGL4ES3();
    }
    
    @Override
    public boolean isGL4bc() {
        return this.downstreamGLES3.isGL4bc();
    }
    
    @Override
    public boolean isGL4core() {
        return this.downstreamGLES3.isGL4core();
    }
    
    @Override
    public boolean isGLES() {
        return this.downstreamGLES3.isGLES();
    }
    
    @Override
    public boolean isGLES1() {
        return this.downstreamGLES3.isGLES1();
    }
    
    @Override
    public boolean isGLES2() {
        return this.downstreamGLES3.isGLES2();
    }
    
    @Override
    public boolean isGLES2Compatible() {
        return this.downstreamGLES3.isGLES2Compatible();
    }
    
    @Override
    public boolean isGLES3() {
        return this.downstreamGLES3.isGLES3();
    }
    
    @Override
    public boolean isGLES31Compatible() {
        return this.downstreamGLES3.isGLES31Compatible();
    }
    
    @Override
    public boolean isGLES32Compatible() {
        return this.downstreamGLES3.isGLES32Compatible();
    }
    
    @Override
    public boolean isGLES3Compatible() {
        return this.downstreamGLES3.isGLES3Compatible();
    }
    
    @Override
    public boolean isGLcore() {
        return this.downstreamGLES3.isGLcore();
    }
    
    @Override
    public boolean isNPOTTextureAvailable() {
        return this.downstreamGLES3.isNPOTTextureAvailable();
    }
    
    @Override
    public boolean isPBOPackBound() {
        return this.downstreamGLES3.isPBOPackBound();
    }
    
    @Override
    public boolean isPBOUnpackBound() {
        return this.downstreamGLES3.isPBOUnpackBound();
    }
    
    @Override
    public boolean isTextureFormatBGRA8888Available() {
        return this.downstreamGLES3.isTextureFormatBGRA8888Available();
    }
    
    @Override
    public boolean isVBOArrayBound() {
        return this.downstreamGLES3.isVBOArrayBound();
    }
    
    @Override
    public boolean isVBOElementArrayBound() {
        return this.downstreamGLES3.isVBOElementArrayBound();
    }
    
    @Override
    public GLBufferStorage mapBuffer(final int n, final int n2) {
        this.checkContext();
        final GLBufferStorage mapBuffer = this.downstreamGLES3.mapBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "mapBuffer", n, n2);
        }
        return mapBuffer;
    }
    
    @Override
    public GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.checkContext();
        final GLBufferStorage mapBufferRange = this.downstreamGLES3.mapBufferRange(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <int> 0x%X)", "mapBufferRange", n, n2, n3, n4);
        }
        return mapBufferRange;
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        this.downstreamGLES3.setSwapInterval(swapInterval);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DebugGLES3 [this 0x" + Integer.toHexString(this.hashCode()) + " implementing com.jogamp.opengl.GLES3,\n\t");
        sb.append(" downstream: " + this.downstreamGLES3.toString() + "\n\t]");
        return sb.toString();
    }
    
    private int checkGLError() {
        return this.downstreamGLES3.glGetError();
    }
    
    private void writeGLError(int glGetError, final String s, final Object... array) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Thread.currentThread().toString());
        sb.append(" glGetError() returned the following error codes after a call to ");
        sb.append(String.format(s, array));
        sb.append(": ");
        int n = 10;
        do {
            switch (glGetError) {
                case 1280: {
                    sb.append("GL_INVALID_ENUM ");
                    break;
                }
                case 1281: {
                    sb.append("GL_INVALID_VALUE ");
                    break;
                }
                case 1282: {
                    sb.append("GL_INVALID_OPERATION ");
                    break;
                }
                case 1285: {
                    sb.append("GL_OUT_OF_MEMORY ");
                    break;
                }
                case 0: {
                    throw new InternalError("Should not be treating GL_NO_ERROR as error");
                }
                default: {
                    sb.append("Unknown glGetError() return value: ");
                    break;
                }
            }
            sb.append("( " + glGetError + " 0x" + Integer.toHexString(glGetError).toUpperCase() + "), ");
        } while (--n >= 0 && (glGetError = this.downstreamGLES3.glGetError()) != 0);
        throw new GLException(sb.toString());
    }
    
    private void checkContext() {
        final GLContext current = GLContext.getCurrent();
        if (current == null) {
            throw new GLException("No OpenGL context is current on this thread");
        }
        if (this._context != null && this._context != current) {
            throw new GLException("This GL object is being incorrectly used with a different GLContext than that which created it");
        }
    }
    
    static {
        DEBUG = Debug.debug("DebugGLES3");
    }
}
