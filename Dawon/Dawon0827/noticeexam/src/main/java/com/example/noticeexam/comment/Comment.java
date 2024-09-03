package com.example.noticeexam.comment;

import java.time.LocalDateTime;

import com.example.noticeexam.notice.Notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime date;
	
	
	@Column(length = 200)
	private String content;
	
	
	@ManyToOne
	Notice notice;
}
