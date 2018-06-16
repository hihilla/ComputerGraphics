// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common;

import com.jogamp.common.util.VersionUtil;
import java.util.jar.Manifest;
import com.jogamp.common.util.JogampVersion;

public class GlueGenVersion extends JogampVersion
{
    protected static volatile GlueGenVersion jogampCommonVersionInfo;
    
    protected GlueGenVersion(final String s, final Manifest manifest) {
        super(s, manifest);
    }
    
    public static GlueGenVersion getInstance() {
        if (null == GlueGenVersion.jogampCommonVersionInfo) {
            synchronized (GlueGenVersion.class) {
                if (null == GlueGenVersion.jogampCommonVersionInfo) {
                    final Manifest manifest = VersionUtil.getManifest(GlueGenVersion.class.getClassLoader(), "com.jogamp.common");
                    if (null != manifest) {
                        GlueGenVersion.jogampCommonVersionInfo = new GlueGenVersion("com.jogamp.common", manifest);
                    }
                    else {
                        GlueGenVersion.jogampCommonVersionInfo = new GlueGenVersion("com.jogamp.gluegen", VersionUtil.getManifest(GlueGenVersion.class.getClassLoader(), "com.jogamp.gluegen"));
                    }
                }
            }
        }
        return GlueGenVersion.jogampCommonVersionInfo;
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(getInstance());
    }
}
