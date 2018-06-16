// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt;

import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import com.jogamp.nativewindow.NativeWindowVersion;
import java.util.jar.Manifest;
import com.jogamp.common.util.JogampVersion;

public class NewtVersion extends JogampVersion
{
    protected static volatile NewtVersion jogampCommonVersionInfo;
    
    protected NewtVersion(final String s, final Manifest manifest) {
        super(s, manifest);
    }
    
    public static NewtVersion getInstance() {
        if (null == NewtVersion.jogampCommonVersionInfo) {
            synchronized (NewtVersion.class) {
                if (null == NewtVersion.jogampCommonVersionInfo) {
                    NewtVersion.jogampCommonVersionInfo = new NewtVersion("com.jogamp.newt", VersionUtil.getManifest(NativeWindowVersion.class.getClassLoader(), new String[] { "com.jogamp.newt", "com.jogamp.opengl" }));
                }
            }
        }
        return NewtVersion.jogampCommonVersionInfo;
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(NativeWindowVersion.getInstance());
        System.err.println(getInstance());
    }
}
