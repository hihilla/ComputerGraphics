// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common;

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
        return debugAll() || PropertyAccess.isPropertyDefined("jogamp.debug." + s, true);
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                PropertyAccess.addTrustedPrefix("jogamp.");
                return null;
            }
        });
        verbose = PropertyAccess.isPropertyDefined("jogamp.verbose", true);
        debugAll = PropertyAccess.isPropertyDefined("jogamp.debug", true);
    }
}
