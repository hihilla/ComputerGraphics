// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;
import java.util.ArrayList;

public class GlyfCompositeDescript extends GlyfDescript
{
    private final ArrayList<GlyfCompositeComp> _components;
    
    public GlyfCompositeDescript(final GlyfTable glyfTable, final int n, final DataInput dataInput) throws IOException {
        super(glyfTable, n, (short)(-1), dataInput);
        this._components = new ArrayList<GlyfCompositeComp>();
        int n2 = 0;
        int n3 = 0;
        try {
            GlyfCompositeComp glyfCompositeComp;
            do {
                this._components.add(glyfCompositeComp = new GlyfCompositeComp(n2, n3, dataInput));
                final GlyfDescript description = glyfTable.getDescription(glyfCompositeComp.getGlyphIndex());
                if (description != null) {
                    n2 += description.getPointCount();
                    n3 += description.getContourCount();
                }
            } while ((glyfCompositeComp.getFlags() & 0x20) != 0x0);
            if ((glyfCompositeComp.getFlags() & 0x100) != 0x0) {
                this.readInstructions(dataInput, dataInput.readShort());
            }
        }
        catch (IOException ex) {
            throw ex;
        }
    }
    
    @Override
    public int getEndPtOfContours(final int n) {
        final GlyfCompositeComp compositeCompEndPt = this.getCompositeCompEndPt(n);
        if (compositeCompEndPt != null) {
            return this._parentTable.getDescription(compositeCompEndPt.getGlyphIndex()).getEndPtOfContours(n - compositeCompEndPt.getFirstContour()) + compositeCompEndPt.getFirstIndex();
        }
        return 0;
    }
    
    @Override
    public byte getFlags(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            return this._parentTable.getDescription(compositeComp.getGlyphIndex()).getFlags(n - compositeComp.getFirstIndex());
        }
        return 0;
    }
    
    @Override
    public short getXCoordinate(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            final GlyfDescript description = this._parentTable.getDescription(compositeComp.getGlyphIndex());
            final int n2 = n - compositeComp.getFirstIndex();
            return (short)((short)compositeComp.scaleX(description.getXCoordinate(n2), description.getYCoordinate(n2)) + compositeComp.getXTranslate());
        }
        return 0;
    }
    
    @Override
    public short getYCoordinate(final int n) {
        final GlyfCompositeComp compositeComp = this.getCompositeComp(n);
        if (compositeComp != null) {
            final GlyfDescript description = this._parentTable.getDescription(compositeComp.getGlyphIndex());
            final int n2 = n - compositeComp.getFirstIndex();
            return (short)((short)compositeComp.scaleY(description.getXCoordinate(n2), description.getYCoordinate(n2)) + compositeComp.getYTranslate());
        }
        return 0;
    }
    
    @Override
    public boolean isComposite() {
        return true;
    }
    
    @Override
    public int getPointCount() {
        final GlyfCompositeComp glyfCompositeComp = this._components.get(this._components.size() - 1);
        final GlyfDescript description = this._parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
        if (description != null) {
            return glyfCompositeComp.getFirstIndex() + description.getPointCount();
        }
        return 0;
    }
    
    @Override
    public int getContourCount() {
        final GlyfCompositeComp glyfCompositeComp = this._components.get(this._components.size() - 1);
        final GlyfDescript description = this._parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
        return glyfCompositeComp.getFirstContour() + ((null != description) ? description.getContourCount() : 0);
    }
    
    public int getComponentIndex(final int n) {
        return this._components.get(n).getFirstIndex();
    }
    
    public int getComponentCount() {
        return this._components.size();
    }
    
    public GlyfCompositeComp getComponent(final int n) {
        return this._components.get(n);
    }
    
    protected GlyfCompositeComp getCompositeComp(final int n) {
        for (int i = 0; i < this._components.size(); ++i) {
            final GlyfCompositeComp glyfCompositeComp = this._components.get(i);
            final GlyfDescript description = this._parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
            if (glyfCompositeComp.getFirstIndex() <= n && n < glyfCompositeComp.getFirstIndex() + description.getPointCount()) {
                return glyfCompositeComp;
            }
        }
        return null;
    }
    
    protected GlyfCompositeComp getCompositeCompEndPt(final int n) {
        for (int i = 0; i < this._components.size(); ++i) {
            final GlyfCompositeComp glyfCompositeComp = this._components.get(i);
            final GlyfDescript description = this._parentTable.getDescription(glyfCompositeComp.getGlyphIndex());
            if (glyfCompositeComp.getFirstContour() <= n && n < glyfCompositeComp.getFirstContour() + description.getContourCount()) {
                return glyfCompositeComp;
            }
        }
        return null;
    }
}
