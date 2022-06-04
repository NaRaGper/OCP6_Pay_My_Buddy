package com.naragper.paymybuddy.model;

import lombok.Data;

@Data
public class SignupForm {
	
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String username = email;
}
