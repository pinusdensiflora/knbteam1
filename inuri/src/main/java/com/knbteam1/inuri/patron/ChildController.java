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
import java.util.stream.Collectors;

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

import com.knbteam1.inuri.auth.Customer;

@Controller

public class ChildController {

    @Autowired
    private ChildService childService;

 // 특정 아동 상세 페이지
    @GetMapping("/child_detail/{chid}")
    public String childdetail(@PathVariable("chid") Integer chid, Model model) {
        Optional<Child> child = childService.getChildById(chid);

        if (child.isPresent()) {
            Child childEntity = child.get();

            // 후원 내역을 모델에 추가
            List<Donation> donations = childEntity.getDonations(); // 아동과 관련된 모든 후원 가져오기

            model.addAttribute("child", childEntity);
            model.addAttribute("donations", donations);  // 후원 정보 모델에 추가

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
            @RequestParam(value = "filter", defaultValue = "all") String filter, // 필터 파라미터 추가
            Model model) {

        // 필터에 따라 헤더 텍스트 설정
        String headerTitle;
        switch (filter) {
            case "sponsored":
                headerTitle = "후원받은 아동";
                break;
            case "unsponsored":
                headerTitle = "후원이 필요한 아동";
                break;
            default:
                headerTitle = "모든 아동";
        }

        // 필터에 따른 아동 목록 가져오기
        Page<Child> paging = childService.getFilteredChildren(page, keyword, searchType, filter);

        // 검색 및 필터 관련 정보 모델에 추가
        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("headerTitle", headerTitle); // 헤더에 표시할 제목 전달
        model.addAttribute("filter", filter); // 현재 필터 정보 전달

        return "patron/child_list";  // 목록 페이지로 이동
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
        
        return "redirect:/child_list"; // 아동 목록으로 리다이렉트
    }


    // 아동 삭제
    @GetMapping("/child/delete/{chid}")
    public String deleteChild(@PathVariable Integer id) {
        childService.deleteChildById(id);
        return "redirect:patron/child_list"; // 아동 목록으로 리다이렉트
    }
}
