package com.samyuktatech.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.samyuktatech.util.Utility;

public class AccessTokenStore {

	Map<String, AccessToken> tokenMap;
	
	public AccessTokenStore() {
		tokenMap = new HashMap<>();
	}
	
	public String storeToken(String username, Authentication authentication) {
		Utility.consoleLog("Storing token for usename : " + username);
		
		String token = Utility.getUniqueToken();
		
		AccessToken accessToken = new AccessToken();
		accessToken.setUsername(username);
		accessToken.setCreatedTime(new Date());
		accessToken.setValid(true);
		accessToken.setAuthentication(authentication);
		
		// Invalidate other token of this user
		tokenMap.entrySet()
			.removeIf(entry -> entry.getValue().getUsername().equals(username));
		
		tokenMap.put(token, accessToken);
		
		return token;
		
	}
	
	public Authentication getAuthentication(String accessToken) {
		
		AccessToken token = tokenMap.get(accessToken);
		
		if (token != null) {
			return token.getAuthentication();
		}
		
		return null;
	}
}
