package com.samyuktatech.mysql.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.UserEntity;
import com.samyuktatech.mysql.entity.UserFriendEntity;

public interface UserFriendEntityRepository extends CrudRepository<UserFriendEntity, Long> {

	Long countByUserIdAndFriendIdAndIsRequestSent(Long userId, Long friendId, boolean isRequestSent);

	Long countByUserIdAndFriendIdAndIsRequestAccepted(Long userId, Long friendId, boolean isRequestAccepted);
	
	UserFriendEntity findByUserIdAndFriendIdAndIsRequestSent(Long userId, Long friendId, boolean isRequestSent);
	
	UserFriendEntity findByUserIdAndFriendId(Long userId, Long friendId);
	
	UserFriendEntity findByUserIdAndFriendIdAndIsRequestAccepted(Long userId, Long friendId, boolean isRequestAccepted);

	List<UserFriendEntity> findByUserIdOrFriendIdAndIsRequestAccepted(Long userId, Long friendId, boolean isRequestAccepted);

}
