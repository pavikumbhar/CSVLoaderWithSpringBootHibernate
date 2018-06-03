package com.pravinkumbhar.service;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pravinkumbhar.metadata.entity.FirstTable;
import com.pravinkumbhar.metadata.entity.SecondTable;
import com.pravinkumbhar.util.CsvReader;
/**
 * 
 * @author pavikumbhar
 *
 */

@Component
public class MetadataLoder {
	
	 @Autowired
	 private CsvReader parser;
	
	 @Autowired
	 private Environment environment;
	 
	 @Autowired
	 private PersistanceService persistanceService;
 
	 private static final String BASE_METADATA_ENTITY_PKG="com.pravinkumbhar.metadata.entity.";
	
	/***
	 * 
	 */
	public void metadataLoder() {

		String loadAll = environment.getProperty("loadAll");

		if ("true".equals(loadAll)) {
			loadAllMetadataFile();
		} else {
			loadSpecificMetadataFile();
		}

	}
	 
	 
	 
	 /***
	  * 
	  */
	 public  void loadSpecificMetadataFile(){
			String baseDirectory=environment.getProperty("metadataBasepath");
			String metadataFiles=environment.getProperty("metadataFiles");
			File file=null;
			 int count=0;
			if(metadataFiles!=null) {
				String[] metadataFileArray = metadataFiles.split(",");
				for (int i = 0; i < metadataFileArray.length; i++) {
					StringBuilder fileName=new StringBuilder();
					String csvFileName =fileName.append(baseDirectory).append(metadataFileArray[i]).append(".csv").toString();
				    System.out.println("File Name :[" + count + "]" + csvFileName);
				    String className = metadataFileArray[i];
					String qualifiedClassname =  BASE_METADATA_ENTITY_PKG+ className;
					Class<?> clazz = getClassByQualifiedClassName(qualifiedClassname);
					if (clazz != null) {
						persistanceService.deleteFromTable(clazz);
						file=new File(csvFileName);
						parser.parseFile(file, clazz);

					}

		           	
				}
			}
		 
	 }
	 
	
	/**
	 * 
	 * @param environment
	 */
	public void loadAllMetadataFile() {
		try {

			String baseDirectory = environment.getProperty("metadataBasepath");

			File contentDirectory = new File(baseDirectory);

			int count = 0;
			File[] files = contentDirectory.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						count++;
						System.out.println("File Name :[" + count + "]" + file.getName());
						String fileName = file.getName().replace(".csv", "");
						String qualifiedClassname =BASE_METADATA_ENTITY_PKG + fileName;

						Class<?> clazz = getClassByQualifiedClassName(qualifiedClassname);
						if (clazz != null) {
							persistanceService.deleteFromTable(clazz);
							parser.parseFile(file, clazz);

						}
						
						  /* if(fileName.equals(FirstTable.class.getSimpleName())){
		            	   parser.parseFile(file,getClass("com.pravinkumbhar.metadata.entity.FirstTable"));
		            	   
		               }
		               if(fileName.equals(SecondTable.class.getSimpleName())){
		            	   parser.parseFile(file,getClass("com.pravinkumbhar.metadata.entity.SecondTable"));
		            	   
		               }if(fileName.equals(LookupMetadata.class.getSimpleName())){
		            	   parser.parseFile(file,LookupMetadata.class);
		            	   
		               }*/

					}
				}

			} else {
				System.err.println("File not found at baseDirectory " + baseDirectory);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	 
	 
	 /**
	  * 
	  * @param qualifiedClassname
	  * @return
	  */
	 public static Class<?> getClassByQualifiedClassName(String qualifiedClassName) {
			Class<?> clazz = null;
			try {
				System.err.println("qualifiedClassName is : {}   "+qualifiedClassName);
				clazz = Class.forName(qualifiedClassName);
			} catch (ClassNotFoundException cnf) {
				System.err.println("Invalid qualifiedClassName..");
				return clazz;
			}
			return clazz;		
		}
	
	/***
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getBaseName(String fileName) {
	    int index = fileName.lastIndexOf('.');
	    if (index == -1) {
	        return fileName;
	    } else {
	        return fileName.substring(0, index);
	    }
	}

}
