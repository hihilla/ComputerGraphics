// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

import java.nio.ByteBuffer;
import com.jogamp.opengl.util.av.AudioSink;

public class NullAudioSink implements AudioSink
{
    private volatile float playSpeed;
    private volatile boolean playRequested;
    private volatile int playingPTS;
    private float volume;
    private AudioFormat chosenFormat;
    private boolean initialized;
    
    public NullAudioSink() {
        this.playSpeed = 1.0f;
        this.playRequested = false;
        this.playingPTS = Integer.MIN_VALUE;
        this.volume = 1.0f;
        this.initialized = true;
        this.chosenFormat = null;
    }
    
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
    @Override
    public final float getPlaySpeed() {
        return this.playSpeed;
    }
    
    @Override
    public final boolean setPlaySpeed(float playSpeed) {
        if (Math.abs(1.0f - playSpeed) < 0.01f) {
            playSpeed = 1.0f;
        }
        this.playSpeed = playSpeed;
        return true;
    }
    
    @Override
    public final float getVolume() {
        return this.volume;
    }
    
    @Override
    public final boolean setVolume(final float volume) {
        this.volume = volume;
        return true;
    }
    
    @Override
    public AudioFormat getPreferredFormat() {
        return NullAudioSink.DefaultFormat;
    }
    
    @Override
    public final int getMaxSupportedChannels() {
        return 8;
    }
    
    @Override
    public final boolean isSupported(final AudioFormat audioFormat) {
        return true;
    }
    
    @Override
    public boolean init(final AudioFormat chosenFormat, final float n, final int n2, final int n3, final int n4) {
        this.chosenFormat = chosenFormat;
        return true;
    }
    
    @Override
    public final AudioFormat getChosenFormat() {
        return this.chosenFormat;
    }
    
    @Override
    public boolean isPlaying() {
        return this.playRequested;
    }
    
    @Override
    public void play() {
        this.playRequested = true;
    }
    
    @Override
    public void pause() {
        this.playRequested = false;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void destroy() {
        this.initialized = false;
        this.chosenFormat = null;
    }
    
    @Override
    public final int getEnqueuedFrameCount() {
        return 0;
    }
    
    @Override
    public int getFrameCount() {
        return 0;
    }
    
    @Override
    public int getQueuedFrameCount() {
        return 0;
    }
    
    @Override
    public int getQueuedByteCount() {
        return 0;
    }
    
    @Override
    public int getQueuedTime() {
        return 0;
    }
    
    @Override
    public final int getPTS() {
        return this.playingPTS;
    }
    
    @Override
    public int getFreeFrameCount() {
        return 1;
    }
    
    @Override
    public AudioFrame enqueueData(final int playingPTS, final ByteBuffer byteBuffer, final int n) {
        if (!this.initialized || null == this.chosenFormat) {
            return null;
        }
        this.playingPTS = playingPTS;
        return null;
    }
}
