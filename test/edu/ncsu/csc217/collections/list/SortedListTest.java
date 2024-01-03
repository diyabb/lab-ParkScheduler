package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;


/**
 * A collection of test cases for SortedList
 */
public class SortedListTest {
	
	/**
	 * tests the sorted list construction
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		//Test that the list grows by adding at least 11 elements
		//Remember the list's initial capacity is 10
		list.add("apple1");
		list.add("apple2");
		list.add("apple3");
		list.add("apple4");
		list.add("apple5");
		list.add("apple6");
		list.add("apple7");
		list.add("apple8");
		list.add("apple9");
		list.add("apple10");
		list.add("apple11");
		assertEquals(11, list.size());
	}
	
	
	/**
	 * tests the add function to a sorted list
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		// Test adding to the front, middle and back of the list
		list.add("apple");
		assertEquals("apple", list.get(0));
		
		list.add("azula");
		assertEquals("azula", list.get(1));
		
		list.add("cucumber");
		assertEquals("cucumber", list.get(3));
		
		// Test adding a null element
		assertThrows(NullPointerException.class, () -> list.add(null));
		
		// Test adding a duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add("apple"));
	}
	
	/**
	 * tests the getter function of an element of a list
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		// Test getting an element from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		
		// Add some elements to the list
		list.add("banana");
		list.add("apple");
		list.add("azula");
		list.add("cucumber");
		
		// Test getting an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		
		// Test getting an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
		
	}
	
	/**
	 * tests to see that items are removed by Remove function
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		// Test removing from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		
		// Add some elements to the list - at least 4
		list.add("apple");
		list.add("azula");
		list.add("banana");
		list.add("cucumber");
		
		// Test removing an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		
		// Test removing an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
		
		// Test removing a middle element
		assertEquals("azula", list.remove(1));
		
		// Test removing the last element
		assertEquals("cucumber", list.remove(2));
		
		// Test removing the first element
		assertEquals("apple", list.remove(0));
		
		// Test removing the last element
		assertEquals("banana", list.remove(0));
	}
	
	/**
	 * tests to see that proper index is returned by IndexOf
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		// Test indexOf on an empty list
		//Exception e2 = assertThrows(NullPointerException.class, () -> list.indexOf("apple"));
		//assertEquals(null, e2.getMessage(), null);
		assertEquals(-1, list.indexOf("apple"));
		
		// Add some elements
		list.add("apple");
		list.add("azula");
		list.add("banana");
		list.add("cucumber");
		
		// Test various calls to indexOf for elements in the list
		//and not in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(-1, list.indexOf("dikon"));
		assertEquals(3, list.indexOf("cucumber"));
		
		// Test checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
		
	}
	
	/**
	 * test to see if Clear functions clears out a list
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("azula");
		list.add("banana");
		list.add("cucumber");
		
		// Clear the list
		list.clear();
		
		// Test that the list is empty
		assertEquals(0, list.size());
	}
	
	/**
	 * test to see if IsEmpty catches empty lists
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		// Test that the list starts empty
		assertTrue(list.isEmpty());
		
		// Add at least one element
		list.add("apple");
		list.add("azula");
		
		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}
	
	/**
	 * test contains function to see that it checks if list contains elements
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		// Test the empty list case
		assertFalse(list.contains("apple"));
		
		// Add some elements
		list.add("apple");
		list.add("azula");
		
		// Test some true and false cases
		assertFalse(list.contains("banana"));
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("azula"));
	}
	
	/**
	 * tests equals function to see that it catches duplicate lists
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("azula");
		list1.add("banana");
		list1.add("cucumber");
		
		list2.add("apple");
		list2.add("azula");
		list2.add("banana");
		list2.add("cucumber");
		
		list3.add("Joey");
		list3.add("Stu");
		
		
		// Test for equality and non-equality
		//gives PMD issues no matter what
//		assertFalse(list1.equals(list3));
//		assertFalse(list2.equals(list3));
//		assertTrue(list1.equals(list2));
		assertNotEquals(list1, list3);
		assertNotEquals(list2, list3);
		assertEquals(list1, list2);
		
//		assertEquals(false, list1.equals(list3));
//		assertNotEquals(true, list2.equals(list3));
//		assertEquals(true, list1.equals(list2));
	}
	
	/**
	 * tests hash code to make sure its formatted properly
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make two lists the same and one list different
		list1.add("apple");
		list1.add("azula");
		list1.add("banana");
		list1.add("cucumber");
		
		list2.add("apple");
		list2.add("azula");
		list2.add("banana");
		list2.add("cucumber");
		
		list3.add("Joey");
		list3.add("Stu");
		
		// Test for the same and different hashCodes
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}

}
 
