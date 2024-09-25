/* 
Profile.java
생성자: 김가은
생성날짜: 9.19
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/profile")
	public String profile(Model model) {

		model.addAttribute("customer", customerService.authen());
		
		return "auth/profile";
	}
	
	
    @GetMapping("/userQuestions")
    public String userQuestions(Model model) {
    	model.addAttribute("customer", customerService.authen());
    	return "auth/userQuestions"; //1:1질문내역으로 이동
    }
}
