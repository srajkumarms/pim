<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>PIM - Products</title>
	<link href="styles/site.css" rel="stylesheet" />
	<link href="styles/bootstrap.css" rel="stylesheet" />
	<script src="scripts/vendor/jquery/jquery.js"></script>
	<script src="scripts/vendor/angular/angular.js"></script>
	<script src="scripts/vendor/handlebar/handlebars-v4.0.10.js"></script>
	<script src="scripts/vendor/bootstrap/bootstrap.js"></script>
	
	<script src="scripts/app/product.js"></script>
</head>
<body>
	<div class="header-container">
		<img src="" alt="Logo" title="Logo goes here" class="logo" />
		<span class="title">Product Information Management</span>
		<a onclick="logout()" class="settings-menu">LOGOUT</a>
	</div>
	
	<div class="content-container container-fluid">		
		<div class="products-container row">
			<h2>Manage Products:</h2>
			
			<div class="product-list col-md-6">
				
			</div>
			
			<div class="product-details col-md-6">				
				
			</div>
		</div>
		<script>
			var contextPath = "<%=request.getContextPath()%>";			
		</script>
		<script id="product-template" type="text/x-handlebars-template">
			<div class="responsive-table">
				{{#if products}}
				<table class="table table-hover">
					<thead>
						<th>#</th>
						<th>Name</th>
						<th>Description</th>
						<th>Active</th>
						<th>Related Products</th>
						<th>Actions</th>
					</thead>
					<tbody>
						{{#each products}}
							<tr id="row_{{id}}" onmouseover="mouseOver({{id}})" onmouseout="mouseOut({{id}})">
								<td onclick="populateProductDetails({{id}})">{{rowIndex @index}}</td>
								<td onclick="populateProductDetails({{id}})">{{name}}</td>
								<td onclick="populateProductDetails({{id}})" title="{{description}}">{{trimDescription description 50}}</td>
								<td onclick="populateProductDetails({{id}})">
									{{#if isActive}}
										<span class="glyphicon glyphicon-ok product-active"></span>
									{{else}}
										<span class="glyphicon glyphicon-remove product-inactive"></span>
									{{/if}}	
								</td>
								<td onclick="populateProductDetails({{id}})">{{relatedProducts.length}}</td>
								<td>
									<span id="action_{{id}}" class="hide-action">
										<span class="glyphicon glyphicon-edit edit-icon" onclick="editProduct({{id}})"></span> |
										<span class="glyphicon glyphicon-remove-circle delete-icon" onclick="deleteProduct({{id}})"></span>
									</span>
								</td>
							</tr>
						{{/each}}						
					</tbody>
				</table>
				{{/if}}
				{{#unless products}}
					<table class="table">
						<tr>
							<td>
								<h3>No records found.</h3>
							</td>
						</tr>
					</table>
				{{/unless}}
				<button id="btnSave" onclick="editProduct(-1)" class="create-new">Create New</button>
			</div>			
		</script>
		
		<script id="product-detail-template" type="text/x-handlebars-template">
			<div class="product row">
				<div class="product-image col-md-4">
					<img src="{{getImage image}}" width="150" height="150" />
				</div>
				<div class="product-details-section col-md-7">
					<h2>{{name}}</h2>
					<h4>{{description}}</h4>
					Active
					{{#if isActive}}
						<span class="glyphicon glyphicon-ok product-active"></span>
					{{else}}
						<span class="glyphicon glyphicon-remove product-inactive"></span>
					{{/if}}		
				</div>			
			</div>
			<div class="row">
				{{#if relatedProducts}}	
					<div class="related-products-section row">
						{{#each relatedProducts}}
							<div class="related-product" onclick="populateProductDetails({{id}})">
								<div class="product-image col-md-4">
									<img src="{{getImage image}}" width="75" height="75" />
								</div>
								<div class="related-product-details">
									<h4>{{name}}</h2>
									<h5 title="{{description}}">{{trimDescription description 30}}</h4>
								</div>
							</div>							
						{{/each}}
					</div>
				{{/if}}
			</div>
		</script>				
										
		<script id="product-details-edit" type="text/x-handlebars-template">
				<table>
					<tr>
						<td class="product-label">Name:</td>
						<td class="product-input">
							<input type="text" id="txtName" name="txtName" value="{{name}}" onchange="markDirty()"/> 
						</td>
					</tr>
					<tr>
						<td class="product-label">Description:</td>
						<td class="product-input">
							<textarea id="taDescription" name="taDescription" onchange="markDirty()">{{description}}</textarea> 
						</td>
					</tr>
					<tr>
						<td class="product-label"></td>
						<td class="product-input">
							<input type="checkbox" id="chkIsActive" onchange="markDirty()" />Is Active
						</td>
					</tr>
					<tr>
						<td class="product-label">Image:</td>
						<td class="product-input">
							<input type="text" id="txtImage" value="{{image}}" onchange="markDirty()" />
						</td>
					</tr>
					<tr>
						<td class="product-label">Related Products:</td>
						<td class="product-input product-selection-container">
							<section>			
								Available Products: <br />
								<select id="lstAvailableProducts" multiple="true" onchange="toggleButton('lstAvailableProducts', 'btnMoveRight')">
									{{#availableProducts}}
										{{#compareProduct id}}
											<option value="{{id}}">{{name}}</option>
										{{/compareProduct}}
									{{/availableProducts}}
								</select>
							</section>
							<span class="products-shifter">
								<button id="btnMoveRight" onclick="move('lstAvailableProducts', 'lstRelatedTo', 'btnMoveRight')" class="glyphicon glyphicon-chevron-right" disabled></button>
								<button id="btnMoveLeft" onclick="move('lstRelatedTo', 'lstAvailableProducts', 'btnMoveLeft')" class="glyphicon glyphicon-chevron-left" disabled></button>
							</span>
							<section>
								Related to: <br />
								<select id="lstRelatedTo" multiple="true" onchange="toggleButton('lstRelatedTo', 'btnMoveLeft')">
									{{#relatedProducts}}
										<option value="{{id}}">{{name}}</option>
									{{/relatedProducts}}
								</select>
							</section>
						</td>
					</tr>
					<tr>
						<td class="product-label"></td>
						<td class="product-input">							
							<button id="btnReset" onclick="reset()" class="product-reset">Reset</button>
							<button id="btnSave" onclick="saveProduct()" class="product-save">Save</button>
						</td>
					</tr>
				</table>	
		</script>
		
	</div>
	
</body>
</html>