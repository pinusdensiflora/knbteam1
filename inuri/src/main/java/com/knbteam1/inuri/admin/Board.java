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
	 * 기본 보드 4개 만들고 진행해주세요 ㅜㅜㅜ 죄송
	 * bid까지 일치해야합니다 ㅠ ㅠ ㅠ ㅠ ㅠ ㅠㅠ
	 * 
	 * ========================
	 * bid	bcate	bname
	 * 1	1		공지사항
	 * 2	2		기업소식
	 * 3	2		후원금사용내역
	 * 4	3		자주묻는질문
	 * ========================
	 * */

}
