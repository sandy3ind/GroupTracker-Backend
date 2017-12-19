package com.samyuktatech.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.GroupUserEntity;
import com.samyuktatech.util.Constants.GroupUserStatus;

public interface GroupUserEntityRepository extends CrudRepository<GroupUserEntity, Long> {

	GroupUserEntity findByGroupIdAndUserIdAndStatus(Long groupId, Long userId, GroupUserStatus status);
	
	GroupUserEntity findByGroupIdAndUserId(Long groupId, Long userId);
	
	Long countByGroupIdAndUserIdAndStatus(Long groupId, Long userId, GroupUserStatus status);

}
