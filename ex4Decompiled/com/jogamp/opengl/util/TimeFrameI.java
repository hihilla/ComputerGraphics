// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

public class TimeFrameI
{
    public static final int INVALID_PTS = Integer.MIN_VALUE;
    public static final int END_OF_STREAM_PTS = Integer.MAX_VALUE;
    protected int pts;
    protected int duration;
    
    public TimeFrameI() {
        this.pts = Integer.MIN_VALUE;
        this.duration = 0;
    }
    
    public TimeFrameI(final int pts, final int duration) {
        this.pts = pts;
        this.duration = duration;
    }
    
    public final int getPTS() {
        return this.pts;
    }
    
    public final void setPTS(final int pts) {
        this.pts = pts;
    }
    
    public final int getDuration() {
        return this.duration;
    }
    
    public final void setDuration(final int duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString() {
        return "TimeFrame[pts " + this.pts + " ms, l " + this.duration + " ms]";
    }
}
