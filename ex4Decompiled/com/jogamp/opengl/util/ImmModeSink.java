// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES1;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLException;
import com.jogamp.common.os.Platform;
import java.nio.ByteBuffer;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.nio.Buffer;
import java.util.Iterator;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.glsl.ShaderState;
import java.util.ArrayList;

public class ImmModeSink
{
    protected static final boolean DEBUG_BEGIN_END;
    protected static final boolean DEBUG_DRAW;
    protected static final boolean DEBUG_BUFFER;
    public static final int GL_QUADS = 7;
    public static final int GL_QUAD_STRIP = 8;
    public static final int GL_POLYGON = 9;
    private VBOSet vboSet;
    private final ArrayList<VBOSet> vboSetList;
    
    public static ImmModeSink createFixed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        return new ImmModeSink(n, n2, n3, n4, n5, n6, n7, n8, n9, false, n10, null, 0);
    }
    
    public static ImmModeSink createGLSL(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ShaderState shaderState) {
        return new ImmModeSink(n, n2, n3, n4, n5, n6, n7, n8, n9, true, n10, shaderState, 0);
    }
    
    public static ImmModeSink createGLSL(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11) {
        return new ImmModeSink(n, n2, n3, n4, n5, n6, n7, n8, n9, true, n10, null, n11);
    }
    
    public void destroy(final GL gl) {
        this.destroyList(gl);
        this.vboSet.destroy(gl);
    }
    
    public void reset() {
        this.reset(null);
    }
    
    public void reset(final GL gl) {
        this.destroyList(gl);
        this.vboSet.reset(gl);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImmModeSink[");
        sb.append(",\n\tVBO list: " + this.vboSetList.size() + " [");
        final Iterator<VBOSet> iterator = this.vboSetList.iterator();
        while (iterator.hasNext()) {
            sb.append("\n\t");
            sb.append(iterator.next());
        }
        if (this.vboSetList.size() > 0) {
            sb.append("\n\t],\nVBO current: NOP]");
        }
        else {
            sb.append("\n\t],\nVBO current: \n");
            sb.append(this.vboSet);
            sb.append("\n]");
        }
        return sb.toString();
    }
    
    public void draw(final GL gl, final boolean b) {
        if (ImmModeSink.DEBUG_DRAW) {
            System.err.println("ImmModeSink.draw(disableBufferAfterDraw: " + b + "):\n\t" + this);
        }
        for (int n = 0, i = 0; i < this.vboSetList.size(); ++i, ++n) {
            this.vboSetList.get(i).draw(gl, null, b, n);
        }
    }
    
    public void draw(final GL gl, final Buffer buffer, final boolean b) {
        if (ImmModeSink.DEBUG_DRAW) {
            System.err.println("ImmModeSink.draw(disableBufferAfterDraw: " + b + "):\n\t" + this);
        }
        for (int n = 0, i = 0; i < this.vboSetList.size(); ++i, ++n) {
            this.vboSetList.get(i).draw(gl, buffer, b, n);
        }
    }
    
    public void glBegin(int n) {
        this.vboSet.modeOrig = n;
        switch (n) {
            case 8: {
                n = 5;
                break;
            }
            case 9: {
                n = 6;
                break;
            }
        }
        this.vboSet.mode = n;
        if (ImmModeSink.DEBUG_BEGIN_END) {
            System.err.println("ImmModeSink.glBegin(" + this.vboSet.modeOrig + " -> " + this.vboSet.mode + ")");
        }
        this.vboSet.checkSeal(false);
    }
    
    public final void glEnd(final GL gl) {
        this.glEnd(gl, null, true);
    }
    
    public void glEnd(final GL gl, final boolean b) {
        this.glEnd(gl, null, b);
    }
    
    public final void glEnd(final GL gl, final Buffer buffer) {
        this.glEnd(gl, buffer, true);
    }
    
    private void glEnd(final GL gl, final Buffer buffer, final boolean b) {
        if (ImmModeSink.DEBUG_BEGIN_END) {
            System.err.println("ImmModeSink START glEnd(immediate: " + b + ")");
        }
        if (b) {
            this.vboSet.seal(gl, true);
            this.vboSet.draw(gl, buffer, true, -1);
            this.reset(gl);
        }
        else {
            this.vboSet.seal(gl, true);
            this.vboSet.enableBuffer(gl, false);
            this.vboSetList.add(this.vboSet);
            this.vboSet = this.vboSet.regenerate(gl);
        }
        if (ImmModeSink.DEBUG_BEGIN_END) {
            System.err.println("ImmModeSink END glEnd(immediate: " + b + ")");
        }
    }
    
    public void glVertexv(final Buffer buffer) {
        this.vboSet.glVertexv(buffer);
    }
    
    public void glNormalv(final Buffer buffer) {
        this.vboSet.glNormalv(buffer);
    }
    
    public void glColorv(final Buffer buffer) {
        this.vboSet.glColorv(buffer);
    }
    
    public void glTexCoordv(final Buffer buffer) {
        this.vboSet.glTexCoordv(buffer);
    }
    
    public final void glVertex2f(final float n, final float n2) {
        this.vboSet.glVertex2f(n, n2);
    }
    
    public final void glVertex3f(final float n, final float n2, final float n3) {
        this.vboSet.glVertex3f(n, n2, n3);
    }
    
    public final void glNormal3f(final float n, final float n2, final float n3) {
        this.vboSet.glNormal3f(n, n2, n3);
    }
    
    public final void glColor3f(final float n, final float n2, final float n3) {
        this.vboSet.glColor3f(n, n2, n3);
    }
    
    public final void glColor4f(final float n, final float n2, final float n3, final float n4) {
        this.vboSet.glColor4f(n, n2, n3, n4);
    }
    
    public final void glTexCoord2f(final float n, final float n2) {
        this.vboSet.glTexCoord2f(n, n2);
    }
    
    public final void glTexCoord3f(final float n, final float n2, final float n3) {
        this.vboSet.glTexCoord3f(n, n2, n3);
    }
    
    public final void glVertex2s(final short n, final short n2) {
        this.vboSet.glVertex2s(n, n2);
    }
    
    public final void glVertex3s(final short n, final short n2, final short n3) {
        this.vboSet.glVertex3s(n, n2, n3);
    }
    
    public final void glNormal3s(final short n, final short n2, final short n3) {
        this.vboSet.glNormal3s(n, n2, n3);
    }
    
    public final void glColor3s(final short n, final short n2, final short n3) {
        this.vboSet.glColor3s(n, n2, n3);
    }
    
    public final void glColor4s(final short n, final short n2, final short n3, final short n4) {
        this.vboSet.glColor4s(n, n2, n3, n4);
    }
    
    public final void glTexCoord2s(final short n, final short n2) {
        this.vboSet.glTexCoord2s(n, n2);
    }
    
    public final void glTexCoord3s(final short n, final short n2, final short n3) {
        this.vboSet.glTexCoord3s(n, n2, n3);
    }
    
    public final void glVertex2b(final byte b, final byte b2) {
        this.vboSet.glVertex2b(b, b2);
    }
    
    public final void glVertex3b(final byte b, final byte b2, final byte b3) {
        this.vboSet.glVertex3b(b, b2, b3);
    }
    
    public final void glNormal3b(final byte b, final byte b2, final byte b3) {
        this.vboSet.glNormal3b(b, b2, b3);
    }
    
    public final void glColor3b(final byte b, final byte b2, final byte b3) {
        this.vboSet.glColor3b(b, b2, b3);
    }
    
    public final void glColor3ub(final byte b, final byte b2, final byte b3) {
        this.vboSet.glColor3ub(b, b2, b3);
    }
    
    public final void glColor4b(final byte b, final byte b2, final byte b3, final byte b4) {
        this.vboSet.glColor4b(b, b2, b3, b4);
    }
    
    public final void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        this.vboSet.glColor4ub(b, b2, b3, b4);
    }
    
    public final void glTexCoord2b(final byte b, final byte b2) {
        this.vboSet.glTexCoord2b(b, b2);
    }
    
    public final void glTexCoord3b(final byte b, final byte b2, final byte b3) {
        this.vboSet.glTexCoord3b(b, b2, b3);
    }
    
    protected ImmModeSink(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final boolean b, final int n10, final ShaderState shaderState, final int n11) {
        this.vboSet = new VBOSet(n, n2, n3, n4, n5, n6, n7, n8, n9, b, n10, shaderState, n11);
        this.vboSetList = new ArrayList<VBOSet>();
    }
    
    public boolean getUseVBO() {
        return this.vboSet.getUseVBO();
    }
    
    public int getResizeElementCount() {
        return this.vboSet.getResizeElementCount();
    }
    
    public void setResizeElementCount(final int resizeElementCount) {
        this.vboSet.setResizeElementCount(resizeElementCount);
    }
    
    private void destroyList(final GL gl) {
        for (int i = 0; i < this.vboSetList.size(); ++i) {
            this.vboSetList.get(i).destroy(gl);
        }
        this.vboSetList.clear();
    }
    
    static {
        Debug.initSingleton();
        DEBUG_BEGIN_END = PropertyAccess.isPropertyDefined("jogl.debug.ImmModeSink.BeginEnd", true);
        DEBUG_DRAW = PropertyAccess.isPropertyDefined("jogl.debug.ImmModeSink.Draw", true);
        DEBUG_BUFFER = PropertyAccess.isPropertyDefined("jogl.debug.ImmModeSink.Buffer", true);
    }
    
    protected static class VBOSet
    {
        private boolean usingShaderProgram;
        private final int glBufferUsage;
        private final int initialElementCount;
        private final boolean useVBO;
        private final boolean useGLSL;
        private final ShaderState shaderState;
        private int shaderProgram;
        private int mode;
        private int modeOrig;
        private int resizeElementCount;
        private ByteBuffer buffer;
        private int vboName;
        private static final int VERTEX = 0;
        private static final int COLOR = 1;
        private static final int NORMAL = 2;
        private static final int TEXTCOORD = 3;
        private int vCount;
        private int cCount;
        private int nCount;
        private int tCount;
        private int vOffset;
        private int cOffset;
        private int nOffset;
        private int tOffset;
        private int vElems;
        private int cElems;
        private int nElems;
        private int tElems;
        private final int vComps;
        private final int cComps;
        private final int nComps;
        private final int tComps;
        private final int vCompsBytes;
        private final int cCompsBytes;
        private final int nCompsBytes;
        private final int tCompsBytes;
        private final int vDataType;
        private final int cDataType;
        private final int nDataType;
        private final int tDataType;
        private final boolean vDataTypeSigned;
        private final boolean cDataTypeSigned;
        private final boolean nDataTypeSigned;
        private final boolean tDataTypeSigned;
        private final int pageSize;
        private Buffer vertexArray;
        private Buffer colorArray;
        private Buffer normalArray;
        private Buffer textCoordArray;
        private GLArrayDataWrapper vArrayData;
        private GLArrayDataWrapper cArrayData;
        private GLArrayDataWrapper nArrayData;
        private GLArrayDataWrapper tArrayData;
        private boolean sealed;
        private boolean sealedGL;
        private boolean bufferEnabled;
        private boolean bufferWritten;
        private boolean bufferWrittenOnce;
        private boolean glslLocationSet;
        
        protected VBOSet(final int n, final int vComps, final int vDataType, final int cComps, final int cDataType, final int nComps, final int nDataType, final int tComps, final int tDataType, final boolean useGLSL, final int glBufferUsage, final ShaderState shaderState, final int shaderProgram) {
            this.usingShaderProgram = false;
            this.glBufferUsage = glBufferUsage;
            this.initialElementCount = n;
            this.useVBO = (0 != glBufferUsage);
            this.useGLSL = useGLSL;
            this.shaderState = shaderState;
            this.shaderProgram = shaderProgram;
            if (useGLSL && null == this.shaderState && 0 == shaderProgram) {
                throw new IllegalArgumentException("Using GLSL but neither a valid shader-program nor ShaderState has been passed!");
            }
            this.resizeElementCount = n;
            this.vDataType = vDataType;
            this.vDataTypeSigned = GLBuffers.isSignedGLType(vDataType);
            this.vComps = vComps;
            this.vCompsBytes = vComps * GLBuffers.sizeOfGLType(vDataType);
            this.cDataType = cDataType;
            this.cDataTypeSigned = GLBuffers.isSignedGLType(cDataType);
            this.cComps = cComps;
            this.cCompsBytes = cComps * GLBuffers.sizeOfGLType(cDataType);
            this.nDataType = nDataType;
            this.nDataTypeSigned = GLBuffers.isSignedGLType(nDataType);
            this.nComps = nComps;
            this.nCompsBytes = nComps * GLBuffers.sizeOfGLType(nDataType);
            this.tDataType = tDataType;
            this.tDataTypeSigned = GLBuffers.isSignedGLType(tDataType);
            this.tComps = tComps;
            this.tCompsBytes = tComps * GLBuffers.sizeOfGLType(tDataType);
            this.vboName = 0;
            this.vCount = 0;
            this.cCount = 0;
            this.nCount = 0;
            this.tCount = 0;
            this.vElems = 0;
            this.cElems = 0;
            this.nElems = 0;
            this.tElems = 0;
            this.pageSize = Platform.getMachineDataInfo().pageSizeInBytes();
            this.reallocateBuffer(n);
            this.rewind();
            this.sealed = false;
            this.sealedGL = false;
            this.mode = 0;
            this.modeOrig = 0;
            this.bufferEnabled = false;
            this.bufferWritten = false;
            this.bufferWrittenOnce = false;
            this.glslLocationSet = false;
        }
        
        protected int getResizeElementCount() {
            return this.resizeElementCount;
        }
        
        protected void setResizeElementCount(final int resizeElementCount) {
            this.resizeElementCount = resizeElementCount;
        }
        
        protected boolean getUseVBO() {
            return this.useVBO;
        }
        
        protected final VBOSet regenerate(final GL gl) {
            return new VBOSet(this.initialElementCount, this.vComps, this.vDataType, this.cComps, this.cDataType, this.nComps, this.nDataType, this.tComps, this.tDataType, this.useGLSL, this.glBufferUsage, this.shaderState, this.shaderProgram);
        }
        
        protected void checkSeal(final boolean b) throws GLException {
            if (0 == this.mode) {
                throw new GLException("No mode set yet, call glBegin(mode) first:\n\t" + this);
            }
            if (this.sealed == b) {
                return;
            }
            if (b) {
                throw new GLException("Not Sealed yet, call glEnd() first:\n\t" + this);
            }
            throw new GLException("Already Sealed, can't modify VBO after glEnd():\n\t" + this);
        }
        
        protected void useShaderProgram(final GL2ES2 gl2ES2, final boolean b) {
            if (b || !this.usingShaderProgram) {
                if (null != this.shaderState) {
                    this.shaderState.useProgram(gl2ES2, true);
                }
                else {
                    gl2ES2.glUseProgram(this.shaderProgram);
                }
                this.usingShaderProgram = true;
            }
        }
        
        protected void draw(final GL gl, final Buffer buffer, final boolean b, final int n) {
            this.enableBuffer(gl, true);
            if (null != this.shaderState || 0 != this.shaderProgram) {
                this.useShaderProgram(gl.getGL2ES2(), false);
            }
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.draw[" + n + "].0 (disableBufferAfterDraw: " + b + "):\n\t" + this);
            }
            if (this.buffer != null) {
                if (null == buffer) {
                    if (7 == this.mode && !gl.isGL2()) {
                        for (int i = 0; i < this.vElems - 3; i += 4) {
                            gl.glDrawArrays(6, i, 4);
                        }
                    }
                    else {
                        gl.glDrawArrays(this.mode, 0, this.vElems);
                    }
                }
                else {
                    if (!gl.getContext().isCPUDataSourcingAvail()) {
                        throw new GLException("CPU data sourcing n/a w/ " + gl.getContext());
                    }
                    int n2;
                    if (buffer instanceof ByteBuffer) {
                        n2 = 5121;
                    }
                    else if (buffer instanceof ShortBuffer) {
                        n2 = 5123;
                    }
                    else {
                        if (!(buffer instanceof IntBuffer)) {
                            throw new GLException("Given Buffer Class not supported: " + buffer.getClass() + ", should be ubyte, ushort or uint:\n\t" + this);
                        }
                        n2 = 5125;
                    }
                    final int remaining = buffer.remaining();
                    final int position = buffer.position();
                    if (7 == this.mode && !gl.isGL2()) {
                        if (5121 == n2) {
                            final ByteBuffer byteBuffer = (ByteBuffer)buffer;
                            for (int j = 0; j < remaining; ++j) {
                                gl.glDrawArrays(6, 0xFF & byteBuffer.get(position + j), 4);
                            }
                        }
                        else if (5123 == n2) {
                            final ShortBuffer shortBuffer = (ShortBuffer)buffer;
                            for (int k = 0; k < remaining; ++k) {
                                gl.glDrawArrays(6, 0xFFFF & shortBuffer.get(position + k), 4);
                            }
                        }
                        else {
                            final IntBuffer intBuffer = (IntBuffer)buffer;
                            for (int l = 0; l < remaining; ++l) {
                                gl.glDrawArrays(6, -1 & intBuffer.get(position + l), 4);
                            }
                        }
                    }
                    else {
                        ((GL2ES1)gl).glDrawElements(this.mode, remaining, n2, buffer);
                    }
                }
            }
            if (b) {
                this.enableBuffer(gl, false);
            }
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.draw[" + n + "].X (disableBufferAfterDraw: " + b + ")");
            }
        }
        
        public void glVertexv(final Buffer buffer) {
            this.checkSeal(false);
            Buffers.put(this.vertexArray, buffer);
        }
        
        public void glNormalv(final Buffer buffer) {
            this.checkSeal(false);
            Buffers.put(this.normalArray, buffer);
        }
        
        public void glColorv(final Buffer buffer) {
            this.checkSeal(false);
            Buffers.put(this.colorArray, buffer);
        }
        
        public void glTexCoordv(final Buffer buffer) {
            this.checkSeal(false);
            Buffers.put(this.textCoordArray, buffer);
        }
        
        public void glVertex2b(final byte b, final byte b2) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNb(this.vertexArray, this.vDataTypeSigned, b, true);
            }
            if (this.vComps > 1) {
                Buffers.putNb(this.vertexArray, this.vDataTypeSigned, b2, true);
            }
            this.countAndPadding(0, this.vComps - 2);
        }
        
        public void glVertex3b(final byte b, final byte b2, final byte b3) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNb(this.vertexArray, this.vDataTypeSigned, b, true);
            }
            if (this.vComps > 1) {
                Buffers.putNb(this.vertexArray, this.vDataTypeSigned, b2, true);
            }
            if (this.vComps > 2) {
                Buffers.putNb(this.vertexArray, this.vDataTypeSigned, b3, true);
            }
            this.countAndPadding(0, this.vComps - 3);
        }
        
        public void glVertex2s(final short n, final short n2) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNs(this.vertexArray, this.vDataTypeSigned, n, true);
            }
            if (this.vComps > 1) {
                Buffers.putNs(this.vertexArray, this.vDataTypeSigned, n2, true);
            }
            this.countAndPadding(0, this.vComps - 2);
        }
        
        public void glVertex3s(final short n, final short n2, final short n3) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNs(this.vertexArray, this.vDataTypeSigned, n, true);
            }
            if (this.vComps > 1) {
                Buffers.putNs(this.vertexArray, this.vDataTypeSigned, n2, true);
            }
            if (this.vComps > 2) {
                Buffers.putNs(this.vertexArray, this.vDataTypeSigned, n3, true);
            }
            this.countAndPadding(0, this.vComps - 3);
        }
        
        public void glVertex2f(final float n, final float n2) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNf(this.vertexArray, this.vDataTypeSigned, n);
            }
            if (this.vComps > 1) {
                Buffers.putNf(this.vertexArray, this.vDataTypeSigned, n2);
            }
            this.countAndPadding(0, this.vComps - 2);
        }
        
        public void glVertex3f(final float n, final float n2, final float n3) {
            this.checkSeal(false);
            this.growBuffer(0);
            if (this.vComps > 0) {
                Buffers.putNf(this.vertexArray, this.vDataTypeSigned, n);
            }
            if (this.vComps > 1) {
                Buffers.putNf(this.vertexArray, this.vDataTypeSigned, n2);
            }
            if (this.vComps > 2) {
                Buffers.putNf(this.vertexArray, this.vDataTypeSigned, n3);
            }
            this.countAndPadding(0, this.vComps - 3);
        }
        
        public void glNormal3b(final byte b, final byte b2, final byte b3) {
            this.checkSeal(false);
            this.growBuffer(2);
            if (this.nComps > 0) {
                Buffers.putNb(this.normalArray, this.nDataTypeSigned, b, true);
            }
            if (this.nComps > 1) {
                Buffers.putNb(this.normalArray, this.nDataTypeSigned, b2, true);
            }
            if (this.nComps > 2) {
                Buffers.putNb(this.normalArray, this.nDataTypeSigned, b3, true);
            }
            this.countAndPadding(2, this.nComps - 3);
        }
        
        public void glNormal3s(final short n, final short n2, final short n3) {
            this.checkSeal(false);
            this.growBuffer(2);
            if (this.nComps > 0) {
                Buffers.putNs(this.normalArray, this.nDataTypeSigned, n, true);
            }
            if (this.nComps > 1) {
                Buffers.putNs(this.normalArray, this.nDataTypeSigned, n2, true);
            }
            if (this.nComps > 2) {
                Buffers.putNs(this.normalArray, this.nDataTypeSigned, n3, true);
            }
            this.countAndPadding(2, this.nComps - 3);
        }
        
        public void glNormal3f(final float n, final float n2, final float n3) {
            this.checkSeal(false);
            this.growBuffer(2);
            if (this.nComps > 0) {
                Buffers.putNf(this.normalArray, this.nDataTypeSigned, n);
            }
            if (this.nComps > 1) {
                Buffers.putNf(this.normalArray, this.nDataTypeSigned, n2);
            }
            if (this.nComps > 2) {
                Buffers.putNf(this.normalArray, this.nDataTypeSigned, n3);
            }
            this.countAndPadding(2, this.nComps - 3);
        }
        
        public void glColor3b(final byte b, final byte b2, final byte b3) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b, true);
            }
            if (this.cComps > 1) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b2, true);
            }
            if (this.cComps > 2) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b3, true);
            }
            this.countAndPadding(1, this.cComps - 3);
        }
        
        public void glColor3ub(final byte b, final byte b2, final byte b3) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b, false);
            }
            if (this.cComps > 1) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b2, false);
            }
            if (this.cComps > 2) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b3, false);
            }
            this.countAndPadding(1, this.cComps - 3);
        }
        
        public void glColor4b(final byte b, final byte b2, final byte b3, final byte b4) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b, true);
            }
            if (this.cComps > 1) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b2, true);
            }
            if (this.cComps > 2) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b3, true);
            }
            if (this.cComps > 3) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b4, true);
            }
            this.countAndPadding(1, this.cComps - 4);
        }
        
        public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b, false);
            }
            if (this.cComps > 1) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b2, false);
            }
            if (this.cComps > 2) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b3, false);
            }
            if (this.cComps > 3) {
                Buffers.putNb(this.colorArray, this.cDataTypeSigned, b4, false);
            }
            this.countAndPadding(1, this.cComps - 4);
        }
        
        public void glColor3s(final short n, final short n2, final short n3) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n, true);
            }
            if (this.cComps > 1) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n2, true);
            }
            if (this.cComps > 2) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n3, true);
            }
            this.countAndPadding(1, this.cComps - 3);
        }
        
        public void glColor4s(final short n, final short n2, final short n3, final short n4) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n, true);
            }
            if (this.cComps > 1) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n2, true);
            }
            if (this.cComps > 2) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n3, true);
            }
            if (this.cComps > 3) {
                Buffers.putNs(this.colorArray, this.cDataTypeSigned, n4, true);
            }
            this.countAndPadding(1, this.cComps - 4);
        }
        
        public void glColor3f(final float n, final float n2, final float n3) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n);
            }
            if (this.cComps > 1) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n2);
            }
            if (this.cComps > 2) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n3);
            }
            this.countAndPadding(1, this.cComps - 3);
        }
        
        public void glColor4f(final float n, final float n2, final float n3, final float n4) {
            this.checkSeal(false);
            this.growBuffer(1);
            if (this.cComps > 0) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n);
            }
            if (this.cComps > 1) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n2);
            }
            if (this.cComps > 2) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n3);
            }
            if (this.cComps > 3) {
                Buffers.putNf(this.colorArray, this.cDataTypeSigned, n4);
            }
            this.countAndPadding(1, this.cComps - 4);
        }
        
        public void glTexCoord2b(final byte b, final byte b2) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNb(this.textCoordArray, this.tDataTypeSigned, b, true);
            }
            if (this.tComps > 1) {
                Buffers.putNb(this.textCoordArray, this.tDataTypeSigned, b2, true);
            }
            this.countAndPadding(3, this.tComps - 2);
        }
        
        public void glTexCoord3b(final byte b, final byte b2, final byte b3) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNb(this.textCoordArray, this.tDataTypeSigned, b, true);
            }
            if (this.tComps > 1) {
                Buffers.putNb(this.textCoordArray, this.tDataTypeSigned, b2, true);
            }
            if (this.tComps > 2) {
                Buffers.putNb(this.textCoordArray, this.tDataTypeSigned, b3, true);
            }
            this.countAndPadding(3, this.tComps - 3);
        }
        
        public void glTexCoord2s(final short n, final short n2) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNs(this.textCoordArray, this.tDataTypeSigned, n, true);
            }
            if (this.tComps > 1) {
                Buffers.putNs(this.textCoordArray, this.tDataTypeSigned, n2, true);
            }
            this.countAndPadding(3, this.tComps - 2);
        }
        
        public void glTexCoord3s(final short n, final short n2, final short n3) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNs(this.textCoordArray, this.tDataTypeSigned, n, true);
            }
            if (this.tComps > 1) {
                Buffers.putNs(this.textCoordArray, this.tDataTypeSigned, n2, true);
            }
            if (this.tComps > 2) {
                Buffers.putNs(this.textCoordArray, this.tDataTypeSigned, n3, true);
            }
            this.countAndPadding(3, this.tComps - 3);
        }
        
        public void glTexCoord2f(final float n, final float n2) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNf(this.textCoordArray, this.tDataTypeSigned, n);
            }
            if (this.tComps > 1) {
                Buffers.putNf(this.textCoordArray, this.tDataTypeSigned, n2);
            }
            this.countAndPadding(3, this.tComps - 2);
        }
        
        public void glTexCoord3f(final float n, final float n2, final float n3) {
            this.checkSeal(false);
            this.growBuffer(3);
            if (this.tComps > 0) {
                Buffers.putNf(this.textCoordArray, this.tDataTypeSigned, n);
            }
            if (this.tComps > 1) {
                Buffers.putNf(this.textCoordArray, this.tDataTypeSigned, n2);
            }
            if (this.tComps > 2) {
                Buffers.putNf(this.textCoordArray, this.tDataTypeSigned, n3);
            }
            this.countAndPadding(3, this.tComps - 3);
        }
        
        public void rewind() {
            if (null != this.vertexArray) {
                this.vertexArray.rewind();
            }
            if (null != this.colorArray) {
                this.colorArray.rewind();
            }
            if (null != this.normalArray) {
                this.normalArray.rewind();
            }
            if (null != this.textCoordArray) {
                this.textCoordArray.rewind();
            }
        }
        
        public void setShaderProgram(final int shaderProgram) {
            if (null == this.shaderState && 0 == shaderProgram) {
                throw new IllegalArgumentException("Not allowed to zero shader program if no ShaderState is set");
            }
            this.shaderProgram = shaderProgram;
            this.glslLocationSet = false;
        }
        
        private boolean resetGLSLArrayLocation(final GL2ES2 gl2ES2) {
            int n = 0;
            int n2 = 0;
            if (null != this.vArrayData) {
                ++n;
                if (this.vArrayData.setLocation(gl2ES2, this.shaderProgram) >= 0) {
                    ++n2;
                }
            }
            if (null != this.cArrayData) {
                ++n;
                if (this.cArrayData.setLocation(gl2ES2, this.shaderProgram) >= 0) {
                    ++n2;
                }
            }
            if (null != this.nArrayData) {
                ++n;
                if (this.nArrayData.setLocation(gl2ES2, this.shaderProgram) >= 0) {
                    ++n2;
                }
            }
            if (null != this.tArrayData) {
                ++n;
                if (this.tArrayData.setLocation(gl2ES2, this.shaderProgram) >= 0) {
                    ++n2;
                }
            }
            return this.glslLocationSet = (n == n2);
        }
        
        public void destroy(final GL gl) {
            this.reset(gl);
            this.vCount = 0;
            this.cCount = 0;
            this.nCount = 0;
            this.tCount = 0;
            this.vertexArray = null;
            this.colorArray = null;
            this.normalArray = null;
            this.textCoordArray = null;
            this.vArrayData = null;
            this.cArrayData = null;
            this.nArrayData = null;
            this.tArrayData = null;
            this.buffer = null;
        }
        
        public void reset(final GL gl) {
            this.enableBuffer(gl, false);
            this.reset();
        }
        
        public void reset() {
            if (this.buffer != null) {
                this.buffer.clear();
            }
            this.rewind();
            this.mode = 0;
            this.modeOrig = 0;
            this.sealed = false;
            this.sealedGL = false;
            this.bufferEnabled = false;
            this.bufferWritten = false;
            this.vElems = 0;
            this.cElems = 0;
            this.nElems = 0;
            this.tElems = 0;
        }
        
        public void seal(final GL gl, final boolean sealedGL) {
            this.seal(sealedGL);
            if (this.sealedGL == sealedGL) {
                return;
            }
            this.sealedGL = sealedGL;
            final GL gl2 = gl.getGL();
            if (sealedGL) {
                if (this.useVBO) {
                    if (0 == this.vboName) {
                        final int[] array = { 0 };
                        gl2.glGenBuffers(1, array, 0);
                        this.vboName = array[0];
                    }
                    if (null != this.vArrayData) {
                        this.vArrayData.setVBOName(this.vboName);
                    }
                    if (null != this.cArrayData) {
                        this.cArrayData.setVBOName(this.vboName);
                    }
                    if (null != this.nArrayData) {
                        this.nArrayData.setVBOName(this.vboName);
                    }
                    if (null != this.tArrayData) {
                        this.tArrayData.setVBOName(this.vboName);
                    }
                }
                this.enableBuffer(gl2, true);
            }
            else {
                this.enableBuffer(gl2, false);
            }
        }
        
        public void seal(final boolean sealed) {
            if (this.sealed == sealed) {
                return;
            }
            this.sealed = sealed;
            if (sealed) {
                this.bufferWritten = false;
                this.rewind();
            }
        }
        
        public void enableBuffer(final GL gl, final boolean bufferEnabled) {
            if (this.bufferEnabled != bufferEnabled && this.vElems > 0) {
                if (bufferEnabled) {
                    this.checkSeal(true);
                }
                this.bufferEnabled = bufferEnabled;
                if (this.useGLSL) {
                    this.useShaderProgram(gl.getGL2ES2(), true);
                    if (null != this.shaderState) {
                        this.enableBufferGLSLShaderState(gl, bufferEnabled);
                    }
                    else {
                        this.enableBufferGLSLSimple(gl, bufferEnabled);
                    }
                }
                else {
                    this.enableBufferFixed(gl, bufferEnabled);
                }
            }
        }
        
        private final void writeBuffer(final GL gl) {
            final int n = this.vElems * this.vCompsBytes;
            final int n2 = this.cElems * this.cCompsBytes;
            final int n3 = this.nElems * this.nCompsBytes;
            final int n4 = this.tElems * this.tCompsBytes;
            final int n5 = this.buffer.limit() - (n + n2 + n3 + n4);
            if (this.bufferWrittenOnce && n5 > this.pageSize) {
                if (0 < n) {
                    gl.glBufferSubData(34962, this.vOffset, n, this.vertexArray);
                }
                if (0 < n2) {
                    gl.glBufferSubData(34962, this.cOffset, n2, this.colorArray);
                }
                if (0 < n3) {
                    gl.glBufferSubData(34962, this.nOffset, n3, this.normalArray);
                }
                if (0 < n4) {
                    gl.glBufferSubData(34962, this.tOffset, n4, this.textCoordArray);
                }
            }
            else {
                gl.glBufferData(34962, this.buffer.limit(), this.buffer, this.glBufferUsage);
                this.bufferWrittenOnce = true;
            }
        }
        
        private void enableBufferFixed(final GL gl, final boolean b) {
            final GL2ES1 gl2ES1 = gl.getGL2ES1();
            final boolean b2 = this.vComps > 0 && this.vElems > 0;
            final boolean b3 = this.cComps > 0 && this.cElems > 0;
            final boolean b4 = this.nComps > 0 && this.nElems > 0;
            final boolean b5 = this.tComps > 0 && this.tElems > 0;
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableFixed.0 " + b + ": use [ v " + b2 + ", c " + b3 + ", n " + b4 + ", t " + b5 + "], " + this.getElemUseCountStr() + ", " + this.buffer);
            }
            if (b) {
                if (this.useVBO) {
                    if (0 == this.vboName) {
                        throw new InternalError("Using VBO but no vboName");
                    }
                    gl2ES1.glBindBuffer(34962, this.vboName);
                    if (!this.bufferWritten) {
                        this.writeBuffer(gl);
                    }
                }
                this.bufferWritten = true;
            }
            if (b2) {
                if (b) {
                    gl2ES1.glEnableClientState(32884);
                    gl2ES1.glVertexPointer(this.vArrayData);
                }
                else {
                    gl2ES1.glDisableClientState(32884);
                }
            }
            if (b3) {
                if (b) {
                    gl2ES1.glEnableClientState(32886);
                    gl2ES1.glColorPointer(this.cArrayData);
                }
                else {
                    gl2ES1.glDisableClientState(32886);
                }
            }
            if (b4) {
                if (b) {
                    gl2ES1.glEnableClientState(32885);
                    gl2ES1.glNormalPointer(this.nArrayData);
                }
                else {
                    gl2ES1.glDisableClientState(32885);
                }
            }
            if (b5) {
                if (b) {
                    gl2ES1.glEnableClientState(32888);
                    gl2ES1.glTexCoordPointer(this.tArrayData);
                }
                else {
                    gl2ES1.glDisableClientState(32888);
                }
            }
            if (b && this.useVBO) {
                gl.glBindBuffer(34962, 0);
            }
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableFixed.X ");
            }
        }
        
        private void enableBufferGLSLShaderState(final GL gl, final boolean b) {
            final GL2ES2 gl2ES2 = gl.getGL2ES2();
            final boolean b2 = this.vComps > 0 && this.vElems > 0;
            final boolean b3 = this.cComps > 0 && this.cElems > 0;
            final boolean b4 = this.nComps > 0 && this.nElems > 0;
            final boolean b5 = this.tComps > 0 && this.tElems > 0;
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableGLSL.A.0 " + b + ": use [ v " + b2 + ", c " + b3 + ", n " + b4 + ", t " + b5 + "], " + this.getElemUseCountStr() + ", " + this.buffer);
            }
            if (b) {
                if (this.useVBO) {
                    if (0 == this.vboName) {
                        throw new InternalError("Using VBO but no vboName");
                    }
                    gl2ES2.glBindBuffer(34962, this.vboName);
                    if (!this.bufferWritten) {
                        this.writeBuffer(gl);
                    }
                }
                this.bufferWritten = true;
            }
            if (b2) {
                if (b) {
                    this.shaderState.enableVertexAttribArray(gl2ES2, this.vArrayData);
                    this.shaderState.vertexAttribPointer(gl2ES2, this.vArrayData);
                }
                else {
                    this.shaderState.disableVertexAttribArray(gl2ES2, this.vArrayData);
                }
            }
            if (b3) {
                if (b) {
                    this.shaderState.enableVertexAttribArray(gl2ES2, this.cArrayData);
                    this.shaderState.vertexAttribPointer(gl2ES2, this.cArrayData);
                }
                else {
                    this.shaderState.disableVertexAttribArray(gl2ES2, this.cArrayData);
                }
            }
            if (b4) {
                if (b) {
                    this.shaderState.enableVertexAttribArray(gl2ES2, this.nArrayData);
                    this.shaderState.vertexAttribPointer(gl2ES2, this.nArrayData);
                }
                else {
                    this.shaderState.disableVertexAttribArray(gl2ES2, this.nArrayData);
                }
            }
            if (b5) {
                if (b) {
                    this.shaderState.enableVertexAttribArray(gl2ES2, this.tArrayData);
                    this.shaderState.vertexAttribPointer(gl2ES2, this.tArrayData);
                }
                else {
                    this.shaderState.disableVertexAttribArray(gl2ES2, this.tArrayData);
                }
            }
            this.glslLocationSet = true;
            if (b && this.useVBO) {
                gl2ES2.glBindBuffer(34962, 0);
            }
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableGLSL.A.X ");
            }
        }
        
        private void enableBufferGLSLSimple(final GL gl, final boolean b) {
            final GL2ES2 gl2ES2 = gl.getGL2ES2();
            final boolean b2 = this.vComps > 0 && this.vElems > 0;
            final boolean b3 = this.cComps > 0 && this.cElems > 0;
            final boolean b4 = this.nComps > 0 && this.nElems > 0;
            final boolean b5 = this.tComps > 0 && this.tElems > 0;
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableGLSL.B.0 " + b + ": use [ v " + b2 + ", c " + b3 + ", n " + b4 + ", t " + b5 + "], " + this.getElemUseCountStr() + ", " + this.buffer);
            }
            if (!this.glslLocationSet && !this.resetGLSLArrayLocation(gl2ES2)) {
                if (ImmModeSink.DEBUG_DRAW) {
                    System.err.println("ImmModeSink.enableGLSL.B.X attribute locations in shader program " + this.shaderProgram + ", incomplete [" + ((null != this.vArrayData) ? this.vArrayData.getLocation() : -1) + ", " + ((null != this.cArrayData) ? this.cArrayData.getLocation() : -1) + ", " + ((null != this.nArrayData) ? this.nArrayData.getLocation() : -1) + ", " + ((null != this.tArrayData) ? this.tArrayData.getLocation() : -1) + "] - glslLocationSet " + this.glslLocationSet);
                }
                return;
            }
            if (b) {
                if (this.useVBO) {
                    if (0 == this.vboName) {
                        throw new InternalError("Using VBO but no vboName");
                    }
                    gl2ES2.glBindBuffer(34962, this.vboName);
                    if (!this.bufferWritten) {
                        this.writeBuffer(gl);
                    }
                }
                this.bufferWritten = true;
            }
            if (b2) {
                if (b) {
                    gl2ES2.glEnableVertexAttribArray(this.vArrayData.getLocation());
                    gl2ES2.glVertexAttribPointer(this.vArrayData);
                }
                else {
                    gl2ES2.glDisableVertexAttribArray(this.vArrayData.getLocation());
                }
            }
            if (b3) {
                if (b) {
                    gl2ES2.glEnableVertexAttribArray(this.cArrayData.getLocation());
                    gl2ES2.glVertexAttribPointer(this.cArrayData);
                }
                else {
                    gl2ES2.glDisableVertexAttribArray(this.cArrayData.getLocation());
                }
            }
            if (b4) {
                if (b) {
                    gl2ES2.glEnableVertexAttribArray(this.nArrayData.getLocation());
                    gl2ES2.glVertexAttribPointer(this.nArrayData);
                }
                else {
                    gl2ES2.glDisableVertexAttribArray(this.nArrayData.getLocation());
                }
            }
            if (b5) {
                if (b) {
                    gl2ES2.glEnableVertexAttribArray(this.tArrayData.getLocation());
                    gl2ES2.glVertexAttribPointer(this.tArrayData);
                }
                else {
                    gl2ES2.glDisableVertexAttribArray(this.tArrayData.getLocation());
                }
            }
            if (b && this.useVBO) {
                gl2ES2.glBindBuffer(34962, 0);
            }
            if (ImmModeSink.DEBUG_DRAW) {
                System.err.println("ImmModeSink.enableGLSL.B.X ");
            }
        }
        
        @Override
        public String toString() {
            return "VBOSet[mode " + this.mode + ", modeOrig " + this.modeOrig + ", use/count " + this.getElemUseCountStr() + ", sealed " + this.sealed + ", sealedGL " + this.sealedGL + ", bufferEnabled " + this.bufferEnabled + ", bufferWritten " + this.bufferWritten + " (once " + this.bufferWrittenOnce + ")" + ", useVBO " + this.useVBO + ", vboName " + this.vboName + ", useGLSL " + this.useGLSL + (this.useGLSL ? (", useShaderState " + (null != this.shaderState) + ", shaderProgram " + this.shaderProgram + ", glslLocationSet " + this.glslLocationSet) : "") + ",\n\t" + this.vArrayData + ",\n\t" + this.cArrayData + ",\n\t" + this.nArrayData + ",\n\t" + this.tArrayData + "]";
        }
        
        protected String getElemUseCountStr() {
            return "[v " + this.vElems + "/" + this.vCount + ", c " + this.cElems + "/" + this.cCount + ", n " + this.nElems + "/" + this.nCount + ", t " + this.tElems + "/" + this.tCount + "]";
        }
        
        protected boolean fitElementInBuffer(final int n) {
            switch (n) {
                case 0: {
                    return this.vCount - this.vElems >= 1;
                }
                case 1: {
                    return this.cCount - this.cElems >= 1;
                }
                case 2: {
                    return this.nCount - this.nElems >= 1;
                }
                case 3: {
                    return this.tCount - this.tElems >= 1;
                }
                default: {
                    throw new InternalError("XXX");
                }
            }
        }
        
        protected boolean reallocateBuffer(final int n) {
            final int n2 = n - (this.vCount - this.vElems);
            final int n3 = n - (this.cCount - this.cElems);
            final int n4 = n - (this.nCount - this.nElems);
            final int n5 = n - (this.tCount - this.tElems);
            if (0 >= n2 && 0 >= n3 && 0 >= n4 && 0 >= n5) {
                if (ImmModeSink.DEBUG_BUFFER) {
                    System.err.println("ImmModeSink.realloc: " + this.getElemUseCountStr() + " + " + n + " -> NOP");
                }
                return false;
            }
            if (ImmModeSink.DEBUG_BUFFER) {
                System.err.println("ImmModeSink.realloc: " + this.getElemUseCountStr() + " + " + n);
            }
            this.vCount += n2;
            this.cCount += n3;
            this.nCount += n4;
            this.tCount += n5;
            final int n6 = this.vCount * this.vCompsBytes;
            final int n7 = this.cCount * this.cCompsBytes;
            final int n8 = this.nCount * this.nCompsBytes;
            final int n9 = this.tCount * this.tCompsBytes;
            this.buffer = Buffers.newDirectByteBuffer(n6 + n7 + n8 + n9);
            this.vOffset = 0;
            if (n6 > 0) {
                this.vertexArray = GLBuffers.sliceGLBuffer(this.buffer, this.vOffset, n6, this.vDataType);
            }
            else {
                this.vertexArray = null;
            }
            this.cOffset = this.vOffset + n6;
            if (n7 > 0) {
                this.colorArray = GLBuffers.sliceGLBuffer(this.buffer, this.cOffset, n7, this.cDataType);
            }
            else {
                this.colorArray = null;
            }
            this.nOffset = this.cOffset + n7;
            if (n8 > 0) {
                this.normalArray = GLBuffers.sliceGLBuffer(this.buffer, this.nOffset, n8, this.nDataType);
            }
            else {
                this.normalArray = null;
            }
            this.tOffset = this.nOffset + n8;
            if (n9 > 0) {
                this.textCoordArray = GLBuffers.sliceGLBuffer(this.buffer, this.tOffset, n9, this.tDataType);
            }
            else {
                this.textCoordArray = null;
            }
            this.buffer.position(this.tOffset + n9);
            this.buffer.flip();
            if (this.vComps > 0) {
                this.vArrayData = GLArrayDataWrapper.createFixed(32884, this.vComps, this.vDataType, GLBuffers.isGLTypeFixedPoint(this.vDataType), 0, this.vertexArray, 0, this.vOffset, 35044, 34962);
            }
            else {
                this.vArrayData = null;
            }
            if (this.cComps > 0) {
                this.cArrayData = GLArrayDataWrapper.createFixed(32886, this.cComps, this.cDataType, GLBuffers.isGLTypeFixedPoint(this.cDataType), 0, this.colorArray, 0, this.cOffset, 35044, 34962);
            }
            else {
                this.cArrayData = null;
            }
            if (this.nComps > 0) {
                this.nArrayData = GLArrayDataWrapper.createFixed(32885, this.nComps, this.nDataType, GLBuffers.isGLTypeFixedPoint(this.nDataType), 0, this.normalArray, 0, this.nOffset, 35044, 34962);
            }
            else {
                this.nArrayData = null;
            }
            if (this.tComps > 0) {
                this.tArrayData = GLArrayDataWrapper.createFixed(32888, this.tComps, this.tDataType, GLBuffers.isGLTypeFixedPoint(this.tDataType), 0, this.textCoordArray, 0, this.tOffset, 35044, 34962);
            }
            else {
                this.tArrayData = null;
            }
            this.bufferWrittenOnce = false;
            if (ImmModeSink.DEBUG_BUFFER) {
                System.err.println("ImmModeSink.realloc.X: " + this.toString());
                ExceptionUtils.dumpStack(System.err);
            }
            return true;
        }
        
        protected final boolean growBuffer(final int n) {
            if (null != this.buffer && !this.sealed && !this.fitElementInBuffer(n)) {
                final Buffer vertexArray = this.vertexArray;
                final Buffer colorArray = this.colorArray;
                final Buffer normalArray = this.normalArray;
                final Buffer textCoordArray = this.textCoordArray;
                if (this.reallocateBuffer(this.resizeElementCount)) {
                    if (null != vertexArray) {
                        vertexArray.flip();
                        Buffers.put(this.vertexArray, vertexArray);
                    }
                    if (null != colorArray) {
                        colorArray.flip();
                        Buffers.put(this.colorArray, colorArray);
                    }
                    if (null != normalArray) {
                        normalArray.flip();
                        Buffers.put(this.normalArray, normalArray);
                    }
                    if (null != textCoordArray) {
                        textCoordArray.flip();
                        Buffers.put(this.textCoordArray, textCoordArray);
                    }
                    return true;
                }
            }
            return false;
        }
        
        private void countAndPadding(final int n, int i) {
            if (this.sealed) {
                return;
            }
            Buffer buffer = null;
            boolean b = false;
            int n2 = 0;
            switch (n) {
                case 0: {
                    buffer = this.vertexArray;
                    b = this.vDataTypeSigned;
                    n2 = ((4 == this.vComps) ? 1 : 0);
                    ++this.vElems;
                    break;
                }
                case 1: {
                    buffer = this.colorArray;
                    b = this.cDataTypeSigned;
                    n2 = ((4 == this.cComps) ? 1 : 0);
                    ++this.cElems;
                    break;
                }
                case 2: {
                    buffer = this.normalArray;
                    b = this.nDataTypeSigned;
                    n2 = 0;
                    ++this.nElems;
                    break;
                }
                case 3: {
                    buffer = this.textCoordArray;
                    b = this.tDataTypeSigned;
                    n2 = 0;
                    ++this.tElems;
                    break;
                }
                default: {
                    throw new InternalError("Invalid type " + n);
                }
            }
            if (null == buffer) {
                return;
            }
            while (i > n2) {
                --i;
                Buffers.putNf(buffer, b, 0.0f);
            }
            if (i > 0) {
                Buffers.putNf(buffer, b, 1.0f);
            }
        }
    }
}
