// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl.sdk;

import java.io.InputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.jogamp.opengl.GLException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.util.glsl.ShaderCode;
import java.io.File;

public abstract class CompileShader
{
    public abstract int getBinaryFormat();
    
    public abstract File getSDKCompilerDir();
    
    public abstract String getVertexShaderCompiler();
    
    public abstract String getFragmentShaderCompiler();
    
    public void processOneShader(final String s) throws IOException, UnsupportedEncodingException, InterruptedException {
        int n = -1;
        int n2 = -1;
        if (s.endsWith(ShaderCode.getFileSuffix(false, 35632))) {
            n2 = 2;
            n = 35632;
        }
        else if (s.endsWith(".frag")) {
            n2 = 4;
            n = 35632;
        }
        else if (s.endsWith(ShaderCode.getFileSuffix(false, 35633))) {
            n2 = 2;
            n = 35633;
        }
        else if (s.endsWith(".vert")) {
            n2 = 4;
            n = 35633;
        }
        final String basename = basename(s);
        this.processOneShader(s, dirname(IOUtil.getResource(s, this.getClass().getClassLoader(), null).getURL().getPath()) + File.separator + "bin" + File.separator + ShaderCode.getBinarySubPath(this.getBinaryFormat()) + File.separator + (basename.substring(0, basename.length() - n2) + ShaderCode.getFileSuffix(true, n)), n);
    }
    
    public void processOneShader(final String s, final String s2, final int n) throws IOException, UnsupportedEncodingException, InterruptedException {
        final String dirname = dirname(IOUtil.getResource(s, this.getClass().getClassLoader(), null).getURL().getPath());
        final CharSequence shaderSource = ShaderCode.readShaderSource(null, s, false);
        if (null == shaderSource) {
            System.err.println("Can't find shader source " + s + " - ignored");
            return;
        }
        System.err.println("Preprocessing: " + s + ", in dir: " + dirname);
        final String basename = basename(s);
        String s3 = null;
        switch (n) {
            case 35633: {
                s3 = this.getVertexShaderCompiler();
                break;
            }
            case 35632: {
                s3 = this.getFragmentShaderCompiler();
                break;
            }
            default: {
                throw new GLException("Unknown shader type: " + n);
            }
        }
        final File file = new File(s2);
        final File file2 = new File(dirname + File.separator + "tmp");
        file2.mkdirs();
        final File file3 = new File(file2, basename);
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file3));
        bufferedWriter.write(shaderSource.toString(), 0, shaderSource.length());
        bufferedWriter.flush();
        bufferedWriter.close();
        System.err.println("Preprocessed: " + file3.getAbsolutePath());
        final File sdkCompilerDir = this.getSDKCompilerDir();
        System.err.println("SDK: " + sdkCompilerDir.getAbsolutePath() + ", compiler: " + s3);
        System.err.println("Output: " + file.getAbsolutePath());
        final Process exec = Runtime.getRuntime().exec(new String[] { sdkCompilerDir.getAbsolutePath() + File.separator + s3, file3.getAbsolutePath(), file.getAbsolutePath() });
        new IOUtil.StreamMonitor(new InputStream[] { exec.getInputStream(), exec.getErrorStream() }, System.out, null);
        exec.waitFor();
    }
    
    protected static String basename(final String s) {
        int n = s.lastIndexOf("/");
        if (n < 0) {
            n = s.lastIndexOf("\\");
        }
        String substring;
        if (n < 0) {
            substring = s;
        }
        else {
            substring = s.substring(n + 1);
        }
        return substring;
    }
    
    protected static String dirname(final String s) {
        int n = s.lastIndexOf("/");
        if (n < 0) {
            n = s.lastIndexOf("\\");
        }
        String substring;
        if (n < 0) {
            substring = "";
        }
        else {
            substring = s.substring(0, n + 1);
        }
        return substring;
    }
    
    public void run(final String[] array) {
        try {
            for (int i = 0; i < array.length; ++i) {
                this.processOneShader(array[i]);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
