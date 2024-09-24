//Answer Service
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com
package com.knbteam1.inuri.qna.answer;

import com.knbteam1.inuri.auth.CustomerService;
import com.knbteam1.inuri.qna.question.Question;
import com.knbteam1.inuri.qna.question.QuestionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final CustomerService customerService;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, CustomerService customerService, QuestionService questionService, QuestionService questionService1) {
        this.answerRepository = answerRepository;
        this.customerService = customerService;
        this.questionService = questionService1;
    }

    public void create(Answer answer, Integer questionId) {
        Question question = questionService.readdetail(questionId);
        answer.setAdate(LocalDateTime.now());
        answer.setQuestion(question);
//        answer.setAauthor(customerService.authen());

        question.getAnswers().add(answer);

        answerRepository.save(answer);
    }

    public Answer readDetail(Integer id){
        return answerRepository.findById(id).get();
    }

    public List<Answer> readList(){
        return answerRepository.findAll();
    }
}
