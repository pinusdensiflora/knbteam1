package com.knbteam1.inuri.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // Determine the provider
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String email = switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("email");
            case "kakao" -> {
                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
                yield kakaoAccount.get("email").toString();
            }
            default -> "";
        };

        Customer customer = customerRepository.findByusername(email).orElseGet(() -> {
            Customer newCustomer = CustomerFactory.create(userRequest, oAuth2User);
            return customerRepository.save(newCustomer);
        });

        return new OAuth2Customer(customer, oAuth2User.getAttributes());
    }
}
