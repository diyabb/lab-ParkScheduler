package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * A class used for storing information about a faculty user
 * @author Nate Powers
 * @author John Kostic
 * @author Diya Bhavsar
 */
public class Faculty extends User {
	/**Maximum credit amount */
	public static final int MAX_COURSE_LIMIT = 3;
	/** Minimum credit amount */
	public static final int MIN_COURSE_LIMIT = 0;	
	
	/** Faculty's first name. */
	private String facultyFirstName;
	/** Faculty's last name. */
	private String facultyLastName;
	/** Faculty's ID */
	private String facultyID;
	/** Faculty's email */
	private String facultyEmail;
	/** Faculty's password */
	private String facultyPassword;
	/** Faculty's max credits */
	private int facultyMaxCourses;
	/** Faculty's schedule */
	private FacultySchedule facultySchedule;

	/**
	 * Creates a Faculty with first and last name, id, email, password, and max number of
	 * credits
	 * @param firstName first name of Faculty
	 * @param lastName last name of Faculty
	 * @param id Faculty id
	 * @param email Faculty's email address
	 * @param hashPW Faculty's password (hashed)
	 * @param maxCourses the Faculty's maximum number of credits
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
//		setFirstName(firstName);
//		setLastName(lastName);
//		setId(id);
//		setEmail(email);
//		setPassword(hashPW);
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		this.facultySchedule = new FacultySchedule(id);
	}
	
//	/**
//	 * Creates a Faculty with first and last name, id, email, and a password
//	 * @param firstName first name of Faculty
//	 * @param lastName last name of Faculty
//	 * @param id Faculty id
//	 * @param email Faculty's email address
//	 * @param hashPW Faculty's password (hashed)
//	 */
//	public Faculty(String firstName, String lastName, String id, String email, String hashPW) {
//		this(firstName, lastName, id, email, hashPW, MAX_COURSE_LIMIT);
//		this.facultySchedule = new FacultySchedule(id);
//	}
//	

	/**
	 * Sets the Faculty's first name
	 * @param firstName the first name to set
	 * @throws IllegalArgumentException if string is null or blank
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.facultyFirstName = firstName;
	}
	
	/**
	 * Returns the Faculty's first name
	 * @return facultyFirstName the faculty's first name
	 */
	public String getFirstName() {
		return this.facultyFirstName;
	}

	/**
	 * Sets the Faculty's last name
	 * @param lastName the last name to set
	 * @throws IllegalArgumentException if string is null or blank
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.facultyLastName = lastName;
	}
	
	/**
	 * Returns the student's last name
	 * @return studentLastName the Student's last name
	 */
	public String getLastName() {
		return this.facultyLastName;
	}

	/**
	 * Sets the faculty's ID
	 * @param facultyID the ID to set
	 * @throws IllegalArgumentException if faculty id is blank or null
	 */
	public void setId(String facultyID) {
		if (facultyID == null || "".equals(facultyID)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.facultyID = facultyID;
	}
	
	/**
	 * Returns the faculty's ID
	 * @return facultyID the faculty's ID
	 */
	public String getId() {
		return this.facultyID;
	}

	/**
	 * Sets the faculty's email
	 * @param email the email to set
	 * @throws IllegalArgumentException if email is blank, null, doesn't contain @ symbol,
	 * doesn't contain ".", or index of last "." is before index of first "@" character
	 */
	public void setEmail(String email) {
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
		this.facultyEmail = email;
	}
	
	/**
	 * Returns the faculty's email
	 * @return facultyEmail the faculty's email
	 */
	public String getEmail() {
		return this.facultyEmail;
	}

	/**
	 * Sets the faculty's password
	 * @param facultyPassword the facultyPassword to set'
	 * @throws IllegalArgumentException if password is null or blank
	 */
	public void setPassword(String facultyPassword) {
		if (facultyPassword == null || "".equals(facultyPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.facultyPassword = facultyPassword;
	}
	
	/**
	 * Returns the faculty's password
	 * @return the facultyPassword
	 */
	public String getPassword() {
		return facultyPassword;
	}
	
	/**
	 * Sets the faculty's max credits
	 * @param maxCourses the faculty's max courses to set
	 * @throws IllegalArgumentException if courses is less than 0 or greater than 3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSE_LIMIT || maxCourses > MAX_COURSE_LIMIT || maxCourses == 0) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.facultyMaxCourses = maxCourses;
	}
	

	/**
	 * Returns the faculty member's max credits
	 * @return the maxCredits
	 */
	public int getMaxCourses() {
		return facultyMaxCourses;
	}
	
	/**
	 * Returns the faculty member's schedule
	 * @return FacultySchedule
	 */
	public FacultySchedule getSchedule() {
		return this.facultySchedule;
	}
	
	/**
	 * Checks if the schedule is overbooked
	 * @return true if scheduled courses is greater than maxCourses, false if otherwise
	 */
	public boolean isOverloaded() {
//		if (facultySchedule.getScheduledCourses().length > facultyMaxCourses) {
//			return true;
//		}
//		return false;
		return facultySchedule.getNumScheduledCourses() > facultyMaxCourses;
	}
	
	/**
	 * Generates a hashCode for faculty using all fields
	 * @return hashCode for faculty
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facultyEmail == null) ? 0 : facultyEmail.hashCode());
		result = prime * result + ((facultyFirstName == null) ? 0 : facultyFirstName.hashCode());
		result = prime * result + ((facultyID == null) ? 0 : facultyID.hashCode());
		result = prime * result + ((facultyLastName == null) ? 0 : facultyLastName.hashCode());
		result = prime * result + facultyMaxCourses;
		result = prime * result + ((facultyPassword == null) ? 0 : facultyPassword.hashCode());
		return result;
	}
	
	
	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (facultyEmail == null) {
			if (other.facultyEmail != null)
				return false;
		} else if (!facultyEmail.equals(other.facultyEmail))
			return false;
		if (facultyFirstName == null) {
			if (other.facultyFirstName != null)
				return false;
		} else if (!facultyFirstName.equals(other.facultyFirstName))
			return false;
		if (facultyID == null) {
			if (other.facultyID != null)
				return false;
		} else if (!facultyID.equals(other.facultyID))
			return false;
		if (facultyLastName == null) {
			if (other.facultyLastName != null)
				return false;
		} else if (!facultyLastName.equals(other.facultyLastName))
			return false;
		if (facultyMaxCourses != other.facultyMaxCourses)
			return false;
		if (facultyPassword == null) {
			if (other.facultyPassword != null)
				return false;
		} else if (!facultyPassword.equals(other.facultyPassword))
			return false;
		return true;
	}
	
	/**
	 * Returns a comma separated value String of all faculty fields.
	 * @return String representation of faculty
	 */
	@Override
	public String toString() {
	    return facultyFirstName + "," + facultyLastName + "," + facultyID + "," + facultyEmail + "," + 
	    		facultyPassword + "," + facultyMaxCourses;

	}
	
//	/**
//	 * Compares two faculty objects to see if they are the same or different
//	 * faculty object should be listed by last name, first name, faculty id
//	 */
//	@Override
//	public int compareTo(Faculty o) {
//		int firstNameCompare = this.getFirstName().compareTo(o.getFirstName());
//		int lastNameCompare = this.getLastName().compareTo(o.getLastName());
//		int idCompare = this.getId().compareTo(o.getId());
//		
//		if (lastNameCompare == 0) {
//			if (firstNameCompare == 0) {
//				if (idCompare == 0) {
//					return 0;
//				} else if (idCompare < 0) {
//					return -1;
//				} else {
//					return 1;
//				}
//			} else if (firstNameCompare < 0) {
//				return -1;
//			} else {
//				return 1;
//			}
//		} else if (lastNameCompare < 0) {
//			return -1;
//		} else {
//			return 1;
//		}
//	}
	
	
}