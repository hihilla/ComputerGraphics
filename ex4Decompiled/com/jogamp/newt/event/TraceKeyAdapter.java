// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public class TraceKeyAdapter implements KeyListener
{
    KeyListener downstream;
    
    public TraceKeyAdapter() {
        this.downstream = null;
    }
    
    public TraceKeyAdapter(final KeyListener downstream) {
        this.downstream = downstream;
    }
    
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        System.err.println(keyEvent);
        if (null != this.downstream) {
            this.downstream.keyPressed(keyEvent);
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent keyEvent) {
        System.err.println(keyEvent);
        if (null != this.downstream) {
            this.downstream.keyReleased(keyEvent);
        }
    }
}
