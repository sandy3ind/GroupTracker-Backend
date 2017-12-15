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

import com.samyuktatech.util.Constants.GroupUserStatus;

@Entity
@Table(name = "group_users")
public class GroupUserEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "group_user_id")
	private Long id;
	
	@Column(name = "group_id")
	private Long groupId;
	
	@Column(name = "user_id")
	private Long userId;
	
	
	@Column(name = "status")
	private GroupUserStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;	
	
	public GroupUserStatus getStatus() {
		return status;
	}

	public void setStatus(GroupUserStatus status) {
		this.status = status;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public GroupUserEntity() {}
	
	public GroupUserEntity(Long groupId, Long userId) {
		super();
		this.groupId = groupId;
		this.userId = userId;;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
