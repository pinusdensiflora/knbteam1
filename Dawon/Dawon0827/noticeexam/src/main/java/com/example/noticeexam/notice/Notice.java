package com.example.noticeexam.notice;

import java.time.LocalDateTime;
import java.util.List;

import com.example.noticeexam.comment.Comment;

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
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime date;
	
	
	@Column(length = 100)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	
	@OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE)
	List<Comment> comments ;

	
}
