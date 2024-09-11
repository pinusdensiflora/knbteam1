package com.knbteam1.inuri.news;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {

	List<News> findByNcate(String ncate);
	
}
