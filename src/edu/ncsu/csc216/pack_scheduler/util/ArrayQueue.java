package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;


/**
 * Implements the Queue interface.
 * @param <E> the type for ArrayQueue 
 * 
 * @author Diya Bhavsar
 */
public class ArrayQueue<E> implements Queue<E> {

	/** ArrayList field type for Queue */
	private ArrayList<E> queue;
	
	/** The capacity of the queue */
	private int capacity;
	
	/**
	 * Constructs an empty ArrayQueue with the given capacity.
	 * @param capacity the capacity for the queue
	 * @throws IllegalArgumentException if the capacity is invalid
	 */
	public ArrayQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        
        queue = new ArrayList<>();
        setCapacity(capacity);		
	}
	
    /**
     * Adds the element to the back of the Queue. 
     * @param element the element to be added
     * @throws IllegalArgumentException if there is no room 
     */
	@Override
	public void enqueue(E element) {
        if (queue.size() >= capacity) {
            throw new IllegalArgumentException("Queue is full");
        }
        queue.add(element);		
	}

    /**
     * Removes and returns the element at the front of the Queue. 
     * @return the element at the front of the Queue
     * @throws NoSuchElementException if the Queue is empty
     */
	@Override
	public E dequeue() {
		if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return queue.remove(0);
	}

    /**
     * Returns true if the Queue is empty. 
     * @return true if the Queue is empty
     */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

    /**
     * Returns the number of elements in the Queue. 
     * @return the number of elements in the Queue
     */
	@Override
	public int size() {
		return queue.size();
	}

    /**
     * Sets the Queue's capacity. 
     * @param capacity the capacity to set
     * @throws IllegalArgumentException if the actual parameter is negative or 
     * if it is less than the number of elements in the Queue
     */
	@Override
	public void setCapacity(int capacity) {
        if (capacity < 0 || capacity < queue.size()) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
	}

//	/**
//	 * Checks if it contains the student
//	 * @param element the element
//	 * @return true if it exists, false if not
//	 * @throws IllegalArgumentException if element is null
//	 */
//	public boolean contains(E element) {
//		if (element == null) {
//			throw new IllegalArgumentException("Element cannot be null.");
//		}
//		for (int i = 0; i < size(); i++) {
//			if (queue.get(i).equals(element)) {
//				return true;
//			}
//		}
//		return false;
//	}

}
