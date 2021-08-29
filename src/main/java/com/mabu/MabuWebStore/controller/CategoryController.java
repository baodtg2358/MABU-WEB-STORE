package com.mabu.MabuWebStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mabu.MabuWebStore.entity.Category;
import com.mabu.MabuWebStore.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("api/v1/fa/category")
	public List<Category> getAllcategory() {
		List<Category> allcategory = categoryService.findAllCategory();
		return allcategory;
	}
	
	@GetMapping("/category/search")
	public ResponseEntity<Category> getCategoryByType(@RequestParam("categoryType") String keyword){
		Category categoryByType = categoryService.findCategoryByType(keyword);
		return new ResponseEntity<Category>(categoryByType, HttpStatus.OK);
	}
	
	@RequestMapping(value = "api/v1/fa/category", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@ModelAttribute Category category){
		Category newcategory = categoryService.createCategory(category);
		return new ResponseEntity<>(newcategory, HttpStatus.OK);
	}
	
	@RequestMapping(value = "api/v1/fa/category/update/{id}", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategory(@ModelAttribute Category category, @PathVariable("id") Integer id){
		category.setCategoryId(id);
		Category updateCategory = categoryService.updateCategory(category);
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("api/v1/fa/category/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer id){
		categoryService.deleteCategory(id);
		System.out.print(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

