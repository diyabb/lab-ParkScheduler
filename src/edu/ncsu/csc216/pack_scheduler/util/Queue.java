package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for the queue data structure.
 * @param <E> the type for Queue
 * 
 * @author Diya Bhavsar
 */
public interface Queue<E> {

    /**
     * Adds the element to the back of the Queue. 
     * @param element the element to be added
     * @throws IllegalArgumentException if there is no room 
     */
    void enqueue(E element);

    /**
     * Removes and returns the element at the front of the Queue. 
     * @return the element at the front of the Queue
     */
    E dequeue();

    /**
     * Returns true if the Queue is empty. 
     * @return true if the Queue is empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the Queue. 
     * @return the number of elements in the Queue
     */
    int size();

    /**
     * Sets the Queue's capacity. 
     * @param capacity the capacity to set
     * @throws IllegalArgumentException if the actual parameter is negative or 
     * if it is less than the number of elements in the Queue
     */
    void setCapacity(int capacity);
}
