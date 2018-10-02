package com.deniz.blog.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsController implements ErrorController {

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String get403Page() {

		return "errors/403";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "errors/404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "errors/500";
			}
		}
		return "errors/404";
	}

}
