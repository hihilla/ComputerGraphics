// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.geom.plane;

import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.opengl.math.FloatUtil;

public class AffineTransform implements Cloneable
{
    static final String determinantIsZero = "Determinant is zero";
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_MASK_SCALE = 6;
    public static final int TYPE_MASK_ROTATION = 24;
    static final int TYPE_UNKNOWN = -1;
    static final float ZERO = 1.0E-10f;
    float m00;
    float m10;
    float m01;
    float m11;
    float m02;
    float m12;
    transient int type;
    
    public AffineTransform() {
        this.setToIdentity();
    }
    
    public AffineTransform(final AffineTransform affineTransform) {
        this.type = affineTransform.type;
        this.m00 = affineTransform.m00;
        this.m10 = affineTransform.m10;
        this.m01 = affineTransform.m01;
        this.m11 = affineTransform.m11;
        this.m02 = affineTransform.m02;
        this.m12 = affineTransform.m12;
    }
    
    public AffineTransform(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6) {
        this.type = -1;
        this.m00 = m00;
        this.m10 = m2;
        this.m01 = m3;
        this.m11 = m4;
        this.m02 = m5;
        this.m12 = m6;
    }
    
    public AffineTransform(final float[] array) {
        this.type = -1;
        this.m00 = array[0];
        this.m10 = array[1];
        this.m01 = array[2];
        this.m11 = array[3];
        if (array.length > 4) {
            this.m02 = array[4];
            this.m12 = array[5];
        }
    }
    
    public int getType() {
        if (this.type != -1) {
            return this.type;
        }
        int n = 0;
        if (this.m00 * this.m01 + this.m10 * this.m11 != 0.0) {
            return n | 0x20;
        }
        if (this.m02 != 0.0 || this.m12 != 0.0) {
            n |= 0x1;
        }
        else if (this.m00 == 1.0 && this.m11 == 1.0 && this.m01 == 0.0 && this.m10 == 0.0) {
            return 0;
        }
        if (this.m00 * this.m11 - this.m01 * this.m10 < 0.0) {
            n |= 0x40;
        }
        final float n2 = this.m00 * this.m00 + this.m10 * this.m10;
        if (n2 != this.m01 * this.m01 + this.m11 * this.m11) {
            n |= 0x4;
        }
        else if (n2 != 1.0) {
            n |= 0x2;
        }
        if ((this.m00 == 0.0 && this.m11 == 0.0) || (this.m10 == 0.0 && this.m01 == 0.0 && (this.m00 < 0.0 || this.m11 < 0.0))) {
            n |= 0x8;
        }
        else if (this.m01 != 0.0 || this.m10 != 0.0) {
            n |= 0x10;
        }
        return n;
    }
    
    public final float getScaleX() {
        return this.m00;
    }
    
    public final float getScaleY() {
        return this.m11;
    }
    
    public final float getShearX() {
        return this.m01;
    }
    
    public final float getShearY() {
        return this.m10;
    }
    
    public final float getTranslateX() {
        return this.m02;
    }
    
    public final float getTranslateY() {
        return this.m12;
    }
    
    public final boolean isIdentity() {
        return this.getType() == 0;
    }
    
    public final void getMatrix(final float[] array) {
        array[0] = this.m00;
        array[1] = this.m10;
        array[2] = this.m01;
        array[3] = this.m11;
        if (array.length > 4) {
            array[4] = this.m02;
            array[5] = this.m12;
        }
    }
    
    public final float getDeterminant() {
        return this.m00 * this.m11 - this.m01 * this.m10;
    }
    
    public final AffineTransform setTransform(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6) {
        this.type = -1;
        this.m00 = m00;
        this.m10 = m2;
        this.m01 = m3;
        this.m11 = m4;
        this.m02 = m5;
        this.m12 = m6;
        return this;
    }
    
    public final AffineTransform setTransform(final AffineTransform affineTransform) {
        this.type = affineTransform.type;
        this.setTransform(affineTransform.m00, affineTransform.m10, affineTransform.m01, affineTransform.m11, affineTransform.m02, affineTransform.m12);
        return this;
    }
    
    public final AffineTransform setToIdentity() {
        this.type = 0;
        final float n = 1.0f;
        this.m11 = n;
        this.m00 = n;
        final float n2 = 0.0f;
        this.m12 = n2;
        this.m02 = n2;
        this.m01 = n2;
        this.m10 = n2;
        return this;
    }
    
    public final AffineTransform setToTranslation(final float m02, final float m3) {
        final float n = 1.0f;
        this.m11 = n;
        this.m00 = n;
        final float n2 = 0.0f;
        this.m10 = n2;
        this.m01 = n2;
        this.m02 = m02;
        this.m12 = m3;
        if (m02 == 0.0f && m3 == 0.0f) {
            this.type = 0;
        }
        else {
            this.type = 1;
        }
        return this;
    }
    
    public final AffineTransform setToScale(final float m00, final float m2) {
        this.m00 = m00;
        this.m11 = m2;
        final float n = 0.0f;
        this.m12 = n;
        this.m02 = n;
        this.m01 = n;
        this.m10 = n;
        if (m00 != 1.0f || m2 != 1.0f) {
            this.type = -1;
        }
        else {
            this.type = 0;
        }
        return this;
    }
    
    public final AffineTransform setToShear(final float m01, final float m2) {
        final float n = 1.0f;
        this.m11 = n;
        this.m00 = n;
        final float n2 = 0.0f;
        this.m12 = n2;
        this.m02 = n2;
        this.m01 = m01;
        this.m10 = m2;
        if (m01 != 0.0f || m2 != 0.0f) {
            this.type = -1;
        }
        else {
            this.type = 0;
        }
        return this;
    }
    
    public final AffineTransform setToRotation(final float n) {
        float sin = FloatUtil.sin(n);
        float cos = FloatUtil.cos(n);
        if (FloatUtil.abs(cos) < 1.0E-10f) {
            cos = 0.0f;
            sin = ((sin > 0.0f) ? 1.0f : -1.0f);
        }
        else if (FloatUtil.abs(sin) < 1.0E-10f) {
            sin = 0.0f;
            cos = ((cos > 0.0f) ? 1.0f : -1.0f);
        }
        final float n2 = cos;
        this.m11 = n2;
        this.m00 = n2;
        this.m01 = -sin;
        this.m10 = sin;
        final float n3 = 0.0f;
        this.m12 = n3;
        this.m02 = n3;
        this.type = -1;
        return this;
    }
    
    public final AffineTransform setToRotation(final float toRotation, final float n, final float n2) {
        this.setToRotation(toRotation);
        this.m02 = n * (1.0f - this.m00) + n2 * this.m10;
        this.m12 = n2 * (1.0f - this.m00) - n * this.m10;
        this.type = -1;
        return this;
    }
    
    public final AffineTransform translate(final float n, final float n2, final AffineTransform affineTransform) {
        return this.concatenate(affineTransform.setToTranslation(n, n2));
    }
    
    public final AffineTransform scale(final float n, final float n2, final AffineTransform affineTransform) {
        return this.concatenate(affineTransform.setToScale(n, n2));
    }
    
    public final AffineTransform shear(final float n, final float n2, final AffineTransform affineTransform) {
        return this.concatenate(affineTransform.setToShear(n, n2));
    }
    
    public final AffineTransform rotate(final float toRotation, final AffineTransform affineTransform) {
        return this.concatenate(affineTransform.setToRotation(toRotation));
    }
    
    public final AffineTransform rotate(final float n, final float n2, final float n3, final AffineTransform affineTransform) {
        return this.concatenate(affineTransform.setToRotation(n, n2, n3));
    }
    
    public static final AffineTransform multiply(final AffineTransform affineTransform, final AffineTransform affineTransform2) {
        return new AffineTransform(affineTransform2.m00 * affineTransform.m00 + affineTransform2.m10 * affineTransform.m01, affineTransform2.m00 * affineTransform.m10 + affineTransform2.m10 * affineTransform.m11, affineTransform2.m01 * affineTransform.m00 + affineTransform2.m11 * affineTransform.m01, affineTransform2.m01 * affineTransform.m10 + affineTransform2.m11 * affineTransform.m11, affineTransform2.m02 * affineTransform.m00 + affineTransform2.m12 * affineTransform.m01 + affineTransform.m02, affineTransform2.m02 * affineTransform.m10 + affineTransform2.m12 * affineTransform.m11 + affineTransform.m12);
    }
    
    public final AffineTransform concatenate(final AffineTransform affineTransform) {
        this.type = -1;
        this.setTransform(affineTransform.m00 * this.m00 + affineTransform.m10 * this.m01, affineTransform.m00 * this.m10 + affineTransform.m10 * this.m11, affineTransform.m01 * this.m00 + affineTransform.m11 * this.m01, affineTransform.m01 * this.m10 + affineTransform.m11 * this.m11, affineTransform.m02 * this.m00 + affineTransform.m12 * this.m01 + this.m02, affineTransform.m02 * this.m10 + affineTransform.m12 * this.m11 + this.m12);
        return this;
    }
    
    public final AffineTransform preConcatenate(final AffineTransform affineTransform) {
        this.type = -1;
        this.setTransform(this.m00 * affineTransform.m00 + this.m10 * affineTransform.m01, this.m00 * affineTransform.m10 + this.m10 * affineTransform.m11, this.m01 * affineTransform.m00 + this.m11 * affineTransform.m01, this.m01 * affineTransform.m10 + this.m11 * affineTransform.m11, this.m02 * affineTransform.m00 + this.m12 * affineTransform.m01 + affineTransform.m02, this.m02 * affineTransform.m10 + this.m12 * affineTransform.m11 + affineTransform.m12);
        return this;
    }
    
    public final AffineTransform createInverse() throws NoninvertibleTransformException {
        final float determinant = this.getDeterminant();
        if (FloatUtil.abs(determinant) < 1.0E-10f) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        return new AffineTransform(this.m11 / determinant, -this.m10 / determinant, -this.m01 / determinant, this.m00 / determinant, (this.m01 * this.m12 - this.m11 * this.m02) / determinant, (this.m10 * this.m02 - this.m00 * this.m12) / determinant);
    }
    
    public final AABBox transform(final AABBox aabBox, final AABBox aabBox2) {
        final float[] low = aabBox.getLow();
        final float[] high = aabBox.getHigh();
        aabBox2.setSize(low[0] * this.m00 + low[1] * this.m01 + this.m02, low[0] * this.m10 + low[1] * this.m11 + this.m12, low[2], high[0] * this.m00 + high[1] * this.m01 + this.m02, high[0] * this.m10 + high[1] * this.m11 + this.m12, high[2]);
        return aabBox2;
    }
    
    public final Vertex transform(final Vertex vertex, final Vertex vertex2) {
        final float x = vertex.getX();
        final float y = vertex.getY();
        vertex2.setCoord(x * this.m00 + y * this.m01 + this.m02, x * this.m10 + y * this.m11 + this.m12, vertex.getZ());
        return vertex2;
    }
    
    public final void transform(final Vertex[] array, int n, final Vertex[] array2, int n2, int n3) {
        while (--n3 >= 0) {
            final Vertex vertex = array[n++];
            final Vertex vertex2 = array2[n2];
            if (vertex2 == null) {
                throw new IllegalArgumentException("dst[" + n2 + "] is null");
            }
            final float x = vertex.getX();
            final float y = vertex.getY();
            vertex2.setCoord(x * this.m00 + y * this.m01 + this.m02, x * this.m10 + y * this.m11 + this.m12, vertex.getZ());
            array2[n2++] = vertex2;
        }
    }
    
    public final float[] transform(final float[] array, final float[] array2) {
        final float n = array[0];
        final float n2 = array[1];
        array2[0] = n * this.m00 + n2 * this.m01 + this.m02;
        array2[1] = n * this.m10 + n2 * this.m11 + this.m12;
        return array2;
    }
    
    public final void transform(final float[] array, final int n, final float[] array2, final int n2) {
        final float n3 = array[n + 0];
        final float n4 = array[n + 1];
        array2[n2 + 0] = n3 * this.m00 + n4 * this.m01 + this.m02;
        array2[n2 + 1] = n3 * this.m10 + n4 * this.m11 + this.m12;
    }
    
    public final void transform(final float[] array, int n, final float[] array2, int n2, int n3) {
        int n4 = 2;
        if (array == array2 && n < n2 && n2 < n + n3 * 2) {
            n = n + n3 * 2 - 2;
            n2 = n2 + n3 * 2 - 2;
            n4 = -2;
        }
        while (--n3 >= 0) {
            final float n5 = array[n + 0];
            final float n6 = array[n + 1];
            array2[n2 + 0] = n5 * this.m00 + n6 * this.m01 + this.m02;
            array2[n2 + 1] = n5 * this.m10 + n6 * this.m11 + this.m12;
            n += n4;
            n2 += n4;
        }
    }
    
    public final Vertex deltaTransform(final Vertex vertex, final Vertex vertex2) {
        final float x = vertex.getX();
        final float y = vertex.getY();
        vertex2.setCoord(x * this.m00 + y * this.m01, x * this.m10 + y * this.m11, vertex.getZ());
        return vertex2;
    }
    
    public final void deltaTransform(final float[] array, int n, final float[] array2, int n2, int n3) {
        while (--n3 >= 0) {
            final float n4 = array[n++];
            final float n5 = array[n++];
            array2[n2++] = n4 * this.m00 + n5 * this.m01;
            array2[n2++] = n4 * this.m10 + n5 * this.m11;
        }
    }
    
    public final Vertex inverseTransform(final Vertex vertex, final Vertex vertex2) throws NoninvertibleTransformException {
        final float determinant = this.getDeterminant();
        if (FloatUtil.abs(determinant) < 1.0E-10f) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        final float n = vertex.getX() - this.m02;
        final float n2 = vertex.getY() - this.m12;
        vertex2.setCoord((n * this.m11 - n2 * this.m01) / determinant, (n2 * this.m00 - n * this.m10) / determinant, vertex.getZ());
        return vertex2;
    }
    
    public final void inverseTransform(final float[] array, int n, final float[] array2, int n2, int n3) throws NoninvertibleTransformException {
        final float determinant = this.getDeterminant();
        if (FloatUtil.abs(determinant) < 1.0E-10f) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        while (--n3 >= 0) {
            final float n4 = array[n++] - this.m02;
            final float n5 = array[n++] - this.m12;
            array2[n2++] = (n4 * this.m11 - n5 * this.m01) / determinant;
            array2[n2++] = (n5 * this.m00 - n4 * this.m10) / determinant;
        }
    }
    
    public final Path2D createTransformedShape(final Path2D path2D) {
        if (path2D == null) {
            return null;
        }
        return path2D.createTransformedShape(this);
    }
    
    @Override
    public final String toString() {
        return this.getClass().getName() + "[[" + this.m00 + ", " + this.m01 + ", " + this.m02 + "], [" + this.m10 + ", " + this.m11 + ", " + this.m12 + "]]";
    }
    
    public final AffineTransform clone() {
        try {
            return (AffineTransform)super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AffineTransform) {
            final AffineTransform affineTransform = (AffineTransform)o;
            return this.m00 == affineTransform.m00 && this.m01 == affineTransform.m01 && this.m02 == affineTransform.m02 && this.m10 == affineTransform.m10 && this.m11 == affineTransform.m11 && this.m12 == affineTransform.m12;
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        throw new InternalError("hashCode not designed");
    }
}
