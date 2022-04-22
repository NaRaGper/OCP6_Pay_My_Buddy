package com.naragper.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naragper.paymybuddy.model.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

}
