package com.naragper.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}
	
	// Définir les endpoints
	
	//@RequestMapping("/") -- index / home
	//@RequestMapping("/profile")
	//@RequestMapping("/transfer")
	//@RequestMapping("/contacts")
	//@RequestMapping("/signup")
	//@RequestMapping("/logoff")
}
