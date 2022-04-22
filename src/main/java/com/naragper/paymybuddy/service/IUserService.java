package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.naragper.paymybuddy.model.User;

public interface IUserService {

	public List<User> getUsers();

	public Optional<User> getUser(int id);

	public User postUser(@Valid User postUser);

	public User putUser(@Valid User putUser);

	public User deleteUser(@Valid User deleteUser);
	
}
