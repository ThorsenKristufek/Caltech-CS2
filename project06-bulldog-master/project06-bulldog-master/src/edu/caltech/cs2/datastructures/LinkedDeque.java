package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    private Node<E> head;
    private int size;
    private Node<E> current;

    private static class Node<E> {
        public final E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node next, Node prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedDeque(){
        this.head = null;
        this.current = null;
        this.size = 0;
    }

    @Override
    public String toString(){
        String toRet = "[";
        if(this.size > 0) {
            Node store = this.current;
            this.current = this.head;
            for(int i = 0; i < this.size; i++) {
                toRet += this.current.data + ", ";
                this.current = this.current.next;
            }
            toRet = toRet.substring(0, toRet.length() - 2);
            toRet += "]";
            this.current = store;
            return toRet;
        }
        else return "[]";
    }

    @Override
    public void addBack(E e) {
        if(this.head == null){
            this.head = new Node<>(e);
            this.current = this.head;
        }
        else {
            this.current.next = new Node<>(e, null, this.current);
            this.current = this.current.next;
        }
        this.size++;
    }

    @Override
    public void addFront(E e) {
        if(this.head == null){
            this.head = new Node<>(e);
            this.current = this.head;
        }
        else{
            this.head.prev = new Node<>(e, this.head, null);
            this.head = this.head.prev;
        }
        this.size++;
    }

    @Override
    public E removeFront() {
        if(this.size > 1) {
            E toRet = this.head.data;
            this.head = this.head.next;
            this.head.prev = null;
            this.size--;
            return (E) toRet;
        }
        else if(this.size == 1) {
            E toRet = this.head.data;
            this.head = null;
            this.current = null;
            this.size--;
            return (E) toRet;
        }
        return null;
    }

    @Override
    public E removeBack() {
        if(this.size > 1) {
            E toRet = this.current.data;
            this.current = this.current.prev;
            this.current.next = null;
            this.size--;
            return toRet;
        }
        else if(this.size == 1) {
            E toRet = this.current.data;
            this.head = null;
            this.current = null;
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
        return this.removeBack();
    }

    @Override
    public boolean push(E e) {
        this.addBack(e);
        return true;
    }

    @Override
    public E pop() {
        return this.removeBack();
    }

    @Override
    public E peekFront() {
        if(this.size == 0){
            return null;
        }
        return this.head.data;
    }

    @Override
    public E peekBack() {
        if(this.size == 0){
            return null;
        }
        return this.current.data;
    }

    @Override
    public E peek() {
        return this.peekBack();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements Iterator<E> {

        private Node currentNode;

        public LinkedDequeIterator(){
            this.currentNode = LinkedDeque.this.head;
        }

        @Override
        public boolean hasNext() {
            return this.currentNode != null;
        }

        @Override
        public E next() {
            E toRet = (E) this.currentNode.data;
            this.currentNode = this.currentNode.next;
            return toRet;
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}
