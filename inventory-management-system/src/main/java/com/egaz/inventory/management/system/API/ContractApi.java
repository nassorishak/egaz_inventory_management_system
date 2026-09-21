package com.egaz.inventory.management.system.API;
import com.egaz.inventory.management.system.model.Contract;
import com.egaz.inventory.management.system.model.User;
import com.egaz.inventory.management.system.model.AdminRequest;
import com.egaz.inventory.management.system.repository.ContractRepository;
import com.egaz.inventory.management.system.repository.UserRepository;
import com.egaz.inventory.management.system.repository.AdminRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contracts")
public class ContractApi {

    @Autowired private ContractRepository contractRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private AdminRequestRepository requestRepo;

    // ---------- Staff submits contract ----------
    @PostMapping("/create")
    public ResponseEntity<?> createContract(@RequestBody Map<String, Object> payload) {
        try {
            Long staffId = Long.valueOf(payload.get("staffId").toString());
            Long requestId = Long.valueOf(payload.get("requestId").toString());

            User staff = userRepo.findById(staffId.intValue()).orElse(null);
            AdminRequest req = requestRepo.findById(Math.toIntExact(requestId)).orElse(null);

            if (staff == null) return ResponseEntity.badRequest().body("Staff not found");
            if (req == null) return ResponseEntity.badRequest().body("Request not found");

            Contract c = new Contract();
            c.setStaff(staff);
            c.setAdminRequest(req);
            c.setProductName((String) payload.getOrDefault("productName", ""));
            c.setQuantity(Integer.valueOf(payload.getOrDefault("quantity", "0").toString()));
            c.setPurpose((String) payload.getOrDefault("purpose", ""));
            c.setResponsibilityNotes((String) payload.getOrDefault("responsibilityNotes", ""));
            c.setStaffSignature((String) payload.getOrDefault("staffSignature", ""));
            c.setStartDate(java.time.LocalDate.parse(payload.get("startDate").toString()));
            c.setEndDate(java.time.LocalDate.parse(payload.get("endDate").toString()));
            c.setStatus("SUBMITTED");

            Contract saved = contractRepo.save(c);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating contract: " + e.getMessage());
        }
    }

    // ---------- Staff: my contracts ----------
    @GetMapping("/my/{staffId}")
    public ResponseEntity<List<Contract>> myContracts(@PathVariable Integer staffId) {
        return ResponseEntity.ok(contractRepo.findByStaff_UserIdOrderByContractIdDesc(staffId));
    }

    // ---------- Admin: all contracts ----------
    @GetMapping("/all")
    public ResponseEntity<List<Contract>> all() {
        return ResponseEntity.ok(contractRepo.findAllByOrderByContractIdDesc());
    }

    // ---------- Admin: approve/reject a contract ----------
    @PutMapping("/review/{contractId}")
    public ResponseEntity<?> review(@PathVariable Long contractId, @RequestBody Map<String, String> body) {
        Optional<Contract> opt = contractRepo.findById(contractId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Contract c = opt.get();
        String status = body.getOrDefault("status", "APPROVED").toUpperCase();
        String note = body.getOrDefault("adminNote", "");

        c.setStatus(status);
        c.setAdminNote(note);
        contractRepo.save(c);

        return ResponseEntity.ok(c);
    }


}