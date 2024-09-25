/*
 * 생성자 : 김근환 
 * 생성일 : 9.13 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.24
 */
package com.knbteam1.inuri.patron;

import java.io.IOException;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller

public class ChildController {

    @Autowired
    private ChildService childService;

    // 특정 아동 상세 페이지
    @GetMapping("/child_detail/{chid}")
    public String childdetail(@PathVariable("chid") Integer chid, Model model) {
        Optional<Child> child = childService.getChildById(chid);
        if (child.isPresent()) {
            model.addAttribute("child", child.get());
            return "patron/child_detail";
        } else {
            return "error/404"; // 아동이 없을 경우 404 페이지로 연결
        }
    }
    
    // 아동 목록 및 검색 처리
    @GetMapping("/child_list")
    public String list(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchType", required = false, defaultValue = "name") String searchType,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        Page<Child> paging = childService.getList(page, keyword, searchType);
        
        // 검색 관련 정보 모델에 추가
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);

        return "patron/child_list";  // 기존 페이지를 그대로 사용
    }

    @GetMapping("/add_child")
    public String addChild(Model model) {
        model.addAttribute("child", new Child());  // 빈 Child 객체를 모델에 추가
        return "patron/add_child"; 
    }

    
    // 아동 추가 (예: 폼 제출을 통해 처리할 수 있는 POST 요청)

    @PostMapping("/add_child")
    public String addChild(@ModelAttribute Child child,
                           @RequestParam("file") MultipartFile file) throws IOException {
        // 파일이 비어 있는지 확인
        if (!file.isEmpty()) {
            childService.createOrUpdateChild(child, file);
        } else {
            // 파일이 없을 경우 기본 이미지 설정
            child.setChimg("default.jpg");  // 기본 이미지 설정 (미리 준비된 기본 이미지 사용)
            childService.createOrUpdateChild(child, null);
        }
        

    @PostMapping("/child/add")
    public String addChild(@ModelAttribute Child child, @RequestParam("file")MultipartFile file, @RequestParam("location_detail") String l_detail) throws IOException {
        childService.createOrUpdateChild(child, file, l_detail);

        return "redirect:/child_list"; // 아동 목록으로 리다이렉트
    }


    // 아동 삭제
    @GetMapping("/child/delete/{chid}")
    public String deleteChild(@PathVariable Integer id) {
        childService.deleteChildById(id);
        return "redirect:patron/child_list"; // 아동 목록으로 리다이렉트
    }
}
