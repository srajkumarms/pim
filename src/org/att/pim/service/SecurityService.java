package org.att.pim.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.att.pim.model.Logon;
import org.att.pim.model.User;

public class SecurityService {

	private static final Logger logger = Logger.getLogger(SecurityService.class);
	
	private List<User> users;
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public SecurityService() {
		logger.setLevel(Level.INFO);
		this.users = new ArrayList<User>();
		loadUsers();
	}
	
	public boolean doLogin(Logon logon) {		
		logger.debug("Performing authetication");
		boolean result = false;
		
		Iterator<User> iterator = this.getUsers().iterator();		
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			if (user.getUserID().equals(logon.getUsername()) && user.getPassword().equals(logon.getPassword())) {
				result = true;
				break;
			}
		}
		
		if (result) {
			logger.debug("Authetication succeeded");
		} else {
			logger.debug("Authetication failed");
		}
		
		return result;
	}

	private void loadUsers() {
		Properties prop = new Properties();
		try {
			logger.debug("Reading properties for users");
			prop.load(this.getClass().getClassLoader().getResourceAsStream("credentials.properties"));
			this.users.add(new User(prop.getProperty("user1.userID"), prop.getProperty("user1.password")));
			this.users.add(new User(prop.getProperty("user2.userID"), prop.getProperty("user2.password")));			
			
			logger.debug("Loading users from properties file succeeded. Found " + this.users.size() + " users");
		} catch (IOException ex) {
			logger.error("Error occurred while reading user properties file", ex);
		}
	}	
}
