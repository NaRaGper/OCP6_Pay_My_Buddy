package com.naragper.paymybuddy.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.model.ContactForm;
import com.naragper.paymybuddy.model.SignupForm;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.service.IConnectionService;
import com.naragper.paymybuddy.service.IUserService;

@Controller
public class ApplicationController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IConnectionService connectionService;
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/signup")
	public String showSignup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute("signupForm") SignupForm signupForm, Model model) {
		User user = new User();
		user.setEmail(signupForm.getEmail());
		user.setHash(BCrypt.hashpw(signupForm.getPassword(), BCrypt.gensalt()));
		User result = userService.postUser(user);
		if (result != null) {
			return "redirect:login";
		}
		return "signup";
	}
	
	@GetMapping("/login")
	public String showLogin(/*Model model*/) {
		//model.addAttribute("loginForm", new Form());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(/*@ModelAttribute("loginForm") Form form, Model model*/) {
//		System.out.println("TEST MESSAGE");
//		User user = userService.getUserFromEmail(form.getEmail());
//		System.out.println("********************" + user.getHash());
//		System.out.println(user.getEmail());
//		boolean isPasswordValid = userService.verifyHash(form.getPassword(), user.getHash());
//		System.out.println(isPasswordValid);
//		if (isPasswordValid != true) {
//			return "login";
//		}
		return "redirect:index";
	}
	
	@GetMapping("/transfer")
	public String showTransfer() {
		return "transfer";
	}
	
	// Post mapping transfer
	
	@GetMapping("/contacts")
	public String showContacts(Model model) {
		model.addAttribute("contactForm", new ContactForm());
		return "contacts";
	}
	
	@PostMapping("/contacts")
	public String contacts(@ModelAttribute("contactForm") ContactForm contactForm, Model model) {
		Connection connection = new Connection();
		
		// Get a user using a given adress
		User contact = userService.getUserFromEmail(contactForm.getEmail());
		
		contactForm.setSenderId(1); // Hardcoded userId until Spring Security session is added
		connection.setUserId1(contactForm.getSenderId());
		connection.setDate(new Date());
		// If there is a user found, verify that the adress is not it's own
		if (contact != null) {
			if (connection.getUserId1() == contact.getId()) {
				model.addAttribute("errorSameAdressMessage", "The email adress entered is your own.");
			} else {
				connection.setUserId2(contact.getId()); // SET USER_ID_2 ONLY IN "ELSE", CAUSES CONSTRAINT ERROR IF NOT USED
			}
		}
		
		// If there is no user found, the result will be null
		Connection result = connectionService.postConnection(connection);
		
		if (result != null) {
			model.addAttribute("successMessage", "The contact has been added.");
			return "contacts";
		} else {
			model.addAttribute("errorBadAdressMessage", "The email adress is either invalid or you already added this contact.");
			return "contacts";
		}
	}
}
