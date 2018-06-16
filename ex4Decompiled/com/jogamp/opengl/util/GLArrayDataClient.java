// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import jogamp.opengl.util.glsl.GLSLArrayHandler;
import com.jogamp.opengl.GLException;
import java.nio.Buffer;
import jogamp.opengl.util.GLFixedArrayHandler;
import com.jogamp.opengl.util.glsl.ShaderState;
import jogamp.opengl.util.GLArrayHandler;

public class GLArrayDataClient extends GLArrayDataWrapper implements GLArrayDataEditable
{
    private boolean isValidated;
    protected boolean sealed;
    protected boolean bufferEnabled;
    protected boolean bufferWritten;
    protected boolean enableBufferAlways;
    protected int initialElementCount;
    protected GLArrayHandler glArrayHandler;
    protected boolean usesGLSL;
    protected ShaderState shaderState;
    
    public static GLArrayDataClient createFixed(final int n, final int n2, final int n3, final boolean b, final int n4) throws GLException {
        final GLArrayDataClient glArrayDataClient = new GLArrayDataClient();
        glArrayDataClient.init(null, n, n2, n3, b, 0, null, n4, 0, false, new GLFixedArrayHandler(glArrayDataClient), 0, 0L, 0, 0, false);
        return glArrayDataClient;
    }
    
    public static GLArrayDataClient createFixed(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer) throws GLException {
        final GLArrayDataClient glArrayDataClient = new GLArrayDataClient();
        glArrayDataClient.init(null, n, n2, n3, b, n4, buffer, n2 * n2, 0, false, new GLFixedArrayHandler(glArrayDataClient), 0, 0L, 0, 0, false);
        return glArrayDataClient;
    }
    
    public static GLArrayDataClient createGLSL(final String s, final int n, final int n2, final boolean b, final int n3) throws GLException {
        final GLArrayDataClient glArrayDataClient = new GLArrayDataClient();
        glArrayDataClient.init(s, -1, n, n2, b, 0, null, n3, 0, true, new GLSLArrayHandler(glArrayDataClient), 0, 0L, 0, 0, true);
        return glArrayDataClient;
    }
    
    public static GLArrayDataClient createGLSL(final String s, final int n, final int n2, final boolean b, final int n3, final Buffer buffer) throws GLException {
        final GLArrayDataClient glArrayDataClient = new GLArrayDataClient();
        glArrayDataClient.init(s, -1, n, n2, b, n3, buffer, n * n, 0, true, new GLSLArrayHandler(glArrayDataClient), 0, 0L, 0, 0, true);
        return glArrayDataClient;
    }
    
    @Override
    public void associate(final Object o, final boolean b) {
        if (o instanceof ShaderState) {
            if (b) {
                this.shaderState = (ShaderState)o;
            }
            else {
                this.shaderState = null;
            }
        }
    }
    
    @Override
    public final boolean isVBOWritten() {
        return this.bufferWritten;
    }
    
    @Override
    public final boolean sealed() {
        return this.sealed;
    }
    
    @Override
    public final boolean enabled() {
        return this.bufferEnabled;
    }
    
    @Override
    public final void setVBOWritten(final boolean b) {
        this.bufferWritten = (0 != this.mappedElementCount || b);
    }
    
    @Override
    public void destroy(final GL gl) {
        this.reset(gl);
        super.destroy(gl);
    }
    
    @Override
    public void reset(final GL gl) {
        this.enableBuffer(gl, false);
        this.reset();
    }
    
    @Override
    public void seal(final GL gl, final boolean b) {
        this.seal(b);
        this.enableBuffer(gl, b);
    }
    
    @Override
    public void enableBuffer(final GL gl, final boolean bufferEnabled) {
        if (this.enableBufferAlways || this.bufferEnabled != bufferEnabled) {
            if (bufferEnabled) {
                this.checkSeal(true);
                this.init_vbo(gl);
            }
            this.glArrayHandler.enableState(gl, bufferEnabled, this.usesGLSL ? this.shaderState : null);
            this.bufferEnabled = bufferEnabled;
        }
    }
    
    @Override
    public boolean bindBuffer(final GL gl, final boolean b) {
        if (b) {
            this.checkSeal(true);
            this.init_vbo(gl);
        }
        return this.glArrayHandler.bindBuffer(gl, b);
    }
    
    @Override
    public void setEnableAlways(final boolean enableBufferAlways) {
        this.enableBufferAlways = enableBufferAlways;
    }
    
    @Override
    public void reset() {
        if (this.buffer != null) {
            this.buffer.clear();
        }
        this.sealed = false;
        this.bufferEnabled = false;
        this.bufferWritten = (0 != this.mappedElementCount);
    }
    
    @Override
    public void seal(final boolean sealed) {
        if (this.sealed == sealed) {
            return;
        }
        this.sealed = sealed;
        this.bufferWritten = (0 != this.mappedElementCount);
        if (sealed) {
            if (null != this.buffer) {
                this.buffer.flip();
            }
        }
        else if (null != this.buffer) {
            this.buffer.position(this.buffer.limit());
            this.buffer.limit(this.buffer.capacity());
        }
    }
    
    @Override
    public void rewind() {
        if (this.buffer != null) {
            this.buffer.rewind();
        }
    }
    
    @Override
    public void padding(int i) {
        if (this.buffer == null || this.sealed) {
            return;
        }
        while (i < this.strideB) {
            Buffers.putb(this.buffer, (byte)0);
            ++i;
        }
    }
    
    @Override
    public void put(final Buffer buffer) {
        if (this.sealed) {
            return;
        }
        this.growBufferIfNecessary(buffer.remaining());
        Buffers.put(this.buffer, buffer);
    }
    
    @Override
    public void putb(final byte b) {
        if (this.sealed) {
            return;
        }
        this.growBufferIfNecessary(1);
        Buffers.putb(this.buffer, b);
    }
    
    @Override
    public void puts(final short n) {
        if (this.sealed) {
            return;
        }
        this.growBufferIfNecessary(1);
        Buffers.puts(this.buffer, n);
    }
    
    @Override
    public void puti(final int n) {
        if (this.sealed) {
            return;
        }
        this.growBufferIfNecessary(1);
        Buffers.puti(this.buffer, n);
    }
    
    @Override
    public void putx(final int n) {
        this.puti(n);
    }
    
    @Override
    public void putf(final float n) {
        if (this.sealed) {
            return;
        }
        this.growBufferIfNecessary(1);
        Buffers.putf(this.buffer, n);
    }
    
    @Override
    public String toString() {
        return "GLArrayDataClient[" + this.name + ", index " + this.index + ", location " + this.location + ", isVertexAttribute " + this.isVertexAttribute + ", usesGLSL " + this.usesGLSL + ", usesShaderState " + (null != this.shaderState) + ", dataType 0x" + Integer.toHexString(this.componentType) + ", bufferClazz " + this.componentClazz + ", elements " + this.getElementCount() + ", components " + this.componentsPerElement + ", stride " + this.strideB + "b " + this.strideL + "c" + ", mappedElementCount " + this.mappedElementCount + ", initialElementCount " + this.initialElementCount + ", sealed " + this.sealed + ", bufferEnabled " + this.bufferEnabled + ", bufferWritten " + this.bufferWritten + ", buffer " + this.buffer + ", alive " + this.alive + "]";
    }
    
    protected final boolean growBufferIfNecessary(final int n) {
        if (this.buffer != null && this.buffer.remaining() >= n) {
            return false;
        }
        if (0 != this.mappedElementCount) {
            throw new GLException("Mapped buffer can't grow. Insufficient storage size: Needed " + n + " components, " + "mappedElementCount " + this.mappedElementCount + ", has mapped buffer " + this.buffer + "; " + this);
        }
        this.growBuffer(Math.max(this.initialElementCount, (n + this.componentsPerElement - 1) / this.componentsPerElement));
        return true;
    }
    
    protected final void growBuffer(int n) {
        if (!this.alive || this.sealed) {
            throw new GLException("Invalid state: " + this);
        }
        n += n / this.componentsPerElement * (this.strideL - this.componentsPerElement);
        final int n2 = (this.buffer != null) ? this.buffer.capacity() : 0;
        final int n3 = n2 + n * this.componentsPerElement;
        final Buffer buffer = this.buffer;
        if (this.componentClazz == ByteBuffer.class) {
            final ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(n3);
            if (this.buffer != null) {
                this.buffer.flip();
                directByteBuffer.put((ByteBuffer)this.buffer);
            }
            this.buffer = directByteBuffer;
        }
        else if (this.componentClazz == ShortBuffer.class) {
            final ShortBuffer directShortBuffer = Buffers.newDirectShortBuffer(n3);
            if (this.buffer != null) {
                this.buffer.flip();
                directShortBuffer.put((ShortBuffer)this.buffer);
            }
            this.buffer = directShortBuffer;
        }
        else if (this.componentClazz == IntBuffer.class) {
            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(n3);
            if (this.buffer != null) {
                this.buffer.flip();
                directIntBuffer.put((IntBuffer)this.buffer);
            }
            this.buffer = directIntBuffer;
        }
        else {
            if (this.componentClazz != FloatBuffer.class) {
                throw new GLException("Given Buffer Class not supported: " + this.componentClazz + ":\n\t" + this);
            }
            final FloatBuffer directFloatBuffer = Buffers.newDirectFloatBuffer(n3);
            if (this.buffer != null) {
                this.buffer.flip();
                directFloatBuffer.put((FloatBuffer)this.buffer);
            }
            this.buffer = directFloatBuffer;
        }
        if (GLArrayDataClient.DEBUG) {
            System.err.println("*** Grow: comps: " + this.componentsPerElement + ", " + n2 / this.componentsPerElement + "/" + n2 + " -> " + n3 / this.componentsPerElement + "/" + n3 + "; " + buffer + " -> " + this.buffer + "; " + this);
        }
    }
    
    protected final void checkSeal(final boolean b) throws GLException {
        if (!this.alive) {
            throw new GLException("Invalid state: " + this);
        }
        if (this.sealed == b) {
            return;
        }
        if (b) {
            throw new GLException("Not Sealed yet, seal first:\n\t" + this);
        }
        throw new GLException("Already Sealed, can't modify VBO:\n\t" + this);
    }
    
    protected void init(final String s, final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer, final int initialElementCount, final int n5, final boolean b2, final GLArrayHandler glArrayHandler, final int n6, final long n7, final int n8, final int n9, final boolean usesGLSL) throws GLException {
        super.init(s, n, n2, n3, b, n4, buffer, n5, b2, n6, n7, n8, n9);
        if (0 < n5 && 0 < initialElementCount) {
            throw new IllegalArgumentException("mappedElementCount:=" + n5 + " specified, but passing non zero initialElementSize");
        }
        this.initialElementCount = initialElementCount;
        this.glArrayHandler = glArrayHandler;
        this.usesGLSL = usesGLSL;
        this.sealed = false;
        this.bufferEnabled = false;
        this.enableBufferAlways = false;
        this.bufferWritten = (0 != n5);
        if (null == this.buffer && initialElementCount > 0) {
            this.growBuffer(initialElementCount);
        }
    }
    
    protected void init_vbo(final GL gl) {
        if (!this.isValidated) {
            this.isValidated = true;
            this.validate(gl.getGLProfile(), true);
        }
    }
    
    protected GLArrayDataClient() {
        this.isValidated = false;
    }
    
    public GLArrayDataClient(final GLArrayDataClient glArrayDataClient) {
        super(glArrayDataClient);
        this.isValidated = false;
        this.isValidated = glArrayDataClient.isValidated;
        this.sealed = glArrayDataClient.sealed;
        this.bufferEnabled = glArrayDataClient.bufferEnabled;
        this.bufferWritten = glArrayDataClient.bufferWritten;
        this.enableBufferAlways = glArrayDataClient.enableBufferAlways;
        this.initialElementCount = glArrayDataClient.initialElementCount;
        if (null != glArrayDataClient.glArrayHandler) {
            final Class<? extends GLArrayHandler> class1 = glArrayDataClient.glArrayHandler.getClass();
            try {
                this.glArrayHandler = (GLArrayHandler)class1.getConstructor(GLArrayDataEditable.class).newInstance(this);
            }
            catch (Exception ex) {
                throw new RuntimeException("Could not ctor " + class1.getName() + "(" + this.getClass().getName() + ")", ex);
            }
        }
        else {
            this.glArrayHandler = null;
        }
        this.usesGLSL = glArrayDataClient.usesGLSL;
        this.shaderState = glArrayDataClient.shaderState;
    }
}
