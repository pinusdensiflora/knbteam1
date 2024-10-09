package com.knbteam1.inuri.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = attributeMap.get("email").toString();

        Customer customer = customerRepository.findByusername(email).orElseGet(() -> registerCustomer(attributeMap));

        return new OAuth2Customer(customer, oAuth2User.getAttributes());
    }

    private Customer registerCustomer(Map<String, Object> attributeMap) {
        Customer customer = Customer.builder()
                .username(attributeMap.get("email").toString())
                .name(attributeMap.get("name").toString())
                .ctel(attributeMap.get("phone_number").toString())
                .role("ROLE_USER")
                .build();

        return customerRepository.save(customer);

    }
}
