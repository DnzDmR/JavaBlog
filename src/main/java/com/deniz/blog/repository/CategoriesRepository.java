package com.deniz.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.Categories;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Integer> {

}
