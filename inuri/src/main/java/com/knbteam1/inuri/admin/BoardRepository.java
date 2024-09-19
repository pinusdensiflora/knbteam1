/*
 * 생산자: 배다원
 * 생산일: 9/13
 * 연락처 : dawnzeze@gmail.com
 * */
package com.knbteam1.inuri.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knbteam1.inuri.news.News;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findByBcate(Integer bcate);
}
