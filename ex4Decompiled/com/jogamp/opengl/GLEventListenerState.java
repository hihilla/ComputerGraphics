// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import jogamp.opengl.Debug;
import java.util.List;
import com.jogamp.nativewindow.MutableGraphicsConfiguration;
import com.jogamp.nativewindow.DefaultGraphicsDevice;
import com.jogamp.opengl.util.GLDrawableUtil;
import java.util.ArrayList;
import com.jogamp.nativewindow.AbstractGraphicsConfiguration;
import com.jogamp.nativewindow.ProxySurface;
import com.jogamp.nativewindow.NativeSurface;
import com.jogamp.common.util.locks.RecursiveLock;
import com.jogamp.nativewindow.AbstractGraphicsDevice;

public class GLEventListenerState
{
    private static final boolean DEBUG;
    public final AbstractGraphicsDevice upstreamDevice;
    public final boolean proxyOwnsUpstreamDevice;
    public final AbstractGraphicsDevice device;
    public final GLCapabilitiesImmutable caps;
    public final GLContext context;
    public final GLEventListener[] listeners;
    public final boolean[] listenersInit;
    public final GLAnimatorControl anim;
    public final boolean animStarted;
    private volatile RecursiveLock upstreamLock;
    private volatile NativeSurface lockedSurface;
    private volatile boolean owner;
    private final Runnable unlockOp;
    private static final GLRunnable setViewport;
    private static final GLRunnable glFinish;
    
    private GLEventListenerState(final AbstractGraphicsDevice upstreamDevice, final boolean proxyOwnsUpstreamDevice, final AbstractGraphicsDevice device, final GLCapabilitiesImmutable caps, final RecursiveLock upstreamLock, final NativeSurface lockedSurface, final GLContext context, final int n, final GLAnimatorControl anim, final boolean animStarted) {
        this.unlockOp = new Runnable() {
            @Override
            public void run() {
                final RecursiveLock access$000 = GLEventListenerState.this.upstreamLock;
                final NativeSurface access$2 = GLEventListenerState.this.lockedSurface;
                GLEventListenerState.this.upstreamLock = null;
                GLEventListenerState.this.lockedSurface = null;
                if (null != access$000) {
                    access$000.unlock();
                }
                if (null != access$2) {
                    access$2.unlockSurface();
                }
            }
        };
        this.upstreamDevice = upstreamDevice;
        this.proxyOwnsUpstreamDevice = proxyOwnsUpstreamDevice;
        this.device = device;
        this.caps = caps;
        this.upstreamLock = upstreamLock;
        this.lockedSurface = lockedSurface;
        this.context = context;
        this.listeners = new GLEventListener[n];
        this.listenersInit = new boolean[n];
        this.anim = anim;
        this.animStarted = animStarted;
        this.owner = true;
    }
    
    public final boolean isOwner() {
        return this.owner;
    }
    
    public final int listenerCount() {
        return this.listeners.length;
    }
    
    public Runnable getUnlockSurfaceOp() {
        return this.unlockOp;
    }
    
    public void destroy() {
        if (this.owner) {
            for (int listenerCount = this.listenerCount(), i = 0; i < listenerCount; ++i) {
                this.listeners[i] = null;
            }
            this.unlockOp.run();
            this.device.close();
            this.owner = false;
        }
    }
    
    private static AbstractGraphicsDevice cloneDevice(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return (AbstractGraphicsDevice)abstractGraphicsDevice.clone();
    }
    
    public static GLEventListenerState moveFrom(final GLAutoDrawable glAutoDrawable) {
        return moveFrom(glAutoDrawable, false);
    }
    
    public static GLEventListenerState moveFrom(final GLAutoDrawable glAutoDrawable, final boolean b) {
        final GLAnimatorControl animator = glAutoDrawable.getAnimator();
        boolean started;
        if (null != animator) {
            started = animator.isStarted();
            animator.remove(glAutoDrawable);
        }
        else {
            started = false;
        }
        final RecursiveLock upstreamLock = glAutoDrawable.getUpstreamLock();
        upstreamLock.lock();
        GLEventListenerState glEventListenerState;
        try {
            final NativeSurface nativeSurface = glAutoDrawable.getNativeSurface();
            final boolean b2 = 1 < nativeSurface.lockSurface();
            if (glAutoDrawable.isRealized() && !b2) {
                throw new GLException("Could not lock realized surface " + glAutoDrawable);
            }
            try {
                final int glEventListenerCount = glAutoDrawable.getGLEventListenerCount();
                final AbstractGraphicsConfiguration graphicsConfiguration = nativeSurface.getGraphicsConfiguration();
                final GLCapabilitiesImmutable glCapabilitiesImmutable = (GLCapabilitiesImmutable)graphicsConfiguration.getChosenCapabilities();
                final AbstractGraphicsDevice device = graphicsConfiguration.getScreen().getDevice();
                final AbstractGraphicsDevice cloneDevice = cloneDevice(device);
                device.clearHandleOwner();
                if (GLEventListenerState.DEBUG) {
                    System.err.println("GLEventListenerState.moveFrom.0a: orig 0x" + Integer.toHexString(device.hashCode()) + ", " + device);
                    System.err.println("GLEventListenerState.moveFrom.0b: pres 0x" + Integer.toHexString(cloneDevice.hashCode()) + ", " + cloneDevice);
                    System.err.println("GLEventListenerState.moveFrom.1: " + ((ProxySurface)nativeSurface).getClass().getName());
                }
                AbstractGraphicsDevice cloneDevice2 = null;
                boolean containsUpstreamOptionBits;
                if (nativeSurface instanceof ProxySurface) {
                    final ProxySurface proxySurface = (ProxySurface)nativeSurface;
                    containsUpstreamOptionBits = proxySurface.containsUpstreamOptionBits(128);
                    final NativeSurface upstreamSurface = proxySurface.getUpstreamSurface();
                    if (GLEventListenerState.DEBUG && null != upstreamSurface) {
                        System.err.println("GLEventListenerState.moveFrom.2: " + upstreamSurface.getClass().getName() + ", " + upstreamSurface);
                    }
                    if (null != upstreamSurface) {
                        final AbstractGraphicsDevice device2 = upstreamSurface.getGraphicsConfiguration().getScreen().getDevice();
                        cloneDevice2 = cloneDevice(device2);
                        device2.clearHandleOwner();
                        if (GLEventListenerState.DEBUG) {
                            System.err.println("GLEventListenerState.moveFrom.3a: up-orig 0x" + Integer.toHexString(device2.hashCode()) + ", " + device2);
                            System.err.println("GLEventListenerState.moveFrom.3b: up-pres 0x" + Integer.toHexString(cloneDevice2.hashCode()) + ", " + cloneDevice2);
                            System.err.println("GLEventListenerState.moveFrom.3c: " + ((ProxySurface)nativeSurface).getClass().getName() + ", " + nativeSurface);
                            System.err.println("GLEventListenerState.moveFrom.3d: " + upstreamSurface.getClass().getName() + proxySurface.getUpstreamOptionBits(null).toString());
                        }
                    }
                }
                else {
                    containsUpstreamOptionBits = false;
                }
                glEventListenerState = new GLEventListenerState(cloneDevice2, containsUpstreamOptionBits, cloneDevice, glCapabilitiesImmutable, b ? upstreamLock : null, (b2 && b) ? nativeSurface : null, glAutoDrawable.getContext(), glEventListenerCount, animator, started);
                for (int i = 0; i < glEventListenerCount; ++i) {
                    final GLEventListener glEventListener = glAutoDrawable.getGLEventListener(0);
                    glEventListenerState.listenersInit[i] = glAutoDrawable.getGLEventListenerInitState(glEventListener);
                    glEventListenerState.listeners[i] = glAutoDrawable.removeGLEventListener(glEventListener);
                }
                glAutoDrawable.setContext(null, false);
            }
            finally {
                if (b2 && !b) {
                    nativeSurface.unlockSurface();
                }
            }
        }
        finally {
            if (!b) {
                upstreamLock.unlock();
            }
        }
        return glEventListenerState;
    }
    
    public final void moveTo(final GLAutoDrawable glAutoDrawable) throws GLException {
        this.moveTo(glAutoDrawable, null);
    }
    
    public final void moveTo(final GLAutoDrawable glAutoDrawable, final Runnable runnable) throws GLException {
        final GLAnimatorControl animator = glAutoDrawable.getAnimator();
        boolean pause;
        if (null != animator) {
            pause = animator.pause();
            animator.remove(glAutoDrawable);
        }
        else {
            pause = false;
        }
        final ArrayList<GLDrawableUtil.ReshapeGLEventListener> list = new ArrayList<GLDrawableUtil.ReshapeGLEventListener>();
        final int listenerCount = this.listenerCount();
        final RecursiveLock upstreamLock = glAutoDrawable.getUpstreamLock();
        upstreamLock.lock();
        boolean realized;
        try {
            final NativeSurface nativeSurface = glAutoDrawable.getNativeSurface();
            final boolean b = 1 < nativeSurface.lockSurface();
            if (glAutoDrawable.isRealized() && !b) {
                throw new GLException("Could not lock realized surface " + glAutoDrawable);
            }
            try {
                final DefaultGraphicsDevice defaultGraphicsDevice = (DefaultGraphicsDevice)((MutableGraphicsConfiguration)nativeSurface.getGraphicsConfiguration()).getScreen().getDevice();
                final DefaultGraphicsDevice defaultGraphicsDevice2 = (DefaultGraphicsDevice)this.device;
                if (!defaultGraphicsDevice.getUniqueID().equals(defaultGraphicsDevice2.getUniqueID())) {
                    throw new GLException("Incompatible devices: Preserved <" + defaultGraphicsDevice2.getUniqueID() + ">, target <" + defaultGraphicsDevice.getUniqueID() + ">");
                }
                ProxySurface proxySurface;
                NativeSurface upstreamSurface;
                if (nativeSurface instanceof ProxySurface) {
                    proxySurface = (ProxySurface)nativeSurface;
                    upstreamSurface = proxySurface.getUpstreamSurface();
                }
                else {
                    proxySurface = null;
                    upstreamSurface = null;
                }
                if (GLEventListenerState.DEBUG) {
                    System.err.println("GLEventListenerState.moveTo.0 : has aProxy " + (null != proxySurface));
                    System.err.println("GLEventListenerState.moveTo.0 : has aUpSurface " + (null != upstreamSurface));
                }
                if (null == upstreamSurface && null != this.upstreamDevice) {
                    throw new GLException("Incompatible Surface config - Has Upstream-Surface: Prev-Holder = true, New-Holder = false");
                }
                glAutoDrawable.setContext(null, true);
                realized = glAutoDrawable.isRealized();
                if (realized && null != upstreamSurface) {
                    glAutoDrawable.getDelegatedDrawable().setRealized(false);
                }
                if (GLEventListenerState.DEBUG) {
                    System.err.println("GLEventListenerState.moveTo.0a: orig 0x" + Integer.toHexString(defaultGraphicsDevice.hashCode()) + ", " + defaultGraphicsDevice);
                    System.err.println("GLEventListenerState.moveTo.0b: pres 0x" + Integer.toHexString(defaultGraphicsDevice2.hashCode()) + ", " + defaultGraphicsDevice2);
                }
                DefaultGraphicsDevice.swapDeviceHandleAndOwnership(defaultGraphicsDevice, defaultGraphicsDevice2);
                defaultGraphicsDevice2.close();
                if (GLEventListenerState.DEBUG) {
                    System.err.println("GLEventListenerState.moveTo.1a: orig 0x" + Integer.toHexString(defaultGraphicsDevice.hashCode()) + ", " + defaultGraphicsDevice);
                    System.err.println("GLEventListenerState.moveTo.1b: pres 0x" + Integer.toHexString(defaultGraphicsDevice2.hashCode()) + ", " + defaultGraphicsDevice2);
                }
                if (null != upstreamSurface) {
                    final MutableGraphicsConfiguration mutableGraphicsConfiguration = (MutableGraphicsConfiguration)upstreamSurface.getGraphicsConfiguration();
                    if (null == this.upstreamDevice) {
                        throw new GLException("Incompatible Surface config - Has Upstream-Surface: Prev-Holder = false, New-Holder = true");
                    }
                    final DefaultGraphicsDevice defaultGraphicsDevice3 = (DefaultGraphicsDevice)mutableGraphicsConfiguration.getScreen().getDevice();
                    final DefaultGraphicsDevice defaultGraphicsDevice4 = (DefaultGraphicsDevice)this.upstreamDevice;
                    if (!defaultGraphicsDevice3.getUniqueID().equals(defaultGraphicsDevice4.getUniqueID())) {
                        throw new GLException("Incompatible updtream devices: Preserved <" + defaultGraphicsDevice4.getUniqueID() + ">, target <" + defaultGraphicsDevice3.getUniqueID() + ">");
                    }
                    if (GLEventListenerState.DEBUG) {
                        System.err.println("GLEventListenerState.moveTo.2a: up-orig 0x" + Integer.toHexString(defaultGraphicsDevice3.hashCode()) + ", " + defaultGraphicsDevice3);
                        System.err.println("GLEventListenerState.moveTo.2b: up-pres 0x" + Integer.toHexString(defaultGraphicsDevice4.hashCode()) + ", " + defaultGraphicsDevice4);
                        System.err.println("GLEventListenerState.moveTo.2c:  " + upstreamSurface.getClass().getName() + proxySurface.getUpstreamOptionBits(null).toString());
                    }
                    DefaultGraphicsDevice.swapDeviceHandleAndOwnership(defaultGraphicsDevice3, defaultGraphicsDevice4);
                    defaultGraphicsDevice4.close();
                    if (this.proxyOwnsUpstreamDevice) {
                        proxySurface.addUpstreamOptionBits(128);
                    }
                    if (GLEventListenerState.DEBUG) {
                        System.err.println("GLEventListenerState.moveTo.3a: up-orig 0x" + Integer.toHexString(defaultGraphicsDevice3.hashCode()) + ", " + defaultGraphicsDevice3);
                        System.err.println("GLEventListenerState.moveTo.3b: up-pres 0x" + Integer.toHexString(defaultGraphicsDevice4.hashCode()) + ", " + defaultGraphicsDevice4);
                        System.err.println("GLEventListenerState.moveTo.3c:  " + upstreamSurface.getClass().getName() + proxySurface.getUpstreamOptionBits(null).toString());
                    }
                }
                if (realized && null != upstreamSurface) {
                    glAutoDrawable.getDelegatedDrawable().setRealized(true);
                }
                if (GLEventListenerState.DEBUG) {
                    System.err.println("GLEventListenerState.moveTo.X : has aProxy " + (null != proxySurface));
                    System.err.println("GLEventListenerState.moveTo.X : has aUpSurface " + (null != upstreamSurface));
                }
                glAutoDrawable.setContext(this.context, false);
            }
            finally {
                if (b) {
                    nativeSurface.unlockSurface();
                }
            }
        }
        finally {
            upstreamLock.unlock();
        }
        if (null != runnable) {
            runnable.run();
        }
        this.owner = false;
        list.add((GLDrawableUtil.ReshapeGLEventListener)GLEventListenerState.setViewport);
        for (int i = 0; i < listenerCount; ++i) {
            if (this.listenersInit[i]) {
                list.add(new GLDrawableUtil.ReshapeGLEventListener(this.listeners[i], false));
            }
        }
        list.add((GLDrawableUtil.ReshapeGLEventListener)GLEventListenerState.glFinish);
        glAutoDrawable.invoke(realized, (List<GLRunnable>)list);
        for (int j = 0; j < listenerCount; ++j) {
            final GLEventListener glEventListener = this.listeners[j];
            glAutoDrawable.addGLEventListener(glEventListener);
            glAutoDrawable.setGLEventListenerInitState(glEventListener, this.listenersInit[j]);
            this.listeners[j] = null;
        }
        if (null != animator) {
            animator.add(glAutoDrawable);
            if (pause) {
                animator.resume();
            }
        }
        else if (null != this.anim) {
            this.anim.add(glAutoDrawable);
            if (this.animStarted) {
                this.anim.start();
            }
        }
    }
    
    static {
        DEBUG = (Debug.debug("GLDrawable") || Debug.debug("GLEventListenerState"));
        setViewport = new GLRunnable() {
            @Override
            public boolean run(final GLAutoDrawable glAutoDrawable) {
                glAutoDrawable.getGL().glViewport(0, 0, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
                return true;
            }
        };
        glFinish = new GLRunnable() {
            @Override
            public boolean run(final GLAutoDrawable glAutoDrawable) {
                glAutoDrawable.getGL().glFinish();
                return true;
            }
        };
    }
}
