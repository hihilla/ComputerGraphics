// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.geom.plane;

import com.jogamp.opengl.math.FloatUtil;

public class Crossing
{
    static final float DELTA = 1.0E-5f;
    static final float ROOT_DELTA = 1.0E-10f;
    public static final int CROSSING = 255;
    static final int UNKNOWN = 254;
    
    public static int solveQuad(final float[] array, final float[] array2) {
        final float n = array[2];
        final float n2 = array[1];
        final float n3 = array[0];
        int n4 = 0;
        if (n == 0.0) {
            if (n2 == 0.0) {
                return -1;
            }
            array2[n4++] = -n3 / n2;
        }
        else {
            final float n5 = n2 * n2 - 4.0f * n * n3;
            if (n5 < 0.0) {
                return 0;
            }
            final float sqrt = FloatUtil.sqrt(n5);
            array2[n4++] = (-n2 + sqrt) / (n * 2.0f);
            if (sqrt != 0.0) {
                array2[n4++] = (-n2 - sqrt) / (n * 2.0f);
            }
        }
        return fixRoots(array2, n4);
    }
    
    public static int solveCubic(final float[] array, final float[] array2) {
        final float n = array[3];
        if (n == 0.0f) {
            return solveQuad(array, array2);
        }
        final float n2 = array[2] / n;
        final float n3 = array[1] / n;
        final float n4 = array[0] / n;
        int n5 = 0;
        final float n6 = (n2 * n2 - 3.0f * n3) / 9.0f;
        final float n7 = (2.0f * n2 * n2 * n2 - 9.0f * n2 * n3 + 27.0f * n4) / 54.0f;
        final float n8 = n6 * n6 * n6;
        final float n9 = n7 * n7;
        final float n10 = -n2 / 3.0f;
        if (n9 < n8) {
            final float n11 = FloatUtil.acos(n7 / FloatUtil.sqrt(n8)) / 3.0f;
            final float n12 = -2.0f * FloatUtil.sqrt(n6);
            array2[n5++] = n12 * FloatUtil.cos(n11) + n10;
            array2[n5++] = n12 * FloatUtil.cos(n11 + 2.0943952f) + n10;
            array2[n5++] = n12 * FloatUtil.cos(n11 - 2.0943952f) + n10;
        }
        else {
            float pow = FloatUtil.pow(FloatUtil.abs(n7) + FloatUtil.sqrt(n9 - n8), 0.33333334f);
            if (n7 > 0.0) {
                pow = -pow;
            }
            if (-1.0E-10f < pow && pow < 1.0E-10f) {
                array2[n5++] = n10;
            }
            else {
                final float n13 = n6 / pow;
                array2[n5++] = pow + n13 + n10;
                final float n14 = n9 - n8;
                if (-1.0E-10f < n14 && n14 < 1.0E-10f) {
                    array2[n5++] = -(pow + n13) / 2.0f + n10;
                }
            }
        }
        return fixRoots(array2, n5);
    }
    
    static int fixRoots(final float[] array, final int n) {
        int n2 = 0;
        int i = 0;
    Label_0004:
        while (i < n) {
            while (true) {
                for (int j = i + 1; j < n; ++j) {
                    if (isZero(array[i] - array[j])) {
                        ++i;
                        continue Label_0004;
                    }
                }
                array[n2++] = array[i];
                continue;
            }
        }
        return n2;
    }
    
    public static int crossLine(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if ((n5 < n && n5 < n3) || (n5 > n && n5 > n3) || (n6 > n2 && n6 > n4) || n == n3) {
            return 0;
        }
        if (n6 >= n2 || n6 >= n4) {
            if ((n4 - n2) * (n5 - n) / (n3 - n) <= n6 - n2) {
                return 0;
            }
        }
        if (n5 == n) {
            return (n < n3) ? 0 : -1;
        }
        if (n5 == n3) {
            return (n < n3) ? 1 : 0;
        }
        return (n < n3) ? 1 : -1;
    }
    
    public static int crossQuad(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        if ((n7 < n && n7 < n3 && n7 < n5) || (n7 > n && n7 > n3 && n7 > n5) || (n8 > n2 && n8 > n4 && n8 > n6) || (n == n3 && n3 == n5)) {
            return 0;
        }
        if (n8 >= n2 || n8 >= n4 || n8 >= n6 || n7 == n || n7 == n5) {
            final QuadCurve quadCurve = new QuadCurve(n, n2, n3, n4, n5, n6);
            final float n9 = n7 - n;
            final float n10 = n8 - n2;
            final float[] array = new float[3];
            return quadCurve.cross(array, quadCurve.solvePoint(array, n9), n10, n10);
        }
        if (n < n5) {
            return (n < n7 && n7 < n5) ? 1 : 0;
        }
        return (n5 < n7 && n7 < n) ? -1 : 0;
    }
    
    public static int crossCubic(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10) {
        if ((n9 < n && n9 < n3 && n9 < n5 && n9 < n7) || (n9 > n && n9 > n3 && n9 > n5 && n9 > n7) || (n10 > n2 && n10 > n4 && n10 > n6 && n10 > n8) || (n == n3 && n3 == n5 && n5 == n7)) {
            return 0;
        }
        if (n10 >= n2 || n10 >= n4 || n10 >= n6 || n10 >= n8 || n9 == n || n9 == n7) {
            final CubicCurve cubicCurve = new CubicCurve(n, n2, n3, n4, n5, n6, n7, n8);
            final float n11 = n9 - n;
            final float n12 = n10 - n2;
            final float[] array = new float[3];
            return cubicCurve.cross(array, cubicCurve.solvePoint(array, n11), n12, n12);
        }
        if (n < n7) {
            return (n < n9 && n9 < n7) ? 1 : 0;
        }
        return (n7 < n9 && n9 < n) ? -1 : 0;
    }
    
    public static int crossPath(final PathIterator pathIterator, final float n, final float n2) {
        int n3 = 0;
        float n7;
        float n6;
        float n5;
        float n4 = n5 = (n6 = (n7 = 0.0f));
        final float[] array = new float[6];
        while (!pathIterator.isDone()) {
            final int currentSegment = pathIterator.currentSegment(array);
            switch (currentSegment) {
                case 0: {
                    if (n6 != n5 || n7 != n4) {
                        n3 += crossLine(n6, n7, n5, n4, n, n2);
                    }
                    n6 = (n5 = array[0]);
                    n7 = (n4 = array[1]);
                    break;
                }
                case 1: {
                    n3 += crossLine(n6, n7, n6 = array[0], n7 = array[1], n, n2);
                    break;
                }
                case 2: {
                    n3 += crossQuad(n6, n7, array[0], array[1], n6 = array[2], n7 = array[3], n, n2);
                    break;
                }
                case 3: {
                    n3 += crossCubic(n6, n7, array[0], array[1], array[2], array[3], n6 = array[4], n7 = array[5], n, n2);
                    break;
                }
                case 4: {
                    if (n7 != n4 || n6 != n5) {
                        n3 += crossLine(n6, n7, n6 = n5, n7 = n4, n, n2);
                        break;
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unhandled Segment Type: " + currentSegment);
                }
            }
            if (n == n6 && n2 == n7) {
                n3 = 0;
                n7 = n4;
                break;
            }
            pathIterator.next();
        }
        if (n7 != n4) {
            n3 += crossLine(n6, n7, n5, n4, n, n2);
        }
        return n3;
    }
    
    public static int crossShape(final Path2D path2D, final float n, final float n2) {
        if (!path2D.getBounds2D().contains(n, n2)) {
            return 0;
        }
        return crossPath(path2D.iterator(null), n, n2);
    }
    
    public static boolean isZero(final float n) {
        return -1.0E-5f < n && n < 1.0E-5f;
    }
    
    static void sortBound(final float[] array, final int n) {
        for (int i = 0; i < n - 4; i += 4) {
            int n2 = i;
            for (int j = i + 4; j < n; j += 4) {
                if (array[n2] > array[j]) {
                    n2 = j;
                }
            }
            if (n2 != i) {
                final float n3 = array[i];
                array[i] = array[n2];
                array[n2] = n3;
                final float n4 = array[i + 1];
                array[i + 1] = array[n2 + 1];
                array[n2 + 1] = n4;
                final float n5 = array[i + 2];
                array[i + 2] = array[n2 + 2];
                array[n2 + 2] = n5;
                final float n6 = array[i + 3];
                array[i + 3] = array[n2 + 3];
                array[n2 + 3] = n6;
            }
        }
    }
    
    static int crossBound(final float[] array, final int n, final float n2, final float n3) {
        if (n == 0) {
            return 0;
        }
        int n4 = 0;
        int n5 = 0;
        for (int i = 2; i < n; i += 4) {
            if (array[i] < n2) {
                ++n4;
            }
            else {
                if (array[i] <= n3) {
                    return 255;
                }
                ++n5;
            }
        }
        if (n5 == 0) {
            return 0;
        }
        if (n4 != 0) {
            sortBound(array, n);
            boolean b = array[2] > n3;
            for (int j = 6; j < n; j += 4) {
                final boolean b2 = array[j] > n3;
                if (b != b2 && array[j + 1] != array[j - 3]) {
                    return 255;
                }
                b = b2;
            }
        }
        return 254;
    }
    
    public static int intersectLine(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        if ((n7 < n && n7 < n3) || (n5 > n && n5 > n3) || (n6 > n2 && n6 > n4)) {
            return 0;
        }
        if (n8 >= n2 || n8 >= n4) {
            if (n == n3) {
                return 255;
            }
            float n9;
            float n10;
            if (n < n3) {
                n9 = ((n < n5) ? n5 : n);
                n10 = ((n3 < n7) ? n3 : n7);
            }
            else {
                n9 = ((n3 < n5) ? n5 : n3);
                n10 = ((n < n7) ? n : n7);
            }
            final float n11 = (n4 - n2) / (n3 - n);
            final float n12 = n11 * (n9 - n) + n2;
            final float n13 = n11 * (n10 - n) + n2;
            if (n12 < n6 && n13 < n6) {
                return 0;
            }
            if (n12 <= n8 || n13 <= n8) {
                return 255;
            }
        }
        if (n == n3) {
            return 0;
        }
        if (n5 == n) {
            return (n < n3) ? 0 : -1;
        }
        if (n5 == n3) {
            return (n < n3) ? 1 : 0;
        }
        if (n < n3) {
            return (n < n5 && n5 < n3) ? 1 : 0;
        }
        return (n3 < n5 && n5 < n) ? -1 : 0;
    }
    
    public static int intersectQuad(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10) {
        if ((n9 < n && n9 < n3 && n9 < n5) || (n7 > n && n7 > n3 && n7 > n5) || (n8 > n2 && n8 > n4 && n8 > n6)) {
            return 0;
        }
        if (n10 < n2 && n10 < n4 && n10 < n6 && n7 != n && n7 != n5) {
            if (n < n5) {
                return (n < n7 && n7 < n5) ? 1 : 0;
            }
            return (n5 < n7 && n7 < n) ? -1 : 0;
        }
        else {
            final QuadCurve quadCurve = new QuadCurve(n, n2, n3, n4, n5, n6);
            final float n11 = n7 - n;
            final float n12 = n8 - n2;
            final float n13 = n9 - n;
            final float n14 = n10 - n2;
            final float[] array = new float[3];
            final float[] array2 = new float[3];
            final int solvePoint = quadCurve.solvePoint(array, n11);
            final int solvePoint2 = quadCurve.solvePoint(array2, n13);
            if (solvePoint == 0 && solvePoint2 == 0) {
                return 0;
            }
            final float n15 = n11 - 1.0E-5f;
            final float n16 = n13 + 1.0E-5f;
            final float[] array3 = new float[28];
            int addBound = quadCurve.addBound(array3, quadCurve.addBound(array3, quadCurve.addBound(array3, 0, array, solvePoint, n15, n16, false, 0), array2, solvePoint2, n15, n16, false, 1), array2, quadCurve.solveExtrem(array2), n15, n16, true, 2);
            if (n7 < n && n < n9) {
                array3[addBound++] = 0.0f;
                array3[addBound++] = 0.0f;
                array3[addBound++] = 0.0f;
                array3[addBound++] = 4.0f;
            }
            if (n7 < n5 && n5 < n9) {
                array3[addBound++] = 1.0f;
                array3[addBound++] = quadCurve.ax;
                array3[addBound++] = quadCurve.ay;
                array3[addBound++] = 5.0f;
            }
            final int crossBound = crossBound(array3, addBound, n12, n14);
            if (crossBound != 254) {
                return crossBound;
            }
            return quadCurve.cross(array, solvePoint, n12, n14);
        }
    }
    
    public static int intersectCubic(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12) {
        if ((n11 < n && n11 < n3 && n11 < n5 && n11 < n7) || (n9 > n && n9 > n3 && n9 > n5 && n9 > n7) || (n10 > n2 && n10 > n4 && n10 > n6 && n10 > n8)) {
            return 0;
        }
        if (n12 < n2 && n12 < n4 && n12 < n6 && n12 < n8 && n9 != n && n9 != n7) {
            if (n < n7) {
                return (n < n9 && n9 < n7) ? 1 : 0;
            }
            return (n7 < n9 && n9 < n) ? -1 : 0;
        }
        else {
            final CubicCurve cubicCurve = new CubicCurve(n, n2, n3, n4, n5, n6, n7, n8);
            final float n13 = n9 - n;
            final float n14 = n10 - n2;
            final float n15 = n11 - n;
            final float n16 = n12 - n2;
            final float[] array = new float[3];
            final float[] array2 = new float[3];
            final int solvePoint = cubicCurve.solvePoint(array, n13);
            final int solvePoint2 = cubicCurve.solvePoint(array2, n15);
            if (solvePoint == 0 && solvePoint2 == 0) {
                return 0;
            }
            final float n17 = n13 - 1.0E-5f;
            final float n18 = n15 + 1.0E-5f;
            final float[] array3 = new float[40];
            int addBound = cubicCurve.addBound(array3, cubicCurve.addBound(array3, cubicCurve.addBound(array3, cubicCurve.addBound(array3, 0, array, solvePoint, n17, n18, false, 0), array2, solvePoint2, n17, n18, false, 1), array2, cubicCurve.solveExtremX(array2), n17, n18, true, 2), array2, cubicCurve.solveExtremY(array2), n17, n18, true, 4);
            if (n9 < n && n < n11) {
                array3[addBound++] = 0.0f;
                array3[addBound++] = 0.0f;
                array3[addBound++] = 0.0f;
                array3[addBound++] = 6.0f;
            }
            if (n9 < n7 && n7 < n11) {
                array3[addBound++] = 1.0f;
                array3[addBound++] = cubicCurve.ax;
                array3[addBound++] = cubicCurve.ay;
                array3[addBound++] = 7.0f;
            }
            final int crossBound = crossBound(array3, addBound, n14, n16);
            if (crossBound != 254) {
                return crossBound;
            }
            return cubicCurve.cross(array, solvePoint, n14, n16);
        }
    }
    
    public static int intersectPath(final PathIterator pathIterator, final float n, final float n2, final float n3, final float n4) {
        int n5 = 0;
        float n9;
        float n8;
        float n7;
        float n6 = n7 = (n8 = (n9 = 0.0f));
        final float[] array = new float[6];
        final float n10 = n + n3;
        final float n11 = n2 + n4;
        while (!pathIterator.isDone()) {
            int n12 = 0;
            final int currentSegment = pathIterator.currentSegment(array);
            switch (currentSegment) {
                case 0: {
                    if (n8 != n7 || n9 != n6) {
                        n12 = intersectLine(n8, n9, n7, n6, n, n2, n10, n11);
                    }
                    n8 = (n7 = array[0]);
                    n9 = (n6 = array[1]);
                    break;
                }
                case 1: {
                    n12 = intersectLine(n8, n9, n8 = array[0], n9 = array[1], n, n2, n10, n11);
                    break;
                }
                case 2: {
                    n12 = intersectQuad(n8, n9, array[0], array[1], n8 = array[2], n9 = array[3], n, n2, n10, n11);
                    break;
                }
                case 3: {
                    n12 = intersectCubic(n8, n9, array[0], array[1], array[2], array[3], n8 = array[4], n9 = array[5], n, n2, n10, n11);
                    break;
                }
                case 4: {
                    if (n9 != n6 || n8 != n7) {
                        n12 = intersectLine(n8, n9, n7, n6, n, n2, n10, n11);
                    }
                    n8 = n7;
                    n9 = n6;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unhandled Segment Type: " + currentSegment);
                }
            }
            if (n12 == 255) {
                return 255;
            }
            n5 += n12;
            pathIterator.next();
        }
        if (n9 != n6) {
            final int intersectLine = intersectLine(n8, n9, n7, n6, n, n2, n10, n11);
            if (intersectLine == 255) {
                return 255;
            }
            n5 += intersectLine;
        }
        return n5;
    }
    
    public static int intersectShape(final Path2D path2D, final float n, final float n2, final float n3, final float n4) {
        if (!path2D.getBounds2D().intersects2DRegion(n, n2, n3, n4)) {
            return 0;
        }
        return intersectPath(path2D.iterator(null), n, n2, n3, n4);
    }
    
    public static boolean isInsideNonZero(final int n) {
        return n != 0;
    }
    
    public static boolean isInsideEvenOdd(final int n) {
        return (n & 0x1) != 0x0;
    }
    
    public static class QuadCurve
    {
        float ax;
        float ay;
        float bx;
        float by;
        float Ax;
        float Ay;
        float Bx;
        float By;
        
        public QuadCurve(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
            this.ax = n5 - n;
            this.ay = n6 - n2;
            this.bx = n3 - n;
            this.by = n4 - n2;
            this.Bx = this.bx + this.bx;
            this.Ax = this.ax - this.Bx;
            this.By = this.by + this.by;
            this.Ay = this.ay - this.By;
        }
        
        int cross(final float[] array, final int n, final float n2, final float n3) {
            int n4 = 0;
            for (final float n5 : array) {
                if (n5 >= -1.0E-5f) {
                    if (n5 <= 1.00001f) {
                        if (n5 < 1.0E-5f) {
                            if (n2 < 0.0 && ((this.bx != 0.0) ? this.bx : (this.ax - this.bx)) < 0.0) {
                                --n4;
                            }
                        }
                        else if (n5 > 0.99999f) {
                            if (n2 < this.ay && ((this.ax != this.bx) ? (this.ax - this.bx) : this.bx) > 0.0) {
                                ++n4;
                            }
                        }
                        else if (n5 * (n5 * this.Ay + this.By) > n3) {
                            final float n6 = n5 * this.Ax + this.bx;
                            if (n6 <= -1.0E-5f || n6 >= 1.0E-5f) {
                                n4 += ((n6 > 0.0) ? 1 : -1);
                            }
                        }
                    }
                }
            }
            return n4;
        }
        
        int solvePoint(final float[] array, final float n) {
            return Crossing.solveQuad(new float[] { -n, this.Bx, this.Ax }, array);
        }
        
        int solveExtrem(final float[] array) {
            int n = 0;
            if (this.Ax != 0.0) {
                array[n++] = -this.Bx / (this.Ax + this.Ax);
            }
            if (this.Ay != 0.0) {
                array[n++] = -this.By / (this.Ay + this.Ay);
            }
            return n;
        }
        
        int addBound(final float[] array, int n, final float[] array2, final int n2, final float n3, final float n4, final boolean b, int n5) {
            for (final float n6 : array2) {
                if (n6 > -1.0E-5f && n6 < 1.00001f) {
                    final float n7 = n6 * (n6 * this.Ax + this.Bx);
                    if (n3 <= n7 && n7 <= n4) {
                        array[n++] = n6;
                        array[n++] = n7;
                        array[n++] = n6 * (n6 * this.Ay + this.By);
                        array[n++] = n5;
                        if (b) {
                            ++n5;
                        }
                    }
                }
            }
            return n;
        }
    }
    
    public static class CubicCurve
    {
        float ax;
        float ay;
        float bx;
        float by;
        float cx;
        float cy;
        float Ax;
        float Ay;
        float Bx;
        float By;
        float Cx;
        float Cy;
        float Ax3;
        float Bx2;
        
        public CubicCurve(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
            this.ax = n7 - n;
            this.ay = n8 - n2;
            this.bx = n3 - n;
            this.by = n4 - n2;
            this.cx = n5 - n;
            this.cy = n6 - n2;
            this.Cx = this.bx + this.bx + this.bx;
            this.Bx = this.cx + this.cx + this.cx - this.Cx - this.Cx;
            this.Ax = this.ax - this.Bx - this.Cx;
            this.Cy = this.by + this.by + this.by;
            this.By = this.cy + this.cy + this.cy - this.Cy - this.Cy;
            this.Ay = this.ay - this.By - this.Cy;
            this.Ax3 = this.Ax + this.Ax + this.Ax;
            this.Bx2 = this.Bx + this.Bx;
        }
        
        int cross(final float[] array, final int n, final float n2, final float n3) {
            int n4 = 0;
            for (final float n5 : array) {
                Label_0328: {
                    if (n5 >= -1.0E-5f) {
                        if (n5 <= 1.00001f) {
                            if (n5 < 1.0E-5f) {
                                if (n2 < 0.0 && ((this.bx != 0.0) ? this.bx : ((this.cx != this.bx) ? (this.cx - this.bx) : (this.ax - this.cx))) < 0.0) {
                                    --n4;
                                }
                            }
                            else if (n5 > 0.99999f) {
                                if (n2 < this.ay && ((this.ax != this.cx) ? (this.ax - this.cx) : ((this.cx != this.bx) ? (this.cx - this.bx) : this.bx)) > 0.0) {
                                    ++n4;
                                }
                            }
                            else if (n5 * (n5 * (n5 * this.Ay + this.By) + this.Cy) > n3) {
                                float ax = n5 * (n5 * this.Ax3 + this.Bx2) + this.Cx;
                                if (ax > -1.0E-5f && ax < 1.0E-5f) {
                                    final float n6 = n5 * (this.Ax3 + this.Ax3) + this.Bx2;
                                    if (n6 < -1.0E-5f) {
                                        break Label_0328;
                                    }
                                    if (n6 > 1.0E-5f) {
                                        break Label_0328;
                                    }
                                    ax = this.ax;
                                }
                                n4 += ((ax > 0.0) ? 1 : -1);
                            }
                        }
                    }
                }
            }
            return n4;
        }
        
        int solvePoint(final float[] array, final float n) {
            return Crossing.solveCubic(new float[] { -n, this.Cx, this.Bx, this.Ax }, array);
        }
        
        int solveExtremX(final float[] array) {
            return Crossing.solveQuad(new float[] { this.Cx, this.Bx2, this.Ax3 }, array);
        }
        
        int solveExtremY(final float[] array) {
            return Crossing.solveQuad(new float[] { this.Cy, this.By + this.By, this.Ay + this.Ay + this.Ay }, array);
        }
        
        int addBound(final float[] array, int n, final float[] array2, final int n2, final float n3, final float n4, final boolean b, int n5) {
            for (final float n6 : array2) {
                if (n6 > -1.0E-5f && n6 < 1.00001f) {
                    final float n7 = n6 * (n6 * (n6 * this.Ax + this.Bx) + this.Cx);
                    if (n3 <= n7 && n7 <= n4) {
                        array[n++] = n6;
                        array[n++] = n7;
                        array[n++] = n6 * (n6 * (n6 * this.Ay + this.By) + this.Cy);
                        array[n++] = n5;
                        if (b) {
                            ++n5;
                        }
                    }
                }
            }
            return n;
        }
    }
}
