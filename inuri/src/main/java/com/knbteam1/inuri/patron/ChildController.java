/*
 * 생성자 : 김근환 생성일 : 9.13 연락처 : ghwan07@gmail.com
 */
package com.knbteam1.inuri.patron;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping("/donate") public String donate() { 
    	return "patron/donate"; 
    	}
    
    // 아동 목록 페이지
    @GetMapping("/child_list")
    public String childlist(Model model, @RequestParam(value="page", defaultValue="0") int page) {
    	//페이징
    	Page<Child> paging = this.childService.getList(page);
        model.addAttribute("paging", paging);
    	
        List<Child> children = childService.getAllChildren();
        model.addAttribute("children", children);
        return "patron/child_list";
    }

    // 특정 아동 상세 페이지
    @GetMapping("/child_detail/{chid}")
    public String childdetail(@PathVariable("chid") Integer chid, Model model) {
        Optional<Child> child = childService.getChildById(chid);
        if (child.isPresent()) {
            model.addAttribute("child", child.get());
            return "patron/child_detail";
        } else {
            return "error/404"; // 아동이 없을 경우 404 페이지로 연결
        }
    }

    // 아동 검색 페이지
    @GetMapping("/child_search")
    public String childsearch(@RequestParam("keyword") String keyword, Model model) {
        List<Child> searchResults = childService.searchChildren(keyword);
        model.addAttribute("searchResults", searchResults);
        return "patron/child_search"; // child_search.html로 연결
    }

    // 아동 추가 (예: 폼 제출을 통해 처리할 수 있는 POST 요청)
    @PostMapping("/child/add")
    public String addChild(@ModelAttribute Child child) {
        childService.createOrUpdateChild(child);
        return "redirect:patron/child_list"; // 아동 목록으로 리다이렉트
    }

    // 아동 삭제
    @GetMapping("/child/delete/{chid}")
    public String deleteChild(@PathVariable Integer id) {
        childService.deleteChildById(id);
        return "redirect:patron/child_list"; // 아동 목록으로 리다이렉트
    }
}
