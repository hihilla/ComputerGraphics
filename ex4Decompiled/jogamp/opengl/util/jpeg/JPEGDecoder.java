// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.jpeg;

import com.jogamp.common.util.ArrayHashSet;
import com.jogamp.common.util.VersionNumber;
import jogamp.opengl.Debug;
import com.jogamp.opengl.util.texture.TextureData;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import java.io.InputStream;
import com.jogamp.common.util.Bitstream;

public class JPEGDecoder
{
    private static final boolean DEBUG;
    private static final boolean DEBUG_IN = false;
    private static final int M_SOI = 65496;
    private static final int M_EOI = 65497;
    private static final int M_SOF0 = 65472;
    private static final int M_SOF2 = 65474;
    private static final int M_DHT = 65476;
    private static final int M_SOS = 65498;
    private static final int M_QTT = 65499;
    private static final int M_DRI = 65501;
    private static final int M_APP00 = 65504;
    private static final int M_APP01 = 65505;
    private static final int M_APP02 = 65506;
    private static final int M_APP03 = 65507;
    private static final int M_APP04 = 65508;
    private static final int M_APP05 = 65509;
    private static final int M_APP06 = 65510;
    private static final int M_APP07 = 65511;
    private static final int M_APP08 = 65512;
    private static final int M_APP09 = 65513;
    private static final int M_APP10 = 65514;
    private static final int M_APP11 = 65515;
    private static final int M_APP12 = 65516;
    private static final int M_APP13 = 65517;
    private static final int M_APP14 = 65518;
    private static final int M_APP15 = 65519;
    private static final int M_ANO = 65534;
    static final int[] dctZigZag;
    static final int dctCos1 = 4017;
    static final int dctSin1 = 799;
    static final int dctCos3 = 3406;
    static final int dctSin3 = 2276;
    static final int dctCos6 = 1567;
    static final int dctSin6 = 3784;
    static final int dctSqrt2 = 5793;
    static final int dctSqrt1d2 = 2896;
    private final Bitstream<InputStream> bstream;
    private int width;
    private int height;
    private JFIF jfif;
    private EXIF exif;
    private Adobe adobe;
    private ComponentOut[] components;
    private final Output output;
    private final Decoder decoder;
    
    public JPEGDecoder() {
        this.bstream = new Bitstream<InputStream>(new Bitstream.ByteInputStream(null), false);
        this.width = 0;
        this.height = 0;
        this.jfif = null;
        this.exif = null;
        this.adobe = null;
        this.components = null;
        this.output = new Output();
        this.decoder = new Decoder();
    }
    
    @Override
    public String toString() {
        return "JPEG[size " + this.width + "x" + this.height + ", compOut " + ((null != this.components) ? Arrays.asList(this.components).toString() : "nil") + ", " + ((null != this.jfif) ? this.jfif.toString() : "JFIF nil") + ", " + ((null != this.exif) ? this.exif.toString() : "Exif nil") + ", " + ((null != this.adobe) ? this.adobe.toString() : "Adobe nil") + "]";
    }
    
    public final JFIF getJFIFHeader() {
        return this.jfif;
    }
    
    public final EXIF getEXIFHeader() {
        return this.exif;
    }
    
    public final Adobe getAdobeHeader() {
        return this.adobe;
    }
    
    public final int getWidth() {
        return this.width;
    }
    
    public final int getHeight() {
        return this.height;
    }
    
    private final void setStream(final InputStream inputStream) {
        try {
            this.bstream.setStream(inputStream, false);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private final int readUInt8() throws IOException {
        return this.bstream.readUInt8();
    }
    
    private final int readUInt16() throws IOException {
        return this.bstream.readUInt16(true);
    }
    
    private final int readNumber() throws IOException {
        final int uInt16 = this.readUInt16();
        if (uInt16 != 4) {
            throw new CodecException("ERROR: Define number format error [Len!=4, but " + uInt16 + "]");
        }
        return this.readUInt16();
    }
    
    private final byte[] readDataBlock() throws IOException {
        int i = 0;
        int n = 0;
        final int uInt16 = this.readUInt16();
        i += 2;
        final byte[] array = new byte[uInt16 - 2];
        while (i < uInt16) {
            array[n++] = (byte)this.readUInt8();
            ++i;
        }
        return array;
    }
    
    static final void dumpData(final byte[] array, final int n, final int n2) {
        int i = 0;
        while (i < n2) {
            System.err.print(i % 8 + ": ");
            for (int n3 = 0; n3 < 8 && i < n2; ++n3, ++i) {
                System.err.print(toHexString(0xFF & array[n + i]) + ", ");
            }
            System.err.println("");
        }
    }
    
    public synchronized void clear(final InputStream stream) {
        this.setStream(stream);
        this.width = 0;
        this.height = 0;
        this.jfif = null;
        this.exif = null;
        this.adobe = null;
        this.components = null;
    }
    
    public synchronized JPEGDecoder parse(final InputStream inputStream) throws IOException {
        this.clear(inputStream);
        final int[][] array = new int[15][];
        final BinObj[] array2 = new BinObj[15];
        final BinObj[] array3 = new BinObj[15];
        Frame frame = null;
        int number = 0;
        final int uInt16 = this.readUInt16();
        if (uInt16 != 65496) {
            throw new CodecException("SOI not found, but has marker " + toHexString(uInt16));
        }
        for (int i = this.readUInt16(); i != 65497; i = this.readUInt16()) {
            if (JPEGDecoder.DEBUG) {
                System.err.println("JPG.parse got marker " + toHexString(i));
            }
            switch (i) {
                case 65504:
                case 65505:
                case 65506:
                case 65507:
                case 65508:
                case 65509:
                case 65510:
                case 65511:
                case 65512:
                case 65513:
                case 65514:
                case 65515:
                case 65516:
                case 65517:
                case 65518:
                case 65519:
                case 65534: {
                    final byte[] dataBlock = this.readDataBlock();
                    if (i == 65504) {
                        this.jfif = JFIF.get(dataBlock);
                    }
                    if (i == 65505) {
                        this.exif = EXIF.get(dataBlock);
                    }
                    if (i == 65518) {
                        this.adobe = Adobe.get(dataBlock);
                    }
                    i = 0;
                    break;
                }
                case 65499: {
                    int j = 0;
                    final int uInt17 = this.readUInt16();
                    j += 2;
                    while (j < uInt17) {
                        final int uInt18 = this.readUInt8();
                        ++j;
                        final int n = uInt18 >> 4;
                        final int n2 = uInt18 & 0xF;
                        final int[] array4 = new int[64];
                        if (n == 0) {
                            for (int k = 0; k < 64; ++k) {
                                array4[JPEGDecoder.dctZigZag[k]] = this.readUInt8();
                                ++j;
                            }
                        }
                        else {
                            if (n != 1) {
                                throw new CodecException("DQT: invalid table precision " + n + ", quantizationTableSpec " + uInt18 + ", idx " + n2);
                            }
                            for (int l = 0; l < 64; ++l) {
                                array4[JPEGDecoder.dctZigZag[l]] = this.readUInt16();
                                j += 2;
                            }
                        }
                        array[n2] = array4;
                        if (JPEGDecoder.DEBUG) {
                            System.err.println("JPEG.parse.QTT[" + n2 + "]: spec " + uInt18 + ", precision " + n + ", data " + j + "/" + uInt17);
                        }
                    }
                    if (j != uInt17) {
                        throw new CodecException("ERROR: QTT format error [count!=Length]: " + j + "/" + uInt17);
                    }
                    i = 0;
                    break;
                }
                case 65472:
                case 65474: {
                    if (null != frame) {
                        throw new CodecException("only single frame JPEGs supported");
                    }
                    int n3 = 0;
                    final int uInt19 = this.readUInt16();
                    n3 += 2;
                    final boolean b = i == 65474;
                    final int uInt20 = this.readUInt8();
                    ++n3;
                    final int uInt21 = this.readUInt16();
                    n3 += 2;
                    final int uInt22 = this.readUInt16();
                    n3 += 2;
                    final int uInt23 = this.readUInt8();
                    ++n3;
                    frame = new Frame(b, uInt20, uInt21, uInt22, uInt23, array);
                    this.width = frame.samplesPerLine;
                    this.height = frame.scanLines;
                    for (int n4 = 0; n4 < uInt23; ++n4) {
                        final int uInt24 = this.readUInt8();
                        ++n3;
                        final int uInt25 = this.readUInt8();
                        ++n3;
                        final int n5 = uInt25 >> 4;
                        final int n6 = uInt25 & 0xF;
                        final int uInt26 = this.readUInt8();
                        ++n3;
                        frame.putOrdered(uInt24, new ComponentIn(n5, n6, uInt26));
                    }
                    if (n3 != uInt19) {
                        throw new CodecException("ERROR: SOF format error [count!=Length]");
                    }
                    this.prepareComponents(frame);
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPG.parse.SOF[02]: Got frame " + frame);
                    }
                    i = 0;
                    break;
                }
                case 65476: {
                    int n7 = 0;
                    final int uInt27 = this.readUInt16();
                    n7 += 2;
                    int n8 = n7;
                    int n9 = 0;
                    while (n8 < uInt27) {
                        final int uInt28 = this.readUInt8();
                        ++n7;
                        final int[] array5 = new int[16];
                        int n10 = 0;
                        for (int n11 = 0; n11 < 16; ++n11) {
                            final int n12 = n10;
                            final int[] array6 = array5;
                            final int n13 = n11;
                            final int uInt29 = this.readUInt8();
                            array6[n13] = uInt29;
                            n10 = n12 + uInt29;
                            ++n7;
                        }
                        final byte[] array7 = new byte[n10];
                        for (int n14 = 0; n14 < n10; ++n14) {
                            array7[n14] = (byte)this.readUInt8();
                            ++n7;
                        }
                        n9 += n10;
                        n8 += 17 + n10;
                        ((uInt28 >> 4 == 0) ? array3 : array2)[uInt28 & 0xF] = this.buildHuffmanTable(array5, array7);
                    }
                    if (n7 != uInt27 || n8 != n7) {
                        throw new CodecException("ERROR: Huffman table format error [count!=Length]");
                    }
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPG.parse.DHT: Got Huffman CodeLengthTotal " + n9);
                    }
                    i = 0;
                    break;
                }
                case 65501: {
                    number = this.readNumber();
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPG.parse.DRI: Got Reset Interval " + number);
                    }
                    i = 0;
                    break;
                }
                case 65498: {
                    int n15 = 0;
                    final int uInt30 = this.readUInt16();
                    n15 += 2;
                    final int uInt31 = this.readUInt8();
                    ++n15;
                    final ArrayList<ComponentIn> list = new ArrayList<ComponentIn>();
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPG.parse.SOS: selectorCount [0.." + (uInt31 - 1) + "]: " + frame);
                    }
                    for (int n16 = 0; n16 < uInt31; ++n16) {
                        final int uInt32 = this.readUInt8();
                        ++n15;
                        final ComponentIn compByID = frame.getCompByID(uInt32);
                        final int uInt33 = this.readUInt8();
                        ++n15;
                        compByID.huffmanTableDC = array3[uInt33 >> 4];
                        compByID.huffmanTableAC = array2[uInt33 & 0xF];
                        list.add(compByID);
                    }
                    final int uInt34 = this.readUInt8();
                    ++n15;
                    final int uInt35 = this.readUInt8();
                    ++n15;
                    final int uInt36 = this.readUInt8();
                    if (++n15 != uInt30) {
                        throw new CodecException("ERROR: scan header format error [count!=Length]");
                    }
                    i = this.decoder.decodeScan(frame, list, number, uInt34, uInt35, uInt36 >> 4, uInt36 & 0xF);
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPG.parse.SOS.decode result " + toHexString(i));
                    }
                    break;
                }
                default: {
                    throw new CodecException("unknown JPEG marker " + toHexString(i) + ", " + this.bstream);
                }
            }
            if (i == 0) {}
        }
        if (JPEGDecoder.DEBUG) {
            System.err.println("JPG.parse.2: End of parsing input " + this);
        }
        if (null == frame) {
            throw new CodecException("no single frame found in stream " + this);
        }
        frame.validateComponents();
        final int compCount = frame.getCompCount();
        this.components = new ComponentOut[compCount];
        for (int n17 = 0; n17 < compCount; ++n17) {
            final ComponentIn compByIndex = frame.getCompByIndex(n17);
            this.components[n17] = new ComponentOut(this.output.buildComponentData(frame, compByIndex), compByIndex.h / frame.maxH, compByIndex.v / frame.maxV);
        }
        if (JPEGDecoder.DEBUG) {
            System.err.println("JPG.parse.X: End of processing input " + this);
        }
        return this;
    }
    
    private void prepareComponents(final Frame frame) {
        int h = 0;
        int v = 0;
        final int compCount = frame.getCompCount();
        for (int i = 0; i < compCount; ++i) {
            final ComponentIn compByIndex = frame.getCompByIndex(i);
            if (h < compByIndex.h) {
                h = compByIndex.h;
            }
            if (v < compByIndex.v) {
                v = compByIndex.v;
            }
        }
        final int mcusPerLine = (int)Math.ceil(frame.samplesPerLine / 8.0f / h);
        final int mcusPerColumn = (int)Math.ceil(frame.scanLines / 8.0f / v);
        for (int j = 0; j < compCount; ++j) {
            final ComponentIn compByIndex2 = frame.getCompByIndex(j);
            compByIndex2.allocateBlocks((int)Math.ceil(Math.ceil(frame.scanLines / 8.0f) * compByIndex2.v / v), mcusPerColumn * compByIndex2.v, (int)Math.ceil(Math.ceil(frame.samplesPerLine / 8.0f) * compByIndex2.h / h), mcusPerLine * compByIndex2.h);
        }
        frame.maxH = h;
        frame.maxV = v;
        frame.mcusPerLine = mcusPerLine;
        frame.mcusPerColumn = mcusPerColumn;
    }
    
    private BinObj buildHuffmanTable(final int[] array, final byte[] array2) {
        int n = 0;
        int n2 = 16;
        final ArrayList<BinObjIdxed> list = new ArrayList<BinObjIdxed>();
        while (n2 > 0 && 0 == array[n2 - 1]) {
            --n2;
        }
        list.add(new BinObjIdxed());
        BinObjIdxed binObjIdxed = list.get(0);
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < array[i]; ++j) {
                binObjIdxed = list.remove(list.size() - 1);
                binObjIdxed.children.set(binObjIdxed.index, array2[n]);
                while (binObjIdxed.index > 0) {
                    binObjIdxed = list.remove(list.size() - 1);
                }
                final BinObjIdxed binObjIdxed2 = binObjIdxed;
                ++binObjIdxed2.index;
                list.add(binObjIdxed);
                while (list.size() <= i) {
                    final BinObjIdxed binObjIdxed3 = new BinObjIdxed();
                    list.add(binObjIdxed3);
                    binObjIdxed.children.set(binObjIdxed.index, binObjIdxed3.children);
                    binObjIdxed = binObjIdxed3;
                }
                ++n;
            }
            if (i + 1 < n2) {
                final BinObjIdxed binObjIdxed4 = new BinObjIdxed();
                list.add(binObjIdxed4);
                binObjIdxed.children.set(binObjIdxed.index, binObjIdxed4.children);
                binObjIdxed = binObjIdxed4;
            }
        }
        return list.get(0).children;
    }
    
    public synchronized void getPixel(final ColorSink colorSink, final int n, final int n2) {
        final int n3 = this.width / n;
        final int n4 = this.height / n2;
        final int length = this.components.length;
        final TextureData.ColorSpace colorSpace = (null != this.adobe) ? this.adobe.colorSpace : TextureData.ColorSpace.YCbCr;
        final TextureData.ColorSpace allocate = colorSink.allocate(n, n2, colorSpace, length);
        if (TextureData.ColorSpace.RGB != allocate && TextureData.ColorSpace.YCbCr != allocate) {
            throw new IllegalArgumentException("Unsupported storage color space: " + allocate);
        }
        switch (length) {
            case 1: {
                final ComponentOut componentOut = this.components[0];
                for (int i = 0; i < n2; ++i) {
                    final byte[] line = componentOut.getLine((int)(i * componentOut.scaleY * n4));
                    for (int j = 0; j < n; ++j) {
                        final byte b = line[(int)(j * componentOut.scaleX * n3)];
                        if (TextureData.ColorSpace.YCbCr == allocate) {
                            colorSink.storeYCbCr(j, i, b, (byte)0, (byte)0);
                        }
                        else {
                            colorSink.storeRGB(j, i, b, b, b);
                        }
                    }
                }
                break;
            }
            case 2: {
                final ComponentOut componentOut2 = this.components[0];
                final ComponentOut componentOut3 = this.components[1];
                for (int k = 0; k < n2; ++k) {
                    final int n5 = k * n4;
                    final byte[] line2 = componentOut2.getLine((int)(n5 * componentOut2.scaleY));
                    final byte[] line3 = componentOut2.getLine((int)(n5 * componentOut3.scaleY));
                    for (int l = 0; l < n; ++l) {
                        final int n6 = l * n3;
                        colorSink.store2(l, k, line2[(int)(n6 * componentOut2.scaleX)], line3[(int)(n6 * componentOut3.scaleX)]);
                    }
                }
                break;
            }
            case 3: {
                if (TextureData.ColorSpace.YCbCr != colorSpace) {
                    throw new CodecException("Unsupported source color space w 3 components: " + colorSpace);
                }
                final ComponentOut componentOut4 = this.components[0];
                final ComponentOut componentOut5 = this.components[1];
                final ComponentOut componentOut6 = this.components[2];
                for (int n7 = 0; n7 < n2; ++n7) {
                    final int n8 = n7 * n4;
                    final byte[] line4 = componentOut4.getLine((int)(n8 * componentOut4.scaleY));
                    final byte[] line5 = componentOut5.getLine((int)(n8 * componentOut5.scaleY));
                    final byte[] line6 = componentOut6.getLine((int)(n8 * componentOut6.scaleY));
                    if (TextureData.ColorSpace.YCbCr == allocate) {
                        for (int n9 = 0; n9 < n; ++n9) {
                            final int n10 = n9 * n3;
                            colorSink.storeYCbCr(n9, n7, line4[(int)(n10 * componentOut4.scaleX)], line5[(int)(n10 * componentOut5.scaleX)], line6[(int)(n10 * componentOut6.scaleX)]);
                        }
                    }
                    else {
                        for (int n11 = 0; n11 < n; ++n11) {
                            final int n12 = n11 * n3;
                            final int n13 = 0xFF & line4[(int)(n12 * componentOut4.scaleX)];
                            final int n14 = 0xFF & line5[(int)(n12 * componentOut5.scaleX)];
                            final int n15 = 0xFF & line6[(int)(n12 * componentOut6.scaleX)];
                            colorSink.storeRGB(n11, n7, clampTo8bit(n13 + 1.402f * (n15 - 128.0f)), clampTo8bit(n13 - 0.3441363f * (n14 - 128.0f) - 0.71413636f * (n15 - 128.0f)), clampTo8bit(n13 + 1.772f * (n14 - 128.0f)));
                        }
                    }
                }
                break;
            }
            case 4: {
                if (TextureData.ColorSpace.YCCK != colorSpace && TextureData.ColorSpace.CMYK != colorSpace) {
                    throw new CodecException("Unsupported source color space w 4 components: " + colorSpace);
                }
                final ComponentOut componentOut7 = this.components[0];
                final ComponentOut componentOut8 = this.components[1];
                final ComponentOut componentOut9 = this.components[2];
                final ComponentOut componentOut10 = this.components[3];
                for (int n16 = 0; n16 < n2; ++n16) {
                    final int n17 = n16 * n4;
                    final byte[] line7 = componentOut7.getLine((int)(n17 * componentOut7.scaleY));
                    final byte[] line8 = componentOut8.getLine((int)(n17 * componentOut8.scaleY));
                    final byte[] line9 = componentOut9.getLine((int)(n17 * componentOut9.scaleY));
                    final byte[] line10 = componentOut10.getLine((int)(n17 * componentOut10.scaleY));
                    if (TextureData.ColorSpace.YCbCr == allocate) {
                        if (TextureData.ColorSpace.YCCK != colorSpace) {
                            throw new CodecException("Unsupported storage color space " + allocate + " with source color space " + colorSpace);
                        }
                        for (int n18 = 0; n18 < n; ++n18) {
                            final int n19 = n18 * n3;
                            colorSink.storeYCbCr(n18, n16, line7[(int)(n19 * componentOut7.scaleX)], line8[(int)(n19 * componentOut8.scaleX)], line9[(int)(n19 * componentOut9.scaleX)]);
                        }
                    }
                    else if (TextureData.ColorSpace.CMYK == colorSpace) {
                        for (int n20 = 0; n20 < n; ++n20) {
                            final int n21 = n20 * n3;
                            final int n22 = 0xFF & line7[(int)(n21 * componentOut7.scaleX)];
                            final int n23 = 0xFF & line8[(int)(n21 * componentOut8.scaleX)];
                            final int n24 = 0xFF & line9[(int)(n21 * componentOut9.scaleX)];
                            final int n25 = 0xFF & line10[(int)(n21 * componentOut10.scaleX)];
                            colorSink.storeRGB(n20, n16, clampTo8bit(n22 * n25 / 255.0f), clampTo8bit(n23 * n25 / 255.0f), clampTo8bit(n24 * n25 / 255.0f));
                        }
                    }
                    else {
                        for (int n26 = 0; n26 < n; ++n26) {
                            final int n27 = n26 * n3;
                            final int n28 = 0xFF & line7[(int)(n27 * componentOut7.scaleX)];
                            final int n29 = 0xFF & line8[(int)(n27 * componentOut8.scaleX)];
                            final int n30 = 0xFF & line9[(int)(n27 * componentOut9.scaleX)];
                            final int n31 = 0xFF & line10[(int)(n27 * componentOut10.scaleX)];
                            colorSink.storeRGB(n26, n16, clampTo8bit((255.0f - (n28 + 1.402f * (n30 - 128.0f))) * n31 / 255.0f), clampTo8bit((255.0f - (n28 - 0.3441363f * (n29 - 128.0f) - 0.71413636f * (n30 - 128.0f))) * n31 / 255.0f), clampTo8bit((255.0f - (n28 + 1.772f * (n29 - 128.0f))) * n31 / 255.0f));
                        }
                    }
                }
                break;
            }
            default: {
                throw new CodecException("Unsupported color model: Space " + colorSpace + ", components " + length);
            }
        }
    }
    
    private static byte clampTo8bit(final float n) {
        return (byte)((n < 0.0f) ? 0.0f : ((n > 255.0f) ? 255.0f : n));
    }
    
    private static String toHexString(final int n) {
        return "0x" + Integer.toHexString(n);
    }
    
    static {
        DEBUG = Debug.debug("JPEGImage");
        dctZigZag = new int[] { 0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63 };
    }
    
    public static class JFIF
    {
        final VersionNumber version;
        final int densityUnits;
        final int xDensity;
        final int yDensity;
        final int thumbWidth;
        final int thumbHeight;
        final byte[] thumbData;
        
        private JFIF(final byte[] array) {
            this.version = new VersionNumber(array[5], array[6], 0);
            this.densityUnits = array[7];
            this.xDensity = ((array[8] << 8 & 0xFF00) | (array[9] & 0xFF));
            this.yDensity = ((array[10] << 8 & 0xFF00) | (array[11] & 0xFF));
            this.thumbWidth = array[12];
            this.thumbHeight = array[13];
            if (0 < this.thumbWidth && 0 < this.thumbHeight) {
                final int n = 14 + 3 * this.thumbWidth * this.thumbHeight;
                System.arraycopy(array, 14, this.thumbData = new byte[n], 0, n);
            }
            else {
                this.thumbData = null;
            }
        }
        
        public static final JFIF get(final byte[] array) throws RuntimeException {
            if (array[0] == 74 && array[1] == 70 && array[2] == 73 && array[3] == 70 && array[4] == 0) {
                return new JFIF(array);
            }
            return null;
        }
        
        @Override
        public final String toString() {
            return "JFIF[ver " + this.version + ", density[units " + this.densityUnits + ", " + this.xDensity + "x" + this.yDensity + "], thumb " + this.thumbWidth + "x" + this.thumbHeight + "]";
        }
    }
    
    public static class Adobe
    {
        final short version;
        final short flags0;
        final short flags1;
        final short colorCode;
        final TextureData.ColorSpace colorSpace;
        
        private Adobe(final byte[] array) {
            this.version = array[6];
            this.flags0 = (short)((array[7] << 8 & 0xFF00) | (array[8] & 0xFF));
            this.flags1 = (short)((array[9] << 8 & 0xFF00) | (array[10] & 0xFF));
            switch (this.colorCode = array[11]) {
                case 2: {
                    this.colorSpace = TextureData.ColorSpace.YCCK;
                    break;
                }
                case 1: {
                    this.colorSpace = TextureData.ColorSpace.YCbCr;
                    break;
                }
                default: {
                    this.colorSpace = TextureData.ColorSpace.CMYK;
                    break;
                }
            }
        }
        
        public static final Adobe get(final byte[] array) throws RuntimeException {
            if (array[0] == 65 && array[1] == 100 && array[2] == 111 && array[3] == 98 && array[4] == 101 && array[5] == 0) {
                return new Adobe(array);
            }
            return null;
        }
        
        @Override
        public final String toString() {
            return "Adobe[ver " + this.version + ", flags[" + toHexString(this.flags0) + ", " + toHexString(this.flags1) + "], colorSpace/Code " + this.colorSpace + "/" + toHexString(this.colorCode) + "]";
        }
    }
    
    public static class EXIF
    {
        private EXIF(final byte[] array) {
        }
        
        public static final EXIF get(final byte[] array) throws RuntimeException {
            if (array[0] == 69 && array[1] == 120 && array[2] == 105 && array[3] == 102 && array[4] == 0) {
                return new EXIF(array);
            }
            return null;
        }
        
        @Override
        public final String toString() {
            return "EXIF[]";
        }
    }
    
    public static class CodecException extends RuntimeException
    {
        CodecException(final String s) {
            super(s);
        }
    }
    
    public static class MarkerException extends CodecException
    {
        final int marker;
        
        MarkerException(final int marker, final String s) {
            super(s + " - Marker " + toHexString(marker));
            this.marker = marker;
        }
        
        public int getMarker() {
            return this.marker;
        }
    }
    
    static class Frame
    {
        final boolean progressive;
        final int precision;
        final int scanLines;
        final int samplesPerLine;
        private final ArrayHashSet<Integer> compIDs;
        private final ComponentIn[] comps;
        private final int compCount;
        final int[][] qtt;
        int maxCompID;
        int maxH;
        int maxV;
        int mcusPerLine;
        int mcusPerColumn;
        
        Frame(final boolean progressive, final int precision, final int scanLines, final int samplesPerLine, final int compCount, final int[][] qtt) {
            this.progressive = progressive;
            this.precision = precision;
            this.scanLines = scanLines;
            this.samplesPerLine = samplesPerLine;
            this.compIDs = new ArrayHashSet<Integer>(false, compCount, 0.75f);
            this.comps = new ComponentIn[compCount];
            this.compCount = compCount;
            this.qtt = qtt;
        }
        
        private final void checkBounds(final int n) {
            if (0 > n || n >= this.compCount) {
                throw new CodecException("Idx out of bounds " + n + ", " + this);
            }
        }
        
        public final void validateComponents() {
            for (int i = 0; i < this.compCount; ++i) {
                final ComponentIn componentIn = this.comps[i];
                if (null == componentIn) {
                    throw new CodecException("Component[" + i + "] null");
                }
                if (null == this.qtt[componentIn.qttIdx]) {
                    throw new CodecException("Component[" + i + "].qttIdx -> null QTT");
                }
            }
        }
        
        public final int getCompCount() {
            return this.compCount;
        }
        
        public final int getMaxCompID() {
            return this.maxCompID;
        }
        
        public final void putOrdered(final int maxCompID, final ComponentIn componentIn) {
            if (this.maxCompID < maxCompID) {
                this.maxCompID = maxCompID;
            }
            final int size = this.compIDs.size();
            this.checkBounds(size);
            this.compIDs.add(maxCompID);
            this.comps[size] = componentIn;
        }
        
        public final ComponentIn getCompByIndex(final int n) {
            this.checkBounds(n);
            return this.comps[n];
        }
        
        public final ComponentIn getCompByID(final int n) {
            return this.getCompByIndex(this.compIDs.indexOf(n));
        }
        
        public final int getCompID(final int n) {
            return this.compIDs.get(n);
        }
        
        public final boolean hasCompID(final int n) {
            return this.compIDs.contains(n);
        }
        
        @Override
        public final String toString() {
            return "Frame[progressive " + this.progressive + ", precision " + this.precision + ", scanLines " + this.scanLines + ", samplesPerLine " + this.samplesPerLine + ", components[count " + this.compCount + ", maxID " + this.maxCompID + ", componentIDs " + this.compIDs + ", comps " + Arrays.asList(this.comps) + "]]";
        }
    }
    
    static class ComponentIn
    {
        final int h;
        final int v;
        final int qttIdx;
        int blocksPerColumn;
        int blocksPerColumnForMcu;
        int blocksPerLine;
        int blocksPerLineForMcu;
        int[][][] blocks;
        int pred;
        BinObj huffmanTableAC;
        BinObj huffmanTableDC;
        
        ComponentIn(final int h, final int v, final int qttIdx) {
            this.h = h;
            this.v = v;
            this.qttIdx = qttIdx;
        }
        
        public final void allocateBlocks(final int blocksPerColumn, final int blocksPerColumnForMcu, final int blocksPerLine, final int blocksPerLineForMcu) {
            this.blocksPerColumn = blocksPerColumn;
            this.blocksPerColumnForMcu = blocksPerColumnForMcu;
            this.blocksPerLine = blocksPerLine;
            this.blocksPerLineForMcu = blocksPerLineForMcu;
            this.blocks = new int[blocksPerColumnForMcu][blocksPerLineForMcu][64];
        }
        
        public final int[] getBlock(final int n, final int n2) {
            if (n >= this.blocksPerColumnForMcu || n2 >= this.blocksPerLineForMcu) {
                throw new CodecException("Out of bounds given [" + n + "][" + n2 + "] - " + this);
            }
            return this.blocks[n][n2];
        }
        
        @Override
        public final String toString() {
            return "CompIn[h " + this.h + ", v " + this.v + ", qttIdx " + this.qttIdx + ", blocks[" + this.blocksPerColumn + ", mcu " + this.blocksPerColumnForMcu + "][" + this.blocksPerLine + ", mcu " + this.blocksPerLineForMcu + "][64]]";
        }
    }
    
    static class ComponentOut
    {
        private final ArrayList<byte[]> lines;
        final float scaleX;
        final float scaleY;
        
        ComponentOut(final ArrayList<byte[]> lines, final float scaleX, final float scaleY) {
            this.lines = lines;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
        
        public final byte[] getLine(final int n) {
            final int size = this.lines.size();
            return this.lines.get((n < size) ? n : (size - 1));
        }
        
        @Override
        public final String toString() {
            return "CompOut[lines " + this.lines.size() + ", scale " + this.scaleX + "x" + this.scaleY + "]";
        }
    }
    
    static class BinObjIdxed
    {
        final BinObj children;
        byte index;
        
        BinObjIdxed() {
            this.children = new BinObj();
            this.index = 0;
        }
    }
    
    static class BinObj
    {
        final boolean isValue;
        final BinObj[] tree;
        final byte b;
        
        BinObj(final byte b) {
            this.isValue = true;
            this.b = b;
            this.tree = null;
        }
        
        BinObj() {
            this.isValue = false;
            this.b = 0;
            this.tree = new BinObj[2];
        }
        
        final byte getValue() {
            return this.b;
        }
        
        final BinObj get(final int n) {
            return this.tree[n];
        }
        
        final void set(final byte b, final byte b2) {
            this.tree[b] = new BinObj(b2);
        }
        
        final void set(final byte b, final BinObj binObj) {
            this.tree[b] = binObj;
        }
    }
    
    static class Output
    {
        private int blocksPerLine;
        private int blocksPerColumn;
        private int samplesPerLine;
        
        private ArrayList<byte[]> buildComponentData(final Frame frame, final ComponentIn componentIn) {
            final ArrayList<byte[]> list = new ArrayList<byte[]>();
            this.blocksPerLine = componentIn.blocksPerLine;
            this.blocksPerColumn = componentIn.blocksPerColumn;
            this.samplesPerLine = this.blocksPerLine << 3;
            final int[] array = new int[64];
            final byte[] array2 = new byte[64];
            for (int i = 0; i < this.blocksPerColumn; ++i) {
                final int n = i << 3;
                for (int j = 0; j < 8; ++j) {
                    list.add(new byte[this.samplesPerLine]);
                }
                for (int k = 0; k < this.blocksPerLine; ++k) {
                    this.quantizeAndInverse(componentIn.getBlock(i, k), array2, array, frame.qtt[componentIn.qttIdx]);
                    final int n2 = k << 3;
                    int n3 = 0;
                    for (int l = 0; l < 8; ++l) {
                        final byte[] array3 = list.get(n + l);
                        for (int n4 = 0; n4 < 8; ++n4) {
                            array3[n2 + n4] = array2[n3++];
                        }
                    }
                }
            }
            return list;
        }
        
        private void quantizeAndInverse(final int[] array, final byte[] array2, final int[] array3, final int[] array4) {
            for (int i = 0; i < 64; ++i) {
                array3[i] = array[i] * array4[i];
            }
            for (int j = 0; j < 8; ++j) {
                final int n = 8 * j;
                if (array3[1 + n] == 0 && array3[2 + n] == 0 && array3[3 + n] == 0 && array3[4 + n] == 0 && array3[5 + n] == 0 && array3[6 + n] == 0 && array3[7 + n] == 0) {
                    final int n2 = 5793 * array3[0 + n] + 512 >> 10;
                    array3[1 + n] = (array3[0 + n] = n2);
                    array3[3 + n] = (array3[2 + n] = n2);
                    array3[5 + n] = (array3[4 + n] = n2);
                    array3[7 + n] = (array3[6 + n] = n2);
                }
                else {
                    final int n3 = 5793 * array3[0 + n] + 128 >> 8;
                    final int n4 = 5793 * array3[4 + n] + 128 >> 8;
                    final int n5 = array3[2 + n];
                    final int n6 = array3[6 + n];
                    final int n7 = 2896 * (array3[1 + n] - array3[7 + n]) + 128 >> 8;
                    final int n8 = 2896 * (array3[1 + n] + array3[7 + n]) + 128 >> 8;
                    final int n9 = array3[3 + n] << 4;
                    final int n10 = array3[5 + n] << 4;
                    final int n11 = n3 - n4 + 1 >> 1;
                    final int n12 = n3 + n4 + 1 >> 1;
                    final int n13 = n11;
                    final int n14 = n5 * 3784 + n6 * 1567 + 128 >> 8;
                    final int n15 = n5 * 1567 - n6 * 3784 + 128 >> 8;
                    final int n16 = n14;
                    final int n17 = n7 - n10 + 1 >> 1;
                    final int n18 = n7 + n10 + 1 >> 1;
                    final int n19 = n17;
                    final int n20 = n8 + n9 + 1 >> 1;
                    final int n21 = n8 - n9 + 1 >> 1;
                    final int n22 = n20;
                    final int n23 = n12 - n16 + 1 >> 1;
                    final int n24 = n12 + n16 + 1 >> 1;
                    final int n25 = n23;
                    final int n26 = n13 - n15 + 1 >> 1;
                    final int n27 = n13 + n15 + 1 >> 1;
                    final int n28 = n26;
                    final int n29 = n18 * 2276 + n22 * 3406 + 2048 >> 12;
                    final int n30 = n18 * 3406 - n22 * 2276 + 2048 >> 12;
                    final int n31 = n29;
                    final int n32 = n21 * 799 + n19 * 4017 + 2048 >> 12;
                    final int n33 = n21 * 4017 - n19 * 799 + 2048 >> 12;
                    final int n34 = n32;
                    array3[0 + n] = n24 + n31;
                    array3[7 + n] = n24 - n31;
                    array3[1 + n] = n27 + n34;
                    array3[6 + n] = n27 - n34;
                    array3[2 + n] = n28 + n33;
                    array3[5 + n] = n28 - n33;
                    array3[3 + n] = n25 + n30;
                    array3[4 + n] = n25 - n30;
                }
            }
            for (int k = 0; k < 8; ++k) {
                final int n35 = k;
                if (array3[8 + n35] == 0 && array3[16 + n35] == 0 && array3[24 + n35] == 0 && array3[32 + n35] == 0 && array3[40 + n35] == 0 && array3[48 + n35] == 0 && array3[56 + n35] == 0) {
                    final int n36 = 5793 * array3[k + 0] + 8192 >> 14;
                    array3[8 + n35] = (array3[0 + n35] = n36);
                    array3[24 + n35] = (array3[16 + n35] = n36);
                    array3[40 + n35] = (array3[32 + n35] = n36);
                    array3[56 + n35] = (array3[48 + n35] = n36);
                }
                else {
                    final int n37 = 5793 * array3[0 + n35] + 2048 >> 12;
                    final int n38 = 5793 * array3[32 + n35] + 2048 >> 12;
                    final int n39 = array3[16 + n35];
                    final int n40 = array3[48 + n35];
                    final int n41 = 2896 * (array3[8 + n35] - array3[56 + n35]) + 2048 >> 12;
                    final int n42 = 2896 * (array3[8 + n35] + array3[56 + n35]) + 2048 >> 12;
                    final int n43 = array3[24 + n35];
                    final int n44 = array3[40 + n35];
                    final int n45 = n37 - n38 + 1 >> 1;
                    final int n46 = n37 + n38 + 1 >> 1;
                    final int n47 = n45;
                    final int n48 = n39 * 3784 + n40 * 1567 + 2048 >> 12;
                    final int n49 = n39 * 1567 - n40 * 3784 + 2048 >> 12;
                    final int n50 = n48;
                    final int n51 = n41 - n44 + 1 >> 1;
                    final int n52 = n41 + n44 + 1 >> 1;
                    final int n53 = n51;
                    final int n54 = n42 + n43 + 1 >> 1;
                    final int n55 = n42 - n43 + 1 >> 1;
                    final int n56 = n54;
                    final int n57 = n46 - n50 + 1 >> 1;
                    final int n58 = n46 + n50 + 1 >> 1;
                    final int n59 = n57;
                    final int n60 = n47 - n49 + 1 >> 1;
                    final int n61 = n47 + n49 + 1 >> 1;
                    final int n62 = n60;
                    final int n63 = n52 * 2276 + n56 * 3406 + 2048 >> 12;
                    final int n64 = n52 * 3406 - n56 * 2276 + 2048 >> 12;
                    final int n65 = n63;
                    final int n66 = n55 * 799 + n53 * 4017 + 2048 >> 12;
                    final int n67 = n55 * 4017 - n53 * 799 + 2048 >> 12;
                    final int n68 = n66;
                    array3[0 + n35] = n58 + n65;
                    array3[56 + n35] = n58 - n65;
                    array3[8 + n35] = n61 + n68;
                    array3[48 + n35] = n61 - n68;
                    array3[16 + n35] = n62 + n67;
                    array3[40 + n35] = n62 - n67;
                    array3[24 + n35] = n59 + n64;
                    array3[32 + n35] = n59 - n64;
                }
            }
            for (int l = 0; l < 64; ++l) {
                final int n69 = 128 + (array3[l] + 8 >> 4);
                array2[l] = (byte)((n69 < 0) ? 0 : ((n69 > 255) ? 255 : n69));
            }
        }
    }
    
    class Decoder
    {
        private int mcusPerLine;
        private boolean progressive;
        private int spectralStart;
        private int spectralEnd;
        private int successive;
        private int eobrun;
        private int successiveACState;
        private int successiveACNextValue;
        final DecoderFunction decodeBaseline;
        final DecoderFunction decodeDCFirst;
        final DecoderFunction decodeDCSuccessive;
        final DecoderFunction decodeACFirst;
        final DecoderFunction decodeACSuccessive;
        
        Decoder() {
            this.decodeBaseline = new BaselineDecoder();
            this.decodeDCFirst = new DCFirstDecoder();
            this.decodeDCSuccessive = new DCSuccessiveDecoder();
            this.decodeACFirst = new ACFirstDecoder();
            this.decodeACSuccessive = new ACSuccessiveDecoder();
        }
        
        private int decodeScan(final Frame frame, final ArrayList<ComponentIn> list, int n, final int spectralStart, final int spectralEnd, final int n2, final int successive) throws IOException {
            this.mcusPerLine = frame.mcusPerLine;
            this.progressive = frame.progressive;
            JPEGDecoder.this.bstream.skip(JPEGDecoder.this.bstream.getBitCount());
            this.spectralStart = spectralStart;
            this.spectralEnd = spectralEnd;
            this.successive = successive;
            final int size = list.size();
            DecoderFunction decodeBaseline;
            if (this.progressive) {
                if (spectralStart == 0) {
                    decodeBaseline = ((n2 == 0) ? this.decodeDCFirst : this.decodeDCSuccessive);
                }
                else {
                    decodeBaseline = ((n2 == 0) ? this.decodeACFirst : this.decodeACSuccessive);
                }
            }
            else {
                decodeBaseline = this.decodeBaseline;
            }
            int i = 0;
            int n3;
            if (size == 1) {
                final ComponentIn componentIn = list.get(0);
                n3 = componentIn.blocksPerLine * componentIn.blocksPerColumn;
            }
            else {
                n3 = this.mcusPerLine * frame.mcusPerColumn;
            }
            if (0 == n) {
                n = n3;
            }
            if (JPEGDecoder.DEBUG) {
                System.err.println("JPEG.decodeScan.1 resetInterval " + n + ", mcuExpected " + n3 + ", sA " + spectralStart + ", sP " + n2 + ", sE " + spectralEnd + ", suc " + successive + ", decodeFn " + decodeBaseline.getClass().getSimpleName());
            }
            int access$500 = 0;
            while (i < n3) {
                for (int j = 0; j < size; ++j) {
                    list.get(j).pred = 0;
                }
                this.eobrun = 0;
                try {
                    if (size == 1) {
                        final ComponentIn componentIn2 = list.get(0);
                        for (int k = 0; k < n; ++k) {
                            this.decodeBlock(componentIn2, decodeBaseline, i);
                            ++i;
                        }
                    }
                    else {
                        for (int l = 0; l < n; ++l) {
                            for (int n4 = 0; n4 < size; ++n4) {
                                final ComponentIn componentIn3 = list.get(n4);
                                final int h = componentIn3.h;
                                for (int v = componentIn3.v, n5 = 0; n5 < v; ++n5) {
                                    for (int n6 = 0; n6 < h; ++n6) {
                                        this.decodeMcu(componentIn3, decodeBaseline, i, n5, n6);
                                    }
                                }
                            }
                            ++i;
                        }
                    }
                }
                catch (MarkerException ex) {
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPEG.decodeScan: Marker exception: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    return ex.getMarker();
                }
                catch (CodecException ex2) {
                    if (JPEGDecoder.DEBUG) {
                        System.err.println("JPEG.decodeScan: Codec exception: " + ex2.getMessage());
                        ex2.printStackTrace();
                    }
                    JPEGDecoder.this.bstream.skip(JPEGDecoder.this.bstream.getBitCount());
                    return 65497;
                }
                JPEGDecoder.this.bstream.skip(JPEGDecoder.this.bstream.getBitCount());
                JPEGDecoder.this.bstream.mark(2);
                access$500 = JPEGDecoder.this.readUInt16();
                if (access$500 < 65280) {
                    JPEGDecoder.this.bstream.reset();
                    throw new CodecException("marker not found @ mcu " + i + "/" + n3 + ", u16: " + toHexString(access$500));
                }
                final boolean b = 65488 <= access$500 && access$500 <= 65495;
                if (JPEGDecoder.DEBUG) {
                    System.err.println("JPEG.decodeScan: MCUs " + i + "/" + n3 + ", u16 " + toHexString(access$500) + ", RSTx " + b + ", " + frame);
                }
                if (!b) {
                    break;
                }
            }
            return access$500;
        }
        
        private final int readBit() throws MarkerException, IOException {
            final int bit = JPEGDecoder.this.bstream.readBit(true);
            if (-1 == bit || 7 != JPEGDecoder.this.bstream.getBitCount()) {
                return bit;
            }
            final int bitBuffer = JPEGDecoder.this.bstream.getBitBuffer();
            if (255 == bitBuffer) {
                final int read = JPEGDecoder.this.bstream.getStream().read();
                if (-1 == read) {
                    throw new CodecException("marked prefix 0xFF, then EOF");
                }
                if (read != 0) {
                    throw new MarkerException(bitBuffer << 8 | read, "Marker at readBit pos " + JPEGDecoder.this.bstream);
                }
            }
            return bit;
        }
        
        private int decodeHuffman(final BinObj binObj) throws IOException {
            BinObj value = binObj;
            int bit;
            while ((bit = this.readBit()) != -1) {
                value = value.get(bit);
                if (value.isValue) {
                    return 0xFF & value.getValue();
                }
            }
            throw new CodecException("EOF reached at " + JPEGDecoder.this.bstream);
        }
        
        private int receive(int i) throws IOException {
            int n = 0;
            while (i > 0) {
                final int bit = this.readBit();
                if (bit == -1) {
                    return -1;
                }
                n = (n << 1 | bit);
                --i;
            }
            return n;
        }
        
        private int receiveAndExtend(final int n) throws IOException {
            final int receive = this.receive(n);
            if (receive >= 1 << n - 1) {
                return receive;
            }
            return receive + (-1 << n) + 1;
        }
        
        void decodeMcu(final ComponentIn componentIn, final DecoderFunction decoderFunction, final int n, final int n2, final int n3) throws IOException {
            decoderFunction.decode(componentIn, componentIn.getBlock((n / this.mcusPerLine | 0x0) * componentIn.v + n2, n % this.mcusPerLine * componentIn.h + n3));
        }
        
        void decodeBlock(final ComponentIn componentIn, final DecoderFunction decoderFunction, final int n) throws IOException {
            decoderFunction.decode(componentIn, componentIn.getBlock(n / componentIn.blocksPerLine | 0x0, n % componentIn.blocksPerLine));
        }
        
        class BaselineDecoder implements DecoderFunction
        {
            @Override
            public void decode(final ComponentIn componentIn, final int[] array) throws IOException {
                final int access$600 = Decoder.this.decodeHuffman(componentIn.huffmanTableDC);
                array[0] = (componentIn.pred += ((access$600 == 0) ? 0 : Decoder.this.receiveAndExtend(access$600)));
                int i = 1;
                while (i < 64) {
                    final int access$601 = Decoder.this.decodeHuffman(componentIn.huffmanTableAC);
                    final int n = access$601 & 0xF;
                    final int n2 = access$601 >> 4;
                    if (n == 0) {
                        if (n2 < 15) {
                            break;
                        }
                        i += 16;
                    }
                    else {
                        i += n2;
                        array[JPEGDecoder.dctZigZag[i]] = Decoder.this.receiveAndExtend(n);
                        ++i;
                    }
                }
            }
        }
        
        class DCFirstDecoder implements DecoderFunction
        {
            @Override
            public void decode(final ComponentIn componentIn, final int[] array) throws IOException {
                final int access$600 = Decoder.this.decodeHuffman(componentIn.huffmanTableDC);
                array[0] = (componentIn.pred += ((access$600 == 0) ? 0 : (Decoder.this.receiveAndExtend(access$600) << Decoder.this.successive)));
            }
        }
        
        class DCSuccessiveDecoder implements DecoderFunction
        {
            @Override
            public void decode(final ComponentIn componentIn, final int[] array) throws IOException {
                final int n = 0;
                array[n] |= Decoder.this.readBit() << Decoder.this.successive;
            }
        }
        
        class ACFirstDecoder implements DecoderFunction
        {
            @Override
            public void decode(final ComponentIn componentIn, final int[] array) throws IOException {
                if (Decoder.this.eobrun > 0) {
                    Decoder.this.eobrun--;
                    return;
                }
                int i = Decoder.this.spectralStart;
                while (i <= Decoder.this.spectralEnd) {
                    final int access$600 = Decoder.this.decodeHuffman(componentIn.huffmanTableAC);
                    final int n = access$600 & 0xF;
                    final int n2 = access$600 >> 4;
                    if (n == 0) {
                        if (n2 < 15) {
                            Decoder.this.eobrun = Decoder.this.receive(n2) + (1 << n2) - 1;
                            break;
                        }
                        i += 16;
                    }
                    else {
                        i += n2;
                        array[JPEGDecoder.dctZigZag[i]] = Decoder.this.receiveAndExtend(n) * (1 << Decoder.this.successive);
                        ++i;
                    }
                }
            }
        }
        
        class ACSuccessiveDecoder implements DecoderFunction
        {
            @Override
            public void decode(final ComponentIn componentIn, final int[] array) throws IOException {
                int i = Decoder.this.spectralStart;
                final int access$1200 = Decoder.this.spectralEnd;
                int n = 0;
                while (i <= access$1200) {
                    final int n2 = JPEGDecoder.dctZigZag[i];
                    switch (Decoder.this.successiveACState) {
                        case 0: {
                            final int access$1201 = Decoder.this.decodeHuffman(componentIn.huffmanTableAC);
                            final int n3 = access$1201 & 0xF;
                            n = access$1201 >> 4;
                            if (n3 == 0) {
                                if (n < 15) {
                                    Decoder.this.eobrun = Decoder.this.receive(n) + (1 << n);
                                    Decoder.this.successiveACState = 4;
                                    continue;
                                }
                                n = 16;
                                Decoder.this.successiveACState = 1;
                                continue;
                            }
                            else {
                                if (n3 != 1) {
                                    throw new CodecException("invalid ACn encoding");
                                }
                                Decoder.this.successiveACNextValue = Decoder.this.receiveAndExtend(n3);
                                Decoder.this.successiveACState = ((n != 0) ? 2 : 3);
                                continue;
                            }
                            break;
                        }
                        case 1:
                        case 2: {
                            if (array[n2] != 0) {
                                final int n4 = n2;
                                array[n4] += Decoder.this.readBit() << Decoder.this.successive;
                                break;
                            }
                            if (--n == 0) {
                                Decoder.this.successiveACState = ((Decoder.this.successiveACState == 2) ? 3 : 0);
                                break;
                            }
                            break;
                        }
                        case 3: {
                            if (array[n2] != 0) {
                                final int n5 = n2;
                                array[n5] += Decoder.this.readBit() << Decoder.this.successive;
                                break;
                            }
                            array[n2] = Decoder.this.successiveACNextValue << Decoder.this.successive;
                            Decoder.this.successiveACState = 0;
                            break;
                        }
                        case 4: {
                            if (array[n2] != 0) {
                                final int n6 = n2;
                                array[n6] += Decoder.this.readBit() << Decoder.this.successive;
                                break;
                            }
                            break;
                        }
                    }
                    ++i;
                }
                if (Decoder.this.successiveACState == 4) {
                    Decoder.this.eobrun--;
                    if (Decoder.this.eobrun == 0) {
                        Decoder.this.successiveACState = 0;
                    }
                }
            }
        }
    }
    
    interface DecoderFunction
    {
        void decode(final ComponentIn p0, final int[] p1) throws IOException;
    }
    
    public interface ColorSink
    {
        TextureData.ColorSpace allocate(final int p0, final int p1, final TextureData.ColorSpace p2, final int p3) throws RuntimeException;
        
        void store2(final int p0, final int p1, final byte p2, final byte p3);
        
        void storeRGB(final int p0, final int p1, final byte p2, final byte p3, final byte p4);
        
        void storeYCbCr(final int p0, final int p1, final byte p2, final byte p3, final byte p4);
    }
}
