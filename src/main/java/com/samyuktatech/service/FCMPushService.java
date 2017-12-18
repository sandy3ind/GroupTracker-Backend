package com.samyuktatech.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.samyuktatech.mysql.entity.FCMTokenEntity;
import com.samyuktatech.mysql.repository.FCMTokenEntityRepository;
import com.samyuktatech.util.HeaderRequestInterceptor;
import com.samyuktatech.util.Utility;

@Component
public class FCMPushService {
	
	@Value("${FCM.url}")
	private String fcmUrl;

	@Value("${FCM.server.key}")
	private String fcmServerKey;

	@Autowired
	private FCMTokenEntityRepository fcmTokenEntityRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void pushToUser(Long userId) {
		
		FCMTokenEntity fcmToken = fcmTokenEntityRepository.findByUserId(userId);
		
		if (fcmToken != null && !fcmToken.getDeviceToken().isEmpty()) {
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmServerKey));
			interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
			restTemplate.setInterceptors(interceptors);
			
			try {
				JSONObject body = new JSONObject();
				body.put("to", fcmToken);
				body.put("priority", "high");
		 
				JSONObject notification = new JSONObject();
				notification.put("title", "JSA Notification");
				notification.put("body", "Happy Message!");
				
				JSONObject data = new JSONObject();
				data.put("Key-1", "JSA Data 1");
				data.put("Key-2", "JSA Data 2");
		 
				body.put("notification", notification);
				body.put("data", data);
				
				HttpEntity<String> request = new HttpEntity<>(body.toString());
				
				String fcmResponse = restTemplate.postForObject(fcmUrl, request, String.class);
				
				Utility.consoleLog("Response from FCM : " + fcmResponse);
			}
			catch (JSONException ex) {
				ex.printStackTrace();
			}
			
		}
		
	}
}
