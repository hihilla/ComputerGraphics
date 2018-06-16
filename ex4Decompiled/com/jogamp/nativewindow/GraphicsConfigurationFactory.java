// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import java.util.Collections;
import java.util.HashMap;
import jogamp.nativewindow.Debug;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.nativewindow.DefaultGraphicsConfigurationFactoryImpl;
import java.util.Map;

public abstract class GraphicsConfigurationFactory
{
    protected static final boolean DEBUG;
    private static final Map<DeviceCapsType, GraphicsConfigurationFactory> registeredFactories;
    private static final DeviceCapsType defaultDeviceCapsType;
    static boolean initialized;
    
    public static synchronized void initSingleton() {
        if (!GraphicsConfigurationFactory.initialized) {
            GraphicsConfigurationFactory.initialized = true;
            if (GraphicsConfigurationFactory.DEBUG) {
                System.err.println(Thread.currentThread().getName() + " - GraphicsConfigurationFactory.initSingleton()");
            }
            registerFactory(GraphicsConfigurationFactory.defaultDeviceCapsType.deviceType, GraphicsConfigurationFactory.defaultDeviceCapsType.capsType, new DefaultGraphicsConfigurationFactoryImpl());
            if (NativeWindowFactory.TYPE_X11 == NativeWindowFactory.getNativeWindowType(true)) {
                try {
                    ReflectionUtil.callStaticMethod("jogamp.nativewindow.x11.X11GraphicsConfigurationFactory", "registerFactory", null, null, GraphicsConfigurationFactory.class.getClassLoader());
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (NativeWindowFactory.isAWTAvailable()) {
                    try {
                        ReflectionUtil.callStaticMethod("jogamp.nativewindow.x11.awt.X11AWTGraphicsConfigurationFactory", "registerFactory", null, null, GraphicsConfigurationFactory.class.getClassLoader());
                    }
                    catch (Exception ex2) {}
                }
            }
        }
    }
    
    public static synchronized void shutdown() {
        if (GraphicsConfigurationFactory.initialized) {
            GraphicsConfigurationFactory.initialized = false;
            if (GraphicsConfigurationFactory.DEBUG) {
                System.err.println(Thread.currentThread().getName() + " - GraphicsConfigurationFactory.shutdown()");
            }
            GraphicsConfigurationFactory.registeredFactories.clear();
        }
    }
    
    protected static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    protected static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    protected static String toHexString(final long n) {
        return "0x" + Long.toHexString(n);
    }
    
    public static GraphicsConfigurationFactory getFactory(final AbstractGraphicsDevice abstractGraphicsDevice, final CapabilitiesImmutable capabilitiesImmutable) {
        if (abstractGraphicsDevice == null) {
            throw new IllegalArgumentException("null device");
        }
        if (capabilitiesImmutable == null) {
            throw new IllegalArgumentException("null caps");
        }
        return getFactory(abstractGraphicsDevice.getClass(), capabilitiesImmutable.getClass());
    }
    
    public static GraphicsConfigurationFactory getFactory(final Class<?> clazz, final Class<?> clazz2) throws IllegalArgumentException, NativeWindowException {
        if (!GraphicsConfigurationFactory.defaultDeviceCapsType.deviceType.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Given class must implement AbstractGraphicsDevice");
        }
        if (!GraphicsConfigurationFactory.defaultDeviceCapsType.capsType.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException("Given capabilities class must implement CapabilitiesImmutable");
        }
        if (GraphicsConfigurationFactory.DEBUG) {
            ExceptionUtils.dumpStack(System.err);
            System.err.println("GraphicsConfigurationFactory.getFactory: " + clazz.getName() + ", " + clazz2.getName());
            dumpFactories();
        }
        final ArrayList<Class<?>> allAssignableClasses = getAllAssignableClassesFrom(GraphicsConfigurationFactory.defaultDeviceCapsType.deviceType, clazz, false);
        if (GraphicsConfigurationFactory.DEBUG) {
            System.err.println("GraphicsConfigurationFactory.getFactory() deviceTypes: " + allAssignableClasses);
        }
        final ArrayList<Class<?>> allAssignableClasses2 = getAllAssignableClassesFrom(GraphicsConfigurationFactory.defaultDeviceCapsType.capsType, clazz2, true);
        if (GraphicsConfigurationFactory.DEBUG) {
            System.err.println("GraphicsConfigurationFactory.getFactory() capabilitiesTypes: " + allAssignableClasses2);
        }
        for (int i = 0; i < allAssignableClasses.size(); ++i) {
            final Class<?> clazz3 = allAssignableClasses.get(i);
            for (int j = 0; j < allAssignableClasses2.size(); ++j) {
                final DeviceCapsType deviceCapsType = new DeviceCapsType(clazz3, allAssignableClasses2.get(j));
                final GraphicsConfigurationFactory graphicsConfigurationFactory = GraphicsConfigurationFactory.registeredFactories.get(deviceCapsType);
                if (graphicsConfigurationFactory != null) {
                    if (GraphicsConfigurationFactory.DEBUG) {
                        System.err.println("GraphicsConfigurationFactory.getFactory() found " + deviceCapsType + " -> " + graphicsConfigurationFactory);
                    }
                    return graphicsConfigurationFactory;
                }
            }
        }
        final GraphicsConfigurationFactory graphicsConfigurationFactory2 = GraphicsConfigurationFactory.registeredFactories.get(GraphicsConfigurationFactory.defaultDeviceCapsType);
        if (GraphicsConfigurationFactory.DEBUG) {
            System.err.println("GraphicsConfigurationFactory.getFactory() DEFAULT " + GraphicsConfigurationFactory.defaultDeviceCapsType + " -> " + graphicsConfigurationFactory2);
        }
        return graphicsConfigurationFactory2;
    }
    
    private static ArrayList<Class<?>> getAllAssignableClassesFrom(final Class<?> clazz, final Class<?> clazz2, final boolean b) {
        final ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        final ArrayList<Class<?>> list2 = new ArrayList<Class<?>>();
        list.add(clazz2);
        for (int i = 0; i < list.size(); ++i) {
            getAllAssignableClassesFrom(clazz, list.get(i), b, list2, list);
        }
        return list2;
    }
    
    private static void getAllAssignableClassesFrom(final Class<?> clazz, final Class<?> clazz2, final boolean b, final List<Class<?>> list, final List<Class<?>> list2) {
        final ArrayList<Class<?>> list3 = new ArrayList<Class<?>>();
        if (clazz.isAssignableFrom(clazz2) && !list.contains(clazz2) && (!b || clazz2.isInterface())) {
            list3.add(clazz2);
        }
        list3.addAll(Arrays.asList(clazz2.getInterfaces()));
        for (int i = 0; i < list3.size(); ++i) {
            final Class<?> clazz3 = list3.get(i);
            if (clazz.isAssignableFrom(clazz3) && !list.contains(clazz3)) {
                list.add(clazz3);
                if (!clazz.equals(clazz3) && !list2.contains(clazz3)) {
                    list2.add(clazz3);
                }
            }
        }
        final Class<?> superclass = clazz2.getSuperclass();
        if (null != superclass && clazz.isAssignableFrom(superclass) && !list2.contains(superclass)) {
            list2.add(superclass);
        }
    }
    
    private static void dumpFactories() {
        final Set<DeviceCapsType> keySet = GraphicsConfigurationFactory.registeredFactories.keySet();
        int n = 0;
        for (final DeviceCapsType deviceCapsType : keySet) {
            System.err.println("Factory #" + n + ": " + deviceCapsType + " -> " + GraphicsConfigurationFactory.registeredFactories.get(deviceCapsType));
            ++n;
        }
    }
    
    protected static GraphicsConfigurationFactory registerFactory(final Class<?> clazz, final Class<?> clazz2, final GraphicsConfigurationFactory graphicsConfigurationFactory) throws IllegalArgumentException {
        if (!GraphicsConfigurationFactory.defaultDeviceCapsType.deviceType.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Given device class must implement AbstractGraphicsDevice");
        }
        if (!GraphicsConfigurationFactory.defaultDeviceCapsType.capsType.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException("Given capabilities class must implement CapabilitiesImmutable");
        }
        final DeviceCapsType deviceCapsType = new DeviceCapsType(clazz, clazz2);
        GraphicsConfigurationFactory graphicsConfigurationFactory2;
        if (null == graphicsConfigurationFactory) {
            graphicsConfigurationFactory2 = GraphicsConfigurationFactory.registeredFactories.remove(deviceCapsType);
            if (GraphicsConfigurationFactory.DEBUG) {
                System.err.println("GraphicsConfigurationFactory.registerFactory() remove " + deviceCapsType + ", deleting: " + graphicsConfigurationFactory2);
            }
        }
        else {
            graphicsConfigurationFactory2 = GraphicsConfigurationFactory.registeredFactories.put(deviceCapsType, graphicsConfigurationFactory);
            if (GraphicsConfigurationFactory.DEBUG) {
                System.err.println("GraphicsConfigurationFactory.registerFactory() put " + deviceCapsType + " -> " + graphicsConfigurationFactory + ", overridding: " + graphicsConfigurationFactory2);
            }
        }
        return graphicsConfigurationFactory2;
    }
    
    public final AbstractGraphicsConfiguration chooseGraphicsConfiguration(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) throws IllegalArgumentException, NativeWindowException {
        if (null == capabilitiesImmutable) {
            throw new NativeWindowException("Chosen Capabilities are null");
        }
        if (null == capabilitiesImmutable2) {
            throw new NativeWindowException("Requested Capabilities are null");
        }
        if (null == abstractGraphicsScreen) {
            throw new NativeWindowException("Screen is null");
        }
        final AbstractGraphicsDevice device = abstractGraphicsScreen.getDevice();
        if (null == device) {
            throw new NativeWindowException("Screen's Device is null");
        }
        device.lock();
        try {
            return this.chooseGraphicsConfigurationImpl(capabilitiesImmutable, capabilitiesImmutable2, capabilitiesChooser, abstractGraphicsScreen, n);
        }
        finally {
            device.unlock();
        }
    }
    
    protected abstract AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable p0, final CapabilitiesImmutable p1, final CapabilitiesChooser p2, final AbstractGraphicsScreen p3, final int p4) throws IllegalArgumentException, NativeWindowException;
    
    static {
        GraphicsConfigurationFactory.initialized = false;
        DEBUG = Debug.debug("GraphicsConfiguration");
        if (GraphicsConfigurationFactory.DEBUG) {
            System.err.println(Thread.currentThread().getName() + " - Info: GraphicsConfigurationFactory.<init>");
        }
        registeredFactories = Collections.synchronizedMap(new HashMap<DeviceCapsType, GraphicsConfigurationFactory>());
        defaultDeviceCapsType = new DeviceCapsType(AbstractGraphicsDevice.class, CapabilitiesImmutable.class);
    }
    
    private static class DeviceCapsType
    {
        public final Class<?> deviceType;
        public final Class<?> capsType;
        private final int hash32;
        
        public DeviceCapsType(final Class<?> deviceType, final Class<?> capsType) {
            this.deviceType = deviceType;
            this.capsType = capsType;
            final int n = 31 + deviceType.hashCode();
            this.hash32 = (n << 5) - n + capsType.hashCode();
        }
        
        @Override
        public final int hashCode() {
            return this.hash32;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof DeviceCapsType) {
                final DeviceCapsType deviceCapsType = (DeviceCapsType)o;
                return this.deviceType == deviceCapsType.deviceType && this.capsType == deviceCapsType.capsType;
            }
            return false;
        }
        
        @Override
        public final String toString() {
            return "DeviceCapsType[" + this.deviceType.getName() + ", " + this.capsType.getName() + "]";
        }
    }
}
