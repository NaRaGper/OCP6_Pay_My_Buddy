package com.naragper.paymybuddy.dto;

import com.naragper.paymybuddy.model.PaymentType;

public class UserTransactionsDTO {
	
	// Informations needed by the page transfer.html
	private String username;
	private PaymentType type;
	private String description;
	private double amount;
	
	public UserTransactionsDTO(String username, PaymentType type, String description, double amount) {
		super();
		this.username = username;
		this.type = type;
		this.description = description;
		this.amount = amount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
