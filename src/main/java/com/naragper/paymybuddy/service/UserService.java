package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}

	public Optional<User> getUser(int id) {
		return userRepository.findById(id);
	}

	public User postUser(@Valid User postUser) {
		// TODO : implement logic
		return userRepository.save(postUser);
	}

	public User putUser(@Valid User putUser) {
		// TODO : implement logic
		return userRepository.save(putUser);
	}
	
	// param : id
	public User deleteUser(@Valid User deleteUser) {
		Optional<User> user = getUser(deleteUser.getId());
		if (user != null) {
			userRepository.delete(deleteUser);
			return user.get();
		} else {
			return null;
		}
	}

}
