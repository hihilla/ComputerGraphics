// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

public interface GLBase
{
    boolean isGL();
    
    boolean isGL4bc();
    
    boolean isGL4();
    
    boolean isGL3bc();
    
    boolean isGL3();
    
    boolean isGL2();
    
    boolean isGLES1();
    
    boolean isGLES2();
    
    boolean isGLES3();
    
    boolean isGLES();
    
    boolean isGL2ES1();
    
    boolean isGL2ES2();
    
    boolean isGL2ES3();
    
    boolean isGL3ES3();
    
    boolean isGL4ES3();
    
    boolean isGL2GL3();
    
    boolean isGL4core();
    
    boolean isGL3core();
    
    boolean isGLcore();
    
    boolean isGLES2Compatible();
    
    boolean isGLES3Compatible();
    
    boolean isGLES31Compatible();
    
    boolean isGLES32Compatible();
    
    boolean hasGLSL();
    
    GL getDownstreamGL() throws GLException;
    
    GL getRootGL() throws GLException;
    
    GL getGL() throws GLException;
    
    GL4bc getGL4bc() throws GLException;
    
    GL4 getGL4() throws GLException;
    
    GL3bc getGL3bc() throws GLException;
    
    GL3 getGL3() throws GLException;
    
    GL2 getGL2() throws GLException;
    
    GLES1 getGLES1() throws GLException;
    
    GLES2 getGLES2() throws GLException;
    
    GLES3 getGLES3() throws GLException;
    
    GL2ES1 getGL2ES1() throws GLException;
    
    GL2ES2 getGL2ES2() throws GLException;
    
    GL2ES3 getGL2ES3() throws GLException;
    
    GL3ES3 getGL3ES3() throws GLException;
    
    GL4ES3 getGL4ES3() throws GLException;
    
    GL2GL3 getGL2GL3() throws GLException;
    
    GLProfile getGLProfile();
    
    GLContext getContext();
    
    boolean isFunctionAvailable(final String p0);
    
    boolean isExtensionAvailable(final String p0);
    
    boolean hasBasicFBOSupport();
    
    boolean hasFullFBOSupport();
    
    int getMaxRenderbufferSamples();
    
    boolean isNPOTTextureAvailable();
    
    boolean isTextureFormatBGRA8888Available();
    
    void setSwapInterval(final int p0) throws GLException;
    
    int getSwapInterval();
    
    Object getPlatformGLExtensions();
    
    Object getExtension(final String p0);
    
    void glClearDepth(final double p0);
    
    void glDepthRange(final double p0, final double p1);
    
    int getBoundBuffer(final int p0);
    
    GLBufferStorage getBufferStorage(final int p0);
    
    GLBufferStorage mapBuffer(final int p0, final int p1) throws GLException;
    
    GLBufferStorage mapBufferRange(final int p0, final long p1, final long p2, final int p3) throws GLException;
    
    boolean isVBOArrayBound();
    
    boolean isVBOElementArrayBound();
    
    int getBoundFramebuffer(final int p0);
    
    int getDefaultDrawFramebuffer();
    
    int getDefaultReadFramebuffer();
    
    int getDefaultReadBuffer();
}
