//QuestionController
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com

package com.knbteam1.inuri.qna.question;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;

	@GetMapping("/question/{id}")
	public String readdetail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("question", questionService.readdetail(id));
		return "admin/qa/readQA";
	}

	// 관리자용 1:1 문의 목록 확인
	@GetMapping("/admin/qalist")
	public String readQAList(Model model) {
		model.addAttribute("qalist", questionService.readList());
		return "admin/qa/readQAList";
	}

	
	
	
	
	// /news/assist/question 에서 문의를 받음, S3에 올리는 작업 아직...
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/question/create")
	public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult
			,@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2
			) throws IOException {


		if (bindingResult.hasErrors()) {

			return "news/assist/question"; // 파일 위치 확인 요함

		}

		Question q = new Question();
		q.setSubject(questionForm.getSubject());
		q.setContent(questionForm.getContent());

		if (!file1.isEmpty()) {

			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file1.getOriginalFilename();

			// 파일 업로드 (S3 등)
			// s3Service.uploadFile(file1, fileName);
			System.out.println(fileName);
			q.setImg1(fileName);

			
		}
		if (!file2.isEmpty()) {

			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file1.getOriginalFilename();

			// 파일 업로드 (S3 등)
			// s3Service.uploadFile(file1, fileName);
			System.out.println(fileName);
			q.setImg2(fileName);

			
		}

		questionService.create(q);

		return "redirect:/news/assist/questionthanks";
	}
	
	@GetMapping("/news/assist/questionthanks")
	public String thx() {
		
		return "news/assist/questionThanks";
	}

}
