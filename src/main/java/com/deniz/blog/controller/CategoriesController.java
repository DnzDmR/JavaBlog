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
@RequestMapping("/admin/categories")
public class CategoriesController {

	@Autowired
	CategoriesRepository categoriesRepository;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getCategoriesAddPage(Model model) {

		model.addAttribute("category", new Categories());
		model.addAttribute("categories", categoriesRepository.findAll());
		return "adminPages/categoriesAdd";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getCategoriesListPage(Model model) {

 		model.addAttribute("categories", categoriesRepository.findAll());
		return "adminPages/categoriesList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postCategoriesAddPage(@ModelAttribute("category") Categories category, RedirectAttributes redirectAttributes) {

		String url = category.getCategoryName().toLowerCase().replace(" ", "");
		category.setCategoryUrl(url);
		categoriesRepository.save(category);

		redirectAttributes.addFlashAttribute("message", "Başarı ile eklendi");

		return "redirect:/admin/categories/add";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getCategoriesDeletePage(@PathVariable("id") Integer id) {

		categoriesRepository.deleteById(id);

		return "redirect:/admin/categories/list";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getCategoriesEditPage(Model model,@PathVariable("id") Integer id) {

 		model.addAttribute("category", categoriesRepository.findById(id));
		return "adminPages/categoriesEdit";
	}
	

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String postCategoriesEditPage(@ModelAttribute("category") Categories category, RedirectAttributes redirectAttributes) {

		String url = category.getCategoryName().toLowerCase().replace(" ", "");
		category.setCategoryUrl(url);
		categoriesRepository.save(category);

		redirectAttributes.addFlashAttribute("message", "Başarı ile güncellendi");

		return "redirect:/admin/categories/edit/"+category.getId();
	}

}
