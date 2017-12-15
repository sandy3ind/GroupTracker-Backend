package com.samyuktatech.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fcm_tokens")
public class FCMTokenEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "fcm_token_id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "device_token")
	private String deviceToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
