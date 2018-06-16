// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import jogamp.graph.font.typecast.ot.Fixed;
import java.io.IOException;
import java.io.DataInput;

public class PostTable implements Table
{
    private static final String[] macGlyphName;
    private final DirectoryEntry de;
    private final int version;
    private final int italicAngle;
    private final short underlinePosition;
    private final short underlineThickness;
    private final int isFixedPitch;
    private final int minMemType42;
    private final int maxMemType42;
    private final int minMemType1;
    private final int maxMemType1;
    private int numGlyphs;
    private int[] glyphNameIndex;
    private String[] psGlyphName;
    
    protected PostTable(final DirectoryEntry directoryEntry, final DataInput dataInput) throws IOException {
        this.de = (DirectoryEntry)directoryEntry.clone();
        this.version = dataInput.readInt();
        this.italicAngle = dataInput.readInt();
        this.underlinePosition = dataInput.readShort();
        this.underlineThickness = dataInput.readShort();
        this.isFixedPitch = dataInput.readInt();
        this.minMemType42 = dataInput.readInt();
        this.maxMemType42 = dataInput.readInt();
        this.minMemType1 = dataInput.readInt();
        this.maxMemType1 = dataInput.readInt();
        if (this.version == 131072) {
            this.numGlyphs = dataInput.readUnsignedShort();
            this.glyphNameIndex = new int[this.numGlyphs];
            for (int i = 0; i < this.numGlyphs; ++i) {
                this.glyphNameIndex[i] = dataInput.readUnsignedShort();
            }
            int highestGlyphNameIndex = this.highestGlyphNameIndex();
            if (highestGlyphNameIndex > 257) {
                highestGlyphNameIndex -= 257;
                this.psGlyphName = new String[highestGlyphNameIndex];
                for (int j = 0; j < highestGlyphNameIndex; ++j) {
                    final byte[] array = new byte[dataInput.readUnsignedByte()];
                    dataInput.readFully(array);
                    this.psGlyphName[j] = new String(array);
                }
            }
        }
    }
    
    public int getVersion() {
        return this.version;
    }
    
    private int highestGlyphNameIndex() {
        int n = 0;
        for (int i = 0; i < this.numGlyphs; ++i) {
            if (n < this.glyphNameIndex[i]) {
                n = this.glyphNameIndex[i];
            }
        }
        return n;
    }
    
    public String getGlyphName(final int n) {
        if (this.version == 131072) {
            return (this.glyphNameIndex[n] > 257) ? this.psGlyphName[this.glyphNameIndex[n] - 258] : PostTable.macGlyphName[this.glyphNameIndex[n]];
        }
        return null;
    }
    
    private boolean isMacGlyphName(final int n) {
        return this.version == 131072 && this.glyphNameIndex[n] <= 257;
    }
    
    @Override
    public int getType() {
        return 1886352244;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("'post' Table - PostScript Metrics\n---------------------------------\n").append("\n        'post' version:        ").append(Fixed.floatValue(this.version)).append("\n        italicAngle:           ").append(Fixed.floatValue(this.italicAngle)).append("\n        underlinePosition:     ").append(this.underlinePosition).append("\n        underlineThickness:    ").append(this.underlineThickness).append("\n        isFixedPitch:          ").append(this.isFixedPitch).append("\n        minMemType42:          ").append(this.minMemType42).append("\n        maxMemType42:          ").append(this.maxMemType42).append("\n        minMemType1:           ").append(this.minMemType1).append("\n        maxMemType1:           ").append(this.maxMemType1);
        if (this.version == 131072) {
            sb.append("\n\n        Format 2.0:  Non-Standard (for PostScript) TrueType Glyph Set.\n");
            sb.append("        numGlyphs:      ").append(this.numGlyphs).append("\n");
            for (int i = 0; i < this.numGlyphs; ++i) {
                sb.append("        Glyf ").append(i).append(" -> ");
                if (this.isMacGlyphName(i)) {
                    sb.append("Mac Glyph # ").append(this.glyphNameIndex[i]).append(", '").append(PostTable.macGlyphName[this.glyphNameIndex[i]]).append("'\n");
                }
                else {
                    sb.append("PSGlyf Name # ").append(this.glyphNameIndex[i] - 257).append(", name= '").append(this.psGlyphName[this.glyphNameIndex[i] - 258]).append("'\n");
                }
            }
            sb.append("\n        Full List of PSGlyf Names\n        ------------------------\n");
            for (int j = 0; j < this.psGlyphName.length; ++j) {
                sb.append("        PSGlyf Name # ").append(j + 1).append(": ").append(this.psGlyphName[j]).append("\n");
            }
        }
        return sb.toString();
    }
    
    @Override
    public DirectoryEntry getDirectoryEntry() {
        return this.de;
    }
    
    static {
        macGlyphName = new String[] { ".notdef", "null", "CR", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "Adieresis", "Aring", "Ccedilla", "Eacute", "Ntilde", "Odieresis", "Udieresis", "aacute", "agrave", "acircumflex", "adieresis", "atilde", "aring", "ccedilla", "eacute", "egrave", "ecircumflex", "edieresis", "iacute", "igrave", "icircumflex", "idieresis", "ntilde", "oacute", "ograve", "ocircumflex", "odieresis", "otilde", "uacute", "ugrave", "ucircumflex", "udieresis", "dagger", "degree", "cent", "sterling", "section", "bullet", "paragraph", "germandbls", "registered", "copyright", "trademark", "acute", "dieresis", "notequal", "AE", "Oslash", "infinity", "plusminus", "lessequal", "greaterequal", "yen", "mu", "partialdiff", "summation", "product", "pi", "integral'", "ordfeminine", "ordmasculine", "Omega", "ae", "oslash", "questiondown", "exclamdown", "logicalnot", "radical", "florin", "approxequal", "increment", "guillemotleft", "guillemotright", "ellipsis", "nbspace", "Agrave", "Atilde", "Otilde", "OE", "oe", "endash", "emdash", "quotedblleft", "quotedblright", "quoteleft", "quoteright", "divide", "lozenge", "ydieresis", "Ydieresis", "fraction", "currency", "guilsinglleft", "guilsinglright", "fi", "fl", "daggerdbl", "middot", "quotesinglbase", "quotedblbase", "perthousand", "Acircumflex", "Ecircumflex", "Aacute", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Oacute", "Ocircumflex", "", "Ograve", "Uacute", "Ucircumflex", "Ugrave", "dotlessi", "circumflex", "tilde", "overscore", "breve", "dotaccent", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "Lslash", "lslash", "Scaron", "scaron", "Zcaron", "zcaron", "brokenbar", "Eth", "eth", "Yacute", "yacute", "Thorn", "thorn", "minus", "multiply", "onesuperior", "twosuperior", "threesuperior", "onehalf", "onequarter", "threequarters", "franc", "Gbreve", "gbreve", "Idot", "Scedilla", "scedilla", "Cacute", "cacute", "Ccaron", "ccaron", "" };
    }
}
