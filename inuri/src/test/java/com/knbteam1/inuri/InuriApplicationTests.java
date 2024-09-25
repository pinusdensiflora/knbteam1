package com.knbteam1.inuri;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    
	
	//작동을 위한 기본적인 Board 입니다. Board가 없다면 생성하고 진행
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
    
    //테스트용 News 게시글을 생성하는 코드
    //@Test
    void makeTest() {
    	
    	for(int i = 1 ; i <= 50; i++) {
    		News n = new News();
        	n.setNdate(LocalDateTime.now());
        	n.setNkind(1); //공지사항
        	n.setNtitle("테스트데이터" + i);
        	n.setNdesc("반복생성 데이터 입니다." + i);
 
    		newsService.create(n);
    		
    	}
    	
    	for(int i = 51 ; i <= 100; i++) {
    		News n = new News();
        	n.setNdate(LocalDateTime.now());
        	n.setNkind(2); //소식
        	n.setNtitle("소식 테스트데이터" + i);
        	n.setNdesc("반복생성 데이터 입니다." + i);
 
    		newsService.create(n);
    		
    	}
    	
    }
	
	
	
}
