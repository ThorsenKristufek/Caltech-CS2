package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class MoveToFrontDictionary<K, V> implements IDictionary<K,V> {
    private Node head;
    private int size;


    private static class Node {
        public final Object key;
        public Object value;
        public MoveToFrontDictionary.Node next;

        public Node(Object key, Object value) {
            this(key, value, null);
        }

        public Node(Object key, Object value, MoveToFrontDictionary.Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MoveToFrontDictionary() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public V remove(K key) {
        V toRet = get(key);
        if(keys().contains(key)) size--;
        if(!keys().contains(key)) return null;
        head = head.next;
        return toRet;
    }

    @Override
    public V put(K key, V value) {
        V toRet = get(key);
        if(toRet == null) {
            if(head == null) {
                head = new Node(key, value);
            }
            else {
                Node ugh = head;
                head = new Node(key, value);
                head.next = ugh;
            }
            size++;
        }
        else head.value = value;
        return toRet;
        }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return this.values().contains(value);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keys() {
        LinkedDeque<K> keys = new LinkedDeque<>();
        Node current = head;
        if(current == null) return keys;
        while(current != null) {
            keys.add((K) current.key);
            current = current.next;
        }
        return keys;
    }

    @Override
    public ICollection<V> values() {
        LinkedDeque<V> values = new LinkedDeque<>();
        Node current = head;
        if(current == null) return values;
        while(current != null) {
            values.add((V) current.value);
            current = current.next;
        }
        return values;
    }

    public V get(K key) {
        Node currentKey = head;
        V toRet = null;
        Node newHead = null;
        if(currentKey == null) return null;
        if(currentKey.key.equals(key)) return (V) currentKey.value;
        while(currentKey != null) {
            if(currentKey.next == null) return null;
            if(currentKey.next.key.equals(key)){
                newHead = currentKey.next;
                currentKey.next = currentKey.next.next;
                Node ugh = head;
                head = newHead;
                head.next = ugh;
                toRet = (V) newHead.value;
                break;
            }
            currentKey = currentKey.next;
        }
        return toRet;
    }

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }
}
