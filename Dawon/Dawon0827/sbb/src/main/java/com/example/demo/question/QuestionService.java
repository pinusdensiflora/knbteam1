package com.example.demo.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getList(){
		return questionRepository.findAll();
		
	}

	
	public Question getQuestion(Integer id) {
		
		Optional<Question> question = this.questionRepository.findById(id);
		
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question을 찾을 수 없습니다..."); //추후 구현
			
		}

		
		//return question.get(); //실무에선 if 문 안걸고 그냥 리턴함
		
		
	}
	
}
