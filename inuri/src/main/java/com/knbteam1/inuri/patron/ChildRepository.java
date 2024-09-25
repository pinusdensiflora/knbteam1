/*
 * 생성자 : 김근환 
 * 생성일 : 9.13 
 * 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Integer> {
	 //페이징
	 Page<Child> findAll(Pageable pageable);
	 
	 //검색
	 Page<Child> findAll(Specification<Child> spec, Pageable pageable);
}
