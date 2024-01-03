package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * File IO for reading/writing a student's record from/to a file]
 * @author Nate Powers
 */
public class StudentRecordIO {
	
	/**Max credit amount */
	public static final int MAX_CREDITS = 18;
	/** Min credit amount */
	private static final int MIN_CREDIT_LIMIT = 3;

	/**
	 * reads in a Student's record
	 * @param fileName the file name to read in from
	 * @return a read in file
	 * @throws FileNotFoundException if there is an error finding the file
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
			
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
		    
		    SortedList<Student> students = new SortedList<Student>(); //Create an empty array of student objects
		
		    while (fileReader.hasNextLine()) { //While we have more lines in the file
		        try { //Attempt to do the following
		            Student student = readStudent(fileReader.nextLine()); 
		            
		            students.add(student); //Add to the list
		        } catch (IllegalArgumentException e) {
		            //The line is invalid b/c we couldn't create a student, skip it!
		        }
		    }
		    //Close the Scanner b/c we're responsible with our file handles
		    fileReader.close();
		    //Return the SortedList with all the student record
		    return students;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}
	
	/**
    //This method reads in the line and parses out into a formatted course record
    * @param nextLine the next line read in
    * @throws IllegalArgumentException if parameters are invalid
    * @return new course object that is the course read in from file
    *
    */
   private static Student readStudent(String nextLine) {
	   
		Scanner lineScanner = new Scanner(nextLine);
		
		lineScanner.useDelimiter(",");
		
		int tokenNum = 0;
		String firstName = "";
		String lastName = "";
		String id = "";
		String email = "";
		String hashedPW = "";
		int maxCredits = 0;
		
		try {
			while (lineScanner.hasNext()) {
				
				switch (tokenNum) {
					case 0: //Student First Name
						firstName = lineScanner.next();
						if ("".equals(firstName)) {
							throw new IllegalArgumentException("Invalid first name");
						}
						break;
					case 1: //Student Last Name Title
						lastName = lineScanner.next();
						if ("".equals(lastName)) {
							throw new IllegalArgumentException("Invalid last name");
						}
						break;
					case 2: //Student ID
						id = lineScanner.next();
						if ("".equals(id)) {
							throw new IllegalArgumentException("Invalid id");
						}
						break;
					case 3: //Student email
						email = lineScanner.next();
						if (email == null || "".equals(email)) {
							throw new IllegalArgumentException("Invalid email");
						}
						boolean atSign = false;
						boolean period = false;
						int lastPeriod = email.lastIndexOf('.');
						int firstAtSign = email.indexOf('@');
						
						for (int i = 0; i < email.length(); i++) {
							if (email.charAt(i) == '@') {
								atSign = true;
							} else if (email.charAt(i) == '.') {
								period = true;
							}
						}
						
						if (!atSign || !period || lastPeriod < firstAtSign) {
							throw new IllegalArgumentException("Invalid email");
						}
						break;
					case 4: //Instructor ID
						hashedPW = lineScanner.next();
						if ("".equals(hashedPW)) {
							throw new IllegalArgumentException("Invalid password");
						}
						break;
					case 5: //Meeting Days
						maxCredits = lineScanner.nextInt();
						if (maxCredits < MIN_CREDIT_LIMIT || maxCredits > MAX_CREDITS) {
							throw new IllegalArgumentException("Invalid max credits");
						}
						break;
					default:
						lineScanner.close();
						throw new IllegalArgumentException("Nothing found");
				}
				++tokenNum;
			}
			
		}  catch (InputMismatchException e) {
			lineScanner.close();
			throw new IllegalArgumentException("InputMismatchException");
		} catch (NoSuchElementException e1) {
			lineScanner.close();
			throw new IllegalArgumentException("No such element");
		}
		
		lineScanner.close();
		return new Student(firstName, lastName, id, email, hashedPW, maxCredits);
	}
	
	/**
	 * writes a Student's record
	 * @param fileName the file name to read in from
	 * @param studentDirectory the student directory
	 * @throws IOException if there is an error finding the file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < studentDirectory.size(); i++) {
    	    fileWriter.println(studentDirectory.get(i).toString());
    	}

    	fileWriter.close();
		
	}

}