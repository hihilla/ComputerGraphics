// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import com.jogamp.opengl.GLDrawable;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.GLPixelBuffer;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.GLReadBufferUtil;

public class AWTGLReadBufferUtil extends GLReadBufferUtil
{
    public AWTGLReadBufferUtil(final GLProfile glProfile, final boolean b) {
        super(new AWTGLPixelBuffer.AWTGLPixelBufferProvider(glProfile.isGL2ES3()), b, false);
    }
    
    public AWTGLPixelBuffer getAWTGLPixelBuffer() {
        return (AWTGLPixelBuffer)this.getPixelBuffer();
    }
    
    public BufferedImage readPixelsToBufferedImage(final GL gl, final boolean b) {
        return this.readPixelsToBufferedImage(gl, 0, 0, 0, 0, b);
    }
    
    public BufferedImage readPixelsToBufferedImage(final GL gl, final int n, final int n2, final int n3, final int n4, final boolean b) {
        final GLDrawable glReadDrawable = gl.getContext().getGLReadDrawable();
        int surfaceWidth;
        if (0 >= n3 || glReadDrawable.getSurfaceWidth() < n3) {
            surfaceWidth = glReadDrawable.getSurfaceWidth();
        }
        else {
            surfaceWidth = n3;
        }
        int surfaceHeight;
        if (0 >= n4 || glReadDrawable.getSurfaceHeight() < n4) {
            surfaceHeight = glReadDrawable.getSurfaceHeight();
        }
        else {
            surfaceHeight = n4;
        }
        if (this.readPixelsImpl(glReadDrawable, gl, n, n2, surfaceWidth, surfaceHeight, b)) {
            final BufferedImage alignedImage = this.getAWTGLPixelBuffer().getAlignedImage(surfaceWidth, surfaceHeight);
            if (this.getTextureData().getMustFlipVertically()) {
                ImageUtil.flipImageVertically(alignedImage);
            }
            return alignedImage;
        }
        return null;
    }
}
