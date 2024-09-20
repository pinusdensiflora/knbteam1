/* 
Customer.java
생성자: 김가은
생성날짜: 9.11
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;
import java.util.List;

import com.knbteam1.inuri.qna.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;
	private LocalDateTime cdate;
	
	//시큐리티 규격에 맞게 항상 4개는 항상 똑같이 넣어준다. 
	private String username;  // 회원 아이디, 이메일 주소로 하면 많이 편하다. 
	private String password;
	private boolean enabled;
	private String role;
	
	private String name;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Donation> donation;


}
