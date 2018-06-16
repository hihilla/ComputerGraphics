// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import java.util.EventObject;

public class NEWTEvent extends EventObject
{
    public static final Object consumedTag;
    private final short eventType;
    private final long when;
    private Object attachment;
    static final boolean DEBUG = false;
    
    protected NEWTEvent(final short eventType, final Object o, final long when) {
        super(o);
        this.eventType = eventType;
        this.when = when;
        this.attachment = null;
    }
    
    public final short getEventType() {
        return this.eventType;
    }
    
    public final long getWhen() {
        return this.when;
    }
    
    public final void setAttachment(final Object attachment) {
        this.attachment = attachment;
    }
    
    public final Object getAttachment() {
        return this.attachment;
    }
    
    public final boolean isConsumed() {
        return NEWTEvent.consumedTag == this.attachment;
    }
    
    public final void setConsumed(final boolean b) {
        if (b) {
            this.setAttachment(NEWTEvent.consumedTag);
        }
        else if (NEWTEvent.consumedTag == this.attachment) {
            this.setAttachment(null);
        }
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        return sb.append("NEWTEvent[source:").append(this.getSource().getClass().getName()).append(", consumed ").append(this.isConsumed()).append(", when:").append(this.getWhen()).append(" d ").append(System.currentTimeMillis() - this.getWhen()).append("ms]");
    }
    
    public static String toHexString(final short n) {
        return "0x" + Integer.toHexString(n & 0xFFFF);
    }
    
    static {
        consumedTag = new Object();
    }
}
