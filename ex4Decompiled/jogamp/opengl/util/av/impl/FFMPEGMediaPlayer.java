// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av.impl;

import com.jogamp.common.os.Platform;
import com.jogamp.common.util.VersionNumber;
import java.nio.ByteBuffer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.av.GLMediaPlayer;
import jogamp.opengl.util.av.AudioSampleFormat;
import com.jogamp.opengl.util.texture.TextureSequence;
import java.security.AccessController;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import java.security.PrivilegedAction;
import jogamp.opengl.GLContextImpl;
import java.io.IOException;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.opengl.util.av.AudioSinkFactory;
import com.jogamp.common.util.IOUtil;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.av.AudioSink;
import com.jogamp.opengl.util.GLPixelStorageModes;
import jogamp.opengl.util.av.VideoPixelFormat;
import jogamp.opengl.util.av.GLMediaPlayerImpl;

public class FFMPEGMediaPlayer extends GLMediaPlayerImpl
{
    private static final int ENOSYS = 38;
    private static final FFMPEGNatives natives;
    private static final int avUtilMajorVersionCC;
    private static final int avFormatMajorVersionCC;
    private static final int avCodecMajorVersionCC;
    private static final int avResampleMajorVersionCC;
    private static final int swResampleMajorVersionCC;
    private static final boolean available;
    private static final boolean enableAvResample;
    private static final boolean enableSwResample;
    private long moviePtr;
    private String texLookupFuncName;
    private boolean usesTexLookupShader;
    private VideoPixelFormat vPixelFmt;
    private int vPlanes;
    private int vBitsPerPixel;
    private int vBytesPerPixelPerPlane;
    private int texWidth;
    private int texHeight;
    private String singleTexComp;
    private final GLPixelStorageModes psm;
    private AudioSink.AudioFormat avChosenAudioFormat;
    private int audioSamplesPerFrameAndChannel;
    public static final String dev_video_linux = "/dev/video";
    
    public static final boolean isAvailable() {
        return FFMPEGMediaPlayer.available;
    }
    
    public FFMPEGMediaPlayer() {
        this.moviePtr = 0L;
        this.texLookupFuncName = "ffmpegTexture2D";
        this.usesTexLookupShader = false;
        this.vPixelFmt = null;
        this.vPlanes = 0;
        this.vBitsPerPixel = 0;
        this.vBytesPerPixelPerPlane = 0;
        this.singleTexComp = "r";
        this.audioSamplesPerFrameAndChannel = 0;
        if (!FFMPEGMediaPlayer.available) {
            throw new RuntimeException("FFMPEGMediaPlayer not available");
        }
        this.moviePtr = FFMPEGMediaPlayer.natives.createInstance0(this, FFMPEGMediaPlayer.enableAvResample, FFMPEGMediaPlayer.enableSwResample, FFMPEGMediaPlayer.DEBUG_NATIVE);
        if (0L == this.moviePtr) {
            throw new GLException("Couldn't create FFMPEGInstance");
        }
        this.psm = new GLPixelStorageModes();
        this.audioSink = null;
    }
    
    @Override
    protected final void destroyImpl(final GL gl) {
        if (this.moviePtr != 0L) {
            FFMPEGMediaPlayer.natives.destroyInstance0(this.moviePtr);
            this.moviePtr = 0L;
        }
        this.destroyAudioSink();
    }
    
    private final void destroyAudioSink() {
        final AudioSink audioSink = this.audioSink;
        if (null != audioSink) {
            this.audioSink = null;
            audioSink.destroy();
        }
    }
    
    @Override
    protected final void initStreamImpl(final int n, final int n2) throws IOException {
        if (0L == this.moviePtr) {
            throw new GLException("FFMPEG native instance null");
        }
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("initStream: p1 " + this);
        }
        final String uriFilePathOrASCII = IOUtil.getUriFilePathOrASCII(this.getUri());
        this.destroyAudioSink();
        if (-2 == n2) {
            this.audioSink = AudioSinkFactory.createNull();
        }
        else {
            this.audioSink = AudioSinkFactory.createDefault();
        }
        final AudioSink.AudioFormat preferredFormat = this.audioSink.getPreferredFormat();
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("initStream: p2 preferred " + preferredFormat + ", " + this);
        }
        final boolean b = null != this.cameraPath;
        int n3 = -1;
        int n4 = -1;
        int n5 = -1;
        String s = null;
        String s2 = null;
        if (b) {
            switch (PlatformPropsImpl.OS_TYPE) {
                case ANDROID:
                case FREEBSD:
                case HPUX:
                case LINUX:
                case SUNOS: {
                    s2 = "/dev/video" + this.cameraPath.decode();
                    break;
                }
                default: {
                    s2 = this.cameraPath.decode();
                    break;
                }
            }
            if (null != this.cameraProps) {
                s = this.cameraProps.get("size");
                final int propIntVal = GLMediaPlayerImpl.getPropIntVal(this.cameraProps, "width");
                if (propIntVal > 0) {
                    n3 = propIntVal;
                }
                final int propIntVal2 = GLMediaPlayerImpl.getPropIntVal(this.cameraProps, "height");
                if (propIntVal2 > 0) {
                    n4 = propIntVal2;
                }
                final int propIntVal3 = GLMediaPlayerImpl.getPropIntVal(this.cameraProps, "rate");
                if (propIntVal3 > 0) {
                    n5 = propIntVal3;
                }
            }
        }
        else {
            s2 = uriFilePathOrASCII;
        }
        final int maxSupportedChannels = this.audioSink.getMaxSupportedChannels();
        final int sampleRate = preferredFormat.sampleRate;
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("initStream: p3 cameraPath " + (Object)this.cameraPath + ", isCameraInput " + b);
            System.err.println("initStream: p3 stream " + this.getUri() + " -> " + uriFilePathOrASCII + " -> " + s2);
            System.err.println("initStream: p3 vid " + n + ", sizes " + s + ", reqVideo " + n3 + "x" + n4 + "@" + n5 + ", aid " + n2 + ", aMaxChannelCount " + maxSupportedChannels + ", aPrefSampleRate " + sampleRate);
        }
        FFMPEGMediaPlayer.natives.setStream0(this.moviePtr, s2, b, n, s, n3, n4, n5, n2, maxSupportedChannels, sampleRate);
    }
    
    @Override
    protected final void initGLImpl(final GL gl) throws IOException, GLException {
        if (0L == this.moviePtr) {
            throw new GLException("FFMPEG native instance null");
        }
        if (null == this.audioSink) {
            throw new GLException("AudioSink null");
        }
        int n;
        if (null != gl && -2 != this.getVID()) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                final /* synthetic */ GLContextImpl val$ctx = (GLContextImpl)gl.getContext();
                
                @Override
                public Object run() {
                    final ProcAddressTable glProcAddressTable = this.val$ctx.getGLProcAddressTable();
                    FFMPEGMediaPlayer.natives.setGLFuncs0(FFMPEGMediaPlayer.this.moviePtr, glProcAddressTable.getAddressFor("glTexSubImage2D"), glProcAddressTable.getAddressFor("glGetError"), glProcAddressTable.getAddressFor("glFlush"), glProcAddressTable.getAddressFor("glFinish"));
                    return null;
                }
            });
            n = 3072;
        }
        else {
            n = 1024;
        }
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("initGL: p3 avChosen " + this.avChosenAudioFormat);
        }
        if (-2 == this.getAID()) {
            this.audioSink.destroy();
            (this.audioSink = AudioSinkFactory.createNull()).init(AudioSink.DefaultFormat, 0.0f, 512, 512, n);
        }
        else {
            float samplesDuration;
            if (this.audioSamplesPerFrameAndChannel > 0) {
                samplesDuration = this.avChosenAudioFormat.getSamplesDuration(this.audioSamplesPerFrameAndChannel);
            }
            else {
                samplesDuration = 32.0f;
            }
            if (!this.audioSink.init(this.avChosenAudioFormat, samplesDuration, 512, 512, n)) {
                System.err.println("AudioSink " + this.audioSink.getClass().getName() + " does not support " + this.avChosenAudioFormat + ", using Null");
                this.audioSink.destroy();
                (this.audioSink = AudioSinkFactory.createNull()).init(this.avChosenAudioFormat, 0.0f, 512, 512, n);
            }
        }
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("initGL: p4 chosen " + this.avChosenAudioFormat);
            System.err.println("initGL: p4 chosen " + this.audioSink);
        }
        if (null != gl && -2 != this.getVID()) {
            int n2 = 0;
            int n3 = 0;
            switch (this.vBytesPerPixelPerPlane) {
                case 1: {
                    if (gl.isGL3ES3()) {
                        n2 = 6403;
                        n3 = 6403;
                        this.singleTexComp = "r";
                        break;
                    }
                    n2 = 6406;
                    n3 = 6406;
                    this.singleTexComp = "a";
                    break;
                }
                case 2: {
                    if (this.vPixelFmt == VideoPixelFormat.YUYV422 || this.vPixelFmt == VideoPixelFormat.UYVY422) {
                        n2 = 6408;
                        n3 = 6408;
                        break;
                    }
                    n2 = 33319;
                    n3 = 33319;
                    break;
                }
                case 3: {
                    n2 = 6407;
                    n3 = 6407;
                    break;
                }
                case 4: {
                    if (this.vPixelFmt == VideoPixelFormat.BGRA) {
                        n2 = 32993;
                        n3 = 6408;
                        break;
                    }
                    n2 = 6408;
                    n3 = 6408;
                    break;
                }
                default: {
                    throw new RuntimeException("Unsupported bytes-per-pixel / plane " + this.vBytesPerPixelPerPlane);
                }
            }
            this.setTextureFormat(n3, n2);
            this.setTextureType(5121);
            if (FFMPEGMediaPlayer.DEBUG) {
                System.err.println("initGL: p5: video " + this.vPixelFmt + ", planes " + this.vPlanes + ", bpp " + this.vBitsPerPixel + "/" + this.vBytesPerPixelPerPlane + ", tex " + this.texWidth + "x" + this.texHeight + ", usesTexLookupShader " + this.usesTexLookupShader);
            }
        }
    }
    
    @Override
    protected final TextureSequence.TextureFrame createTexImage(final GL gl, final int n) {
        return new TextureSequence.TextureFrame(this.createTexImageImpl(gl, n, this.texWidth, this.texHeight));
    }
    
    final boolean isAudioFormatSupported(final int n, final int n2, final int n3) {
        final AudioSampleFormat value = AudioSampleFormat.valueOf(n);
        final AudioSink.AudioFormat avAudioFormat2Local = this.avAudioFormat2Local(value, n2, n3);
        final boolean supported = this.audioSink.isSupported(avAudioFormat2Local);
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("AudioSink.isSupported: " + supported + ": av[fmt " + value + ", rate " + n2 + ", chan " + n3 + "] -> " + avAudioFormat2Local);
        }
        return supported;
    }
    
    private final AudioSink.AudioFormat avAudioFormat2Local(final AudioSampleFormat audioSampleFormat, final int n, final int n2) {
        boolean b = true;
        boolean b2 = true;
        int n3 = 0;
        boolean b3 = false;
        switch (audioSampleFormat) {
            case S32: {
                b = false;
            }
            case S32P: {
                n3 = 32;
                b3 = true;
                break;
            }
            case S16: {
                b = false;
            }
            case S16P: {
                n3 = 16;
                b3 = true;
                break;
            }
            case U8: {
                b = false;
            }
            case U8P: {
                n3 = 8;
                b3 = false;
                break;
            }
            case DBL: {
                b = false;
            }
            case DBLP: {
                n3 = 64;
                b3 = true;
                b2 = false;
                break;
            }
            case FLT: {
                b = false;
            }
            case FLTP: {
                n3 = 32;
                b3 = true;
                b2 = false;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported sampleformat: " + audioSampleFormat);
            }
        }
        return new AudioSink.AudioFormat(n, n3, n2, b3, b2, b, true);
    }
    
    void setupFFAttributes(final int n, final int n2, final int vPlanes, final int vBitsPerPixel, final int vBytesPerPixelPerPlane, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int audioSamplesPerFrameAndChannel) {
        this.vPixelFmt = null;
        this.vPlanes = 0;
        this.vBitsPerPixel = 0;
        this.vBytesPerPixelPerPlane = 0;
        this.usesTexLookupShader = false;
        this.texWidth = 0;
        this.texHeight = 0;
        final int[] array = { 0, 0, 0 };
        if (-2 != n) {
            this.vPixelFmt = VideoPixelFormat.valueOf(n2);
            this.vPlanes = vPlanes;
            this.vBitsPerPixel = vBitsPerPixel;
            this.vBytesPerPixelPerPlane = vBytesPerPixelPerPlane;
            array[0] = n3;
            array[1] = n4;
            array[2] = n5;
            switch (this.vPixelFmt) {
                case YUVJ420P:
                case YUV420P: {
                    this.usesTexLookupShader = true;
                    this.texWidth = array[0] + array[1];
                    this.texHeight = n7;
                    break;
                }
                case YUVJ422P:
                case YUV422P: {
                    this.usesTexLookupShader = true;
                    this.texWidth = array[0] + array[1] + array[2];
                    this.texHeight = n7;
                    break;
                }
                case YUYV422:
                case UYVY422:
                case BGR24: {
                    this.usesTexLookupShader = true;
                    this.texWidth = array[0];
                    this.texHeight = n7;
                    break;
                }
                case RGB24:
                case ARGB:
                case RGBA:
                case ABGR:
                case BGRA: {
                    this.usesTexLookupShader = false;
                    this.texWidth = array[0];
                    this.texHeight = n7;
                    break;
                }
                default: {
                    throw new RuntimeException("Unsupported pixelformat: " + this.vPixelFmt);
                }
            }
        }
        this.avChosenAudioFormat = null;
        this.audioSamplesPerFrameAndChannel = 0;
        AudioSampleFormat value;
        if (-2 != n8) {
            value = AudioSampleFormat.valueOf(n9);
            this.avChosenAudioFormat = this.avAudioFormat2Local(value, n10, n11);
            this.audioSamplesPerFrameAndChannel = audioSamplesPerFrameAndChannel;
        }
        else {
            value = null;
        }
        if (FFMPEGMediaPlayer.DEBUG) {
            System.err.println("audio: id " + n8 + ", fmt " + value + ", " + this.avChosenAudioFormat + ", aFrameSize/fc " + audioSamplesPerFrameAndChannel);
            System.err.println("video: id " + n + ", fmt " + n6 + "x" + n7 + ", " + this.vPixelFmt + ", planes " + this.vPlanes + ", bpp " + this.vBitsPerPixel + "/" + this.vBytesPerPixelPerPlane + ", usesTexLookupShader " + this.usesTexLookupShader);
            for (int i = 0; i < 3; ++i) {
                System.err.println("video: p[" + i + "]: " + array[i]);
            }
            System.err.println("video: total tex " + this.texWidth + "x" + this.texHeight);
            System.err.println(this.toString());
        }
    }
    
    void updateVidAttributes(final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
    }
    
    @Override
    public final String getTextureLookupFunctionName(final String texLookupFuncName) throws IllegalStateException {
        if (GLMediaPlayer.State.Uninitialized == this.getState()) {
            throw new IllegalStateException("Instance not initialized: " + this);
        }
        if (this.usesTexLookupShader) {
            if (null != texLookupFuncName && texLookupFuncName.length() > 0) {
                this.texLookupFuncName = texLookupFuncName;
            }
            return this.texLookupFuncName;
        }
        return super.getTextureLookupFunctionName(texLookupFuncName);
    }
    
    @Override
    public final String getTextureLookupFragmentShaderImpl() throws IllegalStateException {
        if (GLMediaPlayer.State.Uninitialized == this.getState()) {
            throw new IllegalStateException("Instance not initialized: " + this);
        }
        if (!this.usesTexLookupShader) {
            return super.getTextureLookupFragmentShaderImpl();
        }
        final float n = this.getWidth() / this.texWidth;
        switch (this.vPixelFmt) {
            case YUVJ420P:
            case YUV420P: {
                return "vec4 " + this.texLookupFuncName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  const vec2 u_off = vec2(" + n + ", 0.0);\n" + "  const vec2 v_off = vec2(" + n + ", 0.5);\n" + "  vec2 tc_half = texCoord*0.5;\n" + "  float y,u,v,r,g,b;\n" + "  y = texture2D(image, texCoord)." + this.singleTexComp + ";\n" + "  u = texture2D(image, u_off+tc_half)." + this.singleTexComp + ";\n" + "  v = texture2D(image, v_off+tc_half)." + this.singleTexComp + ";\n" + "  y = 1.1643*(y-0.0625);\n" + "  u = u-0.5;\n" + "  v = v-0.5;\n" + "  r = y+1.5958*v;\n" + "  g = y-0.39173*u-0.81290*v;\n" + "  b = y+2.017*u;\n" + "  return vec4(r, g, b, 1);\n" + "}\n";
            }
            case YUVJ422P:
            case YUV422P: {
                return "vec4 " + this.texLookupFuncName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  const vec2 u_off = vec2(" + n + "      , 0.0);\n" + "  const vec2 v_off = vec2(" + n + " * 1.5, 0.0);\n" + "  vec2 tc_halfw = vec2(texCoord.x*0.5, texCoord.y);\n" + "  float y,u,v,r,g,b;\n" + "  y = texture2D(image, texCoord)." + this.singleTexComp + ";\n" + "  u = texture2D(image, u_off+tc_halfw)." + this.singleTexComp + ";\n" + "  v = texture2D(image, v_off+tc_halfw)." + this.singleTexComp + ";\n" + "  y = 1.1643*(y-0.0625);\n" + "  u = u-0.5;\n" + "  v = v-0.5;\n" + "  r = y+1.5958*v;\n" + "  g = y-0.39173*u-0.81290*v;\n" + "  b = y+2.017*u;\n" + "  return vec4(r, g, b, 1);\n" + "}\n";
            }
            case YUYV422: {
                return "vec4 " + this.texLookupFuncName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  " + "  float y1,u,y2,v,y,r,g,b;\n" + "  vec2 tc_halfw = vec2(texCoord.x*0.5, texCoord.y);\n" + "  vec4 yuyv = texture2D(image, tc_halfw).rgba;\n" + "  y1 = yuyv.r;\n" + "  u  = yuyv.g;\n" + "  y2 = yuyv.b;\n" + "  v  = yuyv.a;\n" + "  y = mix( y1, y2, mod(gl_FragCoord.x, 2) ); /* avoid branching! */\n" + "  y = 1.1643*(y-0.0625);\n" + "  u = u-0.5;\n" + "  v = v-0.5;\n" + "  r = y+1.5958*v;\n" + "  g = y-0.39173*u-0.81290*v;\n" + "  b = y+2.017*u;\n" + "  return vec4(r, g, b, 1);\n" + "}\n";
            }
            case UYVY422: {
                return "vec4 " + this.texLookupFuncName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  " + "  float y1,u,y2,v,y,r,g,b;\n" + "  vec2 tc_halfw = vec2(texCoord.x*0.5, texCoord.y);\n" + "  vec4 uyvy = texture2D(image, tc_halfw).rgba;\n" + "  u  = uyvy.r;\n" + "  y1 = uyvy.g;\n" + "  v  = uyvy.b;\n" + "  y2 = uyvy.a;\n" + "  y = mix( y1, y2, mod(gl_FragCoord.x, 2) ); /* avoid branching! */\n" + "  y = 1.1643*(y-0.0625);\n" + "  u = u-0.5;\n" + "  v = v-0.5;\n" + "  r = y+1.5958*v;\n" + "  g = y-0.39173*u-0.81290*v;\n" + "  b = y+2.017*u;\n" + "  return vec4(r, g, b, 1);\n" + "}\n";
            }
            case BGR24: {
                return "vec4 " + this.texLookupFuncName + "(in " + this.getTextureSampler2DType() + " image, in vec2 texCoord) {\n" + "  " + "  vec3 bgr = texture2D(image, texCoord).rgb;\n" + "  return vec4(bgr.b, bgr.g, bgr.r, 1);\n" + "}\n";
            }
            default: {
                throw new InternalError("Add proper mapping of: vPixelFmt " + this.vPixelFmt + ", usesTexLookupShader " + this.usesTexLookupShader);
            }
        }
    }
    
    public final boolean playImpl() {
        if (0L == this.moviePtr) {
            return false;
        }
        final int play0 = FFMPEGMediaPlayer.natives.play0(this.moviePtr);
        if (FFMPEGMediaPlayer.DEBUG_NATIVE && play0 != 0 && play0 != -38) {
            System.err.println("libav play err: " + play0);
        }
        return true;
    }
    
    public final boolean pauseImpl() {
        if (0L == this.moviePtr) {
            return false;
        }
        final int pause0 = FFMPEGMediaPlayer.natives.pause0(this.moviePtr);
        if (FFMPEGMediaPlayer.DEBUG_NATIVE && pause0 != 0 && pause0 != -38) {
            System.err.println("libav pause err: " + pause0);
        }
        return true;
    }
    
    @Override
    protected final synchronized int seekImpl(final int n) {
        if (0L == this.moviePtr) {
            throw new GLException("FFMPEG native instance null");
        }
        return FFMPEGMediaPlayer.natives.seek0(this.moviePtr, n);
    }
    
    @Override
    protected void preNextTextureImpl(final GL gl) {
        this.psm.setUnpackAlignment(gl, 1);
        gl.glActiveTexture(33984 + this.getTextureUnit());
    }
    
    @Override
    protected void postNextTextureImpl(final GL gl) {
        this.psm.restore(gl);
    }
    
    @Override
    protected final int getNextTextureImpl(final GL gl, final TextureSequence.TextureFrame textureFrame) {
        if (0L == this.moviePtr) {
            throw new GLException("FFMPEG native instance null");
        }
        int nextPacket0 = Integer.MIN_VALUE;
        if (null != gl) {
            final Texture texture = textureFrame.getTexture();
            texture.enable(gl);
            texture.bind(gl);
        }
        for (int n = 0; Integer.MIN_VALUE == nextPacket0 && 10 > n; nextPacket0 = FFMPEGMediaPlayer.natives.readNextPacket0(this.moviePtr, this.getTextureTarget(), this.getTextureFormat(), this.getTextureType()), ++n) {}
        if (null != textureFrame) {
            textureFrame.setPTS(nextPacket0);
        }
        return nextPacket0;
    }
    
    final void pushSound(final ByteBuffer byteBuffer, final int n, final int firstAudioPTS2SCR) {
        this.setFirstAudioPTS2SCR(firstAudioPTS2SCR);
        if (1.0f == this.getPlaySpeed() || this.audioSinkPlaySpeedSet) {
            this.audioSink.enqueueData(firstAudioPTS2SCR, byteBuffer, n);
        }
    }
    
    static {
        final boolean initSingleton = FFMPEGDynamicLibraryBundleInfo.initSingleton();
        int n;
        if (FFMPEGDynamicLibraryBundleInfo.libsLoaded()) {
            natives = FFMPEGDynamicLibraryBundleInfo.getNatives();
            if (null != FFMPEGMediaPlayer.natives) {
                avCodecMajorVersionCC = FFMPEGMediaPlayer.natives.getAvCodecMajorVersionCC0();
                avFormatMajorVersionCC = FFMPEGMediaPlayer.natives.getAvFormatMajorVersionCC0();
                avUtilMajorVersionCC = FFMPEGMediaPlayer.natives.getAvUtilMajorVersionCC0();
                avResampleMajorVersionCC = FFMPEGMediaPlayer.natives.getAvResampleMajorVersionCC0();
                swResampleMajorVersionCC = FFMPEGMediaPlayer.natives.getSwResampleMajorVersionCC0();
            }
            else {
                avUtilMajorVersionCC = 0;
                avFormatMajorVersionCC = 0;
                avCodecMajorVersionCC = 0;
                avResampleMajorVersionCC = 0;
                swResampleMajorVersionCC = 0;
            }
            final VersionNumber avCodecVersion = FFMPEGDynamicLibraryBundleInfo.avCodecVersion;
            final VersionNumber avFormatVersion = FFMPEGDynamicLibraryBundleInfo.avFormatVersion;
            final VersionNumber avUtilVersion = FFMPEGDynamicLibraryBundleInfo.avUtilVersion;
            final VersionNumber avResampleVersion = FFMPEGDynamicLibraryBundleInfo.avResampleVersion;
            final boolean avResampleLoaded = FFMPEGDynamicLibraryBundleInfo.avResampleLoaded();
            final VersionNumber swResampleVersion = FFMPEGDynamicLibraryBundleInfo.swResampleVersion;
            final boolean swResampleLoaded = FFMPEGDynamicLibraryBundleInfo.swResampleLoaded();
            if (FFMPEGMediaPlayer.DEBUG) {
                System.err.println("LIB_AV Codec   : " + avCodecVersion + " [cc " + FFMPEGMediaPlayer.avCodecMajorVersionCC + "]");
                System.err.println("LIB_AV Format  : " + avFormatVersion + " [cc " + FFMPEGMediaPlayer.avFormatMajorVersionCC + "]");
                System.err.println("LIB_AV Util    : " + avUtilVersion + " [cc " + FFMPEGMediaPlayer.avUtilMajorVersionCC + "]");
                System.err.println("LIB_AV Resample: " + avResampleVersion + " [cc " + FFMPEGMediaPlayer.avResampleMajorVersionCC + ", loaded " + avResampleLoaded + "]");
                System.err.println("LIB_SW Resample: " + swResampleVersion + " [cc " + FFMPEGMediaPlayer.swResampleMajorVersionCC + ", loaded " + swResampleLoaded + "]");
                System.err.println("LIB_AV Device  : [loaded " + FFMPEGDynamicLibraryBundleInfo.avDeviceLoaded() + "]");
                System.err.println("LIB_AV Class   : " + ((null != FFMPEGMediaPlayer.natives) ? FFMPEGMediaPlayer.natives.getClass().getSimpleName() : "n/a"));
            }
            final int major = avCodecVersion.getMajor();
            final int major2 = avFormatVersion.getMajor();
            final int major3 = avUtilVersion.getMajor();
            n = ((FFMPEGMediaPlayer.avCodecMajorVersionCC == major && FFMPEGMediaPlayer.avFormatMajorVersionCC == major2 && (FFMPEGMediaPlayer.avUtilMajorVersionCC == major3 || (55 == FFMPEGMediaPlayer.avCodecMajorVersionCC && 53 == FFMPEGMediaPlayer.avUtilMajorVersionCC && 52 == major3))) ? 1 : 0);
            enableAvResample = (avResampleLoaded && FFMPEGMediaPlayer.avResampleMajorVersionCC == avResampleVersion.getMajor());
            enableSwResample = (swResampleLoaded && FFMPEGMediaPlayer.swResampleMajorVersionCC == swResampleVersion.getMajor());
            if (FFMPEGMediaPlayer.DEBUG) {
                System.err.println("LIB_AV Resample: enabled " + FFMPEGMediaPlayer.enableAvResample);
                System.err.println("LIB_SW Resample: enabled " + FFMPEGMediaPlayer.enableSwResample);
            }
            if (n == 0) {
                System.err.println("LIB_AV Not Matching Compile-Time / Runtime Major-Version");
            }
        }
        else {
            natives = null;
            avUtilMajorVersionCC = 0;
            avFormatMajorVersionCC = 0;
            avCodecMajorVersionCC = 0;
            avResampleMajorVersionCC = 0;
            swResampleMajorVersionCC = 0;
            n = 0;
            enableAvResample = false;
            enableSwResample = false;
        }
        available = (initSingleton && n != 0 && null != FFMPEGMediaPlayer.natives);
    }
}
