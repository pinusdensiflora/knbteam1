package com.knbteam1.inuri.news;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/news")
@Controller
public class NewsController {
	
	private final Map<String, String> cateMap = Map.of(
            "notice", "공지사항",
            "info1", "기업소식",
            "info2", "후원금 사용내역",
            "info3", "진행프로젝트",
            "info4", "피후원자 추가안내",
            "info5", "명예후원자",
            "info6", "인재 채용",
            "info7", "보도자료",
            "terms", "이용약관",
            "faq", "FAQ"
        );
	
	
	private final NewsService newsService;
	
	//news생성
	@GetMapping("/{cate}/create")
	public String create(Model model, @PathVariable("cate") String cate) {
		model.addAttribute("cateKey", cate);
		model.addAttribute("cateValue", cateMap.get(cate));
		
		return "news/newsCreate";
	}
	
	
	@PostMapping("/{cate}/create")
	public String create(@ModelAttribute News news) {
		newsService.create(news);
		return "redirect:/news/" + news.getNcate(); //카테고리에 따른 리턴 차이 필요
	}
	
	
	
	
	
	
	
	
	
	//공지사항탭
	@GetMapping({"","/notice"})
	public String notice(Model model) {
		model.addAttribute("newsl", newsService.readlist("notice")); //notice 카테고리
		return "news/notice";
		
	}

	
	//소식탭
	@GetMapping({"/info" , "/info1"})
	public String companyNews(Model model) {
		
		model.addAttribute("newsl", newsService.readlist("info1")); //카테고리만
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
