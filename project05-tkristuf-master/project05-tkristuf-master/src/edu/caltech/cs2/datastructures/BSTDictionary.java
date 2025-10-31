package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class BSTDictionary<K extends Comparable<? super K>, V>
        implements IDictionary<K, V> {

    protected BSTNode<K, V> root;
    protected int size;

    /**
     * Class representing an individual node in the Binary Search Tree
     */
    protected static class BSTNode<K, V> {
        public final K key;
        public V value;

        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        /**
         * Constructor initializes this node's key, value, and children
         */
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public BSTNode(BSTNode<K, V> o) {
            this.key = o.key;
            this.value = o.value;
            this.left = o.left;
            this.right = o.right;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        public boolean hasBothChildren() {
            return this.left != null && this.right != null;
        }
    }

    /**
     * Initializes an empty Binary Search Tree
     */
    public BSTDictionary() {
        this.root = null;
        this.size = 0;
    }


    @Override
    public V get(K key) {
        if (root == null)
            return null;
        BSTNode<K, V> curr = root;
        while (curr != null) {
            if (key.compareTo(curr.key) < 0) {
                if (curr.left != null)
                    curr = curr.left;
                else
                    return null;
            }
            else if (key.compareTo(curr.key) == 0) {
                return curr.value;
            }
            else if (key.compareTo(curr.key) > 0) {
                if (curr.right != null)
                    curr = curr.right;
                else
                    return null;
            }
        }
        return curr.value;
    }

    @Override
    public V remove(K key) {
        System.out.println(toString());
        if (!containsKey(key)) {
            return null;
        }
        if (root != null) {
            return removeHelper(key);
        }
        return null;
    }

    private BSTNode<K, V> findMin(BSTNode<K, V> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public V removeHelper(K key) {
        BSTNode<K, V> curr = root;
        BSTNode<K, V> parent = null;

        while (curr != null) {
            if (key.compareTo(curr.key) < 0) {
                if (curr.left != null) {
                    parent = curr;
                    curr = curr.left;
                }
            }
            else if (key.compareTo(curr.key) == 0) {
                break;
            }
            else if (key.compareTo(curr.key) > 0) {
                if (curr.right != null) {
                    parent = curr;
                    curr = curr.right;
                }
            }
            System.out.println(curr.key + " " + curr.value);
        }

        if (curr.isLeaf()) {
            if (parent != null) {
                if (key.compareTo(parent.key) < 0) {
                    parent.left = null;
                    size--;
                    System.out.println(toString());
                    return curr.value;
                }
                else if (key.compareTo(parent.key) > 0) {
                    parent.right = null;
                    size--;
                    System.out.println(toString());
                    return curr.value;
                }
            }
            else {
                root = null;
                size--;
                System.out.println(toString());
                return curr.value;
            }
        }
        else if (curr.hasBothChildren()) {
            BSTNode<K, V> min = findMin(curr.right);
            V v = remove(min.key);
            size++;
            min.left = curr.left;
            min.right = curr.right;
            if (parent != null) {
                if (key.compareTo(parent.key) < 0) {
                    parent.left = min;
                }
                else if (key.compareTo(parent.key) > 0) {
                    parent.right = min;
                }
            }
            else{
                root = min;
            }
            size--;
            System.out.println(toString());
            return curr.value;
        }
        else if (curr.left == null) {
            if (parent != null) {
                if (key.compareTo(parent.key) < 0)
                    parent.left = curr.right;
                else if (key.compareTo(parent.key) > 0)
                    parent.right = curr.right;
            }
            else{
                root = root.right;
            }
            size--;
            System.out.println(toString());
            return curr.value;
        }
        else if (curr.right == null) {
            if (parent != null) {
                if (key.compareTo(parent.key) < 0)
                    parent.left = curr.left;
                else if (key.compareTo(parent.key) > 0)
                    parent.right = curr.left;
            }
            else{
                root = root.left;
            }
            size--;
            System.out.println(toString());
            return curr.value;
        }
        return null;
    }


    @Override
    public V put(K key, V value) {
        if (root == null) {
            root = new BSTNode<>(key, value);
            size++;
            return null;
        }
        return this.putHelper(new BSTNode<>(key, value));
    }
    private V putHelper(BSTNode<K, V> putNode) {
        BSTNode<K, V> curr = root;
        while (curr != null) {
            if (putNode.key.compareTo(curr.key) < 0) {
                if (curr.left != null)
                    curr = curr.left;
                else {
                    curr.left = putNode;
                    size++;
                    return null;
                }
            }
            else if (putNode.key.compareTo(curr.key) == 0) {
                V v = curr.value;
                curr.value = putNode.value;
                return v;
            }
            else if (putNode.key.compareTo(curr.key) > 0) {
                if (curr.right != null)
                    curr = curr.right;
                else {
                    curr.right = putNode;
                    size++;
                    return null;
                }
            }
        }
        return null;

    }
    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (this.root != null)
            return this.values().contains(value);
        return false;
    }

    /**
     * @return number of nodes in the BST
     */
    @Override
    public int size() {
        return this.size;
    }

    public ICollection<BSTNode<K, V>> traverseHelper(BSTNode<K, V> curr, ICollection<BSTNode<K, V>> kvList) {
        if (curr.left != null)
            kvList = traverseHelper(curr.left, kvList);
        kvList.add(curr);
        if (curr.right != null)
            kvList = traverseHelper(curr.right, kvList);
        return kvList;
    }

    @Override
    public ICollection<K> keys() {
        if (root == null)
            return null;
        ICollection<BSTNode<K, V>> dictList = traverseHelper(root, new ArrayDeque<>());
        ICollection<K> keySet = new LinkedDeque<>();
        for (BSTNode<K, V> node : dictList) {
            keySet.add(node.key);
        }
        return keySet;
    }

    @Override
    public ICollection<V> values() {
        if (root == null)
            return null;
        ICollection<BSTNode<K, V>> dictList = traverseHelper(root, new ArrayDeque<>());
        ICollection<V> valueSet = new LinkedDeque<>();
        for (BSTNode<K, V> node : dictList) {
            if (!valueSet.contains(node.value))
                valueSet.add(node.value);
        }
        return valueSet;
    }

    /**
     * Implementation of an iterator over the BST
     */

    @Override
    public Iterator<K> iterator() {
        return keys().iterator();
    }

    @Override
    public String toString() {
        if (this.root == null) {
            return "{}";
        }

        StringBuilder contents = new StringBuilder();

        IQueue<BSTNode<K, V>> nodes = new ArrayDeque<>();
        BSTNode<K, V> current = this.root;
        while (current != null) {
            contents.append(current.key + ": " + current.value + ", ");

            if (current.left != null) {
                nodes.enqueue(current.left);
            }
            if (current.right != null) {
                nodes.enqueue(current.right);
            }

            current = nodes.dequeue();
        }

        return "{" + contents.toString().substring(0, contents.length() - 2) + "}";
    }
}
