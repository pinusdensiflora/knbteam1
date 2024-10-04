/* 
Profile.java
생성자: 김가은
생성날짜: 9.19
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knbteam1.inuri.qna.question.QuestionService;

@Controller
public class ProfileController {

	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private QuestionService questionService;
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/profile")
	public String profile(Model model) {

		model.addAttribute("customer", customerService.authen());
		
		return "auth/profile";
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/userQuestions")
    public String userQuestions(Model model) {
    	model.addAttribute("customer", customerService.authen());
    	return "auth/userQuestions"; //1:1질문내역으로 이동
    }
    
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/detailquestion/{id}")
    public String userAnswer(@PathVariable("id") Integer id,Model model) {
    	model.addAttribute("question", questionService.readdetail(id));
    	
    	return "auth/detailquestion"; // 답변으로 이동
    }
}

