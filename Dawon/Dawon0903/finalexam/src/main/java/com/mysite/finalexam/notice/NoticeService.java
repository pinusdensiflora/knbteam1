package com.mysite.finalexam.notice;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.finalexam.S3Service;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private S3Service s3Service;// 사진 파일을 보내기 위함
	
	// create
//	public void create(Notice Notice) {
//		Notice.setDate(LocalDateTime.now());
//		
//		NoticeRepository.save(Notice);
//	}
	
	
	public void create(Notice notice, MultipartFile file) throws IOException {
		//s3Service의 uploadFile 메서드를 사용하기 위해 파일 이름이 필요한데, 이때 uuid 를 추가해서 쓴다.
		
		//기본 사진이름을 uuid 처리 후 aws에 저장
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename(); //"uuid_기존파일이름" 형태로 만든다 
		s3Service.uploadFile(file, fileName); //예외처리가 요구됨
		
		//객체에 저장
		notice.setImg(fileName);
		notice.setDate(LocalDateTime.now());
		
		noticeRepository.save(notice);
	}
	
	
	// readlist
	public List<Notice> readlist() {
		return noticeRepository.findAll();
	}
	
	// readdetail
	public Notice readdetail(Integer id) {
		Optional<Notice> ob = noticeRepository.findById(id);
		return ob.get();
	}
	
//	// update 오류코드
//	public void update(Notice notice, MultipartFile file) throws IOException {
//		
//		if(file.isEmpty()) {//사진 변경안함
//			notice.setImg(notice.getImg());//기존 사진을 그대로 가져옴
//		}else {//기존 사진을 그대로 쓰지 않고 새로운 사진을 넣은 경우
//			
//			//create 에서 붙여넣음
//			UUID uuid = UUID.randomUUID();
//			String fileName = uuid + "_" + file.getOriginalFilename(); //"uuid_기존파일이름" 형태로 만든다 
//			s3Service.uploadFile(file, fileName); //예외처리가 요구됨
//			
//			notice.setImg(fileName);
//		}
//		
//		//create도 save를 사용한다. 중요!! update은 id가 hidden으로 함께 와 있다.
//		noticeRepository.save(notice);
//	}
	
	
	// update
	public void update(Notice notice, MultipartFile file) throws IOException {
		//사진을 새로 넣지 않았을 경우
		if (file.isEmpty()) {
			notice.setImg(notice.getImg());//기존 사진 이름을 그대로 다시 사용한다. 
		} else { //기존 사진을 그대로 쓰지 않고 새 사진을 넣은 경우, 사진 변경 처리
			//기본 사진이름을 uuid 처리 후 aws에 저장
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			s3Service.uploadFile(file, fileName);
			notice.setImg(fileName);  //새로운 uuid 붙인 사진 넣기
		}
		//create도 save를 사용한다. 중요!!update 은 id가 hidden 으로 함께 와 있다. 
		noticeRepository.save(notice);
	}
	
	
	// delete
	public void delete(Integer id) {
		noticeRepository.deleteById(id);
	}
	
}
