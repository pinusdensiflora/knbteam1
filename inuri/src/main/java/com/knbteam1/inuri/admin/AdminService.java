package com.knbteam1.inuri.admin;

import com.knbteam1.inuri.news.News;
import com.knbteam1.inuri.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final NewsRepository newsRepository;

    public List<News> readAllNews() {
        return newsRepository.findAll();
    }
}
