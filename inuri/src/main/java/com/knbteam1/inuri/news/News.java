/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nid;

	private LocalDateTime ndate;
	
	private String ntitle;
	
	@Column(columnDefinition = "TEXT")//썸머노트
	private String ndesc;
	
	private String ncate;
	
    private Integer nkind; // 게시판의 종류를 숫자로 부여하게 되면 쉽게 관리 할수 있다. 
	
	private Integer nhit;
	
	private String nimg1;
	//private String nimg2;
	
	//news는 관리자만이 생성하기 떄문에 작성자를 넣지 않음
	
	@OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE)
	private List<Img> imgs;
	
	
}
