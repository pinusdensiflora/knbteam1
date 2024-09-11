package com.knbteam1.inuri.auth;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	//스프링 시큐리티 로그인 아이디로 객체 찾기
		Optional<Customer> findByusername(String username);
}
