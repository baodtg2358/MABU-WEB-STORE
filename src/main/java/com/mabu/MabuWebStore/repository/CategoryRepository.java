package com.mabu.MabuWebStore.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mabu.MabuWebStore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategoryType(String type);
}
