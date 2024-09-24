/*
 * 생산자 배다원
 * 생성일 0923
 * 연락처 dawnzeze@gmail.com
 * 
 * 여러 이미지를 담기위한 엔티티
 * */

package com.knbteam1.inuri.news;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Img {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iid;

	private LocalDateTime idate;
	
	private String ilink;
	
	@ManyToOne
	private News imgNews;
}
