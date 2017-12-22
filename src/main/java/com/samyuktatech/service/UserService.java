package com.samyuktatech.service;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samyuktatech.model.User;

import com.samyuktatech.mysql.entity.UserEntity;

import com.samyuktatech.mysql.entity.FCMTokenEntity;
import com.samyuktatech.mysql.repository.FCMTokenEntityRepository;

import com.samyuktatech.mysql.repository.UserEntityRepository;
import com.samyuktatech.util.Utility;


@RestController
@RequestMapping("/user")
public class UserService {
	
	@Autowired
	private UserEntityRepository userEntityRepository;
	
	@Autowired
	private FCMTokenEntityRepository fcmTokenEntityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Autowired
	private FCMPushService fcmPushService;

	/**
	 * Save User into Mysql
	 * 
	 * @param user
	 * @param errors
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody User user, Errors errors) {
		
		if (errors.hasErrors()) {			
			// get all errors
            String msg = (errors.getAllErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(msg);
		}
		else {
			
			// Check if user already exists
			Long count = userEntityRepository.countByEmail(user.getEmail());
			
			if (count > 0) {
				return ResponseEntity.badRequest().body("User already exists");
			}
			
			// Encode password
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			// Save user 
			userEntityRepository.save(Utility.userToUserEntity(user));
			
			return  ResponseEntity.ok("User saved successfully");
		    	    
		}		
	}
	

	@GetMapping("email/{email}")
	public ResponseEntity<?> getUserByEmail(
			@RequestParam("email") String email) {
		
		fcmPushService.pushToUser(1l);
		
		UserEntity userEntity = userEntityRepository.findByEmail(email);
		
		return  ResponseEntity.ok("User saved successfully");
	}

	/**
	 * Save/Update device FCM token
	 * 
	 * @param userId
	 * @param deviceToken
	 * @return
	 */
	@PostMapping("/fcm-device-token/userId/{userId}/deviceToken/{deviceToken}")
	public ResponseEntity<?> fcmDevice(
			@PathVariable("userId") Long userId, 
			@PathVariable("deviceToken") String deviceToken) {
		
		FCMTokenEntity fcmToken = fcmTokenEntityRepository.findByUserId(userId);
		
		if (fcmToken != null) {
			fcmToken.setDeviceToken(deviceToken);
			fcmToken.setModifiedDate(new Date());			
		}
		else {
			fcmToken = new FCMTokenEntity(userId, deviceToken);
			fcmToken.setInsertedDate(new Date());
		}
		
		fcmTokenEntityRepository.save(fcmToken);
		
		return  ResponseEntity.ok().build();
		
	}
	

}
