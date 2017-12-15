package com.samyuktatech.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.UserEntity;

public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

	Long countByEmail(String email);

	UserEntity findByEmail(String email);

}
