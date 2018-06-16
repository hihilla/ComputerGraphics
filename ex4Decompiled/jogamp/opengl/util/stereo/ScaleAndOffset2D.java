// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.stereo;

import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.nativewindow.util.RectangleImmutable;
import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.opengl.math.FovHVHalves;

public final class ScaleAndOffset2D
{
    final float[] scale;
    final float[] offset;
    private static final float[] vec2Half;
    
    @Override
    public String toString() {
        return "[offset " + this.offset[0] + " / " + this.offset[1] + ", scale " + this.scale[0] + " x " + this.scale[1] + "]";
    }
    
    public ScaleAndOffset2D(final float[] scale, final float[] offset) {
        this.scale = scale;
        this.offset = offset;
    }
    
    public ScaleAndOffset2D(final FovHVHalves fovHVHalves) {
        final FovHVHalves tangents = fovHVHalves.toTangents();
        final float n = 2.0f / (tangents.left + tangents.right);
        final float n2 = 2.0f / (tangents.top + tangents.bottom);
        final float n3 = (tangents.left - tangents.right) * n * 0.5f;
        final float n4 = (tangents.top - tangents.bottom) * n2 * 0.5f;
        this.scale = new float[] { n, n2 };
        this.offset = new float[] { n3, n4 };
    }
    
    public ScaleAndOffset2D(final FovHVHalves fovHVHalves, final DimensionImmutable dimensionImmutable, final RectangleImmutable rectangleImmutable) {
        final ScaleAndOffset2D scaleAndOffset2D = new ScaleAndOffset2D(fovHVHalves);
        final float[] array = new float[2];
        final float[] array2 = new float[2];
        final float[] scaleVec2 = VectorUtil.scaleVec2(array, scaleAndOffset2D.scale, 0.5f);
        final float[] addVec2 = VectorUtil.addVec2(array2, VectorUtil.scaleVec2(array2, scaleAndOffset2D.offset, 0.5f), ScaleAndOffset2D.vec2Half);
        final float[] array3 = { rectangleImmutable.getWidth() / dimensionImmutable.getWidth(), rectangleImmutable.getHeight() / dimensionImmutable.getHeight() };
        final float[] array4 = { rectangleImmutable.getX() / dimensionImmutable.getWidth(), rectangleImmutable.getY() / dimensionImmutable.getHeight() };
        VectorUtil.scaleVec2(scaleVec2, scaleVec2, array3);
        VectorUtil.addVec2(addVec2, VectorUtil.scaleVec2(addVec2, addVec2, array3), array4);
        this.scale = scaleVec2;
        this.offset = addVec2;
    }
    
    public final float[] convertToTanFovSpace(final float[] array) {
        final float[] array2 = new float[2];
        return VectorUtil.divVec2(array2, VectorUtil.subVec2(array2, array, this.offset), this.scale);
    }
    
    static {
        vec2Half = new float[] { 0.5f, 0.5f };
    }
}
