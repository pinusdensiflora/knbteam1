/* 
CustomerController.java
생성자: 김가은
생성날짜: 9.11
수정날짜: 9.20
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	
	
	
	@GetMapping("/signup")
	public String signup() {
	
		return "auth/registrationForm";
	}
	
	
	@PostMapping("/signup")
	public String signup(Customer customer) {
		customerService.create(customer);
		return "redirect:/";
	}
	
	
	@GetMapping("/signin")
	public String signin() {
		
		return "auth/loginForm";
	}
	
	
	 
	

}
