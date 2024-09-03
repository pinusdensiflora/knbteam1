package com.example.noticeexam.notice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
	
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	public List<Notice> getlist(){

		return noticeRepository.findAll();
	}
	
	public Notice getNotice(Integer id) {
		
		Optional<Notice> on = noticeRepository.findById(id);
		return on.get();
		
	}
	

}
