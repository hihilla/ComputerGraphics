// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.io.IOException;
import jogamp.opengl.util.pngj.chunks.ChunkRaw;
import jogamp.opengl.util.pngj.chunks.PngChunkSkipped;
import java.util.Collection;
import jogamp.opengl.util.pngj.chunks.PngChunk;
import jogamp.opengl.util.pngj.chunks.PngChunkIDAT;
import jogamp.opengl.util.pngj.chunks.PngChunkIHDR;
import jogamp.opengl.util.pngj.chunks.ChunkHelper;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.InflaterInputStream;
import java.io.InputStream;
import java.util.zip.Inflater;
import jogamp.opengl.util.pngj.chunks.ChunksList;
import jogamp.opengl.util.pngj.chunks.PngMetadata;
import java.util.HashSet;
import jogamp.opengl.util.pngj.chunks.ChunkLoadBehaviour;

public class PngReader
{
    public final ImageInfo imgInfo;
    protected final String filename;
    private ChunkLoadBehaviour chunkLoadBehaviour;
    private boolean shouldCloseStream;
    private long maxTotalBytesRead;
    private int maxBytesMetadata;
    private int skipChunkMaxSize;
    private String[] skipChunkIds;
    private HashSet<String> skipChunkIdsSet;
    protected final PngMetadata metadata;
    protected final ChunksList chunksList;
    protected ImageLine imgLine;
    protected final int buffersLen;
    protected byte[] rowb;
    protected byte[] rowbprev;
    protected byte[] rowbfilter;
    private final boolean interlaced;
    private final PngDeinterlacer deinterlacer;
    private boolean crcEnabled;
    private boolean unpackedMode;
    private Inflater inflater;
    protected int currentChunkGroup;
    protected int rowNum;
    private long offset;
    private int bytesChunksLoaded;
    protected final InputStream inputStream;
    protected InflaterInputStream idatIstream;
    protected PngIDatChunkInputStream iIdatCstream;
    protected CRC32 crctest;
    
    public PngReader(final InputStream inputStream, final String s) {
        this.chunkLoadBehaviour = ChunkLoadBehaviour.LOAD_CHUNK_ALWAYS;
        this.shouldCloseStream = true;
        this.maxTotalBytesRead = 209715200L;
        this.maxBytesMetadata = 5242880;
        this.skipChunkMaxSize = 2097152;
        this.skipChunkIds = new String[] { "fdAT" };
        this.rowb = null;
        this.rowbprev = null;
        this.rowbfilter = null;
        this.crcEnabled = true;
        this.unpackedMode = false;
        this.inflater = null;
        this.currentChunkGroup = -1;
        this.rowNum = -1;
        this.offset = 0L;
        this.filename = ((s == null) ? "" : s);
        this.inputStream = inputStream;
        this.chunksList = new ChunksList(null);
        this.metadata = new PngMetadata(this.chunksList);
        final byte[] array = new byte[8];
        PngHelperInternal.readBytes(inputStream, array, 0, array.length);
        this.offset += array.length;
        if (!Arrays.equals(array, PngHelperInternal.getPngIdSignature())) {
            throw new PngjInputException("Bad PNG signature");
        }
        this.currentChunkGroup = 0;
        final int int4 = PngHelperInternal.readInt4(inputStream);
        this.offset += 4L;
        if (int4 != 13) {
            throw new PngjInputException("IDHR chunk len != 13 ?? " + int4);
        }
        final byte[] array2 = new byte[4];
        PngHelperInternal.readBytes(inputStream, array2, 0, 4);
        if (!Arrays.equals(array2, ChunkHelper.b_IHDR)) {
            throw new PngjInputException("IHDR not found as first chunk??? [" + ChunkHelper.toString(array2) + "]");
        }
        this.offset += 4L;
        final PngChunkIHDR pngChunkIHDR = (PngChunkIHDR)this.readChunk(array2, int4, false);
        this.imgInfo = new ImageInfo(pngChunkIHDR.getCols(), pngChunkIHDR.getRows(), pngChunkIHDR.getBitspc(), (pngChunkIHDR.getColormodel() & 0x4) != 0x0, pngChunkIHDR.getColormodel() == 0 || pngChunkIHDR.getColormodel() == 4, (pngChunkIHDR.getColormodel() & 0x1) != 0x0);
        this.interlaced = (pngChunkIHDR.getInterlaced() == 1);
        this.deinterlacer = (this.interlaced ? new PngDeinterlacer(this.imgInfo) : null);
        this.buffersLen = this.imgInfo.bytesPerRow + 1;
        if (pngChunkIHDR.getFilmeth() != 0 || pngChunkIHDR.getCompmeth() != 0 || (pngChunkIHDR.getInterlaced() & 0xFFFE) != 0x0) {
            throw new PngjInputException("compression method o filter method or interlaced unrecognized ");
        }
        if (pngChunkIHDR.getColormodel() < 0 || pngChunkIHDR.getColormodel() > 6 || pngChunkIHDR.getColormodel() == 1 || pngChunkIHDR.getColormodel() == 5) {
            throw new PngjInputException("Invalid colormodel " + pngChunkIHDR.getColormodel());
        }
        if (pngChunkIHDR.getBitspc() != 1 && pngChunkIHDR.getBitspc() != 2 && pngChunkIHDR.getBitspc() != 4 && pngChunkIHDR.getBitspc() != 8 && pngChunkIHDR.getBitspc() != 16) {
            throw new PngjInputException("Invalid bit depth " + pngChunkIHDR.getBitspc());
        }
    }
    
    private boolean firstChunksNotYetRead() {
        return this.currentChunkGroup < 1;
    }
    
    private void allocateBuffers() {
        if (this.rowbfilter == null || this.rowbfilter.length < this.buffersLen) {
            this.rowbfilter = new byte[this.buffersLen];
            this.rowb = new byte[this.buffersLen];
            this.rowbprev = new byte[this.buffersLen];
        }
    }
    
    private void readLastAndClose() {
        if (this.currentChunkGroup < 5) {
            try {
                this.idatIstream.close();
            }
            catch (Exception ex) {}
            this.readLastChunks();
        }
        this.close();
    }
    
    private void close() {
        if (this.currentChunkGroup < 6) {
            try {
                this.idatIstream.close();
            }
            catch (Exception ex2) {}
            this.currentChunkGroup = 6;
        }
        if (this.shouldCloseStream) {
            try {
                this.inputStream.close();
            }
            catch (Exception ex) {
                throw new PngjInputException("error closing input stream!", ex);
            }
        }
    }
    
    private void unfilterRow(final int n) {
        final byte b = this.rowbfilter[0];
        final FilterType byVal = FilterType.getByVal(b);
        if (byVal == null) {
            throw new PngjInputException("Filter type " + b + " invalid");
        }
        switch (byVal) {
            case FILTER_NONE: {
                this.unfilterRowNone(n);
                break;
            }
            case FILTER_SUB: {
                this.unfilterRowSub(n);
                break;
            }
            case FILTER_UP: {
                this.unfilterRowUp(n);
                break;
            }
            case FILTER_AVERAGE: {
                this.unfilterRowAverage(n);
                break;
            }
            case FILTER_PAETH: {
                this.unfilterRowPaeth(n);
                break;
            }
            default: {
                throw new PngjInputException("Filter type " + b + " not implemented");
            }
        }
        if (this.crctest != null) {
            this.crctest.update(this.rowb, 1, this.buffersLen - 1);
        }
    }
    
    private void unfilterRowAverage(final int n) {
        for (int n2 = 1 - this.imgInfo.bytesPixel, i = 1; i <= n; ++i, ++n2) {
            this.rowb[i] = (byte)(this.rowbfilter[i] + (((n2 > 0) ? (this.rowb[n2] & 0xFF) : 0) + (this.rowbprev[i] & 0xFF)) / 2);
        }
    }
    
    private void unfilterRowNone(final int n) {
        for (int i = 1; i <= n; ++i) {
            this.rowb[i] = this.rowbfilter[i];
        }
    }
    
    private void unfilterRowPaeth(final int n) {
        for (int n2 = 1 - this.imgInfo.bytesPixel, i = 1; i <= n; ++i, ++n2) {
            this.rowb[i] = (byte)(this.rowbfilter[i] + PngHelperInternal.filterPaethPredictor((n2 > 0) ? (this.rowb[n2] & 0xFF) : 0, this.rowbprev[i] & 0xFF, (n2 > 0) ? (this.rowbprev[n2] & 0xFF) : 0));
        }
    }
    
    private void unfilterRowSub(final int n) {
        for (int i = 1; i <= this.imgInfo.bytesPixel; ++i) {
            this.rowb[i] = this.rowbfilter[i];
        }
        for (int n2 = 1, j = this.imgInfo.bytesPixel + 1; j <= n; ++j, ++n2) {
            this.rowb[j] = (byte)(this.rowbfilter[j] + this.rowb[n2]);
        }
    }
    
    private void unfilterRowUp(final int n) {
        for (int i = 1; i <= n; ++i) {
            this.rowb[i] = (byte)(this.rowbfilter[i] + this.rowbprev[i]);
        }
    }
    
    private final void readFirstChunks() {
        if (!this.firstChunksNotYetRead()) {
            return;
        }
        int int4 = 0;
        int i = 0;
        final byte[] array = new byte[4];
        this.currentChunkGroup = 1;
        while (i == 0) {
            int4 = PngHelperInternal.readInt4(this.inputStream);
            this.offset += 4L;
            if (int4 < 0) {
                break;
            }
            PngHelperInternal.readBytes(this.inputStream, array, 0, 4);
            this.offset += 4L;
            if (Arrays.equals(array, ChunkHelper.b_IDAT)) {
                i = 1;
                this.currentChunkGroup = 4;
                this.chunksList.appendReadChunk(new PngChunkIDAT(this.imgInfo, int4, this.offset - 8L), this.currentChunkGroup);
                break;
            }
            if (Arrays.equals(array, ChunkHelper.b_IEND)) {
                throw new PngjInputException("END chunk found before image data (IDAT) at offset=" + this.offset);
            }
            if (Arrays.equals(array, ChunkHelper.b_PLTE)) {
                this.currentChunkGroup = 2;
            }
            this.readChunk(array, int4, false);
            if (!Arrays.equals(array, ChunkHelper.b_PLTE)) {
                continue;
            }
            this.currentChunkGroup = 3;
        }
        final int n = (i != 0) ? int4 : -1;
        if (n < 0) {
            throw new PngjInputException("first idat chunk not found!");
        }
        this.iIdatCstream = new PngIDatChunkInputStream(this.inputStream, n, this.offset);
        if (this.inflater == null) {
            this.inflater = new Inflater();
        }
        else {
            this.inflater.reset();
        }
        this.idatIstream = new InflaterInputStream(this.iIdatCstream, this.inflater);
        if (!this.crcEnabled) {
            this.iIdatCstream.disableCrcCheck();
        }
    }
    
    void readLastChunks() {
        this.currentChunkGroup = 5;
        if (!this.iIdatCstream.isEnded()) {
            this.iIdatCstream.forceChunkEnd();
        }
        int n = this.iIdatCstream.getLenLastChunk();
        final byte[] idLastChunk = this.iIdatCstream.getIdLastChunk();
        int i = 0;
        int n2 = 1;
        while (i == 0) {
            boolean b = false;
            if (n2 == 0) {
                n = PngHelperInternal.readInt4(this.inputStream);
                this.offset += 4L;
                if (n < 0) {
                    throw new PngjInputException("bad chuck len " + n);
                }
                PngHelperInternal.readBytes(this.inputStream, idLastChunk, 0, 4);
                this.offset += 4L;
            }
            n2 = 0;
            if (Arrays.equals(idLastChunk, ChunkHelper.b_IDAT)) {
                b = true;
            }
            else if (Arrays.equals(idLastChunk, ChunkHelper.b_IEND)) {
                this.currentChunkGroup = 6;
                i = 1;
            }
            this.readChunk(idLastChunk, n, b);
        }
        if (i == 0) {
            throw new PngjInputException("end chunk not found - offset=" + this.offset);
        }
    }
    
    private PngChunk readChunk(final byte[] array, final int n, final boolean b) {
        if (n < 0) {
            throw new PngjInputException("invalid chunk lenght: " + n);
        }
        if (this.skipChunkIdsSet == null && this.currentChunkGroup > 0) {
            this.skipChunkIdsSet = new HashSet<String>(Arrays.asList(this.skipChunkIds));
        }
        final String string = ChunkHelper.toString(array);
        final boolean critical = ChunkHelper.isCritical(string);
        boolean b2 = b;
        if (this.maxTotalBytesRead > 0L && n + this.offset > this.maxTotalBytesRead) {
            throw new PngjInputException("Maximum total bytes to read exceeeded: " + this.maxTotalBytesRead + " offset:" + this.offset + " clen=" + n);
        }
        if (this.currentChunkGroup > 0 && !critical) {
            b2 = (b2 || (this.skipChunkMaxSize > 0 && n >= this.skipChunkMaxSize) || this.skipChunkIdsSet.contains(string) || (this.maxBytesMetadata > 0 && n > this.maxBytesMetadata - this.bytesChunksLoaded) || !ChunkHelper.shouldLoad(string, this.chunkLoadBehaviour));
        }
        PngChunk factory;
        if (b2) {
            PngHelperInternal.skipBytes(this.inputStream, n);
            PngHelperInternal.readInt4(this.inputStream);
            factory = new PngChunkSkipped(string, this.imgInfo, n);
        }
        else {
            final ChunkRaw chunkRaw = new ChunkRaw(n, array, true);
            chunkRaw.readChunkData(this.inputStream, this.crcEnabled || critical);
            factory = PngChunk.factory(chunkRaw, this.imgInfo);
            if (!factory.crit) {
                this.bytesChunksLoaded += chunkRaw.len;
            }
        }
        factory.setOffset(this.offset - 8L);
        this.chunksList.appendReadChunk(factory, this.currentChunkGroup);
        this.offset += n + 4L;
        return factory;
    }
    
    protected void logWarn(final String s) {
        System.err.println(s);
    }
    
    public ChunkLoadBehaviour getChunkLoadBehaviour() {
        return this.chunkLoadBehaviour;
    }
    
    public void setChunkLoadBehaviour(final ChunkLoadBehaviour chunkLoadBehaviour) {
        this.chunkLoadBehaviour = chunkLoadBehaviour;
    }
    
    public ChunksList getChunksList() {
        if (this.firstChunksNotYetRead()) {
            this.readFirstChunks();
        }
        return this.chunksList;
    }
    
    int getCurrentChunkGroup() {
        return this.currentChunkGroup;
    }
    
    public PngMetadata getMetadata() {
        if (this.firstChunksNotYetRead()) {
            this.readFirstChunks();
        }
        return this.metadata;
    }
    
    public ImageLine readRow(final int n) {
        if (this.imgLine == null) {
            this.imgLine = new ImageLine(this.imgInfo, ImageLine.SampleType.INT, this.unpackedMode);
        }
        return (this.imgLine.sampleType != ImageLine.SampleType.BYTE) ? this.readRowInt(n) : this.readRowByte(n);
    }
    
    public ImageLine readRowInt(final int rown) {
        if (this.imgLine == null) {
            this.imgLine = new ImageLine(this.imgInfo, ImageLine.SampleType.INT, this.unpackedMode);
        }
        if (this.imgLine.getRown() == rown) {
            return this.imgLine;
        }
        this.readRowInt(this.imgLine.scanline, rown);
        this.imgLine.setFilterUsed(FilterType.getByVal(this.rowbfilter[0]));
        this.imgLine.setRown(rown);
        return this.imgLine;
    }
    
    public ImageLine readRowByte(final int rown) {
        if (this.imgLine == null) {
            this.imgLine = new ImageLine(this.imgInfo, ImageLine.SampleType.BYTE, this.unpackedMode);
        }
        if (this.imgLine.getRown() == rown) {
            return this.imgLine;
        }
        this.readRowByte(this.imgLine.scanlineb, rown);
        this.imgLine.setFilterUsed(FilterType.getByVal(this.rowbfilter[0]));
        this.imgLine.setRown(rown);
        return this.imgLine;
    }
    
    public final int[] readRow(final int[] array, final int n) {
        return this.readRowInt(array, n);
    }
    
    public final int[] readRowInt(int[] array, final int n) {
        if (array == null) {
            array = new int[this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked];
        }
        if (!this.interlaced) {
            if (n <= this.rowNum) {
                throw new PngjInputException("rows must be read in increasing order: " + n);
            }
            int rowRaw = 0;
            while (this.rowNum < n) {
                rowRaw = this.readRowRaw(this.rowNum + 1);
            }
            this.decodeLastReadRowToInt(array, rowRaw);
        }
        else {
            if (this.deinterlacer.getImageInt() == null) {
                this.deinterlacer.setImageInt(this.readRowsInt().scanlines);
            }
            System.arraycopy(this.deinterlacer.getImageInt()[n], 0, array, 0, this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked);
        }
        return array;
    }
    
    public final byte[] readRowByte(byte[] array, final int n) {
        if (array == null) {
            array = new byte[this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked];
        }
        if (!this.interlaced) {
            if (n <= this.rowNum) {
                throw new PngjInputException("rows must be read in increasing order: " + n);
            }
            int rowRaw = 0;
            while (this.rowNum < n) {
                rowRaw = this.readRowRaw(this.rowNum + 1);
            }
            this.decodeLastReadRowToByte(array, rowRaw);
        }
        else {
            if (this.deinterlacer.getImageByte() == null) {
                this.deinterlacer.setImageByte(this.readRowsByte().scanlinesb);
            }
            System.arraycopy(this.deinterlacer.getImageByte()[n], 0, array, 0, this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked);
        }
        return array;
    }
    
    public ImageLine getRow(final int n) {
        return this.readRow(n);
    }
    
    private void decodeLastReadRowToInt(final int[] array, final int n) {
        if (this.imgInfo.bitDepth <= 8) {
            int i = 0;
            int n2 = 1;
            while (i < n) {
                array[i] = (this.rowb[n2++] & 0xFF);
                ++i;
            }
        }
        else {
            for (int n3 = 0, j = 1; j <= n; array[n3] = ((this.rowb[j++] & 0xFF) << 8) + (this.rowb[j++] & 0xFF), ++n3) {}
        }
        if (this.imgInfo.packed && this.unpackedMode) {
            ImageLine.unpackInplaceInt(this.imgInfo, array, array, false);
        }
    }
    
    private void decodeLastReadRowToByte(final byte[] array, final int n) {
        if (this.imgInfo.bitDepth <= 8) {
            System.arraycopy(this.rowb, 1, array, 0, n);
        }
        else {
            int n2 = 0;
            for (int i = 1; i < n; i += 2) {
                array[n2] = this.rowb[i];
                ++n2;
            }
        }
        if (this.imgInfo.packed && this.unpackedMode) {
            ImageLine.unpackInplaceByte(this.imgInfo, array, array, false);
        }
    }
    
    public ImageLines readRowsInt(final int n, int n2, final int n3) {
        if (n2 < 0) {
            n2 = (this.imgInfo.rows - n) / n3;
        }
        if (n3 < 1 || n < 0 || n2 * n3 + n > this.imgInfo.rows) {
            throw new PngjInputException("bad args");
        }
        final ImageLines imageLines = new ImageLines(this.imgInfo, ImageLine.SampleType.INT, this.unpackedMode, n, n2, n3);
        if (!this.interlaced) {
            for (int i = 0; i < this.imgInfo.rows; ++i) {
                final int rowRaw = this.readRowRaw(i);
                final int imageRowToMatrixRowStrict = imageLines.imageRowToMatrixRowStrict(i);
                if (imageRowToMatrixRowStrict >= 0) {
                    this.decodeLastReadRowToInt(imageLines.scanlines[imageRowToMatrixRowStrict], rowRaw);
                }
            }
        }
        else {
            final int[] array = new int[this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked];
            for (int j = 1; j <= 7; ++j) {
                this.deinterlacer.setPass(j);
                for (int k = 0; k < this.deinterlacer.getRows(); ++k) {
                    final int rowRaw2 = this.readRowRaw(k);
                    final int imageRowToMatrixRowStrict2 = imageLines.imageRowToMatrixRowStrict(this.deinterlacer.getCurrRowReal());
                    if (imageRowToMatrixRowStrict2 >= 0) {
                        this.decodeLastReadRowToInt(array, rowRaw2);
                        this.deinterlacer.deinterlaceInt(array, imageLines.scanlines[imageRowToMatrixRowStrict2], !this.unpackedMode);
                    }
                }
            }
        }
        this.end();
        return imageLines;
    }
    
    public ImageLines readRowsInt() {
        return this.readRowsInt(0, this.imgInfo.rows, 1);
    }
    
    public ImageLines readRowsByte(final int n, int n2, final int n3) {
        if (n2 < 0) {
            n2 = (this.imgInfo.rows - n) / n3;
        }
        if (n3 < 1 || n < 0 || n2 * n3 + n > this.imgInfo.rows) {
            throw new PngjInputException("bad args");
        }
        final ImageLines imageLines = new ImageLines(this.imgInfo, ImageLine.SampleType.BYTE, this.unpackedMode, n, n2, n3);
        if (!this.interlaced) {
            for (int i = 0; i < this.imgInfo.rows; ++i) {
                final int rowRaw = this.readRowRaw(i);
                final int imageRowToMatrixRowStrict = imageLines.imageRowToMatrixRowStrict(i);
                if (imageRowToMatrixRowStrict >= 0) {
                    this.decodeLastReadRowToByte(imageLines.scanlinesb[imageRowToMatrixRowStrict], rowRaw);
                }
            }
        }
        else {
            final byte[] array = new byte[this.unpackedMode ? this.imgInfo.samplesPerRow : this.imgInfo.samplesPerRowPacked];
            for (int j = 1; j <= 7; ++j) {
                this.deinterlacer.setPass(j);
                for (int k = 0; k < this.deinterlacer.getRows(); ++k) {
                    final int rowRaw2 = this.readRowRaw(k);
                    final int imageRowToMatrixRowStrict2 = imageLines.imageRowToMatrixRowStrict(this.deinterlacer.getCurrRowReal());
                    if (imageRowToMatrixRowStrict2 >= 0) {
                        this.decodeLastReadRowToByte(array, rowRaw2);
                        this.deinterlacer.deinterlaceByte(array, imageLines.scanlinesb[imageRowToMatrixRowStrict2], !this.unpackedMode);
                    }
                }
            }
        }
        this.end();
        return imageLines;
    }
    
    public ImageLines readRowsByte() {
        return this.readRowsByte(0, this.imgInfo.rows, 1);
    }
    
    private int readRowRaw(final int n) {
        if (n == 0) {
            if (this.firstChunksNotYetRead()) {
                this.readFirstChunks();
            }
            this.allocateBuffers();
            if (this.interlaced) {
                Arrays.fill(this.rowb, (byte)0);
            }
        }
        int bytesPerRow = this.imgInfo.bytesPerRow;
        if (this.interlaced) {
            if (n < 0 || n > this.deinterlacer.getRows() || (n != 0 && n != this.deinterlacer.getCurrRowSubimg() + 1)) {
                throw new PngjInputException("invalid row in interlaced mode: " + n);
            }
            this.deinterlacer.setRow(n);
            bytesPerRow = (this.imgInfo.bitspPixel * this.deinterlacer.getPixelsToRead() + 7) / 8;
            if (bytesPerRow < 1) {
                throw new PngjExceptionInternal("wtf??");
            }
        }
        else if (n < 0 || n >= this.imgInfo.rows || n != this.rowNum + 1) {
            throw new PngjInputException("invalid row: " + n);
        }
        this.rowNum = n;
        final byte[] rowb = this.rowb;
        this.rowb = this.rowbprev;
        this.rowbprev = rowb;
        PngHelperInternal.readBytes(this.idatIstream, this.rowbfilter, 0, bytesPerRow + 1);
        this.offset = this.iIdatCstream.getOffset();
        if (this.offset < 0L) {
            throw new PngjExceptionInternal("bad offset ??" + this.offset);
        }
        if (this.maxTotalBytesRead > 0L && this.offset >= this.maxTotalBytesRead) {
            throw new PngjInputException("Reading IDAT: Maximum total bytes to read exceeeded: " + this.maxTotalBytesRead + " offset:" + this.offset);
        }
        this.rowb[0] = 0;
        this.unfilterRow(bytesPerRow);
        this.rowb[0] = this.rowbfilter[0];
        if ((this.rowNum == this.imgInfo.rows - 1 && !this.interlaced) || (this.interlaced && this.deinterlacer.isAtLastRow())) {
            this.readLastAndClose();
        }
        return bytesPerRow;
    }
    
    public void readSkippingAllRows() {
        if (this.firstChunksNotYetRead()) {
            this.readFirstChunks();
        }
        this.iIdatCstream.disableCrcCheck();
        this.allocateBuffers();
        try {
            while (this.iIdatCstream.read(this.rowbfilter, 0, this.buffersLen) >= 0) {}
        }
        catch (IOException ex) {
            throw new PngjInputException("error in raw read of IDAT", ex);
        }
        this.offset = this.iIdatCstream.getOffset();
        if (this.offset < 0L) {
            throw new PngjExceptionInternal("bad offset ??" + this.offset);
        }
        if (this.maxTotalBytesRead > 0L && this.offset >= this.maxTotalBytesRead) {
            throw new PngjInputException("Reading IDAT: Maximum total bytes to read exceeeded: " + this.maxTotalBytesRead + " offset:" + this.offset);
        }
        this.readLastAndClose();
    }
    
    public void setMaxTotalBytesRead(final long maxTotalBytesRead) {
        this.maxTotalBytesRead = maxTotalBytesRead;
    }
    
    public long getMaxTotalBytesRead() {
        return this.maxTotalBytesRead;
    }
    
    public void setMaxBytesMetadata(final int maxBytesMetadata) {
        this.maxBytesMetadata = maxBytesMetadata;
    }
    
    public int getMaxBytesMetadata() {
        return this.maxBytesMetadata;
    }
    
    public void setSkipChunkMaxSize(final int skipChunkMaxSize) {
        this.skipChunkMaxSize = skipChunkMaxSize;
    }
    
    public int getSkipChunkMaxSize() {
        return this.skipChunkMaxSize;
    }
    
    public void setSkipChunkIds(final String[] array) {
        this.skipChunkIds = ((array == null) ? new String[0] : array);
    }
    
    public String[] getSkipChunkIds() {
        return this.skipChunkIds;
    }
    
    public void setShouldCloseStream(final boolean shouldCloseStream) {
        this.shouldCloseStream = shouldCloseStream;
    }
    
    public void end() {
        if (this.currentChunkGroup < 6) {
            this.close();
        }
    }
    
    public boolean isInterlaced() {
        return this.interlaced;
    }
    
    public void setUnpackedMode(final boolean unpackedMode) {
        this.unpackedMode = unpackedMode;
    }
    
    public boolean isUnpackedMode() {
        return this.unpackedMode;
    }
    
    public void reuseBuffersFrom(final PngReader pngReader) {
        if (pngReader == null) {
            return;
        }
        if (pngReader.currentChunkGroup < 5) {
            throw new PngjInputException("PngReader to be reused have not yet ended reading pixels");
        }
        if (pngReader.rowbfilter != null && pngReader.rowbfilter.length >= this.buffersLen) {
            this.rowbfilter = pngReader.rowbfilter;
            this.rowb = pngReader.rowb;
            this.rowbprev = pngReader.rowbprev;
        }
        this.inflater = pngReader.inflater;
    }
    
    public void setCrcCheckDisabled() {
        this.crcEnabled = false;
    }
    
    long getCrctestVal() {
        return this.crctest.getValue();
    }
    
    void initCrctest() {
        this.crctest = new CRC32();
    }
    
    @Override
    public String toString() {
        return "filename=" + this.filename + " " + this.imgInfo.toString();
    }
}
