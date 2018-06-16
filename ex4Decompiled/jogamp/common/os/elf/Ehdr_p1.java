// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import java.nio.ByteBuffer;
import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.MachineDataInfo;
import com.jogamp.common.nio.StructAccessor;

public class Ehdr_p1
{
    StructAccessor accessor;
    private static final int mdIdx = 0;
    private final MachineDataInfo md;
    private static final int[] Ehdr_p1_size;
    private static final int[] e_ident_offset;
    private static final int[] e_ident_size;
    private static final int[] e_type_offset;
    private static final int[] e_machine_offset;
    private static final int[] e_version_offset;
    
    public static int size() {
        return Ehdr_p1.Ehdr_p1_size[0];
    }
    
    public static Ehdr_p1 create() {
        return create(Buffers.newDirectByteBuffer(size()));
    }
    
    public static Ehdr_p1 create(final ByteBuffer byteBuffer) {
        return new Ehdr_p1(byteBuffer);
    }
    
    Ehdr_p1(final ByteBuffer byteBuffer) {
        this.md = MachineDataInfo.StaticConfig.values()[0].md;
        this.accessor = new StructAccessor(byteBuffer);
    }
    
    public ByteBuffer getBuffer() {
        return this.accessor.getBuffer();
    }
    
    public static final int getE_identArrayLength() {
        return 16;
    }
    
    public Ehdr_p1 setE_ident(final int n, final byte[] array) {
        if (n + array.length > 16) {
            throw new IndexOutOfBoundsException("offset " + n + " + val.length " + array.length + " > array-length " + 16);
        }
        final ByteBuffer buffer = this.getBuffer();
        if (16 > Ehdr_p1.e_ident_size[0]) {
            throw new IndexOutOfBoundsException("bTotal 16 > size " + Ehdr_p1.e_ident_size[0] + ", elemSize " + 1 + " * " + 16);
        }
        final int n2 = Ehdr_p1.e_ident_offset[0];
        final int n3 = n2 + 16;
        if (n3 > buffer.limit()) {
            throw new IndexOutOfBoundsException("bLimes " + n3 + " > buffer.limit " + buffer.limit() + ", elemOff " + n2 + ", elemSize " + 1 + " * " + 16);
        }
        this.accessor.setBytesAt(n2 + 1 * n, array);
        return this;
    }
    
    public ByteBuffer getE_ident() {
        return this.accessor.slice(Ehdr_p1.e_ident_offset[0], 16);
    }
    
    public byte[] getE_ident(final int n, final byte[] array) {
        if (n + array.length > 16) {
            throw new IndexOutOfBoundsException("offset " + n + " + result.length " + array.length + " > array-length " + 16);
        }
        return this.accessor.getBytesAt(Ehdr_p1.e_ident_offset[0] + 1 * n, array);
    }
    
    public Ehdr_p1 setE_type(final short n) {
        this.accessor.setShortAt(Ehdr_p1.e_type_offset[0], n);
        return this;
    }
    
    public short getE_type() {
        return this.accessor.getShortAt(Ehdr_p1.e_type_offset[0]);
    }
    
    public Ehdr_p1 setE_machine(final short n) {
        this.accessor.setShortAt(Ehdr_p1.e_machine_offset[0], n);
        return this;
    }
    
    public short getE_machine() {
        return this.accessor.getShortAt(Ehdr_p1.e_machine_offset[0]);
    }
    
    public Ehdr_p1 setE_version(final int n) {
        this.accessor.setIntAt(Ehdr_p1.e_version_offset[0], n);
        return this;
    }
    
    public int getE_version() {
        return this.accessor.getIntAt(Ehdr_p1.e_version_offset[0]);
    }
    
    static {
        Ehdr_p1_size = new int[] { 24, 24, 24, 24, 24, 24, 24, 24 };
        e_ident_offset = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        e_ident_size = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        e_type_offset = new int[] { 16, 16, 16, 16, 16, 16, 16, 16 };
        e_machine_offset = new int[] { 18, 18, 18, 18, 18, 18, 18, 18 };
        e_version_offset = new int[] { 20, 20, 20, 20, 20, 20, 20, 20 };
    }
}
