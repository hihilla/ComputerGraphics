// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import jogamp.opengl.Debug;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLException;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.nativewindow.util.Dimension;

public abstract class TileRendererBase
{
    public static final int TR_IMAGE_WIDTH = 1;
    public static final int TR_IMAGE_HEIGHT = 2;
    public static final int TR_CURRENT_TILE_X_POS = 3;
    public static final int TR_CURRENT_TILE_Y_POS = 4;
    public static final int TR_CURRENT_TILE_WIDTH = 5;
    public static final int TR_CURRENT_TILE_HEIGHT = 6;
    static final boolean DEBUG;
    protected final Dimension imageSize;
    protected final GLPixelStorageModes psm;
    protected GLPixelBuffer imageBuffer;
    protected GLPixelBuffer tileBuffer;
    protected boolean beginCalled;
    protected int currentTileXPos;
    protected int currentTileYPos;
    protected int currentTileWidth;
    protected int currentTileHeight;
    protected GLAutoDrawable glad;
    protected boolean gladRequiresPreSwap;
    protected boolean gladAutoSwapBufferMode;
    protected GLEventListener[] listeners;
    protected boolean[] listenersInit;
    protected GLEventListener glEventListenerPre;
    protected GLEventListener glEventListenerPost;
    private final GLEventListener tiledGLEL;
    
    private final String hashStr(final Object o) {
        return "0x" + Integer.toHexString((null != o) ? o.hashCode() : 0);
    }
    
    protected StringBuilder tileDetails(final StringBuilder sb) {
        return sb.append("cur " + this.currentTileXPos + "/" + this.currentTileYPos + " " + this.currentTileWidth + "x" + this.currentTileHeight + ", buffer " + this.hashStr(this.tileBuffer));
    }
    
    public StringBuilder toString(final StringBuilder sb) {
        final int n = (null != this.listeners) ? this.listeners.length : 0;
        sb.append("tile[");
        this.tileDetails(sb);
        sb.append("], image[size " + this.imageSize + ", buffer " + this.hashStr(this.imageBuffer) + "], glad[" + n + " listener, pre " + (null != this.glEventListenerPre) + ", post " + (null != this.glEventListenerPost) + ", preSwap " + this.gladRequiresPreSwap + "]");
        sb.append(", isSetup " + this.isSetup());
        return sb;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.toString(new StringBuilder()).toString() + "]";
    }
    
    protected TileRendererBase() {
        this.imageSize = new Dimension(0, 0);
        this.psm = new GLPixelStorageModes();
        this.beginCalled = false;
        this.gladAutoSwapBufferMode = true;
        this.glEventListenerPre = null;
        this.glEventListenerPost = null;
        this.tiledGLEL = new GLEventListener() {
            final TileRenderer tileRenderer = (TileRendererBase.this instanceof TileRenderer) ? ((TileRenderer)TileRendererBase.this) : null;
            
            @Override
            public void init(final GLAutoDrawable glAutoDrawable) {
                if (null != TileRendererBase.this.glEventListenerPre) {
                    TileRendererBase.this.glEventListenerPre.init(glAutoDrawable);
                }
                for (int length = TileRendererBase.this.listenersInit.length, i = 0; i < length; ++i) {
                    final GLEventListener glEventListener = TileRendererBase.this.listeners[i];
                    if (!TileRendererBase.this.listenersInit[i] && glEventListener instanceof TileRendererListener) {
                        glEventListener.init(glAutoDrawable);
                        TileRendererBase.this.listenersInit[i] = true;
                    }
                }
                if (null != TileRendererBase.this.glEventListenerPost) {
                    TileRendererBase.this.glEventListenerPost.init(glAutoDrawable);
                }
            }
            
            @Override
            public void dispose(final GLAutoDrawable glAutoDrawable) {
                if (null != TileRendererBase.this.glEventListenerPre) {
                    TileRendererBase.this.glEventListenerPre.dispose(glAutoDrawable);
                }
                for (int length = TileRendererBase.this.listenersInit.length, i = 0; i < length; ++i) {
                    TileRendererBase.this.listeners[i].dispose(glAutoDrawable);
                }
                if (null != TileRendererBase.this.glEventListenerPost) {
                    TileRendererBase.this.glEventListenerPost.dispose(glAutoDrawable);
                }
            }
            
            @Override
            public void display(final GLAutoDrawable glAutoDrawable) {
                if (null != TileRendererBase.this.glEventListenerPre) {
                    TileRendererBase.this.glEventListenerPre.reshape(glAutoDrawable, 0, 0, TileRendererBase.this.currentTileWidth, TileRendererBase.this.currentTileHeight);
                    TileRendererBase.this.glEventListenerPre.display(glAutoDrawable);
                }
                if (!TileRendererBase.this.isSetup()) {
                    if (TileRendererBase.DEBUG) {
                        System.err.println("TileRenderer.glel.display: !setup: " + TileRendererBase.this);
                    }
                    return;
                }
                if (TileRendererBase.this.eot()) {
                    if (TileRendererBase.DEBUG) {
                        System.err.println("TileRenderer.glel.display: EOT: " + TileRendererBase.this);
                    }
                    return;
                }
                final GL gl = glAutoDrawable.getGL();
                TileRendererBase.this.beginTile(gl);
                final int length = TileRendererBase.this.listenersInit.length;
                for (int i = 0; i < length; ++i) {
                    final GLEventListener glEventListener = TileRendererBase.this.listeners[i];
                    if (glEventListener instanceof TileRendererListener) {
                        final TileRendererListener tileRendererListener = (TileRendererListener)glEventListener;
                        if (null == this.tileRenderer || 0 == this.tileRenderer.getCurrentTile()) {
                            tileRendererListener.startTileRendering(TileRendererBase.this);
                        }
                        tileRendererListener.reshapeTile(TileRendererBase.this, TileRendererBase.this.currentTileXPos, TileRendererBase.this.currentTileYPos, TileRendererBase.this.currentTileWidth, TileRendererBase.this.currentTileHeight, TileRendererBase.this.imageSize.getWidth(), TileRendererBase.this.imageSize.getHeight());
                        glEventListener.display(glAutoDrawable);
                    }
                }
                if (TileRendererBase.this.gladRequiresPreSwap) {
                    TileRendererBase.this.glad.swapBuffers();
                    TileRendererBase.this.endTile(gl);
                }
                else {
                    TileRendererBase.this.endTile(gl);
                    TileRendererBase.this.glad.swapBuffers();
                }
                if (null == this.tileRenderer || this.tileRenderer.eot()) {
                    for (int j = 0; j < length; ++j) {
                        final GLEventListener glEventListener2 = TileRendererBase.this.listeners[j];
                        if (glEventListener2 instanceof TileRendererListener) {
                            ((TileRendererListener)glEventListener2).endTileRendering(TileRendererBase.this);
                        }
                    }
                }
                if (null != TileRendererBase.this.glEventListenerPost) {
                    TileRendererBase.this.glEventListenerPost.reshape(glAutoDrawable, 0, 0, TileRendererBase.this.currentTileWidth, TileRendererBase.this.currentTileHeight);
                    TileRendererBase.this.glEventListenerPost.display(glAutoDrawable);
                }
            }
            
            @Override
            public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
            }
        };
    }
    
    public abstract int getParam(final int p0) throws IllegalArgumentException;
    
    public final void setTileBuffer(final GLPixelBuffer tileBuffer) {
        this.tileBuffer = tileBuffer;
        if (TileRendererBase.DEBUG) {
            System.err.println("TileRenderer: tile-buffer " + this.tileBuffer);
        }
    }
    
    public final GLPixelBuffer getTileBuffer() {
        return this.tileBuffer;
    }
    
    public void setImageSize(final int n, final int n2) {
        this.imageSize.set(n, n2);
    }
    
    public final DimensionImmutable getImageSize() {
        return this.imageSize;
    }
    
    public final void setImageBuffer(final GLPixelBuffer imageBuffer) {
        this.imageBuffer = imageBuffer;
        if (TileRendererBase.DEBUG) {
            System.err.println("TileRenderer: image-buffer " + this.imageBuffer);
        }
    }
    
    public final GLPixelBuffer getImageBuffer() {
        return this.imageBuffer;
    }
    
    final void validateGL(final GL gl) throws GLException {
        if (this.imageBuffer != null && !gl.isGL2ES3()) {
            throw new GLException("Using image-buffer w/ inssufficient GL context: " + gl.getContext().getGLVersion() + ", " + gl.getGLProfile());
        }
    }
    
    public abstract boolean isSetup();
    
    public abstract boolean eot();
    
    public abstract void reset();
    
    public abstract void beginTile(final GL p0) throws IllegalStateException, GLException;
    
    public abstract void endTile(final GL p0) throws IllegalStateException, GLException;
    
    public final boolean reqPreSwapBuffers(final GLCapabilitiesImmutable glCapabilitiesImmutable) {
        return GLDrawableUtil.swapBuffersBeforeRead(glCapabilitiesImmutable);
    }
    
    public final void attachAutoDrawable(final GLAutoDrawable glad) throws IllegalStateException {
        if (null != this.glad) {
            throw new IllegalStateException("GLAutoDrawable already attached");
        }
        this.glad = glad;
        final int glEventListenerCount = glad.getGLEventListenerCount();
        this.listeners = new GLEventListener[glEventListenerCount];
        this.listenersInit = new boolean[glEventListenerCount];
        for (int i = 0; i < glEventListenerCount; ++i) {
            final GLEventListener glEventListener = glad.getGLEventListener(0);
            this.listenersInit[i] = glad.getGLEventListenerInitState(glEventListener);
            this.listeners[i] = glad.removeGLEventListener(glEventListener);
            boolean b;
            if (this.listeners[i] instanceof TileRendererListener) {
                b = true;
                ((TileRendererListener)this.listeners[i]).addTileRendererNotify(this);
            }
            else {
                b = false;
            }
            if (TileRendererBase.DEBUG) {
                System.err.println("TileRenderer.attach[" + i + "]: isInit " + this.listenersInit[i] + ", isTRN " + b + ", " + this.listeners[i].getClass().getName());
            }
        }
        glad.addGLEventListener(this.tiledGLEL);
        this.gladAutoSwapBufferMode = glad.getAutoSwapBufferMode();
        this.gladRequiresPreSwap = this.reqPreSwapBuffers(glad.getChosenGLCapabilities());
        glad.setAutoSwapBufferMode(false);
        if (TileRendererBase.DEBUG) {
            System.err.println("TileRenderer: attached: " + glad);
            System.err.println("TileRenderer: preSwap " + this.gladRequiresPreSwap + ", " + glad.getChosenGLCapabilities() + ", cached " + this.listeners.length + " listener");
        }
    }
    
    public final GLAutoDrawable getAttachedDrawable() {
        return this.glad;
    }
    
    public final void detachAutoDrawable() {
        if (null != this.glad) {
            this.glad.removeGLEventListener(this.tiledGLEL);
            for (int length = this.listenersInit.length, i = 0; i < length; ++i) {
                final GLEventListener glEventListener = this.listeners[i];
                if (glEventListener instanceof TileRendererListener) {
                    ((TileRendererListener)glEventListener).removeTileRendererNotify(this);
                }
                this.glad.addGLEventListener(glEventListener);
                this.glad.setGLEventListenerInitState(glEventListener, this.listenersInit[i]);
            }
            this.glad.setAutoSwapBufferMode(this.gladAutoSwapBufferMode);
            if (TileRendererBase.DEBUG) {
                System.err.println("TileRenderer: detached: " + this.glad);
                System.err.println("TileRenderer: " + this.glad.getChosenGLCapabilities());
            }
            this.listeners = null;
            this.listenersInit = null;
            this.glad = null;
        }
    }
    
    public final void setGLEventListener(final GLEventListener glEventListenerPre, final GLEventListener glEventListenerPost) {
        this.glEventListenerPre = glEventListenerPre;
        this.glEventListenerPost = glEventListenerPost;
    }
    
    public final void display() throws IllegalStateException {
        if (null == this.glad) {
            throw new IllegalStateException("No GLAutoDrawable attached");
        }
        this.glad.display();
    }
    
    static {
        DEBUG = Debug.debug("TileRenderer");
    }
    
    public interface TileRendererListener
    {
        void addTileRendererNotify(final TileRendererBase p0);
        
        void removeTileRendererNotify(final TileRendererBase p0);
        
        void reshapeTile(final TileRendererBase p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
        
        void startTileRendering(final TileRendererBase p0);
        
        void endTileRendering(final TileRendererBase p0);
    }
}
