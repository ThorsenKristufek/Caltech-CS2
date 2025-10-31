package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IDictionary;
import edu.caltech.cs2.interfaces.IQueue;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ChainingHashDictionary<K, V> implements IDictionary<K, V> {
    private Supplier<IDictionary<K, V>> chain;
    private IDictionary<K, V>[] hashTable;
    private IQueue<Integer> primes;
    private int size;

    public ChainingHashDictionary(Supplier<IDictionary<K, V>> chain) {
        Integer[] primeArr = {31, 67, 127, 257, 509, 1021, 2053, 4091, 8191, 16381, 32771, 65537, 131071, 262147, 524287};
        primes = new ArrayDeque<>();
        for(int i = 0; i < primeArr.length; i++) {
            primes.enqueue(primeArr[i]);
        }
        this.chain = chain;
        hashTable = new IDictionary[primes.dequeue()];
        size = 0;
    }

    public Integer mod(Integer code, Integer size) {
        if(code % size < 0) return code % size + size;
        else return code % size;
    }

    public void checkSize() {
        if((this.size + 1) / hashTable.length >= 1) {
            IDictionary[] newArr = new IDictionary[primes.dequeue()];
            for(K key : keys()) {
                int hash = mod(key.hashCode(),  newArr.length);
                V value = get(key);
                if(newArr[hash] == null) {
                    IDictionary<K, V> bucket = chain.get();
                    bucket.put(key, value);
                    newArr[hash] = bucket;
                }
                else {
                    IDictionary<K, V> bucket =  newArr[hash];
                    bucket.put(key, value);
                }
            }
            hashTable = newArr;
        }
    }

    /**
     * @param key
     * @return value corresponding to key
     */
    @Override
    public V get(K key) {
        if(hashTable[mod(key.hashCode(), hashTable.length)] == null) return null;
        IDictionary<K, V> bucket = hashTable[mod(key.hashCode(), hashTable.length)];
        return  bucket.get(key);
    }

    @Override
    public V remove(K key) {
        int hash = mod(key.hashCode(), hashTable.length);
        V toRet;
        IDictionary<K, V> bucket = hashTable[hash];
        if(bucket == null) return null;
        else {
            toRet = bucket.get(key);
            bucket.remove(key);
                }
        if(toRet != null) size--;
        return toRet;
    }

    @Override
    public V put(K key, V value) {
        checkSize();
        int hash = mod(key.hashCode(), hashTable.length);
        if(hashTable[hash] == null) {
            IDictionary<K, V> bucket = chain.get();
            bucket.put(key, value);
            hashTable[hash] = bucket;
            size++;
            return null;
        }
        else {
            IDictionary<K, V> bucket = hashTable[hash];
            V toRet = bucket.get(key);
            bucket.put(key, value);
            if(toRet == null) size++;
            return toRet;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
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
        return this.size;
    }

    @Override
    public ICollection<K> keys() {
        LinkedDeque<K> keys = new LinkedDeque<>();
        if(this.size == 0) return keys;
        for(Object bucket : this.hashTable) {
            if(bucket != null){
                for(K key : (IDictionary<K, V>) bucket) {
                    keys.add(key);
                }
            }
            if(keys.size() == size) return keys;
        }
        return keys;
    }

    @Override
    public ICollection<V> values() {
        LinkedDeque<V> values = new LinkedDeque<>();
        if(size == 0) return values;
        for(K key : this) {
            values.add(get(key));
        }
        return values;
    }

    /**
     * @return An iterator for all entries in the HashDictionary
     */
    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }
}
