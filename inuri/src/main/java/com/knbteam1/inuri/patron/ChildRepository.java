/*
 * 생성자 : 김근환 생성일 : 9.13 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Integer> {

	 Page<Child> findAll(Pageable pageable);
	 List<Child> findByChnameContaining(String keyword);
}
