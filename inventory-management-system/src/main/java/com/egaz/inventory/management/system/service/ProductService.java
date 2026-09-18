package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.Product;
import com.egaz.inventory.management.system.repository.ProductRepository;
import com.egaz.inventory.management.system.repository.ProductRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRequestRepository productRequestRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    // ✅ Only ONE save method — takes a Product, not an Optional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteById(Integer id) {
        int purged = productRequestRepository.deleteByProductId(id);
        System.out.println(">>> Purged " + purged + " product_request row(s) for product " + id);

        productRepository.deleteById(id);
        productRepository.flush();
        System.out.println(">>> Product " + id + " deleted");
    }

    public List<Product> generatePdfReport() {
        return productRepository.findAll();
    }
}