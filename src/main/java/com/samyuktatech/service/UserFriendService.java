package com.samyuktatech.service;

import java.util.Date;
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
import com.samyuktatech.mysql.entity.UserFriendEntity;
import com.samyuktatech.mysql.repository.UserEntityRepository;
import com.samyuktatech.mysql.repository.UserFriendEntityRepository;
import com.samyuktatech.util.Utility;

@RestController
@RequestMapping("/user/friend")
public class UserFriendService {
	
	@Autowired
	private UserFriendEntityRepository userFriendEntityRepository;

	
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
	
	@PostMapping("accept-request/{userId}/{friendId}")
	public ResponseEntity<?> acceptRequest(
			@PathVariable("userId") Long userId,
			@PathVariable("friendId") Long friendId) {
		
		// Get request sent entity
		UserFriendEntity userFriendEntity = userFriendEntityRepository.findByUserIdAndFriendIdAndIsRequestSent(userId, friendId, true);
		if (userFriendEntity == null) {
			return ResponseEntity.badRequest().body("Request has not sent");
		}
		
		userFriendEntity.setRequestAccepted(true);
		userFriendEntity.setRequestAcceptedDate(new Date());
		
		userFriendEntityRepository.save(userFriendEntity);
		
		return  ResponseEntity.ok("Request accepted successfully");		    	    
			
	}
}
