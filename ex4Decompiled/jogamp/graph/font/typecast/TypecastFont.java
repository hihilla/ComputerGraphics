// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast;

import com.jogamp.graph.font.FontFactory;
import jogamp.graph.geom.plane.AffineTransform;
import com.jogamp.opengl.math.geom.AABBox;
import jogamp.graph.font.typecast.ot.table.HdmxTable;
import jogamp.graph.font.typecast.ot.OTGlyph;
import jogamp.graph.font.typecast.ot.table.CmapIndexEntry;
import jogamp.graph.font.typecast.ot.table.CmapTable;
import jogamp.graph.font.typecast.ot.OTFontCollection;
import com.jogamp.common.util.IntObjectHashMap;
import jogamp.graph.font.typecast.ot.table.CmapFormat;
import jogamp.graph.font.typecast.ot.OTFont;
import com.jogamp.graph.geom.SVertex;
import com.jogamp.graph.geom.Vertex;
import com.jogamp.graph.font.Font;

class TypecastFont implements Font
{
    static final boolean DEBUG = false;
    private static final Vertex.Factory<SVertex> vertexFactory;
    final OTFont font;
    private final CmapFormat cmapFormat;
    private final int cmapentries;
    private final IntObjectHashMap char2Glyph;
    private final TypecastHMetrics metrics;
    private final float[] tmpV3;
    
    public TypecastFont(final OTFontCollection collection) {
        this.tmpV3 = new float[3];
        this.font = collection.getFont(0);
        final CmapTable cmapTable = this.font.getCmapTable();
        final CmapFormat[] array = { null, null, null, null };
        int n = -1;
        int length = -1;
        for (int i = 0; i < cmapTable.getNumTables(); ++i) {
            final CmapIndexEntry cmapIndexEntry = cmapTable.getCmapIndexEntry(i);
            final int platformId = cmapIndexEntry.getPlatformId();
            final CmapFormat format = cmapIndexEntry.getFormat();
            if (array[platformId] == null || array[platformId].getLength() < format.getLength()) {
                array[platformId] = format;
                if (format.getLength() > length) {
                    length = format.getLength();
                    n = platformId;
                    cmapIndexEntry.getEncodingId();
                }
            }
        }
        if (0 <= n) {
            this.cmapFormat = array[n];
        }
        else {
            CmapFormat cmapFormat = null;
            if (null == cmapFormat) {
                cmapFormat = cmapTable.getCmapFormat((short)3, (short)1);
            }
            if (null == cmapFormat) {
                cmapFormat = cmapTable.getCmapFormat((short)3, (short)0);
            }
            if (null == cmapFormat) {
                throw new RuntimeException("Cannot find a suitable cmap table for font " + this.font);
            }
            this.cmapFormat = cmapFormat;
        }
        int cmapentries = 0;
        for (int j = 0; j < this.cmapFormat.getRangeCount(); ++j) {
            final CmapFormat.Range range = this.cmapFormat.getRange(j);
            cmapentries += range.getEndCode() - range.getStartCode() + 1;
        }
        this.cmapentries = cmapentries;
        this.char2Glyph = new IntObjectHashMap(this.cmapentries + this.cmapentries / 4);
        this.metrics = new TypecastHMetrics(this);
    }
    
    @Override
    public StringBuilder getName(final StringBuilder sb, final int n) {
        return this.font.getName(n, sb);
    }
    
    @Override
    public String getName(final int n) {
        return this.getName(null, n).toString();
    }
    
    @Override
    public StringBuilder getAllNames(final StringBuilder sb, final String s) {
        return this.font.getAllNames(sb, s);
    }
    
    @Override
    public StringBuilder getFullFamilyName(StringBuilder append) {
        append = this.getName(append, 1).append("-");
        this.getName(append, 2);
        return append;
    }
    
    @Override
    public float getAdvanceWidth(final int n, final float n2) {
        return this.font.getHmtxTable().getAdvanceWidth(n) * this.metrics.getScale(n2);
    }
    
    @Override
    public final Metrics getMetrics() {
        return this.metrics;
    }
    
    @Override
    public Glyph getGlyph(final char c) {
        TypecastGlyph typecastGlyph = (TypecastGlyph)this.char2Glyph.get(c);
        if (null == typecastGlyph) {
            short n = (short)this.cmapFormat.mapCharCode(c);
            if (n == 0 && '\0' != c) {
                switch (c) {
                    case ' ': {
                        n = 3;
                        break;
                    }
                    case '\n': {
                        n = 2;
                        break;
                    }
                    default: {
                        n = 0;
                        break;
                    }
                }
            }
            OTGlyph otGlyph = this.font.getGlyph(n);
            if (null == otGlyph) {
                otGlyph = this.font.getGlyph(0);
            }
            if (null == otGlyph) {
                throw new RuntimeException("Could not retrieve glyph for symbol: <" + c + "> " + (int)c + " -> glyph id " + n);
            }
            typecastGlyph = new TypecastGlyph(this, c, n, otGlyph.getBBox(), otGlyph.getAdvanceWidth(), TypecastRenderer.buildShape(c, otGlyph, TypecastFont.vertexFactory));
            otGlyph.clearPointData();
            final HdmxTable hdmxTable = this.font.getHdmxTable();
            if (null != typecastGlyph && null != hdmxTable) {
                for (int i = 0; i < hdmxTable.getNumberOfRecords(); ++i) {
                    final HdmxTable.DeviceRecord record = hdmxTable.getRecord(i);
                    typecastGlyph.addAdvance(record.getWidth(n), record.getPixelSize());
                }
            }
            this.char2Glyph.put(c, typecastGlyph);
        }
        return typecastGlyph;
    }
    
    @Override
    public final float getPixelSize(final float n, final float n2) {
        return n * n2 / 72.0f;
    }
    
    @Override
    public float getLineHeight(final float n) {
        final Metrics metrics = this.getMetrics();
        return -(metrics.getLineGap(n) - metrics.getDescent(n) + metrics.getAscent(n));
    }
    
    @Override
    public float getMetricWidth(final CharSequence charSequence, final float n) {
        float n2 = 0.0f;
        for (int length = charSequence.length(), i = 0; i < length; ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 == '\n') {
                n2 = 0.0f;
            }
            else {
                n2 += this.getGlyph(char1).getAdvance(n, false);
            }
        }
        return (int)(n2 + 0.5f);
    }
    
    @Override
    public float getMetricHeight(final CharSequence charSequence, final float n, final AABBox aabBox) {
        int n2 = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 != ' ') {
                n2 = (int)Math.ceil(Math.max(this.getGlyph(char1).getBBox(aabBox, n, this.tmpV3).getHeight(), n2));
            }
        }
        return n2;
    }
    
    @Override
    public AABBox getMetricBounds(final CharSequence charSequence, final float n) {
        if (charSequence == null) {
            return new AABBox();
        }
        final int length = charSequence.length();
        final float lineHeight = this.getLineHeight(n);
        float n2 = 0.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        for (int i = 0; i < length; ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 == '\n') {
                n3 = Math.max(n4, n3);
                n4 = 0.0f;
                n2 += lineHeight;
            }
            else {
                n4 += this.getGlyph(char1).getAdvance(n, true);
            }
        }
        if (n4 > 0.0f) {
            n2 += lineHeight;
            n3 = Math.max(n4, n3);
        }
        return new AABBox(0.0f, 0.0f, 0.0f, n3, n2, 0.0f);
    }
    
    @Override
    public AABBox getPointsBounds(final AffineTransform transform, final CharSequence charSequence, final float n, final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        if (charSequence == null) {
            return new AABBox();
        }
        final int length = charSequence.length();
        final float lineHeight = this.getLineHeight(n);
        final float scale = this.getMetrics().getScale(n);
        final AABBox aabBox = new AABBox();
        final AABBox aabBox2 = new AABBox();
        float n2 = 0.0f;
        float n3 = 0.0f;
        for (int i = 0; i < length; ++i) {
            final char char1 = charSequence.charAt(i);
            if ('\n' == char1) {
                n2 -= lineHeight;
                n3 = 0.0f;
            }
            else if (char1 == ' ') {
                n3 += this.getAdvanceWidth(3, n);
            }
            else {
                if (null != transform) {
                    affineTransform.setTransform(transform);
                }
                else {
                    affineTransform.setToIdentity();
                }
                affineTransform.translate(n3, n2, affineTransform2);
                affineTransform.scale(scale, scale, affineTransform2);
                aabBox.reset();
                final Glyph glyph = this.getGlyph(char1);
                aabBox2.resize(affineTransform.transform(glyph.getBBox(), aabBox));
                if (null != glyph.getShape()) {
                    n3 += glyph.getAdvance(n, true);
                }
            }
        }
        return aabBox2;
    }
    
    @Override
    public final int getNumGlyphs() {
        return this.font.getNumGlyphs();
    }
    
    @Override
    public boolean isPrintableChar(final char c) {
        return FontFactory.isPrintableChar(c);
    }
    
    @Override
    public String toString() {
        return this.getFullFamilyName(null).toString();
    }
    
    static {
        vertexFactory = SVertex.factory();
    }
}
