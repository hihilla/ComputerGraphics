// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot;

public class Disassembler
{
    public static int advanceIP(final short[] array, int n) {
        int n2 = n & 0xFFFF;
        ++n;
        if (64 == array[n2]) {
            n += array[++n2] + 1;
        }
        else if (65 == array[n2]) {
            n += array[++n2] * 2 + 1;
        }
        else if (0xB0 == (array[n2] & 0xF8)) {
            n += (short)((array[n2] & 0x7) + 1);
        }
        else if (0xB8 == (array[n2] & 0xF8)) {
            n += (short)((array[n2] & 0x7) + 1) * 2;
        }
        return n;
    }
    
    public static short getPushCount(final short[] array, final int n) {
        final short n2 = array[n & 0xFFFF];
        if (64 == n2 || 65 == n2) {
            return array[(n & 0xFFFF) + 1];
        }
        if (0xB0 == (n2 & 0xF8) || 0xB8 == (n2 & 0xF8)) {
            return (short)((n2 & 0x7) + 1);
        }
        return 0;
    }
    
    public static int[] getPushData(final short[] array, final int n) {
        final short pushCount = getPushCount(array, n);
        final int[] array2 = new int[pushCount];
        final int n2 = n & 0xFFFF;
        final short n3 = array[n2];
        if (64 == n3) {
            for (short n4 = 0; n4 < pushCount; ++n4) {
                array2[n4] = array[n2 + n4 + 2];
            }
        }
        else if (0xB0 == (n3 & 0xF8)) {
            for (short n5 = 0; n5 < pushCount; ++n5) {
                array2[n5] = array[n2 + n5 + 1];
            }
        }
        else if (65 == n3) {
            for (short n6 = 0; n6 < pushCount; ++n6) {
                array2[n6] = (array[n2 + n6 * 2 + 2] << 8 | array[n2 + n6 * 2 + 3]);
            }
        }
        else if (0xB8 == (n3 & 0xF8)) {
            for (short n7 = 0; n7 < pushCount; ++n7) {
                array2[n7] = (array[n2 + n7 * 2 + 1] << 8 | array[n2 + n7 * 2 + 2]);
            }
        }
        return array2;
    }
    
    public static String disassemble(final short[] array, final int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i = advanceIP(array, i)) {
            for (int j = 0; j < n; ++j) {
                sb.append(" ");
            }
            sb.append(i).append(": ");
            sb.append(Mnemonic.getMnemonic(array[i]));
            if (getPushCount(array, i) > 0) {
                final int[] pushData = getPushData(array, i);
                for (int k = 0; k < pushData.length; ++k) {
                    if (array[i] == 184 || array[i] == 65) {
                        sb.append(" ").append((short)pushData[k]);
                    }
                    else {
                        sb.append(" ").append(pushData[k]);
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
