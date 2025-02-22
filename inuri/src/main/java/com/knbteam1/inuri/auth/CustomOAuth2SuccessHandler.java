package com.knbteam1.inuri.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2Customer oAuth2Customer = (OAuth2Customer) authentication.getPrincipal();
        Customer customer = oAuth2Customer.getCustomer();

        boolean needsSignup = false;

        // 필요한 정보가 누락되었는지 확인
        if (customer.getUsername() == null || customer.getUsername().isEmpty() ||
                customer.getName() == null || customer.getName().isEmpty() ||
                customer.getCtel() == null || customer.getCtel().isEmpty() ||
                customer.getCaddr() == null || customer.getCaddr().isEmpty() ||
                customer.getPostcode() == null || customer.getPostcode().isEmpty()
        ) {
            needsSignup = true;
        }

        if (needsSignup) {
            // 기존 데이터를 쿼리 파라미터로 전달하여 /signup으로 리디렉션
            // 기존 데이터를 쿼리 파라미터로 전달하여 /signup으로 리디렉션
            String username = URLEncoder.encode(customer.getUsername() != null ? customer.getUsername() : "", StandardCharsets.UTF_8);
//            String name = URLEncoder.encode(customer.getName() != null ? customer.getName() : "", StandardCharsets.UTF_8);
//            String ctel = customer.getCtel() != null ? customer.getCtel() : "";

            String redirectUrl = String.format("/signup?username=%s",
                    username);
            System.out.println("redirectUrl = " + redirectUrl);
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            // 기본 성공 URL로 리디렉션
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
