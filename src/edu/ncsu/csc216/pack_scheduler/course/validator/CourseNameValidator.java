package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class implements the CourseNameValidatorFSM. 
 * This class implements four states, InitialState, LetterState, DigitState and SuffixState, 
 * all of which implement the State abstract class. This checks for whether a submitted Course name
 * is a valid entry or not.
 * @author John Kostic
 * @author Nate Powers
 *
 */
public class CourseNameValidator {
	
	/** Initial state before input is examined */
	private final State stateInitial = new InitialState();
	/** State in which a letter is being processed */
	private final State stateLetter = new LetterState();
	/** State in which a number is being processed */	
	private final State stateNumber = new NumberState();
	/** State in which a closing suffix is being processed */	
	private final State stateSuffix = new SuffixState();
	/** State which is  currently being handled */
	private State currentState;
	/** determines whether or not a valid end state has been reached */	
	private boolean validEndState = false;
	/** number of letters that have been processed */	
	private int letterCount;
	/** number of digits that have been processed */		
	private int digitCount;
	
	/**
	 * abstract class representing the State a courseName is in in the FSM
	 * This abstract class contains the methods related to transitions between states
	 * @author John Kostic
	 *
	 */
	public abstract class State {
		
		/**
		 * abstract method for handling a letter input
		 * @throws InvalidTransitionException if input invalid
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * abstract method for handling a digit input
		 * @throws InvalidTransitionException if input invalid
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * abstract method for handling any non-letter/digit input
		 * @throws InvalidTransitionException if input invalid
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * class for handling the InitialState
	 * @author John Kostic
	 * @throws InvalidTransitionException if illegal transition of State
	 *
	 */
	private class InitialState extends State {

		@Override
		public void onLetter()  {
			currentState = stateLetter;
			letterCount++;
			
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
			
		}
		
	}
	
	/**
	 * class for handling the LetterState
	 * @author John Kostic
	 * @throws InvalidTransitionException if illegal transition of State
	 *
	 */
	private class LetterState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < 4) {
			currentState = stateLetter;
			letterCount++;
			}
			else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			if (letterCount >= 1 && letterCount <= 4) {
			currentState = stateNumber;
			digitCount++;
			}
			else { 
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}
		
	}
	
	/**
	 * class for handling the DigitState
	 * @author John Kostic
	 *
	 */
	private class NumberState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount < 3) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
			else {
				currentState = stateSuffix;			
			}
			
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < 3) {
				currentState = stateNumber;
				digitCount++;
			}
			else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			if (digitCount == 3) {
				validEndState = true;
			}
		}		
	}
	
	/**
	 * class for handling the suffix state
	 * @author John Kostic
	 *
	 */
	private class SuffixState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");						
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
			
		}
		
	}
	
	/**
	 * tests whether or not a Course name is valid
	 * @param courseName to be tested for validity
	 * @return true if the Course name matches the needed format, false if otherwise
	 * @throws InvalidTransitionException if any illegal State transitions are attempted
	 */
	public boolean isValid (String courseName) throws InvalidTransitionException {
		letterCount = 0;
		digitCount = 0;
		currentState = stateInitial;
		
		for (int i = 0; i < courseName.length(); i++) {
			if (Character.isLetter(courseName.charAt(i))) {
				currentState.onLetter();
			}
			else if (Character.isDigit(courseName.charAt(i))) {
					currentState.onDigit();
				}
			else {
				currentState.onOther();
			}
		}
		return validEndState;
	}
}
