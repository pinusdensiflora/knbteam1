//Question Service
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com
package com.knbteam1.inuri.qna.question;

import com.knbteam1.inuri.auth.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CustomerService customerService;

    public QuestionService(QuestionRepository questionRepository, CustomerService customerService) {
        this.questionRepository = questionRepository;
        this.customerService = customerService;
    }


    public void create(Question question){
        question.setQdate(LocalDateTime.now());
        question.setQauthor(customerService.authen());
        questionRepository.save(question);
    }

    public Question readdetail(Integer id){
        return questionRepository.findById(id).get();
    }

    public List<Question> readList(){
        return questionRepository.findAll();
    }
}
