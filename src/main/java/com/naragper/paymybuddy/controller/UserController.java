package com.naragper.paymybuddy.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/list")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping
	public ResponseEntity<Object> getUser(@RequestParam int id) {
		Optional<User> result = userService.getUser(id);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This user cannot be found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> postUser(@Valid @RequestBody User postUser) {
		User result = userService.postUser(postUser);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("This user cannot be created", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> putUser(@Valid @RequestBody User putUser) {
		User result = userService.putUser(putUser);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This user cannot be modified", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteUser(@RequestParam int id) {
		User result = userService.deleteUser(id);
		if (result != null) {
			return new ResponseEntity<>("The user has been deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This user cannot be deleted", HttpStatus.BAD_REQUEST);
		}
	}
}
