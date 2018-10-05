package com.deniz.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JavaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBlogApplication.class, args);
	}
	
	//for Google reCaptcha 
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
