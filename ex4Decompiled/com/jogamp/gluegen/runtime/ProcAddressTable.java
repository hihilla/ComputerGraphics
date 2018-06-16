// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.gluegen.runtime;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import com.jogamp.common.util.SecurityUtil;
import java.util.TreeMap;
import java.util.Map;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.AccessibleObject;
import com.jogamp.common.os.DynamicLookupHelper;

public abstract class ProcAddressTable
{
    private static final String PROCADDRESS_VAR_PREFIX = "_addressof_";
    private static final int PROCADDRESS_VAR_PREFIX_LEN;
    protected static boolean DEBUG;
    protected static String DEBUG_PREFIX;
    protected static int debugNum;
    private final FunctionAddressResolver resolver;
    
    public ProcAddressTable() {
        this(new One2OneResolver());
    }
    
    public ProcAddressTable(final FunctionAddressResolver resolver) {
        this.resolver = resolver;
    }
    
    public void reset(final DynamicLookupHelper dynamicLookupHelper) throws SecurityException, RuntimeException {
        if (null == dynamicLookupHelper) {
            throw new RuntimeException("Passed null DynamicLookupHelper");
        }
        final Field[] declaredFields = this.getClass().getDeclaredFields();
        PrintStream debugOutStream;
        if (ProcAddressTable.DEBUG) {
            debugOutStream = getDebugOutStream();
            debugOutStream.println(this.getClass().getName() + ".reset() (w/ " + declaredFields.length + " prospective fields)");
        }
        else {
            debugOutStream = null;
        }
        AccessibleObject.setAccessible(declaredFields, true);
        dynamicLookupHelper.claimAllLinkPermission();
        try {
            for (int i = 0; i < declaredFields.length; ++i) {
                final String name = declaredFields[i].getName();
                if (this.isAddressField(name)) {
                    this.setEntry(declaredFields[i], this.fieldToFunctionName(name), dynamicLookupHelper);
                }
            }
        }
        finally {
            dynamicLookupHelper.releaseAllLinkPermission();
        }
        if (ProcAddressTable.DEBUG) {
            debugOutStream.flush();
            if (ProcAddressTable.DEBUG_PREFIX != null) {
                debugOutStream.close();
            }
        }
    }
    
    public void initEntry(final String s, final DynamicLookupHelper dynamicLookupHelper) throws SecurityException, IllegalArgumentException {
        final Field fieldForFunction = this.fieldForFunction(s);
        fieldForFunction.setAccessible(true);
        this.setEntry(fieldForFunction, s, dynamicLookupHelper);
    }
    
    private final void setEntry(final Field field, final String s, final DynamicLookupHelper dynamicLookupHelper) throws SecurityException {
        try {
            assert field.getType() == Long.TYPE;
            final long resolve = this.resolver.resolve(s, dynamicLookupHelper);
            field.setLong(this, resolve);
            if (ProcAddressTable.DEBUG) {
                getDebugOutStream().println("  " + field.getName() + " -> 0x" + Long.toHexString(resolve));
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not get proc address for method \"" + s + "\": Couldn't set value of field \"" + field, ex);
        }
    }
    
    private final String fieldToFunctionName(final String s) {
        return s.substring(ProcAddressTable.PROCADDRESS_VAR_PREFIX_LEN);
    }
    
    private final Field fieldForFunction(final String s) throws IllegalArgumentException {
        try {
            return this.getClass().getDeclaredField("_addressof_" + s);
        }
        catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException(this.getClass().getName() + " has no entry for the function '" + s + "'.", ex);
        }
    }
    
    private final Field fieldForFunctionInSec(final String s) throws IllegalArgumentException {
        return AccessController.doPrivileged((PrivilegedAction<Field>)new PrivilegedAction<Field>() {
            @Override
            public Field run() {
                try {
                    final Field declaredField = ProcAddressTable.this.getClass().getDeclaredField("_addressof_" + s);
                    declaredField.setAccessible(true);
                    return declaredField;
                }
                catch (NoSuchFieldException ex) {
                    throw new IllegalArgumentException(this.getClass().getName() + " has no entry for the function '" + s + "'.", ex);
                }
            }
        });
    }
    
    private final boolean isAddressField(final String s) {
        return s.startsWith("_addressof_");
    }
    
    private static final PrintStream getDebugOutStream() {
        PrintStream printStream = null;
        if (ProcAddressTable.DEBUG) {
            if (ProcAddressTable.DEBUG_PREFIX != null) {
                try {
                    printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(ProcAddressTable.DEBUG_PREFIX + File.separatorChar + "procaddresstable-" + ++ProcAddressTable.debugNum + ".txt")));
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    printStream = System.err;
                }
            }
            else {
                printStream = System.err;
            }
        }
        return printStream;
    }
    
    private final Map<String, Long> toMap() {
        final TreeMap<String, Long> treeMap = new TreeMap<String, Long>();
        final Field[] fields = this.getClass().getFields();
        try {
            for (int i = 0; i < fields.length; ++i) {
                final String name = fields[i].getName();
                if (this.isAddressField(name)) {
                    treeMap.put(this.fieldToFunctionName(name), (Long)fields[i].get(this));
                }
            }
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
        catch (IllegalAccessException ex2) {
            throw new RuntimeException(ex2);
        }
        return treeMap;
    }
    
    public final boolean isFunctionAvailable(final String s) {
        try {
            return this.isFunctionAvailableImpl(s);
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
    }
    
    protected boolean isFunctionAvailableImpl(final String s) throws IllegalArgumentException {
        final Field fieldForFunctionInSec = this.fieldForFunctionInSec(s);
        try {
            return 0L != fieldForFunctionInSec.getLong(this);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public long getAddressFor(final String s) throws SecurityException, IllegalArgumentException {
        SecurityUtil.checkAllLinkPermission();
        final Field fieldForFunctionInSec = this.fieldForFunctionInSec(s);
        try {
            return fieldForFunctionInSec.getLong(this);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public final Set<String> getNullPointerFunctions() {
        final Map<String, Long> map = this.toMap();
        final LinkedHashSet<Object> set = new LinkedHashSet<Object>();
        for (final Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue() == 0L) {
                set.add(entry.getKey());
            }
        }
        return (Set<String>)set;
    }
    
    @Override
    public final String toString() {
        return this.getClass().getName() + "" + this.toMap();
    }
    
    static {
        PROCADDRESS_VAR_PREFIX_LEN = "_addressof_".length();
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                ProcAddressTable.DEBUG = (System.getProperty("jogamp.debug.ProcAddressHelper") != null);
                if (ProcAddressTable.DEBUG) {
                    ProcAddressTable.DEBUG_PREFIX = System.getProperty("jogamp.debug.ProcAddressHelper.prefix");
                }
                return null;
            }
        });
    }
    
    private static class One2OneResolver implements FunctionAddressResolver
    {
        @Override
        public long resolve(final String s, final DynamicLookupHelper dynamicLookupHelper) throws SecurityException {
            return dynamicLookupHelper.dynamicLookupFunction(s);
        }
    }
}
