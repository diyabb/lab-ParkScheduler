package edu.ncsu.csc216.pack_scheduler.util;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * test class for LinkedAbstractList
 * @author John Kostic
 *
 */
class LinkedAbstractListTest { //tests adapted from ArrayListTest

	/**
	 * test for LinkedAbstractList constructor with String type
	 */
	@Test
	public void testLinkedAbstractList() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertEquals(0, testList.size());
	}
	
	/**
	 * test add() method
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertTrue(testList.add("3"));
		assertEquals(1, testList.size());
		assertEquals("3", testList.get(0));
		assertThrows(IllegalArgumentException.class, () -> testList.add(1, "3"));
		assertThrows(NullPointerException.class, () -> testList.add(1, null));
		
		assertTrue(testList.add("8"));
		assertEquals(2, testList.size());
		assertEquals("8", testList.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.add(-1, "6"));
	}
	
	/**
	 * test remove() method
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals(4, testList.size());
		assertEquals("21", testList.get(3));
		assertEquals("8", testList.remove(1));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(-1));
	}
	
	/**
	 * test remove() method
	 */
	@Test
	public void testRemoveEmpty() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(1));
	}
	
	/**
	 * test set() method
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals("11", testList.set(2, "12"));
	}
	
	/**
	 * test get() method
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<String> testList = new LinkedAbstractList<String>(10);
		assertTrue(testList.add("3"));
		assertTrue(testList.add("8"));
		assertTrue(testList.add("11"));
		assertTrue(testList.add("21"));
		assertEquals("11", testList.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.get(777));
	}

}
