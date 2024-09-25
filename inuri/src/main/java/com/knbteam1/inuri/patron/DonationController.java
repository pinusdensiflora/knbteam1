/*
 * 생성자 : 김근환 
 * 생성일 : 9.15 
 * 연락처 : ghwan07@gmail.com
 * 수정일 : 9.24
 */
package com.knbteam1.inuri.patron;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ChildService childService; 
    
    @Autowired
    private CustomerService customerService;

    // 후원 페이지 렌더링 & 정보 가져오기
    @GetMapping("/donate")
    public String donate(@RequestParam("childId") Integer childId, Model model) {
        Optional<Child> child = childService.getChildById(childId);
        Customer currentCustomer = customerService.authen(); // 현재 로그인된 고객 정보 가져오기 (authen 사용)
        
        if (child.isPresent()) {
            model.addAttribute("child", child.get());
            model.addAttribute("customer", currentCustomer); // 고객 정보를 모델에 추가
            return "patron/donate"; 
        } else {
            return "error/404"; 
        }
    }
 
    // 후원 정보 저장
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createDonation(@RequestBody Map<String, Object> donationData) {
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

            // 응답 메시지
            Map<String, String> response = new HashMap<>();
            response.put("message", "후원 저장에 성공했습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 오류 처리
            Map<String, String> response = new HashMap<>();
            response.put("message", "후원 저장 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
