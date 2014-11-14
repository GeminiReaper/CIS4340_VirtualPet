package edu.temple.virtualpet;

//We will save this data somewhere more permanent at some point but until then they'll
//go in this class.
public class UserData {
	private static UserData instance;
	private String userId;
	private String username;
	private String email;
	private String petNickname;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPetNickname() {
		return petNickname;
	}
	public void setPetNickname(String petNickname) {
		this.petNickname = petNickname;
	} 
	
	public static UserData getInstance() {
		if(instance == null) {
			instance = new UserData();
		}
		return instance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
