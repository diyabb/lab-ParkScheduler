
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * A class used to define behavior for an invalid state transition
 * @author Nate Powers
 * @author John Kostic
 */
public class InvalidTransitionException extends Exception {
	/**
	 * Setting the default serialization value to ignore serialization
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructs and sets the default message for the exception
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

	/**
	 * Constructor with a custom message for the exception
	 * @param message the message to display about exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
}
