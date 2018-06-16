// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ShortBuffer;
import java.nio.ByteBuffer;

public class Image
{
    public static short getShortFromByteArray(final byte[] array, final int n) {
        return (short)((short)(array[n] << 8) | (short)(0xFF & array[n + 1]));
    }
    
    public static int getIntFromByteArray(final byte[] array, final int n) {
        return (array[n] << 24 & 0xFF000000) | (array[n + 1] << 16 & 0xFF0000) | (array[n + 2] << 8 & 0xFF00) | (array[n + 3] & 0xFF);
    }
    
    public static float getFloatFromByteArray(final byte[] array, final int n) {
        return Float.intBitsToFloat(getIntFromByteArray(array, n));
    }
    
    public static void fill_image(final PixelStorageModes pixelStorageModes, final int n, final int n2, final int n3, final int n4, final boolean b, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        int n5 = 0;
        Extract extract = null;
        switch (n4) {
            case 32818: {
                extract = new Extract332();
                break;
            }
            case 33634: {
                extract = new Extract233rev();
                break;
            }
            case 33635: {
                extract = new Extract565();
                break;
            }
            case 33636: {
                extract = new Extract565rev();
                break;
            }
            case 32819: {
                extract = new Extract4444();
                break;
            }
            case 33637: {
                extract = new Extract4444rev();
                break;
            }
            case 32820: {
                extract = new Extract5551();
                break;
            }
            case 33638: {
                extract = new Extract1555rev();
                break;
            }
            case 32821: {
                extract = new Extract8888();
                break;
            }
            case 33639: {
                extract = new Extract8888rev();
                break;
            }
            case 36342: {
                extract = new Extract1010102();
                break;
            }
            case 33640: {
                extract = new Extract2101010rev();
                break;
            }
        }
        boolean unpackSwapBytes = pixelStorageModes.getUnpackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n3, n4);
        int unpackRowLength;
        if (pixelStorageModes.getUnpackRowLength() > 0) {
            unpackRowLength = pixelStorageModes.getUnpackRowLength();
        }
        else {
            unpackRowLength = n;
        }
        if (n4 == 6656) {
            int n6 = (unpackRowLength * elements_per_group + 7) / 8;
            final int n7 = n6 % pixelStorageModes.getUnpackAlignment();
            if (n7 != 0) {
                n6 += pixelStorageModes.getUnpackAlignment() - n7;
            }
            int n8 = pixelStorageModes.getUnpackSkipRows() * n6 + pixelStorageModes.getUnpackSkipPixels() * elements_per_group / 8;
            final int n9 = n * elements_per_group;
            int n10 = 0;
            for (int i = 0; i < n2; ++i) {
                int n11 = n8;
                byteBuffer.position(n11);
                int n12 = pixelStorageModes.getUnpackSkipPixels() * elements_per_group % 8;
                for (int j = 0; j < n9; ++j) {
                    int n13;
                    if (pixelStorageModes.getUnpackLsbFirst()) {
                        byteBuffer.position(n11);
                        n13 = (byteBuffer.get() & 0xFF & 1 << n12);
                    }
                    else {
                        n13 = (byteBuffer.get() & 0xFF & 1 << 7 - n12);
                    }
                    if (n13 != 0) {
                        if (b) {
                            shortBuffer.position(n10);
                            shortBuffer.put((short)1);
                        }
                        else {
                            shortBuffer.position(n10);
                            shortBuffer.put((short)(-1));
                        }
                    }
                    else {
                        shortBuffer.position(n10);
                        shortBuffer.put((short)0);
                    }
                    if (++n12 == 8) {
                        n12 = 0;
                        ++n11;
                    }
                    ++n10;
                }
                n8 += n6;
            }
        }
        else {
            final int bytes_per_element = Mipmap.bytes_per_element(n4);
            final int n14 = bytes_per_element * elements_per_group;
            if (bytes_per_element == 1) {
                unpackSwapBytes = false;
            }
            int n15 = unpackRowLength * n14;
            final int n16 = n15 % pixelStorageModes.getUnpackAlignment();
            if (n16 != 0) {
                n15 += pixelStorageModes.getUnpackAlignment() - n16;
            }
            int n17 = pixelStorageModes.getUnpackSkipRows() * n15 + pixelStorageModes.getUnpackSkipPixels() * n14;
            final int n18 = n * elements_per_group;
            int n19 = 0;
            for (int k = 0; k < n2; ++k) {
                int n20 = n17;
                byteBuffer.position(n20);
                for (int l = 0; l < n18; ++l) {
                    final Type_Widget type_Widget = new Type_Widget();
                    final float[] array = new float[4];
                    byteBuffer.position(n20);
                    switch (n4) {
                        case 32818: {
                            extract.extract(false, byteBuffer, array);
                            for (int n21 = 0; n21 < 3; ++n21) {
                                shortBuffer.put(n19++, (short)(array[n21] * 65535.0f));
                            }
                            break;
                        }
                        case 33634: {
                            extract.extract(false, byteBuffer, array);
                            for (int n22 = 0; n22 < 3; ++n22) {
                                shortBuffer.put(n19++, (short)(array[n22] * 65535.0f));
                            }
                            break;
                        }
                        case 5121: {
                            if (b) {
                                shortBuffer.put(n19++, (short)(0xFF & byteBuffer.get()));
                                break;
                            }
                            shortBuffer.put(n19++, (short)(0xFF & byteBuffer.get() * 257));
                            break;
                        }
                        case 5120: {
                            if (b) {
                                shortBuffer.put(n19++, byteBuffer.get());
                                break;
                            }
                            shortBuffer.put(n19++, (short)(byteBuffer.get() * 516));
                            break;
                        }
                        case 33635: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n23 = 0; n23 < 3; ++n23) {
                                shortBuffer.put(n19++, (short)(array[n23] * 65535.0f));
                            }
                            break;
                        }
                        case 33636: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n24 = 0; n24 < 3; ++n24) {
                                shortBuffer.put(n19++, (short)(array[n24] * 65535.0f));
                            }
                            break;
                        }
                        case 32819: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n25 = 0; n25 < 4; ++n25) {
                                shortBuffer.put(n19++, (short)(array[n25] * 65535.0f));
                            }
                            break;
                        }
                        case 33637: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n26 = 0; n26 < 4; ++n26) {
                                shortBuffer.put(n19++, (short)(array[n26] * 65535.0f));
                            }
                            break;
                        }
                        case 32820: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n27 = 0; n27 < 4; ++n27) {
                                shortBuffer.put(n19++, (short)(array[n27] * 65535.0f));
                            }
                            break;
                        }
                        case 33638: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n28 = 0; n28 < 4; ++n28) {
                                shortBuffer.put(n19++, (short)(array[n28] * 65535.0f));
                            }
                            break;
                        }
                        case 5122:
                        case 5123: {
                            if (unpackSwapBytes) {
                                type_Widget.setUB1(byteBuffer.get());
                                type_Widget.setUB0(byteBuffer.get());
                            }
                            else {
                                type_Widget.setUB0(byteBuffer.get());
                                type_Widget.setUB1(byteBuffer.get());
                            }
                            if (n4 != 5122) {
                                shortBuffer.put(n19++, type_Widget.getUS0());
                                break;
                            }
                            if (b) {
                                shortBuffer.put(n19++, type_Widget.getS0());
                                break;
                            }
                            shortBuffer.put(n19++, (short)(type_Widget.getS0() * 2));
                            break;
                        }
                        case 32821: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n29 = 0; n29 < 4; ++n29) {
                                shortBuffer.put(n19++, (short)(array[n29] * 65535.0f));
                            }
                            break;
                        }
                        case 33639: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n30 = 0; n30 < 4; ++n30) {
                                shortBuffer.put(n19++, (short)(array[n30] * 65535.0f));
                            }
                            break;
                        }
                        case 36342: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n31 = 0; n31 < 4; ++n31) {
                                shortBuffer.put(n19++, (short)(array[n31] * 65535.0f));
                            }
                            break;
                        }
                        case 33640: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n32 = 0; n32 < 4; ++n32) {
                                shortBuffer.put(n19++, (short)(array[n32] * 65535.0f));
                            }
                            break;
                        }
                        case 5124:
                        case 5125:
                        case 5126: {
                            if (unpackSwapBytes) {
                                type_Widget.setUB3(byteBuffer.get());
                                type_Widget.setUB2(byteBuffer.get());
                                type_Widget.setUB1(byteBuffer.get());
                                type_Widget.setUB0(byteBuffer.get());
                            }
                            else {
                                type_Widget.setUB0(byteBuffer.get());
                                type_Widget.setUB1(byteBuffer.get());
                                type_Widget.setUB2(byteBuffer.get());
                                type_Widget.setUB3(byteBuffer.get());
                            }
                            if (n4 == 5126) {
                                if (b) {
                                    shortBuffer.put(n19++, (short)type_Widget.getF());
                                    break;
                                }
                                shortBuffer.put(n19++, (short)(type_Widget.getF() * 65535.0f));
                                break;
                            }
                            else if (n4 == 5125) {
                                if (b) {
                                    shortBuffer.put(n19++, (short)type_Widget.getUI());
                                    break;
                                }
                                shortBuffer.put(n19++, (short)(type_Widget.getUI() >> 16));
                                break;
                            }
                            else {
                                if (b) {
                                    shortBuffer.put(n19++, (short)type_Widget.getI());
                                    break;
                                }
                                shortBuffer.put(n19++, (short)(type_Widget.getI() >> 15));
                                break;
                            }
                            break;
                        }
                    }
                    n20 += bytes_per_element;
                }
                n17 = (n5 = n17 + n15);
            }
            if (!Mipmap.isTypePackedPixel(n4)) {
                assert n19 == n * n2 * elements_per_group;
            }
            else {
                assert n19 == n * n2 * Mipmap.elements_per_group(n3, 0);
            }
            assert n5 == n15 * n2 + pixelStorageModes.getUnpackSkipRows() * n15 + pixelStorageModes.getUnpackSkipPixels() * n14;
        }
    }
    
    public static void empty_image(final PixelStorageModes pixelStorageModes, final int n, final int n2, final int n3, final int n4, final boolean b, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        int n5 = 0;
        Extract extract = null;
        switch (n4) {
            case 32818: {
                extract = new Extract332();
                break;
            }
            case 33634: {
                extract = new Extract233rev();
                break;
            }
            case 33635: {
                extract = new Extract565();
                break;
            }
            case 33636: {
                extract = new Extract565rev();
                break;
            }
            case 32819: {
                extract = new Extract4444();
                break;
            }
            case 33637: {
                extract = new Extract4444rev();
                break;
            }
            case 32820: {
                extract = new Extract5551();
                break;
            }
            case 33638: {
                extract = new Extract1555rev();
                break;
            }
            case 32821: {
                extract = new Extract8888();
                break;
            }
            case 33639: {
                extract = new Extract8888rev();
                break;
            }
            case 36342: {
                extract = new Extract1010102();
                break;
            }
            case 33640: {
                extract = new Extract2101010rev();
                break;
            }
        }
        boolean packSwapBytes = pixelStorageModes.getPackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n3, n4);
        int packRowLength;
        if (pixelStorageModes.getPackRowLength() > 0) {
            packRowLength = pixelStorageModes.getPackRowLength();
        }
        else {
            packRowLength = n;
        }
        if (n4 == 6656) {
            int n6 = (packRowLength * elements_per_group + 7) / 8;
            final int n7 = n6 % pixelStorageModes.getPackAlignment();
            if (n7 != 0) {
                n6 += pixelStorageModes.getPackAlignment() - n7;
            }
            int n8 = pixelStorageModes.getPackSkipRows() * n6 + pixelStorageModes.getPackSkipPixels() * elements_per_group / 8;
            final int n9 = n * elements_per_group;
            int n10 = 0;
            for (int i = 0; i < n2; ++i) {
                int n11 = n8;
                int n12 = pixelStorageModes.getPackSkipPixels() * elements_per_group % 8;
                for (int j = 0; j < n9; ++j) {
                    int n13;
                    if (b) {
                        n13 = (shortBuffer.get(n10) & 0x1);
                    }
                    else if (shortBuffer.get(n10) < 0) {
                        n13 = 1;
                    }
                    else {
                        n13 = 0;
                    }
                    if (n13 != 0) {
                        if (pixelStorageModes.getPackLsbFirst()) {
                            byteBuffer.put(n11, (byte)(byteBuffer.get(n11) | 1 << n12));
                        }
                        else {
                            byteBuffer.put(n11, (byte)(byteBuffer.get(n11) | 7 - n12));
                        }
                    }
                    else if (pixelStorageModes.getPackLsbFirst()) {
                        byteBuffer.put(n11, (byte)(byteBuffer.get(n11) & ~(1 << n12)));
                    }
                    else {
                        byteBuffer.put(n11, (byte)(byteBuffer.get(n11) & ~(7 - n12)));
                    }
                    if (++n12 == 8) {
                        n12 = 0;
                        ++n11;
                    }
                    ++n10;
                }
                n8 += n6;
            }
        }
        else {
            final float[] array = new float[4];
            final int bytes_per_element = Mipmap.bytes_per_element(n4);
            final int n14 = bytes_per_element * elements_per_group;
            if (bytes_per_element == 1) {
                packSwapBytes = false;
            }
            int n15 = packRowLength * n14;
            final int n16 = n15 % pixelStorageModes.getPackAlignment();
            if (n16 != 0) {
                n15 += pixelStorageModes.getPackAlignment() - n16;
            }
            int n17 = pixelStorageModes.getPackSkipRows() * n15 + pixelStorageModes.getPackSkipPixels() * n14;
            final int n18 = n * elements_per_group;
            int n19 = 0;
            for (int k = 0; k < n2; ++k) {
                int n20 = n17;
                for (int l = 0; l < n18; ++l) {
                    final Type_Widget type_Widget = new Type_Widget();
                    switch (n4) {
                        case 32818: {
                            for (int n21 = 0; n21 < 3; ++n21) {
                                array[n21] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, byteBuffer);
                            break;
                        }
                        case 33634: {
                            for (int n22 = 0; n22 < 3; ++n22) {
                                array[n22] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, byteBuffer);
                            break;
                        }
                        case 5121: {
                            if (b) {
                                byteBuffer.put(n20, (byte)shortBuffer.get(n19++));
                                break;
                            }
                            byteBuffer.put(n20, (byte)shortBuffer.get(n19++));
                            break;
                        }
                        case 5120: {
                            if (b) {
                                byteBuffer.put(n20, (byte)shortBuffer.get(n19++));
                                break;
                            }
                            byteBuffer.put(n20, (byte)shortBuffer.get(n19++));
                            break;
                        }
                        case 33635: {
                            for (int n23 = 0; n23 < 3; ++n23) {
                                array[n23] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 33636: {
                            for (int n24 = 0; n24 < 3; ++n24) {
                                array[n24] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20, type_Widget.getUB1());
                            break;
                        }
                        case 32819: {
                            for (int n25 = 0; n25 < 4; ++n25) {
                                array[n25] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 33637: {
                            for (int n26 = 0; n26 < 4; ++n26) {
                                array[n26] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 32820: {
                            for (int n27 = 0; n27 < 4; ++n27) {
                                array[n27] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 33638: {
                            for (int n28 = 0; n28 < 4; ++n28) {
                                array[n28] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 5122:
                        case 5123: {
                            if (n4 == 5122) {
                                if (b) {
                                    type_Widget.setS0(shortBuffer.get(n19++));
                                }
                                else {
                                    type_Widget.setS0((short)(shortBuffer.get(n19++) >> 1));
                                }
                            }
                            else {
                                type_Widget.setUS0(shortBuffer.get(n19++));
                            }
                            if (packSwapBytes) {
                                byteBuffer.put(n20, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 32821: {
                            for (int n29 = 0; n29 < 4; ++n29) {
                                array[n29] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20 + 3, type_Widget.getUB0());
                                byteBuffer.put(n20 + 2, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB2());
                                byteBuffer.put(n20, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n20, type_Widget.getUI());
                            break;
                        }
                        case 33639: {
                            for (int n30 = 0; n30 < 4; ++n30) {
                                array[n30] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20 + 3, type_Widget.getUB0());
                                byteBuffer.put(n20 + 2, type_Widget.getUB1());
                                byteBuffer.put(n20 + 2, type_Widget.getUB2());
                                byteBuffer.put(n20, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n20, type_Widget.getUI());
                            break;
                        }
                        case 36342: {
                            for (int n31 = 0; n31 < 4; ++n31) {
                                array[n31] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20 + 3, type_Widget.getUB0());
                                byteBuffer.put(n20 + 2, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB2());
                                byteBuffer.put(n20, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n20, type_Widget.getUI());
                            break;
                        }
                        case 33640: {
                            for (int n32 = 0; n32 < 4; ++n32) {
                                array[n32] = shortBuffer.get(n19++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n20 + 3, type_Widget.getUB0());
                                byteBuffer.put(n20 + 2, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB2());
                                byteBuffer.put(n20, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n20, type_Widget.getUI());
                            break;
                        }
                        case 5124:
                        case 5125:
                        case 5126: {
                            if (n4 == 5126) {
                                if (b) {
                                    type_Widget.setF(shortBuffer.get(n19++));
                                }
                                else {
                                    type_Widget.setF(shortBuffer.get(n19++) / 65535.0f);
                                }
                            }
                            else if (n4 == 5125) {
                                if (b) {
                                    type_Widget.setUI(shortBuffer.get(n19++));
                                }
                                else {
                                    type_Widget.setUI(shortBuffer.get(n19++) * 65537);
                                }
                            }
                            else if (b) {
                                type_Widget.setI(shortBuffer.get(n19++));
                            }
                            else {
                                type_Widget.setI(shortBuffer.get(n19++) * 65537 / 2);
                            }
                            if (packSwapBytes) {
                                byteBuffer.put(n20 + 3, type_Widget.getUB0());
                                byteBuffer.put(n20 + 2, type_Widget.getUB1());
                                byteBuffer.put(n20 + 1, type_Widget.getUB2());
                                byteBuffer.put(n20, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.put(n20, type_Widget.getUB0());
                            byteBuffer.put(n20 + 1, type_Widget.getUB1());
                            byteBuffer.put(n20 + 2, type_Widget.getUB2());
                            byteBuffer.put(n20 + 3, type_Widget.getUB3());
                            break;
                        }
                    }
                    n20 += bytes_per_element;
                }
                n17 = (n5 = n17 + n15);
            }
            if (!Mipmap.isTypePackedPixel(n4)) {
                assert n19 == n * n2 * elements_per_group;
            }
            else {
                assert n19 == n * n2 * Mipmap.elements_per_group(n3, 0);
            }
            assert n5 == n15 * n2 + pixelStorageModes.getPackSkipRows() * n15 + pixelStorageModes.getPackSkipPixels() * n14;
        }
    }
    
    public static void fillImage3D(final PixelStorageModes pixelStorageModes, final int n, final int n2, final int n3, final int n4, final int n5, final boolean b, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer) {
        int n6 = 0;
        final Type_Widget type_Widget = new Type_Widget();
        final float[] array = new float[4];
        Extract extract = null;
        switch (n5) {
            case 32818: {
                extract = new Extract332();
                break;
            }
            case 33634: {
                extract = new Extract233rev();
                break;
            }
            case 33635: {
                extract = new Extract565();
                break;
            }
            case 33636: {
                extract = new Extract565rev();
                break;
            }
            case 32819: {
                extract = new Extract4444();
                break;
            }
            case 33637: {
                extract = new Extract4444rev();
                break;
            }
            case 32820: {
                extract = new Extract5551();
                break;
            }
            case 33638: {
                extract = new Extract1555rev();
                break;
            }
            case 32821: {
                extract = new Extract8888();
                break;
            }
            case 33639: {
                extract = new Extract8888rev();
                break;
            }
            case 36342: {
                extract = new Extract1010102();
                break;
            }
            case 33640: {
                extract = new Extract2101010rev();
                break;
            }
        }
        boolean unpackSwapBytes = pixelStorageModes.getUnpackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n4, n5);
        int unpackRowLength;
        if (pixelStorageModes.getUnpackRowLength() > 0) {
            unpackRowLength = pixelStorageModes.getUnpackRowLength();
        }
        else {
            unpackRowLength = n;
        }
        final int bytes_per_element = Mipmap.bytes_per_element(n5);
        final int n7 = bytes_per_element * elements_per_group;
        if (bytes_per_element == 1) {
            unpackSwapBytes = false;
        }
        int unpackImageHeight;
        if (pixelStorageModes.getUnpackImageHeight() > 0) {
            unpackImageHeight = pixelStorageModes.getUnpackImageHeight();
        }
        else {
            unpackImageHeight = n2;
        }
        int n8 = unpackRowLength * n7;
        final int n9 = n8 % pixelStorageModes.getUnpackAlignment();
        if (n9 != 0) {
            n8 += pixelStorageModes.getUnpackAlignment() - n9;
        }
        final int n10 = unpackImageHeight * n8;
        int n11 = pixelStorageModes.getUnpackSkipRows() * n8 + pixelStorageModes.getUnpackSkipPixels() * n7 + pixelStorageModes.getUnpackSkipImages() * n10;
        final int n12 = n * elements_per_group;
        int n13 = 0;
        for (int i = 0; i < n3; ++i) {
            int n14 = n11;
            for (int j = 0; j < n2; ++j) {
                int n15 = n14;
                for (int k = 0; k < n12; ++k) {
                    switch (n5) {
                        case 5121: {
                            if (b) {
                                shortBuffer.put(n13++, (short)(0xFF & byteBuffer.get(n15)));
                                break;
                            }
                            shortBuffer.put(n13++, (short)((0xFF & byteBuffer.get(n15)) * 257));
                            break;
                        }
                        case 5120: {
                            if (b) {
                                shortBuffer.put(n13++, byteBuffer.get(n15));
                                break;
                            }
                            shortBuffer.put(n13++, (short)(byteBuffer.get(n15) * 516));
                            break;
                        }
                        case 32818: {
                            byteBuffer.position(n15);
                            extract.extract(false, byteBuffer, array);
                            for (int l = 0; l < 3; ++l) {
                                shortBuffer.put(n13++, (short)(array[l] * 65535.0f));
                            }
                            break;
                        }
                        case 33634: {
                            byteBuffer.position(n15);
                            extract.extract(false, byteBuffer, array);
                            for (int n16 = 0; n16 < 3; ++n16) {
                                shortBuffer.put(n13++, (short)(array[n16] * 65535.0f));
                            }
                            break;
                        }
                        case 33635: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n17 = 0; n17 < 4; ++n17) {
                                shortBuffer.put(n13++, (short)(array[n17] * 65535.0f));
                            }
                            break;
                        }
                        case 33636: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n18 = 0; n18 < 4; ++n18) {
                                shortBuffer.put(n13++, (short)(array[n18] * 65535.0f));
                            }
                            break;
                        }
                        case 32819: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n19 = 0; n19 < 4; ++n19) {
                                shortBuffer.put(n13++, (short)(array[n19] * 65535.0f));
                            }
                            break;
                        }
                        case 33637: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n20 = 0; n20 < 4; ++n20) {
                                shortBuffer.put(n13++, (short)(array[n20] * 65535.0f));
                            }
                            break;
                        }
                        case 32820: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n21 = 0; n21 < 4; ++n21) {
                                shortBuffer.put(n13++, (short)(array[n21] * 65535.0f));
                            }
                            break;
                        }
                        case 33638: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n22 = 0; n22 < 4; ++n22) {
                                shortBuffer.put(n13++, (short)(array[n22] * 65535.0f));
                            }
                            break;
                        }
                        case 5122:
                        case 5123: {
                            if (unpackSwapBytes) {
                                type_Widget.setUB0(byteBuffer.get(n15 + 1));
                                type_Widget.setUB1(byteBuffer.get(n15));
                            }
                            else {
                                type_Widget.setUB0(byteBuffer.get(n15));
                                type_Widget.setUB1(byteBuffer.get(n15 + 1));
                            }
                            if (n5 != 5122) {
                                shortBuffer.put(n13++, type_Widget.getUS0());
                                break;
                            }
                            if (b) {
                                shortBuffer.put(n13++, type_Widget.getUS0());
                                break;
                            }
                            shortBuffer.put(n13++, (short)(type_Widget.getUS0() * 2));
                            break;
                        }
                        case 32821: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n23 = 0; n23 < 4; ++n23) {
                                shortBuffer.put(n13++, (short)(array[n23] * 65535.0f));
                            }
                            break;
                        }
                        case 33639: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n24 = 0; n24 < 4; ++n24) {
                                shortBuffer.put(n13++, (short)(array[n24] * 65535.0f));
                            }
                            break;
                        }
                        case 36342: {
                            byteBuffer.position(n15);
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n25 = 0; n25 < 4; ++n25) {
                                shortBuffer.put(n13++, (short)(array[n25] * 65535.0f));
                            }
                            break;
                        }
                        case 33640: {
                            extract.extract(unpackSwapBytes, byteBuffer, array);
                            for (int n26 = 0; n26 < 4; ++n26) {
                                shortBuffer.put(n13++, (short)(array[n26] * 65535.0f));
                            }
                            break;
                        }
                        case 5124:
                        case 5125:
                        case 5126: {
                            if (unpackSwapBytes) {
                                type_Widget.setUB0(byteBuffer.get(n15 + 3));
                                type_Widget.setUB1(byteBuffer.get(n15 + 2));
                                type_Widget.setUB2(byteBuffer.get(n15 + 1));
                                type_Widget.setUB3(byteBuffer.get(n15));
                            }
                            else {
                                type_Widget.setUB0(byteBuffer.get(n15));
                                type_Widget.setUB1(byteBuffer.get(n15 + 1));
                                type_Widget.setUB2(byteBuffer.get(n15 + 2));
                                type_Widget.setUB3(byteBuffer.get(n15 + 3));
                            }
                            if (n5 == 5126) {
                                if (b) {
                                    shortBuffer.put(n13++, (short)type_Widget.getF());
                                    break;
                                }
                                shortBuffer.put(n13++, (short)(type_Widget.getF() * 65535.0f));
                                break;
                            }
                            else if (n5 == 5125) {
                                if (b) {
                                    shortBuffer.put(n13++, (short)type_Widget.getUI());
                                    break;
                                }
                                shortBuffer.put(n13++, (short)(type_Widget.getUI() >> 16));
                                break;
                            }
                            else {
                                if (b) {
                                    shortBuffer.put(n13++, (short)type_Widget.getI());
                                    break;
                                }
                                shortBuffer.put(n13++, (short)(type_Widget.getI() >> 15));
                                break;
                            }
                            break;
                        }
                        default: {
                            assert false;
                            break;
                        }
                    }
                    n15 += bytes_per_element;
                }
                n14 = (n6 = n14 + n8);
            }
            n11 += n10;
        }
        if (!Mipmap.isTypePackedPixel(n5)) {
            assert n13 == n * n2 * n3 * elements_per_group;
        }
        else {
            assert n13 == n * n2 * n3 * Mipmap.elements_per_group(n4, 0);
        }
        assert n6 == n8 * n2 * n3 + pixelStorageModes.getUnpackSkipRows() * n8 + pixelStorageModes.getUnpackSkipPixels() * n7 + pixelStorageModes.getUnpackSkipImages() * n10;
    }
    
    public static void emptyImage3D(final PixelStorageModes pixelStorageModes, final int n, final int n2, final int n3, final int n4, final int n5, final boolean b, final ShortBuffer shortBuffer, final ByteBuffer byteBuffer) {
        final Type_Widget type_Widget = new Type_Widget();
        final float[] array = new float[4];
        Extract extract = null;
        switch (n5) {
            case 32818: {
                extract = new Extract332();
                break;
            }
            case 33634: {
                extract = new Extract233rev();
                break;
            }
            case 33635: {
                extract = new Extract565();
                break;
            }
            case 33636: {
                extract = new Extract565rev();
                break;
            }
            case 32819: {
                extract = new Extract4444();
                break;
            }
            case 33637: {
                extract = new Extract4444rev();
                break;
            }
            case 32820: {
                extract = new Extract5551();
                break;
            }
            case 33638: {
                extract = new Extract1555rev();
                break;
            }
            case 32821: {
                extract = new Extract8888();
                break;
            }
            case 33639: {
                extract = new Extract8888rev();
                break;
            }
            case 36342: {
                extract = new Extract1010102();
                break;
            }
            case 33640: {
                extract = new Extract2101010rev();
                break;
            }
        }
        int n6 = 0;
        boolean packSwapBytes = pixelStorageModes.getPackSwapBytes();
        final int elements_per_group = Mipmap.elements_per_group(n4, n5);
        int packRowLength;
        if (pixelStorageModes.getPackRowLength() > 0) {
            packRowLength = pixelStorageModes.getPackRowLength();
        }
        else {
            packRowLength = n;
        }
        final int bytes_per_element = Mipmap.bytes_per_element(n5);
        final int n7 = bytes_per_element * elements_per_group;
        if (bytes_per_element == 1) {
            packSwapBytes = false;
        }
        int packImageHeight;
        if (pixelStorageModes.getPackImageHeight() > 0) {
            packImageHeight = pixelStorageModes.getPackImageHeight();
        }
        else {
            packImageHeight = n2;
        }
        int n8 = packRowLength * n7;
        final int n9 = n8 % pixelStorageModes.getPackAlignment();
        if (n9 != 0) {
            n8 += pixelStorageModes.getPackAlignment() - n9;
        }
        final int n10 = packImageHeight * n8;
        int n11 = pixelStorageModes.getPackSkipRows() * n8 + pixelStorageModes.getPackSkipPixels() * n7 + pixelStorageModes.getPackSkipImages() * n10;
        final int n12 = n * elements_per_group;
        int n13 = 0;
        for (int i = 0; i < n3; ++i) {
            int n14 = n11;
            for (int j = 0; j < n2; ++j) {
                n6 = n14;
                for (int k = 0; k < n12; ++k) {
                    switch (n5) {
                        case 5121: {
                            if (b) {
                                byteBuffer.put(n6, (byte)shortBuffer.get(n13++));
                                break;
                            }
                            byteBuffer.put(n6, (byte)(shortBuffer.get(n13++) >> 8));
                            break;
                        }
                        case 5120: {
                            if (b) {
                                byteBuffer.put(n6, (byte)shortBuffer.get(n13++));
                                break;
                            }
                            byteBuffer.put(n6, (byte)(shortBuffer.get(n13++) >> 9));
                            break;
                        }
                        case 32818: {
                            for (int l = 0; l < 3; ++l) {
                                array[l] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, byteBuffer);
                            break;
                        }
                        case 33634: {
                            for (int n15 = 0; n15 < 3; ++n15) {
                                array[n15] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, byteBuffer);
                            break;
                        }
                        case 33635: {
                            for (int n16 = 0; n16 < 4; ++n16) {
                                array[n16] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.putShort(n6, type_Widget.getUB1());
                                byteBuffer.putShort(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 33636: {
                            for (int n17 = 0; n17 < 4; ++n17) {
                                array[n17] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 32819: {
                            for (int n18 = 0; n18 < 4; ++n18) {
                                array[n18] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 33637: {
                            for (int n19 = 0; n19 < 4; ++n19) {
                                array[n19] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 32820: {
                            for (int n20 = 0; n20 < 4; ++n20) {
                                array[n20] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 33638: {
                            for (int n21 = 0; n21 < 4; ++n21) {
                                array[n21] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putShort(n6, type_Widget.getUS0());
                            break;
                        }
                        case 5122:
                        case 5123: {
                            if (n5 == 5122) {
                                if (b) {
                                    type_Widget.setS0(shortBuffer.get(n13++));
                                }
                                else {
                                    type_Widget.setS0((short)(shortBuffer.get(n13++) >> 1));
                                }
                            }
                            else {
                                type_Widget.setUS0(shortBuffer.get(n13++));
                            }
                            if (packSwapBytes) {
                                byteBuffer.put(n6, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.put(n6, type_Widget.getUB0());
                            byteBuffer.put(n6 + 1, type_Widget.getUB1());
                            break;
                        }
                        case 32821: {
                            for (int n22 = 0; n22 < 4; ++n22) {
                                array[n22] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6 + 3, type_Widget.getUB0());
                                byteBuffer.put(n6 + 2, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB2());
                                byteBuffer.put(n6, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n6, type_Widget.getUI());
                            break;
                        }
                        case 33639: {
                            for (int n23 = 0; n23 < 4; ++n23) {
                                array[n23] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6 + 3, type_Widget.getUB0());
                                byteBuffer.put(n6 + 2, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB2());
                                byteBuffer.put(n6, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n6, type_Widget.getUI());
                            break;
                        }
                        case 36342: {
                            for (int n24 = 0; n24 < 4; ++n24) {
                                array[n24] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6 + 3, type_Widget.getUB0());
                                byteBuffer.put(n6 + 2, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB2());
                                byteBuffer.put(n6, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.putInt(n6, type_Widget.getUI());
                            break;
                        }
                        case 33640: {
                            for (int n25 = 0; n25 < 4; ++n25) {
                                array[n25] = shortBuffer.get(n13++) / 65535.0f;
                            }
                            extract.shove(array, 0, type_Widget.getBuffer());
                            if (packSwapBytes) {
                                byteBuffer.put(n6 + 3, type_Widget.getUB0());
                                byteBuffer.put(n6 + 2, type_Widget.getUB2());
                                byteBuffer.put(n6 + 1, type_Widget.getUB1());
                                byteBuffer.put(n6, type_Widget.getUB0());
                                break;
                            }
                            byteBuffer.putInt(n6, type_Widget.getUI());
                            break;
                        }
                        case 5124:
                        case 5125:
                        case 5126: {
                            if (n5 == 5126) {
                                if (b) {
                                    type_Widget.setF(shortBuffer.get(n13++));
                                }
                                else {
                                    type_Widget.setF(shortBuffer.get(n13++) / 65535.0f);
                                }
                            }
                            else if (n5 == 5125) {
                                if (b) {
                                    type_Widget.setUI(shortBuffer.get(n13++));
                                }
                                else {
                                    type_Widget.setUI(shortBuffer.get(n13++) * 65537);
                                }
                            }
                            else if (b) {
                                type_Widget.setI(shortBuffer.get(n13++));
                            }
                            else {
                                type_Widget.setI(shortBuffer.get(n13++) * 65535 / 2);
                            }
                            if (packSwapBytes) {
                                byteBuffer.put(n6 + 3, type_Widget.getUB0());
                                byteBuffer.put(n6 + 2, type_Widget.getUB1());
                                byteBuffer.put(n6 + 1, type_Widget.getUB2());
                                byteBuffer.put(n6, type_Widget.getUB3());
                                break;
                            }
                            byteBuffer.put(n6, type_Widget.getUB0());
                            byteBuffer.put(n6 + 1, type_Widget.getUB1());
                            byteBuffer.put(n6 + 2, type_Widget.getUB2());
                            byteBuffer.put(n6 + 3, type_Widget.getUB3());
                            break;
                        }
                        default: {
                            assert false;
                            break;
                        }
                    }
                    n6 += bytes_per_element;
                }
                n14 += n8;
            }
            n11 += n10;
        }
        if (!Mipmap.isTypePackedPixel(n5)) {
            assert n13 == n * n2 * n3 * elements_per_group;
        }
        else {
            assert n13 == n * n2 * n3 * Mipmap.elements_per_group(n4, 0);
        }
        assert n6 == n8 * n2 * n3 + pixelStorageModes.getUnpackSkipRows() * n8 + pixelStorageModes.getUnpackSkipPixels() * n7 + pixelStorageModes.getUnpackSkipImages() * n10;
    }
}
