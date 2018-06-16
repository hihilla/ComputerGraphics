// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.curve.opengl;

import com.jogamp.opengl.GL2ES2;
import jogamp.graph.curve.opengl.VBORegionSPES2;
import jogamp.graph.curve.opengl.VBORegion2PMSAAES2;
import jogamp.graph.curve.opengl.VBORegion2PVBAAES2;
import com.jogamp.opengl.util.texture.TextureSequence;
import com.jogamp.graph.curve.Region;

public abstract class GLRegion extends Region
{
    protected final TextureSequence colorTexSeq;
    
    public static GLRegion create(int n, final TextureSequence textureSequence) {
        if (null != textureSequence) {
            n |= 0x400;
        }
        else if (Region.hasColorTexture(n)) {
            throw new IllegalArgumentException("COLORTEXTURE_RENDERING_BIT set but null TextureSequence");
        }
        if (Region.isVBAA(n)) {
            return new VBORegion2PVBAAES2(n, textureSequence, 0);
        }
        if (Region.isMSAA(n)) {
            return new VBORegion2PMSAAES2(n, textureSequence, 0);
        }
        return new VBORegionSPES2(n, textureSequence);
    }
    
    protected GLRegion(final int n, final TextureSequence colorTexSeq) {
        super(n);
        this.colorTexSeq = colorTexSeq;
    }
    
    protected abstract void updateImpl(final GL2ES2 p0);
    
    protected abstract void destroyImpl(final GL2ES2 p0);
    
    protected abstract void clearImpl(final GL2ES2 p0);
    
    public void clear(final GL2ES2 gl2ES2) {
        this.clearImpl(gl2ES2);
        this.clearImpl();
    }
    
    public final void destroy(final GL2ES2 gl2ES2) {
        this.clear(gl2ES2);
        this.destroyImpl(gl2ES2);
    }
    
    public final void draw(final GL2ES2 gl2ES2, final RegionRenderer regionRenderer, final int[] array) {
        if (this.isShapeDirty()) {
            this.updateImpl(gl2ES2);
        }
        this.drawImpl(gl2ES2, regionRenderer, array);
        this.clearDirtyBits(3);
    }
    
    protected abstract void drawImpl(final GL2ES2 p0, final RegionRenderer p1, final int[] p2);
}
