package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Contains multiple test cases used on Conflict Exception
 */
class ConflictExceptionTest {
	
	/**
	 * Tests the default construction message of ConflictException
	 */
	@Test
	void testConflictException() {
		ConflictException ce = new ConflictException();
	    assertEquals("Schedule conflict.", ce.getMessage());
	}
	
	
	/**
	 * Tests constructing a ConflictException with a custom message
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

}
