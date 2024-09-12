package com.knbteam1.inuri;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.knbteam1.inuri.news.News;
import com.knbteam1.inuri.news.NewsService;

@SpringBootTest
class InuriApplicationTests {

	@Test
	void contextLoads() {
	}

	
	
	@Autowired
    private NewsService newsService;

    //@Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
        	News n = new News();
        	n.setNtitle("테스트용 notice");
        	n.setNcate("notice");
        	n.setNdesc("test");
        	
            this.newsService.create(n);
        }
    }
	
	
	
}
