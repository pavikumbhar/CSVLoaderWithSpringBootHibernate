package com.pravinkumbhar.metadata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Metadata {
	
	
	
	@Id
	//@GeneratedValue
	@Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "CREATEDUSERID")
	private String createdUserId;
	
	@Column(name = "CREATEDDATE")
	private Date createdDate;
	
	@Column(name = "LASTMODIFIEDUSERID")
	private String lastModifiedUserId;
	
	@Column(name = "LASTMODIFIEDDATE")
	private Date lastModifiedDate;
	
	
	   @PrePersist
	    protected void onCreate() {
		   createdDate = lastModifiedDate = new Date();
		   createdUserId="SRIMS";
	    }

	    @PreUpdate
	    protected void onUpdate() {
	    lastModifiedDate = new Date();
	    lastModifiedUserId="SRIMS";
	    }
		

}
