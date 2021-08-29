package com.mabu.MabuWebStore.serviceImpl;

import com.mabu.MabuWebStore.entity.Product;
import com.mabu.MabuWebStore.repository.IProductRepo;
import com.mabu.MabuWebStore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    IProductRepo productRepo;

    @Override
    public List<Product> findAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product findProductByName(String keyword) {
        return productRepo.findByName(keyword);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepo.saveAndFlush(product);
    }

    @Override
    public Boolean deleteProduct(Long id) {
        try{
            productRepo.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
