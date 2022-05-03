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

import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.service.IConnectionService;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
	
	@Autowired
	private IConnectionService connectionService;
	
	@GetMapping("/list")
	public List<Connection> getConnections() {
		return connectionService.getConnections();
	}
	
	@GetMapping
	public ResponseEntity<Object> getConnection(@RequestParam int id) {
		Optional<Connection> result = connectionService.getConnection(id);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This connection cannot be found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> postConnection(@Valid @RequestBody Connection postConnection) {
		Connection result = connectionService.postConnection(postConnection);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("This connection already exists", HttpStatus.OK);
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> putConnection(@Valid @RequestBody Connection putConnection) {
		Connection result = connectionService.putConnection(putConnection);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This connection cannot be modified", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteConnection(@RequestParam int id) {
		Connection result = connectionService.deleteConnection(id);
		if (result != null) {
			return new ResponseEntity<>("The connection has been deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This connection cannot be deleted", HttpStatus.BAD_REQUEST);
		}
	}
}
