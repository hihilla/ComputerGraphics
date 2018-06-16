// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture;

import java.nio.IntBuffer;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.opengl.util.texture.spi.JPEGImage;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.util.PNGPixelRect;
import com.jogamp.opengl.util.texture.spi.TGAImage;
import com.jogamp.opengl.util.texture.spi.SGIImage;
import com.jogamp.opengl.util.texture.spi.DDSImage;
import com.jogamp.opengl.util.texture.spi.NetPbmTextureWriter;
import java.util.HashMap;
import java.util.ArrayList;
import jogamp.opengl.Debug;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.util.Iterator;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.util.GLPixelStorageModes;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLContext;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import com.jogamp.common.util.IOUtil;
import java.io.File;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.spi.TextureWriter;
import java.util.Map;
import com.jogamp.opengl.util.texture.spi.TextureProvider;
import java.util.List;

public class TextureIO
{
    public static final String DDS = "dds";
    public static final String SGI = "sgi";
    public static final String SGI_RGB = "rgb";
    public static final String GIF = "gif";
    public static final String JPG = "jpg";
    public static final String PNG = "png";
    public static final String TGA = "tga";
    public static final String TIFF = "tiff";
    public static final String PAM = "pam";
    public static final String PPM = "ppm";
    private static final boolean DEBUG;
    private static boolean texRectEnabled;
    private static List<TextureProvider> textureProviders;
    private static Map<ImageType, TextureProvider> imageType2TextureProvider;
    private static List<TextureWriter> textureWriters;
    
    public static TextureData newTextureData(final GLProfile glProfile, final File file, final boolean b, String fileSuffix) throws IOException {
        if (fileSuffix == null) {
            fileSuffix = IOUtil.getFileSuffix(file);
        }
        return newTextureDataImpl(glProfile, file, 0, 0, b, fileSuffix);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, final boolean b, final String s) throws IOException {
        return newTextureDataImpl(glProfile, inputStream, 0, 0, b, s);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final URL url, final boolean b, String fileSuffix) throws IOException {
        if (fileSuffix == null) {
            fileSuffix = IOUtil.getFileSuffix(url.getPath());
        }
        return newTextureDataImpl(glProfile, url, 0, 0, b, fileSuffix);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final File file, final int n, final int n2, final boolean b, String fileSuffix) throws IOException, IllegalArgumentException {
        if (n == 0 || n2 == 0) {
            throw new IllegalArgumentException("internalFormat and pixelFormat must be non-zero");
        }
        if (fileSuffix == null) {
            fileSuffix = IOUtil.getFileSuffix(file);
        }
        return newTextureDataImpl(glProfile, file, n, n2, b, fileSuffix);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, final int n, final int n2, final boolean b, final String s) throws IOException, IllegalArgumentException {
        if (n == 0 || n2 == 0) {
            throw new IllegalArgumentException("internalFormat and pixelFormat must be non-zero");
        }
        return newTextureDataImpl(glProfile, inputStream, n, n2, b, s);
    }
    
    public static TextureData newTextureData(final GLProfile glProfile, final URL url, final int n, final int n2, final boolean b, String fileSuffix) throws IOException, IllegalArgumentException {
        if (n == 0 || n2 == 0) {
            throw new IllegalArgumentException("internalFormat and pixelFormat must be non-zero");
        }
        if (fileSuffix == null) {
            fileSuffix = IOUtil.getFileSuffix(url.getPath());
        }
        return newTextureDataImpl(glProfile, url, n, n2, b, fileSuffix);
    }
    
    public static Texture newTexture(final TextureData textureData) throws GLException, IllegalArgumentException {
        return newTexture(GLContext.getCurrentGL(), textureData);
    }
    
    public static Texture newTexture(final GL gl, final TextureData textureData) throws GLException, IllegalArgumentException {
        if (textureData == null) {
            throw new IllegalArgumentException("Null TextureData");
        }
        return new Texture(gl, textureData);
    }
    
    public static Texture newTexture(final File file, final boolean b) throws IOException, GLException {
        final GL currentGL = GLContext.getCurrentGL();
        final TextureData textureData = newTextureData(currentGL.getGLProfile(), file, b, IOUtil.getFileSuffix(file));
        final Texture texture = newTexture(currentGL, textureData);
        textureData.flush();
        return texture;
    }
    
    public static Texture newTexture(final InputStream inputStream, final boolean b, final String s) throws IOException, GLException {
        final GL currentGL = GLContext.getCurrentGL();
        final TextureData textureData = newTextureData(currentGL.getGLProfile(), inputStream, b, s);
        final Texture texture = newTexture(currentGL, textureData);
        textureData.flush();
        return texture;
    }
    
    public static Texture newTexture(final URL url, final boolean b, String fileSuffix) throws IOException, GLException {
        if (fileSuffix == null) {
            fileSuffix = IOUtil.getFileSuffix(url.getPath());
        }
        final GL currentGL = GLContext.getCurrentGL();
        final TextureData textureData = newTextureData(currentGL.getGLProfile(), url, b, fileSuffix);
        final Texture texture = newTexture(currentGL, textureData);
        textureData.flush();
        return texture;
    }
    
    public static Texture newTexture(final int n) {
        return new Texture(n);
    }
    
    public static void write(final Texture texture, final File file) throws IOException, GLException {
        if (texture.getTarget() != 3553) {
            throw new GLException("Only GL_TEXTURE_2D textures are supported");
        }
        final GL currentGL = GLContext.getCurrentGL();
        if (!currentGL.isGL2GL3()) {
            throw new GLException("Implementation only supports GL2GL3 (Use GLReadBufferUtil and the TextureData variant), have: " + currentGL);
        }
        final GL2GL3 gl2GL3 = currentGL.getGL2GL3();
        texture.bind(gl2GL3);
        final int glGetTexLevelParameteri = glGetTexLevelParameteri(gl2GL3, 3553, 0, 4099);
        final int glGetTexLevelParameteri2 = glGetTexLevelParameteri(gl2GL3, 3553, 0, 4096);
        final int glGetTexLevelParameteri3 = glGetTexLevelParameteri(gl2GL3, 3553, 0, 4097);
        final int glGetTexLevelParameteri4 = glGetTexLevelParameteri(gl2GL3, 3553, 0, 4101);
        TextureData textureData;
        if (glGetTexLevelParameteri == 33776 || glGetTexLevelParameteri == 33777 || glGetTexLevelParameteri == 33778 || glGetTexLevelParameteri == 33779) {
            final ByteBuffer allocate = ByteBuffer.allocate(glGetTexLevelParameteri(gl2GL3, 3553, 0, 34464));
            gl2GL3.glGetCompressedTexImage(3553, 0, allocate);
            textureData = new TextureData(gl2GL3.getGLProfile(), glGetTexLevelParameteri, glGetTexLevelParameteri2, glGetTexLevelParameteri3, glGetTexLevelParameteri4, glGetTexLevelParameteri, 5121, false, true, true, allocate, null);
        }
        else {
            int n = 0;
            int n2 = 0;
            switch (glGetTexLevelParameteri) {
                case 6407:
                case 32849:
                case 32992: {
                    n = 3;
                    n2 = 6407;
                    break;
                }
                case 6408:
                case 32768:
                case 32856:
                case 32993: {
                    n = 4;
                    n2 = 6408;
                    break;
                }
                default: {
                    throw new IOException("Unsupported texture internal format 0x" + Integer.toHexString(glGetTexLevelParameteri));
                }
            }
            final GLPixelStorageModes glPixelStorageModes = new GLPixelStorageModes();
            glPixelStorageModes.setPackAlignment(gl2GL3, 1);
            final ByteBuffer allocate2 = ByteBuffer.allocate((glGetTexLevelParameteri2 + 2 * glGetTexLevelParameteri4) * (glGetTexLevelParameteri3 + 2 * glGetTexLevelParameteri4) * n);
            if (TextureIO.DEBUG) {
                System.out.println("Allocated buffer of size " + allocate2.remaining() + " for fetched image (" + ((n2 == 6407) ? "GL_RGB" : "GL_RGBA") + ")");
            }
            gl2GL3.glGetTexImage(3553, 0, n2, 5121, allocate2);
            glPixelStorageModes.restore(gl2GL3);
            textureData = new TextureData(gl2GL3.getGLProfile(), glGetTexLevelParameteri, glGetTexLevelParameteri2, glGetTexLevelParameteri3, glGetTexLevelParameteri4, n2, 5121, false, false, false, allocate2, null);
            if (TextureIO.DEBUG) {
                System.out.println("data.getPixelFormat() = " + ((textureData.getPixelFormat() == 6407) ? "GL_RGB" : "GL_RGBA"));
            }
        }
        write(textureData, file);
    }
    
    public static void write(final TextureData textureData, final File file) throws IOException, GLException {
        final Iterator<TextureWriter> iterator = TextureIO.textureWriters.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().write(file, textureData)) {
                return;
            }
        }
        throw new IOException("No suitable texture writer found for " + file.getAbsolutePath());
    }
    
    public static void addTextureProvider(final TextureProvider textureProvider) {
        TextureIO.textureProviders.add(0, textureProvider);
        if (textureProvider instanceof TextureProvider.SupportsImageTypes) {
            final ImageType[] imageTypes = ((TextureProvider.SupportsImageTypes)textureProvider).getImageTypes();
            if (null != imageTypes) {
                for (int i = 0; i < imageTypes.length; ++i) {
                    TextureIO.imageType2TextureProvider.put(imageTypes[i], textureProvider);
                }
            }
        }
    }
    
    public static void addTextureWriter(final TextureWriter textureWriter) {
        TextureIO.textureWriters.add(0, textureWriter);
    }
    
    public static void setTexRectEnabled(final boolean texRectEnabled) {
        TextureIO.texRectEnabled = texRectEnabled;
    }
    
    public static boolean isTexRectEnabled() {
        return TextureIO.texRectEnabled;
    }
    
    private static TextureData newTextureDataImpl(final GLProfile glProfile, InputStream inputStream, final int n, final int n2, final boolean b, String lowerCase) throws IOException {
        if (inputStream == null) {
            throw new IOException("Stream was null");
        }
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        try {
            final ImageType srcImageType = new ImageType(inputStream);
            if (srcImageType.isDefined()) {
                final TextureProvider textureProvider = TextureIO.imageType2TextureProvider.get(srcImageType);
                if (null != textureProvider) {
                    final TextureData textureData = textureProvider.newTextureData(glProfile, inputStream, n, n2, b, srcImageType.type);
                    if (textureData != null) {
                        textureData.srcImageType = srcImageType;
                        return textureData;
                    }
                }
            }
        }
        catch (IOException ex) {
            if (TextureIO.DEBUG) {
                System.err.println("Caught " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        lowerCase = toLowerCase(lowerCase);
        for (final TextureProvider textureProvider2 : TextureIO.textureProviders) {
            final TextureData textureData2 = textureProvider2.newTextureData(glProfile, inputStream, n, n2, b, lowerCase);
            if (textureData2 != null) {
                if (textureProvider2 instanceof TextureProvider.SupportsImageTypes) {
                    textureData2.srcImageType = ((TextureProvider.SupportsImageTypes)textureProvider2).getImageTypes()[0];
                }
                return textureData2;
            }
        }
        throw new IOException("No suitable reader for given stream");
    }
    
    private static TextureData newTextureDataImpl(final GLProfile glProfile, final File file, final int n, final int n2, final boolean b, final String s) throws IOException {
        if (file == null) {
            throw new IOException("File was null");
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        try {
            return newTextureDataImpl(glProfile, bufferedInputStream, n, n2, b, (s != null) ? s : IOUtil.getFileSuffix(file));
        }
        catch (IOException ex) {
            throw new IOException(ex.getMessage() + ", given file " + file.getAbsolutePath(), ex);
        }
        finally {
            bufferedInputStream.close();
        }
    }
    
    private static TextureData newTextureDataImpl(final GLProfile glProfile, final URL url, final int n, final int n2, final boolean b, final String s) throws IOException {
        if (url == null) {
            throw new IOException("URL was null");
        }
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
        try {
            return newTextureDataImpl(glProfile, bufferedInputStream, n, n2, b, s);
        }
        catch (IOException ex) {
            throw new IOException(ex.getMessage() + ", given URL " + url, ex);
        }
        finally {
            bufferedInputStream.close();
        }
    }
    
    private static int glGetTexLevelParameteri(final GL2GL3 gl2GL3, final int n, final int n2, final int n3) {
        final int[] array = { 0 };
        gl2GL3.glGetTexLevelParameteriv(n, 0, n3, array, 0);
        return array[0];
    }
    
    private static String toLowerCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
    
    static {
        DEBUG = Debug.debug("TextureIO");
        TextureIO.texRectEnabled = true;
        TextureIO.textureProviders = new ArrayList<TextureProvider>();
        TextureIO.imageType2TextureProvider = new HashMap<ImageType, TextureProvider>();
        TextureIO.textureWriters = new ArrayList<TextureWriter>();
        if (GLProfile.isAWTAvailable()) {
            try {
                addTextureProvider((TextureProvider)Class.forName("com.jogamp.opengl.util.texture.spi.awt.IIOTextureProvider").newInstance());
            }
            catch (Exception ex) {
                if (TextureIO.DEBUG) {
                    ex.printStackTrace();
                }
            }
        }
        addTextureProvider(new DDSTextureProvider());
        addTextureProvider(new SGITextureProvider());
        addTextureProvider(new TGATextureProvider());
        addTextureProvider(new JPGTextureProvider());
        addTextureProvider(new PNGTextureProvider());
        if (GLProfile.isAWTAvailable()) {
            try {
                addTextureWriter((TextureWriter)Class.forName("com.jogamp.opengl.util.texture.spi.awt.IIOTextureWriter").newInstance());
            }
            catch (Exception ex2) {
                if (TextureIO.DEBUG) {
                    ex2.printStackTrace();
                }
            }
            catch (Error error) {
                if (TextureIO.DEBUG) {
                    error.printStackTrace();
                }
            }
        }
        addTextureWriter(new DDSTextureWriter());
        addTextureWriter(new SGITextureWriter());
        addTextureWriter(new TGATextureWriter());
        addTextureWriter(new NetPbmTextureWriter());
        addTextureWriter(new PNGTextureWriter());
    }
    
    abstract static class StreamBasedTextureProvider implements TextureProvider, SupportsImageTypes
    {
        @Override
        public final TextureData newTextureData(final GLProfile glProfile, final File file, final int n, final int n2, final boolean b, final String s) throws IOException {
            throw new UnsupportedOperationException("Only stream is supported");
        }
        
        @Override
        public final TextureData newTextureData(final GLProfile glProfile, final URL url, final int n, final int n2, final boolean b, final String s) throws IOException {
            throw new UnsupportedOperationException("Only stream is supported");
        }
    }
    
    static class DDSTextureProvider extends StreamBasedTextureProvider
    {
        private static final ImageType[] imageTypes;
        
        @Override
        public final ImageType[] getImageTypes() {
            return DDSTextureProvider.imageTypes;
        }
        
        @Override
        public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, final int n, final int n2, final boolean b, final String s) throws IOException {
            if ("dds".equals(s) || "dds".equals(ImageType.Util.getFileSuffix(inputStream))) {
                return this.newTextureData(glProfile, DDSImage.read(ByteBuffer.wrap(IOUtil.copyStream2ByteArray(inputStream))), n, n2, b);
            }
            return null;
        }
        
        private TextureData newTextureData(final GLProfile glProfile, final DDSImage ddsImage, int n, int n2, final boolean b) {
            final DDSImage.ImageInfo mipMap = ddsImage.getMipMap(0);
            if (n2 == 0) {
                switch (ddsImage.getPixelFormat()) {
                    case 20: {
                        n2 = 6407;
                        break;
                    }
                    default: {
                        n2 = 6408;
                        break;
                    }
                }
            }
            if (mipMap.isCompressed()) {
                switch (mipMap.getCompressionFormat()) {
                    case 827611204: {
                        n = 33776;
                        break;
                    }
                    case 861165636: {
                        n = 33778;
                        break;
                    }
                    case 894720068: {
                        n = 33779;
                        break;
                    }
                    default: {
                        throw new RuntimeException("Unsupported DDS compression format \"" + DDSImage.getCompressionFormatName(mipMap.getCompressionFormat()) + "\"");
                    }
                }
            }
            if (n == 0) {
                switch (ddsImage.getPixelFormat()) {
                    case 20: {
                        n2 = 6407;
                        break;
                    }
                    default: {
                        n2 = 6408;
                        break;
                    }
                }
            }
            final TextureData.Flusher flusher = new TextureData.Flusher() {
                @Override
                public void flush() {
                    ddsImage.close();
                }
            };
            TextureData textureData;
            if (b && ddsImage.getNumMipMaps() > 0) {
                final Buffer[] array = new Buffer[ddsImage.getNumMipMaps()];
                for (int i = 0; i < ddsImage.getNumMipMaps(); ++i) {
                    array[i] = ddsImage.getMipMap(i).getData();
                }
                textureData = new TextureData(glProfile, n, mipMap.getWidth(), mipMap.getHeight(), 0, n2, 5121, mipMap.isCompressed(), true, array, flusher);
            }
            else {
                textureData = new TextureData(glProfile, n, mipMap.getWidth(), mipMap.getHeight(), 0, n2, 5121, false, mipMap.isCompressed(), true, mipMap.getData(), flusher);
            }
            return textureData;
        }
        
        static {
            imageTypes = new ImageType[] { new ImageType("dds") };
        }
    }
    
    static class SGITextureProvider extends StreamBasedTextureProvider
    {
        private static final ImageType[] imageTypes;
        
        @Override
        public final ImageType[] getImageTypes() {
            return SGITextureProvider.imageTypes;
        }
        
        @Override
        public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, int format, int format2, final boolean b, final String s) throws IOException {
            if ("sgi".equals(s) || "rgb".equals(s) || "sgi".equals(ImageType.Util.getFileSuffix(inputStream)) || "rgb".equals(ImageType.Util.getFileSuffix(inputStream))) {
                final SGIImage read = SGIImage.read(inputStream);
                if (format2 == 0) {
                    format2 = read.getFormat();
                }
                if (format == 0) {
                    format = read.getFormat();
                }
                return new TextureData(glProfile, format, read.getWidth(), read.getHeight(), 0, format2, 5121, b, false, false, ByteBuffer.wrap(read.getData()), null);
            }
            return null;
        }
        
        static {
            imageTypes = new ImageType[] { new ImageType("rgb") };
        }
    }
    
    static class TGATextureProvider extends StreamBasedTextureProvider
    {
        private static final ImageType[] imageTypes;
        
        @Override
        public final ImageType[] getImageTypes() {
            return TGATextureProvider.imageTypes;
        }
        
        @Override
        public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, int n, int glFormat, final boolean b, final String s) throws IOException {
            if ("tga".equals(s)) {
                final TGAImage read = TGAImage.read(glProfile, inputStream);
                if (glFormat == 0) {
                    glFormat = read.getGLFormat();
                }
                if (n == 0) {
                    if (glProfile.isGL2ES3()) {
                        n = ((read.getBytesPerPixel() == 4) ? 32856 : 32849);
                    }
                    else {
                        n = ((read.getBytesPerPixel() == 4) ? 6408 : 6407);
                    }
                }
                return new TextureData(glProfile, n, read.getWidth(), read.getHeight(), 0, glFormat, 5121, b, false, false, read.getData(), null);
            }
            return null;
        }
        
        static {
            imageTypes = new ImageType[] { new ImageType("tga") };
        }
    }
    
    static class PNGTextureProvider extends StreamBasedTextureProvider
    {
        private static final ImageType[] imageTypes;
        
        @Override
        public final ImageType[] getImageTypes() {
            return PNGTextureProvider.imageTypes;
        }
        
        @Override
        public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, int n, int format, final boolean b, final String s) throws IOException {
            if ("png".equals(s) || "png".equals(ImageType.Util.getFileSuffix(inputStream))) {
                final PNGPixelRect read = PNGPixelRect.read(inputStream, null, true, 0, true);
                final GLPixelBuffer.GLPixelAttributes glPixelAttributes = new GLPixelBuffer.GLPixelAttributes(glProfile, read.getPixelformat(), false);
                if (0 == format) {
                    format = glPixelAttributes.format;
                }
                if (0 == n) {
                    final boolean b2 = 4 == glPixelAttributes.pfmt.comp.bytesPerPixel();
                    if (glProfile.isGL2ES3()) {
                        n = (b2 ? 32856 : 32849);
                    }
                    else {
                        n = (b2 ? 6408 : 6407);
                    }
                }
                return new TextureData(glProfile, n, read.getSize().getWidth(), read.getSize().getHeight(), 0, format, glPixelAttributes.type, b, false, false, read.getPixels(), null);
            }
            return null;
        }
        
        static {
            imageTypes = new ImageType[] { new ImageType("png") };
        }
    }
    
    static class JPGTextureProvider extends StreamBasedTextureProvider
    {
        private static final ImageType[] imageTypes;
        
        @Override
        public final ImageType[] getImageTypes() {
            return JPGTextureProvider.imageTypes;
        }
        
        @Override
        public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, int n, int glFormat, final boolean b, final String s) throws IOException {
            if ("jpg".equals(s) || "jpg".equals(ImageType.Util.getFileSuffix(inputStream))) {
                final JPEGImage read = JPEGImage.read(inputStream);
                if (glFormat == 0) {
                    glFormat = read.getGLFormat();
                }
                if (n == 0) {
                    if (glProfile.isGL2ES3()) {
                        n = ((read.getBytesPerPixel() == 4) ? 32856 : 32849);
                    }
                    else {
                        n = ((read.getBytesPerPixel() == 4) ? 6408 : 6407);
                    }
                }
                return new TextureData(glProfile, n, read.getWidth(), read.getHeight(), 0, glFormat, read.getGLType(), b, false, false, read.getData(), null);
            }
            return null;
        }
        
        static {
            imageTypes = new ImageType[] { new ImageType("jpg") };
        }
    }
    
    static class DDSTextureWriter implements TextureWriter
    {
        @Override
        public boolean write(final File file, final TextureData textureData) throws IOException {
            if (!"dds".equals(IOUtil.getFileSuffix(file))) {
                return false;
            }
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = textureData.getPixelAttributes();
            final int format = pixelAttributes.format;
            final int type = pixelAttributes.type;
            if (type != 5120 && type != 5121) {
                throw new IOException("DDS writer only supports byte / unsigned byte textures");
            }
            int n = 0;
            switch (format) {
                case 6407: {
                    n = 20;
                    break;
                }
                case 6408: {
                    n = 21;
                    break;
                }
                case 33776: {
                    n = 827611204;
                    break;
                }
                case 33777: {
                    throw new IOException("RGBA DXT1 not yet supported");
                }
                case 33778: {
                    n = 861165636;
                    break;
                }
                case 33779: {
                    n = 894720068;
                    break;
                }
                default: {
                    throw new IOException("Unsupported pixel format 0x" + Integer.toHexString(format) + " by DDS writer");
                }
            }
            ByteBuffer[] array;
            if (textureData.getMipmapData() != null) {
                array = new ByteBuffer[textureData.getMipmapData().length];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = (ByteBuffer)textureData.getMipmapData()[i];
                }
            }
            else {
                array = new ByteBuffer[] { (ByteBuffer)textureData.getBuffer() };
            }
            DDSImage.createFromData(n, textureData.getWidth(), textureData.getHeight(), array).write(file);
            return true;
        }
    }
    
    static class SGITextureWriter implements TextureWriter
    {
        @Override
        public boolean write(final File file, final TextureData textureData) throws IOException {
            final String fileSuffix = IOUtil.getFileSuffix(file);
            if (!"sgi".equals(fileSuffix) && !"rgb".equals(fileSuffix)) {
                return false;
            }
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = textureData.getPixelAttributes();
            final int format = pixelAttributes.format;
            final int type = pixelAttributes.type;
            if ((format == 6407 || format == 6408) && (type == 5120 || type == 5121)) {
                final ByteBuffer byteBuffer = (ByteBuffer)((textureData.getBuffer() != null) ? textureData.getBuffer() : ((ByteBuffer)textureData.getMipmapData()[0]));
                byte[] array;
                if (byteBuffer.hasArray()) {
                    array = byteBuffer.array();
                }
                else {
                    byteBuffer.rewind();
                    array = new byte[byteBuffer.remaining()];
                    byteBuffer.get(array);
                    byteBuffer.rewind();
                }
                SGIImage.createFromData(textureData.getWidth(), textureData.getHeight(), format == 6408, array).write(file, false);
                return true;
            }
            throw new IOException("SGI writer doesn't support this pixel format / type (only GL_RGB/A + bytes)");
        }
    }
    
    static class TGATextureWriter implements TextureWriter
    {
        @Override
        public boolean write(final File file, final TextureData textureData) throws IOException {
            if (!"tga".equals(IOUtil.getFileSuffix(file))) {
                return false;
            }
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = textureData.getPixelAttributes();
            final int format = pixelAttributes.format;
            final int type = pixelAttributes.type;
            if ((format == 6407 || format == 6408 || format == 32992 || format == 32993) && (type == 5120 || type == 5121)) {
                ByteBuffer byteBuffer = (ByteBuffer)textureData.getBuffer();
                if (null == byteBuffer) {
                    byteBuffer = (ByteBuffer)textureData.getMipmapData()[0];
                }
                byteBuffer.rewind();
                if (format == 6407 || format == 6408) {
                    for (int n = (format == 6407) ? 3 : 4, i = 0; i < byteBuffer.remaining(); i += n) {
                        final byte value = byteBuffer.get(i + 0);
                        byteBuffer.put(i + 0, byteBuffer.get(i + 2));
                        byteBuffer.put(i + 2, value);
                    }
                }
                TGAImage.createFromData(textureData.getWidth(), textureData.getHeight(), format == 6408 || format == 32993, false, byteBuffer).write(file);
                return true;
            }
            throw new IOException("TGA writer doesn't support this pixel format 0x" + Integer.toHexString(format) + " / type 0x" + Integer.toHexString(format) + " (only GL_RGB/A, GL_BGR/A + bytes)");
        }
    }
    
    static class PNGTextureWriter implements TextureWriter
    {
        @Override
        public boolean write(final File file, final TextureData textureData) throws IOException {
            if (!"png".equals(IOUtil.getFileSuffix(file))) {
                return false;
            }
            final GLPixelBuffer.GLPixelAttributes pixelAttributes = textureData.getPixelAttributes();
            final int format = pixelAttributes.format;
            final int type = pixelAttributes.type;
            final int bytesPerPixel = pixelAttributes.pfmt.comp.bytesPerPixel();
            final PixelFormat pfmt = pixelAttributes.pfmt;
            if ((bytesPerPixel == 0 && 3 != bytesPerPixel && 4 != bytesPerPixel) || (type != 5120 && type != 5121)) {
                throw new IOException("PNG writer doesn't support this pixel format 0x" + Integer.toHexString(format) + " / type 0x" + Integer.toHexString(format) + " (only GL_RGB/A, GL_BGR/A + bytes)");
            }
            Buffer buffer = textureData.getBuffer();
            if (null == buffer) {
                buffer = textureData.getMipmapData()[0];
            }
            if (null == buffer) {
                throw new IOException("Pixel storage buffer is null");
            }
            final Dimension dimension = new Dimension(textureData.getWidth(), textureData.getHeight());
            if (buffer instanceof ByteBuffer) {
                final ByteBuffer byteBuffer = (ByteBuffer)buffer;
                byteBuffer.rewind();
                new PNGPixelRect(pfmt, dimension, 0, !textureData.getMustFlipVertically(), byteBuffer, -1.0, -1.0).write(new BufferedOutputStream(IOUtil.getFileOutputStream(file, true)), true);
                return true;
            }
            if (buffer instanceof IntBuffer) {
                final IntBuffer intBuffer = (IntBuffer)buffer;
                intBuffer.rewind();
                PNGPixelRect.write(pfmt, dimension, 0, !textureData.getMustFlipVertically(), intBuffer, -1.0, -1.0, new BufferedOutputStream(IOUtil.getFileOutputStream(file, true)), true);
                return true;
            }
            throw new IOException("PNG writer doesn't support pixel storage buffer of type " + ((IntBuffer)buffer).getClass().getName());
        }
    }
}
