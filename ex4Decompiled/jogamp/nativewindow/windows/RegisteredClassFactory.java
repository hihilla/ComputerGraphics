// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.nativewindow.Debug;
import com.jogamp.nativewindow.NativeWindowException;
import java.util.ArrayList;

public class RegisteredClassFactory
{
    private static final boolean DEBUG;
    private static final ArrayList<RegisteredClassFactory> registeredFactories;
    private static final long hInstance;
    private final String classBaseName;
    private final long wndProc;
    private final boolean useDummyDispatchThread;
    private final long iconSmallHandle;
    private final long iconBigHandle;
    private RegisteredClass sharedClass;
    private int classIter;
    private int sharedRefCount;
    private final Object sync;
    
    private String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    @Override
    public final String toString() {
        return "RegisteredClassFactory[moduleHandle " + this.toHexString(RegisteredClassFactory.hInstance) + ", " + this.classBaseName + ", wndProc " + this.toHexString(this.wndProc) + ", useDDT " + this.useDummyDispatchThread + ", shared[refCount " + this.sharedRefCount + ", class " + this.sharedClass + "]]";
    }
    
    public static void shutdownSharedClasses() {
        synchronized (RegisteredClassFactory.registeredFactories) {
            if (RegisteredClassFactory.DEBUG) {
                System.err.println("RegisteredClassFactory.shutdownSharedClasses: " + RegisteredClassFactory.registeredFactories.size());
            }
            for (int i = 0; i < RegisteredClassFactory.registeredFactories.size(); ++i) {
                final RegisteredClassFactory registeredClassFactory = RegisteredClassFactory.registeredFactories.get(i);
                synchronized (registeredClassFactory.sync) {
                    if (null != registeredClassFactory.sharedClass) {
                        GDIUtil.DestroyWindowClass0(registeredClassFactory.sharedClass.getHInstance(), registeredClassFactory.sharedClass.getName(), registeredClassFactory.sharedClass.getHDispThreadContext());
                        registeredClassFactory.sharedClass = null;
                        registeredClassFactory.sharedRefCount = 0;
                        registeredClassFactory.classIter = 0;
                        if (RegisteredClassFactory.DEBUG) {
                            System.err.println("RegisteredClassFactory #" + i + "/" + RegisteredClassFactory.registeredFactories.size() + ": shutdownSharedClasses : " + registeredClassFactory.sharedClass);
                        }
                    }
                    else if (RegisteredClassFactory.DEBUG) {
                        System.err.println("RegisteredClassFactory #" + i + "/" + RegisteredClassFactory.registeredFactories.size() + ": null");
                    }
                }
            }
        }
    }
    
    public static long getHInstance() {
        return RegisteredClassFactory.hInstance;
    }
    
    public RegisteredClassFactory(final String classBaseName, final long wndProc, final boolean useDummyDispatchThread, final long iconSmallHandle, final long iconBigHandle) {
        this.sharedClass = null;
        this.classIter = 0;
        this.sharedRefCount = 0;
        this.sync = new Object();
        this.classBaseName = classBaseName;
        this.wndProc = wndProc;
        this.useDummyDispatchThread = useDummyDispatchThread;
        this.iconSmallHandle = iconSmallHandle;
        this.iconBigHandle = iconBigHandle;
        synchronized (RegisteredClassFactory.registeredFactories) {
            RegisteredClassFactory.registeredFactories.add(this);
        }
    }
    
    public RegisteredClass getSharedClass() throws NativeWindowException {
        synchronized (this.sync) {
            if (0 == this.sharedRefCount) {
                if (null != this.sharedClass) {
                    throw new InternalError("Error (" + this.sharedRefCount + "): SharedClass not null: " + this.sharedClass);
                }
                String string = null;
                boolean createWindowClass0 = false;
                for (int n = this.classIter - 1; !createWindowClass0 && n != this.classIter; createWindowClass0 = GDIUtil.CreateWindowClass0(RegisteredClassFactory.hInstance, string, this.wndProc, this.iconSmallHandle, this.iconBigHandle)) {
                    string = this.classBaseName + this.classIter;
                    ++this.classIter;
                }
                if (!createWindowClass0) {
                    throw new NativeWindowException("Error: Could not create WindowClass: " + string);
                }
                long createDummyDispatchThread0;
                if (this.useDummyDispatchThread) {
                    createDummyDispatchThread0 = GDIUtil.CreateDummyDispatchThread0();
                    if (0L == createDummyDispatchThread0) {
                        throw new NativeWindowException("Error: Could not create DDT " + string);
                    }
                }
                else {
                    createDummyDispatchThread0 = 0L;
                }
                this.sharedClass = new RegisteredClass(RegisteredClassFactory.hInstance, string, createDummyDispatchThread0);
                if (RegisteredClassFactory.DEBUG) {
                    System.err.println("RegisteredClassFactory getSharedClass (" + this.sharedRefCount + ") initialized: " + this.sharedClass);
                }
            }
            else if (null == this.sharedClass) {
                throw new InternalError("Error (" + this.sharedRefCount + "): SharedClass is null");
            }
            ++this.sharedRefCount;
        }
        return this.sharedClass;
    }
    
    public void releaseSharedClass() {
        synchronized (this.sync) {
            if (0 == this.sharedRefCount) {
                if (null != this.sharedClass) {
                    throw new InternalError("Error (" + this.sharedRefCount + "): SharedClass not null: " + this.sharedClass);
                }
            }
            else {
                --this.sharedRefCount;
                if (null == this.sharedClass) {
                    throw new InternalError("Error (" + this.sharedRefCount + "): SharedClass is null");
                }
                if (0 == this.sharedRefCount) {
                    GDIUtil.DestroyWindowClass0(this.sharedClass.getHInstance(), this.sharedClass.getName(), this.sharedClass.getHDispThreadContext());
                    if (RegisteredClassFactory.DEBUG) {
                        System.err.println("RegisteredClassFactory releaseSharedClass (" + this.sharedRefCount + ") released: " + this.sharedClass);
                    }
                    this.sharedClass = null;
                    this.sharedRefCount = 0;
                    this.classIter = 0;
                }
            }
        }
    }
    
    public int getSharedRefCount() {
        return this.sharedRefCount;
    }
    
    static {
        DEBUG = Debug.debug("RegisteredClass");
        hInstance = GDI.GetApplicationHandle();
        if (0L == RegisteredClassFactory.hInstance) {
            throw new NativeWindowException("Error: Null ModuleHandle for Application");
        }
        registeredFactories = new ArrayList<RegisteredClassFactory>();
    }
}
