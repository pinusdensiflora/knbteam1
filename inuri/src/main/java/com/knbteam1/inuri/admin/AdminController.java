/*
admin/leftNav.html
생산자: 이강혁
생성날짜: 9.10
연락처: rkdgur5381@gmail.com
*/
package com.knbteam1.inuri.admin;

import com.knbteam1.inuri.news.NewsService;
import com.knbteam1.inuri.patron.Child;
import com.knbteam1.inuri.patron.ChildService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;
    private final NewsService newsService;
    private final ChildService childService;

    public AdminController(AdminService adminService, NewsService newsService, ChildService childService) {
        this.adminService = adminService;
        this.newsService = newsService;
        this.childService = childService;
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

    @GetMapping("/children")
    public String readChildren(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model
    ) {

        Page<Child> paging = childService.getFilteredChildren(page, "", "", "all");
        model.addAttribute("paging", paging);
        model.addAttribute("page", paging.getNumber() + 1);
        return "admin/child/readChildren";
    }

    @GetMapping("/faq")
    public String readFaq(Model model) {
        model.addAttribute("faqlist", newsService.readlist(5));
        return "admin/faq/faqList";
    }

    @GetMapping("/donations")
    public String readDonation(Model model) {
        model.addAttribute("donationlist", adminService.readDonations());
        return "admin/donation/readDonationList";
    }
}
