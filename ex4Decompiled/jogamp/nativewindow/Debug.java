// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.AccessControlException;
import com.jogamp.common.util.PropertyAccess;

public class Debug extends PropertyAccess
{
    private static final boolean verbose;
    private static final boolean debugAll;
    
    public static final void initSingleton() {
    }
    
    public static final boolean verbose() {
        return Debug.verbose;
    }
    
    public static final boolean debugAll() {
        return Debug.debugAll;
    }
    
    public static final boolean debug(final String s) {
        return debugAll() || PropertyAccess.isPropertyDefined("nativewindow.debug." + s, true);
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                PropertyAccess.addTrustedPrefix("nativewindow.");
                return null;
            }
        });
        verbose = PropertyAccess.isPropertyDefined("nativewindow.verbose", true);
        debugAll = PropertyAccess.isPropertyDefined("nativewindow.debug", true);
        if (Debug.verbose) {
            final Package package1 = Package.getPackage("com.jogamp.nativewindow");
            System.err.println("NativeWindow specification version " + package1.getSpecificationVersion());
            System.err.println("NativeWindow implementation version " + package1.getImplementationVersion());
            System.err.println("NativeWindow implementation vendor " + package1.getImplementationVendor());
        }
    }
}
