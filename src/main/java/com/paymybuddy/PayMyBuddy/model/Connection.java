package com.paymybuddy.PayMyBuddy.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Connection {
	
	@Id
	private int id;
	@NotNull
	private int userId1;
	@NotNull
	private int userId2;
	@NotNull
	private String date;
	
	public Connection() {
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId1() {
		return userId1;
	}

	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}

	public int getUserId2() {
		return userId2;
	}

	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
