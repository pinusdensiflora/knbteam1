package com.example.noticeexam.notice;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	
	
	
	@GetMapping("/")
	public String list(Model model) {

		List<Notice> notices = this.noticeService.getlist(); // 몽땅 조회함

		model.addAttribute("notices", notices); // ("이름", 조회해온 것) 조회한 것을 저 이름으로 가지고 가라

		return "readlist";

	}

	
	//readdetail
	@GetMapping("/readdetail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		
		Notice notice = this.noticeService.getNotice(id);
		
		model.addAttribute("notice", notice); //(네이밍, 조회한 객체)
		
		return "readdetail";
		
	}
	
	
	
	
}
