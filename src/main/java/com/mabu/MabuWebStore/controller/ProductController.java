package com.mabu.MabuWebStore.controller;

import com.mabu.MabuWebStore.dto.ProductDTO;
import com.mabu.MabuWebStore.entity.Category;
import com.mabu.MabuWebStore.entity.Product;
import com.mabu.MabuWebStore.repository.CategoryRepository;
import com.mabu.MabuWebStore.repository.IProductRepo;
import com.mabu.MabuWebStore.service.IProductService;
import com.mabu.MabuWebStore.uploadfile.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/fa")
public class ProductController {

    private final StorageService storageService;

    @Autowired
    IProductService productService;

    @Autowired
    IProductRepo productRepo;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public ProductController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {
    	System.out.println("Products Is calling");
        List<Product> allProduct = productService.findAllProduct();
        return new ResponseEntity<>(allProduct, HttpStatus.OK);

    }

    @GetMapping("/products/search")
    public ResponseEntity<?> getProductByName(@RequestParam String keyword) {
        Product productByName = productService.findProductByName(keyword);
        return new ResponseEntity<>(productByName, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDTO product, @RequestParam(value ="file", required = false) MultipartFile file) {
        Category category = categoryRepository.findById(product.getCategoryID()).get();
        Product productEntity = new Product();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setStatus(product.getStatus());
        productEntity.setSize(product.getSize());
        productEntity.setCategory(category);

        if(file != null && !file.isEmpty()){
            storageService.store(file);
            productEntity.setPicture(file.getOriginalFilename());
        }

//
        Product newProduct = productService.createProduct(productEntity);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductDTO product, @RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println(product);

        Category category = categoryRepository.findById(product.getCategoryID()).get();
        Product productEntity = new Product();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setStatus(product.getStatus());
        productEntity.setSize(product.getSize());
        productEntity.setCategory(category);

        if(file != null && !file.isEmpty()){
            storageService.store(file);
            productEntity.setPicture(file.getOriginalFilename());
        }


        Product updateProduct = productService.updateProduct(productEntity);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        Product product = productRepo.findById(id).get();

        product.setStatus(false);

        productService.updateProduct(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/products/recover/{id}")
    public ResponseEntity<?> recoverProduct(@PathVariable Long id) {

        Product product = productRepo.findById(id).get();

        product.setStatus(true);

        productService.updateProduct(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
