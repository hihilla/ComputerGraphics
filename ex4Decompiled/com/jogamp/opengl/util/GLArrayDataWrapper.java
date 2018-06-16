// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import jogamp.opengl.Debug;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.fixedfunc.GLPointerFuncUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLException;
import java.nio.Buffer;
import com.jogamp.opengl.GLArrayData;

public class GLArrayDataWrapper implements GLArrayData
{
    public static final boolean DEBUG;
    protected boolean alive;
    protected int index;
    protected int location;
    protected String name;
    protected int componentsPerElement;
    protected int componentType;
    protected Class<?> componentClazz;
    protected int componentByteSize;
    protected boolean normalized;
    protected int strideB;
    protected int strideL;
    protected Buffer buffer;
    protected int mappedElementCount;
    protected boolean isVertexAttribute;
    protected long vboOffset;
    protected int vboName;
    protected boolean vboEnabled;
    protected int vboUsage;
    protected int vboTarget;
    
    public static GLArrayDataWrapper createFixed(final int n, final int n2, final int n3, final boolean b, final int n4, final Buffer buffer, final int n5, final long n6, final int n7, final int n8) throws GLException {
        final GLArrayDataWrapper glArrayDataWrapper = new GLArrayDataWrapper();
        glArrayDataWrapper.init(null, n, n2, n3, b, n4, buffer, 0, false, n5, n6, n7, n8);
        return glArrayDataWrapper;
    }
    
    public static GLArrayDataWrapper createFixed(final int n, final int n2, final int n3, final boolean b, final int n4, final int n5, final int n6, final long n7, final int n8, final int n9) throws GLException {
        final GLArrayDataWrapper glArrayDataWrapper = new GLArrayDataWrapper();
        glArrayDataWrapper.init(null, n, n2, n3, b, n4, null, n5, false, n6, n7, n8, n9);
        return glArrayDataWrapper;
    }
    
    public static GLArrayDataWrapper createGLSL(final String s, final int n, final int n2, final boolean b, final int n3, final Buffer buffer, final int n4, final long n5, final int n6, final int n7) throws GLException {
        final GLArrayDataWrapper glArrayDataWrapper = new GLArrayDataWrapper();
        glArrayDataWrapper.init(s, -1, n, n2, b, n3, buffer, 0, true, n4, n5, n6, n7);
        return glArrayDataWrapper;
    }
    
    public static GLArrayDataWrapper createGLSL(final String s, final int n, final int n2, final boolean b, final int n3, final int n4, final int n5, final long n6, final int n7, final int n8) throws GLException {
        final GLArrayDataWrapper glArrayDataWrapper = new GLArrayDataWrapper();
        glArrayDataWrapper.init(s, -1, n, n2, b, n3, null, n4, true, n5, n6, n7, n8);
        return glArrayDataWrapper;
    }
    
    public final boolean validate(final GLProfile glProfile, final boolean b) {
        if (!this.alive) {
            if (b) {
                throw new GLException("Instance !alive " + this);
            }
            return false;
        }
        else {
            if (!this.isVertexAttribute() || glProfile.hasGLSL()) {
                return glProfile.isValidArrayDataType(this.getIndex(), this.getComponentCount(), this.getComponentType(), this.isVertexAttribute(), b);
            }
            if (b) {
                throw new GLException("GLSL not supported on " + glProfile + ", " + this);
            }
            return false;
        }
    }
    
    @Override
    public void associate(final Object o, final boolean b) {
    }
    
    @Override
    public final boolean isVertexAttribute() {
        return this.isVertexAttribute;
    }
    
    @Override
    public final int getIndex() {
        return this.index;
    }
    
    @Override
    public final int getLocation() {
        return this.location;
    }
    
    @Override
    public final int setLocation(final int location) {
        return this.location = location;
    }
    
    @Override
    public final int setLocation(final GL2ES2 gl2ES2, final int n) {
        return this.location = gl2ES2.glGetAttribLocation(n, this.name);
    }
    
    @Override
    public final int setLocation(final GL2ES2 gl2ES2, final int n, final int location) {
        gl2ES2.glBindAttribLocation(n, this.location = location, this.name);
        return location;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    @Override
    public final long getVBOOffset() {
        return this.vboEnabled ? this.vboOffset : 0L;
    }
    
    @Override
    public final int getVBOName() {
        return this.vboEnabled ? this.vboName : 0;
    }
    
    @Override
    public final boolean isVBO() {
        return this.vboEnabled;
    }
    
    @Override
    public final int getVBOUsage() {
        return this.vboEnabled ? this.vboUsage : 0;
    }
    
    @Override
    public final int getVBOTarget() {
        return this.vboEnabled ? this.vboTarget : 0;
    }
    
    @Override
    public Buffer getBuffer() {
        return this.buffer;
    }
    
    @Override
    public final int getComponentCount() {
        return this.componentsPerElement;
    }
    
    @Override
    public final int getComponentType() {
        return this.componentType;
    }
    
    @Override
    public final int getComponentSizeInBytes() {
        return this.componentByteSize;
    }
    
    @Override
    public final int getElementCount() {
        if (0 != this.mappedElementCount) {
            return this.mappedElementCount;
        }
        if (null != this.buffer) {
            return ((0 == this.buffer.position()) ? this.buffer.limit() : this.buffer.position()) * this.componentByteSize / this.strideB;
        }
        return 0;
    }
    
    @Override
    public final int getSizeInBytes() {
        if (0 != this.mappedElementCount) {
            return this.mappedElementCount * this.componentsPerElement * this.componentByteSize;
        }
        if (null != this.buffer) {
            return (this.buffer.position() == 0) ? (this.buffer.limit() * this.componentByteSize) : (this.buffer.position() * this.componentByteSize);
        }
        return 0;
    }
    
    @Override
    public final boolean getNormalized() {
        return this.normalized;
    }
    
    @Override
    public final int getStride() {
        return this.strideB;
    }
    
    public final Class<?> getBufferClass() {
        return this.componentClazz;
    }
    
    @Override
    public void destroy(final GL gl) {
        this.buffer = null;
        this.vboName = 0;
        this.vboEnabled = false;
        this.vboOffset = 0L;
        this.alive = false;
    }
    
    @Override
    public String toString() {
        return "GLArrayDataWrapper[" + this.name + ", index " + this.index + ", location " + this.location + ", isVertexAttribute " + this.isVertexAttribute + ", dataType 0x" + Integer.toHexString(this.componentType) + ", bufferClazz " + this.componentClazz + ", elements " + this.getElementCount() + ", components " + this.componentsPerElement + ", stride " + this.strideB + "b " + this.strideL + "c" + ", mappedElementCount " + this.mappedElementCount + ", buffer " + this.buffer + ", vboEnabled " + this.vboEnabled + ", vboName " + this.vboName + ", vboUsage 0x" + Integer.toHexString(this.vboUsage) + ", vboTarget 0x" + Integer.toHexString(this.vboTarget) + ", vboOffset " + this.vboOffset + ", alive " + this.alive + "]";
    }
    
    public static final Class<?> getBufferClass(final int n) {
        switch (n) {
            case 5120:
            case 5121: {
                return ByteBuffer.class;
            }
            case 5122:
            case 5123: {
                return ShortBuffer.class;
            }
            case 5124:
            case 5125:
            case 5132: {
                return IntBuffer.class;
            }
            case 5126: {
                return FloatBuffer.class;
            }
            default: {
                throw new GLException("Given OpenGL data type not supported: " + n);
            }
        }
    }
    
    @Override
    public void setName(final String name) {
        this.location = -1;
        this.name = name;
    }
    
    public void setVBOEnabled(final boolean vboEnabled) {
        this.vboEnabled = vboEnabled;
    }
    
    public void setVBOName(final int vboName) {
        this.vboName = vboName;
        this.setVBOEnabled(0 != vboName);
    }
    
    public void setVBOUsage(final int vboUsage) {
        this.vboUsage = vboUsage;
    }
    
    public void setVBOTarget(final int vboTarget) {
        this.vboTarget = vboTarget;
    }
    
    protected void init(final String s, final int index, final int componentsPerElement, final int componentType, final boolean normalized, final int n, final Buffer buffer, final int mappedElementCount, final boolean isVertexAttribute, final int vboName, final long vboOffset, final int vboUsage, final int vboTarget) throws GLException {
        if (0 < mappedElementCount && null != buffer) {
            throw new IllegalArgumentException("mappedElementCount:=" + mappedElementCount + " specified, but passing non null buffer");
        }
        this.isVertexAttribute = isVertexAttribute;
        this.index = index;
        this.location = -1;
        if (34963 != vboTarget) {
            if ((0 == vboUsage && 0 == vboTarget) || 34962 == vboTarget) {
                this.name = ((null == s) ? GLPointerFuncUtil.getPredefinedArrayIndexName(index) : s);
                if (null == this.name) {
                    throw new GLException("Not a valid array buffer index: " + index);
                }
            }
            else if (0 < vboTarget) {
                throw new GLException("Invalid GPUBuffer target: 0x" + Integer.toHexString(vboTarget));
            }
        }
        this.componentType = componentType;
        this.componentClazz = getBufferClass(componentType);
        if (GLBuffers.isGLTypeFixedPoint(componentType)) {
            this.normalized = normalized;
        }
        else {
            this.normalized = false;
        }
        this.componentByteSize = GLBuffers.sizeOfGLType(componentType);
        if (0 > this.componentByteSize) {
            throw new GLException("Given componentType not supported: " + componentType + ":\n\t" + this);
        }
        if (0 >= componentsPerElement) {
            throw new GLException("Invalid number of components: " + componentsPerElement);
        }
        this.componentsPerElement = componentsPerElement;
        if (0 < n && n < componentsPerElement * this.componentByteSize) {
            throw new GLException("stride (" + n + ") lower than component bytes, " + componentsPerElement + " * " + this.componentByteSize);
        }
        if (0 < n && n % this.componentByteSize != 0) {
            throw new GLException("stride (" + n + ") not a multiple of bpc " + this.componentByteSize);
        }
        this.buffer = buffer;
        this.mappedElementCount = mappedElementCount;
        this.strideB = ((0 == n) ? (componentsPerElement * this.componentByteSize) : n);
        this.strideL = this.strideB / this.componentByteSize;
        this.vboName = vboName;
        this.vboEnabled = (0 != vboName);
        this.vboOffset = vboOffset;
        switch (vboUsage) {
            case 0:
            case 35040:
            case 35044:
            case 35048: {
                switch (vboTarget) {
                    case 0:
                    case 34962:
                    case 34963: {
                        this.vboUsage = vboUsage;
                        this.vboTarget = vboTarget;
                        this.alive = true;
                        return;
                    }
                    default: {
                        throw new GLException("invalid gpuBufferTarget: " + vboTarget + ":\n\t" + this);
                    }
                }
                break;
            }
            default: {
                throw new GLException("invalid gpuBufferUsage: " + vboUsage + ":\n\t" + this);
            }
        }
    }
    
    protected GLArrayDataWrapper() {
    }
    
    public GLArrayDataWrapper(final GLArrayDataWrapper glArrayDataWrapper) {
        this.alive = glArrayDataWrapper.alive;
        this.index = glArrayDataWrapper.index;
        this.location = glArrayDataWrapper.location;
        this.name = glArrayDataWrapper.name;
        this.componentsPerElement = glArrayDataWrapper.componentsPerElement;
        this.componentType = glArrayDataWrapper.componentType;
        this.componentClazz = glArrayDataWrapper.componentClazz;
        this.componentByteSize = glArrayDataWrapper.componentByteSize;
        this.normalized = glArrayDataWrapper.normalized;
        this.strideB = glArrayDataWrapper.strideB;
        this.strideL = glArrayDataWrapper.strideL;
        if (null != glArrayDataWrapper.buffer) {
            if (glArrayDataWrapper.buffer.position() == 0) {
                this.buffer = Buffers.slice(glArrayDataWrapper.buffer);
            }
            else {
                this.buffer = Buffers.slice(glArrayDataWrapper.buffer, 0, glArrayDataWrapper.buffer.limit());
            }
        }
        else {
            this.buffer = null;
        }
        this.mappedElementCount = glArrayDataWrapper.mappedElementCount;
        this.isVertexAttribute = glArrayDataWrapper.isVertexAttribute;
        this.vboOffset = glArrayDataWrapper.vboOffset;
        this.vboName = glArrayDataWrapper.vboName;
        this.vboEnabled = glArrayDataWrapper.vboEnabled;
        this.vboUsage = glArrayDataWrapper.vboUsage;
        this.vboTarget = glArrayDataWrapper.vboTarget;
    }
    
    static {
        DEBUG = Debug.debug("GLArrayData");
    }
}
