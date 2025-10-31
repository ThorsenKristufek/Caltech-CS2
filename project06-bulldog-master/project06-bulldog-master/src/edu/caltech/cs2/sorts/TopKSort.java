package edu.caltech.cs2.sorts;

import edu.caltech.cs2.datastructures.MinFourHeap;
import edu.caltech.cs2.interfaces.IPriorityQueue;

public class TopKSort {
    /**
     * Sorts the largest K elements in the array in descending order. Modifies the array in place.
     * @param array - the array to be sorted; will be manipulated.
     * @param K - the number of values to sort
     * @param <E> - the type of values in the array
     * @throws IllegalArgumentException if K < 0
     */
    public static <E> void sort(IPriorityQueue.PQElement<E>[] array, int K) {
        if (K < 0) {
            throw new IllegalArgumentException("K cannot be negative!");
        }
        IPriorityQueue.PQElement<E>[] newArr = new IPriorityQueue.PQElement[array.length];
        if(K != 0) {
            MinFourHeap<E> heap = new MinFourHeap<>();
            for(IPriorityQueue.PQElement<E> node : array) {
                if(heap.size() < K) heap.enqueue(node);
                else {
                    if(heap.size() == 0) break;
                    if(node.priority > heap.peek().priority) {
                        heap.dequeue();
                        heap.enqueue(node);
                    }
                }
            }
            for(int i = heap.size() - 1; i >= 0; i--) {
                newArr[i] = heap.dequeue();
            }
        }
        for(int i = 0; i < newArr.length; i++) {
            array[i] = newArr[i];
        }
    }
}
