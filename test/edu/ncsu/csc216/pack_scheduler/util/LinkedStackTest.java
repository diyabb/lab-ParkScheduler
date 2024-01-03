package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of LinkedStack.
 * 
 * @author Diya Bhavsar
 */
class LinkedStackTest {

	/** The stack to test */
	private LinkedStack<String> stack;
	
	@BeforeEach
	public void setUp() {
		stack = new LinkedStack<>(5);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	void testLinkedStack() {
		assertTrue(stack.isEmpty());
		assertEquals(0, stack.size());
	}

	/** 
	 * Tests for the push method.
	 */
	@Test
	void testPush() {
		//Inserting a single element into the stack
		stack.push("First");
		assertEquals(1, stack.size());
		assertFalse(stack.isEmpty());
	}

	/**
	 * Tests for the pop method.
	 */
	@Test
	void testPop() {
		stack.push("First");
		
		//Removing a single element from the stack
		String pop = stack.pop();
		assertEquals("First", pop);
		assertTrue(stack.isEmpty());
		assertEquals(0, stack.size());
		
		//Attempting to remove an element from an empty stack
		assertThrows(EmptyStackException.class, () -> stack.pop());

		
	}

	/** 
	 * Tests for the size method.
	 */
	@Test
	void testSize() {
		//Inserting multiple elements into the stack
		stack.push("First");
		stack.push("Second");
		stack.push("Third");

		assertEquals(3, stack.size());
	}

	/** 
	 * Tests for the setCapacity method.
	 */
	@Test
	void testSetCapacityInvalid() {
		// invalid capacity- negative
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1));
		
		// invalid capacity- less than size
		stack.push("A");
		stack.push("B");
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(1));
				
		//valid 
		stack.setCapacity(2);
		assertEquals(2, stack.size());
	}

}
