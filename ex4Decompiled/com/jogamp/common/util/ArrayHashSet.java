// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;

public class ArrayHashSet<E> implements Cloneable, Collection<E>, List<E>
{
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final HashMap<E, E> map;
    private final ArrayList<E> data;
    private final boolean supportNullValue;
    
    public ArrayHashSet() {
        this(true, 16, 0.75f);
    }
    
    public ArrayHashSet(final int n) {
        this(true, n, 0.75f);
    }
    
    public ArrayHashSet(final int n, final float n2) {
        this(true, n, n2);
    }
    
    public ArrayHashSet(final boolean supportNullValue, final int n, final float n2) {
        this.map = new HashMap<E, E>(n, n2);
        this.data = new ArrayList<E>(n);
        this.supportNullValue = supportNullValue;
    }
    
    public ArrayHashSet(final ArrayHashSet<E> set) {
        this.map = new HashMap<E, E>((Map<? extends E, ? extends E>)set.map);
        this.data = new ArrayList<E>((Collection<? extends E>)set.data);
        this.supportNullValue = set.supportNullValue;
    }
    
    public final boolean supportsNullValue() {
        return this.supportNullValue;
    }
    
    public final Object clone() {
        return new ArrayHashSet((ArrayHashSet<Object>)this);
    }
    
    public final ArrayList<E> getData() {
        return this.data;
    }
    
    public final HashMap<E, E> getMap() {
        return this.map;
    }
    
    @Override
    public final String toString() {
        return this.data.toString();
    }
    
    @Override
    public final void clear() {
        this.data.clear();
        this.map.clear();
    }
    
    @Override
    public final boolean add(final E e) throws NullPointerException {
        if (!this.supportNullValue) {
            checkNull(e);
        }
        if (this.map.containsKey(e)) {
            return false;
        }
        if (null != this.map.put(e, e)) {
            throw new InternalError("Already existing, but checked before: " + e);
        }
        if (!this.data.add(e)) {
            throw new InternalError("Couldn't add element: " + e);
        }
        return true;
    }
    
    @Override
    public final boolean remove(final Object o) throws NullPointerException {
        if (this.supportNullValue) {
            if (this.map.containsKey(o)) {
                this.map.remove(o);
                if (!this.data.remove(o)) {
                    throw new InternalError("Couldn't remove prev mapped element: " + o);
                }
                return true;
            }
        }
        else {
            checkNull(o);
            if (null != this.map.remove(o)) {
                if (!this.data.remove(o)) {
                    throw new InternalError("Couldn't remove prev mapped element: " + o);
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final boolean addAll(final Collection<? extends E> collection) {
        boolean b = false;
        final Iterator<? extends E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            b |= this.add(iterator.next());
        }
        return b;
    }
    
    @Override
    public final boolean contains(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public final boolean containsAll(final Collection<?> collection) {
        final Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public final boolean removeAll(final Collection<?> collection) {
        boolean b = false;
        final Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            b |= this.remove(iterator.next());
        }
        return b;
    }
    
    @Override
    public final boolean retainAll(final Collection<?> collection) {
        boolean b = false;
        for (final Object next : collection) {
            if (!collection.contains(next)) {
                b |= this.remove(next);
            }
        }
        return b;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof ArrayHashSet && this.data.equals(((ArrayHashSet)o).data);
    }
    
    @Override
    public final int hashCode() {
        return this.data.hashCode();
    }
    
    @Override
    public final boolean isEmpty() {
        return this.data.isEmpty();
    }
    
    @Override
    public final Iterator<E> iterator() {
        return this.data.iterator();
    }
    
    @Override
    public final int size() {
        return this.data.size();
    }
    
    @Override
    public final Object[] toArray() {
        return this.data.toArray();
    }
    
    @Override
    public final <T> T[] toArray(final T[] array) {
        return this.data.toArray(array);
    }
    
    @Override
    public final E get(final int n) {
        return this.data.get(n);
    }
    
    @Override
    public final int indexOf(final Object o) {
        return this.data.indexOf(o);
    }
    
    @Override
    public final void add(final int n, final E e) throws IllegalArgumentException, NullPointerException {
        if (!this.supportNullValue) {
            checkNull(e);
        }
        if (this.map.containsKey(e)) {
            throw new IllegalArgumentException("Element " + e + " is already contained");
        }
        if (null != this.map.put(e, e)) {
            throw new InternalError("Already existing, but checked before: " + e);
        }
        this.data.add(n, e);
    }
    
    @Override
    public final boolean addAll(final int n, final Collection<? extends E> collection) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public final E set(final int n, final E e) {
        final E remove = this.remove(n);
        if (null != remove) {
            this.add(n, e);
        }
        return remove;
    }
    
    @Override
    public final E remove(final int n) {
        final E value = this.get(n);
        if (null != value && this.remove(value)) {
            return value;
        }
        return null;
    }
    
    @Override
    public final int lastIndexOf(final Object o) {
        return this.indexOf(o);
    }
    
    @Override
    public final ListIterator<E> listIterator() {
        return this.data.listIterator();
    }
    
    @Override
    public final ListIterator<E> listIterator(final int n) {
        return this.data.listIterator(n);
    }
    
    @Override
    public final List<E> subList(final int n, final int n2) {
        return this.data.subList(n, n2);
    }
    
    public final ArrayList<E> toArrayList() {
        return new ArrayList<E>((Collection<? extends E>)this.data);
    }
    
    public final E get(final Object o) {
        return this.map.get(o);
    }
    
    public final E getOrAdd(final E e) throws NullPointerException {
        if (this.supportNullValue) {
            if (this.map.containsKey(e)) {
                return this.map.get(e);
            }
        }
        else {
            checkNull(e);
            final E value = this.map.get(e);
            if (null != value) {
                return value;
            }
        }
        if (!this.add(e)) {
            throw new InternalError("Element not mapped, but contained in list: " + e);
        }
        return e;
    }
    
    public final boolean containsSafe(final Object o) {
        return this.data.contains(o);
    }
    
    private static final void checkNull(final Object o) throws NullPointerException {
        if (null == o) {
            throw new NullPointerException("Null element not supported");
        }
    }
}
