// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLRendererQuirks;
import com.jogamp.opengl.GLProfile;
import com.jogamp.nativewindow.AbstractGraphicsScreen;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import com.jogamp.common.util.SourcedInterruptedException;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.InterruptedRuntimeException;
import com.jogamp.common.util.InterruptSource;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import java.util.HashSet;

public class SharedResourceRunner implements Runnable
{
    protected static final boolean DEBUG;
    final HashSet<String> devicesTried;
    final Implementation impl;
    Thread thread;
    boolean running;
    boolean ready;
    boolean shouldRelease;
    AbstractGraphicsDevice initDevice;
    AbstractGraphicsDevice releaseDevice;
    
    private boolean getDeviceTried(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return this.devicesTried.contains(abstractGraphicsDevice.getUniqueID());
    }
    
    private void addDeviceTried(final AbstractGraphicsDevice abstractGraphicsDevice) {
        this.devicesTried.add(abstractGraphicsDevice.getUniqueID());
    }
    
    private void removeDeviceTried(final AbstractGraphicsDevice abstractGraphicsDevice) {
        this.devicesTried.remove(abstractGraphicsDevice.getUniqueID());
    }
    
    public SharedResourceRunner(final Implementation impl) {
        this.devicesTried = new HashSet<String>();
        this.impl = impl;
        this.resetState();
    }
    
    private void resetState() {
        this.devicesTried.clear();
        this.thread = null;
        this.ready = false;
        this.running = false;
        this.shouldRelease = false;
        this.initDevice = null;
        this.releaseDevice = null;
    }
    
    public Thread start() {
        synchronized (this) {
            if (null != this.thread && !this.thread.isAlive()) {
                if (SharedResourceRunner.DEBUG) {
                    System.err.println("SharedResourceRunner.start() - dead-old-thread cleanup - " + getThreadName());
                }
                this.releaseSharedResources();
                this.thread = null;
                this.running = false;
            }
            if (null == this.thread) {
                if (SharedResourceRunner.DEBUG) {
                    System.err.println("SharedResourceRunner.start() - start new Thread - " + getThreadName());
                }
                this.resetState();
                (this.thread = new InterruptSource.Thread(null, this, getThreadName() + "-SharedResourceRunner")).setDaemon(true);
                this.thread.start();
                try {
                    while (!this.running) {
                        this.wait();
                    }
                }
                catch (InterruptedException ex) {
                    this.shouldRelease = true;
                    this.notifyAll();
                    throw new InterruptedRuntimeException(ex);
                }
            }
        }
        return this.thread;
    }
    
    public void stop() {
        synchronized (this) {
            if (null != this.thread) {
                if (SharedResourceRunner.DEBUG) {
                    System.err.println("SharedResourceRunner.stop() - " + getThreadName());
                }
                synchronized (this) {
                    this.shouldRelease = true;
                    this.notifyAll();
                    try {
                        while (this.running) {
                            this.wait();
                        }
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                }
            }
        }
    }
    
    public Resource getOrCreateShared(final AbstractGraphicsDevice abstractGraphicsDevice) {
        Resource resource = null;
        if (null != abstractGraphicsDevice) {
            synchronized (this) {
                this.start();
                resource = this.impl.mapGet(abstractGraphicsDevice);
                if (null == resource && !this.getDeviceTried(abstractGraphicsDevice)) {
                    this.addDeviceTried(abstractGraphicsDevice);
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.getOrCreateShared() " + abstractGraphicsDevice + ": trying - " + getThreadName());
                        ExceptionUtils.dumpStack(System.err);
                    }
                    if (this.impl.isDeviceSupported(abstractGraphicsDevice)) {
                        try {
                            this.doAndWait(abstractGraphicsDevice, null);
                        }
                        catch (InterruptedException ex) {
                            throw new InterruptedRuntimeException(ex);
                        }
                        resource = this.impl.mapGet(abstractGraphicsDevice);
                    }
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.getOrCreateShared() " + abstractGraphicsDevice + ": " + ((null != resource) ? "success" : "failed") + " - " + getThreadName());
                    }
                }
            }
        }
        return resource;
    }
    
    public Resource releaseShared(final AbstractGraphicsDevice abstractGraphicsDevice) {
        Resource mapGet = null;
        if (null != abstractGraphicsDevice) {
            synchronized (this) {
                mapGet = this.impl.mapGet(abstractGraphicsDevice);
                if (null != mapGet) {
                    this.removeDeviceTried(abstractGraphicsDevice);
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.releaseShared() " + abstractGraphicsDevice + ": trying - " + getThreadName());
                    }
                    try {
                        this.doAndWait(null, abstractGraphicsDevice);
                    }
                    catch (InterruptedException ex) {
                        throw new InterruptedRuntimeException(ex);
                    }
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.releaseShared() " + abstractGraphicsDevice + ": done - " + getThreadName());
                    }
                }
            }
        }
        return mapGet;
    }
    
    private final void doAndWait(final AbstractGraphicsDevice initDevice, final AbstractGraphicsDevice releaseDevice) throws InterruptedException {
        synchronized (this) {
            final String threadName = getThreadName();
            if (SharedResourceRunner.DEBUG) {
                System.err.println("SharedResourceRunner.doAndWait() START init: " + initDevice + ", release: " + releaseDevice + " - " + threadName);
            }
            try {
                while (!this.ready && this.running) {
                    this.wait();
                }
                if (SharedResourceRunner.DEBUG) {
                    System.err.println("SharedResourceRunner.doAndWait() set command: " + initDevice + ", release: " + releaseDevice + " - " + threadName);
                }
                this.initDevice = initDevice;
                this.releaseDevice = releaseDevice;
                this.notifyAll();
                while (this.running && (!this.ready || null != this.initDevice || null != this.releaseDevice)) {
                    this.wait();
                }
            }
            catch (InterruptedException ex) {
                final InterruptedException wrap = SourcedInterruptedException.wrap(ex);
                if (SharedResourceRunner.DEBUG) {
                    System.err.println("SharedResourceRunner.doAndWait() INTERRUPT init: " + initDevice + ", release: " + releaseDevice + " - " + threadName);
                    ExceptionUtils.dumpThrowable("", wrap);
                }
                final AbstractGraphicsDevice initDevice2 = this.initDevice;
                if (null != initDevice2) {
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.doAndWait() Cleanup init: " + initDevice2 + " -> release: " + this.releaseDevice + " - " + threadName);
                    }
                    this.releaseDevice = initDevice2;
                    this.initDevice = null;
                    this.notifyAll();
                }
                throw wrap;
            }
            if (SharedResourceRunner.DEBUG) {
                System.err.println("SharedResourceRunner.doAndWait() END init: " + initDevice + ", release: " + releaseDevice + " - " + threadName);
            }
        }
    }
    
    @Override
    public final void run() {
        final String threadName = getThreadName();
        if (SharedResourceRunner.DEBUG) {
            System.err.println("SharedResourceRunner.run(): STARTED - " + threadName);
        }
        synchronized (this) {
            this.running = true;
            while (!this.shouldRelease) {
                try {
                    this.ready = true;
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.run(): READY - " + threadName);
                    }
                    this.notifyAll();
                    while (!this.shouldRelease && null == this.initDevice && null == this.releaseDevice) {
                        this.wait();
                    }
                }
                catch (InterruptedException ex) {
                    this.shouldRelease = true;
                    ExceptionUtils.dumpThrowable("handled", SourcedInterruptedException.wrap(ex));
                }
                this.ready = false;
                if (!this.shouldRelease) {
                    if (SharedResourceRunner.DEBUG) {
                        System.err.println("SharedResourceRunner.run(): WOKE UP for device connection init: " + this.initDevice + ", release: " + this.releaseDevice + " - " + threadName);
                    }
                    if (null != this.initDevice) {
                        if (SharedResourceRunner.DEBUG) {
                            System.err.println("SharedResourceRunner.run(): create Shared for: " + this.initDevice + " - " + threadName);
                        }
                        Resource sharedResource = null;
                        try {
                            sharedResource = this.impl.createSharedResource(this.initDevice);
                        }
                        catch (Exception ex2) {
                            ExceptionUtils.dumpThrowable("handled", ex2);
                        }
                        if (null != sharedResource) {
                            this.impl.mapPut(this.initDevice, sharedResource);
                        }
                    }
                    if (null != this.releaseDevice) {
                        if (SharedResourceRunner.DEBUG) {
                            System.err.println("SharedResourceRunner.run(): release Shared for: " + this.releaseDevice + " - " + threadName);
                        }
                        final Resource mapGet = this.impl.mapGet(this.releaseDevice);
                        if (null != mapGet) {
                            try {
                                this.impl.releaseSharedResource(mapGet);
                            }
                            catch (Exception ex3) {
                                ExceptionUtils.dumpThrowable("handled", ex3);
                            }
                            finally {
                                this.impl.mapPut(this.releaseDevice, null);
                            }
                        }
                    }
                }
                this.initDevice = null;
                this.releaseDevice = null;
            }
            if (SharedResourceRunner.DEBUG) {
                System.err.println("SharedResourceRunner.run(): RELEASE START - " + threadName);
            }
            this.releaseSharedResources();
            if (SharedResourceRunner.DEBUG) {
                System.err.println("SharedResourceRunner.run(): RELEASE END - " + threadName);
            }
            this.shouldRelease = false;
            this.running = false;
            this.thread = null;
            this.notifyAll();
        }
    }
    
    private void releaseSharedResources() {
        this.devicesTried.clear();
        final Iterator<Resource> iterator = this.impl.mapValues().iterator();
        while (iterator.hasNext()) {
            try {
                this.impl.releaseSharedResource(iterator.next());
            }
            catch (Throwable t) {
                ExceptionUtils.dumpThrowable("", t);
            }
        }
        this.impl.clear();
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        DEBUG = GLDrawableImpl.DEBUG;
    }
    
    public abstract static class AImplementation implements Implementation
    {
        private final HashMap<String, Resource> sharedMap;
        
        public AImplementation() {
            this.sharedMap = new HashMap<String, Resource>();
        }
        
        public Map<String, Resource> getSharedMap() {
            return this.sharedMap;
        }
        
        @Override
        public final void clear() {
            this.sharedMap.clear();
        }
        
        @Override
        public final Resource mapPut(final AbstractGraphicsDevice abstractGraphicsDevice, final Resource resource) {
            return this.sharedMap.put(abstractGraphicsDevice.getUniqueID(), resource);
        }
        
        @Override
        public final Resource mapGet(final AbstractGraphicsDevice abstractGraphicsDevice) {
            return this.sharedMap.get(abstractGraphicsDevice.getUniqueID());
        }
        
        @Override
        public final Collection<Resource> mapValues() {
            return this.sharedMap.values();
        }
    }
    
    public interface Resource
    {
        boolean isAvailable();
        
        AbstractGraphicsDevice getDevice();
        
        AbstractGraphicsScreen getScreen();
        
        GLDrawableImpl getDrawable();
        
        GLContextImpl getContext();
        
        GLRendererQuirks getRendererQuirks(final GLProfile p0);
    }
    
    public interface Implementation
    {
        boolean isDeviceSupported(final AbstractGraphicsDevice p0);
        
        Resource createSharedResource(final AbstractGraphicsDevice p0);
        
        void releaseSharedResource(final Resource p0);
        
        void clear();
        
        Resource mapPut(final AbstractGraphicsDevice p0, final Resource p1);
        
        Resource mapGet(final AbstractGraphicsDevice p0);
        
        Collection<Resource> mapValues();
    }
}
