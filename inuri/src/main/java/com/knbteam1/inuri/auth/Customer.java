/* 
Customer.java
생성자: 김가은
생성날짜: 9.11
수정날짜: 9.20
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;
import java.util.List;


import com.knbteam1.inuri.patron.Donation;

import com.knbteam1.inuri.qna.answer.Answer;
import com.knbteam1.inuri.qna.question.Question;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;
	private LocalDateTime cdate;
	

	private String username;
	private String password;
	private boolean enabled;
	private String role;
	
	private String name;
	
	private String postcode;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Donation> donation;

	@OneToMany(mappedBy = "aauthor", cascade = CascadeType.REMOVE)
	private List<Answer> answers;
	
	@OneToMany(mappedBy = "qauthor", cascade = CascadeType.REMOVE)
	private List<Question> questions;
	
	
}
