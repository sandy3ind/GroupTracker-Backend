package com.samyuktatech.mysql.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "groups")
public class GroupEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "group_id")
    private Long id;
	
	@Column(name = "name")
	private String name;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "user_id")
	private UserEntity createdBy;
	
	public GroupEntity() {}
	
	public GroupEntity(Long id, String name, Date createdDate, UserEntity createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}
		
}
