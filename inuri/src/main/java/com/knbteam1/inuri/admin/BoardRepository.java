package com.knbteam1.inuri.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knbteam1.inuri.news.News;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findByBcate(Integer bcate);
}
