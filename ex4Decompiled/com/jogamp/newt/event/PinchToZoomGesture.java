// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import jogamp.newt.Debug;
import com.jogamp.nativewindow.NativeSurface;

public class PinchToZoomGesture implements GestureHandler
{
    public static final boolean DEBUG;
    private final NativeSurface surface;
    private final boolean allowMorePointer;
    private float zoom;
    private int zoomLastEdgeDist;
    private boolean zoomFirstTouch;
    private boolean zoomMode;
    private ZoomEvent zoomEvent;
    private final short[] pIds;
    
    public PinchToZoomGesture(final NativeSurface surface, final boolean allowMorePointer) {
        this.pIds = new short[] { -1, -1 };
        this.clear(true);
        this.surface = surface;
        this.allowMorePointer = allowMorePointer;
        this.zoom = 1.0f;
    }
    
    @Override
    public String toString() {
        return "PinchZoom[1stTouch " + this.zoomFirstTouch + ", in " + this.isWithinGesture() + ", has " + (null != this.zoomEvent) + ", zoom " + this.zoom + "]";
    }
    
    private int gesturePointers(final MouseEvent mouseEvent, final int n) {
        int n2 = 0;
        for (int i = mouseEvent.getPointerCount() - 1; i >= 0; --i) {
            if (n != i) {
                final short pointerId = mouseEvent.getPointerId(i);
                if (this.pIds[0] == pointerId || this.pIds[1] == pointerId) {
                    ++n2;
                }
            }
        }
        return n2;
    }
    
    @Override
    public void clear(final boolean b) {
        this.zoomEvent = null;
        if (b) {
            this.zoomLastEdgeDist = 0;
            this.zoomFirstTouch = true;
            this.zoomMode = false;
            this.pIds[0] = -1;
            this.pIds[1] = -1;
        }
    }
    
    @Override
    public boolean isWithinGesture() {
        return this.zoomMode;
    }
    
    @Override
    public boolean hasGesture() {
        return null != this.zoomEvent;
    }
    
    @Override
    public InputEvent getGestureEvent() {
        return this.zoomEvent;
    }
    
    public final float getZoom() {
        return this.zoom;
    }
    
    public final void setZoom(final float zoom) {
        this.zoom = zoom;
    }
    
    @Override
    public boolean process(final InputEvent inputEvent) {
        if (null != this.zoomEvent || !(inputEvent instanceof MouseEvent)) {
            return true;
        }
        final MouseEvent mouseEvent = (MouseEvent)inputEvent;
        final int pointerCount = mouseEvent.getPointerCount();
        if (mouseEvent.getPointerType(0).getPointerClass() != MouseEvent.PointerClass.Onscreen || (!this.allowMorePointer && pointerCount > 2)) {
            return false;
        }
        final short eventType = mouseEvent.getEventType();
        final boolean b = this.surface.getSurfaceWidth() >= this.surface.getSurfaceHeight();
        switch (eventType) {
            case 203: {
                if (pointerCount != 0) {
                    this.pIds[0] = mouseEvent.getPointerId(0);
                    this.pIds[1] = -1;
                }
                else if (2 <= pointerCount) {
                    this.pIds[0] = mouseEvent.getPointerId(0);
                    this.pIds[1] = mouseEvent.getPointerId(1);
                }
                if (PinchToZoomGesture.DEBUG) {
                    System.err.println("XXX1: id0 " + this.pIds[0] + " -> idx0 " + 0 + ", id1 " + this.pIds[1] + " -> idx1 " + 1);
                    System.err.println(this + ".pressed: down " + pointerCount + ", gPtr " + this.gesturePointers(mouseEvent, -1) + ", event " + mouseEvent);
                    break;
                }
                break;
            }
            case 204: {
                final int gesturePointers = this.gesturePointers(mouseEvent, 0);
                if (gesturePointers != 0) {
                    this.zoomFirstTouch = true;
                    this.zoomMode = false;
                }
                else if (gesturePointers == 0) {
                    this.clear(true);
                }
                if (PinchToZoomGesture.DEBUG) {
                    System.err.println(this + ".released: down " + pointerCount + ", gPtr " + gesturePointers + ", event " + mouseEvent);
                }
                break;
            }
            case 206: {
                if (2 <= pointerCount) {
                    final int gesturePointers2 = this.gesturePointers(mouseEvent, -1);
                    if (2 == gesturePointers2) {
                        final int pointerIdx = mouseEvent.getPointerIdx(this.pIds[0]);
                        final int pointerIdx2 = mouseEvent.getPointerIdx(this.pIds[1]);
                        if (0 <= pointerIdx && 0 <= pointerIdx2) {
                            final int n = b ? mouseEvent.getY(pointerIdx) : mouseEvent.getX(pointerIdx);
                            final int n2 = b ? mouseEvent.getY(pointerIdx2) : mouseEvent.getX(pointerIdx2);
                            if (this.zoomFirstTouch) {
                                this.zoomLastEdgeDist = Math.abs(n - n2);
                                this.zoomFirstTouch = false;
                                this.zoomMode = true;
                            }
                            else if (this.zoomMode) {
                                final int abs = Math.abs(n - n2);
                                final int n3 = abs - this.zoomLastEdgeDist;
                                final float n4 = b ? this.surface.getSurfaceHeight() : ((float)this.surface.getSurfaceWidth());
                                final float n5 = n3 / n4;
                                if (PinchToZoomGesture.DEBUG) {
                                    System.err.println("XXX2: id0 " + this.pIds[0] + " -> idx0 " + pointerIdx + ", id1 " + this.pIds[1] + " -> idx1 " + pointerIdx2);
                                    System.err.println("XXX3: d " + abs + ", ld " + this.zoomLastEdgeDist + ", dd " + n3 + ", screen " + n4 + " -> incr " + n5 + ", zoom " + this.zoom + " -> " + (this.zoom + n5));
                                }
                                this.zoom += n5;
                                if (2.0f < this.zoom) {
                                    this.zoom = 2.0f;
                                }
                                else if (0.0f > this.zoom) {
                                    this.zoom = 0.0f;
                                }
                                this.zoomLastEdgeDist = abs;
                                this.zoomEvent = new ZoomEvent(mouseEvent.getSource(), mouseEvent.getWhen(), mouseEvent.getModifiers(), this, mouseEvent, this.zoom, n5, n4);
                            }
                        }
                    }
                    if (PinchToZoomGesture.DEBUG) {
                        System.err.println(this + ".dragged: down " + pointerCount + ", gPtr " + gesturePointers2 + ", event " + mouseEvent);
                    }
                    break;
                }
                break;
            }
        }
        return null != this.zoomEvent;
    }
    
    static {
        DEBUG = Debug.debug("Window.MouseEvent");
    }
    
    public static class ZoomEvent extends GestureEvent
    {
        private final float zoom;
        private final float delta;
        private final float scale;
        
        public ZoomEvent(final Object o, final long n, final int n2, final GestureHandler gestureHandler, final MouseEvent mouseEvent, final float zoom, final float delta, final float scale) {
            super(o, n, n2, gestureHandler, mouseEvent);
            this.zoom = zoom;
            this.delta = delta;
            this.scale = scale;
        }
        
        public final float getZoom() {
            return this.zoom;
        }
        
        public final float getDelta() {
            return this.delta;
        }
        
        public final float getScale() {
            return this.scale;
        }
        
        @Override
        public final String toString() {
            return "ZoomEvent[zoom " + this.zoom + ", delta " + this.delta + ", scale " + this.scale + ", trigger " + this.getTrigger() + ", handler " + this.getHandler() + "]";
        }
    }
}
