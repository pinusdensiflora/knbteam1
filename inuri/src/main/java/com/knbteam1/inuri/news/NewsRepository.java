/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {

	List<News> findByNcate(String ncate);
	List<News> findByNkind(Integer nkind);
	
	Page<News> findByNcate(String ncate, Pageable pageable);
	Page<News> findByNkind(Integer nkind, Pageable pageable);
	Page<News> findAll(Pageable pageable);
	
	Page<News> findAll(Specification<News> spec, Pageable pageable);
	
}
