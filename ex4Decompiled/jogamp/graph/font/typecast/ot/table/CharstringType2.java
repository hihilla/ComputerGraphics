// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

public class CharstringType2 extends Charstring
{
    private static final String[] _oneByteOperators;
    private static final String[] _twoByteOperators;
    private final int _index;
    private final String _name;
    private final int[] _data;
    private final int _offset;
    private final int _length;
    private final CffTable.Index _localSubrIndex;
    private final CffTable.Index _globalSubrIndex;
    private int _ip;
    
    protected CharstringType2(final int index, final String name, final int[] data, final int offset, final int length, final CffTable.Index localSubrIndex, final CffTable.Index globalSubrIndex) {
        this._index = index;
        this._name = name;
        this._data = data;
        this._offset = offset;
        this._length = length;
        this._localSubrIndex = localSubrIndex;
        this._globalSubrIndex = globalSubrIndex;
    }
    
    @Override
    public int getIndex() {
        return this._index;
    }
    
    @Override
    public String getName() {
        return this._name;
    }
    
    private void disassemble(final StringBuilder sb) {
        while (this.isOperandAtIndex()) {
            sb.append(this.nextOperand()).append(" ");
        }
        final int nextByte = this.nextByte();
        String s;
        if (nextByte == 12) {
            int nextByte2 = this.nextByte();
            if (nextByte2 > 38) {
                nextByte2 = 38;
            }
            s = CharstringType2._twoByteOperators[nextByte2];
        }
        else {
            s = CharstringType2._oneByteOperators[nextByte];
        }
        sb.append(s);
    }
    
    public void resetIP() {
        this._ip = this._offset;
    }
    
    public boolean isOperandAtIndex() {
        final int n = this._data[this._ip];
        return (32 <= n && n <= 255) || n == 28;
    }
    
    public Number nextOperand() {
        final int n = this._data[this._ip];
        if (32 <= n && n <= 246) {
            ++this._ip;
            return n - 139;
        }
        if (247 <= n && n <= 250) {
            final int n2 = this._data[this._ip + 1];
            this._ip += 2;
            return (n - 247) * 256 + n2 + 108;
        }
        if (251 <= n && n <= 254) {
            final int n3 = this._data[this._ip + 1];
            this._ip += 2;
            return -(n - 251) * 256 - n3 - 108;
        }
        if (n == 28) {
            final int n4 = this._data[this._ip + 1];
            final int n5 = this._data[this._ip + 2];
            this._ip += 3;
            return n4 << 8 | n5;
        }
        if (n == 255) {
            final byte b = (byte)this._data[this._ip + 1];
            final int n6 = this._data[this._ip + 2];
            final int n7 = this._data[this._ip + 3];
            final int n8 = this._data[this._ip + 4];
            this._ip += 5;
            return (b << 8 | n6) + (n7 << 8 | n8) / 65536.0f;
        }
        return null;
    }
    
    public int nextByte() {
        return this._data[this._ip++];
    }
    
    public boolean moreBytes() {
        return this._ip < this._offset + this._length;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        this.resetIP();
        while (this.moreBytes()) {
            this.disassemble(sb);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    static {
        _oneByteOperators = new String[] { "-Reserved-", "hstem", "-Reserved-", "vstem", "vmoveto", "rlineto", "hlineto", "vlineto", "rrcurveto", "-Reserved-", "callsubr", "return", "escape", "-Reserved-", "endchar", "-Reserved-", "-Reserved-", "-Reserved-", "hstemhm", "hintmask", "cntrmask", "rmoveto", "hmoveto", "vstemhm", "rcurveline", "rlinecurve", "vvcurveto", "hhcurveto", "shortint", "callgsubr", "vhcurveto", "hvcurveto" };
        _twoByteOperators = new String[] { "-Reserved- (dotsection)", "-Reserved-", "-Reserved-", "and", "or", "not", "-Reserved-", "-Reserved-", "-Reserved-", "abs", "add", "sub", "div", "-Reserved-", "neg", "eq", "-Reserved-", "-Reserved-", "drop", "-Reserved-", "put", "get", "ifelse", "random", "mul", "-Reserved-", "sqrt", "dup", "exch", "index", "roll", "-Reserved-", "-Reserved-", "-Reserved-", "hflex", "flex", "hflex1", "flex1", "-Reserved-" };
    }
}
