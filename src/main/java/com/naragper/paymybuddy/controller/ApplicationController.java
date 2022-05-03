package com.naragper.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {
	
//	@Autowired
//	private IUserService userService;
//	@Autowired
//	private ITransactionService transactionService;
//	@Autowired
//	private IConnectionService connectionService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/transfer")
	public String transfer() {
		return "transfer";
	}
	
	// Définir les endpoints
	
	//@RequestMapping("/profile")
	//@RequestMapping("/contacts")
	//@RequestMapping("/signup")
	//@RequestMapping("/logoff")
}
