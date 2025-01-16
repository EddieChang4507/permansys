package com.permansys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@GetMapping("/homePage")
	public String homePage() {
		return "homePage"; // 對應 /templates/homePage.html
	}

	@GetMapping("/addPage")
	public String addPage() {
		return "addPage"; // 對應 /templates/addPage.html
	}

	@GetMapping("/welcomePage")
	public String welcomePage() {
		return "welcomePage"; // 對應 /templates/welcomePage.html
	}

	@GetMapping("/updatePage")
	public String updatePage() {
		return "updatePage"; // 對應 /templates/updatePage.html
	}

	@GetMapping("/searchPage")
	public String searchPage() {
		return "searchPage"; // 對應 /templates/searchPage.html
	}

	@GetMapping("/signupPage")
	public String signupPage() {
		return "signupPage"; // 對應 /templates/signupPage.html
	}
}
