package com.deniz.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deniz.blog.entites.Categories;
import com.deniz.blog.repository.CategoriesRepository;

@Controller
@RequestMapping("/admin/lessons")
public class CategoriesController {

	@Autowired
	CategoriesRepository categoriesRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getCategoriesAddPage(Model model) {

		model.addAttribute("category", new Categories());
		model.addAttribute("categories", categoriesRepository.findAll());
		return "adminPages/lessonsAdd";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getCategoriesEditPage(Model model) {

 		model.addAttribute("categories", categoriesRepository.findAll());
		return "adminPages/lessonsEdit";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postCategoriesAddPage(@ModelAttribute("lesson") Categories categories, RedirectAttributes redirectAttributes) {

		String url = categories.getCategoryName().toLowerCase().replace(" ", "");
		categories.setCategoryUrl(url);
		categoriesRepository.save(categories);

		redirectAttributes.addFlashAttribute("message", "Başarı ile eklendi");

		return "redirect:/admin/lessons/add";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getCategoriesDeletePage(@PathVariable("id") Integer id) {

		categoriesRepository.deleteById(id);

		return "redirect:/admin/lessons/edit";
	}

}
