package com.deniz.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.deniz.blog.entites.Users;

public interface UsersRepository extends CrudRepository<Users, Integer>{
	
	@Query("SELECT u from Users u where u.username=:username")
	Optional<Users> getUserByUsername(@Param("username") String username);

}
