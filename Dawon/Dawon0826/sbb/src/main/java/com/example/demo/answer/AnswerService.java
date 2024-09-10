package com.example.demo.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.question.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;


    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
	

}
