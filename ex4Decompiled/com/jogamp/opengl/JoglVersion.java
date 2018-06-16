// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.common.GlueGenVersion;
import java.util.List;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.VersionUtil;
import java.util.jar.Manifest;
import com.jogamp.common.util.JogampVersion;

public class JoglVersion extends JogampVersion
{
    protected static volatile JoglVersion jogampCommonVersionInfo;
    
    protected JoglVersion(final String s, final Manifest manifest) {
        super(s, manifest);
    }
    
    public static JoglVersion getInstance() {
        if (null == JoglVersion.jogampCommonVersionInfo) {
            synchronized (JoglVersion.class) {
                if (null == JoglVersion.jogampCommonVersionInfo) {
                    JoglVersion.jogampCommonVersionInfo = new JoglVersion("com.jogamp.opengl", VersionUtil.getManifest(JoglVersion.class.getClassLoader(), "com.jogamp.opengl"));
                }
            }
        }
        return JoglVersion.jogampCommonVersionInfo;
    }
    
    public StringBuilder toString(final GL gl, StringBuilder append) {
        append = super.toString(append).append(Platform.getNewline());
        getGLInfo(gl, append);
        return append;
    }
    
    public String toString(final GL gl) {
        return this.toString(gl, null).toString();
    }
    
    public static StringBuilder getAvailableCapabilitiesInfo(final GLDrawableFactory glDrawableFactory, final AbstractGraphicsDevice abstractGraphicsDevice, StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        boolean b = false;
        if (null != glDrawableFactory) {
            try {
                final List<GLCapabilitiesImmutable> availableCapabilities = glDrawableFactory.getAvailableCapabilities(abstractGraphicsDevice);
                if (null != availableCapabilities && availableCapabilities.size() > 0) {
                    for (int i = 0; i < availableCapabilities.size(); ++i) {
                        sb.append("\t").append(availableCapabilities.get(i)).append(Platform.getNewline());
                    }
                    b = true;
                }
            }
            catch (GLException ex) {}
        }
        if (!b) {
            sb.append("\tnone").append(Platform.getNewline());
        }
        sb.append(Platform.getNewline());
        return sb;
    }
    
    public static StringBuilder getAllAvailableCapabilitiesInfo(AbstractGraphicsDevice defaultDevice, StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        if (null == defaultDevice) {
            defaultDevice = GLProfile.getDefaultDevice();
        }
        sb.append(Platform.getNewline()).append(Platform.getNewline());
        sb.append("Desktop Capabilities: ").append(Platform.getNewline());
        getAvailableCapabilitiesInfo(GLDrawableFactory.getDesktopFactory(), defaultDevice, sb);
        sb.append("EGL Capabilities: ").append(Platform.getNewline());
        getAvailableCapabilitiesInfo(GLDrawableFactory.getEGLFactory(), defaultDevice, sb);
        return sb;
    }
    
    public static StringBuilder getDefaultOpenGLInfo(AbstractGraphicsDevice defaultDevice, StringBuilder allAvailableCapabilitiesInfo, final boolean b) {
        if (null == allAvailableCapabilitiesInfo) {
            allAvailableCapabilitiesInfo = new StringBuilder();
        }
        if (null == defaultDevice) {
            defaultDevice = GLProfile.getDefaultDevice();
        }
        allAvailableCapabilitiesInfo.append("GLProfiles on device ").append(defaultDevice).append(Platform.getNewline());
        if (null != defaultDevice) {
            GLProfile.glAvailabilityToString(defaultDevice, allAvailableCapabilitiesInfo, "\t", 1);
        }
        else {
            allAvailableCapabilitiesInfo.append("none");
        }
        if (b) {
            allAvailableCapabilitiesInfo = getAllAvailableCapabilitiesInfo(defaultDevice, allAvailableCapabilitiesInfo);
        }
        return allAvailableCapabilitiesInfo;
    }
    
    public static StringBuilder getGLInfo(final GL gl, final StringBuilder sb) {
        return getGLInfo(gl, sb, false);
    }
    
    public static StringBuilder getGLInfo(final GL gl, final StringBuilder sb, final boolean b) {
        return getGLInfo(gl, sb, true, b, b);
    }
    
    public static StringBuilder getGLInfo(final GL gl, StringBuilder sb, final boolean b, final boolean b2, final boolean b3) {
        final AbstractGraphicsDevice device = gl.getContext().getGLDrawable().getNativeSurface().getGraphicsConfiguration().getScreen().getDevice();
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("-----------------------------------------------------------------------------------------------------").append(Platform.getNewline());
        sb.append(device.getClass().getSimpleName()).append("[type ").append(device.getType()).append(", connection ").append(device.getConnection()).append("]: ").append(Platform.getNewline());
        if (b) {
            GLProfile.glAvailabilityToString(device, sb, "\t", 1);
        }
        sb.append(Platform.getNewline());
        sb = getGLStrings(gl, sb, b3);
        if (b2) {
            sb = getAllAvailableCapabilitiesInfo(device, sb);
        }
        return sb;
    }
    
    public static StringBuilder getGLStrings(final GL gl, final StringBuilder sb) {
        return getGLStrings(gl, sb, true);
    }
    
    public static StringBuilder getGLStrings(final GL gl, StringBuilder sb, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final GLContext context = gl.getContext();
        sb.append("Swap Interval  ").append(gl.getSwapInterval());
        sb.append(Platform.getNewline());
        sb.append("GL Profile     ").append(gl.getGLProfile());
        sb.append(Platform.getNewline());
        sb.append("GL Version     ").append(context.getGLVersion()).append(" [GL ").append(context.getGLVersionNumber()).append(", vendor ").append(context.getGLVendorVersionNumber()).append("]");
        sb.append(Platform.getNewline());
        sb.append("Quirks         ").append(context.getRendererQuirks());
        sb.append(Platform.getNewline());
        sb.append("Impl. class    ").append(gl.getClass().getCanonicalName());
        sb.append(Platform.getNewline());
        sb.append("GL_VENDOR      ").append(gl.glGetString(7936));
        sb.append(Platform.getNewline());
        sb.append("GL_RENDERER    ").append(gl.glGetString(7937));
        sb.append(Platform.getNewline());
        sb.append("GL_VERSION     ").append(gl.glGetString(7938));
        sb.append(Platform.getNewline());
        sb.append("GLSL           ").append(gl.hasGLSL()).append(", has-compiler-func: ").append(gl.isFunctionAvailable("glCompileShader"));
        if (gl.hasGLSL()) {
            sb.append(", version: ").append(gl.glGetString(35724)).append(" / ").append(context.getGLSLVersionNumber());
        }
        sb.append(Platform.getNewline());
        sb.append("GL FBO: basic ").append(gl.hasBasicFBOSupport()).append(", full ").append(gl.hasFullFBOSupport());
        sb.append(Platform.getNewline());
        sb.append("GL_EXTENSIONS  ").append(context.getGLExtensionCount());
        sb.append(Platform.getNewline());
        if (b) {
            sb.append("               ").append(context.getGLExtensionsString());
            sb.append(Platform.getNewline());
        }
        sb.append("GLX_EXTENSIONS ").append(context.getPlatformExtensionCount());
        sb.append(Platform.getNewline());
        if (b) {
            sb.append("               ").append(context.getPlatformExtensionsString());
            sb.append(Platform.getNewline());
        }
        sb.append("-----------------------------------------------------------------------------------------------------");
        return sb;
    }
    
    public StringBuilder getBriefOSGLBuildInfo(final GL gl, StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("OS: ").append(Platform.getOSName()).append(", version ").append(Platform.getOSVersion()).append(", arch ").append(Platform.getArchName());
        sb.append(Platform.getNewline());
        sb.append("GL_VENDOR     ").append(gl.glGetString(7936));
        sb.append(Platform.getNewline());
        sb.append("GL_RENDERER   ").append(gl.glGetString(7937));
        sb.append(Platform.getNewline());
        sb.append("GL_VERSION    ").append(gl.glGetString(7938));
        sb.append(Platform.getNewline());
        sb.append("JOGL GIT sha1 ").append(this.getImplementationCommit());
        sb.append(Platform.getNewline());
        return sb;
    }
    
    public static void main(final String[] array) {
        System.err.println(VersionUtil.getPlatformInfo());
        System.err.println(GlueGenVersion.getInstance());
        System.err.println(getInstance());
    }
}
