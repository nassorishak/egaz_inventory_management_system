package com.egaz.inventory.management.system.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    // Who is receiving the product
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

    // The request this contract fulfills
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private AdminRequest adminRequest;

    // Product details (copied at signing time for historical record)
    private String productName;
    private Integer quantity;

    // Contract fields the staff fills in
    private LocalDate startDate;
    private LocalDate endDate;
    private String purpose;               // reason for taking the product
    private String responsibilityNotes;   // agreement text
    private String staffSignature;        // typed full name

    // Admin decision fields
    private String status;                // SUBMITTED | APPROVED | REJECTED
    private String adminNote;

    // Getters & setters
    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }

    public User getStaff() { return staff; }
    public void setStaff(User staff) { this.staff = staff; }

    public AdminRequest getAdminRequest() { return adminRequest; }
    public void setAdminRequest(AdminRequest adminRequest) { this.adminRequest = adminRequest; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getResponsibilityNotes() { return responsibilityNotes; }
    public void setResponsibilityNotes(String responsibilityNotes) { this.responsibilityNotes = responsibilityNotes; }

    public String getStaffSignature() { return staffSignature; }
    public void setStaffSignature(String staffSignature) { this.staffSignature = staffSignature; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }
}