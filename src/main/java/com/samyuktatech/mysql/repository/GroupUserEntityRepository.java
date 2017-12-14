package com.samyuktatech.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.GroupUserEntity;

public interface GroupUserEntityRepository extends CrudRepository<GroupUserEntity, Long> {

	GroupUserEntity findByGroupIdAndUserIdAndIsInvitationSent(Long groupId, Long userId, boolean isInvitationSent);
	
	Long countByGroupIdAndUserIdAndIsInvitationAccepted(Long groupId, Long userId, boolean isInvitationSentAccepted);

	GroupUserEntity findByGroupIdAndUserIdAndIsInvitationAccepted(Long groupId, Long userId, boolean isInvitationSentAccepted);

}
