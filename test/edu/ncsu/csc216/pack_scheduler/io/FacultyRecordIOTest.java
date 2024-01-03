/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * tests Faculty Record IO program
 * @author Nate Powers
 * @author John Kostic
 */
public class FacultyRecordIOTest {
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	
	//Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,2
	//Fiona,Meadows,fmeadow,pharetra.sed@et.org,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,3
	//Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,1
	//Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,3
	//Kevyn,Patel,kpatel,risus@pellentesque.ca,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,1
	//Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,3
	//Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,1
	//Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,0ÉRú±"ÃùuŸ¦Ù\7X²F´þâ9•{-OîFâapÄ,2

	/** Expected results for valid faculty in actual_faculty_records.txt - line 1 */
	private final String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,2";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 2 */
	private final String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,3";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 3 */
	private final String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,1";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 4 */
	private final String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,3";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 5 */
	private final String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,1";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 6 */
	private final String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,3";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 7 */
	private final String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,1";
	/** Expected results for valid faculty in actual_faculty_records.txt - line 8 */
	private final String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,0ÉRú±\"ÃùuŸ¦Ù\\7X²F´þâ9•{-OîFâapÄ,2";
	
	/** Array to hold expected results */
	private final String [] validFacultys = {validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7, validFaculty8};
	
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
	        
	        for (int i = 0; i < validFacultys.length; i++) {
	            validFacultys[i] = validFacultys[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	/**
	 * Resets faculty_records.txt for use in other tests.
	 * @throws Exception if unable to reset files
	 */
	@BeforeEach
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}	
	
	/**
	 * Resets actual_faculty_nate_powers.txt for use in writing tests.
	 * @throws Exception if unable to reset files
	 */
	@BeforeEach
	public void setUp2() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "blank_starter_file.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "actual_faculty_nate_powers.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	
	/**
	 * Tests readValidFacultyRecords().
	 * @throws Exception if file can't be set up
	 */
	@Test
	public void testReadValidFacultyRecords() throws Exception {
		try {
			setUp();
		} catch (Exception e) {
			System.out.println("Failed to set up file");
		}
		try {
			LinkedList<Faculty> facultys = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, facultys.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests readInvalidFacultyRecords().
	 */
	@Test
	public void testReadInvalidFacultyRecords() {
		LinkedList<Faculty> facultys;
		try {
			facultys = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, facultys.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}
	
	/**
	 * Tests readInvalidFacultyRecords().
	 */
	@Test
    public void testFileNotFoundException() {
        //Provide a non-existent file name
        String nonExistentFileName = "nonexistentfile.txt";

        //Use assertThrows to check if the method throws FileNotFoundException
        assertThrows(IllegalArgumentException.class, () -> {
        	FacultyRecordIO.readFacultyRecords(nonExistentFileName);
        });
    }
	
	/**
	 * Tests writeFacultyRecords()
	 */
	@Test
	public void testWriteFacultyRecords() {
		
		LinkedList<Faculty> facultys = new LinkedList<Faculty>();
		
		facultys.add(new Faculty("Nate", "Powers", "napower2", "napower2@gmail.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3));
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_nate_powers.txt", facultys);
		} catch (IOException e) {
			fail("Cannot write to faculty records file");
		}
		
		checkFiles("test-files/expected_faculty_nate_powers.txt", "test-files/actual_faculty_nate_powers.txt");
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
	
	/**
	 * Test for reading an empty file.
	 */
    @Test
    public void testReadEmptyFacultyRecords() throws Exception {
        try {
            setUpEmptyFile();
        } catch (Exception e) {
            System.out.println("Failed to set up empty file");
        }
        assertThrows(IllegalArgumentException.class, () -> FacultyRecordIO.readFacultyRecords("test-files/empty_faculty_records.txt"));
//            assertEquals(0, facultys.size());
    }


    /**
     * Helper method to set up an empty file for testing
     */
    private void setUpEmptyFile() throws Exception {
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "empty_faculty_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
        Files.deleteIfExists(destinationPath);
        Files.copy(sourcePath, destinationPath);
    }
}
