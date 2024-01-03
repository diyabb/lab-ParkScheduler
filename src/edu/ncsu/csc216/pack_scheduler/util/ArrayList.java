package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**This class is responsible for creating and handling custom ArrayLists
 * whose type can change depending on the type of data being handled.
 * @author John Kostic
 * @param <E> generic type
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/**initial size of the ArrayList*/
	private static final int INIT_SIZE = 10;
	/** list with customizable object type */
	private E[] list;
	/** size of the list */
	private int size;
	
	/** constructor for custom ArrayList */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		Object[] obj = new Object[INIT_SIZE];
		list = (E[])obj;
	}
	
	/**returns the number of elements in the array */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * returns an item at a given index of a lists
	 * @throws IndexOutOfBoundsException if index > size
	 */
	@Override
	public E get(int index) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }
	    return list[index];
	}
	
	/**
	 * adds a new element to the lists
	 * @throws IndexOutOfBoundsException if index > size
	 * @throws IllegalArgumentException if duplicate element is to be added
	 * @throws NullPointerException if element is null
	 */
	@Override
	public void add(int index, E element) {
	    if (index < 0 || index > size) {
	        throw new IndexOutOfBoundsException();
	    }
	    for (int i = 0; i < size; i++) {
	    	if(list[i].equals(element)) {
	    		throw new IllegalArgumentException();
	    	}
	    }
	    if (element == null) {
	    	throw new NullPointerException();
	    }
	    if (size == list.length) {
	        growArray();
	    }
	    for (int i = size; i > index; i--) {
	        list[i] = list[i - 1];
	    }
	    list[index] = element;
	    size++;
	}
	
	/**
	 * sets the value of the list that was replaced at the particular index 
	 * @return list[index] - the value in the list that was set
	 * @throws IndexOutOfBoundsException if index is greater than size or less than zero
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate of another in list
	 */
	@Override
	public E set(int index, E element) {
	    if (index < 0 || index > size - 1) {
	        throw new IndexOutOfBoundsException();
	    }
	    if (element == null) {
	    	throw new NullPointerException();
	    }
	    
	    for (int i = 0; i < size; i++) {
	    	if(list[i].equals(element)) {
	    		throw new IllegalArgumentException();
	    	}
	    }
	    E value = list[index];
	    list[index] = element;
	    return value;
	}
	
	/**
	 * removes an element from the list
	 * @return value - the object removed
	 * @throws IndexOutOfBoundsException if index > size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index > size - 1) {
	        throw new IndexOutOfBoundsException();
	    }
		E value = list[index];
		// left shift elements
		for (int i = index; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		//make last element (is a duplicate) null
		list[size - 1] = null;
		size--;
		return value;
	}
	
	/**
	 * helper method for resizing arrays
	 */
	//TODO check if suppress can be avoided
	@SuppressWarnings("unchecked")
	private void growArray() {
		int newSize = list.length * 2;
		E[] temp = (E[]) new Object[newSize];
		for (int i = 0; i < size; i++) {
			temp[i] = list[i];
		}
		list = temp;
	}

}
