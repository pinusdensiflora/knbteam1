/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.util.Map;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/news")
@Controller
public class NewsController {
	
	private final Map<String, String> cateMap = Map.of(
			//article
			"notice", "공지사항",
			
			//info
            "info1", "기업소식",
            "info2", "후원금 사용내역",
            "info3", "진행프로젝트",
            "info4", "피후원자 추가안내",
            "info5", "명예후원자",
            "info6", "인재 채용",
            "info7", "보도자료",
            
            //assist
            "terms", "이용약관",
            "faq", "FAQ"
    );
	
	
	private final NewsService newsService;
	
	//news CREATE 
	//관리자만접근
	@GetMapping("/{cate}/create")
	public String create(Model model, @PathVariable("cate") String cate) {
		model.addAttribute("cateKey", cate);
		model.addAttribute("cateValue", cateMap.get(cate));
		
		return "news/newsCreate";
	}
	
	
	@PostMapping("/{cate}/create")
	public String create(@ModelAttribute News news) {
		newsService.create(news);
		/*faq 작성 시 admin 페이지로 이동하도록 설정 - 이강혁/0913*/
		if (news.getNcate().equals("faq")) {
			return "redirect:/admin/faq";
		}
		return "redirect:/news/" + news.getNcate(); //카테고리에 따른 리턴 차이 필요
	}
	
	
	//readdetail
	@GetMapping("/article/{id}")
	public String readdetail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("news", newsService.readdetail(id));
		model.addAttribute("cateValue", cateMap.get(newsService.readdetail(id).getNcate()));
		return "news/readdetail";
	}
	
	
	//UPDATE
	
	
	
	
	
	//DELETE
	@PostMapping("/news/{cate}/delete/{id}")
	public String delete(@PathVariable("id") Integer id,@PathVariable("cate") String cate) {
		newsService.delete(id);		
		return "redirect:/news/" + cate;
	}
	
	
	
	/*@GetMapping("/{cate}")
	public String cate(Model model, @PathVariable("cate") String cate ) {
		
		model.addAttribute("newsl", newsService.readlist(cate));
		
		if(cate.equals("notice")) {
			return "news/notice";
			
		} else if(cate.substring(0, 4).equals("info")) {
			
			return "news/info/"+cate;
		}
		return "news/assist/"+ cate;
	}
	*/
	
	@GetMapping("/{cate}")
	public String cate(Model model, @PathVariable("cate") String cate, 
			@RequestParam(value="page", defaultValue="0") int page) {
		
		Page<News> paging = newsService.readlistpage(cate, page);
	    model.addAttribute("paging", paging);
		
		
		//model.addAttribute("newsl", newsService.readlist(cate));

		if(cate.equals("notice")) {
			return "news/notice";
			
		} else if(cate.substring(0, 4).equals("info")) {
			
			return "news/info/"+cate;
		}
		return "news/assist/"+ cate;
	}
	
	
	
	//공지사항탭
	@GetMapping("")
	public String notice(Model model) {
//		model.addAttribute("newsl", newsService.readlist("notice")); //notice 카테고리
//		return "news/notice";
		return "redirect:/news/notice";
		
	}

	
	//소식탭
	@GetMapping("/info")
	public String infoMain(Model model) {
		
//		model.addAttribute("newsl", newsService.readlist("info1")); //카테고리만
//		return "news/info/info1";
		
		return "redirect:/news/info1";
		
	}
	
//	@GetMapping("/article/{cateNum}")
//	public String info(Model model, @PathVariable("cateNum") String cateNum) {
//		
//		model.addAttribute("newsl", newsService.readlist("info"+ cateNum)); //카테고리만
//		return "news/info/info"+ cateNum;
//		
//	}
	
//	@GetMapping("/article/{cate}")
//	public String info(Model model, @PathVariable("cate") String cate) {
//		//info 조건문 넣어서 전체 가능하게 변경하기
//		model.addAttribute("newsl", newsService.readlist(cate); //카테고리만
//		return "news/info/info"+ cate.charAt(4);
//		
//	}
	


	
	
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
