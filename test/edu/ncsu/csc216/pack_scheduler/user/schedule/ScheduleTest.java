package edu.ncsu.csc216.pack_scheduler.user.schedule;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * test class for Schedule
 * @author John Kostic
 *
 */
public class ScheduleTest {
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 12;
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	
	/**
	 * test for Schedule()
	 */
	@Test
	public void testSchedule() {
		Schedule testSchedule = new Schedule();
		assertEquals(0, testSchedule.getScheduledCourses().length);
	}
	
	/**
	 * test for addCourseToSchedule()
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule testSchedule = new Schedule();
		assertEquals(0, testSchedule.getScheduledCourses().length);
		Course newCourse = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertTrue(testSchedule.canAdd(newCourse));
		testSchedule.addCourseToSchedule(newCourse);
		//Course with conflict
		Course newCourse2 = new Course("CSC217", "Software Development Fundamentals Lab", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertFalse(testSchedule.canAdd(newCourse2));
		//null course
		Course nullCourse = null;
		assertFalse(testSchedule.canAdd(nullCourse));
		
		//testSchedule.addCourseToSchedule(newCourse);
		assertEquals(1, testSchedule.getScheduledCourses().length);
		Course secondCourse = new Course("CSC207", "Test2", "009", 4, "npower", 10, "T", 1200, 1300);
		testSchedule.addCourseToSchedule(secondCourse);
		//Course's name, section, title, and meeting string
		String[][] testScheduleCourseArray = testSchedule.getScheduledCourses();
		assertAll("ScheduleArray", 
			() -> assertEquals(NAME, testScheduleCourseArray[0][0]),
			() -> assertEquals(SECTION, testScheduleCourseArray[0][1]),
			() -> assertEquals(TITLE, testScheduleCourseArray[0][2]),
			() -> assertEquals(newCourse.getMeetingString(), testScheduleCourseArray[0][3]),
			() -> assertEquals(7, testSchedule.getScheduleCredits()));
		
		//check duplicate exception
		Assertions.assertThrows(IllegalArgumentException.class, () -> testSchedule.addCourseToSchedule(newCourse)
				);
		//Check conflict exception
		Assertions.assertThrows(IllegalArgumentException.class, () -> testSchedule.addCourseToSchedule(newCourse2)
				);
		//check null exception
		Assertions.assertThrows(NullPointerException.class, () -> testSchedule.addCourseToSchedule(nullCourse)
				);
		
		
		
		testSchedule.removeCourseFromSchedule(newCourse);
		
		assertEquals(1, testSchedule.getScheduledCourses().length);
	}
}
