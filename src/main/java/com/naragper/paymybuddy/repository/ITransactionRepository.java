package com.naragper.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naragper.paymybuddy.model.Transaction;

@Repository
public interface ITransactionRepository extends CrudRepository<Transaction, Integer> {

}
