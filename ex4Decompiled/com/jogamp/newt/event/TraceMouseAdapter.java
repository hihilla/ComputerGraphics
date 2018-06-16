// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public class TraceMouseAdapter implements MouseListener
{
    MouseListener downstream;
    
    public TraceMouseAdapter() {
        this.downstream = null;
    }
    
    public TraceMouseAdapter(final MouseListener downstream) {
        this.downstream = downstream;
    }
    
    @Override
    public void mouseClicked(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseClicked(mouseEvent);
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseEntered(mouseEvent);
        }
    }
    
    @Override
    public void mouseExited(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseExited(mouseEvent);
        }
    }
    
    @Override
    public void mousePressed(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mousePressed(mouseEvent);
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseReleased(mouseEvent);
        }
    }
    
    @Override
    public void mouseMoved(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseMoved(mouseEvent);
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseDragged(mouseEvent);
        }
    }
    
    @Override
    public void mouseWheelMoved(final MouseEvent mouseEvent) {
        System.err.println(mouseEvent);
        if (null != this.downstream) {
            this.downstream.mouseWheelMoved(mouseEvent);
        }
    }
}
