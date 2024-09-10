package com.knbteam1.inuri.patron;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatronController {
	
	//김근환
	@GetMapping("/donate")
	public String donate() {
		return "patron/donate";
	}
	
}
