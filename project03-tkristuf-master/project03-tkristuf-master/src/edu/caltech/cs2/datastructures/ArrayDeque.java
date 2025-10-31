package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;
import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private E[] data;
    private int size;
    private static final int growthf = 2;
    private static final int default_cap = 10;
    public ArrayDeque(){
        this(default_cap);
    }
    public ArrayDeque(int initialcap) {
        this.data = (E[])new Object[initialcap];
        this.size = 0;
    }
    private int capacity() {return this.data.length;}
    private void ensureCapacity(int size) {
        if (this.capacity() < size) {
            E[] newData = (E[])new Object[(int)(this.capacity()*growthf)];
            for (int i = 0; i < this.size; i++) {newData[i] = this.data[i];}
            this.data = newData;}}

    @Override
    public void addFront(E e) {
        this.ensureCapacity(this.size +1);

        for(int i = this.size-1; i >= 0; i--){
            this.data[i+1] = this.data[i];
        }
        this.data[0] = e;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        this.ensureCapacity(this.size + 1);
        this.data[this.size] = e;
        this.size++;
    }
    @Override
    public E removeFront() {
        if(this.size == 0) return null;
        E taken = this.data[0];
        for(int i = 0; i < this.size-1; i++){
            this.data[i] = this.data[i+1];
        }
        this.size--;
        return taken;
    }

    @Override
    public E removeBack() {
        if(this.size == 0) return null;
        E taken = this.data[this.size-1];
        this.data[this.size-1] = null;
        this.size--;
        return taken;
    }

    @Override
    public boolean enqueue(E e) {
        addFront(e);
        return true;
    }

    @Override
    public E dequeue() {
        return removeBack();
    }

    @Override
    public boolean push(E e) {
        addBack(e);
        return true;
    }

    @Override
    public E pop() {
       return removeBack();
    }

    @Override
    public E peekFront() {
        if(this.size == 0) return null;
        return this.data[0];
    }

    @Override
    public E peekBack() {
        if(this.size == 0) return null;
        return this.data[this.size-1];
    }

    @Override
    public E peek() {
        return peekBack();
    }

    @Override
    public Iterator<E> iterator() {
        return new DequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }
    public String toString() {
        if(this.size == 0) return "[]";
        String result = "[";
        for (int i = 0; i < this.size; i++) {
            result += this.data[i] + ", ";
        }
        if (this.size != 0) {
            result = result.substring(0, result.length() - 2);
        }
        return result + "]";
    }
    private class DequeIterator implements Iterator<E> {
        private int index;
        public DequeIterator(){
            this.index = 0;
        }
        public boolean hasNext(){
            return this.index < ArrayDeque.this.size;
        }
        public E next(){
            E element = ArrayDeque.this.data[this.index];
            this.index++;
            return element;
        }
    }
}


