//Answer Controller
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com

package com.knbteam1.inuri.qna.answer;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerService;
import com.knbteam1.inuri.qna.question.Question;
import com.knbteam1.inuri.qna.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, Answer answer) {
        answerService.create(answer, id);
        return String.format("redirect:/question/%s#answer_%s", id, answer.getAid());
    }
}
