package com.deniz.blog.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deniz.blog.entites.Authorities;
import com.deniz.blog.entites.Users;
import com.deniz.blog.repository.UsersRepository;

@Controller
@RequestMapping("/admin/users")
public class UsersController {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getUsersAddPage(Model model) {

		model.addAttribute("user", new Users());

		return "adminPages/usersAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postUsersAddPage(@ModelAttribute("user") @Valid Users user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "adminPages/usersAdd";
		}

		Authorities authorities = new Authorities();
		authorities.setUsername(user.getUsername());
		authorities.setAuthority("ROLE_USER");

		Collection<Authorities> list = new ArrayList<Authorities>();
		list.add(authorities);

 		user.setAuthorities(list);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		usersRepository.save(user);

		return "redirect:/admin/users/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getUsersListPage(Model model) {

		Iterable<Users> users = usersRepository.findAll();

		model.addAttribute("users", users);

		return "adminPages/usersList";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String getUsersEditPage(Model model, @PathVariable("id") Integer id) {

		Optional<Users> user = usersRepository.findById(id);

		model.addAttribute("user", user.get());

		return "adminPages/usersEdit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String postUsersEditPage(@ModelAttribute("user") @Valid Users user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "adminPages/usersEdit";
		}

		usersRepository.save(user);

		return "redirect:/admin/users/list";
	}

}
