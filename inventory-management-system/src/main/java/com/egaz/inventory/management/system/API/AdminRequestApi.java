package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.AdminRequest;
import com.egaz.inventory.management.system.service.AdminRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-requests")
@CrossOrigin(origins = "*")
public class AdminRequestApi {

    @Autowired
    private AdminRequestService service;

    // ====== ADMIN ANA TUMĀ OMBĪ ======
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AdminRequest req) {
        try {
            return ResponseEntity.ok(service.createRequest(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ====== SUPERADMIN ANAONA MAOMBI YOTE ======
    @GetMapping("/all")
    public List<AdminRequest> getAll() {
        return service.getAll();
    }

    @GetMapping("/pending")
    public List<AdminRequest> getPending() {
        return service.getPending();
    }

    // ====== ADMIN ANAONA MAOMBI YAKE ======
    @GetMapping("/my/{adminId}")
    public List<AdminRequest> getMy(@PathVariable Integer adminId) {
        return service.getByAdmin(adminId);
    }

    // ====== SUPERADMIN ANAKUBALI ======
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(
            @PathVariable Integer id,
            @RequestParam Integer superAdminId,
            @RequestParam(required = false) String note) {
        try {
            return ResponseEntity.ok(service.approveRequest(id, superAdminId, note));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ====== SUPERADMIN ANAKATAA ======
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(
            @PathVariable Integer id,
            @RequestParam Integer superAdminId,
            @RequestParam(required = false) String note) {
        try {
            return ResponseEntity.ok(service.rejectRequest(id, superAdminId, note));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ====== ADMIN ANAKAMILISHA (toa product kwa staff) ======
    @PutMapping("/complete/{id}")
    public ResponseEntity<?> complete(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.completeRequest(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}