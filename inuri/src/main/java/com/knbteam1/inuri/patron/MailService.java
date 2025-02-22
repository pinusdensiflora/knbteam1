/* 생성자 : 김근환
생성일 : 9.24
연락처 : ghwan07@gmail.com
*/
package com.knbteam1.inuri.patron;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CustomerRepository customerRepository;

    // 이메일과 인증번호를 저장할 임시 저장소 (HashMap을 사용한 예시)
    private final Map<String, Integer> authCodeStorage = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public void create(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rkdgur5381@naver.com"); // 변동 불가
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // 이미지 포함해서 전송시키기
    public void create(String to, String subject, String text, String imageUrl) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("rkdgur5381@naver.com"); // 변동 불가
            helper.setTo(to);
            helper.setSubject(subject);
            // Create HTML content with image
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body>");
            htmlContent.append("<p>").append(text).append("</p>");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                htmlContent.append("<img src='").append(imageUrl).append("' alt='Image' width='512' height='512 />");
            }
            htmlContent.append("</body></html>");
            helper.setText(htmlContent.toString(), true); // Set to true for HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception or throw a custom exception
        }
    }

    // 인증번호 생성하기
    public Integer createRandomNumber(String mail) {
        if (!checkIsUser(mail)) {

            if(authCodeStorage.containsKey(mail)) {
                return authCodeStorage.get(mail);
            }

            Random random = new Random();

            int randomNumber = 100000 + random.nextInt(900000);

            // 인증 번호 저장 (나중에 검증에 사용)
            authCodeStorage.put(mail, randomNumber);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("rkdgur5381@naver.com");
            message.setTo(mail);
            message.setSubject("Inuri 로그인 인증번호 안내");
            message.setText(mail + "님의 인증번호는 " + randomNumber + "입니다.");
            mailSender.send(message);

            scheduleAuthCodeRemoval(mail, 3);

            return randomNumber;
        } else {
            return -1;
        }
    }

    // 유저 유무 확인하기
    private boolean checkIsUser(String mail) {
        Optional<Customer> customer = customerRepository.findByusername(mail);
        System.out.println("customer = " + customer.isPresent());
        return customer.isPresent();
    }

    // 인증 번호 확인
    public boolean verifyAuthCode(String mail, int code) {
        // 이메일에 대해 저장된 인증 번호가 있는지 확인
        if (authCodeStorage.containsKey(mail)) {
            int storedCode = authCodeStorage.get(mail);
            // 입력한 코드가 저장된 인증 번호와 일치하는지 확인
            if (storedCode == code) {
                // 인증이 성공하면 저장소에서 제거 (1회용 인증 코드)
                authCodeStorage.remove(mail);
                return true;
            }
        }
        // 인증 실패
        return false;
    }

    // 인증 번호를 일정 시간이 지나면 삭제하는 메서드
    private void scheduleAuthCodeRemoval(String mail, int minutes) {
        scheduler.schedule(() -> {
            authCodeStorage.remove(mail);
            System.out.println("Auth code for " + mail + " has been removed after " + minutes + " minutes.");
        }, minutes, TimeUnit.MINUTES);
    }

}
