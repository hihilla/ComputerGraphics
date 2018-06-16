// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;

public class DebugGLES1 implements GL2ES1, GLES1
{
    public static final boolean DEBUG;
    private GLContext _context;
    private GLES1 downstreamGLES1;
    
    public DebugGLES1(final GLES1 downstreamGLES1) {
        if (downstreamGLES1 == null) {
            throw new IllegalArgumentException("null downstreamGLES1");
        }
        this.downstreamGLES1 = downstreamGLES1;
        this._context = downstreamGLES1.getContext();
    }
    
    @Override
    public final GL getDownstreamGL() throws GLException {
        return this.downstreamGLES1;
    }
    
    @Override
    public int getBoundBuffer(final int n) {
        return this.downstreamGLES1.getBoundBuffer(n);
    }
    
    @Override
    public int getBoundFramebuffer(final int n) {
        return this.downstreamGLES1.getBoundFramebuffer(n);
    }
    
    @Override
    public GLBufferStorage getBufferStorage(final int n) {
        return this.downstreamGLES1.getBufferStorage(n);
    }
    
    @Override
    public GLContext getContext() {
        return this.downstreamGLES1.getContext();
    }
    
    @Override
    public int getDefaultDrawFramebuffer() {
        return this.downstreamGLES1.getDefaultDrawFramebuffer();
    }
    
    @Override
    public int getDefaultReadBuffer() {
        return this.downstreamGLES1.getDefaultReadBuffer();
    }
    
    @Override
    public int getDefaultReadFramebuffer() {
        return this.downstreamGLES1.getDefaultReadFramebuffer();
    }
    
    @Override
    public Object getExtension(final String s) {
        return this.downstreamGLES1.getExtension(s);
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
        if (this.isGLES1()) {
            return this;
        }
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
        return this.downstreamGLES1.getGLProfile();
    }
    
    @Override
    public int getMaxRenderbufferSamples() {
        return this.downstreamGLES1.getMaxRenderbufferSamples();
    }
    
    @Override
    public Object getPlatformGLExtensions() {
        return this.downstreamGLES1.getPlatformGLExtensions();
    }
    
    @Override
    public GL getRootGL() {
        return this.downstreamGLES1.getRootGL();
    }
    
    @Override
    public int getSwapInterval() {
        return this.downstreamGLES1.getSwapInterval();
    }
    
    @Override
    public void glActiveTexture(final int n) {
        this.checkContext();
        this.downstreamGLES1.glActiveTexture(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glActiveTexture", n);
        }
    }
    
    @Override
    public void glAlphaFunc(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glAlphaFunc(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glAlphaFunc", n, n2);
        }
    }
    
    @Override
    public void glAlphaFuncx(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glAlphaFuncx(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glAlphaFuncx", n, n2);
        }
    }
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBindBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindBuffer", n, n2);
        }
    }
    
    @Override
    public void glBindFramebuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBindFramebuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindFramebuffer", n, n2);
        }
    }
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBindRenderbuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindRenderbuffer", n, n2);
        }
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBindTexture(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBindTexture", n, n2);
        }
    }
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        this.checkContext();
        this.downstreamGLES1.glBindVertexArrayOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBindVertexArrayOES", n);
        }
    }
    
    @Override
    public void glBlendEquation(final int n) {
        this.checkContext();
        this.downstreamGLES1.glBlendEquation(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glBlendEquation", n);
        }
    }
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBlendEquationSeparate(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendEquationSeparate", n, n2);
        }
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glBlendFunc(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glBlendFunc", n, n2);
        }
    }
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glBlendFuncSeparate(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glBlendFuncSeparate", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glBufferData(n, n2, buffer, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <java.nio.Buffer> %s, <int> 0x%X)", "glBufferData", n, n2, buffer, n3);
        }
    }
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glBufferSubData(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <java.nio.Buffer> %s)", "glBufferSubData", n, n2, n3, buffer);
        }
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        this.checkContext();
        final int glCheckFramebufferStatus = this.downstreamGLES1.glCheckFramebufferStatus(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCheckFramebufferStatus", n);
        }
        return glCheckFramebufferStatus;
    }
    
    @Override
    public void glClear(final int n) {
        this.checkContext();
        this.downstreamGLES1.glClear(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClear", n);
        }
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES1.glClearColor(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s)", "glClearColor", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glClearColorx(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glClearColorx(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glClearColorx", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.checkContext();
        this.downstreamGLES1.glClearDepth(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s)", "glClearDepth", n);
        }
    }
    
    @Override
    public void glClearDepthf(final float n) {
        this.checkContext();
        this.downstreamGLES1.glClearDepthf(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glClearDepthf", n);
        }
    }
    
    @Override
    public void glClearDepthx(final int n) {
        this.checkContext();
        this.downstreamGLES1.glClearDepthx(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClearDepthx", n);
        }
    }
    
    @Override
    public void glClearStencil(final int n) {
        this.checkContext();
        this.downstreamGLES1.glClearStencil(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClearStencil", n);
        }
    }
    
    @Override
    public void glClientActiveTexture(final int n) {
        this.checkContext();
        this.downstreamGLES1.glClientActiveTexture(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glClientActiveTexture", n);
        }
    }
    
    @Override
    public void glClipPlanef(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanef(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glClipPlanef", n, floatBuffer);
        }
    }
    
    @Override
    public void glClipPlanef(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanef(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glClipPlanef", n, n2);
        }
    }
    
    @Override
    public void glClipPlanefIMG(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanefIMG(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glClipPlanefIMG", n, floatBuffer);
        }
    }
    
    @Override
    public void glClipPlanefIMG(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanefIMG(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glClipPlanefIMG", n, n2);
        }
    }
    
    @Override
    public void glClipPlanex(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanex(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glClipPlanex", n, intBuffer);
        }
    }
    
    @Override
    public void glClipPlanex(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanex(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glClipPlanex", n, n2);
        }
    }
    
    @Override
    public void glClipPlanexIMG(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanexIMG(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glClipPlanexIMG", n, intBuffer);
        }
    }
    
    @Override
    public void glClipPlanexIMG(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glClipPlanexIMG(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glClipPlanexIMG", n, n2);
        }
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES1.glColor4f(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s)", "glColor4f", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        this.checkContext();
        this.downstreamGLES1.glColor4ub(b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<byte> %s, <byte> %s, <byte> %s, <byte> %s)", "glColor4ub", b, b2, b3, b4);
        }
    }
    
    @Override
    public void glColor4x(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glColor4x(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glColor4x", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.checkContext();
        this.downstreamGLES1.glColorMask(b, b2, b3, b4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<boolean> %s, <boolean> %s, <boolean> %s, <boolean> %s)", "glColorMask", b, b2, b3, b4);
        }
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glColorPointer(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glColorPointer", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glColorPointer(final GLArrayData glArrayData) {
        this.checkContext();
        this.downstreamGLES1.glColorPointer(glArrayData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLArrayData> %s)", "glColorPointer", glArrayData);
        }
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkContext();
        this.downstreamGLES1.glColorPointer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glColorPointer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexImage2D", n, n2, n3, n4, n5, n6, n7, buffer);
        }
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.checkContext();
        this.downstreamGLES1.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glCompressedTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES1.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glCompressedTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.checkContext();
        this.downstreamGLES1.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTexImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.checkContext();
        this.downstreamGLES1.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8);
        }
    }
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glCopyTextureLevelsAPPLE(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glCopyTextureLevelsAPPLE", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glCullFace(final int n) {
        this.checkContext();
        this.downstreamGLES1.glCullFace(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCullFace", n);
        }
    }
    
    @Override
    public void glCurrentPaletteMatrixOES(final int n) {
        this.checkContext();
        this.downstreamGLES1.glCurrentPaletteMatrixOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glCurrentPaletteMatrixOES", n);
        }
    }
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDeleteBuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteBuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDeleteBuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteBuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDeleteFramebuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteFramebuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDeleteFramebuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteFramebuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDeleteRenderbuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteRenderbuffers", n, n2);
        }
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDeleteRenderbuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteRenderbuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDeleteTextures(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteTextures", n, intBuffer);
        }
    }
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDeleteTextures(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteTextures", n, n2);
        }
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDeleteVertexArraysOES(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glDeleteVertexArraysOES", n, n2);
        }
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDeleteVertexArraysOES(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glDeleteVertexArraysOES", n, intBuffer);
        }
    }
    
    @Override
    public void glDepthFunc(final int n) {
        this.checkContext();
        this.downstreamGLES1.glDepthFunc(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDepthFunc", n);
        }
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        this.checkContext();
        this.downstreamGLES1.glDepthMask(b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<boolean> %s)", "glDepthMask", b);
        }
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.checkContext();
        this.downstreamGLES1.glDepthRange(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s, <double> %s)", "glDepthRange", n, n2);
        }
    }
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glDepthRangef(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s)", "glDepthRangef", n, n2);
        }
    }
    
    @Override
    public void glDepthRangex(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glDepthRangex(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glDepthRangex", n, n2);
        }
    }
    
    @Override
    public void glDisable(final int n) {
        this.checkContext();
        this.downstreamGLES1.glDisable(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisable", n);
        }
    }
    
    @Override
    public void glDisableClientState(final int n) {
        this.checkContext();
        this.downstreamGLES1.glDisableClientState(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisableClientState", n);
        }
    }
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES1.glDisableDriverControlQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glDisableDriverControlQCOM", n);
        }
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glDiscardFramebufferEXT(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glDiscardFramebufferEXT", n, n2, n3);
        }
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDiscardFramebufferEXT(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glDiscardFramebufferEXT", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glDrawArrays(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawArrays", n, n2, n3);
        }
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.checkContext();
        this.downstreamGLES1.glDrawElements(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glDrawElements", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glDrawElements(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glDrawElements", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glDrawTexfOES(final float n, final float n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexfOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glDrawTexfOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawTexfvOES(final float[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexfvOES(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[F>, <int> 0x%X)", "glDrawTexfvOES", n);
        }
    }
    
    @Override
    public void glDrawTexfvOES(final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexfvOES(floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.FloatBuffer> %s)", "glDrawTexfvOES", floatBuffer);
        }
    }
    
    @Override
    public void glDrawTexiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexiOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawTexiOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawTexivOES(final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexivOES(intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s)", "glDrawTexivOES", intBuffer);
        }
    }
    
    @Override
    public void glDrawTexivOES(final int[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexivOES(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X)", "glDrawTexivOES", n);
        }
    }
    
    @Override
    public void glDrawTexsOES(final short n, final short n2, final short n3, final short n4, final short n5) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexsOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<short> %s, <short> %s, <short> %s, <short> %s, <short> %s)", "glDrawTexsOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawTexsvOES(final short[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexsvOES(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[S>, <int> 0x%X)", "glDrawTexsvOES", n);
        }
    }
    
    @Override
    public void glDrawTexsvOES(final ShortBuffer shortBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexsvOES(shortBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.ShortBuffer> %s)", "glDrawTexsvOES", shortBuffer);
        }
    }
    
    @Override
    public void glDrawTexxOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexxOES(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glDrawTexxOES", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glDrawTexxvOES(final int[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexxvOES(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X)", "glDrawTexxvOES", n);
        }
    }
    
    @Override
    public void glDrawTexxvOES(final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glDrawTexxvOES(intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s)", "glDrawTexxvOES", intBuffer);
        }
    }
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        this.checkContext();
        this.downstreamGLES1.glEGLImageTargetRenderbufferStorageOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s)", "glEGLImageTargetRenderbufferStorageOES", n, n2);
        }
    }
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        this.checkContext();
        this.downstreamGLES1.glEGLImageTargetTexture2DOES(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s)", "glEGLImageTargetTexture2DOES", n, n2);
        }
    }
    
    @Override
    public void glEnable(final int n) {
        this.checkContext();
        this.downstreamGLES1.glEnable(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnable", n);
        }
    }
    
    @Override
    public void glEnableClientState(final int n) {
        this.checkContext();
        this.downstreamGLES1.glEnableClientState(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnableClientState", n);
        }
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES1.glEnableDriverControlQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEnableDriverControlQCOM", n);
        }
    }
    
    @Override
    public void glEndTilingQCOM(final int n) {
        this.checkContext();
        this.downstreamGLES1.glEndTilingQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glEndTilingQCOM", n);
        }
    }
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        this.checkContext();
        this.downstreamGLES1.glExtGetBufferPointervQCOM(n, pointerBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <com.jogamp.common.nio.PointerBuffer> %s)", "glExtGetBufferPointervQCOM", n, pointerBuffer);
        }
    }
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetBuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetBuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetBuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetBuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetFramebuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetFramebuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetFramebuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetFramebuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glExtGetProgramBinarySourceQCOM(n, n2, byteBuffer, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.ByteBuffer> %s, <java.nio.IntBuffer> %s)", "glExtGetProgramBinarySourceQCOM", n, n2, byteBuffer, intBuffer);
        }
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glExtGetProgramBinarySourceQCOM(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[B>, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetProgramBinarySourceQCOM", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetProgramsQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetProgramsQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetProgramsQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetProgramsQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetRenderbuffersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetRenderbuffersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetRenderbuffersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetRenderbuffersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetShadersQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetShadersQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetShadersQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetShadersQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, array, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetTexLevelParameterivQCOM", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetTexLevelParameterivQCOM", n, n2, n3, n4, intBuffer);
        }
    }
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glExtGetTexSubImageQCOM(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glExtGetTexSubImageQCOM", n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        }
    }
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtGetTexturesQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glExtGetTexturesQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glExtGetTexturesQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glExtGetTexturesQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        this.checkContext();
        final boolean glExtIsProgramBinaryQCOM = this.downstreamGLES1.glExtIsProgramBinaryQCOM(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glExtIsProgramBinaryQCOM", n);
        }
        return glExtIsProgramBinaryQCOM;
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glExtTexObjectStateOverrideiQCOM(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glExtTexObjectStateOverrideiQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glFinish() {
        this.checkContext();
        this.downstreamGLES1.glFinish();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glFinish");
        }
    }
    
    @Override
    public void glFlush() {
        this.checkContext();
        this.downstreamGLES1.glFlush();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glFlush");
        }
    }
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        this.checkContext();
        this.downstreamGLES1.glFlushMappedBufferRange(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s)", "glFlushMappedBufferRange", n, n2, n3);
        }
    }
    
    @Override
    public void glFogf(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glFogf(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glFogf", n, n2);
        }
    }
    
    @Override
    public void glFogfv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glFogfv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glFogfv", n, floatBuffer);
        }
    }
    
    @Override
    public void glFogfv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glFogfv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glFogfv", n, n2);
        }
    }
    
    @Override
    public void glFogx(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glFogx(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glFogx", n, n2);
        }
    }
    
    @Override
    public void glFogxv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glFogxv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glFogxv", n, n2);
        }
    }
    
    @Override
    public void glFogxv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glFogxv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glFogxv", n, intBuffer);
        }
    }
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glFramebufferRenderbuffer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferRenderbuffer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glFramebufferTexture2D(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2D", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glFramebufferTexture2DMultisampleEXT(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2DMultisampleEXT", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glFramebufferTexture2DMultisampleIMG(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFramebufferTexture2DMultisampleIMG", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFrontFace(final int n) {
        this.checkContext();
        this.downstreamGLES1.glFrontFace(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glFrontFace", n);
        }
    }
    
    @Override
    public void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.checkContext();
        this.downstreamGLES1.glFrustum(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s, <double> %s, <double> %s, <double> %s, <double> %s, <double> %s)", "glFrustum", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.checkContext();
        this.downstreamGLES1.glFrustumf(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glFrustumf", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glFrustumx(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glFrustumx(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glFrustumx", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGenBuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenBuffers", n, n2);
        }
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGenBuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenBuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGenFramebuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenFramebuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGenFramebuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenFramebuffers", n, n2);
        }
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGenRenderbuffers(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenRenderbuffers", n, intBuffer);
        }
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGenRenderbuffers(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenRenderbuffers", n, n2);
        }
    }
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGenTextures(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenTextures", n, n2);
        }
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGenTextures(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenTextures", n, intBuffer);
        }
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGenVertexArraysOES(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGenVertexArraysOES", n, intBuffer);
        }
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGenVertexArraysOES(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGenVertexArraysOES", n, n2);
        }
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        this.checkContext();
        this.downstreamGLES1.glGenerateMipmap(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glGenerateMipmap", n);
        }
    }
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetBooleanv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[B>, <int> 0x%X)", "glGetBooleanv", n, n2);
        }
    }
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetBooleanv(n, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.ByteBuffer> %s)", "glGetBooleanv", n, byteBuffer);
        }
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetBufferParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetBufferParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetBufferParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetBufferParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetClipPlanef(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetClipPlanef(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetClipPlanef", n, floatBuffer);
        }
    }
    
    @Override
    public void glGetClipPlanef(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetClipPlanef(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glGetClipPlanef", n, n2);
        }
    }
    
    @Override
    public void glGetClipPlanex(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetClipPlanex(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGetClipPlanex", n, n2);
        }
    }
    
    @Override
    public void glGetClipPlanex(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetClipPlanex(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGetClipPlanex", n, intBuffer);
        }
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetDriverControlStringQCOM(n, n2, intBuffer, byteBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s, <java.nio.ByteBuffer> %s)", "glGetDriverControlStringQCOM", n, n2, intBuffer, byteBuffer);
        }
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glGetDriverControlStringQCOM(n, n2, array, n3, array2, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X, <[B>, <int> 0x%X)", "glGetDriverControlStringQCOM", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetDriverControlsQCOM(array, n, n2, array2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetDriverControlsQCOM", n, n2, n3);
        }
    }
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.checkContext();
        this.downstreamGLES1.glGetDriverControlsQCOM(intBuffer, n, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetDriverControlsQCOM", intBuffer, n, intBuffer2);
        }
    }
    
    @Override
    public int glGetError() {
        this.checkContext();
        final int glGetError = this.downstreamGLES1.glGetError();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glGetError");
        }
        return glGetError;
    }
    
    @Override
    public void glGetFixedv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetFixedv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGetFixedv", n, intBuffer);
        }
    }
    
    @Override
    public void glGetFixedv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetFixedv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGetFixedv", n, n2);
        }
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetFloatv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetFloatv", n, floatBuffer);
        }
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetFloatv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glGetFloatv", n, n2);
        }
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetFramebufferAttachmentParameteriv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glGetFramebufferAttachmentParameteriv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetFramebufferAttachmentParameteriv", n, n2, n3, n4);
        }
    }
    
    @Override
    public int glGetGraphicsResetStatus() {
        this.checkContext();
        final int glGetGraphicsResetStatus = this.downstreamGLES1.glGetGraphicsResetStatus();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glGetGraphicsResetStatus");
        }
        return glGetGraphicsResetStatus;
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetIntegerv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glGetIntegerv", n, intBuffer);
        }
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glGetIntegerv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glGetIntegerv", n, n2);
        }
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetLightfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetLightfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetLightfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetLightfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetLightxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetLightxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetLightxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetLightxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetLightxv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetMaterialfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetMaterialfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetMaterialfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetMaterialfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetMaterialxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetMaterialxv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetMaterialxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetMaterialxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetRenderbufferParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetRenderbufferParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetRenderbufferParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetRenderbufferParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public String glGetString(final int n) {
        this.checkContext();
        final String glGetString = this.downstreamGLES1.glGetString(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glGetString", n);
        }
        return glGetString;
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnvfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetTexEnvfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnvfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetTexEnvfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnviv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexEnviv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnviv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexEnviv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnvxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexEnvxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexEnvxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexEnvxv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGenfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetTexGenfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGenfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetTexGenfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGeniv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexGeniv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGeniv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexGeniv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGenxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexGenxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexGenxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexGenxv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetTexParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetTexParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameterxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetTexParameterxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glGetTexParameterxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetTexParameterxv", n, n2, n3);
        }
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetnUniformfv(n, n2, n3, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glGetnUniformfv", n, n2, n3, floatBuffer);
        }
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glGetnUniformfv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glGetnUniformfv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glGetnUniformiv(n, n2, n3, array, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glGetnUniformiv", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glGetnUniformiv(n, n2, n3, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glGetnUniformiv", n, n2, n3, intBuffer);
        }
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glHint(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glHint", n, n2);
        }
    }
    
    @Override
    public boolean glIsBuffer(final int n) {
        this.checkContext();
        final boolean glIsBuffer = this.downstreamGLES1.glIsBuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsBuffer", n);
        }
        return glIsBuffer;
    }
    
    @Override
    public boolean glIsEnabled(final int n) {
        this.checkContext();
        final boolean glIsEnabled = this.downstreamGLES1.glIsEnabled(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsEnabled", n);
        }
        return glIsEnabled;
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        this.checkContext();
        final boolean glIsFramebuffer = this.downstreamGLES1.glIsFramebuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsFramebuffer", n);
        }
        return glIsFramebuffer;
    }
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        this.checkContext();
        final boolean glIsRenderbuffer = this.downstreamGLES1.glIsRenderbuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsRenderbuffer", n);
        }
        return glIsRenderbuffer;
    }
    
    @Override
    public boolean glIsTexture(final int n) {
        this.checkContext();
        final boolean glIsTexture = this.downstreamGLES1.glIsTexture(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsTexture", n);
        }
        return glIsTexture;
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        this.checkContext();
        final boolean glIsVertexArrayOES = this.downstreamGLES1.glIsVertexArrayOES(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glIsVertexArrayOES", n);
        }
        return glIsVertexArrayOES;
    }
    
    @Override
    public void glLightModelf(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glLightModelf(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glLightModelf", n, n2);
        }
    }
    
    @Override
    public void glLightModelfv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLightModelfv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glLightModelfv", n, floatBuffer);
        }
    }
    
    @Override
    public void glLightModelfv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glLightModelfv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glLightModelfv", n, n2);
        }
    }
    
    @Override
    public void glLightModelx(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glLightModelx(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glLightModelx", n, n2);
        }
    }
    
    @Override
    public void glLightModelxv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glLightModelxv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glLightModelxv", n, n2);
        }
    }
    
    @Override
    public void glLightModelxv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLightModelxv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glLightModelxv", n, intBuffer);
        }
    }
    
    @Override
    public void glLightf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glLightf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glLightf", n, n2, n3);
        }
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glLightfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glLightfv", n, n2, n3);
        }
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLightfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glLightfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glLightx(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glLightx(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glLightx", n, n2, n3);
        }
    }
    
    @Override
    public void glLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLightxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glLightxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glLightxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glLightxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glLightxv", n, n2, n3);
        }
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.checkContext();
        this.downstreamGLES1.glLineWidth(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glLineWidth", n);
        }
    }
    
    @Override
    public void glLineWidthx(final int n) {
        this.checkContext();
        this.downstreamGLES1.glLineWidthx(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glLineWidthx", n);
        }
    }
    
    @Override
    public void glLoadIdentity() {
        this.checkContext();
        this.downstreamGLES1.glLoadIdentity();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glLoadIdentity");
        }
    }
    
    @Override
    public void glLoadMatrixf(final float[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glLoadMatrixf(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[F>, <int> 0x%X)", "glLoadMatrixf", n);
        }
    }
    
    @Override
    public void glLoadMatrixf(final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLoadMatrixf(floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.FloatBuffer> %s)", "glLoadMatrixf", floatBuffer);
        }
    }
    
    @Override
    public void glLoadMatrixx(final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glLoadMatrixx(intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s)", "glLoadMatrixx", intBuffer);
        }
    }
    
    @Override
    public void glLoadMatrixx(final int[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glLoadMatrixx(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X)", "glLoadMatrixx", n);
        }
    }
    
    @Override
    public void glLoadPaletteFromModelViewMatrixOES() {
        this.checkContext();
        this.downstreamGLES1.glLoadPaletteFromModelViewMatrixOES();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glLoadPaletteFromModelViewMatrixOES");
        }
    }
    
    @Override
    public void glLogicOp(final int n) {
        this.checkContext();
        this.downstreamGLES1.glLogicOp(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glLogicOp", n);
        }
    }
    
    @Override
    public ByteBuffer glMapBuffer(final int n, final int n2) {
        this.checkContext();
        final ByteBuffer glMapBuffer = this.downstreamGLES1.glMapBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glMapBuffer", n, n2);
        }
        return glMapBuffer;
    }
    
    @Override
    public ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.checkContext();
        final ByteBuffer glMapBufferRange = this.downstreamGLES1.glMapBufferRange(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <int> 0x%X)", "glMapBufferRange", n, n2, n3, n4);
        }
        return glMapBufferRange;
    }
    
    @Override
    public void glMaterialf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glMaterialf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glMaterialf", n, n2, n3);
        }
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glMaterialfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glMaterialfv", n, n2, n3);
        }
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glMaterialfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glMaterialfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glMaterialx(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glMaterialx(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glMaterialx", n, n2, n3);
        }
    }
    
    @Override
    public void glMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glMaterialxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glMaterialxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glMaterialxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glMaterialxv", n, n2, n3);
        }
    }
    
    @Override
    public void glMatrixIndexPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glMatrixIndexPointerOES(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glMatrixIndexPointerOES", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glMatrixMode(final int n) {
        this.checkContext();
        this.downstreamGLES1.glMatrixMode(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glMatrixMode", n);
        }
    }
    
    @Override
    public void glMultMatrixf(final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glMultMatrixf(floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.FloatBuffer> %s)", "glMultMatrixf", floatBuffer);
        }
    }
    
    @Override
    public void glMultMatrixf(final float[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glMultMatrixf(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[F>, <int> 0x%X)", "glMultMatrixf", n);
        }
    }
    
    @Override
    public void glMultMatrixx(final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glMultMatrixx(intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s)", "glMultMatrixx", intBuffer);
        }
    }
    
    @Override
    public void glMultMatrixx(final int[] array, final int n) {
        this.checkContext();
        this.downstreamGLES1.glMultMatrixx(array, n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X)", "glMultMatrixx", n);
        }
    }
    
    @Override
    public void glMultiTexCoord4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.checkContext();
        this.downstreamGLES1.glMultiTexCoord4f(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s, <float> %s, <float> %s, <float> %s)", "glMultiTexCoord4f", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glMultiTexCoord4x(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glMultiTexCoord4x(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glMultiTexCoord4x", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glNormal3f(final float n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glNormal3f(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s)", "glNormal3f", n, n2, n3);
        }
    }
    
    @Override
    public void glNormal3x(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glNormal3x(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glNormal3x", n, n2, n3);
        }
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final long n3) {
        this.checkContext();
        this.downstreamGLES1.glNormalPointer(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <long> %s)", "glNormalPointer", n, n2, n3);
        }
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glNormalPointer(n, n2, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glNormalPointer", n, n2, buffer);
        }
    }
    
    @Override
    public void glNormalPointer(final GLArrayData glArrayData) {
        this.checkContext();
        this.downstreamGLES1.glNormalPointer(glArrayData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLArrayData> %s)", "glNormalPointer", glArrayData);
        }
    }
    
    @Override
    public void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.checkContext();
        this.downstreamGLES1.glOrtho(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<double> %s, <double> %s, <double> %s, <double> %s, <double> %s, <double> %s)", "glOrtho", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.checkContext();
        this.downstreamGLES1.glOrthof(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s, <float> %s, <float> %s)", "glOrthof", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glOrthox(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glOrthox(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glOrthox", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glPixelStorei(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPixelStorei", n, n2);
        }
    }
    
    @Override
    public void glPointParameterf(final int n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterf(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <float> %s)", "glPointParameterf", n, n2);
        }
    }
    
    @Override
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterfv(n, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.FloatBuffer> %s)", "glPointParameterfv", n, floatBuffer);
        }
    }
    
    @Override
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterfv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[F>, <int> 0x%X)", "glPointParameterfv", n, n2);
        }
    }
    
    @Override
    public void glPointParameterx(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterx(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPointParameterx", n, n2);
        }
    }
    
    @Override
    public void glPointParameterxv(final int n, final int[] array, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterxv(n, array, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <[I>, <int> 0x%X)", "glPointParameterxv", n, n2);
        }
    }
    
    @Override
    public void glPointParameterxv(final int n, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glPointParameterxv(n, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <java.nio.IntBuffer> %s)", "glPointParameterxv", n, intBuffer);
        }
    }
    
    @Override
    public void glPointSize(final float n) {
        this.checkContext();
        this.downstreamGLES1.glPointSize(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s)", "glPointSize", n);
        }
    }
    
    @Override
    public void glPointSizePointerOES(final int n, final int n2, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glPointSizePointerOES(n, n2, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glPointSizePointerOES", n, n2, buffer);
        }
    }
    
    @Override
    public void glPointSizex(final int n) {
        this.checkContext();
        this.downstreamGLES1.glPointSizex(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glPointSizex", n);
        }
    }
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        this.checkContext();
        this.downstreamGLES1.glPolygonOffset(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s)", "glPolygonOffset", n, n2);
        }
    }
    
    @Override
    public void glPolygonOffsetx(final int n, final int n2) {
        this.checkContext();
        this.downstreamGLES1.glPolygonOffsetx(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "glPolygonOffsetx", n, n2);
        }
    }
    
    @Override
    public void glPopMatrix() {
        this.checkContext();
        this.downstreamGLES1.glPopMatrix();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glPopMatrix");
        }
    }
    
    @Override
    public void glPushMatrix() {
        this.checkContext();
        this.downstreamGLES1.glPushMatrix();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glPushMatrix");
        }
    }
    
    @Override
    public int glQueryMatrixxOES(final int[] array, final int n, final int[] array2, final int n2) {
        this.checkContext();
        final int glQueryMatrixxOES = this.downstreamGLES1.glQueryMatrixxOES(array, n, array2, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<[I>, <int> 0x%X, <[I>, <int> 0x%X)", "glQueryMatrixxOES", n, n2);
        }
        return glQueryMatrixxOES;
    }
    
    @Override
    public int glQueryMatrixxOES(final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.checkContext();
        final int glQueryMatrixxOES = this.downstreamGLES1.glQueryMatrixxOES(intBuffer, intBuffer2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<java.nio.IntBuffer> %s, <java.nio.IntBuffer> %s)", "glQueryMatrixxOES", intBuffer, intBuffer2);
        }
        return glQueryMatrixxOES;
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.checkContext();
        this.downstreamGLES1.glReadPixels(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glReadPixels", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glReadPixels", n, n2, n3, n4, n5, n6, buffer);
        }
    }
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glReadnPixels(n, n2, n3, n4, n5, n6, n7, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glReadnPixels", n, n2, n3, n4, n5, n6, n7, buffer);
        }
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glRenderbufferStorage(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorage", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisample", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisampleEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glRenderbufferStorageMultisampleIMG(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRenderbufferStorageMultisampleIMG", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        this.checkContext();
        this.downstreamGLES1.glResolveMultisampleFramebuffer();
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s()", "glResolveMultisampleFramebuffer");
        }
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.checkContext();
        this.downstreamGLES1.glRotatef(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s, <float> %s)", "glRotatef", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glRotatex(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glRotatex(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glRotatex", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        this.checkContext();
        this.downstreamGLES1.glSampleCoverage(n, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <boolean> %s)", "glSampleCoverage", n, b);
        }
    }
    
    @Override
    public void glSampleCoveragex(final int n, final boolean b) {
        this.checkContext();
        this.downstreamGLES1.glSampleCoveragex(n, b);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <boolean> %s)", "glSampleCoveragex", n, b);
        }
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glScalef(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s)", "glScalef", n, n2, n3);
        }
    }
    
    @Override
    public void glScalex(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glScalex(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glScalex", n, n2, n3);
        }
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glScissor(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glScissor", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glShadeModel(final int n) {
        this.checkContext();
        this.downstreamGLES1.glShadeModel(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glShadeModel", n);
        }
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glStartTilingQCOM(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStartTilingQCOM", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glStencilFunc(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilFunc", n, n2, n3);
        }
    }
    
    @Override
    public void glStencilMask(final int n) {
        this.checkContext();
        this.downstreamGLES1.glStencilMask(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glStencilMask", n);
        }
    }
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glStencilOp(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glStencilOp", n, n2, n3);
        }
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glTexCoordPointer(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexCoordPointer", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glTexCoordPointer(final GLArrayData glArrayData) {
        this.checkContext();
        this.downstreamGLES1.glTexCoordPointer(glArrayData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLArrayData> %s)", "glTexCoordPointer", glArrayData);
        }
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkContext();
        this.downstreamGLES1.glTexCoordPointer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexCoordPointer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glTexEnvf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glTexEnvf", n, n2, n3);
        }
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glTexEnvfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glTexEnvfv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvi(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexEnvi", n, n2, n3);
        }
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnviv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexEnviv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexEnviv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexEnviv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexEnvx(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvx(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexEnvx", n, n2, n3);
        }
    }
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexEnvxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexEnvxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexEnvxv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGenf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGenf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glTexGenf", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGenfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glTexGenfv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexGenfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glTexGenfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glTexGeni(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGeni(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexGeni", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGeniv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexGeniv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexGeniv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexGeniv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexGenx(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGenx(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexGenx", n, n2, n3);
        }
    }
    
    @Override
    public void glTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexGenxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexGenxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexGenxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexGenxv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES1.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterf(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <float> %s)", "glTexParameterf", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterfv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[F>, <int> 0x%X)", "glTexParameterfv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterfv(n, n2, floatBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.FloatBuffer> %s)", "glTexParameterfv", n, n2, floatBuffer);
        }
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameteri(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexParameteri", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexParameteriv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexParameteriv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameteriv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexParameteriv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterx(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterx(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexParameterx", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterxv(n, n2, array, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <[I>, <int> 0x%X)", "glTexParameterxv", n, n2, n3);
        }
    }
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.checkContext();
        this.downstreamGLES1.glTexParameterxv(n, n2, intBuffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <java.nio.IntBuffer> %s)", "glTexParameterxv", n, n2, intBuffer);
        }
    }
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glTexStorage1D(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage1D", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glTexStorage2D(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage2D", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glTexStorage3D(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTexStorage3D", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, buffer);
        }
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.checkContext();
        this.downstreamGLES1.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glTexSubImage2D", n, n2, n3, n4, n5, n6, n7, n8, n9);
        }
    }
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.checkContext();
        this.downstreamGLES1.glTextureStorage1DEXT(n, n2, n3, n4, n5);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage1DEXT", n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.checkContext();
        this.downstreamGLES1.glTextureStorage2DEXT(n, n2, n3, n4, n5, n6);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage2DEXT", n, n2, n3, n4, n5, n6);
        }
    }
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.checkContext();
        this.downstreamGLES1.glTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTextureStorage3DEXT", n, n2, n3, n4, n5, n6, n7);
        }
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        this.checkContext();
        this.downstreamGLES1.glTranslatef(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<float> %s, <float> %s, <float> %s)", "glTranslatef", n, n2, n3);
        }
    }
    
    @Override
    public void glTranslatex(final int n, final int n2, final int n3) {
        this.checkContext();
        this.downstreamGLES1.glTranslatex(n, n2, n3);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X)", "glTranslatex", n, n2, n3);
        }
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        this.checkContext();
        final boolean glUnmapBuffer = this.downstreamGLES1.glUnmapBuffer(n);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X)", "glUnmapBuffer", n);
        }
        return glUnmapBuffer;
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glVertexPointer(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glVertexPointer", n, n2, n3, buffer);
        }
    }
    
    @Override
    public void glVertexPointer(final GLArrayData glArrayData) {
        this.checkContext();
        this.downstreamGLES1.glVertexPointer(glArrayData);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<com.jogamp.opengl.GLArrayData> %s)", "glVertexPointer", glArrayData);
        }
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        this.checkContext();
        this.downstreamGLES1.glVertexPointer(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <long> %s)", "glVertexPointer", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        this.checkContext();
        this.downstreamGLES1.glViewport(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <int> 0x%X)", "glViewport", n, n2, n3, n4);
        }
    }
    
    @Override
    public void glWeightPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        this.checkContext();
        this.downstreamGLES1.glWeightPointerOES(n, n2, n3, buffer);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X, <int> 0x%X, <java.nio.Buffer> %s)", "glWeightPointerOES", n, n2, n3, buffer);
        }
    }
    
    @Override
    public boolean hasBasicFBOSupport() {
        return this.downstreamGLES1.hasBasicFBOSupport();
    }
    
    @Override
    public boolean hasFullFBOSupport() {
        return this.downstreamGLES1.hasFullFBOSupport();
    }
    
    @Override
    public boolean hasGLSL() {
        return this.downstreamGLES1.hasGLSL();
    }
    
    @Override
    public boolean isExtensionAvailable(final String s) {
        return this.downstreamGLES1.isExtensionAvailable(s);
    }
    
    @Override
    public boolean isFunctionAvailable(final String s) {
        return this.downstreamGLES1.isFunctionAvailable(s);
    }
    
    @Override
    public boolean isGL() {
        return true;
    }
    
    @Override
    public boolean isGL2() {
        return this.downstreamGLES1.isGL2();
    }
    
    @Override
    public boolean isGL2ES1() {
        return this.downstreamGLES1.isGL2ES1();
    }
    
    @Override
    public boolean isGL2ES2() {
        return this.downstreamGLES1.isGL2ES2();
    }
    
    @Override
    public boolean isGL2ES3() {
        return this.downstreamGLES1.isGL2ES3();
    }
    
    @Override
    public boolean isGL2GL3() {
        return this.downstreamGLES1.isGL2GL3();
    }
    
    @Override
    public boolean isGL3() {
        return this.downstreamGLES1.isGL3();
    }
    
    @Override
    public boolean isGL3ES3() {
        return this.downstreamGLES1.isGL3ES3();
    }
    
    @Override
    public boolean isGL3bc() {
        return this.downstreamGLES1.isGL3bc();
    }
    
    @Override
    public boolean isGL3core() {
        return this.downstreamGLES1.isGL3core();
    }
    
    @Override
    public boolean isGL4() {
        return this.downstreamGLES1.isGL4();
    }
    
    @Override
    public boolean isGL4ES3() {
        return this.downstreamGLES1.isGL4ES3();
    }
    
    @Override
    public boolean isGL4bc() {
        return this.downstreamGLES1.isGL4bc();
    }
    
    @Override
    public boolean isGL4core() {
        return this.downstreamGLES1.isGL4core();
    }
    
    @Override
    public boolean isGLES() {
        return this.downstreamGLES1.isGLES();
    }
    
    @Override
    public boolean isGLES1() {
        return this.downstreamGLES1.isGLES1();
    }
    
    @Override
    public boolean isGLES2() {
        return this.downstreamGLES1.isGLES2();
    }
    
    @Override
    public boolean isGLES2Compatible() {
        return this.downstreamGLES1.isGLES2Compatible();
    }
    
    @Override
    public boolean isGLES3() {
        return this.downstreamGLES1.isGLES3();
    }
    
    @Override
    public boolean isGLES31Compatible() {
        return this.downstreamGLES1.isGLES31Compatible();
    }
    
    @Override
    public boolean isGLES32Compatible() {
        return this.downstreamGLES1.isGLES32Compatible();
    }
    
    @Override
    public boolean isGLES3Compatible() {
        return this.downstreamGLES1.isGLES3Compatible();
    }
    
    @Override
    public boolean isGLcore() {
        return this.downstreamGLES1.isGLcore();
    }
    
    @Override
    public boolean isNPOTTextureAvailable() {
        return this.downstreamGLES1.isNPOTTextureAvailable();
    }
    
    @Override
    public boolean isTextureFormatBGRA8888Available() {
        return this.downstreamGLES1.isTextureFormatBGRA8888Available();
    }
    
    @Override
    public boolean isVBOArrayBound() {
        return this.downstreamGLES1.isVBOArrayBound();
    }
    
    @Override
    public boolean isVBOElementArrayBound() {
        return this.downstreamGLES1.isVBOElementArrayBound();
    }
    
    @Override
    public GLBufferStorage mapBuffer(final int n, final int n2) {
        this.checkContext();
        final GLBufferStorage mapBuffer = this.downstreamGLES1.mapBuffer(n, n2);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <int> 0x%X)", "mapBuffer", n, n2);
        }
        return mapBuffer;
    }
    
    @Override
    public GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.checkContext();
        final GLBufferStorage mapBufferRange = this.downstreamGLES1.mapBufferRange(n, n2, n3, n4);
        final int checkGLError = this.checkGLError();
        if (checkGLError != 0) {
            this.writeGLError(checkGLError, "%s(<int> 0x%X, <long> %s, <long> %s, <int> 0x%X)", "mapBufferRange", n, n2, n3, n4);
        }
        return mapBufferRange;
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        this.downstreamGLES1.setSwapInterval(swapInterval);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DebugGLES1 [this 0x" + Integer.toHexString(this.hashCode()) + " implementing com.jogamp.opengl.GLES1,\n\t");
        sb.append(" downstream: " + this.downstreamGLES1.toString() + "\n\t]");
        return sb.toString();
    }
    
    private int checkGLError() {
        return this.downstreamGLES1.glGetError();
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
        } while (--n >= 0 && (glGetError = this.downstreamGLES1.glGetError()) != 0);
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
        DEBUG = Debug.debug("DebugGLES1");
    }
}
