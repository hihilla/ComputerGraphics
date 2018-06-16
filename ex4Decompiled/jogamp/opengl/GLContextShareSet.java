// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.util.IdentityHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import java.util.Map;

public class GLContextShareSet
{
    private static final boolean DEBUG;
    private static final Map<GLContext, ShareSet> shareMap;
    
    public static synchronized void registerSharing(final GLContext glContext, final GLContext glContext2) {
        if (glContext == null || glContext2 == null) {
            throw new IllegalArgumentException("Both slave and master must be non-null");
        }
        ShareSet set = entryFor(glContext);
        if (null == set) {
            set = entryFor(glContext2);
        }
        if (null == set) {
            set = new ShareSet();
        }
        set.addNew(glContext, glContext2);
        set.addIfNew(glContext2, glContext2);
        addEntry(glContext, set);
        addEntry(glContext2, set);
        if (GLContextShareSet.DEBUG) {
            System.err.println("GLContextShareSet: registereSharing: 1: " + toHexString(glContext.hashCode()) + ", 2: " + toHexString(glContext2.hashCode()));
        }
    }
    
    public static synchronized void unregisterSharing(final GLContext glContext) {
        if (glContext == null) {
            throw new IllegalArgumentException("Last context is null");
        }
        final ShareSet entry = entryFor(glContext);
        if (entry == null) {
            throw new GLException("Last context is unknown: " + glContext);
        }
        final Set<GLContext> createdShares = entry.getCreatedShares();
        if (createdShares.size() > 0) {
            throw new GLException("Last context's share set contains " + createdShares.size() + " non destroyed context");
        }
        final Set<GLContext> destroyedShares = entry.getDestroyedShares();
        if (destroyedShares.size() == 0) {
            throw new GLException("Last context's share set contains no destroyed context");
        }
        if (GLContextShareSet.DEBUG) {
            System.err.println("GLContextShareSet: unregisterSharing: " + toHexString(glContext.hashCode()) + ", entries: " + destroyedShares.size());
        }
        final Iterator<GLContext> iterator = destroyedShares.iterator();
        while (iterator.hasNext()) {
            if (null == removeEntry(iterator.next())) {
                throw new GLException("Removal of shareSet for context failed");
            }
        }
    }
    
    public static synchronized boolean isShared(final GLContext glContext) {
        if (glContext == null) {
            throw new IllegalArgumentException("context is null");
        }
        return entryFor(glContext) != null;
    }
    
    public static synchronized GLContext getSharedMaster(final GLContext glContext) {
        final ShareSet entry = entryFor(glContext);
        if (entry == null) {
            return null;
        }
        return entry.getMaster(glContext);
    }
    
    private static synchronized Set<GLContext> getCreatedSharesImpl(final GLContext glContext) {
        if (glContext == null) {
            throw new IllegalArgumentException("context is null");
        }
        final ShareSet entry = entryFor(glContext);
        if (entry != null) {
            return entry.getCreatedShares();
        }
        return null;
    }
    
    private static synchronized Set<GLContext> getDestroyedSharesImpl(final GLContext glContext) {
        if (glContext == null) {
            throw new IllegalArgumentException("context is null");
        }
        final ShareSet entry = entryFor(glContext);
        if (entry != null) {
            return entry.getDestroyedShares();
        }
        return null;
    }
    
    public static synchronized boolean hasCreatedSharedLeft(final GLContext glContext) {
        final Set<GLContext> createdSharesImpl = getCreatedSharesImpl(glContext);
        return null != createdSharesImpl && createdSharesImpl.size() > 0;
    }
    
    public static synchronized ArrayList<GLContext> getCreatedShares(final GLContext glContext) {
        final ArrayList<GLContext> list = new ArrayList<GLContext>();
        final Set<GLContext> createdSharesImpl = getCreatedSharesImpl(glContext);
        if (null != createdSharesImpl) {
            for (final GLContext glContext2 : createdSharesImpl) {
                if (glContext2 != glContext) {
                    list.add(glContext2);
                }
            }
        }
        return list;
    }
    
    public static synchronized ArrayList<GLContext> getDestroyedShares(final GLContext glContext) {
        final ArrayList<GLContext> list = new ArrayList<GLContext>();
        final Set<GLContext> destroyedSharesImpl = getDestroyedSharesImpl(glContext);
        if (null != destroyedSharesImpl) {
            for (final GLContext glContext2 : destroyedSharesImpl) {
                if (glContext2 != glContext) {
                    list.add(glContext2);
                }
            }
        }
        return list;
    }
    
    public static synchronized boolean contextCreated(final GLContext glContext) {
        final ShareSet entry = entryFor(glContext);
        if (entry != null) {
            entry.contextCreated(glContext);
            return true;
        }
        return false;
    }
    
    public static synchronized boolean contextDestroyed(final GLContext glContext) {
        final ShareSet entry = entryFor(glContext);
        if (entry != null) {
            entry.contextDestroyed(glContext);
            return true;
        }
        return false;
    }
    
    private static ShareSet entryFor(final GLContext glContext) {
        return GLContextShareSet.shareMap.get(glContext);
    }
    
    private static void addEntry(final GLContext glContext, final ShareSet set) {
        if (GLContextShareSet.shareMap.get(glContext) == null) {
            GLContextShareSet.shareMap.put(glContext, set);
        }
    }
    
    private static ShareSet removeEntry(final GLContext glContext) {
        return GLContextShareSet.shareMap.remove(glContext);
    }
    
    private static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    static {
        DEBUG = GLContextImpl.DEBUG;
        shareMap = new IdentityHashMap<GLContext, ShareSet>();
    }
    
    private static class ShareSet
    {
        private final Map<GLContext, GLContext> createdShares;
        private final Map<GLContext, GLContext> destroyedShares;
        
        private ShareSet() {
            this.createdShares = new IdentityHashMap<GLContext, GLContext>();
            this.destroyedShares = new IdentityHashMap<GLContext, GLContext>();
        }
        
        public final void addNew(final GLContext glContext, final GLContext glContext2) {
            GLContext glContext3;
            if (glContext.isCreated()) {
                glContext3 = this.createdShares.put(glContext, glContext2);
            }
            else {
                glContext3 = this.destroyedShares.put(glContext, glContext2);
            }
            if (null != glContext3) {
                throw new InternalError("State of ShareSet corrupted: Slave " + toHexString(glContext.hashCode()) + " is not new w/ master " + toHexString(glContext3.hashCode()));
            }
        }
        
        public final void addIfNew(final GLContext glContext, final GLContext glContext2) {
            if (null == this.getMaster(glContext2)) {
                this.addNew(glContext, glContext2);
            }
        }
        
        public final GLContext getMaster(final GLContext glContext) {
            final GLContext glContext2 = this.createdShares.get(glContext);
            return (null != glContext2) ? glContext2 : this.destroyedShares.get(glContext);
        }
        
        public Set<GLContext> getCreatedShares() {
            return this.createdShares.keySet();
        }
        
        public Set<GLContext> getDestroyedShares() {
            return this.destroyedShares.keySet();
        }
        
        public void contextCreated(final GLContext glContext) {
            final GLContext glContext2 = this.destroyedShares.remove(glContext);
            if (null == glContext2) {
                throw new InternalError("State of ShareSet corrupted: Context " + toHexString(glContext.hashCode()) + " should have been in destroyed-set");
            }
            if (null != this.createdShares.put(glContext, glContext2)) {
                throw new InternalError("State of ShareSet corrupted: Context " + toHexString(glContext.hashCode()) + " shouldn't have been in created-set");
            }
        }
        
        public void contextDestroyed(final GLContext glContext) {
            final GLContext glContext2 = this.createdShares.remove(glContext);
            if (null == glContext2) {
                throw new InternalError("State of ShareSet corrupted: Context " + toHexString(glContext.hashCode()) + " should have been in created-set");
            }
            if (null != this.destroyedShares.put(glContext, glContext2)) {
                throw new InternalError("State of ShareSet corrupted: Context " + toHexString(glContext.hashCode()) + " shouldn't have been in destroyed-set");
            }
        }
    }
}
