// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.util.Iterator;
import jogamp.opengl.util.pngj.chunks.PngChunkSkipped;
import jogamp.opengl.util.pngj.chunks.PngChunkTextVar;
import jogamp.opengl.util.pngj.chunks.ChunkHelper;
import java.io.IOException;
import jogamp.opengl.util.pngj.chunks.PngChunkIHDR;
import java.util.List;
import jogamp.opengl.util.pngj.chunks.PngChunk;
import jogamp.opengl.util.pngj.chunks.PngChunkIEND;
import java.util.Arrays;
import java.util.zip.Deflater;
import jogamp.opengl.util.pngj.chunks.ChunksList;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import jogamp.opengl.util.pngj.chunks.PngMetadata;
import jogamp.opengl.util.pngj.chunks.ChunksListForWrite;

public class PngWriter
{
    public final ImageInfo imgInfo;
    private final String filename;
    protected int rowNum;
    private final ChunksListForWrite chunksList;
    private final PngMetadata metadata;
    protected int currentChunkGroup;
    protected FilterWriteStrategy filterStrat;
    private int compLevel;
    private boolean shouldCloseStream;
    private PngIDatChunkOutputStream datStream;
    private DeflaterOutputStream datStreamDeflated;
    private int deflaterStrategy;
    private final int[] histox;
    private int idatMaxSize;
    private final OutputStream os;
    protected byte[] rowb;
    protected byte[] rowbfilter;
    protected byte[] rowbprev;
    private boolean unpackedMode;
    
    public PngWriter(final OutputStream outputStream, final ImageInfo imageInfo) {
        this(outputStream, imageInfo, "[NO FILENAME AVAILABLE]");
    }
    
    public PngWriter(final OutputStream os, final ImageInfo imgInfo, final String s) {
        this.rowNum = -1;
        this.currentChunkGroup = -1;
        this.compLevel = 6;
        this.shouldCloseStream = true;
        this.deflaterStrategy = 1;
        this.histox = new int[256];
        this.idatMaxSize = 0;
        this.rowb = null;
        this.rowbfilter = null;
        this.rowbprev = null;
        this.unpackedMode = false;
        this.filename = ((s == null) ? "" : s);
        this.os = os;
        this.imgInfo = imgInfo;
        this.rowb = new byte[imgInfo.bytesPerRow + 1];
        this.rowbprev = new byte[this.rowb.length];
        this.rowbfilter = new byte[this.rowb.length];
        this.chunksList = new ChunksListForWrite(imgInfo);
        this.metadata = new PngMetadata(this.chunksList);
        this.filterStrat = new FilterWriteStrategy(imgInfo, FilterType.FILTER_DEFAULT);
    }
    
    private void init() {
        this.datStream = new PngIDatChunkOutputStream(this.os, this.idatMaxSize);
        final Deflater deflater = new Deflater(this.compLevel);
        deflater.setStrategy(this.deflaterStrategy);
        this.datStreamDeflated = new DeflaterOutputStream(this.datStream, deflater);
        this.writeSignatureAndIHDR();
        this.writeFirstChunks();
    }
    
    private void reportResultsForFilter(final int n, final FilterType filterType, final boolean b) {
        Arrays.fill(this.histox, 0);
        byte b2 = 0;
        for (int i = 1; i <= this.imgInfo.bytesPerRow; ++i) {
            final byte b3 = this.rowbfilter[i];
            if (b3 < 0) {
                b2 -= b3;
            }
            else {
                b2 += b3;
            }
            final int[] histox = this.histox;
            final int n2 = b3 & 0xFF;
            ++histox[n2];
        }
        this.filterStrat.fillResultsForFilter(n, filterType, b2, this.histox, b);
    }
    
    private void writeEndChunk() {
        new PngChunkIEND(this.imgInfo).createRawChunk().writeChunk(this.os);
    }
    
    private void writeFirstChunks() {
        this.currentChunkGroup = 1;
        this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        this.currentChunkGroup = 2;
        final int writeChunks = this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        if (writeChunks > 0 && this.imgInfo.greyscale) {
            throw new PngjOutputException("cannot write palette for this format");
        }
        if (writeChunks == 0 && this.imgInfo.indexed) {
            throw new PngjOutputException("missing palette");
        }
        this.currentChunkGroup = 3;
        this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        this.currentChunkGroup = 4;
    }
    
    private void writeLastChunks() {
        this.currentChunkGroup = 5;
        this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        final List<PngChunk> queuedChunks = this.chunksList.getQueuedChunks();
        if (!queuedChunks.isEmpty()) {
            throw new PngjOutputException(queuedChunks.size() + " chunks were not written! Eg: " + queuedChunks.get(0).toString());
        }
        this.currentChunkGroup = 6;
    }
    
    private void writeSignatureAndIHDR() {
        this.currentChunkGroup = 0;
        PngHelperInternal.writeBytes(this.os, PngHelperInternal.getPngIdSignature());
        final PngChunkIHDR pngChunkIHDR = new PngChunkIHDR(this.imgInfo);
        pngChunkIHDR.setCols(this.imgInfo.cols);
        pngChunkIHDR.setRows(this.imgInfo.rows);
        pngChunkIHDR.setBitspc(this.imgInfo.bitDepth);
        int colormodel = 0;
        if (this.imgInfo.alpha) {
            colormodel += 4;
        }
        if (this.imgInfo.indexed) {
            ++colormodel;
        }
        if (!this.imgInfo.greyscale) {
            colormodel += 2;
        }
        pngChunkIHDR.setColormodel(colormodel);
        pngChunkIHDR.setCompmeth(0);
        pngChunkIHDR.setFilmeth(0);
        pngChunkIHDR.setInterlaced(0);
        pngChunkIHDR.createRawChunk().writeChunk(this.os);
    }
    
    protected void encodeRowFromByte(final byte[] array) {
        if (array.length == this.imgInfo.samplesPerRowPacked) {
            int n = 1;
            if (this.imgInfo.bitDepth <= 8) {
                for (int length = array.length, i = 0; i < length; ++i) {
                    this.rowb[n++] = array[i];
                }
            }
            else {
                for (int length2 = array.length, j = 0; j < length2; ++j) {
                    this.rowb[n] = array[j];
                    n += 2;
                }
            }
        }
        else {
            if (array.length >= this.imgInfo.samplesPerRow && this.unpackedMode) {
                ImageLine.packInplaceByte(this.imgInfo, array, array, false);
            }
            if (this.imgInfo.bitDepth <= 8) {
                int k = 0;
                int n2 = 1;
                while (k < this.imgInfo.samplesPerRowPacked) {
                    this.rowb[n2++] = array[k];
                    ++k;
                }
            }
            else {
                int l = 0;
                int n3 = 1;
                while (l < this.imgInfo.samplesPerRowPacked) {
                    this.rowb[n3++] = array[l];
                    this.rowb[n3++] = 0;
                    ++l;
                }
            }
        }
    }
    
    protected void encodeRowFromInt(final int[] array) {
        if (array.length == this.imgInfo.samplesPerRowPacked) {
            int n = 1;
            if (this.imgInfo.bitDepth <= 8) {
                for (int length = array.length, i = 0; i < length; ++i) {
                    this.rowb[n++] = (byte)array[i];
                }
            }
            else {
                for (final int n2 : array) {
                    this.rowb[n++] = (byte)(n2 >> 8);
                    this.rowb[n++] = (byte)n2;
                }
            }
        }
        else {
            if (array.length >= this.imgInfo.samplesPerRow && this.unpackedMode) {
                ImageLine.packInplaceInt(this.imgInfo, array, array, false);
            }
            if (this.imgInfo.bitDepth <= 8) {
                int k = 0;
                int n3 = 1;
                while (k < this.imgInfo.samplesPerRowPacked) {
                    this.rowb[n3++] = (byte)array[k];
                    ++k;
                }
            }
            else {
                int l = 0;
                int n4 = 1;
                while (l < this.imgInfo.samplesPerRowPacked) {
                    this.rowb[n4++] = (byte)(array[l] >> 8);
                    this.rowb[n4++] = (byte)array[l];
                    ++l;
                }
            }
        }
    }
    
    private void filterRow(final int n) {
        if (this.filterStrat.shouldTestAll(n)) {
            this.filterRowNone();
            this.reportResultsForFilter(n, FilterType.FILTER_NONE, true);
            this.filterRowSub();
            this.reportResultsForFilter(n, FilterType.FILTER_SUB, true);
            this.filterRowUp();
            this.reportResultsForFilter(n, FilterType.FILTER_UP, true);
            this.filterRowAverage();
            this.reportResultsForFilter(n, FilterType.FILTER_AVERAGE, true);
            this.filterRowPaeth();
            this.reportResultsForFilter(n, FilterType.FILTER_PAETH, true);
        }
        final FilterType gimmeFilterType = this.filterStrat.gimmeFilterType(n, true);
        this.rowbfilter[0] = (byte)gimmeFilterType.val;
        switch (gimmeFilterType) {
            case FILTER_NONE: {
                this.filterRowNone();
                break;
            }
            case FILTER_SUB: {
                this.filterRowSub();
                break;
            }
            case FILTER_UP: {
                this.filterRowUp();
                break;
            }
            case FILTER_AVERAGE: {
                this.filterRowAverage();
                break;
            }
            case FILTER_PAETH: {
                this.filterRowPaeth();
                break;
            }
            default: {
                throw new PngjUnsupportedException("Filter type " + gimmeFilterType + " not implemented");
            }
        }
        this.reportResultsForFilter(n, gimmeFilterType, false);
    }
    
    private void prepareEncodeRow(final int n) {
        if (this.datStream == null) {
            this.init();
        }
        ++this.rowNum;
        if (n >= 0 && this.rowNum != n) {
            throw new PngjOutputException("rows must be written in order: expected:" + this.rowNum + " passed:" + n);
        }
        final byte[] rowb = this.rowb;
        this.rowb = this.rowbprev;
        this.rowbprev = rowb;
    }
    
    private void filterAndSend(final int n) {
        this.filterRow(n);
        try {
            this.datStreamDeflated.write(this.rowbfilter, 0, this.imgInfo.bytesPerRow + 1);
        }
        catch (IOException ex) {
            throw new PngjOutputException(ex);
        }
    }
    
    protected void filterRowAverage() {
        for (int bytesPerRow = this.imgInfo.bytesPerRow, n = 1 - this.imgInfo.bytesPixel, i = 1; i <= bytesPerRow; ++i, ++n) {
            this.rowbfilter[i] = (byte)(this.rowb[i] - ((this.rowbprev[i] & 0xFF) + ((n > 0) ? (this.rowb[n] & 0xFF) : 0)) / 2);
        }
    }
    
    protected void filterRowNone() {
        for (int i = 1; i <= this.imgInfo.bytesPerRow; ++i) {
            this.rowbfilter[i] = this.rowb[i];
        }
    }
    
    protected void filterRowPaeth() {
        for (int bytesPerRow = this.imgInfo.bytesPerRow, n = 1 - this.imgInfo.bytesPixel, i = 1; i <= bytesPerRow; ++i, ++n) {
            this.rowbfilter[i] = (byte)PngHelperInternal.filterRowPaeth(this.rowb[i], (n > 0) ? (this.rowb[n] & 0xFF) : 0, this.rowbprev[i] & 0xFF, (n > 0) ? (this.rowbprev[n] & 0xFF) : 0);
        }
    }
    
    protected void filterRowSub() {
        for (int i = 1; i <= this.imgInfo.bytesPixel; ++i) {
            this.rowbfilter[i] = this.rowb[i];
        }
        for (int n = 1, j = this.imgInfo.bytesPixel + 1; j <= this.imgInfo.bytesPerRow; ++j, ++n) {
            this.rowbfilter[j] = (byte)PngHelperInternal.filterRowSub(this.rowb[j], this.rowb[n]);
        }
    }
    
    protected void filterRowUp() {
        for (int i = 1; i <= this.imgInfo.bytesPerRow; ++i) {
            this.rowbfilter[i] = (byte)PngHelperInternal.filterRowUp(this.rowb[i], this.rowbprev[i]);
        }
    }
    
    protected int sumRowbfilter() {
        byte b = 0;
        for (int i = 1; i <= this.imgInfo.bytesPerRow; ++i) {
            if (this.rowbfilter[i] < 0) {
                b -= this.rowbfilter[i];
            }
            else {
                b += this.rowbfilter[i];
            }
        }
        return b;
    }
    
    private void copyChunks(final PngReader pngReader, final int n, final boolean b) {
        final boolean b2 = this.currentChunkGroup >= 4;
        if (b && pngReader.getCurrentChunkGroup() < 6) {
            throw new PngjExceptionInternal("tried to copy last chunks but reader has not ended");
        }
        for (final PngChunk pngChunk : pngReader.getChunksList().getChunks()) {
            if (pngChunk.getChunkGroup() < 4 && b2) {
                continue;
            }
            boolean b3 = false;
            if (pngChunk.crit) {
                if (pngChunk.id.equals("PLTE")) {
                    if (this.imgInfo.indexed && ChunkHelper.maskMatch(n, 1)) {
                        b3 = true;
                    }
                    if (!this.imgInfo.greyscale && ChunkHelper.maskMatch(n, 8)) {
                        b3 = true;
                    }
                }
            }
            else {
                final boolean b4 = pngChunk instanceof PngChunkTextVar;
                final boolean safe = pngChunk.safe;
                if (ChunkHelper.maskMatch(n, 8)) {
                    b3 = true;
                }
                if (safe && ChunkHelper.maskMatch(n, 4)) {
                    b3 = true;
                }
                if (pngChunk.id.equals("tRNS") && ChunkHelper.maskMatch(n, 64)) {
                    b3 = true;
                }
                if (pngChunk.id.equals("pHYs") && ChunkHelper.maskMatch(n, 16)) {
                    b3 = true;
                }
                if (b4 && ChunkHelper.maskMatch(n, 32)) {
                    b3 = true;
                }
                if (ChunkHelper.maskMatch(n, 256) && !ChunkHelper.isUnknown(pngChunk) && !b4 && !pngChunk.id.equals("hIST") && !pngChunk.id.equals("tIME")) {
                    b3 = true;
                }
                if (pngChunk instanceof PngChunkSkipped) {
                    b3 = false;
                }
            }
            if (!b3) {
                continue;
            }
            this.chunksList.queue(PngChunk.cloneChunk(pngChunk, this.imgInfo));
        }
    }
    
    public void copyChunksFirst(final PngReader pngReader, final int n) {
        this.copyChunks(pngReader, n, false);
    }
    
    public void copyChunksLast(final PngReader pngReader, final int n) {
        this.copyChunks(pngReader, n, true);
    }
    
    public double computeCompressionRatio() {
        if (this.currentChunkGroup < 6) {
            throw new PngjOutputException("must be called after end()");
        }
        return this.datStream.getCountFlushed() / ((this.imgInfo.bytesPerRow + 1) * this.imgInfo.rows);
    }
    
    public void end() {
        if (this.rowNum != this.imgInfo.rows - 1) {
            throw new PngjOutputException("all rows have not been written");
        }
        try {
            this.datStreamDeflated.finish();
            this.datStream.flush();
            this.writeLastChunks();
            this.writeEndChunk();
            if (this.shouldCloseStream) {
                this.os.close();
            }
        }
        catch (IOException ex) {
            throw new PngjOutputException(ex);
        }
    }
    
    public ChunksListForWrite getChunksList() {
        return this.chunksList;
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public PngMetadata getMetadata() {
        return this.metadata;
    }
    
    public void setCompLevel(final int compLevel) {
        if (compLevel < 0 || compLevel > 9) {
            throw new PngjOutputException("Compression level invalid (" + compLevel + ") Must be 0..9");
        }
        this.compLevel = compLevel;
    }
    
    public void setFilterType(final FilterType filterType) {
        this.filterStrat = new FilterWriteStrategy(this.imgInfo, filterType);
    }
    
    public void setIdatMaxSize(final int idatMaxSize) {
        this.idatMaxSize = idatMaxSize;
    }
    
    public void setShouldCloseStream(final boolean shouldCloseStream) {
        this.shouldCloseStream = shouldCloseStream;
    }
    
    public void setDeflaterStrategy(final int deflaterStrategy) {
        this.deflaterStrategy = deflaterStrategy;
    }
    
    public void writeRow(final ImageLine imageLine) {
        this.writeRow(imageLine.scanline, imageLine.getRown());
    }
    
    public void writeRow(final ImageLine imageLine, final int n) {
        this.unpackedMode = imageLine.samplesUnpacked;
        if (imageLine.sampleType == ImageLine.SampleType.INT) {
            this.writeRowInt(imageLine.scanline, n);
        }
        else {
            this.writeRowByte(imageLine.scanlineb, n);
        }
    }
    
    public void writeRow(final int[] array) {
        this.writeRow(array, -1);
    }
    
    public void writeRow(final int[] array, final int n) {
        this.writeRowInt(array, n);
    }
    
    public void writeRowInt(final int[] array, final int n) {
        this.prepareEncodeRow(n);
        this.encodeRowFromInt(array);
        this.filterAndSend(n);
    }
    
    public void writeRowByte(final byte[] array, final int n) {
        this.prepareEncodeRow(n);
        this.encodeRowFromByte(array);
        this.filterAndSend(n);
    }
    
    public void writeRowsInt(final int[][] array) {
        for (int i = 0; i < this.imgInfo.rows; ++i) {
            this.writeRowInt(array[i], i);
        }
    }
    
    public void writeRowsByte(final byte[][] array) {
        for (int i = 0; i < this.imgInfo.rows; ++i) {
            this.writeRowByte(array[i], i);
        }
    }
    
    public boolean isUnpackedMode() {
        return this.unpackedMode;
    }
    
    public void setUseUnPackedMode(final boolean unpackedMode) {
        this.unpackedMode = unpackedMode;
    }
}
