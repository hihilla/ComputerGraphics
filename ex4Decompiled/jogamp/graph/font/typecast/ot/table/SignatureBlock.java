// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.IOException;
import java.io.DataInput;

public class SignatureBlock
{
    private final int reserved1;
    private final int reserved2;
    private final int signatureLen;
    private final byte[] signature;
    
    protected SignatureBlock(final DataInput dataInput) throws IOException {
        this.reserved1 = dataInput.readUnsignedShort();
        this.reserved2 = dataInput.readUnsignedShort();
        this.signatureLen = dataInput.readInt();
        dataInput.readFully(this.signature = new byte[this.signatureLen]);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.signatureLen; i += 16) {
            if (this.signatureLen - i >= 16) {
                sb.append(new String(this.signature, i, 16)).append("\n");
            }
            else {
                sb.append(new String(this.signature, i, this.signatureLen - i)).append("\n");
            }
        }
        return sb.toString();
    }
}
