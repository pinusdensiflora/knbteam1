package com.knbteam1.inuri.configuration;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Value("inuri-bucket-106wnz")
	private String bucketName;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	
	public void uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
		
		//사진 파일을 서버에 저장
		File file = new File(multipartFile.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(multipartFile.getBytes());
		}
		
		//aws 전송
		amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));
		file.delete();
	}
	
	
}
