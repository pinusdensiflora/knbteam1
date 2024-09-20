/* 
Donation.java
생성자: 김가은
생성날짜: 9.12
연락처: kkydu007@naver.com
 */



package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Donation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer did;
	private LocalDateTime ddate;
	private Integer dwon;
	private String dwhere;
	
	@ManyToOne
	private Customer customer;
	


}
