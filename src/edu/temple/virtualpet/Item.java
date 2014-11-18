package edu.temple.virtualpet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String itemId;
	private String inventoryId;
	private String name;
	private String description;
	private String imageUrl;
	
	public Item(String itemId, String inventoryId, String name, String description, String imageURL)
	{
		setName(name);
		setDescription(description);
		setImageUrl(imageURL);
		setInventoryId(inventoryId);
		setItemId(itemId);
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
		return Constants.SERVER + imageUrl;
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
	
	private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
		inputStream.defaultReadObject();
	}
	
	private void writeObject(ObjectOutputStream outputStream) throws IOException {
		outputStream.defaultWriteObject();
	}
	
}

