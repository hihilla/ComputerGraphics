// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.error;

import jogamp.opengl.glu.Glue;

public class Error
{
    private static String[] glErrorStrings;
    private static String[] gluErrorStrings;
    
    public static String gluErrorString(final int n) {
        if (n == 0) {
            return "no error";
        }
        if (n >= 1280 && n <= 1286) {
            return Error.glErrorStrings[n - 1280];
        }
        if (n == 32817) {
            return "table too large";
        }
        if (n >= 100900 && n <= 100904) {
            return Error.gluErrorStrings[n - 100900];
        }
        if (n >= 100151 && n <= 100158) {
            return Glue.__gluTessErrorString(n - 100150);
        }
        return "error (" + n + ")";
    }
    
    static {
        Error.glErrorStrings = new String[] { "invalid enumerant", "invalid value", "invalid operation", "stack overflow", "stack underflow", "out of memory", "invalid framebuffer operation" };
        Error.gluErrorStrings = new String[] { "invalid enumerant", "invalid value", "out of memory", "", "invalid operation" };
    }
}
