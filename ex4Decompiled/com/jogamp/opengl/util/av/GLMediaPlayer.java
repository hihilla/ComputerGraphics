// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.av;

import jogamp.opengl.Debug;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;
import com.jogamp.common.net.Uri;
import com.jogamp.opengl.util.texture.TextureSequence;

public interface GLMediaPlayer extends TextureSequence
{
    public static final boolean DEBUG = Debug.debug("GLMediaPlayer");
    public static final boolean DEBUG_NATIVE = Debug.debug("GLMediaPlayer.Native");
    public static final int TEXTURE_COUNT_DEFAULT = 4;
    public static final int TEXTURE_COUNT_MIN = 1;
    public static final int STREAM_ID_NONE = -2;
    public static final int STREAM_ID_AUTO = -1;
    public static final Uri.Encoded CameraInputScheme = Uri.Encoded.cast("camera");
    public static final String CameraPropSizeS = "size";
    public static final String CameraPropWidth = "width";
    public static final String CameraPropHeight = "height";
    public static final String CameraPropRate = "rate";
    public static final int MAXIMUM_VIDEO_ASYNC = 22;
    
    int getTextureCount();
    
    void setTextureUnit(final int p0);
    
    void setTextureMinMagFilter(final int[] p0);
    
    void setTextureWrapST(final int[] p0);
    
    void initStream(final Uri p0, final int p1, final int p2, final int p3) throws IllegalStateException, IllegalArgumentException;
    
    StreamException getStreamException();
    
    void initGL(final GL p0) throws IllegalStateException, StreamException, GLException;
    
    AudioSink getAudioSink();
    
    State destroy(final GL p0);
    
    boolean setPlaySpeed(final float p0);
    
    float getPlaySpeed();
    
    boolean setAudioVolume(final float p0);
    
    float getAudioVolume();
    
    State play();
    
    State pause(final boolean p0);
    
    int seek(final int p0);
    
    State getState();
    
    int getVID();
    
    int getAID();
    
    int getDecodedFrameCount();
    
    int getPresentedFrameCount();
    
    int getVideoPTS();
    
    int getAudioPTS();
    
    TextureFrame getLastTexture() throws IllegalStateException;
    
    TextureFrame getNextTexture(final GL p0) throws IllegalStateException;
    
    Uri getUri();
    
    String getVideoCodec();
    
    String getAudioCodec();
    
    int getVideoFrames();
    
    int getAudioFrames();
    
    int getDuration();
    
    long getStreamBitrate();
    
    int getVideoBitrate();
    
    int getAudioBitrate();
    
    float getFramerate();
    
    boolean isGLOriented();
    
    int getWidth();
    
    int getHeight();
    
    String toString();
    
    String getPerfString();
    
    void addEventListener(final GLMediaEventListener p0);
    
    void removeEventListener(final GLMediaEventListener p0);
    
    GLMediaEventListener[] getEventListeners();
    
    Object getAttachedObject(final String p0);
    
    Object attachObject(final String p0, final Object p1);
    
    Object detachObject(final String p0);
    
    public static class StreamException extends Exception
    {
        public StreamException(final Throwable t) {
            super(t);
        }
        
        public StreamException(final String s, final Throwable t) {
            super(s, t);
        }
    }
    
    public enum State
    {
        Uninitialized(0), 
        Initialized(1), 
        Playing(2), 
        Paused(3);
        
        public final int id;
        
        private State(final int id) {
            this.id = id;
        }
    }
    
    public interface GLMediaEventListener extends TexSeqEventListener<GLMediaPlayer>
    {
        public static final int EVENT_CHANGE_INIT = 1;
        public static final int EVENT_CHANGE_UNINIT = 2;
        public static final int EVENT_CHANGE_PLAY = 4;
        public static final int EVENT_CHANGE_PAUSE = 8;
        public static final int EVENT_CHANGE_EOS = 16;
        public static final int EVENT_CHANGE_ERR = 32;
        public static final int EVENT_CHANGE_VID = 65536;
        public static final int EVENT_CHANGE_AID = 131072;
        public static final int EVENT_CHANGE_SIZE = 262144;
        public static final int EVENT_CHANGE_FPS = 524288;
        public static final int EVENT_CHANGE_BPS = 1048576;
        public static final int EVENT_CHANGE_LENGTH = 2097152;
        public static final int EVENT_CHANGE_CODEC = 4194304;
        
        void attributesChanged(final GLMediaPlayer p0, final int p1, final long p2);
    }
}
