package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This class houses the instance of Registration Manager as well as the behavior associated with it
 * @author John Kostic
 * @author Blake Boykin 
 * @author Diya Bhavsar
 */
public class RegistrationManager {
	
	/** the private instance field */
	private static RegistrationManager instance;
	/** the private courseCatalog field */
	private CourseCatalog courseCatalog = new CourseCatalog();
	/** the private studentDirectory field */
	private StudentDirectory studentDirectory = new StudentDirectory();
	/** private field for a registrar User */
	private User registrar;
	/** private field to store current User */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** the properties file with registrar credentials */
	private static final String PROP_FILE = "registrar.properties";
    /** New private field for FacultyDirectory */
    private FacultyDirectory facultyDirectory;
	
	
	/**
	 * The constructor for a Registration Manager object
	 */
	private RegistrationManager() {
		createRegistrar();
		facultyDirectory = new FacultyDirectory();
//        facultyDirectory.newFacultyDirectory();  
//        createRegistrar();
		
	}
	
	/**
	 * Returns the faculty directory.
	 * @return the faculty directory.
	 */
	public FacultyDirectory getFacultyDirectory() {
	    return facultyDirectory;
	}
	
	/**
	 * the code provided to create a registrar object and assign course catalog and student directory
	 * to the Registration manager
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
		
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
	}
	
	/**
	 * the provided code to create a hashed password
	 * @param pw the password to hash
	 * @return the hashed password string
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * The method to ensure there is an instance of Registration Manager at all times
	 * @return instance the singleton instance of RM
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the course catalog associated with the instance of Registration Manager
	 * @return courseCatalog the course Catalog to return
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the student directory associated with the instance of Registration Manager
	 * @return studentDirectory the student directory to return
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	
	/**
	 * The login method for registration manager
	 * @param id the user id to login with
	 * @param password the user password to login with
	 * @return true/false if login was successful or not
	 */
	public boolean login(String id, String password) {
		
		if (currentUser != null) {
			return false;
		}
		
		Student s = studentDirectory.getStudentById(id);
		
		String localHashPW = hashPW(password);
		
		if (s == null && !registrar.getId().equals(id)) {
			throw new IllegalArgumentException("User doesn't exist.");
		} else if (s != null && s.getPassword().equals(localHashPW)) {
			currentUser = s;
			return true;
		} else if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
			currentUser = registrar;
			return true;
		}
		
		return false;
		
//		if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
//			currentUser = registrar;
//			return true;
//		} else if (s == null) {
//			throw new IllegalArgumentException("User doesn't exist.");
//		} else if (s.getPassword().equals(localHashPW)) {
//			currentUser = s;
//		} else if (registrar.getId().equals(id) && !registrar.getPassword().equals(localHashPW)) {
//			return false;
//		}
		
		//check if user 
//		if (s == null) {
//			throw new IllegalArgumentException("User doesn't exist.");
//		}
//		
//		if (s.getPassword().equals(localHashPW)) {
//			currentUser = s;
//			return true;
//		}
	}
	
	/**
	 * Method for logging out of the RegistrationManager
	 * Sets currentUser to null when logging out
	 */
	public void logout() {
		currentUser = null;
	}
	
	/**
	 * Method to return the current user
	 * @return currentUser the current user of the registration manager
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * method to clear the catalog and student directory on load
	 */
	public void clearData() {
//		if (this.courseCatalog != null && this.studentDirectory != null) {
			courseCatalog.newCourseCatalog();
			studentDirectory.newStudentDirectory();
			
			facultyDirectory.newFacultyDirectory();
			currentUser = null;

	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Adds the given faculty to the specified course. 
	 * If the currentUser isn’t null and is the Registrar, 
	 * the Faculty’s FacultySchedule should be updated with the given Course.
	 * Any exceptions should be propagated to the GUI.
	 * @param course the course to add faculty to
	 * @param faculty the faculty to be added to the course
	 * @throws IllegalArgumentException if the currentUser is not the Registrar
	 * @return true if the faculty was added to the course, false if not
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
	    if (currentUser != null && currentUser.equals(registrar)) { 
//	    if (currentUser instanceof Registrar) {
	        try {
//	            if (course.getInstructorId() != null) {
//	                throw new IllegalArgumentException("The course already has an instructor.");
//	            }
//
//	            FacultySchedule facultySchedule = faculty.getSchedule();
//	            facultySchedule.addCourseToSchedule(course);
//	            course.setInstructorId(faculty.getId());
//
//	            return true;
	        	
	            faculty.getSchedule().addCourseToSchedule(course);
	            return true;	        	
	        	
	        } catch (Exception e) {
	            throw new IllegalArgumentException();
	        }
	    } else {
	        throw new IllegalArgumentException("Only the Registrar can add faculty to a course.");
	    }
	}
	
	/**
	 * Removes the given faculty from the specified course.
	 * If the currentUser isn’t null and is the Registrar,
	 * the Faculty’s FacultySchedule should be updated by removing the given Course.
	 * Any exceptions should be propagated to the GUI.
	 * @param course the course to remove faculty from
	 * @param faculty the faculty to be removed from the course
	 * @throws IllegalArgumentException if the currentUser is not the Registrar
	 * @return true if the faculty was removed from the course, false if not
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
//	    if (currentUser != null && currentUser instanceof Registrar) { (gives pmd)
	    if (currentUser != null && currentUser.equals(registrar)) { 
	    	try {
	            FacultySchedule facultySchedule = faculty.getSchedule();
	            facultySchedule.removeCourseFromSchedule(course);
	            return true;
	        } catch (IllegalArgumentException e) {
	            throw e;
	        }
	    } else {
	        throw new IllegalArgumentException("Only the Registrar can remove faculty from a course.");
	    }
	}
	
	/**
	 * Resets the schedule of the given faculty.
	 * If the currentUser isn’t null and is the Registrar,
	 * the Faculty’s FacultySchedule should be reset.
	 * Any exceptions should be propagated to the GUI.
	 * @param faculty the faculty whose schedule needs to be reset
	 * @throws IllegalArgumentException if the currentUser is not the Registrar
	 */
	public void resetFacultySchedule(Faculty faculty) {
//	    if (currentUser != null && currentUser instanceof Registrar) { (gives pmd)
	    if (currentUser != null && currentUser.equals(registrar)) { 
	    	FacultySchedule facultySchedule = faculty.getSchedule();
	        facultySchedule.resetSchedule();
	    } else {
	        throw new IllegalArgumentException("Only the Registrar can reset the faculty schedule.");
	    }
	}

	
	/**
	 * Inner class to store behaviors surrounding the registrar user
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user.
		 * @param firstName the registrar's first name
		 * @param lastName the registrar's last name
		 * @param id the registrar's id
		 * @param email the registrar's email
		 * @param hashPW the hashed pawssword
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}