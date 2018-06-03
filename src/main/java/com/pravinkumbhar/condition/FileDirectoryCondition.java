package com.pravinkumbhar.condition;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class FileDirectoryCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context,AnnotatedTypeMetadata annotatedTypeMetadata) {
		String baseDirectory = context.getEnvironment().getProperty("metadataBasepath");

		if (StringUtils.isEmpty(baseDirectory)) {
			System.err.println("key not found with name [metadataBasepath] or value is empty baseDirectory : "+ baseDirectory);
			System.err.println("Please  mention key as [metadataBasepath]  and value  as [Directory where your csv files are present]  in proerties file ");
							
			return false;
		}

		File contentDirectory = new File(baseDirectory);
		File[] files = contentDirectory.listFiles();

		if (files != null) {
			return true;
		} else {
			System.err.println("File not found at baseDirectory or Directory not found with name : "+ baseDirectory);
			return false;
		}
	}

}
