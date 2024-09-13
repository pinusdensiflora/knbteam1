/*
 생산자: 배다원
 생산날짜: 9.13
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewsForm {

	@NotEmpty(message = "제목은 필수항목입니다.")
	@Size(max = 200)
	private String ntitle;

	private String ndesc;
	
	@NotEmpty(message = "카테고리를 설정해주세요")
	private String ncate;
}
