package com.samyuktatech.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.samyuktatech.model.User;
import com.samyuktatech.mysql.entity.UserEntity;

public class SecurityUser extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SecurityUser(UserEntity userEntity) {
		this.setId(userEntity.getId());
		this.setEmail(userEntity.getEmail());
		this.setName(userEntity.getName());
		this.setPhone(userEntity.getPhone());
		this.setPassword(userEntity.getPassword());
	}
	
	// Get current user.
	public static User getUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {		
		return super.getPassword();
	}

	@Override
	public String getUsername() {		
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
