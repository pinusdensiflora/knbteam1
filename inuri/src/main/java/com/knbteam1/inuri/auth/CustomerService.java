package com.knbteam1.inuri.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.knbteam1.inuri.auth.api.CustomerDTO;
import com.knbteam1.inuri.auth.api.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원가입
    public void create(Customer customer) {

        customer.setCdate(LocalDateTime.now());
        customer.setRole("ROLE_USER");
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        this.customerRepository.save(customer);
    }


    //시큐리티 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> _siteUser = this.customerRepository.findByusername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Customer customer = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(customer.getUsername(), customer.getPassword(), authorities);
    }

    public Customer authen() {
        //접속자 정보 추출
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Optional<Customer> oc = customerRepository.findByusername(username);

        return oc.get();
    }

    // RestAPI SignUp
    public CustomerDTO apiSignUp(String username, String password, String name, String addr, String postcode, String tel) {

        // 이미 유저가 있는 경우 오류 발생시킴
        customerRepository.findByusername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(username);
                });

        Customer customer = Customer.of(username, passwordEncoder.encode(password), name, addr, postcode, tel);
        customer.setCdate(LocalDateTime.now());
        if (username.equals("admin") || username.equals("admin@admin.com")) {
            customer.setRole("ROLE_ADMIN");
        } else {
            customer.setRole("ROLE_USER");
        }
        Customer saved = customerRepository.save(customer);

        return CustomerDTO.from(saved);
    }

}