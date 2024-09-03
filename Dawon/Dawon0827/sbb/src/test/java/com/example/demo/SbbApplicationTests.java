package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;

@SpringBootTest
class SbbApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	void readdetail() {
		Optional<Question> oq = this.questionRepository.findById(1); //객체 하나 꺼낼 때는 Optional 로 받는 것이 좋다.
		//Optional 이 있으면 그제서야 Question 객체 q 를 만들어서 넣어준다.
		//update와 readdetail 이 Optional을 쓴다.
		//객체를 바로 뽑아낼 수가 없기 때문에 Optional을 사용하는 것이다.
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());//성공하면 일치하는 것
		}
		
	}
	
	
	//readlist
	@Test
	void readlist() {
	
		List<Question> all = this.questionRepository.findAll();
        assertEquals(8, all.size()); // testJpa 4번 실행해서 2*4 = 8개 됨... 
        

	}
	
	
	//@Test //이게 붙어야 JUnit에서 실행됨
	void testJpa() {
		//질문1
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q1); //db에 저장
		
		
		
		//질문2
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		
		this.questionRepository.save(q2); //db에 저장
		
		//서비스, 컨트롤러 없이 단위 테스트가 가능
	}
	
}
