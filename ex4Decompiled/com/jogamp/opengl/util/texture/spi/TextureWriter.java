// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import java.io.IOException;
import com.jogamp.opengl.util.texture.TextureData;
import java.io.File;

public interface TextureWriter
{
    boolean write(final File p0, final TextureData p1) throws IOException;
}
