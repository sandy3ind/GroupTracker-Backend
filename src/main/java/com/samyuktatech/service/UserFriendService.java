package com.samyuktatech.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samyuktatech.model.User;
import com.samyuktatech.mysql.entity.UserFriendEntity;
import com.samyuktatech.mysql.repository.UserEntityRepository;
import com.samyuktatech.mysql.repository.UserFriendEntityRepository;
import com.samyuktatech.util.Utility;

@RestController
@RequestMapping("/user/friend")
public class UserFriendService {
	
	@Autowired
	private UserFriendEntityRepository userFriendEntityRepository;
	
	@Autowired
	private UserEntityRepository userEntityRepository;

	
	@PostMapping("send-request/{userId}/{friendId}")
	public ResponseEntity<?> sendRequest(
			@PathVariable("userId") Long userId,
			@PathVariable("friendId") Long friendId) {
		
		// Check if already request sent
		Long isSent = userFriendEntityRepository.countByUserIdAndFriendIdAndIsRequestSent(userId, friendId, true);
		if (isSent > 0) {
			return ResponseEntity.badRequest().body("Request already sent");
		}
		
		UserFriendEntity userFriendEntity = new UserFriendEntity();
		userFriendEntity.setUserId(userId);
		userFriendEntity.setFriendId(friendId);
		userFriendEntity.setRequestSent(true);
		userFriendEntity.setRequestSentDate(new Date());
		
		userFriendEntityRepository.save(userFriendEntity);
		
		return  ResponseEntity.ok("Request sent successfully");		    	    
			
	}
	
	/**
	 * Accept Request
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@PostMapping("accept-request/{userId}/{friendId}")
	public ResponseEntity<?> acceptRequest(
			@PathVariable("userId") Long userId,
			@PathVariable("friendId") Long friendId) {
		
		// Get request sent entity
		UserFriendEntity userFriendEntity = userFriendEntityRepository.findByUserIdAndFriendIdAndIsRequestSent(userId, friendId, true);
		if (userFriendEntity == null) {
			return ResponseEntity.badRequest().body("Request has not sent");
		}
		
		// Check if request has already been accepted
		if (userFriendEntity.isRequestAccepted()) {
			return ResponseEntity.badRequest().body("Request has already beed accepted");
		}
		
		userFriendEntity.setRequestAccepted(true);
		userFriendEntity.setRequestAcceptedDate(new Date());
		
		userFriendEntityRepository.save(userFriendEntity);
		
		return  ResponseEntity.ok("Request accepted successfully");		    	    
			
	}
	
	@GetMapping("/friends/{userId}")
	public ResponseEntity<?> getFriends(@PathVariable("userId") Long userId) {
		
		List<UserFriendEntity> userFriends = userFriendEntityRepository.findByUserIdOrFriendIdAndIsRequestAccepted(
				userId, userId, true);
		
		if (!userFriends.isEmpty()) {			
			
			// Iterate and build Users list out of UserFriend list
			// If user.id == userId then request has been sent by this user
			// else sent by other user			
			List<User> users = 
					userFriends
						.stream()
							.filter(u -> u.getUserId() != null && u.getFriendId() != null)
							.map(u -> {								
								if (u.getUserId().equals(userId)) {
									return Utility.userEntityToUser(userEntityRepository.findOne(u.getFriendId()));
								}
								else {
									return Utility.userEntityToUser(userEntityRepository.findOne(u.getUserId()));
								}
							})
							.collect(Collectors.toList());
			
			return ResponseEntity.ok(users);
		}
		
		return ResponseEntity.ok(Collections.emptyList());
	}
}
