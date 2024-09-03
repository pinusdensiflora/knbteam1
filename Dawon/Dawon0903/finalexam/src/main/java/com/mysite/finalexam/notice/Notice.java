package com.mysite.finalexam.notice;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Notice {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime date;

	
	@Column(length = 200)
	private String title;

	@Column(columnDefinition = "TEXT") //썸머노트같이 많은 내용을 담으려면 이러한 처리가 필요
	private String about;
	
	private String img;
	
	private String cate; //카데고리 , 공지를 종류별로 분리 하려함
	

	
	
}
