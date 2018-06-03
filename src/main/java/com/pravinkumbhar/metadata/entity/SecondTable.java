package com.pravinkumbhar.metadata.entity;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@javax.persistence.Entity
public class SecondTable  extends Metadata{
	
	@Column(name = "NAME")
    private String name;
	
	
		
	

}
