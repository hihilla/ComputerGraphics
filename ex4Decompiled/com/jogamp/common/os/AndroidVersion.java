// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.os;

import com.jogamp.common.util.ReflectionUtil;
import java.lang.reflect.Field;
import com.jogamp.common.util.IntObjectHashMap;

public class AndroidVersion
{
    public static final boolean isAvailable;
    public static final String CPU_ABI;
    public static final Platform.CPUType CPU_TYPE;
    public static final Platform.ABIType ABI_TYPE;
    public static final String CPU_ABI2;
    public static final Platform.CPUType CPU_TYPE2;
    public static final Platform.ABIType ABI_TYPE2;
    public static final String CODENAME;
    public static final String INCREMENTAL;
    public static final String RELEASE;
    public static final int SDK_INT;
    public static final String SDK_NAME;
    private static final String androidBuild = "android.os.Build";
    private static final String androidBuildVersion = "android.os.Build$VERSION";
    private static final String androidBuildVersionCodes = "android.os.Build$VERSION_CODES";
    
    private static final IntObjectHashMap getVersionCodes(final Class<?> clazz, final Object o) {
        final Field[] fields = clazz.getFields();
        final IntObjectHashMap intObjectHashMap = new IntObjectHashMap(3 * fields.length / 2, 0.75f);
        for (int i = 0; i < fields.length; ++i) {
            try {
                intObjectHashMap.put(new Integer(fields[i].getInt(o)), fields[i].getName());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return intObjectHashMap;
    }
    
    private static final String getString(final Class<?> clazz, final Object o, final String s, final boolean b) {
        try {
            final String s2 = (String)clazz.getField(s).get(o);
            if (b && null != s2) {
                return s2.toLowerCase();
            }
            return s2;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static final int getInt(final Class<?> clazz, final Object o, final String s) {
        try {
            return clazz.getField(s).getInt(o);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    static {
        final ClassLoader classLoader = AndroidVersion.class.getClassLoader();
        Class<?> class1 = null;
        Object instance = null;
        Class<?> class2 = null;
        Object instance2 = null;
        Class<?> class3 = null;
        Object instance3 = null;
        try {
            class1 = ReflectionUtil.getClass("android.os.Build", true, classLoader);
            instance = class1.newInstance();
            class2 = ReflectionUtil.getClass("android.os.Build$VERSION", true, classLoader);
            instance2 = class2.newInstance();
            class3 = ReflectionUtil.getClass("android.os.Build$VERSION_CODES", true, classLoader);
            instance3 = class3.newInstance();
        }
        catch (Exception ex) {}
        isAvailable = (null != instance && null != instance2);
        if (AndroidVersion.isAvailable) {
            CPU_ABI = getString(class1, instance, "CPU_ABI", true);
            CPU_ABI2 = getString(class1, instance, "CPU_ABI2", true);
            CODENAME = getString(class2, instance2, "CODENAME", false);
            INCREMENTAL = getString(class2, instance2, "INCREMENTAL", false);
            RELEASE = getString(class2, instance2, "RELEASE", false);
            SDK_INT = getInt(class2, instance2, "SDK_INT");
            String s;
            if (null != instance3) {
                s = (String)getVersionCodes(class3, instance3).get(AndroidVersion.SDK_INT);
            }
            else {
                s = null;
            }
            SDK_NAME = ((null != s) ? s : ("SDK_" + AndroidVersion.SDK_INT));
            CPU_TYPE = Platform.CPUType.query(AndroidVersion.CPU_ABI);
            ABI_TYPE = Platform.ABIType.query(AndroidVersion.CPU_TYPE, AndroidVersion.CPU_ABI);
            if (null != AndroidVersion.CPU_ABI2 && AndroidVersion.CPU_ABI2.length() > 0) {
                CPU_TYPE2 = Platform.CPUType.query(AndroidVersion.CPU_ABI2);
                ABI_TYPE2 = Platform.ABIType.query(AndroidVersion.CPU_TYPE2, AndroidVersion.CPU_ABI2);
            }
            else {
                CPU_TYPE2 = null;
                ABI_TYPE2 = null;
            }
        }
        else {
            CPU_ABI = null;
            CPU_ABI2 = null;
            CODENAME = null;
            INCREMENTAL = null;
            RELEASE = null;
            SDK_INT = -1;
            SDK_NAME = null;
            CPU_TYPE = null;
            ABI_TYPE = null;
            CPU_TYPE2 = null;
            ABI_TYPE2 = null;
        }
    }
}
