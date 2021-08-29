package com.mabu.MabuWebStore.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabu.MabuWebStore.entity.Category;
import com.mabu.MabuWebStore.repository.CategoryRepository;
import com.mabu.MabuWebStore.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	

	@Override
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findCategoryByType(String keyword) {
		return categoryRepository.findByCategoryType(keyword).isPresent()?categoryRepository.findByCategoryType(keyword).get():null;
	}

	@Override
	public Category createCategory(Category categories) {
		return categoryRepository.save(categories);
	}

	@Override
	public Category updateCategory(Category categories) {
		return categoryRepository.saveAndFlush(categories);
	}

	@Override
	public void deleteCategory(Integer id) {
			categoryRepository.deleteById(id);
		} 
	}
