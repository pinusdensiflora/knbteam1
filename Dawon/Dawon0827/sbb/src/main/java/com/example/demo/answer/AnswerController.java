package com.example.demo.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam("content") String content) {
		
		Question question = this.questionService.getQuestion(id);
		
		this.answerService.create(question, content);
		
		//return String.format("redirect:/question/detail/%s", id);
		return "redirect:/question/detail/" + id ;
		
		
	}
	


}
