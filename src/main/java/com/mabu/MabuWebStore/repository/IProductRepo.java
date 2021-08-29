package com.mabu.MabuWebStore.repository;

import com.mabu.MabuWebStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepo extends JpaRepository<Product, Long> {

    Product findByName(String name);
}
