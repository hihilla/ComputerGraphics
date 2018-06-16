// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.awt;

import java.awt.EventQueue;
import com.jogamp.opengl.glu.GLU;
import java.nio.Buffer;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.awt.font.GlyphMetrics;
import com.jogamp.opengl.util.texture.TextureCoords;
import java.awt.font.GlyphVector;
import java.awt.image.ImageObserver;
import java.text.CharacterIterator;
import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import java.awt.event.WindowListener;
import com.jogamp.common.util.InterruptSource;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.Component;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLCapabilities;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import com.jogamp.opengl.util.packrect.RectVisitor;
import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import java.awt.RenderingHints;
import java.awt.Composite;
import java.awt.AlphaComposite;
import com.jogamp.opengl.GLException;
import java.awt.geom.Rectangle2D;
import com.jogamp.opengl.util.packrect.BackingStoreManager;
import java.util.HashMap;
import java.awt.Frame;
import java.awt.Color;
import com.jogamp.opengl.util.packrect.Rect;
import java.util.Map;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import com.jogamp.opengl.util.packrect.RectanglePacker;
import java.awt.Font;

public class TextRenderer
{
    private static final boolean DEBUG;
    private static final boolean DISABLE_GLYPH_CACHE = false;
    private static final boolean DRAW_BBOXES = false;
    static final int kSize = 256;
    private static final int CYCLES_PER_FLUSH = 100;
    private static final float MAX_VERTICAL_FRAGMENTATION = 0.7f;
    static final int kQuadsPerBuffer = 100;
    static final int kCoordsPerVertVerts = 3;
    static final int kCoordsPerVertTex = 2;
    static final int kVertsPerQuad = 4;
    static final int kTotalBufferSizeVerts = 400;
    static final int kTotalBufferSizeCoordsVerts = 1200;
    static final int kTotalBufferSizeCoordsTex = 800;
    static final int kTotalBufferSizeBytesVerts = 4800;
    static final int kTotalBufferSizeBytesTex = 3200;
    static final int kSizeInBytes_OneVertices_VertexData = 12;
    static final int kSizeInBytes_OneVertices_TexData = 8;
    private final Font font;
    private final boolean antialiased;
    private final boolean useFractionalMetrics;
    private boolean mipmap;
    private RectanglePacker packer;
    private boolean haveMaxSize;
    private final RenderDelegate renderDelegate;
    private TextureRenderer cachedBackingStore;
    private Graphics2D cachedGraphics;
    private FontRenderContext cachedFontRenderContext;
    private final Map<String, Rect> stringLocations;
    private final GlyphProducer mGlyphProducer;
    private int numRenderCycles;
    private boolean inBeginEndPair;
    private boolean isOrthoMode;
    private int beginRenderingWidth;
    private int beginRenderingHeight;
    private boolean beginRenderingDepthTestDisabled;
    private boolean haveCachedColor;
    private float cachedR;
    private float cachedG;
    private float cachedB;
    private float cachedA;
    private Color cachedColor;
    private boolean needToResetColor;
    private Frame dbgFrame;
    private boolean debugged;
    Pipelined_QuadRenderer mPipelinedQuadRenderer;
    private boolean useVertexArrays;
    private boolean isExtensionAvailable_GL_VERSION_1_5;
    private boolean checkFor_isExtensionAvailable_GL_VERSION_1_5;
    private boolean smoothing;
    private final char[] singleUnicode;
    
    public TextRenderer(final Font font) {
        this(font, false, false, null, false);
    }
    
    public TextRenderer(final Font font, final boolean b) {
        this(font, false, false, null, b);
    }
    
    public TextRenderer(final Font font, final boolean b, final boolean b2) {
        this(font, b, b2, null, false);
    }
    
    public TextRenderer(final Font font, final boolean b, final boolean b2, final RenderDelegate renderDelegate) {
        this(font, b, b2, renderDelegate, false);
    }
    
    public TextRenderer(final Font font, final boolean antialiased, final boolean useFractionalMetrics, RenderDelegate renderDelegate, final boolean mipmap) {
        this.stringLocations = new HashMap<String, Rect>();
        this.useVertexArrays = true;
        this.smoothing = true;
        this.singleUnicode = new char[1];
        this.font = font;
        this.antialiased = antialiased;
        this.useFractionalMetrics = useFractionalMetrics;
        this.mipmap = mipmap;
        this.packer = new RectanglePacker(new Manager(), 256, 256);
        if (renderDelegate == null) {
            renderDelegate = new DefaultRenderDelegate();
        }
        this.renderDelegate = renderDelegate;
        this.mGlyphProducer = new GlyphProducer(font.getNumGlyphs());
    }
    
    public Rectangle2D getBounds(final String s) {
        return this.getBounds((CharSequence)s);
    }
    
    public Rectangle2D getBounds(final CharSequence charSequence) {
        final Rect rect = this.stringLocations.get(charSequence);
        if (rect != null) {
            final TextData textData = (TextData)rect.getUserData();
            return new Rectangle2D.Double(-textData.origin().x, -textData.origin().y, rect.w(), rect.h());
        }
        return this.normalize(this.renderDelegate.getBounds(charSequence, this.font, this.getFontRenderContext()));
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public FontRenderContext getFontRenderContext() {
        if (this.cachedFontRenderContext == null) {
            this.cachedFontRenderContext = this.getGraphics2D().getFontRenderContext();
        }
        return this.cachedFontRenderContext;
    }
    
    public void beginRendering(final int n, final int n2) throws GLException {
        this.beginRendering(n, n2, true);
    }
    
    public void beginRendering(final int n, final int n2, final boolean b) throws GLException {
        this.beginRendering(true, n, n2, b);
    }
    
    public void begin3DRendering() throws GLException {
        this.beginRendering(false, 0, 0, false);
    }
    
    public void setColor(final Color color) throws GLException {
        if (!this.haveCachedColor || this.cachedColor == null || !color.equals(this.cachedColor)) {
            this.flushGlyphPipeline();
        }
        this.getBackingStore().setColor(color);
        this.haveCachedColor = true;
        this.cachedColor = color;
    }
    
    public void setColor(final float cachedR, final float cachedG, final float cachedB, final float cachedA) throws GLException {
        if (!this.haveCachedColor || this.cachedColor != null || cachedR != this.cachedR || cachedG != this.cachedG || cachedB != this.cachedB || cachedA != this.cachedA) {
            this.flushGlyphPipeline();
        }
        this.getBackingStore().setColor(cachedR, cachedG, cachedB, cachedA);
        this.haveCachedColor = true;
        this.cachedR = cachedR;
        this.cachedG = cachedG;
        this.cachedB = cachedB;
        this.cachedA = cachedA;
        this.cachedColor = null;
    }
    
    public void draw(final CharSequence charSequence, final int n, final int n2) throws GLException {
        this.draw3D(charSequence, n, n2, 0.0f, 1.0f);
    }
    
    public void draw(final String s, final int n, final int n2) throws GLException {
        this.draw3D(s, n, n2, 0.0f, 1.0f);
    }
    
    public void draw3D(final CharSequence charSequence, final float n, final float n2, final float n3, final float n4) {
        this.internal_draw3D(charSequence, n, n2, n3, n4);
    }
    
    public void draw3D(final String s, final float n, final float n2, final float n3, final float n4) {
        this.internal_draw3D(s, n, n2, n3, n4);
    }
    
    public float getCharWidth(final char c) {
        return this.mGlyphProducer.getGlyphPixelWidth(c);
    }
    
    public void flush() {
        this.flushGlyphPipeline();
    }
    
    public void endRendering() throws GLException {
        this.endRendering(true);
    }
    
    public void end3DRendering() throws GLException {
        this.endRendering(false);
    }
    
    public void dispose() throws GLException {
        this.packer.dispose();
        this.packer = null;
        this.cachedBackingStore = null;
        this.cachedGraphics = null;
        this.cachedFontRenderContext = null;
        if (this.dbgFrame != null) {
            this.dbgFrame.dispose();
        }
    }
    
    private static Rectangle2D preNormalize(final Rectangle2D rectangle2D) {
        final int n = (int)Math.floor(rectangle2D.getMinX()) - 1;
        final int n2 = (int)Math.floor(rectangle2D.getMinY()) - 1;
        return new Rectangle2D.Double(n, n2, (int)Math.ceil(rectangle2D.getMaxX()) + 1 - n, (int)Math.ceil(rectangle2D.getMaxY()) + 1 - n2);
    }
    
    private Rectangle2D normalize(final Rectangle2D rectangle2D) {
        final int n = (int)Math.max(1.0, 0.015 * this.font.getSize());
        return new Rectangle2D.Double((int)Math.floor(rectangle2D.getMinX() - n), (int)Math.floor(rectangle2D.getMinY() - n), (int)Math.ceil(rectangle2D.getWidth() + 2 * n), (int)Math.ceil(rectangle2D.getHeight()) + 2 * n);
    }
    
    private TextureRenderer getBackingStore() {
        final TextureRenderer cachedBackingStore = (TextureRenderer)this.packer.getBackingStore();
        if (cachedBackingStore != this.cachedBackingStore) {
            if (this.cachedGraphics != null) {
                this.cachedGraphics.dispose();
                this.cachedGraphics = null;
                this.cachedFontRenderContext = null;
            }
            this.cachedBackingStore = cachedBackingStore;
        }
        return this.cachedBackingStore;
    }
    
    private Graphics2D getGraphics2D() {
        final TextureRenderer backingStore = this.getBackingStore();
        if (this.cachedGraphics == null) {
            (this.cachedGraphics = backingStore.createGraphics()).setComposite(AlphaComposite.Src);
            this.cachedGraphics.setColor(Color.WHITE);
            this.cachedGraphics.setFont(this.font);
            this.cachedGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antialiased ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            this.cachedGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.useFractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        return this.cachedGraphics;
    }
    
    private void beginRendering(final boolean isOrthoMode, final int beginRenderingWidth, final int beginRenderingHeight, final boolean beginRenderingDepthTestDisabled) {
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        if (TextRenderer.DEBUG && !this.debugged) {
            this.debug(gl2);
        }
        this.inBeginEndPair = true;
        this.isOrthoMode = isOrthoMode;
        this.beginRenderingWidth = beginRenderingWidth;
        this.beginRenderingHeight = beginRenderingHeight;
        this.beginRenderingDepthTestDisabled = beginRenderingDepthTestDisabled;
        if (isOrthoMode) {
            this.getBackingStore().beginOrthoRendering(beginRenderingWidth, beginRenderingHeight, beginRenderingDepthTestDisabled);
        }
        else {
            this.getBackingStore().begin3DRendering();
        }
        gl2.glPushClientAttrib(-1);
        if (!this.haveMaxSize) {
            final int[] array = { 0 };
            gl2.glGetIntegerv(3379, array, 0);
            this.packer.setMaxSize(array[0], array[0]);
            this.haveMaxSize = true;
        }
        if (this.needToResetColor && this.haveCachedColor) {
            if (this.cachedColor == null) {
                this.getBackingStore().setColor(this.cachedR, this.cachedG, this.cachedB, this.cachedA);
            }
            else {
                this.getBackingStore().setColor(this.cachedColor);
            }
            this.needToResetColor = false;
        }
        if (this.mipmap && !this.getBackingStore().isUsingAutoMipmapGeneration()) {
            if (TextRenderer.DEBUG) {
                System.err.println("Disabled mipmapping in TextRenderer");
            }
            this.mipmap = false;
        }
    }
    
    private void endRendering(final boolean b) throws GLException {
        this.flushGlyphPipeline();
        this.inBeginEndPair = false;
        final GL2 gl2 = GLContext.getCurrentGL().getGL2();
        gl2.glPopClientAttrib();
        if (this.getUseVertexArrays() && this.is15Available(gl2)) {
            try {
                gl2.glBindBuffer(34962, 0);
            }
            catch (Exception ex) {
                this.isExtensionAvailable_GL_VERSION_1_5 = false;
            }
        }
        if (b) {
            this.getBackingStore().endOrthoRendering();
        }
        else {
            this.getBackingStore().end3DRendering();
        }
        if (++this.numRenderCycles >= 100) {
            this.numRenderCycles = 0;
            if (TextRenderer.DEBUG) {
                System.err.println("Clearing unused entries in endRendering()");
            }
            this.clearUnusedEntries();
        }
    }
    
    private void clearUnusedEntries() {
        final ArrayList<Rect> list = new ArrayList<Rect>();
        this.packer.visit(new RectVisitor() {
            @Override
            public void visit(final Rect rect) {
                final TextData textData = (TextData)rect.getUserData();
                if (textData.used()) {
                    textData.clearUsed();
                }
                else {
                    list.add(rect);
                }
            }
        });
        for (final Rect rect : list) {
            this.packer.remove(rect);
            this.stringLocations.remove(((TextData)rect.getUserData()).string());
            final int unicodeID = ((TextData)rect.getUserData()).unicodeID;
            if (unicodeID > 0) {
                this.mGlyphProducer.clearCacheEntry(unicodeID);
            }
        }
        final float verticalFragmentationRatio = this.packer.verticalFragmentationRatio();
        if (!list.isEmpty() && verticalFragmentationRatio > 0.7f) {
            if (TextRenderer.DEBUG) {
                System.err.println("Compacting TextRenderer backing store due to vertical fragmentation " + verticalFragmentationRatio);
            }
            this.packer.compact();
        }
        if (TextRenderer.DEBUG) {
            this.getBackingStore().markDirty(0, 0, this.getBackingStore().getWidth(), this.getBackingStore().getHeight());
        }
    }
    
    private void internal_draw3D(final CharSequence charSequence, float n, final float n2, final float n3, final float n4) {
        final Iterator<Glyph> iterator = this.mGlyphProducer.getGlyphs(charSequence).iterator();
        while (iterator.hasNext()) {
            n += iterator.next().draw3D(n, n2, n3, n4) * n4;
        }
    }
    
    private void flushGlyphPipeline() {
        if (this.mPipelinedQuadRenderer != null) {
            this.mPipelinedQuadRenderer.draw();
        }
    }
    
    private void draw3D_ROBUST(final CharSequence charSequence, final float n, final float n2, final float n3, final float n4) {
        String string;
        if (charSequence instanceof String) {
            string = (String)charSequence;
        }
        else {
            string = charSequence.toString();
        }
        Rect rect = this.stringLocations.get(string);
        if (rect == null) {
            this.getGraphics2D();
            final Rectangle2D preNormalize = preNormalize(this.renderDelegate.getBounds(string, this.font, this.getFontRenderContext()));
            final Rectangle2D normalize = this.normalize(preNormalize);
            final Point point = new Point((int)(-normalize.getMinX()), (int)(-normalize.getMinY()));
            rect = new Rect(0, 0, (int)normalize.getWidth(), (int)normalize.getHeight(), new TextData(string, point, preNormalize, -1));
            this.packer.add(rect);
            this.stringLocations.put(string, rect);
            final Graphics2D graphics2D = this.getGraphics2D();
            final int n5 = rect.x() + point.x;
            final int n6 = rect.y() + point.y;
            graphics2D.setComposite(AlphaComposite.Clear);
            graphics2D.fillRect(rect.x(), rect.y(), rect.w(), rect.h());
            graphics2D.setComposite(AlphaComposite.Src);
            this.renderDelegate.draw(graphics2D, string, n5, n6);
            this.getBackingStore().markDirty(rect.x(), rect.y(), rect.w(), rect.h());
        }
        final TextureRenderer backingStore = this.getBackingStore();
        final TextData textData = (TextData)rect.getUserData();
        textData.markUsed();
        final Rectangle2D origRect = textData.origRect();
        backingStore.draw3DRect(n - n4 * textData.origOriginX(), n2 - n4 * ((float)origRect.getHeight() - textData.origOriginY()), n3, rect.x() + (textData.origin().x - textData.origOriginX()), backingStore.getHeight() - rect.y() - (int)origRect.getHeight() - (textData.origin().y - textData.origOriginY()), (int)origRect.getWidth(), (int)origRect.getHeight(), n4);
    }
    
    private void debug(final GL gl) {
        this.dbgFrame = new Frame("TextRenderer Debug Output");
        final GLCanvas glCanvas = new GLCanvas(new GLCapabilities(gl.getGLProfile()));
        glCanvas.setSharedContext(GLContext.getCurrent());
        glCanvas.addGLEventListener(new DebugListener(gl, this.dbgFrame));
        this.dbgFrame.add(glCanvas);
        final FPSAnimator fpsAnimator = new FPSAnimator(glCanvas, 10);
        this.dbgFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                new InterruptSource.Thread(null, new Runnable() {
                    @Override
                    public void run() {
                        fpsAnimator.stop();
                    }
                }).start();
            }
        });
        this.dbgFrame.setSize(256, 256);
        this.dbgFrame.setVisible(true);
        fpsAnimator.start();
        this.debugged = true;
    }
    
    public void setUseVertexArrays(final boolean useVertexArrays) {
        this.useVertexArrays = useVertexArrays;
    }
    
    public final boolean getUseVertexArrays() {
        return this.useVertexArrays;
    }
    
    public void setSmoothing(final boolean b) {
        this.smoothing = b;
        this.getBackingStore().setSmoothing(b);
    }
    
    public boolean getSmoothing() {
        return this.smoothing;
    }
    
    private final boolean is15Available(final GL gl) {
        if (!this.checkFor_isExtensionAvailable_GL_VERSION_1_5) {
            this.isExtensionAvailable_GL_VERSION_1_5 = gl.isExtensionAvailable("GL_VERSION_1_5");
            this.checkFor_isExtensionAvailable_GL_VERSION_1_5 = true;
        }
        return this.isExtensionAvailable_GL_VERSION_1_5;
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.TextRenderer", true);
    }
    
    private static class CharSequenceIterator implements CharacterIterator
    {
        CharSequence mSequence;
        int mLength;
        int mCurrentIndex;
        
        CharSequenceIterator() {
        }
        
        CharSequenceIterator(final CharSequence charSequence) {
            this.initFromCharSequence(charSequence);
        }
        
        public void initFromCharSequence(final CharSequence mSequence) {
            this.mSequence = mSequence;
            this.mLength = this.mSequence.length();
            this.mCurrentIndex = 0;
        }
        
        @Override
        public char last() {
            this.mCurrentIndex = Math.max(0, this.mLength - 1);
            return this.current();
        }
        
        @Override
        public char current() {
            if (this.mLength == 0 || this.mCurrentIndex >= this.mLength) {
                return '\uffff';
            }
            return this.mSequence.charAt(this.mCurrentIndex);
        }
        
        @Override
        public char next() {
            ++this.mCurrentIndex;
            return this.current();
        }
        
        @Override
        public char previous() {
            this.mCurrentIndex = Math.max(this.mCurrentIndex - 1, 0);
            return this.current();
        }
        
        @Override
        public char setIndex(final int mCurrentIndex) {
            this.mCurrentIndex = mCurrentIndex;
            return this.current();
        }
        
        @Override
        public int getBeginIndex() {
            return 0;
        }
        
        @Override
        public int getEndIndex() {
            return this.mLength;
        }
        
        @Override
        public int getIndex() {
            return this.mCurrentIndex;
        }
        
        @Override
        public Object clone() {
            final CharSequenceIterator charSequenceIterator = new CharSequenceIterator(this.mSequence);
            charSequenceIterator.mCurrentIndex = this.mCurrentIndex;
            return charSequenceIterator;
        }
        
        @Override
        public char first() {
            if (this.mLength == 0) {
                return '\uffff';
            }
            this.mCurrentIndex = 0;
            return this.current();
        }
    }
    
    static class TextData
    {
        private final String str;
        int unicodeID;
        private final Point origin;
        private final Rectangle2D origRect;
        private boolean used;
        
        TextData(final String str, final Point origin, final Rectangle2D origRect, final int unicodeID) {
            this.str = str;
            this.origin = origin;
            this.origRect = origRect;
            this.unicodeID = unicodeID;
        }
        
        String string() {
            return this.str;
        }
        
        Point origin() {
            return this.origin;
        }
        
        int origOriginX() {
            return (int)(-this.origRect.getMinX());
        }
        
        int origOriginY() {
            return (int)(-this.origRect.getMinY());
        }
        
        Rectangle2D origRect() {
            return this.origRect;
        }
        
        boolean used() {
            return this.used;
        }
        
        void markUsed() {
            this.used = true;
        }
        
        void clearUsed() {
            this.used = false;
        }
    }
    
    class Manager implements BackingStoreManager
    {
        private Graphics2D g;
        
        @Override
        public Object allocateBackingStore(final int n, final int n2) {
            TextureRenderer alphaOnlyRenderer;
            if (TextRenderer.this.renderDelegate.intensityOnly()) {
                alphaOnlyRenderer = TextureRenderer.createAlphaOnlyRenderer(n, n2, TextRenderer.this.mipmap);
            }
            else {
                alphaOnlyRenderer = new TextureRenderer(n, n2, true, TextRenderer.this.mipmap);
            }
            alphaOnlyRenderer.setSmoothing(TextRenderer.this.smoothing);
            if (TextRenderer.DEBUG) {
                System.err.println(" TextRenderer allocating backing store " + n + " x " + n2);
            }
            return alphaOnlyRenderer;
        }
        
        @Override
        public void deleteBackingStore(final Object o) {
            ((TextureRenderer)o).dispose();
        }
        
        @Override
        public boolean preExpand(final Rect rect, final int n) {
            if (n == 0) {
                if (TextRenderer.DEBUG) {
                    System.err.println("Clearing unused entries in preExpand(): attempt number " + n);
                }
                if (TextRenderer.this.inBeginEndPair) {
                    TextRenderer.this.flush();
                }
                TextRenderer.this.clearUnusedEntries();
                return true;
            }
            return false;
        }
        
        @Override
        public boolean additionFailed(final Rect rect, final int n) {
            TextRenderer.this.packer.clear();
            TextRenderer.this.stringLocations.clear();
            TextRenderer.this.mGlyphProducer.clearAllCacheEntries();
            if (TextRenderer.DEBUG) {
                System.err.println(" *** Cleared all text because addition failed ***");
            }
            return n == 0;
        }
        
        @Override
        public boolean canCompact() {
            return true;
        }
        
        @Override
        public void beginMovement(final Object o, final Object o2) {
            if (TextRenderer.this.inBeginEndPair) {
                TextRenderer.this.flush();
                final GL2 gl2 = GLContext.getCurrentGL().getGL2();
                gl2.glPopClientAttrib();
                if (TextRenderer.this.getUseVertexArrays() && TextRenderer.this.is15Available(gl2)) {
                    try {
                        gl2.glBindBuffer(34962, 0);
                    }
                    catch (Exception ex) {
                        TextRenderer.this.isExtensionAvailable_GL_VERSION_1_5 = false;
                    }
                }
                if (TextRenderer.this.isOrthoMode) {
                    ((TextureRenderer)o).endOrthoRendering();
                }
                else {
                    ((TextureRenderer)o).end3DRendering();
                }
            }
            this.g = ((TextureRenderer)o2).createGraphics();
        }
        
        @Override
        public void move(final Object o, final Rect rect, final Object o2, final Rect rect2) {
            final TextureRenderer textureRenderer = (TextureRenderer)o;
            if (textureRenderer == o2) {
                this.g.copyArea(rect.x(), rect.y(), rect.w(), rect.h(), rect2.x() - rect.x(), rect2.y() - rect.y());
            }
            else {
                this.g.drawImage(textureRenderer.getImage(), rect2.x(), rect2.y(), rect2.x() + rect2.w(), rect2.y() + rect2.h(), rect.x(), rect.y(), rect.x() + rect.w(), rect.y() + rect.h(), null);
            }
        }
        
        @Override
        public void endMovement(final Object o, final Object o2) {
            this.g.dispose();
            final TextureRenderer textureRenderer = (TextureRenderer)o2;
            textureRenderer.markDirty(0, 0, textureRenderer.getWidth(), textureRenderer.getHeight());
            if (TextRenderer.this.inBeginEndPair) {
                if (TextRenderer.this.isOrthoMode) {
                    ((TextureRenderer)o2).beginOrthoRendering(TextRenderer.this.beginRenderingWidth, TextRenderer.this.beginRenderingHeight, TextRenderer.this.beginRenderingDepthTestDisabled);
                }
                else {
                    ((TextureRenderer)o2).begin3DRendering();
                }
                GLContext.getCurrentGL().getGL2().glPushClientAttrib(-1);
                if (TextRenderer.this.haveCachedColor) {
                    if (TextRenderer.this.cachedColor == null) {
                        ((TextureRenderer)o2).setColor(TextRenderer.this.cachedR, TextRenderer.this.cachedG, TextRenderer.this.cachedB, TextRenderer.this.cachedA);
                    }
                    else {
                        ((TextureRenderer)o2).setColor(TextRenderer.this.cachedColor);
                    }
                }
            }
            else {
                TextRenderer.this.needToResetColor = true;
            }
        }
    }
    
    public static class DefaultRenderDelegate implements RenderDelegate
    {
        @Override
        public boolean intensityOnly() {
            return true;
        }
        
        @Override
        public Rectangle2D getBounds(final CharSequence charSequence, final Font font, final FontRenderContext fontRenderContext) {
            return this.getBounds(font.createGlyphVector(fontRenderContext, new CharSequenceIterator(charSequence)), fontRenderContext);
        }
        
        @Override
        public Rectangle2D getBounds(final String s, final Font font, final FontRenderContext fontRenderContext) {
            return this.getBounds(font.createGlyphVector(fontRenderContext, s), fontRenderContext);
        }
        
        @Override
        public Rectangle2D getBounds(final GlyphVector glyphVector, final FontRenderContext fontRenderContext) {
            return glyphVector.getVisualBounds();
        }
        
        @Override
        public void drawGlyphVector(final Graphics2D graphics2D, final GlyphVector glyphVector, final int n, final int n2) {
            graphics2D.drawGlyphVector(glyphVector, n, n2);
        }
        
        @Override
        public void draw(final Graphics2D graphics2D, final String s, final int n, final int n2) {
            graphics2D.drawString(s, n, n2);
        }
    }
    
    class Glyph
    {
        private int unicodeID;
        private int glyphCode;
        private GlyphProducer producer;
        private float advance;
        private GlyphVector singleUnicodeGlyphVector;
        private Rect glyphRectForTextureMapping;
        private String str;
        private boolean needAdvance;
        
        public Glyph(final int unicodeID, final int glyphCode, final float advance, final GlyphVector singleUnicodeGlyphVector, final GlyphProducer producer) {
            this.unicodeID = unicodeID;
            this.glyphCode = glyphCode;
            this.advance = advance;
            this.singleUnicodeGlyphVector = singleUnicodeGlyphVector;
            this.producer = producer;
        }
        
        public Glyph(final String str, final boolean needAdvance) {
            this.str = str;
            this.needAdvance = needAdvance;
        }
        
        public int getUnicodeID() {
            return this.unicodeID;
        }
        
        public int getGlyphCode() {
            return this.glyphCode;
        }
        
        public float getAdvance() {
            return this.advance;
        }
        
        public float draw3D(final float n, final float n2, final float n3, final float n4) {
            if (this.str == null) {
                if (this.glyphRectForTextureMapping == null) {
                    this.upload();
                }
                try {
                    if (TextRenderer.this.mPipelinedQuadRenderer == null) {
                        TextRenderer.this.mPipelinedQuadRenderer = new Pipelined_QuadRenderer();
                    }
                    final TextureRenderer access$2500 = TextRenderer.this.getBackingStore();
                    final TextureCoords imageTexCoords = access$2500.getTexture().getImageTexCoords();
                    final float right = imageTexCoords.right();
                    final float bottom = imageTexCoords.bottom();
                    final Rect glyphRectForTextureMapping = this.glyphRectForTextureMapping;
                    final TextData textData = (TextData)glyphRectForTextureMapping.getUserData();
                    textData.markUsed();
                    final Rectangle2D origRect = textData.origRect();
                    final float n5 = n - n4 * textData.origOriginX();
                    final float n6 = n2 - n4 * ((float)origRect.getHeight() - textData.origOriginY());
                    final int n7 = glyphRectForTextureMapping.x() + (textData.origin().x - textData.origOriginX());
                    final int n8 = access$2500.getHeight() - glyphRectForTextureMapping.y() - (int)origRect.getHeight() - (textData.origin().y - textData.origOriginY());
                    final int n9 = (int)origRect.getWidth();
                    final int n10 = (int)origRect.getHeight();
                    final float n11 = right * n7 / access$2500.getWidth();
                    final float n12 = bottom * (1.0f - n8 / access$2500.getHeight());
                    final float n13 = right * (n7 + n9) / access$2500.getWidth();
                    final float n14 = bottom * (1.0f - (n8 + n10) / access$2500.getHeight());
                    TextRenderer.this.mPipelinedQuadRenderer.glTexCoord2f(n11, n12);
                    TextRenderer.this.mPipelinedQuadRenderer.glVertex3f(n5, n6, n3);
                    TextRenderer.this.mPipelinedQuadRenderer.glTexCoord2f(n13, n12);
                    TextRenderer.this.mPipelinedQuadRenderer.glVertex3f(n5 + n9 * n4, n6, n3);
                    TextRenderer.this.mPipelinedQuadRenderer.glTexCoord2f(n13, n14);
                    TextRenderer.this.mPipelinedQuadRenderer.glVertex3f(n5 + n9 * n4, n6 + n10 * n4, n3);
                    TextRenderer.this.mPipelinedQuadRenderer.glTexCoord2f(n11, n14);
                    TextRenderer.this.mPipelinedQuadRenderer.glVertex3f(n5, n6 + n10 * n4, n3);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                return this.advance;
            }
            TextRenderer.this.draw3D_ROBUST(this.str, n, n2, n3, n4);
            if (!this.needAdvance) {
                return 0.0f;
            }
            final GlyphVector glyphVector = TextRenderer.this.font.createGlyphVector(TextRenderer.this.getFontRenderContext(), this.str);
            float n15 = 0.0f;
            for (int i = 0; i < glyphVector.getNumGlyphs(); ++i) {
                n15 += glyphVector.getGlyphMetrics(i).getAdvance();
            }
            return n15;
        }
        
        public void clear() {
            this.glyphRectForTextureMapping = null;
        }
        
        private void upload() {
            final GlyphVector glyphVector = this.getGlyphVector();
            final Rectangle2D access$2600 = preNormalize(TextRenderer.this.renderDelegate.getBounds(glyphVector, TextRenderer.this.getFontRenderContext()));
            final Rectangle2D access$2601 = TextRenderer.this.normalize(access$2600);
            final Point point = new Point((int)(-access$2601.getMinX()), (int)(-access$2601.getMinY()));
            final Rect glyphRectForTextureMapping = new Rect(0, 0, (int)access$2601.getWidth(), (int)access$2601.getHeight(), new TextData(null, point, access$2600, this.unicodeID));
            TextRenderer.this.packer.add(glyphRectForTextureMapping);
            this.glyphRectForTextureMapping = glyphRectForTextureMapping;
            final Graphics2D access$2602 = TextRenderer.this.getGraphics2D();
            final int n = glyphRectForTextureMapping.x() + point.x;
            final int n2 = glyphRectForTextureMapping.y() + point.y;
            access$2602.setComposite(AlphaComposite.Clear);
            access$2602.fillRect(glyphRectForTextureMapping.x(), glyphRectForTextureMapping.y(), glyphRectForTextureMapping.w(), glyphRectForTextureMapping.h());
            access$2602.setComposite(AlphaComposite.Src);
            TextRenderer.this.renderDelegate.drawGlyphVector(access$2602, glyphVector, n, n2);
            TextRenderer.this.getBackingStore().markDirty(glyphRectForTextureMapping.x(), glyphRectForTextureMapping.y(), glyphRectForTextureMapping.w(), glyphRectForTextureMapping.h());
            this.producer.register(this);
        }
        
        private GlyphVector getGlyphVector() {
            final GlyphVector singleUnicodeGlyphVector = this.singleUnicodeGlyphVector;
            if (singleUnicodeGlyphVector != null) {
                this.singleUnicodeGlyphVector = null;
                return singleUnicodeGlyphVector;
            }
            TextRenderer.this.singleUnicode[0] = (char)this.unicodeID;
            return TextRenderer.this.font.createGlyphVector(TextRenderer.this.getFontRenderContext(), TextRenderer.this.singleUnicode);
        }
    }
    
    class GlyphProducer
    {
        static final int undefined = -2;
        final FontRenderContext fontRenderContext;
        List<Glyph> glyphsOutput;
        HashMap<String, GlyphVector> fullGlyphVectorCache;
        HashMap<Character, GlyphMetrics> glyphMetricsCache;
        int[] unicodes2Glyphs;
        Glyph[] glyphCache;
        CharSequenceIterator iter;
        
        GlyphProducer(final int n) {
            this.fontRenderContext = null;
            this.glyphsOutput = new ArrayList<Glyph>();
            this.fullGlyphVectorCache = new HashMap<String, GlyphVector>();
            this.glyphMetricsCache = new HashMap<Character, GlyphMetrics>();
            this.iter = new CharSequenceIterator();
            this.unicodes2Glyphs = new int[512];
            this.glyphCache = new Glyph[n];
            this.clearAllCacheEntries();
        }
        
        public List<Glyph> getGlyphs(final CharSequence charSequence) {
            this.glyphsOutput.clear();
            GlyphVector glyphVector = this.fullGlyphVectorCache.get(charSequence.toString());
            if (glyphVector == null) {
                this.iter.initFromCharSequence(charSequence);
                glyphVector = TextRenderer.this.font.createGlyphVector(TextRenderer.this.getFontRenderContext(), this.iter);
                this.fullGlyphVectorCache.put(charSequence.toString(), glyphVector);
            }
            if (glyphVector.getLayoutFlags() == 0) {
                final int numGlyphs = glyphVector.getNumGlyphs();
                int i = 0;
                while (i < numGlyphs) {
                    final Character value = CharacterCache.valueOf(charSequence.charAt(i));
                    GlyphMetrics glyphMetrics = this.glyphMetricsCache.get(value);
                    if (glyphMetrics == null) {
                        glyphMetrics = glyphVector.getGlyphMetrics(i);
                        this.glyphMetricsCache.put(value, glyphMetrics);
                    }
                    final Glyph glyph = this.getGlyph(charSequence, glyphMetrics, i);
                    if (glyph != null) {
                        this.glyphsOutput.add(glyph);
                        ++i;
                    }
                    else {
                        final StringBuilder sb = new StringBuilder();
                        while (i < numGlyphs && this.getGlyph(charSequence, glyphVector.getGlyphMetrics(i), i) == null) {
                            sb.append(charSequence.charAt(i++));
                        }
                        this.glyphsOutput.add(new Glyph(sb.toString(), i < numGlyphs));
                    }
                }
                return this.glyphsOutput;
            }
            this.glyphsOutput.add(new Glyph(charSequence.toString(), false));
            return this.glyphsOutput;
        }
        
        public void clearCacheEntry(final int n) {
            final int n2 = this.unicodes2Glyphs[n];
            if (n2 != -2) {
                final Glyph glyph = this.glyphCache[n2];
                if (glyph != null) {
                    glyph.clear();
                }
                this.glyphCache[n2] = null;
            }
            this.unicodes2Glyphs[n] = -2;
        }
        
        public void clearAllCacheEntries() {
            for (int i = 0; i < this.unicodes2Glyphs.length; ++i) {
                this.clearCacheEntry(i);
            }
        }
        
        public void register(final Glyph glyph) {
            this.unicodes2Glyphs[glyph.getUnicodeID()] = glyph.getGlyphCode();
            this.glyphCache[glyph.getGlyphCode()] = glyph;
        }
        
        public float getGlyphPixelWidth(final char c) {
            final Glyph glyph = this.getGlyph(c);
            if (glyph != null) {
                return glyph.getAdvance();
            }
            TextRenderer.this.singleUnicode[0] = c;
            if (null == this.fontRenderContext) {
                throw new InternalError("fontRenderContext never initialized!");
            }
            return TextRenderer.this.font.createGlyphVector(this.fontRenderContext, TextRenderer.this.singleUnicode).getGlyphMetrics(0).getAdvance();
        }
        
        private Glyph getGlyph(final CharSequence charSequence, final GlyphMetrics glyphMetrics, final int n) {
            final char char1 = charSequence.charAt(n);
            if (char1 >= this.unicodes2Glyphs.length) {
                return null;
            }
            final int n2 = this.unicodes2Glyphs[char1];
            if (n2 != -2) {
                return this.glyphCache[n2];
            }
            TextRenderer.this.singleUnicode[0] = char1;
            return this.getGlyph(char1, TextRenderer.this.font.createGlyphVector(TextRenderer.this.getFontRenderContext(), TextRenderer.this.singleUnicode), glyphMetrics);
        }
        
        private Glyph getGlyph(final int n) {
            if (n >= this.unicodes2Glyphs.length) {
                return null;
            }
            final int n2 = this.unicodes2Glyphs[n];
            if (n2 != -2) {
                return this.glyphCache[n2];
            }
            TextRenderer.this.singleUnicode[0] = (char)n;
            final GlyphVector glyphVector = TextRenderer.this.font.createGlyphVector(TextRenderer.this.getFontRenderContext(), TextRenderer.this.singleUnicode);
            return this.getGlyph(n, glyphVector, glyphVector.getGlyphMetrics(0));
        }
        
        private Glyph getGlyph(final int n, final GlyphVector glyphVector, final GlyphMetrics glyphMetrics) {
            final int glyphCode = glyphVector.getGlyphCode(0);
            if (glyphCode >= this.glyphCache.length) {
                return null;
            }
            final Glyph glyph = new Glyph(n, glyphCode, glyphMetrics.getAdvance(), glyphVector, this);
            this.register(glyph);
            return glyph;
        }
    }
    
    private static class CharacterCache
    {
        static final Character[] cache;
        
        public static Character valueOf(final char c) {
            if (c <= '\u007f') {
                return CharacterCache.cache[c];
            }
            return c;
        }
        
        static {
            cache = new Character[128];
            for (int i = 0; i < CharacterCache.cache.length; ++i) {
                CharacterCache.cache[i] = (char)i;
            }
        }
    }
    
    class Pipelined_QuadRenderer
    {
        int mOutstandingGlyphsVerticesPipeline;
        FloatBuffer mTexCoords;
        FloatBuffer mVertCoords;
        boolean usingVBOs;
        int mVBO_For_ResuableTileVertices;
        int mVBO_For_ResuableTileTexCoords;
        
        Pipelined_QuadRenderer() {
            this.mOutstandingGlyphsVerticesPipeline = 0;
            final GL2 gl2 = GLContext.getCurrentGL().getGL2();
            this.mVertCoords = Buffers.newDirectFloatBuffer(1200);
            this.mTexCoords = Buffers.newDirectFloatBuffer(800);
            this.usingVBOs = (TextRenderer.this.getUseVertexArrays() && TextRenderer.this.is15Available(gl2));
            if (this.usingVBOs) {
                try {
                    final int[] array = new int[2];
                    gl2.glGenBuffers(2, IntBuffer.wrap(array));
                    this.mVBO_For_ResuableTileVertices = array[0];
                    this.mVBO_For_ResuableTileTexCoords = array[1];
                    gl2.glBindBuffer(34962, this.mVBO_For_ResuableTileVertices);
                    gl2.glBufferData(34962, 4800L, null, 35040);
                    gl2.glBindBuffer(34962, this.mVBO_For_ResuableTileTexCoords);
                    gl2.glBufferData(34962, 3200L, null, 35040);
                }
                catch (Exception ex) {
                    TextRenderer.this.isExtensionAvailable_GL_VERSION_1_5 = false;
                    this.usingVBOs = false;
                }
            }
        }
        
        public void glTexCoord2f(final float n, final float n2) {
            this.mTexCoords.put(n);
            this.mTexCoords.put(n2);
        }
        
        public void glVertex3f(final float n, final float n2, final float n3) {
            this.mVertCoords.put(n);
            this.mVertCoords.put(n2);
            this.mVertCoords.put(n3);
            ++this.mOutstandingGlyphsVerticesPipeline;
            if (this.mOutstandingGlyphsVerticesPipeline >= 400) {
                this.draw();
            }
        }
        
        private void draw() {
            if (TextRenderer.this.useVertexArrays) {
                this.drawVertexArrays();
            }
            else {
                this.drawIMMEDIATE();
            }
        }
        
        private void drawVertexArrays() {
            if (this.mOutstandingGlyphsVerticesPipeline > 0) {
                final GL2 gl2 = GLContext.getCurrentGL().getGL2();
                TextRenderer.this.getBackingStore().getTexture();
                this.mVertCoords.rewind();
                this.mTexCoords.rewind();
                gl2.glEnableClientState(32884);
                if (this.usingVBOs) {
                    gl2.glBindBuffer(34962, this.mVBO_For_ResuableTileVertices);
                    gl2.glBufferSubData(34962, 0L, this.mOutstandingGlyphsVerticesPipeline * 12, this.mVertCoords);
                    gl2.glVertexPointer(3, 5126, 0, 0L);
                }
                else {
                    gl2.glVertexPointer(3, 5126, 0, this.mVertCoords);
                }
                gl2.glEnableClientState(32888);
                if (this.usingVBOs) {
                    gl2.glBindBuffer(34962, this.mVBO_For_ResuableTileTexCoords);
                    gl2.glBufferSubData(34962, 0L, this.mOutstandingGlyphsVerticesPipeline * 8, this.mTexCoords);
                    gl2.glTexCoordPointer(2, 5126, 0, 0L);
                }
                else {
                    gl2.glTexCoordPointer(2, 5126, 0, this.mTexCoords);
                }
                gl2.glDrawArrays(7, 0, this.mOutstandingGlyphsVerticesPipeline);
                this.mVertCoords.rewind();
                this.mTexCoords.rewind();
                this.mOutstandingGlyphsVerticesPipeline = 0;
            }
        }
        
        private void drawIMMEDIATE() {
            if (this.mOutstandingGlyphsVerticesPipeline > 0) {
                TextRenderer.this.getBackingStore().getTexture();
                final GL2 gl2 = GLContext.getCurrentGL().getGL2();
                gl2.glBegin(7);
                try {
                    final int n = this.mOutstandingGlyphsVerticesPipeline / 4;
                    this.mVertCoords.rewind();
                    this.mTexCoords.rewind();
                    for (int i = 0; i < n; ++i) {
                        gl2.glTexCoord2f(this.mTexCoords.get(), this.mTexCoords.get());
                        gl2.glVertex3f(this.mVertCoords.get(), this.mVertCoords.get(), this.mVertCoords.get());
                        gl2.glTexCoord2f(this.mTexCoords.get(), this.mTexCoords.get());
                        gl2.glVertex3f(this.mVertCoords.get(), this.mVertCoords.get(), this.mVertCoords.get());
                        gl2.glTexCoord2f(this.mTexCoords.get(), this.mTexCoords.get());
                        gl2.glVertex3f(this.mVertCoords.get(), this.mVertCoords.get(), this.mVertCoords.get());
                        gl2.glTexCoord2f(this.mTexCoords.get(), this.mTexCoords.get());
                        gl2.glVertex3f(this.mVertCoords.get(), this.mVertCoords.get(), this.mVertCoords.get());
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    gl2.glEnd();
                    this.mVertCoords.rewind();
                    this.mTexCoords.rewind();
                    this.mOutstandingGlyphsVerticesPipeline = 0;
                }
            }
        }
    }
    
    class DebugListener implements GLEventListener
    {
        private GLU glu;
        private Frame frame;
        
        DebugListener(final GL gl, final Frame frame) {
            this.glu = GLU.createGLU(gl);
            this.frame = frame;
        }
        
        @Override
        public void display(final GLAutoDrawable glAutoDrawable) {
            GLContext.getCurrentGL().getGL2().glClear(16640);
            if (TextRenderer.this.packer == null) {
                return;
            }
            final TextureRenderer access$2500 = TextRenderer.this.getBackingStore();
            final int width = access$2500.getWidth();
            final int height = access$2500.getHeight();
            access$2500.beginOrthoRendering(width, height);
            access$2500.drawOrthoRect(0, 0);
            access$2500.endOrthoRendering();
            if (this.frame.getWidth() != width || this.frame.getHeight() != height) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DebugListener.this.frame.setSize(width, height);
                    }
                });
            }
        }
        
        @Override
        public void dispose(final GLAutoDrawable glAutoDrawable) {
            this.glu = null;
            this.frame = null;
        }
        
        @Override
        public void init(final GLAutoDrawable glAutoDrawable) {
        }
        
        @Override
        public void reshape(final GLAutoDrawable glAutoDrawable, final int n, final int n2, final int n3, final int n4) {
        }
        
        public void displayChanged(final GLAutoDrawable glAutoDrawable, final boolean b, final boolean b2) {
        }
    }
    
    public interface RenderDelegate
    {
        boolean intensityOnly();
        
        Rectangle2D getBounds(final String p0, final Font p1, final FontRenderContext p2);
        
        Rectangle2D getBounds(final CharSequence p0, final Font p1, final FontRenderContext p2);
        
        Rectangle2D getBounds(final GlyphVector p0, final FontRenderContext p1);
        
        void draw(final Graphics2D p0, final String p1, final int p2, final int p3);
        
        void drawGlyphVector(final Graphics2D p0, final GlyphVector p1, final int p2, final int p3);
    }
}
