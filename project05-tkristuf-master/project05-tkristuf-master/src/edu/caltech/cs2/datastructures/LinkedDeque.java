package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private Node<E> head;
    private Node<E>  tail;
    private int size;


    private static class Node<E> {
        public final E data;
        public Node<E> next;
        public Node<E> previous;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    public LinkedDeque(){this.size = 0;}

    @Override
    public void addFront(E e) {
        if(this.size == 0){
          this.head = new Node<>(e);
          this.tail = this.head;
        }
        else{
              Node<E> nHead = new Node<>(e, this.head, null);
              this.head.previous = nHead;
              this.head = nHead;
            }
        this.size++;
    }


    @Override
    public void addBack(E e) {
    if(this.head == null){
        this.head = new Node<>(e);
        this.tail = this.head;
    }
    else{
        Node<E> ntail = new Node<>(e,null,this.tail);
        this.tail.next = ntail;
        this.tail = ntail;
    }
    this.size++;
    }

    @Override
    public E removeFront() {
        if(this.size == 0) return null;
        E temp = this.head.data;
        this.head = this.head.next;
        if(this.head != null){
            this.head.previous = null;
        }
        this.size--;
        return temp;
    }

    @Override
    public E removeBack() {
        if(this.size == 0) return null;
        if(this.size == 1){
          E temp = this.head.data;
          this.head = null;
          this.size--;
          return temp;
        }
        E tem = this.tail.data;
        this.tail = this.tail.previous;
        this.tail.next = null;
        this.size--;
        return tem;
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
        else return this.head.data;
    }

    @Override
    public E peekBack() {
        if(this.size == 0) return null;
        else return this.tail.data;
    }

    @Override
    public E peek() {
        return peekBack();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }
    public String toString() {
        if(this.size == 0) return "[]";
        String result = "[";
        Node<E> val = this.head;
        while(val != null){
            result += val.data+", ";
            val = val.next;}
        result = result.substring(0, result.length() - 2);
         return result + "]";
    }
    private class LinkedDequeIterator implements Iterator<E> {
        private Node<E> current;
        private boolean fin;
        public LinkedDequeIterator(){
            this.current = head;
            this.fin = false;
        }
        public boolean hasNext(){
            return !this.fin && size > 0;
        }
        public E next(){
            if(current.next == null){
                fin = true;
                return current.data;
            }
            else{
                this.current = current.next;
                return this.current.previous.data;
            }
        }
    }
}
