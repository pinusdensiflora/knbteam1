//Answer Service
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com
package com.knbteam1.inuri.qna.answer;

import com.knbteam1.inuri.auth.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final CustomerService customerService;

    public AnswerService(AnswerRepository answerRepository, CustomerService customerService) {
        this.answerRepository = answerRepository;
        this.customerService = customerService;
    }

    public void create(Answer answer) {
        answer.setAdate(LocalDateTime.now());
        answer.setAauthor(customerService.authen());
        answerRepository.save(answer);
    }

    public Answer readDetail(Integer id){
        return answerRepository.findById(id).get();
    }

    public List<Answer> readList(){
        return answerRepository.findAll();
    }
}
