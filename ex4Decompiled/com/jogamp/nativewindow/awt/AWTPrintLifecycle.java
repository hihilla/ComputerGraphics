// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.awt;

import java.awt.Component;
import jogamp.nativewindow.awt.AWTMisc;
import java.awt.Container;

public interface AWTPrintLifecycle
{
    public static final int DEFAULT_PRINT_TILE_SIZE = 1024;
    
    void setupPrint(final double p0, final double p1, final int p2, final int p3, final int p4);
    
    void releasePrint();
    
    public static class Context
    {
        private final Container cont;
        private final double scaleMatX;
        private final double scaleMatY;
        private final int numSamples;
        private final int tileWidth;
        private final int tileHeight;
        private int count;
        private final AWTMisc.ComponentAction setupAction;
        private final AWTMisc.ComponentAction releaseAction;
        
        public static Context setupPrint(final Container container, final double n, final double n2, final int n3, final int n4, final int n5) {
            final Context context = new Context(container, n, n2, n3, n4, n5);
            context.setupPrint(container);
            return context;
        }
        
        public void releasePrint() {
            this.count = AWTMisc.performAction(this.cont, AWTPrintLifecycle.class, this.releaseAction);
        }
        
        public int getCount() {
            return this.count;
        }
        
        private Context(final Container cont, final double scaleMatX, final double scaleMatY, final int numSamples, final int tileWidth, final int tileHeight) {
            this.setupAction = new AWTMisc.ComponentAction() {
                @Override
                public void run(final Component component) {
                    ((AWTPrintLifecycle)component).setupPrint(Context.this.scaleMatX, Context.this.scaleMatY, Context.this.numSamples, Context.this.tileWidth, Context.this.tileHeight);
                }
            };
            this.releaseAction = new AWTMisc.ComponentAction() {
                @Override
                public void run(final Component component) {
                    ((AWTPrintLifecycle)component).releasePrint();
                }
            };
            this.cont = cont;
            this.scaleMatX = scaleMatX;
            this.scaleMatY = scaleMatY;
            this.numSamples = numSamples;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
            this.count = 0;
        }
        
        private void setupPrint(final Container container) {
            this.count = AWTMisc.performAction(container, AWTPrintLifecycle.class, this.setupAction);
        }
    }
}
