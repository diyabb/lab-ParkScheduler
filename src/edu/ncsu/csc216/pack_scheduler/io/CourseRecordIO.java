package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * @author Marylyn Ngo
 * @author Linda Li
 * @author Sarah Heckman
 * @author Blake Boykin
 */
public class CourseRecordIO {
	
    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the list!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the list with all the courses we read!
	    return courses;
	}
    
    /**
     * Helper method to process each course
     * @param line the next line in the text file to be processed 
     * @return the course object if all the inputs are valid
     * @throws IllegalArgumentException if the file has invalid or out of bounds tokens 
     */
	private static Course readCourse(String line) {
	    // Creates a scanner and sets delimiter to ',' to read through the list
	    Scanner lineReader = new Scanner(line);
	    lineReader.useDelimiter(",");

	    try {
	        // Read in tokens for name, title, section, credits, instructorId,
	        // enrollmentCap, meetingDays and stores them in local variables
	        String name = lineReader.next();
	        String title = lineReader.next();
	        String section = lineReader.next();
	        int credits = lineReader.nextInt();
	        String instructorId = lineReader.next();
	        // Initialize instructorId to null
	        if ("null".equalsIgnoreCase(instructorId)) {
	            instructorId = null;
	        }
	        int enrollmentCap = lineReader.nextInt();
	        String meetingDays = lineReader.next();

	        Course course;
	        
	        // If the meeting days are arranged,
	        if ("A".equals(meetingDays)) {
	            // Throws exception if the "A" courses have any more tokens after meetingDays
	            if (lineReader.hasNext()) {
	                // Avoids resource leak
	                lineReader.close();
	                throw new IllegalArgumentException();
	            } else {
	                // Avoids resource leak
	                lineReader.close();
	                // Returns a newly constructed Course object
	                course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
	                // Add the course to the faculty's schedule if the instructorId is not null
//	                    Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
//	                    if (faculty != null) {
//	                        faculty.getSchedule().addCourseToSchedule(course);
//	                }
//	                return course;
	            }
	        } else {
	            // Reads in tokens for startTime and endTime and stores them in local variables
	            int startTime = lineReader.nextInt();
	            int endTime = lineReader.nextInt();
	            // Throws exception if there are any additional unwanted tokens
	            if (lineReader.hasNext()) {
	                // Avoids resource leak
	                lineReader.close();
	                throw new IllegalArgumentException();
	            } else {

	                // Returns a newly constructed Course object
	                course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
	                // Avoids resource leak
	                lineReader.close();
	            }
	        }
	                // Add the course to the faculty's schedule if the instructorId is not null
	                    Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
	                    if (faculty != null) {
	                        faculty.getSchedule().addCourseToSchedule(course);
	                }
	                return course;
	        // Catches any exceptions and throws a new IAE
	    } catch (Exception e) {
	        throw new IllegalArgumentException();
	    }
	}

	/**
     * Writes the given list of Courses to a file
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
    public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
    	PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < courses.size(); i++) {
    	    fileWriter.println(courses.get(i).toString());
    	}

    	fileWriter.close();
    }

}