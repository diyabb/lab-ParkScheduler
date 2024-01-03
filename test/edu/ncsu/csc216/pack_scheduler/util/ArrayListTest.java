package edu.ncsu.csc216.pack_scheduler.util;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * test class for ArrayList
 * @author John Kostic
 */
public class ArrayListTest { //tests modeled adapted from the 10/24 lecture
	
	/** class constant for default init size */
	private static final int INIT_SIZE = 10;
	
	/**
	 * test for ArrayList constructor with String type
	 */
	@Test
	public void testArrayList() {
		ArrayList<String> testList = new ArrayList<String>();
		assertEquals(0, testList.size());
	}
	
	/**
	 * test add() method
	 */
	@Test
	public void testAdd() {
		ArrayList<String> testList = new ArrayList<String>();
		assertTrue(testList.add("3"));
		assertEquals(1, testList.size());
		assertEquals("3", testList.get(0));
		
		assertTrue(testList.add("8"));
		assertEquals(2, testList.size());
		assertEquals("8", testList.get(1));
	}
	
	/**
	 * test set() method
	 */
	@Test
	public void testSet() {
		ArrayList<String> testList = new ArrayList<String>();
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals("11", testList.set(2, "12"));
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

}
