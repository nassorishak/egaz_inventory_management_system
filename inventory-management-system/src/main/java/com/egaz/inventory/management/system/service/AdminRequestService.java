package com.egaz.inventory.management.system.service;

import com.egaz.inventory.management.system.model.AdminRequest;
import com.egaz.inventory.management.system.repository.AdminRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminRequestService {

    @Autowired
    private AdminRequestRepository adminRequestRepository;

    public List<AdminRequest> getAll() {
        return adminRequestRepository.findAll();
    }

    public AdminRequest getById(Integer id) {
        return adminRequestRepository.findById(id).orElse(null);
    }

    public List<AdminRequest> getByAdmin(Integer adminId) {
        return adminRequestRepository.findByAdminId(adminId);
    }

    public List<AdminRequest> getPending() {
        return adminRequestRepository.findByStatus("PENDING");
    }

    public AdminRequest createRequest(AdminRequest req) {
        if (req == null) throw new RuntimeException("Request data cannot be empty");
        if (req.getProductName() == null || req.getProductName().trim().isEmpty())
            throw new RuntimeException("Product name is required");
        if (req.getQuantity() == null)
            throw new RuntimeException("Quantity is required");

        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());
        return adminRequestRepository.save(req);
    }

    public AdminRequest approveRequest(Integer id, Integer superAdminId, String note) {
        AdminRequest req = getById(id);
        if (req == null) throw new RuntimeException("Request not found with ID: " + id);

        req.setStatus("APPROVED");
        req.setSuperAdminId(superAdminId);
        if (note != null) req.setSuperAdminNote(note);
        req.setRespondedAt(LocalDateTime.now());
        return adminRequestRepository.save(req);
    }

    public AdminRequest rejectRequest(Integer id, Integer superAdminId, String note) {
        AdminRequest req = getById(id);
        if (req == null) throw new RuntimeException("Request not found with ID: " + id);

        req.setStatus("REJECTED");
        req.setSuperAdminId(superAdminId);
        if (note != null) req.setSuperAdminNote(note);
        req.setRespondedAt(LocalDateTime.now());
        return adminRequestRepository.save(req);
    }


    public AdminRequest completeRequest(Integer id) {
        AdminRequest req = getById(id);
        if (req == null) throw new RuntimeException("Request not found with ID: " + id);
        if (!"APPROVED".equalsIgnoreCase(req.getStatus()))
            throw new RuntimeException("Only APPROVED requests can be completed. Current: " + req.getStatus());

        req.setStatus("COMPLETED");
        return adminRequestRepository.save(req);
    }

    public AdminRequest save(AdminRequest req) {
        return adminRequestRepository.save(req);
    }
    public void deleteById(Integer id) {
        if (!adminRequestRepository.existsById(id)) {
            throw new RuntimeException("Request not found with ID: " + id);
        }
        adminRequestRepository.deleteById(id);
    }
}