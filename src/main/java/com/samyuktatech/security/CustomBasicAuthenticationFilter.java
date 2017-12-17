package com.samyuktatech.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import com.samyuktatech.util.Utility;

public class CustomBasicAuthenticationFilter extends GenericFilterBean {	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationFilter.class);
	
	@Autowired
	private AccessTokenStore accessTokenStore;
	
	private AuthenticationManager authenticationManager;
	private AuthFailureHandler authFailureHandler;
	
	public CustomBasicAuthenticationFilter() {}
	
	public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager, AuthFailureHandler authFailureHandler) {		
		this.authenticationManager = authenticationManager;
		this.authFailureHandler = authFailureHandler;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		Utility.consoleLog("In CustomBasicAuthenticationFilter doFilter");
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String header = request.getHeader("Access-Token");
		
		try {
			// Check if user is Authenticated
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			
			if (authentication == null) {
				throw new UsernameNotFoundException("Authentcation not found");
			}
			
			// Check if use has valid Access Token
			if (header == null || !accessTokenStore.isValidAccessToken(header)) {
				throw new AuthenticationCredentialsNotFoundException("InValid Access Token");
			}
			
		}
		catch (AuthenticationException ex) {
			Utility.consoleLog("Auth failed");
			authFailureHandler.onAuthenticationFailure(request, response, ex);
			return;
		}		
		
		chain.doFilter(request, response);
		
	}

}
