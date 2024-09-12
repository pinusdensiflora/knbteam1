/*
admin/leftNav.html
생산자: 이강혁
생성날짜: 9.10
연락처: rkdgur5381@gmail.com
*/
package com.knbteam1.inuri.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("newsList", adminService.readAllNews());
        return "admin/main";
    }

    @GetMapping("/patrons")
    public String readPatron(Model model) {
        model.addAttribute("patrons", adminService.readAllCustomers());
        return "admin/patrons/readPatronList";
    }

    @GetMapping("/children")
    public String readChildren() {
        return "admin/child/readChildren";
    }

    @GetMapping("/children/add")
    public String addChild(){
        return "admin/child/addChild";
    }
}
