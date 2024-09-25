/*
 * 생성자 : 김근환
 *  생성일 : 9.13 
 *  연락처 : ghwan07@gmail.com
 *  수정일 : 9.24
 */
package com.knbteam1.inuri.patron;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ChildService {
	
	@Autowired
	private S3Service s3Service;
    @Autowired
    private ChildRepository childRepository;

    // 아동 저장 또는 업데이트
    public void createOrUpdateChild(Child child, MultipartFile file) throws IOException {
    
    	//기본 사진이름을 uuid 처리 후 aws에 저장
    			UUID uuid = UUID.randomUUID();
    			String fileName = uuid + "_" + file.getOriginalFilename();
    			s3Service.uploadFile(file, fileName);
    			
    			//객체에 저장
    			child.setChimg(fileName);
    			child.setChdate(LocalDateTime.now());
    			
    			childRepository.save(child);
    }
    // ID로 아동 조회
    public Optional<Child> getChildById(Integer id) {
        return childRepository.findById(id);
    }

    // 모든 아동 조회
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }
    

 // 검색 로직을 처리하는 Specification 메서드
    private Specification<Child> search(String keyword, String searchType) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Child> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복 제거

                switch (searchType) {
                    case "name":
                        return cb.like(root.get("chname"), "%" + keyword + "%");
                    case "location":
                        return cb.like(root.get("chlocation"), "%" + keyword + "%");
                    case "age":
                        try {
                            // 입력된 나이를 Integer로 변환
                            int age = Integer.parseInt(keyword);
                            
                            // 나이로부터 생년월일 범위 계산
                            LocalDate today = LocalDate.now();
                            LocalDate startDob = today.minusYears(age + 1).plusDays(1); // 해당 나이의 시작 생년월일
                            LocalDate endDob = today.minusYears(age); // 해당 나이의 마지막 생년월일
                            
                            // 해당 생년월일 범위에 속하는 아동 검색
                            return cb.between(root.get("chdob"), startDob, endDob);
                        } catch (NumberFormatException e) {
                            return cb.disjunction();  // 나이가 잘못된 경우 빈 검색 결과 반환
                        }
                    default:
                        return cb.conjunction();  // 조건 없을 때 빈 결과 반환
                }
            }
        };
    }


    // 검색 조건에 따른 아동 목록을 반환하는 메서드
    public Page<Child> getList(int page, String keyword, String searchType) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc("chdate")));
        Specification<Child> spec = search(keyword, searchType);
        return childRepository.findAll(spec, pageable);
    }

    // ID로 아동 삭제
    public void deleteChildById(Integer id) {
        childRepository.deleteById(id);
    }
}

