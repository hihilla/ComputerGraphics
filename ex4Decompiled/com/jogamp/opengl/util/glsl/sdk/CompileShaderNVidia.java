// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl.sdk;

import com.jogamp.opengl.GLException;
import java.io.File;

public class CompileShaderNVidia extends CompileShader
{
    private static final String NVAPSDK;
    
    @Override
    public int getBinaryFormat() {
        return 35083;
    }
    
    @Override
    public File getSDKCompilerDir() {
        File file = new File(CompileShaderNVidia.NVAPSDK + File.separator + "tools" + File.separator);
        File file2 = new File(file, this.getVertexShaderCompiler());
        if (!file2.exists()) {
            file = new File(CompileShaderNVidia.NVAPSDK);
            file2 = new File(file, this.getVertexShaderCompiler());
        }
        if (!file2.exists()) {
            throw new GLException("Can't find compiler: " + this.getVertexShaderCompiler() + " in : " + CompileShaderNVidia.NVAPSDK + ", " + CompileShaderNVidia.NVAPSDK + File.separator + "tools");
        }
        return file;
    }
    
    @Override
    public String getVertexShaderCompiler() {
        return "glslv.bat";
    }
    
    @Override
    public String getFragmentShaderCompiler() {
        return "glslf.bat";
    }
    
    public static void main(final String[] array) {
        new CompileShaderNVidia().run(array);
    }
    
    static {
        final String property = System.getProperty("NVAPSDK");
        if (property != null) {
            NVAPSDK = property;
        }
        else {
            NVAPSDK = "C:\\nvap_sdk_0_3_x";
        }
    }
}
