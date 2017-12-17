package com.samyuktatech.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.samyuktatech.util.Utility;

public class AccessTokenStore {

	Map<String, AccessToken> tokenMap;
	
	public AccessTokenStore() {
		tokenMap = new HashMap<>();
	}
	
	public String storeToken(String username) {
		Utility.consoleLog("Storing token for usename : " + username);
		
		String token = Utility.getUniqueToken();
		
		AccessToken accessToken = new AccessToken();
		accessToken.setUsername(username);
		accessToken.setCreatedTime(new Date());
		accessToken.setValid(true);
		
		// Invalidate other token of this user
		tokenMap.entrySet()
			.removeIf(entry -> entry.getValue().getUsername().equals(username));
		
		tokenMap.put(token, accessToken);
		
		return token;
		
	}
	
	public boolean isValidAccessToken(String accessToken) {
		Utility.consoleLog("Validating Token : " + accessToken);
		
		AccessToken token = tokenMap.get(accessToken);
		
		if (token != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
