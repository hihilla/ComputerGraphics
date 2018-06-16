// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj.chunks;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import jogamp.opengl.util.pngj.PngjException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import jogamp.opengl.util.pngj.PngHelperInternal;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ChunkHelper
{
    public static final String IHDR = "IHDR";
    public static final String PLTE = "PLTE";
    public static final String IDAT = "IDAT";
    public static final String IEND = "IEND";
    public static final byte[] b_IHDR;
    public static final byte[] b_PLTE;
    public static final byte[] b_IDAT;
    public static final byte[] b_IEND;
    public static final String cHRM = "cHRM";
    public static final String gAMA = "gAMA";
    public static final String iCCP = "iCCP";
    public static final String sBIT = "sBIT";
    public static final String sRGB = "sRGB";
    public static final String bKGD = "bKGD";
    public static final String hIST = "hIST";
    public static final String tRNS = "tRNS";
    public static final String pHYs = "pHYs";
    public static final String sPLT = "sPLT";
    public static final String tIME = "tIME";
    public static final String iTXt = "iTXt";
    public static final String tEXt = "tEXt";
    public static final String zTXt = "zTXt";
    private static final ThreadLocal<Inflater> inflaterProvider;
    private static final ThreadLocal<Deflater> deflaterProvider;
    private static byte[] tmpbuffer;
    
    public static byte[] toBytes(final String s) {
        return s.getBytes(PngHelperInternal.charsetLatin1);
    }
    
    public static String toString(final byte[] array) {
        return new String(array, PngHelperInternal.charsetLatin1);
    }
    
    public static String toString(final byte[] array, final int n, final int n2) {
        return new String(array, n, n2, PngHelperInternal.charsetLatin1);
    }
    
    public static byte[] toBytesUTF8(final String s) {
        return s.getBytes(PngHelperInternal.charsetUTF8);
    }
    
    public static String toStringUTF8(final byte[] array) {
        return new String(array, PngHelperInternal.charsetUTF8);
    }
    
    public static String toStringUTF8(final byte[] array, final int n, final int n2) {
        return new String(array, n, n2, PngHelperInternal.charsetUTF8);
    }
    
    public static boolean isCritical(final String s) {
        return Character.isUpperCase(s.charAt(0));
    }
    
    public static boolean isPublic(final String s) {
        return Character.isUpperCase(s.charAt(1));
    }
    
    public static boolean isSafeToCopy(final String s) {
        return !Character.isUpperCase(s.charAt(3));
    }
    
    public static boolean isUnknown(final PngChunk pngChunk) {
        return pngChunk instanceof PngChunkUNKNOWN;
    }
    
    public static int posNullByte(final byte[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean shouldLoad(final String s, final ChunkLoadBehaviour chunkLoadBehaviour) {
        if (isCritical(s)) {
            return true;
        }
        final boolean known = PngChunk.isKnown(s);
        switch (chunkLoadBehaviour) {
            case LOAD_CHUNK_ALWAYS: {
                return true;
            }
            case LOAD_CHUNK_IF_SAFE: {
                return known || isSafeToCopy(s);
            }
            case LOAD_CHUNK_KNOWN: {
                return known;
            }
            case LOAD_CHUNK_NEVER: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
    
    public static final byte[] compressBytes(final byte[] array, final boolean b) {
        return compressBytes(array, 0, array.length, b);
    }
    
    public static byte[] compressBytes(final byte[] array, final int n, final int n2, final boolean b) {
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array, n, n2);
            final InflaterInputStream inflaterInputStream = (InflaterInputStream)(b ? byteArrayInputStream : new InflaterInputStream(byteArrayInputStream, getInflater()));
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DeflaterOutputStream deflaterOutputStream = (DeflaterOutputStream)(b ? new DeflaterOutputStream(byteArrayOutputStream) : byteArrayOutputStream);
            shovelInToOut(inflaterInputStream, deflaterOutputStream);
            inflaterInputStream.close();
            deflaterOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception ex) {
            throw new PngjException(ex);
        }
    }
    
    private static void shovelInToOut(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        synchronized (ChunkHelper.tmpbuffer) {
            int read;
            while ((read = inputStream.read(ChunkHelper.tmpbuffer)) > 0) {
                outputStream.write(ChunkHelper.tmpbuffer, 0, read);
            }
        }
    }
    
    public static boolean maskMatch(final int n, final int n2) {
        return (n & n2) != 0x0;
    }
    
    public static List<PngChunk> filterList(final List<PngChunk> list, final ChunkPredicate chunkPredicate) {
        final ArrayList<PngChunk> list2 = new ArrayList<PngChunk>();
        for (final PngChunk pngChunk : list) {
            if (chunkPredicate.match(pngChunk)) {
                list2.add(pngChunk);
            }
        }
        return list2;
    }
    
    public static int trimList(final List<PngChunk> list, final ChunkPredicate chunkPredicate) {
        final Iterator<PngChunk> iterator = list.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            if (chunkPredicate.match(iterator.next())) {
                iterator.remove();
                ++n;
            }
        }
        return n;
    }
    
    public static final boolean equivalent(final PngChunk pngChunk, final PngChunk pngChunk2) {
        if (pngChunk == pngChunk2) {
            return true;
        }
        if (pngChunk == null || pngChunk2 == null || !pngChunk.id.equals(pngChunk2.id)) {
            return false;
        }
        if (pngChunk.getClass() != pngChunk2.getClass()) {
            return false;
        }
        if (!pngChunk2.allowsMultiple()) {
            return true;
        }
        if (pngChunk instanceof PngChunkTextVar) {
            return ((PngChunkTextVar)pngChunk).getKey().equals(((PngChunkTextVar)pngChunk2).getKey());
        }
        return pngChunk instanceof PngChunkSPLT && ((PngChunkSPLT)pngChunk).getPalName().equals(((PngChunkSPLT)pngChunk2).getPalName());
    }
    
    public static boolean isText(final PngChunk pngChunk) {
        return pngChunk instanceof PngChunkTextVar;
    }
    
    public static Inflater getInflater() {
        final Inflater inflater = ChunkHelper.inflaterProvider.get();
        inflater.reset();
        return inflater;
    }
    
    public static Deflater getDeflater() {
        final Deflater deflater = ChunkHelper.deflaterProvider.get();
        deflater.reset();
        return deflater;
    }
    
    static {
        b_IHDR = toBytes("IHDR");
        b_PLTE = toBytes("PLTE");
        b_IDAT = toBytes("IDAT");
        b_IEND = toBytes("IEND");
        inflaterProvider = new ThreadLocal<Inflater>() {
            @Override
            protected Inflater initialValue() {
                return new Inflater();
            }
        };
        deflaterProvider = new ThreadLocal<Deflater>() {
            @Override
            protected Deflater initialValue() {
                return new Deflater();
            }
        };
        ChunkHelper.tmpbuffer = new byte[4096];
    }
}
