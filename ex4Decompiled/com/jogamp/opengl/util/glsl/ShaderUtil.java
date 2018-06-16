// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl;

import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLException;
import java.util.HashSet;
import java.util.Set;
import java.nio.IntBuffer;
import java.io.PrintStream;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL;

public class ShaderUtil
{
    private static final String implObjectKey = "com.jogamp.opengl.util.glsl.ShaderUtil";
    
    public static String getShaderInfoLog(final GL gl, final int n) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int[] array = { 0 };
        gl2ES2.glGetShaderiv(n, 35716, array, 0);
        if (array[0] == 0) {
            return "(no info log)";
        }
        final int[] array2 = { 0 };
        final byte[] array3 = new byte[array[0]];
        gl2ES2.glGetShaderInfoLog(n, array[0], array2, 0, array3, 0);
        return new String(array3, 0, array2[0]);
    }
    
    public static String getProgramInfoLog(final GL gl, final int n) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int[] array = { 0 };
        gl2ES2.glGetProgramiv(n, 35716, array, 0);
        if (array[0] == 0) {
            return "(no info log)";
        }
        final int[] array2 = { 0 };
        final byte[] array3 = new byte[array[0]];
        gl2ES2.glGetProgramInfoLog(n, array[0], array2, 0, array3, 0);
        return new String(array3, 0, array2[0]);
    }
    
    public static boolean isShaderStatusValid(final GL gl, final int n, final int n2, final PrintStream printStream) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int[] array = { 0 };
        gl2ES2.glGetShaderiv(n, n2, array, 0);
        final boolean b = array[0] == 1;
        if (!b && null != printStream) {
            printStream.println("Shader status invalid: " + getShaderInfoLog(gl2ES2, n));
        }
        return b;
    }
    
    public static boolean isShaderStatusValid(final GL gl, final IntBuffer intBuffer, final int n, final PrintStream printStream) {
        boolean b = true;
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            b = (isShaderStatusValid(gl, intBuffer.get(i), n, printStream) && b);
        }
        return b;
    }
    
    public static boolean isProgramStatusValid(final GL gl, final int n, final int n2) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int[] array = { 0 };
        gl2ES2.glGetProgramiv(n, n2, array, 0);
        return array[0] == 1;
    }
    
    public static boolean isProgramLinkStatusValid(final GL gl, final int n, final PrintStream printStream) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (!gl2ES2.glIsProgram(n)) {
            if (null != printStream) {
                printStream.println("Program name invalid: " + n);
            }
            return false;
        }
        if (!isProgramStatusValid(gl2ES2, n, 35714)) {
            if (null != printStream) {
                printStream.println("Program link failed: " + n + "\n\t" + getProgramInfoLog(gl2ES2, n));
            }
            return false;
        }
        return true;
    }
    
    public static boolean isProgramExecStatusValid(final GL gl, final int n, final PrintStream printStream) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        gl2ES2.glValidateProgram(n);
        if (!isProgramStatusValid(gl2ES2, n, 35715)) {
            if (null != printStream) {
                printStream.println("Program validation failed: " + n + "\n\t" + getProgramInfoLog(gl2ES2, n));
            }
            return false;
        }
        return true;
    }
    
    public static void createShader(final GL gl, final int n, final IntBuffer intBuffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            intBuffer.put(i, gl2ES2.glCreateShader(n));
        }
    }
    
    public static Set<Integer> getShaderBinaryFormats(final GL gl) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final ProfileInformation profileInformation = getProfileInformation(gl2ES2);
        if (null == profileInformation.shaderBinaryFormats) {
            profileInformation.shaderBinaryFormats = new HashSet<Integer>();
            if (gl2ES2.isGLES2Compatible()) {
                try {
                    final int[] array = { 0 };
                    gl2ES2.glGetIntegerv(36345, array, 0);
                    final int n = (0 == gl2ES2.glGetError()) ? array[0] : 0;
                    if (n > 0) {
                        final int[] array2 = new int[n];
                        gl2ES2.glGetIntegerv(36344, array2, 0);
                        for (int i = 0; i < n; ++i) {
                            profileInformation.shaderBinaryFormats.add(array2[i]);
                        }
                    }
                }
                catch (GLException ex) {
                    System.err.println("Caught exception on thread " + Thread.currentThread().getName());
                    ex.printStackTrace();
                }
            }
        }
        return profileInformation.shaderBinaryFormats;
    }
    
    public static boolean isShaderCompilerAvailable(final GL gl) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final ProfileInformation profileInformation = getProfileInformation(gl2ES2);
        if (null == profileInformation.shaderCompilerAvailable) {
            if (gl2ES2.isGLES2()) {
                boolean b = false;
                try {
                    final byte[] array = { 0 };
                    gl2ES2.glGetBooleanv(36346, array, 0);
                    boolean b2 = 0 == gl2ES2.glGetError() && array[0] != 0;
                    if (!b2 && getShaderBinaryFormats(gl2ES2).size() == 0) {
                        b2 = true;
                    }
                    profileInformation.shaderCompilerAvailable = b2;
                    b = true;
                }
                catch (GLException ex) {
                    System.err.println("Caught exception on thread " + Thread.currentThread().getName());
                    ex.printStackTrace();
                }
                if (!b) {
                    profileInformation.shaderCompilerAvailable = true;
                }
            }
            else {
                if (!gl2ES2.isGL2ES2()) {
                    throw new GLException("Invalid OpenGL profile");
                }
                profileInformation.shaderCompilerAvailable = new Boolean(true);
            }
        }
        return profileInformation.shaderCompilerAvailable;
    }
    
    public static boolean isGeometryShaderSupported(final GL gl) {
        final GLContext context = gl.getContext();
        return context.getGLVersionNumber().compareTo(GLContext.Version3_2) >= 0 || context.isExtensionAvailable("GL_ARB_geometry_shader4");
    }
    
    public static void shaderSource(final GL gl, final int n, final CharSequence[] array) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (!isShaderCompilerAvailable(gl)) {
            throw new GLException("No compiler is available");
        }
        final int n2 = (null != array) ? array.length : 0;
        if (n2 == 0) {
            throw new GLException("No sources specified");
        }
        final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(n2);
        for (int i = 0; i < n2; ++i) {
            directIntBuffer.put(i, array[i].length());
        }
        if (array instanceof String[]) {
            gl2ES2.glShaderSource(n, n2, (String[])array, directIntBuffer);
        }
        else {
            final String[] array2 = new String[array.length];
            for (int j = array.length - 1; j >= 0; --j) {
                final CharSequence charSequence = array[j];
                if (charSequence instanceof String) {
                    array2[j] = (String)charSequence;
                }
                else {
                    array2[j] = array[j].toString();
                }
            }
            gl2ES2.glShaderSource(n, n2, array2, directIntBuffer);
        }
    }
    
    public static void shaderSource(final GL gl, final IntBuffer intBuffer, final CharSequence[][] array) {
        final int n = (null != array) ? array.length : 0;
        final int n2 = (null != intBuffer) ? intBuffer.remaining() : 0;
        if (n2 <= 0 || n <= 0 || n2 != n) {
            throw new GLException("Invalid number of shaders and/or sources: shaders=" + n2 + ", sources=" + n);
        }
        for (int i = 0; i < n; ++i) {
            shaderSource(gl, intBuffer.get(intBuffer.position() + i), array[i]);
        }
    }
    
    public static void shaderBinary(final GL gl, final IntBuffer intBuffer, final int n, final Buffer buffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        if (getShaderBinaryFormats(gl2ES2).size() <= 0) {
            throw new GLException("No binary formats are supported");
        }
        final int remaining = intBuffer.remaining();
        if (remaining <= 0) {
            throw new GLException("No shaders specified");
        }
        if (null == buffer) {
            throw new GLException("Null shader binary");
        }
        final int remaining2 = buffer.remaining();
        if (0 >= remaining2) {
            throw new GLException("Empty shader binary (remaining == 0)");
        }
        gl2ES2.glShaderBinary(remaining, intBuffer, n, buffer, remaining2);
    }
    
    public static void compileShader(final GL gl, final IntBuffer intBuffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            gl2ES2.glCompileShader(intBuffer.get(i));
        }
    }
    
    public static void attachShader(final GL gl, final int n, final IntBuffer intBuffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            gl2ES2.glAttachShader(n, intBuffer.get(i));
        }
    }
    
    public static void detachShader(final GL gl, final int n, final IntBuffer intBuffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            gl2ES2.glDetachShader(n, intBuffer.get(i));
        }
    }
    
    public static void deleteShader(final GL gl, final IntBuffer intBuffer) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        for (int i = intBuffer.position(); i < intBuffer.limit(); ++i) {
            gl2ES2.glDeleteShader(intBuffer.get(i));
        }
    }
    
    public static boolean createAndLoadShader(final GL gl, final IntBuffer intBuffer, final int n, final int n2, final Buffer buffer, final PrintStream printStream) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int glGetError = gl2ES2.glGetError();
        if (glGetError != 0 && null != printStream) {
            printStream.println("createAndLoadShader: Pre GL Error: 0x" + Integer.toHexString(glGetError));
        }
        createShader(gl2ES2, n, intBuffer);
        final int glGetError2 = gl2ES2.glGetError();
        if (glGetError2 != 0) {
            throw new GLException("createAndLoadShader: CreateShader failed, GL Error: 0x" + Integer.toHexString(glGetError2));
        }
        shaderBinary(gl2ES2, intBuffer, n2, buffer);
        final int glGetError3 = gl2ES2.glGetError();
        if (glGetError3 != 0 && null != printStream) {
            printStream.println("createAndLoadShader: ShaderBinary failed, GL Error: 0x" + Integer.toHexString(glGetError3));
        }
        return glGetError3 == 0;
    }
    
    public static boolean createAndCompileShader(final GL gl, final IntBuffer intBuffer, final int n, final CharSequence[][] array, final PrintStream printStream) {
        final GL2ES2 gl2ES2 = gl.getGL2ES2();
        final int glGetError = gl2ES2.glGetError();
        if (glGetError != 0 && null != printStream) {
            printStream.println("createAndCompileShader: Pre GL Error: 0x" + Integer.toHexString(glGetError));
        }
        createShader(gl2ES2, n, intBuffer);
        final int glGetError2 = gl2ES2.glGetError();
        if (glGetError2 != 0) {
            throw new GLException("createAndCompileShader: CreateShader failed, GL Error: 0x" + Integer.toHexString(glGetError2));
        }
        shaderSource(gl2ES2, intBuffer, array);
        final int glGetError3 = gl2ES2.glGetError();
        if (glGetError3 != 0) {
            throw new GLException("createAndCompileShader: ShaderSource failed, GL Error: 0x" + Integer.toHexString(glGetError3));
        }
        compileShader(gl2ES2, intBuffer);
        final int glGetError4 = gl2ES2.glGetError();
        if (glGetError4 != 0 && null != printStream) {
            printStream.println("createAndCompileShader: CompileShader failed, GL Error: 0x" + Integer.toHexString(glGetError4));
        }
        return isShaderStatusValid(gl2ES2, intBuffer, 35713, printStream) && glGetError4 == 0;
    }
    
    private static ProfileInformation getProfileInformation(final GL gl) {
        final GLContext context = gl.getContext();
        context.validateCurrent();
        ProfileInformation profileInformation = (ProfileInformation)context.getAttachedObject("com.jogamp.opengl.util.glsl.ShaderUtil");
        if (profileInformation == null) {
            profileInformation = new ProfileInformation();
            context.attachObject("com.jogamp.opengl.util.glsl.ShaderUtil", profileInformation);
        }
        return profileInformation;
    }
    
    private static class ProfileInformation
    {
        Boolean shaderCompilerAvailable;
        Set<Integer> shaderBinaryFormats;
        
        private ProfileInformation() {
            this.shaderCompilerAvailable = null;
            this.shaderBinaryFormats = null;
        }
    }
}
