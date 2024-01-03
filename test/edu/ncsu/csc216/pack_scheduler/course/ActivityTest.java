package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Contains multiple test cases used on ACtivity to check conflicts
 */
class ActivityTest {
	
	/**
	 * Tests that Activity.checkConflict does not throw exception when there is not a conflict
	 */
	@Test
	public void testCheckConflict() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		
	    
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that Activity.checkConflict does throw exception with the correct message for schedule conflict
	 */
	@Test
	public void testCheckConflictWithConflict() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "M", 1330, 1445);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests conflicts on courses with the same start time
	 */
	@Test
	public void testCheckConflictWithSameStartTime() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "001", 3, "sesmith5", 10, "M", 1330, 1500);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests conflicts on courses with the same start time as the end time of another
	 */
	@Test
	public void testCheckConflictWithSameStartTimeAsEndTime() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1215, 1330);
	    Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "001", 3, "sesmith5", 10, "M", 1330, 1500);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests for no conflicts on courses with the same start time different days
	 */
	@Test
	public void testCheckConflictWithSameStartTimeDifferentDays() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1215, 1330);
	    Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "001", 3, "sesmith5", 10, "TH", 1330, 1500);
		
		
	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests for conflicts on a course and event with the same start time same day
	 */
	@Test
	public void testCheckConflictCourseArranged() {
		try {
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
	    Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "001", 3, "sesmith5", 10, "A");

	    assertDoesNotThrow(() -> a1.checkConflict(a2));
	    assertDoesNotThrow(() -> a2.checkConflict(a1));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
