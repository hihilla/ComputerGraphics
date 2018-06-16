// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.gluegen.runtime.opengl;

public class GLNameResolver
{
    private static final String[] extensionsARB;
    private static final String[] extensionsVEN;
    
    public static final boolean isGLFunction(final String s) {
        return s.startsWith("gl") || s.startsWith("egl") || s.startsWith("wgl") || s.startsWith("agl") || s.startsWith("cgl");
    }
    
    public static final boolean isGLEnumeration(final String s) {
        return s.startsWith("GL_") || s.startsWith("GLU_") || s.startsWith("GLX_") || s.startsWith("EGL_") || s.startsWith("WGL_") || s.startsWith("AGL_") || s.startsWith("CGL_");
    }
    
    public static final int getExtensionIdx(final String[] array, final String s, final boolean b) {
        if (b) {
            for (int i = array.length - 1; i >= 0; --i) {
                if (s.endsWith(array[i])) {
                    return i;
                }
            }
        }
        else {
            for (int j = array.length - 1; j >= 0; --j) {
                if (s.endsWith("_" + array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public static final boolean isExtension(final String[] array, final String s, final boolean b) {
        return getExtensionIdx(array, s, b) >= 0;
    }
    
    public static final String getExtensionSuffix(final String s, final boolean b) {
        final int extensionIdx = getExtensionIdx(GLNameResolver.extensionsARB, s, b);
        if (extensionIdx >= 0) {
            return GLNameResolver.extensionsARB[extensionIdx];
        }
        final int extensionIdx2 = getExtensionIdx(GLNameResolver.extensionsVEN, s, b);
        if (extensionIdx2 >= 0) {
            return GLNameResolver.extensionsVEN[extensionIdx2];
        }
        return null;
    }
    
    public static final String normalize(final String[] array, String s, final boolean b) {
        for (int n = 0, n2 = array.length - 1; n == 0 && n2 >= 0; --n2) {
            if (b) {
                if (s.endsWith(array[n2])) {
                    s = s.substring(0, s.length() - array[n2].length());
                    n = 1;
                }
            }
            else if (s.endsWith("_" + array[n2])) {
                s = s.substring(0, s.length() - 1 - array[n2].length());
                n = 1;
            }
        }
        return s;
    }
    
    public static final String normalizeARB(final String s, final boolean b) {
        return normalize(GLNameResolver.extensionsARB, s, b);
    }
    
    public static final boolean isExtensionARB(final String s, final boolean b) {
        return isExtension(GLNameResolver.extensionsARB, s, b);
    }
    
    public static final String normalizeVEN(final String s, final boolean b) {
        return normalize(GLNameResolver.extensionsVEN, s, b);
    }
    
    public static final boolean isExtensionVEN(final String s, final boolean b) {
        return isExtension(GLNameResolver.extensionsVEN, s, b);
    }
    
    public static final String normalize(final String s, final boolean b) {
        if (isExtensionARB(s, b)) {
            return normalizeARB(s, b);
        }
        if (isExtensionVEN(s, b)) {
            return normalizeVEN(s, b);
        }
        return s;
    }
    
    public static final boolean isExtension(final String s, final boolean b) {
        return isExtension(GLNameResolver.extensionsARB, s, b) || isExtension(GLNameResolver.extensionsVEN, s, b);
    }
    
    public static final int getFuncNamePermutationNumber(final String s) {
        if (isExtensionARB(s, true) || isExtensionVEN(s, true)) {
            return 1;
        }
        return 1 + GLNameResolver.extensionsARB.length + GLNameResolver.extensionsVEN.length;
    }
    
    public static final String getFuncNamePermutation(final String s, int n) {
        if (n == 0) {
            return s;
        }
        if (0 > n || n >= 1 + GLNameResolver.extensionsARB.length + GLNameResolver.extensionsVEN.length) {
            throw new RuntimeException("Index out of range [0.." + (1 + GLNameResolver.extensionsARB.length + GLNameResolver.extensionsVEN.length - 1) + "]: " + n);
        }
        if (--n < GLNameResolver.extensionsARB.length) {
            return s + GLNameResolver.extensionsARB[n];
        }
        n -= GLNameResolver.extensionsARB.length;
        return s + GLNameResolver.extensionsVEN[n];
    }
    
    static {
        extensionsARB = new String[] { "ARB", "GL2", "OES", "KHR", "OML" };
        extensionsVEN = new String[] { "3DFX", "AMD", "ANDROID", "ANGLE", "ARM", "APPLE", "ATI", "EXT", "FJ", "HI", "HP", "IBM", "IMG", "INGR", "INTEL", "MESA", "MESAX", "NV", "PGI", "QCOM", "SGI", "SGIS", "SGIX", "SUN", "VIV", "WIN" };
    }
}
