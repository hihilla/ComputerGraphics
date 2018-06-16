// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

public class WindowEvent extends NEWTEvent
{
    public static final short EVENT_WINDOW_RESIZED = 100;
    public static final short EVENT_WINDOW_MOVED = 101;
    public static final short EVENT_WINDOW_DESTROY_NOTIFY = 102;
    public static final short EVENT_WINDOW_GAINED_FOCUS = 103;
    public static final short EVENT_WINDOW_LOST_FOCUS = 104;
    public static final short EVENT_WINDOW_REPAINT = 105;
    public static final short EVENT_WINDOW_DESTROYED = 106;
    
    public WindowEvent(final short n, final Object o, final long n2) {
        super(n, o, n2);
    }
    
    public static String getEventTypeString(final short n) {
        switch (n) {
            case 100: {
                return "WINDOW_RESIZED";
            }
            case 101: {
                return "WINDOW_MOVED";
            }
            case 102: {
                return "EVENT_WINDOW_DESTROY_NOTIFY";
            }
            case 103: {
                return "EVENT_WINDOW_GAINED_FOCUS";
            }
            case 104: {
                return "EVENT_WINDOW_LOST_FOCUS";
            }
            case 105: {
                return "EVENT_WINDOW_REPAINT";
            }
            case 106: {
                return "EVENT_WINDOW_DESTROYED";
            }
            default: {
                return "unknown (" + n + ")";
            }
        }
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
        sb.append("WindowEvent[").append(getEventTypeString(this.getEventType())).append(", ");
        return super.toString(sb).append("]");
    }
}
