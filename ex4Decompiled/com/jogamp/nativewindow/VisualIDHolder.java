// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow;

import java.util.Comparator;

public interface VisualIDHolder
{
    public static final int VID_UNDEFINED = 0;
    
    int getVisualID(final VIDType p0) throws NativeWindowException;
    
    public enum VIDType
    {
        INTRINSIC(0), 
        NATIVE(1), 
        EGL_CONFIG(10), 
        X11_XVISUAL(20), 
        X11_FBCONFIG(21), 
        WIN32_PFD(30);
        
        public final int id;
        
        private VIDType(final int id) {
            this.id = id;
        }
    }
    
    public static class VIDComparator implements Comparator<VisualIDHolder>
    {
        private final VIDType type;
        
        public VIDComparator(final VIDType type) {
            this.type = type;
        }
        
        @Override
        public int compare(final VisualIDHolder visualIDHolder, final VisualIDHolder visualIDHolder2) {
            final int visualID = visualIDHolder.getVisualID(this.type);
            final int visualID2 = visualIDHolder2.getVisualID(this.type);
            if (visualID > visualID2) {
                return 1;
            }
            if (visualID < visualID2) {
                return -1;
            }
            return 0;
        }
    }
}
