package com.knbteam1.inuri.news;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/news")
@Controller
public class NewsController {
	
	
	private final NewsService newsService;
	
	//news생성
	@GetMapping("/create")
	public String create() {
		
		return "news/newsCreate";
	}
	
	
	@PostMapping("/create")
	public String create(@ModelAttribute News news) {
		
		return "redirect:/news/"; //카테고리에 따른 리턴 차이 필요
	}
	
	
	//공지사항탭
	@GetMapping("")
	public String notice(Model model) {
		model.addAttribute("newsl", newsService.readlist("notice")); //notice 카테고리
		return "news/notice";
		
	}
	
	//소식탭
	@GetMapping("/info")
	public String companyNews(Model model) {
		
		model.addAttribute("newsl", newsService.readlist("info")); //카테고리만
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
