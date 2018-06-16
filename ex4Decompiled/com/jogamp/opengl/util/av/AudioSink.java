// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.av;

import com.jogamp.opengl.util.TimeFrameI;
import jogamp.opengl.Debug;
import java.nio.ByteBuffer;

public interface AudioSink
{
    public static final boolean DEBUG = Debug.debug("AudioSink");
    public static final int DefaultFrameDuration = 32;
    public static final int DefaultInitialQueueSize = 512;
    public static final int DefaultQueueGrowAmount = 512;
    public static final int DefaultQueueLimitWithVideo = 3072;
    public static final int DefaultQueueLimitAudioOnly = 1024;
    public static final AudioFormat DefaultFormat = new AudioFormat(44100, 16, 2, true, true, false, true);
    
    boolean isInitialized();
    
    float getPlaySpeed();
    
    boolean setPlaySpeed(final float p0);
    
    float getVolume();
    
    boolean setVolume(final float p0);
    
    AudioFormat getPreferredFormat();
    
    int getMaxSupportedChannels();
    
    boolean isSupported(final AudioFormat p0);
    
    boolean init(final AudioFormat p0, final float p1, final int p2, final int p3, final int p4);
    
    AudioFormat getChosenFormat();
    
    boolean isPlaying();
    
    void play();
    
    void pause();
    
    void flush();
    
    void destroy();
    
    int getFrameCount();
    
    int getEnqueuedFrameCount();
    
    int getQueuedFrameCount();
    
    int getQueuedByteCount();
    
    int getQueuedTime();
    
    int getPTS();
    
    int getFreeFrameCount();
    
    AudioFrame enqueueData(final int p0, final ByteBuffer p1, final int p2);
    
    public static class AudioFormat
    {
        public final int sampleRate;
        public final int sampleSize;
        public final int channelCount;
        public final boolean signed;
        public final boolean fixedP;
        public final boolean planar;
        public final boolean littleEndian;
        
        public AudioFormat(final int sampleRate, final int sampleSize, final int channelCount, final boolean signed, final boolean fixedP, final boolean planar, final boolean littleEndian) {
            this.sampleRate = sampleRate;
            this.sampleSize = sampleSize;
            this.channelCount = channelCount;
            this.signed = signed;
            this.fixedP = fixedP;
            this.planar = planar;
            this.littleEndian = littleEndian;
            if (!fixedP) {
                if (sampleSize != 32 && sampleSize != 64) {
                    throw new IllegalArgumentException("Floating point: sampleSize " + sampleSize + " bits");
                }
                if (!signed) {
                    throw new IllegalArgumentException("Floating point: unsigned");
                }
            }
        }
        
        public final int getDurationsByteSize(final int n) {
            return n * (this.channelCount * (this.sampleSize >>> 3) * (this.sampleRate / 1000));
        }
        
        public final int getBytesDuration(final int n) {
            return n / (this.channelCount * (this.sampleSize >>> 3) * (this.sampleRate / 1000));
        }
        
        public final float getSamplesDuration(final int n) {
            return 1000.0f * n / this.sampleRate;
        }
        
        public final int getFrameCount(final int n, final float n2) {
            return Math.max(1, (int)(n / n2 + 0.5f));
        }
        
        public final int getSamplesByteCount(final int n) {
            return n * (this.sampleSize >>> 3);
        }
        
        public final int getBytesSampleCount(final int n) {
            return (n << 3) / this.sampleSize;
        }
        
        @Override
        public String toString() {
            return "AudioDataFormat[sampleRate " + this.sampleRate + ", sampleSize " + this.sampleSize + ", channelCount " + this.channelCount + ", signed " + this.signed + ", fixedP " + this.fixedP + ", " + (this.planar ? "planar" : "packed") + ", " + (this.littleEndian ? "little" : "big") + "-endian]";
        }
    }
    
    public abstract static class AudioFrame extends TimeFrameI
    {
        protected int byteSize;
        
        public AudioFrame() {
            this.byteSize = 0;
        }
        
        public AudioFrame(final int n, final int n2, final int byteSize) {
            super(n, n2);
            this.byteSize = byteSize;
        }
        
        public final int getByteSize() {
            return this.byteSize;
        }
        
        public final void setByteSize(final int byteSize) {
            this.byteSize = byteSize;
        }
        
        @Override
        public String toString() {
            return "AudioFrame[pts " + this.pts + " ms, l " + this.duration + " ms, " + this.byteSize + " bytes]";
        }
    }
    
    public static class AudioDataFrame extends AudioFrame
    {
        protected final ByteBuffer data;
        
        public AudioDataFrame(final int n, final int n2, final ByteBuffer data, final int n3) {
            super(n, n2, n3);
            if (n3 > data.remaining()) {
                throw new IllegalArgumentException("Give size " + n3 + " exceeds remaining bytes in ls " + data + ". " + this);
            }
            this.data = data;
        }
        
        public final ByteBuffer getData() {
            return this.data;
        }
        
        @Override
        public String toString() {
            return "AudioDataFrame[pts " + this.pts + " ms, l " + this.duration + " ms, " + this.byteSize + " bytes, " + this.data + "]";
        }
    }
}
