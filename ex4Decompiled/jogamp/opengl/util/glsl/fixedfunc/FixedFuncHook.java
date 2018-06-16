// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.glsl.fixedfunc;

import com.jogamp.opengl.util.GLArrayDataWrapper;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLArrayData;
import com.jogamp.common.util.ValueConv;
import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.Buffer;
import com.jogamp.opengl.util.glsl.fixedfunc.ShaderSelectionMode;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

public class FixedFuncHook implements GLLightingFunc, GLMatrixFunc, GLPointerFunc
{
    public static final int MAX_TEXTURE_UNITS = 8;
    protected final GLProfile gl2es1GLProfile;
    protected FixedFuncPipeline fixedFunction;
    protected PMVMatrix pmvMatrix;
    protected boolean ownsPMVMatrix;
    protected GL2ES2 gl;
    
    public FixedFuncHook(final GL2ES2 gl, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix) {
        this.gl2es1GLProfile = GLProfile.createCustomGLProfile("GL2ES1", gl.getGLProfile().getImpl());
        this.gl = gl;
        if (null != pmvMatrix) {
            this.ownsPMVMatrix = false;
            this.pmvMatrix = pmvMatrix;
        }
        else {
            this.ownsPMVMatrix = true;
            this.pmvMatrix = new PMVMatrix();
        }
        this.fixedFunction = new FixedFuncPipeline(this.gl, shaderSelectionMode, this.pmvMatrix);
    }
    
    public FixedFuncHook(final GL2ES2 gl, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix, final Class<?> clazz, final String s, final String s2, final String s3, final String s4, final String s5, final String s6) {
        this.gl2es1GLProfile = GLProfile.createCustomGLProfile("GL2ES1", gl.getGLProfile().getImpl());
        this.gl = gl;
        if (null != pmvMatrix) {
            this.ownsPMVMatrix = false;
            this.pmvMatrix = pmvMatrix;
        }
        else {
            this.ownsPMVMatrix = true;
            this.pmvMatrix = new PMVMatrix();
        }
        this.fixedFunction = new FixedFuncPipeline(this.gl, shaderSelectionMode, this.pmvMatrix, clazz, s, s2, s3, s4, s5, s6);
    }
    
    public boolean verbose() {
        return this.fixedFunction.verbose();
    }
    
    public void setVerbose(final boolean verbose) {
        this.fixedFunction.setVerbose(verbose);
    }
    
    public void destroy() {
        this.fixedFunction.destroy(this.gl);
        this.fixedFunction = null;
        this.pmvMatrix = null;
        this.gl = null;
    }
    
    public PMVMatrix getMatrix() {
        return this.pmvMatrix;
    }
    
    public final boolean isGL4core() {
        return false;
    }
    
    public final boolean isGL3core() {
        return false;
    }
    
    public final boolean isGLcore() {
        return false;
    }
    
    public final boolean isGLES2Compatible() {
        return false;
    }
    
    public final boolean isGLES3Compatible() {
        return false;
    }
    
    public final GLProfile getGLProfile() {
        return this.gl2es1GLProfile;
    }
    
    public void glDrawArrays(final int n, final int n2, final int n3) {
        this.fixedFunction.glDrawArrays(this.gl, n, n2, n3);
    }
    
    public void glDrawElements(final int n, final int n2, final int n3, final Buffer buffer) {
        this.fixedFunction.glDrawElements(this.gl, n, n2, n3, buffer);
    }
    
    public void glDrawElements(final int n, final int n2, final int n3, final long n4) {
        this.fixedFunction.glDrawElements(this.gl, n, n2, n3, n4);
    }
    
    public void glActiveTexture(final int n) {
        this.fixedFunction.glActiveTexture(n);
        this.gl.glActiveTexture(n);
    }
    
    public void glEnable(final int n) {
        if (this.fixedFunction.glEnable(n, true)) {
            this.gl.glEnable(n);
        }
    }
    
    public void glDisable(final int n) {
        if (this.fixedFunction.glEnable(n, false)) {
            this.gl.glDisable(n);
        }
    }
    
    @Override
    public void glGetFloatv(final int n, final FloatBuffer floatBuffer) {
        if (PMVMatrix.isMatrixGetName(n)) {
            this.pmvMatrix.glGetFloatv(n, floatBuffer);
            return;
        }
        this.gl.glGetFloatv(n, floatBuffer);
    }
    
    @Override
    public void glGetFloatv(final int n, final float[] array, final int n2) {
        if (PMVMatrix.isMatrixGetName(n)) {
            this.pmvMatrix.glGetFloatv(n, array, n2);
            return;
        }
        this.gl.glGetFloatv(n, array, n2);
    }
    
    @Override
    public void glGetIntegerv(final int n, final IntBuffer intBuffer) {
        if (PMVMatrix.isMatrixGetName(n)) {
            this.pmvMatrix.glGetIntegerv(n, intBuffer);
            return;
        }
        this.gl.glGetIntegerv(n, intBuffer);
    }
    
    @Override
    public void glGetIntegerv(final int n, final int[] array, final int n2) {
        if (PMVMatrix.isMatrixGetName(n)) {
            this.pmvMatrix.glGetIntegerv(n, array, n2);
            return;
        }
        this.gl.glGetIntegerv(n, array, n2);
    }
    
    public void glTexEnvi(final int n, final int n2, final int n3) {
        this.fixedFunction.glTexEnvi(n, n2, n3);
    }
    
    public void glGetTexEnviv(final int n, final int n2, final IntBuffer intBuffer) {
        this.fixedFunction.glGetTexEnviv(n, n2, intBuffer);
    }
    
    public void glGetTexEnviv(final int n, final int n2, final int[] array, final int n3) {
        this.fixedFunction.glGetTexEnviv(n, n2, array, n3);
    }
    
    public void glBindTexture(final int n, final int n2) {
        this.fixedFunction.glBindTexture(n, n2);
        this.gl.glBindTexture(n, n2);
    }
    
    public void glTexImage2D(final int n, final int n2, int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final Buffer buffer) {
        switch (n3) {
            case 3: {
                n3 = ((6408 == n7) ? 6408 : 6407);
                break;
            }
            case 4: {
                n3 = ((6407 == n7) ? 6407 : 6408);
                break;
            }
        }
        this.fixedFunction.glTexImage2D(n, n3, n7);
        this.gl.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
    }
    
    public void glTexImage2D(final int n, final int n2, int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final long n9) {
        switch (n3) {
            case 3: {
                n3 = ((6408 == n7) ? 6408 : 6407);
                break;
            }
            case 4: {
                n3 = ((6407 == n7) ? 6407 : 6408);
                break;
            }
        }
        this.fixedFunction.glTexImage2D(n, n3, n7);
        this.gl.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, n9);
    }
    
    public void glPointSize(final float n) {
        this.fixedFunction.glPointSize(n);
    }
    
    public void glPointParameterf(final int n, final float n2) {
        this.fixedFunction.glPointParameterf(n, n2);
    }
    
    public void glPointParameterfv(final int n, final float[] array, final int n2) {
        this.fixedFunction.glPointParameterfv(n, array, n2);
    }
    
    public void glPointParameterfv(final int n, final FloatBuffer floatBuffer) {
        this.fixedFunction.glPointParameterfv(n, floatBuffer);
    }
    
    public int glGetMatrixMode() {
        return this.pmvMatrix.glGetMatrixMode();
    }
    
    @Override
    public void glMatrixMode(final int n) {
        this.pmvMatrix.glMatrixMode(n);
    }
    
    @Override
    public void glLoadMatrixf(final FloatBuffer floatBuffer) {
        this.pmvMatrix.glLoadMatrixf(floatBuffer);
    }
    
    @Override
    public void glLoadMatrixf(final float[] array, final int n) {
        this.glLoadMatrixf(Buffers.newDirectFloatBuffer(array, n));
    }
    
    @Override
    public void glPopMatrix() {
        this.pmvMatrix.glPopMatrix();
    }
    
    @Override
    public void glPushMatrix() {
        this.pmvMatrix.glPushMatrix();
    }
    
    @Override
    public void glLoadIdentity() {
        this.pmvMatrix.glLoadIdentity();
    }
    
    @Override
    public void glMultMatrixf(final FloatBuffer floatBuffer) {
        this.pmvMatrix.glMultMatrixf(floatBuffer);
    }
    
    @Override
    public void glMultMatrixf(final float[] array, final int n) {
        this.glMultMatrixf(Buffers.newDirectFloatBuffer(array, n));
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        this.pmvMatrix.glTranslatef(n, n2, n3);
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.pmvMatrix.glRotatef(n, n2, n3, n4);
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        this.pmvMatrix.glScalef(n, n2, n3);
    }
    
    public void glOrtho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.glOrthof((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6);
    }
    
    @Override
    public void glOrthof(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.pmvMatrix.glOrthof(n, n2, n3, n4, n5, n6);
    }
    
    public void glFrustum(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.glFrustumf((float)n, (float)n2, (float)n3, (float)n4, (float)n5, (float)n6);
    }
    
    @Override
    public void glFrustumf(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.pmvMatrix.glFrustumf(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, final float n4) {
        this.fixedFunction.glColor4f(this.gl, n, n2, n3, n4);
    }
    
    public void glColor4ub(final byte b, final byte b2, final byte b3, final byte b4) {
        this.glColor4f(ValueConv.byte_to_float(b, false), ValueConv.byte_to_float(b2, false), ValueConv.byte_to_float(b3, false), ValueConv.byte_to_float(b4, false));
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.fixedFunction.glLightfv(this.gl, n, n2, floatBuffer);
    }
    
    @Override
    public void glLightfv(final int n, final int n2, final float[] array, final int n3) {
        this.glLightfv(n, n2, Buffers.newDirectFloatBuffer(array, n3));
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final FloatBuffer floatBuffer) {
        this.fixedFunction.glMaterialfv(this.gl, n, n2, floatBuffer);
    }
    
    @Override
    public void glMaterialfv(final int n, final int n2, final float[] array, final int n3) {
        this.glMaterialfv(n, n2, Buffers.newDirectFloatBuffer(array, n3));
    }
    
    @Override
    public void glMaterialf(final int n, final int n2, final float n3) {
        this.glMaterialfv(n, n2, Buffers.newDirectFloatBuffer(new float[] { n3 }));
    }
    
    @Override
    public void glShadeModel(final int n) {
        this.fixedFunction.glShadeModel(this.gl, n);
    }
    
    public void glAlphaFunc(final int n, final float n2) {
        this.fixedFunction.glAlphaFunc(n, n2);
    }
    
    public void glClientActiveTexture(final int n) {
        this.fixedFunction.glClientActiveTexture(n);
    }
    
    @Override
    public void glEnableClientState(final int n) {
        this.fixedFunction.glEnableClientState(this.gl, n);
    }
    
    @Override
    public void glDisableClientState(final int n) {
        this.fixedFunction.glDisableClientState(this.gl, n);
    }
    
    @Override
    public void glVertexPointer(final GLArrayData glArrayData) {
        if (glArrayData.isVBO()) {
            if (!this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not enabled: " + glArrayData);
            }
        }
        else {
            if (this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not disabled: " + glArrayData);
            }
            Buffers.rangeCheck(glArrayData.getBuffer(), 1);
            if (!Buffers.isDirect(glArrayData.getBuffer())) {
                throw new GLException("Argument \"pointer\" was not a direct buffer");
            }
        }
        this.fixedFunction.glVertexPointer(this.gl, glArrayData);
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.glVertexPointer(GLArrayDataWrapper.createFixed(32884, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, buffer, 0, 0L, 0, 34962));
    }
    
    @Override
    public void glVertexPointer(final int n, final int n2, final int n3, final long n4) {
        final int boundBuffer = this.gl.getBoundBuffer(34962);
        if (boundBuffer == 0) {
            throw new GLException("no GL_ARRAY_BUFFER VBO bound");
        }
        this.glVertexPointer(GLArrayDataWrapper.createFixed(32884, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, null, boundBuffer, n4, 35044, 34962));
    }
    
    @Override
    public void glColorPointer(final GLArrayData glArrayData) {
        if (glArrayData.isVBO()) {
            if (!this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not enabled: " + glArrayData);
            }
        }
        else {
            if (this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not disabled: " + glArrayData);
            }
            Buffers.rangeCheck(glArrayData.getBuffer(), 1);
            if (!Buffers.isDirect(glArrayData.getBuffer())) {
                throw new GLException("Argument \"pointer\" was not a direct buffer");
            }
        }
        this.fixedFunction.glColorPointer(this.gl, glArrayData);
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.glColorPointer(GLArrayDataWrapper.createFixed(32886, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, buffer, 0, 0L, 0, 34962));
    }
    
    @Override
    public void glColorPointer(final int n, final int n2, final int n3, final long n4) {
        final int boundBuffer = this.gl.getBoundBuffer(34962);
        if (boundBuffer == 0) {
            throw new GLException("no GL_ARRAY_BUFFER VBO bound");
        }
        this.glColorPointer(GLArrayDataWrapper.createFixed(32886, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, null, boundBuffer, n4, 35044, 34962));
    }
    
    @Override
    public void glNormalPointer(final GLArrayData glArrayData) {
        if (glArrayData.getComponentCount() != 3) {
            throw new GLException("Only 3 components per normal allowed");
        }
        if (glArrayData.isVBO()) {
            if (!this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not enabled: " + glArrayData);
            }
        }
        else {
            if (this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not disabled: " + glArrayData);
            }
            Buffers.rangeCheck(glArrayData.getBuffer(), 1);
            if (!Buffers.isDirect(glArrayData.getBuffer())) {
                throw new GLException("Argument \"pointer\" was not a direct buffer");
            }
        }
        this.fixedFunction.glNormalPointer(this.gl, glArrayData);
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final Buffer buffer) {
        this.glNormalPointer(GLArrayDataWrapper.createFixed(32885, 3, n, GLBuffers.isGLTypeFixedPoint(n), n2, buffer, 0, 0L, 0, 34962));
    }
    
    @Override
    public void glNormalPointer(final int n, final int n2, final long n3) {
        final int boundBuffer = this.gl.getBoundBuffer(34962);
        if (boundBuffer == 0) {
            throw new GLException("no GL_ARRAY_BUFFER VBO bound");
        }
        this.glNormalPointer(GLArrayDataWrapper.createFixed(32885, 3, n, GLBuffers.isGLTypeFixedPoint(n), n2, null, boundBuffer, n3, 35044, 34962));
    }
    
    @Override
    public void glTexCoordPointer(final GLArrayData glArrayData) {
        if (glArrayData.isVBO()) {
            if (!this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not enabled: " + glArrayData);
            }
        }
        else {
            if (this.gl.isVBOArrayBound()) {
                throw new GLException("VBO array is not disabled: " + glArrayData);
            }
            Buffers.rangeCheck(glArrayData.getBuffer(), 1);
            if (!Buffers.isDirect(glArrayData.getBuffer())) {
                throw new GLException("Argument \"pointer\" was not a direct buffer");
            }
        }
        this.fixedFunction.glTexCoordPointer(this.gl, glArrayData);
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final Buffer buffer) {
        this.glTexCoordPointer(GLArrayDataWrapper.createFixed(32888, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, buffer, 0, 0L, 0, 34962));
    }
    
    @Override
    public void glTexCoordPointer(final int n, final int n2, final int n3, final long n4) {
        final int boundBuffer = this.gl.getBoundBuffer(34962);
        if (boundBuffer == 0) {
            throw new GLException("no GL_ARRAY_BUFFER VBO bound");
        }
        this.glTexCoordPointer(GLArrayDataWrapper.createFixed(32888, n, n2, GLBuffers.isGLTypeFixedPoint(n2), n3, null, boundBuffer, n4, 35044, 34962));
    }
    
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName() + " (");
        if (null != this.pmvMatrix) {
            sb.append(", matrixDirty: " + (0 != this.pmvMatrix.getModifiedBits(false)));
        }
        sb.append("\n\t, FixedFunction: " + this.fixedFunction);
        sb.append(this.gl);
        sb.append(" )");
        return sb.toString();
    }
}
