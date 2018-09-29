package com.deniz.blog.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

	private String baseUrl = System.getProperty("user.home") + "/images/";

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String getHomePage(Model model) {

		Optional<News> lastNews = newsRepository.findFirstByOrderByIdDesc();
		if (lastNews.isPresent()) {
			model.addAttribute("lastNews", lastNews.get());
		}

		model.addAttribute("categories", categoriesRepository.findAll());
		model.addAttribute("news", newsRepository.findFirst6ByOrderByIdDesc());
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

 		model.addAttribute("lesson", lessonRepository.findById(id).get());

		return "blogPages/lessonsContent";
	}

	@RequestMapping(value = { "/news/pages/{id}", "/news" }, method = RequestMethod.GET)
	public String getNewsPageNumber(Model model, @PathVariable(required = false, value = "id") Integer id) {

		List<News> news = (List<News>) newsRepository.findAllByOrderByIdDesc();

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

		List<Lessons> news = lessonRepository.findAllByCategoryOrderByIdDesc(a);

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
			
		model.addAttribute("lessons", tempList);
		model.addAttribute("totalPage", totalPageCount);
		model.addAttribute("id", id);
		model.addAttribute("lessonUrl", lesson);

		return "blogPages/lessons";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearchPage(@RequestParam("search") String search, Model model) {

		List<Lessons> values = lessonRepository.searchLessonByValue(search);

		model.addAttribute("result", values);

		return "blogPages/search";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {

		String imageUrl = "";
		try {

			File a = new File(baseUrl);
			if (!a.exists()) {
				a.mkdirs();
			}

			// Get the filename and build the local file path (be sure that the
			// application have write permissions on such directory)
			String filename = UUID.randomUUID().toString();
			String directory = baseUrl;
			String filepath = Paths.get(directory, filename + ".jpg").toString();

			// Save the file locally
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			stream.write(uploadfile.getBytes());
			stream.close();

			imageUrl = "http://localhost:8080/files/" + filename + ".jpg";

		} catch (Exception e) {
			return ResponseEntity.ok("Failed !");
		}

		return ResponseEntity.ok(imageUrl);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {

		Path file = Paths.get(baseUrl).resolve(filename);
		Resource resource = new UrlResource(file.toUri());
		return ResponseEntity.ok().body(resource);
	}

}
