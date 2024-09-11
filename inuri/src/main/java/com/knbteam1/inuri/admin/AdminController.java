// 이강혁 4
package com.knbteam1.inuri.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("")
    public String index() {
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
