package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of ArrayQueue.
 * 
 * @author Diya Bhavsar
 */
class ArrayQueueTest {
	
	/** Queue to test */
	private ArrayQueue<String> queue;
	
	@BeforeEach
	public void setUp() {
        queue = new ArrayQueue<>(5);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	void testArrayQueue() {
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());       
	}

	/**
	 * Tests the enqueue method.
	 */
	@Test
	void testEnqueue() {
		// Inserting a single element into the Queue
        queue.enqueue("First");
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
	}

	/**
	 * Tests the dequeue method.
	 */
	@Test
	void testDequeue() {
        queue.enqueue("First");
        // Removing a single element from the Queue
        assertEquals("First", queue.dequeue());
        assertTrue(queue.isEmpty());
        
        // Adding multiple elements
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        
        // Removing multiple elements
        assertEquals("First", queue.dequeue());
        assertEquals("Second", queue.dequeue());
        assertEquals("Third", queue.dequeue());
        assertTrue(queue.isEmpty());
	}

	/**
	 * Tests the isEmpty method.
	 */
	@Test
	void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue("First");
        assertFalse(queue.isEmpty());	        
	}
	
	/**
	 * Tests the setCapacity method.
	 */
	@Test
	void testSetCapacity() {
		// invalid capacity- negative
		assertThrows(IllegalArgumentException.class, () -> queue.setCapacity(-1));
		
		// invalid capacity- less than size
		queue.enqueue("A");
		queue.enqueue("B");
		assertThrows(IllegalArgumentException.class, () -> queue.setCapacity(1));
				
		//valid 
		queue.setCapacity(2);
		assertEquals(2, queue.size());
		
	}

}
