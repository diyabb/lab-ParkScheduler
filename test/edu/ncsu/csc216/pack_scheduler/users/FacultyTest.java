package edu.ncsu.csc216.pack_scheduler.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * tests the Faculty class
 * @author Nate Powers
 * @author Diya Bhavsar
 */
public class FacultyTest {
	/** Test Faculty's first name. */
	private String firstName = "first";
	/** Test Faculty's last name */
	private String lastName = "last";
	/** Test Faculty's id */
	private String id = "flast";
	/** Test Faculty's email */
	private String email = "first_last@ncsu.edu";
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the FacultyTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the FacultyTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Test for setId() method.
	 */
	@Test
	public void testSetId() {
        Faculty faculty = new Faculty("this", "is", "tis", "tis@ncsu.edu", "pw", 2);

        //valid
        faculty.setId("thisis");
        assertEquals("thisis", faculty.getId());
        
        //invalid
        assertThrows(IllegalArgumentException.class, () -> faculty.setId(null));
        assertThrows(IllegalArgumentException.class, () -> faculty.setId(""));


	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty(firstName, lastName, id, email, "pw", 2);
		assertEquals("first,last,flast,first_last@ncsu.edu,pw,2", s1.toString());		
	}
	
	/**
	 * Tests that the equals method works for all Course fields.
	 */
	@Test
	public void testEqualsObject() {

		Faculty c1 = new Faculty(firstName, lastName, id, email, "pw", 2);
		Faculty c2 = new Faculty(firstName, lastName, id, email, "pw", 2);
		Faculty c3 = new Faculty("Nate", "Powers", id, email, "pw", 2);
		Faculty c4 = new Faculty("Nate", "Jello", id, email, "pw", 2);
		Faculty c5 = new Faculty(firstName, lastName, id, email, "1234", 2);
		Faculty c6 = new Faculty(firstName, lastName, id, "npowers@gmail.com", "pw", 2);
		Faculty c7 = new Faculty(firstName, lastName, "1234", email, "pw", 2);


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
	 * Test getEmail() method.
	 */
	@Test
	public void testSetEmail() {
		Faculty s1 = new Faculty(firstName, lastName, id, email, "pw", 2);
		assertEquals(email, s1.getEmail());
	}
	
	/**
	 * Tests setEmail(). This can ONLY be done through the Faculty constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidEmail invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"np@gmail", "nate.powers@gmailcom"})
	public void testSetEmailInvalid(String invalidEmail) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, lastName, id, invalidEmail, "pw", 2));
		assertEquals("Invalid email", e1.getMessage(), "Incorrect exception thrown with invalid email - " + invalidEmail);
	}
	
	/**
	 * Tests setPassword(). This can ONLY be done through the Faculty constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nullPassword invalid course name
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetPasswordInvalid(String nullPassword) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, lastName, id, email, null, 2));
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception thrown with invalid password - " + nullPassword);
	}
	
	/**
	 * Tests setLastName(). This can ONLY be done through the Faculty constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidLastName invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetLastNameInvalid(String invalidLastName) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(firstName, invalidLastName, id, email, "pw", 2));
		assertEquals("Invalid last name", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + invalidLastName);
	}
	
	/**
	 * Tests setFirstName(). This can ONLY be done through the Faculty constructor.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param invalidFirstName invalid course name to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetFirstNameInvalid(String invalidFirstName) {
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(invalidFirstName, firstName, id, email, "pw", 2));
		assertEquals("Invalid first name", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + invalidFirstName);
	}
	
	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		User c1 = new Faculty(firstName, lastName, id, email, "pw", 2);
		User c2 = new Faculty(firstName, lastName, id, email,  "pw", 2);
		User c3 = new Faculty("Nate", lastName, id, email,  "pw", 2);
		User c4 = new Faculty(firstName, "Jello", id, email,  "pw", 2);
		User c5 = new Faculty(firstName, lastName, "rat", email,  "pw", 2);
		User c6 = new Faculty(firstName, lastName, id, "npowers@gmail.com",  "pw", 2);
		User c7 = new Faculty(firstName, lastName, id, email,  "pwpw", 2);


		// Test for the same hash code for the same values
		assertEquals(c1.hashCode(), c2.hashCode());

		// Test for each of the fields
		assertNotEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
		assertNotEquals(c1.hashCode(), c5.hashCode());
		assertNotEquals(c1.hashCode(), c6.hashCode());
		assertNotEquals(c1.hashCode(), c7.hashCode());
		
		
	}
	
	
//	/**
//	 * Tests that comparable is working
//	 */
//	@Test
//	public void testComparable() {
//		Faculty c1 = new Faculty("Nate", "Powers", "1234", "napower2@ncsu.edu", "1234abcd");
//		Faculty c2 = new Faculty("Kate", "Powers", "1235", "kapower@ncsu.edu", "1234abcd");
//		Faculty c3 = new Faculty("Pate", "Nowers", "1212", "napower2@ncsu.edu", "1234abcd");
//		
//		// Test that faculty objects are the same
//		assertEquals(0, c1.compareTo(c1));
//		
//		// Test that faculty objects are not the same
//		assertNotEquals(0, c1.compareTo(c2));
//		assertEquals(-1, c2.compareTo(c1));
//		assertEquals(-1, c3.compareTo(c1));
//		
//	}
}
