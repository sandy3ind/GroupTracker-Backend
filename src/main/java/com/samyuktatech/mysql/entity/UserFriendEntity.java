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
@Table(name = "user_friends")
public class UserFriendEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_friend_id")
    private Long id;	
	
	@Column(name = "user_id")
	private Long userId;
		
	@Column(name = "friend_id")
	private Long friendId;
	
	@Column(name = "is_request_sent")
	private boolean isRequestSent;
	
	@Column(name = "is_request_accepted")
	private boolean isRequestAccepted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_sent_date")
	private Date requestSentDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_sent_accepted")
	private Date requestAcceptedDate;

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

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public boolean isRequestSent() {
		return isRequestSent;
	}

	public void setRequestSent(boolean isRequestSent) {
		this.isRequestSent = isRequestSent;
	}

	public boolean isRequestAccepted() {
		return isRequestAccepted;
	}

	public void setRequestAccepted(boolean isRequestAccepted) {
		this.isRequestAccepted = isRequestAccepted;
	}

	public Date getRequestSentDate() {
		return requestSentDate;
	}

	public void setRequestSentDate(Date requestSentDate) {
		this.requestSentDate = requestSentDate;
	}

	public Date getRequestAcceptedDate() {
		return requestAcceptedDate;
	}

	public void setRequestAcceptedDate(Date requestAcceptedDate) {
		this.requestAcceptedDate = requestAcceptedDate;
	}
	
	
	
}
