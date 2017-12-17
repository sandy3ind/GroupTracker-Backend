package com.samyuktatech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.samyuktatech.mysql.entity.FCMTokenEntity;
import com.samyuktatech.mysql.repository.FCMTokenEntityRepository;
import com.samyuktatech.util.HeaderRequestInterceptor;

@Component
public class FCMPushService {
	
	@Value("${FCM.url}")
	private String fcmKey;

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
		}
		
	}
}
