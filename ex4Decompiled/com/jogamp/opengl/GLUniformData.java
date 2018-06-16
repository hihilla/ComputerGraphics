// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.math.FloatUtil;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class GLUniformData
{
    private String name;
    private int location;
    private int rows;
    private int columns;
    private int count;
    private Object data;
    private boolean isMatrix;
    
    public GLUniformData(final String s, final int n) {
        this.initScalar(s, 1, n);
    }
    
    public GLUniformData(final String s, final float n) {
        this.initScalar(s, 1, n);
    }
    
    public GLUniformData(final String s, final int n, final IntBuffer intBuffer) {
        this.initBuffer(s, n, intBuffer);
    }
    
    public GLUniformData(final String s, final int n, final FloatBuffer floatBuffer) {
        this.initBuffer(s, n, floatBuffer);
    }
    
    private GLUniformData(final int n, final String s) {
        this.initBuffer(s, n, null);
    }
    
    public static GLUniformData creatEmptyVector(final String s, final int n) {
        return new GLUniformData(n, s);
    }
    
    public static GLUniformData creatEmptyMatrix(final String s, final int n, final int n2) {
        return new GLUniformData(s, n, n2, null);
    }
    
    public GLUniformData(final String s, final int n, final int n2, final FloatBuffer floatBuffer) {
        this.initBuffer(s, n, n2, floatBuffer);
    }
    
    public GLUniformData setData(final int n) {
        this.initScalar(n);
        return this;
    }
    
    public GLUniformData setData(final float n) {
        this.initScalar(n);
        return this;
    }
    
    public GLUniformData setData(final IntBuffer intBuffer) {
        this.initBuffer(intBuffer);
        return this;
    }
    
    public GLUniformData setData(final FloatBuffer floatBuffer) {
        this.initBuffer(floatBuffer);
        return this;
    }
    
    public int intValue() {
        return (int)this.data;
    }
    
    public float floatValue() {
        return (float)this.data;
    }
    
    public IntBuffer intBufferValue() {
        return (IntBuffer)this.data;
    }
    
    public FloatBuffer floatBufferValue() {
        return (FloatBuffer)this.data;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("GLUniformData[name ").append(this.name).append(", location ").append(this.location).append(", size ").append(this.rows).append("x").append(this.columns).append(", count ").append(this.count).append(", data ");
        if (this.isMatrix() && this.data instanceof FloatBuffer) {
            sb.append("\n");
            final FloatBuffer floatBuffer = (FloatBuffer)this.getBuffer();
            for (int i = 0; i < this.count; ++i) {
                FloatUtil.matrixToString(sb, i + ": ", "%10.5f", floatBuffer, i * this.rows * this.columns, this.rows, this.columns, false);
                sb.append(",\n");
            }
        }
        else if (this.isBuffer()) {
            Buffers.toString(sb, null, this.getBuffer());
        }
        else {
            sb.append(this.data);
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    private void initBuffer(final String name, final int rows, final int columns, final Buffer buffer) {
        if (2 > rows || rows > 4 || 2 > columns || columns > 4) {
            throw new GLException("rowsXcolumns must be within [2..4]X[2..4], is: " + rows + "X" + columns);
        }
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.isMatrix = true;
        this.location = -1;
        this.initBuffer(buffer);
    }
    
    private void initScalar(final String name, final int columns, final Object o) {
        if (1 > columns || columns > 4) {
            throw new GLException("components must be within [1..4], is: " + columns);
        }
        this.name = name;
        this.columns = columns;
        this.rows = 1;
        this.isMatrix = false;
        this.location = -1;
        this.initScalar(o);
    }
    
    private void initBuffer(final String name, final int columns, final Buffer buffer) {
        if (1 > columns || columns > 4) {
            throw new GLException("components must be within [1..4], is: " + columns);
        }
        this.name = name;
        this.columns = columns;
        this.rows = 1;
        this.isMatrix = false;
        this.location = -1;
        this.initBuffer(buffer);
    }
    
    private void initScalar(final Object o) {
        if (o instanceof Buffer) {
            this.initBuffer((Buffer)o);
        }
        else if (null != o) {
            if (this.isMatrix) {
                throw new GLException("Atom type not allowed for matrix : " + this);
            }
            this.count = 1;
            this.data = o;
        }
        else {
            this.count = 0;
            this.data = o;
        }
    }
    
    private void initBuffer(final Buffer data) {
        if (null != data) {
            final int n = this.rows * this.columns;
            if (data.remaining() < n || 0 != data.remaining() % n) {
                throw new GLException("remaining data buffer size invalid: buffer: " + data.toString() + "\n\t" + this);
            }
            this.count = data.remaining() / n;
            this.data = data;
        }
        else {
            this.count = 0;
            this.data = null;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLocation() {
        return this.location;
    }
    
    public int setLocation(final int location) {
        return this.location = location;
    }
    
    public int setLocation(final GL2ES2 gl2ES2, final int n) {
        return this.location = gl2ES2.glGetUniformLocation(n, this.name);
    }
    
    public Object getObject() {
        return this.data;
    }
    
    public Buffer getBuffer() {
        return (this.data instanceof Buffer) ? ((Buffer)this.data) : null;
    }
    
    public boolean isBuffer() {
        return this.data instanceof Buffer;
    }
    
    public boolean isMatrix() {
        return this.isMatrix;
    }
    
    public int count() {
        return this.count;
    }
    
    public int components() {
        return this.rows * this.columns;
    }
    
    public int rows() {
        return this.rows;
    }
    
    public int columns() {
        return this.columns;
    }
}
