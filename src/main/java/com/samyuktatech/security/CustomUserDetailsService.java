package com.samyuktatech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samyuktatech.mysql.entity.UserEntity;
import com.samyuktatech.mysql.repository.UserEntityRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserEntityRepository userEntityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserEntity userEntity = userEntityRepository.findByEmail(username);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		
		return new SecurityUser(userEntity);
	}

}
