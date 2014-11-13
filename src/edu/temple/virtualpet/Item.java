package edu.temple.virtualpet;

public class Item 
{
	private String name;
	private String description;
	private String imageURL;
	
	public Item(String name, String description, String imageURL)
	{
		setName(name);
		setDescription(description);
		setImageURL(imageURL);
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
}

