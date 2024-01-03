package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This class creates a linked list of nodes using generic data
 * @author John Kostic
 *
 * @param <E> generic data type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	
	/**front node of the list*/
	private ListNode front;
	/**back node of the list */
	private ListNode back;
	/**size of the list */
	private int size;
	
	/**
	 * constructor for LinkedList
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	/**
	 * iterates through a LinkedList of generic type E
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index); //format gotten in OH
	}
	
	/**
	 * returns the size of the list
	 * @return size size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * sets the value of the list that was replaced at the particular index 
	 * @throws IllegalArgumentException if the element in question is not in the list
	 */
	@Override
	public E set(int index, E element) {
		if (super.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}
	
	/**
	 * adds a new element to the list
	 * @throws IllegalArgumentException if the element is already in the list
	 */
	@Override
	public void add(int index, E element) {
		if (super.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}

	/**
	 * This class creates and handles the nodes in the LinkedList
	 * @author John Kostic
	 *
	 */
	private class ListNode {
		
		/**data stored in the nodes */
		public E data;
		/**reference to the next node */
		public ListNode next;
		/**reference to the previous node */
		public ListNode prev;
		
		/**
		 * constructor for ListNode
		 * @param data stored in the node
		 */
		public ListNode(E data) {
			this.data = data;
		}
		
		/**
		 * constructor for ListNode
		 * @param data data stored in the node
		 * @param prev reference to the previous node
		 * @param next reference to the next node
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * This class iterates through the LinkedList and handles the nodes and data therein.
	 * @author John Kostic
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		
		/** the ListNode that was returned on the last call to either previous() or next()  */
		private ListNode lastRetrieved;
		/** the ListNode that would be returned no a call to previous() */
		public ListNode previous;
		/** the ListNode that would be returned no a call to next() */
		public ListNode next;
		/** the index that would be returned on a call to previousIndex() */
		public int previousIndex;
		/**index that would be returned on a call to nextIndex() */
		public int nextIndex;
		
		/**
		 * Constructor for LinkedListIterator
		 * @param index of the node being handled
		 * @throws IndexOutOfBoundsException if index in question is not in the list
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			previous = front;
			next = front.next;
			for (int i = 0; i < index; i++) {
				previous = next;
				next = previous.next;
			}
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}
		
		/**
		 * checks if there is data in the next node
		 * @return true if there is data in the reference to next, false if otherwise
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}
		
		/**
		 * returns the element in the next node
		 * @return data found in the reference to next
		 * @throws NoSuchElementException if reference to next is null
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			E temp = next.data;
			previous = next;
			next = previous.next;
			nextIndex++;
			previousIndex++;
			return temp;
		}
		/**
		 * checks if there is data in the previous node
		 * @return true if there is data in the reference to previous, false if otherwise
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
		 * returns the element in the previous node
		 * @return data found in the reference to previous
		 * @throws NoSuchElementException if reference to previous is null
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			E temp = previous.data;
			next = previous;
			previous = next.prev;
			nextIndex--;
			previousIndex--;
			return temp;
		}
		
		/**
		 * returns index of reference to next
		 * @return index of reference to next
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}
		
		/**
		 * returns index of reference to previous
		 * @return index of reference to previous
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}
		
		/**
		 * removes data from the list
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() { //is lastRetrieved being skipped?
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
		    ListNode before = lastRetrieved.prev;
		    ListNode after = lastRetrieved.next;

		    before.next = after;
		    after.prev = before;
			
			//change references to skip lastRetrieved
			lastRetrieved = null;
			size--;
			
		}
		
		/**
		 * modifies the element returned by the last call to previous() or next()
		 * @param element to be modified
		 * @throws NullPointerException if element is null
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void set(E element) {
			if (element == null) {
				throw new NullPointerException();
			}
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.data = element;
			
		}
		
		/**
		 * adds a new node and data to the list
		 * @param element to be added
		 * @throws NullPointerException if reference to the new node is null
		 */
		@Override
		public void add(E element) {
			if (element == null) {
				throw new NullPointerException();
			}
			ListNode addedNode  = new ListNode(element, previous, next);
			next.prev = addedNode;
			previous.next = addedNode;
			previous = addedNode;
			nextIndex++;
			previousIndex++;
			size++;
			lastRetrieved = null;			
		} 
		
	}


}
