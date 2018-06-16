// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public abstract class PngChunkSingle extends PngChunk
{
    protected PngChunkSingle(final String s, final ImageInfo imageInfo) {
        super(s, imageInfo);
    }
    
    @Override
    public final boolean allowsMultiple() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * 1 + ((this.id == null) ? 0 : this.id.hashCode());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final PngChunkSingle pngChunkSingle = (PngChunkSingle)o;
        if (this.id == null) {
            if (pngChunkSingle.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(pngChunkSingle.id)) {
            return false;
        }
        return true;
    }
}
