// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public interface MouseListener extends NEWTEventListener
{
    void mouseClicked(final MouseEvent p0);
    
    void mouseEntered(final MouseEvent p0);
    
    void mouseExited(final MouseEvent p0);
    
    void mousePressed(final MouseEvent p0);
    
    void mouseReleased(final MouseEvent p0);
    
    void mouseMoved(final MouseEvent p0);
    
    void mouseDragged(final MouseEvent p0);
    
    void mouseWheelMoved(final MouseEvent p0);
}
