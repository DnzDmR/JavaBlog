package com.deniz.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.repository.ContactsRepository;

@Controller
@RequestMapping("admin/contacts")
public class ContactsController {

	@Autowired
	ContactsRepository contactRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getContactList(Model model) {

		model.addAttribute("contacts", contactRepository.findAll());
		
		return "adminPages/contactsList";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String getNewsDelete(@PathVariable("id") Integer id) {
		
		contactRepository.deleteById(id);
	 
		return "redirect:/admin/contacts/list";
	}
}
