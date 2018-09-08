package com.deniz.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.News;

@Repository
public interface NewsRepository extends CrudRepository<News, Integer> {

	Optional<News> findFirstByOrderByIdDesc();
	
	List<News> findFirst6ByOrderByIdDesc();
}
