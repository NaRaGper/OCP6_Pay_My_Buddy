package com.naragper.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naragper.paymybuddy.model.Form;
import com.naragper.paymybuddy.service.IUserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping
	public String showLogin(Model model) {
		model.addAttribute("loginForm", new Form());
		return "login";
	}
	
	@PostMapping
	public String login(@ModelAttribute("loginForm") Form form, Model model) {
		return "login";
	}
}
