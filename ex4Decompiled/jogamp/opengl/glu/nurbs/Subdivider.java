// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Subdivider
{
    public static final int CULL_TRIVIAL_REJECT = 0;
    public static final int CULL_ACCEPT = 1;
    private static final int MAXARCS = 10;
    Quilt qlist;
    private Renderhints renderhints;
    private Backend backend;
    private int subdivisions;
    private float domain_distance_u_rate;
    private int is_domain_distance_sampling;
    private Bin initialbin;
    private boolean showDegenerate;
    private boolean isArcTypeBezier;
    private Flist tpbrkpts;
    private Flist spbrkpts;
    private int s_index;
    private Arc pjarc;
    private ArcTesselator arctesselator;
    private int t_index;
    private final Flist smbrkpts;
    private float domain_distance_v_rate;
    
    public Subdivider() {
        this.smbrkpts = new Flist();
    }
    
    public void beginQuilts(final Backend backend) {
        this.qlist = null;
        this.renderhints = new Renderhints();
        this.backend = backend;
        this.initialbin = new Bin();
        this.arctesselator = new ArcTesselator();
    }
    
    public void addQuilt(final Quilt quilt) {
        if (this.qlist == null) {
            this.qlist = quilt;
        }
        else {
            quilt.next = this.qlist;
            this.qlist = quilt;
        }
    }
    
    public void endQuilts() {
    }
    
    public void drawSurfaces() {
        this.renderhints.init();
        if (this.qlist == null) {
            return;
        }
        for (Quilt quilt = this.qlist; quilt != null; quilt = quilt.next) {
            if (quilt.isCulled() == 0) {
                this.freejarcs(this.initialbin);
                return;
            }
        }
        final float[] array = new float[2];
        final float[] array2 = new float[2];
        this.spbrkpts = new Flist();
        this.tpbrkpts = new Flist();
        this.qlist.getRange(array, array2, this.spbrkpts, this.tpbrkpts);
        final boolean b = this.is_domain_distance_sampling > 0 && this.renderhints.display_method != 5;
        final boolean b2 = true;
        if (!this.initialbin.isnonempty()) {
            if (!b2) {
                this.makeBorderTrim(array, array2);
            }
        }
        else {
            this.qlist.findRates(this.spbrkpts, this.tpbrkpts, new float[2]);
        }
        this.backend.bgnsurf(this.renderhints.wiretris, this.renderhints.wirequads);
        if (!this.initialbin.isnonempty() && b2) {
            for (int i = this.spbrkpts.start; i < this.spbrkpts.end - 1; ++i) {
                for (int j = this.tpbrkpts.start; j < this.tpbrkpts.end - 1; ++j) {
                    final float[] array3 = new float[2];
                    final float[] array4 = new float[2];
                    array3[0] = this.spbrkpts.pts[i];
                    array4[0] = this.spbrkpts.pts[i + 1];
                    array3[1] = this.tpbrkpts.pts[j];
                    array4[1] = this.tpbrkpts.pts[j + 1];
                    this.qlist.downloadAll(array3, array4, this.backend);
                    int n = (int)(this.domain_distance_u_rate * (array4[0] - array3[0]));
                    int n2 = (int)(this.domain_distance_v_rate * (array4[1] - array3[1]));
                    if (n <= 0) {
                        n = 1;
                    }
                    if (n2 <= 0) {
                        n2 = 1;
                    }
                    this.backend.surfgrid(array3[0], array4[0], n, array4[1], array3[1], n2);
                    this.backend.surfmesh(0, 0, n, n2);
                }
            }
        }
        else {
            this.subdivideInS(this.initialbin);
        }
        this.backend.endsurf();
    }
    
    private void freejarcs(final Bin bin) {
    }
    
    private void subdivideInS(final Bin bin) {
        if (this.renderhints.display_method == 6) {
            this.outline(bin);
            this.freejarcs(bin);
        }
        else {
            this.setArcTypeBezier();
            this.setNonDegenerate();
            this.splitInS(bin, this.spbrkpts.start, this.spbrkpts.end);
        }
    }
    
    private void splitInS(final Bin bin, final int s_index, final int n) {
        if (bin.isnonempty()) {
            if (s_index != n) {
                final int n2 = s_index + (n - s_index) / 2;
                final Bin bin2 = new Bin();
                final Bin bin3 = new Bin();
                this.split(bin, bin2, bin3, 0, this.spbrkpts.pts[n2]);
                this.splitInS(bin2, s_index, n2);
                this.splitInS(bin3, n2 + 1, n);
            }
            else if (s_index == this.spbrkpts.start || s_index == this.spbrkpts.end) {
                this.freejarcs(bin);
            }
            else if (this.renderhints.display_method == 7) {
                this.outline(bin);
                this.freejarcs(bin);
            }
            else {
                this.setArcTypeBezier();
                this.setNonDegenerate();
                this.s_index = s_index;
                this.splitInT(bin, this.tpbrkpts.start, this.tpbrkpts.end);
            }
        }
    }
    
    private void splitInT(final Bin bin, final int t_index, final int n) {
        if (bin.isnonempty()) {
            if (t_index != n) {
                final int n2 = t_index + (n - t_index) / 2;
                final Bin bin2 = new Bin();
                final Bin bin3 = new Bin();
                this.split(bin, bin2, bin3, 1, this.tpbrkpts.pts[n2 + 1]);
                this.splitInT(bin2, t_index, n2);
                this.splitInT(bin3, n2 + 1, n);
            }
            else if (t_index == this.tpbrkpts.start || t_index == this.tpbrkpts.end) {
                this.freejarcs(bin);
            }
            else if (this.renderhints.display_method == 8) {
                this.outline(bin);
                this.freejarcs(bin);
            }
            else {
                this.t_index = t_index;
                this.setArcTypeBezier();
                this.setDegenerate();
                final float[] array = new float[2];
                final float[] array2 = new float[2];
                array[0] = this.spbrkpts.pts[this.s_index - 1];
                array[1] = this.tpbrkpts.pts[this.t_index - 1];
                array2[0] = this.spbrkpts.pts[this.s_index];
                array2[1] = this.tpbrkpts.pts[this.t_index];
                this.qlist.downloadAll(array, array2, this.backend);
                this.samplingSplit(bin, new Patchlist(this.qlist, array, array2), this.renderhints.maxsubdivisions, 0);
                this.setNonDegenerate();
                this.setArcTypeBezier();
            }
        }
    }
    
    private void samplingSplit(final Bin bin, final Patchlist patchlist, final int n, int n2) {
        if (!bin.isnonempty()) {
            return;
        }
        if (patchlist.cullCheck() == 0) {
            this.freejarcs(bin);
            return;
        }
        patchlist.getstepsize();
        if (this.renderhints.display_method == 5) {
            this.tesselation(bin, patchlist);
            this.outline(bin);
            this.freejarcs(bin);
            return;
        }
        this.tesselation(bin, patchlist);
        if (patchlist.needsSamplingSubdivision() && n > 0) {
            if (!patchlist.needsSubdivision(0)) {
                n2 = 1;
            }
            else if (patchlist.needsSubdivision(1)) {
                n2 = 0;
            }
            else {
                n2 = 1 - n2;
            }
            final Bin bin2 = new Bin();
            final Bin bin3 = new Bin();
            final float n3 = (float)((patchlist.pspec[n2].range[0] + patchlist.pspec[n2].range[1]) * 0.5);
            this.split(bin, bin2, bin3, n2, n3);
            final Patchlist patchlist2 = new Patchlist(patchlist, n2, n3);
            this.samplingSplit(bin2, patchlist2, n - 1, n2);
            this.samplingSplit(bin3, patchlist2, n - 1, n2);
        }
        else {
            this.setArcTypePwl();
            this.setDegenerate();
            this.nonSamplingSplit(bin, patchlist, n, n2);
            this.setDegenerate();
            this.setArcTypeBezier();
        }
    }
    
    private void nonSamplingSplit(final Bin bin, final Patchlist patchlist, final int n, int n2) {
        if (patchlist.needsNonSamplingSubdivision() && n > 0) {
            n2 = 1 - n2;
            final Bin bin2 = new Bin();
            final Bin bin3 = new Bin();
            final float n3 = (float)((patchlist.pspec[n2].range[0] + patchlist.pspec[n2].range[1]) * 0.5);
            this.split(bin, bin2, bin3, n2, n3);
            final Patchlist patchlist2 = new Patchlist(patchlist, n2, n3);
            if (bin2.isnonempty()) {
                if (patchlist2.cullCheck() == 0) {
                    this.freejarcs(bin2);
                }
                else {
                    this.nonSamplingSplit(bin2, patchlist2, n - 1, n2);
                }
            }
            if (bin3.isnonempty()) {
                if (patchlist.cullCheck() == 0) {
                    this.freejarcs(bin3);
                }
                else {
                    this.nonSamplingSplit(bin3, patchlist2, n - 1, n2);
                }
            }
        }
        else {
            patchlist.bbox();
            this.backend.patch(patchlist.pspec[0].range[0], patchlist.pspec[0].range[1], patchlist.pspec[1].range[0], patchlist.pspec[1].range[1]);
            if (this.renderhints.display_method == 9) {
                this.outline(bin);
                this.freejarcs(bin);
            }
            else {
                this.setArcTypePwl();
                this.setDegenerate();
                this.findIrregularS(bin);
                this.monosplitInS(bin, this.smbrkpts.start, this.smbrkpts.end);
            }
        }
    }
    
    private void monosplitInS(final Bin bin, final int n, final int n2) {
    }
    
    private void findIrregularS(final Bin bin) {
    }
    
    private void setArcTypePwl() {
    }
    
    private void tesselation(final Bin bin, final Patchlist patchlist) {
    }
    
    private void setDegenerate() {
    }
    
    private void split(final Bin bin, final Bin bin2, final Bin bin3, final int n, final float n2) {
        final Bin bin4 = new Bin();
        final Bin bin5 = new Bin();
        this.partition(bin, bin2, bin4, bin3, bin5, n, n2);
        final int numarcs = bin4.numarcs();
        if (numarcs % 2 == 0) {
            final Arc[] array = new Arc[10];
            CArrayOfArcs cArrayOfArcs;
            if (numarcs >= 10) {
                cArrayOfArcs = new CArrayOfArcs(new Arc[numarcs]);
            }
            else {
                cArrayOfArcs = new CArrayOfArcs(array);
            }
            final CArrayOfArcs cArrayOfArcs2 = new CArrayOfArcs(cArrayOfArcs);
            Arc removearc;
            while ((removearc = bin4.removearc()) != null) {
                cArrayOfArcs2.set(removearc);
                cArrayOfArcs2.pp();
            }
            if (n == 0) {
                new ArcSdirSorter(this).qsort(cArrayOfArcs, numarcs);
                final CArrayOfArcs cArrayOfArcs3 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs3.getPointer() < cArrayOfArcs2.getPointer()) {
                    this.check_s(cArrayOfArcs3.get(), cArrayOfArcs3.getRelative(1));
                    cArrayOfArcs3.raisePointerBy(2);
                }
                final CArrayOfArcs cArrayOfArcs4 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs4.getPointer() < cArrayOfArcs2.getPointer()) {
                    this.join_s(bin2, bin3, cArrayOfArcs4.get(), cArrayOfArcs4.getRelative(1));
                    cArrayOfArcs4.raisePointerBy(2);
                }
                final CArrayOfArcs cArrayOfArcs5 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs5.getPointer() != cArrayOfArcs2.getPointer()) {
                    if (cArrayOfArcs5.get().head()[0] <= n2 && cArrayOfArcs5.get().tail()[0] <= n2) {
                        bin2.addarc(cArrayOfArcs5.get());
                    }
                    else {
                        bin3.addarc(cArrayOfArcs5.get());
                    }
                    cArrayOfArcs5.pp();
                }
            }
            else {
                new ArcTdirSorter(this).qsort(cArrayOfArcs, numarcs);
                final CArrayOfArcs cArrayOfArcs6 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs6.getPointer() < cArrayOfArcs2.getPointer()) {
                    this.check_t(cArrayOfArcs6.get(), cArrayOfArcs6.getRelative(1));
                    cArrayOfArcs6.raisePointerBy(2);
                }
                final CArrayOfArcs cArrayOfArcs7 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs7.getPointer() < cArrayOfArcs2.getPointer()) {
                    this.join_t(bin2, bin3, cArrayOfArcs7.get(), cArrayOfArcs7.getRelative(1));
                    cArrayOfArcs7.raisePointerBy(2);
                }
                final CArrayOfArcs cArrayOfArcs8 = new CArrayOfArcs(cArrayOfArcs);
                while (cArrayOfArcs8.getPointer() != cArrayOfArcs2.getPointer()) {
                    if (cArrayOfArcs8.get().head()[0] <= n2 && cArrayOfArcs8.get().tail()[0] <= n2) {
                        bin2.addarc(cArrayOfArcs8.get());
                    }
                    else {
                        bin3.addarc(cArrayOfArcs8.get());
                    }
                    cArrayOfArcs8.raisePointerBy(2);
                }
            }
            bin5.adopt();
        }
    }
    
    private void join_t(final Bin bin, final Bin bin2, final Arc arc, final Arc arc2) {
    }
    
    private void check_t(final Arc arc, final Arc arc2) {
    }
    
    private void join_s(final Bin bin, final Bin bin2, Arc next, Arc next2) {
        if (!next.getitail()) {
            next = next.next;
        }
        if (!next2.getitail()) {
            next2 = next2.next;
        }
        final float n = next.tail()[0];
        final float n2 = next.tail()[1];
        final float n3 = next2.tail()[1];
        if (n2 == n3) {
            this.simplelink(next, next2);
        }
        else {
            final Arc arc = new Arc(1);
            final Arc arc2 = new Arc(3);
            if (this.isBezierArcType()) {
                this.arctesselator.bezier(arc, n, n, n2, n3);
                this.arctesselator.bezier(arc2, n, n, n3, n2);
            }
            else {
                this.arctesselator.pwl_right(arc, n, n2, n3, 0.0f);
                this.arctesselator.pwl_left(arc, n, n3, n2, 0.0f);
            }
            this.link(next, next2, arc, arc2);
            bin.addarc(arc);
            bin2.addarc(arc2);
        }
    }
    
    private void link(final Arc arc, final Arc arc2, final Arc arc3, final Arc arc4) {
    }
    
    private boolean isBezierArcType() {
        return true;
    }
    
    private void simplelink(final Arc arc, final Arc arc2) {
    }
    
    private void check_s(final Arc arc, final Arc arc2) {
    }
    
    private void partition(final Bin bin, final Bin bin2, final Bin bin3, final Bin bin4, final Bin bin5, final int n, final float n2) {
        final Bin bin6 = new Bin();
        final Bin bin7 = new Bin();
        final Bin bin8 = new Bin();
        final Bin bin9 = new Bin();
        for (Arc arc = bin.removearc(); arc != null; arc = bin.removearc()) {
            final float n3 = arc.tail()[n] - n2;
            final float n4 = arc.head()[n] - n2;
            if (n3 > 0.0f) {
                if (n4 > 0.0f) {
                    bin4.addarc(arc);
                }
                else if (n4 == 0.0f) {
                    bin9.addarc(arc);
                }
                else {
                    switch (this.arc_split(arc, n, n2, 0)) {
                        case 2: {
                            bin9.addarc(arc);
                            bin6.addarc(arc.next);
                            break;
                        }
                        default: {
                            System.out.println("TODO subdivider.partition rest cases");
                            break;
                        }
                    }
                }
            }
            else if (n3 == 0.0f) {
                if (n4 > 0.0f) {
                    bin7.addarc(arc);
                }
                else if (n4 == 0.0f) {
                    bin5.addarc(arc);
                }
                else {
                    bin7.addarc(arc);
                }
            }
            else if (n4 <= 0.0f) {
                if (n4 == 0.0f) {
                    bin8.addarc(arc);
                }
                else {
                    bin2.addarc(arc);
                }
            }
        }
        if (n == 0) {
            this.classify_headonleft_s(bin6, bin3, bin2, n2);
            this.classify_tailonleft_s(bin8, bin3, bin2, n2);
            this.classify_headonright_s(bin7, bin3, bin4, n2);
            this.classify_tailonright_s(bin9, bin3, bin4, n2);
        }
        else {
            this.classify_headonleft_t(bin6, bin3, bin2, n2);
            this.classify_tailonleft_t(bin8, bin3, bin2, n2);
            this.classify_headonright_t(bin7, bin3, bin4, n2);
            this.classify_tailonright_t(bin9, bin3, bin4, n2);
        }
    }
    
    private void classify_tailonright_t(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
    }
    
    private void classify_tailonleft_s(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
        Arc removearc;
        while ((removearc = bin.removearc()) != null) {
            removearc.clearitail();
            final float n2 = removearc.next.head()[0] - n;
            if (n2 > 0.0f) {
                bin2.addarc(removearc);
            }
            else if (n2 < 0.0f) {
                if (this.ccwTurn_sl(removearc, removearc.next)) {
                    bin3.addarc(removearc);
                }
                else {
                    bin2.addarc(removearc);
                }
            }
            else if (removearc.next.tail()[1] > removearc.next.head()[1]) {
                bin2.addarc(removearc);
            }
            else {
                bin3.addarc(removearc);
            }
        }
    }
    
    private void classify_headonright_s(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
        Arc removearc;
        while ((removearc = bin.removearc()) != null) {
            removearc.setitail();
            final float n2 = removearc.prev.tail()[0] - n;
            if (n2 > 0.0f) {
                if (this.ccwTurn_sr(removearc.prev, removearc)) {
                    bin3.addarc(removearc);
                }
                else {
                    bin2.addarc(removearc);
                }
            }
            else if (n2 < 0.0f) {
                bin3.addarc(removearc);
            }
            else if (removearc.prev.tail()[1] > removearc.prev.head()[1]) {
                bin3.addarc(removearc);
            }
            else {
                bin2.addarc(removearc);
            }
        }
    }
    
    private boolean ccwTurn_sr(final Arc arc, final Arc arc2) {
        return false;
    }
    
    private void classify_headonright_t(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
    }
    
    private void classify_tailonleft_t(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
    }
    
    private void classify_headonleft_t(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
        Arc removearc;
        while ((removearc = bin.removearc()) != null) {
            removearc.setitail();
            final float n2 = removearc.prev.tail()[1] - n;
            if (n2 > 0.0f) {
                bin3.addarc(removearc);
            }
            else if (n2 < 0.0f) {
                if (this.ccwTurn_tl(removearc.prev, removearc)) {
                    bin3.addarc(removearc);
                }
                else {
                    bin2.addarc(removearc);
                }
            }
            else if (removearc.prev.tail()[0] > removearc.prev.head()[0]) {
                bin3.addarc(removearc);
            }
            else {
                bin2.addarc(removearc);
            }
        }
    }
    
    private boolean ccwTurn_tl(final Arc arc, final Arc arc2) {
        return false;
    }
    
    private void classify_tailonright_s(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
        Arc removearc;
        while ((removearc = bin.removearc()) != null) {
            removearc.clearitail();
            final float n2 = removearc.next.head()[0] - n;
            if (n2 > 0.0f) {
                if (this.ccwTurn_sr(removearc, removearc.next)) {
                    bin3.addarc(removearc);
                }
                else {
                    bin2.addarc(removearc);
                }
            }
            else if (n2 < 0.0f) {
                bin2.addarc(removearc);
            }
            else if (removearc.next.tail()[1] > removearc.next.head()[1]) {
                bin3.addarc(removearc);
            }
            else {
                bin2.addarc(removearc);
            }
        }
    }
    
    private void classify_headonleft_s(final Bin bin, final Bin bin2, final Bin bin3, final float n) {
        Arc removearc;
        while ((removearc = bin.removearc()) != null) {
            removearc.setitail();
            final float n2 = removearc.prev.tail()[0] - n;
            if (n2 > 0.0f) {
                bin3.addarc(removearc);
            }
            else if (n2 < 0.0f) {
                if (this.ccwTurn_sl(removearc.prev, removearc)) {
                    bin3.addarc(removearc);
                }
                else {
                    bin2.addarc(removearc);
                }
            }
            else if (removearc.prev.tail()[1] > removearc.prev.head()[1]) {
                bin2.addarc(removearc);
            }
            else {
                bin3.addarc(removearc);
            }
        }
    }
    
    private boolean ccwTurn_sl(final Arc arc, final Arc arc2) {
        return false;
    }
    
    private int arc_split(final Arc arc, final int n, final float n2, final int n3) {
        return 0;
    }
    
    private void setNonDegenerate() {
        this.showDegenerate = false;
    }
    
    private void setArcTypeBezier() {
        this.isArcTypeBezier = true;
    }
    
    private void outline(final Bin bin) {
    }
    
    private void makeBorderTrim(final float[] array, final float[] array2) {
        final float n = array[0];
        final float n2 = array2[0];
        final float n3 = array[1];
        final float n4 = array2[1];
        this.pjarc = null;
        final Arc arc = new Arc(4);
        this.arctesselator.bezier(arc, n, n2, n3, n3);
        this.initialbin.addarc(arc);
        this.pjarc = arc.append(this.pjarc);
        final Arc arc2 = new Arc(1);
        this.arctesselator.bezier(arc2, n2, n2, n3, n4);
        this.initialbin.addarc(arc2);
        this.pjarc = arc2.append(this.pjarc);
        final Arc arc3 = new Arc(2);
        this.arctesselator.bezier(arc3, n2, n, n4, n4);
        this.initialbin.addarc(arc3);
        this.pjarc = arc3.append(this.pjarc);
        final Arc arc4 = new Arc(3);
        this.arctesselator.bezier(arc4, n, n, n4, n3);
        this.initialbin.addarc(arc4);
        arc4.append(this.pjarc);
    }
    
    public void drawCurves() {
        final float[] array = { 0.0f };
        final float[] array2 = { 0.0f };
        final Flist flist = new Flist();
        this.qlist.getRange(array, array2, flist);
        this.renderhints.init();
        this.backend.bgncurv();
        for (int i = flist.start; i < flist.end - 1; ++i) {
            final float[] array3 = { 0.0f };
            final float[] array4 = { 0.0f };
            array3[0] = flist.pts[i];
            array4[0] = flist.pts[i + 1];
            this.qlist.downloadAll(array3, array4, this.backend);
            this.samplingSplit(new Curvelist(this.qlist, array3, array4), this.renderhints.maxsubdivisions);
        }
        this.backend.endcurv();
    }
    
    private void samplingSplit(final Curvelist curvelist, final int n) {
        if (curvelist.cullCheck() == 0) {
            return;
        }
        curvelist.getstepsize();
        if (!curvelist.needsSamplingSubdivision() || this.subdivisions <= 0) {
            final int n2 = (int)(1.0f + curvelist.range[2] / curvelist.stepsize);
            this.backend.curvgrid(curvelist.range[0], curvelist.range[1], n2);
            this.backend.curvmesh(0, n2);
        }
    }
    
    public void set_domain_distance_u_rate(final double n) {
        this.domain_distance_u_rate = (float)n;
    }
    
    public void set_domain_distance_v_rate(final double n) {
        this.domain_distance_v_rate = (float)n;
    }
    
    public void set_is_domain_distance_sampling(final int is_domain_distance_sampling) {
        this.is_domain_distance_sampling = is_domain_distance_sampling;
    }
}
