package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * This class keeps and maintains a record of the number of students enrolled in
 * a given NCSU class
 * 
 * @author John Kostic
 * @author Diya Bhavsar
 * @author Blake Boykin
 *
 */
public class CourseRoll {

	/** a list of the roll for a given NCSU class */
	private LinkedAbstractList<Student> roll;

	/** the roll's enrollment capacity */
	private int enrollmentCap;

	/** the minimum number of students that may be enrolled in a class */
	private static final int MIN_ENROLLMENT = 10;

	/** the maximum number of students that may be enrolled in a class */
	private static final int MAX_ENROLLMENT = 250;

	/** The associated course */
	private Course course;

	/** The waitlist for the course */
	private LinkedAbstractList<Student> waitlist;

	/**
	 * constructor for a new CourseRoll
	 * 
	 * @param course        the associated course
	 * @param enrollmentCap the roll's enrollment capacity
	 * @throws IllegalArgumentException if enrollment cap is not w/i bounds or
	 *                                  course is null
	 */
	public CourseRoll(Course course, int enrollmentCap) {
		this.roll = new LinkedAbstractList<Student>(enrollmentCap);
		setCourse(course);
		setEnrollmentCap(enrollmentCap);
		this.waitlist = new LinkedAbstractList<Student>(10);
	}
	
	/**
	 * Sets the course. 
	 * @param course the course to set
	 * @throws IllegalArgumentException if the course is null
	 */
	private void setCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}
		this.course = course;
	}
	
	/**
	 * Returns the course.
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * getter for enrollmentCap
	 * 
	 * @return enrollmentCap the roll's enrollment capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * setter for enrollmentCap
	 * 
	 * @param enrollmentCap the roll's enrollment capacity
	 * @throws IllegalArgumentException if enrollment cap is not w/i bounds
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap > MAX_ENROLLMENT || enrollmentCap < MIN_ENROLLMENT || enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment capacity.");
		}
		this.enrollmentCap = enrollmentCap;
		roll.setCapacity(enrollmentCap);
	}

	/**
	 * This method adds a new student to the roll
	 * 
	 * @param s Student to be added to the roll
	 * @throws IllegalArgumentException if student is null, already enrolled, the
	 *                                  roll is full, or if any other error prevents
	 *                                  adding the student to the roll
	 */
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
		if (roll.size() >= enrollmentCap) {
			if (waitlist.size() >= 10) {
				throw new IllegalArgumentException("Student cannot be enrolled or added to waitlist.");
			} else {
				waitlist.add(waitlist.size(), s);
				return;
			}
		}
		if (roll.contains(s) || waitlist.contains(s)) {
			throw new IllegalArgumentException("Student is already enrolled or on the waitlist.");
		}
		try {
			roll.add(roll.size(), s);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to enroll student.");
		}
	}

	/**
	 * This method removes a student from the roll
	 * 
	 * @param s Student to be removed from the roll
	 * @throws IllegalArgumentException is student is null or otherwise cannot be
	 *                                  removed from the list
	 */
	public void drop(Student s) {
		
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
		if (roll.contains(s)) {
			roll.remove(s);
			if (!waitlist.isEmpty()  && roll.size() < enrollmentCap) {
	            Student waitlistedStudent = waitlist.remove(0);
	            roll.add(roll.size(), waitlistedStudent);
	            waitlistedStudent.getSchedule().addCourseToSchedule(course);			}
		} else if (waitlist.contains(s)) {
			waitlist.remove(s);
		} else {
			throw new IllegalArgumentException("Student is not enrolled or on the waitlist.");
		}
	}

	/**
	 * gives the number of available spots in a given NCSU class
	 * 
	 * @return openSeats the number of open seats in the class
	 */
	public int getOpenSeats() {
		int openSeats = enrollmentCap - roll.size();
		return openSeats;
	}

	/**
	 * Checks if a student may enroll in a given class
	 * 
	 * @param s student that wishes to enroll in a class
	 * @return true if a student may enroll in the class, false if otherwise
	 */
	public boolean canEnroll(Student s) {
	    if (s == null) {
	        return false;
	    }
	    
	    // Check if the student is already enrolled or on the waitlist
//	    if (roll.contains(s) || waitlist.contains(s)) {
//	        return false;
//	    }
	    
	    // Check if the student is already enrolled
	    if (roll.contains(s)) {
	        return false;
	    }

	    // Check if the student is on the waitlist
	    if (waitlist.contains(s)) {
	        return true;
	    }

	    // Check if there is room in the roll
	    if (roll.size() >= enrollmentCap) {
	        return waitlist.size() < 10;
	    }

//	    // Check if the student can be added to the waitlist
//	    return waitlist.size() < 10 && !waitlist.contains(s);
	    
	    return true;
	}

	/**
	 * Returns the number of Students on the waitlist.
	 * 
	 * @return the number of Students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}