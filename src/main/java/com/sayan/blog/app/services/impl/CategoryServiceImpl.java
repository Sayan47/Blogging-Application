package com.sayan.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sayan.blog.app.entities.Category;
import com.sayan.blog.app.exceptions.ResourceNotFoundException;
import com.sayan.blog.app.payloads.CategoryDto;
import com.sayan.blog.app.repos.CategoryRepo;
import com.sayan.blog.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = categoryDtoToCategory(categoryDto);
		Category newCategory = categoryRepo.save(category);
		return categoryToCategoryDto(newCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedCategory = categoryRepo.save(category);

		return categoryToCategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategory(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		categoryRepo.delete(category);

	}

	@Override
	public CategoryDto getCategory(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map((category) -> categoryToCategoryDto(category))
				.collect(Collectors.toList());
		return categoriesDto;
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
}
