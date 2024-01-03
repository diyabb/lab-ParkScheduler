package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;


/**
 * Creates a student record that keeps track of a student's first and last name, as well
 * as their studentID, email address, password, and maximum credits for a semester
 * @author Nate Powers
 * @author John Kostic
 */
public class Student extends User implements Comparable<Student> {
	
	/**Max credit amount */
	public static final int MAX_CREDITS = 18;
	/** Min credit amount */
	private static final int MIN_CREDIT_LIMIT = 3;
	
	/** Student's max credits */
	private int studentMaxCredits;
	
	/** Student's course schedule */
	public Schedule schedule = new Schedule();

	/**
	 * Creates a Student with first and last name, id, email, password, and max number of
	 * credits
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id student id
	 * @param email student's email address
	 * @param hashPW student's password (hashed)
	 * @param maxCredits the student's maximum number of credits
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
	}
	
	/**
	 * Creates a Student with first and last name, id, email, and a password
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id student id
	 * @param email student's email address
	 * @param hashPW student's password (hashed)
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}
	

	/**
	 * Sets the student's max credits
	 * @param maxCredits the students max credits to set
	 * @throws IllegalArgumentException if credits is less than 3 or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < MIN_CREDIT_LIMIT || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.studentMaxCredits = maxCredits;
	}
	

	/**
	 * Returns the student's max credits
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return this.studentMaxCredits;
	}
	
	
	/**
	 * Returns a comma separated value String of all Student fields.
	 * @return String representation of Student
	 */
	@Override
	public String toString() {
	    return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + studentMaxCredits;

	}
	
	/**
	 * Compares two student objects to see if they are the same or different
	 * Student object should be listed by last name, first name, student id
	 */
	@Override
	public int compareTo(Student o) {
		int firstNameCompare = this.getFirstName().compareTo(o.getFirstName());
		int lastNameCompare = this.getLastName().compareTo(o.getLastName());
		int idCompare = this.getId().compareTo(o.getId());
		
		if (lastNameCompare == 0) {
			if (firstNameCompare == 0) {
				if (idCompare == 0) {
					return 0;
				} else if (idCompare < 0) {
					return -1;
				} else {
					return 1;
				}
			} else if (firstNameCompare < 0) {
				return -1;
			} else {
				return 1;
			}
		} else if (lastNameCompare < 0) {
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * hashcode for Student
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + studentMaxCredits;
		return result;
	}
	
	/**
	 * simple equals method for Student
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Student other = (Student) obj;
//		if (studentMaxCredits != other.studentMaxCredits) {
//			return false;
//		}
		return studentMaxCredits == other.studentMaxCredits;
	}
	
	/**
	 * simple method to return the student's schedule
	 * @return schedule - the student's course schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * method to determine if course can be added to student's schedule based on credits
	 * @param course - course to check
	 * @return true - if course can be added, false if not
	 */
	public boolean canAdd(Course course) {
		if (!schedule.canAdd(course)) {
			return false;
		} else if ((schedule.getScheduleCredits() + course.getCredits()) > studentMaxCredits) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds a course to the schedule.
	 * @param course course to add
	 */
	public void addCourseToSchedule(Course course) {
	    if (canAdd(course)) {
	        schedule.addCourseToSchedule(course);
	    } else {
	        throw new IllegalArgumentException("Cannot add course to schedule");
	    }
	}
}
