package com.samyuktatech.service;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samyuktatech.model.User;
import com.samyuktatech.mysql.repository.UserEntityRepository;
import com.samyuktatech.util.Utility;

@RestController
@RequestMapping("/user")
public class UserService {
	
	@Autowired
	private UserEntityRepository userEntityRepository;

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
			
			// Save user 
			userEntityRepository.save(Utility.userToUserEntity(user));
			
			return  ResponseEntity.ok("User saved successfully");
		    	    
		}		
	}
	
	@PostMapping()
	public ResponseEntity<?> fcmDevice(
			@PathVariable("userId") Long userId, 
			@PathVariable("deviceToken") Long deviceToken) {
		
		return  ResponseEntity.ok("FCM token saved successfully");
		
	}
	
}
