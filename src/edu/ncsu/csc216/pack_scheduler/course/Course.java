/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;


/**
 * This Class is design to store and update primary information about an educational course, such as the
 * course name, title, section, credits, instructor, or time window
 * @author Nate Powers
 * @author John Kostic
 * @author Blake Boykin
 */
public class Course extends Activity implements Comparable<Course> {
	
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**minimum length of course name**/
	public static final int MIN_NAME_LENGTH = 5;
	/**maximum length of course name**/
	public static final int MAX_NAME_LENGTH = 8;
	/**minimum letter count in course name**/
	public static final int MIN_LETTER_COUNT = 1;
	/**maximum letter count in course name**/
	public static final int MAX_LETTER_COUNT = 4;
	/**digit count in course name**/
	public static final int DIGIT_COUNT = 3;
	/**section length**/
	public static final int SECTION_LENGTH = 3;
	/**minimum number of credits**/
	public static final int MIN_CREDITS = 1;
	/**maximum number of credits**/
	public static final int MAX_CREDITS = 5;
	/**acceptable characters for meeting days**/
	public static final char[] ACCEPTED_CHARACTERS = {'M', 'T', 'W', 'H', 'F', 'A'};
	/**checks for validity of Course names**/
	public CourseNameValidator validator;
	/** private field for course roll */
	private CourseRoll roll;
	
	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.  If the name is null, has a length less than 5 or more than 8,
	 * does not contain a space between letter characters and number characters, has less than 1
	 * or more than 4 letter characters, and not exactly three trailing digit characters, an
	 * IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name)  {
		try {
		    if (validator.isValid(name)) {
		        this.name = name;
		    }
		    else {
		    	throw new IllegalArgumentException("Invalid course name.");
		    }
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
	}
		
		
//		//Throw exception if the name is null
//	    if (Objects.isNull(name)) {
//	        throw new IllegalArgumentException("Invalid course name.");
//	    }
//
//	    //Throw exception if the name is an empty string
//	    //Throw exception if the name contains less than 5 character or greater than 8 characters
//	    if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
//	    	throw new IllegalArgumentException("Invalid course name.");
//	    }
//
//	    //Check for pattern of L[LLL] NNN
//	    int numberLetters = 0;
//	    int numberDigits = 0;
//	    boolean spaceFound = false;
//	    for (int i = 0; i < name.length(); i++) {
//	    	char character = name.charAt(i);
//	    	
//	    	if (!spaceFound) {
//	    		if (Character.isLetter(character)) {
//	    			numberLetters++;
//	    		} else if (character == ' ') {
//	    			spaceFound = true;
//	    		} else {
//	                throw new IllegalArgumentException("Invalid course name.");
//	    		}
//	    	} else {
//	    		if (Character.isDigit(character)) {
//	    			numberDigits++;
//	    		} else {
//	    			throw new IllegalArgumentException("Invalid course name.");
//	    		}
//	    	}
//	    }
//	    
//	    //Check that the number of letters is correct
//	    if (numberLetters < MIN_LETTER_COUNT || numberLetters > MAX_LETTER_COUNT) {
//	        throw new IllegalArgumentException("Invalid course name.");
//	    }
//	    //Check that the number of digits is correct
//	    if (numberDigits != DIGIT_COUNT) {
//	        throw new IllegalArgumentException("Invalid course name.");
//	    }
//	    
//	    this.name = name; 
//	}

	/**
	 * Returns the Course's section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section
	 * @param section the section to set
	 * @throws IllegalArgumentException if invalid parameter is found
	 */
	public void setSection(String section) {
		try {
			if (section.length() != SECTION_LENGTH) {
				throw new IllegalArgumentException("Invalid section.");
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Invalid section.");
		}
		for (int i = 0; i < section.length(); i++) {
			char character = section.charAt(i);
			if (!Character.isDigit(character)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
 		this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's Instructor ID
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's Instructor ID 
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if(instructorId == null) {
		    this.instructorId = instructorId;
		} else if("".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
	    this.instructorId = instructorId;		
	}
	
	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + ","
	    	+ getStartTime() + "," + getEndTime(); 
	}
	
	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, meetingDays, start  and end times for 
	 * courses that are arranged
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours of Course
	 * @param instructorId Instructor ID of Course
	 * @param enrollmentCap the cap of students that can be enrolled in the class
	 * @param meetingDays meeting days of Course as series of chars
	 * @param startTime starting time of course
	 * @param endTime ending time of course 
	 * @throws IllegalArgumentException if parameter is null
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, 
			String meetingDays, int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        this.validator = new CourseNameValidator();
        this.roll = new CourseRoll(this, enrollmentCap);
        setName(name);
        //setTitle(title);  <-- DELETE THIS, called in super
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        //setMeetingDaysAndTime(meetingDays, startTime, endTime); <-- DELETE THIS, called in super
    }
	
	/**
	 * Generates a hashCode for Course using all fields
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours of Course
	 * @param instructorId Instructor ID of Course
	 * @param enrollmentCap the cap of enrollment for the course
	 * @param meetingDays meeting days of Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}
	
	/**
	 * Creates short version array of strings for display in the GUI
	 * @return shortDisplay string array containing course name, section, title, and meeting string
	 */
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		
		shortDisplay[0] = this.getName();
		shortDisplay[1] = this.getSection();
		shortDisplay[2] = this.getTitle();
		shortDisplay[3] = this.getMeetingString();
		shortDisplay[4] = Integer.toString(roll.getOpenSeats());
		
		return shortDisplay;
	}
	
	
	/**
	 * Sets the Course's meeting days, start time, and end time
	 * @param meetingDays the meetingDays to set
	 * @param startTime the starting time of the class
	 * @param endTime the ending time of the class
	 * @throws IllegalArgumentException if parameters are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		int countM = 0;
		int countT = 0;
		int countW = 0;
		int countH = 0;
		int countF = 0;
		
		if (meetingDays.contains("A")) {
			if (startTime == 0 && endTime == 0) {
				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			} else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else {
			for (int i = 0; i < meetingDays.length(); i++) {
				char character = meetingDays.charAt(i);
				
				if (character == 'M') {
					countM++;
				} else if (character == 'T' ) {
					countT++;
				} else if (character == 'W' ) {
					countW++;
				} else if (character == 'H' ) {
					countH++;
				} else if (character == 'F' ) {
					countF++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
				
			if (countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Creates long version array of strings for display in the GUI
	 * @return LongDisplay string array containing course name, section, title, credits, instructorID, 
	 * meeting string, and blank string
	 */
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		
		longDisplay[0] = this.getName();
		longDisplay[1] = this.getSection();
		longDisplay[2] = this.getTitle();
		longDisplay[3] = Integer.toString(this.getCredits());
		longDisplay[4] = this.getInstructorId();
		longDisplay[5] = this.getMeetingString();
		longDisplay[6] = "";
		
		return longDisplay;
	}

	/**
	 * Runs the Course class program
	 * @param args no args to run
	 */
	public static void main(String[] args) {
		// Auto-generated method stub	
	}
	
	/**
	 * Checks to see if an activity is a course and if it is a duplicate
	 * @return true/false if it is a duplicate class
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course activityCourse = (Course) activity;
			if (activityCourse.getName().equals(this.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Compares two course objects to see if they are the same or different
	 */
	@Override
	public int compareTo(Course o) {
		
		int nameCompare = this.getName().compareTo(o.getName());
		int sectionCompare = this.getSection().compareTo(o.getSection());
		
		if (nameCompare == 0) {
			if (sectionCompare == 0) {
				return 0;
			} else if (sectionCompare < 0) {
				return -1;
			} else {
				return 1;
			}
		} else if (nameCompare < 0) {
			return -1;
		} else {
			return 1;
		}
	}
	
	/**
	 * Returns the course's roll
	 * @return roll the course roll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
	
	
}
