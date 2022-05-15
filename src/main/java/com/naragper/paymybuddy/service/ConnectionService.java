package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.repository.IConnectionRepository;

@Service
public class ConnectionService implements IConnectionService {

	@Autowired
	IConnectionRepository connectionRepository;

	@Autowired
	IUserService userService;

	public List<Connection> getConnections() {
		return (List<Connection>) connectionRepository.findAll();
	}

	public Optional<Connection> getConnection(int id) {
		return connectionRepository.findById(id);
	}

	public Connection postConnection(@Valid Connection postConnection) {
		// Verify that the users IDs exist
		boolean validIds = false;
		if (userService.getUser(postConnection.getUserId1()) != null
				&& userService.getUser(postConnection.getUserId2()) != null) {
				validIds = true;
		}
		// If the connection doesn't exist and user IDs are valid, create it
		if (validIds == true && connectionExists(postConnection.getUserId1(), postConnection.getUserId2()) == false) {
			// Organize user IDs by value if necessary (lesser, greater)
			if (postConnection.getUserId1() > postConnection.getUserId2()) {
				int lesserId;
				int greaterId;
				lesserId = postConnection.getUserId2();
				greaterId = postConnection.getUserId1();
				postConnection.setUserId1(lesserId);
				postConnection.setUserId2(greaterId);
			}
			return connectionRepository.save(postConnection);
		} else {
			return null;
		}
	}

	public Connection putConnection(@Valid Connection putConnection) {
		// If the connection already exists, modify it
		if (getConnection(putConnection.getId()) != null) {
			return connectionRepository.save(putConnection);
		} else {
			return null;
		}
	}

	public Connection deleteConnection(int id) {
		Optional<Connection> connection = getConnection(id);
		if (connection != null) {
			connectionRepository.deleteById(id);
			return connection.get();
		} else {
			return null;
		}
	}

	public boolean connectionExists(int userId1, int userId2) {
		int lesserId;
		int greaterId;
		if (userId1 > userId2) {
			lesserId = userId2;
			greaterId = userId1;
		} else {
			lesserId = userId1;
			greaterId = userId2;
		}
		return getConnections().stream()
				.anyMatch(connection -> connection.getUserId1() == lesserId && connection.getUserId2() == greaterId);
	}
}
