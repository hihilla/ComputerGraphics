// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.regex.Pattern;

public class VersionNumberString extends VersionNumber
{
    public static final VersionNumberString zeroVersion;
    protected final String strVal;
    
    protected VersionNumberString(final int n, final int n2, final int n3, final int n4, final short n5, final String strVal) {
        super(n, n2, n3, n4, n5);
        this.strVal = strVal;
    }
    
    public VersionNumberString(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, -1, (short)7, s);
    }
    
    public VersionNumberString(final String strVal) {
        super(strVal);
        this.strVal = strVal;
    }
    
    public VersionNumberString(final String strVal, final String s) {
        super(strVal, s);
        this.strVal = strVal;
    }
    
    public VersionNumberString(final String strVal, final Pattern pattern) {
        super(strVal, pattern);
        this.strVal = strVal;
    }
    
    public final String getVersionString() {
        return this.strVal;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (" + this.strVal + ")";
    }
    
    static {
        zeroVersion = new VersionNumberString(0, 0, 0, -1, (short)0, "n/a");
    }
}
