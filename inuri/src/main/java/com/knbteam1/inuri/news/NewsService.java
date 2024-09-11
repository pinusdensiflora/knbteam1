/*
 생산자: 배다원
 생산날짜: 9.10
 연락처: dawnzeze@gmail.com
 
 */
package com.knbteam1.inuri.news;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class NewsService {

	private final NewsRepository newsRepository;
	
	
	
	//작성자 기입하기
		//public void create(News news, MultipartFile file) throws IOException {
		public void create(News news){
			//s3Service의 uploadFile 메서드를 사용하기 위해 파일 이름이 필요한데, 이때 uuid 를 추가해서 쓴다.
			
//			//기본 사진이름을 uuid 처리 후 aws에 저장
//			UUID uuid = UUID.randomUUID();
//			String fileName = uuid + "_" + file.getOriginalFilename(); //"uuid_기존파일이름" 형태로 만든다 
//			s3Service.uploadFile(file, fileName); //예외처리가 요구됨
			
//			//접속자(작성자) 정보 뽑아내기
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//			String username = userDetails.getUsername();
//			Optional<Customer> oc = customerRepository.findByusername(username);
			
			
			//객체에 저장
			//news.setImg(fileName);
			news.setNdate(LocalDateTime.now());
		
			
			//접속자(작성자) 정보 저장
			//news.setAuthor(oc.get());
			
			newsRepository.save(news);
			
			//이메일 보내기
			//mailService.create("게시글이 등록되었습니다.", username + "회원님께서 " + notice.getTitle() + "의 제목으로 글을 등록하셨습니다.", username); //아이디가 이메일 주소
		}
		
		
		// readlist 카테고리별
		public List<News> readlist(String cate) {
			return newsRepository.findByNcate(cate);
		}
		
		// readdetail
		public News readdetail(Integer id) {
			Optional<News> ob = newsRepository.findById(id);
			return ob.get();
		}
		
		
		// update
		public void update(News news, MultipartFile file) {
			
//			//사진을 새로 넣지 않았을 경우
//			if (file.isEmpty()) {
//				news.setImg(news.getImg());//기존 사진 이름을 그대로 다시 사용한다. 
//			} else { //기존 사진을 그대로 쓰지 않고 새 사진을 넣은 경우, 사진 변경 처리
//				//기본 사진이름을 uuid 처리 후 aws에 저장
//				UUID uuid = UUID.randomUUID();
//				String fileName = uuid + "_" + file.getOriginalFilename();
//				s3Service.uploadFile(file, fileName);
//				news.setImg(fileName);  //새로운 uuid 붙인 사진 넣기
//			}
			
			//update시 id는 hidden 으로 함께 추가됨
			newsRepository.save(news);
		}
		
		
		// delete
		public void delete(Integer id) {
			
			newsRepository.deleteById(id);
		}
	
	
	
	
}
