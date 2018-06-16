// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

import com.jogamp.opengl.util.av.GLMediaPlayer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.GLException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.URLConnection;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.GLProfile;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.GL;
import com.jogamp.common.os.Platform;
import com.jogamp.opengl.util.texture.TextureData;

public class NullGLMediaPlayer extends GLMediaPlayerImpl
{
    private TextureData texData;
    private int pos_ms;
    private long pos_start;
    
    public NullGLMediaPlayer() {
        this.texData = null;
        this.pos_ms = 0;
        this.pos_start = 0L;
    }
    
    @Override
    protected final boolean setPlaySpeedImpl(final float n) {
        return false;
    }
    
    @Override
    protected final boolean playImpl() {
        this.pos_start = Platform.currentTimeMillis();
        return true;
    }
    
    @Override
    protected final boolean pauseImpl() {
        return true;
    }
    
    @Override
    protected final int seekImpl(final int pos_ms) {
        this.pos_ms = pos_ms;
        this.validatePos();
        return this.pos_ms;
    }
    
    @Override
    protected final int getNextTextureImpl(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        final int audioPTSImpl = this.getAudioPTSImpl();
        textureFrame.setPTS(audioPTSImpl);
        return audioPTSImpl;
    }
    
    @Override
    protected final int getAudioPTSImpl() {
        this.pos_ms = (int)(Platform.currentTimeMillis() - this.pos_start);
        this.validatePos();
        return this.pos_ms;
    }
    
    @Override
    protected final void destroyImpl(final GL gl) {
        if (null != this.texData) {
            this.texData.destroy();
            this.texData = null;
        }
    }
    
    public static final TextureData createTestTextureData() {
        TextureData textureData = null;
        try {
            final URLConnection resource = IOUtil.getResource("jogl/util/data/av/test-ntsc01-28x16.png", NullGLMediaPlayer.class.getClassLoader());
            if (null != resource) {
                textureData = TextureIO.newTextureData(GLProfile.getGL2ES2(), resource.getInputStream(), false, "png");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null == textureData) {
            final ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(57600);
            while (directByteBuffer.hasRemaining()) {
                directByteBuffer.put((byte)(-22));
                directByteBuffer.put((byte)(-22));
                directByteBuffer.put((byte)(-22));
                directByteBuffer.put((byte)(-22));
            }
            directByteBuffer.rewind();
            textureData = new TextureData(GLProfile.getGL2ES2(), 6408, 160, 90, 0, 6408, 5121, false, false, false, directByteBuffer, null);
        }
        return textureData;
    }
    
    @Override
    protected final void initStreamImpl(final int n, final int n2) throws IOException {
        this.texData = createTestTextureData();
        this.updateAttributes(0, -2, this.texData.getWidth(), this.texData.getHeight(), 0, 0, 0, 24.0f, 14400, 0, 600000, "png-static", null);
    }
    
    @Override
    protected final void initGLImpl(final GL gl) throws IOException, GLException {
        this.setIsGLOriented(true);
    }
    
    @Override
    protected int validateTextureCount(final int n) {
        return 1;
    }
    
    @Override
    protected final TextureSequence.TextureFrame createTexImage(final GL gl, final int n) {
        final Texture texImageImpl = super.createTexImageImpl(gl, n, this.getWidth(), this.getHeight());
        if (null != this.texData) {
            texImageImpl.updateImage(gl, this.texData);
        }
        return new TextureSequence.TextureFrame(texImageImpl);
    }
    
    @Override
    protected final void destroyTexFrame(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        super.destroyTexFrame(gl, textureFrame);
    }
    
    private void validatePos() {
        boolean b = false;
        if (0 > this.pos_ms) {
            this.pos_ms = 0;
            b = true;
        }
        else if (this.pos_ms > this.getDuration()) {
            this.pos_ms = this.getDuration();
            b = true;
        }
        if (b && GLMediaPlayer.State.Playing == this.getState()) {
            this.setState(GLMediaPlayer.State.Paused);
        }
    }
}
