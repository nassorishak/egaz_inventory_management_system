package com.egaz.inventory.management.system.repository;

import com.egaz.inventory.management.system.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
    List<ProductRequest> findByUser_UserId(Integer userId);
    List<ProductRequest> findByStatus(String status);

}