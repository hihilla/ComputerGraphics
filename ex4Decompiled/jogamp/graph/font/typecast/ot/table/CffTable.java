// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.util.Iterator;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Dictionary;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.DataInput;

public class CffTable implements Table
{
    private final DirectoryEntry _de;
    private final int _major;
    private final int _minor;
    private final int _hdrSize;
    private final int _offSize;
    private final NameIndex _nameIndex;
    private final TopDictIndex _topDictIndex;
    private final StringIndex _stringIndex;
    private final Index _globalSubrIndex;
    private final Index[] _charStringsIndexArray;
    private final Charset[] _charsets;
    private final Charstring[][] _charstringsArray;
    private final byte[] _buf;
    
    protected CffTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this._de = (DirectoryEntry)directoryEntry.clone();
        dataInput.readFully(this._buf = new byte[directoryEntry.getLength()]);
        final DataInput dataInputForOffset = this.getDataInputForOffset(0);
        this._major = dataInputForOffset.readUnsignedByte();
        this._minor = dataInputForOffset.readUnsignedByte();
        this._hdrSize = dataInputForOffset.readUnsignedByte();
        this._offSize = dataInputForOffset.readUnsignedByte();
        final DataInput dataInputForOffset2 = this.getDataInputForOffset(this._hdrSize);
        this._nameIndex = new NameIndex(dataInputForOffset2);
        this._topDictIndex = new TopDictIndex(dataInputForOffset2);
        this._stringIndex = new StringIndex(dataInputForOffset2);
        this._globalSubrIndex = new Index(dataInputForOffset2);
        this._charStringsIndexArray = new Index[this._topDictIndex.getCount()];
        this._charsets = new Charset[this._topDictIndex.getCount()];
        this._charstringsArray = new Charstring[this._topDictIndex.getCount()][];
        for (int i = 0; i < this._topDictIndex.getCount(); ++i) {
            this._charStringsIndexArray[i] = new Index(this.getDataInputForOffset((int)this._topDictIndex.getTopDict(i).getValue(17)));
            final int count = this._charStringsIndexArray[i].getCount();
            final DataInput dataInputForOffset3 = this.getDataInputForOffset((int)this._topDictIndex.getTopDict(i).getValue(15));
            switch (dataInputForOffset3.readUnsignedByte()) {
                case 0: {
                    this._charsets[i] = new CharsetFormat0(dataInputForOffset3, count);
                    break;
                }
                case 1: {
                    this._charsets[i] = new CharsetFormat1(dataInputForOffset3, count);
                    break;
                }
                case 2: {
                    this._charsets[i] = new CharsetFormat2(dataInputForOffset3, count);
                    break;
                }
            }
            this._charstringsArray[i] = new Charstring[count];
            for (int j = 0; j < count; ++j) {
                final int n = this._charStringsIndexArray[i].getOffset(j) - 1;
                this._charstringsArray[i][j] = new CharstringType2(i, this._stringIndex.getString(this._charsets[i].getSID(j)), this._charStringsIndexArray[i].getData(), n, this._charStringsIndexArray[i].getOffset(j + 1) - n - 1, null, null);
            }
        }
    }
    
    private DataInput getDataInputForOffset(final int n) {
        return new DataInputStream(new ByteArrayInputStream(this._buf, n, this._de.getLength() - n));
    }
    
    public NameIndex getNameIndex() {
        return this._nameIndex;
    }
    
    public Charset getCharset(final int n) {
        return this._charsets[n];
    }
    
    public Charstring getCharstring(final int n, final int n2) {
        return this._charstringsArray[n][n2];
    }
    
    public int getCharstringCount(final int n) {
        return this._charstringsArray[n].length;
    }
    
    @Override
    public int getType() {
        return 1128678944;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'CFF' Table - Compact Font Format\n---------------------------------\n");
        sb.append("\nName INDEX\n");
        sb.append(this._nameIndex.toString());
        sb.append("\nTop DICT INDEX\n");
        sb.append(this._topDictIndex.toString());
        sb.append("\nString INDEX\n");
        sb.append(this._stringIndex.toString());
        sb.append("\nGlobal Subr INDEX\n");
        sb.append(this._globalSubrIndex.toString());
        for (int i = 0; i < this._charStringsIndexArray.length; ++i) {
            sb.append("\nCharStrings INDEX ").append(i).append("\n");
            sb.append(this._charStringsIndexArray[i].toString());
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this._de;
    }
    
    public static class Dict
    {
        private final Dictionary<Integer, Object> _entries;
        private final int[] _data;
        private int _index;
        
        protected Dict(final int[] data, final int index, final int n) {
            this._entries = new Hashtable<Integer, Object>();
            this._data = data;
            this._index = index;
            while (this._index < index + n) {
                this.addKeyAndValueEntry();
            }
        }
        
        public Object getValue(final int n) {
            return this._entries.get(n);
        }
        
        private boolean addKeyAndValueEntry() {
            final ArrayList<Object> list = new ArrayList<Object>();
            Object nextOperand = null;
            while (this.isOperandAtIndex()) {
                nextOperand = this.nextOperand();
                list.add(nextOperand);
            }
            int n = this._data[this._index++];
            if (n == 12) {
                n = (n << 8 | this._data[this._index++]);
            }
            if (list.size() == 1) {
                this._entries.put(n, nextOperand);
            }
            else {
                this._entries.put(n, list);
            }
            return true;
        }
        
        private boolean isOperandAtIndex() {
            final int n = this._data[this._index];
            return (32 <= n && n <= 254) || n == 28 || n == 29 || n == 30;
        }
        
        private boolean isOperatorAtIndex() {
            final int n = this._data[this._index];
            return 0 <= n && n <= 21;
        }
        
        private Object nextOperand() {
            final int n = this._data[this._index];
            if (32 <= n && n <= 246) {
                ++this._index;
                return n - 139;
            }
            if (247 <= n && n <= 250) {
                final int n2 = this._data[this._index + 1];
                this._index += 2;
                return (n - 247) * 256 + n2 + 108;
            }
            if (251 <= n && n <= 254) {
                final int n3 = this._data[this._index + 1];
                this._index += 2;
                return -(n - 251) * 256 - n3 - 108;
            }
            if (n == 28) {
                final int n4 = this._data[this._index + 1];
                final int n5 = this._data[this._index + 2];
                this._index += 3;
                return n4 << 8 | n5;
            }
            if (n == 29) {
                final int n6 = this._data[this._index + 1];
                final int n7 = this._data[this._index + 2];
                final int n8 = this._data[this._index + 3];
                final int n9 = this._data[this._index + 4];
                this._index += 5;
                return n6 << 24 | n7 << 16 | n8 << 8 | n9;
            }
            if (n == 30) {
                final StringBuilder sb = new StringBuilder();
                int n10 = 0;
                int n11 = 0;
                ++this._index;
                while (n10 != 15 && n11 != 15) {
                    n10 = this._data[this._index] >> 4;
                    n11 = (this._data[this._index] & 0xF);
                    ++this._index;
                    sb.append(this.decodeRealNibble(n10));
                    sb.append(this.decodeRealNibble(n11));
                }
                return Float.valueOf(sb.toString());
            }
            return null;
        }
        
        private String decodeRealNibble(final int n) {
            if (n < 10) {
                return Integer.toString(n);
            }
            if (n == 10) {
                return ".";
            }
            if (n == 11) {
                return "E";
            }
            if (n == 12) {
                return "E-";
            }
            if (n == 14) {
                return "-";
            }
            return "";
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            final Enumeration<Integer> keys = this._entries.keys();
            while (keys.hasMoreElements()) {
                final Integer n = keys.nextElement();
                if ((n & 0xC00) == 0xC00) {
                    sb.append("12 ").append(n & 0xFF).append(": ");
                }
                else {
                    sb.append(n.toString()).append(": ");
                }
                sb.append(this._entries.get(n).toString()).append("\n");
            }
            return sb.toString();
        }
    }
    
    public class Index
    {
        private final int _count;
        private final int _offSize;
        private final int[] _offset;
        private final int[] _data;
        
        protected Index(final DataInput dataInput) throws IOException {
            this._count = dataInput.readUnsignedShort();
            this._offset = new int[this._count + 1];
            this._offSize = dataInput.readUnsignedByte();
            for (int i = 0; i < this._count + 1; ++i) {
                int n = 0;
                for (int j = 0; j < this._offSize; ++j) {
                    n |= dataInput.readUnsignedByte() << (this._offSize - j - 1) * 8;
                }
                this._offset[i] = n;
            }
            this._data = new int[this.getDataLength()];
            for (int k = 0; k < this.getDataLength(); ++k) {
                this._data[k] = dataInput.readUnsignedByte();
            }
        }
        
        public int getCount() {
            return this._count;
        }
        
        public int getOffset(final int n) {
            return this._offset[n];
        }
        
        public int getDataLength() {
            return this._offset[this._offset.length - 1] - 1;
        }
        
        public int[] getData() {
            return this._data;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("DICT\n");
            sb.append("count: ").append(this._count).append("\n");
            sb.append("offSize: ").append(this._offSize).append("\n");
            for (int i = 0; i < this._count + 1; ++i) {
                sb.append("offset[").append(i).append("]: ").append(this._offset[i]).append("\n");
            }
            sb.append("data:");
            for (int j = 0; j < this._data.length; ++j) {
                if (j % 8 == 0) {
                    sb.append("\n");
                }
                else {
                    sb.append(" ");
                }
                sb.append(this._data[j]);
            }
            sb.append("\n");
            return sb.toString();
        }
    }
    
    public class TopDictIndex extends Index
    {
        protected TopDictIndex(final DataInput dataInput) throws IOException {
            super(dataInput);
        }
        
        public Dict getTopDict(final int n) {
            final int n2 = this.getOffset(n) - 1;
            return new Dict(this.getData(), n2, this.getOffset(n + 1) - n2 - 1);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.getCount(); ++i) {
                sb.append(this.getTopDict(i).toString()).append("\n");
            }
            return sb.toString();
        }
    }
    
    public class NameIndex extends Index
    {
        protected NameIndex(final DataInput dataInput) throws IOException {
            super(dataInput);
        }
        
        public String getName(final int n) {
            final int n2 = this.getOffset(n) - 1;
            final int n3 = this.getOffset(n + 1) - n2 - 1;
            String string;
            if (this.getData()[n2] != 0) {
                final StringBuilder sb = new StringBuilder();
                for (int i = n2; i < n2 + n3; ++i) {
                    sb.append((char)this.getData()[i]);
                }
                string = sb.toString();
            }
            else {
                string = "DELETED NAME";
            }
            return string;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.getCount(); ++i) {
                sb.append(this.getName(i)).append("\n");
            }
            return sb.toString();
        }
    }
    
    public class StringIndex extends Index
    {
        protected StringIndex(final DataInput dataInput) throws IOException {
            super(dataInput);
        }
        
        public String getString(int n) {
            if (n < CffStandardStrings.standardStrings.length) {
                return CffStandardStrings.standardStrings[n];
            }
            n -= CffStandardStrings.standardStrings.length;
            if (n >= this.getCount()) {
                return null;
            }
            final int n2 = this.getOffset(n) - 1;
            final int n3 = this.getOffset(n + 1) - n2 - 1;
            final StringBuilder sb = new StringBuilder();
            for (int i = n2; i < n2 + n3; ++i) {
                sb.append((char)this.getData()[i]);
            }
            return sb.toString();
        }
        
        @Override
        public String toString() {
            final int length = CffStandardStrings.standardStrings.length;
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.getCount(); ++i) {
                sb.append(length + i).append(": ");
                sb.append(this.getString(length + i)).append("\n");
            }
            return sb.toString();
        }
    }
    
    private class CharsetRange
    {
        private int _first;
        private int _left;
        
        public int getFirst() {
            return this._first;
        }
        
        protected void setFirst(final int first) {
            this._first = first;
        }
        
        public int getLeft() {
            return this._left;
        }
        
        protected void setLeft(final int left) {
            this._left = left;
        }
    }
    
    private class CharsetRange1 extends CharsetRange
    {
        protected CharsetRange1(final DataInput dataInput) throws IOException {
            this.setFirst(dataInput.readUnsignedShort());
            this.setLeft(dataInput.readUnsignedByte());
        }
    }
    
    private class CharsetRange2 extends CharsetRange
    {
        protected CharsetRange2(final DataInput dataInput) throws IOException {
            this.setFirst(dataInput.readUnsignedShort());
            this.setLeft(dataInput.readUnsignedShort());
        }
    }
    
    private abstract class Charset
    {
        public abstract int getFormat();
        
        public abstract int getSID(final int p0);
    }
    
    private class CharsetFormat0 extends Charset
    {
        private final int[] _glyph;
        
        protected CharsetFormat0(final DataInput dataInput, final int n) throws IOException {
            this._glyph = new int[n - 1];
            for (int i = 0; i < n - 1; ++i) {
                this._glyph[i] = dataInput.readUnsignedShort();
            }
        }
        
        @Override
        public int getFormat() {
            return 0;
        }
        
        @Override
        public int getSID(final int n) {
            if (n == 0) {
                return 0;
            }
            return this._glyph[n - 1];
        }
    }
    
    private class CharsetFormat1 extends Charset
    {
        private final ArrayList<CharsetRange> _charsetRanges;
        
        protected CharsetFormat1(final DataInput dataInput, final int n) throws IOException {
            this._charsetRanges = new ArrayList<CharsetRange>();
            CharsetRange1 charsetRange1;
            for (int i = n - 1; i > 0; i -= charsetRange1.getLeft() + 1) {
                charsetRange1 = new CharsetRange1(dataInput);
                this._charsetRanges.add(charsetRange1);
            }
        }
        
        @Override
        public int getFormat() {
            return 1;
        }
        
        @Override
        public int getSID(final int n) {
            if (n == 0) {
                return 0;
            }
            int n2 = 0;
            for (final CharsetRange charsetRange : this._charsetRanges) {
                n2 += charsetRange.getLeft();
                if (n < n2) {
                    return n - n2 + charsetRange.getFirst();
                }
            }
            return 0;
        }
    }
    
    private class CharsetFormat2 extends Charset
    {
        private final ArrayList<CharsetRange> _charsetRanges;
        
        protected CharsetFormat2(final DataInput dataInput, final int n) throws IOException {
            this._charsetRanges = new ArrayList<CharsetRange>();
            CharsetRange2 charsetRange2;
            for (int i = n - 1; i > 0; i -= charsetRange2.getLeft() + 1) {
                charsetRange2 = new CharsetRange2(dataInput);
                this._charsetRanges.add(charsetRange2);
            }
        }
        
        @Override
        public int getFormat() {
            return 2;
        }
        
        @Override
        public int getSID(final int n) {
            if (n == 0) {
                return 0;
            }
            int n2 = 0;
            for (final CharsetRange charsetRange : this._charsetRanges) {
                if (n < charsetRange.getLeft() + n2) {
                    return n - n2 + charsetRange.getFirst() - 1;
                }
                n2 += charsetRange.getLeft();
            }
            return 0;
        }
    }
}
