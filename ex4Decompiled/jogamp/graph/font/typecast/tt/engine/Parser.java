// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.tt.engine;

import jogamp.graph.font.typecast.ot.Mnemonic;

public class Parser
{
    private final short[][] instructions;
    
    public Parser() {
        this.instructions = new short[3][];
    }
    
    public int advanceIP(int n) {
        final int n2 = n >> 16;
        int n3 = n & 0xFFFF;
        ++n;
        if (64 == this.instructions[n2][n3]) {
            n += this.instructions[n2][++n3] + 1;
        }
        else if (65 == this.instructions[n2][n3]) {
            n += this.instructions[n2][++n3] * 2 + 1;
        }
        else if (0xB0 == (this.instructions[n2][n3] & 0xF8)) {
            n += (short)((this.instructions[n2][n3] & 0x7) + 1);
        }
        else if (0xB8 == (this.instructions[n2][n3] & 0xF8)) {
            n += (short)((this.instructions[n2][n3] & 0x7) + 1) * 2;
        }
        return n;
    }
    
    public int getISLength(final int n) {
        return this.instructions[n].length;
    }
    
    public short getOpcode(final int n) {
        return this.instructions[n >> 16][n & 0xFFFF];
    }
    
    public short getPushCount(final int n) {
        final short n2 = this.instructions[n >> 16][n & 0xFFFF];
        if (64 == n2 || 65 == n2) {
            return this.instructions[n >> 16][(n & 0xFFFF) + 1];
        }
        if (0xB0 == (n2 & 0xF8) || 0xB8 == (n2 & 0xF8)) {
            return (short)((n2 & 0x7) + 1);
        }
        return 0;
    }
    
    public int[] getPushData(final int n) {
        final short pushCount = this.getPushCount(n);
        final int[] array = new int[pushCount];
        final int n2 = n >> 16;
        final int n3 = n & 0xFFFF;
        final short n4 = this.instructions[n2][n3];
        if (64 == n4) {
            for (short n5 = 0; n5 < pushCount; ++n5) {
                array[n5] = this.instructions[n2][n3 + n5 + 2];
            }
        }
        else if (0xB0 == (n4 & 0xF8)) {
            for (short n6 = 0; n6 < pushCount; ++n6) {
                array[n6] = this.instructions[n2][n3 + n6 + 1];
            }
        }
        else if (65 == n4) {
            for (short n7 = 0; n7 < pushCount; ++n7) {
                array[n7] = (this.instructions[n2][n3 + n7 * 2 + 2] << 8 | this.instructions[n2][n3 + n7 * 2 + 3]);
            }
        }
        else if (0xB8 == (n4 & 0xF8)) {
            for (short n8 = 0; n8 < pushCount; ++n8) {
                array[n8] = (this.instructions[n2][n3 + n8 * 2 + 1] << 8 | this.instructions[n2][n3 + n8 * 2 + 2]);
            }
        }
        return array;
    }
    
    public int handleElse(int advanceIP) {
        while (this.instructions[advanceIP >> 16][advanceIP & 0xFFFF] != 89) {
            advanceIP = this.advanceIP(advanceIP);
        }
        return advanceIP;
    }
    
    public int handleIf(final boolean b, int advanceIP) {
        if (!b) {
            while (this.instructions[advanceIP >> 16][advanceIP & 0xFFFF] != 27 && this.instructions[advanceIP >> 16][advanceIP & 0xFFFF] != 89) {
                advanceIP = this.advanceIP(advanceIP);
            }
        }
        return advanceIP;
    }
    
    public void setCvtProgram(final short[] array) {
        this.instructions[1] = array;
    }
    
    public void setFontProgram(final short[] array) {
        this.instructions[0] = array;
    }
    
    public void setGlyphProgram(final short[] array) {
        this.instructions[2] = array;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.instructions[0].length; i = this.advanceIP(i)) {
            sb.append(Mnemonic.getMnemonic(this.getOpcode(i)));
            if (this.getPushCount(i) > 0) {
                final int[] pushData = this.getPushData(i);
                for (int j = 0; j < pushData.length; ++j) {
                    sb.append(" ").append(pushData[j]);
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        for (int k = 65536; k < (0x10000 | this.instructions[1].length); k = this.advanceIP(k)) {
            sb.append(Mnemonic.getMnemonic(this.getOpcode(k)));
            if (this.getPushCount(k) > 0) {
                final int[] pushData2 = this.getPushData(k);
                for (int l = 0; l < pushData2.length; ++l) {
                    sb.append(" ").append(pushData2[l]);
                }
            }
            sb.append("\n");
        }
        sb.append("\n");
        for (int advanceIP = 131072; advanceIP < (0x20000 | this.instructions[2].length); advanceIP = this.advanceIP(advanceIP)) {
            sb.append(Mnemonic.getMnemonic(this.getOpcode(advanceIP)));
            if (this.getPushCount(advanceIP) > 0) {
                final int[] pushData3 = this.getPushData(advanceIP);
                for (int n = 0; n < pushData3.length; ++n) {
                    sb.append(" ").append(pushData3[n]);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
