package com.samyuktatech.util;

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
}