// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArrayHashMap<K, V> implements Cloneable, Map<K, V>
{
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final HashMap<K, V> map;
    private final ArrayList<V> data;
    private final boolean supportNullValue;
    
    public ArrayHashMap(final boolean supportNullValue, final int n, final float n2) {
        this.map = new HashMap<K, V>(n, n2);
        this.data = new ArrayList<V>(n);
        this.supportNullValue = supportNullValue;
    }
    
    public ArrayHashMap(final ArrayHashMap<K, V> arrayHashMap) {
        this.map = new HashMap<K, V>((Map<? extends K, ? extends V>)arrayHashMap.map);
        this.data = new ArrayList<V>((Collection<? extends V>)arrayHashMap.data);
        this.supportNullValue = arrayHashMap.supportNullValue;
    }
    
    public final boolean supportsNullValue() {
        return this.supportNullValue;
    }
    
    public final Object clone() {
        return new ArrayHashMap((ArrayHashMap<Object, Object>)this);
    }
    
    public final ArrayList<V> getData() {
        return this.data;
    }
    
    public final ArrayList<V> toArrayList() {
        return new ArrayList<V>((Collection<? extends V>)this.data);
    }
    
    public final HashMap<K, V> getMap() {
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
    public Set<K> keySet() {
        return this.map.keySet();
    }
    
    @Override
    public Collection<V> values() {
        return this.map.values();
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }
    
    @Override
    public final V get(final Object o) {
        return this.map.get(o);
    }
    
    @Override
    public final V put(final K k, final V v) throws NullPointerException {
        V v2;
        if (this.supportNullValue) {
            if (!this.map.containsKey(k)) {
                if (null != (v2 = this.map.put(k, v))) {
                    throw new InternalError("Already existing, but checked before: " + k + " -> " + v2);
                }
            }
            else {
                v2 = this.map.put(k, v);
                if (!this.data.remove(v2)) {
                    throw new InternalError("Already existing, but not in list: " + v2);
                }
            }
        }
        else {
            checkNullValue(v);
            if (null != (v2 = this.map.put(k, v)) && !this.data.remove(v2)) {
                throw new InternalError("Already existing, but not in list: " + v2);
            }
        }
        if (!this.data.add(v)) {
            throw new InternalError("Couldn't add value to list: " + v);
        }
        return v2;
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public final V remove(final Object o) {
        if (this.supportNullValue) {
            if (!this.map.containsKey(o)) {
                return null;
            }
            final V remove = this.map.remove(o);
            if (!this.data.remove(remove)) {
                throw new InternalError("Couldn't remove prev mapped pair: " + o + " -> " + remove);
            }
            return remove;
        }
        else {
            final V remove2;
            if (null != (remove2 = this.map.remove(o)) && !this.data.remove(remove2)) {
                throw new InternalError("Couldn't remove prev mapped pair: " + o + " -> " + remove2);
            }
            return remove2;
        }
    }
    
    @Override
    public final boolean containsKey(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.map.containsValue(o);
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof ArrayHashMap && this.map.equals(((ArrayHashMap)o).map);
    }
    
    @Override
    public final int hashCode() {
        return this.map.hashCode();
    }
    
    @Override
    public final boolean isEmpty() {
        return this.data.isEmpty();
    }
    
    @Override
    public final int size() {
        return this.data.size();
    }
    
    private static final void checkNullValue(final Object o) throws NullPointerException {
        if (null == o) {
            throw new NullPointerException("Null value not supported");
        }
    }
}
