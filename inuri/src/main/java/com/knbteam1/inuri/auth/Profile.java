/* 
Profile.java
생성자: 김가은
생성날짜: 9.19
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class Profile {

@GetMapping("/profile")
	
	public String main() {
		return "auth/profile";
				}
}
