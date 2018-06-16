// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast;

import jogamp.graph.font.typecast.ot.table.HheaTable;
import com.jogamp.opengl.math.geom.AABBox;
import jogamp.graph.font.typecast.ot.table.HeadTable;
import com.jogamp.graph.font.Font;

class TypecastHMetrics implements Font.Metrics
{
    private final TypecastFont fontImpl;
    private final HeadTable headTable;
    private final float unitsPerEM_Inv;
    private final AABBox bbox;
    private final HheaTable hheaTable;
    
    public TypecastHMetrics(final TypecastFont fontImpl) {
        this.fontImpl = fontImpl;
        this.headTable = this.fontImpl.font.getHeadTable();
        this.hheaTable = this.fontImpl.font.getHheaTable();
        this.unitsPerEM_Inv = 1.0f / this.headTable.getUnitsPerEm();
        final short n = (short)(this.headTable.getXMax() - this.headTable.getXMin());
        final short n2 = (short)(this.headTable.getYMax() - this.headTable.getYMin());
        final float n3 = this.headTable.getXMin();
        final float n4 = -(this.headTable.getYMin() + n2);
        this.bbox = new AABBox(n3, n4, 0.0f, n3 + n, n4 + n2, 0.0f);
    }
    
    @Override
    public final float getAscent(final float n) {
        return this.getScale(n) * -this.hheaTable.getAscender();
    }
    
    @Override
    public final float getDescent(final float n) {
        return this.getScale(n) * -this.hheaTable.getDescender();
    }
    
    @Override
    public final float getLineGap(final float n) {
        return this.getScale(n) * -this.hheaTable.getLineGap();
    }
    
    @Override
    public final float getMaxExtend(final float n) {
        return this.getScale(n) * this.hheaTable.getXMaxExtent();
    }
    
    @Override
    public final float getScale(final float n) {
        return n * this.unitsPerEM_Inv;
    }
    
    @Override
    public final AABBox getBBox(final AABBox aabBox, final float n, final float[] array) {
        return aabBox.setSize(this.bbox.getLow(), this.bbox.getHigh()).scale(this.getScale(n), array);
    }
}
