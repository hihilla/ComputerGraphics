// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

import com.jogamp.opengl.glu.GLUtessellatorCallbackAdapter;
import com.jogamp.opengl.glu.GLUtessellatorCallback;
import com.jogamp.opengl.glu.GLUtessellator;

public class GLUtessellatorImpl implements GLUtessellator
{
    public static final int TESS_MAX_CACHE = 100;
    private int state;
    private GLUhalfEdge lastEdge;
    GLUmesh mesh;
    double[] normal;
    double[] sUnit;
    double[] tUnit;
    private double relTolerance;
    int windingRule;
    boolean fatalError;
    Dict dict;
    PriorityQ pq;
    GLUvertex event;
    boolean flagBoundary;
    boolean boundaryOnly;
    boolean avoidDegenerateTris;
    GLUface lonelyTriList;
    private boolean flushCacheOnNextVertex;
    int cacheCount;
    CachedVertex[] cache;
    private Object polygonData;
    private GLUtessellatorCallback callBegin;
    private GLUtessellatorCallback callEdgeFlag;
    private GLUtessellatorCallback callVertex;
    private GLUtessellatorCallback callEnd;
    private GLUtessellatorCallback callError;
    private GLUtessellatorCallback callCombine;
    private GLUtessellatorCallback callBeginData;
    private GLUtessellatorCallback callEdgeFlagData;
    private GLUtessellatorCallback callVertexData;
    private GLUtessellatorCallback callEndData;
    private GLUtessellatorCallback callErrorData;
    private GLUtessellatorCallback callCombineData;
    private static final double GLU_TESS_DEFAULT_TOLERANCE = 0.0;
    private static GLUtessellatorCallback NULL_CB;
    
    private GLUtessellatorImpl() {
        this.normal = new double[3];
        this.sUnit = new double[3];
        this.tUnit = new double[3];
        this.cache = new CachedVertex[100];
        this.state = 0;
        this.normal[0] = 0.0;
        this.normal[1] = 0.0;
        this.normal[2] = 0.0;
        this.relTolerance = 0.0;
        this.windingRule = 100130;
        this.flagBoundary = false;
        this.boundaryOnly = false;
        this.callBegin = GLUtessellatorImpl.NULL_CB;
        this.callEdgeFlag = GLUtessellatorImpl.NULL_CB;
        this.callVertex = GLUtessellatorImpl.NULL_CB;
        this.callEnd = GLUtessellatorImpl.NULL_CB;
        this.callError = GLUtessellatorImpl.NULL_CB;
        this.callCombine = GLUtessellatorImpl.NULL_CB;
        this.callBeginData = GLUtessellatorImpl.NULL_CB;
        this.callEdgeFlagData = GLUtessellatorImpl.NULL_CB;
        this.callVertexData = GLUtessellatorImpl.NULL_CB;
        this.callEndData = GLUtessellatorImpl.NULL_CB;
        this.callErrorData = GLUtessellatorImpl.NULL_CB;
        this.callCombineData = GLUtessellatorImpl.NULL_CB;
        this.polygonData = null;
        for (int i = 0; i < this.cache.length; ++i) {
            this.cache[i] = new CachedVertex();
        }
    }
    
    public static GLUtessellator gluNewTess() {
        return new GLUtessellatorImpl();
    }
    
    private void makeDormant() {
        if (this.mesh != null) {
            Mesh.__gl_meshDeleteMesh(this.mesh);
        }
        this.state = 0;
        this.lastEdge = null;
        this.mesh = null;
    }
    
    private void requireState(final int n) {
        if (this.state != n) {
            this.gotoState(n);
        }
    }
    
    private void gotoState(final int n) {
        while (this.state != n) {
            if (this.state < n) {
                if (this.state == 0) {
                    this.callErrorOrErrorData(100151);
                    this.gluTessBeginPolygon(null);
                }
                else {
                    if (this.state != 1) {
                        continue;
                    }
                    this.callErrorOrErrorData(100152);
                    this.gluTessBeginContour();
                }
            }
            else if (this.state == 2) {
                this.callErrorOrErrorData(100154);
                this.gluTessEndContour();
            }
            else {
                if (this.state != 1) {
                    continue;
                }
                this.callErrorOrErrorData(100153);
                this.makeDormant();
            }
        }
    }
    
    public void gluDeleteTess() {
        this.requireState(0);
    }
    
    public void gluTessProperty(final int n, final double relTolerance) {
        Label_0135: {
            switch (n) {
                case 100142: {
                    if (relTolerance < 0.0) {
                        break;
                    }
                    if (relTolerance > 1.0) {
                        break;
                    }
                    this.relTolerance = relTolerance;
                    return;
                }
                case 100140: {
                    final int windingRule = (int)relTolerance;
                    if (windingRule != relTolerance) {
                        break;
                    }
                    switch (windingRule) {
                        case 100130:
                        case 100131:
                        case 100132:
                        case 100133:
                        case 100134: {
                            this.windingRule = windingRule;
                            return;
                        }
                        default: {
                            break Label_0135;
                        }
                    }
                    break;
                }
                case 100141: {
                    this.boundaryOnly = (relTolerance != 0.0);
                    return;
                }
                case 100149: {
                    this.avoidDegenerateTris = (relTolerance != 0.0);
                    return;
                }
                default: {
                    this.callErrorOrErrorData(100900);
                    return;
                }
            }
        }
        this.callErrorOrErrorData(100901);
    }
    
    public void gluGetTessProperty(final int n, final double[] array, final int n2) {
        switch (n) {
            case 100142: {
                assert 0.0 <= this.relTolerance && this.relTolerance <= 1.0;
                array[n2] = this.relTolerance;
                break;
            }
            case 100140: {
                assert this.windingRule == 100134;
                array[n2] = this.windingRule;
                break;
            }
            case 100141: {
                assert !this.boundaryOnly;
                array[n2] = (this.boundaryOnly ? 1.0 : 0.0);
                break;
            }
            case 100149: {
                array[n2] = (this.avoidDegenerateTris ? 1.0 : 0.0);
                break;
            }
            default: {
                array[n2] = 0.0;
                this.callErrorOrErrorData(100900);
                break;
            }
        }
    }
    
    public void gluTessNormal(final double n, final double n2, final double n3) {
        this.normal[0] = n;
        this.normal[1] = n2;
        this.normal[2] = n3;
    }
    
    public void gluTessCallback(final int n, final GLUtessellatorCallback glUtessellatorCallback) {
        switch (n) {
            case 100100: {
                this.callBegin = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100106: {
                this.callBeginData = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100104: {
                this.callEdgeFlag = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
                this.flagBoundary = (glUtessellatorCallback != null);
            }
            case 100110: {
                GLUtessellatorCallback null_CB;
                GLUtessellatorCallback callEdgeFlagData;
                if (glUtessellatorCallback == null) {
                    callEdgeFlagData = (null_CB = GLUtessellatorImpl.NULL_CB);
                }
                else {
                    callEdgeFlagData = glUtessellatorCallback;
                    null_CB = glUtessellatorCallback;
                }
                this.callBegin = null_CB;
                this.callEdgeFlagData = callEdgeFlagData;
                this.flagBoundary = (glUtessellatorCallback != null);
            }
            case 100101: {
                this.callVertex = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100107: {
                this.callVertexData = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100102: {
                this.callEnd = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100108: {
                this.callEndData = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100103: {
                this.callError = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100109: {
                this.callErrorData = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100105: {
                this.callCombine = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            case 100111: {
                this.callCombineData = ((glUtessellatorCallback == null) ? GLUtessellatorImpl.NULL_CB : glUtessellatorCallback);
            }
            default: {
                this.callErrorOrErrorData(100900);
            }
        }
    }
    
    private boolean addVertex(final double[] array, final Object data) {
        final GLUhalfEdge lastEdge = this.lastEdge;
        GLUhalfEdge lastEdge2;
        if (lastEdge == null) {
            lastEdge2 = Mesh.__gl_meshMakeEdge(this.mesh);
            if (lastEdge2 == null) {
                return false;
            }
            if (!Mesh.__gl_meshSplice(lastEdge2, lastEdge2.Sym)) {
                return false;
            }
        }
        else {
            Mesh.__gl_meshSplitEdge(lastEdge);
            lastEdge2 = lastEdge.Lnext;
        }
        lastEdge2.Org.data = data;
        lastEdge2.Org.coords[0] = array[0];
        lastEdge2.Org.coords[1] = array[1];
        lastEdge2.Org.coords[2] = array[2];
        lastEdge2.winding = 1;
        lastEdge2.Sym.winding = -1;
        this.lastEdge = lastEdge2;
        return true;
    }
    
    private void cacheVertex(final double[] array, final Object data) {
        if (this.cache[this.cacheCount] == null) {
            this.cache[this.cacheCount] = new CachedVertex();
        }
        final CachedVertex cachedVertex = this.cache[this.cacheCount];
        cachedVertex.data = data;
        cachedVertex.coords[0] = array[0];
        cachedVertex.coords[1] = array[1];
        cachedVertex.coords[2] = array[2];
        ++this.cacheCount;
    }
    
    private boolean flushCache() {
        final CachedVertex[] cache = this.cache;
        this.mesh = Mesh.__gl_meshNewMesh();
        for (int i = 0; i < this.cacheCount; ++i) {
            final CachedVertex cachedVertex = cache[i];
            if (!this.addVertex(cachedVertex.coords, cachedVertex.data)) {
                return false;
            }
        }
        this.cacheCount = 0;
        this.flushCacheOnNextVertex = false;
        return true;
    }
    
    public void gluTessVertex(final double[] array, final int n, final Object o) {
        boolean b = false;
        final double[] array2 = new double[3];
        this.requireState(2);
        if (this.flushCacheOnNextVertex) {
            if (!this.flushCache()) {
                this.callErrorOrErrorData(100902);
                return;
            }
            this.lastEdge = null;
        }
        for (int i = 0; i < 3; ++i) {
            double n2 = array[i + n];
            if (n2 < -1.0E150) {
                n2 = -1.0E150;
                b = true;
            }
            if (n2 > 1.0E150) {
                n2 = 1.0E150;
                b = true;
            }
            array2[i] = n2;
        }
        if (b) {
            this.callErrorOrErrorData(100155);
        }
        if (this.mesh == null) {
            if (this.cacheCount < 100) {
                this.cacheVertex(array2, o);
                return;
            }
            if (!this.flushCache()) {
                this.callErrorOrErrorData(100902);
                return;
            }
        }
        if (!this.addVertex(array2, o)) {
            this.callErrorOrErrorData(100902);
        }
    }
    
    public void gluTessBeginPolygon(final Object polygonData) {
        this.requireState(0);
        this.state = 1;
        this.cacheCount = 0;
        this.flushCacheOnNextVertex = false;
        this.mesh = null;
        this.polygonData = polygonData;
    }
    
    public void gluTessBeginContour() {
        this.requireState(1);
        this.state = 2;
        this.lastEdge = null;
        if (this.cacheCount > 0) {
            this.flushCacheOnNextVertex = true;
        }
    }
    
    public void gluTessEndContour() {
        this.requireState(2);
        this.state = 1;
    }
    
    public void gluTessEndPolygon() {
        try {
            this.requireState(1);
            this.state = 0;
            if (this.mesh == null) {
                if (!this.flagBoundary && Render.__gl_renderCache(this)) {
                    this.polygonData = null;
                    return;
                }
                if (!this.flushCache()) {
                    throw new RuntimeException();
                }
            }
            Normal.__gl_projectPolygon(this);
            if (!Sweep.__gl_computeInterior(this)) {
                throw new RuntimeException();
            }
            final GLUmesh mesh = this.mesh;
            if (!this.fatalError) {
                boolean b;
                if (this.boundaryOnly) {
                    b = TessMono.__gl_meshSetWindingNumber(mesh, 1, true);
                }
                else {
                    b = TessMono.__gl_meshTessellateInterior(mesh, this.avoidDegenerateTris);
                }
                if (!b) {
                    throw new RuntimeException();
                }
                Mesh.__gl_meshCheckMesh(mesh);
                if (this.callBegin != GLUtessellatorImpl.NULL_CB || this.callEnd != GLUtessellatorImpl.NULL_CB || this.callVertex != GLUtessellatorImpl.NULL_CB || this.callEdgeFlag != GLUtessellatorImpl.NULL_CB || this.callBeginData != GLUtessellatorImpl.NULL_CB || this.callEndData != GLUtessellatorImpl.NULL_CB || this.callVertexData != GLUtessellatorImpl.NULL_CB || this.callEdgeFlagData != GLUtessellatorImpl.NULL_CB) {
                    if (this.boundaryOnly) {
                        Render.__gl_renderBoundary(this, mesh);
                    }
                    else {
                        Render.__gl_renderMesh(this, mesh);
                    }
                }
            }
            Mesh.__gl_meshDeleteMesh(mesh);
            this.polygonData = null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.callErrorOrErrorData(100902);
        }
    }
    
    public void gluBeginPolygon() {
        this.gluTessBeginPolygon(null);
        this.gluTessBeginContour();
    }
    
    public void gluNextContour(final int n) {
        this.gluTessEndContour();
        this.gluTessBeginContour();
    }
    
    public void gluEndPolygon() {
        this.gluTessEndContour();
        this.gluTessEndPolygon();
    }
    
    void callBeginOrBeginData(final int n) {
        if (this.callBeginData != GLUtessellatorImpl.NULL_CB) {
            this.callBeginData.beginData(n, this.polygonData);
        }
        else {
            this.callBegin.begin(n);
        }
    }
    
    void callVertexOrVertexData(final Object o) {
        if (this.callVertexData != GLUtessellatorImpl.NULL_CB) {
            this.callVertexData.vertexData(o, this.polygonData);
        }
        else {
            this.callVertex.vertex(o);
        }
    }
    
    void callEdgeFlagOrEdgeFlagData(final boolean b) {
        if (this.callEdgeFlagData != GLUtessellatorImpl.NULL_CB) {
            this.callEdgeFlagData.edgeFlagData(b, this.polygonData);
        }
        else {
            this.callEdgeFlag.edgeFlag(b);
        }
    }
    
    void callEndOrEndData() {
        if (this.callEndData != GLUtessellatorImpl.NULL_CB) {
            this.callEndData.endData(this.polygonData);
        }
        else {
            this.callEnd.end();
        }
    }
    
    void callCombineOrCombineData(final double[] array, final Object[] array2, final float[] array3, final Object[] array4) {
        if (this.callCombineData != GLUtessellatorImpl.NULL_CB) {
            this.callCombineData.combineData(array, array2, array3, array4, this.polygonData);
        }
        else {
            this.callCombine.combine(array, array2, array3, array4);
        }
    }
    
    void callErrorOrErrorData(final int n) {
        if (this.callErrorData != GLUtessellatorImpl.NULL_CB) {
            this.callErrorData.errorData(n, this.polygonData);
        }
        else {
            this.callError.error(n);
        }
    }
    
    static {
        GLUtessellatorImpl.NULL_CB = new GLUtessellatorCallbackAdapter();
    }
}
