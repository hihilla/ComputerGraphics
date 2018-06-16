// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import com.jogamp.common.nio.Buffers;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GLException;
import java.nio.Buffer;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GL;

public class Mipmap
{
    public static int computeLog(int n) {
        int n2 = 0;
        if (n == 0) {
            return -1;
        }
        while ((n & 0x1) < 1) {
            n >>= 1;
            ++n2;
        }
        if (n != 1) {
            return -1;
        }
        return n2;
    }
    
    public static int nearestPower(int i) {
        int n = 1;
        if (i == 0) {
            return -1;
        }
        while (i != 1) {
            if (i == 3) {
                return n * 4;
            }
            i >>= 1;
            n *= 2;
        }
        return n;
    }
    
    public static short GLU_SWAP_2_BYTES(final short n) {
        return (short)((short)(n << 8) | (0xFF & (byte)(n >>> 8)));
    }
    
    public static int GLU_SWAP_4_BYTES(final int n) {
        return n << 24 | (0xFF0000 & n << 8) | (0xFF00 & n >>> 8) | (0xFF & n >>> 24);
    }
    
    public static float GLU_SWAP_4_BYTES(final float n) {
        return Float.intBitsToFloat(Float.floatToRawIntBits(n));
    }
    
    public static int checkMipmapArgs(final int n, final int n2, final int n3) {
        if (!legalFormat(n2) || !legalType(n3)) {
            return 100900;
        }
        if (n2 == 6401) {
            return 100900;
        }
        if (!isLegalFormatForPackedPixelType(n2, n3)) {
            return 100904;
        }
        return 0;
    }
    
    public static boolean legalFormat(final int n) {
        switch (n) {
            case 6400:
            case 6401:
            case 6402:
            case 6403:
            case 6404:
            case 6405:
            case 6406:
            case 6407:
            case 6408:
            case 6409:
            case 6410:
            case 32992:
            case 32993: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean legalType(final int n) {
        switch (n) {
            case 5120:
            case 5121:
            case 5122:
            case 5123:
            case 5124:
            case 5125:
            case 5126:
            case 6656:
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
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean isTypePackedPixel(final int n) {
        assert legalType(n);
        return n == 32818 || n == 33634 || n == 33635 || n == 33636 || n == 32819 || n == 33637 || n == 32820 || n == 33638 || n == 32821 || n == 33639 || n == 36342 || n == 33640;
    }
    
    public static boolean isLegalFormatForPackedPixelType(final int n, final int n2) {
        return isTypePackedPixel(n2) || (!((n2 == 32818 || n2 == 33634 || n2 == 33635 || n2 == 33636) & n != 6407) && ((n2 != 32819 && n2 != 33637 && n2 != 32820 && n2 != 33638 && n2 != 32821 && n2 != 33639 && n2 != 36342 && n2 != 33640) || n == 6408 || n == 32993));
    }
    
    public static boolean isLegalLevels(final int n, final int n2, final int n3, final int n4) {
        return n2 >= 0 && n2 >= n && n3 >= n2 && n4 >= n3;
    }
    
    public static void closestFit(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int[] array, final int[] array2) {
        if (gl.isGL2GL3() && gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_1) >= 0) {
            int nearestPower = nearestPower(n2);
            int nearestPower2 = nearestPower(n3);
            final int[] array3 = { 0 };
            boolean b = false;
            try {
                do {
                    final int n7 = (n2 > 1) ? (nearestPower >> 1) : nearestPower;
                    final int n8 = (n3 > 1) ? (nearestPower2 >> 1) : nearestPower2;
                    assert n7 > 0;
                    assert n8 > 0;
                    int n9;
                    if (n == 3553 || n == 32868) {
                        n9 = 32868;
                        gl.glTexImage2D(n9, 1, n4, n7, n8, 0, n5, n6, null);
                    }
                    else if (n == 34069 || n == 34070 || n == 34071 || n == 34072 || n == 34073 || n == 34074) {
                        n9 = 34075;
                        gl.glTexImage2D(n9, 1, n4, n7, n8, 0, n5, n6, null);
                    }
                    else {
                        assert n == 32867;
                        n9 = 32867;
                        gl.getGL2GL3().glTexImage1D(n9, 1, n4, n7, 0, n5, n6, null);
                    }
                    if (gl.isGL2GL3()) {
                        gl.getGL2GL3().glGetTexLevelParameteriv(n9, 1, 4096, array3, 0);
                    }
                    else {
                        array3[0] = 0;
                    }
                    if (array3[0] != 0) {
                        continue;
                    }
                    if (nearestPower == 1 && nearestPower2 == 1) {
                        b = true;
                        break;
                    }
                    nearestPower = n7;
                    nearestPower2 = n8;
                } while (array3[0] == 0);
            }
            catch (GLException ex) {
                b = true;
            }
            if (!b) {
                array[0] = nearestPower;
                array2[0] = nearestPower2;
                return;
            }
        }
        final int[] array4 = { 0 };
        gl.glGetIntegerv(3379, array4, 0);
        array[0] = nearestPower(n2);
        if (array[0] > array4[0]) {
            array[0] = array4[0];
        }
        array2[0] = nearestPower(n3);
        if (array2[0] > array4[0]) {
            array2[0] = array4[0];
        }
    }
    
    public static void closestFit3D(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int[] array, final int[] array2, final int[] array3) {
        int nearestPower = nearestPower(n2);
        int nearestPower2 = nearestPower(n3);
        int nearestPower3 = nearestPower(n4);
        final int[] array4 = { 0 };
        do {
            final int n8 = (nearestPower > 1) ? (nearestPower >> 1) : nearestPower;
            final int n9 = (nearestPower2 > 1) ? (nearestPower2 >> 1) : nearestPower2;
            final int n10 = (nearestPower3 > 1) ? (nearestPower3 >> 1) : nearestPower3;
            int n11 = 0;
            assert n8 > 0;
            assert n9 > 0;
            assert n10 > 0;
            if (n == 32879 || n == 32880) {
                n11 = 32880;
                gl.getGL2GL3().glTexImage3D(n11, 1, n5, n8, n9, n10, 0, n6, n7, null);
            }
            if (gl.isGL2GL3()) {
                gl.getGL2GL3().glGetTexLevelParameteriv(n11, 1, 4096, array4, 0);
            }
            else {
                array4[0] = 0;
            }
            if (array4[0] != 0) {
                continue;
            }
            if (nearestPower == 1 && nearestPower2 == 1 && nearestPower3 == 1) {
                final int n12 = 0;
                final int n13 = 0;
                final int n14 = 0;
                final boolean b = true;
                array3[n14] = (b ? 1 : 0);
                array[n12] = (array2[n13] = (b ? 1 : 0));
                return;
            }
            nearestPower = n8;
            nearestPower2 = n9;
            nearestPower3 = n10;
        } while (array4[0] == 0);
        array[0] = nearestPower;
        array2[0] = nearestPower2;
        array3[0] = nearestPower3;
    }
    
    public static int elements_per_group(final int n, final int n2) {
        if (n2 == 32818 || n2 == 33634 || n2 == 33635 || n2 == 33636 || n2 == 32819 || n2 == 33637 || n2 == 32820 || n2 == 33638 || n2 == 32821 || n2 == 33639 || n2 == 36342 || n2 == 33640) {
            return 1;
        }
        switch (n) {
            case 6407:
            case 32992: {
                return 3;
            }
            case 6410: {
                return 2;
            }
            case 6408:
            case 32993: {
                return 4;
            }
            default: {
                return 1;
            }
        }
    }
    
    public static int bytes_per_element(final int n) {
        switch (n) {
            case 5120:
            case 5121:
            case 6656:
            case 32818:
            case 33634: {
                return 1;
            }
            case 5122:
            case 5123:
            case 32819:
            case 32820:
            case 33635:
            case 33636:
            case 33637:
            case 33638: {
                return 2;
            }
            case 5124:
            case 5125:
            case 5126:
            case 32821:
            case 33639:
            case 33640:
            case 36342: {
                return 4;
            }
            default: {
                return 4;
            }
        }
    }
    
    public static boolean is_index(final int n) {
        return n == 6400 || n == 6401;
    }
    
    public static int image_size(final int n, final int n2, final int n3, final int n4) {
        assert n > 0;
        assert n2 > 0;
        final int elements_per_group = elements_per_group(n3, n4);
        int n5;
        if (n4 == 6656) {
            n5 = (n + 7) / 8;
        }
        else {
            n5 = bytes_per_element(n4) * n;
        }
        return n5 * n2 * elements_per_group;
    }
    
    public static int imageSize3D(final int n, final int n2, final int n3, final int n4, final int n5) {
        final int elements_per_group = elements_per_group(n4, n5);
        final int n6 = bytes_per_element(n5) * n;
        assert n > 0 && n2 > 0 && n3 > 0;
        assert n5 != 6656;
        return n6 * n2 * n3 * elements_per_group;
    }
    
    public static void retrieveStoreModes(final GL gl, final PixelStorageModes pixelStorageModes) {
        final int[] array = { 0 };
        gl.glGetIntegerv(3317, array, 0);
        pixelStorageModes.setUnpackAlignment(array[0]);
        gl.glGetIntegerv(3314, array, 0);
        pixelStorageModes.setUnpackRowLength(array[0]);
        gl.glGetIntegerv(3315, array, 0);
        pixelStorageModes.setUnpackSkipRows(array[0]);
        gl.glGetIntegerv(3316, array, 0);
        pixelStorageModes.setUnpackSkipPixels(array[0]);
        gl.glGetIntegerv(3313, array, 0);
        pixelStorageModes.setUnpackLsbFirst(array[0] == 1);
        gl.glGetIntegerv(3312, array, 0);
        pixelStorageModes.setUnpackSwapBytes(array[0] == 1);
        gl.glGetIntegerv(3333, array, 0);
        pixelStorageModes.setPackAlignment(array[0]);
        gl.glGetIntegerv(3330, array, 0);
        pixelStorageModes.setPackRowLength(array[0]);
        gl.glGetIntegerv(3331, array, 0);
        pixelStorageModes.setPackSkipRows(array[0]);
        gl.glGetIntegerv(3332, array, 0);
        pixelStorageModes.setPackSkipPixels(array[0]);
        gl.glGetIntegerv(3329, array, 0);
        pixelStorageModes.setPackLsbFirst(array[0] == 1);
        gl.glGetIntegerv(3328, array, 0);
        pixelStorageModes.setPackSwapBytes(array[0] == 1);
    }
    
    public static void retrieveStoreModes3D(final GL gl, final PixelStorageModes pixelStorageModes) {
        final int[] array = { 0 };
        gl.glGetIntegerv(3317, array, 0);
        pixelStorageModes.setUnpackAlignment(array[0]);
        gl.glGetIntegerv(3314, array, 0);
        pixelStorageModes.setUnpackRowLength(array[0]);
        gl.glGetIntegerv(3315, array, 0);
        pixelStorageModes.setUnpackSkipRows(array[0]);
        gl.glGetIntegerv(3316, array, 0);
        pixelStorageModes.setUnpackSkipPixels(array[0]);
        gl.glGetIntegerv(3313, array, 0);
        pixelStorageModes.setUnpackLsbFirst(array[0] == 1);
        gl.glGetIntegerv(3312, array, 0);
        pixelStorageModes.setUnpackSwapBytes(array[0] == 1);
        gl.glGetIntegerv(32877, array, 0);
        pixelStorageModes.setUnpackSkipImages(array[0]);
        gl.glGetIntegerv(32878, array, 0);
        pixelStorageModes.setUnpackImageHeight(array[0]);
        gl.glGetIntegerv(3333, array, 0);
        pixelStorageModes.setPackAlignment(array[0]);
        gl.glGetIntegerv(3330, array, 0);
        pixelStorageModes.setPackRowLength(array[0]);
        gl.glGetIntegerv(3331, array, 0);
        pixelStorageModes.setPackSkipRows(array[0]);
        gl.glGetIntegerv(3332, array, 0);
        pixelStorageModes.setPackSkipPixels(array[0]);
        gl.glGetIntegerv(3329, array, 0);
        pixelStorageModes.setPackLsbFirst(array[0] == 1);
        gl.glGetIntegerv(3328, array, 0);
        pixelStorageModes.setPackSwapBytes(array[0] == 1);
        gl.glGetIntegerv(32875, array, 0);
        pixelStorageModes.setPackSkipImages(array[0]);
        gl.glGetIntegerv(32876, array, 0);
        pixelStorageModes.setPackImageHeight(array[0]);
    }
    
    public static int gluScaleImage(final GL gl, final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer2) {
        final int position = byteBuffer.position();
        final int position2 = byteBuffer2.position();
        try {
            final PixelStorageModes pixelStorageModes = new PixelStorageModes();
            if (n2 == 0 || n3 == 0 || n5 == 0 || n6 == 0) {
                return 0;
            }
            if (n2 < 0 || n3 < 0 || n5 < 0 || n6 < 0) {
                return 100901;
            }
            if (!legalFormat(n) || !legalType(n4) || !legalType(n7)) {
                return 100900;
            }
            if (!isLegalFormatForPackedPixelType(n, n4)) {
                return 100904;
            }
            if (!isLegalFormatForPackedPixelType(n, n7)) {
                return 100904;
            }
            final ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(image_size(n2, n3, n, 5123));
            final ByteBuffer directByteBuffer2 = Buffers.newDirectByteBuffer(image_size(n5, n6, n, 5123));
            if (directByteBuffer == null || directByteBuffer2 == null) {
                return 100902;
            }
            retrieveStoreModes(gl, pixelStorageModes);
            Image.fill_image(pixelStorageModes, n2, n3, n, n4, is_index(n), byteBuffer, directByteBuffer.asShortBuffer());
            ScaleInternal.scale_internal(elements_per_group(n, 0), n2, n3, directByteBuffer.asShortBuffer(), n5, n6, directByteBuffer2.asShortBuffer());
            Image.empty_image(pixelStorageModes, n5, n6, n, n7, is_index(n), directByteBuffer2.asShortBuffer(), byteBuffer2);
            return 0;
        }
        finally {
            byteBuffer.position(position);
            byteBuffer2.position(position2);
        }
    }
    
    public static int gluBuild1DMipmapLevels(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        try {
            final int checkMipmapArgs = checkMipmapArgs(n2, n4, n5);
            if (checkMipmapArgs != 0) {
                return checkMipmapArgs;
            }
            if (n3 < 1) {
                return 100901;
            }
            if (!isLegalLevels(n6, n7, n8, computeLog(n3) + n6)) {
                return 100901;
            }
            return BuildMipmap.gluBuild1DMipmapLevelsCore(gl, n, n2, n3, n3, n4, n5, n6, n7, n8, byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
    
    public static int gluBuild1DMipmaps(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        try {
            final int[] array = { 0 };
            final int[] array2 = { 0 };
            final int checkMipmapArgs = checkMipmapArgs(n2, n4, n5);
            if (checkMipmapArgs != 0) {
                return checkMipmapArgs;
            }
            if (n3 < 1) {
                return 100901;
            }
            closestFit(gl, n, n3, 1, n2, n4, n5, array, array2);
            return BuildMipmap.gluBuild1DMipmapLevelsCore(gl, n, n2, n3, array[0], n4, n5, 0, 0, computeLog(array[0]), byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
    
    public static int gluBuild2DMipmapLevels(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final Object o) {
        final int checkMipmapArgs = checkMipmapArgs(n2, n5, n6);
        if (checkMipmapArgs != 0) {
            return checkMipmapArgs;
        }
        if (n3 < 1 || n4 < 1) {
            return 100901;
        }
        int computeLog = computeLog(n3);
        final int computeLog2 = computeLog(n4);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        if (!isLegalLevels(n7, n8, n9, computeLog + n7)) {
            return 100901;
        }
        ByteBuffer byteBuffer;
        if (o instanceof ByteBuffer) {
            byteBuffer = (ByteBuffer)o;
        }
        else if (o instanceof byte[]) {
            final byte[] array = (byte[])o;
            byteBuffer = ByteBuffer.allocateDirect(array.length);
            byteBuffer.put(array);
            byteBuffer.flip();
        }
        else if (o instanceof short[]) {
            final short[] array2 = (short[])o;
            byteBuffer = ByteBuffer.allocateDirect(array2.length * 2);
            byteBuffer.asShortBuffer().put(array2);
        }
        else if (o instanceof int[]) {
            final int[] array3 = (int[])o;
            byteBuffer = ByteBuffer.allocateDirect(array3.length * 4);
            byteBuffer.asIntBuffer().put(array3);
        }
        else {
            if (!(o instanceof float[])) {
                throw new IllegalArgumentException("Unhandled data type: " + o.getClass().getName());
            }
            final float[] array4 = (float[])o;
            byteBuffer = ByteBuffer.allocateDirect(array4.length * 4);
            byteBuffer.asFloatBuffer().put(array4);
        }
        final int position = byteBuffer.position();
        try {
            return BuildMipmap.gluBuild2DMipmapLevelsCore(gl, n, n2, n3, n4, n3, n4, n5, n6, n7, n8, n9, byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
    
    public static int gluBuild2DMipmaps(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Object o) {
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        final int checkMipmapArgs = checkMipmapArgs(n2, n5, n6);
        if (checkMipmapArgs != 0) {
            return checkMipmapArgs;
        }
        if (n3 < 1 || n4 < 1) {
            return 100901;
        }
        closestFit(gl, n, n3, n4, n2, n5, n6, array, array2);
        int computeLog = computeLog(array[0]);
        final int computeLog2 = computeLog(array2[0]);
        if (computeLog2 > computeLog) {
            computeLog = computeLog2;
        }
        ByteBuffer byteBuffer;
        if (o instanceof ByteBuffer) {
            byteBuffer = (ByteBuffer)o;
        }
        else if (o instanceof byte[]) {
            final byte[] array3 = (byte[])o;
            byteBuffer = ByteBuffer.allocateDirect(array3.length);
            byteBuffer.put(array3);
            byteBuffer.flip();
        }
        else if (o instanceof short[]) {
            final short[] array4 = (short[])o;
            byteBuffer = ByteBuffer.allocateDirect(array4.length * 2);
            byteBuffer.asShortBuffer().put(array4);
        }
        else if (o instanceof int[]) {
            final int[] array5 = (int[])o;
            byteBuffer = ByteBuffer.allocateDirect(array5.length * 4);
            byteBuffer.asIntBuffer().put(array5);
        }
        else {
            if (!(o instanceof float[])) {
                throw new IllegalArgumentException("Unhandled data type: " + o.getClass().getName());
            }
            final float[] array6 = (float[])o;
            byteBuffer = ByteBuffer.allocateDirect(array6.length * 4);
            byteBuffer.asFloatBuffer().put(array6);
        }
        final int position = byteBuffer.position();
        try {
            return BuildMipmap.gluBuild2DMipmapLevelsCore(gl, n, n2, n3, n4, array[0], array2[0], n5, n6, 0, 0, computeLog, byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
    
    public static int gluBuild3DMipmaps(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        try {
            final int[] array = { 0 };
            final int[] array2 = { 0 };
            final int[] array3 = { 0 };
            final int checkMipmapArgs = checkMipmapArgs(n2, n6, n7);
            if (checkMipmapArgs != 0) {
                return checkMipmapArgs;
            }
            if (n3 < 1 || n4 < 1 || n5 < 1) {
                return 100901;
            }
            if (n7 == 6656) {
                return 100900;
            }
            closestFit3D(gl, n, n3, n4, n5, n2, n6, n7, array, array2, array3);
            int computeLog = computeLog(array[0]);
            final int computeLog2 = computeLog(array2[0]);
            if (computeLog2 > computeLog) {
                computeLog = computeLog2;
            }
            final int computeLog3 = computeLog(array3[0]);
            if (computeLog3 > computeLog) {
                computeLog = computeLog3;
            }
            return BuildMipmap.gluBuild3DMipmapLevelsCore(gl, n, n2, n3, n4, n5, array[0], array2[0], array3[0], n6, n7, 0, 0, computeLog, byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
    
    public static int gluBuild3DMipmapLevels(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10, final ByteBuffer byteBuffer) {
        final int position = byteBuffer.position();
        try {
            final int checkMipmapArgs = checkMipmapArgs(n2, n6, n7);
            if (checkMipmapArgs != 0) {
                return checkMipmapArgs;
            }
            if (n3 < 1 || n4 < 1 || n5 < 1) {
                return 100901;
            }
            if (n7 == 6656) {
                return 100900;
            }
            int computeLog = computeLog(n3);
            final int computeLog2 = computeLog(n4);
            if (computeLog2 > computeLog) {
                computeLog = computeLog2;
            }
            final int computeLog3 = computeLog(n5);
            if (computeLog3 > computeLog) {
                computeLog = computeLog3;
            }
            if (!isLegalLevels(n8, n9, n10, computeLog + n8)) {
                return 100901;
            }
            return BuildMipmap.gluBuild3DMipmapLevelsCore(gl, n, n2, n3, n4, n5, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
        }
        finally {
            byteBuffer.position(position);
        }
    }
}
