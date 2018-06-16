// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.AccessControlException;
import java.util.HashSet;

public class PropertyAccess
{
    public static final String jnlp_prefix = "jnlp.";
    public static final String javaws_prefix = "javaws.";
    static final HashSet<String> trustedPrefixes;
    static final HashSet<String> trusted;
    
    protected static final void addTrustedPrefix(final String s) throws AccessControlException {
        SecurityUtil.checkAllPermissions();
        PropertyAccess.trustedPrefixes.add(s);
    }
    
    public static final boolean isTrusted(final String s) {
        final int index = s.indexOf(46);
        return 0 <= index && (PropertyAccess.trustedPrefixes.contains(s.substring(0, index + 1)) || PropertyAccess.trusted.contains(s));
    }
    
    public static final int getIntProperty(final String s, final boolean b, final int n) {
        int int1 = n;
        try {
            final String property = getProperty(s, b);
            if (null != property) {
                int1 = Integer.parseInt(property);
            }
        }
        catch (NumberFormatException ex) {}
        return int1;
    }
    
    public static final long getLongProperty(final String s, final boolean b, final long n) {
        long long1 = n;
        try {
            final String property = getProperty(s, b);
            if (null != property) {
                long1 = Long.parseLong(property);
            }
        }
        catch (NumberFormatException ex) {}
        return long1;
    }
    
    public static final boolean getBooleanProperty(final String s, final boolean b) {
        return Boolean.valueOf(getProperty(s, b));
    }
    
    public static final boolean getBooleanProperty(final String s, final boolean b, final boolean b2) {
        final String property = getProperty(s, b);
        if (null != property) {
            return Boolean.valueOf(property);
        }
        return b2;
    }
    
    public static final boolean isPropertyDefined(final String s, final boolean b) {
        return getProperty(s, b) != null;
    }
    
    public static final String getProperty(final String s, final boolean b) throws SecurityException, NullPointerException, IllegalArgumentException {
        if (null == s) {
            throw new NullPointerException("propertyKey is NULL");
        }
        if (0 == s.length()) {
            throw new IllegalArgumentException("propertyKey is empty");
        }
        String s2;
        if (isTrusted(s)) {
            s2 = getTrustedPropKey(s);
        }
        else {
            s2 = System.getProperty(s);
        }
        if (null == s2 && b && !s.startsWith("jnlp.")) {
            s2 = getTrustedPropKey("jnlp." + s);
        }
        return s2;
    }
    
    public static final String getProperty(final String s, final boolean b, final String s2) throws SecurityException, NullPointerException, IllegalArgumentException {
        final String property = getProperty(s, b);
        if (null != property) {
            return property;
        }
        return s2;
    }
    
    private static final String getTrustedPropKey(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                try {
                    return System.getProperty(s);
                }
                catch (SecurityException ex) {
                    throw new SecurityException("Could not access trusted property '" + s + "'", ex);
                }
            }
        });
    }
    
    static {
        (trustedPrefixes = new HashSet<String>()).add("javaws.");
        PropertyAccess.trustedPrefixes.add("jnlp.");
        (trusted = new HashSet<String>()).add("sun.java2d.opengl");
        PropertyAccess.trusted.add("sun.java2d.noddraw");
        PropertyAccess.trusted.add("sun.java2d.d3d");
        PropertyAccess.trusted.add("sun.awt.noerasebackground");
    }
}
