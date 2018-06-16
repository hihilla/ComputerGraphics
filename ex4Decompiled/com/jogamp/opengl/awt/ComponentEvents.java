// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.awt;

import java.beans.PropertyChangeListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.FocusListener;
import java.awt.event.ComponentListener;

public interface ComponentEvents
{
    void addComponentListener(final ComponentListener p0);
    
    void removeComponentListener(final ComponentListener p0);
    
    void addFocusListener(final FocusListener p0);
    
    void removeFocusListener(final FocusListener p0);
    
    void addHierarchyBoundsListener(final HierarchyBoundsListener p0);
    
    void removeHierarchyBoundsListener(final HierarchyBoundsListener p0);
    
    void addHierarchyListener(final HierarchyListener p0);
    
    void removeHierarchyListener(final HierarchyListener p0);
    
    void addInputMethodListener(final InputMethodListener p0);
    
    void removeInputMethodListener(final InputMethodListener p0);
    
    void addKeyListener(final KeyListener p0);
    
    void removeKeyListener(final KeyListener p0);
    
    void addMouseListener(final MouseListener p0);
    
    void removeMouseListener(final MouseListener p0);
    
    void addMouseMotionListener(final MouseMotionListener p0);
    
    void removeMouseMotionListener(final MouseMotionListener p0);
    
    void addMouseWheelListener(final MouseWheelListener p0);
    
    void removeMouseWheelListener(final MouseWheelListener p0);
    
    void addPropertyChangeListener(final PropertyChangeListener p0);
    
    void removePropertyChangeListener(final PropertyChangeListener p0);
    
    void addPropertyChangeListener(final String p0, final PropertyChangeListener p1);
    
    void removePropertyChangeListener(final String p0, final PropertyChangeListener p1);
}
