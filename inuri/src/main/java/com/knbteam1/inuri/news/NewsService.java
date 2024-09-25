/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knbteam1.inuri.configuration.S3Service;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class NewsService {

	private final NewsRepository newsRepository;
	private final S3Service s3Service;
	private final ImgService imgService;
	private final ImgRepository imgRepository;
	
	
		public void create(News news){ //사진없음
			
			news.setNdate(LocalDateTime.now());
			news.setNhit(0);
			
			newsRepository.save(news);
		}

		public void create(News news, MultipartFile[] files) throws IOException {

			
//			//접속자(작성자) 정보 뽑아내기
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//			String username = userDetails.getUsername();
//			Optional<Customer> oc = customerRepository.findByusername(username);
			
			
			//객체에 저장
			news.setNdate(LocalDateTime.now());
			news.setNhit(0);

			
			//접속자(작성자) 정보 저장
			//news.setAuthor(oc.get());
			
			//newsRepository.save(news);
			
			//이메일 보내기
			//mailService.create("게시글이 등록되었습니다.", username + "회원님께서 " + notice.getTitle() + "의 제목으로 글을 등록하셨습니다.", username); //아이디가 이메일 주소
		
			
			//재호출 때문에 이렇게 저장
			News savedNews = newsRepository.save(news);

			
			for (MultipartFile file : files) {
		        if (!file.isEmpty()) {
		            // UUID 생성 및 파일명 설정
		            UUID uuid = UUID.randomUUID();
		            String fileName = uuid + "_" + file.getOriginalFilename();
		            
		            // 파일 업로드 (S3 등)
		            s3Service.uploadFile(file, fileName);
		            
		            imgService.create(fileName, savedNews);
		            System.out.println(fileName);
		        }
		    }
		
		}
		
		
		// readlist 카테고리별 cate -> Integer Nkind 로 수정
		public List<News> readlist(Integer kind) {
			return newsRepository.findByNkind(kind);
		}
		
		public List<News> readlist(String cate) {
			return newsRepository.findByNcate(cate);
		}
		
/*		// readlist 페이징
		public Page<News> readlistpage(String cate, int page) {
			Pageable pageable = PageRequest.of(page, 10);
			
			return newsRepository.findByNcate(cate, pageable);
			//return newsRepository.findAll(pageable);
			
			//return newsRepository.findByNcate(cate);
		}*/
		
		// readlist 페이징, 멀티 게시판
		public Page<News> readlistpage(Integer kind, int page) {
			
			List<Sort.Order> sorts = new ArrayList<>();
	        sorts.add(Sort.Order.desc("ndate"));
			
			
			Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
			
			return newsRepository.findByNkind(kind, pageable);
		}
		
		//검색결과 페이징
		public Page<News> keywordlist(int page, String kw){
	        List<Sort.Order> sorts = new ArrayList<>();
	        sorts.add(Sort.Order.desc("ndate"));
	        
	        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
	        Specification<News> spec = search(kw);
	        
			return newsRepository.findAll(spec, pageable);
		}
	
		
		
		
		// readdetail
		public News readdetail(Integer id) {
			Optional<News> ob = newsRepository.findById(id);
			return ob.get();
		}
		
		
		//update
		public void updateNews(News news, MultipartFile[] newFiles, List<Integer> imgIdsToDelete) throws IOException {
		    
			// 기존 뉴스 정보 업데이트
		    Optional<News> on = newsRepository.findById(news.getNid());                                   
		    News existingNews = on.get();
		    existingNews.setNtitle(news.getNtitle());
		    existingNews.setNdesc(news.getNdesc());
		    existingNews.setNkind(news.getNkind());
		    
		    
		 // 삭제할 이미지 ID 리스트 처리
		    if (imgIdsToDelete != null) {
		        for (Integer imgId : imgIdsToDelete) {
		            imgRepository.deleteById(imgId);
		        }
		    }
		    
		    // 수정된 뉴스 저장
		    News savedNews = newsRepository.save(existingNews);
		    
		    
		 

		    // 새로운 이미지 추가
		    for (MultipartFile file : newFiles) {
		        if (!file.isEmpty()) {
		            UUID uuid = UUID.randomUUID();
		            String fileName = uuid + "_" + file.getOriginalFilename();
		            s3Service.uploadFile(file, fileName);

//		            Img img = new Img();
//		            img.setIlink(fileName);
//		            img.setIdate(LocalDateTime.now());
//		            img.setImgNews(existingNews);
		            System.out.println(existingNews.getNid() + "    " + fileName);
		            
		            imgService.create(fileName, savedNews);
		            
		            
		            
		        }
		    }
		    
		    
		    
		    
		    
		}
		
		
		// delete
		public void delete(Integer id) {
			
			newsRepository.deleteById(id);
		}
	
	
		public void hit(Integer id) {
			Optional<News> ob = newsRepository.findById(id);
			Integer hit = ob.get().getNhit() + 1;
			ob.get().setNhit(hit);
			newsRepository.save(ob.get());
		}
	
		
		
		
		
		 private Specification<News> search(String kw) {
		        return new Specification<>() {
		            private static final long serialVersionUID = 1L;
		            @Override
		            public Predicate toPredicate(Root<News> n, CriteriaQuery<?> query, CriteriaBuilder cb) {
		                query.distinct(true);  // 중복을 제거 
		                return cb.or(cb.like(n.get("ntitle"), "%" + kw + "%"), // 제목 
		                        cb.like(n.get("ndesc"), "%" + kw + "%"));      // 내용 
		                        
		            }
		        };
		    }
	
}
