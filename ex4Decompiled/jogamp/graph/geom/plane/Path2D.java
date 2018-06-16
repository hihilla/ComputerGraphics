// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.geom.plane;

import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.graph.geom.SVertex;
import java.util.NoSuchElementException;

public final class Path2D implements Cloneable
{
    public static final int WIND_EVEN_ODD = 0;
    public static final int WIND_NON_ZERO = 1;
    static final String invalidWindingRuleValue = "Invalid winding rule value";
    static final String iteratorOutOfBounds = "Iterator out of bounds";
    private static final int BUFFER_SIZE = 10;
    private static final int BUFFER_CAPACITY = 10;
    byte[] types;
    float[] points;
    int typeSize;
    int pointSize;
    int rule;
    static int[] pointShift;
    
    public Path2D() {
        this(1, 10);
    }
    
    public Path2D(final int n) {
        this(n, 10);
    }
    
    public Path2D(final int windingRule, final int n) {
        this.setWindingRule(windingRule);
        this.types = new byte[n];
        this.points = new float[n * 2];
    }
    
    public Path2D(final Path2D path2D) {
        this(1, 10);
        final PathIterator iterator = path2D.iterator(null);
        this.setWindingRule(iterator.getWindingRule());
        this.append(iterator, false);
    }
    
    public void setWindingRule(final int rule) {
        if (rule != 0 && rule != 1) {
            throw new NoSuchElementException("Invalid winding rule value");
        }
        this.rule = rule;
    }
    
    public int getWindingRule() {
        return this.rule;
    }
    
    void checkBuf(final int n, final boolean b) {
        if (b && this.typeSize == 0) {
            throw new IllegalPathStateException("First segment should be SEG_MOVETO type");
        }
        if (this.typeSize == this.types.length) {
            final byte[] types = new byte[this.typeSize + 10];
            System.arraycopy(this.types, 0, types, 0, this.typeSize);
            this.types = types;
        }
        if (this.pointSize + n > this.points.length) {
            final float[] points = new float[this.pointSize + Math.max(20, n)];
            System.arraycopy(this.points, 0, points, 0, this.pointSize);
            this.points = points;
        }
    }
    
    public void moveTo(final float n, final float n2) {
        if (this.typeSize > 0 && this.types[this.typeSize - 1] == 0) {
            this.points[this.pointSize - 2] = n;
            this.points[this.pointSize - 1] = n2;
        }
        else {
            this.checkBuf(2, false);
            this.types[this.typeSize++] = 0;
            this.points[this.pointSize++] = n;
            this.points[this.pointSize++] = n2;
        }
    }
    
    public void lineTo(final float n, final float n2) {
        this.checkBuf(2, true);
        this.types[this.typeSize++] = 1;
        this.points[this.pointSize++] = n;
        this.points[this.pointSize++] = n2;
    }
    
    public void quadTo(final float n, final float n2, final float n3, final float n4) {
        this.checkBuf(4, true);
        this.types[this.typeSize++] = 2;
        this.points[this.pointSize++] = n;
        this.points[this.pointSize++] = n2;
        this.points[this.pointSize++] = n3;
        this.points[this.pointSize++] = n4;
    }
    
    public void curveTo(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.checkBuf(6, true);
        this.types[this.typeSize++] = 3;
        this.points[this.pointSize++] = n;
        this.points[this.pointSize++] = n2;
        this.points[this.pointSize++] = n3;
        this.points[this.pointSize++] = n4;
        this.points[this.pointSize++] = n5;
        this.points[this.pointSize++] = n6;
    }
    
    public final int size() {
        return this.typeSize;
    }
    
    public final boolean isClosed() {
        return this.typeSize > 0 && this.types[this.typeSize - 1] == 4;
    }
    
    public void closePath() {
        if (!this.isClosed()) {
            this.checkBuf(0, true);
            this.types[this.typeSize++] = 4;
        }
    }
    
    @Override
    public String toString() {
        return "[size " + this.size() + ", closed " + this.isClosed() + "]";
    }
    
    public void append(final Path2D path2D, final boolean b) {
        this.append(path2D.iterator(null), b);
    }
    
    public void append(final PathIterator pathIterator, boolean b) {
        while (!pathIterator.isDone()) {
            final float[] array = new float[6];
            final int currentSegment = pathIterator.currentSegment(array);
            switch (currentSegment) {
                case 0: {
                    if (!b || this.typeSize == 0) {
                        this.moveTo(array[0], array[1]);
                        break;
                    }
                    if (this.types[this.typeSize - 1] != 4 && this.points[this.pointSize - 2] == array[0] && this.points[this.pointSize - 1] == array[1]) {
                        break;
                    }
                }
                case 1: {
                    this.lineTo(array[0], array[1]);
                    break;
                }
                case 2: {
                    this.quadTo(array[0], array[1], array[2], array[3]);
                    break;
                }
                case 3: {
                    this.curveTo(array[0], array[1], array[2], array[3], array[4], array[5]);
                    break;
                }
                case 4: {
                    this.closePath();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unhandled Segment Type: " + currentSegment);
                }
            }
            pathIterator.next();
            b = false;
        }
    }
    
    public SVertex getCurrentPoint() {
        if (this.typeSize == 0) {
            return null;
        }
        int n = this.pointSize - 2;
        if (this.types[this.typeSize - 1] == 4) {
            for (int i = this.typeSize - 2; i > 0; --i) {
                final byte b = this.types[i];
                if (b == 0) {
                    break;
                }
                n -= Path2D.pointShift[b];
            }
        }
        return new SVertex(this.points[n], this.points[n + 1], 0.0f, true);
    }
    
    public void reset() {
        this.typeSize = 0;
        this.pointSize = 0;
    }
    
    public void transform(final AffineTransform affineTransform) {
        affineTransform.transform(this.points, 0, this.points, 0, this.pointSize / 2);
    }
    
    public Path2D createTransformedShape(final AffineTransform affineTransform) {
        final Path2D path2D = (Path2D)this.clone();
        if (affineTransform != null) {
            path2D.transform(affineTransform);
        }
        return path2D;
    }
    
    public final synchronized AABBox getBounds2D() {
        float n4;
        float n3;
        float n2;
        float n;
        if (this.pointSize == 0) {
            n = (n2 = (n3 = (n4 = 0.0f)));
        }
        else {
            int i = this.pointSize - 1;
            n4 = (n = this.points[i--]);
            n3 = (n2 = this.points[i--]);
            while (i > 0) {
                final float n5 = this.points[i--];
                final float n6 = this.points[i--];
                if (n6 < n2) {
                    n2 = n6;
                }
                else if (n6 > n3) {
                    n3 = n6;
                }
                if (n5 < n) {
                    n = n5;
                }
                else {
                    if (n5 <= n4) {
                        continue;
                    }
                    n4 = n5;
                }
            }
        }
        return new AABBox(n2, n, 0.0f, n3, n4, 0.0f);
    }
    
    boolean isInside(final int n) {
        if (this.rule == 1) {
            return Crossing.isInsideNonZero(n);
        }
        return Crossing.isInsideEvenOdd(n);
    }
    
    public boolean contains(final float n, final float n2) {
        return this.isInside(Crossing.crossShape(this, n, n2));
    }
    
    public boolean contains(final float n, final float n2, final float n3, final float n4) {
        final int intersectShape = Crossing.intersectShape(this, n, n2, n3, n4);
        return intersectShape != 255 && this.isInside(intersectShape);
    }
    
    public boolean intersects(final float n, final float n2, final float n3, final float n4) {
        final int intersectShape = Crossing.intersectShape(this, n, n2, n3, n4);
        return intersectShape == 255 || this.isInside(intersectShape);
    }
    
    public boolean contains(final Vertex vertex) {
        return this.contains(vertex.getX(), vertex.getY());
    }
    
    public boolean contains(final AABBox aabBox) {
        return this.contains(aabBox.getMinX(), aabBox.getMinY(), aabBox.getWidth(), aabBox.getHeight());
    }
    
    public boolean intersects(final AABBox aabBox) {
        return this.intersects(aabBox.getMinX(), aabBox.getMinY(), aabBox.getWidth(), aabBox.getHeight());
    }
    
    public PathIterator iterator() {
        return new Iterator(this);
    }
    
    public PathIterator iterator(final AffineTransform affineTransform) {
        return new Iterator(this, affineTransform);
    }
    
    public Object clone() {
        try {
            final Path2D path2D = (Path2D)super.clone();
            path2D.types = this.types.clone();
            path2D.points = this.points.clone();
            return path2D;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }
    
    static {
        Path2D.pointShift = new int[] { 2, 2, 4, 6, 0 };
    }
    
    static class Iterator implements PathIterator
    {
        int typeIndex;
        int pointIndex;
        Path2D p;
        AffineTransform t;
        
        Iterator(final Path2D path2D) {
            this(path2D, null);
        }
        
        Iterator(final Path2D p2, final AffineTransform t) {
            this.p = p2;
            this.t = t;
        }
        
        @Override
        public int getWindingRule() {
            return this.p.getWindingRule();
        }
        
        @Override
        public boolean isDone() {
            return this.typeIndex >= this.p.typeSize;
        }
        
        @Override
        public void next() {
            ++this.typeIndex;
        }
        
        @Override
        public int currentSegment(final float[] array) {
            if (this.isDone()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            final byte b = this.p.types[this.typeIndex];
            final int n = Path2D.pointShift[b];
            System.arraycopy(this.p.points, this.pointIndex, array, 0, n);
            if (this.t != null) {
                this.t.transform(array, 0, array, 0, n / 2);
            }
            this.pointIndex += n;
            return b;
        }
    }
}
