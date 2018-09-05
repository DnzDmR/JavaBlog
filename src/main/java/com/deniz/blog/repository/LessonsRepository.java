package com.deniz.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deniz.blog.entites.Lessons;

@Repository
public interface LessonsRepository extends CrudRepository<Lessons, Integer> {

}
