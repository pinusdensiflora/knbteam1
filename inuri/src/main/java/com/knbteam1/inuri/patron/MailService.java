 /* 생성자 : 김근환 
 생성일 : 9.24 
 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

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
                htmlContent.append("<img src='").append(imageUrl).append("' alt='Image' />");
            }
            htmlContent.append("</body></html>");
            helper.setText(htmlContent.toString(), true); // Set to true for HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception or throw a custom exception
        }
    }

}
