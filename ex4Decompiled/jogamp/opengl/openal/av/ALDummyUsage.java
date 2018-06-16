// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.openal.av;

import com.jogamp.openal.JoalVersion;
import com.jogamp.openal.AL;

public class ALDummyUsage
{
    static AL al;
    
    public static void main(final String[] array) {
        System.err.println("JOGL> Hello JOAL");
        System.err.println("JOAL: " + JoalVersion.getInstance().toString());
    }
}
