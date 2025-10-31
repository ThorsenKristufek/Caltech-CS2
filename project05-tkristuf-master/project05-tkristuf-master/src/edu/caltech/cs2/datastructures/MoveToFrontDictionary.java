package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class MoveToFrontDictionary<K, V> implements IDictionary<K,V> {
    private Node<K, V> r;
    private int size;

    private static class Node<K, V>{
        private Node next;
        private final K key;
        private V value;

        public Node(K k, V e){
            this(null, k, e);
        }

        public Node(Node n, K k, V e){
            this.next = n;
            this.key = k;
            this.value = e;
        }

        public boolean hasNext() {
            return (this.next != null);
        }
    }
    public MoveToFrontDictionary() {
        // student: TODO fill this in
        r = null;
        size = 0;
    }

    @Override
    public V remove(K key) {
        if (r == null)
            return null;
        V value = this.get(key);
        if (value != null) {
            this.r = this.r.next;
            this.size--;
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (this.containsKey(key)) {
            V replaced = this.get(key);
            r.value = value;
            return replaced;
        }
        Node next = this.r;
        this.r = new Node(next, key, value);
        size++;
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
 //       return this.values().contains(value);
        ICollection<K> keys = keys();
        for (K k : keys) {
            if (this.get(k) == value)
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ICollection<K> keys() {
        LinkedDeque<K> keySet = new LinkedDeque<>();
        if (this.r == null) {
            return keySet;
        }
        Node<K, V> current = r;
        while (current != null) {
            Node<K, V> prev = current;
            current = current.next;
            keySet.add(prev.key);
        }
        return keySet;
    }

    @Override
    public ICollection<V> values() {
        LinkedDeque<V> valueSet = new LinkedDeque<>();
        if (this.r == null) {
            return valueSet;
        }
        Node<K, V> current = r;
        while (current != null) {
            Node<K, V> prev = current;
            current = current.next;
            if (!valueSet.contains(prev.value)) valueSet.add(prev.value);
        }
        return valueSet;
    }

    public V get(K key) {
        if (r == null)
            return null;
        if (this.r.key.equals(key))
            return this.r.value;
        Node<K, V> current = r;
        Node next;
        while (current.hasNext()) {
                next = current.next;
                if (next.key.equals(key)){
                    current.next = next.next;
                    next.next = this.r;
                    this.r = next;
                    return this.r.value;
                }
                current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return this.keys().iterator();
    }
}
