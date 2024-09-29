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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Integer> {

	List<News> findByNcate(String ncate);
	List<News> findByNkind(Integer nkind);
	
	Page<News> findByNcate(String ncate, Pageable pageable);
	Page<News> findByNkind(Integer nkind, Pageable pageable);
	Page<News> findAll(Pageable pageable);
	
	Page<News> findAll(Specification<News> spec, Pageable pageable);
	
	
	/*
	 * @Query("SELECT n FROM News n WHERE " +
	 * "LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', n.ndesc, '<', ''), '>', '')) LIKE LOWER(CONCAT('%', :kw, '%')) "
	 * + "OR LOWER(n.ntitle) LIKE LOWER(CONCAT('%', :kw, '%'))") Page<News>
	 * findAllByKeyword(Pageable pageable, @Param("kw") String kw);
	 */
	
}
