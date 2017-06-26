/* >>>Start: Handlebar Helpers */

Handlebars.registerHelper("trimDescription", function trimDescription(description, length) {
	return description.length > length ? description.substring(0, length-1) + "..." : description;
});


Handlebars.registerHelper("compareProduct", function compareProduct(id1, options) {
	// validate related products
	var result = false;
	if (selectedProduct.relatedProducts) {
		for(var i = 0; i < selectedProduct.relatedProducts.length; i++) {
			if (id1 === selectedProduct.relatedProducts[i].id) {
				result = true;
			}
		}
	}	
	
	return (id1 !== selectedProduct.id && !result) ? options.fn(this) : options.inverse(this);
});

Handlebars.registerHelper("rowIndex", function rowIndex(index, options) {
	return ++index;
});

Handlebars.registerHelper("getImage", function getImage(imageSrc) {
	return (imageSrc && imageSrc.length > 0) ? imageSrc : contextPath + "/resources/images/no-image.png";
});
/* <<<End: Handlebar Helpers */

var products = {},
	selectedProduct = null,
	isDirty = false;

$(document).ready(function onReady() {
	getProductList();
});

function getProductList() {
	createRequest("/pim/products/getProductList", null, null, "GET", getProductSuccess, getProductFailure);
}

function getProductSuccess(data) {
	 products = {
		products: data
	};
	var	rawTemplate = $("#product-template").html(),
		generatedTemplate = Handlebars.compile(rawTemplate),
		compiledTemplate = generatedTemplate(products),
		productsContainer = $(".product-list").html(compiledTemplate);
}

function getProductFailure(data) {
	console.log("Error")
}

function createRequest(url, data, dataType, method, successCallback, failureCallback) {
	var request = undefined;
	
	switch(method) {
		case "GET":
			request = $.ajax({
				url: url,
				type: method
			})
			.done(successCallback)
			.fail(failureCallback);
			break;
		case "POST":
			request = $.ajax({
				url: url,
				data: data ? data : "",
				dataType: "JSON",
				type: method,
				contentType: 'application/json'
			})
			.done(successCallback)
			.fail(failureCallback);
			break;
	}
	
	return request;
}

function mouseOver(id) {
	$("#action_" + id).toggleClass("show-action", true);
}

function mouseOut(id) {
	$("#action_" + id).toggleClass("show-action", false);
}

function setSelected(id) {	
	for(var product of products.products) {
		if (product.id === id) {
			selectedProduct = product;
			break;
		}
	}
	
	selectedProduct.editMode = false;
}

function checkDirty() {	
	if (isDirty) {
		if (!confirm("You have unsaved changes. Do you want to proceed?")) {
			return;
		} else {
			isDirty = false;
		}
	}
}
function populateProductDetails(id) {
	checkDirty();	
	setSelected(id);	
	var	rawTemplate = $("#product-detail-template").html(),
		generatedTemplate = Handlebars.compile(rawTemplate),
		compiledTemplate = generatedTemplate(selectedProduct),
		productsContainer = $(".product-details").html(compiledTemplate);	

	$(".product-details").css("opacity", "1");
	$("tr[id^='row_']").each(function iterator(index, element) {
		element.style.background = "none";
	});	
	$("#row_" + id).css("background", "lightskyblue");
}


function editProduct(id) {
	checkDirty();
	if (id >= 0) {
		setSelected(id);		
	} else {
		selectedProduct = {};
	}
	
	selectedProduct.editMode = true;
	selectedProduct.availableProducts = (products && products.products) ? products.products : {};
	
	var	rawTemplate = $("#product-details-edit").html(),
		generatedTemplate = Handlebars.compile(rawTemplate),
		compiledTemplate = generatedTemplate(selectedProduct),
		productsContainer = $(".product-details").html(compiledTemplate);	
	
	$(".product-details").css("opacity", "1");
	$("tr[id^='row_']").each(function iterator(index, element) {
		element.style.background = "none";
	});	
	$("#row_" + id).css("background", "lightskyblue");
	$("#chkIsActive").prop("checked", selectedProduct.isActive);	
}


function move(list1, list2, button) {
	$("#" + list1 + "> option").each(function iterator(index, element) {
		if (element.selected) {
			$("#" + list2).append(new Option(element.text, element.value));
			element.remove();
		}
	})
	
	$("#" + button).attr("disabled", true);
	event.preventDefault();
}

function toggleButton(list, button) {
	var selected = false;
	
	$("#" + list + "> option").each(function iterator(index, element) {
		if (element.selected) {
			selected = true;
		}
	});
	
	$("#" + button).attr("disabled", !selected);
	markDirty();
}

function getRelatedProducts() {
	var relatedTo = "";
	$("#lstRelatedTo > option").each(function iterator(index, element) {		
		relatedTo += element.value + ",";
	});
	
	relatedTo = (relatedTo.length> 0) ? relatedTo.substring(0, relatedTo.length - 1) : "";
	return relatedTo;
}

function saveProduct() {	
	if(!validate()) {
		return;
	}
	
	isDirty = false;
	var dataObj = {
			"product" : {
				"id" : (selectedProduct && selectedProduct.id) ? selectedProduct.id : -1,
				"name" : $("#txtName").val(),
				"description" : $("#taDescription").val(),
				"isActive" : $("#chkIsActive").is(':checked'),
				"image": $("#txtImage").val() ? $("#txtImage").val() : "",
				"relatedProducts" : getRelatedProducts()
			}
		};

	var data1 = JSON.stringify(dataObj);
	createRequest("/pim/products/addEditProduct.action", data1, "JSON", "POST", saveProductSuccess, saveProductFailure);
}

function saveProductSuccess(data) {
	getProductList();
	editProduct(-1);
} 

function saveProductFailure(data) {
	//TODO:
}

function deleteProduct(id) {
	if (confirm("Are you sure you want to delete this product?")) {
		var dataObj = {
				"deleteProductId": id
			};

		var data1 = JSON.stringify(dataObj);
		createRequest("/pim/products/deleteProduct.action", data1, "JSON", "POST", deleteProductSuccess, deleteProductFailure);
	}	
}

function deleteProductSuccess(data) {
	getProductList();
	editProduct(-1);
} 

function deleteProductFailure(data) {
	//TODO:
}

function reset() {
	checkDirty();
	isDirty = false;
	editProduct(selectedProduct.id);
}


function markDirty() {
	isDirty = true;
}

function validate() {
	var txtName = $("#txtName"),
		txtDescription = $("#taDescription"),
		txtImage = $("#txtImage"),
		result = true;
	
	if (txtName.val().trim().length === 0) {
		txtName.toggleClass("input-error", true);
		if ($("#errTxtName").length === 0) {
			txtName.parent().append("<span class='glyphicon glyphicon-warning-sign error-indicator' id='errTxtName' title='Product name cannot be empty'></span>");
		}		
		result = false;
	} else {
		txtName.toggleClass("input-error", false);
		$("#errTxtName").remove();
	}
	
	if (txtDescription.val().trim().length === 0) {
		txtDescription.toggleClass("input-error", true);
		if ($("#errTaDescription").length === 0) {
			txtDescription.parent().append("<span class='glyphicon glyphicon-warning-sign error-indicator' id='errTaDescription' title='Product description cannot be empty'></span>");
		}
		result = false;
	} else {
		txtDescription.toggleClass("input-error", false);
		$("#errTaDescription").remove();
	}
	
	/*
	if (txtImage.val().trim().length === 0) {
		txtImage.toggleClass("input-error", true);
		if ($("#errTxtImage").length === 0) {
			txtImage.parent().append("<span class='glyphicon glyphicon-warning-sign error-indicator' id='errTxtImage' title='Product image cannot be empty'></span>");
		}
		result = false;
	} else {
		txtImage.toggleClass("input-error", true);
		$("#errTxtImage").remove();
	}
	*/
	return result;
}

function logout() {
	// createRequest("/pim/logout.action", null, null, "GET", logoutSuccess, logoutFailure);
	window.location.href = "/pim/logout";
}

function logoutSuccess() {
	console.log("Logout Success");
}

function logoutFailure() {
	console.log("Logout Failure");
}