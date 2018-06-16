// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import java.util.LinkedList;

public class NEWTEventFiFo
{
    private final LinkedList events;
    
    public NEWTEventFiFo() {
        this.events = new LinkedList();
    }
    
    public synchronized void put(final NEWTEvent newtEvent) {
        this.events.addLast(newtEvent);
        this.notifyAll();
    }
    
    public synchronized NEWTEvent get() {
        if (0 == this.events.size()) {
            return null;
        }
        return this.events.removeFirst();
    }
    
    public synchronized int size() {
        return this.events.size();
    }
    
    public synchronized void clear() {
        this.events.clear();
    }
}
