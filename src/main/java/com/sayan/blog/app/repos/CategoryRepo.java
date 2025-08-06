package com.sayan.blog.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayan.blog.app.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {

}
