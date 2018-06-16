// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.Iterator;
import java.io.InputStream;
import java.util.Enumeration;
import java.io.IOException;
import java.util.jar.Attributes;
import java.io.Closeable;
import java.net.URL;
import java.util.jar.Manifest;
import com.jogamp.common.os.AndroidVersion;
import jogamp.common.os.PlatformPropsImpl;
import com.jogamp.common.os.Platform;

public class VersionUtil
{
    public static final String SEPERATOR = "-----------------------------------------------------------------------------------------------------";
    
    public static StringBuilder getPlatformInfo(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("-----------------------------------------------------------------------------------------------------").append(Platform.getNewline());
        sb.append("Platform: ").append(Platform.getOSType()).append(" / ").append(Platform.getOSName()).append(' ').append(Platform.getOSVersion()).append(" (").append(Platform.getOSVersionNumber()).append("), ");
        sb.append(Platform.getArchName()).append(" (").append(Platform.getCPUType()).append(", ").append(Platform.getABIType()).append("), ");
        sb.append(Runtime.getRuntime().availableProcessors()).append(" cores, ").append("littleEndian ").append(PlatformPropsImpl.LITTLE_ENDIAN);
        sb.append(Platform.getNewline());
        if (Platform.OSType.ANDROID == PlatformPropsImpl.OS_TYPE) {
            sb.append("Platform: Android Version: ").append(AndroidVersion.CODENAME).append(", ");
            sb.append(AndroidVersion.RELEASE).append(" [").append(AndroidVersion.RELEASE).append("], SDK: ").append(AndroidVersion.SDK_INT).append(", ").append(AndroidVersion.SDK_NAME);
            sb.append(Platform.getNewline());
        }
        Platform.getMachineDataInfo().toString(sb).append(Platform.getNewline());
        sb.append("Platform: Java Version: ").append(Platform.getJavaVersion()).append(" (").append(Platform.getJavaVersionNumber()).append("u").append(PlatformPropsImpl.JAVA_VERSION_UPDATE).append("), VM: ").append(Platform.getJavaVMName());
        sb.append(", Runtime: ").append(Platform.getJavaRuntimeName()).append(Platform.getNewline());
        sb.append("Platform: Java Vendor: ").append(Platform.getJavaVendor()).append(", ").append(Platform.getJavaVendorURL());
        sb.append(", JavaSE: ").append(PlatformPropsImpl.JAVA_SE);
        sb.append(", Java6: ").append(PlatformPropsImpl.JAVA_6);
        sb.append(", AWT enabled: ").append(Platform.AWT_AVAILABLE);
        sb.append(Platform.getNewline()).append("-----------------------------------------------------------------------------------------------------");
        return sb;
    }
    
    public static String getPlatformInfo() {
        return getPlatformInfo(null).toString();
    }
    
    public static Manifest getManifest(final ClassLoader classLoader, final String s) {
        return getManifest(classLoader, new String[] { s });
    }
    
    public static Manifest getManifest(final ClassLoader classLoader, final String[] array) {
        final Manifest[] array2 = new Manifest[array.length];
        try {
            final Enumeration<URL> resources = classLoader.getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                final InputStream openStream = resources.nextElement().openStream();
                Manifest manifest;
                try {
                    manifest = new Manifest(openStream);
                }
                finally {
                    IOUtil.close(openStream, false);
                }
                final Attributes mainAttributes = manifest.getMainAttributes();
                if (mainAttributes != null) {
                    for (int n = 0; n < array.length && null == array2[n]; ++n) {
                        if (array[n].equals(mainAttributes.getValue(Attributes.Name.EXTENSION_NAME))) {
                            if (n == 0) {
                                return manifest;
                            }
                            array2[n] = manifest;
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Unable to read manifest.", ex);
        }
        for (int i = 1; i < array2.length; ++i) {
            if (null != array2[i]) {
                return array2[i];
            }
        }
        return null;
    }
    
    public static StringBuilder getFullManifestInfo(final Manifest manifest, StringBuilder sb) {
        if (null == manifest) {
            return sb;
        }
        if (null == sb) {
            sb = new StringBuilder();
        }
        final Attributes mainAttributes = manifest.getMainAttributes();
        for (final Attributes.Name name : mainAttributes.keySet()) {
            final String value = mainAttributes.getValue(name);
            sb.append(" ");
            sb.append(name);
            sb.append(" = ");
            sb.append(value);
            sb.append(Platform.getNewline());
        }
        return sb;
    }
}
