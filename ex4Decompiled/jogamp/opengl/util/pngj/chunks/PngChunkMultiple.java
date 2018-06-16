// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public abstract class PngChunkMultiple extends PngChunk
{
    protected PngChunkMultiple(final String s, final ImageInfo imageInfo) {
        super(s, imageInfo);
    }
    
    @Override
    public final boolean allowsMultiple() {
        return true;
    }
}
