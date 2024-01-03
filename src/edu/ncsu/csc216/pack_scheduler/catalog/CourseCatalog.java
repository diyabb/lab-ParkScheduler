package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This class helps manage and maintain a course schedule given
 * a course catalog read in from a file
 * @author Nate Powers
 * @author John Kostic
 * @author Diya Bhavsar
 */
public class CourseCatalog {
	
	/** Course Catalog object */
	private SortedList<Course> catalog;
	/** Class constant for number of columns */
	public static final int NUM_COL = 3;
	/** Class constant for number of columns of full schedule */
	public static final int NUM_COL_FULL = 6;
	
	/**
	 * Constructs a CourseCatalog class for storing information about courses
	 */
	public CourseCatalog() {
        catalog = new SortedList<Course>();
    }
	
	/**
	 * Constructs an empty CourseCatalog class for storing information about courses
	 */
	public void newCourseCatalog() {
        catalog.clear();
    }
	
	/**
	 * loads in courses from a file into a course catalog
	 * @param filename the name of the file to read in
	 * @throws IllegalArgumentException if files not found
	 */
	public void loadCoursesFromFile(String filename) {
//		SortedList<Course> courses = new SortedList<Course>();
		
		try {
			catalog = CourseRecordIO.readCourseRecords(filename);
//			for (int i = 0; i < courses.size(); i++) {
//				catalog.add(courses.get(i));
//			}
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("The file was not found.");
		}
	}
	
	/**
	 * Adds a course to the catalog
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours of Course
	 * @param instructorId Instructor ID of Course
	 * @param enrollmentCap - the cap of course enrollment
	 * @param meetingDays meeting days of Course as series of chars
	 * @param startTime starting time of course
	 * @param endTime ending time of course 
	 * @throws IllegalArgumentException if any exception is found
	 * @return true or false if course was added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits,
			String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		
		//Course courseToAdd = new Course(name, title, section, credits, instructorId, meetingDays,
		//		startTime, endTime);
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return false; // Course already exists
			}
		}
		
		try {			
			Course courseToAdd = new Course(name, title, section, credits, instructorId, enrollmentCap, 
					meetingDays, startTime, endTime);
			catalog.add(courseToAdd);
			return true;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	/**
	 * Checks if a course is part of catalog and if so, removes it
	 * @param name the name of a course
	 * @param section the section of a course
	 * @return true/false if course can be removed or not
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * looks for a given course name and section in catalog and returns the course if found
	 * @param name the course name
	 * @param section the course section
	 * @return course the course object found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		return null;
	}
	
	/**
	 * returns the name, section, title, meeting information, and number of open seats for Courses 
	 * in the catalog.
	 * @return string array of names
	 */
	public String[][] getCourseCatalog() {
		int size = catalog.size();
		
		String[][] catalogString = new String[size][];
		
		for (int i = 0; i < size; i++) {
			Course course = catalog.get(i);
			String[] thisCourseShort = course.getShortDisplayArray();
			catalogString[i] = thisCourseShort;
		}
		
		return catalogString;
	}

	/**
	 * saves the catalog course records to the given file
	 * @param fileName the name of the file to read in
	 * @throws IllegalArgumentException if files not found
	 */
	public void saveCourseCatalog(String fileName) {
		
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file was not found.");
		}
	}

}
