/* 
CustomerController.java
생성자: 김가은
생성날짜: 9.11
수정날짜: 9.20
연락처: kkydu007@naver.com
 */

package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String signup(CustomerCreateForm customerCreateForm, Model model) {
        return "auth/registrationForm"; // 회원가입 폼으로 이동
    }

    @PostMapping("/signup")
    public String createCustomer(@Valid CustomerCreateForm customerCreateForm,
    							 BindingResult bindingResult) {
    	
    	
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            return "auth/registrationForm"; // 유효성 검사를 통과하지 못하면 다시 폼으로
        }
        if (!customerCreateForm.getPassword1().equals(customerCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "auth/registrationForm";
        }
        
       
        Customer c = new Customer();
        
        

        c.setUsername(customerCreateForm.getUsername());
        c.setName(customerCreateForm.getName());
        c.setCtel(customerCreateForm.getTel());
        c.setCaddr(customerCreateForm.getAddr() + " " + customerCreateForm.getDetailAddr());
        c.setPostcode(customerCreateForm.getPostcode());
        c.setPassword(customerCreateForm.getPassword1());
        
        

        
        customerService.create(c);
        
        return "redirect:/"; // 성공적으로 생성된 경우 리다이렉트
    }

    @GetMapping("/signin")
    public String signin() {
        return "auth/loginForm"; // 로그인 폼으로 이동
    }
    

}
