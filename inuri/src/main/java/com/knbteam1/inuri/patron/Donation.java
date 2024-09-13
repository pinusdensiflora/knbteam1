package com.knbteam1.inuri.patron;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/*
 * 생성자 : 김근환 생성일 : 9.12 연락처 : ghwan07@gmail.com
 */
@Data
@Entity
public class Donation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer did;

	private LocalDateTime ddate; //후원날짜
	
	private String donationMethod; //후원 방법
	
	private Integer donationAmount; //후원 금액
	
	private Integer donationPeriod; // 정기 후원 기간
	
	
	@ManyToOne
    private Child child; // 후원 아동 정보

	/*
	 * @ManyToOne private Customer customer; // 후원자 정보
	 */
}
