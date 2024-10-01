package com.knbteam1.inuri.qna.question;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.qna.answer.Answer;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qid;
    private LocalDateTime qdate;


    private String subject;
    private String content;


    private Integer qauthor;
    private String qauthorName;

    private List<Answer> answers;
}
