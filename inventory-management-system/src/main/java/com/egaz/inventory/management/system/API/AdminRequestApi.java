//package com.egaz.inventory.management.system.API;
//
//import com.egaz.inventory.management.system.model.AdminRequest;
//import com.egaz.inventory.management.system.model.Product;
//import com.egaz.inventory.management.system.service.AdminRequestService;
//import com.egaz.inventory.management.system.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/admin-requests")
//@CrossOrigin(origins = "*")
//public class AdminRequestApi {
//
//    @Autowired
//    private AdminRequestService service;
//
//    // ====== ADMIN ANA TUMĀ OMBĪ ======
//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestBody AdminRequest req) {
//        try {
//            return ResponseEntity.ok(service.createRequest(req));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== SUPERADMIN ANAONA MAOMBI YOTE ======
//    @GetMapping("/all")
//    public List<AdminRequest> getAll() {
//        return service.getAll();
//    }
//
//    @GetMapping("/pending")
//    public List<AdminRequest> getPending() {
//        return service.getPending();
//    }
//
//    // ====== ADMIN ANAONA MAOMBI YAKE ======
//    @GetMapping("/my/{adminId}")
//    public List<AdminRequest> getMy(@PathVariable Integer adminId) {
//        return service.getByAdmin(adminId);
//    }
//
//    // ====== SUPERADMIN ANAKUBALI ======
//    @PutMapping("/approve/{id}")
//    public ResponseEntity<?> approve(
//            @PathVariable Integer id,
//            @RequestParam Integer superAdminId,
//            @RequestParam(required = false) String note) {
//        try {
//            return ResponseEntity.ok(service.approveRequest(id, superAdminId, note));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== SUPERADMIN ANAKATAA ======
//    @PutMapping("/reject/{id}")
//    public ResponseEntity<?> reject(
//            @PathVariable Integer id,
//            @RequestParam Integer superAdminId,
//            @RequestParam(required = false) String note) {
//        try {
//            return ResponseEntity.ok(service.rejectRequest(id, superAdminId, note));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== ADMIN ANAKAMILISHA (toa product kwa staff) ======
//    @PutMapping("/complete/{id}")
//    public ResponseEntity<?> complete(@PathVariable Integer id) {
//        try {
//            return ResponseEntity.ok(service.completeRequest(id));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // Staff submits the contract for a request
//    @PutMapping("/submit-contract/{id}")
//    public ResponseEntity<?> submitContract(
//            @PathVariable Long id,
//            @RequestBody Map<String, Object> body) {
//        try {
//            ProductService adminRequestRepository = null;
//            Product req = adminRequestRepository.findById(Math.toIntExact(id)).orElse(null);
//            if (req == null) return ResponseEntity.notFound().build();
//
//            req.setContractPurpose((String) body.getOrDefault("purpose", ""));
//            req.setContractSignature((String) body.getOrDefault("signature", ""));
//            req.setContractStartDate(java.time.LocalDate.parse(body.get("startDate").toString()));
//            req.setContractEndDate(java.time.LocalDate.parse(body.get("endDate").toString()));
//            req.setContractStatus("SUBMITTED");
//
//            adminRequestRepository.save(req);
//            return ResponseEntity.ok(req);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error submitting contract: " + e.getMessage());
//        }
//    }
//
//    // Admin acknowledges the submitted contract
//    @PutMapping("/acknowledge-contract/{id}")
//    public ResponseEntity<?> acknowledgeContract(@PathVariable Long id) {
//        ProductService adminRequestRepository = null;
//        Product req = adminRequestRepository.findById(Math.toIntExact(id)).orElse(null);
//        if (req == null) return ResponseEntity.notFound().build();
//
//        req.setContractStatus("ACKNOWLEDGED");
//        adminRequestRepository.save(req);
//        return ResponseEntity.ok(req);
//    }
//
//
//
//}

//package com.egaz.inventory.management.system.API;
//
//import com.egaz.inventory.management.system.model.AdminRequest;
//import com.egaz.inventory.management.system.model.Product;
//import com.egaz.inventory.management.system.service.AdminRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/admin-requests")
//@CrossOrigin(origins = "*")
//public class AdminRequestApi {
//
//    @Autowired
//    private AdminRequestService service;
//
//    // ====== ADMIN ANA TUMĀ OMBĪ ======
//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestBody AdminRequest req) {
//        try {
//            return ResponseEntity.ok(service.createRequest(req));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== SUPERADMIN ANAONA MAOMBI YOTE ======
//    @GetMapping("/all")
//    public List<AdminRequest> getAll() {
//        return service.getAll();
//    }
//
//    @GetMapping("/pending")
//    public List<AdminRequest> getPending() {
//        return service.getPending();
//    }
//
//    // ====== ADMIN ANAONA MAOMBI YAKE ======
//    @GetMapping("/my/{adminId}")
//    public List<AdminRequest> getMy(@PathVariable Integer adminId) {
//        return service.getByAdmin(adminId);
//    }
//
//    // ====== SUPERADMIN ANAKUBALI ======
//    @PutMapping("/approve/{id}")
//    public ResponseEntity<?> approve(
//            @PathVariable Integer id,
//            @RequestParam Integer superAdminId,
//            @RequestParam(required = false) String note) {
//        try {
//            return ResponseEntity.ok(service.approveRequest(id, superAdminId, note));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== SUPERADMIN ANAKATAA ======
//    @PutMapping("/reject/{id}")
//    public ResponseEntity<?> reject(
//            @PathVariable Integer id,
//            @RequestParam Integer superAdminId,
//            @RequestParam(required = false) String note) {
//        try {
//            return ResponseEntity.ok(service.rejectRequest(id, superAdminId, note));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== ADMIN ANAKAMILISHA (toa product kwa staff) ======
//    @PutMapping("/complete/{id}")
//    public ResponseEntity<?> complete(@PathVariable Integer id) {
//        try {
//            return ResponseEntity.ok(service.completeRequest(id));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ====== STAFF ANATUMA MKATABA ======
//    @PutMapping("/submit-contract/{id}")
//    public ResponseEntity<?> submitContract(
//            @PathVariable Integer id,
//            @RequestBody Map<String, Object> body) {
//        try {
//            AdminRequest req = service.getById(id);
//            if (req == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Request not found with ID: " + id);
//            }
//
//            String purpose   = strOrEmpty(body.get("purpose"));
//            String signature = strOrEmpty(body.get("signature"));
//            String startStr  = strOrEmpty(body.get("startDate"));
//            String endStr    = strOrEmpty(body.get("endDate"));
//
//            if (purpose.isEmpty())   return badRequest("Purpose is required");
//            if (signature.isEmpty()) return badRequest("Signature is required");
//            if (startStr.isEmpty())  return badRequest("Start date is required");
//            if (endStr.isEmpty())    return badRequest("End date is required");
//
//            java.time.LocalDate start;
//            java.time.LocalDate end;
//            try {
//                start = java.time.LocalDate.parse(startStr);
//                end   = java.time.LocalDate.parse(endStr);
//            } catch (java.time.format.DateTimeParseException ex) {
//                return badRequest(
//                        "Invalid date format. Use YYYY-MM-DD. Got start='"
//                                + startStr + "', end='" + endStr + "'");
//            }
//
//            if (end.isBefore(start)) {
//                return badRequest("End date cannot be before start date");
//            }
//
//            req.setContractPurpose(purpose);
//            req.setContractSignature(signature);
//            req.setContractStartDate(start);
//            req.setContractEndDate(end);
//            req.setContractStatus("SUBMITTED");
//
//            return ResponseEntity.ok(service.save(req));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error submitting contract: " + e.getMessage());
//        }
//    }
//
//    // ====== ADMIN ANAKUBALI MKATABA ======
//    @PutMapping("/acknowledge-contract/{id}")
//    public ResponseEntity<?> acknowledgeContract(@PathVariable Integer id) {
//        try {
//            AdminRequest req = service.getById(id);
//            if (req == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Request not found with ID: " + id);
//            }
//
//            req.setContractStatus("ACKNOWLEDGED");
//            return ResponseEntity.ok(service.save(req));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error acknowledging contract: " + e.getMessage());
//        }
//
//    }
//
//    // ============================================
//    // ====== HELPER METHODS ======
//    // ============================================
//
//    private static String strOrEmpty(Object o) {
//        return o == null ? "" : o.toString().trim();
//    }
//
//    private static ResponseEntity<?> badRequest(String msg) {
//        return ResponseEntity.badRequest().body(msg);
//    }
//}

package com.egaz.inventory.management.system.API;

import com.egaz.inventory.management.system.model.AdminRequest;
import com.egaz.inventory.management.system.service.AdminRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // ====== STAFF ANATUMA MKATABA ======
    @PutMapping("/submit-contract/{id}")
    public ResponseEntity<?> submitContract(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body) {
        try {
            AdminRequest req = service.getById(id);
            if (req == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Request not found with ID: " + id);
            }

            String purpose   = strOrEmpty(body.get("purpose"));
            String signature = strOrEmpty(body.get("signature"));
            String startStr  = strOrEmpty(body.get("startDate"));
            String endStr    = strOrEmpty(body.get("endDate"));

            if (purpose.isEmpty())   return badRequest("Purpose is required");
            if (signature.isEmpty()) return badRequest("Signature is required");
            if (startStr.isEmpty())  return badRequest("Start date is required");
            if (endStr.isEmpty())    return badRequest("End date is required");

            java.time.LocalDate start;
            java.time.LocalDate end;
            try {
                start = java.time.LocalDate.parse(startStr);
                end   = java.time.LocalDate.parse(endStr);
            } catch (java.time.format.DateTimeParseException ex) {
                return badRequest(
                        "Invalid date format. Use YYYY-MM-DD. Got start='"
                                + startStr + "', end='" + endStr + "'");
            }

            if (end.isBefore(start)) {
                return badRequest("End date cannot be before start date");
            }

            req.setContractPurpose(purpose);
            req.setContractSignature(signature);
            req.setContractStartDate(start);
            req.setContractEndDate(end);
            req.setContractStatus("SUBMITTED");

            return ResponseEntity.ok(service.save(req));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting contract: " + e.getMessage());
        }
    }

    // ====== ADMIN ANAKUBALI MKATABA ======
    @PutMapping("/acknowledge-contract/{id}")
    public ResponseEntity<?> acknowledgeContract(@PathVariable Integer id) {
        try {
            AdminRequest req = service.getById(id);
            if (req == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Request not found with ID: " + id);
            }

            req.setContractStatus("ACKNOWLEDGED");
            return ResponseEntity.ok(service.save(req));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error acknowledging contract: " + e.getMessage());
        }
    }

    // ============================================
    // ====== UPDATE REQUEST ======
    // ============================================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRequest(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body) {
        try {
            AdminRequest req = service.getById(id);
            if (req == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Request not found with ID: " + id);
            }

            // Only update fields present in the payload
            if (body.containsKey("requestType") && body.get("requestType") != null)
                req.setRequestType(strOrEmpty(body.get("requestType")));

            if (body.containsKey("status") && body.get("status") != null)
                req.setStatus(strOrEmpty(body.get("status")));

            if (body.containsKey("quantity") && body.get("quantity") != null) {
                try {
                    req.setQuantity(Integer.valueOf(body.get("quantity").toString()));
                } catch (NumberFormatException nfe) {
                    return badRequest("Quantity must be a number");
                }
            }

            if (body.containsKey("staffName"))
                req.setStaffName(strOrEmpty(body.get("staffName")));

            if (body.containsKey("departmentName"))
                req.setDepartmentName(strOrEmpty(body.get("departmentName")));

            if (body.containsKey("description"))
                req.setDescription(strOrEmpty(body.get("description")));

            if (body.containsKey("superAdminNote"))
                req.setSuperAdminNote(strOrEmpty(body.get("superAdminNote")));

            return ResponseEntity.ok(service.save(req));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating request: " + e.getMessage());
        }
    }

    // ============================================
    // ====== DELETE REQUEST ======
    // ============================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        try {
            AdminRequest req = service.getById(id);
            if (req == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Request not found with ID: " + id);
            }

            service.deleteById(id);
            return ResponseEntity.ok("Request deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting request: " + e.getMessage());
        }
    }

    // ============================================
    // ====== HELPER METHODS ======
    // ============================================

    private static String strOrEmpty(Object o) {
        return o == null ? "" : o.toString().trim();
    }

    private static ResponseEntity<?> badRequest(String msg) {
        return ResponseEntity.badRequest().body(msg);
    }
}