package edu.temple.virtualpet;

public class Pet {
	private String userPetId;
	private String name;
	private String description;
	private String nickname;
	private String imageUrl;
	public String getUserPetId() {
		return userPetId;
	}
	public void setUserPetId(String userPetId) {
		this.userPetId = userPetId;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImageUrl() {
		return Constants.SERVER + imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Pet(String userPetId, String name, String nickname, String description, String imageUrl) {
		setUserPetId(userPetId);
		setName(name);
		setNickname(nickname);
		setDescription(description);
		setImageUrl(imageUrl);
	}

}
