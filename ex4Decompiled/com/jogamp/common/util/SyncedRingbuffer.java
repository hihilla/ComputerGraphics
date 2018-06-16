// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.lang.reflect.Array;
import java.io.PrintStream;

public class SyncedRingbuffer<T> implements Ringbuffer<T>
{
    private final Object syncGlobal;
    private T[] array;
    private int capacity;
    private int readPos;
    private int writePos;
    private int size;
    
    @Override
    public final String toString() {
        return "SyncedRingbuffer<?>[filled " + this.size + " / " + this.capacity + ", writePos " + this.writePos + ", readPos " + this.readPos + "]";
    }
    
    @Override
    public final void dump(final PrintStream printStream, final String s) {
        printStream.println(s + " " + this.toString() + " {");
        for (int i = 0; i < this.capacity; ++i) {
            printStream.println("\t[" + i + "]: " + this.array[i]);
        }
        printStream.println("}");
    }
    
    public SyncedRingbuffer(final T[] array) throws IllegalArgumentException {
        this.syncGlobal = new Object();
        this.capacity = array.length;
        this.array = newArray(array.getClass(), this.capacity);
        this.resetImpl(true, array);
    }
    
    public SyncedRingbuffer(final Class<? extends T[]> clazz, final int capacity) {
        this.syncGlobal = new Object();
        this.capacity = capacity;
        this.array = newArray((Class<? extends Object[]>)clazz, capacity);
        this.resetImpl(false, null);
    }
    
    @Override
    public final int capacity() {
        return this.capacity;
    }
    
    @Override
    public final void clear() {
        synchronized (this.syncGlobal) {
            this.resetImpl(false, null);
            for (int i = 0; i < this.capacity; ++i) {
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
                if (array.length != this.capacity()) {
                    throw new IllegalArgumentException("copyFrom array length " + array.length + " != capacity " + this);
                }
                System.arraycopy(array, 0, this.array, 0, array.length);
            }
            else if (b) {
                throw new IllegalArgumentException("copyFrom array is null");
            }
            this.readPos = 0;
            this.writePos = 0;
            this.size = (b ? this.capacity : 0);
        }
    }
    
    @Override
    public final int size() {
        synchronized (this.syncGlobal) {
            return this.size;
        }
    }
    
    @Override
    public final int getFreeSlots() {
        synchronized (this.syncGlobal) {
            return this.capacity - this.size;
        }
    }
    
    @Override
    public final boolean isEmpty() {
        synchronized (this.syncGlobal) {
            return 0 == this.size;
        }
    }
    
    @Override
    public final boolean isFull() {
        synchronized (this.syncGlobal) {
            return this.capacity == this.size;
        }
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
        synchronized (this.syncGlobal) {
            if (0 == this.size) {
                if (!b) {
                    return null;
                }
                while (0 == this.size) {
                    this.syncGlobal.wait();
                }
            }
            final int readPos = this.readPos;
            final Object o = this.array[readPos];
            if (!b2) {
                this.array[readPos] = null;
                --this.size;
                this.readPos = (readPos + 1) % this.capacity;
                this.syncGlobal.notifyAll();
            }
            return (T)o;
        }
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
        synchronized (this.syncGlobal) {
            if (this.capacity == this.size) {
                if (!b2) {
                    return false;
                }
                while (this.capacity == this.size) {
                    this.syncGlobal.wait();
                }
            }
            final int writePos = this.writePos;
            if (!b) {
                this.array[writePos] = t;
            }
            ++this.size;
            this.writePos = (writePos + 1) % this.capacity;
            this.syncGlobal.notifyAll();
            return true;
        }
    }
    
    @Override
    public final void waitForFreeSlots(final int n) throws InterruptedException {
        synchronized (this.syncGlobal) {
            if (this.capacity - this.size < n) {
                while (this.capacity - this.size < n) {
                    this.syncGlobal.wait();
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
            final int length = array.length;
            final int capacity = this.capacity + length;
            final Object[] array2 = this.array;
            final Object[] array3 = newArray(class1, capacity);
            this.writePos += length;
            if (this.readPos > 0) {
                System.arraycopy(array2, 0, array3, 0, this.readPos);
            }
            if (length > 0) {
                System.arraycopy(array, 0, array3, this.readPos, length);
            }
            final int n = this.capacity - this.readPos;
            if (n > 0) {
                System.arraycopy(array2, this.readPos, array3, this.writePos, n);
            }
            this.size = length;
            this.capacity = capacity;
            this.array = array3;
        }
    }
    
    @Override
    public final void growFullBuffer(final int n) throws IllegalStateException, IllegalArgumentException {
        synchronized (this.syncGlobal) {
            if (0 > n) {
                throw new IllegalArgumentException("amount " + n + " < 0 ");
            }
            if (this.capacity != this.size) {
                throw new IllegalStateException("Buffer is not full: " + this);
            }
            if (this.readPos != this.writePos) {
                throw new InternalError("R/W pos not equal: " + this);
            }
            final Class<? extends Object[]> class1 = this.array.getClass();
            final int capacity = this.capacity + n;
            final Object[] array = this.array;
            final Object[] array2 = newArray(class1, capacity);
            this.readPos += n;
            if (this.writePos > 0) {
                System.arraycopy(array, 0, array2, 0, this.writePos);
            }
            final int n2 = this.capacity - this.writePos;
            if (n2 > 0) {
                System.arraycopy(array, this.writePos, array2, this.readPos, n2);
            }
            this.capacity = capacity;
            this.array = array2;
        }
    }
    
    private static <T> T[] newArray(final Class<? extends T[]> clazz, final int n) {
        return (T[])((clazz == Object[].class) ? new Object[n] : Array.newInstance(clazz.getComponentType(), n));
    }
}
