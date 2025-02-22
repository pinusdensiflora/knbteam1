package com.knbteam1.inuri.auth;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class CustomerFactory {
    public static Customer create(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        return switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "kakao" -> {
                Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
                yield Customer.builder()
                        .username(attributeMap.get("email").toString())
//                        .name(attributeMap.get("name").toString())
//                        .ctel(attributeMap.get("phone_number").toString())
                        .role("ROLE_USER")
                        .build();
            }
            case "google" -> {
                Map<String, Object> attributeMap = oAuth2User.getAttributes();
                yield Customer.builder()
                        .username(attributeMap.get("email").toString())
                        .name(attributeMap.get("name").toString())
                        .role("ROLE_USER")
                        .build();
            }
            default -> throw new IllegalArgumentException("연동되지 않은 서비스입니다.");
        };
    }
}
