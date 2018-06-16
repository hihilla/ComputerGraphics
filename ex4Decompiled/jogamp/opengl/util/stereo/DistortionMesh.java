// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.stereo;

import com.jogamp.opengl.util.stereo.EyeParameter;
import com.jogamp.opengl.util.stereo.generic.GenericStereoDeviceConfig;

public class DistortionMesh
{
    final DistortionVertex[] vertices;
    final int vertexCount;
    final short[] indices;
    final int indexCount;
    
    public DistortionMesh(final DistortionVertex[] vertices, final int vertexCount, final short[] indices, final int indexCount) {
        this.vertices = vertices;
        this.vertexCount = vertexCount;
        this.indices = indices;
        this.indexCount = indexCount;
    }
    
    public static class DistortionVertex
    {
        public static final int def_pos_size = 2;
        public static final int def_vignetteFactor_size = 1;
        public static final int def_timewarpFactor_size = 1;
        public static final int def_texR_size = 2;
        public static final int def_texG_size = 2;
        public static final int def_texB_size = 2;
        public static final int def_total_size = 10;
        final float[] data;
        final int pos_size;
        final int vignetteFactor_size;
        final int timewarpFactor_size;
        final int texR_size;
        final int texG_size;
        final int texB_size;
        
        public DistortionVertex(final float[] data, final int pos_size, final int vignetteFactor_size, final int timewarpFactor_size, final int texR_size, final int texG_size, final int texB_size) {
            this.data = data;
            this.pos_size = pos_size;
            this.vignetteFactor_size = vignetteFactor_size;
            this.timewarpFactor_size = timewarpFactor_size;
            this.texR_size = texR_size;
            this.texG_size = texG_size;
            this.texB_size = texB_size;
        }
    }
    
    public interface Producer
    {
        void init(final GenericStereoDeviceConfig p0, final float[] p1) throws IllegalStateException;
        
        DistortionMesh create(final EyeParameter p0, final int p1);
    }
}
