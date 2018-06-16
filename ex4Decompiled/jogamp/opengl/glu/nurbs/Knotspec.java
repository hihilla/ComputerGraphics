// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Knotspec
{
    public CArrayOfFloats inkbegin;
    public CArrayOfFloats inkend;
    public int prestride;
    public int order;
    public Knotspec next;
    public CArrayOfFloats klast;
    CArrayOfFloats kfirst;
    CArrayOfBreakpts bbegin;
    CArrayOfBreakpts bend;
    CArrayOfFloats kleft;
    CArrayOfFloats kright;
    int preoffset;
    int postwidth;
    private CArrayOfFloats sbegin;
    private CArrayOfFloats outkbegin;
    private CArrayOfFloats outkend;
    int prewidth;
    int postoffset;
    public int poststride;
    public int ncoords;
    public boolean istransformed;
    public Knotspec kspectotrans;
    
    public void preselect() {
        (this.klast = new CArrayOfFloats(this.inkend)).lessenPointerBy(this.order);
        final float value = this.klast.get();
        while (this.klast.getPointer() != this.inkend.getPointer() && Knotvector.identical(this.klast.get(), value)) {
            this.klast.pp();
        }
        (this.kfirst = new CArrayOfFloats(this.inkbegin)).raisePointerBy(this.order - 1);
        final float value2 = this.kfirst.get();
        while (this.kfirst.getPointer() != this.inkend.getPointer() && Knotvector.identical(this.kfirst.get(), value2)) {
            this.kfirst.pp();
        }
        final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(this.kfirst);
        cArrayOfFloats.mm();
        while (cArrayOfFloats.getPointer() >= this.inkbegin.getPointer() && Knotvector.identical(value2, cArrayOfFloats.get())) {
            cArrayOfFloats.mm();
        }
        cArrayOfFloats.pp();
        final Breakpt[] array = new Breakpt[this.klast.getPointer() - this.kfirst.getPointer() + 1];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new Breakpt();
        }
        this.bbegin = new CArrayOfBreakpts(array, 0);
        this.bbegin.get().multi = this.kfirst.getPointer() - cArrayOfFloats.getPointer();
        this.bbegin.get().value = value2;
        this.bend = new CArrayOfBreakpts(this.bbegin);
        this.kleft = new CArrayOfFloats(this.kfirst);
        this.kright = new CArrayOfFloats(this.kfirst);
    }
    
    public void select() {
        this.breakpoints();
        this.knots();
        this.factors();
        this.preoffset = this.kleft.getPointer() - (this.inkbegin.getPointer() + this.order);
        this.postwidth = (this.bend.getPointer() - this.bbegin.getPointer()) * this.order;
        this.prewidth = this.outkend.getPointer() - this.outkbegin.getPointer() - this.order;
        this.postoffset = ((this.bbegin.get().def > 1) ? (this.bbegin.get().def - 1) : 0);
    }
    
    private void factors() {
        final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(this.outkend.getArray(), this.outkend.getPointer() - 1 - this.order + this.bend.get().multi);
        CArrayOfFloats cArrayOfFloats2 = null;
        if (this.sbegin != null) {
            cArrayOfFloats2 = new CArrayOfFloats(this.sbegin);
        }
        final CArrayOfBreakpts cArrayOfBreakpts = new CArrayOfBreakpts(this.bend);
        while (cArrayOfBreakpts.getPointer() >= this.bbegin.getPointer()) {
            cArrayOfFloats.lessenPointerBy(cArrayOfBreakpts.get().multi);
            final int n = cArrayOfBreakpts.get().def - 1;
            if (n >= 0) {
                final float value = cArrayOfBreakpts.get().value;
                final CArrayOfFloats cArrayOfFloats3 = new CArrayOfFloats(cArrayOfFloats.getArray(), cArrayOfFloats.getPointer() - n + (this.order - 1));
                final CArrayOfFloats cArrayOfFloats4 = new CArrayOfFloats(cArrayOfFloats3.getArray(), cArrayOfFloats3.getPointer() + n);
                while (cArrayOfFloats4.getPointer() != cArrayOfFloats3.getPointer()) {
                    final CArrayOfFloats cArrayOfFloats5 = new CArrayOfFloats(cArrayOfFloats4);
                    final CArrayOfFloats cArrayOfFloats6 = new CArrayOfFloats(cArrayOfFloats);
                    while (cArrayOfFloats5.getPointer() != cArrayOfFloats3.getPointer()) {
                        cArrayOfFloats2.set((value - cArrayOfFloats6.get()) / (cArrayOfFloats5.get() - cArrayOfFloats6.get()));
                        cArrayOfFloats2.pp();
                        cArrayOfFloats6.mm();
                        cArrayOfFloats5.mm();
                    }
                    cArrayOfFloats4.set(value);
                    cArrayOfFloats4.mm();
                }
            }
            cArrayOfBreakpts.mm();
        }
    }
    
    private void knots() {
        final CArrayOfFloats cArrayOfFloats = new CArrayOfFloats(this.kleft.getArray(), this.kleft.getPointer() - this.order);
        final CArrayOfFloats cArrayOfFloats2 = new CArrayOfFloats(this.kright.getArray(), this.kright.getPointer() + this.bend.get().def);
        this.outkbegin = new CArrayOfFloats(new float[cArrayOfFloats2.getPointer() - cArrayOfFloats.getPointer()], 0);
        final CArrayOfFloats cArrayOfFloats3 = new CArrayOfFloats(this.outkbegin);
        while (cArrayOfFloats.getPointer() != cArrayOfFloats2.getPointer()) {
            cArrayOfFloats3.set(cArrayOfFloats.get());
            cArrayOfFloats.pp();
            cArrayOfFloats3.pp();
        }
        this.outkend = new CArrayOfFloats(cArrayOfFloats3);
    }
    
    private void breakpoints() {
        final CArrayOfBreakpts cArrayOfBreakpts = new CArrayOfBreakpts(this.bbegin);
        final CArrayOfBreakpts cArrayOfBreakpts2 = new CArrayOfBreakpts(this.bend);
        int n = 0;
        cArrayOfBreakpts.get().value = cArrayOfBreakpts2.get().value;
        cArrayOfBreakpts.get().multi = cArrayOfBreakpts2.get().multi;
        this.kleft = new CArrayOfFloats(this.kright);
        while (this.kright.getPointer() != this.klast.getPointer()) {
            if (Knotvector.identical(this.kright.get(), cArrayOfBreakpts.get().value)) {
                final Breakpt value = cArrayOfBreakpts.get();
                ++value.multi;
            }
            else {
                cArrayOfBreakpts.get().def = this.order - cArrayOfBreakpts.get().multi;
                n += cArrayOfBreakpts.get().def * (cArrayOfBreakpts.get().def - 1) / 2;
                cArrayOfBreakpts.pp();
                cArrayOfBreakpts.get().value = this.kright.get();
                cArrayOfBreakpts.get().multi = 1;
            }
            this.kright.pp();
        }
        cArrayOfBreakpts.get().def = this.order - cArrayOfBreakpts.get().multi;
        final int n2 = n + cArrayOfBreakpts.get().def * (cArrayOfBreakpts.get().def - 1) / 2;
        this.bend = new CArrayOfBreakpts(cArrayOfBreakpts);
        if (n2 > 0) {
            this.sbegin = new CArrayOfFloats(new float[n2], 0);
        }
        else {
            this.sbegin = null;
        }
    }
    
    public void copy(final CArrayOfFloats cArrayOfFloats, final CArrayOfFloats cArrayOfFloats2) {
        final CArrayOfFloats cArrayOfFloats3 = new CArrayOfFloats(cArrayOfFloats);
        final CArrayOfFloats cArrayOfFloats4 = new CArrayOfFloats(cArrayOfFloats2);
        cArrayOfFloats3.raisePointerBy(this.preoffset);
        if (this.next != null) {
            while (cArrayOfFloats4.getPointer() != new CArrayOfFloats(cArrayOfFloats4.getArray(), cArrayOfFloats4.getPointer() + this.prewidth).getPointer()) {
                this.next.copy(cArrayOfFloats3, cArrayOfFloats4);
                cArrayOfFloats3.raisePointerBy(this.prestride);
                cArrayOfFloats4.raisePointerBy(this.poststride);
            }
        }
        else {
            while (cArrayOfFloats4.getPointer() != new CArrayOfFloats(cArrayOfFloats4.getArray(), cArrayOfFloats4.getPointer() + this.prewidth).getPointer()) {
                this.pt_io_copy(cArrayOfFloats4, cArrayOfFloats3);
                cArrayOfFloats3.raisePointerBy(this.prestride);
                cArrayOfFloats4.raisePointerBy(this.poststride);
            }
        }
    }
    
    private void pt_io_copy(final CArrayOfFloats cArrayOfFloats, final CArrayOfFloats cArrayOfFloats2) {
        switch (this.ncoords) {
            case 4: {
                cArrayOfFloats.setRelative(3, cArrayOfFloats2.getRelative(3));
            }
            case 3: {
                cArrayOfFloats.setRelative(2, cArrayOfFloats2.getRelative(2));
            }
            case 2: {
                cArrayOfFloats.setRelative(1, cArrayOfFloats2.getRelative(1));
            }
            case 1: {
                cArrayOfFloats.set(cArrayOfFloats2.get());
                break;
            }
        }
    }
    
    public void transform(final CArrayOfFloats cArrayOfFloats) {
        final CArrayOfFloats cArrayOfFloats2 = new CArrayOfFloats(cArrayOfFloats);
        if (this.next != null) {
            if (this.equals(this.kspectotrans)) {
                this.next.transform(cArrayOfFloats2);
            }
            else if (this.istransformed) {
                cArrayOfFloats2.raisePointerBy(this.postoffset);
                while (cArrayOfFloats2.getPointer() != new CArrayOfFloats(cArrayOfFloats2.getArray(), cArrayOfFloats2.getPointer() + this.postwidth).getPointer()) {
                    this.next.transform(cArrayOfFloats2);
                    cArrayOfFloats2.raisePointerBy(this.poststride);
                }
            }
            else {
                while (cArrayOfFloats2.getPointer() != new CArrayOfFloats(cArrayOfFloats2.getArray(), cArrayOfFloats2.getPointer() + this.prewidth).getPointer()) {
                    this.next.transform(cArrayOfFloats2);
                    cArrayOfFloats2.raisePointerBy(this.poststride);
                }
            }
        }
        else if (this.equals(this.kspectotrans)) {
            this.insert(cArrayOfFloats2);
        }
        else if (this.istransformed) {
            cArrayOfFloats2.raisePointerBy(this.postoffset);
            while (cArrayOfFloats2.getPointer() != new CArrayOfFloats(cArrayOfFloats2.getArray(), cArrayOfFloats2.getPointer() + this.postwidth).getPointer()) {
                this.kspectotrans.insert(cArrayOfFloats2);
                cArrayOfFloats2.raisePointerBy(this.poststride);
            }
        }
        else {
            while (cArrayOfFloats2.getPointer() != new CArrayOfFloats(cArrayOfFloats2.getArray(), cArrayOfFloats2.getPointer() + this.prewidth).getPointer()) {
                this.kspectotrans.insert(cArrayOfFloats2);
                cArrayOfFloats2.raisePointerBy(this.poststride);
            }
        }
    }
    
    private void insert(final CArrayOfFloats cArrayOfFloats) {
        CArrayOfFloats cArrayOfFloats2 = null;
        if (this.sbegin != null) {
            cArrayOfFloats2 = new CArrayOfFloats(this.sbegin);
        }
        final CArrayOfFloats cArrayOfFloats3 = new CArrayOfFloats(cArrayOfFloats.getArray(), cArrayOfFloats.getPointer() + this.prewidth - this.poststride);
        final CArrayOfFloats cArrayOfFloats4 = new CArrayOfFloats(cArrayOfFloats.getArray(), cArrayOfFloats.getPointer() + this.postwidth + this.postoffset - this.poststride);
        final CArrayOfBreakpts cArrayOfBreakpts = new CArrayOfBreakpts(this.bend);
        final CArrayOfFloats cArrayOfFloats5 = new CArrayOfFloats(cArrayOfFloats3.getArray(), cArrayOfFloats3.getPointer() - this.poststride * cArrayOfBreakpts.get().def);
        while (cArrayOfFloats3.getPointer() != cArrayOfFloats5.getPointer()) {
            final CArrayOfFloats cArrayOfFloats6 = new CArrayOfFloats(cArrayOfFloats3);
            final CArrayOfFloats cArrayOfFloats7 = new CArrayOfFloats(cArrayOfFloats3.getArray(), cArrayOfFloats3.getPointer() - this.poststride);
            while (cArrayOfFloats7.getPointer() != cArrayOfFloats5.getPointer()) {
                this.pt_oo_sum(cArrayOfFloats6, cArrayOfFloats6, cArrayOfFloats7, cArrayOfFloats2.get(), 1.0 - cArrayOfFloats2.get());
                cArrayOfFloats2.pp();
                cArrayOfFloats6.setPointer(cArrayOfFloats7.getPointer());
                cArrayOfFloats7.lessenPointerBy(this.poststride);
            }
            cArrayOfFloats5.raisePointerBy(this.poststride);
        }
        cArrayOfBreakpts.mm();
        while (cArrayOfBreakpts.getPointer() >= this.bbegin.getPointer()) {
            for (int i = cArrayOfBreakpts.get().multi; i > 0; --i) {
                this.pt_oo_copy(cArrayOfFloats4, cArrayOfFloats3);
                cArrayOfFloats4.lessenPointerBy(this.poststride);
                cArrayOfFloats3.lessenPointerBy(this.poststride);
            }
            final CArrayOfFloats cArrayOfFloats8 = new CArrayOfFloats(cArrayOfFloats3.getArray(), cArrayOfFloats3.getPointer() - this.poststride * cArrayOfBreakpts.get().def);
            while (cArrayOfFloats3.getPointer() != cArrayOfFloats8.getPointer()) {
                this.pt_oo_copy(cArrayOfFloats4, cArrayOfFloats3);
                final CArrayOfFloats cArrayOfFloats9 = new CArrayOfFloats(cArrayOfFloats3);
                final CArrayOfFloats cArrayOfFloats10 = new CArrayOfFloats(cArrayOfFloats3.getArray(), cArrayOfFloats3.getPointer() - this.poststride);
                while (cArrayOfFloats10.getPointer() != cArrayOfFloats8.getPointer()) {
                    this.pt_oo_sum(cArrayOfFloats9, cArrayOfFloats9, cArrayOfFloats10, cArrayOfFloats2.get(), 1.0 - cArrayOfFloats2.get());
                    cArrayOfFloats2.pp();
                    cArrayOfFloats9.setPointer(cArrayOfFloats10.getPointer());
                    cArrayOfFloats10.lessenPointerBy(this.poststride);
                }
                cArrayOfFloats8.raisePointerBy(this.poststride);
                cArrayOfFloats4.lessenPointerBy(this.poststride);
            }
            cArrayOfBreakpts.mm();
        }
    }
    
    private void pt_oo_copy(final CArrayOfFloats cArrayOfFloats, final CArrayOfFloats cArrayOfFloats2) {
        switch (this.ncoords) {
            case 4: {
                cArrayOfFloats.setRelative(3, cArrayOfFloats2.getRelative(3));
            }
            case 3: {
                cArrayOfFloats.setRelative(2, cArrayOfFloats2.getRelative(2));
            }
            case 2: {
                cArrayOfFloats.setRelative(1, cArrayOfFloats2.getRelative(1));
            }
            case 1: {
                cArrayOfFloats.setRelative(0, cArrayOfFloats2.getRelative(0));
                break;
            }
        }
    }
    
    private void pt_oo_sum(final CArrayOfFloats cArrayOfFloats, final CArrayOfFloats cArrayOfFloats2, final CArrayOfFloats cArrayOfFloats3, final float n, final double n2) {
        switch (this.ncoords) {
            case 4: {
                cArrayOfFloats.setRelative(3, (float)(n * cArrayOfFloats2.getRelative(3) + n2 * cArrayOfFloats3.getRelative(3)));
            }
            case 3: {
                cArrayOfFloats.setRelative(2, (float)(n * cArrayOfFloats2.getRelative(2) + n2 * cArrayOfFloats3.getRelative(2)));
            }
            case 2: {
                cArrayOfFloats.setRelative(1, (float)(n * cArrayOfFloats2.getRelative(1) + n2 * cArrayOfFloats3.getRelative(1)));
            }
            case 1: {
                cArrayOfFloats.setRelative(0, (float)(n * cArrayOfFloats2.getRelative(0) + n2 * cArrayOfFloats3.getRelative(0)));
                break;
            }
        }
    }
}
