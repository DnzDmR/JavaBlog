package com.deniz.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.repository.NewsRepository;

@Controller
@RequestMapping("")
public class HomeController {
	
	@Autowired
	NewsRepository newsRepository;
	
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
		
		model.addAttribute("news", newsRepository.findAll());
		
		return "blogPages/news";
	}

}
