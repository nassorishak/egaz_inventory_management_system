package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.Product;
import com.egaz.inventory.management.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer userId) {
        return productRepository.findById(userId);
    }

    public Product save(Optional<Product> product) {
        return productRepository.save(product.get());
    }

    public void deleteById(Integer id) {

    }
    public Product save(Product product) {
        return productRepository.save(product);
    }


}