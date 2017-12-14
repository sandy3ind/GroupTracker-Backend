package com.samyuktatech.mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mysql.entity.GroupEntity;

public interface GroupEntityRepository extends CrudRepository<GroupEntity, Long> {

	Long countByName(String name);

}
