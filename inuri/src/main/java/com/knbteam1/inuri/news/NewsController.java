/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.knbteam1.inuri.admin.Board;
import com.knbteam1.inuri.admin.BoardService;

import jakarta.validation.Valid;
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
	private final BoardService boardService;
	
	//news CREATE ==================================================================================
	//관리자만접근
	


	@GetMapping("/{kind}/create") // board를 이용한 create로 추후 변경하기
	public String create(NewsForm newsForm, Model model, @PathVariable("kind") Integer kind){
	
		
		Board b = boardService.findBoard(kind);
		
		model.addAttribute("cateKey", kind);
		model.addAttribute("cateValue", b.getBname());
		model.addAttribute("boards", boardService.readlist());
		
		
		return "news/newsCreate";
	  
	 }
	 
	@PostMapping("/{kind}/create")
	public String create(@Valid NewsForm newsForm, BindingResult bindingResult) {
		

		if (bindingResult.hasErrors()) {
			return "news/newsCreate";
		}
		
		News news = new News();
		news.setNkind(newsForm.getNkind());
		news.setNtitle(newsForm.getNtitle());
		news.setNdesc(newsForm.getNdesc());
		newsService.create(news);
		
		return "redirect:/news/" + news.getNkind(); //카테고리에 따른 리턴 차이 필요
	}
	
	
	
	
	/*	@GetMapping("/{cate}/create")


	//public String create(Model model, @PathVariable("cate") String cate) {
	public String create(NewsForm newsForm, Model model,@PathVariable("cate") String cate) {
		model.addAttribute("cateKey", cate);
		model.addAttribute("cateValue", cateMap.get(cate));
		model.addAttribute("boards", boardService.readlist());
		
		return "news/newsCreate";
	}
	
	
	@PostMapping("/{cate}/create")
	public String create(@Valid NewsForm newsForm, BindingResult bindingResult
						//@ModelAttribute @Valid News news, BindingResult bindingResult,
						) {
		if (bindingResult.hasErrors()) {
			return "news/newsCreate";
		}
		
		//newsService.create(news);
		News news = new News();
		news.setNkind(newsForm.getNcate());
		news.setNtitle(newsForm.getNtitle());
		news.setNdesc(newsForm.getNdesc());
		newsService.create(news);
		
		return "redirect:/news/" + news.getNcate(); //카테고리에 따른 리턴 차이 필요
	}
	
	*/
	
	
	
	//readdetail==================================================================================
	/*@GetMapping("/article/{id}")
	public String readdetail(Model model, @PathVariable("id") Integer id) {
		newsService.hit(id);
		model.addAttribute("news", newsService.readdetail(id));
		model.addAttribute("cateValue", cateMap.get(newsService.readdetail(id).getNcate()));
		return "news/readdetail";
	}*/
	
	//멀티
	@GetMapping("/article/{id}")
	public String readdetail(Model model, @PathVariable("id") Integer id) {
		newsService.hit(id);
		
		News n = newsService.readdetail(id);
		
		model.addAttribute("news", n);
		model.addAttribute("cateValue", boardService.getBname(n.getNkind()));

		
		return "news/readdetail";
	}
	
	
	//UPDATE==================================================================================
	
	@GetMapping("/update/{id}")
	public String update(NewsForm newsForm, Model model, @PathVariable("id") Integer id){
		News news = newsService.readdetail(id);
		model.addAttribute("cateValue", cateMap.get(news.getNcate()));
		model.addAttribute("news", news);
		
		return "news/newsUpdate";
	}

	@PostMapping("/update")
	public String update(NewsForm newsForm, BindingResult bindingResult,
						 Model model){
		
		if (bindingResult.hasErrors()) {
			return "news/newsUpdate";
		}
		
		//newsService.create(news);
		News news = newsService.readdetail(newsForm.getNid());
		//news.setNcate(newsForm.getNcate());
		news.setNkind(newsForm.getNkind());
//		news.setNkind(newsForm.getNcate());
		news.setNtitle(newsForm.getNtitle());
		news.setNdesc(newsForm.getNdesc());
		newsService.create(news);//임시
		
		return "redirect:/news/article/"+news.getNid();
	}
	
	
	
	//DELETE==================================================================================
	@GetMapping("/{cate}/delete/{id}")
	public String delete(@PathVariable("id") Integer id, 
						 @PathVariable("cate") String cate) {
		newsService.delete(id);	
		return "redirect:/news/" + cate;
	}
	
	

	//==================================================================================
	/*@GetMapping("/{cate}")
	public String cate(Model model, @PathVariable("cate") String cate, 
			@RequestParam(value="page", defaultValue="0") int page) {
		
		Page<News> paging = newsService.readlistpage(cate, page);
	    model.addAttribute("paging", paging);
	    model.addAttribute("cateboards", boardService.findbcate());
		
		//model.addAttribute("newsl", newsService.readlist(cate));

	    if(cate.equals("faq")) {
	    	System.out.println("faq 들어옴");
	    	return "news/assist/faq";
	    	
	    }else if(cate.equals("notice")) {
			return "news/notice";
			
		}else if(cate.substring(0, 4).equals("info")) {
			
			return "news/info/"+cate;
		}
		return "redirect:/news";
		
		
				
		
	}*/
	
	@GetMapping("/{bid}")
	public String cate(Model model, @PathVariable("bid") Integer bid, 
			@RequestParam(value="page", defaultValue="0") int page) {
		
		Page<News> paging = newsService.readlistpage(bid, page);
	    model.addAttribute("paging", paging);
	    model.addAttribute("cateboards", boardService.findbcate());
		
	    if(bid.equals(1)) {
	    	return "news/notice";
	    	
	    }
	    else if(bid.equals(2)) {
	    	return "news/info/info1";
	    	
	    }
	    else {
	    	return "news/assist/faq";
	    }
		
				
		
	}
	
	
	
	
	
	
	//공지사항탭
	@GetMapping("")
	public String notice(Model model) {
		
		return "redirect:/news/1";
		
	}

	
	//소식탭
	@GetMapping("/info")
	public String infoMain(Model model) {
		
		return "redirect:/news/2";
		
	}
	

	
	//고객지원탭
	@GetMapping("/assist")
	public String assist() {
		return "redirect:/news/faq";
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
