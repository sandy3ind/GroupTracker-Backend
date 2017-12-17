package com.samyuktatech.util;

import java.util.UUID;

import com.samyuktatech.model.User;
import com.samyuktatech.mysql.entity.UserEntity;

public class Utility {

	public static UserEntity userToUserEntity(User user) {
		return new UserEntity(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				user.getPhone());		
	}
	
	public static User userEntityToUser(UserEntity userEntity) {
		return new User(
				userEntity.getId(),
				userEntity.getName(),
				userEntity.getEmail(),
				userEntity.getPassword(),
				userEntity.getPhone());		
	}
	
	public static String getUniqueToken() {
		return UUID.randomUUID().toString();
	}
	
	public static void consoleLog(String msg) {
		System.out.println(msg);
	}
}
