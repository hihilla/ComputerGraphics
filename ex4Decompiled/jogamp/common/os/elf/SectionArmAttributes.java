// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.common.os.elf;

import java.util.Collection;
import java.util.ArrayList;
import com.jogamp.common.util.Bitstream;
import java.util.List;

public class SectionArmAttributes extends Section
{
    public static final byte FORMAT_VERSION_A = 65;
    public static final byte ABI_VFP_ARGS_IS_BASE_VARIANT = 0;
    public static final byte ABI_VFP_ARGS_IS_VFP_VARIANT = 1;
    public static final byte ABI_VFP_ARGS_IS_CUSTOM_VARIANT = 2;
    public static final byte ABI_VFP_ARGS_IS_BOTH_BASE_AND_VFP_VARIANT = 3;
    public final List<VendorAttributes> vendorAttributesList;
    
    public static final boolean abiVFPArgsAcceptsVFPVariant(final byte b) {
        return 1 == b || 3 == b;
    }
    
    SectionArmAttributes(final SectionHeader sectionHeader, final byte[] array, final int n, final int n2) throws IndexOutOfBoundsException, IllegalArgumentException {
        super(sectionHeader, array, n, n2);
        this.vendorAttributesList = parse(sectionHeader, array, n, n2);
    }
    
    @Override
    public String toString() {
        return "SectionArmAttributes[" + super.toSubString() + ", " + this.vendorAttributesList.toString() + "]";
    }
    
    public final Attribute get(final Tag tag) {
        for (int i = 0; i < this.vendorAttributesList.size(); ++i) {
            final List<Attribute> attributes = this.vendorAttributesList.get(i).attributes;
            for (int j = 0; j < attributes.size(); ++j) {
                final Attribute attribute = attributes.get(j);
                if (attribute.tag == tag) {
                    return attribute;
                }
            }
        }
        return null;
    }
    
    public final List<Attribute> get(final String s) {
        return get(this.vendorAttributesList, s);
    }
    
    static final List<Attribute> get(final List<VendorAttributes> list, final String s) {
        for (int i = 0; i < list.size(); ++i) {
            final VendorAttributes vendorAttributes = list.get(i);
            if (vendorAttributes.vendor.equals(s)) {
                return vendorAttributes.attributes;
            }
        }
        return null;
    }
    
    static List<VendorAttributes> parse(final SectionHeader sectionHeader, final byte[] array, final int n, final int n2) throws IndexOutOfBoundsException, IllegalArgumentException {
        Bitstream.checkBounds(array, n, n2);
        int i = n;
        if (65 != array[i]) {
            throw new IllegalArgumentException("ShArmAttr: Not version A, but: " + IOUtils.toHexString(array[i]));
        }
        ++i;
        final ArrayList<VendorAttributes> list = new ArrayList<VendorAttributes>();
        final boolean bigEndian = sectionHeader.eh2.eh1.isBigEndian();
        while (i < n2) {
            final int n3 = i;
            final int uInt32 = IOUtils.readUInt32(bigEndian, array, i);
            i += 4;
            final int[] array2 = { 0 };
            final String string = IOUtils.getString(array, i, uInt32 - 4, array2);
            i = array2[0];
            final ArrayList<Attribute> list2 = new ArrayList<Attribute>();
            while (i < uInt32) {
                final int[] array3 = { 0 };
                parseSub(bigEndian, array, i, uInt32 - i, array3, list2);
                i = array3[0];
            }
            if (n3 + uInt32 != i) {
                throw new IllegalArgumentException("ShArmAttr: Section length count mismatch, expected " + (n3 + uInt32) + ", has " + i);
            }
            final List<Attribute> value = get(list, string);
            if (null != value) {
                value.addAll(list2);
            }
            else {
                list.add(new VendorAttributes(string, list2));
            }
        }
        return list;
    }
    
    private static void parseSub(final boolean b, final byte[] array, final int n, final int n2, final int[] array2, final List<Attribute> list) throws IndexOutOfBoundsException, IllegalArgumentException {
        Bitstream.checkBounds(array, n, n2);
        int i = n;
        final byte b2 = array[i++];
        final Tag value = Tag.get(b2);
        if (null == value) {
            throw new IllegalArgumentException("ShArmAttr: Invalid Sub-Section tag (NaT): " + b2);
        }
        switch (value) {
            case File:
            case Section:
            case Symbol: {
                final int uInt32 = IOUtils.readUInt32(b, array, i);
                i += 4;
                if (Tag.File == value) {
                    while (i < n + uInt32) {
                        final byte b3 = array[i++];
                        final Tag value2 = Tag.get(b3);
                        if (null == value2) {
                            throw new IllegalArgumentException("ShArmAttr: Invalid Attribute tag (NaT): " + b3);
                        }
                        switch (value2.type) {
                            case NTBS: {
                                final int[] array3 = { 0 };
                                list.add(new Attribute(value2, IOUtils.getString(array, i, uInt32 + n - i, array3)));
                                i = array3[0];
                                continue;
                            }
                            case ULEB128: {
                                list.add(new Attribute(value2, new Byte(array[i++])));
                                continue;
                            }
                            default: {
                                throw new IllegalArgumentException("ShArmAttr: Invalid Attribute tag: " + value2);
                            }
                        }
                    }
                }
                array2[0] = n + uInt32;
            }
            default: {
                throw new IllegalArgumentException("ShArmAttr: Invalid Sub-Section tag: " + value);
            }
        }
    }
    
    public enum Type
    {
        None, 
        SubSection, 
        NTBS, 
        ULEB128;
    }
    
    public enum Tag
    {
        None(0, Type.None), 
        File(1, Type.SubSection), 
        Section(2, Type.SubSection), 
        Symbol(3, Type.SubSection), 
        CPU_raw_name(4, Type.NTBS), 
        CPU_name(5, Type.NTBS), 
        CPU_arch(6, Type.ULEB128), 
        CPU_arch_profile(7, Type.ULEB128), 
        ARM_ISA_use(8, Type.ULEB128), 
        THUMB_ISA_use(9, Type.ULEB128), 
        FP_arch(10, Type.ULEB128), 
        WMMX_arch(11, Type.ULEB128), 
        Advanced_SIMD_arch(12, Type.ULEB128), 
        PCS_config(13, Type.ULEB128), 
        ABI_PCS_R9_use(14, Type.ULEB128), 
        ABI_PCS_RW_data(15, Type.ULEB128), 
        ABI_PCS_RO_data(16, Type.ULEB128), 
        ABI_PCS_GOT_use(17, Type.ULEB128), 
        ABI_PCS_wchar_t(18, Type.ULEB128), 
        ABI_FP_rounding(19, Type.ULEB128), 
        ABI_FP_denormal(20, Type.ULEB128), 
        ABI_FP_exceptions(21, Type.ULEB128), 
        ABI_FP_user_exceptions(22, Type.ULEB128), 
        ABI_FP_number_model(23, Type.ULEB128), 
        ABI_align_needed(24, Type.ULEB128), 
        ABI_align_preserved(25, Type.ULEB128), 
        ABI_enum_size(26, Type.ULEB128), 
        ABI_HardFP_use(27, Type.ULEB128), 
        ABI_VFP_args(28, Type.ULEB128), 
        ABI_WMMX_args(29, Type.ULEB128), 
        ABI_optimization_goals(30, Type.ULEB128), 
        ABI_FP_optimization_goals(31, Type.ULEB128), 
        compatibility(32, Type.NTBS), 
        CPU_unaligned_access(34, Type.ULEB128), 
        FP_HP_extension(36, Type.ULEB128), 
        ABI_FP_16bit_format(38, Type.ULEB128), 
        MPextension_use(42, Type.ULEB128), 
        DIV_use(44, Type.ULEB128), 
        nodefaults(64, Type.ULEB128), 
        also_compatible_with(65, Type.ULEB128), 
        T2EE_use(66, Type.ULEB128), 
        conformance(67, Type.NTBS), 
        Virtualization_use(68, Type.ULEB128), 
        undefined69(69, Type.None), 
        MPextension_use_legacy(70, Type.ULEB128);
        
        public final int id;
        public final Type type;
        
        public static Tag get(final int n) {
            final Tag[] values = values();
            for (int length = values.length, i = 0; i < length; ++i) {
                if (values[i].id == n) {
                    return values[i];
                }
            }
            return null;
        }
        
        private Tag(final int id, final Type type) {
            this.id = id;
            this.type = type;
        }
    }
    
    public static class Attribute
    {
        public final Tag tag;
        private final Object value;
        
        Attribute(final Tag tag, final Object value) {
            this.tag = tag;
            this.value = value;
        }
        
        public final boolean isNTBS() {
            return Type.NTBS == this.tag.type;
        }
        
        public final String getNTBS() {
            if (Type.NTBS == this.tag.type) {
                return (String)this.value;
            }
            throw new IllegalArgumentException("Not NTBS but " + this.tag.type);
        }
        
        public final boolean isULEB128() {
            return Type.ULEB128 == this.tag.type;
        }
        
        public final byte getULEB128() {
            if (Type.ULEB128 == this.tag.type) {
                return (byte)this.value;
            }
            throw new IllegalArgumentException("Not ULEB128 but " + this.tag.type);
        }
        
        @Override
        public String toString() {
            return this.tag + " = " + this.value;
        }
    }
    
    public static class VendorAttributes
    {
        public final String vendor;
        public final List<Attribute> attributes;
        
        VendorAttributes(final String vendor, final List<Attribute> attributes) {
            this.vendor = vendor;
            this.attributes = attributes;
        }
        
        @Override
        public String toString() {
            return this.vendor + this.attributes.toString();
        }
    }
}
