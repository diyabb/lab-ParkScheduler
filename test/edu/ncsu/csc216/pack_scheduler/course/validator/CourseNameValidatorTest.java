package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Test class for CourseNameValidator
 * @author John Kostic
 *
 */
public class CourseNameValidatorTest {
	/**
	 * Test method for valid state transition out of state L with a digit
	 */
	@Test
	void testValidLTransitionDigit() {
		CourseNameValidator c = new CourseNameValidator();
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
		CourseNameValidator c = new CourseNameValidator();
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
		CourseNameValidator c = new CourseNameValidator();
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
		CourseNameValidator c = new CourseNameValidator();
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
		CourseNameValidator c = new CourseNameValidator();
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
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("C!103A"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition for Initial when given digit
	 */
	@Test
	void testInvalidInitialTransitionDigit() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("303CSC"));
		assertEquals("Course name must start with a letter.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of LLLL when given another letter
	 */
	@Test
	void testInvalidLLLLTransitionLetter() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSCSC103A"));
		assertEquals("Course name cannot start with more than 4 letters.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of D
	 */
	@Test
	void testInvalidDTransitionLetter() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC1A"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of DD
	 */
	@Test
	void testInvalidDDTransitionLetter() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC10A"));
		assertEquals("Course name must have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of DDD when given another digit
	 */
	@Test
	void testInvalidDDDTransitionDigit() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC1010A"));
		assertEquals("Course name can only have 3 digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of Suffix State when given another digit
	 */
	@Test
	void testInvalidSTransitionDigit() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC101A1"));
		assertEquals("Course name cannot contain digits after the suffix.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of S when given another letter
	 */
	@Test
	void testInvalidSTransitionLetter() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC101AA"));
		assertEquals("Course name can only have a 1 letter suffix.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}
	
	/**
	 * Test method for invalid state transition of S when given another letter
	 */
	@Test
	void testInvalidSTransitionOther() {
		CourseNameValidator c = new CourseNameValidator();
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> c.isValid("CSC216-001"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage(), "Incorrect exception thrown for invalid transition");
	}

}
