package com.naragper.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.model.UserPrincipal;
import com.naragper.paymybuddy.repository.IUserRepository;

@Service
public class UserPrincipalService implements UserDetailsService {
	
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		
		user.orElseThrow(() -> new UsernameNotFoundException("This user doesn't exist."));
		
		return user.map(UserPrincipal::new).get();
	}
}
