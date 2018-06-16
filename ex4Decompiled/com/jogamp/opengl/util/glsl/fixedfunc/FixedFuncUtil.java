// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl.fixedfunc;

import com.jogamp.opengl.fixedfunc.GLPointerFuncUtil;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLException;
import jogamp.opengl.util.glsl.fixedfunc.FixedFuncImpl;
import jogamp.opengl.util.glsl.fixedfunc.FixedFuncHook;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.GL;

public class FixedFuncUtil
{
    public static final String mgl_Vertex = "mgl_Vertex";
    public static final String mgl_Normal = "mgl_Normal";
    public static final String mgl_Color = "mgl_Color";
    public static final String mgl_MultiTexCoord = "mgl_MultiTexCoord";
    
    public static final GL2ES1 wrapFixedFuncEmul(final GL gl, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix, final boolean b, final boolean verbose) {
        if (gl.isGL2ES2() && (!gl.isGL2ES1() || b)) {
            final GL2ES2 gl2ES2 = gl.getGL2ES2();
            final FixedFuncHook fixedFuncHook = new FixedFuncHook(gl2ES2, shaderSelectionMode, pmvMatrix);
            fixedFuncHook.setVerbose(verbose);
            final FixedFuncImpl gl2 = new FixedFuncImpl(gl2ES2, fixedFuncHook);
            gl.getContext().setGL(gl2);
            return gl2;
        }
        if (gl.isGL2ES1()) {
            return gl.getGL2ES1();
        }
        throw new GLException("GL Object is neither GL2ES1 nor GL2ES2: " + gl.getContext());
    }
    
    public static final GL2ES1 wrapFixedFuncEmul(final GL gl, final ShaderSelectionMode shaderSelectionMode, final PMVMatrix pmvMatrix) {
        return wrapFixedFuncEmul(gl, shaderSelectionMode, null, false, false);
    }
    
    public static String getPredefinedArrayIndexName(final int n) {
        return GLPointerFuncUtil.getPredefinedArrayIndexName(n);
    }
}
