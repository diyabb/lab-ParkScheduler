package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ncsu.csc216.pack_scheduler.user.Student;


/**
 * Tests StudentDirectory.
 * @author Sarah Heckman
 * @author Nate Powers
 * @author John Kostic
 * @author Blake Boykin
 */
public class StudentDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@BeforeEach
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
				
		//Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();
		
		//Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
		
	}
	
	/**
	 * Tests StudentDirectory.addStudent()on a duplicate student
	 */
	@Test
	public void testAddStudentDuplicate() {
		StudentDirectory sd = new StudentDirectory();
		
		//Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		// Testing for duplicate student
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
		assertEquals("Student already in system", e1.getMessage(), "Student already in system");
	}
	
	/**
	 * Tests addStudent(). This can ONLY be done through the Student Directory add function.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nullPassword null password to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddStudentNullPassword(String nullPassword) {
		StudentDirectory sd = new StudentDirectory();
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, nullPassword, MAX_CREDITS));
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + nullPassword);
	}
	
	/**
	 * Tests addStudent(). This can ONLY be done through the Student Directory add function.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nonMatchingPassword non matching password to test
	 */
	@ParameterizedTest
	@ValueSource(strings = {"1234"})
	public void testSetFirstNameInvalid(String nonMatchingPassword) {
		StudentDirectory sd = new StudentDirectory();
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, nonMatchingPassword, MAX_CREDITS));
		assertEquals("Passwords do not match", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + nonMatchingPassword);
	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 * @throws Exception if io exception
	 */
	@Test
	public void testRemoveStudent() throws Exception {
		StudentDirectory sd = new StudentDirectory();
		//Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		
		//Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Tests getStudentById()
	 */
	@Test
	public void testGetStudentById() {		
	    StudentDirectory directory = new StudentDirectory();


	    directory.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

	    Student s = directory.getStudentById(ID);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		//assertEquals(MAX_CREDITS, s.getMaxCredits());
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
    /**
     * Test saving students to an invalid file.
     */
    @Test
    public void testSaveStudentsToInvalidFile() {
        StudentDirectory sd = new StudentDirectory();

        // Add a student to the directory
        sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            sd.saveStudentDirectory("invalid/directory/file.txt");
        });

        assertEquals("Unable to write to file invalid/directory/file.txt", e.getMessage());
    }
    
    /**
     * Test loading students from a nonexistent file.
     */
    @Test
    public void testLoadStudentsFromNonexistentFile() {
        StudentDirectory sd = new StudentDirectory();

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            sd.loadStudentsFromFile("nonexistentFile.txt");
        });

        assertEquals("Unable to read file nonexistentFile.txt", e.getMessage());
        assertEquals(0, sd.getStudentDirectory().length);
    }

}
