package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
	
	@GetMapping("/sbb")
	@ResponseBody
	public String index() {
		System.out.println("index");
		return "index";
	}
	
	@GetMapping("/")
	public String root() {
		return "redirect:/question/list"; // redirect: 의 뜻) 주소창에 다음을 그대로 입력한다. (새로운 주소를 입력한다)
		
	}


}
