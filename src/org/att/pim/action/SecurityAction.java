package org.att.pim.action;

import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.att.pim.model.Logon;
import org.att.pim.service.SecurityService;


public class SecurityAction extends ActionSupport implements ModelDriven<Logon>, SessionAware {
	
	private String greeting;
	private String errorMessage;
	private Logon logon = new Logon();
	private SessionMap<String,Object> sessionMap; 
	
	private static final long serialVersionUID = -6765991741441442190L;
	private static final Logger logger = Logger.getLogger(SecurityAction.class);
	
	public Logon getLogon() {
		return logon;
	}

	public void setLogon(Logon logon) {
		this.logon = logon;
	}
	
	public SecurityAction() {
		logger.setLevel(Level.INFO);
	}

	/*
	 * Default action method for login
	 * Returns success on successful login
	 * or redirects to login page on failure.
	 * Also creates session and add attributes for 
	 * validation of subsequent calls. 
	 */
	public String execute() {
		logger.debug("In SecurityAction to validate credentials");
		
		
		if (SecurityService.getInstance().doLogin(logon)) {
			setErrorMessage("");
			sessionMap.put("login", true);
			sessionMap.put("userId", logon.getUsername());
			logger.debug("User validation succeeded");
			return SUCCESS;
		} else {
			setErrorMessage("Invalid username/password");
			logger.debug("User validation failed");
			return LOGIN;
		}
	}
	
	/*
	 * Logout method clears the session and
	 * redirects to index page.
	 */
	public String logout() {
		logger.debug("Perform logout");
		try {
			if (sessionMap != null) {
				sessionMap.invalidate();
			}
			logger.debug("Logout succeeded");
			return SUCCESS;
		} catch (Exception ex) {
			logger.debug("Error occurred during Logout");
			return ERROR;
		}
	}
	
	/*
	public void validate() {
		if (StringUtils.isEmpty(logon.getUsername())) {
			logger.debug("Username was empty");
			addFieldError("username", "Username is empty");
		}
		
		if (StringUtils.isEmpty(logon.getPassword())) {
			logger.debug("Password was empty");
			addFieldError("password", "Password is empty");
		}
	}
	
	*/

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public Logon getModel() {
		return logon;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		sessionMap = (SessionMap<String, Object>)session;		
	}
}
