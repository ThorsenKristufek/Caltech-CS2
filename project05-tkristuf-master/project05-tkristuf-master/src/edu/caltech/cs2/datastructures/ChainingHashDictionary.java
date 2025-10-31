package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;
import java.util.function.Supplier;

public class ChainingHashDictionary<K, V> implements IDictionary<K, V> {
    private Supplier<IDictionary<K, V>> chain;
    private Iterator<Integer> it;
    private IDictionary<K, V>[] bin;
    private int size;
    public ChainingHashDictionary(Supplier<IDictionary<K, V>> chain) {
        // student: TODO fill me in!
        final int[] capacityList = {11, 23, 47, 97, 197, 397, 809, 1619, 3299, 6599, 13297, 26597, 53201, 106501, 213019, 426061};
        ArrayDeque<Integer> capL = new ArrayDeque<>();
        for(int a: capacityList){
            capL.add(a);
        }
        this.chain = chain;
        this.it = capL.iterator();
        this.bin = new IDictionary[it.next()];
        for(int i = 0; i < this.bin.length; i++) this.bin[i] = chain.get();
        this.size = 0;
    }

    /**
     * @param key
     * @return value corresponding to key
     */
    @Override
    public V get(K key) {
        if(key.hashCode() < 0) return bin[-key.hashCode() % bin.length].get(key);
        return  bin[key.hashCode() % bin.length].get(key);
    }

    @Override
    public V remove(K key) {
        int idx = key.hashCode() % bin.length;
        if(key.hashCode() < 0) idx = -key.hashCode() % bin.length;
        V val = this.bin[idx].remove(key);
        if(val != null) this.size--;
        return val;
    }

    @Override
    public V put(K key, V value) {
        if ((this.size + 1) / this.bin.length > 1) {
            IDictionary<K, V>[] temp = new IDictionary[it.next()];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = chain.get();
            }
            for (K k : keys()) {
                if (k.hashCode() >= 0)
                    temp[k.hashCode() % temp.length].put(k, this.get(k));
                else
                    temp[-k.hashCode() % temp.length].put(k, this.get(k));
            }
            this.bin = temp;
        }
        int idx = key.hashCode() % bin.length;
        if (key.hashCode() < 0)
            idx = -key.hashCode() % bin.length;
        if (this.bin[idx].get(key) != null) {
            return this.bin[idx].put(key, value);
        }
        this.bin[idx].put(key, value);
        this.size++;
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return keys().contains(key);
    }

    /**
     * @param value
     * @return true if the HashDictionary contains a key-value pair with
     * this value, and false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        return values().contains(value);
    }

    /**
     * @return number of key-value pairs in the HashDictionary
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public ICollection<K> keys() {
        ArrayDeque<K> keySet = new ArrayDeque<>();
        for (IDictionary<K, V> dict : this.bin) {
            for (K k : dict.keys()) {
                keySet.add(k);
            }
        }
        return keySet;
    }

    @Override
    public ICollection<V> values() {
        ArrayDeque<V> valueSet = new ArrayDeque<>();
        for (IDictionary<K, V> dict : this.bin) {
            for (V v : dict.values()) {
                valueSet.add(v);
            }
        }
        return valueSet;
    }

    /**
     * @return An iterator for all entries in the HashDictionary
     */
    @Override
    public Iterator<K> iterator() {
        return this.keys().iterator();
    }
}