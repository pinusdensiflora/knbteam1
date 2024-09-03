package com.mysite.finalexam.notice;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


//@RequestMapping("/notice")
@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
	
	
	
	
	//create
	@GetMapping("/create")
	public String create() {
		return "create";
	}
	
//	@PostMapping("/create")
//	public String create(@ModelAttribute Notice notice) {
//		noticeService.create(notice);
//		return "redirect:/readlist";
//	}
	
	//파일 입력 받기
	@PostMapping("/create")
	public String create(@ModelAttribute Notice notice,
					     @RequestParam("file") MultipartFile file
			) throws IOException {
		noticeService.create(notice, file); //예외처리 요구됨
		return "redirect:/readlist";
	}
	
	
	//readlist
	@GetMapping("/readlist")
	public String readlist(Model model) {
		model.addAttribute("notices", noticeService.readlist());
		model.addAttribute("downpath", "http://" + downpath);
		return "readlist";
	}
	
	
	//readdetail
	@GetMapping("/readdetail/{id}")
	public String readdetail(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("notice", noticeService.readdetail(id));
		model.addAttribute("downpath", "https://" + downpath);
		return "readdetail";
	}
	
	
//	//update 오류코드
//	@GetMapping("/update/{id}")
//	public String update(Model model, @PathVariable("id") Integer id) {
//		model.addAttribute("notice", noticeService.readdetail(id));
//		return "update";
//	}
//	
//	@PostMapping("/update/")
//	public String update(@ModelAttribute Notice notice, 
//						 @RequestParam MultipartFile file) throws IOException {//객체와 사진파일(없을 수도 있음)을 받음
//		noticeService.update(notice, file);
//		return "redirect:/readdetail/" + notice.getId();
//	}
	 
	
	//update
	@GetMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("notice", noticeService.readdetail(id));
		return "update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Notice notice,
		     @RequestParam("file") MultipartFile file
		     ) throws IOException {
		
		noticeService.update(notice, file);
		
		return "redirect:readdetail/"+ notice.getId();
	}
	
	
	
	//delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		noticeService.delete(id);
		return "redirect:/readlist";
	}
	
	
}