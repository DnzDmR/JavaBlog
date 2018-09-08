package com.deniz.blog.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deniz.blog.entites.About;
import com.deniz.blog.entites.Categories;
import com.deniz.blog.entites.Contacts;
import com.deniz.blog.entites.Lessons;
import com.deniz.blog.entites.News;
import com.deniz.blog.repository.AboutRepository;
import com.deniz.blog.repository.CategoriesRepository;
import com.deniz.blog.repository.ContactsRepository;
import com.deniz.blog.repository.LessonsRepository;
import com.deniz.blog.repository.NewsRepository;

@Controller
@RequestMapping("")
public class HomeController {

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	ContactsRepository contactRepository;

	@Autowired
	AboutRepository aboutRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Autowired
	LessonsRepository lessonRepository;

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

		if (aboutRepository.getAboutList().size() > 0) {
			model.addAttribute("about", aboutRepository.getAboutList().get(0));
		} else {
			model.addAttribute("about", new About());
		}

		return "blogPages/about";
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public String getNewsPage(Model model, @PathVariable Integer id) {

		model.addAttribute("news", newsRepository.findById(id).get());

		return "blogPages/newsContent";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String postContactPage(@ModelAttribute("contact") Contacts contact, RedirectAttributes redirectAttributes) {

		contact.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		contactRepository.save(contact);

		redirectAttributes.addFlashAttribute("message",
				"Mesajınız başarıyla gönderilmiştir. Mail aracılığıyla dönüş yapılacaktır.");

		return "redirect:/contact";
	}

	@ModelAttribute("categories")
	public Iterable<Categories> getLessonList() {
		return categoriesRepository.findAll();
	}

	@RequestMapping(value = "/lessons/{lesson}/{id}", method = RequestMethod.GET)
	public String getLessonContentPage(@PathVariable("lesson") String lesson, @PathVariable("id") Integer id,
			Model model) {

		System.out.println("##" + lessonRepository.findById(id).get().getTitle());
		model.addAttribute("lesson", lessonRepository.findById(id).get());

		return "blogPages/lessonsContent";
	}

	@RequestMapping(value = { "/news/pages/{id}", "/news" }, method = RequestMethod.GET)
	public String getNewsPageNumber(Model model, @PathVariable(required = false, value = "id") Integer id) {

		List<News> news = (List<News>) newsRepository.findAll();

		List<News> tempList = new ArrayList<News>();

		if (id == null) {
			id = 1;
		}
		int pageLimit = 10;

		double totalNumber = news.size();

		double temp = Math.ceil(totalNumber / pageLimit);
		Integer totalPageCount = Integer.parseInt(Double.toString(temp).replace(".0", ""));

		if (totalPageCount >= id) {

			for (int startValue = (id - 1) * pageLimit; startValue < pageLimit * id; startValue++) {

				if (news.size() < startValue + 1) {
					break;
				}
				tempList.add(news.get(startValue));
			}
		}
		if (tempList.size() > 0) {
			model.addAttribute("news", tempList);
		}

		model.addAttribute("totalPage", totalPageCount);
		model.addAttribute("id", id);

		return "blogPages/news";
	}

	@RequestMapping(value = { "/lessons/{lessons}/pages/{id}", "/lessons/{lessons}" }, method = RequestMethod.GET)
	public String getLessonsPageNumber(Model model, @PathVariable("lessons") String lesson,
			@PathVariable(required = false, value = "id") Integer id) {

		Categories a = categoriesRepository.getCategoryByCategoryName(lesson).get(0);

		List<Lessons> news = lessonRepository.getLessonsByCategory(a);

		List<Lessons> tempList = new ArrayList<Lessons>();

		if (id == null) {
			id = 1;
		}
		int pageLimit = 10;

		double totalNumber = news.size();

		double temp = Math.ceil(totalNumber / pageLimit);
		Integer totalPageCount = Integer.parseInt(Double.toString(temp).replace(".0", ""));

		if (totalPageCount >= id) {

			for (int startValue = (id - 1) * pageLimit; startValue < pageLimit * id; startValue++) {

				if (news.size() < startValue + 1) {
					break;
				}
				tempList.add(news.get(startValue));
			}
		}
		if (tempList.size() > 0) {
			model.addAttribute("lessons", tempList);
		}

		model.addAttribute("totalPage", totalPageCount);
		model.addAttribute("id", id);
		model.addAttribute("lessonUrl", lesson);

		return "blogPages/lessons";
	}
}
