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

	@NotEmpty(message = "내용은 필수항목입니다.")
	private String ndesc;
	
	@NotEmpty(message = "카테고리는 필수항목입니다.")
	private String ncate;
}
