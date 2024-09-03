package com.example.demo.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content) {
		Answer answer = new Answer();
		answer.setContent(content); //받은거
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question); //받은거
		
		this.answerRepository.save(answer);
		
	}
	
	
}
