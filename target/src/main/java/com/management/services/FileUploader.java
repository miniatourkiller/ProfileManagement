package com.management.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploader {

	@Autowired
	ResourceLoader resourceLoader;
	Logger logger = LoggerFactory.getLogger(this.getClass());
public String uploadFile(MultipartFile file) {
	if (!file.isEmpty()) {
		String fileName = System.currentTimeMillis()+"."+this.getExtension(file.getOriginalFilename());
		System.out.println();
		try {
			Resource resource = resourceLoader.getResource("classpath:profilePics");
			File file1 = new File(resource.getFile().getPath()+"/"+fileName);
			file.transferTo(file1);
		}catch(Exception e){
			logger.warn(e.getMessage());
			return "500.err";
		}
		return fileName;

	}
return "500.err";
}

public String getExtension(String fileName) {
	String[] parts = fileName.split(".", 2);
	return parts[1];
}

public boolean deleteFile(String fileName) {
	File fileToDelete = null;
	try{
		fileToDelete= resourceLoader.getResource("classpath:profilePics/"+fileName).getFile();
	}catch(Exception e){
		logger.warn(e.getMessage());
		logger.warn("file was not deleted");
	}
	if(fileToDelete == null){
		return false;
	}else{
		return fileToDelete.delete();
	}
}
}
