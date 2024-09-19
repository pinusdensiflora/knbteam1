/*
 * 생성자 : 김근환 생성일 : 9.13 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.knbteam1.inuri.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildRepository childRepository;
    private final S3Service s3Service;

    // 아동 저장 또는 업데이트
    public Child createOrUpdateChild(Child child, MultipartFile file, String l_detail) throws IOException {
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + child.getChname();
        s3Service.uploadFile(file, filename);

        child.setChimg(filename);
        child.setChdate(LocalDateTime.now());
        child.setChlocation(child.getChlocation()+" "+l_detail);

        return childRepository.save(child);
    }

    // ID로 아동 조회
    public Optional<Child> getChildById(Integer id) {
        return childRepository.findById(id);
    }

    // 모든 아동 조회
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    // 키워드로 아동 검색
    public List<Child> searchChildren(String keyword) {
        return childRepository.findByChnameContaining(keyword);
    }

    // ID로 아동 삭제
    public void deleteChildById(Integer id) {
        childRepository.deleteById(id);
    }
    
    //페이징
    public Page<Child> getList(int page) {
    	//최신순
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("chdate"));
    	
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        return this.childRepository.findAll(pageable);
    }
}
