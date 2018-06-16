// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

public class RegisteredClass
{
    private final long hInstance;
    private final String className;
    private final long hDDTCtx;
    
    RegisteredClass(final long hInstance, final String className, final long hddtCtx) {
        this.hInstance = hInstance;
        this.className = className;
        this.hDDTCtx = hddtCtx;
    }
    
    public final long getHInstance() {
        return this.hInstance;
    }
    
    public final String getName() {
        return this.className;
    }
    
    public final long getHDispThreadContext() {
        return this.hDDTCtx;
    }
    
    @Override
    public final String toString() {
        return "RegisteredClass[handle 0x" + Long.toHexString(this.hInstance) + ", " + this.className + ", dtx 0x" + Long.toHexString(this.hDDTCtx) + "]";
    }
}
