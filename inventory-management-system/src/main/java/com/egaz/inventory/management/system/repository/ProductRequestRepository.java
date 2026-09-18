package com.egaz.inventory.management.system.repository;

import com.egaz.inventory.management.system.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ProductRequest pr WHERE pr.product.productId = :productId")
    int deleteByProductId(@Param("productId") Integer productId);

    List<ProductRequest> findByUser_UserId(Integer userId);

    List<ProductRequest> findByStatus(String status);
}