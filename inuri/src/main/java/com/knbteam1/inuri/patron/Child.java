/*
 * 생성자 : 김근환 생성일 : 9.12 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.knbteam1.inuri.auth.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chid;
    
    private LocalDateTime chdate; // 생성날짜
    private LocalDate chdob; // 생년월일 (Date of Birth, 나이를 계산할 때 사용)
    private String chname; // 이름
    private String chimg; // 이미지
    private String chgender; // 성별
    private String chlocation; // 거주지
    private String chdesc; // 아동 설명
    private String chemail; // 아동 이메일 주소

    @OneToMany(mappedBy = "child")
    private List<Donation> donations; // 후원 내역

    // 나이를 계산하는 메서드
    public int getAge() {
        return Period.between(this.chdob, LocalDate.now()).getYears();
    }

	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}
}
