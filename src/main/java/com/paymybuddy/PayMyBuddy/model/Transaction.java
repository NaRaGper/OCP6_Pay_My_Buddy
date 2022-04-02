package com.paymybuddy.PayMyBuddy.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Transaction {
	
	@Id
	private int id;
	@NotNull
	private enum Type {
		USER, TOBANK, FROMBANK, TOCASH, FROMCASH
	}
	@NotNull
	private double amount;
	@NotNull
	private String date;
	private String description;
	private int senderId;
	private int receiverId;
	
	public Transaction() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
}
