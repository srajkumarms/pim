package org.att.pim.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.att.pim.model.Product;
import org.att.pim.model.ProductUI;

public class ProductService {
	private List<Product> products;
	
	private static ProductService instance;
	private static final Logger logger = Logger.getLogger(ProductService.class);
	
	private ProductService() {
		logger.setLevel(Level.INFO);
		logger.debug("ProductService instantiated");
		products = new ArrayList<Product>();
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	

	public List<Product> getProductList() {
		return this.products;
	}
	
	public boolean addEditProduct(ProductUI product) throws Exception {		
		boolean result = false;		
		try {
			logger.debug("Inside add/edit product");
			result = (product.getId() >= 0) ? editProduct(mapUiProduct(product)) : addProduct(mapUiProduct(product));
			logger.debug("Add/edit product succeeded");
		} catch(Exception ex) {
			logger.error("Add/edit product failed", ex);
			return result;
		}
		
		return result;
	}
	
	private Product mapUiProduct(ProductUI productUi) {
		logger.debug("Mapping UI prodcut to ProductService product");
		Product product = new Product();
		product.setId(productUi.getId());
		product.setName(productUi.getName());
		product.setDescription(productUi.getDescription());
		product.setIsActive(productUi.getIsActive());
		product.setImage(productUi.getImage());
		
		List<Product> relatedTo = new ArrayList<Product>();
		if (productUi.getRelatedProducts().length() > 0) {
			String[] relatedProducts = productUi.getRelatedProducts().split(","); 
			for(int i = 0; i < relatedProducts.length ; i++) {
				int id = Integer.parseInt(relatedProducts[i]);
				Optional<Product> result = this.products.stream().filter(item -> item.getId() == id).findFirst();
				
				if(result.isPresent()) {
					relatedTo.add(result.get());
				}
			}			
		}
		product.setRelatedProducts(relatedTo);
		logger.debug("Mapping UI product succeeded");
		return product;
	}
	
	private boolean addProduct(Product product) throws Exception {
		try {
			logger.debug("Performing Add product");
			product.setId(getMaxId() + 1);
			this.products.add(product);
			logger.debug("Add product succeeded");
		} catch(Exception ex) {
			logger.error("Add product failed", ex);
			return false;
		}		
		
		return true;
	}
	
	private int getMaxId() {
		logger.debug("Entering to get max id");
		int maxId = 0;
		
		if (this.products.size() > 0) {
			Iterator<Product> iterator = this.products.iterator();
			while(iterator.hasNext()) {
				int id = iterator.next().getId(); 
				maxId = id > maxId ? id : maxId;
			}
		}
		logger.debug("Got max id");
		return maxId;
	}
	
	public boolean editProduct(Product product) throws Exception {
		logger.debug("Performing edit product");
		try {
			int index = 0;
			Iterator<Product> iterator = this.products.iterator();
			while(iterator.hasNext()) {
				if (iterator.next().getId() == product.getId()) {
					break;
				}
				index++;
			}
			
			this.products.set(index, product);
			logger.debug("Edit product succeeded");
		} catch(Exception ex) {
			logger.error("Edit product failed", ex);
			return false;
		}		
		
		return true;
	}
	
	public boolean deleteProduct(int id) throws Exception {
		logger.debug("Performing delete product");
		try {
			Optional<Product> result = this.products.stream().filter(item -> item.getId() == id).findFirst();
			if (result.isPresent()) {
				Product product = result.get();
				this.products.remove(product);
				logger.debug("Deleteproduct succeeded");
			} else {
				logger.error("Deleting product not found");
				throw new Exception("Unable to delete product");
			}			
		} catch(Exception ex) {
			logger.error("Delete product failed", ex);
			return false;
		}		
		
		return true;
	}

	public static ProductService getInstance() {
		if (instance == null) {			
			instance = new ProductService();
			logger.debug("ProductService singleton instance created");
		}
		
		return instance;
	}
}
