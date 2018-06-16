// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;
import jogamp.opengl.util.glsl.GLSLArrayHandlerFlat;
import jogamp.opengl.util.glsl.GLSLArrayHandlerInterleaved;
import jogamp.opengl.util.GLArrayHandlerFlat;
import jogamp.opengl.util.GLFixedArrayHandlerFlat;
import com.jogamp.opengl.GLArrayData;
import jogamp.opengl.util.GLArrayHandlerInterleaved;
import jogamp.opengl.util.GLDataArrayHandler;
import jogamp.opengl.util.glsl.GLSLArrayHandler;
import com.jogamp.opengl.GLException;
import jogamp.opengl.util.GLArrayHandler;
import jogamp.opengl.util.GLFixedArrayHandler;
import java.nio.Buffer;
import com.jogamp.opengl.GLBufferStorage;

public class GLArrayDataServer extends GLArrayDataClient implements GLArrayDataEditable
{
    private int interleavedOffset;
    private GLBufferStorage mappedStorage;
    
    public static GLArrayDataServer createFixed(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer, final int n5) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(null, n, n2, n3, b, n4, buffer, buffer.limit(), 0, false, new GLFixedArrayHandler(glArrayDataServer), 0, 0L, n5, 34962, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createFixed(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(null, n, n2, n3, b, 0, null, n4, 0, false, new GLFixedArrayHandler(glArrayDataServer), 0, 0L, n5, 34962, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createGLSL(final String s, final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(s, -1, n, n2, b, 0, null, n3, 0, true, new GLSLArrayHandler(glArrayDataServer), 0, 0L, n4, 34962, true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createGLSLMapped(final String s, final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(s, -1, n, n2, b, 0, null, 0, n3, true, new GLSLArrayHandler(glArrayDataServer), 0, 0L, n4, 34962, true);
        glArrayDataServer.seal(true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createGLSL(final String s, final int n, final int n2, final boolean b, final int n3, final Buffer buffer, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(s, -1, n, n2, b, n3, buffer, buffer.limit(), 0, true, new GLSLArrayHandler(glArrayDataServer), 0, 0L, n4, 34962, true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createData(final int n, final int n2, final int n3, final Buffer buffer, final int n4, final int n5) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(null, -1, n, n2, false, n3, buffer, buffer.limit(), 0, false, new GLDataArrayHandler(glArrayDataServer), 0, 0L, n4, n5, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createData(final int n, final int n2, final int n3, final int n4, final int n5) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(null, -1, n, n2, false, 0, null, n3, 0, false, new GLDataArrayHandler(glArrayDataServer), 0, 0L, n4, n5, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createDataMapped(final int n, final int n2, final int n3, final int n4, final int n5) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init(null, -1, n, n2, false, 0, null, 0, n3, false, new GLDataArrayHandler(glArrayDataServer), 0, 0L, n4, n5, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createFixedInterleaved(final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, false, 0, null, n3, 0, false, new GLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, false);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createFixedInterleavedMapped(final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, false, 0, null, 0, n3, false, new GLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, false);
        glArrayDataServer.seal(true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createFixedInterleaved(final int n, final int n2, final boolean b, final int n3, final Buffer buffer, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, b, n3, buffer, buffer.limit(), 0, false, new GLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, false);
        return glArrayDataServer;
    }
    
    public GLArrayData addFixedSubArray(final int n, final int n2, final int n3) {
        if (this.interleavedOffset >= this.getComponentCount() * this.getComponentSizeInBytes()) {
            throw new GLException("Interleaved offset > total components (" + this.interleavedOffset / this.getComponentSizeInBytes() + " > " + this.getComponentCount() + ")");
        }
        if (this.usesGLSL) {
            throw new GLException("buffer uses GLSL");
        }
        final int n4 = (0 == this.getStride()) ? (this.getComponentCount() * this.getComponentSizeInBytes()) : this.getStride();
        GLArrayDataWrapper glArrayDataWrapper;
        if (0 < this.mappedElementCount) {
            glArrayDataWrapper = GLArrayDataWrapper.createFixed(n, n2, this.getComponentType(), this.getNormalized(), n4, this.mappedElementCount, this.getVBOName(), this.interleavedOffset, this.getVBOUsage(), n3);
        }
        else {
            glArrayDataWrapper = GLArrayDataWrapper.createFixed(n, n2, this.getComponentType(), this.getNormalized(), n4, this.getBuffer(), this.getVBOName(), this.interleavedOffset, this.getVBOUsage(), n3);
        }
        glArrayDataWrapper.setVBOEnabled(this.isVBO());
        this.interleavedOffset += n2 * this.getComponentSizeInBytes();
        if (34962 == n3) {
            this.glArrayHandler.addSubHandler(new GLFixedArrayHandlerFlat(glArrayDataWrapper));
        }
        return glArrayDataWrapper;
    }
    
    public static GLArrayDataServer createGLSLInterleaved(final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, b, 0, null, n3, 0, false, new GLSLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createGLSLInterleavedMapped(final int n, final int n2, final boolean b, final int n3, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, b, 0, null, 0, n3, false, new GLSLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, true);
        glArrayDataServer.seal(true);
        return glArrayDataServer;
    }
    
    public static GLArrayDataServer createGLSLInterleaved(final int n, final int n2, final boolean b, final int n3, final Buffer buffer, final int n4) throws GLException {
        final GLArrayDataServer glArrayDataServer = new GLArrayDataServer();
        glArrayDataServer.init("mgl_InterleaveArray", -1, n, n2, b, n3, buffer, buffer.limit(), 0, false, new GLSLArrayHandlerInterleaved(glArrayDataServer), 0, 0L, n4, 34962, true);
        return glArrayDataServer;
    }
    
    public GLArrayData addGLSLSubArray(final String s, final int n, final int n2) {
        if (this.interleavedOffset >= this.getComponentCount() * this.getComponentSizeInBytes()) {
            throw new GLException("Interleaved offset > total components (" + this.interleavedOffset / this.getComponentSizeInBytes() + " > " + this.getComponentCount() + ")");
        }
        if (!this.usesGLSL) {
            throw new GLException("buffer uses fixed function");
        }
        final int n3 = (0 == this.getStride()) ? (this.getComponentCount() * this.getComponentSizeInBytes()) : this.getStride();
        GLArrayDataWrapper glArrayDataWrapper;
        if (0 < this.mappedElementCount) {
            glArrayDataWrapper = GLArrayDataWrapper.createGLSL(s, n, this.getComponentType(), this.getNormalized(), n3, this.mappedElementCount, this.getVBOName(), this.interleavedOffset, this.getVBOUsage(), n2);
        }
        else {
            glArrayDataWrapper = GLArrayDataWrapper.createGLSL(s, n, this.getComponentType(), this.getNormalized(), n3, this.getBuffer(), this.getVBOName(), this.interleavedOffset, this.getVBOUsage(), n2);
        }
        glArrayDataWrapper.setVBOEnabled(this.isVBO());
        this.interleavedOffset += n * this.getComponentSizeInBytes();
        if (34962 == n2) {
            this.glArrayHandler.addSubHandler(new GLSLArrayHandlerFlat(glArrayDataWrapper));
        }
        return glArrayDataWrapper;
    }
    
    public final void setInterleavedOffset(final int interleavedOffset) {
        this.interleavedOffset = interleavedOffset;
    }
    
    public final int getInterleavedOffset() {
        return this.interleavedOffset;
    }
    
    @Override
    public void destroy(final GL gl) {
        final int vboName = this.vboName;
        super.destroy(gl);
        if (vboName != 0) {
            gl.glDeleteBuffers(1, new int[] { vboName }, 0);
            this.vboName = 0;
        }
    }
    
    @Override
    public void setVBOEnabled(final boolean vboEnabled) {
        this.checkSeal(false);
        super.setVBOEnabled(vboEnabled);
    }
    
    public GLBufferStorage mapStorage(final GL gl, final int n) {
        if (null != this.getBuffer()) {
            throw new IllegalStateException("user buffer not null");
        }
        if (null != this.mappedStorage) {
            throw new IllegalStateException("already mapped: " + this.mappedStorage);
        }
        this.checkSeal(true);
        this.bindBuffer(gl, true);
        gl.glBufferData(this.getVBOTarget(), this.getSizeInBytes(), null, this.getVBOUsage());
        final GLBufferStorage mapBuffer = gl.mapBuffer(this.getVBOTarget(), n);
        this.setMappedBuffer(mapBuffer);
        this.bindBuffer(gl, false);
        this.seal(false);
        this.rewind();
        return mapBuffer;
    }
    
    public GLBufferStorage mapStorage(final GL gl, final long n, final long n2, final int n3) {
        if (null != this.getBuffer()) {
            throw new IllegalStateException("user buffer not null");
        }
        if (null != this.mappedStorage) {
            throw new IllegalStateException("already mapped: " + this.mappedStorage);
        }
        this.checkSeal(true);
        this.bindBuffer(gl, true);
        gl.glBufferData(this.getVBOTarget(), this.getSizeInBytes(), null, this.getVBOUsage());
        final GLBufferStorage mapBufferRange = gl.mapBufferRange(this.getVBOTarget(), n, n2, n3);
        this.setMappedBuffer(mapBufferRange);
        this.bindBuffer(gl, false);
        this.seal(false);
        this.rewind();
        return mapBufferRange;
    }
    
    private final void setMappedBuffer(final GLBufferStorage mappedStorage) {
        this.mappedStorage = mappedStorage;
        final ByteBuffer mappedBuffer = mappedStorage.getMappedBuffer();
        if (this.componentClazz == ByteBuffer.class) {
            this.buffer = mappedBuffer;
        }
        else if (this.componentClazz == ShortBuffer.class) {
            this.buffer = mappedBuffer.asShortBuffer();
        }
        else if (this.componentClazz == IntBuffer.class) {
            this.buffer = mappedBuffer.asIntBuffer();
        }
        else {
            if (this.componentClazz != FloatBuffer.class) {
                throw new GLException("Given Buffer Class not supported: " + this.componentClazz + ":\n\t" + this);
            }
            this.buffer = mappedBuffer.asFloatBuffer();
        }
    }
    
    public void unmapStorage(final GL gl) {
        if (null == this.mappedStorage) {
            throw new IllegalStateException("not mapped");
        }
        this.mappedStorage = null;
        this.buffer = null;
        this.seal(true);
        this.bindBuffer(gl, true);
        gl.glUnmapBuffer(this.getVBOTarget());
        this.bindBuffer(gl, false);
    }
    
    @Override
    public String toString() {
        return "GLArrayDataServer[" + this.name + ", index " + this.index + ", location " + this.location + ", isVertexAttribute " + this.isVertexAttribute + ", usesGLSL " + this.usesGLSL + ", usesShaderState " + (null != this.shaderState) + ", dataType 0x" + Integer.toHexString(this.componentType) + ", bufferClazz " + this.componentClazz + ", elements " + this.getElementCount() + ", components " + this.componentsPerElement + ", stride " + this.strideB + "b " + this.strideL + "c" + ", initialElementCount " + this.initialElementCount + ", mappedElementCount " + this.mappedElementCount + ", mappedStorage " + this.mappedStorage + ", vboEnabled " + this.vboEnabled + ", vboName " + this.vboName + ", vboUsage 0x" + Integer.toHexString(this.vboUsage) + ", vboTarget 0x" + Integer.toHexString(this.vboTarget) + ", vboOffset " + this.vboOffset + ", sealed " + this.sealed + ", bufferEnabled " + this.bufferEnabled + ", bufferWritten " + this.bufferWritten + ", buffer " + this.buffer + ", alive " + this.alive + "]";
    }
    
    @Override
    protected void init(final String s, final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer, final int n5, final int n6, final boolean b2, final GLArrayHandler glArrayHandler, final int n7, final long n8, final int n9, final int n10, final boolean b3) throws GLException {
        super.init(s, n, n2, n3, b, n4, buffer, n5, n6, b2, glArrayHandler, n7, n8, n9, n10, b3);
        this.vboEnabled = true;
    }
    
    @Override
    protected void init_vbo(final GL gl) {
        super.init_vbo(gl);
        if (this.vboEnabled && this.vboName == 0) {
            final int[] array = { 0 };
            gl.glGenBuffers(1, array, 0);
            this.vboName = array[0];
            if (0 < this.interleavedOffset) {
                this.glArrayHandler.setSubArrayVBOName(this.vboName);
            }
        }
    }
    
    protected GLArrayDataServer() {
        this.interleavedOffset = 0;
        this.mappedStorage = null;
    }
    
    public GLArrayDataServer(final GLArrayDataServer glArrayDataServer) {
        super(glArrayDataServer);
        this.interleavedOffset = 0;
        this.mappedStorage = null;
        this.interleavedOffset = glArrayDataServer.interleavedOffset;
        this.mappedStorage = glArrayDataServer.mappedStorage;
    }
}
