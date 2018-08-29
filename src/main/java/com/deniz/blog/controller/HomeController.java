package com.deniz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class HomeController {
	
	
	@RequestMapping(value= {"","/"}, method= RequestMethod.GET)
	public String getHomePage(Model model) {
		
		return "blogPages/home";
	}
	
	@RequestMapping(value="/contact", method= RequestMethod.GET)
	public String getContactPage(Model model) {
		
		return "blogPages/contact";
	}
	
	@RequestMapping(value="/about", method= RequestMethod.GET)
	public String getAboutPage(Model model) {
		
		return "blogPages/about";
	}
	
	@RequestMapping(value="/news", method= RequestMethod.GET)
	public String getNewsPage(Model model) {
		
		return "blogPages/news";
	}

}
