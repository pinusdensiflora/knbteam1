package com.knbteam1.inuri.qna.answer;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.qna.question.Question;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AnswerDTO {
    private Integer aid;
    private LocalDateTime adate;
    private String content;
    private Integer aauthorId;
    private String aauthorName;
    private Integer questionId;
}
