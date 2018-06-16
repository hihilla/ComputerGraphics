// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.fixedfunc;

public class GLPointerFuncUtil
{
    public static final String mgl_Vertex = "mgl_Vertex";
    public static final String mgl_Normal = "mgl_Normal";
    public static final String mgl_Color = "mgl_Color";
    public static final String mgl_MultiTexCoord = "mgl_MultiTexCoord";
    public static final String mgl_InterleaveArray = "mgl_InterleaveArray";
    
    public static String getPredefinedArrayIndexName(final int n) {
        return getPredefinedArrayIndexName(n, -1);
    }
    
    public static String getPredefinedArrayIndexName(final int n, final int n2) {
        switch (n) {
            case 32884: {
                return "mgl_Vertex";
            }
            case 32885: {
                return "mgl_Normal";
            }
            case 32886: {
                return "mgl_Color";
            }
            case 32888: {
                if (0 <= n2) {
                    return "mgl_MultiTexCoord" + n2;
                }
                return "mgl_MultiTexCoord" + n2;
            }
            default: {
                return null;
            }
        }
    }
}
