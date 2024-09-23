package com.knbteam1.inuri.news;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImgService {
	private final ImgRepository imgRepository;
	
	public void create(String filename, News news) {
		Img img = new Img();
		img.setIdate(LocalDateTime.now());
		img.setIlink(filename);
		img.setImgNews(news);
		imgRepository.save(img);
	}
	
	public void delete(Integer id) {
		imgRepository.deleteById(id);
	}
	
}
