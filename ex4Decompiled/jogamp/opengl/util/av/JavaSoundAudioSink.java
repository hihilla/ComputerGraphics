// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.av;

import java.nio.ByteBuffer;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioSystem;
import java.util.Arrays;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import com.jogamp.opengl.util.av.AudioSink;

public class JavaSoundAudioSink implements AudioSink
{
    public static final int BUFFER_SIZE = 1000;
    public static final int SAMPLES_PER_BUFFER = 500;
    private static final boolean staticAvailable;
    private javax.sound.sampled.AudioFormat format;
    private DataLine.Info info;
    private SourceDataLine auline;
    private int bufferCount;
    private final byte[] sampleData;
    private boolean initialized;
    private AudioFormat chosenFormat;
    private volatile boolean playRequested;
    private float volume;
    
    public JavaSoundAudioSink() {
        this.sampleData = new byte[1000];
        this.initialized = false;
        this.chosenFormat = null;
        this.playRequested = false;
        this.volume = 1.0f;
    }
    
    @Override
    public String toString() {
        return "JavaSoundSink[init " + this.initialized + ", dataLine " + this.info + ", source " + this.auline + ", bufferCount " + this.bufferCount + ", chosen " + this.chosenFormat + ", jsFormat " + this.format;
    }
    
    @Override
    public final float getPlaySpeed() {
        return 1.0f;
    }
    
    @Override
    public final boolean setPlaySpeed(final float n) {
        return false;
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
        return JavaSoundAudioSink.DefaultFormat;
    }
    
    @Override
    public final int getMaxSupportedChannels() {
        return 2;
    }
    
    @Override
    public final boolean isSupported(final AudioFormat audioFormat) {
        return true;
    }
    
    @Override
    public boolean init(final AudioFormat chosenFormat, final float n, final int n2, final int n3, final int n4) {
        if (!JavaSoundAudioSink.staticAvailable) {
            return false;
        }
        this.format = new javax.sound.sampled.AudioFormat(chosenFormat.sampleRate, chosenFormat.sampleSize, chosenFormat.channelCount, chosenFormat.signed, !chosenFormat.littleEndian);
        this.info = new DataLine.Info(SourceDataLine.class, this.format);
        Arrays.fill(this.sampleData, (byte)0);
        try {
            (this.auline = (SourceDataLine)AudioSystem.getLine(this.info)).open(this.format);
            this.auline.start();
            System.out.println("JavaSound audio sink");
            this.initialized = true;
            this.chosenFormat = chosenFormat;
        }
        catch (Exception ex) {
            this.initialized = false;
        }
        return true;
    }
    
    @Override
    public final AudioFormat getChosenFormat() {
        return this.chosenFormat;
    }
    
    @Override
    public boolean isPlaying() {
        return this.playRequested && this.auline.isRunning();
    }
    
    @Override
    public void play() {
        if (null != this.auline) {
            this.playRequested = true;
            this.playImpl();
        }
    }
    
    private void playImpl() {
        if (this.playRequested && !this.auline.isRunning()) {
            this.auline.start();
        }
    }
    
    @Override
    public void pause() {
        if (null != this.auline) {
            this.playRequested = false;
            this.auline.stop();
        }
    }
    
    @Override
    public void flush() {
        if (null != this.auline) {
            this.playRequested = false;
            this.auline.stop();
            this.auline.flush();
        }
    }
    
    @Override
    public final int getEnqueuedFrameCount() {
        return 0;
    }
    
    @Override
    public int getFrameCount() {
        return 1;
    }
    
    @Override
    public int getQueuedFrameCount() {
        return 0;
    }
    
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
    @Override
    public void destroy() {
        this.initialized = false;
        this.chosenFormat = null;
    }
    
    @Override
    public AudioFrame enqueueData(final int n, final ByteBuffer byteBuffer, final int n2) {
        final byte[] array = new byte[n2];
        final int position = byteBuffer.position();
        byteBuffer.get(array, 0, n2);
        byteBuffer.position(position);
        int write;
        for (int n3 = 0, i = n2; i > 0; i -= write, n3 += write) {
            write = this.auline.write(array, n3, n2);
        }
        this.playImpl();
        return new AudioDataFrame(n, this.chosenFormat.getBytesDuration(n2), byteBuffer, n2);
    }
    
    @Override
    public int getQueuedByteCount() {
        return this.auline.getBufferSize() - this.auline.available();
    }
    
    @Override
    public int getFreeFrameCount() {
        return this.auline.available();
    }
    
    @Override
    public int getQueuedTime() {
        return this.getQueuedTimeImpl(this.getQueuedByteCount());
    }
    
    private final int getQueuedTimeImpl(final int n) {
        return n / (this.chosenFormat.channelCount * (this.chosenFormat.sampleSize >>> 3) * (this.chosenFormat.sampleRate / 1000));
    }
    
    @Override
    public final int getPTS() {
        return 0;
    }
    
    static {
        boolean staticAvailable2 = false;
        try {
            AudioSystem.getAudioFileTypes();
            staticAvailable2 = true;
        }
        catch (Throwable t) {}
        staticAvailable = staticAvailable2;
    }
}
