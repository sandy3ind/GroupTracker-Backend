package com.samyuktatech.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samyuktatech.model.User;
import com.samyuktatech.util.Utility;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private AccessTokenStore accessTokenStore;

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
		
		SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
		
		Utility.consoleLog("Auth : "+authentication);
		
		// Store Access Token
		User user = new User(securityUser.getId(), securityUser.getName(), securityUser.getEmail(), securityUser.getPhone());
		
		user.setAccessToken(accessTokenStore.storeToken(user.getEmail(), authentication));
		
		// Flush to client
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();        
        mapper.writeValue(writer, user);        
        writer.flush();
		
	}
}
