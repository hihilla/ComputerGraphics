// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.IOException;
import java.io.DataInput;
import java.io.RandomAccessFile;

public class ResourceFile
{
    private final ResourceHeader header;
    private final ResourceMap map;
    
    public ResourceFile(final RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(0L);
        this.header = new ResourceHeader(randomAccessFile);
        randomAccessFile.seek(this.header.getMapOffset());
        this.map = new ResourceMap(randomAccessFile);
    }
    
    public ResourceMap getResourceMap() {
        return this.map;
    }
    
    public static void main(final String[] array) {
        try {
            final ResourceFile resourceFile = new ResourceFile(new RandomAccessFile("/Library/Fonts/Georgia/..namedfork/rsrc", "r"));
            for (int i = 0; i < resourceFile.getResourceMap().getResourceTypeCount(); ++i) {
                System.out.println(resourceFile.getResourceMap().getResourceType(i).getTypeAsString());
            }
            resourceFile.getResourceMap().getResourceType("sfnt").getReference(0);
            final ResourceType resourceType = resourceFile.getResourceMap().getResourceType("FOND");
            for (int j = 0; j < resourceType.getCount(); ++j) {
                System.out.println(resourceType.getReference(j).getName());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
