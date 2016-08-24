package com.github.fzakaria.collection;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A linked list collection that returns the inner {@link Node} structure as opposed to {@link java.util.LinkedList}
 * which abstracts the inner structure away from you. This can be sometimes useful when you want to do O(1) insert
 * and deletes if you store reference to the {@link Node}
 */
public class DoubleLinkedList<T> implements Iterable<T>  {

    /**
     * The node class used by this double linked list class
     * @param <T>
     */
    public static class Node<T> {

        private Node next;
        private Node prev;
        private T item;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrev() {
            return prev;
        }
    }

    /**
     * Pointer to the head of the linked list
     */
    private Node<T> head;

    /**
     * Pointer to the tail of the linked list
     */
    private Node<T> tail;

    /**
     * Constructor
     */
    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Helpful constructor to create a list given a vararg amount of items
     * @param items The items to create in the linked list
     * @param <T> The type parameter
     * @return  a constructed double linked list
     */
    public static <T> DoubleLinkedList<T> create(T... items) {
        DoubleLinkedList<T> list = new DoubleLinkedList<>();
        for (T item : items) {
            list.insertEnd(new Node<>(item));
        }
        return list;
    }

    public Node<T> getTail() {
        return tail;
    }

    public Node<T> getHead() {
        return head;
    }

    /**
     * Insert newNode after node
     * @param node
     * @param newNode
     */
    public synchronized void insertAfter(Node node, Node newNode) {
        newNode.prev = node;
        newNode.next = node.next;
        if (node.next == null) {
            tail = newNode;
        } else {
            node.next.prev = newNode;
        }
        node.next = newNode;
    }

    /**
     * Insert newNode before node
     * @param node
     * @param newNode
     */
    public synchronized void insertBefore(Node node, Node newNode) {
        newNode.prev = node.prev;
        newNode.next = node;
        if (node.prev == null) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
    }

    /**
     * Insert newNode as the new head
     * @param newNode
     */
    public synchronized void insertBeginning(Node newNode) {
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.prev = null;
            newNode.next = null;
        } else {
            insertBefore(head, newNode);
        }
    }

    /**
     * Insert newNode as the new tail
     * @param newNode
     */
    public synchronized void insertEnd(Node newNode) {
        if (tail == null) {
            insertBeginning(newNode);
        } else {
            insertAfter(tail, newNode);
        }
    }

    /**
     * Remove node from the linked list
     * @param node
     */
    public synchronized void remove(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    /**
     * Create a
     * @return
     */
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private DoubleLinkedList.Node<T> curr = head;
            public boolean hasNext() {
                return curr != null;
            }
            public T next() {
                Node<T> temp = curr;
                curr = curr.next;
                return temp.getItem();
            }
            public void remove() {
            }
        };
    }

}
