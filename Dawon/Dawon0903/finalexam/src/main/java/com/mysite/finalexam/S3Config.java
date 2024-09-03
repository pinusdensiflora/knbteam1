package com.mysite.finalexam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {


    @Value("AKIAQKPILT7S7F5Z2ROJ")//임포트시 롬복이 아니라 springframework
    private String awsAccessKey;//버킷생성시 엑세스키

    @Value("6Ble6+G2eqHnJBm5SrOiXNsrvuIJgSTZ49dgVEFv")
    private String awsSecretKey;//버킷생성시 보안키

    @Value("ap-northeast-2")
    private String region;//한국
	
    @Bean
    public AmazonS3 s3client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
    
	
}
