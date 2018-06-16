// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.security.AllPermission;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.security.Permission;

public class SecurityUtil
{
    private static final SecurityManager securityManager;
    private static final Permission allPermissions;
    private static final boolean DEBUG = false;
    private static final RuntimePermission allLinkPermission;
    
    public static final boolean hasAllPermissions() {
        return hasPermission(SecurityUtil.allPermissions);
    }
    
    public static final boolean hasPermission(final Permission permission) {
        try {
            checkPermission(permission);
            return true;
        }
        catch (SecurityException ex) {
            return false;
        }
    }
    
    public static final void checkAllPermissions() throws SecurityException {
        checkPermission(SecurityUtil.allPermissions);
    }
    
    public static final void checkPermission(final Permission permission) throws SecurityException {
        if (null != SecurityUtil.securityManager) {
            SecurityUtil.securityManager.checkPermission(permission);
        }
    }
    
    public static final boolean hasLinkPermission(final String s) {
        try {
            checkLinkPermission(s);
            return true;
        }
        catch (SecurityException ex) {
            return false;
        }
    }
    
    public static final void checkLinkPermission(final String s) throws SecurityException {
        if (null != SecurityUtil.securityManager) {
            SecurityUtil.securityManager.checkLink(s);
        }
    }
    
    public static final void checkAllLinkPermission() throws SecurityException {
        if (null != SecurityUtil.securityManager) {
            SecurityUtil.securityManager.checkPermission(SecurityUtil.allLinkPermission);
        }
    }
    
    public static final Certificate[] getCerts(final Class<?> clazz) throws SecurityException {
        final ProtectionDomain protectionDomain = clazz.getProtectionDomain();
        final CodeSource codeSource = (null != protectionDomain) ? protectionDomain.getCodeSource() : null;
        final Certificate[] array = (Certificate[])((null != codeSource) ? codeSource.getCertificates() : null);
        return (Certificate[])((null != array && array.length > 0) ? array : null);
    }
    
    public static final boolean equals(final Certificate[] array, final Certificate[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        if (array.length != array2.length) {
            return false;
        }
        int n;
        for (n = 0; n < array.length && array[n].equals(array2[n]); ++n) {}
        return n == array.length;
    }
    
    static {
        allPermissions = new AllPermission();
        securityManager = System.getSecurityManager();
        allLinkPermission = new RuntimePermission("loadLibrary.*");
    }
}
