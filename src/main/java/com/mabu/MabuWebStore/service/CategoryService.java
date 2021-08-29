package com.mabu.MabuWebStore.service;

import java.util.List;

import com.mabu.MabuWebStore.entity.Category;



public interface CategoryService {
	List<Category> findAllCategory();
		
	Category findCategoryByType(String keyword);
	
	Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Integer id);
}
