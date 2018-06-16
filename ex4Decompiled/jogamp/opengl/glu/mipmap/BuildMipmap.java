// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import jogamp.opengl.Debug;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.ShortBuffer;
import java.nio.Buffer;
import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;

public class BuildMipmap
{
    private static final boolean DEBUG;
    private static final boolean VERBOSE;
    private static final int TARGA_HEADER_SIZE = 18;
    
    public static int gluBuild1DMipmapLevelsCore(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer) {
        ShortBuffer shortBuffer = null;
        final PixelStorageModes pixelStorageModes = new PixelStorageModes();
        assert Mipmap.checkMipmapArgs(n2, n5, n6) == 0;
        assert n3 >= 1;
        int n10 = n4;
        final int n11 = Mipmap.computeLog(n10) + n7;
        Mipmap.retrieveStoreModes(gl, pixelStorageModes);
        ShortBuffer shortBuffer2;
        try {
            shortBuffer2 = Buffers.newDirectByteBuffer(Mipmap.image_size(n3, 1, n5, 5123)).asShortBuffer();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return 100902;
        }
        int n12 = n3;
        Image.fill_image(pixelStorageModes, n3, 1, n5, n6, Mipmap.is_index(n5), byteBuffer, shortBuffer2);
        final int elements_per_group = Mipmap.elements_per_group(n5, n6);
        gl.glPixelStorei(3317, 2);
        gl.glPixelStorei(3315, 0);
        gl.glPixelStorei(3316, 0);
        gl.glPixelStorei(3314, 0);
        gl.glPixelStorei(3312, 0);
        for (int i = n7; i <= n11; ++i) {
            if (n12 == n10) {
                if (n8 <= i && i <= n9) {
                    gl.getGL2().glTexImage1D(n, i, n2, n12, 0, n5, 5123, shortBuffer2);
                }
            }
            else {
                if (shortBuffer == null) {
                    final int image_size = Mipmap.image_size(n10, 1, n5, 5123);
                    try {
                        shortBuffer = Buffers.newDirectByteBuffer(image_size).asShortBuffer();
                    }
                    catch (OutOfMemoryError outOfMemoryError2) {
                        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                        return 100902;
                    }
                }
                ScaleInternal.scale_internal(elements_per_group, n12, 1, shortBuffer2, n10, 1, shortBuffer);
                final ShortBuffer shortBuffer3 = shortBuffer;
                shortBuffer = shortBuffer2;
                shortBuffer2 = shortBuffer3;
                n12 = n10;
                if (n8 <= i && i <= n9) {
                    gl.getGL2().glTexImage1D(n, i, n2, n12, 0, n5, 5123, shortBuffer2);
                }
            }
            if (n10 > 1) {
                n10 /= 2;
            }
        }
        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
        return 0;
    }
    
    public static int bitmapBuild2DMipmaps(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        ShortBuffer shortBuffer = null;
        final PixelStorageModes pixelStorageModes = new PixelStorageModes();
        Mipmap.retrieveStoreModes(gl, pixelStorageModes);
        Mipmap.closestFit(gl, n, n3, n4, n2, n5, n6, array, array2);
        int computeLog = Mipmap.computeLog(array[0]);
        final int computeLog2 = Mipmap.computeLog(array2[0]);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        ShortBuffer shortBuffer2;
        try {
            shortBuffer2 = Buffers.newDirectByteBuffer(Mipmap.image_size(n3, n4, n5, 5123)).asShortBuffer();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return 100902;
        }
        int n7 = n3;
        int n8 = n4;
        Image.fill_image(pixelStorageModes, n3, n4, n5, n6, Mipmap.is_index(n5), byteBuffer, shortBuffer2);
        final int elements_per_group = Mipmap.elements_per_group(n5, n6);
        gl.glPixelStorei(3317, 2);
        gl.glPixelStorei(3315, 0);
        gl.glPixelStorei(3316, 0);
        gl.glPixelStorei(3314, 0);
        gl.glPixelStorei(3312, 0);
        for (int i = 0; i < computeLog; ++i) {
            if (n7 == array[0] && n8 == array2[0]) {
                shortBuffer2.rewind();
                gl.glTexImage2D(n, i, n2, n7, n8, 0, n5, 5123, shortBuffer2);
            }
            else {
                if (shortBuffer == null) {
                    final int image_size = Mipmap.image_size(array[0], array2[0], n5, 5123);
                    try {
                        shortBuffer = Buffers.newDirectByteBuffer(image_size).asShortBuffer();
                    }
                    catch (OutOfMemoryError outOfMemoryError2) {
                        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                        return 100902;
                    }
                }
                ScaleInternal.scale_internal(elements_per_group, n7, n8, shortBuffer2, array[0], array2[0], shortBuffer);
                final ShortBuffer shortBuffer3 = shortBuffer;
                shortBuffer = shortBuffer2;
                shortBuffer2 = shortBuffer3;
                n7 = array[0];
                n8 = array2[0];
                shortBuffer2.rewind();
                gl.glTexImage2D(n, i, n2, n7, n8, 0, n5, 5123, shortBuffer2);
            }
            if (array2[0] > 1) {
                final int[] array3 = array;
                final int n9 = 0;
                array3[n9] /= 2;
            }
            if (array2[0] > 1) {
                final int[] array4 = array2;
                final int n10 = 0;
                array4[n10] /= 2;
            }
        }
        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
        return 0;
    }
    
    public static int gluBuild2DMipmapLevelsCore(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final ByteBuffer byteBuffer) {
        final ByteBuffer byteBuffer2 = null;
        final PixelStorageModes pixelStorageModes = new PixelStorageModes();
        assert Mipmap.checkMipmapArgs(n2, n7, n8) == 0;
        assert n3 >= 1 && n4 >= 1;
        if (n8 == 6656) {
            return bitmapBuild2DMipmaps(gl, n, n2, n3, n4, n7, n8, byteBuffer);
        }
        int n12 = n5;
        int n13 = n6;
        int computeLog = Mipmap.computeLog(n12);
        final int computeLog2 = Mipmap.computeLog(n13);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        final int n14 = computeLog + n9;
        Mipmap.retrieveStoreModes(gl, pixelStorageModes);
        boolean unpackSwapBytes = pixelStorageModes.getUnpackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n7, n8);
        int unpackRowLength;
        if (pixelStorageModes.getUnpackRowLength() > 0) {
            unpackRowLength = pixelStorageModes.getUnpackRowLength();
        }
        else {
            unpackRowLength = n3;
        }
        final int bytes_per_element = Mipmap.bytes_per_element(n8);
        final int n15 = bytes_per_element * elements_per_group;
        if (bytes_per_element == 1) {
            unpackSwapBytes = false;
        }
        int n16 = unpackRowLength * n15;
        final int n17 = n16 % pixelStorageModes.getUnpackAlignment();
        if (n17 != 0) {
            n16 += pixelStorageModes.getUnpackAlignment() - n17;
        }
        final int n18 = pixelStorageModes.getUnpackSkipRows() * n16 + pixelStorageModes.getUnpackSkipPixels() * n15;
        byteBuffer.position(n18);
        gl.glPixelStorei(3315, 0);
        gl.glPixelStorei(3316, 0);
        gl.glPixelStorei(3314, 0);
        boolean b;
        int n21;
        ByteBuffer byteBuffer3;
        ByteBuffer byteBuffer4;
        int i;
        if (n3 == n12 && n4 == n13) {
            if (n10 <= n9 && n9 <= n11) {
                byteBuffer.rewind();
                gl.glTexImage2D(n, n9, n2, n3, n4, 0, n7, n8, byteBuffer);
            }
            if (n14 == 0) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                return 0;
            }
            int n19 = n12 / 2;
            int n20 = n13 / 2;
            if (n19 < 1) {
                n19 = 1;
            }
            if (n20 < 1) {
                n20 = 1;
            }
            final int image_size = Mipmap.image_size(n19, n20, n7, n8);
            ByteBuffer directByteBuffer = null;
            try {
                switch (n8) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        directByteBuffer = Buffers.newDirectByteBuffer(image_size);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                return 100902;
            }
            if (directByteBuffer != null) {
                switch (n8) {
                    case 5121: {
                        HalveImage.halveImage_ubyte(elements_per_group, n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, n15);
                        break;
                    }
                    case 5120: {
                        HalveImage.halveImage_byte(elements_per_group, n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, n15);
                        break;
                    }
                    case 5123: {
                        HalveImage.halveImage_ushort(elements_per_group, n3, n4, byteBuffer, directByteBuffer.asShortBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                        break;
                    }
                    case 5122: {
                        HalveImage.halveImage_short(elements_per_group, n3, n4, byteBuffer, directByteBuffer.asShortBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                        break;
                    }
                    case 5125: {
                        HalveImage.halveImage_uint(elements_per_group, n3, n4, byteBuffer, directByteBuffer.asIntBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                        break;
                    }
                    case 5124: {
                        HalveImage.halveImage_int(elements_per_group, n3, n4, byteBuffer, directByteBuffer.asIntBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                        break;
                    }
                    case 5126: {
                        HalveImage.halveImage_float(elements_per_group, n3, n4, byteBuffer, directByteBuffer.asFloatBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                        break;
                    }
                    case 32818: {
                        assert n7 == 6407;
                        HalveImage.halveImagePackedPixel(3, new Extract332(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33634: {
                        assert n7 == 6407;
                        HalveImage.halveImagePackedPixel(3, new Extract233rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33635: {
                        HalveImage.halveImagePackedPixel(3, new Extract565(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33636: {
                        HalveImage.halveImagePackedPixel(3, new Extract565rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 32819: {
                        HalveImage.halveImagePackedPixel(4, new Extract4444(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33637: {
                        HalveImage.halveImagePackedPixel(4, new Extract4444rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 32820: {
                        HalveImage.halveImagePackedPixel(4, new Extract5551(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33638: {
                        HalveImage.halveImagePackedPixel(4, new Extract1555rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 32821: {
                        HalveImage.halveImagePackedPixel(4, new Extract8888(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33639: {
                        HalveImage.halveImagePackedPixel(4, new Extract8888rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 36342: {
                        HalveImage.halveImagePackedPixel(4, new Extract1010102(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    case 33640: {
                        HalveImage.halveImagePackedPixel(4, new Extract2101010rev(), n3, n4, byteBuffer, directByteBuffer, bytes_per_element, n16, unpackSwapBytes);
                        break;
                    }
                    default: {
                        assert false;
                        break;
                    }
                }
            }
            n12 = n3 / 2;
            n13 = n4 / 2;
            if (n12 < 1) {
                n12 = 1;
            }
            if (n13 < 1) {
                n13 = 1;
            }
            b = false;
            n21 = n12 * n15;
            final int image_size2 = Mipmap.image_size(n12, n13, n7, n8);
            byteBuffer3 = directByteBuffer;
            try {
                switch (n8) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        byteBuffer4 = Buffers.newDirectByteBuffer(image_size2);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                return 100902;
            }
            i = n9 + 1;
        }
        else {
            final int image_size3 = Mipmap.image_size(n12, n13, n7, n8);
            ByteBuffer directByteBuffer2 = null;
            try {
                switch (n8) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        directByteBuffer2 = Buffers.newDirectByteBuffer(image_size3);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError3) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                return 100902;
            }
            byteBuffer.position(n18);
            switch (n8) {
                case 5121: {
                    ScaleInternal.scale_internal_ubyte(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, n15);
                    break;
                }
                case 5120: {
                    ScaleInternal.scale_internal_byte(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, n15);
                    break;
                }
                case 5123: {
                    ScaleInternal.scale_internal_ushort(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2.asShortBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                    break;
                }
                case 5122: {
                    ScaleInternal.scale_internal_ushort(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2.asShortBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                    break;
                }
                case 5125: {
                    ScaleInternal.scale_internal_uint(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2.asIntBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                    break;
                }
                case 5124: {
                    ScaleInternal.scale_internal_int(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2.asIntBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                    break;
                }
                case 5126: {
                    ScaleInternal.scale_internal_float(elements_per_group, n3, n4, byteBuffer, n12, n13, directByteBuffer2.asFloatBuffer(), bytes_per_element, n16, n15, unpackSwapBytes);
                    break;
                }
                case 32818: {
                    ScaleInternal.scaleInternalPackedPixel(3, new Extract332(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33634: {
                    ScaleInternal.scaleInternalPackedPixel(3, new Extract233rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33635: {
                    ScaleInternal.scaleInternalPackedPixel(3, new Extract565(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33636: {
                    ScaleInternal.scaleInternalPackedPixel(3, new Extract565rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 32819: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract4444(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33637: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract4444rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 32820: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract5551(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33638: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract1555rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 32821: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract8888(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33639: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract8888rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 36342: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract1010102(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                case 33640: {
                    ScaleInternal.scaleInternalPackedPixel(4, new Extract2101010rev(), n3, n4, byteBuffer, n12, n13, directByteBuffer2, bytes_per_element, n16, unpackSwapBytes);
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
            b = false;
            n21 = n12 * n15;
            final ByteBuffer byteBuffer5 = byteBuffer2;
            byteBuffer3 = directByteBuffer2;
            byteBuffer4 = byteBuffer5;
            if (n14 != 0) {
                int n22 = n12 / 2;
                int n23 = n13 / 2;
                if (n22 < 1) {
                    n22 = 1;
                }
                if (n23 < 1) {
                    n23 = 1;
                }
                final int image_size4 = Mipmap.image_size(n22, n23, n7, n8);
                try {
                    switch (n8) {
                        case 5120:
                        case 5121:
                        case 5122:
                        case 5123:
                        case 5124:
                        case 5125:
                        case 5126:
                        case 32818:
                        case 32819:
                        case 32820:
                        case 32821:
                        case 33634:
                        case 33635:
                        case 33636:
                        case 33637:
                        case 33638:
                        case 33639:
                        case 33640:
                        case 36342: {
                            byteBuffer4 = Buffers.newDirectByteBuffer(image_size4);
                            break;
                        }
                        default: {
                            return 100900;
                        }
                    }
                }
                catch (OutOfMemoryError outOfMemoryError4) {
                    gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                    gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                    gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                    gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                    gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                    return 100902;
                }
            }
            i = n9;
        }
        gl.glPixelStorei(3312, 0);
        if (n10 <= i && i <= n11) {
            byteBuffer3.rewind();
            gl.glTexImage2D(n, i, n2, n12, n13, 0, n7, n8, byteBuffer3);
            if (BuildMipmap.DEBUG) {
                System.err.println("GL Error(" + i + "): " + gl.glGetError());
                if (BuildMipmap.VERBOSE) {
                    byteBuffer3.limit(Mipmap.image_size(n12, n13, n7, n8));
                    writeTargaFile("glu2DMipmapJ" + i + ".tga", byteBuffer3, n12, n13);
                    byteBuffer3.clear();
                }
            }
        }
        ++i;
        while (i <= n14) {
            byteBuffer3.rewind();
            byteBuffer4.rewind();
            switch (n8) {
                case 5121: {
                    HalveImage.halveImage_ubyte(elements_per_group, n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, n15);
                    break;
                }
                case 5120: {
                    HalveImage.halveImage_byte(elements_per_group, n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, n15);
                    break;
                }
                case 5123: {
                    HalveImage.halveImage_ushort(elements_per_group, n12, n13, byteBuffer3, byteBuffer4.asShortBuffer(), bytes_per_element, n21, n15, b);
                    break;
                }
                case 5122: {
                    HalveImage.halveImage_short(elements_per_group, n12, n13, byteBuffer3, byteBuffer4.asShortBuffer(), bytes_per_element, n21, n15, b);
                    break;
                }
                case 5125: {
                    HalveImage.halveImage_uint(elements_per_group, n12, n13, byteBuffer3, byteBuffer4.asIntBuffer(), bytes_per_element, n21, n15, b);
                    break;
                }
                case 5124: {
                    HalveImage.halveImage_int(elements_per_group, n12, n13, byteBuffer3, byteBuffer4.asIntBuffer(), bytes_per_element, n21, n15, b);
                    break;
                }
                case 5126: {
                    HalveImage.halveImage_float(elements_per_group, n12, n13, byteBuffer3, byteBuffer4.asFloatBuffer(), bytes_per_element, n21, n15, b);
                    break;
                }
                case 32818: {
                    assert n7 == 6407;
                    HalveImage.halveImagePackedPixel(3, new Extract332(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33634: {
                    assert n7 == 6407;
                    HalveImage.halveImagePackedPixel(3, new Extract233rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33635: {
                    HalveImage.halveImagePackedPixel(3, new Extract565(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33636: {
                    HalveImage.halveImagePackedPixel(3, new Extract565rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 32819: {
                    HalveImage.halveImagePackedPixel(4, new Extract4444(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33637: {
                    HalveImage.halveImagePackedPixel(4, new Extract4444rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 32820: {
                    HalveImage.halveImagePackedPixel(4, new Extract5551(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33638: {
                    HalveImage.halveImagePackedPixel(4, new Extract1555rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 32821: {
                    HalveImage.halveImagePackedPixel(4, new Extract8888(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33639: {
                    HalveImage.halveImagePackedPixel(4, new Extract8888rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 36342: {
                    HalveImage.halveImagePackedPixel(4, new Extract1010102(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                case 33640: {
                    HalveImage.halveImagePackedPixel(4, new Extract2101010rev(), n12, n13, byteBuffer3, byteBuffer4, bytes_per_element, n21, b);
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
            final ByteBuffer byteBuffer6 = byteBuffer3;
            byteBuffer3 = byteBuffer4;
            byteBuffer4 = byteBuffer6;
            if (n12 > 1) {
                n12 /= 2;
                n21 /= 2;
            }
            if (n13 > 1) {
                n13 /= 2;
            }
            final int n24 = n21 % pixelStorageModes.getUnpackAlignment();
            if (n24 == 0) {
                if (n10 <= i && i <= n11) {
                    byteBuffer3.rewind();
                    gl.glTexImage2D(n, i, n2, n12, n13, 0, n7, n8, byteBuffer3);
                    if (BuildMipmap.DEBUG) {
                        System.err.println("GL Error(" + i + "): " + gl.glGetError());
                        if (BuildMipmap.VERBOSE) {
                            byteBuffer3.limit(Mipmap.image_size(n12, n13, n7, n8));
                            writeTargaFile("glu2DMipmapJ" + i + ".tga", byteBuffer3, n12, n13);
                            byteBuffer3.clear();
                        }
                    }
                }
            }
            else {
                final int n25 = n21 + pixelStorageModes.getUnpackAlignment() - n24;
                ByteBuffer allocateDirect;
                try {
                    allocateDirect = ByteBuffer.allocateDirect(n25 * n13);
                }
                catch (OutOfMemoryError outOfMemoryError5) {
                    gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                    gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                    gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                    gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                    gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                    return 100902;
                }
                byteBuffer3.rewind();
                for (int j = 0; j < n13; ++j) {
                    allocateDirect.position(n25 * j);
                    for (int k = 0; k < n21; ++k) {
                        allocateDirect.put(byteBuffer3.get());
                    }
                }
                if (n10 <= i && i <= n11) {
                    allocateDirect.rewind();
                    gl.glTexImage2D(n, i, n2, n12, n13, 0, n7, n8, allocateDirect);
                    if (BuildMipmap.DEBUG) {
                        System.err.println("GL Error(" + i + " padded): " + gl.glGetError());
                        if (BuildMipmap.VERBOSE) {
                            writeTargaFile("glu2DMipmapJ" + i + ".tga", allocateDirect, n12, n13);
                        }
                    }
                }
            }
            ++i;
        }
        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
        return 0;
    }
    
    public static int fastBuild2DMipmaps(final GL gl, final PixelStorageModes pixelStorageModes, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        Mipmap.closestFit(gl, n, n3, n4, n2, n5, n6, array, array2);
        int computeLog = Mipmap.computeLog(array[0]);
        final int computeLog2 = Mipmap.computeLog(array2[0]);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        final int elements_per_group = Mipmap.elements_per_group(n5, n6);
        ByteBuffer directByteBuffer = null;
        ByteBuffer directByteBuffer2;
        int n7;
        int n8;
        if (pixelStorageModes.getUnpackSkipRows() == 0 && pixelStorageModes.getUnpackSkipPixels() == 0) {
            directByteBuffer2 = byteBuffer;
            n7 = n3;
            n8 = n4;
        }
        else {
            try {
                directByteBuffer2 = Buffers.newDirectByteBuffer(Mipmap.image_size(n3, n4, n5, 5121));
            }
            catch (OutOfMemoryError outOfMemoryError) {
                return 100902;
            }
            n7 = n3;
            n8 = n4;
            int unpackRowLength;
            if (pixelStorageModes.getUnpackRowLength() > 0) {
                unpackRowLength = pixelStorageModes.getUnpackRowLength();
            }
            else {
                unpackRowLength = n3;
            }
            final int n9 = unpackRowLength * elements_per_group;
            final int n10 = n3 * elements_per_group;
            int n11 = pixelStorageModes.getUnpackSkipRows() * n9 + pixelStorageModes.getUnpackSkipPixels() * elements_per_group;
            for (int i = 0; i < n4; ++i) {
                byteBuffer.position(n11);
                for (int j = 0; j < n10; ++j) {
                    directByteBuffer2.put(byteBuffer.get());
                }
                n11 += n9;
            }
        }
        gl.glPixelStorei(3317, 1);
        gl.glPixelStorei(3315, 0);
        gl.glPixelStorei(3316, 0);
        gl.glPixelStorei(3314, 0);
        gl.glPixelStorei(3312, 0);
        for (int k = 0; k <= computeLog; ++k) {
            if (n7 == array[0] && n8 == array2[0]) {
                directByteBuffer2.rewind();
                gl.glTexImage2D(n, k, n2, n7, n8, 0, n5, 5121, directByteBuffer2);
            }
            else {
                if (directByteBuffer == null) {
                    final int image_size = Mipmap.image_size(array[0], array2[0], n5, 5121);
                    try {
                        directByteBuffer = Buffers.newDirectByteBuffer(image_size);
                    }
                    catch (OutOfMemoryError outOfMemoryError2) {
                        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                        return 100902;
                    }
                }
                final ByteBuffer byteBuffer2 = directByteBuffer;
                directByteBuffer = directByteBuffer2;
                directByteBuffer2 = byteBuffer2;
                n7 = array[0];
                n8 = array2[0];
                directByteBuffer2.rewind();
                gl.glTexImage2D(n, k, n2, n7, n8, 0, n5, 5121, directByteBuffer2);
            }
            if (array[0] > 1) {
                final int[] array3 = array;
                final int n12 = 0;
                array3[n12] /= 2;
            }
            if (array2[0] > 1) {
                final int[] array4 = array2;
                final int n13 = 0;
                array4[n13] /= 2;
            }
        }
        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
        return 0;
    }
    
    public static int gluBuild3DMipmapLevelsCore(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final int n11, final int n12, final int n13, final ByteBuffer byteBuffer) {
        final PixelStorageModes pixelStorageModes = new PixelStorageModes();
        assert Mipmap.checkMipmapArgs(n2, n9, n10) == 0;
        assert n3 >= 1 && n4 >= 1 && n5 >= 1;
        assert n10 != 6656;
        final ByteBuffer byteBuffer2 = null;
        int n14 = n6;
        int n15 = n7;
        int n16 = n8;
        int computeLog = Mipmap.computeLog(n14);
        final int computeLog2 = Mipmap.computeLog(n15);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        final int computeLog3 = Mipmap.computeLog(n16);
        if (computeLog3 > computeLog) {
            computeLog = computeLog3;
        }
        final int n17 = computeLog + n11;
        Mipmap.retrieveStoreModes3D(gl, pixelStorageModes);
        boolean unpackSwapBytes = pixelStorageModes.getUnpackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n9, n10);
        int unpackRowLength;
        if (pixelStorageModes.getUnpackRowLength() > 0) {
            unpackRowLength = pixelStorageModes.getUnpackRowLength();
        }
        else {
            unpackRowLength = n3;
        }
        final int bytes_per_element = Mipmap.bytes_per_element(n10);
        final int n18 = bytes_per_element * elements_per_group;
        if (bytes_per_element == 1) {
            unpackSwapBytes = false;
        }
        int unpackImageHeight;
        if (pixelStorageModes.getUnpackImageHeight() > 0) {
            unpackImageHeight = pixelStorageModes.getUnpackImageHeight();
        }
        else {
            unpackImageHeight = n4;
        }
        int n19 = unpackRowLength * n18;
        final int n20 = n19 % pixelStorageModes.getUnpackAlignment();
        if (n20 != 0) {
            n19 += pixelStorageModes.getUnpackAlignment() - n20;
        }
        final int n21 = unpackImageHeight * n19;
        final ByteBuffer wrap = ByteBuffer.wrap(byteBuffer.array());
        final int n22 = pixelStorageModes.getUnpackSkipRows() * n19 + pixelStorageModes.getUnpackSkipPixels() * n18 + pixelStorageModes.getUnpackSkipImages() * n21;
        wrap.position(n22);
        gl.glPixelStorei(3315, 0);
        gl.glPixelStorei(3316, 0);
        gl.glPixelStorei(3314, 0);
        gl.glPixelStorei(32877, 0);
        gl.glPixelStorei(32878, 0);
        boolean b;
        int n26;
        int n27;
        ByteBuffer byteBuffer3;
        ByteBuffer byteBuffer4;
        int i;
        if (n3 == n14 && n4 == n15 && n5 == n16) {
            if (n12 <= n11 && n11 <= n13) {
                gl.getGL2().glTexImage3D(n, n11, n2, n3, n4, n5, 0, n9, n10, wrap);
            }
            if (n17 == 0) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
                gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
                return 0;
            }
            int n23 = n14 / 2;
            int n24 = n15 / 2;
            int n25 = n16 / 2;
            if (n23 < 1) {
                n23 = 1;
            }
            if (n24 < 1) {
                n24 = 1;
            }
            if (n25 < 1) {
                n25 = 1;
            }
            final int imageSize3D = Mipmap.imageSize3D(n23, n24, n25, n9, n10);
            ByteBuffer directByteBuffer = null;
            try {
                switch (n10) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        directByteBuffer = Buffers.newDirectByteBuffer(imageSize3D);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
                gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
                return 100902;
            }
            if (directByteBuffer != null) {
                switch (n10) {
                    case 5121: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractUByte(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_ubyte(elements_per_group, n3, n4, wrap, directByteBuffer, bytes_per_element, n19, n18);
                        break;
                    }
                    case 5120: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractSByte(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_byte(elements_per_group, n3, n4, wrap, directByteBuffer, bytes_per_element, n19, n18);
                        break;
                    }
                    case 5123: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractUShort(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_ushort(elements_per_group, n3, n4, wrap, directByteBuffer.asShortBuffer(), bytes_per_element, n19, n18, unpackSwapBytes);
                        break;
                    }
                    case 5122: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractSShort(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_short(elements_per_group, n3, n4, wrap, directByteBuffer.asShortBuffer(), bytes_per_element, n19, n18, unpackSwapBytes);
                        break;
                    }
                    case 5125: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractUInt(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_uint(elements_per_group, n3, n4, wrap, directByteBuffer.asIntBuffer(), bytes_per_element, n19, n18, unpackSwapBytes);
                        break;
                    }
                    case 5124: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractSInt(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_int(elements_per_group, n3, n4, wrap, directByteBuffer.asIntBuffer(), bytes_per_element, n19, n18, unpackSwapBytes);
                        break;
                    }
                    case 5126: {
                        if (n5 > 1) {
                            HalveImage.halveImage3D(elements_per_group, new ExtractFloat(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n18, n19, n21, unpackSwapBytes);
                            break;
                        }
                        HalveImage.halveImage_float(elements_per_group, n3, n4, wrap, directByteBuffer.asFloatBuffer(), bytes_per_element, n19, n18, unpackSwapBytes);
                        break;
                    }
                    case 32818: {
                        assert n9 == 6407;
                        HalveImage.halveImagePackedPixel3D(3, new Extract332(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33634: {
                        assert n9 == 6407;
                        HalveImage.halveImagePackedPixel3D(3, new Extract233rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33635: {
                        HalveImage.halveImagePackedPixel3D(3, new Extract565(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33636: {
                        HalveImage.halveImagePackedPixel3D(3, new Extract565rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 32819: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract4444(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33637: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract4444rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 32820: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract5551(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33638: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract1555rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 32821: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract8888(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33639: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract8888rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 36342: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract1010102(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    case 33640: {
                        HalveImage.halveImagePackedPixel3D(4, new Extract2101010rev(), n3, n4, n5, wrap, directByteBuffer, bytes_per_element, n19, n21, unpackSwapBytes);
                        break;
                    }
                    default: {
                        assert false;
                        break;
                    }
                }
            }
            n14 = n3 / 2;
            n15 = n4 / 2;
            n16 = n5 / 2;
            if (n14 < 1) {
                n14 = 1;
            }
            if (n15 < 1) {
                n15 = 1;
            }
            if (n16 < 1) {
                n16 = 1;
            }
            b = false;
            n26 = n14 * n18;
            n27 = n26 * n15;
            final int imageSize3D2 = Mipmap.imageSize3D(n14, n15, n16, n9, n10);
            byteBuffer3 = directByteBuffer;
            try {
                switch (n10) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        byteBuffer4 = Buffers.newDirectByteBuffer(imageSize3D2);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
                gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
                return 100902;
            }
            i = n11 + 1;
        }
        else {
            final int imageSize3D3 = Mipmap.imageSize3D(n14, n15, n16, n9, n10);
            ByteBuffer directByteBuffer2 = null;
            try {
                switch (n10) {
                    case 5120:
                    case 5121:
                    case 5122:
                    case 5123:
                    case 5124:
                    case 5125:
                    case 5126:
                    case 32818:
                    case 32819:
                    case 32820:
                    case 32821:
                    case 33634:
                    case 33635:
                    case 33636:
                    case 33637:
                    case 33638:
                    case 33639:
                    case 33640:
                    case 36342: {
                        directByteBuffer2 = Buffers.newDirectByteBuffer(imageSize3D3);
                        break;
                    }
                    default: {
                        return 100900;
                    }
                }
            }
            catch (OutOfMemoryError outOfMemoryError3) {
                gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
                gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
                return 100902;
            }
            ScaleInternal.gluScaleImage3D(gl, n9, n3, n4, n5, n10, wrap, n14, n15, n16, n10, directByteBuffer2);
            b = false;
            n26 = n14 * n18;
            n27 = n26 * n15;
            final ByteBuffer byteBuffer5 = byteBuffer2;
            byteBuffer3 = directByteBuffer2;
            byteBuffer4 = byteBuffer5;
            if (n17 != 0) {
                int n28 = n14 / 2;
                int n29 = n15 / 2;
                int n30 = n16 / 2;
                if (n28 < 1) {
                    n28 = 1;
                }
                if (n29 < 1) {
                    n29 = 1;
                }
                if (n30 < 1) {
                    n30 = 1;
                }
                final int imageSize3D4 = Mipmap.imageSize3D(n28, n29, n30, n9, n10);
                try {
                    switch (n10) {
                        case 5120:
                        case 5121:
                        case 5122:
                        case 5123:
                        case 5124:
                        case 5125:
                        case 5126:
                        case 32818:
                        case 32819:
                        case 32820:
                        case 32821:
                        case 33634:
                        case 33635:
                        case 33636:
                        case 33637:
                        case 33638:
                        case 33639:
                        case 33640:
                        case 36342: {
                            byteBuffer4 = Buffers.newDirectByteBuffer(imageSize3D4);
                            break;
                        }
                        default: {
                            return 100900;
                        }
                    }
                }
                catch (OutOfMemoryError outOfMemoryError4) {
                    gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
                    gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
                    gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
                    gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
                    gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
                    gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
                    gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
                    return 100902;
                }
            }
            i = n11;
        }
        gl.glPixelStorei(3312, 0);
        if (n12 <= i && i <= n13) {
            wrap.position(n22);
            gl.getGL2().glTexImage3D(n, i, n2, n3, n4, n5, 0, n9, n10, wrap);
        }
        ++i;
        while (i <= n17) {
            switch (n10) {
                case 5121: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractUByte(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_ubyte(elements_per_group, n3, n4, wrap, byteBuffer4, bytes_per_element, n26, n18);
                    break;
                }
                case 5120: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractSByte(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_byte(elements_per_group, n3, n4, wrap, byteBuffer4, bytes_per_element, n26, n18);
                    break;
                }
                case 5123: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractUShort(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_ushort(elements_per_group, n3, n4, wrap, byteBuffer4.asShortBuffer(), bytes_per_element, n26, n18, b);
                    break;
                }
                case 5122: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractSShort(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_short(elements_per_group, n3, n4, wrap, byteBuffer4.asShortBuffer(), bytes_per_element, n26, n18, b);
                    break;
                }
                case 5125: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractUInt(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_uint(elements_per_group, n3, n4, wrap, byteBuffer4.asIntBuffer(), bytes_per_element, n26, n18, b);
                    break;
                }
                case 5124: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractSInt(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_int(elements_per_group, n3, n4, wrap, byteBuffer4.asIntBuffer(), bytes_per_element, n26, n18, b);
                    break;
                }
                case 5126: {
                    if (n5 > 1) {
                        HalveImage.halveImage3D(elements_per_group, new ExtractFloat(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n18, n26, n27, b);
                        break;
                    }
                    HalveImage.halveImage_float(elements_per_group, n3, n4, wrap, byteBuffer4.asFloatBuffer(), bytes_per_element, n26, n18, b);
                    break;
                }
                case 32818: {
                    HalveImage.halveImagePackedPixel3D(3, new Extract332(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33634: {
                    HalveImage.halveImagePackedPixel3D(3, new Extract233rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33635: {
                    HalveImage.halveImagePackedPixel3D(3, new Extract565(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33636: {
                    HalveImage.halveImagePackedPixel3D(3, new Extract565rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 32819: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract4444(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33637: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract4444rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 32820: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract5551(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33638: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract1555rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 32821: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract8888(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33639: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract8888rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 36342: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract1010102(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                case 33640: {
                    HalveImage.halveImagePackedPixel3D(4, new Extract2101010rev(), n3, n4, n5, wrap, byteBuffer4, bytes_per_element, n26, n27, b);
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
            final ByteBuffer byteBuffer6 = byteBuffer3;
            byteBuffer3 = byteBuffer4;
            byteBuffer4 = byteBuffer6;
            if (n14 > 1) {
                n14 /= 2;
                n26 /= 2;
            }
            if (n15 > 1) {
                n15 /= 2;
                n27 = n26 * n15;
            }
            if (n16 > 1) {
                n16 /= 2;
            }
            if (n12 <= i && i <= n13) {
                wrap.position(n22);
                gl.getGL2().glTexImage3D(n, i, n2, n3, n4, n5, 0, n9, n10, wrap);
            }
            ++i;
        }
        gl.glPixelStorei(3317, pixelStorageModes.getUnpackAlignment());
        gl.glPixelStorei(3315, pixelStorageModes.getUnpackSkipRows());
        gl.glPixelStorei(3316, pixelStorageModes.getUnpackSkipPixels());
        gl.glPixelStorei(3314, pixelStorageModes.getUnpackRowLength());
        gl.glPixelStorei(3312, pixelStorageModes.getUnpackSwapBytes() ? 1 : 0);
        gl.glPixelStorei(32877, pixelStorageModes.getUnpackSkipImages());
        gl.glPixelStorei(32878, pixelStorageModes.getUnpackImageHeight());
        return 0;
    }
    
    private static void writeTargaFile(final String s, final ByteBuffer byteBuffer, final int n, final int n2) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(new File(s));
            final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(18);
            allocateDirect.put(0, (byte)0).put(1, (byte)0);
            allocateDirect.put(2, (byte)2);
            allocateDirect.put(12, (byte)(n & 0xFF));
            allocateDirect.put(13, (byte)(n >> 8));
            allocateDirect.put(14, (byte)(n2 & 0xFF));
            allocateDirect.put(15, (byte)(n2 >> 8));
            allocateDirect.put(16, (byte)24);
            fileOutputStream.write(allocateDirect.array());
            fileOutputStream.write(byteBuffer.array());
            byteBuffer.clear();
            fileOutputStream.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        DEBUG = Debug.debug("BuildMipmap");
        VERBOSE = Debug.verbose();
    }
}
