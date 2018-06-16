// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.ArrayList;

public class ListenerSyncedImplStub<E>
{
    private ArrayList<E> listeners;
    
    public ListenerSyncedImplStub() {
        this.reset();
    }
    
    public final synchronized void reset() {
        this.listeners = new ArrayList<E>();
    }
    
    public final synchronized void destroy() {
        this.listeners.clear();
        this.listeners = null;
    }
    
    public final synchronized int size() {
        return this.listeners.size();
    }
    
    public final synchronized void addListener(final E e) {
        this.addListener(-1, e);
    }
    
    public final synchronized void addListener(int size, final E e) {
        if (0 > size) {
            size = this.listeners.size();
        }
        this.listeners.add(size, e);
    }
    
    public final synchronized void removeListener(final E e) {
        this.listeners.remove(e);
    }
    
    public final ArrayList<E> getListeners() {
        return this.listeners;
    }
}
