// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.curve.tess;

import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.graph.geom.Triangle;

public class CDTriangulator2DExpAddOn
{
    private final float[] tempV3a;
    private final float[] tempV3b;
    
    public CDTriangulator2DExpAddOn() {
        this.tempV3a = new float[3];
        this.tempV3b = new float[3];
    }
    
    protected final void markLineInTriangle(final Triangle triangle, final float[] array) {
        if (!triangle.isOnCurve() || !triangle.isLine()) {
            return;
        }
        final boolean[] verticesBoundary = triangle.getVerticesBoundary();
        final Vertex[] vertices = triangle.getVertices();
        final Vertex vertex = vertices[0];
        final Vertex vertex2 = vertices[1];
        final Vertex vertex3 = vertices[2];
        int n = 0;
        boolean b;
        if (vertex.isOnCurve() && VectorUtil.isVec2Zero(vertex.getTexCoord(), 0) && !verticesBoundary[0]) {
            b = true;
            ++n;
        }
        else {
            b = false;
        }
        boolean b2;
        if (vertex2.isOnCurve() && VectorUtil.isVec2Zero(vertex2.getTexCoord(), 0) && !verticesBoundary[1]) {
            b2 = true;
            ++n;
        }
        else {
            b2 = false;
        }
        boolean b3;
        if (vertex3.isOnCurve() && VectorUtil.isVec2Zero(vertex3.getTexCoord(), 0) && !verticesBoundary[2]) {
            b3 = true;
            ++n;
        }
        else {
            b3 = false;
        }
        if (2 > n) {
            return;
        }
        if (CDTriangulator2D.DEBUG) {
            System.err.println("CDTri.markLine.1: " + triangle);
            System.err.println("CDTri.markLine.1: count " + n + ", v0IsLS " + b + ", v1IsLS " + b2 + ", v2IsLS " + b3);
        }
        if (b) {
            vertex.setTexCoord(0.0f, 0.0f, 2.0f);
        }
        if (b2) {
            vertex2.setTexCoord(0.0f, 0.0f, 2.0f);
        }
        if (b3) {
            vertex3.setTexCoord(0.0f, 0.0f, 2.0f);
        }
    }
    
    protected final float[] processLineAA(final int n, final Triangle triangle, final Triangle triangle2, final float[] array) {
        if (CDTriangulator2D.DEBUG) {
            System.err.println("CDTri.genP2[" + n + "].1: ? t1 " + triangle);
            System.err.println("CDTri.genP2[" + n + "].1: ? t2 " + triangle2);
        }
        final float[] processLineAAImpl = this.processLineAAImpl(triangle, triangle2, array);
        if (CDTriangulator2D.DEBUG) {
            if (null != processLineAAImpl) {
                System.err.println("CDTri.genP2[" + n + "].1: RECT [" + processLineAAImpl[0] + ", " + processLineAAImpl[1] + "]");
                System.err.println("CDTri.genP2[" + n + "].1: RECT t1 " + triangle);
                System.err.println("CDTri.genP2[" + n + "].1: RECT t2 " + triangle2);
            }
            else {
                System.err.println("CDTri.genP2[" + n + "].1: RECT NOPE, t1 " + triangle);
                System.err.println("CDTri.genP2[" + n + "].1: RECT NOPE, t2 " + triangle2);
            }
        }
        return processLineAAImpl;
    }
    
    private final float[] processLineAAImpl(final Triangle triangle, final Triangle triangle2, final float[] array) {
        if (!triangle.isOnCurve() || !triangle2.isOnCurve() || !triangle.isLine() || !triangle2.isLine()) {
            return null;
        }
        int n = 0;
        final int[] array2 = { -1, -1 };
        final int[] array3 = { -1, -1 };
        final Vertex[] vertices = triangle.getVertices();
        final Vertex[] vertices2 = triangle2.getVertices();
        final float[] coord = vertices[0].getCoord();
        if (VectorUtil.isVec3Equal(coord, 0, vertices2[0].getCoord(), 0, 1.1920929E-7f)) {
            array3[n] = (array2[n] = 0);
            ++n;
        }
        else if (VectorUtil.isVec3Equal(coord, 0, vertices2[1].getCoord(), 0, 1.1920929E-7f)) {
            array2[n] = 0;
            array3[n] = 1;
            ++n;
        }
        else if (VectorUtil.isVec3Equal(coord, 0, vertices2[2].getCoord(), 0, 1.1920929E-7f)) {
            array2[n] = 0;
            array3[n] = 2;
            ++n;
        }
        final float[] coord2 = vertices[1].getCoord();
        if (VectorUtil.isVec3Equal(coord2, 0, vertices2[0].getCoord(), 0, 1.1920929E-7f)) {
            array2[n] = 1;
            array3[n] = 0;
            ++n;
        }
        else if (VectorUtil.isVec3Equal(coord2, 0, vertices2[1].getCoord(), 0, 1.1920929E-7f)) {
            array3[n] = (array2[n] = 1);
            ++n;
        }
        else if (VectorUtil.isVec3Equal(coord2, 0, vertices2[2].getCoord(), 0, 1.1920929E-7f)) {
            array2[n] = 1;
            array3[n] = 2;
            ++n;
        }
        int n2;
        if (2 == n) {
            n2 = 3 - (array2[0] + array2[1]);
        }
        else {
            final float[] coord3 = vertices[2].getCoord();
            if (VectorUtil.isVec3Equal(coord3, 0, vertices2[0].getCoord(), 0, 1.1920929E-7f)) {
                array2[n] = 2;
                array3[n] = 0;
                ++n;
            }
            else if (VectorUtil.isVec3Equal(coord3, 0, vertices2[1].getCoord(), 0, 1.1920929E-7f)) {
                array2[n] = 2;
                array3[n] = 1;
                ++n;
            }
            else if (VectorUtil.isVec3Equal(coord3, 0, vertices2[2].getCoord(), 0, 1.1920929E-7f)) {
                array3[n] = (array2[n] = 2);
                ++n;
            }
            if (2 == n) {
                n2 = 3 - (array2[0] + array2[1]);
            }
            else {
                n2 = -1;
            }
        }
        float[] array4;
        if (0 <= n2 && array3[0] != array3[1]) {
            final int n3 = 3 - (array3[0] + array3[1]);
            if (vertices[array2[0]] != vertices2[array3[0]] || vertices[array2[1]] != vertices2[array3[1]]) {
                throw new InternalError("XXX: diff shared verts");
            }
            final Vertex clone = vertices[array2[0]].clone();
            vertices[array2[0]] = clone;
            vertices2[array3[0]] = clone;
            final Vertex clone2 = vertices[array2[1]].clone();
            vertices[array2[1]] = clone2;
            vertices2[array3[1]] = clone2;
            final Vertex clone3 = vertices[n2].clone();
            vertices[n2] = clone3;
            final Vertex clone4 = vertices2[n3].clone();
            vertices2[n3] = clone4;
            final float[] coord4 = clone3.getCoord();
            final float distVec3 = VectorUtil.distVec3(coord4, clone.getCoord());
            final float distVec4 = VectorUtil.distVec3(coord4, clone2.getCoord());
            float n4;
            Vertex vertex;
            Vertex vertex2;
            Vertex vertex3;
            Vertex vertex4;
            if (distVec3 < distVec4) {
                n4 = distVec3;
                array[0] = distVec3;
                array[1] = distVec4;
                vertex = clone3;
                vertex2 = clone2;
                vertex3 = clone4;
                vertex4 = clone;
            }
            else {
                n4 = distVec4;
                array[0] = distVec4;
                array[1] = distVec3;
                vertex = clone4;
                vertex2 = clone2;
                vertex3 = clone3;
                vertex4 = clone;
            }
            final float n5 = n4 / 3.0f;
            final float n6 = (n4 + n5) / 2.0f;
            vertex.setTexCoord(n4, n6, 2.0f);
            vertex2.setTexCoord(n4, n6, 2.0f);
            vertex3.setTexCoord(n4, -n6, 2.0f);
            vertex4.setTexCoord(n4, -n6, 2.0f);
            if (CDTriangulator2D.DEBUG) {
                System.err.println("RECT.0 : lineWidth: " + n4 + ", dim " + distVec3 + " x " + distVec4 + ", radius " + n5);
                System.err.println("RECT Left.0: " + vertex + ", " + vertex2);
                System.err.println("RECT Right.0: " + vertex3 + ", " + vertex4);
            }
            array4 = array;
        }
        else {
            array4 = null;
        }
        return array4;
    }
}
