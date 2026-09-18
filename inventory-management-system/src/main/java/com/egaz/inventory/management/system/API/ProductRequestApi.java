package com.egaz.inventory.management.system.API;//package com.egaz.inventory.management.system.API;
//
//import com.egaz.inventory.management.system.model.ProductRequest;
//import com.egaz.inventory.management.system.service.ProductRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/product-requests")
//@CrossOrigin(origins = "*")
//public class ProductRequestApi {
//
//    @Autowired
//    private ProductRequestService requestService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createRequest(
//            @RequestBody RequestPayload payload,
//            @RequestParam Integer userId) {
//        try {
//            ProductRequest saved = requestService.createRequest(
//                    userId,
//                    payload.getProductId(),
//                    payload.getDepartmentId(),
//                    payload.getQuantity(),
//                    payload.getIssueDate()
//            );
//            return ResponseEntity.ok(saved);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//
//
//    @GetMapping("/my-requests")
//    public ResponseEntity<?> getMyRequests(@RequestParam Integer userId) {
//        try {
//            return ResponseEntity.ok(requestService.getRequestsByUser(userId));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/all")
//    public List<ProductRequest> getAllRequests() {
//        return requestService.getAllRequests();
//    }
//
//    @GetMapping("/pending")
//    public List<ProductRequest> getPendingRequests() {
//        return requestService.getRequestsByStatus("PENDING");
//    }
//
//    @PutMapping("/approve/{requestId}")
//    public ResponseEntity<?> approveRequest(@PathVariable Integer requestId) {
//        try {
//            return ResponseEntity.ok(requestService.approveRequest(requestId));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PutMapping("/reject/{requestId}")
//    public ResponseEntity<?> rejectRequest(
//            @PathVariable Integer requestId,
//            @RequestParam(required = false) String note) {
//        try {
//            return ResponseEntity.ok(requestService.rejectRequest(requestId, note));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    public static class RequestPayload {
//        private Integer productId;
//        private Integer departmentId;
//        private Integer quantity;
//        private LocalDate issueDate;
//
//        public Integer getProductId() { return productId; }
//        public void setProductId(Integer productId) { this.productId = productId; }
//
//        public Integer getDepartmentId() { return departmentId; }
//        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
//
//        public Integer getQuantity() { return quantity; }
//        public void setQuantity(Integer quantity) { this.quantity = quantity; }
//
//        public LocalDate getIssueDate() { return issueDate; }
//        public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
//    }
//}

import com.egaz.inventory.management.system.model.ProductRequest;
import com.egaz.inventory.management.system.service.ProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-requests")
@CrossOrigin(origins = "*")
public class ProductRequestApi {

    @Autowired
    private ProductRequestService requestService;

    @PostMapping("/create")
    public ResponseEntity<?> createRequest(
            @RequestBody ProductRequest request,
            @RequestParam Integer userId,
            @RequestParam Integer productId,
            @RequestParam Integer departmentId) {
        try {
            ProductRequest saved = requestService.createRequest(
                    userId,
                    productId,
                    departmentId,
                    request.getQuantity(),
                    request.getIssueDate(),
                    request.getDescription()
            );
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-requests")
    public ResponseEntity<?> getMyRequests(@RequestParam Integer userId) {
        try {
            return ResponseEntity.ok(requestService.getRequestsByUser(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<ProductRequest> getAllRequests() {
        return requestService.getAllRequests();
    }

    @GetMapping("/pending")
    public List<ProductRequest> getPendingRequests() {
        return requestService.getRequestsByStatus("PENDING");
    }

    @PutMapping("/approve/{requestId}")
    public ResponseEntity<?> approveRequest(
            @PathVariable Integer requestId,
            @RequestParam(required = false) String note) {
        try {
            return ResponseEntity.ok(requestService.approveRequest(requestId, note));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(
            @PathVariable Integer requestId,
            @RequestParam(required = false) String note) {
        try {
            return ResponseEntity.ok(requestService.rejectRequest(requestId, note));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{requestId}")
    public ResponseEntity<?> updateRequest(
            @PathVariable Integer requestId,
            @RequestBody ProductRequest body) {
        try {
            ProductRequest updated = requestService.updateRequest(
                    requestId,
                    body.getQuantity(),
                    body.getIssueDate(),
                    body.getDescription()
            );
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId) {
        try {
            requestService.deleteRequest(requestId);
            return ResponseEntity.ok("Request deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}