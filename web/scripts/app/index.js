function doLogin() {
	return validate();
}

function validate() {
	
	var txtUsername = $("#txtUsername"),
		txtPassword = $("#txtPassword"),
		result = true;
	
	if (txtUsername.val().trim().length === 0) {
		txtUsername.toggleClass("input-error", true);
		if ($("#errTxtUsername").length === 0) {
			txtUsername.parent().append("<span class='glyphicon glyphicon-warning-sign error-indicator' id='errTxtUsername' title='Username cannot be empty'></span>");
		}		
		result = false;
	} else {
		txtUsername.toggleClass("input-error", false);
		$("#errTxtUsername").remove();
	}
	
	if (txtPassword.val().trim().length === 0) {
		txtPassword.toggleClass("input-error", true);
		if ($("#errTxtPassword").length === 0) {
			txtPassword.parent().append("<span class='glyphicon glyphicon-warning-sign error-indicator' id='errTxtPassword' title='Password cannot be empty'></span>");
		}
		result = false;
	} else {
		txtPassword.toggleClass("input-error", false);
		$("#errTxtPassword").remove();
	}
	
	return result;
}

function resetForm() {	
	$("#txtUsername").val("");
	$("#txtPassword").val("");
}