/* 
MemberInform.java
생성자: 김가은
생성날짜: 9.11
연락처: kkydu007@naver.com
 */


package com.knbteam1.inuri.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberInform {
	@GetMapping("/memberInform")
	
	public String main() {
		return "auth/memberInform";
	}

}






