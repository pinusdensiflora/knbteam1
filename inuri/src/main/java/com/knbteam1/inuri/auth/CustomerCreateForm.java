package com.knbteam1.inuri.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateForm {

	@NotEmpty(message = "이름은 필수항목입니다.")
	private String name;

	@NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String username;//이메일
	
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
    
    @NotEmpty(message = "주소는 필수항목입니다.")
    private String addr;
    
    @NotEmpty(message = "우편번호는 필수항목입니다.")
    private String postcode;
    
    @NotEmpty(message = "전화번호는 필수항목입니다.")
    private String tel;

    
}
