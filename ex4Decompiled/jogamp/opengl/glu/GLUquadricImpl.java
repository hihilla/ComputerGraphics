// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu;

import com.jogamp.opengl.util.ImmModeSink;
import com.jogamp.opengl.util.glsl.ShaderState;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.glu.GLUquadric;

public class GLUquadricImpl implements GLUquadric
{
    private final boolean useGLSL;
    private int drawStyle;
    private int orientation;
    private boolean textureFlag;
    private int normals;
    private boolean immModeSinkEnabled;
    private boolean immModeSinkImmediate;
    public int normalType;
    public GL gl;
    public ShaderState shaderState;
    public int shaderProgram;
    public static final boolean USE_NORM = true;
    public static final boolean USE_TEXT = false;
    private ImmModeSink immModeSink;
    private static final float PI = 3.1415927f;
    private static final float PI_2 = 6.2831855f;
    private static final int CACHE_SIZE = 240;
    
    public GLUquadricImpl(final GL gl, final boolean useGLSL, final ShaderState shaderState, final int shaderProgram) {
        this.immModeSink = null;
        this.gl = gl;
        this.useGLSL = useGLSL;
        this.drawStyle = 100012;
        this.orientation = 100020;
        this.textureFlag = false;
        this.normals = 100000;
        this.normalType = (gl.isGLES1() ? 5120 : 5126);
        this.immModeSinkImmediate = true;
        this.immModeSinkEnabled = !gl.isGL2();
        this.shaderState = shaderState;
        this.shaderProgram = shaderProgram;
        this.replaceImmModeSink();
    }
    
    @Override
    public void enableImmModeSink(final boolean immModeSinkEnabled) {
        if (this.gl.isGL2()) {
            this.immModeSinkEnabled = immModeSinkEnabled;
        }
        else {
            this.immModeSinkEnabled = true;
        }
        if (null == this.immModeSink && this.immModeSinkEnabled) {
            this.replaceImmModeSink();
        }
    }
    
    @Override
    public boolean isImmModeSinkEnabled() {
        return this.immModeSinkEnabled;
    }
    
    @Override
    public void setImmMode(final boolean immModeSinkImmediate) {
        if (this.immModeSinkEnabled) {
            this.immModeSinkImmediate = immModeSinkImmediate;
        }
        else {
            this.immModeSinkImmediate = true;
        }
    }
    
    @Override
    public boolean getImmMode() {
        return this.immModeSinkImmediate;
    }
    
    @Override
    public ImmModeSink replaceImmModeSink() {
        if (!this.immModeSinkEnabled) {
            return null;
        }
        final ImmModeSink immModeSink = this.immModeSink;
        if (this.useGLSL) {
            if (null != this.shaderState) {
                this.immModeSink = ImmModeSink.createGLSL(32, 3, 5126, 0, 5126, 3, this.normalType, 0, 5126, 35044, this.shaderState);
            }
            else {
                this.immModeSink = ImmModeSink.createGLSL(32, 3, 5126, 0, 5126, 3, this.normalType, 0, 5126, 35044, this.shaderProgram);
            }
        }
        else {
            this.immModeSink = ImmModeSink.createFixed(32, 3, 5126, 0, 5126, 3, this.normalType, 0, 5126, 35044);
        }
        return immModeSink;
    }
    
    @Override
    public void resetImmModeSink(final GL gl) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.reset(gl);
        }
    }
    
    public void setDrawStyle(final int drawStyle) {
        this.drawStyle = drawStyle;
    }
    
    public void setNormals(final int normals) {
        this.normals = normals;
    }
    
    public void setOrientation(final int orientation) {
        this.orientation = orientation;
    }
    
    public void setTextureFlag(final boolean textureFlag) {
        this.textureFlag = textureFlag;
    }
    
    public int getDrawStyle() {
        return this.drawStyle;
    }
    
    public int getNormals() {
        return this.normals;
    }
    
    public int getOrientation() {
        return this.orientation;
    }
    
    public boolean getTextureFlag() {
        return this.textureFlag;
    }
    
    public void drawCylinder(final GL gl, final float n, final float n2, final float n3, final int n4, final int n5) {
        float n6;
        if (this.orientation == 100021) {
            n6 = -1.0f;
        }
        else {
            n6 = 1.0f;
        }
        final float n7 = 6.2831855f / n4;
        final float n8 = (n2 - n) / n5;
        final float n9 = n3 / n5;
        final float n10 = (n - n2) / n3;
        if (this.drawStyle == 100010) {
            this.glBegin(gl, 0);
            for (int i = 0; i < n4; ++i) {
                final float cos = this.cos(i * n7);
                final float sin = this.sin(i * n7);
                this.normal3f(gl, cos * n6, sin * n6, n10 * n6);
                float n11 = 0.0f;
                float n12 = n;
                for (int j = 0; j <= n5; ++j) {
                    this.glVertex3f(gl, cos * n12, sin * n12, n11);
                    n11 += n9;
                    n12 += n8;
                }
            }
            this.glEnd(gl);
        }
        else if (this.drawStyle == 100011 || this.drawStyle == 100013) {
            if (this.drawStyle == 100011) {
                float n13 = 0.0f;
                float n14 = n;
                for (int k = 0; k <= n5; ++k) {
                    this.glBegin(gl, 2);
                    for (int l = 0; l < n4; ++l) {
                        final float cos2 = this.cos(l * n7);
                        final float sin2 = this.sin(l * n7);
                        this.normal3f(gl, cos2 * n6, sin2 * n6, n10 * n6);
                        this.glVertex3f(gl, cos2 * n14, sin2 * n14, n13);
                    }
                    this.glEnd(gl);
                    n13 += n9;
                    n14 += n8;
                }
            }
            else if (n != 0.0) {
                this.glBegin(gl, 2);
                for (int n15 = 0; n15 < n4; ++n15) {
                    final float cos3 = this.cos(n15 * n7);
                    final float sin3 = this.sin(n15 * n7);
                    this.normal3f(gl, cos3 * n6, sin3 * n6, n10 * n6);
                    this.glVertex3f(gl, cos3 * n, sin3 * n, 0.0f);
                }
                this.glEnd(gl);
                this.glBegin(gl, 2);
                for (int n16 = 0; n16 < n4; ++n16) {
                    final float cos4 = this.cos(n16 * n7);
                    final float sin4 = this.sin(n16 * n7);
                    this.normal3f(gl, cos4 * n6, sin4 * n6, n10 * n6);
                    this.glVertex3f(gl, cos4 * n2, sin4 * n2, n3);
                }
                this.glEnd(gl);
            }
            this.glBegin(gl, 1);
            for (int n17 = 0; n17 < n4; ++n17) {
                final float cos5 = this.cos(n17 * n7);
                final float sin5 = this.sin(n17 * n7);
                this.normal3f(gl, cos5 * n6, sin5 * n6, n10 * n6);
                this.glVertex3f(gl, cos5 * n, sin5 * n, 0.0f);
                this.glVertex3f(gl, cos5 * n2, sin5 * n2, n3);
            }
            this.glEnd(gl);
        }
        else if (this.drawStyle == 100012) {
            final float n18 = 1.0f / n4;
            final float n19 = 1.0f / n5;
            float n20 = 0.0f;
            float n21 = 0.0f;
            float n22 = n;
            for (int n23 = 0; n23 < n5; ++n23) {
                float n24 = 0.0f;
                this.glBegin(gl, 8);
                for (int n25 = 0; n25 <= n4; ++n25) {
                    float n26;
                    float n27;
                    if (n25 == n4) {
                        n26 = this.sin(0.0f);
                        n27 = this.cos(0.0f);
                    }
                    else {
                        n26 = this.sin(n25 * n7);
                        n27 = this.cos(n25 * n7);
                    }
                    this.normal3f(gl, n26 * n6, n27 * n6, n10 * n6);
                    this.TXTR_COORD(gl, n24, n20);
                    this.glVertex3f(gl, n26 * n22, n27 * n22, n21);
                    this.normal3f(gl, n26 * n6, n27 * n6, n10 * n6);
                    this.TXTR_COORD(gl, n24, n20 + n19);
                    this.glVertex3f(gl, n26 * (n22 + n8), n27 * (n22 + n8), n21 + n9);
                    n24 += n18;
                }
                this.glEnd(gl);
                n22 += n8;
                n20 += n19;
                n21 += n9;
            }
        }
    }
    
    public void drawDisk(final GL gl, final float n, final float n2, final int n3, final int n4) {
        if (this.normals != 100002) {
            if (this.orientation == 100020) {
                this.glNormal3f(gl, 0.0f, 0.0f, 1.0f);
            }
            else {
                this.glNormal3f(gl, 0.0f, 0.0f, -1.0f);
            }
        }
        final float n5 = 6.2831855f / n3;
        final float n6 = (n2 - n) / n4;
        switch (this.drawStyle) {
            case 100012: {
                final float n7 = 2.0f * n2;
                float n8 = n;
                for (int i = 0; i < n4; ++i) {
                    final float n9 = n8 + n6;
                    if (this.orientation == 100020) {
                        this.glBegin(gl, 8);
                        for (int j = 0; j <= n3; ++j) {
                            float n10;
                            if (j == n3) {
                                n10 = 0.0f;
                            }
                            else {
                                n10 = j * n5;
                            }
                            final float sin = this.sin(n10);
                            final float cos = this.cos(n10);
                            this.TXTR_COORD(gl, 0.5f + sin * n9 / n7, 0.5f + cos * n9 / n7);
                            this.glVertex2f(gl, n9 * sin, n9 * cos);
                            this.TXTR_COORD(gl, 0.5f + sin * n8 / n7, 0.5f + cos * n8 / n7);
                            this.glVertex2f(gl, n8 * sin, n8 * cos);
                        }
                        this.glEnd(gl);
                    }
                    else {
                        this.glBegin(gl, 8);
                        for (int k = n3; k >= 0; --k) {
                            float n11;
                            if (k == n3) {
                                n11 = 0.0f;
                            }
                            else {
                                n11 = k * n5;
                            }
                            final float sin2 = this.sin(n11);
                            final float cos2 = this.cos(n11);
                            this.TXTR_COORD(gl, 0.5f - sin2 * n9 / n7, 0.5f + cos2 * n9 / n7);
                            this.glVertex2f(gl, n9 * sin2, n9 * cos2);
                            this.TXTR_COORD(gl, 0.5f - sin2 * n8 / n7, 0.5f + cos2 * n8 / n7);
                            this.glVertex2f(gl, n8 * sin2, n8 * cos2);
                        }
                        this.glEnd(gl);
                    }
                    n8 = n9;
                }
                break;
            }
            case 100011: {
                for (int l = 0; l <= n4; ++l) {
                    final float n12 = n + l * n6;
                    this.glBegin(gl, 2);
                    for (int n13 = 0; n13 < n3; ++n13) {
                        final float n14 = n13 * n5;
                        this.glVertex2f(gl, n12 * this.sin(n14), n12 * this.cos(n14));
                    }
                    this.glEnd(gl);
                }
                for (int n15 = 0; n15 < n3; ++n15) {
                    final float n16 = n15 * n5;
                    final float sin3 = this.sin(n16);
                    final float cos3 = this.cos(n16);
                    this.glBegin(gl, 3);
                    for (int n17 = 0; n17 <= n4; ++n17) {
                        final float n18 = n + n17 * n6;
                        this.glVertex2f(gl, n18 * sin3, n18 * cos3);
                    }
                    this.glEnd(gl);
                }
                break;
            }
            case 100010: {
                this.glBegin(gl, 0);
                for (int n19 = 0; n19 < n3; ++n19) {
                    final float n20 = n19 * n5;
                    final float sin4 = this.sin(n20);
                    final float cos4 = this.cos(n20);
                    for (int n21 = 0; n21 <= n4; ++n21) {
                        final float n22 = n * n21 * n6;
                        this.glVertex2f(gl, n22 * sin4, n22 * cos4);
                    }
                }
                this.glEnd(gl);
                break;
            }
            case 100013: {
                if (n != 0.0) {
                    this.glBegin(gl, 2);
                    for (float n23 = 0.0f; n23 < 6.2831855f; n23 += n5) {
                        this.glVertex2f(gl, n * this.sin(n23), n * this.cos(n23));
                    }
                    this.glEnd(gl);
                }
                this.glBegin(gl, 2);
                for (float n24 = 0.0f; n24 < 6.2831855f; n24 += n5) {
                    this.glVertex2f(gl, n2 * this.sin(n24), n2 * this.cos(n24));
                }
                this.glEnd(gl);
                break;
            }
            default: {}
        }
    }
    
    public void drawPartialDisk(final GL gl, final float n, final float n2, int n3, final int n4, float n5, float n6) {
        final float[] array = new float[240];
        final float[] array2 = new float[240];
        float n7 = 0.0f;
        float n8 = 0.0f;
        if (n3 >= 240) {
            n3 = 239;
        }
        if (n3 < 2 || n4 < 1 || n2 <= 0.0f || n < 0.0f || n > n2) {
            System.err.println("PartialDisk: GLU_INVALID_VALUE");
            return;
        }
        if (n6 < -360.0f) {
            n6 = 360.0f;
        }
        if (n6 > 360.0f) {
            n6 = 360.0f;
        }
        if (n6 < 0.0f) {
            n5 += n6;
            n6 = -n6;
        }
        int n9;
        if (n6 == 360.0f) {
            n9 = n3;
        }
        else {
            n9 = n3 + 1;
        }
        final float n10 = n2 - n;
        final float n11 = n5 / 180.0f * 3.1415927f;
        for (int i = 0; i <= n3; ++i) {
            final float n12 = n11 + 3.1415927f * n6 / 180.0f * i / n3;
            array[i] = this.sin(n12);
            array2[i] = this.cos(n12);
        }
        if (n6 == 360.0f) {
            array[n3] = array[0];
            array2[n3] = array2[0];
        }
        switch (this.normals) {
            case 100000:
            case 100001: {
                if (this.orientation == 100020) {
                    this.glNormal3f(gl, 0.0f, 0.0f, 1.0f);
                    break;
                }
                this.glNormal3f(gl, 0.0f, 0.0f, -1.0f);
                break;
            }
        }
        switch (this.drawStyle) {
            case 100012: {
                int n13;
                if (n == 0.0f) {
                    n13 = n4 - 1;
                    this.glBegin(gl, 6);
                    if (this.textureFlag) {
                        this.glTexCoord2f(gl, 0.5f, 0.5f);
                    }
                    this.glVertex3f(gl, 0.0f, 0.0f, 0.0f);
                    final float n14 = n2 - n10 * ((n4 - 1) / n4);
                    if (this.textureFlag) {
                        n7 = n14 / n2 / 2.0f;
                    }
                    if (this.orientation == 100020) {
                        for (int j = n3; j >= 0; --j) {
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n7 * array[j] + 0.5f, n7 * array2[j] + 0.5f);
                            }
                            this.glVertex3f(gl, n14 * array[j], n14 * array2[j], 0.0f);
                        }
                    }
                    else {
                        for (int k = 0; k <= n3; ++k) {
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n7 * array[k] + 0.5f, n7 * array2[k] + 0.5f);
                            }
                            this.glVertex3f(gl, n14 * array[k], n14 * array2[k], 0.0f);
                        }
                    }
                    this.glEnd(gl);
                }
                else {
                    n13 = n4;
                }
                for (int l = 0; l < n13; ++l) {
                    final float n15 = n2 - n10 * (l / n4);
                    final float n16 = n2 - n10 * ((l + 1) / n4);
                    if (this.textureFlag) {
                        n7 = n15 / n2 / 2.0f;
                        n8 = n16 / n2 / 2.0f;
                    }
                    this.glBegin(gl, 8);
                    for (int n17 = 0; n17 <= n3; ++n17) {
                        if (this.orientation == 100020) {
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n7 * array[n17] + 0.5f, n7 * array2[n17] + 0.5f);
                            }
                            this.glVertex3f(gl, n15 * array[n17], n15 * array2[n17], 0.0f);
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n8 * array[n17] + 0.5f, n8 * array2[n17] + 0.5f);
                            }
                            this.glVertex3f(gl, n16 * array[n17], n16 * array2[n17], 0.0f);
                        }
                        else {
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n8 * array[n17] + 0.5f, n8 * array2[n17] + 0.5f);
                            }
                            this.glVertex3f(gl, n16 * array[n17], n16 * array2[n17], 0.0f);
                            if (this.textureFlag) {
                                this.glTexCoord2f(gl, n7 * array[n17] + 0.5f, n7 * array2[n17] + 0.5f);
                            }
                            this.glVertex3f(gl, n15 * array[n17], n15 * array2[n17], 0.0f);
                        }
                    }
                    this.glEnd(gl);
                }
                break;
            }
            case 100010: {
                this.glBegin(gl, 0);
                for (int n18 = 0; n18 < n9; ++n18) {
                    final float n19 = array[n18];
                    final float n20 = array2[n18];
                    for (int n21 = 0; n21 <= n4; ++n21) {
                        final float n22 = n2 - n10 * (n21 / n4);
                        if (this.textureFlag) {
                            final float n23 = n22 / n2 / 2.0f;
                            this.glTexCoord2f(gl, n23 * array[n18] + 0.5f, n23 * array2[n18] + 0.5f);
                        }
                        this.glVertex3f(gl, n22 * n19, n22 * n20, 0.0f);
                    }
                }
                this.glEnd(gl);
                break;
            }
            case 100011: {
                if (n == n2) {
                    this.glBegin(gl, 3);
                    for (int n24 = 0; n24 <= n3; ++n24) {
                        if (this.textureFlag) {
                            this.glTexCoord2f(gl, array[n24] / 2.0f + 0.5f, array2[n24] / 2.0f + 0.5f);
                        }
                        this.glVertex3f(gl, n * array[n24], n * array2[n24], 0.0f);
                    }
                    this.glEnd(gl);
                    break;
                }
                for (int n25 = 0; n25 <= n4; ++n25) {
                    final float n26 = n2 - n10 * (n25 / n4);
                    if (this.textureFlag) {
                        n7 = n26 / n2 / 2.0f;
                    }
                    this.glBegin(gl, 3);
                    for (int n27 = 0; n27 <= n3; ++n27) {
                        if (this.textureFlag) {
                            this.glTexCoord2f(gl, n7 * array[n27] + 0.5f, n7 * array2[n27] + 0.5f);
                        }
                        this.glVertex3f(gl, n26 * array[n27], n26 * array2[n27], 0.0f);
                    }
                    this.glEnd(gl);
                }
                for (int n28 = 0; n28 < n9; ++n28) {
                    final float n29 = array[n28];
                    final float n30 = array2[n28];
                    this.glBegin(gl, 3);
                    for (int n31 = 0; n31 <= n4; ++n31) {
                        final float n32 = n2 - n10 * (n31 / n4);
                        if (this.textureFlag) {
                            n7 = n32 / n2 / 2.0f;
                        }
                        if (this.textureFlag) {
                            this.glTexCoord2f(gl, n7 * array[n28] + 0.5f, n7 * array2[n28] + 0.5f);
                        }
                        this.glVertex3f(gl, n32 * n29, n32 * n30, 0.0f);
                    }
                    this.glEnd(gl);
                }
                break;
            }
            case 100013: {
                if (n6 < 360.0f) {
                    for (int n33 = 0; n33 <= n3; n33 += n3) {
                        final float n34 = array[n33];
                        final float n35 = array2[n33];
                        this.glBegin(gl, 3);
                        for (int n36 = 0; n36 <= n4; ++n36) {
                            final float n37 = n2 - n10 * (n36 / n4);
                            if (this.textureFlag) {
                                n7 = n37 / n2 / 2.0f;
                                this.glTexCoord2f(gl, n7 * array[n33] + 0.5f, n7 * array2[n33] + 0.5f);
                            }
                            this.glVertex3f(gl, n37 * n34, n37 * n35, 0.0f);
                        }
                        this.glEnd(gl);
                    }
                }
                for (int n38 = 0; n38 <= n4; n38 += n4) {
                    final float n39 = n2 - n10 * (n38 / n4);
                    if (this.textureFlag) {
                        n7 = n39 / n2 / 2.0f;
                    }
                    this.glBegin(gl, 3);
                    for (int n40 = 0; n40 <= n3; ++n40) {
                        if (this.textureFlag) {
                            this.glTexCoord2f(gl, n7 * array[n40] + 0.5f, n7 * array2[n40] + 0.5f);
                        }
                        this.glVertex3f(gl, n39 * array[n40], n39 * array2[n40], 0.0f);
                    }
                    this.glEnd(gl);
                    if (n == n2) {
                        break;
                    }
                }
                break;
            }
        }
    }
    
    public void drawSphere(final GL gl, final float n, final int n2, final int n3) {
        final boolean b = this.normals != 100002;
        float n4;
        if (this.orientation == 100021) {
            n4 = -1.0f;
        }
        else {
            n4 = 1.0f;
        }
        final float n5 = 3.1415927f / n3;
        final float n6 = 6.2831855f / n2;
        if (this.drawStyle == 100012) {
            if (!this.textureFlag) {
                this.glBegin(gl, 6);
                this.glNormal3f(gl, 0.0f, 0.0f, 1.0f);
                this.glVertex3f(gl, 0.0f, 0.0f, n4 * n);
                for (int i = 0; i <= n2; ++i) {
                    final float n7 = (i == n2) ? 0.0f : (i * n6);
                    final float n8 = -this.sin(n7) * this.sin(n5);
                    final float n9 = this.cos(n7) * this.sin(n5);
                    final float n10 = n4 * this.cos(n5);
                    if (b) {
                        this.glNormal3f(gl, n8 * n4, n9 * n4, n10 * n4);
                    }
                    this.glVertex3f(gl, n8 * n, n9 * n, n10 * n);
                }
                this.glEnd(gl);
            }
            final float n11 = 1.0f / n2;
            final float n12 = 1.0f / n3;
            float n13 = 1.0f;
            int n14;
            int n15;
            if (this.textureFlag) {
                n14 = 0;
                n15 = n3;
            }
            else {
                n14 = 1;
                n15 = n3 - 1;
            }
            for (int j = n14; j < n15; ++j) {
                final float n16 = j * n5;
                this.glBegin(gl, 8);
                float n17 = 0.0f;
                for (int k = 0; k <= n2; ++k) {
                    final float n18 = (k == n2) ? 0.0f : (k * n6);
                    final float n19 = -this.sin(n18) * this.sin(n16);
                    final float n20 = this.cos(n18) * this.sin(n16);
                    final float n21 = n4 * this.cos(n16);
                    if (b) {
                        this.glNormal3f(gl, n19 * n4, n20 * n4, n21 * n4);
                    }
                    this.TXTR_COORD(gl, n17, n13);
                    this.glVertex3f(gl, n19 * n, n20 * n, n21 * n);
                    final float n22 = -this.sin(n18) * this.sin(n16 + n5);
                    final float n23 = this.cos(n18) * this.sin(n16 + n5);
                    final float n24 = n4 * this.cos(n16 + n5);
                    if (b) {
                        this.glNormal3f(gl, n22 * n4, n23 * n4, n24 * n4);
                    }
                    this.TXTR_COORD(gl, n17, n13 - n12);
                    n17 += n11;
                    this.glVertex3f(gl, n22 * n, n23 * n, n24 * n);
                }
                this.glEnd(gl);
                n13 -= n12;
            }
            if (!this.textureFlag) {
                this.glBegin(gl, 6);
                this.glNormal3f(gl, 0.0f, 0.0f, -1.0f);
                this.glVertex3f(gl, 0.0f, 0.0f, -n * n4);
                final float n25 = 3.1415927f - n5;
                float n26 = 1.0f;
                for (int l = n2; l >= 0; --l) {
                    final float n27 = (l == n2) ? 0.0f : (l * n6);
                    final float n28 = -this.sin(n27) * this.sin(n25);
                    final float n29 = this.cos(n27) * this.sin(n25);
                    final float n30 = n4 * this.cos(n25);
                    if (b) {
                        this.glNormal3f(gl, n28 * n4, n29 * n4, n30 * n4);
                    }
                    n26 -= n11;
                    this.glVertex3f(gl, n28 * n, n29 * n, n30 * n);
                }
                this.glEnd(gl);
            }
        }
        else if (this.drawStyle == 100011 || this.drawStyle == 100013) {
            for (int n31 = 1; n31 < n3; ++n31) {
                final float n32 = n31 * n5;
                this.glBegin(gl, 2);
                for (int n33 = 0; n33 < n2; ++n33) {
                    final float n34 = n33 * n6;
                    final float n35 = this.cos(n34) * this.sin(n32);
                    final float n36 = this.sin(n34) * this.sin(n32);
                    final float cos = this.cos(n32);
                    if (b) {
                        this.glNormal3f(gl, n35 * n4, n36 * n4, cos * n4);
                    }
                    this.glVertex3f(gl, n35 * n, n36 * n, cos * n);
                }
                this.glEnd(gl);
            }
            for (int n37 = 0; n37 < n2; ++n37) {
                final float n38 = n37 * n6;
                this.glBegin(gl, 3);
                for (int n39 = 0; n39 <= n3; ++n39) {
                    final float n40 = n39 * n5;
                    final float n41 = this.cos(n38) * this.sin(n40);
                    final float n42 = this.sin(n38) * this.sin(n40);
                    final float cos2 = this.cos(n40);
                    if (b) {
                        this.glNormal3f(gl, n41 * n4, n42 * n4, cos2 * n4);
                    }
                    this.glVertex3f(gl, n41 * n, n42 * n, cos2 * n);
                }
                this.glEnd(gl);
            }
        }
        else if (this.drawStyle == 100010) {
            this.glBegin(gl, 0);
            if (b) {
                this.glNormal3f(gl, 0.0f, 0.0f, n4);
            }
            this.glVertex3f(gl, 0.0f, 0.0f, n);
            if (b) {
                this.glNormal3f(gl, 0.0f, 0.0f, -n4);
            }
            this.glVertex3f(gl, 0.0f, 0.0f, -n);
            for (int n43 = 1; n43 < n3 - 1; ++n43) {
                final float n44 = n43 * n5;
                for (int n45 = 0; n45 < n2; ++n45) {
                    final float n46 = n45 * n6;
                    final float n47 = this.cos(n46) * this.sin(n44);
                    final float n48 = this.sin(n46) * this.sin(n44);
                    final float cos3 = this.cos(n44);
                    if (b) {
                        this.glNormal3f(gl, n47 * n4, n48 * n4, cos3 * n4);
                    }
                    this.glVertex3f(gl, n47 * n, n48 * n, cos3 * n);
                }
            }
            this.glEnd(gl);
        }
    }
    
    private final void glBegin(final GL gl, final int n) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.glBegin(n);
        }
        else {
            gl.getGL2().glBegin(n);
        }
    }
    
    private final void glEnd(final GL gl) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.glEnd(gl, this.immModeSinkImmediate);
        }
        else {
            gl.getGL2().glEnd();
        }
    }
    
    private final void glVertex2f(final GL gl, final float n, final float n2) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.glVertex2f(n, n2);
        }
        else {
            gl.getGL2().glVertex2f(n, n2);
        }
    }
    
    private final void glVertex3f(final GL gl, final float n, final float n2, final float n3) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.glVertex3f(n, n2, n3);
        }
        else {
            gl.getGL2().glVertex3f(n, n2, n3);
        }
    }
    
    private final void glNormal3f_s(final GL gl, final float n, final float n2, final float n3) {
        final short n4 = (short)(n * 65535.0f);
        final short n5 = (short)(n2 * 65535.0f);
        final short n6 = (short)(n3 * 65535.0f);
        if (this.immModeSinkEnabled) {
            this.immModeSink.glNormal3s(n4, n5, n6);
        }
        else {
            gl.getGL2().glNormal3s(n4, n5, n6);
        }
    }
    
    private final void glNormal3f_b(final GL gl, final float n, final float n2, final float n3) {
        final byte b = (byte)(n * 255.0f);
        final byte b2 = (byte)(n2 * 255.0f);
        final byte b3 = (byte)(n3 * 255.0f);
        if (this.immModeSinkEnabled) {
            this.immModeSink.glNormal3b(b, b2, b3);
        }
        else {
            gl.getGL2().glNormal3b(b, b2, b3);
        }
    }
    
    private final void glNormal3f(final GL gl, final float n, final float n2, final float n3) {
        switch (this.normalType) {
            case 5126: {
                if (this.immModeSinkEnabled) {
                    this.immModeSink.glNormal3f(n, n2, n3);
                    break;
                }
                gl.getGL2().glNormal3f(n, n2, n3);
                break;
            }
            case 5122: {
                this.glNormal3f_s(gl, n, n2, n3);
                break;
            }
            case 5120: {
                this.glNormal3f_b(gl, n, n2, n3);
                break;
            }
        }
    }
    
    private final void glTexCoord2f(final GL gl, final float n, final float n2) {
        if (this.immModeSinkEnabled) {
            this.immModeSink.glTexCoord2f(n, n2);
        }
        else {
            gl.getGL2().glTexCoord2f(n, n2);
        }
    }
    
    private void normal3f(final GL gl, float n, float n2, float n3) {
        final float n4 = (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        if (n4 > 1.0E-5f) {
            n /= n4;
            n2 /= n4;
            n3 /= n4;
        }
        this.glNormal3f(gl, n, n2, n3);
    }
    
    private final void TXTR_COORD(final GL gl, final float n, final float n2) {
        if (this.textureFlag) {
            this.glTexCoord2f(gl, n, n2);
        }
    }
    
    private float sin(final float n) {
        return (float)Math.sin(n);
    }
    
    private float cos(final float n) {
        return (float)Math.cos(n);
    }
}
