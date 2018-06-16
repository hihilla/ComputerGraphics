// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class Normal
{
    static boolean SLANTED_SWEEP;
    static double S_UNIT_X;
    static double S_UNIT_Y;
    private static final boolean TRUE_PROJECT = false;
    
    private static double Dot(final double[] array, final double[] array2) {
        return array[0] * array2[0] + array[1] * array2[1] + array[2] * array2[2];
    }
    
    static void Normalize(final double[] array) {
        final double n = array[0] * array[0] + array[1] * array[1] + array[2] * array[2];
        assert n > 0.0;
        final double sqrt = Math.sqrt(n);
        final int n2 = 0;
        array[n2] /= sqrt;
        final int n3 = 1;
        array[n3] /= sqrt;
        final int n4 = 2;
        array[n4] /= sqrt;
    }
    
    static int LongAxis(final double[] array) {
        int n = 0;
        if (Math.abs(array[1]) > Math.abs(array[0])) {
            n = 1;
        }
        if (Math.abs(array[2]) > Math.abs(array[n])) {
            n = 2;
        }
        return n;
    }
    
    static void ComputeNormal(final GLUtessellatorImpl glUtessellatorImpl, final double[] array) {
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        final double[] array2 = new double[3];
        final double[] array3 = new double[3];
        final GLUvertex[] array4 = new GLUvertex[3];
        final GLUvertex[] array5 = new GLUvertex[3];
        final double[] array6 = new double[3];
        final double[] array7 = new double[3];
        final double[] array8 = new double[3];
        final double[] array9 = array2;
        final int n = 0;
        final double[] array10 = array2;
        final int n2 = 1;
        final double[] array11 = array2;
        final int n3 = 2;
        final double n4 = -2.0E150;
        array11[n3] = n4;
        array9[n] = (array10[n2] = n4);
        final double[] array12 = array3;
        final int n5 = 0;
        final double[] array13 = array3;
        final int n6 = 1;
        final double[] array14 = array3;
        final int n7 = 2;
        final double n8 = 2.0E150;
        array14[n7] = n8;
        array12[n5] = (array13[n6] = n8);
        for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
            for (int i = 0; i < 3; ++i) {
                final double n9 = glUvertex.coords[i];
                if (n9 < array3[i]) {
                    array3[i] = n9;
                    array4[i] = glUvertex;
                }
                if (n9 > array2[i]) {
                    array2[i] = n9;
                    array5[i] = glUvertex;
                }
            }
        }
        int n10 = 0;
        if (array2[1] - array3[1] > array2[0] - array3[0]) {
            n10 = 1;
        }
        if (array2[2] - array3[2] > array2[n10] - array3[n10]) {
            n10 = 2;
        }
        if (array3[n10] >= array2[n10]) {
            array[1] = (array[0] = 0.0);
            array[2] = 1.0;
            return;
        }
        double n11 = 0.0;
        final GLUvertex glUvertex2 = array4[n10];
        final GLUvertex glUvertex3 = array5[n10];
        array6[0] = glUvertex2.coords[0] - glUvertex3.coords[0];
        array6[1] = glUvertex2.coords[1] - glUvertex3.coords[1];
        array6[2] = glUvertex2.coords[2] - glUvertex3.coords[2];
        for (GLUvertex glUvertex4 = vHead.next; glUvertex4 != vHead; glUvertex4 = glUvertex4.next) {
            array7[0] = glUvertex4.coords[0] - glUvertex3.coords[0];
            array7[1] = glUvertex4.coords[1] - glUvertex3.coords[1];
            array7[2] = glUvertex4.coords[2] - glUvertex3.coords[2];
            array8[0] = array6[1] * array7[2] - array6[2] * array7[1];
            array8[1] = array6[2] * array7[0] - array6[0] * array7[2];
            array8[2] = array6[0] * array7[1] - array6[1] * array7[0];
            final double n12 = array8[0] * array8[0] + array8[1] * array8[1] + array8[2] * array8[2];
            if (n12 > n11) {
                n11 = n12;
                array[0] = array8[0];
                array[1] = array8[1];
                array[2] = array8[2];
            }
        }
        if (n11 <= 0.0) {
            final int n13 = 0;
            final int n14 = 1;
            final int n15 = 2;
            final double n16 = 0.0;
            array[n15] = n16;
            array[n13] = (array[n14] = n16);
            array[LongAxis(array6)] = 1.0;
        }
    }
    
    static void CheckOrientation(final GLUtessellatorImpl glUtessellatorImpl) {
        final GLUface fHead = glUtessellatorImpl.mesh.fHead;
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        double n = 0.0;
        for (GLUface glUface = fHead.next; glUface != fHead; glUface = glUface.next) {
            GLUhalfEdge glUhalfEdge = glUface.anEdge;
            if (glUhalfEdge.winding > 0) {
                do {
                    n += (glUhalfEdge.Org.s - glUhalfEdge.Sym.Org.s) * (glUhalfEdge.Org.t + glUhalfEdge.Sym.Org.t);
                    glUhalfEdge = glUhalfEdge.Lnext;
                } while (glUhalfEdge != glUface.anEdge);
            }
        }
        if (n < 0.0) {
            for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
                glUvertex.t = -glUvertex.t;
            }
            glUtessellatorImpl.tUnit[0] = -glUtessellatorImpl.tUnit[0];
            glUtessellatorImpl.tUnit[1] = -glUtessellatorImpl.tUnit[1];
            glUtessellatorImpl.tUnit[2] = -glUtessellatorImpl.tUnit[2];
        }
    }
    
    public static void __gl_projectPolygon(final GLUtessellatorImpl glUtessellatorImpl) {
        final GLUvertex vHead = glUtessellatorImpl.mesh.vHead;
        final double[] array = new double[3];
        boolean b = false;
        array[0] = glUtessellatorImpl.normal[0];
        array[1] = glUtessellatorImpl.normal[1];
        array[2] = glUtessellatorImpl.normal[2];
        if (array[0] == 0.0 && array[1] == 0.0 && array[2] == 0.0) {
            ComputeNormal(glUtessellatorImpl, array);
            b = true;
        }
        final double[] sUnit = glUtessellatorImpl.sUnit;
        final double[] tUnit = glUtessellatorImpl.tUnit;
        final int longAxis = LongAxis(array);
        sUnit[longAxis] = 0.0;
        sUnit[(longAxis + 1) % 3] = Normal.S_UNIT_X;
        sUnit[(longAxis + 2) % 3] = Normal.S_UNIT_Y;
        tUnit[longAxis] = 0.0;
        tUnit[(longAxis + 1) % 3] = ((array[longAxis] > 0.0) ? (-Normal.S_UNIT_Y) : Normal.S_UNIT_Y);
        tUnit[(longAxis + 2) % 3] = ((array[longAxis] > 0.0) ? Normal.S_UNIT_X : (-Normal.S_UNIT_X));
        for (GLUvertex glUvertex = vHead.next; glUvertex != vHead; glUvertex = glUvertex.next) {
            glUvertex.s = Dot(glUvertex.coords, sUnit);
            glUvertex.t = Dot(glUvertex.coords, tUnit);
        }
        if (b) {
            CheckOrientation(glUtessellatorImpl);
        }
    }
    
    static {
        Normal.SLANTED_SWEEP = false;
        if (Normal.SLANTED_SWEEP) {
            Normal.S_UNIT_X = 0.5094153956495538;
            Normal.S_UNIT_Y = 0.8605207462201063;
        }
        else {
            Normal.S_UNIT_X = 1.0;
            Normal.S_UNIT_Y = 0.0;
        }
    }
}
