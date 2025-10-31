package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDictionary;
import edu.caltech.cs2.interfaces.IPriorityQueue;

import java.util.Iterator;

public class MinFourHeap<E> implements IPriorityQueue<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private PQElement<E>[] data;
    private IDictionary<E, Integer> keyToIndexMap;

    /**
     * Creates a new empty heap with DEFAULT_CAPACITY.
     */
    public MinFourHeap() {
        this.size = 0;
        this.data = new PQElement[DEFAULT_CAPACITY];
        this.keyToIndexMap = new ChainingHashDictionary<>(MoveToFrontDictionary::new);
    }

    private void ensureCapacity() {
        if (this.size >= this.data.length) {
            PQElement<E>[] newData =  new PQElement[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                newData[i] = this.data[i];
            }
            this.data = newData;
        }
    }

    private void percUp(Integer index) {
        while(data[(index - 1) / 4] != null && data[(index - 1)/4].priority > data[index].priority) {
            PQElement par = data[(index - 1) / 4];
            data[(index - 1) / 4] = data[index];
            data[index] = par;
            keyToIndexMap.put(data[index].data, index);
            keyToIndexMap.put(data[(index - 1) / 4].data, (index - 1) / 4);
            index = (index - 1)/4;
        }
    }

    private PQElement<E> findMinChild(Integer index) {
        double minVal = Double.MAX_VALUE;
        int minDex = 0;
        for(int i = 1; i <= 4; i++) {
            if((4 * index) + i < data.length) {
            if(data[(4 * index) + i] == null) break;
                if(data[(4 * index) + i].priority < minVal) {
                    minVal = data[(4 * index) + i].priority;
                    minDex = (4 * index) + i;
                }
            }
        }
        return data[minDex];
    }

    private void percDown(Integer index) {
            while (!((4 * index) + 1 > size - 1) && data[(4 * index) + 1] != null && data[index].priority > findMinChild(index).priority) {
                PQElement<E> minChild = findMinChild(index);
                PQElement<E> swap = data[index];
                data[index] = minChild;
                Integer idx = keyToIndexMap.get(minChild.data);
                data[idx] = swap;
                keyToIndexMap.put(data[index].data, index);
                keyToIndexMap.put(data[idx].data, idx);
                index = idx;
            }
    }

    @Override
    public void increaseKey(PQElement<E> key) {
        Integer gert = keyToIndexMap.get(key.data);
        if(gert == null) throw new IllegalArgumentException("key is not in queue");
        data[gert] = key;
        keyToIndexMap.put(key.data, gert);
        percDown(gert);
    }

    @Override
    public void decreaseKey(PQElement<E> key) {
        Integer gert = keyToIndexMap.get(key.data);
        if(gert == null) throw new IllegalArgumentException("key is not in queue");
        data[gert] = key;
        keyToIndexMap.put(key.data, gert);
        percUp(gert);
    }

    @Override
    public boolean enqueue(PQElement<E> epqElement) {
        if(keyToIndexMap.containsKey(epqElement.data)) throw new IllegalArgumentException("Element already in queue");
        else {
            ensureCapacity();
            data[size] = epqElement;
            keyToIndexMap.put(epqElement.data, size);
            percUp(size);
            size++;
            return true;
        }
    }

    @Override
    public PQElement<E> dequeue() {
        if(size == 0) return null;
        PQElement<E> toRet = data[0];
        data[0] = data[size - 1];
        if(size - 1 != 0) {
            data[size - 1] = null;
        }
        keyToIndexMap.put(data[0].data, 0);
        percDown(0);
        keyToIndexMap.remove(toRet.data);
        size--;
        return toRet;
    }

    @Override
    public PQElement<E> peek() {
        return data[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<PQElement<E>> iterator() {
        return null;
    }
}