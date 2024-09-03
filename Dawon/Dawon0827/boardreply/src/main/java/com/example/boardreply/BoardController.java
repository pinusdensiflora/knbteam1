package com.example.boardreply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/readlist";
	}
	
	@GetMapping("/readlist")
	public String readlist(Model model) {
		model.addAttribute("boards", boardService.readlist());
		return "readlist";
	}
	
	@GetMapping("/readdetail/{id}")
	public String readdetail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("board", boardService.readdetail(id));
		return "readdetail";
	}
	
}