// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.nativewindow.NativeWindowException;
import com.jogamp.opengl.GLContext;
import java.util.ArrayList;
import com.jogamp.opengl.GLDebugMessage;
import com.jogamp.opengl.GLException;
import com.jogamp.common.os.Platform;
import jogamp.common.os.PlatformPropsImpl;
import java.security.AccessController;
import java.security.PrivilegedAction;
import com.jogamp.gluegen.runtime.ProcAddressTable;
import com.jogamp.opengl.GLDebugListener;

public class GLDebugMessageHandler
{
    private static final boolean DEBUG;
    private static final int EXT_KHR = 1;
    private static final int EXT_ARB = 2;
    private static final int EXT_AMD = 3;
    private final GLContextImpl ctx;
    private final ListenerSyncedImplStub<GLDebugListener> listenerImpl;
    private String extName;
    private String extSuffix;
    private int extType;
    private long glDebugMessageCallbackProcAddress;
    private boolean extAvailable;
    private boolean synchronous;
    private long handle;
    
    public GLDebugMessageHandler(final GLContextImpl ctx) {
        this.ctx = ctx;
        this.listenerImpl = new ListenerSyncedImplStub<GLDebugListener>();
        this.glDebugMessageCallbackProcAddress = 0L;
        this.extName = null;
        this.extSuffix = null;
        this.extType = 0;
        this.extAvailable = false;
        this.handle = 0L;
        this.synchronous = true;
    }
    
    public void init(final boolean b) {
        if (GLDebugMessageHandler.DEBUG) {
            System.err.println("GLDebugMessageHandler.init(" + b + ")");
        }
        this.init();
        if (this.isAvailable()) {
            this.enableImpl(b);
        }
        else if (GLDebugMessageHandler.DEBUG) {
            System.err.println("GLDebugMessageHandler.init(" + b + ") .. n/a");
        }
    }
    
    private final long getAddressFor(final ProcAddressTable procAddressTable, final String s) {
        return AccessController.doPrivileged((PrivilegedAction<Long>)new PrivilegedAction<Long>() {
            @Override
            public Long run() {
                try {
                    return procAddressTable.getAddressFor(s);
                }
                catch (IllegalArgumentException ex) {
                    return 0L;
                }
            }
        });
    }
    
    public void init() {
        this.ctx.validateCurrent();
        if (this.isAvailable()) {
            return;
        }
        if (!this.ctx.isGLDebugEnabled()) {
            if (GLDebugMessageHandler.DEBUG) {
                System.err.println("GLDebugMessageHandler: GL DEBUG not set in ARB ctx options: " + this.ctx.getGLVersion());
            }
            return;
        }
        if (PlatformPropsImpl.OS_TYPE == Platform.OSType.WINDOWS && Platform.is32Bit()) {
            if (GLDebugMessageHandler.DEBUG) {
                System.err.println("GLDebugMessageHandler: Windows 32bit currently not supported!");
            }
            return;
        }
        if (this.ctx.isExtensionAvailable("GL_KHR_debug")) {
            this.extName = "GL_KHR_debug";
            this.extSuffix = (this.ctx.isGLES() ? "KHR" : "");
            this.extType = 1;
        }
        else if (this.ctx.isExtensionAvailable("GL_ARB_debug_output")) {
            this.extName = "GL_ARB_debug_output";
            this.extSuffix = "ARB";
            this.extType = 2;
        }
        else if (this.ctx.isExtensionAvailable("GL_AMD_debug_output")) {
            this.extName = "GL_AMD_debug_output";
            this.extSuffix = "AMD";
            this.extType = 3;
        }
        switch (this.extType) {
            case 1: {
                if (!this.ctx.isGL2ES2()) {
                    if (GLDebugMessageHandler.DEBUG) {
                        System.err.println("Non GL2ES2 context not supported, has " + this.ctx.getGLVersion());
                    }
                    this.extType = 0;
                    break;
                }
                break;
            }
            case 2:
            case 3: {
                if (!this.ctx.isGL2GL3()) {
                    if (GLDebugMessageHandler.DEBUG) {
                        System.err.println("Non GL2GL3 context not supported, has " + this.ctx.getGLVersion());
                    }
                    this.extType = 0;
                    break;
                }
                break;
            }
        }
        if (0 == this.extType) {
            this.extName = null;
            this.extSuffix = null;
            if (GLDebugMessageHandler.DEBUG) {
                System.err.println("GLDebugMessageHandler: No extension available! " + this.ctx.getGLVersion());
                System.err.println("GL_EXTENSIONS  " + this.ctx.getGLExtensionCount());
                System.err.println(this.ctx.getGLExtensionsString());
            }
            return;
        }
        if (GLDebugMessageHandler.DEBUG) {
            System.err.println("GLDebugMessageHandler: Using extension: <" + this.extName + "> with suffix <" + this.extSuffix + ">");
        }
        final ProcAddressTable glProcAddressTable = this.ctx.getGLProcAddressTable();
        switch (this.extType) {
            case 1: {
                this.glDebugMessageCallbackProcAddress = this.getAddressFor(glProcAddressTable, "glDebugMessageCallback" + this.extSuffix);
                break;
            }
            case 2: {
                this.glDebugMessageCallbackProcAddress = this.getAddressFor(glProcAddressTable, "glDebugMessageCallback" + this.extSuffix);
                break;
            }
            case 3: {
                this.glDebugMessageCallbackProcAddress = this.getAddressFor(glProcAddressTable, "glDebugMessageCallback" + this.extSuffix);
                break;
            }
        }
        this.extAvailable = (0 < this.extType && null != this.extName && null != this.extSuffix && 0L != this.glDebugMessageCallbackProcAddress);
        if (GLDebugMessageHandler.DEBUG) {
            System.err.println("GLDebugMessageHandler: extAvailable: " + this.extAvailable + ", glDebugMessageCallback* : 0x" + Long.toHexString(this.glDebugMessageCallbackProcAddress));
        }
        if (!this.extAvailable) {
            this.glDebugMessageCallbackProcAddress = 0L;
        }
        this.handle = 0L;
    }
    
    public final boolean isAvailable() {
        return this.extAvailable;
    }
    
    public final String getExtension() {
        return this.extName;
    }
    
    public final boolean isExtensionKHRARB() {
        return 1 == this.extType || 2 == this.extType;
    }
    
    public final boolean isExtensionKHR() {
        return 1 == this.extType;
    }
    
    public final boolean isExtensionARB() {
        return 2 == this.extType;
    }
    
    public final boolean isExtensionAMD() {
        return 3 == this.extType;
    }
    
    public final boolean isSynchronous() {
        return this.synchronous;
    }
    
    public final void setSynchronous(final boolean synchronous) {
        this.synchronous = synchronous;
        if (this.isEnabled()) {
            this.setSynchronousImpl();
        }
    }
    
    private final void setSynchronousImpl() {
        if (this.isExtensionKHRARB()) {
            if (this.synchronous) {
                this.ctx.getGL().glEnable(33346);
            }
            else {
                this.ctx.getGL().glDisable(33346);
            }
            if (GLDebugMessageHandler.DEBUG) {
                System.err.println("GLDebugMessageHandler: synchronous " + this.synchronous);
            }
        }
    }
    
    public final void enable(final boolean b) throws GLException {
        this.ctx.validateCurrent();
        if (!this.isAvailable()) {
            return;
        }
        this.enableImpl(b);
    }
    
    final void enableImpl(final boolean b) throws GLException {
        if (b) {
            if (0L == this.handle) {
                this.setSynchronousImpl();
                this.handle = this.register0(this.glDebugMessageCallbackProcAddress, this.extType);
                if (0L == this.handle) {
                    throw new GLException("Failed to register via \"glDebugMessageCallback*\" using " + this.extName);
                }
            }
        }
        else if (0L != this.handle) {
            this.unregister0(this.glDebugMessageCallbackProcAddress, this.handle);
            this.handle = 0L;
        }
        if (GLDebugMessageHandler.DEBUG) {
            System.err.println("GLDebugMessageHandler: enable(" + b + ") -> 0x" + Long.toHexString(this.handle));
        }
    }
    
    public final boolean isEnabled() {
        return 0L != this.handle;
    }
    
    public final int listenerSize() {
        return this.listenerImpl.size();
    }
    
    public final void addListener(final GLDebugListener glDebugListener) {
        this.listenerImpl.addListener(-1, glDebugListener);
    }
    
    public final void addListener(final int n, final GLDebugListener glDebugListener) {
        this.listenerImpl.addListener(n, glDebugListener);
    }
    
    public final void removeListener(final GLDebugListener glDebugListener) {
        this.listenerImpl.removeListener(glDebugListener);
    }
    
    private final void sendMessage(final GLDebugMessage glDebugMessage) {
        synchronized (this.listenerImpl) {
            if (GLDebugMessageHandler.DEBUG) {
                System.err.println("GLDebugMessageHandler: " + glDebugMessage);
            }
            final ArrayList<GLDebugListener> listeners = this.listenerImpl.getListeners();
            for (int i = 0; i < listeners.size(); ++i) {
                listeners.get(i).messageSent(glDebugMessage);
            }
        }
    }
    
    protected final void glDebugMessageARB(final int n, final int n2, final int n3, final int n4, final String s) {
        this.sendMessage(new GLDebugMessage(this.ctx, System.currentTimeMillis(), n, n2, n3, n4, s));
    }
    
    protected final void glDebugMessageAMD(final int n, final int n2, final int n3, final String s) {
        this.sendMessage(GLDebugMessage.translateAMDEvent(this.ctx, System.currentTimeMillis(), n, n2, n3, s));
    }
    
    private static native boolean initIDs0();
    
    private native long register0(final long p0, final int p1);
    
    private native void unregister0(final long p0, final long p1);
    
    static {
        DEBUG = Debug.debug("GLDebugMessageHandler");
        if (!initIDs0()) {
            throw new NativeWindowException("Failed to initialize GLDebugMessageHandler jmethodIDs");
        }
    }
    
    public static class StdErrGLDebugListener implements GLDebugListener
    {
        boolean threadDump;
        
        public StdErrGLDebugListener(final boolean threadDump) {
            this.threadDump = threadDump;
        }
        
        @Override
        public void messageSent(final GLDebugMessage glDebugMessage) {
            System.err.println(glDebugMessage);
            if (this.threadDump) {
                ExceptionUtils.dumpStack(System.err);
            }
        }
    }
}
