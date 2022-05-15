package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naragper.paymybuddy.model.PaymentType;
import com.naragper.paymybuddy.model.Transaction;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.ITransactionRepository;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IUserService userService;

	public List<Transaction> getTransactions() {
		return (List<Transaction>) transactionRepository.findAll();
	}

	public Optional<Transaction> getTransaction(int id) {
		return transactionRepository.findById(id);
	}

	public Transaction postTransaction(@Valid Transaction postTransaction) {
		// Multiple similar transaction can exist
		return transactionRepository.save(postTransaction);
	}

	public Transaction putTransaction(@Valid Transaction putTransaction) {
		// If the transaction already exists, modify it
		if (getTransaction(putTransaction.getId()) != null) {
			return transactionRepository.save(putTransaction);
		} else {
			return null;
		}
	}

	public Transaction deleteTransaction(int id) {
		Optional<Transaction> transaction = getTransaction(id);
		if (transaction != null) {
			transactionRepository.deleteById(id);
			return transaction.get();
		} else {
			return null;
		}
	}

	@Transactional
	public Transaction payUser(int senderId, int receiverId, double amount, String description) {
		// Retrieve both users
		User sender = userService.getUser(senderId);
		User receiver = userService.getUser(receiverId);
		// Add fees to the amount
		double taxedAmount = addFees(amount);
		// Verify that users exist
		if (sender == null || receiver == null) {
			return null;
		// Verifying that the sender can pay
		} else if (sender.getBalance() - taxedAmount < 0) {
			return null;
		} else {
			// Modify their respective balance
			sender.setBalance(sender.getBalance() - taxedAmount);
			receiver.setBalance(receiver.getBalance() + amount);
			// Update the users
			userService.putUser(sender);
			userService.putUser(receiver);
			// Create a new transaction
			Transaction transaction = new Transaction();
			transaction.setType(PaymentType.USER);
			transaction.setSenderId(senderId);
			transaction.setReceiverId(receiverId);
			transaction.setDescription(description);
			transaction.setAmount(taxedAmount);
			transaction.setFees(taxedAmount - amount);
			postTransaction(transaction);
			return transaction;
		}
	}

	@Transactional
	public Transaction nonUserTransaction(int userId, PaymentType type, double amount, String description) {
		// Retrieve the user
		User user = userService.getUser(userId);
		// Modify the balance and create a new transaction accordingly
		Transaction transaction = new Transaction();
		transaction.setType(type);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		switch (type) {
		case TOCASH:
			if (user.getBalance() - amount < 0) {
				return null;
			} else {
			user.setBalance(user.getBalance() - amount);
			transaction.setSenderId(userId);
			transaction.setReceiverId(userId);
			}
			break;
		case FROMCASH:
			user.setBalance(user.getBalance() + amount);
			transaction.setReceiverId(userId);
			transaction.setSenderId(userId);
			break;
		default:
			break;
		}
		// Update the user
		userService.putUser(user);
		// Post the transaction
		postTransaction(transaction);
		return transaction;
	}

	public double addFees(double amount) {
		double fees = amount * (0.5 / 100);
		return amount + fees;
	}
}
