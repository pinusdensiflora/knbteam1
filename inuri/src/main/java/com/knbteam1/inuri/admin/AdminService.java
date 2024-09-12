/*
admin/leftNav.html
생산자: 이강혁
생성날짜: 9.11
연락처: rkdgur5381@gmail.com
*/
package com.knbteam1.inuri.admin;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerRepository;
import com.knbteam1.inuri.auth.CustomerService;
import com.knbteam1.inuri.news.News;
import com.knbteam1.inuri.news.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final NewsRepository newsRepository;
    private final CustomerRepository customerRepository;

    public List<News> readAllNews() {
        return newsRepository.findAll();
    }

    public List<Customer> readAllCustomers() {
        return customerRepository.findAll();
    }
}
