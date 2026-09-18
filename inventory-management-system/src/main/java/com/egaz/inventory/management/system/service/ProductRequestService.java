

package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.*;
import com.egaz.inventory.management.system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductRequestService {

    @Autowired private ProductRequestRepository requestRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private DepartmentRepository departmentRepo;

    // ========================================
    // STAFF: Submit new request
    // ========================================
    @Transactional
    public ProductRequest createRequest(Integer userId,
                                        Integer productId,
                                        Integer departmentId,
                                        Integer quantity,
                                        LocalDate issueDate,
                                        String description) {          // ✅ added

        if (userId == null)       throw new RuntimeException("User ID is required");
        if (productId == null)    throw new RuntimeException("Product ID is required");
        if (departmentId == null) throw new RuntimeException("Department ID is required");
        if (quantity == null || quantity <= 0)
            throw new RuntimeException("Quantity must be greater than 0");
        if (description == null || description.trim().isEmpty())
            throw new RuntimeException("Description is required");    // ✅ validate

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        Department dept = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found: " + departmentId));

        // Reject if requested quantity exceeds stock
        int stock;
        try {
            stock = Integer.parseInt(product.getProductQuantity());
        } catch (Exception e) {
            throw new RuntimeException("Invalid stock value on product");
        }
        if (quantity > stock) {
            throw new RuntimeException(
                    "Requested quantity (" + quantity + ") exceeds available stock (" + stock + ")"
            );
        }

        ProductRequest req = new ProductRequest();
        req.setUser(user);
        req.setProduct(product);
        req.setDepartment(dept);
        req.setQuantity(quantity);
        req.setIssueDate(issueDate != null ? issueDate : LocalDate.now());
        req.setDescription(description.trim());          // ✅ save it
        req.setStatus("PENDING");
        req.setRequestDate(LocalDate.now());

        return requestRepo.save(req);
    }

    // ========================================
    // STAFF: My requests
    // ========================================
    public List<ProductRequest> getRequestsByUser(Integer userId) {
        return requestRepo.findByUser_UserId(userId);
    }

    // ========================================
    // ADMIN: All requests
    // ========================================
    public List<ProductRequest> getAllRequests() {
        return requestRepo.findAll();
    }

    public List<ProductRequest> getRequestsByStatus(String status) {
        return requestRepo.findByStatus(status);
    }

    // ========================================
    // ADMIN: Approve (reduce stock + save note)
    // ========================================
    @Transactional
    public ProductRequest approveRequest(Integer requestId, String note) {   // ✅ note param
        ProductRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equalsIgnoreCase(req.getStatus())) {
            throw new RuntimeException("Request already processed: " + req.getStatus());
        }

        Product product = req.getProduct();
        int stock = Integer.parseInt(product.getProductQuantity());
        if (req.getQuantity() > stock) {
            throw new RuntimeException("Not enough stock. Available: " + stock);
        }

        product.setProductQuantity(String.valueOf(stock - req.getQuantity()));
        productRepo.save(product);

        req.setStatus("APPROVED");
        req.setAdminNote(note);                          // ✅ save the note
        return requestRepo.save(req);
    }

    // ========================================
    // ADMIN: Reject
    // ========================================
    @Transactional
    public ProductRequest rejectRequest(Integer requestId, String note) {
        ProductRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equalsIgnoreCase(req.getStatus())) {
            throw new RuntimeException("Request already processed: " + req.getStatus());
        }

        req.setStatus("REJECTED");
        req.setAdminNote(note);
        return requestRepo.save(req);
    }

    // ========================================
// ADMIN: Update request (edit fields)
// ========================================
    @Transactional
    public ProductRequest updateRequest(Integer requestId,
                                        Integer quantity,
                                        LocalDate issueDate,
                                        String description) {
        ProductRequest req = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));

        if (quantity == null || quantity <= 0)
            throw new RuntimeException("Quantity must be greater than 0");

        if (description == null || description.trim().isEmpty())
            throw new RuntimeException("Description is required");

        // Optional: if still PENDING, verify against stock
        if ("PENDING".equalsIgnoreCase(req.getStatus())) {
            int stock = Integer.parseInt(req.getProduct().getProductQuantity());
            if (quantity > stock) {
                throw new RuntimeException(
                        "Quantity (" + quantity + ") exceeds available stock (" + stock + ")"
                );
            }
        }

        req.setQuantity(quantity);
        req.setIssueDate(issueDate != null ? issueDate : req.getIssueDate());
        req.setDescription(description.trim());

        return requestRepo.save(req);
    }

    // ========================================
// ADMIN: Delete request
// ========================================
    @Transactional
    public void deleteRequest(Integer requestId) {
        if (!requestRepo.existsById(requestId)) {
            throw new RuntimeException("Request not found: " + requestId);
        }
        requestRepo.deleteById(requestId);
    }
}