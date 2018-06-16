// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import java.nio.Buffer;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.util.Arrays;
import com.jogamp.common.ExceptionUtils;

public class FBObject
{
    protected static final boolean DEBUG;
    private static final int USER_MAX_TEXTURE_SIZE;
    private static final boolean FBOResizeQuirk = false;
    private boolean initialized;
    private boolean fullFBOSupport;
    private boolean rgba8Avail;
    private boolean depth24Avail;
    private boolean depth32Avail;
    private boolean stencil01Avail;
    private boolean stencil04Avail;
    private boolean stencil08Avail;
    private boolean stencil16Avail;
    private boolean packedDepthStencilAvail;
    private int maxColorAttachments;
    private int maxSamples;
    private int maxTextureSize;
    private int maxRenderbufferSize;
    private int width;
    private int height;
    private int samples;
    private int vStatus;
    private boolean ignoreStatus;
    private int fbName;
    private boolean bound;
    private int colorbufferCount;
    private int textureAttachmentCount;
    private Colorbuffer[] colorbufferAttachments;
    private RenderAttachment depth;
    private RenderAttachment stencil;
    private boolean modified;
    private FBObject samplingSink;
    private Colorbuffer samplingColorSink;
    private boolean samplingSinkDirty;
    public static final int DEFAULT_BITS = 0;
    public static final int REQUESTED_BITS = -1;
    public static final int CHOSEN_BITS = -2;
    public static final int MAXIMUM_BITS = -3;
    
    static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    public static final TextureAttachment createColorTextureAttachment(final GL gl, final boolean b, final int n, final int n2) {
        return createColorTextureAttachment(gl, b, n, n2, 9728, 9728, 33071, 33071);
    }
    
    public static final TextureAttachment createColorTextureAttachment(final GL gl, final boolean b, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        int n7;
        int n8;
        int n9;
        if (gl.isGLES3()) {
            n7 = (b ? 32856 : 32849);
            n8 = (b ? 6408 : 6407);
            n9 = 5121;
        }
        else if (gl.isGLES()) {
            n7 = (b ? 6408 : 6407);
            n8 = (b ? 6408 : 6407);
            n9 = 5121;
        }
        else {
            n7 = (b ? 32856 : 32849);
            n8 = (b ? 32993 : 6407);
            n9 = (b ? 33639 : 5121);
        }
        return createColorTextureAttachment(n7, n, n2, n8, n9, n3, n4, n5, n6);
    }
    
    public static final TextureAttachment createColorTextureAttachment(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        final boolean hasAlpha = hasAlpha(n);
        int n8;
        int n9;
        if (gl.isGLES()) {
            n8 = (hasAlpha ? 6408 : 6407);
            n9 = 5121;
        }
        else {
            n8 = (hasAlpha ? 32993 : 6407);
            n9 = (hasAlpha ? 33639 : 5121);
        }
        return createColorTextureAttachment(n, n2, n3, n8, n9, n4, n5, n6, n7);
    }
    
    public static final TextureAttachment createColorTextureAttachment(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9) {
        return new TextureAttachment(Attachment.Type.COLOR_TEXTURE, n, n2, n3, n4, n5, n6, n7, n8, n9, 0);
    }
    
    private static boolean hasAlpha(final int n) {
        switch (n) {
            case 4:
            case 6408:
            case 32854:
            case 32856:
            case 32993: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private final void validateColorAttachmentPointRange(final int n) {
        if (!this.initialized) {
            throw new GLException("FBO not initialized");
        }
        if (this.maxColorAttachments != this.colorbufferAttachments.length) {
            throw new InternalError(String.format("maxColorAttachments %d, array.length %d", this.maxColorAttachments, this.colorbufferAttachments.length));
        }
        if (0 > n || n >= this.maxColorAttachments) {
            throw new IllegalArgumentException(String.format("attachment point out of range: %d, should be within [0..%d], %s", n, this.maxColorAttachments - 1, this.toString()));
        }
    }
    
    private final void validateAddColorAttachment(final int n, final Colorbuffer colorbuffer) {
        this.validateColorAttachmentPointRange(n);
        if (null != this.colorbufferAttachments[n]) {
            throw new IllegalStateException(String.format("Cannot attach %s at %d, attachment point already in use by %s, %s", colorbuffer.toString(), n, this.colorbufferAttachments[n].toString(), this.toString()));
        }
    }
    
    private final void addColorAttachment(final int n, final Colorbuffer colorbuffer, final boolean b) {
        final Colorbuffer colorbuffer2 = this.colorbufferAttachments[n];
        if (b) {
            this.validateColorAttachmentPointRange(n);
            if (null == colorbuffer) {
                throw new IllegalArgumentException("Colorbuffer is null");
            }
            if (null != colorbuffer2) {
                throw new IllegalStateException(String.format("Cannot attach %s at %d, attachment point already in use by %s, %s", colorbuffer.toString(), n, colorbuffer2.toString(), this.toString()));
            }
        }
        this.colorbufferAttachments[n] = colorbuffer;
        ++this.colorbufferCount;
        if (colorbuffer.isTextureAttachment()) {
            ++this.textureAttachmentCount;
        }
        this.modified = true;
    }
    
    private final void removeColorAttachment(final int n, final Colorbuffer colorbuffer) {
        this.validateColorAttachmentPointRange(n);
        if (null == colorbuffer) {
            throw new IllegalArgumentException("Colorbuffer is null");
        }
        final Colorbuffer colorbuffer2 = this.colorbufferAttachments[n];
        if (colorbuffer2 != colorbuffer) {
            throw new IllegalStateException(String.format("Cannot detach %s at %d, slot is holding other: %s, %s", colorbuffer.toString(), n, colorbuffer2.toString(), this.toString()));
        }
        this.colorbufferAttachments[n] = null;
        --this.colorbufferCount;
        if (colorbuffer.isTextureAttachment()) {
            --this.textureAttachmentCount;
        }
        this.modified = true;
    }
    
    public final Colorbuffer getColorbuffer(final int n) {
        this.validateColorAttachmentPointRange(n);
        return this.colorbufferAttachments[n];
    }
    
    public final int getColorbufferAttachmentPoint(final Colorbuffer colorbuffer) {
        for (int i = 0; i < this.colorbufferAttachments.length; ++i) {
            if (this.colorbufferAttachments[i] == colorbuffer) {
                return i;
            }
        }
        return -1;
    }
    
    public final Colorbuffer getColorbuffer(final Colorbuffer colorbuffer) {
        final int colorbufferAttachmentPoint = this.getColorbufferAttachmentPoint(colorbuffer);
        return (colorbufferAttachmentPoint >= 0) ? this.getColorbuffer(colorbufferAttachmentPoint) : null;
    }
    
    public final boolean hasAttachmentUsingAlpha() {
        final int colorbufferCount = this.getColorbufferCount();
        boolean b = false;
        for (int i = 0; i < colorbufferCount; ++i) {
            final Attachment attachment = (Attachment)this.getColorbuffer(i);
            if (null == attachment) {
                break;
            }
            if (hasAlpha(attachment.format)) {
                b = true;
                break;
            }
        }
        return b;
    }
    
    public FBObject() {
        this.initialized = false;
        this.fullFBOSupport = false;
        this.rgba8Avail = false;
        this.depth24Avail = false;
        this.depth32Avail = false;
        this.stencil01Avail = false;
        this.stencil04Avail = false;
        this.stencil08Avail = false;
        this.stencil16Avail = false;
        this.packedDepthStencilAvail = false;
        this.maxColorAttachments = -1;
        this.maxSamples = -1;
        this.maxTextureSize = 0;
        this.maxRenderbufferSize = 0;
        this.width = 0;
        this.height = 0;
        this.samples = 0;
        this.vStatus = -1;
        this.ignoreStatus = false;
        this.fbName = 0;
        this.bound = false;
        this.colorbufferAttachments = null;
        this.colorbufferCount = 0;
        this.textureAttachmentCount = 0;
        this.depth = null;
        this.stencil = null;
        this.modified = true;
        this.samplingSink = null;
        this.samplingColorSink = null;
        this.samplingSinkDirty = true;
    }
    
    public void init(final GL gl, final int n, final int n2, final int n3) throws IllegalStateException, GLException {
        if (this.initialized) {
            throw new IllegalStateException("FBO already initialized");
        }
        if (!gl.hasBasicFBOSupport()) {
            throw new GLException("FBO not supported w/ context: " + gl.getContext() + ", " + this);
        }
        this.fullFBOSupport = gl.hasFullFBOSupport();
        this.rgba8Avail = (gl.isGL2ES3() || gl.isExtensionAvailable("GL_OES_rgb8_rgba8"));
        this.depth24Avail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_depth24"));
        this.depth32Avail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_depth32"));
        this.stencil01Avail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_stencil1"));
        this.stencil04Avail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_stencil4"));
        this.stencil08Avail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_stencil8"));
        this.stencil16Avail = this.fullFBOSupport;
        this.packedDepthStencilAvail = (this.fullFBOSupport || gl.isExtensionAvailable("GL_OES_packed_depth_stencil") || gl.isExtensionAvailable("GL_EXT_packed_depth_stencil"));
        final boolean extensionAvailable = gl.isExtensionAvailable("GL_NV_fbo_color_attachments");
        final int[] array = { 0 };
        checkPreGLError(gl);
        int n4 = 1;
        this.maxColorAttachments = 1;
        Label_0371: {
            if (!this.fullFBOSupport) {
                if (!extensionAvailable) {
                    break Label_0371;
                }
            }
            try {
                gl.glGetIntegerv(36063, array, array[0] = 0);
                n4 = ((1 <= array[0]) ? array[0] : 1);
            }
            catch (GLException ex) {
                ex.printStackTrace();
            }
        }
        this.maxColorAttachments = ((n4 <= 8) ? n4 : 8);
        this.colorbufferAttachments = new Colorbuffer[this.maxColorAttachments];
        this.colorbufferCount = 0;
        this.textureAttachmentCount = 0;
        this.maxSamples = gl.getMaxRenderbufferSamples();
        gl.glGetIntegerv(3379, array, 0);
        final int maxTextureSize = array[0];
        if (0 < FBObject.USER_MAX_TEXTURE_SIZE) {
            this.maxTextureSize = FBObject.USER_MAX_TEXTURE_SIZE;
        }
        else {
            this.maxTextureSize = maxTextureSize;
        }
        gl.glGetIntegerv(34024, array, 0);
        this.maxRenderbufferSize = array[0];
        this.width = ((0 < n) ? n : 1);
        this.height = ((0 < n2) ? n2 : 1);
        this.samples = ((n3 <= this.maxSamples) ? n3 : this.maxSamples);
        if (FBObject.DEBUG) {
            System.err.println("FBObject.init() START: " + this.width + "x" + this.height + ", " + n3 + " -> " + this.samples + " samples");
            System.err.println("fullFBOSupport:           " + this.fullFBOSupport);
            System.err.println("maxColorAttachments:      " + this.maxColorAttachments + "/" + n4 + " [capped/real]");
            System.err.println("maxSamples:               " + this.maxSamples);
            System.err.println("maxTextureSize:           " + maxTextureSize + " -> " + this.maxTextureSize);
            System.err.println("maxRenderbufferSize:      " + this.maxRenderbufferSize);
            System.err.println("rgba8:                    " + this.rgba8Avail);
            System.err.println("depth24:                  " + this.depth24Avail);
            System.err.println("depth32:                  " + this.depth32Avail);
            System.err.println("stencil01:                " + this.stencil01Avail);
            System.err.println("stencil04:                " + this.stencil04Avail);
            System.err.println("stencil08:                " + this.stencil08Avail);
            System.err.println("stencil16:                " + this.stencil16Avail);
            System.err.println("packedDepthStencil:       " + this.packedDepthStencilAvail);
            System.err.println("NV_fbo_color_attachments: " + extensionAvailable);
            System.err.println(gl.getContext().getGLVersion());
            System.err.println(JoglVersion.getGLStrings(gl, null, false).toString());
        }
        checkPreGLError(gl);
        if (this.width > this.maxRenderbufferSize || this.height > this.maxRenderbufferSize) {
            throw new GLException("Size " + this.width + "x" + this.height + " exceeds on of the maxima renderbuffer size " + this.maxRenderbufferSize + ": \n\t" + this);
        }
        this.modified = true;
        this.samplingSinkDirty = true;
        gl.glGenFramebuffers(1, array, 0);
        this.fbName = array[0];
        if (0 == this.fbName) {
            throw new GLException("null framebuffer");
        }
        gl.glBindFramebuffer(36160, this.fbName);
        this.checkNoError(gl, gl.glGetError(), "FBObject Init.bindFB");
        if (!gl.glIsFramebuffer(this.fbName)) {
            this.checkNoError(gl, 1281, "FBObject Init.isFB");
        }
        this.bound = true;
        this.initialized = true;
        this.vStatus = 36055;
        if (FBObject.DEBUG) {
            System.err.println("FBObject.init() END: " + this);
            ExceptionUtils.dumpStack(System.err);
        }
    }
    
    public final boolean reset(final GL gl, int width, int height, int samples) throws GLException, IllegalStateException {
        if (!this.initialized) {
            throw new IllegalStateException("FBO not initialized");
        }
        samples = ((samples <= this.maxSamples) ? samples : this.maxSamples);
        if (width == this.width && height == this.height && samples == this.samples) {
            return false;
        }
        if (0 >= width) {
            width = 1;
        }
        if (0 >= height) {
            height = 1;
        }
        if (this.textureAttachmentCount > 0 && (width > 2 + this.maxTextureSize || height > 2 + this.maxTextureSize)) {
            throw new GLException("Size " + width + "x" + height + " exceeds on of the maximum texture size " + this.maxTextureSize + ": \n\t" + this);
        }
        if (width > this.maxRenderbufferSize || height > this.maxRenderbufferSize) {
            throw new GLException("Size " + width + "x" + height + " exceeds on of the maxima renderbuffer size " + this.maxRenderbufferSize + ": \n\t" + this);
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.reset - START - " + this.width + "x" + this.height + ", " + this.samples + " -> " + width + "x" + height + ", " + samples + "; " + this);
        }
        final boolean bound = this.isBound();
        int n;
        if ((0 < this.samples && 0 < samples) || (0 == this.samples && 0 == samples)) {
            n = 0;
        }
        else if (0 == this.samples && 0 < samples) {
            n = 1;
        }
        else {
            if (0 >= this.samples || 0 != samples) {
                throw new IllegalArgumentException("Error in sampleCount change: " + this.samples + " -> " + samples);
            }
            n = -1;
        }
        this.width = width;
        this.height = height;
        this.samples = samples;
        this.modified = true;
        this.detachAllImpl(gl, this.samplingSinkDirty = true, true, n);
        this.resetSamplingSink(gl);
        if (!bound) {
            this.unbind(gl);
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.reset - END - wasBound, " + bound + ", " + this);
        }
        return true;
    }
    
    private final void resetSizeImpl(final GL gl, final int width, final int height) {
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSize - START - " + this.width + "x" + this.height + ", " + this.samples + " -> " + width + "x" + height);
        }
        this.width = width;
        this.height = height;
        this.modified = true;
        this.detachAllImpl(gl, this.samplingSinkDirty = true, true, 0);
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSize - END - " + this);
        }
    }
    
    private void validateAttachmentSize(final Attachment attachment) {
        final int width = attachment.getWidth();
        final int height = attachment.getHeight();
        if (attachment instanceof TextureAttachment && (width > 2 + this.maxTextureSize || height > 2 + this.maxTextureSize)) {
            throw new GLException("Size " + width + "x" + height + " of " + attachment + " exceeds on of the maximum texture size " + this.maxTextureSize + ": \n\t" + this);
        }
        if (width > this.maxRenderbufferSize || height > this.maxRenderbufferSize) {
            throw new GLException("Size " + width + "x" + height + " of " + attachment + " exceeds on of the maxima renderbuffer size " + this.maxRenderbufferSize + ": \n\t" + this);
        }
    }
    
    public final void formatToGLCapabilities(final GLCapabilities glCapabilities) {
        glCapabilities.setSampleBuffers(this.samples > 0);
        glCapabilities.setNumSamples(this.samples);
        glCapabilities.setDepthBits(0);
        glCapabilities.setStencilBits(0);
        final Colorbuffer colorbuffer = (this.samples > 0) ? this.getSamplingSink() : this.getColorbuffer(0);
        if (null != colorbuffer) {
            colorbuffer.formatToGLCapabilities(glCapabilities, this.rgba8Avail);
        }
        if (null != this.depth) {
            this.depth.formatToGLCapabilities(glCapabilities, this.rgba8Avail);
        }
        if (null != this.stencil && this.stencil != this.depth) {
            this.stencil.formatToGLCapabilities(glCapabilities, this.rgba8Avail);
        }
    }
    
    public final int getStatus() {
        return this.vStatus;
    }
    
    public final String getStatusString() {
        return getStatusString(this.vStatus);
    }
    
    public static final String getStatusString(final int n) {
        switch (n) {
            case -1: {
                return "NOT A FBO";
            }
            case 36053: {
                return "OK";
            }
            case 36054: {
                return "FBO incomplete attachment\n";
            }
            case 36055: {
                return "FBO missing attachment";
            }
            case 36057: {
                return "FBO attached images must have same dimensions";
            }
            case 36058: {
                return "FBO attached images must have same format";
            }
            case 36059: {
                return "FBO missing draw buffer";
            }
            case 36060: {
                return "FBO missing read buffer";
            }
            case 36182: {
                return "FBO missing multisample buffer";
            }
            case 36264: {
                return "FBO missing layer targets";
            }
            case 36061: {
                return "Unsupported FBO format";
            }
            case 33305: {
                return "FBO undefined";
            }
            case 0: {
                return "FBO implementation fault";
            }
            default: {
                return "FBO incomplete, implementation ERROR " + toHexString(n);
            }
        }
    }
    
    public final boolean isStatusValid() {
        switch (this.vStatus) {
            case 36053: {
                return true;
            }
            case 36054:
            case 36055:
            case 36057:
            case 36058:
            case 36059:
            case 36060:
            case 36182:
            case 36264: {
                if (0 == this.colorbufferCount || null == this.depth) {
                    return true;
                }
                break;
            }
        }
        if (FBObject.DEBUG) {
            System.err.println("Framebuffer " + this.fbName + " is incomplete, status = " + toHexString(this.vStatus) + " : " + getStatusString(this.vStatus));
        }
        return false;
    }
    
    private static int checkPreGLError(final GL gl) {
        final int glGetError = gl.glGetError();
        if (FBObject.DEBUG && glGetError) {
            System.err.println("Pre-existing GL error: " + toHexString(glGetError));
            ExceptionUtils.dumpStack(System.err);
        }
        return glGetError;
    }
    
    private final boolean checkNoError(final GL gl, final int n, final String s) throws GLException {
        if (0 == n) {
            return true;
        }
        if (null != gl) {
            this.destroy(gl);
        }
        if (null != s) {
            throw new GLException(s + " GL Error " + toHexString(n) + " of " + this.toString());
        }
        return false;
    }
    
    private final void checkInitialized() throws GLException {
        if (!this.initialized) {
            throw new GLException("FBO not initialized, call init(GL) first.");
        }
    }
    
    public final TextureAttachment attachTexture2D(final GL gl, final int n, final boolean b) throws GLException {
        return this.attachColorbuffer(gl, n, createColorTextureAttachment(gl, b, this.width, this.height)).getTextureAttachment();
    }
    
    public final TextureAttachment attachTexture2D(final GL gl, final int n, final boolean b, final int n2, final int n3, final int n4, final int n5) throws GLException {
        return this.attachColorbuffer(gl, n, createColorTextureAttachment(gl, b, this.width, this.height, n2, n3, n4, n5)).getTextureAttachment();
    }
    
    public final TextureAttachment attachTexture2D(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) throws GLException {
        return this.attachColorbuffer(gl, n, createColorTextureAttachment(n2, this.width, this.height, n3, n4, n5, n6, n7, n8)).getTextureAttachment();
    }
    
    public final ColorAttachment createColorAttachment(final boolean b) {
        int n;
        if (this.rgba8Avail) {
            n = (b ? 32856 : 32849);
        }
        else {
            n = (b ? 32854 : 36194);
        }
        return createColorAttachment(n, this.samples, this.width, this.height);
    }
    
    public static final ColorAttachment createColorAttachment(final int n, final int n2, final int n3, final int n4) {
        return new ColorAttachment(n, n2, n3, n4, 0);
    }
    
    public static final RenderAttachment createRenderAttachment(final Attachment.Type type, final int n, final int n2, final int n3, final int n4) {
        return new RenderAttachment(type, n, n2, n3, n4, 0);
    }
    
    public final ColorAttachment attachColorbuffer(final GL gl, final int n, final boolean b) throws GLException {
        return this.attachColorbuffer(gl, n, this.createColorAttachment(b)).getColorAttachment();
    }
    
    public final ColorAttachment attachColorbuffer(final GL gl, final int n, final int n2) throws GLException, IllegalArgumentException {
        if (Attachment.Type.COLOR != Attachment.Type.determine(n2)) {
            throw new IllegalArgumentException("colorformat invalid: " + toHexString(n2) + ", " + this);
        }
        return this.attachColorbuffer(gl, n, createColorAttachment(n2, this.samples, this.width, this.height)).getColorAttachment();
    }
    
    public final Colorbuffer attachColorbuffer(final GL gl, final int n, final Colorbuffer colorbuffer) throws GLException {
        this.bind(gl);
        return this.attachColorbufferImpl(gl, n, colorbuffer);
    }
    
    private final Colorbuffer attachColorbufferImpl(final GL gl, final int n, final Colorbuffer colorbuffer) throws GLException {
        this.validateAddColorAttachment(n, colorbuffer);
        this.validateAttachmentSize((Attachment)colorbuffer);
        final boolean initialize = colorbuffer.initialize(gl);
        this.addColorAttachment(n, colorbuffer, false);
        if (colorbuffer.isTextureAttachment()) {
            final TextureAttachment textureAttachment = colorbuffer.getTextureAttachment();
            if (this.samples > 0) {
                this.removeColorAttachment(n, textureAttachment);
                if (initialize) {
                    textureAttachment.free(gl);
                }
                throw new GLException("Texture2D not supported w/ MSAA. If you have enabled MSAA with exisiting texture attachments, you may want to detach them via detachAllTexturebuffer(gl).");
            }
            gl.glFramebufferTexture2D(36160, 36064 + n, 3553, textureAttachment.getName(), 0);
            if (!this.ignoreStatus) {
                this.updateStatus(gl);
                if (!this.isStatusValid()) {
                    this.detachColorbuffer(gl, n, true);
                    throw new GLException("attachTexture2D " + textureAttachment + " at " + n + " failed: " + this.getStatusString() + ", " + this);
                }
            }
        }
        else {
            final ColorAttachment colorAttachment = colorbuffer.getColorAttachment();
            gl.glFramebufferRenderbuffer(36160, 36064 + n, 36161, colorAttachment.getName());
            if (!this.ignoreStatus) {
                this.updateStatus(gl);
                if (!this.isStatusValid()) {
                    this.detachColorbuffer(gl, n, true);
                    throw new GLException("attachColorbuffer " + colorAttachment + " at " + n + " failed: " + this.getStatusString() + ", " + this);
                }
            }
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.attachColorbuffer.X: [attachmentPoint " + n + ", colbuf " + colorbuffer + "]: " + this);
        }
        return colorbuffer;
    }
    
    private final int getDepthIFormat(final int n) {
        if (32 <= n && this.depth32Avail) {
            return 33191;
        }
        if (24 > n || (!this.depth24Avail && !this.depth32Avail)) {
            return 33189;
        }
        if (this.depth24Avail) {
            return 33190;
        }
        return 33191;
    }
    
    private final int getStencilIFormat(final int n) {
        if (16 <= n && this.stencil16Avail) {
            return 36169;
        }
        if (8 <= n && (this.stencil08Avail || this.stencil16Avail)) {
            if (this.stencil08Avail) {
                return 36168;
            }
            return 36169;
        }
        else if (4 <= n && (this.stencil04Avail || this.stencil08Avail || this.stencil16Avail)) {
            if (this.stencil04Avail) {
                return 36167;
            }
            if (this.stencil08Avail) {
                return 36168;
            }
            return 36169;
        }
        else {
            if (1 > n || (!this.stencil01Avail && !this.stencil04Avail && !this.stencil08Avail && !this.stencil16Avail)) {
                throw new GLException("stencil buffer n/a");
            }
            if (this.stencil01Avail) {
                return 36166;
            }
            if (this.stencil04Avail) {
                return 36167;
            }
            if (this.stencil08Avail) {
                return 36168;
            }
            return 36169;
        }
    }
    
    public final void attachRenderbuffer(final GL gl, final Attachment.Type type, final int n) throws GLException, IllegalArgumentException {
        if (-3 > n) {
            throw new IllegalArgumentException("reqBits out of range, shall be >= -3");
        }
        int n2;
        int n3;
        if (-3 == n) {
            n2 = 32;
            n3 = 16;
        }
        else if (-2 == n) {
            final GLCapabilitiesImmutable chosenGLCapabilities = gl.getContext().getGLDrawable().getChosenGLCapabilities();
            n2 = chosenGLCapabilities.getDepthBits();
            n3 = chosenGLCapabilities.getStencilBits();
        }
        else if (-1 == n) {
            final GLCapabilitiesImmutable requestedGLCapabilities = gl.getContext().getGLDrawable().getRequestedGLCapabilities();
            n2 = requestedGLCapabilities.getDepthBits();
            n3 = requestedGLCapabilities.getStencilBits();
        }
        else if (0 == n) {
            n2 = 24;
            n3 = 8;
        }
        else {
            n2 = n;
            n3 = n;
        }
        int stencilIFormat = -1;
        int n4 = 0;
        switch (type) {
            case DEPTH: {
                n4 = this.getDepthIFormat(n2);
                break;
            }
            case STENCIL: {
                n4 = this.getStencilIFormat(n3);
                break;
            }
            case DEPTH_STENCIL: {
                if (this.packedDepthStencilAvail) {
                    n4 = 35056;
                    break;
                }
                n4 = this.getDepthIFormat(n2);
                stencilIFormat = this.getStencilIFormat(n3);
                break;
            }
            default: {
                throw new IllegalArgumentException("only depth/stencil types allowed, was " + type + ", " + this);
            }
        }
        this.attachRenderbufferImpl(gl, type, n4);
        if (0 <= stencilIFormat) {
            this.attachRenderbufferImpl(gl, Attachment.Type.STENCIL, stencilIFormat);
        }
    }
    
    public final void attachRenderbuffer(final GL gl, final int n) throws GLException, IllegalArgumentException {
        final Attachment.Type determine = Attachment.Type.determine(n);
        if (Attachment.Type.DEPTH != determine && Attachment.Type.STENCIL != determine && Attachment.Type.DEPTH_STENCIL != determine) {
            throw new IllegalArgumentException("renderformat invalid: " + toHexString(n) + ", " + this);
        }
        this.attachRenderbufferImpl(gl, determine, n);
    }
    
    protected final void attachRenderbufferImpl(final GL gl, final Attachment.Type type, final int n) throws GLException {
        if (null != this.depth && (Attachment.Type.DEPTH == type || Attachment.Type.DEPTH_STENCIL == type)) {
            throw new GLException("FBO depth buffer already attached (rb " + this.depth + "), type is " + type + ", " + toHexString(n) + ", " + this);
        }
        if (null != this.stencil && (Attachment.Type.STENCIL == type || Attachment.Type.DEPTH_STENCIL == type)) {
            throw new GLException("FBO stencil buffer already attached (rb " + this.stencil + "), type is " + type + ", " + toHexString(n) + ", " + this);
        }
        this.bind(gl);
        this.attachRenderbufferImpl2(gl, type, n);
    }
    
    private final void attachRenderbufferImpl2(final GL gl, final Attachment.Type type, final int n) throws GLException {
        if (Attachment.Type.DEPTH == type) {
            if (null == this.depth) {
                this.depth = createRenderAttachment(Attachment.Type.DEPTH, n, this.samples, this.width, this.height);
            }
            else {
                this.depth.setSize(this.width, this.height);
                this.depth.setSamples(this.samples);
            }
            this.validateAttachmentSize(this.depth);
            this.depth.initialize(gl);
        }
        else if (Attachment.Type.STENCIL == type) {
            if (null == this.stencil) {
                this.stencil = createRenderAttachment(Attachment.Type.STENCIL, n, this.samples, this.width, this.height);
            }
            else {
                this.stencil.setSize(this.width, this.height);
                this.stencil.setSamples(this.samples);
            }
            this.validateAttachmentSize(this.stencil);
            this.stencil.initialize(gl);
        }
        else if (Attachment.Type.DEPTH_STENCIL == type) {
            if (null == this.depth) {
                if (null != this.stencil) {
                    throw new InternalError("XXX: DEPTH_STENCIL, depth was null, stencil not: " + this.toString());
                }
                this.depth = createRenderAttachment(Attachment.Type.DEPTH_STENCIL, n, this.samples, this.width, this.height);
            }
            else {
                this.depth.setSize(this.width, this.height);
                this.depth.setSamples(this.samples);
            }
            this.validateAttachmentSize(this.depth);
            this.depth.initialize(gl);
            this.stencil = this.depth;
        }
        if (Attachment.Type.DEPTH == type) {
            gl.glFramebufferRenderbuffer(36160, 36096, 36161, this.depth.getName());
        }
        else if (Attachment.Type.STENCIL == type) {
            gl.glFramebufferRenderbuffer(36160, 36128, 36161, this.stencil.getName());
        }
        else if (Attachment.Type.DEPTH_STENCIL == type) {
            gl.glFramebufferRenderbuffer(36160, 36096, 36161, this.depth.getName());
            gl.glFramebufferRenderbuffer(36160, 36128, 36161, this.stencil.getName());
        }
        this.modified = true;
        if (!this.ignoreStatus) {
            this.updateStatus(gl);
            if (!this.isStatusValid()) {
                this.detachRenderbuffer(gl, type, true);
                throw new GLException("renderbuffer [attachmentType " + type + ", iformat " + toHexString(n) + "] failed: " + this.getStatusString() + ", " + this.toString());
            }
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.attachRenderbuffer.X: [attachmentType " + type + ", iformat " + toHexString(n) + "]: " + this);
        }
    }
    
    public final Colorbuffer detachColorbuffer(final GL gl, final int n, final boolean b) throws IllegalArgumentException {
        this.bind(gl);
        final Colorbuffer detachColorbufferImpl = this.detachColorbufferImpl(gl, n, b ? DetachAction.DISPOSE : DetachAction.NONE, 0);
        if (null == detachColorbufferImpl) {
            throw new IllegalArgumentException("ColorAttachment at " + n + ", not attached, " + this);
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.detachColorbuffer.X: [attachmentPoint " + n + ", dispose " + b + "]: " + detachColorbufferImpl + ", " + this);
        }
        return detachColorbufferImpl;
    }
    
    private final Colorbuffer detachColorbufferImpl(final GL gl, final int n, final DetachAction detachAction, final int n2) {
        final Colorbuffer colorbuffer = this.colorbufferAttachments[n];
        if (null == colorbuffer) {
            return null;
        }
        this.removeColorAttachment(n, colorbuffer);
        if (colorbuffer.isTextureAttachment()) {
            final TextureAttachment textureAttachment = colorbuffer.getTextureAttachment();
            if (0 != textureAttachment.getName()) {
                gl.glFramebufferTexture2D(36160, 36064 + n, 3553, 0, 0);
                gl.glBindTexture(3553, 0);
                switch (detachAction) {
                    case DISPOSE:
                    case RECREATE: {
                        textureAttachment.free(gl);
                        break;
                    }
                }
            }
            if (DetachAction.RECREATE == detachAction) {
                Colorbuffer colorAttachment;
                if (0 < n2) {
                    colorAttachment = this.createColorAttachment(hasAlpha(textureAttachment.format));
                }
                else {
                    textureAttachment.setSize(this.width, this.height);
                    colorAttachment = textureAttachment;
                }
                this.attachColorbufferImpl(gl, n, colorAttachment);
            }
        }
        else {
            final ColorAttachment colorAttachment2 = colorbuffer.getColorAttachment();
            if (0 != colorAttachment2.getName()) {
                gl.glFramebufferRenderbuffer(36160, 36064 + n, 36161, 0);
                switch (detachAction) {
                    case DISPOSE:
                    case RECREATE: {
                        colorAttachment2.free(gl);
                        break;
                    }
                }
            }
            if (DetachAction.RECREATE == detachAction) {
                Colorbuffer colorbuffer2;
                if (0 <= n2 || null == this.samplingColorSink) {
                    colorAttachment2.setSize(this.width, this.height);
                    colorAttachment2.setSamples(this.samples);
                    colorbuffer2 = colorAttachment2;
                }
                else if (this.samplingColorSink.isTextureAttachment()) {
                    final TextureAttachment textureAttachment2 = this.samplingColorSink.getTextureAttachment();
                    colorbuffer2 = createColorTextureAttachment(textureAttachment2.format, this.width, this.height, textureAttachment2.dataFormat, textureAttachment2.dataType, textureAttachment2.magFilter, textureAttachment2.minFilter, textureAttachment2.wrapS, textureAttachment2.wrapT);
                }
                else {
                    colorbuffer2 = createColorAttachment(this.samplingColorSink.getFormat(), 0, this.width, this.height);
                }
                this.attachColorbuffer(gl, n, colorbuffer2);
            }
        }
        return colorbuffer;
    }
    
    private final void freeAllColorbufferImpl(final GL gl) {
        for (int i = 0; i < this.maxColorAttachments; ++i) {
            final Colorbuffer colorbuffer = this.colorbufferAttachments[i];
            if (null == colorbuffer) {
                return;
            }
            if (colorbuffer.isTextureAttachment()) {
                final TextureAttachment textureAttachment = colorbuffer.getTextureAttachment();
                if (0 != textureAttachment.getName()) {
                    gl.glFramebufferTexture2D(36160, 36064 + i, 3553, 0, 0);
                    gl.glBindTexture(3553, 0);
                }
                textureAttachment.free(gl);
            }
            else {
                final ColorAttachment colorAttachment = colorbuffer.getColorAttachment();
                if (0 != colorAttachment.getName()) {
                    gl.glFramebufferRenderbuffer(36160, 36064 + i, 36161, 0);
                }
                colorAttachment.free(gl);
            }
        }
    }
    
    public final void detachRenderbuffer(final GL gl, final Attachment.Type type, final boolean b) throws IllegalArgumentException {
        this.bind(gl);
        if (null == this.detachRenderbufferImpl(gl, type, b ? DetachAction.DISPOSE : DetachAction.NONE)) {
            throw new IllegalArgumentException("RenderAttachment type " + type + ", not attached, " + this);
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.detachRenderbuffer.X: [attachmentType " + type + ", dispose " + b + "]: " + this);
        }
    }
    
    public final boolean isDepthStencilPackedFormat() {
        final boolean b = null != this.depth && null != this.stencil && this.depth.format == this.stencil.format;
        if (b) {
            if (this.depth.getName() != this.stencil.getName()) {
                throw new InternalError("depth/stencil packed format not sharing: depth " + this.depth + ", stencil " + this.stencil);
            }
            if (this.depth != this.stencil) {
                throw new InternalError("depth/stencil packed format not a shared reference: depth " + this.depth + ", stencil " + this.stencil);
            }
        }
        return b;
    }
    
    private final RenderAttachment detachRenderbufferImpl(final GL gl, Attachment.Type depth_STENCIL, final DetachAction detachAction) throws IllegalArgumentException {
        switch (depth_STENCIL) {
            case DEPTH_STENCIL:
            case DEPTH:
            case STENCIL: {
                if (null == this.depth && null == this.stencil) {
                    return null;
                }
                final boolean depthStencilPackedFormat = this.isDepthStencilPackedFormat();
                if (depthStencilPackedFormat) {
                    depth_STENCIL = Attachment.Type.DEPTH_STENCIL;
                }
                RenderAttachment renderAttachment = null;
                switch (depth_STENCIL) {
                    case DEPTH: {
                        renderAttachment = this.depth;
                        if (null != renderAttachment) {
                            final int format = renderAttachment.format;
                            if (0 != renderAttachment.getName()) {
                                gl.glFramebufferRenderbuffer(36160, 36096, 36161, 0);
                                switch (detachAction) {
                                    case DISPOSE:
                                    case RECREATE: {
                                        renderAttachment.free(gl);
                                        break;
                                    }
                                }
                            }
                            if (DetachAction.RECREATE == detachAction) {
                                this.attachRenderbufferImpl2(gl, depth_STENCIL, format);
                            }
                            else {
                                this.depth = null;
                            }
                            break;
                        }
                        break;
                    }
                    case STENCIL: {
                        renderAttachment = this.stencil;
                        if (null != renderAttachment) {
                            final int format2 = renderAttachment.format;
                            if (0 != renderAttachment.getName()) {
                                gl.glFramebufferRenderbuffer(36160, 36128, 36161, 0);
                                switch (detachAction) {
                                    case DISPOSE:
                                    case RECREATE: {
                                        renderAttachment.free(gl);
                                        break;
                                    }
                                }
                            }
                            if (DetachAction.RECREATE == detachAction) {
                                this.attachRenderbufferImpl2(gl, depth_STENCIL, format2);
                            }
                            else {
                                this.stencil = null;
                            }
                            break;
                        }
                        break;
                    }
                    case DEPTH_STENCIL: {
                        renderAttachment = this.depth;
                        if (null != renderAttachment) {
                            final int format3 = renderAttachment.format;
                            if (0 != renderAttachment.getName()) {
                                gl.glFramebufferRenderbuffer(36160, 36096, 36161, 0);
                                if (depthStencilPackedFormat) {
                                    gl.glFramebufferRenderbuffer(36160, 36128, 36161, 0);
                                }
                                switch (detachAction) {
                                    case DISPOSE:
                                    case RECREATE: {
                                        renderAttachment.free(gl);
                                        break;
                                    }
                                }
                            }
                            if (DetachAction.RECREATE == detachAction) {
                                this.attachRenderbufferImpl2(gl, depthStencilPackedFormat ? Attachment.Type.DEPTH_STENCIL : Attachment.Type.DEPTH, format3);
                            }
                            else {
                                this.depth = null;
                                if (depthStencilPackedFormat) {
                                    this.stencil = null;
                                }
                            }
                        }
                        if (!depthStencilPackedFormat && null != this.stencil) {
                            final int format4 = this.stencil.format;
                            if (0 != this.stencil.getName()) {
                                gl.glFramebufferRenderbuffer(36160, 36128, 36161, 0);
                                switch (detachAction) {
                                    case DISPOSE:
                                    case RECREATE: {
                                        this.stencil.free(gl);
                                        break;
                                    }
                                }
                            }
                            if (DetachAction.RECREATE == detachAction) {
                                this.attachRenderbufferImpl2(gl, Attachment.Type.STENCIL, format4);
                            }
                            else {
                                this.stencil = null;
                            }
                            break;
                        }
                        break;
                    }
                    default: {
                        throw new InternalError("XXX");
                    }
                }
                this.modified = true;
                return renderAttachment;
            }
            default: {
                throw new IllegalArgumentException("only depth/stencil types allowed, was " + depth_STENCIL + ", " + this);
            }
        }
    }
    
    private final void freeAllRenderbufferImpl(final GL gl) throws IllegalArgumentException {
        final boolean depthStencilPackedFormat = this.isDepthStencilPackedFormat();
        if (null != this.depth && 0 != this.depth.getName()) {
            gl.glFramebufferRenderbuffer(36160, 36096, 36161, 0);
            if (depthStencilPackedFormat) {
                gl.glFramebufferRenderbuffer(36160, 36128, 36161, 0);
            }
            this.depth.free(gl);
        }
        if (!depthStencilPackedFormat && null != this.stencil && 0 != this.stencil.getName()) {
            gl.glFramebufferRenderbuffer(36160, 36128, 36161, 0);
            this.stencil.free(gl);
        }
    }
    
    public final void detachAll(final GL gl) {
        if (null != this.samplingSink) {
            this.samplingSink.detachAll(gl);
        }
        this.detachAllImpl(gl, true, false, 0);
    }
    
    public final void detachAllColorbuffer(final GL gl) {
        if (null != this.samplingSink) {
            this.samplingSink.detachAllColorbuffer(gl);
        }
        this.detachAllImpl(gl, false, false, 0);
    }
    
    public final void detachAllTexturebuffer(final GL gl) {
        if (!this.isInitialized()) {
            return;
        }
        if (null != this.samplingSink) {
            this.samplingSink.detachAllTexturebuffer(gl);
        }
        this.bind(gl);
        for (int i = 0; i < this.maxColorAttachments; ++i) {
            if (this.colorbufferAttachments[i].isTextureAttachment()) {
                this.detachColorbufferImpl(gl, i, DetachAction.DISPOSE, 0);
            }
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.detachAllTexturebuffer.X: " + this);
        }
    }
    
    public final void detachAllRenderbuffer(final GL gl) {
        if (!this.isInitialized()) {
            return;
        }
        if (null != this.samplingSink) {
            this.samplingSink.detachAllRenderbuffer(gl);
        }
        this.bind(gl);
        this.detachRenderbufferImpl(gl, Attachment.Type.DEPTH_STENCIL, DetachAction.DISPOSE);
    }
    
    private final void detachAllImpl(final GL gl, final boolean b, final boolean ignoreStatus, final int n) {
        if (!this.isInitialized()) {
            return;
        }
        this.ignoreStatus = ignoreStatus;
        try {
            this.bind(gl);
            for (int i = 0; i < this.maxColorAttachments; ++i) {
                this.detachColorbufferImpl(gl, i, ignoreStatus ? DetachAction.RECREATE : DetachAction.DISPOSE, n);
            }
            if (!ignoreStatus && this.colorbufferCount > 0) {
                throw new InternalError("Non zero ColorAttachments " + this);
            }
            if (b) {
                this.detachRenderbufferImpl(gl, Attachment.Type.DEPTH_STENCIL, ignoreStatus ? DetachAction.RECREATE : DetachAction.DISPOSE);
            }
            if (this.ignoreStatus) {
                this.updateStatus(gl);
                if (!this.isStatusValid()) {
                    throw new GLException("detachAllImpl failed: " + this.getStatusString() + ", " + this);
                }
            }
        }
        finally {
            this.ignoreStatus = false;
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.detachAll.X: [resetNonColorbuffer " + b + ", recreate " + ignoreStatus + "]: " + this);
        }
    }
    
    public final void destroy(final GL gl) {
        if (!this.initialized) {
            return;
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.destroy.0: " + this);
        }
        if (null != this.samplingSink && this.samplingSink.isInitialized()) {
            this.samplingSink.destroy(gl);
        }
        this.detachAllImpl(gl, true, false, 0);
        final int fbName = this.fbName;
        this.fbName = 0;
        final int[] array = { 0 };
        if (fbName != 0) {
            array[0] = fbName;
            gl.glDeleteFramebuffers(1, array, 0);
        }
        this.initialized = false;
        this.bound = false;
        if (FBObject.DEBUG) {
            System.err.println("FBObject.destroy.X: " + this);
        }
    }
    
    private final boolean sampleSinkSizeMismatch() {
        return this.samplingSink.getWidth() != this.width || this.samplingSink.getHeight() != this.height;
    }
    
    private final boolean sampleSinkDepthStencilMismatch() {
        return (null != this.depth && (null == this.samplingSink.depth || this.depth.format != this.samplingSink.depth.format)) || (null == this.depth && null != this.samplingSink.depth) || ((null != this.stencil && (null == this.samplingSink.stencil || this.stencil.format != this.samplingSink.stencil.format)) || (null == this.stencil && null != this.samplingSink.stencil));
    }
    
    private final boolean sampleSinkExFormatMismatch(final GL gl) {
        if (null != this.samplingColorSink && this.getColorbufferCount() > 0 && gl.isGL2ES3()) {
            final Attachment attachment = (Attachment)this.getColorbuffer(0);
            return null != attachment && attachment.format != this.samplingColorSink.getFormat();
        }
        return false;
    }
    
    public final boolean resetSamplingSink(final GL gl) throws GLException {
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSamplingSink.0");
            ExceptionUtils.dumpStack(System.err);
        }
        if (0 == this.samples) {
            boolean b;
            if (null != this.samplingSink) {
                if (this.samplingSink.initialized) {
                    this.samplingSink.detachAll(gl);
                }
                this.samplingSink = null;
                this.samplingColorSink = null;
                b = true;
            }
            else {
                b = false;
            }
            this.modified = false;
            if (FBObject.DEBUG) {
                System.err.println("FBObject.resetSamplingSink.X1: zero samples, mod " + b + "\n\tTHIS " + this);
            }
            return b;
        }
        boolean b2 = false;
        if (null == this.samplingSink) {
            (this.samplingSink = new FBObject()).init(gl, this.width, this.height, 0);
            this.samplingColorSink = null;
            b2 = true;
        }
        else {
            if (!this.samplingSink.initialized) {
                throw new InternalError("InitState Mismatch: samplingSink set, but not initialized " + this.samplingSink);
            }
            if (null == this.samplingColorSink || 0 == this.samplingColorSink.getName()) {
                throw new InternalError("InitState Mismatch: samplingColorSink set, but not initialized " + this.samplingColorSink + ", " + this.samplingSink);
            }
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSamplingSink.1: mod " + b2 + "\n\tTHIS " + this + ",\n\tSINK " + this.samplingSink);
        }
        final boolean sampleSinkExFormatMismatch = this.sampleSinkExFormatMismatch(gl);
        final boolean sampleSinkSizeMismatch = this.sampleSinkSizeMismatch();
        final boolean sampleSinkDepthStencilMismatch = this.sampleSinkDepthStencilMismatch();
        if (b2) {
            if (sampleSinkExFormatMismatch || sampleSinkSizeMismatch) {
                throw new InternalError("InitState Mismatch: Matching exFormat " + !sampleSinkExFormatMismatch + ", size " + !sampleSinkSizeMismatch + ", " + this);
            }
        }
        else if (!sampleSinkExFormatMismatch && !sampleSinkSizeMismatch && !sampleSinkDepthStencilMismatch) {
            if (FBObject.DEBUG) {
                System.err.println("FBObject.resetSamplingSink.X2: Matching: exFormat " + !sampleSinkExFormatMismatch + ", size " + !sampleSinkSizeMismatch + ", depthStencil " + !sampleSinkDepthStencilMismatch + ", mod " + b2);
            }
            this.samplingSink.modified = false;
            this.modified = false;
            return b2;
        }
        boolean b3;
        if (this.isBound()) {
            this.markUnbound();
            b3 = true;
        }
        else {
            b3 = false;
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSamplingSink.2: wasBound " + b3 + ", matching: exFormat " + !sampleSinkExFormatMismatch + ", size " + !sampleSinkSizeMismatch + ", depthStencil " + !sampleSinkDepthStencilMismatch);
        }
        final boolean b4 = true;
        if (sampleSinkDepthStencilMismatch) {
            this.samplingSink.detachAllRenderbuffer(gl);
        }
        final boolean b5 = null == this.samplingColorSink || this.samplingColorSink.isTextureAttachment();
        if (sampleSinkExFormatMismatch) {
            this.samplingSink.detachAllColorbuffer(gl);
            this.samplingColorSink = null;
        }
        else if (sampleSinkSizeMismatch) {
            this.samplingSink.resetSizeImpl(gl, this.width, this.height);
            this.samplingColorSink = this.samplingSink.getColorbuffer(0);
        }
        if (null == this.samplingColorSink) {
            final Colorbuffer colorbuffer = this.getColorbuffer(0);
            if (null != colorbuffer) {
                if (b5) {
                    this.samplingColorSink = createColorTextureAttachment(gl, colorbuffer.getFormat(), this.width, this.height, 9728, 9728, 33071, 33071);
                }
                else {
                    this.samplingColorSink = createColorAttachment(colorbuffer.getFormat(), 0, this.width, this.height);
                }
                this.samplingSink.attachColorbuffer(gl, 0, this.samplingColorSink);
            }
            else {
                final boolean hasAttachmentUsingAlpha = this.hasAttachmentUsingAlpha();
                if (b5) {
                    this.samplingColorSink = this.samplingSink.attachTexture2D(gl, 0, hasAttachmentUsingAlpha);
                }
                else {
                    this.samplingColorSink = this.samplingSink.attachColorbuffer(gl, 0, hasAttachmentUsingAlpha);
                }
            }
        }
        if (sampleSinkDepthStencilMismatch) {
            this.samplingSink.attachRenderbuffer(gl, this.depth.format);
            if (null != this.stencil && !this.isDepthStencilPackedFormat()) {
                this.samplingSink.attachRenderbuffer(gl, this.stencil.format);
            }
        }
        final boolean sampleSinkExFormatMismatch2 = this.sampleSinkExFormatMismatch(gl);
        final boolean sampleSinkSizeMismatch2 = this.sampleSinkSizeMismatch();
        final boolean sampleSinkDepthStencilMismatch2 = this.sampleSinkDepthStencilMismatch();
        if (sampleSinkExFormatMismatch2 || sampleSinkSizeMismatch2 || sampleSinkDepthStencilMismatch2) {
            throw new InternalError("Samples sink mismatch after reset: \n\tTHIS " + this + ",\n\t SINK " + this.samplingSink + "\n\t Mismatch. Matching: exFormat " + !sampleSinkExFormatMismatch2 + ", size " + !sampleSinkSizeMismatch2 + ", depthStencil " + !sampleSinkDepthStencilMismatch2);
        }
        this.samplingSink.modified = false;
        this.samplingSink.unbind(gl);
        this.modified = false;
        if (b3) {
            this.bind(gl);
        }
        if (FBObject.DEBUG) {
            System.err.println("FBObject.resetSamplingSink.XX: END mod " + b4 + "\n\tTHIS " + this + ",\n\tSINK " + this.samplingSink + "\n\t Matching: exFormat " + !sampleSinkExFormatMismatch2 + ", size " + !sampleSinkSizeMismatch2 + ", depthStencil " + !sampleSinkDepthStencilMismatch2);
        }
        return b4;
    }
    
    public FBObject setSamplingSink(final FBObject samplingSink) throws IllegalStateException, GLException {
        final FBObject samplingSink2 = this.samplingSink;
        if (null == samplingSink) {
            this.samplingSink = null;
            this.samplingColorSink = null;
        }
        else {
            if (this.samples <= 0) {
                throw new GLException("Setting SamplingSink for non MSAA FBO not allowed: " + this);
            }
            if (!samplingSink.isInitialized()) {
                throw new IllegalStateException("SamplingSink not initialized: " + samplingSink);
            }
            if (samplingSink.getNumSamples() > 0) {
                throw new GLException("SamplingSink FBO cannot use MSAA itself: " + samplingSink);
            }
            this.samplingSink = samplingSink;
            this.samplingColorSink = samplingSink.getColorbuffer(0);
        }
        this.modified = true;
        this.samplingSinkDirty = true;
        return samplingSink2;
    }
    
    public final void bind(final GL gl) throws GLException {
        if (!this.bound || this.fbName != gl.getBoundFramebuffer(36160)) {
            this.checkInitialized();
            if (this.fullFBOSupport) {
                gl.glBindFramebuffer(36009, this.getWriteFramebuffer());
                gl.glBindFramebuffer(36008, this.getReadFramebuffer());
            }
            else {
                gl.glBindFramebuffer(36160, this.getWriteFramebuffer());
            }
            this.bound = true;
            this.samplingSinkDirty = true;
        }
    }
    
    public final void unbind(final GL gl) throws GLException {
        if (this.bound) {
            if (this.fullFBOSupport) {
                gl.glBindFramebuffer(36009, 0);
                gl.glBindFramebuffer(36008, 0);
            }
            else {
                gl.glBindFramebuffer(36160, 0);
            }
            this.bound = false;
        }
    }
    
    public final void markUnbound() {
        this.bound = false;
    }
    
    public final boolean isBound(final GL gl) {
        return this.bound = (this.bound && this.fbName == gl.getBoundFramebuffer(36160));
    }
    
    public final boolean isBound() {
        return this.bound;
    }
    
    public final void syncSamplingSink(final GL gl) {
        this.markUnbound();
        if (this.samples > 0 && this.samplingSinkDirty) {
            this.samplingSinkDirty = false;
            if (this.isModified()) {
                this.resetSamplingSink(gl);
            }
            final boolean b = FBObject.DEBUG || GLContext.DEBUG_GL;
            if (b) {
                checkPreGLError(gl);
            }
            gl.glBindFramebuffer(36008, this.fbName);
            gl.glBindFramebuffer(36009, this.samplingSink.getWriteFramebuffer());
            ((GL2ES3)gl).glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, this.width, this.height, 16384, 9728);
            if (b) {
                this.checkNoError(null, gl.glGetError(), "FBObject syncSampleSink");
            }
        }
        else {
            this.modified = false;
        }
        if (this.fullFBOSupport) {
            gl.glBindFramebuffer(36009, 0);
            gl.glBindFramebuffer(36008, 0);
        }
        else {
            gl.glBindFramebuffer(36160, 0);
        }
    }
    
    public final void use(final GL gl, final TextureAttachment textureAttachment) throws IllegalArgumentException {
        this.syncSamplingSink(gl);
        if (null != textureAttachment) {
            gl.glBindTexture(3553, textureAttachment.getName());
        }
    }
    
    public final void unuse(final GL gl) {
        this.unbind(gl);
        gl.glBindTexture(3553, 0);
    }
    
    public final boolean hasFullFBOSupport() throws GLException {
        this.checkInitialized();
        return this.fullFBOSupport;
    }
    
    public final boolean supportsRGBA8() throws GLException {
        this.checkInitialized();
        return this.rgba8Avail;
    }
    
    public final boolean supportsDepth(final int n) throws GLException {
        this.checkInitialized();
        switch (n) {
            case 16: {
                return true;
            }
            case 24: {
                return this.depth24Avail;
            }
            case 32: {
                return this.depth32Avail;
            }
            default: {
                return false;
            }
        }
    }
    
    public final boolean supportsStencil(final int n) throws GLException {
        this.checkInitialized();
        switch (n) {
            case 1: {
                return this.stencil01Avail;
            }
            case 4: {
                return this.stencil04Avail;
            }
            case 8: {
                return this.stencil08Avail;
            }
            case 16: {
                return this.stencil16Avail;
            }
            default: {
                return false;
            }
        }
    }
    
    public final boolean supportsPackedDepthStencil() throws GLException {
        this.checkInitialized();
        return this.packedDepthStencilAvail;
    }
    
    public final int getMaxColorAttachments() throws GLException {
        this.checkInitialized();
        return this.maxColorAttachments;
    }
    
    public final int getMaxTextureSize() throws GLException {
        this.checkInitialized();
        return this.maxTextureSize;
    }
    
    public final int getMaxRenderbufferSize() throws GLException {
        this.checkInitialized();
        return this.maxRenderbufferSize;
    }
    
    public final int getMaxSamples() throws GLException {
        this.checkInitialized();
        return this.maxSamples;
    }
    
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    public final int getWidth() {
        return this.width;
    }
    
    public final int getHeight() {
        return this.height;
    }
    
    public final int getNumSamples() {
        return this.samples;
    }
    
    public final int getWriteFramebuffer() {
        return this.fbName;
    }
    
    public final int getReadFramebuffer() {
        return (0 < this.samples) ? ((null != this.samplingSink) ? this.samplingSink.getReadFramebuffer() : 0) : this.fbName;
    }
    
    public final int getDefaultReadBuffer() {
        return 36064;
    }
    
    public final int getColorbufferCount() {
        return this.colorbufferCount;
    }
    
    public final int getTextureAttachmentCount() {
        return this.textureAttachmentCount;
    }
    
    public final RenderAttachment getStencilAttachment() {
        return this.stencil;
    }
    
    public final RenderAttachment getDepthAttachment() {
        return this.depth;
    }
    
    public final FBObject getSamplingSinkFBO() {
        return this.samplingSink;
    }
    
    public final Colorbuffer getSamplingSink() {
        return this.samplingColorSink;
    }
    
    public final boolean isSamplingBufferDirty() {
        return this.samplingSinkDirty;
    }
    
    public final boolean isModified() {
        return this.modified || (null != this.samplingSink && this.samplingSink.modified);
    }
    
    int objectHashCode() {
        return super.hashCode();
    }
    
    @Override
    public final String toString() {
        return "FBO[name r/w " + this.fbName + "/" + this.getReadFramebuffer() + ", init " + this.initialized + ", bound " + this.bound + ", size " + this.width + "x" + this.height + ", samples " + this.samples + "/" + this.maxSamples + ", modified " + this.modified + "/" + this.isModified() + ", depth " + this.depth + ", stencil " + this.stencil + ", colorbuffer attachments: " + this.colorbufferCount + "/" + this.maxColorAttachments + ", with " + this.textureAttachmentCount + " textures" + ": " + ((null != this.colorbufferAttachments) ? Arrays.asList(this.colorbufferAttachments).toString() : null) + ", msaa[" + this.samplingColorSink + ", hasSink " + (null != this.samplingSink) + ", dirty " + this.samplingSinkDirty + "], state " + this.getStatusString() + ", obj " + toHexString(this.objectHashCode()) + "]";
    }
    
    private final void updateStatus(final GL gl) {
        if (0 == this.fbName) {
            this.vStatus = -1;
        }
        else {
            this.vStatus = gl.glCheckFramebufferStatus(36160);
        }
    }
    
    static {
        Debug.initSingleton();
        DEBUG = Debug.debug("FBObject");
        USER_MAX_TEXTURE_SIZE = PropertyAccess.getIntProperty("jogl.debug.FBObject.MaxTextureSize", true, 0);
    }
    
    private enum DetachAction
    {
        NONE, 
        DISPOSE, 
        RECREATE;
    }
    
    public abstract static class Attachment
    {
        public final Type type;
        public final int format;
        private int width;
        private int height;
        private int name;
        
        protected Attachment(final Type type, final int format, final int width, final int height, final int name) {
            this.type = type;
            this.format = format;
            this.width = width;
            this.height = height;
            this.name = name;
        }
        
        public final void formatToGLCapabilities(final GLCapabilities glCapabilities, final boolean b) {
            int format = 0;
            switch (this.format) {
                case 4:
                case 6408: {
                    format = (b ? 32856 : 32854);
                    break;
                }
                case 3:
                case 6407: {
                    format = (b ? 32849 : 36194);
                    break;
                }
                default: {
                    format = this.format;
                    break;
                }
            }
            switch (format) {
                case 32854: {
                    glCapabilities.setRedBits(4);
                    glCapabilities.setGreenBits(4);
                    glCapabilities.setBlueBits(4);
                    glCapabilities.setAlphaBits(4);
                    break;
                }
                case 32855: {
                    glCapabilities.setRedBits(5);
                    glCapabilities.setGreenBits(5);
                    glCapabilities.setBlueBits(5);
                    glCapabilities.setAlphaBits(1);
                    break;
                }
                case 36194: {
                    glCapabilities.setRedBits(5);
                    glCapabilities.setGreenBits(6);
                    glCapabilities.setBlueBits(5);
                    glCapabilities.setAlphaBits(0);
                    break;
                }
                case 32849: {
                    glCapabilities.setRedBits(8);
                    glCapabilities.setGreenBits(8);
                    glCapabilities.setBlueBits(8);
                    glCapabilities.setAlphaBits(0);
                    break;
                }
                case 32856: {
                    glCapabilities.setRedBits(8);
                    glCapabilities.setGreenBits(8);
                    glCapabilities.setBlueBits(8);
                    glCapabilities.setAlphaBits(8);
                    break;
                }
                case 33189: {
                    glCapabilities.setDepthBits(16);
                    break;
                }
                case 33190: {
                    glCapabilities.setDepthBits(24);
                    break;
                }
                case 33191: {
                    glCapabilities.setDepthBits(32);
                    break;
                }
                case 36166: {
                    glCapabilities.setStencilBits(1);
                    break;
                }
                case 36167: {
                    glCapabilities.setStencilBits(4);
                    break;
                }
                case 36168: {
                    glCapabilities.setStencilBits(8);
                    break;
                }
                case 35056: {
                    glCapabilities.setDepthBits(24);
                    glCapabilities.setStencilBits(8);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("format invalid: " + FBObject.toHexString(this.format));
                }
            }
        }
        
        public final int getFormat() {
            return this.format;
        }
        
        public final int getWidth() {
            return this.width;
        }
        
        public final int getHeight() {
            return this.height;
        }
        
        final void setSize(final int width, final int height) {
            this.width = width;
            this.height = height;
        }
        
        public final int getName() {
            return this.name;
        }
        
        final void setName(final int name) {
            this.name = name;
        }
        
        public abstract boolean initialize(final GL p0) throws GLException;
        
        public abstract void free(final GL p0) throws GLException;
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Attachment)) {
                return false;
            }
            final Attachment attachment = (Attachment)o;
            return this.type == attachment.type && this.format == attachment.format && this.width == attachment.width && this.height == attachment.height && this.name == attachment.name;
        }
        
        @Override
        public int hashCode() {
            final int n = 31 + this.type.ordinal();
            final int n2 = (n << 5) - n + this.format;
            final int n3 = (n2 << 5) - n2 + this.width;
            final int n4 = (n3 << 5) - n3 + this.height;
            return (n4 << 5) - n4 + this.name;
        }
        
        int objectHashCode() {
            return super.hashCode();
        }
        
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "[type " + this.type + ", format " + FBObject.toHexString(this.format) + ", " + this.width + "x" + this.height + "; name " + FBObject.toHexString(this.name) + ", obj " + FBObject.toHexString(this.objectHashCode()) + "]";
        }
        
        public static Type getType(final int n, final int n2) {
            if (36064 <= n && n < 36064 + n2) {
                return Type.COLOR;
            }
            switch (n) {
                case 36096: {
                    return Type.DEPTH;
                }
                case 36128: {
                    return Type.STENCIL;
                }
                default: {
                    throw new IllegalArgumentException("Invalid attachment point " + FBObject.toHexString(n));
                }
            }
        }
        
        public enum Type
        {
            NONE, 
            DEPTH, 
            STENCIL, 
            DEPTH_STENCIL, 
            COLOR, 
            COLOR_TEXTURE, 
            DEPTH_TEXTURE, 
            STENCIL_TEXTURE;
            
            public static Type determine(final int n) throws IllegalArgumentException {
                switch (n) {
                    case 32849:
                    case 32854:
                    case 32855:
                    case 32856:
                    case 36194: {
                        return Type.COLOR;
                    }
                    case 33189:
                    case 33190:
                    case 33191: {
                        return Type.DEPTH;
                    }
                    case 36166:
                    case 36167:
                    case 36168: {
                        return Type.STENCIL;
                    }
                    case 35056: {
                        return Type.DEPTH_STENCIL;
                    }
                    default: {
                        throw new IllegalArgumentException("format invalid: " + FBObject.toHexString(n));
                    }
                }
            }
        }
    }
    
    public static class RenderAttachment extends Attachment
    {
        private int samples;
        
        public RenderAttachment(final Type type, final int n, final int samples, final int n2, final int n3, final int n4) {
            super(validateType(type), n, n2, n3, n4);
            this.samples = samples;
        }
        
        public final int getSamples() {
            return this.samples;
        }
        
        final void setSamples(final int samples) {
            this.samples = samples;
        }
        
        private static Type validateType(final Type type) {
            switch (type) {
                case DEPTH_STENCIL:
                case DEPTH:
                case STENCIL:
                case COLOR: {
                    return type;
                }
                default: {
                    throw new IllegalArgumentException("Invalid type: " + type);
                }
            }
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof RenderAttachment && super.equals(o) && this.samples == ((RenderAttachment)o).samples);
        }
        
        @Override
        public int hashCode() {
            final int hashCode = super.hashCode();
            return (hashCode << 5) - hashCode + this.samples;
        }
        
        @Override
        public boolean initialize(final GL gl) throws GLException {
            final boolean b = 0 == this.getName();
            if (b) {
                final boolean b2 = FBObject.DEBUG || GLContext.DEBUG_GL;
                if (b2) {
                    checkPreGLError(gl);
                }
                final int[] array = { -1 };
                gl.glGenRenderbuffers(1, array, 0);
                this.setName(array[0]);
                gl.glBindRenderbuffer(36161, this.getName());
                if (this.samples > 0) {
                    ((GL2ES3)gl).glRenderbufferStorageMultisample(36161, this.samples, this.format, this.getWidth(), this.getHeight());
                }
                else {
                    gl.glRenderbufferStorage(36161, this.format, this.getWidth(), this.getHeight());
                }
                if (b2) {
                    final int glGetError = gl.glGetError();
                    if (glGetError != 0) {
                        gl.glDeleteRenderbuffers(1, array, 0);
                        this.setName(0);
                        throw new GLException("GL Error " + FBObject.toHexString(glGetError) + " while creating " + this);
                    }
                }
                if (FBObject.DEBUG) {
                    System.err.println("Attachment.init.X: " + this);
                }
            }
            return b;
        }
        
        @Override
        public void free(final GL gl) {
            final int[] array = { this.getName() };
            if (0 != array[0]) {
                if (FBObject.DEBUG) {
                    System.err.println("Attachment.free.0: " + this);
                }
                gl.glDeleteRenderbuffers(1, array, 0);
                this.setName(0);
            }
        }
        
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "[type " + this.type + ", format " + FBObject.toHexString(this.format) + ", samples " + this.samples + ", " + this.getWidth() + "x" + this.getHeight() + ", name " + FBObject.toHexString(this.getName()) + ", obj " + FBObject.toHexString(this.objectHashCode()) + "]";
        }
    }
    
    public static class ColorAttachment extends RenderAttachment implements Colorbuffer
    {
        public ColorAttachment(final int n, final int n2, final int n3, final int n4, final int n5) {
            super(Type.COLOR, n, n2, n3, n4, n5);
        }
        
        @Override
        public final boolean isTextureAttachment() {
            return false;
        }
        
        @Override
        public final TextureAttachment getTextureAttachment() {
            throw new GLException("Not a TextureAttachment, but ColorAttachment");
        }
        
        @Override
        public final ColorAttachment getColorAttachment() {
            return this;
        }
    }
    
    public static class TextureAttachment extends Attachment implements Colorbuffer
    {
        public final int dataFormat;
        public final int dataType;
        public final int magFilter;
        public final int minFilter;
        public final int wrapS;
        public final int wrapT;
        
        public TextureAttachment(final Type type, final int n, final int n2, final int n3, final int dataFormat, final int dataType, final int magFilter, final int minFilter, final int wrapS, final int wrapT, final int n4) {
            super(validateType(type), n, n2, n3, n4);
            this.dataFormat = dataFormat;
            this.dataType = dataType;
            this.magFilter = magFilter;
            this.minFilter = minFilter;
            this.wrapS = wrapS;
            this.wrapT = wrapT;
        }
        
        private static Type validateType(final Type type) {
            switch (type) {
                case COLOR_TEXTURE:
                case DEPTH_TEXTURE:
                case STENCIL_TEXTURE: {
                    return type;
                }
                default: {
                    throw new IllegalArgumentException("Invalid type: " + type);
                }
            }
        }
        
        @Override
        public boolean initialize(final GL gl) throws GLException {
            final boolean b = 0 == this.getName();
            if (b) {
                final boolean b2 = FBObject.DEBUG || GLContext.DEBUG_GL;
                if (b2) {
                    checkPreGLError(gl);
                }
                final int[] array = { -1 };
                gl.glGenTextures(1, array, 0);
                if (0 == array[0]) {
                    throw new GLException("null texture, " + this);
                }
                this.setName(array[0]);
                gl.glBindTexture(3553, array[0]);
                if (0 < this.magFilter) {
                    gl.glTexParameteri(3553, 10240, this.magFilter);
                }
                if (0 < this.minFilter) {
                    gl.glTexParameteri(3553, 10241, this.minFilter);
                }
                if (0 < this.wrapS) {
                    gl.glTexParameteri(3553, 10242, this.wrapS);
                }
                if (0 < this.wrapT) {
                    gl.glTexParameteri(3553, 10243, this.wrapT);
                }
                if (b2) {
                    boolean b3 = true;
                    int n = gl.glGetError();
                    if (n == 0) {
                        b3 = false;
                        gl.glTexImage2D(3553, 0, this.format, this.getWidth(), this.getHeight(), 0, this.dataFormat, this.dataType, null);
                        n = gl.glGetError();
                    }
                    if (n != 0) {
                        gl.glDeleteTextures(1, array, 0);
                        this.setName(0);
                        throw new GLException("GL Error " + FBObject.toHexString(n) + " while creating (pre TexImage2D " + b3 + ") " + this);
                    }
                }
                else {
                    gl.glTexImage2D(3553, 0, this.format, this.getWidth(), this.getHeight(), 0, this.dataFormat, this.dataType, null);
                }
                if (FBObject.DEBUG) {
                    System.err.println("Attachment.init.X: " + this);
                }
            }
            return b;
        }
        
        @Override
        public void free(final GL gl) {
            final int[] array = { this.getName() };
            if (0 != array[0]) {
                if (FBObject.DEBUG) {
                    System.err.println("Attachment.free.0: " + this);
                }
                gl.glDeleteTextures(1, array, 0);
                this.setName(0);
            }
        }
        
        @Override
        public final boolean isTextureAttachment() {
            return true;
        }
        
        @Override
        public final TextureAttachment getTextureAttachment() {
            return this;
        }
        
        @Override
        public final ColorAttachment getColorAttachment() {
            throw new GLException("Not a ColorAttachment, but TextureAttachment");
        }
        
        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "[type " + this.type + ", target GL_TEXTURE_2D, level 0, format " + FBObject.toHexString(this.format) + ", " + this.getWidth() + "x" + this.getHeight() + ", border 0, dataFormat " + FBObject.toHexString(this.dataFormat) + ", dataType " + FBObject.toHexString(this.dataType) + "; min/mag " + FBObject.toHexString(this.minFilter) + "/" + FBObject.toHexString(this.magFilter) + ", wrap S/T " + FBObject.toHexString(this.wrapS) + "/" + FBObject.toHexString(this.wrapT) + "; name " + FBObject.toHexString(this.getName()) + ", obj " + FBObject.toHexString(this.objectHashCode()) + "]";
        }
    }
    
    public interface Colorbuffer
    {
        boolean initialize(final GL p0) throws GLException;
        
        void free(final GL p0) throws GLException;
        
        void formatToGLCapabilities(final GLCapabilities p0, final boolean p1);
        
        boolean isTextureAttachment();
        
        TextureAttachment getTextureAttachment();
        
        ColorAttachment getColorAttachment();
        
        int getFormat();
        
        int getWidth();
        
        int getHeight();
        
        int getName();
    }
}
