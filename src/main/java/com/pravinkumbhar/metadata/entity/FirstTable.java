package com.pravinkumbhar.metadata.entity;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@javax.persistence.Entity
public class FirstTable extends Metadata {
	
	@Column(name = "NAME")
	private String name;
	
	
}
