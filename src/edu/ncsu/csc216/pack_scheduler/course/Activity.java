package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A superclass that can be used to track information about a course or event
 * @author Nate Powers
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/**maximum hour for time**/
	public static final int UPPER_HOUR = 23;
	/**maximum minutes for time**/
	public static final int UPPER_MINUTE = 59;
	
	/**
	 * Constructs an Activity class for storing information about courses/events
	 * @param title the title of course/event
	 * @param meetingDays the meeting days or course/event
	 * @param startTime the start time of course/event
	 * @param endTime the end time of course/event
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
	
	/**
	 * Generates a hashCode for Activity using all fields
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Returns the Course's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title
	 * @param title the title to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	public void setTitle(String title) {
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}
	
	/**
	 * Creates short version array of strings for display in the GUI
	 * @return string array containing course name, section, title and meeting string
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Creates long version array of strings for display in the GUI
	 * @return string array containing course name, section, title, credits, instructorID, meeting string,
	 * and an empty string
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Checks to see if activity is a duplicate
	 * @param activity - activity to check for duplicate
	 * @return boolean yes/no if duplicate or not
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Sets the Activity meeting days, start time, and end time
	 * @param meetingDays the meetingDays to set
	 * @param startTime the starting time of the class
	 * @param endTime the ending time of the class
	 * @throws IllegalArgumentException if parameters are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		int startHour = startTime / 100;
		int startMinute = startTime % 100;
		int endHour = endTime / 100;
		int endMinute = endTime % 100;
		
		if (startHour < 0 || startHour > UPPER_HOUR || startMinute < 0 || startMinute > UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endHour < 0 || endHour > UPPER_HOUR || endMinute < 0 || endMinute > UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		} else if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns the courses meeting time as a string in standard format
	 * @return the meeting time as a string
	 */
	public String getMeetingString() {
		
		if (meetingDays.contains("A")) {
			return "Arranged";
		}
		String standardStart = getTimeString(startTime);
		String standardEnd = getTimeString(endTime);
			
		String meetingString = meetingDays + " " + standardStart + "-" + standardEnd;
		return meetingString;
	}

	/**
	 * Returns military time converted to standard format
	 * @param time time in military format
	 * @return standardTime the time in standard format
	 */
	private String getTimeString(int time) {
		String standardTime = "";
		String timeOfDay = "";
		
		int hour = time / 100;
		int minute = time % 100;
		
		if (hour > 12) {
			hour = hour - 12;
			timeOfDay = "PM";
		} else if (hour == 12) {
			timeOfDay = "PM";
		} else if (hour == 0) {
			hour = 12;
			timeOfDay = "AM";
		} else {
			timeOfDay = "AM";
		}
		
		String minuteString = "" + minute;
		
		if (minute < 10) {
			minuteString = "0" + minuteString;
		}
		
		standardTime = hour + ":" + minuteString + timeOfDay;
		
		return standardTime;
	}

	/**
	 * Returns the courses start time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * Checks an Activity for a conflict in the existing schedule
	 * An Activity is conflicting with another if there is an overlap of at least one day and time
	 * @param possibleConflictingActivity an activity that needs to be checked for conflicts
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String currentMeetingDays = this.getMeetingDays();
		String possibleMeetingDays = possibleConflictingActivity.getMeetingDays();

		
		for (char day : currentMeetingDays.toCharArray()) {
			if (possibleMeetingDays.indexOf(day) != -1 && day != 'A') {
				if (this.getStartTime() >= possibleConflictingActivity.getStartTime() && this.getStartTime() 
					<= possibleConflictingActivity.getEndTime()) {
					throw new ConflictException();
				} else if (this.getEndTime() >= possibleConflictingActivity.getStartTime() && this.getStartTime() 
						<= possibleConflictingActivity.getEndTime()) {
					throw new ConflictException();
				}
			}
		}
	}

}