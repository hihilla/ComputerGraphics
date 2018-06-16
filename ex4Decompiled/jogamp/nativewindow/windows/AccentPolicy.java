// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.windows;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class AccentPolicy
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] AccentPolicy_size;
    private static final int[] AccentState_offset;
    private static final int[] AccentFlags_offset;
    private static final int[] GradientColor_offset;
    private static final int[] AnimationId_offset;
    
    public static int size() {
        return AccentPolicy.AccentPolicy_size[AccentPolicy.mdIdx];
    }
    
    public static AccentPolicy create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static AccentPolicy create(final ByteBuffer byteBuffer) {
        return new AccentPolicy(byteBuffer);
    }
    
    AccentPolicy(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[AccentPolicy.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public AccentPolicy setAccentState(final int n) {
        this.accessor.setIntAt(AccentPolicy.AccentState_offset[AccentPolicy.mdIdx], n);
        return this;
    }
    
    public int getAccentState() {
        return this.accessor.getIntAt(AccentPolicy.AccentState_offset[AccentPolicy.mdIdx]);
    }
    
    public AccentPolicy setAccentFlags(final int n) {
        this.accessor.setIntAt(AccentPolicy.AccentFlags_offset[AccentPolicy.mdIdx], n);
        return this;
    }
    
    public int getAccentFlags() {
        return this.accessor.getIntAt(AccentPolicy.AccentFlags_offset[AccentPolicy.mdIdx]);
    }
    
    public AccentPolicy setGradientColor(final int n) {
        this.accessor.setIntAt(AccentPolicy.GradientColor_offset[AccentPolicy.mdIdx], n);
        return this;
    }
    
    public int getGradientColor() {
        return this.accessor.getIntAt(AccentPolicy.GradientColor_offset[AccentPolicy.mdIdx]);
    }
    
    public AccentPolicy setAnimationId(final int n) {
        this.accessor.setIntAt(AccentPolicy.AnimationId_offset[AccentPolicy.mdIdx], n);
        return this;
    }
    
    public int getAnimationId() {
        return this.accessor.getIntAt(AccentPolicy.AnimationId_offset[AccentPolicy.mdIdx]);
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        AccentPolicy_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        AccentState_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        AccentFlags_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        GradientColor_offset = new int[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        AnimationId_offset = new int[] { 12, 12, 12, 12, 12, 12, 12, 12 };
    }
}
