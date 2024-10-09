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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/signup")
    public String signup(@ModelAttribute CustomerCreateForm customerCreateForm,
                         Model model,
                         @RequestParam(name = "username", required = false) String username,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "ctel", required = false) String ctel
    ) {
        // 전달된 파라미터가 있으면 폼 객체에 설정
        if (username != null && !username.isEmpty()) {
            customerCreateForm.setUsername(username);
        }
        if (name != null && !name.isEmpty()) {
            customerCreateForm.setName(name);
        }
        if (ctel != null && !ctel.isEmpty()) {
            customerCreateForm.setTel(ctel);
        }

        // 소셜 로그인 여부 판단
        boolean isSocialSignup = (username != null && !username.isEmpty());
        model.addAttribute("isSocialSignup", isSocialSignup);
        model.addAttribute("customerCreateForm", customerCreateForm);

        return "auth/registrationForm"; // 회원가입 폼으로 이동
    }

    @PostMapping("/signup")
    public String createCustomer(@ModelAttribute("customercreateForm") @Valid CustomerCreateForm customerCreateForm,
                                 @RequestParam("isSocialSignup") boolean isSocialSignup,
                                 BindingResult bindingResult) {


        // 유효성 검사
        if (bindingResult.hasErrors()) {
            return "auth/registrationForm"; // 유효성 검사를 통과하지 못하면 다시 폼으로
        }
//        if (!customerCreateForm.getPassword1().equals(customerCreateForm.getPassword2())) {
//            bindingResult.rejectValue("password2", "passwordInCorrect",
//                    "2개의 패스워드가 일치하지 않습니다.");
//            return "auth/registrationForm";
//        }

        // 소셜 로그인 시 비밀번호 필드 유효성 검증을 생략
        if (!isSocialSignup) {
            // 비밀번호1 검증
            if (customerCreateForm.getPassword1() == null || customerCreateForm.getPassword1().isEmpty()) {
                bindingResult.rejectValue("password1", "password.empty", "비밀번호는 필수항목입니다.");
            } else if (customerCreateForm.getPassword1().length() < 8) {
                bindingResult.rejectValue("password1", "password.short", "비밀번호는 최소 8자 이상이어야 합니다.");
            }

            // 비밀번호2 검증
            if (customerCreateForm.getPassword2() == null || customerCreateForm.getPassword2().isEmpty()) {
                bindingResult.rejectValue("password2", "passwordConfirm.empty", "비밀번호 확인은 필수항목입니다.");
            } else if (!customerCreateForm.getPassword1().equals(customerCreateForm.getPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            }
        }


        Customer c = isSocialSignup? customerService.authen() :new Customer();


        c.setUsername(customerCreateForm.getUsername());
        c.setName(customerCreateForm.getName());
        c.setCtel(customerCreateForm.getTel());
        c.setCaddr(customerCreateForm.getAddr() + " " + customerCreateForm.getDetailAddr());
        c.setPostcode(customerCreateForm.getPostcode());
//        c.setPassword(customerCreateForm.getPassword1());
        if (!isSocialSignup) {
            // 비밀번호 암호화 후 설정
            c.setPassword(customerCreateForm.getPassword1());
        } else {
            // 소셜 로그인 시 비밀번호는 필요 없거나 다른 방식으로 처리
            c.setPassword(null); // 또는 임시 비밀번호 생성 등
        }


        customerService.create(c);

        return "redirect:/"; // 성공적으로 생성된 경우 리다이렉트
    }

    @GetMapping("/signin")
    public String signin() {
        return "auth/loginForm"; // 로그인 폼으로 이동
    }


}
