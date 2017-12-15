package com.samyuktatech.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.FCMTokenEntity;
import com.samyuktatech.mysql.entity.GroupEntity;

public interface FCMTokenEntityRepository extends CrudRepository<FCMTokenEntity, Long> {

	FCMTokenEntity findByUserId(Long userId);

}
