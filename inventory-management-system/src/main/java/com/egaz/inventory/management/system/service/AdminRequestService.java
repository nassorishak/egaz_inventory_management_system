package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.AdminRequest;
import com.egaz.inventory.management.system.model.Product;
import com.egaz.inventory.management.system.repository.AdminRequestRepository;
import com.egaz.inventory.management.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminRequestService {

    @Autowired private AdminRequestRepository requestRepo;
    @Autowired private ProductRepository productRepo;

    // ============ 1. ADMIN ANA TUMĀ OMBĪ ============
    @Transactional
    public AdminRequest createRequest(AdminRequest req) {

        // Kama ni ADMIN_REQUEST, hakikisha stock ipo (kabla ya kuomba)
        if ("ADMIN_REQUEST".equals(req.getRequestType()) && req.getProductId() != null) {
            Product p = productRepo.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            req.setProductName(p.getProductName());
        }

        if ("STOCK_REQUEST".equals(req.getRequestType()) && req.getProductId() != null) {
            Product p = productRepo.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            req.setProductName(p.getProductName());
        }

        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());
        return requestRepo.save(req);
    }

    // ============ 2. SUPERADMIN ANAKUBALI ============
    @Transactional
    public AdminRequest approveRequest(Integer id, Integer superAdminId, String note) {
        AdminRequest r = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equals(r.getStatus())) {
            throw new RuntimeException("Request already " + r.getStatus());
        }

        r.setStatus("APPROVED");
        r.setSuperAdminId(superAdminId);
        r.setSuperAdminNote(note);
        r.setRespondedAt(LocalDateTime.now());

        // Kama ni STOCK_REQUEST, ongeza stock moja kwa moja
        if ("STOCK_REQUEST".equals(r.getRequestType()) && r.getProductId() != null) {
            Product p = productRepo.findById(r.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // ✅ FIX: convert String → int kwa usalama
            int currentStock = 0;
            try {
                if (p.getProductQuantity() != null && !p.getProductQuantity().isEmpty()) {
                    currentStock = Integer.parseInt(p.getProductQuantity());
                }
            } catch (NumberFormatException e) {
                currentStock = 0;
            }

            int newStock = currentStock + r.getQuantity();
            p.setProductQuantity(String.valueOf(newStock));
            productRepo.save(p);

            // Stock imeshaongezwa, hivyo request inakuwa COMPLETED moja kwa moja
            r.setStatus("COMPLETED");
        }

        return requestRepo.save(r);
    }

    // ============ 3. SUPERADMIN ANAKATAA ============
    @Transactional
    public AdminRequest rejectRequest(Integer id, Integer superAdminId, String note) {
        AdminRequest r = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        r.setStatus("REJECTED");
        r.setSuperAdminId(superAdminId);
        r.setSuperAdminNote(note);
        r.setRespondedAt(LocalDateTime.now());

        return requestRepo.save(r);
    }

    // ============ 4. ADMIN ANAKAMILISHA (toa product kwa staff) ============
    @Transactional
    public AdminRequest completeRequest(Integer id) {
        AdminRequest r = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"APPROVED".equals(r.getStatus())) {
            throw new RuntimeException("Request must be APPROVED first");
        }

        if (r.getProductId() != null && r.getQuantity() != null) {
            Product p = productRepo.findById(r.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // ✅ FIX: convert String → int kwa usalama
            int stock = 0;
            try {
                if (p.getProductQuantity() != null && !p.getProductQuantity().isEmpty()) {
                    stock = Integer.parseInt(p.getProductQuantity());
                }
            } catch (NumberFormatException e) {
                stock = 0;
            }

            if (stock < r.getQuantity()) {
                throw new RuntimeException("Not enough stock in store. Available: " + stock);
            }

            int newStock = stock - r.getQuantity();
            p.setProductQuantity(String.valueOf(newStock));
            productRepo.save(p);
        }

        r.setStatus("COMPLETED");
        return requestRepo.save(r);
    }

    // ============ QUERIES ============
    public List<AdminRequest> getAll() {
        return requestRepo.findAll();
    }

    public List<AdminRequest> getPending() {
        return requestRepo.findByStatusOrderByCreatedAtDesc("PENDING");
    }

    public List<AdminRequest> getByAdmin(Integer adminId) {
        return requestRepo.findByAdminIdOrderByCreatedAtDesc(adminId);
    }

    public List<AdminRequest> getByType(String type) {
        return requestRepo.findByRequestType(type);
    }
}