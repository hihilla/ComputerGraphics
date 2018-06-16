// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.concurrent.TimeUnit;
import java.io.PrintStream;
import com.jogamp.opengl.FPSCounter;

public class FPSCounterImpl implements FPSCounter
{
    private int fpsUpdateFramesInterval;
    private PrintStream fpsOutputStream;
    private long fpsStartTime;
    private long fpsLastUpdateTime;
    private long fpsLastPeriod;
    private long fpsTotalDuration;
    private int fpsTotalFrames;
    private float fpsLast;
    private float fpsTotal;
    
    public FPSCounterImpl() {
        this.setUpdateFPSFrames(0, null);
    }
    
    public final synchronized void tickFPS() {
        ++this.fpsTotalFrames;
        if (this.fpsUpdateFramesInterval > 0 && this.fpsTotalFrames % this.fpsUpdateFramesInterval == 0) {
            final long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
            this.fpsLastPeriod = millis - this.fpsLastUpdateTime;
            this.fpsLastPeriod = Math.max(this.fpsLastPeriod, 1L);
            this.fpsLast = this.fpsUpdateFramesInterval * 1000.0f / this.fpsLastPeriod;
            this.fpsTotalDuration = millis - this.fpsStartTime;
            this.fpsTotalDuration = Math.max(this.fpsTotalDuration, 1L);
            this.fpsTotal = this.fpsTotalFrames * 1000.0f / this.fpsTotalDuration;
            if (null != this.fpsOutputStream) {
                this.fpsOutputStream.println(this.toString());
            }
            this.fpsLastUpdateTime = millis;
        }
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String value = String.valueOf(this.fpsLast);
        final String substring = value.substring(0, value.indexOf(46) + 2);
        final String value2 = String.valueOf(this.fpsTotal);
        sb.append(this.fpsTotalDuration / 1000L + " s: " + this.fpsUpdateFramesInterval + " f / " + this.fpsLastPeriod + " ms, " + substring + " fps, " + this.fpsLastPeriod / this.fpsUpdateFramesInterval + " ms/f; " + "total: " + this.fpsTotalFrames + " f, " + value2.substring(0, value2.indexOf(46) + 2) + " fps, " + this.fpsTotalDuration / this.fpsTotalFrames + " ms/f");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    @Override
    public final synchronized void setUpdateFPSFrames(final int fpsUpdateFramesInterval, final PrintStream fpsOutputStream) {
        this.fpsUpdateFramesInterval = fpsUpdateFramesInterval;
        this.fpsOutputStream = fpsOutputStream;
        this.resetFPSCounter();
    }
    
    @Override
    public final synchronized void resetFPSCounter() {
        this.fpsStartTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        this.fpsLastUpdateTime = this.fpsStartTime;
        this.fpsLastPeriod = 0L;
        this.fpsTotalFrames = 0;
        this.fpsLast = 0.0f;
        this.fpsTotal = 0.0f;
        this.fpsLastPeriod = 0L;
        this.fpsTotalDuration = 0L;
    }
    
    @Override
    public final synchronized int getUpdateFPSFrames() {
        return this.fpsUpdateFramesInterval;
    }
    
    @Override
    public final synchronized long getFPSStartTime() {
        return this.fpsStartTime;
    }
    
    @Override
    public final synchronized long getLastFPSUpdateTime() {
        return this.fpsLastUpdateTime;
    }
    
    @Override
    public final synchronized long getLastFPSPeriod() {
        return this.fpsLastPeriod;
    }
    
    @Override
    public final synchronized float getLastFPS() {
        return this.fpsLast;
    }
    
    @Override
    public final synchronized int getTotalFPSFrames() {
        return this.fpsTotalFrames;
    }
    
    @Override
    public final synchronized long getTotalFPSDuration() {
        return this.fpsTotalDuration;
    }
    
    @Override
    public final synchronized float getTotalFPS() {
        return this.fpsTotal;
    }
}
