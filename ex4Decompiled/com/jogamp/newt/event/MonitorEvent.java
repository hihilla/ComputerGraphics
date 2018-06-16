// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;

public class MonitorEvent extends OutputEvent
{
    public static final short EVENT_MONITOR_MODE_CHANGE_NOTIFY = 600;
    public static final short EVENT_MONITOR_MODE_CHANGED = 601;
    private final MonitorMode mode;
    
    public MonitorEvent(final short n, final MonitorDevice monitorDevice, final long n2, final MonitorMode mode) {
        super(n, monitorDevice, n2);
        this.mode = mode;
    }
    
    public final MonitorDevice getMonitor() {
        return (MonitorDevice)this.source;
    }
    
    public final MonitorMode getMode() {
        return this.mode;
    }
    
    public static String getEventTypeString(final short n) {
        switch (n) {
            case 600: {
                return "EVENT_MONITOR_MODE_CHANGE_NOTIFY";
            }
            case 601: {
                return "EVENT_MONITOR_MODE_CHANGED";
            }
            default: {
                return "unknown (" + n + ")";
            }
        }
    }
    
    @Override
    public final String toString() {
        return this.toString(null).toString();
    }
    
    @Override
    public final StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("MonitorEvent[").append(getEventTypeString(this.getEventType())).append(", source ").append(this.source).append(", mode ").append(this.mode).append(", ");
        return super.toString(sb).append("]");
    }
}
