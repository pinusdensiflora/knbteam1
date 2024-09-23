package com.knbteam1.inuri;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.knbteam1.inuri.admin.Board;
import com.knbteam1.inuri.admin.BoardRepository;
import com.knbteam1.inuri.admin.BoardService;
import com.knbteam1.inuri.news.News;
import com.knbteam1.inuri.news.NewsService;

@SpringBootTest
class InuriApplicationTests {


	
	
	@Autowired
    private NewsService newsService;
	@Autowired
	private BoardRepository boardRepository;

    //@Test
//    void testJpa() {
//        for (int i = 1; i <= 300; i++) {
//        	News n = new News();
//        	n.setNtitle("테스트용 notice");
//        	n.setNcate("notice");
//        	n.setNdesc("test");
//        	
//            //this.newsService.create(n);
//        }
//    }
    
    //@Test
    void makeBoard() {
    
    	Board notice = new Board();
    	notice.setBcate(1);
    	notice.setBname("공지사항");
    	
   
    	Board info1 = new Board();
    	info1.setBcate(2);
    	info1.setBname("기업소식");
    	
    	
    	Board info2 = new Board();
    	info2.setBcate(2);
    	info2.setBname("후원금 사용내역");
    	
    	Board faq = new Board();
    	faq.setBcate(3);
    	faq.setBname("자주묻는 질문");
    	
    	boardRepository.save(notice);
    	boardRepository.save(info1);
    	boardRepository.save(info2);
    	boardRepository.save(faq);

    	

    }
	
	
	
}
