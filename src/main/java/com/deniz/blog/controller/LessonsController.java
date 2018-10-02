package com.deniz.blog.controller;

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

import com.deniz.blog.entites.Categories;
import com.deniz.blog.entites.Lessons;
import com.deniz.blog.repository.CategoriesRepository;
import com.deniz.blog.repository.LessonsRepository;
import com.deniz.blog.repository.UsersRepository;

@Controller
@RequestMapping("/admin/lessons")
public class LessonsController {

	@Autowired
	LessonsRepository lessonsRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getCategoriesAddPage(Model model) {

		model.addAttribute("lesson", new Lessons());

		return "adminPages/lessonsAdd";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getLessonsEditPage(Model model) {

		model.addAttribute("lessons", lessonsRepository.findAll());
		return "adminPages/lessonsList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postNewsAddPage(@ModelAttribute("lesson") Lessons lesson) {

		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		lesson.setAuthor(usersRepository.getUserByUsername(currentPrincipalName).get());
		lesson.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		lessonsRepository.save(lesson);

		return "redirect:/admin/lessons/list";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getLessonsDeletePage(@PathVariable("id") Integer id) {

		lessonsRepository.deleteById(id);

		return "redirect:/admin/lessons/list";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getLessonsEditPage(@PathVariable("id") Integer id, Model model) {

		Optional<Lessons> lesson = lessonsRepository.findById(id);
		model.addAttribute("lesson", lesson.get());

		return "adminPages/lessonsEdit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String postLessonsEditPage(@ModelAttribute("lesson") Lessons lesson) {

		lessonsRepository.save(lesson);

		return "redirect:/admin/lessons/list";
	}

	@ModelAttribute("categories")
	public Iterable<Categories> getLessonList() {
		return categoriesRepository.findAll();
	}

}
