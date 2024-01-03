package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Test class for LinkedList.
 * @author John Kostic
 * @author Diya Bhavsar
 */
class LinkedListTest {

	
	/** class constant for default init size */
	private static final int INIT_SIZE = 10;
	/** LinkedList of String object */
    private LinkedList<String> list;

	
	/**
	 * test for ArrayList constructor with String type
	 */
	@Test
	public void testLinkedList() {
		LinkedList<String> testList = new LinkedList<String>();
		assertEquals(0, testList.size());
		//tests for null previous and next
		ListIterator<String> testIterator = testList.listIterator(0);
		assertThrows(NoSuchElementException.class, () -> testIterator.previous());
		assertThrows(NoSuchElementException.class, () -> testIterator.next());
	}
	
	/**
	 * test add() method
	 */
	@Test
	public void testAdd() {
		ArrayList<String> testList = new ArrayList<String>();
		assertThrows(NullPointerException.class, () -> testList.add(null));
		assertTrue(testList.add("3"));
		assertEquals(1, testList.size());
		assertEquals("3", testList.get(0));
		
		assertTrue(testList.add("8"));
		assertEquals(2, testList.size());
		assertEquals("8", testList.get(1));
		
	    //invalid
		assertThrows(IllegalArgumentException.class, () -> testList.add("3"));
	}
	
	/**
	 * test set() method
	 */
	@Test
	public void testSet() {
		ArrayList<String> testList = new ArrayList<String>();
		//valid
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals("11", testList.set(2, "12"));
		
		//valid
	    LinkedList<String> testList1 = new LinkedList<>();
	    testList1.add("A");
	    testList1.add("B");
	    testList1.set(1, "X");
	    assertEquals("A", testList1.get(0));
	    assertEquals("X", testList1.get(1));
	    
	    //invalid
		assertThrows(NullPointerException.class, () -> testList1.set(1, null));
		assertThrows(IllegalArgumentException.class, () -> testList1.set(1, "X"));
	}
	
	/**
	 * test remove() method
	 */
	@Test
	public void testRemove() {
		ArrayList<String> testList = new ArrayList<String>();
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals(4, testList.size());
		assertEquals("21", testList.get(3));
		assertEquals("8", testList.remove(1));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.get(3));

	}
	
	/**
	 * test add() method for an IndexOutOfBoundsException
	 */
	@Test
	public void testAddOutOfBounds() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		assertThrows(IndexOutOfBoundsException.class, () -> testList.add(777, 777));
	}
	
	/**
	 * test add() method for an IllegalArgumentException for adding a duplicate element
	 */
	@Test
	public void testAddDuplicate() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0, 777);
		assertThrows(IllegalArgumentException.class, () -> testList.add(1, 777));
	}
	
	/**
	 * test add() method in which growArray() is called
	 */
	@Test
	public void testAddAndGrow() {
		ArrayList<String> testList = new ArrayList<String>();
		for (int i = 0; i < INIT_SIZE + 1; i++) {
			String testItem = "testingNumber" + i;
			testList.add(i, testItem);
		}
		assertEquals(11, testList.size());
	}
	

	/**
	 * Test for LinkedListIterator class has next method.
	 */
	@Test 
	public void testLinkendListIteratorHasNext() {
        list = new LinkedList<String>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        //has next
        ListIterator<String> iterator1 = list.listIterator(0);

        assertTrue(iterator1.hasNext());
        assertEquals("apple", iterator1.next());
        //tests nextIndex() and previousIndex()
        assertEquals(1, iterator1.nextIndex());
        assertEquals(0, iterator1.previousIndex());

        assertTrue(iterator1.hasNext());
        assertEquals("banana", iterator1.next());

        assertTrue(iterator1.hasNext());
        assertEquals("cherry", iterator1.next());
        
        
	}
        
//	/**
//	 * Test for LinkedListIterator class has next method.
//	 */
//	@Test 
//	public void testLinkendListIteratorHasPrevious() {
//        list = new LinkedList<String>();
//        list.add("apple");
//        list.add("banana");
//        list.add("cherry");
//        
//        //has previous
//        ListIterator<String> iterator = list.listIterator(3);
//
//        assertTrue(iterator.hasPrevious());
//        assertEquals("cherry", iterator.previous());
//
//        assertTrue(iterator.hasPrevious());
//        assertEquals("banana", iterator.previous());
//
//        assertTrue(iterator.hasPrevious());
//        assertEquals("apple", iterator.previous());
//        
//        
//	}
}
