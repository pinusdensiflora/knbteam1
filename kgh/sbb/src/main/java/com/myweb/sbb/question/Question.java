package com.myweb.sbb.question;

import java.time.LocalDate;
import java.util.List;

import com.myweb.sbb.aswer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Question {
	
	@Id //id의 대표
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //이름의 아이디
	
	@Column(length=200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDate createDate;
									  //게시물이 삭제되면 거기에 달린 답변도 자동으로 삭제한다는 뜻
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
    private List<Answer> answerList; 
		
		
}

