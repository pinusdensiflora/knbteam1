package com.example.demo.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

//생성자 주입 방식으로 객체생성
@RequiredArgsConstructor
@Controller
public class QuestionController {

//	// 기존의 객체 생성 방법
//	@Autowired
//	private QuestionRepository questionRepository;

	// 생성자 주입 방식으로 객체생성
	private final QuestionService questionService;

	// @ResponseBody
	@GetMapping("/question/list")
	public String list(Model model) {
		// return "(@ResponseBody로 띄움) question list";

		List<Question> questionList = this.questionService.getList(); // 몽땅 조회함

		model.addAttribute("questionList", questionList); // ("이름", 조회해온 것) 조회한 것을 저 이름으로 가지고 가라

		return "question_list";

	}

	
	//readdetail
	@GetMapping("/question/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		
		Question question = this.questionService.getQuestion(id);
		
		model.addAttribute("question", question); //(네이밍, 조회한 객체)
		
		return "question_detail";
		
	}
	
}
