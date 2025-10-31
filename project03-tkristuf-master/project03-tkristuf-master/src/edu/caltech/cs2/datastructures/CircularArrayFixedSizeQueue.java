package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IFixedSizeQueue;

import java.util.Iterator;

public class CircularArrayFixedSizeQueue<E> implements IFixedSizeQueue<E> {
    private E[] data;
    private int head;
    private int tail;

    public CircularArrayFixedSizeQueue(int capacity){
        this.head = -1;
        this.tail = -1;
        this.data = (E[]) new Object[capacity];
    }
    public boolean isFull() {
        return this.size() == this.capacity();
    }

    @Override
    public int capacity() {
        return this.data.length;
    }

    @Override
    public boolean enqueue(E e) {
        if(this.isFull()) return false;
        if(this.size() == 0){
            this.tail = 0;
            this.head = 0;
        }
        else{
            this.tail = (this.tail + 1) % this.capacity();
        }
        this.data[this.tail] = e;
        return true;
    }

    @Override
    public E dequeue() {
        E f;
        if(this.size() == 0) return null;
        if(this.head == this.tail){
            f = this.data[this.head];
            this.head = -1;
            this.tail = -1;
        }
        else{
            f = this.data[this.head];
            this.head = (this.head +1) % this.capacity();
        }
        return f;
    }

    @Override
    public E peek() {
        if(this.size() == 0) return null;
        return this.data[this.head];
    }

    @Override
    public int size() {
        if(this.head == -1 && this.tail == -1) return 0;
        return ((this.capacity() - this.head + this.tail) % this.capacity()) +1;
    }
    public String toString() {
        if(this.head == -1 && this.tail == -1) return "[]";
        String result = "[";
        if(this.head <= this.tail || this.size() ==1){
            for(int i = this.head; i <= this.tail; i++) result += this.data[i] + ", ";
        }
        else{
            for(int p = this.head; p < this.capacity(); p++) result += this.data[p] + ", ";
            for(int y = 0; y <= this.tail; y++) result += this.data[y] + ", ";
        }
        if(this.data.length != 0) result = result.substring(0, result.length() - 2);
        return result + "]";
    }
    @Override
    public Iterator<E> iterator() {
        return new CircularArrayQueueIterator();
    }
    private class CircularArrayQueueIterator implements Iterator<E> {
        private int current;
        public CircularArrayQueueIterator(){
            this.current = head;
        }
        public boolean hasNext(){
            return current < (head + CircularArrayFixedSizeQueue.this.size());
        }
        public E next(){
            E elem = data[this.current % CircularArrayFixedSizeQueue.this.capacity()];
            this.current++;
            return elem;
        }
    }
}
