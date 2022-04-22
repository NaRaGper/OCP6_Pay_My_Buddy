package com.naragper.paymybuddy.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.IUserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UserRepositoryITTest {

	@Autowired
	private IUserRepository userRepository;
	private User user1;
	private User user2;
	
	@BeforeAll
	public void firstTeardown() {
		userRepository.deleteAll();
	}
	
	@BeforeEach
	public void setUp() {
		//String salt = BCrypt.gensalt();

		user1 = new User();
		user1.setEmail("user1mailtest@mail.com");
		user1.setHash("FakeHashUser1.0000000000000000000000000000000000000000000000");
		//user1.setHash(BCrypt.hashpw("password134", salt));

		user2 = new User();
		user2.setEmail("user2mailtest@mail.com");
		user2.setHash("FakeHashUser2.0000000000000000000000000000000000000000000000");
		//user2.setHash(BCrypt.hashpw("22password22", salt));
		user2.setUsername("User 2");
		user2.setBalance(2000);
	}

	@AfterEach
	public void teardown() {
		userRepository.deleteAll();
		user1 = null;
		user2 = null;
	}

	@Test
	@DisplayName("Save user to database")
	public void saveUser() {
		userRepository.save(user1);

		// Retrieve this user by ID
		User fetchedUser = userRepository.findById(user1.getId()).get();

		// Verify that the user exists
		assertNotNull(fetchedUser);
	}

	@Test
	@DisplayName("Get all existing users")
	public void getAllUsers() {
		// Save setup users to the database
		userRepository.save(user1);
		userRepository.save(user2);

		// Retrieve all existing users in a list
		List<User> users = (List<User>) userRepository.findAll();

		// Verify informations on retrieved users
		// User in index ID 0 should user1
		assertThat(users.get(0).getEmail()).isEqualTo(user1.getEmail());

		// User in index ID 1 should user2
		assertThat(users.get(1).getEmail()).isEqualTo(user2.getEmail());
	}

	@Test
	@DisplayName("Delete one user from the database")
	public void deleteUser() {
		// Save setup users to the database
		userRepository.save(user1);
		userRepository.save(user2);
		
		// Delete user1
		userRepository.delete(user1);
		
		// Retrieve all users
		List<User> users = (List<User>) userRepository.findAll();
		
		// Verify that user1 is removed but that user2 still exists
		// User in index ID 0 should be user2
		assertThat(users.get(0).getEmail()).isEqualTo(user2.getEmail());
	}
}
