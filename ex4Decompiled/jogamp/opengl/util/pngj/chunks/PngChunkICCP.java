// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.PngjException;
import jogamp.opengl.util.pngj.PngHelperInternal;
import jogamp.opengl.util.pngj.ImageInfo;

public class PngChunkICCP extends PngChunkSingle
{
    public static final String ID = "iCCP";
    private String profileName;
    private byte[] compressedProfile;
    
    public PngChunkICCP(final ImageInfo imageInfo) {
        super("iCCP", imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }
    
    @Override
    public ChunkRaw createRawChunk() {
        final ChunkRaw emptyChunk = this.createEmptyChunk(this.profileName.length() + this.compressedProfile.length + 2, true);
        System.arraycopy(ChunkHelper.toBytes(this.profileName), 0, emptyChunk.data, 0, this.profileName.length());
        emptyChunk.data[this.profileName.length()] = 0;
        emptyChunk.data[this.profileName.length() + 1] = 0;
        System.arraycopy(this.compressedProfile, 0, emptyChunk.data, this.profileName.length() + 2, this.compressedProfile.length);
        return emptyChunk;
    }
    
    @Override
    public void parseFromRaw(final ChunkRaw chunkRaw) {
        final int posNullByte = ChunkHelper.posNullByte(chunkRaw.data);
        this.profileName = new String(chunkRaw.data, 0, posNullByte, PngHelperInternal.charsetLatin1);
        if ((chunkRaw.data[posNullByte + 1] & 0xFF) != 0x0) {
            throw new PngjException("bad compression for ChunkTypeICCP");
        }
        final int n = chunkRaw.data.length - (posNullByte + 2);
        this.compressedProfile = new byte[n];
        System.arraycopy(chunkRaw.data, posNullByte + 2, this.compressedProfile, 0, n);
    }
    
    @Override
    public void cloneDataFromRead(final PngChunk pngChunk) {
        final PngChunkICCP pngChunkICCP = (PngChunkICCP)pngChunk;
        this.profileName = pngChunkICCP.profileName;
        this.compressedProfile = new byte[pngChunkICCP.compressedProfile.length];
        System.arraycopy(pngChunkICCP.compressedProfile, 0, this.compressedProfile, 0, pngChunkICCP.compressedProfile.length);
    }
    
    public void setProfileNameAndContent(final String profileName, final byte[] array) {
        this.profileName = profileName;
        this.compressedProfile = ChunkHelper.compressBytes(array, true);
    }
    
    public void setProfileNameAndContent(final String s, final String s2) {
        this.setProfileNameAndContent(s, s2.getBytes(PngHelperInternal.charsetLatin1));
    }
    
    public String getProfileName() {
        return this.profileName;
    }
    
    public byte[] getProfile() {
        return ChunkHelper.compressBytes(this.compressedProfile, false);
    }
    
    public String getProfileAsString() {
        return new String(this.getProfile(), PngHelperInternal.charsetLatin1);
    }
}
