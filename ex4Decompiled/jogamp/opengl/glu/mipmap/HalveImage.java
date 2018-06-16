// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class HalveImage
{
    private static final int BOX2 = 2;
    private static final int BOX4 = 4;
    private static final int BOX8 = 8;
    
    public static void halveImage(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final ShortBuffer shortBuffer2) {
        int n4 = 0;
        final int n5 = n2 / 2;
        final int n6 = n3 / 2;
        final int n7 = n2 * n;
        for (int i = 0; i < n6; ++i) {
            for (int j = 0; j < n5; ++j) {
                for (int k = 0; k < n; ++k) {
                    shortBuffer.position(n4);
                    final short value = shortBuffer.get();
                    shortBuffer.position(n4 + n);
                    final short n8 = (short)(value + shortBuffer.get());
                    shortBuffer.position(n4 + n7);
                    final short n9 = (short)(n8 + shortBuffer.get());
                    shortBuffer.position(n4 + n7 + n);
                    shortBuffer2.put((short)((short)((short)(n9 + shortBuffer.get()) + 2) / 4));
                    ++n4;
                }
                n4 += n;
            }
            n4 += n7;
        }
    }
    
    public static void halveImage_ubyte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final int n6) {
        if (n2 != 1 && n3 != 1) {
            final int n7 = n2 / 2;
            final int n8 = n3 / 2;
            int n9 = 0;
            for (int i = 0; i < n8; ++i) {
                for (int j = 0; j < n7; ++j) {
                    for (int k = 0; k < n; ++k) {
                        byteBuffer.position(n9);
                        final int n10 = 0xFF & byteBuffer.get();
                        byteBuffer.position(n9 + n6);
                        final int n11 = n10 + (0xFF & byteBuffer.get());
                        byteBuffer.position(n9 + n5);
                        final int n12 = n11 + (0xFF & byteBuffer.get());
                        byteBuffer.position(n9 + n5 + n6);
                        byteBuffer2.put((byte)((n12 + ((0xFF & byteBuffer.get()) + 2)) / 4));
                        n9 += n4;
                    }
                    n9 += n6;
                }
                n9 += n5;
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_ubyte(n, n2, n3, byteBuffer, byteBuffer2, n4, n5, n6);
    }
    
    public static void halve1Dimage_ubyte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final int n6) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n; ++j) {
                    byteBuffer.position(n9);
                    final int n11 = 0xFF & byteBuffer.get();
                    byteBuffer.position(n9 + n6);
                    byteBuffer2.put((byte)((n11 + (0xFF & byteBuffer.get())) / 2));
                    n9 += n4;
                    ++n10;
                }
                n9 += n6;
            }
            n9 += n5 - n2 * n6;
        }
        else if (n2 == 1) {
            final int n12 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    byteBuffer.position(n9);
                    final int n13 = 0xFF & byteBuffer.get();
                    byteBuffer.position(n9 + n5);
                    byteBuffer2.put((byte)((n13 + (0xFF & byteBuffer.get())) / 2));
                    n9 += n4;
                    ++n10;
                }
                n9 = n9 + n12 + n5;
            }
        }
        assert n9 == n5 * n3;
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_byte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final int n6) {
        int n7 = 0;
        if (n2 != 1 && n3 != 1) {
            final int n8 = n2 / 2;
            for (int n9 = n3 / 2, i = 0; i < n9; ++i) {
                for (int j = 0; j < n8; ++j) {
                    for (int k = 0; k < n; ++k) {
                        byteBuffer.position(n7);
                        final byte value = byteBuffer.get();
                        byteBuffer.position(n7 + n6);
                        final byte b = (byte)(value + byteBuffer.get());
                        byteBuffer.position(n7 + n5);
                        final byte b2 = (byte)(b + byteBuffer.get());
                        byteBuffer.position(n7 + n5 + n6);
                        byteBuffer2.put((byte)((byte)((byte)(b2 + byteBuffer.get()) + 2) / 4));
                        n7 += n4;
                    }
                    n7 += n6;
                }
                n7 += n5;
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_byte(n, n2, n3, byteBuffer, byteBuffer2, n4, n5, n6);
    }
    
    public static void halve1Dimage_byte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final int n6) {
        int n7 = n2 / 2;
        int n8 = n2 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n; ++j) {
                    byteBuffer.position(n9);
                    final byte value = byteBuffer.get();
                    byteBuffer.position(n9 + n6);
                    byteBuffer2.put((byte)((byte)(value + byteBuffer.get()) / 2));
                    n9 += n4;
                    ++n10;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    byteBuffer.position(n9);
                    final byte value2 = byteBuffer.get();
                    byteBuffer.position(n9 + n5);
                    final byte b = (byte)((byte)(value2 + byteBuffer.get()) / 2);
                    n9 += n4;
                    ++n10;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_ushort(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = 0;
        if (n2 != 1 && n3 != 1) {
            final int n8 = n2 / 2;
            final int n9 = n3 / 2;
            if (!b) {
                for (int i = 0; i < n9; ++i) {
                    for (int j = 0; j < n8; ++j) {
                        for (int k = 0; k < n; ++k) {
                            byteBuffer.position(n7);
                            final int n10 = 0xFFFF & byteBuffer.getShort();
                            byteBuffer.position(n7 + n6);
                            final int n11 = n10 + (0xFFFF & byteBuffer.getShort());
                            byteBuffer.position(n7 + n5);
                            final int n12 = n11 + (0xFFFF & byteBuffer.getShort());
                            byteBuffer.position(n7 + n5 + n6);
                            shortBuffer.put((short)((n12 + (0xFFFF & byteBuffer.getShort()) + 2) / 4));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            else {
                for (int l = 0; l < n9; ++l) {
                    for (int n13 = 0; n13 < n8; ++n13) {
                        for (int n14 = 0; n14 < n; ++n14) {
                            byteBuffer.position(n7);
                            final int n15 = 0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            byteBuffer.position(n7 + n6);
                            final int n16 = n15 + (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                            byteBuffer.position(n7 + n5);
                            final int n17 = n16 + (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                            byteBuffer.position(n7 + n5 + n6);
                            shortBuffer.put((short)((n17 + (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) + 2) / 4));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_ushort(n, n2, n3, byteBuffer, shortBuffer, n4, n5, n6, b);
    }
    
    public static void halve1Dimage_ushort(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n8; ++j) {
                    final int[] array = new int[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array[0] = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                        byteBuffer.position(n9 + n6);
                        array[1] = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array[0] = (0xFFFF & byteBuffer.getShort());
                        byteBuffer.position(n9 + n6);
                        array[1] = (0xFFFF & byteBuffer.getShort());
                    }
                    shortBuffer.put((short)((array[0] + array[1]) / 2));
                    n9 += n4;
                    n10 += 2;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    final int[] array2 = new int[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array2[0] = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                        byteBuffer.position(n9 + n5);
                        array2[0] = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array2[0] = (0xFFFF & byteBuffer.getShort());
                        byteBuffer.position(n9 + n5);
                        array2[1] = (0xFFFF & byteBuffer.getShort());
                    }
                    shortBuffer.put((short)((array2[0] + array2[1]) / 2));
                    n9 += n4;
                    n10 += 2;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_short(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = 0;
        if (n2 != 1 && n3 != 1) {
            final int n8 = n2 / 2;
            final int n9 = n3 / 2;
            if (!b) {
                for (int i = 0; i < n9; ++i) {
                    for (int j = 0; j < n8; ++j) {
                        for (int k = 0; k < n; ++k) {
                            byteBuffer.position(n7);
                            final short short1 = byteBuffer.getShort();
                            byteBuffer.position(n7 + n6);
                            final short n10 = (short)(short1 + byteBuffer.getShort());
                            byteBuffer.position(n7 + n5);
                            final short n11 = (short)(n10 + byteBuffer.getShort());
                            byteBuffer.position(n7 + n5 + n6);
                            shortBuffer.put((short)((short)((short)(n11 + byteBuffer.getShort()) + 2) / 4));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            else {
                for (int l = 0; l < n9; ++l) {
                    for (int n12 = 0; n12 < n8; ++n12) {
                        for (int n13 = 0; n13 < n; ++n13) {
                            byteBuffer.position(n7);
                            final short glu_SWAP_2_BYTES = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            byteBuffer.position(n7 + n6);
                            final short n14 = (short)(glu_SWAP_2_BYTES + Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                            byteBuffer.position(n7 + n5);
                            final short n15 = (short)(n14 + Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                            byteBuffer.position(n7 + n5 + n6);
                            shortBuffer.put((short)((short)((short)(n15 + Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) + 2) / 4));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_short(n, n2, n3, byteBuffer, shortBuffer, n4, n5, n6, b);
    }
    
    public static void halve1Dimage_short(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final ShortBuffer shortBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n; ++j) {
                    final short[] array = new short[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array[0] = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                        byteBuffer.position(n9 + n6);
                        array[1] = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                    }
                    else {
                        byteBuffer.position(n9);
                        array[0] = byteBuffer.getShort();
                        byteBuffer.position(n9 + n6);
                        array[1] = byteBuffer.getShort();
                    }
                    shortBuffer.put((short)((array[0] + array[1]) / 2));
                    n9 += n4;
                    n10 += 2;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    final short[] array2 = new short[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array2[0] = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                        byteBuffer.position(n9 + n5);
                        array2[1] = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                    }
                    else {
                        byteBuffer.position(n9);
                        array2[0] = byteBuffer.getShort();
                        byteBuffer.position(n9 + n5);
                        array2[1] = byteBuffer.getShort();
                    }
                    shortBuffer.put((short)((array2[0] + array2[1]) / 2));
                    n9 += n4;
                    n10 += 2;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_uint(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = 0;
        double n8 = 0.0;
        if (n2 != 1 && n3 != 1) {
            final int n9 = n2 / 2;
            final int n10 = n3 / 2;
            if (!b) {
                for (int i = 0; i < n10; ++i) {
                    for (int j = 0; j < n9; ++j) {
                        for (int k = 0; k < n; ++k) {
                            byteBuffer.position(n7);
                            final double n11 = 0x7FFFFFFFL & byteBuffer.getInt();
                            byteBuffer.position(n7 + n6);
                            final double n12 = n11 + (0x7FFFFFFFL & byteBuffer.getInt());
                            byteBuffer.position(n7 + n5);
                            final double n13 = n12 + (0x7FFFFFFFL & byteBuffer.getInt());
                            byteBuffer.position(n7 + n5 + n6);
                            intBuffer.put((int)((n13 + (0x7FFFFFFFL & byteBuffer.getInt())) / 4.0 + 0.5));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            else {
                for (int l = 0; l < n10; ++l) {
                    for (int n14 = 0; n14 < n9; ++n14) {
                        for (int n15 = 0; n15 < n; ++n15) {
                            byteBuffer.position(n7);
                            final double n16 = -1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            byteBuffer.position(n7 + n6);
                            final double n17 = n16 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            byteBuffer.position(n7 + n5);
                            final double n18 = n17 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            byteBuffer.position(n7 + n5 + n6);
                            final double n19 = n18 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            n8 = n8 / 4.0 + 0.5;
                            intBuffer.put((int)n8);
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_uint(n, n2, n3, byteBuffer, intBuffer, n4, n5, n6, b);
    }
    
    public static void halve1Dimage_uint(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n8; ++j) {
                    final long[] array = new long[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array[0] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                        byteBuffer.position(n9 + n6);
                        array[1] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array[0] = (-1 & byteBuffer.getInt());
                        byteBuffer.position(n9 + n6);
                        array[1] = (-1 & byteBuffer.getInt());
                    }
                    intBuffer.put((int)((array[0] + array[1]) / 2.0));
                    n9 += n4;
                    n10 += 4;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    final long[] array2 = new long[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array2[0] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                        byteBuffer.position(n9 + n6);
                        array2[0] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array2[0] = (-1 & byteBuffer.getInt());
                        byteBuffer.position(n9 + n5);
                        array2[1] = (-1 & byteBuffer.getInt());
                    }
                    intBuffer.put((int)((array2[0] + array2[1]) / 2.0));
                    n9 += n4;
                    n10 += 4;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_int(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = 0;
        if (n2 != 1 && n3 != 1) {
            final int n8 = n2 / 2;
            final int n9 = n3 / 2;
            if (!b) {
                for (int i = 0; i < n9; ++i) {
                    for (int j = 0; j < n8; ++j) {
                        for (int k = 0; k < n; ++k) {
                            byteBuffer.position(n7);
                            final int int1 = byteBuffer.getInt();
                            byteBuffer.position(n7 + n6);
                            final int n10 = int1 + byteBuffer.getInt();
                            byteBuffer.position(n7 + n5);
                            final int n11 = n10 + byteBuffer.getInt();
                            byteBuffer.position(n7 + n5 + n6);
                            intBuffer.put((int)((n11 + byteBuffer.getInt()) / 4.0f + 0.5f));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            else {
                for (int l = 0; l < n9; ++l) {
                    for (int n12 = 0; n12 < n8; ++n12) {
                        for (int n13 = 0; n13 < n; ++n13) {
                            byteBuffer.position(n7);
                            final float n14 = -1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            byteBuffer.position(n7 + n6);
                            final float n15 = n14 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            byteBuffer.position(n7 + n5);
                            final float n16 = n15 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            byteBuffer.position(n7 + n5 + n6);
                            intBuffer.put((int)((n16 + (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()))) / 4.0f + 0.5f));
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_int(n, n2, n3, byteBuffer, intBuffer, n4, n5, n6, b);
    }
    
    public static void halve1Dimage_int(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final IntBuffer intBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n; ++j) {
                    final long[] array = new long[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array[0] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                        byteBuffer.position(n9 + n6);
                        array[1] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array[0] = (-1 & byteBuffer.getInt());
                        byteBuffer.position(n9 + n6);
                        array[1] = (-1 & byteBuffer.getInt());
                    }
                    intBuffer.put((int)((array[0] + array[1]) / 2.0f));
                    n9 += n4;
                    n10 += 4;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    final long[] array2 = new long[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array2[0] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                        byteBuffer.position(n9 + n5);
                        array2[1] = (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                    }
                    else {
                        byteBuffer.position(n9);
                        array2[0] = (-1 & byteBuffer.getInt());
                        byteBuffer.position(n9 + n5);
                        array2[1] = (-1 & byteBuffer.getInt());
                    }
                    intBuffer.put((int)((array2[0] + array2[1]) / 2.0f));
                    n9 += n4;
                    n10 += 4;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImage_float(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = 0;
        if (n2 != 1 && n3 != 1) {
            final int n8 = n2 / 2;
            final int n9 = n3 / 2;
            if (!b) {
                for (int i = 0; i < n9; ++i) {
                    for (int j = 0; j < n8; ++j) {
                        for (int k = 0; k < n; ++k) {
                            byteBuffer.position(n7);
                            final float float1 = byteBuffer.getFloat();
                            byteBuffer.position(n7 + n6);
                            final float n10 = float1 + byteBuffer.getFloat();
                            byteBuffer.position(n7 + n5);
                            final float n11 = n10 + byteBuffer.getFloat();
                            byteBuffer.position(n7 + n5 + n6);
                            floatBuffer.put(n11 / 4.0f);
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            else {
                for (int l = 0; l < n9; ++l) {
                    for (int n12 = 0; n12 < n8; ++n12) {
                        for (int n13 = 0; n13 < n; ++n13) {
                            byteBuffer.position(n7);
                            final float glu_SWAP_4_BYTES = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            byteBuffer.position(n7 + n6);
                            final float n14 = glu_SWAP_4_BYTES + Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            byteBuffer.position(n7 + n5);
                            final float n15 = n14 + Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            byteBuffer.position(n7 + n5 + n6);
                            floatBuffer.put((n15 + Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat())) / 4.0f);
                            n7 += n4;
                        }
                        n7 += n6;
                    }
                    n7 += n5;
                }
            }
            return;
        }
        assert n3 != 1;
        halve1Dimage_float(n, n2, n3, byteBuffer, floatBuffer, n4, n5, n6, b);
    }
    
    public static void halve1Dimage_float(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final FloatBuffer floatBuffer, final int n4, final int n5, final int n6, final boolean b) {
        int n7 = n2 / 2;
        int n8 = n3 / 2;
        int n9 = 0;
        int n10 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            assert n2 != 1;
            n8 = 1;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n; ++j) {
                    final float[] array = new float[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array[0] = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                        byteBuffer.position(n9 + n6);
                        array[1] = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                    }
                    else {
                        byteBuffer.position(n9);
                        array[0] = byteBuffer.getFloat();
                        byteBuffer.position(n9 + n6);
                        array[1] = byteBuffer.getFloat();
                    }
                    floatBuffer.put((array[0] + array[1]) / 2.0f);
                    n9 += n4;
                    n10 += 4;
                }
                n9 += n6;
            }
        }
        else if (n2 == 1) {
            final int n11 = n5 - n2 * n6;
            assert n3 != 1;
            n7 = 1;
            for (int k = 0; k < n8; ++k) {
                for (int l = 0; l < n; ++l) {
                    final float[] array2 = new float[2];
                    if (b) {
                        byteBuffer.position(n9);
                        array2[0] = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                        byteBuffer.position(n9 + n5);
                        array2[1] = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                    }
                    else {
                        byteBuffer.position(n9);
                        array2[0] = byteBuffer.getFloat();
                        byteBuffer.position(n9 + n5);
                        array2[1] = byteBuffer.getFloat();
                    }
                    floatBuffer.put((array2[0] + array2[1]) / 2.0f);
                    n9 += n4;
                    n10 += 4;
                }
                n9 = n9 + n11 + n5;
            }
            assert n9 == n5 * n3;
        }
        assert n10 == n * n4 * n7 * n8;
    }
    
    public static void halveImagePackedPixel(final int n, final Extract extract, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final boolean b) {
        if (n2 == 1 || n3 == 1) {
            assert n3 != 1;
            halve1DimagePackedPixel(n, extract, n2, n3, byteBuffer, byteBuffer2, n4, n5, b);
        }
        else {
            final int n6 = n2 / 2;
            final int n7 = n3 / 2;
            int n8 = 0;
            final int n9 = n5 - n2 * n4;
            int n10 = 0;
            for (int i = 0; i < n7; ++i) {
                for (int j = 0; j < n6; ++j) {
                    final float[] array = new float[4];
                    final float[][] array2 = new float[4][4];
                    byteBuffer.position(n8);
                    extract.extract(b, byteBuffer, array2[0]);
                    byteBuffer.position(n8 + n4);
                    extract.extract(b, byteBuffer, array2[1]);
                    byteBuffer.position(n8 + n5);
                    extract.extract(b, byteBuffer, array2[2]);
                    byteBuffer.position(n8 + n5 + n4);
                    extract.extract(b, byteBuffer, array2[3]);
                    for (int k = 0; k < n; ++k) {
                        array[k] = 0.0f;
                        for (int l = 0; l < 4; ++l) {
                            final float[] array3 = array;
                            final int n11 = k;
                            array3[n11] += array2[l][k];
                        }
                        final float[] array4 = array;
                        final int n12 = k;
                        array4[n12] /= 4.0f;
                    }
                    extract.shove(array, n10, byteBuffer2);
                    ++n10;
                    n8 += n4 + n4;
                }
                n8 = n8 + n9 + n5;
            }
            assert n8 == n5 * n3;
            assert n10 == n6 * n7;
        }
    }
    
    public static void halve1DimagePackedPixel(final int n, final Extract extract, final int n2, final int n3, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n4, final int n5, final boolean b) {
        final int n6 = n2 / 2;
        final int n7 = n3 / 2;
        int n8 = 0;
        assert n3 == 1;
        assert n2 != n3;
        if (n3 == 1) {
            int n9 = 0;
            assert n2 != 1;
            final int n10 = 1;
            for (int i = 0; i < n6; ++i) {
                final float[] array = new float[4];
                final float[][] array2 = new float[2][4];
                byteBuffer.position(n8);
                extract.extract(b, byteBuffer, array2[0]);
                byteBuffer.position(n8 + n4);
                extract.extract(b, byteBuffer, array2[1]);
                for (int j = 0; j < n; ++j) {
                    array[j] = 0.0f;
                    for (int k = 0; k < 2; ++k) {
                        final float[] array3 = array;
                        final int n11 = j;
                        array3[n11] += array2[k][j];
                    }
                    final float[] array4 = array;
                    final int n12 = j;
                    array4[n12] /= 2.0f;
                }
                extract.shove(array, n9, byteBuffer2);
                ++n9;
                n8 += n4 + n4;
            }
            final int n13 = n8 + (n5 - n2 * n4);
            assert n13 == n5;
            assert n9 == n6 * n10;
        }
        else if (n2 == 1) {
            int n14 = 0;
            assert n3 != 1;
            final int n15 = 1;
            for (int l = 0; l < n7; ++l) {
                final float[] array5 = new float[4];
                final float[][] array6 = new float[2][4];
                byteBuffer.position(n8);
                extract.extract(b, byteBuffer, array6[0]);
                byteBuffer.position(n8 + n5);
                extract.extract(b, byteBuffer, array6[1]);
                for (int n16 = 0; n16 < n; ++n16) {
                    array5[n16] = 0.0f;
                    for (int n17 = 0; n17 < 2; ++n17) {
                        final float[] array7 = array5;
                        final int n18 = n16;
                        array7[n18] += array6[n17][n16];
                    }
                    final float[] array8 = array5;
                    final int n19 = n16;
                    array8[n19] /= 2.0f;
                }
                extract.shove(array5, n14, byteBuffer2);
                ++n14;
                n8 += n5 + n5;
            }
            assert n8 == n5;
            assert n14 == n15 * n7;
        }
    }
    
    public static void halveImagePackedPixelSlice(final int n, final Extract extract, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n5, final int n6, final int n7, final boolean b) {
        final int n8 = n2 / 2;
        final int n9 = n4 / 2;
        int n10 = 0;
        int n11 = 0;
        assert (n2 == 1 || n3 == 1) && n4 >= 2;
        if (n2 == n3) {
            assert n2 == 1 && n3 == 1;
            assert n4 >= 2;
            for (int i = 0; i < n9; ++i) {
                final float[] array = new float[4];
                final float[][] array2 = new float[2][4];
                byteBuffer.position(n10);
                extract.extract(b, byteBuffer, array2[0]);
                byteBuffer.position(n10 + n7);
                extract.extract(b, byteBuffer, array2[1]);
                for (int j = 0; j < n; ++j) {
                    array[j] = 0.0f;
                    for (int k = 0; k < 2; ++k) {
                        final float[] array3 = array;
                        final int n12 = j;
                        array3[n12] += array2[k][j];
                    }
                    final float[] array4 = array;
                    final int n13 = j;
                    array4[n13] /= 2.0f;
                }
                extract.shove(array, n11, byteBuffer2);
                ++n11;
                n10 += n7 + n7;
            }
        }
        else if (n3 == 1) {
            assert n2 != 1;
            for (int l = 0; l < n9; ++l) {
                for (int n14 = 0; n14 < n8; ++n14) {
                    final float[] array5 = new float[4];
                    final float[][] array6 = new float[4][4];
                    byteBuffer.position(n10);
                    extract.extract(b, byteBuffer, array6[0]);
                    byteBuffer.position(n10 + n5);
                    extract.extract(b, byteBuffer, array6[1]);
                    byteBuffer.position(n10 + n7);
                    extract.extract(b, byteBuffer, array6[2]);
                    byteBuffer.position(n10 + n5 + n7);
                    extract.extract(b, byteBuffer, array6[3]);
                    for (int n15 = 0; n15 < n; ++n15) {
                        array5[n15] = 0.0f;
                        for (int n16 = 0; n16 < 4; ++n16) {
                            final float[] array7 = array5;
                            final int n17 = n15;
                            array7[n17] += array6[n16][n15];
                        }
                        final float[] array8 = array5;
                        final int n18 = n15;
                        array8[n18] /= 4.0f;
                    }
                    extract.shove(array5, n11, byteBuffer2);
                    ++n11;
                    n10 += n7 + n7;
                }
            }
        }
        else if (n2 == 1) {
            assert n3 != 1;
            for (int n19 = 0; n19 < n9; ++n19) {
                for (int n20 = 0; n20 < n8; ++n20) {
                    final float[] array9 = new float[4];
                    final float[][] array10 = new float[4][4];
                    byteBuffer.position(n10);
                    extract.extract(b, byteBuffer, array10[0]);
                    byteBuffer.position(n10 + n6);
                    extract.extract(b, byteBuffer, array10[1]);
                    byteBuffer.position(n10 + n7);
                    extract.extract(b, byteBuffer, array10[2]);
                    byteBuffer.position(n10 + n6 + n7);
                    extract.extract(b, byteBuffer, array10[3]);
                    for (int n21 = 0; n21 < n; ++n21) {
                        array9[n21] = 0.0f;
                        for (int n22 = 0; n22 < 4; ++n22) {
                            final float[] array11 = array9;
                            final int n23 = n21;
                            array11[n23] += array10[n22][n21];
                        }
                        final float[] array12 = array9;
                        final int n24 = n21;
                        array12[n24] /= 4.0f;
                    }
                    extract.shove(array9, n11, byteBuffer2);
                    ++n11;
                    n10 += n7 + n7;
                }
            }
        }
    }
    
    public static void halveImageSlice(final int n, final ExtractPrimitive extractPrimitive, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        final int n9 = n2 / 2;
        final int n10 = n3 / 2;
        final int n11 = n4 / 2;
        int n12 = 0;
        final int n13 = n7 - n2 * n6;
        int n14 = 0;
        assert (n2 == 1 || n3 == 1) && n4 >= 2;
        if (n2 == n3) {
            assert n2 == 1 && n3 == 1;
            assert n4 >= 2;
            for (int i = 0; i < n11; ++i) {
                for (int j = 0; j < n; ++j) {
                    final double[] array = new double[4];
                    final double[][] array2 = new double[2][4];
                    byteBuffer.position(n12);
                    array2[0][j] = extractPrimitive.extract(b, byteBuffer);
                    byteBuffer.position(n12 + n8);
                    array2[1][j] = extractPrimitive.extract(b, byteBuffer);
                    array[j] = 0.0;
                    for (int k = 0; k < 2; ++k) {
                        final double[] array3 = array;
                        final int n15 = j;
                        array3[n15] += array2[k][j];
                    }
                    final double[] array4 = array;
                    final int n16 = j;
                    array4[n16] /= 2.0;
                    extractPrimitive.shove(array[j], n14, byteBuffer2);
                    ++n14;
                    n12 += n5;
                }
                n12 += n7;
            }
            assert n12 == n7 * n3 * n4;
            assert n14 == n11 * n;
        }
        else if (n3 == 1) {
            assert n2 != 1;
            for (int l = 0; l < n11; ++l) {
                for (int n17 = 0; n17 < n9; ++n17) {
                    for (int n18 = 0; n18 < n; ++n18) {
                        final double[] array5 = new double[4];
                        final double[][] array6 = new double[4][4];
                        byteBuffer.position(n12);
                        array6[0][n18] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n6);
                        array6[1][n18] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n8);
                        array6[2][n18] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n8 + n6);
                        array6[3][n18] = extractPrimitive.extract(b, byteBuffer);
                        array5[n18] = 0.0;
                        for (int n19 = 0; n19 < 4; ++n19) {
                            final double[] array7 = array5;
                            final int n20 = n18;
                            array7[n20] += array6[n19][n18];
                        }
                        final double[] array8 = array5;
                        final int n21 = n18;
                        array8[n21] /= 4.0;
                        extractPrimitive.shove(array5[n18], n14, byteBuffer2);
                        ++n14;
                        n12 += n5;
                    }
                    n12 += n5;
                }
                n12 = n12 + n13 + n7;
            }
            assert n12 == n7 * n3 * n4;
            assert n14 == n9 * n11 * n;
        }
        else if (n2 == 1) {
            assert n3 != 1;
            for (int n22 = 0; n22 < n11; ++n22) {
                for (int n23 = 0; n23 < n10; ++n23) {
                    for (int n24 = 0; n24 < n; ++n24) {
                        final double[] array9 = new double[4];
                        final double[][] array10 = new double[4][4];
                        byteBuffer.position(n12);
                        array10[0][n24] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n7);
                        array10[1][n24] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n8);
                        array10[2][n24] = extractPrimitive.extract(b, byteBuffer);
                        byteBuffer.position(n12 + n8 + n6);
                        array10[3][n24] = extractPrimitive.extract(b, byteBuffer);
                        array9[n24] = 0.0;
                        for (int n25 = 0; n25 < 4; ++n25) {
                            final double[] array11 = array9;
                            final int n26 = n24;
                            array11[n26] += array10[n25][n24];
                        }
                        final double[] array12 = array9;
                        final int n27 = n24;
                        array12[n27] /= 4.0;
                        extractPrimitive.shove(array9[n24], n14, byteBuffer2);
                        ++n14;
                        n12 += n5;
                    }
                    n12 = n12 + n13 + n7;
                }
                n12 += n8;
            }
            assert n12 == n7 * n3 * n4;
            assert n14 == n9 * n11 * n;
        }
    }
    
    public static void halveImage3D(final int n, final ExtractPrimitive extractPrimitive, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        assert n4 > 1;
        if (n2 == 1 || n3 == 1) {
            assert 1 <= n4;
            halveImageSlice(n, extractPrimitive, n2, n3, n4, byteBuffer, byteBuffer2, n5, n6, n7, n8, b);
        }
        else {
            final int n9 = n2 / 2;
            final int n10 = n3 / 2;
            final int n11 = n4 / 2;
            int n12 = 0;
            final int n13 = n7 - n2 * n6;
            int n14 = 0;
            for (int i = 0; i < n11; ++i) {
                for (int j = 0; j < n10; ++j) {
                    for (int k = 0; k < n9; ++k) {
                        for (int l = 0; l < n; ++l) {
                            final double[] array = new double[4];
                            final double[][] array2 = new double[8][4];
                            byteBuffer.position(n12);
                            array2[0][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n6);
                            array2[1][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n7);
                            array2[2][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n7 + n6);
                            array2[3][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n8);
                            array2[4][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n6 + n8);
                            array2[5][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n7 + n8);
                            array2[6][l] = extractPrimitive.extract(b, byteBuffer);
                            byteBuffer.position(n12 + n7 + n8 + n6);
                            array2[7][l] = extractPrimitive.extract(b, byteBuffer);
                            array[l] = 0.0;
                            for (int n15 = 0; n15 < 8; ++n15) {
                                final double[] array3 = array;
                                final int n16 = l;
                                array3[n16] += array2[n15][l];
                            }
                            final double[] array4 = array;
                            final int n17 = l;
                            array4[n17] /= 8.0;
                            extractPrimitive.shove(array[l], n14, byteBuffer2);
                            ++n14;
                            n12 += n5;
                        }
                        n12 += n6;
                    }
                    n12 = n12 + n13 + n7;
                }
                n12 += n8;
            }
            assert n12 == n7 * n3 * n4;
            assert n14 == n9 * n10 * n11 * n;
        }
    }
    
    public static void halveImagePackedPixel3D(final int n, final Extract extract, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2, final int n5, final int n6, final int n7, final boolean b) {
        if (n4 == 1) {
            assert 1 <= n2 && 1 <= n3;
            halveImagePackedPixel(n, extract, n2, n3, byteBuffer, byteBuffer2, n5, n6, b);
        }
        else if (n2 == 1 || n3 == 1) {
            assert 1 <= n4;
            halveImagePackedPixelSlice(n, extract, n2, n3, n4, byteBuffer, byteBuffer2, n5, n6, n7, b);
        }
        else {
            final int n8 = n2 / 2;
            final int n9 = n3 / 2;
            final int n10 = n4 / 2;
            int n11 = 0;
            final int n12 = n6 - n2 * n5;
            int n13 = 0;
            for (int i = 0; i < n10; ++i) {
                for (int j = 0; j < n9; ++j) {
                    for (int k = 0; k < n8; ++k) {
                        final float[] array = new float[4];
                        final float[][] array2 = new float[8][4];
                        byteBuffer.position(n11);
                        extract.extract(b, byteBuffer, array2[0]);
                        byteBuffer.position(n11 + n5);
                        extract.extract(b, byteBuffer, array2[1]);
                        byteBuffer.position(n11 + n6);
                        extract.extract(b, byteBuffer, array2[2]);
                        byteBuffer.position(n11 + n6 + n5);
                        extract.extract(b, byteBuffer, array2[3]);
                        byteBuffer.position(n11 + n7);
                        extract.extract(b, byteBuffer, array2[4]);
                        byteBuffer.position(n11 + n5 + n7);
                        extract.extract(b, byteBuffer, array2[5]);
                        byteBuffer.position(n11 + n6 + n7);
                        extract.extract(b, byteBuffer, array2[6]);
                        byteBuffer.position(n11 + n6 + n5 + n7);
                        extract.extract(b, byteBuffer, array2[7]);
                        for (int l = 0; l < n; ++l) {
                            array[l] = 0.0f;
                            for (int n14 = 0; n14 < 8; ++n14) {
                                final float[] array3 = array;
                                final int n15 = l;
                                array3[n15] += array2[n14][l];
                            }
                            final float[] array4 = array;
                            final int n16 = l;
                            array4[n16] /= 8.0f;
                        }
                        extract.shove(array, n13, byteBuffer2);
                        ++n13;
                        n11 += n5 + n5;
                    }
                    n11 = n11 + n12 + n6;
                }
                n11 += n7;
            }
            assert n11 == n6 * n3 * n4;
            assert n13 == n8 * n9 * n10;
        }
    }
}
