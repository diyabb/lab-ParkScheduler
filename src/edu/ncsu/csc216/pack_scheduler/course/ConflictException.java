/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A class used to define behavior for a conflict exception
 * @author Nate Powers
 * @author John Kostic
 */
public class ConflictException extends Exception {

	/**
	 * Setting the default serialization value to ignore serialization
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructs and sets the default message for exception to be Schedule Conflict
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

	/**
	 * Constructor with a custom message
	 * @param message the message to display about exception
	 */
	public ConflictException(String message) {
		super(message);
	}

}
