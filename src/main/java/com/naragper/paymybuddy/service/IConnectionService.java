package com.naragper.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.naragper.paymybuddy.model.Connection;

public interface IConnectionService {

	public List<Connection> getConnections();

	public Optional<Connection> getConnection(int id);

	public Connection postConnection(@Valid Connection postConnection);

	public Connection putConnection(@Valid Connection putConnection);

	public Connection deleteConnection(int id);
	
	public boolean connectionExists(int userId1, int userId2);
}
