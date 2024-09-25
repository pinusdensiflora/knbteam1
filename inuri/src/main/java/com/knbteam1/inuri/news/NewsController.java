/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.knbteam1.inuri.admin.Board;
import com.knbteam1.inuri.admin.BoardService;
import com.knbteam1.inuri.qna.question.QuestionForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/news")
@Controller
public class NewsController {

	private final NewsService newsService;
	private final BoardService boardService;
	
	@Value("${cloud.aws.s3.endpoint}")
    private String s3Endpoint;
	
	
	//news CREATE ==================================================================================
	//관리자만접근
	

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@GetMapping("/{kind}/create") // board를 이용한 create로 추후 변경하기
	public String create(NewsForm newsForm, Model model, @PathVariable("kind") Integer kind){
	
		
		Board b = boardService.findBoard(kind);
		
		model.addAttribute("cateKey", kind);
		model.addAttribute("cateValue", b.getBname());
		model.addAttribute("boards", boardService.readlist());
		
		
		return "news/newsCreate";
	  
	 }
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@PostMapping("/{kind}/create")
		public String create(@Valid NewsForm newsForm, BindingResult bindingResult, 
							 Model model, @PathVariable("kind") Integer kind, 
							 @RequestParam("files") MultipartFile[] files//파일처리
							) throws IOException {
		
		
		

		if (bindingResult.hasErrors()) {
			
			Board b = boardService.findBoard(kind);
			
			model.addAttribute("cateKey", kind);
			model.addAttribute("cateValue", b.getBname());
			model.addAttribute("boards", boardService.readlist());
			
			return "news/newsCreate";
			
			
		}
		
	
		
		News news = new News();
		news.setNkind(newsForm.getNkind());
		news.setNtitle(newsForm.getNtitle());
		news.setNdesc(newsForm.getNdesc());
		//newsService.create(news);
		
		
		
		// 파일이 존재하는 경우에만 처리
	    //if (!files.isEmpty()) {
	    if (files != null && files.length > 0) {
	        newsService.create(news, files);  // 파일 처리
	    } else {
	        newsService.create(news);  // 파일 없이 생성
	    }
		
		
		
		//newsService.create(news, file);
		
		return "redirect:/news/" + news.getNkind(); //카테고리에 따른 리턴 차이 필요
	}
	
	
	
	
	//멀티
	@GetMapping("/article/{id}")
	public String readdetail(Model model, @PathVariable("id") Integer id) {
		newsService.hit(id);
		
		News n = newsService.readdetail(id);
		
		model.addAttribute("news", n);
		model.addAttribute("cateValue", boardService.getBname(n.getNkind()));
		model.addAttribute("endpoint",s3Endpoint);
		return "news/readdetail";
	}
	
	
	//UPDATE==================================================================================
	
	//멀티
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@GetMapping("/update/{id}")
	public String update(NewsForm newsForm, Model model, @PathVariable("id") Integer id){
		News news = newsService.readdetail(id);
		model.addAttribute("cateValue", boardService.getBname(news.getNkind()));
		model.addAttribute("news", news);
		model.addAttribute("boards", boardService.readlist());
		model.addAttribute("endpoint",s3Endpoint);
	    
	    // 기존 데이터 설정
	    newsForm.setNid(news.getNid());
	    newsForm.setNtitle(news.getNtitle());  // 제목 설정
	    newsForm.setNkind(news.getNkind());
	    newsForm.setNdesc(news.getNdesc());
		
		
		
		return "news/newsUpdate";
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@PostMapping("/update/{id}")
	public String update(@Valid NewsForm newsForm, BindingResult bindingResult,
						 Model model, @PathVariable("id") Integer id,
						 @RequestParam(value = "files", required = false) MultipartFile[] newFiles,
                         @RequestParam(value = "imgIdsToDelete", required = false) List<Integer> imgIdsToDelete) throws IOException {

		
		if (bindingResult.hasErrors()) {
			
	
			News news = newsService.readdetail(id);
			model.addAttribute("cateValue", boardService.getBname(news.getNkind()));
			model.addAttribute("news", news);
			model.addAttribute("boards", boardService.readlist());
			model.addAttribute("endpoint",s3Endpoint);
			
			
			
			//return "news/newsUpdate";
			return "news/newsUpdate";
		}
		
		//newsService.create(news);
		News news = newsService.readdetail(newsForm.getNid());
		//news.setNcate(newsForm.getNcate());
		news.setNkind(newsForm.getNkind());
		news.setNtitle(newsForm.getNtitle());
		news.setNdesc(newsForm.getNdesc());
		
		newsService.updateNews(news, newFiles, imgIdsToDelete); // 뉴스 업데이트
		
		return "redirect:/news/article/"+news.getNid();
	}
	
	
	
	//DELETE==================================================================================

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@GetMapping("/{kind}/delete/{id}")
	public String delete(@PathVariable("id") Integer id, 
			@PathVariable("kind") String cate) {
		newsService.delete(id);	
		return "redirect:/news/" + cate;
	}
	
	

	//매핑==================================================================================

	//board에 생성한 공지사항, bid와 html명이 일치하여야 동작, bcate: 1=공지사항 2=기업소식 3=고객지원
	@GetMapping("/{bid}")
	public String cate(Model model, @PathVariable("bid") Integer bid, 
			@RequestParam(value="page", defaultValue="0") int page) {
		
		Integer bcate = boardService.getBcate(bid);
		
		Page<News> paging = newsService.readlistpage(bid, page);
	    model.addAttribute("paging", paging);
	    model.addAttribute("cateboards", boardService.findListBcate(bcate));
	    model.addAttribute("bid", bid);
	    
	    List<News> allList = newsService.readlist(bid);
		int len = allList.size();
	    model.addAttribute("listsize", len);
		
	    if(bcate.equals(1)) {
	    	return "news/bid"+bid;
	    	
	    }
	    else if(bcate.equals(2)) {
	    	return "news/info/bid"+ bid;
	    	
	    }
	    else if(bcate.equals(3)){
	    	return "news/assist/bid" + bid;
	    }
	    else {
	    	return "redirect:/news/1";
	    }
				
		
	}
	
	
	
	//공지사항탭 (기본 news 페이지)
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
		return "redirect:/news/4";
	}
	
	//1대1문의
	@GetMapping("/assist/question")
	public String inquiry(QuestionForm questionForm) {
		
		return "news/assist/question";
	}
	
	
	
	//찾아오시는 길
	@GetMapping("/address")
	public String address() {
		return "news/address";
		
	}
	
	
	//검색결과
	@GetMapping("/search")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<News> paging = newsService.keywordlist(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "news/search";
    }
	


}
