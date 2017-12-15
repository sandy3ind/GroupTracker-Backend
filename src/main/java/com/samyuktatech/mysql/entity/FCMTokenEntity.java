package com.samyuktatech.mysql.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_date")
	private Date insertedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	public FCMTokenEntity() {}	
	
	public FCMTokenEntity(Long userId, String deviceToken) {
		super();
		this.userId = userId;
		this.deviceToken = deviceToken;
	}



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
	

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
