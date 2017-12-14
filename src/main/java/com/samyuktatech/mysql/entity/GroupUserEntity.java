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
	
	@Column(name = "is_invitation_sent")
	private boolean isInvitationSent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invitation_sent_date")
	private Date invitationSentDate;
	
	@Column(name = "is_invitation_accepted")
	private boolean isInvitationAccepted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invitation_accepted_date")
	private Date invitationAccepted;
	
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

	public boolean isInvitationSent() {
		return isInvitationSent;
	}

	public void setInvitationSent(boolean isInvitationSent) {
		this.isInvitationSent = isInvitationSent;
	}

	public Date getInvitationSentDate() {
		return invitationSentDate;
	}

	public void setInvitationSentDate(Date invitationSentDate) {
		this.invitationSentDate = invitationSentDate;
	}

	public boolean isInvitationAccepted() {
		return isInvitationAccepted;
	}

	public void setInvitationAccepted(boolean isInvitationAccepted) {
		this.isInvitationAccepted = isInvitationAccepted;
	}

	public Date getInvitationAccepted() {
		return invitationAccepted;
	}

	public void setInvitationAccepted(Date invitationAccepted) {
		this.invitationAccepted = invitationAccepted;
	}
}
