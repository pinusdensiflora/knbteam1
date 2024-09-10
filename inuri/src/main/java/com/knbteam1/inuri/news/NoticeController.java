package com.knbteam1.inuri.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/news")
@Controller
public class NoticeController {
	
	
	@GetMapping("")
	public String notice() {
		
		return "news/notice";
		
	}

}
