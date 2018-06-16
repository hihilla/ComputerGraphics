// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public interface WindowListener extends NEWTEventListener
{
    void windowResized(final WindowEvent p0);
    
    void windowMoved(final WindowEvent p0);
    
    void windowDestroyNotify(final WindowEvent p0);
    
    void windowDestroyed(final WindowEvent p0);
    
    void windowGainedFocus(final WindowEvent p0);
    
    void windowLostFocus(final WindowEvent p0);
    
    void windowRepaint(final WindowUpdateEvent p0);
}
