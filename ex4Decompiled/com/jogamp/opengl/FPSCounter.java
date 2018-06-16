// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.io.PrintStream;

public interface FPSCounter
{
    public static final int DEFAULT_FRAMES_PER_INTERVAL = 300;
    
    void setUpdateFPSFrames(final int p0, final PrintStream p1);
    
    void resetFPSCounter();
    
    int getUpdateFPSFrames();
    
    long getFPSStartTime();
    
    long getLastFPSUpdateTime();
    
    long getLastFPSPeriod();
    
    float getLastFPS();
    
    int getTotalFPSFrames();
    
    long getTotalFPSDuration();
    
    float getTotalFPS();
}
