package com.jingle.JingleChat.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jingle.JingleChat.Entities.CustomUserDetails;
import com.jingle.JingleChat.Entities.User;
import com.jingle.JingleChat.Entities.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = repo.findByUserName(userName);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found, Kindly Register");
		}
		return new CustomUserDetails(user);
	}

}
