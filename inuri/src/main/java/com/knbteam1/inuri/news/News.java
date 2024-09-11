/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nid;

	private LocalDateTime ndate;
	
	private String ntitle;
	
	private String ndesc;
	
	private String ncate;
	
	private String nsubcate;
	//notice, info, 


	//private String nimg;
	
}
