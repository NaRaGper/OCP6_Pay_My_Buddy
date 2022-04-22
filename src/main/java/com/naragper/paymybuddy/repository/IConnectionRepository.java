package com.naragper.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naragper.paymybuddy.model.Connection;

@Repository
public interface IConnectionRepository extends CrudRepository<Connection, Integer> {

}
