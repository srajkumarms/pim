<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>PIM - Login</title>
<link href="styles/site.css" rel="stylesheet" />
<link href="styles/bootstrap.css" rel="stylesheet" />
<script src="scripts/vendor/jquery/jquery.js"></script>
<script src="scripts/app/index.js"></script>

</head>
<body>	
	<div class="logon-container">
		<span class="form-caption">Logon</span>
		<t:form action="login" validate="false" onsubmit="return doLogin()">
			<t:textfield id="txtUsername" name="username" key="username" label="Username" class="input" ></t:textfield>
			
			<t:password id="txtPassword" name="password" key="password" label="Password" class="input"></t:password>
				
			<t:label name="errorMessage" id="lblLoginError"></t:label>
			
			<div class="logon-controls">
				<input type="button" class="btnReset" value="Reset" onclick="resetForm()" />
				<input type="submit" class="btnLogon" value="Logon" />
			</div>	
		</t:form>			
	</div>
</body>
</html>