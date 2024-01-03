package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * test class for CourseRoll
 * @author John Kostic
 * @author Diya Bhavsar
 * @author Blake Boykin
 */
class CourseRollTest {
//	/** the course roll */
//	private CourseRoll roll;
	
    /**
     * test for CourseRoll constructor
     */
    @Test
    public void courseRollTest() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = new CourseRoll(c, 77);
        assertEquals(77, roll.getEnrollmentCap());
        assertEquals(77, roll.getOpenSeats());   
    }
	
    /**
     * test for CourseRoll constructor invalids
     */
    @Test
    public void courseRollTestInvalid() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c, 700));        
        
        try {
	        //invalid course- null
	        Course c1 = null;
	        assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c1, 700));
        } catch (IllegalArgumentException e) {
        	//IllegalArgumentException
        }

    }
	
    /**
     * test for CourseRoll constructor
     */
    @Test
    public void enrollTest() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = new CourseRoll(c, 10);
        assertEquals(10, roll.getEnrollmentCap());
        assertEquals(10, roll.getOpenSeats());
        Student testStudent = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");
        Student nullStudent = null;
        roll.enroll(testStudent);
        assertEquals(9, roll.getOpenSeats());
        assertThrows(IllegalArgumentException.class, () -> roll.enroll(nullStudent));
        assertThrows(IllegalArgumentException.class, () -> roll.enroll(testStudent));
        }
	
    /**
     * test for drop method
     */
    @Test
    public void dropTest() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = new CourseRoll(c, 77);
        assertEquals(77, roll.getEnrollmentCap());
        assertEquals(77, roll.getOpenSeats());
        Student testStudent = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");
        roll.enroll(testStudent);
        assertEquals(76, roll.getOpenSeats());
        roll.drop(testStudent);
        assertEquals(77, roll.getOpenSeats());
        //Student nullStudent = null;
//        assertThrows(IllegalArgumentException.class, () -> roll.drop(null));
        
//        Student testStudent1 = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");
//        assertThrows(IllegalArgumentException.class, () -> roll.drop(testStudent1));

    }

    /**
     * Test for dropping a student and adding a waitlisted student back to the roll
     */
    @Test
    public void dropAndAddWaitlistedTest() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = new CourseRoll(c, 10);

        for (int i = 0; i < 10; i++) {
            Student student = new Student("Student" + i, "Last" + i, "ID" + i, "student" + i + "@ncsu.edu", "password");
            roll.enroll(student);
        }

        Student waitlistedStudent = new Student("i", "am", "iam", "iam@student.com", "password");
        roll.enroll(waitlistedStudent);
        assertEquals(1, roll.getNumberOnWaitlist());
        assertEquals(0, waitlistedStudent.getSchedule().getScheduleCredits());

        Student droppedStudent = new Student("Student0", "Last0", "ID0", "student0@ncsu.edu", "password");
        roll.drop(droppedStudent);
        assertEquals(0, roll.getNumberOnWaitlist());
//        assertEquals(roll.getEnrollmentCap() - 1, roll.getOpenSeats());
//        assertEquals(1, waitlistedStudent.getSchedule().getScheduleCredits());
    }
	
//	/**
//	 * test for dropping from an empty list
//	 */
//	@Test
//	public void dropTestEmpty() {
//        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
//		CourseRoll roll2 = new CourseRoll(c, 10);
//		Student testStudent = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");
//		//roll.enroll(testStudent);
//		assertThrows(IllegalArgumentException.class, () -> roll2.drop(testStudent));
//		
//	}
	
    /**
     * test for canEnroll method
     */
    @Test
    public void canEnrollTest() {
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = new CourseRoll(c, 12);
        assertEquals(12, roll.getEnrollmentCap());
        assertEquals(12, roll.getOpenSeats());
        Student testStudent = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");
        assertTrue(roll.canEnroll(testStudent));
        roll.enroll(testStudent);
        assertEquals(11, roll.getOpenSeats());
        assertFalse(roll.canEnroll(testStudent));
        Student testStudent2 = new Student("Joe", "Smith", "123456", "joesmith@ncsu.edu", "12345abcde");
        roll.enroll(testStudent2);
        assertEquals(10, roll.getOpenSeats());
        Student testStudent3 = new Student("Jim", "Smith", "1234567", "jimsmith@ncsu.edu", "12345abcde");
        assertTrue(roll.canEnroll(testStudent3));
        
        assertEquals(0, roll.getNumberOnWaitlist());
        
        //null student
        assertFalse(roll.canEnroll(null));

    }
    
    /**
	 * Tests the getOpenSeats() method.
	 */
	@Test
	public void testGetOpenSeats() {
		CourseRoll roll;
		Course course = new Course("CSC216", "Software Development Fundamentals Lab", "216", 1, "sheckman", 87, "W");
		
        Student student = new Student("John", "Smith", "12345", "josmith@ncsu.edu", "12345abcde");

		roll = course.getCourseRoll();
		assertEquals(87, roll.getOpenSeats());
		roll.enroll(student);
		assertEquals(86, roll.getOpenSeats());
	}
	
	@Test
	public void testEnrollmentCapacityBounds() {
	    Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
	    CourseRoll roll = new CourseRoll(c, 10);
	    assertEquals(10, roll.getEnrollmentCap());

	    // Similarly, test for CourseRoll.MAX_ENROLLMENT and MAX_ENROLLMENT - 1
	}
}
