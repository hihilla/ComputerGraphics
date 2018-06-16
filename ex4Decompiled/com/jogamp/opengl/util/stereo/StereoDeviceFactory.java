// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.stereo;

import com.jogamp.nativewindow.NativeWindowFactory;
import com.jogamp.opengl.util.stereo.generic.GenericStereoDeviceFactory;
import com.jogamp.common.util.ReflectionUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class StereoDeviceFactory
{
    private static final String OVRStereoDeviceClazzName = "jogamp.opengl.oculusvr.OVRStereoDeviceFactory";
    private static final String GenericStereoDeviceClazzName;
    private static final String isAvailableMethodName = "isAvailable";
    private static final ArrayList<WeakReference<StereoDeviceFactory>> factoryList;
    private static final ArrayList<WeakReference<StereoDevice>> deviceList;
    
    public static StereoDeviceFactory createDefaultFactory() {
        final ClassLoader classLoader = StereoDeviceFactory.class.getClassLoader();
        StereoDeviceFactory stereoDeviceFactory = createFactory(classLoader, "jogamp.opengl.oculusvr.OVRStereoDeviceFactory");
        if (null == stereoDeviceFactory) {
            stereoDeviceFactory = createFactory(classLoader, StereoDeviceFactory.GenericStereoDeviceClazzName);
        }
        return stereoDeviceFactory;
    }
    
    public static StereoDeviceFactory createFactory(final DeviceType deviceType) {
        String genericStereoDeviceClazzName = null;
        switch (deviceType) {
            case Default: {
                return createDefaultFactory();
            }
            case Generic: {
                genericStereoDeviceClazzName = StereoDeviceFactory.GenericStereoDeviceClazzName;
                break;
            }
            case OculusVR: {
                genericStereoDeviceClazzName = "jogamp.opengl.oculusvr.OVRStereoDeviceFactory";
                break;
            }
            default: {
                throw new InternalError("Unsupported type " + deviceType);
            }
        }
        return createFactory(StereoDeviceFactory.class.getClassLoader(), genericStereoDeviceClazzName);
    }
    
    public static StereoDeviceFactory createFactory(final ClassLoader classLoader, final String s) {
        StereoDeviceFactory stereoDeviceFactory = null;
        try {
            if (ReflectionUtil.callStaticMethod(s, "isAvailable", null, null, classLoader)) {
                stereoDeviceFactory = (StereoDeviceFactory)ReflectionUtil.createInstance(s, classLoader);
            }
        }
        catch (Throwable t) {
            if (StereoDevice.DEBUG) {
                System.err.println("Caught " + t.getClass().getName() + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        if (null != stereoDeviceFactory) {
            addFactory2List(stereoDeviceFactory);
        }
        return stereoDeviceFactory;
    }
    
    public final StereoDevice createDevice(final int n, final StereoDeviceConfig stereoDeviceConfig, final boolean b) {
        final StereoDevice deviceImpl = this.createDeviceImpl(n, stereoDeviceConfig, b);
        if (null != deviceImpl) {
            addDevice2List(deviceImpl);
        }
        return deviceImpl;
    }
    
    protected abstract StereoDevice createDeviceImpl(final int p0, final StereoDeviceConfig p1, final boolean p2);
    
    public abstract boolean isValid();
    
    public abstract void shutdown();
    
    private static void addFactory2List(final StereoDeviceFactory stereoDeviceFactory) {
        synchronized (StereoDeviceFactory.factoryList) {
            int i = 0;
            while (i < StereoDeviceFactory.factoryList.size()) {
                if (null == StereoDeviceFactory.factoryList.get(i).get()) {
                    StereoDeviceFactory.factoryList.remove(i);
                }
                else {
                    ++i;
                }
            }
            StereoDeviceFactory.factoryList.add(new WeakReference<StereoDeviceFactory>(stereoDeviceFactory));
        }
    }
    
    private static void addDevice2List(final StereoDevice stereoDevice) {
        synchronized (StereoDeviceFactory.deviceList) {
            int i = 0;
            while (i < StereoDeviceFactory.deviceList.size()) {
                if (null == StereoDeviceFactory.deviceList.get(i).get()) {
                    StereoDeviceFactory.deviceList.remove(i);
                }
                else {
                    ++i;
                }
            }
            StereoDeviceFactory.deviceList.add(new WeakReference<StereoDevice>(stereoDevice));
        }
    }
    
    private static final void shutdownAll() {
        shutdownDevices();
        shutdownFactories();
    }
    
    private static final void shutdownFactories() {
        while (0 < StereoDeviceFactory.factoryList.size()) {
            final StereoDeviceFactory stereoDeviceFactory = StereoDeviceFactory.factoryList.remove(0).get();
            if (null != stereoDeviceFactory && stereoDeviceFactory.isValid()) {
                stereoDeviceFactory.shutdown();
            }
        }
    }
    
    private static final void shutdownDevices() {
        while (0 < StereoDeviceFactory.deviceList.size()) {
            final StereoDevice stereoDevice = StereoDeviceFactory.deviceList.remove(0).get();
            if (null != stereoDevice && stereoDevice.isValid()) {
                stereoDevice.dispose();
            }
        }
    }
    
    static {
        GenericStereoDeviceClazzName = GenericStereoDeviceFactory.class.getName();
        NativeWindowFactory.addCustomShutdownHook(false, new Runnable() {
            @Override
            public void run() {
                shutdownAll();
            }
        });
        factoryList = new ArrayList<WeakReference<StereoDeviceFactory>>();
        deviceList = new ArrayList<WeakReference<StereoDevice>>();
    }
    
    public enum DeviceType
    {
        Default, 
        Generic, 
        OculusVR, 
        OculusVR_DK2;
    }
}
