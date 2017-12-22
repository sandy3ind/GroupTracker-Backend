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
import com.samyuktatech.util.Constants.GroupUserStatus;
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
				groupUser.setStatus(GroupUserStatus.INVITATION_SENT);
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
	@PostMapping("/accept-reject-invitation/groupId/{groupId}/userId/{userId}/isAccepted/{isAccepted}")
	public ResponseEntity<?> accept_rejectInvitation(
			@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId,
			@PathVariable("isAccepted") boolean isAccepted) {
		
		// Check if invitation was sent to this user
		GroupUserEntity groupUser = groupUserEntityRepository.findByGroupIdAndUserIdAndStatus(groupId, userId, GroupUserStatus.INVITATION_SENT);
		if (groupUser == null) {
			ResponseEntity.badRequest().body("Invitation was not sent to this user");
		}
		
		// Check if Invitation was already accepted by this user
		Long count = groupUserEntityRepository.countByGroupIdAndUserIdAndStatus(groupId, userId, GroupUserStatus.INVITATION_ACCEPTED);
		if (count > 0) {
			return ResponseEntity.badRequest().body("Invitation was already Accepted by this user");
		}
		
		// Check if Invitation was already rejected by this user
		count = groupUserEntityRepository.countByGroupIdAndUserIdAndStatus(
				groupId, userId, GroupUserStatus.INVITATION_REJECTED);
		if (count > 0) {
			return ResponseEntity.badRequest().body("Invitation was already Rejected by this user");
		}
		
		// Set accepted/reject and save into database
		if (isAccepted) {
			groupUser.setStatus(GroupUserStatus.INVITATION_ACCEPTED);
		}
		else {
			groupUser.setStatus(GroupUserStatus.INVITATION_REJECTED);
		}
		
		groupUserEntityRepository.save(groupUser);
		
		return  ResponseEntity.ok("Success");
	}
	
	/**
	 * Get group invitation status based on userid and groupid
	 * It can be used at device to decide whether group user has accepted invitation or not
	 * and start sending location from device
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	@PostMapping("inviation-status/userId/{userId}/groupId/{groupId}")
	public ResponseEntity<?> getGroupByName(
			@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId) {
		
		GroupUserEntity groupUser = groupUserEntityRepository.findByGroupIdAndUserId(groupId, userId);
		
		return  ResponseEntity.ok(groupUser);		
	}
}
