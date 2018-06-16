// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.jogamp.common.util.VersionNumber;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import java.util.StringTokenizer;
import com.jogamp.opengl.GL2ES3;
import java.util.HashMap;

final class ExtensionAvailabilityCache
{
    protected static final boolean DEBUG;
    private boolean initialized;
    private String glExtensions;
    private int glExtensionCount;
    private String glXExtensions;
    private int glXExtensionCount;
    private final HashMap<String, String> availableExtensionCache;
    
    ExtensionAvailabilityCache() {
        this.initialized = false;
        this.glExtensions = null;
        this.glExtensionCount = 0;
        this.glXExtensions = null;
        this.glXExtensionCount = 0;
        this.availableExtensionCache = new HashMap<String, String>(100);
        this.flush();
    }
    
    final void flush() {
        this.initialized = false;
        this.glExtensions = null;
        this.glExtensionCount = 0;
        this.glXExtensions = null;
        this.glXExtensionCount = 0;
        this.availableExtensionCache.clear();
    }
    
    final void reset(final GLContextImpl glContextImpl) {
        this.flush();
        this.initAvailableExtensions(glContextImpl);
    }
    
    final boolean isInitialized() {
        return this.initialized;
    }
    
    final int getTotalExtensionCount() {
        this.validateInitialization();
        return this.availableExtensionCache.size();
    }
    
    final boolean isExtensionAvailable(final String s) {
        this.validateInitialization();
        return null != this.availableExtensionCache.get(s);
    }
    
    final int getPlatformExtensionCount() {
        this.validateInitialization();
        return this.glXExtensionCount;
    }
    
    final String getPlatformExtensionsString() {
        this.validateInitialization();
        return this.glXExtensions;
    }
    
    final int getGLExtensionCount() {
        this.validateInitialization();
        return this.glExtensionCount;
    }
    
    final String getGLExtensionsString() {
        this.validateInitialization();
        if (ExtensionAvailabilityCache.DEBUG) {
            System.err.println("ExtensionAvailabilityCache: getGLExtensions() called");
        }
        return this.glExtensions;
    }
    
    private final void validateInitialization() {
        if (!this.isInitialized()) {
            throw new InternalError("ExtensionAvailabilityCache not initialized!");
        }
    }
    
    private final void initAvailableExtensions(final GLContextImpl glContextImpl) {
        final GL gl = glContextImpl.getGL();
        if (this.isInitialized()) {
            throw new InternalError("ExtensionAvailabilityCache already initialized!");
        }
        if (ExtensionAvailabilityCache.DEBUG) {
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: Pre-caching init " + gl + ", OpenGL " + glContextImpl.getGLVersion());
        }
        int n = 0;
        if (glContextImpl.isGL3() || glContextImpl.isGLES3()) {
            if (!glContextImpl.isFunctionAvailable("glGetStringi")) {
                if (ExtensionAvailabilityCache.DEBUG) {
                    System.err.println("GLContext: GL >= 3.1 usage, but no glGetStringi");
                }
            }
            else {
                n = 1;
            }
        }
        if (ExtensionAvailabilityCache.DEBUG) {
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: Pre-caching extension availability OpenGL " + glContextImpl.getGLVersion() + ", use " + ((n != 0) ? "glGetStringi" : "glGetString"));
        }
        if (n != 0) {
            final GL2ES3 gl2ES3 = (GL2ES3)gl;
            final int[] array = { 0 };
            gl2ES3.glGetIntegerv(33309, array, 0);
            final int glExtensionCount = array[0];
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < glExtensionCount; ++i) {
                final String glGetStringi = gl2ES3.glGetStringi(7939, i);
                if (null == this.availableExtensionCache.put(glGetStringi, glGetStringi)) {
                    if (0 < i) {
                        sb.append(" ");
                    }
                    sb.append(glGetStringi);
                }
            }
            if (glExtensionCount == 0 || sb.length() == 0) {
                n = 0;
            }
            else {
                this.glExtensions = sb.toString();
                this.glExtensionCount = glExtensionCount;
            }
        }
        if (n == 0) {
            this.glExtensions = gl.glGetString(7939);
            if (null != this.glExtensions) {
                final StringTokenizer stringTokenizer = new StringTokenizer(this.glExtensions);
                int glExtensionCount2 = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    final String trim = stringTokenizer.nextToken().trim();
                    if (null == this.availableExtensionCache.put(trim, trim)) {
                        ++glExtensionCount2;
                    }
                }
                this.glExtensionCount = glExtensionCount2;
            }
        }
        if (ExtensionAvailabilityCache.DEBUG) {
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: GL_EXTENSIONS: " + this.glExtensionCount + ", used " + ((n != 0) ? "glGetStringi" : "glGetString"));
        }
        final StringBuilder sb2 = new StringBuilder();
        final StringTokenizer stringTokenizer2 = new StringTokenizer(glContextImpl.getPlatformExtensionsStringImpl().toString());
        int glXExtensionCount = 0;
        while (stringTokenizer2.hasMoreTokens()) {
            final String trim2 = stringTokenizer2.nextToken().trim();
            if (null == this.availableExtensionCache.put(trim2, trim2)) {
                if (0 < glXExtensionCount) {
                    sb2.append(" ");
                }
                sb2.append(trim2);
                ++glXExtensionCount;
            }
        }
        this.glXExtensions = sb2.toString();
        this.glXExtensionCount = glXExtensionCount;
        if (ExtensionAvailabilityCache.DEBUG) {
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: GLX_EXTENSIONS: " + this.glXExtensionCount);
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: GL vendor: " + gl.glGetString(7936));
            System.err.println(getThreadName() + ":ExtensionAvailabilityCache: ALL EXTENSIONS: " + this.availableExtensionCache.size());
        }
        final int ctxOptions = glContextImpl.getCtxOptions();
        final VersionNumber glVersionNumber = glContextImpl.getGLVersionNumber();
        final int[] array2 = { glVersionNumber.getMajor() };
        final int[] array3 = { glVersionNumber.getMinor() };
        do {
            final String string = (glContextImpl.isGLES() ? "GL_ES_VERSION_" : "GL_VERSION_") + array2[0] + "_" + array3[0];
            this.availableExtensionCache.put(string, string);
            if (ExtensionAvailabilityCache.DEBUG) {
                System.err.println(getThreadName() + ":ExtensionAvailabilityCache: Added " + string + " to known extensions");
            }
        } while (GLContext.decrementGLVersion(ctxOptions, array2, array3));
        this.availableExtensionCache.put("<INTERNAL_DUMMY_PLACEHOLDER>", "<INTERNAL_DUMMY_PLACEHOLDER>");
        this.initialized = true;
    }
    
    static String getThreadName() {
        return Thread.currentThread().getName();
    }
    
    static {
        DEBUG = GLContextImpl.DEBUG;
    }
}
