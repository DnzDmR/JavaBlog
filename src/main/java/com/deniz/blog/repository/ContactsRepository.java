package com.deniz.blog.repository;

import org.springframework.data.repository.CrudRepository;

import com.deniz.blog.entites.Contacts;

public interface ContactsRepository extends CrudRepository<Contacts, Integer> {

}
