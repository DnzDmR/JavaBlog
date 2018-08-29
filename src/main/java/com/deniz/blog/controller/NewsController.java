package com.deniz.blog.controller;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.entites.News;
import com.deniz.blog.repository.NewsRepository;
import com.deniz.blog.repository.UsersRepository;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	UsersRepository usersRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getNewsAddPage(Model model) {

		model.addAttribute("news", new News());
		return "adminPages/newsAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postNewsAddPage(@ModelAttribute("news") News news) {

		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();

		news.setAuthor(usersRepository.getUserByUsername(currentPrincipalName).get());
		news.setCreateDate(new Date(Calendar.getInstance().getTime().getTime()));
		newsRepository.save(news);

		return "redirect:/admin/news/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getNewsListPage(Model model) {

		Iterable<News> news = newsRepository.findAll();

		model.addAttribute("news", news);

		return "adminPages/newsList";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getNewsDelete(@PathVariable("id") Integer id) {
		
		newsRepository.deleteById(id);
	 
		return "redirect:/admin/news/list";
	}
	
	

}
