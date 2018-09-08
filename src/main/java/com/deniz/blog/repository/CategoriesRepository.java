package com.deniz.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.Categories;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Integer> {

	@Query("SELECT c FROM Categories c WHERE c.categoryUrl =:category")
	List<Categories> getCategoryByCategoryName(@Param("category") String category);
		
}
