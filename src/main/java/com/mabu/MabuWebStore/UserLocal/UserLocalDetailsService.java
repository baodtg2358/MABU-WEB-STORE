package com.mabu.MabuWebStore.UserLocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mabu.MabuWebStore.entity.User;
import com.mabu.MabuWebStore.repository.UserRepository;

@Service
public class UserLocalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByEmail(username).get();
		return new UserLocalDetails(user);
	}

}
