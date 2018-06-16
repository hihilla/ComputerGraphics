// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import java.util.jar.Manifest;
import com.jogamp.common.util.JogampVersion;

public class NativeWindowVersion extends JogampVersion
{
    protected static volatile NativeWindowVersion jogampCommonVersionInfo;
    
    protected NativeWindowVersion(final String s, final Manifest manifest) {
        super(s, manifest);
    }
    
    public static NativeWindowVersion getInstance() {
        if (null == NativeWindowVersion.jogampCommonVersionInfo) {
            synchronized (NativeWindowVersion.class) {
                if (null == NativeWindowVersion.jogampCommonVersionInfo) {
                    NativeWindowVersion.jogampCommonVersionInfo = new NativeWindowVersion("com.jogamp.nativewindow", VersionUtil.getManifest(NativeWindowVersion.class.getClassLoader(), new String[] { "com.jogamp.nativewindow", "com.jogamp.opengl" }));
                }
            }
        }
        return NativeWindowVersion.jogampCommonVersionInfo;
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(getInstance());
    }
}
