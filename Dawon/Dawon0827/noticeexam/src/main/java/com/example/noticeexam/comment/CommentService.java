package com.example.noticeexam.comment;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.noticeexam.notice.Notice;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public void create(Notice notice, String content) {
		
		Comment c = new Comment();
		
		c.setNotice(notice);
		c.setContent(content);
		
		c.setDate(LocalDateTime.now());
		
		commentRepository.save(c);

		
	}
	
}
