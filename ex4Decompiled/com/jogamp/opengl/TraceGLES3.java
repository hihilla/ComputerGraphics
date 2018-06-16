// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.lang.reflect.Array;
import java.nio.LongBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.io.PrintStream;

public class TraceGLES3 implements GLES2, GL4ES3, GLES3
{
    public static final boolean DEBUG;
    private PrintStream stream;
    private int indent;
    private GLES3 downstreamGLES3;
    
    public TraceGLES3(final GLES3 downstreamGLES3, final PrintStream stream) {
        this.indent = 0;
        if (downstreamGLES3 == null) {
            throw new IllegalArgumentException("null downstreamGLES3");
        }
        this.downstreamGLES3 = downstreamGLES3;
        this.stream = stream;
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
        this.printIndent();
        this.print("glActiveShaderProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glActiveShaderProgram(n, n2);
        this.println("");
    }
    
    @Override
    public void glActiveTexture(final int n) {
        this.printIndent();
        this.print("glActiveTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glActiveTexture(n);
        this.println("");
    }
    
    @Override
    public void glAlphaFuncQCOM(final int n, final float n2) {
        this.printIndent();
        this.print("glAlphaFuncQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES3.glAlphaFuncQCOM(n, n2);
        this.println("");
    }
    
    @Override
    public void glApplyFramebufferAttachmentCMAAINTEL() {
        this.printIndent();
        this.print("glApplyFramebufferAttachmentCMAAINTEL()");
        this.downstreamGLES3.glApplyFramebufferAttachmentCMAAINTEL();
        this.println("");
    }
    
    @Override
    public void glAttachShader(final int n, final int n2) {
        this.printIndent();
        this.print("glAttachShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glAttachShader(n, n2);
        this.println("");
    }
    
    @Override
    public void glBeginConditionalRender(final int n, final int n2) {
        this.printIndent();
        this.print("glBeginConditionalRender(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBeginConditionalRender(n, n2);
        this.println("");
    }
    
    @Override
    public void glBeginQuery(final int n, final int n2) {
        this.printIndent();
        this.print("glBeginQuery(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBeginQuery(n, n2);
        this.println("");
    }
    
    @Override
    public void glBeginTransformFeedback(final int n) {
        this.printIndent();
        this.print("glBeginTransformFeedback(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glBeginTransformFeedback(n);
        this.println("");
    }
    
    @Override
    public void glBindAttribLocation(final int n, final int n2, final String s) {
        this.printIndent();
        this.print("glBindAttribLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        this.downstreamGLES3.glBindAttribLocation(n, n2, s);
        this.println("");
    }
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindBuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindBufferBase(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBindBufferBase(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBindBufferBase(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBindBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glBindBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glBindBufferRange(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final byte[] array, final int n3) {
        this.printIndent();
        this.print("glBindFragDataLocationEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBindFragDataLocationEXT(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glBindFragDataLocationEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glBindFragDataLocationEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glBindFragDataLocationEXT(n, n2, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glBindFragDataLocationIndexedEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glBindFragDataLocationIndexedEXT(n, n2, n3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glBindFragDataLocationIndexedEXT(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.printIndent();
        this.print("glBindFragDataLocationIndexedEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glBindFragDataLocationIndexedEXT(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glBindFramebuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindFramebuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindImageTexture(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glBindImageTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glBindImageTexture(n, n2, n3, b, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glBindProgramPipeline(final int n) {
        this.printIndent();
        this.print("glBindProgramPipeline(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glBindProgramPipeline(n);
        this.println("");
    }
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindRenderbuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindSampler(final int n, final int n2) {
        this.printIndent();
        this.print("glBindSampler(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindSampler(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.printIndent();
        this.print("glBindTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindTexture(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindTransformFeedback(final int n, final int n2) {
        this.printIndent();
        this.print("glBindTransformFeedback(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBindTransformFeedback(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindVertexArray(final int n) {
        this.printIndent();
        this.print("glBindVertexArray(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glBindVertexArray(n);
        this.println("");
    }
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        this.printIndent();
        this.print("glBindVertexArrayOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glBindVertexArrayOES(n);
        this.println("");
    }
    
    @Override
    public void glBindVertexBuffer(final int n, final int n2, final long n3, final int n4) {
        this.printIndent();
        this.print("glBindVertexBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glBindVertexBuffer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glBlendBarrier() {
        this.printIndent();
        this.print("glBlendBarrier()");
        this.downstreamGLES3.glBlendBarrier();
        this.println("");
    }
    
    @Override
    public void glBlendColor(final float n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glBlendColor(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES3.glBlendColor(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glBlendEquation(final int n) {
        this.printIndent();
        this.print("glBlendEquation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquation(n);
        this.println("");
    }
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendEquationSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationSeparate(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendEquationSeparatei(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendEquationSeparatei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationSeparatei(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlendEquationSeparateiEXT(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendEquationSeparateiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationSeparateiEXT(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlendEquationSeparateiOES(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendEquationSeparateiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationSeparateiOES(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlendEquationi(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendEquationi(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationi(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendEquationiEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendEquationiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationiEXT(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendEquationiOES(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendEquationiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBlendEquationiOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFunc(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glBlendFuncSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFuncSeparate(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glBlendFuncSeparatei(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glBlendFuncSeparatei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFuncSeparatei(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glBlendFuncSeparateiEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glBlendFuncSeparateiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFuncSeparateiEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glBlendFuncSeparateiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glBlendFuncSeparateiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFuncSeparateiOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glBlendFunci(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendFunci(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFunci(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlendFunciEXT(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendFunciEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFunciEXT(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlendFunciOES(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glBlendFunciOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBlendFunciOES(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glBlitFramebuffer(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.printIndent();
        this.print("glBlitFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ")");
        this.downstreamGLES3.glBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        this.println("");
    }
    
    @Override
    public void glBlitFramebufferANGLE(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.printIndent();
        this.print("glBlitFramebufferANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ")");
        this.downstreamGLES3.glBlitFramebufferANGLE(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        this.println("");
    }
    
    @Override
    public void glBlitFramebufferNV(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        this.printIndent();
        this.print("glBlitFramebufferNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ")");
        this.downstreamGLES3.glBlitFramebufferNV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        this.println("");
    }
    
    @Override
    public void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.printIndent();
        this.print("glBufferData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBufferData(n, n2, buffer, n3);
        this.println("");
    }
    
    @Override
    public void glBufferStorageEXT(final int n, final long n2, final Buffer buffer, final int n3) {
        this.printIndent();
        this.print("glBufferStorageEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glBufferStorageEXT(n, n2, buffer, n3);
        this.println("");
    }
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        this.printIndent();
        this.print("glBufferSubData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glBufferSubData(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        this.printIndent();
        this.print("glCheckFramebufferStatus(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final int glCheckFramebufferStatus = this.downstreamGLES3.glCheckFramebufferStatus(n);
        this.println(" = " + glCheckFramebufferStatus);
        return glCheckFramebufferStatus;
    }
    
    @Override
    public void glClear(final int n) {
        this.printIndent();
        this.print("glClear(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glClear(n);
        this.println("");
    }
    
    @Override
    public void glClearBufferfi(final int n, final int n2, final float n3, final int n4) {
        this.printIndent();
        this.print("glClearBufferfi(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glClearBufferfi(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glClearBufferfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glClearBufferfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glClearBufferfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glClearBufferfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glClearBufferfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glClearBufferiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glClearBufferiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glClearBufferiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glClearBufferiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glClearBufferiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glClearBufferuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glClearBufferuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glClearBufferuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glClearBufferuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glClearBufferuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glClearColor(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES3.glClearColor(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.printIndent();
        this.print("glClearDepth(<double> " + n + ")");
        this.downstreamGLES3.glClearDepth(n);
        this.println("");
    }
    
    @Override
    public void glClearDepthf(final float n) {
        this.printIndent();
        this.print("glClearDepthf(<float> " + n + ")");
        this.downstreamGLES3.glClearDepthf(n);
        this.println("");
    }
    
    @Override
    public void glClearStencil(final int n) {
        this.printIndent();
        this.print("glClearStencil(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glClearStencil(n);
        this.println("");
    }
    
    @Override
    public int glClientWaitSync(final long n, final int n2, final long n3) {
        this.printIndent();
        this.print("glClientWaitSync(<long> " + n + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ")");
        final int glClientWaitSync = this.downstreamGLES3.glClientWaitSync(n, n2, n3);
        this.println(" = " + glClientWaitSync);
        return glClientWaitSync;
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.printIndent();
        this.print("glColorMask(<boolean> " + b + ", " + "<boolean> " + b2 + ", " + "<boolean> " + b3 + ", " + "<boolean> " + b4 + ")");
        this.downstreamGLES3.glColorMask(b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glColorMaski(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.printIndent();
        this.print("glColorMaski(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<boolean> " + b + ", " + "<boolean> " + b2 + ", " + "<boolean> " + b3 + ", " + "<boolean> " + b4 + ")");
        this.downstreamGLES3.glColorMaski(n, b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glColorMaskiEXT(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.printIndent();
        this.print("glColorMaskiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<boolean> " + b + ", " + "<boolean> " + b2 + ", " + "<boolean> " + b3 + ", " + "<boolean> " + b4 + ")");
        this.downstreamGLES3.glColorMaskiEXT(n, b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glColorMaskiOES(final int n, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.printIndent();
        this.print("glColorMaskiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<boolean> " + b + ", " + "<boolean> " + b2 + ", " + "<boolean> " + b3 + ", " + "<boolean> " + b4 + ")");
        this.downstreamGLES3.glColorMaskiOES(n, b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glCompileShader(final int n) {
        this.printIndent();
        this.print("glCompileShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glCompileShader(n);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.printIndent();
        this.print("glCompressedTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<long> " + n8 + ")");
        this.downstreamGLES3.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glCompressedTexImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES3.glCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glCompressedTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glCompressedTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES3.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexSubImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glCompressedTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.printIndent();
        this.print("glCompressedTexSubImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<long> " + n11 + ")");
        this.downstreamGLES3.glCompressedTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        this.println("");
    }
    
    @Override
    public void glCopyBufferSubData(final int n, final int n2, final long n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glCopyBufferSubData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glCopyBufferSubData(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glCopyBufferSubDataNV(final int n, final int n2, final long n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glCopyBufferSubDataNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glCopyBufferSubDataNV(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glCopyImageSubData(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final int n14, final int n15) {
        this.printIndent();
        this.print("glCopyImageSubData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n11).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n12).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n13).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n14).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n15).toUpperCase() + ")");
        this.downstreamGLES3.glCopyImageSubData(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15);
        this.println("");
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.printIndent();
        this.print("glCopyTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES3.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.printIndent();
        this.print("glCopyTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES3.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCopyTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        this.printIndent();
        this.print("glCopyTexSubImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ")");
        this.downstreamGLES3.glCopyTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glCopyTextureLevelsAPPLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glCopyTextureLevelsAPPLE(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glCoverageMaskNV(final boolean b) {
        this.printIndent();
        this.print("glCoverageMaskNV(<boolean> " + b + ")");
        this.downstreamGLES3.glCoverageMaskNV(b);
        this.println("");
    }
    
    @Override
    public void glCoverageModulationNV(final int n) {
        this.printIndent();
        this.print("glCoverageModulationNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glCoverageModulationNV(n);
        this.println("");
    }
    
    @Override
    public void glCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glCoverageModulationTableNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glCoverageModulationTableNV(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glCoverageModulationTableNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glCoverageModulationTableNV(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glCoverageOperationNV(final int n) {
        this.printIndent();
        this.print("glCoverageOperationNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glCoverageOperationNV(n);
        this.println("");
    }
    
    @Override
    public int glCreateProgram() {
        this.printIndent();
        this.print("glCreateProgram()");
        final int glCreateProgram = this.downstreamGLES3.glCreateProgram();
        this.println(" = " + glCreateProgram);
        return glCreateProgram;
    }
    
    @Override
    public int glCreateShader(final int n) {
        this.printIndent();
        this.print("glCreateShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final int glCreateShader = this.downstreamGLES3.glCreateShader(n);
        this.println(" = " + glCreateShader);
        return glCreateShader;
    }
    
    @Override
    public int glCreateShaderProgramv(final int n, final int n2, final String[] array) {
        this.printIndent();
        this.print("glCreateShaderProgramv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ")");
        final int glCreateShaderProgramv = this.downstreamGLES3.glCreateShaderProgramv(n, n2, array);
        this.println(" = " + glCreateShaderProgramv);
        return glCreateShaderProgramv;
    }
    
    @Override
    public void glCullFace(final int n) {
        this.printIndent();
        this.print("glCullFace(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glCullFace(n);
        this.println("");
    }
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final boolean b) {
        this.printIndent();
        this.print("glDebugMessageControl(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glDebugMessageControl(n, n2, n3, n4, array, n5, b);
        this.println("");
    }
    
    @Override
    public void glDebugMessageControl(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final boolean b) {
        this.printIndent();
        this.print("glDebugMessageControl(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glDebugMessageControl(n, n2, n3, n4, intBuffer, b);
        this.println("");
    }
    
    @Override
    public void glDebugMessageInsert(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        this.printIndent();
        this.print("glDebugMessageInsert(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        this.downstreamGLES3.glDebugMessageInsert(n, n2, n3, n4, n5, s);
        this.println("");
    }
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteBuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteBuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteFramebuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteFramebuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteProgram(final int n) {
        this.printIndent();
        this.print("glDeleteProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteProgram(n);
        this.println("");
    }
    
    @Override
    public void glDeleteProgramPipelines(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteProgramPipelines(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteProgramPipelines(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteProgramPipelines(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteProgramPipelines(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteProgramPipelines(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteQueries(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteQueries(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteQueries(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteQueries(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteQueries(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteQueries(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteRenderbuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteRenderbuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteSamplers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteSamplers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteSamplers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteSamplers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteSamplers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteSamplers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteShader(final int n) {
        this.printIndent();
        this.print("glDeleteShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteShader(n);
        this.println("");
    }
    
    @Override
    public void glDeleteSync(final long n) {
        this.printIndent();
        this.print("glDeleteSync(<long> " + n + ")");
        this.downstreamGLES3.glDeleteSync(n);
        this.println("");
    }
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteTextures(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteTextures(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteTransformFeedbacks(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteTransformFeedbacks(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteTransformFeedbacks(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteTransformFeedbacks(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArrays(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteVertexArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteVertexArrays(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArrays(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteVertexArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteVertexArrays(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDeleteVertexArraysOES(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDeleteVertexArraysOES(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDepthFunc(final int n) {
        this.printIndent();
        this.print("glDepthFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDepthFunc(n);
        this.println("");
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        this.printIndent();
        this.print("glDepthMask(<boolean> " + b + ")");
        this.downstreamGLES3.glDepthMask(b);
        this.println("");
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.printIndent();
        this.print("glDepthRange(<double> " + n + ", " + "<double> " + n2 + ")");
        this.downstreamGLES3.glDepthRange(n, n2);
        this.println("");
    }
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glDepthRangeArrayfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glDepthRangeArrayfvNV(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glDepthRangeArrayfvNV(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glDepthRangeArrayfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glDepthRangeArrayfvNV(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glDepthRangeIndexedfNV(final int n, final float n2, final float n3) {
        this.printIndent();
        this.print("glDepthRangeIndexedfNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glDepthRangeIndexedfNV(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        this.printIndent();
        this.print("glDepthRangef(<float> " + n + ", " + "<float> " + n2 + ")");
        this.downstreamGLES3.glDepthRangef(n, n2);
        this.println("");
    }
    
    @Override
    public void glDetachShader(final int n, final int n2) {
        this.printIndent();
        this.print("glDetachShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDetachShader(n, n2);
        this.println("");
    }
    
    @Override
    public void glDisable(final int n) {
        this.printIndent();
        this.print("glDisable(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDisable(n);
        this.println("");
    }
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        this.printIndent();
        this.print("glDisableDriverControlQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDisableDriverControlQCOM(n);
        this.println("");
    }
    
    @Override
    public void glDisableVertexAttribArray(final int n) {
        this.printIndent();
        this.print("glDisableVertexAttribArray(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glDisableVertexAttribArray(n);
        this.println("");
    }
    
    @Override
    public void glDisablei(final int n, final int n2) {
        this.printIndent();
        this.print("glDisablei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDisablei(n, n2);
        this.println("");
    }
    
    @Override
    public void glDisableiEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glDisableiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDisableiEXT(n, n2);
        this.println("");
    }
    
    @Override
    public void glDisableiNV(final int n, final int n2) {
        this.printIndent();
        this.print("glDisableiNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDisableiNV(n, n2);
        this.println("");
    }
    
    @Override
    public void glDisableiOES(final int n, final int n2) {
        this.printIndent();
        this.print("glDisableiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDisableiOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDiscardFramebufferEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDiscardFramebufferEXT(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glDiscardFramebufferEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glDiscardFramebufferEXT(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glDispatchCompute(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glDispatchCompute(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glDispatchCompute(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glDispatchComputeIndirect(final long n) {
        this.printIndent();
        this.print("glDispatchComputeIndirect(<long> " + n + ")");
        this.downstreamGLES3.glDispatchComputeIndirect(n);
        this.println("");
    }
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glDrawArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glDrawArrays(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glDrawArraysIndirect(final int n, final long n2) {
        this.printIndent();
        this.print("glDrawArraysIndirect(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ")");
        this.downstreamGLES3.glDrawArraysIndirect(n, n2);
        this.println("");
    }
    
    @Override
    public void glDrawArraysIndirect(final int n, final Buffer buffer) {
        this.printIndent();
        this.print("glDrawArraysIndirect(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glDrawArraysIndirect(n, buffer);
        this.println("");
    }
    
    @Override
    public void glDrawArraysInstanced(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glDrawArraysInstanced(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawArraysInstanced(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glDrawArraysInstancedANGLE(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glDrawArraysInstancedANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawArraysInstancedANGLE(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glDrawArraysInstancedBaseInstance(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawArraysInstancedBaseInstance(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawArraysInstancedBaseInstance(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawArraysInstancedNV(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glDrawArraysInstancedNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawArraysInstancedNV(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glDrawBuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDrawBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glDrawBuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDrawBuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDrawBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glDrawBuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final int[] array, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glDrawBuffersIndexedEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glDrawBuffersIndexedEXT(n, array, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glDrawBuffersIndexedEXT(final int n, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glDrawBuffersIndexedEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glDrawBuffersIndexedEXT(n, intBuffer, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.printIndent();
        this.print("glDrawElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ")");
        this.downstreamGLES3.glDrawElements(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glDrawElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glDrawElements(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsBaseVertex(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElementsBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.printIndent();
        this.print("glDrawElementsBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsBaseVertex(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawElementsBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsBaseVertexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsBaseVertexEXT(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElementsBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsBaseVertexOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsBaseVertexOES(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final Buffer buffer) {
        this.printIndent();
        this.print("glDrawElementsIndirect(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glDrawElementsIndirect(n, n2, buffer);
        this.println("");
    }
    
    @Override
    public void glDrawElementsIndirect(final int n, final int n2, final long n3) {
        this.printIndent();
        this.print("glDrawElementsIndirect(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ")");
        this.downstreamGLES3.glDrawElementsIndirect(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsInstanced(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstanced(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstanced(final int n, final int n2, final int n3, final long n4, final int n5) {
        this.printIndent();
        this.print("glDrawElementsInstanced(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstanced(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedANGLE(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsInstancedANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedANGLE(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseInstance(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseInstance(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final long n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseVertex(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertex(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseVertex(n, n2, n3, buffer, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexBaseInstance(final int n, final int n2, final int n3, final long n4, final int n5, final int n6, final int n7) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseVertexBaseInstance(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexBaseInstance(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexEXT(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseVertexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexEXT(n, n2, n3, buffer, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedBaseVertexOES(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawElementsInstancedBaseVertexOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedBaseVertexOES(n, n2, n3, buffer, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawElementsInstancedNV(final int n, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glDrawElementsInstancedNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glDrawElementsInstancedNV(n, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final long n6) {
        this.printIndent();
        this.print("glDrawRangeElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<long> " + n6 + ")");
        this.downstreamGLES3.glDrawRangeElements(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElements(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer) {
        this.printIndent();
        this.print("glDrawRangeElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glDrawRangeElements(n, n2, n3, n4, n5, buffer);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final long n6, final int n7) {
        this.printIndent();
        this.print("glDrawRangeElementsBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<long> " + n6 + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElementsBaseVertex(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.printIndent();
        this.print("glDrawRangeElementsBaseVertex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, buffer, n6);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElementsBaseVertexEXT(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.printIndent();
        this.print("glDrawRangeElementsBaseVertexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glDrawRangeElementsBaseVertexEXT(n, n2, n3, n4, n5, buffer, n6);
        this.println("");
    }
    
    @Override
    public void glDrawRangeElementsBaseVertexOES(final int n, final int n2, final int n3, final int n4, final int n5, final Buffer buffer, final int n6) {
        this.printIndent();
        this.print("glDrawRangeElementsBaseVertexOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glDrawRangeElementsBaseVertexOES(n, n2, n3, n4, n5, buffer, n6);
        this.println("");
    }
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        this.printIndent();
        this.print("glEGLImageTargetRenderbufferStorageOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ")");
        this.downstreamGLES3.glEGLImageTargetRenderbufferStorageOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        this.printIndent();
        this.print("glEGLImageTargetTexture2DOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ")");
        this.downstreamGLES3.glEGLImageTargetTexture2DOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glEnable(final int n) {
        this.printIndent();
        this.print("glEnable(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glEnable(n);
        this.println("");
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        this.printIndent();
        this.print("glEnableDriverControlQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glEnableDriverControlQCOM(n);
        this.println("");
    }
    
    @Override
    public void glEnableVertexAttribArray(final int n) {
        this.printIndent();
        this.print("glEnableVertexAttribArray(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glEnableVertexAttribArray(n);
        this.println("");
    }
    
    @Override
    public void glEnablei(final int n, final int n2) {
        this.printIndent();
        this.print("glEnablei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glEnablei(n, n2);
        this.println("");
    }
    
    @Override
    public void glEnableiEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glEnableiEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glEnableiEXT(n, n2);
        this.println("");
    }
    
    @Override
    public void glEnableiNV(final int n, final int n2) {
        this.printIndent();
        this.print("glEnableiNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glEnableiNV(n, n2);
        this.println("");
    }
    
    @Override
    public void glEnableiOES(final int n, final int n2) {
        this.printIndent();
        this.print("glEnableiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glEnableiOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glEndConditionalRender() {
        this.printIndent();
        this.print("glEndConditionalRender()");
        this.downstreamGLES3.glEndConditionalRender();
        this.println("");
    }
    
    @Override
    public void glEndQuery(final int n) {
        this.printIndent();
        this.print("glEndQuery(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glEndQuery(n);
        this.println("");
    }
    
    @Override
    public void glEndTilingQCOM(final int n) {
        this.printIndent();
        this.print("glEndTilingQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glEndTilingQCOM(n);
        this.println("");
    }
    
    @Override
    public void glEndTransformFeedback() {
        this.printIndent();
        this.print("glEndTransformFeedback()");
        this.downstreamGLES3.glEndTransformFeedback();
        this.println("");
    }
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        this.printIndent();
        this.print("glExtGetBufferPointervQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ")");
        this.downstreamGLES3.glExtGetBufferPointervQCOM(n, pointerBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetBuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetBuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetBuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetBuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetFramebuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetFramebuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetFramebuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetFramebuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glExtGetProgramBinarySourceQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glExtGetProgramBinarySourceQCOM(n, n2, byteBuffer, intBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        this.printIndent();
        this.print("glExtGetProgramBinarySourceQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetProgramBinarySourceQCOM(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetProgramsQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetProgramsQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetProgramsQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetProgramsQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetRenderbuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetRenderbuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetRenderbuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetRenderbuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetShadersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetShadersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetShadersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetShadersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glExtGetTexLevelParameterivQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, intBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.printIndent();
        this.print("glExtGetTexLevelParameterivQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, array, n5);
        this.println("");
    }
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.printIndent();
        this.print("glExtGetTexSubImageQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glExtGetTexSubImageQCOM(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        this.println("");
    }
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetTexturesQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtGetTexturesQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetTexturesQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glExtGetTexturesQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        this.printIndent();
        this.print("glExtIsProgramBinaryQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glExtIsProgramBinaryQCOM = this.downstreamGLES3.glExtIsProgramBinaryQCOM(n);
        this.println(" = " + glExtIsProgramBinaryQCOM);
        return glExtIsProgramBinaryQCOM;
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glExtTexObjectStateOverrideiQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glExtTexObjectStateOverrideiQCOM(n, n2, n3);
        this.println("");
    }
    
    @Override
    public long glFenceSync(final int n, final int n2) {
        this.printIndent();
        this.print("glFenceSync(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final long glFenceSync = this.downstreamGLES3.glFenceSync(n, n2);
        this.println(" = " + glFenceSync);
        return glFenceSync;
    }
    
    @Override
    public void glFinish() {
        this.printIndent();
        this.print("glFinish()");
        this.downstreamGLES3.glFinish();
        this.println("");
    }
    
    @Override
    public void glFlush() {
        this.printIndent();
        this.print("glFlush()");
        this.downstreamGLES3.glFlush();
        this.println("");
    }
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        this.printIndent();
        this.print("glFlushMappedBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ")");
        this.downstreamGLES3.glFlushMappedBufferRange(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glFragmentCoverageColorNV(final int n) {
        this.printIndent();
        this.print("glFragmentCoverageColorNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glFragmentCoverageColorNV(n);
        this.println("");
    }
    
    @Override
    public void glFramebufferParameteri(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glFramebufferParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferParameteri(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glFramebufferRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferRenderbuffer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glFramebufferSampleLocationsfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glFramebufferSampleLocationsfvNV(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glFramebufferSampleLocationsfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferSampleLocationsfvNV(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glFramebufferTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTexture(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glFramebufferTexture2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTexture2D(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTexture2DMultisampleEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTexture2DMultisampleEXT(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTexture2DMultisampleIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTexture2DMultisampleIMG(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTexture3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTexture3D(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFramebufferTextureEXT(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glFramebufferTextureEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTextureEXT(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glFramebufferTextureLayer(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glFramebufferTextureLayer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTextureLayer(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glFramebufferTextureMultiviewOVR(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTextureMultiviewOVR(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTextureMultiviewOVR(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFramebufferTextureOES(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glFramebufferTextureOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glFramebufferTextureOES(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glFrontFace(final int n) {
        this.printIndent();
        this.print("glFrontFace(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glFrontFace(n);
        this.println("");
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenBuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenBuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenFramebuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenFramebuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenProgramPipelines(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenProgramPipelines(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenProgramPipelines(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenProgramPipelines(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenProgramPipelines(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenProgramPipelines(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenQueries(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenQueries(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenQueries(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenQueries(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenQueries(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenQueries(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenRenderbuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenRenderbuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenSamplers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenSamplers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenSamplers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenSamplers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenSamplers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenSamplers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenTextures(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenTextures(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenTransformFeedbacks(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenTransformFeedbacks(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenTransformFeedbacks(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenTransformFeedbacks(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenTransformFeedbacks(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenTransformFeedbacks(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenVertexArrays(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenVertexArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenVertexArrays(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenVertexArrays(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenVertexArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenVertexArrays(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGenVertexArraysOES(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGenVertexArraysOES(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        this.printIndent();
        this.print("glGenerateMipmap(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glGenerateMipmap(n);
        this.println("");
    }
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetActiveAttrib(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.IntBuffer> " + intBuffer3 + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetActiveAttrib(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetActiveAttrib(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.printIndent();
        this.print("glGetActiveAttrib(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glGetActiveAttrib(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetActiveUniform(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.IntBuffer> " + intBuffer3 + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetActiveUniform(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniform(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.printIndent();
        this.print("glGetActiveUniform(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glGetActiveUniform(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        this.printIndent();
        this.print("glGetActiveUniformBlockName(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glGetActiveUniformBlockName(n, n2, n3, array, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformBlockName(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetActiveUniformBlockName(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetActiveUniformBlockName(n, n2, n3, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetActiveUniformBlockiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetActiveUniformBlockiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformBlockiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetActiveUniformBlockiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetActiveUniformBlockiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final int[] array, final int n3, final int n4, final int[] array2, final int n5) {
        this.printIndent();
        this.print("glGetActiveUniformsiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glGetActiveUniformsiv(n, n2, array, n3, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glGetActiveUniformsiv(final int n, final int n2, final IntBuffer intBuffer, final int n3, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetActiveUniformsiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glGetActiveUniformsiv(n, n2, intBuffer, n3, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        this.printIndent();
        this.print("glGetAttachedShaders(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetAttachedShaders(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetAttachedShaders(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetAttachedShaders(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glGetAttachedShaders(n, n2, intBuffer, intBuffer2);
        this.println("");
    }
    
    @Override
    public int glGetAttribLocation(final int n, final String s) {
        this.printIndent();
        this.print("glGetAttribLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        final int glGetAttribLocation = this.downstreamGLES3.glGetAttribLocation(n, s);
        this.println(" = " + glGetAttribLocation);
        return glGetAttribLocation;
    }
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final byte[] array, final int n3) {
        this.printIndent();
        this.print("glGetBooleani_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetBooleani_v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetBooleani_v(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetBooleani_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetBooleani_v(n, n2, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetBooleanv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetBooleanv(n, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        this.printIndent();
        this.print("glGetBooleanv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGetBooleanv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.printIndent();
        this.print("glGetBufferParameteri64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.LongBuffer> " + longBuffer + ")");
        this.downstreamGLES3.glGetBufferParameteri64v(n, n2, longBuffer);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteri64v(final int n, final int n2, final long[] array, final int n3) {
        this.printIndent();
        this.print("glGetBufferParameteri64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[J>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetBufferParameteri64v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetBufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetBufferParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetBufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetBufferParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glGetCoverageModulationTableNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGetCoverageModulationTableNV(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetCoverageModulationTableNV(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetCoverageModulationTableNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetCoverageModulationTableNV(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final int[] array3, final int n5, final int[] array4, final int n6, final int[] array5, final int n7, final byte[] array6, final int n8) {
        this.printIndent();
        this.print("glGetDebugMessageLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        final int glGetDebugMessageLog = this.downstreamGLES3.glGetDebugMessageLog(n, n2, array, n3, array2, n4, array3, n5, array4, n6, array5, n7, array6, n8);
        this.println(" = " + glGetDebugMessageLog);
        return glGetDebugMessageLog;
    }
    
    @Override
    public int glGetDebugMessageLog(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final IntBuffer intBuffer4, final IntBuffer intBuffer5, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetDebugMessageLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.IntBuffer> " + intBuffer3 + ", " + "<java.nio.IntBuffer> " + intBuffer4 + ", " + "<java.nio.IntBuffer> " + intBuffer5 + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        final int glGetDebugMessageLog = this.downstreamGLES3.glGetDebugMessageLog(n, n2, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
        this.println(" = " + glGetDebugMessageLog);
        return glGetDebugMessageLog;
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetDriverControlStringQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetDriverControlStringQCOM(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetDriverControlStringQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetDriverControlStringQCOM(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glGetDriverControlsQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetDriverControlsQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetDriverControlsQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glGetDriverControlsQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public int glGetError() {
        this.printIndent();
        this.print("glGetError()");
        final int glGetError = this.downstreamGLES3.glGetError();
        this.println(" = " + glGetError);
        return glGetError;
    }
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetFloati_vNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetFloati_vNV(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFloati_vNV(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetFloati_vNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetFloati_vNV(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetFloatv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetFloatv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glGetFloatv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGetFloatv(n, array, n2);
        this.println("");
    }
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetFragDataIndexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        final int glGetFragDataIndexEXT = this.downstreamGLES3.glGetFragDataIndexEXT(n, byteBuffer);
        this.println(" = " + glGetFragDataIndexEXT);
        return glGetFragDataIndexEXT;
    }
    
    @Override
    public int glGetFragDataIndexEXT(final int n, final byte[] array, final int n2) {
        this.printIndent();
        this.print("glGetFragDataIndexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final int glGetFragDataIndexEXT = this.downstreamGLES3.glGetFragDataIndexEXT(n, array, n2);
        this.println(" = " + glGetFragDataIndexEXT);
        return glGetFragDataIndexEXT;
    }
    
    @Override
    public int glGetFragDataLocation(final int n, final String s) {
        this.printIndent();
        this.print("glGetFragDataLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        final int glGetFragDataLocation = this.downstreamGLES3.glGetFragDataLocation(n, s);
        this.println(" = " + glGetFragDataLocation);
        return glGetFragDataLocation;
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetFramebufferAttachmentParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetFramebufferAttachmentParameteriv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetFramebufferAttachmentParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetFramebufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetFramebufferParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetFramebufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetFramebufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetFramebufferParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public int glGetGraphicsResetStatus() {
        this.printIndent();
        this.print("glGetGraphicsResetStatus()");
        final int glGetGraphicsResetStatus = this.downstreamGLES3.glGetGraphicsResetStatus();
        this.println(" = " + glGetGraphicsResetStatus);
        return glGetGraphicsResetStatus;
    }
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final long[] array, final int n3) {
        this.printIndent();
        this.print("glGetInteger64i_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[J>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetInteger64i_v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetInteger64i_v(final int n, final int n2, final LongBuffer longBuffer) {
        this.printIndent();
        this.print("glGetInteger64i_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.LongBuffer> " + longBuffer + ")");
        this.downstreamGLES3.glGetInteger64i_v(n, n2, longBuffer);
        this.println("");
    }
    
    @Override
    public void glGetInteger64v(final int n, final LongBuffer longBuffer) {
        this.printIndent();
        this.print("glGetInteger64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.LongBuffer> " + longBuffer + ")");
        this.downstreamGLES3.glGetInteger64v(n, longBuffer);
        this.println("");
    }
    
    @Override
    public void glGetInteger64v(final int n, final long[] array, final int n2) {
        this.printIndent();
        this.print("glGetInteger64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[J>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGetInteger64v(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetIntegeri_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetIntegeri_v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetIntegeri_v(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetIntegeri_v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetIntegeri_v(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetIntegeri_vEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetIntegeri_vEXT(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetIntegeri_vEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetIntegeri_vEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetIntegeri_vEXT(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGetIntegerv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glGetIntegerv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetIntegerv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetIntegerv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetInternalformativ(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetInternalformativ(n, n2, n3, n4, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetInternalformativ(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.printIndent();
        this.print("glGetInternalformativ(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glGetInternalformativ(n, n2, n3, n4, array, n5);
        this.println("");
    }
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetMultisamplefv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetMultisamplefv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetMultisamplefv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetMultisamplefv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetMultisamplefv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetObjectLabel(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetObjectLabel(n, n2, n3, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetObjectLabel(final int n, final int n2, final int n3, final int[] array, final int n4, final byte[] array2, final int n5) {
        this.printIndent();
        this.print("glGetObjectLabel(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glGetObjectLabel(n, n2, n3, array, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final int[] array, final int n2, final byte[] array2, final int n3) {
        this.printIndent();
        this.print("glGetObjectPtrLabel(<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetObjectPtrLabel(buffer, n, array, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glGetObjectPtrLabel(final Buffer buffer, final int n, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetObjectPtrLabel(<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetObjectPtrLabel(buffer, n, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2, final Buffer buffer) {
        this.printIndent();
        this.print("glGetProgramBinary(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glGetProgramBinary(n, n2, intBuffer, intBuffer2, buffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramBinary(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4, final Buffer buffer) {
        this.printIndent();
        this.print("glGetProgramBinary(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glGetProgramBinary(n, n2, array, n3, array2, n4, buffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetProgramInfoLog(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetProgramInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramInfoLog(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetProgramInterfaceiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramInterfaceiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetProgramInterfaceiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetProgramInterfaceiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetProgramInterfaceiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramPipelineInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetProgramPipelineInfoLog(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramPipelineInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetProgramPipelineInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramPipelineInfoLog(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetProgramPipelineiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramPipelineiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetProgramPipelineiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetProgramPipelineiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetProgramPipelineiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final byte[] array, final int n3) {
        this.printIndent();
        this.print("glGetProgramResourceIndex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        final int glGetProgramResourceIndex = this.downstreamGLES3.glGetProgramResourceIndex(n, n2, array, n3);
        this.println(" = " + glGetProgramResourceIndex);
        return glGetProgramResourceIndex;
    }
    
    @Override
    public int glGetProgramResourceIndex(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramResourceIndex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        final int glGetProgramResourceIndex = this.downstreamGLES3.glGetProgramResourceIndex(n, n2, byteBuffer);
        this.println(" = " + glGetProgramResourceIndex);
        return glGetProgramResourceIndex;
    }
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramResourceLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        final int glGetProgramResourceLocation = this.downstreamGLES3.glGetProgramResourceLocation(n, n2, byteBuffer);
        this.println(" = " + glGetProgramResourceLocation);
        return glGetProgramResourceLocation;
    }
    
    @Override
    public int glGetProgramResourceLocation(final int n, final int n2, final byte[] array, final int n3) {
        this.printIndent();
        this.print("glGetProgramResourceLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        final int glGetProgramResourceLocation = this.downstreamGLES3.glGetProgramResourceLocation(n, n2, array, n3);
        this.println(" = " + glGetProgramResourceLocation);
        return glGetProgramResourceLocation;
    }
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramResourceLocationIndexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        final int glGetProgramResourceLocationIndexEXT = this.downstreamGLES3.glGetProgramResourceLocationIndexEXT(n, n2, byteBuffer);
        this.println(" = " + glGetProgramResourceLocationIndexEXT);
        return glGetProgramResourceLocationIndexEXT;
    }
    
    @Override
    public int glGetProgramResourceLocationIndexEXT(final int n, final int n2, final byte[] array, final int n3) {
        this.printIndent();
        this.print("glGetProgramResourceLocationIndexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        final int glGetProgramResourceLocationIndexEXT = this.downstreamGLES3.glGetProgramResourceLocationIndexEXT(n, n2, array, n3);
        this.println(" = " + glGetProgramResourceLocationIndexEXT);
        return glGetProgramResourceLocationIndexEXT;
    }
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final byte[] array2, final int n6) {
        this.printIndent();
        this.print("glGetProgramResourceName(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramResourceName(n, n2, n3, n4, array, n5, array2, n6);
        this.println("");
    }
    
    @Override
    public void glGetProgramResourceName(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetProgramResourceName(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetProgramResourceName(n, n2, n3, n4, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5, final int n6, final int[] array2, final int n7, final int[] array3, final int n8) {
        this.printIndent();
        this.print("glGetProgramResourceiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramResourceiv(n, n2, n3, n4, array, n5, n6, array2, n7, array3, n8);
        this.println("");
    }
    
    @Override
    public void glGetProgramResourceiv(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer, final int n5, final IntBuffer intBuffer2, final IntBuffer intBuffer3) {
        this.printIndent();
        this.print("glGetProgramResourceiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.IntBuffer> " + intBuffer3 + ")");
        this.downstreamGLES3.glGetProgramResourceiv(n, n2, n3, n4, intBuffer, n5, intBuffer2, intBuffer3);
        this.println("");
    }
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetProgramiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetProgramiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetProgramiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetProgramiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetProgramiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final long[] array, final int n3) {
        this.printIndent();
        this.print("glGetQueryObjecti64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[J>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetQueryObjecti64v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjecti64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.printIndent();
        this.print("glGetQueryObjecti64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.LongBuffer> " + longBuffer + ")");
        this.downstreamGLES3.glGetQueryObjecti64v(n, n2, longBuffer);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetQueryObjectiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetQueryObjectiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetQueryObjectiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetQueryObjectiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final LongBuffer longBuffer) {
        this.printIndent();
        this.print("glGetQueryObjectui64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.LongBuffer> " + longBuffer + ")");
        this.downstreamGLES3.glGetQueryObjectui64v(n, n2, longBuffer);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectui64v(final int n, final int n2, final long[] array, final int n3) {
        this.printIndent();
        this.print("glGetQueryObjectui64v(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[J>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetQueryObjectui64v(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetQueryObjectuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetQueryObjectuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetQueryObjectuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetQueryObjectuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetQueryObjectuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetQueryiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetQueryiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetQueryiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetQueryiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetQueryiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetRenderbufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetRenderbufferParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetRenderbufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetRenderbufferParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetSamplerParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetSamplerParameterIiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetSamplerParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetSamplerParameterIiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetSamplerParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetSamplerParameterIuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetSamplerParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetSamplerParameterIuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetSamplerParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetSamplerParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetSamplerParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetSamplerParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetSamplerParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetSamplerParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetSamplerParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetSamplerParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetShaderInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetShaderInfoLog(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetShaderInfoLog(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetShaderInfoLog(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetShaderInfoLog(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final int[] array, final int n3, final int[] array2, final int n4) {
        this.printIndent();
        this.print("glGetShaderPrecisionFormat(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetShaderPrecisionFormat(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetShaderPrecisionFormat(final int n, final int n2, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetShaderPrecisionFormat(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glGetShaderPrecisionFormat(n, n2, intBuffer, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetShaderSource(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetShaderSource(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetShaderSource(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetShaderSource(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetShaderSource(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetShaderiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetShaderiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetShaderiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetShaderiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetShaderiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public String glGetString(final int n) {
        this.printIndent();
        this.print("glGetString(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final String glGetString = this.downstreamGLES3.glGetString(n);
        this.println(" = " + glGetString);
        return glGetString;
    }
    
    @Override
    public String glGetStringi(final int n, final int n2) {
        this.printIndent();
        this.print("glGetStringi(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final String glGetStringi = this.downstreamGLES3.glGetStringi(n, n2);
        this.println(" = " + glGetStringi);
        return glGetStringi;
    }
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetSynciv(<long> " + n + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glGetSynciv(n, n2, n3, intBuffer, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glGetSynciv(final long n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5) {
        this.printIndent();
        this.print("glGetSynciv(<long> " + n + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glGetSynciv(n, n2, n3, array, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetTexLevelParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetTexLevelParameterfv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexLevelParameterfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glGetTexLevelParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexLevelParameterfv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetTexLevelParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexLevelParameteriv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetTexLevelParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexLevelParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetTexLevelParameteriv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexParameterIiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetTexParameterIiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexParameterIuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetTexParameterIuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetTexParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetTexParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetTexParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int n5, final int[] array3, final int n6, final byte[] array4, final int n7) {
        this.printIndent();
        this.print("glGetTransformFeedbackVarying(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glGetTransformFeedbackVarying(n, n2, n3, array, n4, array2, n5, array3, n6, array4, n7);
        this.println("");
    }
    
    @Override
    public void glGetTransformFeedbackVarying(final int n, final int n2, final int n3, final IntBuffer intBuffer, final IntBuffer intBuffer2, final IntBuffer intBuffer3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetTransformFeedbackVarying(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ", " + "<java.nio.IntBuffer> " + intBuffer3 + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetTransformFeedbackVarying(n, n2, n3, intBuffer, intBuffer2, intBuffer3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetTranslatedShaderSourceANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetTranslatedShaderSourceANGLE(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetTranslatedShaderSourceANGLE(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetTranslatedShaderSourceANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glGetTranslatedShaderSourceANGLE(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public int glGetUniformBlockIndex(final int n, final String s) {
        this.printIndent();
        this.print("glGetUniformBlockIndex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        final int glGetUniformBlockIndex = this.downstreamGLES3.glGetUniformBlockIndex(n, s);
        this.println(" = " + glGetUniformBlockIndex);
        return glGetUniformBlockIndex;
    }
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glGetUniformIndices(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetUniformIndices(n, n2, array, array2, n3);
        this.println("");
    }
    
    @Override
    public void glGetUniformIndices(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetUniformIndices(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetUniformIndices(n, n2, array, intBuffer);
        this.println("");
    }
    
    @Override
    public int glGetUniformLocation(final int n, final String s) {
        this.printIndent();
        this.print("glGetUniformLocation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.lang.String> " + s + ")");
        final int glGetUniformLocation = this.downstreamGLES3.glGetUniformLocation(n, s);
        this.println(" = " + glGetUniformLocation);
        return glGetUniformLocation;
    }
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetUniformfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetUniformfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetUniformfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetUniformiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetUniformiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetUniformiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetUniformuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetUniformuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetUniformuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetUniformuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetUniformuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetVertexAttribIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetVertexAttribIiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribIiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetVertexAttribIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetVertexAttribIiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetVertexAttribIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetVertexAttribIuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetVertexAttribIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetVertexAttribIuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetVertexAttribfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetVertexAttribfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetVertexAttribfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetVertexAttribfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetVertexAttribiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glGetVertexAttribiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetVertexAttribiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetVertexAttribiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetVertexAttribiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetnUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glGetnUniformfv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glGetnUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetnUniformfv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetnUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetnUniformiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetnUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetnUniformiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetnUniformuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glGetnUniformuiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetnUniformuiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetnUniformuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glGetnUniformuiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        this.printIndent();
        this.print("glHint(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glHint(n, n2);
        this.println("");
    }
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glInvalidateFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glInvalidateFramebuffer(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glInvalidateFramebuffer(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glInvalidateFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glInvalidateFramebuffer(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final IntBuffer intBuffer, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glInvalidateSubFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glInvalidateSubFramebuffer(n, n2, intBuffer, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glInvalidateSubFramebuffer(final int n, final int n2, final int[] array, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.printIndent();
        this.print("glInvalidateSubFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glInvalidateSubFramebuffer(n, n2, array, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public boolean glIsBuffer(final int n) {
        this.printIndent();
        this.print("glIsBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsBuffer = this.downstreamGLES3.glIsBuffer(n);
        this.println(" = " + glIsBuffer);
        return glIsBuffer;
    }
    
    @Override
    public boolean glIsEnabled(final int n) {
        this.printIndent();
        this.print("glIsEnabled(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsEnabled = this.downstreamGLES3.glIsEnabled(n);
        this.println(" = " + glIsEnabled);
        return glIsEnabled;
    }
    
    @Override
    public boolean glIsEnabledi(final int n, final int n2) {
        this.printIndent();
        this.print("glIsEnabledi(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final boolean glIsEnabledi = this.downstreamGLES3.glIsEnabledi(n, n2);
        this.println(" = " + glIsEnabledi);
        return glIsEnabledi;
    }
    
    @Override
    public boolean glIsEnablediEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glIsEnablediEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final boolean glIsEnablediEXT = this.downstreamGLES3.glIsEnablediEXT(n, n2);
        this.println(" = " + glIsEnablediEXT);
        return glIsEnablediEXT;
    }
    
    @Override
    public boolean glIsEnablediNV(final int n, final int n2) {
        this.printIndent();
        this.print("glIsEnablediNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final boolean glIsEnablediNV = this.downstreamGLES3.glIsEnablediNV(n, n2);
        this.println(" = " + glIsEnablediNV);
        return glIsEnablediNV;
    }
    
    @Override
    public boolean glIsEnablediOES(final int n, final int n2) {
        this.printIndent();
        this.print("glIsEnablediOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final boolean glIsEnablediOES = this.downstreamGLES3.glIsEnablediOES(n, n2);
        this.println(" = " + glIsEnablediOES);
        return glIsEnablediOES;
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        this.printIndent();
        this.print("glIsFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsFramebuffer = this.downstreamGLES3.glIsFramebuffer(n);
        this.println(" = " + glIsFramebuffer);
        return glIsFramebuffer;
    }
    
    @Override
    public boolean glIsProgram(final int n) {
        this.printIndent();
        this.print("glIsProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsProgram = this.downstreamGLES3.glIsProgram(n);
        this.println(" = " + glIsProgram);
        return glIsProgram;
    }
    
    @Override
    public boolean glIsProgramPipeline(final int n) {
        this.printIndent();
        this.print("glIsProgramPipeline(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsProgramPipeline = this.downstreamGLES3.glIsProgramPipeline(n);
        this.println(" = " + glIsProgramPipeline);
        return glIsProgramPipeline;
    }
    
    @Override
    public boolean glIsQuery(final int n) {
        this.printIndent();
        this.print("glIsQuery(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsQuery = this.downstreamGLES3.glIsQuery(n);
        this.println(" = " + glIsQuery);
        return glIsQuery;
    }
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        this.printIndent();
        this.print("glIsRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsRenderbuffer = this.downstreamGLES3.glIsRenderbuffer(n);
        this.println(" = " + glIsRenderbuffer);
        return glIsRenderbuffer;
    }
    
    @Override
    public boolean glIsSampler(final int n) {
        this.printIndent();
        this.print("glIsSampler(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsSampler = this.downstreamGLES3.glIsSampler(n);
        this.println(" = " + glIsSampler);
        return glIsSampler;
    }
    
    @Override
    public boolean glIsShader(final int n) {
        this.printIndent();
        this.print("glIsShader(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsShader = this.downstreamGLES3.glIsShader(n);
        this.println(" = " + glIsShader);
        return glIsShader;
    }
    
    @Override
    public boolean glIsSync(final long n) {
        this.printIndent();
        this.print("glIsSync(<long> " + n + ")");
        final boolean glIsSync = this.downstreamGLES3.glIsSync(n);
        this.println(" = " + glIsSync);
        return glIsSync;
    }
    
    @Override
    public boolean glIsTexture(final int n) {
        this.printIndent();
        this.print("glIsTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsTexture = this.downstreamGLES3.glIsTexture(n);
        this.println(" = " + glIsTexture);
        return glIsTexture;
    }
    
    @Override
    public boolean glIsTransformFeedback(final int n) {
        this.printIndent();
        this.print("glIsTransformFeedback(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsTransformFeedback = this.downstreamGLES3.glIsTransformFeedback(n);
        this.println(" = " + glIsTransformFeedback);
        return glIsTransformFeedback;
    }
    
    @Override
    public boolean glIsVertexArray(final int n) {
        this.printIndent();
        this.print("glIsVertexArray(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsVertexArray = this.downstreamGLES3.glIsVertexArray(n);
        this.println(" = " + glIsVertexArray);
        return glIsVertexArray;
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        this.printIndent();
        this.print("glIsVertexArrayOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsVertexArrayOES = this.downstreamGLES3.glIsVertexArrayOES(n);
        this.println(" = " + glIsVertexArrayOES);
        return glIsVertexArrayOES;
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.printIndent();
        this.print("glLineWidth(<float> " + n + ")");
        this.downstreamGLES3.glLineWidth(n);
        this.println("");
    }
    
    @Override
    public void glLinkProgram(final int n) {
        this.printIndent();
        this.print("glLinkProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glLinkProgram(n);
        this.println("");
    }
    
    @Override
    public ByteBuffer glMapBuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glMapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final ByteBuffer glMapBuffer = this.downstreamGLES3.glMapBuffer(n, n2);
        this.println(" = " + glMapBuffer);
        return glMapBuffer;
    }
    
    @Override
    public ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.printIndent();
        this.print("glMapBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        final ByteBuffer glMapBufferRange = this.downstreamGLES3.glMapBufferRange(n, n2, n3, n4);
        this.println(" = " + glMapBufferRange);
        return glMapBufferRange;
    }
    
    @Override
    public void glMemoryBarrier(final int n) {
        this.printIndent();
        this.print("glMemoryBarrier(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glMemoryBarrier(n);
        this.println("");
    }
    
    @Override
    public void glMemoryBarrierByRegion(final int n) {
        this.printIndent();
        this.print("glMemoryBarrierByRegion(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glMemoryBarrierByRegion(n);
        this.println("");
    }
    
    @Override
    public void glMinSampleShading(final float n) {
        this.printIndent();
        this.print("glMinSampleShading(<float> " + n + ")");
        this.downstreamGLES3.glMinSampleShading(n);
        this.println("");
    }
    
    @Override
    public void glMinSampleShadingOES(final float n) {
        this.printIndent();
        this.print("glMinSampleShadingOES(<float> " + n + ")");
        this.downstreamGLES3.glMinSampleShadingOES(n);
        this.println("");
    }
    
    @Override
    public void glMultiDrawArraysIndirectEXT(final int n, final Buffer buffer, final int n2, final int n3) {
        this.printIndent();
        this.print("glMultiDrawArraysIndirectEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glMultiDrawArraysIndirectEXT(n, buffer, n2, n3);
        this.println("");
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glMultiDrawElementsBaseVertexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glMultiDrawElementsBaseVertexEXT(n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexEXT(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        this.printIndent();
        this.print("glMultiDrawElementsBaseVertexEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glMultiDrawElementsBaseVertexEXT(n, array, n2, n3, pointerBuffer, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final int[] array, final int n2, final int n3, final PointerBuffer pointerBuffer, final int n4, final int[] array2, final int n5) {
        this.printIndent();
        this.print("glMultiDrawElementsBaseVertexOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glMultiDrawElementsBaseVertexOES(n, array, n2, n3, pointerBuffer, n4, array2, n5);
        this.println("");
    }
    
    @Override
    public void glMultiDrawElementsBaseVertexOES(final int n, final IntBuffer intBuffer, final int n2, final PointerBuffer pointerBuffer, final int n3, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glMultiDrawElementsBaseVertexOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES3.glMultiDrawElementsBaseVertexOES(n, intBuffer, n2, pointerBuffer, n3, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glMultiDrawElementsIndirectEXT(final int n, final int n2, final Buffer buffer, final int n3, final int n4) {
        this.printIndent();
        this.print("glMultiDrawElementsIndirectEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glMultiDrawElementsIndirectEXT(n, n2, buffer, n3, n4);
        this.println("");
    }
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glNamedFramebufferSampleLocationsfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glNamedFramebufferSampleLocationsfvNV(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glNamedFramebufferSampleLocationsfvNV(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glNamedFramebufferSampleLocationsfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glNamedFramebufferSampleLocationsfvNV(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.printIndent();
        this.print("glObjectLabel(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glObjectLabel(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glObjectLabel(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glObjectLabel(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glObjectLabel(n, n2, n3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glObjectPtrLabel(<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glObjectPtrLabel(buffer, n, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glObjectPtrLabel(final Buffer buffer, final int n, final byte[] array, final int n2) {
        this.printIndent();
        this.print("glObjectPtrLabel(<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glObjectPtrLabel(buffer, n, array, n2);
        this.println("");
    }
    
    @Override
    public void glPatchParameteri(final int n, final int n2) {
        this.printIndent();
        this.print("glPatchParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glPatchParameteri(n, n2);
        this.println("");
    }
    
    @Override
    public void glPatchParameteriEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glPatchParameteriEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glPatchParameteriEXT(n, n2);
        this.println("");
    }
    
    @Override
    public void glPatchParameteriOES(final int n, final int n2) {
        this.printIndent();
        this.print("glPatchParameteriOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glPatchParameteriOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glPauseTransformFeedback() {
        this.printIndent();
        this.print("glPauseTransformFeedback()");
        this.downstreamGLES3.glPauseTransformFeedback();
        this.println("");
    }
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.printIndent();
        this.print("glPixelStorei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glPixelStorei(n, n2);
        this.println("");
    }
    
    @Override
    public void glPolygonModeNV(final int n, final int n2) {
        this.printIndent();
        this.print("glPolygonModeNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glPolygonModeNV(n, n2);
        this.println("");
    }
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        this.printIndent();
        this.print("glPolygonOffset(<float> " + n + ", " + "<float> " + n2 + ")");
        this.downstreamGLES3.glPolygonOffset(n, n2);
        this.println("");
    }
    
    @Override
    public void glPopDebugGroup() {
        this.printIndent();
        this.print("glPopDebugGroup()");
        this.downstreamGLES3.glPopDebugGroup();
        this.println("");
    }
    
    @Override
    public void glPrimitiveBoundingBox(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.printIndent();
        this.print("glPrimitiveBoundingBox(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ", " + "<float> " + n7 + ", " + "<float> " + n8 + ")");
        this.downstreamGLES3.glPrimitiveBoundingBox(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glPrimitiveBoundingBoxEXT(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.printIndent();
        this.print("glPrimitiveBoundingBoxEXT(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ", " + "<float> " + n7 + ", " + "<float> " + n8 + ")");
        this.downstreamGLES3.glPrimitiveBoundingBoxEXT(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glPrimitiveBoundingBoxOES(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        this.printIndent();
        this.print("glPrimitiveBoundingBoxOES(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ", " + "<float> " + n7 + ", " + "<float> " + n8 + ")");
        this.downstreamGLES3.glPrimitiveBoundingBoxOES(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glProgramBinary(final int n, final int n2, final Buffer buffer, final int n3) {
        this.printIndent();
        this.print("glProgramBinary(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glProgramBinary(n, n2, buffer, n3);
        this.println("");
    }
    
    @Override
    public void glProgramParameteri(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glProgramParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glProgramParameteri(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1f(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glProgramUniform1f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glProgramUniform1f(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform1fv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniform1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniform1fv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1i(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glProgramUniform1i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform1i(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform1iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform1iv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform1iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform1iv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1ui(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glProgramUniform1ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform1ui(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform1uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform1uiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform1uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform1uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform1uiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2f(final int n, final int n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glProgramUniform2f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES3.glProgramUniform2f(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniform2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniform2fv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform2fv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2i(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glProgramUniform2i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform2i(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform2iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform2iv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform2iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform2iv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2ui(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glProgramUniform2ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform2ui(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform2uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform2uiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform2uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform2uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform2uiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3f(final int n, final int n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glProgramUniform3f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES3.glProgramUniform3f(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform3fv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniform3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniform3fv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glProgramUniform3i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform3i(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform3iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform3iv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform3iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform3iv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glProgramUniform3ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform3ui(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform3uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform3uiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform3uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform3uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform3uiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4f(final int n, final int n2, final float n3, final float n4, final float n5, final float n6) {
        this.printIndent();
        this.print("glProgramUniform4f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ")");
        this.downstreamGLES3.glProgramUniform4f(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniform4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniform4fv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4fv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform4fv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4i(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glProgramUniform4i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform4i(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform4iv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4iv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform4iv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glProgramUniform4ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform4ui(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glProgramUniform4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glProgramUniform4uiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniform4uiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniform4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniform4uiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix2fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix2fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix2x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix2x3fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix2x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix2x3fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix2x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix2x4fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix2x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix2x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix2x4fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix3fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix3fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix3x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix3x2fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix3x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix3x2fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix3x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix3x4fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix3x4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix3x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix3x4fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix4fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix4fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix4x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix4x2fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4x2fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix4x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix4x2fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final float[] array, final int n4) {
        this.printIndent();
        this.print("glProgramUniformMatrix4x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glProgramUniformMatrix4x3fv(n, n2, n3, b, array, n4);
        this.println("");
    }
    
    @Override
    public void glProgramUniformMatrix4x3fv(final int n, final int n2, final int n3, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glProgramUniformMatrix4x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glProgramUniformMatrix4x3fv(n, n2, n3, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glPushDebugGroup(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES3.glPushDebugGroup(n, n2, n3, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glPushDebugGroup(final int n, final int n2, final int n3, final byte[] array, final int n4) {
        this.printIndent();
        this.print("glPushDebugGroup(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glPushDebugGroup(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glQueryCounter(final int n, final int n2) {
        this.printIndent();
        this.print("glQueryCounter(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glQueryCounter(n, n2);
        this.println("");
    }
    
    @Override
    public void glRasterSamplesEXT(final int n, final boolean b) {
        this.printIndent();
        this.print("glRasterSamplesEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glRasterSamplesEXT(n, b);
        this.println("");
    }
    
    @Override
    public void glReadBuffer(final int n) {
        this.printIndent();
        this.print("glReadBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glReadBuffer(n);
        this.println("");
    }
    
    @Override
    public void glReadBufferIndexedEXT(final int n, final int n2) {
        this.printIndent();
        this.print("glReadBufferIndexedEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glReadBufferIndexedEXT(n, n2);
        this.println("");
    }
    
    @Override
    public void glReadBufferNV(final int n) {
        this.printIndent();
        this.print("glReadBufferNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glReadBufferNV(n);
        this.println("");
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.printIndent();
        this.print("glReadPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<long> " + n7 + ")");
        this.downstreamGLES3.glReadPixels(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.printIndent();
        this.print("glReadPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        this.println("");
    }
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.printIndent();
        this.print("glReadnPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glReadnPixels(n, n2, n3, n4, n5, n6, n7, buffer);
        this.println("");
    }
    
    @Override
    public void glReleaseShaderCompiler() {
        this.printIndent();
        this.print("glReleaseShaderCompiler()");
        this.downstreamGLES3.glReleaseShaderCompiler();
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glRenderbufferStorage(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glRenderbufferStorage(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisampleEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisampleIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glRenderbufferStorageMultisampleIMG(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisampleNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisampleNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glRenderbufferStorageMultisampleNV(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glResolveDepthValuesNV() {
        this.printIndent();
        this.print("glResolveDepthValuesNV()");
        this.downstreamGLES3.glResolveDepthValuesNV();
        this.println("");
    }
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        this.printIndent();
        this.print("glResolveMultisampleFramebuffer()");
        this.downstreamGLES3.glResolveMultisampleFramebuffer();
        this.println("");
    }
    
    @Override
    public void glResumeTransformFeedback() {
        this.printIndent();
        this.print("glResumeTransformFeedback()");
        this.downstreamGLES3.glResumeTransformFeedback();
        this.println("");
    }
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        this.printIndent();
        this.print("glSampleCoverage(<float> " + n + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glSampleCoverage(n, b);
        this.println("");
    }
    
    @Override
    public void glSampleMaski(final int n, final int n2) {
        this.printIndent();
        this.print("glSampleMaski(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glSampleMaski(n, n2);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glSamplerParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glSamplerParameterIiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glSamplerParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glSamplerParameterIiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glSamplerParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glSamplerParameterIuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glSamplerParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glSamplerParameterIuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glSamplerParameterf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glSamplerParameterf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glSamplerParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glSamplerParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glSamplerParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glSamplerParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glSamplerParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameteri(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glSamplerParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glSamplerParameteri(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glSamplerParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glSamplerParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glSamplerParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glSamplerParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glSamplerParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glScissor(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glScissor(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glScissorArrayvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glScissorArrayvNV(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glScissorArrayvNV(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glScissorArrayvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glScissorArrayvNV(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glScissorIndexedNV(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glScissorIndexedNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glScissorIndexedNV(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glScissorIndexedvNV(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glScissorIndexedvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glScissorIndexedvNV(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glScissorIndexedvNV(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glScissorIndexedvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glScissorIndexedvNV(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glShaderBinary(final int n, final int[] array, final int n2, final int n3, final Buffer buffer, final int n4) {
        this.printIndent();
        this.print("glShaderBinary(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glShaderBinary(n, array, n2, n3, buffer, n4);
        this.println("");
    }
    
    @Override
    public void glShaderBinary(final int n, final IntBuffer intBuffer, final int n2, final Buffer buffer, final int n3) {
        this.printIndent();
        this.print("glShaderBinary(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glShaderBinary(n, intBuffer, n2, buffer, n3);
        this.println("");
    }
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glShaderSource(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glShaderSource(n, n2, array, intBuffer);
        this.println("");
    }
    
    @Override
    public void glShaderSource(final int n, final int n2, final String[] array, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glShaderSource(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glShaderSource(n, n2, array, array2, n3);
        this.println("");
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glStartTilingQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glStartTilingQCOM(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glStencilFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glStencilFunc(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glStencilFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glStencilFuncSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glStencilFuncSeparate(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glStencilMask(final int n) {
        this.printIndent();
        this.print("glStencilMask(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glStencilMask(n);
        this.println("");
    }
    
    @Override
    public void glStencilMaskSeparate(final int n, final int n2) {
        this.printIndent();
        this.print("glStencilMaskSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glStencilMaskSeparate(n, n2);
        this.println("");
    }
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glStencilOp(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glStencilOp(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glStencilOpSeparate(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glStencilOpSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glStencilOpSeparate(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glSubpixelPrecisionBiasNV(final int n, final int n2) {
        this.printIndent();
        this.print("glSubpixelPrecisionBiasNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glSubpixelPrecisionBiasNV(n, n2);
        this.println("");
    }
    
    @Override
    public void glTexBuffer(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexBuffer(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexBufferEXT(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexBufferEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexBufferEXT(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexBufferOES(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexBufferOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexBufferOES(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexBufferRange(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glTexBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glTexBufferRange(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTexBufferRangeEXT(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glTexBufferRangeEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glTexBufferRangeEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTexBufferRangeOES(final int n, final int n2, final int n3, final long n4, final long n5) {
        this.printIndent();
        this.print("glTexBufferRangeOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glTexBufferRangeOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES3.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glTexImage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        this.printIndent();
        this.print("glTexImage2DMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexImage2DMultisample(n, n2, n3, n4, n5, b);
        this.println("");
    }
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Buffer buffer) {
        this.printIndent();
        this.print("glTexImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, buffer);
        this.println("");
    }
    
    @Override
    public void glTexImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final long n10) {
        this.printIndent();
        this.print("glTexImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<long> " + n10 + ")");
        this.downstreamGLES3.glTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
        this.println("");
    }
    
    @Override
    public void glTexImage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.printIndent();
        this.print("glTexImage3DMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexImage3DMultisample(n, n2, n3, n4, n5, n6, b);
        this.println("");
    }
    
    @Override
    public void glTexPageCommitmentEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        this.printIndent();
        this.print("glTexPageCommitmentEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexPageCommitmentEXT(n, n2, n3, n4, n5, n6, n7, n8, b);
        this.println("");
    }
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexParameterIiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterIiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexParameterIiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glTexParameterIiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexParameterIuiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterIuiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexParameterIuiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glTexParameterIuiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glTexParameterf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glTexParameterf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glTexParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexParameteri(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTexParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glTexParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glTexStorage1D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glTexStorage1D(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glTexStorage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glTexStorage2D(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTexStorage2DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        this.printIndent();
        this.print("glTexStorage2DMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexStorage2DMultisample(n, n2, n3, n4, n5, b);
        this.println("");
    }
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glTexStorage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glTexStorage3D(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glTexStorage3DMultisample(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.printIndent();
        this.print("glTexStorage3DMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexStorage3DMultisample(n, n2, n3, n4, n5, n6, b);
        this.println("");
    }
    
    @Override
    public void glTexStorage3DMultisampleOES(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.printIndent();
        this.print("glTexStorage3DMultisampleOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES3.glTexStorage3DMultisampleOES(n, n2, n3, n4, n5, n6, b);
        this.println("");
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES3.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.printIndent();
        this.print("glTexSubImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        this.println("");
    }
    
    @Override
    public void glTexSubImage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final long n11) {
        this.printIndent();
        this.print("glTexSubImage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<long> " + n11 + ")");
        this.downstreamGLES3.glTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11);
        this.println("");
    }
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glTextureStorage1DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glTextureStorage1DEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glTextureStorage2DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES3.glTextureStorage2DEXT(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.printIndent();
        this.print("glTextureStorage3DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES3.glTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glTextureView(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.printIndent();
        this.print("glTextureView(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES3.glTextureView(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glTransformFeedbackVaryings(final int n, final int n2, final String[] array, final int n3) {
        this.printIndent();
        this.print("glTransformFeedbackVaryings(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[Ljava.lang.String;>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glTransformFeedbackVaryings(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform(final GLUniformData glUniformData) {
        this.printIndent();
        this.print("glUniform(<com.jogamp.opengl.GLUniformData> " + glUniformData + ")");
        this.downstreamGLES3.glUniform(glUniformData);
        this.println("");
    }
    
    @Override
    public void glUniform1f(final int n, final float n2) {
        this.printIndent();
        this.print("glUniform1f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES3.glUniform1f(n, n2);
        this.println("");
    }
    
    @Override
    public void glUniform1fv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniform1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform1fv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform1fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniform1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniform1fv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform1i(final int n, final int n2) {
        this.printIndent();
        this.print("glUniform1i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glUniform1i(n, n2);
        this.println("");
    }
    
    @Override
    public void glUniform1iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform1iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform1iv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform1iv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform1iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform1iv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform1ui(final int n, final int n2) {
        this.printIndent();
        this.print("glUniform1ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glUniform1ui(n, n2);
        this.println("");
    }
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform1uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform1uiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform1uiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform1uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform1uiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2f(final int n, final float n2, final float n3) {
        this.printIndent();
        this.print("glUniform2f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glUniform2f(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniform2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniform2fv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform2fv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniform2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform2fv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2i(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glUniform2i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform2i(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform2iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform2iv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform2iv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform2iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform2iv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2ui(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glUniform2ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform2ui(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform2uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform2uiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform2uiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform2uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform2uiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform3f(final int n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glUniform3f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES3.glUniform3f(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glUniform3fv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniform3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform3fv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform3fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniform3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniform3fv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform3i(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glUniform3i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glUniform3i(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glUniform3iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform3iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform3iv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform3iv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform3iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform3iv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform3ui(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glUniform3ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glUniform3ui(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform3uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform3uiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform3uiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform3uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform3uiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glUniform4f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES3.glUniform4f(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glUniform4fv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniform4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniform4fv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform4fv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniform4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform4fv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glUniform4i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glUniform4i(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glUniform4iv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform4iv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform4iv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform4iv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniform4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glUniform4ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glUniform4ui(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glUniform4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glUniform4uiv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glUniform4uiv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glUniform4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniform4uiv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformBlockBinding(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glUniformBlockBinding(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformBlockBinding(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix2fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix2fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix2x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix2x3fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix2x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix2x3fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix2x3fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix2x3fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix2x3fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix2x3fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix2x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix2x4fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix2x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix2x4fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix2x4fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix2x4fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix2x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix2x4fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix2x4fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix3fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix3fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix3x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix3x2fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix3x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix3x2fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix3x2fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix3x2fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix3x2fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix3x2fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix3x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix3x4fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix3x4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix3x4fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix3x4fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix3x4fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix3x4fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix3x4fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix3x4fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix4fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix4fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix4x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix4x2fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x2fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix4x2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix4x2fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix4x2fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix4x2fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x2fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix4x2fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix4x2fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix4x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix4x3fv(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x3fv(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix4x3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix4x3fv(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final float[] array, final int n3) {
        this.printIndent();
        this.print("glUniformMatrix4x3fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUniformMatrix4x3fvNV(n, n2, b, array, n3);
        this.println("");
    }
    
    @Override
    public void glUniformMatrix4x3fvNV(final int n, final int n2, final boolean b, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glUniformMatrix4x3fvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<boolean> " + b + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glUniformMatrix4x3fvNV(n, n2, b, floatBuffer);
        this.println("");
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        this.printIndent();
        this.print("glUnmapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glUnmapBuffer = this.downstreamGLES3.glUnmapBuffer(n);
        this.println(" = " + glUnmapBuffer);
        return glUnmapBuffer;
    }
    
    @Override
    public void glUseProgram(final int n) {
        this.printIndent();
        this.print("glUseProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glUseProgram(n);
        this.println("");
    }
    
    @Override
    public void glUseProgramStages(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glUseProgramStages(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glUseProgramStages(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glValidateProgram(final int n) {
        this.printIndent();
        this.print("glValidateProgram(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glValidateProgram(n);
        this.println("");
    }
    
    @Override
    public void glValidateProgramPipeline(final int n) {
        this.printIndent();
        this.print("glValidateProgramPipeline(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES3.glValidateProgramPipeline(n);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib1f(final int n, final float n2) {
        this.printIndent();
        this.print("glVertexAttrib1f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES3.glVertexAttrib1f(n, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib1fv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttrib1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttrib1fv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib1fv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glVertexAttrib1fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glVertexAttrib1fv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib2f(final int n, final float n2, final float n3) {
        this.printIndent();
        this.print("glVertexAttrib2f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES3.glVertexAttrib2f(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib2fv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glVertexAttrib2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glVertexAttrib2fv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib2fv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttrib2fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttrib2fv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib3f(final int n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glVertexAttrib3f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES3.glVertexAttrib3f(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib3fv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttrib3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttrib3fv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib3fv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glVertexAttrib3fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glVertexAttrib3fv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glVertexAttrib4f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES3.glVertexAttrib4f(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib4fv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glVertexAttrib4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glVertexAttrib4fv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttrib4fv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttrib4fv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttrib4fv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribBinding(final int n, final int n2) {
        this.printIndent();
        this.print("glVertexAttribBinding(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribBinding(n, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribDivisor(final int n, final int n2) {
        this.printIndent();
        this.print("glVertexAttribDivisor(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribDivisor(n, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribDivisorANGLE(final int n, final int n2) {
        this.printIndent();
        this.print("glVertexAttribDivisorANGLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribDivisorANGLE(n, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribDivisorNV(final int n, final int n2) {
        this.printIndent();
        this.print("glVertexAttribDivisorNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribDivisorNV(n, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribFormat(final int n, final int n2, final int n3, final boolean b, final int n4) {
        this.printIndent();
        this.print("glVertexAttribFormat(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribFormat(n, n2, n3, b, n4);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4i(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glVertexAttribI4i(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribI4i(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4iv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttribI4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribI4iv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4iv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glVertexAttribI4iv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glVertexAttribI4iv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4ui(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glVertexAttribI4ui(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribI4ui(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4uiv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glVertexAttribI4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribI4uiv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glVertexAttribI4uiv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glVertexAttribI4uiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES3.glVertexAttribI4uiv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttribIFormat(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glVertexAttribIFormat(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glVertexAttribIFormat(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final long n5) {
        this.printIndent();
        this.print("glVertexAttribIPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glVertexAttribIPointer(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glVertexAttribIPointer(final int n, final int n2, final int n3, final int n4, final Buffer buffer) {
        this.printIndent();
        this.print("glVertexAttribIPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glVertexAttribIPointer(n, n2, n3, n4, buffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer) {
        this.printIndent();
        this.print("glVertexAttribPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES3.glVertexAttribPointer(n, n2, n3, b, n4, buffer);
        this.println("");
    }
    
    @Override
    public void glVertexAttribPointer(final int n, final int n2, final int n3, final boolean b, final int n4, final long n5) {
        this.printIndent();
        this.print("glVertexAttribPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<boolean> " + b + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<long> " + n5 + ")");
        this.downstreamGLES3.glVertexAttribPointer(n, n2, n3, b, n4, n5);
        this.println("");
    }
    
    @Override
    public void glVertexAttribPointer(final GLArrayData glArrayData) {
        this.printIndent();
        this.print("glVertexAttribPointer(<com.jogamp.opengl.GLArrayData> " + glArrayData + ")");
        this.downstreamGLES3.glVertexAttribPointer(glArrayData);
        this.println("");
    }
    
    @Override
    public void glVertexBindingDivisor(final int n, final int n2) {
        this.printIndent();
        this.print("glVertexBindingDivisor(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glVertexBindingDivisor(n, n2);
        this.println("");
    }
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glViewport(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES3.glViewport(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glViewportArrayvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES3.glViewportArrayvNV(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glViewportArrayvNV(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glViewportArrayvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glViewportArrayvNV(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glViewportIndexedfNV(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glViewportIndexedfNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES3.glViewportIndexedfNV(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glViewportIndexedfvNV(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glViewportIndexedfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES3.glViewportIndexedfvNV(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glViewportIndexedfvNV(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glViewportIndexedfvNV(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES3.glViewportIndexedfvNV(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glWaitSync(final long n, final int n2, final long n3) {
        this.printIndent();
        this.print("glWaitSync(<long> " + n + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ")");
        this.downstreamGLES3.glWaitSync(n, n2, n3);
        this.println("");
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
        this.printIndent();
        this.print("mapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final GLBufferStorage mapBuffer = this.downstreamGLES3.mapBuffer(n, n2);
        this.println(" = " + mapBuffer);
        return mapBuffer;
    }
    
    @Override
    public GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.printIndent();
        this.print("mapBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        final GLBufferStorage mapBufferRange = this.downstreamGLES3.mapBufferRange(n, n2, n3, n4);
        this.println(" = " + mapBufferRange);
        return mapBufferRange;
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        this.downstreamGLES3.setSwapInterval(swapInterval);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TraceGLES3 [this 0x" + Integer.toHexString(this.hashCode()) + " implementing com.jogamp.opengl.GLES3,\n\t");
        sb.append(" downstream: " + this.downstreamGLES3.toString() + "\n\t]");
        return sb.toString();
    }
    
    protected String dumpArray(final Object o) {
        if (o == null) {
            return "[null]";
        }
        final StringBuilder sb = new StringBuilder("[");
        final int length = Array.getLength(o);
        for (int min = Math.min(length, 16), i = 0; i < min; ++i) {
            sb.append(Array.get(o, i));
            if (i < min - 1) {
                sb.append(',');
            }
        }
        if (length > 16) {
            sb.append("...").append(length);
        }
        sb.append(']');
        return sb.toString();
    }
    
    protected void print(final String s) {
        this.stream.print(s);
    }
    
    protected void println(final String s) {
        this.stream.println(s);
    }
    
    protected void printIndent() {
        for (int i = 0; i < this.indent; ++i) {
            this.stream.print(' ');
        }
    }
    
    static {
        DEBUG = Debug.debug("TraceGLES3");
    }
}
