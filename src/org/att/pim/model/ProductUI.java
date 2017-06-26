package org.att.pim.model;

public class ProductUI {
	private int id;
	private String name;
	private String description;
	private Boolean isActive;
	private String image;
	private String relatedProducts;
	
	public ProductUI() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(String relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
