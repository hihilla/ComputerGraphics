// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.SourcedInterruptedException;
import com.jogamp.opengl.GLCapabilitiesChooser;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.opengl.GLDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.common.util.PropertyAccess;
import java.util.Iterator;
import com.jogamp.common.os.Platform;
import java.nio.Buffer;
import com.jogamp.opengl.util.texture.Texture;
import java.io.IOException;
import com.jogamp.common.util.LFRingbuffer;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.common.net.UriQueryProps;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.glsl.ShaderCode;
import java.util.HashMap;
import java.util.ArrayList;
import com.jogamp.common.util.Ringbuffer;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.opengl.util.av.AudioSink;
import java.util.Map;
import com.jogamp.common.net.Uri;
import com.jogamp.opengl.util.av.GLMediaPlayer;

public abstract class GLMediaPlayerImpl implements GLMediaPlayer
{
    private static final int STREAM_WORKER_DELAY;
    private static final String unknown = "unknown";
    private volatile State state;
    private final Object stateLock;
    private int textureCount;
    private int textureTarget;
    private int textureFormat;
    private int textureInternalFormat;
    private int textureType;
    private int texUnit;
    private int textureFragmentShaderHashCode;
    private final int[] texMinMagFilter;
    private final int[] texWrapST;
    private Uri streamLoc;
    protected Uri.Encoded cameraPath;
    protected Map<String, String> cameraProps;
    private volatile float playSpeed;
    private float audioVolume;
    private int vid;
    private int aid;
    private int width;
    private int height;
    private float fps;
    private float frame_duration;
    private int bps_stream;
    private int bps_video;
    private int bps_audio;
    private int videoFrames;
    private int audioFrames;
    private int duration;
    private String acodec;
    private String vcodec;
    private volatile int decodedFrameCount;
    private int presentedFrameCount;
    private int displayedFrameCount;
    private volatile int video_pts_last;
    private int nullFrameCount;
    private int maxNullFrameCountUntilEOS;
    private static final int MAX_FRAMELESS_MS_UNTIL_EOS = 5000;
    private static final int MAX_FRAMELESS_UNTIL_EOS_DEFAULT = 166;
    protected AudioSink audioSink;
    protected boolean audioSinkPlaySpeedSet;
    private long audio_scr_t0;
    private boolean audioSCR_reset;
    private long video_scr_t0;
    private int video_scr_pts;
    private float video_dpts_cum;
    private int video_dpts_count;
    private static final int VIDEO_DPTS_NUM = 20;
    private static final float VIDEO_DPTS_COEFF = 0.7943282f;
    private static final int VIDEO_DPTS_MAX = 5000;
    private boolean videoSCR_reset;
    private TextureSequence.TextureFrame[] videoFramesOrig;
    private Ringbuffer<TextureSequence.TextureFrame> videoFramesFree;
    private Ringbuffer<TextureSequence.TextureFrame> videoFramesDecoded;
    private volatile TextureSequence.TextureFrame lastFrame;
    private boolean isInGLOrientation;
    private final ArrayList<GLMediaEventListener> eventListeners;
    private TextureSequence.TextureFrame cachedFrame;
    private long lastTimeMillis;
    private final boolean[] stGotVFrame;
    static int StreamWorkerInstanceId;
    private volatile StreamWorker streamWorker;
    private volatile StreamException streamErr;
    private final Object eventListenersLock;
    private final HashMap<String, Object> attachedObjects;
    
    protected GLMediaPlayerImpl() {
        this.stateLock = new Object();
        this.texMinMagFilter = new int[] { 9728, 9728 };
        this.texWrapST = new int[] { 33071, 33071 };
        this.streamLoc = null;
        this.cameraPath = null;
        this.cameraProps = null;
        this.playSpeed = 1.0f;
        this.audioVolume = 1.0f;
        this.vid = -1;
        this.aid = -1;
        this.width = 0;
        this.height = 0;
        this.fps = 0.0f;
        this.frame_duration = 0.0f;
        this.bps_stream = 0;
        this.bps_video = 0;
        this.bps_audio = 0;
        this.videoFrames = 0;
        this.audioFrames = 0;
        this.duration = 0;
        this.acodec = "unknown";
        this.vcodec = "unknown";
        this.decodedFrameCount = 0;
        this.presentedFrameCount = 0;
        this.displayedFrameCount = 0;
        this.video_pts_last = 0;
        this.nullFrameCount = 0;
        this.maxNullFrameCountUntilEOS = 0;
        this.audioSink = null;
        this.audioSinkPlaySpeedSet = false;
        this.audio_scr_t0 = 0L;
        this.audioSCR_reset = true;
        this.video_scr_t0 = 0L;
        this.video_scr_pts = 0;
        this.video_dpts_cum = 0.0f;
        this.video_dpts_count = 0;
        this.videoSCR_reset = false;
        this.videoFramesOrig = null;
        this.videoFramesFree = null;
        this.videoFramesDecoded = null;
        this.lastFrame = null;
        this.isInGLOrientation = false;
        this.eventListeners = new ArrayList<GLMediaEventListener>();
        this.cachedFrame = null;
        this.lastTimeMillis = 0L;
        this.stGotVFrame = new boolean[] { false };
        this.streamWorker = null;
        this.streamErr = null;
        this.eventListenersLock = new Object();
        this.attachedObjects = new HashMap<String, Object>();
        this.textureCount = 0;
        this.textureTarget = 3553;
        this.textureFormat = 6408;
        this.textureInternalFormat = 6408;
        this.textureType = 5121;
        this.texUnit = 0;
        this.textureFragmentShaderHashCode = 0;
        this.state = State.Uninitialized;
    }
    
    @Override
    public final void setTextureUnit(final int texUnit) {
        this.texUnit = texUnit;
    }
    
    @Override
    public final int getTextureUnit() {
        return this.texUnit;
    }
    
    @Override
    public final int getTextureTarget() {
        return this.textureTarget;
    }
    
    protected final int getTextureFormat() {
        return this.textureFormat;
    }
    
    protected final int getTextureType() {
        return this.textureType;
    }
    
    @Override
    public final int getTextureCount() {
        return this.textureCount;
    }
    
    protected final void setTextureTarget(final int textureTarget) {
        this.textureTarget = textureTarget;
    }
    
    protected final void setTextureFormat(final int textureInternalFormat, final int textureFormat) {
        this.textureInternalFormat = textureInternalFormat;
        this.textureFormat = textureFormat;
    }
    
    protected final void setTextureType(final int textureType) {
        this.textureType = textureType;
    }
    
    @Override
    public final void setTextureMinMagFilter(final int[] array) {
        this.texMinMagFilter[0] = array[0];
        this.texMinMagFilter[1] = array[1];
    }
    
    @Override
    public final int[] getTextureMinMagFilter() {
        return this.texMinMagFilter;
    }
    
    @Override
    public final void setTextureWrapST(final int[] array) {
        this.texWrapST[0] = array[0];
        this.texWrapST[1] = array[1];
    }
    
    @Override
    public final int[] getTextureWrapST() {
        return this.texWrapST;
    }
    
    private final void checkGLInit() {
        if (State.Uninitialized == this.state || State.Initialized == this.state) {
            throw new IllegalStateException("GL not initialized: " + this);
        }
    }
    
    @Override
    public String getRequiredExtensionsShaderStub() throws IllegalStateException {
        this.checkGLInit();
        if (36197 == this.textureTarget) {
            return ShaderCode.createExtensionDirective("GL_OES_EGL_image_external", "enable");
        }
        return "";
    }
    
    @Override
    public String getTextureSampler2DType() throws IllegalStateException {
        this.checkGLInit();
        switch (this.textureTarget) {
            case 3553:
            case 34037: {
                return "sampler2D";
            }
            case 36197: {
                return "samplerExternalOES";
            }
            default: {
                throw new GLException("Unsuported texture target: " + toHexString(this.textureTarget));
            }
        }
    }
    
    @Override
    public String getTextureLookupFunctionName(final String s) throws IllegalStateException {
        this.checkGLInit();
        return "texture2D";
    }
    
    @Override
    public String getTextureLookupFragmentShaderImpl() throws IllegalStateException {
        this.checkGLInit();
        return "";
    }
    
    @Override
    public final int getTextureFragmentShaderHashCode() {
        if (!this.isTextureAvailable()) {
            return this.textureFragmentShaderHashCode = 0;
        }
        if (0 == this.textureFragmentShaderHashCode) {
            final int n = 31 + this.getTextureLookupFragmentShaderImpl().hashCode();
            this.textureFragmentShaderHashCode = (n << 5) - n + this.getTextureSampler2DType().hashCode();
        }
        return this.textureFragmentShaderHashCode;
    }
    
    @Override
    public final int getDecodedFrameCount() {
        return this.decodedFrameCount;
    }
    
    @Override
    public final int getPresentedFrameCount() {
        return this.presentedFrameCount;
    }
    
    @Override
    public final int getVideoPTS() {
        return this.video_pts_last;
    }
    
    @Override
    public final int getAudioPTS() {
        if (State.Uninitialized != this.state) {
            return this.getAudioPTSImpl();
        }
        return 0;
    }
    
    protected int getAudioPTSImpl() {
        if (null != this.audioSink) {
            return this.audioSink.getPTS();
        }
        return 0;
    }
    
    @Override
    public final State getState() {
        return this.state;
    }
    
    protected final void setState(final State state) {
        this.state = state;
    }
    
    @Override
    public final State play() {
        synchronized (this.stateLock) {
            final State state = this.state;
            switch (this.state) {
                case Paused: {
                    if (this.playImpl()) {
                        this.resetAVPTS();
                        if (null != this.audioSink) {
                            this.audioSink.play();
                        }
                        if (null != this.streamWorker) {
                            this.streamWorker.doResume();
                        }
                        this.changeState(0, State.Playing);
                        break;
                    }
                    break;
                }
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("Play: " + state + " -> " + this.state + ", " + this.toString());
            }
            return this.state;
        }
    }
    
    protected abstract boolean playImpl();
    
    @Override
    public final State pause(final boolean b) {
        return this.pauseImpl(b, 0);
    }
    
    private final State pauseImpl(final boolean b, int addStateEventMask) {
        synchronized (this.stateLock) {
            final State state = this.state;
            if (State.Playing == this.state) {
                addStateEventMask = this.addStateEventMask(addStateEventMask, State.Paused);
                this.setState(State.Paused);
                if (null != this.streamWorker) {
                    this.streamWorker.doPause(true);
                }
                if (b) {
                    this.resetAVPTSAndFlush();
                }
                else if (null != this.audioSink) {
                    this.audioSink.pause();
                }
                this.attributesUpdated(addStateEventMask);
                if (!this.pauseImpl()) {
                    this.play();
                }
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("Pause: " + state + " -> " + this.state + ", " + this.toString());
            }
            return this.state;
        }
    }
    
    protected abstract boolean pauseImpl();
    
    @Override
    public final State destroy(final GL gl) {
        return this.destroyImpl(gl, 0);
    }
    
    private final State destroyImpl(final GL gl, final int n) {
        synchronized (this.stateLock) {
            if (null != this.streamWorker) {
                this.streamWorker.doStop();
                this.streamWorker = null;
            }
            this.destroyImpl(gl);
            this.removeAllTextureFrames(gl);
            this.textureCount = 0;
            this.changeState(n, State.Uninitialized);
            this.attachedObjects.clear();
            return this.state;
        }
    }
    
    protected abstract void destroyImpl(final GL p0);
    
    @Override
    public final int seek(int n) {
        synchronized (this.stateLock) {
            final State state = this.state;
            int seekImpl = 0;
            switch (this.state) {
                case Paused:
                case Playing: {
                    final State state2 = this.state;
                    this.setState(State.Paused);
                    if (null != this.streamWorker) {
                        this.streamWorker.doPause(true);
                    }
                    if (n >= this.duration) {
                        n = this.duration - (int)Math.floor(this.frame_duration);
                    }
                    else if (n < 0) {
                        n = 0;
                    }
                    seekImpl = this.seekImpl(n);
                    this.resetAVPTSAndFlush();
                    if (null != this.audioSink && State.Playing == state2) {
                        this.audioSink.play();
                    }
                    if (GLMediaPlayerImpl.DEBUG) {
                        System.err.println("Seek(" + n + "): " + this.getPerfString());
                    }
                    if (null != this.streamWorker) {
                        this.streamWorker.doResume();
                    }
                    this.setState(state2);
                    break;
                }
                default: {
                    seekImpl = 0;
                    break;
                }
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("Seek(" + n + "): " + state + " -> " + this.state + ", " + this.toString());
            }
            return seekImpl;
        }
    }
    
    protected abstract int seekImpl(final int p0);
    
    @Override
    public final float getPlaySpeed() {
        return this.playSpeed;
    }
    
    @Override
    public final boolean setPlaySpeed(float n) {
        synchronized (this.stateLock) {
            final float playSpeed = this.playSpeed;
            boolean b = false;
            if (State.Uninitialized != this.state && n > 0.01f) {
                if (Math.abs(1.0f - n) < 0.01f) {
                    n = 1.0f;
                }
                if (this.setPlaySpeedImpl(n)) {
                    this.resetAVPTS();
                    this.playSpeed = n;
                    b = true;
                }
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("setPlaySpeed(" + n + "): " + this.state + ", " + playSpeed + " -> " + this.playSpeed + ", " + this.toString());
            }
            return b;
        }
    }
    
    protected boolean setPlaySpeedImpl(final float playSpeed) {
        if (null != this.audioSink) {
            this.audioSinkPlaySpeedSet = this.audioSink.setPlaySpeed(playSpeed);
        }
        return true;
    }
    
    @Override
    public final float getAudioVolume() {
        this.getAudioVolumeImpl();
        return this.audioVolume;
    }
    
    protected void getAudioVolumeImpl() {
        if (null != this.audioSink) {
            this.audioVolume = this.audioSink.getVolume();
        }
    }
    
    @Override
    public boolean setAudioVolume(float n) {
        synchronized (this.stateLock) {
            final float audioVolume = this.audioVolume;
            boolean b = false;
            if (State.Uninitialized != this.state) {
                if (Math.abs(n) < 0.01f) {
                    n = 0.0f;
                }
                else if (Math.abs(1.0f - n) < 0.01f) {
                    n = 1.0f;
                }
                if (this.setAudioVolumeImpl(n)) {
                    this.audioVolume = n;
                    b = true;
                }
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("setAudioVolume(" + n + "): " + this.state + ", " + audioVolume + " -> " + this.audioVolume + ", " + this.toString());
            }
            return b;
        }
    }
    
    protected boolean setAudioVolumeImpl(final float volume) {
        return null == this.audioSink || this.audioSink.setVolume(volume);
    }
    
    @Override
    public final void initStream(final Uri streamLoc, final int vid, final int aid, final int n) throws IllegalStateException, IllegalArgumentException {
        synchronized (this.stateLock) {
            if (State.Uninitialized != this.state) {
                throw new IllegalStateException("Instance not in state unintialized: " + this);
            }
            if (null == streamLoc) {
                throw new IllegalArgumentException("streamLock is null");
            }
            if (-2 != vid) {
                this.textureCount = this.validateTextureCount(n);
                if (this.textureCount < 1) {
                    throw new InternalError("Validated texture count < 1: " + this.textureCount);
                }
            }
            else {
                this.textureCount = 0;
            }
            this.decodedFrameCount = 0;
            this.presentedFrameCount = 0;
            this.displayedFrameCount = 0;
            this.nullFrameCount = 0;
            this.maxNullFrameCountUntilEOS = 166;
            this.streamLoc = streamLoc;
            this.cameraPath = null;
            this.cameraProps = null;
            final Uri.Encoded scheme = streamLoc.scheme;
            if (null != scheme && scheme.equals(GLMediaPlayerImpl.CameraInputScheme)) {
                final Uri.Encoded path = streamLoc.path;
                if (null == path || path.length() <= 0) {
                    throw new IllegalArgumentException("Camera path is empty: " + streamLoc.toString());
                }
                this.cameraPath = path.substring(1);
                this.cameraProps = UriQueryProps.create(streamLoc, ';').getProperties();
            }
            this.vid = vid;
            this.aid = aid;
            new InterruptSource.Thread() {
                @Override
                public void run() {
                    try {
                        GLMediaPlayerImpl.this.initStreamImpl(vid, aid);
                    }
                    catch (Throwable t) {
                        GLMediaPlayerImpl.this.streamErr = new StreamException(t.getClass().getSimpleName() + " while initializing: " + GLMediaPlayerImpl.this.toString(), t);
                        GLMediaPlayerImpl.this.changeState(32, GLMediaPlayer.State.Uninitialized);
                    }
                }
            }.start();
        }
    }
    
    protected abstract void initStreamImpl(final int p0, final int p1) throws Exception;
    
    @Override
    public final StreamException getStreamException() {
        final StreamException streamErr;
        synchronized (this.stateLock) {
            streamErr = this.streamErr;
            this.streamErr = null;
        }
        return streamErr;
    }
    
    @Override
    public final void initGL(final GL gl) throws IllegalStateException, StreamException, GLException {
        synchronized (this.stateLock) {
            if (State.Initialized != this.state) {
                throw new IllegalStateException("Stream not in state initialized: " + this);
            }
            if (null != this.streamWorker) {
                final StreamException streamException = this.getStreamException();
                if (null != streamException) {
                    this.streamWorker = null;
                    this.destroy(null);
                    throw streamException;
                }
            }
            try {
                if (-2 != this.vid) {
                    this.removeAllTextureFrames(gl);
                    this.initGLImpl(gl);
                    if (GLMediaPlayerImpl.DEBUG) {
                        System.err.println("initGLImpl.X " + this);
                    }
                    this.videoFramesOrig = this.createTexFrames(gl, this.textureCount);
                    if (1 == this.textureCount) {
                        this.videoFramesFree = null;
                        this.videoFramesDecoded = null;
                        this.lastFrame = this.videoFramesOrig[0];
                    }
                    else {
                        this.videoFramesFree = new LFRingbuffer<TextureSequence.TextureFrame>(this.videoFramesOrig);
                        this.videoFramesDecoded = new LFRingbuffer<TextureSequence.TextureFrame>(TextureSequence.TextureFrame[].class, this.textureCount);
                        this.lastFrame = this.videoFramesFree.getBlocking();
                    }
                    if (null != this.streamWorker) {
                        this.streamWorker.initGL(gl);
                    }
                }
                else {
                    this.removeAllTextureFrames(null);
                    this.initGLImpl(null);
                    this.setTextureFormat(-1, -1);
                    this.setTextureType(-1);
                    this.videoFramesOrig = null;
                    this.videoFramesFree = null;
                    this.videoFramesDecoded = null;
                    this.lastFrame = null;
                }
                this.changeState(0, State.Paused);
            }
            catch (Throwable t) {
                this.destroyImpl(gl, 32);
                throw new GLException("Error initializing GL resources", t);
            }
        }
    }
    
    protected abstract void initGLImpl(final GL p0) throws IOException, GLException;
    
    protected int validateTextureCount(final int n) {
        return (n < 1) ? 1 : n;
    }
    
    protected TextureSequence.TextureFrame[] createTexFrames(final GL gl, final int n) {
        final int[] array = new int[n];
        gl.glGenTextures(n, array, 0);
        final int glGetError = gl.glGetError();
        if (glGetError != 0) {
            throw new RuntimeException("TextureNames creation failed (num: " + n + "): err " + toHexString(glGetError));
        }
        final TextureSequence.TextureFrame[] array2 = new TextureSequence.TextureFrame[n];
        for (int i = 0; i < n; ++i) {
            array2[i] = this.createTexImage(gl, array[i]);
        }
        return array2;
    }
    
    protected abstract TextureSequence.TextureFrame createTexImage(final GL p0, final int p1);
    
    protected final Texture createTexImageImpl(final GL gl, final int n, final int n2, final int n3) {
        if (0 > n) {
            throw new RuntimeException("TextureName " + toHexString(n) + " invalid.");
        }
        gl.glActiveTexture(33984 + this.getTextureUnit());
        gl.glBindTexture(this.textureTarget, n);
        final int glGetError = gl.glGetError();
        if (glGetError != 0) {
            throw new RuntimeException("Couldn't bind textureName " + toHexString(n) + " to 2D target, err " + toHexString(glGetError));
        }
        if (36197 != this.textureTarget) {
            gl.glTexImage2D(this.textureTarget, 0, this.textureInternalFormat, n2, n3, 0, this.textureFormat, this.textureType, null);
            final int glGetError2 = gl.glGetError();
            if (glGetError2 != 0) {
                throw new RuntimeException("Couldn't create TexImage2D RGBA " + n2 + "x" + n3 + ", target " + toHexString(this.textureTarget) + ", ifmt " + toHexString(this.textureInternalFormat) + ", fmt " + toHexString(this.textureFormat) + ", type " + toHexString(this.textureType) + ", err " + toHexString(glGetError2));
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("Created TexImage2D RGBA " + n2 + "x" + n3 + ", target " + toHexString(this.textureTarget) + ", ifmt " + toHexString(this.textureInternalFormat) + ", fmt " + toHexString(this.textureFormat) + ", type " + toHexString(this.textureType));
            }
        }
        gl.glTexParameteri(this.textureTarget, 10241, this.texMinMagFilter[0]);
        gl.glTexParameteri(this.textureTarget, 10240, this.texMinMagFilter[1]);
        gl.glTexParameteri(this.textureTarget, 10242, this.texWrapST[0]);
        gl.glTexParameteri(this.textureTarget, 10243, this.texWrapST[1]);
        return new Texture(n, this.textureTarget, n2, n3, this.width, this.height, !this.isInGLOrientation);
    }
    
    protected void destroyTexFrame(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        textureFrame.getTexture().destroy(gl);
    }
    
    @Override
    public final boolean isTextureAvailable() {
        return State.Paused == this.state || State.Playing == this.state;
    }
    
    @Override
    public final TextureSequence.TextureFrame getLastTexture() throws IllegalStateException {
        if (State.Paused != this.state && State.Playing != this.state) {
            throw new IllegalStateException("Instance not paused or playing: " + this);
        }
        return this.lastFrame;
    }
    
    private final void removeAllTextureFrames(final GL gl) {
        final TextureSequence.TextureFrame[] videoFramesOrig = this.videoFramesOrig;
        this.videoFramesOrig = null;
        this.videoFramesFree = null;
        this.videoFramesDecoded = null;
        this.lastFrame = null;
        if (null != videoFramesOrig) {
            for (int i = 0; i < videoFramesOrig.length; ++i) {
                final TextureSequence.TextureFrame textureFrame = videoFramesOrig[i];
                if (null != textureFrame) {
                    if (null != gl) {
                        this.destroyTexFrame(gl, textureFrame);
                    }
                    videoFramesOrig[i] = null;
                }
                if (GLMediaPlayerImpl.DEBUG) {
                    System.err.println(Thread.currentThread().getName() + "> Clear TexFrame[" + i + "]: " + textureFrame + " -> null");
                }
            }
        }
    }
    
    @Override
    public final TextureSequence.TextureFrame getNextTexture(final GL gl) throws IllegalStateException {
        synchronized (this.stateLock) {
            if (State.Paused != this.state && State.Playing != this.state) {
                throw new IllegalStateException("Instance not paused or playing: " + this);
            }
            if (State.Playing == this.state) {
                boolean b = false;
                try {
                    do {
                        boolean b2;
                        if (b) {
                            --this.presentedFrameCount;
                            b = false;
                            b2 = true;
                        }
                        else {
                            b2 = false;
                        }
                        final boolean b3 = null != this.cachedFrame;
                        TextureSequence.TextureFrame textureFrame;
                        int n;
                        boolean b4;
                        if (b3) {
                            textureFrame = this.cachedFrame;
                            this.cachedFrame = null;
                            --this.presentedFrameCount;
                            n = textureFrame.getPTS();
                            b4 = true;
                        }
                        else if (null != this.videoFramesDecoded) {
                            textureFrame = this.videoFramesDecoded.get();
                            if (null != textureFrame) {
                                n = textureFrame.getPTS();
                                b4 = true;
                            }
                            else {
                                n = Integer.MIN_VALUE;
                                b4 = false;
                            }
                        }
                        else {
                            n = this.getNextSingleThreaded(gl, this.lastFrame, this.stGotVFrame);
                            textureFrame = this.lastFrame;
                            b4 = this.stGotVFrame[0];
                        }
                        final long currentTimeMillis = Platform.currentTimeMillis();
                        if (Integer.MAX_VALUE == n || (this.duration > 0 && this.duration <= n) || this.maxNullFrameCountUntilEOS <= this.nullFrameCount) {
                            if (GLMediaPlayerImpl.DEBUG) {
                                System.err.println("AV-EOS (getNextTexture): EOS_PTS " + (Integer.MAX_VALUE == n) + ", " + this);
                            }
                            this.pauseImpl(true, 16);
                        }
                        else if (Integer.MIN_VALUE == n) {
                            if (null == this.videoFramesDecoded || !this.videoFramesDecoded.isEmpty()) {
                                ++this.nullFrameCount;
                            }
                            if (GLMediaPlayerImpl.DEBUG) {
                                final int audioPTSImpl = this.getAudioPTSImpl();
                                final int n2 = (int)((currentTimeMillis - this.audio_scr_t0) * this.playSpeed);
                                int n3;
                                if (audioPTSImpl != Integer.MIN_VALUE) {
                                    n3 = audioPTSImpl - n2;
                                }
                                else {
                                    n3 = 0;
                                }
                                final int n4 = this.video_scr_pts + (int)((currentTimeMillis - this.video_scr_t0) * this.playSpeed);
                                System.err.println("AV~: dT " + (currentTimeMillis - this.lastTimeMillis) + ", nullFrames " + this.nullFrameCount + this.getPerfStringImpl(n4, n, n - n4, n2, audioPTSImpl, n3, 0) + ", droppedFrame " + b2);
                            }
                        }
                        else {
                            this.nullFrameCount = 0;
                            if (b4) {
                                ++this.presentedFrameCount;
                                final int audioPTSImpl2 = this.getAudioPTSImpl();
                                final int n5 = (int)((currentTimeMillis - this.audio_scr_t0) * this.playSpeed);
                                int n6;
                                if (audioPTSImpl2 != Integer.MIN_VALUE) {
                                    n6 = audioPTSImpl2 - n5;
                                }
                                else {
                                    n6 = 0;
                                }
                                final int n7 = n - this.video_pts_last;
                                if (this.videoSCR_reset || n7 > this.frame_duration * 10.0f) {
                                    this.videoSCR_reset = false;
                                    this.video_scr_t0 = currentTimeMillis;
                                    this.video_scr_pts = n;
                                }
                                final int n8 = this.video_scr_pts + (int)((currentTimeMillis - this.video_scr_t0) * this.playSpeed);
                                final int n9 = n - n8;
                                if (-5000 > n9 || n9 > 5000) {
                                    if (GLMediaPlayerImpl.DEBUG) {
                                        System.err.println("AV*: dT " + (currentTimeMillis - this.lastTimeMillis) + ", " + this.getPerfStringImpl(n8, n, n9, n5, audioPTSImpl2, n6, 0) + ", " + textureFrame + ", playCached " + b3 + ", dropFrame " + b);
                                    }
                                }
                                else {
                                    final int n10 = (int)(currentTimeMillis - this.video_scr_t0) / ((this.displayedFrameCount > 0) ? this.displayedFrameCount : 1);
                                    final int min = Math.min(n10, 22);
                                    ++this.video_dpts_count;
                                    this.video_dpts_cum = n9 + 0.7943282f * this.video_dpts_cum;
                                    final int n11 = (this.video_dpts_count >= 20) ? this.getVideoDPTSAvg() : 0;
                                    final int n12 = (int)(n11 / this.playSpeed + 0.5f);
                                    final TextureSequence.TextureFrame textureFrame2 = textureFrame;
                                    if (n12 > min) {
                                        this.cachedFrame = textureFrame;
                                        textureFrame = null;
                                    }
                                    else if (!b2 && n12 < -min && null != this.videoFramesDecoded && this.videoFramesDecoded.size() > 0) {
                                        b = true;
                                    }
                                    this.video_pts_last = n;
                                    if (GLMediaPlayerImpl.DEBUG) {
                                        System.err.println("AV_: dT " + (currentTimeMillis - this.lastTimeMillis) + ", " + this.getPerfStringImpl(n8, n, n9, n5, audioPTSImpl2, n6, n11) + ", avg dpy-fps " + n10 + " ms/f, maxD " + min + " ms, " + textureFrame2 + ", playCached " + b3 + ", dropFrame " + b);
                                    }
                                }
                            }
                        }
                        if (null != this.videoFramesFree && null != textureFrame) {
                            final TextureSequence.TextureFrame lastFrame = this.lastFrame;
                            this.lastFrame = textureFrame;
                            if (null != lastFrame) {
                                this.videoFramesFree.putBlocking(lastFrame);
                            }
                        }
                        this.lastTimeMillis = currentTimeMillis;
                    } while (b);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            ++this.displayedFrameCount;
            return this.lastFrame;
        }
    }
    
    protected void preNextTextureImpl(final GL gl) {
    }
    
    protected void postNextTextureImpl(final GL gl) {
    }
    
    protected abstract int getNextTextureImpl(final GL p0, final TextureSequence.TextureFrame p1) throws InterruptedException;
    
    protected final int getNextSingleThreaded(final GL gl, final TextureSequence.TextureFrame textureFrame, final boolean[] array) throws InterruptedException {
        int n;
        if (-2 != this.vid) {
            this.preNextTextureImpl(gl);
            n = this.getNextTextureImpl(gl, textureFrame);
            this.postNextTextureImpl(gl);
            if (Integer.MIN_VALUE != n) {
                this.newFrameAvailable(textureFrame, Platform.currentTimeMillis());
                array[0] = true;
            }
            else {
                array[0] = false;
            }
        }
        else {
            n = this.getNextTextureImpl(null, null);
            array[0] = false;
        }
        return n;
    }
    
    @Override
    public final AudioSink getAudioSink() {
        return this.audioSink;
    }
    
    protected void setFirstAudioPTS2SCR(final int n) {
        if (this.audioSCR_reset) {
            this.audio_scr_t0 = Platform.currentTimeMillis() - n;
            this.audioSCR_reset = false;
        }
    }
    
    private void flushAllVideoFrames() {
        if (null != this.videoFramesFree) {
            this.videoFramesFree.resetFull(this.videoFramesOrig);
            this.lastFrame = this.videoFramesFree.get();
            if (null == this.lastFrame) {
                throw new InternalError("XXX");
            }
            this.videoFramesDecoded.clear();
        }
        this.cachedFrame = null;
    }
    
    private void resetAVPTSAndFlush() {
        this.video_dpts_cum = 0.0f;
        this.video_dpts_count = 0;
        this.resetAVPTS();
        this.flushAllVideoFrames();
        if (null != this.audioSink) {
            this.audioSink.flush();
        }
    }
    
    private void resetAVPTS() {
        this.nullFrameCount = 0;
        this.presentedFrameCount = 0;
        this.displayedFrameCount = 0;
        this.decodedFrameCount = 0;
        this.audioSCR_reset = true;
        this.videoSCR_reset = true;
    }
    
    private final int getVideoDPTSAvg() {
        return (int)(this.video_dpts_cum * 0.20567179f + 0.5f);
    }
    
    private final void newFrameAvailable(final TextureSequence.TextureFrame textureFrame, final long n) {
        ++this.decodedFrameCount;
        if (0 == textureFrame.getDuration()) {
            textureFrame.setDuration((int)this.frame_duration);
        }
        synchronized (this.eventListenersLock) {
            final Iterator<GLMediaEventListener> iterator = this.eventListeners.iterator();
            while (iterator.hasNext()) {
                ((TextureSequence.TexSeqEventListener<GLMediaPlayerImpl>)iterator.next()).newFrameAvailable(this, textureFrame, n);
            }
        }
    }
    
    protected final int addStateEventMask(int n, final State state) {
        if (this.state != state) {
            switch (state) {
                case Uninitialized: {
                    n |= 0x2;
                    break;
                }
                case Initialized: {
                    n |= 0x1;
                    break;
                }
                case Playing: {
                    n |= 0x4;
                    break;
                }
                case Paused: {
                    n |= 0x8;
                    break;
                }
            }
        }
        return n;
    }
    
    protected final void attributesUpdated(final int n) {
        if (0 != n) {
            final long currentTimeMillis = Platform.currentTimeMillis();
            synchronized (this.eventListenersLock) {
                final Iterator<GLMediaEventListener> iterator = this.eventListeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().attributesChanged(this, n, currentTimeMillis);
                }
            }
        }
    }
    
    protected final void changeState(int addStateEventMask, final State state) {
        addStateEventMask = this.addStateEventMask(addStateEventMask, state);
        if (0 != addStateEventMask) {
            this.setState(state);
            if (!this.isTextureAvailable()) {
                this.textureFragmentShaderHashCode = 0;
            }
            this.attributesUpdated(addStateEventMask);
        }
    }
    
    protected final void updateAttributes(int vid, final int aid, final int width, final int height, final int bps_stream, final int bps_video, final int bps_audio, final float fps, final int videoFrames, final int audioFrames, final int duration, final String vcodec, final String acodec) {
        int n = 0;
        final boolean b = this.state == State.Uninitialized;
        if (b) {
            n |= 0x1;
            this.setState(State.Initialized);
        }
        if (-1 == vid) {
            vid = -2;
        }
        if (this.vid != vid) {
            n |= 0x10000;
            this.vid = vid;
        }
        if (-1 == vid) {
            vid = -2;
        }
        if (this.aid != aid) {
            n |= 0x20000;
            this.aid = aid;
        }
        if (this.width != width || this.height != height) {
            n |= 0x40000;
            this.width = width;
            this.height = height;
        }
        if (this.fps != fps) {
            n |= 0x80000;
            this.fps = fps;
            if (0.0f != fps) {
                this.frame_duration = 1000.0f / fps;
                final int n2 = (int)this.frame_duration;
                if (0 < n2) {
                    this.maxNullFrameCountUntilEOS = 5000 / n2;
                }
                else {
                    this.maxNullFrameCountUntilEOS = 166;
                }
            }
            else {
                this.frame_duration = 0.0f;
                this.maxNullFrameCountUntilEOS = 166;
            }
        }
        if (this.bps_stream != bps_stream || this.bps_video != bps_video || this.bps_audio != bps_audio) {
            n |= 0x100000;
            this.bps_stream = bps_stream;
            this.bps_video = bps_video;
            this.bps_audio = bps_audio;
        }
        if (this.videoFrames != videoFrames || this.audioFrames != audioFrames || this.duration != duration) {
            n |= 0x200000;
            this.videoFrames = videoFrames;
            this.audioFrames = audioFrames;
            this.duration = duration;
        }
        if (null != acodec && acodec.length() > 0 && !this.acodec.equals(acodec)) {
            n |= 0x400000;
            this.acodec = acodec;
        }
        if (null != vcodec && vcodec.length() > 0 && !this.vcodec.equals(vcodec)) {
            n |= 0x400000;
            this.vcodec = vcodec;
        }
        if (n == 0) {
            return;
        }
        if (b) {
            if (null != this.streamWorker) {
                throw new InternalError("XXX: StreamWorker not null - " + this);
            }
            if (1 < this.textureCount || -2 == vid) {
                this.streamWorker = new StreamWorker();
            }
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("XXX Initialize @ updateAttributes: " + this);
            }
        }
        this.attributesUpdated(n);
    }
    
    protected void setIsGLOriented(final boolean isInGLOrientation) {
        if (this.isInGLOrientation != isInGLOrientation) {
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("XXX gl-orient " + this.isInGLOrientation + " -> " + isInGLOrientation);
            }
            this.isInGLOrientation = isInGLOrientation;
            if (null != this.videoFramesOrig) {
                for (int i = 0; i < this.videoFramesOrig.length; ++i) {
                    this.videoFramesOrig[i].getTexture().setMustFlipVertically(!isInGLOrientation);
                }
                this.attributesUpdated(262144);
            }
        }
    }
    
    @Override
    public final Uri getUri() {
        return this.streamLoc;
    }
    
    @Override
    public final int getVID() {
        return this.vid;
    }
    
    @Override
    public final int getAID() {
        return this.aid;
    }
    
    @Override
    public final String getVideoCodec() {
        return this.vcodec;
    }
    
    @Override
    public final String getAudioCodec() {
        return this.acodec;
    }
    
    @Override
    public final int getVideoFrames() {
        return this.videoFrames;
    }
    
    @Override
    public final int getAudioFrames() {
        return this.audioFrames;
    }
    
    @Override
    public final int getDuration() {
        return this.duration;
    }
    
    @Override
    public final long getStreamBitrate() {
        return this.bps_stream;
    }
    
    @Override
    public final int getVideoBitrate() {
        return this.bps_video;
    }
    
    @Override
    public final int getAudioBitrate() {
        return this.bps_audio;
    }
    
    @Override
    public final float getFramerate() {
        return this.fps;
    }
    
    @Override
    public final boolean isGLOriented() {
        return this.isInGLOrientation;
    }
    
    @Override
    public final int getWidth() {
        return this.width;
    }
    
    @Override
    public final int getHeight() {
        return this.height;
    }
    
    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + "[" + this.state + ", vSCR " + (this.video_scr_pts + (int)((Platform.currentTimeMillis() - this.video_scr_t0) * this.playSpeed)) + ", frames[p " + this.presentedFrameCount + ", d " + this.decodedFrameCount + ", t " + this.videoFrames + " (" + this.getDuration() / 1000.0f + " s), z " + this.nullFrameCount + " / " + this.maxNullFrameCountUntilEOS + "], " + "speed " + this.playSpeed + ", " + this.bps_stream + " bps, hasSW " + (null != this.streamWorker) + ", Texture[count " + this.textureCount + ", free " + ((null != this.videoFramesFree) ? this.videoFramesFree.size() : 0) + ", dec " + ((null != this.videoFramesDecoded) ? this.videoFramesDecoded.size() : 0) + ", tagt " + toHexString(this.textureTarget) + ", ifmt " + toHexString(this.textureInternalFormat) + ", fmt " + toHexString(this.textureFormat) + ", type " + toHexString(this.textureType) + "], " + "Video[id " + this.vid + ", <" + this.vcodec + ">, " + this.width + "x" + this.height + ", glOrient " + this.isInGLOrientation + ", " + this.fps + " fps, " + this.frame_duration + " fdur, " + this.bps_video + " bps], " + "Audio[id " + this.aid + ", <" + this.acodec + ">, " + this.bps_audio + " bps, " + this.audioFrames + " frames], uri " + ((null != this.streamLoc) ? this.streamLoc.toString() : "<undefined stream>") + ((null != this.cameraPath) ? (", camera: " + (Object)this.cameraPath) : "") + "]";
    }
    
    @Override
    public final String getPerfString() {
        final long currentTimeMillis = Platform.currentTimeMillis();
        final int n = this.video_scr_pts + (int)((currentTimeMillis - this.video_scr_t0) * this.playSpeed);
        final int n2 = this.video_pts_last - n;
        final int n3 = (int)((currentTimeMillis - this.audio_scr_t0) * this.playSpeed);
        final int audioPTSImpl = this.getAudioPTSImpl();
        return this.getPerfStringImpl(n, this.video_pts_last, n2, n3, audioPTSImpl, audioPTSImpl - n3, this.getVideoDPTSAvg());
    }
    
    private final String getPerfStringImpl(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final float n8 = this.getDuration() / 1000.0f;
        final AudioSink audioSink = this.getAudioSink();
        String string;
        if (null != audioSink) {
            string = "AudioSink[frames [p " + audioSink.getEnqueuedFrameCount() + ", q " + audioSink.getQueuedFrameCount() + ", f " + audioSink.getFreeFrameCount() + ", c " + audioSink.getFrameCount() + "], time " + audioSink.getQueuedTime() + ", bytes " + audioSink.getQueuedByteCount() + "]";
        }
        else {
            string = "";
        }
        int size;
        int size2;
        if (null != this.videoFramesFree) {
            size = this.videoFramesFree.size();
            size2 = this.videoFramesDecoded.size();
        }
        else {
            size = 0;
            size2 = 0;
        }
        return this.state + ", frames[(p " + this.presentedFrameCount + ", d " + this.decodedFrameCount + ") / " + this.videoFrames + ", " + n8 + " s, z " + this.nullFrameCount + " / " + this.maxNullFrameCountUntilEOS + "], " + "speed " + this.playSpeed + ", dAV " + (n3 - n6) + ", vSCR " + n + ", vpts " + n2 + ", dSCR[" + n3 + ", avrg " + n7 + "], " + "aSCR " + n4 + ", apts " + n5 + " ( " + n6 + " ), " + string + ", Texture[count " + this.textureCount + ", free " + size + ", dec " + size2 + "]";
    }
    
    @Override
    public final void addEventListener(final GLMediaEventListener glMediaEventListener) {
        if (glMediaEventListener == null) {
            return;
        }
        synchronized (this.eventListenersLock) {
            this.eventListeners.add(glMediaEventListener);
        }
    }
    
    @Override
    public final void removeEventListener(final GLMediaEventListener glMediaEventListener) {
        if (glMediaEventListener == null) {
            return;
        }
        synchronized (this.eventListenersLock) {
            this.eventListeners.remove(glMediaEventListener);
        }
    }
    
    @Override
    public final GLMediaEventListener[] getEventListeners() {
        synchronized (this.eventListenersLock) {
            return this.eventListeners.toArray(new GLMediaEventListener[this.eventListeners.size()]);
        }
    }
    
    @Override
    public final Object getAttachedObject(final String s) {
        return this.attachedObjects.get(s);
    }
    
    @Override
    public final Object attachObject(final String s, final Object o) {
        return this.attachedObjects.put(s, o);
    }
    
    @Override
    public final Object detachObject(final String s) {
        return this.attachedObjects.remove(s);
    }
    
    protected static final String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    protected static final String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    protected static final int getPropIntVal(final Map<String, String> map, final String s) {
        final String s2 = map.get(s);
        try {
            return Integer.parseInt(s2);
        }
        catch (NumberFormatException ex) {
            if (GLMediaPlayerImpl.DEBUG) {
                System.err.println("Not a valid integer for <" + s + ">: <" + s2 + ">");
            }
            return 0;
        }
    }
    
    static {
        STREAM_WORKER_DELAY = PropertyAccess.getIntProperty("jogl.debug.GLMediaPlayer.StreamWorker.delay", false, 0);
        GLMediaPlayerImpl.StreamWorkerInstanceId = 0;
    }
    
    class StreamWorker extends InterruptSource.Thread
    {
        private volatile boolean isRunning;
        private volatile boolean isActive;
        private volatile boolean isBlocked;
        private volatile boolean shallPause;
        private volatile boolean shallStop;
        private volatile GLContext sharedGLCtx;
        private boolean sharedGLCtxCurrent;
        private GLDrawable dummyDrawable;
        
        StreamWorker() {
            this.isRunning = false;
            this.isActive = false;
            this.isBlocked = false;
            this.shallPause = true;
            this.shallStop = false;
            this.sharedGLCtx = null;
            this.sharedGLCtxCurrent = false;
            this.dummyDrawable = null;
            this.setDaemon(true);
            synchronized (this) {
                this.start();
                try {
                    this.notifyAll();
                    while (!this.isRunning && !this.shallStop) {
                        this.wait();
                    }
                }
                catch (InterruptedException ex) {
                    throw new InterruptedRuntimeException(ex);
                }
            }
        }
        
        private void makeCurrent(final GLContext glContext) {
            if (0 >= glContext.makeCurrent()) {
                throw new GLException("Couldn't make ctx current: " + glContext);
            }
        }
        
        private void destroySharedGL() {
            if (null != this.sharedGLCtx) {
                if (this.sharedGLCtx.isCreated()) {
                    try {
                        this.sharedGLCtx.destroy();
                    }
                    catch (GLException ex) {
                        ex.printStackTrace();
                    }
                }
                this.sharedGLCtx = null;
            }
            if (null != this.dummyDrawable) {
                final AbstractGraphicsDevice device = this.dummyDrawable.getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
                this.dummyDrawable.setRealized(false);
                this.dummyDrawable = null;
                device.close();
            }
        }
        
        public final synchronized void initGL(final GL gl) {
            final GLContext context = gl.getContext();
            final boolean current = context.isCurrent();
            (this.dummyDrawable = GLDrawableFactory.getFactory(gl.getGLProfile()).createDummyDrawable(context.getGLDrawable().getNativeSurface().getGraphicsConfiguration().getScreen().getDevice(), true, context.getGLDrawable().getChosenGLCapabilities(), null)).setRealized(true);
            this.makeCurrent(this.sharedGLCtx = this.dummyDrawable.createContext(context));
            if (current) {
                this.makeCurrent(context);
            }
            else {
                this.sharedGLCtx.release();
            }
        }
        
        public final synchronized void doPause(final boolean b) {
            if (this.isActive) {
                this.shallPause = true;
                if (java.lang.Thread.currentThread() != this) {
                    if (this.isBlocked && this.isActive) {
                        this.interrupt();
                    }
                    if (b) {
                        try {
                            while (this.isActive && this.isRunning) {
                                this.wait();
                            }
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                    }
                }
            }
        }
        
        public final synchronized void doResume() {
            if (this.isRunning && !this.isActive) {
                this.shallPause = false;
                if (java.lang.Thread.currentThread() != this) {
                    try {
                        this.notifyAll();
                        while (!this.isActive && !this.shallPause && this.isRunning) {
                            this.wait();
                        }
                    }
                    catch (InterruptedException ex) {
                        final InterruptedException wrap = SourcedInterruptedException.wrap(ex);
                        this.doPause(false);
                        throw new InterruptedRuntimeException(wrap);
                    }
                }
            }
        }
        
        public final synchronized void doStop() {
            if (this.isRunning) {
                this.shallStop = true;
                if (java.lang.Thread.currentThread() != this) {
                    if (this.isBlocked && this.isRunning) {
                        this.interrupt();
                    }
                    try {
                        this.notifyAll();
                        while (this.isRunning) {
                            this.wait();
                        }
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                }
            }
        }
        
        public final boolean isRunning() {
            return this.isRunning;
        }
        
        public final boolean isActive() {
            return this.isActive;
        }
        
        @Override
        public final void run() {
            this.setName(this.getName() + "-StreamWorker_" + GLMediaPlayerImpl.StreamWorkerInstanceId);
            ++GLMediaPlayerImpl.StreamWorkerInstanceId;
            synchronized (this) {
                this.isRunning = true;
                this.notifyAll();
            }
            while (!this.shallStop) {
                TextureSequence.TextureFrame textureFrame = null;
                try {
                    if (this.shallPause) {
                        synchronized (this) {
                            if (this.sharedGLCtxCurrent) {
                                GLMediaPlayerImpl.this.postNextTextureImpl(this.sharedGLCtx.getGL());
                                this.sharedGLCtx.release();
                            }
                            while (this.shallPause && !this.shallStop) {
                                this.isActive = false;
                                this.notifyAll();
                                try {
                                    this.wait();
                                }
                                catch (InterruptedException ex) {
                                    if (!this.shallPause) {
                                        throw SourcedInterruptedException.wrap(ex);
                                    }
                                    continue;
                                }
                            }
                            if (this.sharedGLCtxCurrent) {
                                this.makeCurrent(this.sharedGLCtx);
                                GLMediaPlayerImpl.this.preNextTextureImpl(this.sharedGLCtx.getGL());
                            }
                            this.isActive = true;
                            this.notifyAll();
                        }
                    }
                    if (!this.sharedGLCtxCurrent && null != this.sharedGLCtx) {
                        synchronized (this) {
                            if (null != this.sharedGLCtx) {
                                this.makeCurrent(this.sharedGLCtx);
                                GLMediaPlayerImpl.this.preNextTextureImpl(this.sharedGLCtx.getGL());
                                this.sharedGLCtxCurrent = true;
                            }
                            if (null == GLMediaPlayerImpl.this.videoFramesFree) {
                                throw new InternalError("XXX videoFramesFree is null");
                            }
                        }
                    }
                    if (!this.shallStop) {
                        this.isBlocked = true;
                        GL gl;
                        if (-2 != GLMediaPlayerImpl.this.vid) {
                            textureFrame = GLMediaPlayerImpl.this.videoFramesFree.getBlocking();
                            textureFrame.setPTS(Integer.MIN_VALUE);
                            gl = this.sharedGLCtx.getGL();
                        }
                        else {
                            gl = null;
                        }
                        this.isBlocked = false;
                        final int nextTextureImpl = GLMediaPlayerImpl.this.getNextTextureImpl(gl, textureFrame);
                        int n = 0;
                        if (Integer.MIN_VALUE != nextTextureImpl) {
                            if (null != textureFrame) {
                                if (GLMediaPlayerImpl.STREAM_WORKER_DELAY > 0) {
                                    java.lang.Thread.sleep(GLMediaPlayerImpl.STREAM_WORKER_DELAY);
                                }
                                if (!GLMediaPlayerImpl.this.videoFramesDecoded.put(textureFrame)) {
                                    throw new InternalError("XXX: free " + GLMediaPlayerImpl.this.videoFramesFree + ", decoded " + GLMediaPlayerImpl.this.videoFramesDecoded + ", " + GLMediaPlayerImpl.this);
                                }
                                GLMediaPlayerImpl.this.newFrameAvailable(textureFrame, Platform.currentTimeMillis());
                                textureFrame = null;
                            }
                            else if (Integer.MAX_VALUE == nextTextureImpl || (GLMediaPlayerImpl.this.duration > 0 && GLMediaPlayerImpl.this.duration < nextTextureImpl)) {
                                n = 1;
                            }
                            else {
                                GLMediaPlayerImpl.this.nullFrameCount = 0;
                            }
                        }
                        else if (null == textureFrame) {
                            n = ((GLMediaPlayerImpl.this.maxNullFrameCountUntilEOS <= GLMediaPlayerImpl.this.nullFrameCount) ? 1 : 0);
                            if (null == GLMediaPlayerImpl.this.audioSink || 0 == GLMediaPlayerImpl.this.audioSink.getEnqueuedFrameCount()) {
                                GLMediaPlayerImpl.this.nullFrameCount++;
                            }
                        }
                        if (n != 0) {
                            synchronized (this) {
                                this.shallPause = true;
                                this.isActive = false;
                                this.notifyAll();
                            }
                            if (GLMediaPlayer.DEBUG) {
                                System.err.println("AV-EOS (StreamWorker): EOS_PTS " + (Integer.MAX_VALUE == nextTextureImpl) + ", " + GLMediaPlayerImpl.this);
                            }
                            GLMediaPlayerImpl.this.pauseImpl(true, 16);
                        }
                    }
                }
                catch (InterruptedException ex2) {
                    if (!this.isBlocked) {
                        GLMediaPlayerImpl.this.streamErr = new StreamException("InterruptedException while decoding: " + GLMediaPlayerImpl.this.toString(), SourcedInterruptedException.wrap(ex2));
                    }
                    this.isBlocked = false;
                }
                catch (Throwable t) {
                    GLMediaPlayerImpl.this.streamErr = new StreamException(t.getClass().getSimpleName() + " while decoding: " + GLMediaPlayerImpl.this.toString(), t);
                }
                finally {
                    if (null != textureFrame) {
                        GLMediaPlayerImpl.this.videoFramesFree.put(textureFrame);
                    }
                    if (null != GLMediaPlayerImpl.this.streamErr) {
                        if (GLMediaPlayer.DEBUG) {
                            ExceptionUtils.dumpThrowable("handled", GLMediaPlayerImpl.this.streamErr);
                        }
                        synchronized (this) {
                            this.shallPause = true;
                            this.isActive = false;
                            this.notifyAll();
                        }
                        GLMediaPlayerImpl.this.pauseImpl(true, 32);
                    }
                }
            }
            synchronized (this) {
                if (this.sharedGLCtxCurrent) {
                    GLMediaPlayerImpl.this.postNextTextureImpl(this.sharedGLCtx.getGL());
                }
                this.destroySharedGL();
                this.isRunning = false;
                this.isActive = false;
                this.notifyAll();
            }
        }
    }
}
