package com.myweb.sbb.aswer; 

import java.time.LocalDateTime;

import com.myweb.sbb.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data; 

@Data
@Entity
public class Answer { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(columnDefinition = "TEXT") 
    private String content; 

    private LocalDateTime createDate; 

    @ManyToOne //항상 자신이 앞이다. 대부분이 ManyToOne 하나의 게시글에 여러 댓글 N:1관계 
    private Question question; 
}