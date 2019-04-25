package com.google.codeu.data;


import java.util.ArrayList;

/** A single message posted by a user. */
public class User{

  private String userName;
  private String gender;
  private String email;
  private String birthday;
  private String description;
  private Datastore datastore;

  public User(String userName, String email, String gender, String birthday, String description) {
    this.userName = userName;
    this.email = email;
    this.gender = gender;
    this.birthday = birthday;
    this.description = description;
  }

	/**
	* Returns value of userName
	* @return
	*/
	public String getUserName() {
		return userName;
	}

	public User getUserFromEmail(String email){
		ArrayList<User> use = datastore.getAllUsers();
		for(User u:use){
			if(u.getEmail().equals(email)){
				return u;
		}
		}

		return null;
	}
	/**
	* Sets new value of userName
	* @param
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	* Returns value of gender
	* @return
	*/
	public String getGender() {
		return gender;
	}

	/**
	* Sets new value of gender
	* @param
	*/
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	* Returns value of email
	* @return
	*/
	public String getEmail() {
		return email;
	}

	/**
	* Sets new value of email
	* @param
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	* Returns value of birthday
	* @return
	*/
	public String getBirthday() {
		return birthday;
	}

	/**
	* Sets new value of birthday
	* @param
	*/
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	* Returns value of description
	* @return
	*/
	public String getDescription() {
		return description;
	}

	/**
	* Sets new value of description
	* @param
	*/
	public void setDescription(String description) {
		this.description = description;
	}
}
	
