// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import com.jogamp.nativewindow.util.Rectangle;

public class WindowUpdateEvent extends WindowEvent
{
    final Rectangle bounds;
    
    public WindowUpdateEvent(final short n, final Object o, final long n2, final Rectangle bounds) {
        super(n, o, n2);
        this.bounds = bounds;
    }
    
    public Rectangle getBounds() {
        return this.bounds;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    @Override
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("WindowUpdateEvent[").append(this.bounds).append(", ");
        return super.toString(sb).append("]");
    }
}
