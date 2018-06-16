// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.newt.event;

import com.jogamp.common.util.PropertyAccess;
import jogamp.newt.Debug;

public class DoubleTapScrollGesture implements GestureHandler
{
    public static final int SCROLL_SLOP_PIXEL;
    public static final int DOUBLE_TAP_SLOP_PIXEL;
    public static final float SCROLL_SLOP_MM;
    public static final float DOUBLE_TAP_SLOP_MM;
    private static final int ST_NONE = 0;
    private static final int ST_1PRESS = 1;
    private static final int ST_2PRESS_T = 2;
    private static final int ST_2PRESS_C = 3;
    private static final int ST_SCROLL = 4;
    private final int scrollSlop;
    private final int scrollSlopSquare;
    private final int doubleTapSlop;
    private final int doubleTapSlopSquare;
    private final float[] scrollDistance;
    private final int[] pIds;
    private int gestureState;
    private int sqStartDist;
    private int lastX;
    private int lastY;
    private int pointerDownCount;
    private MouseEvent hitGestureEvent;
    
    private static final int getSquareDistance(final float n, final float n2, final float n3, final float n4) {
        final int n5 = (int)n - (int)n3;
        final int n6 = (int)n2 - (int)n4;
        return n5 * n5 + n6 * n6;
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
    
    public DoubleTapScrollGesture(final int scrollSlop, final int doubleTapSlop) {
        this.scrollDistance = new float[] { 0.0f, 0.0f };
        this.pIds = new int[] { -1, -1 };
        this.scrollSlop = scrollSlop;
        this.scrollSlopSquare = scrollSlop * scrollSlop;
        this.doubleTapSlop = doubleTapSlop;
        this.doubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
        this.pointerDownCount = 0;
        this.clear(true);
        if (DoubleTapScrollGesture.DEBUG) {
            System.err.println("DoubleTapScroll    scrollSlop (scaled) " + this.scrollSlop);
            System.err.println("DoubleTapScroll doubleTapSlop (scaled) " + this.doubleTapSlop);
        }
    }
    
    @Override
    public String toString() {
        return "DoubleTapScroll[state " + this.gestureState + ", in " + this.isWithinGesture() + ", has " + (null != this.hitGestureEvent) + ", pc " + this.pointerDownCount + "]";
    }
    
    @Override
    public void clear(final boolean b) {
        this.scrollDistance[0] = 0.0f;
        this.scrollDistance[1] = 0.0f;
        this.hitGestureEvent = null;
        if (b) {
            this.gestureState = 0;
            this.sqStartDist = 0;
            this.pIds[0] = -1;
            this.pIds[1] = -1;
            this.lastX = 0;
            this.lastY = 0;
        }
    }
    
    @Override
    public boolean isWithinGesture() {
        return 3 <= this.gestureState;
    }
    
    @Override
    public boolean hasGesture() {
        return null != this.hitGestureEvent;
    }
    
    @Override
    public InputEvent getGestureEvent() {
        if (null != this.hitGestureEvent) {
            final MouseEvent hitGestureEvent = this.hitGestureEvent;
            int modifiers = hitGestureEvent.getModifiers();
            final float[] rotation = hitGestureEvent.getRotation();
            rotation[0] = this.scrollDistance[0] / this.scrollSlop;
            rotation[1] = this.scrollDistance[1] / this.scrollSlop;
            if (rotation[0] * rotation[0] > rotation[1] * rotation[1]) {
                modifiers |= 0x1;
            }
            return new MouseEvent((short)207, hitGestureEvent.getSource(), hitGestureEvent.getWhen(), modifiers, hitGestureEvent.getAllPointerTypes(), hitGestureEvent.getAllPointerIDs(), hitGestureEvent.getAllX(), hitGestureEvent.getAllY(), hitGestureEvent.getAllPressures(), hitGestureEvent.getMaxPressure(), hitGestureEvent.getButton(), hitGestureEvent.getClickCount(), rotation, this.scrollSlop);
        }
        return null;
    }
    
    public final float[] getScrollDistanceXY() {
        return this.scrollDistance;
    }
    
    @Override
    public boolean process(final InputEvent inputEvent) {
        if (null != this.hitGestureEvent || !(inputEvent instanceof MouseEvent)) {
            return true;
        }
        final MouseEvent hitGestureEvent = (MouseEvent)inputEvent;
        if (hitGestureEvent.getPointerType(0).getPointerClass() != MouseEvent.PointerClass.Onscreen) {
            return false;
        }
        this.pointerDownCount = hitGestureEvent.getPointerCount();
        final short eventType = hitGestureEvent.getEventType();
        final int x = hitGestureEvent.getX(0);
        final int y = hitGestureEvent.getY(0);
        switch (eventType) {
            case 203: {
                int gesturePointers = 0;
                if (0 == this.gestureState && 1 == this.pointerDownCount) {
                    this.pIds[0] = hitGestureEvent.getPointerId(0);
                    this.pIds[1] = -1;
                    this.gestureState = 1;
                }
                else if (0 < this.gestureState && 2 == this.pointerDownCount && 1 == this.gesturePointers(hitGestureEvent, 0)) {
                    final int x2 = hitGestureEvent.getX(1);
                    final int y2 = hitGestureEvent.getY(1);
                    final int n = (x + x2) / 2;
                    final int n2 = (y + y2) / 2;
                    if (1 == this.gestureState) {
                        final int squareDistance = getSquareDistance(x, y, x2, y2);
                        final boolean b = squareDistance < this.doubleTapSlopSquare;
                        if (b) {
                            gesturePointers = 2;
                            this.pIds[0] = hitGestureEvent.getPointerId(0);
                            this.pIds[1] = hitGestureEvent.getPointerId(1);
                            this.lastX = n;
                            this.lastY = n2;
                            this.sqStartDist = squareDistance;
                            this.gestureState = 2;
                        }
                        if (DoubleTapScrollGesture.DEBUG) {
                            System.err.println(this + ".pressed.1: dist " + (int)Math.round(Math.sqrt(squareDistance)) + ", gPtr " + gesturePointers + ", distWithin2DTSlop " + b + ", last " + this.lastX + "/" + this.lastY + ", " + hitGestureEvent);
                        }
                    }
                    else if (3 == this.gestureState) {
                        gesturePointers = this.gesturePointers(hitGestureEvent, -1);
                        if (2 == gesturePointers) {
                            this.lastX = n;
                            this.lastY = n2;
                        }
                        else {
                            this.clear(true);
                        }
                    }
                }
                if (DoubleTapScrollGesture.DEBUG) {
                    System.err.println(this + ".pressed: gPtr " + gesturePointers + ", this " + this.lastX + "/" + this.lastY + ", " + hitGestureEvent);
                }
                break;
            }
            case 204: {
                --this.pointerDownCount;
                final int gesturePointers2 = this.gesturePointers(hitGestureEvent, 0);
                if (gesturePointers2 != 0) {
                    this.gestureState = 3;
                }
                else if (gesturePointers2 == 0) {
                    this.clear(true);
                }
                if (DoubleTapScrollGesture.DEBUG) {
                    System.err.println(this + ".released: gPtr " + gesturePointers2 + ", " + hitGestureEvent);
                }
                break;
            }
            case 206: {
                if (2 == this.pointerDownCount && 1 < this.gestureState) {
                    final int gesturePointers3 = this.gesturePointers(hitGestureEvent, -1);
                    if (2 == gesturePointers3) {
                        final int x3 = hitGestureEvent.getX(1);
                        final int y3 = hitGestureEvent.getY(1);
                        final int lastX = (x + x3) / 2;
                        final int lastY = (y + y3) / 2;
                        final int squareDistance2 = getSquareDistance(x, y, x3, y3);
                        final boolean b2 = Math.abs(squareDistance2 - this.sqStartDist) <= this.doubleTapSlopSquare;
                        if (b2) {
                            switch (this.gestureState) {
                                case 2: {
                                    if (getSquareDistance(this.lastX, this.lastY, lastX, lastY) > this.scrollSlopSquare) {
                                        this.gestureState = 4;
                                    }
                                    break;
                                }
                                case 3: {
                                    this.gestureState = 4;
                                    break;
                                }
                                case 4: {
                                    this.scrollDistance[0] = this.lastX - lastX;
                                    this.scrollDistance[1] = this.lastY - lastY;
                                    this.hitGestureEvent = hitGestureEvent;
                                    break;
                                }
                            }
                            if (DoubleTapScrollGesture.DEBUG) {
                                System.err.println(this + ".dragged.1: pDist " + (int)Math.round(Math.sqrt(squareDistance2)) + ", scrollLen " + (int)Math.round(Math.sqrt(getSquareDistance(this.lastX, this.lastY, lastX, lastY))) + ", gPtr " + gesturePointers3 + " [" + this.pIds[0] + ", " + this.pIds[1] + "]" + ", diffDistWithinTapSlop " + b2 + ", distWithin2DTSlop " + (squareDistance2 < this.doubleTapSlopSquare) + ", this " + lastX + "/" + lastY + ", last " + this.lastX + "/" + this.lastY + ", d " + this.scrollDistance[0] + "/" + this.scrollDistance[1]);
                            }
                        }
                        else {
                            if (DoubleTapScrollGesture.DEBUG) {
                                System.err.println(this + ".dragged.X1: pDist " + (int)Math.round(Math.sqrt(squareDistance2)) + ", distStart " + (int)Math.round(Math.sqrt(this.sqStartDist)) + ", gPtr " + gesturePointers3 + " [" + this.pIds[0] + ", " + this.pIds[1] + "]" + ", diffDistWithinTapSlop " + b2 + ", distWithin2DTSlop " + (squareDistance2 < this.doubleTapSlopSquare) + ", this " + lastX + "/" + lastY + ", last " + this.lastX + "/" + this.lastY + ", d " + this.scrollDistance[0] + "/" + this.scrollDistance[1]);
                            }
                            this.clear(true);
                        }
                        if (2 < this.gestureState) {
                            this.lastX = lastX;
                            this.lastY = lastY;
                        }
                    }
                    else {
                        if (DoubleTapScrollGesture.DEBUG) {
                            System.err.println(this + ".dragged.X2: gPtr " + gesturePointers3 + " [" + this.pIds[0] + ", " + this.pIds[1] + "]" + ", last " + this.lastX + "/" + this.lastY + ", d " + this.scrollDistance[0] + "/" + this.scrollDistance[1]);
                        }
                        this.clear(true);
                    }
                    break;
                }
                break;
            }
        }
        return null != this.hitGestureEvent;
    }
    
    static {
        Debug.initSingleton();
        SCROLL_SLOP_PIXEL = PropertyAccess.getIntProperty("newt.event.scroll_slop_pixel", true, 16);
        DOUBLE_TAP_SLOP_PIXEL = PropertyAccess.getIntProperty("newt.event.double_tap_slop_pixel", true, 104);
        SCROLL_SLOP_MM = PropertyAccess.getIntProperty("newt.event.scroll_slop_mm", true, 3);
        DOUBLE_TAP_SLOP_MM = PropertyAccess.getIntProperty("newt.event.double_tap_slop_mm", true, 20);
    }
}
