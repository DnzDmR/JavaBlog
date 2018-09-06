package com.deniz.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.Categories;
import com.deniz.blog.entites.Lessons;

@Repository
public interface LessonsRepository extends CrudRepository<Lessons, Integer> {

	@Query("SELECT l FROM Lessons l WHERE l.category =:category")
	List<Lessons> getLessonsByCategory(@Param("category") Categories category);

}
