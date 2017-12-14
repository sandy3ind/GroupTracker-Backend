package com.samyuktatech.service;

import java.util.Date;
import java.util.List;
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

import com.samyuktatech.model.Group;
import com.samyuktatech.model.User;
import com.samyuktatech.mysql.entity.GroupEntity;
import com.samyuktatech.mysql.entity.GroupUserEntity;
import com.samyuktatech.mysql.entity.UserEntity;
import com.samyuktatech.mysql.repository.GroupEntityRepository;
import com.samyuktatech.mysql.repository.GroupUserEntityRepository;
import com.samyuktatech.mysql.repository.UserEntityRepository;
import com.samyuktatech.util.Utility;

@RestController
@RequestMapping("/group")
public class GroupService {
	
	@Autowired
	private GroupEntityRepository groupEntityRepository;
	
	@Autowired
	private GroupUserEntityRepository groupUserEntityRepository;

	/**
	 * Save Group and sent invitation to each user
	 * 
	 * @param group
	 * @param errors
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody Group group, Errors errors) {
		
		if (errors.hasErrors()) {			
			// get all errors
            String msg = (errors.getAllErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(msg);
		}
		else {
			
			// Check if group already exists
			Long count = groupEntityRepository.countByName(group.getName());
			
			if (count > 0) {
				return ResponseEntity.badRequest().body("Group already exists");
			}
			
			// Save group 
			GroupEntity groupEntity = new GroupEntity(group.getId(), group.getName(), new Date(), 
					new UserEntity(group.getCreatedById()));					
			
			groupEntity = groupEntityRepository.save(groupEntity);
			
			// Save group invitation to each user			
			for (Long id : group.getUserIds()) {
				GroupUserEntity groupUser = new GroupUserEntity(groupEntity.getId(), id);
				groupUser.setInvitationSent(true);
				groupUser.setInvitationSentDate(new Date());
				groupUserEntityRepository.save(groupUser);
			}
						
			return  ResponseEntity.ok("Group saved successfully");
		    	    
		}			
	}
	
	/**
	 * Accept/Reject Group Invitation
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public ResponseEntity<?> accept_rejectInvitation(
			@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId,
			@PathVariable("isAccepted") boolean isAccepted) {
		
		// Check if invitation was sent to this user
		GroupUserEntity groupUser = groupUserEntityRepository.findByGroupIdAndUserIdAndIsInvitationSent(
				groupId, userId, true);
		if (groupUser == null) {
			ResponseEntity.badRequest().body("Invitation was not sent to this user");
		}
		
		// Check if Invitation was already accepted by this user
		Long count = groupUserEntityRepository.countByGroupIdAndUserIdAndIsInvitationAccepted(
				groupId, userId, true);
		if (count > 0) {
			ResponseEntity.badRequest().body("Invitation was already accepted by this user");
		}
		// Set accepted/reject and save into database
		
		
		return  ResponseEntity.ok("Group saved successfully");
	}
}
