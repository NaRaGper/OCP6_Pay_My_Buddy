package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.naragper.paymybuddy.model.PaymentType;
import com.naragper.paymybuddy.model.Transaction;

public interface ITransactionService {

	public List<Transaction> getTransactions();

	public Optional<Transaction> getTransaction(int id);

	public Transaction postTransaction(@Valid Transaction postTransaction);

	public Transaction putTransaction(@Valid Transaction putTransaction);

	public Transaction deleteTransaction(int id);
	
	public Transaction payUser(int senderId, int receiverId, double amount, String description);
	
	public Transaction nonUserTransaction(int userId, PaymentType type, double amount, String description);
	
	public double addFees(double amount);
}
