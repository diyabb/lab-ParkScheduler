package edu.ncsu.csc216.pack_scheduler.users;


import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the Student object.
 * @author SarahHeckman
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}
	
	/**
	 * Tests that the equals method works for all Course fields.
	 */
	@Test
	public void testEqualsObject() {

		User c1 = new Student(firstName, lastName, id, email, hashPW);
		User c2 = new Student(firstName, lastName, id, email, hashPW);
		User c3 = new Student("Nate", "Powers", id, email, hashPW);
		User c4 = new Student("Nate", "Jello", id, email, hashPW);
		User c5 = new Student(firstName, lastName, id, email, "1234");
		User c6 = new Student(firstName, lastName, id, "npowers@gmail.com", hashPW);
		User c7 = new Student(firstName, lastName, "1234", email, hashPW);


		// Test for equality in both directions
		assertEquals(c1, c2);
		assertEquals(c2, c1);

		// Test for each of the fields
		assertNotEquals(c1, c3);
		assertNotEquals(c3, c4);
		assertNotEquals(c1, c5);
		assertNotEquals(c1, c6);
		assertNotEquals(c1, c7);
		
	}
	
	/**
	 * Tests setEmail(). This can ONLY be done through the Student constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidEmail invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"np@gmail", "nate.powers@gmailcom"})
	public void testSetEmailInvalid(String invalidEmail) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, invalidEmail, hashPW));
		assertEquals("Invalid email", e1.getMessage(), "Incorrect exception thrown with invalid email - " + invalidEmail);
	}
	
	/**
	 * Tests setPassword(). This can ONLY be done through the Student constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nullPassword invalid course name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetPasswordInvalid(String nullPassword) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, email, nullPassword));
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception thrown with invalid password - " + nullPassword);
	}
	
	/**
	 * Tests setLastName(). This can ONLY be done through the Student constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidLastName invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetLastNameInvalid(String invalidLastName) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, invalidLastName, id, email, hashPW));
		assertEquals("Invalid last name", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + invalidLastName);
	}
	
	/**
	 * Tests setFirstName(). This can ONLY be done through the Student constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidFirstName invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetFirstNameInvalid(String invalidFirstName) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(invalidFirstName, firstName, id, email, hashPW));
		assertEquals("Invalid first name", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + invalidFirstName);
	}
	
	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		User c1 = new Student(firstName, lastName, id, email, hashPW);
		User c2 = new Student(firstName, lastName, id, email, hashPW);
		User c3 = new Student("Nate", "Powers", id, email, hashPW);
		User c4 = new Student("Nate", "Jello", id, email, hashPW);
		User c5 = new Student(firstName, lastName, id, email, "1234");
		User c6 = new Student(firstName, lastName, id, "npowers@gmail.com", hashPW);
		User c7 = new Student(firstName, lastName, "1234", email, hashPW);


		// Test for the same hash code for the same values
		assertEquals(c1.hashCode(), c2.hashCode());

		// Test for each of the fields
		assertNotEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
		assertNotEquals(c1.hashCode(), c5.hashCode());
		assertNotEquals(c1.hashCode(), c6.hashCode());
		assertNotEquals(c1.hashCode(), c7.hashCode());
	}
	
	
	/**
	 * Tests that comparable is working
	 */
	@Test
	public void testComparable() {
		Student c1 = new Student("Nate", "Powers", "1234", "napower2@ncsu.edu", "1234abcd");
		Student c2 = new Student("Kate", "Powers", "1235", "kapower@ncsu.edu", "1234abcd");
		Student c3 = new Student("Pate", "Nowers", "1212", "napower2@ncsu.edu", "1234abcd");
		
		// Test that student objects are the same
		assertEquals(0, c1.compareTo(c1));
		
		// Test that student objects are not the same
		assertNotEquals(0, c1.compareTo(c2));
		assertEquals(-1, c2.compareTo(c1));
		assertEquals(-1, c3.compareTo(c1));
		
	}
	
	/**
	 * Tests the student's schedule
	 */
	@Test
	public void testSchedule() {
		Student s1 = new Student("Nate", "Powers", "1234", "napower2@ncsu.edu", "1234abcd");
		Course c1 = new Course("CSC216", "Programming", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Course c2 = new Course("CSC217", "Programming2", "002", 3, "sesmith6", 10, "F", 1445, 1600);
		s1.schedule.addCourseToSchedule(c1);
		s1.schedule.addCourseToSchedule(c2);
		
		Schedule testSchedule = s1.getSchedule();
		String[][] testScheduleCourseArray = testSchedule.getScheduledCourses();
		
		assertAll("Student Schedule", 
				() -> assertEquals(c1.getName(), testScheduleCourseArray[0][0]),
				() -> assertEquals(c1.getSection(), testScheduleCourseArray[0][1]),
				() -> assertEquals(c1.getTitle(), testScheduleCourseArray[0][2]),
				() -> assertEquals(c1.getMeetingString(), testScheduleCourseArray[0][3]));
	}
	
	/**
	 * Tests the student's schedule with adding too many credits
	 */
	@Test
	public void testScheduleTooManyCredits() {
		Student s1 = new Student("Nate", "Powers", "1234", "napower2@ncsu.edu", "1234abcd");
		Course c1 = new Course("CSC216", "Programming", "001", 5, "sesmith5", 10, "MW", 1330, 1445);
		Course c2 = new Course("CSC217", "Programming2", "002", 5, "sesmith6", 10, "F", 1445, 1600);
		Course c3 = new Course("CSC218", "Programming3", "003", 5, "sesmith7", 10, "T", 1500, 1615);
		Course c4 = new Course("CSC219", "Programming4", "004", 5, "sesmith7", 10, "T", 1000, 1115);
		s1.schedule.addCourseToSchedule(c1);
		assertTrue(s1.canAdd(c2));
		s1.schedule.addCourseToSchedule(c2);
		s1.schedule.addCourseToSchedule(c3);
		assertEquals(18, s1.getMaxCredits());
		assertEquals(15, s1.schedule.getScheduleCredits());
		assertFalse(s1.canAdd(c4));
	}
	
}
