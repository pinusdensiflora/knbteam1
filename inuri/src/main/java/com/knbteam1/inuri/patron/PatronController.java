package com.knbteam1.inuri.patron;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatronController {
	
	
	/*
	 * 생성자 : 김근환 생성일 : 9.11 연락처 : ghwan07@gmail.com
	 */
	
	@GetMapping("/donate")
	public String donate() {
		return "patron/donate";
	}
	
	@GetMapping("/patron_list")
	public String patronlist() {
		return "patron/patron_list";
	}
	
	@GetMapping("/patron_detail")
	public String patrondetail() {
		return "patron/patron_detail";
	}
	
}
