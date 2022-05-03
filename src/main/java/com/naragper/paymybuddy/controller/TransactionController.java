package com.naragper.paymybuddy.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naragper.paymybuddy.model.Transaction;
import com.naragper.paymybuddy.service.ITransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private ITransactionService transactionService;
	
	@GetMapping("/list")
	public List<Transaction> getTransactions() {
		return transactionService.getTransactions();
	}
	
	@GetMapping
	public ResponseEntity<Object> getTransaction(@RequestParam int id) {
		Optional<Transaction> result = transactionService.getTransaction(id);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This transaction cannot be found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> postTransaction(@Valid @RequestBody Transaction postTransaction) {
		Transaction result = transactionService.postTransaction(postTransaction);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("This transaction cannot be created", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> putTransaction(@Valid @RequestBody Transaction putTransaction) {
		Transaction result = transactionService.putTransaction(putTransaction);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This transaction cannot be modified", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteTransaction(@RequestParam int id) {
		Transaction result = transactionService.deleteTransaction(id);
		if (result != null) {
			return new ResponseEntity<>("The transaction has been deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This transaction cannot be deleted", HttpStatus.BAD_REQUEST);
		}
	}
}
