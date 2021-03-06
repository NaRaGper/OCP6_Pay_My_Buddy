package com.naragper.paymybuddy.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.ITransactionRepository;
import com.naragper.paymybuddy.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;
	@Autowired
	ITransactionRepository transactionRepository;

	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User getUser(int id) {
		return userRepository.findById(id).orElse(null);
	}

	public User postUser(@Valid User postUser) {
		// If the user doesn't exist, create it
		// We cannot use it's id as it is not generated yet
		if (getUserFromEmail(postUser.getEmail()) == null) {
			return userRepository.save(postUser);
		} else {
			return null;
		}
	}

	public User putUser(@Valid User putUser) {
		// If the user exists, modify it
		if (getUser(putUser.getId()) != null) {
			return userRepository.save(putUser);
		} else {
			return null;
		}
	}
	
	public User deleteUser(int id) {
		User user = getUser(id);
		if (user != null) {
			userRepository.deleteById(id);
			return user;
		} else {
			return null;
		}
	}
	
	public User getUserFromEmail(String email) {
		User foundUser = userRepository.findByEmailIgnoreCase(email).orElse(null);
		if (foundUser != null) {
			return foundUser;
		} else {
			return null;
		}
	}
	
	public boolean verifyHash(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
