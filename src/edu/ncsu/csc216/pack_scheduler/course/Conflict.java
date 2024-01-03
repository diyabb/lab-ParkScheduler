package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A class used to handle conflicts and exceptions with Courses
 * @author Nate Powers
 * @author John Kostic
 */
public interface Conflict {
	
	/**
	 * A method used to check if there is a Course conflict
	 * @param possibleConflictingActivity an activity that needs to be checked for conflicts
	 * @throws ConflictException if exception is found
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
	

}
