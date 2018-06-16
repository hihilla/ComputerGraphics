// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.util.pngj;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

abstract class ProgressiveOutputStream extends ByteArrayOutputStream
{
    private final int size;
    private long countFlushed;
    
    public ProgressiveOutputStream(final int size) {
        this.countFlushed = 0L;
        this.size = size;
    }
    
    @Override
    public final void close() throws IOException {
        this.flush();
        super.close();
    }
    
    @Override
    public final void flush() throws IOException {
        super.flush();
        this.checkFlushBuffer(true);
    }
    
    @Override
    public final void write(final byte[] array, final int n, final int n2) {
        super.write(array, n, n2);
        this.checkFlushBuffer(false);
    }
    
    @Override
    public final void write(final byte[] array) throws IOException {
        super.write(array);
        this.checkFlushBuffer(false);
    }
    
    @Override
    public final void write(final int n) {
        super.write(n);
        this.checkFlushBuffer(false);
    }
    
    @Override
    public final synchronized void reset() {
        super.reset();
    }
    
    private final void checkFlushBuffer(final boolean b) {
        while (b || this.count >= this.size) {
            int n = this.size;
            if (n > this.count) {
                n = this.count;
            }
            if (n == 0) {
                return;
            }
            this.flushBuffer(this.buf, n);
            this.countFlushed += n;
            final int count = this.count - n;
            if ((this.count = count) <= 0) {
                continue;
            }
            System.arraycopy(this.buf, n, this.buf, 0, count);
        }
    }
    
    protected abstract void flushBuffer(final byte[] p0, final int p1);
    
    public long getCountFlushed() {
        return this.countFlushed;
    }
}
