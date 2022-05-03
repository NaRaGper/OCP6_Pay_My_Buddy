package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Optional<User> getUser(int id) {
		return userRepository.findById(id);
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
		Optional<User> user = getUser(id);
		if (user != null) {
			userRepository.deleteById(id);
			return user.get();
		} else {
			return null;
		}
	}
	
	public User getUserFromEmail(String email) {
		User foundUser = (User) getUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email));
		if (foundUser != null) {
			return foundUser;
		} else {
			return null;
		}
	}
}
