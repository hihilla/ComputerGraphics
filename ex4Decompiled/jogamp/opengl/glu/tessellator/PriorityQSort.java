// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.tessellator;

class PriorityQSort extends PriorityQ
{
    PriorityQHeap heap;
    Object[] keys;
    int[] order;
    int size;
    int max;
    boolean initialized;
    Leq leq;
    
    public PriorityQSort(final Leq leq) {
        this.heap = new PriorityQHeap(leq);
        this.keys = new Object[32];
        this.size = 0;
        this.max = 32;
        this.initialized = false;
        this.leq = leq;
    }
    
    @Override
    void pqDeletePriorityQ() {
        if (this.heap != null) {
            this.heap.pqDeletePriorityQ();
        }
        this.order = null;
        this.keys = null;
    }
    
    private static boolean LT(final Leq leq, final Object o, final Object o2) {
        return !PriorityQ.LEQ(leq, o2, o);
    }
    
    private static boolean GT(final Leq leq, final Object o, final Object o2) {
        return !PriorityQ.LEQ(leq, o, o2);
    }
    
    private static void Swap(final int[] array, final int n, final int n2) {
        final int n3 = array[n];
        array[n] = array[n2];
        array[n2] = n3;
    }
    
    @Override
    boolean pqInit() {
        final Stack[] array = new Stack[50];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new Stack();
        }
        int n = 0;
        int abs = 2016473283;
        this.order = new int[this.size + 1];
        final int p = 0;
        final int r = this.size - 1;
        int n2 = 0;
        for (int j = p; j <= r; ++j) {
            this.order[j] = n2;
            ++n2;
        }
        array[n].p = p;
        array[n].r = r;
        ++n;
        while (--n >= 0) {
            int p2 = array[n].p;
            int k = array[n].r;
            while (k > p2 + 10) {
                abs = Math.abs(abs * 1539415821 + 1);
                final int n3 = p2 + abs % (k - p2 + 1);
                final int n4 = this.order[n3];
                this.order[n3] = this.order[p2];
                this.order[p2] = n4;
                int n5 = p2 - 1;
                int n6 = k + 1;
                while (true) {
                    ++n5;
                    if (!GT(this.leq, this.keys[this.order[n5]], this.keys[n4])) {
                        do {
                            --n6;
                        } while (LT(this.leq, this.keys[this.order[n6]], this.keys[n4]));
                        Swap(this.order, n5, n6);
                        if (n5 >= n6) {
                            break;
                        }
                        continue;
                    }
                }
                Swap(this.order, n5, n6);
                if (n5 - p2 < k - n6) {
                    array[n].p = n6 + 1;
                    array[n].r = k;
                    ++n;
                    k = n5 - 1;
                }
                else {
                    array[n].p = p2;
                    array[n].r = n5 - 1;
                    ++n;
                    p2 = n6 + 1;
                }
            }
            for (int l = p2 + 1; l <= k; ++l) {
                int n7;
                int n8;
                for (n7 = this.order[l], n8 = l; n8 > p2 && LT(this.leq, this.keys[this.order[n8 - 1]], this.keys[n7]); --n8) {
                    this.order[n8] = this.order[n8 - 1];
                }
                this.order[n8] = n7;
            }
        }
        this.max = this.size;
        this.initialized = true;
        this.heap.pqInit();
        return true;
    }
    
    @Override
    int pqInsert(final Object o) {
        if (this.initialized) {
            return this.heap.pqInsert(o);
        }
        final int size = this.size;
        if (++this.size >= this.max) {
            this.max <<= 1;
            final Object[] keys = new Object[this.max];
            System.arraycopy(this.keys, 0, keys, 0, this.keys.length);
            this.keys = keys;
        }
        assert size != Integer.MAX_VALUE;
        this.keys[size] = o;
        return -(size + 1);
    }
    
    @Override
    Object pqExtractMin() {
        if (this.size == 0) {
            return this.heap.pqExtractMin();
        }
        final Object o = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty() && PriorityQ.LEQ(this.leq, this.heap.pqMinimum(), o)) {
            return this.heap.pqExtractMin();
        }
        do {
            --this.size;
        } while (this.size > 0 && this.keys[this.order[this.size - 1]] == null);
        return o;
    }
    
    @Override
    Object pqMinimum() {
        if (this.size == 0) {
            return this.heap.pqMinimum();
        }
        final Object o = this.keys[this.order[this.size - 1]];
        if (!this.heap.pqIsEmpty()) {
            final Object pqMinimum = this.heap.pqMinimum();
            if (PriorityQ.LEQ(this.leq, pqMinimum, o)) {
                return pqMinimum;
            }
        }
        return o;
    }
    
    @Override
    boolean pqIsEmpty() {
        return this.size == 0 && this.heap.pqIsEmpty();
    }
    
    @Override
    void pqDelete(int n) {
        if (n >= 0) {
            this.heap.pqDelete(n);
            return;
        }
        n = -(n + 1);
        assert n < this.max && this.keys[n] != null;
        this.keys[n] = null;
        while (this.size > 0 && this.keys[this.order[this.size - 1]] == null) {
            --this.size;
        }
    }
    
    private static class Stack
    {
        int p;
        int r;
    }
}
