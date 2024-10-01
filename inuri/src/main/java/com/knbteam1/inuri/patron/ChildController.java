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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerService;

@Controller
public class ChildController {

    @Autowired
    private ChildService childService;
    
    @Autowired
	private MailService mailService;
    
    @Autowired
	private CustomerService customerService;
    
    @Autowired
	private DonationService donationService;

    // 편지 작성 페이지
    @GetMapping("/child_letter/{chid}")
    public String childLetter(@PathVariable("chid") Integer chid, Model model, Authentication authentication) {
        // 현재 로그인된 후원자 정보 가져오기
        Customer currentCustomer = customerService.authen();

        // 후원자가 해당 아동을 후원했는지 확인
        Optional<Donation> donationOpt = donationService.findByChild_ChidAndCustomer_Cid(chid, currentCustomer.getCid());
        
        if (donationOpt.isPresent()) {
            // 후원 기록이 있으면 편지 작성 페이지로 이동
            Optional<Child> childOpt = childService.getChildById(chid);
            if (childOpt.isPresent()) {
                model.addAttribute("child", childOpt.get());
                return "patron/child_letter"; // 편지 작성 페이지 반환
            } else {
                return "error/404";  // 아동이 존재하지 않는 경우
            }
        } else {
            return "error/no_permission";  // 후원자가 아닌 경우
        }
    }

    // 편지 전송 메서드
    @PostMapping("/send_letter")
    public String sendLetter(
            @RequestParam("childId") Integer childId,
            @RequestParam("message") String message,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            Authentication authentication) {

        Optional<Child> childOpt = childService.getChildById(childId);
        if (childOpt.isPresent()) {
            Child child = childOpt.get();
            String childEmail = child.getChemail(); // 아동 이메일

            if (childEmail == null || childEmail.isEmpty()) {
                return "error/no_email"; // 이메일 주소가 없을 경우 처리
            }

            try {
                String imagePath = null;
                if(imageUrl != null && !imageUrl.isEmpty()) {
                    imagePath = imageUrl;
                }
                mailService.create(childEmail, "후원자님의 편지", message, imagePath);
            } catch (Exception e) {
                return "error/mail_error"; // 메일 전송 실패 처리
            }

            return "redirect:/child_detail/" + childId; // 편지 전송 후 상세 페이지로 리다이렉트
        } else {
            return "error/404"; // 아동이 없을 경우 404 페이지로 연결
        }
    }
    
 // 특정 아동 상세 페이지
    @GetMapping("/child_detail/{chid}")
    public String childdetail(@PathVariable("chid") Integer chid, Model model, Authentication authentication) {
        Optional<Child> child = childService.getChildById(chid);

        if (child.isPresent()) {
            Child childEntity = child.get();

            // 후원 내역을 모델에 추가
            List<Donation> donations = childEntity.getDonations(); // 아동과 관련된 모든 후원 가져오기

            model.addAttribute("child", childEntity);
            model.addAttribute("donations", donations);  // 후원 정보 모델에 추가
            
            // 현재 로그인된 후원자 정보 가져오기
            if (authentication != null && authentication.isAuthenticated()) {
                Customer currentCustomer = customerService.authen();
                boolean hasDonated = donationService.findByChild_ChidAndCustomer_Cid(chid, currentCustomer.getCid()).isPresent();
                model.addAttribute("hasDonated", hasDonated); // 후원 여부를 모델에 추가
            } else {
                model.addAttribute("hasDonated", false); // 로그인하지 않은 경우 후원 여부는 false
            }

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

    // 아동 추가
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
    public String deleteChild(@PathVariable Integer chid) {
        childService.deleteChildById(chid);
        return "redirect:/child_list"; // 아동 목록으로 리다이렉트
    }
}
