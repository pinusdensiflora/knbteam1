/*
 * 생성자 : 김근환 
 * 생성일 : 9.15 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.26
 */
package com.knbteam1.inuri.patron;


import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerService;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;
    
    @Autowired
    private DonationRepository donationRepository;
    
    @Autowired
    private ChildService childService; 
    
    @Autowired
    private CustomerService customerService;

    // 후원 페이지 렌더링 & 정보 가져오기
    @GetMapping("/donate")
    public String donate(@RequestParam("childId") Integer childId, Model model) {
        Optional<Child> child = childService.getChildById(childId);
        
        // 현재 로그인된 고객 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 로그인 여부를 모델에 추가
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (child.isPresent()) {
            model.addAttribute("child", child.get());
            return "patron/donate"; 
        } else {
            return "error/404"; 
        }
    }
 
 // 후원 정보 저장
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createDonation(@RequestBody Map<String, Object> donationData) {
        try {
            // 클라이언트로부터 받은 데이터 처리
            String method = (String) donationData.get("method");
            Integer amount = Integer.parseInt(donationData.get("amount").toString());
            Integer period = Integer.parseInt(donationData.get("period").toString());
            Integer childId = Integer.parseInt(donationData.get("childId").toString());

            // 현재 로그인된 고객 정보 가져오기 (authen 사용)
            Customer customer = customerService.authen();

            // 후원 아동 정보 조회
            Child child = childService.getChildById(childId)
                                      .orElseThrow(() -> new RuntimeException("아동을 찾을 수 없습니다."));

            // 후원 정보 생성 및 저장
            Donation donation = donationService.createDonation(method, amount, period, child, customer);

            // 후원 저장에 성공했을 경우 리다이렉트 URL을 포함한 응답 반환
            String redirectUrl = "/donation/donateThanks";  // 후원 성공 시 리다이렉트할 URL
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", redirectUrl);  // 클라이언트에서 사용할 리다이렉트 URL

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 오류 처리
            Map<String, String> response = new HashMap<>();
            response.put("message", "후원 저장 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
 // 후원 감사 페이지 렌더링
    @GetMapping("/donateThanks")
    public String donateThanks(Model model) {
        // 필요한 모델 속성 추가
        return "patron/donateThanks"; // 후원 감사 페이지의 템플릿 이름
    }
  
    @GetMapping("/donorDonations")
    @ResponseBody
    public ResponseEntity<?> getDonorDonations(@RequestParam("donorId") Integer donorId,
                                                @RequestParam("childId") Integer childId) {
        try {
            List<Donation> donations = donationRepository.findByChild_ChidAndCustomer_Cid(childId, donorId);
            
            List<Map<String, Object>> donationDetails = donations.stream()
                .map(donation -> {
                    Map<String, Object> donationData = new HashMap<>();
                    donationData.put("amount", donation.getDonationAmount());
                    donationData.put("date", donation.getDdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    donationData.put("method", donation.getDonationMethod());
                    return donationData;
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(donationDetails);
            
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "후원 내역 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

