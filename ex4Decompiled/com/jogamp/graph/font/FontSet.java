// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.graph.font;

import java.io.IOException;

public interface FontSet
{
    public static final int FAMILY_REGULAR = 0;
    public static final int FAMILY_LIGHT = 1;
    public static final int FAMILY_MEDIUM = 2;
    public static final int FAMILY_CONDENSED = 3;
    public static final int FAMILY_MONOSPACED = 4;
    public static final int STYLE_NONE = 0;
    public static final int STYLE_SERIF = 2;
    public static final int STYLE_BOLD = 4;
    public static final int STYLE_ITALIC = 8;
    
    Font getDefault() throws IOException;
    
    Font get(final int p0, final int p1) throws IOException;
}
