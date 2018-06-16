// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.gluegen.runtime.FunctionAddressResolver;
import jogamp.opengl.GLContextImpl;
import com.jogamp.common.util.locks.LockFactory;
import com.jogamp.common.util.PropertyAccess;
import java.util.List;
import jogamp.opengl.GLDynamicLookupHelper;
import jogamp.opengl.DesktopGLDynamicLookupHelper;
import com.jogamp.nativewindow.NativeWindowVersion;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.common.util.VersionUtil;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.util.Iterator;
import java.util.Map;
import java.security.AccessController;
import com.jogamp.common.jvm.JNILibLoaderBase;
import jogamp.nativewindow.Debug;
import com.jogamp.common.util.cache.TempJarCache;
import com.jogamp.common.os.Platform;
import java.security.PrivilegedAction;
import com.jogamp.common.util.ReflectionUtil;
import com.jogamp.common.ExceptionUtils;
import java.util.HashMap;
import com.jogamp.common.util.locks.RecursiveThreadGroupLock;
import com.jogamp.nativewindow.AbstractGraphicsDevice;
import jogamp.opengl.GLDrawableFactoryImpl;
import java.lang.reflect.Constructor;

public class GLProfile
{
    public static final boolean DEBUG;
    public static final boolean disableOpenGLCore;
    public static final boolean disableOpenGLARBContext;
    public static final boolean disableOpenGLES;
    public static final boolean disableOpenGLDesktop;
    public static final boolean disableSurfacelessContext;
    public static final boolean enableANGLE;
    public static final String GL4bc = "GL4bc";
    public static final String GL4 = "GL4";
    public static final String GL3bc = "GL3bc";
    public static final String GL3 = "GL3";
    public static final String GL2 = "GL2";
    public static final String GLES1 = "GLES1";
    public static final String GLES2 = "GLES2";
    public static final String GLES3 = "GLES3";
    public static final String GL2ES1 = "GL2ES1";
    public static final String GL2ES2 = "GL2ES2";
    public static final String GL2GL3 = "GL2GL3";
    public static final String GL4ES3 = "GL4ES3";
    private static final String GL_DEFAULT = "GL_DEFAULT";
    private static final String GL_GL = "GL";
    public static final String[] GL_PROFILE_LIST_ALL;
    public static final String[] GL_PROFILE_LIST_MAX;
    public static final String[] GL_PROFILE_LIST_MIN;
    public static final String[] GL_PROFILE_LIST_MIN_DESKTOP;
    public static final String[] GL_PROFILE_LIST_MAX_FIXEDFUNC;
    public static final String[] GL_PROFILE_LIST_MAX_PROGSHADER;
    public static final String[] GL_PROFILE_LIST_MAX_PROGSHADER_CORE;
    private static boolean isAWTAvailable;
    private static boolean hasDesktopGLFactory;
    private static boolean hasGL234Impl;
    private static boolean hasEGLFactory;
    private static boolean hasGLES3Impl;
    private static boolean hasGLES1Impl;
    private static boolean hasGL234OnEGLImpl;
    private static Constructor<?> ctorGL234Impl;
    private static Constructor<?> ctorGLES3Impl;
    private static Constructor<?> ctorGLES1Impl;
    private static Constructor<?> ctorGL234ProcAddr;
    private static Constructor<?> ctorGLES3ProcAddr;
    private static Constructor<?> ctorGLES1ProcAddr;
    private static GLDrawableFactoryImpl eglFactory;
    private static GLDrawableFactoryImpl desktopFactory;
    private static AbstractGraphicsDevice defaultDevice;
    private static boolean initialized;
    private static final RecursiveThreadGroupLock initLock;
    private static final Class<?>[] ctorGLArgs;
    private static final Class<?>[] ctorProcArgs;
    private static final String GL4bcImplClassName = "jogamp.opengl.gl4.GL4bcImpl";
    private static final String GL4bcProcClassName = "jogamp.opengl.gl4.GL4bcProcAddressTable";
    private static final String GLES1ImplClassName = "jogamp.opengl.es1.GLES1Impl";
    private static final String GLES1ProcClassName = "jogamp.opengl.es1.GLES1ProcAddressTable";
    private static final String GLES3ImplClassName = "jogamp.opengl.es3.GLES3Impl";
    private static final String GLES3ProcClassName = "jogamp.opengl.es3.GLES3ProcAddressTable";
    private static HashMap<String, HashMap<String, GLProfile>> deviceConn2ProfileMap;
    private final GLProfile profileImpl;
    private final String profile;
    private final boolean isHardwareRasterizer;
    private final boolean isCustom;
    
    public static boolean isInitialized() {
        GLProfile.initLock.lock();
        try {
            return GLProfile.initialized;
        }
        finally {
            GLProfile.initLock.unlock();
        }
    }
    
    public static void initSingleton() {
        GLProfile.initLock.lock();
        int n;
        try {
            if (!GLProfile.initialized) {
                GLProfile.initialized = true;
                n = 1;
                if (GLProfile.DEBUG) {
                    System.err.println("GLProfile.initSingleton() - thread " + Thread.currentThread().getName());
                    ExceptionUtils.dumpStack(System.err);
                }
                if (ReflectionUtil.DEBUG_STATS_FORNAME) {
                    ReflectionUtil.resetForNameCount();
                }
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                    @Override
                    public Object run() {
                        Platform.initSingleton();
                        if (TempJarCache.isInitialized()) {
                            final ClassLoader classLoader = GLProfile.class.getClassLoader();
                            final Class[] array = { Debug.class, jogamp.opengl.Debug.class, null };
                            if (ReflectionUtil.isClassAvailable("jogamp.newt.Debug", classLoader)) {
                                array[2] = ReflectionUtil.getClass("jogamp.newt.Debug", false, classLoader);
                            }
                            JNILibLoaderBase.addNativeJarLibsJoglCfg(array);
                        }
                        initProfilesForDefaultDevices();
                        return null;
                    }
                });
                if (ReflectionUtil.DEBUG_STATS_FORNAME && n != 0) {
                    System.err.println(ReflectionUtil.getForNameStats(null).toString());
                }
            }
            else {
                n = 0;
            }
        }
        finally {
            GLProfile.initLock.unlock();
        }
        if (GLProfile.DEBUG && n != 0 && (GLProfile.hasGL234Impl || GLProfile.hasGL234OnEGLImpl || GLProfile.hasGLES1Impl || GLProfile.hasGLES3Impl)) {
            System.err.println(JoglVersion.getDefaultOpenGLInfo(GLProfile.defaultDevice, null, true));
        }
    }
    
    public static void initProfiles(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        getProfileMap(abstractGraphicsDevice, true);
    }
    
    public static void shutdown() {
        GLProfile.initLock.lock();
        try {
            if (GLProfile.initialized) {
                GLProfile.initialized = false;
                if (GLProfile.DEBUG) {
                    System.err.println("GLProfile.shutdown() - thread " + Thread.currentThread().getName());
                    ExceptionUtils.dumpStack(System.err);
                }
                GLDrawableFactory.shutdown();
            }
        }
        finally {
            GLProfile.initLock.unlock();
        }
    }
    
    public static boolean isAvailable(final AbstractGraphicsDevice abstractGraphicsDevice, final String s) {
        initSingleton();
        return isAvailableImpl(getProfileMap(abstractGraphicsDevice, false), s);
    }
    
    private static boolean isAvailableImpl(final HashMap<String, GLProfile> hashMap, final String s) {
        return null != hashMap && null != hashMap.get(s);
    }
    
    public static boolean isAvailable(final String s) {
        return isAvailable(null, s);
    }
    
    public static boolean isAnyAvailable() {
        return isAvailable(null, null);
    }
    
    public static String glAvailabilityToString(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return glAvailabilityToString(abstractGraphicsDevice, null).toString();
    }
    
    public static StringBuilder glAvailabilityToString(final AbstractGraphicsDevice abstractGraphicsDevice, final StringBuilder sb) {
        return glAvailabilityToString(abstractGraphicsDevice, sb, null, 0);
    }
    
    private static StringBuilder doIndent(final StringBuilder sb, final String s, int i) {
        while (i > 0) {
            sb.append(s);
            --i;
        }
        return sb;
    }
    
    public static StringBuilder glAvailabilityToString(AbstractGraphicsDevice defaultDevice, StringBuilder sb, final String s, int n) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final boolean b = null != s;
        initSingleton();
        int n2 = 0;
        int n3 = 0;
        if (null == defaultDevice) {
            defaultDevice = GLProfile.defaultDevice;
        }
        final HashMap<String, GLProfile> profileMap = getProfileMap(defaultDevice, false);
        if (b) {
            doIndent(sb, s, n).append("Natives");
            ++n;
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL4bc ").append(s);
        }
        else {
            sb.append("Natives[GL4bc ");
        }
        final boolean availableImpl = isAvailableImpl(profileMap, "GL4bc");
        sb.append(availableImpl);
        if (availableImpl) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 4, 2);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL4 ").append(s);
        }
        else {
            sb.append(", GL4 ");
        }
        final boolean availableImpl2 = isAvailableImpl(profileMap, "GL4");
        sb.append(availableImpl2);
        if (availableImpl2) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 4, 4);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GLES3 ").append(s);
        }
        else {
            sb.append(", GLES3 ");
        }
        final boolean availableImpl3 = isAvailableImpl(profileMap, "GLES3");
        sb.append(availableImpl3);
        if (availableImpl3) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 3, 8);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL3bc ").append(s);
        }
        else {
            sb.append(", GL3bc ");
        }
        final boolean availableImpl4 = isAvailableImpl(profileMap, "GL3bc");
        sb.append(availableImpl4);
        if (availableImpl4) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 3, 2);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL3 ").append(s);
        }
        else {
            sb.append(", GL3 ");
        }
        final boolean availableImpl5 = isAvailableImpl(profileMap, "GL3");
        sb.append(availableImpl5);
        if (availableImpl5) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 3, 4);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL2 ").append(s);
        }
        else {
            sb.append(", GL2 ");
        }
        final boolean availableImpl6 = isAvailableImpl(profileMap, "GL2");
        sb.append(availableImpl6);
        if (availableImpl6) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 2, 2);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GLES2 ").append(s);
        }
        else {
            sb.append(", GLES2 ");
        }
        final boolean availableImpl7 = isAvailableImpl(profileMap, "GLES2");
        sb.append(availableImpl7);
        if (availableImpl7) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 2, 8);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GLES1 ").append(s);
        }
        else {
            sb.append(", GLES1 ");
        }
        final boolean availableImpl8 = isAvailableImpl(profileMap, "GLES1");
        sb.append(availableImpl8);
        if (availableImpl8) {
            ++n3;
            glAvailabilityToString(defaultDevice, sb.append(" "), 1, 8);
        }
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("Count\t" + n3 + " / " + n2);
            --n;
            doIndent(sb.append(Platform.getNewline()), s, n).append("Common");
            ++n;
        }
        else {
            sb.append(", count " + n3 + " / " + n2 + "], Common[");
        }
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL4ES3 ").append(s);
        }
        else {
            sb.append(", GL4ES3 ");
        }
        sb.append(isAvailableImpl(profileMap, "GL4ES3"));
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL2GL3 ").append(s);
        }
        else {
            sb.append(", GL2GL3 ");
        }
        sb.append(isAvailableImpl(profileMap, "GL2GL3"));
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL2ES2 ").append(s);
        }
        else {
            sb.append(", GL2ES2 ");
        }
        sb.append(isAvailableImpl(profileMap, "GL2ES2"));
        ++n2;
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("GL2ES1 ").append(s);
        }
        else {
            sb.append(", GL2ES1 ");
        }
        sb.append(isAvailableImpl(profileMap, "GL2ES1"));
        ++n2;
        if (b) {
            --n;
            doIndent(sb.append(Platform.getNewline()), s, n).append("Mappings");
            ++n;
        }
        else {
            sb.append("], Mappings[");
        }
        int n4 = 0;
        if (null != profileMap) {
            for (final Map.Entry<String, GLProfile> entry : profileMap.entrySet()) {
                if ("GL_DEFAULT" != entry.getKey()) {
                    if (b) {
                        doIndent(sb.append(Platform.getNewline()), s, n);
                    }
                    sb.append(entry.getKey() + (b ? " \t" : " ") + entry.getValue());
                    if (!b) {
                        sb.append(", ");
                    }
                    ++n4;
                }
            }
            if (b) {
                doIndent(sb.append(Platform.getNewline()), s, n).append("default ");
            }
            else {
                sb.append(", default ");
            }
            try {
                sb.append(getDefault(defaultDevice));
            }
            catch (GLException ex) {
                sb.append("n/a");
            }
        }
        if (b) {
            doIndent(sb.append(Platform.getNewline()), s, n).append("Count\t" + n4 + " / " + n2);
            sb.append(Platform.getNewline());
        }
        else {
            sb.append(", count " + n4 + " / " + n2 + "]");
        }
        return sb;
    }
    
    public static String glAvailabilityToString() {
        return glAvailabilityToString(null);
    }
    
    public static GLProfile getDefault(final AbstractGraphicsDevice abstractGraphicsDevice) {
        return get(abstractGraphicsDevice, "GL_DEFAULT");
    }
    
    public static GLProfile getDefault() {
        return getDefault(GLProfile.defaultDevice);
    }
    
    public static GLProfile getMaximum(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) throws GLException {
        return get(abstractGraphicsDevice, GLProfile.GL_PROFILE_LIST_MAX, b);
    }
    
    public static GLProfile getMaximum(final boolean b) throws GLException {
        return get(GLProfile.GL_PROFILE_LIST_MAX, b);
    }
    
    public static GLProfile getMinimum(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) throws GLException {
        return get(abstractGraphicsDevice, GLProfile.GL_PROFILE_LIST_MIN, b);
    }
    
    public static GLProfile getMinimum(final boolean b) throws GLException {
        return get(GLProfile.GL_PROFILE_LIST_MIN, b);
    }
    
    public static GLProfile getMaxFixedFunc(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) throws GLException {
        return get(abstractGraphicsDevice, GLProfile.GL_PROFILE_LIST_MAX_FIXEDFUNC, b);
    }
    
    public static GLProfile getMaxFixedFunc(final boolean b) throws GLException {
        return get(GLProfile.GL_PROFILE_LIST_MAX_FIXEDFUNC, b);
    }
    
    public static GLProfile getMaxProgrammable(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) throws GLException {
        return get(abstractGraphicsDevice, GLProfile.GL_PROFILE_LIST_MAX_PROGSHADER, b);
    }
    
    public static GLProfile getMaxProgrammable(final boolean b) throws GLException {
        return get(GLProfile.GL_PROFILE_LIST_MAX_PROGSHADER, b);
    }
    
    public static GLProfile getMaxProgrammableCore(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b) throws GLException {
        return get(abstractGraphicsDevice, GLProfile.GL_PROFILE_LIST_MAX_PROGSHADER_CORE, b);
    }
    
    public static GLProfile getMaxProgrammableCore(final boolean b) throws GLException {
        return get(GLProfile.GL_PROFILE_LIST_MAX_PROGSHADER_CORE, b);
    }
    
    public static GLProfile getGL2ES1(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        return get(abstractGraphicsDevice, "GL2ES1").getImpl();
    }
    
    public static GLProfile getGL2ES1() throws GLException {
        return get(GLProfile.defaultDevice, "GL2ES1").getImpl();
    }
    
    public static GLProfile getGL2ES2(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        return get(abstractGraphicsDevice, "GL2ES2").getImpl();
    }
    
    public static GLProfile getGL2ES2() throws GLException {
        return get(GLProfile.defaultDevice, "GL2ES2").getImpl();
    }
    
    public static GLProfile getGL4ES3(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        return get(abstractGraphicsDevice, "GL4ES3").getImpl();
    }
    
    public static GLProfile getGL4ES3() throws GLException {
        return get(GLProfile.defaultDevice, "GL4ES3").getImpl();
    }
    
    public static GLProfile getGL2GL3(final AbstractGraphicsDevice abstractGraphicsDevice) throws GLException {
        return get(abstractGraphicsDevice, "GL2GL3").getImpl();
    }
    
    public static GLProfile getGL2GL3() throws GLException {
        return get(GLProfile.defaultDevice, "GL2GL3").getImpl();
    }
    
    public static GLProfile get(final AbstractGraphicsDevice abstractGraphicsDevice, String s) throws GLException {
        if (null == s || s == "GL") {
            s = "GL_DEFAULT";
        }
        final HashMap<String, GLProfile> profileMap = getProfileMap(abstractGraphicsDevice, true);
        final GLProfile glProfile = profileMap.get(s);
        if (null == glProfile) {
            throw new GLException("Profile " + s + " is not available on " + abstractGraphicsDevice + ", but: " + profileMap.values());
        }
        return glProfile;
    }
    
    public static GLProfile get(final String s) throws GLException {
        return get(GLProfile.defaultDevice, s);
    }
    
    public static GLProfile get(final AbstractGraphicsDevice abstractGraphicsDevice, final String[] array, final boolean b) throws GLException {
        GLProfile glProfile = null;
        final HashMap<String, GLProfile> profileMap = getProfileMap(abstractGraphicsDevice, true);
        for (int i = 0; i < array.length; ++i) {
            final GLProfile glProfile2 = profileMap.get(array[i]);
            if (null != glProfile2) {
                if (!b) {
                    return glProfile2;
                }
                if (glProfile2.isHardwareRasterizer()) {
                    return glProfile2;
                }
                if (null == glProfile) {
                    glProfile = glProfile2;
                }
            }
        }
        if (null != glProfile) {
            return glProfile;
        }
        throw new GLException("Profiles " + array2String(array) + " not available on device " + abstractGraphicsDevice);
    }
    
    public static GLProfile get(final String[] array, final boolean b) throws GLException {
        return get(GLProfile.defaultDevice, array, b);
    }
    
    public static boolean usesNativeGLES1(final String s) {
        return "GLES1" == s;
    }
    
    public static boolean usesNativeGLES2(final String s) {
        return "GLES3" == s || "GLES2" == s;
    }
    
    public static boolean usesNativeGLES3(final String s) {
        return "GLES3" == s;
    }
    
    public static boolean usesNativeGLES(final String s) {
        return usesNativeGLES2(s) || usesNativeGLES1(s);
    }
    
    public static boolean isAWTAvailable() {
        return GLProfile.isAWTAvailable;
    }
    
    public static String getGLTypeName(final int n) {
        switch (n) {
            case 5121: {
                return "GL_UNSIGNED_BYTE";
            }
            case 5120: {
                return "GL_BYTE";
            }
            case 5123: {
                return "GL_UNSIGNED_SHORT";
            }
            case 5122: {
                return "GL_SHORT";
            }
            case 5126: {
                return "GL_FLOAT";
            }
            case 5132: {
                return "GL_FIXED";
            }
            case 5124: {
                return "GL_INT";
            }
            case 5125: {
                return "GL_UNSIGNED_INT";
            }
            case 5130: {
                return "GL_DOUBLE";
            }
            case 5127: {
                return "GL_2_BYTES";
            }
            case 5128: {
                return "GL_3_BYTES";
            }
            case 5129: {
                return "GL_4_BYTES";
            }
            default: {
                return null;
            }
        }
    }
    
    public static String getGLArrayName(final int n) {
        switch (n) {
            case 32884: {
                return "GL_VERTEX_ARRAY";
            }
            case 32885: {
                return "GL_NORMAL_ARRAY";
            }
            case 32886: {
                return "GL_COLOR_ARRAY";
            }
            case 32888: {
                return "GL_TEXTURE_COORD_ARRAY";
            }
            default: {
                return null;
            }
        }
    }
    
    public final String getGLImplBaseClassName() {
        return getGLImplBaseClassName(this.getImplName());
    }
    
    private static final String getGLImplBaseClassName(final String s) {
        if ("GLES2" == s || "GLES3" == s) {
            return "jogamp.opengl.es3.GLES3";
        }
        if ("GLES1" == s) {
            return "jogamp.opengl.es1.GLES1";
        }
        if ("GL4bc" == s || "GL4" == s || "GL3bc" == s || "GL3" == s || "GL2" == s) {
            return "jogamp.opengl.gl4.GL4bc";
        }
        throw new GLException("unsupported profile \"" + s + "\"");
    }
    
    public final Constructor<?> getGLCtor(final boolean b) {
        return getGLCtor(this.getImplName(), b);
    }
    
    private static final Constructor<?> getGLCtor(final String s, final boolean b) {
        if ("GLES2" == s || "GLES3" == s) {
            return b ? GLProfile.ctorGLES3Impl : GLProfile.ctorGLES3ProcAddr;
        }
        if ("GLES1" == s) {
            return b ? GLProfile.ctorGLES1Impl : GLProfile.ctorGLES1ProcAddr;
        }
        if ("GL4bc" == s || "GL4" == s || "GL3bc" == s || "GL3" == s || "GL2" == s) {
            return b ? GLProfile.ctorGL234Impl : GLProfile.ctorGL234ProcAddr;
        }
        throw new GLException("unsupported profile \"" + s + "\"");
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GLProfile) {
            final GLProfile glProfile = (GLProfile)o;
            return this.getName() == glProfile.getName() && this.getImplName() == glProfile.getImplName();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 97 * (97 * 5 + this.getImplName().hashCode()) + this.getName().hashCode();
    }
    
    public final void verifyEquality(final GLProfile glProfile) throws GLException {
        if (!this.equals(glProfile)) {
            throw new GLException("GLProfiles are not equal: " + this + " != " + glProfile);
        }
    }
    
    public final String getName() {
        return this.profile;
    }
    
    public final GLProfile getImpl() {
        return (null != this.profileImpl) ? this.profileImpl : this;
    }
    
    public final boolean isHardwareRasterizer() {
        return this.isHardwareRasterizer;
    }
    
    public final String getImplName() {
        return (null != this.profileImpl) ? this.profileImpl.getName() : this.getName();
    }
    
    public final boolean isGL4bc() {
        return "GL4bc" == this.profile;
    }
    
    public final boolean isGL4() {
        return this.isGL4bc() || "GL4" == this.profile;
    }
    
    public final boolean isGL3bc() {
        return this.isGL4bc() || "GL3bc" == this.profile;
    }
    
    public final boolean isGL3() {
        return this.isGL4() || this.isGL3bc() || "GL3" == this.profile;
    }
    
    public final boolean isGL2() {
        return this.isGL3bc() || "GL2" == this.profile;
    }
    
    public final boolean isGLES1() {
        return "GLES1" == this.profile;
    }
    
    public final boolean isGLES2() {
        return this.isGLES3() || "GLES2" == this.profile;
    }
    
    public final boolean isGLES3() {
        return "GLES3" == this.profile;
    }
    
    public final boolean isGLES() {
        return "GLES3" == this.profile || "GLES2" == this.profile || "GLES1" == this.profile;
    }
    
    public final boolean isGL2ES1() {
        return "GL2ES1" == this.profile || this.isGLES1() || this.isGL2();
    }
    
    public final boolean isGL2GL3() {
        return "GL2GL3" == this.profile || this.isGL3() || this.isGL2();
    }
    
    public final boolean isGL2ES2() {
        return "GL2ES2" == this.profile || this.isGLES2() || this.isGL2GL3();
    }
    
    public final boolean isGL2ES3() {
        return this.isGL3ES3() || this.isGL2GL3();
    }
    
    public final boolean isGL3ES3() {
        return this.isGL4ES3() || this.isGL3();
    }
    
    public final boolean isGL4ES3() {
        return "GL4ES3" == this.profile || this.isGLES3() || this.isGL4();
    }
    
    public final boolean hasGLSL() {
        return this.isGL2ES2();
    }
    
    public final boolean usesNativeGLES1() {
        return "GLES1" == this.getImplName();
    }
    
    public final boolean usesNativeGLES2() {
        return "GLES2" == this.getImplName();
    }
    
    public final boolean usesNativeGLES3() {
        return "GLES3" == this.getImplName();
    }
    
    public final boolean usesNativeGLES() {
        return this.usesNativeGLES3() || this.usesNativeGLES2() || this.usesNativeGLES1();
    }
    
    public boolean isValidDataType(final int n, final boolean b) {
        switch (n) {
            case 5120:
            case 5121:
            case 5122:
            case 5123:
            case 5126:
            case 5132: {
                return true;
            }
            case 5124:
            case 5125: {
                if (this.isGL2ES2()) {
                    return true;
                }
            }
            case 5130: {
                if (this.isGL3()) {
                    return true;
                }
            }
            case 5127:
            case 5128:
            case 5129: {
                if (this.isGL2()) {
                    return true;
                }
                break;
            }
        }
        if (b) {
            throw new GLException("Illegal data type on profile " + this + ": " + n);
        }
        return false;
    }
    
    public boolean isValidArrayDataType(final int n, final int n2, final int n3, final boolean b, final boolean b2) {
        final String glArrayName = getGLArrayName(n);
        Label_1703: {
            if (this.isGLES1()) {
                if (b) {
                    if (b2) {
                        throw new GLException("Illegal array type for " + glArrayName + " on profile GLES1: VertexAttribPointer");
                    }
                    return false;
                }
                else {
                    Label_0595: {
                        switch (n) {
                            case 32884:
                            case 32888: {
                                switch (n3) {
                                    case 5120:
                                    case 5122:
                                    case 5126:
                                    case 5132: {
                                        switch (n2) {
                                            case 0:
                                            case 2:
                                            case 3:
                                            case 4: {
                                                break Label_0595;
                                            }
                                            default: {
                                                if (b2) {
                                                    throw new GLException("Illegal component number for " + glArrayName + " on profile GLES1: " + n2);
                                                }
                                                return false;
                                            }
                                        }
                                        break;
                                    }
                                    default: {
                                        if (b2) {
                                            throw new GLException("Illegal data type for " + glArrayName + " on profile GLES1: " + n3);
                                        }
                                        return false;
                                    }
                                }
                                break;
                            }
                            case 32885: {
                                switch (n3) {
                                    case 5120:
                                    case 5122:
                                    case 5126:
                                    case 5132: {
                                        switch (n2) {
                                            case 0:
                                            case 3: {
                                                break Label_0595;
                                            }
                                            default: {
                                                if (b2) {
                                                    throw new GLException("Illegal component number for " + glArrayName + " on profile GLES1: " + n2);
                                                }
                                                return false;
                                            }
                                        }
                                        break;
                                    }
                                    default: {
                                        if (b2) {
                                            throw new GLException("Illegal data type for " + glArrayName + " on profile GLES1: " + n3);
                                        }
                                        return false;
                                    }
                                }
                                break;
                            }
                            case 32886: {
                                switch (n3) {
                                    case 5121:
                                    case 5126:
                                    case 5132: {
                                        switch (n2) {
                                            case 0:
                                            case 4: {
                                                break Label_0595;
                                            }
                                            default: {
                                                if (b2) {
                                                    throw new GLException("Illegal component number for " + glArrayName + " on profile GLES1: " + n2);
                                                }
                                                return false;
                                            }
                                        }
                                        break;
                                    }
                                    default: {
                                        if (b2) {
                                            throw new GLException("Illegal data type for " + glArrayName + " on profile GLES1: " + n3);
                                        }
                                        return false;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            else if (this.isGLES2()) {
                switch (n3) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5126:
                    case 5132: {
                        break;
                    }
                    default: {
                        if (b2) {
                            throw new GLException("Illegal data type for " + glArrayName + " on profile GLES2: " + n3);
                        }
                        return false;
                    }
                }
            }
            else if (this.isGL2ES2()) {
                if (b) {
                    switch (n3) {
                        case 5120:
                        case 5121:
                        case 5122:
                        case 5123:
                        case 5124:
                        case 5125:
                        case 5126:
                        case 5130: {
                            switch (n2) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4: {
                                    break Label_1703;
                                }
                                default: {
                                    if (b2) {
                                        throw new GLException("Illegal component number for " + glArrayName + " on profile GL2: " + n2);
                                    }
                                    return false;
                                }
                            }
                            break;
                        }
                        default: {
                            if (b2) {
                                throw new GLException("Illegal data type for " + glArrayName + " on profile GL2: " + n3);
                            }
                            return false;
                        }
                    }
                }
                else {
                    switch (n) {
                        case 32884: {
                            switch (n3) {
                                case 5122:
                                case 5124:
                                case 5126:
                                case 5130: {
                                    switch (n2) {
                                        case 0:
                                        case 2:
                                        case 3:
                                        case 4: {
                                            break Label_1703;
                                        }
                                        default: {
                                            if (b2) {
                                                throw new GLException("Illegal component number for " + glArrayName + " on profile GL2: " + n2);
                                            }
                                            return false;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    if (b2) {
                                        throw new GLException("Illegal data type for " + glArrayName + " on profile GL2: " + n3);
                                    }
                                    return false;
                                }
                            }
                            break;
                        }
                        case 32885: {
                            switch (n3) {
                                case 5120:
                                case 5122:
                                case 5124:
                                case 5126:
                                case 5130: {
                                    switch (n2) {
                                        case 0:
                                        case 3: {
                                            break Label_1703;
                                        }
                                        default: {
                                            if (b2) {
                                                throw new GLException("Illegal component number for " + glArrayName + " on profile GLES1: " + n2);
                                            }
                                            return false;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    if (b2) {
                                        throw new GLException("Illegal data type for " + glArrayName + " on profile GL2: " + n3);
                                    }
                                    return false;
                                }
                            }
                            break;
                        }
                        case 32886: {
                            switch (n3) {
                                case 5120:
                                case 5121:
                                case 5122:
                                case 5123:
                                case 5124:
                                case 5125:
                                case 5126:
                                case 5130: {
                                    switch (n2) {
                                        case 0:
                                        case 3:
                                        case 4: {
                                            break Label_1703;
                                        }
                                        default: {
                                            if (b2) {
                                                throw new GLException("Illegal component number for " + glArrayName + " on profile GL2: " + n2);
                                            }
                                            return false;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    if (b2) {
                                        throw new GLException("Illegal data type for " + glArrayName + " on profile GL2: " + n3);
                                    }
                                    return false;
                                }
                            }
                            break;
                        }
                        case 32888: {
                            switch (n3) {
                                case 5122:
                                case 5124:
                                case 5126:
                                case 5130: {
                                    switch (n2) {
                                        case 0:
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4: {
                                            break Label_1703;
                                        }
                                        default: {
                                            if (b2) {
                                                throw new GLException("Illegal component number for " + glArrayName + " on profile GL2: " + n2);
                                            }
                                            return false;
                                        }
                                    }
                                    break;
                                }
                                default: {
                                    if (b2) {
                                        throw new GLException("Illegal data type for " + glArrayName + " on profile GL2: " + n3);
                                    }
                                    return false;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "GLProfile[" + this.getName() + "/" + this.getImplName() + "." + (this.isHardwareRasterizer ? "hw" : "sw") + (this.isCustom ? ".custom" : "") + "]";
    }
    
    private static final Constructor<?> getCtor(final String s, final boolean b, final ClassLoader classLoader) {
        try {
            return ReflectionUtil.getConstructor(s, b ? GLProfile.ctorGLArgs : GLProfile.ctorProcArgs, false, classLoader);
        }
        catch (Throwable t) {
            if (GLProfile.DEBUG) {
                System.err.println("Caught: " + t.getMessage());
                t.printStackTrace();
            }
            return null;
        }
    }
    
    private static final void initGLCtorImpl() {
        final ClassLoader classLoader = GLProfile.class.getClassLoader();
        final Constructor<?> ctor = getCtor("jogamp.opengl.gl4.GL4bcImpl", true, classLoader);
        final Constructor<?> ctorGL234ProcAddr = (null != ctor) ? getCtor("jogamp.opengl.gl4.GL4bcProcAddressTable", false, classLoader) : null;
        if (null != ctorGL234ProcAddr) {
            GLProfile.hasGL234Impl = true;
            GLProfile.ctorGL234Impl = ctor;
            GLProfile.ctorGL234ProcAddr = ctorGL234ProcAddr;
        }
        else {
            GLProfile.hasGL234Impl = false;
            GLProfile.ctorGL234Impl = null;
            GLProfile.ctorGL234ProcAddr = null;
        }
        GLProfile.hasGL234OnEGLImpl = GLProfile.hasGL234Impl;
        final Constructor<?> ctor2 = getCtor("jogamp.opengl.es1.GLES1Impl", true, classLoader);
        final Constructor<?> ctorGLES1ProcAddr = (null != ctor2) ? getCtor("jogamp.opengl.es1.GLES1ProcAddressTable", false, classLoader) : null;
        if (null != ctorGLES1ProcAddr) {
            GLProfile.hasGLES1Impl = true;
            GLProfile.ctorGLES1Impl = ctor2;
            GLProfile.ctorGLES1ProcAddr = ctorGLES1ProcAddr;
        }
        else {
            GLProfile.hasGLES1Impl = false;
            GLProfile.ctorGLES1Impl = null;
            GLProfile.ctorGLES1ProcAddr = null;
        }
        final Constructor<?> ctor3 = getCtor("jogamp.opengl.es3.GLES3Impl", true, classLoader);
        final Constructor<?> ctorGLES3ProcAddr = (null != ctor3) ? getCtor("jogamp.opengl.es3.GLES3ProcAddressTable", false, classLoader) : null;
        if (null != ctorGLES3ProcAddr) {
            GLProfile.hasGLES3Impl = true;
            GLProfile.ctorGLES3Impl = ctor3;
            GLProfile.ctorGLES3ProcAddr = ctorGLES3ProcAddr;
        }
        else {
            GLProfile.hasGLES3Impl = false;
            GLProfile.ctorGLES3Impl = null;
            GLProfile.ctorGLES3ProcAddr = null;
        }
    }
    
    private static void initProfilesForDefaultDevices() {
        NativeWindowFactory.initSingleton();
        if (GLProfile.DEBUG) {
            System.err.println("GLProfile.init - thread: " + Thread.currentThread().getName());
            System.err.println(VersionUtil.getPlatformInfo());
            System.err.println(GlueGenVersion.getInstance());
            System.err.println(NativeWindowVersion.getInstance());
            System.err.println(JoglVersion.getInstance());
        }
        final ClassLoader classLoader = GLProfile.class.getClassLoader();
        GLProfile.isAWTAvailable = (NativeWindowFactory.isAWTAvailable() && ReflectionUtil.isClassAvailable("com.jogamp.opengl.awt.GLCanvas", classLoader));
        initGLCtorImpl();
        GLDrawableFactory.initSingleton();
        Throwable t = null;
        try {
            GLProfile.desktopFactory = (GLDrawableFactoryImpl)GLDrawableFactory.getFactoryImpl("GL2");
            if (null != GLProfile.desktopFactory) {
                final DesktopGLDynamicLookupHelper desktopGLDynamicLookupHelper = (DesktopGLDynamicLookupHelper)GLProfile.desktopFactory.getGLDynamicLookupHelper(2, 2);
                GLProfile.hasGL234Impl = (null != desktopGLDynamicLookupHelper && desktopGLDynamicLookupHelper.isLibComplete() && GLProfile.hasGL234Impl);
                GLProfile.hasDesktopGLFactory = GLProfile.hasGL234Impl;
            }
        }
        catch (LinkageError linkageError) {
            t = linkageError;
        }
        catch (RuntimeException ex) {
            t = ex;
        }
        catch (Throwable t2) {
            t = t2;
        }
        if (GLProfile.DEBUG && null != t) {
            t.printStackTrace();
        }
        AbstractGraphicsDevice defaultDevice;
        if (null == GLProfile.desktopFactory) {
            GLProfile.hasDesktopGLFactory = false;
            GLProfile.hasGL234Impl = false;
            defaultDevice = null;
            if (GLProfile.DEBUG) {
                System.err.println("Info: GLProfile.init - Desktop GLDrawable factory not available");
            }
        }
        else {
            defaultDevice = GLProfile.desktopFactory.getDefaultDevice();
        }
        if (ReflectionUtil.isClassAvailable("jogamp.opengl.egl.EGLDrawableFactory", classLoader)) {
            Throwable t3 = null;
            try {
                GLProfile.eglFactory = (GLDrawableFactoryImpl)GLDrawableFactory.getFactoryImpl("GLES2");
                if (null != GLProfile.eglFactory) {
                    final GLDynamicLookupHelper glDynamicLookupHelper = GLProfile.eglFactory.getGLDynamicLookupHelper(2, 8);
                    final GLDynamicLookupHelper glDynamicLookupHelper2 = GLProfile.eglFactory.getGLDynamicLookupHelper(1, 8);
                    final GLDynamicLookupHelper glDynamicLookupHelper3 = GLProfile.eglFactory.getGLDynamicLookupHelper(3, 4);
                    GLProfile.hasGLES3Impl = (null != glDynamicLookupHelper && glDynamicLookupHelper.isLibComplete() && GLProfile.hasGLES3Impl);
                    GLProfile.hasGLES1Impl = (null != glDynamicLookupHelper2 && glDynamicLookupHelper2.isLibComplete() && GLProfile.hasGLES1Impl);
                    GLProfile.hasGL234OnEGLImpl = (null != glDynamicLookupHelper3 && glDynamicLookupHelper3.isLibComplete() && GLProfile.hasGL234OnEGLImpl);
                    GLProfile.hasEGLFactory = (GLProfile.hasGLES3Impl || GLProfile.hasGLES1Impl || GLProfile.hasGL234OnEGLImpl);
                }
            }
            catch (LinkageError linkageError2) {
                t3 = linkageError2;
            }
            catch (SecurityException ex2) {
                t3 = ex2;
            }
            catch (NullPointerException ex3) {
                t3 = ex3;
            }
            catch (RuntimeException ex4) {
                t3 = ex4;
            }
            if (GLProfile.DEBUG && null != t3) {
                t3.printStackTrace();
            }
        }
        AbstractGraphicsDevice defaultDevice2;
        if (null == GLProfile.eglFactory) {
            GLProfile.hasEGLFactory = false;
            GLProfile.hasGL234OnEGLImpl = false;
            GLProfile.hasGLES3Impl = false;
            GLProfile.hasGLES1Impl = false;
            defaultDevice2 = null;
            if (GLProfile.DEBUG) {
                System.err.println("Info: GLProfile.init - EGL GLDrawable factory not available");
            }
        }
        else {
            defaultDevice2 = GLProfile.eglFactory.getDefaultDevice();
        }
        if (null != defaultDevice) {
            GLProfile.defaultDevice = defaultDevice;
            if (GLProfile.DEBUG) {
                System.err.println("Info: GLProfile.init - Default device is desktop derived: " + GLProfile.defaultDevice);
            }
        }
        else if (null != defaultDevice2) {
            GLProfile.defaultDevice = defaultDevice2;
            if (GLProfile.DEBUG) {
                System.err.println("Info: GLProfile.init - Default device is EGL derived: " + GLProfile.defaultDevice);
            }
        }
        else {
            if (GLProfile.DEBUG) {
                System.err.println("Info: GLProfile.init - Default device not available");
            }
            GLProfile.defaultDevice = null;
        }
        final boolean b = null != defaultDevice2 && initProfilesForDevice(defaultDevice2);
        final boolean b2 = null != defaultDevice && initProfilesForDevice(defaultDevice);
        final boolean b3 = b || b2;
        if (GLProfile.DEBUG) {
            System.err.println("GLProfile.init addedAnyProfile       " + b3 + " (desktop: " + b2 + ", egl " + b + ")");
            System.err.println("GLProfile.init isAWTAvailable        " + GLProfile.isAWTAvailable);
            System.err.println("GLProfile.init hasDesktopGLFactory   " + GLProfile.hasDesktopGLFactory);
            System.err.println("GLProfile.init hasGL234Impl          " + GLProfile.hasGL234Impl);
            System.err.println("GLProfile.init hasEGLFactory         " + GLProfile.hasEGLFactory);
            System.err.println("GLProfile.init hasGLES1Impl          " + GLProfile.hasGLES1Impl);
            System.err.println("GLProfile.init hasGLES3Impl          " + GLProfile.hasGLES3Impl);
            System.err.println("GLProfile.init hasGL234OnEGLImpl     " + GLProfile.hasGL234OnEGLImpl);
            System.err.println("GLProfile.init defaultDevice         " + GLProfile.defaultDevice);
            System.err.println("GLProfile.init defaultDevice Desktop " + defaultDevice);
            System.err.println("GLProfile.init defaultDevice EGL     " + defaultDevice2);
            System.err.println("GLProfile.init profile order         " + array2String(GLProfile.GL_PROFILE_LIST_ALL));
        }
    }
    
    private static boolean initProfilesForDevice(final AbstractGraphicsDevice abstractGraphicsDevice) {
        if (null == abstractGraphicsDevice) {
            return false;
        }
        GLProfile.initLock.lock();
        try {
            final GLDrawableFactory factoryImpl = GLDrawableFactory.getFactoryImpl(abstractGraphicsDevice);
            factoryImpl.enterThreadCriticalZone();
            try {
                return initProfilesForDeviceCritical(abstractGraphicsDevice);
            }
            finally {
                factoryImpl.leaveThreadCriticalZone();
            }
        }
        finally {
            GLProfile.initLock.unlock();
        }
    }
    
    private static boolean initProfilesForDeviceCritical(final AbstractGraphicsDevice abstractGraphicsDevice) {
        final boolean availableGLVersionsSet = GLContext.getAvailableGLVersionsSet(abstractGraphicsDevice);
        if (GLProfile.DEBUG) {
            System.err.println("Info: GLProfile.initProfilesForDevice: " + abstractGraphicsDevice + " (" + abstractGraphicsDevice.getClass().getName() + "), isSet " + availableGLVersionsSet + ", hasDesktopGLFactory " + GLProfile.hasDesktopGLFactory + ", hasEGLFactory " + GLProfile.hasEGLFactory);
        }
        if (!availableGLVersionsSet) {
            HashMap<? extends String, ? extends GLProfile> computeProfileMap = null;
            boolean b = false;
            HashMap<? extends String, ? extends GLProfile> computeProfileMap2 = null;
            boolean b2 = false;
            final boolean b3 = GLProfile.hasDesktopGLFactory && GLProfile.desktopFactory.getIsDeviceCompatible(abstractGraphicsDevice);
            if (b3) {
                computeProfileMap(abstractGraphicsDevice, true, true);
                final Thread sharedResourceThread = GLProfile.desktopFactory.getSharedResourceThread();
                if (null != sharedResourceThread) {
                    GLProfile.initLock.addOwner(sharedResourceThread);
                }
                final boolean sharedResource = GLProfile.desktopFactory.createSharedResource(abstractGraphicsDevice);
                if (null != sharedResourceThread) {
                    GLProfile.initLock.removeOwner(sharedResourceThread);
                }
                if (sharedResource) {
                    if (!GLContext.getAvailableGLVersionsSet(abstractGraphicsDevice)) {
                        throw new InternalError("Available GLVersions not set for " + abstractGraphicsDevice);
                    }
                    computeProfileMap = computeProfileMap(abstractGraphicsDevice, false, false);
                    b = (computeProfileMap.size() > 0);
                    if (GLProfile.DEBUG) {
                        System.err.println("GLProfile.initProfilesForDevice: " + abstractGraphicsDevice + ": desktop Shared Ctx " + sharedResource + ", profiles: " + (b ? computeProfileMap.size() : 0));
                    }
                }
            }
            final boolean b4 = GLProfile.hasEGLFactory && GLProfile.eglFactory.getIsDeviceCompatible(abstractGraphicsDevice);
            if (b4) {
                computeProfileMap(abstractGraphicsDevice, true, true);
                final Thread sharedResourceThread2 = GLProfile.eglFactory.getSharedResourceThread();
                if (null != sharedResourceThread2) {
                    GLProfile.initLock.addOwner(sharedResourceThread2);
                }
                final boolean sharedResource2 = GLProfile.eglFactory.createSharedResource(abstractGraphicsDevice);
                if (null != sharedResourceThread2) {
                    GLProfile.initLock.removeOwner(sharedResourceThread2);
                }
                if (sharedResource2) {
                    if (!GLContext.getAvailableGLVersionsSet(abstractGraphicsDevice)) {
                        throw new InternalError("Available GLVersions not set for " + abstractGraphicsDevice);
                    }
                    computeProfileMap2 = computeProfileMap(abstractGraphicsDevice, false, false);
                    b2 = (computeProfileMap2.size() > 0);
                }
                if (GLProfile.DEBUG) {
                    System.err.println("GLProfile.initProfilesForDevice: " + abstractGraphicsDevice + ": egl Shared Ctx " + sharedResource2 + ", profiles: " + (b2 ? computeProfileMap2.size() : 0));
                }
            }
            if (!b && !b2) {
                setProfileMap(abstractGraphicsDevice, new HashMap<String, GLProfile>());
                if (GLProfile.DEBUG) {
                    System.err.println("GLProfile: device could not be initialized: " + abstractGraphicsDevice);
                    System.err.println("GLProfile: compatible w/ desktop: " + b3 + ", egl " + b4);
                    System.err.println("GLProfile: desktoplFactory      " + GLProfile.desktopFactory);
                    System.err.println("GLProfile: eglFactory           " + GLProfile.eglFactory);
                    System.err.println("GLProfile: hasGLES1Impl         " + GLProfile.hasGLES1Impl);
                    System.err.println("GLProfile: hasGLES3Impl         " + GLProfile.hasGLES3Impl);
                }
            }
            else {
                final HashMap<String, GLProfile> hashMap = new HashMap<String, GLProfile>();
                if (b2) {
                    hashMap.putAll(computeProfileMap2);
                }
                if (b) {
                    hashMap.putAll(computeProfileMap);
                }
                setProfileMap(abstractGraphicsDevice, hashMap);
            }
            GLContext.setAvailableGLVersionsSet(abstractGraphicsDevice, true);
            if (GLProfile.DEBUG) {
                System.err.println("GLProfile.initProfilesForDevice: " + abstractGraphicsDevice.getUniqueID() + ": added profile(s): desktop " + b + ", egl " + b2);
                System.err.println("GLProfile.initProfilesForDevice: " + abstractGraphicsDevice.getUniqueID() + ": " + glAvailabilityToString(abstractGraphicsDevice));
                if (b) {
                    dumpGLInfo(GLProfile.desktopFactory, abstractGraphicsDevice);
                    final List<GLCapabilitiesImmutable> availableCapabilities = GLProfile.desktopFactory.getAvailableCapabilities(abstractGraphicsDevice);
                    for (int i = 0; i < availableCapabilities.size(); ++i) {
                        System.err.println(availableCapabilities.get(i));
                    }
                }
                else if (b2) {
                    dumpGLInfo(GLProfile.eglFactory, abstractGraphicsDevice);
                    final List<GLCapabilitiesImmutable> availableCapabilities2 = GLProfile.eglFactory.getAvailableCapabilities(abstractGraphicsDevice);
                    for (int j = 0; j < availableCapabilities2.size(); ++j) {
                        System.err.println(availableCapabilities2.get(j));
                    }
                }
            }
            return b || b2;
        }
        final HashMap<String, GLProfile> hashMap2 = GLProfile.deviceConn2ProfileMap.get(abstractGraphicsDevice.getUniqueID());
        if (null == hashMap2) {
            throw new InternalError("GLContext Avail. GLVersion is set - but no profile map for device: " + abstractGraphicsDevice);
        }
        return null != hashMap2.get("GL_DEFAULT");
    }
    
    private static void dumpGLInfo(final GLDrawableFactoryImpl glDrawableFactoryImpl, final AbstractGraphicsDevice abstractGraphicsDevice) {
        final GLContext orCreateSharedContext = glDrawableFactoryImpl.getOrCreateSharedContext(abstractGraphicsDevice);
        if (null != orCreateSharedContext) {
            System.err.println("GLProfile.dumpGLInfo: " + orCreateSharedContext);
            orCreateSharedContext.makeCurrent();
            try {
                System.err.println(JoglVersion.getGLInfo(orCreateSharedContext.getGL(), null));
            }
            finally {
                orCreateSharedContext.release();
            }
        }
        else {
            System.err.println("GLProfile.dumpGLInfo: shared context n/a");
            System.err.println(abstractGraphicsDevice.getClass().getSimpleName() + "[type " + abstractGraphicsDevice.getType() + ", connection " + abstractGraphicsDevice.getConnection() + "]:");
            System.err.println(glAvailabilityToString(abstractGraphicsDevice, null, "\t", 1).toString());
        }
    }
    
    public static AbstractGraphicsDevice getDefaultDevice() {
        initSingleton();
        return GLProfile.defaultDevice;
    }
    
    private static String array2String(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static void glAvailabilityToString(final AbstractGraphicsDevice abstractGraphicsDevice, final StringBuilder sb, final int n, final int n2) {
        final String availableGLVersionAsString = GLContext.getAvailableGLVersionAsString(abstractGraphicsDevice, n, n2);
        if (null == availableGLVersionAsString) {
            throw new GLException("Internal Error");
        }
        sb.append("[");
        sb.append(availableGLVersionAsString);
        sb.append("]");
    }
    
    private static HashMap<String, GLProfile> computeProfileMap(final AbstractGraphicsDevice abstractGraphicsDevice, final boolean b, final boolean b2) {
        if (GLProfile.DEBUG) {
            System.err.println("GLProfile.init map " + abstractGraphicsDevice.getUniqueID() + ", desktopCtxUndef " + b + ", esCtxUndef " + b2);
        }
        final boolean[] array = { false };
        GLProfile glProfile = null;
        GLProfile glProfile2 = null;
        final HashMap<String, GLProfile> hashMap = new HashMap<String, GLProfile>(GLProfile.GL_PROFILE_LIST_ALL.length + 1);
        for (int i = 0; i < GLProfile.GL_PROFILE_LIST_ALL.length; ++i) {
            final String s = GLProfile.GL_PROFILE_LIST_ALL[i];
            final String computeProfileImpl = computeProfileImpl(abstractGraphicsDevice, s, b, b2, array);
            if (null != computeProfileImpl) {
                GLProfile glProfile3;
                if (s.equals(computeProfileImpl)) {
                    glProfile3 = new GLProfile(s, null, array[0], false);
                }
                else {
                    final GLProfile glProfile4 = hashMap.get(computeProfileImpl);
                    if (null == glProfile4) {
                        throw new InternalError("XXX0 profile[" + i + "]: " + s + " -> profileImpl " + computeProfileImpl + " !!! not mapped ");
                    }
                    glProfile3 = new GLProfile(s, glProfile4, array[0], false);
                }
                hashMap.put(s, glProfile3);
                if (GLProfile.DEBUG) {
                    System.err.println("GLProfile.init map " + glProfile3 + " on device " + abstractGraphicsDevice.getUniqueID());
                }
                if (null == glProfile2 && array[0]) {
                    glProfile2 = glProfile3;
                    if (GLProfile.DEBUG) {
                        System.err.println("GLProfile.init map defaultHW " + glProfile3 + " on device " + abstractGraphicsDevice.getUniqueID());
                    }
                }
                else if (null == glProfile) {
                    glProfile = glProfile3;
                    if (GLProfile.DEBUG) {
                        System.err.println("GLProfile.init map defaultAny " + glProfile3 + " on device " + abstractGraphicsDevice.getUniqueID());
                    }
                }
            }
            else if (GLProfile.DEBUG) {
                System.err.println("GLProfile.init map *** no mapping for " + s + " on device " + abstractGraphicsDevice.getUniqueID());
            }
        }
        if (null != glProfile2) {
            hashMap.put("GL_DEFAULT", glProfile2);
        }
        else if (null != glProfile) {
            hashMap.put("GL_DEFAULT", glProfile);
        }
        setProfileMap(abstractGraphicsDevice, hashMap);
        return hashMap;
    }
    
    private static String computeProfileImpl(final AbstractGraphicsDevice abstractGraphicsDevice, final String s, final boolean b, final boolean b2, final boolean[] array) {
        final boolean b3 = GLProfile.hasGL234Impl || GLProfile.hasGL234OnEGLImpl;
        final boolean[] array2 = { false };
        if ("GL2ES1" == s) {
            int n;
            boolean b4;
            if (GLProfile.hasGLES1Impl) {
                n = ((b2 || GLContext.isGLES1Available(abstractGraphicsDevice, array2)) ? 1 : 0);
                b4 = (n != 0 && array2[0]);
            }
            else {
                n = 0;
                b4 = false;
            }
            if (b3) {
                final boolean gl3bcAvailable = GLContext.isGL3bcAvailable(abstractGraphicsDevice, array2);
                final boolean b5 = gl3bcAvailable && array2[0];
                final boolean gl2Available = GLContext.isGL2Available(abstractGraphicsDevice, array2);
                final boolean b6 = gl2Available && array2[0];
                final boolean b7 = b5 || b6 || b4;
                if (GLContext.isGL4bcAvailable(abstractGraphicsDevice, array) && (array[0] || !b7)) {
                    return "GL4bc";
                }
                if (gl3bcAvailable && (b5 || !b7)) {
                    array[0] = b5;
                    return "GL3bc";
                }
                if ((b || gl2Available) && (b6 || !b7)) {
                    array[0] = b6;
                    return "GL2";
                }
            }
            if (n != 0) {
                array[0] = b4;
                return "GLES1";
            }
        }
        else if ("GL2ES2" == s) {
            int n2;
            boolean b8;
            int n3;
            boolean b9;
            if (GLProfile.hasGLES3Impl) {
                n2 = ((b2 || GLContext.isGLES2Available(abstractGraphicsDevice, array2)) ? 1 : 0);
                b8 = (n2 != 0 && array2[0]);
                n3 = ((b2 || GLContext.isGLES3Available(abstractGraphicsDevice, array2)) ? 1 : 0);
                b9 = (n3 != 0 && array2[0]);
            }
            else {
                n2 = 0;
                b8 = false;
                n3 = 0;
                b9 = false;
            }
            if (b3) {
                final boolean gl4bcAvailable = GLContext.isGL4bcAvailable(abstractGraphicsDevice, array2);
                final boolean b10 = gl4bcAvailable && array2[0];
                final boolean gl3Available = GLContext.isGL3Available(abstractGraphicsDevice, array2);
                final boolean b11 = gl3Available && array2[0];
                final boolean gl3bcAvailable2 = GLContext.isGL3bcAvailable(abstractGraphicsDevice, array2);
                final boolean b12 = gl3bcAvailable2 && array2[0];
                final boolean gl2Available2 = GLContext.isGL2Available(abstractGraphicsDevice, array2);
                final boolean b13 = gl2Available2 && array2[0];
                final boolean b14 = b10 || b11 || b12 || b13 || b9 || b8;
                if (GLContext.isGL4Available(abstractGraphicsDevice, array) && (array[0] || !b14)) {
                    return "GL4";
                }
                if (gl4bcAvailable && (b10 || !b14)) {
                    array[0] = b10;
                    return "GL4bc";
                }
                if (gl3Available && (b11 || !b14)) {
                    array[0] = b11;
                    return "GL3";
                }
                if (gl3bcAvailable2 && (b12 || !b14)) {
                    array[0] = b12;
                    return "GL3bc";
                }
                if ((b || gl2Available2) && (b13 || !b14)) {
                    array[0] = b13;
                    return "GL2";
                }
            }
            if (n3 != 0 && (b9 || !b8)) {
                array[0] = b9;
                return "GLES3";
            }
            if (n2 != 0) {
                array[0] = b8;
                return "GLES2";
            }
        }
        else if ("GL4ES3" == s) {
            final boolean gles3CompatibleAvailable = GLContext.isGLES3CompatibleAvailable(abstractGraphicsDevice);
            if (b || b2 || gles3CompatibleAvailable) {
                final boolean[] array3 = { false };
                final boolean b15 = GLProfile.hasGLES3Impl && (b2 || GLContext.isGLES3Available(abstractGraphicsDevice, array3));
                final boolean b16 = b15 && array3[0];
                if (b3) {
                    final boolean gl4bcAvailable2 = GLContext.isGL4bcAvailable(abstractGraphicsDevice, array2);
                    final boolean b17 = gl4bcAvailable2 && array2[0];
                    final boolean b18 = b17 || b16;
                    if (GLContext.isGL4Available(abstractGraphicsDevice, array) && (array[0] || !b18)) {
                        return "GL4";
                    }
                    if ((b || gl4bcAvailable2) && (b17 || !b18)) {
                        array[0] = b17;
                        return "GL4bc";
                    }
                }
                if (b15) {
                    array[0] = array3[0];
                    return "GLES3";
                }
            }
        }
        else if ("GL2GL3" == s) {
            if (b3) {
                final boolean gl4Available = GLContext.isGL4Available(abstractGraphicsDevice, array2);
                final boolean b19 = gl4Available && array2[0];
                final boolean gl3Available2 = GLContext.isGL3Available(abstractGraphicsDevice, array2);
                final boolean b20 = gl3Available2 && array2[0];
                final boolean gl3bcAvailable3 = GLContext.isGL3bcAvailable(abstractGraphicsDevice, array2);
                final boolean b21 = gl3bcAvailable3 && array2[0];
                final boolean gl2Available3 = GLContext.isGL2Available(abstractGraphicsDevice, array2);
                final boolean b22 = gl2Available3 && array2[0];
                final boolean b23 = b19 || b20 || b21 || b22;
                if (GLContext.isGL4bcAvailable(abstractGraphicsDevice, array) && (array[0] || !b23)) {
                    return "GL4bc";
                }
                if (gl4Available && (b19 || !b23)) {
                    array[0] = b19;
                    return "GL4";
                }
                if (gl3bcAvailable3 && (b21 || !b23)) {
                    array[0] = b21;
                    return "GL3bc";
                }
                if (gl3Available2 && (b20 || !b23)) {
                    array[0] = b20;
                    return "GL3";
                }
                if (b || gl2Available3) {
                    array[0] = b22;
                    return "GL2";
                }
            }
        }
        else {
            if ("GL4bc" == s && b3 && (b || GLContext.isGL4bcAvailable(abstractGraphicsDevice, array))) {
                return b ? "GL4bc" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 4, 2);
            }
            if ("GL4" == s && b3 && (b || GLContext.isGL4Available(abstractGraphicsDevice, array))) {
                return b ? "GL4" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 4, 4);
            }
            if ("GL3bc" == s && b3 && (b || GLContext.isGL3bcAvailable(abstractGraphicsDevice, array))) {
                return b ? "GL3bc" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 3, 2);
            }
            if ("GL3" == s && b3 && (b || GLContext.isGL3Available(abstractGraphicsDevice, array))) {
                return b ? "GL3" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 3, 4);
            }
            if ("GL2" == s && b3 && (b || GLContext.isGL2Available(abstractGraphicsDevice, array))) {
                return b ? "GL2" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 2, 2);
            }
            if ("GLES3" == s && GLProfile.hasGLES3Impl && (b2 || GLContext.isGLES3Available(abstractGraphicsDevice, array))) {
                return b2 ? "GLES3" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 3, 8);
            }
            if ("GLES2" == s && GLProfile.hasGLES3Impl && (b2 || GLContext.isGLES2Available(abstractGraphicsDevice, array))) {
                return b2 ? "GLES2" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 2, 8);
            }
            if ("GLES1" == s && GLProfile.hasGLES1Impl && (b2 || GLContext.isGLES1Available(abstractGraphicsDevice, array))) {
                return b2 ? "GLES1" : GLContext.getAvailableGLProfileName(abstractGraphicsDevice, 1, 8);
            }
        }
        return null;
    }
    
    private static HashMap<String, GLProfile> getProfileMap(AbstractGraphicsDevice defaultDevice, final boolean b) throws GLException {
        initSingleton();
        if (null == GLProfile.defaultDevice) {
            throw new GLException("No default device available");
        }
        if (null == defaultDevice) {
            defaultDevice = GLProfile.defaultDevice;
        }
        final String uniqueID = defaultDevice.getUniqueID();
        final HashMap<String, GLProfile> hashMap = GLProfile.deviceConn2ProfileMap.get(uniqueID);
        if (null != hashMap) {
            return hashMap;
        }
        if (!initProfilesForDevice(defaultDevice)) {
            if (b) {
                throw new GLException("No Profile available for " + defaultDevice);
            }
            return null;
        }
        else {
            final HashMap<String, GLProfile> hashMap2 = GLProfile.deviceConn2ProfileMap.get(uniqueID);
            if (null == hashMap2 && b) {
                throw new InternalError("initProfilesForDevice(..) didn't setProfileMap(..) for " + defaultDevice);
            }
            return hashMap2;
        }
    }
    
    private static void setProfileMap(final AbstractGraphicsDevice abstractGraphicsDevice, final HashMap<String, GLProfile> hashMap) {
        synchronized (GLProfile.deviceConn2ProfileMap) {
            GLProfile.deviceConn2ProfileMap.put(abstractGraphicsDevice.getUniqueID(), hashMap);
        }
    }
    
    private GLProfile(final String profile, final GLProfile profileImpl, final boolean isHardwareRasterizer, final boolean isCustom) {
        this.profile = profile;
        this.profileImpl = profileImpl;
        this.isHardwareRasterizer = isHardwareRasterizer;
        this.isCustom = isCustom;
    }
    
    public static GLProfile createCustomGLProfile(final String s, final GLProfile glProfile) {
        return new GLProfile(s, glProfile, glProfile.isHardwareRasterizer, true);
    }
    
    static {
        Platform.initSingleton();
        final boolean b = Platform.OSType.MACOS == Platform.getOSType();
        DEBUG = jogamp.opengl.Debug.debug("GLProfile");
        disableOpenGLCore = (PropertyAccess.isPropertyDefined("jogl.disable.openglcore", true) && !b);
        disableOpenGLARBContext = (PropertyAccess.isPropertyDefined("jogl.disable.openglarbcontext", true) && !b);
        disableOpenGLES = (GLProfile.disableOpenGLARBContext || PropertyAccess.isPropertyDefined("jogl.disable.opengles", true));
        disableOpenGLDesktop = PropertyAccess.isPropertyDefined("jogl.disable.opengldesktop", true);
        disableSurfacelessContext = PropertyAccess.isPropertyDefined("jogl.disable.surfacelesscontext", true);
        enableANGLE = PropertyAccess.isPropertyDefined("jogl.enable.ANGLE", true);
        GL_PROFILE_LIST_ALL = new String[] { "GL4bc", "GL3bc", "GL2", "GL4", "GL3", "GLES3", "GL4ES3", "GL2GL3", "GLES2", "GL2ES2", "GLES1", "GL2ES1" };
        GL_PROFILE_LIST_MAX = new String[] { "GL4bc", "GL4", "GL3bc", "GL3", "GLES3", "GL2", "GLES2", "GLES1" };
        GL_PROFILE_LIST_MIN = new String[] { "GLES1", "GLES2", "GL2", "GLES3", "GL3", "GL3bc", "GL4", "GL4bc" };
        GL_PROFILE_LIST_MIN_DESKTOP = new String[] { "GL2", "GL3bc", "GL4bc", "GL3", "GL4" };
        GL_PROFILE_LIST_MAX_FIXEDFUNC = new String[] { "GL4bc", "GL3bc", "GL2", "GLES1" };
        GL_PROFILE_LIST_MAX_PROGSHADER = new String[] { "GL4bc", "GL4", "GL3bc", "GL3", "GLES3", "GL2", "GLES2" };
        GL_PROFILE_LIST_MAX_PROGSHADER_CORE = new String[] { "GL4", "GL3", "GLES3", "GLES2" };
        GLProfile.eglFactory = null;
        GLProfile.desktopFactory = null;
        GLProfile.defaultDevice = null;
        GLProfile.initialized = false;
        initLock = LockFactory.createRecursiveThreadGroupLock();
        ctorGLArgs = new Class[] { GLProfile.class, GLContextImpl.class };
        ctorProcArgs = new Class[] { FunctionAddressResolver.class };
        GLProfile.deviceConn2ProfileMap = new HashMap<String, HashMap<String, GLProfile>>();
    }
}
