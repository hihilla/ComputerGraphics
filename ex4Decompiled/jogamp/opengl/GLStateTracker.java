// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl;

import com.jogamp.opengl.GLException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import com.jogamp.common.util.IntIntHashMap;

public class GLStateTracker
{
    public static final int MIN_CLIENT_ATTRIB_STACK_DEPTH = 16;
    private static final int PIXEL_STATE_MAP_CAPACITY = 32;
    private volatile boolean enabled;
    private IntIntHashMap pixelStateMap;
    private final ArrayList<SavedState> stack;
    
    public GLStateTracker() {
        this.enabled = true;
        (this.pixelStateMap = new IntIntHashMap(32, 0.75f)).setKeyNotFoundValue(-1);
        this.resetStates();
        this.stack = new ArrayList<SavedState>(16);
    }
    
    public final void clearStates() {
        this.pixelStateMap.clear();
    }
    
    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public final boolean isEnabled() {
        return this.enabled;
    }
    
    public final boolean getInt(final int n, final int[] array, final int n2) {
        if (this.enabled) {
            final int value = this.pixelStateMap.get(n);
            if (-1 != value) {
                array[n2] = value;
                return true;
            }
        }
        return false;
    }
    
    public final boolean getInt(final int n, final IntBuffer intBuffer, final int n2) {
        if (this.enabled) {
            final int value = this.pixelStateMap.get(n);
            if (-1 != value) {
                intBuffer.put(intBuffer.position(), value);
                return true;
            }
        }
        return false;
    }
    
    public final void setInt(final int n, final int n2) {
        if (this.enabled) {
            this.pixelStateMap.put(n, n2);
        }
    }
    
    public final void pushAttrib(final int n) {
        if (this.enabled) {
            final SavedState savedState = new SavedState();
            if (0x0 != (n & 0x1)) {
                savedState.setPixelStateMap(this.pixelStateMap);
            }
            this.stack.add(this.stack.size(), savedState);
        }
    }
    
    public final void popAttrib() {
        if (this.enabled) {
            if (this.stack.isEmpty()) {
                throw new GLException("stack contains no elements");
            }
            final SavedState savedState = this.stack.remove(this.stack.size() - 1);
            if (null == savedState) {
                throw new GLException("null stack element (remaining stack size " + this.stack.size() + ")");
            }
            final IntIntHashMap pixelStateMap = savedState.getPixelStateMap();
            if (null != pixelStateMap) {
                this.pixelStateMap = pixelStateMap;
            }
        }
    }
    
    private final void resetStates() {
        this.pixelStateMap.clear();
        this.pixelStateMap.put(3333, 4);
        this.pixelStateMap.put(3328, 0);
        this.pixelStateMap.put(3329, 0);
        this.pixelStateMap.put(3330, 0);
        this.pixelStateMap.put(3331, 0);
        this.pixelStateMap.put(3332, 0);
        this.pixelStateMap.put(32876, 0);
        this.pixelStateMap.put(32875, 0);
        this.pixelStateMap.put(3317, 4);
        this.pixelStateMap.put(3312, 0);
        this.pixelStateMap.put(3313, 0);
        this.pixelStateMap.put(3314, 0);
        this.pixelStateMap.put(3315, 0);
        this.pixelStateMap.put(3316, 0);
        this.pixelStateMap.put(32878, 0);
        this.pixelStateMap.put(32877, 0);
    }
    
    static class SavedState
    {
        private IntIntHashMap pixelStateMap;
        
        final void setPixelStateMap(final IntIntHashMap intIntHashMap) {
            this.pixelStateMap = (IntIntHashMap)intIntHashMap.clone();
        }
        
        final IntIntHashMap getPixelStateMap() {
            return this.pixelStateMap;
        }
    }
}
