package com.pravinkumbhar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Conditional;

import com.pravinkumbhar.condition.FileDirectoryCondition;
import com.pravinkumbhar.service.MetadataLoder;

@SpringBootApplication
@Conditional(FileDirectoryCondition.class)
public class SpringMetadataApplication implements CommandLineRunner {
	
	@Autowired
	private MetadataLoder metadataLoder;

    public static void main(String[] args) throws Exception {

        SpringApplication.run(SpringMetadataApplication.class, args);
		
    }

  
    @Override
    public void run(String... args) throws Exception {
    	metadataLoder.metadataLoder();
    }
}