// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util;

import java.awt.EventQueue;
import javax.swing.SwingUtilities;
import com.jogamp.opengl.GLAutoDrawable;
import java.awt.Container;
import java.util.Iterator;
import java.util.IdentityHashMap;
import java.util.ArrayList;
import java.awt.Rectangle;
import javax.swing.RepaintManager;
import java.util.Map;
import javax.swing.JComponent;
import java.util.List;

class AWTAnimatorImpl implements AnimatorBase.AnimatorImpl
{
    private final List<JComponent> lightweights;
    private final Map<RepaintManager, RepaintManager> repaintManagers;
    private final Map<JComponent, Rectangle> dirtyRegions;
    private final Runnable drawWithRepaintManagerRunnable;
    
    AWTAnimatorImpl() {
        this.lightweights = new ArrayList<JComponent>();
        this.repaintManagers = new IdentityHashMap<RepaintManager, RepaintManager>();
        this.dirtyRegions = new IdentityHashMap<JComponent, Rectangle>();
        this.drawWithRepaintManagerRunnable = new Runnable() {
            @Override
            public void run() {
                for (JComponent component : AWTAnimatorImpl.this.lightweights) {
                    final RepaintManager currentManager = RepaintManager.currentManager(component);
                    currentManager.markCompletelyDirty(component);
                    AWTAnimatorImpl.this.repaintManagers.put(currentManager, currentManager);
                    final Rectangle visibleRect = component.getVisibleRect();
                    int x = visibleRect.x;
                    int y = visibleRect.y;
                    while (component != null) {
                        x += component.getX();
                        y += component.getY();
                        final Container parent = component.getParent();
                        if (parent == null || !(parent instanceof JComponent)) {
                            component = null;
                        }
                        else {
                            component = (JComponent)parent;
                            if (component.isOptimizedDrawingEnabled()) {
                                continue;
                            }
                            final RepaintManager currentManager2 = RepaintManager.currentManager(component);
                            AWTAnimatorImpl.this.repaintManagers.put(currentManager2, currentManager2);
                            final Rectangle rectangle = AWTAnimatorImpl.this.dirtyRegions.get(component);
                            if (rectangle == null) {
                                AWTAnimatorImpl.this.dirtyRegions.put(component, new Rectangle(x, y, visibleRect.width, visibleRect.height));
                            }
                            else {
                                rectangle.add(new Rectangle(x, y, visibleRect.width, visibleRect.height));
                            }
                        }
                    }
                }
                for (final JComponent component2 : AWTAnimatorImpl.this.dirtyRegions.keySet()) {
                    final Rectangle rectangle2 = AWTAnimatorImpl.this.dirtyRegions.get(component2);
                    RepaintManager.currentManager(component2).addDirtyRegion(component2, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
                }
                final Iterator<RepaintManager> iterator3 = AWTAnimatorImpl.this.repaintManagers.keySet().iterator();
                while (iterator3.hasNext()) {
                    iterator3.next().paintDirtyRegions();
                }
                AWTAnimatorImpl.this.dirtyRegions.clear();
                AWTAnimatorImpl.this.repaintManagers.clear();
            }
        };
    }
    
    @Override
    public void display(final ArrayList<GLAutoDrawable> list, final boolean b, final boolean b2) throws AnimatorBase.UncaughtAnimatorException {
        for (int n = 0, n2 = 0; n == 0 && n2 < list.size(); ++n2) {
            GLAutoDrawable glAutoDrawable = null;
            boolean b3 = true;
            try {
                glAutoDrawable = list.get(n2);
                b3 = false;
                if (glAutoDrawable instanceof JComponent) {
                    this.lightweights.add((JComponent)glAutoDrawable);
                }
                else {
                    glAutoDrawable.display();
                }
            }
            catch (Throwable t) {
                if (b3 && t instanceof IndexOutOfBoundsException) {
                    n = 1;
                }
                else {
                    if (!b) {
                        throw new AnimatorBase.UncaughtAnimatorException(glAutoDrawable, t);
                    }
                    if (b2) {
                        t.printStackTrace();
                    }
                }
            }
        }
        if (this.lightweights.size() > 0) {
            try {
                SwingUtilities.invokeAndWait(this.drawWithRepaintManagerRunnable);
            }
            catch (Throwable t2) {
                t2.printStackTrace();
            }
            this.lightweights.clear();
        }
    }
    
    @Override
    public boolean blockUntilDone(final Thread thread) {
        return Thread.currentThread() != thread && !EventQueue.isDispatchThread();
    }
}
