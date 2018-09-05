package com.deniz.blog.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String postNewsAddPage(@ModelAttribute("news") News news, @RequestParam("images") MultipartFile file ) throws IOException {

		news.setImage(file.getBytes());
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

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getNewsEditPage(@PathVariable("id") Integer id, Model model) {

		Optional<News> news = newsRepository.findById(id);
		model.addAttribute("news", news.get());

		return "adminPages/newsEdit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String postNewsEditPage(@ModelAttribute("news") News news) {

		newsRepository.save(news);

		return "redirect:/admin/news/list";
	}

}
