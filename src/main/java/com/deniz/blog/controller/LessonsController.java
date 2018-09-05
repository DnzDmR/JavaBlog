package com.deniz.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deniz.blog.entites.Lessons;
import com.deniz.blog.repository.LessonsRepository;

@Controller
@RequestMapping("/admin/lessons")
public class LessonsController {

	@Autowired
	LessonsRepository lessonsRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getLessonAddPage(Model model) {

		model.addAttribute("lesson", new Lessons());
		model.addAttribute("lessons", lessonsRepository.findAll());
		return "adminPages/lessonsAdd";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getLessonEditPage(Model model) {

 		model.addAttribute("lessons", lessonsRepository.findAll());
		return "adminPages/lessonsEdit";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postLessonAddPage(@ModelAttribute("lesson") Lessons lesson, RedirectAttributes redirectAttributes) {

		String url = lesson.getLessonsName().toLowerCase().replace(" ", "");
		lesson.setLessonsUrl(url);
		lessonsRepository.save(lesson);

		redirectAttributes.addFlashAttribute("message", "Başarı ile eklendi");

		return "redirect:/admin/lessons/add";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getLessonDeletePage(@PathVariable("id") Integer id) {

		lessonsRepository.deleteById(id);

		return "redirect:/admin/lessons/edit";
	}

}
