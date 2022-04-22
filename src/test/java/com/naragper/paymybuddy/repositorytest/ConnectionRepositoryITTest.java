package com.naragper.paymybuddy.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.IConnectionRepository;
import com.naragper.paymybuddy.repository.IUserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ConnectionRepositoryITTest {

	@Autowired
	private IConnectionRepository connectionRepository;
	@Autowired
	private IUserRepository userRepository;
	private Connection connection;
	private User user1;
	private User user2;

	@BeforeAll
	public void firstTeardown() {
		connectionRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@BeforeEach
	public void setUp() {
		// Create two users which will be used to make a new connection
		//String salt = BCrypt.gensalt();

		user1 = new User();
		user1.setEmail("user1mailtest@mail.com");
		user1.setHash("FakeHashUser1.0000000000000000000000000000000000000000000000");
		//user1.setHash(BCrypt.hashpw("password134", salt));

		user2 = new User();
		user2.setEmail("user2mailtest@mail.com");
		user2.setHash("FakeHashUser2.0000000000000000000000000000000000000000000000");
		//user2.setHash(BCrypt.hashpw("22password22", salt));

		// Save users to the database
		userRepository.save(user1);
		userRepository.save(user2);

		// Create new connection
		connection = new Connection();
		connection.setUserId1(user1.getId());
		connection.setUserId2(user2.getId());
	}

	@AfterEach
	public void teardown() {
		connectionRepository.deleteAll();
		userRepository.deleteAll();
		connection = null;
		user1 = null;
		user2 = null;
	}

	@Test
	@DisplayName("Save connection to database and retrieve it")
	public void saveConnectionAndRead() {
		connectionRepository.save(connection);

		// Retrieve connection by ID
		Connection fetchedConnection = connectionRepository.findById(connection.getId()).get();

		// Verify that the connection exists
		assertNotNull(fetchedConnection);
		assertThat(fetchedConnection.getUserId1()).isEqualTo(user1.getId());
		assertThat(fetchedConnection.getUserId2()).isEqualTo(user2.getId());
	}

	@Test
	@DisplayName("Delete connection")
	public void deleteConnection() {
		connectionRepository.save(connection);

		connectionRepository.delete(connection);
		
		// Verify that there is no connection
		assertThat(connectionRepository.findAll()).isEmpty();;
	}
}
