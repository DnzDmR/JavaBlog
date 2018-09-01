package com.deniz.blog.controller;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deniz.blog.entites.Contacts;
import com.deniz.blog.repository.ContactsRepository;
import com.deniz.blog.repository.NewsRepository;

@Controller
@RequestMapping("")
public class HomeController {

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	ContactsRepository contactRepository;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String getHomePage(Model model) {

		return "blogPages/home";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String getContactPage(Model model) {

		model.addAttribute("contact", new Contacts());
		return "blogPages/contact";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String getAboutPage(Model model) {

		return "blogPages/about";
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String getNewsPage(Model model) {

		model.addAttribute("news", newsRepository.findAll());

		return "blogPages/news";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String postContactPage(@ModelAttribute("contact") Contacts contact, RedirectAttributes redirectAttributes) {

		contact.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		contactRepository.save(contact);

		redirectAttributes.addFlashAttribute("message",
				"Mesajınız başarıyla gönderilmiştir. Mail aracılığıyla dönüş yapılacaktır.");

		return "redirect:/contact";
	}

}
