package edu.ncsu.csc216.pack_scheduler.io;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

/**
 * Tests StudentRecordIO.
 * @author Nate Powers
 */
public class StudentRecordIOTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	
	/** Expected results for valid student in actual_student_records.txt - line 1 */
	private final String validStudent1 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,15";
	/** Expected results for valid student in actual_student_records.txt - line 2 */
	private final String validStudent2 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,4";
	/** Expected results for valid student in actual_student_records.txt - line 3 */
	private final String validStudent3 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14";
	/** Expected results for valid student in actual_student_records.txt - line 4 */
	private final String validStudent4 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,18";
	/** Expected results for valid student in actual_student_records.txt - line 5 */
	private final String validStudent5 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,12";
	/** Expected results for valid student in actual_student_records.txt - line 6 */
	private final String validStudent6 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
	/** Expected results for valid student in actual_student_records.txt - line 7 */
	private final String validStudent7 = "Lane,Berg,lberg,sociis@non.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,14";
	/** Expected results for valid student in actual_student_records.txt - line 8 */
	private final String validStudent8 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,17";
	/** Expected results for valid student in actual_student_records.txt - line 9 */
	private final String validStudent9 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,11";
	/** Expected results for valid student in actual_student_records.txt - line 10 */
	private final String validStudent10 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,5";
	
	/** Array to hold expected results */
	private final String [] validStudents = {validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9, validStudent10};
	
	/** the hashed password */
	private String hashPW;
	/** the hash algorithm being used */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Sets up the hashed PW so that it can be verified/checked
	 */
	@BeforeEach
	public void setUpPW() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	/**
	 * Resets actual_student_records.txt for use in other tests.
	 * @throws Exception if unable to reset files
	 */
	@BeforeEach
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
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
	 * Tests readValidStudentRecords().
	 * @throws Exception if file can't be set up
	 */
	@Test
	public void testReadValidStudentRecords() throws Exception {
		try {
			setUp();
		} catch (Exception e) {
			System.out.println("Failed to set up file");
		}
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests readInvalidStudentRecords().
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}
	
	/**
	 * Tests readInvalidStudentRecords().
	 */
	@Test
    public void testFileNotFoundException() {
        //Provide a non-existent file name
        String nonExistentFileName = "nonexistentfile.txt";

        //Use assertThrows to check if the method throws FileNotFoundException
        assertThrows(FileNotFoundException.class, () -> {
        	StudentRecordIO.readStudentRecords(nonExistentFileName);
        });
    }
	
	/**
	 * Tests writeStudentRecords()
	 */
	@Test
	public void testWriteStudentRecords() {
		
		SortedList<Student> students = new SortedList<Student>();
		
		students.add(new Student("Nate", "Powers", "napower2", "napower2@gmail.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 8));
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_nate_powers.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}
		
		checkFiles("test-files/expected_nate_powers.txt", "test-files/actual_nate_powers.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}