package com.naragper.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naragper.paymybuddy.model.Form;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.service.IUserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping
	public String showSignup(Model model) {
		model.addAttribute("signupForm", new Form());
		return "signup";
	}
	
	@PostMapping
	public String signup(@ModelAttribute("signupForm") Form form, Model model) {
		User user = new User();
		user.setEmail(form.getEmail());
		user.setHash(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
		User result = userService.postUser(user);
		if (result != null) {
			return "login";
		}
		return "signup";
	}
	
}
