// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.windows.wgl;

import jogamp.common.os.MachineDataInfoRuntime;
import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class PGPU_DEVICE
{
    StructAccessor accessor;
    private static final int mdIdx;
    private final MachineDataInfo md;
    private static final int[] PGPU_DEVICE_size;
    private static final int[] cb_offset;
    private static final int[] DeviceName_offset;
    private static final int[] DeviceName_size;
    private static final int[] DeviceString_offset;
    private static final int[] DeviceString_size;
    private static final int[] Flags_offset;
    private static final int[] rcVirtualScreen_offset;
    private static final int[] rcVirtualScreen_size;
    
    public static int size() {
        return PGPU_DEVICE.PGPU_DEVICE_size[PGPU_DEVICE.mdIdx];
    }
    
    public static PGPU_DEVICE create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static PGPU_DEVICE create(final ByteBuffer byteBuffer) {
        return new PGPU_DEVICE(byteBuffer);
    }
    
    PGPU_DEVICE(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[PGPU_DEVICE.mdIdx].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public PGPU_DEVICE setCb(final int n) {
        this.accessor.setIntAt(PGPU_DEVICE.cb_offset[PGPU_DEVICE.mdIdx], n);
        return this;
    }
    
    public int getCb() {
        return this.accessor.getIntAt(PGPU_DEVICE.cb_offset[PGPU_DEVICE.mdIdx]);
    }
    
    public static final int getDeviceNameArrayLength() {
        return 32;
    }
    
    public PGPU_DEVICE setDeviceName(final int n, final byte[] array) {
        if (n + array.length > 32) {
            throw new IndexOutOfBoundsException("offset " + n + " + val.length " + array.length + " > array-length " + 32);
        }
        final ByteBuffer buffer = this.getBuffer();
        if (32 > PGPU_DEVICE.DeviceName_size[PGPU_DEVICE.mdIdx]) {
            throw new IndexOutOfBoundsException("bTotal 32 > size " + PGPU_DEVICE.DeviceName_size[PGPU_DEVICE.mdIdx] + ", elemSize " + 1 + " * " + 32);
        }
        final int n2 = PGPU_DEVICE.DeviceName_offset[PGPU_DEVICE.mdIdx];
        final int n3 = n2 + 32;
        if (n3 > buffer.limit()) {
            throw new IndexOutOfBoundsException("bLimes " + n3 + " > buffer.limit " + buffer.limit() + ", elemOff " + n2 + ", elemSize " + 1 + " * " + 32);
        }
        this.accessor.setBytesAt(n2 + 1 * n, array);
        return this;
    }
    
    public ByteBuffer getDeviceName() {
        return this.accessor.slice(PGPU_DEVICE.DeviceName_offset[PGPU_DEVICE.mdIdx], 32);
    }
    
    public byte[] getDeviceName(final int n, final byte[] array) {
        if (n + array.length > 32) {
            throw new IndexOutOfBoundsException("offset " + n + " + result.length " + array.length + " > array-length " + 32);
        }
        return this.accessor.getBytesAt(PGPU_DEVICE.DeviceName_offset[PGPU_DEVICE.mdIdx] + 1 * n, array);
    }
    
    public static final int getDeviceStringArrayLength() {
        return 128;
    }
    
    public PGPU_DEVICE setDeviceString(final int n, final byte[] array) {
        if (n + array.length > 128) {
            throw new IndexOutOfBoundsException("offset " + n + " + val.length " + array.length + " > array-length " + 128);
        }
        final ByteBuffer buffer = this.getBuffer();
        if (128 > PGPU_DEVICE.DeviceString_size[PGPU_DEVICE.mdIdx]) {
            throw new IndexOutOfBoundsException("bTotal 128 > size " + PGPU_DEVICE.DeviceString_size[PGPU_DEVICE.mdIdx] + ", elemSize " + 1 + " * " + 128);
        }
        final int n2 = PGPU_DEVICE.DeviceString_offset[PGPU_DEVICE.mdIdx];
        final int n3 = n2 + 128;
        if (n3 > buffer.limit()) {
            throw new IndexOutOfBoundsException("bLimes " + n3 + " > buffer.limit " + buffer.limit() + ", elemOff " + n2 + ", elemSize " + 1 + " * " + 128);
        }
        this.accessor.setBytesAt(n2 + 1 * n, array);
        return this;
    }
    
    public ByteBuffer getDeviceString() {
        return this.accessor.slice(PGPU_DEVICE.DeviceString_offset[PGPU_DEVICE.mdIdx], 128);
    }
    
    public byte[] getDeviceString(final int n, final byte[] array) {
        if (n + array.length > 128) {
            throw new IndexOutOfBoundsException("offset " + n + " + result.length " + array.length + " > array-length " + 128);
        }
        return this.accessor.getBytesAt(PGPU_DEVICE.DeviceString_offset[PGPU_DEVICE.mdIdx] + 1 * n, array);
    }
    
    public PGPU_DEVICE setFlags(final int n) {
        this.accessor.setIntAt(PGPU_DEVICE.Flags_offset[PGPU_DEVICE.mdIdx], n);
        return this;
    }
    
    public int getFlags() {
        return this.accessor.getIntAt(PGPU_DEVICE.Flags_offset[PGPU_DEVICE.mdIdx]);
    }
    
    public RECT getRcVirtualScreen() {
        return RECT.create(this.accessor.slice(PGPU_DEVICE.rcVirtualScreen_offset[PGPU_DEVICE.mdIdx], PGPU_DEVICE.rcVirtualScreen_size[PGPU_DEVICE.mdIdx]));
    }
    
    static {
        mdIdx = MachineDataInfoRuntime.getStatic().ordinal();
        PGPU_DEVICE_size = new int[] { 184, 184, 184, 184, 184, 184, 184, 184 };
        cb_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        DeviceName_offset = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
        DeviceName_size = new int[] { 32, 32, 32, 32, 32, 32, 32, 32 };
        DeviceString_offset = new int[] { 36, 36, 36, 36, 36, 36, 36, 36 };
        DeviceString_size = new int[] { 128, 128, 128, 128, 128, 128, 128, 128 };
        Flags_offset = new int[] { 164, 164, 164, 164, 164, 164, 164, 164 };
        rcVirtualScreen_offset = new int[] { 168, 168, 168, 168, 168, 168, 168, 168 };
        rcVirtualScreen_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
    }
}
