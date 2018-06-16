// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.lang.reflect.Array;
import java.io.PrintStream;

public class LFRingbuffer<T> implements Ringbuffer<T>
{
    private final Object syncRead;
    private final Object syncWrite;
    private final Object syncGlobal;
    private volatile T[] array;
    private volatile int capacityPlusOne;
    private volatile int readPos;
    private volatile int writePos;
    private volatile int size;
    
    @Override
    public final String toString() {
        return "LFRingbuffer<?>[filled " + this.size + " / " + (this.capacityPlusOne - 1) + ", writePos " + this.writePos + ", readPos " + this.readPos + "]";
    }
    
    @Override
    public final void dump(final PrintStream printStream, final String s) {
        printStream.println(s + " " + this.toString() + " {");
        for (int i = 0; i < this.capacityPlusOne; ++i) {
            printStream.println("\t[" + i + "]: " + this.array[i]);
        }
        printStream.println("}");
    }
    
    public LFRingbuffer(final T[] array) throws IllegalArgumentException {
        this.syncRead = new Object();
        this.syncWrite = new Object();
        this.syncGlobal = new Object();
        this.capacityPlusOne = array.length + 1;
        this.array = newArray(array.getClass(), this.capacityPlusOne);
        this.resetImpl(true, array);
    }
    
    public LFRingbuffer(final Class<? extends T[]> clazz, final int n) {
        this.syncRead = new Object();
        this.syncWrite = new Object();
        this.syncGlobal = new Object();
        this.capacityPlusOne = n + 1;
        this.array = newArray((Class<? extends Object[]>)clazz, this.capacityPlusOne);
        this.resetImpl(false, null);
    }
    
    @Override
    public final int capacity() {
        return this.capacityPlusOne - 1;
    }
    
    @Override
    public final void clear() {
        synchronized (this.syncGlobal) {
            this.resetImpl(false, null);
            for (int i = 0; i < this.capacityPlusOne; ++i) {
                this.array[i] = null;
            }
        }
    }
    
    @Override
    public final void resetFull(final T[] array) throws IllegalArgumentException {
        this.resetImpl(true, array);
    }
    
    private final void resetImpl(final boolean b, final T[] array) throws IllegalArgumentException {
        synchronized (this.syncGlobal) {
            if (null != array) {
                if (array.length != this.capacityPlusOne - 1) {
                    throw new IllegalArgumentException("copyFrom array length " + array.length + " != capacity " + this);
                }
                System.arraycopy(array, 0, this.array, 0, array.length);
                this.array[this.capacityPlusOne - 1] = null;
            }
            else if (b) {
                throw new IllegalArgumentException("copyFrom array is null");
            }
            this.readPos = this.capacityPlusOne - 1;
            if (b) {
                this.writePos = this.readPos - 1;
                this.size = this.capacityPlusOne - 1;
            }
            else {
                this.writePos = this.readPos;
                this.size = 0;
            }
        }
    }
    
    @Override
    public final int size() {
        return this.size;
    }
    
    @Override
    public final int getFreeSlots() {
        return this.capacityPlusOne - 1 - this.size;
    }
    
    @Override
    public final boolean isEmpty() {
        return 0 == this.size;
    }
    
    @Override
    public final boolean isFull() {
        return this.capacityPlusOne - 1 == this.size;
    }
    
    @Override
    public final T get() {
        try {
            return this.getImpl(false, false);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public final T getBlocking() throws InterruptedException {
        return this.getImpl(true, false);
    }
    
    @Override
    public final T peek() {
        try {
            return this.getImpl(false, true);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public final T peekBlocking() throws InterruptedException {
        return this.getImpl(true, true);
    }
    
    private final T getImpl(final boolean b, final boolean b2) throws InterruptedException {
        final int i = this.readPos;
        if (i == this.writePos) {
            if (!b) {
                return null;
            }
            synchronized (this.syncRead) {
                while (i == this.writePos) {
                    this.syncRead.wait();
                }
            }
        }
        final int readPos = (i + 1) % this.capacityPlusOne;
        final Object o = this.array[readPos];
        if (!b2) {
            this.array[readPos] = null;
            synchronized (this.syncWrite) {
                --this.size;
                this.readPos = readPos;
                this.syncWrite.notifyAll();
            }
        }
        return (T)o;
    }
    
    @Override
    public final boolean put(final T t) {
        try {
            return this.putImpl(t, false, false);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public final void putBlocking(final T t) throws InterruptedException {
        if (!this.putImpl(t, false, true)) {
            throw new InternalError("Blocking put failed: " + this);
        }
    }
    
    @Override
    public final boolean putSame(final boolean b) throws InterruptedException {
        return this.putImpl(null, true, b);
    }
    
    private final boolean putImpl(final T t, final boolean b, final boolean b2) throws InterruptedException {
        final int i = (this.writePos + 1) % this.capacityPlusOne;
        if (i == this.readPos) {
            if (!b2) {
                return false;
            }
            synchronized (this.syncWrite) {
                while (i == this.readPos) {
                    this.syncWrite.wait();
                }
            }
        }
        if (!b) {
            this.array[i] = t;
        }
        synchronized (this.syncRead) {
            ++this.size;
            this.writePos = i;
            this.syncRead.notifyAll();
        }
        return true;
    }
    
    @Override
    public final void waitForFreeSlots(final int n) throws InterruptedException {
        synchronized (this.syncRead) {
            if (this.capacityPlusOne - 1 - this.size < n) {
                while (this.capacityPlusOne - 1 - this.size < n) {
                    this.syncRead.wait();
                }
            }
        }
    }
    
    @Override
    public final void growEmptyBuffer(final T[] array) throws IllegalStateException, IllegalArgumentException {
        synchronized (this.syncGlobal) {
            if (null == array) {
                throw new IllegalArgumentException("newElements is null");
            }
            final Class<? extends Object[]> class1 = this.array.getClass();
            final Class<? extends Object[]> class2 = array.getClass();
            if (class1 != class2) {
                throw new IllegalArgumentException("newElements array-type mismatch, internal " + class1 + ", newElements " + class2);
            }
            if (0 != this.size) {
                throw new IllegalStateException("Buffer is not empty: " + this);
            }
            if (this.readPos != this.writePos) {
                throw new InternalError("R/W pos not equal: " + this);
            }
            if (this.readPos != this.writePos) {
                throw new InternalError("R/W pos not equal at empty: " + this);
            }
            final int length = array.length;
            final int capacityPlusOne = this.capacityPlusOne + length;
            final Object[] array2 = this.array;
            final Object[] array3 = newArray(class1, capacityPlusOne);
            this.writePos += length;
            if (this.readPos >= 0) {
                System.arraycopy(array2, 0, array3, 0, this.readPos + 1);
            }
            if (length > 0) {
                System.arraycopy(array, 0, array3, this.readPos + 1, length);
            }
            final int n = this.capacityPlusOne - 1 - this.readPos;
            if (n > 0) {
                System.arraycopy(array2, this.readPos + 1, array3, this.writePos + 1, n);
            }
            this.size = length;
            this.capacityPlusOne = capacityPlusOne;
            this.array = array3;
        }
    }
    
    @Override
    public final void growFullBuffer(final int n) throws IllegalStateException, IllegalArgumentException {
        synchronized (this.syncGlobal) {
            if (0 > n) {
                throw new IllegalArgumentException("amount " + n + " < 0 ");
            }
            if (this.capacityPlusOne - 1 != this.size) {
                throw new IllegalStateException("Buffer is not full: " + this);
            }
            if ((this.writePos + 1) % this.capacityPlusOne != this.readPos) {
                throw new InternalError("R != W+1 pos at full: " + this);
            }
            final Class<? extends Object[]> class1 = this.array.getClass();
            final int capacityPlusOne = this.capacityPlusOne + n;
            final Object[] array = this.array;
            final Object[] array2 = newArray(class1, capacityPlusOne);
            this.readPos = (this.writePos + 1 + n) % capacityPlusOne;
            if (this.writePos >= 0) {
                System.arraycopy(array, 0, array2, 0, this.writePos + 1);
            }
            final int n2 = this.capacityPlusOne - 1 - this.writePos;
            if (n2 > 0) {
                System.arraycopy(array, this.writePos + 1, array2, this.readPos, n2);
            }
            this.capacityPlusOne = capacityPlusOne;
            this.array = array2;
        }
    }
    
    private static <T> T[] newArray(final Class<? extends T[]> clazz, final int n) {
        return (T[])((clazz == Object[].class) ? new Object[n] : Array.newInstance(clazz.getComponentType(), n));
    }
}
