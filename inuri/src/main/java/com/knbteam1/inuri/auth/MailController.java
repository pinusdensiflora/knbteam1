package com.knbteam1.inuri.auth;

import com.knbteam1.inuri.patron.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/api/mail")
    public ResponseEntity<Map<String, Object>> sendMail(@RequestParam("mail") String mail) {
        int authNumber = mailService.createRandomNumber(mail);
        Map<String, Object> response = new HashMap<>();

        if (authNumber != -1) {
            response.put("success", true);
            response.put("message", "인증 메일이 성공적으로 전송되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "이미 등록된 메일이거나 메일 전송에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/api/verify")
    public boolean verifyCode(@RequestParam("mail") String mail, @RequestParam("code") int code) {
        // MailService에서 인증 번호를 확인하는 메서드 호출
        return mailService.verifyAuthCode(mail, code);
    }

}
