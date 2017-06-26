package org.att.pim.model;

public class User {
	private String userID;
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	private String password;
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() { }
	
	public User(String userID, String password) {
		this.setUserID(userID);
		this.setPassword(password);
	}
}
