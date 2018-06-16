// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

public class SurfaceScaleUtils
{
    private static final float EPSILON = 1.1920929E-7f;
    
    private static boolean isZero(final float n) {
        return Math.abs(n) < 1.1920929E-7f;
    }
    
    public static int scale(final int n, final float n2) {
        return (int)(n * n2 + 0.5f);
    }
    
    public static int scaleInv(final int n, final float n2) {
        return (int)(n / n2 + 0.5f);
    }
    
    public static int[] scale(final int[] array, final int[] array2, final float[] array3) {
        array[0] = (int)(array2[0] * array3[0] + 0.5f);
        array[1] = (int)(array2[1] * array3[1] + 0.5f);
        return array;
    }
    
    public static int[] scaleInv(final int[] array, final int[] array2, final float[] array3) {
        array[0] = (int)(array2[0] / array3[0] + 0.5f);
        array[1] = (int)(array2[1] / array3[1] + 0.5f);
        return array;
    }
    
    public static float clampPixelScale(final float n, final float n2, final float n3) {
        if (isZero(n - 1.0f)) {
            return 1.0f;
        }
        if (isZero(n - 0.0f) || n > n3 || isZero(n - n3)) {
            return n3;
        }
        if (n < n2 || isZero(n - n2)) {
            return n2;
        }
        return n;
    }
    
    public static float[] clampPixelScale(final float[] array, final float[] array2, final float[] array3, final float[] array4) {
        array[0] = clampPixelScale(array2[0], array3[0], array4[0]);
        array[1] = clampPixelScale(array2[1], array3[1], array4[1]);
        return array;
    }
    
    public static boolean setNewPixelScale(final float[] array, final float[] array2, final float[] array3, final float[] array4, final float[] array5, final String s) {
        final float clampPixelScale = clampPixelScale(array3[0], array4[0], array5[0]);
        final float clampPixelScale2 = clampPixelScale(array3[1], array4[1], array5[1]);
        final boolean b = clampPixelScale != array2[0] || clampPixelScale2 != array2[1];
        if (null != s) {
            System.err.println(s + ".setNewPixelScale: pre[" + array2[0] + ", " + array2[1] + "], req[" + array3[0] + ", " + array3[1] + "], min[" + array4[0] + ", " + array4[1] + "], max[" + array5[0] + ", " + array5[1] + "] -> result[" + clampPixelScale + ", " + clampPixelScale2 + "], changed " + b);
        }
        array[0] = clampPixelScale;
        array[1] = clampPixelScale2;
        return b;
    }
}
