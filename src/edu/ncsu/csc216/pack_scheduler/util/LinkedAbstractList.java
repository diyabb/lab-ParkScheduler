package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * This class is responsible for creating and handling custom LinkedLists
 * whose type can change depending on the type of data being handled.
 * 
 * @author John Kostic
 * @author Blake Boykin
 * 
 * @param <E> generic type for objects in a given list
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

    /** front of a given list */
    private ListNode front;

    /** back of a given list */
    private ListNode back;

    /** number of elements currently in a given list */
    private int size;

    /** maximum number of elements a given list could hold */
    private int capacity;

    /**
     * constructor for LinkedAbstractList
     * 
     * @param capacity maximum number of elements the list could hold
     */
    public LinkedAbstractList(int capacity) {
        setCapacity(capacity);
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    /**
     * setter for Capacity
     * 
     * @param capacity maximum number of elements the list could hold
     * @throws IllegalArgumentException if capacity is less than zero or the
     *                                  list's size
     */
    public void setCapacity(int capacity) {
        if (capacity < 0 || capacity < size) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    /**
     * This method adds a new element to the list
     * 
     * @param index   at which the element is to be added
     * @param element to be added
     * @throws NullPointerException      if the element to add is null
     * @throws IllegalArgumentException  if duplicate element in the list or if
     *                                   the list is full
     * @throws IndexOutOfBoundsException if index to be added does not exist in the
     *                                   list
     */
    @Override
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (size == capacity) {
            throw new IllegalArgumentException("List is full");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate element");
        }

        if (front == null) {
            // Special case for an empty list
            front = new ListNode(element);
            back = front;
        } else if (index == 0) {
            // Adding at the front of the list
            front = new ListNode(element, front);
        } else if (index == size) {
            // Adding at the back of the list
            back.next = new ListNode(element);
            back = back.next;
        } else {
            // Adding in the middle of the list
            ListNode current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = new ListNode(element, current.next);
        }
        size++;
    }

    /**
     * removes an element from the list
     * 
     * @param index where the element to be removed is
     * @throws IndexOutOfBoundsException if the index in question does not exist in
     *                                   the list
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E value = null;

        if (index == 0) {
            // Removing the front element
            value = front.data;
            front = front.next;
            if (front == null) {
                // The list is now empty
                back = null;
            }
        } else {
            ListNode current = front;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            value = current.next.data;
            current.next = current.next.next;
            if (current.next == null) {
                // Removed the last element, update the 'back' reference
                back = current;
            }
        }
        size--;
        return value;
    }

    /**
     * sets the value of the list that was replaced at the particular index
     * 
     * @return data the value in the list that was replaced
     * @throws IndexOutOfBoundsException if index is greater than size or less than
     *                                   zero
     * @throws NullPointerException      if element is null
     * @throws IllegalArgumentException  if element is a duplicate of another in
     *                                   list
     */
    @Override
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate element");
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListNode current = front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E data = current.data;
        current.data = element;
        return data;
    }

    /**
     * returns an item at a given index of a lists
     * 
     * @throws IndexOutOfBoundsException if index > size
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ListNode current = front;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * returns the number of elements in the array
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private class ListNode {

        /** data in the node */
        private E data;

        /** the next node in the list */
        private ListNode next;

        /**
         * constructor for ListNode
         * 
         * @param data the data being stored in the node
         */
        public ListNode(E data) {
            this.data = data;
            this.next = null;
        }

        /**
         * constructor for ListNode
         * 
         * @param data the data being stored in the node
         * @param next the reference to the next node
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
