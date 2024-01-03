package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;


/**
 * This class reads in and stores records stored in the input file, and creates a Schedule for the user.
 * @author John Kostic
 * @author Nate Powers
 *
 */
public class Schedule {
	
	/** catalog of Courses listed that can be taken 
	 * @param courseSchedule list of Courses in the Sc-hedule
	 */
	private ArrayList<Course> schedule;
	
	/** title of the schedule */	
	private String title;
	
	/**
	 * constructor for Schedule
	 */
	public Schedule() {
		this.schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 * adds a new Course to the user's Schedule
	 * @param course Course to be added
	 * @return true if newCourse is successfully added to the schedule
	 */
	public boolean addCourseToSchedule(Course course) {
	    if (course == null) {
	        throw new NullPointerException();
	    }
		
	    for (int i = 0; i < schedule.size(); i++) {
	        Course existingCourse = schedule.get(i);
	        if (existingCourse.equals(course) || existingCourse.isDuplicate(course)) {
	            throw new IllegalArgumentException("You are already enrolled in " + course.getName());
	        }
	    }
	    
	    for (int i = 0; i < schedule.size(); i++) {
	        Course existingCourse = schedule.get(i);
	        try {
	            existingCourse.checkConflict(course);
	        } 
	        catch (ConflictException e) {
	            throw new IllegalArgumentException("The course cannot be added due to a conflict.");
	        }
	    }
	    
	    schedule.add(course);
		return true;
	}
	
	/**
	 * removes a Course from the schedule
	 * @param course Course to be removed from the Schedule
	 * @return true if the Course is successfully removed; false if otherwise
	 */
	public boolean removeCourseFromSchedule(Course course) {
	    for (int i = 0; i < schedule.size(); i++) {
	        Course existingCourse = schedule.get(i);
	        if (existingCourse.equals(course)) {
	            schedule.remove(i);
	            return true;
	        }
	    }
		return false;
	}
	
	/**
	 * creates a new, empty Schedule
	 */
	public void resetSchedule() {
	    this.schedule = new ArrayList<Course>();
	    this.title = "My Schedule";
	}
	
	/**
	 * creates and returns a 2D Array
	 * @return scheduleGrid 2D array containing a Course's name, section, title, and meeting string
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleGrid = new String[schedule.size()][5];
	    for (int i = 0; i < schedule.size(); i++) {
	        Course currentCourse = schedule.get(i);
	        String[] courseDetails = currentCourse.getShortDisplayArray();
	        scheduleGrid[i][0] = courseDetails[0];
	        scheduleGrid[i][1] = courseDetails[1];
	        scheduleGrid[i][2] = courseDetails[2];
	        scheduleGrid[i][3] = courseDetails[3];
	        scheduleGrid[i][4] = courseDetails[4];
	    }

	    return scheduleGrid;
	}
	
	/**
	 * Returns the Schedule's title.
	 * @return title of the Schedule
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * setter for title
	 * @param title title of the Schedule
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * cumulative sum that returns total credits in schedule
	 * @return sumCredits - the sum total of credits
	 */
	public int getScheduleCredits() {
		int sumCredits = 0;
		
		for (int i = 0; i < schedule.size(); i++) {
	        Course currentCourse = schedule.get(i);
	        sumCredits += currentCourse.getCredits();
	    }
		
		return sumCredits;
	}
	
	/**
	 * Determines if course can be added to schedule or not
	 * @param course - the course being checked
	 * @return true - if course can be added to schedule, false if not
	 */
	public boolean canAdd(Course course) {
		if (course == null) {
	        return false;
	    }
		
	    for (int i = 0; i < schedule.size(); i++) {
	        Course existingCourse = schedule.get(i);
	        if (existingCourse.equals(course) || existingCourse.isDuplicate(course)) {
	        	return false;
	        }
	    }
	    
	    for (int i = 0; i < schedule.size(); i++) {
	    	Course existingCourse = schedule.get(i);
	        try {
	            existingCourse.checkConflict(course);
	        } catch (ConflictException e) {
	            return false;
	        }
	    }
	    return true;
		
	}
}
