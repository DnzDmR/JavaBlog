package com.deniz.blog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.Categories;
import com.deniz.blog.entites.Lessons;

@Repository
public interface LessonsRepository extends CrudRepository<Lessons, Integer> {

	List<Lessons> findAllByCategoryOrderByIdDesc(Categories category);

}
