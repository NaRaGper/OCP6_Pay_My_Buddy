package com.naragper.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naragper.paymybuddy.model.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByEmailIgnoreCase(String email);
}
