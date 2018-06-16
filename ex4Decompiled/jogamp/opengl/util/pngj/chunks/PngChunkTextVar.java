// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import jogamp.opengl.util.pngj.ImageInfo;

public abstract class PngChunkTextVar extends PngChunkMultiple
{
    protected String key;
    protected String val;
    public static final String KEY_Title = "Title";
    public static final String KEY_Author = "Author";
    public static final String KEY_Description = "Description";
    public static final String KEY_Copyright = "Copyright";
    public static final String KEY_Creation_Time = "Creation Time";
    public static final String KEY_Software = "Software";
    public static final String KEY_Disclaimer = "Disclaimer";
    public static final String KEY_Warning = "Warning";
    public static final String KEY_Source = "Source";
    public static final String KEY_Comment = "Comment";
    
    protected PngChunkTextVar(final String s, final ImageInfo imageInfo) {
        super(s, imageInfo);
    }
    
    @Override
    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getVal() {
        return this.val;
    }
    
    public void setKeyVal(final String key, final String val) {
        this.key = key;
        this.val = val;
    }
    
    public static class PngTxtInfo
    {
        public String title;
        public String author;
        public String description;
        public String creation_time;
        public String software;
        public String disclaimer;
        public String warning;
        public String source;
        public String comment;
    }
}
