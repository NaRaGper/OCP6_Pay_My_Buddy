package com.naragper.paymybuddy.repositorytest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.naragper.paymybuddy.repository.ITransactionRepository;
import com.naragper.paymybuddy.repository.IUserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TransactionRepositoryITTest {
	
	@Autowired
	private ITransactionRepository transactionRepository;
	@Autowired
	private IUserRepository userRepository;
	
	@BeforeAll
	public void firstTeardown() {
		transactionRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
