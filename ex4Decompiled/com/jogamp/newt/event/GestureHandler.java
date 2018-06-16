// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import jogamp.newt.Debug;

public interface GestureHandler
{
    public static final boolean DEBUG = Debug.debug("Window.MouseEvent");
    
    void clear(final boolean p0);
    
    boolean hasGesture();
    
    InputEvent getGestureEvent();
    
    boolean isWithinGesture();
    
    boolean process(final InputEvent p0);
    
    public static class GestureEvent extends InputEvent
    {
        public static final short EVENT_GESTURE_DETECTED = 400;
        private final GestureHandler handler;
        private final InputEvent ie;
        
        public GestureEvent(final Object o, final long n, final int n2, final GestureHandler handler, final InputEvent ie) {
            super((short)400, o, n, n2);
            this.handler = handler;
            this.ie = ie;
        }
        
        public GestureEvent(final short n, final Object o, final long n2, final int n3, final GestureHandler handler, final InputEvent ie) {
            super(n, o, n2, n3);
            this.handler = handler;
            this.ie = ie;
        }
        
        public final GestureHandler getHandler() {
            return this.handler;
        }
        
        public final InputEvent getTrigger() {
            return this.ie;
        }
        
        @Override
        public String toString() {
            return "GestureEvent[handler " + this.handler + "]";
        }
    }
    
    public interface GestureListener extends NEWTEventListener
    {
        void gestureDetected(final GestureEvent p0);
    }
}
