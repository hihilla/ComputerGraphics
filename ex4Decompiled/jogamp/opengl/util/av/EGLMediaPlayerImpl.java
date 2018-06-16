// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

import java.nio.Buffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.egl.EGLExt;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.egl.EGL;
import com.jogamp.common.nio.Buffers;
import jogamp.opengl.egl.EGLDrawable;
import jogamp.opengl.egl.EGLContext;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.GL;

public abstract class EGLMediaPlayerImpl extends GLMediaPlayerImpl
{
    protected final TextureType texType;
    protected final boolean useKHRSync;
    
    protected EGLMediaPlayerImpl(final TextureType texType, final boolean useKHRSync) {
        this.texType = texType;
        this.useKHRSync = useKHRSync;
    }
    
    @Override
    protected TextureSequence.TextureFrame createTexImage(final GL gl, final int n) {
        final Texture texImageImpl = super.createTexImageImpl(gl, n, this.getWidth(), this.getHeight());
        EGLContext eglContext;
        EGLExt eglExt;
        EGLDrawable eglDrawable;
        if (TextureType.KHRImage == this.texType || this.useKHRSync) {
            eglContext = (EGLContext)gl.getContext();
            eglExt = eglContext.getEGLExt();
            eglDrawable = (EGLDrawable)eglContext.getGLDrawable();
        }
        else {
            eglContext = null;
            eglExt = null;
            eglDrawable = null;
        }
        Buffer buffer;
        long eglCreateImageKHR;
        if (TextureType.KHRImage == this.texType) {
            final IntBuffer directIntBuffer = Buffers.newDirectIntBuffer(1);
            buffer = null;
            directIntBuffer.put(0, 12344);
            eglCreateImageKHR = eglExt.eglCreateImageKHR(eglDrawable.getNativeSurface().getDisplayHandle(), eglContext.getHandle(), 12465, buffer, directIntBuffer);
            if (0L == eglCreateImageKHR) {
                throw new RuntimeException("EGLImage creation failed: " + EGL.eglGetError() + ", ctx " + eglContext + ", tex " + n + ", err " + GLMediaPlayerImpl.toHexString(EGL.eglGetError()));
            }
        }
        else {
            buffer = null;
            eglCreateImageKHR = 0L;
        }
        long eglCreateSyncKHR;
        if (this.useKHRSync) {
            final IntBuffer directIntBuffer2 = Buffers.newDirectIntBuffer(1);
            directIntBuffer2.put(0, 12344);
            eglCreateSyncKHR = eglExt.eglCreateSyncKHR(eglDrawable.getNativeSurface().getDisplayHandle(), 12537, directIntBuffer2);
            if (0L == eglCreateSyncKHR) {
                throw new RuntimeException("EGLSync creation failed: " + EGL.eglGetError() + ", ctx " + eglContext + ", err " + GLMediaPlayerImpl.toHexString(EGL.eglGetError()));
            }
        }
        else {
            eglCreateSyncKHR = 0L;
        }
        return new EGLTextureFrame(buffer, texImageImpl, eglCreateImageKHR, eglCreateSyncKHR);
    }
    
    @Override
    protected void destroyTexFrame(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        EGLExt eglExt;
        EGLDrawable eglDrawable;
        if (TextureType.KHRImage == this.texType || this.useKHRSync) {
            final EGLContext eglContext = (EGLContext)gl.getContext();
            eglExt = eglContext.getEGLExt();
            eglDrawable = (EGLDrawable)eglContext.getGLDrawable();
        }
        else {
            eglExt = null;
            eglDrawable = null;
        }
        final EGLTextureFrame eglTextureFrame = (EGLTextureFrame)textureFrame;
        if (0L != eglTextureFrame.getImage()) {
            eglExt.eglDestroyImageKHR(eglDrawable.getNativeSurface().getDisplayHandle(), eglTextureFrame.getImage());
        }
        if (0L != eglTextureFrame.getSync()) {
            eglExt.eglDestroySyncKHR(eglDrawable.getNativeSurface().getDisplayHandle(), eglTextureFrame.getSync());
        }
        super.destroyTexFrame(gl, textureFrame);
    }
    
    public enum TextureType
    {
        GL(0), 
        KHRImage(1);
        
        public final int id;
        
        private TextureType(final int id) {
            this.id = id;
        }
    }
    
    public static class EGLTextureFrame extends TextureSequence.TextureFrame
    {
        protected final Buffer clientBuffer;
        protected final long image;
        protected final long sync;
        
        public EGLTextureFrame(final Buffer clientBuffer, final Texture texture, final long image, final long sync) {
            super(texture);
            this.clientBuffer = clientBuffer;
            this.image = image;
            this.sync = sync;
        }
        
        public final Buffer getClientBuffer() {
            return this.clientBuffer;
        }
        
        public final long getImage() {
            return this.image;
        }
        
        public final long getSync() {
            return this.sync;
        }
        
        @Override
        public String toString() {
            return "EGLTextureFrame[pts " + this.pts + " ms, l " + this.duration + " ms, texID " + this.texture.getTextureObject() + ", img " + this.image + ", sync " + this.sync + ", clientBuffer " + this.clientBuffer + "]";
        }
    }
}
