package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests the Faculty Directory class
 * @author Nate Powers
 * @author John Kostic
 * @author Diya Bhavsar
 */
public class FacultyDirectoryTest {
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "fdent";
	/** Test email */
	private static final String EMAIL = "fdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max courses */
	private static final int MAX_COURSES = 3;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@BeforeEach
	public void setUp() throws Exception {		
		//Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (FileNotFoundException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		//Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are facultys in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultysFromFile().
	 */
	@Test
	public void testLoadFacultysFromFile() {
		FacultyDirectory fd = new FacultyDirectory();
				
		//Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Test valid Faculty
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
		
		//invalid
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(null, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, PASSWORD, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(null, null, null, null, null, null, MAX_COURSES));
		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty("", "", "", "", "", "", MAX_COURSES));

	}
	
	/**
	 * Tests FacultyDirectory.addFaculty()on a duplicate faculty
	 */
	@Test
	public void testAddFacultyDuplicate() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Test valid Faculty
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		// Testing for duplicate faculty
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertEquals("Faculty already in system", e1.getMessage(), "Faculty already in system");
	}
	
	/**
	 * Tests addFaculty(). This can ONLY be done through the Faculty Directory add function.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nullPassword null password to test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testAddFacultyNullPassword(String nullPassword) {
		FacultyDirectory fd = new FacultyDirectory();
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, nullPassword, MAX_COURSES));		
		assertEquals("Invalid password", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + nullPassword);
		
//		assertThrows(IllegalArgumentException.class, () -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 4));
	}
	
	/**
	 * Tests addFaculty(). This can ONLY be done through the Faculty Directory add function.
	 * The test only considers invalid values, which should throw IllegalArgumentExceptions.
	 * @param nonMatchingPassword non matching password to test
	 */
	@ParameterizedTest
	@ValueSource(strings = {"1234"})
	public void testSetFirstNameInvalid(String nonMatchingPassword) {
		FacultyDirectory fd = new FacultyDirectory();
		// Testing for null name - IAE should be thrown
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, nonMatchingPassword, MAX_COURSES));
		assertEquals("Passwords do not match", e1.getMessage(), "Incorrect exception thrown with invalid credits - " + nonMatchingPassword);
	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 * @throws Exception if io exception
	 */
	@Test
	public void testRemoveFaculty() throws Exception {
		FacultyDirectory fd = new FacultyDirectory();
		//Add facultys and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("fmeadow"));
		String [][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Elton", facultyDirectory[4][0]);
		assertEquals("Briggs", facultyDirectory[4][1]);
		assertEquals("ebriggs", facultyDirectory[4][2]);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		
		//Add a faculty
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);

		assertEquals(3, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
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
	 * Test for getFacultyById() method.
	 */
	@Test 
	public void testGetFacultyById() {
        FacultyDirectory facultyDirectory = new FacultyDirectory();
        facultyDirectory.addFaculty("Don", "Ron", "dron", "dron@ncsu.edu", "password", "password", 3);
        
        
        Faculty don = facultyDirectory.getFacultyById("dron");
        assertEquals("Don", don.getFirstName());
        assertEquals("Ron", don.getLastName());
        assertEquals("dron@ncsu.edu", don.getEmail());
	}
}