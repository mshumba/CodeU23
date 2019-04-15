package com.google.codeu.data;

import java.util.UUID;
import java.lang.err;

/** A single message posted by a user. */
public class User {

  private UUID id;
  private String userName;
  private String gender;
  private String birthday;
  private String description;
//  private String schoolID;
  private long timestamp;

  public User(String userName, String gender, String birthday, String description) {
    this(UUID.randomUUID(), user, gender,birthday,description, System.currentTimeMillis());
  }
  public User(UUID id, String userName, String gender, String birthday, String description,long timestamp) {
    this.id = id;
    this.userName = userName;
    this.gender = gender;
    this.birthday = birthday;
    this.description = description;
    this.timestamp = timestamp;
  }
  //getter and setter
	/**
	* Returns value of id
	* @return
	*/
	public UUID getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	* Returns value of userName
	* @return
	*/
	public String getUserName() {
		return userName;
	}

	/**
	* Sets new value of userName
	* @param
	*/
	public void setUserName(String userName) {
      this.userName = userName;
    }
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

	/**
	* Returns value of schoolID
	* @return
	*/
	public String getSchoolID() {
		return schoolID;
	}

	/**
	* Sets new value of schoolID
	* @param
	*/
	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	/**
	* Returns value of timestamp
	* @return
	*/
	public long getTimestamp() {
		return timestamp;
	}

	/**
	* Sets new value of timestamp
	* @param
	*/
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
