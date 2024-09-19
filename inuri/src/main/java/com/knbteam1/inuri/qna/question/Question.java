/*
 생산자: 배다원
 생산날짜: 9.12
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.qna.question;

import java.time.LocalDateTime;
import java.util.List;

import com.knbteam1.inuri.auth.Customer;

import com.knbteam1.inuri.qna.answer.Answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer qid;
	private LocalDateTime qdate;

	private String subject;
	private String content;
	
	

	@ManyToOne
	private Customer qauthor;
	 
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answers;
	

}
