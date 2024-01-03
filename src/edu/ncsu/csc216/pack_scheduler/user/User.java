package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Superclass for any user type that may utilize the program
 * @author Nate Powers
 * @author John Kostic
 */
public abstract class User {

	/** User's first name. */
	private String firstName;
	/** User's last name. */
	private String lastName;
	/** User's ID */
	private String id;
	/** User's email */
	private String email;
	/** User's password */
	private String password;
	
	/**
	 * constructs a new User
	 * @param userFirstName User's first name
	 * @param userLastName User's last name
	 * @param userID User's ID
	 * @param userEmail User's email
	 * @param userPassword User's email password
	 */
	public User(String userFirstName, String userLastName,
			String userID, String userEmail, String userPassword) {
		setFirstName(userFirstName);
		setLastName(userLastName);
		setId(userID);
		setEmail(userEmail);
		setPassword(userPassword);
	}

	/**
	 * Sets the user's first name
	 * @param firstName the first name to set
	 * @throws IllegalArgumentException if string is null or blank
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Returns the user's first name
	 * @return firstName the User's first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the user's last name
	 * @param lastName the last name to set
	 * @throws IllegalArgumentException if string is null or blank
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Returns the user's last name
	 * @return lastName the User's last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the user's ID
	 * @param userID the ID to set
	 * @throws IllegalArgumentException if user id is blank or null
	 */
	public void setId(String userID) {
		if (userID == null || "".equals(userID)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = userID;
	}

	/**
	 * Returns the user's ID
	 * @return id the user's ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the user's email
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
		this.email = email;
	}

	/**
	 * Returns the user's email
	 * @return email the user's email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the user's password
	 * @param userPassword the userPassword to set'
	 * @throws IllegalArgumentException if password is null or blank
	 */
	public void setPassword(String userPassword) {
		if (userPassword == null || "".equals(userPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = userPassword;
	}

	/**
	 * Returns the user's password
	 * @return password the user's password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * hashcode for User
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	
	/**
	 * simple equals method for User
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}