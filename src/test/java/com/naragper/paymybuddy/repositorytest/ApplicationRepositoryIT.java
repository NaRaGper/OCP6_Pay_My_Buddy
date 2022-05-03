package com.naragper.paymybuddy.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.model.PaymentType;
import com.naragper.paymybuddy.model.Transaction;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.IConnectionRepository;
import com.naragper.paymybuddy.repository.ITransactionRepository;
import com.naragper.paymybuddy.repository.IUserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ApplicationRepositoryIT {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ITransactionRepository transactionRepository;
	@Autowired
	private IConnectionRepository connectionRepository;

	private User user1;
	private User user2;
	private Transaction transaction1;
	private Transaction transaction2;
	private Connection connection;

	@BeforeAll
	public void firstTeardown() {
		userRepository.deleteAll();
		transactionRepository.deleteAll();
		connectionRepository.deleteAll();
	}

	@AfterAll
	void finalTeardown() {
		userRepository.deleteAll();
		transactionRepository.deleteAll();
		connectionRepository.deleteAll();
		user1 = null;
		user2 = null;
		transaction1 = null;
		transaction2 = null;
		connection = null;
	}

	@Test
	@Order(1)
	@DisplayName("Create and save two users")
	void createAndSaveUsers() {
		// Setting up User 1
		user1 = new User();
		user1.setEmail("user1mailtest@mail.com");
		user1.setHash(BCrypt.hashpw("password134", BCrypt.gensalt()));
		// Setting up User 2
		user2 = new User();
		user2.setEmail("user2mailtest@mail.com");
		user2.setHash(BCrypt.hashpw("22password22", BCrypt.gensalt()));
		user2.setUsername("User 2");
		user2.setBalance(2000);
		// Saving them
		userRepository.save(user1);
		userRepository.save(user2);
		// Retrieve users by their ID
		User fetchedUser1 = userRepository.findById(user1.getId()).get();
		User fetchedUser2 = userRepository.findById(user2.getId()).get();
		// Verify that the users exist
		assertNotNull(fetchedUser1);
		assertNotNull(fetchedUser2);
	}

	@Test
	@Order(2)
	@DisplayName("Create and save two transactions")
	void createAndSaveTransactions() {
		// Setting up Transaction 1
		transaction1 = new Transaction();
		transaction1.setType(PaymentType.USER);
		transaction1.setSenderId(user1.getId());
		transaction1.setReceiverId(user2.getId());
		transaction1.setAmount(100100);
		transaction1.setDescription("This is the first transaction, it goes from User1 to User2");
		// Setting up Transaction 2
		transaction2 = new Transaction();
		transaction1.setType(PaymentType.USER);
		transaction2.setSenderId(user2.getId());
		transaction2.setReceiverId(user1.getId());
		transaction2.setAmount(222);
		transaction2.setDescription("This is the second transaction, it goes from User2 to User1");
		// Saving them
		transactionRepository.save(transaction1);
		transactionRepository.save(transaction2);
		// Retrieving transactions by their ID
		Transaction fetchedTransaction1 = transactionRepository.findById(transaction1.getId()).get();
		Transaction fetchedTransaction2 = transactionRepository.findById(transaction2.getId()).get();
		// Verify that the transactions exist
		assertNotNull(fetchedTransaction1);
		assertNotNull(fetchedTransaction2);
	}

	@Test
	@Order(3)
	@DisplayName("Create and save a connection between User1 and User2")
	void createAndSaveConnection() {
		// Setting up the Connection
		connection = new Connection();
		connection.setUserId1(user1.getId());
		connection.setUserId2(user2.getId());
		// Saving them
		connectionRepository.save(connection);
		// Retrieving the connection by it's ID
		Connection fetchedConnection = connectionRepository.findById(connection.getId()).get();
		// Verify that it exists
		assertNotNull(fetchedConnection);
	}

	@Test
	@Order(4)
	@DisplayName("Get every existing users")
	void getAllUsers() {
		// Retrieve all existing users in a list
		List<User> users = (List<User>) userRepository.findAll();
		// Verify informations on retrieved users
		// User in index ID 0 should user1
		assertThat(users.get(0).getEmail()).isEqualTo(user1.getEmail());
		// User in index ID 1 should user2
		assertThat(users.get(1).getEmail()).isEqualTo(user2.getEmail());
	}

	@Test
	@Order(5)
	@DisplayName("Get every existing transactions")
	void getAllTransactions() {
		// Retrieve all existing transactions in a list
		List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
		// Verify informations on retrieved transactions :
		// Transaction in index ID 0 should transaction1
		assertThat(transactions.get(0).getId()).isEqualTo(transaction1.getId());
		// Transaction in index ID 1 should transaction2
		assertThat(transactions.get(1).getId()).isEqualTo(transaction2.getId());
	}

	@Test
	@Order(6)
	@DisplayName("Get every existing connections")
	void getAllConnections() {
		// Retrieve all existing connections in a list
		List<Connection> connections = (List<Connection>) connectionRepository.findAll();
		// Verify information on retrieved connections
		// Connection in index ID 0 should be the only connection created
		assertThat(connections.get(0).getId()).isEqualTo(connection.getId());
	}

}
