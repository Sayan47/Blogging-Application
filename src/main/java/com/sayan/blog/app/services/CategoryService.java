package com.sayan.blog.app.services;

import java.util.List;

import com.sayan.blog.app.payloads.CategoryDto;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

	// delete
	void deleteCategory(String categoryId);

	// get
	CategoryDto getCategory(String categoryId);

	// get All

	List<CategoryDto> getCategories();

}
