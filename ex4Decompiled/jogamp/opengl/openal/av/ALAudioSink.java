// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.openal.av;

import com.jogamp.openal.ALFactory;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import com.jogamp.common.util.LFRingbuffer;
import com.jogamp.openal.util.ALHelpers;
import com.jogamp.common.ExceptionUtils;
import java.nio.IntBuffer;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.common.util.Ringbuffer;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.openal.ALCcontext;
import com.jogamp.openal.ALCdevice;
import com.jogamp.openal.ALExt;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALC;
import com.jogamp.opengl.util.av.AudioSink;

public class ALAudioSink implements AudioSink
{
    private static final String AL_SOFT_buffer_samples = "AL_SOFT_buffer_samples";
    private static final String ALC_EXT_thread_local_context = "ALC_EXT_thread_local_context";
    private static final boolean DEBUG_TRACE;
    private static final ALC alc;
    private static final AL al;
    private static final ALExt alExt;
    private static final boolean staticAvailable;
    private String deviceSpecifier;
    private ALCdevice device;
    private boolean hasSOFTBufferSamples;
    private boolean hasALC_thread_local_context;
    private AudioFormat preferredAudioFormat;
    private ALCcontext context;
    private final RecursiveLock lock;
    private float playSpeed;
    private float volume;
    private int[] alBufferNames;
    private int frameGrowAmount;
    private int frameLimit;
    private Ringbuffer<ALAudioFrame> alFramesAvail;
    private Ringbuffer<ALAudioFrame> alFramesPlaying;
    private volatile int alBufferBytesQueued;
    private volatile int playingPTS;
    private volatile int enqueuedFrameCount;
    private int[] alSource;
    private AudioFormat chosenFormat;
    private int alChannelLayout;
    private int alSampleType;
    private int alFormat;
    private boolean initialized;
    private volatile boolean playRequested;
    
    private void clearPreALError(final String s) {
        this.checkALError(s);
    }
    
    private boolean checkALError(final String s) {
        final int alcGetError = ALAudioSink.alc.alcGetError(this.device);
        final int alGetError = ALAudioSink.al.alGetError();
        final boolean b = alcGetError == 0 && alGetError == 0;
        if (ALAudioSink.DEBUG) {
            System.err.println("ALAudioSink." + s + ": ok " + b + ", err [alc " + toHexString(alcGetError) + ", al " + toHexString(alGetError) + "]");
        }
        return b;
    }
    
    public ALAudioSink() {
        this.lock = LockFactory.createRecursiveLock();
        this.volume = 1.0f;
        this.alBufferNames = null;
        this.frameGrowAmount = 0;
        this.frameLimit = 0;
        this.alFramesAvail = null;
        this.alFramesPlaying = null;
        this.alBufferBytesQueued = 0;
        this.playingPTS = Integer.MIN_VALUE;
        this.alSource = null;
        this.playRequested = false;
        this.initialized = false;
        this.chosenFormat = null;
        if (!ALAudioSink.staticAvailable) {
            return;
        }
        synchronized (ALAudioSink.class) {
            try {
                this.device = ALAudioSink.alc.alcOpenDevice((String)null);
                if (this.device == null) {
                    throw new RuntimeException(getThreadName() + ": ALAudioSink: Error opening default OpenAL device");
                }
                int n = 1;
                this.clearPreALError("init." + n++);
                this.deviceSpecifier = ALAudioSink.alc.alcGetString(this.device, 4101);
                if (this.deviceSpecifier == null) {
                    throw new RuntimeException(getThreadName() + ": ALAudioSink: Error getting specifier for default OpenAL device");
                }
                this.context = ALAudioSink.alc.alcCreateContext(this.device, (IntBuffer)null);
                if (this.context == null) {
                    throw new RuntimeException(getThreadName() + ": ALAudioSink: Error creating OpenAL context for " + this.deviceSpecifier);
                }
                this.lockContext();
                try {
                    if (ALAudioSink.alc.alcGetError(this.device) != 0) {
                        throw new RuntimeException(getThreadName() + ": ALAudioSink: Error making OpenAL context current");
                    }
                    this.hasSOFTBufferSamples = ALAudioSink.al.alIsExtensionPresent("AL_SOFT_buffer_samples");
                    this.hasALC_thread_local_context = (ALAudioSink.alc.alcIsExtensionPresent((ALCdevice)null, "ALC_EXT_thread_local_context") || ALAudioSink.alc.alcIsExtensionPresent(this.device, "ALC_EXT_thread_local_context"));
                    this.clearPreALError("init." + n++);
                    this.preferredAudioFormat = new AudioFormat(this.querySampleRate(), ALAudioSink.DefaultFormat.sampleSize, ALAudioSink.DefaultFormat.channelCount, ALAudioSink.DefaultFormat.signed, ALAudioSink.DefaultFormat.fixedP, ALAudioSink.DefaultFormat.planar, ALAudioSink.DefaultFormat.littleEndian);
                    if (ALAudioSink.DEBUG) {
                        System.out.println("ALAudioSink: OpenAL Extensions:" + ALAudioSink.al.alGetString(45060));
                        this.clearPreALError("init." + n++);
                        System.out.println("ALAudioSink: Null device OpenAL Extensions:" + ALAudioSink.alc.alcGetString((ALCdevice)null, 4102));
                        this.clearPreALError("init." + n++);
                        System.out.println("ALAudioSink: Device " + this.deviceSpecifier + " OpenAL Extensions:" + ALAudioSink.alc.alcGetString(this.device, 4102));
                        System.out.println("ALAudioSink: hasSOFTBufferSamples " + this.hasSOFTBufferSamples);
                        System.out.println("ALAudioSink: hasALC_thread_local_context " + this.hasALC_thread_local_context);
                        System.out.println("ALAudioSink: preferredAudioFormat " + this.preferredAudioFormat);
                        this.clearPreALError("init." + n++);
                    }
                    this.alSource = new int[1];
                    ALAudioSink.al.alGenSources(1, this.alSource, 0);
                    final int alGetError = ALAudioSink.al.alGetError();
                    if (alGetError != 0) {
                        this.alSource = null;
                        throw new RuntimeException(getThreadName() + ": ALAudioSink: Error generating Source: 0x" + Integer.toHexString(alGetError));
                    }
                    if (ALAudioSink.DEBUG) {
                        System.err.println("ALAudioSink: Using device: " + this.deviceSpecifier);
                    }
                    this.initialized = true;
                }
                finally {
                    this.unlockContext();
                }
            }
            catch (Exception ex) {
                if (ALAudioSink.DEBUG) {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                }
                this.destroy();
            }
        }
    }
    
    private final int querySampleRate() {
        final int[] array = { 0 };
        ALAudioSink.alc.alcGetIntegerv(this.device, 4103, 1, array, 0);
        final int alcGetError = ALAudioSink.alc.alcGetError(this.device);
        final int alGetError = ALAudioSink.al.alGetError();
        int sampleRate;
        if (alcGetError == 0 && alGetError == 0 && 0 != array[0]) {
            sampleRate = array[0];
        }
        else {
            sampleRate = ALAudioSink.DefaultFormat.sampleRate;
        }
        if (ALAudioSink.DEBUG) {
            System.err.println("ALAudioSink.querySampleRate: err [alc " + toHexString(alcGetError) + ", al " + toHexString(alGetError) + "], freq: " + array[0] + " -> " + sampleRate);
        }
        return sampleRate;
    }
    
    private final void lockContext() {
        this.lock.lock();
        if (this.hasALC_thread_local_context) {
            ALAudioSink.alExt.alcSetThreadContext(this.context);
        }
        else {
            ALAudioSink.alc.alcMakeContextCurrent(this.context);
        }
        final int alcGetError = ALAudioSink.alc.alcGetError((ALCdevice)null);
        if (alcGetError != 0) {
            final String string = getThreadName() + ": ALCError " + toHexString(alcGetError) + " while makeCurrent. " + this;
            System.err.println(string);
            ExceptionUtils.dumpStack(System.err);
            this.lock.unlock();
            throw new RuntimeException(string);
        }
        final int alGetError = ALAudioSink.al.alGetError();
        if (alGetError && ALAudioSink.DEBUG) {
            System.err.println(getThreadName() + ": Prev - ALError " + toHexString(alGetError) + " @ makeCurrent. " + this);
            ExceptionUtils.dumpStack(System.err);
        }
    }
    
    private final void unlockContext() {
        if (this.hasALC_thread_local_context) {
            ALAudioSink.alExt.alcSetThreadContext((ALCcontext)null);
        }
        else {
            ALAudioSink.alc.alcMakeContextCurrent((ALCcontext)null);
        }
        this.lock.unlock();
    }
    
    private final void destroyContext() {
        this.lock.lock();
        try {
            if (null != this.context) {
                try {
                    ALAudioSink.alc.alcDestroyContext(this.context);
                }
                catch (Throwable t) {
                    if (ALAudioSink.DEBUG) {
                        ExceptionUtils.dumpThrowable("", t);
                    }
                }
                this.context = null;
            }
            while (this.lock.getHoldCount() > 1) {
                this.lock.unlock();
            }
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final String toString() {
        return "ALAudioSink[init " + this.initialized + ", playRequested " + this.playRequested + ", device " + this.deviceSpecifier + ", ctx " + toHexString((this.context != null) ? this.context.hashCode() : 0) + ", alSource " + ((null != this.alSource) ? this.alSource[0] : 0) + ", chosen " + this.chosenFormat + ", al[chan " + ALHelpers.alChannelLayoutName(this.alChannelLayout) + ", type " + ALHelpers.alSampleTypeName(this.alSampleType) + ", fmt " + toHexString(this.alFormat) + ", soft " + this.hasSOFTBufferSamples + "], playSpeed " + this.playSpeed + ", buffers[total " + ((null != this.alBufferNames) ? this.alBufferNames.length : 0) + ", avail " + this.alFramesAvail.size() + ", " + "queued[" + this.alFramesPlaying.size() + ", apts " + this.getPTS() + ", " + this.getQueuedTime() + " ms, " + this.alBufferBytesQueued + " bytes], " + "queue[g " + this.frameGrowAmount + ", l " + this.frameLimit + "]";
    }
    
    private final String shortString() {
        return "[ctx " + toHexString((this.context != null) ? this.context.hashCode() : 0) + ", playReq " + this.playRequested + ", alSrc " + ((null != this.alSource) ? this.alSource[0] : 0) + ", queued[" + this.alFramesPlaying.size() + ", " + this.alBufferBytesQueued + " bytes], " + "queue[g " + this.frameGrowAmount + ", l " + this.frameLimit + "]";
    }
    
    public final String getPerfString() {
        return "Play [buffer " + this.alFramesPlaying.size() + "/" + ((null != this.alBufferNames) ? this.alBufferNames.length : 0) + ", apts " + this.getPTS() + ", " + this.getQueuedTime() + " ms, " + this.alBufferBytesQueued + " bytes]";
    }
    
    @Override
    public final AudioFormat getPreferredFormat() {
        if (!ALAudioSink.staticAvailable) {
            return null;
        }
        return this.preferredAudioFormat;
    }
    
    @Override
    public final int getMaxSupportedChannels() {
        if (!ALAudioSink.staticAvailable) {
            return 0;
        }
        return this.hasSOFTBufferSamples ? 8 : 2;
    }
    
    @Override
    public final boolean isSupported(final AudioFormat audioFormat) {
        if (!ALAudioSink.staticAvailable) {
            return false;
        }
        if (audioFormat.planar || !audioFormat.littleEndian) {
            return false;
        }
        final int defaultALChannelLayout = ALHelpers.getDefaultALChannelLayout(audioFormat.channelCount);
        if (defaultALChannelLayout != 0) {
            final int alSampleType = ALHelpers.getALSampleType(audioFormat.sampleSize, audioFormat.signed, audioFormat.fixedP);
            if (alSampleType != 0) {
                this.lockContext();
                try {
                    return 0 != ALHelpers.getALFormat(defaultALChannelLayout, alSampleType, this.hasSOFTBufferSamples, ALAudioSink.al, ALAudioSink.alExt);
                }
                finally {
                    this.unlockContext();
                }
            }
        }
        return false;
    }
    
    @Override
    public final boolean init(final AudioFormat chosenFormat, final float n, final int n2, final int n3, final int n4) {
        if (!ALAudioSink.staticAvailable) {
            return false;
        }
        this.alChannelLayout = ALHelpers.getDefaultALChannelLayout(chosenFormat.channelCount);
        this.alSampleType = ALHelpers.getALSampleType(chosenFormat.sampleSize, chosenFormat.signed, chosenFormat.fixedP);
        this.lockContext();
        try {
            if (0 != this.alChannelLayout && 0 != this.alSampleType) {
                this.alFormat = ALHelpers.getALFormat(this.alChannelLayout, this.alSampleType, this.hasSOFTBufferSamples, ALAudioSink.al, ALAudioSink.alExt);
            }
            else {
                this.alFormat = 0;
            }
            if (0 == this.alFormat) {
                return false;
            }
            this.destroyBuffers();
            final float n5 = (n > 1.0f) ? n : 32.0f;
            final int frameCount = chosenFormat.getFrameCount((n2 > 0) ? n2 : 512, n5);
            this.alBufferNames = new int[frameCount];
            ALAudioSink.al.alGenBuffers(frameCount, this.alBufferNames, 0);
            final int alGetError = ALAudioSink.al.alGetError();
            if (alGetError != 0) {
                this.alBufferNames = null;
                throw new RuntimeException(getThreadName() + ": ALAudioSink: Error generating Buffers: 0x" + Integer.toHexString(alGetError));
            }
            final ALAudioFrame[] array = new ALAudioFrame[frameCount];
            for (int i = 0; i < frameCount; ++i) {
                array[i] = new ALAudioFrame(this.alBufferNames[i]);
            }
            this.alFramesAvail = new LFRingbuffer<ALAudioFrame>(array);
            this.alFramesPlaying = new LFRingbuffer<ALAudioFrame>(ALAudioFrame[].class, frameCount);
            this.frameGrowAmount = chosenFormat.getFrameCount((n3 > 0) ? n3 : 512, n5);
            this.frameLimit = chosenFormat.getFrameCount((n4 > 0) ? n4 : 3072, n5);
            if (ALAudioSink.DEBUG_TRACE) {
                this.alFramesAvail.dump(System.err, "Avail-init");
                this.alFramesPlaying.dump(System.err, "Playi-init");
            }
        }
        finally {
            this.unlockContext();
        }
        this.chosenFormat = chosenFormat;
        return true;
    }
    
    @Override
    public final AudioFormat getChosenFormat() {
        return this.chosenFormat;
    }
    
    private static int[] concat(final int[] array, final int[] array2) {
        final int[] copy = Arrays.copyOf(array, array.length + array2.length);
        System.arraycopy(array2, 0, copy, array.length, array2.length);
        return copy;
    }
    
    private boolean growBuffers() {
        if (!this.alFramesAvail.isEmpty() || !this.alFramesPlaying.isFull()) {
            throw new InternalError("Buffers: Avail is !empty " + this.alFramesAvail + " or Playing is !full " + this.alFramesPlaying);
        }
        if (this.alFramesAvail.capacity() >= this.frameLimit || this.alFramesPlaying.capacity() >= this.frameLimit) {
            if (ALAudioSink.DEBUG) {
                System.err.println(getThreadName() + ": ALAudioSink.growBuffers: Frame limit " + this.frameLimit + " reached: Avail " + this.alFramesAvail + ", Playing " + this.alFramesPlaying);
            }
            return false;
        }
        final int[] array = new int[this.frameGrowAmount];
        ALAudioSink.al.alGenBuffers(this.frameGrowAmount, array, 0);
        final int alGetError = ALAudioSink.al.alGetError();
        if (alGetError != 0) {
            if (ALAudioSink.DEBUG) {
                System.err.println(getThreadName() + ": ALAudioSink.growBuffers: Error generating " + this.frameGrowAmount + " new Buffers: 0x" + Integer.toHexString(alGetError));
            }
            return false;
        }
        this.alBufferNames = concat(this.alBufferNames, array);
        final ALAudioFrame[] array2 = new ALAudioFrame[this.frameGrowAmount];
        for (int i = 0; i < this.frameGrowAmount; ++i) {
            array2[i] = new ALAudioFrame(array[i]);
        }
        this.alFramesAvail.growEmptyBuffer(array2);
        this.alFramesPlaying.growFullBuffer(this.frameGrowAmount);
        if (this.alFramesAvail.isEmpty() || this.alFramesPlaying.isFull()) {
            throw new InternalError("Buffers: Avail is empty " + this.alFramesAvail + " or Playing is full " + this.alFramesPlaying);
        }
        if (ALAudioSink.DEBUG) {
            System.err.println(getThreadName() + ": ALAudioSink: Buffer grown " + this.frameGrowAmount + ": Avail " + this.alFramesAvail + ", playing " + this.alFramesPlaying);
        }
        if (ALAudioSink.DEBUG_TRACE) {
            this.alFramesAvail.dump(System.err, "Avail-grow");
            this.alFramesPlaying.dump(System.err, "Playi-grow");
        }
        return true;
    }
    
    private void destroyBuffers() {
        if (!ALAudioSink.staticAvailable) {
            return;
        }
        if (null != this.alBufferNames) {
            try {
                ALAudioSink.al.alDeleteBuffers(this.alBufferNames.length, this.alBufferNames, 0);
            }
            catch (Throwable t) {
                if (ALAudioSink.DEBUG) {
                    System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                    t.printStackTrace();
                }
            }
            this.alFramesAvail.clear();
            this.alFramesAvail = null;
            this.alFramesPlaying.clear();
            this.alFramesPlaying = null;
            this.alBufferBytesQueued = 0;
            this.alBufferNames = null;
        }
    }
    
    @Override
    public final void destroy() {
        this.initialized = false;
        if (!ALAudioSink.staticAvailable) {
            return;
        }
        if (null != this.context) {
            this.lockContext();
        }
        try {
            this.stopImpl(true);
            if (null != this.alSource) {
                try {
                    ALAudioSink.al.alDeleteSources(1, this.alSource, 0);
                }
                catch (Throwable t) {
                    if (ALAudioSink.DEBUG) {
                        System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                        t.printStackTrace();
                    }
                }
                this.alSource = null;
            }
            this.destroyBuffers();
        }
        finally {
            this.destroyContext();
        }
        if (null != this.device) {
            try {
                ALAudioSink.alc.alcCloseDevice(this.device);
            }
            catch (Throwable t2) {
                if (ALAudioSink.DEBUG) {
                    System.err.println("Caught " + t2.getClass().getName() + ": " + t2.getMessage());
                    t2.printStackTrace();
                }
            }
            this.device = null;
        }
        this.chosenFormat = null;
    }
    
    @Override
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    private final int dequeueBuffer(final boolean b, final boolean b2) {
        int n2;
        if (this.alBufferBytesQueued > 0) {
            final int max = Math.max(1, this.alFramesPlaying.size() / 4);
            final int[] array = { 0 };
            int n = 0;
            do {
                ALAudioSink.al.alGetSourcei(this.alSource[0], 4118, array, 0);
                final int alGetError = ALAudioSink.al.alGetError();
                if (alGetError != 0) {
                    throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError) + " while quering processed buffers at source. " + this);
                }
                if (!b || array[0] >= max) {
                    continue;
                }
                ++n;
                final int bytesDuration = this.chosenFormat.getBytesDuration(this.alBufferBytesQueued / this.alFramesPlaying.size());
                final int max2 = Math.max(2, Math.min(100, max * bytesDuration));
                if (ALAudioSink.DEBUG) {
                    System.err.println(getThreadName() + ": ALAudioSink: Dequeue.wait[" + n + "]: avgBufferDura " + bytesDuration + ", releaseBufferLimes " + max + ", sleep " + max2 + " ms, playImpl " + (4114 == this.getSourceState(false)) + ", processed " + array[0] + ", " + this);
                }
                this.unlockContext();
                try {
                    Thread.sleep(max2 - 1);
                }
                catch (InterruptedException ex) {}
                finally {
                    this.lockContext();
                }
            } while (b && array[0] < max && this.alBufferBytesQueued > 0);
            n2 = array[0];
        }
        else {
            n2 = 0;
        }
        if (n2 > 0) {
            final int[] array2 = new int[n2];
            ALAudioSink.al.alSourceUnqueueBuffers(this.alSource[0], n2, array2, 0);
            final int alGetError2 = ALAudioSink.al.alGetError();
            if (alGetError2 != 0) {
                throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError2) + " while dequeueing " + n2 + " buffers. " + this);
            }
            for (int i = 0; i < n2; ++i) {
                final ALAudioFrame alAudioFrame = this.alFramesPlaying.get();
                if (null == alAudioFrame) {
                    if (!b2) {
                        throw new InternalError("Internal Error: " + this);
                    }
                }
                else {
                    if (ALAudioSink.DEBUG_TRACE) {
                        System.err.println("<  [al " + array2[i] + ", q " + alAudioFrame.alBuffer + "] <- " + this.shortString() + " @ " + getThreadName());
                    }
                    if (alAudioFrame.alBuffer != array2[i] && !b2) {
                        this.alFramesAvail.dump(System.err, "Avail-deq02-post");
                        this.alFramesPlaying.dump(System.err, "Playi-deq02-post");
                        throw new InternalError("Buffer name mismatch: dequeued: " + array2[i] + ", released " + alAudioFrame + ", " + this);
                    }
                    this.alBufferBytesQueued -= alAudioFrame.getByteSize();
                    if (!this.alFramesAvail.put(alAudioFrame)) {
                        throw new InternalError("Internal Error: " + this);
                    }
                    if (ALAudioSink.DEBUG_TRACE) {
                        System.err.println("<< [al " + array2[i] + ", q " + alAudioFrame.alBuffer + "] <- " + this.shortString() + " @ " + getThreadName());
                    }
                }
            }
        }
        return n2;
    }
    
    private final void dequeueForceAll() {
        if (ALAudioSink.DEBUG_TRACE) {
            System.err.println("<   _FLUSH_  <- " + this.shortString() + " @ " + getThreadName());
        }
        final int[] array = { 0 };
        ALAudioSink.al.alSourcei(this.alSource[0], 4105, 0);
        if (ALAudioSink.DEBUG_TRACE) {
            ALAudioSink.al.alGetSourcei(this.alSource[0], 4118, array, 0);
        }
        final int alGetError = ALAudioSink.al.alGetError();
        while (!this.alFramesPlaying.isEmpty()) {
            final ALAudioFrame alAudioFrame = this.alFramesPlaying.get();
            if (null == alAudioFrame) {
                throw new InternalError("Internal Error: " + this);
            }
            this.alBufferBytesQueued -= alAudioFrame.getByteSize();
            if (!this.alFramesAvail.put(alAudioFrame)) {
                throw new InternalError("Internal Error: " + this);
            }
        }
        this.alBufferBytesQueued = 0;
        if (ALAudioSink.DEBUG_TRACE) {
            System.err.println("<<  _FLUSH_  [al " + array[0] + ", err " + toHexString(alGetError) + "] <- " + this.shortString() + " @ " + getThreadName());
            ExceptionUtils.dumpStack(System.err);
        }
    }
    
    private final int dequeueBuffer(final boolean b, final int playingPTS, final int n) {
        final int dequeueBuffer = this.dequeueBuffer(b, false);
        final ALAudioFrame alAudioFrame = this.alFramesPlaying.peek();
        if (null != alAudioFrame) {
            this.playingPTS = alAudioFrame.getPTS();
        }
        else {
            this.playingPTS = playingPTS;
        }
        if (ALAudioSink.DEBUG && dequeueBuffer > 0) {
            System.err.println(getThreadName() + ": ALAudioSink: Write " + playingPTS + ", " + n + " ms, dequeued " + dequeueBuffer + ", wait " + b + ", " + this.getPerfString());
        }
        return dequeueBuffer;
    }
    
    @Override
    public final AudioFrame enqueueData(final int pts, final ByteBuffer byteBuffer, final int byteSize) {
        if (!this.initialized || null == this.chosenFormat) {
            return null;
        }
        this.lockContext();
        ALAudioFrame alAudioFrame;
        try {
            final int bytesDuration = this.chosenFormat.getBytesDuration(byteSize);
            boolean b;
            if (this.alFramesAvail.isEmpty()) {
                b = (this.dequeueBuffer(false, pts, bytesDuration) > 0);
                if (this.alFramesAvail.isEmpty()) {
                    this.growBuffers();
                }
            }
            else {
                b = false;
            }
            if (!b && this.alFramesPlaying.size() > 0) {
                this.dequeueBuffer(this.isPlayingImpl0() && this.alFramesAvail.isEmpty(), pts, bytesDuration);
            }
            alAudioFrame = this.alFramesAvail.get();
            if (null == alAudioFrame) {
                this.alFramesAvail.dump(System.err, "Avail");
                throw new InternalError("Internal Error: avail.get null " + this.alFramesAvail + ", " + this);
            }
            alAudioFrame.setPTS(pts);
            alAudioFrame.setDuration(bytesDuration);
            alAudioFrame.setByteSize(byteSize);
            if (!this.alFramesPlaying.put(alAudioFrame)) {
                throw new InternalError("Internal Error: " + this);
            }
            final int[] array = { alAudioFrame.alBuffer };
            if (this.hasSOFTBufferSamples) {
                ALAudioSink.alExt.alBufferSamplesSOFT(alAudioFrame.alBuffer, this.chosenFormat.sampleRate, this.alFormat, this.chosenFormat.getBytesSampleCount(byteSize) / this.chosenFormat.channelCount, this.alChannelLayout, this.alSampleType, (Buffer)byteBuffer);
            }
            else {
                ALAudioSink.al.alBufferData(alAudioFrame.alBuffer, this.alFormat, (Buffer)byteBuffer, byteSize, this.chosenFormat.sampleRate);
            }
            if (ALAudioSink.DEBUG_TRACE) {
                System.err.println(">  " + alAudioFrame.alBuffer + " -> " + this.shortString() + " @ " + getThreadName());
            }
            ALAudioSink.al.alSourceQueueBuffers(this.alSource[0], 1, array, 0);
            final int alGetError = ALAudioSink.al.alGetError();
            if (alGetError != 0) {
                throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError) + " while queueing buffer " + toHexString(array[0]) + ". " + this);
            }
            this.alBufferBytesQueued += byteSize;
            ++this.enqueuedFrameCount;
            if (ALAudioSink.DEBUG_TRACE) {
                System.err.println(">> " + alAudioFrame.alBuffer + " -> " + this.shortString() + " @ " + getThreadName());
            }
            this.playImpl();
        }
        finally {
            this.unlockContext();
        }
        return alAudioFrame;
    }
    
    @Override
    public final boolean isPlaying() {
        if (!this.initialized || null == this.chosenFormat) {
            return false;
        }
        if (this.playRequested) {
            this.lockContext();
            try {
                return this.isPlayingImpl0();
            }
            finally {
                this.unlockContext();
            }
        }
        return false;
    }
    
    private final boolean isPlayingImpl0() {
        return this.playRequested && 4114 == this.getSourceState(false);
    }
    
    private final int getSourceState(final boolean b) {
        final int[] array = { 0 };
        ALAudioSink.al.alGetSourcei(this.alSource[0], 4112, array, 0);
        final int alGetError = ALAudioSink.al.alGetError();
        if (alGetError != 0) {
            final String string = getThreadName() + ": ALError " + toHexString(alGetError) + " while querying SOURCE_STATE. " + this;
            if (!b) {
                throw new RuntimeException(string);
            }
            if (ALAudioSink.DEBUG) {
                System.err.println(string);
            }
        }
        return array[0];
    }
    
    @Override
    public final void play() {
        if (!this.initialized || null == this.chosenFormat) {
            return;
        }
        this.playRequested = true;
        this.lockContext();
        try {
            this.playImpl();
            if (ALAudioSink.DEBUG) {
                System.err.println(getThreadName() + ": ALAudioSink: PLAY playImpl " + (4114 == this.getSourceState(false)) + ", " + this);
            }
        }
        finally {
            this.unlockContext();
        }
    }
    
    private final void playImpl() {
        if (this.playRequested && 4114 != this.getSourceState(false)) {
            ALAudioSink.al.alSourcePlay(this.alSource[0]);
            final int alGetError = ALAudioSink.al.alGetError();
            if (alGetError != 0) {
                throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError) + " while start playing. " + this);
            }
        }
    }
    
    @Override
    public final void pause() {
        if (!this.initialized || null == this.chosenFormat) {
            return;
        }
        if (this.playRequested) {
            this.lockContext();
            try {
                this.pauseImpl();
                if (ALAudioSink.DEBUG) {
                    System.err.println(getThreadName() + ": ALAudioSink: PAUSE playImpl " + (4114 == this.getSourceState(false)) + ", " + this);
                }
            }
            finally {
                this.unlockContext();
            }
        }
    }
    
    private final void pauseImpl() {
        if (this.isPlayingImpl0()) {
            this.playRequested = false;
            ALAudioSink.al.alSourcePause(this.alSource[0]);
            final int alGetError = ALAudioSink.al.alGetError();
            if (alGetError != 0) {
                throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError) + " while pausing. " + this);
            }
        }
    }
    
    private final void stopImpl(final boolean b) {
        if (4116 != this.getSourceState(b)) {
            this.playRequested = false;
            ALAudioSink.al.alSourceStop(this.alSource[0]);
            final int alGetError = ALAudioSink.al.alGetError();
            if (alGetError != 0) {
                final String string = "ALError " + toHexString(alGetError) + " while stopping. " + this;
                if (!b) {
                    throw new RuntimeException(getThreadName() + ": ALError " + toHexString(alGetError) + " while stopping. " + this);
                }
                if (ALAudioSink.DEBUG) {
                    System.err.println(getThreadName() + ": " + string);
                }
            }
        }
    }
    
    @Override
    public final float getPlaySpeed() {
        return this.playSpeed;
    }
    
    @Override
    public final boolean setPlaySpeed(float playSpeed) {
        if (!this.initialized || null == this.chosenFormat) {
            return false;
        }
        this.lockContext();
        try {
            if (Math.abs(1.0f - playSpeed) < 0.01f) {
                playSpeed = 1.0f;
            }
            if (0.5f <= playSpeed && playSpeed <= 2.0f) {
                this.playSpeed = playSpeed;
                ALAudioSink.al.alSourcef(this.alSource[0], 4099, this.playSpeed);
                return true;
            }
        }
        finally {
            this.unlockContext();
        }
        return false;
    }
    
    @Override
    public final float getVolume() {
        return this.volume;
    }
    
    @Override
    public final boolean setVolume(float volume) {
        if (!this.initialized || null == this.chosenFormat) {
            return false;
        }
        this.lockContext();
        try {
            if (Math.abs(volume) < 0.01f) {
                volume = 0.0f;
            }
            else if (Math.abs(1.0f - volume) < 0.01f) {
                volume = 1.0f;
            }
            if (0.0f <= volume && volume <= 1.0f) {
                this.volume = volume;
                ALAudioSink.al.alSourcef(this.alSource[0], 4106, volume);
                return true;
            }
        }
        finally {
            this.unlockContext();
        }
        return false;
    }
    
    @Override
    public final void flush() {
        if (!this.initialized || null == this.chosenFormat) {
            return;
        }
        this.lockContext();
        try {
            this.stopImpl(false);
            this.dequeueForceAll();
            if (this.alBufferNames.length != this.alFramesAvail.size() || this.alFramesPlaying.size() != 0) {
                throw new InternalError("XXX: " + this);
            }
            if (ALAudioSink.DEBUG) {
                System.err.println(getThreadName() + ": ALAudioSink: FLUSH playImpl " + (4114 == this.getSourceState(false)) + ", " + this);
            }
        }
        finally {
            this.unlockContext();
        }
    }
    
    @Override
    public final int getEnqueuedFrameCount() {
        return this.enqueuedFrameCount;
    }
    
    @Override
    public final int getFrameCount() {
        return (null != this.alBufferNames) ? this.alBufferNames.length : 0;
    }
    
    @Override
    public final int getQueuedFrameCount() {
        if (!this.initialized || null == this.chosenFormat) {
            return 0;
        }
        return this.alFramesPlaying.size();
    }
    
    @Override
    public final int getFreeFrameCount() {
        if (!this.initialized || null == this.chosenFormat) {
            return 0;
        }
        return this.alFramesAvail.size();
    }
    
    @Override
    public final int getQueuedByteCount() {
        if (!this.initialized || null == this.chosenFormat) {
            return 0;
        }
        return this.alBufferBytesQueued;
    }
    
    @Override
    public final int getQueuedTime() {
        if (!this.initialized || null == this.chosenFormat) {
            return 0;
        }
        return this.chosenFormat.getBytesDuration(this.alBufferBytesQueued);
    }
    
    @Override
    public final int getPTS() {
        return this.playingPTS;
    }
    
    private static final String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    private static final String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        Debug.initSingleton();
        DEBUG_TRACE = PropertyAccess.isPropertyDefined("jogl.debug.AudioSink.trace", true);
        ALC alc2 = null;
        AL al2 = null;
        ALExt alExt2 = null;
        try {
            alc2 = ALFactory.getALC();
            al2 = ALFactory.getAL();
            alExt2 = ALFactory.getALExt();
        }
        catch (Throwable t) {
            if (ALAudioSink.DEBUG) {
                System.err.println("ALAudioSink: Caught " + t.getClass().getName() + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        alc = alc2;
        al = al2;
        alExt = alExt2;
        staticAvailable = (null != ALAudioSink.alc && null != ALAudioSink.al && null != ALAudioSink.alExt);
    }
    
    static class ALAudioFrame extends AudioFrame
    {
        private final int alBuffer;
        
        ALAudioFrame(final int alBuffer) {
            this.alBuffer = alBuffer;
        }
        
        public ALAudioFrame(final int alBuffer, final int n, final int n2, final int n3) {
            super(n, n2, n3);
            this.alBuffer = alBuffer;
        }
        
        public final int getALBuffer() {
            return this.alBuffer;
        }
        
        @Override
        public String toString() {
            return "ALAudioFrame[pts " + this.pts + " ms, l " + this.duration + " ms, " + this.byteSize + " bytes, buffer " + this.alBuffer + "]";
        }
    }
}
