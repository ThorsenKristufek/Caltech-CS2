package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    private E[] data;
    private int size;
    private static final int growFactor = 2;
    private static final int capacity = 10;

    public ArrayDeque(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
        this.size = 0;
    }

    public ArrayDeque(){
        this(capacity);
    }

    private void ensureCapacity() {
        if (this.size >= this.data.length) {
            E[] newData = (E[]) new Object[this.size * this.growFactor];
            for (int i = 0; i < this.size; i++) {
                newData[i] = this.data[i];
            }
            this.data = newData;
        }
    }

    @Override
    public String toString() {
        if(this.size > 0) {
            String toRet = "[";
            for (int i = 0; i < this.size - 1; i++) {
                toRet += this.data[i] + ", ";
            }
            toRet += this.data[this.size - 1];
            toRet += "]";
            return toRet;
        }
        else return "[]";
    }


    @Override
    public void addBack(E e) {
        this.ensureCapacity();
        this.data[this.size] = e;
        this.size++;
    }

    @Override
    public void addFront(E e) {
        this.ensureCapacity();
        E[] local = this.data.clone();
        for(int i = 0; i < this.size; i++){
            this.data[i + 1] = local[i];
        }
        this.data[0] = e;
        this.size++;
    }

    @Override
    public E removeBack() {
        if(this.size > 0) {
            E toRet = this.data[this.size - 1];
            this.data[this.size - 1] = null;
            this.size--;
            return toRet;
        }
        return null;
    }

    @Override
    public E removeFront() {
        if(this.size > 0){
            E toRet = this.data[0];
            E[] copy = (E[]) new Object[this.size];
            for(int i = 0; i < this.size - 1; i++) {
                copy[i] = this.data[i + 1];
            }
            this.data = copy;
            this.size--;
            return toRet;
        }
        return null;
    }

    @Override
    public boolean enqueue(E e) {
        this.addFront(e);
        return true;
    }

    @Override
    public E dequeue() {
        E toRet = this.removeBack();
        return toRet;
    }

    @Override
    public boolean push(E e) {
        this.addBack(e);
        return true;
    }

    @Override
    public E pop() {
        E toRet = this.removeBack();
        return toRet;
    }

    @Override
    public E peekBack() {
        if(this.size > 0){
            return this.data[this.size - 1];
        }
        return null;
    }

    @Override
    public E peekFront() {
        if(this.size > 0){
            return this.data[0];
        }
        return null;
    }

    @Override
    public E peek() {
        return this.peekBack();
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<E> {

        private int currentIndex;

        public ArrayDequeIterator() {
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < ArrayDeque.this.size;
        }

        @Override
        public E next() {
            E element = ArrayDeque.this.data[this.currentIndex];
            this.currentIndex++;
            return element;
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}