package com.deniz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(Model model) {

		return "adminPages/login";
	}

	@RequestMapping(value = {"/home",""}, method = RequestMethod.GET)
	public String getHomePage(Model model) {

		return "adminPages/home";
	}

}
