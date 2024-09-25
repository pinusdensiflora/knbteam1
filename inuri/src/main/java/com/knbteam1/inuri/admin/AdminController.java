/*
admin/leftNav.html
생산자: 이강혁
생성날짜: 9.10
연락처: rkdgur5381@gmail.com
*/
package com.knbteam1.inuri.admin;

import com.knbteam1.inuri.news.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;
    private final NewsService newsService;

    public AdminController(AdminService adminService, NewsService newsService) {
        this.adminService = adminService;
        this.newsService = newsService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("newsList", adminService.readAllNews());
        model.addAttribute("patronsCount", adminService.readAllCustomers().toArray().length);
        model.addAttribute("childrenCount", adminService.readAllChildren().toArray().length);
//        model.addAttribute("donationSum", adminService.readDonations()
//                .stream()
//                .mapToInt(donation::getDonationAmount));
//        model.addAttribute("todayDonation", adminService.calTodayDonate());
        return "admin/main";
    }

    @GetMapping("/patrons")
    public String readPatron(Model model) {
        model.addAttribute("patrons", adminService.readAllCustomers());
        return "admin/patrons/readPatronList";
    }

    @GetMapping("/children/add")
    public String addChild() {
        return "admin/child/addChild";
    }

    @GetMapping("/faq")
    public String readFaq(Model model) {
        model.addAttribute("faqlist", newsService.readlist("faq"));
        return "admin/faq/faqList";
    }

    @GetMapping("/donations")
    public String readDonation(Model model) {
        model.addAttribute("donationlist", adminService.readDonations());
        return "admin/donation/readDonationList";
    }
}
