/*
 * 생산자: 배다원
 * 생산일: 9/13
 * 연락처 : dawnzeze@gmail.com
 * 
 * */
package com.knbteam1.inuri.admin;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bid;//자동 증가되는 번호

	private Integer bcate; //공지=1, info=2, 고객지원=3
	private String bname;  // 게시판 마다의 이름
	
	
	/*
	 * 
	 * 1, "공지사항",
	 * 2, "기업소식",
	 * 3, "후원금 사용내역", 
	 * 4, "진행프로젝트",
	 * 5, "피후원자 추가안내",
	 * 6, "명예후원자",
	 * 7, "인재 채용",
	 * 8, "보도자료",
	 * 
	 * 지금은 예시로 번호마다 항목의 이름을 적어 두었으나 게시판을 새롭게 생성 즉 등록하면서 아이디 값이 자동으로 부여될 것이다.
	 */
}
