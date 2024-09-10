package com.knbteam1.inuri.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {// 상속을 받는 형태로 설정하는 방식은 2023 03 이전 방식이다.

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // 인가를 처리하는 부분 접근 가능한 영역을 설정하는 부분, 컨트롤로에서도 가능하다.
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/signin")//커스텀 로그인 창 호출 위치
                        .defaultSuccessUrl("/")) //로그인 성공시 이동할 위치
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/signout")) //로그아웃 경로
                        .logoutSuccessUrl("/")  //로그아웃 성공한 후에 이동할 위치
                        .invalidateHttpSession(true)) //세션 삭제
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}