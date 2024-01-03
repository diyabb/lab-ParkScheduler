package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements the Stack interface.
 * @param <E> type for LnikedStack
 * 
 * @author Diya Bhavsar
 */
public class LinkedStack<E> implements Stack<E> {

	/** Field of type LinkedAbstractList */
	private LinkedAbstractList<E> stack;
	
	/**
	 * Constructs an empty LinkedStack with the given capacity.
	 * @param capacity the capacity for the stack
	 */
	public LinkedStack(int capacity) {
        stack = new LinkedAbstractList<>(capacity);
        setCapacity(capacity);
	}
	
	/**
	 * Adds the element to the top of the stack.  
	 * @param element the element to be added
	 * @throws IllegalArgumentException if the capacity has been reached
	 */
	@Override
	public void push(E element) {
		// IllegalArgumentException if the capacity has been reached
		/*
        if (stack.size() >= capacity) {
            throw new IllegalArgumentException("Stack is full");
        }
        */
		
        stack.add(0, element);
	}

	/**
	 * Removes and returns the element at the top of the stack.
	 * @throws EmptyStackException if the stack is empty
	 * @return the top element 
	 */
	@Override
	public E pop() {
		// EmptyStackException if the stack is empty
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(0);
	}

	/**
	 * Returns true if the stack is empty.
	 * @return true if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the number of elements in the stack.
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return stack.size();
	}

	/**
	 * Sets the capacity of the stack.
	 * @param capacity the capacity
	 * @throws IllegalArgumentException if the capacity is negative or if it is less than the number
	 * of elements.
	 */
	@Override
	public void setCapacity(int capacity) {
		// IllegalArgumentException if the capacity is negative or if it is less than the number of elements.
		if (capacity < 0 || capacity < stack.size()) {
            throw new IllegalArgumentException();
        }
        stack.setCapacity(capacity);		
	}

}
