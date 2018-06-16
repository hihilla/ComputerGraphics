// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionNumber implements Comparable<Object>
{
    public static final VersionNumber zeroVersion;
    private static volatile Pattern defPattern;
    protected final int major;
    protected final int minor;
    protected final int sub;
    protected final int strEnd;
    protected final short state;
    protected static final short HAS_MAJOR = 1;
    protected static final short HAS_MINOR = 2;
    protected static final short HAS_SUB = 4;
    
    public static Pattern getVersionNumberPattern(final String s) {
        return Pattern.compile("\\D*(\\d+)[^\\" + s + "\\s]*(?:\\" + s + "\\D*(\\d+)[^\\" + s + "\\s]*(?:\\" + s + "\\D*(\\d+))?)?");
    }
    
    public static Pattern getDefaultVersionNumberPattern() {
        if (null == VersionNumber.defPattern) {
            synchronized (VersionNumber.class) {
                if (null == VersionNumber.defPattern) {
                    VersionNumber.defPattern = getVersionNumberPattern(".");
                }
            }
        }
        return VersionNumber.defPattern;
    }
    
    protected VersionNumber(final int major, final int minor, final int sub, final int strEnd, final short state) {
        this.major = major;
        this.minor = minor;
        this.sub = sub;
        this.strEnd = strEnd;
        this.state = state;
    }
    
    public VersionNumber(final int n, final int n2, final int n3) {
        this(n, n2, n3, -1, (short)7);
    }
    
    public VersionNumber(final String s) {
        this(s, getDefaultVersionNumberPattern());
    }
    
    public VersionNumber(final String s, final String s2) {
        this(s, getVersionNumberPattern(s2));
    }
    
    public VersionNumber(final String s, final Pattern pattern) {
        final int[] array = new int[3];
        int end = 0;
        short state = 0;
        try {
            final Matcher matcher = pattern.matcher(s);
            if (matcher.lookingAt()) {
                end = matcher.end();
                final int groupCount = matcher.groupCount();
                if (1 <= groupCount) {
                    array[0] = Integer.parseInt(matcher.group(1));
                    state = 1;
                    if (2 <= groupCount) {
                        array[1] = Integer.parseInt(matcher.group(2));
                        state |= 0x2;
                        if (3 <= groupCount) {
                            array[2] = Integer.parseInt(matcher.group(3));
                            state |= 0x4;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
        this.major = array[0];
        this.minor = array[1];
        this.sub = array[2];
        this.strEnd = end;
        this.state = state;
    }
    
    public final boolean isZero() {
        return this.major == 0 && this.minor == 0 && this.sub == 0;
    }
    
    public final boolean hasMajor() {
        return 0x0 != (0x1 & this.state);
    }
    
    public final boolean hasMinor() {
        return 0x0 != (0x2 & this.state);
    }
    
    public final boolean hasSub() {
        return 0x0 != (0x4 & this.state);
    }
    
    public final int endOfStringMatch() {
        return this.strEnd;
    }
    
    @Override
    public final int hashCode() {
        final int n = 31 + this.major;
        final int n2 = (n << 5) - n + this.minor;
        return (n2 << 5) - n2 + this.sub;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof VersionNumber && 0 == this.compareTo((VersionNumber)o);
    }
    
    @Override
    public final int compareTo(final Object o) {
        if (!(o instanceof VersionNumber)) {
            throw new ClassCastException("Not a VersionNumber object: " + ((null != o) ? o.getClass() : null));
        }
        return this.compareTo((VersionNumber)o);
    }
    
    public final int compareTo(final VersionNumber versionNumber) {
        if (this.major > versionNumber.major) {
            return 1;
        }
        if (this.major < versionNumber.major) {
            return -1;
        }
        if (this.minor > versionNumber.minor) {
            return 1;
        }
        if (this.minor < versionNumber.minor) {
            return -1;
        }
        if (this.sub > versionNumber.sub) {
            return 1;
        }
        if (this.sub < versionNumber.sub) {
            return -1;
        }
        return 0;
    }
    
    public final int getMajor() {
        return this.major;
    }
    
    public final int getMinor() {
        return this.minor;
    }
    
    public final int getSub() {
        return this.sub;
    }
    
    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.sub;
    }
    
    static {
        zeroVersion = new VersionNumber(0, 0, 0, -1, (short)0);
        VersionNumber.defPattern = null;
    }
}
