/*
 * 생산자 : 배다원
 * 생성일 : 0930
 * 연락처 : dawnzeze@gmail.com
 * */
package com.knbteam1.inuri.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class GptController {
	
	@Autowired
	private GptService gptService;

//	@GetMapping("/send")
//	public String send() {
//		return "send";
//	}
	
//	@PostMapping("/response")
//	public String respond(Model model, 
//						  @RequestParam("msg") String msg) {
//		model.addAttribute("msg", gptService.getChatResponse(msg));
//		return "gpt/response";
//	}
	
	@PostMapping("/gpt/response")
	@ResponseBody
	public String respond(@RequestParam("msg") String msg) {
		System.out.println("넘어온 값 확인 : " + msg);
		
		return gptService.getChatResponse(msg);
	}
	
}
