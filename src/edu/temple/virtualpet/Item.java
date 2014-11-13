package edu.temple.virtualpet;

public class Item 
{
	private String itemId;
	private String inventoryId;
	private String name;
	private String description;
	private String imageUrl;
	
	public Item(String name, String description, String imageURL)
	{
		setName(name);
		setDescription(description);
		setImageUrl(imageURL);
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageURL) {
		this.imageUrl = imageURL;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	
}

