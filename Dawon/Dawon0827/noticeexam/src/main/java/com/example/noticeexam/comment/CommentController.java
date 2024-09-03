package com.example.noticeexam.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.noticeexam.notice.Notice;
import com.example.noticeexam.notice.NoticeService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NoticeService noticeService;
	
	
	
	@PostMapping("/comment/create/{id}") //form 에서 로드한 주소
	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam("content") String content) {
		
		Notice notice = this.noticeService.getNotice(id); //어디 소속의 댓글인지 알아야하는데 그러려면 상위 Notice 객체를 알아야함
		
		this.commentService.create(notice, content); //소속과 내용을 보낸다.
		
		return "redirect:/readdetail/" + id ; //GetMapping 중인 어떠한 페이지로...
		
		
	}
	
	
	
}
