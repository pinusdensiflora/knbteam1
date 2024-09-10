package com.knbteam1.inuri.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/news")
@Controller
public class NoticeController {
	
	
	//news생성
	@GetMapping("/create")
	public String create() {
		
		return "news/newsCreate";
	}
	
	
	//공지사항탭
	@GetMapping("")
	public String notice() {
		
		return "news/notice";
		
	}
	
	//소식탭
	@GetMapping("/info")
	public String companyNews() {
		
		return "news/info/companyNews";
		
	}
	
	
	//고객지원탭
	@GetMapping("/assist")
	public String assist() {
		return "news/assist/FAQ";
	}
	
	@GetMapping("/assist/inquiry")
	public String inquiry() {
		return "news/assist/inquiry";
	}
	
	
	
	
	//찾아오시는 길
	@GetMapping("/address")
	public String address() {
		return "news/address";
		
	}
	
	

}
