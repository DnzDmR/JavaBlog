package com.deniz.blog.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.entites.About;
import com.deniz.blog.repository.AboutRepository;

@Controller
@RequestMapping("/admin/about")
public class AboutController {

	@Autowired
	AboutRepository aboutRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getEditPage(Model model) {

		List<About> about = aboutRepository.getAboutList();

		if (about.size() > 0) {
			model.addAttribute("about", aboutRepository.findById(about.get(0).getId()).get());
		} else {
			model.addAttribute("about", new About());
		}

		return "adminPages/aboutEdit";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postEditPage(@ModelAttribute("about") About about) {

		about.setDate(new Date(Calendar.getInstance().getTime().getTime()));

		aboutRepository.save(about);

		return "adminPages/aboutEdit";
	}

}
