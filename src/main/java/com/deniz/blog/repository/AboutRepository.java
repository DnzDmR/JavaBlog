package com.deniz.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.deniz.blog.entites.About;

public interface AboutRepository extends CrudRepository<About, Integer>{

	@Query("SELECT a FROM About a") 
	List<About> getAboutList();
}
