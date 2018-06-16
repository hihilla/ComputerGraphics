// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.HashMap;
import jogamp.common.Debug;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import com.jogamp.common.JogampRuntimeException;
import com.jogamp.common.ExceptionUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

public final class ReflectionUtil
{
    public static final boolean DEBUG;
    public static final boolean DEBUG_STATS_FORNAME;
    private static final Object forNameLock;
    private static final Map<String, ClassNameLookup> forNameStats;
    private static int forNameCount;
    private static long forNameNanoCosts;
    private static final Class<?>[] zeroTypes;
    
    public static void resetForNameCount() {
        if (ReflectionUtil.DEBUG_STATS_FORNAME) {
            synchronized (ReflectionUtil.forNameLock) {
                ReflectionUtil.forNameCount = 0;
                ReflectionUtil.forNameNanoCosts = 0L;
                ReflectionUtil.forNameStats.clear();
            }
        }
    }
    
    public static StringBuilder getForNameStats(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        if (ReflectionUtil.DEBUG_STATS_FORNAME) {
            synchronized (ReflectionUtil.forNameLock) {
                sb.append(String.format("ReflectionUtil.forName: %8.3f ms, %03d invoc%n", ReflectionUtil.forNameNanoCosts / 1000000.0, ReflectionUtil.forNameCount));
                final Set<Map.Entry<String, ClassNameLookup>> entrySet = ReflectionUtil.forNameStats.entrySet();
                int n = 0;
                final Iterator<Map.Entry<String, ClassNameLookup>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    sb.append(String.format("ReflectionUtil.forName[%03d]: %s%n", n, iterator.next().getValue()));
                    ++n;
                }
            }
        }
        return sb;
    }
    
    private static Class<?> getClassImpl(final String s, final boolean b, final ClassLoader classLoader) throws ClassNotFoundException {
        if (ReflectionUtil.DEBUG_STATS_FORNAME) {
            final long nanoTime = System.nanoTime();
            final Class<?> forName = Class.forName(s, b, classLoader);
            final long n = System.nanoTime() - nanoTime;
            synchronized (ReflectionUtil.forNameLock) {
                ++ReflectionUtil.forNameCount;
                ReflectionUtil.forNameNanoCosts += n;
                ClassNameLookup classNameLookup = ReflectionUtil.forNameStats.get(s);
                if (null == classNameLookup) {
                    classNameLookup = new ClassNameLookup(s);
                    ReflectionUtil.forNameStats.put(s, classNameLookup);
                }
                final ClassNameLookup classNameLookup2 = classNameLookup;
                ++classNameLookup2.count;
                final ClassNameLookup classNameLookup3 = classNameLookup;
                classNameLookup3.nanoCosts += n;
                System.err.printf("ReflectionUtil.getClassImpl.%03d: %8.3f ms, init %b, [%s]@ Thread %s%n", ReflectionUtil.forNameCount, n / 1000000.0, b, classNameLookup.toString(), Thread.currentThread().getName());
                if (ReflectionUtil.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
            }
            return forName;
        }
        return Class.forName(s, b, classLoader);
    }
    
    public static final boolean isClassAvailable(final String s, final ClassLoader classLoader) {
        try {
            return null != getClassImpl(s, false, classLoader);
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
    }
    
    public static final Class<?> getClass(final String s, final boolean b, final ClassLoader classLoader) throws JogampRuntimeException {
        try {
            return getClassImpl(s, b, classLoader);
        }
        catch (ClassNotFoundException ex) {
            throw new JogampRuntimeException(s + " not available", ex);
        }
    }
    
    public static final Constructor<?> getConstructor(final String s, final Class<?>[] array, final boolean b, final ClassLoader classLoader) throws JogampRuntimeException {
        try {
            return getConstructor(getClassImpl(s, b, classLoader), array);
        }
        catch (ClassNotFoundException ex) {
            throw new JogampRuntimeException(s + " not available", ex);
        }
    }
    
    static final String asString(final Class<?>[] array) {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        if (null != array) {
            for (int i = 0; i < array.length; ++i) {
                if (n != 0) {
                    sb.append(", ");
                }
                sb.append(array[i].getName());
                n = 1;
            }
        }
        return sb.toString();
    }
    
    public static final Constructor<?> getConstructor(final Class<?> clazz, Class<?>... zeroTypes) throws JogampRuntimeException {
        if (null == zeroTypes) {
            zeroTypes = ReflectionUtil.zeroTypes;
        }
        Constructor<?> declaredConstructor = null;
        try {
            declaredConstructor = clazz.getDeclaredConstructor(zeroTypes);
        }
        catch (NoSuchMethodException ex) {}
        if (null == declaredConstructor) {
            final Constructor[] constructors = clazz.getConstructors();
            for (int n = 0; null == declaredConstructor && n < constructors.length; ++n) {
                final Constructor constructor = constructors[n];
                final Class[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == zeroTypes.length) {
                    int n2;
                    for (n2 = 0; n2 < parameterTypes.length && parameterTypes[n2].isAssignableFrom(zeroTypes[n2]); ++n2) {}
                    if (parameterTypes.length == n2) {
                        declaredConstructor = (Constructor<?>)constructor;
                    }
                }
            }
        }
        if (null == declaredConstructor) {
            throw new JogampRuntimeException("Constructor: '" + clazz.getName() + "(" + asString(zeroTypes) + ")' not found");
        }
        return declaredConstructor;
    }
    
    public static final Constructor<?> getConstructor(final String s, final ClassLoader classLoader) throws JogampRuntimeException {
        return getConstructor(s, null, true, classLoader);
    }
    
    public static final Object createInstance(final Constructor<?> constructor, final Object... array) throws JogampRuntimeException, RuntimeException {
        try {
            return constructor.newInstance(array);
        }
        catch (Exception ex) {
            Throwable targetException = ex;
            if (targetException instanceof InvocationTargetException) {
                targetException = ((InvocationTargetException)targetException).getTargetException();
            }
            if (targetException instanceof Error) {
                throw (Error)targetException;
            }
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException)targetException;
            }
            throw new JogampRuntimeException("can not create instance of " + constructor.getName(), targetException);
        }
    }
    
    public static final Object createInstance(final Class<?> clazz, final Class<?>[] array, final Object... array2) throws JogampRuntimeException, RuntimeException {
        return createInstance(getConstructor(clazz, array), array2);
    }
    
    public static final Object createInstance(final Class<?> clazz, final Object... array) throws JogampRuntimeException, RuntimeException {
        Class<?>[] array2 = null;
        if (null != array) {
            array2 = (Class<?>[])new Class[array.length];
            for (int i = 0; i < array.length; ++i) {
                array2[i] = array[i].getClass();
            }
        }
        return createInstance(clazz, array2, array);
    }
    
    public static final Object createInstance(final String s, final Class<?>[] array, final Object[] array2, final ClassLoader classLoader) throws JogampRuntimeException, RuntimeException {
        try {
            return createInstance(getClassImpl(s, true, classLoader), array, array2);
        }
        catch (ClassNotFoundException ex) {
            throw new JogampRuntimeException(s + " not available", ex);
        }
    }
    
    public static final Object createInstance(final String s, final Object[] array, final ClassLoader classLoader) throws JogampRuntimeException, RuntimeException {
        Class<?>[] array2 = null;
        if (null != array) {
            array2 = (Class<?>[])new Class[array.length];
            for (int i = 0; i < array.length; ++i) {
                array2[i] = array[i].getClass();
            }
        }
        return createInstance(s, array2, array, classLoader);
    }
    
    public static final Object createInstance(final String s, final ClassLoader classLoader) throws JogampRuntimeException, RuntimeException {
        return createInstance(s, null, null, classLoader);
    }
    
    public static final boolean instanceOf(final Object o, final String s) {
        return instanceOf(o.getClass(), s);
    }
    
    public static final boolean instanceOf(Class<?> superclass, final String s) {
        while (!superclass.getName().equals(s)) {
            superclass = superclass.getSuperclass();
            if (superclass == null) {
                return false;
            }
        }
        return true;
    }
    
    public static final boolean implementationOf(final Object o, final String s) {
        return implementationOf(o.getClass(), s);
    }
    
    public static final boolean implementationOf(Class<?> superclass, final String s) {
        do {
            final Class[] interfaces = superclass.getInterfaces();
            for (int i = interfaces.length - 1; i >= 0; --i) {
                if (interfaces[i].getName().equals(s)) {
                    return true;
                }
            }
            superclass = superclass.getSuperclass();
        } while (superclass != null);
        return false;
    }
    
    public static boolean isAWTComponent(final Object o) {
        return instanceOf(o, "java.awt.Component");
    }
    
    public static boolean isAWTComponent(final Class<?> clazz) {
        return instanceOf(clazz, "java.awt.Component");
    }
    
    public static final Method getMethod(final Class<?> clazz, final String s, final Class<?>... array) throws JogampRuntimeException, RuntimeException {
        Throwable t = null;
        Method declaredMethod = null;
        try {
            declaredMethod = clazz.getDeclaredMethod(s, (Class[])array);
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            t = noClassDefFoundError;
        }
        catch (NoSuchMethodException ex) {
            t = ex;
        }
        if (null != t) {
            throw new JogampRuntimeException("Method: '" + clazz + "." + s + "(" + asString(array) + ")' not found", t);
        }
        return declaredMethod;
    }
    
    public static final Method getMethod(final String s, final String s2, final Class<?>[] array, final ClassLoader classLoader) throws JogampRuntimeException, RuntimeException {
        try {
            return getMethod(getClassImpl(s, true, classLoader), s2, array);
        }
        catch (ClassNotFoundException ex) {
            throw new JogampRuntimeException(s + " not available", ex);
        }
    }
    
    public static final Object callMethod(final Object o, final Method method, final Object... array) throws JogampRuntimeException, RuntimeException {
        try {
            return method.invoke(o, array);
        }
        catch (Exception ex) {
            Throwable targetException = ex;
            if (targetException instanceof InvocationTargetException) {
                targetException = ((InvocationTargetException)targetException).getTargetException();
            }
            if (targetException instanceof Error) {
                throw (Error)targetException;
            }
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException)targetException;
            }
            throw new JogampRuntimeException("calling " + method + " failed", targetException);
        }
    }
    
    public static final Object callStaticMethod(final String s, final String s2, final Class<?>[] array, final Object[] array2, final ClassLoader classLoader) throws JogampRuntimeException, RuntimeException {
        return callMethod(null, getMethod(s, s2, array, classLoader), array2);
    }
    
    static {
        ReflectionUtil.forNameCount = 0;
        ReflectionUtil.forNameNanoCosts = 0L;
        Debug.initSingleton();
        DEBUG = Debug.debug("ReflectionUtil");
        DEBUG_STATS_FORNAME = PropertyAccess.isPropertyDefined("jogamp.debug.ReflectionUtil.forNameStats", true);
        if (ReflectionUtil.DEBUG_STATS_FORNAME) {
            forNameLock = new Object();
            forNameStats = new HashMap<String, ClassNameLookup>();
        }
        else {
            forNameLock = null;
            forNameStats = null;
        }
        zeroTypes = new Class[0];
    }
    
    public static class AWTNames
    {
        public static final String ComponentClass = "java.awt.Component";
        public static final String GraphicsEnvironmentClass = "java.awt.GraphicsEnvironment";
        public static final String isHeadlessMethod = "isHeadless";
    }
    
    private static class ClassNameLookup
    {
        public final String name;
        public long nanoCosts;
        public int count;
        
        public ClassNameLookup(final String name) {
            this.name = name;
            this.nanoCosts = 0L;
            this.count = 0;
        }
        
        @Override
        public String toString() {
            return String.format("%8.3f ms, %03d invoc, %s", this.nanoCosts / 1000000.0, this.count, this.name);
        }
    }
    
    public static class MethodAccessor
    {
        Method m;
        
        public MethodAccessor(final Class<?> clazz, final String s, final Class<?>... array) {
            this.m = null;
            try {
                this.m = ReflectionUtil.getMethod(clazz, s, array);
            }
            catch (JogampRuntimeException ex) {}
        }
        
        public boolean available() {
            return null != this.m;
        }
        
        public Object callMethod(final Object o, final Object... array) {
            if (null == this.m) {
                throw new JogampRuntimeException("Method not available. Instance: " + o);
            }
            return ReflectionUtil.callMethod(o, this.m, array);
        }
    }
}
