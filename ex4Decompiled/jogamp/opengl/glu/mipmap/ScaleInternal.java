// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class ScaleInternal
{
    public static final float UINT_MAX = -1.0f;
    
    public static void scale_internal(final int n, final int n2, final int n3, final ShortBuffer shortBuffer, final int n4, final int n5, final ShortBuffer shortBuffer2) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage(n, n2, n3, shortBuffer, shortBuffer2);
            return;
        }
        final float n6 = n3 / n5;
        final float n7 = n2 / n4;
        final float n8 = n7 / 2.0f;
        final float n9 = n6 / 2.0f;
        for (int i = 0; i < n5; ++i) {
            final float n10 = n6 * (i + 0.5f);
            float n11;
            float n12;
            if (n3 > n5) {
                n11 = n10 + n9;
                n12 = n10 - n9;
            }
            else {
                n11 = n10 + 0.5f;
                n12 = n10 - 0.5f;
            }
            for (int j = 0; j < n4; ++j) {
                final float n13 = n7 * (j + 0.5f);
                float n14;
                float n15;
                if (n2 > n4) {
                    n14 = n13 + n8;
                    n15 = n13 - n8;
                }
                else {
                    n14 = n13 + 0.5f;
                    n15 = n13 - 0.5f;
                }
                final float[] array2 = array;
                final int n16 = 0;
                final float[] array3 = array;
                final int n17 = 1;
                final float[] array4 = array;
                final int n18 = 2;
                final float[] array5 = array;
                final int n19 = 3;
                final float n20 = 0.0f;
                array4[n18] = (array5[n19] = n20);
                array2[n16] = (array3[n17] = n20);
                float n21 = 0.0f;
                float n22 = n12;
                for (int n23 = (int)Math.floor(n22); n22 < n11; n22 = ++n23) {
                    final int n24 = (n23 + n3) % n3;
                    float n25;
                    if (n11 < n23 + 1) {
                        n25 = n11 - n22;
                    }
                    else {
                        n25 = n23 + 1 - n22;
                    }
                    float n26 = n15;
                    for (int n27 = (int)Math.floor(n26); n26 < n14; n26 = ++n27) {
                        final int n28 = (n27 + n2) % n2;
                        float n29;
                        if (n14 < n27 + 1) {
                            n29 = n14 - n26;
                        }
                        else {
                            n29 = n27 + 1 - n26;
                        }
                        final float n30 = n29 * n25;
                        n21 += n30;
                        final int n31 = (n28 + n24 * n2) * n;
                        for (int k = 0; k < n; ++k) {
                            final float[] array6 = array;
                            final int n32 = k;
                            array6[n32] += shortBuffer.get(n31 + k) * n30;
                        }
                    }
                }
                final int n33 = (j + i * n4) * n;
                for (int l = 0; l < n; ++l) {
                    shortBuffer2.put(n33 + l, (short)((array[l] + 0.5f) / n21));
                }
            }
        }
    }
    
    public static void scale_internal_ubyte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final ByteBuffer byteBuffer2, final int n6, final int n7, final int n8) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_ubyte(n, n2, n3, byteBuffer, byteBuffer2, n6, n7, n8);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        final float[] array6 = array;
                        final int n34 = k;
                        array6[n34] += (0xFF & byteBuffer.get()) * n32;
                    }
                    int n35 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n36 = 0, n37 = n31; n36 < n; ++n36, n37 += n6) {
                            byteBuffer.position(n37);
                            final float[] array7 = array;
                            final int n38 = n36;
                            array7[n38] += (0xFF & byteBuffer.get()) * n30;
                        }
                    }
                    int n40;
                    final int n39 = n40 = n31 + n8;
                    final float n41 = n30 * n23;
                    for (int n42 = 0, n43 = n39; n42 < n; ++n42, n43 += n6) {
                        byteBuffer.position(n43);
                        final float[] array8 = array;
                        final int n44 = n42;
                        array8[n44] += (0xFF & byteBuffer.get()) * n41;
                    }
                    final float n45 = n19;
                    final float n46 = n45 * (1.0f - n21);
                    int n47 = n29 + n18 * n7;
                    for (int n48 = 0, n49 = n47; n48 < n; ++n48, n49 += n6) {
                        byteBuffer.position(n49);
                        final float[] array9 = array;
                        final int n50 = n48;
                        array9[n50] += (0xFF & byteBuffer.get()) * n46;
                    }
                    for (int n51 = n20 + 1; n51 < n22; ++n51) {
                        n47 += n8;
                        for (int n52 = 0, n53 = n47; n52 < n; ++n52, n53 += n6) {
                            byteBuffer.position(n53);
                            final float[] array10 = array;
                            final int n54 = n52;
                            array10[n54] += (0xFF & byteBuffer.get()) * n45;
                        }
                    }
                    final int n55 = n47 + n8;
                    final float n56 = n45 * n23;
                    for (int n57 = 0, n58 = n55; n57 < n; ++n57, n58 += n6) {
                        byteBuffer.position(n58);
                        final float[] array11 = array;
                        final int n59 = n57;
                        array11[n59] += (0xFF & byteBuffer.get()) * n56;
                    }
                    for (int n60 = n16 + 1; n60 < n18; ++n60) {
                        n35 += n7;
                        n40 += n7;
                        for (int n61 = 0; n61 < n; ++n61, n35 += n6, n40 += n6) {
                            byteBuffer.position(n35);
                            final float n62 = (0xFF & byteBuffer.get()) * (1.0f - n21);
                            byteBuffer.position(n40);
                            final float n63 = n62 + (0xFF & byteBuffer.get()) * n23;
                            final float[] array12 = array;
                            final int n64 = n61;
                            array12[n64] += n63;
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n65 = n23 - n21;
                    final float n66 = (1.0f - n17) * n65;
                    int n67 = n29 + n16 * n7;
                    for (int n68 = 0, n69 = n67; n68 < n; ++n68, n69 += n6) {
                        byteBuffer.position(n69);
                        final float[] array13 = array;
                        final int n70 = n68;
                        array13[n70] += (0xFF & byteBuffer.get()) * n66;
                    }
                    for (int n71 = n16 + 1; n71 < n18; ++n71) {
                        n67 += n7;
                        for (int n72 = 0, n73 = n67; n72 < n; ++n72, n73 += n6) {
                            byteBuffer.position(n73);
                            final float[] array14 = array;
                            final int n74 = n72;
                            array14[n74] += (0xFF & byteBuffer.get()) * n65;
                        }
                    }
                    final float n75 = n65 * n19;
                    final int n76 = n67 + n7;
                    for (int n77 = 0, n78 = n76; n77 < n; ++n77, n78 += n6) {
                        byteBuffer.position(n78);
                        final float[] array15 = array;
                        final int n79 = n77;
                        array15[n79] += (0xFF & byteBuffer.get()) * n75;
                    }
                }
                else if (n22 > n20) {
                    final float n80 = n19 - n17;
                    final float n81 = (1.0f - n21) * n80;
                    int n82 = n29 + n16 * n7;
                    for (int n83 = 0, n84 = n82; n83 < n; ++n83, n84 += n6) {
                        byteBuffer.position(n84);
                        final float[] array16 = array;
                        final int n85 = n83;
                        array16[n85] += (0xFF & byteBuffer.get()) * n81;
                    }
                    for (int n86 = n20 + 1; n86 < n22; ++n86) {
                        n82 += n8;
                        for (int n87 = 0, n88 = n82; n87 < n; ++n87, n88 += n6) {
                            byteBuffer.position(n88);
                            final float[] array17 = array;
                            final int n89 = n87;
                            array17[n89] += (0xFF & byteBuffer.get()) * n80;
                        }
                    }
                    final int n90 = n82 + n8;
                    final float n91 = n80 * n23;
                    for (int n92 = 0, n93 = n90; n92 < n; ++n92, n93 += n6) {
                        byteBuffer.position(n93);
                        final float[] array18 = array;
                        final int n94 = n92;
                        array18[n94] += (0xFF & byteBuffer.get()) * n91;
                    }
                }
                else {
                    final float n95 = (n19 - n17) * (n23 - n21);
                    final int n96 = n29 + n16 * n7;
                    for (int n97 = 0, n98 = n96; n97 < n; ++n97, n98 += n6) {
                        byteBuffer.position(n98);
                        final float[] array19 = array;
                        final int n99 = n97;
                        array19[n99] += (0xFF & byteBuffer.get()) * n95;
                    }
                }
                int n100 = n29 + n8 + (n16 + 1) * n7;
                for (int n101 = n16 + 1; n101 < n18; ++n101) {
                    int n102 = n100;
                    for (int n103 = n20 + 1; n103 < n22; ++n103) {
                        for (int n104 = 0, n105 = n102; n104 < n; ++n104, n105 += n6) {
                            byteBuffer.position(n105);
                            final float[] array20 = array;
                            final int n106 = n104;
                            array20[n106] += (0xFF & byteBuffer.get());
                        }
                        n102 += n8;
                    }
                    n100 += n7;
                }
                final int n107 = (j + i * n4) * n;
                for (int n108 = 0; n108 < n; ++n108) {
                    byteBuffer2.position(n107 + n108);
                    byteBuffer2.put((byte)(array[n108] / n15));
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n109 = n22 - n2 + 1;
                    n20 -= n109;
                    n22 -= n109;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_byte(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final ByteBuffer byteBuffer2, final int n6, final int n7, final int n8) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_byte(n, n2, n3, byteBuffer, byteBuffer2, n6, n7, n8);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        final float[] array6 = array;
                        final int n34 = k;
                        array6[n34] += byteBuffer.get() * n32;
                    }
                    int n35 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n36 = 0, n37 = n31; n36 < n; ++n36, n37 += n6) {
                            byteBuffer.position(n37);
                            final float[] array7 = array;
                            final int n38 = n36;
                            array7[n38] += byteBuffer.get() * n30;
                        }
                    }
                    int n40;
                    final int n39 = n40 = n31 + n8;
                    final float n41 = n30 * n23;
                    for (int n42 = 0, n43 = n39; n42 < n; ++n42, n43 += n6) {
                        byteBuffer.position(n43);
                        final float[] array8 = array;
                        final int n44 = n42;
                        array8[n44] += byteBuffer.get() * n41;
                    }
                    final float n45 = n19;
                    final float n46 = n45 * (1.0f - n21);
                    int n47 = n29 + n18 * n7;
                    for (int n48 = 0, n49 = n47; n48 < n; ++n48, n49 += n6) {
                        byteBuffer.position(n49);
                        final float[] array9 = array;
                        final int n50 = n48;
                        array9[n50] += byteBuffer.get() * n46;
                    }
                    for (int n51 = n20 + 1; n51 < n22; ++n51) {
                        n47 += n8;
                        for (int n52 = 0, n53 = n47; n52 < n; ++n52, n53 += n6) {
                            byteBuffer.position(n53);
                            final float[] array10 = array;
                            final int n54 = n52;
                            array10[n54] += byteBuffer.get() * n45;
                        }
                    }
                    final int n55 = n47 + n8;
                    final float n56 = n45 * n23;
                    for (int n57 = 0, n58 = n55; n57 < n; ++n57, n58 += n6) {
                        byteBuffer.position(n58);
                        final float[] array11 = array;
                        final int n59 = n57;
                        array11[n59] += byteBuffer.get() * n56;
                    }
                    for (int n60 = n16 + 1; n60 < n18; ++n60) {
                        n35 += n7;
                        n40 += n7;
                        for (int n61 = 0; n61 < n; ++n61, n35 += n6, n40 += n6) {
                            byteBuffer.position(n35);
                            final float n62 = byteBuffer.get() * (1.0f - n21);
                            byteBuffer.position(n40);
                            final float n63 = n62 + byteBuffer.get() * n23;
                            final float[] array12 = array;
                            final int n64 = n61;
                            array12[n64] += n63;
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n65 = n23 - n21;
                    final float n66 = (1.0f - n17) * n65;
                    int n67 = n29 + n16 * n7;
                    for (int n68 = 0, n69 = n67; n68 < n; ++n68, n69 += n6) {
                        byteBuffer.position(n69);
                        final float[] array13 = array;
                        final int n70 = n68;
                        array13[n70] += byteBuffer.get() * n66;
                    }
                    for (int n71 = n16 + 1; n71 < n18; ++n71) {
                        n67 += n7;
                        for (int n72 = 0, n73 = n67; n72 < n; ++n72, n73 += n6) {
                            byteBuffer.position(n73);
                            final float[] array14 = array;
                            final int n74 = n72;
                            array14[n74] += byteBuffer.get() * n65;
                        }
                    }
                    final float n75 = n65 * n19;
                    final int n76 = n67 + n7;
                    for (int n77 = 0, n78 = n76; n77 < n; ++n77, n78 += n6) {
                        byteBuffer.position(n78);
                        final float[] array15 = array;
                        final int n79 = n77;
                        array15[n79] += byteBuffer.get() * n75;
                    }
                }
                else if (n22 > n20) {
                    final float n80 = n19 - n17;
                    final float n81 = (1.0f - n21) * n80;
                    int n82 = n29 + n16 * n7;
                    for (int n83 = 0, n84 = n82; n83 < n; ++n83, n84 += n6) {
                        byteBuffer.position(n84);
                        final float[] array16 = array;
                        final int n85 = n83;
                        array16[n85] += byteBuffer.get() * n81;
                    }
                    for (int n86 = n20 + 1; n86 < n22; ++n86) {
                        n82 += n8;
                        for (int n87 = 0, n88 = n82; n87 < n; ++n87, n88 += n6) {
                            byteBuffer.position(n88);
                            final float[] array17 = array;
                            final int n89 = n87;
                            array17[n89] += byteBuffer.get() * n80;
                        }
                    }
                    final int n90 = n82 + n8;
                    final float n91 = n80 * n23;
                    for (int n92 = 0, n93 = n90; n92 < n; ++n92, n93 += n6) {
                        byteBuffer.position(n93);
                        final float[] array18 = array;
                        final int n94 = n92;
                        array18[n94] += byteBuffer.get() * n91;
                    }
                }
                else {
                    final float n95 = (n19 - n17) * (n23 - n21);
                    final int n96 = n29 + n16 * n7;
                    for (int n97 = 0, n98 = n96; n97 < n; ++n97, n98 += n6) {
                        byteBuffer.position(n98);
                        final float[] array19 = array;
                        final int n99 = n97;
                        array19[n99] += byteBuffer.get() * n95;
                    }
                }
                int n100 = n29 + n8 + (n16 + 1) * n7;
                for (int n101 = n16 + 1; n101 < n18; ++n101) {
                    int n102 = n100;
                    for (int n103 = n20 + 1; n103 < n22; ++n103) {
                        for (int n104 = 0, n105 = n102; n104 < n; ++n104, n105 += n6) {
                            byteBuffer.position(n105);
                            final float[] array20 = array;
                            final int n106 = n104;
                            array20[n106] += byteBuffer.get();
                        }
                        n102 += n8;
                    }
                    n100 += n7;
                }
                final int n107 = (j + i * n4) * n;
                for (int n108 = 0; n108 < n; ++n108) {
                    byteBuffer2.position(n107 + n108);
                    byteBuffer2.put((byte)(array[n108] / n15));
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n109 = n22 - n2 + 1;
                    n20 -= n109;
                    n22 -= n109;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_ushort(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final ShortBuffer shortBuffer, final int n6, final int n7, final int n8, final boolean b) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_ushort(n, n2, n3, byteBuffer, shortBuffer, n6, n7, n8, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        if (b) {
                            final float[] array6 = array;
                            final int n34 = k;
                            array6[n34] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n32;
                        }
                        else {
                            final float[] array7 = array;
                            final int n35 = k;
                            array7[n35] += (0xFFFF & byteBuffer.getShort()) * n32;
                        }
                    }
                    int n36 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n37 = 0, n38 = n31; n37 < n; ++n37, n38 += n6) {
                            byteBuffer.position(n38);
                            if (b) {
                                final float[] array8 = array;
                                final int n39 = n37;
                                array8[n39] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n30;
                            }
                            else {
                                final float[] array9 = array;
                                final int n40 = n37;
                                array9[n40] += (0xFFFF & byteBuffer.getShort()) * n30;
                            }
                        }
                    }
                    int n42;
                    final int n41 = n42 = n31 + n8;
                    final float n43 = n30 * n23;
                    for (int n44 = 0, n45 = n41; n44 < n; ++n44, n45 += n6) {
                        byteBuffer.position(n45);
                        if (b) {
                            final float[] array10 = array;
                            final int n46 = n44;
                            array10[n46] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n43;
                        }
                        else {
                            final float[] array11 = array;
                            final int n47 = n44;
                            array11[n47] += (0xFFFF & byteBuffer.getShort()) * n43;
                        }
                    }
                    final float n48 = n19;
                    final float n49 = n48 * (1.0f - n21);
                    int n50 = n29 + n18 * n7;
                    for (int n51 = 0, n52 = n50; n51 < n; ++n51, n52 += n6) {
                        byteBuffer.position(n52);
                        if (b) {
                            final float[] array12 = array;
                            final int n53 = n51;
                            array12[n53] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n49;
                        }
                        else {
                            final float[] array13 = array;
                            final int n54 = n51;
                            array13[n54] += (0xFFFF & byteBuffer.getShort()) * n49;
                        }
                    }
                    for (int n55 = n20 + 1; n55 < n22; ++n55) {
                        n50 += n8;
                        for (int n56 = 0, n57 = n50; n56 < n; ++n56, n57 += n6) {
                            byteBuffer.position(n57);
                            if (b) {
                                final float[] array14 = array;
                                final int n58 = n56;
                                array14[n58] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n48;
                            }
                            else {
                                final float[] array15 = array;
                                final int n59 = n56;
                                array15[n59] += (0xFFFF & byteBuffer.getShort()) * n48;
                            }
                        }
                    }
                    final int n60 = n50 + n8;
                    final float n61 = n48 * n23;
                    for (int n62 = 0, n63 = n60; n62 < n; ++n62, n63 += n6) {
                        byteBuffer.position(n63);
                        if (b) {
                            final float[] array16 = array;
                            final int n64 = n62;
                            array16[n64] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n61;
                        }
                        else {
                            final float[] array17 = array;
                            final int n65 = n62;
                            array17[n65] += (0xFFFF & byteBuffer.getShort()) * n61;
                        }
                    }
                    for (int n66 = n16 + 1; n66 < n18; ++n66) {
                        n36 += n7;
                        n42 += n7;
                        for (int n67 = 0; n67 < n; ++n67, n36 += n6, n42 += n6) {
                            if (b) {
                                byteBuffer.position(n36);
                                final float n68 = (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float n69 = n68 + (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n23;
                                final float[] array18 = array;
                                final int n70 = n67;
                                array18[n70] += n69;
                            }
                            else {
                                byteBuffer.position(n36);
                                final float n71 = (0xFFFF & byteBuffer.getShort()) * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float n72 = n71 + (0xFFFF & byteBuffer.getShort()) * n23;
                                final float[] array19 = array;
                                final int n73 = n67;
                                array19[n73] += n72;
                            }
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n74 = n23 - n21;
                    final float n75 = (1.0f - n17) * n74;
                    int n76 = n29 + n16 * n7;
                    for (int n77 = 0, n78 = n76; n77 < n; ++n77, n78 += n6) {
                        byteBuffer.position(n78);
                        if (b) {
                            final float[] array20 = array;
                            final int n79 = n77;
                            array20[n79] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n75;
                        }
                        else {
                            final float[] array21 = array;
                            final int n80 = n77;
                            array21[n80] += (0xFFFF & byteBuffer.getShort()) * n75;
                        }
                    }
                    for (int n81 = n16 + 1; n81 < n18; ++n81) {
                        n76 += n7;
                        for (int n82 = 0, n83 = n76; n82 < n; ++n82, n83 += n6) {
                            byteBuffer.position(n83);
                            if (b) {
                                final float[] array22 = array;
                                final int n84 = n82;
                                array22[n84] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n74;
                            }
                            else {
                                final float[] array23 = array;
                                final int n85 = n82;
                                array23[n85] += (0xFFFF & byteBuffer.getShort()) * n74;
                            }
                        }
                    }
                    final float n86 = n74 * n19;
                    final int n87 = n76 + n7;
                    for (int n88 = 0, n89 = n87; n88 < n; ++n88, n89 += n6) {
                        byteBuffer.position(n89);
                        if (b) {
                            final float[] array24 = array;
                            final int n90 = n88;
                            array24[n90] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n86;
                        }
                        else {
                            final float[] array25 = array;
                            final int n91 = n88;
                            array25[n91] += (0xFFFF & byteBuffer.getShort()) * n86;
                        }
                    }
                }
                else if (n22 > n20) {
                    final float n92 = n19 - n17;
                    final float n93 = (1.0f - n21) * n92;
                    int n94 = n29 + n16 * n7;
                    for (int n95 = 0, n96 = n94; n95 < n; ++n95, n96 += n6) {
                        byteBuffer.position(n96);
                        if (b) {
                            final float[] array26 = array;
                            final int n97 = n95;
                            array26[n97] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n93;
                        }
                        else {
                            final float[] array27 = array;
                            final int n98 = n95;
                            array27[n98] += (0xFFFF & byteBuffer.getShort()) * n93;
                        }
                    }
                    for (int n99 = n20 + 1; n99 < n22; ++n99) {
                        n94 += n8;
                        for (int n100 = 0, n101 = n94; n100 < n; ++n100, n101 += n6) {
                            byteBuffer.position(n101);
                            if (b) {
                                final float[] array28 = array;
                                final int n102 = n100;
                                array28[n102] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n92;
                            }
                            else {
                                final float[] array29 = array;
                                final int n103 = n100;
                                array29[n103] += (0xFFFF & byteBuffer.getShort()) * n92;
                            }
                        }
                    }
                    final int n104 = n94 + n8;
                    final float n105 = n92 * n23;
                    for (int n106 = 0, n107 = n104; n106 < n; ++n106, n107 += n6) {
                        byteBuffer.position(n107);
                        if (b) {
                            final float[] array30 = array;
                            final int n108 = n106;
                            array30[n108] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n105;
                        }
                        else {
                            final float[] array31 = array;
                            final int n109 = n106;
                            array31[n109] += (0xFFFF & byteBuffer.getShort()) * n105;
                        }
                    }
                }
                else {
                    final float n110 = (n19 - n17) * (n23 - n21);
                    final int n111 = n29 + n16 * n7;
                    for (int n112 = 0, n113 = n111; n112 < n; ++n112, n113 += n6) {
                        byteBuffer.position(n113);
                        if (b) {
                            final float[] array32 = array;
                            final int n114 = n112;
                            array32[n114] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort())) * n110;
                        }
                        else {
                            final float[] array33 = array;
                            final int n115 = n112;
                            array33[n115] += (0xFFFF & byteBuffer.getShort()) * n110;
                        }
                    }
                }
                int n116 = n29 + n8 + (n16 + 1) * n7;
                for (int n117 = n16 + 1; n117 < n18; ++n117) {
                    int n118 = n116;
                    for (int n119 = n20 + 1; n119 < n22; ++n119) {
                        for (int n120 = 0, n121 = n118; n120 < n; ++n120, n121 += n6) {
                            byteBuffer.position(n121);
                            if (b) {
                                final float[] array34 = array;
                                final int n122 = n120;
                                array34[n122] += (0xFFFF & Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort()));
                            }
                            else {
                                final float[] array35 = array;
                                final int n123 = n120;
                                array35[n123] += (0xFFFF & byteBuffer.getShort());
                            }
                        }
                        n118 += n8;
                    }
                    n116 += n7;
                }
                final int n124 = (j + i * n4) * n;
                for (int n125 = 0; n125 < n; ++n125) {
                    shortBuffer.position(n124 + n125);
                    shortBuffer.put((short)(array[n125] / n15));
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n126 = n22 - n2 + 1;
                    n20 -= n126;
                    n22 -= n126;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_short(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final ShortBuffer shortBuffer, final int n6, final int n7, final int n8, final boolean b) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_short(n, n2, n3, byteBuffer, shortBuffer, n6, n7, n8, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        if (b) {
                            final short glu_SWAP_2_BYTES = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array6 = array;
                            final int n34 = k;
                            array6[n34] += glu_SWAP_2_BYTES * n32;
                        }
                        else {
                            final float[] array7 = array;
                            final int n35 = k;
                            array7[n35] += byteBuffer.getShort() * n32;
                        }
                    }
                    int n36 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n37 = 0, n38 = n31; n37 < n; ++n37, n38 += n6) {
                            byteBuffer.position(n38);
                            if (b) {
                                final short glu_SWAP_2_BYTES2 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array8 = array;
                                final int n39 = n37;
                                array8[n39] += glu_SWAP_2_BYTES2 * n30;
                            }
                            else {
                                final float[] array9 = array;
                                final int n40 = n37;
                                array9[n40] += byteBuffer.getShort() * n30;
                            }
                        }
                    }
                    int n42;
                    final int n41 = n42 = n31 + n8;
                    final float n43 = n30 * n23;
                    for (int n44 = 0, n45 = n41; n44 < n; ++n44, n45 += n6) {
                        byteBuffer.position(n45);
                        if (b) {
                            final short glu_SWAP_2_BYTES3 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array10 = array;
                            final int n46 = n44;
                            array10[n46] += glu_SWAP_2_BYTES3 * n43;
                        }
                        else {
                            final float[] array11 = array;
                            final int n47 = n44;
                            array11[n47] += byteBuffer.getShort() * n43;
                        }
                    }
                    final float n48 = n19;
                    final float n49 = n48 * (1.0f - n21);
                    int n50 = n29 + n18 * n7;
                    for (int n51 = 0, n52 = n50; n51 < n; ++n51, n52 += n6) {
                        byteBuffer.position(n52);
                        if (b) {
                            final short glu_SWAP_2_BYTES4 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array12 = array;
                            final int n53 = n51;
                            array12[n53] += glu_SWAP_2_BYTES4 * n49;
                        }
                        else {
                            final float[] array13 = array;
                            final int n54 = n51;
                            array13[n54] += byteBuffer.getShort() * n49;
                        }
                    }
                    for (int n55 = n20 + 1; n55 < n22; ++n55) {
                        n50 += n8;
                        for (int n56 = 0, n57 = n50; n56 < n; ++n56, n57 += n6) {
                            byteBuffer.position(n57);
                            if (b) {
                                final short glu_SWAP_2_BYTES5 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array14 = array;
                                final int n58 = n56;
                                array14[n58] += glu_SWAP_2_BYTES5 * n48;
                            }
                            else {
                                final float[] array15 = array;
                                final int n59 = n56;
                                array15[n59] += byteBuffer.getShort() * n48;
                            }
                        }
                    }
                    final int n60 = n50 + n8;
                    final float n61 = n48 * n23;
                    for (int n62 = 0, n63 = n60; n62 < n; ++n62, n63 += n6) {
                        byteBuffer.position(n63);
                        if (b) {
                            final short glu_SWAP_2_BYTES6 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array16 = array;
                            final int n64 = n62;
                            array16[n64] += glu_SWAP_2_BYTES6 * n61;
                        }
                        else {
                            final float[] array17 = array;
                            final int n65 = n62;
                            array17[n65] += byteBuffer.getShort() * n61;
                        }
                    }
                    for (int n66 = n16 + 1; n66 < n18; ++n66) {
                        n36 += n7;
                        n42 += n7;
                        for (int n67 = 0; n67 < n; ++n67, n36 += n6, n42 += n6) {
                            if (b) {
                                byteBuffer.position(n36);
                                final short glu_SWAP_2_BYTES7 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array18 = array;
                                final int n68 = n67;
                                array18[n68] += glu_SWAP_2_BYTES7 * (1.0f - n21);
                                byteBuffer.position(n42);
                                final short glu_SWAP_2_BYTES8 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array19 = array;
                                final int n69 = n67;
                                array19[n69] += glu_SWAP_2_BYTES8 * n23;
                            }
                            else {
                                byteBuffer.position(n36);
                                final float[] array20 = array;
                                final int n70 = n67;
                                array20[n70] += byteBuffer.getShort() * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float[] array21 = array;
                                final int n71 = n67;
                                array21[n71] += byteBuffer.getShort() * n23;
                            }
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n72 = n23 - n21;
                    final float n73 = (1.0f - n17) * n72;
                    int n74 = n29 + n16 * n7;
                    for (int n75 = 0, n76 = n74; n75 < n; ++n75, n76 += n6) {
                        byteBuffer.position(n76);
                        if (b) {
                            final short glu_SWAP_2_BYTES9 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array22 = array;
                            final int n77 = n75;
                            array22[n77] += glu_SWAP_2_BYTES9 * n73;
                        }
                        else {
                            final float[] array23 = array;
                            final int n78 = n75;
                            array23[n78] += byteBuffer.getShort() * n73;
                        }
                    }
                    for (int n79 = n16 + 1; n79 < n18; ++n79) {
                        n74 += n7;
                        for (int n80 = 0, n81 = n74; n80 < n; ++n80, n81 += n6) {
                            byteBuffer.position(n81);
                            if (b) {
                                final short glu_SWAP_2_BYTES10 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array24 = array;
                                final int n82 = n80;
                                array24[n82] += glu_SWAP_2_BYTES10 * n72;
                            }
                            else {
                                final float[] array25 = array;
                                final int n83 = n80;
                                array25[n83] += byteBuffer.getShort() * n72;
                            }
                        }
                    }
                    final float n84 = n72 * n19;
                    final int n85 = n74 + n7;
                    for (int n86 = 0, n87 = n85; n86 < n; ++n86, n87 += n6) {
                        byteBuffer.position(n87);
                        if (b) {
                            final short glu_SWAP_2_BYTES11 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array26 = array;
                            final int n88 = n86;
                            array26[n88] += glu_SWAP_2_BYTES11 * n84;
                        }
                        else {
                            final float[] array27 = array;
                            final int n89 = n86;
                            array27[n89] += byteBuffer.getShort() * n84;
                        }
                    }
                }
                else if (n22 > n20) {
                    final float n90 = n19 - n17;
                    final float n91 = (1.0f - n21) * n90;
                    int n92 = n29 + n16 * n7;
                    for (int n93 = 0, n94 = n92; n93 < n; ++n93, n94 += n6) {
                        byteBuffer.position(n94);
                        if (b) {
                            final short glu_SWAP_2_BYTES12 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array28 = array;
                            final int n95 = n93;
                            array28[n95] += glu_SWAP_2_BYTES12 * n91;
                        }
                        else {
                            final float[] array29 = array;
                            final int n96 = n93;
                            array29[n96] += byteBuffer.getShort() * n91;
                        }
                    }
                    for (int n97 = n20 + 1; n97 < n22; ++n97) {
                        n92 += n8;
                        for (int n98 = 0, n99 = n92; n98 < n; ++n98, n99 += n6) {
                            byteBuffer.position(n99);
                            if (b) {
                                final short glu_SWAP_2_BYTES13 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array30 = array;
                                final int n100 = n98;
                                array30[n100] += glu_SWAP_2_BYTES13 * n90;
                            }
                            else {
                                final float[] array31 = array;
                                final int n101 = n98;
                                array31[n101] += byteBuffer.getShort() * n90;
                            }
                        }
                    }
                    final int n102 = n92 + n8;
                    final float n103 = n90 * n23;
                    for (int n104 = 0, n105 = n102; n104 < n; ++n104, n105 += n6) {
                        byteBuffer.position(n105);
                        if (b) {
                            final short glu_SWAP_2_BYTES14 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array32 = array;
                            final int n106 = n104;
                            array32[n106] += glu_SWAP_2_BYTES14 * n103;
                        }
                        else {
                            final float[] array33 = array;
                            final int n107 = n104;
                            array33[n107] += byteBuffer.getShort() * n103;
                        }
                    }
                }
                else {
                    final float n108 = (n19 - n17) * (n23 - n21);
                    final int n109 = n29 + n16 * n7;
                    for (int n110 = 0, n111 = n109; n110 < n; ++n110, n111 += n6) {
                        byteBuffer.position(n111);
                        if (b) {
                            final short glu_SWAP_2_BYTES15 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                            final float[] array34 = array;
                            final int n112 = n110;
                            array34[n112] += glu_SWAP_2_BYTES15 * n108;
                        }
                        else {
                            final float[] array35 = array;
                            final int n113 = n110;
                            array35[n113] += byteBuffer.getShort() * n108;
                        }
                    }
                }
                int n114 = n29 + n8 + (n16 + 1) * n7;
                for (int n115 = n16 + 1; n115 < n18; ++n115) {
                    int n116 = n114;
                    for (int n117 = n20 + 1; n117 < n22; ++n117) {
                        for (int n118 = 0, n119 = n116; n118 < n; ++n118, n119 += n6) {
                            byteBuffer.position(n119);
                            if (b) {
                                final short glu_SWAP_2_BYTES16 = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
                                final float[] array36 = array;
                                final int n120 = n118;
                                array36[n120] += glu_SWAP_2_BYTES16;
                            }
                            else {
                                final float[] array37 = array;
                                final int n121 = n118;
                                array37[n121] += byteBuffer.getShort();
                            }
                        }
                        n116 += n8;
                    }
                    n114 += n7;
                }
                final int n122 = (j + i * n4) * n;
                for (int n123 = 0; n123 < n; ++n123) {
                    shortBuffer.position(n122 + n123);
                    shortBuffer.put((short)(array[n123] / n15));
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n124 = n22 - n2 + 1;
                    n20 -= n124;
                    n22 -= n124;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_uint(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final IntBuffer intBuffer, final int n6, final int n7, final int n8, final boolean b) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_uint(n, n2, n3, byteBuffer, intBuffer, n6, n7, n8, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        if (b) {
                            final float[] array6 = array;
                            final int n34 = k;
                            array6[n34] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n32;
                        }
                        else {
                            final float[] array7 = array;
                            final int n35 = k;
                            array7[n35] += (-1 & byteBuffer.getInt()) * n32;
                        }
                    }
                    int n36 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n37 = 0, n38 = n31; n37 < n; ++n37, n38 += n6) {
                            byteBuffer.position(n38);
                            if (b) {
                                final float[] array8 = array;
                                final int n39 = n37;
                                array8[n39] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n30;
                            }
                            else {
                                final float[] array9 = array;
                                final int n40 = n37;
                                array9[n40] += (-1 & byteBuffer.getInt()) * n30;
                            }
                        }
                    }
                    int n42;
                    final int n41 = n42 = n31 + n8;
                    final float n43 = n30 * n23;
                    for (int n44 = 0, n45 = n41; n44 < n; ++n44, n45 += n6) {
                        byteBuffer.position(n45);
                        if (b) {
                            final float[] array10 = array;
                            final int n46 = n44;
                            array10[n46] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n43;
                        }
                        else {
                            final float[] array11 = array;
                            final int n47 = n44;
                            array11[n47] += (-1 & byteBuffer.getInt()) * n43;
                        }
                    }
                    final float n48 = n19;
                    final float n49 = n48 * (1.0f - n21);
                    int n50 = n29 + n18 * n7;
                    for (int n51 = 0, n52 = n50; n51 < n; ++n51, n52 += n6) {
                        byteBuffer.position(n52);
                        if (b) {
                            final float[] array12 = array;
                            final int n53 = n51;
                            array12[n53] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n49;
                        }
                        else {
                            final float[] array13 = array;
                            final int n54 = n51;
                            array13[n54] += (-1 & byteBuffer.getInt()) * n49;
                        }
                    }
                    for (int n55 = n20 + 1; n55 < n22; ++n55) {
                        n50 += n8;
                        for (int n56 = 0, n57 = n50; n56 < n; ++n56, n57 += n6) {
                            byteBuffer.position(n57);
                            if (b) {
                                final float[] array14 = array;
                                final int n58 = n56;
                                array14[n58] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n48;
                            }
                            else {
                                final float[] array15 = array;
                                final int n59 = n56;
                                array15[n59] += (-1 & byteBuffer.getInt()) * n48;
                            }
                        }
                    }
                    final int n60 = n50 + n8;
                    final float n61 = n48 * n23;
                    for (int n62 = 0, n63 = n60; n62 < n; ++n62, n63 += n6) {
                        byteBuffer.position(n63);
                        if (b) {
                            final float[] array16 = array;
                            final int n64 = n62;
                            array16[n64] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n61;
                        }
                        else {
                            final float[] array17 = array;
                            final int n65 = n62;
                            array17[n65] += (-1 & byteBuffer.getInt()) * n61;
                        }
                    }
                    for (int n66 = n16 + 1; n66 < n18; ++n66) {
                        n36 += n7;
                        n42 += n7;
                        for (int n67 = 0; n67 < n; ++n67, n36 += n6, n42 += n6) {
                            if (b) {
                                byteBuffer.position(n36);
                                final float[] array18 = array;
                                final int n68 = n67;
                                array18[n68] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float[] array19 = array;
                                final int n69 = n67;
                                array19[n69] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n23;
                            }
                            else {
                                byteBuffer.position(n36);
                                final float[] array20 = array;
                                final int n70 = n67;
                                array20[n70] += (-1 & byteBuffer.getInt()) * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float[] array21 = array;
                                final int n71 = n67;
                                array21[n71] += (-1 & byteBuffer.getInt()) * n23;
                            }
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n72 = n23 - n21;
                    final float n73 = (1.0f - n17) * n72;
                    int n74 = n29 + n16 * n7;
                    for (int n75 = 0, n76 = n74; n75 < n; ++n75, n76 += n6) {
                        byteBuffer.position(n76);
                        if (b) {
                            final float[] array22 = array;
                            final int n77 = n75;
                            array22[n77] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n73;
                        }
                        else {
                            final float[] array23 = array;
                            final int n78 = n75;
                            array23[n78] += (-1 & byteBuffer.getInt()) * n73;
                        }
                    }
                    for (int n79 = n16 + 1; n79 < n18; ++n79) {
                        n74 += n7;
                        for (int n80 = 0, n81 = n74; n80 < n; ++n80, n81 += n6) {
                            byteBuffer.position(n81);
                            if (b) {
                                final float[] array24 = array;
                                final int n82 = n80;
                                array24[n82] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n72;
                            }
                            else {
                                final float[] array25 = array;
                                final int n83 = n80;
                                array25[n83] += (-1 & byteBuffer.getInt()) * n72;
                            }
                        }
                    }
                    final float n84 = n72 * n19;
                    final int n85 = n74 + n7;
                    for (int n86 = 0, n87 = n85; n86 < n; ++n86, n87 += n6) {
                        byteBuffer.position(n87);
                        if (b) {
                            final float[] array26 = array;
                            final int n88 = n86;
                            array26[n88] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n84;
                        }
                        else {
                            final float[] array27 = array;
                            final int n89 = n86;
                            array27[n89] += (-1 & byteBuffer.getInt()) * n84;
                        }
                    }
                }
                else if (n22 > n20) {
                    final float n90 = n19 - n17;
                    final float n91 = (1.0f - n21) * n90;
                    int n92 = n29 + n16 * n7;
                    for (int n93 = 0, n94 = n92; n93 < n; ++n93, n94 += n6) {
                        byteBuffer.position(n94);
                        if (b) {
                            final float[] array28 = array;
                            final int n95 = n93;
                            array28[n95] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n91;
                        }
                        else {
                            final float[] array29 = array;
                            final int n96 = n93;
                            array29[n96] += (-1 & byteBuffer.getInt()) * n91;
                        }
                    }
                    for (int n97 = n20 + 1; n97 < n22; ++n97) {
                        n92 += n8;
                        for (int n98 = 0, n99 = n92; n98 < n; ++n98, n99 += n6) {
                            byteBuffer.position(n99);
                            if (b) {
                                final float[] array30 = array;
                                final int n100 = n98;
                                array30[n100] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n90;
                            }
                            else {
                                final float[] array31 = array;
                                final int n101 = n98;
                                array31[n101] += (-1 & byteBuffer.getInt()) * n90;
                            }
                        }
                    }
                    final int n102 = n92 + n8;
                    final float n103 = n90 * n23;
                    for (int n104 = 0, n105 = n102; n104 < n; ++n104, n105 += n6) {
                        byteBuffer.position(n105);
                        if (b) {
                            final float[] array32 = array;
                            final int n106 = n104;
                            array32[n106] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n103;
                        }
                        else {
                            final float[] array33 = array;
                            final int n107 = n104;
                            array33[n107] += (-1 & byteBuffer.getInt()) * n103;
                        }
                    }
                }
                else {
                    final float n108 = (n19 - n17) * (n23 - n21);
                    final int n109 = n29 + n16 * n7;
                    for (int n110 = 0, n111 = n109; n110 < n; ++n110, n111 += n6) {
                        final long n112 = 0xFFFFFFFFL & byteBuffer.getInt(n111);
                        byteBuffer.position(n111);
                        final long n113 = 0xFFFFFFFFL & byteBuffer.getInt();
                        byteBuffer.position(n111);
                        if (b) {
                            final float[] array34 = array;
                            final int n114 = n110;
                            array34[n114] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt())) * n108;
                        }
                        else {
                            final float[] array35 = array;
                            final int n115 = n110;
                            array35[n115] += (-1 & byteBuffer.getInt()) * n108;
                        }
                    }
                }
                int n116 = n29 + n8 + (n16 + 1) * n7;
                for (int n117 = n16 + 1; n117 < n18; ++n117) {
                    int n118 = n116;
                    for (int n119 = n20 + 1; n119 < n22; ++n119) {
                        for (int n120 = 0, n121 = n118; n120 < n; ++n120, n121 += n6) {
                            byteBuffer.position(n121);
                            if (b) {
                                final float[] array36 = array;
                                final int n122 = n120;
                                array36[n122] += (-1 & Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt()));
                            }
                            else {
                                final float[] array37 = array;
                                final int n123 = n120;
                                array37[n123] += (-1 & byteBuffer.getInt());
                            }
                        }
                        n118 += n8;
                    }
                    n116 += n7;
                }
                final int n124 = (j + i * n4) * n;
                for (int n125 = 0; n125 < n; ++n125) {
                    final float n126 = array[n125] / n15;
                    intBuffer.position(n124 + n125);
                    if (n126 >= -1.0f) {
                        intBuffer.put((int)n126);
                    }
                    else {
                        intBuffer.put((int)(array[n125] / n15));
                    }
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n127 = n22 - n2 + 1;
                    n20 -= n127;
                    n22 -= n127;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_int(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final IntBuffer intBuffer, final int n6, final int n7, final int n8, final boolean b) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_int(n, n2, n3, byteBuffer, intBuffer, n6, n7, n8, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        if (b) {
                            final long n34 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array6 = array;
                            final int n35 = k;
                            array6[n35] += n34 * n32;
                        }
                        else {
                            final float[] array7 = array;
                            final int n36 = k;
                            array7[n36] += byteBuffer.getInt() * n32;
                        }
                    }
                    int n37 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n38 = 0, n39 = n31; n38 < n; ++n38, n39 += n6) {
                            byteBuffer.position(n39);
                            if (b) {
                                final long n40 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array8 = array;
                                final int n41 = n38;
                                array8[n41] += n40 * n30;
                            }
                            else {
                                final float[] array9 = array;
                                final int n42 = n38;
                                array9[n42] += byteBuffer.getInt() * n30;
                            }
                        }
                    }
                    int n44;
                    final int n43 = n44 = n31 + n8;
                    final float n45 = n30 * n23;
                    for (int n46 = 0, n47 = n43; n46 < n; ++n46, n47 += n6) {
                        byteBuffer.position(n47);
                        if (b) {
                            final long n48 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array10 = array;
                            final int n49 = n46;
                            array10[n49] += n48 * n45;
                        }
                        else {
                            final float[] array11 = array;
                            final int n50 = n46;
                            array11[n50] += byteBuffer.getInt() * n45;
                        }
                    }
                    final float n51 = n19;
                    final float n52 = n51 * (1.0f - n21);
                    int n53 = n29 + n18 * n7;
                    for (int n54 = 0, n55 = n53; n54 < n; ++n54, n55 += n6) {
                        byteBuffer.position(n55);
                        if (b) {
                            final long n56 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array12 = array;
                            final int n57 = n54;
                            array12[n57] += n56 * n52;
                        }
                        else {
                            final float[] array13 = array;
                            final int n58 = n54;
                            array13[n58] += byteBuffer.getInt() * n52;
                        }
                    }
                    for (int n59 = n20 + 1; n59 < n22; ++n59) {
                        n53 += n8;
                        for (int n60 = 0, n61 = n53; n60 < n; ++n60, n61 += n6) {
                            byteBuffer.position(n61);
                            if (b) {
                                final long n62 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array14 = array;
                                final int n63 = n60;
                                array14[n63] += n62 * n51;
                            }
                            else {
                                final float[] array15 = array;
                                final int n64 = n60;
                                array15[n64] += byteBuffer.getInt() * n51;
                            }
                        }
                    }
                    final int n65 = n53 + n8;
                    final float n66 = n51 * n23;
                    for (int n67 = 0, n68 = n65; n67 < n; ++n67, n68 += n6) {
                        byteBuffer.position(n68);
                        if (b) {
                            final long n69 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array16 = array;
                            final int n70 = n67;
                            array16[n70] += n69 * n66;
                        }
                        else {
                            final float[] array17 = array;
                            final int n71 = n67;
                            array17[n71] += byteBuffer.getInt() * n66;
                        }
                    }
                    for (int n72 = n16 + 1; n72 < n18; ++n72) {
                        n37 += n7;
                        n44 += n7;
                        for (int n73 = 0; n73 < n; ++n73, n37 += n6, n44 += n6) {
                            if (b) {
                                byteBuffer.position(n37);
                                final long n74 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array18 = array;
                                final int n75 = n73;
                                array18[n75] += n74 * (1.0f - n21);
                                byteBuffer.position(n44);
                                final long n76 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array19 = array;
                                final int n77 = n73;
                                array19[n77] += n76 * n23;
                            }
                            else {
                                byteBuffer.position(n37);
                                final float[] array20 = array;
                                final int n78 = n73;
                                array20[n78] += byteBuffer.getInt() * (1.0f - n21);
                                byteBuffer.position(n44);
                                final float[] array21 = array;
                                final int n79 = n73;
                                array21[n79] += byteBuffer.getInt() * n23;
                            }
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n80 = n23 - n21;
                    final float n81 = (1.0f - n17) * n80;
                    int n82 = n29 + n16 * n7;
                    for (int n83 = 0, n84 = n82; n83 < n; ++n83, n84 += n6) {
                        byteBuffer.position(n84);
                        if (b) {
                            final long n85 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array22 = array;
                            final int n86 = n83;
                            array22[n86] += n85 * n81;
                        }
                        else {
                            final float[] array23 = array;
                            final int n87 = n83;
                            array23[n87] += byteBuffer.getInt() * n81;
                        }
                    }
                    for (int n88 = n16 + 1; n88 < n18; ++n88) {
                        n82 += n7;
                        for (int n89 = 0, n90 = n82; n89 < n; ++n89, n90 += n6) {
                            byteBuffer.position(n90);
                            if (b) {
                                final long n91 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array24 = array;
                                final int n92 = n89;
                                array24[n92] += n91 * n80;
                            }
                            else {
                                final float[] array25 = array;
                                final int n93 = n89;
                                array25[n93] += byteBuffer.getInt() * n80;
                            }
                        }
                    }
                    final float n94 = n80 * n19;
                    final int n95 = n82 + n7;
                    for (int n96 = 0, n97 = n95; n96 < n; ++n96, n97 += n6) {
                        byteBuffer.position(n97);
                        if (b) {
                            final long n98 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array26 = array;
                            final int n99 = n96;
                            array26[n99] += n98 * n94;
                        }
                        else {
                            final float[] array27 = array;
                            final int n100 = n96;
                            array27[n100] += byteBuffer.getInt() * n94;
                        }
                    }
                }
                else if (n22 > n20) {
                    final float n101 = n19 - n17;
                    final float n102 = (1.0f - n21) * n101;
                    int n103 = n29 + n16 * n7;
                    for (int n104 = 0, n105 = n103; n104 < n; ++n104, n105 += n6) {
                        byteBuffer.position(n105);
                        if (b) {
                            final long n106 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array28 = array;
                            final int n107 = n104;
                            array28[n107] += n106 * n102;
                        }
                        else {
                            final float[] array29 = array;
                            final int n108 = n104;
                            array29[n108] += byteBuffer.getInt() * n102;
                        }
                    }
                    for (int n109 = n20 + 1; n109 < n22; ++n109) {
                        n103 += n8;
                        for (int n110 = 0, n111 = n103; n110 < n; ++n110, n111 += n6) {
                            byteBuffer.position(n111);
                            if (b) {
                                final long n112 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array30 = array;
                                final int n113 = n110;
                                array30[n113] += n112 * n101;
                            }
                            else {
                                final float[] array31 = array;
                                final int n114 = n110;
                                array31[n114] += byteBuffer.getInt() * n101;
                            }
                        }
                    }
                    final int n115 = n103 + n8;
                    final float n116 = n101 * n23;
                    for (int n117 = 0, n118 = n115; n117 < n; ++n117, n118 += n6) {
                        byteBuffer.position(n118);
                        if (b) {
                            final long n119 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array32 = array;
                            final int n120 = n117;
                            array32[n120] += n119 * n116;
                        }
                        else {
                            final float[] array33 = array;
                            final int n121 = n117;
                            array33[n121] += byteBuffer.getInt() * n116;
                        }
                    }
                }
                else {
                    final float n122 = (n19 - n17) * (n23 - n21);
                    final int n123 = n29 + n16 * n7;
                    for (int n124 = 0, n125 = n123; n124 < n; ++n124, n125 += n6) {
                        byteBuffer.position(n125);
                        if (b) {
                            final long n126 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                            final float[] array34 = array;
                            final int n127 = n124;
                            array34[n127] += n126 * n122;
                        }
                        else {
                            final float[] array35 = array;
                            final int n128 = n124;
                            array35[n128] += byteBuffer.getInt() * n122;
                        }
                    }
                }
                int n129 = n29 + n8 + (n16 + 1) * n7;
                for (int n130 = n16 + 1; n130 < n18; ++n130) {
                    int n131 = n129;
                    for (int n132 = n20 + 1; n132 < n22; ++n132) {
                        for (int n133 = 0, n134 = n131; n133 < n; ++n133, n134 += n6) {
                            byteBuffer.position(n134);
                            if (b) {
                                final long n135 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getInt());
                                final float[] array36 = array;
                                final int n136 = n133;
                                array36[n136] += n135;
                            }
                            else {
                                final float[] array37 = array;
                                final int n137 = n133;
                                array37[n137] += byteBuffer.getInt();
                            }
                        }
                        n131 += n8;
                    }
                    n129 += n7;
                }
                final int n138 = (j + i * n4) * n;
                for (int n139 = 0; n139 < n; ++n139) {
                    intBuffer.position(n138 + n139);
                    intBuffer.put((int)(array[n139] / n15));
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n140 = n22 - n2 + 1;
                    n20 -= n140;
                    n22 -= n140;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scale_internal_float(final int n, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final FloatBuffer floatBuffer, final int n6, final int n7, final int n8, final boolean b) {
        final float[] array = new float[4];
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImage_float(n, n2, n3, byteBuffer, floatBuffer, n6, n7, n8, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n12;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22;
            float n23;
            if (n2 == 1 && n4 == 1) {
                n22 = 0;
                n23 = 0.0f;
            }
            else {
                n22 = n13;
                n23 = n14;
            }
            for (int j = 0; j < n4; ++j) {
                final float[] array2 = array;
                final int n24 = 0;
                final float[] array3 = array;
                final int n25 = 1;
                final float[] array4 = array;
                final int n26 = 2;
                final float[] array5 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array4[n26] = (array5[n27] = n28);
                array2[n24] = (array3[n25] = n28);
                final int n29 = n20 * n8;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    for (int k = 0, n33 = n31; k < n; ++k, n33 += n6) {
                        byteBuffer.position(n33);
                        if (b) {
                            final float glu_SWAP_4_BYTES = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array6 = array;
                            final int n34 = k;
                            array6[n34] += glu_SWAP_4_BYTES * n32;
                        }
                        else {
                            final float[] array7 = array;
                            final int n35 = k;
                            array7[n35] += byteBuffer.getFloat() * n32;
                        }
                    }
                    int n36 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n8;
                        for (int n37 = 0, n38 = n31; n37 < n; ++n37, n38 += n6) {
                            byteBuffer.position(n38);
                            if (b) {
                                final float glu_SWAP_4_BYTES2 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array8 = array;
                                final int n39 = n37;
                                array8[n39] += glu_SWAP_4_BYTES2 * n30;
                            }
                            else {
                                final float[] array9 = array;
                                final int n40 = n37;
                                array9[n40] += byteBuffer.getFloat() * n30;
                            }
                        }
                    }
                    int n42;
                    final int n41 = n42 = n31 + n8;
                    final float n43 = n30 * n23;
                    for (int n44 = 0, n45 = n41; n44 < n; ++n44, n45 += n6) {
                        byteBuffer.position(n45);
                        if (b) {
                            final float glu_SWAP_4_BYTES3 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array10 = array;
                            final int n46 = n44;
                            array10[n46] += glu_SWAP_4_BYTES3 * n43;
                        }
                        else {
                            final float[] array11 = array;
                            final int n47 = n44;
                            array11[n47] += byteBuffer.getFloat() * n43;
                        }
                    }
                    final float n48 = n19;
                    final float n49 = n48 * (1.0f - n21);
                    int n50 = n29 + n18 * n7;
                    for (int n51 = 0, n52 = n50; n51 < n; ++n51, n52 += n6) {
                        byteBuffer.position(n52);
                        if (b) {
                            final float glu_SWAP_4_BYTES4 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array12 = array;
                            final int n53 = n51;
                            array12[n53] += glu_SWAP_4_BYTES4 * n49;
                        }
                        else {
                            final float[] array13 = array;
                            final int n54 = n51;
                            array13[n54] += byteBuffer.getFloat() * n49;
                        }
                    }
                    for (int n55 = n20 + 1; n55 < n22; ++n55) {
                        n50 += n8;
                        for (int n56 = 0, n57 = n50; n56 < n; ++n56, n57 += n6) {
                            byteBuffer.position(n57);
                            if (b) {
                                final float glu_SWAP_4_BYTES5 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array14 = array;
                                final int n58 = n56;
                                array14[n58] += glu_SWAP_4_BYTES5 * n48;
                            }
                            else {
                                final float[] array15 = array;
                                final int n59 = n56;
                                array15[n59] += byteBuffer.getFloat() * n48;
                            }
                        }
                    }
                    final int n60 = n50 + n8;
                    final float n61 = n48 * n23;
                    for (int n62 = 0, n63 = n60; n62 < n; ++n62, n63 += n6) {
                        byteBuffer.position(n63);
                        if (b) {
                            final float glu_SWAP_4_BYTES6 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array16 = array;
                            final int n64 = n62;
                            array16[n64] += glu_SWAP_4_BYTES6 * n61;
                        }
                        else {
                            final float[] array17 = array;
                            final int n65 = n62;
                            array17[n65] += byteBuffer.getFloat() * n61;
                        }
                    }
                    for (int n66 = n16 + 1; n66 < n18; ++n66) {
                        n36 += n7;
                        n42 += n7;
                        for (int n67 = 0; n67 < n; ++n67, n36 += n6, n42 += n6) {
                            if (b) {
                                byteBuffer.position(n36);
                                final float glu_SWAP_4_BYTES7 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array18 = array;
                                final int n68 = n67;
                                array18[n68] += glu_SWAP_4_BYTES7 * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float glu_SWAP_4_BYTES8 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array19 = array;
                                final int n69 = n67;
                                array19[n69] += glu_SWAP_4_BYTES8 * n23;
                            }
                            else {
                                byteBuffer.position(n36);
                                final float[] array20 = array;
                                final int n70 = n67;
                                array20[n70] += byteBuffer.getFloat() * (1.0f - n21);
                                byteBuffer.position(n42);
                                final float[] array21 = array;
                                final int n71 = n67;
                                array21[n71] += byteBuffer.getFloat() * n23;
                            }
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n72 = n23 - n21;
                    final float n73 = (1.0f - n17) * n72;
                    int n74 = n29 + n16 * n7;
                    for (int n75 = 0, n76 = n74; n75 < n; ++n75, n76 += n6) {
                        byteBuffer.position(n76);
                        if (b) {
                            final float glu_SWAP_4_BYTES9 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array22 = array;
                            final int n77 = n75;
                            array22[n77] += glu_SWAP_4_BYTES9 * n73;
                        }
                        else {
                            final float[] array23 = array;
                            final int n78 = n75;
                            array23[n78] += byteBuffer.getFloat() * n73;
                        }
                    }
                    for (int n79 = n16 + 1; n79 < n18; ++n79) {
                        n74 += n7;
                        for (int n80 = 0, n81 = n74; n80 < n; ++n80, n81 += n6) {
                            byteBuffer.position(n81);
                            if (b) {
                                final float glu_SWAP_4_BYTES10 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array24 = array;
                                final int n82 = n80;
                                array24[n82] += glu_SWAP_4_BYTES10 * n72;
                            }
                            else {
                                final float[] array25 = array;
                                final int n83 = n80;
                                array25[n83] += byteBuffer.getFloat() * n72;
                            }
                        }
                    }
                    final float n84 = n72 * n19;
                    final int n85 = n74 + n7;
                    for (int n86 = 0, n87 = n85; n86 < n; ++n86, n87 += n6) {
                        byteBuffer.position(n87);
                        if (b) {
                            final float glu_SWAP_4_BYTES11 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array26 = array;
                            final int n88 = n86;
                            array26[n88] += glu_SWAP_4_BYTES11 * n84;
                        }
                        else {
                            final float[] array27 = array;
                            final int n89 = n86;
                            array27[n89] += byteBuffer.getFloat() * n84;
                        }
                    }
                }
                else if (n22 > n20) {
                    final float n90 = n19 - n17;
                    final float n91 = (1.0f - n21) * n90;
                    int n92 = n29 + n16 * n7;
                    for (int n93 = 0, n94 = n92; n93 < n; ++n93, n94 += n6) {
                        byteBuffer.position(n94);
                        if (b) {
                            final float glu_SWAP_4_BYTES12 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array28 = array;
                            final int n95 = n93;
                            array28[n95] += glu_SWAP_4_BYTES12 * n91;
                        }
                        else {
                            final float[] array29 = array;
                            final int n96 = n93;
                            array29[n96] += byteBuffer.getFloat() * n91;
                        }
                    }
                    for (int n97 = n20 + 1; n97 < n22; ++n97) {
                        n92 += n8;
                        for (int n98 = 0, n99 = n92; n98 < n; ++n98, n99 += n6) {
                            byteBuffer.position(n99);
                            if (b) {
                                final float glu_SWAP_4_BYTES13 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array30 = array;
                                final int n100 = n98;
                                array30[n100] += glu_SWAP_4_BYTES13 * n90;
                            }
                            else {
                                final float[] array31 = array;
                                final int n101 = n98;
                                array31[n101] += byteBuffer.getFloat() * n90;
                            }
                        }
                    }
                    final int n102 = n92 + n8;
                    final float n103 = n90 * n23;
                    for (int n104 = 0, n105 = n102; n104 < n; ++n104, n105 += n6) {
                        byteBuffer.position(n105);
                        if (b) {
                            final float glu_SWAP_4_BYTES14 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array32 = array;
                            final int n106 = n104;
                            array32[n106] += glu_SWAP_4_BYTES14 * n103;
                        }
                        else {
                            final float[] array33 = array;
                            final int n107 = n104;
                            array33[n107] += byteBuffer.getFloat() * n103;
                        }
                    }
                }
                else {
                    final float n108 = (n19 - n17) * (n23 - n21);
                    final int n109 = n29 + n16 * n7;
                    for (int n110 = 0, n111 = n109; n110 < n; ++n110, n111 += n6) {
                        byteBuffer.position(n111);
                        if (b) {
                            final float glu_SWAP_4_BYTES15 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                            final float[] array34 = array;
                            final int n112 = n110;
                            array34[n112] += glu_SWAP_4_BYTES15 * n108;
                        }
                        else {
                            final float[] array35 = array;
                            final int n113 = n110;
                            array35[n113] += byteBuffer.getFloat() * n108;
                        }
                    }
                }
                int n114 = n29 + n8 + (n16 + 1) * n7;
                for (int n115 = n16 + 1; n115 < n18; ++n115) {
                    int n116 = n114;
                    for (int n117 = n20 + 1; n117 < n22; ++n117) {
                        for (int n118 = 0, n119 = n116; n118 < n; ++n118, n119 += n6) {
                            byteBuffer.position(n119);
                            if (b) {
                                final float glu_SWAP_4_BYTES16 = Mipmap.GLU_SWAP_4_BYTES(byteBuffer.getFloat());
                                final float[] array36 = array;
                                final int n120 = n118;
                                array36[n120] += glu_SWAP_4_BYTES16;
                            }
                            else {
                                final float[] array37 = array;
                                final int n121 = n118;
                                array37[n121] += byteBuffer.getFloat();
                            }
                        }
                        n116 += n8;
                    }
                    n114 += n7;
                }
                final int n122 = (j + i * n4) * n;
                for (int n123 = 0; n123 < n; ++n123) {
                    floatBuffer.position(n122 + n123);
                    floatBuffer.put(array[n123] / n15);
                }
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n124 = n22 - n2 + 1;
                    n20 -= n124;
                    n22 -= n124;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
    }
    
    public static void scaleInternalPackedPixel(final int n, final Extract extract, final int n2, final int n3, final ByteBuffer byteBuffer, final int n4, final int n5, final ByteBuffer byteBuffer2, final int n6, final int n7, final boolean b) {
        final float[] array = new float[4];
        final float[] array2 = new float[4];
        final float[] array3 = new float[4];
        final float[] array4 = new float[4];
        int n8 = 0;
        if (n2 == n4 * 2 && n3 == n5 * 2) {
            HalveImage.halveImagePackedPixel(n, extract, n2, n3, byteBuffer, byteBuffer2, n6, n7, b);
            return;
        }
        final float n9 = n3 / n5;
        final float n10 = n2 / n4;
        final int n11 = (int)Math.floor(n9);
        final float n12 = n9 - n11;
        final int n13 = (int)Math.floor(n10);
        final float n14 = n10 - n13;
        final float n15 = n10 * n9;
        int n16 = 0;
        float n17 = 0.0f;
        int n18 = n11;
        float n19 = n14;
        for (int i = 0; i < n5; ++i) {
            if (n18 >= n3) {
                n18 = n3 - 1;
            }
            int n20 = 0;
            float n21 = 0.0f;
            int n22 = n13;
            float n23 = n14;
            for (int j = 0; j < n4; ++j) {
                final float[] array5 = array;
                final int n24 = 0;
                final float[] array6 = array;
                final int n25 = 1;
                final float[] array7 = array;
                final int n26 = 2;
                final float[] array8 = array;
                final int n27 = 3;
                final float n28 = 0.0f;
                array7[n26] = (array8[n27] = n28);
                array5[n24] = (array6[n25] = n28);
                final int n29 = n20 * n6;
                float n51;
                if (n18 > n16 && n22 > n20) {
                    final float n30 = 1.0f - n17;
                    int n31 = n29 + n16 * n7;
                    final float n32 = n30 * (1.0f - n21);
                    byteBuffer.position(n31);
                    extract.extract(b, byteBuffer, array2);
                    for (int k = 0; k < n; ++k) {
                        final float[] array9 = array;
                        final int n33 = k;
                        array9[n33] += array2[k] * n32;
                    }
                    int n34 = n31;
                    for (int l = n20 + 1; l < n22; ++l) {
                        n31 += n6;
                        byteBuffer.position(n31);
                        extract.extract(b, byteBuffer, array2);
                        for (int n35 = 0; n35 < n; ++n35) {
                            final float[] array10 = array;
                            final int n36 = n35;
                            array10[n36] += array2[n35] * n30;
                        }
                    }
                    int n38;
                    final int n37 = n38 = n31 + n6;
                    final float n39 = n30 * n23;
                    byteBuffer.position(n37);
                    extract.extract(b, byteBuffer, array2);
                    for (int n40 = 0; n40 < n; ++n40) {
                        final float[] array11 = array;
                        final int n41 = n40;
                        array11[n41] += array2[n40] * n39;
                    }
                    final float n42 = n19;
                    final float n43 = n42 * (1.0f - n21);
                    int n44 = n29 + n18 * n7;
                    byteBuffer.position(n44);
                    extract.extract(b, byteBuffer, array2);
                    for (int n45 = 0; n45 < n; ++n45) {
                        final float[] array12 = array;
                        final int n46 = n45;
                        array12[n46] += array2[n45] * n43;
                    }
                    for (int n47 = n20 + 1; n47 < n22; ++n47) {
                        n44 += n6;
                        byteBuffer.position(n44);
                        extract.extract(b, byteBuffer, array2);
                        for (int n48 = 0; n48 < n; ++n48) {
                            final float[] array13 = array;
                            final int n49 = n48;
                            array13[n49] += array2[n48] * n42;
                        }
                    }
                    final int n50 = n44 + n6;
                    n51 = n42 * n23;
                    byteBuffer.position(n50);
                    for (int n52 = 0; n52 < n; ++n52) {
                        final float[] array14 = array;
                        final int n53 = n52;
                        array14[n53] += array2[n52] * n51;
                    }
                    for (int n54 = n16 + 1; n54 < n18; ++n54) {
                        n34 += n7;
                        n38 += n7;
                        byteBuffer.position(n34);
                        extract.extract(b, byteBuffer, array2);
                        byteBuffer.position(n38);
                        extract.extract(b, byteBuffer, array3);
                        for (int n55 = 0; n55 < n; ++n55) {
                            final float[] array15 = array;
                            final int n56 = n55;
                            array15[n56] += array2[n55] * (1.0f - n21) + array3[n55] * n23;
                        }
                    }
                }
                else if (n18 > n16) {
                    final float n57 = n23 - n21;
                    final float n58 = (1.0f - n17) * n57;
                    int n59 = n29 + n16 * n7;
                    byteBuffer.position(n59);
                    extract.extract(b, byteBuffer, array2);
                    for (int n60 = 0; n60 < n; ++n60) {
                        final float[] array16 = array;
                        final int n61 = n60;
                        array16[n61] += array2[n60] * n58;
                    }
                    for (int n62 = n16 + 1; n62 < n18; ++n62) {
                        n59 += n7;
                        byteBuffer.position(n59);
                        extract.extract(b, byteBuffer, array2);
                        for (int n63 = 0; n63 < n; ++n63) {
                            final float[] array17 = array;
                            final int n64 = n63;
                            array17[n64] += array2[n63] * n57;
                        }
                    }
                    n51 = n57 * n19;
                    byteBuffer.position(n59 + n7);
                    extract.extract(b, byteBuffer, array2);
                    for (int n65 = 0; n65 < n; ++n65) {
                        final float[] array18 = array;
                        final int n66 = n65;
                        array18[n66] += array2[n65] * n51;
                    }
                }
                else if (n22 > n20) {
                    final float n67 = n19 - n17;
                    final float n68 = (1.0f - n21) * n67;
                    int n69 = n29 + n16 * n7;
                    byteBuffer.position(n69);
                    extract.extract(b, byteBuffer, array2);
                    for (int n70 = 0; n70 < n; ++n70) {
                        final float[] array19 = array;
                        final int n71 = n70;
                        array19[n71] += array2[n70] * n68;
                    }
                    for (int n72 = n20 + 1; n72 < n22; ++n72) {
                        n69 += n6;
                        byteBuffer.position(n69);
                        extract.extract(b, byteBuffer, array2);
                        for (int n73 = 0; n73 < n; ++n73) {
                            final float[] array20 = array;
                            final int n74 = n73;
                            array20[n74] += array2[n73] * n67;
                        }
                    }
                    final int n75 = n69 + n6;
                    n51 = n67 * n23;
                    byteBuffer.position(n75);
                    extract.extract(b, byteBuffer, array2);
                    for (int n76 = 0; n76 < n; ++n76) {
                        final float[] array21 = array;
                        final int n77 = n76;
                        array21[n77] += array2[n76] * n51;
                    }
                }
                else {
                    n51 = (n19 - n17) * (n23 - n21);
                    byteBuffer.position(n29 + n16 * n7);
                    extract.extract(b, byteBuffer, array2);
                    for (int n78 = 0; n78 < n; ++n78) {
                        final float[] array22 = array;
                        final int n79 = n78;
                        array22[n79] += array2[n78] * n51;
                    }
                }
                int n80 = n29 + n6 + (n16 + 1) * n7;
                for (int n81 = n16 + 1; n81 < n18; ++n81) {
                    int n82 = n80;
                    for (int n83 = n20 + 1; n83 < n22; ++n83) {
                        byteBuffer.position(n82);
                        extract.extract(b, byteBuffer, array2);
                        for (int n84 = 0; n84 < n; ++n84) {
                            final float[] array23 = array;
                            final int n85 = n84;
                            array23[n85] += array2[n84] * n51;
                        }
                        n82 += n6;
                    }
                    n80 += n7;
                }
                n8 = j + i * n4;
                for (int n86 = 0; n86 < n; ++n86) {
                    array4[n86] = array[n86] / n15;
                }
                extract.shove(array4, n8, byteBuffer2);
                n20 = n22;
                n21 = n23;
                n22 += n13;
                n23 += n14;
                if (n23 > 1.0f) {
                    --n23;
                    ++n22;
                }
                if (n22 > n2 - 1) {
                    final int n87 = n22 - n2 + 1;
                    n20 -= n87;
                    n22 -= n87;
                }
            }
            n16 = n18;
            n17 = n19;
            n18 += n11;
            n19 += n12;
            if (n19 > 1.0f) {
                --n19;
                ++n18;
            }
        }
        assert n8 == n4 * n5 - 1;
    }
    
    public static void scaleInternal3D(final int n, final int n2, final int n3, final int n4, final ShortBuffer shortBuffer, final int n5, final int n6, final int n7, final ShortBuffer shortBuffer2) {
        final float[] array = new float[4];
        final float n11;
        final float n10;
        final float n9;
        final float n8 = n9 = (n10 = (n11 = 0.0f));
        final float n12 = n4 / n7;
        final float n13 = n3 / n6;
        final float n14 = n2 / n5;
        final float n15 = n14 / 2.0f;
        for (int i = 0; i < n7; ++i) {
            final float n16 = n12 * (i + 0.5f);
            if (n4 > n7) {}
            for (int j = 0; j < n6; ++j) {
                final float n17 = n13 * (j + 0.5f);
                if (n3 > n6) {}
                for (int k = 0; k < n5; ++k) {
                    final float n18 = n14 * (k + 0.5f);
                    float n19;
                    float n20;
                    if (n4 > n7) {
                        n19 = n18 + n15;
                        n20 = n18 - n15;
                    }
                    else {
                        n19 = n18 + 0.5f;
                        n20 = n18 - 0.5f;
                    }
                    final float[] array2 = array;
                    final int n21 = 0;
                    final float[] array3 = array;
                    final int n22 = 1;
                    final float[] array4 = array;
                    final int n23 = 2;
                    final float[] array5 = array;
                    final int n24 = 3;
                    final float n25 = 0.0f;
                    array4[n23] = (array5[n24] = n25);
                    array2[n21] = (array3[n22] = n25);
                    float n26 = 0.0f;
                    float n27 = n20;
                    for (int n28 = (int)Math.floor(n27); n27 < n19; n27 = ++n28) {
                        final int n29 = (n28 + n4) % n4;
                        float n30;
                        if (n19 < n28 + 1) {
                            n30 = n19 - n27;
                        }
                        else {
                            n30 = n28 + 1 - n27;
                        }
                        float n31 = n9;
                        for (int n32 = (int)Math.floor(n31); n31 < n8; n31 = ++n32) {
                            final int n33 = (n32 + n3) % n3;
                            float n34;
                            if (n8 < n32 + 1) {
                                n34 = n8 - n31;
                            }
                            else {
                                n34 = n32 + 1 - n31;
                            }
                            float n35 = n10;
                            for (int n36 = (int)Math.floor(n35); n35 < n11; n35 = ++n36) {
                                final int n37 = (n36 + n2) % n2;
                                float n38;
                                if (n11 < n36 + 1) {
                                    n38 = n11 - n35;
                                }
                                else {
                                    n38 = n36 + 1 - n35;
                                }
                                final float n39 = n38 * n34 * n30;
                                n26 += n39;
                                final int n40 = (n37 + n33 * n2 + n29 * n2 * n3) * n;
                                for (int l = 0; l < n; ++l) {
                                    assert 0 <= n40 + l && n40 + l < n2 * n3 * n4 * n;
                                    final float[] array6 = array;
                                    final int n41 = l;
                                    array6[n41] += shortBuffer.get(n40 + l) * n39;
                                }
                            }
                        }
                    }
                    final int n42 = (k + j * n5 + i * n5 * n6) * n;
                    for (int n43 = 0; n43 < n; ++n43) {
                        assert 0 <= n42 + n43 && n42 + n43 < n5 * n6 * n7 * n;
                        shortBuffer2.put(n42 + n43, (short)((array[n43] + 0.5f) / n26));
                    }
                }
            }
        }
    }
    
    public static int gluScaleImage3D(final GL gl, final int n, final int n2, final int n3, final int n4, final int n5, final ByteBuffer byteBuffer, final int n6, final int n7, final int n8, final int n9, final ByteBuffer byteBuffer2) {
        final PixelStorageModes pixelStorageModes = new PixelStorageModes();
        if (n2 == 0 || n3 == 0 || n4 == 0 || n6 == 0 || n7 == 0 || n8 == 0) {
            return 0;
        }
        if (n2 < 0 || n3 < 0 || n4 < 0 || n6 < 0 || n7 < 0 || n8 < 0) {
            return 100901;
        }
        if (!Mipmap.legalFormat(n) || !Mipmap.legalType(n5) || !Mipmap.legalType(n9) || n5 == 6656 || n9 == 6656) {
            return 100900;
        }
        if (!Mipmap.isLegalFormatForPackedPixelType(n, n5)) {
            return 100904;
        }
        if (!Mipmap.isLegalFormatForPackedPixelType(n, n9)) {
            return 100904;
        }
        ShortBuffer shortBuffer;
        ShortBuffer shortBuffer2;
        try {
            shortBuffer = Buffers.newDirectByteBuffer(Mipmap.imageSize3D(n2, n3, n4, n, 5123)).asShortBuffer();
            shortBuffer2 = Buffers.newDirectByteBuffer(Mipmap.imageSize3D(n2, n3, n4, n, 5123)).asShortBuffer();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return 100902;
        }
        Mipmap.retrieveStoreModes3D(gl, pixelStorageModes);
        Image.fillImage3D(pixelStorageModes, n2, n3, n4, n, n5, Mipmap.is_index(n), byteBuffer, shortBuffer);
        scaleInternal3D(Mipmap.elements_per_group(n, 0), n2, n3, n4, shortBuffer, n6, n7, n8, shortBuffer2);
        Image.emptyImage3D(pixelStorageModes, n6, n7, n8, n, n9, Mipmap.is_index(n), shortBuffer2, byteBuffer2);
        return 0;
    }
}
