// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.PointerBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import java.io.PrintStream;

public class TraceGLES1 implements GL2ES1, GLES1
{
    public static final boolean DEBUG;
    private PrintStream stream;
    private int indent;
    private GLES1 downstreamGLES1;
    
    public TraceGLES1(final GLES1 downstreamGLES1, final PrintStream stream) {
        this.indent = 0;
        if (downstreamGLES1 == null) {
            throw new IllegalArgumentException("null downstreamGLES1");
        }
        this.downstreamGLES1 = downstreamGLES1;
        this.stream = stream;
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
        this.printIndent();
        this.print("glActiveTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glActiveTexture(n);
        this.println("");
    }
    
    @Override
    public void glAlphaFunc(final int n, final float n2) {
        this.printIndent();
        this.print("glAlphaFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glAlphaFunc(n, n2);
        this.println("");
    }
    
    @Override
    public void glAlphaFuncx(final int n, final int n2) {
        this.printIndent();
        this.print("glAlphaFuncx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glAlphaFuncx(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindBuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBindBuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindFramebuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBindFramebuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindRenderbuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glBindRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBindRenderbuffer(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.printIndent();
        this.print("glBindTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBindTexture(n, n2);
        this.println("");
    }
    
    @Override
    public void glBindVertexArrayOES(final int n) {
        this.printIndent();
        this.print("glBindVertexArrayOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glBindVertexArrayOES(n);
        this.println("");
    }
    
    @Override
    public void glBlendEquation(final int n) {
        this.printIndent();
        this.print("glBlendEquation(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glBlendEquation(n);
        this.println("");
    }
    
    @Override
    public void glBlendEquationSeparate(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendEquationSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBlendEquationSeparate(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.printIndent();
        this.print("glBlendFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glBlendFunc(n, n2);
        this.println("");
    }
    
    @Override
    public void glBlendFuncSeparate(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glBlendFuncSeparate(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glBlendFuncSeparate(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glBufferData(final int n, final long n2, final Buffer buffer, final int n3) {
        this.printIndent();
        this.print("glBufferData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<java.nio.Buffer> " + buffer + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glBufferData(n, n2, buffer, n3);
        this.println("");
    }
    
    @Override
    public void glBufferSubData(final int n, final long n2, final long n3, final Buffer buffer) {
        this.printIndent();
        this.print("glBufferSubData(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glBufferSubData(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public int glCheckFramebufferStatus(final int n) {
        this.printIndent();
        this.print("glCheckFramebufferStatus(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final int glCheckFramebufferStatus = this.downstreamGLES1.glCheckFramebufferStatus(n);
        this.println(" = " + glCheckFramebufferStatus);
        return glCheckFramebufferStatus;
    }
    
    @Override
    public void glClear(final int n) {
        this.printIndent();
        this.print("glClear(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glClear(n);
        this.println("");
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glClearColor(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES1.glClearColor(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glClearColorx(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glClearColorx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glClearColorx(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glClearDepth(final double n) {
        this.printIndent();
        this.print("glClearDepth(<double> " + n + ")");
        this.downstreamGLES1.glClearDepth(n);
        this.println("");
    }
    
    @Override
    public void glClearDepthf(final float n) {
        this.printIndent();
        this.print("glClearDepthf(<float> " + n + ")");
        this.downstreamGLES1.glClearDepthf(n);
        this.println("");
    }
    
    @Override
    public void glClearDepthx(final int n) {
        this.printIndent();
        this.print("glClearDepthx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glClearDepthx(n);
        this.println("");
    }
    
    @Override
    public void glClearStencil(final int n) {
        this.printIndent();
        this.print("glClearStencil(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glClearStencil(n);
        this.println("");
    }
    
    @Override
    public void glClientActiveTexture(final int n) {
        this.printIndent();
        this.print("glClientActiveTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glClientActiveTexture(n);
        this.println("");
    }
    
    @Override
    public void glClipPlanef(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glClipPlanef(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glClipPlanef(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glClipPlanef(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glClipPlanef(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glClipPlanef(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glClipPlanefIMG(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glClipPlanefIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glClipPlanefIMG(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glClipPlanefIMG(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glClipPlanefIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glClipPlanefIMG(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glClipPlanex(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glClipPlanex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glClipPlanex(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glClipPlanex(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glClipPlanex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glClipPlanex(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glClipPlanexIMG(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glClipPlanexIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glClipPlanexIMG(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glClipPlanexIMG(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glClipPlanexIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glClipPlanexIMG(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glColor4f(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES1.glColor4f(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        this.printIndent();
        this.print("glColor4ub(<byte> " + b + ", " + "<byte> " + b2 + ", " + "<byte> " + b3 + ", " + "<byte> " + b4 + ")");
        this.downstreamGLES1.glColor4ub(b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glColor4x(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glColor4x(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glColor4x(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.printIndent();
        this.print("glColorMask(<boolean> " + b + ", " + "<boolean> " + b2 + ", " + "<boolean> " + b3 + ", " + "<boolean> " + b4 + ")");
        this.downstreamGLES1.glColorMask(b, b2, b3, b4);
        this.println("");
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glColorPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glColorPointer(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glColorPointer(final GLArrayData glArrayData) {
        this.printIndent();
        this.print("glColorPointer(<com.jogamp.opengl.GLArrayData> " + glArrayData + ")");
        this.downstreamGLES1.glColorPointer(glArrayData);
        this.println("");
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        this.printIndent();
        this.print("glColorPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ")");
        this.downstreamGLES1.glColorPointer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final long n8) {
        this.printIndent();
        this.print("glCompressedTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<long> " + n8 + ")");
        this.downstreamGLES1.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glCompressedTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glCompressedTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glCompressedTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES1.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.printIndent();
        this.print("glCopyTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES1.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCopyTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        this.printIndent();
        this.print("glCopyTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ")");
        this.downstreamGLES1.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.println("");
    }
    
    @Override
    public void glCopyTextureLevelsAPPLE(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glCopyTextureLevelsAPPLE(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glCopyTextureLevelsAPPLE(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glCullFace(final int n) {
        this.printIndent();
        this.print("glCullFace(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glCullFace(n);
        this.println("");
    }
    
    @Override
    public void glCurrentPaletteMatrixOES(final int n) {
        this.printIndent();
        this.print("glCurrentPaletteMatrixOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glCurrentPaletteMatrixOES(n);
        this.println("");
    }
    
    @Override
    public void glDeleteBuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDeleteBuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteBuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDeleteBuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDeleteFramebuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteFramebuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDeleteFramebuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDeleteRenderbuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDeleteRenderbuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteTextures(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDeleteTextures(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDeleteTextures(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDeleteTextures(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glDeleteVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDeleteVertexArraysOES(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glDeleteVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDeleteVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDeleteVertexArraysOES(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDepthFunc(final int n) {
        this.printIndent();
        this.print("glDepthFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDepthFunc(n);
        this.println("");
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        this.printIndent();
        this.print("glDepthMask(<boolean> " + b + ")");
        this.downstreamGLES1.glDepthMask(b);
        this.println("");
    }
    
    @Override
    public void glDepthRange(final double n, final double n2) {
        this.printIndent();
        this.print("glDepthRange(<double> " + n + ", " + "<double> " + n2 + ")");
        this.downstreamGLES1.glDepthRange(n, n2);
        this.println("");
    }
    
    @Override
    public void glDepthRangef(final float n, final float n2) {
        this.printIndent();
        this.print("glDepthRangef(<float> " + n + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glDepthRangef(n, n2);
        this.println("");
    }
    
    @Override
    public void glDepthRangex(final int n, final int n2) {
        this.printIndent();
        this.print("glDepthRangex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glDepthRangex(n, n2);
        this.println("");
    }
    
    @Override
    public void glDisable(final int n) {
        this.printIndent();
        this.print("glDisable(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDisable(n);
        this.println("");
    }
    
    @Override
    public void glDisableClientState(final int n) {
        this.printIndent();
        this.print("glDisableClientState(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDisableClientState(n);
        this.println("");
    }
    
    @Override
    public void glDisableDriverControlQCOM(final int n) {
        this.printIndent();
        this.print("glDisableDriverControlQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDisableDriverControlQCOM(n);
        this.println("");
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glDiscardFramebufferEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glDiscardFramebufferEXT(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glDiscardFramebufferEXT(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDiscardFramebufferEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDiscardFramebufferEXT(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glDrawArrays(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glDrawArrays(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.printIndent();
        this.print("glDrawElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ")");
        this.downstreamGLES1.glDrawElements(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glDrawElements(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glDrawElements(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glDrawTexfOES(final float n, final float n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glDrawTexfOES(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES1.glDrawTexfOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawTexfvOES(final float[] array, final int n) {
        this.printIndent();
        this.print("glDrawTexfvOES(<[F>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexfvOES(array, n);
        this.println("");
    }
    
    @Override
    public void glDrawTexfvOES(final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glDrawTexfvOES(<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glDrawTexfvOES(floatBuffer);
        this.println("");
    }
    
    @Override
    public void glDrawTexiOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawTexiOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexiOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawTexivOES(final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDrawTexivOES(<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDrawTexivOES(intBuffer);
        this.println("");
    }
    
    @Override
    public void glDrawTexivOES(final int[] array, final int n) {
        this.printIndent();
        this.print("glDrawTexivOES(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexivOES(array, n);
        this.println("");
    }
    
    @Override
    public void glDrawTexsOES(final short n, final short n2, final short n3, final short n4, final short n5) {
        this.printIndent();
        this.print("glDrawTexsOES(<short> " + n + ", " + "<short> " + n2 + ", " + "<short> " + n3 + ", " + "<short> " + n4 + ", " + "<short> " + n5 + ")");
        this.downstreamGLES1.glDrawTexsOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawTexsvOES(final short[] array, final int n) {
        this.printIndent();
        this.print("glDrawTexsvOES(<[S>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexsvOES(array, n);
        this.println("");
    }
    
    @Override
    public void glDrawTexsvOES(final ShortBuffer shortBuffer) {
        this.printIndent();
        this.print("glDrawTexsvOES(<java.nio.ShortBuffer> " + shortBuffer + ")");
        this.downstreamGLES1.glDrawTexsvOES(shortBuffer);
        this.println("");
    }
    
    @Override
    public void glDrawTexxOES(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glDrawTexxOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexxOES(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glDrawTexxvOES(final int[] array, final int n) {
        this.printIndent();
        this.print("glDrawTexxvOES(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glDrawTexxvOES(array, n);
        this.println("");
    }
    
    @Override
    public void glDrawTexxvOES(final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glDrawTexxvOES(<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glDrawTexxvOES(intBuffer);
        this.println("");
    }
    
    @Override
    public void glEGLImageTargetRenderbufferStorageOES(final int n, final long n2) {
        this.printIndent();
        this.print("glEGLImageTargetRenderbufferStorageOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ")");
        this.downstreamGLES1.glEGLImageTargetRenderbufferStorageOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glEGLImageTargetTexture2DOES(final int n, final long n2) {
        this.printIndent();
        this.print("glEGLImageTargetTexture2DOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ")");
        this.downstreamGLES1.glEGLImageTargetTexture2DOES(n, n2);
        this.println("");
    }
    
    @Override
    public void glEnable(final int n) {
        this.printIndent();
        this.print("glEnable(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glEnable(n);
        this.println("");
    }
    
    @Override
    public void glEnableClientState(final int n) {
        this.printIndent();
        this.print("glEnableClientState(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glEnableClientState(n);
        this.println("");
    }
    
    @Override
    public void glEnableDriverControlQCOM(final int n) {
        this.printIndent();
        this.print("glEnableDriverControlQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glEnableDriverControlQCOM(n);
        this.println("");
    }
    
    @Override
    public void glEndTilingQCOM(final int n) {
        this.printIndent();
        this.print("glEndTilingQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glEndTilingQCOM(n);
        this.println("");
    }
    
    @Override
    public void glExtGetBufferPointervQCOM(final int n, final PointerBuffer pointerBuffer) {
        this.printIndent();
        this.print("glExtGetBufferPointervQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<com.jogamp.common.nio.PointerBuffer> " + pointerBuffer + ")");
        this.downstreamGLES1.glExtGetBufferPointervQCOM(n, pointerBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetBuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetBuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetBuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetBuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetBuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetBuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetFramebuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetFramebuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetFramebuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetFramebuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetFramebuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final ByteBuffer byteBuffer, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glExtGetProgramBinarySourceQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glExtGetProgramBinarySourceQCOM(n, n2, byteBuffer, intBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramBinarySourceQCOM(final int n, final int n2, final byte[] array, final int n3, final int[] array2, final int n4) {
        this.printIndent();
        this.print("glExtGetProgramBinarySourceQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetProgramBinarySourceQCOM(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetProgramsQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetProgramsQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetProgramsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetProgramsQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetProgramsQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetRenderbuffersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetRenderbuffersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetRenderbuffersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetRenderbuffersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetRenderbuffersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetShadersQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetShadersQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetShadersQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetShadersQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetShadersQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetShadersQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final int[] array, final int n5) {
        this.printIndent();
        this.print("glExtGetTexLevelParameterivQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, array, n5);
        this.println("");
    }
    
    @Override
    public void glExtGetTexLevelParameterivQCOM(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glExtGetTexLevelParameterivQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glExtGetTexLevelParameterivQCOM(n, n2, n3, n4, intBuffer);
        this.println("");
    }
    
    @Override
    public void glExtGetTexSubImageQCOM(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final Buffer buffer) {
        this.printIndent();
        this.print("glExtGetTexSubImageQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n9).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n10).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glExtGetTexSubImageQCOM(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, buffer);
        this.println("");
    }
    
    @Override
    public void glExtGetTexturesQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glExtGetTexturesQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtGetTexturesQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glExtGetTexturesQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glExtGetTexturesQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glExtGetTexturesQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public boolean glExtIsProgramBinaryQCOM(final int n) {
        this.printIndent();
        this.print("glExtIsProgramBinaryQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glExtIsProgramBinaryQCOM = this.downstreamGLES1.glExtIsProgramBinaryQCOM(n);
        this.println(" = " + glExtIsProgramBinaryQCOM);
        return glExtIsProgramBinaryQCOM;
    }
    
    @Override
    public void glExtTexObjectStateOverrideiQCOM(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glExtTexObjectStateOverrideiQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glExtTexObjectStateOverrideiQCOM(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glFinish() {
        this.printIndent();
        this.print("glFinish()");
        this.downstreamGLES1.glFinish();
        this.println("");
    }
    
    @Override
    public void glFlush() {
        this.printIndent();
        this.print("glFlush()");
        this.downstreamGLES1.glFlush();
        this.println("");
    }
    
    @Override
    public void glFlushMappedBufferRange(final int n, final long n2, final long n3) {
        this.printIndent();
        this.print("glFlushMappedBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ")");
        this.downstreamGLES1.glFlushMappedBufferRange(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glFogf(final int n, final float n2) {
        this.printIndent();
        this.print("glFogf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glFogf(n, n2);
        this.println("");
    }
    
    @Override
    public void glFogfv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glFogfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glFogfv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glFogfv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glFogfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glFogfv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glFogx(final int n, final int n2) {
        this.printIndent();
        this.print("glFogx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glFogx(n, n2);
        this.println("");
    }
    
    @Override
    public void glFogxv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glFogxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glFogxv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glFogxv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glFogxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glFogxv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glFramebufferRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glFramebufferRenderbuffer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glFramebufferTexture2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glFramebufferTexture2D(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTexture2DMultisampleEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glFramebufferTexture2DMultisampleEXT(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFramebufferTexture2DMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFramebufferTexture2DMultisampleIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glFramebufferTexture2DMultisampleIMG(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFrontFace(final int n) {
        this.printIndent();
        this.print("glFrontFace(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glFrontFace(n);
        this.println("");
    }
    
    @Override
    public void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.printIndent();
        this.print("glFrustum(<double> " + n + ", " + "<double> " + n2 + ", " + "<double> " + n3 + ", " + "<double> " + n4 + ", " + "<double> " + n5 + ", " + "<double> " + n6 + ")");
        this.downstreamGLES1.glFrustum(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.printIndent();
        this.print("glFrustumf(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ")");
        this.downstreamGLES1.glFrustumf(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glFrustumx(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glFrustumx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glFrustumx(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glGenBuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGenBuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenBuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenBuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGenBuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenFramebuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGenFramebuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenFramebuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenFramebuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGenFramebuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGenRenderbuffers(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenRenderbuffers(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenRenderbuffers(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGenRenderbuffers(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenTextures(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGenTextures(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenTextures(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenTextures(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGenTextures(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGenVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGenVertexArraysOES(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGenVertexArraysOES(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGenVertexArraysOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGenVertexArraysOES(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGenerateMipmap(final int n) {
        this.printIndent();
        this.print("glGenerateMipmap(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glGenerateMipmap(n);
        this.println("");
    }
    
    @Override
    public void glGetBooleanv(final int n, final byte[] array, final int n2) {
        this.printIndent();
        this.print("glGetBooleanv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetBooleanv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetBooleanv(final int n, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetBooleanv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES1.glGetBooleanv(n, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetBufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetBufferParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetBufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetBufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetBufferParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetClipPlanef(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetClipPlanef(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetClipPlanef(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetClipPlanef(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glGetClipPlanef(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetClipPlanef(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetClipPlanex(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGetClipPlanex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetClipPlanex(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetClipPlanex(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetClipPlanex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetClipPlanex(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final IntBuffer intBuffer, final ByteBuffer byteBuffer) {
        this.printIndent();
        this.print("glGetDriverControlStringQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.ByteBuffer> " + byteBuffer + ")");
        this.downstreamGLES1.glGetDriverControlStringQCOM(n, n2, intBuffer, byteBuffer);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlStringQCOM(final int n, final int n2, final int[] array, final int n3, final byte[] array2, final int n4) {
        this.printIndent();
        this.print("glGetDriverControlStringQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[B>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glGetDriverControlStringQCOM(n, n2, array, n3, array2, n4);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlsQCOM(final int[] array, final int n, final int n2, final int[] array2, final int n3) {
        this.printIndent();
        this.print("glGetDriverControlsQCOM(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetDriverControlsQCOM(array, n, n2, array2, n3);
        this.println("");
    }
    
    @Override
    public void glGetDriverControlsQCOM(final IntBuffer intBuffer, final int n, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glGetDriverControlsQCOM(<java.nio.IntBuffer> " + intBuffer + ", " + "<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        this.downstreamGLES1.glGetDriverControlsQCOM(intBuffer, n, intBuffer2);
        this.println("");
    }
    
    @Override
    public int glGetError() {
        this.printIndent();
        this.print("glGetError()");
        final int glGetError = this.downstreamGLES1.glGetError();
        this.println(" = " + glGetError);
        return glGetError;
    }
    
    @Override
    public void glGetFixedv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetFixedv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetFixedv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFixedv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGetFixedv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetFixedv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetFloatv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetFloatv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glGetFloatv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetFloatv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetFramebufferAttachmentParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetFramebufferAttachmentParameteriv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetFramebufferAttachmentParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glGetFramebufferAttachmentParameteriv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public int glGetGraphicsResetStatus() {
        this.printIndent();
        this.print("glGetGraphicsResetStatus()");
        final int glGetGraphicsResetStatus = this.downstreamGLES1.glGetGraphicsResetStatus();
        this.println(" = " + glGetGraphicsResetStatus);
        return glGetGraphicsResetStatus;
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetIntegerv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetIntegerv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glGetIntegerv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glGetIntegerv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetLightfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetLightfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetLightxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetLightxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetLightxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetLightxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetLightxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetMaterialfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetMaterialfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetMaterialxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetMaterialxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetMaterialxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetMaterialxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetRenderbufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetRenderbufferParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetRenderbufferParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetRenderbufferParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetRenderbufferParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public String glGetString(final int n) {
        this.printIndent();
        this.print("glGetString(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final String glGetString = this.downstreamGLES1.glGetString(n);
        this.println(" = " + glGetString);
        return glGetString;
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetTexEnvfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexEnvfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexEnviv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexEnviv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexEnvxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexEnvxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexEnvxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexEnvxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetTexGenfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetTexGenfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexGenfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexGenfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexGeniv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexGeniv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexGeniv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexGeniv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexGenxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexGenxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexGenxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexGenxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetTexParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetTexParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetTexParameterxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glGetTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glGetTexParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glGetTexParameterxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glGetnUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glGetnUniformfv(n, n2, n3, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glGetnUniformfv(final int n, final int n2, final int n3, final float[] array, final int n4) {
        this.printIndent();
        this.print("glGetnUniformfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glGetnUniformfv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final int[] array, final int n4) {
        this.printIndent();
        this.print("glGetnUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glGetnUniformiv(n, n2, n3, array, n4);
        this.println("");
    }
    
    @Override
    public void glGetnUniformiv(final int n, final int n2, final int n3, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glGetnUniformiv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glGetnUniformiv(n, n2, n3, intBuffer);
        this.println("");
    }
    
    @Override
    public void glHint(final int n, final int n2) {
        this.printIndent();
        this.print("glHint(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glHint(n, n2);
        this.println("");
    }
    
    @Override
    public boolean glIsBuffer(final int n) {
        this.printIndent();
        this.print("glIsBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsBuffer = this.downstreamGLES1.glIsBuffer(n);
        this.println(" = " + glIsBuffer);
        return glIsBuffer;
    }
    
    @Override
    public boolean glIsEnabled(final int n) {
        this.printIndent();
        this.print("glIsEnabled(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsEnabled = this.downstreamGLES1.glIsEnabled(n);
        this.println(" = " + glIsEnabled);
        return glIsEnabled;
    }
    
    @Override
    public boolean glIsFramebuffer(final int n) {
        this.printIndent();
        this.print("glIsFramebuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsFramebuffer = this.downstreamGLES1.glIsFramebuffer(n);
        this.println(" = " + glIsFramebuffer);
        return glIsFramebuffer;
    }
    
    @Override
    public boolean glIsRenderbuffer(final int n) {
        this.printIndent();
        this.print("glIsRenderbuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsRenderbuffer = this.downstreamGLES1.glIsRenderbuffer(n);
        this.println(" = " + glIsRenderbuffer);
        return glIsRenderbuffer;
    }
    
    @Override
    public boolean glIsTexture(final int n) {
        this.printIndent();
        this.print("glIsTexture(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsTexture = this.downstreamGLES1.glIsTexture(n);
        this.println(" = " + glIsTexture);
        return glIsTexture;
    }
    
    @Override
    public boolean glIsVertexArrayOES(final int n) {
        this.printIndent();
        this.print("glIsVertexArrayOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glIsVertexArrayOES = this.downstreamGLES1.glIsVertexArrayOES(n);
        this.println(" = " + glIsVertexArrayOES);
        return glIsVertexArrayOES;
    }
    
    @Override
    public void glLightModelf(final int n, final float n2) {
        this.printIndent();
        this.print("glLightModelf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glLightModelf(n, n2);
        this.println("");
    }
    
    @Override
    public void glLightModelfv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glLightModelfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glLightModelfv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glLightModelfv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glLightModelfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glLightModelfv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glLightModelx(final int n, final int n2) {
        this.printIndent();
        this.print("glLightModelx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glLightModelx(n, n2);
        this.println("");
    }
    
    @Override
    public void glLightModelxv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glLightModelxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glLightModelxv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glLightModelxv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glLightModelxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glLightModelxv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glLightf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glLightf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glLightf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glLightfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glLightfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glLightfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glLightx(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glLightx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glLightx(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glLightxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glLightxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glLightxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glLightxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glLightxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glLightxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.printIndent();
        this.print("glLineWidth(<float> " + n + ")");
        this.downstreamGLES1.glLineWidth(n);
        this.println("");
    }
    
    @Override
    public void glLineWidthx(final int n) {
        this.printIndent();
        this.print("glLineWidthx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glLineWidthx(n);
        this.println("");
    }
    
    @Override
    public void glLoadIdentity() {
        this.printIndent();
        this.print("glLoadIdentity()");
        this.downstreamGLES1.glLoadIdentity();
        this.println("");
    }
    
    @Override
    public void glLoadMatrixf(final float[] array, final int n) {
        this.printIndent();
        this.print("glLoadMatrixf(<[F>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glLoadMatrixf(array, n);
        this.println("");
    }
    
    @Override
    public void glLoadMatrixf(final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glLoadMatrixf(<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glLoadMatrixf(floatBuffer);
        this.println("");
    }
    
    @Override
    public void glLoadMatrixx(final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glLoadMatrixx(<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glLoadMatrixx(intBuffer);
        this.println("");
    }
    
    @Override
    public void glLoadMatrixx(final int[] array, final int n) {
        this.printIndent();
        this.print("glLoadMatrixx(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glLoadMatrixx(array, n);
        this.println("");
    }
    
    @Override
    public void glLoadPaletteFromModelViewMatrixOES() {
        this.printIndent();
        this.print("glLoadPaletteFromModelViewMatrixOES()");
        this.downstreamGLES1.glLoadPaletteFromModelViewMatrixOES();
        this.println("");
    }
    
    @Override
    public void glLogicOp(final int n) {
        this.printIndent();
        this.print("glLogicOp(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glLogicOp(n);
        this.println("");
    }
    
    @Override
    public ByteBuffer glMapBuffer(final int n, final int n2) {
        this.printIndent();
        this.print("glMapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final ByteBuffer glMapBuffer = this.downstreamGLES1.glMapBuffer(n, n2);
        this.println(" = " + glMapBuffer);
        return glMapBuffer;
    }
    
    @Override
    public ByteBuffer glMapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.printIndent();
        this.print("glMapBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        final ByteBuffer glMapBufferRange = this.downstreamGLES1.glMapBufferRange(n, n2, n3, n4);
        this.println(" = " + glMapBufferRange);
        return glMapBufferRange;
    }
    
    @Override
    public void glMaterialf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glMaterialf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glMaterialf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glMaterialfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glMaterialfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glMaterialfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glMaterialx(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glMaterialx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glMaterialx(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glMaterialxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glMaterialxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glMaterialxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glMaterialxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glMaterialxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glMaterialxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glMatrixIndexPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glMatrixIndexPointerOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glMatrixIndexPointerOES(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glMatrixMode(final int n) {
        this.printIndent();
        this.print("glMatrixMode(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glMatrixMode(n);
        this.println("");
    }
    
    @Override
    public void glMultMatrixf(final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glMultMatrixf(<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glMultMatrixf(floatBuffer);
        this.println("");
    }
    
    @Override
    public void glMultMatrixf(final float[] array, final int n) {
        this.printIndent();
        this.print("glMultMatrixf(<[F>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glMultMatrixf(array, n);
        this.println("");
    }
    
    @Override
    public void glMultMatrixx(final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glMultMatrixx(<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glMultMatrixx(intBuffer);
        this.println("");
    }
    
    @Override
    public void glMultMatrixx(final int[] array, final int n) {
        this.printIndent();
        this.print("glMultMatrixx(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glMultMatrixx(array, n);
        this.println("");
    }
    
    @Override
    public void glMultiTexCoord4f(final int n, final float n2, final float n3, final float n4, final float n5) {
        this.printIndent();
        this.print("glMultiTexCoord4f(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ")");
        this.downstreamGLES1.glMultiTexCoord4f(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glMultiTexCoord4x(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glMultiTexCoord4x(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glMultiTexCoord4x(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glNormal3f(final float n, final float n2, final float n3) {
        this.printIndent();
        this.print("glNormal3f(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glNormal3f(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glNormal3x(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glNormal3x(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glNormal3x(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final long n3) {
        this.printIndent();
        this.print("glNormalPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<long> " + n3 + ")");
        this.downstreamGLES1.glNormalPointer(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final Buffer buffer) {
        this.printIndent();
        this.print("glNormalPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glNormalPointer(n, n2, buffer);
        this.println("");
    }
    
    @Override
    public void glNormalPointer(final GLArrayData glArrayData) {
        this.printIndent();
        this.print("glNormalPointer(<com.jogamp.opengl.GLArrayData> " + glArrayData + ")");
        this.downstreamGLES1.glNormalPointer(glArrayData);
        this.println("");
    }
    
    @Override
    public void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.printIndent();
        this.print("glOrtho(<double> " + n + ", " + "<double> " + n2 + ", " + "<double> " + n3 + ", " + "<double> " + n4 + ", " + "<double> " + n5 + ", " + "<double> " + n6 + ")");
        this.downstreamGLES1.glOrtho(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.printIndent();
        this.print("glOrthof(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ", " + "<float> " + n5 + ", " + "<float> " + n6 + ")");
        this.downstreamGLES1.glOrthof(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glOrthox(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glOrthox(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glOrthox(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glPixelStorei(final int n, final int n2) {
        this.printIndent();
        this.print("glPixelStorei(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glPixelStorei(n, n2);
        this.println("");
    }
    
    @Override
    public void glPointParameterf(final int n, final float n2) {
        this.printIndent();
        this.print("glPointParameterf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glPointParameterf(n, n2);
        this.println("");
    }
    
    @Override
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glPointParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glPointParameterfv(n, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        this.printIndent();
        this.print("glPointParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glPointParameterfv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glPointParameterx(final int n, final int n2) {
        this.printIndent();
        this.print("glPointParameterx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glPointParameterx(n, n2);
        this.println("");
    }
    
    @Override
    public void glPointParameterxv(final int n, final int[] array, final int n2) {
        this.printIndent();
        this.print("glPointParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glPointParameterxv(n, array, n2);
        this.println("");
    }
    
    @Override
    public void glPointParameterxv(final int n, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glPointParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glPointParameterxv(n, intBuffer);
        this.println("");
    }
    
    @Override
    public void glPointSize(final float n) {
        this.printIndent();
        this.print("glPointSize(<float> " + n + ")");
        this.downstreamGLES1.glPointSize(n);
        this.println("");
    }
    
    @Override
    public void glPointSizePointerOES(final int n, final int n2, final Buffer buffer) {
        this.printIndent();
        this.print("glPointSizePointerOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glPointSizePointerOES(n, n2, buffer);
        this.println("");
    }
    
    @Override
    public void glPointSizex(final int n) {
        this.printIndent();
        this.print("glPointSizex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glPointSizex(n);
        this.println("");
    }
    
    @Override
    public void glPolygonOffset(final float n, final float n2) {
        this.printIndent();
        this.print("glPolygonOffset(<float> " + n + ", " + "<float> " + n2 + ")");
        this.downstreamGLES1.glPolygonOffset(n, n2);
        this.println("");
    }
    
    @Override
    public void glPolygonOffsetx(final int n, final int n2) {
        this.printIndent();
        this.print("glPolygonOffsetx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        this.downstreamGLES1.glPolygonOffsetx(n, n2);
        this.println("");
    }
    
    @Override
    public void glPopMatrix() {
        this.printIndent();
        this.print("glPopMatrix()");
        this.downstreamGLES1.glPopMatrix();
        this.println("");
    }
    
    @Override
    public void glPushMatrix() {
        this.printIndent();
        this.print("glPushMatrix()");
        this.downstreamGLES1.glPushMatrix();
        this.println("");
    }
    
    @Override
    public int glQueryMatrixxOES(final int[] array, final int n, final int[] array2, final int n2) {
        this.printIndent();
        this.print("glQueryMatrixxOES(<[I>, <int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final int glQueryMatrixxOES = this.downstreamGLES1.glQueryMatrixxOES(array, n, array2, n2);
        this.println(" = " + glQueryMatrixxOES);
        return glQueryMatrixxOES;
    }
    
    @Override
    public int glQueryMatrixxOES(final IntBuffer intBuffer, final IntBuffer intBuffer2) {
        this.printIndent();
        this.print("glQueryMatrixxOES(<java.nio.IntBuffer> " + intBuffer + ", " + "<java.nio.IntBuffer> " + intBuffer2 + ")");
        final int glQueryMatrixxOES = this.downstreamGLES1.glQueryMatrixxOES(intBuffer, intBuffer2);
        this.println(" = " + glQueryMatrixxOES);
        return glQueryMatrixxOES;
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.printIndent();
        this.print("glReadPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<long> " + n7 + ")");
        this.downstreamGLES1.glReadPixels(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Buffer buffer) {
        this.printIndent();
        this.print("glReadPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        this.println("");
    }
    
    @Override
    public void glReadnPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final Buffer buffer) {
        this.printIndent();
        this.print("glReadnPixels(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glReadnPixels(n, n2, n3, n4, n5, n6, n7, buffer);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glRenderbufferStorage(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glRenderbufferStorage(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisample(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisample(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisampleEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisampleEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glRenderbufferStorageMultisampleEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glRenderbufferStorageMultisampleIMG(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glRenderbufferStorageMultisampleIMG(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glRenderbufferStorageMultisampleIMG(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glResolveMultisampleFramebuffer() {
        this.printIndent();
        this.print("glResolveMultisampleFramebuffer()");
        this.downstreamGLES1.glResolveMultisampleFramebuffer();
        this.println("");
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.printIndent();
        this.print("glRotatef(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ", " + "<float> " + n4 + ")");
        this.downstreamGLES1.glRotatef(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glRotatex(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glRotatex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glRotatex(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glSampleCoverage(final float n, final boolean b) {
        this.printIndent();
        this.print("glSampleCoverage(<float> " + n + ", " + "<boolean> " + b + ")");
        this.downstreamGLES1.glSampleCoverage(n, b);
        this.println("");
    }
    
    @Override
    public void glSampleCoveragex(final int n, final boolean b) {
        this.printIndent();
        this.print("glSampleCoveragex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<boolean> " + b + ")");
        this.downstreamGLES1.glSampleCoveragex(n, b);
        this.println("");
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        this.printIndent();
        this.print("glScalef(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glScalef(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glScalex(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glScalex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glScalex(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glScissor(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glScissor(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glShadeModel(final int n) {
        this.printIndent();
        this.print("glShadeModel(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glShadeModel(n);
        this.println("");
    }
    
    @Override
    public void glStartTilingQCOM(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glStartTilingQCOM(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glStartTilingQCOM(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glStencilFunc(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glStencilFunc(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glStencilFunc(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glStencilMask(final int n) {
        this.printIndent();
        this.print("glStencilMask(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        this.downstreamGLES1.glStencilMask(n);
        this.println("");
    }
    
    @Override
    public void glStencilOp(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glStencilOp(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glStencilOp(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glTexCoordPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glTexCoordPointer(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glTexCoordPointer(final GLArrayData glArrayData) {
        this.printIndent();
        this.print("glTexCoordPointer(<com.jogamp.opengl.GLArrayData> " + glArrayData + ")");
        this.downstreamGLES1.glTexCoordPointer(glArrayData);
        this.println("");
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        this.printIndent();
        this.print("glTexCoordPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ")");
        this.downstreamGLES1.glTexCoordPointer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glTexEnvf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glTexEnvf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glTexEnvf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glTexEnvfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glTexEnvfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glTexEnvfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexEnvfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexEnvi(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexEnvi(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexEnviv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexEnviv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexEnviv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexEnvx(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexEnvx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexEnvx(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexEnvxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexEnvxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexEnvxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexEnvxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexEnvxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexGenf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glTexGenf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glTexGenf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexGenfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glTexGenfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexGenfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexGenfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glTexGenfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glTexGenfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glTexGeni(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexGeni(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexGeni(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexGeniv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexGeniv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexGeniv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexGeniv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexGeniv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexGeniv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexGenx(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexGenx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexGenx(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexGenxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexGenxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexGenxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexGenxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexGenxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexGenxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES1.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glTexImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glTexParameterf(final int n, final int n2, final float n3) {
        this.printIndent();
        this.print("glTexParameterf(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glTexParameterf(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final float[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[F>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexParameterfv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.printIndent();
        this.print("glTexParameterfv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.FloatBuffer> " + floatBuffer + ")");
        this.downstreamGLES1.glTexParameterfv(n, n2, floatBuffer);
        this.println("");
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexParameteri(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexParameteri(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexParameteriv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexParameteriv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameteriv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexParameteriv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterx(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTexParameterx(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexParameterx(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final int[] array, final int n3) {
        this.printIndent();
        this.print("glTexParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<[I>" + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTexParameterxv(n, n2, array, n3);
        this.println("");
    }
    
    @Override
    public void glTexParameterxv(final int n, final int n2, final IntBuffer intBuffer) {
        this.printIndent();
        this.print("glTexParameterxv(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<java.nio.IntBuffer> " + intBuffer + ")");
        this.downstreamGLES1.glTexParameterxv(n, n2, intBuffer);
        this.println("");
    }
    
    @Override
    public void glTexStorage1D(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glTexStorage1D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glTexStorage1D(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glTexStorage2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glTexStorage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glTexStorage2D(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTexStorage3D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glTexStorage3D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glTexStorage3D(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        this.printIndent();
        this.print("glTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.println("");
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        this.printIndent();
        this.print("glTexSubImage2D(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n8).toUpperCase() + ", " + "<long> " + n9 + ")");
        this.downstreamGLES1.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
        this.println("");
    }
    
    @Override
    public void glTextureStorage1DEXT(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.printIndent();
        this.print("glTextureStorage1DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ")");
        this.downstreamGLES1.glTextureStorage1DEXT(n, n2, n3, n4, n5);
        this.println("");
    }
    
    @Override
    public void glTextureStorage2DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.printIndent();
        this.print("glTextureStorage2DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ")");
        this.downstreamGLES1.glTextureStorage2DEXT(n, n2, n3, n4, n5, n6);
        this.println("");
    }
    
    @Override
    public void glTextureStorage3DEXT(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        this.printIndent();
        this.print("glTextureStorage3DEXT(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n5).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n6).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n7).toUpperCase() + ")");
        this.downstreamGLES1.glTextureStorage3DEXT(n, n2, n3, n4, n5, n6, n7);
        this.println("");
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        this.printIndent();
        this.print("glTranslatef(<float> " + n + ", " + "<float> " + n2 + ", " + "<float> " + n3 + ")");
        this.downstreamGLES1.glTranslatef(n, n2, n3);
        this.println("");
    }
    
    @Override
    public void glTranslatex(final int n, final int n2, final int n3) {
        this.printIndent();
        this.print("glTranslatex(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ")");
        this.downstreamGLES1.glTranslatex(n, n2, n3);
        this.println("");
    }
    
    @Override
    public boolean glUnmapBuffer(final int n) {
        this.printIndent();
        this.print("glUnmapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ")");
        final boolean glUnmapBuffer = this.downstreamGLES1.glUnmapBuffer(n);
        this.println(" = " + glUnmapBuffer);
        return glUnmapBuffer;
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glVertexPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glVertexPointer(n, n2, n3, buffer);
        this.println("");
    }
    
    @Override
    public void glVertexPointer(final GLArrayData glArrayData) {
        this.printIndent();
        this.print("glVertexPointer(<com.jogamp.opengl.GLArrayData> " + glArrayData + ")");
        this.downstreamGLES1.glVertexPointer(glArrayData);
        this.println("");
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        this.printIndent();
        this.print("glVertexPointer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<long> " + n4 + ")");
        this.downstreamGLES1.glVertexPointer(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glViewport(final int n, final int n2, final int n3, final int n4) {
        this.printIndent();
        this.print("glViewport(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        this.downstreamGLES1.glViewport(n, n2, n3, n4);
        this.println("");
    }
    
    @Override
    public void glWeightPointerOES(final int n, final int n2, final int n3, final Buffer buffer) {
        this.printIndent();
        this.print("glWeightPointerOES(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n3).toUpperCase() + ", " + "<java.nio.Buffer> " + buffer + ")");
        this.downstreamGLES1.glWeightPointerOES(n, n2, n3, buffer);
        this.println("");
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
        this.printIndent();
        this.print("mapBuffer(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<int> 0x" + Integer.toHexString(n2).toUpperCase() + ")");
        final GLBufferStorage mapBuffer = this.downstreamGLES1.mapBuffer(n, n2);
        this.println(" = " + mapBuffer);
        return mapBuffer;
    }
    
    @Override
    public GLBufferStorage mapBufferRange(final int n, final long n2, final long n3, final int n4) {
        this.printIndent();
        this.print("mapBufferRange(<int> 0x" + Integer.toHexString(n).toUpperCase() + ", " + "<long> " + n2 + ", " + "<long> " + n3 + ", " + "<int> 0x" + Integer.toHexString(n4).toUpperCase() + ")");
        final GLBufferStorage mapBufferRange = this.downstreamGLES1.mapBufferRange(n, n2, n3, n4);
        this.println(" = " + mapBufferRange);
        return mapBufferRange;
    }
    
    @Override
    public void setSwapInterval(final int swapInterval) {
        this.downstreamGLES1.setSwapInterval(swapInterval);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TraceGLES1 [this 0x" + Integer.toHexString(this.hashCode()) + " implementing com.jogamp.opengl.GLES1,\n\t");
        sb.append(" downstream: " + this.downstreamGLES1.toString() + "\n\t]");
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
        DEBUG = Debug.debug("TraceGLES1");
    }
}
