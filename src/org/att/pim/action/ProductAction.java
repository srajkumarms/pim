package org.att.pim.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.att.pim.model.Product;
import org.att.pim.model.ProductUI;
import org.att.pim.service.ProductService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductAction extends ActionSupport implements ModelDriven<List<Product>> {
	private List<Product> products;
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	
	
	private boolean isDeleted;
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	private boolean isAddEditSucceed;
	public boolean isAddEditSucceed() {
		return isAddEditSucceed;
	}

	public void setAddEditSucceed(boolean isAddEditSucceed) {
		this.isAddEditSucceed = isAddEditSucceed;
	}

	
	private ProductUI product;
	public ProductUI getProduct() {
		return product;
	}

	public void setProduct(ProductUI product) {
		this.product = product;
	}

	
	private int deleteProductId;
	public int getDeleteProductId() {
		return deleteProductId;
	}

	public void setDeleteProductId(int deleteProductId) {
		this.deleteProductId = deleteProductId;
	}
	
	private static final long serialVersionUID = -6765991741441442190L;
	private static final Logger logger = Logger.getLogger(ProductAction.class);
	
	public ProductAction() {
		logger.setLevel(Level.INFO);
		logger.debug("ProductAction instantiated");
		products = new ArrayList<Product>();
	}
	
	/*
	 * To retrieve a list of available products
	 * No instance member is used as input
	 * Returns success on successful updation
	 * or redirects to index page on failure
	 */
	public String getProductList() {
		try {
			if (validateSession()) {
				logger.debug("Retrieving available products");
				this.setProducts(ProductService.getInstance().getProductList());
				logger.debug("Retrieving available products succeeded");
				
				return SUCCESS;
			} else {
				throw new Exception("Invalid Session");
			}
		} catch (Exception ex) {
			logger.error("Retrieving available products", ex);
		}		
		
		return LOGIN;
	}
	
	/*
	 * To add or edit a product
	 * Instance member product of type ProductUI is used 
	 * as input fron UI
	 * Returns success on successful updation
	 * or redirects to index page on failure 
	 */
	public String addEditProduct() {		
		try {
			isAddEditSucceed = false;
			if (validateSession()) {
				logger.debug("Performing add/edit product");
				isAddEditSucceed = ProductService.getInstance().addEditProduct(getProduct());
				logger.debug("Add/edit product succeeded");
				
				return SUCCESS;
			} else {
				throw new Exception("Invalid Session");
			}			
		} catch (Exception ex) {
			logger.error("Add/edit product failed", ex);
		}		
		return ERROR;
	}
	
	/*
	 * To delete a product
	 * Instance member deleteProductId of type int is used 
	 * as input fron UI
	 * Returns success on successful deletion
	 * or redirects to index page on failure 
	 */
	public String deleteProduct() throws Exception {
		try {
			if (validateSession()) {
				logger.debug("Performing delete product");
				isDeleted = ProductService.getInstance().deleteProduct(getDeleteProductId()); 
				logger.debug("Delete product succeeded");
				
				return SUCCESS;
			} else {
				throw new Exception("Invalid Session");
			}
		} catch(Exception ex) {
			logger.error("Delete product failed", ex);
		}
		
		return ERROR;	
	}
	
	@Override
	public List<Product> getModel() {
		return products;
	}
	
	/*
	 * Private method to validate session
	 */
	private boolean validateSession(){
		HttpSession session=ServletActionContext.getRequest().getSession(false);
		
		return (session != null  && session.getAttribute("userId") != null && session.getAttribute("login") != null);
	}
}
