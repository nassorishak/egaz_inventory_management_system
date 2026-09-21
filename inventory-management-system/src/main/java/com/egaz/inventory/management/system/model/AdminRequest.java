//package com.egaz.inventory.management.system.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "admin_requests")
//public class AdminRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    // ADMIN_REQUEST (kutoa product kwa staff)
//    // STOCK_REQUEST (kuomba stock mpya)
//    @Column(nullable = false)
//    private String requestType;
//
//    // PENDING, APPROVED, REJECTED, COMPLETED
//    @Column(nullable = false)
//    private String status = "PENDING";
//
//    // Admin aliyeomba
//    private Integer adminId;
//    private String adminName;
//
//    // Product husika
//    private Integer productId;
//    private String productName;
//
//    private Integer quantity;
//
//    // Staff atakayepokea (kwa ADMIN_REQUEST)
//    private Integer staffId;
//    private String staffName;
//
//    // Department ya staff
//    private Integer departmentId;
//    private String departmentName;
//
//    private LocalDate issueDate;
//
//    @Column(length = 1000)
//    private String description;
//
//    // Jibu la SuperAdmin
//    @Column(length = 1000)
//    private String superAdminNote;
//
//    private Integer superAdminId;
//    private LocalDateTime respondedAt;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    // ===== Getters & Setters =====
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public String getRequestType() { return requestType; }
//    public void setRequestType(String requestType) { this.requestType = requestType; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//
//    public Integer getAdminId() { return adminId; }
//    public void setAdminId(Integer adminId) { this.adminId = adminId; }
//
//    public String getAdminName() { return adminName; }
//    public void setAdminName(String adminName) { this.adminName = adminName; }
//
//    public Integer getProductId() { return productId; }
//    public void setProductId(Integer productId) { this.productId = productId; }
//
//    public String getProductName() { return productName; }
//    public void setProductName(String productName) { this.productName = productName; }
//
//    public Integer getQuantity() { return quantity; }
//    public void setQuantity(Integer quantity) { this.quantity = quantity; }
//
//    public Integer getStaffId() { return staffId; }
//    public void setStaffId(Integer staffId) { this.staffId = staffId; }
//
//    public String getStaffName() { return staffName; }
//    public void setStaffName(String staffName) { this.staffName = staffName; }
//
//    public Integer getDepartmentId() { return departmentId; }
//    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
//
//    public String getDepartmentName() { return departmentName; }
//    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
//
//    public LocalDate getIssueDate() { return issueDate; }
//    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public String getSuperAdminNote() { return superAdminNote; }
//    public void setSuperAdminNote(String superAdminNote) { this.superAdminNote = superAdminNote; }
//
//    public Integer getSuperAdminId() { return superAdminId; }
//    public void setSuperAdminId(Integer superAdminId) { this.superAdminId = superAdminId; }
//
//    public LocalDateTime getRespondedAt() { return respondedAt; }
//    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//
//    public void setContractPurpose(String purpose) {
//    }
//
//    public void setContractSignature(String signature) {
//    }
//
//    public void setContractStartDate(LocalDate startDate) {
//    }
//
//    public void setContractEndDate(LocalDate endDate) {
//    }
//
//    public void setContractStatus(String submitted) {
//    }
//
//    public void setSuperAdmin(User superAdmin) {
//    }
//}
package com.egaz.inventory.management.system.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_requests")
public class AdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ADMIN_REQUEST (kutoa product kwa staff)
    // STOCK_REQUEST (kuomba stock mpya)
    @Column(nullable = false)
    private String requestType;

    // PENDING, APPROVED, REJECTED, COMPLETED
    @Column(nullable = false)
    private String status = "PENDING";

    // Admin aliyeomba
    private Integer adminId;
    private String adminName;

    // Product husika
    private Integer productId;
    private String productName;

    private Integer quantity;

    // Staff atakayepokea (kwa ADMIN_REQUEST)
    private Integer staffId;
    private String staffName;

    // Department ya staff
    private Integer departmentId;
    private String departmentName;

    private LocalDate issueDate;

    @Column(length = 1000)
    private String description;

    // Jibu la SuperAdmin
    @Column(length = 1000)
    private String superAdminNote;

    private Integer superAdminId;
    private LocalDateTime respondedAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    // =====================================================
    // ===== CONTRACT FIELDS (filled by staff) =====
    // =====================================================
    @Column(length = 1000)
    private String contractPurpose;

    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    @Column(length = 255)
    private String contractSignature;

    // null | SUBMITTED | ACKNOWLEDGED
    @Column(length = 30)
    private String contractStatus;

    // ===== Getters & Setters =====

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getStaffId() { return staffId; }
    public void setStaffId(Integer staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSuperAdminNote() { return superAdminNote; }
    public void setSuperAdminNote(String superAdminNote) { this.superAdminNote = superAdminNote; }

    public Integer getSuperAdminId() { return superAdminId; }
    public void setSuperAdminId(Integer superAdminId) { this.superAdminId = superAdminId; }

    public LocalDateTime getRespondedAt() { return respondedAt; }
    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ===== Contract getters/setters =====

    public String getContractPurpose() { return contractPurpose; }
    public void setContractPurpose(String contractPurpose) { this.contractPurpose = contractPurpose; }

    public LocalDate getContractStartDate() { return contractStartDate; }
    public void setContractStartDate(LocalDate contractStartDate) { this.contractStartDate = contractStartDate; }

    public LocalDate getContractEndDate() { return contractEndDate; }
    public void setContractEndDate(LocalDate contractEndDate) { this.contractEndDate = contractEndDate; }

    public String getContractSignature() { return contractSignature; }
    public void setContractSignature(String contractSignature) { this.contractSignature = contractSignature; }

    public String getContractStatus() { return contractStatus; }
    public void setContractStatus(String contractStatus) { this.contractStatus = contractStatus; }
}