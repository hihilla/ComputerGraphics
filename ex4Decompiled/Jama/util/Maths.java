// 
// Decompiled by Procyon v0.5.30
// 

package Jama.util;

public class Maths
{
    public static double hypot(final double n, final double n2) {
        double n4;
        if (Math.abs(n) > Math.abs(n2)) {
            final double n3 = n2 / n;
            n4 = Math.abs(n) * Math.sqrt(1.0 + n3 * n3);
        }
        else if (n2 != 0.0) {
            final double n5 = n / n2;
            n4 = Math.abs(n2) * Math.sqrt(1.0 + n5 * n5);
        }
        else {
            n4 = 0.0;
        }
        return n4;
    }
}
