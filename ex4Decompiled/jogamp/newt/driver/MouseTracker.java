// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver;

import com.jogamp.newt.event.WindowListener;

public interface MouseTracker extends WindowListener
{
    int getLastY();
    
    int getLastX();
}
