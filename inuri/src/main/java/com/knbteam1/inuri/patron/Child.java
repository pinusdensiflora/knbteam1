/*
 * 생성자 : 김근환 생성일 : 9.12 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chid;
    
    private LocalDateTime chdate; // 생성날짜

    private LocalDate chdob; // 생년월일

    private String chname; // 이름

    private String chimg; // 이미지

    private String chgender; // 성별

    private String chlocation; // 거주지
    
    private String chdesc; // 아동 설명

    @ToString.Exclude // Exclude the child from toString() to prevent recursion
    @OneToMany(mappedBy = "child")
    private List<Donation> donations; // 후원 내역
}
