package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of LinkedRecursive class.
 * 
 * @author Diya Bhavsar
 */
public class LinkedListRecursiveTest {

	/**
	 * Tests the constructor.
	 */
	@Test
	void testLinkedListRecursive() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());        
	}

	/**
	 * Tests the size(0 method.
	 */
	@Test
	void testSize() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertEquals(0, list.size());

        list.add("apple");
        assertEquals(1, list.size());

        list.add("banana");
        list.add("cherry");
        assertEquals(3, list.size());

        list.remove("apple");
        assertEquals(2, list.size());	
        
	}

	/**
	 * Tests the isEmpty() method.
	 */
	@Test
	void testIsEmpty() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertTrue(list.isEmpty());

        list.add("apple");
        assertFalse(list.isEmpty());

        list.remove("apple");
        assertTrue(list.isEmpty());	
        
	}

	/**
	 * Tests the contains() method.
	 */
	@Test
	void testContains() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertFalse(list.contains("apple"));

        list.add("apple");
        assertTrue(list.contains("apple"));

        list.add("banana");
        assertTrue(list.contains("banana"));

        list.add("cherry");
        assertTrue(list.contains("cherry"));
        assertFalse(list.contains("grape"));	
        
	}

	/**
	 * Tests the add() method.
	 */
	@Test
	void testAddE() {
		//valid cases
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertTrue(list.add("apple"));
        assertTrue(list.add("banana"));
        assertTrue(list.add("cherry"));

//        assertFalse(list.add("banana")); 
        //invalid case
		assertThrows(IllegalArgumentException.class, () -> list.add("apple"));

        
	}

	/**
	 * Test the add() at index method.
	 */
	@Test
	void testAddIntE() {
		//valid cases
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add(0, "apple");
        assertEquals("apple", list.get(0));

        list.add(1, "banana");
        assertEquals("banana", list.get(1));

        list.add(1, "cherry");
        assertEquals("cherry", list.get(1));
        
        //invalid cases
        
        //duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add(1, "apple"));

		//invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "grape"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, "grape"));

		//null element
		assertThrows(NullPointerException.class, () -> list.add(1, null));

	}

	/**
	 * Tests the get() method.
	 */
	@Test
	void testGet() {
		//valid cases
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("cherry", list.get(2));	
        
		//invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
	}

	/**
	 * Tests the remove() at index method.
	 */
	@Test
	void testRemoveInt() {
		//valid cases
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        assertEquals("banana", list.remove(1));
        assertEquals(2, list.size());
        assertFalse(list.contains("banana"));	
        
        //remove from front
        assertEquals("apple", list.remove(0));
        
		//invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
        
	}

	/**
	 * Tests the remove() method.
	 */
	@Test
	void testRemoveE() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        assertTrue(list.remove("banana"));
        assertEquals(2, list.size());
        assertFalse(list.contains("banana"));

        
	}

	/**
	 * Tests the set() method.
	 */
	@Test
	void testSet() {
		//valid cases
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        assertEquals("banana", list.set(1, "blueberry"));
        assertEquals("blueberry", list.get(1));	
        
		//invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "kiwi"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(5, "kiwi"));
        
	}

}
