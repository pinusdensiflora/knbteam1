//QuestionController
//생산자: 이강혁
//생성날짜: 9.19
//연락처: rkdgur5381@gmail.com

package com.knbteam1.inuri.qna.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/{id}")
    public String readdetail(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("question", questionService.readdetail(id));
        return "admin/qa/readQA";
    }

    // 관리자용 1:1 문의 목록 확인
    @GetMapping("/admin/qalist")
    public String readQAList(Model model) {
        model.addAttribute("qalist", questionService.readList());
        return "admin/qa/readQAList";
    }
}
