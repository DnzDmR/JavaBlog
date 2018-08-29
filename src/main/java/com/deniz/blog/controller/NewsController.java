package com.deniz.blog.controller;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.entites.News;
import com.deniz.blog.repository.NewsRepository;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

	@Autowired
	NewsRepository newsRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getNewsAddPage(Model model) {

		model.addAttribute("news", new News());
		return "adminPages/newsAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postNewsAddPage(@ModelAttribute("news") News news) {

		news.setCreateDate(new Date(Calendar.getInstance().getTime().getTime()));
		newsRepository.save(news);

		return "adminPages/newsEdit";
	}

}
