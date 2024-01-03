package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedListRecursive represents a custom implementation of a recursive linked list that 
 * doesn’t allow for null elements or duplicate elements as defined by the equals() method.
 * 
 * @param <E> the generic parameter for the list
 * 
 * @author Diya Bhavsar
 */
public class LinkedListRecursive<E> {

	/** The front of the list */
	private ListNode front;
	
	/** The size of the list */
	private int size;
	
	/**
	 * Constructs an empty list.
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}
	
	/**
	 * Returns the size of the list.
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the list is empty or not.
	 * @return true if the list is empty or false if not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Checks if the list contains the given element.
	 * @param element the given element
	 * @return true if the list contains the element, false if not
	 */
	public boolean contains(E element) {
		// handles the special case of an empty list
		if (size == 0) {
			return false;
		}
		
		// If the list is not empty, then the flow is transfered to the private method
		return front.contains(element);
	}
	
	/**
	 * Adds the given element to the end of the list.
	 * @param element the element to be added to the list
	 * @return true if the element is added, false if not
	 * @throws IllegalArgumentException if the element is already exists
	 */
	public boolean add(E element) {
		// checks that the element isn’t already in the list (an IllegalArgumentException is thrown if the element already exists) 
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate found.");
		}
		
		if (element == null) {
			throw new NullPointerException("Element is null");
		}
		
		// handles the special case of adding a node to an empty list
		if (size == 0) {
			front = new ListNode(element, null);
			size++;
		} else {
			// if the list is not empty, then the flow is transfered to a private method
			front.add(element);
			size++;
			return true;
		}		
		
		return true;
	}
	
	/**
	 * Adds the given element at the specified index.
	 * @param idx the index where the element has to be added
	 * @param element the element to be added
	 * @throws IllegalArgumentException if the element already exists or is null
	 * @throws IndexOutOfBoundsException if the index is invalid
	 * @throws NullPointerException if the element is null
	 * @return true if successful, false if not
	 */
	public boolean add(int idx, E element) {
		
		// IllegalArgumentException if the element already exists or is null
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate found");
		}
		
		// IndexOutOfBoundsException if the index is invalid
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		// NullPointerException if the element is null
		if (element == null) {
			throw new NullPointerException("Cannot add null element");
		}
		
		// handles the special case of adding a node to the front of the list
        if (idx == 0) {
            ListNode node = new ListNode(element, null);
            if(size == 0) {
            	front = node;
            	size++;
            } else {
            	node.next = front;
            	front = node;
            	size++;
            }
        } else {
            // else the flow is transferred to a private method
            front.add(idx, element);
            size++;
        }
        return true;
	}
	
	/**
	 * Returns the element at the specified index.
	 * @param idx the index
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 * @throws NoSuchElementException if the front is null
	 */
	public E get(int idx) {
		// checks the bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		if (front == null) {
			throw new NoSuchElementException("Element is null");
		}
		
		// the flow is transfered to a private method
		return front.get(idx);
	}
	
	/**
	 * Removes the element at the specified index.
	 * @param idx the index
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
    public E remove(int idx) {
    	// handles the bounds checking
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        E elementToRemove;
        
        // the special case of removing the first node in the list.
        if (idx == 0) {
        	elementToRemove = front.data;
            front = front.next;
        } else {
        	// the flow is transfered to a private method
        	elementToRemove = front.remove(idx);
        }
        size--;
        return elementToRemove;
    }	
    
    /**
     * Removes the specified element.
     * @param element the element to remove
     * @return true of the element is removed or false if not
     */
    public boolean remove(E element) {
    	// checks if the element is null or if the list is empty
        if (element == null) {
            return false;
        }
        if(isEmpty()) {
        	return false;
        }
        // handles the special case of removing the first node in the list
        if (element.equals(front.data)) {
            front = front.next;
            size--;
            return true;
        } else {
        	// the flow is transfered to a private method
            return front.remove(element);
        }
    }

    /**
     * Sets the element at the given index.
     * @param idx the index for the element
     * @param element the element
     * @return the element
	 * @throws IndexOutOfBoundsException if the index is invalid
	 * @throws NullPointerException is the element is null
	 * @throws IllegalArgumentException if the element already exists
     */
    public E set(int idx, E element) {
    	// bound checking
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index or element.");
        }
		// NullPointerException if the element is null
		if (element == null) {
			throw new NullPointerException("Cannot add null element");
		}
	
		if(this.contains(element)) {
			throw new IllegalArgumentException("Duplicate found");
		}
		        
    	// the flow is transfered to a private method
        return front.set(idx, element);
    }
    
	
	/*
	 * Represents a node in the list.
	 * 
	 * @author Diya Bhavsar
	 */
	private class ListNode {
		
		/** The data in the node */
		public E data;
		
		/** The next node in the list */
		public ListNode next;
		
        /**
         * Constructs a ListNode with the given data and next node. *
         * @param data the data
         * @param next the next node
         */
        private ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Sets the element at the given index.
         * @param idx the given index
         * @param element the element
         * @return returns the element        
         */
        public E set(int idx, E element) {
            if (idx == 0) {
                E prevElement = data;
                data = element;
                return prevElement;
            } else {
                return next.set(idx - 1, element);
            }
		}

        /**
         * Removes the given element.
         * @param element the element to be removed
         * @return true if the element is removed, false if not
         */
        public boolean remove(E element) {
//            if (next != null && next.data.equals(element)) {
//                next = next.next;
//                return true;
//            } else if (next != null) {
//                return next.remove(element);
//            } else {
//                return false;
//            }
            if (next == null) {
            	return false;
            } else {
            	if(next.data.equals(element)) {
            		if(next.next == null) {
            			next = null;
            			size--;
            			return true;
            		} else {
            			next = next.next;
            			size--;
            			return true;
            		}
            	} else {
            	next.remove(element);
            	size--;
            	return true;
            	}
            }
		}

        /**
         * Removes the element at the given index.
         * @param idx the given index
         * @return the removed element
         */
        public E remove(int idx) {
            if (idx == 1) {
                    E elementToRemove = next.data;
                    next = next.next;
                    return elementToRemove;
            } else {
                return next.remove(idx - 1);
            } 
		}

        /**
         * Returns the element at the given index.
         * @param idx the given index
         * @return the element at the index
         */
        public E get(int idx) {
            if (idx == 0) {
                return data;
            } else {
                return next.get(idx - 1);
            }
		}

        /**
         * Adds the given element at the specified index.
         * @param idx the specified index
         * @param element the given element
         */
        public void add(int idx, E element) {
            if (idx == 1) {
            	if (next == null) {
                    next = new ListNode(element, null);
            	} else {
                    next = new ListNode(element, next);
            	}
            } else {
                if (next != null) {
                    next.add(idx - 1, element);
                }
            }
		}

		/**
		 * Add the given element to the list.
		 * @param element the element to add
		 */
        public void add(E element) {
	        if (next == null) {
	            next = new ListNode(element, null);
	        } else {
	            next.add(element);
	        }			
		}

		/**
		 * Checks if the list contains the given element.
		 * @param element the element to check
		 * @return true if the element is found, false if not
		 */
        public boolean contains(E element) {
	        if (data.equals(element)) {
	            return true;
	        } else if (next != null) {
	            return next.contains(element);
	        } else {
	            return false;
	        }
		}

	}
}
