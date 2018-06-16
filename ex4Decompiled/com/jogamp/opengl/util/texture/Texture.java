// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.nativewindow.NativeWindowFactory;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import com.jogamp.opengl.util.texture.spi.DDSImage;
import java.nio.Buffer;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL;

public class Texture
{
    private int target;
    private int imageTarget;
    private int texID;
    private int texWidth;
    private int texHeight;
    private int imgWidth;
    private int imgHeight;
    private float aspectRatio;
    private boolean mustFlipVertically;
    private boolean usingAutoMipmapGeneration;
    private TextureCoords coords;
    private int estimatedMemorySize;
    private static final boolean DEBUG;
    private static final boolean VERBOSE;
    private static final boolean disableNPOT;
    private static final boolean disableTexRect;
    
    @Override
    public String toString() {
        return "Texture[target " + ((this.target == this.imageTarget) ? Integer.toHexString(this.target) : (Integer.toHexString(this.target) + " - image " + Integer.toHexString(this.imageTarget))) + ", name " + this.texID + ", " + this.imgWidth + "/" + this.texWidth + " x " + this.imgHeight + "/" + this.texHeight + ", y-flip " + this.mustFlipVertically + ", " + this.estimatedMemorySize + " bytes]";
    }
    
    public Texture(final GL gl, final TextureData textureData) throws GLException {
        this.texID = 0;
        this.target = 0;
        this.imageTarget = 0;
        this.updateImage(gl, textureData);
    }
    
    public Texture(final int n) {
        this.texID = 0;
        this.target = n;
        this.imageTarget = n;
    }
    
    public Texture(final int texID, final int n, final int texWidth, final int texHeight, final int imgWidth, final int imgHeight, final boolean mustFlipVertically) {
        this.texID = texID;
        this.target = n;
        this.imageTarget = n;
        this.mustFlipVertically = mustFlipVertically;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.aspectRatio = imgWidth / imgHeight;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.updateTexCoords();
    }
    
    public void enable(final GL gl) throws GLException {
        if (!gl.isGLcore() && 36197 != this.target) {
            gl.glEnable(this.target);
        }
    }
    
    public void disable(final GL gl) throws GLException {
        if (!gl.isGLcore() && 36197 != this.target) {
            gl.glDisable(this.target);
        }
    }
    
    public void bind(final GL gl) throws GLException {
        this.validateTexID(gl, true);
        gl.glBindTexture(this.target, this.texID);
    }
    
    public void destroy(final GL gl) throws GLException {
        if (0 != this.texID) {
            gl.glDeleteTextures(1, new int[] { this.texID }, 0);
            this.texID = 0;
        }
    }
    
    public int getTarget() {
        return this.target;
    }
    
    public int getImageTarget() {
        return this.imageTarget;
    }
    
    public int getWidth() {
        return this.texWidth;
    }
    
    public int getHeight() {
        return this.texHeight;
    }
    
    public int getImageWidth() {
        return this.imgWidth;
    }
    
    public int getImageHeight() {
        return this.imgHeight;
    }
    
    public float getAspectRatio() {
        return this.aspectRatio;
    }
    
    public TextureCoords getImageTexCoords() {
        return this.coords;
    }
    
    public TextureCoords getSubImageTexCoords(final int n, final int n2, final int n3, final int n4) {
        if (34037 == this.imageTarget) {
            if (this.mustFlipVertically) {
                return new TextureCoords(n, this.texHeight - n2, n3, this.texHeight - n4);
            }
            return new TextureCoords(n, n2, n3, n4);
        }
        else {
            final float n5 = n / this.texWidth;
            final float n6 = n2 / this.texHeight;
            final float n7 = n3 / this.texWidth;
            final float n8 = n4 / this.texHeight;
            if (this.mustFlipVertically) {
                final float n9 = this.imgHeight / this.texHeight;
                return new TextureCoords(n5, n9 - n6, n7, n9 - n8);
            }
            return new TextureCoords(n5, n6, n7, n8);
        }
    }
    
    public void updateImage(final GL gl, final TextureData textureData) throws GLException {
        this.updateImage(gl, textureData, 0);
    }
    
    public boolean getMustFlipVertically() {
        return this.mustFlipVertically;
    }
    
    public void setMustFlipVertically(final boolean mustFlipVertically) {
        if (mustFlipVertically != this.mustFlipVertically) {
            this.mustFlipVertically = mustFlipVertically;
            this.updateTexCoords();
        }
    }
    
    public void updateImage(final GL gl, final TextureData textureData, final int n) throws GLException {
        this.validateTexID(gl, true);
        this.imgWidth = textureData.getWidth();
        this.imgHeight = textureData.getHeight();
        this.aspectRatio = this.imgWidth / this.imgHeight;
        this.mustFlipVertically = textureData.getMustFlipVertically();
        int n2 = 0;
        final int target = this.target;
        boolean b = gl.isExtensionAvailable("GL_VERSION_1_4") || gl.isExtensionAvailable("GL_SGIS_generate_mipmap");
        textureData.setHaveEXTABGR(gl.isExtensionAvailable("GL_EXT_abgr"));
        textureData.setHaveGL12(gl.isExtensionAvailable("GL_VERSION_1_2"));
        final boolean b2 = isPowerOfTwo(this.imgWidth) && isPowerOfTwo(this.imgHeight);
        if (!b2 && !haveNPOT(gl)) {
            b = false;
        }
        boolean b3 = false;
        int n3 = 0;
        if (textureData.getMipmap() && !b) {
            this.imgWidth = nextPowerOfTwo(this.imgWidth);
            this.imgHeight = nextPowerOfTwo(this.imgHeight);
            this.texWidth = this.imgWidth;
            this.texHeight = this.imgHeight;
            n2 = 3553;
            n3 = 1;
        }
        if (n3 == 0 && preferTexRect(gl) && !b2 && haveTexRect(gl) && !textureData.isDataCompressed() && !gl.isGL3() && !gl.isGLES()) {
            if (Texture.DEBUG) {
                System.err.println("Using GL_ARB_texture_rectangle preferentially on this hardware");
            }
            this.texWidth = this.imgWidth;
            this.texHeight = this.imgHeight;
            n2 = 34037;
            n3 = 1;
        }
        if (n3 == 0 && (b2 || haveNPOT(gl))) {
            if (Texture.DEBUG) {
                if (b2) {
                    System.err.println("Power-of-two texture");
                }
                else {
                    System.err.println("Using GL_ARB_texture_non_power_of_two");
                }
            }
            this.texWidth = this.imgWidth;
            this.texHeight = this.imgHeight;
            n2 = 3553;
            n3 = 1;
        }
        if (n3 == 0 && haveTexRect(gl) && !textureData.isDataCompressed() && !gl.isGL3() && !gl.isGLES()) {
            if (Texture.DEBUG) {
                System.err.println("Using GL_ARB_texture_rectangle");
            }
            this.texWidth = this.imgWidth;
            this.texHeight = this.imgHeight;
            n2 = 34037;
            n3 = 1;
        }
        if (n3 == 0) {
            if (textureData.isDataCompressed()) {
                if (textureData.getMipmapData() != null) {
                    throw new GLException("Mipmapped non-power-of-two compressed textures only supported on OpenGL 2.0 hardware (GL_ARB_texture_non_power_of_two)");
                }
                b3 = true;
            }
            if (Texture.DEBUG) {
                System.err.println("Expanding texture to power-of-two dimensions");
            }
            if (textureData.getBorder() != 0) {
                throw new RuntimeException("Scaling up a non-power-of-two texture which has a border won't work");
            }
            this.texWidth = nextPowerOfTwo(this.imgWidth);
            this.texHeight = nextPowerOfTwo(this.imgHeight);
            n2 = 3553;
        }
        int target2 = n2;
        this.imageTarget = n2;
        this.updateTexCoords();
        if (n != 0) {
            if (this.target == 0) {
                throw new GLException("Override of target failed; no target specified yet");
            }
            n2 = n;
            target2 = this.target;
            gl.glBindTexture(target2, this.texID);
        }
        else {
            gl.glBindTexture(n2, this.texID);
        }
        if (textureData.getMipmap() && !b) {
            final int[] array = { 0 };
            gl.glGetIntegerv(3317, array, 0);
            gl.glPixelStorei(3317, textureData.getAlignment());
            if (textureData.isDataCompressed()) {
                throw new GLException("May not request mipmap generation for compressed textures");
            }
            try {
                GLU.createGLU(gl).gluBuild2DMipmaps(n2, textureData.getInternalFormat(), textureData.getWidth(), textureData.getHeight(), textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());
            }
            finally {
                gl.glPixelStorei(3317, array[0]);
            }
        }
        else {
            this.checkCompressedTextureExtensions(gl, textureData);
            final Buffer[] mipmapData = textureData.getMipmapData();
            if (mipmapData != null) {
                int n4 = this.texWidth;
                int n5 = this.texHeight;
                for (int i = 0; i < mipmapData.length; ++i) {
                    if (textureData.isDataCompressed()) {
                        gl.glCompressedTexImage2D(n2, i, textureData.getInternalFormat(), n4, n5, textureData.getBorder(), mipmapData[i].remaining(), mipmapData[i]);
                    }
                    else {
                        gl.glTexImage2D(n2, i, textureData.getInternalFormat(), n4, n5, textureData.getBorder(), textureData.getPixelFormat(), textureData.getPixelType(), null);
                        this.updateSubImageImpl(gl, textureData, n2, i, 0, 0, 0, 0, textureData.getWidth(), textureData.getHeight());
                    }
                    n4 = Math.max(n4 / 2, 1);
                    n5 = Math.max(n5 / 2, 1);
                }
            }
            else if (textureData.isDataCompressed()) {
                if (!b3) {
                    gl.glCompressedTexImage2D(n2, 0, textureData.getInternalFormat(), this.texWidth, this.texHeight, textureData.getBorder(), textureData.getBuffer().capacity(), textureData.getBuffer());
                }
                else {
                    final ByteBuffer allocateBlankBuffer = DDSImage.allocateBlankBuffer(this.texWidth, this.texHeight, textureData.getInternalFormat());
                    gl.glCompressedTexImage2D(n2, 0, textureData.getInternalFormat(), this.texWidth, this.texHeight, textureData.getBorder(), allocateBlankBuffer.capacity(), allocateBlankBuffer);
                    this.updateSubImageImpl(gl, textureData, n2, 0, 0, 0, 0, 0, textureData.getWidth(), textureData.getHeight());
                }
            }
            else {
                if (textureData.getMipmap() && b && gl.isGL2ES1()) {
                    gl.glTexParameteri(target2, 33169, 1);
                    this.usingAutoMipmapGeneration = true;
                }
                gl.glTexImage2D(n2, 0, textureData.getInternalFormat(), this.texWidth, this.texHeight, textureData.getBorder(), textureData.getPixelFormat(), textureData.getPixelType(), null);
                this.updateSubImageImpl(gl, textureData, n2, 0, 0, 0, 0, 0, textureData.getWidth(), textureData.getHeight());
            }
        }
        final int n6 = textureData.getMipmap() ? 9987 : 9729;
        final int n7 = (gl.isExtensionAvailable("GL_VERSION_1_2") || !gl.isGL2()) ? 33071 : 10496;
        if (n2 != 34037) {
            gl.glTexParameteri(target2, 10241, n6);
            gl.glTexParameteri(target2, 10240, 9729);
            gl.glTexParameteri(target2, 10242, n7);
            gl.glTexParameteri(target2, 10243, n7);
            if (this.target == 34067) {
                gl.glTexParameteri(target2, 32882, n7);
            }
        }
        if (this.target == 0 || this.target == 3553 || this.target == 34037) {
            this.target = n2;
        }
        this.estimatedMemorySize = textureData.getEstimatedMemorySize();
    }
    
    public void updateSubImage(final GL gl, final TextureData textureData, final int n, final int n2, final int n3) throws GLException {
        if (this.usingAutoMipmapGeneration && n != 0) {
            return;
        }
        this.bind(gl);
        this.updateSubImageImpl(gl, textureData, this.target, n, n2, n3, 0, 0, textureData.getWidth(), textureData.getHeight());
    }
    
    public void updateSubImage(final GL gl, final TextureData textureData, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) throws GLException {
        if (textureData.isDataCompressed()) {
            throw new GLException("updateSubImage specifying a sub-rectangle is not supported for compressed TextureData");
        }
        if (this.usingAutoMipmapGeneration && n != 0) {
            return;
        }
        this.bind(gl);
        this.updateSubImageImpl(gl, textureData, this.target, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void setTexParameterf(final GL gl, final int n, final float n2) {
        this.bind(gl);
        gl.glTexParameterf(this.target, n, n2);
    }
    
    public void setTexParameterfv(final GL gl, final int n, final FloatBuffer floatBuffer) {
        this.bind(gl);
        gl.glTexParameterfv(this.target, n, floatBuffer);
    }
    
    public void setTexParameterfv(final GL gl, final int n, final float[] array, final int n2) {
        this.bind(gl);
        gl.glTexParameterfv(this.target, n, array, n2);
    }
    
    public void setTexParameteri(final GL gl, final int n, final int n2) {
        this.bind(gl);
        gl.glTexParameteri(this.target, n, n2);
    }
    
    public void setTexParameteriv(final GL gl, final int n, final IntBuffer intBuffer) {
        this.bind(gl);
        gl.glTexParameteriv(this.target, n, intBuffer);
    }
    
    public void setTexParameteriv(final GL gl, final int n, final int[] array, final int n2) {
        this.bind(gl);
        gl.glTexParameteriv(this.target, n, array, n2);
    }
    
    public int getTextureObject(final GL gl) {
        this.validateTexID(gl, false);
        return this.texID;
    }
    
    public int getTextureObject() {
        return this.texID;
    }
    
    public int getEstimatedMemorySize() {
        return this.estimatedMemorySize;
    }
    
    public boolean isUsingAutoMipmapGeneration() {
        return this.usingAutoMipmapGeneration;
    }
    
    private static boolean isPowerOfTwo(final int n) {
        return (n & n - 1) == 0x0;
    }
    
    private static int nextPowerOfTwo(final int n) {
        int i;
        for (i = 1; i < n; i <<= 1) {}
        return i;
    }
    
    private void updateTexCoords() {
        if (34037 == this.imageTarget) {
            if (this.mustFlipVertically) {
                this.coords = new TextureCoords(0.0f, this.imgHeight, this.imgWidth, 0.0f);
            }
            else {
                this.coords = new TextureCoords(0.0f, 0.0f, this.imgWidth, this.imgHeight);
            }
        }
        else if (this.mustFlipVertically) {
            this.coords = new TextureCoords(0.0f, this.imgHeight / this.texHeight, this.imgWidth / this.texWidth, 0.0f);
        }
        else {
            this.coords = new TextureCoords(0.0f, 0.0f, this.imgWidth / this.texWidth, this.imgHeight / this.texHeight);
        }
    }
    
    private void updateSubImageImpl(final GL gl, final TextureData textureData, final int n, final int n2, int n3, int n4, int n5, int n6, int max, int max2) throws GLException {
        textureData.setHaveEXTABGR(gl.isExtensionAvailable("GL_EXT_abgr"));
        textureData.setHaveGL12(gl.isExtensionAvailable("GL_VERSION_1_2"));
        Buffer buffer = textureData.getBuffer();
        if (buffer == null && textureData.getMipmapData() == null) {
            return;
        }
        int rowLength = textureData.getRowLength();
        int n7 = textureData.getWidth();
        int n8 = textureData.getHeight();
        if (textureData.getMipmapData() != null) {
            for (int i = 0; i < n2; ++i) {
                max = Math.max(max / 2, 1);
                max2 = Math.max(max2 / 2, 1);
                n7 = Math.max(n7 / 2, 1);
                n8 = Math.max(n8 / 2, 1);
            }
            rowLength = 0;
            buffer = textureData.getMipmapData()[n2];
        }
        if (n5 < 0) {
            max += n5;
            n5 = 0;
        }
        if (n6 < 0) {
            max2 += n6;
            n6 = 0;
        }
        if (n3 < 0) {
            max += n3;
            n3 = 0;
        }
        if (n4 < 0) {
            max2 += n4;
            n4 = 0;
        }
        if (n5 + max > n7) {
            max = n7 - n5;
        }
        if (n6 + max2 > n8) {
            max2 = n8 - n6;
        }
        if (n3 + max > this.texWidth) {
            max = this.texWidth - n3;
        }
        if (n4 + max2 > this.texHeight) {
            max2 = this.texHeight - n4;
        }
        this.checkCompressedTextureExtensions(gl, textureData);
        if (textureData.isDataCompressed()) {
            gl.glCompressedTexSubImage2D(n, n2, n3, n4, max, max2, textureData.getInternalFormat(), buffer.remaining(), buffer);
        }
        else {
            final int[] array = { 0 };
            final int[] array2 = { 0 };
            final int[] array3 = { 0 };
            final int[] array4 = { 0 };
            gl.glGetIntegerv(3317, array, 0);
            if (gl.isGL2GL3()) {
                gl.glGetIntegerv(3314, array2, 0);
                gl.glGetIntegerv(3315, array3, 0);
                gl.glGetIntegerv(3316, array4, 0);
            }
            gl.glPixelStorei(3317, textureData.getAlignment());
            if (Texture.DEBUG && Texture.VERBOSE) {
                System.out.println("Row length  = " + rowLength);
                System.out.println("skip pixels = " + n5);
                System.out.println("skip rows   = " + n6);
                System.out.println("dstx        = " + n3);
                System.out.println("dsty        = " + n4);
                System.out.println("width       = " + max);
                System.out.println("height      = " + max2);
            }
            if (gl.isGL2GL3()) {
                gl.glPixelStorei(3314, rowLength);
                gl.glPixelStorei(3315, n6);
                gl.glPixelStorei(3316, n5);
            }
            else if (rowLength != 0 && rowLength != max && n6 != 0 && n5 != 0) {
                throw new GLException("rowlen and/or x/y offset only available for GL2");
            }
            gl.glTexSubImage2D(n, n2, n3, n4, max, max2, textureData.getPixelFormat(), textureData.getPixelType(), buffer);
            gl.glPixelStorei(3317, array[0]);
            if (gl.isGL2GL3()) {
                gl.glPixelStorei(3314, array2[0]);
                gl.glPixelStorei(3315, array3[0]);
                gl.glPixelStorei(3316, array4[0]);
            }
        }
    }
    
    private void checkCompressedTextureExtensions(final GL gl, final TextureData textureData) {
        if (textureData.isDataCompressed()) {
            switch (textureData.getInternalFormat()) {
                case 33776:
                case 33777:
                case 33778:
                case 33779: {
                    if (!gl.isExtensionAvailable("GL_EXT_texture_compression_s3tc") && !gl.isExtensionAvailable("GL_NV_texture_compression_vtc")) {
                        throw new GLException("DXTn compressed textures not supported by this graphics card");
                    }
                    break;
                }
            }
        }
    }
    
    private boolean validateTexID(final GL gl, final boolean b) {
        if (0 == this.texID) {
            if (null != gl) {
                final int[] array = { 0 };
                gl.glGenTextures(1, array, 0);
                this.texID = array[0];
                if (0 == this.texID && b) {
                    throw new GLException("Create texture ID invalid: texID " + this.texID + ", glerr 0x" + Integer.toHexString(gl.glGetError()));
                }
            }
            else if (b) {
                throw new GLException("No GL context given, can't create texture ID");
            }
        }
        return 0 != this.texID;
    }
    
    private static boolean haveNPOT(final GL gl) {
        return !Texture.disableNPOT && gl.isNPOTTextureAvailable();
    }
    
    private static boolean haveTexRect(final GL gl) {
        return !Texture.disableTexRect && TextureIO.isTexRectEnabled() && gl.isExtensionAvailable("GL_ARB_texture_rectangle");
    }
    
    private static boolean preferTexRect(final GL gl) {
        if (NativeWindowFactory.TYPE_MACOSX == NativeWindowFactory.getNativeWindowType(false)) {
            final String glGetString = gl.glGetString(7936);
            if (glGetString != null && glGetString.startsWith("ATI")) {
                return true;
            }
        }
        return false;
    }
    
    static {
        DEBUG = Debug.debug("Texture");
        VERBOSE = Debug.verbose();
        disableNPOT = PropertyAccess.isPropertyDefined("jogl.texture.nonpot", true);
        disableTexRect = PropertyAccess.isPropertyDefined("jogl.texture.notexrect", true);
    }
}
