/*
 생산자: 배다원
 생산날짜: 9.12
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.qna;

import java.time.LocalDateTime;

import com.knbteam1.inuri.auth.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer aid;
	private LocalDateTime adate;
	
	@ManyToOne
	private Customer aauthor;
	
	@ManyToOne
	private Question question;
	
	
	
}
