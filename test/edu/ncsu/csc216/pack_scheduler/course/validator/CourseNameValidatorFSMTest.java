/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the state transitions of the CourseNameValidator program
 * @author Nate Powers
 * @author John Kostic
 */
class CourseNameValidatorFSMTest {
	
	/**
	 * Test method for valid state transition out of state L with a digit
	 */
	@Test
	void testValidLTransitionDigit() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		try {
			assertTrue(c.isValid("C103"));
		} catch (InvalidTransitionException i) {
			fail("Invalid Transition found");
		}
	}
	
	/**
	 * Test method for valid state transition out of state LL with a letter
	 */
	@Test
	void testValidLLTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		try {
			assertTrue(c.isValid("CS103"));
		} catch (InvalidTransitionException i) {
			fail("Invalid Transition found");
		}
	}
	
	/**
	 * Test method for valid state transition out of state LLL with a letter
	 */
	@Test
	void testValidLLLTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		try {
			assertTrue(c.isValid("CSC103"));
		} catch (InvalidTransitionException i) {
			fail("Invalid Transition found");
		}
	}
	
	/**
	 * Test method for valid state transition out of state LLLL with a digit
	 */
	@Test
	void testValidLLLLTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		try {
			assertTrue(c.isValid("CSCA103"));
		} catch (InvalidTransitionException i) {
			fail("Invalid Transition found");
		}
	}
	
	/**
	 * Test method for valid state transition out of state DDD with a letter (suffix)
	 */
	@Test
	void testValidDDDTransitionSuffix() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		assertAll("Suffixes",
				() -> assertTrue(c.isValid("CSCA103A")),
				() -> assertTrue(c.isValid("CSC103A")),
				() -> assertTrue(c.isValid("CS103A")),
				() -> assertTrue(c.isValid("C103A")));
	}
	
	/**
	 * Test method for invalid state transition for L given a non-digit, non-letter character
	 */
	@Test
	void testInvalidLTransitionOther() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("C!103A"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition for Initial when given digit
	 */
	@Test
	void testInvalidInitialTransitionDigit() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("303CSC"));
		assertEquals("Course name must start with a letter.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of LLLL when given another letter
	 */
	@Test
	void testInvalidLLLLTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSCSC103A"));
		assertEquals("Course name cannot start with more than 4 letters.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of D when given a letter
	 */
	@Test
	void testInvalidDTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC1A"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of DD when given a letter
	 */
	@Test
	void testInvalidDDTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC10A"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of DDD when given another digit
	 */
	@Test
	void testInvalidDDDTransitionDigit() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC1010A"));
		assertEquals("Course name can only have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of S when given another digit
	 */
	@Test
	void testInvalidSTransitionDigit() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC101A1"));
		assertEquals("Course name cannot contain digits after the suffix.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of S when given another letter
	 */
	@Test
	void testInvalidSTransitionLetter() {
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC101AA"));
		assertEquals("Course name can only have a 1 letter suffix.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}

}
