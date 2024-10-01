/*
 생산자: 배다원
 생산날짜: 9.12
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.qna.answer;

import java.time.LocalDateTime;

import com.knbteam1.inuri.auth.Customer;

import com.knbteam1.inuri.qna.question.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer aid;
	private LocalDateTime adate;

	private String content;


	@ToString.Exclude // Exclude the child from toString() to prevent recursion
	@ManyToOne
	private Customer aauthor;

	@ToString.Exclude // Exclude the child from toString() to prevent recursion
	@ManyToOne
	private Question question;
}
