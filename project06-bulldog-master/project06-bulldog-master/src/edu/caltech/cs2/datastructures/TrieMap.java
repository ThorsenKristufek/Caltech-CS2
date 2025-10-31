package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Iterator;

public class TrieMap<A, K extends Iterable<A>, V> implements ITrieMap<A, K, V> {
    private TrieNode<A, V> root;
    private Function<IDeque<A>, K> collector;
    private int size;

    public TrieMap(Function<IDeque<A>, K> collector) {
        this.root = null;
        this.collector = collector;
        this.size = 0;
    }
    

    @Override
    public boolean isPrefix(K key) {
        TrieNode<A, V> currNode = root;
        if(root == null) return false;
        for(A letter : key){
            if(currNode == null) return false;
            else if(currNode.pointers.containsKey(letter)) currNode = currNode.pointers.get(letter);
            else return false;
        }
        return true;
    }

    public ICollection<V> completionsHelper(TrieNode<A, V> node, ICollection<V> values) {
        if(node != null){
        if(node.value != null) values.add(node.value);
        for(A pointer : node.pointers.keySet()) {
            completionsHelper(node.pointers.get(pointer), values);
        }
        }
        return values;
    }

    @Override
    public IDeque<V> getCompletions(K prefix) {
        TrieNode<A, V> currNode = root;
        if(root == null) { return new ArrayDeque<>();}
        for (A letter : prefix) {
            if(currNode == null) { return new ArrayDeque<>();}
            currNode = currNode.pointers.get(letter);
        }
        ArrayDeque<V> values = new ArrayDeque<>();
        return (IDeque<V>) completionsHelper(currNode, values);
    }

    @Override
    public void clear() {
        if(this.root != null) {
            this.root.pointers.clear();
            this.root = null;
        }
        this.size = 0;
    }

    public boolean pruner(TrieNode<A, V> node ){
        if(node == null) return false;
        if(node.value == null && node.pointers.isEmpty()) return true;
        IDeque<A> toRem = new ArrayDeque<>();
        for(A letter : (K) node.pointers.keySet()){
            TrieNode<A, V> noid = node.pointers.get(letter);
            boolean remove = pruner(noid);
            if(remove) {
                node.pointers.get(letter).value = null;
                toRem.add(letter);
            }
        }
        for(A letter : toRem) {
            node.pointers.remove(letter);
        }
        return false;
        }


    @Override
    public V get(K key) {
        TrieNode<A, V> currNode = root;
        for (A letter : key) {
            if(currNode != null) currNode = currNode.pointers.get(letter);
            }
        if(currNode == null) return null;
        return currNode.value;
        }

    public boolean removeHelper(TrieNode<A, V> node, ArrayDeque<V> based) {
        return node != null && valueHelper(node, new ArrayDeque<>()) == based && node.pointers.size() == 1;
    }

    @Override
    public V remove(K key) {
        if(containsKey(key)) size--;
        if(this.root == null) {return null;}
        if(values().size() == 0) {root = null;}
        TrieNode<A, V> currNode = root;
        V value = this.get(key);
        ArrayDeque<V> based = new ArrayDeque<>();
        boolean death = false;
        A wrapper;
        if(key.iterator().hasNext()) {
            wrapper = key.iterator().next();
        }
        else {
            wrapper = (A) key;
            if(!containsKey(key)) return null;
             else {
               if(!removeHelper(currNode, based)) {
                   currNode.value = null;
                    return value;
               }
            }
         }
        // if(currNode == root) root = null;
        Iterator<A> boop = key.iterator();
        boop.next();
        while(!death) {
            if(currNode == null) return null;
            if(!boop.hasNext()) {currNode = currNode.pointers.get(wrapper); break;}
            if(!removeHelper(currNode, based)) {currNode = currNode.pointers.get(wrapper);}
            else death = true;
            wrapper = boop.next();
        }
        if(currNode != null) currNode.value = null;
        if(values().size() == 0) {root = null;}
        if(currNode != null) currNode.pointers.remove(wrapper);
        pruner(root);
        return value;
    }

    @Override
    public V put(K key, V value) {
        TrieNode<A, V> currNode = root;
        int check = 0;
        for (A letter : key) {
            if (currNode == null) {
                root = new TrieNode<>();
                root.pointers.put(letter, new TrieNode<>());
                currNode = root.pointers.get(letter);
            }
            else if (!currNode.pointers.containsKey(letter)) {
                currNode.pointers.put(letter, new TrieNode<>());
                currNode = currNode.pointers.get(letter);
            }
            else currNode = currNode.pointers.get(letter);
            check++;
        }
        if(check == 0) {
            V toRet;
            if (root == null) {
                toRet = null;
            } else {
                toRet = root.value;
            }
            root = new TrieNode<>(value);
            return toRet;
        }
        V toRet;
        toRet = currNode.value;
        if(toRet == null) this.size++;
        currNode.value = value;
        return toRet;
    }

    @Override
    public boolean containsKey(K key) {
        TrieNode<A, V> currNode = root;
        if(root == null) return false;
        for(A letter : key){
            if(currNode == null) return false;
            else if(currNode.pointers.containsKey(letter)) currNode = currNode.pointers.get(letter);
            else return false;
        }
        return currNode.value != null;
    }

    @Override
    public boolean containsValue(V value) {
        return values().contains(value);
    }

    @Override
    public int size() {
        return this.size;
    }

    public ICollection<K> keyHelper(ICollection<K> keys, IDeque<A> partial, TrieNode<A, V> node) {
        if(node.value != null) {
            keys.add(collector.apply(partial));
        }
        for(A pointer : node.pointers.keySet()) {
            ArrayDeque<A> attempt = new ArrayDeque<>();
            for(A item : partial) { attempt.add(item); }
            attempt.add(pointer);
            keyHelper(keys, attempt, node.pointers.get(pointer));

        }
        return keys;
    }

    @Override
    public ICollection<K> keys() {
        ArrayDeque<K> keys = new ArrayDeque<>();
        ArrayDeque<A> partial = new ArrayDeque<>();
        if(root == null) {
            return keys;
        }
        return keyHelper(keys, partial, root);
    }

    public ICollection<V> valueHelper(TrieNode<A, V> node, ICollection<V> acc) {
        if(node.value != null) acc.add(node.value);
        for(A pointer : node.pointers.keySet()) {
            valueHelper(node.pointers.get(pointer), acc);
        }
        return acc;
    }

    @Override
    public ICollection<V> values() {
        ArrayDeque<V> acc = new ArrayDeque<>();
        if(root == null) return acc;
        else return valueHelper(root, acc);
    }

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }
    
    private static class TrieNode<A, V> {
        public final Map<A, TrieNode<A, V>> pointers;
        public V value;

        public TrieNode() {
            this(null);
        }

        public TrieNode(V value) {
            this.pointers = new HashMap<>();
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            if (this.value != null) {
                b.append("[" + this.value + "]-> {\n");
                this.toString(b, 1);
                b.append("}");
            }
            else {
                this.toString(b, 0);
            }
            return b.toString();
        }

        private String spaces(int i) {
            StringBuilder sp = new StringBuilder();
            for (int x = 0; x < i; x++) {
                sp.append(" ");
            }
            return sp.toString();
        }

        protected boolean toString(StringBuilder s, int indent) {
            boolean isSmall = this.pointers.entrySet().size() == 0;

            for (Map.Entry<A, TrieNode<A, V>> entry : this.pointers.entrySet()) {
                A idx = entry.getKey();
                TrieNode<A, V> node = entry.getValue();

                if (node == null) {
                    continue;
                }

                V value = node.value;
                s.append(spaces(indent) + idx + (value != null ? "[" + value + "]" : ""));
                s.append("-> {\n");
                boolean bc = node.toString(s, indent + 2);
                if (!bc) {
                    s.append(spaces(indent) + "},\n");
                }
                else if (s.charAt(s.length() - 5) == '-') {
                    s.delete(s.length() - 5, s.length());
                    s.append(",\n");
                }
            }
            if (!isSmall) {
                s.deleteCharAt(s.length() - 2);
            }
            return isSmall;
        }
    }
}
