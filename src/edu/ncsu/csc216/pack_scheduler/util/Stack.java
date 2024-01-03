package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for the stack data structure.
 * @param <E> type for Stack
 * 
 * @author Diya Bhavsar
 */
public interface Stack<E> {
	/**
	 * Adds the element to the top of the stack.  
	 * @param element the element to be added
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	void push(E element);
	
	/**
	 * Removes and returns the element at the top of the stack.
	 * @return the top element 
	 */
	E pop();
	
	/**
	 * Returns true if the stack is empty.
	 * @return true if the stack is empty
	 */
	boolean isEmpty();

	
	/**
	 * Returns the number of elements in the stack.
	 * @return the size of the stack
	 */
	int size();
	
	/**
	 * Sets the capacity of the stack.
	 * @param capacity the capacity
	 * @throws IllegalArgumentException if the capacity is negative or if it is less than the number
	 * of elements.
	 */
	void setCapacity(int capacity);	
	
}
