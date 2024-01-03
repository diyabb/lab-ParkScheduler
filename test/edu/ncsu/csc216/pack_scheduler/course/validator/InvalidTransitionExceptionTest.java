package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvalidTransitionExceptionTest {
	
	/**
	 * Tests the default construction message of InvalidTransitionException
	 */
	@Test
	void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
	    assertEquals("Invalid FSM Transition.", ce.getMessage());
	}
	
	
	/**
	 * Tests constructing a InvalidTransitionException with a custom message
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
	    InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}
}
