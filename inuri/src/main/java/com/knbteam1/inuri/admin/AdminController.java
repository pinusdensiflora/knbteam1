// 이강혁 4
package com.knbteam1.inuri.admin;

import com.knbteam1.inuri.news.News;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String readPatron() {
        return "admin/patron/readPatronList";
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
