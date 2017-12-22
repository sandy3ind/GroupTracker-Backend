package com.samyuktatech.mongodb.repository;

import org.springframework.data.repository.CrudRepository;

import com.samyuktatech.mongodb.document.Location;

public interface LocationRepository extends CrudRepository<Location, String> {

}
