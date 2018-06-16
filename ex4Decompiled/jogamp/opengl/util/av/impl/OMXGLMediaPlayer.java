// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

import com.jogamp.opengl.egl.EGL;
import java.io.IOException;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import jogamp.opengl.util.av.EGLMediaPlayerImpl;

public class OMXGLMediaPlayer extends EGLMediaPlayerImpl
{
    static final boolean available;
    protected long moviePtr;
    
    public static final boolean isAvailable() {
        return OMXGLMediaPlayer.available;
    }
    
    public OMXGLMediaPlayer() {
        super(TextureType.KHRImage, true);
        this.moviePtr = 0L;
        if (!OMXGLMediaPlayer.available) {
            throw new RuntimeException("OMXGLMediaPlayer not available");
        }
        this.initOMX();
    }
    
    protected void initOMX() {
        this.moviePtr = this._createInstance();
        if (0L == this.moviePtr) {
            throw new GLException("Couldn't create OMXInstance");
        }
    }
    
    @Override
    protected TextureSequence.TextureFrame createTexImage(final GL gl, final int n) {
        final EGLTextureFrame eglTextureFrame = (EGLTextureFrame)super.createTexImage(gl, n);
        this._setStreamEGLImageTexture2D(this.moviePtr, n, eglTextureFrame.getImage(), eglTextureFrame.getSync());
        return eglTextureFrame;
    }
    
    @Override
    protected void destroyTexFrame(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        super.destroyTexFrame(gl, textureFrame);
    }
    
    @Override
    protected void destroyImpl(final GL gl) {
        if (this.moviePtr != 0L) {
            this._stop(this.moviePtr);
            this._detachVideoRenderer(this.moviePtr);
            this._destroyInstance(this.moviePtr);
            this.moviePtr = 0L;
        }
    }
    
    @Override
    protected void initStreamImpl(final int n, final int n2) throws IOException {
        if (0L == this.moviePtr) {
            throw new GLException("OMX native instance null");
        }
        if (!this.getUri().isFileScheme()) {
            throw new IOException("Only file schemes are allowed: " + this.getUri());
        }
        final String decode = this.getUri().path.decode();
        if (OMXGLMediaPlayer.DEBUG) {
            System.out.println("initGLStream: clean path " + decode);
        }
        if (OMXGLMediaPlayer.DEBUG) {
            System.out.println("initGLStream: p1 " + this);
        }
        this._setStream(this.moviePtr, this.getTextureCount(), decode);
        if (OMXGLMediaPlayer.DEBUG) {
            System.out.println("initGLStream: p2 " + this);
        }
    }
    
    @Override
    protected final void initGLImpl(final GL gl) throws IOException, GLException {
        this.setIsGLOriented(true);
    }
    
    @Override
    protected int getAudioPTSImpl() {
        return (0L != this.moviePtr) ? this._getCurrentPosition(this.moviePtr) : 0;
    }
    
    @Override
    protected boolean setPlaySpeedImpl(final float n) {
        if (0L == this.moviePtr) {
            throw new GLException("OMX native instance null");
        }
        this._setPlaySpeed(this.moviePtr, n);
        return true;
    }
    
    public synchronized boolean playImpl() {
        if (0L == this.moviePtr) {
            return false;
        }
        this._play(this.moviePtr);
        return true;
    }
    
    public synchronized boolean pauseImpl() {
        if (0L == this.moviePtr) {
            return false;
        }
        this._pause(this.moviePtr);
        return true;
    }
    
    @Override
    protected int seekImpl(final int n) {
        if (0L == this.moviePtr) {
            throw new GLException("OMX native instance null");
        }
        return this._seek(this.moviePtr, n);
    }
    
    @Override
    protected int getNextTextureImpl(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        if (0L == this.moviePtr) {
            throw new GLException("OMX native instance null");
        }
        if (0 < this._getNextTextureID(this.moviePtr, true)) {}
        return 0;
    }
    
    private String replaceAll(final String s, final String s2, final String s3) {
        final StringBuilder sb = new StringBuilder();
        int n;
        int index;
        for (n = 0; (index = s.indexOf(s2, n)) >= 0; n = index + 1) {
            sb.append(s.substring(n, index));
            sb.append(s3);
        }
        return sb.append(s.substring(n, s.length())).toString();
    }
    
    private void errorCheckEGL(final String s) {
        final int eglGetError;
        if ((eglGetError = EGL.eglGetError()) != 12288) {
            System.out.println("EGL Error: (" + s + "): 0x" + Integer.toHexString(eglGetError));
        }
    }
    
    private static native boolean initIDs0();
    
    private native long _createInstance();
    
    private native void _destroyInstance(final long p0);
    
    private native void _detachVideoRenderer(final long p0);
    
    private native void _attachVideoRenderer(final long p0);
    
    private native void _setStream(final long p0, final int p1, final String p2);
    
    private native void _activateStream(final long p0);
    
    private native void _setStreamEGLImageTexture2D(final long p0, final int p1, final long p2, final long p3);
    
    private native int _seek(final long p0, final int p1);
    
    private native void _setPlaySpeed(final long p0, final float p1);
    
    private native void _play(final long p0);
    
    private native void _pause(final long p0);
    
    private native void _stop(final long p0);
    
    private native int _getNextTextureID(final long p0, final boolean p1);
    
    private native int _getCurrentPosition(final long p0);
    
    static {
        available = false;
    }
}
