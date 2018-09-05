package com.deniz.blog.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lessons {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique=true)
	private String lessonsName;

	@Column(unique=true)
	private String lessonsUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLessonsName() {
		return lessonsName;
	}

	public void setLessonsName(String lessonsName) {
		this.lessonsName = lessonsName;
	}

	public String getLessonsUrl() {
		return lessonsUrl;
	}

	public void setLessonsUrl(String lessonsUrl) {
		this.lessonsUrl = lessonsUrl;
	}

 

}
