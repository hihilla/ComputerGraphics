// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import java.security.AccessController;
import com.jogamp.common.JogampRuntimeException;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class IntObjectHashMap implements Cloneable, Iterable<Entry>
{
    private final float loadFactor;
    private Entry[] table;
    private int size;
    private int mask;
    private int capacity;
    private int threshold;
    private Object keyNotFoundValue;
    private static final boolean isPrimitive;
    private static final Constructor<Entry> entryConstructor;
    private static final Method equalsMethod;
    
    public IntObjectHashMap() {
        this(16, 0.75f);
    }
    
    public IntObjectHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public IntObjectHashMap(final int n, final float loadFactor) {
        this.keyNotFoundValue = null;
        if (n > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity must be greater than zero.");
        }
        if (loadFactor <= 0.0f) {
            throw new IllegalArgumentException("loadFactor must be greater than zero.");
        }
        this.capacity = 1;
        while (this.capacity < n) {
            this.capacity <<= 1;
        }
        this.loadFactor = loadFactor;
        this.threshold = (int)(this.capacity * loadFactor);
        this.table = new Entry[this.capacity];
        this.mask = this.capacity - 1;
    }
    
    private IntObjectHashMap(final float loadFactor, final int n, final int size, final int mask, final int capacity, final int threshold, final Object keyNotFoundValue) {
        this.keyNotFoundValue = null;
        this.loadFactor = loadFactor;
        this.table = new Entry[n];
        this.size = size;
        this.mask = mask;
        this.capacity = capacity;
        this.threshold = threshold;
        this.keyNotFoundValue = keyNotFoundValue;
    }
    
    public Object clone() {
        final IntObjectHashMap intObjectHashMap = new IntObjectHashMap(this.loadFactor, this.table.length, this.size, this.mask, this.capacity, this.threshold, this.keyNotFoundValue);
        final ArrayList<Entry> list = new ArrayList<Entry>();
        for (int i = this.table.length - 1; i >= 0; --i) {
            for (Entry next = this.table[i]; null != next; next = next.next) {
                list.add(next);
            }
            final int size = list.size();
            Entry entry = null;
            for (int j = size - 1; j >= 0; --j) {
                final Entry entry2 = list.remove(j);
                if (IntObjectHashMap.isPrimitive) {
                    entry = new Entry(entry2.key, entry2.value, entry);
                }
                else {
                    entry = (Entry)ReflectionUtil.createInstance(IntObjectHashMap.entryConstructor, entry2.key, ReflectionUtil.callMethod(entry2.value, getCloneMethod(entry2.value), new Object[0]), entry);
                }
            }
            intObjectHashMap.table[i] = entry;
        }
        return intObjectHashMap;
    }
    
    public boolean containsValue(final Object o) {
        final Entry[] table = this.table;
        int length = table.length;
        while (length-- > 0) {
            for (Entry next = table[length]; next != null; next = next.next) {
                if (IntObjectHashMap.isPrimitive) {
                    if (next.value == o) {
                        return true;
                    }
                }
                else if (ReflectionUtil.callMethod(o, IntObjectHashMap.equalsMethod, next.value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsKey(final int n) {
        for (Entry next = this.table[n & this.mask]; next != null; next = next.next) {
            if (next.key == n) {
                return true;
            }
        }
        return false;
    }
    
    public Object get(final int n) {
        for (Entry next = this.table[n & this.mask]; next != null; next = next.next) {
            if (next.key == n) {
                return next.value;
            }
        }
        return this.keyNotFoundValue;
    }
    
    public Object put(final int n, final Object value) {
        final Entry[] table = this.table;
        final int n2 = n & this.mask;
        for (Entry next = table[n2]; next != null; next = next.next) {
            if (next.key == n) {
                final Object value2 = next.value;
                next.value = value;
                return value2;
            }
        }
        table[n2] = new Entry(n, value, table[n2]);
        if (this.size++ >= this.threshold) {
            final int capacity = 2 * this.capacity;
            final Entry[] table2 = new Entry[capacity];
            final int mask = capacity - 1;
            for (int i = 0; i < table.length; ++i) {
                Entry entry = table[i];
                if (entry != null) {
                    table[i] = null;
                    do {
                        final Entry next2 = entry.next;
                        final int n3 = entry.key & mask;
                        entry.next = table2[n3];
                        table2[n3] = entry;
                        entry = next2;
                    } while (entry != null);
                }
            }
            this.table = table2;
            this.capacity = capacity;
            this.threshold = (int)(capacity * this.loadFactor);
            this.mask = mask;
        }
        return this.keyNotFoundValue;
    }
    
    public void putAll(final IntObjectHashMap intObjectHashMap) {
        for (final Entry entry : intObjectHashMap) {
            this.put(entry.key, entry.value);
        }
    }
    
    public Object remove(final int n) {
        final Entry[] table = this.table;
        final int n2 = n & this.mask;
        Entry entry2;
        Entry next;
        for (Entry entry = entry2 = table[n2]; entry2 != null; entry2 = next) {
            next = entry2.next;
            if (entry2.key == n) {
                --this.size;
                if (entry == entry2) {
                    table[n2] = next;
                }
                else {
                    entry.next = next;
                }
                return entry2.value;
            }
            entry = entry2;
        }
        return this.keyNotFoundValue;
    }
    
    public int size() {
        return this.size;
    }
    
    public int capacity() {
        return this.capacity;
    }
    
    public void clear() {
        Arrays.fill(this.table, null);
        this.size = 0;
    }
    
    @Override
    public Iterator<Entry> iterator() {
        return new EntryIterator(this.table);
    }
    
    public Object setKeyNotFoundValue(final Object keyNotFoundValue) {
        final Object keyNotFoundValue2 = this.keyNotFoundValue;
        this.keyNotFoundValue = keyNotFoundValue;
        return keyNotFoundValue2;
    }
    
    public Object getKeyNotFoundValue() {
        return this.keyNotFoundValue;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("{");
        final Iterator<Entry> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next().toString(sb);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    private static Method getCloneMethod(final Object o) {
        return AccessController.doPrivileged((PrivilegedAction<Method>)new PrivilegedAction<Method>() {
            final /* synthetic */ Class val$clazz = o.getClass();
            
            @Override
            public Method run() {
                try {
                    return this.val$clazz.getDeclaredMethod("clone", (Class[])new Class[0]);
                }
                catch (NoSuchMethodException ex) {
                    throw new JogampRuntimeException("Class " + this.val$clazz + " doesn't support clone()", ex);
                }
            }
        });
    }
    
    static {
        final Class<Object> clazz = Object.class;
        final Class<Integer> type = Integer.TYPE;
        if (!(isPrimitive = clazz.isPrimitive())) {
            final EntryCM entryCM = AccessController.doPrivileged((PrivilegedAction<EntryCM>)new PrivilegedAction<EntryCM>() {
                final /* synthetic */ Class val$keyClazz = type;
                final /* synthetic */ Class val$valueClazz = clazz;
                
                @Override
                public EntryCM run() {
                    final EntryCM entryCM = new EntryCM();
                    entryCM.c = (Constructor<Entry>)ReflectionUtil.getConstructor(Entry.class, this.val$keyClazz, this.val$valueClazz, Entry.class);
                    try {
                        entryCM.m1 = this.val$valueClazz.getDeclaredMethod("equals", Object.class);
                    }
                    catch (NoSuchMethodException ex) {
                        throw new JogampRuntimeException("Class " + this.val$valueClazz + " doesn't support equals(Object)");
                    }
                    return entryCM;
                }
            });
            entryConstructor = entryCM.c;
            equalsMethod = entryCM.m1;
        }
        else {
            entryConstructor = null;
            equalsMethod = null;
        }
    }
    
    static class EntryCM
    {
        Constructor<Entry> c;
        Method m1;
        
        EntryCM() {
            this.c = null;
            this.m1 = null;
        }
    }
    
    private static final class EntryIterator implements Iterator<Entry>
    {
        private final Entry[] entries;
        private int index;
        private Entry next;
        
        private EntryIterator(final Entry[] entries) {
            this.entries = entries;
            this.next();
        }
        
        @Override
        public boolean hasNext() {
            return this.next != null;
        }
        
        @Override
        public Entry next() {
            final Entry next = this.next;
            if (next != null && next.next != null) {
                this.next = next.next;
            }
            else {
                while (this.index < this.entries.length) {
                    final Entry next2 = this.entries[this.index++];
                    if (next2 != null) {
                        this.next = next2;
                        return next;
                    }
                }
                this.next = null;
            }
            return next;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public static final class Entry
    {
        public final int key;
        public Object value;
        Entry next;
        
        Entry(final int key, final Object value, final Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public int getKey() {
            return this.key;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public void setValue(final Object value) {
            this.value = value;
        }
        
        public StringBuilder toString(StringBuilder sb) {
            if (null == sb) {
                sb = new StringBuilder();
            }
            sb.append("[").append(this.key).append(":").append(this.value).append("]");
            return sb;
        }
        
        @Override
        public String toString() {
            return this.toString(null).toString();
        }
    }
}