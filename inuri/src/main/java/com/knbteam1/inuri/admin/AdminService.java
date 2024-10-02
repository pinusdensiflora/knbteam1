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
import com.knbteam1.inuri.patron.Child;
import com.knbteam1.inuri.patron.ChildRepository;
import com.knbteam1.inuri.patron.Donation;
import com.knbteam1.inuri.patron.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final NewsRepository newsRepository;
    private final CustomerRepository customerRepository;
    private final DonationRepository donationRepository;
    private final ChildRepository childRepository;

    public Page<News> readAllNews(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return newsRepository.findAll(pageable);
    }

    public List<Customer> readAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Child> readAllChildren(){ return childRepository.findAll(); }

    public List<Donation> readDonations() {
        return donationRepository.findAll();
    }


//    public Integer calTodayDonate(){
//        return donationRepository.findTotalDonationsByDate(LocalDateTime.now());
//    }
}
